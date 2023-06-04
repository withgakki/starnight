package com.tracejp.starnight.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.tracejp.starnight.entity.ExamPaperQuestionAnswerEntity;
import com.tracejp.starnight.service.ExamPaperQuestionAnswerService;
import com.tracejp.starnight.controller.BaseController;
import com.tracejp.starnight.entity.base.TableDataInfo;
import com.tracejp.starnight.entity.base.AjaxResult;


/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
@RestController
@RequestMapping("admin/exampaperquestionanswer")
public class ExamPaperQuestionAnswerController extends BaseController {

    @Autowired
    private ExamPaperQuestionAnswerService examPaperQuestionAnswerService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ExamPaperQuestionAnswerEntity examPaperQuestionAnswer) {
        startPage();
        QueryWrapper<ExamPaperQuestionAnswerEntity> queryWrapper = new QueryWrapper<>(examPaperQuestionAnswer);
        List<ExamPaperQuestionAnswerEntity> list = examPaperQuestionAnswerService.list(queryWrapper);
        return getDataTable(list);
    }

    /**
     * 信息
     */
    @GetMapping("/{id}")
    public AjaxResult info(@PathVariable Long id) {
		ExamPaperQuestionAnswerEntity examPaperQuestionAnswer = examPaperQuestionAnswerService.getById(id);
        return success(examPaperQuestionAnswer);
    }

    /**
     * 保存
     */
    @PostMapping
    public AjaxResult save(@RequestBody ExamPaperQuestionAnswerEntity examPaperQuestionAnswer) {
		examPaperQuestionAnswerService.save(examPaperQuestionAnswer);
        return success();
    }

    /**
     * 修改
     */
    @PutMapping
    public AjaxResult update(@RequestBody ExamPaperQuestionAnswerEntity examPaperQuestionAnswer) {
		examPaperQuestionAnswerService.updateById(examPaperQuestionAnswer);
        return success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{ids}")
    public AjaxResult delete(@PathVariable List<Long> ids) {
		examPaperQuestionAnswerService.removeByIds(ids);
        return success();
    }

}
