package com.tracejp.starnight.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tracejp.starnight.dao.ExamPaperAnswerDao;
import com.tracejp.starnight.entity.*;
import com.tracejp.starnight.entity.bo.ExamPaperAnswerBo;
import com.tracejp.starnight.entity.enums.ExamPaperAnswerStatusEnum;
import com.tracejp.starnight.entity.enums.ExamPaperTypeEnum;
import com.tracejp.starnight.entity.enums.QuestionTypeEnum;
import com.tracejp.starnight.entity.po.ExamPaperQuestionItemPo;
import com.tracejp.starnight.entity.po.ExamPaperTitleItemPo;
import com.tracejp.starnight.entity.vo.student.ExamPaperAnswerSubmitItemVo;
import com.tracejp.starnight.entity.vo.student.ExamPaperAnswerSubmitVo;
import com.tracejp.starnight.exception.ServiceException;
import com.tracejp.starnight.service.*;
import com.tracejp.starnight.utils.ArrayStringUtils;
import com.tracejp.starnight.utils.StringUtils;
import com.tracejp.starnight.utils.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
@Service("examPaperAnswerService")
public class ExamPaperAnswerServiceImpl extends ServiceImpl<ExamPaperAnswerDao, ExamPaperAnswerEntity> implements ExamPaperAnswerService {

    @Autowired
    private ExamPaperService examPaperService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ExamPaperQuestionAnswerService examPaperQuestionAnswerService;

    @Autowired
    private TaskExamAnswerService taskExamAnswerService;

    @Autowired
    private TextContentService textContentService;

    @Autowired
    private ExamPaperAnswerDao examPaperAnswerDao;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Override
    public List<ExamPaperAnswerEntity> listPage(ExamPaperAnswerEntity examPaperAnswer) {
        return examPaperAnswerDao.listPage(examPaperAnswer);
    }

    @Override
    public ExamPaperAnswerBo buildExamPaperBo(ExamPaperAnswerSubmitVo answerVo, UserEntity user) {
        ExamPaperAnswerBo examPaperAnswerBo = new ExamPaperAnswerBo();
        ExamPaperEntity examPaperEntity = examPaperService.getById(answerVo.getId());
        if (examPaperEntity == null) {
            throw new ServiceException("试卷不存在");
        }
        examPaperAnswerBo.setExamPaper(examPaperEntity);

        // 任务试卷仅能做一次
        ExamPaperTypeEnum paperType = ExamPaperTypeEnum.fromCode(examPaperEntity.getPaperType());
        if (paperType == ExamPaperTypeEnum.TASK) {
            if (hasAnswerByPaperIdUserId(examPaperEntity.getId(), user.getId())) {
                throw new ServiceException("任务试卷仅能做一次");
            }
        }

        // 原始试卷问题集合 查找结构
        TextContentEntity paperContent = textContentService.getById(examPaperEntity.getFrameTextContentId());
        if (paperContent == null) {
            throw new ServiceException("题目不存在");
        }
        List<ExamPaperTitleItemPo> examPaperTitleItemPos = paperContent.getContentArray(ExamPaperTitleItemPo.class);
        List<Long> questionIds = examPaperTitleItemPos.stream()
                .flatMap(title -> title.getQuestionItems().stream()
                        .map(ExamPaperQuestionItemPo::getId))
                .collect(Collectors.toList());
        List<QuestionEntity> questionEntities = questionService.listByIds(questionIds);
        Map<Long, QuestionEntity> findQuestionMap = null;
        if (!CollectionUtils.isEmpty(questionEntities)) {
            findQuestionMap = questionEntities.stream()
                    .collect(Collectors.toMap(QuestionEntity::getId, question -> question));
        }

        // 构造提交答案问题集合 查找结构
        List<ExamPaperAnswerSubmitItemVo> answerItems = answerVo.getAnswerItems();
        Map<Long, ExamPaperAnswerSubmitItemVo> findQuestionAnswerMap = null;
        if (!CollectionUtils.isEmpty(answerItems)) {
            findQuestionAnswerMap = answerItems.stream()
                    .collect(Collectors.toMap(ExamPaperAnswerSubmitItemVo::getQuestionId, answer -> answer));
        }

        // 构造问题结构
        final Map<Long, QuestionEntity> findQuestionMapFinal = findQuestionMap;
        final Map<Long, ExamPaperAnswerSubmitItemVo> findQuestionAnswerMapFinal = findQuestionAnswerMap;
        List<ExamPaperQuestionAnswerEntity> questionAnswerEntities = examPaperTitleItemPos.stream()
                .flatMap(title -> title.getQuestionItems().stream().map(questionItem -> {
                    QuestionEntity question = null;
                    ExamPaperAnswerSubmitItemVo questionAnswer = null;
                    if (!CollectionUtils.isEmpty(findQuestionMapFinal)) {
                        question = findQuestionMapFinal.get(questionItem.getId());
                    }
                    if (!CollectionUtils.isEmpty(findQuestionAnswerMapFinal)) {
                        questionAnswer = findQuestionAnswerMapFinal.get(questionItem.getId());
                    }

                    return buildAnswerQuestionEntityFormVo(question, questionAnswer, examPaperEntity, user,
                            questionItem.getItemOrder());
                })).collect(Collectors.toList());
        examPaperAnswerBo.setQuestionAnswers(questionAnswerEntities);

        // 构造试卷答案实体
        ExamPaperAnswerEntity examPaperAnswerEntity = buildExamPaperAnswerFromVo(answerVo, examPaperEntity,
                questionAnswerEntities, user);
        examPaperAnswerBo.setAnswer(examPaperAnswerEntity);

        return examPaperAnswerBo;
    }

