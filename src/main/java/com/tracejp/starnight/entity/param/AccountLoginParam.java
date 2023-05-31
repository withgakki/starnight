package com.tracejp.starnight.entity.param;

import com.tracejp.starnight.entity.enums.RoleEnum;
import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
    @NotEmpty
    private String username;

    /**
     * 密码
     */
    @NotEmpty
    private String password;

    /**
     * 角色
     */
    @NotNull
    private RoleEnum role;

    /**
     * 获取 security 包装对象
     * @return UsernamePasswordAuthenticationToken
     */
    public UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken() {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        token.setDetails(role);
        return token;
    }

}
