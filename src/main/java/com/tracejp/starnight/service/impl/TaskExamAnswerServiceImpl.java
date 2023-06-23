package com.tracejp.starnight.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.tracejp.starnight.dao.TaskExamAnswerDao;
import com.tracejp.starnight.entity.TaskExamAnswerEntity;
import com.tracejp.starnight.service.TaskExamAnswerService;

import java.util.List;

/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
@Service("taskExamAnswerService")
public class TaskExamAnswerServiceImpl extends ServiceImpl<TaskExamAnswerDao, TaskExamAnswerEntity> implements TaskExamAnswerService {

    @Override
    public List<TaskExamAnswerEntity> listByUserId(Long userId) {
        LambdaQueryWrapper<TaskExamAnswerEntity> wrapper = Wrappers.lambdaQuery(TaskExamAnswerEntity.class)
                .eq(TaskExamAnswerEntity::getCreateBy, userId);
        return list(wrapper);
    }

    @Override
    public List<TaskExamAnswerEntity> listByUserIdTaskIds(Long userId, List<Long> taskIds) {
        LambdaQueryWrapper<TaskExamAnswerEntity> wrapper = Wrappers.lambdaQuery(TaskExamAnswerEntity.class)
                .eq(TaskExamAnswerEntity::getCreateBy, userId)
                .in(TaskExamAnswerEntity::getTaskExamId, taskIds);
        return list(wrapper);
    }

}