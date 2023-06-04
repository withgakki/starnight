package com.tracejp.starnight.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tracejp.starnight.entity.ExamPaperEntity;

import java.util.List;

/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
public interface ExamPaperService extends IService<ExamPaperEntity> {

    /**
     * 分页
     */
    List<ExamPaperEntity> listPage(ExamPaperEntity examPaper);

}

