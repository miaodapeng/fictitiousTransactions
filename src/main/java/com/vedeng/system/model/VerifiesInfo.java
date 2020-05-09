package com.vedeng.system.model;

import java.io.Serializable;
import java.util.List;

public class VerifiesInfo implements Serializable{
    private Integer verifiesInfoId;

    private String relateTable;

    private Integer relateTableKey;

    private String verifyUsername;
    
    private Integer verifiesType;
    
    private Integer status;

    private Long addTime;

    private Long modTime;

    private List<Integer> relateTableKeys;//关联主键ID集合
    
    public Integer getVerifiesInfoId() {
        return verifiesInfoId;
    }

    public void setVerifiesInfoId(Integer verifiesInfoId) {
        this.verifiesInfoId = verifiesInfoId;
    }

    public String getRelateTable() {
        return relateTable;
    }

    public void setRelateTable(String relateTable) {
        this.relateTable = relateTable == null ? null : relateTable.trim();
    }

    public Integer getRelateTableKey() {
        return relateTableKey;
    }

    public void setRelateTableKey(Integer relateTableKey) {
        this.relateTableKey = relateTableKey;
    }

    public String getVerifyUsername() {
        return verifyUsername;
    }

    public void setVerifyUsername(String verifyUsername) {
        this.verifyUsername = verifyUsername == null ? null : verifyUsername.trim();
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    public Long getModTime() {
        return modTime;
    }

    public void setModTime(Long modTime) {
        this.modTime = modTime;
    }

    public Integer getStatus() {
	return status;
    }

    public void setStatus(Integer status) {
	this.status = status;
    }

    public Integer getVerifiesType() {
	return verifiesType;
    }

    public void setVerifiesType(Integer verifiesType) {
	this.verifiesType = verifiesType;
    }

    public List<Integer> getRelateTableKeys() {
	return relateTableKeys;
    }

    public void setRelateTableKeys(List<Integer> relateTableKeys) {
	this.relateTableKeys = relateTableKeys;
    }
}