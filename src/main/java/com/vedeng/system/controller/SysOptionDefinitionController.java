package com.vedeng.system.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vedeng.common.annotation.SystemControllerLog;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.shiro.JedisUtils;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.system.service.SysOptionDefinitionService;

/**
 * <b>Description:</b><br> 数据字典维护
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.system.controller
 * <br><b>ClassName:</b> OptiondefinitionController
 * <br><b>Date:</b> 2017年9月7日 上午9:13:59
 */
@Controller
@RequestMapping("/system/sysoptiondefinition")
public class SysOptionDefinitionController extends BaseController{
	@Autowired
	@Qualifier("sysOptionDefinitionService")
	private SysOptionDefinitionService sysOptionDefinitionService;
	
	/**
	 * <b>Description:</b><br> 数据字典列表
	 * @param request
	 * @param sysOptionDefinition
	 * @param pageNo
	 * @param pageSize
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年9月7日 上午9:16:37
	 */
	@ResponseBody
    @RequestMapping(value = "/list")
	public ModelAndView list(HttpServletRequest request,SysOptionDefinition sysOptionDefinition,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo, 
			@RequestParam(required = false) Integer pageSize,
			HttpSession session){
		ModelAndView mv = new ModelAndView("system/optiondefinition/list_optiondefinition");  
		Page page = getPageTag(request,pageNo,pageSize);
		List<SysOptionDefinition> list = null;
		
		Map<String,Object>  map = sysOptionDefinitionService.querylistPage(sysOptionDefinition, page);
		
		list = (List<SysOptionDefinition>) map.get("list");
		mv.addObject("sysOptionDefinition", sysOptionDefinition);
		mv.addObject("list", list);
		mv.addObject("page", (Page) map.get("page"));
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 添加数据字典
	 * @param request
	 * @param sysOptionDefinition
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年9月7日 下午1:16:56
	 */
	@ResponseBody
    @RequestMapping(value = "/add")
	public ModelAndView add(HttpServletRequest request,SysOptionDefinition sysOptionDefinition){
		ModelAndView mv = new ModelAndView("system/optiondefinition/add_optiondefinition"); 
		mv.addObject("sysOptionDefinition", sysOptionDefinition);
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 添加子集
	 * @param request
	 * @param sysOptionDefinition
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年9月7日 下午3:26:32
	 */
	@ResponseBody
	@RequestMapping(value = "/addchild")
	public ModelAndView addChild(HttpServletRequest request,SysOptionDefinition sysOptionDefinition){
		ModelAndView mv = new ModelAndView("system/optiondefinition/add_optiondefinition_child"); 
		
		SysOptionDefinition optionDefinition = sysOptionDefinitionService.getOptionById(sysOptionDefinition);
		
		mv.addObject("sysOptionDefinition", optionDefinition);
		return mv;
	}
	
	
	/**
	 * <b>Description:</b><br> 保存新增数据字典
	 * @param request
	 * @param sysOptionDefinition
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年9月7日 下午1:45:15
	 */
	@ResponseBody
    @RequestMapping(value = "/saveadd")
	@SystemControllerLog(operationType = "add",desc = "保存新增数据字典")
	public ResultInfo saveAdd(HttpServletRequest request,SysOptionDefinition sysOptionDefinition){
		ResultInfo resultInfo = sysOptionDefinitionService.saveAdd(sysOptionDefinition);
		return resultInfo;
	}
	
	/**
	 * <b>Description:</b><br> 编辑数据字典
	 * @param request
	 * @param sysOptionDefinition
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年9月7日 上午11:34:33
	 */
	@ResponseBody
    @RequestMapping(value = "/edit")
	public ModelAndView edit(HttpServletRequest request,SysOptionDefinition sysOptionDefinition){
		if(null == sysOptionDefinition || sysOptionDefinition.getSysOptionDefinitionId() <= 0){
			return pageNotFound();
		}
		ModelAndView mv = new ModelAndView("system/optiondefinition/edit_optiondefinition"); 
		SysOptionDefinition optionDefinition = sysOptionDefinitionService.getOptionById(sysOptionDefinition);
		
		mv.addObject("sysOptionDefinition", optionDefinition);
		try {
			mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(optionDefinition)));
		} catch (Exception e) {
			logger.error("sys option edit:", e);
		}
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 编辑数据字典子集
	 * @param request
	 * @param sysOptionDefinition
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年9月7日 下午3:27:26
	 */
	@ResponseBody
	@RequestMapping(value = "/editchild")
	public ModelAndView editChild(HttpServletRequest request,SysOptionDefinition sysOptionDefinition){
		if(null == sysOptionDefinition || sysOptionDefinition.getSysOptionDefinitionId() <= 0){
			return pageNotFound();
		}
		ModelAndView mv = new ModelAndView("system/optiondefinition/edit_optiondefinition_child"); 
		SysOptionDefinition optionDefinition = sysOptionDefinitionService.getOptionById(sysOptionDefinition);
		
		mv.addObject("sysOptionDefinition", optionDefinition);
		try {
			mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(optionDefinition)));
		} catch (Exception e) {
			logger.error("sys option editchild:", e);
		}
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 保存编辑数据字典
	 * @param request
	 * @param sysOptionDefinition
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年9月7日 下午2:28:27
	 */
	@ResponseBody
    @RequestMapping(value = "/saveedit")
	@SystemControllerLog(operationType = "edit",desc = "保存编辑数据字典")
	public ResultInfo saveEdit(HttpServletRequest request,SysOptionDefinition sysOptionDefinition,String beforeParams){
		if( null == sysOptionDefinition.getSysOptionDefinitionId() || sysOptionDefinition.getSysOptionDefinitionId() <= 0){
			return new ResultInfo();
		}
		ResultInfo resultInfo = sysOptionDefinitionService.saveEdit(sysOptionDefinition);
		if(resultInfo != null && resultInfo.getCode() == 0){
			if(sysOptionDefinition.getParentId() == 0){
				JedisUtils.del(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_OBJECT+sysOptionDefinition.getSysOptionDefinitionId());
			}else{
				JedisUtils.del(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST+sysOptionDefinition.getParentId());
			}
		}
		return resultInfo;
	}
	
