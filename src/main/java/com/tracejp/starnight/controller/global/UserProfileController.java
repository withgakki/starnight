package com.tracejp.starnight.controller.global;

import com.tracejp.starnight.controller.BaseController;
import com.tracejp.starnight.entity.UserEntity;
import com.tracejp.starnight.entity.base.AjaxResult;
import com.tracejp.starnight.entity.base.LoginUser;
import com.tracejp.starnight.entity.dto.UserDto;
import com.tracejp.starnight.entity.param.UpdatePwdParam;
import com.tracejp.starnight.entity.param.UserProfileParam;
import com.tracejp.starnight.handler.file.IFileHandler;
import com.tracejp.starnight.handler.token.TokenHandler;
import com.tracejp.starnight.service.UserService;
import com.tracejp.starnight.utils.BeanUtils;
import com.tracejp.starnight.utils.SecurityUtils;
import com.tracejp.starnight.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

import static com.tracejp.starnight.constants.Constants.AVATAR_FILE_SIZE;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/5/23 17:08
 */
@RestController
@RequestMapping("/user/profile")
public class UserProfileController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenHandler tokenHandler;

    @Autowired
    private IFileHandler fileHandler;


    @GetMapping
    public AjaxResult profile() {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        UserDto user = new UserDto().convertFrom(loginUser.getUser());
        return success(user);
    }

    /**
     * 修改用户
     */
    @PutMapping
    public AjaxResult updateProfile(@RequestBody UserProfileParam user) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        UserEntity userEntity = loginUser.getUser();

        // 更新数据库
        UserEntity userProfile = user.convertTo();
        userProfile.setId(userEntity.getId());
        userProfile.setUpdateTime(new Date());
        if (userService.updateById(userProfile)) {
            // 更新缓存
            BeanUtils.updateProperties(userProfile, userEntity);
            tokenHandler.setLoginUser(loginUser);
            return success();
        }
        return error("修改个人信息异常，请联系管理员");
    }

    /**
     * 重置密码
     */
    @PutMapping("/updatePwd")
    public AjaxResult updatePwd(@RequestBody UpdatePwdParam param) {
        String oldPassword = param.getOldPassword();
        String newPassword = param.getNewPassword();

        String username = SecurityUtils.getUsername();
        UserEntity user = userService.getByUserName(username);
        String password = user.getPassword();
        if (!SecurityUtils.matchesPassword(oldPassword, password)) {
            return error("修改密码失败，旧密码错误");
        }
        if (SecurityUtils.matchesPassword(newPassword, password)) {
            return error("新密码不能与旧密码相同");
        }

        // 更新数据库
        String encodePassword = SecurityUtils.encryptPassword(newPassword);
        user.setUpdateTime(new Date());
        user.setPassword(encodePassword);
        if (userService.updateById(user)) {
            // 更新缓存
            LoginUser loginUser = SecurityUtils.getLoginUser();
            loginUser.setUser(user);
            tokenHandler.setLoginUser(loginUser);
            return success();
        }
        return error("修改密码异常，请联系管理员");
    }

    /**
     * 头像上传
     */
    @PostMapping("/avatar")
    public AjaxResult avatar(@RequestParam("avatarfile") MultipartFile file) throws Exception {
        if (!file.isEmpty()) {

            if (file.getSize() > AVATAR_FILE_SIZE) {
                return error("上传图片大小不能超过" + AVATAR_FILE_SIZE / 1024 / 1024 + "M");
            }

            LoginUser loginUser = SecurityUtils.getLoginUser();
            // 上传
            String fileUrl = fileHandler.uploadFile(file);
            if (StringUtils.isEmpty(fileUrl)) {
                return error("文件上传异常，请联系管理员");
            }
            // 更新数据库
            UserEntity userEntity = loginUser.getUser();
            userEntity.setUpdateTime(new Date());
            userEntity.setAvatarPath(fileUrl);
            if (userService.updateById(userEntity)) {
                // 更新缓存
                loginUser.setUser(userEntity);
                tokenHandler.setLoginUser(loginUser);
                return success().put("imgUrl", fileUrl);
            }
        }
        return error("上传图片异常，请联系管理员");
    }

}
