package com.vedeng.system.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.vedeng.authorization.model.*;
import com.vedeng.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.vedeng.common.annotation.SystemControllerLog;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.model.QRCode;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.JsonUtils;


/**
 * <b>Description:</b><br> 节点管理
 * @author duke
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.system.controller
 * <br><b>ClassName:</b> ActionController
 * <br><b>Date:</b> 2017年4月25日 下午6:16:19
 */
@Controller
@RequestMapping("/system/action")
public class ActionController extends BaseController{
//	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	@Qualifier("actionService")
	private ActionService actionService;
	
	@Autowired
	@Qualifier("actiongroupService")
	private ActiongroupService actiongroupService;
	
	@Autowired
	@Qualifier("roleService")
	private RoleService roleService;

	@Autowired
	@Qualifier("platformService")
	private PlatformService platformService;
	
	/**
	 * <b>Description:</b><br> 查询节点列表数据
	 * @param request
	 * @param action
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午6:16:41
	 */
	@ResponseBody
    @RequestMapping(value = "/querylistpage")
	public ModelAndView querylistPage(HttpServletRequest request, Action action,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,  //required = false:可不传入pageNo这个参数，true必须传入，defaultValue默认值，若无默认值，使用Page类中的默认值
            @RequestParam(required = false) Integer pageSize) {
        ModelAndView mv = new ModelAndView();         
        try {
        	List<Actiongroup> groupList = actiongroupService.getActionGroupList();
        	mv.addObject("actiongroupList",groupList);
			List<Platform> platforms = platformService.queryList();
			mv.addObject("platformsList", platforms);

			Map<Integer,String> map = platforms.stream().collect(Collectors.toMap(Platform::getPlatformId, Platform::getPlatformName));

			//查询集合
			mv.addObject("action", action);
			mv.setViewName("system/action/main_action");
			Page page = getPageTag(request,pageNo,pageSize);
			List<Action> actionList = actionService.querylistpage(action, page);

			actionList.forEach(e->{
				e.setPlatformName(map.get(e.getPlatformId()));
			});

			mv.addObject("actionList",actionList);  
			mv.addObject("page", page);



		} catch (Exception e) {
			logger.error("action querylistpage:", e);
		}
        return mv;  
	}
	
	/**
	 * <b>Description:</b><br> 添加节点初始化
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午6:16:52
	 */
	@RequestMapping(value="/addAction", method = RequestMethod.GET)
	public ModelAndView addAction(){
		ModelAndView mv = new ModelAndView();
		List<Actiongroup> groupList = actiongroupService.getActionGroupList();
		mv.addObject("groupList", groupList);
		List<Platform> platforms = platformService.queryList();
		mv.addObject("platformsList", platforms);
		mv.setViewName("system/action/add_action");
		return mv; 
	}
	
	/**
	 * <b>Description:</b><br> 根据主键查询节点详细信息
	 * @param actionId
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午6:17:06
	 */
	@ResponseBody
	@RequestMapping(value = "getActionByKey")
	public Action getActionByKey(Integer actionId){
		return actionService.getActionByKey(actionId);
	}
	
	/**
	 * <b>Description:</b><br> 添加节点保存数据
	 * @param action
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午6:17:16
	 */
	@ResponseBody
	@RequestMapping(value = "/saveAction")
	@SystemControllerLog(operationType = "add",desc = "保存节点保存数据")
	public ResultInfo<?> saveAction(HttpSession session, Action action){
		User user =(User)session.getAttribute(ErpConst.CURR_USER);
		ResultInfo<?> result = new ResultInfo<>();
		if(user!=null){
			action.setCreator(user.getUserId());
			action.setModTime(DateUtil.sysTimeMillis());
			
			action.setUpdater(user.getUserId());
			action.setModTime(DateUtil.sysTimeMillis());
		}
		int i = actionService.insert(action);
		if(i==1){
			result.setCode(0);result.setMessage("操作成功");
			super.delMenuRedis(action.getActionId(), null, null,user);
		}
		return result;
	}

	
	/**
	 * <b>Description:</b><br> 编辑节点管理初始化
	 * @param action
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午6:17:27
	 */
	@RequestMapping(value="/editAction")
	public ModelAndView editAction(Action action){
		ModelAndView mv = new ModelAndView();
		try {
			mv.setViewName("system/action/edit_action");
			action = actionService.getActionByKey(action.getActionId());
			mv.addObject(action);
			//日志
			mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(action)));
			List<Actiongroup> groupList = actiongroupService.getActionGroupList();
			mv.addObject("groupList", groupList);
			List<Platform> platforms = platformService.queryList();
			mv.addObject("platformsList", platforms);
		} catch (Exception e) {
			logger.error("editAction:", e);
		}
		return mv; 
	}
	
	/**
	 * <b>Description:</b><br> 修改节点信息
	 * @param action
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午6:17:53
	 */
	@ResponseBody
	@RequestMapping(value = "/updateAction")
	@SystemControllerLog(operationType = "edit",desc = "保存节点保存数据")
	public ResultInfo<?> updateAction(HttpSession session, Action action){
		User user =(User)session.getAttribute(ErpConst.CURR_USER);
		ResultInfo<?> result = new ResultInfo<>();
		int i = actionService.update(action);
		if(i==1){
			result.setCode(0);result.setMessage("操作成功");
			super.delMenuRedis(action.getActionId(), null, null,user);
		}
		return result;
	}
	
	/**
	 * <b>Description:</b><br> 删除分组
	 * @param action 功能bean
	 * @return ResultInfo<Action>
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月25日 下午1:43:22
	 */
	@ResponseBody
	@RequestMapping(value="deleteaction")
	@SystemControllerLog(operationType = "delete",desc = "删除分组")
	public ResultInfo<Action> deleteAction(Action action){
		ResultInfo<Action> resultInfo = new ResultInfo<Action>();
		
		List<Role> roleList = roleService.getRoleByActionId(action.getActionId());
		if(roleList.size() > 0){
			resultInfo.setMessage("该功能分组正在使用，无法删除");
			return resultInfo;
		}
		Integer res = actionService.delete(action);
		if(res > 0){
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
		}
		return resultInfo;
	}

	@Autowired
	@Qualifier("ftpUtilService")
	private FtpUtilService ftpUtilService;
	
	/**
	 * <b>Description:</b><br> 測試生成二維碼
	 * @param action
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年8月8日 上午9:42:53
	 */
	@ResponseBody
	@RequestMapping(value="testQRcu")
	public ResultInfo<?> testQRcu(Action action){
		ResultInfo<QRCode> result = new ResultInfo<QRCode>();
		try {
			QRCode qrCode = new QRCode();
			qrCode.setContent("www.baidu.com");
			
			List<QRCode> list = new ArrayList<>();
			list.add(qrCode);
			//二維碼內容，保存地址，文件名稱（無logo）
			result = ftpUtilService.makeQRCode(list,"QRCode/Image");
			if(result.getCode()==0){
				System.out.println("result:"+JSON.toJSONString(result.getListData()));
			}
			
			
			qrCode.setLogoUrl("E://123.jpg");
			List<QRCode> logo_list = new ArrayList<>();
			logo_list.add(qrCode);
			//二維碼內容，保存地址，文件名稱，logo圖片地址，是否压缩logo（有logo）
			result = ftpUtilService.makeLogoQRCode(logo_list,"QRCode/Image",true);
			if(result.getCode()==0){
				System.out.println("result:"+JSON.toJSONString(result.getListData()));
			}
		} catch (Exception e) {
			logger.error("testQRcu:", e);
			return result;
		}
		return result;
	}
}
