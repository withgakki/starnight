package com.tracejp.starnight.entity.vo;

import com.tracejp.starnight.entity.ExamPaperEntity;
import lombok.Data;

import java.util.List;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/6/4 17:52
 */
@Data
public class TaskExamVo {

    private Long id;

    private Integer gradeLevel;

    private String title;

    private List<ExamPaperEntity> paperItems;

}
