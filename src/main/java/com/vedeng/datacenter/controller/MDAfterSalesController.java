package com.vedeng.datacenter.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.datacenter.service.MDTraderService;
import com.vedeng.homepage.model.vo.EchartsDataVo;
import com.vedeng.system.service.OrgService;
import com.vedeng.system.service.UserService;

/**
 * <b>Description:</b><br> 数据中心-售后
 * @author leo.yang
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.datacenter.controller
 * <br><b>ClassName:</b> MDAfterSalesController
 * <br><b>Date:</b> 2017年12月25日 下午6:43:07
 */
@Controller
@RequestMapping("/datacenter/aftersales")
public class MDAfterSalesController extends BaseController{

	@Autowired
	@Qualifier("mDTraderService")
	private MDTraderService mDTraderService;
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	@Autowired
	@Qualifier("orgService")
	private OrgService orgService;
	
	/**
	 * <b>Description:</b><br> 售后数据
	 * @param request
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年12月25日 下午6:43:21
	 */
	@ResponseBody
	@RequestMapping(value="aftersalesStatistics")
	public ModelAndView aftersalesStatistics(HttpServletRequest request,HttpSession session){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("datacenter/aftersales/aftersales_statistics");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 售后数据中心数据
	 * @param request
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年11月21日 下午1:31:11
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ResponseBody
	@RequestMapping(value="/getAftersalesDataCenter")
	public ResultInfo<?> getAftersalesDataCenter(HttpServletRequest request){
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		EchartsDataVo echartsDataVo = new EchartsDataVo();
		echartsDataVo.setCompanyId(user.getCompanyId());
		EchartsDataVo edv = mDTraderService.getAftersalesDataCenter(echartsDataVo);
		ResultInfo res = new ResultInfo(0, "查询成功", edv);
		return res;
	}
	
	
}
