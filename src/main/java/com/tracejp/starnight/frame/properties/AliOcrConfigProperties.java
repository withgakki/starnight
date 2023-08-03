package com.tracejp.starnight.frame.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/8/3 10:59
 */
@Data
@ConfigurationProperties(prefix = "starnight.ocr")
public class AliOcrConfigProperties {

    /**
     * 阿里云账号的AccessKey ID
     */
    private String accessKeyId;

    /**
     * 阿里云账号的AccessKey Secret
     */
    private String accessKeySecret;

    /**
     * 阿里云账号的Endpoint
     */
    private String endpoint;

}
