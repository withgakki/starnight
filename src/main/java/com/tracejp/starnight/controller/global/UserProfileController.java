package com.tracejp.starnight.controller.global;

import com.tracejp.starnight.controller.BaseController;
import com.tracejp.starnight.entity.base.AjaxResult;
import com.tracejp.starnight.entity.base.LoginUser;
import com.tracejp.starnight.utils.SecurityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/5/23 17:08
 */
@RestController
@RequestMapping("/api/user/profile")
public class UserProfileController extends BaseController {

    @RequestMapping("/info")
    public AjaxResult getUserInfo() {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        return success(loginUser.getUser());
    }

}
