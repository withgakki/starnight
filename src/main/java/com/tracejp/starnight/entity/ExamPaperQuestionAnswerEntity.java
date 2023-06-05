package com.tracejp.starnight.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

import lombok.Data;

/**
 * @author traceJP
 * @since 2023-05-20 23:37:41
 */
@Data
@TableName("t_exam_paper_question_answer")
public class ExamPaperQuestionAnswerEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId
    private Long id;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 创建者id（做题人）
     */
    private Long createBy;

    /**
     * 题目id
     */
    private Long questionId;

    /**
     * 试卷id
     */
    private Long examPaperId;

    /**
     * 试卷答案id
     */
    private Long examPaperAnswerId;

    /**
     * 题型
     */
    private Integer questionType;

    /**
     * 学科id
     */
    private Long subjectId;

    /**
     * 做题人得分
     */
    private Integer customerScore;

    /**
     * 题目原始分数
     */
    private Integer questionScore;

    /**
     * 问题内容
     */
    private Long questionTextContentId;

    /**
     * 做题人答案
     */
    private String answer;

    /**
     * 题目内容id
     */
    private Long textContentId;

    /**
     * 是否正确
     */
    private Boolean doRight;

    /**
     * 排序字段
     */
    private Integer itemOrder;

}
