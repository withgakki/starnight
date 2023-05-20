package com.tracejp.starnight.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.tracejp.starnight.dao.ExamPaperAnswerDao;
import com.tracejp.starnight.entity.ExamPaperAnswerEntity;
import com.tracejp.starnight.service.ExamPaperAnswerService;

/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
@Service("examPaperAnswerService")
public class ExamPaperAnswerServiceImpl extends ServiceImpl<ExamPaperAnswerDao, ExamPaperAnswerEntity> implements ExamPaperAnswerService {

}