	/**
	 * <b>Description:</b><br> 查看子集字典
	 * @param request
	 * @param sysOptionDefinition
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年9月7日 下午3:13:04
	 */
	@ResponseBody
    @RequestMapping(value = "/viewchilds")
	public ModelAndView viewChilds(HttpServletRequest request,SysOptionDefinition sysOptionDefinition){
		ModelAndView mv = new ModelAndView("system/optiondefinition/list_optiondefinition_childs");
		
		List<SysOptionDefinition> list = sysOptionDefinitionService.getChilds(sysOptionDefinition);
		mv.addObject("list", list);
		mv.addObject("sysOptionDefinition", sysOptionDefinition);
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 启用\禁用数据字典
	 * @param request
	 * @param sysOptionDefinition
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年9月7日 下午3:13:41
	 */
	@ResponseBody
    @RequestMapping(value = "/changestatus")
	@SystemControllerLog(operationType = "edit",desc = "启用/禁用数据字典")
	public ResultInfo changeStatus(HttpServletRequest request,SysOptionDefinition sysOptionDefinition){
		if(null == sysOptionDefinition || sysOptionDefinition.getSysOptionDefinitionId() <= 0){
			return new ResultInfo();
		}
		ResultInfo resultInfo = sysOptionDefinitionService.changeStatus(sysOptionDefinition);
		if(sysOptionDefinition.getParentId() == 0){
			JedisUtils.del(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_OBJECT+sysOptionDefinition.getSysOptionDefinitionId());
		}else{
			JedisUtils.del(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST+sysOptionDefinition.getParentId());
		}
		return resultInfo;
	}
}
