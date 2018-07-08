package com.witt.utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author witt
 * @fileName RSACrypt
 * @date 2018/7/8 16:19
 * @description
 * @history <author>    <time>    <version>    <desc>
 */

public class RSACrypt {

    public static final String ALGORITHM = "RSA";

    /**
     * https://blog.csdn.net/LVXIANGAN/article/details/45487943
     *
     * (RSA/ECB/PKCS1Padding (1024, 2048))
     * 关于每次加密块的最大长度问题
     * 如果是 1024 bit，那么铭文长度就应该是 1024 / 8 -11 = 117
     * 如果是 2048 bit，那么铭文长度就应该是 2048 / 8 -11 = 245
     *
     * 解密密文
     * 如果是 1024 bit，那么铭文长度就应该是 1024 / 8 = 128
     * 如果是 2048 bit，那么铭文长度就应该是 2048 / 8 = 256
     */
    public static void main(String[] args) {
        // RSA 加密，每次加密的铭文有长度限制，可以采用分段加密解决这个问题
        String str = "喜欢我的猪喜欢我的猪喜欢我的猪喜欢我的猪" +
                     "喜欢我的猪喜欢我的猪喜欢我的猪喜欢我的猪" +
                     "喜欢我的猪喜欢我的猪喜欢我的猪喜欢我的猪" +
                     "喜欢我的猪喜欢我的猪喜欢我的猪喜欢我的猪" +
                     "123456";
        System.out.println(str.getBytes().length);
        try {
            /* 实际开发中不肯每次都生成密钥，都是使用保存起来的密钥

            // 秘钥对生成器
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
            // 秘钥对称对象
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            // 生成公钥/私钥
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();
            System.out.println("私钥："+Base64.getEncoder().encodeToString(privateKey.getEncoded()));
            System.out.println("公钥："+Base64.getEncoder().encodeToString(publicKey.getEncoded()));

            */

            // 使用保存期来的密钥
            String privateStr = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDAtQsCO60/NExf4fq8CLnVuKA3BYKw5u0/pfUR9gWAYdsyM8oNZFRsICfq39+eGqG2u8ID9aGsxi6q5zqHGhXFvGSYjIJvAeJrhlxehru2DhiTFF9sGg3SO5kwJczVLctwmuLoL6cb1x5e49VsQxNLiILY4I/Bb8YHZrZm3GrAMQKQoAUSFtv2xQSO7Bo/4yZkk2/iyV0NwjAlHXtg8+cp1snQ7hG4wyCIkJInrnq3ZvdvN8fAgslKygW9q6ma0hOeLRv7qj7QUj4s/599TiNTsbQwigfy1O7lwKzlhYy8h0/bVT0l3akNE+mqYdvbhKlDZiONafM7/06+SiP9WNDbAgMBAAECggEBAKNkHYxsKu2PGz6k7SInR+HFI9rjCjeR5RD1VoA/F9UNo3vCW4vVSJtQ/GdyIDj+naTBN7SBvO4wLvtrCL3qSRGaNbT+Vc1nTKIcjw76sAehlgaG3I1c3eEl9THUqwMJTBgSy1dQqCC72Z1dEeU0ThTaDw9x/5FkgwRde0RlmQGQUz3DtZUU/zSl6YNLZFech+qYMMUPZ5bFh7zOMuSSLhcgRpJERsBOipaNAN3xKiHa2Iezha0zagztuazi7LBebOsuEYo3k7BsOcZGjGZjd6wU+38N8wGv0WxmlL+V90PE0sCjZ0UdyhniDPxp0OODYGW2yNlsh926LlZ2YbXzegkCgYEA6uyBBq3YWdSJ1Ym6UYZyQ2KUcKNrCUo0PpkvR7WaWdpBZD5A8osj/RPagWKQ0gtVvMNWVKCT0H7v6Qp6fGs+moG+ThmgxUaexMfx8tadIpqwF5O3Wm781gF+KPqUdblc9PhNUkZEK9PrzxoZdE9ke0406wp+y5z1vCy1dkcFJAUCgYEA0f7zBb2kLhWVk+/RdPkIXTka6gMx6YigCHhBCbpRWl6KfkcvkgLIr3Rc6Zw8wr96FqfTgvqXyXBX2qs80N0h4opnN7u13c5F1tw7UpQ3vrXXxJLwVBgFgig6RclJ34DTtAWdhvsgXTqTLnM6IWTv2OzOfTJsJELyY7nPkwWkF18CgYEAlri8I0vgFeotsIoXtvx6TM9vi0DdiG1KTas8UVarO+CyuZzTUImCwnk9ZGuXnJtXG697sPoBO/Bk/fASG8c2uCxrq3H32vnvHTNnALU+xZfXSJkmNugkYS0+Aw6Zt1oA0M6J1TJtxGzHyuzkzeGuBwprGy11oi8G3f8VQVhZbP0CgYEAnq4aEXJ1x2UD/B+xlMRBb8Ag+EelxaQ52WyVKLajlrftyvIbnieAYiR5uQUYXMi3hrsWdtjJLaw4lsHiWKlgW4Dd8h1jldGysGMGaKYbYX6jJqUp+UGVl/6x2d36dmswjc3YdRD6KbiDUww7FtTg8HgwqCYb1WLJKlZT5fLTRd0CgYBMkQMtjTLX8QAnB6txvR7R2zKfmxjHgEn98aFuZtjTrGRiN7Ydom1KDv66PjJbKi49jxKsShvLh4La+Id3z6LrMV5avE9M+PrzzxA/jimZetpbU4oSV/4aZY/pir2M38RimYaTwjvjyI3A5WYTIpySndRGUFiAaV7eYnYUUSwYSQ==";
            String publicStr = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwLULAjutPzRMX+H6vAi51bigNwWCsObtP6X1EfYFgGHbMjPKDWRUbCAn6t/fnhqhtrvCA/WhrMYuquc6hxoVxbxkmIyCbwHia4ZcXoa7tg4YkxRfbBoN0juZMCXM1S3LcJri6C+nG9ceXuPVbEMTS4iC2OCPwW/GB2a2ZtxqwDECkKAFEhbb9sUEjuwaP+MmZJNv4sldDcIwJR17YPPnKdbJ0O4RuMMgiJCSJ656t2b3bzfHwILJSsoFvaupmtITni0b+6o+0FI+LP+ffU4jU7G0MIoH8tTu5cCs5YWMvIdP21U9Jd2pDRPpqmHb24SpQ2YjjWnzO/9Ovkoj/VjQ2wIDAQAB";

            // 创建密钥工厂
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            // 字符串转密钥对象
            PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateStr)));
            PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(publicStr)));

            // 当明文超过最大长度限制，会报错
            //String encryptPrivateKey = encryptByPrivateKey(str, privateKey);
            //System.out.println(encryptPrivateKey);
            //
            //String encryptPublicKey = encryptByPublicKey(str, publicKey);
            //System.out.println(encryptPublicKey);

            // 分块加密不存在明文长度的限制
            String piecewiseEncryptPrivateKey = piecewiseEncrypt(str, privateKey);
            System.out.println("私钥加密："+piecewiseEncryptPrivateKey);
            System.out.println("公钥解密："+piecewiseDecrypt(piecewiseEncryptPrivateKey,publicKey));

            String piecewiseEncryptPublicKey = piecewiseEncrypt(str, publicKey);
            System.out.println("公钥加密："+piecewiseEncryptPublicKey);
            System.out.println("私钥解密："+piecewiseDecrypt(piecewiseEncryptPublicKey,privateKey));


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

    }

    /**
     * @Description: 私钥加密
     * @Param: [str, privateKey]
     * @return: java.lang.String
     * @Date: 2018/7/8
     */
    private static String encryptByPrivateKey(String str, PrivateKey privateKey) {
        try {
            // 1. 创建 cipher 对象
            Cipher cipher = Cipher.getInstance(ALGORITHM);

            // 2. 初始化 加密/解密 模式
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            // 3. 加密/解密
            byte[] encrypt = cipher.doFinal(str.getBytes());

            // base64 编码
            return Base64.getEncoder().encodeToString(encrypt);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | InvalidKeyException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Description: 私钥加密
     * @Param: [str, privateKey]
     * @return: java.lang.String
     * @Date: 2018/7/8
     */
    private static String encryptByPublicKey(String str, PublicKey publicKey) {
        try {
            // 1. 创建 cipher 对象
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            // 2. 初始化 加密/解密 模式
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            // 3. 加密/解密
            byte[] encrypt = cipher.doFinal(str.getBytes());
            // base64 编码
            return Base64.getEncoder().encodeToString(encrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @Description: piecewise 分段加密
     */

    public static String piecewiseEncrypt(String input, Key key) {

        int encryptMaxSize = 245;

        try {
            // 1. 创建 cipher 对象
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            // 2. 初始化 加密 模式
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // 3. 加密
            int offset = 0;
            byte[] buffer;
            ByteArrayOutputStream byteArrayInputStream = new ByteArrayOutputStream();
            while (input.getBytes().length - offset > 0) {

                //计算最后一块长度
                if (input.getBytes().length - offset >= encryptMaxSize) {
                    buffer = cipher.doFinal(input.getBytes(), offset, encryptMaxSize);
                    offset += encryptMaxSize;
                } else {
                    buffer = cipher.doFinal(input.getBytes(),offset,input.getBytes().length - offset);
                    offset = input.getBytes().length;
                }
                byteArrayInputStream.write(buffer);
            }
            // base64 编码
            return Base64.getEncoder().encodeToString(byteArrayInputStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Description: piecewise 分段解密
     */

    public static String piecewiseDecrypt(String input, Key key) {

        int encryptMaxSize = 256;
        try {
            // 密文 base64 解码
            byte[] inputDecoder = Base64.getDecoder().decode(input);
            // 1. 创建 cipher 对象
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            // 2. 初始化 解密 模式
            cipher.init(Cipher.DECRYPT_MODE, key);
            // 3. 解密
            int offset = 0;
            byte[] buffer;
            ByteArrayOutputStream byteArrayInputStream = new ByteArrayOutputStream();
            while (inputDecoder.length - offset > 0) {

                // 如果最后一块不足 encryptMaxSize 单独计算偏移量
                if ((offset + encryptMaxSize) > inputDecoder.length) {
                    encryptMaxSize = inputDecoder.length - offset;
                }

                buffer = cipher.doFinal(inputDecoder,offset,encryptMaxSize);
                offset += encryptMaxSize;
                byteArrayInputStream.write(buffer);
            }
            return byteArrayInputStream.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
