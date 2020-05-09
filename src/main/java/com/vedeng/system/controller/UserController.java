package com.vedeng.system.controller;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.vedeng.aftersales.service.WebAccountService;
import com.vedeng.authorization.model.*;
import com.vedeng.common.util.DateUtil;
import com.vedeng.soap.ApiSoap;
import com.vedeng.system.service.*;
import com.vedeng.trader.dao.TraderMapper;
import com.vedeng.trader.dao.WebAccountMapper;
import com.vedeng.trader.model.RTraderJUser;
import com.vedeng.trader.model.Trader;
import com.vedeng.trader.model.WebAccount;
import com.vedeng.trader.model.vo.WebAccountVo;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.vedeng.common.annotation.SystemControllerLog;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.shiro.JedisUtils;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.common.util.Salt;
import com.vedeng.common.validator.FormToken;

/**
 * <b>Description:</b><br>
 * 员工管理
 * 
 * @author Jerry
 * @Note <b>ProjectName:</b> erp <br>
 *       <b>PackageName:</b> com.vedeng.system.controller <br>
 *       <b>ClassName:</b> UserController <br>
 *       <b>Date:</b> 2017年4月25日 上午11:21:34
 */
@Controller
@RequestMapping("/system/user")
public class UserController extends BaseController {
	@Autowired
	@Qualifier("userService")
	private UserService userService;// 自动注入userService

	@Autowired
	@Qualifier("orgService")
	private OrgService orgService;// 自动注入orgService

	@Autowired
	@Qualifier("positService")
	private PositService positService;// 自动注入positService

	@Autowired
	@Qualifier("regionService")
	private RegionService regionService;// 自动注入regionService

	@Autowired
	@Qualifier("roleService")
	private RoleService roleService;// 自动注入roleService

	@Autowired
	@Qualifier("companyService")
	private CompanyService companyService;

	@Autowired
	@Qualifier("platformService")
	private PlatformService platformService;

	@Autowired
	@Qualifier("userBelongCompanyService")
	private UserBelongCompanyService userBelongCompanyService;

	@Autowired
	@Qualifier("apiSoap")
    private ApiSoap apiSoap;

	@Autowired
	private WebAccountService webAccountService;

	@Resource
	TraderMapper traderMapper;

	@Resource
	WebAccountMapper webAccountMapper;

	private final String DEFAULT_NAME = "南京贝登医疗股份有限公司";

	private final Integer DAFULT_ID_NO=0;//否
	private final Integer DAFULT_ID_YES=1;//是

