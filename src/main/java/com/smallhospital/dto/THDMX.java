package com.smallhospital.dto;

public class THDMX {

    private String returnListId;

    private String returnId;

    private Integer supplyListId;

    private Integer returnQuantity;

    private String remark;

    public String getReturnListId() {
        return returnListId;
    }

    public void setReturnListId(String returnListId) {
        this.returnListId = returnListId;
    }

    public String getReturnId() {
        return returnId;
    }

    public void setReturnId(String returnId) {
        this.returnId = returnId;
    }

    public Integer getSupplyListId() {
        return supplyListId;
    }

    public void setSupplyListId(Integer supplyListId) {
        this.supplyListId = supplyListId;
    }

    public Integer getReturnQuantity() {
        return returnQuantity;
    }

    public void setReturnQuantity(Integer returnQuantity) {
        this.returnQuantity = returnQuantity;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "THDMX{" +
                "returnListId='" + returnListId + '\'' +
                ", returnId='" + returnId + '\'' +
                ", supplyListId='" + supplyListId + '\'' +
                ", returnQuantity=" + returnQuantity +
                ", remark='" + remark + '\'' +
                '}';
    }
}
