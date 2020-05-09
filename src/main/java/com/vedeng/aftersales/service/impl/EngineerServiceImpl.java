package com.vedeng.aftersales.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.common.constants.Contant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.aftersales.model.Engineer;
import com.vedeng.aftersales.model.vo.EngineerVo;
import com.vedeng.aftersales.service.EngineerService;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.DateUtil;
import com.vedeng.system.service.RegionService;

@Service("engineerService")
public class EngineerServiceImpl extends BaseServiceimpl implements EngineerService {
	public static Logger logger = LoggerFactory.getLogger(EngineerServiceImpl.class);

	@Autowired
	@Qualifier("regionService")
	private RegionService regionService;// 自动注入regionService
	
	@Override
	public Map<String, Object> querylistPage(EngineerVo engineerVo, Page page) {
		String url = httpUrl + "engineer/getengineerlistpage.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<EngineerVo>>> TypeRef2 = new TypeReference<ResultInfo<List<EngineerVo>>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, engineerVo, clientId, clientKey, TypeRef2,
					page);
			List<EngineerVo> optionList = (List<EngineerVo>) result.getData();
			page = result.getPage();
			
			if(optionList.size() > 0){
				for(EngineerVo engineer : optionList){
					if(engineer.getAreaId() > 0){
						engineer.setAreaStr((String)regionService.getRegion(engineer.getAreaId(), 2));
					}
				}
			}
			
			Map<String, Object> map = new HashMap<>();
			map.put("list", optionList);
			map.put("page", page);
			return map;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}

	@Override
	public ResultInfo saveAdd(Engineer engineer, HttpServletRequest request, HttpSession session) {
		Integer areaId = 0;
		String areaIds = "";
		if (Integer.parseInt(request.getParameter("zone")) > 0) {
			areaId = Integer.parseInt(request.getParameter("zone"));
			areaIds = request.getParameter("province")+","+request.getParameter("city")+","+request.getParameter("zone");
		} else if (Integer.parseInt(request.getParameter("city")) > 0) {
			areaId = Integer.parseInt(request.getParameter("city"));
			areaIds = request.getParameter("province")+","+request.getParameter("city");
		} else if (Integer.parseInt(request.getParameter("province")) > 0) {
			areaId = Integer.parseInt(request.getParameter("province"));
			areaIds = request.getParameter("province");
		}
		engineer.setAreaId(areaId);
		engineer.setAreaIds(areaIds);
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		Long time = DateUtil.sysTimeMillis();

		engineer.setCompanyId(user.getCompanyId());
		engineer.setAddTime(time);
		engineer.setCreator(user.getUserId());
		engineer.setModTime(time);
		engineer.setUpdater(user.getUserId());

		String url = httpUrl + "engineer/saveaddengineer.htm";
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, engineer, clientId, clientKey, TypeRef);
			return result;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return new ResultInfo();
		}
	}

	@Override
	public ResultInfo saveChangeEnable(Engineer engineer, HttpSession session) {
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		Long time = DateUtil.sysTimeMillis();
		engineer.setModTime(time);
		engineer.setUpdater(user.getUserId());

		String url = httpUrl + "engineer/savechangeenable.htm";
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, engineer, clientId, clientKey, TypeRef);
			return result;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return new ResultInfo();
		}
	}

	@Override
	public Map<String, Object> getEngineer(Engineer engineer, Page page) {
		String url = httpUrl + "engineer/getengineer.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<EngineerVo>> TypeRef2 = new TypeReference<ResultInfo<EngineerVo>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, engineer, clientId, clientKey, TypeRef2,
					page);
			EngineerVo engineerVo = (EngineerVo) result.getData();
			page = result.getPage();
			
			if(null != engineerVo){
				if(engineerVo.getAreaId() > 0){
					engineerVo.setAreaStr((String)regionService.getRegion(engineerVo.getAreaId(), 2));
				}
			}
			
			Map<String, Object> map = new HashMap<>();
			map.put("engineer", engineerVo);
			map.put("page", page);
			return map;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}

	@Override
	public Engineer getEngineerInfo(Engineer engineer) {
		String url = httpUrl + "engineer/getengineerinfo.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Engineer>> TypeRef2 = new TypeReference<ResultInfo<Engineer>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, engineer, clientId, clientKey, TypeRef2);
			Engineer engineerInfo = (Engineer) result.getData();
			
			return engineerInfo;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}

	@Override
	public ResultInfo saveEdit(Engineer engineer, HttpServletRequest request, HttpSession session) {
		//地区处理
		if(null != request.getParameter("zone")
				|| null != request.getParameter("city")
				|| null != request.getParameter("province")){
			Integer areaId = 0;
			String areaIds = "";
			if (Integer.parseInt(request.getParameter("zone")) > 0) {
				areaId = Integer.parseInt(request.getParameter("zone"));
				areaIds = request.getParameter("province")+","+request.getParameter("city")+","+request.getParameter("zone");
			} else if (Integer.parseInt(request.getParameter("city")) > 0) {
				areaId = Integer.parseInt(request.getParameter("city"));
				areaIds = request.getParameter("province")+","+request.getParameter("city");
			} else if (Integer.parseInt(request.getParameter("province")) > 0) {
				areaId = Integer.parseInt(request.getParameter("province"));
				areaIds = request.getParameter("province");
			}
			engineer.setAreaId(areaId);
			engineer.setAreaIds(areaIds);
		}
		if(null != request.getParameter("workYear") && request.getParameter("workYear").equals("")){
			engineer.setWorkYear(0);
		}
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		Long time = DateUtil.sysTimeMillis();

		engineer.setModTime(time);
		engineer.setUpdater(user.getUserId());

		String url = httpUrl + "engineer/saveeditengineer.htm";
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, engineer, clientId, clientKey, TypeRef);
			return result;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return new ResultInfo();
		}
	}

}
