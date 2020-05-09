package com.vedeng.system.model;

import java.io.Serializable;

import com.vedeng.authorization.model.UserDetail;

public class MessageUser implements Serializable{
    private Integer messageUserId;

    private Integer messageId;

    private Integer userId;

    private Integer isView;

    private Long viewTime;
    
    private Message message;//详细

    public Integer getMessageUserId() {
        return messageUserId;
    }

    public void setMessageUserId(Integer messageUserId) {
        this.messageUserId = messageUserId;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getIsView() {
        return isView;
    }

    public void setIsView(Integer isView) {
        this.isView = isView;
    }

    public Long getViewTime() {
        return viewTime;
    }

    public void setViewTime(Long viewTime) {
        this.viewTime = viewTime;
    }

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}
}