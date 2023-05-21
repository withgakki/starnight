package com.tracejp.starnight.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.tracejp.starnight.entity.MessageUserEntity;
import com.tracejp.starnight.service.MessageUserService;
import com.tracejp.starnight.controller.BaseController;
import com.tracejp.starnight.entity.base.TableDataInfo;
import com.tracejp.starnight.entity.base.AjaxResult;


/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
@RestController
@RequestMapping("admin/messageuser")
public class MessageUserController extends BaseController {

    @Autowired
    private MessageUserService messageUserService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public TableDataInfo list(MessageUserEntity messageUser) {
        startPage();
        QueryWrapper<MessageUserEntity> queryWrapper = new QueryWrapper<>(messageUser);
        List<MessageUserEntity> list = messageUserService.list(queryWrapper);
        return getDataTable(list);
    }

    /**
     * 信息
     */
    @GetMapping("/{id}")
    public AjaxResult info(@PathVariable Long id) {
		MessageUserEntity messageUser = messageUserService.getById(id);
        return success(messageUser);
    }

    /**
     * 保存
     */
    @PostMapping
    public AjaxResult save(@RequestBody MessageUserEntity messageUser) {
		messageUserService.save(messageUser);
        return success();
    }

    /**
     * 修改
     */
    @PutMapping
    public AjaxResult update(@RequestBody MessageUserEntity messageUser) {
		messageUserService.updateById(messageUser);
        return success();
    }

    /**
     * 删除
     */
    @DeleteMapping
    public AjaxResult delete(@RequestBody List<Long> ids) {
		messageUserService.removeByIds(ids);
        return success();
    }

}
