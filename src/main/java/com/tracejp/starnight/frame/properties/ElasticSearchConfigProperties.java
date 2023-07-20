package com.tracejp.starnight.frame.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p> elastic search 配置属性 <p/>
 *
 * @author traceJP
 * @since 2023/7/19 18:05
 */
@Data
@ConfigurationProperties(prefix = "elasticsearch")
public class ElasticSearchConfigProperties {

    /**
     * 地址
     */
    private String address;

    /**
     * 端口
     */
    private Integer port = 9200;

    /**
     * 协议
     */
    private String scheme = "http";

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

}
