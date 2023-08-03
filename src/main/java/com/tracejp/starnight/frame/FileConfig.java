package com.tracejp.starnight.frame;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.tracejp.starnight.frame.properties.FileConfigProperties;
import com.tracejp.starnight.handler.file.MinioFileHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p> 文件处理器配置 <p/>
 *
 * @author traceJP
 * @since 2023/5/21 19:37
 */
@Configuration
public class FileConfig {

    @Configuration
    @EnableConfigurationProperties(FileConfigProperties.class)
    @ConditionalOnClass(MinioFileHandler.class)
    public static class MinioFileConfig {

        @Bean
        public AmazonS3 amazonS3Client(FileConfigProperties properties) {
            ClientConfiguration config = new ClientConfiguration();
            config.setProtocol(Protocol.HTTP);
            config.setConnectionTimeout(5000);
            config.setUseExpectContinue(true);
            AWSCredentials credentials = new BasicAWSCredentials(properties.getAccessKey(), properties.getSecretKey());
            AwsClientBuilder.EndpointConfiguration endPoint =
                    new AwsClientBuilder.EndpointConfiguration(properties.getUrl(), Regions.CN_NORTH_1.name());

            return AmazonS3ClientBuilder.standard()
                    .withClientConfiguration(config)
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .withEndpointConfiguration(endPoint)
                    .withPathStyleAccessEnabled(true).build();
        }

    }

}
