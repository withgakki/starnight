package com.tracejp.starnight.handler.gpt;

import com.alibaba.fastjson2.JSON;
import com.tracejp.starnight.exception.ThirdPartyResponseException;
import com.tracejp.starnight.frame.properties.ChatGPTConfigProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/7/16 16:58
 */
@Slf4j
@Primary
@Component
public class ChatGPTGPTHandler implements IGPTHandler {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ChatGPTConfigProperties properties;

    @Override
    public String chat(String... prompts) {
        HttpEntity<Request> request = buildRequestEntity(prompts);
        String responseJSON = restTemplate.postForObject(properties.getApiUrl(), request, String.class);
        try {
            Result result = JSON.parseObject(responseJSON, Result.class);
            if (result == null) {
                throw new ThirdPartyResponseException("GPT-3 API 响应异常");
            }
            List<Message> resultMessages = result.getChoices().get(0).getMessage();
            return resultMessages.get(0).getContent();
        } catch (Exception e) {
            log.error("GPT-3 API 响应异常", e);
            throw new ThirdPartyResponseException(e.getMessage());
        }
    }

    @Override
    public String chat(List<String> prompts) {
        return chat(prompts.toArray(new String[0]));
    }

    private HttpEntity<Request> buildRequestEntity(String... prompts) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + properties.getApiKey());
        headers.setContentType(MediaType.APPLICATION_JSON);
        Request request = new Request();
        request.setModel(properties.getModel());
        List<Message> messages = Arrays.stream(prompts).map(prompt -> new Message("user", prompt))
                .collect(Collectors.toList());
        request.setMessages(messages);
        return new HttpEntity<>(request, headers);
    }

    /**
     * 请求参数封装类 - GPT 3.5
     */
    @Data
    private static final class Request {

        private String model;

        private List<Message> messages;

        private Integer max_tokens = 1024;

        private Integer temperature = 1;

        private Integer top_p = 1;

        private Integer n = 1;

        private Boolean stream = false;

    }

    /**
     * 响应结果封装类
     */
    @Data
    private static final class Result {

        private String id;

        private String object;

        private String created;

        private String model;

        private List<Choices> choices;

        private Usage usage;

        @Data
        private static final class Choices {

            private String index;

            private List<Message> message;

            private String finishReason;

        }

        @Data
        private static final class Usage {

            private Integer promptTokens;

            private Integer completionTokens;

            private Integer totalTokens;

        }

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class Message {

        private String role;

        private String content;

    }

}
