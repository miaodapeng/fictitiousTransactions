package com.vedeng.system.model;

import java.io.Serializable;

public class Attachment implements Serializable{

    private Integer attachmentId;

    private Integer attachmentType;

    private Integer attachmentFunction;

    private Integer relatedId;

    private String name;

    private String domain;

    private String uri;

    private String alt;

    private Integer sort;

    private Integer isDefault;

    private Long addTime;

    private Integer creator;
    
    private String username;//操作人名称
    
    private String relatedNo;

    public Integer getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Integer attachmentId) {
        this.attachmentId = attachmentId;
    }

    public Integer getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(Integer attachmentType) {
        this.attachmentType = attachmentType;
    }

    public Integer getAttachmentFunction() {
        return attachmentFunction;
    }

    public void setAttachmentFunction(Integer attachmentFunction) {
        this.attachmentFunction = attachmentFunction;
    }

    public Integer getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(Integer relatedId) {
        this.relatedId = relatedId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain == null ? null : domain.trim();
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri == null ? null : uri.trim();
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt == null ? null : alt.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRelatedNo() {
		return relatedNo;
	}

	public void setRelatedNo(String relatedNo) {
		this.relatedNo = relatedNo;
	}
}