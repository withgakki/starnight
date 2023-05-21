package com.tracejp.starnight.handler.file;

import java.util.Map;

/**
 * <p> FileHandler 接口 <p/>
 *
 * @author traceJP
 * @since 2023/5/2 13:16
 */
public interface IFileHandler {

    /**
     * 获取文件上传签名
     *
     * @param fileKey    文件名
     * @param params     附加参数
     * @return 签名
     */
    Map<String, String> uploadPreSign(String fileKey, Map<String, String> params);

}
