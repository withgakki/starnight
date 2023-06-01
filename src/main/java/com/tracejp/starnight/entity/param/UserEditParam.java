package com.tracejp.starnight.entity.param;

import com.tracejp.starnight.entity.UserEntity;
import com.tracejp.starnight.entity.base.IInputConverter;
import lombok.Data;

import java.util.Date;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/5/31 21:15
 */
@Data
public class UserEditParam implements IInputConverter<UserEntity> {

    /**
     * 自增id
     */
    private Long id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别 1.男 2女
     */
    private Integer sex;

    /**
     * 生日
     */
    private Date birthDay;

    /**
     * 学生年级(1-12)
     */
    private Integer userLevel;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 角色 1.学生 2.教师 3.管理员
     */
    private Integer role;

    /**
     * 状态 1.启用 2禁用
     */
    private Integer status;

}
