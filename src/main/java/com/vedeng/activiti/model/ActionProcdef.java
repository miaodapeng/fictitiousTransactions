package com.vedeng.activiti.model;

import java.io.Serializable;

public class ActionProcdef implements Serializable{
    private Integer companyId;

    private Integer actionId;

    private String procdefId;

    private String preBusinessKey;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getActionId() {
        return actionId;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }

    public String getProcdefId() {
        return procdefId;
    }

    public void setProcdefId(String procdefId) {
        this.procdefId = procdefId == null ? null : procdefId.trim();
    }

    public String getPreBusinessKey() {
        return preBusinessKey;
    }

    public void setPreBusinessKey(String preBusinessKey) {
        this.preBusinessKey = preBusinessKey == null ? null : preBusinessKey.trim();
    }
}