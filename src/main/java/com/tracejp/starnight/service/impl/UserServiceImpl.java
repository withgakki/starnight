package com.tracejp.starnight.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.tracejp.starnight.dao.UserDao;
import com.tracejp.starnight.entity.UserEntity;
import com.tracejp.starnight.service.UserService;

/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

}