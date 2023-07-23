package com.tracejp.starnight.controller.admin;

import com.tracejp.starnight.controller.BaseController;
import com.tracejp.starnight.entity.QuestionEntity;
import com.tracejp.starnight.entity.base.AjaxResult;
import com.tracejp.starnight.entity.base.TableDataInfo;
import com.tracejp.starnight.entity.vo.QuestionInfoVo;
import com.tracejp.starnight.entity.po.QuestionPo;
import com.tracejp.starnight.entity.vo.QuestionVo;
import com.tracejp.starnight.service.QuestionService;
import com.tracejp.starnight.service.TextContentService;
import com.tracejp.starnight.utils.BeanUtils;
import com.tracejp.starnight.utils.HtmlUtils;
import com.tracejp.starnight.utils.ScoreUtils;
import com.tracejp.starnight.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
@RestController
@RequestMapping("/admin/question")
public class QuestionController extends BaseController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private TextContentService textContentService;


    /**
     * 列表
     */
    @GetMapping("/list")
    public TableDataInfo list(QuestionEntity question) {
        startPage();
        List<QuestionEntity> list = questionService.listPage(question);
        // 封装 dto
        List<QuestionInfoVo> dtoList = list.stream().map(item -> {
            QuestionInfoVo questionInfoVo = new QuestionInfoVo();
            BeanUtils.copyProperties(item, questionInfoVo);
            questionInfoVo.setScore(ScoreUtils.scoreToVM(item.getScore()));
            QuestionPo content = textContentService.getById(item.getInfoTextContentId()).getContent(QuestionPo.class);
            questionInfoVo.setShortTitle(HtmlUtils.clear(content.getTitleContent()));
            questionInfoVo.setAnalyze(content.getAnalyze());
            return questionInfoVo;
        }).collect(Collectors.toList());
        TableDataInfo dataTable = getDataTable(list);
        dataTable.setRows(dtoList);
        return dataTable;
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
        questionService.removeCheckByIds(ids);
        return success();
    }

}
