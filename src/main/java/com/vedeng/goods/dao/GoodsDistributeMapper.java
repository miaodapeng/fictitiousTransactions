/**   
 * Copyright © 2019 公司名. All rights reserved.
 * 
 * @Title: GoodsDistributeMapper.java 
 * @Prject: erp.vedeng.com
 * @Package: com.vedeng.goods.dao 
 * @Description: TODO
 * @author: vedeng   
 * @date: 2019年6月4日 下午1:43:18 
 * @version: V1.0   
 */
package com.vedeng.goods.dao;

import java.util.List;
import java.util.Map;

import com.vedeng.firstengage.model.FirstEngage;
import com.vedeng.goods.model.GoodsDistribute;

 
 /**
  * 
  * @ClassName: GoodsDistributeMapper 
  * @Description: TODO
  * @author: vedeng
  * @date: 2019年6月4日 下午1:58:15
  */
public interface GoodsDistributeMapper {

	/**
	 * 
	 * @Title: getFirstEngageInfoList 
	 * @Description: 商品归属列表
	 * @param paramMap
	 * @return
	 * @return: List<FirstEngage>
	 */
	List<GoodsDistribute> getGoodsDistributeListPage(Map<String, Object> paramMap);
    /**
     * 
     * @Title: addDistribution 
     * @Description: 分配商品归属
     * @param paramMap
     * @return: void
     */
    void addDistribution(Map<String, Object> paramMap);
    /**
     * 
     * @Title: editDistribution 
     * @Description: 归属人经理更换
     * @param paramMap
     * @return: void
     */
    void editDistributionM(Map<String, Object> paramMap);
    
    /**
     * 
     * @Title: editDistribution 
     * @Description: 归属人助理更换
     * @param paramMap
     * @return: void
     */
    void editDistributionA(Map<String, Object> paramMap);
    
    /**
     * 
     * @Title: isHaveDistribution 
     * @Description: 是否包含已经分配的商品
     * @return
     * @return: List<GoodsDistribute>
     */
    List<GoodsDistribute> isHaveDistribution(Map<String, Object> paramMap);
 
}
