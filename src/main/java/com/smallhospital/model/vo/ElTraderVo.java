package com.smallhospital.model.vo;

import com.smallhospital.model.ElTrader;

public class ElTraderVo extends ElTrader {

    private  String traderName;

    private  String address;

    private String ownerName;

    public String getTraderName() {
        return traderName;
    }

    public void setTraderName(String traderName) {
        this.traderName = traderName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
