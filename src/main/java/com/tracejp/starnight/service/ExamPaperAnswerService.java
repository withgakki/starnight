package com.tracejp.starnight.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tracejp.starnight.entity.ExamPaperAnswerEntity;

import java.util.List;

/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
public interface ExamPaperAnswerService extends IService<ExamPaperAnswerEntity> {

    /**
     * 列表
     */
    List<ExamPaperAnswerEntity> listPage(ExamPaperAnswerEntity examPaperAnswer);

}

