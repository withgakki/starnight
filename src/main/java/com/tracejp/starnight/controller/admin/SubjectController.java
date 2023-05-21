package com.tracejp.starnight.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.tracejp.starnight.entity.SubjectEntity;
import com.tracejp.starnight.service.SubjectService;
import com.tracejp.starnight.controller.BaseController;
import com.tracejp.starnight.entity.base.TableDataInfo;
import com.tracejp.starnight.entity.base.AjaxResult;


/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
@RestController
@RequestMapping("admin/subject")
public class SubjectController extends BaseController {

    @Autowired
    private SubjectService subjectService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SubjectEntity subject) {
        startPage();
        QueryWrapper<SubjectEntity> queryWrapper = new QueryWrapper<>(subject);
        List<SubjectEntity> list = subjectService.list(queryWrapper);
        return getDataTable(list);
    }

    /**
     * 信息
     */
    @GetMapping("/{id}")
    public AjaxResult info(@PathVariable Long id) {
		SubjectEntity subject = subjectService.getById(id);
        return success(subject);
    }

    /**
     * 保存
     */
    @PostMapping
    public AjaxResult save(@RequestBody SubjectEntity subject) {
		subjectService.save(subject);
        return success();
    }

    /**
     * 修改
     */
    @PutMapping
    public AjaxResult update(@RequestBody SubjectEntity subject) {
		subjectService.updateById(subject);
        return success();
    }

    /**
     * 删除
     */
    @DeleteMapping
    public AjaxResult delete(@RequestBody List<Long> ids) {
		subjectService.removeByIds(ids);
        return success();
    }

}
