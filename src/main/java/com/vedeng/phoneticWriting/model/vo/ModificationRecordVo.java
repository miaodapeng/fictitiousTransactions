package com.vedeng.phoneticWriting.model.vo;


import com.vedeng.phoneticWriting.model.ModificationRecord;

public class ModificationRecordVo extends ModificationRecord {

    private String addTimeStartStr;

    private String addTimeEndStr;

    private String modTimeStartStr;

    private String modTimeEndStr;

    private String creatorName;

    private String updaterName;

    private String addTimeStr;

    private String modTimeStr;

    private Long addTimeStart;

    private Long addTimeEnd;

    private Long modTimeStart;

    private Long modTimeEnd;

    public String getAddTimeStartStr() {
        return addTimeStartStr;
    }

    public void setAddTimeStartStr(String addTimeStartStr) {
        this.addTimeStartStr = addTimeStartStr;
    }

    public String getAddTimeEndStr() {
        return addTimeEndStr;
    }

    public void setAddTimeEndStr(String addTimeEndStr) {
        this.addTimeEndStr = addTimeEndStr;
    }

    public String getModTimeStartStr() {
        return modTimeStartStr;
    }

    public void setModTimeStartStr(String modTimeStartStr) {
        this.modTimeStartStr = modTimeStartStr;
    }

    public String getModTimeEndStr() {
        return modTimeEndStr;
    }

    public void setModTimeEndStr(String modTimeEndStr) {
        this.modTimeEndStr = modTimeEndStr;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getUpdaterName() {
        return updaterName;
    }

    public void setUpdaterName(String updaterName) {
        this.updaterName = updaterName;
    }

    public String getAddTimeStr() {
        return addTimeStr;
    }

    public void setAddTimeStr(String addTimeStr) {
        this.addTimeStr = addTimeStr;
    }

    public String getModTimeStr() {
        return modTimeStr;
    }

    public void setModTimeStr(String modTimeStr) {
        this.modTimeStr = modTimeStr;
    }

    public Long getAddTimeStart() {
        return addTimeStart;
    }

    public void setAddTimeStart(Long addTimeStart) {
        this.addTimeStart = addTimeStart;
    }

    public Long getAddTimeEnd() {
        return addTimeEnd;
    }

    public void setAddTimeEnd(Long addTimeEnd) {
        this.addTimeEnd = addTimeEnd;
    }

    public Long getModTimeStart() {
        return modTimeStart;
    }

    public void setModTimeStart(Long modTimeStart) {
        this.modTimeStart = modTimeStart;
    }

    public Long getModTimeEnd() {
        return modTimeEnd;
    }

    public void setModTimeEnd(Long modTimeEnd) {
        this.modTimeEnd = modTimeEnd;
    }
}
