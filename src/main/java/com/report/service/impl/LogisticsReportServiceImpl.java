package com.report.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.common.constants.Contant;
import com.vedeng.logistics.dao.WarehouseGoodsOperateLogMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.report.dao.CommonReportMapper;
import com.report.dao.LogisticsReportMapper;
import com.report.model.export.ExpressExport;
import com.report.model.export.WareHouseLogExport;
import com.report.service.LogisticsReportService;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.logistics.model.Express;
import com.vedeng.logistics.model.FileDelivery;
import com.vedeng.logistics.model.WarehouseGoodsOperateLog;
import com.vedeng.logistics.model.WarehouseGoodsSet;

@SuppressWarnings("unchecked")
@Service("logisticsReportService")
public class LogisticsReportServiceImpl extends BaseServiceimpl implements LogisticsReportService {
	public static Logger logger = LoggerFactory.getLogger(LogisticsReportServiceImpl.class);

	@Autowired
	@Qualifier("logisticsReportMapper")
	private LogisticsReportMapper logisticsReportMapper;

	@Autowired
	@Qualifier("commonReportMapper")
	private CommonReportMapper commonReportMapper;

	@Override
	public List<WarehouseGoodsSet> exportWarehouseGoodsSetList(WarehouseGoodsSet warehouseGoodsSet, Page page) {
		List<WarehouseGoodsSet> warehouseGoodsSetList = null;

		Map<String, Object> warehouseGoodsSetMap = this.getWarehouseGoodsSetList(warehouseGoodsSet, page);
		Page pageInfo = (Page) warehouseGoodsSetMap.get("page");
		warehouseGoodsSetList = (List<WarehouseGoodsSet>) warehouseGoodsSetMap.get("list");
		Integer total = pageInfo.getTotalPage();
		for (int i = 2; i <= total; i++) {
			pageInfo.setPageNo(i);

			warehouseGoodsSetMap = this.getWarehouseGoodsSetList(warehouseGoodsSet, page);
			List<WarehouseGoodsSet> list = (List<WarehouseGoodsSet>) warehouseGoodsSetMap.get("list");
			if (null != list && list.size() > 0) {
				warehouseGoodsSetList.addAll(list);
			}
		}
		return warehouseGoodsSetList;
	}


	@Override
	public Map<String, Object> getWarehouseGoodsSetList(WarehouseGoodsSet warehouseGoodsSet, Page page) {
		String url = httpUrl + "report/logistics/getwarehousegoodssetlist.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<WarehouseGoodsSet>>> TypeRef2 = new TypeReference<ResultInfo<List<WarehouseGoodsSet>>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, warehouseGoodsSet, clientId, clientKey,
					TypeRef2, page);
			List<WarehouseGoodsSet> list = (List<WarehouseGoodsSet>) result.getData();
			page = result.getPage();

