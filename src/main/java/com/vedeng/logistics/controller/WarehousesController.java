package com.vedeng.logistics.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.rabbitmq.MsgProducer;
import com.rabbitmq.RabbitConfig;
import com.vedeng.logistics.model.WarehouseGoodsStatus;
import com.vedeng.logistics.model.WarehouseStock;
import com.vedeng.logistics.service.WarehouseStockService;
import com.vedeng.order.dao.SaleorderMapper;
import com.vedeng.order.model.Saleorder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vedeng.authorization.model.Company;
import com.vedeng.authorization.model.Organization;
import com.vedeng.authorization.model.Region;
import com.vedeng.authorization.model.User;
import com.vedeng.common.annotation.SystemControllerLog;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.goods.model.Goods;
import com.vedeng.order.model.BussinessChance;
import com.vedeng.order.model.Quoteorder;
import com.vedeng.order.model.vo.BussinessChanceVo;
import com.vedeng.system.model.Attachment;
import com.vedeng.system.service.OrgService;
import com.vedeng.system.service.RegionService;
import com.vedeng.system.service.UserService;
import com.vedeng.trader.model.CommunicateRecord;
import com.vedeng.trader.model.Trader;
import com.vedeng.trader.model.TraderCustomer;
import com.vedeng.logistics.model.StorageRoom;
import com.vedeng.logistics.model.Warehouse;
import com.vedeng.logistics.service.StorageroomService;
import com.vedeng.logistics.service.WarehousesService;

import net.sf.json.JSONObject;
/**
 * 
 * <b>Description:</b><br> 仓库管理
 * @author scott
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.warehouse.controller
 * <br><b>ClassName:</b> WarehousesController
 * <br><b>Date:</b> 2017年7月13日 下午3:06:35
 */
@Controller
@RequestMapping("warehouse/warehouses")
public class WarehousesController extends BaseController {
	
	@Autowired
	@Qualifier("warehousesService")
	private WarehousesService warehousesService;
	
	@Autowired
	@Qualifier("storageroomService")
	private StorageroomService storageroomService;
	
	@Autowired
	@Qualifier("regionService")
	private RegionService regionService;// 自动注入regionService
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;//自动注入userService

	@Autowired
	@Qualifier("warehouseStockService")
	private WarehouseStockService warehouseStockService;

	@Resource
	private SaleorderMapper saleorderMapper;
	
