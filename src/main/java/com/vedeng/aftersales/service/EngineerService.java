package com.vedeng.aftersales.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.vedeng.aftersales.model.Engineer;
import com.vedeng.aftersales.model.vo.EngineerVo;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;

public interface EngineerService extends BaseService{

	/**
	 * <b>Description:</b><br> 工程师列表
	 * @param engineer
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年9月25日 上午10:39:21
	 */
	Map<String, Object> querylistPage(EngineerVo engineerVo, Page page);

	/**
	 * <b>Description:</b><br> 保存新增工程师
	 * @param engineer
	 * @param request
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年9月25日 下午2:03:50
	 */
	ResultInfo saveAdd(Engineer engineer, HttpServletRequest request, HttpSession session);

	/**
	 * <b>Description:</b><br> 禁用工程师
	 * @param engineer
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年9月25日 下午4:33:24
	 */
	ResultInfo saveChangeEnable(Engineer engineer, HttpSession session);

	/**
	 * <b>Description:</b><br> 工程师详情
	 * @param engineer
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年9月26日 上午9:36:14
	 */
	Map<String, Object> getEngineer(Engineer engineer, Page page);

	/**
	 * <b>Description:</b><br> 工程师详情
	 * @param engineer
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年9月26日 上午10:41:52
	 */
	Engineer getEngineerInfo(Engineer engineer);

	/**
	 * <b>Description:</b><br> 保存编辑工程师
	 * @param engineer
	 * @param request
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年9月26日 上午11:13:11
	 */
	ResultInfo saveEdit(Engineer engineer, HttpServletRequest request, HttpSession session);

}
