package com.tracejp.starnight.controller.student;

import com.tracejp.starnight.controller.BaseController;
import com.tracejp.starnight.entity.UserEventLogEntity;
import com.tracejp.starnight.entity.base.AjaxResult;
import com.tracejp.starnight.service.UserEventLogService;
import com.tracejp.starnight.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
@RestController("studentUserEventLogController")
@RequestMapping("/student/usereventlog")
public class UserEventLogController extends BaseController {

    @Autowired
    private UserEventLogService userEventLogService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public AjaxResult list() {
        Long userId = SecurityUtils.getUserId();
        List<UserEventLogEntity> list = userEventLogService.listFromUser(userId);
        return success(list);
    }

}
