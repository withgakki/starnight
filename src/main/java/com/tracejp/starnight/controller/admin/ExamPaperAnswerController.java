package com.tracejp.starnight.controller.admin;

import com.tracejp.starnight.controller.BaseController;
import com.tracejp.starnight.entity.ExamPaperAnswerEntity;
import com.tracejp.starnight.entity.base.AjaxResult;
import com.tracejp.starnight.entity.base.TableDataInfo;
import com.tracejp.starnight.entity.vo.ExamPaperAnswerSubmitVo;
import com.tracejp.starnight.service.ExamPaperAnswerService;
import com.tracejp.starnight.utils.ScoreUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
@RestController
@RequestMapping("/admin/exampaperanswer")
public class ExamPaperAnswerController extends BaseController {

    @Autowired
    private ExamPaperAnswerService examPaperAnswerService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ExamPaperAnswerEntity examPaperAnswer) {
        startPage();
        List<ExamPaperAnswerEntity> list = examPaperAnswerService.listPage(examPaperAnswer);
        return getDataTable(list);
    }

    /**
     * 改卷
     * 只批改 填空 简答
     */
    @PutMapping("/judge")
    public AjaxResult judge(@RequestBody ExamPaperAnswerSubmitVo submitVo) {
        Integer score = examPaperAnswerService.judge(submitVo);
        return success(ScoreUtils.scoreToVM(score));
    }

}
