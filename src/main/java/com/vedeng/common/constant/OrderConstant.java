
package com.vedeng.common.constant;

import java.math.BigDecimal;

/**
 * <b>Description: 订单常量</b><br>
 * <b>Author: Franlin</b>
 * 
 * @fileName OrderConstant.java <br>
 *           <b>Date: 2018年10月26日 上午10:48:27 </b>
 *
 */
public class OrderConstant {

	/**
	 * 订单单个商品价格和总额限制
	 */
	public static final BigDecimal AMOUNT_LIMIT=new BigDecimal("300000000.00");
    /**
     * 订单单个商品数量限制
     */
	public static final int GOODS_NUM_LIMIT=100000000;
	/**
	 * 订单类型 0--销售订单
	 */
	public static final Integer ORDER_TYPE_SALE = 0;

	/**
	 * 订单类型 2--BD订单
	 */
	public static final Integer ORDER_TYPE_BD = 1;

	/**
	 * 订单类型 2--备货订单
	 */
	public static final Integer ORDER_TYPE_BH = 2;

	/**
	 * 订单类型 3--订货订单
	 */
	public static final Integer ORDER_TYPE_DH = 3;

	/**
	 * 订单类型 4--经销商订单
	 */
	public static final Integer ORDER_TYPE_JX = 4;

	/**
	 * 订单类型 5--耗材订单
	 */
	public static final Integer ORDER_TYPE_HC = 5;

	/**
	 * 订单类型 6--小医院订单
	 */
	public static final Integer ORDER_TYPE_EL = 6;

	public static final Integer ORDER_SOURCE_ADK_3 = 3;

	public static final Integer ORDER_SOURCE_EL_4 = 4;

	// 订单的一些初始化状态
	public static final Integer ORDER_STATUS_INIT0 = 0;
	/**
	 * 订单类型 1--订货订单
	 */
	//public static final Integer ORDER_TYPE_BD = 1;

	public static final Integer CANCEL_BD_TIMEOUT=1;
	public static final Integer CANCEL_BD_HAND=2;

	//直发
	public static final Integer DELEVIRY_STATUS_1 = 1;
}
