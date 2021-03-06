package com.witt.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * @author witt
 * @fileName AESUtil
 * @date 2018/7/8 19:26
 * @description aes 加密工具类
 * @history <author>    <time>    <version>    <desc>
 */

public class AESUtil {

    public static void main(String[] args) {
        String str = "{\"str\":\"drc.oschina.net\"}";
        String seed = "123456ABC!";
        String encrypt = encrypt(str, seed);
        System.out.println(encrypt);
        String decrypt = decrypt(encrypt, seed);
        System.out.println(decrypt);
    }

    private static final String ALGORITHM = "AES";

    /**
     * @Description: 加密
     */
    public static String encrypt(String content, String seed) {
        try {
            SecretKey secretKey = getSecretKey(seed);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptBytes = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Description: 解密
     */
    public static String decrypt(String encryptContent, String seed) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKey secretKey = getSecretKey(seed);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] encryptBytes = Base64.getDecoder().decode(encryptContent);
            byte[] decryptBytes = cipher.doFinal(encryptBytes);
            return new String(decryptBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Description: 生成加密密匙
     */
    private static SecretKey getSecretKey(String seed) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(seed.getBytes(StandardCharsets.UTF_8));
        keyGenerator.init(128, random);
        return new SecretKeySpec(keyGenerator.generateKey().getEncoded(), ALGORITHM);
    }

}
