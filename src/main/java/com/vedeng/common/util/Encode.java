package com.vedeng.common.util;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class Encode {
	public static String ALGORITHM = "PBEWithMD5AndDES";
	public static String PASSWORD = "^[bAuTiFuLl_GiRl]$";

	// 等maven环境好了之后，引入 armor-sqlist里面的这个类
	/**
	 * PBEWithMD5AndDES 加密
	 */
	public static String encode(String key) {
		// 加密工具
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setAlgorithm(ALGORITHM);
		encryptor.setPassword(PASSWORD);
		// 加密
		String ciphertext = encryptor.encrypt(key);
		return ciphertext;
	}

	public static String decode(String value) {
		StandardPBEStringEncryptor stringEncryptor = new StandardPBEStringEncryptor();
		stringEncryptor.setAlgorithm(ALGORITHM);
		stringEncryptor.setPassword(PASSWORD);
		return stringEncryptor.decrypt(value);
	}

}
