package com.tracejp.starnight.controller.student;

import com.tracejp.starnight.controller.BaseController;
import com.tracejp.starnight.entity.base.AjaxResult;
import com.tracejp.starnight.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/7/17 14:50
 */
@RestController("studentQuestionController")
@RequestMapping("/student/question")
public class QuestionController extends BaseController {

    @Autowired
    private QuestionService questionService;

    /**
     * GPT 题目解析
     */
    @GetMapping("/analyze/{id}")
    public AjaxResult gptQuestionAnalyze(@PathVariable Long id) {
        String result = questionService.gptQuestionAnalyze(id);
        return success(result);
    }

}
