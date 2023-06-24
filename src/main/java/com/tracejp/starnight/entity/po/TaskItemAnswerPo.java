package com.tracejp.starnight.entity.po;

import lombok.Data;

@Data
public class TaskItemAnswerPo {

    private Long examPaperId;

    private Long examPaperAnswerId;

    /**
     * 任务状态
     * 同步 ExamPaperAnswerEntity.status 试卷状态
     */
    private Integer status;

}
