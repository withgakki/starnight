package com.tracejp.starnight.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.tracejp.starnight.dao.TaskExamDao;
import com.tracejp.starnight.entity.TaskExamEntity;
import com.tracejp.starnight.service.TaskExamService;

/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
@Service("taskExamService")
public class TaskExamServiceImpl extends ServiceImpl<TaskExamDao, TaskExamEntity> implements TaskExamService {

}