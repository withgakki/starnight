package com.tracejp.starnight.controller.admin;

import com.tracejp.starnight.controller.BaseController;
import com.tracejp.starnight.entity.ExamPaperAnswerEntity;
import com.tracejp.starnight.entity.UserEntity;
import com.tracejp.starnight.entity.base.AjaxResult;
import com.tracejp.starnight.entity.base.TableDataInfo;
import com.tracejp.starnight.entity.dto.UserDto;
import com.tracejp.starnight.entity.vo.ExamPaperAndAnswerVo;
import com.tracejp.starnight.entity.vo.ExamPaperAnswerSubmitVo;
import com.tracejp.starnight.entity.vo.ExamPaperAnswerVo;
import com.tracejp.starnight.entity.vo.ExamPaperVo;
import com.tracejp.starnight.service.ExamPaperAnswerService;
import com.tracejp.starnight.service.ExamPaperService;
import com.tracejp.starnight.service.UserEventLogService;
import com.tracejp.starnight.service.UserService;
import com.tracejp.starnight.utils.ScoreUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
@RestController
@RequestMapping("/admin/exampaperanswer")
public class ExamPaperAnswerController extends BaseController {

    @Autowired
    private ExamPaperAnswerService examPaperAnswerService;

    @Autowired
    private ExamPaperService examPaperService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserEventLogService userEventLogService;

    /**
     * 列表 vo
     */
    @GetMapping("/list")
    public TableDataInfo list(ExamPaperAnswerEntity examPaperAnswer) {
        startPage();
        List<ExamPaperAnswerVo> list = examPaperAnswerService.listPageVo(examPaperAnswer);
        return getDataTable(list);
    }

    /**
     * 试卷&答卷查询 答卷id
     */
    @GetMapping("/{id}")
    public AjaxResult info(@PathVariable Long id) {
        ExamPaperAndAnswerVo vo = new ExamPaperAndAnswerVo();
        ExamPaperAnswerEntity answerEntity = examPaperAnswerService.getById(id);
        if (answerEntity == null) {
            return error("答卷不存在");
        }
        ExamPaperVo examPaperVo = examPaperService.getExamPaperVo(answerEntity.getExamPaperId());
        ExamPaperAnswerSubmitVo submitVo = examPaperAnswerService.getAnswerSubmitVoById(id);
        UserEntity userEntity = userService.getById(answerEntity.getCreateBy());
        vo.setPaper(examPaperVo);
        vo.setAnswer(submitVo);
        vo.setUser(new UserDto().convertFrom(userEntity));
        return success(vo);
    }

    /**
     * 人工改卷
     * 只批改 填空 简答
     */
    @PutMapping("/judge")
    public AjaxResult judge(@RequestBody ExamPaperAnswerSubmitVo submitVo) {
        Integer score = examPaperAnswerService.judge(submitVo);
        String scoreVm = ScoreUtils.scoreToVM(score);
        // 日志记录
        String log = "批改了试卷，答卷id：" + submitVo.getId() + "，人工判卷得分：" + scoreVm;
        userEventLogService.saveAsync(log);
        return success(scoreVm);
    }

    /**
     * 智能改卷
     */
    @PutMapping("/judge/{id}")
    public AjaxResult autoJudge(@PathVariable Long id) {
        Integer score = examPaperAnswerService.autoJudge(id);
        String scoreVm = ScoreUtils.scoreToVM(score);
        // 日志记录
        String log = "批改了试卷，答卷id：" + id + "，智能判卷得分：" + scoreVm;
        userEventLogService.saveAsync(log);
        return success(scoreVm);
    }

    /**
     * 删除
     */
    @DeleteMapping("/{ids}")
    public AjaxResult delete(@PathVariable List<Long> ids) {
        examPaperAnswerService.removeAllByIds(ids);
        return success();
    }

}