			Map<String, Object> map = new HashMap<>();
			map.put("list", list);
			map.put("page", page);
			return map;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}

	@Override
	public List<FileDelivery> exportFileDeliveryList(FileDelivery fileDelivery, List<Integer> creatorIds, Page page) {
		List<FileDelivery> fileDeliveryList = null;

		List<Integer> relatedIds = null;
		Express express = new Express();
		if (null != fileDelivery.getLogisticsId() && fileDelivery.getLogisticsId() > 0) {
			express.setLogisticsId(fileDelivery.getLogisticsId());
		}
		if (null != fileDelivery.getLogisticsNo() && !fileDelivery.getLogisticsNo().equals("")) {
			express.setLogisticsNo(fileDelivery.getLogisticsNo());
		}
		// 业务类型为文件寄送
		express.setBusinessType(498);

		String url = httpUrl + "report/logistics/getexpressinfo.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<Integer>>> TypeRef = new TypeReference<ResultInfo<List<Integer>>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, express, clientId, clientKey, TypeRef);
			if (result.getCode() == 0) {
				relatedIds = (List<Integer>) result.getData();
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}

		if(null == relatedIds){
			relatedIds = new ArrayList<>();
		}

		Map<String, Object> map = this.getFileDeliveryList(fileDelivery, creatorIds,relatedIds, page);
		Page pageInfo = (Page) map.get("page");
		fileDeliveryList = (List<FileDelivery>) map.get("list");
		Integer total = pageInfo.getTotalPage();
		for (int i = 2; i <= total; i++) {
			pageInfo.setPageNo(i);

			map = this.getFileDeliveryList(fileDelivery, creatorIds,relatedIds, page);
			List<FileDelivery> list = (List<FileDelivery>) map.get("list");
			if (null != list && list.size() > 0) {
				fileDeliveryList.addAll(list);
			}
		}

		return fileDeliveryList;
	}

	@Override
	public Map<String, Object> getFileDeliveryList(FileDelivery fileDelivery, List<Integer> creatorIds, List<Integer> relatedIds, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("creatorIds", creatorIds);
		map.put("relatedIds", relatedIds);
		map.put("fileDelivery", fileDelivery);
		map.put("page", page);
		List<FileDelivery> list = logisticsReportMapper.getFileDeliveryListPage(map);

		if (null != list && list.size() > 0) {
			// 客户性质（分销/终端） 合作次数 快递 快递单号 计重数量 费用 快递状态 备注
			String url = httpUrl + "report/logistics/getfiledeliverydata.htm";
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<List<FileDelivery>>> TypeRef2 = new TypeReference<ResultInfo<List<FileDelivery>>>() {
			};
			try {
				ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, list, clientId, clientKey, TypeRef2);
				list = (List<FileDelivery>) result.getData();
				if (null == list || list.size() == 0) {
					return null;
				}
			} catch (IOException e) {
				logger.error(Contant.ERROR_MSG, e);
				return null;
			}

			List<Integer> customerIds = new ArrayList<>();// 客户
			List<Integer> supplierIds = new ArrayList<>();// 供应商

			for (FileDelivery delivery : list) {
				if (delivery.getTraderType().equals(1)) {
					customerIds.add(delivery.getTraderId());
				}
				if (delivery.getTraderType().equals(2)) {
					supplierIds.add(delivery.getTraderId());
				}
			}
			// 客户归属
			if (customerIds.size() > 0) {
				List<User> customerList = commonReportMapper.getUserByTraderIdList(customerIds, ErpConst.ONE);
				if (null != customerList && customerList.size() > 0) {
					for (User u : customerList) {
						for (FileDelivery delivery : list) {
							if (delivery.getTraderType().equals(1) && u.getTraderId().equals(delivery.getTraderId())) {
								delivery.setOwner(u.getUsername());
							}
						}
					}
				}
			}
			// 供应商归属
			if (supplierIds.size() > 0) {
				List<User> supplierList = commonReportMapper.getUserByTraderIdList(supplierIds, ErpConst.TWO);
				if (null != supplierList && supplierList.size() > 0) {
					for (User u : supplierList) {
						for (FileDelivery delivery : list) {
							if (delivery.getTraderType().equals(2) && u.getTraderId().equals(delivery.getTraderId())) {
								delivery.setOwner(u.getUsername());
							}
						}
					}
				}
			}
		}
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("list", list);
		resultMap.put("page", page);
		return resultMap;
	}

	@Override
	public List<WarehouseGoodsSet> exportWarehouseList(WarehouseGoodsSet warehouseGoodsSet, Page page) {
		List<WarehouseGoodsSet> warehouseGoodsSetList = null;

		Map<String, Object> warehouseGoodsSetMap = this.getWarehouseList(warehouseGoodsSet, page);
		Page pageInfo = (Page) warehouseGoodsSetMap.get("page");
		warehouseGoodsSetList = (List<WarehouseGoodsSet>) warehouseGoodsSetMap.get("list");
		Integer total = pageInfo.getTotalPage();
		for (int i = 2; i <= total; i++) {
			pageInfo.setPageNo(i);

			warehouseGoodsSetMap = this.getWarehouseList(warehouseGoodsSet, page);
			List<WarehouseGoodsSet> list = (List<WarehouseGoodsSet>) warehouseGoodsSetMap.get("list");
			if (null != list && list.size() > 0) {
				warehouseGoodsSetList.addAll(list);
			}
		}
		return warehouseGoodsSetList;
	}

	@Override
	public Map<String, Object> getWarehouseList(WarehouseGoodsSet warehouseGoodsSet, Page page) {
		List<WarehouseGoodsSet> list = null;
		Map<String,Object> map = new HashMap<>();
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<WarehouseGoodsSet>>> TypeRef = new TypeReference<ResultInfo<List<WarehouseGoodsSet>>>() {};
		String url=httpUrl + "report/logistics/getwarehouselist.htm";
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
	public List<ExpressExport> exportExpressList(Express express, Page page) {
		List<ExpressExport> list = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<ExpressExport>>> TypeRef = new TypeReference<ResultInfo<List<ExpressExport>>>() {};
		String url = httpUrl + "report/logistics/getexpresslist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, express, clientId, clientKey, TypeRef, page);
			list = (List<ExpressExport>) result.getData();
			// 发件人
			if (null != list && list.size() > 0) {
				int list_size = list.size();
				List<Integer> userIdList = new ArrayList<>();
				for (int i = 0; i < list_size; i++) {
					if (list.get(i).getCreator() > 0) {
						userIdList.add(list.get(i).getCreator());
					}
				}

				if (userIdList.size() > 0) {
					//去除重复
					userIdList = new ArrayList<Integer>(new HashSet<Integer>(userIdList));
					List<User> userList = commonReportMapper.getUserByUserIds(userIdList);
					for (int i = 0; i < list_size; i++) {
						for (int j = 0; j < userList.size(); j++) {
							list.get(i).setCreatName(userList.get(j).getUsername());
						}
					}
				}
				//收件地址
				
			}

			Page pageInfo = result.getPage();
			Integer total = pageInfo.getTotalPage();
			for (int i = 2; i <= total; i++) {
				pageInfo.setPageNo(i);

				List<ExpressExport> result_list = this.exportExpressList(express, page);
				if (null != list && list.size() > 0) {
					list.addAll(result_list);
				}
			}
			return list;
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return null;
	}

	@Override
	public List<FileDelivery> getFileDeliveryListByUName(User user) {
		return logisticsReportMapper.getFileDeliveryListByUName(user);
	}

	@Override
	public List<WareHouseLogExport> exportWareHouseOutList(WarehouseGoodsOperateLog warehouseGoodsOperateLog, Page page) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<WareHouseLogExport>>> TypeRef = new TypeReference<ResultInfo<List<WareHouseLogExport>>>() {};
		String url=httpUrl + "report/logistics/exportwarehouseoutlist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, warehouseGoodsOperateLog, clientId, clientKey, TypeRef, page);
			if(result != null && result.getCode() == 0){
				List<WareHouseLogExport> list = (List<WareHouseLogExport>) result.getData();
				if(null != list && !list.isEmpty()){
					List<Integer> userIdList = new ArrayList<>();
					for(int i=0;i<list.size();i++){
						userIdList.add(list.get(i).getCreator());
					}
					if(userIdList.size()>0){
						userIdList = new ArrayList<Integer>(new HashSet<Integer>(userIdList));
						List<User> userList = commonReportMapper.getUserByUserIds(userIdList);
						if(null != userList && !userList.isEmpty()){
							for(int i=0;i<list.size();i++){
								for(int j=0;j<userList.size();j++){
									if(list.get(i).getCreator().equals(userList.get(j).getUserId())){
										list.get(i).setCreatorNm(userList.get(j).getUsername());
									}
								}
							}
						}
					}
					//分页多次查询
					Page pageInfo = (Page) result.getPage();
					if(pageInfo.getPageNo() == 1){
						Integer total = pageInfo.getTotalPage();
						for (int i = 2; i <= total; i++) {
							pageInfo.setPageNo(i);
							List<WareHouseLogExport> result_list = this.exportWareHouseOutList(warehouseGoodsOperateLog, pageInfo);
							list.addAll(result_list);
						}
					}
					return list;
				}
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return null;
	}

	@Override
	public List<WareHouseLogExport> exportWareHouseInList(WarehouseGoodsOperateLog warehouseGoodsOperateLog, Page page) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<WareHouseLogExport>>> TypeRef = new TypeReference<ResultInfo<List<WareHouseLogExport>>>() {};
		String url=httpUrl + "report/logistics/exportwarehouseinlist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, warehouseGoodsOperateLog, clientId, clientKey, TypeRef, page);
			if(result != null && result.getCode() == 0){
				List<WareHouseLogExport> list = (List<WareHouseLogExport>) result.getData();
				if(null != list && !list.isEmpty()){
					List<Integer> userIdList = new ArrayList<>();
					String invoiceColor = "",invoiceIsEnable = "";
					for(int i=0;i<list.size();i++){
						userIdList.add(list.get(i).getCreator());
						userIdList.add(list.get(i).getCheckStatusUser());
						if(StringUtils.isNotBlank(list.get(i).getInvoiceNo())){
							if(list.get(i).getColorType().equals(2)){
								invoiceColor = "蓝字";
							}else{
								invoiceColor = "红字";
							}
							if(list.get(i).getIsEnable().equals(1)){
								invoiceIsEnable = "有效";
							}else{
								invoiceIsEnable = "作废";
							}
							list.get(i).setInvoiceColorTypeStr(invoiceColor+invoiceIsEnable);
						}
					}
					if(userIdList.size()>0){
						userIdList = new ArrayList<Integer>(new HashSet<Integer>(userIdList));
						List<User> userList = commonReportMapper.getUserByUserIds(userIdList);
						if(null != userList && !userList.isEmpty()){
							for(int i=0;i<list.size();i++){
								for(int j=0;j<userList.size();j++){
									if(userList.get(j).getUserId().equals(list.get(i).getCreator())){
										list.get(i).setCreatorNm(userList.get(j).getUsername());
									}
									if(userList.get(j).getUserId().equals(list.get(i).getCheckStatusUser())){
										list.get(i).setCheckStatusUserNm(userList.get(j).getUsername());
									}
								}
							}
						}
					}
					//分页多次查询
					Page pageInfo = (Page) result.getPage();
					if(pageInfo.getPageNo() == 1){
						Integer total = pageInfo.getTotalPage();
						for (int i = 2; i <= total; i++) {
							pageInfo.setPageNo(i);
							List<WareHouseLogExport> result_list = this.exportWareHouseInList(warehouseGoodsOperateLog, pageInfo);
							list.addAll(result_list);
						}
					}
					return list;
				}
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return null;
	}

	@Override
	public List<WarehouseGoodsSet> exportWarehouseGoodsSetsList(WarehouseGoodsSet warehouseGoodsSet) {
		return logisticsReportMapper.exportWarehouseGoodsSetsList(warehouseGoodsSet);
	}

}
