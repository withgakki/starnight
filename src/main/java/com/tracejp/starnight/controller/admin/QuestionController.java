package com.tracejp.starnight.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.tracejp.starnight.entity.QuestionEntity;
import com.tracejp.starnight.service.QuestionService;
import com.tracejp.starnight.controller.BaseController;
import com.tracejp.starnight.entity.base.TableDataInfo;
import com.tracejp.starnight.entity.base.AjaxResult;


/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
@RestController
@RequestMapping("admin/question")
public class QuestionController extends BaseController {

    @Autowired
    private QuestionService questionService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public TableDataInfo list(QuestionEntity question) {
        startPage();
        QueryWrapper<QuestionEntity> queryWrapper = new QueryWrapper<>(question);
        List<QuestionEntity> list = questionService.list(queryWrapper);
        return getDataTable(list);
    }

    /**
     * 信息
     */
    @GetMapping("/{id}")
    public AjaxResult info(@PathVariable Long id) {
		QuestionEntity question = questionService.getById(id);
        return success(question);
    }

    /**
     * 保存
     */
    @PostMapping
    public AjaxResult save(@RequestBody QuestionEntity question) {
		questionService.save(question);
        return success();
    }

    /**
     * 修改
     */
    @PutMapping
    public AjaxResult update(@RequestBody QuestionEntity question) {
		questionService.updateById(question);
        return success();
    }

    /**
     * 删除
     */
    @DeleteMapping
    public AjaxResult delete(@RequestBody List<Long> ids) {
		questionService.removeByIds(ids);
        return success();
    }

}
