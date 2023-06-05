package com.tracejp.starnight.controller.admin;

import com.tracejp.starnight.controller.BaseController;
import com.tracejp.starnight.entity.TaskExamEntity;
import com.tracejp.starnight.entity.UserEntity;
import com.tracejp.starnight.entity.base.AjaxResult;
import com.tracejp.starnight.entity.base.TableDataInfo;
import com.tracejp.starnight.entity.vo.TaskExamVo;
import com.tracejp.starnight.service.TaskExamService;
import com.tracejp.starnight.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
@RestController
@RequestMapping("/api/admin/taskexam")
public class TaskExamController extends BaseController {

    @Autowired
    private TaskExamService taskExamService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public TableDataInfo list(TaskExamEntity taskExam) {
        startPage();
        List<TaskExamEntity> list = taskExamService.listPage(taskExam);
        return getDataTable(list);
    }

    /**
     * 信息
     */
    @GetMapping("/{id}")
    public AjaxResult info(@PathVariable Long id) {
        TaskExamVo taskExam = taskExamService.getTaskExamVo(id);
        return success(taskExam);
    }

    /**
     * 保存
     */
    @PostMapping
    public AjaxResult save(@RequestBody TaskExamVo taskExam) {
        taskExamService.saveTaskExamVo(taskExam, new UserEntity());  // SecurityUtils.getLoginUser().getUser()
        return success();
    }

    /**
     * 修改
     */
    @PutMapping
    public AjaxResult update(@RequestBody TaskExamVo taskExam) {
        taskExamService.updateTaskExamVo(taskExam);
        return success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{ids}")
    public AjaxResult delete(@PathVariable List<Long> ids) {
        taskExamService.removeTaskByIds(ids);
        return success();
    }

}
