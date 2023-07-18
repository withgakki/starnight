package com.tracejp.starnight.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tracejp.starnight.constants.CacheConstants;
import com.tracejp.starnight.dao.QuestionDao;
import com.tracejp.starnight.entity.QuestionEntity;
import com.tracejp.starnight.entity.TextContentEntity;
import com.tracejp.starnight.entity.enums.QuestionStatusEnum;
import com.tracejp.starnight.entity.enums.QuestionTypeEnum;
import com.tracejp.starnight.entity.po.QuestionItemPo;
import com.tracejp.starnight.entity.po.QuestionPo;
import com.tracejp.starnight.entity.vo.QuestionItemVo;
import com.tracejp.starnight.entity.vo.QuestionVo;
import com.tracejp.starnight.exception.ServiceException;
import com.tracejp.starnight.handler.gpt.IGPTHandler;
import com.tracejp.starnight.service.QuestionService;
import com.tracejp.starnight.service.SubjectService;
import com.tracejp.starnight.service.TextContentService;
import com.tracejp.starnight.utils.ArrayStringUtils;
import com.tracejp.starnight.utils.BeanUtils;
import com.tracejp.starnight.utils.HtmlUtils;
import com.tracejp.starnight.utils.ScoreUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
@Service("questionService")
public class QuestionServiceImpl extends ServiceImpl<QuestionDao, QuestionEntity> implements QuestionService {

    @Autowired
    private TextContentService textContentService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private IGPTHandler gptHandler;

    @Override
    public List<QuestionEntity> listPage(QuestionEntity question) {
        return questionDao.listPage(question);
    }

    @Override
    public QuestionVo getQuestionVo(Long id) {
        return getQuestionVo(getById(id));
    }

    @Override
    public QuestionVo getQuestionVo(QuestionEntity question) {
        if (question == null) {
            throw new ServiceException("题目不存在");
        }

        // 基本信息处理
        QuestionVo questionVo = new QuestionVo();
        BeanUtils.copyProperties(question, questionVo);

        // 题目处理
        TextContentEntity questionInfo = textContentService.getById(question.getInfoTextContentId());
        QuestionPo questionPo = questionInfo.getContent(QuestionPo.class);
        if (questionPo == null) {
            throw new ServiceException("题目信息解析失败");
        }
        questionVo.setTitle(questionPo.getTitleContent());

        // 题目选项处理
        List<QuestionItemPo> questionItemPos = questionPo.getQuestionItemPos();
        if (!CollectionUtils.isEmpty(questionItemPos)) {
            List<QuestionItemVo> questionItemVos = questionItemPos.stream().map(item -> {
                QuestionItemVo questionItemVo = new QuestionItemVo();
                BeanUtils.copyProperties(item, questionItemVo);
                return questionItemVo;
            }).collect(Collectors.toList());
            questionVo.setItems(questionItemVos);
        }

        /*
         * 正确答案处理
         * 单选、判断、简答题：返回字符串答案
         * 多选、填空：返回字符串数组答案
         */
        QuestionTypeEnum questionTypeEnum = QuestionTypeEnum.fromCode(question.getQuestionType());
        switch (questionTypeEnum) {
            case SingleChoice:
            case TrueFalse:
            case ShortAnswer:
                questionVo.setCorrect(question.getCorrect());
                break;
            case MultipleChoice:
                questionVo.setCorrectArray(ArrayStringUtils.contentToArray(question.getCorrect()));
                break;
            case GapFilling:
                List<String> correctContent = questionItemPos.stream()
                        .map(QuestionItemPo::getContent)
                        .collect(Collectors.toList());
                questionVo.setCorrectArray(correctContent);
                break;
            default:
                throw new ServiceException("题目信息解析失败");
        }

        // 其他信息处理
        questionVo.setScore(ScoreUtils.scoreToVM(question.getScore()));
        questionVo.setAnalyze(questionPo.getAnalyze());

        return questionVo;
    }

