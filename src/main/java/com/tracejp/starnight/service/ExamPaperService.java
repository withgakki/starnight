package com.tracejp.starnight.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tracejp.starnight.entity.ExamPaperEntity;
import com.tracejp.starnight.entity.vo.ExamPaperVo;

import java.util.List;

/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
public interface ExamPaperService extends IService<ExamPaperEntity> {

    /**
     * 分页
     */
    List<ExamPaperEntity> listPage(ExamPaperEntity examPaper);

    /**
     * 分页任务试卷
     */
    List<ExamPaperEntity> listTaskExamPaperPage(ExamPaperEntity examPaper);

    /**
     * 随机获取普通试卷（除任务试卷）
     */
    List<ExamPaperEntity> listStudentIndexPage(Integer level, Integer type);

    /**
     * 通过 id 获取 vo
     */
    ExamPaperVo getExamPaperVo(Long id);

    /**
     * 保存 vo
     */
    void saveExamPaperVo(ExamPaperVo examPaper, Long userId);

    /**
     * 修改 vo
     */
    void updateExamPaperVo(ExamPaperVo examPaper);

    /**
     * 修改任务试卷关联
     */
    void updateTaskPaperRelation(Long taskId, List<ExamPaperEntity> examPaperItems);

}

