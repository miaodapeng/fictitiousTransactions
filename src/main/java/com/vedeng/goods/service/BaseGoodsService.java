package com.vedeng.goods.service;

import com.vedeng.goods.model.CoreSkuGenerate;
import com.vedeng.goods.model.CoreSkuGenerateExample;
import com.vedeng.goods.model.CoreSpuGenerate;
import com.vedeng.goods.model.dto.CoreSkuBaseDTO;
import com.vedeng.goods.model.dto.CoreSpuBaseDTO;
import java.util.List;

/**
 * 缓存的高速的产品服务
 * 
 * @author vedeng
 *
 */

public interface BaseGoodsService {

	CoreSpuBaseDTO selectSpuBaseById(Integer spuId);

	CoreSkuBaseDTO selectSkuBaseById(Integer skuId);

	List<CoreSkuBaseDTO> selectSkuBaseByIds(String[] skuIds);

	void flushSpu();
	void flushSku();

	void mergeSpu(CoreSpuGenerate spu);
	void mergeSku(CoreSkuGenerate sku);

	void mergeSkuByIds(CoreSkuGenerate generate, List<Integer> list);

	/**
	 * <b>Description:</b>根据spuId更新推送状态<br>
	 * @param
	 * @return
	 * @Note
	 * <b>Author:calvin</b>
	 * <br><b>Date:</b> ${date} ${time}
	 */
	int updatePushStatusBySpuId(Integer spuId,int status);

	/**
	 * <b>Description:</b>根据skuId获取推送状态<br>
	 * @param
	 * @return
	 * @Note
	 * <b>Author:calvin</b>
	 * <br><b>Date:</b> ${date} ${time}
	 */
	int getPushStatusBySkuId(Integer skuId);

	/**
	 * <b>Description:</b>根据skuId更新推送标识<br>
	 * @param
	 * @return
	 * @Note
	 * <b>Author:calvin</b>
	 * <br><b>Date:</b> ${date} ${time}
	 */
	int updatePushStatusBySkuId(Integer skuId,int status);
}
