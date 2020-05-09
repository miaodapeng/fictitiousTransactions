package com.vedeng.order.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.vedeng.authorization.model.User;
import com.vedeng.goods.model.Goods;

public class QuoteorderConsult implements Serializable{
	
    private Integer quoteorderConsultId;

    private Integer quoteorderId;//报价单号

    private Integer type;//1销售咨询  2采购回复

    private String content;//内容

    private Long addTime;

    private Integer creator;
    
    private String quoteorderNo;//报价单号
    
    private Integer saleUserId;//销售人员ID
    
    private String saleUserName;//销售人员名称
    
    private Integer purchaseReplyNum;//采购回答次数
    
    private Integer saleQuizNum;//销售提问次数
    
    private Long replayTime;//最后一次回复时间
    
    private Integer consultStatus;//咨询答复
    
    private Long beginTime;
    
    private Long endTime;
    
    private Goods goods;//产品信息
    
    private Long sendTime;
    
    private Integer categoryId;//产品分类
    
    private List<User> userList;//产品负责人
    
    private List<Integer> categoryIdList;//分类集合
    
    private String goodsUserNm;//产品归属
    
    private Integer companyId;

    private String sourceQuae;  //根据VD 或者 BS搜索订单
    
    public List<Integer> getCategoryIdList() {
		return categoryIdList;
	}

	public void setCategoryIdList(List<Integer> categoryIdList) {
		this.categoryIdList = categoryIdList;
	}

	public String getGoodsUserNm() {
		return goodsUserNm;
	}

	public void setGoodsUserNm(String goodsUserNm) {
		this.goodsUserNm = goodsUserNm;
	}

	public Long getSendTime() {
		return sendTime;
	}

	public void setSendTime(Long sendTime) {
		this.sendTime = sendTime;
	}

	private Map<String , Map<String,Object>> consultMap;

	
	public Map<String, Map<String, Object>> getConsultMap() {
		return consultMap;
	}

	public void setConsultMap(Map<String, Map<String, Object>> consultMap) {
		this.consultMap = consultMap;
	}

	public Long getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Long beginTime) {
		this.beginTime = beginTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public String getQuoteorderNo() {
		return quoteorderNo;
	}

	public void setQuoteorderNo(String quoteorderNo) {
		this.quoteorderNo = quoteorderNo;
	}

	public Integer getSaleUserId() {
		return saleUserId;
	}

	public void setSaleUserId(Integer saleUserId) {
		this.saleUserId = saleUserId;
	}

	public String getSaleUserName() {
		return saleUserName;
	}

	public void setSaleUserName(String saleUserName) {
		this.saleUserName = saleUserName;
	}

	public Integer getPurchaseReplyNum() {
		return purchaseReplyNum;
	}

	public void setPurchaseReplyNum(Integer purchaseReplyNum) {
		this.purchaseReplyNum = purchaseReplyNum;
	}

	public Integer getSaleQuizNum() {
		return saleQuizNum;
	}

	public void setSaleQuizNum(Integer saleQuizNum) {
		this.saleQuizNum = saleQuizNum;
	}

	public Long getReplayTime() {
		return replayTime;
	}

	public void setReplayTime(Long replayTime) {
		this.replayTime = replayTime;
	}

	public Integer getConsultStatus() {
		return consultStatus;
	}

	public void setConsultStatus(Integer consultStatus) {
		this.consultStatus = consultStatus;
	}

	public Integer getQuoteorderConsultId() {
        return quoteorderConsultId;
    }

    public void setQuoteorderConsultId(Integer quoteorderConsultId) {
        this.quoteorderConsultId = quoteorderConsultId;
    }

    public Integer getQuoteorderId() {
        return quoteorderId;
    }

    public void setQuoteorderId(Integer quoteorderId) {
        this.quoteorderId = quoteorderId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
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

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getSourceQuae() {
		return sourceQuae;
	}

	public void setSourceQuae(String sourceQuae) {
		this.sourceQuae = sourceQuae;
	}
}