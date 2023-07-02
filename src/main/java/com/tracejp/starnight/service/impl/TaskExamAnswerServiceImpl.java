package com.tracejp.starnight.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tracejp.starnight.dao.TaskExamAnswerDao;
import com.tracejp.starnight.entity.ExamPaperAnswerEntity;
import com.tracejp.starnight.entity.ExamPaperEntity;
import com.tracejp.starnight.entity.TaskExamAnswerEntity;
import com.tracejp.starnight.entity.TextContentEntity;
import com.tracejp.starnight.entity.po.TaskItemAnswerPo;
import com.tracejp.starnight.service.TaskExamAnswerService;
import com.tracejp.starnight.service.TextContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
@Service("taskExamAnswerService")
public class TaskExamAnswerServiceImpl extends ServiceImpl<TaskExamAnswerDao, TaskExamAnswerEntity> implements TaskExamAnswerService {

    @Autowired
    private TextContentService textContentService;

    @Override
    public List<TaskExamAnswerEntity> listByUserId(Long userId) {
        LambdaQueryWrapper<TaskExamAnswerEntity> wrapper = Wrappers.lambdaQuery(TaskExamAnswerEntity.class)
                .eq(TaskExamAnswerEntity::getCreateBy, userId);
        return list(wrapper);
    }

    @Override
    public TaskExamAnswerEntity listByUserIdTaskId(Long userId, Long taskId) {
        LambdaQueryWrapper<TaskExamAnswerEntity> wrapper = Wrappers.lambdaQuery(TaskExamAnswerEntity.class)
                .eq(TaskExamAnswerEntity::getCreateBy, userId)
                .eq(TaskExamAnswerEntity::getTaskExamId, taskId);
        return getOne(wrapper);
    }

    @Override
    public List<TaskExamAnswerEntity> listByUserIdTaskId(Long userId, List<Long> taskIds) {
        LambdaQueryWrapper<TaskExamAnswerEntity> wrapper = Wrappers.lambdaQuery(TaskExamAnswerEntity.class)
                .eq(TaskExamAnswerEntity::getCreateBy, userId)
                .in(TaskExamAnswerEntity::getTaskExamId, taskIds);
        return list(wrapper);
    }

    @Transactional
    @Override
    public void saveByPaperAnswer(ExamPaperEntity examPaper, ExamPaperAnswerEntity examPaperAnswerEntity) {
        // 保存任务完成情况
        TaskItemAnswerPo taskItemAnswerPo = new TaskItemAnswerPo();
        taskItemAnswerPo.setExamPaperId(examPaper.getId());
        taskItemAnswerPo.setExamPaperAnswerId(examPaperAnswerEntity.getId());
        taskItemAnswerPo.setStatus(examPaperAnswerEntity.getStatus());
        List<TaskItemAnswerPo> taskItemAnswerPos = Collections.singletonList(taskItemAnswerPo);
        TextContentEntity textContentEntity = new TextContentEntity();
        textContentEntity.setContent(taskItemAnswerPos);
        textContentService.save(textContentEntity);

        // 保存任务实体
        TaskExamAnswerEntity taskExamAnswerEntity = new TaskExamAnswerEntity();
        taskExamAnswerEntity.setCreateBy(examPaper.getCreateBy());
        taskExamAnswerEntity.setTaskExamId(examPaper.getTaskExamId());
        taskExamAnswerEntity.setTextContentId(textContentEntity.getId());
        save(taskExamAnswerEntity);
    }

}