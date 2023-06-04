package com.tracejp.starnight.entity.po;


import lombok.Data;

import java.util.List;

@Data
public class ExamPaperTitleItemPo {

    /**
     * 试卷标题名
     */
    private String name;

    /**
     * 试卷标题下题目
     */
    private List<ExamPaperQuestionItemPo> questionItems;

}
