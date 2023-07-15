package com.tracejp.starnight.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tracejp.starnight.controller.BaseController;
import com.tracejp.starnight.entity.UserEventLogEntity;
import com.tracejp.starnight.entity.base.TableDataInfo;
import com.tracejp.starnight.service.UserEventLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
@RestController()
@RequestMapping("/admin/usereventlog")
public class UserEventLogController extends BaseController {

    @Autowired
    private UserEventLogService userEventLogService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public TableDataInfo list(UserEventLogEntity userEventLog) {
        startPage();
        QueryWrapper<UserEventLogEntity> queryWrapper = new QueryWrapper<>(userEventLog);
        List<UserEventLogEntity> list = userEventLogService.list(queryWrapper);
        return getDataTable(list);
    }

}
