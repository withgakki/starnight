package com.tracejp.starnight.frame.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p> chat-gpt 配置 <p/>
 *
 * @author traceJP
 * @since 2023/7/16 16:54
 */
@Data
@ConfigurationProperties(prefix = "starnight.chat-gpt")
public class ChatGPTConfigProperties {

    /**
     * 请求地址
     */
    private String apiUrl;

    /**
     * 请求密钥
     */
    private String apiKey;

}
