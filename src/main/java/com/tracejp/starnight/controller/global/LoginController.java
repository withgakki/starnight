package com.tracejp.starnight.controller.global;

import com.tracejp.starnight.controller.BaseController;
import com.tracejp.starnight.entity.base.AjaxResult;
import com.tracejp.starnight.entity.base.LoginUser;
import com.tracejp.starnight.entity.param.AccountLoginParam;
import com.tracejp.starnight.handler.token.TokenHandler;
import com.tracejp.starnight.utils.JwtUtils;
import com.tracejp.starnight.utils.SecurityUtils;
import com.tracejp.starnight.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/5/24 21:45
 */
@Slf4j
@RestController
@RequestMapping("/api/user")
public class LoginController extends BaseController {

    @Autowired
    private AuthenticationManager authorizationManager;

    @Autowired
    private TokenHandler tokenHandler;

    @PostMapping("/login")
    public AjaxResult login(@RequestBody AccountLoginParam loginBody) {
        Authentication authenticate = authorizationManager.authenticate(
                loginBody.getUsernamePasswordAuthenticationToken()
        );
        LoginUser details = (LoginUser) authenticate.getDetails();
        // 生成 token
        return success(tokenHandler.createToken(details));
    }

    @DeleteMapping("/logout")
    public AjaxResult logout(HttpServletRequest request) {
        String token = SecurityUtils.getToken(request);
        if (StringUtils.isNotEmpty(token)) {
            String username = JwtUtils.getUserName(token);
            log.info("用户登出，username：{}", username);
            tokenHandler.delLoginUser(token);
        }
        return success();
    }

}
