package com.vedeng.datacenter.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.ObjectUtils;
import com.vedeng.datacenter.service.MDTraderService;
import com.vedeng.homepage.model.vo.EchartsDataVo;
import com.vedeng.system.service.OrgService;
import com.vedeng.system.service.UserService;

/**
 * <b>Description:</b><br> 数据中心-物流相关
 * @author leo.yang
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.datacenter.controller
 * <br><b>ClassName:</b> MDLogisticsController
 * <br><b>Date:</b> 2017年12月25日 下午6:38:54
 */
@Controller
@RequestMapping("/datacenter/logistics")
public class MDLogisticsController extends BaseController{

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
	 * <b>Description:</b><br> 物流数据统计
	 * @param request
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年12月25日 下午6:38:29
	 */
	@ResponseBody
	@RequestMapping(value="logisticsDataStatistics")
	public ModelAndView logisticsDataStatistics(HttpServletRequest request,HttpSession session){
		ModelAndView mv = new ModelAndView();
		mv.addObject("year", DateUtil.getNowYear());
		mv.setViewName("datacenter/logistics/logistics_data_statistics");
		return mv;
	}
	

	
	/**
	 * <b>Description:</b><br> 物流的首页数据
	 * @param request
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年11月21日 下午1:31:11
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ResponseBody
	@RequestMapping(value="/getLogisticsHomePageMsg")
	public ResultInfo<?> getLogisticsHomePageMsg(HttpServletRequest request){
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		EchartsDataVo echartsDataVo = new EchartsDataVo();
		echartsDataVo.setCompanyId(user.getCompanyId());
		String year = (String) request.getAttribute("year");
		if(ObjectUtils.notEmpty(year)){
			echartsDataVo.setYear(Integer.valueOf(year));
		}else{
			echartsDataVo.setYear(DateUtil.getNowYear());
		}
		EchartsDataVo edv = mDTraderService.getLogisticsEchartsDataVo(echartsDataVo);
		ResultInfo res = new ResultInfo(0, "查询成功", edv);
		return res;
	}
	
	
	@ResponseBody
	@RequestMapping(value="warehouseDataStatistics")
	public ModelAndView warehouseDataStatistics(HttpServletRequest request,HttpSession session){
		ModelAndView mv = new ModelAndView();
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		Map<String, Object> map = new HashMap<>();
		EchartsDataVo echartsDataVo = new EchartsDataVo();
		try {
			//获取当前销售用户下级职位用户
			List<Integer> positionType = new ArrayList<>();
			positionType.add(SysOptionConstant.ID_310);
			List<User> userList = userService.getMyUserList(user, positionType, false);
			mv.addObject("userList",userList);//销售人员
			
			String userId = request.getParameter("userId");
			if(StringUtils.isNoneBlank(userId)){
				//获取销售基本信息
				User userInfo = userService.getUserById(Integer.parseInt(userId));
				mv.addObject("userInfo",userInfo);
				mv.addObject("userId", userId);
			}
		} catch (Exception e) {
			logger.error("warehouseDataStatistics:", e);
		}
		
		mv.setViewName("datacenter/logistics/warehouse_data_statistics");
		return mv;
	}
	
}
