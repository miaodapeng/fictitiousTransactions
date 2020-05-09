package com.vedeng.trader.model;



import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WebAccount implements Serializable{
    private Integer erpAccountId;
    
    private Integer webAccountId;

    private Integer ssoAccountId;

    private Integer traderId,traderContactId;

    private Integer traderAddressId;

    private Integer userId;

    private Integer isEnable;

    private Integer from;

    private Integer companyStatus;

    private Integer indentityStatus;

    private Integer isOpenStore;

    private String account;

    private String email;

    private String mobile;

    private String companyName;

    private String name;

    private Integer sex;

    private String weixinOpenid;

    private String uuid;

    private Long addTime;

    private Long lastLoginTime;
    
    private Integer orgId;//部门

    private Integer isVedengJx;//是否是贝登精选会员(以废弃)

    private Integer isVedengJoin;//是否申请参加贝登精选

    private Long modTimeJoin; //申请加入时间

    private Long startAddDate,endAddDate;

    private String optType;

    private List<Integer> traders;
    private  Integer isSendMessage; //改用户是否推送过消息

    private Integer isVedengMember;//是否是贝登会员(新)

    private Date vedengMemberTime;//加入贝登会员时间
    /**
     * 注册平台(1贝登医疗，2医械购，3科研购，4集团业务部，5其他)
     */
    private Integer registerPlatform;
    /**
     * 归属平台（1贝登医疗，2医械购，3科研购，4集团业务部，5其他）
     */
    private Integer belongPlatform;


    private Integer status;
    private Integer customerNature;

    private Date modTime;

    public Integer getIsVedengMember() {
        return isVedengMember;
    }

    public void setIsVedengMember(Integer isVedengMember) {
        this.isVedengMember = isVedengMember;
    }

    public Date getVedengMemberTime() {
        return vedengMemberTime;
    }

    public void setVedengMemberTime(Date vedengMemberTime) {
        this.vedengMemberTime = vedengMemberTime;
    }

    public Integer getRegisterPlatform() {
        return registerPlatform;
    }

    public void setRegisterPlatform(Integer registerPlatform) {
        this.registerPlatform = registerPlatform;
    }

    public Integer getBelongPlatform() {
        return belongPlatform;
    }

    public void setBelongPlatform(Integer belongPlatform) {
        this.belongPlatform = belongPlatform;
    }

    public Integer getTraderId() {
        return traderId;
    }

    public void setTraderId(Integer traderId) {
        this.traderId = traderId;
    }

    public String getOptType() {
        return optType;
    }

    public void setOptType(String optType) {
        this.optType = optType;
    }

    public Long getStartAddDate() {
        return startAddDate;
    }

    public void setStartAddDate(Long startAddDate) {
        this.startAddDate = startAddDate;
    }

    public Long getEndAddDate() {
        return endAddDate;
    }

    public void setEndAddDate(Long endAddDate) {
        this.endAddDate = endAddDate;
    }


    public Integer getIsVedengJx() {
        return isVedengJx;
    }

    public void setIsVedengJx(Integer isVedengJx) {
        this.isVedengJx = isVedengJx;
    }

    public Integer getWebAccountId() {
        return webAccountId;
    }

    public void setWebAccountId(Integer webAccountId) {
        this.webAccountId = webAccountId;
    }

    public Integer getSsoAccountId() {
        return ssoAccountId;
    }

    public void setSsoAccountId(Integer ssoAccountId) {
        this.ssoAccountId = ssoAccountId;
    }

    public Integer getTraderContactId() {
        return traderContactId;
    }

    public void setTraderContactId(Integer traderContactId) {
        this.traderContactId = traderContactId;
    }

    public Integer getTraderAddressId() {
        return traderAddressId;
    }

    public void setTraderAddressId(Integer traderAddressId) {
        this.traderAddressId = traderAddressId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getCompanyStatus() {
        return companyStatus;
    }

    public void setCompanyStatus(Integer companyStatus) {
        this.companyStatus = companyStatus;
    }

    public Integer getIndentityStatus() {
        return indentityStatus;
    }

    public void setIndentityStatus(Integer indentityStatus) {
        this.indentityStatus = indentityStatus;
    }

    public Integer getIsOpenStore() {
        return isOpenStore;
    }

    public void setIsOpenStore(Integer isOpenStore) {
        this.isOpenStore = isOpenStore;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getWeixinOpenid() {
        return weixinOpenid;
    }

    public void setWeixinOpenid(String weixinOpenid) {
        this.weixinOpenid = weixinOpenid == null ? null : weixinOpenid.trim();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    public Long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

	public Integer getErpAccountId() {
		return erpAccountId;
	}

	public void setErpAccountId(Integer erpAccountId) {
		this.erpAccountId = erpAccountId;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

    public Integer getIsVedengJoin() {
        return isVedengJoin;
    }

    public void setIsVedengJoin(Integer isVedengJoin) {
        this.isVedengJoin = isVedengJoin;
    }

    public Long getModTimeJoin() {
        return modTimeJoin;
    }

    public void setModTimeJoin(Long modTimeJoin) {
        this.modTimeJoin = modTimeJoin;
    }

    public List<Integer> getTraders() {
        return traders;
    }

    public void setTraders(List<Integer> traders) {
        this.traders = traders;
    }

    public Integer getIsSendMessage() {
        return isSendMessage;
    }

    public void setIsSendMessage(Integer isSendMessage) {
        this.isSendMessage = isSendMessage;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCustomerNature() {
        return customerNature;
    }

    public void setCustomerNature(Integer customerNature) {
        this.customerNature = customerNature;
    }

    public Date getModTime() {
        return modTime;
    }

    public void setModTime(Date modTime) {
        this.modTime = modTime;
    }
}
