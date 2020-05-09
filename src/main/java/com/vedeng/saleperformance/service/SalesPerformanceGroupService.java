package com.vedeng.saleperformance.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.vedeng.authorization.model.User;
import com.vedeng.common.model.ResultInfo;
/**
 * 团队详情
 * <b>Description:</b><br>
 * @author bill.bo
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.saleperformance.service
 * <br><b>ClassName:</b> SalesPerformanceGroupService
 * <br><b>Date:</b> 2019年2月20日 上午8:59:12
 */
public interface SalesPerformanceGroupService {

	/**
	 * 团队详情
	 * <b>Description:</b>
	 * @param request
	 * @param user 
	 * @return ResultInfo<?>
	 * @Note
	 * <b>Author：</b> bill.bo
	 * <b>Date:</b> 2019年2月20日 上午9:12:15
	 */
	List<Map<String, Object>> getGroupDetail(HttpServletRequest request, User user);

	/**
	 * 小组详情
	 * <b>Description:</b><br> 
	 * @param request
	 * @param session
	 * @param saleorderId
	 * @return
	 * @Note
	 * <b>Author:</b> bill.bo
	 * <br><b>Date:</b> 2019年2月22日
	 */		
	List<Map<String, Object>> getDeptDetail(HttpServletRequest request, User user);
	
}
