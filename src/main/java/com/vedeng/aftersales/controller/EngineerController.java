package com.vedeng.aftersales.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vedeng.aftersales.model.AfterSalesInstallstion;
import com.vedeng.aftersales.model.Engineer;
import com.vedeng.aftersales.model.vo.EngineerVo;
import com.vedeng.aftersales.service.AfterSalesInstallstionService;
import com.vedeng.aftersales.service.EngineerService;
import com.vedeng.authorization.model.Region;
import com.vedeng.authorization.model.User;
import com.vedeng.common.annotation.SystemControllerLog;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.common.validator.FormToken;
import com.vedeng.system.service.RegionService;

/**
 * <b>Description:</b><br>
 * 工程师
 * 
 * @author Jerry
 * @Note <b>ProjectName:</b> erp <br>
 *       <b>PackageName:</b> com.vedeng.aftersales.controller <br>
 *       <b>ClassName:</b> EngineerController <br>
 *       <b>Date:</b> 2017年9月25日 上午9:50:32
 */
@Controller
@RequestMapping("/aftersales/engineer")
public class EngineerController extends BaseController {

	@Autowired
	@Qualifier("engineerService")
	private EngineerService engineerService;

	@Autowired
	@Qualifier("regionService")
	private RegionService regionService;// 自动注入regionService
	
	@Autowired
	@Qualifier("afterSalesInstallstionService")
	private AfterSalesInstallstionService afterSalesInstallstionService;

