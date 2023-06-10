package com.tracejp.starnight.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p> HTML 工具 <p/>
 *
 * @author traceJP
 * @since 2023/6/8 19:08
 */
public class HtmlUtils {

    private static final String REG_EX_SCRIPT = "<script[^>]*?>[\\s\\S]*?<\\/script>";
    private static final String REG_EX_STYLE = "<style[^>]*?>[\\s\\S]*?<\\/style>";
    private static final String REG_EX_HTML = "<[^>]+>";

    public static String clear(String htmlStr) {
        Pattern pScript = Pattern.compile(REG_EX_SCRIPT, Pattern.CASE_INSENSITIVE);
        Matcher mScript = pScript.matcher(htmlStr);
        htmlStr = mScript.replaceAll("");
        Pattern pStyle = Pattern.compile(REG_EX_STYLE, Pattern.CASE_INSENSITIVE);
        Matcher mStyle = pStyle.matcher(htmlStr);
        htmlStr = mStyle.replaceAll("");
        Pattern pHtml = Pattern.compile(REG_EX_HTML, Pattern.CASE_INSENSITIVE);
        Matcher mHtml = pHtml.matcher(htmlStr);
        htmlStr = mHtml.replaceAll("");
        return htmlStr.trim();
    }

}