    private ExamPaperAnswerEntity buildExamPaperAnswerFromVo(ExamPaperAnswerSubmitVo answerVo,
                                                             ExamPaperEntity examPaperEntity,
                                                             List<ExamPaperQuestionAnswerEntity> questionAnswerEntities,
                                                             UserEntity user) {
        // 分数 & 正确题数 求和
        int systemScore = questionAnswerEntities.stream()
                .mapToInt(ExamPaperQuestionAnswerEntity::getCustomerScore)
                .sum();
        long doRightCount = questionAnswerEntities.stream()
                .filter(ExamPaperQuestionAnswerEntity::getDoRight)
                .count();

        // 设置基本信息
        ExamPaperAnswerEntity examPaperAnswerEntity = new ExamPaperAnswerEntity();
        examPaperAnswerEntity.setPaperName(examPaperEntity.getName());
        examPaperAnswerEntity.setDoTime(answerVo.getDoTime());
        examPaperAnswerEntity.setExamPaperId(examPaperEntity.getId());
        examPaperAnswerEntity.setCreateBy(user.getId());
        examPaperAnswerEntity.setSubjectId(examPaperEntity.getSubjectId());
        examPaperAnswerEntity.setQuestionCount(examPaperEntity.getQuestionCount());
        examPaperAnswerEntity.setPaperScore(examPaperEntity.getScore());
        examPaperAnswerEntity.setPaperType(examPaperEntity.getPaperType());
        examPaperAnswerEntity.setSystemScore(systemScore);
        examPaperAnswerEntity.setTaskExamId(examPaperEntity.getTaskExamId());
        examPaperAnswerEntity.setQuestionCorrect((int) doRightCount);

        // 设置答卷状态 - 任务试卷需要等待批改
        ExamPaperTypeEnum paperType = ExamPaperTypeEnum.fromCode(examPaperEntity.getPaperType());

        // 任务试卷需要等待批改
        boolean needJudge = questionAnswerEntities.stream()
                .anyMatch(q -> QuestionTypeEnum.needSaveTextContent(q.getQuestionType()));
        if (paperType == ExamPaperTypeEnum.TASK && needJudge) {
            examPaperAnswerEntity.setStatus(ExamPaperAnswerStatusEnum.WaitJudge.getCode());
        } else {
            examPaperAnswerEntity.setUserScore(examPaperAnswerEntity.getSystemScore());
            examPaperAnswerEntity.setStatus(ExamPaperAnswerStatusEnum.Complete.getCode());
        }

        return examPaperAnswerEntity;
    }