	/**
	 * <b>Description:</b><br>
	 * 员工列表
	 * 
	 * @param request
	 *            请求
	 * @param user
	 *            员工bean
	 * @param pageNo
	 *            当前页 默认1
	 * @param pageSize
	 *            每页条数
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年4月25日 上午11:21:44
	 */
	@ResponseBody
	@RequestMapping(value = "/index")
	public ModelAndView index(HttpServletRequest request, User user,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo, // required
																				// =
																				// false:可不传入pageNo这个参数，true必须传入，defaultValue默认值，若无默认值，使用Page类中的默认值
			@RequestParam(required = false) Integer pageSize, HttpSession session,String flag) {
		User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
		// 所有分公司
		List<Company> companyList = companyService.getAll();
		// 获取部门
		List<Organization> orgList = orgService.getOrgList(0, session_user.getCompanyId(), true);

		// 获取职位
		List<Position> positList = new ArrayList<Position>();
		if (user.getOrgId() != null && user.getOrgId() > 0) {
			positList = positService.getPositByOrgId(user.getOrgId());
		}

		ModelAndView mv = new ModelAndView();
		// 查询集合
		Page page = getPageTag(request, pageNo, pageSize);
		if (null == user.getCompanyId()) {
			user.setCompanyId(session_user.getCompanyId());
		}

		List<User> userList = new ArrayList<>();

		List<Platform> platformList = platformService.queryList();

		if("1".equals(flag)){
			//当前是搜索
			if(StringUtils.isEmpty(user.getBelongCompanyName())){
				//直接查询
				userList  = userService.querylistPage(user, page);
			}else {
				UserBelongCompany exist = userBelongCompanyService.getUserCompanyByName(user.getBelongCompanyName());
				if(exist == null){

					//没有该所属公司，直接返回空
					mv.addObject("platformList", platformList);
					mv.addObject("companyList", companyList);
					mv.addObject("orgList", orgList);
					mv.addObject("positList", positList);
					mv.addObject("user", user);
					mv.addObject("users", userList);
					mv.addObject("page", page);
					mv.setViewName("system/user/index");
					return mv;
				}else {
					//正常搜索
					user.setUserBelongCompanyId(exist.getUserBelongCompanyId());
					userList  = userService.querylistPage(user, page);
				}
			}
		}else {
			//当前不是索搜，直接查询
			userList  = userService.querylistPage(user, page);
		}

		List<UserBelongCompany> listCompany = userBelongCompanyService.queryAll();

		Map<Integer,String> mapCompany = listCompany.stream().
				collect(Collectors.toMap(UserBelongCompany::getUserBelongCompanyId, UserBelongCompany::getCompanyName));

		for(User each : userList){
			//id,id,id转name,name,name
			if(each.getSystems() != null) {
				each.setSystems(String.join(",", getSystems(Arrays.asList(each.getSystems().split(",")))));
			}
			each.setBelongCompanyName(mapCompany.get(each.getUserBelongCompanyId()) == null
					? DEFAULT_NAME : mapCompany.get(each.getUserBelongCompanyId()) );
		}

		// 页面传值
		mv.addObject("platformList", platformList);
		mv.addObject("companyList", companyList);
		mv.addObject("orgList", orgList);
		mv.addObject("positList", positList);
		mv.addObject("user", user);

		mv.addObject("users", userList);
		mv.addObject("page", page);
		mv.setViewName("system/user/index");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 新增管理员
	 * 
	 * @param request
	 * @param session
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年8月9日 上午10:28:55
	 */
	@FormToken(save=true)
	@ResponseBody
	@RequestMapping(value = "/addmanager")
	public ModelAndView addManager(HttpServletRequest request, HttpSession session) {
		User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("system/user/add_manager");
		// 所有分公司
		List<Company> companyList = companyService.getAll();
		Role role = new Role();
		role.setCompanyId(session_user.getCompanyId()); // 公司ID session中获取
		// 角色
		List<Role> roleList = roleService.getAllRoles(role);
		mv.addObject("companyList", companyList);
		mv.addObject("roleList", roleList);
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 保存管理员
	 * 
	 * @param request
	 * @param session
	 * @param user
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 * 		<b>Date:</b> 2017年8月9日 上午10:58:51
	 */
	@FormToken(remove=true)
	@ResponseBody
	@RequestMapping(value = "/savemanager")
	@SystemControllerLog(operationType = "add",desc = "保存新增管理员")
	public ResultInfo saveManager(HttpServletRequest request, HttpSession session, User user, UserDetail userDetail) {
		User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
		ResultInfo resultInfo = new ResultInfo<>();
		try {
			user.setIsAdmin(ErpConst.ONE);
			Integer userId = userService.addUser(session, user, userDetail);
			if (userId > 0) {
				JedisUtils.del(dbType + ErpConst.KEY_PREFIX_MENU + userId);
				resultInfo.setCode(0);
				resultInfo.setMessage("操作成功");
			}
			return resultInfo;
		} catch (Exception e) {
			logger.error("savemanager:", e);
			return resultInfo;
		}
	}

	/**
	 * <b>Description:</b><br>
	 * 编辑管理员
	 * 
	 * @param request
	 * @param session
	 * @param user
	 * @return
	 * @throws IOException 
	 * @Note <b>Author:</b> Jerry <br>
	 * 		<b>Date:</b> 2017年8月10日 上午9:15:56
	 */
	@ResponseBody
	@RequestMapping(value = "/editmanager")
	public ModelAndView editManager(HttpServletRequest request, HttpSession session, User user, UserDetail userDetail) throws IOException {
		if (null == user.getUserId() || user.getUserId() <= 0) {
			return pageNotFound();
		}
		User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("system/user/edit_manager");
		User userInfo = userService.getUserById(user.getUserId());
		// 所有分公司
		List<Company> companyList = companyService.getAll();
		Role role = new Role();
		role.setCompanyId(session_user.getCompanyId()); // 公司ID session中获取
		// 角色
		List<Role> roleList = roleService.getAllRoles(role);

		// 角色处理
		List<Role> userRoles = roleService.getUserRoles(user.getUserId());

		mv.addObject("user", userInfo);
		mv.addObject("userRoles", userRoles);
		mv.addObject("companyList", companyList);
		mv.addObject("roleList", roleList);
		mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(userInfo)));
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 保存编辑管理员
	 * 
	 * @param request
	 * @param session
	 * @param user
	 * @param userDetail
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 * 		<b>Date:</b> 2017年8月10日 上午9:27:07
	 */
	@ResponseBody
	@RequestMapping(value = "/saveeditmanager")
	@SystemControllerLog(operationType = "edit",desc = "保存编辑管理员")
	public ResultInfo saveEditManager(HttpServletRequest request, HttpSession session, User user,
			UserDetail userDetail,String beforeParams) {
		User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
		ResultInfo resultInfo = new ResultInfo<>();
		try {
			if (null == user.getUserId() || user.getUserId() <= 0) {
				return resultInfo;
			}
			Integer userId = userService.editUser(session, user, userDetail);
			if (userId > 0) {
				JedisUtils.del(dbType + ErpConst.KEY_PREFIX_MENU + userId);
				resultInfo.setCode(0);
				resultInfo.setMessage("操作成功");
			}
			return resultInfo;
		} catch (Exception e) {
			logger.error("user saveeditmanager:", e);
			return resultInfo;
		}
	}

	/**
	 * <b>Description:</b><br>
	 * 添加/编辑员工
	 * 
	 * @param request
	 *            请求
	 * @param user
	 *            员工bean
	 * @return
	 * @throws IOException 
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年4月25日 上午11:22:17
	 */
	@FormToken(save=true)
	@ResponseBody
	@RequestMapping(value = "/modifyuser")
	public ModelAndView modifyUser(HttpServletRequest request, HttpSession session, User user) throws IOException {
		User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView();

		Integer companyId = 0;
		User userInfo = new User();
		if (!StringUtils.isEmpty(user) && null != user.getUserId() && user.getUserId() > 0) {
			userInfo = userService.getUserById(user.getUserId());
			companyId = userInfo.getCompanyId();
			
			mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(userInfo)));
			UserBelongCompany exist = userBelongCompanyService.getUserCompanyById(userInfo.getUserBelongCompanyId());
			mv.addObject("loginSystemList", Arrays.asList(userInfo.getSystems().split(",")));
			mv.addObject("belongCompany", exist == null ? null : exist.getCompanyName());
		} else {
			companyId = session_user.getCompanyId();
		}
		// 获取部门
		List<Organization> orgList = orgService.getOrgList(0, companyId, true);

