package com.tracejp.starnight.utils;

/**
 * <p> 分数转换工具 <p/>
 *
 * @author traceJP
 * @since 2023/6/1 22:38
 */
public class ScoreUtils {

    /**
     * 千分制分数 ===》 字符串分数
     */
    public static String scoreToVM(Integer score) {
        if (score % 10 == 0) {
            return String.valueOf(score / 10);
        } else {
            return String.format("%.1f", score / 10.0);
        }
    }

    /**
     * 字符串分数 ===》 千分制分数
     */
    public static Integer scoreFromVM(String score) {
        if (score == null) {
            return null;
        } else {
            return (int) (Float.parseFloat(score) * 10);
        }
    }

}
