package com.vedeng.authorization.model;

import com.vedeng.common.validation.Interface.First;
import com.vedeng.common.validation.Interface.Second;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.GroupSequence;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * <b>Description:</b><br>
 * 员工bean
 * 
 * @author Jerry
 * @Note <b>ProjectName:</b> erp <br>
 *       <b>PackageName:</b> com.vedeng.authorization.model <br>
 *       <b>ClassName:</b> User <br>
 *       <b>Date:</b> 2017年4月25日 上午11:06:39
 */
// 通过@GroupSequence指定验证顺序：先验证First分组，如果有错误立即返回而不会验证Second分组
@GroupSequence({ First.class, Second.class, User.class })
public class User implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	private Integer userId;

	private Integer companyId;

	@NotEmpty(message = "用户名不允许为空", groups = { First.class })
	@Size(min = 2, max = 32, message = "用户名长度应该在2-32个字符之间", groups = { Second.class })
	@Pattern(regexp = "^[a-zA-Z0-9\\.]{2,32}$", message = "用户名不允许使用汉字和特殊字符", groups = { User.class })
	private String username;

	private String number;

	private String password;

	private String salt;

	private Integer parentId;

	private Integer isDisabled;

	private String disabledReason;

	private Long lastLoginTime;

	private String email;

	private String lastLoginIp, realName;

	private Long addTime;

	private Integer creator;

	private Long modTime;

	private Integer updater;

	private Integer orgId, positionId;// 归属部门，职位

	private String orgName, positionName, pUsername;// 部门名称，职位名称\直接上级

	private UserDetail userDetail;// 详细

	private List<Position> positions;// 职位集合

	private List<Role> roles;// 角色集合

	private UserAddress userAddress;// 地址

	private String orgIds; // 部门ID集合

	private String positionIds;// 职位ID集合

	private String roleIds;// 角色ID集合

	private Integer province, city, zone;// 地区

	private String birthday;

	private Integer positType;// 当前用户身份类型：销售、 采购...

	private Integer positLevel;// 当前用户身份级别

	private String ccNumber;

	private List<Integer> positionTypes;// 身份集合

	private Integer isAdmin;// 是否管理员0普通用户1管理员2超级管理员

	private String companyName;// 公司名称

	private Integer traderId;

	private String owners;

	private Integer categoryId;

	private Integer pUserId;

	private Integer orgId2;// 二级部ID

	private Integer orgId3;// 三级部ID

	/**
	 * 用户所属公司Id
	 */
	private Integer userBelongCompanyId;

	/**
	 * 是否贝登员工 1是 0否
	 */
	private Integer staff;

	/**
	 * 可登录系统1,2,3
	 */
	private String systems;

	/**
	 * 当前登陆系统
	 */
	private Integer currentLoginSystem;

	/**
	 * 页面传入字段所属公司
	 */
	private String belongCompanyName;

	public Integer getCurrentLoginSystem() {
		return currentLoginSystem;
	}

	public void setCurrentLoginSystem(Integer currentLoginSystem) {
		this.currentLoginSystem = currentLoginSystem;
	}

	public String getBelongCompanyName() {
		return belongCompanyName;
	}

	public void setBelongCompanyName(String belongCompanyName) {
		this.belongCompanyName = belongCompanyName == null ? null : belongCompanyName.trim();
	}

	public User() {
		super();
	}

	public boolean checkIsAdmin() {
		return isAdmin > 0;
	}

	public Integer getTraderId() {
		return traderId;
	}

	public void setTraderId(Integer traderId) {
		this.traderId = traderId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}

	public String getNumber() {
		return number;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public void setNumber(String number) {
		this.number = number == null ? null : number.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt == null ? null : salt.trim();
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getDisabledReason() {
		return disabledReason;
	}

	public void setDisabledReason(String disabledReason) {
		this.disabledReason = disabledReason == null ? null : disabledReason.trim();
	}

	public Long getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Long lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp == null ? null : lastLoginIp.trim();
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

	public UserDetail getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

	public List<Position> getPositions() {
		return positions;
	}

	public void setPositions(List<Position> positions) {
		this.positions = positions;
	}

	public Integer getIsDisabled() {
		return isDisabled;
	}

	public void setIsDisabled(Integer isDisabled) {
		this.isDisabled = isDisabled;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Integer getPositionId() {
		return positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public UserAddress getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(UserAddress userAddress) {
		this.userAddress = userAddress;
	}

	public String getOrgIds() {
		return orgIds;
	}

	public void setOrgIds(String orgIds) {
		this.orgIds = orgIds;
	}

	public String getPositionIds() {
		return positionIds;
	}

	public void setPositionIds(String positionIds) {
		this.positionIds = positionIds;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public Integer getProvince() {
		return province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public Integer getZone() {
		return zone;
	}

	public void setZone(Integer zone) {
		this.zone = zone;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getpUsername() {
		return pUsername;
	}

	public void setpUsername(String pUsername) {
		this.pUsername = pUsername;
	}

	public Integer getPositType() {
		return positType;
	}

	public void setPositType(Integer positType) {
		this.positType = positType;
	}

	public Integer getPositLevel() {
		return positLevel;
	}

	public void setPositLevel(Integer positLevel) {
		this.positLevel = positLevel;
	}

	public String getCcNumber() {
		return ccNumber;
	}

	public void setCcNumber(String ccNumber) {
		this.ccNumber = ccNumber;
	}

	public List<Integer> getPositionTypes() {
		return positionTypes;
	}

	public void setPositionTypes(List<Integer> positionTypes) {
		this.positionTypes = positionTypes;
	}

	public Integer getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName == null ? null
				: companyName.replaceAll("\\(", "（").replaceAll("\\)", "）").trim();
	}

	public String getOwners() {
		return owners;
	}

	public void setOwners(String owners) {
		this.owners = owners;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getpUserId() {
		return pUserId;
	}

	public void setpUserId(Integer pUserId) {
		this.pUserId = pUserId;
	}

	public Integer getOrgId2() {
		return orgId2;
	}

	public void setOrgId2(Integer orgId2) {
		this.orgId2 = orgId2;
	}

	public Integer getOrgId3() {
		return orgId3;
	}

	public void setOrgId3(Integer orgId3) {
		this.orgId3 = orgId3;
	}

	public Integer getStaff() {
		return staff;
	}

	public void setStaff(Integer staff) {
		this.staff = staff;
	}

	public String getSystems() {
		return systems;
	}

	public void setSystems(String systems) {
		this.systems = systems;
	}

	public Integer getUserBelongCompanyId() {
		return userBelongCompanyId;
	}

	public void setUserBelongCompanyId(Integer userBelongCompanyId) {
		this.userBelongCompanyId = userBelongCompanyId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Object clone() {
		User o = null;
		try {
			o = (User) super.clone();
		} catch (CloneNotSupportedException e) {
			System.out.println(e.toString());
		}
		return o;
	}
	public boolean hasRole(String roleName){
		if ( checkIsAdmin()){
			return true;
		}
		if(CollectionUtils.isNotEmpty( getRoles())){
			for(Role role: getRoles()){
				if(StringUtils.equalsIgnoreCase(roleName,role.getRoleName()) ){
					return true;
				}
			}
		}
		return false;
	}

}