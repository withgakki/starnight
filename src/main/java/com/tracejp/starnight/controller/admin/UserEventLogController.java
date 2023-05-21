package com.tracejp.starnight.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.tracejp.starnight.entity.UserEventLogEntity;
import com.tracejp.starnight.service.UserEventLogService;
import com.tracejp.starnight.controller.BaseController;
import com.tracejp.starnight.entity.base.TableDataInfo;
import com.tracejp.starnight.entity.base.AjaxResult;


/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
@RestController
@RequestMapping("admin/usereventlog")
public class UserEventLogController extends BaseController {

    @Autowired
    private UserEventLogService userEventLogService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public TableDataInfo list(UserEventLogEntity userEventLog) {
        startPage();
        QueryWrapper<UserEventLogEntity> queryWrapper = new QueryWrapper<>(userEventLog);
        List<UserEventLogEntity> list = userEventLogService.list(queryWrapper);
        return getDataTable(list);
    }

    /**
     * 信息
     */
    @GetMapping("/{id}")
    public AjaxResult info(@PathVariable Long id) {
		UserEventLogEntity userEventLog = userEventLogService.getById(id);
        return success(userEventLog);
    }

    /**
     * 保存
     */
    @PostMapping
    public AjaxResult save(@RequestBody UserEventLogEntity userEventLog) {
		userEventLogService.save(userEventLog);
        return success();
    }

    /**
     * 修改
     */
    @PutMapping
    public AjaxResult update(@RequestBody UserEventLogEntity userEventLog) {
		userEventLogService.updateById(userEventLog);
        return success();
    }

    /**
     * 删除
     */
    @DeleteMapping
    public AjaxResult delete(@RequestBody List<Long> ids) {
		userEventLogService.removeByIds(ids);
        return success();
    }

}
