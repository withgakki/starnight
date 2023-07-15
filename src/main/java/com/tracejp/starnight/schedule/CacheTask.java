package com.tracejp.starnight.schedule;

import com.tracejp.starnight.constants.CacheConstants;
import com.tracejp.starnight.entity.vo.DashboardChartVo;
import com.tracejp.starnight.service.ChartService;
import com.tracejp.starnight.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * <p> 缓存任务 <p/>
 *
 * @author traceJP
 * @since 2023/7/15 22:03
 */
@Component
public class CacheTask {

    @Autowired
    private ChartService chartService;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 更新 admin dashboard chart 缓存
     * 每天凌晨 3 点执行
     */
    @Scheduled(cron = "0 0 3 * * ?")
    public void adminDashboardChartCache() {
        DashboardChartVo chartVo = chartService.buildAdminDashboardChart();
        redisUtils.setCacheObject(CacheConstants.ADMIN_DASHBOARD_CHART_CACHE_KEY, chartVo, 1L, TimeUnit.DAYS);
    }

}
