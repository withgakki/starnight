package com.tracejp.starnight.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tracejp.starnight.entity.UserEntity;
import com.tracejp.starnight.entity.dto.SearchUserDto;

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
     * 按关键字 搜索 用户 dto
     */
    List<SearchUserDto> searchDtoByKeyword(String keyword);

    /**
     * 修改用户状态
     */
    void changeStatus(Long id);

    /**
     * 保存用户 - 级联保存
     */
    void saveToAll(UserEntity user);

    /**
     * 更新用户 - 级联更新
     */
    boolean updateToAll(UserEntity user);

    /**
     * 删除用户 - 级联删除
     */
    boolean removeToAllByIds(List<Long> ids);

}

