package com.tracejp.starnight.entity.param;

import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * <p> 登录参数 <p/>
 *
 * @author traceJP
 * @since 2023/5/23 9:16
 */
@Data
public class AccountLoginParam {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 获取 security 包装对象
     * @return UsernamePasswordAuthenticationToken
     */
    public UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken() {
        return new UsernamePasswordAuthenticationToken(username, password);
    }

}
