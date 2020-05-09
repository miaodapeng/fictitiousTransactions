package com.vedeng.logistics.controller;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.concurrent.Executors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.common.collect.Collections2;
import com.smallhospital.dto.ElRequestStatus;
import com.smallhospital.dto.ElResultDTO;
import com.smallhospital.service.ElSaleOrderService;
import com.alibaba.fastjson.JSON;
import com.common.constants.Contant;
import com.rabbitmq.MsgProducer;
import com.rabbitmq.RabbitConfig;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.vedeng.common.annotation.MethodLock;
import com.vedeng.common.annotation.MethodLockParam;
import com.vedeng.common.constant.*;
import com.vedeng.common.model.TemplateVar;
import com.vedeng.common.model.vo.ReqTemplateVariable;
import com.vedeng.common.util.*;
import com.vedeng.finance.model.Invoice;
import com.vedeng.finance.model.InvoiceApply;
import com.vedeng.finance.model.InvoiceApplyDetail;
import com.vedeng.finance.service.InvoiceService;
import com.vedeng.goods.model.CoreSpuGenerate;
import com.vedeng.goods.service.VgoodsService;
import com.vedeng.goods.model.vo.GoodsVo;
import com.vedeng.logistics.dao.LogisticsMapper;
import com.vedeng.logistics.model.*;
import com.vedeng.order.dao.SaleorderMapper;
import com.vedeng.ordergoods.dao.SaleorderGoodsGenerateMapper;
import com.vedeng.ordergoods.model.SaleorderGoodsGenerate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.vedeng.aftersales.model.AfterSales;
import com.vedeng.aftersales.model.AfterSalesGoods;
import com.vedeng.aftersales.model.vo.AfterSalesGoodsVo;
import com.vedeng.aftersales.model.vo.AfterSalesVo;
import com.vedeng.aftersales.service.AfterSalesService;
import com.vedeng.authorization.model.Organization;
import com.vedeng.authorization.model.User;
import com.vedeng.common.annotation.SystemControllerLog;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.exception.ShowErrorMsgException;
import com.vedeng.common.model.FileInfo;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.putHCutil.service.HcSaleorderService;
import com.vedeng.common.redis.RedisUtils;
import com.vedeng.common.shiro.JedisUtils;
import com.vedeng.common.validator.FormToken;
import com.vedeng.goods.model.Goods;
import com.vedeng.goods.model.vo.GoodsExpirationWarnVo;
import com.vedeng.logistics.dao.LendOutMapper;
import com.vedeng.logistics.service.ExpressService;
import com.vedeng.logistics.service.LogisticsService;
import com.vedeng.logistics.service.StorageroomService;
import com.vedeng.logistics.service.WarehouseGoodsOperateLogService;
import com.vedeng.logistics.service.WarehouseGoodsSetService;
import com.vedeng.logistics.service.WarehouseInService;
import com.vedeng.logistics.service.WarehouseOutService;
import com.vedeng.logistics.service.WarehousesService;
import com.vedeng.order.model.Buyorder;
import com.vedeng.order.model.BuyorderGoods;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.model.SaleorderGoods;
import com.vedeng.order.model.vo.BuyorderGoodsVo;
import com.vedeng.order.model.vo.BuyorderVo;
import com.vedeng.order.service.BuyorderService;
import com.vedeng.order.service.SaleorderService;
import com.vedeng.soap.service.VedengSoapService;
import com.vedeng.system.model.Attachment;
import com.vedeng.system.service.CompanyService;
import com.vedeng.system.service.FtpUtilService;
import com.vedeng.system.service.OrgService;
import com.vedeng.system.service.UserService;

/**
 * <b>Description:</b><br>
 * 采购入库 logistics/warehousein/saveExpress
 *
 * @author Administrator
 * @Note <b>ProjectName:</b> erp <br>
 *       <b>PackageName:</b> com.vedeng.logistics.controller <br>
 *       <b>ClassName:</b> WarehouseInController <br>
 *       <b>Date:</b> 2017年8月16日 下午4:43:00
 */
@Controller
@RequestMapping("logistics/warehousein")
public class WarehouseInController extends BaseController {
	Logger logger=LoggerFactory.getLogger(WarehouseInController.class);

	@Autowired
	@Qualifier("warehouseInService")
	private WarehouseInService warehouseInService;

	@Autowired
	@Qualifier("warehouseGoodsSetService")
	private WarehouseGoodsSetService warehouseGoodsSetService;

	@Autowired
	@Qualifier("warehousesService")
	private WarehousesService warehousesService;

	@Autowired
	@Qualifier("buyorderService")
	private BuyorderService buyorderService;

	@Autowired
	@Qualifier("storageroomService")
	private StorageroomService storageroomService;

	@Autowired
	@Qualifier("orgService")
	private OrgService orgService;// 部门

	@Autowired
	@Qualifier("userService")
	private UserService userService;// 自动注入userService

	@Autowired
	@Qualifier("warehouseGoodsOperateLogService")
	private WarehouseGoodsOperateLogService warehouseGoodsOperateLogService;

	@Autowired
	@Qualifier("logisticsService")
	private LogisticsService logisticsService;

	@Autowired
	@Qualifier("expressService")
	private ExpressService expressService;

	@Resource
	private AfterSalesService afterSalesOrderService;

	@Autowired
	@Qualifier("companyService")
	private CompanyService companyService;

	@Autowired
	@Qualifier("ftpUtilService")
	private FtpUtilService ftpUtilService;

	@Autowired
	@Qualifier("vedengSoapService")
	private VedengSoapService vedengSoapService;

	@Autowired
	@Qualifier("saleorderService")
	private SaleorderService saleorderService;

	@Autowired
	@Qualifier("hcSaleorderService")
	protected HcSaleorderService hcSaleorderService;

	@Autowired
	@Qualifier("lendOutMapper")
	private LendOutMapper lendOutMapper;

	@Autowired
	@Qualifier("warehouseOutService")
	private WarehouseOutService warehouseOutService;

	@Autowired
	private RedisUtils redisUtils;

	@Autowired
	private ElSaleOrderService elSaleOrderService;

	@Resource
	SaleorderMapper saleorderMapper;

	@Autowired
	private InvoiceService invoiceService;

	@Autowired
	@Qualifier("msgProducer")
	MsgProducer msgProducer;

	@Autowired
	SaleorderGoodsGenerateMapper saleorderGoodsGenerateMapper;

	@Autowired
	private VgoodsService vgoodsService;

	/**
	 * <b>Description:</b><br>
	 * 采购入库列表
	 *
	 * @param request
	 * @param fileDelivery
	 * @param express
	 * @param pageNo
	 * @param pageSize
	 * @param session
	 * @param searchBeginTime
	 * @param searchEndTime
	 * @return
	 * @Note <b>Author:</b> Administrator <br>
	 *       <b>Date:</b> 2017年8月3日 下午2:51:40
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/index")
	public ModelAndView index(HttpServletRequest request, BuyorderVo buyorderVo,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo, // required
			// =
			// false:可不传入pageNo这个参数，true必须传入，defaultValue默认值，若无默认值，使用Page类中的默认值
			@RequestParam(required = false) Integer pageSize, HttpSession session,
			@RequestParam(required = false, value = "beginTime") String searchBeginTime,
			@RequestParam(required = false, value = "endTime") String searchEndTime) {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView();
		Page page = getPageTag(request, pageNo, pageSize);
		mv.addObject("beginTime", searchBeginTime);
		mv.addObject("endTime", searchEndTime);
		if (searchBeginTime != null && !searchBeginTime.equals("")) {
			buyorderVo.setSearchBegintime(DateUtil.convertLong(searchBeginTime + " 00:00:00", DateUtil.TIME_FORMAT));
		}
		if (searchEndTime != null && !searchEndTime.equals("")) {
			buyorderVo.setSearchEndtime(DateUtil.convertLong(searchEndTime + " 23:59:59", DateUtil.TIME_FORMAT));
		}
		// 产品部门--选择条件
		List<Organization> productOrgList = orgService.getOrgListByPositType(SysOptionConstant.ID_311,
				user.getCompanyId());
		mv.addObject("productOrgList", productOrgList);
		// 产品负责人
		List<User> productUserList = userService.getUserListByPositType(SysOptionConstant.ID_311, user.getCompanyId());
		mv.addObject("productUserList", productUserList);

		if (buyorderVo.getProOrgtId() != null) {
			List<User> userList = userService.getUserListByOrgId(buyorderVo.getProOrgtId());
			List<Integer> userIds = new ArrayList<Integer>();
			// 如果申请人条件是全部的话
			for (User c : userList) {
				userIds.add(c.getUserId());
			}
			buyorderVo.setUserIds(userIds);
		}
		// 选取进行中的采购单
		buyorderVo.setStatus(1);
		// 收货状态不等于全部收货
		buyorderVo.setArrivalStatus(3);
		buyorderVo.setSearchDateType(2);
		// 是否直发(否)
		buyorderVo.setDeliveryDirect(0);
		buyorderVo.setValidStatus(1);
		Map<String, Object> buyorderWarehousesInList;
		buyorderVo.setCompanyId(user.getCompanyId());
		try {
			buyorderWarehousesInList = warehouseInService.getWarehouseinList(buyorderVo, page, productUserList);

			List<BuyorderVo> buyOrderList = (List<BuyorderVo>)buyorderWarehousesInList.get("list");

			//新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
			if(!CollectionUtils.isEmpty(buyOrderList)){
				List<Integer> skuIds = new ArrayList<>();
				buyOrderList.stream().forEach(buyOrder -> {
					if(!CollectionUtils.isEmpty(buyOrder.getBuyorderGoodsVoList())){
						buyOrder.getBuyorderGoodsVoList().stream().forEach(buyorderGood -> {
							skuIds.add(buyorderGood.getGoodsId());
						});
					}
				});
				List<Map<String,Object>> skuTipsMap = this.vgoodsService.skuTipList(skuIds);
				Map<String,Map<String,Object>> newSkuInfosMap = skuTipsMap.stream().collect(Collectors.toMap(key->key.get("SKU_NO").toString(), v -> v, (k, v) -> v));
				mv.addObject("newSkuInfosMap", newSkuInfosMap);
			}
			//新商品流切换 VDERP-1970 add by brianna 2020/3/16 end

			mv.addObject("list",buyOrderList );
			mv.addObject("page", buyorderWarehousesInList.get("page"));
		} catch (Exception e) {
			logger.error("warehourse:", e);
		}
		mv.addObject("buyorderVo", buyorderVo);
		mv.setViewName("logistics/warehousein/index");
		return mv;

	}

	/**
	 * <b>Description:</b><br>
	 * 采购入库操作页面
	 *
	 * @param session
	 * @param buyorderVo
	 * @return
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年8月9日 下午6:02:53
	 */
	@FormToken(save = true)
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/getWarehouseIn")
	public ModelAndView getWarehouseIn(HttpSession session, BuyorderVo buyorderVo) {
		ModelAndView mv = new ModelAndView();
		// 获取session中user信息
		User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
		BuyorderVo buyorderInfo = buyorderService.getBuyorderInDetail(buyorderVo, session_user);
		// 获取条码入库记录
		WarehouseGoodsOperateLog wlg = new WarehouseGoodsOperateLog();
		// 查询入库记录
		wlg.setOperateType(1);
		wlg.setIsEnable(1);
		wlg.setCompanyId(session_user.getCompanyId());
		wlg.setBuyorderNo(buyorderVo.getBuyorderNo());
		try {
			// 入库记录
			List<WarehouseGoodsOperateLog> wlog = warehouseGoodsOperateLogService.getWGOlog(wlg);
			if (null != wlg) {
				Integer wNum = 0;
				List<String> timeArray = new ArrayList<>();
				for (WarehouseGoodsOperateLog w : wlog) {
					wNum += w.getNum();
					timeArray.add(DateUtil.convertString(w.getAddTime(), "yyyy.MM.dd"));
				}
				HashSet<String> tArray = new HashSet<String>(timeArray);
				// 已入库数量
				mv.addObject("wNum", wNum);
				// 获取排重后的日期
				mv.addObject("timeArray", tArray);
			}
			mv.addObject("wlog", wlog);

			//新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
			if(!CollectionUtils.isEmpty(wlog)){
				List<Integer> skuIds = new ArrayList<>();
				wlog.stream().forEach(warehouseGoodsOperateLog -> {
					skuIds.add(warehouseGoodsOperateLog.getGoodsId());
				});
				List<Map<String,Object>> skuTipsMap = this.vgoodsService.skuTipList(skuIds);
				Map<String,Map<String,Object>> newSkuInfosMap = skuTipsMap.stream().collect(Collectors.toMap(key->key.get("SKU_NO").toString(), v -> v, (k, v) -> v));
				mv.addObject("warehouseGoodsOperateLogMap", newSkuInfosMap);
			}
			//新商品流切换 VDERP-1970 add by brianna 2020/3/16 end

		} catch (Exception e) {
			logger.error("getWarehouseIn error:{}", e);
		}

		// 物流信息
		Express express = new Express();
		express.setBusinessType(SysOptionConstant.ID_515);
		List<Integer> relatedIds = new ArrayList<Integer>();
		List<Express> expressList = new ArrayList<Express>();
		for (BuyorderGoodsVo buyorderGoodsVo : buyorderInfo.getBuyorderGoodsVoList()) {
			// 原来取得总数是num为了不影响其他功能，将num改为减去售后的值，num-afterSaleUpLimitNum
			relatedIds.add(buyorderGoodsVo.getBuyorderGoodsId());
			buyorderGoodsVo.setNum(buyorderGoodsVo.getNum() - buyorderGoodsVo.getAfterSaleUpLimitNum());
		}
		if (relatedIds != null && !relatedIds.isEmpty()) {
			express.setRelatedIds(relatedIds);
			try {
				expressList = expressService.getExpressList(express);
				mv.addObject("expressList", expressList);
			} catch (Exception e) {
				logger.error("getWarehouseIn error{}:", e);
			}
		}
		// 入库附件
		Attachment att = new Attachment();
		att.setRelatedId(buyorderInfo.getBuyorderId());
		att.setAttachmentFunction(SysOptionConstant.ID_837);
		// att.setAttachmentType(460);
		List<Attachment> AttachmentList = warehouseInService.getAttachmentList(att);
		mv.addObject("AttachmentList", AttachmentList);
		mv.addObject("buyorderInfo", buyorderInfo);


		//新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
		if(!CollectionUtils.isEmpty(buyorderInfo.getBuyorderGoodsVoList())){
			List<Integer> skuIds = new ArrayList<>();
			buyorderInfo.getBuyorderGoodsVoList().stream().forEach(buyOrder -> {
				skuIds.add(buyOrder.getGoodsId());
			});
			List<Map<String,Object>> skuTipsMap = this.vgoodsService.skuTipList(skuIds);
			Map<String,Map<String,Object>> newSkuInfosMap = skuTipsMap.stream().collect(Collectors.toMap(key->key.get("SKU_NO").toString(), v -> v, (k, v) -> v));
			mv.addObject("newSkuInfosMap", newSkuInfosMap);
		}
		//新商品流切换 VDERP-1970 add by brianna 2020/3/16 end

		mv.setViewName("logistics/warehousein/view_warehousein");
		return mv;
	}

	/**
	 * 打开入库附件上传页面 <b>Description:</b><br>
	 *
	 * @param request
	 * @param session
	 * @param saleorderId
	 * @return
	 * @Note <b>Author:</b> Cooper <br>
	 *       <b>Date:</b> 2018年7月31日 下午3:50:24
	 */
	@ResponseBody
	@RequestMapping(value = "/contractReturnInit")
	public ModelAndView contractReturnInit(HttpServletRequest request, HttpSession session, Integer buyorderId) {
		// User user = (User)
		// request.getSession().getAttribute(Consts.SESSION_USER);
		ModelAndView mv = new ModelAndView();

		mv.addObject("buyorderId", buyorderId);
		mv.setViewName("logistics/warehousein/contract_return");
		return mv;
	}

