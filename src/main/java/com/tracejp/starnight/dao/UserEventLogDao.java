package com.tracejp.starnight.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tracejp.starnight.entity.UserEventLogEntity;
import com.tracejp.starnight.entity.po.MonthCountPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author traceJP
 * @since 2023-05-20 22:16:49
 */
@Mapper
public interface UserEventLogDao extends BaseMapper<UserEventLogEntity> {

    /**
     * 按月/日统计用户日志数量
     */
    List<MonthCountPo> selectCountByDate(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

}
