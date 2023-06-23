package com.tracejp.starnight.entity.vo.student;

import com.tracejp.starnight.entity.ExamPaperEntity;
import lombok.Data;

import java.util.List;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/6/11 15:59
 */
@Data
public class ExamPaperIndexVo {

    private List<ExamPaperEntity> fixedPaper;

    private List<ExamPaperEntity> timeLimitPaper;

}
