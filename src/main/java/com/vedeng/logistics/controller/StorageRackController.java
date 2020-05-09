package com.vedeng.logistics.controller;


import com.vedeng.authorization.model.User;
import com.vedeng.common.annotation.SystemControllerLog;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.model.FileInfo;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.logistics.model.*;
import com.vedeng.logistics.model.bo.StorageRackBo;
import com.vedeng.logistics.service.StorageareaService;
import com.vedeng.logistics.service.StoragerackService;
import com.vedeng.logistics.service.StorageroomService;
import com.vedeng.logistics.service.WarehousesService;
import com.vedeng.system.service.UserService;
import net.sf.json.JSONObject;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * 
 * <b>Description:</b><br> 货架管理
 * @author scott
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.warehouse.controller
 * <br><b>ClassName:</b> StorageRackController
 * <br><b>Date:</b> 2017年7月25日 下午2:13:49
 */

@Controller
@RequestMapping("warehouse/storageRackMag")
public class StorageRackController extends BaseController {
	
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
	private WarehousesService warehousesService;
	
	/**
	 * 
	 * <b>Description:</b><br> 货架列表
	 * @param request
	 * @param storageRack
	 * @param pageNo
	 * @param pageSize
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月25日 下午2:16:18
	 */
	@ResponseBody
	@RequestMapping(value="/index")
	public ModelAndView index(HttpServletRequest request,StorageRack storageRack,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo, 
			@RequestParam(required = false) Integer pageSize,HttpSession session) {
		User session_user =(User)session.getAttribute(ErpConst.CURR_USER);
		Page page = getPageTag(request,pageNo,pageSize);
		ModelAndView mv = new ModelAndView();
		
		//根据公司id获取所属货区
		StorageArea storageArea = new StorageArea();
		storageArea.setCompanyId(session_user.getCompanyId());
		List<StorageArea> listArea = storagerackService.getStorageAreaList(storageArea);
		
		storageRack.setCompanyId(session_user.getCompanyId());
		Map<String,Object> map = storagerackService.getStorageRackList(storageRack,page);
		List<StorageRack> list = (List<StorageRack>) map.get("list");
		for (StorageRack storageRack2 : list) {
			storageRack2.setCnt(storagerackService.getGoodsNum(storageRack2).getCnt());
		}
		mv.addObject("listArea",listArea);
		mv.addObject("storageRackList",list);
		mv.addObject("page", (Page)map.get("page"));
		mv.setViewName("logistics/storageRackMag/storagerack_index");
		return mv;
	}
	/**
	 * 
	 * <b>Description:</b><br> 新增货架页面跳转
	 * @param storageRack
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月25日 下午3:55:43
	 */
	@ResponseBody
	@RequestMapping(value = "/addStorageRack")
	public ModelAndView addStorageRack(StorageRack storageRack,HttpSession session) {
		User session_user =(User)session.getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView();
		//获取公司id获取所属仓库
		Warehouse warehouses = new Warehouse();
		warehouses.setCompanyId(session_user.getCompanyId());
		List<Warehouse> warehouseList = storageroomService.getWarehouseByCompanyId(warehouses);
		mv.addObject("warehouseList",warehouseList);
		mv.setViewName("logistics/storageRackMag/add_storagerack");
		return mv;
	}
	/**
	 * 
	 * <b>Description:</b><br> 新增货架
	 * @param request
	 * @param session
	 * @param storageRack
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月25日 下午4:54:32
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/saveStorageRack")
	@SystemControllerLog(operationType = "add",desc = "保存新增货架")
	public ModelAndView saveStorageRack(HttpServletRequest request, HttpSession session, StorageRack storageRack) {
		ModelAndView mav = new ModelAndView();
		ResultInfo rs  = storagerackService.saveStorageRack(storageRack, request, session);
		if (null != rs &&rs.getCode()==0) {
			JSONObject json=JSONObject.fromObject(rs.getData());
			StorageRack sr=(StorageRack) JSONObject.toBean(json, StorageRack.class);
			mav.addObject("refresh", "true_false_true");
			//mav.addObject("url", "./toStorageRackDetailPage.do?storageRackId="+sr.getStorageRackId());
			return success(mav);
		} else {
			return fail(mav);
		}
	}
	/**
	 * 
	 * <b>Description:</b><br> 根据name查询货架
	 * @param request
	 * @param storageRack
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月25日 下午4:59:01
	 */
	@ResponseBody
	@RequestMapping(value = "/getStorageRackByName")
	public ResultInfo<StorageRack> getStorageRackByName(HttpServletRequest request, StorageRack storageRack, HttpSession session) {
		ResultInfo<StorageRack> resultInfo = new ResultInfo<StorageRack>();
		StorageRack storageRackInfo = storagerackService.getStorageRackByName(storageRack,session);
		if (null != storageRackInfo) {
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
			resultInfo.setData(storageRackInfo);
		}
		return resultInfo;
	}
	/**
	 * 
	 * <b>Description:</b><br> 货架详情
	 * @param storageRack
	 * @param request
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月26日 上午8:39:38
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/toStorageRackDetailPage")
	public ModelAndView toStorageRackDetailPage(StorageRack storageRack, HttpServletRequest request,HttpSession session) {
		ModelAndView mav = new ModelAndView("logistics/storageRackMag/view_StoragerackDetail");
		//获取货架详细信息
		StorageRack storageRackInfo = storagerackService.getStoragerackById(storageRack);
		storageRackInfo.setCreatorName(userService.getUserById(storageRackInfo.getCreator()).getUsername());
		//查询货架下的商品
		StorageRack sr = storagerackService.getGoodsNum(storageRack);
		if(sr!=null && sr.getCnt()>0){
			storageRackInfo.setCnt(sr.getCnt());
		}else{
			storageRackInfo.setCnt(0);
		}
		//获取货架下的库区
		List<StorageLocation> list = storagerackService.getStorageLocationList(storageRackInfo);
		mav.addObject("storageRack",storageRackInfo);
		mav.addObject("StorageLocationList",list);
		return mav;
	}
	/**
	 * 
	 * <b>Description:</b><br> 编辑页面跳转
	 * @param storageRack
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月26日 下午2:51:25
	 */
	@ResponseBody
	@RequestMapping(value = "/editStoragerackJump")
	public ModelAndView editStoragerackJump(StorageRack storageRack,HttpSession session) {
		User session_user =(User)session.getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView();
		//获取货架信息
		StorageRack storageRackInfo = storagerackService.getStoragerackById(storageRack);
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
		
		mv.addObject("warehouseList",warehouseList);
		mv.addObject("listRoom",listRoom);
		mv.addObject("storageAreaList",storageAreaList);
		mv.addObject("storageRackInfo",storageRackInfo);
		try {
			mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(storageRack)));
		} catch (Exception e) {
			logger.error("editStoragerackJump:", e);
		}
		mv.setViewName("logistics/storageRackMag/edit_storagerack");
		return mv;
	}
	/**
	 * 
	 * <b>Description:</b><br> 编辑货架
	 * @param request
	 * @param session
	 * @param storageRack
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月26日 下午2:55:43
	 */
	@ResponseBody
	@RequestMapping(value = "/editStorageRack")
	@SystemControllerLog(operationType = "edit",desc = "保存编辑货架")
	public ModelAndView editStorageRack(HttpServletRequest request, HttpSession session,StorageRack storageRack,String beforeParams){
		ModelAndView mav = new ModelAndView();
		ResultInfo rs  = storagerackService.editStorageRack(storageRack, request, session);
		if (null != rs &&rs.getCode()==0) {
			JSONObject json=JSONObject.fromObject(rs.getData());
			StorageRack sr=(StorageRack) JSONObject.toBean(json, StorageRack.class);
			mav.addObject("refresh", "true_false_true");
			//mav.addObject("url",  "./toStorageRackDetailPage.do?storageRackId="+sr.getStorageRackId());
			return success(mav);
		} else {
			return fail(mav);
		}
	}
	/**
	 * 
	 * <b>Description:</b><br> 禁用货架页面跳转
	 * @param storageRack
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月26日 下午3:52:14
	 */
	@ResponseBody
	@RequestMapping(value = "/disableStorageRack")
	public ModelAndView disableStorageRack(StorageRack storageRack) {
		ModelAndView mv = new ModelAndView();
		//查询货架下的商品
		StorageRack sr = storagerackService.getGoodsNum(storageRack);
		if(sr!=null && sr.getCnt()>0){
			mv.setViewName("logistics/storageRackMag/storagerackWarnForbid");
		}else{
			mv.setViewName("logistics/storageRackMag/storagerackForbid");
		}
		try {
			mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(storageRack)));
		} catch (Exception e) {
			logger.error("disableStorageRack:", e);
		}
		mv.addObject("storageRack",storageRack);
		return mv;
	}
	/**
	 * 
	 * <b>Description:</b><br> 禁用货架
	 * @param request
	 * @param storageRack
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月26日 下午3:56:16
	 */
	@ResponseBody
	@RequestMapping(value="/updateDisableStorageRack")
	@SystemControllerLog(operationType = "edit",desc = "保存禁用货架")
	public ResultInfo<?> updateDisableStorageRack(HttpServletRequest request,StorageRack storageRack,String beforeParams){
		int is = storageRack.getIsEnable();
	    if(is==0){
	    	storageRack.setIsEnable(1);//启用
	    }else{
	    	storageRack.setIsEnable(0);//禁用
	    	storageRack.setEnabletime(DateUtil.sysTimeMillis());
	    }
		return storagerackService.updateDisableStorageRack(storageRack);
	}
	/**
	 * 
	 * <b>Description:</b><br> 货架下的库位
	 * @param request
	 * @param storageRack
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年8月1日 下午3:05:01
	 */
	@ResponseBody
	@RequestMapping(value = "/getStorageroomByRId")
	public ResultInfo<List<StorageLocation>> getStorageroomByRId(HttpServletRequest request,StorageRack storageRack, HttpSession session) {
		ResultInfo<List<StorageLocation>> resultInfo = new ResultInfo<List<StorageLocation>>();
		List<StorageLocation> list = storagerackService.getStorageLocationList(storageRack);
		if (null != list) {
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
			resultInfo.setData(list);
		}
		return resultInfo;
	}

	/**
	 * <b>Description:</b><br>
	 * 批量新增货架初始化
	 *
	 * @param request
	 * @return
	 * @Note <b>Author:</b> Hugo <br>
	 *       <b>Date:</b> 2020年2月11日 下午6:22:59
	 */
	@ResponseBody
	@RequestMapping(value = "/batchAddStorageRack")
	public ModelAndView batchAddStorageRack(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("logistics/storageAreaMag/batch_add_storageRack");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 批量保存货架信息（读取Excel）
	 *
	 * @param request
	 * @return
	 * @Note <b>Author:</b> Hugo <br>
	 *       <b>Date:</b> 2020年2月12日 下午6:22:59
	 */
	@ResponseBody
	@RequestMapping(value = "/batchSaveStorageRack")
	public ResultInfo<?> batchSaveWareStorageRack(HttpServletRequest request, HttpSession session,
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
					// 创建一个用于保存Excel数据的雨雾对象
					ArrayList<StorageRackBo> storageRackBos = new ArrayList<>();

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
					if (5 != firstRow.getLastCellNum()){
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
									log.info("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列货架名称错误！");
									resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列货架名称错误！");
									return resultInfo;
								} else {
									//设置单元格类型防止类型转换错误
									row.getCell(cellNum).setCellType(Cell.CELL_TYPE_STRING);
									//3.校验货架名的长度和类型
									String storageRackName = cell.getStringCellValue().toString();
									String pattern = "[\u4e00-\u9fa5_a-zA-Z0-9_]{0,32}";
									if (!storageRackName.matches(pattern)){
										log.info("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列货架名称最多输入32个字符并且不能是中英文以及数字以外的字符！！");
										resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列货架名称最多输入32个字符并且不能是中英文以及数字以外的字符！！");
										return resultInfo;
									} else {
										//4.校验表格中的货架名不重复
										if (storageRackBos.size() > 0){
											for (StorageRackBo storageRackBo : storageRackBos) {
												if (storageRackBo.getStorageRack().equals(storageRackName)){
													log.info("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列货架名称重复！");
													resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列货架名称重复！");
													return resultInfo;
												}
											}
										}

										//5.校验货架在数据库中是否存在
										StorageRack storageRackNow  = new StorageRack();
										storageRackNow.setStorageRackName(storageRackName);
										StorageRack storageRackBefore = storagerackService.getStorageRackByName(storageRackNow,session);
										if (storageRackBefore == null){
											StorageRackBo storageRackBo4Save = new StorageRackBo();
											storageRackBo4Save.setStorageRack(storageRackName);
											storageRackBos.add(storageRackBo4Save);
										} else {
											log.info("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列货架已使用，请重新提交！");
											resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列货架已使用，请重新提交！");
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
									storageRackBos.get(rowNum - 1).setWarehouse(cell.getStringCellValue().toString());
								}
							}

							//将库房名称放入业务对象中
							if (cellNum == 2) {// 第三列数据cellNum==startCellNum
								// 若excel中无内容，而且没有空格，cell为空--默认3，空白
								if (cell == null || type != 1) {
									log.info("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列库房名称错误！");
									resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列库房名称错误！");
									return resultInfo;
								} else {
									storageRackBos.get(rowNum - 1).setStorageRoom(cell.getStringCellValue().toString());
								}
							}

							//将货区放入业务对象中
							if (cellNum == 3) {// 第四列数据cellNum==startCellNum
								// 若excel中无内容，而且没有空格，cell为空--默认3，空白
								if (cell == null || type != 1) {
									log.info("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列货区名称错误！");
									resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列货区名称错误！");
									return resultInfo;
								} else {
									storageRackBos.get(rowNum - 1).setStorageArea(cell.getStringCellValue().toString());
								}
							}

							// 将货架备注放入业务对象中
							if (cellNum == 4) {// 第五列数据cellNum==startCellNum
								// 若excel中无内容，而且没有空格，cell为空--默认3，空白
								if (cell != null ) {
									//设置单元格类型防止类型转换错误
									row.getCell(cellNum).setCellType(Cell.CELL_TYPE_STRING);
									//6.校验备注的长度小于128个字符
									String comment = cell.getStringCellValue().toString();
									if (comment.length() > 128){
										log.info("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列名称货区备注小于128个字符！");
										resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列名称货区备注小于128个字符！");
										return resultInfo;
									}else {
										storageRackBos.get(rowNum - 1).setComments(cell.getStringCellValue().toString());
									}
								}
							}
						}

						//7.校验仓库在数库中是否存在
						Warehouse warehouseBefore = new Warehouse();
						warehouseBefore.setWarehouseName(storageRackBos.get(rowNum - 1).getWarehouse());
						Warehouse warehouseNow = warehousesService.getWarehouseByName(warehouseBefore, session);
						if (warehouseNow == null){
							log.info("表格项错误，第" + (rowNum + 1) + "行2列名称仓库不存在！");
							resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行2列名称仓库不存在！");
							return resultInfo;
						}

						//8.校验库房在数据库中是否存在并且级联关系正确
						StorageRoom storageRoomBefore = new StorageRoom();
						storageRoomBefore.setStorageRoomName(storageRackBos.get(rowNum - 1).getStorageRoom());
						storageRoomBefore.setWarehouseId(warehouseNow.getWarehouseId());
						StorageRoom storageRoomNow = storageroomService.getStorageRoomByName(storageRoomBefore, session);
						if (storageRoomNow == null){
							log.info("表格项错误，第" + (rowNum + 1) + "行3列名称库房不存在或者不属于" + warehouseBefore.getWarehouseName() +  "！");
							resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行3列名称库房不存在或者不属于 " + warehouseBefore.getWarehouseName() +  "！");
							return resultInfo;
						}

						//9.校验货区在数据库中是否存在并且级联关系正确
						StorageArea storageAreaBefore = new StorageArea();
						storageAreaBefore.setStorageAreaName(storageRackBos.get(rowNum - 1).getStorageArea());
						storageAreaBefore.setStorageRoomId(storageRoomNow.getStorageroomId());
						StorageArea storageAreaNow = storageareaService.getStorageAreaByName(storageAreaBefore, session);
						if (storageAreaNow == null){
							log.info("表格项错误，第" + (rowNum + 1) + "行4列名称库区不存在或者不属于" + storageRoomBefore.getStorageRoomName() +  "！");
							resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行4列名称库区不存在或者不属于 " + storageRoomBefore.getStorageRoomName()+  "！");
							return resultInfo;
						} else {
							storageRackBos.get(rowNum -1).setStorageAreaId(storageAreaNow.getStorageAreaId());
						}
					}

					for (int rowNum = startRowNum; rowNum <= endRowNum; rowNum++) {// 循环行数
						//10.保存该行货架数据
						StorageRack storageRack4Save = new StorageRack();
						storageRack4Save.setStorageAreaId(storageRackBos.get(rowNum - 1).getStorageAreaId());
						storageRack4Save.setStorageRackName(storageRackBos.get(rowNum - 1).getStorageRack());
						storageRack4Save.setComments(storageRackBos.get(rowNum - 1).getComments());
						ResultInfo resultInfo4Save = storagerackService.saveStorageRack(storageRack4Save, request, session);
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
			logger.error("batchSaveStorageRack:", e);
			return resultInfo;
		}

	}
}
