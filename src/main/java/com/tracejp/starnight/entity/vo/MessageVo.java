package com.tracejp.starnight.entity.vo;

import lombok.Data;

import java.util.Date;

/**
 * <p> 消息 vo <p/>
 *
 * @author traceJP
 * @since 2023/7/19 16:42
 */
@Data
public class MessageVo {

    /**
     * 消息id
     */
    private Long id;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 发送者用户名
     */
    private String sendUserName;

    /**
     * 接收人数
     */
    private Integer receiveUserCount;

    /**
     * 已读人数
     */
    private Integer readCount;

    /**
     * 接收者用户名 - 以逗号分隔
     */
    private String receives;

}
