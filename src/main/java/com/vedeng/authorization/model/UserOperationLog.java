package com.vedeng.authorization.model;

import java.io.Serializable;

/**
 * <b>Description:</b><br> 员工操作日志bean
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.authorization.model
 * <br><b>ClassName:</b> UserOperationLog
 * <br><b>Date:</b> 2017年4月25日 上午11:07:31
 */
public class UserOperationLog implements Serializable{
    private Integer userOperationLogId;

    private Integer actionId;

    private Long accessTime;

    private String accessIp;

    private Integer operationType;

    private Integer userId;

    private Integer resultStatus;

    private String username;

    private String desc;

    private String beforeEntity;

    private String afterEntity;
    
    private String actionName;
    
    private Integer type;
    
    private Integer relatedId;
    
    private String differEntity;

    public Integer getUserOperationLogId() {
        return userOperationLogId;
    }

    public void setUserOperationLogId(Integer userOperationLogId) {
        this.userOperationLogId = userOperationLogId;
    }

    public Integer getActionId() {
        return actionId;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }

    public Long getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(Long accessTime) {
        this.accessTime = accessTime;
    }

    public String getAccessIp() {
        return accessIp;
    }

    public void setAccessIp(String accessIp) {
        this.accessIp = accessIp == null ? null : accessIp.trim();
    }

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(Integer resultStatus) {
        this.resultStatus = resultStatus;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    public String getBeforeEntity() {
        return beforeEntity;
    }

    public void setBeforeEntity(String beforeEntity) {
        this.beforeEntity = beforeEntity == null ? null : beforeEntity.trim();
    }

    public String getAfterEntity() {
        return afterEntity;
    }

    public void setAfterEntity(String afterEntity) {
        this.afterEntity = afterEntity == null ? null : afterEntity.trim();
    }

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getRelatedId() {
		return relatedId;
	}

	public void setRelatedId(Integer relatedId) {
		this.relatedId = relatedId;
	}

	public String getDifferEntity() {
		return differEntity;
	}

	public void setDifferEntity(String differEntity) {
		this.differEntity = differEntity;
	}
}