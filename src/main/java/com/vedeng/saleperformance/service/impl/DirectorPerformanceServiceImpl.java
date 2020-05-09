package com.vedeng.saleperformance.service.impl;

import java.util.List;
import java.util.Map;

import com.common.constants.Contant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.authorization.model.User;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.saleperformance.model.RSalesPerformanceGroupJUser;
import com.vedeng.saleperformance.model.vo.GroupMemberDetailsVo;
import com.vedeng.saleperformance.model.vo.SaleperformanceProcess;
import com.vedeng.saleperformance.model.vo.SalesPerformanceSortVo;
import com.vedeng.saleperformance.service.DirectorPerformanceService;

import net.sf.json.JSONObject;

@Service
public class DirectorPerformanceServiceImpl extends BaseServiceimpl implements DirectorPerformanceService{
	public static Logger logger = LoggerFactory.getLogger(DirectorPerformanceServiceImpl.class);

	
	@Override
	public List<RSalesPerformanceGroupJUser> getAllMenbersList() {
		// TODO Auto-generated method stub
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<List<RSalesPerformanceGroupJUser>>> TypeRef = new TypeReference<ResultInfo<List<RSalesPerformanceGroupJUser>>>() {};
			String url=httpUrl + "director/performance/getAllMenbersList.htm";
			ResultInfo<?> result = (ResultInfo<?>)HttpClientUtils.post(url,null,clientId,clientKey, TypeRef);
			if(result == null  || result.getCode() == -1){
				return null;
			}
			
			List<RSalesPerformanceGroupJUser> users = (List<RSalesPerformanceGroupJUser>) result.getData();
			return users;
			
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}
	

	@Override
	public Map<String, Object> getGroupList(Integer groupId) {
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<Map<String, Object>>> TypeRef = new TypeReference<ResultInfo<Map<String, Object>>>() {};
			String url=httpUrl + "director/performance/getGroupList.htm";
			ResultInfo<?> result = (ResultInfo<?>)HttpClientUtils.post(url,groupId,clientId,clientKey, TypeRef);
			if(result == null  || result.getCode() == -1){
				return null;
			}
			
			Map<String, Object> groupList = (Map<String, Object>) result.getData();
			return groupList;
			
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}
	
	@Override
	public Map<String, Object> getDeptList(Integer deptId) {
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<Map<String, Object>>> TypeRef = new TypeReference<ResultInfo<Map<String, Object>>>() {};
			String url=httpUrl + "director/performance/getMembersListByOrgId.htm";
			ResultInfo<?> result = (ResultInfo<?>)HttpClientUtils.post(url,deptId,clientId,clientKey, TypeRef);
			if(result == null  || result.getCode() == -1){
				return null;
			}
			
			Map<String, Object> deptList = (Map<String, Object>) result.getData();
			return deptList;
			
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}

	
	
	
	
	@Override
	public List<GroupMemberDetailsVo> getMemberDetails(GroupMemberDetailsVo groupMemberDetailsVo) {
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<List<GroupMemberDetailsVo>>> TypeRef = new TypeReference<ResultInfo<List<GroupMemberDetailsVo>>>() {};
			String url=httpUrl + "director/performance/getMemberDetails.htm";
			ResultInfo<?> result = (ResultInfo<?>)HttpClientUtils.post(url,groupMemberDetailsVo,clientId,clientKey, TypeRef);
			if(result == null  || result.getCode() == -1){
				return null;
			}
			
			List<GroupMemberDetailsVo> membersList = (List<GroupMemberDetailsVo>) result.getData();
			return membersList;
			
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}

	@Override
	public List<GroupMemberDetailsVo> getAreaDetailsList(GroupMemberDetailsVo groupMemberDetailsVo) {
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<List<GroupMemberDetailsVo>>> TypeRef = new TypeReference<ResultInfo<List<GroupMemberDetailsVo>>>() {};
			String url=httpUrl + "director/performance/getAreaDetailsList.htm";
			ResultInfo<?> result = (ResultInfo<?>)HttpClientUtils.post(url,groupMemberDetailsVo,clientId,clientKey, TypeRef);
			if(result == null  || result.getCode() == -1){
				return null;
			}
			
			List<GroupMemberDetailsVo> areaData = (List<GroupMemberDetailsVo>) result.getData();
			return areaData;
			
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}


