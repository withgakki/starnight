package com.tracejp.starnight.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.tracejp.starnight.entity.TaskExamEntity;
import com.tracejp.starnight.service.TaskExamService;
import com.tracejp.starnight.controller.base.BaseController;
import com.tracejp.starnight.entity.base.TableDataInfo;
import com.tracejp.starnight.entity.base.AjaxResult;


/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
@RestController
@RequestMapping("admin/taskexam")
public class TaskExamController extends BaseController {

    @Autowired
    private TaskExamService taskExamService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public TableDataInfo list(TaskExamEntity taskExam) {
        startPage();
        QueryWrapper<TaskExamEntity> queryWrapper = new QueryWrapper<>(taskExam);
        List<TaskExamEntity> list = taskExamService.list(queryWrapper);
        return getDataTable(list);
    }

    /**
     * 信息
     */
    @GetMapping("/{id}")
    public AjaxResult info(@PathVariable Long id) {
		TaskExamEntity taskExam = taskExamService.getById(id);
        return success(taskExam);
    }

    /**
     * 保存
     */
    @PostMapping
    public AjaxResult save(@RequestBody TaskExamEntity taskExam) {
		taskExamService.save(taskExam);
        return success();
    }

    /**
     * 修改
     */
    @PutMapping
    public AjaxResult update(@RequestBody TaskExamEntity taskExam) {
		taskExamService.updateById(taskExam);
        return success();
    }

    /**
     * 删除
     */
    @DeleteMapping
    public AjaxResult delete(@RequestBody List<Long> ids) {
		taskExamService.removeByIds(ids);
        return success();
    }

}