    @Override
    public List<QuestionVo> getQuestionVo(List<Long> ids) {
        List<QuestionEntity> questionEntities = listByIds(ids);
        if (CollectionUtils.isEmpty(questionEntities)) {
            return new ArrayList<>();
        }
        return questionEntities.stream().map(this::getQuestionVo).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void saveQuestionVo(QuestionVo question, Long userId) {
        // 保存题目内容
        TextContentEntity textContentEntity = buildTextContentByQuestionVo(question);
        textContentService.save(textContentEntity);

        // 保存题目
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setCreateBy(userId);
        questionEntity.setSubjectId(question.getSubjectId());
        Integer gradeLevel = subjectService.getLevelById(question.getSubjectId());
        questionEntity.setGradeLevel(gradeLevel);
        questionEntity.setQuestionType(question.getQuestionType());
        questionEntity.setStatus(QuestionStatusEnum.OK.getCode());
        questionEntity.setCorrectFromVo(question.getCorrect(), question.getCorrectArray());
        questionEntity.setScore(ScoreUtils.scoreFromVM(question.getScore()));
        questionEntity.setDifficult(question.getDifficult());
        questionEntity.setInfoTextContentId(textContentEntity.getId());
        save(questionEntity);
    }

    @Transactional
    @Override
    public void updateQuestionVo(QuestionVo question) {
        // 更新 entity
        QuestionEntity questionLast = getById(question.getId());
        if (questionLast == null) {
            throw new ServiceException("题目不存在");
        }
        questionLast.setSubjectId(question.getSubjectId());
        Integer gradeLevel = subjectService.getLevelById(question.getSubjectId());
        questionLast.setGradeLevel(gradeLevel);
        questionLast.setScore(ScoreUtils.scoreFromVM(question.getScore()));
        questionLast.setDifficult(question.getDifficult());
        questionLast.setCorrectFromVo(question.getCorrect(), question.getCorrectArray());
        updateById(questionLast);

        // 更新 题干、解析、选项等
        TextContentEntity textContentEntity = buildTextContentByQuestionVo(question);
        textContentEntity.setId(questionLast.getInfoTextContentId());
        textContentService.updateById(textContentEntity);
    }

    @Override
    @Cacheable(CacheConstants.QUESTION_ANALYZE_CACHE_KEY)
    public String gptQuestionAnalyze(Long id) {
        QuestionEntity question = getById(id);
        if (question == null) {
            throw new ServiceException("题目不存在");
        }
        TextContentEntity textContent = textContentService.getById(question.getInfoTextContentId());
        QuestionPo questionPo = textContent.getContent(QuestionPo.class);
        String prompts = buildGptPromptByQuestionAnalyze(question, questionPo);
        String result = gptHandler.chat(prompts);
        return result.replaceAll("\n", "<br>");
    }

    /**
     * 构建 题目解析 prompt 提问字符串
     */
    private String buildGptPromptByQuestionAnalyze(QuestionEntity question, QuestionPo questionPo) {
        List<QuestionItemPo> questionItemPos = questionPo.getQuestionItemPos();
        QuestionTypeEnum questionTypeEnum = QuestionTypeEnum.fromCode(question.getQuestionType());
        StringBuilder questionItemsStr = new StringBuilder();
        StringBuilder answerStr = new StringBuilder();
        String title = questionPo.getTitleContent();
        String typeName = questionTypeEnum.getName();
        switch (questionTypeEnum) {
            case SingleChoice:
            case MultipleChoice:
            case TrueFalse:
                for (QuestionItemPo po : questionItemPos) {
                    questionItemsStr.append(po.getPrefix()).append("、")
                            .append(HtmlUtils.clear(po.getContent())).append(";");
                }
                answerStr.append(question.getCorrect());
                title = HtmlUtils.clear(title);
                break;
            case GapFilling:
                questionItemsStr.append("填空题选项包含在题目中，使用span标签且有gapfilling-span样式的标签包围");
                for (QuestionItemPo po : questionItemPos) {
                    answerStr.append(po.getPrefix()).append("、")
                            .append(HtmlUtils.clear(po.getContent())).append(";");
                }
                break;
            case ShortAnswer:
                answerStr.append(HtmlUtils.clear(question.getCorrect()));
                break;
            default:
                throw new ServiceException("未知的题型");
        }

        return "请解析该题的解答，并分点简要给出的解析过程，不超过5点，每点不超过100字：\n" +
                "题目：" + title + "\n" +
                "题型：" + typeName + "\n" +
                "选项：" + questionItemsStr + "\n" +
                "参考答案：" + answerStr + "\n";
    }

    /**
     * 转换 QuestionVo 为 TextContent
     */
    private TextContentEntity buildTextContentByQuestionVo(QuestionVo question) {
        // 题目选项处理
        List<QuestionItemVo> questionItems = question.getItems();
        List<QuestionItemPo> itemPos = null;
        if (!CollectionUtils.isEmpty(questionItems)) {
            itemPos = questionItems.parallelStream().map(item -> {  // 映射为Po
                QuestionItemPo questionItemPo = new QuestionItemPo();
                BeanUtils.copyProperties(item, questionItemPo);
                return questionItemPo;
            }).collect(Collectors.toList());
        }

        // 题目处理
        QuestionPo questionPo = new QuestionPo();
        questionPo.setQuestionItemPos(itemPos);
        questionPo.setAnalyze(question.getAnalyze());
        questionPo.setTitleContent(question.getTitle());
        questionPo.setCorrect(question.getCorrect());

        // 包装为 TextContent
        TextContentEntity textContentEntity = new TextContentEntity();
        textContentEntity.setContent(questionPo);
        return textContentEntity;
    }

}