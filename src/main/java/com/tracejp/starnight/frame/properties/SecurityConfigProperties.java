package com.tracejp.starnight.frame.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * <p> 安全配置 <p/>
 *
 * @author traceJP
 * @since 2023/5/23 17:01
 */
@Data
@ConfigurationProperties(prefix = "starnight.security")
public class SecurityConfigProperties {

    /**
     * 排除的路径
     */
    private List<String> ignores;

}
