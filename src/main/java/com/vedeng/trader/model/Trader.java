package com.vedeng.trader.model;

import java.io.Serializable;

public class Trader implements Serializable{
	private static final long serialVersionUID = 2202486432851713199L;

//	private static final long serialVersionUID = -8790034910321973070L;
	private Integer traderId;

    private Integer companyId;

    private Integer isEnable;

    private String traderName;

    private Integer areaId;

    private String areaIds;

    private String address;
    
    private Integer threeInOne;//三证合一
    
    private Integer medicalQualification;//医疗资质合一

    private Long addTime;

    private Integer creator;

    private Long modTime;

    private Integer updater;
    
    private TraderCustomer traderCustomer;
    
    private TraderSupplier traderSupplier;
    
    private Integer traderCustomerId,traderSupplierId;
    
    private TraderFinance traderFinance;
    
    private Integer traderType;
    
    private String traderNameBefore;//修改之前的客户名称
    
	private Integer openStore;
	
	private Integer openNum;
	
	private Integer ordergoodsStoreId;
	
	/**
	 * 表 T_TRADER_CUSTOMER
	 */
	private Integer customerType; //客户类型
	private String customerLevelStr;//客户等级
	
	private Integer customerNature;//客户性质
	/* 表: T_TRADER_CONTACT
	 * 对应TRADER_CONTACT_ID
	 * NAME
	 * TELEPHONE
	 * MOBILE
	 */
	private Integer traderContactId;
	private String traderContactName;//NAME
	private String traderContactTelephone;//TELEPHONE
	private String traderContactMobile;//MOBILE
	
	/**
	 * 表T_TRADER_ADDRESS
	 * 对应TRADER_ADDRESS_ID
	 * ADDRESS
	 */
	private Integer traderAddressId;
	private String traderAddress;//ADDRESS
	
	// add by franlin.wu for[耗材商城的客户同步，新增客户来源类型] begin
  	/**
	  * 来源0ERP 1耗材商城
	  */
	 private Integer source;
	// add by franlin.wu for[耗材商城的客户同步，新增客户来源类型] end
	 
	/**
     * 耗材商城客户ID
     */
    private Integer accountCompanyId;
	 
	 /**
	  * 客户资质状态
	  */
	 private Integer traderStatus;

	private Integer belongPlatform;//客户归属平台

	private Integer userId;//用户id
    
    public String getCustomerLevelStr() {
		return customerLevelStr;
	}

	public void setCustomerLevelStr(String customerLevelStr) {
		this.customerLevelStr = customerLevelStr;
	}

	public Integer getTraderAddressId() {
		return traderAddressId;
	}

	public void setTraderAddressId(Integer traderAddressId) {
		this.traderAddressId = traderAddressId;
	}


	public String getTraderAddress() {
		return traderAddress;
	}

	public void setTraderAddress(String traderAddress) {
		this.traderAddress = traderAddress;
	}

	public String getTraderContactName() {
		return traderContactName;
	}

	public void setTraderContactName(String traderContactName) {
		this.traderContactName = traderContactName;
	}

	public String getTraderContactTelephone() {
		return traderContactTelephone;
	}

	public void setTraderContactTelephone(String traderContactTelephone) {
		this.traderContactTelephone = traderContactTelephone;
	}

	public String getTraderContactMobile() {
		return traderContactMobile;
	}

	public void setTraderContactMobile(String traderContactMobile) {
		this.traderContactMobile = traderContactMobile;
	}

	public Integer getTraderContactId() {
		return traderContactId;
	}

	public void setTraderContactId(Integer traderContactId) {
		this.traderContactId = traderContactId;
	}


	public Integer getCustomerType() {
		return customerType;
	}

	public void setCustomerType(Integer customerType) {
		this.customerType = customerType;
	}

	public Integer getCustomerNature() {
		return customerNature;
	}

	public void setCustomerNature(Integer customerNature) {
		this.customerNature = customerNature;
	}

	public Integer getTraderType() {
		return traderType;
	}

	public void setTraderType(Integer traderType) {
		this.traderType = traderType;
	}
    
    public TraderFinance getTraderFinance() {
		return traderFinance;
	}

	public void setTraderFinance(TraderFinance traderFinance) {
		this.traderFinance = traderFinance;
	}

	public Integer getTraderId() {
        return traderId;
    }

    public void setTraderId(Integer traderId) {
        this.traderId = traderId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public String getTraderName() {
        return traderName;
    }

    public void setTraderName(String traderName) {
        this.traderName = traderName==null ? null :traderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(String areaIds) {
        this.areaIds = areaIds == null ? null : areaIds.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
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

	public TraderCustomer getTraderCustomer() {
		return traderCustomer;
	}

	public void setTraderCustomer(TraderCustomer traderCustomer) {
		this.traderCustomer = traderCustomer;
	}

	public TraderSupplier getTraderSupplier() {
		return traderSupplier;
	}

	public void setTraderSupplier(TraderSupplier traderSupplier) {
		this.traderSupplier = traderSupplier;
	}

	public Integer getThreeInOne() {
		return threeInOne;
	}

	public void setThreeInOne(Integer threeInOne) {
		this.threeInOne = threeInOne;
	}

	public Integer getMedicalQualification() {
		return medicalQualification;
	}

	public void setMedicalQualification(Integer medicalQualification) {
		this.medicalQualification = medicalQualification;
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

	public String getTraderNameBefore() {
	    return traderNameBefore;
	}

	public void setTraderNameBefore(String traderNameBefore) {
	    this.traderNameBefore = traderNameBefore;
	}

	public Integer getOpenStore() {
		return openStore;
	}

	public void setOpenStore(Integer openStore) {
		this.openStore = openStore;
	}

	public Integer getOpenNum() {
		return openNum;
	}

	public void setOpenNum(Integer openNum) {
		this.openNum = openNum;
	}

	public Integer getOrdergoodsStoreId() {
		return ordergoodsStoreId;
	}

	public void setOrdergoodsStoreId(Integer ordergoodsStoreId) {
		this.ordergoodsStoreId = ordergoodsStoreId;
	}

	public Integer getSource()
	{
		return source;
	}

	public void setSource(Integer source)
	{
		this.source = source;
	}
	
	public Integer getAccountCompanyId()
	{
		return accountCompanyId;
	}

	public void setAccountCompanyId(Integer accountCompanyId)
	{
		this.accountCompanyId = accountCompanyId;
	}

	public Integer getTraderStatus()
	{
		return traderStatus;
	}

	public void setTraderStatus(Integer traderStatus)
	{
		this.traderStatus = traderStatus;
	}

	public Integer getBelongPlatform() {
		return belongPlatform;
	}

	public void setBelongPlatform(Integer belongPlatform) {
		this.belongPlatform = belongPlatform;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}