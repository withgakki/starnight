package com.tracejp.starnight.entity.dto;

import com.tracejp.starnight.entity.QuestionEntity;
import com.tracejp.starnight.entity.base.IOutputConverter;
import lombok.Data;

import java.util.Date;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/6/8 17:41
 */
@Data
public class QuestionDto implements IOutputConverter<QuestionDto, QuestionEntity> {

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
     * 题目类型 1.单选题 2.多选题 3.判断题 4.填空题 5.简答题
     */
    private Integer questionType;

    /**
     * 学科
     */
    private Long subjectId;

    /**
     * 题目总分
     */
    private String score;

    /**
     * 级别
     */
    private Integer gradeLevel;

    /**
     * 题目难度
     */
    private Integer difficult;

    /**
     * 正确答案
     */
    private String correct;

    /**
     * 题目内容信息id
     */
    private Long infoTextContentId;

    /**
     * 状态
     */
    private Integer status;

    // ================================== 关联字段 ==================================

    /**
     * 分析内容信息id
     */
    private String analyze;

    /**
     * 题目内容 【去HTML格式】
     */
    private String shortTitle;

}
