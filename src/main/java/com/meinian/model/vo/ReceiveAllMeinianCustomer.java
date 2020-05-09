package com.meinian.model.vo;

import lombok.Data;

/**
 * 功能描述 用户包装美年所有用户的信息类
 * className
 *
 * @author Bert
 * @date 2018/11/12 21:48
 * @Description //TODO
 * @version: 1.0
 */
@Data
public class ReceiveAllMeinianCustomer {

    /**
     *用户包装用户的选择该按钮
     */
    private String checkbox;

    /**
     * 客户编码
     */
    private String customercode;

    /**
     * 客户名称
     */
    private String customername;

    public String getCheckbox() {
        return checkbox;
    }

    public void setCheckbox(String checkbox) {
        this.checkbox = checkbox;
    }

    public String getCustomercode() {
        return customercode;
    }

    public void setCustomercode(String customercode) {
        this.customercode = customercode;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }
}
