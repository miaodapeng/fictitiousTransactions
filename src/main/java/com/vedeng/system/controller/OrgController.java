package com.vedeng.system.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vedeng.authorization.model.Organization;
import com.vedeng.authorization.model.Position;
import com.vedeng.authorization.model.User;
import com.vedeng.common.annotation.SystemControllerLog;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.shiro.JedisUtils;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.common.validator.FormToken;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.system.service.OrgService;
import com.vedeng.system.service.PositService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * <b>Description:</b><br> 部门管理
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.system.controller
 * <br><b>ClassName:</b> OrgController
 * <br><b>Date:</b> 2017年4月25日 上午11:18:20
 */
@Controller()
@RequestMapping("/system/org")
public class OrgController extends BaseController {
	@Autowired
	@Qualifier("orgService")
	private OrgService orgService;
	
	@Autowired
	@Qualifier("positService")
	private PositService positService;
	
	/**
	 * <b>Description:</b><br> 部门列表 
	 * @param request 请求
	 * @param org 部门bean
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月25日 上午11:18:32
	 */
	@ResponseBody
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request, Organization org, HttpSession session) {
		User user =(User)session.getAttribute(ErpConst.CURR_USER);
		List<Organization> orgList = orgService.getOrgList(0, user.getCompanyId(), false);
		if(orgList.size() > 0){
			for(Organization o : orgList){
//				if (JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_OBJECT + o.getType())) {
//					JSONObject jsonObject = JSONObject
//							.fromObject(JedisUtils.get(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_OBJECT + o.getType()));
//					SysOptionDefinition sod = (SysOptionDefinition) JSONObject.toBean(jsonObject,
//							SysOptionDefinition.class);
//					o.setTypeName(sod.getTitle());
//				}
				SysOptionDefinition sysOptionDefinition = getSysOptionDefinition(o.getType());
				if(null != sysOptionDefinition){
					o.setTypeName(getSysOptionDefinition(o.getType()).getTitle());
				}
			}
		}
		
		ModelAndView mav=new ModelAndView();
		mav.addObject("orgList", orgList);
		mav.setViewName("system/org/index");
        return mav;
	}
	
	/**
	 * <b>Description:</b><br> 新增/编辑部门
	 * @param request 请求
	 * @param org 部门bean
	 * @param session
	 * @return
	 * @throws IOException 
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月25日 上午11:18:53
	 */
	@FormToken(save=true)
	@ResponseBody
	@RequestMapping("/modifyorg")
	public ModelAndView modifyOrg(HttpServletRequest request, Organization org, HttpSession session) throws IOException{
		ModelAndView mav=new ModelAndView();
		//编辑
		if(!StringUtils.isEmpty(org)
				&& null != org.getOrgId()
				&& org.getOrgId() > 0){
			Organization orgInfo = orgService.getOrgByOrgId(org.getOrgId());
			
			mav.addObject("orgInfo", orgInfo);
			mav.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(orgInfo)));
		}
		//获取部门
		User user =(User)session.getAttribute(ErpConst.CURR_USER);
		List<Organization> orgList = orgService.getOrgList(0,user.getCompanyId(), true);
		mav.addObject("orgList", orgList);
		
		//获取类型
