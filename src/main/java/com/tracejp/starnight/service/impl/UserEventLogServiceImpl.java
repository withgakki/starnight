package com.tracejp.starnight.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.tracejp.starnight.dao.UserEventLogDao;
import com.tracejp.starnight.entity.UserEventLogEntity;
import com.tracejp.starnight.service.UserEventLogService;

/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
@Service("userEventLogService")
public class UserEventLogServiceImpl extends ServiceImpl<UserEventLogDao, UserEventLogEntity> implements UserEventLogService {

}