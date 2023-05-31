package com.tracejp.starnight.frame.security;

import com.tracejp.starnight.entity.UserEntity;
import com.tracejp.starnight.entity.base.LoginUser;
import com.tracejp.starnight.entity.enums.RoleEnum;
import com.tracejp.starnight.entity.enums.SystemCodeEnum;
import com.tracejp.starnight.handler.token.TokenHandler;
import com.tracejp.starnight.utils.SecurityUtils;
import com.tracejp.starnight.utils.ServletUtils;
import com.tracejp.starnight.utils.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p> token 过滤器 <p/>
 *
 * @author traceJP
 * @since 2023/5/23 21:16
 */
public class TokenAuthFilter extends BasicAuthenticationFilter {

    private final TokenHandler tokenHandler;

    public TokenAuthFilter(AuthenticationManager authenticationManager, TokenHandler tokenHandler) {
        super(authenticationManager);
        this.tokenHandler = tokenHandler;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String token = SecurityUtils.getToken(request);

        // 匿名请求
        if (StringUtils.isEmpty(token)) {
            chain.doFilter(request, response);
            return;
        }

        // token 验证
        LoginUser loginUser = tokenHandler.getLoginUser(token);
        if (loginUser != null) {
            // 存放 token 信息到 security
            UsernamePasswordAuthenticationToken authToken = buildToken(loginUser);
            SecurityContextHolder.getContext().setAuthentication(authToken);
            chain.doFilter(request, response);
            return;
        }

        // 拦截请求
        SystemCodeEnum code = SystemCodeEnum.AccessTokenError;
        ServletUtils.renderString(response, code.getCode(), code.getMessage());
    }

    /**
     * 构建 security 用户实体
     */
    private UsernamePasswordAuthenticationToken buildToken(LoginUser loginUser) {
        UserEntity user = loginUser.getUser();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(RoleEnum.fromCode(user.getRole()).getRoleName()));
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                user.getUserName(), user.getPassword(), grantedAuthorities
        );
        authToken.setDetails(loginUser);
        return authToken;
    }

}
