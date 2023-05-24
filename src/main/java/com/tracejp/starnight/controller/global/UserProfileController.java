package com.tracejp.starnight.controller.global;

import com.tracejp.starnight.controller.BaseController;
import com.tracejp.starnight.entity.base.AjaxResult;
import com.tracejp.starnight.entity.base.LoginUser;
import com.tracejp.starnight.entity.param.AccountLoginParam;
import com.tracejp.starnight.handler.token.TokenHandler;
import com.tracejp.starnight.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/5/23 17:08
 */
@RestController
@RequestMapping("/api/user")
public class UserProfileController extends BaseController {

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
        return AjaxResult.success(tokenHandler.createToken(details));
    }

    // profile
    @GetMapping
    public AjaxResult test() {

        System.out.println(SecurityUtils.getUserId());
        System.out.println(SecurityUtils.getUsername());
        System.out.println(SecurityUtils.getLoginUser());

        return success("test");
    }

}
