package com.vedeng.system.model;

import java.io.Serializable;

public class VerifiesRecord implements Serializable{
    private Integer verifiesRecordId;

    private Integer verifiesType;

    private Integer relatedId;

    private Integer status;

    private Long addTime;

    private Integer creator;

    private String comments;
    
    private String username;

    public Integer getVerifiesRecordId() {
        return verifiesRecordId;
    }

    public void setVerifiesRecordId(Integer verifiesRecordId) {
        this.verifiesRecordId = verifiesRecordId;
    }

    public Integer getVerifiesType() {
        return verifiesType;
    }

    public void setVerifiesType(Integer verifiesType) {
        this.verifiesType = verifiesType;
    }

    public Integer getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(Integer relatedId) {
        this.relatedId = relatedId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments == null ? null : comments.trim();
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}