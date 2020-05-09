package com.vedeng.logistics.controller;



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

import com.vedeng.authorization.model.Region;
import com.vedeng.authorization.model.User;
import com.vedeng.common.annotation.SystemControllerLog;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.logistics.model.StorageArea;
import com.vedeng.logistics.model.StorageRack;
import com.vedeng.logistics.model.StorageRoom;
import com.vedeng.logistics.model.Warehouse;
import com.vedeng.logistics.service.StorageareaService;
import com.vedeng.logistics.service.StorageroomService;
import com.vedeng.system.service.UserService;

import net.sf.json.JSONObject;

/**
 * 
 * <b>Description:</b><br> 货区管理
 * @author scott
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.warehouse.controller
 * <br><b>ClassName:</b> StorageAreaController
 * <br><b>Date:</b> 2017年7月21日 上午8:57:25
 */
@Controller
@RequestMapping("warehouse/storageAreaMag")
public class StorageAreaController extends BaseController {
	
	@Autowired
	@Qualifier("storageAreaService")
	private StorageareaService storageareaService;
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;//自动注入userService
	
	@Autowired
	@Qualifier("storageroomService")
	private StorageroomService storageroomService;
	
	/**
	 * 
	 * <b>Description:</b><br> 货区列表
	 * @param request
	 * @param warehouses
	 * @param pageNo
	 * @param pageSize
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月19日 下午2:28:25
	 */
	@ResponseBody
	@RequestMapping(value="/index")
	public ModelAndView index(HttpServletRequest request,StorageArea storageArea,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo, 
			@RequestParam(required = false) Integer pageSize,HttpSession session) {
		User session_user =(User)session.getAttribute(ErpConst.CURR_USER);
		Page page = getPageTag(request,pageNo,pageSize);
		ModelAndView mv = new ModelAndView();
		
		//根据公司id获取所属库房
		StorageRoom storageroom = new StorageRoom();
		storageroom.setCompanyId(session_user.getCompanyId());
		List<StorageRoom> listRoom = storageareaService.getStorageRoomList(storageroom);
		
		storageArea.setCompanyId(session_user.getCompanyId());
		Map<String,Object> map = storageareaService.getStorageAreaList(storageArea,page);
		List<StorageArea> list = (List<StorageArea>) map.get("list");
		for (StorageArea storageArea2 : list) {
			storageArea2.setCompanyId(session_user.getCompanyId());
			storageArea2.setCnt(storageareaService.getGoodsNum(storageArea2).getCnt());
		}
		mv.addObject("storageArea",storageArea);
		mv.addObject("storageAreaList",list);
		mv.addObject("listRoom",listRoom);
		mv.addObject("page", (Page)map.get("page"));
		mv.setViewName("logistics/storageAreaMag/storagearea_index");
		return mv;
	}
	/**
	 * 
	 * <b>Description:</b><br> 新增货区页面跳转
	 * @param storageroom
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月21日 下午1:01:56
	 */
	@ResponseBody
	@RequestMapping(value = "/addStorageArea")
	public ModelAndView addStorageArea(StorageRoom storageroom,HttpSession session) {
		User session_user =(User)session.getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView();
		//获取公司id获取所属仓库
		Warehouse warehouses = new Warehouse();
		warehouses.setCompanyId(session_user.getCompanyId());
		List<Warehouse> warehouseList = storageroomService.getWarehouseByCompanyId(warehouses);
		mv.addObject("warehouseList",warehouseList);
		mv.setViewName("logistics/storageAreaMag/add_storagearea");
		return mv;
	}
	/**
	 * 
	 * <b>Description:</b><br> 新增货区
	 * @param request
	 * @param session
	 * @param storageArea
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月21日 下午4:33:25
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/saveStorageArea")
	@SystemControllerLog(operationType = "add",desc = "保存新增货区")
	public ModelAndView saveStorageRoom(HttpServletRequest request, HttpSession session, StorageArea storageArea) {
		ModelAndView mav = new ModelAndView();
		ResultInfo rs  = storageareaService.saveStorageArea(storageArea, request, session);
		if (null != rs &&rs.getCode()==0) {
			JSONObject json=JSONObject.fromObject(rs.getData());
			StorageArea sr=(StorageArea) JSONObject.toBean(json, StorageArea.class);
			mav.addObject("refresh", "true_false_true");
			return success(mav);
		} else {
			return fail(mav);
		}
	}
	/**
	 * 
	 * <b>Description:</b><br> 货区的详细信息
	 * @param storageArea
	 * @param request
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月21日 下午4:57:02
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/toStorageAreaDetailPage")
	public ModelAndView toWarehouseDetailPage(StorageArea storageArea, HttpServletRequest request,HttpSession session) {
		ModelAndView mav = new ModelAndView("logistics/storageAreaMag/view_StorageareaDetail");
		//货区的详细信息
		StorageArea sr = storageareaService.getStorageareaById(storageArea);
		//获取创建人
		sr.setCreatorName(userService.getUserById(sr.getCreator()).getUsername());
		//查询货区下的商品数量
		StorageArea sa = storageareaService.getGoodsNum(storageArea);
		if(sa!=null && sa.getCnt()>0){
			sr.setCnt(sa.getCnt());
		}else{
			sr.setCnt(0);
		}
		//获取货架列表
		List<StorageRack> storageRackList = storageareaService.getStorageRackByRId(storageArea);
		mav.addObject("storageRackList",storageRackList);
		mav.addObject("storageArea",sr);
		return mav;
	}
	/**
	 * 
	 * <b>Description:</b><br> 跳转到编辑页面
	 * @param storageArea
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月21日 下午5:47:10
	 */
	@ResponseBody
	@RequestMapping(value = "/editStorageAreaJump")
	public ModelAndView editStorageAreaJump(StorageArea storageArea,HttpSession session) {
		User session_user =(User)session.getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView();
		StorageArea sr = storageareaService.getStorageareaById(storageArea);
		//获取公司id获取所属仓库
		Warehouse warehouses = new Warehouse();
		warehouses.setCompanyId(session_user.getCompanyId());
		List<Warehouse> warehouseList = storageroomService.getWarehouseByCompanyId(warehouses);
		//根据公司id获取所属库房
		StorageRoom storageroom = new StorageRoom();
		storageroom.setCompanyId(session_user.getCompanyId());
		List<StorageRoom> listRoom = storageareaService.getStorageRoomList(storageroom);
		mv.addObject("warehouseList",warehouseList);
		mv.addObject("listRoom",listRoom);
		mv.addObject("storageArea",sr);
		try {
			mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(storageArea)));
		} catch (Exception e) {
			logger.error("editStorageAreaJump:", e);
		}
		mv.setViewName("logistics/storageAreaMag/edit_storagearea");
		return mv;
	}
	/**
	 * 
	 * <b>Description:</b><br> 编辑货区
	 * @param request
	 * @param session
	 * @param storageArea
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月21日 下午5:54:57
	 */
	@ResponseBody
	@RequestMapping(value = "/editStorageArea")
	@SystemControllerLog(operationType = "edit",desc = "保存编辑货区")
	public ModelAndView editStorageArea(HttpServletRequest request, HttpSession session, StorageArea storageArea,String beforeParams){
		ModelAndView mav = new ModelAndView();
		ResultInfo rs  = storageareaService.editStorageArea(storageArea, request, session);
		if (null != rs &&rs.getCode()==0) {
			JSONObject json=JSONObject.fromObject(rs.getData());
			StorageArea sr=(StorageArea) JSONObject.toBean(json, StorageArea.class);
			mav.addObject("refresh", "true_false_true");
			return success(mav);
		} else {
			return fail(mav);
		}
	}
	/**
	 * 
	 * <b>Description:</b><br> 按照名称查询货区
	 * @param request
	 * @param storageroom
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月17日 上午10:31:02
	 */
	@ResponseBody
	@RequestMapping(value = "/getStorageAreaByName")
	public ResultInfo<StorageArea> getStorageAreaByName(HttpServletRequest request, StorageArea storageArea, HttpSession session) {
		User session_user =(User)session.getAttribute(ErpConst.CURR_USER);
		ResultInfo<StorageArea> resultInfo = new ResultInfo<StorageArea>();
		StorageArea storageAreaInfo = storageareaService.getStorageAreaByName(storageArea,session);
		if (null != storageAreaInfo) {
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
			resultInfo.setData(storageAreaInfo);
		}
		return resultInfo;
	}
	/**
	 * 
	 * <b>Description:</b><br> 货区禁用跳转
	 * @param storageArea
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月24日 上午10:13:47
	 */
	@ResponseBody
	@RequestMapping(value = "/disableStorageArea")
	public ModelAndView disableStorageArea(StorageArea storageArea) {
		ModelAndView mv = new ModelAndView();
		//查询货区下的商品数量
		StorageArea sa = storageareaService.getGoodsNum(storageArea);
		if(sa!=null && sa.getCnt()>0){
			mv.setViewName("logistics/storageAreaMag/storageareaWarnForbid");
		}else{
			mv.setViewName("logistics/storageAreaMag/storageareaForbid");
		}
		try {
			mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(storageArea)));
		} catch (Exception e) {
			logger.error("disableStorageArea:", e);
		}
		mv.addObject("storageArea",storageArea);
		return mv;
	}
	/**
	 * 
	 * <b>Description:</b><br> 禁用货区
	 * @param request
	 * @param storageArea
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月24日 上午10:15:01
	 */
	@ResponseBody
	@RequestMapping(value="/upDisableStorageArea")
	@SystemControllerLog(operationType = "edit",desc = "保存禁用货区")
	public ResultInfo<?> upDisableStorageArea(HttpServletRequest request,StorageArea storageArea,String beforeParams){
		int is = storageArea.getIsEnable();
	    if(is==0){
	    	storageArea.setIsEnable(1);//启用
	    }else{
	    	storageArea.setIsEnable(0);//禁用
	    	storageArea.setEnabletime(DateUtil.sysTimeMillis());
	    }
		return storageareaService.upDisableStorageArea(storageArea);
	}
	/**
	 * 
	 * <b>Description:</b><br> 获取货区下的货架
	 * @param request
	 * @param storageArea
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月27日 上午9:03:30
	 */
	@ResponseBody
	@RequestMapping(value = "/getStorageRackByAId")
	public ResultInfo<List<StorageRack>> getStorageRackByAId(HttpServletRequest request, StorageArea storageArea, HttpSession session) {
		ResultInfo<List<StorageRack>> resultInfo = new ResultInfo<List<StorageRack>>();
		List<StorageRack> storageRackList = storageareaService.getStorageRackByRId(storageArea);
		if (null != storageRackList) {
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
			resultInfo.setData(storageRackList);
		}
		return resultInfo;
	}
	
	
}
