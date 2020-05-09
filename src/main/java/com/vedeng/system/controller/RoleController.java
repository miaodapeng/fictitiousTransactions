package com.vedeng.system.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.vedeng.authorization.model.*;
import com.vedeng.authorization.model.vo.RRoleActionGroupVo;
import com.vedeng.authorization.model.vo.RolePlatformVo;
import com.vedeng.authorization.model.vo.RoleVo;
import com.vedeng.system.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.model.SelectModel;
import com.vedeng.common.page.Page;
import com.vedeng.common.validator.FormToken;


/**
 * <b>Description:</b><br> 角色管理
 * @author duke
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.system.controller
 * <br><b>ClassName:</b> RoleController
 * <br><b>Date:</b> 2017年4月25日 下午6:15:40
 */
@Controller
@RequestMapping("/system/role")
public class RoleController extends BaseController{
	@Autowired
	@Qualifier("roleService")
	private RoleService roleService;
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Autowired
	@Qualifier("platformService")
	private PlatformService platformService;

	@Autowired
	@Qualifier("rolePlatformService")
	private RolePlatformService rolePlatformService;

	@Autowired
	@Qualifier("actiongroupService")
	private ActiongroupService actiongroupService;

	@Autowired
	@Qualifier("actionService")
	private ActionService actionService;

	@Autowired
	@Qualifier("roleDataAuthorityService")
	private DataAuthorityService dataAuthorityService;


/*	@Autowired
	@Qualifier("actionService")
	private ActionService actionService;*/
	
	/**
	 * <b>Description: </b><br> 角色列表查询
	 * @param request
	 * @param roleVo
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午6:15:20
	 */
	@ResponseBody
	@RequestMapping(value="/index")
	public ModelAndView index(HttpServletRequest request,RoleVo roleVo,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(required = false) Integer pageSize,
            HttpSession session){
		User user =(User)session.getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView(); 
		//查询集合        
        Page page = getPageTag(request,pageNo,pageSize);
		roleVo.setCompanyId(user.getCompanyId());
		List<Role> roleList = roleService.queryListPage(roleVo, page);

		List<RolePlatformVo> choosedPlatform = rolePlatformService.queryListByIds(
				roleList.stream().map(Role::getRoleId).collect(Collectors.toList()));

		List<RolePlatformVo> showRolePlatform = new ArrayList<>();
		choosedPlatform.stream()
				.collect(Collectors.groupingBy(RolePlatformVo::getRoleId))
				.forEach((k,v) -> {
					Optional<RolePlatformVo> sum = v.stream().reduce((v1, v2) -> {
						v1.setPlatformName(v1.getPlatformName()+";"+v2.getPlatformName());
						return v1;
					});
					showRolePlatform.add(sum.orElse(null));
				});

		mv.addObject("role", roleVo);
		mv.addObject("choosedPlatform", CollectionUtils.isNotEmpty(roleVo.getPlatformId()) ? roleVo.getPlatformId().get(0) : null);
		mv.addObject("showRolePlatform", showRolePlatform);
		List<Platform> platforms = platformService.queryList();
		mv.addObject("platformList", platforms);
        mv.addObject("roleList",roleList);
        mv.addObject("page", page);
		mv.setViewName("system/role/index");
		return mv;
	}
	
	
	/**
	 * <b>Description:</b><br> 添加角色初始化
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午6:15:08
	 */
	@FormToken(save=true)
	@RequestMapping(value="/addRole")
	public ModelAndView addRole(){
		ModelAndView mv = new ModelAndView();
		List<Platform> platforms = platformService.queryList();
		mv.addObject("platformList", platforms);
		mv.setViewName("system/role/add_role");
		return mv; 
	}
	
	
	/**
	 * <b>Description:</b><br> 根据主键查询角色详细信息
	 * @param role
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午6:14:56
	 */
	@ResponseBody
	@RequestMapping(value = "getRoleByKey")
	public Role getRoleByKey(Role role){
		return roleService.getRoleByKey(role);
	}
	
	
	/**
	 * <b>Description:</b><br> 添加角色保存数据
	 * @param roleVo
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午6:14:46
	 */
	@FormToken(remove=true)
	@ResponseBody
	@RequestMapping(value = "/saveRole",method = RequestMethod.POST)
	public ResultInfo<?> saveRole(HttpSession session, RoleVo roleVo){
		User user =(User)session.getAttribute(ErpConst.CURR_USER);
		roleVo.setCompanyId(user.getCompanyId());
		return roleService.insertRole(roleVo);
	}

	
	/**
	 * <b>Description:</b><br> 编辑角色管理初始化
	 * @param role
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午6:14:16
	 */
	@RequestMapping(value="/editRole")
	public ModelAndView editRole(Role role){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("system/role/edit_role");
		role = roleService.getRoleByKey(role);
		List<RolePlatform> rolePlatforms = rolePlatformService.queryList(role.getRoleId());

		//erp已选功能组
		List<RRoleActiongroup> erp = roleService.getRoleGroup(role,1);
		//运营后台已选择功能组
		List<RRoleActiongroup> biz = roleService.getRoleGroup(role,2);
		//医械购已选功能组
		List<RRoleActiongroup> yxg = roleService.getRoleGroup(role,3);

		List<Platform> platforms = platformService.queryList();
		mv.addObject("platformList", platforms);
		mv.addObject("rolePlatformList", rolePlatforms);
		mv.addObject("erp", erp.size() > 0 ? "1": "0");
		mv.addObject("biz", biz.size() > 0 ? "1": "0");
		mv.addObject("yxg", yxg.size() > 0 ? "1": "0");
		mv.addObject(role);
		return mv; 
	}
	
