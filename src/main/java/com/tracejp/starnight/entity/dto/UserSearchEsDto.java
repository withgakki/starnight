package com.tracejp.starnight.entity.dto;

import com.tracejp.starnight.entity.UserEntity;
import com.tracejp.starnight.entity.base.IOutputConverter;
import lombok.Data;

/**
 * <p> 用户搜索实体 <p/>
 *
 * @see com.tracejp.starnight.entity.UserEntity
 * @see com.tracejp.starnight.constants.ElasticSearchConstants#USER_INDEX_MAPPING
 * @author traceJP
 * @since 2023/7/20 10:58
 */
@Data
public class UserSearchEsDto implements IOutputConverter<UserSearchEsDto, UserEntity> {

    private static final long serialVersionUID = 1L;

    /**
     * 自增 id
     */
    private Long id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 手机号
     */
    private String phone;

}
