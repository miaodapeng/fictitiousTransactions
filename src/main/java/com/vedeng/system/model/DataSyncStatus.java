package com.vedeng.system.model;

import java.io.Serializable;

public class DataSyncStatus implements Serializable{
    private Integer dataSyncStatusId;

    private Integer goalType;

    private Integer status;

    private String sourceTable;

    private Integer relatedId;

    private Long addTime;

    public Integer getDataSyncStatusId() {
        return dataSyncStatusId;
    }

    public void setDataSyncStatusId(Integer dataSyncStatusId) {
        this.dataSyncStatusId = dataSyncStatusId;
    }

    public Integer getGoalType() {
        return goalType;
    }

    public void setGoalType(Integer goalType) {
        this.goalType = goalType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSourceTable() {
        return sourceTable;
    }

    public void setSourceTable(String sourceTable) {
        this.sourceTable = sourceTable == null ? null : sourceTable.trim();
    }

    public Integer getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(Integer relatedId) {
        this.relatedId = relatedId;
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }
}