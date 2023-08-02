package com.tracejp.starnight.handler.nlp;

import com.baidu.aip.nlp.AipNlp;
import com.tracejp.starnight.exception.ThirdPartyResponseException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/7/17 9:15
 */
@Slf4j
@Primary
@Component
public class BaiduNlpHandler implements INlpHandler {

    @Autowired
    private AipNlp client;

    private static final String RESULT_SCORE_KEY = "score";

    @Retryable(value = ThirdPartyResponseException.class, recover = "simnetRecover")
    @Override
    public Double simnet(String text1, String text2) {
        // noinspection VulnerableCodeUsages
        JSONObject result = client.simnet(text1, text2, new HashMap<>());
        log.info("simnet result: {}", result);
        try {
            return result.getDouble(RESULT_SCORE_KEY);
        } catch (Exception e) {
            log.error("simnet error: {}", result);
            throw new ThirdPartyResponseException("百度自然语言处理接口响应异常");
        }
    }

    @Recover
    private Double simnetRecover(ThirdPartyResponseException e, String text1, String text2) {
        log.error("simnet recover: {}; text1: {}; text2: {}", e.getMessage(), text1, text2);
        return 0.0;
    }

}
