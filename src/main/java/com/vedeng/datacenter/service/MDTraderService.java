package com.vedeng.datacenter.service;

import java.util.Map;

import com.vedeng.authorization.model.User;
import com.vedeng.homepage.model.vo.EchartsDataVo;

public interface MDTraderService {

	/**
	 * <b>Description:</b><br> 获取总客户数
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年12月8日 上午11:04:46
	 */
	Integer getTotalCustomerNum();

	/**
	 * <b>Description:</b><br> 获取成交客户数
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年12月8日 上午11:05:06
	 */
	Integer getTotalTraderCustomerNum();

	/**
	 * <b>Description:</b><br> 获取多次成交客户数
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年12月8日 上午11:05:20
	 */
	Integer getTotalManyTraderCustomerNum();

	/**
	 * <b>Description:</b><br> 获取客户性质比例
	 * @param map 
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年12月13日 下午2:14:09
	 */

	Map<String, String> getCustomerNaturePro(Map<String, Object> map);

	/**
	 * <b>Description:</b><br> 获取客户等级比例
	 * @param map 
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年12月13日 下午4:46:11
	 */
	Map<String, String> getCustomerLevelPro(Map<String, Object> map);

	/**
	 * <b>Description:</b><br> 获取客户数概况
	 * @param map 
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年12月18日 下午3:36:47
	 */
	Map<String, String> getCustomerNumInfo(Map<String, Object> map);

	/**
	 * <b>Description:</b><br> 客户分部门占比,成交客户分部门占比,多次成交客户分部门占比
	 * @param echartsDataVo
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年12月19日 下午4:38:59
	 */
	EchartsDataVo getOrgTraderInfo(EchartsDataVo echartsDataVo);
	
	/**
	 * <b>Description:</b><br> 查询物流数据中心数据
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年1月11日 上午11:40:07
	 */
	EchartsDataVo getLogisticsEchartsDataVo(EchartsDataVo echartsDataVo);
	
	/**
	 * <b>Description:</b><br> 查询售后数据中心数据
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年1月11日 上午11:40:07
	 */
	EchartsDataVo getAftersalesDataCenter(EchartsDataVo echartsDataVo);
    

}
