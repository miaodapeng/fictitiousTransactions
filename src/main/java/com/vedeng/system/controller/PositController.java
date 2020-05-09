package com.vedeng.system.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vedeng.authorization.model.Organization;
import com.vedeng.authorization.model.Position;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.shiro.JedisUtils;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.validator.FormToken;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.system.service.OrgService;
import com.vedeng.system.service.PositService;
import com.vedeng.system.service.UserService;

import net.sf.json.JSONArray;

/**
 * <b>Description:</b><br> 职位管理
 * @author duke
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.system.controller
 * <br><b>ClassName:</b> PositController
 * <br><b>Date:</b> 2017年4月25日 下午6:26:41
 */
@Controller
@RequestMapping("/system/posit")
public class PositController extends BaseController{

	@Autowired
	@Qualifier("positService")
	private PositService positService;
	
	@Autowired
	@Qualifier("orgService")
	private OrgService orgService;//自动注入orgService
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	/**
	 * <b>Description:</b><br> 职位列表展示
	 * @param request
	 * @param position
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午6:26:56
	 */
	@ResponseBody
	@RequestMapping(value="/index")
	public ModelAndView index(HttpServletRequest request,Position position,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,  //required = false:可不传入pageNo这个参数，true必须传入，defaultValue默认值，若无默认值，使用Page类中的默认值
            @RequestParam(required = false) Integer pageSize,
            HttpSession session){
		ModelAndView mv = new ModelAndView();
		User user =(User)session.getAttribute(ErpConst.CURR_USER);
		//查询集合        
        Page page = getPageTag(request,pageNo,pageSize);
        Organization o = new Organization();
        o.setCompanyId(user.getCompanyId());
        position.setOrganization(o);
        List<Position> positionList = positService.querylistPage(position, page);
        
        if(null != positionList && positionList.size() > 0){
			for(Position p : positionList){
				if(null != p.getType() && p.getType() > 0){
					SysOptionDefinition sysOptionDefinition = getSysOptionDefinition(p.getType());
					if(null != sysOptionDefinition){
						p.setTypeName(sysOptionDefinition.getTitle());
					}
				}
				if(null != p.getLevel() && p.getLevel() > 0){
					SysOptionDefinition sysOptionDefinition = getSysOptionDefinition(p.getLevel());
					if(null != sysOptionDefinition){
						p.setLevelName(sysOptionDefinition.getTitle());
					}
				}
			}
		}
        
        //获取部门
  		List<Organization> orgList = orgService.getOrgList(0,user.getCompanyId(), true);
        
        //页面传值
        mv.addObject("position", position);
        mv.addObject("orgList", orgList);
        
        mv.addObject("positionList",positionList);  
        mv.addObject("page", page);
		mv.setViewName("system/posit/index");
		return mv;
	}
	

	/**
	 * <b>Description:</b><br> 根据部门获取部门职位
	 * @param position
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午6:27:10
	 */
	@ResponseBody
	@RequestMapping("getposit")
	public ResultInfo<Position> getPosit(Position position){
		List<Position> positList = positService.getPositByOrgId(position.getOrgId());
		ResultInfo<Position> result = new ResultInfo<Position>();
		if(positList != null){
			result.setCode(0);
			result.setMessage("操作成功");
			result.setListData(positList);
		}
		return result;
	}
	
	/**
	 * <b>Description:</b><br> 添加职位信息初始化 
	 * @param request
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午6:27:20
	 */
	@FormToken(save=true)
	@RequestMapping(value="/addposition", method = RequestMethod.GET)
	public ModelAndView addPost(HttpServletRequest request,HttpSession session){
		User user =(User)session.getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView();
		//获取部门
  		List<Organization> orgList = orgService.getOrgList(0,user.getCompanyId(),true);
  		mv.addObject("orgList", orgList);
  		List<SysOptionDefinition> list = positService.getPositType(1034);
  		mv.addObject("listType", list);
  		//职位级别
		List<SysOptionDefinition> positLevelList = getSysOptionDefinitionList(440);
//		if(JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST + 440)){
//			String json_result = JedisUtils.get(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST + 440);
//			JSONArray jsonArray = JSONArray.fromObject(json_result);
//			positLevelList = (List<SysOptionDefinition>) JSONArray.toCollection(jsonArray, SysOptionDefinition.class);
//		}
		mv.addObject("positLevelList", positLevelList);
  		
		mv.setViewName("system/posit/add_posit");
		return mv; 
	}
	