	/**
	 * <b>Description:</b><br>
	 * 工程师列表
	 * 
	 * @param request
	 * @param sysOptionDefinition
	 * @param pageNo
	 * @param pageSize
	 * @param session
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年9月25日 上午9:51:51
	 */
	@ResponseBody
	@RequestMapping(value = "/list")
	public ModelAndView list(HttpServletRequest request, EngineerVo engineerVo,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize, HttpSession session) {
		ModelAndView mv = new ModelAndView("aftersales/engineer/list_engineer");
		Page page = getPageTag(request, pageNo, pageSize);
		List<EngineerVo> list = null;

		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		engineerVo.setCompanyId(user.getCompanyId());
		
		Integer areaId = 0;
		if (request.getParameter("zone") != null && Integer.parseInt(request.getParameter("zone")) > 0) {
			areaId = Integer.parseInt(request.getParameter("zone"));
		} else if (request.getParameter("city") != null && Integer.parseInt(request.getParameter("city")) > 0) {
			areaId = Integer.parseInt(request.getParameter("city"));
		} else if (request.getParameter("province") != null && Integer.parseInt(request.getParameter("province")) > 0) {
			areaId = Integer.parseInt(request.getParameter("province"));
		}
		engineerVo.setAreaId(areaId);

		Map<String, Object> map = engineerService.querylistPage(engineerVo, page);

		// 地区
		List<Region> provinceList = regionService.getRegionByParentId(1);
		mv.addObject("provinceList", provinceList);

		if (null != engineerVo.getProvince() && engineerVo.getProvince() > 0) {
			List<Region> cityList = regionService.getRegionByParentId(engineerVo.getProvince());
			mv.addObject("cityList", cityList);
		}

		if (null != engineerVo.getCity() && engineerVo.getCity() > 0) {
			List<Region> zoneList = regionService.getRegionByParentId(engineerVo.getCity());
			mv.addObject("zoneList", zoneList);
		}

		list = (List<EngineerVo>) map.get("list");
		mv.addObject("engineer", engineerVo);
		mv.addObject("list", list);
		mv.addObject("page", (Page) map.get("page"));
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 查看工程师详情
	 * 
	 * @param request
	 * @param sysOptionDefinition
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年9月25日 上午10:14:25
	 */
	@ResponseBody
	@RequestMapping(value = "/view")
	public ModelAndView view(HttpServletRequest request, Engineer engineer,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize) {
		if (null == engineer || engineer.getEngineerId() <= 0) {
			return pageNotFound();
		}
		ModelAndView mv = new ModelAndView("aftersales/engineer/view_engineer");

		Page page = getPageTag(request, pageNo, pageSize);
		EngineerVo engineerVo = null;
		Map<String, Object> map = engineerService.getEngineer(engineer, page);
		engineerVo = (EngineerVo) map.get("engineer");
		mv.addObject("engineer", engineerVo);
		mv.addObject("page", (Page) map.get("page"));
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 新增工程师
	 * 
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年9月25日 上午9:53:06
	 */
	@FormToken(save=true)
	@ResponseBody
	@RequestMapping(value = "/add")
	public ModelAndView add(HttpSession session) {
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView("aftersales/engineer/add_engineer");
		// 地区
		List<Region> provinceList = regionService.getRegionByParentId(1);
		mv.addObject("provinceList", provinceList);
		mv.addObject("companyName", user.getCompanyName());
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 保存新增工程师
	 * 
	 * @param request
	 * @param engineer
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年9月25日 上午9:54:27
	 */
	@FormToken(remove=true)
	@ResponseBody
	@RequestMapping(value = "/saveadd")
	@SystemControllerLog(operationType = "add",desc = "保存新增工程师")
	public ResultInfo saveAdd(HttpServletRequest request, Engineer engineer, HttpSession session) {
		ResultInfo resultInfo = engineerService.saveAdd(engineer, request, session);
		return resultInfo;
	}

	/**
	 * <b>Description:</b><br>
	 * 编辑工程师
	 * 
	 * @param request
	 * @param engineer
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年9月25日 上午9:55:06
	 */
	@ResponseBody
	@RequestMapping(value = "/edit")
	public ModelAndView edit(HttpServletRequest request, Engineer engineer,HttpSession session) {
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		if (null == engineer || engineer.getEngineerId() <= 0) {
			return pageNotFound();
		}
		ModelAndView mv = new ModelAndView("aftersales/engineer/edit_engineer");

		Engineer engineerInfo = engineerService.getEngineerInfo(engineer);
		// 地区
		List<Region> provinceList = regionService.getRegionByParentId(1);
		if (null != engineerInfo.getAreaId() && engineerInfo.getAreaId() > 0) {

			Integer areaId = engineerInfo.getAreaId();
			List<Region> regionList = (List<Region>) regionService.getRegion(areaId, 1);

			if (!StringUtils.isEmpty(regionList)) {
				for (Region r : regionList) {
					switch (r.getRegionType()) {
					case 1:
						List<Region> cityList = regionService.getRegionByParentId(r.getRegionId());
						mv.addObject("provinceRegion", r);
						mv.addObject("cityList", cityList);
						break;
					case 2:
						List<Region> zoneList = regionService.getRegionByParentId(r.getRegionId());
						mv.addObject("cityRegion", r);
						mv.addObject("zoneList", zoneList);
						break;
					case 3:
						mv.addObject("zoneRegion", r);
						break;
					default:
						mv.addObject("countryRegion", r);
						break;
					}
				}
			}
		}
		mv.addObject("provinceList", provinceList);
		mv.addObject("engineer", engineerInfo);
		mv.addObject("companyName", user.getCompanyName());
		try {
			mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(engineerInfo)));
		} catch (Exception e) {
			logger.error("engineer error:", e);
		}
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 保存编辑工程师
	 * 
	 * @param request
	 * @param engineer
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年9月25日 上午9:55:32
	 */
	@ResponseBody
	@RequestMapping(value = "/saveedit")
	@SystemControllerLog(operationType = "edit",desc = "保存编辑工程师")
	public ResultInfo saveEdit(HttpServletRequest request, Engineer engineer, HttpSession session,String beforeParams) {
		ResultInfo resultInfo = engineerService.saveEdit(engineer, request, session);
		return resultInfo;
	}

	/**
	 * <b>Description:</b><br>
	 * 编辑财务信息
	 * 
	 * @param request
	 * @param engineer
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年9月25日 上午9:56:40
	 */
	@ResponseBody
	@RequestMapping(value = "/editfinance")
	public ModelAndView editFinance(HttpServletRequest request, Engineer engineer) {
		if (null == engineer || engineer.getEngineerId() <= 0) {
			return pageNotFound();
		}
		ModelAndView mv = new ModelAndView("aftersales/engineer/edit_finance");
		Engineer engineerInfo = engineerService.getEngineerInfo(engineer);
		mv.addObject("engineer", engineerInfo);
		try {
			mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(engineerInfo)));
		} catch (IOException e) {
			logger.error("editfinance:", e);
		}
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 保存编辑财务信息
	 * 
	 * @param request
	 * @param engineer
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年9月25日 上午9:59:36
	 */
	@ResponseBody
	@RequestMapping(value = "/saveeditfinance")
	@SystemControllerLog(operationType = "edit",desc = "保存编辑工程师财务信息")
	public ResultInfo saveEditFinance(HttpServletRequest request, Engineer engineer,HttpSession session,String beforeParams) {
		ResultInfo resultInfo = engineerService.saveEdit(engineer, request, session);
		return resultInfo;
	}

	/**
	 * <b>Description:</b><br>
	 * 编辑评分
	 * 
	 * @param request
	 * @param afterSalesInstallstion
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年9月25日 上午10:00:28
	 */
	@ResponseBody
	@RequestMapping(value = "/editscore")
	public ModelAndView editScore(HttpServletRequest request, AfterSalesInstallstion afterSalesInstallstion) {
		if(null == afterSalesInstallstion
				|| afterSalesInstallstion.getAfterSalesInstallstionId() <= 0){
			return pageNotFound();
		}
		ModelAndView mv = new ModelAndView("aftersales/engineer/edit_score");
		AfterSalesInstallstion salesInstallstion = afterSalesInstallstionService.getAfterSalesInstallstion(afterSalesInstallstion);
		mv.addObject("afterSalesInstallstion", salesInstallstion);
		try {
			mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(salesInstallstion)));
		} catch (Exception e) {
			logger.error("editscore:", e);
		}
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 保存编辑评分
	 * 
	 * @param request
	 * @param afterSalesInstallstion
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年9月25日 上午10:00:54
	 */
	@ResponseBody
	@RequestMapping(value = "/saveeditscore")
	@SystemControllerLog(operationType = "edit",desc = "保存工程师评分")
	public ResultInfo saveEditScore(HttpServletRequest request, AfterSalesInstallstion afterSalesInstallstion, HttpSession session,String beforeParams) {
		if(null == afterSalesInstallstion
				|| afterSalesInstallstion.getAfterSalesInstallstionId() <= 0){
			return new ResultInfo();
		}
		return afterSalesInstallstionService.saveInstallstionScore(afterSalesInstallstion,session);
	}

	/**
	 * <b>Description:</b><br>
	 * 启用/禁用工程师
	 * 
	 * @param request
	 * @param engineer
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年9月25日 上午10:02:06
	 */
	@ResponseBody
	@RequestMapping(value = "/changeenable")
	public ModelAndView changeEnable(HttpServletRequest request, Engineer engineer) {
		ModelAndView mv = new ModelAndView("aftersales/engineer/change_enable");
		mv.addObject("engineer", engineer);
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 保存启用/禁用工程师
	 * 
	 * @param request
	 * @param engineer
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年9月25日 上午10:28:43
	 */
	@ResponseBody
	@RequestMapping(value = "/savechangeenable")
	@SystemControllerLog(operationType = "edit",desc = "启用/禁用工程师")
	public ResultInfo saveChangeEnable(HttpServletRequest request, Engineer engineer, HttpSession session) {
		if (null == engineer.getEngineerId() || engineer.getEngineerId() <= 0) {
			return new ResultInfo();
		}
		return engineerService.saveChangeEnable(engineer, session);
	}
}
