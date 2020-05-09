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

import com.vedeng.authorization.model.Organization;
import com.vedeng.authorization.model.User;
import com.vedeng.authorization.model.vo.OrganizationVo;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.controller.BaseController;
import com.vedeng.datacenter.model.EchartsData;
import com.vedeng.datacenter.service.MDTraderService;
import com.vedeng.homepage.model.vo.EchartsDataVo;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.system.service.OrgService;
import com.vedeng.system.service.UserService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * <b>Description:</b><br> 数据中心-产品
 * @author leo.yang
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.datacenter.controller
 * <br><b>ClassName:</b> MDGoodsController
 * <br><b>Date:</b> 2017年12月25日 下午6:45:04
 */
@Controller
@RequestMapping("/datacenter/goods")
public class MDGoodsController extends BaseController{

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
	 * <b>Description:</b><br> 产品统计
	 * @param request
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年12月25日 下午6:45:45
	 */
	@ResponseBody
	@RequestMapping(value="goodsStatistics")
	public ModelAndView goodsStatistics(HttpServletRequest request,HttpSession session){
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
			logger.error("goodsStatistics:", e);
		}
		
		mv.setViewName("datacenter/goods/goods_statistics");
		return mv;
	}
	
	
}
