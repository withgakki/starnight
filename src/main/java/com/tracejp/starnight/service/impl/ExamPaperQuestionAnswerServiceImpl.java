package com.tracejp.starnight.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.tracejp.starnight.dao.ExamPaperQuestionAnswerDao;
import com.tracejp.starnight.entity.ExamPaperQuestionAnswerEntity;
import com.tracejp.starnight.service.ExamPaperQuestionAnswerService;

/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
@Service("examPaperQuestionAnswerService")
public class ExamPaperQuestionAnswerServiceImpl extends ServiceImpl<ExamPaperQuestionAnswerDao, ExamPaperQuestionAnswerEntity> implements ExamPaperQuestionAnswerService {

}