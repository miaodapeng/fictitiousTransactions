package com.vedeng.system.model;

import java.io.Serializable;
import java.util.List;

import com.vedeng.authorization.model.User;

public class SysOptionDefinition implements Serializable {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private Integer sysOptionDefinitionId;

	private Integer scope;

	private Integer pScope;

	private Integer parentId;

	private Integer status;

	private Integer sort;

	private String title;

	private String comments;

	private String relatedTable;

	private String relatedField;

	private String pTitle;

	private List<SysOptionDefinition> sysOptionDefinitions;

	private List<User> userList;// 产品类别管制归属

	private Integer num;// 数量
	private String optionType;

	public Integer getpScope() {
		return pScope;
	}

	public void setpScope(Integer pScope) {
		this.pScope = pScope;
	}

	public Integer getSysOptionDefinitionId() {
		return sysOptionDefinitionId;
	}

	public void setSysOptionDefinitionId(Integer sysOptionDefinitionId) {
		this.sysOptionDefinitionId = sysOptionDefinitionId;
	}

	public Integer getScope() {
		return scope;
	}

	public void setScope(Integer scope) {
		this.scope = scope;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments == null ? null : comments.trim();
	}

	public String getRelatedTable() {
		return relatedTable;
	}

	public void setRelatedTable(String relatedTable) {
		this.relatedTable = relatedTable == null ? null : relatedTable.trim();
	}

	public String getRelatedField() {
		return relatedField;
	}

	public void setRelatedField(String relatedField) {
		this.relatedField = relatedField == null ? null : relatedField.trim();
	}

	public String getpTitle() {
		return pTitle;
	}

	public void setpTitle(String pTitle) {
		this.pTitle = pTitle;
	}

	public List<SysOptionDefinition> getSysOptionDefinitions() {
		return sysOptionDefinitions;
	}

	public void setSysOptionDefinitions(List<SysOptionDefinition> sysOptionDefinitions) {
		this.sysOptionDefinitions = sysOptionDefinitions;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getOptionType() {
		return optionType;
	}

	public void setOptionType(String optionType) {
		this.optionType = optionType;
	}
}