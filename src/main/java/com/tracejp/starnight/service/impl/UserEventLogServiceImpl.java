package com.tracejp.starnight.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tracejp.starnight.dao.UserEventLogDao;
import com.tracejp.starnight.entity.UserEntity;
import com.tracejp.starnight.entity.UserEventLogEntity;
import com.tracejp.starnight.service.UserEventLogService;
import com.tracejp.starnight.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
@Slf4j
@Service("userEventLogService")
public class UserEventLogServiceImpl extends ServiceImpl<UserEventLogDao, UserEventLogEntity> implements UserEventLogService {

    @Autowired
    private ThreadPoolExecutor poolExecutor;

    @Override
    public CompletableFuture<Boolean> saveAsync(UserEventLogEntity userEventLogEntity) {
        CompletableFuture<Boolean> future = CompletableFuture.supplyAsync(() -> save(userEventLogEntity), poolExecutor);
        future.exceptionally(e -> {
            log.error("异步保存用户事件日志失败: {}", userEventLogEntity);
            return false;
        });
        return future;
    }

    @Override
    public CompletableFuture<Boolean> saveAsync(UserEntity user, String content) {
        UserEventLogEntity entity = new UserEventLogEntity();
        entity.setUserId(user.getId());
        entity.setUserName(user.getUserName());
        entity.setRealName(user.getRealName());
        entity.setContent(content);
        return saveAsync(entity);
    }

    @Override
    public CompletableFuture<Boolean> saveAsync(String content) {
        UserEntity user = SecurityUtils.getLoginUser().getUser();
        return saveAsync(user, content);
    }

    @Override
    public List<UserEventLogEntity> listByUserId(Long userId) {
        LambdaQueryWrapper<UserEventLogEntity> wrapper = Wrappers.lambdaQuery(UserEventLogEntity.class)
                .eq(UserEventLogEntity::getUserId, userId);
        return list(wrapper);
    }

    @Override
    public List<UserEventLogEntity> listFromUser(Long userId) {
        LambdaQueryWrapper<UserEventLogEntity> wrapper = Wrappers.lambdaQuery(UserEventLogEntity.class)
                .eq(UserEventLogEntity::getUserId, userId)
                .orderByDesc(UserEventLogEntity::getCreateTime)
                .last("limit 10");
        return list(wrapper);
    }

}