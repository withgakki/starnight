package com.tracejp.starnight.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tracejp.starnight.dao.ExamPaperDao;
import com.tracejp.starnight.dao.TaskExamDao;
import com.tracejp.starnight.entity.ExamPaperEntity;
import com.tracejp.starnight.entity.TaskExamEntity;
import com.tracejp.starnight.entity.TextContentEntity;
import com.tracejp.starnight.entity.UserEntity;
import com.tracejp.starnight.entity.po.TaskItemPo;
import com.tracejp.starnight.entity.vo.TaskExamVo;
import com.tracejp.starnight.exception.ServiceException;
import com.tracejp.starnight.service.ExamPaperService;
import com.tracejp.starnight.service.TaskExamService;
import com.tracejp.starnight.service.TextContentService;
import com.tracejp.starnight.utils.BeanUtils;
import com.tracejp.starnight.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
@Service("taskExamService")
public class TaskExamServiceImpl extends ServiceImpl<TaskExamDao, TaskExamEntity> implements TaskExamService {

    @Autowired
    private ExamPaperService examPaperService;

    @Autowired
    private TextContentService textContentService;

    @Autowired
    private TaskExamDao taskExamDao;

    @Autowired
    private ExamPaperDao examPaperDao;


    @Override
    public List<TaskExamEntity> listPage(TaskExamEntity taskExam) {
        return taskExamDao.listPage(taskExam);
    }

    @Transactional
    @Override
    public void saveTaskExamVo(TaskExamVo taskExam, UserEntity user) {
        // 保存任务试卷内容
        List<ExamPaperEntity> examPaperItems = taskExam.getPaperItems();
        TextContentEntity textContent = buildTextContentByExamPaper(examPaperItems);
        textContentService.save(textContent);

        // 保存任务基本信息
        TaskExamEntity taskExamEntity = new TaskExamEntity();
        BeanUtils.copyProperties(taskExam, taskExamEntity);
        taskExamEntity.setCreateBy(user.getId());
        if (StringUtils.isNotEmpty(user.getRealName())) {
            taskExamEntity.setCreateUserName(user.getRealName());
        } else {
            taskExamEntity.setCreateUserName(user.getUserName());
        }
        taskExamEntity.setFrameTextContentId(textContent.getId());
        save(taskExamEntity);

        // 保存任务试卷关联信息
        examPaperService.updateTaskPaperRelation(taskExamEntity.getId(), examPaperItems);
    }

    @Transactional
    @Override
    public void updateTaskExamVo(TaskExamVo taskExam) {
        TaskExamEntity taskExamLast = getById(taskExam.getId());
        if (taskExamLast == null) {
            throw new ServiceException("任务不存在");
        }

        // 重新保存试卷关联信息
        TextContentEntity contentLast = textContentService.getById(taskExamLast.getFrameTextContentId());
        if (contentLast == null) {
            throw new ServiceException("任务内容不存在");
        }
        List<TaskItemPo> taskItemPosLast = contentLast.getContentArray(TaskItemPo.class);
        if (!CollectionUtils.isEmpty(taskItemPosLast)) {
            List<Long> ids = taskItemPosLast.stream()
                    .map(TaskItemPo::getExamPaperId)
                    .collect(Collectors.toList());
            examPaperDao.updateTaskByIds(null, ids);
        }
        examPaperService.updateTaskPaperRelation(taskExam.getId(), taskExam.getPaperItems());

        // 保存试卷内容
        TextContentEntity textContent = buildTextContentByExamPaper(taskExam.getPaperItems());
        textContent.setId(taskExamLast.getFrameTextContentId());
        textContentService.updateById(textContent);

        // 修改任务基本信息
        BeanUtils.copyProperties(taskExam, taskExamLast);
        updateById(taskExamLast);
    }

    @Override
    public TaskExamVo getTaskExamVo(Long id) {
        TaskExamEntity taskExam = getById(id);
        if (taskExam == null) {
            throw new ServiceException("任务不存在");
        }

        // 构造任务基本信息
        TaskExamVo taskExamVo = new TaskExamVo();
        BeanUtils.copyProperties(taskExam, taskExamVo);

        // 构造任务试卷信息
        TextContentEntity textContent = textContentService.getById(taskExam.getFrameTextContentId());
        if (textContent == null) {
            throw new ServiceException("任务内容不存在");
        }
        List<TaskItemPo> taskItemPos = textContent.getContentArray(TaskItemPo.class);
        if (!CollectionUtils.isEmpty(taskItemPos)) {
            List<Long> examPaperIds = taskItemPos.stream()
                    .map(TaskItemPo::getExamPaperId)
                    .collect(Collectors.toList());
            List<ExamPaperEntity> examPaperEntities = examPaperService.listByIds(examPaperIds);
            taskExamVo.setPaperItems(examPaperEntities);
        }

        return taskExamVo;
    }

    @Transactional
    @Override
    public void removeTaskByIds(List<Long> ids) {
        // 删除任务基本信息
        removeByIds(ids);
        // 删除任务试卷关联信息
        examPaperDao.updateTaskByIds(null, ids);
    }

    /**
     * paperText ===> textContent
     */
    private TextContentEntity buildTextContentByExamPaper(List<ExamPaperEntity> paperItems) {
        TextContentEntity textContentEntity = new TextContentEntity();
        if (!CollectionUtils.isEmpty(paperItems)) {
            List<TaskItemPo> taskItemPos = paperItems.stream().map(item -> {
                TaskItemPo taskItemPo = new TaskItemPo();
                taskItemPo.setExamPaperId(item.getId());
                taskItemPo.setExamPaperName(item.getName());
                return taskItemPo;
            }).collect(Collectors.toList());
            textContentEntity.setContent(taskItemPos);
        }
        return textContentEntity;
    }

}