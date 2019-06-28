package com.witt.practice.aes;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * @author witt
 * @fileName AESExample
 * @date 2018/7/7 17:27
 * @description
 * @history <author>    <time>    <version>    <desc>
 */

public class AESExample {

    private static String str = "witt aes security";

    public static void jdkAES() {
        try {

            // 生成 key
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            //keyGenerator.init(128);
            keyGenerator.init(128);
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] keyBytes = secretKey.getEncoded();

            // 转换 key
            Key key = new SecretKeySpec(keyBytes,"AES");

            // 加密
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] result = cipher.doFinal(str.getBytes());
            System.out.println("jdk aes encrypt: " + Base64.encodeBase64String(result));

            // 解密
            cipher.init(Cipher.DECRYPT_MODE, key);
            result = cipher.doFinal(result);
            System.out.println("jdk aes decrypt: " + new String(result));

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
    }

    public static void main(String[] args) {
        jdkAES();

    }
}
