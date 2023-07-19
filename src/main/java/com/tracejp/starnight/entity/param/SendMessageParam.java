package com.tracejp.starnight.entity.param;

import lombok.Data;

import java.util.List;

/**
 * <p> 发送消息参数 <p/>
 *
 * @author traceJP
 * @since 2023/7/19 16:25
 */
@Data
public class SendMessageParam {

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 接收者 ids
     */
    private List<Integer> receiveUserIds;

}