    public boolean hasAnswerByPaperIdUserId(Long paperId, Long userId) {
        LambdaQueryWrapper<ExamPaperAnswerEntity> wrapper = Wrappers.lambdaQuery(ExamPaperAnswerEntity.class)
                .eq(ExamPaperAnswerEntity::getExamPaperId, paperId)
                .eq(ExamPaperAnswerEntity::getCreateBy, userId);
        return examPaperAnswerDao.selectCount(wrapper) > 0;
    }

    @Transactional
    @Override
    public CompletableFuture<Void> saveAnswerBoAsync(ExamPaperAnswerBo examPaperAnswerBo) {
        return CompletableFuture.runAsync(() -> {
            ExamPaperEntity examPaper = examPaperAnswerBo.getExamPaper();
            ExamPaperAnswerEntity examPaperAnswerEntity = examPaperAnswerBo.getAnswer();
            List<ExamPaperQuestionAnswerEntity> questionAnswers = examPaperAnswerBo.getQuestionAnswers();
            if (examPaper == null || examPaperAnswerEntity == null || questionAnswers == null) {
                throw new ServiceException("答卷保存失败，业务Bo构造异常");
            }

            // 答卷保存
            this.save(examPaperAnswerEntity);

            // 问题保存 - 简答 填空 需要保存文本内容
            for (ExamPaperQuestionAnswerEntity question : questionAnswers) {
                if (QuestionTypeEnum.needSaveTextContent(question.getQuestionType())) {
                    TextContentEntity textContentEntity = new TextContentEntity();
                    textContentEntity.setContent(question.getAnswer());
                    textContentService.save(textContentEntity);
                    question.setTextContentId(textContentEntity.getId());
                    question.setAnswer(null);
                }
            }
            for (ExamPaperQuestionAnswerEntity question : questionAnswers) {
                question.setExamPaperAnswerId(examPaperAnswerEntity.getId());
            }
            examPaperQuestionAnswerService.saveBatch(questionAnswers);

            // 任务试卷更新
            if (ExamPaperTypeEnum.fromCode(examPaper.getPaperType()) == ExamPaperTypeEnum.TASK) {
                taskExamAnswerService.saveByPaperAnswer(examPaper, examPaperAnswerEntity);
            }

        }, threadPoolExecutor);
    }

    /**
     * 通过 原始问题、答案、用户 构造问题答案实体
     */
    private ExamPaperQuestionAnswerEntity buildAnswerQuestionEntityFormVo(QuestionEntity question,
                                                                          ExamPaperAnswerSubmitItemVo questionAnswer,
                                                                          ExamPaperEntity examPaperEntity,
                                                                          UserEntity user,
                                                                          Integer itemOrder) {
        ExamPaperQuestionAnswerEntity questionAnswerEntity = new ExamPaperQuestionAnswerEntity();
        if (question == null) {
            throw new ServiceException("题目不存在");
        }

        // 基本信息设置
        questionAnswerEntity.setQuestionId(question.getId());
        questionAnswerEntity.setExamPaperId(examPaperEntity.getId());
        questionAnswerEntity.setQuestionScore(question.getScore());
        questionAnswerEntity.setSubjectId(examPaperEntity.getSubjectId());
        questionAnswerEntity.setItemOrder(itemOrder);
        questionAnswerEntity.setCreateBy(user.getId());
        questionAnswerEntity.setQuestionType(question.getQuestionType());
        questionAnswerEntity.setQuestionTextContentId(question.getInfoTextContentId());

        // 计算分数
        if (questionAnswer == null) {
            questionAnswerEntity.setCustomerScore(0);
        } else {
            // 预批改
            setQuestionAnswerProperties(questionAnswerEntity, question, questionAnswer);
        }

        return questionAnswerEntity;
    }

