
package com.vedeng.common.util;
import java.math.BigDecimal;

import com.vedeng.common.exception.ShowErrorMsgException;

/**
 * <b>Description: 对于开单的数字金额转换为汉字大写</b><br> 
 * <b>Author: Franlin</b> 
 * @fileName DigitUppercaseUtils.java
 * <br><b>Date: 2018年5月22日 下午2:48:58 </b> 
 *
 */
public class DigitToChineseUppercaseNumberUtils
{
	/**
	 * 数字
	 * '零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'
	 */
	private static final char[] NUMBER_ARR = { '零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖' };
	
	
	
	/**
	 * 单位
	 *       "元" "拾", "佰", "仟", "万", "拾万",  "佰万", "仟万 ", "亿", "拾亿", "佰亿", "仟亿"
	 *  位数:   1    2   3    4    5     6      7      8     9      10    11    12
	 *  	  0    1   2     3    4     5     6       7     8      9     10    11
	 *  	  &                   &                         &
	 */
	private static final String[] SYSTEM_ARR = { "圆", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟"};
	
	/**
	 * 单位
	 */
	private static final char[] UNITS_ARR = { '角', '分'};
	
	/**
	 * 整
	 */
	private static final String UNIT_NAME = "整";
	
	/**
	 * 
	 * <b>Description: 将金额数字[保留2位有效数字，四舍五入]转换为汉字大写</b><br>[默认最大值是千亿] 
	 * @param number BigDecimal
	 * @return
	 * <b>Author: Franlin</b>  
	 * <br><b>Date: 2018年5月22日 下午2:58:47 </b>
	 * @throws ShowErrorMsgException 超出部分抛出异常
	 */
	public static String numberToChineseNumber(BigDecimal number) throws ShowErrorMsgException
	{
		if(null == number)
			return null;
		StringBuffer buf = new StringBuffer(number.compareTo(new BigDecimal(0.00)) >= 0 ? "" : "负");
		// 保留2位有效数字   四舍五入
		number = number.setScale(2, BigDecimal.ROUND_HALF_UP);
//		System.out.println(" === " + number.toString());
		// 取整
		Long d = number.longValue();
		
		String longStr = d.toString();
		int index = longStr.length();
		if(index > 12)
		{
			throw new ShowErrorMsgException("", number + "超出千亿, 请检查是否合法!");
		}
		index = index - 1;
		int beginIndex = index;
		char[] arr = longStr.toCharArray();
		for(char c : arr)
		{
			// 取值
			Integer val = Integer.parseInt(c +"");
			// 开头
			if(val == 0 && beginIndex == index)
			{	
				index--;
				continue;
			}
			// 非开头
			else if(val == 0)
			{
				if(index == 0)
				{
					// 元
					buf.append(SYSTEM_ARR[index]);
				}
				else if(index == 4 && getWnumber(arr))
				{
					// 万
					buf.append(SYSTEM_ARR[index]);
				}
				else if(index == 8)
				{
					// 亿
					buf.append(SYSTEM_ARR[index]);
				}
				// 补零
				else if(11 > index  && index > 8 && !"0".equals("" + arr[arr.length - index]))
				{
					buf.append('零');
				}
				else if(8 > index  && index > 4 && !"0".equals("" + arr[arr.length - index]))
				{
					buf.append('零');
				}
				else if(4 > index  && index > 0 && !"0".equals("" + arr[arr.length - index]))
				{
					buf.append('零');
				}
				index--;
				continue;
			}
			buf.append(NUMBER_ARR[val]).append(SYSTEM_ARR[index]);
			index--;
		}
		
//		DecimalFormat df2 =new DecimalFormat("#.00"); 
		// 取小数点后2位
		String[] rearArr = number.toString().split("\\.");
		
		// 后2位非0
		if(rearArr.length == 2 && Integer.parseInt(rearArr[1]) != 0)
		{
			index = 0;
			for(char c : rearArr[1].toCharArray())
			{
				Integer val = Integer.parseInt(c +"");
				// 只取后2位
				if(index > 1)
					continue;
				// 01
				if(val == 0 && index == 0)
				{	
					buf.append('零');
					index++;
					continue;
				}
				// 10
				else if(val == 0 && index == 1)
				{	
					index++;
					continue;
				}	
				buf.append(NUMBER_ARR[val]).append(UNITS_ARR[index]);
				index++;
			}
		}
		else
		{
			buf.append(UNIT_NAME);
		}
		
		return buf.toString();
	}

	public static boolean getWnumber(char[] arr){
		int start=4;
		if(arr.length<start){
			return false;
		}

		if(Integer.valueOf(arr[start]+"")>0){
			return true;
		}
		start++;
		if(arr.length>start&&Integer.valueOf(arr[start]+"")>0){
			return true;
		}
		start++;
		if(arr.length>start&&Integer.valueOf(arr[start]+"")>0){
			return true;
		}
		return false;
	}

//	public static void main(String[] args) throws ShowErrorMsgException
//	{
//		String f = "200040150000.12";
//		
//		String n = numberToChineseNumber(new BigDecimal(f));
//		
//		System.out.println("n = " + n);
//		
//		// TODO 贰仟亿肆仟零壹拾伍万圆零壹分 是否要去掉 园
//	
//	}
	
}

