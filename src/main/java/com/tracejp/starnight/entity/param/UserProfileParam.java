package com.tracejp.starnight.entity.param;

import com.tracejp.starnight.entity.UserEntity;
import com.tracejp.starnight.entity.base.IInputConverter;
import lombok.Data;

import java.util.Date;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/5/30 20:45
 */
@Data
public class UserProfileParam implements IInputConverter<UserEntity> {

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 手机号
     */
    private String phone;

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

}
