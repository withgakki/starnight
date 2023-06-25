package com.tracejp.starnight.controller.student;

import com.tracejp.starnight.controller.BaseController;
import com.tracejp.starnight.entity.base.TableDataInfo;
import com.tracejp.starnight.entity.vo.student.QuestionAnswerErrorVo;
import com.tracejp.starnight.service.ExamPaperQuestionAnswerService;
import com.tracejp.starnight.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/6/25 15:30
 */
@RestController("studentExamPaperQuestionAnswerController")
@RequestMapping("/student/exampaperquestionanswer")
public class ExamPaperQuestionAnswerController extends BaseController {

    @Autowired
    private ExamPaperQuestionAnswerService examPaperQuestionAnswerService;

    @GetMapping("/list")
    public TableDataInfo listQuestionAnswerErrorVo() {
        Long userId = SecurityUtils.getUserId();
        startPage();
        List<QuestionAnswerErrorVo> list = examPaperQuestionAnswerService.listQuestionAnswerErrorVo(userId);
        return getDataTable(list);
    }

}
