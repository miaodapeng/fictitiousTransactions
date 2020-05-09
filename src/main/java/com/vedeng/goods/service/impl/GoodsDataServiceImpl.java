package com.vedeng.goods.service.impl;


import com.vedeng.goods.service.GoodsDataService;
import com.vedeng.logistics.dao.WarehouseGoodsOperateLogMapper;
import com.vedeng.logistics.dao.WarehouseGoodsStatusMapper;
import com.vedeng.logistics.model.WarehouseGoodsStatus;
import com.vedeng.order.dao.SaleorderMapper;
import com.vedeng.order.model.GoodsData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

@Service("goodsDataService")
public class GoodsDataServiceImpl implements GoodsDataService {
	@Autowired
	@Qualifier("warehouseGoodsStatusMapper")
	private WarehouseGoodsStatusMapper warehouseGoodsStatusMapper;
	
	@Autowired
	@Qualifier("warehouseGoodsOperateLogMapper")
	private WarehouseGoodsOperateLogMapper warehouseGoodsOperateLogMapper;

	@Autowired
	@Qualifier("saleorderMapper")
	private SaleorderMapper saleorderMapper;
	
	@Override
	public Integer getGoodsStockNum(Integer goodsId, Integer companyId) {
		WarehouseGoodsStatus warehouseGoodsStatus = new WarehouseGoodsStatus();
		warehouseGoodsStatus.setCompanyId(companyId);
		warehouseGoodsStatus.setGoodsId(goodsId);
		return warehouseGoodsStatusMapper.getGoodsStock(warehouseGoodsStatus);
	}
	
	@Override
	public List<GoodsData> getGoodsStockNumList(List<Integer> goodsIds, Integer companyId) {
		return warehouseGoodsStatusMapper.getGoodsStockList(goodsIds,companyId);
	}

	@Override
	public Integer getGoodsOccupyNum(Integer goodsId, Integer companyId) {
		Integer goodsOccupyNum = saleorderMapper.getGoodsOccupyNum(goodsId);
		return goodsOccupyNum;
	}

	@Override
	public List<GoodsData> getGoodsOccupyNumList(List<Integer> goodsIds, Integer companyId) {
		return saleorderMapper.getGoodsOccupyNumList(goodsIds);
	}

	@Override
	public Integer getGoodsId(String sku) {
		return warehouseGoodsStatusMapper.getGoodsId(sku);
	}

}
