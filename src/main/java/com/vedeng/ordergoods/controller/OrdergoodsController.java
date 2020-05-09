package com.vedeng.ordergoods.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.vedeng.authorization.model.User;
import com.vedeng.common.annotation.SystemControllerLog;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.model.FileInfo;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.common.util.RegexUtil;
import com.vedeng.common.validator.FormToken;
import com.vedeng.goods.model.Goods;
import com.vedeng.goods.model.vo.GoodsVo;
import com.vedeng.goods.service.GoodsService;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.model.vo.SaleorderVo;
import com.vedeng.ordergoods.model.OrdergoodsCategory;
import com.vedeng.ordergoods.model.OrdergoodsLaunch;
import com.vedeng.ordergoods.model.OrdergoodsLaunchGoal;
import com.vedeng.ordergoods.model.OrdergoodsStore;
import com.vedeng.ordergoods.model.OrdergoodsStoreAccount;
import com.vedeng.ordergoods.model.ROrdergoodsGoodsJCategory;
import com.vedeng.ordergoods.model.ROrdergoodsLaunchGoodsJCategory;
import com.vedeng.ordergoods.model.vo.OrdergoodsGoodsAccountVo;
import com.vedeng.ordergoods.model.vo.OrdergoodsGoodsVo;
import com.vedeng.ordergoods.model.vo.OrdergoodsLaunchVo;
import com.vedeng.ordergoods.model.vo.OrdergoodsStoreAccountVo;
import com.vedeng.ordergoods.model.vo.ROrdergoodsLaunchGoodsJCategoryVo;
import com.vedeng.ordergoods.service.OrdergoodsService;
import com.vedeng.soap.service.OrdergoodsSoapService;
import com.vedeng.system.service.FtpUtilService;
import com.vedeng.system.service.UserService;
import com.vedeng.trader.model.TraderContact;
import com.vedeng.trader.model.vo.TraderAddressVo;
import com.vedeng.trader.model.vo.TraderContactVo;
import com.vedeng.trader.model.vo.TraderCustomerVo;
import com.vedeng.trader.model.vo.TraderFinanceVo;
import com.vedeng.trader.model.vo.TraderVo;

/**
 * <b>Description:</b><br> 订货系统
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.ordergoods.controller
 * <br><b>ClassName:</b> OrdergoodsController
 * <br><b>Date:</b> 2017年11月20日 下午2:31:45
 */
@Controller
@RequestMapping("/ordergoods/manage")
public class OrdergoodsController extends BaseController {
	@Autowired
	@Qualifier("ordergoodsService")
	private OrdergoodsService ordergoodsService;
	@Autowired
	@Qualifier("ftpUtilService")
	private FtpUtilService ftpUtilService;
	@Autowired
	@Qualifier("goodsService")
	private GoodsService goodsService;
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	@Autowired
	@Qualifier("ordergoodsSoapService")
	private OrdergoodsSoapService ordergoodsSoapService;
	
