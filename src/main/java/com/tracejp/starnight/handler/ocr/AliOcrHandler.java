package com.tracejp.starnight.handler.ocr;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.aliyun.ocr_api20210707.Client;
import com.aliyun.ocr_api20210707.models.RecognizeHandwritingRequest;
import com.aliyun.ocr_api20210707.models.RecognizeHandwritingResponse;
import com.tracejp.starnight.exception.ThirdPartyResponseException;
import com.tracejp.starnight.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/8/2 22:55
 */
@Slf4j
@Primary
@Component
public class AliOcrHandler implements IOcrHandler {

    private static final String CONTENT_KEY = "content";

    @Autowired
    private Client client;

    @Retryable(value = ThirdPartyResponseException.class, recover = "ocrRecover")
    @Override
    public String ocr(String url) {
        if (StringUtils.isEmpty(url)) {
            return null;
        }
        RecognizeHandwritingRequest request = new RecognizeHandwritingRequest()
                .setUrl(url);
        try {
            RecognizeHandwritingResponse response = client.recognizeHandwriting(request);
            if (response.getStatusCode() == 200) {
                String jsonData = response.getBody().getData();
                JSONObject jsonObject = JSON.parseObject(jsonData);
                return jsonObject.getString(CONTENT_KEY);
            }
            return null;
        } catch (Exception e) {
            log.error("阿里云OCR接口调用失败，原因：{}", e.getMessage());
            throw new ThirdPartyResponseException(e.getMessage());
        }
    }

    @Recover
    private String ocrRecover(ThirdPartyResponseException e, String url) {
        log.error("ocr recover: {}; url: {}", e.getMessage(), url);
        return null;
    }

}
