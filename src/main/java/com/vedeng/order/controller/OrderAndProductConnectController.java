
package com.vedeng.order.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vedeng.common.controller.BaseController;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.order.model.vo.OrderConnectVo;
import com.vedeng.order.service.OrderAndProductConnectService;
import com.vedeng.system.service.UserService;

/**
 * <b>Description: 订单与订单与商品关联controller</b><br> 
 * <b>Author: Franlin</b> 
 * @fileName OrderAndProductConnectController.java
 * <br><b>Date: 2018年8月23日 下午2:30:18 </b> 
 *
 */
@Controller
@RequestMapping("/order/productConnect")
public class OrderAndProductConnectController extends BaseController
{
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	@Autowired
	@Qualifier("orderAndProductConnectService")
	private OrderAndProductConnectService orderAndProductConnectService;
	
	/**
	 * 
	 * <b>Description: 备货单下商品关联销售单</b><br> 
	 * @param request
	 * @param reqVo
	 * @return
	 * <b>Author: Franlin</b>  
	 * <br><b>Date: 2018年8月24日 下午4:19:46 </b>
	 */
	@ResponseBody
	@RequestMapping(value="/queryOrderAndProductConnectOrderNo")
	public ResultInfo<List<OrderConnectVo>> queryOrderAndProductConnectOrderNo(HttpServletRequest request, OrderConnectVo reqVo)
	{
		
		ResultInfo<List<OrderConnectVo>> result = null;
		
		try
		{	
			result = orderAndProductConnectService.queryOrderAndProductConnectOrderNo(reqVo);			
		}
		catch (Exception e)
		{
			logger.error("queryOrderAndProductConnectOrderNo:", e);
			
			return new ResultInfo<List<OrderConnectVo>>();
		}	
		
		return result;
	}
	
}

