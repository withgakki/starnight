package com.tracejp.starnight.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tracejp.starnight.dao.UserDao;
import com.tracejp.starnight.entity.UserEntity;
import com.tracejp.starnight.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

    @Autowired
    private UserDao userDao;


    @Override
    public List<UserEntity> listPage(UserEntity user) {
        return userDao.listPage(user);
    }

    @Override
    public UserEntity getByAccount(String account) {
        LambdaQueryWrapper<UserEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserEntity::getUserName, account).or().eq(UserEntity::getPhone, account);
        return getOne(wrapper);
    }

    @Override
    public void changeStatus(Long id) {
        userDao.changeStatus(id);
    }

}