	/**
	 * 入库附件上传 <b>Description:</b><br>
	 *
	 * @param request
	 * @param response
	 * @param lwfile
	 * @return
	 * @Note <b>Author:</b> Cooper <br>
	 *       <b>Date:</b> 2018年8月1日 上午9:04:03
	 */
	@ResponseBody
	@RequestMapping(value = "/contractReturnUpload")
	public FileInfo contractReturnUpload(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("lwfile") MultipartFile lwfile) {
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		if (user != null) {
			String path = "/upload/warehousein";
			long size = lwfile.getSize();
			if (size > 2 * 1024 * 1024) {
				return new FileInfo(-1, "图片大小应为2MB以内");
			}
			return ftpUtilService.uploadFile(lwfile, path, request, "");
		} else {
			return new FileInfo(-1, "登录用户不能为空");
		}
	}

	/**
	 * 入库附件保存 <b>Description:</b><br>
	 *
	 * @param request
	 * @param attachment
	 * @return
	 * @Note <b>Author:</b> Cooper <br>
	 *       <b>Date:</b> 2018年7月31日 下午5:39:45
	 */
	@ResponseBody
	@RequestMapping(value = "/contractReturnSave")
	public ResultInfo<?> contractReturnSave(HttpServletRequest request, Attachment attachment) {
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		if (attachment != null && (attachment.getUri().contains("jpg") || attachment.getUri().contains("png")
				|| attachment.getUri().contains("gif") || attachment.getUri().contains("bmp"))) {
			attachment.setAttachmentType(SysOptionConstant.ID_460);
		} else {
			attachment.setAttachmentType(SysOptionConstant.ID_461);
		}
		attachment.setAttachmentFunction(SysOptionConstant.ID_837);
		if (user != null) {
			attachment.setCreator(user.getUserId());
			attachment.setAddTime(DateUtil.sysTimeMillis());
		}
		ResultInfo<?> saveAttachment = warehouseInService.saveWarehouseinAttachment(attachment);
		// 附件同步
		if (saveAttachment.getCode().equals(0) && user.getCompanyId().equals(1)) {
			Attachment att = (Attachment) saveAttachment.getData();
			if (null != att) {
				vedengSoapService.warehouseinAttachmentSyncWeb(att.getAttachmentId(), false);
			}
		}
		return saveAttachment;
	}

	/**
	 * 入库附件删除 <b>Description:</b><br>
	 *
	 * @param request
	 * @param attachment
	 * @return
	 * @Note <b>Author:</b> Cooper <br>
	 *       <b>Date:</b> 2018年8月1日 下午1:34:41
	 */
	@ResponseBody
	@RequestMapping(value = "/contractReturnDel")
	@SystemControllerLog(operationType = "delete", desc = "入库附件删除")
	public ResultInfo<?> contractReturnDel(HttpServletRequest request, Attachment attachment) {
		ResultInfo<?> delWarehouseinAttachment = warehouseInService.delWarehouseinAttachment(attachment);
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		// 附件同步
		if (delWarehouseinAttachment.getCode().equals(0) && user.getCompanyId().equals(1)) {
			vedengSoapService.warehouseinAttachmentSyncWeb(attachment.getAttachmentId(), true);
		}
		return delWarehouseinAttachment;
	}

