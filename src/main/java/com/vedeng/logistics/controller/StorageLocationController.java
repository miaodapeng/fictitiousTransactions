package com.vedeng.logistics.controller;



import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.vedeng.common.controller.Consts;
import com.vedeng.common.model.FileInfo;
import com.vedeng.logistics.model.bo.StorageLocationBo;
import com.vedeng.logistics.service.*;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
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
import com.vedeng.logistics.model.StorageLocation;
import com.vedeng.logistics.model.StorageRack;
import com.vedeng.logistics.model.StorageRoom;
import com.vedeng.logistics.model.Warehouse;
import com.vedeng.logistics.service.StorageareaService;
import com.vedeng.logistics.service.StoragelocationService;
import com.vedeng.logistics.service.StoragerackService;
import com.vedeng.logistics.service.StorageroomService;
import com.vedeng.system.service.UserService;

import net.sf.json.JSONObject;
/**
 * 
 * <b>Description:</b><br> 库位管理
 * @author scott
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.warehouse.controller
 * <br><b>ClassName:</b> StorageLocationController
 * <br><b>Date:</b> 2017年7月26日 下午4:23:51
 */

@Controller
@RequestMapping("warehouse/storageLocationMag")
public class StorageLocationController extends BaseController {
	
	@Autowired
	@Qualifier("storagerackService")
	private StoragerackService storagerackService;
	
	@Autowired
	@Qualifier("storageroomService")
	private StorageroomService storageroomService;
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;//自动注入userService
	
	@Autowired
	@Qualifier("storageAreaService")
	private StorageareaService storageareaService;

	@Autowired
	@Qualifier("storagelocationService")
	private StoragelocationService storagelocationService;

	@Autowired
	private WarehousesService warehousesService;
	
