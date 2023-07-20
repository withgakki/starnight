package com.tracejp.starnight.controller.global;

import com.tracejp.starnight.controller.BaseController;
import com.tracejp.starnight.entity.UserEntity;
import com.tracejp.starnight.entity.base.AjaxResult;
import com.tracejp.starnight.entity.base.LoginUser;
import com.tracejp.starnight.entity.enums.RoleEnum;
import com.tracejp.starnight.entity.enums.UserStatusEnum;
import com.tracejp.starnight.entity.param.AccountLoginParam;
import com.tracejp.starnight.entity.param.RegisterParam;
import com.tracejp.starnight.handler.token.TokenHandler;
import com.tracejp.starnight.service.UserEventLogService;
import com.tracejp.starnight.service.UserService;
import com.tracejp.starnight.utils.JwtUtils;
import com.tracejp.starnight.utils.SecurityUtils;
import com.tracejp.starnight.utils.StringUtils;
import com.tracejp.starnight.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/5/24 21:45
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class LoginController extends BaseController {

    @Autowired
    private AuthenticationManager authorizationManager;

    @Autowired
    private TokenHandler tokenHandler;

    @Autowired
    private UserService userService;

    @Autowired
    private UserEventLogService userEventLogService;

    @PostMapping("/login")
    public AjaxResult login(@Valid @RequestBody AccountLoginParam loginBody) {
        Authentication authenticate = authorizationManager.authenticate(
                loginBody.getUsernamePasswordAuthenticationToken()
        );
        LoginUser details = (LoginUser) authenticate.getDetails();

        // 日志记录
        userEventLogService.saveAsync(details.getUser(), "登录了星空在线考试平台");

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

    @PostMapping("/register/student")
    public AjaxResult register(@RequestBody RegisterParam registerBody) {
        UserEntity byUserName = userService.getByUserName(registerBody.getUserName());
        if (byUserName != null) {
            return error("用户名已存在");
        }
        UserEntity user = registerBody.convertTo();
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        user.setUserUuid(UUIDUtils.randomUUID().toString());
        user.setStatus(UserStatusEnum.Enable.getCode());
        user.setRole(RoleEnum.STUDENT.getCode());
        userService.saveToAll(user);
        return success();
    }

}
