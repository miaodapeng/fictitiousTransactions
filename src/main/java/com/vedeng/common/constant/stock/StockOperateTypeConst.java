package com.vedeng.common.constant.stock;

/**
 *业务类型标识
 * @author strange
 * @date $
 */
public class StockOperateTypeConst {
    /**
     *  0关闭订单
     */
    public final static Integer COLES_ORDER = 0;
    /**
     *  11保存订单
     */
    public final static Integer START_ORDER = 11;
    /**
     *  539 售后退货完成
     */
    public final static Integer AFTERORDER_BACK_FINSH = 539;
    /**
     *  540 售后换货完成
     */
    public final static Integer AFTERORDER_CHANGE_FINSH = 540;
    /**
    *  1入库
    */
    public final static Integer WAREHOUSE_IN = 1;
    /**
     *  2出库
     */
    public final static Integer WAREHOUSE_OUT = 2;
    /**
     *  3销售换货入库
     */
    public final static Integer ORDER_WAREHOUSE_CHANGE_IN = 3;
    /**
     *  4销售换货出库
     */
    public final static Integer ORDER_WAREHOUSE_CHANGE_OUT = 4;
    /**
     *  5销售退货入库
     */
    public final static Integer ORDER_WAREHOUSE_BACK_IN = 5;
    /**
     *  6采购退货出库
     */
    public final static Integer BUYORDER_WAREHOUSE_BACK_OUT = 6;
    /**
     *  7采购换货出库
     */
    public final static Integer BUYORDER_WAREHOUSE_CHANGE_OUT = 7;
    /**
     *  8采购换货入库
     */
    public final static Integer BUYORDER_WAREHOUSE_CHANGE_IN = 8;
    /**
     *  9外借入库
     */
    public final static Integer LENDOUT_WAREHOUSE_IN = 9;
    /**
     *  10外借出库
     */
    public final static Integer LENDOUT_WAREHOUSE_OUT = 10;
}
