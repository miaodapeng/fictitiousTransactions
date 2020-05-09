package com.vedeng.logistics.service.impl;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.common.constants.Contant;
import com.vedeng.logistics.dao.WarehouseGoodsSetMapper;
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
import com.vedeng.goods.model.Brand;
import com.vedeng.goods.model.Category;
import com.vedeng.goods.model.Goods;
import com.vedeng.logistics.model.StorageRack;
import com.vedeng.logistics.model.WarehouseGoodsSet;
import com.vedeng.logistics.service.WarehouseGoodsSetService;

@Service("warehouseGoodsSetService")
public class WarehouseGoodsSetServiceImpl  extends BaseServiceimpl implements WarehouseGoodsSetService {
	public static Logger logger = LoggerFactory.getLogger(WarehouseGoodsSetServiceImpl.class);

	@Resource
	private WarehouseGoodsSetMapper warehouseGoodsSetMapper;

	@Override
	public Map<String, Object> getWarehouseGoodsSetList(WarehouseGoodsSet warehouseGoodsSet, Page page) {
		List<WarehouseGoodsSet> list = null;
		Map<String,Object> map = new HashMap<>();
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<WarehouseGoodsSet>>> TypeRef = new TypeReference<ResultInfo<List<WarehouseGoodsSet>>>() {};
		String url=httpUrl + "warehousegoodsset/getwarehousegoodssetlistpage.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url,warehouseGoodsSet,clientId,clientKey, TypeRef,page);
			list = (List<WarehouseGoodsSet>) result.getData();
			page = result.getPage();
			map.put("list", list);
			map.put("page", page);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return map;
	}

	@Override
	public ResultInfo<?> delWarehouseGoods(WarehouseGoodsSet warehouseGoodsSet) {
        ResultInfo<?> result = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<WarehouseGoodsSet>> TypeRef = new TypeReference<ResultInfo<WarehouseGoodsSet>>() {};
		String url=httpUrl + "warehousegoodsset/delwarehousegoods.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, warehouseGoodsSet,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

	@Override
	public ResultInfo<?> delBatchWarehouseGoods(List<WarehouseGoodsSet> list) {
		ResultInfo<?> result = null;
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
			String url=httpUrl + "warehousegoodsset/delbatchwarehousegoods.htm";
			result = (ResultInfo<?>) HttpClientUtils.post(url, list,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

	@Override
	public List<String> batchSaveSkuName(List<WarehouseGoodsSet> list) {
		List<String> result_list = null;
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<List<String>>> TypeRef = new TypeReference<ResultInfo<List<String>>>() {};
			String url=httpUrl + "warehousegoodsset/batchsaveskuname.htm";
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, list,clientId,clientKey, TypeRef);
			result_list = (List<String>) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result_list;
	}

	@Override
	public ResultInfo<?> batchSaveWarehouseGoods(List<WarehouseGoodsSet> list) {
		ResultInfo<?> result = null;
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<List<WarehouseGoodsSet>>> TypeRef = new TypeReference<ResultInfo<List<WarehouseGoodsSet>>>() {};
			String url=httpUrl + "warehousegoodsset/batchsavewarehousegoods.htm";
			result = (ResultInfo<?>) HttpClientUtils.post(url, list,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

	@Override
	public Map<String, Object> getWarehouseList(WarehouseGoodsSet warehouseGoodsSet,Page page) {
		List<WarehouseGoodsSet> list = null;
		Map<String,Object> map = new HashMap<>();
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<WarehouseGoodsSet>>> TypeRef = new TypeReference<ResultInfo<List<WarehouseGoodsSet>>>() {};
		String url=httpUrl + "warehousegoodsset/getwarehouselistpage.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url,warehouseGoodsSet,clientId,clientKey, TypeRef,page);
			list = (List<WarehouseGoodsSet>) result.getData();
			page = result.getPage();
			map.put("list", list);
			map.put("page", page);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return map;
	}

	@Override
	public List<Goods> getGoods(Goods goods) {
		List<Goods> result_list = null;
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<List<Goods>>> TypeRef = new TypeReference<ResultInfo<List<Goods>>>() {};
			String url=httpUrl + "warehousegoodsset/getgoods.htm";
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, goods,clientId,clientKey, TypeRef);
			result_list = (List<Goods>) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result_list;
	}
	@Override
	public List<WarehouseGoodsSet> getWarehouseSetForGood(WarehouseGoodsSet warehouseGoodsSet) {
	    List<WarehouseGoodsSet> result_list = null;
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<List<WarehouseGoodsSet>>> TypeRef = new TypeReference<ResultInfo<List<WarehouseGoodsSet>>>() {};
			String url=httpUrl + "warehousegoodsset/getwarehousesetforgood.htm";
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, warehouseGoodsSet,clientId,clientKey, TypeRef);
			result_list = (List<WarehouseGoodsSet>) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result_list;
	}

	@Override
	public Map<String, WarehouseGoodsSet> getStorageLocationNameBuySku(List<String> skuList) {
		List<WarehouseGoodsSet> list = new ArrayList<>();
		for (String sku : skuList) {
			WarehouseGoodsSet warehouseGoodsSet = warehouseGoodsSetMapper.getStorageLocationNameBuySku(sku);
			if(warehouseGoodsSet != null){
				list.add(warehouseGoodsSet);
			}
		}
		Map<String, WarehouseGoodsSet> map=new HashMap<>();
		for(WarehouseGoodsSet warhouse:list){
			map.put(warhouse.getSku(),warhouse);
		}
		//Map<String, WarehouseGoodsSet> map = list.stream().collect(Collectors.toMap(WarehouseGoodsSet::getSku,warehouseGoodsSet -> warehouseGoodsSet));
		return map;
	}


}
