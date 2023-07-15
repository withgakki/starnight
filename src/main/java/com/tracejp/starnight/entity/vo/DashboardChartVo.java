package com.tracejp.starnight.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * <p> 图表 vo <p/>
 *
 * @author traceJP
 * @since 2023/7/15 20:48
 */
@Data
public class DashboardChartVo {

    /**
     * 试卷总数
     */
    private Integer examPaperCount;

    /**
     * 问题总数
     */
    private Integer questionCount;

    /**
     * 答卷总数
     */
    private Integer doExamPaperCount;

    /**
     * 答题总数
     */
    private Integer doQuestionCount;

    /**
     * 用户活跃度 - 月/日
     */
    private List<Integer> monthDayUserActionValue;

    /**
     * 答题量 - 月/日
     */
    private List<Integer> monthDayDoExamQuestionValue;

    /**
     * 图表横轴 月/日
     */
    private List<String> monthDayText;

}
