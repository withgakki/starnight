package com.tracejp.starnight.controller.admin;

import com.tracejp.starnight.constants.CacheConstants;
import com.tracejp.starnight.controller.BaseController;
import com.tracejp.starnight.entity.base.AjaxResult;
import com.tracejp.starnight.entity.vo.DashboardChartVo;
import com.tracejp.starnight.service.ChartService;
import com.tracejp.starnight.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/7/15 20:41
 */
@RestController
@RequestMapping("/admin/dashboard")
public class DashboardController extends BaseController {

    @Autowired
    private ChartService chartService;

    @Autowired
    private RedisUtils redisUtils;

    @GetMapping("/chart")
    public AjaxResult dashboardChart() {
        DashboardChartVo chartVo = redisUtils.getCacheObject(CacheConstants.ADMIN_DASHBOARD_CHART_CACHE_KEY);
        if (chartVo == null) {
            chartVo = chartService.buildAdminDashboardChart();
            redisUtils.setCacheObject(CacheConstants.ADMIN_DASHBOARD_CHART_CACHE_KEY, chartVo, 1L, TimeUnit.DAYS);
        }
        return success(chartVo);
    }

}