    /**
     * 根据题型 设置 问题答案属性
     * 包括（系统）预批改题目
     */
    private void setQuestionAnswerProperties(ExamPaperQuestionAnswerEntity questionAnswerEntity,
                                             QuestionEntity question,
                                             ExamPaperAnswerSubmitItemVo questionAnswer) {
        QuestionTypeEnum questionTypeEnum = QuestionTypeEnum.fromCode(question.getQuestionType());
        boolean isRight;
        String strAnswer;
        switch (questionTypeEnum) {
            // 单选、判断：比对答案
            case SingleChoice:
            case TrueFalse:
                questionAnswerEntity.setAnswer(questionAnswer.getContent());
                isRight = StringUtils.equals(question.getCorrect(), questionAnswer.getContent());
                questionAnswerEntity.setDoRight(isRight);
                questionAnswerEntity.setCustomerScore(isRight ? question.getScore() : 0);
                break;
            // 多选：比对答案数组
            case MultipleChoice:
                strAnswer = ArrayStringUtils.contentToString(questionAnswer.getContentArray());
                questionAnswerEntity.setAnswer(strAnswer);
                boolean hasError = false;
                long doRightCount = 0;
                List<String> correct = ArrayStringUtils.contentToArray(question.getCorrect());
                List<String> answers = questionAnswer.getContentArray();
                for (String answer : answers) {
                    if (StringUtils.isNotEmpty(answer) && correct.contains(answer)) {
                        doRightCount++;
                    } else {
                        hasError = true;
                    }
                }
                if (doRightCount == correct.size() && !hasError) {  // 全对得满分
                    questionAnswerEntity.setDoRight(true);
                    questionAnswerEntity.setCustomerScore(question.getScore());
                } else if (hasError) {  // 错一个不得分
                    questionAnswerEntity.setDoRight(false);
                    questionAnswerEntity.setCustomerScore(0);
                } else {  // 部分对得半分
                    questionAnswerEntity.setDoRight(false);
                    questionAnswerEntity.setCustomerScore(question.getScore() / 2);
                }
                break;
            // 填空：比对答案，每空得分为总分数的百分比
            case GapFilling:
                List<String> answer = questionAnswer.getContentArray();
                strAnswer = ArrayStringUtils.contentToString(answer);
                questionAnswerEntity.setAnswer(strAnswer);
                int rightCount = 0;
                List<String> origin = ArrayStringUtils.contentToArray(question.getCorrect());
                for (int i = 0; i < origin.size(); i++) {
                    if (StringUtils.isNotEmpty(answer.get(i)) && StringUtils.equals(origin.get(i), answer.get(i))) {
                        rightCount++;
                    }
                }
                questionAnswerEntity.setCustomerScore(question.getScore() * rightCount / origin.size());
                questionAnswerEntity.setDoRight(rightCount == origin.size());
                break;
            // 简答：得分为答案相似度 * 总分
            case ShortAnswer:
                questionAnswerEntity.setAnswer(questionAnswer.getContent());
                double similarity = TextUtils.computeTFIDF(questionAnswer.getContent(), question.getCorrect());
                questionAnswerEntity.setCustomerScore((int) (similarity * question.getScore()));
                questionAnswerEntity.setDoRight(similarity >= 0.8);
                break;
            default:
                questionAnswerEntity.setAnswer(questionAnswer.getContent());
                questionAnswerEntity.setCustomerScore(0);
                questionAnswerEntity.setDoRight(false);
                break;
        }
    }

}