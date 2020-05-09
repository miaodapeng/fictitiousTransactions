package com.vedeng.saleperformance.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.common.constants.Contant;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.authorization.dao.OrganizationMapper;
import com.vedeng.authorization.dao.UserMapper;
import com.vedeng.authorization.model.Organization;
import com.vedeng.authorization.model.User;
import com.vedeng.authorization.model.vo.UserVo;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.ObjectUtils;
import com.vedeng.order.model.vo.OrderDetailsVo;
import com.vedeng.saleperformance.model.SalesPerformanceBrand;
import com.vedeng.saleperformance.model.SalesPerformanceBrandConfig;
import com.vedeng.saleperformance.model.SalesPerformanceDept;
import com.vedeng.saleperformance.model.SalesPerformanceDeptManager;
import com.vedeng.saleperformance.model.SalesPerformanceDeptUser;
import com.vedeng.saleperformance.model.SalesPerformanceGroup;
import com.vedeng.saleperformance.model.SalesPerformanceGroupManager;
import com.vedeng.saleperformance.model.perf.MonthSceneModel;
import com.vedeng.saleperformance.model.vo.RSalesPerformanceGroupJConfigVo;
import com.vedeng.saleperformance.model.vo.SaleperformanceProcess;
import com.vedeng.saleperformance.model.vo.SalesPerformanceDeptUserVo;
import com.vedeng.saleperformance.model.vo.SalesPerformanceGroupVo;
import com.vedeng.saleperformance.service.SalesPerformanceService;

@Service("salesPerformanceService")
public class SalesPerformanceServiceImpl extends BaseServiceimpl implements SalesPerformanceService 
{
	public static Logger logger = LoggerFactory.getLogger(SalesPerformanceServiceImpl.class);

	@Autowired
	@Qualifier("userMapper")
	private UserMapper userMapper;
	
