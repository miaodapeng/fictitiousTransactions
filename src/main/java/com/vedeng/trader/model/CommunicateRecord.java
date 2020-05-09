package com.vedeng.trader.model;

import java.io.Serializable;
import java.util.List;

import com.vedeng.authorization.model.User;
import com.vedeng.order.model.vo.QuoteorderVo;
import com.vedeng.phoneticWriting.model.Comment;
import com.vedeng.system.model.Tag;
import com.vedeng.trader.model.vo.TraderCustomerVo;

public class CommunicateRecord implements Serializable{
    /**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private Integer communicateRecordId;

    private Integer communicateType;
    
    private Integer companyId;

    private Integer relatedId;

    private Long begintime;

    private Long endtime;

    private Integer traderContactId;

    private Integer communicateMode;

    private Integer communicateGoal;

    private String nextContactDate;

    private String phone;

    private String coid;

    private Integer coidType;
    
    private Integer coidLength;

    private String coidDomain;

    private String coidUri;

    private String nextContactContent;

    private String comments;

    private Integer isDone;
    
    private Long addTime;

    private Integer creator;
    
    private Long modTime;

    private Integer updater;

    private Integer syncStatus;
    
    private String creatorName;
    
    private String contact;//联系人
    
    private String contactMob;//联系电话
    
    private Integer traderId,traderCustomerId,traderSupplierId;
    
    private List<Integer> traderCustomerIds;//客户
    
    private List<Integer> traderSupplierIds;//供应商
    
    private List<Integer> enquiryOrderIds;//询价
    
    private List<Integer> quoteOrderIds;//报价
    
    private List<Integer> saleOrderIds;//销售
    
    private	List<Integer> buyOrderIds;//采购订单
    
    private List<Integer> serviceOrderIds;//售后
    
    private List<Integer> bussinessChanceIds;//商机
    
    private List<Tag> tag;
    
    private List<String> tagName;
    
    private User user;
    
    private String contactName,communicateModeName,communicateGoalName;//联系人， 沟通方法、目的
    
    private Boolean isToday;
    
    private Integer bussinessChanceId,quoteorderId,saleorderId,buyorderId,afterSalesId;//商机、报价、订单、采购沟通记录

    private Integer communicateCount;//沟通次数
    
    private Long lastCommunicateTime;//上次沟通时间
    
    private Integer traderType;//客户类型
    
    private String bussinessChanceNo,quoteorderNo,saleorderNo;//商机、报价、订单单号
    
    private String traderName;//客户名称
    
    private String ownerUsername;//所属销售
    
    private String phoneArea;//归属地
    
    private String begindate;

    private String enddate;
    
    private Integer result;//通话结果 1接通 2未接通
    
    private List<Integer> traderIds;//客户ID
    
    private String number;//用户工号
    
    private String buyorderNo;//采购单号
    
    private String aftersalesNo;//售后单号
    
    private List<QuoteorderVo> quoteorderVolist;//报价关联的沟通记录
    
    private List<TraderCustomerVo> traderCustomerVos;
    
    private List<Integer> userIds;//
    /*** 2018-08-09 ***/
    private Integer afterSalesTraderId;//售后对象Id
    
    private String afterSalesTraderName;//售后对象名称
    
    private String contactContent;//沟通内容
    
    private Integer relateCommunicateRecordId;//关联沟通Id（通话录音Id）

	private Integer beginValue;//通话时长初始值

	private Integer endValue;//通话时长结束值

	private Integer isComment;//是否已点评

	private List<Comment> commentList;//录音下的所有点评

	private Integer isTranslation;//是否转译完成

	public Integer getIsTranslation() {
		return isTranslation;
	}

	public void setIsTranslation(Integer isTranslation) {
		this.isTranslation = isTranslation;
	}

