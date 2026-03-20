package com.smartpark.util.crypto;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;

import java.nio.charset.StandardCharsets;

/**
 * AES 加密工具类
 */
public class AESUtil {

    private static final String DEFAULT_KEY = "SmartParkEnergy2024";

    /**
     * 加密
     */
    public static String encrypt(String content, String key) {
        AES aes = SecureUtil.aes(key.getBytes(StandardCharsets.UTF_8));
        return aes.encryptBase64(content);
    }

    /**
     * 加密（使用默认密钥）
     */
    public static String encrypt(String content) {
        return encrypt(content, DEFAULT_KEY);
    }

    /**
     * 解密
     */
    public static String decrypt(String content, String key) {
        AES aes = SecureUtil.aes(key.getBytes(StandardCharsets.UTF_8));
        return aes.decryptStr(content);
    }

    /**
     * 解密（使用默认密钥）
     */
    public static String decrypt(String content) {
        return decrypt(content, DEFAULT_KEY);
    }
}
