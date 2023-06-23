package com.tracejp.starnight.entity.bo;

import com.tracejp.starnight.entity.ExamPaperAnswerEntity;
import com.tracejp.starnight.entity.ExamPaperEntity;
import com.tracejp.starnight.entity.ExamPaperQuestionAnswerEntity;
import lombok.Data;

import java.util.List;

/**
 * <p> 答卷 提交/批改 业务模型 <p/>
 *
 * @author traceJP
 * @since 2023/6/23 14:02
 */
@Data
public class ExamPaperAnswerBo {

    /**
     * 试卷实体
     */
    public ExamPaperEntity examPaper;

    /**
     * 答卷实体
     */
    public ExamPaperAnswerEntity answer;

    /**
     * 答卷问题集合
     */
    public List<ExamPaperQuestionAnswerEntity> questionAnswers;

}
