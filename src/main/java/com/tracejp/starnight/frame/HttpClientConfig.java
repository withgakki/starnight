package com.tracejp.starnight.frame;

import com.tracejp.starnight.frame.properties.ChatGPTConfigProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * <p> Http 客户端 配置 <p/>
 *
 * @author traceJP
 * @since 2023/7/16 16:52
 */
@Configuration
@EnableConfigurationProperties(ChatGPTConfigProperties.class)
public class HttpClientConfig {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        return restTemplate;
    }

}
