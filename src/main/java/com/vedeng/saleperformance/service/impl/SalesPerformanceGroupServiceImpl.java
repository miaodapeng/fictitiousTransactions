package com.vedeng.saleperformance.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.common.constants.Contant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.authorization.model.User;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.saleperformance.service.SalesPerformanceGroupService;

@Service("salesPerformanceGroupService")
public class SalesPerformanceGroupServiceImpl extends BaseServiceimpl implements SalesPerformanceGroupService{
	public static Logger logger = LoggerFactory.getLogger(SalesPerformanceGroupServiceImpl.class);


	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getGroupDetail(HttpServletRequest req, User user) {
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<Map<String, Object>>>> TypeRef = new TypeReference<ResultInfo<List<Map<String, Object>>>>(){};
		String url = httpUrl + "salesperformance/group/getgroupdetail.htm";
		try{
			ResultInfo<List<Map<String, Object>>> result = (ResultInfo<List<Map<String, Object>>>) HttpClientUtils.post(url, user, clientId, clientKey, TypeRef);
			return result.getData();
		}catch (IOException e){
			logger.error(Contant.ERROR_MSG, e);
		}
		return null;
	}


	/**
	 * 小组详情页数据
	 * @param request
	 * @param user
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getDeptDetail(HttpServletRequest request, User user) {
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<Map<String, Object>>>> TypeRef = new TypeReference<ResultInfo<List<Map<String, Object>>>>(){};
		String url = httpUrl + "salesperformance/group/getdeptdetail.htm";
		try{
			ResultInfo<List<Map<String, Object>>> result = (ResultInfo<List<Map<String, Object>>>) HttpClientUtils.post(url, user, clientId, clientKey, TypeRef);
			return result.getData();
		}catch (IOException e){
			logger.error(Contant.ERROR_MSG, e);
		}
		return null;
	}

}
