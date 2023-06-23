package com.tracejp.starnight.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tracejp.starnight.entity.TaskExamAnswerEntity;

import java.util.List;

/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
public interface TaskExamAnswerService extends IService<TaskExamAnswerEntity> {

    /**
     * 通过用户 id 获取任务考试答案列表
     */
    List<TaskExamAnswerEntity> listByUserId(Long userId);

    /**
     * 通过用户id、任务ids 获取任务考试答案列表
     */
    List<TaskExamAnswerEntity> listByUserIdTaskIds(Long userId, List<Long> taskIds);

}

