package com.tracejp.starnight.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tracejp.starnight.entity.TaskExamEntity;
import com.tracejp.starnight.entity.UserEntity;
import com.tracejp.starnight.entity.vo.TaskExamVo;

import java.util.List;

/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
public interface TaskExamService extends IService<TaskExamEntity> {

    /**
     * 分页
     */
    List<TaskExamEntity> listPage(TaskExamEntity taskExam);

    /**
     * 保存 vo
     */
    void saveTaskExamVo(TaskExamVo taskExam, UserEntity user);

    /**
     * 修改 vo
     */
    void updateTaskExamVo(TaskExamVo taskExam);

    /**
     * 通过 id 获取 vo
     */
    TaskExamVo getTaskExamVo(Long id);

    /**
     * 通过 ids 删除任务
     */
    void removeTaskByIds(List<Long> ids);

}

