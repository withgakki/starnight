package com.tracejp.starnight.frame;

import com.tracejp.starnight.entity.enums.RoleEnum;
import com.tracejp.starnight.frame.properties.SecurityConfigProperties;
import com.tracejp.starnight.frame.security.RestAccessDeniedHandler;
import com.tracejp.starnight.frame.security.RestAuthEntryPoint;
import com.tracejp.starnight.frame.security.TokenAuthFilter;
import com.tracejp.starnight.handler.token.TokenHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import java.util.List;

/**
 * <p> Spring Security 配置 <p/>
 *
 * @author traceJP
 * @since 2023/5/23 9:08
 */
@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(SecurityConfigProperties.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityConfigProperties configProperties;

    @Autowired
    private TokenHandler tokenHandler;

    @Autowired
    private RestAuthEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private RestAccessDeniedHandler restAccessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 白名单
        List<String> securityIgnoreUrls = configProperties.getIgnores();

        http
                .addFilter(tokenAuthFilter(tokenHandler))// 普通请求 token 过滤器
                .authorizeRequests()
                .antMatchers(securityIgnoreUrls.toArray(new String[0])).permitAll()  // 白名单接口放行
                .antMatchers("/api/admin/**").hasRole(RoleEnum.ADMIN.getName())  // admin 接口鉴权
                .antMatchers("/api/student/**").hasRole(RoleEnum.STUDENT.getName())  // student 接口鉴权
                .anyRequest().authenticated()  // global 接口认证
                .and().exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint)  // 未登录异常处理
                .accessDeniedHandler(restAccessDeniedHandler);  // 未认证异常处理

    }

    /**
     * 暴露系统统一鉴权管理器
     */
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /**
     * token 过滤器
     */
    protected TokenAuthFilter tokenAuthFilter(TokenHandler tokenHandler) throws Exception {
        return new TokenAuthFilter(authenticationManagerBean(), tokenHandler);
    }

}
