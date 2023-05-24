package com.tracejp.starnight.frame.security;

import com.tracejp.starnight.entity.enums.SystemCodeEnum;
import com.tracejp.starnight.utils.ServletUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p> 未登录 <p/>
 *
 * @author traceJP
 * @since 2023/5/24 21:58
 */
@Component
public class RestAuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        SystemCodeEnum code = SystemCodeEnum.UNAUTHORIZED;
        ServletUtils.renderString(response, code.getCode(), code.getMessage());
    }

}
