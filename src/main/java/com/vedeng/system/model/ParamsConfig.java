package com.vedeng.system.model;

import java.io.Serializable;

public class ParamsConfig implements Serializable{
    private Integer paramsConfigId;

    private Integer status;

    private Integer paramsKey;

    private String title;

    private String comments;

    public Integer getParamsConfigId() {
        return paramsConfigId;
    }

    public void setParamsConfigId(Integer paramsConfigId) {
        this.paramsConfigId = paramsConfigId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getParamsKey() {
        return paramsKey;
    }

    public void setParamsKey(Integer paramsKey) {
        this.paramsKey = paramsKey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments == null ? null : comments.trim();
    }
}