//		if (JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST + SysOptionConstant.ID_309)) {
//			String strJson = JedisUtils.get(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST + SysOptionConstant.ID_309);
//			JSONArray jsonArray = JSONArray.fromObject(strJson);
//			List<SysOptionDefinition> listType = (List<SysOptionDefinition>) JSONArray.toCollection(jsonArray,
//					SysOptionDefinition.class);
//
//			mav.addObject("listType", listType);
//		}
		mav.addObject("listType", getSysOptionDefinitionList(SysOptionConstant.ID_309));
		mav.setViewName("system/org/modify_org");
        return mav;
	}
	
	/**
	 * <b>Description:</b><br> 保存新增部门
	 * @param org
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年9月12日 下午4:17:58
	 */
	@FormToken(remove=true)
	@ResponseBody
	@RequestMapping("/saveaddorg")
	@SystemControllerLog(operationType = "add",desc = "保存新增部门")
	public ResultInfo<Organization> saveAddOrg(Organization org, HttpSession session){
		ResultInfo<Organization> resultInfo = new ResultInfo<Organization>();
		User user =(User)session.getAttribute(ErpConst.CURR_USER);
		if(user!=null){
			org.setCreator(user.getUserId());
			org.setAddTime(DateUtil.sysTimeMillis());
			
			org.setUpdater(user.getUserId());
			org.setModTime(DateUtil.sysTimeMillis());
		}
		Organization o = new Organization();
		o.setParentId(org.getParentId());
		o.setCompanyId(user.getCompanyId());
		o.setOrgName(org.getOrgName());
		Organization info = orgService.getOrg(o);
		if(null != info){
			resultInfo.setMessage("部门名称不允许重复");
			return resultInfo;
		}
		try {
			Integer res = orgService.modifyOrg(org,session);
			if(res > 0){
				resultInfo.setCode(0);
				resultInfo.setMessage("操作成功");
			}
		} catch (Exception e) {
			logger.error("org saveaddorg:", e);
		}
		return resultInfo;
	}
	
	/**
	 * <b>Description:</b><br> 保存编辑部门
	 * @param org 部门bean
	 * @param session
	 * @return ResultInfo<Organization> json
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月25日 上午11:19:26
	 */
	@ResponseBody
	@RequestMapping("/saveeditorg")
	@SystemControllerLog(operationType = "edit",desc = "保存编辑部门")
	public ResultInfo<Organization> saveEditOrg(Organization org, HttpSession session,String beforeParams){
		ResultInfo<Organization> resultInfo = new ResultInfo<Organization>();
		User user =(User)session.getAttribute(ErpConst.CURR_USER);
		if(user!=null){
			org.setCreator(user.getUserId());
			org.setModTime(DateUtil.sysTimeMillis());
			
			org.setUpdater(user.getUserId());
			org.setModTime(DateUtil.sysTimeMillis());
		}
		Organization o = new Organization();
		o.setParentId(org.getParentId());
		o.setCompanyId(user.getCompanyId());
		o.setOrgName(org.getOrgName());
		Organization info = orgService.getOrg(o);
		if(null != org.getOrgId() 
				&& org.getOrgId() > 0
				&& null != info
				&& !info.getOrgId().equals(org.getOrgId())){
			resultInfo.setMessage("部门名称不允许重复");
			return resultInfo;
		}
		try {
			Integer res = orgService.modifyOrg(org,session);
			if(res > 0){
				resultInfo.setCode(0);
				resultInfo.setMessage("操作成功");
			}
		} catch (Exception e) {
			logger.error("org saveeditorg:", e);
		}
		return resultInfo;
	}
	
	/**
	 * <b>Description:</b><br> 删除部门（职位不为空，无法删除！）
	 * @param org 部门bean
	 * @param session
	 * @return ResultInfo<Organization> json
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月25日 上午11:20:11
	 */
	@ResponseBody
	@RequestMapping(value="deleteorg")
	@SystemControllerLog(operationType = "delete",desc = "删除部门")
	public ResultInfo<Organization> deleteOrg(Organization org, HttpSession session){
		User user =(User)session.getAttribute(ErpConst.CURR_USER);
		ResultInfo<Organization> resultInfo = new ResultInfo<Organization>();
		List<Integer> orgIds = new ArrayList<Integer>();
		orgIds.add(org.getOrgId());
		//当前部门及子集职位是否为空
		List<Organization> orgList = orgService.getOrgList(org.getOrgId(),user.getCompanyId(),false);
		if(null != orgList){
			for(Organization o : orgList){
				orgIds.add(o.getOrgId());
			}
		}
		
		List<Position> positList = positService.getPositByOrgIds(orgIds);
		if(!positList.isEmpty()){
			resultInfo.setMessage("该部门/下级部门下的职位不为空，无法删除");
			return resultInfo;
		}
		
		Integer res = orgService.deleteOrgByOrgId(orgIds);
		if(res > 0){
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
		}
		return resultInfo;
	}
}
