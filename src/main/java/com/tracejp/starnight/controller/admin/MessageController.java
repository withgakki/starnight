package com.tracejp.starnight.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.tracejp.starnight.entity.MessageEntity;
import com.tracejp.starnight.service.MessageService;
import com.tracejp.starnight.controller.BaseController;
import com.tracejp.starnight.entity.base.TableDataInfo;
import com.tracejp.starnight.entity.base.AjaxResult;


/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
@RestController
@RequestMapping("/admin/message")
public class MessageController extends BaseController {

    @Autowired
    private MessageService messageService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public TableDataInfo list(MessageEntity message) {
        startPage();
        QueryWrapper<MessageEntity> queryWrapper = new QueryWrapper<>(message);
        List<MessageEntity> list = messageService.list(queryWrapper);
        return getDataTable(list);
    }

    /**
     * 信息
     */
    @GetMapping("/{id}")
    public AjaxResult info(@PathVariable Long id) {
		MessageEntity message = messageService.getById(id);
        return success(message);
    }

    /**
     * 保存
     */
    @PostMapping
    public AjaxResult save(@RequestBody MessageEntity message) {
		messageService.save(message);
        return success();
    }

    /**
     * 修改
     */
    @PutMapping
    public AjaxResult update(@RequestBody MessageEntity message) {
		messageService.updateById(message);
        return success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{ids}")
    public AjaxResult delete(@PathVariable List<Long> ids) {
		messageService.removeByIds(ids);
        return success();
    }

}
