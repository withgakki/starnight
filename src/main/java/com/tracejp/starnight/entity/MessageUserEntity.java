package com.tracejp.starnight.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

import lombok.Data;

/**
 * @author traceJP
 * @since 2023-05-20 23:37:40
 */
@Data
@TableName("t_message_user")
public class MessageUserEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 自增id
	 */
	@TableId
	private Long id;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 消息内容id
	 */
	private Long messageId;

	/**
	 * 接收人id
	 */
	private Long receiveUserId;

	/**
	 * 接收人用户名
	 */
	private String receiveUserName;

	/**
	 * 接收人真实姓名
	 */
	private String receiveRealName;

	/**
	 * 是否已读
	 */
	private Boolean readed;

	/**
	 * 阅读时间
	 */
	private Date readTime;

}
