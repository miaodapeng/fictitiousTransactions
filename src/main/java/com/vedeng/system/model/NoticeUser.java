package com.vedeng.system.model;

public class NoticeUser {
    private Integer noticeUserId;

    private Integer noticeId;

    private Integer userId;

    private Long addTime;

    public Integer getNoticeUserId() {
        return noticeUserId;
    }

    public void setNoticeUserId(Integer noticeUserId) {
        this.noticeUserId = noticeUserId;
    }

    public Integer getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }
}