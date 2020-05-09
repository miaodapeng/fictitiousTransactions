package com.vedeng.goods.controller;

import com.alibaba.fastjson.JSON;
import com.common.constants.Contant;
import com.vedeng.activiti.service.ActionProcdefService;
import com.vedeng.authorization.model.Region;
import com.vedeng.authorization.model.User;
import com.vedeng.common.annotation.SystemControllerLog;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.exception.ShowErrorMsgException;
import com.vedeng.common.model.FileInfo;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.common.util.StringUtil;
import com.vedeng.common.util.VerifyAndShowErrorMsgUtil;
import com.vedeng.common.validator.FormToken;
import com.vedeng.goods.model.*;
import com.vedeng.goods.service.*;
import com.vedeng.report.service.ExportService;
import com.vedeng.system.model.Attachment;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.system.service.*;
import com.vedeng.trader.model.vo.TraderSupplierVo;
import com.vedeng.trader.service.TraderSupplierService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLDecoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <b>Description:</b><br>
 * 产品管理功能
 * 
 * @author leo.yang
 * @Note <b>ProjectName:</b> erp <br>
 *       <b>PackageName:</b> com.vedeng.goods.controller <br>
 *       <b>ClassName:</b> GoodsController <br>
 *       <b>Date:</b> 2017年5月12日 下午3:17:14
 */
@Controller
@RequestMapping("goods/goods")
public class GoodsController extends BaseController {
	public static Logger logger = LoggerFactory.getLogger(GoodsController.class);

	@Autowired // 自动装载
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	@Autowired // 自动装载
	@Qualifier("historyService")
	private HistoryService historyService;

	@Autowired
	@Qualifier("goodsService")
	private GoodsService goodsService;

	@Autowired
	@Qualifier("brandService")
	private BrandService brandService;

	@Autowired
	@Qualifier("unitService")
	private UnitService unitService;

	@Autowired
	@Qualifier("sysOptionDefinitionService")
	private SysOptionDefinitionService sysOptionDefinitionService;

	@Autowired
	@Qualifier("categoryService")
	private CategoryService categoryService;

	@Autowired
	@Qualifier("exportService")
	private ExportService exportService;

	@Autowired
	@Qualifier("ftpUtilService")
	private FtpUtilService ftpUtilService;

	@Resource
	private UserService userService;

	@Autowired
	@Qualifier("regionService")
	private RegionService regionService;

	@Autowired
	@Qualifier("verifiesRecordService")
	private VerifiesRecordService verifiesRecordService;

	@Autowired
	@Qualifier("actionProcdefService")
	private ActionProcdefService actionProcdefService;

	@Autowired
	@Qualifier("goodsChannelPriceService")
	private GoodsChannelPriceService goodsChannelPriceService;

	@Autowired
	@Qualifier("traderSupplierService")
	private TraderSupplierService traderSupplierService;

	@Autowired
	@Qualifier("goodsSettlementPriceService")
	private GoodsSettlementPriceService goodsSettlementPriceService;

	// private GoodsChannelPrice goodsPrice;

	/**
	 * <b>Description:</b><br>
	 * 产品列表（分页）
	 * 
	 * @param request
	 * @param goods
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年5月24日 下午6:12:34
	 */
	@ResponseBody
	@RequestMapping(value = "/index")
	public ModelAndView index(HttpServletRequest request, Goods goods,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize, HttpSession session) {
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		// 查询顶级产品分类
		Category category = new Category();
		StandardCategory standardCategory = new StandardCategory();
		standardCategory.setParentId(0);
		category.setParentId(0);
		if (null != user) {
			category.setCompanyId(user.getCompanyId());
			goods.setCompanyId(user.getCompanyId());
		}
		goods.setSource(0);// 来源于ERP
		List<Category> categoryList = categoryService.getParentCateList(category);
		List<StandardCategory> standardCategoryList = categoryService.getParentStandardCateList(standardCategory);

		// 产品级别
		List<SysOptionDefinition> manageCategoryLevels = getSysOptionDefinitionList(SysOptionConstant.ID_338);

		// 产品级别
		List<SysOptionDefinition> goodsLevels = getSysOptionDefinitionList(SysOptionConstant.ID_334);
		// if(JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST+SysOptionConstant.ID_334)){
		// String
		// json=JedisUtils.get(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST+SysOptionConstant.ID_334);
		// JSONArray jsonArray=JSONArray.fromObject(json);
		// goodsLevels=(List<SysOptionDefinition>)
		// JSONArray.toCollection(jsonArray, SysOptionDefinition.class);
		// }

		// 产品类别
		List<SysOptionDefinition> goodsTypes = getSysOptionDefinitionList(SysOptionConstant.ID_315);
		// if(JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST+SysOptionConstant.ID_315)){
		// String
		// json=JedisUtils.get(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST+SysOptionConstant.ID_315);
		// JSONArray jsonArray=JSONArray.fromObject(json);
		// goodsTypes=(List<SysOptionDefinition>)
		// JSONArray.toCollection(jsonArray, SysOptionDefinition.class);
		// }
		// 产品部人员列表
		List<User> buyerList = userService.getUserByPositType(SysOptionConstant.ID_311, user.getCompanyId());
		Integer isBuyer = 0;
		for (User buyer : buyerList) {
			if (buyer.getUserId().intValue() == user.getUserId().intValue()) {
				isBuyer = 1;
				break;
			}
		}

		if (null != goods.getGoodsUserId() && goods.getGoodsUserId() != -1) {
			List<Integer> categoryIdList = userService.getCategoryIdListByUserId(goods.getGoodsUserId());
			if (categoryIdList == null || categoryIdList.isEmpty()) {
				categoryIdList.add(-1);
			}
			goods.setCategoryIdList(categoryIdList);
		}

		//核价筛选
		if(goods.getIsChannelPrice() != null) {
			List<GoodsChannelPrice> goodsIds = goodsChannelPriceService.getGoodsChannelPriceListAll();
			List<String> channelPriceGoodsList = new ArrayList<String>();
			for(GoodsChannelPrice goodsChannelPrice:goodsIds) {
				if(goodsChannelPrice != null && goodsChannelPrice.getGoodsId() != null) {
					channelPriceGoodsList.add(String.valueOf(goodsChannelPrice.getGoodsId()));
				}
			}
			goods.setChannelPriceGoodsArr(channelPriceGoodsList);
		}

		ModelAndView mv = new ModelAndView();
		Page page = getPageTag(request, pageNo, pageSize);
		Map<String, Object> map = goodsService.getGoodsListPage(request, goods, page, session);
		List<Goods> list = (List<Goods>) map.get("list");
		
		//添加核价 信息
		//如果列表查询条件中包含核价信息
		if(list != null) {
			for(Goods goodsWithPrice : list) {
				GoodsChannelPrice price = goodsChannelPriceService.getGoodsChannelPriceByGoodsIdOne(goodsWithPrice);
				if(price != null) {
					goodsWithPrice.setChannelPrice(price.getDistributionPrice());
				}
			}
		}

		for (int i = 0; i < list.size(); i++) {
			// 审核人
			if (null != list.get(i).getVerifyUsername()) {
				List<String> verifyUsernameList = Arrays.asList(list.get(i).getVerifyUsername().split(","));
				list.get(i).setVerifyUsernameList(verifyUsernameList);
			}
		}
		mv.addObject("goodsList", list);
		mv.addObject("goods", goods);
		mv.addObject("page", (Page) map.get("page"));
		mv.addObject("categoryList", categoryList);
		mv.addObject("standardCategoryList", standardCategoryList);
		mv.addObject("goodsLevels", goodsLevels);
		mv.addObject("manageCategoryLevels", manageCategoryLevels);
		mv.addObject("goodsTypes", goodsTypes);
		mv.addObject("buyerList", buyerList);
		mv.addObject("loginUser", user);
		mv.addObject("isBuyer", isBuyer);
		mv.setViewName("goods/goods/index");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * ajax补充产品列表相关信息
	 * 
	 * @param request
	 * @param goodsIdStr
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2018年2月10日 下午7:00:31
	 */
	@ResponseBody
	@RequestMapping(value = "getgoodslistextrainfo")
	public ResultInfo<?> getGoodsListExtraInfo(HttpServletRequest request,HttpServletResponse response, Goods goods) {
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		if (null != request.getParameter("goodsIdStr") && request.getParameter("goodsIdStr") != "") {
			String[] split = request.getParameter("goodsIdStr").split("_");
			goods.setGoodsIdArr(Arrays.asList(split));
		}
		if(user==null){
			user=new User();
			user.setCompanyId(1);
		}
		goods.setCompanyId(user.getCompanyId());

		ResultInfo<?> goodsListExtraInfo = goodsService.getGoodsListExtraInfo(goods);
		List<Integer> goodsIdList = goods.getGoodsIdArr().stream().map(Integer::parseInt).collect(Collectors.toList());
		goodsListExtraInfo.setParam(goodsChannelPriceService.getGoodsChannelPriceListByChina(goodsIdList, 1));

		return goodsListExtraInfo;
	}

	/**
	 * <b>Description:</b><br>
	 * 产品列表导出功能
	 * 
	 * @param request
	 * @param response
	 * @param goods
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年6月2日 下午4:35:18
	 */
	@RequestMapping(value = "/exportgoodslist")
	public void export(HttpServletRequest request, HttpServletResponse response, Goods goods, HttpSession session) {
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
			Map<String, Object> map = null;
			Page pageTotal = null;

			// ------------------定义表头----------------------------------------
			int page_size = 1000;// 数据库中每次查询条数
			page = getPageTag(request, 1, page_size);
			map = goodsService.getGoodsListPage(request, goods, page, session);
			pageTotal = (Page) map.get("page");
			for (int i = 1; i <= pageTotal.getTotalPage(); i++) {
				page = getPageTag(request, i, page_size);
				map = goodsService.getGoodsListPage(request, goods, page, session);
				List<Goods> goodsLists = new ArrayList<>();
				goodsLists = (List<Goods>) map.get("list");
				for (Goods goodsList : goodsLists) {
					row_value = sh.createRow(bodyRowCount);

					String isDiscardStr = goodsList.getIsDiscard() == 1 ? "已禁用" : "未禁用";
					String isOnSaleStr = goodsList.getIsOnSale() == 1 ? "已上架" : "未上架";

					row_value.createCell(0).setCellValue(goodsList.getSku());
					row_value.createCell(1).setCellValue(goodsList.getGoodsName());
					row_value.createCell(2).setCellValue(goodsList.getBrandName());
					row_value.createCell(3).setCellValue(goodsList.getModel());
					row_value.createCell(4).setCellValue(goodsList.getMaterialCode());
					row_value.createCell(5).setCellValue(goodsList.getUnitName());
					row_value.createCell(6).setCellValue(goodsList.getCategoryName());
					row_value.createCell(7).setCellValue(goodsList.getGoodsType());
					row_value.createCell(8).setCellValue(goodsList.getGoodsLevel());
					row_value.createCell(9).setCellValue(goodsList.getManageCategory());
					row_value.createCell(10).setCellValue("");
					row_value.createCell(11)
							.setCellValue("长度 " + goodsList.getGoodsLength() + " cm   宽度" + goodsList.getGoodsWidth()
									+ " cm   高度 " + goodsList.getGoodsHeight() + " cm   净重 " + goodsList.getNetWeight()
									+ " kg");
					row_value.createCell(12)
							.setCellValue("长度 " + goodsList.getPackageLength() + " cm   宽度"
									+ goodsList.getPackageWidth() + " cm   高度 " + goodsList.getPackageHeight()
									+ " cm   毛重 " + goodsList.getGrossWeight() + " kg");
					row_value.createCell(13).setCellValue(goodsList.getPackingList());
					row_value.createCell(14).setCellValue("");
					row_value.createCell(15).setCellValue("");
					row_value.createCell(16).setCellValue("");
					row_value.createCell(17).setCellValue("");
					row_value.createCell(18).setCellValue(isDiscardStr);
					row_value.createCell(19).setCellValue(isOnSaleStr);
					row_value.createCell(20).setCellValue("");
					row_value.createCell(21).setCellValue("");
					row_value.createCell(22).setCellValue("");
					row_value.createCell(23).setCellValue("");
					row_value.createCell(24).setCellValue("");
					row_value.createCell(25).setCellValue("");
					row_value.createCell(26).setCellValue("");
					row_value.createCell(27).setCellValue(DateUtil.convertString(goodsList.getAddTime(), null));
					row_value.createCell(28).setCellValue(DateUtil.convertString(goodsList.getModTime(), null));
					row_value.createCell(29).setCellValue("");
					row_value.createCell(30).setCellValue("");
					bodyRowCount++;
				}
				goodsLists.clear();
			}

			wb.write(out);
			out.close();
			wb.dispose();

		} catch (Exception e) {
			logger.error("exportgoodslist:", e);
		}
	}

	public void writeTitleContent(Sheet sh) {
		Row row = sh.createRow(0);
		// --------------------------------------------------
		row.createCell(0).setCellValue("订货号");
		row.createCell(1).setCellValue("产品名称");
		row.createCell(2).setCellValue("品牌");
		row.createCell(3).setCellValue("型号");
		row.createCell(4).setCellValue("物料编码");
		row.createCell(5).setCellValue("单位");
		row.createCell(6).setCellValue("产品分类");
		row.createCell(7).setCellValue("产品类型");
		row.createCell(8).setCellValue("产品级别");
		row.createCell(9).setCellValue("管理类别");
		row.createCell(10).setCellValue("产品负责人");
		row.createCell(11).setCellValue("尺寸");
		row.createCell(12).setCellValue("包装信息");
		row.createCell(13).setCellValue("包装清单");
		row.createCell(14).setCellValue("库存数量");
		row.createCell(15).setCellValue("订单占用");
		row.createCell(16).setCellValue("可调剂");
		row.createCell(17).setCellValue("在途数量");
		row.createCell(18).setCellValue("禁用状态");
		row.createCell(19).setCellValue("上架状态");
		row.createCell(20).setCellValue("浏览次数");
		row.createCell(21).setCellValue("近一年采购量");
		row.createCell(22).setCellValue("近一年销售量");
		row.createCell(23).setCellValue("近半年采购量");
		row.createCell(24).setCellValue("近半年销售量");
		row.createCell(25).setCellValue("近三个月采购量");
		row.createCell(26).setCellValue("近三个月销售量");
		row.createCell(27).setCellValue("创建时间");
		row.createCell(28).setCellValue("更新时间");
		row.createCell(29).setCellValue("审核时间");
		row.createCell(30).setCellValue("审核状态");
	}

	/**
	 * <b>Description:</b><br>
	 * 添加产品页面
	 * 
	 * @param request
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年5月19日 上午9:55:54
	 */
	@ResponseBody
	@RequestMapping(value = "/add")
	public ModelAndView add(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);

		Brand brand = new Brand();
		brand.setCompanyId(user.getCompanyId());
		List<Brand> brandList = brandService.getAllBrand(brand);

		Unit unit = new Unit();
		unit.setCompanyId(user.getCompanyId());
		List<Unit> unitList = unitService.getAllUnitList(unit);

		// 查询顶级产品分类
		Category category = new Category();
		StandardCategory standardCategory = new StandardCategory();
		standardCategory.setParentId(0);
		category.setParentId(0);
		if (user != null) {
			category.setCompanyId(user.getCompanyId());
		}
		List<Category> categoryList = categoryService.getParentCateList(category);
		List<StandardCategory> standardCategoryList = categoryService.getParentStandardCateList(standardCategory);

		ModelAndView mv = new ModelAndView();
		mv.addObject("brandList", brandList);
		mv.addObject("unitList", unitList);
		mv.addObject("categoryList", categoryList);
		mv.addObject("standardCategoryList", standardCategoryList);
		mv.setViewName("goods/goods/add");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 获取产品默认字典库数据
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年5月19日 下午3:25:39
	 */
	@ResponseBody
	@RequestMapping(value = "/getgoodsoption")
	public ResultInfo<?> getGoodsOption(HttpServletRequest request) throws IOException {
		ResultInfo<?> resultInfo = new ResultInfo<>();
		List<Integer> scopeList = new ArrayList<Integer>();
		scopeList.add(1035);// 产品类型
		scopeList.add(1036);// 应用科室
		scopeList.add(1037);// 产品级别
		scopeList.add(1020);// 管理类别
		scopeList.add(1038);// 管理类别级别

		Map<Integer, List<SysOptionDefinition>> optionList = sysOptionDefinitionService.getOptionByScopeList(scopeList);

		if (optionList != null) {
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
			resultInfo.setParam(JsonUtils.translateToJson(optionList));
		}
		return resultInfo;
	}

	/**
	 * 
	 * <b>Description:</b><br>
	 * 批量商品授权与定价
	 * 
	 * @param request
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2018年3月13日 上午10:22:41
	 */
	@ResponseBody
	@RequestMapping(value = "/batchAuthorizationPricing")
	public ModelAndView batchAuthorizationPricing(HttpServletRequest request) {
		return new ModelAndView("goods/goods/batch_authorization_pricing");
	}

