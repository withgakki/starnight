package com.tracejp.starnight.frame;

import com.tracejp.starnight.frame.properties.ElasticSearchConfigProperties;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p> elastic search 配置 <p/>
 *
 * @author traceJP
 * @since 2023/7/19 18:05
 */
@Configuration
@EnableConfigurationProperties(ElasticSearchConfigProperties.class)
public class ElasticSearchConfig {

    @Bean
    public RestHighLevelClient restHighLevelClient(ElasticSearchConfigProperties properties) {
        HttpHost httpHost = new HttpHost(properties.getAddress(), properties.getPort(), properties.getScheme());
        RestClientBuilder builder = RestClient.builder(httpHost);
        return new RestHighLevelClient(builder);
    }

}
