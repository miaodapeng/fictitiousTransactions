package com.vedeng.phoneticWriting.model;

public class Comment {
    /** 点评id  COMMENT_ID **/
    private Integer commentId;

    /** 评论内容  CONTENT **/
    private String content;

    /** 添加时间  ADD_TIME **/
    private Long addTime;

    /** 添加人  CREATOR **/
    private Integer creator;

    /** 最近一次编辑时间  MOD_TIME **/
    private Long modTime;

    /** 最近一次编辑人  UPDATER **/
    private Integer updater;

    /** 沟通记录主键  RELATED_ID **/
    private Integer relatedId;

    // 评论人
    private String userName ;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(Integer relatedId) {
        this.relatedId = relatedId;
    }

    /**   点评id  COMMENT_ID   **/
    public Integer getCommentId() {
        return commentId;
    }

    /**   点评id  COMMENT_ID   **/
    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    /**   评论内容  CONTENT   **/
    public String getContent() {
        return content;
    }

    /**   评论内容  CONTENT   **/
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
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

    /**   最近一次编辑时间  MOD_TIME   **/
    public Long getModTime() {
        return modTime;
    }

    /**   最近一次编辑时间  MOD_TIME   **/
    public void setModTime(Long modTime) {
        this.modTime = modTime;
    }

    /**   最近一次编辑人  UPDATER   **/
    public Integer getUpdater() {
        return updater;
    }

    /**   最近一次编辑人  UPDATER   **/
    public void setUpdater(Integer updater) {
        this.updater = updater;
    }
}