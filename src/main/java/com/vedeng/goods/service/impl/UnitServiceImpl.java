package com.vedeng.goods.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.common.constants.Contant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.goods.model.Category;
import com.vedeng.goods.model.Unit;
import com.vedeng.goods.model.UnitGroup;
import com.vedeng.goods.service.UnitService;

@Service("unitService")
public class UnitServiceImpl extends BaseServiceimpl implements UnitService {
	public static Logger logger = LoggerFactory.getLogger(UnitServiceImpl.class);

	@Override
	public List<UnitGroup> getUnitGroupList(UnitGroup unitGroup) {
		List<UnitGroup> unitGroupList = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<UnitGroup>>> TypeRef = new TypeReference<ResultInfo<List<UnitGroup>>>() {};
		String url=httpUrl + "goods/unit/getunitgrouplist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, unitGroup,clientId,clientKey, TypeRef);
			unitGroupList = (List<UnitGroup>) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
		return unitGroupList;
	}
	
	@Override
	public Unit getUnitById(Unit unit) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Unit>> TypeRef = new TypeReference<ResultInfo<Unit>>() {};
		String url=httpUrl + "goods/unit/getunitbyid.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, unit,clientId,clientKey, TypeRef);
			unit = (Unit) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
		return unit;
	}

	@Override
	public ResultInfo<?> addUnit(Unit unit) {
		ResultInfo<?> result = new ResultInfo<>();
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Unit>> TypeRef = new TypeReference<ResultInfo<Unit>>() {};
		String url=httpUrl + "goods/unit/addunit.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, unit,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

	@Override
	public Map<String, Object> getUnitListPage(Unit unit, Page page) {
		List<Unit> list = null;
		Map<String,Object> map = new HashMap<>();
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<List<Unit>>> TypeRef = new TypeReference<ResultInfo<List<Unit>>>() {};
			String url=httpUrl + "goods/unit/getunitlistpage.htm";
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, unit,clientId,clientKey, TypeRef,page);
			list = (List<Unit>) result.getData();
			page = result.getPage();
			
			map.put("list", list);
			map.put("page", page);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return map;
	}

	@Override
	public ResultInfo<?> editUnit(Unit unit) {
		ResultInfo<?> result = new ResultInfo<>();
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Unit>> TypeRef = new TypeReference<ResultInfo<Unit>>() {};
		String url=httpUrl + "goods/unit/editunit.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, unit,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

	@Override
	public ResultInfo<?> delUnitById(Unit unit) {
		ResultInfo<?> result = new ResultInfo<>();
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Unit>> TypeRef = new TypeReference<ResultInfo<Unit>>() {};
		String url=httpUrl + "goods/unit/delunit.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, unit,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

	@Override
	public List<Unit> getAllUnitList(Unit unit) {
		List<Unit> unitList = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<Unit>>> TypeRef = new TypeReference<ResultInfo<List<Unit>>>() {};
		String url=httpUrl + "goods/unit/getallunitlist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, unit,clientId,clientKey, TypeRef);
			unitList = (List<Unit>) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
		return unitList;
	}
}
