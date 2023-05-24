package com.tracejp.starnight.entity.base;

import com.tracejp.starnight.entity.UserEntity;
import lombok.Data;

/**
 * <p> 登录用户实体 <p/>
 *
 * @author traceJP
 * @since 2023/5/24 19:41
 */
@Data
public class LoginUser {

    /**
     * 登录用户令牌
     */
    private String token;

    /**
     * userId
     */
    private Long userid;

    /**
     * username
     */
    private String username;

    /**
     * 登录时间
     */
    private Long loginTime;

    /**
     * 过期时间
     */
    private Long expireTime;

    /**
     * 用户信息
     */
    private UserEntity user;

    public static LoginUser build(UserEntity user) {
        LoginUser loginUser = new LoginUser();
        loginUser.setUserid(user.getId());
        loginUser.setUsername(user.getUserName());
        loginUser.setUser(user);
        return loginUser;
    }

}
