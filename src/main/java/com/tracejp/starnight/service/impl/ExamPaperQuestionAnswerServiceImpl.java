package com.tracejp.starnight.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tracejp.starnight.dao.ExamPaperQuestionAnswerDao;
import com.tracejp.starnight.entity.ExamPaperQuestionAnswerEntity;
import com.tracejp.starnight.entity.TextContentEntity;
import com.tracejp.starnight.entity.enums.QuestionTypeEnum;
import com.tracejp.starnight.entity.vo.ExamPaperAnswerSubmitItemVo;
import com.tracejp.starnight.service.ExamPaperQuestionAnswerService;
import com.tracejp.starnight.service.TextContentService;
import com.tracejp.starnight.utils.ArrayStringUtils;
import com.tracejp.starnight.utils.ScoreUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
@Service("examPaperQuestionAnswerService")
public class ExamPaperQuestionAnswerServiceImpl extends ServiceImpl<ExamPaperQuestionAnswerDao, ExamPaperQuestionAnswerEntity> implements ExamPaperQuestionAnswerService {

    @Autowired
    private TextContentService textContentService;

    @Override
    public List<ExamPaperQuestionAnswerEntity> listByAnswerId(Long answerId) {
        LambdaQueryWrapper<ExamPaperQuestionAnswerEntity> wrapper = Wrappers.lambdaQuery(ExamPaperQuestionAnswerEntity.class)
                .eq(ExamPaperQuestionAnswerEntity::getExamPaperAnswerId, answerId);
        return list(wrapper);
    }

    @Override
    public ExamPaperAnswerSubmitItemVo buildAnswerSubmitItemVo(ExamPaperQuestionAnswerEntity questionAnswerEntity) {
        ExamPaperAnswerSubmitItemVo vo = new ExamPaperAnswerSubmitItemVo();
        vo.setId(questionAnswerEntity.getId());
        vo.setQuestionId(questionAnswerEntity.getQuestionId());
        vo.setDoRight(questionAnswerEntity.getDoRight());
        vo.setScore(ScoreUtils.scoreToVM(questionAnswerEntity.getCustomerScore()));
        vo.setQuestionScore(ScoreUtils.scoreToVM(questionAnswerEntity.getQuestionScore()));
        vo.setItemOrder(questionAnswerEntity.getItemOrder());
        setAnswerSubmitItemVoProperties(vo, questionAnswerEntity);
        return vo;
    }

    private void setAnswerSubmitItemVoProperties(ExamPaperAnswerSubmitItemVo vo,
                                                 ExamPaperQuestionAnswerEntity questionAnswerEntity) {
        if (QuestionTypeEnum.needSaveTextContent(questionAnswerEntity.getQuestionType())) {
            TextContentEntity textContent = textContentService.getById(questionAnswerEntity.getTextContentId());
            String content = textContent.getContent(String.class);
            QuestionTypeEnum questionTypeEnum = QuestionTypeEnum.fromCode(questionAnswerEntity.getQuestionType());
            if (questionTypeEnum == QuestionTypeEnum.GapFilling) {
                vo.setContentArray(ArrayStringUtils.contentToArray(content));
            } else {  // 简答
                vo.setContent(content);
            }
        } else {
            vo.setContent(questionAnswerEntity.getAnswer());
        }
    }

}