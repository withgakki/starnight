package com.tracejp.starnight.controller.student;

import com.tracejp.starnight.controller.BaseController;
import com.tracejp.starnight.entity.SubjectEntity;
import com.tracejp.starnight.entity.base.AjaxResult;
import com.tracejp.starnight.service.SubjectService;
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
@RestController("studentSubjectController")
@RequestMapping("/student/subject")
public class SubjectController extends BaseController {

    @Autowired
    private SubjectService subjectService;

    /**
     * 当前学生学科列表
     */
    @GetMapping("/list")
    public AjaxResult list() {
        Integer level = SecurityUtils.getLoginUser().getUser().getUserLevel();
        List<SubjectEntity> subjectEntities = subjectService.listByLevel(level);
        return success(subjectEntities);
    }

}
