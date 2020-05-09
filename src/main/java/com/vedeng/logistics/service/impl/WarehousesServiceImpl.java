package com.vedeng.logistics.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.common.constants.Contant;
import com.vedeng.logistics.dao.BarcodeMapper;
import com.vedeng.logistics.dao.WarehouseGoodsStatusMapper;
import com.vedeng.logistics.model.*;
import io.netty.util.internal.UnstableApi;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.authorization.dao.CompanyMapper;
import com.vedeng.authorization.model.Company;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.DateUtil;
import com.vedeng.goods.model.Goods;
import com.vedeng.logistics.service.WarehousesService;
import com.vedeng.order.model.Quoteorder;
import com.vedeng.system.service.RegionService;

@Service("warehousesService")
public class WarehousesServiceImpl extends BaseServiceimpl implements WarehousesService {
	public static Logger logger = LoggerFactory.getLogger(WarehousesServiceImpl.class);

	@Autowired
	@Qualifier("companyMapper")
	private CompanyMapper companyMapper;
	
	@Autowired
	@Qualifier("regionService")
	private RegionService regionService;

	@Autowired
	private BarcodeMapper barcodeMapper;

	@Autowired
	@Qualifier("warehouseGoodsStatusMapper")
	private WarehouseGoodsStatusMapper warehouseGoodsStatusMapper;
	@Override
	public Map<String, Object> getWarehouseList(Warehouse warehouses, Page page) {
		List<Warehouse> list = null;
		Map<String,Object> map = new HashMap<>();
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<Warehouse>>> TypeRef = new TypeReference<ResultInfo<List<Warehouse>>>() {};
		String url=httpUrl + "warehouses/getwarehouseslistpage.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url,warehouses,clientId,clientKey, TypeRef,page);
			list = (List<Warehouse>) result.getData();
			for(int i=0;i<list.size();i++){
				//数据处理(地区)
				Integer areaId = list.get(i).getAreaId();
				if(areaId > 0){
					String region = (String) regionService.getRegion(areaId, 2);
					list.get(i).setAreaName(region);
				}
			}
			page = result.getPage();
			map.put("list", list);
			map.put("page", page);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return map;
	}

	@Override
	public Company getCompanyName(int companyId) {
		return companyMapper.selectByPrimaryKey(companyId);
	}
    
	@Override
	public Warehouse getWarehouseByName(Warehouse warehouses, HttpSession session) {
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		warehouses.setCompanyId(user.getCompanyId());
		// 接口调用
		String url = httpUrl + "warehouses/getwarehousebyname.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Warehouse>> TypeRef2 = new TypeReference<ResultInfo<Warehouse>>() {};
		try {
			ResultInfo<Warehouse> result2 = (ResultInfo<Warehouse>) HttpClientUtils.post(url, warehouses, clientId, clientKey,TypeRef2);
			if (null == result2) {
				return null;
			}
			Warehouse res = (Warehouse) result2.getData();
			if(null != res){
				//数据处理(地区)
				Integer areaId = res.getAreaId();
				if(areaId > 0){
					String region = (String) regionService.getRegion(areaId, 2);
					res.setAreaName(region);
				}
			}
			return res;
		} catch (IOException e) {
			return null;
		}
	}
	
