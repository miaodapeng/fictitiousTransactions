package com.vedeng.homepage.model.vo;

import java.io.Serializable;
import java.util.List;

/**
 * <b>Description:</b><br> 堆叠柱状图DTO
 * @author east
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.homepage.model.vo
 * <br><b>ClassName:</b> ChildrenEchartsDataVo
 * <br><b>Date:</b> 2017年12月28日 下午5:25:07
 */
public class ChildrenEchartsDataVo implements Serializable{

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	
	private List<String> childrenList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getChildrenList() {
		return childrenList;
	}

	public void setChildrenList(List<String> childrenList) {
		this.childrenList = childrenList;
	}
	
	

}
