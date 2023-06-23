package com.tracejp.starnight.controller.student;

import com.tracejp.starnight.controller.BaseController;
import com.tracejp.starnight.entity.UserEntity;
import com.tracejp.starnight.entity.base.AjaxResult;
import com.tracejp.starnight.entity.bo.ExamPaperAnswerBo;
import com.tracejp.starnight.entity.vo.student.ExamPaperAnswerSubmitVo;
import com.tracejp.starnight.service.ExamPaperAnswerService;
import com.tracejp.starnight.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/6/11 10:36
 */
@RestController("studentExamPaperAnswerController")
@RequestMapping(value = "/student/exampaperanswer")
public class ExamPaperAnswerController extends BaseController {

    @Autowired
    private ExamPaperAnswerService examPaperAnswerService;


    /**
     * 提交答案
     */
    @PostMapping("/submit")
    public AjaxResult submit(@RequestBody ExamPaperAnswerSubmitVo answerVo) {
        UserEntity user = SecurityUtils.getLoginUser().getUser();
        ExamPaperAnswerBo examPaperAnswerBo = examPaperAnswerService.buildExamPaperBo(answerVo, user);

        return success();
    }

}
