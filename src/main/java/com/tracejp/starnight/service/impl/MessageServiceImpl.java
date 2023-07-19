package com.tracejp.starnight.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tracejp.starnight.dao.MessageDao;
import com.tracejp.starnight.entity.MessageEntity;
import com.tracejp.starnight.entity.MessageUserEntity;
import com.tracejp.starnight.entity.UserEntity;
import com.tracejp.starnight.entity.param.SendMessageParam;
import com.tracejp.starnight.exception.ServiceException;
import com.tracejp.starnight.service.MessageService;
import com.tracejp.starnight.service.MessageUserService;
import com.tracejp.starnight.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
@Service("messageService")
public class MessageServiceImpl extends ServiceImpl<MessageDao, MessageEntity> implements MessageService {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageUserService messageUserService;

    @Autowired
    private MessageDao messageDao;

    @Override
    public List<MessageEntity> listPage(MessageEntity message) {
        return messageDao.listPage(message);
    }

    @Transactional
    @Override
    public void sendMessage(SendMessageParam param, UserEntity createUser) {
        List<UserEntity> receiveUsers = userService.listByIds(param.getReceiveUserIds());
        if (CollectionUtils.isEmpty(receiveUsers)) {
            throw new ServiceException("接收用户不存在");
        }

        // 构建 & 保存 消息
        MessageEntity message = new MessageEntity();
        message.setTitle(param.getTitle());
        message.setContent(param.getContent());
        message.setSendUserId(createUser.getId());
        message.setSendUserName(createUser.getUserName());
        message.setSendRealName(createUser.getRealName());
        message.setReceiveUserCount(receiveUsers.size());
        message.setReadCount(0);
        save(message);

        // 构建 & 保存 用户消息
        List<MessageUserEntity> messageUsers = receiveUsers.stream().map(receive -> {
            MessageUserEntity messageUser = new MessageUserEntity();
            messageUser.setMessageId(message.getId());
            messageUser.setReceiveUserId(receive.getId());
            messageUser.setReceiveUserName(receive.getUserName());
            messageUser.setReceiveRealName(receive.getRealName());
            messageUser.setReaded(false);
            return messageUser;
        }).collect(Collectors.toList());
        messageUserService.saveBatch(messageUsers);
    }

    @Transactional
    @Override
    public void removeAllByIds(List<Long> ids) {
        removeByIds(ids);
        messageUserService.removeByMessageIds(ids);
    }

}