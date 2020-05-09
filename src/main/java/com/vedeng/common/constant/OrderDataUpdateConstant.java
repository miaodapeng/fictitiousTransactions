package com.vedeng.common.constant;

/**
 *
 *订单更新时间常量类型
 * @author strange
 * @date $
 */
public class OrderDataUpdateConstant {
    /**
     * 1.订单生成<p>-
     * 2.订单生效审核<p>-
     * 3.订单结款<p>-
     * 4.订单出库<p>-
     * 5.订单完结<p>订单完结逻辑在dbCENTER开票及快递签收中因此可以合并.方法为saleorderDataService.getIsEnd
     * 6.订单关闭<p>-
     * 7.订单快递签收<p>-
     * 8.订单开票<p>-自动纸质票开票方法:ST_INVOICE,为航信软件直调db
     * 9.订单编辑<p>-
     * 10.订单商品编辑<p>-
     * 11.生成关联采购单<p>-
     * 12.销售单关联退货换货售后单操作<p>-
     * 更新销售单updateDataTime
     * @param operateType 和上述编号一致
     *  @Author:strange
     *  @Date:10:15 2020-04-06
     */
    public static final String SALE_ORDER_GENERATE="1";
    public static final String SALE_ORDER_VAILD="2";
    public static final String SALE_ORDER_PAY="3";
    public static final String SALE_ORDER_OUT="4";
    public static final String SALE_ORDER_END="5";
    public static final String SALE_ORDER_CLOSE="6";
    public static final String SALE_ORDER_EXPRESS_END="7";
    public static final String SALE_ORDER_INVOICE="8";
    public static final String SALE_ORDER_EDIT="9";
    public static final String SALE_ORDER_GOODS_EDIT="10";
    public static final String SALE_ORDER_BUYORDER_OPT="11";
    public static final String SALE_ORDER_AFTERODER_OPT="12";
    /**
     * 1.备货单生成<p>-
     * 2.采购单生成-更新销售单<p>-
     * 3.采购单审核<p>-
     * 4.采购单付款<p>-
     * 5.采购单入库<p>-
     * 6.采购单完成<p>
     * 7.采购单关闭<p>-
     * 8.采购录票<p>-
     * 9.采购单编辑<p>-
     * 10.采购单确认收货<p>-
     * 11.采购单关联售后单操作<p>-
     * 更新采购单updateDataTime
     * @param operateType 和上述编号一致
     * @Author:strange
     * @Date:10:15 2020-04-06
     */
    public static final String BUY_VB_ORDER_GENERATE="1";
    public static final String BUY_VP_ORDER_GENERATE="2";
    public static final String BUY_ORDER_VAILD="3";
    public static final String BUY_ORDER_PAY="4";
    public static final String BUY_ORDER_IN="5";
    public static final String BUY_ORDER_END="6";
    public static final String BUY_ORDER_CLOSE="7";
    public static final String BUY_ORDER_INVOICE="8";
    public static final String BUY_ORDER_EDIT="9";
    public static final String BUY_ORDER_CONFIRM="10";
    public static final String BUY_ORDER_AFTERORDER_OPY="11";

    /**
     *处理售后换货,售后退货
     * 采购单和销售单售后情况
     * 1.生成售后单<p>-
     * 2.售后单生效审核<p>-
     * 3.销售换货入库<p>-
     * 4.销售换货出库<p>-
     * 5.销售退货入库<p>-
     * 6.采购退货出库<p>-
     * 7.采购换货出库<p>-
     * 8.采购换货入库<p>-
     * 9.售后单执行退款运算<p>-
     * 10.售后单完结<p>-
     * 11.售后单关闭<p>-
     * 12.售后单结款<p>-
     * 13.售后录票<p>-
     * 更新售后单updateDataTime
     * @param operateType 和上述编号一致
     * @Author:strange
     * @Date:10:15 2020-04-06
     */
    public static final String AFTER_ORDER_GENERATE="1";
    public static final String AFTER_ORDER_VAILD="2";
    public static final String AFTER_ORDER_SALE_EXCHANGE_IN="3";
    public static final String AFTER_ORDER_SALE_EXCHANGE_OUT="4";
    public static final String AFTER_ORDER_SALE_RETURN_IN="5";
    public static final String AFTER_ORDER_BUY_RETURN_OUT="6";
    public static final String AFTER_ORDER_BUY_EXCHANGE_OUT="7";
    public static final String AFTER_ORDER_BUY_EXCHANGE_IN="8";
    public static final String AFTER_ORDER_REFUND="9";
    public static final String AFTER_ORDER_END="10";
    public static final String AFTER_ORDER_CLOSE="11";
    public static final String AFTER_ORDER_PAY="12";
    public static final String AFTER_ORDER_INVOICE="13";
}
