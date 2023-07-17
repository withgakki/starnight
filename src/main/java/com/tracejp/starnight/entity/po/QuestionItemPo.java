package com.tracejp.starnight.entity.po;


import lombok.Data;

/**
 * 问题项 PO
 * - 单选题、多选题、判断题为题目选项
 * - 填空题为答案选项
 */
@Data
public class QuestionItemPo {

    private String prefix;

    private String content;

    private String score;

    private String itemUuid;

}
