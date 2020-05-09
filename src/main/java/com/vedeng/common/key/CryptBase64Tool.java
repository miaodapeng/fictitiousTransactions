package com.vedeng.common.key;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.common.constants.Contant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * <b>Description:</b><br>
 * 定义加密解密方式
 * 
 * @author duke
 * @Note <b>ProjectName:</b> dbcenter <br>
 *       <b>PackageName:</b> com.vedeng.common <br>
 *       <b>ClassName:</b> CryptBase64Tool <br>
 *       <b>Date:</b> 2017年5月9日 下午3:35:49
 */
public class CryptBase64Tool {
	public static Logger logger = LoggerFactory.getLogger(CryptBase64Tool.class);

	// private static SecretKey secretKey = null;//key对象

	// private static Cipher cipher = null; //私鈅加密对象Cipher

	// private static String keyString = "AKlMU89D3FchIkhKyMma6FiE";//密钥

	private static Logger log = LoggerFactory.getLogger(CryptBase64Tool.class);

	/*
	 * static { try { secretKey = new SecretKeySpec(keyString.getBytes(),
	 * "DESede");// 获得密钥 获得一个私鈅加密类Cipher，DESede是算法，ECB是加密模式，PKCS5Padding是填充方式 cipher
	 * = Cipher.getInstance("DESede/ECB/PKCS5Padding"); } catch (Exception e) {
	 * log.error(e.getMessage(), e); } }
	 */

	/**
	 * <b>Description:</b><br>
	 * 加密
	 * 
	 * @param message
	 * @return
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2017年5月9日 下午3:36:10
	 */
	public static String desEncrypt(String message, String keyString) {
		String result = ""; // DES加密字符串
		String newResult = "";// 去掉换行符后的加密字符串
		try {
			SecretKey secretKey = new SecretKeySpec(keyString.getBytes(), "Blowfish");// 获得密钥
			/* 获得一个私鈅加密类Cipher，DESede是算法，ECB是加密模式，PKCS5Padding是填充方式 */
			Cipher cipher = Cipher.getInstance("Blowfish/ECB/PKCS5Padding");

			cipher.init(Cipher.ENCRYPT_MODE, secretKey); // 设置工作模式为加密模式，给出密钥
			byte[] resultBytes = cipher.doFinal(message.getBytes("UTF-8")); // 正式执行加密操作
			BASE64Encoder enc = new BASE64Encoder();
			result = enc.encode(resultBytes);// 进行BASE64编码
			newResult = filter(result); // 去掉加密串中的换行符
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return newResult;
	}

	/**
	 * 解密
	 * 
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public static String desDecrypt(String message, String keyString) throws Exception {
		String result = "";
		try {
			SecretKey secretKey = new SecretKeySpec(keyString.getBytes(), "Blowfish");// 获得密钥
			/* 获得一个私鈅加密类Cipher，DESede是算法，ECB是加密模式，PKCS5Padding是填充方式 */
			Cipher cipher = Cipher.getInstance("Blowfish/ECB/PKCS5Padding");

			BASE64Decoder dec = new BASE64Decoder();
			byte[] messageBytes = dec.decodeBuffer(message); // 进行BASE64编码
			cipher.init(Cipher.DECRYPT_MODE, secretKey); // 设置工作模式为解密模式，给出密钥
			byte[] resultBytes = cipher.doFinal(messageBytes);// 正式执行解密操作
			result = new String(resultBytes, "UTF-8");
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

	/**
	 * 去掉加密字符串换行符
	 * 
	 * @param str
	 * @return
	 */
	public static String filter(String str) {
		String output = "";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			int asc = str.charAt(i);
			if (asc != 10 && asc != 13) {
				sb.append(str.subSequence(i, i + 1));
			}
		}
		output = new String(sb);
		return output;
	}

	/**
	 * 加密解密测试
	 * 
	 * @param args
	 */
	/*
	 * public static void main(String[] args) { try { String strText =
	 * "Hello world!"; String deseResult = desEncrypt(strText);// 加密
	 * System.out.println("加密结果：" + deseResult); String desdResult =
	 * desDecrypt(deseResult);// 解密 System.out.println("解密结果：" + desdResult);
	 * 
	 * } catch (Exception e) { logger.error(Contant.ERROR_MSG, e); } }
	 */
}
