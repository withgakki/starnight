package com.tracejp.starnight.entity.vo.student;

import lombok.Data;

import java.util.Date;

/**
 * <p> 用户消息 vo <p/>
 *
 * @author traceJP
 * @since 2023/7/19 17:42
 */
@Data
public class MessageUserVo {

    /**
     * 自增 id
     */
    private Integer id;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 消息内容 id
     */
    private Integer messageId;

    /**
     * 是否已读
     */
    private Boolean readed;

    /**
     * 发送者用户名
     */
    private String sendUserName;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

}
