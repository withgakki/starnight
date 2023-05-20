package com.tracejp.starnight.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.tracejp.starnight.dao.MessageDao;
import com.tracejp.starnight.entity.MessageEntity;
import com.tracejp.starnight.service.MessageService;

/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
@Service("messageService")
public class MessageServiceImpl extends ServiceImpl<MessageDao, MessageEntity> implements MessageService {

}