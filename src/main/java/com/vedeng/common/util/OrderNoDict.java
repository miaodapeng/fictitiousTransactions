package com.vedeng.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <b>Description:</b><br>
 * 订单单号生成
 * 
 * @author Jerry
 * @Note <b>ProjectName:</b> dbcenter <br>
 *       <b>PackageName:</b> com.vedeng.common.tools <br>
 *       <b>ClassName:</b> OrderNoDict <br>
 *       <b>Date:</b> 2017年6月23日 上午9:44:55
 */
public class OrderNoDict {
	public static String prefixNum;

	public static String dictMapFile;

	public static String dictMap;

	public static Integer saltNum = 1487245;

	public static Integer[] dictMapList = new Integer[] { 0, 7, 1, 6, 9, 3, 4, 5, 2, 8 };

	public static Integer ADC_ORDER_TYPE = 16;
	
	public static Integer LEND_OUT_TYPE =17;

	public static Integer EL_OUT_TYPE =18;

	public OrderNoDict() {

	}

	/**
	 * <b>Description:</b><br>
	 * 生成订单编码
	 * 
	 * @param originalNum
	 *            主键ID
	 * @param type
	 *            类型 1:询价 2：报价 3：销售订单 4：备货订单 5：采购订单 6：售后订单 7：订单修改申请 8：工单 9:订货订单
	 *            10:财务流水 11文件寄送12销售订单修改申请 13随机数14备货采购单 15采购订单修改申请 16 ADK  17外借单
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年6月23日 上午10:10:27
	 */
	public static String getOrderNum(Integer originalNum, Integer type) {

		return makeOrderNum(originalNum, type);
	}

	protected static final String makeOrderNum(Integer originalNum, Integer type) {
		originalNum = originalNum * 3 + saltNum;

		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yy");
		String dateNowStr = sdf.format(d);
		String distNum = String.valueOf(dateNowStr);

		String s = String.valueOf(originalNum);
		String[] ss = s.split("");

		for (String v : ss) {
			distNum += dictMapList[Integer.parseInt(v)];
		}
		if (type != null) {
			switch (type) {
			case 1:
				break;
			case 2:
				distNum = "VD" + distNum;
				break;
			case 3:
				distNum = "VS" + distNum;
				break;
			case 4:
				distNum = "BH" + distNum;
				break;
			case 5:
				distNum = "VP" + distNum;
				break;
			case 6:
				distNum = "SH" + distNum;
				break;
			case 7:
				distNum = "MD" + distNum;
				break;
			case 8:
				distNum = "WR" + distNum;
				break;
			case 9:
				distNum = "DH" + distNum;
				break;
			case 10:// 年+月+日+时+分+秒+8位加密数字
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
				String dateStr = df.format(new Date());
				distNum = dateStr + distNum;
				break;
			case 11:// 文件寄送
				distNum = "WJ" + distNum;
				break;
			case 12:// 销售订单修改
				distNum = "XG" + distNum;
				break;
			case 13:// 随机数
				break;
			case 14:
				distNum = "VB" + distNum;
				break;
			case 15:
				distNum = "XV" + distNum;
				break;
			case 16:
				distNum = "ADK" + distNum;
				break;
			case 17:
				distNum = "JY" + distNum;
				break;
			case 18:
				distNum = "EL" + distNum;
				break;
			default:
				break;
			}

		}
		return distNum;
	}

	public static void main(String[] args) {
		System.out.println(OrderNoDict.getOrderNum(2, 1));
		System.out.println(OrderNoDict.getOrderNum(3, 2));
		System.out.println(OrderNoDict.getOrderNum(22167, 3));
		System.out.println(OrderNoDict.getOrderNum(5, 4));
		System.out.println(OrderNoDict.getOrderNum(6, 5));
		System.out.println(OrderNoDict.getOrderNum(7, 6));
		System.out.println(OrderNoDict.getOrderNum(8, 7));
		System.out.println(OrderNoDict.getOrderNum(9, 8));
		System.out.println(OrderNoDict.getOrderNum(10, 9));
		System.out.println(OrderNoDict.getOrderNum(10, 10));
		System.out.println(OrderNoDict.getOrderNum(10, 11));
		System.out.println(OrderNoDict.getOrderNum(50745, 1));
		System.out.println(OrderNoDict.getOrderNum(112254, 18));
	}

}
