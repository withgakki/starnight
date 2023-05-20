package com.tracejp.starnight.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.tracejp.starnight.dao.TaskExamAnswerDao;
import com.tracejp.starnight.entity.TaskExamAnswerEntity;
import com.tracejp.starnight.service.TaskExamAnswerService;

/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
@Service("taskExamAnswerService")
public class TaskExamAnswerServiceImpl extends ServiceImpl<TaskExamAnswerDao, TaskExamAnswerEntity> implements TaskExamAnswerService {

}