package com.tracejp.starnight.handler.gpt;

import java.util.List;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/7/16 16:58
 */
public interface IGPTHandler {

    /**
     * 聊天
     * @param prompts 提示内容
     * @return 响应内容
     */
    String chat(String... prompts);

    String chat(List<String> prompts);

}
