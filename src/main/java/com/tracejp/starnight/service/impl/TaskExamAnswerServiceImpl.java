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
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
    public void saveOrUpdateByPaperAnswer(ExamPaperEntity examPaper, ExamPaperAnswerEntity examPaperAnswerEntity) {
        // 构造试卷任务项
        TaskItemAnswerPo taskItemAnswerPo = new TaskItemAnswerPo();
        taskItemAnswerPo.setExamPaperId(examPaper.getId());
        taskItemAnswerPo.setExamPaperAnswerId(examPaperAnswerEntity.getId());
        taskItemAnswerPo.setStatus(examPaperAnswerEntity.getStatus());

        LambdaQueryWrapper<TaskExamAnswerEntity> wrapper = Wrappers.lambdaQuery(TaskExamAnswerEntity.class)
                .eq(TaskExamAnswerEntity::getCreateBy, examPaperAnswerEntity.getCreateBy())
                .eq(TaskExamAnswerEntity::getTaskExamId, examPaper.getTaskExamId());
        TaskExamAnswerEntity taskExamAnswer = getOne(wrapper);
        if (taskExamAnswer == null) {  // 保存
            // 保存任务完成情况
            List<TaskItemAnswerPo> taskItemAnswerPos = Collections.singletonList(taskItemAnswerPo);
            TextContentEntity textContentEntity = new TextContentEntity();
            textContentEntity.setContent(taskItemAnswerPos);
            textContentService.save(textContentEntity);
            // 保存任务实体
            TaskExamAnswerEntity taskExamAnswerEntity = new TaskExamAnswerEntity();
            taskExamAnswerEntity.setCreateBy(examPaperAnswerEntity.getCreateBy());
            taskExamAnswerEntity.setTaskExamId(examPaper.getTaskExamId());
            taskExamAnswerEntity.setTextContentId(textContentEntity.getId());
            save(taskExamAnswerEntity);
        } else {  // 修改
            TextContentEntity textContent = textContentService.getById(taskExamAnswer.getTextContentId());
            List<TaskItemAnswerPo> taskItemAnswerPos = textContent.getContentArray(TaskItemAnswerPo.class);
            taskItemAnswerPos.add(taskItemAnswerPo);
            textContent.setContent(taskItemAnswerPos);
            textContentService.updateById(textContent);
        }
    }

    @Override
    public void removeByAnswers(List<ExamPaperAnswerEntity> paperListByTask) {
        for(ExamPaperAnswerEntity item : paperListByTask) {
            LambdaQueryWrapper<TaskExamAnswerEntity> wrapper = Wrappers.lambdaQuery(TaskExamAnswerEntity.class)
                    .eq(TaskExamAnswerEntity::getTaskExamId, item.getTaskExamId());
            List<TaskExamAnswerEntity> taskExamAnswerEntities = list(wrapper);

            // 重新保存任务
            for(TaskExamAnswerEntity taskExamAnswerEntity : taskExamAnswerEntities) {
                TextContentEntity textContentEntity = textContentService.getById(taskExamAnswerEntity.getTextContentId());
                List<TaskItemAnswerPo> taskItemAnswerPos = textContentEntity.getContentArray(TaskItemAnswerPo.class);
                taskItemAnswerPos.removeIf(po -> Objects.equals(po.getExamPaperAnswerId(), item.getId()));
                if (taskItemAnswerPos.isEmpty()) {
                    textContentService.removeById(textContentEntity.getId());
                    removeById(taskExamAnswerEntity.getId());
                } else {
                    textContentEntity.setContent(taskItemAnswerPos);
                    textContentService.updateById(textContentEntity);
                }
            }
        }
    }

    @Override
    public boolean removeByTaskIds(List<Long> taskIds) {
        LambdaQueryWrapper<TaskExamAnswerEntity> wrapper = Wrappers.lambdaQuery(TaskExamAnswerEntity.class)
                .in(TaskExamAnswerEntity::getTaskExamId, taskIds);
        return remove(wrapper);
    }

}