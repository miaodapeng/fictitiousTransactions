package com.vedeng.aftersales.model;

import java.io.Serializable;

public class AfterSalesRecord implements Serializable{
    /**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private Integer afterSalesRecordId;

    private Integer afterSalesId;

    private String content;

    private Long addTime;

    private Integer creator;

    private Long modTime;

    private Integer updater;

    public Integer getAfterSalesRecordId() {
        return afterSalesRecordId;
    }

    public void setAfterSalesRecordId(Integer afterSalesRecordId) {
        this.afterSalesRecordId = afterSalesRecordId;
    }

    public Integer getAfterSalesId() {
        return afterSalesId;
    }

    public void setAfterSalesId(Integer afterSalesId) {
        this.afterSalesId = afterSalesId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
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
}