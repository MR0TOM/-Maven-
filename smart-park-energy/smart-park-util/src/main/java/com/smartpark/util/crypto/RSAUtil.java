package com.smartpark.util.crypto;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;

/**
 * RSA 加密工具类
 */
public class RSAUtil {

    /**
     * 生成 RSA 密钥对
     */
    public static RSA generateKeyPair() {
        return new RSA();
    }

    /**
     * 公钥加密
     */
    public static String encryptByPublicKey(String content, String publicKey) {
        RSA rsa = new RSA(null, publicKey);
        return rsa.encryptBase64(content, KeyType.PublicKey);
    }

    /**
     * 私钥解密
     */
    public static String decryptByPrivateKey(String content, String privateKey) {
        RSA rsa = new RSA(privateKey, null);
        return rsa.decryptStr(content, KeyType.PrivateKey);
    }
}
