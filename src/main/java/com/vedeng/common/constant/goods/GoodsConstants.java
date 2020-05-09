package com.vedeng.common.constant.goods;

public class GoodsConstants {
	public static final String GOODS_EDIT_ROLE = "商品流编辑商品角色";
	public static final String GOODS_EDIT_TEMP_ROLE = "商品流编辑临时商品角色";
	public static final String GOODS_CHECK_ROLE = "商品流审核商品角色";
	public static final String SPU_NO_PRE = "V";
	public static final String SKU_NO_PRE = "V";

	public static final int SKU_PAGE_SIZE = 5;
	/**
	 * 器械
	 */
	public static final int SKU_TYPE_INSTRUMENT = 1;//器械
	/**
	 * 耗材
	 */
	public static final int SKU_TYPE_CONSUMABLES = 2;//耗材


	public static final int SPU_OPERATE = 1;//spu

	public static final int SKU_OPERATE  = 2;//sku

	//商品未推送到平台
	public static final int PUSH_STATUS_UN_PUSH=0;
	//商品推送到贝登
	public static final int PUSH_STATUS_VEDENG=1;
	//商品推送到医械购
	public static final int PUSH_STATUS_YXG=2;
	//商品推送到贝登和医械购
	public static final int PUSH_STATUS_ALL=3;

	// 值为0
	public static final int ZERO=0;
	// 值为1
	public static final int ONE=1;
	//值为2
	public static final int TWO=2;








}
