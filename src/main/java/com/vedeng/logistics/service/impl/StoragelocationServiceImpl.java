package com.vedeng.logistics.service.impl;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.common.constants.Contant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.DateUtil;
import com.vedeng.logistics.model.StorageArea;
import com.vedeng.logistics.model.StorageLocation;
import com.vedeng.logistics.model.StorageRack;
import com.vedeng.logistics.service.StoragelocationService;

@Service("storagelocationService")
public class StoragelocationServiceImpl  extends BaseServiceimpl implements StoragelocationService {
	public static Logger logger = LoggerFactory.getLogger(StoragelocationServiceImpl.class);

	@Override
	public Map<String, Object> getStorageLocationList(StorageLocation storageLocation, Page page) {
		List<StorageLocation> list = null;
		Map<String,Object> map = new HashMap<>();
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<StorageLocation>>> TypeRef = new TypeReference<ResultInfo<List<StorageLocation>>>() {};
		String url=httpUrl + "storagelocation/getstoragerlocationlistpage.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url,storageLocation,clientId,clientKey, TypeRef,page);
			list = (List<StorageLocation>) result.getData();
			page = result.getPage();
			map.put("list", list);
			map.put("page", page);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return map;
	}

	@Override
	public ResultInfo saveStorageLocation(StorageLocation storageLocation, HttpServletRequest request,
			HttpSession session) {
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		Long time = DateUtil.sysTimeMillis();
		//库区的基本信息
		storageLocation.setCompanyId(user.getCompanyId());
		storageLocation.setIsEnable(ErpConst.ONE);
		storageLocation.setAddTime(time);
		storageLocation.setCreator(user.getUserId());
		storageLocation.setModTime(time);
		storageLocation.setUpdater(user.getUserId());
		// 接口调用
		String url = httpUrl + "storagelocation/savestoragelocation.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<StorageLocation>> TypeRef2 = new TypeReference<ResultInfo<StorageLocation>>() {};
		try {
			ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, storageLocation, clientId, clientKey, TypeRef2);
			return result2;
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public StorageLocation getStorageLocationByName(StorageLocation storageLocation, HttpSession session) {
		// 接口调用
		String url = httpUrl + "storagelocation/getstoragelocationbyname.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<StorageLocation>> TypeRef2 = new TypeReference<ResultInfo<StorageLocation>>() {};
		try {
			ResultInfo<StorageLocation> result2 = (ResultInfo<StorageLocation>) HttpClientUtils.post(url, storageLocation, clientId, clientKey,TypeRef2);
			if (null == result2) {
				return null;
			}
			StorageLocation res = (StorageLocation) result2.getData();
			return res;
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public StorageLocation getStoragelocationById(StorageLocation storageLocation) {
		// 接口调用
		String url = httpUrl + "storagelocation/getstoragelocationbyid.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<StorageLocation>> TypeRef2 = new TypeReference<ResultInfo<StorageLocation>>() {};
		try {
			ResultInfo<StorageLocation> result2 = (ResultInfo<StorageLocation>) HttpClientUtils.post(url, storageLocation, clientId, clientKey,TypeRef2);
			if (null == result2) {
				return null;
			}
			StorageLocation res = (StorageLocation) result2.getData();
			return res;
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public ResultInfo editStorageLocation(StorageLocation storageLocation, HttpServletRequest request,
			HttpSession session) {
		ResultInfo<?> result = null;
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		Long time = DateUtil.sysTimeMillis();
		storageLocation.setModTime(time);
		storageLocation.setUpdater(user.getUserId());
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		String url=httpUrl + "storagelocation/editstoragelocation.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, storageLocation,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

	@Override
	public ResultInfo<?> updateDisableStorageLocation(StorageLocation storageLocation) {
		ResultInfo<?> result = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<StorageLocation>> TypeRef = new TypeReference<ResultInfo<StorageLocation>>() {};
		String url=httpUrl + "storagelocation/updisablestoragelocation.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, storageLocation,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

	@Override
	public List<StorageLocation> getStorageLocationListByCId(StorageLocation storageLocation) {
		List<StorageLocation> list = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<StorageLocation>>> TypeRef = new TypeReference<ResultInfo<List<StorageLocation>>>() {};
		String url=httpUrl + "storagelocation/getstoragelocationlistbycid.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, storageLocation,clientId,clientKey, TypeRef);
			list = (List<StorageLocation>) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return list;
	}

	@Override
	public List<StorageLocation> getAllStorageLocation(StorageLocation storageLocation) {
		List<StorageLocation> list = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<StorageLocation>>> TypeRef = new TypeReference<ResultInfo<List<StorageLocation>>>() {};
		String url=httpUrl + "storagelocation/getallstoragelocation.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, storageLocation,clientId,clientKey, TypeRef);
			list = (List<StorageLocation>) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return list;
	}

	@Override
	public StorageLocation getGoodsNum(StorageLocation storageLocation) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<StorageLocation>> TypeRef2 = new TypeReference<ResultInfo<StorageLocation>>() {};
		String url=httpUrl + "storagelocation/getgoodsnum.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url,storageLocation,clientId,clientKey, TypeRef2);
			if (null == result) {
				return null;
			}
			StorageLocation res = (StorageLocation) result.getData();
			return res;
		} catch (IOException e) {
			return null;
		}
	}


}
