package com.tracejp.starnight.frame;

import com.baidu.aip.nlp.AipNlp;
import com.tracejp.starnight.frame.properties.BaiduNlpConfigProperties;
import com.tracejp.starnight.handler.nlp.BaiduNlpHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p> nlp 配置 <p/>
 *
 * @author traceJP
 * @since 2023/7/17 9:13
 */
@Configuration
public class NlpConfig {

    @Configuration
    @EnableConfigurationProperties(BaiduNlpConfigProperties.class)
    @ConditionalOnClass(BaiduNlpHandler.class)
    static class BaiduNlpConfig {

        @Bean
        public AipNlp aipNlp(BaiduNlpConfigProperties properties) {
            return new AipNlp(properties.getAppId(), properties.getApiKey(), properties.getSecretKey());
        }

    }


}
