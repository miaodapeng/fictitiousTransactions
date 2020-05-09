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
import com.vedeng.logistics.dao.WarehousePickingMapper;
import com.vedeng.logistics.model.LendOut;
import com.vedeng.logistics.model.StorageRoom;
import com.vedeng.logistics.model.Warehouse;
import com.vedeng.logistics.model.WarehousePicking;
import com.vedeng.logistics.service.WarehousePickService;
import com.vedeng.logistics.service.WarehousesService;
import com.vedeng.order.model.Quoteorder;
import com.vedeng.order.model.Saleorder;
import com.vedeng.system.service.RegionService;

@Service("warehousePickService")
public class WarehousePickServiceImpl extends BaseServiceimpl implements WarehousePickService {
	public static Logger logger = LoggerFactory.getLogger(WarehousePickServiceImpl.class);

	@Autowired
	private WarehousePickingMapper warehousePickingMapper;
	
	@Override
	public List<WarehousePicking> getPickDetil(Saleorder saleorder) {
		List<WarehousePicking> list = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<WarehousePicking>>> TypeRef = new TypeReference<ResultInfo<List<WarehousePicking>>>() {};
		String url=httpUrl + "warehouseout/getwarehousepicklist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url,saleorder,clientId,clientKey, TypeRef);
			list = (List<WarehousePicking>) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return list;
	}

	@Override
	public ResultInfo<?> savePickRecord(Saleorder sd,HttpSession session) {
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		Long time = DateUtil.sysTimeMillis();
		sd.setCompanyId(user.getCompanyId());
		sd.setCreatorjh(user.getUserId());
		sd.setAddTimejh(time);
		sd.setModTimejh(time);
		sd.setAddTimejh(time);
		sd.setUpdaterjh(user.getUserId());
		ResultInfo<?> result = null;
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
			String url=httpUrl + "warehouseout/savepickrecord.htm";
			result = (ResultInfo<?>) HttpClientUtils.post(url, sd,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

	@Override
	public ResultInfo<?> editIsEnablePick(List<WarehousePicking> wpList) {
		String url = httpUrl + "warehouseout/editisenablewp.htm";
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		try {
		    ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, wpList, clientId, clientKey, TypeRef);
		    // 接口返回条码生成的记录
		    if (result.getCode() == 0) {
			return new ResultInfo(0, "修改成功");
		    }else{
			return new ResultInfo();
		    }
		} catch (IOException e) {
		    logger.error(Contant.ERROR_MSG, e);
		    return new ResultInfo();
		}
	}

	@Override
	public List<WarehousePicking> printPickOrder(WarehousePicking wp) {
		List<WarehousePicking> list = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<WarehousePicking>>> TypeRef = new TypeReference<ResultInfo<List<WarehousePicking>>>() {};
		String url=httpUrl + "warehouseout/getwarehousepicklistbyid.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url,wp,clientId,clientKey, TypeRef);
			list = (List<WarehousePicking>) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return list;
	}

	@Override
	public List<WarehousePicking> getPickLendOutDetil(Saleorder saleorder) {
		return warehousePickingMapper.getWarehouseLendOutPickList(saleorder);
	}

	@Override
	public Integer getLendOutPickCnt(LendOut lendout) {
		return warehousePickingMapper.getLendOutPickCnt(lendout.getLendOutId(),lendout.getGoodsId(),3);
	}
	
	
}