	/**
	 * <b>Description:</b><br>
	 * 生成条形码
	 *
	 * @param session
	 * @param buyorderGoods
	 * @return
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年8月11日 上午11:05:13
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/addBarcode")
	@SystemControllerLog(operationType = "add", desc = "保存生成条形码信息")
	public ModelAndView addBarcode(HttpSession session, BuyorderGoods buyorderGoods, Integer type,
			AfterSalesGoods afterSalesGoods, Integer businessType,Integer rknum, Integer goodsId) {
		ModelAndView mv = new ModelAndView();
		// 获取session中user信息
		User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
        Boolean logisticeFlag = userService.getLogisticeFlagByUserId(session_user.getUserId());
        BuyorderVo buyorderVo = new BuyorderVo();
		BuyorderGoodsVo buyorderGoodsInfo = new BuyorderGoodsVo();
		AfterSalesGoodsVo afterSalesGoodsInfo = new AfterSalesGoodsVo();
		Goods goods = new Goods();
		LendOut lendout = LendOut.getinstance();


		//新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
		Map<String,Object> newSkuInfo = null;

		int _type = type;
		if (_type == 1) {
			buyorderVo.setBuyorderId(buyorderGoods.getBuyorderId());
			BuyorderVo buyorderInfo = buyorderService.getBuyorderInDetail(buyorderVo, session_user);
			for (BuyorderGoodsVo bg : buyorderInfo.getBuyorderGoodsVoList()) {
				if (buyorderGoods.getBuyorderGoodsId().intValue() == bg.getBuyorderGoodsId()) {
					// 原来取得总数是num为了不影响其他功能，将num改为减去售后的值，num-afterSaleUpLimitNum
					bg.setNum(bg.getNum() - bg.getAfterSaleUpLimitNum());
					buyorderGoodsInfo = bg;
				}
			}

			//新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
			newSkuInfo = vgoodsService.skuTip(buyorderGoodsInfo.getGoodsId());

		} else if (_type == 4){
			_type = 4;
			goods = warehouseInService.getLendoutGoodsInfo(afterSalesGoods.getAfterSalesGoodsId());

			newSkuInfo = vgoodsService.skuTip(afterSalesGoodsInfo.getGoodsId());

			lendout = lendOutMapper.selectByPrimaryKey(afterSalesGoods.getAfterSalesGoodsId());
		}else{
			_type = 2;
			afterSalesGoodsInfo = afterSalesOrderService.getAfterSalesGoodsInfo(afterSalesGoods);

			newSkuInfo = vgoodsService.skuTip(afterSalesGoodsInfo.getGoodsId());
		}

		mv.addObject("newSkuInfo", newSkuInfo);
		//新商品流切换 VDERP-1970 add by brianna 2020/3/16 end

		// 有效二维码信息
		Barcode barcodeEnable = new Barcode();
		barcodeEnable.setIsEnable(4);
		barcodeEnable.setType(_type);// 设置条码类型 1采购 2售后 4外借
		if (_type == 1) {
			barcodeEnable.setDetailGoodsId(buyorderGoods.getBuyorderGoodsId());
		} else if(_type == 4){
			barcodeEnable.setDetailGoodsId(lendout.getLendOutId());
		}else{
			barcodeEnable.setDetailGoodsId(afterSalesGoodsInfo.getAfterSalesGoodsId());
		}
		List<Barcode> barcodeEnableList;
		try {
			barcodeEnable.setCompanyId(session_user.getCompanyId());
			barcodeEnableList = warehouseInService.getBarcode(barcodeEnable);
			mv.addObject("barcodeEnableList", barcodeEnableList);
			// 获取排重后的日期
			if (null != barcodeEnableList) {
				List<String> timeArray = new ArrayList<>();
				for (Barcode b : barcodeEnableList) {
					timeArray.add(DateUtil.convertString(b.getAddTime(), "YYYY.MM.dd"));
				}
				HashSet<String> tArray = new HashSet<String>(timeArray);
				mv.addObject("timeArray", tArray);
			}
		} catch (Exception e) {
			logger.error("addBarcode:", e);
		}
		// 所有这个产品的二维码信息
		Barcode barcode = new Barcode();
		if (type == 1) {
			barcode.setDetailGoodsId(buyorderGoods.getBuyorderGoodsId());
		}else if(type == 4){
			barcode.setDetailGoodsId(lendout.getLendOutId());
		} else {
			barcode.setDetailGoodsId(afterSalesGoodsInfo.getAfterSalesGoodsId());
		}
		barcode.setType(_type);
		List<Barcode> barcodeList;
		ResultInfo<?> result = new ResultInfo<>();
		try {

			barcode.setCompanyId(session_user.getCompanyId());
			barcodeList = warehouseInService.getBarcode(barcode);
			List<Barcode> supplementBarcode = new ArrayList<>();
			for (Barcode bd : barcodeList) {
				if (null == bd.getFtpPath() || "".equals(bd.getFtpPath())) {
					supplementBarcode.add(bd);
				}
			}
			// 分页补充生成条码
			if (supplementBarcode.size() > 1000) {
				double count = supplementBarcode.size();
				double pageNum = 1000;
				Integer page = (int) Math.ceil(count / pageNum);
				for (Integer i = 0; i < page; i++) {
					List<Barcode> barcodeInfoList = new ArrayList<>();
					for (Integer j = 1000 * i; 1000 * (i + 1) > j && j < supplementBarcode.size(); j++) {
						barcodeInfoList.add(supplementBarcode.get(j));
					}
					result = warehouseInService.supplementBarcode(barcodeInfoList);
				}
			} else if (supplementBarcode.size() > 0) {
				result = warehouseInService.supplementBarcode(supplementBarcode);
			}
			// 如果有补充条码重新查询
			if (supplementBarcode.size() > 0) {
				barcodeList = warehouseInService.getBarcode(barcode);
			}


			//新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
			if(!CollectionUtils.isEmpty(barcodeList)){
				List<Integer> skuIds = new ArrayList<>();
				barcodeList.stream().forEach(barcodeItem -> {
					skuIds.add(barcodeItem.getBuyorderGood().getGoodsId());
				});
				List<Map<String,Object>> skuTipsMap = this.vgoodsService.skuTipList(skuIds);
				Map<Integer,Map<String,Object>> newSkuInfosMap = skuTipsMap.stream().collect(Collectors.toMap(key->Integer.valueOf(key.get("SKU_ID").toString()), v -> v, (k, v) -> v));
				mv.addObject("barcodesMap", newSkuInfosMap);
			}
			//新商品流切换 VDERP-1970 add by brianna 2020/3/16 end


			mv.addObject("barcodeList", barcodeList);
		} catch (Exception e) {
			logger.error("addBarcode 2:", e);
		}
		mv.addObject("businessType", businessType);
		try {
			Attachment attachment = warehouseInService.getSkuBarcode(goodsId);
			mv.addObject("attachment",attachment);
		}catch (Exception e){
			logger.error("skuBarcode error:{}",e);
		}
		mv.addObject("logisticeFlag",logisticeFlag);
		if (type == 1) {
			mv.addObject("buyorderGoodsInfo", buyorderGoodsInfo);
			mv.setViewName("logistics/warehousein/add_barcode");
		} else if (type == 2) {
			mv.addObject("afterSalesGoodsInfo", afterSalesGoodsInfo);
			mv.addObject("rknum",rknum);
			mv.setViewName("aftersales/storageAftersales/add_returnGoodsbarcode");

		} else if (type == 3) {
			mv.addObject("afterSalesGoodsInfo", afterSalesGoodsInfo);
			mv.addObject("rknum",rknum);
			mv.setViewName("aftersales/storageAftersales/add_changeGoodsbarcode");
		}else if (type == 4) {
			mv.addObject("goods", goods);
			mv.addObject("lendout", lendout);
			mv.setViewName("logistics/warehousein/add_lendout_barcode");
		}
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 生成二维码图片
	 *
	 * @param buyorderGoods
	 *            需要生产的采购产品
	 * @param num
	 *            生成个数
	 * @return
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年8月11日 上午11:28:53
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/addBarcodeImg")
	@SystemControllerLog(operationType = "add", desc = "保存生成二维码图片")
	public ResultInfo addBarcodeImg(BuyorderGoods buyorderGoods, Integer num, Integer type,
			AfterSalesGoods afterSalesGoods, HttpSession session) {
		Barcode barcode = new Barcode();
		ResultInfo<?> result = new ResultInfo<>();
		// 获取session中user信息
		User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
		// 所有这个采购产品的二维码信息
		Barcode barcodes = new Barcode();
		if (type == 1) {
			barcodes.setDetailGoodsId(buyorderGoods.getBuyorderGoodsId());
		} else if(type == 4){//外借
			type = 4;
			barcodes.setDetailGoodsId(afterSalesGoods.getAfterSalesGoodsId());
		}else{
			type = 2;
			barcodes.setDetailGoodsId(afterSalesGoods.getAfterSalesGoodsId());
		}
		barcodes.setType(type);// 设置条码类型（1采购 2售后 4外借）
		List<Barcode> barcodeList = null;
		try {
			barcodes.setCompanyId(session_user.getCompanyId());
			barcodeList = warehouseInService.getBarcode(barcodes);
		} catch (Exception e) {
			logger.error("addBarcodeImg:", e);
		}

		if (type == 1) {
			barcode.setDetailGoodsId(buyorderGoods.getBuyorderGoodsId());// 设置采购产品ID
			barcode.setGoodsId(buyorderGoods.getGoodsId());// 设置商品ID
		} else {
			barcode.setDetailGoodsId(afterSalesGoods.getAfterSalesGoodsId());// 设置售后产品ID
			barcode.setGoodsId(afterSalesGoods.getGoodsId());// 设置商品ID
		}
		barcode.setType(type);// 设置条码类型 1采购 2售后 4外借
		barcode.setCreator(session_user.getUserId());// 设置添加人
		barcode.setAddTime(DateUtil.sysTimeMillis());// 添加时间
		barcode.setUpdater(session_user.getUserId());// 设置更新人
		barcode.setModTime(DateUtil.sysTimeMillis());// 修改时间
		barcode.setSequence(barcodeList.size());// 设置序号的初始值
		barcode.setIsEnable(1);
		try {
			result = warehouseInService.addBarcode(num, barcode);
		} catch (Exception e) {
			logger.error("addBarcodeImg 2:", e);
		}
		return result;
	}

	/**
	 * <b>Description:</b><br>
	 * 修改二维码前对二维码是否入库 进行判断
	 *
	 * @param barcodeIds
	 * @param session
	 * @param beforeParams
	 * @return
	 * @Note <b>Author:</b> Cooper <br>
	 *       <b>Date:</b> 2018年5月22日 下午4:35:20
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/checkBarcode")
	public ResultInfo checkBarcode(String barcodeId, HttpSession session, String beforeParams) {
		ResultInfo<?> result = new ResultInfo<>();
		// 获取session中user信息
		User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
		Barcode barcode = null;
		if (null != barcodeId) {
			// 根据二维码ID封装对象
			barcode = new Barcode();
			barcode.setBarcodeId(Integer.parseInt(barcodeId));
			barcode.setUpdater(session_user.getUserId());
			barcode.setModTime(DateUtil.sysTimeMillis());
			barcode.setIsEnable(0);
		}
		try {
			result = warehouseInService.checkBarcode(barcode);
		} catch (Exception e) {
			logger.error("checkBarcode:", e);
		}
		return result;
	}

	/**
	 * <b>Description:</b><br>
	 * 修改二维码状态
	 *
	 * @param barcodeIds
	 * @param session
	 * @return
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年8月24日 下午1:46:29
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/editBarcode")
	@SystemControllerLog(operationType = "edit", desc = "保存编辑二维码状态")
	public ResultInfo editBarcode(String barcodeIds, HttpSession session, String beforeParams) {
		ResultInfo<?> result = new ResultInfo<>();
		// 获取session中user信息
		User session_user = (User) session.getAttribute(ErpConst.CURR_USER);

		List<Barcode> barcodeList = new ArrayList<Barcode>();
		Barcode barcode = null;
		if (null != barcodeIds) {
			// 切割barcodeId拼接成的字符串
			String[] params = barcodeIds.split("_");
			for (int i = 0; i < params.length; i++) {
				barcode = new Barcode();
				Integer barcodeId = Integer.parseInt(params[i]);
				barcode.setBarcodeId(barcodeId);
				barcode.setUpdater(session_user.getUserId());
				barcode.setModTime(DateUtil.sysTimeMillis());
				barcode.setIsEnable(0);
				barcodeList.add(barcode);
			}
		}
		try {
			result = warehouseInService.editBarcode(barcodeList);
		} catch (Exception e) {
			logger.error("editBarcode:", e);
		}
		return result;
	}

	/**
	 * <b>Description:</b><br>
	 * 入库操作
	 *
	 * @param session
	 * @param buyorderGoods
	 * @return
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年8月24日 下午1:45:07
	 */
	@FormToken(save = true)
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/addWarehouseIn")
	public ModelAndView addWarehouseIn(HttpSession session, BuyorderGoods buyorderGoods, Integer type,
			AfterSalesGoods afterSalesGoods, Integer businessType,Integer rknum) {
		ModelAndView mv = new ModelAndView();
		// 获取session中user信息
		User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
		BuyorderVo buyorderVo = new BuyorderVo();
		BuyorderVo buyorderInfo = new BuyorderVo();
		BuyorderGoodsVo buyorderGoodsInfo = new BuyorderGoodsVo();
		AfterSalesGoodsVo afterSalesGoodsInfo = new AfterSalesGoodsVo();
		Goods goods = new Goods();
		LendOut lendout = LendOut.getinstance();

		//新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
		Map<String,Object> newSkuInfo =  null;

		if (type == 1) {
			buyorderVo.setBuyorderId(buyorderGoods.getBuyorderId());
			buyorderInfo = buyorderService.getBuyorderInDetail(buyorderVo, session_user);
			for (BuyorderGoodsVo bg : buyorderInfo.getBuyorderGoodsVoList()) {
				if (buyorderGoods.getBuyorderGoodsId().intValue() == bg.getBuyorderGoodsId()) {
					// 原来取得总数是num为了不影响其他功能，将num改为减去售后的值，num-afterSaleUpLimitNum
					bg.setNum(bg.getNum() - bg.getAfterSaleUpLimitNum());
					buyorderGoodsInfo = bg;
				}
			}

			newSkuInfo = vgoodsService.skuTip(buyorderGoodsInfo.getGoodsId());

		}else if (type == 4){
			lendout = lendOutMapper.selectByPrimaryKey(afterSalesGoods.getAfterSalesGoodsId());
//			Integer deliveryNum = lendOutMapper.getdeliveryNum(lendout);//已出库数量
			Integer deliveryNum = warehouseOutService.getdeliveryNum(lendout);
			goods = warehouseInService.getLendoutGoodsInfo(afterSalesGoods.getAfterSalesGoodsId());
			goods.setNum(deliveryNum);

			newSkuInfo = vgoodsService.skuTip(goods.getGoodsId());

		}else {
			afterSalesGoodsInfo = afterSalesOrderService.getAfterSalesGoodsInfo(afterSalesGoods);

			newSkuInfo = vgoodsService.skuTip(afterSalesGoodsInfo.getGoodsId());

			if(type==2&&checkDirectSaleorderGoods(afterSalesGoodsInfo.getOrderDetailId())){
				//throw new Exception("直发商品不能入库");
				logger.warn("直发商品不能入库"+afterSalesGoodsInfo.getAfterSalesGoodsId());
				mv.addObject("message","退货直发商品不能入库"+afterSalesGoodsInfo.getGoodsName());
				return fail(mv);
			}
		}

		mv.addObject("newSkuInfo", newSkuInfo);
		//新商品流切换 VDERP-1970 add by brianna 2020/3/16 end

		// 获取这个产品的库位分配的库位
		WarehouseGoodsSet warehouseGoodsSet = new WarehouseGoodsSet();
		warehouseGoodsSet.setCompanyId(session_user.getCompanyId());
		if (type == 1) {
			warehouseGoodsSet.setGoodsId(buyorderGoods.getGoodsId());
		} else if(type==4){
			warehouseGoodsSet.setGoodsId(lendout.getGoodsId());
		}else{
			warehouseGoodsSet.setGoodsId(afterSalesGoodsInfo.getGoodsId());
		}
		List<WarehouseGoodsSet> warehouseGoodsSetList = warehouseGoodsSetService
				.getWarehouseSetForGood(warehouseGoodsSet);
		// 获取所有仓库中，IS_TEMP=1的临时仓库
		Warehouse warehouse = new Warehouse();
		warehouse.setCompanyId(session_user.getCompanyId());
		// 如果采购单是采购订单，或者
		if (buyorderInfo.getOrderType() != null && buyorderInfo.getOrderType() == 1
				&& session_user.getCompanyId() == 1) {
			// 备货库，取ID第二个仓库
			warehouse.setWarehouseId(2);
		} else {
			warehouse.setIsTemp(1);
		}

		List<Warehouse> warehouseLists = warehousesService.getAllWarehouse(warehouse);
		if (null != warehouseLists) {
			for (Warehouse w : warehouseLists) {
				WarehouseGoodsSet wgs = new WarehouseGoodsSet();
				wgs.setWareHouseId(w.getWarehouseId());
				wgs.setWareHouseName(w.getWarehouseName());
				wgs.setComments(w.getComments());
				warehouseGoodsSetList.add(wgs);
			}
		}
		// 获取当前公司下的仓库
		Warehouse warehouses = new Warehouse();
		warehouses.setCompanyId(session_user.getCompanyId());
		List<Warehouse> warehouseList = storageroomService.getWarehouseByCompanyId(warehouses);

		// 获取条码入库记录
		WarehouseGoodsOperateLog wlg = new WarehouseGoodsOperateLog();
		// 查询入库记录
		wlg.setIsEnable(1);
		wlg.setCompanyId(session_user.getCompanyId());
		if (type == 1) {
			wlg.setGoodsId(buyorderGoods.getGoodsId());
			wlg.setRelatedId(buyorderGoods.getBuyorderGoodsId());
			wlg.setOperateType(1);
		} else if(type==4){//外借
			wlg.setGoodsId(lendout.getGoodsId());
			wlg.setRelatedId(lendout.getLendOutId());
			wlg.setOperateType(9);
		}else{
			wlg.setGoodsId(afterSalesGoods.getGoodsId());
			wlg.setRelatedId(afterSalesGoods.getAfterSalesGoodsId());
			if (afterSalesGoodsInfo.getType() == 540) {
				wlg.setOperateType(3);
			} else if (afterSalesGoodsInfo.getType() == 539) {
				wlg.setOperateType(5);
			} else if (afterSalesGoodsInfo.getType() == 547) {
				wlg.setOperateType(8);
			}
		}
		try {
			List<WarehouseGoodsOperateLog> wlog = warehouseGoodsOperateLogService.getWGOlog(wlg);
			if (null != wlg) {
				Integer wNum = 0;
				List<String> timeArray = new ArrayList<>();
				for (WarehouseGoodsOperateLog w : wlog) {
					wNum += w.getNum();
					timeArray.add(DateUtil.convertString(w.getAddTime(), "YYYY-MM-dd"));
				}
				HashSet<String> tArray = new HashSet<String>(timeArray);
				// 已入库数量
				mv.addObject("wNum", wNum);
				// 获取排重后的日期
				mv.addObject("timeArray", tArray);
			}
			mv.addObject("wlog", wlog);
		} catch (Exception e) {
			logger.error("addWarehouseIn:", e);
		}
		mv.addObject("businessType", businessType);
		mv.addObject("warehouseGoodsSetList", warehouseGoodsSetList);
		mv.addObject("warehouseList", warehouseList);
		mv.addObject("buyorderGoodsInfo", buyorderGoodsInfo);
		mv.addObject("afterSalesGoodsInfo", afterSalesGoodsInfo);
		if (type == 1) {
			mv.setViewName("logistics/warehousein/add_warehousein");
		} else if (type == 2) {
			mv.addObject("rknum",rknum);
			mv.setViewName("aftersales/storageAftersales/add_returnGoodsWarehousein");
		} else if (type == 3) {
			mv.addObject("rknum",rknum);
			mv.setViewName("aftersales/storageAftersales/add_changeGoodsWarehousein");
		}else if(type == 4) {
			mv.addObject("goods", goods);
			mv.addObject("lendout", lendout);
			mv.setViewName("logistics/warehousein/add_lendout_warehousein");
		}
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 验证二维码是否跟商品匹配
	 *
	 * @param barcode
	 * @return
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年8月24日 下午1:44:42
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/getVerifyBarcode")
	public ResultInfo getVerifyBarcode(Barcode barcode) {
		ResultInfo<?> result = new ResultInfo<>();
		List<Barcode> barcodeList = null;
		if (barcode.getBarcode() == null || barcode.getBarcode() == "") {
			return new ResultInfo(-1, "请扫描条码");
		}
		try {
			// 有效的条码;
			barcode.setIsEnable(4);
			barcodeList = warehouseInService.getBarcode(barcode);
			if (barcodeList.size() > 0) {
				WarehouseGoodsOperateLog warehouseGoodsOperateLog = new WarehouseGoodsOperateLog();
				List<WarehouseGoodsOperateLog> warehouseGoodsOperateLogList = new ArrayList<>();
				// 每个条码只能被一个入库记录使用
				try {
					// 条形码
					warehouseGoodsOperateLog.setBarcode(barcode.getBarcode());
					// 入库的记录
					if(barcode.getType()==4) {
						warehouseGoodsOperateLog.setOperateType(9);
					}else {
						warehouseGoodsOperateLog.setOperateType(1);
					}
					// 有效的
					warehouseGoodsOperateLog.setIsEnable(1);
					warehouseGoodsOperateLog.setIsBarcode(1);
					warehouseGoodsOperateLogList = warehouseGoodsOperateLogService.getWGOlog(warehouseGoodsOperateLog);
					if (warehouseGoodsOperateLogList.size() > 0) {
						return new ResultInfo(-1, "条码已使用，请重新选择条码");
					} else {
						return new ResultInfo(0, "查询成功", barcodeList.get(0));
					}
				} catch (Exception e) {
					logger.error("getVerifyBarcode:", e);
					return new ResultInfo();
				}
			} else {
				return new ResultInfo(-1, "条码已作废或条码与产品不符，请确认");
			}
		} catch (Exception e) {
			logger.error("getVerifyBarcode 2:", e);
			return new ResultInfo<>();
		}
	}

	/**
	 * <b>Description:</b><br>
	 * 保存入库日志
	 *
	 * @param request
	 * @return
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年8月24日 下午1:44:12
	 */
	@FormToken(remove = true)
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/saveWarehouseIn")
	@SystemControllerLog(operationType = "add", desc = "保存入库日志")
	public ModelAndView  saveWarehouseIn(HttpServletRequest request) {
		String[] barcodeIds = request.getParameterValues("barcodeId");
		String[] barcodeFactorys = request.getParameterValues("barcodeFactory");
		String[] nums = request.getParameterValues("num");
		String[] batchNumbers = request.getParameterValues("batchNumber");
		String[] expirationDates = request.getParameterValues("expirationDate");
		String[] productDates = request.getParameterValues("productDate");
		// 获取五级仓ID下划线拼接组合
		String warehouses = request.getParameter("warehouses");
		// 切割五级仓Id拼接成的字符串
		String[] params = warehouses.split("_");
		String comments = request.getParameter("comments");
		String buyorderGoodsId = request.getParameter("buyorderGoodsId");
		String goodsId = request.getParameter("goodsId");
		String buyorderId = request.getParameter("buyorderId");
		String type = request.getParameter("ywtype");
		String businessType = request.getParameter("businessType");
		String rknum=request.getParameter("rknum");
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		AfterSales afterSales = new AfterSales();
		AfterSalesVo asv = new AfterSalesVo();
		ModelAndView mav = new ModelAndView();
		Integer returnNum = 0;
		if ("2".equals(type) || "3".equals(type)) {
			afterSales.setAfterSalesId(Integer.parseInt(buyorderId));
			afterSales.setBusinessType(Integer.parseInt(businessType));
			asv = afterSalesOrderService.getAfterSalesVoListById(afterSales);
		}
		if (user != null) {
			List<WarehouseGoodsOperateLog> warehouseGoodsOperateLogList = new ArrayList<>();
			for (int i = 0; i < nums.length; i++) {
				// 根据入库数量作为判断，如果没有入库数量不存
				if (null != nums[i] && nums[i] != "") {
					WarehouseGoodsOperateLog warehouseGoodsOperateLog = new WarehouseGoodsOperateLog();
					if (null != barcodeIds && null != barcodeIds[i] && barcodeIds[i] != "") {
						warehouseGoodsOperateLog.setBarcodeId(Integer.parseInt(barcodeIds[i]));
					}else{
						//VDERP-2239
						//扫码入库前段拦截bug会导致无条码也可入库
						continue;
					}
					if (null != barcodeFactorys && null != barcodeFactorys[i] && barcodeFactorys[i] != "") {
						warehouseGoodsOperateLog.setBarcodeFactory(barcodeFactorys[i]);
					}
					if (null != nums && null != nums[i] && nums[i] != "") {
						warehouseGoodsOperateLog.setNum(Integer.parseInt(nums[i]));
					}
					if (null != batchNumbers && null != batchNumbers[i] && batchNumbers[i] != "") {
						warehouseGoodsOperateLog.setBatchNumber(batchNumbers[i]);
					}
					if (null != expirationDates && null != expirationDates[i] && expirationDates[i] != "") {
						warehouseGoodsOperateLog
								.setExpirationDate(DateUtil.convertLong(expirationDates[i], DateUtil.DATE_FORMAT));
					}
					if (null != productDates && null != productDates[i] && productDates[i] != "") {
						warehouseGoodsOperateLog
								.setProductDate(DateUtil.convertLong(productDates[i], DateUtil.DATE_FORMAT));
					}
					// 仓库位置
					if (null != params && params.length >= 1) {
						warehouseGoodsOperateLog.setWarehouseId(Integer.parseInt(params[0]));
					}
					if (null != params && params.length >= 2) {
						warehouseGoodsOperateLog.setStorageRoomId(Integer.parseInt(params[1]));
					}
					if (null != params && params.length >= 3) {
						warehouseGoodsOperateLog.setStorageAreaId(Integer.parseInt(params[2]));
					}
					if (null != params && params.length >= 4) {
						warehouseGoodsOperateLog.setStorageRackId(Integer.parseInt(params[3]));
					}
					if (null != params && params.length >= 5) {
						warehouseGoodsOperateLog.setStorageLocationId(Integer.parseInt(params[4]));
					}
					Integer barcodeIsEnable = warehouseGoodsOperateLogService.getBarcodeIsEnable(warehouseGoodsOperateLog,1);
					//使用过的条码排除
					if(barcodeIsEnable > 0) {
					// 入库
					warehouseGoodsOperateLog.setIsEnable(1);
					if ("2".equals(type) || "3".equals(type)) {
						if (asv.getType() == 540) {
							warehouseGoodsOperateLog.setOperateType(3);
						} else if (asv.getType() == 539) {
							warehouseGoodsOperateLog.setOperateType(5);
						} else if (asv.getType() == 547) {
							warehouseGoodsOperateLog.setOperateType(8);
						}
					} else if("4".equals(type)){
						warehouseGoodsOperateLog.setOperateType(9);
					}else{
						warehouseGoodsOperateLog.setOperateType(1);
					}
					//1入库 2出库3销售换货入库4销售换货出库5销售退货入库6采购退货出库7采购换货出库8采购换货入库9外借入库 10外借出库
					if(warehouseGoodsOperateLog.getOperateType().equals(3)||warehouseGoodsOperateLog.equals(5)){
						AfterSalesGoods afterSalesGoods = new AfterSalesGoods();
						afterSalesGoods.setAfterSalesGoodsId(Integer.parseInt(buyorderGoodsId));
						AfterSalesGoodsVo afterSalesGoodsInfo = afterSalesOrderService.getAfterSalesGoodsInfo(afterSalesGoods);
						warehouseGoodsOperateLog.setIsActionGoods(afterSalesGoodsInfo.getIsActionGoods());
					}
					warehouseGoodsOperateLog.setComments(comments);
					warehouseGoodsOperateLog.setCompanyId(user.getCompanyId());
					warehouseGoodsOperateLog.setRelatedId(Integer.parseInt(buyorderGoodsId));
					warehouseGoodsOperateLog.setGoodsId(Integer.parseInt(goodsId));
					warehouseGoodsOperateLog.setCreator(user.getUserId());
					warehouseGoodsOperateLog.setAddTime(DateUtil.sysTimeMillis());
					warehouseGoodsOperateLog.setUpdater(user.getUserId());
					warehouseGoodsOperateLog.setModTime(DateUtil.sysTimeMillis());
					warehouseGoodsOperateLogList.add(warehouseGoodsOperateLog);
					//外借单入库数量
					String num = nums[i];
					if(!num.isEmpty()) {
					returnNum +=Integer.valueOf(num);
						}
					}
				}
			}
			ResultInfo result = new ResultInfo<>();
			if(warehouseGoodsOperateLogList.size()==0) {
				return fail(mav);
			}
			if(CollectionUtils.isNotEmpty(warehouseGoodsOperateLogList)) {
			result = warehouseGoodsOperateLogService.addWlogList(warehouseGoodsOperateLogList);
			}

			if (null != result && result.getCode() == 0) {
				mav.addObject("refresh", "false_true_false");// 是否关闭当前页，是否刷新当前页，是否刷新上层页面----三个参数为必传项
				if ("2".equals(type) || "3".equals(type)) {

					String str="./addWarehouseIn.do?afterSalesId=" + buyorderId + "&goodsId=" + goodsId
							+ "&afterSalesGoodsId=" + buyorderGoodsId + "&type=" + type + "&businessType="
							+ Integer.parseInt(businessType);
					if (rknum!=null && rknum!=""){
						str="./addWarehouseIn.do?afterSalesId=" + buyorderId + "&goodsId=" + goodsId
								+ "&afterSalesGoodsId=" + buyorderGoodsId + "&type=" + type + "&businessType="
								+ Integer.parseInt(businessType)+"&rknum="+Integer.parseInt(rknum);
					}
					mav.addObject("url",
							str);
				} else if("4".equals(type)) {//外借单
					LendOut lendout = lendOutMapper.selectByPrimaryKey(Integer.valueOf(buyorderId));

//					for(int i = 0;i < nums.length;i++) {
//						String num = nums[i];
//						if(!num.isEmpty()) {
//						returnNum +=Integer.valueOf(num);
//						}
//					}
					returnNum += lendout.getReturnNum();
					lendout.setReturnNum(returnNum);//返回数量
					if(returnNum.equals(lendout.getLendOutNum())) {
						lendout.setLendOutStatus(1);//状态完结
					}
					lendout.setModTime(DateUtil.sysTimeMillis());
					logger.info("外借入库更新外借单信息:",lendout.toString());
					int i = lendOutMapper.updateByPrimaryKeySelective(lendout);
					if(i==0) {
						return fail(mav);
					}

					mav.addObject("url", "./addWarehouseIn.do?afterSalesId="+lendout.getLendOutId()+"&goodsId="+lendout.getGoodsId()+
							"&afterSalesGoodsId="+lendout.getLendOutId()+"&type=4");
				}else{
					mav.addObject("url", "./addWarehouseIn.do?buyorderId=" + buyorderId + "&goodsId=" + goodsId
							+ "&buyorderGoodsId=" + buyorderGoodsId + "&type=" + type);
				}
				return success(mav);
			} else {
				return fail(mav);
			}
		}
		return null;
	}

	/**
	 * <b>Description:</b><br>
	 * 修改出入库日志是否有效状态
	 *
	 * @param wlogIds
	 * @param type
	 * @param session
	 * @return
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年8月24日 下午1:43:42
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/editIsEnableWlog")
	@SystemControllerLog(operationType = "edit", desc = "保存编辑出入库日志是否有效状态")
	public ResultInfo editIsEnableWlog(String wlogIds, String ywType, HttpSession session) {
		ResultInfo<?> result = new ResultInfo<>();
		// 获取session中user信息
		User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
		List<WarehouseGoodsOperateLog> wlogList = new ArrayList<WarehouseGoodsOperateLog>();
		if (null != wlogIds) {
			// 切割barcodeId拼接成的字符串
			String[] params = wlogIds.split("_");
			for (int i = 0; i < Math.min(5000,params.length); i++) {
				WarehouseGoodsOperateLog wlog = new WarehouseGoodsOperateLog();
				Integer wlogId = Integer.parseInt(params[i]);
				//redis分布式锁,出库记录id为key
				String key = ErpConst.WLOG+params[i];
				Boolean lock = redisUtils.tryGetDistributedLock(key, UUID.randomUUID().toString(), 300000);
				//判断此记录是否可撤销
				Integer isEnableFlag = warehouseGoodsOperateLogService.getWarehouseIsEnable(wlogId);
				if(lock && isEnableFlag.equals(1)) {
					//删除出库时的缓存数据,释放锁
					Barcode barcode = warehouseOutService.getBarcodeByWarehouseGoodsOperateLogId(wlogId);
					String  lockKey = ErpConst.RETURN_BARCODE+barcode.getBarcode();
					String requestId = redisUtils.get(lockKey);
					boolean releaselock = redisUtils.releaseDistributedLock(lockKey, requestId);
					wlog.setWarehouseGoodsOperateLogId(wlogId);
					if (ywType != null && "2".equals(ywType)) {
						wlog.setOperateType(3);
					} else if (ywType != null && "1".equals(ywType)) {
						wlog.setOperateType(1);
					}
					wlog = warehouseGoodsOperateLogService.updateIsActionGoods(wlog);
					wlog.setUpdater(session_user.getUserId());
					wlog.setModTime(DateUtil.sysTimeMillis());
					// 撤销入库记录
					wlog.setIsEnable(0);
					wlogList.add(wlog);
				}
			}
		}
		if(CollectionUtils.isEmpty(wlogList)){
		    result.setMessage("该记录已撤销,请刷新页面");
		    return result;
        }
		try {
			result = warehouseGoodsOperateLogService.editIsEnableWlog(wlogList);
		} catch (Exception e) {
			logger.error("editIsEnableWlog:", e);
		}
		return result;
	}

	/**
	 * <b>Description:</b><br>
	 * 修改出入库日志验收和复合状态
	 *
	 * @param wlogIds
	 * @param session
	 * @return
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年8月24日 下午2:29:48
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/editCheckStatusWlog")
	@SystemControllerLog(operationType = "edit", desc = "保存编辑出入库日志验收和复合状态")
	public ResultInfo editCheckStatusWlog(String wlogIds, HttpSession session, String beforeParams) {
		ResultInfo<?> result = new ResultInfo<>();
		// 获取session中user信息
		User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
		List<WarehouseGoodsOperateLog> wlogList = new ArrayList<WarehouseGoodsOperateLog>();
		if (null != wlogIds) {
			// 切割barcodeId拼接成的字符串
			String[] params = wlogIds.split("_");
			for (int i = 0; i < params.length; i++) {
				WarehouseGoodsOperateLog wlog = new WarehouseGoodsOperateLog();
				Integer wlogId = Integer.parseInt(params[i]);
				wlog.setWarehouseGoodsOperateLogId(wlogId);
				wlog.setOperateType(1);
				wlog.setUpdater(session_user.getUserId());
				wlog.setModTime(DateUtil.sysTimeMillis());
				// 验收入库记录
				wlog.setCheckStatus(1);
				wlog.setCheckStatusTime(DateUtil.sysTimeMillis());
				wlog.setCheckStatusUser(session_user.getUserId());
				wlogList.add(wlog);
			}
		}
		try {
			result = warehouseGoodsOperateLogService.editWlog(wlogList);
		} catch (Exception e) {
			logger.error("editCheckStatusWlog:", e);
		}
		return result;
	}

	/**
	 * <b>Description:</b><br>
	 * 入库记录
	 *
	 * @return
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年8月23日 下午4:36:27
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/warehouseInLogList")
	public ModelAndView warehouseInLogList(HttpServletRequest request,
			WarehouseGoodsOperateLog warehouseGoodsOperateLog,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo, // required
			// false:可不传入pageNo这个参数，true必须传入，defaultValue默认值，若无默认值，使用Page类中的默认值
			@RequestParam(required = false) Integer pageSize, HttpSession session,
			@RequestParam(required = false, value = "searchbeginTime") String searchBeginTime,
			@RequestParam(required = false, value = "searchendTime") String searchEndTime) {
		ModelAndView mv = new ModelAndView();
		Page page = getPageTag(request, pageNo, pageSize);
		// 获取session中user信息
		User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
		mv.addObject("searchbeginTime", searchBeginTime);
		mv.addObject("searchendTime", searchEndTime);
		if (searchBeginTime != null && !"".equals(searchBeginTime)) {
			warehouseGoodsOperateLog
					.setBeginTime(DateUtil.convertLong(searchBeginTime + " 00:00:00", DateUtil.TIME_FORMAT));
		}
		if (searchEndTime != null && !"".equals(searchEndTime)) {
			warehouseGoodsOperateLog
					.setEndTime(DateUtil.convertLong(searchEndTime + " 23:59:59", DateUtil.TIME_FORMAT));
		}
		if (StringUtil.isBlank(searchBeginTime)) {
			mv.addObject("searchbeginTime", DateUtil.getDayOfMonth(-1));
			warehouseGoodsOperateLog
					.setBeginTime(DateUtil.convertLong(DateUtil.getDayOfMonth(-1) + " 00:00:00", DateUtil.TIME_FORMAT));
		}
		if (StringUtil.isBlank(searchEndTime)) {
			mv.addObject("searchendTime", DateUtil.getNowDate(DateUtil.DATE_FORMAT));
			warehouseGoodsOperateLog.setEndTime(DateUtil
					.convertLong(DateUtil.getNowDate(DateUtil.DATE_FORMAT) + " 23:59:59", DateUtil.TIME_FORMAT));
		}
		// 物流部人员
		List<User> logisticsUserList = userService.getUserListByPositType(SysOptionConstant.ID_313,
				session_user.getCompanyId());
		mv.addObject("logisticsUserList", logisticsUserList);
		// 获取条码入库记录
		// 查询入库记录
		warehouseGoodsOperateLog.setOperateType(1);
		warehouseGoodsOperateLog.setIsEnable(1);
		warehouseGoodsOperateLog.setCompanyId(session_user.getCompanyId());
		Map<String, Object> wlog = null;
		try {
			wlog = warehouseGoodsOperateLogService.getWGOlogList(warehouseGoodsOperateLog, page);

			//新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
			List<WarehouseGoodsOperateLog> warehouseGoodsLogList = (List<WarehouseGoodsOperateLog>)wlog.get("list");
			if(!CollectionUtils.isEmpty(warehouseGoodsLogList)){
				List<Integer> skuIds = new ArrayList<>();
				warehouseGoodsLogList.stream().forEach(warehouseGoodsLog -> {
					skuIds.add(warehouseGoodsLog.getGoodsId());
				});
				List<Map<String,Object>> skuTipsMap = this.vgoodsService.skuTipList(skuIds);
				Map<String,Map<String,Object>> newSkuInfosMap = skuTipsMap.stream().collect(Collectors.toMap(key->key.get("SKU_NO").toString(), v -> v, (k, v) -> v));
				mv.addObject("newSkuInfosMap", newSkuInfosMap);
			}
			//新商品流切换 VDERP-1970 add by brianna 2020/3/16 end

			mv.addObject("list", warehouseGoodsLogList);
			mv.addObject("page", wlog.get("page"));
		} catch (Exception e) {
			logger.error("warehouseInLogList:", e);
		}
		mv.addObject("time", DateUtil.sysTimeMillis());

		mv.setViewName("logistics/warehousein/list_warehousein");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 效期管理列表
	 *
	 * @param request
	 * @param warehouseGoodsOperateLog
	 * @param pageNo
	 * @param pageSize
	 * @param session
	 * @return
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年8月29日 上午10:31:06
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/expirationDateList")
	public ModelAndView expirationDateList(HttpServletRequest request,
			WarehouseGoodsOperateLog warehouseGoodsOperateLog,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo, // required
			// false:可不传入pageNo这个参数，true必须传入，defaultValue默认值，若无默认值，使用Page类中的默认值
			@RequestParam(required = false) Integer pageSize, HttpSession session) {
		//Logger logger = LoggerFactory.getLogger(WarehouseInController.class);
		ModelAndView mv = new ModelAndView();
		Page page = getPageTag(request, pageNo, pageSize);
		// 获取session中user信息
		User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
		logger.info("获取session的user信息========");
//		if (null != warehouseGoodsOperateLog.getGoodsUserId() && warehouseGoodsOperateLog.getGoodsUserId() != -1) {
//			List<Integer> categoryIdList = userService
//					.getCategoryIdListByUserId(warehouseGoodsOperateLog.getGoodsUserId());
//			if (categoryIdList == null || categoryIdList.isEmpty()) {
//				categoryIdList.add(-1);
//			}
//			warehouseGoodsOperateLog.setCategoryIdList(categoryIdList);
//		}
		Map<String, Object> wlog = null;
		Integer expirationDateStatus = null;
		if (warehouseGoodsOperateLog.getExpirationDateStatus() == null) {
			expirationDateStatus = 1;
			warehouseGoodsOperateLog.setExpirationDateStatus(expirationDateStatus);
		} else {
			expirationDateStatus = warehouseGoodsOperateLog.getExpirationDateStatus();
		}
		try {
			warehouseGoodsOperateLog.setCompanyId(session_user.getCompanyId());
			logger.debug("获取效期列表=======");
			logger.info("获取效期列表=======");
			wlog = warehouseGoodsOperateLogService.getExpirationDateList(warehouseGoodsOperateLog, page);
			logger.info("获取效期列表end=======wlog.size()=====");
			logger.debug("获取效期列表end=======");


		} catch (Exception e) {
			logger.error("expirationDateList:", e);
		}
		// 产品部人员列表
		List<User> buyerList = userService.getUserByPositType(SysOptionConstant.ID_311, session_user.getCompanyId());
		mv.addObject("expirationDateStatus", expirationDateStatus);
		mv.addObject("time", DateUtil.sysTimeMillis());
		mv.addObject("list", wlog.get("list"));
		mv.addObject("page", wlog.get("page"));
		mv.addObject("buyerList", buyerList);
		mv.addObject("user", session_user);
		mv.setViewName("logistics/warehousein/list_expirationDate");
		return mv;

	}

	/**
	 * <b>Description:</b><br>
	 * 编辑效期
	 *
	 * @param request
	 * @param warehouseGoodsOperateLog
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年8月29日 上午10:31:31
	 */
	@ResponseBody
	@RequestMapping(value = "/editExpirationDate")
	public ModelAndView editExpirationDate(HttpServletRequest request,
			WarehouseGoodsOperateLog warehouseGoodsOperateLog,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo, // required
			// false:可不传入pageNo这个参数，true必须传入，defaultValue默认值，若无默认值，使用Page类中的默认值
			@RequestParam(required = false) Integer pageSize) {
		ModelAndView mv = new ModelAndView();
		Page page = getPageTag(request, pageNo, pageSize);
		Map<String, Object> wlog = null;
		try {
			wlog = warehouseGoodsOperateLogService.getExpirationDateList(warehouseGoodsOperateLog, page);
			List<WarehouseGoodsOperateLog> list = (List<WarehouseGoodsOperateLog>) wlog.get("list");
			// 去除条数大于0条，取第一条
			if (list.size() > 0) {
				mv.addObject("warehouseGoodsOperateLog", list.get(0));
				mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(list.get(0))));
			}
		} catch (Exception e) {
			logger.error("editExpirationDate:", e);
		}
		mv.setViewName("logistics/warehousein/edit_expirationDate");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 保存效期
	 *
	 * @param request
	 * @param warehouseGoodsOperateLogId
	 * @param batchNumber
	 * @param expirationDate
	 * @param session
	 * @return
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年8月29日 上午10:32:08
	 */
	@ResponseBody
	@RequestMapping(value = "/saveExpirationDate")
	@SystemControllerLog(operationType = "edit", desc = "保存编辑效期")
	public ResultInfo saveExpirationDate(HttpServletRequest request, Integer warehouseGoodsOperateLogId,
			String batchNumber, String expirationDate, HttpSession session) {
		ResultInfo<?> result = new ResultInfo<>();
		// 获取session中user信息
		User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
		List<WarehouseGoodsOperateLog> wlogList = new ArrayList<WarehouseGoodsOperateLog>();
		WarehouseGoodsOperateLog wlog = new WarehouseGoodsOperateLog();
		wlog.setWarehouseGoodsOperateLogId(warehouseGoodsOperateLogId);
		wlog.setBatchNumber(batchNumber);
		wlog.setExpirationDate(DateUtil.convertLong(expirationDate, DateUtil.DATE_FORMAT));
		wlog.setUpdater(session_user.getUserId());
		wlog.setModTime(DateUtil.sysTimeMillis());
		wlogList.add(wlog);
		try {
			result = warehouseGoodsOperateLogService.editWlog(wlogList);
		} catch (Exception e) {
			logger.error("saveExpirationDate:", e);
		}
		return result;
	}

