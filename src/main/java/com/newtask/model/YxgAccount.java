package com.newtask.model;

/**
 * @author Daniel
 * @date created in 2020/3/4 11:18
 */
public class YxgAccount {

    private Integer accountId;

    private String account;

    private String companyName;

    private String email;

    private String mobile;

    private String nickname;

    private Integer status;

    /**
     * 是否开通商家
     */
    private Integer isOpenStore;

    /**
     * 三证审核状态
     */
    private Integer companyStatus;

    /**
     * 资质审核状态
     */
    private Integer indentityStatus;

    private Long addTime;

    private Integer ssoAccountId;

    private String uuid;

    private String weixinOpenid;

    /**
     * erp联系人
     */
    private Integer employeeId;

    private Integer from;

    private Integer traderId;

    public Integer getTraderId() {
        return traderId;
    }

    public void setTraderId(Integer traderId) {
        this.traderId = traderId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsOpenStore() {
        return isOpenStore;
    }

    public void setIsOpenStore(Integer isOpenStore) {
        this.isOpenStore = isOpenStore;
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

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    public Integer getSsoAccountId() {
        return ssoAccountId;
    }

    public void setSsoAccountId(Integer ssoAccountId) {
        this.ssoAccountId = ssoAccountId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getWeixinOpenid() {
        return weixinOpenid;
    }

    public void setWeixinOpenid(String weixinOpenid) {
        this.weixinOpenid = weixinOpenid;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }
}
