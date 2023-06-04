package com.tracejp.starnight.controller.admin;

import com.tracejp.starnight.controller.BaseController;
import com.tracejp.starnight.entity.ExamPaperEntity;
import com.tracejp.starnight.entity.base.AjaxResult;
import com.tracejp.starnight.entity.base.TableDataInfo;
import com.tracejp.starnight.service.ExamPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
@RestController
@RequestMapping("/api/admin/exampaper")
public class ExamPaperController extends BaseController {

    @Autowired
    private ExamPaperService examPaperService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ExamPaperEntity examPaper) {
        startPage();
        List<ExamPaperEntity> list = examPaperService.listPage(examPaper);
        return getDataTable(list);
    }

    /**
     * 信息
     */
    @GetMapping("/{id}")
    public AjaxResult info(@PathVariable Long id) {
        ExamPaperEntity examPaper = examPaperService.getById(id);
        return success(examPaper);
    }

    /**
     * 保存
     */
    @PostMapping
    public AjaxResult save(@RequestBody ExamPaperEntity examPaper) {
        examPaperService.save(examPaper);
        return success();
    }

    /**
     * 修改
     */
    @PutMapping
    public AjaxResult update(@RequestBody ExamPaperEntity examPaper) {
        examPaperService.updateById(examPaper);
        return success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{ids}")
    public AjaxResult delete(@PathVariable List<Long> ids) {
        examPaperService.removeByIds(ids);
        return success();
    }

}
