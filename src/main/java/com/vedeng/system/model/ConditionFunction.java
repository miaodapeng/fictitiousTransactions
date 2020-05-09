package com.vedeng.system.model;

import java.io.Serializable;

public class ConditionFunction implements Serializable{
    private Integer conditionFunctionId;

    private String name;

    private String conditionFunctionParams;

    private String comments;

    public Integer getConditionFunctionId() {
        return conditionFunctionId;
    }

    public void setConditionFunctionId(Integer conditionFunctionId) {
        this.conditionFunctionId = conditionFunctionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getConditionFunctionParams() {
        return conditionFunctionParams;
    }

    public void setConditionFunctionParams(String conditionFunctionParams) {
        this.conditionFunctionParams = conditionFunctionParams == null ? null : conditionFunctionParams.trim();
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments == null ? null : comments.trim();
    }
}