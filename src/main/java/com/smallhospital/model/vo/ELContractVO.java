package com.smallhospital.model.vo;

import com.smallhospital.model.ElContract;

public class ELContractVO extends ElContract {

    private String traderName;

    private String signDateStr;

    private String contractValidityDateStartStr;

    private String contractValidityDateEndStr;

    private String ownerName;

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getSignDateStr() {
        return signDateStr;
    }

    public void setSignDateStr(String signDateStr) {
        this.signDateStr = signDateStr;
    }

    public String getContractValidityDateStartStr() {
        return contractValidityDateStartStr;
    }

    public void setContractValidityDateStartStr(String contractValidityDateStartStr) {
        this.contractValidityDateStartStr = contractValidityDateStartStr;
    }

    public String getContractValidityDateEndStr() {
        return contractValidityDateEndStr;
    }

    public void setContractValidityDateEndStr(String contractValidityDateEndStr) {
        this.contractValidityDateEndStr = contractValidityDateEndStr;
    }

    public String getTraderName() {
        return traderName;
    }

    public void setTraderName(String traderName) {
        this.traderName = traderName;
    }
}
