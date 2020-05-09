package com.vedeng.aftersales.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.common.constants.Contant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.aftersales.service.GoodsWarrantyService;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.order.model.vo.SaleorderGoodsWarrantyVo;

@Service("goodsWarrantyService")
public class GoodsWarrantyServiceImpl extends BaseServiceimpl implements GoodsWarrantyService {
	public static Logger logger = LoggerFactory.getLogger(GoodsWarrantyServiceImpl.class);

	@Override
	public Map<String, Object> querylistPage(SaleorderGoodsWarrantyVo saleorderGoodsWarrantyVo, Page page) {
		String url = httpUrl + "goodswarranty/getgoodswarrantylistpage.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<SaleorderGoodsWarrantyVo>>> TypeRef2 = new TypeReference<ResultInfo<List<SaleorderGoodsWarrantyVo>>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, saleorderGoodsWarrantyVo, clientId, clientKey, TypeRef2,
					page);
			List<SaleorderGoodsWarrantyVo> optionList = (List<SaleorderGoodsWarrantyVo>) result.getData();
			page = result.getPage();
			
			Map<String, Object> map = new HashMap<>();
			map.put("list", optionList);
			map.put("page", page);
			return map;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}

}
