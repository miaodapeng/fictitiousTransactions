package com.vedeng.system.model;

import java.io.Serializable;

public class Notice implements Serializable{
    private Integer noticeId;

    private Integer companyId;

    private Integer noticeCategory;

    private String title;

    private Integer isEnable;
    
    private Integer isTop;

    private Long addTime;

    private Integer creator;

    private Long modTime;

    private Integer updater;

    private String content;
    
    private String creatorName;

    private String noticeCategoryName;
    
    public Integer getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getNoticeCategory() {
        return noticeCategory;
    }

    public void setNoticeCategory(Integer noticeCategory) {
        this.noticeCategory = noticeCategory;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
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

    public Long getModTime() {
        return modTime;
    }

    public void setModTime(Long modTime) {
        this.modTime = modTime;
    }

    public Integer getUpdater() {
        return updater;
    }

    public void setUpdater(Integer updater) {
        this.updater = updater;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getNoticeCategoryName() {
		return noticeCategoryName;
	}

	public void setNoticeCategoryName(String noticeCategoryName) {
		this.noticeCategoryName = noticeCategoryName;
	}

	public Integer getIsTop() {
		return isTop;
	}

	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}
}