package com.tracejp.starnight.service.impl;

import com.tracejp.starnight.entity.vo.DashboardChartVo;
import com.tracejp.starnight.service.*;
import com.tracejp.starnight.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/7/15 22:14
 */
@Service
public class ChartServiceImpl implements ChartService {

    @Autowired
    private ExamPaperService examPaperService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ExamPaperAnswerService examPaperAnswerService;

    @Autowired
    private ExamPaperQuestionAnswerService examPaperQuestionAnswerService;

    @Autowired
    private UserEventLogService userEventLogService;

    @Override
    public DashboardChartVo buildAdminDashboardChart() {
        DashboardChartVo chartVo = new DashboardChartVo();

        chartVo.setExamPaperCount(examPaperService.count());
        chartVo.setQuestionCount(questionService.count());
        chartVo.setDoExamPaperCount(examPaperAnswerService.count());
        chartVo.setDoQuestionCount(examPaperQuestionAnswerService.count());

        List<Integer> monthDayUserActionValue = userEventLogService.countMonth();
        List<Integer> monthDayDoExamQuestionValue = examPaperQuestionAnswerService.countMonth();

        chartVo.setMonthDayUserActionValue(monthDayUserActionValue);
        chartVo.setMonthDayDoExamQuestionValue(monthDayDoExamQuestionValue);
        chartVo.setMonthDayText(DateTimeUtils.MothDay());

        return chartVo;
    }

}
