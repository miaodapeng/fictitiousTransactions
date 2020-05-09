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
 * <b>Description:</b><br> 数据中心-客户相关数据统计
 * @author leo.yang
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.datacenter.controller
 * <br><b>ClassName:</b> MDTraderController
 * <br><b>Date:</b> 2017年12月4日 下午3:39:50
 */
@Controller
@RequestMapping("/datacenter/trader")
public class MDTraderController extends BaseController{

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
	 * <b>Description:</b><br> 获取客户数据统计
	 * @param request
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年12月4日 下午3:41:14
	 */
	@ResponseBody
	@RequestMapping(value="customer")
	public ModelAndView getCustomerStatistics(HttpServletRequest request,HttpSession session){
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
			
			
			map.put("companyId", user.getCompanyId());
			echartsDataVo.setCompanyId(user.getCompanyId());
			
			//获取客户数概况
			Map<String, String> customerNumInfo = mDTraderService.getCustomerNumInfo(map);
			//获取总客户数
			String totalCustomerNum = customerNumInfo.get("totalCustomerNum");
			mv.addObject("totalCustomerNum", totalCustomerNum);
			//获取成交客户数
			String totalTraderCustomerNum = customerNumInfo.get("totalTraderCustomerNum");
			mv.addObject("totalTraderCustomerNum", totalTraderCustomerNum);
			//获取多次成交客户数
			String totalManyTraderCustomerNum = customerNumInfo.get("totalManyTraderCustomerNum");
			mv.addObject("totalManyTraderCustomerNum", totalManyTraderCustomerNum);
			
			//客户性质占比
			Map<String, String> customerNaturePro = mDTraderService.getCustomerNaturePro(map);
			List<SysOptionDefinition> customerNatures = getSysOptionDefinitionList(464);
			List<String> cnpLegendList = new ArrayList<>();
			List<EchartsData> echartsDataList = new ArrayList<>();
			for(SysOptionDefinition cn:customerNatures) {
				//['直接访问','邮件营销']
				cnpLegendList.add("'"+cn.getTitle()+"'");
				/*[
			        {value:335, name:'直接访问'},
			        {value:310, name:'邮件营销'}
			    ]*/
				EchartsData echartsData = new EchartsData();
				echartsData.setValue(customerNaturePro.get(cn.getSysOptionDefinitionId().toString()));
				echartsData.setName(cn.getTitle());
				echartsDataList.add(echartsData);
			}
			JSONArray fromObject = JSONArray.fromObject(echartsDataList);
			mv.addObject("customerNatureLegendList", cnpLegendList);
			mv.addObject("customerNatureData", fromObject);
			
			//客户等级占比
			Map<String, String> customerLevelPro = mDTraderService.getCustomerLevelPro(map);
			List<SysOptionDefinition> customerLevels = getSysOptionDefinitionList(11);
			List<String> clpLegendList = new ArrayList<>();
			List<EchartsData> echartsDataClpList = new ArrayList<>();
			for(SysOptionDefinition cl:customerLevels) {
				//['直接访问','邮件营销']
				clpLegendList.add("'"+cl.getTitle()+"'");
				/*[
			        {value:335, name:'直接访问'},
			        {value:310, name:'邮件营销'}
			    ]*/
				EchartsData echartsData = new EchartsData();
				echartsData.setValue(customerLevelPro.get(cl.getSysOptionDefinitionId().toString()));
				echartsData.setName(cl.getTitle());
				echartsDataClpList.add(echartsData);
			}
			JSONArray clpFromObject = JSONArray.fromObject(echartsDataClpList);
			mv.addObject("customerLevelLegendList", clpLegendList);
			mv.addObject("customerLevelData", clpFromObject);
			
			List<String> cjkhLegendList = new ArrayList<>();
			List<EchartsData> echartsDataCjkhList = new ArrayList<>();
			cjkhLegendList.add("'成交'");
			cjkhLegendList.add("'未成交'");
			EchartsData echartsData = new EchartsData();
			echartsData.setValue(totalTraderCustomerNum);
			echartsData.setName("成交");
			echartsDataCjkhList.add(echartsData);
			EchartsData echartsData2 = new EchartsData();
			echartsData2.setValue((Integer.valueOf(totalCustomerNum) - Integer.valueOf(totalTraderCustomerNum)) + "");
			echartsData2.setName("未成交");
			echartsDataCjkhList.add(echartsData2);
			JSONArray cjkhFromObject = JSONArray.fromObject(echartsDataCjkhList);
			mv.addObject("cjkhLegendList", cjkhLegendList);
			mv.addObject("echartsDataCjkhList", cjkhFromObject);
			
			//获取营销中心一级部门
			Organization organization = new Organization();
			organization.setParentId(18);
			organization.setType(310);
			List<OrganizationVo> oneLevelOrgList = orgService.getOrganizationVoList(organization);
			
			Map<Integer, List<Integer>> orgMap = new HashMap<>();
			for (int i = 0; i < oneLevelOrgList.size(); i++) {
				List<Integer> orgIdList = orgService.getOrgIds(oneLevelOrgList.get(i).getOrgId(), user.getCompanyId());
				orgMap.put(oneLevelOrgList.get(i).getOrgId(), orgIdList);
			}
			echartsDataVo.setOrgMap(orgMap);
			
			//客户分部门占比,成交客户分部门占比,多次成交客户分部门占比
			EchartsDataVo orgTraderInfo = mDTraderService.getOrgTraderInfo(echartsDataVo);
			
			if (orgTraderInfo != null) {
				Map<Integer, Integer> orgCustomerMap = orgTraderInfo.getOrgCustomerMap();
				
				Map<Integer, Integer> orgTraderCustomerMap = orgTraderInfo.getOrgTraderCustomerMap();
				
				Map<Integer, Integer> orgManyTraderCustomerMap = orgTraderInfo.getOrgManyTraderCustomerMap();
				
				List<String> orgCustomerLegendList = new ArrayList<>();
				List<String> orgTraderCustomerLegendList = new ArrayList<>();
				List<String> orgManyTraderCustomerLegendList = new ArrayList<>();
				List<EchartsData> orgCustomeEchartsDatarList = new ArrayList<>();
				List<EchartsData> orgTraderCustomeEchartsDatarList = new ArrayList<>();
				List<EchartsData> orgManyTraderCustomeEchartsDatarList = new ArrayList<>();
				for (int i = 0; i < oneLevelOrgList.size(); i++) {
					//['直接访问','邮件营销']
					orgCustomerLegendList.add("'"+oneLevelOrgList.get(i).getOrgName()+"'");
					orgTraderCustomerLegendList.add("'"+oneLevelOrgList.get(i).getOrgName()+"'");
					orgManyTraderCustomerLegendList.add("'"+oneLevelOrgList.get(i).getOrgName()+"'");
					
					/*[
				        {value:335, name:'直接访问'},
				        {value:310, name:'邮件营销'}
				    ]*/
					EchartsData orgCustomerEchartsData = new EchartsData();
					orgCustomerEchartsData.setValue(orgCustomerMap.get(oneLevelOrgList.get(i).getOrgId()).toString());
					orgCustomerEchartsData.setName(oneLevelOrgList.get(i).getOrgName());
					orgCustomeEchartsDatarList.add(orgCustomerEchartsData);
					
					EchartsData orgCustomerTraderEchartsData = new EchartsData();
					orgCustomerTraderEchartsData.setValue(orgTraderCustomerMap.get(oneLevelOrgList.get(i).getOrgId()).toString());
					orgCustomerTraderEchartsData.setName(oneLevelOrgList.get(i).getOrgName());
					orgTraderCustomeEchartsDatarList.add(orgCustomerTraderEchartsData);
					
					EchartsData orgManyTraderCustomerEchartsData = new EchartsData();
					orgManyTraderCustomerEchartsData.setValue(orgManyTraderCustomerMap.get(oneLevelOrgList.get(i).getOrgId()).toString());
					orgManyTraderCustomerEchartsData.setName(oneLevelOrgList.get(i).getOrgName());
					orgManyTraderCustomeEchartsDatarList.add(orgManyTraderCustomerEchartsData);
				}
				JSONArray orgCustomerFromObject = JSONArray.fromObject(orgCustomeEchartsDatarList);
				mv.addObject("orgCustomerLegendList", orgCustomerLegendList);
				mv.addObject("orgCustomerFromObject", orgCustomerFromObject);
				
				JSONArray orgTraderCustomerFromObject = JSONArray.fromObject(orgTraderCustomeEchartsDatarList);
				mv.addObject("orgTraderCustomerLegendList", orgTraderCustomerLegendList);
				mv.addObject("orgTraderCustomerFromObject", orgTraderCustomerFromObject);
				
				JSONArray orgManyTraderCustomerFromObject = JSONArray.fromObject(orgManyTraderCustomeEchartsDatarList);
				mv.addObject("orgManyTraderCustomerLegendList", orgManyTraderCustomerLegendList);
				mv.addObject("orgManyTraderCustomerFromObject", orgManyTraderCustomerFromObject);
			}
			
			
		} catch (Exception e) {
			logger.error("trader customer", e);
		}
		
		
		
		//成交客户占比
		
		//客户分部门占比
		
		//成交客户分部门占比
		
		//多次成交客户分部门占比
		
		mv.setViewName("datacenter/trader/index_customer");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 成交客户统计
	 * @param request
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年12月25日 下午6:46:43
	 */
	@ResponseBody
	@RequestMapping(value="traderCustomer")
	public ModelAndView traderCustomer(HttpServletRequest request,HttpSession session){
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
			logger.error("traderCustomer", e);
		}
		
		mv.setViewName("datacenter/trader/trader_customer");
		return mv;
	}
	
}
