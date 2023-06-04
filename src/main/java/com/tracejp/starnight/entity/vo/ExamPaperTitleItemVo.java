package com.tracejp.starnight.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/6/1 21:21
 */
@Data
public class ExamPaperTitleItemVo {

    /**
     * 试卷标题名
     */
    private String name;

    /**
     * 问题 Vos
     */
    private List<QuestionVo> questionItems;

}
