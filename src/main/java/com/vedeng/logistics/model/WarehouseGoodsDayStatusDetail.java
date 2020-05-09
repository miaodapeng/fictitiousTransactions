package com.vedeng.logistics.model;

import java.io.Serializable;

public class WarehouseGoodsDayStatusDetail implements Serializable{
    private Integer warehouseGoodsDayStatusDetailId;

    private Integer warehouseGoodsDayStatusId;

    private Integer saleorderId;

    private String saleorderNo;

    private Integer num;

    public Integer getWarehouseGoodsDayStatusDetailId() {
        return warehouseGoodsDayStatusDetailId;
    }

    public void setWarehouseGoodsDayStatusDetailId(Integer warehouseGoodsDayStatusDetailId) {
        this.warehouseGoodsDayStatusDetailId = warehouseGoodsDayStatusDetailId;
    }

    public Integer getWarehouseGoodsDayStatusId() {
        return warehouseGoodsDayStatusId;
    }

    public void setWarehouseGoodsDayStatusId(Integer warehouseGoodsDayStatusId) {
        this.warehouseGoodsDayStatusId = warehouseGoodsDayStatusId;
    }

    public Integer getSaleorderId() {
        return saleorderId;
    }

    public void setSaleorderId(Integer saleorderId) {
        this.saleorderId = saleorderId;
    }

    public String getSaleorderNo() {
        return saleorderNo;
    }

    public void setSaleorderNo(String saleorderNo) {
        this.saleorderNo = saleorderNo == null ? null : saleorderNo.trim();
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}