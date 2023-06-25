package com.tracejp.starnight.controller.student;

import com.tracejp.starnight.controller.BaseController;
import com.tracejp.starnight.entity.ExamPaperAnswerEntity;
import com.tracejp.starnight.entity.UserEntity;
import com.tracejp.starnight.entity.base.AjaxResult;
import com.tracejp.starnight.entity.bo.ExamPaperAnswerBo;
import com.tracejp.starnight.entity.vo.ExamPaperVo;
import com.tracejp.starnight.entity.vo.ExamPaperAnswerSubmitVo;
import com.tracejp.starnight.entity.vo.student.ExamPaperInfoVo;
import com.tracejp.starnight.service.ExamPaperAnswerService;
import com.tracejp.starnight.service.ExamPaperService;
import com.tracejp.starnight.utils.ScoreUtils;
import com.tracejp.starnight.utils.SecurityUtils;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/6/11 10:36
 */
@Log4j
@RestController("studentExamPaperAnswerController")
@RequestMapping(value = "/student/exampaperanswer")
public class ExamPaperAnswerController extends BaseController {

    @Autowired
    private ExamPaperAnswerService examPaperAnswerService;

    @Autowired
    private ExamPaperService examPaperService;

    /**
     * 提交答案
     */
    @PostMapping("/submit")
    public AjaxResult submit(@RequestBody ExamPaperAnswerSubmitVo answerVo) {
        UserEntity user = SecurityUtils.getLoginUser().getUser();
        ExamPaperAnswerBo examPaperAnswerBo = examPaperAnswerService.buildExamPaperBo(answerVo, user);
        examPaperAnswerService.saveAnswerBoAsync(examPaperAnswerBo).whenComplete((result, e) -> {
            if (e != null) {
                logger.error("试卷答案保存失败[{}]：用户id：{}，试卷id：{}",
                        e, user.getId(), examPaperAnswerBo.getExamPaper().getId());
            }
        });
        Integer systemScore = examPaperAnswerBo.getAnswer().getSystemScore();
        return success(ScoreUtils.scoreToVM(systemScore));
    }

    /**
     * 试卷&答卷查询 答卷id
     */
    @GetMapping("/{id}")
    public AjaxResult info(@PathVariable Long id) {
        ExamPaperInfoVo examPaperInfoVo = new ExamPaperInfoVo();
        ExamPaperAnswerEntity answerEntity = examPaperAnswerService.getById(id);
        if (answerEntity == null) {
            return error("答卷不存在");
        }
        ExamPaperVo examPaperVo = examPaperService.getExamPaperVo(answerEntity.getExamPaperId());
        ExamPaperAnswerSubmitVo submitVo = examPaperAnswerService.getAnswerSubmitVoById(id);
        examPaperInfoVo.setPaper(examPaperVo);
        examPaperInfoVo.setAnswer(submitVo);
        return success(examPaperInfoVo);
    }

}
