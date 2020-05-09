package com.smallhospital.model.vo;

import com.smallhospital.model.ElSku;

public class ELSkuVO extends ElSku {

    private String skuNo;

    private String skuName;

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSkuNo() {
        return skuNo;
    }

    public void setSkuNo(String skuNo) {
        this.skuNo = skuNo;
    }
}
