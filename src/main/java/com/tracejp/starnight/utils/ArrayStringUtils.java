package com.tracejp.starnight.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p> 数组字符串工具 <p/>
 *
 * @author traceJP
 * @since 2023/6/1 22:30
 */
public class ArrayStringUtils {

    private static final String ANSWER_SPLIT = ",";

    /**
     * 将 List ===》 以 , 分割的字符串
     */
    public static String contentToString(List<String> contentArray) {
        return contentArray.stream().sorted().collect(Collectors.joining(ANSWER_SPLIT));
    }


    /**
     * 将 以 , 分割字符串 ===》 List
     */
    public static List<String> contentToArray(String contentArray) {
        return Arrays.asList(contentArray.split(ANSWER_SPLIT));
    }

    private static final String FORM_ANSWER_SPLIT = "_";

    /**
     * 返回以 _ 分割的最后一个数字
     */
    public static Integer lastNum(String str) {
        int start = str.lastIndexOf(FORM_ANSWER_SPLIT);
        return Integer.parseInt(str.substring(start + 1));
    }

}
