package com.tracejp.starnight.frame;


import com.aliyun.ocr_api20210707.Client;
import com.aliyun.teaopenapi.models.Config;
import com.tracejp.starnight.frame.properties.AliOcrConfigProperties;
import com.tracejp.starnight.handler.ocr.AliOcrHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p> ocr config <p/>
 *
 * @author traceJP
 * @since 2023/8/3 10:59
 */
@Configuration
public class OcrConfig {

    @Configuration
    @EnableConfigurationProperties(AliOcrConfigProperties.class)
    @ConditionalOnClass(AliOcrHandler.class)
    public static class AliOcrConfig {

        @Bean
        public Client aliOcrClient(AliOcrConfigProperties properties) throws Exception {
            Config config = new Config()
                    .setAccessKeyId(properties.getAccessKeyId())
                    .setAccessKeySecret(properties.getAccessKeySecret())
                    .setEndpoint(properties.getEndpoint());
            return new Client(config);
        }

    }

}
