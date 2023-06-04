package com.tracejp.starnight.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

import lombok.Data;

/**
 * @author traceJP
 * @since 2023-05-20 23:37:40
 */
@Data
@TableName("t_user_token")
public class UserTokenEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 自增id
	 */
	@TableId
	private Long id;

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	/**
	 * 令牌
	 */
	private String token;

	/**
	 * 用户id
	 */
	private Long userId;

	/**
	 * 微信openId
	 */
	private String wxOpenId;

	/**
	 * 令牌过期时间
	 */
	private Date endTime;

	/**
	 * 用户名
	 */
	private String userName;

}
