package com.tracejp.starnight.controller.admin;

import com.tracejp.starnight.controller.BaseController;
import com.tracejp.starnight.entity.QuestionEntity;
import com.tracejp.starnight.entity.base.AjaxResult;
import com.tracejp.starnight.entity.base.TableDataInfo;
import com.tracejp.starnight.entity.vo.QuestionVo;
import com.tracejp.starnight.service.QuestionService;
import com.tracejp.starnight.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
@RestController
@RequestMapping("/admin/question")
public class QuestionController extends BaseController {

    @Autowired
    private QuestionService questionService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public TableDataInfo list(QuestionEntity question) {
        startPage();
        List<QuestionEntity> list = questionService.listPage(question);
        return getDataTable(list);
    }

    /**
     * 信息
     */
    @GetMapping("/{id}")
    public AjaxResult info(@PathVariable Long id) {
        QuestionVo question = questionService.getQuestionVo(id);
        return success(question);
    }

    /**
     * 保存
     */
    @PostMapping
    public AjaxResult save(@RequestBody QuestionVo question) {
        question.validQuestionVo();
        questionService.saveQuestionVo(question, SecurityUtils.getUserId());
        return success();
    }

    /**
     * 修改
     */
    @PutMapping
    public AjaxResult update(@RequestBody QuestionVo question) {
        questionService.updateQuestionVo(question);
        return success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{ids}")
    public AjaxResult delete(@PathVariable List<Long> ids) {
        questionService.removeByIds(ids);
        return success();
    }

}
