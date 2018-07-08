package com.witt.utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
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
        String str = "柴柴才是世界上最可爱的狗子";
        String password = "1234567890abcdef"; // aes 要求秘钥长度为16位

        String encrypt = encrypt(str, password);
        System.out.println(encrypt);
        String decryptString = decrypt(encrypt, password);
        System.out.println(decryptString);
    }

    public static final String TRANSFORMATION = "AES/ECB/PKCS5Padding"; // 算法/工作模式/填充模式
    public static final String ALGORITHM = "AES";

    public static String encrypt(String input, String password) {

        try {
            // 1. 创建 cipher 对象
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);

            // 2. 初始化加密模式
            // 2.1 通过秘钥规则类创建秘钥对象
            SecretKeySpec key = new SecretKeySpec(password.getBytes(), ALGORITHM);
            // 2.2 初始化加密模式
            cipher.init(Cipher.ENCRYPT_MODE,key);

            // 3. 加密
            byte[] encryptBytes = cipher.doFinal(input.getBytes());

            // Base64编码输出
            return Base64.getEncoder().encodeToString(encryptBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
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
            Key key = new SecretKeySpec(password.getBytes(), ALGORITHM);
            // 2.2 初始化解密模式
            cipher.init(Cipher.DECRYPT_MODE,key);

            // 3. 解密
            byte[] decryptBytes = cipher.doFinal(Base64.getDecoder().decode(input));

            // 转为字符串输出
            return new String(decryptBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        return null;
    }
}
