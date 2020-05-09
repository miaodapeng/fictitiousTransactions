package com.vedeng.trader.model;

public class CustomerAptitudeComment {

    private static final long serialVersionUID = 1L;
    /**
     * 客户资质备注标识
     */
    private Integer commentId;

    /**
     * 客户标识
     */
    private Integer traderCustomerId;

    /**
     * 创建人id
     */
    private Integer creator;

    /**
     * 添加的时间
     */
    private Long addTime;
    /**
     * 评论的内容
     */
    private String comment;

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getTraderCustomerId() {
        return traderCustomerId;
    }

    public void setTraderCustomerId(Integer traderCustomerId) {
        this.traderCustomerId = traderCustomerId;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
