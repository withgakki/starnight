package com.tracejp.starnight.entity.vo;

import lombok.Data;

import java.util.Date;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/7/14 9:05
 */
@Data
public class ExamPaperAnswerVo {

    /**
     * 自增id
     */
    private Long id;

    /**
     * 创建时间（提交时间）
     */
    private Date createTime;

    /**
     * 创建者id
     */
    private Long createBy;

    /**
     * 试卷id
     */
    private Long examPaperId;

    /**
     * 试卷名称
     */
    private String paperName;

    /**
     * 试卷类型 1.固定试卷 4.时段试卷 6.任务试卷
     */
    private Integer paperType;

    /**
     * 学科id
     */
    private Long subjectId;

    /**
     * 系统判定得分
     */
    private Integer systemScore;

    /**
     * 最终得分
     */
    private Integer userScore;

    /**
     * 试卷总分
     */
    private Integer paperScore;

    /**
     * 做对题目数量
     */
    private Integer questionCorrect;

    /**
     * 题目总数量
     */
    private Integer questionCount;

    /**
     * 做题时间 秒
     */
    private Integer doTime;

    /**
     * 试卷状态 1待判分 2完成
     */
    private Integer status;

    /**
     * 考试任务id
     */
    private Long taskExamId;

    // =======================================================

    private String userName;

    private String realName;

}
