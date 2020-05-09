package com.vedeng.authorization.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.GroupSequence;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.vedeng.common.validation.Interface.First;
import com.vedeng.common.validation.Interface.Second;

/**
 * <b>Description:</b><br> 员工详情bean
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.authorization.model
 * <br><b>ClassName:</b> UserDetail
 * <br><b>Date:</b> 2017年4月25日 上午11:07:07
 */
@GroupSequence({First.class, Second.class, UserDetail.class})
public class UserDetail implements Serializable{
    /**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private Integer userDetailId;

    private Integer userId;

    @NotEmpty(message="员工姓名不允许为空",groups = {First.class})
    @Size(min=2,max=16,message="员工姓名长度应该在2-16个字符之间",groups = {Second.class})
    @Pattern(regexp="^[a-zA-Z0-9\\u4e00-\\u9fa5\\.]{2,16}$",message="员工姓名不允许使用特殊字符",groups = {UserDetail.class})
    private String realName;

    @Pattern(regexp="^[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)?@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$|^$",message="邮箱格式错误",groups = {First.class})
    private String email;

    private Integer sex;

    private Date birthday;

    @Pattern(regexp="^1\\d{10}$|^$",message="手机格式错误",groups = {First.class})
    private String mobile;

    @Pattern(regexp="^(\\d{3,4}-?)?\\d{7,9}(-?\\d{2,6})?$|^$",message="电话格式错误",groups = {First.class})
    private String telephone;

    @Pattern(regexp="^(\\d{3,4}-?)?\\d{7,9}(-?\\d{2,6})?$|^$",message="传值格式错误",groups = {First.class})
    private String fax;

    private String qq;

    @Pattern(regexp="^\\d{4}$|^$",message="分机号为4位数字",groups = {First.class})
    private String ccNumber;

    public Integer getUserDetailId() {
        return userDetailId;
    }

    public void setUserDetailId(Integer userDetailId) {
        this.userDetailId = userDetailId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
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

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax == null ? null : fax.trim();
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    public String getCcNumber() {
        return ccNumber;
    }

    public void setCcNumber(String ccNumber) {
        this.ccNumber = ccNumber == null ? null : ccNumber.trim();
    }
}