package com.tracejp.starnight.service;

import com.tracejp.starnight.entity.vo.DashboardChartVo;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/7/15 22:14
 */
public interface ChartService {

    /**
     * 构建 admin dashboard chart
     */
    DashboardChartVo buildAdminDashboardChart();

}