	/**
	 * 
	 * <b>Description:</b><br>
	 * 批量修改商品授权与定价
	 * 
	 * @param request
	 * @param lwfile
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Modify: Franlin at 2018-05-14 fro bug[3663 商品授权与定价下载模板页面，报错提示优化
	 *       ]</b> <br>
	 *       <b>Date:</b> 2018年3月20日 上午10:01:20
	 */
	@SuppressWarnings("resource")
	@ResponseBody
	@RequestMapping("/batchAuthorizationPricingOpt")
	public ResultInfo<?> batchAuthorizationPricingOpt(HttpServletRequest request,
			@RequestParam("lwfile") MultipartFile lwfile) {
		ResultInfo<?> result = new ResultInfo<>();

		try {
			User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
			Long time = DateUtil.sysTimeMillis();
			// 临时文件存放地址
			String path = request.getSession().getServletContext().getRealPath("/upload/goods");
			FileInfo fileInfo = ftpUtilService.fileUploadServe(path, lwfile);

			// 上传成功
			if (null == fileInfo || fileInfo.getCode() != 0) {
				result.setMessage("上传文件失败, 请重试!");
				throw new Exception("上传文件失败, 请重试!");
			}

			// 获取excel路径
			Workbook workbook = WorkbookFactory.create(new FileInputStream(new File(fileInfo.getFilePath())));
			// 获取第一面sheet
			Sheet sheet = workbook.getSheetAt(0);
			// 起始行
			int startRowNum = sheet.getFirstRowNum() + 1;// 第一行标题
			int endRowNum = sheet.getLastRowNum();// 结束行

			if (startRowNum > endRowNum) {
				result.setMessage("上传文件为空模板, 数据读取从第2行开始。 请下载标准模板并正确填写后重试!");
				throw new Exception("上传文件为空模板, 数据读取从第2行开始。 请下载标准模板并正确填写后重试!");
			}

			// 省市地址信息
			List<Region> regionList = regionService.getRegionByParentId(1);// 缓存中拿
			if (regionList.isEmpty()) {
				result.setMessage("未能查找到省市信息，请验证！");
				throw new Exception("未能查找到省市信息，请验证！");
			}
			List<GoodsChannelPrice> list = new ArrayList<>();// 保存Excel中读取的数据

			// 循环行数
			for (int rowNum = startRowNum; rowNum <= endRowNum; rowNum++) {
				Row row = sheet.getRow(rowNum);
				if (row == null) {
					result.setMessage("第" + rowNum + "行中无数据，请验证！");
					throw new Exception("第" + rowNum + "行中无数据，请验证！");
				}
				int startCellNum = 0;// 默认从第一列开始
				int endCellNum = row.getLastCellNum() - 1;// 结束列
				// 获取excel的值
				GoodsChannelPrice goodsPrice = new GoodsChannelPrice();
				if (user != null) {
					goodsPrice.setAddTime(DateUtil.gainNowDate());
					goodsPrice.setCreator(user.getUserId());

					goodsPrice.setUpdater(user.getUserId());
					goodsPrice.setModTime(DateUtil.gainNowDate());
				}
				String str = null;
				List<Region> cityList = null;// 市地址列表
				// 循环列数（下表从0开始）
				for (int cellNum = startCellNum; cellNum <= endCellNum; cellNum++) {
					Cell cell = row.getCell(cellNum);
					// 错误提示信息
					String errorMsg = "第" + (rowNum + 1) + "行，第" + (cellNum + 1) + "列: ";
					Integer type = cell == null ? 3 : cell.getCellType();
					str = null;
					if (cellNum != 30 && cellNum != 31) {
						switch (type) {
						case Cell.CELL_TYPE_BLANK:// 空白单元格
							str = null;
							break;
						case Cell.CELL_TYPE_STRING:// 字符类型
							str = cell.getStringCellValue().toString();
							break;
						default:
							cell.setCellType(Cell.CELL_TYPE_STRING);
							str = cell.getStringCellValue().toString();
							break;
						}
						switch (cellNum) {
						case 0:// 订货号
							if (str == null || "".equals(str)) {
								result.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空，请验证！");
								throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空，请验证！");
							}
							goodsPrice.setSku(str.toString());
							break;
						case 1:
						case 2:
						case 3:
						case 4:
							break;
						case 5:// 省
							if (str != null && !"".equals(str)) {
								if (str.equals("中国")) {
									goodsPrice.setProvinceName(str);
									goodsPrice.setProvinceId(1);
								} else {
									for (int i = 0; i < regionList.size(); i++) {
										if (str.equals(regionList.get(i).getRegionName())) {
											goodsPrice.setProvinceName(regionList.get(i).getRegionName());
											goodsPrice.setProvinceId(regionList.get(i).getRegionId());
											break;
										}
									}
									if (goodsPrice.getProvinceName() == null) { // 未能查询到对应的省ID
										result.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
												+ "列：值错误。请正确填写 授权的地区（省）验证后重试！");
										throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
												+ "列：值错误。请正确填写 授权的地区（省）验证后重试！");
									}
								}
							} else {
								result.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
										+ "列：值不允许为空。 授权的地区（省） 不可为空, 请验证后重试！");
								throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
										+ "列：值不允许为空。授权的地区（省） 不可为空, 请验证后重试！");
							}
							break;
						case 6:// 市
							if (str != null && !"".equals(str)) {

								if (null != goodsPrice.getProvinceId()) {
									// 根据省Id到缓存中查询市列表
									cityList = regionService.getRegionByParentId(goodsPrice.getProvinceId());
									for (int i = 0; i < cityList.size(); i++) {
										if (str.equals(cityList.get(i).getRegionName())) {
											goodsPrice.setCityName(cityList.get(i).getRegionName());
											goodsPrice.setCityId(cityList.get(i).getRegionId());
											break;
										}
									}
								}
								if (goodsPrice.getCityName() == null) {// 未能查询到对应的单位ID
									result.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
											+ "列：值错误。 请正确填写 授权的地区（市）请验证后重试！");
									throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
											+ "列：值错误 。请正确填写 授权的地区（市）请验证后重试!");
								}
							}
							break;
						case 7:// 授权客户类型
							if (StringUtil.isBlank(str)) {
								result.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
										+ "列：值不允许为空。 授权客户类型（支持输入多种客户类型） 不可为空，请正确填写后重试！");
								throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
										+ "列：值不允许为空 。授权客户类型（支持输入多种客户类型） 不可为空，请正确填写后重试！");
							}
							/*
							 * if(!str.contains("科研") && !str.contains("医院>>民营")
							 * && !str.contains("医院>>公立")){
							 * result.setMessage("第:" + (rowNum + 1) + "行，第:" +
							 * (cellNum + 1) + "列：值错误，请验证！"); throw new
							 * Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum
							 * + 1) + "列：值错误，请验证！"); }
							 */
							goodsPrice.setCustomerTypeComments(str);
							break;
						case 8:// 采购最小起订量/最小起订价
							if (StringUtil.isNotBlank(str)) {
								// 最小起订价
								if (str.contains("元")) {
									// DecimalFormat df = new
									// DecimalFormat("#.00");
									goodsPrice.setMinAmountCg(
											VerifyAndShowErrorMsgUtil.verifyBigDecimalByString(str.replace("元", ""),
													errorMsg + "值错误,去掉单位(元)后不是数字。 请正确填写 最小起订量/最小起订价（请带上单位） 验证后重试!"));
								}
								// TODO
								// 最小起订量
								else if (!str.contains("元") && str.length() > 1) {
									goodsPrice.setMinNumCg(VerifyAndShowErrorMsgUtil.verifyIntegerByString(
											str.substring(0, str.length() - 1),
											errorMsg + "值错误，去掉末尾单位后不是数字。 请正确填写 最小起订量/最小起订价（请带上单位） 验证后重试!"));
								} else {
									result.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
											+ "列： 值 错误。 请正确填写 最小起订量/最小起订价（请带上单位） 验证后重试!");
									throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
											+ "列：值错误。请正确填写 最小起订量/最小起订价（请带上单位） 验证后重试!");
								}
							}
							break;
						case 9:// 销售最小起订量/最小起订价
							if (str != null && !"".equals(str)) {
								if (str.contains("元")) {
									// DecimalFormat df = new
									// DecimalFormat("#.00");
									goodsPrice.setMinAmount(VerifyAndShowErrorMsgUtil.verifyBigDecimalByString(
											str.replace("元", ""),
											errorMsg + "值错误,去掉单位(元)后不是数字。 请正确填写 销售端的最小起订量/最小起订价（请带上单位） 验证后重试!"));
								}
								// TODO
								else if (!str.contains("元") && str.length() > 1) {
									goodsPrice.setMinNum(VerifyAndShowErrorMsgUtil.verifyIntegerByString(
											str.substring(0, str.length() - 1),
											errorMsg + "值错误,去掉末尾单位后不是数字。 请正确填写 销售端的最小起订量/最小起订价（请带上单位） 验证后重试!"));
								} else {
									result.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
											+ "列：值错误。请正确填写 销售端的最小起订量/最小起订价（请带上单位） 验证后重试!");
									throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
											+ "列：值错误。请正确填写 销售端的最小起订量/最小起订价（请带上单位） 验证后重试!");
								}
							}
							break;
						case 10:// 采购批量政策
							goodsPrice.setBatchPolicyCg(str);
							break;
						case 11:// 销售批量政策
							goodsPrice.setBatchPolicy(str);
							break;
						case 12:// 市场指导价
							if (StringUtil.isBlank(str)) {
								result.setMessage(
										"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空。 市场指导价 不可为空，请验证后重试！");
								throw new Exception(
										"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空。市场指导价 不可为空，请验证后重试！");
							}

							goodsPrice.setMarketPrice(VerifyAndShowErrorMsgUtil.verifyBigDecimalByString(str,
									errorMsg + " 值不为数字。市场指导价应为是数字, 请验证后重试!"));
							break;
						case 13:// 采购经销商价
							if (str != null && !"".equals(str)) {
								goodsPrice.setDistributionPriceCg(VerifyAndShowErrorMsgUtil
										.verifyBigDecimalByString(str, errorMsg + "不是数字, 请验证后重试!"));
							}
							break;
						case 14:// 销售经销商价
							if (str == null || "".equals(str)) {
								result.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空，请验证！");
								throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空，请验证！");
							}
							goodsPrice.setDistributionPrice(VerifyAndShowErrorMsgUtil.verifyBigDecimalByString(str,
									errorMsg + "不是数字, 请验证后重试!"));
							break;
						case 15:// VIP价
							if (str != null && !"".equals(str)) {
								goodsPrice.setVipPrice(VerifyAndShowErrorMsgUtil.verifyBigDecimalByString(str,
										errorMsg + "不是数字, 请验证后重试!"));
							}
							break;
						case 16:// 采购非公终端价
							if (str != null && !"".equals(str)) {
								goodsPrice.setPrivatePriceCg(VerifyAndShowErrorMsgUtil.verifyBigDecimalByString(str,
										errorMsg + "不是数字, 请验证后重试!"));
							}
							break;
						case 17:// 销售非公终端价
							if (str == null || "".equals(str)) {
								result.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空，请验证！");
								throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空，请验证！");
							}
							goodsPrice.setPrivatePrice(VerifyAndShowErrorMsgUtil.verifyBigDecimalByString(str,
									errorMsg + "不是数字, 请验证后重试!"));
							break;
						case 18:// 采购公立终端价
							if (str != null && !"".equals(str)) {
								goodsPrice.setPublicPriceCg(VerifyAndShowErrorMsgUtil.verifyBigDecimalByString(str,
										errorMsg + "不是数字, 请验证后重试!"));
							}
							break;
						case 19:// 销售公历终端价
							if (str == null || "".equals(str)) {
								result.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空，请验证！");
								throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空，请验证！");
							}
							goodsPrice.setPublicPrice(VerifyAndShowErrorMsgUtil.verifyBigDecimalByString(str,
									errorMsg + "不是数字, 请验证后重试!"));
							break;
						case 20:// 采购终端报备
							if (str != null && !"".equals(str)) {
								if (str.contains("是")) {
									goodsPrice.setIsReportTerminalCg(1);
								} else {
									goodsPrice.setIsReportTerminalCg(0);
								}
							}
							break;
						case 21:// 销售终端报备
							if (str == null || "".equals(str)) {
								result.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空，请验证！");
								throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空，请验证！");
							}
							if (str != null && !"".equals(str)) {
								if (str.contains("是")) {
									goodsPrice.setIsReportTerminal(1);
								} else {
									goodsPrice.setIsReportTerminal(0);
								}
							}
							break;
						case 22:// 采购厂家授权书
							if (str != null && !"".equals(str)) {
								if (str.contains("需保证金")) {
									goodsPrice.setIsManufacturerAuthorizationCg(2);
								} else if (str.contains("是")) {
									goodsPrice.setIsManufacturerAuthorizationCg(1);
								} else {
									goodsPrice.setIsManufacturerAuthorizationCg(0);
								}
							}
							break;
						case 23:// 销售厂家授权书
							if (str != null && !"".equals(str)) {
								if (str.contains("需保证金")) {
									goodsPrice.setIsManufacturerAuthorization(2);
								} else if (str.contains("是")) {
									goodsPrice.setIsManufacturerAuthorization(1);
								} else {
									goodsPrice.setIsManufacturerAuthorization(0);
								}
							} else {
								result.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空，请验证！");
								throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空，请验证！");
							}
							break;
						case 24:// 成本价格时间
							if (str == null || "".equals(str)) {
								result.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
										+ "列：值不允许为空。 成本价的时间不允许为空, 请验证后重试！");
								throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
										+ "列：值不允许为空。 成本价的时间不允许为空, 请验证后重试！");
							}
							if (!str.contains("-")) {
								result.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
										+ "列：值错误。 成本价的时间格式不正确,格式:yyyy年MM月dd日-yyyy年MM月dd日, 请验证后重试！");
								throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
										+ "列：值错误。成本价的时间格式不正确,格式:yyyy年MM月dd日-yyyy年MM月dd日, 请验证后重试！");
							}
							String[] dateStr = str.split("-");
							// SimpleDateFormat sdf = new
							// SimpleDateFormat("yyyy年MM月dd日");
							// Date dateStart = sdf.parse(dateStr[0]);
							Date dateStart = VerifyAndShowErrorMsgUtil.verifyDateByString(dateStr[0], "yyyy年MM月dd日",
									errorMsg);
							// Date dateEnd = sdf.parse(dateStr[1]);
							Date dateEnd = VerifyAndShowErrorMsgUtil.verifyDateByString(dateStr[1], "yyyy年MM月dd日",
									errorMsg);
							// 校验值
							if (dateStart.after(dateEnd)) {
								result.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
										+ "列：值错误。 成本价的时间填写错误, 前面时间应小于后面时间。格式:yyyy年MM月dd日-yyyy年MM月dd日, 请验证后重试！");
								throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
										+ "列：值错误。成本价的时间填写错误, 前面时间应小于后面时间。格式:yyyy年MM月dd日-yyyy年MM月dd日, 请验证后重试！");
							}

							goodsPrice.setCostPriceStartTime(dateStart.getTime());
							goodsPrice.setCostPriceEndTime(dateEnd.getTime());
							break;
						case 25:// 成本价格
							if (str == null || "".equals(str)) {
								result.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空，请验证！");
								throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空，请验证！");
							}
							goodsPrice.setType(1);
							goodsPrice.setCostPrice(VerifyAndShowErrorMsgUtil.verifyBigDecimalByString(str,
									errorMsg + "不是数字, 请验证后重试!"));
							break;
						case 26:// 采购批量价数量
							if (str != null && !"".equals(str)) {
								try {
									if (str.contains("-")) {
										String[] num = str.split("-");
										// Integer minXS =
										// Integer.parseInt(num[0]);
										Integer minXS = VerifyAndShowErrorMsgUtil.verifyIntegerByString(num[0],
												errorMsg + " 最小值不是数字! 请验证后重试!");
										// Integer maxXS =
										// Integer.parseInt(num[1]);
										Integer maxXS = VerifyAndShowErrorMsgUtil.verifyIntegerByString(num[0],
												errorMsg + " 最大值不是数字! 请验证后重试!");
										if (maxXS >= minXS) {
											goodsPrice.setBatchPriceMinNumCg(minXS);
											goodsPrice.setBatchPriceMaxNumCg(maxXS);
										} else {
											result.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
													+ "列：最大值和最小值顺序错误,例如:1-10");
											throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
													+ "列：最大值和最小值顺序错误,例如:1-10！");
										}
									} else if (str.contains(">")) {
										str = str.replace(">", "");
										goodsPrice.setBatchPriceMinNumCg(VerifyAndShowErrorMsgUtil
												.verifyIntegerByString(str, errorMsg + "不是数字。请验证后重试!"));
									} else {
										result.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
												+ "列：最大值和最小值应为数字,例如:1-10或>10");
										throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
												+ "列：最大值和最小值应为数字,例如:1-10或>10");
									}
								} catch (Exception e) {
									result.setMessage(
											"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：最大值和最小值应为数字,例如:1-10或>10");
									throw new Exception(
											"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：最大值和最小值应为数字,例如:1-10或>10",
											e);
								}
							}
							break;
						case 27:// 采购批量价格
							if (StringUtil.isNotBlank(str)) {
								goodsPrice.setBatchPriceCg(VerifyAndShowErrorMsgUtil.verifyBigDecimalByString(str,
										errorMsg + "不是数字。请校验后重试!"));
							}
							break;
						case 28:// 销售批量价数量
							if (StringUtil.isNotBlank(str)) {
								try {
									if (str.contains("-")) {
										String[] num = str.split("-");
										// Integer minXS =
										// Integer.parseInt(num[0]);
										Integer minXS = VerifyAndShowErrorMsgUtil.verifyIntegerByString(num[0],
												errorMsg + " 最小值不是数字! 请验证后重试!");
										// Integer maxXS =
										// Integer.parseInt(num[1]);
										Integer maxXS = VerifyAndShowErrorMsgUtil.verifyIntegerByString(num[1],
												errorMsg + " 最大值不是数字! 请验证后重试!");
										if (maxXS > minXS) {
											goodsPrice.setBatchPriceMinNum(minXS);
											goodsPrice.setBatchPriceMaxNum(maxXS);
										} else {
											result.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
													+ "列：最大值和最小值顺序错误,例如:1-10 。 请验证后重试!");
											throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
													+ "列：最大值和最小值顺序错误,例如:1-10 。 请验证后重试!");
										}
									} else if (str.contains(">")) {
										str = str.replace(">", "");
										goodsPrice.setBatchPriceMinNum(VerifyAndShowErrorMsgUtil
												.verifyIntegerByString(str, errorMsg + " 最小值不是数字!例如:>10。 请验证后重试!"));
									} else {
										result.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
												+ "列：最大值和最小值应为数字,例如:1-10或>10。  请验证后重试!");
										throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
												+ "列：最大值和最小值应为数字,例如:1-10或>10。 请验证后重试!");
									}
								} catch (Exception e) {
									result.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
											+ "列：最大值和最小值应为数字,例如:1-10或>10。 请验证后重试!");
									throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
											+ "列：最大值和最小值应为数字,例如:1-10或>10。 请验证后重试!", e);
								}

							}
							break;
						case 29:// 销售批量价格
							if (StringUtil.isNotBlank(str)) {
								goodsPrice.setBatchPrice(VerifyAndShowErrorMsgUtil.verifyBigDecimalByString(str,
										errorMsg + "不是数字。请验证后重试!"));
							}
							break;
						default:
							result.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：获取参数值异常，请填写正确后重试!");
							throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：获取参数值异常，请填写正确后重试!");
						}
					} else {
						Date dateStart = null;
						switch (type) {
						case Cell.CELL_TYPE_BLANK:// 空白单元格
							str = null;
							break;
						case Cell.CELL_TYPE_NUMERIC:// 数值、日期类型
							if (HSSFDateUtil.isCellDateFormatted(cell)) {
								double d = cell.getNumericCellValue();
								Date date = HSSFDateUtil.getJavaDate(d);
								str = DateUtil.DateToString(date, "yyyy-MM-dd");
								dateStart = DateUtil.StringToDate(str);
							} else {
								if (type != Cell.CELL_TYPE_STRING) {
									cell.setCellType(Cell.CELL_TYPE_STRING);
								}
								String value = cell.getStringCellValue().trim();

								dateStart = VerifyAndShowErrorMsgUtil.verifyDateByString(value, "yyyy年MM月dd日",
										errorMsg);
								// 数值类型
								// result.setMessage("第:" + (rowNum + 1)+ "行，第:"
								// + (cellNum + 1)+ "列：非日期类型，请验证！");
								// throw new Exception("第:" + (rowNum + 1)+
								// "行，第:" + (cellNum + 1)+ "列：非日期类型，请验证！");
							}
							break;
						default:
							if (type != Cell.CELL_TYPE_STRING) {
								cell.setCellType(Cell.CELL_TYPE_STRING);
							}
							String value = cell.getStringCellValue().trim();
							dateStart = VerifyAndShowErrorMsgUtil.verifyDateByString(value, "yyyy年MM月dd日", errorMsg);
							break;
						}

						switch (cellNum) {
						case 30:// 采购核价有效期
							if (dateStart != null) {
								goodsPrice.setPeriodDateCg(dateStart);
							}
							break;
						case 31:// 销售核价有效期
							if (str != null) {
								if (dateStart != null) {
									goodsPrice.setPeriodDateXs(dateStart);
								}
							} else {
								result.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空，请验证！");
								throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空，请验证！");
							}
							break;
						default:
							result.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：获取参数值异常，请检查后重试！");
							throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：获取参数值异常，请检查后重试！");
						}
					}
				}
				// 插入记录的信息
				goodsPrice.setCreator(user.getUserId());
				goodsPrice.setUpdater(user.getUserId());
				goodsPrice.setAddTime(time);
				goodsPrice.setModTime(time);
				list.add(goodsPrice);
			}
			if (list != null && !list.isEmpty()) {
				// 批量保存
				List<String> sku_list = new ArrayList<>();// sku列表
				// 验证sku不允许重复
				if (list != null && !list.isEmpty()) {
					for (int i = 0; i < list.size(); i++) {
						sku_list.add(list.get(i).getSku());
					}
					if (sku_list != null) {
						// 验证sku是否存在产品表
						List<Goods> goods_list = goodsService.batchVailGoodsSku(sku_list);
						if (goods_list != null && !goods_list.isEmpty()) {
							int num = 0;
							for (int x = 0; x < list.size(); x++) {
								for (int y = 0; y < goods_list.size(); y++) {
									if (list.get(x).getSku().equals(goods_list.get(y).getSku())) {
										list.get(x).setGoodsId(goods_list.get(y).getGoodsId());
										num++;
									}
								}
								if (num == 0) {
									// sku在第一列
									result.setMessage("第:" + (x + 2) + "行，第:1列：产品SKU不存在，请验证！");
									throw new Exception("第:" + (x + 2) + "行，第:1列：产品SKU不存在，请验证！");
								}
								num = 0;
							}
						}
					}
					result = goodsService.batchGoodsPriceSave(list);
				}
			}

		} catch (Exception e) {
			if (e instanceof IllegalArgumentException && null != e.getMessage() && e.getMessage().indexOf("OLE2") != -1
					&& e.getMessage().indexOf("OOXML") != -1) {
				result.setMessage("上传文件格式错误，请下载标准模板填写正确后重试！");
			} else if (e instanceof ShowErrorMsgException) {
				ShowErrorMsgException seme = (ShowErrorMsgException) e;
				// 错误信息[主要定位错误位置]
				String errorMsg = seme.getErrorMsg();
				// 时间为空
				if ("0001".equals(seme.getErrorCode())) {
					seme.setErrorMsg(errorMsg + "值错误，时间不可为空。请验证后重试！");
				}
				// 时间格式错误
				else if ("0002".equals(seme.getErrorCode())) {
					seme.setErrorMsg(errorMsg + "日期格式错误，格式为*yyyy年MM月dd日。请验证后重试！");
				}
				// 时间值错误
				else if ("0003".equals(seme.getErrorCode())) {
					seme.setErrorMsg(errorMsg + "值错误。请正确填写时间值;如月份范围[1-12],每月天数根据该年的该月份的天数范围填写。请验证后重试！");
				}

				result.setMessage(seme.getErrorMsg());
			}
			if ("操作失败".equals(result.getMessage())) {
				result.setMessage("上传文件失败, 请下载标准模板填写正确后重试！");
			}
			logger.error("batchAuthorizationPricingOpt:", e);
		}

		return result;
	}

	/**
	 * 
	 * <b>Description:</b><br>
	 * 批量物流运输导入模板
	 * 
	 * @param request
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2018年3月20日 下午1:45:34
	 */
	@ResponseBody
	@RequestMapping(value = "/logisticsTransportation")
	public ModelAndView logisticsTransportation(HttpServletRequest request) {
		return new ModelAndView("goods/goods/batch_logistics_ransportation");
	}

	/**
	 * 
	 * <b>Description:</b><br>
	 * 保存批量物流运输
	 * 
	 * @param request
	 * @param lwfile
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2018年3月20日 下午1:50:36
	 */
	@ResponseBody
	@RequestMapping("/batchLogisticsTransportation")
	public ResultInfo<?> batchLogisticsTransportation(HttpServletRequest request,
			@RequestParam("lwfile") MultipartFile lwfile) {
		ResultInfo<?> result = new ResultInfo<>();
		try {
			// 临时文件存放地址
			String path = request.getSession().getServletContext().getRealPath("/upload/goods");
			FileInfo fileInfo = ftpUtilService.fileUploadServe(path, lwfile);
			if (fileInfo.getCode() == 0) {// 上传成功

				List<GoodsExtend> list = new ArrayList<>();// 保存Excel中读取的数据
				// 获取excel路径
				Workbook workbook = WorkbookFactory.create(new FileInputStream(new File(fileInfo.getFilePath())));
				// 获取第一面sheet
				Sheet sheet = workbook.getSheetAt(0);
				// 起始行
				int startRowNum = sheet.getFirstRowNum() + 1;// 第一行标题
				int endRowNum = sheet.getLastRowNum();// 结束行
				for (int rowNum = startRowNum; rowNum <= endRowNum; rowNum++) {// 循环行数
					Row row = sheet.getRow(rowNum);
					if (row == null) {
						result.setMessage("第" + rowNum + "行中无数据，请验证！");
						throw new Exception("第" + rowNum + "行中无数据，请验证！");
					}
					int startCellNum = 0;// 默认从第一列开始
					int endCellNum = row.getLastCellNum() - 1;// 结束列
					// 获取excel的值
					GoodsExtend goodsExtend = new GoodsExtend();
					String str = null;
					for (int cellNum = startCellNum; cellNum <= endCellNum; cellNum++) {// 循环列数（下表从0开始）
						Cell cell = row.getCell(cellNum);
						try {
							Integer type = cell == null ? 3 : cell.getCellType();
							str = null;
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
							case 0:// 订货号
								if (str == null || "".equals(str)) {
									result.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空，请验证！");
									throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空，请验证！");
								}
								goodsExtend.setSku(str);
								break;
							case 1:
							case 2:
							case 3:
							case 4:
								break;
							case 5:// 现货交货日期
								if (str == null || "".equals(str)) {
									result.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空，请验证！");
									throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空，请验证！");
								}
								goodsExtend.setDelivery(str);
								break;
							case 6:// 期货交货期
								if (str == null || "".equals(str)) {
									result.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空，请验证！");
									throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空，请验证！");
								}
								goodsExtend.setFuturesDelivery(str);
								break;
							case 7:// 运输要求
								goodsExtend.setTransportRequirements(str);
								break;
							case 8:// 运输重量
								goodsExtend.setTransportWeight(str);
								break;
							case 9:// 是否包含运费
								if (str != null && !"".equals(str)) {
									if (str.contains("是")) {
										goodsExtend.setIsHvaeFreight(1);
									} else if (str.contains("否")) {
										goodsExtend.setIsHvaeFreight(0);
									}
								}
								break;
							case 10:// 运费结算方式
								if (str != null && !"".equals(str)) {
									if ("货款同步".equals(str)) {
										goodsExtend.setFreightSettlementMethod(669);
									} else if ("月结".equals(str)) {
										goodsExtend.setFreightSettlementMethod(670);
									} else if ("到付".equals(str)) {
										goodsExtend.setFreightSettlementMethod(671);
									} else if ("乙方自取".equals(str)) {
										goodsExtend.setFreightSettlementMethod(672);
									} else {
										result.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
										throw new Exception(
												"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
									}
								}
								break;
							case 11:// 运输完成标准
								if (str != null && !"".equals(str)) {
									if ("上楼".equals(str)) {
										goodsExtend.setTransportationCompletionStandard(662);
									} else if ("不上楼".equals(str)) {
										goodsExtend.setTransportationCompletionStandard(663);
									} else if ("自提".equals(str)) {
										goodsExtend.setTransportationCompletionStandard(664);
									} else {
										result.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
										throw new Exception(
												"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
									}
								}
								break;
							case 12:// 特殊运输条件
								if (str != null && !"".equals(str)) {
									if (!str.contains("易碎品") && !str.contains("危险品") && !str.contains("易燃、易爆品")) {
										result.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
										throw new Exception(
												"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
									} else {
										goodsExtend.setTsCondition(str);
									}
								}
								break;
							case 13:// 验收注意事项
								goodsExtend.setAcceptanceNotice(str);
								break;
							case 14:// 装箱数量
								goodsExtend.setPackingNumber(str);
								break;
							case 15:// 包装数量
								goodsExtend.setPackingQuantity(str);
								break;
							default:
								result.setMessage(
										"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：获取参数值异常，请稍等重试或联系管理员！");
								throw new Exception(
										"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：获取参数值异常，请稍等重试或联系管理员！");
							}
						} catch (Exception e) {
							logger.error(Contant.ERROR_MSG, e);
							return result;
						}
					}
					// 插入记录的信息
					list.add(goodsExtend);
				}
				List<String> sku_list = new ArrayList<>();// sku列表
				// 验证sku不允许重复
				if (list != null && !list.isEmpty()) {
					for (int i = 0; i < list.size(); i++) {
						int num = 0;
						for (int j = 0; j < list.size(); j++) {
							if (list.get(i).getSku().equals(list.get(j).getSku())) {
								if (num > 0) {
									result.setMessage("第:" + (i + 1) + "行，第:" + (j + 1) + "行：SKU名称重复，请验证！");
									throw new Exception("第:" + (i + 1) + "行，第:" + (j + 1) + "行：SKU名称重复，请验证！");
								}
								num++;
							}
						}
						sku_list.add(list.get(i).getSku());
					}
					if (sku_list != null) {
						// 验证sku是否存在产品表
						List<Goods> goods_list = goodsService.batchVailGoodsSku(sku_list);
						if (goods_list != null && !goods_list.isEmpty()) {
							int num = 0;
							for (int x = 0; x < list.size(); x++) {
								for (int y = 0; y < goods_list.size(); y++) {
									if (list.get(x).getSku().equals(goods_list.get(y).getSku())) {
										list.get(x).setGoodsId(goods_list.get(y).getGoodsId());
										num++;
									}
								}
								if (num == 0) {
									// sku在第一列
									result.setMessage("第:" + (x + 2) + "行，第:1列：产品SKU不存在，请验证！");
									throw new Exception("第:" + (x + 2) + "行，第:1列：产品SKU不存在，请验证！");
								}
								num = 0;
							}
						}
					}
					// 批量保存
					result = goodsService.batchSaveGoodsExtend(list);
				}
			}
		} catch (Exception e) {
			logger.error("batchLogisticsTransportation:", e);
			return result;
		}
		return result;
	}

	/**
	 * <b>Description:</b><br>
	 * 保存添加的产品
	 * 
	 * @param request
	 * @param session
	 * @param goods
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年5月22日 下午5:45:43
	 */
	@ResponseBody
	@RequestMapping(value = "/saveadd")
	@SystemControllerLog(operationType = "add", desc = "保存新增产品")
	public ModelAndView saveAdd(HttpServletRequest request, HttpSession session, Goods goods) {
		ModelAndView mv = new ModelAndView();
		try {
			User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
			if (user != null) {
				goods.setCompanyId(user.getCompanyId());
			}
			goods = goodsService.saveAdd(goods, request, session);
			if (null != goods) {
				mv.addObject("url", "./viewbaseinfo.do?goodsId=" + goods.getGoodsId());
				return success(mv);
			} else {
				return fail(mv);
			}
		} catch (Exception e) {
			logger.error("goods saveadd:", e);
			return fail(mv);
		}
	}

	/**
	 * <b>Description:</b><br>
	 * 验证产品名称是否重复
	 * 
	 * @param request
	 * @param goods
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年6月15日 下午5:23:44
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/validgoodsname")
	public ResultInfo validGoodName(HttpServletRequest request, Goods goods) {
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		if (user != null) {
			goods.setCompanyId(user.getCompanyId());
		}
		ResultInfo<?> result = goodsService.validGoodName(goods);
		return result;
	}

	/**
	 * <b>Description:</b><br>
	 * 初始化禁用页面
	 * 
	 * @param request
	 * @param id
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年5月25日 上午11:00:35
	 */
	@ResponseBody
	@RequestMapping(value = "/initDiscardPage")
	public ModelAndView initDisabledPage(HttpServletRequest request, Integer id) {
		ModelAndView mav = new ModelAndView("goods/goods/forbid");
		mav.addObject("id", id);
		return mav;
	}

	/**
	 * <b>Description:</b><br>
	 * 产品启用禁用操作
	 * 
	 * @param id
	 * @param isDiscard
	 * @param discardReason
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年5月25日 上午11:39:17
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/isDiscardById")
	public ResultInfo isDiscardById(HttpServletRequest request, Goods goods) {
		ResultInfo<?> result = goodsService.isDiscardById(goods);
		return result;
	}

	/**
	 * <b>Description:</b><br>
	 * 查看产品详情
	 * 
	 * @param request
	 * @param goods
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年5月25日 下午4:07:46
	 */
	@ResponseBody
	@RequestMapping(value = "/view")
	public ModelAndView view(HttpServletRequest request, Goods goods) {

		ModelAndView mv = new ModelAndView();
		mv.addObject("goods", goods);
		mv.setViewName("goods/goods/view");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 查看产品基本信息
	 * 
	 * @param request
	 * @param goods
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年5月25日 下午4:07:57
	 */
	@RequestMapping(value = "/viewbaseinfo2")
	@FormToken(save = true)
	@ResponseBody
	@Deprecated
	public ModelAndView viewbaseinfo2(HttpServletRequest request, Goods goods) {
//		if(goods.getGoodsId()>500000){
//			ModelAndView mv = new ModelAndView();
//			mv.setViewName("redirect:/goods/vgoods/viewSku.do?skuId="+goods.getGoodsId());
//			return mv;
//		}
		ModelAndView mv = new ModelAndView();
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		mv.addObject("curr_user", user);
		if (user != null) {
			goods.setCompanyId(user.getCompanyId());
		}

		// 获取产品对应的附件列表
		GoodsAttachment goodsAttachment = new GoodsAttachment();
		goodsAttachment.setGoodsId(goods.getGoodsId());
		List<GoodsAttachment> attachmentList = goodsService.getGoodsAttachmentListByGoodsId(goodsAttachment);

		// 产品类别
		List<SysOptionDefinition> goodsTypes = getSysOptionDefinitionList(SysOptionConstant.ID_315);
		// if(JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST+SysOptionConstant.ID_315)){
		// String
		// json=JedisUtils.get(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST+SysOptionConstant.ID_315);
		// JSONArray jsonArray=JSONArray.fromObject(json);
		// goodsTypes=(List<SysOptionDefinition>)
		// JSONArray.toCollection(jsonArray, SysOptionDefinition.class);
		// }

		// 获取应用科室
		List<GoodsSysOptionAttribute> goodsSysOptionAttributeList = goodsService.getGoodsSysOptionAttributeList(goods);
		mv.addObject("goodsSysOptionAttributeList", goodsSysOptionAttributeList);
		// 产品级别
		List<SysOptionDefinition> goodsLevels = getSysOptionDefinitionList(SysOptionConstant.ID_334);
		mv.addObject("goodsLevels", goodsLevels);
		// 管理类别
		List<SysOptionDefinition> manageCategorys = getSysOptionDefinitionList(SysOptionConstant.ID_20);
		mv.addObject("manageCategorys", manageCategorys);
		// 管理类别级别
		List<SysOptionDefinition> manageCategorylevels = getSysOptionDefinitionList(SysOptionConstant.ID_338);
		mv.addObject("manageCategorylevels", manageCategorylevels);

		// 通用属性
		GoodsAttribute goodsAttribute = new GoodsAttribute();
		goodsAttribute.setGoodsId(goods.getGoodsId());
		List<GoodsAttribute> goodsAttributeList = goodsService.getGoodsAttributeList(goodsAttribute);
		Map<String, List> goodsAttributeMap = new HashMap<String, List>();

		List<String> list = new ArrayList<String>();
		Integer oldId = 0;
		Integer vid = 0;
		for (GoodsAttribute attr : goodsAttributeList) {
			Integer newId = attr.getCategoryAttributeId();
			if (vid == 0)
				oldId = attr.getCategoryAttributeId();
			if (newId != oldId) {
				list = new ArrayList<String>();
				oldId = attr.getCategoryAttributeId();
			}
			list.add(attr.getAttrValue());
			goodsAttributeMap.put(attr.getCategoryAttributeName(), list);
			vid++;
		}
		Map<String, Object> historicInfo = actionProcdefService.getHistoric(processEngine,
				"goodsVerify_" + goods.getGoodsId());
		mv.addObject("taskInfo", historicInfo.get("taskInfo"));
		mv.addObject("startUser", historicInfo.get("startUser"));
		mv.addObject("candidateUserMap", historicInfo.get("candidateUserMap"));
		// 最后审核状态
		mv.addObject("endStatus", historicInfo.get("endStatus"));
		mv.addObject("historicActivityInstance", historicInfo.get("historicActivityInstance"));
		mv.addObject("commentMap", historicInfo.get("commentMap"));
		Task taskInfo = (Task) historicInfo.get("taskInfo");
		String verifyUsers = null;
		if (null != taskInfo) {
			Map<String, Object> taskInfoVariables = actionProcdefService.getVariablesMap(taskInfo);
			verifyUsers = (String) taskInfoVariables.get("verifyUsers");
		}
		mv.addObject("verifyUsers", verifyUsers);
		// 获取当前时间
		Long currentTime = DateUtil.convertLong(DateUtil.convertString(DateUtil.sysTimeMillis(), "yyyy-MM-dd"),
				"yyyy-MM-dd");
		mv.addObject("currentTime", currentTime);
		goods = goodsService.getGoodsById(goods);
		mv.addObject("goods", goods);
		mv.addObject("attachmentList", attachmentList);
		mv.addObject("goodsTypes", goodsTypes);
		mv.addObject("goodsAttributeMap", goodsAttributeMap);
		mv.setViewName("goods/goods/view_baseinfo");
		return mv;
	}
	@RequestMapping(value = "/viewbaseinfo")
	public String viewBaseInfo(HttpServletRequest request, Goods goods) {
		return "redirect:/goods/vgoods/viewSku.do?skuId="+goods.getGoodsId();
	}

	/**
	 * <b>Description:</b><br>
	 * 编辑产品基本信息
	 * 
	 * @param request
	 * @param goods
	 * @return
	 * @throws IOException
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年5月26日 上午9:33:15
	 */
	@ResponseBody
	@RequestMapping(value = "/editbaseinfo")
	public ModelAndView editBaseInfo(HttpServletRequest request, Goods goods) throws IOException {
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);

		// 查询顶级产品分类
		Category category = new Category();
		StandardCategory standardCategory = new StandardCategory();
		standardCategory.setParentId(0);
		category.setParentId(0);
		if (user != null) {
			category.setCompanyId(user.getCompanyId());
			goods.setCompanyId(user.getCompanyId());
		}
		List<Category> categoryList = categoryService.getParentCateList(category);
		List<StandardCategory> standardCategoryList = categoryService.getParentStandardCateList(standardCategory);

		Brand brand = new Brand();
		brand.setCompanyId(user.getCompanyId());
		List<Brand> brandList = brandService.getAllBrand(brand);

		Unit unit = new Unit();
		unit.setCompanyId(user.getCompanyId());
		List<Unit> unitList = unitService.getAllUnitList(unit);

		// 获取通用属性选中ID
		GoodsAttribute goodsAttribute = new GoodsAttribute();
		goodsAttribute.setGoodsId(goods.getGoodsId());
		List<GoodsAttribute> goodsAttributeList = goodsService.getGoodsAttributeList(goodsAttribute);
		String goodsAttributeIds = "";
		for (GoodsAttribute attr : goodsAttributeList) {
			goodsAttributeIds += attr.getCategoryAttrValueId() + ",";
		}

		// 获取产品对应的附件列表
		GoodsAttachment goodsAttachment = new GoodsAttachment();
		goodsAttachment.setGoodsId(goods.getGoodsId());
		List<GoodsAttachment> attachmentList = goodsService.getGoodsAttachmentListByGoodsId(goodsAttachment);

		// 获取应用科室
		List<GoodsSysOptionAttribute> goodsSysOptionAttributeList = goodsService.getGoodsSysOptionAttributeList(goods);
		String goodsSysOptionAttributeIds = "";
		for (GoodsSysOptionAttribute goodsSysOptionAttribute : goodsSysOptionAttributeList) {
			goodsSysOptionAttributeIds += goodsSysOptionAttribute.getAttributeId() + ",";
		}

		ModelAndView mv = new ModelAndView();

		goods = goodsService.getGoodsById(goods);
		mv.addObject("categoryList", categoryList);
		mv.addObject("standardCategoryList", standardCategoryList);
		mv.addObject("goods", goods);
		mv.addObject("brandList", brandList);
		mv.addObject("unitList", unitList);
		mv.addObject("goodsAttributeIds", goodsAttributeIds);
		mv.addObject("attachmentList", attachmentList);
		mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(goods)));
		mv.addObject("goodsSysOptionAttributeIds", goodsSysOptionAttributeIds);
		mv.setViewName("goods/goods/edit_baseinfo");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 保存编辑产品基本信息
	 * 
	 * @param request
	 * @param session
	 * @param goods
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年5月26日 下午12:22:28
	 */
	@ResponseBody
	@RequestMapping(value = "/savebaseinfo")
	@SystemControllerLog(operationType = "edit", desc = "保存编辑产品基本信息")
	public ModelAndView saveBaseInfo(HttpServletRequest request, HttpSession session, Goods goods,
			String beforeParams) {
		ModelAndView mv = new ModelAndView();
		try {
			User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
			goods.setCompanyId(user.getCompanyId());
			goods = goodsService.saveBaseInfo(goods, request, session);
			if (null != goods) {
				mv.addObject("url", "./viewbaseinfo.do?goodsId=" + goods.getGoodsId());
				return success(mv);
			} else {
				return fail(mv);
			}
		} catch (Exception e) {
			logger.error("savebaseinfo:", e);
			return fail(mv);
		}
	}

	/**
	 * <b>Description:</b><br>
	 * 查看产品销售信息
	 * 
	 * @param request
	 * @param goods
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年5月25日 下午4:19:44
	 */
	@ResponseBody
	@RequestMapping(value = "/viewsaleinfo")
	public ModelAndView viewSaleInfo(HttpServletRequest request, Goods goods) {
		ModelAndView mv = new ModelAndView();
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		if (user != null) {
			goods.setCompanyId(user.getCompanyId());
		}

		try {
			// 获取应用科室
			List<GoodsSysOptionAttribute> goodsSysOptionAttributeList = goodsService
					.getGoodsSysOptionAttributeList(goods);

			// 获取产品对应的附件列表
			GoodsAttachment goodsAttachment = new GoodsAttachment();
			goodsAttachment.setGoodsId(goods.getGoodsId());
			List<GoodsAttachment> attachmentList = goodsService.getGoodsAttachmentListByGoodsId(goodsAttachment);

			// 产品级别
			List<SysOptionDefinition> goodsLevels = getSysOptionDefinitionList(SysOptionConstant.ID_334);
			// if(JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST+SysOptionConstant.ID_334)){
			// String
			// json=JedisUtils.get(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST+SysOptionConstant.ID_334);
			// JSONArray jsonArray=JSONArray.fromObject(json);
			// goodsLevels=(List<SysOptionDefinition>)
			// JSONArray.toCollection(jsonArray, SysOptionDefinition.class);
			// }

			// 管理类别
			List<SysOptionDefinition> manageCategorys = getSysOptionDefinitionList(SysOptionConstant.ID_20);
			// if(JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST+SysOptionConstant.ID_20)){
			// String
			// json=JedisUtils.get(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST+SysOptionConstant.ID_20);
			// JSONArray jsonArray=JSONArray.fromObject(json);
			// manageCategorys=(List<SysOptionDefinition>)
			// JSONArray.toCollection(jsonArray, SysOptionDefinition.class);
			// }

			// 管理类别级别
			List<SysOptionDefinition> manageCategorylevels = getSysOptionDefinitionList(SysOptionConstant.ID_338);
			// if(JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST+SysOptionConstant.ID_338)){
			// String
			// json=JedisUtils.get(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST+SysOptionConstant.ID_338);
			// JSONArray jsonArray=JSONArray.fromObject(json);
			// manageCategorylevels=(List<SysOptionDefinition>)
			// JSONArray.toCollection(jsonArray, SysOptionDefinition.class);
			// }

			// 获取核价信息
			Integer type = 0;// 销售
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("goodsId", goods.getGoodsId());
			map.put("type", type);
			List<GoodsChannelPrice> goodsChannelPriceList = goodsChannelPriceService.getGoodsChannelPriceByGoodsId(map);
			mv.addObject("goodsChannelPriceList", goodsChannelPriceList);

			// 获取主供应商列表信息
			List<TraderSupplierVo> mainSupplyList = traderSupplierService
					.getMainSupplyListByGoodsId(goods.getGoodsId());
			mv.addObject("mainSupplyList", mainSupplyList);

			GoodsOpt goodsOpt = new GoodsOpt();
			// 获取配件信息
			goodsOpt.setParentGoodsId(goods.getGoodsId());
			List<GoodsOpt> listPackage = goodsService.getGoodsPackageList(goodsOpt);
			mv.addObject("listPackage", listPackage);
			// 获取管理产品信息
			goodsOpt.setGoodsId(goods.getGoodsId());
			List<GoodsOpt> listRecommend = goodsService.getGoodsRecommendList(goodsOpt);
			mv.addObject("listRecommend", listRecommend);

			// 获取产品的扩展信息
			GoodsExtend goodsExtend = goodsService.getGoodsExtend(goods);
			mv.addObject("goodsExtend", goodsExtend);

			// 获取商品单元
			List<Goods> unitList = goodsService.getGoodsUnitList(goods);
			mv.addObject("unitList", unitList);

			// 获取当前时间
			Long currentTime = DateUtil.convertLong(DateUtil.convertString(DateUtil.sysTimeMillis(), "yyyy-MM-dd"),
					"yyyy-MM-dd");

			goods = goodsService.getGoodsById(goods);
			mv.addObject("goods", goods);
			mv.addObject("goodsSysOptionAttributeList", goodsSysOptionAttributeList);
			mv.addObject("attachmentList", attachmentList);
			mv.addObject("goodsLevels", goodsLevels);
			mv.addObject("manageCategorys", manageCategorys);
			mv.addObject("manageCategorylevels", manageCategorylevels);
			mv.addObject("currentTime", currentTime);
		} catch (Exception e) {
			logger.error("viewsaleinfo:", e);
		}
		mv.setViewName("goods/goods/view_saleinfo");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 编辑产品销售信息
	 * 
	 * @param request
	 * @param goods
	 * @return
	 * @throws IOException
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年5月26日 上午9:34:11
	 */
	@ResponseBody
	@RequestMapping(value = "/editsaleinfo")
	public ModelAndView editSaleInfo(HttpServletRequest request, Goods goods) throws IOException {
		ModelAndView mv = new ModelAndView();
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		if (user != null) {
			goods.setCompanyId(user.getCompanyId());
		}
		// 获取应用科室
		List<GoodsSysOptionAttribute> goodsSysOptionAttributeList = goodsService.getGoodsSysOptionAttributeList(goods);
		String goodsSysOptionAttributeIds = "";
		for (GoodsSysOptionAttribute goodsSysOptionAttribute : goodsSysOptionAttributeList) {
			goodsSysOptionAttributeIds += goodsSysOptionAttribute.getAttributeId() + ",";
		}

		// 获取产品对应的附件列表
		GoodsAttachment goodsAttachment = new GoodsAttachment();
		goodsAttachment.setGoodsId(goods.getGoodsId());
		List<GoodsAttachment> attachmentList = goodsService.getGoodsAttachmentListByGoodsId(goodsAttachment);

		goods = goodsService.getGoodsById(goods);
		mv.addObject("goods", goods);
		mv.addObject("goodsSysOptionAttributeIds", goodsSysOptionAttributeIds);
		mv.addObject("attachmentList", attachmentList);
		mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(goods)));
		mv.setViewName("goods/goods/edit_saleinfo");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 保存编辑产品销售信息
	 * 
	 * @param request
	 * @param session
	 * @param goods
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年5月26日 下午1:44:40
	 */
	@ResponseBody
	@RequestMapping(value = "/savesaleinfo")
	@SystemControllerLog(operationType = "edit", desc = "保存编辑产品销售信息")
	public ModelAndView saveSaleInfo(HttpServletRequest request, HttpSession session, Goods goods,
			String beforeParams) {
		ModelAndView mv = new ModelAndView();
		try {
			goods = goodsService.saveSaleInfo(goods, request, session);
			if (null != goods) {
				mv.addObject("url", "./viewbaseinfo.do?goodsId=" + goods.getGoodsId());
				return success(mv);
			} else {
				return fail(mv);
			}
		} catch (Exception e) {
			logger.error("savesaleinfo:", e);
			return fail(mv);
		}
	}

	/**
	 * <b>Description:</b><br>
	 * 查看产品采购信息
	 * 
	 * @param request
	 * @param goods
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2018年1月29日 上午10:53:14
	 */
	@ResponseBody
	@RequestMapping(value = "/viewpurchaseinfo")
	public ModelAndView viewPurchaseInfo(HttpServletRequest request, Goods goods) {
		ModelAndView mv = new ModelAndView();
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		if (user != null) {
			goods.setCompanyId(user.getCompanyId());
		}

		try {

			// 获取主供应商列表信息
			List<TraderSupplierVo> mainSupplyList = traderSupplierService
					.getMainSupplyListByGoodsId(goods.getGoodsId());
			mv.addObject("mainSupplyList", mainSupplyList);

			Map<String, Object> historicInfo = actionProcdefService.getHistoric(processEngine,
					"goodsVerify_" + goods.getGoodsId());
			mv.addObject("taskInfo", historicInfo.get("taskInfo"));
			mv.addObject("startUser", historicInfo.get("startUser"));
			mv.addObject("candidateUserMap", historicInfo.get("candidateUserMap"));
			// 最后审核状态
			mv.addObject("endStatus", historicInfo.get("endStatus"));
			mv.addObject("historicActivityInstance", historicInfo.get("historicActivityInstance"));
			mv.addObject("commentMap", historicInfo.get("commentMap"));
			Task taskInfo = (Task) historicInfo.get("taskInfo");
			String verifyUsers = null;
			if (null != taskInfo) {
				Map<String, Object> taskInfoVariables = actionProcdefService.getVariablesMap(taskInfo);
				verifyUsers = (String) taskInfoVariables.get("verifyUsers");
			}
			mv.addObject("verifyUsers", verifyUsers);

			// 获取当前时间
			Long currentTime = DateUtil.convertLong(DateUtil.convertString(DateUtil.sysTimeMillis(), "yyyy-MM-dd"),
					"yyyy-MM-dd");

			goods = goodsService.getGoodsById(goods);
			mv.addObject("goods", goods);
			mv.addObject("currentTime", currentTime);

			// 获取产品结算价
			GoodsSettlementPrice goodsSettlementPrice = new GoodsSettlementPrice();
			goodsSettlementPrice.setCompanyId(user.getCompanyId());
			goodsSettlementPrice.setGoodsId(goods.getGoodsId());
			GoodsSettlementPrice goodsSettlementPriceInfo = goodsSettlementPriceService
					.getGoodsSettlePriceByGoods(goodsSettlementPrice);
			// 产品核算价
			Integer type = 1;// 采购
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("goodsId", goods.getGoodsId());
			map.put("type", type);
			List<GoodsChannelPrice> goodsChannelPriceList = goodsChannelPriceService.getGoodsChannelPriceByGoodsId(map);
			mv.addObject("goodsChannelPriceList", goodsChannelPriceList);
			// 如果核价信息存在，取第一条的成本价
			if (goodsChannelPriceList != null && !goodsChannelPriceList.isEmpty()) {
				mv.addObject("channelCostPrice", goodsChannelPriceList.get(0).getCostPrice());
			}
			mv.addObject("goodsSettlementPriceInfo", goodsSettlementPriceInfo);
		} catch (Exception e) {
			logger.error("viewpurchaseinfo:", e);
		}
		goods = goodsService.getGoodsById(goods);
		mv.addObject("goods", goods);
		mv.setViewName("goods/goods/view_purchaseinfo");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 上传税收分类编码页面
	 * 
	 * @param request
	 * @param id
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年6月5日 下午1:14:44
	 */
	@ResponseBody
	@RequestMapping(value = "/uplodeTaxCategoryNo")
	public ModelAndView uplodeTaxCategoryNo(HttpServletRequest request, Integer id) {
		ModelAndView mv = new ModelAndView("goods/goods/uplode_tax_category_no");
		mv.addObject("id", id);
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 更新税收分类编码
	 * 
	 * @param request
	 * @param lwfile
	 * @return
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2017年6月7日 下午2:54:44
	 */
	@ResponseBody
	@RequestMapping("saveUplodeTaxCategoryNo")
	@SystemControllerLog(operationType = "import", desc = "导入税收分类编码")
	public ResultInfo<?> saveUplodeTaxCategoryNo(HttpServletRequest request,
			@RequestParam("lwfile") MultipartFile lwfile) {
		ResultInfo<?> resultInfo = new ResultInfo<>();
		try {
			User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
			// 临时文件存放地址
			String path = request.getSession().getServletContext().getRealPath("/upload/goods");
			FileInfo fileInfo = ftpUtilService.fileUploadServe(path, lwfile);
			if (fileInfo.getCode() == 0) {
				List<Goods> list = new ArrayList<>();
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
					int endCellNum = row.getLastCellNum() - 1;// 结束列
					// 获取excel的值
					Goods goods = new Goods();
					if (user != null) {
						goods.setModTime(DateUtil.gainNowDate());
						goods.setUpdater(user.getUserId());
					}
					for (int cellNum = startCellNum; cellNum <= endCellNum; cellNum++) {// 循环列数（下表从0开始）
						Cell cell = row.getCell(cellNum);

						if (cellNum == 0) {// 第一列数据cellNum==startCellNum
							// 若excel中无内容，而且没有空格，cell为空--默认3，空白
							if (cell == null || cell.getCellType() != 1) {
								resultInfo.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列非字符类型，请验证！");
								throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列非字符类型，请验证！");
							} else {
								goods.setSku(cell.getStringCellValue().toString());
							}
						}

						if (cellNum == 1) {// 第二列数据cellNum==(startCellNum+1)
							// 若excel中无内容，而且没有空格，cell为空--默认3，空白
							if (cell == null ) {
								resultInfo.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列非数字类型，请验证！");
								throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列非数字类型，请验证！");
							} else {
								goods.setTaxCategoryNo((long)cell.getNumericCellValue() + "");
							}
						}
						// null值判断
						if (goods.getSku() == null) {
							resultInfo.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列非字符类型，请验证！");
							throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列非字符类型，请验证！");
						}
						if (goods.getTaxCategoryNo() == null) {
							goods.setTaxCategoryNo("");
						}
					}
					list.add(goods);
				}
				// 保存更新
				resultInfo = goodsService.updateGoodsTaxCategoryNo(list);
			}
		} catch (Exception e) {
			logger.error("saveUplodeTaxCategoryNo:", e);
			return resultInfo;
		}
		return resultInfo;
	}

	/**
	 * <b>Description:</b><br>
	 * 批量新增产品信息初始化
	 * 
	 * @param request
	 * @return
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2017年6月7日 下午6:22:59
	 */
	@ResponseBody
	@RequestMapping(value = "/batchAddGoodsInit")
	public ModelAndView batchAddGoodsInit(HttpServletRequest request) {
		return new ModelAndView("goods/goods/batch_add_goods");
	}

	/**
	 * <b>Description:</b><br>
	 * 批量保存产品信息（读取Excel）
	 * 
	 * @param request
	 * @param lwfile
	 * @return
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Modify: Franlin at 2018-05-08 for bug[3815 批量新增商品修改]
	 *       修改批量上传校验逻辑</b> <br>
	 *       <b>Date:</b> 2017年6月9日 上午9:00:27
	 */
	@ResponseBody
	@RequestMapping("batchSaveGoods")
	public ResultInfo<?> batchSaveGoods(HttpServletRequest request, @RequestParam("lwfile") MultipartFile lwfile) {
		ResultInfo<?> resultInfo = new ResultInfo<>();
		try {
			User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);

			// 临时文件存放地址
			String path = request.getSession().getServletContext().getRealPath("/upload/goods");
			FileInfo fileInfo = ftpUtilService.fileUploadServe(path, lwfile);

			if (null == fileInfo || fileInfo.getCode() != 0) {
				resultInfo.setMessage("上传文件失败，请检查上传文件后重试！");
				throw new Exception("上传文件失败，请检查上传文件后重试！");
			}

			// 获取excel路径
			Workbook workbook = WorkbookFactory.create(new FileInputStream(new File(fileInfo.getFilePath())));
			// 获取第一面sheet
			Sheet sheet = workbook.getSheetAt(0);
			// 起始行
			int startRowNum = sheet.getFirstRowNum() + 1;// 第一行标题
			int endRowNum = sheet.getLastRowNum();// 结束行

			if (startRowNum > endRowNum) {
				resultInfo.setMessage("上传文件为空模板, 数据读取从第2行开始。 请下载标准模板并正确填写后重试!");
				throw new Exception("上传文件为空模板, 数据读取从第2行开始。 请下载标准模板并正确填写后重试!");
			}

			// 全部品牌
			Brand br = new Brand();
			br.setCompanyId(user.getCompanyId());
			List<Brand> brandList = brandService.getAllBrand(br);
			if (brandList.isEmpty()) {
				resultInfo.setMessage("未能验证到品牌信息，请验证！");
				throw new Exception("未能验证到品牌信息，请验证！");
			}
			// 获取全部单位
			Unit un = new Unit();
			un.setCompanyId(user.getCompanyId());
			List<Unit> unitList = unitService.getAllUnitList(un);
			if (unitList.isEmpty()) {
				resultInfo.setMessage("未能验证到单位信息，请验证！");
				throw new Exception("未能验证到单位信息，请验证！");
			}
			// 查询顶级产品分类
			Category cgy = new Category();
			cgy.setCompanyId(user.getCompanyId());
			List<Category> categoryList = categoryService.getParentCateList(cgy);
			if (categoryList.isEmpty()) {
				resultInfo.setMessage("未能验证到分类信息，请验证！");
				throw new Exception("未能验证到分类信息，请验证！");
			}

			// 查询顶级新国标分类
			List<StandardCategory> standardCategoryList = categoryService
					.getParentStandardCateList(new StandardCategory());
			if (standardCategoryList.isEmpty()) {
				resultInfo.setMessage("未能验证到新国标分类信息，请验证！");
				throw new Exception("未能验证到新国标分类信息，请验证！");
			}

			// 产品级别
			List<SysOptionDefinition> goodsLevels = getSysOptionDefinitionList(334);

			if (goodsLevels.isEmpty()) {
				resultInfo.setMessage("未能验证到级别信息，请验证！");
				throw new Exception("未能验证到级别信息，请验证！");
			}

			/**
			 * 商品类型
			 */
			List<SysOptionDefinition> goodsTypes = getSysOptionDefinitionList(315);

			if (null == goodsTypes || goodsTypes.isEmpty()) {
				resultInfo.setMessage("未能验证到类别信息，请验证！");
				throw new Exception("未能验证到类别信息，请验证！");
			}

			// 编号代码
			List<SysOptionDefinition> numberCodes = getSysOptionDefinitionList(20);

			if (numberCodes.isEmpty()) {
				resultInfo.setMessage("未能获取到编码代号信息，请验证！");
				throw new Exception("未能获取编码代号信息，请验证！");
			}

			// 编号代码
			List<SysOptionDefinition> managerCategory = getSysOptionDefinitionList(338);

			// 保存Excel中读取的数据
			List<Goods> list = new ArrayList<>();

			// 循环行数
			for (int rowNum = startRowNum; rowNum <= endRowNum; rowNum++) {
				// 获取excel的值
				Goods goods = new Goods();
				// 设置商品属性
				if (null != user) {
					goods.setCompanyId(user.getCompanyId());
					goods.setCreator(user.getUserId());
					goods.setUpdater(user.getUserId());
				}
				goods.setAddTime(DateUtil.gainNowDate());
				goods.setModTime(DateUtil.gainNowDate());

				// 商品类型为试剂标志 为true;
				boolean goodType_SJ_Flag = false;
				// 新国标分类为非医疗机械时为true;
				boolean standardCategory_FYLJX_Flag = false;
				// 存在管理级别标志
				boolean manageCategoryLevelExistFlag = true;
				Integer category1 = null, category2 = null, category3 = null;
				// 新国标一级分类, 二级分类, 三级分类
				Integer standardCategory1 = null, standardCategory2 = null, standardCategory3 = null;
				// 开始时间
				Long beginTime = null;

				// 获取行数据
				Row row = sheet.getRow(rowNum);

				if (row == null) {
					resultInfo.setMessage("第" + (rowNum + 1) + "行中无数据，请验证！");
					throw new Exception("第" + (rowNum + 1) + "行中无数据，请验证！");
				}
				// 设置起始列,默认从第一列开始.
				int startCellNum = 0;
				// 设置结束列,默认第37列结束 AK
				int endCellNum = 36;
				// 循环列数（下表从0开始）
				for (int cellNum = startCellNum; cellNum <= endCellNum; cellNum++) {
					// 获取表格数据
					Cell cell = row.getCell(cellNum);
					String errorMsg = "第" + (rowNum + 1) + "行，第" + (cellNum + 1) + "列: ";
					// 获取当前表格类型[数值型--0/字符串型--1/公式型--2/空值--3/布尔型--4/错误 --5]
					Integer type = null == cell ? 3 : cell.getCellType();
					String str = null;
					// 根据表格类型获取值
					switch (type) {
					// 空白单元格
					case Cell.CELL_TYPE_BLANK:
						str = null;
						break;
					// 字符类型
					case Cell.CELL_TYPE_STRING:
						str = cell.getStringCellValue().toString().trim();
						break;
					// 数值、日期类型
					// case Cell.CELL_TYPE_NUMERIC:
					// // 日期类型
					// Long daLong = getDateLong(getValue(cell));
					//
					// if(null != daLong)
					// {
					// str = daLong + "";
					// }
					// // 数字类型
					// else
					// {
					// double d = cell.getNumericCellValue();
					// str = String.format("%.2f", d);
					// }
					// break;
					// 默认字符串
					default:
						str = getValue(cell, cellNum);
						break;
					}
					// 必填字段
					if (cellNum <= 3 || (cellNum >= 6 && cellNum <= 15) || (cellNum >= 24 && cellNum <= 28)
							|| cellNum == 34) {
						switch (cellNum) {
						// * 商品名称
						case 0:
							if (str == null || "".equals(str)) {
								resultInfo.setMessage(
										"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空。商品名称不可为空，请验证后重试！");
								throw new Exception(
										"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空。商品名称不可为空，请验证后重试！");
							}
							// 长度校验
							if (StringUtil.sumCharacterLength(str) > 80) {
								resultInfo.setMessage(errorMsg + "商品名称为 " + str + "长度超过80个字符,其中一个汉字2个字符,请验证后重试!");
								throw new Exception(errorMsg + "商品名称为 " + str + "长度超过80个字符,其中一个汉字2个字符,请验证后重试!");
							}
							// 商品名称前面不可以有空字符串
							if (str != null) {
								//出去首尾字符串查看是否以*开头
								boolean goodNameStartsWith = str.trim().startsWith("*");
								if ( goodNameStartsWith ) {
									resultInfo.setMessage(errorMsg + "商品名称为 " + str + "产品名称第一位不允许为特殊字符*,请验证后重试!");
									throw new Exception(errorMsg + "商品名称为 " + str + "产品名称第一位不允许为特殊字符*,请验证后重试!");
								}
							}
							
							// 此excel查重
							for (int x = 0; x < list.size(); x++) {
								if (str.equals(list.get(x).getGoodsName())) {
									resultInfo.setMessage(
											"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：商品名称在此Excel表格中存在重复，请验证！");
									throw new Exception(
											"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：商品名称在此Excel表格中存在重复，请验证！");
								}
							}
							goods.setGoodsName(str);
							break;
						// * 品牌名称
						case 1:
							if (StringUtil.isBlank(str)) {
								resultInfo.setMessage(
										"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空。品牌名称不可为空，请验证后重试！");
								throw new Exception(
										"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空。品牌名称不可为空，请验证后重试！");
							}
							for (int i = 0; i < brandList.size(); i++) {
								if (str.equals(brandList.get(i).getBrandName())) {
									goods.setBrandId(brandList.get(i).getBrandId());
									break;
								}
							}
							// 未能查询到对应的品牌ID
							if (goods.getBrandId() == null) {
								resultInfo.setMessage(
										"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误。请正确填写品牌名称，请验证后重试！");
								throw new Exception(
										"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误。请正确填写品牌名称，请验证后重试！");
							}
							break;
						// * 制造商型号
						case 2:
							if (str == null || "".equals(str)) {
								resultInfo.setMessage(
										"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空。制造商型号不可为空，请验证后重试！");
								throw new Exception(
										"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空。制造商型号不可为空，请验证后重试！");
							}
							// 校验长度
							if (StringUtil.sumCharacterLength(str) > 40) {
								resultInfo.setMessage(errorMsg + "制造商型号为 " + str + "长度超过40个字符,其中一个汉字2个字符,请验证后重试!");
								throw new Exception(errorMsg + "制造商型号为 " + str + "长度超过40个字符,其中一个汉字2个字符,请验证后重试!");
							}
							goods.setModel(str);
							break;
						// * 厂家名称
						case 3:
							if (str == null || "".equals(str)) {
								resultInfo.setMessage(
										"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空。厂家名称不可为空，请验证后重试！");
								throw new Exception(
										"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空。厂家名称不可为空，请验证后重试！");
							}
							goods.setManufacturer(str);
							break;
						// * 商品类型(器械设备/试剂/耗材/高值耗材/其他)
						case 6:
							if (str == null || "".equals(str)) {
								resultInfo.setMessage(
										"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空。商品类型不可为空，请验证后重试！");
								throw new Exception(
										"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空。商品类型不可为空，请验证后重试！");
							}
							// 检查商品类型填写是否正确
							for (int i = 0; i < goodsTypes.size(); i++) {
								if (str.equals(goodsTypes.get(i).getTitle())) {
									goods.setGoodsType(goodsTypes.get(i).getSysOptionDefinitionId());
									break;
								}
							}
							if (goods.getGoodsType() == null) {
								resultInfo.setMessage(
										"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误。请正确填写商品类型，请验证后重试！");
								throw new Exception(
										"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误。请正确填写商品类型，请验证后重试！");
							}
							// 为试剂
							if (goods.getGoodsType() == 318) {
								goodType_SJ_Flag = true;
							}
							break;
						// * 新国标一级分类
						case 7:
							// 商品类型不为试剂时
							if (!goodType_SJ_Flag) {
								if (str == null && "".equals(str)) {
									resultInfo.setMessage(
											"第:" + (rowNum + 1) + "行，第:" + (cellNum) + "列：值不允许为空。新国标一级分类不可为空，请验证后重试！");
									throw new Exception(
											"第:" + (rowNum + 1) + "行，第:" + (cellNum) + "列：值不允许为空。新国标一级分类不可为空，请验证后重试！");
								}

								for (int i = 0; i < standardCategoryList.size(); i++) {
									if (str.equals(standardCategoryList.get(i).getCategoryName())) {
										standardCategory1 = standardCategoryList.get(i).getStandardCategoryId();
										break;
									}
								}
								if (standardCategory1 == null) {
									resultInfo.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
											+ "列：值错误。请正确填写新国标一级分类，请验证后重试！");
									throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
											+ "列：值错误。请正确填写新国标一级分类，请验证后重试！");
								} else {
									// 选择非医疗机械
									if (standardCategory1 == 1388) {
										standardCategory_FYLJX_Flag = true;
									}
									goods.setStandardCategoryId(standardCategory1);
								}

							}
							break;
						// 新国标二级分类
						case 8:
							if (!goodType_SJ_Flag) {
								// 根据PARENT_ID查询子类信息
								List<StandardCategory> subList = getSubStandardCategorysByParentId(standardCategory1);

								// 查不到子类列表
								if (null == subList || subList.isEmpty())
									break;

								for (int i = 0; i < subList.size(); i++) {
									if (null == subList.get(i) || null == subList.get(i).getCategoryName())
										continue;
									if ((subList.get(i).getCategoryName()).equals(str)) {
										standardCategory2 = subList.get(i).getStandardCategoryId();
										break;
									}
								}
								if (null == standardCategory2) {
									resultInfo.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
											+ "列：值错误，请正确填写新国标二级分类，请验证后重试！");
									throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
											+ "列：值错误，请正确填写新国标二级分类，请验证后重试！");
								} else {
									goods.setStandardCategoryId(standardCategory2);
								}

							}
							break;
						// 新国标三级分类
						case 9:
							if (!goodType_SJ_Flag) {
								// 根据PARENT_ID查询子类信息
								List<StandardCategory> subList = getSubStandardCategorysByParentId(standardCategory2);

								// 查不到子类列表
								if (null == subList || subList.isEmpty())
									break;

								for (int i = 0; i < subList.size(); i++) {
									if (null == subList.get(i) || null == subList.get(i).getCategoryName())
										continue;
									if ((subList.get(i).getCategoryName()).equals(str)) {
										standardCategory3 = subList.get(i).getStandardCategoryId();
										break;
									}
								}
								if (null == standardCategory3) {
									resultInfo.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
											+ "列：值错误，请正确填写新国标三级分类，请验证后重试！");
									throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
											+ "列：值错误，请正确填写新国标三级分类，请验证后重试！");
								} else {
									goods.setStandardCategoryId(standardCategory3);
								}
							}
							break;
						// * 旧国标分类 194
						case 10:
							if (str == null || "".equals(str)) {
								resultInfo.setMessage(
										"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空。旧国标分类不可为空，请验证后重试！");
								throw new Exception(
										"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空。旧国标分类不可为空，请验证后重试！");
							}
							for (int x = 0; x < numberCodes.size(); x++) {
								if (str.equals(numberCodes.get(x).getTitle())) {
									goods.setManageCategory(numberCodes.get(x).getSysOptionDefinitionId());
									break;
								}
							}
							// 未能查询到对应的编号代码
							if (goods.getManageCategory() == null) {
								resultInfo.setMessage(
										"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误。请正确填写旧国标分类，请验证后重试！");
								throw new Exception(
										"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误。请正确填写旧国标分类，请验证后重试！");
							}
							// 新国标分类为非医疗机械
							if (standardCategory_FYLJX_Flag && goods.getManageCategory() != 194) {
								resultInfo.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
										+ "列：值错误。存在新国标分类且为非医疗机械，旧国标分类应与之保持一致,请验证后重试！");
								throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
										+ "列：值错误。存在新国标分类且为非医疗机械，旧国标分类应与之保持一致,请验证后重试！");
							}
							break;
						// * 管理类别
						case 11:
							// 不为非医疗机械
							if (!standardCategory_FYLJX_Flag) {
								if (str == null || "".equals(str)) {
									resultInfo.setMessage(
											"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空。管理类别不可为空，请验证后重试！");
									throw new Exception(
											"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空。管理类别不可为空，请验证后重试！");
								}
								// if(number_code.startsWith("6821") ||
								// number_code.startsWith("6840")){//医疗器械
								if ("一类".equals(str) || "二类".equals(str) || "三类".equals(str)) {
									for (int x = 0; x < managerCategory.size(); x++) {
										if (str.equals(managerCategory.get(x).getTitle())) {
											goods.setManageCategoryLevel(
													managerCategory.get(x).getSysOptionDefinitionId());
											break;
										}
									}
								}

								if (goods.getManageCategoryLevel() == null) {
									resultInfo.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
											+ "列：值错误。存在新国标分类且不为非医疗机械，管理类别应填写一类，二类或三类,请验证后重试！");
									throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
											+ "列：值错误。存在新国标分类且不为非医疗机械，管理类别应填写一类，二类或三类,请验证后重试！");
								}
							} else {
								manageCategoryLevelExistFlag = false;
							}
							break;
						// * 商品运营一级分类
						case 12:
							if (str == null || "".equals(str)) {
								resultInfo.setMessage(
										"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空。商品运营一级分类不可为空，请验证后重试！");
								throw new Exception(
										"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空。商品运营一级分类不可为空，请验证后重试！");
							}
							for (int i = 0; i < categoryList.size(); i++) {
								if (str.equals(categoryList.get(i).getCategoryName())) {
									category1 = categoryList.get(i).getCategoryId();
									break;
								}
							}
							if (category1 == null) {
								resultInfo.setMessage(
										"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误。请正确填写商品运营一级分类，请验证后重试！");
								throw new Exception(
										"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误。请正确填写商品运营一级分类，请验证后重试！");
							} else {
								goods.setCategoryId(category1);
							}

							break;
						// 商品运营二级分类
						case 13:
							// 根据PARENT_ID查询子类信息
							List<Category> subList = getSubCategorysByParentId(category1, goods.getCompanyId());

							// 查不到子类列表
							if (null == subList || subList.isEmpty())
								break;

							if (StringUtil.isBlank(str)) {
								resultInfo.setMessage(errorMsg + " 值不允许为空。商品运营二级分类不可为空，请验证后重试！");
								throw new Exception(errorMsg + " 值不允许为空。商品运营二级分类不可为空，请验证后重试！");
							}

							for (int i = 0; i < subList.size(); i++) {
								if (null == subList.get(i) || null == subList.get(i).getCategoryName())
									continue;
								if ((subList.get(i).getCategoryName()).equals(str)) {
									category2 = subList.get(i).getCategoryId();
									break;
								}
							}
							if (null == category2) {
								resultInfo.setMessage(
										"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误。请正确填写商品运营二级分类，请验证后重试！");
								throw new Exception(
										"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误。请正确填写商品运营二级分类，请验证后重试！");
							} else {
								goods.setCategoryId(category2);
							}
							break;
						// 商品运营三级分类
						case 14:
							// 根据PARENT_ID查询子类信息
							List<Category> subcgs = getSubCategorysByParentId(category2, goods.getCompanyId());

							// 查不到子类列表
							if (null == subcgs || subcgs.isEmpty())
								break;

							if (StringUtil.isBlank(str)) {
								resultInfo.setMessage(errorMsg + " 值不允许为空。商品运营三级分类不可为空，请验证后重试！");
								throw new Exception(errorMsg + " 值不允许为空。商品运营三级分类不可为空，请验证后重试！");
							}
							for (int i = 0; i < subcgs.size(); i++) {
								if (null == subcgs.get(i) || null == subcgs.get(i).getCategoryName())
									continue;
								if ((subcgs.get(i).getCategoryName()).equals(str)) {
									category3 = subcgs.get(i).getCategoryId();
									break;
								}
							}
							if (null == category3) {
								resultInfo.setMessage(
										"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误。请正确填写商品运营三级分类，请验证后重试！");
								throw new Exception(
										"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误。请正确填写商品运营三级分类，请验证后重试！");
							} else {
								goods.setCategoryId(category3);
							}
							break;
						// * 单位
						case 15:
							if (StringUtil.isBlank(str)) {
								resultInfo.setMessage(
										"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空。单位不可为空，请验证后重试！");
								throw new Exception(
										"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空。单位不可为空，请验证后重试！");
							}
							for (int i = 0; i < unitList.size(); i++) {
								if (str.equals(unitList.get(i).getUnitName())) {
									goods.setUnitId(unitList.get(i).getUnitId());
									break;
								}
							}
							if (goods.getUnitId() == null) {// 未能查询到对应的单位ID
								resultInfo.setMessage(
										"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误。请正确填写单位，请验证后重试！");
								throw new Exception(
										"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误。请正确填写单位，请验证后重试！");
							}
							break;
						// * 技术参数
						case 24:
							if (str == null || "".equals(str)) {
								resultInfo.setMessage(
										"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空。技术参数不可为空，请验证后重试！");
								throw new Exception(
										"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空。技术参数不可为空，请验证后重试！");
							}
							goods.setTechnicalParameter(str);
							break;
						// 性能参数
						case 25:
							goods.setPerformanceParameter(str);
							break;
						// 规格参数
						case 26:
							goods.setSpecParameter(str);
							break;
						// * 备案编号
						case 27:
							// 管理类别为1类, 备案编号必填
							if (manageCategoryLevelExistFlag && 339 == goods.getManageCategoryLevel()) {
								if (str == null || "".equals(str)) {
									resultInfo.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
											+ "列：值不允许为空。当管理类别为一类时，备案编号不可为空，请验证后重试！");
									throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
											+ "列：值不允许为空。当管理类别为一类时，备案编号不可为空，请验证后重试！");
								}
								goods.setRecordNumber(str);
							}
							break;
						// case 28://批准文号
						// goods.setLicenseNumber(str);
						// break;
						// * 注册证号
						case 28:
							// 管理类别为2类或者3类, 注册证号
							if (manageCategoryLevelExistFlag && (340 == goods.getManageCategoryLevel()
									|| 341 == goods.getManageCategoryLevel())) {
								if (str == null || "".equals(str)) {
									resultInfo.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
											+ "列：值不允许为空。当管理类别为二类或三类时，注册证号不可为空，请验证后重试！");
									throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
											+ "列：值不允许为空。当管理类别为二类或三类时，注册证号不可为空，请验证后重试！");
								}
								goods.setRegistrationNumber(str);
							}
							break;
						// 商品级别(核心产品/常规产品/一次性产品)
						case 34:
							if (str == null || "".equals(str)) {
								resultInfo.setMessage(
										"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空。商品级别不可为空，请验证后重试！");
								throw new Exception(
										"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空。商品级别不可为空，请验证后重试！");
							}
							for (int i = 0; i < goodsLevels.size(); i++) {
								if (str.equals(goodsLevels.get(i).getTitle())) {
									goods.setGoodsLevel(goodsLevels.get(i).getSysOptionDefinitionId());
									break;
								}
							}
							if (goods.getGoodsLevel() == null) {
								resultInfo.setMessage(
										"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误。请正确填写商品级别，请验证后重试！");
								throw new Exception(
										"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误。请正确填写商品级别，请验证后重试！");
							}
							break;
						default:
							resultInfo.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：获取参数值异常，请检查后重试!");
							throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：获取参数值异常，请检查后重试!");
						}
					}
					// 注册证生效时间
					else if (cellNum == 29 || cellNum == 30) {

						if (manageCategoryLevelExistFlag
								&& (340 == goods.getManageCategoryLevel() || 341 == goods.getManageCategoryLevel())) {
							if (str == null || "".equals(str)) {
								resultInfo.setMessage("第" + (rowNum + 1) + "行，第" + (cellNum + 1)
										+ "列：值不允许为空。当管理类别为二类或三类时，注册证生效时间不可为空，请验证后重试！");
								throw new Exception("第" + (rowNum + 1) + "行，第" + (cellNum + 1)
										+ "列：值不允许为空。当管理类别为二类或三类时，注册证生效时间不可为空，请验证后重试！");
							}

							Long time = getDateLong(str, errorMsg);

							if (null == time) {
								resultInfo.setMessage(errorMsg + "值错误或格式错误。当管理类别为二类或三类时。请正确填写注册证生效时间和格式，请验证后重试！");
								throw new Exception(errorMsg + "值错误或格式错误。当管理类别为二类或三类时。请正确填写注册证生效时间和格式，请验证后重试！");
							}
							switch (cellNum) {
							// 注册证生效时间(XXX年XX月XX日) 开始时间
							case 29:
								beginTime = time;
								goods.setBegintime(time);
								break;
							// 注册证失效时间(XXX年XX月XX日) 结束时间
							case 30:
								if (time < beginTime) {
									resultInfo
											.setMessage(errorMsg + "值错误。当管理类别为二类或三类时。请正确填写注册证生效时间的结束时间应大于开始时间，请验证后重试！");
									throw new Exception(errorMsg + "值错误。当管理类别为二类或三类时。请正确填写注册证生效时间的结束时间应大于开始时间，请验证后重试！");
								}
								goods.setEndtime(time);
								break;
							default:
								resultInfo.setMessage(errorMsg + "值错误或格式错误。当管理类别为二类或三类时。请正确填写注册证生效时间和格式，请验证后重试！");
								throw new Exception(errorMsg + "值错误或格式错误。当管理类别为二类或三类时。请正确填写注册证生效时间和格式，请验证后重试！");
							}
						}
					}
					// 其他的字段
					else {
						switch (cellNum) {
						// 供应商型号
						case 4:
							goods.setSupplyModel(str);
							break;
						// 物料编码
						case 5:
							goods.setMaterialCode(str);
							break;
						// 尺寸长度（cm）
						case 16:
							if (str != null) {
								goods.setGoodsLength(VerifyAndShowErrorMsgUtil.verifyBigDecimalByString(str,
										errorMsg + "值错误。请检查是否数字，请验证后重试！"));
							}
							break;
						// 尺寸宽度（cm）
						case 17:
							if (str != null) {
								goods.setGoodsWidth(VerifyAndShowErrorMsgUtil.verifyBigDecimalByString(str,
										errorMsg + "值错误。请检查是否数字，请验证后重试！"));
							}
							break;
						// 尺寸高度（cm）
						case 18:
							if (str != null) {
								goods.setGoodsHeight(VerifyAndShowErrorMsgUtil.verifyBigDecimalByString(str,
										errorMsg + "值错误。请检查是否数字，请验证后重试！"));
							}
							break;
						case 19:// 尺寸净重（kg）
							if (str != null) {
								goods.setNetWeight(VerifyAndShowErrorMsgUtil.verifyBigDecimalByString(str,
										errorMsg + "值错误。请检查是否数字，请验证后重试！"));
							}
							break;
						case 20:// 包装信息长度（cm）
							if (str != null) {
								goods.setPackageLength(VerifyAndShowErrorMsgUtil.verifyBigDecimalByString(str,
										errorMsg + "值错误。请检查是否数字，请验证后重试！"));
							}
							break;
						case 21:// 包装信息宽度（cm）
							if (str != null) {
								goods.setPackageWidth(VerifyAndShowErrorMsgUtil.verifyBigDecimalByString(str,
										errorMsg + "值错误。请检查是否数字，请验证后重试！"));
							}
							break;
						case 22:// 包装信息高度（cm）
							if (str != null) {
								goods.setPackageHeight(VerifyAndShowErrorMsgUtil.verifyBigDecimalByString(str,
										errorMsg + "值错误。请检查是否数字，请验证后重试！"));
							}
							break;
						case 23:// 包装信息毛重（kg）
							if (str != null) {
								goods.setGrossWeight(VerifyAndShowErrorMsgUtil.verifyBigDecimalByString(str,
										errorMsg + "值错误。请检查是否数字，请验证后重试！"));
							}
							break;
						// 包装清单
						case 31:
							goods.setPackingList(str);
							break;
						// 商品规格
						case 32:
							goods.setSpec(str);
							break;
						// 商品产地（格式：国家/省/市/详细地址）
						case 33:
							goods.setProductAddress(str);
							break;
						// 采购提醒
						case 35:
							goods.setPurchaseRemind(str);
							break;
						// 服务条款
						case 36:
							goods.setTos(str);
							break;
						default:
							resultInfo.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：获取参数值异常，请检查后重试!");
							throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：获取参数值异常，请检查后重试!");
						}
					}

				}
				list.add(goods);
			}
			if (list != null && !list.isEmpty()) {
				
				//校验产品名称不可以为空或者以*开头
				
				
				// 验证产品名称是否重复
				List<String> str_list = goodsService.batchSaveVailGoodsName(list);
				if (str_list != null && !str_list.isEmpty()) {
					for (int x = 0; x < list.size(); x++) {
						for (String str : str_list) {
							if (list.get(x).getGoodsName().equals(str)) {
								resultInfo.setMessage("第:" + (x + 2) + "行，第:1列：产品名称与已有产品名称重复，请验证！");
								throw new Exception("第:" + (x + 2) + "行，第:1列：产品名称与已有产品名称重复，请验证！");
							}
						}
					}
				}
				// 批量保存
				resultInfo = goodsService.batchSaveGoods(list);
			}

		} catch (Exception e) {
			if (e instanceof IllegalArgumentException && null != e.getMessage() && e.getMessage().indexOf("OLE2") != -1
					&& e.getMessage().indexOf("OOXML") != -1) {
				resultInfo.setMessage("上传文件格式错误，请下载标准模板填写正确后重试！");
			} else if (e instanceof ShowErrorMsgException) {
				ShowErrorMsgException seme = (ShowErrorMsgException) e;
				// 错误信息[主要定位错误位置]
				String errorMsg = seme.getErrorMsg();
				// 时间为空
				if ("0001".equals(seme.getErrorCode())) {
					seme.setErrorMsg(errorMsg + "值不允许为空。当管理类别为二类或三类时，注册证生效时间不可为空，请验证后重试！");
				}
				// 时间格式错误
				else if ("0002".equals(seme.getErrorCode())) {
					seme.setErrorMsg(errorMsg + "值错误。当管理类别为二类或三类时，注册证生效时间时间格式错误，格式：yyyy年MM月dd日。请验证后重试！");
				}
				// 时间值错误
				else if ("0003".equals(seme.getErrorCode())) {
					seme.setErrorMsg(errorMsg
							+ "值错误。当管理类别为二类或三类时，注册证生效时间时间值错误，请正确填写时间值;如月份范围[1-12],每月天数根据该年的该月份的天数范围填写。请验证后重试！");
				}

				resultInfo.setMessage(seme.getErrorMsg());
			}
			logger.error("batchSaveGoods:", e);
		}
		return resultInfo;
	}

	/**
	 * <b>Description:</b><br>
	 * 批量更新产品信息初始化（Excel上传）
	 * 
	 * @param request
	 * @return
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2017年6月9日 上午9:04:39
	 */
	@ResponseBody
	@RequestMapping(value = "/batchUpdateGoodsInit")
	public ModelAndView batchUpdateGoodsInit(HttpServletRequest request) {
		return new ModelAndView("goods/goods/batch_update_goods");
	}

	/**
	 * <b>Description:</b><br>
	 * 保存-批量更新产品信息（Excel解析）
	 * 
	 * @param request
	 * @return
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2017年6月9日 上午9:19:48
	 */
	@ResponseBody
	@RequestMapping(value = "/batchUpdateGoodsSave")
	public ResultInfo<?> batchUpdateGoodsSave(HttpServletRequest request,
			@RequestParam("lwfile") MultipartFile lwfile) {
		ResultInfo<?> resultInfo = new ResultInfo<>();
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		try {
			// 临时文件存放地址
			String path = request.getSession().getServletContext().getRealPath("/upload/goods");
			FileInfo fileInfo = ftpUtilService.fileUploadServe(path, lwfile);

			if (fileInfo.getCode() == 0) {// 上传成功
				// 全部品牌
				List<Brand> brandList = brandService.getAllBrand(new Brand());
				if (brandList.isEmpty()) {
					resultInfo.setMessage("未能验证到品牌信息，请验证！");
					throw new Exception("未能验证到品牌信息，请验证！");
				}
				// 获取全部单位
				List<Unit> unitList = unitService.getAllUnitList(new Unit());
				if (unitList.isEmpty()) {
					resultInfo.setMessage("未能验证到单位信息，请验证！");
					throw new Exception("未能验证到单位信息，请验证！");
				}
				// 查询顶级产品分类
				List<Category> categoryList = categoryService.getParentCateList(new Category());
				if (categoryList.isEmpty()) {
					resultInfo.setMessage("未能验证到分类信息，请验证！");
					throw new Exception("未能验证到分类信息，请验证！");
				}

				// 查询顶级新国标分类
				List<StandardCategory> standardCategoryList = categoryService
						.getParentStandardCateList(new StandardCategory());
				if (standardCategoryList.isEmpty()) {
					resultInfo.setMessage("未能验证到新国标分类信息，请验证！");
					throw new Exception("未能验证到新国标分类信息，请验证！");
				}

				// 产品级别
				List<SysOptionDefinition> goodsLevels = getSysOptionDefinitionList(334);
				// if(JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST+334)){
				// String
				// json=JedisUtils.get(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST+334);
				// JSONArray jsonArray=JSONArray.fromObject(json);
				// goodsLevels=(List<SysOptionDefinition>)
				// JSONArray.toCollection(jsonArray, SysOptionDefinition.class);
				// }
				if (goodsLevels.isEmpty()) {
					resultInfo.setMessage("未能验证到级别信息，请验证！");
					throw new Exception("未能验证到级别信息，请验证！");
				}

				// 产品类别
				List<SysOptionDefinition> goodsTypes = getSysOptionDefinitionList(315);
				// if(JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST+315)){
				// String
				// json=JedisUtils.get(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST+315);
				// JSONArray jsonArray=JSONArray.fromObject(json);
				// goodsTypes=(List<SysOptionDefinition>)
				// JSONArray.toCollection(jsonArray, SysOptionDefinition.class);
				// }
				if (goodsTypes.isEmpty()) {
					resultInfo.setMessage("未能验证到类别信息，请验证！");
					throw new Exception("未能验证到类别信息，请验证！");
				}

				// 编号代码
				List<SysOptionDefinition> numberCodes = getSysOptionDefinitionList(20);
				// if(JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST+20)){
				// String
				// json=JedisUtils.get(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST+20);
				// JSONArray jsonArray=JSONArray.fromObject(json);
				// numberCodes=(List<SysOptionDefinition>)
				// JSONArray.toCollection(jsonArray, SysOptionDefinition.class);
				// }
				if (numberCodes.isEmpty()) {
					resultInfo.setMessage("未能获取到编码代号信息，请验证！");
					throw new Exception("未能获取编码代号信息，请验证！");
				}

				List<SysOptionDefinition> managerCategory = getSysOptionDefinitionList(338);

				List<Goods> list = new ArrayList<>();// 保存Excel中读取的数据
				// 获取excel路径
				Workbook workbook = WorkbookFactory.create(new FileInputStream(new File(fileInfo.getFilePath())));
				// 获取第一面sheet
				Sheet sheet = workbook.getSheetAt(0);
				// 起始行
				int startRowNum = sheet.getFirstRowNum() + 1;// 第一行标题
				int endRowNum = sheet.getLastRowNum();// 结束行

				// DecimalFormat df = new DecimalFormat("#.00");//格式化数字，保留两位小数

				for (int rowNum = startRowNum; rowNum <= endRowNum; rowNum++) {// 循环行数
					Row row = sheet.getRow(rowNum);

					// 不存在下一行，则退出循环
					if (null == row) {
						break;
					}

					// int startCellNum = row.getFirstCellNum();// 起始列（从有值得一列开始）
					int startCellNum = 0;// 默认从第一列开始（防止第一列为空）
					int endCellNum = 38; // row.getLastCellNum() - 1;// 结束列
					// 获取excel的值
					Goods goods = new Goods();
					if (user != null) {
						goods.setCompanyId(user.getCompanyId());
						goods.setUpdater(user.getUserId());
						goods.setModTime(DateUtil.gainNowDate());
					}
					Integer category1 = null, category2 = null, category3 = null;
					Integer standardCategory1 = null, standardCategory2 = null, standardCategory3 = null;
					String number_code = null;
					for (int cellNum = startCellNum; cellNum <= endCellNum; cellNum++) {// 循环列数（下表从0开始）
						Cell cell = row.getCell(cellNum);

						if (cell == null) {// cell==null单元格空白（无内容，默认空）
							if (cellNum == 0) {// 第一列
								resultInfo.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：不允许为空，请验证！");
								throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：不允许为空，请验证！");
							}
							continue;
						}
						Integer type = cell.getCellType();
						String str = null;
						if (cellNum < 17 || (cellNum >= 25 && cellNum <= 30) || cellNum >= 33) {
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
							// resultInfo.setMessage("第:" + (rowNum + 1) +
							// "行，第:" + (cellNum + 1) + "列非字符类型，请验证！");
							// throw new Exception("第:" + (rowNum + 1) + "行，第:"
							// + (cellNum + 1) + "列非字符类型，请验证！");
							}
							switch (cellNum) {
							case 0:// SKU（唯一识别，不为空）
								if (str == null || "".equals(str)) {
									resultInfo
											.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空，请验证！");
									throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空，请验证！");
								}
								for (int x = 0; x < list.size(); x++) {
									if (str.equals(list.get(x).getSku())) {
										resultInfo.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
												+ "列：SKU在此Excel表格中存在重复，请验证！");
										throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
												+ "列：SKU在此Excel表格中存在重复，请验证！");
									}
								}
								goods.setSku(str);
								break;
							case 1:// 产品名称
								goods.setGoodsName(str);
								if (str != null) {
									
									if (str.trim().startsWith("*")) {
										resultInfo.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
												+ "列：产品名称第一位不允许为特殊字符*，请验证！");
										throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
												+ "列：产品名称第一位不允许为特殊字符*，请验证！");
									}
									for (int x = 0; x < list.size(); x++) {
										if (str.equals(list.get(x).getGoodsName())) {
											resultInfo.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
													+ "列：产品名称在此Excel表格中存在重复，请验证！");
											throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
													+ "列：产品名称在此Excel表格中存在重复，请验证！");
										}
									}
								}
								break;
							case 2:// 品牌
								if (str != null && !"".equals(str)) {
									for (int i = 0; i < brandList.size(); i++) {
										if (str.equals(brandList.get(i).getBrandName())) {
											goods.setBrandId(brandList.get(i).getBrandId());
											break;
										}
									}
									if (goods.getBrandId() == null) {// 未能查询到对应的品牌ID
										resultInfo.setMessage(
												"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
										throw new Exception(
												"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
									}
								}
							case 3:// 厂家名称
								goods.setManufacturer(str);
								break;
							case 4:// 制造商型号
								goods.setModel(str);
								break;
							case 5:// 供应商型号
								goods.setSupplyModel(str);
								break;
							case 6:// 物料编码
								goods.setMaterialCode(str);
								break;
							case 7:// 新国标一级分类
								if (str != null && !"".equals(str)) {
									for (int i = 0; i < standardCategoryList.size(); i++) {
										if (str.equals(standardCategoryList.get(i).getCategoryName())) {
											standardCategory1 = standardCategoryList.get(i).getStandardCategoryId();
											break;
										}
									}
									if (standardCategory1 == null) {
										resultInfo.setMessage(
												"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
										throw new Exception(
												"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
									} else {
										goods.setStandardCategoryId(standardCategory1);
									}
								}
								break;
							case 8:// 新国标二级分类
								if (str != null && !"".equals(str)) {
									if (standardCategory1 == null) {// 二级分类不为空，一级分类为空
										resultInfo
												.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum) + "列：值不允许为空，请验证！");
										throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum) + "列：值不允许为空，请验证！");
									}
									for (int i = 0; i < standardCategoryList.size(); i++) {
										if (standardCategoryList.get(i) != null
												&& str.equals(standardCategoryList.get(i).getCategoryName())
												&& standardCategory1
														.equals(standardCategoryList.get(i).getParentId())) {
											standardCategory2 = standardCategoryList.get(i).getStandardCategoryId();
											break;
										}
									}
									if (standardCategory2 == null) {
										resultInfo.setMessage(
												"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
										throw new Exception(
												"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
									} else {
										goods.setStandardCategoryId(standardCategory2);
									}
								}
								break;
							case 9:// 新国标三级分类
								if (str != null && !"".equals(str)) {
									if (standardCategory1 == null || standardCategory2 == null) {// 三级分类不为空，一级或二级分类为空
										resultInfo
												.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum) + "列：值不允许为空，请验证！");
										throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum) + "列：值不允许为空，请验证！");
									}
									for (int i = 0; i < standardCategoryList.size(); i++) {
										if (str.equals(standardCategoryList.get(i).getCategoryName())) {
											System.out.println(standardCategoryList.get(i).getCategoryName());
										}
										if (standardCategory2.equals(standardCategoryList.get(i).getParentId())) {
											System.out.println(standardCategoryList.get(i).getCategoryName());
										}
										if (str.equals(standardCategoryList.get(i).getCategoryName())
												&& standardCategory2
														.equals(standardCategoryList.get(i).getParentId())) {
											standardCategory3 = standardCategoryList.get(i).getStandardCategoryId();
											break;
										}
									}
									if (standardCategory3 == null) {
										resultInfo.setMessage(
												"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
										throw new Exception(
												"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
									} else {
										goods.setStandardCategoryId(standardCategory3);
									}
									// 若第三级目录不是最底层分类
									for (int p = 0; p < standardCategoryList.size(); p++) {
										if (standardCategory3.equals(standardCategoryList.get(p).getParentId())) {
											resultInfo.setMessage(
													"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：非最小分类，请验证！");
											throw new Exception(
													"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：非最小分类，请验证！");
										}
									}
								} else {
									// 获取全部的分类之后，验证规则
									if (standardCategory2 != null) {// 三级目录为空，二级目录不为空
										// 若第二级目录不是最底层分类
										for (int p = 0; p < standardCategoryList.size(); p++) {
											if (standardCategory2.equals(standardCategoryList.get(p).getParentId())) {
												resultInfo.setMessage(
														"第:" + (rowNum + 1) + "行，第:" + (cellNum) + "列：非最小分类，请验证！");
												throw new Exception(
														"第:" + (rowNum + 1) + "行，第:" + (cellNum) + "列：非最小分类，请验证！");
											}
										}
									} else if (standardCategory1 != null) {// 二级目录为空，一级目录不为空
										// 若第一级目录不是最底层分类
										for (int p = 0; p < standardCategoryList.size(); p++) {
											if (standardCategory1.equals(standardCategoryList.get(p).getParentId())) {
												resultInfo.setMessage(
														"第:" + (rowNum + 1) + "行，第:" + (cellNum - 1) + "列：非最小分类，请验证！");
												throw new Exception(
														"第:" + (rowNum + 1) + "行，第:" + (cellNum - 1) + "列：非最小分类，请验证！");
											}
										}
									}
								}
								break;
							case 10:// 管理类别
								if (str != null && !"".equals(str)) {
									// if(number_code.startsWith("6821") ||
									// number_code.startsWith("6840")){//医疗器械
									if ("一类".equals(str) || "二类".equals(str) || "三类".equals(str)) {
										for (int x = 0; x < managerCategory.size(); x++) {
											if (str.equals(managerCategory.get(x).getTitle())) {
												goods.setManageCategoryLevel(
														managerCategory.get(x).getSysOptionDefinitionId());
												break;
											}
										}
									} else {
										resultInfo.setMessage(
												"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
										throw new Exception(
												"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
									}
									if (goods.getManageCategoryLevel() == null) {// 未能查询到对应的管理类别
										resultInfo.setMessage(
												"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
										throw new Exception(
												"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
									}
									// }
								}
								break;
							case 11:// 一级分类
								if (str != null && !"".equals(str)) {
									for (int i = 0; i < categoryList.size(); i++) {
										if (str.equals(categoryList.get(i).getCategoryName())) {
											category1 = categoryList.get(i).getCategoryId();
											break;
										}
									}
									if (category1 == null) {
										resultInfo.setMessage(
												"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
										throw new Exception(
												"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
									} else {
										goods.setCategoryId(category1);
									}
								}
								break;
							case 12:// 二级分类
								if (str != null && !"".equals(str)) {
									if (category1 == null) {// 二级分类不为空，一级分类为空
										resultInfo
												.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum) + "列：值不允许为空，请验证！");
										throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum) + "列：值不允许为空，请验证！");
									}
									for (int i = 0; i < categoryList.size(); i++) {
										if (str.equals(categoryList.get(i).getCategoryName())
												&& category1.equals(categoryList.get(i).getParentId())) {
											category2 = categoryList.get(i).getCategoryId();
											break;
										}
									}
									if (category2 == null) {
										resultInfo.setMessage(
												"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
										throw new Exception(
												"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
									} else {
										goods.setCategoryId(category2);
									}
								}
								break;
							case 13:// 三级分类
								if (str != null && !"".equals(str)) {
									if (category1 == null || category2 == null) {// 三级分类不为空，一级或二级分类为空
										resultInfo
												.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum) + "列：值不允许为空，请验证！");
										throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum) + "列：值不允许为空，请验证！");
									}
									for (int i = 0; i < categoryList.size(); i++) {
										if (str.equals(categoryList.get(i).getCategoryName())) {
											System.out.println(categoryList.get(i).getCategoryName());
										}
										if (category2.equals(categoryList.get(i).getParentId())) {
											System.out.println(categoryList.get(i).getCategoryName());
										}
										if (str.equals(categoryList.get(i).getCategoryName())
												&& category2.equals(categoryList.get(i).getParentId())) {
											category3 = categoryList.get(i).getCategoryId();
											break;
										}
									}
									if (category3 == null) {
										resultInfo.setMessage(
												"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
										throw new Exception(
												"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
									} else {
										goods.setCategoryId(category3);
									}
									// 若第三级目录不是最底层分类
									for (int p = 0; p < categoryList.size(); p++) {
										if (category3.equals(categoryList.get(p).getParentId())) {
											resultInfo.setMessage(
													"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：非最小分类，请验证！");
											throw new Exception(
													"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：非最小分类，请验证！");
										}
									}
								} else {
									// 获取全部的分类之后，验证规则
									if (category2 != null) {// 三级目录为空，二级目录不为空
										// 若第二级目录不是最底层分类
										for (int p = 0; p < categoryList.size(); p++) {
											if (category2.equals(categoryList.get(p).getParentId())) {
												resultInfo.setMessage(
														"第:" + (rowNum + 1) + "行，第:" + (cellNum) + "列：非最小分类，请验证！");
												throw new Exception(
														"第:" + (rowNum + 1) + "行，第:" + (cellNum) + "列：非最小分类，请验证！");
											}
										}
									} else if (category1 != null) {// 二级目录为空，一级目录不为空
										// 若第一级目录不是最底层分类
										for (int p = 0; p < categoryList.size(); p++) {
											if (category1.equals(categoryList.get(p).getParentId())) {
												resultInfo.setMessage(
														"第:" + (rowNum + 1) + "行，第:" + (cellNum - 1) + "列：非最小分类，请验证！");
												throw new Exception(
														"第:" + (rowNum + 1) + "行，第:" + (cellNum - 1) + "列：非最小分类，请验证！");
											}
										}
									}
								}
								break;
							case 14:// 旧国标分类
								if (str != null && !"".equals(str)) {

									for (int x = 0; x < numberCodes.size(); x++) {
										if (str.equals(numberCodes.get(x).getTitle())) {
											goods.setManageCategory(numberCodes.get(x).getSysOptionDefinitionId());
											break;
										}
									}
									if (goods.getManageCategory() == null) {// 未能查询到对应的编号代码
										resultInfo.setMessage(
												"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
										throw new Exception(
												"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
									}
									number_code = str;
								}
								break;
							case 15:// 单位
								if (str != null && !"".equals(str)) {
									for (int i = 0; i < unitList.size(); i++) {
										if (str.equals(unitList.get(i).getUnitName())) {
											goods.setUnitId(unitList.get(i).getUnitId());
											break;
										}
									}
									if (goods.getUnitId() == null) {// 未能查询到对应的单位ID
										resultInfo.setMessage(
												"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
										throw new Exception(
												"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
									}
								}
								break;
							case 16:// 产品类型(器械设备/试剂/耗材/高值耗材/其他)
								if (str != null && !"".equals(str)) {
									for (int i = 0; i < goodsTypes.size(); i++) {
										if (str.equals(goodsTypes.get(i).getTitle())) {
											goods.setGoodsType(goodsTypes.get(i).getSysOptionDefinitionId());
											break;
										}
									}
									if (goods.getGoodsType() == null) {
										resultInfo.setMessage(
												"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
										throw new Exception(
												"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
									}
								}
								break;
							case 25:// 技术参数
								if (str != null && !"".equals(str)) {
									goods.setTechnicalParameter(str);
								}
								break;
							case 26:// 性能参数
								if (str != null && !"".equals(str)) {
									goods.setPerformanceParameter(str);
								}
								break;
							case 27:// 规格参数
								if (str != null && !"".equals(str)) {
									goods.setSpecParameter(str);
								}
								break;
							case 28:// 备案编号
								if (str != null && !"".equals(str)) {
									goods.setRecordNumber(str);
								}
								break;
							case 29:// 批准文号
								if (str != null && !"".equals(str)) {
									goods.setLicenseNumber(str);
								}
								break;
							case 30:// 注册证号
								if (str != null && !"".equals(str)) {
									goods.setRegistrationNumber(str);
								}
								break;
							case 33:// 包装清单
								if (str != null && !"".equals(str)) {
									goods.setPackingList(str);
								}
								break;
							case 34:// 商品规格
								if (str != null && !"".equals(str)) {
									goods.setSpec(str);
								}
								break;
							case 35:// 商品产地（格式：国家/省/市/详细地址）
								if (str != null && !"".equals(str)) {
									goods.setProductAddress(str);
								}
								break;
							case 36:// 产品级别(核心产品/常规产品/一次性产品)
								if (str != null && !"".equals(str)) {
									for (int i = 0; i < goodsLevels.size(); i++) {
										if (str.equals(goodsLevels.get(i).getTitle())) {
											goods.setGoodsLevel(goodsLevels.get(i).getSysOptionDefinitionId());
											break;
										}
									}
									if (goods.getGoodsLevel() == null) {
										resultInfo.setMessage(
												"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
										throw new Exception(
												"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
									}
								}
								break;
							case 37:// 采购提醒
								if (str != null && !"".equals(str)) {
									goods.setPurchaseRemind(str);
								}
								break;
							case 38:// 服务条款
								if (str != null && !"".equals(str)) {
									goods.setTos(str);
								}
								break;
							default:
								resultInfo.setMessage(
										"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：获取参数值异常，请稍等重试或联系管理员！");
								throw new Exception(
										"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：获取参数值异常，请稍等重试或联系管理员！");
							}
						} else if (cellNum == 31 || cellNum == 32) {
							switch (type) {
							case Cell.CELL_TYPE_BLANK:// 空白单元格
								str = null;
								break;
							case Cell.CELL_TYPE_NUMERIC:// 数值、日期类型
								if (HSSFDateUtil.isCellDateFormatted(cell)) {
									double d = cell.getNumericCellValue();
									Date date = HSSFDateUtil.getJavaDate(d);
									// str = DateUtil.DateToString(date,
									// "yyyy-MM-dd");
									str = date.getTime() + "";
								} else {// 数值类型
									resultInfo
											.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：非日期类型，请验证！");
									throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：非日期类型，请验证！");
								}
								break;
							default:
								resultInfo.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：非日期类型，请验证！");
								throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：非日期类型，请验证！");
							}

							switch (cellNum) {
							case 31:// 注册证生效时间(XXX年XX月XX日)
								if (str != null) {
									goods.setBegintime(Long.valueOf(str));
								}
								break;
							case 32:// 注册证失效时间(XXX年XX月XX日)
								if (str != null) {
									goods.setEndtime(Long.valueOf(str));
								}
								break;
							default:
								resultInfo.setMessage(
										"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：获取参数值异常，请稍等重试或联系管理员！");
								throw new Exception(
										"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：获取参数值异常，请稍等重试或联系管理员！");
							}
						} else {
							switch (type) {
							case Cell.CELL_TYPE_BLANK:// 空白单元格
								str = null;
								break;
							case Cell.CELL_TYPE_NUMERIC:// 数值、日期类型
								if (HSSFDateUtil.isCellDateFormatted(cell)) {
									resultInfo
											.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：非数字类型，请验证！");
									throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：非数字类型，请验证！");
								} else {// 数值类型
									double d = cell.getNumericCellValue();
									str = String.format("%.2f", d);
								}
								break;
							default:
								resultInfo.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：非数字类型，请验证！");
								throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：非数字类型，请验证！");
							}
							switch (cellNum) {
							case 17:// 尺寸长度（cm）
								if (str != null) {
									goods.setGoodsLength(new java.math.BigDecimal(str));
								}
								break;
							case 18:// 尺寸宽度（cm）
								if (str != null) {
									goods.setGoodsWidth(new java.math.BigDecimal(str));
								}
								break;
							case 19:// 尺寸高度（cm）
								if (str != null) {
									goods.setGoodsHeight(new java.math.BigDecimal(str));
								}
								break;
							case 20:// 尺寸净重（kg）
								if (str != null) {
									goods.setNetWeight(new java.math.BigDecimal(str));
								}
								break;
							case 21:// 包装信息长度（cm）
								if (str != null) {
									goods.setPackageLength(new java.math.BigDecimal(str));
								}
								break;
							case 22:// 包装信息宽度（cm）
								if (str != null) {
									goods.setPackageWidth(new java.math.BigDecimal(str));
								}
								break;
							case 23:// 包装信息高度（cm）
								if (str != null) {
									goods.setPackageHeight(new java.math.BigDecimal(str));
								}
								break;
							case 24:// 包装信息毛重（kg）
								if (str != null) {
									goods.setGrossWeight(new java.math.BigDecimal(str));
								}
								break;
							default:
								resultInfo.setMessage(
										"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：获取参数值异常，请稍等重试或联系管理员！");
								throw new Exception(
										"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：获取参数值异常，请稍等重试或联系管理员！");
							}
						}

					}
					list.add(goods);
				}
				if (list != null && !list.isEmpty()) {
					// 验证产品名称是否重复
					List<String> str_list = goodsService.batchEditVailGoodsName(list);
					if (str_list != null && !str_list.isEmpty()) {
						for (int x = 0; x < list.size(); x++) {
							for (String str : str_list) {
								if (list.get(x).getGoodsName().equals(str)) {
									resultInfo.setMessage("第:" + (x + 2) + "行，第:1列：产品名称与已有产品名称重复，请验证！");
									throw new Exception("第:" + (x + 2) + "行，第:1列：产品名称与已有产品名称重复，请验证！");
								}
							}
						}
					}
					// 批量更新
					resultInfo = goodsService.batchUpdateGoodsSave(list);
				}
			}
		} catch (Exception e) {
			logger.error("batchUpdateGoodsSave:", e);
			return resultInfo;
		}
		return resultInfo;
	}

	/**
	 * <b>Description:</b><br>
	 * 上传产品批量核价Excel初始换
	 * 
	 * @param request
	 * @return
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2017年6月12日 下午4:55:26
	 */
	@ResponseBody
	@RequestMapping(value = "/batchPriceGoodsInit")
	public ModelAndView batchPriceGoodsInit(HttpServletRequest request) {
		return new ModelAndView("goods/goods/batch_price_goods");
	}

	/**
	 * <b>Description:</b><br>
	 * 上传核价，批量更新产品操作
	 * 
	 * @param request
	 * @param lwfile
	 * @return
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2017年6月12日 下午5:07:48
	 */
	@ResponseBody
	@RequestMapping(value = "/batchPriceGoodsSave")
	public ResultInfo<?> batchPriceGoodsSave(HttpServletRequest request, @RequestParam("lwfile") MultipartFile lwfile) {
		ResultInfo<?> result = new ResultInfo<>();
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		try {
			// 临时文件存放地址
			String path = request.getSession().getServletContext().getRealPath("/upload/goods");
			FileInfo fileInfo = ftpUtilService.fileUploadServe(path, lwfile);

			if (fileInfo.getCode() == 0) {// 上传成功
				/*
				 * //全部品牌 List<Brand> brandList = brandService.getAllBrand(new
				 * Brand()); if(brandList.isEmpty()){
				 * result.setMessage("未能查找到品牌信息，请验证！"); throw new
				 * Exception("未能查找到品牌信息，请验证！"); } //获取全部单位 List<Unit> unitList =
				 * unitService.getAllUnitList(new Unit());
				 * if(unitList.isEmpty()){ result.setMessage("未能查找到单位信息，请验证！");
				 * throw new Exception("未能查找到单位信息，请验证！"); }
				 */
				// 省市地址信息
				List<Region> regionList = regionService.getRegionByParentId(1);// 缓存中拿
				if (regionList.isEmpty()) {
					result.setMessage("未能查找到省市信息，请验证！");
					throw new Exception("未能查找到省市信息，请验证！");
				}
				List<GoodsChannelPrice> list = new ArrayList<GoodsChannelPrice>();// 保存Excel中读取的数据
				// 获取excel路径
				Workbook workbook = WorkbookFactory.create(new FileInputStream(new File(fileInfo.getFilePath())));
				// 获取第一面sheet
				Sheet sheet = workbook.getSheetAt(0);
				// 起始行
				int startRowNum = sheet.getFirstRowNum() + 1;// 第一行标题
				int endRowNum = sheet.getLastRowNum();// 结束行

				String str = null;
				List<Region> cityList = null;// 市地址列表
				for (int rowNum = startRowNum; rowNum <= endRowNum; rowNum++) {// 循环行数
					Row row = sheet.getRow(rowNum);
					// int startCellNum = row.getFirstCellNum();// 起始列（从有值得一列开始）
					int startCellNum = 0;// 默认从第一列开始（防止第一列为空）
					int endCellNum = row.getLastCellNum() - 1;// 结束列
					// 获取excel的值
					GoodsChannelPrice goodsPrice = new GoodsChannelPrice();
					if (user != null) {
						goodsPrice.setAddTime(DateUtil.gainNowDate());
						goodsPrice.setCreator(user.getUserId());

						goodsPrice.setUpdater(user.getUserId());
						goodsPrice.setModTime(DateUtil.gainNowDate());
					}
					for (int cellNum = startCellNum; cellNum <= endCellNum; cellNum++) {// 循环列数（下表从0开始）
						Cell cell = row.getCell(cellNum);
						try {
							if (cell == null) {// cell==null单元格空白（无内容，默认空）
								// 第一列，五列，六列，七列，十一列，十二列
								if (cellNum == 0 || cellNum == 5 || (cellNum >= 7 && cellNum <= 12)) {
									result.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：不允许为空，请验证！");
									throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：不允许为空，请验证！");
								}
								continue;
							}
							Integer type = cell.getCellType();
							if (cellNum == 0 || cellNum == 5 || cellNum == 6 || cellNum == 11 || cellNum >= 13) {// 第一列,第六列,第七列
																													// 和
																													// 第11列、第十四列
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
								// result.setMessage("第:" + (rowNum + 1) +
								// "行，第:" + (cellNum + 1) + "列非字符类型，请验证！");
								// throw new Exception("第:" + (rowNum + 1) +
								// "行，第:" + (cellNum + 1) + "列非字符类型，请验证！");
								}
								switch (cellNum) {
								case 0:// 订货号（唯一识别，不为空）
									if (str == null || "".equals(str)) {
										result.setMessage(
												"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空，请验证！");
										throw new Exception(
												"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空，请验证！");
									}
									goodsPrice.setSku(str);
									break;
								/*
								 * case 1://产品名称 goodsPrice.setGoodsName(str);
								 * break; case 2://品牌 if(str != null &&
								 * !"".equals(str)){ for(int
								 * i=0;i<brandList.size();i++){
								 * if(str.equals(brandList.get(i).getBrandName()
								 * )){ goodsPrice.setBrandId(brandList.get(i).
								 * getBrandId()); break; } }
								 * if(goodsPrice.getBrandId()==null){//
								 * 未能查询到对应的品牌ID result.setMessage("第:" + (rowNum
								 * + 1) + "行，第:" + (cellNum + 1) +
								 * "列：值错误，请验证！"); throw new Exception("第:" +
								 * (rowNum + 1) + "行，第:" + (cellNum + 1) +
								 * "列：值错误，请验证！"); } } case 3://型号
								 * goodsPrice.setModel(str); break; case 4://单位
								 * if(str != null && !"".equals(str)){ for(int
								 * i=0;i<unitList.size();i++){
								 * if(str.equals(unitList.get(i).getUnitName()))
								 * { goodsPrice.setUnitId(unitList.get(i).
								 * getUnitId()); break; } }
								 * if(goodsPrice.getUnitId()==null){//
								 * 未能查询到对应的单位ID result.setMessage("第:" + (rowNum
								 * + 1) + "行，第:" + (cellNum + 1) +
								 * "列：值错误，请验证！"); throw new Exception("第:" +
								 * (rowNum + 1) + "行，第:" + (cellNum + 1) +
								 * "列：值错误，请验证！"); } } break;
								 */
								case 5:// 省
									if (str != null && !"".equals(str)) {
										if (str.equals("中国")) {
											goodsPrice.setProvinceName(str);
											goodsPrice.setProvinceId(1);
										} else {
											for (int i = 0; i < regionList.size(); i++) {
												if (str.equals(regionList.get(i).getRegionName())) {
													goodsPrice.setProvinceName(regionList.get(i).getRegionName());
													goodsPrice.setProvinceId(regionList.get(i).getRegionId());
													break;
												}
											}
											if (goodsPrice.getProvinceName() == null) {// 未能查询到对应的省ID
												result.setMessage(
														"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
												throw new Exception(
														"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
											}
										}
									}
									break;
								case 6:// 市
									if (str != null && !"".equals(str)) {

										if (null != goodsPrice.getProvinceId()) {
											// 根据省Id到缓存中查询市列表
											cityList = regionService.getRegionByParentId(goodsPrice.getProvinceId());
											for (int i = 0; i < cityList.size(); i++) {
												if (str.equals(cityList.get(i).getRegionName())) {
													goodsPrice.setCityName(cityList.get(i).getRegionName());
													goodsPrice.setCityId(cityList.get(i).getRegionId());
													break;
												}
											}
										}
										if (goodsPrice.getCityName() == null) {// 未能查询到对应的单位ID
											result.setMessage(
													"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
											throw new Exception(
													"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
										}
									}
									break;
								case 13:// 需报备终端（是/否）
									if (StringUtils.isNotBlank(str)) {
										if ("是".equals(str.trim())) {
											goodsPrice.setIsReportTerminal(1);
										} else if ("否".equals(str.trim())) {
											goodsPrice.setIsReportTerminal(0);
										}
										if (goodsPrice.getIsReportTerminal() == null) {
											result.setMessage(
													"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
											throw new Exception(
													"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
										}
									}
									break;
								case 14:// 是否可出厂家授权（是，需保证金/是/否）
									if (StringUtils.isNotBlank(str)) {
										if ("否".equals(str.trim())) {
											goodsPrice.setIsManufacturerAuthorization(0);
										} else if ("是".equals(str.trim())) {
											goodsPrice.setIsManufacturerAuthorization(1);
										} else if ("是，需保证金".equals(str.trim())) {
											goodsPrice.setIsManufacturerAuthorization(2);
										}
										if (goodsPrice.getIsManufacturerAuthorization() == null) {
											result.setMessage(
													"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
											throw new Exception(
													"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
										}
									}
									break;
								case 11:// 参考货期（天）
									if (str != null) {
										if (str.length() > 64) {
											result.setMessage(
													"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值过大，请验证！");
											throw new Exception(
													"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值过大，请验证！");
										}
										goodsPrice.setDeliveryCycle(str);
									}
									break;
								default:
									result.setMessage(
											"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：获取参数值异常，请稍等重试或联系管理员！");
									throw new Exception(
											"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：获取参数值异常，请稍等重试或联系管理员！");
								}
							} else if (cellNum >= 7 && cellNum <= 10) {
								switch (type) {
								case Cell.CELL_TYPE_BLANK:// 空白单元格
									str = null;
									break;
								case Cell.CELL_TYPE_NUMERIC:// 数值、日期类型
									if (HSSFDateUtil.isCellDateFormatted(cell)) {// 日期类型
										result.setMessage(
												"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：非数字类型，请验证！");
										throw new Exception(
												"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：非数字类型，请验证！");
									} else {// 数值类型
										double d = cell.getNumericCellValue();
										str = String.format("%.2f", d);
									}
									break;
								default:
									result.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：非日期类型，请验证！");
									throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：非日期类型，请验证！");
								}
								switch (cellNum) {
								case 7:// 成本价
									if (str != null) {
										goodsPrice.setCostPrice(new java.math.BigDecimal(str));
									}
									break;
								case 8:// 公开报价
									if (str != null) {
										goodsPrice.setPublicPrice(new java.math.BigDecimal(str));
									}
									break;
								case 9:// 经销价
									if (str != null) {
										goodsPrice.setDistributionPrice(new java.math.BigDecimal(str));
									}
									break;
								case 10:// 私立终端价
									if (str != null) {
										goodsPrice.setPrivatePrice(new java.math.BigDecimal(str));
									}
									break;
								default:
									result.setMessage(
											"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：获取参数值异常，请稍等重试或联系管理员！");
									throw new Exception(
											"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：获取参数值异常，请稍等重试或联系管理员！");
								}
							} else if (cellNum == 12) {// 日期格式
								switch (type) {
								case Cell.CELL_TYPE_BLANK:// 空白单元格
									str = null;
									break;
								case Cell.CELL_TYPE_NUMERIC:// 数值、日期类型
									if (HSSFDateUtil.isCellDateFormatted(cell)) {// 日期类型
										double d = cell.getNumericCellValue();
										Date date = HSSFDateUtil.getJavaDate(d);
										str = DateUtil.DateToString(date, "yyyy-MM-dd");
									} else {// 数值类型
										result.setMessage(
												"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：非日期类型，请验证！");
										throw new Exception(
												"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：非日期类型，请验证！");
									}
									break;
								default:
									result.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：非日期类型，请验证！");
									throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：非日期类型，请验证！");
								}
								switch (cellNum) {
								case 12:// 核价有效期（X年X月X日）
									if (str != null) {
										goodsPrice.setPeriodDate(DateUtil.StringToDate(str));
									}
									break;
								default:
									result.setMessage(
											"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：获取参数值异常，请稍等重试或联系管理员！");
									throw new Exception(
											"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：获取参数值异常，请稍等重试或联系管理员！");
								}
							}
						} catch (Exception e) {
							logger.error("batchPriceGoodsSave 1:", e);
							return result;
						}
					}
					list.add(goodsPrice);
				}

				List<String> sku_list = new ArrayList<>();// sku列表
				// 验证sku不允许重复
				if (list != null && !list.isEmpty()) {
					for (int i = 0; i < list.size(); i++) {
						int num = 0;
						for (int j = 0; j < list.size(); j++) {
							if (list.get(i).getSku().equals(list.get(j).getSku())
									&& list.get(i).getProvinceId().equals(list.get(j).getProvinceId())) {
								if (num > 0) {
									result.setMessage("第:" + (i + 1) + "行，第:" + (j + 1) + "行：SKU名称与省组合重复，请验证！");
									throw new Exception("第:" + (i + 1) + "行，第:" + (j + 1) + "行：SKU名称与省组合重复，请验证！");
								}
								num++;
							}
						}
						sku_list.add(list.get(i).getSku());
					}
					if (sku_list != null) {
						// 验证sku是否存在产品表
						List<Goods> goods_list = goodsService.batchVailGoodsSku(sku_list);
						if (goods_list != null && !goods_list.isEmpty()) {
							int num = 0;
							for (int x = 0; x < list.size(); x++) {
								for (int y = 0; y < goods_list.size(); y++) {
									if (list.get(x).getSku().equals(goods_list.get(y).getSku())) {
										list.get(x).setGoodsId(goods_list.get(y).getGoodsId());
										num++;
									}
								}
								if (num == 0) {
									// sku在第一列
									result.setMessage("第:" + (x + 2) + "行，第:1列：产品SKU不存在，请验证！");
									throw new Exception("第:" + (x + 2) + "行，第:1列：产品SKU不存在，请验证！");
								}
								num = 0;
							}
						}
					}
					// 批量更新
					result = goodsService.batchGoodsPriceSave(list);
				}
			}
		} catch (Exception e) {
			logger.error("batchPriceGoodsSave 2:", e);
		}
		return result;
	}

	/**
	 * <b>Description:</b><br>
	 * 上传产品批量核价Excel初始换
	 * 
	 * @param request
	 * @return
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2017年6月12日 下午4:55:26
	 */
	@ResponseBody
	@RequestMapping(value = "/batchGoodsSettelmentInit")
	public ModelAndView batchGoodsSettelmentInit(HttpServletRequest request) {
		return new ModelAndView("goods/goods/batch_goods_settlement");
	}

	/**
	 * <b>Description:</b><br>
	 * 产品批量修改结算核价
	 * 
	 * @param request
	 * @param lwfile
	 * @return
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2017年12月7日 上午11:25:41
	 */
	@ResponseBody
	@RequestMapping(value = "/batchGoodsSettelmentSave")
	public ResultInfo<?> batchGoodsSettelmentSave(HttpServletRequest request,
			@RequestParam("lwfile") MultipartFile lwfile) {
		ResultInfo<?> result = new ResultInfo<>();
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		try {
			// 临时文件存放地址
			String path = request.getSession().getServletContext().getRealPath("/upload/goods");
			FileInfo fileInfo = ftpUtilService.fileUploadServe(path, lwfile);

			if (fileInfo.getCode() == 0) {// 上传成功

				List<GoodsSettlementPrice> list = new ArrayList<GoodsSettlementPrice>();// 保存Excel中读取的数据
				// 获取excel路径
				Workbook workbook = WorkbookFactory.create(new FileInputStream(new File(fileInfo.getFilePath())));
				// 获取第一面sheet
				Sheet sheet = workbook.getSheetAt(0);
				// 起始行
				int startRowNum = sheet.getFirstRowNum() + 1;// 第一行标题
				int endRowNum = sheet.getLastRowNum();// 结束行

				String str = null;
				for (int rowNum = startRowNum; rowNum <= endRowNum; rowNum++) {// 循环行数
					Row row = sheet.getRow(rowNum);
					// int startCellNum = row.getFirstCellNum();// 起始列（从有值得一列开始）
					int startCellNum = 0;// 默认从第一列开始（防止第一列为空）
					int endCellNum = row.getLastCellNum() - 1;// 结束列
					// 获取excel的值
					GoodsSettlementPrice gsp = new GoodsSettlementPrice();
					if (user != null) {
						gsp.setAddTime(DateUtil.gainNowDate());
						gsp.setCreator(user.getUserId());

						gsp.setCompanyId(user.getCompanyId());

						gsp.setUpdater(user.getUserId());
						gsp.setModTime(DateUtil.gainNowDate());
					}
					for (int cellNum = startCellNum; cellNum <= endCellNum; cellNum++) {// 循环列数（下表从0开始）
						Cell cell = row.getCell(cellNum);
						try {
							if (cell == null) {// cell==null单元格空白（无内容，默认空）
								// 第一列，四列
								if (cellNum == 0 || cellNum == 4) {
									result.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：不允许为空，请验证！");
									throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：不允许为空，请验证！");
								}
								continue;
							}
							Integer type = cell.getCellType();
							if (cellNum == 0) {// 第一列读取，其他数据作为参考
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
								// result.setMessage("第:" + (rowNum + 1) +
								// "行，第:" + (cellNum + 1) + "列非字符类型，请验证！");
								// throw new Exception("第:" + (rowNum + 1) +
								// "行，第:" + (cellNum + 1) + "列非字符类型，请验证！");
								}
								switch (cellNum) {
								case 0:// 订货号（唯一识别，不为空）
									if (str == null || "".equals(str)) {
										result.setMessage(
												"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空，请验证！");
										throw new Exception(
												"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空，请验证！");
									}
									gsp.setSku(str);
									break;
								default:
									result.setMessage(
											"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：获取参数值异常，请稍等重试或联系管理员！");
									throw new Exception(
											"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：获取参数值异常，请稍等重试或联系管理员！");
								}
							} else if (cellNum == 4) {// 第五列读取，其他数据作为参考
								switch (type) {
								case Cell.CELL_TYPE_BLANK:// 空白单元格
									str = null;
									break;
								case Cell.CELL_TYPE_NUMERIC:// 数值、日期类型
									if (HSSFDateUtil.isCellDateFormatted(cell)) {// 日期类型
										result.setMessage(
												"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：非数字类型，请验证！");
										throw new Exception(
												"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：非数字类型，请验证！");
									} else {// 数值类型
										double d = cell.getNumericCellValue();
										str = String.format("%.2f", d);
									}
									break;
								default:
									result.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：非日期类型，请验证！");
									throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：非日期类型，请验证！");
								}
								switch (cellNum) {
								case 4:// 结算价
									if (str != null) {
										gsp.setSettlementPrice(new java.math.BigDecimal(str));
									}
									break;
								default:
									result.setMessage(
											"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：获取参数值异常，请稍等重试或联系管理员！");
									throw new Exception(
											"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：获取参数值异常，请稍等重试或联系管理员！");
								}
							}
						} catch (Exception e) {
							logger.error("batchGoodsSettelmentSave 1:", e);
							return result;
						}
					}
					list.add(gsp);
				}
				List<String> sku_list = new ArrayList<>();// sku列表
				// 验证sku不允许重复
				if (list != null && !list.isEmpty()) {
					for (int i = 0; i < list.size(); i++) {
						if (list.get(i).getSettlementPrice() == null) {
							result.setMessage("第:" + (i + 1) + "行，第:5列：结算价不允许为空，请验证！");
							throw new Exception("第:" + (i + 1) + "行，第:5列：结算价不允许为空，请验证！");
						} else if (list.get(i).getSettlementPrice().doubleValue() > Double.valueOf(9999999)) {
							result.setMessage("第:" + (i + 1) + "行，第:5列：结算价超出最大限制，请验证！");
							throw new Exception("第:" + (i + 1) + "行，第:5列：结算价超出最大限制，请验证！");
						}
						int num = 0;
						for (int j = 0; j < list.size(); j++) {
							if (list.get(i).getSku().equals(list.get(j).getSku())) {
								if (num > 0) {
									result.setMessage("第:" + (i + 1) + "行，第:" + (j + 1) + "行：SKU名称重复，请验证！");
									throw new Exception("第:" + (i + 1) + "行，第:" + (j + 1) + "行：SKU名称重复，请验证！");
								}
								num++;
							}
						}
						sku_list.add(list.get(i).getSku());
					}
					if (sku_list != null) {
						// 验证sku是否存在产品表
						List<Goods> goods_list = goodsService.batchVailGoodsSku(sku_list);
						if (goods_list != null && !goods_list.isEmpty()) {
							int num = 0;
							for (int x = 0; x < list.size(); x++) {
								for (int y = 0; y < goods_list.size(); y++) {
									if (list.get(x).getSku().equals(goods_list.get(y).getSku())) {
										list.get(x).setGoodsId(goods_list.get(y).getGoodsId());
										num++;
									}
								}
								if (num == 0) {
									// sku在第一列
									result.setMessage("第:" + (x + 2) + "行，第:1列：产品SKU不存在，请验证！");
									throw new Exception("第:" + (x + 2) + "行，第:1列：产品SKU不存在，请验证！");
								}
								num = 0;
							}
						}
					}
					// 批量更新
					result = goodsService.batchGoodsSettelmentSave(list);
				}
			}
		} catch (Exception e) {
			logger.error("batchGoodsSettelmentSave 2:", e);
		}
		return result;
	}

	/**
	 * <b>Description:</b><br>
	 * 产品相关附件上传
	 * 
	 * @param request
	 * @param response
	 * @param lwfile
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年7月19日 上午10:47:52
	 */
	@ResponseBody
	@RequestMapping(value = "/goodsAttachmentUpload")
	public FileInfo goodsImgUpload(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("lwfile") MultipartFile lwfile) {
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		if (user != null) {
			String path = "/upload/goods";
			long size = lwfile.getSize();
			if (size > 5 * 1024 * 1024) {
				return new FileInfo(-1, "图片大小应为5MB以内");
			}
			/*
			 * System.out.println(lwfile.getOriginalFilename()); String fileName
			 * = lwfile.getOriginalFilename(); String
			 * prefix=fileName.substring(fileName.lastIndexOf(".")+1);
			 * if(!prefix.equals("jpg")){ return new FileInfo(-1,"请选择jpg格式图片");
			 * }
			 */
			/*
			 * if(!FileType.getFileHeader(lwfile.getOriginalFilename()).equals(
			 * "jpg")){ return new FileInfo(-1,"请选择jpg格式图片"); }
			 */
			return ftpUtilService.uploadFile(lwfile, path, request, "");
			/*
			 * Attachment ach = new Attachment();
			 * ach.setAttachmentType(SysOptionConstant.ID_343);
			 * ach.setAttachmentFunction(SysOptionConstant.ID_494);
			 * ach.setName(info.getFileName());
			 * ach.setDomain(info.getHttpUrl()); ach.setUri(info.getFilePath());
			 */
		} else {
			return new FileInfo(-1, "登录用户不能为空");
		}
	}

	/**
	 * <b>Description:</b><br>
	 * 批量操作产品
	 * 
	 * @param request
	 * @param goods
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年10月9日 下午4:52:43
	 */
	@ResponseBody
	@RequestMapping(value = "/batchOptGoods")
	public ResultInfo<?> batchOptGoods(HttpServletRequest request, Goods goods) {
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		if (user != null) {
			goods.setUpdater(user.getUserId());
			goods.setModTime(DateUtil.sysTimeMillis());
		}

		goods.setGoodsIdArr(JSON.parseArray(request.getParameter("goodsIdArr").toString(), String.class));

		ResultInfo<?> result = goodsService.batchOptGoods(goods);
		return result;
	}

	/**
	 * <b>Description:</b><br>
	 * 产品管理类别批量分配列表
	 * 
	 * @param request
	 * @param goods
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年10月30日 上午10:32:50
	 */
	@ResponseBody
	@RequestMapping(value = "/assignlist")
	public ModelAndView assignList(HttpServletRequest request, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		List<SysOptionDefinition> optionDefinitions = getSysOptionDefinitionList(SysOptionConstant.ID_20);
		List<SysOptionDefinition> list = goodsService.getAssignList(optionDefinitions, session);
		mv.addObject("list", list);
		mv.setViewName("goods/goods/assign_list");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 编辑产品分类归属
	 * 
	 * @param request
	 * @param manageCategories
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年10月30日 下午2:16:12
	 */
	@ResponseBody
	@RequestMapping(value = "/editcategoryowner")
	public ModelAndView editCategoryOwner(HttpServletRequest request, HttpSession session,
			@RequestParam("manageCategories") String manageCategories) {
		if (null == manageCategories || manageCategories.equals("")) {
			return pageNotFound();
		}
		ModelAndView mv = new ModelAndView();
		User user = (User) session.getAttribute(ErpConst.CURR_USER);

		String[] manageCategoryList = manageCategories.split(",");

		if (manageCategoryList.length == 1) {
			Integer manageCategory = Integer.parseInt(manageCategoryList[0]);
			List<User> users = goodsService.getUserByManageCategory(manageCategory, user.getCompanyId());
			mv.addObject("users", users);
		}

		// 采购人员
		List<Integer> positionType = new ArrayList<>();
		positionType.add(SysOptionConstant.ID_311);// 采购
		List<User> productUserList = userService.getMyUserList(user, positionType, false);

		mv.addObject("productUserList", productUserList);
		mv.addObject("manageCategories", manageCategories);

		mv.setViewName("goods/goods/edit_assign");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 保存产品分类归属
	 * 
	 * @param request
	 * @param session
	 * @param manageCategories
	 * @param userId
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年10月30日 下午3:30:55
	 */
	@ResponseBody
	@RequestMapping(value = "/saveeditcategoryowner")
	public ResultInfo<?> saveEditCategoryOwner(HttpServletRequest request, HttpSession session,
			@RequestParam("manageCategories") String manageCategories, @RequestParam("userId") List<Integer> userId) {
		ResultInfo<?> resultInfo = new ResultInfo<>();
		Boolean res = goodsService.saveEditCategoryOwner(userId, manageCategories, session);
		if (res) {
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
		}
		return resultInfo;
	}

	/**
	 * <b>Description:</b><br>
	 * 产品申请审核
	 * 
	 * @param request
	 * @return
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年10月24日 下午18:42:13
	 */
	@FormToken(remove = true)
	@ResponseBody
	@RequestMapping(value = "/editApplyValidGoods")
	public ResultInfo<?> editApplyValidGoods(HttpServletRequest request, Goods goods, String taskId,
			HttpSession session) {
		try {
			Map<String, Object> variableMap = new HashMap<String, Object>();
			User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);

			// 查询产品状态
			goods = goodsService.getGoodsById(goods);
			// 开始生成流程(如果没有taskId表示新流程需要生成)
			if (taskId.equals("0")) {
				variableMap.put("goods", goods);
				variableMap.put("currentAssinee", user.getUsername());
				variableMap.put("processDefinitionKey", "goodsVerify");
				variableMap.put("businessKey", "goodsVerify_" + goods.getGoodsId());
				variableMap.put("relateTableKey", goods.getGoodsId());
				variableMap.put("relateTable", "T_GOODS");
				actionProcdefService.createProcessInstance(request, "goodsVerify", "goodsVerify_" + goods.getGoodsId(),
						variableMap);
			}
			// 默认申请人通过
			// 根据BusinessKey获取生成的审核实例
			Map<String, Object> historicInfo = actionProcdefService.getHistoric(processEngine,
					"goodsVerify_" + goods.getGoodsId());
			if (historicInfo.get("endStatus") != "审核完成") {
				Task taskInfo = (Task) historicInfo.get("taskInfo");
				taskId = taskInfo.getId();
				Authentication.setAuthenticatedUserId(user.getUsername());
				Map<String, Object> variables = new HashMap<String, Object>();
				// 默认审批通过
				ResultInfo<?> complementStatus = actionProcdefService.complementTask(request, taskId, "",
						user.getUsername(), variables);

				// 如果未结束添加审核对应主表的审核状态
				if (!complementStatus.getData().equals("endEvent")) {
					verifiesRecordService.saveVerifiesInfo(taskId, 0);
				}
			}
			return new ResultInfo(0, "操作成功");
		} catch (Exception e) {
			logger.error("editApplyValidGoods:", e);
			return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
		}

	}

	/**
	 * 
	 * <b>Description:</b><br>
	 * 产品审核弹曾页面
	 * 
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年11月10日 下午1:39:42
	 */
	@FormToken(save = true)
	@ResponseBody
	@RequestMapping(value = "/complement")
	public ModelAndView complement(HttpSession session, Integer goodsId, String taskId, Boolean pass) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("taskId", taskId);
		mv.addObject("pass", pass);
		mv.addObject("goodsId", goodsId);
		mv.setViewName("goods/goods/complement");
		return mv;
	}

	/**
	 * 
	 * <b>Description:</b><br>
	 * 产品审核操作
	 * 
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年11月10日 下午1:39:42
	 */
	@FormToken(remove = true)
	@ResponseBody
	@RequestMapping(value = "/complementTask")
	public ResultInfo<?> complementTask(HttpServletRequest request, Integer goodsId, String taskId, String comment,
			Boolean pass, HttpSession session) {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("pass", pass);
		variables.put("updater", user.getUserId());
		try {
			Integer status = 0;
			if (pass) {
				// 如果审核通过
				status = 0;
			} else {
				// 如果审核不通过
				status = 2;
				verifiesRecordService.saveVerifiesInfo(taskId, status);
			}

			ResultInfo<?> complementStatus = actionProcdefService.complementTask(request, taskId, comment,
					user.getUsername(), variables);
			// 如果未结束添加审核对应主表的审核状态
			if (!complementStatus.getData().equals("endEvent")) {
				verifiesRecordService.saveVerifiesInfo(taskId, status);
			}
			return new ResultInfo(0, "操作成功");
		} catch (Exception e) {
			logger.error("goods complementTask:", e);
			return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
		}

	}

	/**
	 * <b>Description:</b><br>
	 * 搜索供应商信息页面
	 * 
	 * @param request
	 * @param traderSupplierVo
	 * @return
	 * @throws UnsupportedEncodingException
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年7月25日 下午5:10:20
	 */
	@ResponseBody
	@RequestMapping(value = "/getSupplierByName")
	public ModelAndView getSupplierByName(HttpServletRequest request, TraderSupplierVo traderSupplierVo,
			String supplierName, Integer goodsId, @RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false, defaultValue = "10") Integer pageSize) throws UnsupportedEncodingException {
		ModelAndView mav = new ModelAndView("goods/goods/search_supplier");
		try {
			if (supplierName != "") {
				supplierName = URLDecoder.decode(URLDecoder.decode(supplierName, "UTF-8"), "UTF-8");
				mav.addObject("supplierName", supplierName);
				User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
				Page page = getPageTag(request, pageNo, 10);
				traderSupplierVo.setCompanyId(user.getCompanyId());
				traderSupplierVo.setTraderSupplierName(supplierName);
				traderSupplierVo.setIsEnable(ErpConst.ONE);
				// 查询所有职位类型为311的员工
				List<Integer> positionType = new ArrayList<>();
				positionType.add(SysOptionConstant.ID_311);// 采购
				List<User> userList = userService.getMyUserList(user, positionType, false);
				Map<String, Object> map = this.traderSupplierService.getSupplierByName(traderSupplierVo, page,
						userList);
				List<TraderSupplierVo> list = null;
				if (map != null) {
					list = (List<TraderSupplierVo>) map.get("list");
					page = (Page) map.get("page");
				}
				mav.addObject("list", list);
				mav.addObject("page", page);
			}
		} catch (Exception e) {
			logger.error("getSupplierByName", e);
		}
		mav.addObject("goodsId", goodsId);
		return mav;
	}

	/**
	 * <b>Description:</b><br>
	 * 保存添加主要供应商
	 * 
	 * @param request
	 * @param goodsRecommend
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年12月22日 下午5:33:07
	 */
	@ResponseBody
	@RequestMapping(value = "/saveMainSupply")
	@SystemControllerLog(operationType = "add", desc = "保存添加主要供应商")
	public ResultInfo<?> saveMainSupply(HttpServletRequest request, RGoodsJTraderSupplier rGoodsJTraderSupplier) {
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);

		ResultInfo<?> result = goodsService.saveMainSupply(rGoodsJTraderSupplier);
		return result;
	}

	/**
	 * <b>Description:</b><br>
	 * 删除主供应商
	 * 
	 * @param request
	 * @param rGoodsJTraderSupplier
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年12月23日 上午11:04:37
	 */
	@ResponseBody
	@RequestMapping(value = "/delMainSupply")
	@SystemControllerLog(operationType = "delete", desc = "删除主供应商")
	public ResultInfo<?> delMainSupply(HttpServletRequest request, RGoodsJTraderSupplier rGoodsJTraderSupplier) {
		return goodsService.delMainSupply(rGoodsJTraderSupplier);
	}

	/**
	 * <b>Description:</b><br>
	 * 重置产品核价信息
	 * 
	 * @param request
	 * @param goodsChannelPrice
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年12月23日 上午11:07:48
	 */
	@ResponseBody
	@RequestMapping(value = "/delGoodsChannelPrice")
	public ResultInfo<?> delGoodsChannelPrice(HttpServletRequest request, GoodsChannelPrice goodsChannelPrice,
			@RequestParam(defaultValue = "") String goodsChannelPriceIdList) {
		if (null != goodsChannelPrice && null != goodsChannelPriceIdList) {
			String[] goodsChannelPriceIds = goodsChannelPriceIdList.split("\\|");
			List<GoodsChannelPriceExtend> goodsChannelPriceExtendList = new LinkedList<GoodsChannelPriceExtend>();
			for (String gcpId : goodsChannelPriceIds) {
				if (null == gcpId || gcpId.trim().length() == 0)
					continue;
				try {
					Integer gcpeId = Integer.parseInt(gcpId.trim());
					GoodsChannelPriceExtend gcpe = new GoodsChannelPriceExtend();
					gcpe.setGoodsChannelPriceId(gcpeId);
					goodsChannelPriceExtendList.add(gcpe);
				} catch (NumberFormatException e) {
					logger.error(Contant.ERROR_MSG, e);
				}
			}
			goodsChannelPrice.setGoodsChannelPriceExtendList(goodsChannelPriceExtendList);
		}
		return goodsChannelPriceService.delGoodsChannelPrice(goodsChannelPrice);
	}

	/**
	 * <b>Description:</b><br>
	 * 产品重置为待审核
	 * 
	 * @param request
	 * @param session
	 * @param goods
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2018年1月23日 下午3:54:53
	 */
	@ResponseBody
	@RequestMapping(value = "/restverify")
	@SystemControllerLog(operationType = "edit", desc = "产品重置为待审核")
	public ResultInfo restVerify(HttpServletRequest request, HttpSession session, Goods goods) {
		if (null == goods || goods.getGoodsId() == null) {
			return new ResultInfo<>();
		}

		ResultInfo resultInfo = goodsService.restVerify(goods);
		return resultInfo;
	}

	/**
	 * 
	 * <b>Description:</b><br>
	 * 初始化编辑商品宣传工具页面
	 * 
	 * @param request
	 * @param goods
	 * @return
	 * @throws IOException
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2018年3月13日 下午7:04:56
	 */
	@ResponseBody
	@RequestMapping(value = "/editCommodityPropagandaTools")
	public ModelAndView editCommodityPropagandaTools(HttpServletRequest request, Goods goods) throws IOException {
		ModelAndView mv = new ModelAndView();
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		if (user != null) {
			goods.setCompanyId(user.getCompanyId());
		}
		// 获取产品的扩展信息
		GoodsExtend goodsExtend = goodsService.getGoodsExtend(goods);
		mv.addObject("goodsExtend", goodsExtend);
		if (goodsExtend != null) {
			// 宣传视频字符串
			String propagandistVideos = "";
			for (Attachment attachment : goodsExtend.getPropagandistVideoList()) {
				propagandistVideos += attachment.getUri() + ",";
			}
			goodsExtend.setPropagandistVideos(propagandistVideos);
		}
		goods = goodsService.getGoodsById(goods);
		mv.addObject("goods", goods);
		mv.addObject("domain", domain);
		mv.setViewName("goods/goods/edit_commodity_propaganda_tools");
		return mv;
	}

	/**
	 * 
	 * <b>Description:</b><br>
	 * 保存编辑商品工具
	 * 
	 * @param request
	 * @param goods
	 * @param goodsExtend
	 * @param beforeParams
	 * @param fileName1
	 * @param fileUri1
	 * @param fileName2
	 * @param fileUri2
	 * @param fileName3
	 * @param fileUri3
	 * @return
	 * @throws CloneNotSupportedException
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2018年3月14日 下午5:03:51
	 */
	@ResponseBody
	@RequestMapping(value = "saveCommodityPropaganda")
	@SystemControllerLog(operationType = "edit", desc = "保存编辑商品工具")
	public ModelAndView saveCommodityPropaganda(HttpServletRequest request, Goods goods, GoodsExtend goodsExtend,
			String beforeParams, @RequestParam(required = false, value = "fileName11") String[] fileName1,
			@RequestParam(required = false, value = "fileUri11") String[] fileUri1,
			@RequestParam(required = false, value = "fileName21") String[] fileName2,
			@RequestParam(required = false, value = "fileUri21") String[] fileUri2,
			@RequestParam(required = false, value = "fileName31") String[] fileName3,
			@RequestParam(required = false, value = "fileUri31") String[] fileUri3,
			@RequestParam(required = false, value = "fileName41") String[] fileName4,
			@RequestParam(required = false, value = "fileUri41") String[] fileUri4,
			@RequestParam(required = false, value = "fileName51") String[] fileName5,
			@RequestParam(required = false, value = "fileUri51") String[] fileUri5,
			@RequestParam(required = false, value = "fileName61") String[] fileName6,
			@RequestParam(required = false, value = "fileUri61") String[] fileUri6,
			@RequestParam(required = false, value = "fileName71") String[] fileName7,
			@RequestParam(required = false, value = "fileUri71") String[] fileUri7,
			@RequestParam(required = false, value = "fileName81") String[] fileName8,
			@RequestParam(required = false, value = "fileUri81") String[] fileUri8,
			@RequestParam(required = false, value = "fileName9") String[] fileName9,
			@RequestParam(required = false, value = "fileUri9") String[] fileUri9) throws CloneNotSupportedException {

		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);

		goodsExtend.setAttachName1(fileName1);
		goodsExtend.setAttachName2(fileName2);
		goodsExtend.setAttachName3(fileName3);
		goodsExtend.setAttachName4(fileName4);
		goodsExtend.setAttachName5(fileName5);
		goodsExtend.setAttachName6(fileName6);
		goodsExtend.setAttachName7(fileName7);
		goodsExtend.setAttachName8(fileName8);
		goodsExtend.setAttachUri1(fileUri1);
		goodsExtend.setAttachUri2(fileUri2);
		goodsExtend.setAttachUri3(fileUri3);
		goodsExtend.setAttachUri4(fileName4);
		goodsExtend.setAttachUri5(fileUri5);
		goodsExtend.setAttachUri6(fileUri6);
		goodsExtend.setAttachUri7(fileUri7);
		goodsExtend.setAttachUri8(fileUri8);
		goodsExtend.setAttachName9(fileName9);
		goodsExtend.setAttachUri9(fileUri9);
		goodsExtend.setDomain(domain);

		if (goodsExtend.getHaveStandbyMachine() != null && goodsExtend.getHaveStandbyMachine() == 0) {
			goodsExtend.setConditions("");
		}

		goods.setUpdater(user.getUserId());
		goods.setModTime(DateUtil.sysTimeMillis());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goods", goods);
		map.put("goodsExtend", goodsExtend);

		ResultInfo res = goodsService.saveCommodityPropaganda(map);
		ModelAndView mav = new ModelAndView();
		if (res != null && res.getCode() == 0) {
			mav.addObject("url", "./viewsaleinfo.do?goodsId=" + goods.getGoodsId());
			return success(mav);
		} else {
			return fail(new ModelAndView());
		}
	}

	/**
	 * 
	 * <b>Description:</b><br>
	 * 初始化编辑售后商品页面
	 * 
	 * @param request
	 * @param goods
	 * @return
	 * @throws IOException
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2018年3月13日 下午7:04:56
	 */
	@ResponseBody
	@RequestMapping(value = "/editAftersaleGoods")
	public ModelAndView editAftersaleGoods(HttpServletRequest request, Goods goods) throws IOException {
		ModelAndView mv = new ModelAndView();
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		if (user != null) {
			goods.setCompanyId(user.getCompanyId());
		}
		// 获取产品的扩展信息
		GoodsExtend goodsExtend = goodsService.getGoodsExtend(goods);
		mv.addObject("goodsExtend", goodsExtend);

		goods = goodsService.getGoodsById(goods);
		mv.addObject("goods", goods);
		mv.addObject("domain", domain);
		mv.setViewName("goods/goods/edit_aftersale_goods");
		return mv;
	}

	/**
	 * 
	 * <b>Description:</b><br>
	 * 展示成本价/批量价
	 * 
	 * @param goods
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2018年3月16日 下午5:07:28
	 */
	@ResponseBody
	@RequestMapping(value = "/showPriceList")
	public ResultInfo showPriceList(GoodsChannelPriceExtend GoodsChannelPriceExtend) {
		ResultInfo results = goodsChannelPriceService.showPriceList(GoodsChannelPriceExtend);
		return results;

	}

	/**
	 * 
	 * <b>Description: 根据parentId获取商品运营的子类分类信息</b><br>
	 * 
	 * @param parentId
	 * @param companyId
	 * @return <b>Author: Franlin</b> <br>
	 *         <b>Date: 2018年5月8日 上午11:31:58 </b>
	 */
	public List<Category> getSubCategorysByParentId(Integer parentId, Integer companyId) {
		if (null == parentId) {
			return null;
		}
		// 根据PARENT_ID查询子类信息
		Category cg = new Category();
		cg.setParentId(parentId);
		cg.setCompanyId(companyId);

		return categoryService.getCategoryList(cg);
	}

	/**
	 * 
	 * <b>Description: 根据parentId获取新国标的子类信息</b><br>
	 * 
	 * @param parentId
	 * @return <b>Author: Franlin</b> <br>
	 *         <b>Date: 2018年5月8日 上午10:54:29 </b>
	 */
	public List<StandardCategory> getSubStandardCategorysByParentId(Integer parentId) {
		if (null == parentId) {
			return null;
		}
		// 根据PARENT_ID查询子类信息
		StandardCategory sc = new StandardCategory();
		sc.setParentId(parentId);
		return categoryService.getParentStandardCateList(sc);
	}

	/**
	 * 
	 * <b>Description: 获取值</b><br>
	 * 
	 * @param cell
	 * @return <b>Author: Franlin</b> <br>
	 *         <b>Date: 2018年5月8日 下午7:02:12 </b>
	 */
	public String getValue(Cell cell, int cellNum) {
		if (null == cell)
			return null;
		Integer type = cell.getCellType();
		// 时间格式
		if ((cellNum == 29 || cellNum == 30) && Cell.CELL_TYPE_NUMERIC == type) {
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				try {
					double d = cell.getNumericCellValue();
					Date date = HSSFDateUtil.getJavaDate(d);
					return date.getTime() + "";
				} catch (Exception e) {
					logger.error(Contant.ERROR_MSG, e);
				}
			}
		}
		if (type != Cell.CELL_TYPE_STRING) {
			cell.setCellType(Cell.CELL_TYPE_STRING);
		}
		String value = cell.getStringCellValue().trim();

		return value;
	}

	/**
	 * 
	 * <b>Description: 获取时间long</b><br>
	 * 
	 * @param value
	 * @param errorMsg
	 * @return <b>Author: Franlin</b> <br>
	 *         <b>Date: 2018年5月8日 下午5:42:25 </b>
	 */
	public Long getDateLong(String value, String errorMsg) throws ShowErrorMsgException {
		Long lon = null;
		try {
			lon = Long.parseLong(value);
		} catch (Exception e) {
			System.out.println("=============== value = " + value);
		}
		if (null != lon) {
			return lon;
		}
		Date date = VerifyAndShowErrorMsgUtil.verifyDateByString(value, "yyyy年MM月dd日", errorMsg + "值为" + value + "错误。");

		return null == date ? null : date.getTime();
	}

	/**
	 * 
	 * <b>Description:</b><br>
	 * 模糊查询产品库中产品名称相近的产品
	 * 
	 * @param request
	 * @param session
	 * @param goods
	 * @return
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2018年5月10日 上午10:52:05
	 */
	@RequestMapping(value = "/checkProductLibrary")
	public ModelAndView checkProductLibrary(HttpServletRequest request,
			@RequestParam(value = "goodsName", required = false) String goodsName, HttpSession session,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false, defaultValue = "10") Integer pageSize) {
		ModelAndView mv = new ModelAndView();
		if (StringUtils.isNoneBlank(goodsName)) {
			User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
			Page page = getPageTag(request, pageNo, pageSize);
			Goods goods = new Goods();
			goods.setCompanyId(user.getCompanyId());
			goods.setGoodsName(goodsName);
			Map<String, Object> map = goodsService.queryGoodsListPage(goods, page, session);
			mv.addObject("goodsList", (List<Goods>) map.get("list"));
			mv.addObject("page", (Page) map.get("page"));
			mv.addObject("goodsName", goodsName);
		}
		mv.setViewName("goods/goods/goods_list");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 编辑产品基本信息
	 * 
	 * @param request
	 * @param goods
	 * @return
	 * @throws IOException
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年5月26日 上午9:33:15
	 */
	@ResponseBody
	@RequestMapping(value = "/copygoodsinfo")
	public ResultInfo copyGoodsInfo(HttpServletRequest request, HttpSession session, Goods goods) throws IOException {
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		goods.setCompanyId(user.getCompanyId());
		goods.setCreator(user.getUserId());
		goods.setAddTime(DateUtil.sysTimeMillis());
		goods.setUpdater(user.getUserId());
		goods.setModTime(DateUtil.sysTimeMillis());
		ResultInfo res = goodsService.copyGoods(goods);

		ResultInfo resultInfo = new ResultInfo<>();
		if (res.getCode() == 0) {
			Goods goodsInfo = (Goods) res.getData();
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
			resultInfo.setData(goodsInfo.getGoodsId());
			return resultInfo;
		} else {
			return res;
		}

	}

	/**
	 * 
	 * <b>Description:耗材商城商品管理列表</b>
	 * 
	 * @param request
	 * @param goods
	 * @param pageNo
	 * @param pageSize
	 * @param session
	 * @return ModelAndView
	 * @Note <b>Author：</b> cooper.xu <b>Date:</b> 2018年11月15日 下午2:59:16
	 */
	@ResponseBody
	@RequestMapping(value = "/goGoodsIndex")
	public ModelAndView goGoodsIndex(HttpServletRequest request, Goods goods,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize, HttpSession session) {
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		// 查询顶级产品分类
		Category category = new Category();
		StandardCategory standardCategory = new StandardCategory();
		standardCategory.setParentId(0);
		category.setParentId(0);
		if (null != user) {
			category.setCompanyId(user.getCompanyId());
			goods.setCompanyId(user.getCompanyId());
		}
		goods.setSource(1);// 来源于耗材商城
		List<Category> categoryList = categoryService.getParentCateList(category);
		List<StandardCategory> standardCategoryList = categoryService.getParentStandardCateList(standardCategory);

		// 产品级别
		List<SysOptionDefinition> manageCategoryLevels = getSysOptionDefinitionList(SysOptionConstant.ID_338);

		// 产品级别
		List<SysOptionDefinition> goodsLevels = getSysOptionDefinitionList(SysOptionConstant.ID_334);
		// if(JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST+SysOptionConstant.ID_334)){
		// String
		// json=JedisUtils.get(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST+SysOptionConstant.ID_334);
		// JSONArray jsonArray=JSONArray.fromObject(json);
		// goodsLevels=(List<SysOptionDefinition>)
		// JSONArray.toCollection(jsonArray, SysOptionDefinition.class);
		// }

		// 产品类别
		List<SysOptionDefinition> goodsTypes = getSysOptionDefinitionList(SysOptionConstant.ID_315);
		// if(JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST+SysOptionConstant.ID_315)){
		// String
		// json=JedisUtils.get(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST+SysOptionConstant.ID_315);
		// JSONArray jsonArray=JSONArray.fromObject(json);
		// goodsTypes=(List<SysOptionDefinition>)
		// JSONArray.toCollection(jsonArray, SysOptionDefinition.class);
		// }
		// 产品部人员列表
		List<User> buyerList = userService.getUserByPositType(SysOptionConstant.ID_311, user.getCompanyId());
		Integer isBuyer = 0;
		for (User buyer : buyerList) {
			if (buyer.getUserId().intValue() == user.getUserId().intValue()) {
				isBuyer = 1;
				break;
			}
		}

		if (null != goods.getGoodsUserId() && goods.getGoodsUserId() != -1) {
			List<Integer> categoryIdList = userService.getCategoryIdListByUserId(goods.getGoodsUserId());
			if (categoryIdList == null || categoryIdList.isEmpty()) {
				categoryIdList.add(-1);
			}
			goods.setCategoryIdList(categoryIdList);
		}

		ModelAndView mv = new ModelAndView();

		Page page = getPageTag(request, pageNo, pageSize);

		Map<String, Object> map = goodsService.getGoodsListPage(request, goods, page, session);
		List<Goods> list = (List<Goods>) map.get("list");

		for (int i = 0; i < list.size(); i++) {
			// 审核人
			if (null != list.get(i).getVerifyUsername()) {
				List<String> verifyUsernameList = Arrays.asList(list.get(i).getVerifyUsername().split(","));
				list.get(i).setVerifyUsernameList(verifyUsernameList);
			}
		}

		mv.addObject("goodsList", list);
		mv.addObject("goods", goods);
		mv.addObject("page", (Page) map.get("page"));
		mv.addObject("categoryList", categoryList);
		mv.addObject("standardCategoryList", standardCategoryList);
		mv.addObject("goodsLevels", goodsLevels);
		mv.addObject("manageCategoryLevels", manageCategoryLevels);
		mv.addObject("goodsTypes", goodsTypes);
		mv.addObject("buyerList", buyerList);
		mv.addObject("loginUser", user);
		mv.addObject("isBuyer", isBuyer);
		mv.setViewName("goods/goods/index");
		return mv;
	}

	@ResponseBody
	@RequestMapping(value="/updateGoodsInfoById")
	public ResultInfo<?> updateGoodsInfoById(HttpServletRequest request,Goods goods) {
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		goods.setCompanyId(user.getCompanyId());
		goods.setUpdater(user.getUserId());
		goods.setModTime(DateUtil.sysTimeMillis());
		return goodsService.updateGoodsInfoById(goods);
	}
	
	@ResponseBody
	@RequestMapping(value = "/delGoodsChannelPriceAll")
	public ResultInfo<?> delGoodsChannelPriceAll(HttpServletRequest request, @RequestParam(defaultValue = "") String goodsChannelPriceId) {
		GoodsChannelPrice gcp = new GoodsChannelPrice();
		gcp.setGoodsChannelPriceId(Integer.parseInt(goodsChannelPriceId));
		return goodsChannelPriceService.delGoodsChannelPriceAll(gcp);
	}
}
