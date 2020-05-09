package com.vedeng.trader.model;

import java.io.Serializable;
import java.util.List;

import com.vedeng.system.model.Tag;

public class TraderTag implements Serializable{
    private Integer traderTagId;

    private Integer traderType;

    private Integer traderId;

    private Integer tagId;
    
    private Tag tag;

    public Integer getTraderTagId() {
        return traderTagId;
    }

    public void setTraderTagId(Integer traderTagId) {
        this.traderTagId = traderTagId;
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

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}


}