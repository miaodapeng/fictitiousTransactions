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
import com.vedeng.logistics.model.StorageRoom;
import com.vedeng.logistics.model.Warehouse;
import com.vedeng.logistics.service.StorageroomService;

@Service("storageroomService")
public class StorageroomServiceImpl  extends BaseServiceimpl implements StorageroomService {
	public static Logger logger = LoggerFactory.getLogger(StorageroomServiceImpl.class);

	@Override
	public  List<StorageRoom> getStorageListById(Warehouse warehouses) {
		List<StorageRoom> list = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<StorageRoom>>> TypeRef = new TypeReference<ResultInfo<List<StorageRoom>>>() {};
		String url=httpUrl + "warehouses/getstorageroombywidlistpage.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url,warehouses,clientId,clientKey, TypeRef);
			list = (List<StorageRoom>) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return list;
	}
	
	@Override
	public Map<String, Object> getStorageroomList(StorageRoom storageroom, Page page) {
		List<StorageRoom> list = null;
		Map<String,Object> map = new HashMap<>();
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<StorageRoom>>> TypeRef = new TypeReference<ResultInfo<List<StorageRoom>>>() {};
		String url=httpUrl + "storageroom/getstorageroomlistpage.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url,storageroom,clientId,clientKey, TypeRef,page);
			list = (List<StorageRoom>) result.getData();
			page = result.getPage();
			map.put("list", list);
			map.put("page", page);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return map;
	}

	@Override
	public List<Warehouse> getWarehouseByCompanyId(Warehouse warehouses) {
        List<Warehouse> list = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<Warehouse>>> TypeRef = new TypeReference<ResultInfo<List<Warehouse>>>() {};
		String url=httpUrl + "storageroom/getwarehouselist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, warehouses,clientId,clientKey, TypeRef);
			list = (List<Warehouse>) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return list;
	}

	@Override
	public ResultInfo saveStorageRoom(StorageRoom storageroom, HttpServletRequest request, HttpSession session) {
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		Long time = DateUtil.sysTimeMillis();
		//库房的基本信息
		storageroom.setCompanyId(user.getCompanyId());
		storageroom.setIsEnable(ErpConst.ONE);
		storageroom.setAddTime(time);
		storageroom.setCreator(user.getUserId());
		storageroom.setModTime(time);
		storageroom.setUpdater(user.getUserId());
		// 接口调用
		String url = httpUrl + "storageroom/savestorageroom.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<StorageRoom>> TypeRef2 = new TypeReference<ResultInfo<StorageRoom>>() {};
		try {
			ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, storageroom, clientId, clientKey, TypeRef2);
			return result2;
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public StorageRoom getStorageroomById(StorageRoom storageroom) {
		// 接口调用
		String url = httpUrl + "storageroom/getstorageroombyid.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<StorageRoom>> TypeRef2 = new TypeReference<ResultInfo<StorageRoom>>() {};
		try {
			ResultInfo<StorageRoom> result2 = (ResultInfo<StorageRoom>) HttpClientUtils.post(url, storageroom, clientId, clientKey,TypeRef2);
			if (null == result2) {
				return null;
			}
			StorageRoom res = (StorageRoom) result2.getData();
			return res;
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public List<StorageArea> getStorageArea(StorageRoom storageroom) {
		 List<StorageArea> list = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<StorageArea>>> TypeRef = new TypeReference<ResultInfo<List<StorageArea>>>() {};
		String url=httpUrl + "storageroom/getstoragearealist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, storageroom,clientId,clientKey, TypeRef);
			list = (List<StorageArea>) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return list;
	}

	@Override
	public ResultInfo editStorageRoom(StorageRoom storageroom, HttpServletRequest request, HttpSession session) {
		ResultInfo<?> result = null;
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		Long time = DateUtil.sysTimeMillis();
		storageroom.setModTime(time);
		storageroom.setUpdater(user.getUserId());
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		String url=httpUrl + "storageroom/editstorageroom.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, storageroom,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

	@Override
	public ResultInfo<?> upDisableStorageRoom(StorageRoom storageroom) {
        ResultInfo<?> result = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<StorageRoom>> TypeRef = new TypeReference<ResultInfo<StorageRoom>>() {};
		String url=httpUrl + "storageroom/updisablestorageroom.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, storageroom,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

	@Override
	public StorageRoom getStorageRoomByName(StorageRoom storageroom, HttpSession session) {
		// 接口调用
		String url = httpUrl + "storageroom/getstorageroombyname.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<StorageRoom>> TypeRef2 = new TypeReference<ResultInfo<StorageRoom>>() {};
		try {
			ResultInfo<StorageRoom> result2 = (ResultInfo<StorageRoom>) HttpClientUtils.post(url, storageroom, clientId, clientKey,TypeRef2);
			if (null == result2) {
				return null;
			}
			StorageRoom res = (StorageRoom) result2.getData();
			return res;
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public List<StorageRoom> getAllStorageRoom(StorageRoom storageroom) {
		 List<StorageRoom> list = null;
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<List<StorageRoom>>> TypeRef = new TypeReference<ResultInfo<List<StorageRoom>>>() {};
			String url=httpUrl + "storageroom/getallstorageroom.htm";
			try {
				ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, storageroom,clientId,clientKey, TypeRef);
				list = (List<StorageRoom>) result.getData();
			} catch (IOException e) {
				logger.error(Contant.ERROR_MSG, e);
			}
			return list;
	}

	@Override
	public StorageRoom getGoodsNum(StorageRoom storageroom) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<StorageRoom>> TypeRef2 = new TypeReference<ResultInfo<StorageRoom>>() {};
		String url=httpUrl + "storageroom/getgoodslist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url,storageroom,clientId,clientKey, TypeRef2);
			if (null == result) {
				return null;
			}
			StorageRoom res = (StorageRoom) result.getData();
			return res;
		} catch (IOException e) {
			return null;
		}
	}

}