	/**
	 * 
	 * <b>Description:</b><br> 仓库信息查询（分页）
	 * @param request
	 * @param warehouses
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月13日 下午3:06:55
	 */
	@ResponseBody
	@RequestMapping(value="/index")
	public ModelAndView index(HttpServletRequest request,Warehouse warehouses,Company company,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo, 
			@RequestParam(required = false) Integer pageSize,HttpSession session) {
		User session_user =(User)session.getAttribute(ErpConst.CURR_USER);
		Page page = getPageTag(request,pageNo,pageSize);
		ModelAndView mv = new ModelAndView();
		//获取公司
		String companyName = warehousesService.getCompanyName(session_user.getCompanyId()).getCompanyName();
		company.setCompanyName(companyName);
		company.setCompanyId(session_user.getCompanyId());
		// 地区
		List<Region> provinceList = regionService.getRegionByParentId(1);
		mv.addObject("provinceList", provinceList);

		if (null != warehouses.getProvince() && warehouses.getProvince() > 0) {
			List<Region> cityList = regionService.getRegionByParentId(warehouses.getProvince());
			mv.addObject("cityList", cityList);
		}
		if (null != warehouses.getCity() && warehouses.getCity() > 0) {
			List<Region> zoneList = regionService.getRegionByParentId(warehouses.getCity());
			mv.addObject("zoneList", zoneList);
		}
		// 地区处理
		if (null != warehouses.getZone() && warehouses.getZone() > 0) {
			warehouses.setAreaId(warehouses.getZone());
		} else if (null != warehouses.getCity() && warehouses.getCity() > 0) {
			warehouses.setAreaId(warehouses.getCity());
		} else if (null != warehouses.getProvince() && warehouses.getProvince() > 0) {
			warehouses.setAreaId(warehouses.getProvince());
		}
		warehouses.setCompanyId(session_user.getCompanyId());
		Map<String,Object> map = warehousesService.getWarehouseList(warehouses,page);
		List<Warehouse> list = (List<Warehouse>) map.get("list");
		//仓库中商品数量
		for (Warehouse warehouse : list) {
			warehouse.setCompanyId(session_user.getCompanyId());
			warehouse.setCnt(warehousesService.getGoodsList(warehouse).getCnt());
		}
		//获得仓库所属公司
		for(int i=0;i<list.size();i++){
			list.get(i).setCompanyName(warehousesService.getCompanyName(list.get(i).getCompanyId()).getCompanyName());
			list.get(i).setAreaName(list.get(i).getAreaName().replace("中国",""));
		}
		mv.addObject("company",company);
		mv.addObject("warehouses",warehouses);
		mv.addObject("warehouseList",list);
		mv.addObject("page", (Page)map.get("page"));
		mv.setViewName("logistics/warehouseMag/index_warehouse");
		return mv;
	}
	/**
	 * 
	 * <b>Description:</b><br> 仓库信息页面跳转
	 * @param warehouses
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月17日 上午10:28:20
	 */
	@ResponseBody
	@RequestMapping(value = "/addwarehouse")
	public ModelAndView addwarehouse(Warehouse warehouses) {
		ModelAndView mv = new ModelAndView();
		// 地区
		List<Region> provinceList = regionService.getRegionByParentId(1);
		mv.addObject("provinceList", provinceList);
		if (null != warehouses.getProvince() && warehouses.getProvince() > 0) {
			List<Region> cityList = regionService.getRegionByParentId(warehouses.getProvince());
			mv.addObject("cityList", cityList);
		}
		if (null != warehouses.getCity() && warehouses.getCity() > 0) {
			List<Region> zoneList = regionService.getRegionByParentId(warehouses.getCity());
			mv.addObject("zoneList", zoneList);
		}
		mv.setViewName("logistics/warehouseMag/add_warehouse");
		return mv;
	}
	/**
	 * 
	 * <b>Description:</b><br> 按照名称查询仓库
	 * @param request
	 * @param warehouses
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月17日 上午10:31:02
	 */
	@ResponseBody
	@RequestMapping(value = "/getWarehouseByName")
	public ResultInfo<Warehouse> getWarehouseByName(HttpServletRequest request, Warehouse warehouses, HttpSession session) {
		User session_user =(User)session.getAttribute(ErpConst.CURR_USER);
		ResultInfo<Warehouse> resultInfo = new ResultInfo<Warehouse>();
		warehouses.setCompanyId(session_user.getCompanyId());
		Warehouse warehousesInfo = warehousesService.getWarehouseByName(warehouses,session);
		if (null != warehousesInfo) {
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
			resultInfo.setData(warehousesInfo);
		}
		return resultInfo;
	}
	/**
	 * 
	 * <b>Description:</b><br> 保存仓库信息
	 * @param request
	 * @param session
	 * @param warehouses
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月17日 下午2:21:05
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/saveWarehouse")
	@SystemControllerLog(operationType = "add",desc = "保存新增仓库")
	public ModelAndView saveWarehouse(HttpServletRequest request, HttpSession session, Warehouse warehouses) {
		ResultInfo rs = null;
		ModelAndView mav = new ModelAndView();
		try {
			rs = warehousesService.saveWarehouse(warehouses, request, session);
		} catch (Exception e) {
			logger.error("saveWarehouse:", e);
		}
		if (null != rs && rs.getCode() == 0) {
			JSONObject json = JSONObject.fromObject(rs.getData());
			Warehouse wh = (Warehouse) JSONObject.toBean(json, Warehouse.class);
			mav.addObject("refresh", "true_false_true");
			return success(mav);
		} else {
			return fail(mav);
		}
	}
	/**
	 * 
	 * <b>Description:</b><br> 保存成功后跳转到详情页面
	 * @param warehouses
	 * @param request
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月17日 下午6:33:09
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/toWarehouseDetailPage")
	public ModelAndView toWarehouseDetailPage(Warehouse warehouses, HttpServletRequest request,HttpSession session,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize) {
		ModelAndView mav = new ModelAndView("logistics/warehouseMag/view_warehouseDetail");
		
		//新增仓库的详细信息
		Warehouse warehousesInfo = warehousesService.getWarehouseById(warehouses);
		warehousesInfo.setCompanyName(warehousesService.getCompanyName(warehousesInfo.getCompanyId()).getCompanyName());
		
		//获取创建人
		warehousesInfo.setCreatorName(userService.getUserById(warehousesInfo.getCreator()).getUsername());
		//仓库地区名称
		warehousesInfo.setAreaName(warehousesInfo.getAreaName().replace("中国",""));
		//仓库中商品数量
		Warehouse wh = warehousesService.getGoodsList(warehousesInfo);
		if(wh!=null && wh.getCnt()>0){
			warehousesInfo.setCnt(warehousesService.getGoodsList(warehousesInfo).getCnt());
		}else{
			warehousesInfo.setCnt(0);
		}
		//仓库下库房列表
		List<StorageRoom> list = storageroomService.getStorageListById(warehousesInfo);
		mav.addObject("storageroomList",list);
		mav.addObject("warehouses",warehousesInfo);
		return mav;
	}
	/**
	 * 
	 * <b>Description:</b><br> 禁用仓库弹框
	 * @param warehouses
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月18日 上午10:24:08
	 */
	@ResponseBody
	@RequestMapping(value = "/disableWarehouse")
	public ModelAndView disableWarehouse(Warehouse warehouses,HttpSession session) {
		ModelAndView mv = new ModelAndView();
		User session_user =(User)session.getAttribute(ErpConst.CURR_USER);
		//查询仓库下的商品数量
		warehouses.setCompanyId(session_user.getCompanyId());
		Warehouse ws = warehousesService.getGoodsList(warehouses);
		if(ws!=null && ws.getCnt()>0){
			mv.setViewName("logistics/warehouseMag/warehouseWarnForbid");
		}else{
			mv.setViewName("logistics/warehouseMag/warehouseForbid");
		}
		mv.addObject("warehouses",warehouses);
		try {
			mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(warehouses)));
		} catch (Exception e) {
			logger.error("disableWarehouse:", e);
		}
		return mv;
	}
	/**
	 * 
	 * <b>Description:</b><br> 禁用仓库
	 * @param request
	 * @param warehouses
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月18日 下午6:42:03
	 */
	@ResponseBody
	@RequestMapping(value="/upDisableWarehouse")
	@SystemControllerLog(operationType = "edit",desc = "保存编辑禁用仓库")
	public ResultInfo<?> disableWarehouse(HttpServletRequest request,Warehouse warehouses,String beforeParams){
	    int is = warehouses.getIsEnable();
	    if(is==0){
	    	warehouses.setIsEnable(1);//启用
	    }else{
	    	warehouses.setIsEnable(0);//禁用
	    	warehouses.setEnabletime(DateUtil.sysTimeMillis());
	    }
		return warehousesService.disableWarehouse(warehouses);
	}
	/**
	 * 
	 * <b>Description:</b><br> 编辑仓库信息页面跳转
	 * @param warehouses
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月18日 上午10:24:08
	 */
	@ResponseBody
	@RequestMapping(value = "/editWarehouseJump")
	public ModelAndView editWarehouse(Warehouse warehouses) {
		ModelAndView mv = new ModelAndView();
		Warehouse wh = warehousesService.getWarehouseById(warehouses);
		
		//地区
	    List<Region> provinceList = regionService.getRegionByParentId(1);
	    
	    //地区处理
		if(!StringUtils.isEmpty(wh.getAddress())){
			Integer areaId = wh.getAreaId();
			List<Region> regionList = (List<Region>) regionService.getRegion(areaId, 1);
			if(!StringUtils.isEmpty(regionList)){
				for(Region r : regionList){
					switch(r.getRegionType()){
					case 1:
						List<Region> cityList = regionService.getRegionByParentId(r.getRegionId());
						mv.addObject("provinceRegion",r);
						mv.addObject("cityList",cityList);
						break;
					case 2:
						List<Region> zoneList = regionService.getRegionByParentId(r.getRegionId());
						mv.addObject("cityRegion",r);
						mv.addObject("zoneList",zoneList);
						break;
					case 3:
						mv.addObject("zoneRegion",r);
						break;
					default:
						mv.addObject("countryRegion",r);
						break;
					}
				}
			}
		}
		mv.addObject("provinceList", provinceList);
		mv.addObject("warehouses",wh);
		try {
			mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(wh)));
		} catch (Exception e) {
			logger.error("editWarehouseJump:", e);
		}
		mv.setViewName("logistics/warehouseMag/edit_warehouse");
		return mv;
	}
	/**
	 * 
	 * <b>Description:</b><br> 编辑仓库信息
	 * @param warehouses
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月18日 上午10:24:08
	 */
	@ResponseBody
	@RequestMapping(value = "/editWarehouse")
	@SystemControllerLog(operationType = "edit",desc = "保存编辑仓库信息")
	public ModelAndView editWarehouse(HttpServletRequest request, HttpSession session, Warehouse warehouses,String beforeParams){
		ModelAndView mav = new ModelAndView();
		ResultInfo rs  = warehousesService.editWarehouse(warehouses, request, session);
		if (null != rs &&rs.getCode()==0) {
			JSONObject json=JSONObject.fromObject(rs.getData());
			Warehouse wh=(Warehouse) JSONObject.toBean(json, Warehouse.class);
			mav.addObject("refresh", "true_false_true");
			return success(mav);
		} else {
			return fail(mav);
		}
	}
	/**
	 * 
	 * <b>Description:</b><br> 根据id获取仓库
	 * @param request
	 * @param warehouses
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月25日 上午10:45:20
	 */
	@ResponseBody
	@RequestMapping(value = "/getWarehouseById")
	public ResultInfo<Warehouse> getWarehouseById(HttpServletRequest request, Warehouse warehouses, HttpSession session) {
		ResultInfo<Warehouse> resultInfo = new ResultInfo<Warehouse>();
		Warehouse warehousesInfo = warehousesService.getWarehouseById(warehouses);
		if (null != warehousesInfo) {
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
			resultInfo.setData(warehousesInfo);
		}
		return resultInfo;
	}
	/**
	 * 
	 * <b>Description:</b><br> 获取仓库下的库房
	 * @param request
	 * @param warehouses
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月25日 上午11:14:06
	 */
	@ResponseBody
	@RequestMapping(value = "/getWarehouseByWId")
	public ResultInfo<List<StorageRoom>> getWarehouseByWId(HttpServletRequest request, Warehouse warehouses, HttpSession session) {
		ResultInfo<List<StorageRoom>> resultInfo = new ResultInfo<List<StorageRoom>>();
		List<StorageRoom> list = storageroomService.getStorageListById(warehouses);
		if (null != list) {
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
			resultInfo.setData(list);
		}
		return resultInfo;
	}
