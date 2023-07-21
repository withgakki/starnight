package com.tracejp.starnight.constants;

/**
 * <p> elastic search 常量类 <p/>
 *
 * @author traceJP
 * @since 2023/7/20 10:25
 */
public class ElasticSearchConstants {

    /**
     * 用户信息索引
     */
    public static final String USER_INDEX = "starnight_user";

    /**
     * 用户信息索引映射
     */
    public static final String USER_INDEX_MAPPING = "{\n" +
            "  \"properties\": {\n" +
            "    \"id\": {\n" +
            "      \"type\": \"long\"\n" +
            "    },\n" +
            "    \"userName\": {\n" +
            "      \"type\": \"keyword\"\n" +
            "    },\n" +
            "    \"realName\": {\n" +
            "      \"type\": \"keyword\"\n" +
            "    },\n" +
            "    \"phone\": {\n" +
            "      \"type\": \"text\"\n" +
            "    }\n" +
            "  }\n" +
            "}";

}
