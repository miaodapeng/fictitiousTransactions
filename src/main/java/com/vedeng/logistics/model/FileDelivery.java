package com.vedeng.logistics.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class FileDelivery implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5722315502967638455L;

	private Integer fileDeliveryId;
    
    private String fileDeliveryNo;
    
    private Integer companyId;

    private Integer sendType;

    private Integer verifyStatus;

    private Integer deliveryStatus;

    private Long deliveryTime;

    private Integer deliveryUserId;

    private Integer traderId;

    private Integer traderType;

    private String traderName;

    private Integer traderContactId;

    private String traderContactName;

    private String mobile;

    private String telephone;

    private Integer traderAddressId;

    private String area;
    
    private String address;

    private String content;

    private Long addTime;

    private Integer creator;

    private Long modTime;

    private Integer updater;
    
    private Integer delievryType;//寄送文件类型1商品样册 2非样册文件
    
    private Integer deliveryDept;//承运部门：1财务部门   2物流部门
    
    private Integer positionType;//登录用户所属部门
    /**
     * 
     * <b>Description:</b><br> 寄送形式名称
     * @return
     * @Note
     * <b>Author:</b> "Micheal"
     * <br><b>Date:</b> 2017年7月14日 下午6:05:15
     */
    private String sendTypeName;
    /**
     * 
     * <b>Description:</b><br> 审核状态名称
     * @return
     * @Note
     * <b>Author:</b> "Micheal"
     * <br><b>Date:</b> 2017年7月14日 下午6:05:15
     */
    private String verifyStatusName;
    /**
     * 
     * <b>Description:</b><br> 寄送状态名称
     * @return
     * @Note
     * <b>Author:</b> "Micheal"
     * <br><b>Date:</b> 2017年7月14日 下午6:05:15
     */
    private String deliveryStatusName;
    /**
     * 
     * <b>Description:</b><br> 操作人名称
     * @return
     * @Note
     * <b>Author:</b> "Micheal"
     * <br><b>Date:</b> 2017年7月14日 下午6:05:15
     */
    private String deliveryUsername;
    /**
     * 
     * <b>Description:</b><br> 申请人名称
     * @return
     * @Note
     * <b>Author:</b> "Micheal"
     * <br><b>Date:</b> 2017年7月14日 下午6:05:15
     */
    private String creatorUsername;
    /**
     * 
     * <b>Description:</b><br> 物流单号
     * @return
     * @Note
     * <b>Author:</b> "Micheal"
     * <br><b>Date:</b> 2017年7月14日 下午6:06:43
     */
    private String logisticsNo;
    /**
     * 
     * <b>Description:</b><br> 物流公司ID
     * @return
     * @Note
     * <b>Author:</b> "Micheal"
     * <br><b>Date:</b> 2017年7月14日 下午6:08:27
     */
    private Integer logisticsId;
    /**
     * 
     * <b>Description:</b><br> 物流公司名称
     * @return
     * @Note
     * <b>Author:</b> "Micheal"
     * <br><b>Date:</b> 2017年7月14日 下午6:08:27
     */
    private String logisticsName;
    /**
     * 
     * <b>Description:</b><br> 开始时间
     * @return
     * @Note
     * <b>Author:</b> Micheal
     * <br><b>Date:</b> 2017年7月17日 上午11:35:21
     */
    private Long beginTime;
    /**
     * 
     * <b>Description:</b><br> 结束时间
     * @return
     * @Note
     * <b>Author:</b> Micheal
     * <br><b>Date:</b> 2017年7月17日 上午11:35:21
     */
    private Long endTime;
    /**
     * 
     * <b>Description:</b><br> 搜索时间类型 1申请时间  2寄送时间
     * @return
     * @Note
     * <b>Author:</b> Micheal
     * <br><b>Date:</b> 2017年7月17日 上午11:35:21
     */
    private Integer type;
    /**
     * 
     * <b>Description:</b><br> 计重数量
     * @return
     * @Note
     * <b>Author:</b> Micheal
     * <br><b>Date:</b> 2017年7月17日 上午11:35:21
     */
    private Integer num;
    /**
     * 
     * <b>Description:</b><br> 费用
     * @return
     * @Note
     * <b>Author:</b> Micheal
     * <br><b>Date:</b> 2017年7月17日 上午11:35:21
     */
    private BigDecimal amount;
    
    private Integer deliveryEndTime;
    
    private Integer verifiesType;//审核类型
    
    private List<String> verifyUsernameList;//当前审核人
    
    private String addTimeStr;
    
    private Integer customerNature;//客户性质
    
    private Integer orderCount;//合作次数
    
    private String owner;//归属
    
    private String logisticsComments;//物流备注
    
    private Integer arrivalStatus;//
    
    private String deliveryTimeStr;
    
    private String bussinessNo;
    /*********************************************1.	文件寄送列表功能优化 – 增加关闭功能 Bert***************/
	/**
	 * 是否关闭0否1是
	 */
	private Integer isClosed = 0;
	
	/**
	 * 关闭备注
	 */
	private String closedComments  ;
	
	/**
	 * 更新人
	 */
	private String updaterName;
	
	public Integer getPositionType() {
		return positionType;
	}

	public void setPositionType(Integer positionType) {
		this.positionType = positionType;
	}

	public Integer getDelievryType() {
		return delievryType;
	}

	public void setDelievryType(Integer delievryType) {
		this.delievryType = delievryType;
	}

	public Integer getDeliveryDept() {
		return deliveryDept;
	}

	public void setDeliveryDept(Integer deliveryDept) {
		this.deliveryDept = deliveryDept;
	}

	public String getUpdaterName() {
		return updaterName;
	}
	
	public void setUpdaterName(String updaterName) {
		this.updaterName = updaterName;
	}
	
	/*********************************************1.	文件寄送列表功能优化 – 增加关闭功能 Bert***************/
	
	
	
	public Integer getIsClosed() {
		return isClosed;
	}
	
	public void setIsClosed(Integer isClosed) {
		this.isClosed = isClosed;
	}
	
	public String getClosedComments() {
		return closedComments;
	}
	
	public void setClosedComments(String closedComments) {
		this.closedComments = closedComments;
	}
	
	public String getBussinessNo() {
		return bussinessNo;
	}

	public void setBussinessNo(String bussinessNo) {
		this.bussinessNo = bussinessNo;
	}

	public Integer getFileDeliveryId() {
        return fileDeliveryId;
    }

    public void setFileDeliveryId(Integer fileDeliveryId) {
        this.fileDeliveryId = fileDeliveryId;
    }

    public Integer getSendType() {
        return sendType;
    }

    public void setSendType(Integer sendType) {
        this.sendType = sendType;
    }

    public Integer getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(Integer verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

    public Integer getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(Integer deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public Long getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Long deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Integer getDeliveryUserId() {
        return deliveryUserId;
    }

    public void setDeliveryUserId(Integer deliveryUserId) {
        this.deliveryUserId = deliveryUserId;
    }

    public Integer getTraderId() {
        return traderId;
    }

    public void setTraderId(Integer traderId) {
        this.traderId = traderId;
    }

    public Integer getTraderType() {
        return traderType;
    }

    public void setTraderType(Integer traderType) {
        this.traderType = traderType;
    }

    public String getTraderName() {
        return traderName;
    }

    public void setTraderName(String traderName) {
        this.traderName = traderName==null ? null :traderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
    }

    public Integer getTraderContactId() {
        return traderContactId;
    }

    public void setTraderContactId(Integer traderContactId) {
        this.traderContactId = traderContactId;
    }

    public String getTraderContactName() {
        return traderContactName;
    }

    public void setTraderContactName(String traderContactName) {
        this.traderContactName = traderContactName == null ? null : traderContactName.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public Integer getTraderAddressId() {
        return traderAddressId;
    }

    public void setTraderAddressId(Integer traderAddressId) {
        this.traderAddressId = traderAddressId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
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

    public String getDeliveryUsername() {
	return deliveryUsername;
    }

    public void setDeliveryUsername(String deliveryUsername) {
	this.deliveryUsername = deliveryUsername;
    }

    public String getCreatorUsername() {
	return creatorUsername;
    }

    public void setCreatorUsername(String creatorUsername) {
	this.creatorUsername = creatorUsername;
    }

    public String getSendTypeName() {
	return sendTypeName;
    }

    public void setSendTypeName(String sendTypeName) {
	this.sendTypeName = sendTypeName;
    }

    public String getVerifyStatusName() {
	return verifyStatusName;
    }

    public void setVerifyStatusName(String verifyStatusName) {
	this.verifyStatusName = verifyStatusName;
    }

    public String getDeliveryStatusName() {
	return deliveryStatusName;
    }

    public void setDeliveryStatusName(String deliveryStatusName) {
	this.deliveryStatusName = deliveryStatusName;
    }


    public Integer getLogisticsId() {
	return logisticsId;
    }

    public void setLogisticsId(Integer logisticsId) {
	this.logisticsId = logisticsId;
    }

    public String getLogisticsName() {
	return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
	this.logisticsName = logisticsName;
    }

    public String getLogisticsNo() {
	return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
	this.logisticsNo = logisticsNo;
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

    public Integer getDeliveryEndTime() {
	return deliveryEndTime;
    }

    public void setDeliveryEndTime(Integer deliveryEndTime) {
	this.deliveryEndTime = deliveryEndTime;
    }

    public Integer getType() {
	return type;
    }

    public void setType(Integer type) {
	this.type = type;
    }

    public Integer getNum() {
	return num;
    }

    public void setNum(Integer num) {
	this.num = num;
    }

    public BigDecimal getAmount() {
	return amount;
    }

    public void setAmount(BigDecimal amount) {
	this.amount = amount;
    }

    public String getArea() {
	return area;
    }

    public void setArea(String area) {
	this.area = area;
    }

    public String getFileDeliveryNo() {
	return fileDeliveryNo;
    }

    public void setFileDeliveryNo(String fileDeliveryNo) {
	this.fileDeliveryNo = fileDeliveryNo;
    }

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getVerifiesType() {
	    return verifiesType;
	}

	public void setVerifiesType(Integer verifiesType) {
	    this.verifiesType = verifiesType;
	}

	public List<String> getVerifyUsernameList() {
	    return verifyUsernameList;
	}

	public void setVerifyUsernameList(List<String> verifyUsernameList) {
	    this.verifyUsernameList = verifyUsernameList;
	}

	public String getAddTimeStr() {
		return addTimeStr;
	}

	public void setAddTimeStr(String addTimeStr) {
		this.addTimeStr = addTimeStr;
	}

	public Integer getCustomerNature() {
		return customerNature;
	}

	public void setCustomerNature(Integer customerNature) {
		this.customerNature = customerNature;
	}

	public Integer getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getLogisticsComments() {
		return logisticsComments;
	}

	public void setLogisticsComments(String logisticsComments) {
		this.logisticsComments = logisticsComments;
	}

	public Integer getArrivalStatus() {
		return arrivalStatus;
	}

	public void setArrivalStatus(Integer arrivalStatus) {
		this.arrivalStatus = arrivalStatus;
	}

	public String getDeliveryTimeStr() {
		return deliveryTimeStr;
	}

	public void setDeliveryTimeStr(String deliveryTimeStr) {
		this.deliveryTimeStr = deliveryTimeStr;
	}
}