	/**
	 * <b>Description:</b><br> 根据主键查询职位详细信息 
	 * @param request
	 * @param position
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午6:27:29
	 */
	@ResponseBody
	@RequestMapping(value = "getpositionbykey")
	public Position getPositionByKey(HttpServletRequest request, Position position){
		return positService.getPositionByKey(position);
	}
	
	/**
	 * <b>Description:</b><br> 新增职位数据保存
	 * @param request
	 * @param position
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午6:27:37
	 */
	@FormToken(remove=true)
	@ResponseBody
	@RequestMapping(value = "/saveposition")
	public ResultInfo<?> savePosition(HttpServletRequest request, Position position){
		User user= (User) request.getSession().getAttribute(Consts.SESSION_USER);
		if(user!=null){
			position.setCreator(user.getUserId());
			position.setAddTime(DateUtil.sysTimeMillis());
			
			position.setUpdater(user.getUserId());
			position.setModTime(DateUtil.sysTimeMillis());
		}
		return positService.insert(position);
	}

	
	/**
	 * <b>Description:</b><br> 编辑职位信息管理初始化
	 * @param request
	 * @param position
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午6:27:46
	 */
	@RequestMapping(value="/editposition")
	public ModelAndView editPosition(HttpServletRequest request, Position position,HttpSession session){
		User user =(User)session.getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("system/posit/edit_posit");
		position = positService.getPositionByKey(position);
		if(position!=null){
			mv.addObject(position);
		}
		//获取部门
  		List<Organization> orgList = orgService.getOrgList(0,user.getCompanyId(),true);
  		mv.addObject("orgList", orgList);
  		List<SysOptionDefinition> list = positService.getPositType(1034);
  		mv.addObject("listType", list);
  		
  		//职位级别
		List<SysOptionDefinition> positLevelList = getSysOptionDefinitionList(440);
//		if(JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST + 440)){
//			String json_result = JedisUtils.get(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST + 440);
//			JSONArray jsonArray = JSONArray.fromObject(json_result);
//			positLevelList = (List<SysOptionDefinition>) JSONArray.toCollection(jsonArray, SysOptionDefinition.class);
//		}
		mv.addObject("positLevelList", positLevelList);
  		
		return mv; 
	}
	
	/**
	 * <b>Description:</b><br> 删除职位（如果职位下有员工，不能删除）
	 * @param posit 职位bean
	 * @return ResultInfo<Position>
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月25日 下午1:00:21
	 */
	@ResponseBody
	@RequestMapping(value="deleteposit")
	public ResultInfo<Position> deletePosit(HttpServletRequest request,Position posit){
		ResultInfo<Position> resultInfo = new ResultInfo<Position>();
		List<User> userList = userService.getUserByPositId(posit.getPositionId());
		if(userList.size() > 0){
			resultInfo.setMessage("该职位员工不为空，无法删除");
			return resultInfo;
		}
		User user= (User) request.getSession().getAttribute(Consts.SESSION_USER);
		if(user!=null){
			posit.setUpdater(user.getUserId());
			posit.setModTime(DateUtil.sysTimeMillis());
		}
		Integer res = positService.deletePosit(posit);
		if(res > 0){
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
		}
		return resultInfo;
	}
	
	/**
	 * <b>Description:</b><br> 修改职位信息
	 * @param request
	 * @param position
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月28日 下午2:25:23
	 */
	@ResponseBody
	@RequestMapping(value = "/updateposition")
	public ResultInfo<?> updatePosition(HttpServletRequest request, Position position){
		User user= (User) request.getSession().getAttribute(Consts.SESSION_USER);
		if(user!=null){
			position.setUpdater(user.getUserId());
			position.setModTime(DateUtil.sysTimeMillis());
		}
		return positService.update(position);
	}
}