	/**
	 * <b>Description:</b><br> 修改角色信息
	 * @param rolevo
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午6:14:01
	 */
	@ResponseBody
	@RequestMapping(value = "/updateRole")
	public ResultInfo<?> updateRole(HttpSession session, RoleVo rolevo){
		User user =(User)session.getAttribute(ErpConst.CURR_USER);
		if(user != null) {
			rolevo.setCompanyId(user.getCompanyId());
		}
		ResultInfo<?> result = roleService.updateRole(rolevo);
		if(result.getCode() == 0){
			result.setCode(0);result.setMessage("操作成功");
			super.delMenuRedis( null,null, rolevo.getRoleId(),user);
		}
		return result;
	}

	/**
	  *  进入设置权限页面
	  * @author wayne.liu
	  * @date  2019/6/10 19:31
	  * @param role 角色
	  * @return
	  */
	@ResponseBody
	@RequestMapping(value = "/intoMenu")
	public ModelAndView intoMenu(Role role){
		ModelAndView mv = new ModelAndView();

		List<RRoleDataAuthority> data = dataAuthorityService.queryListByRoleId(role.getRoleId());

		List<RolePlatformVo> rolePlatformList = rolePlatformService.queryListByRoleId(role.getRoleId());
		List<Platform> platforms = platformService.queryList();

		List<List<RRoleActionGroupVo>> allList = new ArrayList<>();

		for(RolePlatformVo each : rolePlatformList){
			allList.add(roleService.getRoleAllGroup(role,each.getPlatformId()));
		}

		Role exist = roleService.getRoleByKey(role);
		mv.addObject("data",data);
		mv.addObject("exist",exist);
		mv.addObject("allList",allList);
		mv.addObject("platformList",rolePlatformList);
		mv.addObject("platforms",platforms);
		mv.setViewName("system/role/role_menu");
		mv.addObject(role);
		return mv;
	}

	/**
	  * 进入设置权限 -- 修改权限 页面
	  * @author wayne.liu
	  * @date  2019/6/10 19:31
	  * @param 
	  * @return 
	  */
	@ResponseBody
	@RequestMapping(value = "/addmenu")
	public ModelAndView addMenu(Role role,Integer platformId,String choosedDataAuthority){
		ModelAndView mv = new ModelAndView();

		//读取当前平台所有功能组列表
		List<Actiongroup> allGroupList = roleService.queryMenuListByPlatformId(platformId);

		//角色当前平台已选的功能组功能点
		List<RRoleActiongroup> listGroup = roleService.getRoleGroup(role,platformId);
		//角色当前平台已选的功能点
		List<RRoleAction> listAction = roleService.getRoleAction(role,platformId);

		Platform exist = platformService.queryById(platformId);

		mv.addObject("dataAuthority",choosedDataAuthority);
		mv.addObject("platformId",platformId);
		mv.addObject("exist",exist);
		mv.addObject("allGroupList",allGroupList);
		mv.addObject("listGroup",listGroup);
		mv.addObject("listAction",listAction);
		mv.addObject(role);
		mv.setViewName("system/role/edit_menu");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 删除角色
	 * @param role 角色bean
	 * @return ResultInfo<Role>
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月25日 下午1:34:56
	 */
	@ResponseBody
	@RequestMapping(value="deleterole")
	public ResultInfo<Role> deleteRole(Role role){
		ResultInfo<Role> resultInfo = new ResultInfo<Role>();
		List<User> userList = userService.getUserByRoleId(role.getRoleId());
		if(userList.size() > 0){
			resultInfo.setMessage("该角色正在使用，无法删除");
			return resultInfo;
		}
		
		Integer res = roleService.delete(role);
		if(res > 0){
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
		}
		return resultInfo;
	}
	
	/**
	 * <b>Description:</b><br> 保存角色对应的功能信息
	 * @param request
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午3:57:46
	 */
	@ResponseBody
	@RequestMapping("/savemenu")
	public ModelAndView saveMenu(HttpServletRequest request,HttpSession session){
		Integer platformId = Integer.valueOf(request.getParameter("platformId"));
		User user =(User)session.getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView();ResultInfo<?> result = null;
		Integer roleId = null;
		try {
			if(request.getParameter("roleId")!=null){
				roleId = Integer.valueOf(request.getParameter("roleId"));
			}
			List<String> groupIdArr = JSON.parseArray(request.getParameter("groupIdArr").toString(),String.class);
			List<String> actionIdArr = JSON.parseArray(request.getParameter("actionIdArr").toString(),String.class);
			result=roleService.saveMenu(roleId,groupIdArr,actionIdArr,platformId);
			super.delMenuRedis( null,null, roleId,user);
		} catch (Exception e) {
			logger.error("savemenu:", e);
			return fail(mv);
		}
		mv.addObject("url","./addmenu.do?roleId="+roleId+"&platformId="+platformId);
		if(result.getCode()==0){
			return success(mv);
		}else{
			return fail(mv);
		}
	}

	/**
	 * <b>Description:</b><br> 保存角色对应的数据权限
	 * @param data
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午3:57:46
	 */
	@ResponseBody
	@RequestMapping(value = "/saveAuthority",method = RequestMethod.POST)
	public ResultInfo<?> saveMenu(String data,Integer roleId){
		try{
			dataAuthorityService.saveDataAuthority(data,roleId);
		}catch (Exception e){
			return new ResultInfo<>();
		}
		return new ResultInfo<>(0,"操作成功");
	}
}
