package com.tracejp.starnight.controller.student;

import com.tracejp.starnight.controller.BaseController;
import com.tracejp.starnight.entity.UserEntity;
import com.tracejp.starnight.entity.base.AjaxResult;
import com.tracejp.starnight.entity.vo.student.TaskExamIndexVo;
import com.tracejp.starnight.service.TaskExamService;
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
 * @since 2023/6/11 10:35
 */
@RestController("studentTaskExamController")
@RequestMapping("/student/taskexam")
public class TaskExamController extends BaseController {

    @Autowired
    private TaskExamService taskExamService;

    /**
     * 获取当前学生任务考试列表
     */
    @GetMapping("/list/index")
    public AjaxResult listIndex() {
        UserEntity user = SecurityUtils.getLoginUser().getUser();
        List<TaskExamIndexVo> vos = taskExamService.listAllByStudent(user.getUserLevel(), user.getId());
        return success(vos);
    }

}
