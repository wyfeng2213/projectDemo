package com.cmcc.requestlibrary.utils;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * 加密工具类
 *
 * @author luoyizhou
 */
public class EncryptUtil {

	// 密钥
	private final static String secretKey  = "familyband@archermind.cn";
	// 向量
	private final static String iv = "01234567";
	// 加解密统一使用的编码方式
	private final static String encoding = "utf-8";

	/**
	 * Encode string.
	 *
	 * @param plainText the plain text
	 * @return string
	 * @throws Exception
	 */
	public static String encode(String plainText){
		try{
			Key deskey = null;
			DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
			deskey = keyfactory.generateSecret(spec);
			Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
			IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
			byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
			return EncryptBase64Util.encode(encryptData);
		}catch (Exception e){
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * Decode string.
	 *
	 * @param encryptText 加密文本
	 * @return string
	 * @throws Exception
	 */
	public static String decode(String encryptText){
		try{
			Key deskey = null;
			DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
			deskey = keyfactory.generateSecret(spec);
			Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
			IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
			byte[] decryptData = cipher.doFinal(EncryptBase64Util.decode(encryptText));
			return new String(decryptData, encoding);
		}catch (Exception e){
			e.printStackTrace();
		}
		return encryptText;
	}

}