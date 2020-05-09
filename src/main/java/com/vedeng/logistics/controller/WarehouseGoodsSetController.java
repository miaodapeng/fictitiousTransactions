package com.vedeng.logistics.controller;



import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.vedeng.common.util.StringUtil;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
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
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.model.FileInfo;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.shiro.JedisUtils;
import com.vedeng.common.util.DateUtil;
import com.vedeng.goods.model.Brand;
import com.vedeng.goods.model.Category;
import com.vedeng.goods.model.Goods;
import com.vedeng.goods.model.Unit;
import com.vedeng.logistics.model.StorageArea;
import com.vedeng.logistics.model.StorageLocation;
import com.vedeng.logistics.model.StorageRack;
import com.vedeng.logistics.model.StorageRoom;
import com.vedeng.logistics.model.Warehouse;
import com.vedeng.logistics.model.WarehouseGoodsSet;
import com.vedeng.logistics.service.StorageareaService;
import com.vedeng.logistics.service.StoragelocationService;
import com.vedeng.logistics.service.StoragerackService;
import com.vedeng.logistics.service.StorageroomService;
import com.vedeng.logistics.service.WarehouseGoodsSetService;
import com.vedeng.logistics.service.WarehousesService;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.system.service.FtpUtilService;
import com.vedeng.system.service.UserService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * <b>Description:</b><br> 库位分配
 * @author scott
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.warehouse.controller
 * <br><b>ClassName:</b> StorageDistributionController
 * <br><b>Date:</b> 2017年8月1日 上午9:26:20
 */
@Controller
@RequestMapping("warehouse/warehouseGoodsSet")
public class WarehouseGoodsSetController extends BaseController {
	
	@Autowired
	@Qualifier("storageAreaService")
	private StorageareaService storageareaService;
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;//自动注入userService
	
	@Autowired
	@Qualifier("storageroomService")
	private StorageroomService storageroomService;
	
	@Autowired
	@Qualifier("warehouseGoodsSetService")
	private WarehouseGoodsSetService warehouseGoodsSetService;
	
	@Autowired
	@Qualifier("storagerackService")
	private StoragerackService storagerackService;
	
	@Autowired
	@Qualifier("storagelocationService")
	private StoragelocationService storagelocationService;
	
	@Autowired
	@Qualifier("ftpUtilService")
	private FtpUtilService ftpUtilService;
	
	@Autowired
	@Qualifier("warehousesService")
	private WarehousesService warehousesService;
	