	@Autowired
	@Qualifier("organizationMapper")
	private OrganizationMapper organizationMapper;
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getGroupConfigSetData() {
		// TODO Auto-generated method stub
		//定义Map对象
		Map<String,Object> map = new HashMap<String,Object>();
		//List<SalesPerformanceConfig> configList = null;
		//定义反序列 数据格式
		final TypeReference<ResultInfo<Map<String,Object>>> TypeRef = new TypeReference<ResultInfo<Map<String,Object>>>() {};
		String url=httpUrl + "sales/salesperformance/getgroupconfigsetdata.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, null,clientId,clientKey, TypeRef);
			//获取数据
			map= (Map<String, Object>) result.getData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(Contant.ERROR_MSG, e);
		}
		return map;
	}
	@Override
	public ResultInfo<?> openOrClose(SalesPerformanceGroup salesPerformanceGroup) {
		// TODO Auto-generated method stub
		ResultInfo<?> result = null;
		final TypeReference<ResultInfo<SalesPerformanceGroup>> TypeRef = new TypeReference<ResultInfo<SalesPerformanceGroup>>() {};
		String url=httpUrl + "sales/salesperformance/changeenable.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, salesPerformanceGroup,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getOneGroupConfigSetData(
			RSalesPerformanceGroupJConfigVo rSalesPerformanceGroupJConfigVo) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		//List<SalesPerformanceGroupVo> groupVo=null;
		final TypeReference<ResultInfo<Map<String,Object>>> TypeRef = new TypeReference<ResultInfo<Map<String,Object>>>() {};
		String url=httpUrl + "sales/salesperformance/getonegroupconfigsetdata.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, rSalesPerformanceGroupJConfigVo,clientId,clientKey, TypeRef);
			map=(Map<String, Object>) result.getData();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(Contant.ERROR_MSG, e);
		}
		return map;
	}
	
	@Override
	public ResultInfo<?> saveOrUpdateConfigSetData(SalesPerformanceGroupVo groupVo) {
		// TODO Auto-generated method stub
		ResultInfo<?> result = null;
		final TypeReference<ResultInfo<SalesPerformanceGroupVo>> TypeRef = new TypeReference<ResultInfo<SalesPerformanceGroupVo>>() {};
		String url=httpUrl + "sales/salesperformance/saveorupdateconfigsetdata.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, groupVo,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getBrandConfigListPage(HttpServletRequest request,SalesPerformanceBrandConfig salesPerformanceBrandConfig,
			Page page,HttpSession session) {
		// TODO Auto-generated method stub
		//定义Map对象和品牌对象
		Map<String,Object> map = new HashMap<String,Object>();
		//List<SalesPerformanceBrandConfig> brandConfigList=null;
		//定义反序列 数据格式
		final TypeReference<ResultInfo<Map<String, Object>>> TypeRef = new TypeReference<ResultInfo<Map<String, Object>>>() {};
		String url=httpUrl + "sales/salesperformance/getbrandconfiglistpage.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, salesPerformanceBrandConfig,clientId,clientKey, TypeRef,page);
			//获取数据
			map= (Map<String, Object>) result.getData();
			page=result.getPage();
			map.put("page", page);
		}catch (Exception e) {
			// TODO: handle exception
			e.getStackTrace();
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getBrandListPage(HttpServletRequest request, SalesPerformanceBrand salesPerformanceBrand,
			String groupId,Page page, HttpSession session) {
		// TODO Auto-generated method stub
		//定义Map对象和品牌对象
		Map<String,Object> map = new HashMap<String,Object>();
		List<SalesPerformanceBrand> brandList=null;
		//定义反序列 数据格式
		final TypeReference<ResultInfo<List<SalesPerformanceBrand>>> TypeRef = new TypeReference<ResultInfo<List<SalesPerformanceBrand>>>() {};
		String url=httpUrl + "sales/salesperformance/getbrandlistpage.htm";
		try {
			Map<String,Object> tabMap= new HashMap<String,Object>();
			tabMap.put("salesPerformanceBrand", salesPerformanceBrand);
			tabMap.put("groupId", groupId);
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, tabMap,clientId,clientKey, TypeRef,page);
			brandList=(List<SalesPerformanceBrand>) result.getData();
			//page=result.getPage();
			map.put("brandList", brandList);
			map.put("page",result.getPage());
		} catch (Exception e) {
			// TODO: handle exception
			e.getStackTrace();
		}
		return map;
	}

	@SuppressWarnings({ "unchecked", "static-access" })
	@Override
	public Map<String,Object> getUserConfigList(HttpServletRequest request, SalesPerformanceGroup salesPerformanceGroup, HttpSession session) {
		// TODO Auto-generated method stub
		//定义对象
		List<SalesPerformanceGroupVo> groupUserVoList=new ArrayList<SalesPerformanceGroupVo>();
		List<SalesPerformanceGroupVo> list=new ArrayList<SalesPerformanceGroupVo>();
		Map<String,Object> map=new HashMap<String,Object>();
		//定义反序列 数据格式
		final TypeReference<ResultInfo<Map<String,Object>>> TypeRef = new TypeReference<ResultInfo<Map<String,Object>>>() {};
		String url=httpUrl + "sales/salesperformance/getuserconfiglist.htm";
		try {
			ResultInfo<Map<String, Object>> result = (ResultInfo<Map<String, Object>>) HttpClientUtils.post(url, salesPerformanceGroup,clientId,clientKey, TypeRef);
			
			if(null != result)
			{
				map = result.getData();
				//list转码
				List<Map<String, Object>> listM = (List<Map<String, Object>>) map.get("groupUserVoList");
				
				for(Map<String, Object> m : listM)
				{
					if(null == m)
						continue;
					SalesPerformanceGroupVo vo = new SalesPerformanceGroupVo();
					vo.setSalesPerformanceGoalMonthId((Integer)m.get("salesPerformanceGoalMonthId"));
					vo.setrSalesPerformanceGroupJUserId((Integer)m.get("rSalesPerformanceGroupJUserId"));
					vo.setGoalMonth((String)m.get("goalMonth"));
					vo.setSalesPerformanceGroupId((Integer)m.get("SalesPerformanceGroupId"));
					List<String> arr= (List<String>)m.get("monthGoals");
					if(null != arr)
					{	
						String[] ar = new String[arr.size()];
						vo.setMonthGoals(arr.toArray(ar));
					}
					vo.setSalesPerformanceGoalYearId((Integer)m.get("salesPerformanceGoalYearId"));
					vo.setUserId((Integer)m.get("userId"));
					vo.setGoalYear((String)m.get("goalYear"));
					groupUserVoList.add(vo);
				}
			}
			//////////////////////////////
			//获取user登录名称和部门名称
			/*UserMapper*/
			if(groupUserVoList.size()>0){
				List<UserVo> userVoList=userMapper.getUserOrgList(groupUserVoList);
			    //将user登录名称和部门名称放入List中(需改进)
			    if(userVoList.size()>0){
			    	for(UserVo userVo : userVoList)
			    	{
						for (SalesPerformanceGroupVo groupUserVo : groupUserVoList)
						{
							if (userVo.getUserId() != null && groupUserVo.getUserId() != null)
							{
								if (userVo.getUserId().equals(groupUserVo.getUserId()))
								{
									groupUserVo.setUserName(userVo.getUsername());
									groupUserVo.setOrgName(userVo.getOrgName());
									groupUserVo.setOrgId(userVo.getOrgId());
									list.add(groupUserVo);
								}
							}
						}
					}
			    }
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			e.getStackTrace();
		}
		map.put("list",list);
		return map;
	}

	@Override
	public List<Organization> getOrgList(Integer companyId) {
		// TODO Auto-generated method stub
		List<Organization> orgList=organizationMapper.getUserOrgList(companyId);
		return orgList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserVo> getUserList(UserVo userVo,Integer companyId,Page page) {
		
		List<UserVo> userList=null;
		Map<String,Object> tabmap=new HashMap<String,Object>();
		/*
		final TypeReference<ResultInfo<List<RSalesPerformanceGroupJUser>>> TypeRef = new TypeReference<ResultInfo<List<RSalesPerformanceGroupJUser>>>() {};
		String url=httpUrl + "sales/salesperformance/getuserlistpage.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, companyId,clientId,clientKey, TypeRef);
			groupJUserList=(List<RSalesPerformanceGroupJUser>) result.getData();
			//将已经存在的用户作为条件查询，筛除满足这些条件的数据
			tabmap.put("userVo", userVo);
			tabmap.put("groupJUserList", groupJUserList);
			tabmap.put("page", page);
			userList=userMapper.getUserlistpage(tabmap);
			//map.put("userList", userList);
		} catch (Exception e) {
			//
			e.getStackTrace();
		} */
		
		tabmap.put("page", page);
		tabmap.put("userVo", userVo);
		userList=userMapper.getUserlistpage(tabmap);
		
		return userList;
	}
	@Override
	public ResultInfo<?> changeBrandEnable(SalesPerformanceBrandConfig brandConfig) {
		// TODO Auto-generated method stub
		ResultInfo<?> result = null;
		final TypeReference<ResultInfo<SalesPerformanceGroupVo>> TypeRef = new TypeReference<ResultInfo<SalesPerformanceGroupVo>>() {};
		String url=httpUrl + "sales/salesperformance/changebrandenable.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, brandConfig,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}
	@Override
	public ResultInfo<?> addBrandConfig(Map<String,Object> map) {
		// TODO Auto-generated method stub
		ResultInfo<?> result = null;
		final TypeReference<ResultInfo<Map<String,Object>>> TypeRef = new TypeReference<ResultInfo<Map<String,Object>>>() {};
		String url=httpUrl + "sales/salesperformance/addbrandconfig.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, map,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}
	@Override
	public ResultInfo<?> delUserConfig(Map<String, Object> map) {
		// TODO Auto-generated method stub
		ResultInfo<?> result = null;
		final TypeReference<ResultInfo<Map<String,Object>>> TypeRef = new TypeReference<ResultInfo<Map<String,Object>>>() {};
		String url=httpUrl + "sales/salesperformance/deluserconfig.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, map,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}
	@Override
	public ResultInfo<?> addUserConfig(Map<String, Object> map) {
		// TODO Auto-generated method stub
		ResultInfo<?> result = null;
		final TypeReference<ResultInfo<Map<String,Object>>> TypeRef = new TypeReference<ResultInfo<Map<String,Object>>>() {};
		String url=httpUrl + "sales/salesperformance/adduserconfig.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, map,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}
	
	@Override
	public ResultInfo<?> saveOrUpdateOneUserConfigData(SalesPerformanceGroupVo groupVo) {
		// TODO Auto-generated method stub
		ResultInfo<?> result =null;
		final TypeReference<ResultInfo<Map<String,Object>>> TypeRef = new TypeReference<ResultInfo<Map<String,Object>>>() {};
		String url=httpUrl + "sales/salesperformance/saveorupdateuserconfigdata.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, groupVo,clientId,clientKey, TypeRef);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ResultInfo<MonthSceneModel> getSalesMonthSceneModel(MonthSceneModel reqM)
	{
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<MonthSceneModel>>> TypeRef = new TypeReference<ResultInfo<List<MonthSceneModel>>>(){};
		String url = httpUrl + "sales/salesperformance/monthlySceneDetails.htm";
		ResultInfo<MonthSceneModel> result = null;
		List<MonthSceneModel> resultList = new LinkedList<MonthSceneModel>();
		
		try
		{
			result = (ResultInfo<MonthSceneModel>) HttpClientUtils.post(url, reqM, clientId, clientKey, TypeRef);
		}
		catch (IOException e)
		{
			logger.error(Contant.ERROR_MSG, e);
			
			return new ResultInfo<MonthSceneModel>();
		}
		
		if(null != result)
		{	
			List<MonthSceneModel> reList = (List<MonthSceneModel>) result.getData();
			// 集合非空
			if(!(null == reList || reList.size() == 0))
			{
				for(MonthSceneModel msm : reList)
				{
					if(null == msm || null == msm.getSceneType() || null == msm.getUserId())
						continue;
					// 榜首个人
					if(msm.getSceneType() == 1 || msm.getSceneType() == 3)
					{
						// 查询和当前用户/榜首名称
						User user = userMapper.selectByPrimaryKey(msm.getUserId());
						msm.setSaleName(user.getUsername());
					}
					resultList.add(msm);
				}
			}
			result.setListData(resultList);
		}
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResultInfo<MonthSceneModel> getSceneDataDetails(MonthSceneModel reqM)
	{
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<MonthSceneModel>>> TypeRef = new TypeReference<ResultInfo<List<MonthSceneModel>>>(){};
		String url = httpUrl + "sales/salesperformance/sceneDataDetails.htm";
		ResultInfo<MonthSceneModel> result = null;
		List<MonthSceneModel> resultList = new LinkedList<MonthSceneModel>();
		
		try
		{
			result = (ResultInfo<MonthSceneModel>) HttpClientUtils.post(url, reqM, clientId, clientKey, TypeRef);
		}
		catch (IOException e)
		{
			logger.error(Contant.ERROR_MSG, e);
			
			return new ResultInfo<>();
		}
		
		if(null != result)
		{	
			List<MonthSceneModel> reList = (List<MonthSceneModel>) result.getData();
			// 集合非空
			if(!(null == reList || reList.size() == 0))
			{
				for(MonthSceneModel msm : reList)
				{
					if(null == msm || null == msm.getSortType() || null == msm.getUserId())
						continue;
					// 品牌
					if(msm.getSortType() == 2)
					{
						// 查询榜首名称
						User user = userMapper.selectByPrimaryKey(msm.getUserId());
						if(null != user)
						{							
							msm.setSaleName(user.getUsername());
						}
					}
					resultList.add(msm);
				}
			}
			result.setListData(resultList);
		}
		
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResultInfo<MonthSceneModel> historyDataDetails(MonthSceneModel reqM)
	{
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<MonthSceneModel>>> TypeRef = new TypeReference<ResultInfo<List<MonthSceneModel>>>(){};
		String url = httpUrl + "sales/salesperformance/historyDataDetails.htm";
		ResultInfo<MonthSceneModel> result = null;
		
		try
		{
			result = (ResultInfo<MonthSceneModel>) HttpClientUtils.post(url, reqM, clientId, clientKey, TypeRef);
		}
		catch (IOException e)
		{
			logger.error(Contant.ERROR_MSG, e);
			
			return new ResultInfo<MonthSceneModel>();
		}
		
		if(null != result)
		{	
			result.setListData((List<MonthSceneModel>) result.getData());
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getUserConfigGoal(Map<String, Object> map) {
		Map<String,Object> resultMap=new HashMap<String, Object>();
		final TypeReference<ResultInfo<Map<String,Object>>> TypeRef = new TypeReference<ResultInfo<Map<String,Object>>>() {};
		String url=httpUrl + "sales/salesperformance/getuserconfiggoal.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, map,clientId,clientKey, TypeRef);
			resultMap=(Map<String, Object>) result.getData();
		} catch (Exception e) {
			// TODO: handle exception
			e.getStackTrace();
		}
		return resultMap;
	}
	@SuppressWarnings("unchecked")
	@Override
	public SalesPerformanceGroupVo querySalesForGoal(SalesPerformanceGroupVo req)
	{
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<SalesPerformanceGroupVo>> TypeRef = new TypeReference<ResultInfo<SalesPerformanceGroupVo>>(){};
		String url = httpUrl + "sales/salesperformance/querySalesForGoal.htm";
		ResultInfo<SalesPerformanceGroupVo> result = null;
		
		try
		{
			result = (ResultInfo<SalesPerformanceGroupVo>) HttpClientUtils.post(url, req, clientId, clientKey, TypeRef);
		}
		catch (IOException e)
		{
			logger.error(Contant.ERROR_MSG, e);
		}
		
		if(null == result)
		{
			return null;
		}
		
		return result.getData();
	}
	@Override
	public ResultInfo<?> batchSaveOrUpdate(SalesPerformanceGroupVo req)
	{
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>(){};
		String url = httpUrl + "sales/salesperformance/batchSaveOrUpdate.htm";
		ResultInfo<?> result = null;
		
		try
		{
			result = (ResultInfo<?>) HttpClientUtils.post(url, req, clientId, clientKey, TypeRef);
		}
		catch (IOException e)
		{
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}
	
	/**
	 * 查询部门五行得分和排名
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ResultInfo<?> queryDeptSoreAndSort(MonthSceneModel req)
	{
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<SaleperformanceProcess>>> TypeRef = new TypeReference<ResultInfo<List<SaleperformanceProcess>>>(){};
		String url = httpUrl + "sales/salesperformance/queryDeptSoreAndSort.htm";
		ResultInfo<List<SaleperformanceProcess>> result = null;
		
		try{
			result = (ResultInfo<List<SaleperformanceProcess>>) HttpClientUtils.post(url, req, clientId, clientKey, TypeRef);
		}catch (IOException e){
			logger.error(Contant.ERROR_MSG, e);
		}
		
		if(null != result){
			if(2 == result.getCode()){
				return result;
			}
			List<SaleperformanceProcess> resultList = result.getData();
			// resultList 不为空
			if(CollectionUtils.isNotEmpty(resultList)){
//				// set集合存放groupName
//				Set<String> groupNameSet = new HashSet<>();
				// 团队名称集合（只添加唯一的）
				List<String> groupList = new ArrayList<>();
				// 查询用户名
				for(SaleperformanceProcess userSale : resultList){
					if(null == userSale || null == userSale.getUserId())
						continue;
					// 查询和当前用户/榜首名称
					User user = userMapper.selectByPrimaryKey(userSale.getUserId());
					userSale.setUsername(user.getUsername());
					
					// 如果该团队名称不存在，添加该团队名称
					if(!groupList.contains(userSale.getGroupName())){
						groupList.add(userSale.getGroupName());
					}
					
//					// groupName去重
//					groupNameSet.add(userSale.getGroupName());
				}
				
//				// 给groupNameSet排序
//				List<String> groupNameList = new ArrayList<>();
//				groupNameList.addAll(groupNameSet);
				
				// 定义返回前端的list
				List<Map<String, Object>> mapRes = new ArrayList<>();
				
				int size = resultList.size();
				for (String str : groupList) {
					// 返回
					Map<String, Object> map = new HashMap<>();
					map.put("groupName", str);
					
					// 返回结果集
					List<SaleperformanceProcess> resList = new ArrayList<>();
					for (int i = 0; i < size; i++) {
						SaleperformanceProcess groupSale = resultList.get(i);
						// 根据团队名称将结果分组
						if(str.equals(groupSale.getGroupName())){
							resList.add(groupSale);
						}
					}
					map.put("resultList", resList);
					mapRes.add(map);
				}
 				return new ResultInfo<List<Map<String,Object>>>(0, "成功", 200, mapRes);
			}
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ResultInfo<?> queryOrderDetails(OrderDetailsVo req, Page page)
	{
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<OrderDetailsVo>>> TypeRef = new TypeReference<ResultInfo<List<OrderDetailsVo>>>(){};
		String url = httpUrl + "sales/salesperformance/queryOrderDetails.htm";
		ResultInfo<List<OrderDetailsVo>> result = null;
		
		try
		{
			result = (ResultInfo<List<OrderDetailsVo>>) HttpClientUtils.post(url, req, clientId, clientKey, TypeRef, page);
		}
		catch (IOException e)
		{
			logger.error(Contant.ERROR_MSG, e);
		}
		
		return result;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SaleperformanceProcess> queryAllSalesData(Long longTime, Page page)
	{
		List<SaleperformanceProcess> list = null;
		String url = httpUrl + "sales/salesperformance/queryAllSalesData.htm";
		final TypeReference<ResultInfo<List<SaleperformanceProcess>>> TypeRef = new TypeReference<ResultInfo<List<SaleperformanceProcess>>>() {};
		try
		{
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, longTime, clientId, clientKey, TypeRef, page);
			
			list = (List<SaleperformanceProcess>) result.getData();
			
			if(null != list)
			{
				
				for(SaleperformanceProcess userSale : list)
				{
					if(null == userSale || null == userSale.getUserId())
						continue;
					User user = userMapper.selectByPrimaryKey(userSale.getUserId());
					if(null != user)
					{
						userSale.setUsername(user.getUsername());
					}
				}
			}
			
		}
		catch (Exception e)
		{
			logger.error(Contant.ERROR_MSG, e);
		}

		return list;
		
	}
	
	/**
	 * <b>Description:</b><br>
	 * 获取五行的名称
	 * @param :a
	 *@return :a
	 *@Note <b>Author:</b> Bert <br>
	 * <b>Date:</b> 2019/2/16 14:13
	 */
	@Override
	public ResultInfo<?> findByCompanyIdWuXing(RSalesPerformanceGroupJConfigVo rSalesPerformanceGroupJConfigVo) {
		String url = httpUrl + "sales/salesperformance/findByCompanyIdWuXing.htm";
		final TypeReference<ResultInfo<List<RSalesPerformanceGroupJConfigVo>>> TypeRef = new TypeReference<ResultInfo<List<RSalesPerformanceGroupJConfigVo>>>() {};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url,rSalesPerformanceGroupJConfigVo,  clientId, clientKey, TypeRef);
			if (ObjectUtils.allNotNull(result)){
				List<RSalesPerformanceGroupJConfigVo> list =(List<RSalesPerformanceGroupJConfigVo>) result.getData();
				return new ResultInfo<>(ErpConst.ZERO,"操作成功",list);
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			logger.info("接口调用异常");
		}
		
		return new ResultInfo<>(ErpConst.ONE,"接口调用异常");
	}
	
	/**
	 * <b>Description:</b><br>
	 *  保存团队信息
	 *@Note <b>Author:</b> Bert <br>
	 * <b>Date:</b> 2019/2/16 15:44
	 */
	@Override
	public ResultInfo<?> saveConfigSetData(HttpServletRequest request,SalesPerformanceGroupVo groupVo) {
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		List<RSalesPerformanceGroupJConfigVo> configVoList=groupVo.getConfigVoList();
		if(user!=null){
			groupVo.setUpdater(user.getUserId());
			groupVo.setModTime(DateUtil.sysTimeMillis());
			groupVo.setCompanyId(user.getCompanyId());
			groupVo.setAddTime(DateUtil.sysTimeMillis());
			groupVo.setCreator(user.getUserId());
			if(configVoList!=null&&configVoList.size()>0){
				for(int i=0;i<configVoList.size();i++){
					configVoList.get(i).setAddTime(DateUtil.sysTimeMillis());
					configVoList.get(i).setCompanyId(user.getCompanyId());
					configVoList.get(i).setCreator(user.getUserId());
					configVoList.get(i).setModTime(DateUtil.sysTimeMillis());
					configVoList.get(i).setUpdater(user.getUserId());
				}
			}
		}
		String url = httpUrl + "sales/salesperformance/saveConfigSetData.htm";
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url,groupVo,  clientId, clientKey, TypeRef);
			if (ObjectUtils.allNotNull(result)){
				if (result.getCode().equals(0)){
					return new ResultInfo<>(ErpConst.ZERO,"操作成功");
				}
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			logger.info("接口调用异常");
		}
		return new ResultInfo<>(ErpConst.ONE,"接口异常");
	}
	
	@Override
	public ResultInfo<?> deleteGroupById(HttpServletRequest request,Integer salesPerformanceGroupId) {
		SalesPerformanceGroupVo sVo = new SalesPerformanceGroupVo();
		sVo.setSalesPerformanceGroupId(salesPerformanceGroupId);
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if (ObjectUtils.allNotNull(user)) {
			sVo.setUpdater(user.getUserId());
		}
		String url = httpUrl + "sales/salesperformance/updateGroupStatus.htm";
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url,sVo,  clientId, clientKey, TypeRef);
			if (ObjectUtils.allNotNull(result)){
				if (result.getCode().equals(ErpConst.ZERO)){
					return new ResultInfo<>(ErpConst.ZERO,"操作成功");
				}
				if (result.getCode().equals(ErpConst.TWO)) {
					return new ResultInfo<>(ErpConst.TWO,"操作失败");
				}
				else {
					return new ResultInfo<>(ErpConst.ONE,"操作失败");
				}
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			logger.info("接口调用异常");
		}
		return new ResultInfo<>(ErpConst.ONE,"接口异常");
	}
	
	/**
	 * <b>Description:</b><br>
	 * 查找该公司下的所有团队
	 * @Note <b>Author:</b> Bert <br>
	 * <b>Date:</b> 2019/2/17 15:42
	 */
	@Override
	public ResultInfo<?> queryByCompangIdAndstatus(SalesPerformanceGroup salesPerformanceGroup) {
		String url = httpUrl + "sales/salesperformance/queryByCompangIdAndstatus.htm";
		final TypeReference<ResultInfo<List<SalesPerformanceGroup>>> TypeRef = new TypeReference<ResultInfo<List<SalesPerformanceGroup>>>() {};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url,salesPerformanceGroup,  clientId, clientKey, TypeRef);
			if (ObjectUtils.allNotNull(result)) {
				List<SalesPerformanceGroup> list = (List<SalesPerformanceGroup>) result.getData();
				return new ResultInfo(ErpConst.ZERO,"操作成功",list);
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			logger.info("接口调用异常");
		}
		
		return new ResultInfo(ErpConst.ONE,"操作失败");
	}
	/**
	 * 新增小组
	 * <b>Description:</b><br>
	 * <b>Author:</b> Bert
	 * <br><b>Date:</b> 2019年02月17日 下午2:38:36
	 */
	@Override
	public ResultInfo<?> insertTeam(SalesPerformanceDept salesPerformanceDept) {
		String url = httpUrl + "sales/salesperformance/insertTeam.htm";
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url,salesPerformanceDept,  clientId, clientKey, TypeRef);
			if (ObjectUtils.allNotNull(result)) {
				return new ResultInfo(ErpConst.ZERO,"操作成功",result.getData());
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			logger.info("接口调用异常");
		}
		
		return new ResultInfo(ErpConst.ONE,"操作失败");
	}
	
	/**
	 * <b>Description:</b><br>
	 * 返回各个团队部门下的小组以及负责人
	 * @param :[]
	 * @return :java.util.List<com.vedeng.model.saleperformance.SalesPerformanceDept>
	 * @Note <b>Author:</b> Bert <br>
	 * <b>Date:</b> 2019/2/18 17:49
	 */
	@Override
	public ResultInfo<?> getDeptMember(SalesPerformanceDept salesPerformanceDept) {
		String url = httpUrl + "sales/salesperformance/getDeptMember.htm";
		final TypeReference<ResultInfo<List<SalesPerformanceDept>>> TypeRef = new TypeReference<ResultInfo<List<SalesPerformanceDept>>>() {};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url,salesPerformanceDept,  clientId, clientKey, TypeRef);
			if (ObjectUtils.allNotNull(result)) {
				List<SalesPerformanceDept> list = (List<SalesPerformanceDept>) result.getData();
				return new ResultInfo(ErpConst.ZERO,"操作成功",list);
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			logger.info("接口调用异常");
		}
		
		return new ResultInfo(ErpConst.ONE,"操作失败");
	}
	/**
	 * <b>Description:</b><br>
	 *
	 * 删除小组
	 * @param :[salesPerformanceDept]
	 * @return :com.vedeng.common.model.ResultInfo<?>
	 * @Note <b>Author:</b> Bert <br>
	 * <b>Date:</b> 2019/2/18 21:40
	 */
	
	@Override
	public ResultInfo<?> deleteOneGroupConfigSetData(SalesPerformanceDept salesPerformanceDept) {
		String url = httpUrl + "sales/salesperformance/deleteOneGroupConfigSetData.htm";
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url,salesPerformanceDept,  clientId, clientKey, TypeRef);
			if (ObjectUtils.allNotNull(result)) {
				//等于二表示该小组下面还有成员
				if (result.getCode().equals(ErpConst.TWO)) {
					return new ResultInfo(ErpConst.TWO,"操作失败");
				} else {
					return new ResultInfo(ErpConst.ZERO,"操作成功");
				}
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			logger.info("接口调用异常");
		}
		return new ResultInfo(ErpConst.ONE,"操作失败");
	}
	
	/**
	 * <b>Description:</b><br>
	 *
	 *  获取用户对应的人员
	 * @param :[salesPerformanceDept]
	 * @return :com.vedeng.common.model.ResultInfo<?>
	 * @Note <b>Author:</b> Bert <br>
	 * <b>Date:</b> 2019/2/19 10:17
	 */
	@Override
	public ResultInfo<?> getDeptUser(SalesPerformanceDeptUserVo salesPerformanceDept) {
		String url = httpUrl + "sales/salesperformance/getDeptUser.htm";
		final TypeReference<ResultInfo<List<SalesPerformanceDeptUserVo>>> TypeRef = new TypeReference<ResultInfo<List<SalesPerformanceDeptUserVo>>>() {};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url,salesPerformanceDept,  clientId, clientKey, TypeRef);
			if (ObjectUtils.allNotNull(result)) {
				List<SalesPerformanceDeptUserVo> list = (List<SalesPerformanceDeptUserVo>) result.getData();
				return new ResultInfo(ErpConst.ZERO,"操作成功",list);
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			logger.info("接口调用异常");
		}
		
		return new ResultInfo(ErpConst.ONE,"操作失败");
	}
	
	/**
	 * <b>Description:</b><br>
	 * 编辑保存小组
	 *
	 * @param :[salesPerformanceDeptUser]
	 * @return :com.vedeng.common.model.ResultInfo<?>
	 * @Note <b>Author:</b> Bert <br>
	 * <b>Date:</b> 2019/2/19 11:11
	 */
	@Override
	public ResultInfo<?> editDeptUser(SalesPerformanceDeptUser salesPerformanceDeptUser) {
		String url = httpUrl + "sales/salesperformance/editDeptUser.htm";
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url,salesPerformanceDeptUser,  clientId, clientKey, TypeRef);
			if (ObjectUtils.allNotNull(result)) {
				if (result.getData().equals(ErpConst.ONE)) {
					return new ResultInfo(ErpConst.ZERO,"操作成功");
				}
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			logger.info("接口调用异常");
		}
		
		return new ResultInfo(ErpConst.ONE,"操作失败");
	}
	/**
	 * <b>Description:</b><br>
	 *  删除小组人员
	 *
	 * @param :[salesPerformanceDeptUser]
	 * @return :com.vedeng.common.model.ResultInfo<?>
	 * @Note <b>Author:</b> Bert <br>
	 * <b>Date:</b> 2019/2/19 11:43
	 */
	@Override
	public ResultInfo<?> deleteDeptUser(SalesPerformanceDeptUser salesPerformanceDeptUser) {
		String url = httpUrl + "sales/salesperformance/deleteDeptUser.htm";
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url,salesPerformanceDeptUser,  clientId, clientKey, TypeRef);
			if (ObjectUtils.allNotNull(result)) {
				if (result.getData().equals(ErpConst.ONE)) {
					return new ResultInfo(ErpConst.ZERO,"操作成功");
				}
			}
		} catch (IOException e) {
			logger.info("接口调用异常"+e.getMessage());
		}
		
		return new ResultInfo(ErpConst.ONE,"操作失败");
	}
	
	/**
	 * <b>Description:</b><br>
	 * 小组内新增人员
	 *
	 * @param :[salesPerformanceDeptUserVo]
	 * @return :com.vedeng.common.model.ResultInfo<?>
	 * @Note <b>Author:</b> Bert <br>
	 * <b>Date:</b> 2019/2/19 13:50
	 */
	@Override
	public ResultInfo<?> addDeptUserConfig(SalesPerformanceDeptUserVo salesPerformanceDeptUserVo) {
		String url = httpUrl + "sales/salesperformance/addDeptUserConfig.htm";
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		try {
			//接口调用返回结果
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url,salesPerformanceDeptUserVo,  clientId, clientKey, TypeRef);
			if (ObjectUtils.allNotNull(result)) {
				if ( Integer.parseInt(String.valueOf(result.getData())) >= ErpConst.ONE) {
					return new ResultInfo(ErpConst.ZERO,"操作成功");
				}
			}
		} catch (IOException e) {
			logger.info("接口调用异常"+e.getMessage());
		}
		return new ResultInfo(ErpConst.ONE,"操作失败");
	}
	
	@Override
	public List<SalesPerformanceGroupVo> getAllGroup(SalesPerformanceGroupVo salesPerformanceGroupVo) {
		String url = httpUrl + "sales/salesperformance/getAllGroup.htm";
		final TypeReference<ResultInfo<List<SalesPerformanceGroupVo>>> TypeRef = new TypeReference<ResultInfo<List<SalesPerformanceGroupVo>>>() {};
		try {
			//接口调用返回结果
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url,salesPerformanceGroupVo,  clientId, clientKey, TypeRef);
			if (ObjectUtils.allNotNull(result)) {
				List<SalesPerformanceGroupVo> listData =(List<SalesPerformanceGroupVo>) result.getData();
				return listData;
			}
		} catch (IOException e) {
			logger.info("接口调用异常"+e.getMessage());
		}
		return null;
	}
	
	/**
	 * <b>Description:</b><br>
	 *
	 *  获取所有已经绑定过的客户
	 * @param :[]
	 * @return :java.util.List<com.vedeng.authorization.model.User>
	 * @Note <b>Author:</b> Bert <br>
	 * <b>Date:</b> 2019/2/20 23:20
	 */
	@Override
	public List<User> getHasAllUser() {
		String url = httpUrl + "sales/salesperformance/getAllHasUser.htm";
		final TypeReference<ResultInfo<List<User>>> TypeRef = new TypeReference<ResultInfo<List<User>>>() {};
		try {
			//接口调用返回结果
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url,new User(),  clientId, clientKey, TypeRef);
			if (ObjectUtils.allNotNull(result)) {
				List<User> listData =(List<User>) result.getData();
				return listData;
			}
		} catch (IOException e) {
			logger.info("接口调用异常"+e.getMessage());
		}
		return null;
	}
	
	/**
	 * <b>Description:</b><br>
	 * 删除部门负责人
	 *
	 * @param :[salesPerformanceDeptUser]
	 * @return :com.vedeng.common.model.ResultInfo<?>
	 * @Note <b>Author:</b> Bert <br>
	 * <b>Date:</b> 2019/2/21 11:01
	 */
	@Override
	public ResultInfo<?> delDeptUser(SalesPerformanceDeptManager salesPerformanceDeptManager) {
		String url = httpUrl + "sales/salesperformance/delDeptUser.htm";
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		try {
			//接口调用返回结果
			return  (ResultInfo<?>) HttpClientUtils.post(url, salesPerformanceDeptManager ,  clientId, clientKey, TypeRef);
		} catch (IOException e) {
			logger.info("接口调用异常"+e.getMessage());
		}
		return null;
	}
	
	/**
	 * <b>Description:</b><br>
	 * 删除团队负责人
	 *
	 * @param :[salesPerformanceGroupVo]
	 * @return :com.vedeng.common.model.ResultInfo<?>
	 * @Note <b>Author:</b> Bert <br>
	 * <b>Date:</b> 2019/2/21 11:01
	 */
	@Override
	public ResultInfo<?> delGroupUser(SalesPerformanceGroupManager salesPerformanceGroupManager) {
		String url = httpUrl + "sales/salesperformance/delGroupUser.htm";
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		try {
			//接口调用返回结果
			return  (ResultInfo<?>) HttpClientUtils.post(url, salesPerformanceGroupManager ,  clientId, clientKey, TypeRef);
		} catch (IOException e) {
			logger.info("接口调用异常"+e.getMessage());
		}
		return null;
	}
	
	
}