		// 获取人员
		user.setCompanyId(companyId);
		user.setIsDisabled(ErpConst.ZERO);
		List<User> userList = userService.getAllUser(user);

		// 地区
		List<Region> provinceList = regionService.getRegionByParentId(1);

		Role role = new Role();
		role.setCompanyId(companyId); // 公司ID session中获取
		// 角色
		List<Role> roleList = roleService.getAllRoles(role);

		if (!StringUtils.isEmpty(user) && null != user.getUserId() && user.getUserId() > 0) {
			// 编辑
			// 获取职位
			List<Position> userPositionList = positService.getPositionByUserId(user.getUserId());
			List<Object> orgPositList = new ArrayList<Object>();

			for (Position p : userPositionList) {
				Map map = new HashMap();

				// 获取职位
				List<Position> positList = positService.getPositByOrgId(p.getOrgId());

				map.put("orgList", orgList);
				map.put("positList", positList);
				map.put("orgId", p.getOrgId());
				map.put("positId", p.getPositionId());

				orgPositList.add(map);
			}

			// 地区处理
			if (!StringUtils.isEmpty(userInfo.getUserAddress())) {
				Integer areaId = userInfo.getUserAddress().getAreaId();
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

			// 角色处理
			List<Role> userRoles = roleService.getUserRoles(user.getUserId());

			mv.addObject("userRoles", userRoles);
			mv.addObject("orgPositList", orgPositList);
		}


		mv.addObject("orgList", orgList);
		mv.addObject("userList", userList);
		mv.addObject("provinceList", provinceList);
		mv.addObject("roleList", roleList);
		mv.addObject("user", userInfo);

		if (null != request.getParameter("allErrors")) {
			mv.addObject("allErrors", request.getParameter("allErrors"));
		}
		mv.setViewName("system/user/modify_user");

		List<Platform> platforms = platformService.queryList();
		mv.addObject("platformList", platforms);

		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 保存添加/编辑员工
	 * 
	 * @param request
	 * @param user
	 *            员工bean
	 * @return
	 * @throws IOException 
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年4月25日 上午11:22:40
	 */
	@FormToken(remove=true)
	@ResponseBody
	@RequestMapping(value = "/saveuser")
	@SystemControllerLog(operationType = "add",desc = "保存新增用户")
	public ModelAndView saveUser(HttpServletRequest request, HttpSession session, @Validated User user,
			BindingResult bindingResult, @Validated UserDetail userDetail, BindingResult bindingResultUserDetail,
			@Validated UserAddress userAddress, BindingResult bindingResultUserAddress) throws IOException {
		ModelAndView mv = new ModelAndView();
		List<ObjectError> allErrors = new ArrayList<>();
		if (bindingResult.hasErrors()) {
			List<ObjectError> Errors = bindingResult.getAllErrors();
			for (ObjectError o : Errors) {
				allErrors.add(o);
			}
		}
		if (bindingResultUserDetail.hasErrors()) {
			List<ObjectError> Errors = bindingResultUserDetail.getAllErrors();
			for (ObjectError o : Errors) {
				allErrors.add(o);
			}
		}
		if (bindingResultUserAddress.hasErrors()) {
			List<ObjectError> Errors = bindingResultUserAddress.getAllErrors();
			for (ObjectError o : Errors) {
				allErrors.add(o);
			}
		}
		if (allErrors.size() > 0) {
			request.setAttribute("allErrors", allErrors);
			return this.modifyUser(request, session, user);
		}
		try {
			Integer userId = userService.modifyUser(session, user, userDetail, userAddress);
			if (userId > 0) {
				mv.addObject("url", "./viewuser.do?userId=" + userId);
				//删除用户菜单
				JedisUtils.del(dbType + ErpConst.KEY_PREFIX_MENU + userId);
				return success(mv);
			} else {
				return fail(mv);
			}
		} catch (Exception e) {
			logger.error("user saveuser:", e);
			return fail(mv);
		}
	}
	/**
	 * <b>Description:</b><br> 保存编辑用户信息
	 * @param request
	 * @param session
	 * @param user
	 * @param bindingResult
	 * @param userDetail
	 * @param bindingResultUserDetail
	 * @param userAddress
	 * @param bindingResultUserAddress
	 * @return
	 * @throws IOException
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年9月18日 下午2:23:02
	 */
	@ResponseBody
	@RequestMapping(value = "/saveedituser")
	@SystemControllerLog(operationType = "edit",desc = "保存编辑用户")
	public ModelAndView saveEditUser(HttpServletRequest request, HttpSession session, @Validated User user,
			BindingResult bindingResult, @Validated UserDetail userDetail, BindingResult bindingResultUserDetail,
			@Validated UserAddress userAddress, BindingResult bindingResultUserAddress,String beforeParams) throws IOException {
		ModelAndView mv = new ModelAndView();
		List<ObjectError> allErrors = new ArrayList<>();
		if (bindingResult.hasErrors()) {
			List<ObjectError> Errors = bindingResult.getAllErrors();
			for (ObjectError o : Errors) {
				allErrors.add(o);
			}
		}
		if (bindingResultUserDetail.hasErrors()) {
			List<ObjectError> Errors = bindingResultUserDetail.getAllErrors();
			for (ObjectError o : Errors) {
				allErrors.add(o);
			}
		}
		if (bindingResultUserAddress.hasErrors()) {
			List<ObjectError> Errors = bindingResultUserAddress.getAllErrors();
			for (ObjectError o : Errors) {
				allErrors.add(o);
			}
		}
		if (allErrors.size() > 0) {
			request.setAttribute("allErrors", allErrors);
			return this.modifyUser(request, session, user);
		}
		try {
			Integer userId = userService.modifyUser(session, user, userDetail, userAddress);
			if (userId > 0) {
				 int i=updateTraderBelongFormPlat(userId);
				 if(i==0){
					 mv.addObject("url", "./viewuser.do?userId=" + userId);
					 mv.addObject("message","userId为空");
					 return fail(mv);
				 }
				mv.addObject("url", "./viewuser.do?userId=" + userId);
				//删除用户菜单
				JedisUtils.del(dbType + ErpConst.KEY_PREFIX_MENU + userId);
				JedisUtils.del(dbType + ErpConst.KEY_PREFIX_GROUP_MENU + userId);
				return success(mv);
			} else {
				return fail(mv);
			}
		} catch (Exception e) {
			logger.error("saveedituser:", e);
			return fail(mv);
		}
	}


	public Integer updateTraderBelongFormPlat(Integer userId){
		if(userId==null){
			return 0;
		}
		Integer belongPlatform= userService.getBelongPlatformOfUser(userId,ErpConst.ONE);
		Trader trader=new Trader();
		trader.setBelongPlatform(belongPlatform);
		trader.setUserId(userId);
		int update =traderMapper.updateTrader(trader);
		if(update > 0){
			WebAccountVo webAccountVo = new WebAccountVo();
			webAccountVo.setUserTraderId(userId);
			webAccountVo.setBelongPlatform(belongPlatform);
			webAccountVo.setModTime(new Date());
			int i = webAccountMapper.updateErpUserId(webAccountVo);
		}
		return 1;
	}
	/**
	 * 
	 * <b>Description:</b><br>
	 * 启用/禁用员工
	 * 
	 * @param user
	 *            员工bean
	 * @return ResultInfo<User> json
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年4月25日 上午11:23:04
	 */
	@ResponseBody
	@RequestMapping(value = "/changedisabled")
	@SystemControllerLog(operationType = "edit",desc = "启用/禁用用户")
	public ResultInfo<User> changeDisabled(User user) {
		Boolean suc = userService.changDisabled(user);
		if (1 == user.getIsDisabled()) {
			if (JedisUtils.exists(dbType + ErpConst.KEY_PREFIX_USERID_SESSIONID + user.getUserId())) {
				String sessionId = JedisUtils.get(dbType + ErpConst.KEY_PREFIX_USERID_SESSIONID + user.getUserId());
				JedisUtils.del(ErpConst.KEY_PREFIX_SESSION + sessionId);
			}
		}
		ResultInfo<User> result = new ResultInfo<User>();

		if (suc) {// 成功
			result.setCode(0);
			result.setMessage("操作成功");
		}
		return result;
	}

	/**
	 * <b>Description:</b><br>
	 * 查看员工
	 * 
	 * @param user
	 * @return ModelAndView
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年4月26日 上午8:42:09
	 */
	@ResponseBody
	@RequestMapping(value = "viewuser")
	public ModelAndView viewUser(User user) {
		if (StringUtils.isEmpty(user) || null == user.getUserId() || user.getUserId() <= 0) {
			return pageNotFound();
		}
		ModelAndView mv = new ModelAndView();

		User userInfo = userService.getUserById(user.getUserId());

		if (null == userInfo) {
			return pageNotFound();
		}

		// 地区
		if (!StringUtils.isEmpty(userInfo.getUserAddress()) && null != userInfo.getUserAddress().getAreaId()
				&& userInfo.getUserAddress().getAreaId() > 0) {
			Integer areaId = userInfo.getUserAddress().getAreaId();
			String region = (String) regionService.getRegion(areaId, 2);

			mv.addObject("region", region);
		}

		// 角色处理
		List<Role> userRoles = roleService.getUserRoles(user.getUserId());

		UserBelongCompany exist = userBelongCompanyService.getUserCompanyById(userInfo.getUserBelongCompanyId());

		mv.addObject("belongCompany", exist == null ? DEFAULT_NAME : exist.getCompanyName());
		mv.addObject("showSystems",
				String.join(",",getSystems(Arrays.asList(userInfo.getSystems().split(",")))));
		mv.addObject("userRoles", userRoles);
		mv.addObject("user", userInfo);
		mv.setViewName("system/user/view_user");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 异步查询员工
	 * 
	 * @param user
	 *            员工bean
	 * @return ResultInfo<User>
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年4月27日 上午9:25:45
	 */
	@ResponseBody
	@RequestMapping(value = "getuser")
	public ResultInfo<User> getUser(User user, HttpSession session) {
		ResultInfo<User> resultInfo = new ResultInfo<User>();
		User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
		// 用户名判断
		if (user.getUsername() != null && user.getUsername() != "") {
			User u = new User();
			if (user.getCompanyId() != null && user.getCompanyId() > 0) {
				u.setCompanyId(user.getCompanyId());
			} else {
				u.setCompanyId(session_user.getCompanyId());
			}
			u.setUsername(user.getUsername());

			User info = userService.getUserByName(u);

			if (null != info) {
				if (null != user.getUserId() && !user.getUserId().equals(0)) {// 编辑用户
					if (!user.getUserId().equals(info.getUserId())) {
						resultInfo.setMessage("用户名不允许重复");
						return resultInfo;
					}
				} else {// 新增
					resultInfo.setMessage("用户名不允许重复");
					return resultInfo;
				}
			}
			// if(null != info && null != user.getUserId() &&
			// !user.getUserId().equals(info.getUserId())){
			//
			// resultInfo.setMessage("用户名不允许重复");
			// return resultInfo;
			// }
			// if ((null != user.getUserId() && user.getUserId() > 0 &&
			// !StringUtils.isEmpty(info)
			// && user.getUserId() != info.getUserId())
			// || (null == user.getUserId() && !StringUtils.isEmpty(info))) {
			// if (null != user.getUsername() && user.getUsername() != ""
			// &&
			// (user.getUsername().toLowerCase()).equals(info.getUsername().toLowerCase()))
			// {
			// }
			// }
		}
		// 工号判断
		if (user.getNumber() != null && user.getNumber() != "") {
			User u = new User();
			u.setCompanyId(session_user.getCompanyId());
			u.setNumber(user.getNumber());

			User info = userService.getUserByNumber(u);
			if (null != info) {
				if (null != user.getUserId() && !user.getUserId().equals(0)) {// 编辑用户
					if (!user.getUserId().equals(info.getUserId())) {
						resultInfo.setMessage("工号不允许重复");

						return resultInfo;
					}
				} else {// 新增
					resultInfo.setMessage("工号不允许重复");

					return resultInfo;
				}
			}
			// if(null != info && null != user.getUserId() &&
			// !user.getUserId().equals(info.getUserId())){
			//
			// resultInfo.setMessage("工号不允许重复");
			//
			// return resultInfo;
			// }
			// if ((null != user.getUserId() && user.getUserId() > 0 &&
			// !StringUtils.isEmpty(info)
			// && user.getUserId() != info.getUserId())
			// || (null == user.getUserId() && !StringUtils.isEmpty(info))) {
			// if (null != user.getNumber() && user.getNumber() != "" &&
			// user.getNumber().equals(info.getNumber())) {
			// }
			// }
		}

		resultInfo.setCode(0);
		resultInfo.setMessage("操作成功");
		return resultInfo;
	}

	/**
	 * <b>Description:</b><br> 获取部门用户
	 * @param request
	 * @param orgId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年1月2日 上午11:33:59
	 */
	@ResponseBody
	@RequestMapping(value = "getUserListByOrgId")
	public ResultInfo<?> getUserListByOrgId(HttpServletRequest request, @RequestParam("orgId") Integer orgId) {
		ResultInfo<User> result = new ResultInfo<>();
		List<User> userList = userService.getUserListByOrgId(orgId);
		result.setCode(0);
		result.setMessage("操作成功");
		result.setListData(userList);
		return result;
	}

	/**
	 * <b>Description:</b><br>
	 * 个人设置
	 * 
	 * @param request
	 * @param user
	 * @param session
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年6月28日 上午10:23:11
	 */
	@ResponseBody
	@RequestMapping(value = "myinfo")
	public ModelAndView myInfo(HttpServletRequest request, User user, HttpSession session) {
		ModelAndView mv = new ModelAndView("system/user/my_info");
		User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
		if (StringUtils.isEmpty(user) || null == user.getUserId() || user.getUserId() <= 0
				|| !user.getUserId().equals(session_user.getUserId())) {
			return pageNotFound();
		}

		User userInfo = userService.getUserById(user.getUserId());

		if (null == userInfo) {
			return pageNotFound();
		}
		// 地区
		if (!StringUtils.isEmpty(userInfo.getUserAddress()) && null != userInfo.getUserAddress().getAreaId()
				&& userInfo.getUserAddress().getAreaId() > 0) {
			Integer areaId = userInfo.getUserAddress().getAreaId();
			String region = (String) regionService.getRegion(areaId, 2);

			mv.addObject("region", region);
		}

		// 角色处理
		List<Role> userRoles = roleService.getUserRoles(user.getUserId());

		mv.addObject("userRoles", userRoles);
		mv.addObject("user", userInfo);
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 修改密码
	 * 
	 * @param request
	 * @param user
	 * @param session
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年6月28日 上午10:56:15
	 */
	@ResponseBody
	@RequestMapping(value = "modifypassword")
	public ModelAndView modifyPassword(HttpServletRequest request, User user, HttpSession session) {
		ModelAndView mv = new ModelAndView("system/user/modify_password");
		User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
		if (StringUtils.isEmpty(user) || null == user.getUserId() || user.getUserId() <= 0
				|| !user.getUserId().equals(session_user.getUserId())) {
			return pageNotFound();
		}
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 保存修改密码
	 * 
	 * @param request
	 * @param user
	 * @param session
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年6月28日 上午11:32:22
	 */
	@ResponseBody
	@RequestMapping(value = "savemodifypassword")
	@SystemControllerLog(operationType = "edit",desc = "保存修改密码")
	public ResultInfo<?> saveModifyPassword(HttpServletRequest request, User user, HttpSession session) {
		User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
		ResultInfo resultInfo = new ResultInfo<>();
		if (StringUtils.isEmpty(user) || null == user.getUserId() || user.getUserId() <= 0
				|| !user.getUserId().equals(session_user.getUserId())) {
			return resultInfo;
		}
		// 旧密码是否正确
		User userInfo = userService.getUserById(user.getUserId());
		if (!DigestUtils.md5Hex(request.getParameter("oldpassword") + userInfo.getSalt()).toString()
				.equals(userInfo.getPassword())) {
			resultInfo.setMessage("旧密码不正确");
			return resultInfo;
		}
		// 修改密码
		Salt salt = new Salt();
		String p_salt = salt.createSalt(false);
		user.setSalt(p_salt);
		user.setPassword(DigestUtils.md5Hex(user.getPassword() + p_salt).toString());

		int res = userService.modifyPassowrd(user);
		if (res > 0) {
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
		}
		return resultInfo;
	}
	
	/** 
	 * <b>Description:</b>配置列表字段
	 * @return ModelAndView
	 * @Note
	 * <b>Author：</b> lijie
	 * <b>Date:</b> 2019年3月12日 下午1:47:32
	 */
	@ResponseBody
	@RequestMapping(value = "/define")
	public ModelAndView define(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("system/user/define");
		return modelAndView;
	}

	/**
	  * ids -> names
	  * @author wayne.liu
	  * @date  2019/6/12 14:59
	  * @param
	  * @return
	  */
	private List<String> getSystems(List<String> systems){
		List<Platform> platforms = platformService.queryList();
		List<String> showSystems = new ArrayList<>();
		for(Platform platform : platforms){
			if(systems.contains(String.valueOf(platform.getPlatformId()))){
				showSystems.add(platform.getPlatformName());
			}
		}
		return showSystems;
	}
	/**
	 * <b>Description:</b>根据手机号查询手机用户是否是老用户  是修改申请加入贝登精选的状态
	 * @return resultInfo
	 * @Note
	 * <b>Author：</b> Addis
	 * <b>Date:</b> 2019年7月1日 下午1:47:32
	 */

	@ResponseBody
	@RequestMapping(value = "/updateIsVedengState",method = RequestMethod.POST)
	public ResultInfo updateIsVedengState(@RequestBody(required = false) WebAccount webAccount){

		try {
			logger.info("updateIsVedengState:接收到数据" + DateUtil.getNowDate("yyyy-MM-dd HH:mm:ss") + JSONObject.toJSONString(webAccount));
		int modelNumCount = apiSoap.getMobileCount(webAccount);    //根据手机号查询是否有该用户
		if (modelNumCount >= DAFULT_ID_YES) {
			WebAccount webAccountRecond = apiSoap.selectMobileResult(webAccount);    //根据手机号查询该用户失效状态，是否申请加入贝登精选，是否是会员
			if (webAccountRecond.getIsEnable() == DAFULT_ID_NO) {
			return new ResultInfo(-1, "该用户已失效", JSON.toJSON(webAccountRecond));
			}
			if (webAccountRecond.getIsEnable() == DAFULT_ID_YES) {                //判断是否有效
			if (webAccountRecond.getIsVedengJoin() != null && webAccountRecond.getIsVedengJoin() == DAFULT_ID_YES) {
				if (webAccountRecond.getWebAccountId().equals(webAccount.getWebAccountId())){
					apiSoap.updateisVedengJoin(webAccount);
				}
			return new ResultInfo(-1, "该用户已申请", JSON.toJSON(webAccountRecond));
			} else {
				webAccount.setModTimeJoin(DateUtil.gainNowDate());
				if (webAccountRecond.getWebAccountId().equals(webAccount.getWebAccountId())){
					if (apiSoap.updateisVedengJoin(webAccount) > DAFULT_ID_NO) { //如果有该用户，则更改该用户申请加入状态
						WebAccount webAccountThree = apiSoap.selectMobileResult(webAccount);
						return new ResultInfo(0, "返回数据成功", JSON.toJSON(webAccountThree));
					}
				}
			}
			}
		} else {
			//注册平台默认为贝登医疗
			if (StringUtils.isEmpty(webAccount.getRegisterPlatform())){
				webAccount.setRegisterPlatform(1);
			}
			webAccount.setBelongPlatform(webAccountService.getBelongPlatformOfAccount(webAccount.getUserId(),webAccount.getTraderId(),webAccount.getRegisterPlatform()));
			webAccount.setModTimeJoin(DateUtil.gainNowDate());
			webAccount.setIsEnable(DAFULT_ID_YES);
			webAccount.setAddTime(DateUtil.gainNowDate());
			if (apiSoap.insert(webAccount) > DAFULT_ID_NO) {     //如果没有该用户，则添加一条新用户
			WebAccount webAccountFour = apiSoap.selectMobileResult(webAccount);
			return new ResultInfo(0, "返回数据成功", JSON.toJSON(webAccountFour));
			}
		}
	}catch (Exception e){
		   logger.error("execute fail",e);
	}
		   return new ResultInfo(-1,"执行失败");
	}

	/**
	 * 贝登、科研购账号同步接口
	 * <b>Description:</b>根据手机号查询手机用户是否是老用户
	 * @return resultInfo
	 * @Note
	 * <b>Author：</b> Addis
	 * <b>Date:</b> 2019年7月1日 下午1:47:32
	 */

	@ResponseBody
	@RequestMapping(value = "/doJxAcountData",method = RequestMethod.POST)
	public ResultInfo doJxAcountData(@RequestBody(required = false) WebAccount webAccount){
		logger.info("doJxAcountData:接收到数据" + DateUtil.getNowDate("yyyy-MM-dd HH:mm:ss") + JSONObject.toJSONString(webAccount));
		int modelNumCount = apiSoap.getMobileCount(webAccount);
		    //根据手机号查询该用户失效状态，是否申请加入贝登精选，是否是会员
		Integer result = 0;
		if (modelNumCount >= DAFULT_ID_YES) {
			//判断账号是否来自同一平台，只有来自同一平台才去更新账号信息
			WebAccount webAccountRecond = apiSoap.selectMobileResult(webAccount);
			if (webAccountRecond.getWebAccountId().equals(webAccount.getWebAccountId())){
				result = apiSoap.updateisVedengJoin(webAccount);
			}
		}else {
			webAccount.setIsEnable(DAFULT_ID_YES);
			webAccount.setAddTime(DateUtil.gainNowDate());
			//默认注册平台为贝登医疗
			if (StringUtils.isEmpty(webAccount.getRegisterPlatform())){
				webAccount.setRegisterPlatform(1);
			}
			webAccount.setBelongPlatform(webAccountService.getBelongPlatformOfAccount(webAccount.getUserId(),webAccount.getTraderId(),webAccount.getRegisterPlatform()));
			result = apiSoap.insert(webAccount);
		}
		if (result > 0){
			return new ResultInfo(0, "操作成功");
		}
		return new ResultInfo(-1,"执行失败");
	}
    /** 
    * @Description: 发送申请通过消息 
    * @Param: [webAccountRecond] 
    * @return: void 
    * @Author: addis
    * @Date: 2019/9/27 
    */ 
/*	public  void booleanSend(WebAccount webAccountRecond){
		if(webAccountRecond.getIsSendMessage()==0){
			Boolean flag=passReminderMsg(webAccountRecond);
			if(flag==true){
				WebAccount webAccount1=new WebAccount();
				webAccount1.setMobile(webAccountRecond.getMobile());
				webAccount1.setIsSendMessage(1);
				apiSoap.updateisVedengJoin(webAccount1);
			}
		}
	}
}*/
}