	/**
	 * 
	 * <b>Description:</b><br> 库位列表
	 * @param request
	 * @param storageRack
	 * @param pageNo
	 * @param pageSize
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月26日 下午4:24:27
	 */
	@ResponseBody
	@RequestMapping(value="/index")
	public ModelAndView index(HttpServletRequest request,StorageLocation  storageLocation,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo, 
			@RequestParam(required = false) Integer pageSize,HttpSession session) {
		User session_user =(User)session.getAttribute(ErpConst.CURR_USER);
		Page page = getPageTag(request,pageNo,pageSize);
		ModelAndView mv = new ModelAndView();
		
		//根据公司id获取所属货架
		StorageRack storageRack = new StorageRack();
		storageRack.setCompanyId(session_user.getCompanyId());
		List<StorageRack> listRack = storagerackService.getStorageRackListByCId(storageRack);
		
		storageLocation.setCompanyId(session_user.getCompanyId());
		Map<String,Object> map = storagelocationService.getStorageLocationList(storageLocation,page);
		List<StorageLocation> list = (List<StorageLocation>) map.get("list");
		for (StorageLocation storageLocation2 : list) {
			storageLocation2.setCnt(storagelocationService.getGoodsNum(storageLocation2).getCnt());
		}
		mv.addObject("storageLocation",storageLocation);
		mv.addObject("listRack",listRack);
		mv.addObject("storageLocationList",list);
		mv.addObject("page", (Page)map.get("page"));
		mv.setViewName("logistics/storageLocaltionMag/storagelocation_index");
		return mv;
	}
	/**
	 * 
	 * <b>Description:</b><br> 新增库位页面跳转
	 * @param storageLocation
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月27日 上午8:46:33
	 */
	@ResponseBody
	@RequestMapping(value = "/addStorageLocation")
	public ModelAndView addStorageLocation(StorageLocation  storageLocation,HttpSession session) {
		User session_user =(User)session.getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView();
		//获取公司id获取所属仓库
		Warehouse warehouses = new Warehouse();
		warehouses.setCompanyId(session_user.getCompanyId());
		List<Warehouse> warehouseList = storageroomService.getWarehouseByCompanyId(warehouses);
		mv.addObject("warehouseList",warehouseList);
		mv.setViewName("logistics/storageLocaltionMag/add_storagelocation");
		return mv;
	}
	/**
	 * 
	 * <b>Description:</b><br> 新增库位
	 * @param request
	 * @param session
	 * @param storageLocation
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月27日 上午11:32:40
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/saveStorageLocation")
	@SystemControllerLog(operationType = "add",desc = "保存新增库位")
	public ModelAndView saveStorageLocation(HttpServletRequest request, HttpSession session,StorageLocation  storageLocation) {
		ModelAndView mav = new ModelAndView();
		ResultInfo rs  = storagelocationService.saveStorageLocation(storageLocation, request, session);
		if (null != rs &&rs.getCode()==0) {
			JSONObject json=JSONObject.fromObject(rs.getData());
			StorageLocation sr=(StorageLocation) JSONObject.toBean(json, StorageLocation.class);
			mav.addObject("refresh", "true_false_true");
			//mav.addObject("url", "./toStorageLocationDetailPage.do?storageLocationId="+sr.getStorageLocationId());
			return success(mav);
		} else {
			return fail(mav);
		}
	}
	/**
	 * 
	 * <b>Description:</b><br> 根据name查询库区
	 * @param request
	 * @param storageLocation
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月27日 下午1:16:04
	 */
	@ResponseBody
	@RequestMapping(value = "/getStorageLocationByName")
	public ResultInfo<StorageLocation> getStorageLocationByName(HttpServletRequest request, StorageLocation storageLocation, HttpSession session) {
		ResultInfo<StorageLocation> resultInfo = new ResultInfo<StorageLocation>();
		StorageLocation storageLocationInfo = storagelocationService.getStorageLocationByName(storageLocation,session);
		if (null != storageLocationInfo) {
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
			resultInfo.setData(storageLocationInfo);
		}
		return resultInfo;
	}
	/**
	 * 
	 * <b>Description:</b><br> 库位详情
	 * @param storageLocation
	 * @param request
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月27日 下午1:35:38
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/toStorageLocationDetailPage")
	public ModelAndView toStorageLocationDetailPage(StorageLocation storageLocation, HttpServletRequest request,HttpSession session) {
		ModelAndView mav = new ModelAndView("logistics/storageLocaltionMag/view_StorageLocationDetail");
		//获取货架详细信息
		StorageLocation storageLocationInfo = storagelocationService.getStoragelocationById(storageLocation);
		storageLocationInfo.setCreatorName(userService.getUserById(storageLocationInfo.getCreator()).getUsername());
		//查询库位下的商品个数
		StorageLocation sl = storagelocationService.getGoodsNum(storageLocation);
		if(sl!=null && sl.getCnt()>0){
			storageLocationInfo.setCnt(sl.getCnt());
		}else{
			storageLocationInfo.setCnt(0);
		}
		mav.addObject("storageLocation",storageLocationInfo);
		return mav;
	}
	/**
	 * 
	 * <b>Description:</b><br> 编辑页面跳转
	 * @param storageLocation
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月27日 下午2:17:46
	 */
	@ResponseBody
	@RequestMapping(value = "/editStoragelocationJump")
	public ModelAndView editStoragerackJump(StorageLocation storageLocation,HttpSession session) {
		User session_user =(User)session.getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView();
		//获取库位信息
		StorageLocation storageLocationInfo = storagelocationService.getStoragelocationById(storageLocation);
		//获取公司id获取所属仓库
		Warehouse warehouses = new Warehouse();
		warehouses.setCompanyId(session_user.getCompanyId());
		List<Warehouse> warehouseList = storageroomService.getWarehouseByCompanyId(warehouses);
		//根据公司id获取所属库房
		StorageRoom storageroom = new StorageRoom();
		storageroom.setCompanyId(session_user.getCompanyId());
		List<StorageRoom> listRoom = storageareaService.getStorageRoomList(storageroom);
		//根据公司id获取货区
		StorageArea storageArea = new StorageArea();
		storageArea.setCompanyId(session_user.getCompanyId());
		List<StorageArea> storageAreaList = storageareaService.getStorageAreaListByCId(storageArea);
		//根据公司id获取货架
		StorageRack  storageRack = new StorageRack();
		storageRack.setCompanyId(session_user.getCompanyId());
		List<StorageRack> storageRackList = storagerackService.getStorageRackListByCId(storageRack);
		mv.addObject("warehouseList",warehouseList);
		mv.addObject("listRoom",listRoom);
		mv.addObject("storageRackList",storageRackList);
		mv.addObject("storageAreaList",storageAreaList);
		mv.addObject("storageLocationInfo",storageLocationInfo);
		try {
			mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(storageLocation)));
		} catch (Exception e) {
			logger.error("editStoragelocationJump:", e);
		}
		mv.setViewName("logistics/storageLocaltionMag/edit_storagelocation");
		return mv;
	}
	/**
	 * 
	 * <b>Description:</b><br> 编辑库位
	 * @param request
	 * @param session
	 * @param storageLocation
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月27日 下午2:58:01
	 */
	@ResponseBody
	@RequestMapping(value = "/editStorageLocation")
	@SystemControllerLog(operationType = "edit",desc = "保存编辑库位")
	public ModelAndView editStorageLocation(HttpServletRequest request, HttpSession session,StorageLocation storageLocation,String beforeParams){
		ModelAndView mav = new ModelAndView();
		ResultInfo rs  = storagelocationService.editStorageLocation(storageLocation, request, session);
		if (null != rs &&rs.getCode()==0) {
			JSONObject json=JSONObject.fromObject(rs.getData());
			StorageLocation sr=(StorageLocation) JSONObject.toBean(json, StorageLocation.class);
			mav.addObject("refresh", "true_false_true");
			//mav.addObject("url", "./toStorageLocationDetailPage.do?storageLocationId="+sr.getStorageLocationId());
			return success(mav);
		} else {
			return fail(mav);
		}
	}
	/**
	 * 
	 * <b>Description:</b><br> 禁用库位页面跳转
	 * @param storageLocation
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月27日 下午3:16:08
	 */
	@ResponseBody
	@RequestMapping(value = "/disableStorageLocation")
	public ModelAndView disableStorageLocation(StorageLocation storageLocation) {
		ModelAndView mv = new ModelAndView();
		//查询库位下的商品个数
		StorageLocation sl = storagelocationService.getGoodsNum(storageLocation);
		if(sl!=null && sl.getCnt()>0){
			mv.setViewName("logistics/storageLocaltionMag/storagelocationWarnForbid");
		}else{
			mv.setViewName("logistics/storageLocaltionMag/storagelocationForbid");
		}
		try {
			mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(storageLocation)));
		} catch (Exception e) {
			logger.error("disableStorageLocation:", e);
		}
		mv.addObject("storageLocation",storageLocation);
		return mv;
	}
	/**
	 * 
	 * <b>Description:</b><br> 禁用库位
	 * @param request
	 * @param storageRack
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月27日 下午3:19:01
	 */
	@ResponseBody
	@RequestMapping(value="/updateDisableStorageLocation")
	@SystemControllerLog(operationType = "edit",desc = "保存禁用库位")
	public ResultInfo<?> updateDisableStorageLocation(HttpServletRequest request,StorageLocation storageLocation,String beforeParams){
		int is = storageLocation.getIsEnable();
	    if(is==0){
	    	storageLocation.setIsEnable(1);//启用
	    }else{
	    	storageLocation.setIsEnable(0);//禁用
	    	storageLocation.setEnabletime(DateUtil.sysTimeMillis());
	    }
		return storagelocationService.updateDisableStorageLocation(storageLocation);
	}

	/**
	 * <b>Description:</b><br>
	 * 批量新增库位初始化
	 *
	 * @param request
	 * @return
	 * @Note <b>Author:</b> Hugo <br>
	 *       <b>Date:</b> 2020年2月15日 下午6:22:59
	 */
	@ResponseBody
	@RequestMapping(value = "/batchAddStorageLocation")
	public ModelAndView batchAddWarehouseIn(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("logistics/storageLocaltionMag/batch_add_storageLocation");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 批量保存库位信息（读取Excel）
	 *
	 * @param request
	 * @return
	 * @Note <b>Author:</b> Hugo <br>
	 *       <b>Date:</b> 2020年2月15日 下午6:22:59
	 */
	@ResponseBody
	@RequestMapping(value = "/batchSaveStorageLocation")
	public ResultInfo<?> batchSaveLocation(HttpServletRequest request, HttpSession session,
											  @RequestParam("safile") MultipartFile rkfile) {
		ResultInfo<?> resultInfo = new ResultInfo<>();
		try {
			User user = (User) session.getAttribute(Consts.SESSION_USER);
			// 临时文件存放地址
			String path = request.getSession().getServletContext().getRealPath("/upload/logistics");
			FileInfo fileInfo = ftpUtilService.fileUploadServe(path, rkfile);
			if (fileInfo.getCode() == 0) {
				//1.校验上传的文件类型为Excel
				String suffix = fileInfo.getFilePath().substring(fileInfo.getFilePath().lastIndexOf(".") + 1);
				if ("xlsx".equalsIgnoreCase(suffix)) {
					ArrayList<StorageLocationBo> storageLocationBos = new ArrayList<>();

					// 获取excel路径
					Workbook workbook = null;
					workbook = WorkbookFactory.create(new FileInputStream(new File(fileInfo.getFilePath())));

					// 获取第一面sheet
					Sheet sheet = workbook.getSheetAt(0);
					// 起始行
					int startRowNum = sheet.getFirstRowNum() + 1;
					int endRowNum = sheet.getLastRowNum();// 结束行

					//2.验证模板是否为标准模板
					Row firstRow = sheet.getRow(0);
					if (6 != firstRow.getLastCellNum()){
						log.info("模板不正确，请重新下载模板文件");
						resultInfo.setMessage("模板不正确，请重新下载模板文件");
						return resultInfo;
					}

					if (0 == endRowNum){
						log.info("第1行，第1列不可为空!");
						resultInfo.setMessage("第1行，第1列不可为空!");
						return resultInfo;
					}

					for (int rowNum = startRowNum; rowNum <= endRowNum; rowNum++) {// 循环行数
						Row row = sheet.getRow(rowNum);
						int startCellNum = row.getFirstCellNum();// 起始列
						int endCellNum = row.getLastCellNum() - 1;// 结束列

						// 获取excel的值
						for (int cellNum = startCellNum; cellNum <= endCellNum; cellNum++) {// 循环列数（下表从0开始）
							Cell cell = row.getCell(cellNum);
							Integer type = cell.getCellType();
							if (cellNum == 0) {// 第一列数据cellNum==startCellNum
								// 若excel中无内容，而且没有空格，cell为空--默认3，空白
								if (cell == null) {
									log.info("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列库位名不能为空！");
									resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列库位名称不能为空！");
									return resultInfo;
								} else {
									//3.校验库位名的长度和类型
									//设置单元格类型防止类型转换错误
									row.getCell(cellNum).setCellType(Cell.CELL_TYPE_STRING);
									String storageRackName = cell.getStringCellValue().toString();
									String pattern = "[\u4e00-\u9fa5_a-zA-Z0-9_]{0,32}";

									if (!storageRackName.matches(pattern)){
										log.info("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列库位名称最多输入32个字符并且不能是中英文以及数字以外的字符！");
										resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列库位名称最多输入32个字符并且不能是中英文以及数字以外的字符！");
										return resultInfo;
									} else {
										//4.校验表格中的库位名不重复
										if (storageLocationBos.size() > 0){
											for (StorageLocationBo storageLocationBo : storageLocationBos) {
												if(storageLocationBo.getStorageLocation().equals(cell.getStringCellValue().toString())){
													log.info("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列库位名称重复！");
													resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列库位名称重复！");
													return resultInfo;
												}
											}
										}
										//5.校验货架在数据库中是否存在
										String storageLocationName = cell.getStringCellValue().toString();
										StorageLocation storageLocationBefore = new StorageLocation();
										storageLocationBefore.setStorageLocationName(storageLocationName);
										StorageLocation storageLocationNow = storagelocationService.getStorageLocationByName(storageLocationBefore, session);
										if (storageLocationNow == null){
											StorageLocationBo storageLocationBo4save = new StorageLocationBo();
											storageLocationBo4save.setStorageLocation(storageLocationName);
											storageLocationBos.add(storageLocationBo4save);
										} else {
											log.info("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列库位已使用，请重新提交！");
											resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列库位已使用，请重新提交！");
											return resultInfo;
										}
									}
								}
							}

							if (cellNum == 1) {// 第二列数据cellNum==startCellNum
								// 若excel中无内容，而且没有空格，cell为空--默认3，空白
								if (cell == null || type != 1) {
									log.info("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列仓库名称错误！");
									resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列仓库名称错误！");
									return resultInfo;
								} else {
									storageLocationBos.get(rowNum - 1).setWarehouse(cell.getStringCellValue().toString());
								}
							}

							if (cellNum == 2) {// 第三列数据cellNum==startCellNum
								// 若excel中无内容，而且没有空格，cell为空--默认3，空白
								if (cell == null || type != 1) {
									log.info("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列库房名称错误！");
									resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列库房名称错误！");
									return resultInfo;
								} else {
									storageLocationBos.get(rowNum - 1).setStorageRoom(cell.getStringCellValue().toString());
								}
							}

							if (cellNum == 3) {// 第四列数据cellNum==startCellNum
								// 若excel中无内容，而且没有空格，cell为空--默认3，空白
								if (cell == null || type != 1) {
									log.info("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列货区名称错误！");
									resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列货区名称错误！");
									return resultInfo;
								} else {
									storageLocationBos.get(rowNum - 1).setStorageArea(cell.getStringCellValue().toString());
								}
							}

							if (cellNum == 4) {// 第五列数据cellNum==startCellNum
								// 若excel中无内容，而且没有空格，cell为空--默认3，空白
								if (cell == null) {
									log.info("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列货架名称错误！");
									resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列货架名称错误！");
									return resultInfo;
								} else {
                                    //设置单元格类型防止类型转换错误
                                    row.getCell(cellNum).setCellType(Cell.CELL_TYPE_STRING);
                                    storageLocationBos.get(rowNum- 1).setStorageRack(cell.getStringCellValue().toString());
								}
							}

							if (cellNum == 5) {// 第六列数据cellNum==startCellNum
								// 若excel中无内容，而且没有空格，cell为空--默认3，空白
								if (cell != null) {
									//设置单元格类型防止类型转换错误
									row.getCell(cellNum).setCellType(Cell.CELL_TYPE_STRING);

									//6.校验备注的长度小于128个字符
									String comment = cell.getStringCellValue().toString();
									if (comment.length() > 128){
										log.info("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列名称库位备注小于128个字符！");
										resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列名称库位备注小于128个字符！");
										return resultInfo;
									} else {
										storageLocationBos.get(rowNum - 1).setComments(cell.getStringCellValue().toString());
									}
								}
							}
						}

						//7.校验仓库在数库中是否存在
						Warehouse warehouseBefore = new Warehouse();
						warehouseBefore.setWarehouseName(storageLocationBos.get(rowNum - 1).getWarehouse());
						Warehouse warehouseNow = warehousesService.getWarehouseByName(warehouseBefore, session);
						if (warehouseNow == null){
							log.info("表格项错误，第" + (rowNum + 1) + "行2列名称仓库不存在！");
							resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行2列名称仓库不存在！");
							return resultInfo;
						}

						//8.校验库房在数据库中是否存在并且级联关系正确
						StorageRoom storageRoomBefore = new StorageRoom();
						storageRoomBefore.setStorageRoomName(storageLocationBos.get(rowNum - 1).getStorageRoom());
						storageRoomBefore.setWarehouseId(warehouseNow.getWarehouseId());
						StorageRoom storageRoomNow = storageroomService.getStorageRoomByName(storageRoomBefore, session);
						if (storageRoomNow == null){
							log.info("表格项错误，第" + (rowNum + 1) + "行3列名称库房不存在或者不属于" + warehouseBefore.getWarehouseName() +  "！");
							resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行3列名称库房不存在或者不属于 " + warehouseBefore.getWarehouseName() +  "！");
							return resultInfo;
						}

						//9.校验货区在数据库中是否存在并且级联关系正确
						StorageArea storageAreaBefore = new StorageArea();
						storageAreaBefore.setStorageAreaName(storageLocationBos.get(rowNum - 1).getStorageArea());
						storageAreaBefore.setStorageRoomId(storageRoomNow.getStorageroomId());
						StorageArea storageAreaNow = storageareaService.getStorageAreaByName(storageAreaBefore, session);
						if (storageAreaNow == null){
							log.info("表格项错误，第" + (rowNum + 1) + "行4列名称库区不存在或者不属于" + storageRoomBefore.getStorageRoomName() +  "！");
							resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行4列名称库区不存在或者不属于 " + storageRoomBefore.getStorageRoomName()+  "！");
							return resultInfo;
						}

						//10.校验货架在数据库中是否存在并且级联关系正确
						StorageRack storageRackBefore = new StorageRack();
						storageRackBefore.setStorageRackName(storageLocationBos.get(rowNum - 1).getStorageRack());
						storageRackBefore.setStorageAreaId(storageAreaNow.getStorageAreaId());
						StorageRack storageRackNow = storagerackService.getStorageRackByName(storageRackBefore, session);
						if (storageRackNow == null){
							log.info("表格项错误，第" + (rowNum + 1) + "行5列名称货架不存在或者不属于" + storageAreaBefore.getStorageAreaName() +  "！");
							resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行5列名称货架不存在或者不属于 " + storageAreaBefore.getStorageAreaName() +  "！");
							return resultInfo;
						} else {
							storageLocationBos.get(rowNum - 1).setStorageRackId(storageRackNow.getStorageRackId());
						}
					}

					for (int rowNum = startRowNum; rowNum <= endRowNum; rowNum++) {// 循环行数
						//11.保存该行货架数据
						StorageLocation storageLocation4Save = new StorageLocation();
						storageLocation4Save.setStorageRackId(storageLocationBos.get(rowNum - 1).getStorageRackId());
						storageLocation4Save.setStorageLocationName(storageLocationBos.get(rowNum - 1).getStorageLocation());
						storageLocation4Save.setComments(storageLocationBos.get(rowNum - 1).getComments());
						ResultInfo resultInfo4Save = storagelocationService.saveStorageLocation(storageLocation4Save, request, session);
						resultInfo =  resultInfo4Save;
					}
				} else {
					logger.info("文件格式校验失败");
					resultInfo.setMessage("文件格式校验失败");
					return resultInfo;
				}
			}
			return resultInfo;
		} catch (Exception e) {
			logger.error("batchSaveStorageLocation:", e);
			return resultInfo;
		}
	}
}
