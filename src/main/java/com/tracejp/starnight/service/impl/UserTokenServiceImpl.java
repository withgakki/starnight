package com.tracejp.starnight.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.tracejp.starnight.dao.UserTokenDao;
import com.tracejp.starnight.entity.UserTokenEntity;
import com.tracejp.starnight.service.UserTokenService;

/**
 * @author traceJP
 * @since 2023-05-20 23:19:37
 */
@Service("userTokenService")
public class UserTokenServiceImpl extends ServiceImpl<UserTokenDao, UserTokenEntity> implements UserTokenService {

}