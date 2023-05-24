package com.tracejp.starnight.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tracejp.starnight.entity.UserEntity;

/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
public interface UserService extends IService<UserEntity> {

    /**
     *
     * @param account 账号（用户名 | 手机号）
     * @return
     */
    UserEntity getByAccount(String account);



}