	@Override
	public List<SaleperformanceProcess> getManagerByOrgId(Integer groupId) 
	{
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<List<SaleperformanceProcess>>> TypeRef = new TypeReference<ResultInfo<List<SaleperformanceProcess>>>() {};
			
			String url= httpUrl + "director/performance/getManagerList.htm";
			ResultInfo<List<SaleperformanceProcess>> result = (ResultInfo<List<SaleperformanceProcess>>)HttpClientUtils.post(url,groupId,clientId,clientKey, TypeRef);
			if(result == null  || result.getCode() == -1){
				return null;
			}
			
			List<SaleperformanceProcess> users = (List<SaleperformanceProcess>) result.getData();
			return users;
			
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}

/**
 * 
 * <b>Description: 根据部门Id获取该部门参与考核人员的集合</b><br> 
 * @param 
 * @return
 * @Note
 * <b>Author:</b> Barry
 * <br><b>Date:</b> 2018年07月30日 14:51
 */
	@Override
	public List<SalesPerformanceSortVo> getMembersListByGroupId(SalesPerformanceSortVo salesPerformanceSortVo) 
	{
		try 
		{
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<List<SalesPerformanceSortVo>>> TypeRef = new TypeReference<ResultInfo<List<SalesPerformanceSortVo>>>() {};
			String url=httpUrl + "director/performance/getMembersListByGroupId.htm";
			ResultInfo<?> result = (ResultInfo<?>)HttpClientUtils.post(url,salesPerformanceSortVo,clientId,clientKey, TypeRef);
			if(result == null  || result.getCode() == -1){
				return null;
			}
			
			List<SalesPerformanceSortVo> users = (List<SalesPerformanceSortVo>) result.getData();
			return users;
			
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
		
	}

	/**
	 * 
	 * <b>Description:根据三级部门Id和groupId获取该部门参与考核的人员(user表,当前人员的查询)</b><br> 
	 * @param 
	 * @return
	 * @Note
	 * <b>Author:</b> Barry
	 * <br><b>Date:</b> 2018年08月02日 12:34
	 */
	@Override
	public List<User> getMemberListByGroupId(GroupMemberDetailsVo groupMemberDetailsVo) {
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<List<User>>> TypeRef = new TypeReference<ResultInfo<List<User>>>() {};
			String url=httpUrl + "director/performance/getMemberListByGroupId.htm";
			ResultInfo<?> result = (ResultInfo<?>)HttpClientUtils.post(url,groupMemberDetailsVo,clientId,clientKey, TypeRef);
			if(result == null  || result.getCode() == -1){
				return null;
			}
			
			List<User> users = (List<User>) result.getData();
			return users;
			
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
}

	

	
	@Override
	public User getOrgIdsByUserId(Integer userId, Integer companyId) 
	{
		// TODO Auto-generated method stub
		return userMapper.getOrgIdsByUserId(userId, companyId);
	}

	@Override
	public List<SaleperformanceProcess> getMembersListByOrgId(SaleperformanceProcess req) 
	{
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<SaleperformanceProcess>>> TypeRef = new TypeReference<ResultInfo<List<SaleperformanceProcess>>>() {};
		String url=httpUrl + "director/performance/getMembersListByOrgId.htm";
		ResultInfo<List<SaleperformanceProcess>> result = null;
		try 
		{
			
			result = (ResultInfo<List<SaleperformanceProcess>>)HttpClientUtils.post(url, req, clientId,clientKey, TypeRef);
			
			if(null != result)
			{				
				List<SaleperformanceProcess> resulsList = result.getData();
				
				if(null != req && null != req.getReqType() && 1 == req.getReqType() && null != resulsList)
				{
					User user = new User();
					for(SaleperformanceProcess sProcess : resulsList)
					{
						if(null == sProcess || null == sProcess.getUserId())
							continue;
						user.setUserId(sProcess.getUserId());
						User reUser = userMapper.getUser(user);
						if(null != reUser)
						{
							sProcess.setUsername(reUser.getUsername());
						}
					}
				}
				
				return resulsList;
			}
			
		}
		catch (Exception e) 
		{
			logger.error(Contant.ERROR_MSG, e);
		}
		
		return null;
	}

}
