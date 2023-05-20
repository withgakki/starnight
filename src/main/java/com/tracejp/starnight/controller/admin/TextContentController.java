package com.tracejp.starnight.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.tracejp.starnight.entity.TextContentEntity;
import com.tracejp.starnight.service.TextContentService;
import com.tracejp.starnight.controller.base.BaseController;
import com.tracejp.starnight.entity.base.TableDataInfo;
import com.tracejp.starnight.entity.base.AjaxResult;


/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
@RestController
@RequestMapping("admin/textcontent")
public class TextContentController extends BaseController {

    @Autowired
    private TextContentService textContentService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public TableDataInfo list(TextContentEntity textContent) {
        startPage();
        QueryWrapper<TextContentEntity> queryWrapper = new QueryWrapper<>(textContent);
        List<TextContentEntity> list = textContentService.list(queryWrapper);
        return getDataTable(list);
    }

    /**
     * 信息
     */
    @GetMapping("/{id}")
    public AjaxResult info(@PathVariable Long id) {
		TextContentEntity textContent = textContentService.getById(id);
        return success(textContent);
    }

    /**
     * 保存
     */
    @PostMapping
    public AjaxResult save(@RequestBody TextContentEntity textContent) {
		textContentService.save(textContent);
        return success();
    }

    /**
     * 修改
     */
    @PutMapping
    public AjaxResult update(@RequestBody TextContentEntity textContent) {
		textContentService.updateById(textContent);
        return success();
    }

    /**
     * 删除
     */
    @DeleteMapping
    public AjaxResult delete(@RequestBody List<Long> ids) {
		textContentService.removeByIds(ids);
        return success();
    }

}
