package com.tracejp.starnight.handler.file;

import cn.hutool.core.date.DateUtil;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.tracejp.starnight.frame.properties.FileConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

/**
 * <p> minio 实现类 <p/>
 *
 * @author traceJP
 * @since 2023/5/2 13:21
 */
@Primary
@Component
public class MinioFileHandler implements IFileHandler {

    private static final int PRE_SIGN_URL_EXPIRE = 60 * 1000;

    @Autowired
    private AmazonS3 fileClient;

    @Autowired
    private FileConfigProperties configProperties;


    @Override
    public Map<String, String> uploadPreSign(String fileKey, Map<String, String> params) {
        Date currentDate = new Date();
        Date expireDate = DateUtil.offsetMillisecond(currentDate, PRE_SIGN_URL_EXPIRE);
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(configProperties.getBucketName(), fileKey)
                .withExpiration(expireDate)
                .withMethod(HttpMethod.PUT);
        if (params != null) {
            params.forEach(request::addRequestParameter);
        }
        URL url = fileClient.generatePresignedUrl(request);
        return Collections.singletonMap("url", url.toString());
    }

}
