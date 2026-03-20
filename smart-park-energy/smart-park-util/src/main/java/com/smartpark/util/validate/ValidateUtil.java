package com.smartpark.util.validate;

import cn.hutool.core.util.StrUtil;

import java.util.regex.Pattern;

/**
 * 校验工具类
 */
public class ValidateUtil {

    private static final Pattern EMAIL_PATTERN = 
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    
    private static final Pattern PHONE_PATTERN = 
            Pattern.compile("^1[3-9]\\d{9}$");

    /**
     * 校验邮箱格式
     */
    public static boolean isEmail(String email) {
        return StrUtil.isNotBlank(email) && EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * 校验手机号格式
     */
    public static boolean isPhone(String phone) {
        return StrUtil.isNotBlank(phone) && PHONE_PATTERN.matcher(phone).matches();
    }

    /**
     * 校验字符串是否为空
     */
    public static boolean isEmpty(String str) {
        return StrUtil.isBlank(str);
    }

    /**
     * 校验字符串是否不为空
     */
    public static boolean isNotEmpty(String str) {
        return StrUtil.isNotBlank(str);
    }
}
