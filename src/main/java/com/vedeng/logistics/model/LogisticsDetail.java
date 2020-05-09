package com.vedeng.logistics.model;

import java.io.Serializable;

public class LogisticsDetail implements Serializable{
    private Integer logisticsDetailId;

    private String logisticsNo;

    private String content;

    private Long modTime;
    
    private String dateTime;//日期
    
    private String timeMillis;//时分秒
    
    private String detail;//详情
    
    public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getTimeMillis() {
		return timeMillis;
	}

	public void setTimeMillis(String timeMillis) {
		this.timeMillis = timeMillis;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Integer getLogisticsDetailId() {
        return logisticsDetailId;
    }

    public void setLogisticsDetailId(Integer logisticsDetailId) {
        this.logisticsDetailId = logisticsDetailId;
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo == null ? null : logisticsNo.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Long getModTime() {
        return modTime;
    }

    public void setModTime(Long modTime) {
        this.modTime = modTime;
    }
}