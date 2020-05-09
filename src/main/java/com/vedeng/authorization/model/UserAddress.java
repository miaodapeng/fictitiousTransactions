package com.vedeng.authorization.model;

import java.io.Serializable;

import javax.validation.GroupSequence;
import javax.validation.constraints.Size;

import com.vedeng.common.validation.Interface.First;
import com.vedeng.common.validation.Interface.Second;

/**
 * <b>Description:</b><br> 员工地址bean
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.authorization.model
 * <br><b>ClassName:</b> UserAddress
 * <br><b>Date:</b> 2017年4月25日 上午11:06:53
 */
@GroupSequence({First.class,Second.class,UserAddress.class})
public class UserAddress implements Serializable{
    /**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private Integer userAddressId;

    private Integer userId;

    private Integer areaId;

    private String areaIds;

    @Size(min=0,max=128,message="地址长度不允许超过128个字符",groups={First.class})
    private String address;

    public Integer getUserAddressId() {
        return userAddressId;
    }

    public void setUserAddressId(Integer userAddressId) {
        this.userAddressId = userAddressId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
}