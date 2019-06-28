package com.witt.practice.hm;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

/**
 * @author witt
 * @fileName DESCrypt
 * @date 2018/7/8 13:28
 * @description
 * @history <author>    <time>    <version>    <desc>
 */

public class DESCrypt {

    public static void main(String[] args) {
        String str = "柴柴才是世界上最可爱的狗子";
        String password = "12345678"; // des 要求秘钥长度为8位

        String encrypt = encrypt(str, password);
        System.out.println(encrypt);
        String decryptString = decrypt(encrypt, password);
        System.out.println(decryptString);
    }

    /**
     * 工作模式介绍
     *
     * ECB （电子密码本）
     * 每块独立加密；
     * 优点是分块可以并行计算；
     * 缺点是同样的原文得到相同的密文，容易被攻击
     *
     * CBC （密码分组链接）
     * 密码分组链接；
     * 每块加密依赖于前一块的密文；
     * 优点是同样的原文得到不同的密文，原文的微小改动影响后面全部密文；
     * 缺点是加密需要串行处理，误差会传递
     *
     * CBC模式 初始化时需要额外的参数
     * IvParameterSpec iv = new IvParameterSpec(password.getBytes());
     * cipher.init(Cipher.ENCRYPT_MODE, key, iv);
     *
     * */

    /**
     * 默认 工作模式/填充模式 就是 ECB/PKCS5Padding
     * */
    public static final String TRANSFORMATION = "DES/ECB/PKCS5Padding"; // 算法/工作模式/填充模式
    public static final String ALGORITHM = "DES"; // 算法

    public static String encrypt(String input,String password){
        try {
            // 1. 创建 cipher 对象
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);

            // 2 初始化模式
            // 2.1 创建秘钥工厂
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            // 2.2 转换秘钥规则
            KeySpec keySpec = new DESKeySpec(password.getBytes());
            // 2.3 生成秘钥对象
            Key key = secretKeyFactory.generateSecret(keySpec);
            // 2.4 初始化加密模式
            cipher.init(Cipher.ENCRYPT_MODE, key);

            // 3. 加密
            byte[] encryptBytes = cipher.doFinal(input.getBytes());

            // 编码输出
            return Base64.getEncoder().encodeToString(encryptBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String input,String password){
        try {
            // 1. 创建 cipher 对象
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);

            // 2 初始化模式
            // 2.1 创建秘钥工厂
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            // 2.2 转换秘钥规则
            KeySpec keySpec = new DESKeySpec(password.getBytes());
            // 2.3 生成秘钥对象
            Key key = secretKeyFactory.generateSecret(keySpec);
            // 2.4 初始化解密模式
            cipher.init(Cipher.DECRYPT_MODE, key);

            // 3. 解密
            byte[] decryptBytes = cipher.doFinal(Base64.getDecoder().decode(input));

            // 转为字符串输出
            return new String(decryptBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }
}
