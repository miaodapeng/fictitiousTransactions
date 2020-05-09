
package com.vedeng.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * <b>Description: BigDecimal的操作工具方法</b><br> 
 * <b>Author: Franlin</b> 
 * @fileName BigDecimalCommonUtil.java
 * <br><b>Date: 2018年6月6日 下午4:20:35 </b> 
 *
 */
public class BigDecimalCommonUtil
{	

	/**
	 * 
	 * <b>Description:将String转换为BigDecimal保留2位有效数字</b><br> 
	 * @param val
	 * @return
	 * <b>Author: Franlin</b>  
	 * <br><b>Date: 2018年6月7日 上午10:18:49 </b>
	 */
	public static BigDecimal parseBigDecimal(String val)
	{
		return parseBigDecimal(val, 2);
	}
	
	/**
	 * 
	 * <b>Description: 将String转换为BigDecimal,默认值defaultVal</b><br> 
	 * @param val
	 * @param defaultVal
	 * @return
	 * <b>Author: Franlin</b>  
	 * <br><b>Date: 2018年6月7日 下午2:31:13 </b>
	 */
	public static BigDecimal parseBigDecimal(String val, double defaultVal)
	{
		BigDecimal dig = null;
		try
		{
			dig = new BigDecimal(val);
		}
		catch (Exception e)
		{
			dig = new BigDecimal(defaultVal);
			System.out.println("val不是数字, val = " + val);
		}
		
		return dig;
	}

	/**
	 * 
	 * <b>Description: 将String转换为BigDecimal保留num位有效数字</b><br> 
	 * @param val
	 * @param num
	 * @return
	 * <b>Author: Franlin</b>  
	 * <br><b>Date: 2018年6月7日 上午10:17:43 </b>
	 */
	public static BigDecimal parseBigDecimal(String val, int num)
	{
		return reservedNum(parseBigDecimal(val, 0.00), num);
	}
	
	/**
	 * 
	 * <b>Description: 展示百分比</b><br> 
	 * @param molecular
	 * @param denominator
	 * @return
	 * <b>Author: Franlin</b>  
	 * <br><b>Date: 2018年6月6日 下午4:39:37 </b>
	 */
	public static String percentage(BigDecimal molecular, BigDecimal denominator)
	{
		BigDecimal rate = percentage(molecular, denominator, 4);
		if(null == rate)
			return null;
		return reservedNum(rate.multiply(new BigDecimal("100")), 2).toString();
	}
	
	/**
	 * 
	 * <b>Description: 百分比</b><br> 
	 * @param molecular
	 * @param denominator
	 * @param num  保留几位有效数字
	 * @return
	 * <b>Author: Franlin</b>  
	 * <br><b>Date: 2018年6月6日 下午4:29:39 </b>
	 */
	public static BigDecimal percentage(BigDecimal molecular, BigDecimal denominator, int num)
	{
		if(null == molecular || null == denominator)
			return null;
		BigDecimal zero = new BigDecimal("0.00");
		if(0 >= molecular.compareTo(zero))
		{
			return zero;
		}
		
		return molecular.divide(denominator, num, RoundingMode.HALF_UP);
	}
	
	/**
	 * 
	 * <b>Description: 保留几位有效数字</b><br> 
	 * @param dig
	 * @param num
	 * @return
	 * <b>Author: Franlin</b>  
	 * <br><b>Date: 2018年6月6日 下午4:33:31 </b>
	 */
	public static BigDecimal reservedNum(BigDecimal dig, int num)
	{
		if(null == dig)
			return null;
		
		return percentage(dig, new BigDecimal("1"), num);
	}
	
	/**
	 * 
	 * <b>Description: 除法 保留num位有效数字</b><br> 
	 * @param dig1
	 * @param dig2
	 * @param num
	 * @return
	 * <b>Author: Franlin</b>  
	 * <br><b>Date: 2018年6月13日 下午5:53:24 </b>
	 */
	public static BigDecimal divide(BigDecimal dig1, BigDecimal dig2, int num)
	{
		if(null == dig1 || null == dig2)
			return null;
		return dig1.divide(dig2, num, RoundingMode.HALF_UP);
	}
	
	/**
	 * 
	 * <b>Description: 除法 保留2位有效数字</b><br> 
	 * @param dig1
	 * @param dig2
	 * @return
	 * <b>Author: Franlin</b>  
	 * <br><b>Date: 2018年6月13日 下午5:53:16 </b>
	 */
	public static BigDecimal divide(BigDecimal dig1, BigDecimal dig2)
	{
		if(null == dig1 || null == dig2)
			return null;
		return divide(dig1, dig2, 2);
	}
	
	/**
	 * 
	 * <b>Description: 除法 保留2位有效数字</b><br> 
	 * @param dig1
	 * @param dig2
	 * @return
	 * <b>Author: Franlin</b>  
	 * <br><b>Date: 2018年6月12日 上午11:32:43 </b>
	 */
	public static BigDecimal divide(Integer dig1, Integer dig2)
	{
		if(null == dig1 || null == dig2)
			return null;
		return divide(new BigDecimal(dig1), new BigDecimal(dig2));
	}
}

