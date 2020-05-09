
package com.vedeng.saleperformance.controller;


import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.vedeng.authorization.model.User;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.DateUtil;
import com.vedeng.order.model.vo.OrderDetailsVo;
import com.vedeng.saleperformance.model.perf.MonthSceneModel;
import com.vedeng.saleperformance.model.vo.SaleperformanceProcess;
import com.vedeng.saleperformance.service.SalesPerformanceService;
import com.vedeng.system.service.UserService;


/**
 * <b>Description: 销售之五行剑法--controller</b><br> 
 * <b>Author: Franlin</b> 
 * @fileName FiveElementsOfSaleController.java
 * <br><b>Date: 2018年6月4日 下午6:34:20 </b> 
 *
 */
@Controller
@RequestMapping("/sales/fiveSales")
public class FiveElementsOfSaleController extends BaseController
{
	@Autowired
	@Qualifier("salesPerformanceService")
	private SalesPerformanceService salesPerformanceService;
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	
	/**
	 * 
	 * <b>Description: 跳转销售五行剑法页面--默认业绩页面</b><br> 
	 * @param request
	 * @param user
	 * @return
	 * <b>Author: Franlin</b>  
	 * <br><b>Date: 2018年6月12日 上午9:06:42 </b>
	 */
	@RequestMapping(value="/detailsPage")
	public ModelAndView fiveSalesDetailsPage(HttpServletRequest request, MonthSceneModel reqModel, @RequestParam(required = false, defaultValue = "0") Integer userFlag)
	{
		ModelAndView view = new ModelAndView();
		if(null != reqModel)
		{
			
			view.addObject("five_userId", reqModel.getUserId());
			User user = userService.getUserById(reqModel.getUserId());
			if(null != user)
			{				
				view.addObject("userName", user.getUsername());
			}
			view.addObject("companyId", reqModel.getCompanyId());
		}
		Integer sortType = reqModel.getSortType();
		
		// 五行 业绩
		if (null == sortType || sortType == 1)
		{
			view.setViewName("/saleperformance/sale/performance_sales_page");
		}
		// 五行剑法--核心商品
		else if (sortType == 7)
		{
			view.setViewName("/saleperformance/sale/brans_sales_page");
		}
		// 五行剑法--客戶
		else if (sortType == 3)
		{
			view.setViewName("/saleperformance/sale/customer_sales_page");
		}
		// 五行剑法--通话时长
		else if (sortType == 4)
		{
			view.setViewName("/saleperformance/sale/call_sales_page");
		}
		// 五行剑法--转化率
		else if (sortType == 5)
		{
			view.setViewName("/saleperformance/sale/conversion_rate_sales_page");
		}
		// 五行剑法--总成绩
		else if (sortType == 6)
		{
			view.setViewName("/saleperformance/sale/total_five_sales");
		} else if (sortType == 8) {
			view.setViewName("/saleperformance/sale/bd_sales_page");
		}
		// 默认 业绩页面
		view.addObject("sortType", sortType);
		view.addObject("userFlag", userFlag);
		
		return view;
	}
	
	/**
	 * 
	 * <b>Description: 五行剑法[本月概况]</b><br> 
	 * @param request
	 * @return
	 * <b>Author: Franlin</b>  
	 * <br><b>Date: 2018年6月5日 上午10:48:09 </b>
	 */
	@ResponseBody
	@RequestMapping(value="/monthlySceneDetails")
	public ResultInfo<MonthSceneModel> monthlySceneDetails(HttpServletRequest request, MonthSceneModel msModel)
	{
//		User user = getSessionUser(request);
		
		ResultInfo<MonthSceneModel> result = null;
		
		try
		{			
			result = salesPerformanceService.getSalesMonthSceneModel(msModel);
		}
		catch (Exception e)
		{
			logger.error("monthlySceneDetails:", e);
			
			return new ResultInfo<MonthSceneModel>();
		}	
		
		return result;
	}
	
	/**
	 * 
	 * <b>Description: 五行剑法--明细/</b><br> 
	 * @param request
	 * @return
	 * <b>Author: Franlin</b>  
	 * <br><b>Date: 2018年6月5日 上午10:48:09 </b>
	 */
	@ResponseBody
	@RequestMapping(value="/sceneDataDetails")
	public ResultInfo<MonthSceneModel> sceneDataDetails(HttpServletRequest request, MonthSceneModel msModel)
	{
//		User user = getSessionUser(request);
		
		ResultInfo<MonthSceneModel> result = null;
		
		try
		{			
			result = salesPerformanceService.getSceneDataDetails(msModel);
		}
		catch (Exception e)
		{
			logger.error("sceneDataDetails:", e);
			
			return new ResultInfo<MonthSceneModel>();
		}	
		
		return result;
	}
	
