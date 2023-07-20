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
     * 通过用户名获取用户
     */
    UserEntity getByUserName(String username);

    /**
     * 修改用户状态
     */
    void changeStatus(Long id);

    /**
     * 保存用户 - 级联保存
     */
    void saveToAll(UserEntity user);

}

