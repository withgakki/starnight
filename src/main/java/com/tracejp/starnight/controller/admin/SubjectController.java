package com.tracejp.starnight.controller.admin;

import com.tracejp.starnight.controller.BaseController;
import com.tracejp.starnight.entity.SubjectEntity;
import com.tracejp.starnight.entity.base.AjaxResult;
import com.tracejp.starnight.entity.base.TableDataInfo;
import com.tracejp.starnight.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
@RestController
@RequestMapping("api/admin/subject")
public class SubjectController extends BaseController {

    @Autowired
    private SubjectService subjectService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SubjectEntity subject) {
        startPage();
        List<SubjectEntity> list = subjectService.listPage(subject);
        return getDataTable(list);
    }

    /**
     * 列表所有
     */
    @GetMapping("/list/all")
    public AjaxResult listAll() {
        List<SubjectEntity> list = subjectService.list();
        return success(list);
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
    @DeleteMapping("/{ids}")
    public AjaxResult delete(@PathVariable List<Long> ids) {
        subjectService.removeByIds(ids);
        return success();
    }

}
