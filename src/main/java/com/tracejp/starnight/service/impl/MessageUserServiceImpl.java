package com.tracejp.starnight.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.tracejp.starnight.dao.MessageUserDao;
import com.tracejp.starnight.entity.MessageUserEntity;
import com.tracejp.starnight.service.MessageUserService;

/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
@Service("messageUserService")
public class MessageUserServiceImpl extends ServiceImpl<MessageUserDao, MessageUserEntity> implements MessageUserService {

}