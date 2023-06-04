package com.tracejp.starnight.entity.po;


import lombok.Data;

import java.util.List;

@Data
public class QuestionPo {

    private String titleContent;

    private String analyze;

    private List<QuestionItemPo> questionItemPos;

    private String correct;

}
