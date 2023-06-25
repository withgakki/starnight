package com.tracejp.starnight.entity.vo.student;

import com.tracejp.starnight.entity.vo.ExamPaperAnswerSubmitVo;
import com.tracejp.starnight.entity.vo.ExamPaperVo;
import lombok.Data;

/**
 * <p> 试卷vo 包含试卷和答案 <p/>
 *
 * @author traceJP
 * @since 2023/6/24 13:49
 */
@Data
public class ExamPaperInfoVo {

    private ExamPaperVo paper;

    private ExamPaperAnswerSubmitVo answer;

}
