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
import com.vedeng.logistics.service.StoragerackService;

@Service("storagerackService")
public class StoragerackServiceImpl  extends BaseServiceimpl implements StoragerackService {
	public static Logger logger = LoggerFactory.getLogger(StoragerackServiceImpl.class);

	@Override
	public Map<String, Object> getStorageRackList(StorageRack storageRack, Page page) {
		List<StorageRack> list = null;
		Map<String,Object> map = new HashMap<>();
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<StorageRack>>> TypeRef = new TypeReference<ResultInfo<List<StorageRack>>>() {};
		String url=httpUrl + "storagerack/getstorageracklistpage.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url,storageRack,clientId,clientKey, TypeRef,page);
			list = (List<StorageRack>) result.getData();
			page = result.getPage();
			map.put("list", list);
			map.put("page", page);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return map;
	}

	@Override
	public List<StorageArea> getStorageAreaList(StorageArea storageArea) {
		List<StorageArea> list = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<StorageArea>>> TypeRef = new TypeReference<ResultInfo<List<StorageArea>>>() {};
		String url=httpUrl + "storagerack/getstoragearealist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, storageArea,clientId,clientKey, TypeRef);
			list = (List<StorageArea>) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return list;
	}

	@Override
	public ResultInfo saveStorageRack(StorageRack storageRack, HttpServletRequest request, HttpSession session) {
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		Long time = DateUtil.sysTimeMillis();
		//库区的基本信息
		storageRack.setCompanyId(user.getCompanyId());
		storageRack.setIsEnable(ErpConst.ONE);
		storageRack.setAddTime(time);
		storageRack.setCreator(user.getUserId());
		storageRack.setModTime(time);
		storageRack.setUpdater(user.getUserId());
		// 接口调用
		String url = httpUrl + "storagerack/savestoragerack.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<StorageRack>> TypeRef2 = new TypeReference<ResultInfo<StorageRack>>() {};
		try {
			ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, storageRack, clientId, clientKey, TypeRef2);
			return result2;
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public StorageRack getStorageRackByName(StorageRack storageRack, HttpSession session) {
		// 接口调用
		String url = httpUrl + "storagerack/getstoragrackbyname.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<StorageRack>> TypeRef2 = new TypeReference<ResultInfo<StorageRack>>() {};
		try {
			ResultInfo<StorageRack> result2 = (ResultInfo<StorageRack>) HttpClientUtils.post(url, storageRack, clientId, clientKey,TypeRef2);
			if (null == result2) {
				return null;
			}
			StorageRack res = (StorageRack) result2.getData();
			return res;
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public StorageRack getStoragerackById(StorageRack storageRack) {
		// 接口调用
		String url = httpUrl + "storagerack/getstoragrackbyid.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<StorageRack>> TypeRef2 = new TypeReference<ResultInfo<StorageRack>>() {};
		try {
			ResultInfo<StorageRack> result2 = (ResultInfo<StorageRack>) HttpClientUtils.post(url, storageRack, clientId, clientKey,TypeRef2);
			if (null == result2) {
				return null;
			}
			StorageRack res = (StorageRack) result2.getData();
			return res;
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public List<StorageLocation> getStorageLocationList(StorageRack storageRackInfo) {
		List<StorageLocation> list = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<StorageLocation>>> TypeRef = new TypeReference<ResultInfo<List<StorageLocation>>>() {};
		String url=httpUrl + "storagerack/getstoragelocationlist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, storageRackInfo,clientId,clientKey, TypeRef);
			list = (List<StorageLocation>) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return list;
	}

	@Override
	public ResultInfo editStorageRack(StorageRack storageRack, HttpServletRequest request, HttpSession session) {
		ResultInfo<?> result = null;
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		Long time = DateUtil.sysTimeMillis();
		storageRack.setModTime(time);
		storageRack.setUpdater(user.getUserId());
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		String url=httpUrl + "storagerack/editstoragerack.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, storageRack,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

	@Override
	public ResultInfo<?> updateDisableStorageRack(StorageRack storageRack) {
		ResultInfo<?> result = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<StorageRack>> TypeRef = new TypeReference<ResultInfo<StorageRack>>() {};
		String url=httpUrl + "storagerack/updatedisablestoragerack.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, storageRack,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

	@Override
	public List<StorageRack> getStorageRackListByCId(StorageRack storageRack) {
		List<StorageRack> list = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<StorageRack>>> TypeRef = new TypeReference<ResultInfo<List<StorageRack>>>() {};
		String url=httpUrl + "storagerack/getstorageracklistbycid.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, storageRack,clientId,clientKey, TypeRef);
			list = (List<StorageRack>) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return list;
	}

	@Override
	public List<StorageRack> getAllStorageRack(StorageRack storageRack) {
		List<StorageRack> list = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<StorageRack>>> TypeRef = new TypeReference<ResultInfo<List<StorageRack>>>() {};
		String url=httpUrl + "storagerack/getallstoragerack.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, storageRack,clientId,clientKey, TypeRef);
			list = (List<StorageRack>) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return list;
	}

	@Override
	public StorageRack getGoodsNum(StorageRack storageRack) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<StorageRack>> TypeRef2 = new TypeReference<ResultInfo<StorageRack>>() {};
		String url=httpUrl + "storagerack/getgoodsnum.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url,storageRack,clientId,clientKey, TypeRef2);
			if (null == result) {
				return null;
			}
			StorageRack res = (StorageRack) result.getData();
			return res;
		} catch (IOException e) {
			return null;
		}
	}
}
