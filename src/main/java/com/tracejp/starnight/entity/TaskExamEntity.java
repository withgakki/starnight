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
@TableName("t_task_exam")
public class TaskExamEntity {

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
	 * 创建者id
	 */
	private Long createBy;

	/**
	 * 删除标志位
	 */
	private Boolean delFlag;

	/**
	 * 任务标题
	 */
	private String title;

	/**
	 * 年级
	 */
	private Integer gradeLevel;

	/**
	 * 任务内容id
	 */
	private Long frameTextContentId;

	/**
	 * 创建者用户名
	 */
	private String createUserName;

}
