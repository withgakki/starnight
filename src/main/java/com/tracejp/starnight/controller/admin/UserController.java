package com.tracejp.starnight.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.tracejp.starnight.entity.UserEntity;
import com.tracejp.starnight.service.UserService;
import com.tracejp.starnight.controller.BaseController;
import com.tracejp.starnight.entity.base.TableDataInfo;
import com.tracejp.starnight.entity.base.AjaxResult;


/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
@RestController
@RequestMapping("/api/admin/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public TableDataInfo list(UserEntity user) {
        startPage();
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>(user);
        List<UserEntity> list = userService.list(queryWrapper);
        return getDataTable(list);
    }

    /**
     * 信息
     */
    @GetMapping("/{id}")
    public AjaxResult info(@PathVariable Long id) {
		UserEntity user = userService.getById(id);
        return success(user);
    }

    /**
     * 保存
     */
    @PostMapping
    public AjaxResult save(@RequestBody UserEntity user) {
		userService.save(user);
        return success();
    }

    /**
     * 修改
     */
    @PutMapping
    public AjaxResult update(@RequestBody UserEntity user) {
		userService.updateById(user);
        return success();
    }

    /**
     * 删除
     */
    @DeleteMapping
    public AjaxResult delete(@RequestBody List<Long> ids) {
		userService.removeByIds(ids);
        return success();
    }

}