	/**
	 * <b>Description:</b><br>
	 * 新增快递
	 *
	 * @param session
	 * @param buyorderVo
	 * @return
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年8月29日 下午3:54:40
	 */
	@FormToken(save = true)
	@ResponseBody
	@RequestMapping(value = "/addExpress")
	public ModelAndView addExpress(HttpSession session, BuyorderVo buyorderVo) {
		ModelAndView mv = new ModelAndView();
		// 获取session中user信息
		User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
		BuyorderVo buyorderInfo = buyorderService.getBuyorderVoDetail(buyorderVo, session_user);
		// 物流信息
		Express express = new Express();
		express.setBusinessType(SysOptionConstant.ID_515);
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		List<Integer> relatedIds = new ArrayList<Integer>();
		for (BuyorderGoodsVo buyorderGoodsVo : buyorderInfo.getBuyorderGoodsVoList()) {
			// 拼接关联ID的组
			relatedIds.add(buyorderGoodsVo.getBuyorderGoodsId());
			// 准备计算所有快递单中产品数量
			map.put(buyorderGoodsVo.getBuyorderGoodsId(), 0);
		}
		String nowTime = DateUtil.convertString(DateUtil.sysTimeMillis(), DateUtil.DATE_FORMAT);
		mv.addObject("nowTime", nowTime);
		express.setRelatedIds(relatedIds);
		List<Express> expressList = new ArrayList<>();
		try {
			if (relatedIds != null && relatedIds.size() > 0) {
				expressList = expressService.getExpressList(express);
				if (null != expressList) {
					for (Express e : expressList) {
						if (null != e.getExpressDetail()) {
							// 循环计算每件产品发货数量
							for (ExpressDetail ed : e.getExpressDetail()) {
								Integer num = 0;
								num = (Integer) map.get(ed.getRelatedId());
								num = num + ed.getNum();
								map.put(ed.getRelatedId(), num);
							}
						}
					}
				}
			}
			mv.addObject("expressNumMap", map);
			mv.addObject("expressList", expressList);
		} catch (Exception e) {
			logger.error("warehourse addExpress:", e);
		}
		// 获取物流公司列表
		List<Logistics> logisticsList = logisticsService.getLogisticsList(session_user.getCompanyId());
		mv.addObject("logisticsList", logisticsList);
		mv.addObject("buyorderInfo", buyorderInfo);
		mv.setViewName("logistics/warehousein/add_express");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 跳转快递编辑页面
	 *
	 * @param session
	 * @param buyorderVo
	 * @param express
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2018年1月9日 下午1:00:34
	 */
	@FormToken(save = true)
	@ResponseBody
	@RequestMapping(value = "/editExpress")
	public ModelAndView editExpress(HttpSession session, BuyorderVo buyorderVo, Express express) {
		ModelAndView mv = new ModelAndView();
		// 获取session中user信息
		User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
		BuyorderVo buyorderInfo = buyorderService.getBuyorderVoDetail(buyorderVo, session_user);
		List<Integer> relatedIds = new ArrayList<Integer>();
		// 物流信息
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		Map<Integer, Object> oldmap = new HashMap<Integer, Object>();
		Express oldExpress = new Express();
		oldExpress.setBusinessType(SysOptionConstant.ID_515);
		for (BuyorderGoodsVo buyorderGoodsVo : buyorderInfo.getBuyorderGoodsVoList()) {
			// 拼接关联ID的组
			relatedIds.add(buyorderGoodsVo.getBuyorderGoodsId());
			// 准备计算所有快递单中产品数量
			map.put(buyorderGoodsVo.getBuyorderGoodsId(), 0);
			oldmap.put(buyorderGoodsVo.getBuyorderGoodsId(), 0);
		}
		oldExpress.setRelatedIds(relatedIds);
		try {
			List<Express> expressList = expressService.getExpressList(express);
			if (relatedIds != null && relatedIds.size() > 0) {
				List<Express> oldExpressList = expressService.getExpressList(oldExpress);
				if (null != oldExpressList) {
					for (Express e : oldExpressList) {
						if (null != e.getExpressDetail()) {
							// 循环计算每件产品发货数量
							for (ExpressDetail ed : e.getExpressDetail()) {
								Integer num = 0;
								num = (Integer) oldmap.get(ed.getRelatedId());
								num = num + ed.getNum();
								oldmap.put(ed.getRelatedId(), num);
							}
						}
					}
				}
				if (null != expressList.get(0)) {
					if (null != expressList.get(0).getExpressDetail()) {
						// 循环计算每件产品发货数量
						for (ExpressDetail ed : expressList.get(0).getExpressDetail()) {
							Integer num = 0;
							num = (Integer) map.get(ed.getRelatedId());
							num = num + ed.getNum();
							map.put(ed.getRelatedId(), num);
						}
					}
				}
			}
			mv.addObject("allExpressNumMap", oldmap);
			mv.addObject("expressNumMap", map);
			mv.addObject("expressList", expressList.get(0));
		} catch (Exception e) {
			logger.error("warehourse editExpress:", e);
		}
		// 获取物流公司列表
		List<Logistics> logisticsList = logisticsService.getLogisticsList(session_user.getCompanyId());
		mv.addObject("logisticsList", logisticsList);
		mv.addObject("buyorderInfo", buyorderInfo);
		mv.setViewName("logistics/warehousein/edit_express");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 保存快递信息
	 *
	 * @param express
	 * @param deliveryTimes
	 * @param amount
	 * @param id_num_price
	 * @param session
	 * @return
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年8月30日 下午1:23:53
	 */
	@MethodLock(className = Integer.class)
	@FormToken(remove = true)
	@ResponseBody
	@RequestMapping(value = "/saveExpress")
	@SystemControllerLog(operationType = "edit", desc = "保存或编辑快递信息")
	public ResultInfo saveExpress(Express express, String deliveryTimes, BigDecimal amount, String id_num_price, Saleorder saleOrder,
		 String flag,  @MethodLockParam  Integer orderId, HttpSession session, String beforeParams, String Identifier, String delLogisticsNo) {

		ResultInfo<?> result = new ResultInfo<>();

		// 获取session中user信息
		User session_user = (User) session.getAttribute(ErpConst.CURR_USER);

		// 准备express中的expressDetailList
		List<ExpressDetail> expressDetailList = new ArrayList<ExpressDetail>();
		LogisticsOrderData  logisticsOrderData=new LogisticsOrderData();
		List<LogisticsOrderGoodsData> LogisticsOrderGoodsDataList=new ArrayList<>();
		int countNum = 0;
		// add by frnalin.wu for[统计快递单只呢个商量数量] at 2019-06-19 begin
		// 统计当前快递单中 商品数量
		int exPressSkuNum = 0;
		// add by frnalin.wu for[统计快递单只呢个商量数量] at 2019-06-19 end
		//-----------票货同行------------
		boolean goodsPeerFlag = false;
		if(saleOrder.getSaleorderId() != null && "1".equals(flag)) {
			goodsPeerFlag = invoiceService.getOrderIsGoodsPeer(saleOrder.getSaleorderId());
		}
		List<InvoiceApplyDetail> invoiceApplyDetails = new ArrayList<>();


		//判断是否第一次物流
		Integer tradeFirst=null;
		String hcTwo=null;
		Saleorder saleorderfirst=null;
		try {
			if(saleOrder.getSaleorderId() != null && "1".equals(flag)) {
				saleorderfirst = saleorderService.getsaleorderbySaleorderId(saleOrder.getSaleorderId());
				List<Express> first = expressService.getFirst(saleorderfirst.getTraderId());
				tradeFirst = first.size();
				hcTwo = saleorderfirst.getSaleorderNo().substring(0, 2);
			}
		}catch (Exception e){
			logger.error("判断第一次物流发生异常", e);
		}

		/*if (CommonConstants.SUCCESS_CODE.equals(i1) && "HC".equals(s1) && CommonConstants.SUCCESS_CODE.equals(result.getCode())){
			result.setMessage(result.getMessage()+",该订单首次发货时，请随货邮寄资质清单");
			*//*first.get(0).setLogisticsComments(first.get(0).getLogisticsComments()+"首次合作客户，请随货邮寄资质清单");*//*
			saleorderService.updateLogisticsComments(saleorder1.getSaleorderId(),saleorder1.getLogisticsComments()+"首次合作客户，请随货邮寄资质清单");
		}*/


		//-----------票货同行------------
		if (null != id_num_price) {
			// 切割RelatedId和num拼接成的字符串
			String[] params = id_num_price.split("_");
			// 单价
			Integer price = 0;
			// 所有产品总价
			Integer allPrice = 0;
			// 已经分配过的金额
			Double allAmount = 0.00;
			// 每种产品平摊的运费
			Double expressDetailAmount = 0.00;
			if (null != params) {
				for (String s : params) {
					String[] bid_num = s.split("\\|");
					if (null != bid_num[1] && null != bid_num[2]) {
						// 数量*金额
						allPrice += Integer.parseInt(bid_num[1]) * Double.valueOf(bid_num[2]).intValue();
					}
				}
				for (int j = 0; j < params.length; j++) {
					String[] bid_num = params[j].split("\\|");
					ExpressDetail expressDetail = new ExpressDetail();
					LogisticsOrderGoodsData logisticsOrderGoodsData=new LogisticsOrderGoodsData();
					InvoiceApplyDetail invoiceApplyDetail = new InvoiceApplyDetail();
					if (null != bid_num[0]) {
						// 关联字段
						expressDetail.setRelatedId(Integer.parseInt(bid_num[0]));
						invoiceApplyDetail.setDetailgoodsId(Integer.parseInt(bid_num[0]));
					}
					if (null != bid_num[1]) {
						Integer skuN = Integer.parseInt(bid_num[1]);
						if(skuN==0){
						    countNum+=1;
						    continue;
                        }
						// 数量
						expressDetail.setNum(skuN);
						invoiceApplyDetail.setNum(new BigDecimal(skuN));
						//放到rabbitmq数据
						logisticsOrderGoodsData.setNum(skuN);
						// 累计
						exPressSkuNum += skuN;
					}
					if (null != bid_num[2]) {
						// 单价
						price = Double.valueOf(bid_num[2]).intValue();
					}
					if("1".equals(flag)){
						if(null!=bid_num[3]){
							//放到rabbitmq数据
							logisticsOrderGoodsData.setSkuNo(bid_num[3]);
						}
					}
					invoiceApplyDetails.add(invoiceApplyDetail);
					// 最后一个产品时
					if (null != amount) {
						if (j == (params.length - 1)) {
							expressDetailAmount = amount.doubleValue() - allAmount;
						} else {
							// 运费价格*（产品单价*产品数量）/所有产品总价 保留两位小数
							allAmount += (double) Math.round(amount.doubleValue()
									* (Integer.parseInt(bid_num[1]) * Double.valueOf(bid_num[2]).intValue()) / allPrice
									* 100) / 100;
							expressDetailAmount = (double) Math.round(amount.doubleValue()
									* (Integer.parseInt(bid_num[1]) * Double.valueOf(bid_num[2]).intValue()) / allPrice
									* 100) / 100;
						}
						BigDecimal eda = new java.math.BigDecimal(expressDetailAmount);
						eda = eda.setScale(2, BigDecimal.ROUND_HALF_UP);

						// 金额
						expressDetail.setAmount(eda);
					}
					// 业务类型
					if ("1".equals(flag)) {
						// 销售
						expressDetail.setBusinessType(SysOptionConstant.ID_496);
					} else if ("2".equals(flag)) {
						// 售后
						expressDetail.setBusinessType(SysOptionConstant.ID_582);
					} else if("3".equals(flag)){
						//外借
						expressDetail.setBusinessType(SysOptionConstant.ID_660);
					}else{
						// 采购
						expressDetail.setBusinessType(SysOptionConstant.ID_515);
					}
					expressDetailList.add(expressDetail);
					//rabbitmq需要发送的商品数据
					LogisticsOrderGoodsDataList.add(logisticsOrderGoodsData);
				}
			}
		}
		// 业务类型
		if ("1".equals(flag)) {
			// 销售
			express.setBusinessType(SysOptionConstant.ID_496);
		} else if ("2".equals(flag)) {
			// 售后
			express.setBusinessType(SysOptionConstant.ID_582);
		}else if("3".equals(flag)){
			//外借
			express.setBusinessType(SysOptionConstant.ID_660);
		}
		// else if("3".equals(flag))
		// {
		// // 销售发票 -- 发票寄送
		// express.setBusinessType(SysOptionConstant.ID_497);
		// // 只有一个详情
		// ExpressDetail expressDetail = new ExpressDetail();
		// expressDetail.setAmount(express.getAmount());
		// expressDetail.setBusinessType(express.getBusinessType());
		// expressDetail.setExpressId(express.getExpressId());
		// expressDetail.setRelatedId(orderId);
		// // 暂时默认1
		// expressDetail.setNum(1);
		// expressDetailList.add(expressDetail);
		// }
		else {
			// 采购
			express.setBusinessType(SysOptionConstant.ID_515);
		}
		if(CollectionUtils.isEmpty(expressDetailList)){
			result.setMessage("所选商品的数量为0，不允许提交");
			return result;
		}
		if(express.getExpressId() == null) {
			express.setAddTime(DateUtil.sysTimeMillis());
		}
		express.setCreator(session_user.getUserId());
		express.setUpdater(session_user.getUserId());
		express.setModTime(DateUtil.sysTimeMillis());
		express.setDeliveryTime(DateUtil.convertLong(deliveryTimes, "yyyy-MM-dd"));
		express.setExpressDetail(expressDetailList);
		express.setIsEnable(1);
		express.setCompanyId(session_user.getCompanyId());
		express.setSaleorderId(orderId);
		try {
			//--------------票货同行---------
            boolean isupdatefalg = true;
            if(goodsPeerFlag && "编辑".equals(Identifier)) {
                isupdatefalg = expressService.isUpdateExpressAndInvoice(express);
                if(isupdatefalg){
                    //判断是否已开票
                    Boolean expressFlag = invoiceService.getInvoiceApplyByExpressId(express.getExpressId());
                    if(expressFlag){
                        result.setMessage("已开票,不允许编辑");
                        return result;
                    }
                }
            }
            if(goodsPeerFlag) {
                express.setTravelingByTicket(1);
            }
            if("1".equals(flag) && saleOrder.getSaleorderId() != null) {
				//数据校验避免超量提交
				List<SaleorderGoods> saleorderGoodsList = saleorderService.getSaleorderGoodsById(saleOrder);

				//获取商品信息
				Map<Integer, SaleorderGoods> saleorderGoodsMap = saleorderGoodsList.stream().collect(Collectors.toMap(SaleorderGoods::getSaleorderGoodsId, goods -> goods));
				Map<Integer, Integer> expressDetailMap = expressService.getExpressDetailNumInfo(saleOrder);
				for (ExpressDetail expressDetail : expressDetailList) {

					SaleorderGoods saleorderGoods = saleorderGoodsMap.get(expressDetail.getRelatedId());
					Integer oldNum = expressDetailMap.get(expressDetail.getRelatedId());
					if(oldNum == null){
						oldNum = 0;
					}
					if (!"编辑".equals(Identifier)) {
                        if (saleorderGoods.getDeliveryNum() < oldNum + expressDetail.getNum() && saleorderGoods.getNum() < oldNum + expressDetail.getNum()) {
                            result.setMessage("超出可快递数量!");
							result.setCode(-1);
                            return result;
                        }
                    }else{
						expressDetail.setExpressId(express.getExpressId());
					  ExpressDetail expressDetail1 = expressService.getExpressDetailNumByExpressId(expressDetail);
						Integer num = 0;
						if(expressDetail1 != null){
					  	num = expressDetail1.getNum();
					  }
						//编辑时将之前当前快递单数量减去
                        if (saleorderGoods.getDeliveryNum() < oldNum + expressDetail.getNum() - num &&
                                saleorderGoods.getNum() < oldNum + expressDetail.getNum() - num) {
                            result.setMessage("超出可快递数量!");
                            result.setCode(-1);
                            return result;
                        }
                    }
				}
			}


			//保存快递单信息
			result = expressService.saveExpress(express);
			// 销售
			if ("1".equals(flag) && result != null && result.getCode().equals(0)) {
				Express express1 = (Express) result.getData();
				//保存快递详情和出入库记录关联关系
				warehouseOutService.addExpressDeatilsWarehouse(express1);
			}
			if(result != null && result.getCode().equals(0) && goodsPeerFlag && isupdatefalg) {

                if(CollectionUtils.isNotEmpty(invoiceApplyDetails) && result.getData() != null) {
                    Express express1 = (Express) result.getData();
                    //编辑快递单时驳回之前申请
                    invoiceService.delInvoiceApplyByExpressId(express1.getExpressId());

					InvoiceApply invoiceApply = new InvoiceApply();

					invoiceApply.setRelatedId(saleOrder.getSaleorderId());
					//开票类型自动
					invoiceApply.setIsAuto(3);
					//是否提前开票 0否
					invoiceApply.setIsAdvance(0);
					//销售开票
					invoiceApply.setType(505);
					//申请方式 - 票货同行
					invoiceApply.setApplyMethod(2);
					//默认提前申请审核通过
					invoiceApply.setAdvanceValidStatus(0);
					//默认运营审核通过
					invoiceApply.setYyValidStatus(1);
					invoiceApply.setInvoiceApplyDetails(invoiceApplyDetails);
					invoiceApply.setCreator(session_user.getUserId());
					invoiceApply.setAddTime(DateUtil.gainNowDate());
					invoiceApply.setCompanyId(session_user.getCompanyId());
					invoiceApply.setUpdater(session_user.getUserId());
					invoiceApply.setModTime(DateUtil.gainNowDate());
					invoiceApply.setExpressId(express1.getExpressId());
					invoiceApply = invoiceService.updateInvoiceApplyInfo(invoiceApply);
					if(invoiceApply != null) {
						if(CollectionUtils.isNotEmpty(invoiceApply.getInvoiceApplyDetails())) {
							ResultInfo<?> resultInfo = invoiceService.saveOpenInvoceApply(invoiceApply);
							if (resultInfo != null && resultInfo.getCode().equals(0)) {
								result.setCode(0);
								result.setMessage("操作成功,请开具电子发票!");
							}else{
								log.error("票货同行发票保存失败 订单id:{},",orderId,resultInfo.getMessage());
								result.setCode(-1);
								result.setMessage("快递单保存成功,保存电子发票失败!失败原因:"+resultInfo.getMessage());
							}
						}
					}else{
						result.setCode(-1);
						result.setMessage("商品对应的发票已开出,请在订单详情页下载并随货寄出");
					}
                }
			}
			//判断是否开据发票
			//判断是否为待审核
			/*Boolean expressFlagByValidIsZero = invoiceService.getInvoiceApplyByExpressIdByValidIsZero(express.getExpressId())q;*/
			if (result != null && result.getCode().equals(0) && goodsPeerFlag){
				if (CollectionUtils.isNotEmpty(invoiceApplyDetails) && result.getData() != null) {
					//判断是否开据发票
					logger.info("开始判断是否开据发票");
					try {
						logger.info("该变是否开据发票状态");
						Express express1 = (Express) result.getData();
						expressService.updateIsinvoicing(express1.getExpressId());
					} catch (Exception e) {
						logger.error("无需与待开票判断发生异常", e);
					}
				}
			}



			//--------------票货同行---------

			if(null == result || !CommonConstants.SUCCESS_CODE.equals(result.getCode())) {
				logger.info("saveExpress 查询结果为空 orderId:{}",orderId);
				return new ResultInfo();
			}
			log.info("saveExpress rabbitmq发送快递信息 start-----orderId:{}",orderId);
			if(countNum == 0 && "1".equals(flag)) {
                //添加完成后向rabbitmq发送快递信息
                Saleorder saleorder = saleorderMapper.getWebAccountId(orderId);
                if (null != saleorder && saleorder.getOrderType() == 1) {
                    if (EmptyUtils.isNotBlank(express.getLogisticsName())) {
                        String logisticsCode = logisticsService.getLogisticsCode(express.getLogisticsName());
                        logisticsOrderData.setLogisticsCode(logisticsCode);
                    }
                    logisticsOrderData.setAddLogisticsNo(express.getLogisticsNo());
                    logisticsOrderData.setAccountId(saleorder.getWebAccountId());
                    logisticsOrderData.setOrderNo(saleorder.getSaleorderNo());
                    logisticsOrderData.setLogisticsType(SysOptionConstant.LOGISTICS_TYPE_1);
                    logisticsOrderData.setOrderGoodsLogisticsDataList(LogisticsOrderGoodsDataList);
                    logger.info("生产者开始发送消息=======" + JSON.toJSONString(logisticsOrderData));
                    if ("保存".equals(Identifier)) {
						logisticsOrderData.setDelLogisticsNo(express.getLogisticsNo());
                        logisticsOrderData.setType(SysOptionConstant.LOGISTICS_ADD_1);
                        msgProducer.sendMsg(RabbitConfig.MJX_ADDLOGISTICS_EXCHANGE, RabbitConfig.MJX_ADDLOGISTICS_ROUTINGKEY, JSON.toJSONString(logisticsOrderData));

                    }
                    if ("编辑".equals(Identifier)) {
						logisticsOrderData.setDelLogisticsNo(delLogisticsNo);
                        logisticsOrderData.setType(SysOptionConstant.LOGISTICS_UPDATE_2);
                        msgProducer.sendMsg(RabbitConfig.MJX_ADDLOGISTICS_EXCHANGE, RabbitConfig.MJX_ADDLOGISTICS_ROUTINGKEY, JSON.toJSONString(logisticsOrderData));
                    }
                    logger.info("生产者开始发送消息完毕=======" + JSON.toJSONString(logisticsOrderData));
                }
            }
			log.info("saveExpress rabbitmq发送快递信息 end-----orderId:{}",orderId);
//			if("3".equals(flag)) {//更新外借单数据
//				LendOut lendout = new LendOut();
//				lendout.setLendOutId(orderId);
//				lendout.setBusinessType(660);
//				Integer eNum = lendOutMapper.getKdNum(lendout);//已快递出库数量
//				lendout.setDeliverNum(eNum);
//				lendout.setModTime(DateUtil.sysTimeMillis());
//				int i = lendOutMapper.updateByPrimaryKeySelective(lendout);
//				return result;
//			}
			// 耗材商城订单物流信息推送
			Express ex = (Express) result.getData();
			log.info("saveExpress 耗材商城订单物流信息推送---  start--orderId:{}",orderId);
			if(null == ex) {
				logger.info("saveExpress 物流消息结果为空 orderId:{}",orderId);
				result.setMessage(" 物流消息结果为空");
				return result;
			}
			log.info("saveExpress 耗材商城订单物流信息推送--- Express",ex.getExpressId());
			Saleorder saleOrderFr = null;
			if("1".equals(flag)) {
				saleOrderFr = new Saleorder();
				saleOrderFr.setSaleorderId(ex.getSaleorderId());
				saleOrderFr = saleorderService.getBaseSaleorderInfo(saleOrderFr);
			}
			final Saleorder saleOrderWx = saleOrderFr;
			logger.info("saleOrderWx | orderType :{}", null == saleOrderWx ? "null" : saleOrderWx.getOrderType());
			if (SysOptionConstant.ID_496.equals(ex.getBusinessType()) && CommonConstants.ONE.equals(session_user.getCompanyId())) {
				// 同步
				if (ex.getExpressId() != null && ex.getExpressId() > 0) {
					vedengSoapService.expressSync(ex.getExpressId());
					vedengSoapService.orderSyncWeb(orderId);
					// 上传供货单
					String sendMeinianOrders = warehouseInService.sendMeinianOrders(ex);

				}
				if(null != saleOrderWx) {

					new Thread() {// 异步推送数据
						@Override
						public void run() {
							if (OrderConstant.ORDER_TYPE_HC.equals(saleOrderWx.getOrderType())) {// 如果是耗材商城的订单
								// 根据快递单查询该快递单下的商品列表
								Map<String, Object> map = new HashMap<String, Object>();
								map.put("saleOrder", saleOrderWx);
								try {
									// 查询该快递单下的商品信息列表
									List<SaleorderGoods> goodsList = expressService
											.getSaleorderGoodsListByexpressId(ex.getExpressId());
									map.put("goodsList", goodsList);
									// 调用ERP推送到耗材的接口
									hcSaleorderService.putExpressToHC(map);

								} catch (Exception e) {
									logger.error("调用ERP推送到耗材的接口 | 发生异常", e);
								}

							}
						}
					}.start();
					/*****************************微信发货提醒START**************************************/

					try {
						new Thread() {// 异步推送数据
							@Override
							public void run() {
								// 根据订单的Id查询订单信息，如果是耗材的销售订单，需要退物流信息以及订单的状态
								express.setExpressId(ex.getExpressId());
								// 微信查询数据 查询结果
								Map<String, String> wxSendMap = expressService.sendForExpress(saleOrderWx, express);
								// 贝登精选
								expressService.sendWxMessageForExpress(saleOrderWx, express, wxSendMap);

								// 医械购 发送模板消息开关
								if(CommonConstants.ONE.equals(sendYxgWxTempMsgFlag)) {
									// 医械购
									sendTemplateMsgHcForShip(saleOrderWx, wxSendMap);
								}

								if(!express.getLogisticsId().equals(4)&&!express.getLogisticsId().equals(7)&&!express.getLogisticsId().equals(8)){
									//微信公共号
									sendvxService(saleOrderWx,wxSendMap);
								}


							}
						}.start();

					} catch (Exception e) {
						logger.error("微信发货提醒 | 发生异常", e);
					}
					/*****************************微信发货提醒END**************************************/

					/*****************************小医院订单推送START***************************************/
					if (saleOrderWx.getSaleorderNo() != null && saleOrderWx.getSaleorderNo().startsWith("EL") && express.getExpressDetail().size() > 0){

						Express expressDto = (Express)result.getData();
						try{
							elSaleOrderService.sendElWareHouseInfo(expressDto,saleOrderWx);
						}catch (Exception e){
							logger.error("小医院订单物流信息推送失败，发生异常",e);
						}
					}
					/*****************************小医院订单推送END***************************************/
				}
			}

		} catch (Exception e) {
			logger.error("warehourse saveExpress:", e);
			result.setCode(-1);
			result.setMessage("操作失败");
			return result;
		}
		//判断是否第一次物流
		/*Saleorder saleorder1=saleorderService.getsaleorderbySaleorderId(saleOrder.getSaleorderId());
		List<Express> first=expressService.getFirst(saleorder1.getTraderId());
		Integer i=first.size();
		String s=saleorder1.getSaleorderNo().substring(0,2);*/
		if (CommonConstants.SUCCESS_CODE.equals(tradeFirst) && "HC".equals(hcTwo) && CommonConstants.SUCCESS_CODE.equals(result.getCode())){
			result.setMessage(result.getMessage()+",该订单首次发货时，请随货邮寄资质清单");
			//*first.get(0).setLogisticsComments(first.get(0).getLogisticsComments()+"首次合作客户，请随货邮寄资质清单");*//*
            //如果为空，null不显示在前端
            if(saleorderfirst.getLogisticsComments()==null){
                saleorderService.updateLogisticsComments(saleorderfirst.getSaleorderId(),"首次合作客户，请随货邮寄资质清单");
            }else {
                saleorderService.updateLogisticsComments(saleorderfirst.getSaleorderId(), saleorderfirst.getLogisticsComments() + "  首次合作客户，请随货邮寄资质清单");
            }
		}

		//快递单关联不到发票
		/*Boolean isFlag=invoiceService.getInvoiceApplyByExpressIdNo(express.getExpressId());*/
		//快递单关联的都为不通过发票
		/*Boolean allFaileInvoice= invoiceService.getInvoiceApplyByExpressIdFaile(express.getExpressId());
		if (result != null && result.getCode().equals(0)) {
			if ((isFlag) || (allFaileInvoice)) {
				logger.info("开始判断是否更改开据发票为无需开票");
				try {
					logger.info("该变是否开据发票状态");
					Express express1 = (Express) result.getData();
					expressService.updateIsinvoicingNo(express1.getExpressId());
				} catch (Exception e) {
					logger.error("无需与待开票判断发生异常", e);
				}
			}
		}*/
		return result;
	}

    /**
	 * <b>Description:</b><br>
	 * 打印入库单
	 *
	 * @param saleorder
	 * @return
	 * @throws ShowErrorMsgException
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年9月8日 下午1:59:22
	 */
	@ResponseBody
	@RequestMapping(value = "/printInOrder")
	public ModelAndView printInOrder(HttpServletRequest request, HttpSession session, String wdlIds, Integer type_f,
			BuyorderVo buyorderVo) throws ShowErrorMsgException {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView();
		// 公司
		mv.addObject("companyName", user.getCompanyName());

		// 售后
		if (buyorderVo.getBussinessType() != 0) {
			// 546 547 采购
			if (buyorderVo.getBussinessType() == 546 || buyorderVo.getBussinessType() == 547) {
				Buyorder b = new Buyorder();
				b.setBuyorderId(buyorderVo.getOrderId());
				BuyorderVo bv = buyorderService.getBuyorderVoDetail(b, user);
				mv.addObject("buyorder", bv);
				// 供应商
				mv.addObject("traderName", bv.getTraderName());
				// 公司地址
				mv.addObject("traderAddress", bv.getTraderAddress());
				// 到货日期
				mv.addObject("arrivalTime", bv.getArrivalTime());
				// 联系人
				mv.addObject("contactName", bv.getTraderContactName());
				// 单号
				mv.addObject("bussinessNo", bv.getBuyorderNo());
				// 联系电话
				mv.addObject("contactMobile", bv.getTraderContactMobile());
				// 手机号
				mv.addObject("contactTelephone", bv.getTraderContactTelephone());
			}
			// 销售
			else {
				Saleorder s = new Saleorder();
				s.setSaleorderId(buyorderVo.getOrderId());
				Saleorder sd = saleorderService.getBaseSaleorderInfo(s);
				mv.addObject("saleorder", sd);
				mv.addObject("contactName", sd.getTraderContactName());
				mv.addObject("arrivalTime", sd.getArrivalTime());
				mv.addObject("traderName", sd.getTraderName());
				mv.addObject("traderAddress", sd.getTraderAddress());
				mv.addObject("bussinessNo", sd.getSaleorderNo());
				mv.addObject("contactName", sd.getTakeTraderContactName());
				mv.addObject("contactMobile", sd.getTakeTraderContactMobile());
				// 手机号
				mv.addObject("contactTelephone", sd.getTakeTraderContactTelephone());
			}
		} else { // 普通入库
			Buyorder b = new Buyorder();
			b.setBuyorderId(buyorderVo.getBuyorderId());
			BuyorderVo bv = buyorderService.getBuyorderVoDetail(b, user);
			mv.addObject("buyorder", bv);
			mv.addObject("arrivalTime", bv.getArrivalTime());
			mv.addObject("traderName", bv.getTraderName());
			mv.addObject("traderAddress", bv.getTraderAddress());
			mv.addObject("bussinessNo", bv.getBuyorderNo());
			// 联系人
			mv.addObject("contactName", bv.getTraderContactName());
			mv.addObject("contactMobile", bv.getTraderContactMobile());
			// 手机号
			mv.addObject("contactTelephone", bv.getTraderContactTelephone());
		}

		List<WarehouseGoodsOperateLog> woList = new ArrayList<WarehouseGoodsOperateLog>();
		// 根据出库id获取出库信息
		if (null != wdlIds) {
			String[] p = wdlIds.split("#");
			// 切割拼接成的字符串
			/*
			 * String value = StringUtil.removeStrBeginStrOrEndStr(p[0], "_"); value =
			 * StringUtil.repaceAll(value, "_", ",");
			 */
			// 字符串转化
			String value = "";
			if (p != null) {
				String[] ids = p[0].split("_");
				for (int i = 0; i < ids.length; i++) {
					if (i != (ids.length - 1)) {
						value += ids[i] + ",";
					} else {
						value += ids[i];
					}
				}
			}
			WarehouseGoodsOperateLog w = new WarehouseGoodsOperateLog();
			w.setCreatorNm(value);
			w.setYwType(type_f);
			woList = warehouseGoodsOperateLogService.getWLById(w);

		}

		Long time = 0L;
		// 记录本页数量
		int pageTotalNum = 0;
		BigDecimal pageTotalPrice = new BigDecimal(0.00);
		BigDecimal zioe = pageTotalPrice;
		WarehouseGoodsOperateLog wLog = new WarehouseGoodsOperateLog();
		for (WarehouseGoodsOperateLog wl : woList) {
			if (null == wl)
				continue;
			pageTotalNum += wl.getNum();
			if (null != wl.getPrice()) {
				pageTotalPrice = pageTotalPrice.add(wl.getPrice().multiply(new BigDecimal(wl.getNum())));
			}
			if (wl.getAddTime() > time) {
				time = wl.getAddTime();

				wLog.setCreator(wl.getCreator());
			}
		}

		User u = userService.getUserById(wLog.getCreator());
		mv.addObject("outName", u.getUsername());
		mv.addObject("userName", user.getUsername());
		mv.addObject("woList", woList);
		// mv.addObject("type", p[1]);
		mv.addObject("bussinessType", buyorderVo.getBussinessType());

		if (user.getCompanyId() == 1) {
			mv.setViewName("logistics/warehousein/print_in_order");
		}
		// 分公司
		else {
			// 当前页的 总价格
			mv.addObject("pageTotalPrice", pageTotalPrice.compareTo(zioe) > 0 ? pageTotalPrice : null);
			// 大写汉字 金额总数
			mv.addObject("chineseNumberTotalPrice",
					pageTotalPrice.compareTo(zioe) > 0
							? DigitToChineseUppercaseNumberUtils.numberToChineseNumber(pageTotalPrice)
							: null);
			// 当前页的计数 总数
			mv.addObject("pageTotalNum", pageTotalNum);
			mv.setViewName("logistics/warehousein/print_in_order_companyId_5");
		}
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 根据厂商条码获取入库记录
	 *
	 * @param request
	 * @param warehouseGoodsOperateLog
	 * @param session
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2017年12月5日 上午10:35:45
	 */
	@ResponseBody
	@RequestMapping(value = "/getBarcodeFactory")
	public ResultInfo<List<WarehouseGoodsOperateLog>> getBarcodeFactory(HttpServletRequest request,
			WarehouseGoodsOperateLog warehouseGoodsOperateLog, HttpSession session) {
		User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
		ResultInfo<List<WarehouseGoodsOperateLog>> resultInfo = new ResultInfo<List<WarehouseGoodsOperateLog>>();
		warehouseGoodsOperateLog.setCompanyId(session_user.getCompanyId());
		List<WarehouseGoodsOperateLog> list = warehouseGoodsOperateLogService
				.getWLByBarcodeFactory(warehouseGoodsOperateLog);
		if (null != list) {
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
			resultInfo.setData(list);
		}
		return resultInfo;
	}

	/**
	 * <b>Description:</b><br>
	 * 设置效期天数
	 *
	 * @param request
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年12月6日 上午10:46:01
	 */
	@ResponseBody
	@RequestMapping(value = "/uploadexpirationday")
	public ModelAndView uploadExpirationDay(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("logistics/warehousein/uplode_expiration_day");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 保存设置效期天数
	 *
	 * @param request
	 * @param session
	 * @param lwfile
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年12月6日 上午10:53:35
	 */
	@ResponseBody
	@RequestMapping("saveuploadexpirationday")
	@SystemControllerLog(operationType = "add", desc = "保存设置效期天数")
	public ResultInfo<?> saveUploadExpirationDay(HttpServletRequest request, HttpSession session,
			@RequestParam("lwfile") MultipartFile lwfile) {
		ResultInfo<?> resultInfo = new ResultInfo<>();
		try {
			User user = (User) session.getAttribute(Consts.SESSION_USER);
			// 临时文件存放地址
			String path = request.getSession().getServletContext().getRealPath("/upload/logistics");
			FileInfo fileInfo = ftpUtilService.fileUploadServe(path, lwfile);
			if (fileInfo.getCode() == 0) {
				List<GoodsExpirationWarnVo> list = new ArrayList<>();
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
					GoodsExpirationWarnVo goodsExpirationWarn = new GoodsExpirationWarnVo();

					if (user != null) {
						goodsExpirationWarn.setCompanyId(user.getCompanyId());
						goodsExpirationWarn.setUpdater(user.getUserId());
						goodsExpirationWarn.setModTime(DateUtil.gainNowDate());
					}
					Integer areaId = 0;
					String areaIds = "";
					for (int cellNum = startCellNum; cellNum <= endCellNum; cellNum++) {// 循环列数（下表从0开始）
						Cell cell = row.getCell(cellNum);

						if (cellNum == 0) {// 第一列数据cellNum==startCellNum
							// 若excel中无内容，而且没有空格，cell为空--默认3，空白
							if (cell == null || cell.getCellType() != 1) {
								resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列订货号错误！");
								throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列订货号错误！");
							} else {
								goodsExpirationWarn.setSku(cell.getStringCellValue().toString());
							}
						}

						if (cellNum == 1) {// 第二列数据cellNum==(startCellNum+1)
							// 若excel中无内容，而且没有空格，cell为空--默认3，空白
							if (cell == null) {
								resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列提前预警天数不允许为空！");
								throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列提前预警天数不允许为空！");
							} else if (cell.getCellType() != 0 || cell.getNumericCellValue() <= 0
									|| cell.getNumericCellValue() % 1 != 0) {
								resultInfo.setMessage(
										"表格项错误，第" + (rowNum + 1) + "行第" + (cellNum + 1) + "列提前预警天数只能为大于0的整数！");
								throw new Exception(
										"表格项错误，第" + (rowNum + 1) + "行第" + (cellNum + 1) + "列提前预警天数只能为大于0的整数！");
							} else if (cell.getNumericCellValue() > 1000) {
								resultInfo.setMessage(
										"表格项错误，第" + (rowNum + 1) + "行第" + (cellNum + 1) + "列提前预警天数数量不得超过1000！");
								throw new Exception(
										"表格项错误，第" + (rowNum + 1) + "行第" + (cellNum + 1) + "列提前预警天数数量不得超过1000！");
							} else {
								goodsExpirationWarn.setDay((int) cell.getNumericCellValue());
							}
						}
					}

					list.add(goodsExpirationWarn);
				}

				// 保存更新
				resultInfo = warehouseGoodsOperateLogService.saveUploadExpirationDay(list);
			}
		} catch (Exception e) {
			logger.error("saveuploadexpirationday:", e);
			return resultInfo;
		}
		return resultInfo;
	}

	/**
	 * <b>Description:</b><br>
	 * 批量入库初始化
	 *
	 * @param request
	 * @return
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2017年6月7日 下午6:22:59
	 */
	@ResponseBody
	@RequestMapping(value = "/batchAddWarehouseIn")
	public ModelAndView batchAddWarehouseIn(HttpServletRequest request, Integer orderId, Integer bussnissType,String skurknum) {
		ModelAndView mv = new ModelAndView("logistics/warehousein/batch_add_warehousein");
		mv.addObject("orderId", orderId);

		mv.addObject("skurknum",skurknum);
		mv.addObject("bussnissType", bussnissType);
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 批量保存入库信息（读取Excel）
	 *
	 * @param request
	 * @return
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2017年6月7日 下午6:22:59
	 */
	@ResponseBody
	@RequestMapping(value = "/batchSaveWarehousein")
	public ResultInfo<?> batchSaveWarehousein(HttpServletRequest request, HttpSession session,
			@RequestParam("rkfile") MultipartFile rkfile, Integer orderId, Integer bussnissType,String skurknum) {
		ResultInfo<?> resultInfo = new ResultInfo<>();
		List<WarehouseGoodsOperateLog> list = new ArrayList<>();


		try {
			User user = (User) session.getAttribute(Consts.SESSION_USER);
			// 临时文件存放地址
			String path = request.getSession().getServletContext().getRealPath("/upload/logistics");
			FileInfo fileInfo = ftpUtilService.fileUploadServe(path, rkfile);
			if (fileInfo.getCode() == 0) {

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
					WarehouseGoodsOperateLog warehouseGoodsOperateLog = new WarehouseGoodsOperateLog();

					if (user != null) {
						warehouseGoodsOperateLog.setCompanyId(user.getCompanyId());
						warehouseGoodsOperateLog.setUpdater(user.getUserId());
						warehouseGoodsOperateLog.setModTime(DateUtil.gainNowDate());
						warehouseGoodsOperateLog.setAddTime(DateUtil.gainNowDate());
						warehouseGoodsOperateLog.setCreator(user.getUserId());
					}

					for (int cellNum = startCellNum; cellNum <= endCellNum; cellNum++) {// 循环列数（下表从0开始）
						Cell cell = row.getCell(cellNum);
						Integer type = cell.getCellType();
						if (cellNum == 0) {// 第一列数据cellNum==startCellNum
							// 若excel中无内容，而且没有空格，cell为空--默认3，空白
							if (cell == null || cell.getCellType() != 1) {
							    log.info("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列订货号错误！");
								resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列订货号错误！");
                                return resultInfo;
//								throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列订货号错误！");
							} else {
								warehouseGoodsOperateLog.setSku(cell.getStringCellValue().toString());
							}
						}

						if (cellNum == 6) {// 第七列数据cellNum==(startCellNum+1)
							// 若excel中无内容，而且没有空格，cell为空--默认3，空白
//							if (cell == null || cell.getStringCellValue().toString() == null
//									|| cell.getStringCellValue().toString() != null
//											&& cell.getStringCellValue().toString().equals("")) {
//							    logger.info("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "条码错误");
//								resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "条码错误");
//                                return resultInfo;
////								throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "条码错误！");
//							} else {
//								warehouseGoodsOperateLog.setBarcode(cell.getStringCellValue().toString());
//							}


							try{
								String cellValue="";
								if(cell!=null&&cell.getCellType()==Cell.CELL_TYPE_STRING){//字符串
									cellValue=cell.getStringCellValue();
								}else if(cell!=null&&cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
									cellValue=cell.getNumericCellValue()+"";
								}

								if (StringUtils.isBlank(cellValue)) {
									logger.info("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "条码错误");
									resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "条码错误");
									return resultInfo;
								} else {
									warehouseGoodsOperateLog.setBarcode(cellValue);
								}
							}catch(Exception e){
								resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "条码错误");
								logger.error("批量入库batchSaveWarehousein error:{}",e);
								return resultInfo;
							}


						}
						if (cellNum == 7) {// 第八列数据cellNum==(startCellNum+1)
							// 当类型为数字型
							String cellValue = "";
							if (cell != null && cell.getCellType() == 0) {
								DecimalFormat decimalFormat = new DecimalFormat("#.#");
								cellValue = decimalFormat.format((cell.getNumericCellValue()));
							} else {
								cellValue = cell.getStringCellValue().toString();
							}
							// 判断该数据不为空
							if (cellValue != null && !cellValue.equals("")) {
								warehouseGoodsOperateLog.setBatchNumber(cellValue);
							} else {
							    logger.info("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "批次号错误");
								resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "批次号错误");
                                return resultInfo;
//								throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "批次号错误！");
							}

						}

						if (cellNum == 8) {// 第九列数据cellNum==(startCellNum+1)
							// 当类型为数字型
							String cellValue = "";
							if (cell != null && cell.getCellType() == 0) {
								if (HSSFDateUtil.isCellDateFormatted(cell)) {
									// 日期类型
									double d = cell.getNumericCellValue();
									Date date = HSSFDateUtil.getJavaDate(d);
									SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
									cellValue = sFormat.format(date);
								} else {
									DecimalFormat decimalFormat = new DecimalFormat("#.#");
									cellValue = decimalFormat.format((cell.getNumericCellValue()));
								}
							} else {
								cellValue = cell.getStringCellValue().toString();
							}

							// 判断该数据不为空
							Long productDate = DateUtil.convertLong(cellValue, "yyyy-MM-dd");
							if (productDate != null && !productDate.equals("") && productDate != 0) {
								if ("9999-12-30".equals(cellValue)) {
									warehouseGoodsOperateLog.setProductDate(0L);
								} else {
									warehouseGoodsOperateLog.setProductDate(productDate);
								}
							} else {
								warehouseGoodsOperateLog.setProductDate(0L);
							}
						}

						if (cellNum == 9) {// 第十列数据cellNum==(startCellNum+1)
							// 当类型为数字型
							String cellValue = "";
							if (cell != null && cell.getCellType() == 0) {
								if (HSSFDateUtil.isCellDateFormatted(cell)) {
									// 日期类型
									double d = cell.getNumericCellValue();
									Date date = HSSFDateUtil.getJavaDate(d);
									SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
									cellValue = sFormat.format(date);
								} else {
									DecimalFormat decimalFormat = new DecimalFormat("#.#");
									cellValue = decimalFormat.format((cell.getNumericCellValue()));
								}
							} else {
								cellValue = cell.getStringCellValue().toString();
							}

							// 判断该数据不为空
							Long expirationDate = DateUtil.convertLong(cellValue, "yyyy-MM-dd");
							if (expirationDate != null && !expirationDate.equals("") && expirationDate != 0) {
								if ("9999-12-30".equals(cellValue)) {
									warehouseGoodsOperateLog.setExpirationDate(0L);
								} else {
									warehouseGoodsOperateLog.setExpirationDate(expirationDate);
								}
							} else {
							    logger.info("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "效期截止日期错误");
								resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "效期截止日期错误");
                                return resultInfo;
//								throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "效期截止日期错误！");
							}
						}

						if (cellNum == 10) {// 第十一列数据cellNum==(startCellNum+1)
							// 仓库
							// 若excel中无内容，而且没有空格，cell为空--默认3，空白
							row.getCell(cellNum).setCellType(Cell.CELL_TYPE_STRING);
							if (cell == null || cell.getStringCellValue().toString() == null
									|| cell.getStringCellValue().toString() != null
											&& cell.getStringCellValue().toString().equals("")) {
							    logger.info("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "仓库错误");
								resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "仓库错误");
                                return resultInfo;
//								throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "仓库错误！");
							} else {
								warehouseGoodsOperateLog.setWarehouseName(cell.getStringCellValue().toString());
							}
						}
						if (cellNum == 11) {// 第十二列数据cellNum==(startCellNum+1)
							// 库房
							row.getCell(cellNum).setCellType(Cell.CELL_TYPE_STRING);
							warehouseGoodsOperateLog.setStorageRoomName(cell.getStringCellValue().toString());
						}
						if (cellNum == 12) {
							// 货区
							row.getCell(cellNum).setCellType(Cell.CELL_TYPE_STRING);
							warehouseGoodsOperateLog.setStorageAreaName(cell.getStringCellValue().toString());
						}
						if (cellNum == 13) {
							// 货架
							row.getCell(cellNum).setCellType(Cell.CELL_TYPE_STRING);
							warehouseGoodsOperateLog.setStorageRackName(cell.getStringCellValue().toString());
						}
						if (cellNum == 14) {
							// 库位
							row.getCell(cellNum).setCellType(Cell.CELL_TYPE_STRING);
							warehouseGoodsOperateLog.setStorageLocationName(cell.getStringCellValue().toString());
						}
						if (cellNum == 15) {
							// 库位备注
							row.getCell(cellNum).setCellType(Cell.CELL_TYPE_STRING);
							warehouseGoodsOperateLog.setComments(cell.getStringCellValue().toString());
						}
					}
					// 入库
					if (bussnissType == 0) {
						warehouseGoodsOperateLog.setOperateType(1);
					} else if (bussnissType == 540) {
						warehouseGoodsOperateLog.setOperateType(3);
					} else if (bussnissType == 539) {
						warehouseGoodsOperateLog.setOperateType(5);
					} else if (bussnissType == 547) {
						warehouseGoodsOperateLog.setOperateType(8);
					}
					// 生效
					warehouseGoodsOperateLog.setIsEnable(1);
					// 数量
					warehouseGoodsOperateLog.setNum(1);
					if (bussnissType == 0) {
						warehouseGoodsOperateLog.setBuyorderId(orderId);
						warehouseGoodsOperateLog.setYwType(0);// 采购
					} else if (bussnissType == 539 || bussnissType == 540) {
						warehouseGoodsOperateLog.setAfterSalesId(orderId);
						warehouseGoodsOperateLog.setYwType(1);// 销售售后
					} else if (bussnissType == 547) {
						warehouseGoodsOperateLog.setAfterSalesId(orderId);
						warehouseGoodsOperateLog.setYwType(2);// 销售售后
					}
					list.add(warehouseGoodsOperateLog);
				}
				/*入库数量不得大于需入库数量*/
				/*if (rknum!=null){
					if (rknum>endRowNum){
						logger.info("入库数量不得超过需入库数量");
						resultInfo.setMessage("入库数量不得超过需入库数量");
						return resultInfo;
					}
				}*/
				for (WarehouseGoodsOperateLog warehouseGoodsOperateLog : list) {
					Integer barcodeIsEnable = warehouseGoodsOperateLogService.getBarcodeIsEnable(warehouseGoodsOperateLog, 1);
					if(barcodeIsEnable.equals(0)){
					    logger.info("表格错误，条码：" + warehouseGoodsOperateLog.getBarcode() + "已使用,请重新下载未入库条码！");
						resultInfo.setMessage("表格错误，条码：" + warehouseGoodsOperateLog.getBarcode() + "已使用,请重新下载未入库条码！");
						return resultInfo;
					}
				}
				/*入库数量不得大于需入库数量*/
				if (skurknum!=null && !"".equals(skurknum)){
					String[] str=skurknum.split(",");
					for (String skuandrknum:str) {
						if (skuandrknum!=null && !"".equals(skuandrknum)){
							String[] skurk=skuandrknum.split("-");
							if (skurk.length==2) {
								String sku = skurk[0];
								String rknum = skurk[1];
								Integer count = 0;
								for (int rowNum = startRowNum; rowNum <= endRowNum; rowNum++) {// 循环行数
									Row row = sheet.getRow(rowNum);
									int startCellNum = row.getFirstCellNum();// 起始列
									int endCellNum = row.getLastCellNum() - 1;// 结束列
									for (int cellNum = startCellNum; cellNum <= 0; cellNum++) {// 循环列数（下表从0开始）
										Cell cell = row.getCell(cellNum);
										if (cellNum == 0) {
											if (cell.getStringCellValue().toString().equals(skurk[0])) {
												count++;
											}

										}
									}
								}
								if (count > Integer.parseInt(skurk[1])) {
									logger.info("入库数量不得超过需入库数量");
									resultInfo.setMessage("入库数量不得超过需入库数量");
									return resultInfo;
								}
							}

						}
					}
				}

				ResultInfo<?> result = new ResultInfo<>();
				if (list.size() > 1500) {
					double count = list.size();
					double pageNum = 1500;
					Integer page = (int) Math.ceil(count / pageNum);
					for (Integer i = 0; i < page; i++) {
						List<WarehouseGoodsOperateLog> afterlist = new ArrayList<>();
						for (Integer j = 1500 * i; 1500 * (i + 1) > j && j < list.size(); j++) {
							afterlist.add(list.get(j));
						}
						// 保存更新
						resultInfo = warehouseGoodsOperateLogService.batchAddWarehouseinList(afterlist);
					}
				} else {
					// 保存更新
					resultInfo = warehouseGoodsOperateLogService.batchAddWarehouseinList(list);
				}
			}
		} catch (Exception e) {
			logger.error("batchSaveWarehousein:", e);
			return resultInfo;
		}finally {
			warehouseGoodsOperateLogService.releaseDistributedLock(list);
		}
		return resultInfo;
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/getWarehouseInBarcode")
	public ModelAndView getWarehouseInBarcode(HttpSession session, BuyorderGoods buyorderGoods, Integer type,
			@RequestParam(required = false, value = "searchBeginTime") String searchBeginTime,
			@RequestParam(required = false, value = "searchEndTime") String searchEndTime) {
		ModelAndView mv = new ModelAndView();
		User user = (User) session.getAttribute(Consts.SESSION_USER);
		mv.addObject("searchBeginTime", searchBeginTime);
		mv.addObject("searchEndTime", searchEndTime);

		// 有效二维码信息
		Barcode barcodeEnable = new Barcode();
		barcodeEnable.setType(type);// 设置条码类型 1采购 2售后
		barcodeEnable.setBuyorderId(buyorderGoods.getBuyorderId());
		barcodeEnable.setCompanyId(user.getCompanyId());
		if (searchBeginTime != null && !searchBeginTime.equals("")) {
			barcodeEnable.setSearchBegintime(DateUtil.convertLong(searchBeginTime, DateUtil.TIME_FORMAT));
		}
		if (searchEndTime != null && !searchEndTime.equals("")) {
			barcodeEnable.setSearchEndtime(DateUtil.convertLong(searchEndTime, DateUtil.TIME_FORMAT));
		}
		List<Barcode> barcodeEnableList;
		try {
			barcodeEnableList = warehouseInService.getWarehouseInBarcode(barcodeEnable);
			List<Barcode> supplementBarcode = new ArrayList<>();
			ResultInfo<?> result = new ResultInfo<>();
			for (Barcode bd : barcodeEnableList) {
				if (null == bd.getFtpPath() || "".equals(bd.getFtpPath())) {
					supplementBarcode.add(bd);
				}
			}
			if (supplementBarcode.size() > 1000) {
				double count = supplementBarcode.size();
				double pageNum = 1000;
				Integer page = (int) Math.ceil(count / pageNum);
				for (Integer i = 0; i < page; i++) {
					List<Barcode> barcodeInfoList = new ArrayList<>();
					for (Integer j = 1000 * i; 1000 * (i + 1) > j && j < supplementBarcode.size(); j++) {
						barcodeInfoList.add(supplementBarcode.get(j));
					}
					result = warehouseInService.supplementBarcode(barcodeInfoList);
				}
			} else if (supplementBarcode.size() > 0) {
				result = warehouseInService.supplementBarcode(supplementBarcode);
			}
			if (supplementBarcode.size() > 0) {
				barcodeEnableList = warehouseInService.getWarehouseInBarcode(barcodeEnable);
			}
			mv.addObject("barcodeList", barcodeEnableList);
			// 获取排重后的日期
			if (null != barcodeEnableList) {
				List<String> timeArray = new ArrayList<>();
				for (Barcode b : barcodeEnableList) {
					timeArray.add(DateUtil.convertString(b.getWarehouseInTime(), "YYYY.MM.dd"));
				}
				HashSet<String> tArray = new HashSet<String>(timeArray);
				mv.addObject("timeArray", tArray);
			}
		} catch (Exception e) {
			logger.error("getWarehouseInBarcode:", e);
		}
		mv.addObject("type", type);
		mv.addObject("buyorderId", buyorderGoods.getBuyorderId());
		mv.setViewName("logistics/warehousein/view_warehouseinbarcode");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 打印未到货单
	 *
	 * @param session
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2017年9月8日 下午1:59:22
	 */
	@ResponseBody
	@RequestMapping(value = "/printNoArrive")
	public ModelAndView printNoArrive(HttpSession session, HttpServletRequest request, Integer buyorderId,
			Integer type) {
		ModelAndView mv = new ModelAndView();
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		// 获取session中user信息
		User session_user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		// 采购
		if (type == 0) {
			BuyorderVo buyorderVo = new BuyorderVo();
			buyorderVo.setBuyorderId(buyorderId);
			BuyorderVo buyorderInfo = buyorderService.getBuyorderInDetail(buyorderVo, session_user);
			for (BuyorderGoodsVo bg : buyorderInfo.getBuyorderGoodsVoList()) {
				// 原来取得总数是num为了不影响其他功能，将num改为减去售后的值，num-afterSaleUpLimitNum
				bg.setNum(bg.getNum() - bg.getAfterSaleUpLimitNum());
			}
			mv.addObject("traderName", buyorderInfo.getTraderName());
			mv.addObject("orderNo", buyorderInfo.getBuyorderNo());
			mv.addObject("list", buyorderInfo.getBuyorderGoodsVoList());
		}
		// 售后
		else {
			AfterSales afterSales = new AfterSales();
			afterSales.setAfterSalesId(buyorderId);
			if (type == 539 || type == 540) {
				afterSales.setBusinessType(1);
			} else {
				afterSales.setBusinessType(2);
			}
			AfterSalesVo asv = afterSalesOrderService.getAfterSalesVoListById(afterSales);
			// 售后订单下的产品信息
			asv.setCompanyId(session_user.getCompanyId());
			if (asv.getType() == 539 || asv.getType() == 540) {
				asv.setBusinessType(1);
			} else {
				asv.setBusinessType(2);
			}
			asv.setIsIn(1);
			asv.setIsNormal(1);
			List<AfterSalesGoodsVo> asvList = afterSalesOrderService.getAfterSalesGoodsVoList(asv, session);
			mv.addObject("traderName", asv.getTraderName());
			mv.addObject("orderNo", asv.getAfterSalesNo());
			mv.addObject("list", asvList);
		}
		String time = DateUtil.getNowDate(DateUtil.TIME_FORMAT);
		mv.addObject("time", time);
		mv.addObject("companyName", user.getCompanyName());
		mv.addObject("companyId", user.getCompanyId());
		mv.setViewName("logistics/warehousein/print_no_arrive");
		return mv;
	}
	/**
	* @Description: TODO(商品归还入库列表页)
	* @param @return
	* @return ModelAndView
	* @author strange
	* @throws
	* @date 2019年9月16日
	*/
	@ResponseBody
	@RequestMapping("/lendOutIndex")
	public ModelAndView lendOutIndex(HttpServletRequest request, LendOut lendout,
		    @RequestParam(required = false, defaultValue = "1") Integer pageNo,
		    @RequestParam(required = false) Integer pageSize, HttpSession session) {
//		User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
		Page page = getPageTag(request, pageNo, pageSize);
		ModelAndView mv = new ModelAndView();
		if(lendout.getOverdue()!=null ) {//逾期归还
		Long time = DateUtil.sysTimeMillis();
		lendout.setOverdueTime(time);
		}
		lendout.setLendOutStatus(0);
		List<Barcode> barcodeList = null;
		Barcode barcode = new Barcode();
		Map<String, Object> map = warehouseInService.getlendoutListPage(request,lendout,page);
		List<LendOut> lendoutlist =(List) map.get("list");
		if(CollectionUtils.isNotEmpty(lendoutlist)) {
		for (LendOut lend : lendoutlist) {
			User user = userService.getUserById(lend.getCreator());
			lend.setCreatorName(user.getUsername());
			// 日期
			String returnTime = DateUtil.convertString(lend.getReturnTime(), "yyyy-MM-dd");
			lend.setReturnTimeStr(returnTime);
//			Integer deliveryNum = lendOutMapper.getdeliveryNum(lend);//出库数量
			Integer deliveryNum = warehouseOutService.getdeliveryNum(lend);
			lend.setDeliverNum(deliveryNum);
			// 有效的条码;
			try {
				barcode.setIsEnable(4);
				barcode.setDetailGoodsId(lend.getLendOutId());
				barcode.setType(4);
				barcodeList = warehouseInService.getBarcode(barcode);
				lend.setBarcodeNum(barcodeList.size());
			} catch (Exception e) {
				logger.error(Contant.ERROR_MSG, e);
				logger.info("商品归还入库列表页erro:",e);
			}
		}
		mv.addObject("lendoutlist", lendoutlist);


		//新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
		if(!CollectionUtils.isEmpty(lendoutlist)){

			List<Integer> goodsIds = new ArrayList<>();

			lendoutlist.stream().forEach(lendoutItem->{
				goodsIds.add(lendoutItem.getGoodsId());
			});

			if(CollectionUtils.isNotEmpty(goodsIds)){
				List<CoreSpuGenerate> spuLists = this.vgoodsService.findSpuNamesBySpuIds(goodsIds);
				Map<Integer,String> spuMap = spuLists.stream().collect(Collectors.toMap(k->k.getSpuId(), v -> v.getSpuName(), (k, v) -> v));
				mv.addObject("spuMap", spuMap);
			}
		}
		//新商品流切换 VDERP-1970 add by brianna 2020/3/16 end

		}
		mv.addObject("page", page);
		mv.addObject("lendout", lendout);
		mv.setViewName("logistics/warehousein/lendout_index");
		return mv;
	}
	/**
	* @Description: TODO(商品外借记录列表)
	* @param @return
	* @return ModelAndView
	* @author strange
	* @throws
	* @date 2019年9月19日
	*/
	@ResponseBody
	@RequestMapping("/lendoutListView")
	public ModelAndView lendoutListView(HttpServletRequest request, LendOut lendout,
		    @RequestParam(required = false, defaultValue = "1") Integer pageNo,
		    @RequestParam(required = false, defaultValue= "20") Integer pageSize, HttpSession session) {
//		User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
		Page page = getPageTag(request, pageNo, pageSize);
		lendout.setFlag(1);//区分和外借入库列表页的功能
		ModelAndView mv = new ModelAndView();
		Map<String, Object> map = warehouseInService.getlendoutListPage(request,lendout,page);

		List<LendOut> lendoutlist = new ArrayList<LendOut>();
		lendoutlist =(List) map.get("list");
		if(CollectionUtils.isNotEmpty(lendoutlist)) {
			for (LendOut lend : lendoutlist) {
				User user = userService.getUserById(lend.getCreator());
				lend.setCreatorName(user.getUsername());
				//发货状态
				if(lend.getDeliverNum()!=0 && lend.getLendOutNum()>lend.getDeliverNum()) {
					lend.setDeliveystatus(2);
				}else if(lend.getDeliverNum()!=0 && lend.getLendOutNum()==lend.getDeliverNum()){
					lend.setDeliveystatus(3);
				}else if(lend.getDeliverNum()==0) {
					lend.setDeliveystatus(1);
				}
			}
		}

		List<Integer> goodsIds = lendoutlist.stream().map(lendOut->lendOut.getGoodsId()).collect(Collectors.toList());
		if(CollectionUtils.isNotEmpty(goodsIds)){
			List<CoreSpuGenerate> spuLists = this.vgoodsService.findSpuNamesBySpuIds(goodsIds);
			Map<Integer,String> spuMap = spuLists.stream().collect(Collectors.toMap(k->k.getSpuId(), v -> v.getSpuName(), (k, v) -> v));
			mv.addObject("spuMap", spuMap);
		}

		mv.addObject("lendoutlist", lendoutlist);
		mv.addObject("page", page);
		mv.addObject("lendout", lendout);
		mv.setViewName("logistics/warehousein/lendout_listView_index");
		return mv;
	}


	private boolean checkDirectSaleorderGoods(Integer orderDetailId){
		if(orderDetailId==null){
			return false;
		}
		SaleorderGoodsGenerate saleorderGoodsGenerate=saleorderGoodsGenerateMapper.selectByPrimaryKey(orderDetailId);
		if(saleorderGoodsGenerate!=null&&OrderConstant.DELEVIRY_STATUS_1==saleorderGoodsGenerate.getDeliveryDirect()){
			return true;
		}
		return false;
	}
	/**
	*保存sku二维码
	* @Author:strange
	* @Date:17:55 2020-03-20
	*/
	@ResponseBody
	@RequestMapping("/saveSkuBarcode")
	public ResultInfo saveSkuBarcode(HttpServletRequest request, GoodsVo goodsVo){
		ResultInfo resultInfo = new ResultInfo();
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		List<Integer> goodsIds = goodsVo.getGoodsIds();
		goodsIds = removeNull(goodsIds);
		Boolean logisticeFlag = userService.getLogisticeFlagByUserId(user.getUserId());
		if(!logisticeFlag){
			resultInfo.setMessage("非物流账户不可生成");
			return resultInfo;
		}
		//筛选以生成过的sku
		List<Goods>  goodsList = warehouseInService.checkSkuBarcode(goodsIds);
		if(CollectionUtils.isNotEmpty(goodsList)) {
			resultInfo = warehouseInService.saveSkuBarcode(goodsList, user);
		}else{
			resultInfo.setMessage("选中记录中没有未生成的订货号，请勿重复操作");
		}
		return resultInfo;
	}
	/**
	*打印sku条码
	* @Author:strange
	* @Date:14:38 2020-03-23
	*/
	@ResponseBody
	@RequestMapping("/printSkuBarcode")
	public ModelAndView printSkuBarcode(HttpServletRequest request , GoodsVo goodsVo){
		ModelAndView mv = new ModelAndView();
//		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		List<Integer> goodsIds = goodsVo.getGoodsIds();
		goodsIds = removeNull(goodsIds);
		List<Attachment> attachmentList = warehouseInService.getSkuBarcodeList(goodsIds);
		Map<Integer, Attachment> map = new HashMap<>(16);
		if(CollectionUtils.isNotEmpty(attachmentList)) {
			map = attachmentList.stream().collect(Collectors.toMap(Attachment::getRelatedId, attachment -> attachment));
			mv.addObject("isprint",true);
		}

		mv.addObject("goodsIdList",goodsIds);
		mv.addObject("map",map);
		mv.setViewName("logistics/warehousein/print_skuBarcode");
		return mv;
	}

	private List<Integer> removeNull(List<Integer> goodsIds) {
		List<Integer> list = new ArrayList<>();
		list.add(null);
		goodsIds.removeAll(list);
		return goodsIds;
	}
}
