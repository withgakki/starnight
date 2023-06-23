package com.tracejp.starnight.entity.vo.student;

import lombok.Data;

import java.util.List;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/6/23 13:30
 */
@Data
public class ExamPaperAnswerSubmitItemVo {

    /**
     * 试卷 id
     */
    private Long id;

    /**
     * 问题 id
     */
    private Long questionId;

    /**
     * 是否正确
     */
    private Boolean doRight;

    /**
     * 答案：单选、判断、简答
     */
    private String content;

    /**
     * 答案数组：多选、填空
     */
    private List<String> contentArray;

    /**
     * 当前答案得分
     */
    private String score;

    /**
     * 问题原始分数
     */
    private String questionScore;

    /**
     * 排序字段
     */
    private Integer itemOrder;

}
