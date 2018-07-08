package com.witt.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author witt
 * @fileName MessageDigestUtil
 * @date 2018/7/8 18:24
 * @description
 * @history <author>    <time>    <version>    <desc>
 */

public class MessageDigestUtil {

    public static void main(String[] args) {

        String str = "多多又胖了";
        System.out.println(md5(str));
    }

    private static String md5(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] bytes = messageDigest.digest(str.getBytes());
        return  toHexString(bytes);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Description: 读取文件 md5 值
     */
    private static String md5File(String filePath) {
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fileInputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] bytes = messageDigest.digest(byteArrayOutputStream.toByteArray());
            return  toHexString(bytes);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Description: byte数组转为16进制字符串
     */
    private static String toHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(); // 一个 byte 转为十六进制的高4位和低4位，长度增加一倍
        for (byte b : bytes) {
            int value = b & 0xFF;  // byte 转 16进制，二进制计算，& 运算嘛，同为 1 才为 1，这样和 0xff 一算高位就补了 0
            if (value < 0x10) sb.append("0"); // 如果得到的 16 进制数字，位数小于两位（小于16就小于两位喽），高位补个0
            sb.append(Integer.toHexString(value));
        }
        return sb.toString();
    }
}
