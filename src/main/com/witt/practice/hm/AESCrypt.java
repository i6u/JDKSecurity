package com.witt.practice.hm;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * @author witt
 * @fileName AESCrypt
 * @date 2018/7/8 14:15
 * @description
 * @history <author>    <time>    <version>    <desc>
 */
public class AESCrypt {

    public static void main(String[] args) {
        String str = "{\"str\":\"abc.test.com\",\"code\":123456}";
        String seed = "123456ABC!";
        String encrypt = encrypt(str, seed);
        System.out.println(encrypt);
        String decryptString = decrypt(encrypt, seed);
        System.out.println(decryptString);
    }

    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding"; // 算法/工作模式/填充模式
    private static final String ALGORITHM = "AES";

    public static String encrypt(String input, String password) {

        try {
            // 1. 创建 cipher 对象
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);

            // 2. 初始化加密模式
            // 2.1 通过秘钥规则类创建秘钥对象
            Key key = getSecretKey(password);
            // 2.2 初始化加密模式
            cipher.init(Cipher.ENCRYPT_MODE, key);

            // 3. 加密
            byte[] encryptBytes = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));

            // Base64 编码输出
            return Base64.getEncoder().encodeToString(encryptBytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String decrypt(String input, String password) {

        try {
            // 1. 创建 cipher 对象
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);

            // 2. 初始化加密模式
            // 2.1 创建秘钥对象
            Key key = getSecretKey(password);
            // 2.2 初始化解密模式
            cipher.init(Cipher.DECRYPT_MODE, key);

            // 3. 解密
            byte[] decryptBytes = cipher.doFinal(Base64.getDecoder().decode(input));

            // 转为字符串输出
            return new String(decryptBytes, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 生成加密密匙
     */
    private static SecretKey getSecretKey(String seed) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(seed.getBytes(StandardCharsets.UTF_8));
        keyGenerator.init(256, random);
        return new SecretKeySpec(keyGenerator.generateKey().getEncoded(), ALGORITHM);
    }
}
