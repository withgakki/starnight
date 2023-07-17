package com.tracejp.starnight.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;
import java.util.List;

import com.tracejp.starnight.entity.enums.QuestionTypeEnum;
import com.tracejp.starnight.utils.ArrayStringUtils;
import lombok.Data;

/**
 * @author traceJP
 * @since 2023-05-20 23:37:40
 */
@Data
@TableName("t_question")
public class QuestionEntity {

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
     * 创建者id
     */
    private Long createBy;

    /**
     * 删除标志位
     */
    private Boolean delFlag;

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
    private Integer score;

    /**
     * 级别
     */
    private Integer gradeLevel;

    /**
     * 题目难度
     */
    private Integer difficult;

    /**
     * 正确答案 - 单选、多选、判断
     */
    private String correct;

    /**
     * 题目内容信息id - 填空、简答
     */
    private Long infoTextContentId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 根据 Vo 和 题目类型设置答案
     */
    public void setCorrectFromVo(String correct, List<String> correctArray) {
        int type = this.getQuestionType();
        if (type == QuestionTypeEnum.MultipleChoice.getCode()) {
            String correctJoin = ArrayStringUtils.contentToString(correctArray);
            this.setCorrect(correctJoin);
        } else {
            this.setCorrect(correct);
        }
    }

}
