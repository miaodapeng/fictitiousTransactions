package com.vedeng.homepage.model.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.vedeng.aftersales.model.vo.AfterSalesVo;
import com.vedeng.authorization.model.User;

/**
 * <b>Description:</b><br> 售后总监首页数据
 * @author east
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.homepage.model.vo
 * <br><b>ClassName:</b> AfterSalesDataVo
 * <br><b>Date:</b> 2017年12月26日 上午9:44:06
 */
public class AfterSalesDataVo implements Serializable{

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Integer> todayDoList;//今日 待办数据：
	
	private List<Integer> thisMonthDataList;//本月数据
	
	private List<Integer> thisYearDataList;//本年度数据
	
	private Map<Integer, List<AfterSalesVo>> aftersaleMap;//进行中的售后数据
	
	private List<Integer> aftersalesUserIdList;//售后人员的id集合
	
	private List<User> afterUserList;//售后人员
	
	private Integer companyId;//
	
	private Integer userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public List<Integer> getTodayDoList() {
		return todayDoList;
	}

	public void setTodayDoList(List<Integer> todayDoList) {
		this.todayDoList = todayDoList;
	}

	public List<Integer> getThisMonthDataList() {
		return thisMonthDataList;
	}

	public void setThisMonthDataList(List<Integer> thisMonthDataList) {
		this.thisMonthDataList = thisMonthDataList;
	}

	public List<Integer> getThisYearDataList() {
		return thisYearDataList;
	}

	public void setThisYearDataList(List<Integer> thisYearDataList) {
		this.thisYearDataList = thisYearDataList;
	}

	public Map<Integer, List<AfterSalesVo>> getAftersaleMap() {
		return aftersaleMap;
	}

	public void setAftersaleMap(Map<Integer, List<AfterSalesVo>> aftersaleMap) {
		this.aftersaleMap = aftersaleMap;
	}

	public List<Integer> getAftersalesUserIdList() {
		return aftersalesUserIdList;
	}

	public void setAftersalesUserIdList(List<Integer> aftersalesUserIdList) {
		this.aftersalesUserIdList = aftersalesUserIdList;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public List<User> getAfterUserList() {
		return afterUserList;
	}

	public void setAfterUserList(List<User> afterUserList) {
		this.afterUserList = afterUserList;
	}

}
