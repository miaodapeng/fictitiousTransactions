
package com.vedeng.order.service;

import java.util.List;

import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.service.BaseService;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.model.SaleorderCoupon;
import com.vedeng.order.model.vo.OrderConnectVo;

/**
 * <b>Description:</b><br> 
 * <b>Author: Franlin</b> 
 * @fileName OrderAndProductConnectService.java
 * <br><b>Date: 2018年8月23日 下午3:29:54 </b> 
 *
 */
public interface OrderAndProductConnectService extends BaseService
{
	/**
	 * 
	 * <b>Description: 根据备货订单ID或备货单号查询该订单下商品的关联销售订单</b><br> 
	 * @param reqVo
	 * @return
	 * <b>Author: Franlin</b>  
	 * <br><b>Date: 2018年8月24日 下午4:49:38 </b>
	 */
	public ResultInfo<List<OrderConnectVo>> queryOrderAndProductConnectOrderNo(OrderConnectVo reqVo);


	int saveHCOrder(Saleorder order, List<SaleorderCoupon> couponList) throws Exception;
}

