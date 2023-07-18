package com.tracejp.starnight.exception;

/**
 * <p> 第三方服务响应错误异常 <p/>
 *
 * @author traceJP
 * @since 2023/7/17 17:59
 */
public class ThirdPartyResponseException extends AbsBaseException {

    public ThirdPartyResponseException(String message) {
        super(message);
    }

}
