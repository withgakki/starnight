package com.tracejp.starnight.frame;

import com.tracejp.starnight.constants.ElasticSearchConstants;
import com.tracejp.starnight.frame.properties.ElasticSearchConfigProperties;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * <p> elastic search 配置 <p/>
 *
 * @author traceJP
 * @since 2023/7/19 18:05
 */
@Configuration
@EnableConfigurationProperties(ElasticSearchConfigProperties.class)
public class ElasticSearchConfig {

    /**
     * 配置 RestHighLevelClient
     */
    @Bean
    public RestHighLevelClient restHighLevelClient(ElasticSearchConfigProperties properties) throws IOException {
        HttpHost httpHost = new HttpHost(properties.getAddress(), properties.getPort(), properties.getScheme());
        RestClientBuilder builder = RestClient.builder(httpHost);
        RestHighLevelClient client = new RestHighLevelClient(builder);
        this.initGlobalIndex(client);
        return client;
    }

    /**
     * 初始化索引
     */
    private void initGlobalIndex(RestHighLevelClient client) throws IOException {
        // 用户索引
        GetIndexRequest hasUserIndex = new GetIndexRequest(ElasticSearchConstants.USER_INDEX);
        if (!client.indices().exists(hasUserIndex, RequestOptions.DEFAULT)) {
            CreateIndexRequest userIndex = new CreateIndexRequest(ElasticSearchConstants.USER_INDEX)
                    .mapping(ElasticSearchConstants.USER_INDEX_MAPPING, XContentType.JSON);
            client.indices().create(userIndex, RequestOptions.DEFAULT);
        }
    }

}
