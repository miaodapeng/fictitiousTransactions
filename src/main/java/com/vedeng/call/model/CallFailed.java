package com.vedeng.call.model;

import java.io.Serializable;

public class CallFailed implements Serializable{
    /**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private Integer callFailedId;
	
	private Integer companyId;

    private Integer logId;

    private String coid;

    private String callerNumber;

    private String traderName;

    private Integer userId;

    private Integer isDialBack;

    private Integer dialBackUserId;

    private Long addTime;
    
	private String starttime;// 开始时间

	private String endtime;// 结束时间
	
	private Long starttimeLong;//
	
	private Long endtimeLong;//
	
	private String username;//归属人员
	
	private String dialBackUserUsername;//话务人员
	
	private String phoneArea;//归属地
	
	private String url;//录音地址
	
	private Integer filelen;//录音时长

    public Integer getCallFailedId() {
        return callFailedId;
    }

    public void setCallFailedId(Integer callFailedId) {
        this.callFailedId = callFailedId;
    }

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public String getCoid() {
        return coid;
    }

    public void setCoid(String coid) {
        this.coid = coid == null ? null : coid.trim();
    }

    public String getCallerNumber() {
        return callerNumber;
    }

    public void setCallerNumber(String callerNumber) {
        this.callerNumber = callerNumber == null ? null : callerNumber.trim();
    }

    public String getTraderName() {
        return traderName;
    }

    public void setTraderName(String traderName) {
        this.traderName = traderName==null ? null :traderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getIsDialBack() {
        return isDialBack;
    }

    public void setIsDialBack(Integer isDialBack) {
        this.isDialBack = isDialBack;
    }

    public Integer getDialBackUserId() {
        return dialBackUserId;
    }

    public void setDialBackUserId(Integer dialBackUserId) {
        this.dialBackUserId = dialBackUserId;
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDialBackUserUsername() {
		return dialBackUserUsername;
	}

	public void setDialBackUserUsername(String dialBackUserUsername) {
		this.dialBackUserUsername = dialBackUserUsername;
	}

	public String getPhoneArea() {
		return phoneArea;
	}

	public void setPhoneArea(String phoneArea) {
		this.phoneArea = phoneArea;
	}

	public Long getStarttimeLong() {
		return starttimeLong;
	}

	public void setStarttimeLong(Long starttimeLong) {
		this.starttimeLong = starttimeLong;
	}

	public Long getEndtimeLong() {
		return endtimeLong;
	}

	public void setEndtimeLong(Long endtimeLong) {
		this.endtimeLong = endtimeLong;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getFilelen() {
		return filelen;
	}

	public void setFilelen(Integer filelen) {
		this.filelen = filelen;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
}