package com.witt.practice.hm;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
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
     * <p>
     * (RSA/ECB/PKCS1Padding (1024, 2048))
     * 关于每次加密块的最大长度问题
     * 如果是 1024 bit，那么铭文长度就应该是 1024 / 8 -11 = 117
     * 如果是 2048 bit，那么铭文长度就应该是 2048 / 8 -11 = 245
     * <p>
     * 解密密文
     * 如果是 1024 bit，那么铭文长度就应该是 1024 / 8 = 128
     * 如果是 2048 bit，那么铭文长度就应该是 2048 / 8 = 256
     */
    public static void main(String[] args) throws IOException {
        // RSA 加密，每次加密的铭文有长度限制，可以采用分段加密解决这个问题
        String filePath = "/Users/witt/var/rsa/test.json";
        String str = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
        //String str = "123456ABCDEF";
        System.out.println(str.getBytes().length);
        try {
            /* 实际开发中不可每次都生成密钥，都是使用保存起来的密钥*/

            // 秘钥对生成器
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
            // 秘钥对称对象
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            // 生成公钥/私钥
            PrivateKey privateKeyTemp = keyPair.getPrivate();
            PublicKey publicKeyTemp = keyPair.getPublic();
            //System.out.println("私钥："+Base64.getEncoder().encodeToString(privateKeyTemp.getEncoded()));
            //System.out.println("公钥："+Base64.getEncoder().encodeToString(publicKeyTemp.getEncoded()));


            // 使用保存期来的密钥
            String privateStr = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCCpVWAjtmSjrAhFXF/q4A6/nteJw7UQ/RCjmMLFSaYF7sLCL/u6z3pzpT3sUEEj2veT9cFXYOv1/J7PWzwn3dAVFqVVIAl8nO4fH3oF+Xv5jM6QgpEXndO1CQEssHoWTPMqPjCYhE8KU4PlzbtLJPlzQ0kPkfwAyT0rWqruEsF7L2L3jqUSb04sChy30dIx8zKVVBjsr4ewIuec1ywkywvLLoma8EmAlXufB/m7PUT4cCdreAUbvUTqdpKJWlkWrtgwly1hvZzwi4fiCnbsauH9q6753iBYD8k4Snxs9ikN68w8G2D1WXs8GhppHaM7Wd62WlO1G2q7sjfnnXqpE5nAgMBAAECggEAQKFUBA4nGjaWcTdvd447GHQuSayezGl1m7teyevDtPgAYvwIH0Sm01SMI2C/3mapIUh0AcZ6T/JPlaIUk5D/6IYSbc9oud0QXFLR4pB4As7pxsdG1v24nfmae+yGus/uLS/mKBtllVKFuHbBCGHGJKfk+WoKInUbwiNMIvk/vATdbxjgXfRrLOmpcs9nJSCcwJM9zz+rOoE8lCjCYsfzEapub8VEg4EPRPhK2BdDX3iQIumeev9veHO+cIwtA0QUdmj3SRBWPWghdJHF7skawsc7BmEGg/GEwZ7YXzenR0BsDc9WgKFMFSo/0uUant8DSAJN0htiwvl1BRfpTx0SGQKBgQD4Ghtb4LHdSjpc8hGJUlUxPbqaah0gS1sKf1eBTBUs0o9ZqmavGpGED8dmWcwM1/D0rs5ZG15JLLBbxy+AoJP5AWMnJsujhezwpBr722I4vIIAonixSe+C7eqx9MAn20EG/aLpmsAqU5Sk2c8eUFFVReCc2ZHg2RUZVVJexUa1swKBgQCGzgZewxhT5i2rQG2FM4KhOZ2/cEJhOMFZ8h0z83z650xWlso7+ZE4J/Ag9rToh0UjXPBl7DwOlLYN5xon6BD0DRE6xMynTRKwV2cQav6Wfnf9d19XlN3w3WqClY1UTG2mK1Uf6hBKx5Zsm9H8GN/t3GWBfzkll0h55cY3as0SfQKBgQDN24ub3oJ2ZKKsgEZAtMnvQ5LTkPx90UjHGwYWSHTBbix3b8YnwFnBQ/5lVFjUx1AtFeYtVWIUQTpLzKgDiDl/QMFuuOf61KLWwLOLCmsjPHZ55FVUdU6ASryTz8qox2nbTRSh1D9iX1tUtUz/kBEMOiC2xGggMr8o9/ySmgmjtQKBgQCBqh5afBIkcV0wQjKWUyutZLwlbheLaIoiuE36sJRgyqaVC0nPUoiqmU/ShTsSBzsrfrQFdat24vgSe16fUAsulHskKjQoIGx/jjKmiiQEIfXMqkvc9GZezrsqjCt7EGDP2xJwNjYhl8VLBr5+XHbYVoyUeQlA7TlbdvaoS2SiCQKBgQDpUtM0LI9ypiOF/u8dwYBVwWaW8XHr1Cb8ZLAGDqsFyWDGIdaMlpVLUtm3wcfqe5CH6CMNrV45cKXPRgqbimkfFu28PYdaEs5/ahFUPPelzTs9FfQVAClPlUJTFb/KBteECqTvsmKzCS3UKdvE1CmRUzitC1dTgMnedIhMhWHTfQ==";
            String publicStr = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgqVVgI7Zko6wIRVxf6uAOv57XicO1EP0Qo5jCxUmmBe7Cwi/7us96c6U97FBBI9r3k/XBV2Dr9fyez1s8J93QFRalVSAJfJzuHx96Bfl7+YzOkIKRF53TtQkBLLB6FkzzKj4wmIRPClOD5c27SyT5c0NJD5H8AMk9K1qq7hLBey9i946lEm9OLAoct9HSMfMylVQY7K+HsCLnnNcsJMsLyy6JmvBJgJV7nwf5uz1E+HAna3gFG71E6naSiVpZFq7YMJctYb2c8IuH4gp27Grh/auu+d4gWA/JOEp8bPYpDevMPBtg9Vl7PBoaaR2jO1netlpTtRtqu7I35516qROZwIDAQAB";

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
            long preStart = System.currentTimeMillis();
            String piecewiseEncryptPrivateKey = piecewiseEncrypt(str, privateKey);
            System.out.println("私钥加密：" + piecewiseEncryptPrivateKey.length() + "\n time : " + (System.currentTimeMillis() - preStart));
            long pubStartDec = System.currentTimeMillis();
            String piecewiseDecrypt = piecewiseDecrypt(piecewiseEncryptPrivateKey, publicKey);
            System.out.println("公钥解密：" + piecewiseDecrypt.length() + "\n time : " + (System.currentTimeMillis() - pubStartDec));

            long pubStart = System.currentTimeMillis();
            String piecewiseEncryptPublicKey = piecewiseEncrypt(str, publicKey);
            System.out.println("公钥加密：" + piecewiseEncryptPublicKey.length() + "\n time : " + (System.currentTimeMillis() - pubStart));
            long preStartDec = System.currentTimeMillis();
            String pubDecrypt = piecewiseDecrypt(piecewiseEncryptPublicKey, privateKey);
            System.out.println("私钥解密：" + pubDecrypt.length() + "\n time : " + (System.currentTimeMillis() - preStartDec));


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
                    buffer = cipher.doFinal(input.getBytes(), offset, input.getBytes().length - offset);
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

                buffer = cipher.doFinal(inputDecoder, offset, encryptMaxSize);
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