	/**
	 * 
	 * <b>Description:</b><br> 库位分配列表
	 * @param request
	 * @param storageArea
	 * @param pageNo
	 * @param pageSize
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年8月1日 上午9:26:53
	 */
	@ResponseBody
	@RequestMapping(value="/index")
	public ModelAndView index(HttpServletRequest request,WarehouseGoodsSet warehouseGoodsSet,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo, 
			@RequestParam(required = false) Integer pageSize,HttpSession session) {
		User session_user =(User)session.getAttribute(ErpConst.CURR_USER);
		Page page = getPageTag(request,pageNo,pageSize);
		ModelAndView mv = new ModelAndView();
		//获取当前公司下的仓库
		Warehouse warehouses = new Warehouse();
		warehouses.setCompanyId(session_user.getCompanyId());
		List<Warehouse> warehouseList = storageroomService.getWarehouseByCompanyId(warehouses);
		mv.addObject("warehouseList",warehouseList);
		/*//根据公司id获取所属库房
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
		//根据公司id获取库位
		StorageLocation storageLocation = new StorageLocation();
		storageLocation.setCompanyId(session_user.getCompanyId());
		List<StorageLocation> storageLocationList = storagelocationService.getStorageLocationListByCId(storageLocation);*/
		//商品库位列表
		warehouseGoodsSet.setCompanyId(session_user.getCompanyId());
		Map<String,Object> map = warehouseGoodsSetService.getWarehouseGoodsSetList(warehouseGoodsSet,page);
		List<WarehouseGoodsSet> list = (List<WarehouseGoodsSet>) map.get("list");
		
		//设置每条记录的最小仓库级别和id
		/*for(int i=0;i<list.size();i++){
			if(list.get(i).getWareHouseId()!=null && list.get(i).getWareHouseId()!=0  ){
				if(list.get(i).getStorageroomId()!=null&&!"".equals(list.get(i).getStorageroomId())){
					if(list.get(i).getStorageAreaId()!=null&&!"".equals(list.get(i).getStorageAreaId())){
						if(list.get(i).getStorageRackId()!=null&&!"".equals(list.get(i).getStorageRackId())){
							if(list.get(i).getStorageLocationId()!=null&&!"".equals(list.get(i).getStorageLocationId())){
								list.get(i).setLevelAndId("5&"+list.get(i).getStorageLocationId());
							}else{
								list.get(i).setLevelAndId("4&"+list.get(i).getStorageRackId());
							}
						}else{
							list.get(i).setLevelAndId("3&"+list.get(i).getStorageAreaId());
						}
					}else{
						list.get(i).setLevelAndId("2&"+list.get(i).getStorageroomId());
					}
				}else{
					list.get(i).setLevelAndId("1&"+list.get(i).getWareHouseId());
				}
			}else{
				list.get(i).setLevelAndId("");
			}
		}*/
		/*mv.addObject("listRoom",listRoom);
		mv.addObject("storageAreaList",storageAreaList);
		mv.addObject("storageRackList",storageRackList);*/
		mv.addObject("warehouseList",warehouseList);
		//mv.addObject("storageLocationList",storageLocationList);
		mv.addObject("warehouseGoodsSet",warehouseGoodsSet);
		mv.addObject("warehouseGoodsSetList",list);
		mv.addObject("page", (Page)map.get("page"));
		mv.setViewName("logistics/warehouseGoodsSet/index_warehouseGoodsSet");
		return mv;
	}
	/**
	 * 
	 * <b>Description:</b><br> 删除库位商品
	 * @param request
	 * @param warehouseGoodsSet
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年8月3日 下午6:31:05
	 */
	@ResponseBody
	@RequestMapping(value="/delWarehouseGoods")
	public ResultInfo<?> delWarehouseGoods(HttpServletRequest request,WarehouseGoodsSet warehouseGoodsSet){
		if(warehouseGoodsSet==null){
			return new ResultInfo<>(-1, "参数为空");
		}
		String levelAndId = warehouseGoodsSet.getLevelAndId();
		/*if(levelAndId!=null && !"".equals(levelAndId)){
			setMinId(warehouseGoodsSet,levelAndId);
		}*/
		warehouseGoodsSet.setWarehouseGoodsSetId(Integer.parseInt(levelAndId));
		ResultInfo<?> result = warehouseGoodsSetService.delWarehouseGoods(warehouseGoodsSet);
		return result;
	}
	/**
	 * 
	 * <b>Description:</b><br> 批量删除库位商品
	 * @param request
	 * @param warehouseGoodsSet
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年8月3日 下午6:32:10
	 */
	@ResponseBody
	@RequestMapping(value="/delBatchWarehouseGoods")
	public ResultInfo<?> delBatchWarehouseGoods(HttpServletRequest request,WarehouseGoodsSet warehouseGoodsSet){
		if(warehouseGoodsSet==null){
			return new ResultInfo<>(-1, "参数为空");
		}
		List<WarehouseGoodsSet> list = new ArrayList<WarehouseGoodsSet>();
	    String values = warehouseGoodsSet.getValues();
	    String[] strs = values.split(",");
	    for(int i=0 ;i<strs.length;i++){
	    	WarehouseGoodsSet w = new WarehouseGoodsSet();
	    	String[] str = strs[i].split("@");
	    	w.setGoodsName(str[0]);
	    	if(str.length>1){
	    		//setMinId(w,str[1]);
				w.setWarehouseGoodsSetId(Integer.parseInt(str[1]));
	    	}
	        list.add(w);
	    }
		ResultInfo<?> result = warehouseGoodsSetService.delBatchWarehouseGoods(list);
		return result;
	}
	/**
	 * 
	 * <b>Description:</b><br> 设置最小级别的id
	 * @param warehouseGoodsSet
	 * @param levelAndId
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年8月4日 上午8:42:42
	 */
	private void setMinId(WarehouseGoodsSet warehouseGoodsSet,String levelAndId){
		if(!"".equals(levelAndId)){
			String[] str = levelAndId.split("&");
			if(str[1]!=null && !"".equals(str[1])){
				if("1".equals(str[0])){
					warehouseGoodsSet.setWareHouseId(Integer.parseInt(str[1]));
				}else if("2".equals(str[0])){
					warehouseGoodsSet.setStorageroomId(Integer.parseInt(str[1]));
				}else if("3".equals(str[0])){
					warehouseGoodsSet.setStorageAreaId(Integer.parseInt(str[1]));
				}else if("4".equals(str[0])){
					warehouseGoodsSet.setStorageRackId(Integer.parseInt(str[1]));
				}else if("5".equals(str[0])){
					warehouseGoodsSet.setStorageLocationId(Integer.parseInt(str[1]));
				}
			}
		}
	}
	/**
	 * 
	 * <b>Description:</b><br> 设置商品库位
	 * @param request
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年8月4日 上午10:35:33
	 */
	@ResponseBody
	@RequestMapping(value="/batchSetWarehouseGoodsJump")
	public ModelAndView batchSetWarehouseGoodsJump(HttpServletRequest request){
		return new ModelAndView("logistics/warehouseGoodsSet/batch_set_warehousegoods");
	}
	/**
	 * 
	 * <b>Description:</b><br> 批量设置产品库位
	 * @param request
	 * @param lwfile
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年8月4日 上午10:51:47
	 */
	@ResponseBody
	@RequestMapping("batchSetWarehouseGoods")
	public ResultInfo<?> batchSetWarehouseGoods(HttpServletRequest request,@RequestParam("lwfile") MultipartFile lwfile) {
		ResultInfo<?> resultInfo = new ResultInfo<>();
		try {
			User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
			Long time = DateUtil.sysTimeMillis();
			//临时文件存放地址
			String path = request.getSession().getServletContext().getRealPath("/upload/warehouseGoods");
			FileInfo fileInfo = ftpUtilService.fileUploadServe(path,lwfile);
			if(fileInfo.getCode()==0){//上传成功
				//全部仓库
				Warehouse ww = new Warehouse();
				ww.setCompanyId(user.getCompanyId());
				List<Warehouse> warehouseList = warehousesService.getAllWarehouse(ww);
				if(warehouseList.isEmpty()){
					resultInfo.setMessage("仓储中无仓库信息，请先创建仓库！");
					throw new Exception("仓储中无仓库信息，请先创建仓库！");
				}
				
				List<WarehouseGoodsSet> list = new ArrayList<>();//保存Excel中读取的数据
				// 获取excel路径
				Workbook workbook = WorkbookFactory.create(new FileInputStream(new File(fileInfo.getFilePath())));
				// 获取第一面sheet
				Sheet sheet = workbook.getSheetAt(0);
				// 起始行
				int startRowNum = sheet.getFirstRowNum() + 1;//第一行标题
				int endRowNum = sheet.getLastRowNum();// 结束行
 				for (int rowNum = startRowNum; rowNum <= endRowNum; rowNum++) {// 循环行数
					Row row = sheet.getRow(rowNum);
					if(row==null){
						resultInfo.setMessage("第"+rowNum+"行中无数据，请验证！");
						throw new Exception("第"+rowNum+"行中无数据，请验证！");
					}
					int startCellNum = 0;//默认从第一列开始
					int endCellNum = row.getLastCellNum() - 1;// 结束列
					// 获取excel的值
					WarehouseGoodsSet w = new WarehouseGoodsSet();
					if(user!=null){
						w.setCompanyId(user.getCompanyId());
						w.setCreator(user.getUserId());
						w.setAddTime(DateUtil.gainNowDate());
						w.setUpdater(user.getUserId());
						w.setModTime(DateUtil.gainNowDate());
					}
					Integer category1 = null, category2 = null ,category3 = null;
					String number_code = null;
					String str = null;
					for (int cellNum = startCellNum; cellNum <= endCellNum; cellNum++) {// 循环列数（下表从0开始）
						Cell cell = row.getCell(cellNum);
						try {
							Integer type = cell==null?3:cell.getCellType();
							str=null;
								switch (type) {

									case Cell.CELL_TYPE_BLANK:// 空白单元格
										str = null;
										break;
									case Cell.CELL_TYPE_STRING:// 字符类型
										str = cell.getStringCellValue().toString();
										break;
									default:
										row.getCell(cellNum).setCellType(Cell.CELL_TYPE_STRING);
										str = cell.getStringCellValue().toString();
										break;
								}
								switch (cellNum) {
									case 0://订货号
										if(str == null || "".equals(str)){
											resultInfo.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空，请验证！");
											throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空，请验证！");
										}
										//判断订货号是否存在
										Goods goods = new Goods();
										goods.setSku(str);
										List<Goods> listGoods = warehouseGoodsSetService.getGoods(goods);
										if(listGoods.size()==0){
											resultInfo.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：订货号不存在，请验证！");
											throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：订货号不存在，请验证！");
										}
										/*for(int x=0;x<list.size();x++){
											if(str.equals(list.get(x).getSku())){
												resultInfo.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：订货号在此Excel表格中存在重复，请验证！");
												throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：订货号在此Excel表格中存在重复，请验证！");
											}
										}*/
										w.setSku(str);
										break;
									case 1://仓库
										if(str == null ){
											resultInfo.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空，请验证！");
											throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空，请验证！");
										}
										for(int i=0;i<warehouseList.size();i++){
											if(str.equals(warehouseList.get(i).getWarehouseName())){
												w.setWareHouseId(warehouseList.get(i).getWarehouseId());
												break;
											}
										}
										if(w.getWareHouseId()==null){//未能查询到对应的仓库的id
											resultInfo.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
											throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
										}
										w.setWareHouseId(w.getWareHouseId());
										break;
									case 2://库房
										if(StringUtil.isNotBlank(str)){
											//获取全部库房
											List<StorageRoom> storageRoomList = storageroomService.getAllStorageRoom(new StorageRoom());
											if(storageRoomList.isEmpty()){
												resultInfo.setMessage("仓储中无库房信息，请先创建库房！");
												throw new Exception("仓储中无库房信息，请先创建库房！");
											}
											for(int i=0;i<storageRoomList.size();i++){
												if(str.equals(storageRoomList.get(i).getStorageRoomName())){
													w.setStorageroomId(storageRoomList.get(i).getStorageroomId());
													break;
												}
											}

											if(w.getStorageroomId()==null){//未能查询到对应的库房的id
												resultInfo.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
												throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
											}
											w.setStorageroomId(w.getStorageroomId());
										}
										break;
									case 3://货区
										if(StringUtil.isNotBlank(str) ){
											//查询全部货区
											List<StorageArea> storageAreaList = storageareaService.getAllStorageArea(new StorageArea());
											if(storageAreaList.isEmpty()){
												resultInfo.setMessage("仓储中无货区信息，请先创建货区！");
												throw new Exception("仓储中无货区信息，请先创建货区！");
											}
											for(int i=0;i<storageAreaList.size();i++){
												if(str.equals(storageAreaList.get(i).getStorageAreaName())){
													w.setStorageAreaId(storageAreaList.get(i).getStorageAreaId());
													break;
												}
											}
											if(w.getStorageAreaId()==null){//未能查询到对应的货区的id
												resultInfo.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
												throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
											}
											w.setStorageAreaId(w.getStorageAreaId());
										}
										break;
									case 4://货架
										if(StringUtil.isNotBlank(str)){
											//查询全部货架
											List<StorageRack> storageRackList = storagerackService.getAllStorageRack(new StorageRack());
											if(storageRackList.isEmpty()){
												resultInfo.setMessage("仓储中无货架信息，请先创建货架！");
												throw new Exception("仓储中无货架信息，请先创建货架！");
											}
											for(int i=0;i<storageRackList.size();i++){
												if(str.equals(storageRackList.get(i).getStorageRackName())){
													w.setStorageRackId(storageRackList.get(i).getStorageRackId());
													break;
												}
											}
											if(w.getStorageRackId()==null){//未能查询到对应的货架的id
												resultInfo.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
												throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
											}
											w.setStorageRackId(w.getStorageRackId());
										}
										break;
									case 5://库位
										if(StringUtil.isNotBlank(str) ){
											//查询全部库位
											List<StorageLocation> storageLocationList = storagelocationService.getAllStorageLocation(new StorageLocation());
											if(storageLocationList.isEmpty()){
												resultInfo.setMessage("仓储中无库位信息，请先创建库位！");
												throw new Exception("仓储中无库位信息，请先创建库位！");
											}
											for(int i=0;i<storageLocationList.size();i++){
												if(str.equals(storageLocationList.get(i).getStorageLocationName())){
													w.setStorageLocationId(storageLocationList.get(i).getStorageLocationId());
													break;
												}
											}
											if(w.getStorageLocationId()==null){//未能查询到对应的库位的id
												resultInfo.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
												throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
											}
											w.setStorageLocationId(w.getStorageLocationId());
										}
										break;
									case 6://仓库备注
										if(StringUtil.isNotBlank(str) ){
											if(str.length()>128){
												resultInfo.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值长度不允许超过128个字符，请验证！");
												throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值长度不允许超过128个字符，请验证！");
											}
											w.setComments(str);
										}
										break;
									default:
										resultInfo.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：获取参数值异常，请稍等重试或联系管理员！");
										throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：获取参数值异常，请稍等重试或联系管理员！");
								}
						} catch (Exception e) {
							logger.error("batchSetWarehouseGoods:", e);
							return resultInfo;
						}
					}
					//插入记录的信息
				    w.setCompanyId(user.getCompanyId());
					w.setCreator(user.getUserId());
					w.setAddTime(time);
					list.add(w);
				}
				if(list!=null && !list.isEmpty()){
					/*//验证订货号是否重复
					List<String> str_list = warehouseGoodsSetService.batchSaveSkuName(list);
					if(str_list!=null && !str_list.isEmpty()){
						for(int x=0;x<list.size();x++){
							for(String str:str_list){
								if(list.get(x).getSku().equals(str)){
									resultInfo.setMessage("第:" + (x + 2) + "行，第:1列：订货号重复，请验证！");
									throw new Exception("第:" + (x + 2) + "行，第:1列：订货号重复，请验证！");
								}
							}
						}
					}*/
					//批量保存
					resultInfo = warehouseGoodsSetService.batchSaveWarehouseGoods(list);
				}
			}
		}catch(Exception e) {
			logger.error("batchSetWarehouseGoods 2:", e);
			return resultInfo;
		}
		return resultInfo; 
	}
	/**
	 * 
	 * <b>Description:</b><br> 库位商品列表导出
	 * @param request
	 * @param response
	 * @param warehouseGoodsSet
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年8月8日 上午10:33:57
	 */
	/*@RequestMapping(value = "/exportWarehouseGoodsSetlist")
	public void export(HttpServletRequest request, HttpServletResponse response, WarehouseGoodsSet warehouseGoodsSet) {
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setContentType("multipart/form-data");
		String fileName = System.currentTimeMillis() + ".xlsx";
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
		
		try {
			int sheetNum = 1;// 工作薄sheet编号
			int bodyRowCount = 1;// 正文内容行号

			OutputStream out = response.getOutputStream();
			SXSSFWorkbook wb = new SXSSFWorkbook(10);
			Sheet sh = wb.createSheet("工作簿" + sheetNum);
			writeTitleContent(sh);

			Row row_value = null;
			Page page = null;
			Map<String,Object> map = null;
			Page pageTotal = null;			

			// ------------------定义表头----------------------------------------
			int page_size = 1000;// 数据库中每次查询条数
			page = getPageTag(request,1,page_size);
			map = warehouseGoodsSetService.getWarehouseGoodsSetList(warehouseGoodsSet,page);
			pageTotal = (Page)map.get("page");
			for (int i = 1; i <= pageTotal.getTotalPage(); i++) {
				page = getPageTag(request,i,page_size);
				map = warehouseGoodsSetService.getWarehouseGoodsSetList(warehouseGoodsSet,page);
				List<WarehouseGoodsSet> warehouseGoodLists = new ArrayList<>();
				warehouseGoodLists = (List<WarehouseGoodsSet>)map.get("list");
				for (WarehouseGoodsSet wList : warehouseGoodLists) {
					row_value = sh.createRow(bodyRowCount);
					
					row_value.createCell(0).setCellValue(wList.getSku());
					row_value.createCell(1).setCellValue(wList.getGoodsName());
					row_value.createCell(2).setCellValue(wList.getBrandName());
					row_value.createCell(3).setCellValue(wList.getModel());
					row_value.createCell(4).setCellValue(wList.getMaterialCode());
					row_value.createCell(5).setCellValue(wList.getUnitName());
					row_value.createCell(6).setCellValue(wList.getWareHouseName());
					row_value.createCell(7).setCellValue(wList.getStorageroomName());
					row_value.createCell(8).setCellValue(wList.getStorageAreaName());
					row_value.createCell(9).setCellValue(wList.getStorageRackName());
					row_value.createCell(10).setCellValue(wList.getStorageLocationName());
					row_value.createCell(11).setCellValue(wList.getComments());
					bodyRowCount++;
				}				
				warehouseGoodLists.clear();
			}

			wb.write(out);
			out.close();
			wb.dispose();

		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
	}

	public void writeTitleContent(Sheet sh) {
		Row row = sh.createRow(0);
		row.createCell(0).setCellValue("订货号");
		row.createCell(1).setCellValue("产品名称");
		row.createCell(2).setCellValue("品牌");
		row.createCell(3).setCellValue("型号");
		row.createCell(4).setCellValue("物料编码");
		row.createCell(5).setCellValue("单位");
		row.createCell(6).setCellValue("仓库");
		row.createCell(7).setCellValue("库房");
		row.createCell(8).setCellValue("货区");
		row.createCell(9).setCellValue("货架");
		row.createCell(10).setCellValue("库位");
		row.createCell(11).setCellValue("库位备注");
	}*/
	/**
	 * 
	 * <b>Description:</b><br> 所有库位列表导出
	 * @param request
	 * @param response
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年8月8日 上午10:33:57
	 */
	/*@RequestMapping(value = "/exportWarehouselist")
	public void exportWarehouselist(HttpServletRequest request, HttpServletResponse response,WarehouseGoodsSet warehouseGoodsSet) {
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setContentType("multipart/form-data");
		String fileName = System.currentTimeMillis() + ".xlsx";
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
		
		try {
			int sheetNum = 1;// 工作薄sheet编号
			int bodyRowCount = 1;// 正文内容行号

			OutputStream out = response.getOutputStream();
			SXSSFWorkbook wb = new SXSSFWorkbook(10);
			Sheet sh = wb.createSheet("工作簿" + sheetNum);
			writeWTitleContent(sh);

			Row row_value = null;
			Page page = null;
			Map<String,Object> map = null;
			Page pageTotal = null;			

			// ------------------定义表头----------------------------------------
			int page_size = 1000;// 数据库中每次查询条数
			page = getPageTag(request,1,page_size);
			map = warehouseGoodsSetService.getWarehouseList(warehouseGoodsSet,page);
			pageTotal = (Page)map.get("page");
			for (int i = 1; i <= pageTotal.getTotalPage(); i++) {
				page = getPageTag(request,i,page_size);
				map = warehouseGoodsSetService.getWarehouseList(warehouseGoodsSet,page);
				List<WarehouseGoodsSet> warehouseGoodLists = new ArrayList<>();
				warehouseGoodLists = (List<WarehouseGoodsSet>)map.get("list");
				System.out.println(warehouseGoodLists.size());
				for (WarehouseGoodsSet wList : warehouseGoodLists) {
					row_value = sh.createRow(bodyRowCount);
					
					row_value.createCell(0).setCellValue(wList.getWareHouseName());
					row_value.createCell(1).setCellValue(wList.getStorageroomName());
					row_value.createCell(2).setCellValue(wList.getStorageAreaName());
					row_value.createCell(3).setCellValue(wList.getStorageRackName());
					row_value.createCell(4).setCellValue(wList.getStorageLocationName());
					bodyRowCount++;
				}				
				warehouseGoodLists.clear();
			}

			wb.write(out);
			out.close();
			wb.dispose();

		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
	}

	public void writeWTitleContent(Sheet sh) {
		Row row = sh.createRow(0);
		row.createCell(0).setCellValue("仓库");
		row.createCell(1).setCellValue("库房");
		row.createCell(2).setCellValue("货区");
		row.createCell(3).setCellValue("货架");
		row.createCell(4).setCellValue("库位");
	}*/
}