	/**
	 * 
	 * <b>Description: 过往历史明细</b><br> 
	 * @param request
	 * @param msModel
	 * @return
	 * <b>Author: Franlin</b>  
	 * <br><b>Date: 2018年6月6日 下午6:42:52 </b>
	 */
	@ResponseBody
	@RequestMapping(value="/historyDataDetails")
	public ResultInfo<MonthSceneModel> historyDataDetails(HttpServletRequest request, MonthSceneModel msModel)
	{
//		User user = getSessionUser(request);
		
		ResultInfo<MonthSceneModel> result = null;
		
		try
		{			
			result = salesPerformanceService.historyDataDetails(msModel);
		}
		catch (Exception e)
		{
			logger.error("historyDataDetails:", e);
			
			return new ResultInfo<MonthSceneModel>();
		}	
		
		return result;
	}
	
	
	/**
	 * 
	 * <b>Description: 查询部门五行得分和排名</b><br> 
	 * @param request
	 * @param req
	 * @return
	 * <b>Author: Franlin</b>  
	 * <br><b>Date: 2018年7月23日 上午11:11:50 </b>
	 */
	@ResponseBody
	@RequestMapping(value="/queryDeptSoreAndSort")
	public ResultInfo<?> queryDeptSoreAndSort(HttpServletRequest request, MonthSceneModel req)
	{
		
		ResultInfo<?> result = null;
		
		try
		{			
			result = salesPerformanceService.queryDeptSoreAndSort(req);
		}
		catch (Exception e)
		{
			logger.error("queryDeptSoreAndSort:", e);
		}	
		
		return result;
	}
	
	/**
	 * 
	 * <b>Description: 查询订单详情</b><br> 
	 * @param request
	 * @param req
	 * @return
	 * <b>Author: Franlin</b>  
	 * <br><b>Date: 2018年7月23日 上午11:11:50 </b>
	 */
	@ResponseBody
	@RequestMapping(value="/queryOrderDetails")
	public ResultInfo<?> queryOrderDetails(HttpServletRequest request, OrderDetailsVo req, @RequestParam(required = false, defaultValue = "1") Integer pageNo, @RequestParam(required = false, defaultValue = "10") Integer pageSize)
	{
		Page page = getPageTag(request, pageNo, pageSize);
		ResultInfo<?> result = null;
		
		if(null != req && null != req.getValidStatus() && 1 == req.getValidStatus())
		{
		Date now = new Date();
		Date vd = DateUtil.subDateByDays(now, -15);
		req.setValidTime(vd.getTime());
	}
		
		try
		{			
			result = salesPerformanceService.queryOrderDetails(req, page);
		}
		catch (Exception e)
		{
			logger.error("queryOrderDetails:", e);
		}	
		
		return result;
	}
	
	/**
	 * 
	 * <b>Description: 五行总览页面</b><br> 
	 * @param request
	 * @return
	 * <b>Author: Franlin</b>  
	 * <br><b>Date: 2018年8月13日 上午10:00:21 </b>
	 */
	@RequestMapping(value="/fiveTotalViewPage")
	public ModelAndView fiveTotalViewPage(HttpServletRequest request)
	{
		ModelAndView view = new ModelAndView();
		view.setViewName("/saleperformance/sale/five_total_view_page");
		
		return view;
	}
	
	/**
	 * 
	 * <b>Description: 查询所有五行数据</b><br> 
	 * @param request
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * <b>Author: Franlin</b>  
	 * <br><b>Date: 2018年8月13日 上午9:58:04 </b>
	 */
	@ResponseBody
	@RequestMapping(value="/queryAllSalesData")
	public ResultInfo<?> queryAllSalesData(HttpServletRequest request, @RequestParam(required = false, defaultValue = "1") Integer pageNo, @RequestParam(required = false, defaultValue = "1000") Integer pageSize)
	{
		Page page = getPageTag(request, pageNo, pageSize);
		ResultInfo<List<SaleperformanceProcess>> result = new ResultInfo<List<SaleperformanceProcess>>(0, "查询成功");
		
		Date now = new Date();
		
		List<SaleperformanceProcess> resultList = null;
		
		try
		{			
			resultList = salesPerformanceService.queryAllSalesData(now.getTime(), page);
		}
		catch (Exception e)
		{
			logger.error("queryAllSalesData:", e);
		}
		
		result.setData(resultList);
		
		return result;
	}
	
}

