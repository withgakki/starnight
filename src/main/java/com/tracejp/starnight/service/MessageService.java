package com.tracejp.starnight.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tracejp.starnight.entity.MessageEntity;
import com.tracejp.starnight.entity.UserEntity;
import com.tracejp.starnight.entity.param.SendMessageParam;

import java.util.List;

/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
public interface MessageService extends IService<MessageEntity> {

    /**
     * 分页查询
     */
    List<MessageEntity> listPage(MessageEntity message);

    /**
     * 发送消息
     */
    void sendMessage(SendMessageParam param, UserEntity createUser);

    /**
     * 批量删除消息 - 级联删除
     */
    void removeAllByIds(List<Long> ids);

}

