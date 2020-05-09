/**   
 * Copyright © 2019 公司名. All rights reserved.
 * 
 * @Title: GoodsDistributeService.java 
 * @Prject: erp.vedeng.com
 * @Package: com.vedeng.goods.service 
 * @Description: TODO
 * @author: vedeng   
 * @date: 2019年6月4日 下午1:39:14 
 * @version: V1.0   
 */
package com.vedeng.goods.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.vedeng.common.page.Page;
import com.vedeng.goods.model.Goods;
import com.vedeng.goods.model.GoodsDistribute;

/** 
 * @ClassName: GoodsDistributeService 
 * @Description: TODO
 * @author: vedeng
 * @date: 2019年6月4日 下午1:39:14  
 */
public interface GoodsDistributeService {
	
	/**
	 * 
	 * @Title: getGoodsDistributeListPage 
	 * @Description: 商品归属列表
	 * @param request
	 * @param paramMap
	 * @param page
	 * @param session
	 * @return
	 * @return: Map<String,Object>
	 */
	Map<String, Object> getGoodsDistributeListPage( 
			GoodsDistribute goodsDistribute,Page page);
	
	/**
	 * 
	 * @Title: addDistribution 
	 * @Description: 分配经理人和助理
	 * @param goodsDistribute
	 * @return: void
	 */
	void addDistribution(GoodsDistribute goodsDistribute);
	
	void editDistribution(GoodsDistribute goodsDistribute);
	
	List<GoodsDistribute> isHaveDistribution(Map<String,Object> queryMap);


}
