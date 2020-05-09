
package com.vedeng.common.constant;
/**
 * <b>Description: db的rest接口uri常量类</b><br> 
 * <b>Author: Franlin</b> 
 * @fileName DbRestInterfaceConstant.java
 * <br><b>Date: 2018年10月28日 上午10:00:04 </b> 
 *
 */
public interface DbRestInterfaceConstant {

    /**
     * 耗材商城订单列表页面
     * /rest/order/hc/v1/search/orders
     * POST
     */
    public static final String HC_ORDER_LIST_URI = "/rest/order/hc/v1/search/orders";

    /**
     * 售后线上退款
     * /rest/finance/operate/afterSaleOnlineRefund
     */
    public static final String DB_REST_FINANCE_OPERATE_AFTER_SALE_ONLINE_REFUND = "/rest/finance/operate/afterSaleOnlineRefund";
    
}

