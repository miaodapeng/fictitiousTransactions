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
import com.vedeng.goods.model.Category;
import com.vedeng.logistics.model.StorageArea;
import com.vedeng.logistics.model.StorageRack;
import com.vedeng.logistics.model.StorageRoom;
import com.vedeng.logistics.service.StorageareaService;

@Service("storageAreaService")
public class StorageareaServiceImpl  extends BaseServiceimpl implements StorageareaService {
	public static Logger logger = LoggerFactory.getLogger(StorageareaServiceImpl.class);

	@Override
	public Map<String, Object> getStorageAreaList(StorageArea storageArea, Page page) {
		List<StorageArea> list = null;
		Map<String,Object> map = new HashMap<>();
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<StorageArea>>> TypeRef = new TypeReference<ResultInfo<List<StorageArea>>>() {};
		String url=httpUrl + "storagearea/getstoragearealistpage.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url,storageArea,clientId,clientKey, TypeRef,page);
			list = (List<StorageArea>) result.getData();
			page = result.getPage();
			map.put("list", list);
			map.put("page", page);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return map;
	}

	@Override
	public ResultInfo saveStorageArea(StorageArea storageArea, HttpServletRequest request, HttpSession session) {
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		Long time = DateUtil.sysTimeMillis();
		//库区的基本信息
		storageArea.setCompanyId(user.getCompanyId());
		storageArea.setIsEnable(ErpConst.ONE);
		storageArea.setAddTime(time);
		storageArea.setCreator(user.getUserId());
		storageArea.setModTime(time);
		storageArea.setUpdater(user.getUserId());
		// 接口调用
		String url = httpUrl + "storagearea/savestoragearea.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<StorageArea>> TypeRef2 = new TypeReference<ResultInfo<StorageArea>>() {};
		try {
			ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, storageArea, clientId, clientKey, TypeRef2);
			return result2;
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public StorageArea getStorageareaById(StorageArea storageArea) {
		// 接口调用
		String url = httpUrl + "storagearea/getstorageareabyid.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<StorageArea>> TypeRef2 = new TypeReference<ResultInfo<StorageArea>>() {};
		try {
			ResultInfo<StorageArea> result2 = (ResultInfo<StorageArea>) HttpClientUtils.post(url, storageArea, clientId, clientKey,TypeRef2);
			if (null == result2) {
				return null;
			}
			StorageArea res = (StorageArea) result2.getData();
			return res;
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public ResultInfo editStorageArea(StorageArea storageArea, HttpServletRequest request, HttpSession session) {
		ResultInfo<?> result = null;
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		Long time = DateUtil.sysTimeMillis();
		storageArea.setModTime(time);
		storageArea.setUpdater(user.getUserId());
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		String url=httpUrl + "storagearea/editstoragearea.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, storageArea,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

	@Override
	public List<StorageRoom> getStorageRoomList(StorageRoom storageroom) {
		List<StorageRoom> list = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<StorageRoom>>> TypeRef = new TypeReference<ResultInfo<List<StorageRoom>>>() {};
		String url=httpUrl + "storagearea/getstorageroomlist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, storageroom,clientId,clientKey, TypeRef);
			list = (List<StorageRoom>) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return list;
	}

	@Override
	public StorageArea getStorageAreaByName(StorageArea storageArea, HttpSession session) {
		// 接口调用
		String url = httpUrl + "storagearea/getstorageareabyname.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<StorageArea>> TypeRef2 = new TypeReference<ResultInfo<StorageArea>>() {};
		try {
			ResultInfo<StorageArea> result2 = (ResultInfo<StorageArea>) HttpClientUtils.post(url, storageArea, clientId, clientKey,TypeRef2);
			if (null == result2) {
				return null;
			}
			StorageArea res = (StorageArea) result2.getData();
			return res;
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public List<StorageRack> getStorageRackByRId(StorageArea storageArea) {
		List<StorageRack> list = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<StorageRack>>> TypeRef = new TypeReference<ResultInfo<List<StorageRack>>>() {};
		String url=httpUrl + "storagearea/getstorageracklist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, storageArea,clientId,clientKey, TypeRef);
			list = (List<StorageRack>) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return list;
	}
	
	@Override
	public ResultInfo<?> upDisableStorageArea(StorageArea storageArea) {
		ResultInfo<?> result = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<StorageArea>> TypeRef = new TypeReference<ResultInfo<StorageArea>>() {};
		String url=httpUrl + "storagearea/updisablestoragearea.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, storageArea,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

	@Override
	public List<StorageArea> getStorageAreaListByCId(StorageArea storageArea) {
		List<StorageArea> list = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<StorageArea>>> TypeRef = new TypeReference<ResultInfo<List<StorageArea>>>() {};
		String url=httpUrl + "storagearea/getstorageareabycidlist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, storageArea,clientId,clientKey, TypeRef);
			list = (List<StorageArea>) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return list;
	}

	@Override
	public List<StorageArea> getAllStorageArea(StorageArea storageArea) {
		List<StorageArea> list = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<StorageArea>>> TypeRef = new TypeReference<ResultInfo<List<StorageArea>>>() {};
		String url=httpUrl + "storagearea/getallstoragearea.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, storageArea,clientId,clientKey, TypeRef);
			list = (List<StorageArea>) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return list;
	}

	@Override
	public StorageArea getGoodsNum(StorageArea storageArea) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<StorageArea>> TypeRef2 = new TypeReference<ResultInfo<StorageArea>>() {};
		String url=httpUrl + "storagearea/getgoodsnum.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url,storageArea,clientId,clientKey, TypeRef2);
			if (null == result) {
				return null;
			}
			StorageArea res = (StorageArea) result.getData();
			return res;
		} catch (IOException e) {
			return null;
		}
	}


}
