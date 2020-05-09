package com.vedeng.trader.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class TraderAmountBill implements Serializable{
    /**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private Integer traderAmountBillId;

    private Integer traderType;

    private Integer traderId;

    private Integer event;

    private Long addTime;

    private Integer creator;

    private BigDecimal amount;

    private String content;

    private String comments;
    
    private Integer amountType;
    
    private String optEvent;

    
    public String getOptEvent() {
		return optEvent;
	}

	public void setOptEvent(String optEvent) {
		this.optEvent = optEvent;
	}

	public Integer getTraderAmountBillId() {
        return traderAmountBillId;
    }

    public void setTraderAmountBillId(Integer traderAmountBillId) {
        this.traderAmountBillId = traderAmountBillId;
    }

    public Integer getTraderType() {
        return traderType;
    }

    public void setTraderType(Integer traderType) {
        this.traderType = traderType;
    }

    public Integer getTraderId() {
        return traderId;
    }

    public void setTraderId(Integer traderId) {
        this.traderId = traderId;
    }

    public Integer getEvent() {
        return event;
    }

    public void setEvent(Integer event) {
        this.event = event;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments == null ? null : comments.trim();
    }

	public Integer getAmountType() {
		return amountType;
	}

	public void setAmountType(Integer amountType) {
		this.amountType = amountType;
	}
}