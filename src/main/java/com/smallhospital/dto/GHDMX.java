package com.smallhospital.dto;

public class GHDMX {
    /**
     * 供货清单id，express_detail_id
     */
    private String supplyListId;

    /**
     * 已接收数量
     */
    private Integer receiveQuantity;

    public String getSupplyListId() {
        return supplyListId;
    }

    public void setSupplyListId(String supplyListId) {
        this.supplyListId = supplyListId;
    }

    public Integer getReceiveQuantity() {
        return receiveQuantity;
    }

    public void setReceiveQuantity(Integer receiveQuantity) {
        this.receiveQuantity = receiveQuantity;
    }

    @Override
    public String toString() {
        return "GHDMX{" +
                "supplyListId='" + supplyListId + '\'' +
                ", receiveQuantity=" + receiveQuantity +
                '}';
    }
}
