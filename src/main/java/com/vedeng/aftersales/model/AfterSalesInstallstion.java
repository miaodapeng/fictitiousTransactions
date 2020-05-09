package com.vedeng.aftersales.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class AfterSalesInstallstion implements Serializable{
    /**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private Integer afterSalesInstallstionId;

    private Integer afterSalesId;

    private Integer engineerId;

    private Long serviceTime;

    private BigDecimal engineerAmount;

    private Integer noticeTimes;

    private Long lastNoticeTime;

    private Integer serviceScore;

    private Integer skillScore;

    private Integer scoreUserId;

    private Long scoreTime;

    private String scoreComments;

    private Integer paymentStatus;

    private Long paymentTime;

    private Long addTime;

    private Integer creator;

    private Long modTime;

    private Integer updater;

    public Integer getAfterSalesInstallstionId() {
        return afterSalesInstallstionId;
    }

    public void setAfterSalesInstallstionId(Integer afterSalesInstallstionId) {
        this.afterSalesInstallstionId = afterSalesInstallstionId;
    }

    public Integer getAfterSalesId() {
        return afterSalesId;
    }

    public void setAfterSalesId(Integer afterSalesId) {
        this.afterSalesId = afterSalesId;
    }

    public Integer getEngineerId() {
        return engineerId;
    }

    public void setEngineerId(Integer engineerId) {
        this.engineerId = engineerId;
    }

    public Long getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(Long serviceTime) {
        this.serviceTime = serviceTime;
    }

    public BigDecimal getEngineerAmount() {
        return engineerAmount;
    }

    public void setEngineerAmount(BigDecimal engineerAmount) {
        this.engineerAmount = engineerAmount;
    }

    public Integer getNoticeTimes() {
        return noticeTimes;
    }

    public void setNoticeTimes(Integer noticeTimes) {
        this.noticeTimes = noticeTimes;
    }

    public Long getLastNoticeTime() {
        return lastNoticeTime;
    }

    public void setLastNoticeTime(Long lastNoticeTime) {
        this.lastNoticeTime = lastNoticeTime;
    }

    public Integer getServiceScore() {
        return serviceScore;
    }

    public void setServiceScore(Integer serviceScore) {
        this.serviceScore = serviceScore;
    }

    public Integer getSkillScore() {
        return skillScore;
    }

    public void setSkillScore(Integer skillScore) {
        this.skillScore = skillScore;
    }

    public Integer getScoreUserId() {
        return scoreUserId;
    }

    public void setScoreUserId(Integer scoreUserId) {
        this.scoreUserId = scoreUserId;
    }

    public Long getScoreTime() {
        return scoreTime;
    }

    public void setScoreTime(Long scoreTime) {
        this.scoreTime = scoreTime;
    }

    public String getScoreComments() {
        return scoreComments;
    }

    public void setScoreComments(String scoreComments) {
        this.scoreComments = scoreComments == null ? null : scoreComments.trim();
    }

    public Integer getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Integer paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Long getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Long paymentTime) {
        this.paymentTime = paymentTime;
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