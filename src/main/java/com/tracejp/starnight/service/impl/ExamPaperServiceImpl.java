package com.tracejp.starnight.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tracejp.starnight.dao.ExamPaperDao;
import com.tracejp.starnight.entity.ExamPaperEntity;
import com.tracejp.starnight.entity.QuestionEntity;
import com.tracejp.starnight.entity.TextContentEntity;
import com.tracejp.starnight.entity.enums.ExamPaperTypeEnum;
import com.tracejp.starnight.entity.po.ExamPaperQuestionItemPo;
import com.tracejp.starnight.entity.po.ExamPaperTitleItemPo;
import com.tracejp.starnight.entity.vo.ExamPaperTitleItemVo;
import com.tracejp.starnight.entity.vo.ExamPaperVo;
import com.tracejp.starnight.entity.vo.QuestionVo;
import com.tracejp.starnight.exception.ServiceException;
import com.tracejp.starnight.service.ExamPaperService;
import com.tracejp.starnight.service.QuestionService;
import com.tracejp.starnight.service.SubjectService;
import com.tracejp.starnight.service.TextContentService;
import com.tracejp.starnight.utils.BeanUtils;
import com.tracejp.starnight.utils.ScoreUtils;
import com.tracejp.starnight.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
@Service("examPaperService")
public class ExamPaperServiceImpl extends ServiceImpl<ExamPaperDao, ExamPaperEntity> implements ExamPaperService {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private TextContentService textContentService;

    @Autowired
    private ExamPaperDao examPaperDao;


    @Override
    public List<ExamPaperEntity> listPage(ExamPaperEntity examPaper) {
        return examPaperDao.listPage(examPaper);
    }

    @Override
    public List<ExamPaperEntity> listTaskExamPaperPage(ExamPaperEntity examPaper) {
        examPaper.setPaperType(ExamPaperTypeEnum.TASK.getCode());
        return examPaperDao.listTaskExamPaperPage(examPaper);
    }

    @Override
    public List<ExamPaperEntity> listStudentIndexPage(Integer level, Integer type) {
        LambdaQueryWrapper<ExamPaperEntity> wrapper = new LambdaQueryWrapper<>(ExamPaperEntity.class)
                .eq(ExamPaperEntity::getGradeLevel, level)
                .eq(ExamPaperEntity::getPaperType, type)
                .orderByAsc(ExamPaperEntity::getId)
                .last("limit 5");
        return list(wrapper);
    }

    @Override
    public ExamPaperVo getExamPaperVo(Long id) {
        ExamPaperEntity examPaperEntity = getById(id);
        if (examPaperEntity == null) {
            throw new ServiceException("试卷不存在");
        }

        // 封装基本信息
        ExamPaperVo examPaperVo = new ExamPaperVo();
        BeanUtils.copyProperties(examPaperEntity, examPaperVo);
        examPaperVo.setScore(ScoreUtils.scoreToVM(examPaperEntity.getScore()));

        // 查出标题集合
        TextContentEntity frameTextContent = textContentService.getById(examPaperEntity.getFrameTextContentId());
        List<ExamPaperTitleItemPo> titleItemPos = frameTextContent.getContentArray(ExamPaperTitleItemPo.class);

        // 查出 question 集合
        Map<Long, QuestionEntity> questionMap = new HashMap<>();
        List<Long> questionIds = titleItemPos.stream()
                .flatMap(t -> t.getQuestionItems().stream().map(ExamPaperQuestionItemPo::getId))
                .collect(Collectors.toList());
        List<QuestionEntity> questions = questionService.listByIds(questionIds);
        if (!CollectionUtils.isEmpty(questions)) {
            questionMap = questions.stream().collect(Collectors.toMap(QuestionEntity::getId, q -> q));
        }

        // 封装 title vo
        final Map<Long, QuestionEntity> finalQuestionMap = questionMap;
        List<ExamPaperTitleItemVo> examPaperTitleItemVos = titleItemPos.stream().map(title -> {
            ExamPaperTitleItemVo titleItemVo = new ExamPaperTitleItemVo();
            BeanUtils.copyProperties(title, titleItemVo);
            // 封装 question vo
            List<QuestionVo> questionItemsVM = title.getQuestionItems().parallelStream().map(item -> {
                QuestionEntity question = finalQuestionMap.get(item.getId());
                QuestionVo questionVo = new QuestionVo();
                if (question != null) {
                    questionVo = questionService.getQuestionVo(question);
                    questionVo.setItemOrder(item.getItemOrder());
                    return questionVo;
                }
                return questionVo;
            }).collect(Collectors.toList());
            titleItemVo.setQuestionItems(questionItemsVM);
            return titleItemVo;
        }).collect(Collectors.toList());
        examPaperVo.setTitleItems(examPaperTitleItemVos);

        return examPaperVo;
    }

