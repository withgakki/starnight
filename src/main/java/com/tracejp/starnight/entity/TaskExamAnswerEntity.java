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
@TableName("t_task_exam_answer")
public class TaskExamAnswerEntity {

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
	 * 创建者id
	 */
	private Long createBy;

	/**
	 * 任务id
	 */
	private Long taskExamId;

	/**
	 * 任务完成情况id
	 */
	private Long textContentId;

}
