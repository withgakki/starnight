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
public class ExamPaperAnswerSubmitVo {

    /**
     * 试卷 id
     */
    private Integer id;

    /**
     * 做题时长
     */
    private Integer doTime;

    /**
     * 做题得分
     */
    private String score;

    /**
     * 试卷问题答案
     */
    private List<ExamPaperAnswerSubmitItemVo> answerItems;

}
