package com.tracejp.starnight.entity.param;

import lombok.Data;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/7/2 22:46
 */
@Data
public class UpdatePwdParam {

    private String oldPassword;

    private String newPassword;

}
