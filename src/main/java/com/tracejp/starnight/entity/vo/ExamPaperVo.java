package com.tracejp.starnight.entity.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * <p> 试卷查询Vo <p/>
 *
 * @author traceJP
 * @since 2023/6/1 19:40
 */
@Data
public class ExamPaperVo {

    /**
     * 自增id
     */
    private Long id;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建者id
     */
    private Long createBy;

    /**
     * 删除标志位
     */
    private Boolean delFlag;

    /**
     * 试卷名称
     */
    private String name;

    /**
     * 学科id
     */
    private Long subjectId;

    /**
     * 试卷类型 1.固定试卷 4.时段试卷 6.任务试卷
     */
    private Integer paperType;

    /**
     * 年级
     */
    private Integer gradeLevel;

    /**
     * 试卷总分
     */
    private Integer score;

    /**
     * 题目数量
     */
    private Integer questionCount;

    /**
     * 建议时长 分钟
     */
    private Integer suggestTime;

    /**
     * 时段试卷 开始时间
     */
    private Date limitStartTime;

    /**
     * 时段试卷 结束时间
     */
    private Date limitEndTime;

    /**
     * 考试任务id
     */
    private Long taskExamId;

    /**
     * 试卷题目列表
     */
    private List<ExamPaperTitleItemVo> titleItems;

}
