package com.tracejp.starnight.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.tracejp.starnight.entity.TaskExamAnswerEntity;
import com.tracejp.starnight.service.TaskExamAnswerService;
import com.tracejp.starnight.controller.base.BaseController;
import com.tracejp.starnight.entity.base.TableDataInfo;
import com.tracejp.starnight.entity.base.AjaxResult;


/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
@RestController
@RequestMapping("admin/taskexamanswer")
public class TaskExamAnswerController extends BaseController {

    @Autowired
    private TaskExamAnswerService taskExamAnswerService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public TableDataInfo list(TaskExamAnswerEntity taskExamAnswer) {
        startPage();
        QueryWrapper<TaskExamAnswerEntity> queryWrapper = new QueryWrapper<>(taskExamAnswer);
        List<TaskExamAnswerEntity> list = taskExamAnswerService.list(queryWrapper);
        return getDataTable(list);
    }

    /**
     * 信息
     */
    @GetMapping("/{id}")
    public AjaxResult info(@PathVariable Long id) {
		TaskExamAnswerEntity taskExamAnswer = taskExamAnswerService.getById(id);
        return success(taskExamAnswer);
    }

    /**
     * 保存
     */
    @PostMapping
    public AjaxResult save(@RequestBody TaskExamAnswerEntity taskExamAnswer) {
		taskExamAnswerService.save(taskExamAnswer);
        return success();
    }

    /**
     * 修改
     */
    @PutMapping
    public AjaxResult update(@RequestBody TaskExamAnswerEntity taskExamAnswer) {
		taskExamAnswerService.updateById(taskExamAnswer);
        return success();
    }

    /**
     * 删除
     */
    @DeleteMapping
    public AjaxResult delete(@RequestBody List<Long> ids) {
		taskExamAnswerService.removeByIds(ids);
        return success();
    }

}
