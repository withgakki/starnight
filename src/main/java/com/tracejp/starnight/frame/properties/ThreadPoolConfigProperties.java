package com.tracejp.starnight.frame.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p> Yozu线程池配置 <p/>
 *
 * @author traceJP
 * @since 2023/4/25 16:45
 */
@Data
@ConfigurationProperties(prefix = "starnight.thread.pool")
public class ThreadPoolConfigProperties {

    /**
     * 核心线程数量
     */
    private Integer corePoolSize = 50;

    /**
     * 最大线程数量
     */
    private Integer maximumPoolSize = 200;

    /**
     * 额外线程空闲时间
     */
    private Integer keepAliveTime = 10;

    /**
     * 阻塞队列大小
     */
    private Integer blockingQueueSize = 1000;

}
