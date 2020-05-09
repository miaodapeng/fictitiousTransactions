package com.vedeng.authorization.model;

import java.io.Serializable;

import javax.validation.GroupSequence;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.vedeng.common.validation.Interface.First;
import com.vedeng.common.validation.Interface.Second;

/**
 * <b>Description:</b><br> 公司bean
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.authorization.model
 * <br><b>ClassName:</b> Company
 * <br><b>Date:</b> 2017年4月25日 上午11:01:35
 */
@GroupSequence({First.class, Second.class, Company.class})
public class Company implements Serializable{
    private Integer companyId;

    @NotEmpty(message="公司名称不允许为空",groups = {First.class})
    @Size(min=2,max=128,message="公司名称长度应该在2-128个字符之间",groups = {Second.class})
    @Pattern(regexp="^[a-zA-Z0-9\\u4e00-\\u9fa5\\.\\(\\)\\,]{2,128}$",message="公司名称不允许使用特殊字符",groups = {Company.class})
    private String companyName;

    @NotEmpty(message="访问地址不能为空",groups = {First.class})
    @Pattern(regexp="^((http:\\/\\/)|(https:\\/\\/))?([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}",message="访问地址格式错误",groups = {Second.class})
    private String domain;
    
    private Integer isEnable;

    private Long addTime;

    private Integer creator;

    private Long modTime;

    private Integer updater;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain == null ? null : domain.trim();
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

	public Integer getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Integer isEnable) {
		this.isEnable = isEnable;
	}
}