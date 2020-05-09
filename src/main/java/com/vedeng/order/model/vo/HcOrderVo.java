
package com.vedeng.order.model.vo;

import java.io.Serializable;
import java.util.List;

import com.vedeng.finance.model.SaleorderData;
import com.vedeng.order.model.Saleorder;
import com.vedeng.system.model.SysOptionDefinition;


/**
 * <b>Description: 耗材商城</b><br> 
 * <b>Author: Franlin</b> 
 * @fileName HcOrderVo.java
 * <br><b>Date: 2018年10月26日 下午2:21:11 </b> 
 *
 */
public class HcOrderVo implements Serializable{

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -4363435643500069382L;

    /**
     * 订单列表
     */
    List<Saleorder> orderList;
    
    /**
     * 客户性质
     */
    List<SysOptionDefinition> customerNatures;
    
    /**
     * 根据销售订单ID查询账期付款总额-订单未还账期款---换成Jerry写的方法
     */
    List<SaleorderData> capitalBillList;

    public List<Saleorder> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Saleorder> orderList) {
        this.orderList = orderList;
    }

    public List<SysOptionDefinition> getCustomerNatures() {
        return customerNatures;
    }

    public void setCustomerNatures(List<SysOptionDefinition> customerNatures) {
        this.customerNatures = customerNatures;
    }

    public List<SaleorderData> getCapitalBillList() {
        return capitalBillList;
    }

    public void setCapitalBillList(List<SaleorderData> capitalBillList) {
        this.capitalBillList = capitalBillList;
    }
    
    
}