	/**
	 * <b>Description:</b><br> 订货店铺列表
	 * @param request
	 * @param session
	 * @param ordergoodsCategory
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月21日 下午5:34:52
	 */
	@ResponseBody
	@RequestMapping(value = "storeindex")
	public ModelAndView storeIndex(HttpServletRequest request,HttpSession session){
		ModelAndView mv = new ModelAndView();
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		List<OrdergoodsStore> stores = ordergoodsService.getStore(user.getCompanyId());
		
		mv.addObject("stores", stores);
		mv.setViewName("ordergoods/store/store_index");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 新增店铺
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月21日 下午5:36:24
	 */
	@ResponseBody
	@RequestMapping(value = "addstore")
	public ModelAndView addStore(){
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("ordergoods/store/store_add");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 保存新增店铺
	 * @param request
	 * @param ordergoodsStore
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月21日 下午5:38:41
	 */
	@ResponseBody
	@RequestMapping(value = "saveaddstore")
	@SystemControllerLog(operationType = "add",desc = "保存新增店铺")
	public ResultInfo saveAddStore(HttpServletRequest request,OrdergoodsStore ordergoodsStore,HttpSession session){
		ResultInfo resultInfo = new ResultInfo<>();
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		ordergoodsStore.setCompanyId(user.getCompanyId());
		OrdergoodsStore store = ordergoodsService.getStoreByName(ordergoodsStore);
		if(null != store){
			resultInfo.setMessage("店铺名称不允许重复");
			return resultInfo;
		}
		
		Integer res = ordergoodsService.saveAddStore(ordergoodsStore,session);
		if(res > 0){
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
		}
		return resultInfo;
	}
	
	/**
	 * <b>Description:</b><br> 编辑店铺
	 * @param request
	 * @param ordergoodsStore
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月21日 下午5:41:42
	 */
	@ResponseBody
	@RequestMapping(value = "editstore")
	public ModelAndView editStore(HttpServletRequest request,OrdergoodsStore ordergoodsStore){
		ModelAndView mv = new ModelAndView();
		
		if(null == ordergoodsStore 
				|| null == ordergoodsStore.getOrdergoodsStoreId()
				|| ordergoodsStore.getOrdergoodsStoreId() <= 0){
			return pageNotFound();
		}
		
		OrdergoodsStore storeInfo = ordergoodsService.getStoreById(ordergoodsStore);
		
		mv.addObject("ordergoodsStore", storeInfo);
		try {
			mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(storeInfo)));
		} catch (IOException e) {
			logger.error("order goods", e);
		}
		mv.setViewName("ordergoods/store/store_edit");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 保存编辑店铺
	 * @param request
	 * @param ordergoodsStore
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月21日 下午5:42:04
	 */
	@ResponseBody
	@RequestMapping(value = "saveeditstore")
	@SystemControllerLog(operationType = "edit",desc = "保存编辑店铺")
	public ResultInfo saveeditStore(HttpServletRequest request,OrdergoodsStore ordergoodsStore,HttpSession session,String beforeParams){
		ResultInfo resultInfo = new ResultInfo<>();
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		
		if(null == ordergoodsStore.getOrdergoodsStoreId()){
			return resultInfo;
		}
		
		ordergoodsStore.setCompanyId(user.getCompanyId());
		OrdergoodsStore store = ordergoodsService.getStoreByName(ordergoodsStore);
		
		if(null != store
				&& store.getOrdergoodsStoreId() > 0
				&& !store.getOrdergoodsStoreId().equals(ordergoodsStore.getOrdergoodsStoreId())){
			resultInfo.setMessage("店铺名称不允许重复");
			return resultInfo;
		}
		
		Integer res = ordergoodsService.saveeditStore(ordergoodsStore,session);
		if(res > 0){
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
		}
		return resultInfo;
	}
	
	/**
	 * <b>Description:</b><br>店铺状态更改 
	 * @param ordergoodsStore
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月22日 上午10:08:32
	 */
	@ResponseBody
	@RequestMapping(value = "/changeenable")
	@SystemControllerLog(operationType = "edit",desc = "启用/禁用店铺")
	public ResultInfo changeEnable(OrdergoodsStore ordergoodsStore,HttpSession session){
		ResultInfo resultInfo = new ResultInfo<>();
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		
		if(null == ordergoodsStore 
				|| ordergoodsStore.getOrdergoodsStoreId() == null){
			return resultInfo;
		}
		
		Boolean suc = ordergoodsService.changeEnable(ordergoodsStore,session);
		
		if (suc) {// 成功
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
		}
		return resultInfo;
	}
	
	/**
	 * <b>Description:</b><br> 订货产品分类
	 * @param request
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月21日 下午5:28:35
	 */
	@ResponseBody
	@RequestMapping(value = "categoryindex")
	public ModelAndView categoryIndex(HttpServletRequest request,HttpSession session,OrdergoodsCategory ordergoodsCategory){
		ModelAndView mv = new ModelAndView();
		
		List<OrdergoodsCategory> list = ordergoodsService.getOrdergoodsCategory(0,ordergoodsCategory.getOrdergoodsStoreId(),true);
		
		OrdergoodsStore ordergoodsStore = new OrdergoodsStore();
		ordergoodsStore.setOrdergoodsStoreId(ordergoodsCategory.getOrdergoodsStoreId());
		OrdergoodsStore store = ordergoodsService.getStoreById(ordergoodsStore);
		
		mv.setViewName("ordergoods/category/category_index");
		mv.addObject("ordergoodsCategory", list);
		mv.addObject("ordergoodsStore", store);
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 新增分类
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月21日 下午5:36:24
	 */
	@ResponseBody
	@RequestMapping(value = "addcategory")
	public ModelAndView addCategory(OrdergoodsCategory ordergoodsCategory){
		ModelAndView mv = new ModelAndView();
		List<OrdergoodsCategory> list = ordergoodsService.getOrdergoodsCategory(0,ordergoodsCategory.getOrdergoodsStoreId(),true);
		
		mv.setViewName("ordergoods/category/category_add");
		mv.addObject("ordergoodsCategory", ordergoodsCategory);
		mv.addObject("categoryList", list);
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 保存新增分类
	 * @param request
	 * @param ordergoodsCategory
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月21日 下午5:38:41
	 */
	@ResponseBody
	@RequestMapping(value = "saveaddcategory")
	@SystemControllerLog(operationType = "add",desc = "保存新增店铺分类")
	public ResultInfo saveAddCategory(HttpServletRequest request,OrdergoodsCategory ordergoodsCategory,HttpSession session){
		ResultInfo resultInfo = new ResultInfo<>();
		User user =(User)session.getAttribute(ErpConst.CURR_USER);
		if(user!=null){
			ordergoodsCategory.setCreator(user.getUserId());
			ordergoodsCategory.setAddTime(DateUtil.sysTimeMillis());
			
			ordergoodsCategory.setUpdater(user.getUserId());
			ordergoodsCategory.setModTime(DateUtil.sysTimeMillis());
		}
		if(ordergoodsCategory.getSort() == null){
			ordergoodsCategory.setSort(100);
		}
		
		Integer level = 1;
		if(null != ordergoodsCategory.getParentId() && ordergoodsCategory.getParentId() > 0){
			OrdergoodsCategory category = new OrdergoodsCategory();
			category.setOrdergoodsCategoryId(ordergoodsCategory.getParentId());
			
			OrdergoodsCategory ordergoodsCategoryById = ordergoodsService.getOrdergoodsCategoryById(category);
			level = ordergoodsCategoryById.getLevel()+1;
		}
		
		OrdergoodsCategory cate = new OrdergoodsCategory();
		cate.setCategoryName(ordergoodsCategory.getCategoryName());
		cate.setLevel(level);
		cate.setOrdergoodsStoreId(ordergoodsCategory.getOrdergoodsStoreId());
		OrdergoodsCategory oldInfo = ordergoodsService.getOrdergoodsCategoryByCate(ordergoodsCategory);
		if(null != oldInfo){
			resultInfo.setMessage("分类名称不允许重复");
			return resultInfo;
		}
		
		ordergoodsCategory.setLevel(level);
		Integer res = ordergoodsService.saveAddCategory(ordergoodsCategory);
		if(res > 0){
			//同步
			ordergoodsSoapService.ordergoodsCategorySync(res);
			
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
		}
		return resultInfo;
	}
	
	/**
	 * <b>Description:</b><br> 编辑分类
	 * @param request
	 * @param ordergoodsCategory
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月21日 下午5:41:42
	 */
	@ResponseBody
	@RequestMapping(value = "editcategory")
	public ModelAndView editCategory(HttpServletRequest request,OrdergoodsCategory ordergoodsCategory){
		ModelAndView mv = new ModelAndView();
		List<OrdergoodsCategory> list = ordergoodsService.getOrdergoodsCategory(0,ordergoodsCategory.getOrdergoodsStoreId(),true);
		
		OrdergoodsCategory cate = ordergoodsService.getOrdergoodsCategoryById(ordergoodsCategory);
		mv.setViewName("ordergoods/category/category_edit");
		mv.addObject("categoryList", list);
		mv.addObject("ordergoodsCategory", cate);
		try {
			mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(cate)));
		} catch (Exception e) {
			logger.error("order goods editcategory:", e);
		}
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 保存编辑分类
	 * @param request
	 * @param ordergoodsCategory
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月21日 下午5:42:04
	 */
	@ResponseBody
	@RequestMapping(value = "saveeditcategory")
	@SystemControllerLog(operationType = "edit",desc = "保存编辑店铺分类")
	public ResultInfo saveeditCategory(HttpServletRequest request,OrdergoodsCategory ordergoodsCategory,HttpSession session,String beforeParams){
		ResultInfo resultInfo = new ResultInfo<>();
		User user =(User)session.getAttribute(ErpConst.CURR_USER);
		if(user!=null){
			ordergoodsCategory.setUpdater(user.getUserId());
			ordergoodsCategory.setModTime(DateUtil.sysTimeMillis());
		}
		if(ordergoodsCategory.getSort() == null){
			ordergoodsCategory.setSort(100);
		}
		
		Integer level = 1;
		if(null != ordergoodsCategory.getParentId() && ordergoodsCategory.getParentId() > 0){
			OrdergoodsCategory category = new OrdergoodsCategory();
			category.setOrdergoodsCategoryId(ordergoodsCategory.getParentId());
			
			OrdergoodsCategory ordergoodsCategoryById = ordergoodsService.getOrdergoodsCategoryById(category);
			level = ordergoodsCategoryById.getLevel()+1;
		}
		
		OrdergoodsCategory ordergoodsCategoryById = ordergoodsService.getOrdergoodsCategoryById(ordergoodsCategory);
		
		OrdergoodsCategory cate = new OrdergoodsCategory();
		cate.setCategoryName(ordergoodsCategory.getCategoryName());
		cate.setLevel(level);
		cate.setOrdergoodsStoreId(ordergoodsCategoryById.getOrdergoodsStoreId());
		OrdergoodsCategory oldInfo = ordergoodsService.getOrdergoodsCategoryByCate(ordergoodsCategory);
		if(null != oldInfo && !oldInfo.getOrdergoodsCategoryId().equals(ordergoodsCategory.getOrdergoodsCategoryId())){
			resultInfo.setMessage("分类名称不允许重复");
			return resultInfo;
		}
		
		ordergoodsCategory.setLevel(level);
		Boolean update = false;
		if(!ordergoodsCategoryById.getLevel().equals(level)){
			update = true;
		}
		ordergoodsCategory.setOrdergoodsStoreId(ordergoodsCategoryById.getOrdergoodsStoreId());
		Integer res = ordergoodsService.saveEditCategory(ordergoodsCategory,update,ordergoodsCategoryById.getLevel());
		if(res > 0){
			//同步
			ordergoodsSoapService.ordergoodsCategorySync(ordergoodsCategory.getOrdergoodsCategoryId());
			
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
		}
		return resultInfo;
	}
	
	/**
	 * <b>Description:</b><br> 订货产品列表
	 * @param request
	 * @param ordergoodsGoods
	 * @param pageNo
	 * @param pageSize
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月21日 下午5:46:33
	 */
	@ResponseBody
	@RequestMapping(value = "goodslist")
	public ModelAndView goodsList(HttpServletRequest request, OrdergoodsGoodsVo ordergoodsGoodsVo,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize, HttpSession session){
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView();
		Page page = getPageTag(request, pageNo, pageSize);
		
		Map<String, Object> map = ordergoodsService.getGoodsListPage(ordergoodsGoodsVo,page);
		
		OrdergoodsStore ordergoodsStore = new OrdergoodsStore();
		ordergoodsStore.setOrdergoodsStoreId(ordergoodsGoodsVo.getOrdergoodsStoreId());
		OrdergoodsStore store = ordergoodsService.getStoreById(ordergoodsStore);
		
		mv.addObject("ordergoodsGoodsList", (List<OrdergoodsGoodsVo>) map.get("list"));
		mv.addObject("page", (Page) map.get("page"));
		mv.addObject("ordergoodsStore", store);
		mv.addObject("ordergoodsGoodsVo", ordergoodsGoodsVo);
		mv.setViewName("ordergoods/goods/goods_index");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 上传产品
	 * @param request
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月25日 下午4:58:29
	 */
	@ResponseBody
	@RequestMapping(value="/uplodebatchgoods")
	public ModelAndView uplodeBatchGoods(HttpServletRequest request,@RequestParam("ordergoodsStoreId") Integer ordergoodsStoreId){
		ModelAndView mv=new ModelAndView("ordergoods/goods/uplode_batch_goods");
		mv.addObject("ordergoodsStoreId", ordergoodsStoreId);
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 保存产品
	 * @param request
	 * @param session
	 * @param lwfile
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月25日 下午4:58:59
	 */
	@ResponseBody
	@RequestMapping("saveuplodebatchgoods")
	@SystemControllerLog(operationType = "add",desc = "保存批量上传店铺商品")
	public ResultInfo<?> saveUplodeBatchGoods(HttpServletRequest request,HttpSession session,
			@RequestParam("lwfile") MultipartFile lwfile,@RequestParam("ordergoodsStoreId") Integer ordergoodsStoreId) {
		ResultInfo<?> resultInfo = new ResultInfo<>();
		try {
			User user = (User)session.getAttribute(Consts.SESSION_USER);
			//临时文件存放地址
			String path = request.getSession().getServletContext().getRealPath("/upload/ordergoods");
			FileInfo fileInfo = ftpUtilService.fileUploadServe(path,lwfile);
			if(fileInfo.getCode()==0){
				List<OrdergoodsGoodsVo> list = new ArrayList<>();
				// 获取excel路径
				Workbook workbook = WorkbookFactory.create(new FileInputStream(new File(fileInfo.getFilePath())));
				// 获取第一面sheet
				Sheet sheet = workbook.getSheetAt(0);
				// 起始行
				int startRowNum = sheet.getFirstRowNum() + 1;
				int endRowNum = sheet.getLastRowNum();// 结束行
				
				for (int rowNum = startRowNum; rowNum <= endRowNum; rowNum++) {// 循环行数
					Row row = sheet.getRow(rowNum);
					int startCellNum = row.getFirstCellNum();// 起始列
//					int endCellNum = row.getLastCellNum() - 1;// 结束列
					int endCellNum = sheet.getRow(0).getPhysicalNumberOfCells()-1;
					// 获取excel的值
					OrdergoodsGoodsVo ordergoodsGoodsVo = new OrdergoodsGoodsVo();
					ordergoodsGoodsVo.setOrdergoodsStoreId(ordergoodsStoreId);
					ordergoodsGoodsVo.setCompanyId(user.getCompanyId());
					
					for (int cellNum = startCellNum; cellNum <= endCellNum; cellNum++) {// 循环列数（下表从0开始）
						Cell cell = row.getCell(cellNum);
						String str = null;
						if(cellNum==0){//sku
							 //若excel中无内容，而且没有空格，cell为空--默认3，空白
							if (cell == null || cell.getCellType() != 1) {
								 resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列订货号错误！");
								 throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列订货号错误！");
							 }else{
								 ordergoodsGoodsVo.setSku(cell.getStringCellValue().toString());
							}
						}
						if(cellNum==5){//规格
							if(cell != null){
								if(cell.getStringCellValue().toString().length() > 128){
									resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "规格长度应在128字符以内！");
									throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "规格长度应在128字符以内！");
								}else{
									ordergoodsGoodsVo.setSpec(cell.getStringCellValue().toString());
								}
							}
						}
						if(cellNum==6){//适用机型
							if(cell != null){
								if(cell.getStringCellValue().toString().length() > 128){
									resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "适用机型长度应在128字符以内！");
									throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "适用机型长度应在128字符以内！");
								}else{
									ordergoodsGoodsVo.setUsed(cell.getStringCellValue().toString());
								}
							}
						}
						if(cellNum==7){//计价单位
							if(cell != null){
								if(cell.getStringCellValue().toString().length() > 16){
									resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "计价单位长度应在16字符以内！");
									throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "计价单位长度应在16字符以内！");
								}else{
									ordergoodsGoodsVo.setUnit(cell.getStringCellValue().toString());
								}
							}
						}
						String priceRegex = "(^[1-9]([0-9]+)?(\\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\\.[0-9]([0-9])?$)";
						if(cellNum==8){//成本价
							if(cell != null){
								double numericCellValue = cell.getNumericCellValue();
								str = String.format("%.2f", numericCellValue);
								if(!RegexUtil.match(priceRegex, str)){
									resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "成本价仅允许使用数字，最多精确到小数后两位！");
									throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "成本价仅允许使用数字，最多精确到小数后两位！");
								}else if(numericCellValue > 10000000){
									resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "成本价不允许超过一千万！");
									throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "成本价不允许超过一千万！");
								}else{
									ordergoodsGoodsVo.setCostPrice(new BigDecimal(str));
								}
							}
						}
						if(cellNum==9){//零售价
							if(cell == null){
								resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "零售价不允许为空！");
								throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "零售价不允许为空！");
							}else{
								double numericCellValue = cell.getNumericCellValue();
								str = String.format("%.2f", numericCellValue);
								if(!RegexUtil.match(priceRegex, str)){
									resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "零售价仅允许使用数字，最多精确到小数后两位！");
									throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "零售价仅允许使用数字，最多精确到小数后两位！");
								}else if(numericCellValue > 10000000){
									resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "零售价不允许超过一千万！");
									throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "零售价不允许超过一千万！");
								}else{
									ordergoodsGoodsVo.setRetailPrice(new BigDecimal(str));
								}
							}
						}
						if(cellNum==10){//备注
							if(cell != null){
								if(cell.getStringCellValue().toString().length() > 256){
									resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "备注长度应在256字符以内！");
									throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "备注长度应在256字符以内！");
								}else{
									ordergoodsGoodsVo.setComments(cell.getStringCellValue().toString());
								}
							}
						}
						if(cellNum==11){//检测方法/分析方法
							if(cell != null){
								if(cell.getStringCellValue().toString().length() > 256){
									resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "检测方法/分析方法长度应在256字符以内！");
									throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "检测方法/分析方法长度应在256字符以内！");
								}else{
									ordergoodsGoodsVo.setTestMethod(cell.getStringCellValue().toString());
								}
							}
						}
						if(cellNum==12){//销售公司
							if(cell != null){
								if(cell.getStringCellValue().toString().length() > 128){
									resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "销售公司长度应在128字符以内！");
									throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "销售公司长度应在128字符以内！");
								}else{
									ordergoodsGoodsVo.setSupplyCompany(cell.getStringCellValue().toString());
								}
							}
						}
						if(cellNum==13){//运输条件(冷藏/常温)
							if(cell == null || cell.equals("")){
								resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "运输条件不允许为空！");
								throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "运输条件不允许为空！");
							}else if(!cell.getStringCellValue().toString().equals("冷藏") && !cell.getStringCellValue().toString().equals("常温") && !cell.getStringCellValue().toString().equals("血球冷藏")){
								resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "运输条件错误！");
								throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "运输条件错误！");
							}else{
								if(cell.getStringCellValue().toString().equals("常温")){
									ordergoodsGoodsVo.setTransportRequirements(1);
								}
								if(cell.getStringCellValue().toString().equals("冷藏")){
									ordergoodsGoodsVo.setTransportRequirements(2);
								}
								if(cell.getStringCellValue().toString().equals("血球冷藏")){
									ordergoodsGoodsVo.setTransportRequirements(3);
								}
							}
							
						}
						
					}
					
					
					list.add(ordergoodsGoodsVo);
				}
				
				//保存更新
				resultInfo = ordergoodsService.saveUplodeBatchGoods(list,session);
				
				//同步
				if(resultInfo.getCode().equals(0)){
					ordergoodsSoapService.ordergoodsGoodsBatchSync((List<OrdergoodsGoodsVo>)resultInfo.getListData());
				}
			}
		}catch(Exception e) {
			logger.error("saveuplodebatchgoods:", e);
			return resultInfo;
		}
		return resultInfo; 
	}
	
	/**
	 * <b>Description:</b><br> 删除订货产品
	 * @param request
	 * @param ordergoodsGoods
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月26日 上午9:46:48
	 */
	@ResponseBody
	@RequestMapping(value = "deletegoods")
	@SystemControllerLog(operationType = "delete",desc = "删除订货产品")
	public ResultInfo deleteOrdergoodsGoods(HttpServletRequest request,OrdergoodsGoodsVo ordergoodsGoodsVo,HttpSession session){
		ResultInfo resultInfo = new ResultInfo<>();
		if(null == ordergoodsGoodsVo || null == ordergoodsGoodsVo.getOrdergoodsGoodsId()){
			return resultInfo;
		}
		
		Boolean res = ordergoodsService.deleteOrdergoodsGoods(ordergoodsGoodsVo);
		if(res){
			//同步
			ordergoodsSoapService.delOrdergoodsCategoryGoodsSync(ordergoodsGoodsVo.getGoodsId(),ordergoodsGoodsVo.getOrdergoodsGoodsId(),ordergoodsGoodsVo.getOrdergoodsStoreId());
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
		}
		return resultInfo;
	}
	
	/**
	 * <b>Description:</b><br> 获取订货分类产品
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月26日 上午10:25:32
	 */
	@ResponseBody
	@RequestMapping(value = "getcategorygoods")
	public ModelAndView getOrdergoodsCategoryGoods(HttpServletRequest request, OrdergoodsGoodsVo ordergoodsGoodsVo,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize, HttpSession session){
		ModelAndView mv = new ModelAndView();
		Page page = getPageTag(request, pageNo, pageSize);
		
		Map<String, Object> map = ordergoodsService.getGoodsListPage(ordergoodsGoodsVo,page);
		
		OrdergoodsStore ordergoodsStore = new OrdergoodsStore();
		ordergoodsStore.setOrdergoodsStoreId(ordergoodsGoodsVo.getOrdergoodsStoreId());
		OrdergoodsStore store = ordergoodsService.getStoreById(ordergoodsStore);
		
		OrdergoodsCategory ordergoodsCategory = new  OrdergoodsCategory();
		ordergoodsCategory.setOrdergoodsCategoryId(ordergoodsGoodsVo.getOrdergoodsCategoryId());
		OrdergoodsCategory category = ordergoodsService.getOrdergoodsCategoryById(ordergoodsCategory);
		
		mv.addObject("ordergoodsGoodsList", (List<OrdergoodsGoodsVo>) map.get("list"));
		mv.addObject("page", (Page) map.get("page"));
		mv.addObject("ordergoodsStore", store);
		mv.addObject("ordergoodsGoodsVo", ordergoodsGoodsVo);
		mv.addObject("ordergoodsCategory", category);
		mv.setViewName("ordergoods/category/category_goods");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 删除分类产品
	 * @param request
	 * @param ordergoodsGoodsVo
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月26日 上午11:03:13
	 */
	@ResponseBody
	@RequestMapping(value = "deletecategoods")
	@SystemControllerLog(operationType = "delete",desc = "删除分类产品")
	public ResultInfo deleteOrdergoodsCategoryGoods(HttpServletRequest request,OrdergoodsGoodsVo ordergoodsGoodsVo,HttpSession session){
		ResultInfo resultInfo = new ResultInfo<>();
		
		if(null == ordergoodsGoodsVo || null == ordergoodsGoodsVo.getrOrdergoodsGoodsJCategoryId()){
			return resultInfo;
		}
		Boolean res = ordergoodsService.deleteOrdergoodsCategoryGoods(ordergoodsGoodsVo);
		if(res){
			//同步
			ordergoodsSoapService.delOrdergoodsCategoryGoodsSync(ordergoodsGoodsVo.getGoodsId(),ordergoodsGoodsVo.getOrdergoodsGoodsId(),ordergoodsGoodsVo.getOrdergoodsStoreId());
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
		}
		return resultInfo;
	}
	
	/**
	 * <b>Description:</b><br> 分类绑定产品
	 * @param request
	 * @param searchContent
	 * @param ordergoodsCategoryId
	 * @param session
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月26日 上午11:18:29
	 */
	@ResponseBody
	@RequestMapping(value = "bindcategorygoods")
	public ModelAndView bindCategoryGoods(HttpServletRequest request,@RequestParam(value="searchContent",required=false)String searchContent,
			OrdergoodsGoodsVo ordergoodsGoodsVo,HttpSession session,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo, 
			@RequestParam(required = false, defaultValue = "10") Integer pageSize){
		ModelAndView mv = new ModelAndView();
		try {
			if(StringUtils.isNoneBlank(searchContent)){
				Page page = getPageTag(request,pageNo,pageSize);
				OrdergoodsGoodsVo goodsVo = new OrdergoodsGoodsVo();
				goodsVo.setSearchContent(ordergoodsGoodsVo.getSearchContent());
				goodsVo.setOrdergoodsStoreId(ordergoodsGoodsVo.getOrdergoodsStoreId());
				
				goodsVo.setIsValid(1);//只要审核通过的产品
				Map<String, Object> map = ordergoodsService.getGoodsListPage(goodsVo,page);
				mv.addObject("ordergoodsGoodsList",(List<OrdergoodsGoodsVo>)map.get("list"));
				mv.addObject("page", (Page)map.get("page"));
				mv.addObject("searchContent", searchContent);
			}
			mv.addObject("ordergoodsGoodsVo", ordergoodsGoodsVo);
		} catch (Exception e) {
			logger.error("bindcategorygoods:", e);
		}
		mv.setViewName("ordergoods/category/category_bind_goods");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 分类绑定产品
	 * @param request
	 * @param ordergoodsCategoryId
	 * @param ids
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月26日 下午1:28:49
	 */
	@ResponseBody
	@RequestMapping(value = "savebindcategorygoods")
	@SystemControllerLog(operationType = "add",desc = "保存分类绑定产品")
	public ResultInfo saveBindCategoryGoods(HttpServletRequest request,
			@RequestParam("ordergoodsCategoryId")Integer ordergoodsCategoryId,
			@RequestParam("ids")String ids){
		ResultInfo resultInfo = new ResultInfo<>();
		Boolean suc = ordergoodsService.saveBindCategoryGoods(ordergoodsCategoryId,ids);
		if(suc){
			//同步
			ordergoodsSoapService.ordergoodsCategoryGoodsSync(ordergoodsCategoryId, ids);
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
		}
		return resultInfo;
	}
	
	/**
	 * <b>Description:</b><br> 更改排序
	 * @param request
	 * @param ordergoodsCategoryId
	 * @param ids
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月26日 下午2:07:29
	 */
	@ResponseBody
	@RequestMapping(value = "changsort")
	@SystemControllerLog(operationType = "edit",desc = "更改产品排序")
	public ResultInfo changSort(HttpServletRequest request,ROrdergoodsGoodsJCategory rOrdergoodsGoodsJCategory){
		ResultInfo resultInfo = new ResultInfo<>();
		Boolean suc = ordergoodsService.changSort(rOrdergoodsGoodsJCategory);
		if(suc){
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
		}
		return resultInfo;
	}
	
	/**
	 * <b>Description:</b><br> 设备试剂关系
	 * @param request
	 * @param ordergoodsGoodsVo
	 * @param pageNo
	 * @param pageSize
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月26日 下午2:27:46
	 */
	@ResponseBody
	@RequestMapping(value = "goodscategories")
	public ModelAndView goodsCategories(HttpServletRequest request, ROrdergoodsLaunchGoodsJCategoryVo rOrdergoodsLaunchGoodsJCategoryVo,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize, HttpSession session){
		ModelAndView mv = new ModelAndView();
		Page page = getPageTag(request, pageNo, pageSize);
		
		Map<String, Object> map = ordergoodsService.getGoodsCategoriesListPage(rOrdergoodsLaunchGoodsJCategoryVo,page);
		
		OrdergoodsStore ordergoodsStore = new OrdergoodsStore();
		ordergoodsStore.setOrdergoodsStoreId(rOrdergoodsLaunchGoodsJCategoryVo.getOrdergoodsStoreId());
		OrdergoodsStore store = ordergoodsService.getStoreById(ordergoodsStore);
		
		List<OrdergoodsCategory> ordergoodsCategry = ordergoodsService.getOrdergoodsCategry(rOrdergoodsLaunchGoodsJCategoryVo.getOrdergoodsStoreId());
		
		mv.addObject("ordergoodsGoodsList", (List<ROrdergoodsLaunchGoodsJCategoryVo>) map.get("list"));
		mv.addObject("page", (Page) map.get("page"));
		mv.addObject("ordergoodsStore", store);
		mv.addObject("rOrdergoodsLaunchGoodsJCategoryVo", rOrdergoodsLaunchGoodsJCategoryVo);
		mv.addObject("ordergoodsCategry", ordergoodsCategry);
		mv.setViewName("ordergoods/goods/goods_cate_index");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br>添加设备与试剂分类 
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月27日 上午10:21:01
	 */
	@ResponseBody
	@RequestMapping(value = "addequipmentcategory")
	public ModelAndView addEquipmentCategory(HttpServletRequest request,Integer ordergoodsStoreId,
			@RequestParam(value="searchContent",required=false)String searchContent,HttpSession session,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo, 
			@RequestParam(required = false, defaultValue = "10") Integer pageSize){
		ModelAndView mv = new ModelAndView();
		if(StringUtils.isNoneBlank(searchContent)){
			User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
			Page page = getPageTag(request,pageNo,pageSize);
			Goods goods = new Goods();
			goods.setCompanyId(user.getCompanyId());
			goods.setSearchContent(searchContent);
			Map<String, Object> map = goodsService.getGoodsBaseinfoListPage(goods,page,session);
			
			List<OrdergoodsCategory> ordergoodsCategry = ordergoodsService.getOrdergoodsCategry(ordergoodsStoreId);
			
			mv.addObject("goodsList",(List<GoodsVo>)map.get("list"));
			mv.addObject("page", (Page)map.get("page"));
			
			mv.addObject("ordergoodsStoreId", ordergoodsStoreId);
			mv.addObject("ordergoodsCategry", ordergoodsCategry);
		}
		mv.setViewName("ordergoods/goods/add_equipment_category");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 是否已经存在设备
	 * @param request
	 * @param rOrdergoodsLaunchGoodsJCategoryVo
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月27日 下午2:20:45
	 */
	@ResponseBody
	@RequestMapping(value = "isexitgoodscategroy")
	public ResultInfo isExitGoodsCategroy(HttpServletRequest request,ROrdergoodsLaunchGoodsJCategoryVo rOrdergoodsLaunchGoodsJCategoryVo){
		ResultInfo resultInfo = new ResultInfo<>();
		
		ROrdergoodsLaunchGoodsJCategoryVo info = ordergoodsService.getSotreGoods(rOrdergoodsLaunchGoodsJCategoryVo);
		if(null == info){
			resultInfo.setCode(0);
			resultInfo.setMessage("产品不存在");
		}
		return resultInfo;
	}
	
	/**
	 * <b>Description:</b><br> 保存设备与试剂分类关系
	 * @param request
	 * @param rOrdergoodsLaunchGoodsJCategoryVo
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月27日 下午2:49:38
	 */
	@ResponseBody
	@RequestMapping(value = "saveaddequipmentcategory")
	@SystemControllerLog(operationType = "add",desc = "保存设备与试剂分类关系")
	public ResultInfo saveAddEquipmentCategory(HttpServletRequest request,ROrdergoodsLaunchGoodsJCategory rOrdergoodsLaunchGoodsJCategory){
		ResultInfo resultInfo = new ResultInfo<>();
		
		Integer suc = ordergoodsService.saveAddEquipmentCategory(rOrdergoodsLaunchGoodsJCategory);
		if(suc > 0){
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
		}
		return resultInfo;
	}
	
	/**
	 * <b>Description:</b><br> 编辑设备与试剂分类
	 * @param request
	 * @param ordergoodsStoreId
	 * @param searchContent
	 * @param session
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月27日 下午3:01:02
	 */
	@ResponseBody
	@RequestMapping(value = "editequipmentcategory")
	public ModelAndView EditEquipmentCategory(HttpServletRequest request,ROrdergoodsLaunchGoodsJCategoryVo rOrdergoodsLaunchGoodsJCategoryVo){
		ModelAndView mv = new ModelAndView();
		ROrdergoodsLaunchGoodsJCategoryVo sotreGoods = ordergoodsService.getSotreGoodsInfo(rOrdergoodsLaunchGoodsJCategoryVo);
		List<OrdergoodsCategory> ordergoodsCategry = ordergoodsService.getOrdergoodsCategry(sotreGoods.getOrdergoodsStoreId());
		mv.addObject("rOrdergoodsLaunchGoodsJCategoryVo", sotreGoods);
		mv.addObject("ordergoodsCategry", ordergoodsCategry);
		mv.setViewName("ordergoods/goods/edit_equipment_category");
		try {
			mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(sotreGoods)));
		} catch (Exception e) {
			logger.error("editequipmentcategory:", e);
		}
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 保存编辑设备试剂分类
	 * @param request
	 * @param rOrdergoodsLaunchGoodsJCategory
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月27日 下午3:20:10
	 */
	@ResponseBody
	@RequestMapping(value = "saveeditequipmentcategory")
	@SystemControllerLog(operationType = "edit",desc = "保存编辑设备与试剂分类")
	public ResultInfo saveEditEquipmentCategory(HttpServletRequest request,ROrdergoodsLaunchGoodsJCategory rOrdergoodsLaunchGoodsJCategory,String beforeParams){
		ResultInfo resultInfo = new ResultInfo<>();
		
		Integer suc = ordergoodsService.saveEditEquipmentCategory(rOrdergoodsLaunchGoodsJCategory);
		if(suc > 0){
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
		}
		return resultInfo;
	}
	
	/**
	 * <b>Description:</b><br> 投放列表
	 * @param request
	 * @param ordergoodsLaunchVo
	 * @param pageNo
	 * @param pageSize
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月27日 下午4:12:22
	 */
	@ResponseBody
	@RequestMapping(value = "ordergoodslaunch")
	public ModelAndView orderGoodsLaunch(HttpServletRequest request, OrdergoodsLaunchVo ordergoodsLaunchVo,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize, HttpSession session){
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView();
		Page page = getPageTag(request, pageNo, pageSize);
		
		Map<String, Object> map = ordergoodsService.getOrdergoodsLaunchListPage(ordergoodsLaunchVo,page,session);
		
		OrdergoodsStore ordergoodsStore = new OrdergoodsStore();
		ordergoodsStore.setOrdergoodsStoreId(ordergoodsLaunchVo.getOrdergoodsStoreId());
		OrdergoodsStore store = ordergoodsService.getStoreById(ordergoodsStore);
		
		mv.addObject("ordergoodsLaunchList", (List<OrdergoodsLaunchVo>) map.get("list"));
		mv.addObject("page", (Page) map.get("page"));
		mv.addObject("ordergoodsStore", store);
		mv.addObject("ordergoodsLaunchVo", ordergoodsLaunchVo);
		mv.setViewName("ordergoods/launch/launch_index");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 添加投放
	 * @param request
	 * @param ordergoodsLaunchVo
	 * @param pageNo
	 * @param pageSize
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月27日 下午5:22:03
	 */
	@ResponseBody
	@RequestMapping(value = "addordergoodslaunch")
	public ModelAndView addOrderGoodsLaunch(HttpServletRequest request, OrdergoodsLaunchVo ordergoodsLaunchVo,HttpSession session){
		ModelAndView mv = new ModelAndView();
		
		OrdergoodsStore ordergoodsStore = new OrdergoodsStore();
		ordergoodsStore.setOrdergoodsStoreId(ordergoodsLaunchVo.getOrdergoodsStoreId());
		OrdergoodsStore store = ordergoodsService.getStoreById(ordergoodsStore);
		mv.setViewName("ordergoods/launch/launch_add");
		mv.addObject("ordergoodsStore", store);
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 搜索客户 
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月28日 上午9:18:59
	 */
	@ResponseBody
	@RequestMapping(value = "searchtradercustomer")
	public ModelAndView searchTraderCustomer(HttpServletRequest request,String searchName,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo, 
			@RequestParam(required = false, defaultValue = "10") Integer pageSize){
		ModelAndView mv = new ModelAndView();
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		try {
			if(StringUtils.isNoneBlank(searchName)){
				Page page = getPageTag(request,pageNo,pageSize);
				TraderVo traderVo = new TraderVo();
				traderVo.setCompanyId(user.getCompanyId());
				traderVo.setTraderName(URLDecoder.decode(searchName, "UTF-8"));
				traderVo.setTraderType(ErpConst.ONE);
				Map<String, Object> map = ordergoodsService.getTraderCustomerListPage(traderVo,page);
				mv.addObject("list",(List<TraderVo>)map.get("list"));
				mv.addObject("page", (Page)map.get("page"));
				mv.addObject("searchName", URLDecoder.decode(searchName, "UTF-8"));
			}
		} catch (Exception e) {
			logger.error("searchtradercustomer:", e);
		}
		mv.setViewName("ordergoods/launch/search_customer");
		return mv;
		
	}
	
	/**
	 * <b>Description:</b><br> 订货系统搜索客户
	 * @param request
	 * @param searchName
	 * @param ordergoodsStoreId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年8月1日 下午4:39:46
	 */
	@ResponseBody
	@RequestMapping(value = "searchtradercustomernew")
	public ModelAndView searchTraderCustomerNew(HttpServletRequest request,String searchName,Integer ordergoodsStoreId,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo, 
			@RequestParam(required = false, defaultValue = "10") Integer pageSize){
		ModelAndView mv = new ModelAndView();
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		try {
			if(StringUtils.isNoneBlank(searchName)){
				Page page = getPageTag(request,pageNo,pageSize);
				TraderVo traderVo = new TraderVo();
				traderVo.setCompanyId(user.getCompanyId());
				traderVo.setTraderName(URLDecoder.decode(searchName, "UTF-8"));
				traderVo.setTraderType(ErpConst.ONE);
				traderVo.setOrdergoodsStoreId(ordergoodsStoreId);
				Map<String, Object> map = ordergoodsService.getTraderCustomerListPage(traderVo,page);
				mv.addObject("list",(List<TraderVo>)map.get("list"));
				mv.addObject("page", (Page)map.get("page"));
				mv.addObject("searchName", URLDecoder.decode(searchName, "UTF-8"));
			}
		} catch (Exception e) {
			logger.error("searchtradercustomernew:", e);
		}
		mv.addObject("ordergoodsStoreId",ordergoodsStoreId);
		mv.setViewName("ordergoods/launch/search_customer_new");
		return mv;
		
	}
	/**
	 * <b>Description:</b><br> 搜索设备产品
	 * @param request
	 * @param searchName
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月28日 上午10:48:02
	 */
	@ResponseBody
	@RequestMapping(value = "searchgoods")
	public ModelAndView searchLaunchGoods(HttpServletRequest request,String searchContent,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo, 
			@RequestParam(required = false, defaultValue = "10") Integer pageSize, HttpSession session){
		ModelAndView mv = new ModelAndView();
		try {
			if(StringUtils.isNoneBlank(searchContent)){
				Page page = getPageTag(request, pageNo, pageSize);
				ROrdergoodsLaunchGoodsJCategoryVo rOrdergoodsLaunchGoodsJCategoryVo = new ROrdergoodsLaunchGoodsJCategoryVo();
				rOrdergoodsLaunchGoodsJCategoryVo.setSearchContent(searchContent);
				Map<String, Object> map = ordergoodsService.getGoodsCategoriesListPage(rOrdergoodsLaunchGoodsJCategoryVo,page);
				mv.addObject("goodsList", (List<ROrdergoodsLaunchGoodsJCategoryVo>) map.get("list"));
				mv.addObject("page", (Page) map.get("page"));
			}
		} catch (Exception e) {
			logger.error("searchgoods:", e);
		}
		mv.setViewName("ordergoods/launch/search_goods");
		return mv;
		
	}
	
	/**
	 * <b>Description:</b><br> 保存投放
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月28日 上午11:35:14
	 */
	@ResponseBody
	@RequestMapping(value = "saveaddordergoodslaunch")
	@SystemControllerLog(operationType = "add",desc = "保存投放")
	public ModelAndView saveAddOrdergoodsLaunch(HttpServletRequest request, HttpSession session,
			OrdergoodsLaunch ordergoodsLaunch,
			String[] startdate,String[] enddate,String[] goal){
		ModelAndView mv = new ModelAndView();
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		
		Integer ordergoodsLaunchId = ordergoodsService.saveAddOrdergoodsLaunch(ordergoodsLaunch,startdate,enddate,goal,session);
		if(ordergoodsLaunchId > 0){
			mv.addObject("url", "./viewordergoodslaunch.do?ordergoodsLaunchId=" + ordergoodsLaunchId);
			return success(mv);
		}else{
			return this.fail(mv);
		}
	}
	
	/**
	 * <b>Description:</b><br> 查看投放
	 * @param request
	 * @param ordergoodsLaunch
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月28日 下午1:10:57
	 */
	@ResponseBody
	@RequestMapping(value = "viewordergoodslaunch")
	public ModelAndView viewOrdergoodsLaunch(HttpServletRequest request,OrdergoodsLaunch ordergoodsLaunch,HttpSession session,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize){
		ModelAndView mv = new ModelAndView();
		if(null == ordergoodsLaunch ||  null == ordergoodsLaunch.getOrdergoodsLaunchId()){
			return pageNotFound();
		}
		//投放主表信息
		OrdergoodsLaunchVo ordergoodsLaunchVo = ordergoodsService.getOrdergoodsLaunch(ordergoodsLaunch,session);
		if(null == ordergoodsLaunchVo){
			return pageNotFound();
		}
		List<OrdergoodsLaunchGoal> goalList = ordergoodsService.getOrdergoodsLaunchGoal(ordergoodsLaunch);
		
		OrdergoodsStore ordergoodsStore = new OrdergoodsStore();
		ordergoodsStore.setOrdergoodsStoreId(ordergoodsLaunchVo.getOrdergoodsStoreId());
		OrdergoodsStore store = ordergoodsService.getStoreById(ordergoodsStore);
		
		//每月完成情况
		OrdergoodsLaunchVo monthOrdergoodsLaunch = ordergoodsService.getLaunchMonthGoal(ordergoodsLaunchVo,session);
		
		//相关订单
		Page page = getPageTag(request, pageNo, pageSize);
		List<Saleorder> orderList = ordergoodsService.getLaunchSaleorderListPage(ordergoodsLaunchVo,page,session);
		
		mv.addObject("ordergoodsStore", store);
		mv.addObject("ordergoodsLaunchVo",ordergoodsLaunchVo);
		mv.addObject("goalList",goalList);
		mv.addObject("monthOrdergoodsLaunch",monthOrdergoodsLaunch.getMonthAmountList());
		mv.addObject("orderList",orderList);
		mv.setViewName("ordergoods/launch/launch_view");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 编辑投放
	 * @param request
	 * @param ordergoodsLaunch
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月28日 下午2:25:26
	 */
	@ResponseBody
	@RequestMapping(value = "editordergoodslaunch")
	public ModelAndView editOrdergoodsLaunch(HttpServletRequest request,OrdergoodsLaunch ordergoodsLaunch,HttpSession session){
		ModelAndView mv = new ModelAndView();
		if(null == ordergoodsLaunch ||  null == ordergoodsLaunch.getOrdergoodsLaunchId()){
			return pageNotFound();
		}
		
		//投放主表信息
		OrdergoodsLaunchVo ordergoodsLaunchVo = ordergoodsService.getOrdergoodsLaunch(ordergoodsLaunch,session);
		List<OrdergoodsLaunchGoal> goalList = ordergoodsService.getOrdergoodsLaunchGoal(ordergoodsLaunch);
		
		OrdergoodsStore ordergoodsStore = new OrdergoodsStore();
		ordergoodsStore.setOrdergoodsStoreId(ordergoodsLaunchVo.getOrdergoodsStoreId());
		OrdergoodsStore store = ordergoodsService.getStoreById(ordergoodsStore);
		
		mv.addObject("ordergoodsStore", store);
		mv.addObject("ordergoodsLaunchVo",ordergoodsLaunchVo);
		mv.addObject("goalList",goalList);
		try {
			mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(ordergoodsLaunchVo)));
		} catch (Exception e) {
			logger.error("editordergoodslaunch:", e);
		}
		mv.setViewName("ordergoods/launch/launch_edit");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 保存编辑投放
	 * @param request
	 * @param session
	 * @param ordergoodsLaunch
	 * @param startdate
	 * @param enddate
	 * @param goal
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月28日 下午3:24:51
	 */
	@ResponseBody
	@RequestMapping(value = "saveeditordergoodslaunch")
	@SystemControllerLog(operationType = "edit",desc = "保存编辑投放")
	public ModelAndView saveEditOrdergoodsLaunch(HttpServletRequest request, HttpSession session,
			OrdergoodsLaunch ordergoodsLaunch,
			String[] startdate,String[] enddate,String[] goal,String beforeParams){
		if(null == ordergoodsLaunch || null == ordergoodsLaunch.getOrdergoodsLaunchId()){
			return pageNotFound();
		}
		ModelAndView mv = new ModelAndView();
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		
		Boolean res = ordergoodsService.saveEditOrdergoodsLaunch(ordergoodsLaunch,startdate,enddate,goal,session);
		if(res){
			mv.addObject("url", "./viewordergoodslaunch.do?ordergoodsLaunchId=" + ordergoodsLaunch.getOrdergoodsLaunchId());
			return success(mv);
		}else{
			return this.fail(mv);
		}
	}
	
	/**
	 * <b>Description:</b><br> 经销商列表
	 * @param request
	 * @param ordergoodsLaunchVo
	 * @param pageNo
	 * @param pageSize
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月29日 下午1:33:23
	 */
	@ResponseBody
	@RequestMapping(value = "getordergoodsaccount")
	public ModelAndView getOrdergoodsAccount(HttpServletRequest request, OrdergoodsStoreAccountVo ordergoodsStoreAccountVo,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize, HttpSession session){
		ModelAndView mv = new ModelAndView();
		Page page = getPageTag(request, pageNo, pageSize);
		
		Map<String, Object> map = ordergoodsService.getOrdergoodsAccountListPage(ordergoodsStoreAccountVo,page,session);
		
		OrdergoodsStore ordergoodsStore = new OrdergoodsStore();
		ordergoodsStore.setOrdergoodsStoreId(ordergoodsStoreAccountVo.getOrdergoodsStoreId());
		OrdergoodsStore store = ordergoodsService.getStoreById(ordergoodsStore);
		
		mv.addObject("ordergoodsAccountList", (List<OrdergoodsStoreAccountVo>) map.get("list"));
		mv.addObject("page", (Page) map.get("page"));
		mv.addObject("ordergoodsStore", store);
		mv.setViewName("ordergoods/account/account_index");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 添加经销商
	 * @param request
	 * @param ordergoodsStoreAccountVo
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月29日 下午3:35:49
	 */
	@ResponseBody
	@RequestMapping(value = "addordergoodsaccount")
	public ModelAndView addOrdergoodsAccount(HttpServletRequest request,OrdergoodsStoreAccountVo ordergoodsStoreAccountVo, HttpSession session){
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView();
		OrdergoodsStore ordergoodsStore = new OrdergoodsStore();
		ordergoodsStore.setOrdergoodsStoreId(ordergoodsStoreAccountVo.getOrdergoodsStoreId());
		OrdergoodsStore store = ordergoodsService.getStoreById(ordergoodsStore);
		
		//销售
		List<Integer> salePositionType = new ArrayList<>();
		salePositionType.add(SysOptionConstant.ID_310);//销售
		List<User> saleUserList = userService.getMyUserList(user, salePositionType, false);
		mv.addObject("ordergoodsStore", store);
		mv.addObject("userList", saleUserList);
		mv.setViewName("ordergoods/account/account_add");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 保存经销商账号
	 * @param request
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年1月4日 上午10:44:46
	 */
	@ResponseBody
	@RequestMapping(value = "saveaddordergoodsaccount")
	@SystemControllerLog(operationType = "add",desc = "保存经销商账号")
	public ModelAndView saveAddOrdergoodsAccount(HttpServletRequest request,OrdergoodsStoreAccount ordergoodsStoreAccount, HttpSession session){
		if(null == ordergoodsStoreAccount.getOrdergoodsStoreId()
				|| null == ordergoodsStoreAccount.getTraderContactId()
				|| null == ordergoodsStoreAccount.getTraderAddressId()
				|| ordergoodsStoreAccount.getTraderContactId().equals(0)
				|| ordergoodsStoreAccount.getTraderAddressId().equals(0)
				|| ordergoodsStoreAccount.getOrdergoodsStoreId().equals(0)){
			return pageNotFound();
		}
		ModelAndView mv = new ModelAndView();
		//判断是否添加过账号
		OrdergoodsStoreAccount storeAccount = ordergoodsService.getOrdergoodsStoreAccount(ordergoodsStoreAccount);
		
		if(null != storeAccount){
			mv.addObject("message","该账号不允许重复添加");
			mv.addObject("url", "./viewordergoodsaccount.do?ordergoodsStoreAccountId=" + storeAccount.getOrdergoodsStoreAccountId());
			return fail(mv);
		}
		//先同步账号，成功后再在ERP里面增加账号信息
		ResultInfo resultInfo = ordergoodsSoapService.ordergoodsStoreAccountSync(ordergoodsStoreAccount,1);
		if(!resultInfo.getCode().equals(0)){//失败
			return fail(mv);
		}
		
		OrdergoodsStoreAccount account = (OrdergoodsStoreAccount) resultInfo.getData();
		
		Integer ordergoodsStoreAccountId = ordergoodsService.saveAddOrdergoodsAccount(account,session);
		if(ordergoodsStoreAccountId > 0){
			mv.addObject("url", "./viewordergoodsaccount.do?ordergoodsStoreAccountId=" + ordergoodsStoreAccountId);
			return success(mv);
		}else{
			return fail(mv);
		}
	}

	/**
	 * <b>Description:</b><br> 查看经销商账号
	 * @param request
	 * @param ordergoodsStoreAccountVo
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年1月4日 上午10:50:13
	 */
	@ResponseBody
	@RequestMapping(value = "viewordergoodsaccount")
	public ModelAndView viewOrdergoodsAccount(HttpServletRequest request,OrdergoodsStoreAccount ordergoodsStoreAccount, HttpSession session){
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		if(null == ordergoodsStoreAccount
				|| null == ordergoodsStoreAccount.getOrdergoodsStoreAccountId()
				|| ordergoodsStoreAccount.getOrdergoodsStoreAccountId().equals(0)){
			return pageNotFound();
		}
		
		ModelAndView mv = new ModelAndView();
		
		Map<String, Object> map = ordergoodsService.getOrdergoodsAccount(ordergoodsStoreAccount);
		
		OrdergoodsStoreAccountVo ordergoodsStoreAccountVo = (OrdergoodsStoreAccountVo) map.get("account");
		TraderCustomerVo traderCustomerVo = (TraderCustomerVo) map.get("customer");
		TraderFinanceVo financeVo = (TraderFinanceVo) map.get("finance");
		TraderAddressVo addressVo = (TraderAddressVo) map.get("address");
		OrdergoodsStore ordergoodsStore = new OrdergoodsStore();
		ordergoodsStore.setOrdergoodsStoreId(ordergoodsStoreAccountVo.getOrdergoodsStoreId());
		OrdergoodsStore store = ordergoodsService.getStoreById(ordergoodsStore);
		
		//销售
		List<Integer> salePositionType = new ArrayList<>();
		salePositionType.add(SysOptionConstant.ID_310);//销售
		List<User> saleUserList = userService.getMyUserList(user, salePositionType, false);
		
		mv.addObject("ordergoodsStore", store);
		mv.addObject("userList", saleUserList);
		mv.addObject("ordergoodsStoreAccount", ordergoodsStoreAccountVo);
		mv.addObject("traderCustomer", traderCustomerVo);
		mv.addObject("finance", financeVo);
		mv.addObject("address", addressVo);
		mv.setViewName("ordergoods/account/account_view");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 编辑经销商账号
	 * @param request
	 * @param ordergoodsStoreAccountVo
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年1月5日 上午10:06:33
	 */
	@ResponseBody
	@RequestMapping(value = "editordergoodsaccount")
	public ModelAndView editOrdergoodsAccount(HttpServletRequest request,OrdergoodsStoreAccount ordergoodsStoreAccount, HttpSession session){
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		if(null == ordergoodsStoreAccount
				|| null == ordergoodsStoreAccount.getOrdergoodsStoreAccountId()
				|| ordergoodsStoreAccount.getOrdergoodsStoreAccountId().equals(0)){
			return pageNotFound();
		}
		ModelAndView mv = new ModelAndView();
		Map<String, Object> map  = ordergoodsService.getEditOrdergoodsAccount(ordergoodsStoreAccount);
		OrdergoodsStoreAccountVo accountVo = (OrdergoodsStoreAccountVo) map.get("account");
		if(null == accountVo){
			return pageNotFound();
		}
		
		TraderCustomerVo traderCustomerVo = (TraderCustomerVo) map.get("customer");
		List<TraderAddressVo> addressList = (List<TraderAddressVo>) map.get("address");
		List<TraderContact> contactList = (List<TraderContact>) map.get("contact");
		
		OrdergoodsStore ordergoodsStore = new OrdergoodsStore();
		ordergoodsStore.setOrdergoodsStoreId(accountVo.getOrdergoodsStoreId());
		OrdergoodsStore store = ordergoodsService.getStoreById(ordergoodsStore);
		
		//销售
		List<Integer> salePositionType = new ArrayList<>();
		salePositionType.add(SysOptionConstant.ID_310);//销售
		List<User> saleUserList = userService.getMyUserList(user, salePositionType, false);
		
		mv.addObject("ordergoodsStore", store);
		mv.addObject("userList", saleUserList);
		mv.addObject("ordergoodsStoreAccount", accountVo);
		mv.addObject("traderCustomer", traderCustomerVo);
		mv.addObject("addressList", addressList);
		mv.addObject("contactList", contactList);
		try {
			mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(accountVo)));
		} catch (Exception e) {
			logger.error("editordergoodsaccount:", e);
		}
		mv.setViewName("ordergoods/account/account_edit");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 保存编辑经销商账号
	 * @param request
	 * @param ordergoodsStoreAccount
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年1月5日 上午11:15:01
	 */
	@ResponseBody
	@RequestMapping(value = "saveeditordergoodsaccount")
	@SystemControllerLog(operationType = "edit",desc = "保存编辑经销商账号")
	public ModelAndView saveEditOrdergoodsAccount(HttpServletRequest request,OrdergoodsStoreAccount ordergoodsStoreAccount, HttpSession session,String beforeParams){
		if(null == ordergoodsStoreAccount.getOrdergoodsStoreAccountId()
				|| null == ordergoodsStoreAccount.getTraderContactId()
				|| null == ordergoodsStoreAccount.getTraderAddressId()
				|| ordergoodsStoreAccount.getTraderContactId().equals(0)
				|| ordergoodsStoreAccount.getTraderAddressId().equals(0)
				|| ordergoodsStoreAccount.getOrdergoodsStoreAccountId().equals(0)){
			return pageNotFound();
		}
		ModelAndView mv = new ModelAndView();
		//判断是否添加过账号
		OrdergoodsStoreAccount storeAccount = ordergoodsService.getOrdergoodsStoreAccount(ordergoodsStoreAccount);
		
		if(null != storeAccount && !storeAccount.getOrdergoodsStoreAccountId().equals(ordergoodsStoreAccount.getOrdergoodsStoreAccountId())){
			mv.addObject("message","该账号不允许重复添加");
			mv.addObject("url", "./viewordergoodsaccount.do?ordergoodsStoreAccountId=" + ordergoodsStoreAccount.getOrdergoodsStoreAccountId());
			return fail(mv);
		}
		//先同步账号，成功后再在ERP里面增加账号信息
		ResultInfo resultInfo = ordergoodsSoapService.ordergoodsStoreAccountSync(ordergoodsStoreAccount,1);
		if(!resultInfo.getCode().equals(0)){//失败
			return fail(mv);
		}
		
		Integer suc = ordergoodsService.saveEditOrdergoodsAccount(ordergoodsStoreAccount,session);
		if(suc > 0){
			mv.addObject("url", "./viewordergoodsaccount.do?ordergoodsStoreAccountId=" + ordergoodsStoreAccount.getOrdergoodsStoreAccountId());
			return success(mv);
		}else{
			return fail(mv);
		}
	}
	
	/**
	 * <b>Description:</b><br> 启用/禁用经销商账号
	 * @param ordergoodsStore
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年1月5日 上午11:16:56
	 */
	@ResponseBody
	@RequestMapping(value = "/changeaccountenable")
	@SystemControllerLog(operationType = "edit",desc = "启用/禁用经销商账号")
	public ResultInfo changeAccountEnable(OrdergoodsStoreAccount ordergoodsStoreAccount,HttpSession session){
		ResultInfo resultInfo = new ResultInfo<>();
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		
		if(null == ordergoodsStoreAccount 
				|| ordergoodsStoreAccount.getOrdergoodsStoreAccountId() == null){
			return resultInfo;
		}
		
		Boolean suc = ordergoodsService.changeAccountEnable(ordergoodsStoreAccount,session);
		
		if (suc) {// 成功
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
		}
		return resultInfo;
	}
	
	/**
	 * <b>Description:</b><br> 经销商账号重置密码
	 * @param ordergoodsStoreAccount
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年1月5日 上午11:31:02
	 */
	@ResponseBody
	@RequestMapping(value = "/resetpassword")
	@SystemControllerLog(operationType = "edit",desc = "经销商账号重置密码")
	public ResultInfo resetPassword(OrdergoodsStoreAccount ordergoodsStoreAccount,HttpSession session){
		ResultInfo resultInfo = new ResultInfo<>();
		if(null == ordergoodsStoreAccount || ordergoodsStoreAccount.getOrdergoodsStoreAccountId() == null){
			return resultInfo;
		}
		
		resultInfo = ordergoodsSoapService.ordergoodsStoreAccountRestPasswordSync(ordergoodsStoreAccount);
		return resultInfo;
	}
	
	/**
	 * <b>Description:</b><br> 账号产品列表
	 * @param request
	 * @param ordergoodsGoodsAccountVo
	 * @param pageNo
	 * @param pageSize
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年5月18日 上午11:22:17
	 */
	@ResponseBody
	@RequestMapping(value = "viewordergoodsgoodsaccount")
	public ModelAndView viewOrdergoodsGoodsAccount(HttpServletRequest request, OrdergoodsGoodsAccountVo ordergoodsGoodsAccountVo,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize, HttpSession session){
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView();
		Page page = getPageTag(request, pageNo, pageSize);
		
		Map<String, Object> map = ordergoodsService.getAccountGoodsListPage(ordergoodsGoodsAccountVo,page);
		
		OrdergoodsStore ordergoodsStore = new OrdergoodsStore();
		ordergoodsStore.setOrdergoodsStoreId(ordergoodsGoodsAccountVo.getOrdergoodsStoreId());
		OrdergoodsStore store = ordergoodsService.getStoreById(ordergoodsStore);
		
		mv.addObject("ordergoodsGoodsList", (List<OrdergoodsGoodsAccountVo>) map.get("list"));
		mv.addObject("page", (Page) map.get("page"));
		mv.addObject("ordergoodsStore", store);
		mv.addObject("ordergoodsGoodsAccountVo", ordergoodsGoodsAccountVo);
		mv.setViewName("ordergoods/account/account_goods");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 上传账号产品价格
	 * @param request
	 * @param ordergoodsStoreId
	 * @param webAccountId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年5月18日 上午11:28:40
	 */
	@FormToken(save=true)
	@ResponseBody
	@RequestMapping(value="/uplodebatchaccountgoods")
	public ModelAndView uplodeBatchAccountGoods(HttpServletRequest request,@RequestParam("ordergoodsStoreId") Integer ordergoodsStoreId,
			@RequestParam("webAccountId") Integer webAccountId){
		ModelAndView mv=new ModelAndView("ordergoods/account/uplode_batch_account_goods");
		mv.addObject("ordergoodsStoreId", ordergoodsStoreId);
		mv.addObject("webAccountId", webAccountId);
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 保存批量上传账号商品价格
	 * @param request
	 * @param session
	 * @param lwfile
	 * @param ordergoodsStoreId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年5月18日 上午11:36:39
	 */
	@FormToken(remove=true)
	@ResponseBody
	@RequestMapping("saveuplodebatchaccountgoods")
	@SystemControllerLog(operationType = "add",desc = "保存批量上传账号商品价格")
	public ResultInfo<?> saveUplodeBatchAccountGoods(HttpServletRequest request,HttpSession session,
			@RequestParam("lwfile") MultipartFile lwfile
			,@RequestParam("ordergoodsStoreId") Integer ordergoodsStoreId,
			@RequestParam("webAccountId") Integer webAccountId) {
		ResultInfo<?> resultInfo = new ResultInfo<>();
		try {
			User user = (User)session.getAttribute(Consts.SESSION_USER);
			//临时文件存放地址
			String path = request.getSession().getServletContext().getRealPath("/upload/ordergoods");
			FileInfo fileInfo = ftpUtilService.fileUploadServe(path,lwfile);
			if(fileInfo.getCode()==0){
				List<OrdergoodsGoodsAccountVo> list = new ArrayList<>();
				// 获取excel路径
				Workbook workbook = WorkbookFactory.create(new FileInputStream(new File(fileInfo.getFilePath())));
				// 获取第一面sheet
				Sheet sheet = workbook.getSheetAt(0);
				// 起始行
				int startRowNum = sheet.getFirstRowNum() + 1;
				int endRowNum = sheet.getLastRowNum();// 结束行
				/*Row rowObject = sheet.getRow(startRowNum);
				int firstCellNum=rowObject.getLastCellNum()-1;//第一行的列数
*/				for (int rowNum = startRowNum; rowNum <= endRowNum; rowNum++) {// 循环行数
					Row row = sheet.getRow(rowNum);
					int startCellNum = row.getFirstCellNum();// 起始列
					//int endCellNum = row.getLastCellNum() - 1;// 结束列
					int endCellNum = sheet.getRow(0).getPhysicalNumberOfCells()-1;
					// 获取excel的值
					OrdergoodsGoodsAccountVo ordergoodsGoodsAccountVo = new OrdergoodsGoodsAccountVo();
					ordergoodsGoodsAccountVo.setOrdergoodsStoreId(ordergoodsStoreId);
					ordergoodsGoodsAccountVo.setWebAccountId(webAccountId);
					ordergoodsGoodsAccountVo.setCompanyId(user.getCompanyId());
					List<String> orderQuantityList=new ArrayList<String>();
					List<String> accountList=new ArrayList<String>();
					for (int cellNum = startCellNum; cellNum <= endCellNum; cellNum++) {// 循环列数（下表从0开始）
						Cell cell = row.getCell(cellNum);
						String str = null;
						if(cellNum==0){//sku
							 //若excel中无内容，而且没有空格，cell为空--默认3，空白
							if (cell == null || cell.getCellType() != 1) {
								 resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列【订货号】错误！");
								 throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列【订货号】错误！");
							 }else{
								 ordergoodsGoodsAccountVo.setSku(cell.getStringCellValue().toString());
							}
						}
						String priceRegex = "(^[1-9]([0-9]+)?(\\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\\.[0-9]([0-9])?$)";
						
						if(cellNum==4){//价格
							if(cell == null|| cell.getCellType() == 3){
								resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列【零售价】不允许为空！");
								throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列【零售价】不允许为空！");
							}else{
								double numericCellValue = cell.getNumericCellValue();
								str = String.format("%.2f", numericCellValue);
								if(!RegexUtil.match(priceRegex, str)){
									resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列【零售价】仅允许使用数字，最多精确到小数后两位！");
									throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列【零售价】仅允许使用数字，最多精确到小数后两位！");
								}else if(numericCellValue > 10000000){
									resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列【零售价】不允许超过一千万！");
									throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列【零售价】不允许超过一千万！");
								}else{
									ordergoodsGoodsAccountVo.setPrice(new BigDecimal(str));
								}
							}
						}
						//对起订量不为空时做规范检查,为空时占位且该位值为字符串“null”
						if(cellNum==5||cellNum==7||cellNum==9||cellNum==11){
							int index=0;
							//正整数的正则表达式
							String orderQuantityRegex="^([^01]|\\d{2,})$";
							//为索引赋值
							switch (cellNum) {
								case 5:
									index=0;
									break;
								case 7:
									index=1;
									break;
								case 9:
									index=2;
									break;
								case 11:
									index=3;
									break;
								default:
									break;
							}
							if(cell==null|| cell.getCellType() == 3){
								orderQuantityList.add(index, "null");
							}else{
								//对起订量做规范验证
								if(!RegexUtil.match(orderQuantityRegex, String.valueOf((int)cell.getNumericCellValue()))){
									resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列【起订量】仅允许使用大于1的数字，且不允许为小数！");
									throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列【起订量】仅允许使用大于1的数字，且不允许为小数！");
								}else if(cell.getNumericCellValue()>1000000){
									resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列【起订量】不允许超过一百万！");
									throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列【起订量】不允许超过一百万！");
								}else{
									orderQuantityList.add(index, String.valueOf((int)cell.getNumericCellValue()));
								}
							}
						}
						//对批量价不为空时做规范检查,为空时占位且该位值为字符串“null”
						if(cellNum==6||cellNum==8||cellNum==10||cellNum==12){
							int index=0;
							//为索引赋值
							switch (cellNum) {
								case 6:
									index=0;
									break;
								case 8:
									index=1;
									break;
								case 10:
									index=2;
									break;
								case 12:
									index=3;
									break;
								default:
									break;
							}
							if(cell == null|| cell.getCellType() == 3){
								accountList.add(index,"null");
							}else{
								double numericCellValue = cell.getNumericCellValue();
								str = String.format("%.2f", numericCellValue);
								if(!RegexUtil.match(priceRegex, str)){
									resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列【批量价】仅允许使用数字，最多精确到小数后两位！");
									throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列【批量价】仅允许使用数字，最多精确到小数后两位！");
								}else if(numericCellValue > 10000000){
									resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列【批量价】不允许超过一千万！");
									throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列【批量价】不允许超过一千万！");
								}else{
									accountList.add(index, str);
								}
							}
							
						}
						/*int count=0;
						if(endCellNum<firstCellNum){
							for(int index=firstCellNum;index>(firstCellNum-endCellNum);index--){
								orderQuantityList.add((firstCellNum-endCellNum-count),"null");
								if(endCellNum%2!=0){
									accountList.add((firstCellNum-endCellNum-count),"null");
								}
								count++;
							}
							
						}*/
					}
					if(orderQuantityList!=null&&orderQuantityList.size()>0){
						if("null".equals(orderQuantityList.get(0))){
							if((!"null".equals(orderQuantityList.get(1)))||(!"null".equals(orderQuantityList.get(2)))||(!"null".equals(orderQuantityList.get(3)))){
								resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + "请依次填写【起订量】，不允许跳跃填写！");
								throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + "请依次填写【起订量】，不允许跳跃填写！");
							}
						}
						if("null".equals(orderQuantityList.get(1))){
							if((!"null".equals(orderQuantityList.get(2)))||(!"null".equals(orderQuantityList.get(3)))){
								resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + "请依次填写【起订量】，不允许跳跃填写！");
								throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + "请依次填写【起订量】，不允许跳跃填写！");
							}
						}
						if("null".equals(orderQuantityList.get(2))){
							if(!"null".equals(orderQuantityList.get(3))){
								resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + "请依次填写【起订量】，不允许跳跃填写！");
								throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + "请依次填写【起订量】，不允许跳跃填写！");
							}
						}
						if((!"null".equals(orderQuantityList.get(0)))&&(!"null".equals(orderQuantityList.get(1)))){
							if(Integer.valueOf(orderQuantityList.get(0))>=Integer.valueOf(orderQuantityList.get(1))){
								resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + "【起订量1】必须 小于 【起订量2】！");
								throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + "【起订量1】必须 小于 【起订量2】！");
							}
						}
						if((!"null".equals(orderQuantityList.get(1)))&&(!"null".equals(orderQuantityList.get(2)))){
							if(Integer.valueOf(orderQuantityList.get(1))>=Integer.valueOf(orderQuantityList.get(2))){
								resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + "【起订量2】必须 小于 【起订量3】！");
								throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + "【起订量2】必须 小于 【起订量3】！");
							}
						}
						if((!"null".equals(orderQuantityList.get(2)))&&(!"null".equals(orderQuantityList.get(3)))){
							if(Integer.valueOf(orderQuantityList.get(2))>=Integer.valueOf(orderQuantityList.get(3))){
								resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + "【起订量3】必须 小于 【起订量4】！");
								throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + "【起订量3】必须 小于 【起订量4】！");
							}
						}
					}
					for(int index=0;index<orderQuantityList.size();index++){
						if("null".equals(orderQuantityList.get(index))&&(!"null".equals(accountList.get(index)))){
							resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + "【起订量"+(index+1)+"】为空时，【批量价"+(index+1)+"】也必须为空！");
							throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + "【起订量"+(index+1)+"】为空时，【批量价"+(index+1)+"】也必须为空！");
						}else if("null".equals(accountList.get(index))&&(!"null".equals(orderQuantityList.get(index)))){
							resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + "【起订量"+(index+1)+"】有值时，【批量价"+(index+1)+"】也必须有值！");
							throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + "【起订量"+(index+1)+"】有值时，【批量价"+(index+1)+"】也必须有值！");
						}else if((!"null".equals(orderQuantityList.get(index)))&&(!"null".equals(accountList.get(index)))){
							BigDecimal orderQuantity = new BigDecimal(orderQuantityList.get(index));
							BigDecimal account = new BigDecimal(Double.valueOf(accountList.get(index))+1);
							int result = orderQuantity.multiply(account).intValue();// 相乘结果
							if(result>10000000){
								resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + "【批量价"+(index+1)+"】和【起订量"+(index+1)+"】相乘不允许超过一千万！");
								throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + "【批量价"+(index+1)+"】和【起订量"+(index+1)+"】相乘不允许超过一千万！");
							}else{
								switch (index) {
									case 0:
										ordergoodsGoodsAccountVo.setMinQuantity1(Integer.valueOf(orderQuantityList.get(index)));
										ordergoodsGoodsAccountVo.setPrice1(new BigDecimal(accountList.get(index)));
										break;
									case 1:
										ordergoodsGoodsAccountVo.setMinQuantity2(Integer.valueOf(orderQuantityList.get(index)));
										ordergoodsGoodsAccountVo.setPrice2(new BigDecimal(accountList.get(index)));
										break;
									case 2:
										ordergoodsGoodsAccountVo.setMinQuantity3(Integer.valueOf(orderQuantityList.get(index)));
										ordergoodsGoodsAccountVo.setPrice3(new BigDecimal(accountList.get(index)));
										break;
									case 3:
										ordergoodsGoodsAccountVo.setMinQuantity4(Integer.valueOf(orderQuantityList.get(index)));
										ordergoodsGoodsAccountVo.setPrice4(new BigDecimal(accountList.get(index)));
										break;
									default:
										break;
								}
							}
						}else if("null".equals(accountList.get(index))&&"null".equals(orderQuantityList.get(index))){
							switch (index) {
								case 0:
									ordergoodsGoodsAccountVo.setMinQuantity1(Integer.valueOf(0));
									ordergoodsGoodsAccountVo.setPrice1(new BigDecimal(0.00));
									break;
								case 1:
									ordergoodsGoodsAccountVo.setMinQuantity2(Integer.valueOf(0));
									ordergoodsGoodsAccountVo.setPrice2(new BigDecimal(0.00));
									break;
								case 2:
									ordergoodsGoodsAccountVo.setMinQuantity3(Integer.valueOf(0));
									ordergoodsGoodsAccountVo.setPrice3(new BigDecimal(0.00));
									break;
								case 3:
									ordergoodsGoodsAccountVo.setMinQuantity4(Integer.valueOf(0));
									ordergoodsGoodsAccountVo.setPrice4(new BigDecimal(0.00));
									break;
								default:
									break;
							}
						}
					}
					list.add(ordergoodsGoodsAccountVo);
				}
				//保存更新
				resultInfo = ordergoodsService.saveUplodeBatchAccountGoods(list,session);
				//同步
				if(resultInfo.getCode().equals(0)){
					ordergoodsSoapService.ordergoodsGoodsAccountBatchSync((List<OrdergoodsGoodsAccountVo>)resultInfo.getListData());
				}
			}
		}catch(Exception e) {
			logger.error("saveuplodebatchaccountgoods:", e);
			return resultInfo;
		}
		return resultInfo; 
	}
	
	/**
	 * <b>Description:</b><br> 删除订货账号产品价格
	 * @param request
	 * @param ordergoodsGoodsVo
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年5月18日 上午11:41:24
	 */
	@ResponseBody
	@RequestMapping(value = "deleteaccountgoods")
	@SystemControllerLog(operationType = "delete",desc = "删除订货账号产品价格")
	public ResultInfo deleteOrdergoodsAccountGoods(HttpServletRequest request,OrdergoodsGoodsAccountVo ordergoodsGoodsAccountVo,HttpSession session){
		ResultInfo resultInfo = new ResultInfo<>();
		if(null == ordergoodsGoodsAccountVo 
				|| null == ordergoodsGoodsAccountVo.getGoodsId()
				|| null == ordergoodsGoodsAccountVo.getOrdergoodsStoreId()
				|| null == ordergoodsGoodsAccountVo.getWebAccountId()
				|| null == ordergoodsGoodsAccountVo.getOrdergoodsGoodsAccountId()
				){
			return resultInfo;
		}
		
		Boolean res = ordergoodsService.deleteOrdergoodsAccountGoods(ordergoodsGoodsAccountVo);
		if(res){
			//同步
			ordergoodsSoapService.delOrdergoodsAccountGoodsSync(ordergoodsGoodsAccountVo.getGoodsId(),ordergoodsGoodsAccountVo.getOrdergoodsStoreId(),ordergoodsGoodsAccountVo.getWebAccountId(),ordergoodsGoodsAccountVo.getOrdergoodsGoodsAccountId());
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
		}
		return resultInfo;
	}
}
