package com.tracejp.starnight.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tracejp.starnight.entity.UserEntity;

import java.util.List;

/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
public interface UserService extends IService<UserEntity> {

    /**
     * 分页
     */
    List<UserEntity> listPage(UserEntity user);

    /**
     * @param account 账号（用户名 | 手机号）
     */
    UserEntity getByAccount(String account);

    void changeStatus(Long id);

}

