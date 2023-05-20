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
@TableName("t_subject")
public class SubjectEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 自增id
	 */
	@TableId
	private Long id;

	/**
	 * 删除标志位
	 */
	private Boolean delFlag;

	/**
	 * 学科名
	 */
	private String name;

	/**
	 * 年级
	 */
	private Integer level;

	/**
	 * 年级名
	 */
	private String levelName;

	/**
	 * 排序字段
	 */
	private Integer itemOrder;

}