	@Override
	public ResultInfo saveWarehouse(Warehouse warehouses, HttpServletRequest request, HttpSession session) {
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		Long time = DateUtil.sysTimeMillis();
		//仓库的基本信息
		warehouses.setCompanyId(user.getCompanyId());
		warehouses.setIsEnable(ErpConst.ONE);
		if(Integer.parseInt(request.getParameter("zone")) > 0){
			warehouses.setAreaId(Integer.parseInt(request.getParameter("zone")));
			warehouses.setAreaIds(request.getParameter("province")+","+request.getParameter("city")+","+request.getParameter("zone"));
		}else{
			warehouses.setAreaId(Integer.parseInt(request.getParameter("city")));
			warehouses.setAreaIds(request.getParameter("province")+","+request.getParameter("city")+","+request.getParameter("zone"));
		}
		warehouses.setAddTime(time);
		warehouses.setCreator(user.getUserId());
		warehouses.setModTime(time);
		warehouses.setUpdater(user.getUserId());
		warehouses.setWarehouseName((String) request.getParameter("warehouseName"));
		warehouses.setAddress((String) request.getParameter("wareAddress"));
		warehouses.setComments((String) request.getParameter("comments"));
		// 接口调用
		String url = httpUrl + "warehouses/savewarehouse.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Warehouse>> TypeRef2 = new TypeReference<ResultInfo<Warehouse>>() {};
		try {
			ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, warehouses, clientId, clientKey, TypeRef2);
			return result2;
		} catch (IOException e) {
			return null;
		}
	}
	
	@Override
	public ResultInfo<?> disableWarehouse(Warehouse warehouses) {
		
        ResultInfo<?> result = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Warehouse>> TypeRef = new TypeReference<ResultInfo<Warehouse>>() {};
		String url=httpUrl + "warehouses/disablewarehouse.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, warehouses,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}
	
	@Override
	public Warehouse getWarehouseById(Warehouse warehouses){
		// 接口调用
		String url = httpUrl + "warehouses/getwarehousebyid.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Warehouse>> TypeRef2 = new TypeReference<ResultInfo<Warehouse>>() {};
		try {
			ResultInfo<Warehouse> result2 = (ResultInfo<Warehouse>) HttpClientUtils.post(url, warehouses, clientId, clientKey,TypeRef2);
			if (null == result2) {
				return null;
			}
			Warehouse res = (Warehouse) result2.getData();
			if(null != res){
				//数据处理(地区)
				Integer areaId = res.getAreaId();
				System.out.println("areaId&&"+areaId);
				if(areaId > 0){
					String region = (String) regionService.getRegion(areaId, 2);
					res.setAreaName(region);
				}
			}
			return res;
		} catch (IOException e) {
			return null;
		}
	}
	
	@Override
	public ResultInfo<?> editWarehouse(Warehouse warehouses, HttpServletRequest request, HttpSession session) {
		ResultInfo<?> result = null;
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		Long time = DateUtil.sysTimeMillis();
		if(Integer.parseInt(request.getParameter("zone")) > 0){
			warehouses.setAreaId(Integer.parseInt(request.getParameter("zone")));
			warehouses.setAreaIds(request.getParameter("province")+","+request.getParameter("city")+","+request.getParameter("zone"));
		}else{
			warehouses.setAreaId(Integer.parseInt(request.getParameter("city")));
			warehouses.setAreaIds(request.getParameter("province")+","+request.getParameter("city")+","+request.getParameter("zone"));
		}
		warehouses.setCreator(user.getUserId());
		warehouses.setModTime(time);
		warehouses.setUpdater(user.getUserId());
		warehouses.setWarehouseName((String) request.getParameter("warehouseName"));
		warehouses.setAddress((String) request.getParameter("wareAddress"));
		warehouses.setComments((String) request.getParameter("comments"));
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		String url=httpUrl + "warehouses/editwarehouse.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, warehouses,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

	@Override
	public List<Warehouse> getAllWarehouse(Warehouse warehouses) {
		List<Warehouse> list = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<Warehouse>>> TypeRef = new TypeReference<ResultInfo<List<Warehouse>>>() {};
		String url=httpUrl + "warehouses/getallwarehouse.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url,warehouses,clientId,clientKey, TypeRef);
			list = (List<Warehouse>) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return list;
	}

	@Override
	public Warehouse getGoodsList(Warehouse warehouses) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Warehouse>> TypeRef2 = new TypeReference<ResultInfo<Warehouse>>() {};
		String url=httpUrl + "warehouses/getgoodslist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url,warehouses,clientId,clientKey, TypeRef2);
			if (null == result) {
				return null;
			}
			Warehouse res = (Warehouse) result.getData();
			return res;
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public List<Warehouse> getLsWarehouse(Warehouse warehouses) {
		List<Warehouse> list = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<Warehouse>>> TypeRef = new TypeReference<ResultInfo<List<Warehouse>>>() {};
		String url=httpUrl + "warehouses/getlswarehouse.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url,warehouses,clientId,clientKey, TypeRef);
			list = (List<Warehouse>) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return list;
	}

	@Override
	public Barcode getBarcodeInfoById(Integer barcodeId) {
		Barcode barcode = barcodeMapper.selectByPrimaryKey(barcodeId);
		return barcode;
	}

	@Override
	public List<WarehouseGoodsStatus> getErrorStockGoodsList() {
		List<WarehouseGoodsStatus> list = new ArrayList<>();
		List<WarehouseStock> allGoodsIdList = warehouseGoodsStatusMapper.getAllStockId();
		for (WarehouseStock warehouseStock : allGoodsIdList) {
			//获取老库存表数据
			List<WarehouseGoodsStatus> statuslist = warehouseGoodsStatusMapper.getWarehouseStatusByGoodsId(warehouseStock.getGoodsId());
			//真实库存量
			List<WarehouseGoodsStatus> logList = warehouseGoodsStatusMapper.getReallGoodsStockNumByGoodsId(warehouseStock.getGoodsId());
			//处理判断错误数据
			if(CollectionUtils.isNotEmpty(logList)){
				for (WarehouseGoodsStatus oldInfo : statuslist) {
					for (WarehouseGoodsStatus newInfo : logList) {
						if (isWarehouseGoodsStatusSame(oldInfo, newInfo)) {
							oldInfo.setSameFalg(true);
							if (!oldInfo.getNum().equals(newInfo.getNum())) {
								logger.info("getErrorStockGoods WarehouseGoodsStatusId:{},oldNum:{}", oldInfo.getWarehouseGoodsStatusId(), oldInfo.getNum());
								oldInfo.setNum(newInfo.getNum());
								list.add(oldInfo);
							}
						}
					}
				}
			}
			for (WarehouseGoodsStatus oldInfo : statuslist) {
				if(!oldInfo.getSameFalg()&&oldInfo.getNum() != 0 ){
					logger.info("getErrorStockGoods WarehouseGoodsStatusId:{},oldNum:{}",oldInfo.getWarehouseGoodsStatusId(),oldInfo.getNum());
					oldInfo.setNum(0);
					list.add(oldInfo);
				}
			}
		}
		return list;
	}

	@Override
	public Integer updateStockNumById(List<WarehouseGoodsStatus> StockGoodsList) {
		Integer num = 0;
		if(CollectionUtils.isNotEmpty(StockGoodsList)) {
			for (WarehouseGoodsStatus warehouseGoodsStatus : StockGoodsList) {
			num += warehouseGoodsStatusMapper.updateStockNumById(warehouseGoodsStatus);
			}
		}
		return num;
	}

	private boolean isWarehouseGoodsStatusSame(WarehouseGoodsStatus oldInfo, WarehouseGoodsStatus newInfo) {
		if(!oldInfo.getWarehouseId().equals(newInfo.getWarehouseId())){
			return false;
		}else if(!oldInfo.getStorageRoomId().equals(newInfo.getStorageRoomId())){
			return false;
		}else if(!oldInfo.getStorageAreaId().equals(newInfo.getStorageAreaId())){
			return false;
		}else if(!oldInfo.getStorageLocationId().equals(newInfo.getStorageLocationId())){
			return false;
		}else if(!oldInfo.getStorageRackId().equals(newInfo.getStorageRackId())){
			return false;
		}
		return true;
	}
}
