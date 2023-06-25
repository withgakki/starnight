package com.tracejp.starnight.entity.vo.student;

import com.tracejp.starnight.entity.vo.QuestionVo;
import lombok.Data;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/6/25 15:35
 */
@Data
public class QuestionAnswerErrorVo {

    /**
     * 原问题 id
     */
    private Long questionId;

    /**
     * 题型
     */
    private Integer questionType;

    /**
     * 学科id
     */
    private Long subjectId;

    /**
     * 学科名
     */
    private String subjectName;

    /**
     * 错误次数
     */
    private Integer errorCount;

    /**
     * 问题 vo
     */
    private QuestionVo question;

}
