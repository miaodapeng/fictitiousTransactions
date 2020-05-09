package com.vedeng.common.util;

import java.util.Random;
/**
 * <b>Description:</b><br> 扰码生成
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.common.util
 * <br><b>ClassName:</b> Salt
 * <br><b>Date:</b> 2017年4月27日 上午11:11:00
 */
public class Salt {
	private String charset = "abcdefghkmnprstuvwxyzABCDEFGHJKLMNPRSTUVWXYZ23456789";//随机因子
	private String charsetNum = "0123456789";//纯数字随机因子
	private Integer codelen = 6;//长度
	
	public Integer getCodelen() {
		return codelen;
	}
	public void setCodelen(Integer codelen) {
		this.codelen = codelen;
	}
	
	/**
	 * <b>Description:</b><br> 扰码生成
	 * @param isNum 是否是纯数字
	 * @return String
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月27日 上午11:17:03
	 */
	public String createSalt(Boolean isNum){
		String salt = "";
		String charStr = isNum ? this.charsetNum : this.charset;
		
		Integer len = charStr.length()-1;
		for(int i=0;i<this.codelen;i++){
			Random random = new Random();

	        int s = random.nextInt(len);
			salt += charStr.substring(s, s+1);
		}
		return salt;
	}
}
