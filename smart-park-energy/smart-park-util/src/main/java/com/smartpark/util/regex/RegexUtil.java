package com.smartpark.util.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则工具类
 */
public class RegexUtil {

    /**
     * 是否匹配
     */
    public static boolean matches(String str, String regex) {
        return Pattern.matches(regex, str);
    }

    /**
     * 提取匹配内容
     */
    public static String extract(String str, String regex, int groupIndex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return matcher.group(groupIndex);
        }
        return null;
    }

    /**
     * 替换匹配内容
     */
    public static String replace(String str, String regex, String replacement) {
        return str.replaceAll(regex, replacement);
    }
}
