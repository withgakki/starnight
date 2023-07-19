package com.tracejp.starnight.entity.param;

import lombok.Data;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/7/17 14:41
 */
@Data
public class RandomExamPaperParams {

    /**
     * 试卷名
     */
    private String paperName;

    /**
     * 学科id
     */
    private Long subjectId;

    /**
     * 单选题数量
     */
    private Integer singleChoice;

    /**
     * 多选题数量
     */
    private Integer multipleChoice;

    /**
     * 判断题数量
     */
    private Integer judgeChoice;

    /**
     * 难度
     */
    private Integer difficult;

    /**
     * 建议时长 分钟
     */
    private Integer suggestTime;

}
