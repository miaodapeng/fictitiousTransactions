package com.vedeng.aftersales.service;

import java.util.Map;

import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;
import com.vedeng.order.model.vo.SaleorderGoodsWarrantyVo;

public interface GoodsWarrantyService extends BaseService {

	/**
	 * <b>Description:</b><br> 录保卡列表
	 * @param saleorderGoodsWarrantyVo
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年10月24日 上午10:08:25
	 */
	Map<String, Object> querylistPage(SaleorderGoodsWarrantyVo saleorderGoodsWarrantyVo, Page page);

}
