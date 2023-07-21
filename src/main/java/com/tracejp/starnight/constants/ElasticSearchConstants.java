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
     * 试卷信息索引
     */
    public static final String EXAMPAPER_INDEX = "starnight_exampaper";

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

    /**
     * 试卷信息索引映射
     */
    public static final String EXAMPAPER_INDEX_MAPPING = "{\n" +
            "    \"properties\": {\n" +
            "      \"id\": {\n" +
            "        \"type\": \"long\"\n" +
            "      },\n" +
            "      \"createTime\": {\n" +
            "        \"type\": \"date\",\n" +
            "        \"format\": \"yyyy-MM-dd HH:mm:ss.SSS||yyyy-MM-dd HH:mm:ss||epoch_millis\"\n" +
            "      },\n" +
            "      \"createBy\": {\n" +
            "        \"type\": \"long\"\n" +
            "      },\n" +
            "      \"name\": {\n" +
            "        \"type\": \"text\"\n" +
            "      },\n" +
            "      \"subjectId\": {\n" +
            "        \"type\": \"long\"\n" +
            "      },\n" +
            "      \"paperType\": {\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"gradeLevel\": {\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"score\": {\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"questionCount\": {\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"suggestTime\": {\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"limitStartTime\": {\n" +
            "        \"type\": \"date\",\n" +
            "        \"format\": \"yyyy-MM-dd HH:mm:ss.SSS||yyyy-MM-dd HH:mm:ss||epoch_millis\"\n" +
            "      },\n" +
            "      \"limitEndTime\": {\n" +
            "        \"type\": \"date\",\n" +
            "        \"format\": \"yyyy-MM-dd HH:mm:ss.SSS||yyyy-MM-dd HH:mm:ss||epoch_millis\"\n" +
            "      }\n" +
            "    }\n" +
            "}";

}
