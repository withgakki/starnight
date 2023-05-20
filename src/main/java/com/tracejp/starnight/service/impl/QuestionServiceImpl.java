package com.tracejp.starnight.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.tracejp.starnight.dao.QuestionDao;
import com.tracejp.starnight.entity.QuestionEntity;
import com.tracejp.starnight.service.QuestionService;

/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
@Service("questionService")
public class QuestionServiceImpl extends ServiceImpl<QuestionDao, QuestionEntity> implements QuestionService {

}