	public List<Comment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}

	public Integer getIsComment() {
		return isComment;
	}

	public void setIsComment(Integer isComment) {
		this.isComment = isComment;
	}

	public Integer getBeginValue() {
		return beginValue;
	}

	public void setBeginValue(Integer beginValue) {
		this.beginValue = beginValue;
	}

	public Integer getEndValue() {
		return endValue;
	}

	public void setEndValue(Integer endValue) {
		this.endValue = endValue;
	}

	/*** END ***/



	public String getCreatorName() {
		return creatorName;
	}

	public List<Integer> getBussinessChanceIds() {
		return bussinessChanceIds;
	}

	public void setBussinessChanceIds(List<Integer> bussinessChanceIds) {
		this.bussinessChanceIds = bussinessChanceIds;
	}

	public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactMob() {
        return contactMob;
    }

    public void setContactMob(String contactMob) {
        this.contactMob = contactMob;
    }

    public String getAfterSalesTraderName() {
		return afterSalesTraderName;
	}

	public void setAfterSalesTraderName(String afterSalesTraderName) {
		this.afterSalesTraderName = afterSalesTraderName;
	}

	public Integer getAfterSalesTraderId() {
		return afterSalesTraderId;
	}

	public void setAfterSalesTraderId(Integer afterSalesTraderId) {
		this.afterSalesTraderId = afterSalesTraderId;
	}

	public String getContactContent() {
		return contactContent;
	}

	public void setContactContent(String contactContent) {
		this.contactContent = contactContent;
	}

	public Integer getRelateCommunicateRecordId() {
		return relateCommunicateRecordId;
	}

	public void setRelateCommunicateRecordId(Integer relateCommunicateRecordId) {
		this.relateCommunicateRecordId = relateCommunicateRecordId;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

    public Integer getCommunicateRecordId() {
        return communicateRecordId;
    }

    public void setCommunicateRecordId(Integer communicateRecordId) {
        this.communicateRecordId = communicateRecordId;
    }

    public Integer getCommunicateType() {
        return communicateType;
    }

    public void setCommunicateType(Integer communicateType) {
        this.communicateType = communicateType;
    }

    public Integer getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(Integer relatedId) {
        this.relatedId = relatedId;
    }

    public Long getBegintime() {
        return begintime;
    }

    public void setBegintime(Long begintime) {
        this.begintime = begintime;
    }

    public Long getEndtime() {
        return endtime;
    }

    public void setEndtime(Long endtime) {
        this.endtime = endtime;
    }

    public Integer getTraderContactId() {
        return traderContactId;
    }

    public void setTraderContactId(Integer traderContactId) {
        this.traderContactId = traderContactId;
    }

    public Integer getCommunicateMode() {
        return communicateMode;
    }

    public void setCommunicateMode(Integer communicateMode) {
        this.communicateMode = communicateMode;
    }

    public Integer getCommunicateGoal() {
        return communicateGoal;
    }

    public void setCommunicateGoal(Integer communicateGoal) {
        this.communicateGoal = communicateGoal;
    }

    public String getNextContactDate() {
        return nextContactDate;
    }

    public void setNextContactDate(String nextContactDate) {
        this.nextContactDate = nextContactDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getCoid() {
        return coid;
    }

    public void setCoid(String coid) {
        this.coid = coid == null ? null : coid.trim();
    }

    public Integer getCoidLength() {
        return coidLength;
    }

    public void setCoidLength(Integer coidLength) {
        this.coidLength = coidLength;
    }

    public String getCoidDomain() {
        return coidDomain;
    }

    public void setCoidDomain(String coidDomain) {
        this.coidDomain = coidDomain == null ? null : coidDomain.trim();
    }

    public String getCoidUri() {
        return coidUri;
    }

    public void setCoidUri(String coidUri) {
        this.coidUri = coidUri == null ? null : coidUri.trim();
    }

    public String getNextContactContent() {
        return nextContactContent;
    }

    public void setNextContactContent(String nextContactContent) {
        this.nextContactContent = nextContactContent == null ? null : nextContactContent.trim();
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments == null ? null : comments.trim();
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

    public Integer getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(Integer syncStatus) {
        this.syncStatus = syncStatus;
    }

	public List<Integer> getTraderCustomerIds() {
		return traderCustomerIds;
	}

	public void setTraderCustomerIds(List<Integer> traderCustomerIds) {
		this.traderCustomerIds = traderCustomerIds;
	}

	public List<Integer> getTraderSupplierIds() {
		return traderSupplierIds;
	}

	public void setTraderSupplierIds(List<Integer> traderSupplierIds) {
		this.traderSupplierIds = traderSupplierIds;
	}

	public List<Integer> getEnquiryOrderIds() {
		return enquiryOrderIds;
	}

	public void setEnquiryOrderIds(List<Integer> enquiryOrderIds) {
		this.enquiryOrderIds = enquiryOrderIds;
	}

	public List<Integer> getQuoteOrderIds() {
		return quoteOrderIds;
	}

	public void setQuoteOrderIds(List<Integer> quoteOrderIds) {
		this.quoteOrderIds = quoteOrderIds;
	}

	public List<Integer> getSaleOrderIds() {
		return saleOrderIds;
	}

	public void setSaleOrderIds(List<Integer> saleOrderIds) {
		this.saleOrderIds = saleOrderIds;
	}

	public List<Integer> getBuyOrderIds() {
		return buyOrderIds;
	}

	public void setBuyOrderIds(List<Integer> buyOrderIds) {
		this.buyOrderIds = buyOrderIds;
	}

	public List<Integer> getServiceOrderIds() {
		return serviceOrderIds;
	}

	public void setServiceOrderIds(List<Integer> serviceOrderIds) {
		this.serviceOrderIds = serviceOrderIds;
	}

	public Integer getTraderCustomerId() {
		return traderCustomerId;
	}

	public void setTraderCustomerId(Integer traderCustomerId) {
		this.traderCustomerId = traderCustomerId;
	}

	public Integer getTraderSupplierId() {
		return traderSupplierId;
	}

	public void setTraderSupplierId(Integer traderSupplierId) {
		this.traderSupplierId = traderSupplierId;
	}

	public Integer getTraderId() {
		return traderId;
	}

	public void setTraderId(Integer traderId) {
		this.traderId = traderId;
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

	public List<Tag> getTag() {
		return tag;
	}

	public void setTag(List<Tag> tag) {
		this.tag = tag;
	}

	public List<String> getTagName() {
		return tagName;
	}

	public void setTagName(List<String> tagName) {
		this.tagName = tagName;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getCommunicateModeName() {
		return communicateModeName;
	}

	public void setCommunicateModeName(String communicateModeName) {
		this.communicateModeName = communicateModeName;
	}

	public String getCommunicateGoalName() {
		return communicateGoalName;
	}

	public void setCommunicateGoalName(String communicateGoalName) {
		this.communicateGoalName = communicateGoalName;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Boolean getIsToday() {
		return isToday;
	}

	public void setIsToday(Boolean isToday) {
		this.isToday = isToday;
	}

	public Integer getIsDone() {
		return isDone;
	}

	public void setIsDone(Integer isDone) {
		this.isDone = isDone;
	}

	public Integer getQuoteorderId() {
		return quoteorderId;
	}

	public void setQuoteorderId(Integer quoteorderId) {
		this.quoteorderId = quoteorderId;
	}

	public Integer getBussinessChanceId() {
		return bussinessChanceId;
	}

	public void setBussinessChanceId(Integer bussinessChanceId) {
		this.bussinessChanceId = bussinessChanceId;
	}

	public Integer getSaleorderId() {
		return saleorderId;
	}

	public void setSaleorderId(Integer saleorderId) {
		this.saleorderId = saleorderId;
	}

	public Integer getCommunicateCount() {
		return communicateCount;
	}

	public void setCommunicateCount(Integer communicateCount) {
		this.communicateCount = communicateCount;
	}

	public Long getLastCommunicateTime() {
		return lastCommunicateTime;
	}

	public void setLastCommunicateTime(Long lastCommunicateTime) {
		this.lastCommunicateTime = lastCommunicateTime;
	}

	public Integer getCoidType() {
		return coidType;
	}

	public void setCoidType(Integer coidType) {
		this.coidType = coidType;
	}

	public Integer getTraderType() {
		return traderType;
	}

	public void setTraderType(Integer traderType) {
		this.traderType = traderType;
	}

	public String getBussinessChanceNo() {
		return bussinessChanceNo;
	}

	public void setBussinessChanceNo(String bussinessChanceNo) {
		this.bussinessChanceNo = bussinessChanceNo;
	}

	public String getQuoteorderNo() {
		return quoteorderNo;
	}

	public void setQuoteorderNo(String quoteorderNo) {
		this.quoteorderNo = quoteorderNo;
	}

	public String getSaleorderNo() {
		return saleorderNo;
	}

	public void setSaleorderNo(String saleorderNo) {
		this.saleorderNo = saleorderNo;
	}

	public String getTraderName() {
		return traderName;
	}

	public void setTraderName(String traderName) {
		this.traderName = traderName==null ? null :traderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public String getOwnerUsername() {
		return ownerUsername;
	}

	public void setOwnerUsername(String ownerUsername) {
		this.ownerUsername = ownerUsername;
	}

	public String getPhoneArea() {
		return phoneArea;
	}

	public void setPhoneArea(String phoneArea) {
		this.phoneArea = phoneArea;
	}

	public String getBegindate() {
		return begindate;
	}

	public void setBegindate(String begindate) {
		this.begindate = begindate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public List<Integer> getTraderIds() {
		return traderIds;
	}

	public void setTraderIds(List<Integer> traderIds) {
		this.traderIds = traderIds;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Integer getBuyorderId() {
		return buyorderId;
	}

	public void setBuyorderId(Integer buyorderId) {
		this.buyorderId = buyorderId;
	}

	public Integer getAfterSalesId() {
		return afterSalesId;
	}

	public void setAfterSalesId(Integer afterSalesId) {
		this.afterSalesId = afterSalesId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getBuyorderNo() {
		return buyorderNo;
	}

	public void setBuyorderNo(String buyorderNo) {
		this.buyorderNo = buyorderNo;
	}

	public String getAftersalesNo() {
		return aftersalesNo;
	}

	public void setAftersalesNo(String aftersalesNo) {
		this.aftersalesNo = aftersalesNo;
	}

	public List<Integer> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<Integer> userIds) {
		this.userIds = userIds;
	}

	public List<QuoteorderVo> getQuoteorderVolist() {
		return quoteorderVolist;
	}

	public void setQuoteorderVolist(List<QuoteorderVo> quoteorderVolist) {
		this.quoteorderVolist = quoteorderVolist;
	}

	public List<TraderCustomerVo> getTraderCustomerVos() {
		return traderCustomerVos;
	}

	public void setTraderCustomerVos(List<TraderCustomerVo> traderCustomerVos) {
		this.traderCustomerVos = traderCustomerVos;
	}
	
}