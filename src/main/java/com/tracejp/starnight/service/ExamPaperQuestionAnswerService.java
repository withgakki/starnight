package com.tracejp.starnight.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tracejp.starnight.entity.ExamPaperQuestionAnswerEntity;
import com.tracejp.starnight.entity.vo.ExamPaperAnswerSubmitItemVo;

import java.util.List;

/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
public interface ExamPaperQuestionAnswerService extends IService<ExamPaperQuestionAnswerEntity> {

    /**
     * 通过答卷id 查询问题集合
     */
    List<ExamPaperQuestionAnswerEntity> listByAnswerId(Long answerId);

    /**
     * 通过 entity 构造 ExamPaperAnswerSubmitItemVo
     */
    ExamPaperAnswerSubmitItemVo buildAnswerSubmitItemVo(ExamPaperQuestionAnswerEntity questionAnswerEntity);

}

