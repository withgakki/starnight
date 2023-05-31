package com.tracejp.starnight.frame.security;

import com.tracejp.starnight.entity.UserEntity;
import com.tracejp.starnight.entity.base.LoginUser;
import com.tracejp.starnight.entity.enums.RoleEnum;
import com.tracejp.starnight.entity.enums.UserStatusEnum;
import com.tracejp.starnight.service.UserService;
import com.tracejp.starnight.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> 认证逻辑 <p/>
 *
 * @author traceJP
 * @since 2023/5/23 9:34
 */
@Component
public class LoginAuthProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        RoleEnum role = (RoleEnum) authentication.getDetails();
        UserEntity user = userService.getByAccount(username);

        // 登录失败
        if (user == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        if (user.getRole() != role.getCode()) {
            throw new BadCredentialsException("用户名或密码错误");
        }

        boolean result = SecurityUtils.matchesPassword(password, user.getPassword());
        if (!result) {
            throw new BadCredentialsException("用户名或密码错误");
        }

        UserStatusEnum userStatusEnum = UserStatusEnum.fromCode(user.getStatus());
        if (UserStatusEnum.Disable == userStatusEnum) {
            throw new LockedException("用户被禁用");
        }

        // 登录成功
        return buildToken(user);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }

    /**
     * 构建 security 用户实体
     */
    private UsernamePasswordAuthenticationToken buildToken(UserEntity user) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(RoleEnum.fromCode(user.getRole()).getRoleName()));
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                user.getUserName(), user.getPassword(), grantedAuthorities
        );
        authToken.setDetails(LoginUser.build(user));
        return authToken;
    }

}