/**
	 * 
	 * <b>Description:</b><br> 查询临时库是否存在
	 * @param request
	 * @param warehouses
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年9月5日 上午10:47:45
	 */
	@ResponseBody
	@RequestMapping(value = "/getLsWarehouse")
	public ResultInfo<List<Warehouse>> getLsWarehouse(HttpServletRequest request, Warehouse warehouses, HttpSession session) {
		User session_user =(User)session.getAttribute(ErpConst.CURR_USER);
		ResultInfo<List<Warehouse>> resultInfo = new ResultInfo<List<Warehouse>>();
		warehouses.setCompanyId(session_user.getCompanyId());
		List<Warehouse> list = warehousesService.getLsWarehouse(warehouses);
		if (null != list) {
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
			resultInfo.setData(list);
		}
		return resultInfo;
	}

	/**
	*	数据迁移到新库存表
	* @Author:strange
	* @Date:18:00 2019-11-05
	*/
	@ResponseBody
	@RequestMapping("/insertNewStock")
	public ResultInfo insertNewStock(){
		String re = warehouseStockService.insertNewStock();
		ResultInfo resultInfo = new ResultInfo();
		resultInfo.setMessage(re);
		resultInfo.setCode(0);
		return resultInfo;
	}

	@ResponseBody
	@RequestMapping("/updateSaleorderOccupyNum")
	public ResultInfo updateSaleorderOccupyNum(@RequestParam(required = false) Integer start){
		ResultInfo res = new ResultInfo();
		Saleorder saleorder = new Saleorder();
		if (start == null){
			start = 0;
		}
		saleorder.setOrgId(start);
		int allCount = saleorderMapper.getSaleorderidByStatus();
		for (int i = start; i < allCount/1000+1; i++) {
			saleorder.setOrgId(i*1000);
			Map<String,Integer> map = warehouseStockService.updateSaleorderOccupyNum(saleorder,1);
			Map<String,Integer> actionOccupyNum = warehouseStockService.updateSaleorderActionOccupyNum(saleorder,1);
			logger.info("updateSaleorderOccupyNum 当前更新到行页数 :{}",i);
			if(!CollectionUtils.isEmpty(map)) {
				res.setMessage("更新sku为:"+ map.keySet()+"值为:"+ map.values() + "数据");
				res.setCode(0);
			}
		}
		return res;
	}
	@ResponseBody
	@RequestMapping("/getErrorStockGoodsList")
	public ResultInfo getErrorStockGoodsList(){
		ResultInfo result = new ResultInfo();
		List<WarehouseGoodsStatus> goodsList = 	warehousesService.getErrorStockGoodsList();
		result.setData(goodsList);
		result.setCode(goodsList.size());
		return  result;
	}
}



















