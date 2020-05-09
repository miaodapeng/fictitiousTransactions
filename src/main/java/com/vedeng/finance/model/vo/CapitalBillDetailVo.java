package com.vedeng.finance.model.vo;

import com.vedeng.finance.model.CapitalBillDetail;

public class CapitalBillDetailVo extends CapitalBillDetail {

    /** 该流水所属订单的ID SALEORDER_ID**/
    private Integer saleorderId;

    public Integer getSaleorderId() {
        return saleorderId;
    }

    public void setSaleorderId(Integer saleorderId) {
        this.saleorderId = saleorderId;
    }
}
