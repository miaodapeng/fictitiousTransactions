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
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.logistics.model.StorageArea;
import com.vedeng.logistics.model.StorageRoom;
import com.vedeng.logistics.model.Warehouse;
import com.vedeng.logistics.service.StorageroomService;
import com.vedeng.system.service.UserService;

import net.sf.json.JSONObject;

/**
 * 
 * <b>Description:</b><br>库房管理 
 * @author scott
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.warehouse.controller
 * <br><b>ClassName:</b> StorageRoomController
 * <br><b>Date:</b> 2017年7月19日 下午2:25:53
 */
@Controller
@RequestMapping("warehouse/storageRoomMag")
public class StorageRoomController extends BaseController {
	
	@Autowired
	@Qualifier("storageroomService")
	private StorageroomService storageroomService;
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;//自动注入userService
	
	/**
	 * 
	 * <b>Description:</b><br> 库房列表
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
	public ModelAndView index(HttpServletRequest request,StorageRoom storageroom,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo, 
			@RequestParam(required = false) Integer pageSize,HttpSession session) {
		User session_user =(User)session.getAttribute(ErpConst.CURR_USER);
		//获取所属仓库
		Warehouse warehouses = new Warehouse();
		warehouses.setCompanyId(session_user.getCompanyId());
		List<Warehouse> warehouseList = storageroomService.getWarehouseByCompanyId(warehouses);
		
		Page page = getPageTag(request,pageNo,pageSize);
		ModelAndView mv = new ModelAndView();
		storageroom.setCompanyId(session_user.getCompanyId());
		Map<String,Object> map = storageroomService.getStorageroomList(storageroom,page);
		List<StorageRoom> list = (List<StorageRoom>) map.get("list");
		for (StorageRoom storageRoom2 : list) {
			storageRoom2.setCompanyId(session_user.getCompanyId());
			storageRoom2.setCnt(storageroomService.getGoodsNum(storageRoom2).getCnt());
		}
		mv.addObject("storageroom",storageroom);
		mv.addObject("warehouseList",warehouseList);
		mv.addObject("storageRoomList",list);
		mv.addObject("page", (Page)map.get("page"));
		mv.setViewName("logistics/storageRoomMag/index_storageroom");
		return mv;
	}
	/**
	 * 
	 * <b>Description:</b><br> 库房新增页面跳转
	 * @param storageroom
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月20日 上午8:45:45
	 */
	@ResponseBody
	@RequestMapping(value = "/addStorageRoom")
	public ModelAndView addwarehouse(StorageRoom storageroom,HttpSession session) {
		User session_user =(User)session.getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView();
		//获取所属仓库
		Warehouse warehouses = new Warehouse();
		warehouses.setCompanyId(session_user.getCompanyId());
		List<Warehouse> warehouseList = storageroomService.getWarehouseByCompanyId(warehouses);
		mv.addObject("warehouseList",warehouseList);
		mv.setViewName("logistics/storageRoomMag/add_storageroom");
		return mv;
	}
	/**
	 * 
	 * <b>Description:</b><br> 新增库房
	 * @param request
	 * @param session
	 * @param storageroom
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月20日 上午9:41:36
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/saveStorageRoom")
	@SystemControllerLog(operationType = "add",desc = "保存新增库房")
	public ModelAndView saveStorageRoom(HttpServletRequest request, HttpSession session, StorageRoom storageroom) {
		ModelAndView mav = new ModelAndView();
		ResultInfo rs  = storageroomService.saveStorageRoom(storageroom, request, session);
		if (null != rs &&rs.getCode()==0) {
			JSONObject json=JSONObject.fromObject(rs.getData());
			StorageRoom sr=(StorageRoom) JSONObject.toBean(json, StorageRoom.class);
			mav.addObject("refresh", "true_false_true");
			return success(mav);
		} else {
			return fail(mav);
		}
	}
	/**
	 * 
	 * <b>Description:</b><br> 库房详情
	 * @param storageroom
	 * @param request
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月20日 上午11:35:23
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/toStorageRoomDetailPage")
	public ModelAndView toWarehouseDetailPage(StorageRoom storageroom, HttpServletRequest request,HttpSession session) {
		User session_user =(User)session.getAttribute(ErpConst.CURR_USER);
		ModelAndView mav = new ModelAndView("logistics/storageRoomMag/view_StorageroomDetail");
		//库房的详细信息
		StorageRoom sr = storageroomService.getStorageroomById(storageroom);
		//获取创建人
		sr.setCreatorName(userService.getUserById(sr.getCreator()).getUsername());
		//获取库房下的商品数量
		storageroom.setCompanyId(session_user.getCompanyId());
		StorageRoom st = storageroomService.getGoodsNum(storageroom);
		if(st!=null && st.getCnt()>0){
			sr.setCnt(st.getCnt());
		}else{
			sr.setCnt(0);
		}
		//库房下的货区列表
		storageroom.setFlag("1");
		List<StorageArea> storageAreaList = storageroomService.getStorageArea(storageroom);
		mav.addObject("storageroom",sr);
		mav.addObject("storageAreaList",storageAreaList);
		return mav;
	}
	/**
	 * 
	 * <b>Description:</b><br> 跳转到库房编辑页面
	 * @param storageroom
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月20日 下午2:28:05
	 */
	@ResponseBody
	@RequestMapping(value = "/editStorageRoomJump")
	public ModelAndView editStorageRoom(StorageRoom storageroom,HttpSession session) {
		User session_user =(User)session.getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView();
		StorageRoom sr = storageroomService.getStorageroomById(storageroom);
		//获取所属仓库
		Warehouse warehouses = new Warehouse();
		warehouses.setCompanyId(session_user.getCompanyId());
		List<Warehouse> warehouseList = storageroomService.getWarehouseByCompanyId(warehouses);
		mv.addObject("storageroom",sr);
		mv.addObject("warehouseList",warehouseList);
		try {
			mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(sr)));
		} catch (Exception e) {
			logger.error("editStorageRoomJump:", e);
		}
		mv.setViewName("logistics/storageRoomMag/edit_storageroom");
		return mv;
	}
	/**
	 * 
	 * <b>Description:</b><br> 编辑库房信息
	 * @param request
	 * @param session
	 * @param storageroom
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月20日 下午4:51:30
	 */
	@ResponseBody
	@RequestMapping(value = "/editStorageRoom")
	@SystemControllerLog(operationType = "edit",desc = "保存编辑库房信息")
	public ModelAndView editStorageRoom(HttpServletRequest request, HttpSession session, StorageRoom storageroom,String beforeParams){
		ModelAndView mav = new ModelAndView();
		ResultInfo rs  = storageroomService.editStorageRoom(storageroom, request, session);
		if (null != rs &&rs.getCode()==0) {
			JSONObject json=JSONObject.fromObject(rs.getData());
			StorageRoom sr=(StorageRoom) JSONObject.toBean(json, StorageRoom.class);
			mav.addObject("refresh", "true_false_true");
			return success(mav);
		} else {
			return fail(mav);
		}
	}
	/**
	 * 
	 * <b>Description:</b><br> 禁用库房弹框
	 * @param storageroom
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月20日 下午5:49:52
	 */
	@ResponseBody
	@RequestMapping(value = "/disableStorageroom")
	public ModelAndView disableStorageroom(StorageRoom storageroom,HttpSession session) {
		ModelAndView mv = new ModelAndView();
		User session_user =(User)session.getAttribute(ErpConst.CURR_USER);
		//查询库房下的商品数量
		storageroom.setCompanyId(session_user.getCompanyId());
		StorageRoom sr = storageroomService.getGoodsNum(storageroom);
		if(sr!=null && sr.getCnt()>0){
			mv.setViewName("logistics/storageRoomMag/storageroomWarnForbid");
		}else{
			mv.setViewName("logistics/storageRoomMag/StorageForbid");
		}
		try {
			mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(storageroom)));
		} catch (Exception e) {
			logger.error("disableStorageroom:", e);
		}
		mv.addObject("storageroom",storageroom);
		return mv;
	}
	/**
	 * 
	 * <b>Description:</b><br> 禁用库房
	 * @param request
	 * @param storageroom
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月21日 上午8:58:26
	 */
	@ResponseBody
	@RequestMapping(value="/upDisableStorageRoom")
	@SystemControllerLog(operationType = "edit",desc = "保存编辑禁用库房")
	public ResultInfo<?> upDisableStorageRoom(HttpServletRequest request,StorageRoom storageroom,String beforeParams){
		int is = storageroom.getIsEnable();
	    if(is==0){
	    	storageroom.setIsEnable(1);//启用
	    }else{
	    	storageroom.setIsEnable(0);//禁用
	    	storageroom.setEnabletime(DateUtil.sysTimeMillis());
	    }
		return storageroomService.upDisableStorageRoom(storageroom);
	}
	/**
	 * 
	 * <b>Description:</b><br> 按照名称查询库房
	 * @param request
	 * @param storageroom
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月17日 上午10:31:02
	 */
	@ResponseBody
	@RequestMapping(value = "/getStorageRoomByName")
	public ResultInfo<StorageRoom> getStorageRoomByName(HttpServletRequest request, StorageRoom storageroom, HttpSession session) {
		ResultInfo<StorageRoom> resultInfo = new ResultInfo<StorageRoom>();
		User session_user =(User)session.getAttribute(ErpConst.CURR_USER);
		storageroom.setCompanyId(session_user.getCompanyId());
		StorageRoom storageroomInfo = storageroomService.getStorageRoomByName(storageroom,session);
		if (null != storageroomInfo) {
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
			resultInfo.setData(storageroomInfo);
		}
		return resultInfo;
	}
	/**
	 * 
	 * <b>Description:</b><br> 根据库房id获取货区
	 * @param request
	 * @param storageroom
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月25日 下午4:30:15
	 */
	@ResponseBody
	@RequestMapping(value = "/getStorageroomBySId")
	public ResultInfo<List<StorageArea>> getStorageroomBySId(HttpServletRequest request, StorageRoom storageroom, HttpSession session) {
		ResultInfo<List<StorageArea>> resultInfo = new ResultInfo<List<StorageArea>>();
		User session_user =(User)session.getAttribute(ErpConst.CURR_USER);
		storageroom.setCompanyId(session_user.getCompanyId());
		List<StorageArea> storageAreaList = storageroomService.getStorageArea(storageroom);
		if (null != storageAreaList) {
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
			resultInfo.setData(storageAreaList);
		}
		return resultInfo;
	}
	
}
