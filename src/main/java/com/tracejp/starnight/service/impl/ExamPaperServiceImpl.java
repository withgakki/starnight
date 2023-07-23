package com.tracejp.starnight.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tracejp.starnight.constants.ElasticSearchConstants;
import com.tracejp.starnight.dao.ExamPaperAnswerDao;
import com.tracejp.starnight.dao.ExamPaperDao;
import com.tracejp.starnight.entity.*;
import com.tracejp.starnight.entity.dto.SearchExamPaperDto;
import com.tracejp.starnight.entity.enums.ExamPaperTypeEnum;
import com.tracejp.starnight.entity.enums.QuestionStatusEnum;
import com.tracejp.starnight.entity.enums.QuestionTypeEnum;
import com.tracejp.starnight.entity.param.RandomExamPaperParams;
import com.tracejp.starnight.entity.param.SearchPageParam;
import com.tracejp.starnight.entity.po.ExamPaperQuestionItemPo;
import com.tracejp.starnight.entity.po.ExamPaperTitleItemPo;
import com.tracejp.starnight.entity.vo.ExamPaperTitleItemVo;
import com.tracejp.starnight.entity.vo.ExamPaperVo;
import com.tracejp.starnight.entity.vo.QuestionVo;
import com.tracejp.starnight.entity.vo.student.ExamPaperSearchVo;
import com.tracejp.starnight.exception.ServiceException;
import com.tracejp.starnight.service.*;
import com.tracejp.starnight.utils.BeanUtils;
import com.tracejp.starnight.utils.ElasticSearchUtils;
import com.tracejp.starnight.utils.ScoreUtils;
import com.tracejp.starnight.utils.StringUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
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

    @Autowired
    private ExamPaperAnswerDao examPaperAnswerDao;

    @Autowired
    private ElasticSearchUtils esUtils;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

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
    public ExamPaperSearchVo searchDtoByKeyword(SearchPageParam searchParam) {
        SearchSourceBuilder query = new SearchSourceBuilder();

        final String NAME = "name";

        // 匹配
        query.query(QueryBuilders.matchQuery(NAME, searchParam.getKeyword()));

        // 分页
        query.from((searchParam.getPageNum() - 1) * searchParam.getPageSize());
        query.size(searchParam.getPageSize());

        // 高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder()
                .field(NAME)
                .preTags("<span style='background-color: #f1a532'>")
                .postTags("</span>");
        query.highlighter(highlightBuilder);

        // 封装结果
        ExamPaperSearchVo vo = new ExamPaperSearchVo();
        SearchHits searchHits = esUtils.pageDocument(ElasticSearchConstants.EXAMPAPER_INDEX, query);
        vo.setTotal(searchHits.getTotalHits().value);
        SearchHit[] hits = searchHits.getHits();
        if (hits != null && hits.length > 0) {
            List<SearchExamPaperDto> data = Arrays.stream(hits)
                    .map(hit -> {
                        SearchExamPaperDto dto = JSON.parseObject(hit.getSourceAsString(), SearchExamPaperDto.class);
                        HighlightField highlightName = hit.getHighlightFields().get(NAME);
                        if (highlightName != null) {
                            dto.setName(highlightName.fragments()[0].string());
                        }
                        return dto;
                    })
                    .collect(Collectors.toList());
            vo.setRows(data);
        }

        return vo;
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

        // 修改题目状态
        List<ExamPaperTitleItemVo> titleItems = examPaper.getTitleItems();
        List<QuestionEntity> qEntities = titleItems.stream().flatMap(item -> item.getQuestionItems().stream()
                        .map(q -> {
                            QuestionEntity questionEntity = new QuestionEntity();
                            questionEntity.setId(q.getId());
                            questionEntity.setStatus(QuestionStatusEnum.PUBLISH.getCode());
                            return questionEntity;
                        }))
                .collect(Collectors.toList());
        questionService.updateBatchById(qEntities);

        // 保存试卷基本信息
        ExamPaperEntity examPaperEntity = new ExamPaperEntity();
        setPropertiesFromVo(examPaper, examPaperEntity);
        examPaperEntity.setCreateBy(userId);
        examPaperEntity.setFrameTextContentId(examPaperContent.getId());
        save(examPaperEntity);

        // 保存到 es
        SearchExamPaperDto searchExamPaperDto = new SearchExamPaperDto().convertFrom(examPaperEntity);
        searchExamPaperDto.setCreateTime(new Date());
        esUtils.createDocument(ElasticSearchConstants.EXAMPAPER_INDEX, examPaperEntity.getId(), searchExamPaperDto);
    }

    @Override
    public ExamPaperVo buildRandomExamPaperVo(RandomExamPaperParams randomParams) {
        ExamPaperVo buildVo = new ExamPaperVo();

        SubjectEntity subject = subjectService.getById(randomParams.getSubjectId());

        // 构造试卷结构
        List<ExamPaperTitleItemVo> titleItemVos = new ArrayList<>(3);
        AtomicInteger score = new AtomicInteger(0);
        AtomicInteger questionCount = new AtomicInteger(0);

        // 构建单选
        CompletableFuture<Void> singleChoiceFuture = CompletableFuture.runAsync(() -> {
            List<QuestionVo> vos = questionService.randomExtractQuestionVos(QuestionTypeEnum.SingleChoice.getCode(),
                    randomParams.getSingleChoice(), subject.getId(), randomParams.getDifficult()
            );
            if (!CollectionUtils.isEmpty(vos)) {
                ExamPaperTitleItemVo titleItemVo = buildRandomTitleItemVo(QuestionTypeEnum.SingleChoice, vos, score, questionCount);
                titleItemVos.add(titleItemVo);
            }
        }, threadPoolExecutor);

        // 构建多选
        CompletableFuture<Void> multipleChoiceFuture = CompletableFuture.runAsync(() -> {
            List<QuestionVo> vos = questionService.randomExtractQuestionVos(QuestionTypeEnum.MultipleChoice.getCode(),
                    randomParams.getMultipleChoice(), subject.getId(), randomParams.getDifficult()
            );
            if (!CollectionUtils.isEmpty(vos)) {
                ExamPaperTitleItemVo titleItemVo = buildRandomTitleItemVo(QuestionTypeEnum.MultipleChoice, vos, score, questionCount);
                titleItemVos.add(titleItemVo);
            }
        }, threadPoolExecutor);

        // 构建判断
        CompletableFuture<Void> trueFalseFuture = CompletableFuture.runAsync(() -> {
            List<QuestionVo> vos = questionService.randomExtractQuestionVos(QuestionTypeEnum.TrueFalse.getCode(),
                    randomParams.getJudgeChoice(), subject.getId(), randomParams.getDifficult()
            );
            if (!CollectionUtils.isEmpty(vos)) {
                ExamPaperTitleItemVo titleItemVo = buildRandomTitleItemVo(QuestionTypeEnum.TrueFalse, vos, score, questionCount);
                titleItemVos.add(titleItemVo);
            }
        }, threadPoolExecutor);

        CompletableFuture.allOf(singleChoiceFuture, multipleChoiceFuture, trueFalseFuture).join();

        // 设置试卷基本信息
        buildVo.setScore(ScoreUtils.scoreToVM(score.get()));
        buildVo.setQuestionCount(questionCount.get());
        buildVo.setTitleItems(titleItemVos);
        buildVo.setName(randomParams.getPaperName());
        buildVo.setSubjectId(subject.getId());
        buildVo.setGradeLevel(subject.getLevel());
        buildVo.setPaperType(ExamPaperTypeEnum.FIXED.getCode());
        buildVo.setSuggestTime(randomParams.getSuggestTime());

        return buildVo;
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

        // 更新 es
        SearchExamPaperDto searchExamPaperDto = new SearchExamPaperDto().convertFrom(examPaperLast);
        esUtils.updateDocument(ElasticSearchConstants.EXAMPAPER_INDEX, examPaperLast.getId(), searchExamPaperDto);
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

    @Transactional
    @Override
    public boolean removeToAllByIds(List<Long> ids) {
        LambdaQueryWrapper<ExamPaperEntity> wrapper = Wrappers.lambdaQuery(ExamPaperEntity.class)
                .in(ExamPaperEntity::getId, ids);
        List<ExamPaperEntity> entities = list(wrapper);
        entities.forEach(entity -> {
            if (entity != null && entity.getTaskExamId() != null) {
                throw new ServiceException("试卷已与任务关联，不能删除");
            }
        });

        // 删除 es
        boolean success = esUtils.deleteBatchDocument(ElasticSearchConstants.EXAMPAPER_INDEX, ids);
        if (!success) {
            throw new ServiceException("删除试卷失败");
        }

        // 重置题目状态
        if (!CollectionUtils.isEmpty(entities)) {
            List<Long> textIds = entities.stream().map(ExamPaperEntity::getFrameTextContentId)
                    .collect(Collectors.toList());
            List<TextContentEntity> textEntities = textContentService.listByIds(textIds);
            List<QuestionEntity> questionUpdates = textEntities.stream().flatMap(text -> {
                List<ExamPaperTitleItemPo> contentArray = text.getContentArray(ExamPaperTitleItemPo.class);
                if (CollectionUtils.isEmpty(contentArray)) {
                    return null;
                }
                return contentArray.stream().flatMap(item -> item.getQuestionItems().stream().map(q -> {
                    QuestionEntity question = new QuestionEntity();
                    question.setId(q.getId());
                    question.setStatus(QuestionStatusEnum.OK.getCode());
                    return question;
                }));
            }).collect(Collectors.toList());
            questionService.updateBatchById(questionUpdates);
        }

        // 删答卷
        LambdaQueryWrapper<ExamPaperAnswerEntity> answerWrapper = Wrappers.lambdaQuery(ExamPaperAnswerEntity.class)
                .in(ExamPaperAnswerEntity::getExamPaperId, ids);
        examPaperAnswerDao.delete(answerWrapper);

        return removeByIds(ids);
    }

    /**
     * 构建随机试卷 TitleItemVo & 统计分数、题目数量
     */
    private ExamPaperTitleItemVo buildRandomTitleItemVo(QuestionTypeEnum typeEnum,
                                                        List<QuestionVo> vos,
                                                        AtomicInteger score,
                                                        AtomicInteger questionCount) {
        ExamPaperTitleItemVo titleItemVo = new ExamPaperTitleItemVo();
        titleItemVo.setName(typeEnum.getName());
        titleItemVo.setQuestionItems(vos);
        if (!CollectionUtils.isEmpty(vos)) {
            int sum = vos.stream().mapToInt(item -> ScoreUtils.scoreFromVM(item.getScore())).sum();
            score.addAndGet(sum);
            questionCount.addAndGet(vos.size());
        }
        return titleItemVo;
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