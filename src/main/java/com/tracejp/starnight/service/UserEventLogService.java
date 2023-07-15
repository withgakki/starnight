package com.tracejp.starnight.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tracejp.starnight.entity.UserEntity;
import com.tracejp.starnight.entity.UserEventLogEntity;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
public interface UserEventLogService extends IService<UserEventLogEntity> {

    /**
     * 异步保存
     */
    CompletableFuture<Boolean> saveAsync(UserEventLogEntity userEventLogEntity);

    /**
     * 异步保存
     */
    CompletableFuture<Boolean> saveAsync(UserEntity user, String content);

    /**
     * 异步保存当前用户的事件日志
     */
    CompletableFuture<Boolean> saveAsync(String content);

    /**
     * 通过 用户id 列表
     */
    List<UserEventLogEntity> listByUserId(Long userId);

    /**
     * 列表 用户日志 - 按时间排序 & 只返回最近的 10 条
     */
    List<UserEventLogEntity> listFromUser(Long userId);

    /**
     * 查询月份记录数
     */
    List<Integer> countMonth();

}

