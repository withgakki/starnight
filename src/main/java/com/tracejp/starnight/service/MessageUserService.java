package com.tracejp.starnight.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tracejp.starnight.entity.MessageUserEntity;

import java.util.List;

/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
public interface MessageUserService extends IService<MessageUserEntity> {

    /**
     * 通过 userId 分页 用户消息列表
     */
    List<MessageUserEntity> listPage(Long userId);

    /**
     * 通过消息 ids 列表 用户消息
     */
    List<MessageUserEntity> listByMessageIds(List<Long> ids);

    /**
     * 通过消息 ids 删除 用户消息
     */
    void removeByMessageIds(List<Long> ids);

    /**
     * 通过 userId 获取未读消息数量
     */
    Integer getUnReadCount(Long userId);

    /**
     * 读取一条消息 用户消息id
     */
    void readOne(Long id);

}

