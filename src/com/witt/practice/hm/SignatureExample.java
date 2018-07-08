package com.witt.practice.hm;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author witt
 * @fileName SignatureExample
 * @date 2018/7/8 20:36
 * @description 数字签名
 * @history <author>    <time>    <version>    <desc>
 */

public class SignatureExample {

    private static final String ALGORITHM = "SHA256withRSA";

    public static void main(String[] args) {
        String params = "product=iPhone&price=9688";
        // 加密
        String sign = signature(params);
        System.out.println(sign);
        // 验证
        boolean verify = verify(params, sign);
        System.out.println(verify);
    }

    private static String signature(String params) {
        try {
            // 1. 创建数字签名对象
            Signature signature = Signature.getInstance(ALGORITHM);
            // 2. 初始化签名
            PrivateKey privateKey = getPrivateKey();
            signature.initSign(privateKey);
            // 3. 传入原文
            signature.update(params.getBytes());
            // 4. 签名
            byte[] sign = signature.sign();
            return Base64.getEncoder().encodeToString(sign);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static boolean verify(String params, String sign) {
        try {
            // 1. 创建数字签名对象
            Signature signature = Signature.getInstance(ALGORITHM);
            // 2. 初始化校验
            PublicKey publicKey = getPublicKey();
            signature.initVerify(publicKey);
            // 3. 传入原文
            signature.update(params.getBytes());
            // 4. 校验
            return signature.verify(Base64.getDecoder().decode(sign));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static PrivateKey getPrivateKey() {
        try {
            String privateStr = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDAtQsCO60/NExf4fq8CLnVuKA3BYKw5u0/pfUR9gWAYdsyM8oNZFRsICfq39+eGqG2u8ID9aGsxi6q5zqHGhXFvGSYjIJvAeJrhlxehru2DhiTFF9sGg3SO5kwJczVLctwmuLoL6cb1x5e49VsQxNLiILY4I/Bb8YHZrZm3GrAMQKQoAUSFtv2xQSO7Bo/4yZkk2/iyV0NwjAlHXtg8+cp1snQ7hG4wyCIkJInrnq3ZvdvN8fAgslKygW9q6ma0hOeLRv7qj7QUj4s/599TiNTsbQwigfy1O7lwKzlhYy8h0/bVT0l3akNE+mqYdvbhKlDZiONafM7/06+SiP9WNDbAgMBAAECggEBAKNkHYxsKu2PGz6k7SInR+HFI9rjCjeR5RD1VoA/F9UNo3vCW4vVSJtQ/GdyIDj+naTBN7SBvO4wLvtrCL3qSRGaNbT+Vc1nTKIcjw76sAehlgaG3I1c3eEl9THUqwMJTBgSy1dQqCC72Z1dEeU0ThTaDw9x/5FkgwRde0RlmQGQUz3DtZUU/zSl6YNLZFech+qYMMUPZ5bFh7zOMuSSLhcgRpJERsBOipaNAN3xKiHa2Iezha0zagztuazi7LBebOsuEYo3k7BsOcZGjGZjd6wU+38N8wGv0WxmlL+V90PE0sCjZ0UdyhniDPxp0OODYGW2yNlsh926LlZ2YbXzegkCgYEA6uyBBq3YWdSJ1Ym6UYZyQ2KUcKNrCUo0PpkvR7WaWdpBZD5A8osj/RPagWKQ0gtVvMNWVKCT0H7v6Qp6fGs+moG+ThmgxUaexMfx8tadIpqwF5O3Wm781gF+KPqUdblc9PhNUkZEK9PrzxoZdE9ke0406wp+y5z1vCy1dkcFJAUCgYEA0f7zBb2kLhWVk+/RdPkIXTka6gMx6YigCHhBCbpRWl6KfkcvkgLIr3Rc6Zw8wr96FqfTgvqXyXBX2qs80N0h4opnN7u13c5F1tw7UpQ3vrXXxJLwVBgFgig6RclJ34DTtAWdhvsgXTqTLnM6IWTv2OzOfTJsJELyY7nPkwWkF18CgYEAlri8I0vgFeotsIoXtvx6TM9vi0DdiG1KTas8UVarO+CyuZzTUImCwnk9ZGuXnJtXG697sPoBO/Bk/fASG8c2uCxrq3H32vnvHTNnALU+xZfXSJkmNugkYS0+Aw6Zt1oA0M6J1TJtxGzHyuzkzeGuBwprGy11oi8G3f8VQVhZbP0CgYEAnq4aEXJ1x2UD/B+xlMRBb8Ag+EelxaQ52WyVKLajlrftyvIbnieAYiR5uQUYXMi3hrsWdtjJLaw4lsHiWKlgW4Dd8h1jldGysGMGaKYbYX6jJqUp+UGVl/6x2d36dmswjc3YdRD6KbiDUww7FtTg8HgwqCYb1WLJKlZT5fLTRd0CgYBMkQMtjTLX8QAnB6txvR7R2zKfmxjHgEn98aFuZtjTrGRiN7Ydom1KDv66PjJbKi49jxKsShvLh4La+Id3z6LrMV5avE9M+PrzzxA/jimZetpbU4oSV/4aZY/pir2M38RimYaTwjvjyI3A5WYTIpySndRGUFiAaV7eYnYUUSwYSQ==";
            // 创建密钥工厂
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            // 字符串转密钥对象
            return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateStr)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static PublicKey getPublicKey() {
        try {
            String publicStr = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwLULAjutPzRMX+H6vAi51bigNwWCsObtP6X1EfYFgGHbMjPKDWRUbCAn6t/fnhqhtrvCA/WhrMYuquc6hxoVxbxkmIyCbwHia4ZcXoa7tg4YkxRfbBoN0juZMCXM1S3LcJri6C+nG9ceXuPVbEMTS4iC2OCPwW/GB2a2ZtxqwDECkKAFEhbb9sUEjuwaP+MmZJNv4sldDcIwJR17YPPnKdbJ0O4RuMMgiJCSJ656t2b3bzfHwILJSsoFvaupmtITni0b+6o+0FI+LP+ffU4jU7G0MIoH8tTu5cCs5YWMvIdP21U9Jd2pDRPpqmHb24SpQ2YjjWnzO/9Ovkoj/VjQ2wIDAQAB";
            // 创建密钥工厂
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            // 字符串转密钥对象
            return keyFactory.generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(publicStr)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
