package com.tracejp.starnight.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.tracejp.starnight.entity.ExamPaperAnswerEntity;
import com.tracejp.starnight.service.ExamPaperAnswerService;
import com.tracejp.starnight.controller.base.BaseController;
import com.tracejp.starnight.entity.base.TableDataInfo;
import com.tracejp.starnight.entity.base.AjaxResult;


/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
@RestController
@RequestMapping("admin/exampaperanswer")
public class ExamPaperAnswerController extends BaseController {

    @Autowired
    private ExamPaperAnswerService examPaperAnswerService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ExamPaperAnswerEntity examPaperAnswer) {
        startPage();
        QueryWrapper<ExamPaperAnswerEntity> queryWrapper = new QueryWrapper<>(examPaperAnswer);
        List<ExamPaperAnswerEntity> list = examPaperAnswerService.list(queryWrapper);
        return getDataTable(list);
    }

    /**
     * 信息
     */
    @GetMapping("/{id}")
    public AjaxResult info(@PathVariable Long id) {
		ExamPaperAnswerEntity examPaperAnswer = examPaperAnswerService.getById(id);
        return success(examPaperAnswer);
    }

    /**
     * 保存
     */
    @PostMapping
    public AjaxResult save(@RequestBody ExamPaperAnswerEntity examPaperAnswer) {
		examPaperAnswerService.save(examPaperAnswer);
        return success();
    }

    /**
     * 修改
     */
    @PutMapping
    public AjaxResult update(@RequestBody ExamPaperAnswerEntity examPaperAnswer) {
		examPaperAnswerService.updateById(examPaperAnswer);
        return success();
    }

    /**
     * 删除
     */
    @DeleteMapping
    public AjaxResult delete(@RequestBody List<Long> ids) {
		examPaperAnswerService.removeByIds(ids);
        return success();
    }

}
