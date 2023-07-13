package com.tracejp.starnight.entity.param;

import com.tracejp.starnight.entity.UserEntity;
import com.tracejp.starnight.entity.base.IInputConverter;
import lombok.Data;

/**
 * <p> 注册表单 <p/>
 *
 * @author traceJP
 * @since 2023/7/13 10:26
 */
@Data
public class RegisterParam implements IInputConverter<UserEntity> {

    private Integer userLevel;

    private String userName;

    private String password;

}