    @Transactional
    @Override
    public void saveExamPaperVo(ExamPaperVo examPaper, Long userId) {
        // 保存试卷标题、题目等内容
        TextContentEntity examPaperContent = buildTextContentByExamPaperTitleItemVo(examPaper.getTitleItems());
        if (StringUtils.isEmpty(examPaperContent.getContent())) {
            throw new ServiceException("试卷内容不能为空");
        }
        textContentService.save(examPaperContent);

        // 保存试卷基本信息
        ExamPaperEntity examPaperEntity = new ExamPaperEntity();
        setPropertiesFromVo(examPaper, examPaperEntity);
        examPaperEntity.setCreateBy(userId);
        examPaperEntity.setFrameTextContentId(examPaperContent.getId());
        save(examPaperEntity);
    }

    @Transactional
    @Override
    public void updateExamPaperVo(ExamPaperVo examPaper) {
        ExamPaperEntity examPaperLast = getById(examPaper.getId());
        if (examPaperLast == null) {
            throw new ServiceException("试卷不存在");
        }

        // 更新试卷基本信息
        setPropertiesFromVo(examPaper, examPaperLast);
        updateById(examPaperLast);

        // 更新试卷内容
        TextContentEntity textContentEntity = buildTextContentByExamPaperTitleItemVo(examPaper.getTitleItems());
        textContentEntity.setId(examPaperLast.getFrameTextContentId());
        textContentService.updateById(textContentEntity);
    }

    @Override
    public void updateTaskPaperRelation(Long taskId, List<ExamPaperEntity> examPaperItems) {
        if (!CollectionUtils.isEmpty(examPaperItems)) {
            List<Long> paperIds = examPaperItems.stream()
                    .map(ExamPaperEntity::getId)
                    .collect(Collectors.toList());
            examPaperDao.updateTaskByIds(taskId, paperIds);
        }
    }

    @Override
    public boolean setTaskIdNullByIds(List<Long> ids) {
        LambdaUpdateWrapper<ExamPaperEntity> wrapper = Wrappers.lambdaUpdate(ExamPaperEntity.class)
                .set(ExamPaperEntity::getTaskExamId, null)
                .in(ExamPaperEntity::getId, ids);
        return update(wrapper);
    }

    /**
     * 设置vo公共属性 ===> entity
     */
    private void setPropertiesFromVo(ExamPaperVo vo, ExamPaperEntity entity) {
        BeanUtils.copyProperties(vo, entity);
        Integer gradeLevel = subjectService.getLevelById(vo.getSubjectId());
        entity.setGradeLevel(gradeLevel);

        // 计算总分、题目数
        List<ExamPaperTitleItemVo> titleItems = vo.getTitleItems();
        Integer questionCount = titleItems.stream()
                .mapToInt(t -> t.getQuestionItems().size()).sum();
        Integer score = titleItems.stream().
                flatMapToInt(t -> t.getQuestionItems().stream()
                        .mapToInt(q -> ScoreUtils.scoreFromVM(q.getScore()))
                ).sum();
        entity.setQuestionCount(questionCount);
        entity.setScore(score);

        // 非时段试卷
        if (ExamPaperTypeEnum.TIME_LIMIT != ExamPaperTypeEnum.fromCode(vo.getPaperType())) {
            entity.setLimitStartTime(null);
            entity.setLimitEndTime(null);
        }
    }

    /**
     * 构建 ExamPaperTitleItemVos ===> TextContentEntity
     */
    private TextContentEntity buildTextContentByExamPaperTitleItemVo(List<ExamPaperTitleItemVo> titleItemVos) {
        AtomicInteger index = new AtomicInteger(1);
        TextContentEntity textContentEntity = new TextContentEntity();
        // 构建 examPaper
        if (!CollectionUtils.isEmpty(titleItemVos)) {
            List<ExamPaperTitleItemPo> examPaperPos = titleItemVos.stream().map(title -> {
                ExamPaperTitleItemPo examPaperTitleItemPo = new ExamPaperTitleItemPo();
                BeanUtils.copyProperties(title, examPaperTitleItemPo);
                // 构建 question
                List<QuestionVo> questionItems = title.getQuestionItems();
                if (!CollectionUtils.isEmpty(questionItems)) {
                    List<ExamPaperQuestionItemPo> questionPos = questionItems.stream().map(question -> {
                        ExamPaperQuestionItemPo examPaperQuestionItemPo = new ExamPaperQuestionItemPo();
                        BeanUtils.copyProperties(question, examPaperQuestionItemPo);
                        examPaperQuestionItemPo.setItemOrder(index.getAndIncrement());
                        return examPaperQuestionItemPo;
                    }).collect(Collectors.toList());
                    examPaperTitleItemPo.setQuestionItems(questionPos);
                }
                return examPaperTitleItemPo;
            }).collect(Collectors.toList());
            textContentEntity.setContent(examPaperPos);
        }
        return textContentEntity;
    }

}