package com.vedeng.aftersales.service;

import javax.servlet.http.HttpSession;

import com.vedeng.aftersales.model.AfterSalesInstallstion;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.service.BaseService;

public interface AfterSalesInstallstionService  extends BaseService {

	/**
	 * <b>Description:</b><br> 获取安调信息
	 * @param afterSalesInstallstion
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年9月26日 下午3:23:46
	 */
	AfterSalesInstallstion getAfterSalesInstallstion(AfterSalesInstallstion afterSalesInstallstion);

	/**
	 * <b>Description:</b><br> 安调评分
	 * @param afterSalesInstallstion
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年9月26日 下午3:24:57
	 */
	ResultInfo saveInstallstionScore(AfterSalesInstallstion afterSalesInstallstion, HttpSession session);

}
