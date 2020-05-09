package com.vedeng.authorization.model;

import java.io.Serializable;

/**
 * @Author wayne.liu
 * @Description 用户所属公司model
 * @Date 2019/6/6 9:36
 */
public class UserBelongCompany implements Serializable{

    private static final long serialVersionUID = -2310507068108114602L;
    /**
     * 主键
     */
    private Integer userBelongCompanyId;
    /**
     * 公司名称
     */
    private String companyName;

    public Integer getUserBelongCompanyId() {
        return userBelongCompanyId;
    }

    public void setUserBelongCompanyId(Integer userBelongCompanyId) {
        this.userBelongCompanyId = userBelongCompanyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "UserBelongCompany{" +
                "userBelongCompanyId=" + userBelongCompanyId +
                ", companyName='" + companyName + '\'' +
                '}';
    }
}