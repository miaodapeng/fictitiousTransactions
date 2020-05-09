package com.vedeng.saleperformance.service;


import java.util.List;
import java.util.Map;

import com.vedeng.common.service.BaseService;
import com.vedeng.saleperformance.model.RSalesPerformanceGroupJUser;
import com.vedeng.saleperformance.model.vo.GroupMemberDetailsVo;
import com.vedeng.saleperformance.model.vo.SaleperformanceProcess;
import com.vedeng.saleperformance.model.vo.SalesPerformanceSortVo;
import com.vedeng.authorization.model.User;

public interface DirectorPerformanceService extends BaseService{

	/**
	 * 
	 * <b>Description:</b><br> 获取所有小组成员名单
	 * @param 
	 * @return
	 * @Note
	 * <b>Author:</b> Barry
	 * <br><b>Date:</b> 2018年06月05日 14:10
	 */
	List<RSalesPerformanceGroupJUser> getAllMenbersList();
	

	/**
	 * 
	 * <b>Description:</b>获取个人的数据<br> 
	 * @param 
	 * @return
	 * @Note
	 * <b>Author:</b> Barry
	 * <br><b>Date:</b> 2018年06月19日 14:02
	 */
	List<GroupMemberDetailsVo> getMemberDetails(GroupMemberDetailsVo groupMemberDetailsVo);
	
	/**
	 * 
	 * <b>Description:</b>获取小组的数据<br> 
	 * @param 
	 * @return
	 * @Note
	 * <b>Author:</b> Barry
	 * <br><b>Date:</b> 2018年06月19日 09:07
	 */
	Map<String, Object> getGroupList(Integer groupId);
	
	/**
	 * 
	 * <b>Description:</b>获取总监的数据（人员参数以Map形式传进来）<br> 
	 * @param 
	 * @return
	 * @Note
	 * <b>Author:</b> Barry
	 * <br><b>Date:</b> 2018年06月19日 15:01
	 */
	List<GroupMemberDetailsVo> getAreaDetailsList(GroupMemberDetailsVo groupMemberDetailsVo);
	/**
	 * 
	 * <b>Description: 根据部门Id获取部门总监</b><br> 
	 * @param 
	 * @return
	 * @Note
	 * <b>Author:</b> Barry
	 * <br><b>Date:</b> 2018年07月02日 12:08
	 */
	List<SaleperformanceProcess> getManagerByOrgId(Integer orgId);
	
	/**
	 * 
	 * <b>Description: 根据部门Id获取当前时间该部门参与考核人员的集合</b><br> 
	 * @param 
	 * @return
	 * @Note
	 * <b>Author:</b> Franlin
	 * <br><b>Date:</b> 2018年07月30日 14:49
	 */
	List<SaleperformanceProcess> getMembersListByOrgId(SaleperformanceProcess req);
	
	/**
	 * 
	 * <b>Description: 根据部门Id获取当前时间该部门参与考核人员的集合</b><br> 
	 * @param 
	 * @return
	 * @Note
	 * <b>Author:</b> Barry
	 * <br><b>Date:</b> 2018年07月30日 14:49
	 */
	List<SalesPerformanceSortVo> getMembersListByGroupId(SalesPerformanceSortVo salesPerformanceSortVo);
	
	/**
	 * 
	 * <b>Description:根据三级部门Id和groupId获取该部门参与考核的人员(user表,当前人员的查询)</b><br> 
	 * @param 
	 * @return
	 * @Note
	 * <b>Author:</b> Barry
	 * <br><b>Date:</b> 2018年08月02日 12:34
	 */
	 List<User> getMemberListByGroupId(GroupMemberDetailsVo groupMemberDetailsVo);
	
	 /**
	  * 
	  * <b>Description:根据UserId获取当前用户的orgId</b><br> 
	  * @param 
	  * @return
	  * @Note
	  * <b>Author:</b> Barry
	  * <br><b>Date:</b> 2018年08月02日 14:49
	  */
	User getOrgIdsByUserId(Integer userId,Integer companyId);


	/**
	 * <b>Description:</b><br> 
	 * @param request
	 * @param session
	 * @param saleorderId
	 * @return
	 * @Note
	 * <b>Author:</b> bill.bo
	 * <br><b>Date:</b> 2019年2月23日
	 */		
	Map<String, Object> getDeptList(Integer deptId);
}
