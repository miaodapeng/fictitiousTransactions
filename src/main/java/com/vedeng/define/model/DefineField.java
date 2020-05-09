package com.vedeng.define.model;

public class DefineField {
    /**   DEFINE_ID **/
    private Integer defineId;

    /** 业务唯一标识  BUSSINESS **/
    private String bussiness;

    /** 用户ID  USER_ID **/
    private Integer userId;

    /** 字段名  FIELD **/
    private String field;

    /** 添加时间  ADD_TIME **/
    private Long addTime;

    /** 添加人  CREATOR **/
    private Integer creator;
    
    private String fields;
    
    private String unfields;
    
    private Integer isShow;

    /**     DEFINE_ID   **/
    public Integer getDefineId() {
        return defineId;
    }

    /**     DEFINE_ID   **/
    public void setDefineId(Integer defineId) {
        this.defineId = defineId;
    }

    /**   业务唯一标识  BUSSINESS   **/
    public String getBussiness() {
        return bussiness;
    }

    /**   业务唯一标识  BUSSINESS   **/
    public void setBussiness(String bussiness) {
        this.bussiness = bussiness == null ? null : bussiness.trim();
    }

    /**   用户ID  USER_ID   **/
    public Integer getUserId() {
        return userId;
    }

    /**   用户ID  USER_ID   **/
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**   字段名  FIELD   **/
    public String getField() {
        return field;
    }

    /**   字段名  FIELD   **/
    public void setField(String field) {
        this.field = field == null ? null : field.trim();
    }

    /**   添加时间  ADD_TIME   **/
    public Long getAddTime() {
        return addTime;
    }

    /**   添加时间  ADD_TIME   **/
    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    /**   添加人  CREATOR   **/
    public Integer getCreator() {
        return creator;
    }

    /**   添加人  CREATOR   **/
    public void setCreator(Integer creator) {
        this.creator = creator;
    }

	public String getFields() {
		return fields;
	}

	public void setFields(String fields) {
		this.fields = fields;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	public String getUnfields() {
		return unfields;
	}

	public void setUnfields(String unfields) {
		this.unfields = unfields;
	}
}