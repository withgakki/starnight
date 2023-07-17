package com.tracejp.starnight.frame.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p> 百度 aip nlp 配置 <p/>
 *
 * @author traceJP
 * @since 2023/7/17 9:09
 */
@Data
@ConfigurationProperties(prefix = "starnight.nlp")
public class BaiduNlpConfigProperties {

    private String appId;

    private String apiKey;

    private String secretKey;

}
