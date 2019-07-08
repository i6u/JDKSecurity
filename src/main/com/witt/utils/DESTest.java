package com.witt.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * DESTest.java
 * 
 * @author Techzero
 * @Email techzero@163.com
 * @Time 2013-12-12 下午2:22:58
 */
public class DESTest {
 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String content = "DESTest";
		// 密码长度必须是8的倍数
		String password = "12345678";
		System.out.println("密　钥：" + password);
		System.out.println("加密前：" + content);

		byte[] result = encrypt(content.getBytes(), password);
		System.out.println("加密后：" + new String(result));
		byte[] decryResult = decrypt(result, password);
		System.out.println("解密后：" + new String(decryResult));
	}
 
	/**
	 * 加密
	 * 
	 * @param content
	 *            待加密内容
	 * @param key
	 *            加密的密钥
	 * @return
	 */
	public static byte[] encrypt(byte[] content, String key) {
		try {
			SecureRandom random = new SecureRandom();

			DESKeySpec desKey = new DESKeySpec(key.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(desKey);

			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
			return cipher.doFinal(content);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
 
	/**
	 * 解密
	 * 
	 * @param content
	 *            待解密内容
	 * @param key
	 *            解密的密钥
	 * @return
	 */
	public static byte[] decrypt(byte[] content, String key) {
		try {
			SecureRandom random = new SecureRandom();
			DESKeySpec desKey = new DESKeySpec(key.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(desKey);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, securekey, random);
			return cipher.doFinal(content);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
}