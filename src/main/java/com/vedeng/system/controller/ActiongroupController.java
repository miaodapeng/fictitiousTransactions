package com.vedeng.system.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.vedeng.authorization.model.*;
import com.vedeng.system.service.PlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.system.service.ActionService;
import com.vedeng.system.service.ActiongroupService;
import com.vedeng.system.service.RoleService;


/**
 * <b>Description:</b><br> 功能分组
 * @author duke
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.system.controller
 * <br><b>ClassName:</b> ActiongroupController
 * <br><b>Date:</b> 2017年4月25日 下午6:23:37
 */
@Controller
@RequestMapping("/system/actiongroup")
public class ActiongroupController extends BaseController{
	@Autowired
	@Qualifier("actiongroupService")
	private ActiongroupService actiongroupService;
	
	@Autowired
	@Qualifier("actionService")
	private ActionService actionService;
	
	@Autowired
	@Qualifier("roleService")
	private RoleService roleService;

	@Autowired
	@Qualifier("platformService")
	private PlatformService platformService;
	

	/**
	 * <b>Description:</b><br> 功能分组列表
	 * @param request
	 * @param actiongroup
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午6:23:59
	 */
	@ResponseBody
	@RequestMapping(value="/index")
	public ModelAndView queryGroup(HttpServletRequest request,Actiongroup actiongroup,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,  //required = false:可不传入pageNo这个参数，true必须传入，defaultValue默认值，若无默认值，使用Page类中的默认值
            @RequestParam(required = false) Integer pageSize){
		ModelAndView mv = new ModelAndView(); 
		//查询集合        
		actiongroup.setParentId(0);
		List<Actiongroup> groupList = actiongroupService.getActionGroupListByPlatform(actiongroup.getPlatformId());

		List<Platform> platforms = platformService.queryList();
		Map<Integer, String> platformMap = platforms.stream().collect(Collectors.toMap(Platform::getPlatformId, Platform::getPlatformName));

		groupList.forEach(e->e.setPlatformName(platformMap.get(e.getPlatformId())));

        //页面传值
        mv.addObject("actiongroup", actiongroup);
        
        mv.addObject("actiongroupList",groupList);  
        mv.addObject("platformList",platforms);
		mv.setViewName("system/actiongroup/index");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 新增部门信息初始化 
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午6:24:10
	 */
	@RequestMapping(value="/addGroup")
	public ModelAndView addGroup(){
		ModelAndView mav = new ModelAndView();
		List<Actiongroup> groupList = actiongroupService.getActionGroupList();
		List<Platform> platforms = platformService.queryList();
		mav.addObject("platformList", platforms);
		mav.addObject("groupList", groupList);
		mav.setViewName("system/actiongroup/add_group");
		return mav;
	}
	
	/**
	 * <b>Description:</b><br> 新增部门信息保存
	 * @param actiongroup
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午6:24:20
	 */
	@ResponseBody
	@RequestMapping(value="/saveActionGroup")
	public ResultInfo<?> saveActionGroup(HttpSession session, Actiongroup actiongroup){
		User user =(User)session.getAttribute(ErpConst.CURR_USER);
		ResultInfo<?> result = new ResultInfo<>();
		int i = actiongroupService.insert(actiongroup);
		if(i==1){
			result.setCode(0);result.setMessage("操作成功");
			super.delMenuRedis( null,actiongroup.getActiongroupId(), null,user);
		}
		return result;
	}
	
	/**
	 * <b>Description:</b><br> 编辑部门信息初始化 
	 * @param actiongroup
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午6:24:27
	 */
	@RequestMapping(value="/editGroup")
	public ModelAndView editGroup(Actiongroup actiongroup){
		ModelAndView mav = new ModelAndView();
		List<Actiongroup> groupList = actiongroupService.getActionGroupList();
		mav.addObject("groupList", groupList);
		actiongroup = actiongroupService.getActionGroupByKey(actiongroup);
		mav.addObject("actiongroup", actiongroup);
		List<Platform> platforms = platformService.queryList();
		mav.addObject("platformList", platforms);
		mav.setViewName("system/actiongroup/edit_group");
		return mav;
	}
	
	/**
	 * <b>Description:</b><br> 修改部门信息 
	 * @param request
	 * @param actiongroup
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午6:24:34
	 */
	@ResponseBody
	@RequestMapping(value="/updateActionGroup")
	public ResultInfo<?> updateActionGroup(HttpServletRequest request, HttpSession session,Actiongroup actiongroup){
		User user =(User)session.getAttribute(ErpConst.CURR_USER);
		ResultInfo<?> result = new ResultInfo<>();
		int i = 0;
		try {
			i = actiongroupService.update(actiongroup);
		} catch (Exception e) {
			logger.error("updateActionGroup:", e);
		}
		if(i==1){
			result.setCode(0);result.setMessage("操作成功");
			super.delMenuRedis( null,actiongroup.getActiongroupId(), null,user);
		}
		return result;
	}
	
	/**
	 * <b>Description:</b><br> 删除功能分组
	 * @param actiongroup 功能分组bean
	 * @return ResultInfo<Actiongroup>
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月25日 下午1:51:05
	 */
	@ResponseBody
	@RequestMapping(value="deleteactiongroup")
	public ResultInfo<Actiongroup> deleteActiongroup(Actiongroup actiongroup){
		ResultInfo<Actiongroup> resultInfo = new ResultInfo<>();
		
		List<Action> actionList = actionService.getActionByActiongroupId(actiongroup.getActiongroupId());
		if(actionList.size() > 0){
			resultInfo.setMessage("该功能分组正在使用，无法删除");
			return resultInfo;
		}		
		
		List<Role> roleList = roleService.getRoleByActiongroupId(actiongroup.getActiongroupId());
		if(roleList.size() > 0){
			resultInfo.setMessage("该功能分组正在使用，无法删除");
			return resultInfo;
		}
		
		Integer res = actiongroupService.delete(actiongroup);
		if(res > 0){
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
		}
		return resultInfo;
	}
}
