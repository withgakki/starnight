package com.tracejp.starnight.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tracejp.starnight.dao.MessageDao;
import com.tracejp.starnight.dao.MessageUserDao;
import com.tracejp.starnight.entity.MessageUserEntity;
import com.tracejp.starnight.service.MessageUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
@Service("messageUserService")
public class MessageUserServiceImpl extends ServiceImpl<MessageUserDao, MessageUserEntity> implements MessageUserService {

    @Autowired
    private MessageDao messageDao;

    @Override
    public List<MessageUserEntity> listPage(Long userId) {
        LambdaQueryWrapper<MessageUserEntity> wrapper = Wrappers.lambdaQuery(MessageUserEntity.class)
                .eq(MessageUserEntity::getReceiveUserId, userId);
        return list(wrapper);
    }

    @Override
    public List<MessageUserEntity> listByMessageIds(List<Long> ids) {
        LambdaQueryWrapper<MessageUserEntity> wrapper = Wrappers.lambdaQuery(MessageUserEntity.class)
                .in(MessageUserEntity::getMessageId, ids);
        return list(wrapper);
    }

    @Override
    public void removeByMessageIds(List<Long> ids) {
        LambdaQueryWrapper<MessageUserEntity> wrapper = Wrappers.lambdaQuery(MessageUserEntity.class)
                .in(MessageUserEntity::getMessageId, ids);
        remove(wrapper);
    }

    @Override
    public Integer getUnReadCount(Long userId) {
        LambdaQueryWrapper<MessageUserEntity> wrapper = Wrappers.lambdaQuery(MessageUserEntity.class)
                .eq(MessageUserEntity::getReceiveUserId, userId)
                .eq(MessageUserEntity::getReaded, false);
        return count(wrapper);
    }

    @Transactional
    @Override
    public void readOne(Long id) {
        MessageUserEntity messageUser = getById(id);
        if (messageUser.getReaded()) {
            return;
        }
        messageUser.setReaded(true);
        messageUser.setReadTime(new Date());
        updateById(messageUser);
        messageDao.readIncrById(messageUser.getMessageId());
    }

}