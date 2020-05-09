package com.vedeng.trader.service;

import javax.servlet.http.HttpSession;

import com.vedeng.common.service.BaseService;
import com.vedeng.trader.model.CommunicateRecord;

/**
 * <b>Description:</b><br> 沟通记录
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.trader.service
 * <br><b>ClassName:</b> CommunicateService
 * <br><b>Date:</b> 2017年7月7日 上午9:57:54
 */
public interface CommunicateService extends BaseService {
	/**
	 * <b>Description:</b><br> 更新历史沟通（处理状态）需传入沟通类型+关联主表ID
	 * @param communicateRecord 
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年7月7日 上午9:52:41
	 */
	Integer updateCommunicateDone(CommunicateRecord communicateRecord,HttpSession session);
	
	/**
	 * <b>Description:</b><br> 新增沟通
	 * @param communicateRecord
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年7月13日 下午1:26:49
	 */
	Integer addCommunicate(CommunicateRecord communicateRecord,HttpSession session);
	
}
