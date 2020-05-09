package com.rest.traderMsg.controller;

import java.util.List;

public class WebAccountCertificateMsg {
    private Integer messageTemplateId;
    private String traderName;
    private String url;
    private List<Integer> userIds;
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public List<Integer> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Integer> userIds) {
        this.userIds = userIds;
    }

    public Integer getMessageTemplateId() {
        return messageTemplateId;
    }

    public void setMessageTemplateId(Integer messageTemplateId) {
        this.messageTemplateId = messageTemplateId;
    }

    public String getTraderName() {
        return traderName;
    }

    public void setTraderName(String traderName) {
        this.traderName = traderName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
