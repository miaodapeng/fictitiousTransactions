package com.vedeng.logistics.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.common.constants.Contant;
import com.lowagie.text.ExceptionConverter;
import com.rabbitmq.MsgProducer;
import com.rabbitmq.RabbitConfig;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.vedeng.authorization.model.Role;
import com.vedeng.common.annotation.MethodLock;
import com.vedeng.common.annotation.MethodLockParam;
import com.vedeng.common.constant.CommonConstants;
import com.vedeng.common.util.*;
import com.vedeng.finance.model.Invoice;
import com.vedeng.finance.model.InvoiceApply;
import com.vedeng.finance.service.InvoiceService;
import com.vedeng.goods.service.VgoodsService;
import com.vedeng.logistics.dao.ExpressMapper;
import com.vedeng.logistics.dao.LendOutMapper;
import com.vedeng.logistics.dao.WarehouseGoodsStatusMapper;
import com.vedeng.logistics.dao.WarehousePickingMapper;
import com.vedeng.logistics.model.*;
import com.vedeng.logistics.model.vo.WarehouseGoodsOperateLogVo;
import com.vedeng.logistics.service.*;
import com.vedeng.order.model.vo.SaleorderVo;
import com.vedeng.trader.model.vo.TraderCertificateVo;
import com.vedeng.trader.model.vo.TraderFinanceVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.Connection;
import com.vedeng.aftersales.model.AfterSales;
import com.vedeng.aftersales.model.AfterSalesDetail;
import com.vedeng.aftersales.model.AfterSalesGoods;
import com.vedeng.aftersales.model.vo.AfterSalesGoodsVo;
import com.vedeng.aftersales.model.vo.AfterSalesVo;
import com.vedeng.aftersales.service.AfterSalesService;
import com.vedeng.authorization.model.Organization;
import com.vedeng.authorization.model.User;
import com.vedeng.common.annotation.SystemControllerLog;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.exception.ShowErrorMsgException;
import com.vedeng.common.http.NewHttpClientUtils;
import com.vedeng.common.logisticmd.CallExpressService;
import com.vedeng.common.logisticmd.CallWaybillPrinter;
import com.vedeng.common.logisticmd.ZopExpressService;
import com.vedeng.common.model.FileInfo;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.putHCutil.service.HcSaleorderService;
import com.vedeng.common.redis.RedisUtils;
import com.vedeng.common.shiro.JedisUtils;
import com.vedeng.common.validator.FormToken;
import com.vedeng.finance.service.TraderAccountPeriodApplyService;
import com.vedeng.goods.model.Goods;
import com.vedeng.goods.model.vo.GoodsVo;
import com.vedeng.goods.service.GoodsService;
import com.vedeng.order.dao.SaleorderMapper;
import com.vedeng.order.model.Buyorder;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.model.SaleorderGoods;
import com.vedeng.order.model.vo.BuyorderVo;
import com.vedeng.order.model.vo.OrderData;
import com.vedeng.order.model.vo.SaleorderGoodsVo;
import com.vedeng.order.service.BuyorderService;
import com.vedeng.order.service.SaleorderService;
import com.vedeng.soap.service.VedengSoapService;
import com.vedeng.system.dao.TraderAddressGenerateMapper;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.system.service.CompanyService;
import com.vedeng.system.service.FtpUtilService;
import com.vedeng.system.service.OrgService;
import com.vedeng.system.service.RegionService;
import com.vedeng.system.service.UserService;
import com.vedeng.trader.dao.TraderAddressMapper;
import com.vedeng.trader.dao.TraderContactGenerateMapper;
import com.vedeng.trader.dao.TraderMapper;
import com.vedeng.trader.dao.WebAccountMapper;
import com.vedeng.trader.model.Trader;
import com.vedeng.trader.model.TraderAddress;
import com.vedeng.trader.model.TraderContactGenerate;
import com.vedeng.trader.model.WebAccount;
import com.vedeng.trader.model.vo.TraderAddressVo;
import com.vedeng.trader.model.vo.TraderContactVo;
import com.vedeng.trader.service.TraderAddressService;
import com.vedeng.trader.service.TraderCustomerService;
import com.vedeng.trader.service.TraderSupplierService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * <b>Description:</b><br>
 * 商品出库
 *
 * @author scott
 * @Note <b>ProjectName:</b> erp <br>
 * <b>PackageName:</b> com.vedeng.logistics.controller <br>
 * <b>ClassName:</b> WarehousesOutController <br>
 * <b>Date:</b> 2017年8月8日 下午5:36:00
 */
@Controller
@RequestMapping("/warehouse/warehousesout")
public class WarehousesOutController extends BaseController {
    public static Logger logger = LoggerFactory.getLogger(WarehousesOutController.class);

    @Autowired
    private RedisUtils redisUtils;

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
    private UserService userService;// 自动注入userService

    @Autowired
    @Qualifier("saleorderService")
    private SaleorderService saleorderService;

    @Autowired
    @Qualifier("orgService")
    private OrgService orgService;

    @Autowired
    @Qualifier("warehousePickService")
    private WarehousePickService warehousePickService;

    @Autowired
    @Qualifier("warehouseOutService")
    private WarehouseOutService warehouseOutService;

    @Autowired
    @Qualifier("expressService")
    private ExpressService expressService;

    @Autowired
    @Qualifier("warehouseGoodsOperateLogService")
    private WarehouseGoodsOperateLogService warehouseGoodsOperateLogService;

    @Autowired
    @Qualifier("logisticsService")
    private LogisticsService logisticsService;

    @Autowired
    @Qualifier("goodsService")
    private GoodsService goodsService;

    @Autowired
    @Qualifier("fileDeliveryService")
    private FileDeliveryService fileDeliveryService;

    @Autowired
    @Qualifier("afterSalesOrderService")
    private AfterSalesService afterSalesOrderService;

    @Resource
    private BuyorderService buyorderService;

    @Autowired
    @Qualifier("vedengSoapService")
    private VedengSoapService vedengSoapService;

    @Autowired
    @Qualifier("ftpUtilService")
    private FtpUtilService ftpUtilService;

    @Autowired
    @Qualifier("companyService")
    private CompanyService companyService;

    @Autowired
    @Qualifier("hcSaleorderService")
    protected HcSaleorderService hcSaleorderService;

	@Autowired
	@Qualifier("saleorderMapper")
	private SaleorderMapper saleorderMapper;

	@Autowired
	@Qualifier("traderCustomerService")
	private TraderCustomerService traderCustomerService;

	@Autowired
	@Qualifier("traderSupplierService")
	private TraderSupplierService traderSupplierService;

	@Autowired
	@Qualifier("warehouseInService")
	private WarehouseInService warehouseInService;

	@Autowired
	@Qualifier("webAccountMapper")
	private WebAccountMapper webAccountMapper;

    @Autowired
    @Qualifier("invoiceService")
    private InvoiceService invoiceService;

    @Autowired
    MsgProducer msgProducer;

	@Resource
	ExpressMapper  expressMapper;

	@Autowired
	private WarehouseStockService  warehouseStockService;

	@Autowired
	private WarehouseGoodsSetService warehouseGoodsSetService;

	@Autowired
    private WarehousePickingMapper warehousePickingMapper;

    @Autowired
    VgoodsService vGoodsService;

    /**
     *
     * <b>Description:</b><br>
     * 出库的销售订单列表
     *
     * @param request
     * @param warehouses
     * @param company`
     * @param pageNo
     * @param pageSize
     * @param session
     * @return
     * @Note <b>Author:</b> scott <br>
     * <b>Date:</b> 2017年8月9日 上午10:09:06
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "/index")
	public ModelAndView index(HttpServletRequest request, Saleorder saleorder,
							  @RequestParam(required = false, defaultValue = "1") Integer pageNo,
							  @RequestParam(required = false) Integer pageSize, HttpSession session,
							  @RequestParam(required = false, value = "searchBeginTime") String searchBeginTime,
							  @RequestParam(required = false, value = "searchEndTime") String searchEndTime,
                              @RequestParam(required = false, value = "searchPaymentBeginTimeStr") String searchPaymentBeginTime,
                              @RequestParam(required = false, value = "searchPaymentEndTimeStr") String searchPaymentEndTime,
                              @RequestParam(required = false, value = "flag") String flag
    ) {
		User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
		Page page = getPageTag(request, pageNo, pageSize);
		ModelAndView mv = new ModelAndView();
		mv.addObject("searchBeginTime", searchBeginTime);
		mv.addObject("searchEndTime", searchEndTime);
        if (searchPaymentBeginTime != null && !searchPaymentBeginTime.equals("")) {
            saleorder.setSearchPaymentBeginTime(DateUtil.convertLong(searchPaymentBeginTime + " 00:00:00", DateUtil.TIME_FORMAT));
        }
        if (searchPaymentEndTime != null && !searchPaymentEndTime.equals("")) {
            saleorder.setSearchPaymentEndTime( DateUtil.convertLong(searchPaymentEndTime + " 23:59:59", DateUtil.TIME_FORMAT));
        }
        if (null == flag && null == saleorder.getSearchPaymentBeginTime() && null == saleorder.getSearchPaymentEndTime()){
            Long endTime = System.currentTimeMillis();
            Long beginTime = endTime - 2505599000L;
            searchPaymentEndTime = DateUtil.convertString(endTime,DateUtil.DATE_FORMAT);
            searchPaymentBeginTime = DateUtil.convertString(beginTime,DateUtil.DATE_FORMAT);
            saleorder.setSearchPaymentEndTime(endTime);
            saleorder.setSearchPaymentBeginTime(beginTime);
        }
        mv.addObject("searchPaymentBeginTime",searchPaymentBeginTime);
        mv.addObject("searchPaymentEndTime",searchPaymentEndTime);

		if (searchBeginTime != null && !searchBeginTime.equals("")) {
			saleorder.setSearchBegintime(DateUtil.convertLong(searchBeginTime + " 00:00:00", DateUtil.TIME_FORMAT));
		}
		if (searchEndTime != null && !searchEndTime.equals("")) {
			saleorder.setSearchEndtime(DateUtil.convertLong(searchEndTime + " 23:59:59", DateUtil.TIME_FORMAT));
		}
        Integer saleorderId = saleorder.getSaleorderId();
		// 发货方式
		List<SysOptionDefinition> deliveryTypes = getSysOptionDefinitionList(480);
//		if (JedisUtils.exists(dbType + ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST + 480)) {
//			String json480 = JedisUtils.get(dbType + ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST + 480);
//			JSONArray jsonArray480 = JSONArray.fromObject(json480);
//			deliveryTypes = (List<SysOptionDefinition>) JSONArray.toCollection(jsonArray480, SysOptionDefinition.class);
//		}
		mv.addObject("deliveryTypes", deliveryTypes);
		// 查询销售订单
		saleorder.setCompanyId(session_user.getCompanyId());
		Map<String, Object> map = saleorderService.getSaleorderGoodsListPage(request, saleorder, page);
		// 获取销售部门
		List<Organization> orgList = orgService.getSalesOrgList(SysOptionConstant.ID_310, session_user.getCompanyId());
		mv.addObject("orgList", orgList);

		List<Saleorder> list = (List<Saleorder>) map.get("saleorderList");



		// 获取订单产品信息
		/*
		 * for (Saleorder sd : list) {
		 *
		 * //判断订单是否是状态正常的 Saleorder sl = saleorderService.getSaleorderFlag(sd);
		 * if(sl!=null){ sd.setShow("0");//正常订单 }else{ sd.setShow("1");//问题单 }
		 * List<SaleorderGoods> saleorderGoodsList =
		 * saleorderService.getSaleorderGoodsInfo(sd);
		 * sd.setGoodsList(saleorderGoodsList); //获取订单出库日志 ResultInfo
		 * warehouseGoodsOperateLogInfo =
		 * warehouseGoodsOperateLogService.getCreatorId(sd); if (null !=
		 * warehouseGoodsOperateLogInfo &&
		 * warehouseGoodsOperateLogInfo.getCode() == 0) { JSONObject json =
		 * JSONObject.fromObject(warehouseGoodsOperateLogInfo.getData());
		 * WarehouseGoodsOperateLog wl = (WarehouseGoodsOperateLog)
		 * JSONObject.toBean(json, WarehouseGoodsOperateLog.class); User user =
		 * userService.getUserById(wl.getCreator());
		 * sd.setOptor(user.getUsername()); }
		 *
		 * }
		 */
		String Ids = "";

        for (int i = 0; i < list.size(); i++) {
            // 销售人员
            User user = userService.getUserById(list.get(i).getUserId());
            list.get(i).setSalesName(user == null ? "" : user.getUsername());
            // 销售部门
            if (null != list.get(i).getOrgId()) {
                list.get(i).setSalesDeptName(orgService.getOrgNameById(list.get(i).getOrgId()));
            }
            Ids += list.get(i).getSaleorderId() + "_";
        }
        mv.addObject("Ids", Ids);
        mv.addObject("saleorderList", list);
        mv.addObject("page", (Page) map.get("page"));
        mv.setViewName("logistics/warehouseOut/index_warehouseOut");
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 快递单打印
     *
     * @param request
     * @param saleorderId
     * @param logisticsId
     * @return
     * @Note <b>Author:</b> leo.yang <br>
     *       <b>Date:</b> 2017年8月17日 下午2:04:31
     */
    @ResponseBody
    @RequestMapping(value = "/printExpress")
    public ModelAndView printExpress(HttpSession session, HttpServletRequest request, Integer orderId,
	    Integer logisticsId, Integer type, Integer btype, Integer shType, Integer shOrderId) {
	ModelAndView mv = new ModelAndView();
	User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
	Saleorder saleorder = new Saleorder();
	Buyorder buyorder = new Buyorder();
	AfterSalesDetail av = new AfterSalesDetail();
	FileDelivery fileDelivery = new FileDelivery();
	// 获取订单基本信息
	if (btype == 1) {
	    saleorder.setSaleorderId(orderId);
	    saleorder = saleorderService.getBaseSaleorderInfo(saleorder);
	    mv.addObject("saleorder", saleorder);
	    mv.addObject("shType", 0);
	} /*
	   * else if (btype == 2) { buyorder.setBuyorderId(orderId); BuyorderVo
	   * bv = buyorderService.getAddBuyorderVoDetail(buyorder,
	   * session_user); mv.addObject("buyorderVo", bv); }
	   */ else if (btype == -1) {
	    fileDelivery.setFileDeliveryId(orderId);
	    try {
		fileDelivery = fileDeliveryService.getFileDeliveryInfoById(fileDelivery.getFileDeliveryId());
		User user = userService.getUserById(fileDelivery.getCreator());
		fileDelivery.setDeliveryUsername(user == null ? "" : user.getUsername());
		mv.addObject("fileDelivery", fileDelivery);
		mv.addObject("shType", 0);
	    } catch (Exception e) {
			logger.error("warehouse out printExpress:", e);
	    }
	}
	// 销售售后
	if (shType != null && shType == 1) {
	    saleorder.setSaleorderId(orderId);
	    saleorder = saleorderService.getBaseSaleorderInfo(saleorder);
	    mv.addObject("saleorder", saleorder);

            AfterSales as = new AfterSales();
            as.setAfterSalesId(shOrderId);
            av = afterSalesOrderService.selectadtById(as);
            mv.addObject("av", av);
            mv.addObject("shType", shType);
        }
        // 采购售后
        if (shType != null && shType == 2) {
            buyorder.setBuyorderId(orderId);
            buyorder = buyorderService.getAddBuyorderVoDetail(buyorder, session_user);
            mv.addObject("buyorderVo", buyorder);

            AfterSales as = new AfterSales();
            as.setAfterSalesId(shOrderId);
            av = afterSalesOrderService.selectadtById(as);
            mv.addObject("av", av);
            mv.addObject("shType", shType);
        }

        Long currTime = DateUtil.sysTimeMillis();
        mv.addObject("currTime", currTime);
        // type=2直发，type=1普发
        mv.addObject("type", type);
        mv.addObject("btype", btype);
        mv.addObject("btype", btype);
        mv.addObject("companyId", session_user.getCompanyId());
        mv.setViewName("logistics/warehouseOut/print_express_" + logisticsId);
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 跳转到出库详情页面
     *
     * @param warehouses
     * @return
     * @Note <b>Author:</b> scott <br>
     * <b>Date:</b> 2017年8月10日 上午11:43:00
     */
    @ResponseBody
    @RequestMapping(value = "/detailJump")
    public ModelAndView detailJump(HttpSession session, Saleorder saleorder) {
        User curr_user = (User) session.getAttribute(ErpConst.CURR_USER);
        ModelAndView mv = new ModelAndView();
        // 获取订单详情
        saleorder.setCompanyId(curr_user.getCompanyId());
        Saleorder sd = saleorderService.getBaseSaleorderInfo(saleorder);
        // 判断订单是否是状态正常的
        Saleorder sl = saleorderService.getSaleorderFlag(saleorder);
        if (sl != null) {
            sd.setShow("0");// 正常订单
        } else {
            sd.setShow("1");// 问题单
        }
        // 销售人员
        User user = userService.getUserById(sd.getUserId());
        if(user !=null) {
            sd.setSalesName(user.getUsername());
        }
        // 归属销售
        user = userService.getUserByTraderId(sd.getTraderId(), 1);// 1客户，2供应商
        sd.setOptUserName(user == null ? "" : user.getUsername());
        // 销售部门（若一个多个部门，默认取第一个部门）
        User userOrg = orgService.getTraderUserAndOrgByTraderId(sd.getTraderId(), 1);
        sd.setSalesDeptName(userOrg == null ? "" : userOrg.getOrgName());
        // 获取物流公司列表
        List<Logistics> logisticsList = getLogisticsList(curr_user.getCompanyId());
        mv.addObject("logisticsList", logisticsList);
        // 运费说明
        List<SysOptionDefinition> freightDescriptions = getSysOptionDefinitionList(469);
        mv.addObject("freightDescriptions", freightDescriptions);
        // 付款方式列表
        List<SysOptionDefinition> paymentTermList = getSysOptionDefinitionList(SysOptionConstant.ID_418);
        mv.addObject("paymentTermList", paymentTermList);
        // 获取订单下的商品
        List<SaleorderGoods> saleorderGoodsList = saleorderService.getSaleorderGoodsInfo(sd);
        sd.setGoodsList(saleorderGoodsList);

        List<Integer> skuIds = new ArrayList<>();

        //新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
        if(!CollectionUtils.isEmpty(saleorderGoodsList)){
            saleorderGoodsList.stream().forEach(saleGood -> {
                skuIds.add(saleGood.getGoodsId());
            });
        }
        //新商品流切换 VDERP-1970 add by brianna 2020/3/16 end


        // 商品总数计算
        int goodsNum = 0;
        for (SaleorderGoods s : saleorderGoodsList) {
            goodsNum += s.getNum();
        }
        // 判断是否0<已拣货数量||已发货数量<商品总数
        int allPickCnt = 0;
        int allOutCnt = 0;
        for (SaleorderGoods saleorderGoods : saleorderGoodsList) {
            allPickCnt += saleorderGoods.getPickCnt();
            // allPickCnt -= saleorderGoods.gettNum();
            allOutCnt += saleorderGoods.getDeliveryNum();
            // allOutCnt -= saleorderGoods.gettNum();
        }
        // 拣货记录清单
        saleorder.setBussinessType(2);
        List<WarehousePicking> warehousePickList = warehousePickService.getPickDetil(saleorder);
        List<String> timeArray = new ArrayList<>();
        String pickFlag = "0";
        if (null != warehousePickList) {
            for (WarehousePicking wp : warehousePickList) {
                if (wp.getCnt() == 0) {
                    timeArray.add(DateUtil.convertString(wp.getAddTime(), "YYYY-MM-dd HH:mm:ss"));
                    pickFlag = "1";
                }
            }
            HashSet<String> tArray = new HashSet<String>(timeArray);
            mv.addObject("timeArray", tArray);


            //新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
            if(!CollectionUtils.isEmpty(warehousePickList)){
                warehousePickList.stream().forEach(warehousePick -> {
                    skuIds.add(warehousePick.getGoodsId());
                });
            }
            //新商品流切换 VDERP-1970 add by brianna 2020/3/16 end

        }

        for (WarehousePicking warehousePicking : warehousePickList) {
            User u = userService.getUserById(warehousePicking.getCreator());
            warehousePicking.setOperator(u == null ? "" : u.getUsername());
            for (SaleorderGoods saleorderGoods : saleorderGoodsList) {
                if (warehousePicking.getGoodsId().equals(saleorderGoods.getGoodsId())
                        && saleorderGoods.getLockedStatus() == 1) {
                    warehousePicking.setLockedStatus(1);
                }
            }
        }
		//------------VDERP-1553出库详情页产品信息模块增加库存相关信息---------
		List<String> skuList = saleorderGoodsList.stream().map(item ->item.getSku()).collect(Collectors.toList());
		//获取库存信息
		Map<String, WarehouseStock> stockInfo1 = warehouseStockService.getStockInfo(skuList);
		//获取库位信息
		Map<String, WarehouseGoodsSet> storageLocationNameMap = warehouseGoodsSetService.getStorageLocationNameBuySku(skuList);
		mv.addObject("stockInfo",stockInfo1);
		mv.addObject("storageLocation",storageLocationNameMap);
		//------------VDERP-1553出库详情页产品信息模块增加库存相关信息---------

        // 出库记录清单
        List<WarehouseGoodsOperateLog> warehouseOutList = warehouseOutService.getOutDetil(saleorder);
        List<String> timeArrayWl = new ArrayList<>();
        if (null != warehouseOutList) {
            for (WarehouseGoodsOperateLog wl : warehouseOutList) {
                User u = userService.getUserById(wl.getCreator());
                wl.setOpName(u == null ? "" : u.getUsername());
                timeArrayWl.add(DateUtil.convertString(wl.getAddTime(), "YYYY-MM-dd HH:mm:ss"));
                for (SaleorderGoods saleorderGoods : saleorderGoodsList) {
                    if (wl.getGoodsId().equals(saleorderGoods.getGoodsId()) && saleorderGoods.getLockedStatus() == 1) {
                        wl.setLockedStatus(1);
                    }
                }
            }
            HashSet<String> wArray = new HashSet<String>(timeArrayWl);
            TreeSet ts = new TreeSet(wArray);
            ts.comparator();
            mv.addObject("timeArrayWl", ts);


            //新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
            if(!CollectionUtils.isEmpty(warehouseOutList)){
                warehousePickList.stream().forEach(warehouseOut -> {
                    skuIds.add(warehouseOut.getGoodsId());
                });
            }
            //新商品流切换 VDERP-1970 add by brianna 2020/3/16 end

        }

        //新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
        if(!CollectionUtils.isEmpty(skuIds)){
            List<Map<String,Object>> skuTipsMap = this.vGoodsService.skuTipList(skuIds);
            Map<String,Map<String,Object>> newSkuInfosMap = skuTipsMap.stream().collect(Collectors.toMap(key->key.get("SKU_NO").toString(), v -> v, (k, v) -> v));
            mv.addObject("newSkuInfosMap", newSkuInfosMap);
        }
        //新商品流切换 VDERP-1970 add by brianna 2020/3/16 end

        // 查询发货备注信息
        List<SaleorderWarehouseComments> listComments = new ArrayList<>();
        listComments = saleorderService.getListComments(saleorder);
        mv.addObject("listComments", listComments);
        // 物流信息
        Express express = new Express();
        express.setBusinessType(SysOptionConstant.ID_496);
        express.setCompanyId(curr_user.getCompanyId());
        List<Integer> relatedIds = new ArrayList<Integer>();
        for (SaleorderGoods sg : saleorderGoodsList) {
            relatedIds.add(sg.getSaleorderGoodsId());
        }

        List<Express> expressList = null;
        if (relatedIds != null && relatedIds.size() > 0) {
            express.setRelatedIds(relatedIds);
            try {
                expressList = expressService.getExpressList(express);
                mv.addObject("expressList", expressList);
            } catch (Exception e) {
                logger.error("detailJump:", e);
            }
        }
        //如果是耗材订单显示医械购出库单按钮
        if (sd.getOrderType() == 5) {
            mv.addObject("HCType", 1);
        }

        // 查询所有产品的数量包括售后数量
        List<SaleorderGoods> saleorderGoods = saleorderService.getSaleorderGoodsById(saleorder);

        //调用库存服务查询库存量以及活动锁定库存
        ArrayList<String> skus = new ArrayList<>();
        for (SaleorderGoods saleorderGood : saleorderGoods) {
            skus.add(saleorderGood.getSku());
        }
        Map<String, WarehouseStock> stockInfo = warehouseStockService.getStockInfo(skus);

        //将库存量和活动锁定数量放入saleorder中
        List<SaleorderGoods> goodsList = sd.getGoodsList();
        for (SaleorderGoods goods : goodsList) {
            WarehouseStock warehouseStock = stockInfo.get(goods.getSku());
            if(warehouseStock != null) {
                goods.setTotalNum(warehouseStock.getStockNum());
                goods.setActionLockCount(warehouseStock.getActionLockNum());
            }else{
                goods.setTotalNum(0);
                goods.setActionLockCount(0);
            }
        }

        // add by Tomcat.Hui 2019/12/30 16:29 .Desc: VDERP-1039 票货同行. start
        boolean phtxFlag = invoiceService.getOrderIsGoodsPeer(saleorder.getSaleorderId());
        mv.addObject("phtxFlag", phtxFlag);

        expressList = invoiceService.getExpressInvoiceInfo(expressList);
        // add by Tomcat.Hui 2020/1/2 14:29 .Desc: VDERP-1039 票货同行. end

        //是否是销售
        Boolean saleFlag = userService.getSaleFlagUserId(curr_user.getUserId());
        //是否是物流人员
        Boolean logisticeFlag = userService.getLogisticeFlagByUserId(curr_user.getUserId());
        //订单归属部门是否是科研购
        Boolean KYGorgFlag = orgService.getKYGOrgFlagByTraderId(sd);

        mv.addObject("saleFlag",saleFlag);
        mv.addObject("KYGorgFlag",KYGorgFlag);
        mv.addObject("logisticeFlag",logisticeFlag);
        mv.addObject("saleorderGoods", saleorderGoods);
        mv.addObject("pickFlag", pickFlag);
        mv.addObject("saleorderGoodsList", saleorderGoodsList);
        mv.addObject("goodsNum", goodsNum);
        mv.addObject("allPickCnt", allPickCnt);
        mv.addObject("allOutCnt", allOutCnt);
        mv.addObject("warehouseOutList", warehouseOutList);
        mv.addObject("warehousePickList", warehousePickList);
        mv.addObject("saleorder", sd);
        mv.setViewName("logistics/warehouseOut/view_warehouseOutDetail");

        return mv;
    }


    /** @description: .
     * @notes: VDERP-1039 票货同行 查看申请商品.
     * @author: Tomcat.Hui.
     * @date: 2020/1/6 14:55.
     * @param null
     * @return: .
     * @throws: .
     */
    @ResponseBody
    @RequestMapping(value = "/viewAppliedItems")
    @FormToken(save = true)
    public ModelAndView viewAppliedItems(Integer expressId, HttpSession session){
        ModelAndView mv = new ModelAndView();
        try {
            InvoiceApply apply = new InvoiceApply();
            apply.setExpressId(expressId);
            List<InvoiceApply> invoiceApplyList = invoiceService.getInvoiceApplyInfoByExpressId(apply);
            if (null != invoiceApplyList && invoiceApplyList.size() == 1) {
                mv.addObject("invoiceApply", invoiceApplyList.get(0));
            } else {
                mv.addObject("invoiceApply", null);
            }

        }catch (Exception e) {
            logger.error("查看出库详情已申请开票信息出错:{}",e);
        }

        mv.setViewName("logistics/warehouseOut/view_applied_items");
        return mv;
    }

    /** @description: checkInvoiceParams.
     * @notes: DERP-1039 票货同行 校验财务开票资质.
     * @author: Tomcat.Hui.
     * @date: 2020/1/6 15:58.
     * @param wlogIds
     * @param session
     * @return: com.vedeng.common.model.ResultInfo.
     * @throws: .
     */
    @ResponseBody
    @RequestMapping(value = "/checkInvoiceParams")
    @MethodLock(field="invoiceApplyId",className = InvoiceApply.class)
    public ResultInfo checkInvoiceParams(InvoiceApply invoiceApply, HttpSession session) {
        ResultInfo<?> result = new ResultInfo<>();
        logger.info("开票申请ID：" + invoiceApply.getInvoiceApplyId() + "开始校验财务资质");
        try {
            //临界判断
            List<InvoiceApply> invoiceApplyQuery = invoiceService.getInvoiceApplyInfoByExpressId(invoiceApply);
            if (null == invoiceApplyQuery || invoiceApplyQuery.size() == 0 || null == invoiceApplyQuery.get(0)) {
                return new ResultInfo(-1,"开票申请不存在或已取消");
            } else if (invoiceApplyQuery.size() > 1) {
                return new ResultInfo(-1,"有效开票申请数量大于1,请先驳回开票申请");
            } else if ( null != invoiceApplyQuery.get(0).getValidStatus() && invoiceApplyQuery.get(0).getValidStatus().equals(1)) {
                return new ResultInfo(-1,"该开票申请已开票");
            } else {
                //校验财务资质
                Saleorder queryParams = new Saleorder();
                queryParams.setSaleorderId(invoiceApply.getRelatedId());
                Saleorder saleorder = saleorderService.getBaseSaleorderInfo(queryParams);
                Trader trader = traderCustomerService.getBaseTraderByTraderId(saleorder.getTraderId());
                TraderCertificateVo tc = new TraderCertificateVo();
                tc.setTraderId(trader.getTraderId());
                tc.setTraderType(ErpConst.ONE);
                if (trader.getCustomerNature() != null && trader.getCustomerNature() == 465) {
                    tc.setCustomerType(2);
                } else {
                    tc.setCustomerType(1);
                }
                Map<String, Object> map = traderCustomerService.getFinanceAndAptitudeByTraderId(tc, "all");
                JSONObject json = JSONObject.fromObject(map.get("finance"));
                if (null != json) {
                    TraderFinanceVo tf = (TraderFinanceVo) JSONObject.toBean(json, TraderFinanceVo.class);
                    if(StringUtil.isBlank(tf.getTaxNum())){
                        if(null != tf.getCheckStatus() && tf.getCheckStatus() == 1){
                            result.setCode(0);
                            result.setMessage("财务资质信息校验通过");
                        }else{
                            return new ResultInfo<>(-1,"客户开票资料不全，请联系归属销售进行维护");
                        }
                    }
                    result.setCode(0);
                    result.setMessage("财务资质信息校验通过");
                } else {
                    return new ResultInfo<>(-1,"未查询到财务资质信息");
                }
            }
        } catch (Exception e) {
            logger.error("校验财务开票资质出错{}:", e);
            return new ResultInfo<>(-1,"校验财务开票资质出错");
        }
        return result;
    }

    /**
     * <b>Description:</b><br>
     * 确定拣货数量
     *
     * @param warehouses
     * @return
     * @Note <b>Author:</b> scott <br>
     * <b>Date:</b> 2017年8月16日 上午8:40:11
     */
    /* @FormToken(save=true) */
    @ResponseBody
    @RequestMapping(value = "/viewPicking")
    public ModelAndView viewPicking(Warehouse warehouses, Saleorder saleorder, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        User curr_user = (User) session.getAttribute(ErpConst.CURR_USER);
        // 获取订单详情
        Saleorder sd = saleorderService.getBaseSaleorderInfo(saleorder);
        // 销售人员
        User user = userService.getUserById(sd.getUserId());
        sd.setSalesName(user.getUsername());
        // 归属销售
        User user2 = userService.getUserByTraderId(sd.getTraderId(), 1);// 1客户，2供应商
        sd.setOptUserName(user2 == null ? "" : user2.getUsername());
        // 销售部门（若一个多个部门，默认取第一个部门）
        Organization org = orgService.getOrgNameByUserId(sd.getUserId());
        sd.setSalesDeptName(org == null ? "" : org.getOrgName());
        // 获取订单下的商品
        List<SaleorderGoods> saleorderGoodsList = saleorderService.getSaleorderGoodsInfo(sd);
        // 对产品进行筛选（可拣货库存>0&&需拣货量>0）
        List<SaleorderGoods> sList = new ArrayList<SaleorderGoods>();
        for (SaleorderGoods s : saleorderGoodsList) {
            if ((s.getNum() - s.getPickCnt()) > 0 && (s.getTotalNum() - (s.getPickCnt() - s.getDeliveryNum())) > 0) {
                sList.add(s);
            }
        }
        // 根据商品id获取需要拣货的商品列表清单
        for (SaleorderGoods saleorderGood : sList) {
            saleorderGood.setNowNum(0);
            saleorderGood.setCompanyId(curr_user.getCompanyId());
            saleorderGood.setBussinessType(2);
            List<WarehouseGoodsOperateLog> list = warehouseGoodsOperateLogService.getWlog(saleorderGood);
            if (saleorderGood.getLockedStatus() == 1) {
                for (WarehouseGoodsOperateLog warehouseGoodsOperateLog : list) {
                    warehouseGoodsOperateLog.setLockedStatus(1);
                }
            }
            saleorderGood.setWlist(list);
        }
        // 商品总数计算
        int goodsNum = 0;
        for (SaleorderGoods s : saleorderGoodsList) {
            goodsNum += s.getNum();
        }
        sd.setGoodsList(sList);
        List<SaleorderGoods> goodsList = sd.getGoodsList();
        for (int i = 0; i < goodsList.size(); i++) {
            int needCnt = goodsList.get(i).getNum() - goodsList.get(i).getPickCnt();
            List<WarehouseGoodsOperateLog> w = goodsList.get(i).getWlist();
            int allCnt = 0;
            for (int j = 0; j < w.size(); j++) {
                if (w.get(j).getCnt() > 0) {
                    allCnt += w.get(j).getCnt();
                    if (needCnt > 0) {
                        if (needCnt > w.get(j).getCnt()) {
                            w.get(j).setpCount(w.get(j).getCnt());
                        } else if (needCnt <= w.get(j).getCnt()) {
                            w.get(j).setpCount(needCnt);
                        }
                        needCnt -= w.get(j).getCnt();
                    }
                }
            }

            if (allCnt > (goodsList.get(i).getNum() - goodsList.get(i).getPickCnt())) {
                goodsList.get(i).setpCountAll(goodsList.get(i).getNum() - goodsList.get(i).getPickCnt());
            } else {
                goodsList.get(i).setpCountAll(allCnt);
            }
        }
        mv.addObject("goodsNum", goodsNum);
        mv.addObject("saleorder", sd);
        mv.setViewName("logistics/warehouseOut/view_picking");
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 拣货详情
     *
     * @param warehouses
     * @param saleorder
     * @return
     * @Note <b>Author:</b> scott <br>
     * <b>Date:</b> 2017年8月17日 下午2:18:18
     */
    /* @FormToken(remove = true) */
    @ResponseBody
    @RequestMapping(value = "/viewPickingDetail")
    @SystemControllerLog(operationType = "add", desc = "保存拣货记录")
    public ModelAndView viewPickingDetail(Saleorder saleorder, HttpSession session) {
        User curr_user = (User) session.getAttribute(ErpConst.CURR_USER);

        //获取拣货记录的saleorderGoodsId
        String[] saleorderGoodsIds = StringUtils.split(saleorder.getSaleorderGoodsIds(), ",");

        ModelAndView mv = new ModelAndView();
        // 获取订单详情
        Saleorder sd = saleorderService.getBaseSaleorderInfo(saleorder);

        // 销售人员
        User user = userService.getUserById(sd.getUserId());

        String companyName = sd.getCompanyName();

        companyName = StringUtil.isBlank(companyName) ? user.getCompanyName() : companyName;

        mv.addObject("companyName", companyName);
        mv.addObject("companyId", user.getCompanyId());

        sd.setSalesName(user.getUsername());
        // 归属销售
        User user2 = userService.getUserByTraderId(sd.getTraderId(), 1);// 1客户，2供应商
        sd.setOptUserName(user2 == null ? "" : user2.getUsername());
        // 销售部门（若一个多个部门，默认取第一个部门）
        Organization org = orgService.getOrgNameByUserId(sd.getUserId());
        sd.setSalesDeptName(org == null ? "" : org.getOrgName());
        // 拣货时间
        Long time = DateUtil.sysTimeMillis();
        String pickNums = saleorder.getPickNums();
        mv.addObject("pickNums", pickNums);

        // 获取订单下的商品
        List<SaleorderGoods> saleorderGoodsList = saleorderService.getSaleorderGoodsInfo(sd);
        // 对产品进行筛选（可拣货库存>0&&需拣货量>0）
        List<SaleorderGoods> sList = new ArrayList<SaleorderGoods>();
        for (SaleorderGoods s : saleorderGoodsList) {
            if ((s.getNum() - s.getPickCnt()) > 0 && (s.getTotalNum() - (s.getPickCnt() - s.getDeliveryNum())) > 0) {
                sList.add(s);
            }
        }
        // 根据商品id获取需要拣货的商品列表清单
        for (SaleorderGoods saleorderGood : sList) {

            List<String> pickList = new ArrayList<>();
            List<String> batchNumberList = new ArrayList<>();
            List<String> expirationDateList = new ArrayList<>();
            List<String> relatedIdList = new ArrayList<>();
            List<String> relatedTypeList = new ArrayList<>();
            String picks[] = null;
            String values[] = null;
            String bpvalues[] = null;
            String ervalues[] = null;
            String rtvalues[] = null;
            String goodsList[] = pickNums.split("#");

            int count = 0;
            for (String st : goodsList) {
                String goodsValue[] = st.split("@");
                String gId = goodsValue[0];
                if (gId.equals(saleorderGood.getGoodsId() + "") && saleorderGood.getSaleorderGoodsId() == Integer.parseInt(saleorderGoodsIds[count])) {
                    String nums[] = goodsValue[1].split("_");
                    String pickNum = nums[0];
                    saleorderGood.setNowNum(Integer.parseInt(pickNum));
                    if (nums.length > 1) {

                        picks = nums[1].split(",");
                        for (String str : picks) {
                            values = str.split("!");
                            pickList.add(values[0]);
                            bpvalues = values[1].split("%");
                            batchNumberList.add(bpvalues[0]);
                            ervalues = bpvalues[1].split("\\+");
                            expirationDateList.add(ervalues[0]);
                            rtvalues = ervalues[1].split("\\=");
                            relatedIdList.add(rtvalues[0]);
                            relatedTypeList.add(rtvalues[1]);
                        }
                    }
                    break;
                }
                count++;
            }
            saleorderGood.setBussinessType(2);
            saleorderGood.setCompanyId(curr_user.getCompanyId());
            List<WarehouseGoodsOperateLog> list = warehouseGoodsOperateLogService.getWlog(saleorderGood);
            // 得到商品入库的各详情，设置拣货数
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setpCtn(pickList.get(i));
                if (!"-1".equals(batchNumberList.get(i))) {
                    list.get(i).setBatchNumber(batchNumberList.get(i));
                }
                list.get(i).setExpirationDate(Long.valueOf(expirationDateList.get(i)));
                list.get(i).setOrderId(Integer.parseInt(relatedIdList.get(i)));
                if (!"1".equals(relatedTypeList.get(i))) {
                    list.get(i).setYwType(2);
                } else {
                    list.get(i).setYwType(1);
                }
    		/*if(list.get(i).getGoodsId()==saleorderGood.getGoodsId()){
    		    list.get(i).setStoreNum(saleorderGood.getGoods().getStockNum());
    		}*/
            }
            // 只保存按效期等分组拣货>0的记录
            List<WarehouseGoodsOperateLog> wlist = new ArrayList<WarehouseGoodsOperateLog>();
            for (WarehouseGoodsOperateLog wl : list) {
                if (Integer.parseInt(wl.getpCtn()) > 0) {
                    //更新保质期产品,库位等信息  VDERP-2073订货号条码打印 & 拣货单优化
                    warehouseGoodsOperateLogService.updateWarehouselogInfo(wl);
                    wlist.add(wl);
                }
            }
            saleorderGood.setWlist(wlist);
        }
        sd.setGoodsList(sList);

        //对总拣货数量进行异步验证(当前拣货总数量与历史拣货总数量不能超过该商品需要拣货的总数量)
        Boolean verify = false;
        List<SaleorderGoods> sdGoodsList = sd.getGoodsList();
        for (SaleorderGoods saleorderGoods : sdGoodsList) {
            if (saleorderGoods.getNum() < saleorderGoods.getPickCnt() + saleorderGoods.getNowNum()){
                verify = true;
                break;
            }
        }

        if (verify){
            mv.addObject("verify",true);
        } else {
            // 保存拣货记录
            ResultInfo<?> result = warehousePickService.savePickRecord(sd, session);
            // 拣货记录清单
            saleorder.setBussinessType(2);
            List<WarehousePicking> warehousePickList = warehousePickService.getPickDetil(saleorder);
            for (WarehousePicking warehousePicking : warehousePickList) {
                User u = userService.getUserById(warehousePicking.getCreator());
                warehousePicking.setOperator(u == null ? "" : u.getUsername());
            }
            List<String> timeArray = new ArrayList<>();
            if (null != warehousePickList) {
                for (WarehousePicking wp : warehousePickList) {
                    if (wp.getCnt() == 0)
                        timeArray.add(DateUtil.convertString(wp.getAddTime(), "YYYY-MM-dd HH:mm:ss"));
                }
                HashSet<String> tArray = new HashSet<String>(timeArray);
                // 获取排重后的日期
                mv.addObject("timeArray", tArray);
            }
            List<SaleorderGoods> sdsList = new ArrayList<SaleorderGoods>();
            for (SaleorderGoods sdg : sd.getGoodsList()) {
                if (sdg.getNowNum() > 0) {
                    sdsList.add(sdg);
                }
            }
            // 商品总数计算
            int goodsNum = 0;
            for (SaleorderGoods s : saleorderGoodsList) {
                goodsNum += s.getNum();
            }
            mv.addObject("goodsNum", goodsNum);
            sd.setGoodsList(sdsList);
            mv.addObject("warehousePickList", warehousePickList);
            mv.addObject("saleorder", sd);



            //新商品流切换 VDERP-1970 add by brianna 2020/3/16 start

            Set<Integer> skuIds = new HashSet<>();

            if(!CollectionUtils.isEmpty(saleorderGoodsList)){
                saleorderGoodsList.stream().forEach(saleGood -> {
                    skuIds.add(saleGood.getGoodsId());
                });
            }

            if(!CollectionUtils.isEmpty(warehousePickList)){
                saleorderGoodsList.stream().forEach(warehousePickItem -> {
                    skuIds.add(warehousePickItem.getGoodsId());
                });
            }

            List<Map<String,Object>> skuTipsMap = this.vGoodsService.skuTipList(new ArrayList<>(skuIds));
            Map<String,Map<String,Object>> newSkuInfosMap = skuTipsMap.stream().collect(Collectors.toMap(key->key.get("SKU_NO").toString(), v -> v, (k, v) -> v));
            mv.addObject("newSkuInfosMap", newSkuInfosMap);
            //新商品流切换 VDERP-1970 add by brianna 2020/3/16 end

            mv.addObject("time", time);
            mv.addObject("userName", curr_user.getUsername());
            mv.addObject("verify",false);
        }
        mv.setViewName("logistics/warehouseOut/view_picking_detail");
        return mv;
    }


    /**
     * <b>Description:</b><br>
     * 商品出库
     *
     * @param warehouses
     * @param saleorder
     * @return
     * @Note <b>Author:</b> scott <br>
     * <b>Date:</b> 2017年8月17日 下午2:25:14
     */
    @ResponseBody
    @RequestMapping(value = "/viewOutDetail")
    public ModelAndView viewOutDetail(Saleorder saleorder, HttpSession session) {
        User curr_user = (User) session.getAttribute(ErpConst.CURR_USER);
        ModelAndView mv = new ModelAndView();
        // 获取订单详情
        Saleorder sd = saleorderService.getBaseSaleorderInfo(saleorder);
        // 获取订单下的商品
        List<SaleorderGoods> sl = saleorderService.getSaleorderGoodsInfo(sd);
        // 商品总数计算
        int goodsNum = 0;
        for (SaleorderGoods s : sl) {
            goodsNum += s.getNum();
        }
        // 销售人员
        User user = userService.getUserById(sd.getUserId());
        sd.setSalesName(user.getUsername());
        // 归属销售
        User user2 = userService.getUserByTraderId(sd.getTraderId(), 1);// 1客户，2供应商
        sd.setOptUserName(user2 == null ? "" : user2.getUsername());
        // 销售部门（若一个多个部门，默认取第一个部门）
        Organization org = orgService.getOrgNameByUserId(sd.getUserId());
        sd.setSalesDeptName(org == null ? "" : org.getOrgName());
        // 获取订单下的商品
        sd.setIsOut("1");// 出库只查询已拣货商品
        List<SaleorderGoods> saleorderGoodsList = saleorderService.getSaleorderGoodsInfo(sd);
        // 筛选出库的商品（拣货未发数量>0）
        List<SaleorderGoods> sList = new ArrayList<SaleorderGoods>();
        for (SaleorderGoods s : saleorderGoodsList) {
            if ((s.getPickCnt() - s.getDeliveryNum()) > 0) {
                sList.add(s);
            }
        }
        // 根据商品id获取需要出库的商品列表清单（且拣货数-出库数>0）
        for (SaleorderGoods saleorderGood : sList) {
            saleorderGood.setNowNum(0);
            saleorderGood.setIsOut("1");
            saleorderGood.setBussinessType(2);
            saleorderGood.setCompanyId(curr_user.getCompanyId());
            List<WarehouseGoodsOperateLog> list = warehouseGoodsOperateLogService.getWlog(saleorderGood);
            saleorderGood.setWlist(list);
        }
        sd.setGoodsList(sList);
        // 拣货记录清单
        saleorder.setBussinessType(2);
        List<WarehousePicking> warehousePickList = warehousePickService.getPickDetil(saleorder);
        mv.addObject("warehousePickList", warehousePickList);
        mv.addObject("saleorder", sd);
        mv.addObject("goodsNum", goodsNum);
        mv.setViewName("logistics/warehouseOut/view_warehouseout");
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 商品完成出库
     *
     * @param saleorder
     * @return
     * @Note <b>Author:</b> scott <br>
     * <b>Date:</b> 2017年8月17日 下午3:11:25
     */
    @ResponseBody
    @RequestMapping(value = "/warehouseEnd")
    @SystemControllerLog(operationType = "add", desc = "保存出库记录")
    public ModelAndView warehouseEnd(Saleorder saleorder, HttpSession session) {
        User curr_user = (User) session.getAttribute(ErpConst.CURR_USER);
        ModelAndView mv = new ModelAndView();
        String pickNums = saleorder.getPickNums();
        // 获取订单详情
        Saleorder sd = saleorderService.getBaseSaleorderInfo(saleorder);
        // 销售订单产品出库完成后最后一步填写快递信息--发送消息
        if (sd.getSaleorderId() != null && sd.getTraderId() != null && sd.getSaleorderNo() != null) {
            // 根据客户Id查询客户负责人
            List<Integer> userIdList = userService.getUserIdListByTraderId(sd.getTraderId(), ErpConst.ONE);// ONE客户，TWO供应商
            if (userIdList != null && !userIdList.isEmpty()) {
                Map<String, String> map = new HashMap<>();
                map.put("saleorderNo", sd.getSaleorderNo());
                // 销售产品出库--链接到销售订单详情
                MessageUtil.sendMessage(10, userIdList, map,
                        "./order/saleorder/view.do?saleorderId=" + sd.getSaleorderId());
            }

        }
        // 销售人员
        User user = userService.getUserById(sd.getUserId());
        sd.setSalesName(user.getUsername());
        // 归属销售
        User user2 = userService.getUserByTraderId(sd.getTraderId(), 1);// 1客户，2供应商
        sd.setOptUserName(user2 == null ? "" : user2.getUsername());
        // 销售部门（若一个多个部门，默认取第一个部门）
        Organization org = orgService.getOrgNameByUserId(sd.getUserId());
        sd.setSalesDeptName(org == null ? "" : org.getOrgName());
        // 获取订单下的商品
        sd.setIsOut("1");
        List<SaleorderGoods> saleorderGoodsList = saleorderService.getSaleorderGoodsInfo(sd);
        // 筛选出库的商品（拣货未发数量>0）
        List<SaleorderGoods> sList = new ArrayList<SaleorderGoods>();
        for (SaleorderGoods s : saleorderGoodsList) {
            if ((s.getPickCnt() - s.getDeliveryNum()) > 0) {
                sList.add(s);
            }
        }
        // 根据商品id获取需要出库的商品列表清单
        for (SaleorderGoods saleorderGood : sList) {
            String pick[] = null;
            String goodsList[] = pickNums.split("#");
            for (String st : goodsList) {
                String goodsValue[] = st.split("@");
                String gId = goodsValue[0];
                if (gId.equals(saleorderGood.getGoodsId() + "")) {
                    String nums[] = goodsValue[1].split("_");
                    String pickNum = nums[0];
                    saleorderGood.setNowNum(Integer.parseInt(pickNum));
                    pick = nums[1].split(",");
                    break;
                }
            }
            saleorderGood.setIsOut("1");
            saleorderGood.setBussinessType(2);
            saleorderGood.setCompanyId(curr_user.getCompanyId());
            List<WarehouseGoodsOperateLog> list = warehouseGoodsOperateLogService.getWlog(saleorderGood);
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setpCtn(pick[i]);
            }
            // 筛选出要出库数>0的产品
            List<WarehouseGoodsOperateLog> wlList = new ArrayList<WarehouseGoodsOperateLog>();
            for (WarehouseGoodsOperateLog wgl : list) {
                if (Integer.parseInt(wgl.getpCtn()) > 0) {
                    wlList.add(wgl);
                }
                wgl.setOperateType(2);
            }
            saleorderGood.setWlist(wlList);
        }
        sd.setGoodsList(sList);
        // 保存产品出库记录
        sd.setBussinessType(2);
        ResultInfo<?> result = warehouseGoodsOperateLogService.saveOutRecord(sd, session);
        // 商品总数计算
        int goodsNum = 0;
        for (SaleorderGoods s : saleorderGoodsList) {
            goodsNum += s.getNum();
        }
        // 出库记录清单
        saleorder.setBussinessType(2);
        List<WarehouseGoodsOperateLog> warehouseOutList = warehouseOutService.getOutDetil(saleorder);
        List<String> timeArrayWl = new ArrayList<>();
        if (null != timeArrayWl) {
            for (WarehouseGoodsOperateLog wl : warehouseOutList) {
                timeArrayWl.add(DateUtil.convertString(wl.getAddTime(), "YYYY-MM-dd HH:mm:ss"));
            }
            HashSet<String> wArray = new HashSet<String>(timeArrayWl);
            // 获取排重后的日期
            mv.addObject("timeArrayWl", wArray);
        }
        // 物流信息
        /*
         * Express express = new Express();
         * express.setBusinessType(SysOptionConstant.ID_515); List<Integer>
         * relatedIds = new ArrayList<Integer>(); for (SaleorderGoods sg :
         * saleorderGoodsList) { relatedIds.add(sg.getSaleorderGoodsId()); }
         * express.setRelatedIds(relatedIds); try { List<Express> expressList =
         * expressService.getExpressList(express); mv.addObject("expressList",
         * expressList); } catch (Exception e) { logger.error(Contant.ERROR_MSG, e); }
         */

        if (sd.getCompanyId().equals(1)) {// 发货通知
            vedengSoapService.messageSyncWeb(2, sd.getSaleorderId(), 3);
        }
        mv.addObject("goodsNum", goodsNum);
        mv.addObject("warehouseOutList", warehouseOutList);
        mv.addObject("saleorder", sd);
        mv.setViewName("logistics/warehouseOut/view_out_end");
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 扫码出库
     *
     * @param saleorder
     * @return
     * @Note <b>Author:</b> scott <br>
     * <b>Date:</b> 2017年8月17日 下午4:21:51
     */
   /* @FormToken(save = true)*/
    @ResponseBody
    @RequestMapping(value = "/warehouseSmOut")
    public ModelAndView warehouseSmOut(Saleorder saleorder) {
        ModelAndView mv = new ModelAndView();
        // 获取订单详情
        Saleorder sd = saleorderService.getBaseSaleorderInfo(saleorder);
        // 销售人员
        User user = userService.getUserById(sd.getUserId());
        sd.setSalesName(user.getUsername());
        // 归属销售
        User user2 = userService.getUserByTraderId(sd.getTraderId(), 1);// 1客户，2供应商
        sd.setOptUserName(user2 == null ? "" : user2.getUsername());
        // 销售部门（若一个多个部门，默认取第一个部门）
        Organization org = orgService.getOrgNameByUserId(sd.getUserId());
        sd.setSalesDeptName(org == null ? "" : org.getOrgName());
        // 获取订单下的商品
//	sd.setIsOut("1");
        List<SaleorderGoods> saleorderGoodsList = saleorderService.getSaleorderGoodsInfo(sd);
        sd.setGoodsList(saleorderGoodsList);
        // 商品总数计算
        int goodsNum = 0;
        for (SaleorderGoods s : saleorderGoodsList) {
            goodsNum += s.getNum();
            s.setNowNum(s.getNum() - s.getDeliveryNum());
        }
        // 拣货记录清单
        saleorder.setBussinessType(2);
        List<WarehousePicking> warehousePickList = warehousePickService.getPickDetil(saleorder);
        /*
         * // 物流信息 Express express = new Express();
         * express.setBusinessType(SysOptionConstant.ID_515); List<Integer>
         * relatedIds = new ArrayList<Integer>(); for (SaleorderGoods sg :
         * saleorderGoodsList) { relatedIds.add(sg.getSaleorderGoodsId()); }
         * express.setRelatedIds(relatedIds); try { List<Express> expressList =
         * expressService.getExpressList(express); mv.addObject("expressList",
         * expressList); } catch (Exception e) { logger.error(Contant.ERROR_MSG, e); }
         */
        mv.addObject("goodsNum", goodsNum);
        mv.addObject("warehouseBarcodeOutList", warehousePickList);
        mv.addObject("saleorder", sd);
        mv.setViewName("logistics/warehouseOut/view_sm_warehouseout");
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 根据条码查询商品
     *
     * @param request
     * @param WarehouseGoodsOperateLog
     * @param session
     * @return
     * @Note <b>Author:</b> scott <br>
     * <b>Date:</b> 2017年8月25日 上午10:16:52
     */
    @ResponseBody
    @RequestMapping(value = "/getSMGoods")
    public ResultInfo<List<WarehouseGoodsOperateLog>> getSMGoods(HttpServletRequest request,
                                                                 WarehouseGoodsOperateLog warehouseGoodsOperateLog, HttpSession session) {
        ResultInfo<List<WarehouseGoodsOperateLog>> resultInfo = new ResultInfo<List<WarehouseGoodsOperateLog>>();
        User curr_user = (User) session.getAttribute(ErpConst.CURR_USER);
        // 查询条码商品
        warehouseGoodsOperateLog.setCompanyId(curr_user.getCompanyId());
        List<WarehouseGoodsOperateLog> list1 = warehouseOutService.getSGoods(warehouseGoodsOperateLog);
        /*
         * if(list1.size()>0){ SaleorderGoods saleorderGood = new
         * SaleorderGoods(); saleorderGood.setBussinessType(2);
         * saleorderGood.setGoodsId(list1.get(0).getGoodsId());
         * saleorderGood.setCompanyId(curr_user.getCompanyId());
         * saleorderGood.setSaleorderId(warehouseGoodsOperateLog.getOrderId());
         * saleorderGood.setIsOut("2");
         * saleorderGood.setBarcodeId(list1.get(0).getBarcodeId());
         * List<WarehouseGoodsOperateLog> list =
         * warehouseGoodsOperateLogService.getWlog(saleorderGood);
         * List<WarehouseGoodsOperateLog> l = new
         * ArrayList<WarehouseGoodsOperateLog>(); if(list.size()>0){
         * l.add(list.get(0)); }
         */
        // List<WarehouseGoodsOperateLog> list =
        // warehouseOutService.getSGoods(warehouseGoodsOperateLog);
        if (list1.size() > 0) {
            if (list1.get(0).getWarehouseGoodsOperateLogId() == -1) {
                resultInfo.setCode(-2);
                resultInfo.setMessage("该产品已被锁定，无法出库");
                resultInfo.setData(list1);
            } else {
                resultInfo.setCode(0);
                resultInfo.setMessage("操作成功");
                resultInfo.setData(list1);
            }
        }

        return resultInfo;
    }

    /**
     * <b>Description:</b><br>
     * 条码出库结束
     *
     * @param request
     * @param WarehouseGoodsOperateLog
     * @param session
     * @return
     * @Note <b>Author:</b> scott <br>
     * <b>Date:</b> 2017年8月25日 上午10:16:52
     */
    @MethodLock(field = "saleorderId",className = Saleorder.class)
    //@FormToken(remove = true)
    @ResponseBody
    @RequestMapping(value = "/warehouseSMEnd")
    @SystemControllerLog(operationType = "add", desc = "保存扫码出库记录")
    public ModelAndView warehouseSMEnd(Saleorder saleorder, HttpSession session,@RequestParam(value = "batchType",defaultValue = "0")Integer batchType) {
        User curr_user = (User) session.getAttribute(ErpConst.CURR_USER);
        Long time = DateUtil.sysTimeMillis();
        ModelAndView mv = new ModelAndView();
        String idCnt = saleorder.getIdCnt();
        String values[] = idCnt.split("#");
        StringBuilder newidCnt = new StringBuilder();
        List<WarehouseGoodsOperateLog> wgList = new ArrayList<WarehouseGoodsOperateLog>();
        // 获取订单详情
        Saleorder sd = saleorderService.getBaseSaleorderInfo(saleorder);
        //-----------限购---------------------
        try {
        //是否为活动订单
        boolean isActionOrderflag = false;
        if (sd.getActionId() != 0) {
            //为活动订单
            isActionOrderflag = true;
        }
        //订单商品内未发货数量
        HashMap<Integer, Integer> saleorderGoodsNumMap = new HashMap<>();
        //-----------限购---------------------
        for (int i = 0; i < values.length; i++) {
            String nc[] = values[i].split(",");
            String warehouseGoodsOperateLogId = nc[0];
            WarehouseGoodsOperateLog w = new WarehouseGoodsOperateLog();
            w.setWarehouseGoodsOperateLogId(Integer.parseInt(warehouseGoodsOperateLogId));
            Barcode barcode = warehouseOutService.getBarcodeByWarehouseGoodsOperateLogId(w.getWarehouseGoodsOperateLogId());
            Integer barcodeIsEnable=0;
            if(batchType.equals(1)){
                barcodeIsEnable=1;
            }else{
               barcodeIsEnable = warehouseGoodsOperateLogService.getBarcodeIsEnable(w, 2);
            }
            //判断条码是否使用过
            if (barcodeIsEnable > 0) {
                // 根据id获取入库信息，设置为出库信息
                WarehouseGoodsOperateLog wl = warehouseOutService.getSMGoods(w);
                wl.setAddTime(time);
                wl.setCreator(curr_user.getUserId());
                wl.setUpdater(curr_user.getUserId());
                wl.setModTime(time);
                wl.setOperateType(2);
                wl.setIsEnable(1);
                // 设置销售产品id
                saleorder.setCompanyId(curr_user.getCompanyId());
                saleorder.setGoodsId(wl.getGoodsId());
                saleorder.setIsSaleOut(1);

                //原查询只需获取订单商品id,调用db接口进行了很多不必要的查询
//	    List<SaleorderGoods> saleorderGoodsList = saleorderService.getSaleorderGoodsById(saleorder);
                List<SaleorderGoods> saleorderGoodsList = saleorderService.getSaleorderGoods(saleorder);
                //--------------限购------------
                for (SaleorderGoods saleorderGoods : saleorderGoodsList) {
                    //当前可出库数量
                    Integer saleorderGoodsNum = saleorderGoodsNumMap.get(saleorderGoods.getSaleorderGoodsId());
                    if (saleorderGoodsNum == null) {
                        saleorderGoodsNum = saleorderGoods.getNum() - saleorderGoods.getAfterReturnNum() - saleorderGoods.getDeliveryNum();
                        saleorderGoodsNumMap.put(saleorderGoods.getSaleorderGoodsId(), saleorderGoodsNum);
                    }
                }
                // 为活动订单
                if (isActionOrderflag) {
                    //排序将活动商品排在前面
                    saleorderGoodsList = saleorderGoodsList.stream().sorted((o1, o2) -> {
                        if (o1.getIsActionGoods() > o2.getIsActionGoods()) {
                            return -1;
                        } else {
                            return 1;
                        }
                    }).collect(Collectors.toList());
                    for (SaleorderGoods saleorderGoods : saleorderGoodsList) {
                        Integer goodsNum = saleorderGoodsNumMap.get(saleorderGoods.getSaleorderGoodsId());
                        //为活动商品且未发货数量大于0
                        if (saleorderGoods.getIsActionGoods() > 0 && goodsNum > 0) {
                            wl.setRelatedId(saleorderGoods.getSaleorderGoodsId());
                            wl.setIsActionGoods(1);
                            wl.setNum(0 - Integer.parseInt(nc[1]));
                            wgList.add(wl);
                            saleorderGoodsNumMap.put(saleorderGoods.getSaleorderGoodsId(), goodsNum - Integer.parseInt(nc[1]));
                            break;
                        } else if (saleorderGoods.getIsActionGoods().equals(0) && goodsNum > 0) {
                            wl.setRelatedId(saleorderGoods.getSaleorderGoodsId());
                            wl.setNum(0 - Integer.parseInt(nc[1]));
                            wgList.add(wl);
                            saleorderGoodsNumMap.put(saleorderGoods.getSaleorderGoodsId(), goodsNum - Integer.parseInt(nc[1]));
                            break;
                        }
                    }
                } else {//为普通订单
                    wl.setRelatedId(saleorderGoodsList.get(0).getSaleorderGoodsId());
                    Integer goodsNum = saleorderGoodsNumMap.get(wl.getRelatedId());
                    if(goodsNum == null){
                        goodsNum = 0;
                    }
                    if(goodsNum - Integer.parseInt(nc[1]) >= 0 && goodsNum > 0) {
                        wl.setNum(0 - Integer.parseInt(nc[1]));
                        saleorderGoodsNumMap.put(wl.getRelatedId(), goodsNum - Integer.parseInt(nc[1]));
                        wgList.add(wl);
                    }
                }
                newidCnt.append(nc[0]).append(",").append(nc[1]).append("#");
                //--------------限购------------

            }
        }
        if (wgList.size() == 0) {
            return fail(mv);
        }
            saleorder.setIdCnt(idCnt);
            if (CollectionUtils.isNotEmpty(wgList)) {
				//-----------VDERP-1794订单中"普通商品"在出库时，对出库数量增加限制
			    Boolean  actionFlag = warehouseGoodsOperateLogService.isEnableOutForAction(wgList);
				if(actionFlag){
					return fail(mv);
				}
				//-----------VDERP-1794订单中"普通商品"在出库时，对出库数量增加限制
                // 批量插入条码出库信息
                ResultInfo<?> result = warehouseGoodsOperateLogService.addWlogList(wgList);
            }
        }catch (Exception  e){
            log.error("warehouseSMEnd 保存出库记录 error:{}",e);
        }finally {
            warehouseGoodsOperateLogService.releaseDistributedLock(wgList);
        }

        // 销售人员
        User user = userService.getUserById(sd.getUserId());
        sd.setSalesName(user.getUsername());
        // 归属销售
        User user2 = userService.getUserByTraderId(sd.getTraderId(), 1);// 1客户，2供应商
        sd.setOptUserName(user2 == null ? "" : user2.getUsername());
        // 销售部门（若一个多个部门，默认取第一个部门）
        Organization org = orgService.getOrgNameByUserId(sd.getUserId());
        sd.setSalesDeptName(org == null ? "" : org.getOrgName());
        // 获取订单下的商品
        sd.setIsOut("1");// 出库只查询已拣货商品
        List<SaleorderGoods> saleorderGoodsList2 = saleorderService.getSaleorderGoodsInfo(sd);

        // 商品总数计算
        int goodsNum = 0;
        for (SaleorderGoods s : saleorderGoodsList2) {
            goodsNum += s.getNum();
        }
        // 出库记录清单
        // saleorder.setIsSM("1");
        saleorder.setBussinessType(2);
        List<WarehouseGoodsOperateLog> warehouseOutList = warehouseOutService.getOutDetil(saleorder);
        List<String> timeArrayWl = new ArrayList<>();
        if (null != timeArrayWl) {
            for (WarehouseGoodsOperateLog wl : warehouseOutList) {
                timeArrayWl.add(DateUtil.convertString(wl.getAddTime(), "YYYY-MM-dd HH:mm:ss"));
            }
            HashSet<String> wArray = new HashSet<String>(timeArrayWl);
            // 获取排重后的日期
            mv.addObject("timeArrayWl", wArray);
        }
        // 物流信息
        /*
         * Express express = new Express();
         * express.setBusinessType(SysOptionConstant.ID_515); List<Integer>
         * relatedIds = new ArrayList<Integer>(); for (SaleorderGoods sg :
         * saleorderGoodsList) { relatedIds.add(sg.getSaleorderGoodsId()); }
         * express.setRelatedIds(relatedIds); try { List<Express> expressList =
         * expressService.getExpressList(express); mv.addObject("expressList",
         * expressList); } catch (Exception e) { logger.error(Contant.ERROR_MSG, e); }
         */
        try {
            if (sd.getCompanyId().equals(1)) {// 发货通知
                vedengSoapService.messageSyncWeb(2, sd.getSaleorderId(), 3);

                    if (sd.getOrderType() == 5) {// 如果是耗材商城的订单
                        // 根据快递单查询该快递单下的商品列表
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("saleOrder", sd);
                        // 查询该快递单下的商品信息列表
                        map.put("goodsList", saleorderGoodsList2);
                        // 调用ERP推送到耗材的接口
                        hcSaleorderService.putExpressToHC(map);
                    }
            }
        }catch (Exception e){
            logger.error("发货通知失败",e);
        }
        mv.addObject("goodsNum", goodsNum);
        mv.addObject("warehouseOutList", warehouseOutList);
        mv.addObject("saleorder", sd);
        mv.setViewName("logistics/warehouseOut/view_out_end");
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 撤销拣货
     *
     * @param wlogIds
     * @param session
     * @return
     * @Note <b>Author:</b> scott <br>
     * <b>Date:</b> 2017年8月25日 上午10:16:52
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "/editIsEnablePicking")
    @SystemControllerLog(operationType = "delete", desc = "删除拣货记录")
    public ResultInfo editIsEnablePicking(String wlogIds, HttpSession session) {
        ResultInfo<?> result = new ResultInfo<>();
        // 获取session中user信息
        User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
        List<WarehousePicking> wpList = new ArrayList<WarehousePicking>();
        if (null != wlogIds) {
            // 切割barcodeId拼接成的字符串
            String[] params = wlogIds.split("#");
            for (int i = 0; i < params.length; i++) {
                String[] ids = params[i].split(",");
                WarehousePicking wp = new WarehousePicking();
                Integer wlogId = Integer.parseInt(ids[0]);
                Integer wdId = Integer.parseInt(ids[1]);
                wp.setWarehousePickingDetailId(wdId);
                wp.setWarehousePickingId(wlogId);
                wp.setUpdater(session_user.getUserId());
                wp.setModTime(DateUtil.sysTimeMillis());
                wpList.add(wp);
            }
        }
        try {
            result = warehousePickService.editIsEnablePick(wpList);
        } catch (Exception e) {
            logger.error("editIsEnablePicking:", e);
        }
        return result;
    }

    /**
     * <b>Description:</b><br>
     * 出库记录
     *
     * @param request
     * @param warehouseGoodsOperateLog
     * @param pageNo
     * @param pageSize
     * @param session
     * @param searchBeginTime
     * @param searchEndTime
     * @return
     * @Note <b>Author:</b> scott <br>
     * <b>Date:</b> 2017年8月30日 下午3:01:51
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "/warehouseOutLogList")
    public ModelAndView warehouseInLogList(HttpServletRequest request,
                                           WarehouseGoodsOperateLog warehouseGoodsOperateLog,
                                           @RequestParam(required = false, defaultValue = "1") Integer pageNo, // required
                                           // false:可不传入pageNo这个参数，true必须传入，defaultValue默认值，若无默认值，使用Page类中的默认值
                                           @RequestParam(required = false) Integer pageSize, HttpSession session,
                                           @RequestParam(required = false, value = "_startTime") String _startTime,
                                           @RequestParam(required = false, value = "_endTime") String _endTime,
                                           @RequestParam(required = false, value = "searchDateType") String searchDateType) {
        ModelAndView mv = new ModelAndView();
        Page page = getPageTag(request, pageNo, pageSize);
        // 获取session中user信息
        User session_user = (User) session.getAttribute(ErpConst.CURR_USER);

        if (StringUtils.isBlank(searchDateType)) {// 新打开
            /*
             * _startTime = DateUtil.getFirstDayOfMonth(0);//当月第一天 _endTime =
             * DateUtil.getNowDate("yyyy-MM-dd");//当前日期
             */
        } else {
            mv.addObject("de_startTime", DateUtil.getFirstDayOfMonth(0));
            mv.addObject("de_endTime", DateUtil.getNowDate("yyyy-MM-dd"));
        }
        mv.addObject("searchDateType", searchDateType);
        if (_startTime != null && !"".equals(_startTime)) {
            warehouseGoodsOperateLog.setBeginTime(DateUtil.convertLong(_startTime + " 00:00:00", ""));
        }
        if (_endTime != null && !"".equals(_endTime)) {
            warehouseGoodsOperateLog.setEndTime(DateUtil.convertLong(_endTime + " 23:59:59", ""));
        }
        mv.addObject("_startTime", _startTime);
        mv.addObject("_endTime", _endTime);
        // 物流部人员（出库操作人）
        List<User> logisticsUserList = userService.getUserListByPositType(SysOptionConstant.ID_313,
                session_user.getCompanyId());
        mv.addObject("logisticsUserList", logisticsUserList);
        // 查询出库记录
        warehouseGoodsOperateLog.setOperateType(2);
        warehouseGoodsOperateLog.setIsEnable(1);
        warehouseGoodsOperateLog.setCompanyId(session_user.getCompanyId());
        Map<String, Object> wlog = null;
        try {
            wlog = warehouseGoodsOperateLogService.getWGOlogList(warehouseGoodsOperateLog, page);
        } catch (Exception e) {
            logger.error("warehouseOutLogList:", e);
        }
        mv.addObject("list", wlog!=null?wlog.get("list"):Collections.emptyList());
        mv.addObject("page", wlog!=null?wlog.get("page"):null);
        mv.setViewName("logistics/warehouseOut/list_warehouseout");
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 产品出库导出
     *
     * @param request
     * @param response
     * @param warehouseGoodsOperateLog
     * @Note <b>Author:</b> scott <br>
     * <b>Date:</b> 2017年9月5日 下午1:24:45
     */
    @RequestMapping(value = "/exportOutRlist")
    public void exportOutRlist(HttpServletRequest request, HttpServletResponse response,
                               WarehouseGoodsOperateLog warehouseGoodsOperateLog) {
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
            writeTitleContent2(sh);

            Row row_value = null;
            Page page = null;
            Map<String, Object> map = null;
            Page pageTotal = null;

            // ------------------定义表头----------------------------------------
            int page_size = 1000;// 数据库中每次查询条数
            page = getPageTag(request, 1, page_size);
            map = warehouseGoodsOperateLogService.getWGOlogList(warehouseGoodsOperateLog, page);
            pageTotal = (Page) map.get("page");
            for (int i = 1; i <= pageTotal.getTotalPage(); i++) {
                page = getPageTag(request, i, page_size);
                map = warehouseGoodsOperateLogService.getWGOlogList(warehouseGoodsOperateLog, page);
                List<WarehouseGoodsOperateLog> wgLists = new ArrayList<>();
                wgLists = (List<WarehouseGoodsOperateLog>) map.get("list");
                for (WarehouseGoodsOperateLog wg : wgLists) {
                    row_value = sh.createRow(bodyRowCount);

                    row_value.createCell(0).setCellValue(wg.getAddTime());
                    row_value.createCell(1).setCellValue(wg.getBuyorderNo());
                    row_value.createCell(2).setCellValue(wg.getGoodsName());
                    row_value.createCell(3).setCellValue(wg.getBrandName());
                    row_value.createCell(4).setCellValue(wg.getModel());
                    row_value.createCell(5).setCellValue(wg.getNum());
                    row_value.createCell(6).setCellValue(wg.getUnitName());
                    row_value.createCell(7).setCellValue(wg.getPrice().toString());
                    // row_value.createCell(8).setCellValue((wg.getNum().multiply(new
                    // BigInteger(wg.getPrice())).toString());
                    row_value.createCell(9).setCellValue(wg.getBuytraderName());
                    row_value.createCell(10).setCellValue(wg.getBarcode());
                    row_value.createCell(11).setCellValue(wg.getBarcodeFactory());
                    row_value.createCell(12).setCellValue(wg.getExpirationDate());
                    row_value.createCell(13).setCellValue(wg.getBatchNumber());
                    row_value.createCell(14).setCellValue(wg.getWarehouseArea());
                    row_value.createCell(15).setCellValue(wg.getComments());
                    row_value.createCell(16).setCellValue(wg.getOperator());
                    row_value.createCell(17).setCellValue("复核通过");
                    row_value.createCell(18).setCellValue("shawn.xiong");
                    bodyRowCount++;
                }
                wgLists.clear();
            }
            wb.write(out);
            out.close();
            wb.dispose();
        } catch (Exception e) {
            logger.error("exportOutRlist:", e);
        }
    }

    public void writeTitleContent2(Sheet sh) {
        Row row = sh.createRow(0);
        row.createCell(0).setCellValue("出库时间");
        row.createCell(1).setCellValue("出库单据");
        row.createCell(2).setCellValue("产品名称");
        row.createCell(3).setCellValue("品牌");
        row.createCell(4).setCellValue("型号");
        row.createCell(5).setCellValue("数量");
        row.createCell(6).setCellValue("单位");
        row.createCell(7).setCellValue("单价");
        row.createCell(8).setCellValue("总价");
        row.createCell(9).setCellValue("收货方");
        row.createCell(10).setCellValue("贝登条码");
        row.createCell(11).setCellValue("厂商条码");
        row.createCell(12).setCellValue("效期截止日期");
        row.createCell(13).setCellValue("批次号");
        row.createCell(14).setCellValue("出库操作人");
        row.createCell(15).setCellValue("出库位置");
        row.createCell(16).setCellValue("仓存备注");
        row.createCell(17).setCellValue("复核结果");
        row.createCell(18).setCellValue("复核人");
    }

    /**
     * <b>Description:</b><br>
     * 快递列表
     *
     * @param request
     * @param express
     * @param pageNo
     * @param pageSize
     * @param session
     * @param searchBeginTime
     * @param searchEndTime
     * @return
     * @Note <b>Author:</b> scott <br>
     * <b>Date:</b> 2017年8月31日 下午2:02:43
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "/getExpressListPage")
    public ModelAndView getExpressListPage(HttpServletRequest request, Express express,
                                           @RequestParam(required = false, defaultValue = "1") Integer pageNo, // required
                                           // false:可不传入pageNo这个参数，true必须传入，defaultValue默认值，若无默认值，使用Page类中的默认值
                                           @RequestParam(required = false) Integer pageSize, HttpSession session,
                                           @RequestParam(required = false, value = "_startTime") String _startTime,
                                           @RequestParam(required = false, value = "_endTime") String _endTime,
                                           @RequestParam(required = false, value = "searchDateType") String searchDateType) {
        // 获取session中user信息
        User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
        ModelAndView mv = new ModelAndView();
        Page page = getPageTag(request, pageNo, pageSize);

        // 获取物流公司
        List<Logistics> logisticsList = logisticsService.getAllLogisticsList(session_user.getCompanyId());
        mv.addObject("logisticsList", logisticsList);

        // 获取所有用户
        User user = new User();
        user.setCompanyId(session_user.getCompanyId());
        List<User> allUser = userService.getAllUser(user);
        mv.addObject("userList", allUser);
        express.setCompanyId(session_user.getCompanyId());

        if (StringUtils.isBlank(searchDateType)) {// 新打开
            /*
             * _startTime = DateUtil.getFirstDayOfMonth(0);//当月第一天 _endTime =
             * DateUtil.getNowDate("yyyy-MM-dd");//当前日期
             */
        } else {
            mv.addObject("de_startTime", DateUtil.getFirstDayOfMonth(0));
            mv.addObject("de_endTime", DateUtil.getNowDate("yyyy-MM-dd"));
        }
        mv.addObject("searchDateType", searchDateType);

        if (_startTime != null && !"".equals(_startTime)) {
            express.setBeginTime(DateUtil.convertLong(_startTime + " 00:00:00", ""));
        }
        if (_endTime != null && !"".equals(_endTime)) {
            express.setEndTime(DateUtil.convertLong(_endTime + " 23:59:59", ""));
        }
        mv.addObject("_startTime", _startTime);
        mv.addObject("_endTime", _endTime);

        Map<String, Object> expressMap = null;
        expressMap = expressService.getExpressListPage(express, page);
        List<Express> l = (List<Express>) expressMap.get("list");
        mv.addObject("express", express);
        mv.addObject("list", expressMap.get("list"));
        mv.addObject("page", expressMap.get("page"));

        Express eps = expressService.getCntExpress(express);
        mv.addObject("cnt", eps.getCnt());
        mv.setViewName("logistics/warehouseOut/list_expressDetail");
        return mv;
    }

    public boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * <b>Description:</b><br>
     * 新增快递单
     *
     * @param session
     * @param buyorderVo
     * @return
     * @Note <b>Author:</b> scott <br>
     * <b>Date:</b> 2017年8月31日 下午5:26:18
     */
    @FormToken(save = true)
    @ResponseBody
    @RequestMapping(value = "/addExpress")
    public ModelAndView addExpress(HttpSession session, Saleorder saleorder) {
        ModelAndView mv = new ModelAndView();
        User curr_user = (User) session.getAttribute(ErpConst.CURR_USER);
        Express express = new Express();
        Map<Integer, Object> map = new HashMap<Integer, Object>();
        List<Integer> relatedIds = new ArrayList<Integer>();
        List<SaleorderGoods> saleorderGoodsList = new ArrayList<SaleorderGoods>();
        List<AfterSalesGoodsVo> aList = new ArrayList<AfterSalesGoodsVo>();
        User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
        if (saleorder.getBussinessType() == 1) {
            saleorder.seteFlag("1");
            saleorder.setOptType("496");
            saleorderGoodsList = saleorderService.getSaleorderGoodsInfo(saleorder);
            express.setBusinessType(SysOptionConstant.ID_496);
            for (SaleorderGoods saleorderGood : saleorderGoodsList) {
                relatedIds.add(saleorderGood.getSaleorderGoodsId());
                map.put(saleorderGood.getSaleorderGoodsId(), 0);
            }
            mv.addObject("saleorderGoodsList", saleorderGoodsList);
            mv.setViewName("logistics/warehouseOut/add_express");
        } else if (saleorder.getBussinessType() == 3) {//外借
            LendOut lendout = new LendOut();
            lendout.setLendOutId(saleorder.getSaleorderId());
            lendout = warehouseOutService.getLendOutInfo(lendout);
            Goods goods = new Goods();
            goods.setGoodsId(lendout.getGoodsId());
            goods = goodsService.getGoodsById(goods);
            lendout.setBusinessType(660);
            //已快递出库数量
            Integer eNum =warehouseOutService.getLendOutKdNum(lendout);
            goods.seteNum(eNum);
            lendout.setBusinessType(10);
            //出库数量
            Integer deliveryNum = warehouseOutService.getLendOutdeliveryNum(lendout);
            goods.setDeliveryNum(-deliveryNum);
            express.setBusinessType(SysOptionConstant.ID_660);
            relatedIds.add(saleorder.getSaleorderId());
            List<Goods> list = new ArrayList<Goods>();
            list.add(goods);
            lendout.setGoodslist(list);
            mv.addObject("lendout", lendout);
            mv.setViewName("logistics/warehouseOut/add_lendout_express");
        } else if (saleorder.getBussinessType() != 1 && saleorder.getBussinessType() != 3) {
            AfterSalesVo asv = new AfterSalesVo();
            asv.seteFlag("1");
            asv.setAfterSalesId(saleorder.getBussinessId());
            asv.setCompanyId(curr_user.getCompanyId());
            if (saleorder.getShType() == 546 || saleorder.getShType() == 547) {
                asv.setBusinessType(2);
            } else if (saleorder.getShType() == 540) {
                asv.setBusinessType(1);
            }
            asv.setIsNormal(1);
            aList = afterSalesOrderService.getAfterSalesGoodsVoList(asv, session);
            express.setBusinessType(SysOptionConstant.ID_582);
            for (AfterSalesGoodsVo av : aList) {
                relatedIds.add(av.getAfterSalesGoodsId());
                map.put(av.getAfterSalesGoodsId(), 0);
            }
            mv.addObject("afterSales",asv);
            mv.addObject("afterSalesGoodsList", aList);
            mv.setViewName("logistics/businessWarehouseOut/add_express");
        }
        express.setRelatedIds(relatedIds);
        try {
            List<Express> expressList = new ArrayList<>();
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
            String now = DateUtil.getNowDate("YYYY-MM-dd");
            mv.addObject("now", now);
            mv.addObject("expressNumMap", map);
            mv.addObject("expressList", expressList);
            mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson("")));
        } catch (Exception e) {
            logger.error("warehourse addExpress:", e);
        }
        // 获取物流公司列表
        List<Logistics> logisticsList = logisticsService.getLogisticsList(session_user.getCompanyId());

        if (null != saleorder.getSaleorderId()) {
            saleorder = saleorderService.getSaleOrderInfo(saleorder);
        }

        mv.addObject("saleorder", saleorder);
        mv.addObject("logisticsList", logisticsList);
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 按时间查询批次出库商品
     *
     * @param session
     * @param saleorder
     * @return
     * @Note <b>Author:</b> scott <br>
     * <b>Date:</b> 2017年9月29日 下午6:39:00
     */
    @ResponseBody
    @RequestMapping(value = "/getGoodsExpress")
    public ResultInfo<List<WarehouseGoodsOperateLog>> getGoodsExpress(HttpSession session, Saleorder saleorder) {
        String date[] = saleorder.getOutGoodsTime().split("_");
        String dt = "";
        for (int i = 0; i < date.length; i++) {

            if (i == date.length - 1) {
                dt += DateUtil.convertLong(date[i], "") + "";
            } else {
                dt += DateUtil.convertLong(date[i], "") + ",";
            }
        }
        saleorder.setOutGoodsTime(dt);
        ResultInfo<List<WarehouseGoodsOperateLog>> resultInfo = new ResultInfo<List<WarehouseGoodsOperateLog>>();
        saleorder.setIsSM("1");
        List<WarehouseGoodsOperateLog> warehouseOutList = warehouseOutService.getOutDetil(saleorder);
        if (null != warehouseOutList) {
            resultInfo.setCode(0);
            resultInfo.setMessage("操作成功");
            resultInfo.setData(warehouseOutList);
        }
        return resultInfo;
    }

    /**
     * <b>Description:</b><br>
     * 批量修改物流状态信息
     *
     * @param expressIds
     * @param session
     * @return
     * @Note <b>Author:</b> scott <br>
     * <b>Date:</b> 2017年9月4日 下午6:31:27
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "/editExpressStatus")
    @SystemControllerLog(operationType = "edit", desc = "保存批量修改物流状态信息")
    public ResultInfo editExpressStatus(String expressIds, HttpSession session, String beforeParams) {
        ResultInfo<?> result = new ResultInfo<>();
        // 获取session中user信息
        User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
        List<Express> epList = new ArrayList<Express>();
        if (null != expressIds) {
            // 切割拼接成的字符串
            String[] params = expressIds.split("_");
            for (int i = 0; i < params.length; i++) {
                String sIds[] = params[i].split("&");
                Express ep = new Express();
                Integer epId = Integer.parseInt(sIds[0]);
                if (sIds.length > 1) {
                    ep.setSaleorderId(Integer.parseInt(sIds[1]));
                    ep.setBusinessType(Integer.parseInt(sIds[2]));
                }
                ep.setExpressId(epId);
                ep.setArrivalStatus(2);// 0未签收2已签收
                ep.setUpdater(session_user.getUserId());
                ep.setModTime(DateUtil.sysTimeMillis());
                ep.setArrivalTime(DateUtil.sysTimeMillis());
                epList.add(ep);
            }
        }
        try {
            result = expressService.editBatchExpress(epList);

            // 签收消息推送
            List<SaleorderVo> resaleorderList = (List<SaleorderVo>) result.getData();

            if (CommonConstants.ONE.equals(session_user.getCompanyId()) && CommonConstants.SUCCESS_CODE.equals(result.getCode())) {
         /*       new Thread() {// 异步推送数据
                    @Override
                    public void run() {*/
                        for (Express e : epList) {
                            if (SysOptionConstant.ID_496.equals(e.getBusinessType()) && null != e.getSaleorderId()) {
                                vedengSoapService.orderSyncWeb(e.getSaleorderId());
                                // 修改快递单状态
                                // 根据订单的Id查询订单信息，如果是耗材的销售订单，需要推物流信息以及订单的状态到耗材商城
                                Saleorder saleOrder = new Saleorder();
                                saleOrder.setSaleorderId(e.getSaleorderId());
                                saleOrder = saleorderService.getBaseSaleorderInfo(saleOrder);
                                if (saleOrder.getOrderType() == 5) { // 如果是耗材商城的订单
                                    // 根据快递单查询该快递单下的商品列表
                                    Map<String, Object> map = new HashMap<String, Object>();
                                    map.put("saleOrder", saleOrder);
                                    map.put("type", 1);
                                    // 查询该快递单下的商品信息列表
                                    List<SaleorderGoods> goodsList = expressService.getSaleorderGoodsListByexpressId(e.getExpressId());
                                    map.put("goodsList", goodsList);
                                    // 调用ERP推送到耗材的接口
                                    hcSaleorderService.putExpressToHC(map);
                                }
                                //如果订单是全部发货的话
                                if (null != saleOrder && saleOrder.getArrivalStatus() == 2) {
                                    //全部收货签收发送微信消息给注册用
                                        //更新前台BD订单的状态
                                        Saleorder sv = saleorderMapper.getSaleOrderById(saleOrder.getSaleorderId());
                                        if (sv.getOrderType() == 1) {
                                            OrderData orderData = new OrderData();
                                            orderData.setOrderNo(sv.getSaleorderNo());
                                            WebAccount web = webAccountMapper.getWenAccountInfoByMobile(sv.getCreateMobile());
                                            orderData.setAccountId(web.getWebAccountId());
                                            orderData.setOrderStatus(5);//订单完成
                                            try {
                                                String url = mjxUrl + "/order/updateOrderConfirmFinish";
                                                String json = JsonUtils.translateToJson(orderData);
                                                CloseableHttpClient httpClient = HttpClientBuilder.create().build();
                                                JSONObject result2 = NewHttpClientUtils.httpPost(url, JSONObject.fromObject(orderData));
                                            } catch (Exception ee) {
                                                logger.error(ee.toString());
                                            }
                                        }

                                        // 用于返回避免再次查询
                                        Map sTempMap = null;
                                        //全部收货签收发送微信消息给注册用户(如果订单是VS，JX，DH的订单)
                                        ResultInfo senResult = expressService.sendWxMessageForArrival(e.getSaleorderId());
                                        if (null != senResult) {
                                            sTempMap = (Map) senResult.getData();
                                        }
                                        try {
                                            if (null != sTempMap) {
                                                if (CommonConstants.ONE.equals(sendYxgWxTempMsgFlag)) {
                                                    // 医械购 微信推送 订单签收 消息
                                                    sendTemplateMsgHcForOrderOk(saleOrder, sTempMap);
                                                }
                                                //贝登订单签收公众号
                                                sendOrderForMsg(saleOrder, sTempMap);
                                            }
                                        } catch (Exception e1) {
                                            logger.error("sendTemplateMsgHcForOrderOk:", e1);

                                    }
                                }
                            }
                        }
              /*      }
                }.start();*/
            }

        } catch (Exception e) {
            logger.error("warehourse editExpressStatus:", e);
        }
        return result;
    }

    /**
     *
     * <b>Description:</b><br>
     * 快递单导出
     *
     * @param request
     * @param response
     * @param express
     * @Note <b>Author:</b> scott <br>
     *       <b>Date:</b> 2017年9月5日 上午8:37:00
     */
    /*
     * @RequestMapping(value = "/exportExpresslist") public void
     * export(HttpServletRequest request, HttpServletResponse response, Express
     * express) { response.setHeader("Content-type", "text/html;charset=UTF-8");
     * response.setContentType("multipart/form-data"); String fileName =
     * System.currentTimeMillis() + ".xlsx";
     * response.setHeader("Content-Disposition", "attachment;fileName=" +
     * fileName);
     *
     * try { int sheetNum = 1;// 工作薄sheet编号 int bodyRowCount = 1;// 正文内容行号
     *
     * OutputStream out = response.getOutputStream(); SXSSFWorkbook wb = new
     * SXSSFWorkbook(10); Sheet sh = wb.createSheet("工作簿" + sheetNum);
     * writeTitleContent(sh);
     *
     * Row row_value = null; Page page = null; Map<String,Object> map = null;
     * Page pageTotal = null;
     *
     * // ------------------定义表头---------------------------------------- int
     * page_size = 1000;// 数据库中每次查询条数 page = getPageTag(request,1,page_size);
     * map = expressService.getExpressListPage(express, page); pageTotal =
     * (Page)map.get("page"); for (int i = 1; i <= pageTotal.getTotalPage();
     * i++) { page = getPageTag(request,i,page_size); map =
     * expressService.getExpressListPage(express, page); List<Express>
     * expressLists = new ArrayList<>(); expressLists =
     * (List<Express>)map.get("list"); for (Express ep : expressLists) {
     * row_value = sh.createRow(bodyRowCount);
     *
     * row_value.createCell(0).setCellValue(ep.getLogisticsNo());
     * row_value.createCell(1).setCellValue(ep.getLogisticsCompanyName());
     * row_value.createCell(2).setCellValue(ep.getAmount().toString());
     * row_value.createCell(3).setCellValue(ep.getXsNo());
     * row_value.createCell(4).setCellValue(ep.getCreatName());
     * row_value.createCell(5).setCellValue(ep.getFhTime());
     * row_value.createCell(6).setCellValue(ep.getSjName());
     * row_value.createCell(7).setCellValue(ep.getLogisticsComments()); String
     * st =""; if(ep.getArrivalStatus()==0){ st="未签收"; }else
     * if(ep.getArrivalStatus()==2){ st="已签收"; }
     * row_value.createCell(8).setCellValue(st);
     * row_value.createCell(9).setCellValue(ep.getArrivalTime());
     * bodyRowCount++; } expressLists.clear(); }
     *
     * wb.write(out); out.close(); wb.dispose();
     *
     * } catch (Exception e) { logger.error(Contant.ERROR_MSG, e); } }
     *
     * public void writeTitleContent(Sheet sh) { Row row = sh.createRow(0);
     * row.createCell(0).setCellValue("快递单号");
     * row.createCell(1).setCellValue("快递公司");
     * row.createCell(2).setCellValue("运费");
     * row.createCell(3).setCellValue("业务单据");
     * row.createCell(4).setCellValue("发件人");
     * row.createCell(5).setCellValue("发货日期");
     * row.createCell(6).setCellValue("收件名称");
     * row.createCell(7).setCellValue("备注");
     * row.createCell(8).setCellValue("签收状态");
     * row.createCell(9).setCellValue("签收时间"); }
     */

    /**
     * <b>Description:</b><br>
     * 打印出库单
     *
     * @param saleorder
     * @return
     * @throws ShowErrorMsgException
     * @Note <b>Author:</b> scott <br>
     * <b>Date:</b> 2017年9月8日 下午1:59:22
     */
    @ResponseBody
    @RequestMapping(value = "/printOutOrder")
    public ModelAndView printOutOrder(HttpServletRequest request, String wdlIds, String type_f, Saleorder saleorder)
            throws ShowErrorMsgException {
        User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        ModelAndView mv = new ModelAndView();

        if (saleorder.getBussinessType() == 660) {//外借
            LendOut lendout = new LendOut();
            lendout.setLendOutId(saleorder.getOrderId());
            lendout =  warehouseOutService.getLendOutInfo(lendout);
            saleorder.setBussinessType(496);
            Saleorder s = new Saleorder();
            s.setTraderName(lendout.getTraderName());
            TraderAddress traderAddress  = traderCustomerService.getTraderAddressById(lendout.getTakeTraderAddressId());
            s.setTakeTraderAddress(traderAddress.getAddress());
            TraderContactGenerate tradercontact = traderCustomerService.getTraderContactByTraderContactId(lendout.getTakeTraderContactId());
            s.setTakeTraderContactName(tradercontact.getName());
            s.setTakeTraderContactMobile(tradercontact.getMobile());
            s.setTakeTraderContactTelephone(tradercontact.getTelephone());
            mv.addObject("bussinessNo", lendout.getLendOutNo());
            mv.addObject("saleorder", s);
        } else if (saleorder.getBussinessType() != 496) {// 售后
            AfterSalesDetail av = new AfterSalesDetail();
            AfterSales as = new AfterSales();
            as.setAfterSalesId(saleorder.getOrderId());
            av = afterSalesOrderService.selectadtById(as);
            mv.addObject("afterSalesDetail", av);
        } else {// 销售
            saleorder.setSaleorderId(saleorder.getOrderId());
            Saleorder sd = saleorderService.getBaseSaleorderInfo(saleorder);
            mv.addObject("saleorder", sd);
            mv.addObject("bussinessNo", sd.getSaleorderNo());
        }

        List<WarehouseGoodsOperateLog> woList = new ArrayList<WarehouseGoodsOperateLog>();
        List<WarehouseGoodsOperateLog> firstInfo = new ArrayList<WarehouseGoodsOperateLog>();
		// 根据出库id获取出库信息
        WarehouseGoodsOperateLog w = new WarehouseGoodsOperateLog();
        w.setOperatorfalg(ErpConst.PRINT_ORDER);
        saleorder.setOptType(type_f);
        saleorder.setSearch(wdlIds);
        //组装出库单内出库记录id
        List<Integer>  idList = warehouseOutService.getPrintOutIdListByType(saleorder);
        w.setIdList(idList);

        HashMap<String,String> paramMap = new HashMap<>();
        paramMap.put("type_f",type_f);
        //设置出库单类型
        paramMap = updateTypeAndPrintFlag(paramMap);
        Integer  printFlag = Integer.valueOf(paramMap.get("printFlag"));
        type_f = paramMap.get("type_f");
        boolean  hcPrintOutflag = paramMap.get("hcPrintOutflag") != null ? true :false;

	if(CollectionUtils.isNotEmpty(w.getIdList())) {
//	    w.setCreatorNm(value);
	    int parseInt = Integer.parseInt(type_f);
		w.setYwType(parseInt);
	    woList = warehouseGoodsOperateLogService.getWLById(w);
	    firstInfo =  warehouseGoodsOperateLogService.getfirstRegistrationInfo(woList);//取首营的信息

        if(printFlag.equals(ErpConst.PRICE_PRINT_ORDERTYPE)){
            //计算单种商品总价
            woList = warehouseOutService.getPrintOutSkuAmount(woList);
            //计算出库单商品总价
            BigDecimal totalPrice = warehouseOutService.getPrintOutTotalPrice(woList);
            mv.addObject("totalPrice",totalPrice);
            mv.addObject("ChineseTotal",DigitToChineseUppercaseNumberUtils.numberToChineseNumber(totalPrice));
        }
	}
	//TODO 判定 产品注册证号/者备案凭证编号 ,生产企业,生产企业许可证号/备案凭证编号	,储运条件  字段
	Integer titleType = null;//商品类级标识
    titleType = warehouseOutService.updatefirstRegistraionInfo(woList,firstInfo,titleType,type_f,printFlag);
	if(hcPrintOutflag) {
        HashMap<String,BigDecimal> amoutInfoMap = warehouseOutService.addMvAmoutinfo(woList,saleorder);
        mv.addObject("expressPrice",amoutInfoMap.get("expressPrice"));
        mv.addObject("totalPrice",amoutInfoMap.get("totalPrice"));
        mv.addObject("realTotalPrice",amoutInfoMap.get("realTotalPrice"));
        mv.addObject("couponsPrice",amoutInfoMap.get("couponsPrice"));
        mv.addObject("hcPrintOutflag",hcPrintOutflag);
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
	    pageTotalNum += (0 - wl.getNum());
	    if (null != wl.getPrice()) {
		pageTotalPrice = pageTotalPrice.add(wl.getPrice().multiply(new BigDecimal(0 - wl.getNum())));
	    }
	    if (wl.getAddTime() > time) {
		time = wl.getAddTime();
		wLog.setCreator(wl.getCreator());
	    }
	}
	    User u = userService.getUserById(wLog.getCreator());
        Long currTime = DateUtil.sysTimeMillis();
        if(printFlag.equals(ErpConst.KYG_PRINT_ORDERTYPE) ||
           printFlag.equals(ErpConst.PRICE_PRINT_ORDERTYPE )||
           printFlag.equals(ErpConst.NOPRICE_PRINT_ORDERTYPE)){
            currTime = warehouseOutService.getLastOutTime(w);
        }
        if (CollectionUtils.isNotEmpty(woList)) {
            mv.addObject("timeStr", woList.get(0).getTimeStr());
        } else {
            mv.addObject("timeStr", "");
        }
        mv.addObject("currTime", currTime);
        mv.addObject("outName", u == null ? "" : u.getUsername());
        mv.addObject("userName", user.getUsername());
        mv.addObject("woList", woList);
        mv.addObject("type", type_f);
        mv.addObject("bussinessNo", saleorder.getBussinessNo());
        mv.addObject("bussinessType", saleorder.getBussinessType());
        mv.addObject("titleType", titleType);
        mv.addObject("printFlag",printFlag);
        if (user.getCompanyId() == 1) {
            mv.setViewName("logistics/warehouseOut/print_out_order");
        }
        // 分公司
        else {
            // 当前页的 总价格
            mv.addObject("pageTotalPrice", pageTotalPrice.compareTo(zioe) > 0 ? pageTotalPrice : null);
            // 大写汉字 金额总数
            mv.addObject("chineseNumberTotalPrice", pageTotalPrice.compareTo(zioe) > 0
                    ? DigitToChineseUppercaseNumberUtils.numberToChineseNumber(pageTotalPrice) : null);
            // 当前页的计数 总数
            mv.addObject("pageTotalNum", pageTotalNum);
            String nowDade = DateUtil.DateToString(new Date(), "yyyy-MM-dd");
            // 日期
            mv.addObject("nowDade", nowDade);
            mv.setViewName("logistics/warehouseOut/print_out_order_companyId_5");
        }
        return mv;
    }

    private HashMap<String,String> updateTypeAndPrintFlag(HashMap<String,String> paramMap) {
        String type_f = paramMap.get("type_f");
        Integer printFlag = 0 ;
        if(StringUtils.isBlank(type_f)){
            type_f=ErpConst.PRINT_OUT_TYPE_F;
        }
        if(type_f.equals(ErpConst.PRINT_KYG_TYPE_F)){
            type_f = ErpConst.PRINT_OUT_TYPE_F;
            printFlag = ErpConst.KYG_PRINT_ORDERTYPE;
        }else if(type_f.equals(ErpConst.PRINT_PRICE_TYPE_F) ||
                type_f.equals(ErpConst.PRINT_FLOWERORDER_TYPE_F)){
            type_f = ErpConst.PRINT_EXPIRATIONDATE_TYPE_F;
            printFlag = ErpConst.PRICE_PRINT_ORDERTYPE;
        }else if(type_f.equals(ErpConst.PRINT_NOPRICE_TYPE_F)){
            type_f = ErpConst.PRINT_EXPIRATIONDATE_TYPE_F;
            printFlag = ErpConst.NOPRICE_PRINT_ORDERTYPE;
        }else  if(type_f.equals(ErpConst.PRINT_EXPRESS_HC_TYPE_F)) {
            paramMap.put("hcPrintOutflag","1");
            type_f = ErpConst.PRINT_HC_TYPE_F;
        }else  if(type_f.equals(ErpConst.PRINT_EXPRESS_KYG_TYPE_F)){
            type_f = ErpConst.PRINT_OUT_TYPE_F;
            printFlag = ErpConst.KYG_PRINT_ORDERTYPE;
        }else if(type_f.equals(ErpConst.PRINT_EXPRESS_PRICE_TYPE_F)){
            type_f = ErpConst.PRINT_EXPIRATIONDATE_TYPE_F;
            printFlag = ErpConst.PRICE_PRINT_ORDERTYPE;
        }else if(type_f.equals(ErpConst.PRINT_EXPRESS_NOPRICE_TYPE_F)){
            type_f = ErpConst.PRINT_EXPIRATIONDATE_TYPE_F;
            printFlag = ErpConst.NOPRICE_PRINT_ORDERTYPE;
        }
        paramMap.put("type_f",type_f);
        paramMap.put("printFlag",printFlag.toString());
        return paramMap;
    }

    /**
     * <b>Description:</b><br>
     * 打印送货单
     *
     * @param request
     * @param btype_sh     业务类型
     * @param type_sh      是否效期
     * @param saleorder
     * @param expressId_xs 快递表主键
     * @return
     * @throws ShowErrorMsgException
     * @Note <b>Author:</b> scott <br>
     * <b>Date:</b> 2018年6月5日 下午2:16:47
     */
    @ResponseBody
    @RequestMapping(value = "/printShOutOrder")
    public ModelAndView printShOutOrder(HttpServletRequest request, String btype_sh, Integer orderId,
                                        Integer expressId_xs, String bussinessNo) throws ShowErrorMsgException {
        User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        ModelAndView mv = new ModelAndView();

        // 公共对象saleorder
        Saleorder saleorder = new Saleorder();

        // 销售出库
        if ("496".equals(btype_sh)) {
            Saleorder s = new Saleorder();
            s.setSaleorderId(orderId);
            Saleorder sd = saleorderService.getBaseSaleorderInfo(s);
            mv.addObject("saleorder", sd);
        } else if ("660".equals(btype_sh)) {//外借出库
            LendOut lendout = new LendOut();
            lendout.setLendOutId(orderId);
            lendout = warehouseOutService.getLendOutInfo(lendout);
//		saleorder.setBussinessType(496);
            Saleorder s = new Saleorder();
            s.setTraderName(lendout.getTraderName());
            TraderAddress traderAddress = traderCustomerService.getTraderAddressById(lendout.getTakeTraderAddressId());
            if (traderAddress != null) {
                s.setTakeTraderAddress(traderAddress.getAddress());
            }
            TraderContactGenerate tradercontact = traderCustomerService.getTraderContactByTraderContactId(lendout.getTakeTraderContactId());
            s.setTakeTraderContactName(tradercontact.getName());
            s.setTakeTraderContactMobile(tradercontact.getMobile());
            s.setTakeTraderContactTelephone(tradercontact.getTelephone());
            mv.addObject("bussinessNo", lendout.getLendOutNo());
            mv.addObject("saleorder", s);
        } else {// 售后出库
            AfterSalesDetail av = new AfterSalesDetail();
            AfterSales as = new AfterSales();
            as.setAfterSalesId(orderId);
            av = afterSalesOrderService.selectadtById(as);
            mv.addObject("afterSalesDetail", av);
        }
        // 快递单
        Express express = new Express();
        if ("496".equals(btype_sh)) {
            express.setBusinessType(Integer.parseInt(btype_sh));
        } else if ("660".equals(btype_sh)) {//外借
            express.setBusinessType(660);
        } else {
            express.setBusinessType(582);
        }
        express.setCompanyId(user.getCompanyId());
        express.setExpressId(expressId_xs);
        List<Express> expressList = new ArrayList<>();
        try {
            expressList = expressService.getExpressList(express);
            mv.addObject("express", expressList.get(0));
        } catch (Exception e) {
            logger.error("printShOutOrder:", e);
        }

        // 出库产品
        if ("496".equals(btype_sh)) {
            saleorder.setBussinessType(Integer.parseInt(btype_sh));
        } else if ("660".equals(btype_sh)) {//外借
            saleorder.setBussinessType(660);
            btype_sh = "496";
        } else {
            saleorder.setBussinessType(582);
        }
        saleorder.setBussinessId(orderId);
        saleorder.setOrderId(expressId_xs);
        saleorder.setCompanyId(user.getCompanyId());
        List<WarehouseGoodsOperateLog> OutList = warehouseOutService.getOutDetilList(saleorder);
        mv.addObject("OutList", OutList);

        User u = userService.getUserById(expressList.get(0).getCreator());

        Long currTime = DateUtil.sysTimeMillis();
        mv.addObject("currTime", currTime);
        mv.addObject("outName", u == null ? "" : u.getUsername());
        mv.addObject("userName", user.getUsername());
        mv.addObject("woList", OutList);
        mv.addObject("bussinessType", btype_sh);
        mv.addObject("bussinessNo", bussinessNo);

        mv.setViewName("logistics/warehouseOut/print_out_order_sh");
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 打印拣货单
     *
     * @param saleorder
     * @return
     * @Note <b>Author:</b> scott <br>
     * <b>Date:</b> 2017年9月8日 下午3:28:52
     */
    @ResponseBody
    @RequestMapping(value = "/printPickOrder")
    public ModelAndView printPickOrder(String salegoodsIds, HttpSession session) {

        ModelAndView mv = new ModelAndView();
        List<WarehousePicking> wList = new ArrayList<WarehousePicking>();
        if (null != salegoodsIds) {
            // 切割拼接成的字符串
            String[] params = salegoodsIds.split("_");
            for (int i = 0; i < params.length; i++) {
                // 查询拣货记录
            }
        }
        mv.addObject("warehousePickList", wList);
        mv.setViewName("logistics/warehouseOut/print_pick_order");
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 撤销出库
     *
     * @param wlogIds
     * @param session
     * @return
     * @Note <b>Author:</b> scott <br>
     * <b>Date:</b> 2017年9月14日 下午3:52:41
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "/editIsEnableWlog")
    @SystemControllerLog(operationType = "delete", desc = "删除出库记录")
    public ResultInfo editIsEnableWlog(String wlogIds, String type, HttpSession session, Integer lendOutId, Integer num) {
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
				 Boolean expressflag = warehouseOutService.isEnableExpress(wlogId);
                if(expressflag){
                    result.setMessage("存在快递单中关联记录不可撤销!");
                    return result;
                }
                //redis分布式锁,出库记录id为key
                String key = ErpConst.WLOG + params[i];
                Boolean lock = redisUtils.tryGetDistributedLock(key, UUID.randomUUID().toString(), 300000);
                //判断此记录是否可撤销
                Integer isEnableFlag = warehouseGoodsOperateLogService.getWarehouseIsEnable(wlogId);
                if (isEnableFlag.equals(1)) {
                    //删除出库时的缓存数据,释放锁
                    Barcode barcode = warehouseOutService.getBarcodeByWarehouseGoodsOperateLogId(wlogId);
                    String lockKey = ErpConst.BARCODE + barcode.getBarcode();
                    String requestId = redisUtils.get(lockKey);
                    boolean releaselock = redisUtils.releaseDistributedLock(lockKey, requestId);
                    wlog.setWarehouseGoodsOperateLogId(wlogId);
                    wlog.setBarcode(barcode.getBarcode());
                    if (type != null && !"".equals(type)) {
                        wlog.setOperateType(4);
                    } else {
                        wlog.setOperateType(2);
                    }
                    wlog = warehouseGoodsOperateLogService.updateIsActionGoods(wlog);
                    wlog.setUpdater(session_user.getUserId());
                    wlog.setModTime(DateUtil.sysTimeMillis());
                    wlog.setCompanyId(session_user.getCompanyId());
                    // 撤销出库记录
                    wlog.setIsEnable(0);
                    wlogList.add(wlog);
                }
            }
        }
        try {
            if (CollectionUtils.isEmpty(wlogList)) {
                result.setMessage("该记录已撤销,请刷新页面");
                return result;
            }
            result = warehouseGoodsOperateLogService.editIsEnableWlog(wlogList);
            if ("3".equals(type)) {
                LendOut lendout = LendOut.getinstance();
                lendout.setLendOutId(lendOutId);
                lendout =  warehouseOutService.getLendOutInfo(lendout);
                lendout.setModTime(System.currentTimeMillis());
                lendout.setNum(-num);
                Integer i = warehouseOutService.updateLendoutDeliverNum(lendout);
            }
        } catch (Exception e) {
            logger.error("editIsEnableWlog:Erro:", e);
        }
        return result;
    }

    /**
     * <b>Description:</b><br>
     * 商品库存统计
     *
     * @param request
     * @param goods
     * @param pageNo
     * @param pageSize
     * @param session
     * @return
     * @Note <b>Author:</b> scott <br>
     * <b>Date:</b> 2017年9月15日 下午4:27:11
     */
    @ResponseBody
    @RequestMapping(value = "/listGoodsStock")
    public ModelAndView listGoodsStock(HttpServletRequest request, Goods goods,
                                       @RequestParam(required = false, defaultValue = "1") Integer pageNo,
                                       @RequestParam(required = false) Integer pageSize,
                                       HttpSession session) {
        User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
        Boolean logisticeFlag = userService.getLogisticeFlagByUserId(session_user.getUserId());
        Page page = getPageTag(request, pageNo, pageSize);
        ModelAndView mv = new ModelAndView();
		//随机值,目的将禁用商品在库存商品列表也能搜索出
		goods.setIsDiscard(10);
        goods.setCompanyId(session_user.getCompanyId());
        Map<String, Object> map = goodsService.getlistGoodsStockPage(request, goods, page);
        List<GoodsVo> list = (List<GoodsVo>) map.get("goodsList");
        mv.addObject("list", list);
        mv.addObject("page", (Page) map.get("page"));
        mv.addObject("goods", goods);
        mv.addObject("logisticeFlag", logisticeFlag);
        mv.setViewName("logistics/warehouseOut/list_goodsStock");
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 库存统计商品详情
     *
     * @param saleorder
     * @return
     * @Note <b>Author:</b> scott <br>
     * <b>Date:</b> 2017年9月8日 下午3:28:52
     */
    @ResponseBody
    @RequestMapping(value = "/viewGoodsStock")
    public ModelAndView viewGoodsStock(Goods goods, HttpSession session) {
        User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
        ModelAndView mv = new ModelAndView();
        // 商品基本信息
        goods = goodsService.getGoodsById(goods);

        //新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
        Map<String,Object> newSkuInfo = vGoodsService.skuTip(goods.getGoodsId());
        mv.addObject("newSkuInfo", newSkuInfo);
        //新商品流切换 VDERP-1970 add by brianna 2020/3/16 end

        // 产品级别
       // List<SysOptionDefinition> goodsLevels = getSysOptionDefinitionList(SysOptionConstant.ID_334);
        // 产品类别
       // List<SysOptionDefinition> goodsTypes = getSysOptionDefinitionList(SysOptionConstant.ID_315);
        // 在库列表
        goods.setCompanyId(session_user.getCompanyId());
        List<WarehouseGoodsOperateLog> wglList = warehouseGoodsOperateLogService.getWglList(goods);
        for (WarehouseGoodsOperateLog warehouseGoodsOperateLog : wglList) {
            User u = userService.getUserById(warehouseGoodsOperateLog.getCreator());
            warehouseGoodsOperateLog.setOperator(u == null ? "" : u.getUsername());
        }
        // 在售列表
//	List<SaleorderGoodsVo> sdList = saleorderService.getSdList(goods);
        List<SaleorderGoodsVo> sdList = saleorderService.getNewSdList(goods);
        //活动锁定列表
        List<SaleorderGoodsVo> actionLockList = saleorderService.getactionLockList(goods);
        // 归属销售
        for (SaleorderGoodsVo saleorderGoodsVo : sdList) {
            User user2 = new User();
            if(saleorderGoodsVo.getTraderId() != null) {
                 user2 = userService.getUserByTraderId(saleorderGoodsVo.getTraderId(), 1);// 1客户，2供应商
            }
            saleorderGoodsVo.setSaleName(user2 != null && StringUtils.isNotBlank(user2.getUsername()) ?  user2.getUsername(): "" );
        }
        for (SaleorderGoodsVo saleorderGoodsVo : actionLockList) {
            User user2 = new User();
            if(saleorderGoodsVo.getTraderId() != null) {
                user2 = userService.getUserByTraderId(saleorderGoodsVo.getTraderId(), 1);// 1客户，2供应商
            }
            saleorderGoodsVo.setSaleName(user2 != null && StringUtils.isNotBlank(user2.getUsername()) ? user2.getUsername() : "" );
        }
        // 在途列表
        List<BuyorderVo> bvList = goodsService.getBuyorderVoList(goods);
        List<BuyorderVo> buVoList = new LinkedList<BuyorderVo>();
        // 在途 不展示 关闭订单
        if (null != bvList && bvList.size() > 0) {
            for (BuyorderVo vo : bvList) {
                if (null == vo)
                    continue;
                // 防止 status为空
                if (null == vo.getStatus()) {
                    buVoList.add(vo);
                    continue;
                }
                if (vo.getStatus() != 3) {
                    buVoList.add(vo);
                }
            }
        }

        // 出入库统计
        String[] dateMons = DateUtil.getLast12Months();
        goods.setStartMon(dateMons[0]);
        goods.setEndMon(dateMons[11]);
        List<WarehouseGoodsOperateLog> kindList = warehouseGoodsOperateLogService.getKindList(goods);
        String[] inNumList = new String[12];
        for (int i = 0; i < inNumList.length; i++) {
            inNumList[i] = "0";
        }
        String[] outNumList = new String[12];
        for (int i = 0; i < outNumList.length; i++) {
            outNumList[i] = "0";
        }
        String[] bjNumList = new String[12];
        for (int i = 0; i < bjNumList.length; i++) {
            bjNumList[i] = "0";
        }
        String[] thNumList = new String[12];
        for (int i = 0; i < thNumList.length; i++) {
            thNumList[i] = "0";
        }
        for (WarehouseGoodsOperateLog w : kindList) {
            if ("1".equals(w.getRowNum() + "")) {
                for (int i = 0; i < dateMons.length; i++) {
                    if (inNumList[i] == null || "0".equals(inNumList[i])) {
                        if (dateMons[i].equals(w.getAddtimes())) {
                            inNumList[i] = w.getNums() + "";
                        } else {
                            inNumList[i] = "0";
                        }
                    }
                }
            }
            if ("2".equals(w.getRowNum() + "")) {
                for (int i = 0; i < dateMons.length; i++) {
                    if (outNumList[i] == null || "0".equals(outNumList[i])) {
                        if (dateMons[i].equals(w.getAddtimes())) {
                            outNumList[i] = w.getNums() + "";
                        } else {
                            outNumList[i] = "0";
                        }
                    }
                }
            }
            if ("4".equals(w.getRowNum() + "")) {
                for (int i = 0; i < dateMons.length; i++) {
                    if (bjNumList[i] == null || "0".equals(bjNumList[i])) {
                        if (dateMons[i].equals(w.getAddtimes())) {
                            bjNumList[i] = w.getNums() + "";
                        } else {
                            bjNumList[i] = "0";
                        }
                    }
                }
            }
            if ("3".equals(w.getRowNum() + "")) {
                for (int i = 0; i < dateMons.length; i++) {
                    if (thNumList[i] == null || "0".equals(thNumList[i])) {
                        if (dateMons[i].equals(w.getAddtimes())) {
                            thNumList[i] = w.getNums() + "";
                        } else {
                            thNumList[i] = "0";
                        }
                    }
                }
            }
        }
        /*
         * for (int i = 0; i < thNumList.length; i++) {
         * System.out.println(thNumList[i]); }
         */
        String date = DateUtil.getNowDate(null) + " 00:00:00";

       // mv.addObject("goodsLevels", goodsLevels);
        mv.addObject("dateMons", dateMons);
        mv.addObject("inNumList", inNumList);
        mv.addObject("outNumList", outNumList);
        mv.addObject("currentTime", DateUtil.convertLong(date, null));
        mv.addObject("bjNumList", bjNumList);
        mv.addObject("thNumList", thNumList);
        mv.addObject("bvList", buVoList);
        mv.addObject("goods", goods);
        mv.addObject("wglList", wglList);
        mv.addObject("sdList", sdList);
        mv.addObject("actionLockList", actionLockList);
        mv.setViewName("logistics/warehouseOut/view_goodsStockDetail");
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 删除快递单
     *
     * @param request
     * @param express
     * @return
     * @Note <b>Author:</b> scott <br>
     * <b>Date:</b> 2017年9月30日 下午1:41:16
     */
    @ResponseBody
    @RequestMapping(value = "/delExpress")
    @SystemControllerLog(operationType = "delete", desc = "删除快递单")
    public ResultInfo<?> delExpress(Express express) {
        ResultInfo<?> result = new ResultInfo<>();
        if (express == null) {
            return new ResultInfo<>(-1, "参数为空");
        }
        express.setIsEnable(0);
        //判断是否可以删除快递单
         boolean  flag = invoiceService.isDelExpressByExpressId(express);
        if(flag) {
           result = expressService.delExpress(express);
        }else{
            result.setMessage("已开具发票,不能删除!");
            return result;
        }
        //如果删除返回成功则向rabbit发送消息
        if (null != express && express.getExpressId() != null && result.getCode().equals(0)) {
            //--------------票货同行---------------
            //删除快递单时驳回之前开票申请
            invoiceService.delInvoiceApplyByExpressId(express.getExpressId());
            //--------------票货同行---------------
            Integer expressId = express.getExpressId();
            LogisticsOrderData logisticsOrderData = new LogisticsOrderData();
            List<LogisticsOrderGoodsData> LogisticsOrderGoodsDataList = expressMapper.selectExpressGood(expressId);
            if (express.getSaleorderId() != null) {
                Saleorder saleorder = saleorderMapper.getWebAccountId(express.getSaleorderId());
                if (null != saleorder && saleorder.getOrderType() == 1) {
                    if (EmptyUtils.isNotBlank(express.getLogisticsName())) {
                        String logisticsCode = logisticsService.getLogisticsCode(express.getLogisticsName());
                        logisticsOrderData.setLogisticsCode(logisticsCode);
                    }
                    logisticsOrderData.setAddLogisticsNo(express.getLogisticsNo());
                    logisticsOrderData.setDelLogisticsNo(express.getLogisticsNo());
                    logisticsOrderData.setAccountId(saleorder.getWebAccountId());
                    logisticsOrderData.setOrderNo(saleorder.getSaleorderNo());
                    logisticsOrderData.setLogisticsType(SysOptionConstant.LOGISTICS_TYPE_1);
                    logisticsOrderData.setOrderGoodsLogisticsDataList(LogisticsOrderGoodsDataList);
                    logisticsOrderData.setType(SysOptionConstant.LOGISTICS_DELETE_3);
                    logger.info("删除快递生产者开始发送消息=======" + JSON.toJSONString(logisticsOrderData));
                    msgProducer.sendMsg(RabbitConfig.MJX_ADDLOGISTICS_EXCHANGE, RabbitConfig.MJX_ADDLOGISTICS_ROUTINGKEY, JSON.toJSONString(logisticsOrderData));
                    logger.info("删除快递生产者开始发送消息完毕=======" + JSON.toJSONString(logisticsOrderData));
                }
            }
        }

        return result;
    }

    /**
     * <b>Description:</b><br>
     * 刷新所有的快递信息
     *
     * @param express
     * @return
     * @Note <b>Author:</b> scott <br>
     * <b>Date:</b> 2017年10月25日 下午3:30:15
     */
    @ResponseBody
    @RequestMapping(value = "/queryState")
    @SystemControllerLog(operationType = "edit", desc = "保存编辑快递信息签收状态")
    public ResultInfo<?> queryState(HttpSession session, Express express, String beforeParams) {
        User curr_user = (User) session.getAttribute(ErpConst.CURR_USER);
        ResultInfo<?> result = null;
        try {
            result = expressService.queryState();
        } catch (Exception e) {
            logger.error("warehourse queryState:", e);
        }
        return result;
    }

    /**
     * <b>Description:</b><br>
     * 编辑快递
     *
     * @param session
     * @param afterSales
     * @param express
     * @return
     * @Note <b>Author:</b> scott <br>
     * <b>Date:</b> 2017年10月23日 下午3:33:11
     */
    @FormToken(save = true)
    @ResponseBody
    @RequestMapping(value = "/editExpress")
    public ModelAndView editExpress(HttpSession session, Saleorder saleorder, Express express) {
        ModelAndView mv = new ModelAndView();
        User curr_user = (User) session.getAttribute(ErpConst.CURR_USER);
        Map<Integer, Object> map = new HashMap<Integer, Object>();
        Map<Integer, Object> oldmap = new HashMap<Integer, Object>();
        List<Integer> relatedIds = new ArrayList<Integer>();
        List<SaleorderGoods> saleorderGoodsList = new ArrayList<SaleorderGoods>();
        List<AfterSalesGoodsVo> aList = new ArrayList<AfterSalesGoodsVo>();
        Saleorder sd = new Saleorder();
        Express oldExpress = new Express();
        // 获取session中user信息
        // User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
        /**
         * bussinessType = 1为 销售快递 业务
         */
        if (saleorder.getBussinessType() == 1) {
            sd = saleorderService.getBaseSaleorderInfo(saleorder);
            // 订单下出库的销售商品
            sd.seteFlag("1");
            sd.setCompanyId(curr_user.getCompanyId());
            saleorderGoodsList = saleorderService.getSaleorderGoodsInfo(sd);
            // 物流信息
            oldExpress.setBusinessType(SysOptionConstant.ID_496);
            oldExpress.setCompanyId(curr_user.getCompanyId());
            for (SaleorderGoods saleorderGood : saleorderGoodsList) {
                // 拼接关联ID的组
                relatedIds.add(saleorderGood.getSaleorderGoodsId());
                // 准备计算所有快递单中产品数量
                map.put(saleorderGood.getSaleorderGoodsId(), 0);
                oldmap.put(saleorderGood.getSaleorderGoodsId(), 0);
            }
            mv.addObject("saleorderGoodsList", saleorderGoodsList);
            mv.setViewName("logistics/warehouseOut/edit_express");
        } else if (saleorder.getBussinessType() == 3) {
            LendOut lendout = new LendOut();
            lendout.setLendOutId(saleorder.getSaleorderId());
            lendout =  warehouseOutService.getLendOutInfo(lendout);
            Goods goods = new Goods();
            goods.setGoodsId(lendout.getGoodsId());
            goods = goodsService.getGoodsById(goods);
            lendout.setBusinessType(660);
            //已快递出库数量
            Integer eNum = warehouseOutService.getLendOutKdNum(lendout);
            goods.seteNum(eNum);
            lendout.setBusinessType(10);
//		Integer deliveryNum = lendOutMapper.getdeliveryNum(lendout);//出库数量
            Integer deliveryNum = warehouseOutService.getdeliveryNum(lendout);
            goods.setDeliveryNum(deliveryNum);
            express.setBusinessType(SysOptionConstant.ID_660);
            oldExpress.setBusinessType(SysOptionConstant.ID_660);
            relatedIds.add(saleorder.getSaleorderId());
            List<Goods> list = new ArrayList<Goods>();
            list.add(goods);
            lendout.setGoodslist(list);
            // 准备计算所有快递单中产品数量
            map.put(lendout.getLendOutId(), 0);
            oldmap.put(lendout.getLendOutId(), 0);
            mv.addObject("lendout", lendout);
            mv.setViewName("logistics/warehouseOut/edit_lendout_express");
        } else {
            AfterSalesVo asv = new AfterSalesVo();
            asv.seteFlag("1");
            asv.setAfterSalesId(saleorder.getBussinessId());
            asv.setCompanyId(curr_user.getCompanyId());
            if (saleorder.getShType() == 546 || saleorder.getShType() == 547) {
                asv.setBusinessType(2);
            } else if (saleorder.getShType() == 540) {
                asv.setBusinessType(1);
            }
            aList = afterSalesOrderService.getAfterSalesGoodsVoList(asv, session);
            express.setBusinessType(SysOptionConstant.ID_582);
            express.setCompanyId(curr_user.getCompanyId());
            for (AfterSalesGoodsVo av : aList) {
                relatedIds.add(av.getAfterSalesGoodsId());
                map.put(av.getAfterSalesGoodsId(), 0);
                oldmap.put(av.getAfterSalesGoodsId(), 0);
            }
            mv.addObject("afterSales",asv);
            mv.addObject("afterSalesGoodsList", aList);
            mv.setViewName("logistics/businessWarehouseOut/edit_express");
        }
        oldExpress.setRelatedIds(relatedIds);
        try {
            List<Express> expressList = expressService.getExpressList(express);
            if (relatedIds.size() > 0) {
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
            mv.addObject("allExpressNumMap", oldmap);//商品总数
            mv.addObject("expressNumMap", map);//已发货数量
            mv.addObject("expressList", expressList.get(0));
            mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(expressList.get(0))));
        } catch (Exception e) {
            logger.error("warehourse editExpress:", e);
        }
        // 获取物流公司列表
        List<Logistics> logisticsList = logisticsService.getLogisticsList(curr_user.getCompanyId());
        mv.addObject("logisticsList", logisticsList);
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 判断订单是否可出库
     *
     * @param request
     * @param saleorder
     * @param session
     * @return
     * @Note <b>Author:</b> scott <br>
     * <b>Date:</b> 2018年2月10日 下午2:37:43
     */
    @ResponseBody
    @RequestMapping(value = "/checkState")
    public ResultInfo<Saleorder> checkState(HttpServletRequest request, Saleorder saleorder, HttpSession session) {
        ResultInfo<Saleorder> resultInfo = new ResultInfo<Saleorder>();
        User curr_user = (User) session.getAttribute(ErpConst.CURR_USER);
        saleorder.setCompanyId(curr_user.getCompanyId());
        Saleorder sl = saleorderService.getSaleorderFlag(saleorder);
        if (sl != null) {
            resultInfo.setCode(0);
            resultInfo.setMessage("操作成功");
            resultInfo.setData(sl);
            resultInfo.setParam(saleorder.getSaleorderId());
        } else {
            resultInfo.setCode(-1);
            resultInfo.setMessage("操作成功");
            resultInfo.setData(null);
        }
        return resultInfo;
    }

    /**
     * <b>Description:</b><br>
     * 查询订单下的商品
     *
     * @param request
     * @param saleorder
     * @param session
     * @return
     * @Note <b>Author:</b> scott <br>
     * <b>Date:</b> 2018年2月10日 下午5:51:50
     */
    @ResponseBody
    @RequestMapping(value = "/queryGoodsList")
    public ResultInfo<List<SaleorderGoods>> queryGoodsList(HttpServletRequest request, Saleorder saleorder,
                                                           HttpSession session) {
        ResultInfo<List<SaleorderGoods>> resultInfo = new ResultInfo<List<SaleorderGoods>>();
        saleorder.setCompanyId(1);
        saleorder.setBussinessType(2);

        List<SaleorderGoods> saleorderGoodsList = saleorderService.getSaleorderGoodsInfo(saleorder);

        if (saleorderGoodsList.size() > 0) {


            //新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
            if(!CollectionUtils.isEmpty(saleorderGoodsList)){
                List<Integer> skuIds = new ArrayList<>();
                saleorderGoodsList.stream().forEach(saleGood -> {
                    skuIds.add(saleGood.getGoodsId());
                });
                List<Map<String,Object>> skuTipsMap = this.vGoodsService.skuTipList(skuIds);
                Map<String,Map<String,Object>> newSkuInfosMap = skuTipsMap.stream().collect(Collectors.toMap(key->key.get("SKU_NO").toString(), v -> v, (k, v) -> v));

                saleorderGoodsList.stream().forEach(saleorderGoods->{
                    saleorderGoods.setGoodsName(newSkuInfosMap.get(saleorderGoods.getSku()).get("SHOW_NAME").toString());
                    saleorderGoods.setModel(newSkuInfosMap.get(saleorderGoods.getSku()).get("MODEL").toString());
                    saleorderGoods.setBrandName(newSkuInfosMap.get(saleorderGoods.getSku()).get("BRAND_NAME").toString());
                    saleorderGoods.setUnitName(newSkuInfosMap.get(saleorderGoods.getSku()).get("UNIT_NAME").toString());
                    saleorderGoods.setMaterialCode(newSkuInfosMap.get(saleorderGoods.getSku()).get("MATERIAL_CODE").toString());
                });
            }
            //新商品流切换 VDERP-1970 add by brianna 2020/3/16 end

            resultInfo.setCode(0);
            resultInfo.setMessage("操作成功");
            resultInfo.setData(saleorderGoodsList);
            resultInfo.setParam(saleorder.getSaleorderId());
        } else {
            resultInfo.setCode(-1);
            resultInfo.setMessage("操作失败");
            resultInfo.setData(null);
        }
        return resultInfo;
    }

    /**
     * <b>Description:</b><br>
     * 批量出库初始化
     *
     * @param request
     * @return
     * @Note <b>Author:</b> scott <br>
     * <b>Date:</b> 2018年4月26日 上午11:15:30
     */
	@FormToken(save = true)
    @ResponseBody
    @RequestMapping(value = "/batchAddWarehouseOut")
    public ModelAndView batchAddWarehouseOut(HttpServletRequest request, Integer orderId, Integer businessType) {
        ModelAndView mv = new ModelAndView("logistics/warehouseOut/batch_add_warehouseout");
        mv.addObject("orderId", orderId);
        mv.addObject("businessType", businessType);
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 批量出库
     *
     * @param request
     * @param session
     * @param rkfile
     * @return
     * @Note <b>Author:</b> scott <br>
     * <b>Date:</b> 2018年4月26日 上午11:20:02
     */
    @MethodLock(className = Integer.class)
	//@FormToken(remove = true)
    @ResponseBody
    @RequestMapping(value = "/batchSaveWarehouseOut")
    public ResultInfo<?> batchSaveWarehouseOut(HttpServletRequest request, HttpSession session,
                                               @RequestParam("rkfile") MultipartFile rkfile,@MethodLockParam Integer orderId,
                                               @RequestParam("businessType") Integer businessType) {
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
//						throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列订货号错误！");
                            } else {
                                warehouseGoodsOperateLog.setSku(cell.getStringCellValue().toString());
                                String goodsId = cell.getStringCellValue().toString().replaceAll("(?i)v", "");
                                warehouseGoodsOperateLog.setGoodsId(Integer.parseInt(goodsId));
                            }
                        }

                        if (cellNum == 6) {// 第七列数据cellNum==(startCellNum+1)
                            // 若excel中无内容，而且没有空格，cell为空--默认3，空白
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            if (cell == null || cell.getStringCellValue().toString() == null
                                    || cell.getStringCellValue().toString() != null
                                    && cell.getStringCellValue().toString().equals("")) {
                                log.info("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "条码错误");
                                resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "条码错误");
                                return resultInfo;
//						throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "条码错误！");
                            } else {
                                warehouseGoodsOperateLog.setBarcode(cell.getStringCellValue().toString());
                            }
                        }

                        if (cellNum == 8){
                            try {
                                cell.setCellType(Cell.CELL_TYPE_STRING);
                                String timeStr = cell.getStringCellValue().toString();
                                if (! (cell == null || timeStr == null || "".equals(timeStr)) ){
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    Date time = sdf.parse(timeStr);
                                    warehouseGoodsOperateLog.setProductDate(time.getTime());
                                }
                            } catch (Exception e) {
                                log.info("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "时间格式错误");
                                resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "时间格式错误");
                                return resultInfo;
                            }
                        }
                    }
                    // 生效
                    warehouseGoodsOperateLog.setIsEnable(1);
                    // 数量
                    warehouseGoodsOperateLog.setNum(-1);
                    if (businessType == 0) {
                        warehouseGoodsOperateLog.setOperateType(2);
                        warehouseGoodsOperateLog.setSaleorderId(orderId);
                    } else if (businessType == 540) {// 销售换货
                        warehouseGoodsOperateLog.setOperateType(4);
                        warehouseGoodsOperateLog.setAfterSalesId(orderId);
                    } else if (businessType == 546) {// 采购退货
                        warehouseGoodsOperateLog.setOperateType(6);
                        warehouseGoodsOperateLog.setAfterSalesId(orderId);
                    } else if (businessType == 547) {// 采购换货
                        warehouseGoodsOperateLog.setOperateType(7);
                        warehouseGoodsOperateLog.setAfterSalesId(orderId);
                    }
                    list.add(warehouseGoodsOperateLog);
                }
                if (list != null && list.size() > 0) {
                    // 验证sku是否存在重复
                    for (int x = 0; x < list.size(); x++) {
                        int m = 0;
                        for (int y = 0; y < list.size(); y++) {
                            if (list.get(x).getBarcode().equals(list.get(y).getBarcode())) {
                                m++;
                                if (m > 1) {
                                    log.info("表格错误，条码：" + list.get(y).getBarcode() + "在出库表格中存在重复！");
                                    resultInfo.setMessage("表格错误，条码：" + list.get(y).getBarcode() + "在出库表格中存在重复！");
                                    return resultInfo;
//                                    throw new Exception("表格错误，条码：" + list.get(y).getBarcode() + "在出库表格中存在重复！");
                                }
                            }
                        }
                    }
                    /************** 对表格进行预处理，获取表格中各商品出库数 ***********************/
                    String goodsIds = "";
                    Map<Integer, Integer> goodsMap = new HashMap<Integer, Integer>();
                    for (int i = 0; i < list.size(); i++) {
                        Integer count = null;
                        count = goodsMap.get(list.get(i).getGoodsId());
                        if (count != null) {
                            goodsMap.put(list.get(i).getGoodsId(), ++count);
                        } else {
                            goodsMap.put(list.get(i).getGoodsId(), 1);
                        }
                        if (!goodsIds.contains(list.get(i).getGoodsId() + "")) {
                            goodsIds += list.get(i).getGoodsId() + "_";
                        }
                    }

                    /**** 判断表格中要出库的条码数量正不正确 ****/
                    if (businessType == 0) {
                        // 查询未出库的销售产品---原查询没有将相同商品总数分开
//			List<SaleorderGoods> saleorderGoodList = saleorderService
//				.getSaleorderGoodNoOutList(list.get(0).getSaleorderId());
                        List<SaleorderGoods> saleorderGoodList = saleorderService.getSaleGoodsNoOutNumList(list.get(0).getSaleorderId());
                        for (SaleorderGoods sg : saleorderGoodList) {
                            if (goodsMap.get(sg.getGoodsId()) != null) {
                                int countNum = goodsMap.get(sg.getGoodsId());
                                if (countNum > (sg.getTotalNum() - sg.getDeliveryNum())) {
                                    log.info("表格错误，SKU为V" + sg.getGoodsId() + "的商品的条码数大于需出库数！");
                                    resultInfo.setMessage("表格错误，SKU为V" + sg.getGoodsId() + "的商品的条码数大于需出库数！");
                                    return resultInfo;
//				    throw new Exception("表格错误，SKU为V" + sg.getGoodsId() + "的商品的条码数大于需出库数！");
                                }
                            }
                        }
                    } else {
                        // 查询未出库的售后产品--原查询没有将相同商品总数分开
//			List<AfterSalesGoods> asvList = afterSalesOrderService
//				.getAfterSalesGoodsNoOutList(list.get(0).getAfterSalesId());
                        List<AfterSalesGoods> asvList = afterSalesOrderService.getAfterSalesGoodsNoOutNumList(list.get(0).getAfterSalesId());
                        for (AfterSalesGoods sg : asvList) {
                            if (goodsMap.get(sg.getGoodsId()) != null) {
                                int countNum = goodsMap.get(sg.getGoodsId());
                                if (countNum > (sg.getNum() - sg.getDeliveryNum())) {
                                    log.info("表格错误，SKU为V" + sg.getGoodsId() + "的商品的条码数大于需出库数！");
                                    resultInfo.setMessage("表格错误，SKU为V" + sg.getGoodsId() + "的商品的条码数大于需出库数！");
                                    return resultInfo;
//				    throw new Exception("表格错误，SKU为V" + sg.getGoodsId() + "的商品的条码数大于需出库数！");
                                }
                            }
                        }
                    }

                }
                //判断条码是否使用过
                for (WarehouseGoodsOperateLog warehouseGoodsOperateLog : list) {
                    Integer barcodeIsEnable = warehouseGoodsOperateLogService.getBarcodeIsEnable(warehouseGoodsOperateLog, 2);
                    if (barcodeIsEnable.equals(0)) {
                        log.info("表格错误，条码：" + warehouseGoodsOperateLog.getBarcode() + "已使用,请重新下载未出库条码！");
                        resultInfo.setMessage("表格错误，条码：" + warehouseGoodsOperateLog.getBarcode() + "已使用,请重新下载未出库条码！");
                        return resultInfo;
                    }
                }
                // 保存更新
                if (list.size() > 1000) {
                    double count = list.size();
                    double pageNum = 1000;
                    Integer page = (int) Math.ceil(count / pageNum);
                    for (Integer i = 0; i < page; i++) {
                        List<WarehouseGoodsOperateLog> afterlist = new ArrayList<>();
                        for (Integer j = 1000 * i; 1000 * (i + 1) > j && j < list.size(); j++) {
                            afterlist.add(list.get(j));
                        }
                        // 保存更新
                        resultInfo = warehouseGoodsOperateLogService.batchAddWarehouseOutList(afterlist);
                    }
                } else {
                    // 保存更新
                    resultInfo = warehouseGoodsOperateLogService.batchAddWarehouseOutList(list);
                }
            }
        } catch (Exception e) {
            logger.error("batchSaveWarehouseOut:", e);
            return resultInfo;
        }finally {
            warehouseGoodsOperateLogService.releaseDistributedLock(list);
        }
        return resultInfo;
    }

    /**
     * <b>Description:</b><br>
     * 打印顺丰速运面单
     *
     * @param session
     * @param request
     * @param orderId
     * @param logisticsId
     * @param type
     * @param btype
     * @return
     * @Note <b>Author:</b> scott <br>
     * <b>Date:</b> 2018年5月7日 下午4:07:10
     */
    @ResponseBody
    @RequestMapping(value = "/printSFexpress")
    public ResultInfo<String> printSFexpress(HttpSession session, HttpServletRequest request, Integer orderId,
                                             Integer logisticsId, Integer type, Integer btype, String logisticsNo, String logisticsName,
                                             Integer expressId, Integer shType, Integer shOrderId, Integer pid) {
        ResultInfo<String> resultInfo = new ResultInfo<String>();
        User curr_user = (User) session.getAttribute(ErpConst.CURR_USER);
        String params=orderId+"\tlogisticsId:"+logisticsId+"\ttype"+type+"\tbtype"+btype+"\tlogisticsNo"+logisticsNo+"\tlogisticsName"+logisticsName
                +"\texpressId"+expressId+"\tshType"+shType+"\tshOrderId"+shOrderId+"\t"+pid;
        logger.info("printSFexpress start "+params);
        // 丰桥
        List<SysOptionDefinition> apiList = getSysOptionDefinitionList(689);
        // 中通快递账号
        List<SysOptionDefinition> zotApiList = getSysOptionDefinitionList(701);

        String ip = super.getIpAddress(request);
        String xmlStr = "";
        String image = "";
        String expressNo = "";
        Express express = new Express();
        Saleorder saleorder = new Saleorder();
        saleorder.setCompanyId(curr_user.getCompanyId());
        Buyorder buyorder = new Buyorder();
        FileDelivery fileDelivery = new FileDelivery();
        AfterSalesDetail av = new AfterSalesDetail();
        ResultInfo<?> res = new ResultInfo<>();
        Map<String, String> map = new HashMap<>();

        /************* 在线下单获取单号 ****************/
        // 销售
        if (btype == 1) {
            saleorder.setSaleorderId(orderId);
            saleorder = saleorderService.getPrintOrderInfo(saleorder);
            if ("顺丰速运".equals(logisticsName)) {
                res = new ExpressUtil().createXml(saleorder, type, curr_user.getCompanyId(), btype, apiList, av, 0);
            } else if ("中通快递".equals(logisticsName)) {
                saleorder.setBussinessNo(System.currentTimeMillis() + "");
                res = new ExpressUtil().createJson(saleorder, express, type, curr_user.getCompanyId(), btype, av, 0, 1,
                        map, zotApiList, pid);
            }
        }
        //外借
        if (btype == 9) {
            LendOut lendout = new LendOut();
            lendout.setLendOutId(orderId);
            lendout =  warehouseOutService.getLendOutInfo(lendout);
            Trader trader  = traderCustomerService.getTraderByTraderId(lendout.getTraderId());
            saleorder.setTraderName(trader.getTraderName());
            saleorder.setTakeTraderName(trader.getTraderName());
            if (lendout.getTakeTraderAddressId() != 0) {
                TraderAddress traderAddress = traderCustomerService.getTraderAddressById(lendout.getTakeTraderAddressId());
                saleorder.setTakeTraderAddress(traderAddress.getAddress());
                saleorder.setTraderAddress(traderAddress.getAddress());
                saleorder.setTraderAddressId(traderAddress.getTraderAddressId());
                saleorder.setTakeTraderAddressId(traderAddress.getTraderAddressId());
            }
            if (lendout.getTakeTraderContactId() != 0) {
                TraderContactGenerate tradercontact  =  traderCustomerService.getTraderContactByTraderContactId(lendout.getTakeTraderContactId());
                saleorder.setTraderContactName(tradercontact.getName());
                saleorder.setTakeTraderContactName(tradercontact.getName());
                saleorder.setTraderContactMobile(tradercontact.getMobile());
                saleorder.setTakeTraderContactMobile(tradercontact.getMobile());
                saleorder.setTraderContactTelephone(tradercontact.getTelephone());
                saleorder.setTakeTraderContactTelephone(tradercontact.getTelephone());
            }
            if (lendout.getTakeTraderAreaId() != 0) {
                //查询地区
                String area = regionService.getRegionNameStringByMinRegionIds(trader.getAreaIds());
                saleorder.setTraderArea(area);
                saleorder.setTakeTraderArea(area);
                saleorder.setTraderAreaId(lendout.getTakeTraderAreaId());
                saleorder.setTakeTraderAreaId(lendout.getTakeTraderAreaId());
            }
            saleorder.setSaleorderNo(lendout.getLendOutNo());
            btype = 1;
            if ("顺丰速运".equals(logisticsName)) {
                res = new ExpressUtil().createXml(saleorder, type, curr_user.getCompanyId(), btype, apiList, av, 0);
            } else if ("中通快递".equals(logisticsName)) {
                saleorder.setBussinessNo(System.currentTimeMillis() + "");
                res = new ExpressUtil().createJson(saleorder, express, type, curr_user.getCompanyId(), btype, av, 0, 1,
                        map, zotApiList, pid);
            }
        }
        // 文件寄送
        if (btype == -1) {
            logger.info("printSFexpress 文件寄送 start "+params);
            fileDelivery.setFileDeliveryId(orderId);
            try {
                fileDelivery = fileDeliveryService.getFileDeliveryInfoById(fileDelivery.getFileDeliveryId());
                fileDelivery.setBussinessNo(System.currentTimeMillis() + "");
                if ("顺丰速运".equals(logisticsName)) {
                    res = new ExpressUtil().createXml(fileDelivery, type, curr_user.getCompanyId(), btype, apiList, av,
                            0);
                } else if ("中通快递".equals(logisticsName)) {
                    res = new ExpressUtil().createJson(fileDelivery, express, type, curr_user.getCompanyId(), btype, av,
                            0, 1, map, zotApiList, pid);
                }
            } catch (Exception e) {
                logger.error("warehourse printSFexpress:", e);
            }
            logger.info("printSFexpress 文件寄送 1 "+params+res);
        }
        // 销售售后
        if (shType != null && shType == 1) {
            saleorder.setSaleorderId(orderId);
            saleorder = saleorderService.getBaseSaleorderInfo(saleorder);
            saleorder.setBussinessNo(System.currentTimeMillis() + "");

            AfterSales as = new AfterSales();
            as.setAfterSalesId(shOrderId);
            av = afterSalesOrderService.selectadtById(as);
            if ("顺丰速运".equals(logisticsName)) {
                res = new ExpressUtil().createXml(saleorder, type, curr_user.getCompanyId(), btype, apiList, av,
                        shType);
            } else if ("中通快递".equals(logisticsName)) {
                res = new ExpressUtil().createJson(saleorder, express, type, curr_user.getCompanyId(), btype, av,
                        shType, 1, map, zotApiList, pid);
            }
        }
        // 采购售后
        if (shType != null && shType == 2) {
            buyorder.setBuyorderId(orderId);
            buyorder = buyorderService.getAddBuyorderVoDetail(buyorder, curr_user);
            buyorder.setBussinessNo(System.currentTimeMillis() + "");

            AfterSales as = new AfterSales();
            as.setAfterSalesId(shOrderId);
            av = afterSalesOrderService.selectadtById(as);
            if ("顺丰速运".equals(logisticsName)) {
                res = new ExpressUtil().createXml(buyorder, type, curr_user.getCompanyId(), btype, apiList, av, shType);
            } else if ("中通快递".equals(logisticsName)) {
                res = new ExpressUtil().createJson(buyorder, express, type, curr_user.getCompanyId(), btype, av, shType,
                        1, map, zotApiList, pid);
            }
        }
        if (null == res || res.getCode() != 0) {
            ResultInfo<String> r = new ResultInfo();
            r.setMessage(res.getMessage());
            return r;
        }
        if ("顺丰速运".equals(logisticsName)) {
            xmlStr = (String) res.getData();
            // 生成快递单号
            String ExpressXml = CallExpressService.getExpressNo(xmlStr, apiList);
            expressNo = ExpressUtil.parserXML(ExpressXml);
        } else if ("中通快递".equals(logisticsName)) {
            String jsonStr = (String) res.getData();
            // 生成快递单号
            logger.info("printSFexpress 文件寄送 生成快递单号 1 "+params+res);
            try {
                String ExpressJson = ZopExpressService.getZopInfo(1, jsonStr, zotApiList);
                ResultInfo<?> r = ExpressUtil.parserJsonExpressNo(ExpressJson);
                ResultInfo<String> re1 = new ResultInfo();
                if (r == null) {
                    re1.setMessage("获取单号接口错误");
                    logger.info("printSFexpress 文件寄送 获取单号接口错误1 "+params+re1 );
                    return re1;
                }
                expressNo = r.getData().toString();
            } catch (Exception e) {
                logger.info("printSFexpress 文件寄送 生成快递单号 1 "+params+res,e);
            }
            logger.info("printSFexpress 文件寄送 获取单号接口  "+params+expressNo );
        }
        // 将快递单号回写到快递表
        if ("".equals(logisticsNo)) {
            express.setLogisticsNo(expressNo);
        } else {
            express.setLogisticsNo("");
        }
        express.setExpressId(expressId);
        Express ep = expressService.updateExpressInfoById(express);
        ResultInfo<?> resInfo = new ResultInfo<>();
        try {
            /************ 打印面单 ***************/
            // 销售
            if (btype == 1) {
                if ("顺丰速运".equals(logisticsName)) {
                    resInfo = new CallWaybillPrinter().WayBillPrinterTools(saleorder, ep, type,
                            curr_user.getCompanyId(), btype, ip, apiList, av, 0);
                } else if ("中通快递".equals(logisticsName)) {
                    // 获取大头笔和集散地请求的json
                    resInfo = new ExpressUtil().createJson(saleorder, ep, type, curr_user.getCompanyId(), btype, av, 0,
                            2, map, zotApiList, pid);
                    if (null == resInfo || resInfo.getCode() != 0) {
                        ResultInfo<String> r = new ResultInfo();
                        r.setMessage(res.getMessage());
                        return r;
                    }
                    String jsonStr = (String) resInfo.getData();
                    String bigInfoJson = ZopExpressService.getZopInfo(2, jsonStr, zotApiList);
                    // 获取大头笔信息
                    ResultInfo<?> r = ExpressUtil.parserJsonMark(bigInfoJson);
                    if (r == null) {
                        ResultInfo<String> rest = new ResultInfo();
                        rest.setMessage("获取大头笔接口错误");
                        return rest;
                    }
                    map = (Map<String, String>) r.getData();
                    // 云打印快递面单
                    try {
                        // 生成请求打印的json
                        res = new ExpressUtil().createJson(saleorder, ep, type, curr_user.getCompanyId(), btype, av, 0,
                                3, map, zotApiList, pid);
                        if (null == res || res.getCode() != 0) {
                            ResultInfo<String> r1 = new ResultInfo();
                            r1.setMessage(res.getMessage());
                            return r1;
                        }
                        jsonStr = (String) res.getData();
                        String ExpressJson = ZopExpressService.getZopInfo(3, jsonStr, zotApiList);
                        ResultInfo<?> rp = ExpressUtil.parserJsonPrint(ExpressJson);
                        if (rp == null) {
                            ResultInfo<String> rest = new ResultInfo();
                            rest.setMessage("云打印接口错误");
                            return rest;
                        }
                        resInfo = new ResultInfo(0, "操作成功");
                    } catch (NoSuchAlgorithmException e) {
                        logger.error(Contant.ERROR_MSG, e);
                    } catch (IOException e) {
                        logger.error(Contant.ERROR_MSG, e);
                    }
                }
            }
            // 文件寄送
            if (btype == -1) {
                logger.info("printSFexpress 文件寄送 打印面单 1 "+params );
                if ("顺丰速运".equals(logisticsName)) {
                    resInfo = new CallWaybillPrinter().WayBillPrinterTools(fileDelivery, ep, 3,
                            1, btype, ip, apiList, av, 0);
                } else if ("中通快递".equals(logisticsName)) {
                    // 获取大头笔和集散地请求的json
                    resInfo = new ExpressUtil().createJson(fileDelivery, ep, type, 1, btype, av,
                            0, 2, map, zotApiList, pid);
                    if (null == resInfo || resInfo.getCode() != 0) {
                        ResultInfo<String> r = new ResultInfo();
                        r.setMessage(res.getMessage());
                        logger.error("文件寄送结束 失败"+res.getMessage() +params);
                        return r;
                    }
                    String jsonStr = (String) resInfo.getData();
                    String bigInfoJson = ZopExpressService.getZopInfo(2, jsonStr, zotApiList);
                    // 获取大头笔信息
                    ResultInfo<?> r = ExpressUtil.parserJsonMark(bigInfoJson);
                    if (r == null) {
                        ResultInfo<String> rest = new ResultInfo();
                        rest.setMessage("获取大头笔接口错误");
                        logger.error("文件寄送结束 获取大头笔接口错误"+res.getMessage() +params);
                        return rest;
                    }
                    map = (Map<String, String>) r.getData();
                    // 云打印快递面单
                    try {
                        // 生成请求打印的json
                        res = new ExpressUtil().createJson(fileDelivery, ep, type, curr_user.getCompanyId(), btype, av,
                                0, 3, map, zotApiList, pid);
                        if (null == res || res.getCode() != 0) {
                            ResultInfo<String> r2 = new ResultInfo();
                            r2.setMessage(res.getMessage());
                            logger.error("文件寄送结束 成请求打印错误"+res.getMessage() +params);
                            return r2;
                        }
                        jsonStr = (String) res.getData();
                        String ExpressJson = ZopExpressService.getZopInfo(3, jsonStr, zotApiList);
                        ResultInfo<?> rp = ExpressUtil.parserJsonPrint(ExpressJson);
                        if (rp == null) {
                            ResultInfo<String> rest = new ResultInfo();
                            logger.error("文件寄送结束 云打印接口错误"+res.getMessage() +params);
                            rest.setMessage("云打印接口错误");
                            return rest;
                        }
                        resInfo = new ResultInfo(0, "操作成功");
                    } catch (Exception e) {
                        logger.error("文件寄送结束 云打印接口错误"+res.getMessage() +params,e);
                        ResultInfo<String> rest = new ResultInfo();
                        resultInfo.setMessage(res.getMessage());
                        return resultInfo;
                    }
                }

            }
            // 销售售后
            if (shType != null && shType == 1) {
                if ("顺丰速运".equals(logisticsName)) {
                    resInfo = new CallWaybillPrinter().WayBillPrinterTools(saleorder, ep, 1, curr_user.getCompanyId(),
                            btype, ip, apiList, av, shType);
                } else if ("中通快递".equals(logisticsName)) {
                    // 获取大头笔和集散地请求的json
                    resInfo = new ExpressUtil().createJson(saleorder, ep, type, curr_user.getCompanyId(), btype, av,
                            shType, 2, map, zotApiList, pid);
                    if (null == resInfo || resInfo.getCode() != 0) {
                        ResultInfo<String> r = new ResultInfo();
                        r.setMessage(res.getMessage());
                        return r;
                    }
                    String jsonStr = (String) resInfo.getData();
                    String bigInfoJson = ZopExpressService.getZopInfo(2, jsonStr, zotApiList);
                    // 获取大头笔信息
                    ResultInfo<?> r = ExpressUtil.parserJsonMark(bigInfoJson);
                    if (r == null) {
                        ResultInfo<String> rest = new ResultInfo();
                        rest.setMessage("获取大头笔接口错误");
                        return rest;
                    }
                    map = (Map<String, String>) r.getData();
                    // 云打印快递面单
                    try {
                        // 生成请求打印的json
                        res = new ExpressUtil().createJson(saleorder, ep, type, curr_user.getCompanyId(), btype, av,
                                shType, 3, map, zotApiList, pid);
                        if (null == res || res.getCode() != 0) {
                            ResultInfo<String> r3 = new ResultInfo();
                            r3.setMessage(res.getMessage());
                            return r3;
                        }
                        jsonStr = (String) res.getData();
                        String ExpressJson = ZopExpressService.getZopInfo(3, jsonStr, zotApiList);
                        ResultInfo<?> rp = ExpressUtil.parserJsonPrint(ExpressJson);
                        if (rp == null) {
                            ResultInfo<String> rest = new ResultInfo();
                            rest.setMessage("云打印接口错误");
                            return rest;
                        }
                        resInfo = new ResultInfo(0, "操作成功");
                    } catch (NoSuchAlgorithmException e) {
                        logger.error(Contant.ERROR_MSG, e);
                    } catch (IOException e) {
                        logger.error(Contant.ERROR_MSG, e);
                    }
                }
            }
            // 采购售后
            if (shType != null && shType == 2) {
                if ("顺丰速运".equals(logisticsName)) {
                    resInfo = new CallWaybillPrinter().WayBillPrinterTools(buyorder, ep, 1, curr_user.getCompanyId(),
                            btype, ip, apiList, av, shType);
                } else if ("中通快递".equals(logisticsName)) {
                    // 获取大头笔和集散地请求的json
                    resInfo = new ExpressUtil().createJson(buyorder, ep, type, curr_user.getCompanyId(), btype, av,
                            shType, 2, map, zotApiList, pid);
                    if (null == resInfo || resInfo.getCode() != 0) {
                        ResultInfo<String> r = new ResultInfo();
                        r.setMessage(res.getMessage());
                        return r;
                    }
                    String jsonStr = (String) resInfo.getData();
                    String bigInfoJson = ZopExpressService.getZopInfo(2, jsonStr, zotApiList);
                    // 获取大头笔信息
                    ResultInfo<?> r = ExpressUtil.parserJsonMark(bigInfoJson);
                    if (r == null) {
                        ResultInfo<String> rest = new ResultInfo();
                        rest.setMessage("获取大头笔接口错误");
                        return rest;
                    }
                    map = (Map<String, String>) r.getData();
                    // 云打印快递面单
                    try {
                        // 生成请求打印的json
                        res = new ExpressUtil().createJson(buyorder, ep, type, curr_user.getCompanyId(), btype, av,
                                shType, 3, map, zotApiList, pid);
                        if (null == res || res.getCode() != 0) {
                            ResultInfo<String> r4 = new ResultInfo();
                            r4.setMessage(res.getMessage());
                            return r4;
                        }
                        jsonStr = (String) res.getData();
                        String ExpressJson = ZopExpressService.getZopInfo(3, jsonStr, zotApiList);
                        ResultInfo<?> rp = ExpressUtil.parserJsonPrint(ExpressJson);
                        if (rp == null) {
                            ResultInfo<String> rest = new ResultInfo();
                            rest.setMessage("云打印接口错误");
                            return rest;
                        }
                        resInfo = new ResultInfo(0, "操作成功");
                    } catch (NoSuchAlgorithmException e) {
                        logger.error(Contant.ERROR_MSG, e);
                    } catch (IOException e) {
                        logger.error(Contant.ERROR_MSG, e);
                    }
                }
            }
            if (null == resInfo || resInfo.getCode() != 0) {
                ResultInfo<String> r = new ResultInfo();
                r.setMessage(resInfo.getMessage());
                logger.info("printSFexpress 文件寄送 打印面单 1 "+params+resInfo );
                return r;
            }
        } catch (Exception e) {
            logger.error("printSFexpress:"+params, e);
        }
        resultInfo.setCode(0);
        resultInfo.setMessage("操作成功");
        resultInfo.setData(image);

        return resultInfo;
    }

    /**
     * <b>Description:</b><br>
     * 跳转到发送短息页面
     *
     * @param request
     * @param saleorder
     * @return
     * @Note <b>Author:</b> scott <br>
     * <b>Date:</b> 2018年8月9日 下午1:16:04
     */
    @ResponseBody
    @RequestMapping(value = "/sendOutMsg")
    public ModelAndView sendOutMsg(HttpServletRequest request, Saleorder saleorder) {
        Saleorder sd = new Saleorder();
        if (saleorder.getLendOutId() != null) {
            LendOut lendout = new LendOut();
            lendout.setLendOutId(saleorder.getLendOutId());
            lendout =  warehouseOutService.getLendOutInfo(lendout);
            sd.setSaleorderId(saleorder.getLendOutId());
            sd.setSaleorderNo(lendout.getLendOutNo());
            Trader trader =  traderCustomerService.getTraderByTraderId(lendout.getTraderId());
            if (lendout.getTakeTraderContactId() != 0) {
                TraderContactGenerate tradercontact = traderCustomerService.getTraderContactByTraderContactId(lendout.getTakeTraderContactId());
                sd.setTakeTraderContactMobile(tradercontact.getMobile());
                sd.setTakeTraderContactTelephone(tradercontact.getTelephone());
                sd.setTraderContactMobile(tradercontact.getMobile());
                sd.setTraderContactTelephone(tradercontact.getTelephone());
            }
        } else {
            sd = saleorderService.getBaseSaleorderInfo(saleorder);
        }
        ModelAndView mv = new ModelAndView("logistics/warehouseOut/sendmsg");
        mv.addObject("saleorder", sd);
        mv.addObject("saleorderPam", saleorder);
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 发送短信
     *
     * @param request
     * @param saleorder
     * @param session
     * @return
     * @Note <b>Author:</b> scott <br>
     * <b>Date:</b> 2018年8月10日 上午9:21:26
     */
    @SuppressWarnings("rawtypes")
    @ResponseBody
    @RequestMapping(value = "/toSendOutMsg")
    public ResultInfo<List<SaleorderGoods>> toSendOutMsg(HttpServletRequest request, Saleorder saleorder,
                                                         HttpSession session) {
        ResultInfo resultInfo = new ResultInfo();
        String content = "";
        Boolean sendTplSms = false;
        if (saleorder.getPhoneNo() != null && !"".equals(saleorder.getPhoneNo()) && saleorder.getSaleorderNo() != null
                && !"".equals(saleorder.getSaleorderNo()) && saleorder.getLogisticsName() != null
                && !"".equals(saleorder.getLogisticsName()) && saleorder.getLogisticsNo() != null
                && !"".equals(saleorder.getLogisticsNo())) {
            content = "@1@=" + saleorder.getSaleorderNo() + ",@2@=" + saleorder.getLogisticsName() + ",@3@="
                    + saleorder.getLogisticsNo();
            sendTplSms = SmsUtil.sendTplSms(saleorder.getPhoneNo(), "JSM40187-0035", content);
        }
        if (!sendTplSms) {
            resultInfo.setMessage("发送失败");
            return resultInfo;
        } else {
            // 将快递单改为短信已发送
            Express express = new Express();
            express.setLogisticsNo(saleorder.getLogisticsNo());
            express.setSentSms(1);
            express.setMsgCommtents("sendmsg");
            expressService.updateExpressInfoById(express);
            resultInfo.setCode(0);
            resultInfo.setMessage("操作成功");
        }
        return resultInfo;
    }

    /**
     * <b>Description:</b><br>
     * 查询物流快递信息
     *
     * @param express
     * @param session
     * @return
     * @Note <b>Author:</b> scott <br>
     * <b>Date:</b> 2018年8月17日 下午2:18:02
     */
    @ResponseBody
    @RequestMapping(value = "/queryExpressInfo")
    public ModelAndView queryExpressInfo(Express express, HttpSession session) {
        ModelAndView mv = new ModelAndView();

        // 查询快递信息
        LogisticsDetail lgd = expressService.getLogisticsDetailInfo(express);
        List<LogisticsDetail> ldList = new ArrayList<>();

        // 预处理快递内容
        if (lgd != null && !"".equals(lgd.getContent())) {

            JSONObject rd = JSONObject.fromObject(lgd.getContent());
            String message = rd.getString("message");
            if ("ok".equals(message)) {
                JSONArray data = rd.getJSONArray("data");
                if (data.size() > 0) {
                    for (int i = data.size() - 1; i >= 0; i--) {

                        JSONObject job = data.getJSONObject(i);
                        String time = job.get("time").toString();
                        LogisticsDetail ld = new LogisticsDetail();
                        String dateStr = "";
                        String dateDay = "";
                        String[] arr = time.split("\\s+");
                        try {
                            // 处理页面展示时的日期格式
                            dateStr = DateUtil.getCalender(arr[0]);
                            dateDay = arr[0] + " " + dateStr;
                            if (ldList.size() < 1) {
                                ld.setDateTime(dateDay);
                            }
                            for (int j = 0; j < ldList.size(); j++) {
                                if (dateDay.equals(ldList.get(j).getDateTime())) {
                                    ld.setDateTime("");
                                } else if ("".equals(ldList.get(j).getDateTime())) {
                                    continue;
                                } else {
                                    ld.setDateTime(dateDay);
                                }
                            }
                            ld.setTimeMillis(arr[1]);
                            ld.setDetail(job.get("context").toString());
                            ldList.add(ld);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        }

        mv.addObject("ldList", ldList);
        mv.setViewName("logistics/warehouseOut/logiticsInfo");
        return mv;
    }

    /**
     * <b>Description:</b>跳转新增、编辑出库备注页面
     *
     * @param request
     * @param saleorderWarehouseComments
     * @return ModelAndView
     * @Note <b>Author：</b> scott.zhu
     * <b>Date:</b> 2019年1月22日 下午5:21:11
     */
    @ResponseBody
    @RequestMapping(value = "/addOreditComments")
    public ModelAndView addOreditComments(HttpServletRequest request, SaleorderWarehouseComments saleorderWarehouseComments) {
        ModelAndView mv = new ModelAndView("logistics/warehouseOut/update_comment");
        if (saleorderWarehouseComments.getSaleorderWarehouseCommentsId() != null) {
            //查询销售单出库备注
            SaleorderWarehouseComments sm = saleorderService.getSaleorderWarehouseComments(saleorderWarehouseComments);
            mv.addObject("saleorderWarehouseComments", sm);
        }
        mv.addObject("saleorderId", saleorderWarehouseComments.getSaleorderId());
        return mv;
    }

    /**
     * <b>Description:</b>新增或更新销售单出库备注
     *
     * @param saleorderWarehouseComments
     * @return ResultInfo<?>
     * @Note <b>Author：</b> scott.zhu
     * <b>Date:</b> 2019年1月23日 下午1:37:18
     */
    @ResponseBody
    @RequestMapping(value = "/updateComments")
    public ResultInfo<?> updateComments(HttpSession session, SaleorderWarehouseComments saleorderWarehouseComments) {
        User curr_user = (User) session.getAttribute(ErpConst.CURR_USER);
        if (saleorderWarehouseComments.getIsDelete() != null && saleorderWarehouseComments.getIsDelete() == 1) {
            saleorderWarehouseComments.setIsDelete(1);
        } else {
            saleorderWarehouseComments.setCreator(curr_user.getUserId());
            saleorderWarehouseComments.setAddTime(System.currentTimeMillis());
            saleorderWarehouseComments.setIsDelete(0);
        }
        saleorderWarehouseComments.setUpdater(curr_user.getUserId());
        saleorderWarehouseComments.setModTime(System.currentTimeMillis());
        ResultInfo<?> result = saleorderService.updateComments(saleorderWarehouseComments);
        return result;
    }

    /**
     * <b>Description:</b>打印订单的所有出库记录
     *
     * @param request
     * @param saleorder
     * @return
     * @throws ShowErrorMsgException ModelAndView
     * @Note <b>Author：</b> scott.zhu
     * <b>Date:</b> 2019年1月25日 下午1:47:46
     */
    @ResponseBody
    @RequestMapping(value = "/printAllOutOrder")
    public ModelAndView printAllOutOrder(HttpServletRequest request, Saleorder saleorder) throws ShowErrorMsgException {
        User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        String timeStr = "";
        String names = "";
        ModelAndView mv = new ModelAndView();
        //售后
        if (saleorder.getBussinessType() != 496) {
            AfterSalesDetail av = new AfterSalesDetail();
            AfterSales as = new AfterSales();
            as.setAfterSalesId(saleorder.getOrderId());
            av = afterSalesOrderService.selectadtById(as);
            mv.addObject("afterSalesDetail", av);
            mv.addObject("bussinessNo", saleorder.getBussinessNo());
        }
        //销售
        else {
            saleorder.setSaleorderId(saleorder.getOrderId());
            Saleorder sd = saleorderService.getBaseSaleorderInfo(saleorder);
            mv.addObject("saleorder", sd);
            mv.addObject("bussinessNo", sd.getSaleorderNo());
        }
        List<WarehouseGoodsOperateLog> woList = new ArrayList<WarehouseGoodsOperateLog>();
        //查询订单下的出库记录（效期+sku分组）
        saleorder.setCompanyId(user.getCompanyId());
        woList = warehouseGoodsOperateLogService.printAllOutOrder(saleorder);
        for (WarehouseGoodsOperateLog w : woList) {
            w.setNum(0 - w.getNum());
        }
        if (woList != null && woList.size() > 0) {
            //发货日期区间
            List<Long> dates = woList.get(0).getDates();
            if (dates.size() == 1) {
                timeStr = DateUtil.convertString(dates.get(0), "yyyy-MM-dd HH:mm:ss");
            } else if (dates.size() > 1) {
                Long max = dates.get(0);
                Long min = dates.get(0);
                for (Long time : dates) {
                    if (time < min) {
                        min = time;
                    }
                    if (time > max) {
                        max = time;
                    }
                }
                timeStr = DateUtil.convertString(min, "yyyy-MM-dd HH:mm:ss") + "~<br/>"
                        + DateUtil.convertString(max, "yyyy-MM-dd HH:mm:ss");
            }
            //发货人区间
            List<Integer> creators = woList.get(0).getCreators();
            for (int j = 0; j < creators.size(); j++) {
                User users = userService.getUserById(creators.get(j));
                names += users.getUsername() + "&nbsp;";
            }
        }
        Long currTime = DateUtil.sysTimeMillis();
        mv.addObject("currTime", timeStr);
        mv.addObject("bussinessType", saleorder.getBussinessType());
        mv.addObject("outName", names);
        mv.addObject("userName", user.getUsername());
        mv.addObject("woList", woList);
//        mv.addObject("type", 1);
        mv.addObject("printAllOrder", 1);
        mv.setViewName("logistics/warehouseOut/print_out_order");
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/updateWarehouseProblem")
    public ResultInfo updateWarehouseProblem(Integer[] warehouseGoodsId, Integer[] IsProblems, String[] problemRemark, Integer goodId) {
        List<WarehouseGoodsOperateLogVo> wg = new ArrayList<>();
        WarehouseGoodsStatus warehouseGoodsStatus = new WarehouseGoodsStatus();
        int count = 0;
        for (int i = 0; i < warehouseGoodsId.length; i++) {
            WarehouseGoodsOperateLogVo warehouseGoodsOperateLogVo = new WarehouseGoodsOperateLogVo();
            warehouseGoodsOperateLogVo.setWarehouseGoodsOperateLogId(warehouseGoodsId[i]);
            wg.add(warehouseGoodsOperateLogVo);
        }
        for (int j = 0; j < wg.size(); j++) {
            if (IsProblems[j] == 1) {
                count++;
            }
            wg.get(j).setIsProblem(IsProblems[j]);
        }
        for (int f = 0; f < wg.size(); f++) {
            wg.get(f).setProblemRemark(problemRemark[f]);
        }
        int g = warehouseGoodsOperateLogService.updateWarehouse(wg);
        if (g > 0) {
            if (count > 0) {
                warehouseGoodsStatus.setGoodsId(goodId);
                warehouseGoodsStatus.setIsParentProblem(1);
                warehouseGoodsOperateLogService.updateGoodId(warehouseGoodsStatus);
            } else {
                warehouseGoodsStatus.setGoodsId(goodId);
                warehouseGoodsStatus.setIsParentProblem(0);
                warehouseGoodsOperateLogService.updateGoodId(warehouseGoodsStatus);
            }
            return new ResultInfo(1, "success");
        }
        return new ResultInfo(-1, "fail");
    }

    /**
     * @param @param request
     * @return ModelAndView    返回类型
     * @throws
     * @Title: lendOutIndex
     * @Description: TODO(外借出库商品列表)
     * @author strange
     * @date 2019年8月20日
     */
    @FormToken(save = true)
    @ResponseBody
    @RequestMapping(value = "/lendOutIndex")
    public ModelAndView lendOutIndex(HttpServletRequest request) {
//    	User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("logistics/warehouseOut/index_lendOut");
        return mv;
    }

    /**
     * @param @param request
     * @param @param session
     * @param @param lendout
     * @return ModelAndView    返回类型
     * @throws
     * @Title: saveAddLendOutInfo
     * @Description: TODO(保存新增的外借单)
     * @author strange
     * @date 2019年8月30日
     */
    @FormToken(remove = true)
    @ResponseBody
    @RequestMapping(value = "/saveAddLendOutInfo")
    public ModelAndView saveAddLendOutInfo(HttpServletRequest request, HttpSession session, LendOut lendout) {
        ModelAndView mv = new ModelAndView();
        try {
            long time = DateUtil.convertLong(request.getParameter("returnTimeStr") + " 00:00:00",
                    "yyyy-MM-dd HH:mm:ss");
            lendout.setReturnTime(time);
            User curr_user = (User) session.getAttribute(ErpConst.CURR_USER);
            lendout.setCreator(curr_user.getUserId());
            LendOut lend = warehouseOutService.saveLendOut(lendout);
            if (null != lend) {
                mv.addObject("url", "/warehouse/warehousesout/lendOutdetailJump.do?lendOutId=" + lend.getLendOutId());
                return success(mv);
            } else {
                return fail(mv);
            }
        } catch (Exception e) {
            logger.error("saveAddLendOutInfo:", e);
            return fail(mv);
        }
    }

    /**
     * @param @param session
     * @param @param request
     * @param @param lendout
     * @param @param lendOutId
     * @return ModelAndView    返回类型
     * @throws
     * @Title: lendOutdetailJump
     * @Description: TODO(商品外借跳转到出库详情页面)
     * @author strange
     * @date 2019年8月30日
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @ResponseBody
    @RequestMapping(value = "/lendOutdetailJump")
    public ModelAndView lendOutdetailJump(HttpSession session, ServletRequest request, LendOut lendout, Integer lendOutId) {
        logger.info("lendOutdetailJump商品外借跳转到出库详情页面开始");
//		User curr_user = (User) session.getAttribute(ErpConst.CURR_USER);
        ModelAndView mv = new ModelAndView();
        // 获取订单详情
        lendout.setLendOutId(lendOutId);
        lendout = warehouseOutService.getLendOutInfo(lendout);
        // 创建人
//		User user = userService.getUserByTraderId(lendout.getTraderId(), lendout.getTraderType());// 1客户，2供应商
        User user = userService.getUserById(lendout.getCreator());
        Trader trader =  traderCustomerService.getTraderByTraderId(lendout.getTraderId());
        if (lendout.getTakeTraderAddressId() != 0) {
            TraderAddress traderAddress = traderCustomerService.getTraderAddressById(lendout.getTakeTraderAddressId());
            mv.addObject("traderAddress", traderAddress);
        }
        if (lendout.getTakeTraderContactId() != 0) {
            TraderContactGenerate tradercontact =  traderCustomerService.getTraderContactByTraderContactId(lendout.getTakeTraderContactId());
            mv.addObject("tradercontact", tradercontact);
        }
        if (lendout.getTakeTraderAreaId() != 0) {
            //查询地区
            String area = regionService.getRegionNameStringByMinRegionIds(trader.getAreaIds());
            mv.addObject("area", area);
        }
        mv.addObject("trader", trader);
        //获取物流公司列表
        List<Logistics> logisticsList = getLogisticsList(1);
        mv.addObject("logisticsList", logisticsList);
        // 运费说明
        List<SysOptionDefinition> freightDescriptions = getSysOptionDefinitionList(469);
        mv.addObject("freightDescriptions", freightDescriptions);
        //商品信息
        Goods goods = new Goods();
        goods.setGoodsId(lendout.getGoodsId());
        goods.setSku("V"+lendout.getGoodsId());
       // goods = goodsService.getGoodsById(goods);
        WarehouseGoodsStatus warehouseGoodsStatus = new WarehouseGoodsStatus();
        warehouseGoodsStatus.setCompanyId(1);
        warehouseGoodsStatus.setGoodsId(goods.getGoodsId());
        //Integer goodsStockNum = warehouseGoodsStatusMapper.getGoodsStock(warehouseGoodsStatus);//库存量
        //库存量
        Integer goodsStockNum = warehouseOutService.getGoodsStockByGoodsStatus(warehouseGoodsStatus);
        mv.addObject("goods", goods);
        //mv.addObject("goodsStockNum", goodsStockNum);
        // 商品总数计算
        int goodsNum = lendout.getLendOutNum();
        mv.addObject("goodsNum", goodsNum);
        // 判断是否0<已拣货数量||已发货数量<商品总数
        int allPickCnt = 0;
        int allOutCnt = 0;
//		allOutCnt = lendout.getDeliverNum();
        LendOut l = LendOut.getinstance();
        l.setLendOutId(lendOutId);
        l.setBusinessType(10);
        allOutCnt = warehouseOutService.getdeliveryNum(l);
        Integer kdNum = warehouseOutService.getkdNum(l);
        // 拣货记录清单
        Saleorder saleorder = new Saleorder();
        saleorder.setBussinessType(3);
        saleorder.setBussinessId(lendOutId);
//		List<WarehousePicking> warehousePickList = warehousePickService.getPickDetil(saleorder);
        List<WarehousePicking> warehousePickList = warehousePickService.getPickLendOutDetil(saleorder);
        List<String> timeArray = new ArrayList<>();


        Set<Integer> skuIds = new HashSet<>();

        String pickFlag = "0";
        if (null != warehousePickList) {
            for (WarehousePicking wp : warehousePickList) {
                allPickCnt += wp.getNum();
                if (wp.getCnt() == 0) {
                    timeArray.add(DateUtil.convertString(wp.getAddTime(), "YYYY-MM-dd HH:mm:ss"));
                    pickFlag = "1";
                }
                User u = userService.getUserById(wp.getCreator());
                wp.setOperator(u == null ? "" : u.getUsername());
            }
            HashSet<String> tArray = new HashSet<String>(timeArray);
            mv.addObject("timeArray", tArray);

            //新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
            if(!CollectionUtils.isEmpty(warehousePickList)){
                warehousePickList.stream().forEach(warehousePick -> {
                    skuIds.add(warehousePick.getGoodsId());
                });
            }
            //新商品流切换 VDERP-1970 add by brianna 2020/3/16 end
        }
        // 出库记录清单
//		List<WarehouseGoodsOperateLog> warehouseOutList = warehouseOutService.getOutDetil(saleorder);
        saleorder.setBussinessType(10);//外借出库
        List<WarehouseGoodsOperateLogVo> warehouseOutList = warehouseOutService.getLendOutDetil(saleorder);
        List<String> timeArrayWl = new ArrayList<>();
        if (null != warehouseOutList) {

            //新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
            if(!CollectionUtils.isEmpty(warehouseOutList)){
                warehousePickList.stream().forEach(warehousePick -> {
                    skuIds.add(warehousePick.getGoodsId());
                });
            }
            //新商品流切换 VDERP-1970 add by brianna 2020/3/16 end

            for (WarehouseGoodsOperateLogVo wl : warehouseOutList) {
                User u = userService.getUserById(wl.getCreator());
                wl.setOpName(u == null ? "" : u.getUsername());
                timeArrayWl.add(DateUtil.convertString(wl.getAddTime(), "YYYY-MM-dd HH:mm:ss"));

            }
            HashSet<String> wArray = new HashSet<String>(timeArrayWl);
            TreeSet ts = new TreeSet(wArray);
            ts.comparator();
            mv.addObject("timeArrayWl", ts);
        }
        // 查询发货备注信息
        List<SaleorderWarehouseComments> listComments = new ArrayList<>();
        listComments = saleorderService.getListComments(saleorder);
        mv.addObject("listComments", listComments);
        // 物流信息
        Express express = new Express();
        express.setBusinessType(SysOptionConstant.ID_660);
        express.setCompanyId(1);
        List<Integer> relatedIds = new ArrayList<Integer>();
        relatedIds.add(lendOutId);

        List<Express> expressList = null;
        if (relatedIds != null && relatedIds.size() > 0) {
            express.setRelatedIds(relatedIds);
            try {
                expressList = expressService.getExpressList(express);

                if(!CollectionUtils.isEmpty(expressList)){

                    Map<String,Object> goodsInfoMap = this.vGoodsService.skuTip(lendout.getGoodsId());

                    expressList.stream().forEach(expressItem->{

                        expressItem.getExpressDetail().stream().forEach(expressDetailItem->{
                            expressDetailItem.setGoodName(goodsInfoMap.get("SHOW_NAME").toString());
                            expressDetailItem.setUnitName(goodsInfoMap.get("UNIT_NAME").toString());
                        });
                    });
                }

//				List<Express> expressList = expressService.getLendOutExpressList(express);
                mv.addObject("expressList", expressList);
            } catch (Exception e) {
                logger.error("detailJump:", e);
            }
        }

        List<Barcode> barcodeList = null;
        Barcode barcode = new Barcode();
        // 有效的条码;
        List<WarehouseGoodsOperateLog> warehouseGoodsOperateLogList = new ArrayList<>();
        try {
            barcode.setIsEnable(4);
            barcode.setDetailGoodsId(lendout.getLendOutId());
            barcode.setType(4);
            barcodeList = warehouseInService.getBarcode(barcode);
            lendout.setBarcodeNum(barcodeList.size());
            WarehouseGoodsOperateLog warehouseGoodsOperateLog = new WarehouseGoodsOperateLog();
            // 入库的记录
            warehouseGoodsOperateLog.setOperateType(9);
            // 有效的
            warehouseGoodsOperateLog.setIsEnable(1);
            warehouseGoodsOperateLog.setRelatedId(lendOutId);
            warehouseGoodsOperateLogList = warehouseGoodsOperateLogService.getWGOlog(warehouseGoodsOperateLog);
        } catch (Exception e) {
            logger.error(Contant.ERROR_MSG, e);
            logger.error("商品归还入库列表页erro:", e);
        }

        List<Map<String,Object>> skuTipsMap = this.vGoodsService.skuTipList(new ArrayList<>(skuIds));
        Map<String,Map<String,Object>> newSkuInfosMap = skuTipsMap.stream().collect(Collectors.toMap(key->key.get("SKU_NO").toString(), v -> v, (k, v) -> v));
        mv.addObject("newSkuInfosMap", newSkuInfosMap);

//		LendOut le = LendOut.getinstance();
//		le.setLendOutId(lendOutId);
//		le.setBusinessType(10);
//		Integer houseoutNum = lendOutMapper.getdeliveryNum(le);//已出库数量
//		出库数量大于已快递数量可新增快递
        Integer lockedStatusOutFlag = allOutCnt > kdNum ? 1 : 0;
        mv.addObject("pickFlag", pickFlag);
        mv.addObject("allPickCnt", allPickCnt);
        mv.addObject("lockedStatusOutFlag", lockedStatusOutFlag);
        mv.addObject("allOutCnt", allOutCnt);
        mv.addObject("warehouseOutList", warehouseOutList);
        mv.addObject("warehousePickList", warehousePickList);
        mv.addObject("houseoutNum", lendout.getDeliverNum());
        mv.addObject("warehouseGoodsOperateLogList", warehouseGoodsOperateLogList);
        mv.addObject("kdNum", kdNum);
        // 日期
        String returnTime = DateUtil.convertString(lendout.getReturnTime(), "yyyy-MM-dd");
        mv.addObject("returnTime", returnTime);
        mv.addObject("user", user);
        mv.addObject("lendout", lendout);
        mv.addObject("businessType", 3);
        mv.setViewName("logistics/warehouseOut/view_lendOutDetail");
        logger.info("lendOutdetailJump商品外借跳转到出库详情页面结束");
        return mv;
    }

    /**
     * @param @param lendoutId
     * @return ModelAndView    返回类型
     * @throws
     * @Title: searchLendOutDelivery
     * @Description: TODO(外借单编辑收货信息查询)
     * @author strange
     * @date 2019年8月29日
     */
    @ResponseBody
    @RequestMapping("/searchLendOutDelivery")
    public ModelAndView searchLendOutDelivery(Integer lendoutId) {
        ModelAndView mv = new ModelAndView();
        LendOut lendout = LendOut.getinstance();
        lendout.setLendOutId(lendoutId);
         lendout =  warehouseOutService.getLendOutInfo(lendout);
        List<TraderContactVo> takeTraderContactList = new ArrayList<TraderContactVo>();
        List<TraderAddressVo> takeTraderAddressList = new ArrayList<TraderAddressVo>();
        TraderContactVo takeTraderContactVo = new TraderContactVo();
        Integer traderType = lendout.getTraderType() == 1 ? ErpConst.ONE : ErpConst.TWO;
        takeTraderContactVo.setTraderId(lendout.getTraderId());
        takeTraderContactVo.setIsEnable(1);
        takeTraderContactVo.setTraderType(traderType);
        Map<String, Object> takeMap = traderCustomerService.getTraderContactVoList(takeTraderContactVo);
        String takeTastr = (String) takeMap.get("contact");
        net.sf.json.JSONArray takeJson = net.sf.json.JSONArray.fromObject(takeTastr);
        takeTraderContactList = (List<TraderContactVo>) takeJson.toCollection(takeJson, TraderContactVo.class);
        takeTraderAddressList = (List<TraderAddressVo>) takeMap.get("address");
        // 获取物流公司列表
        List<Logistics> logisticsList = getLogisticsList(1);
        mv.addObject("logisticsList", logisticsList);
        // 运费说明
        if (lendout.getFreightDescription() == null || lendout.getFreightDescription() == 0) {
            lendout.setFreightDescription(470);//默认值
        }
        List<SysOptionDefinition> freightDescriptions = getSysOptionDefinitionList(469);
        mv.addObject("freightDescriptions", freightDescriptions);
        mv.addObject("takeTraderContactList", takeTraderContactList);
        mv.addObject("takeTraderAddressList", takeTraderAddressList);
        mv.addObject("lendout", lendout);
        mv.setViewName("logistics/warehouseOut/search_take_delivery");
        return mv;
    }

    /**
     * @param @param lendout
     * @return ResultInfo    返回类型
     * @throws
     * @Title: saveLendOutDelivery
     * @Description: TODO(外借单编辑信息保存)
     * @author strange
     * @date 2019年8月29日
     */
    @ResponseBody
    @RequestMapping("/saveLendOutEdit")
    public ResultInfo saveLendOutEdit(LendOut lendout) {
        ModelAndView mv = new ModelAndView();
        ResultInfo result = new ResultInfo();
        if (lendout.getLendOutStatus() != null && lendout.getLendOutStatus() == 2) {
            lendout.setBusinessType(10);
            Integer deliveryNum = warehouseOutService.getdeliveryNum(lendout);
            Integer i = 0;
            if (null != deliveryNum && deliveryNum == 0) {
                i = warehouseOutService.updateLendOutInfo(lendout);
            }
            if (i > 0) {
                result.setCode(0);
                result.setMessage("外借单关闭成功");
            } else {
                result.setCode(1);
                result.setMessage("外借单关闭失败");
            }
        } else {
            Integer j = warehouseOutService.updateLendOutInfo(lendout);
            if (j > 0) {
                result.setCode(0);
                result.setMessage("外借单更新成功");
            } else {
                result.setCode(1);
                result.setMessage("外借单更新失败");
            }
        }
        return result;
    }

    /**
     * @param @param session
     * @param @param lendout
     * @param @param businessType
     * @return ModelAndView    返回类型
     * @throws
     * @Title: viewLendOutPicking
     * @Description: TODO(确定外借出库的拣货数量)
     * @author strange
     * @date 2019年8月30日
     */
    @ResponseBody
    @RequestMapping(value = "/viewLendOutPicking")
    public ModelAndView viewLendOutPicking(HttpSession session, LendOut lendout, Integer businessType) {
        ModelAndView mv = new ModelAndView();
        lendout =  warehouseOutService.getLendOutInfo(lendout);
        User user = userService.getUserById(lendout.getCreator());
        //外借订单下的产品信息
        Goods goods = new Goods();
        goods.setGoodsId(lendout.getGoodsId());
        goods = goodsService.getGoodsById(goods);
        //已发数量
        goods.setDeliveryNum(lendout.getDeliverNum());
        goods.setNum(lendout.getLendOutNum());
        // 拣货记录清单
        Saleorder saleorder = new Saleorder();
        saleorder.setBussinessType(3);
        saleorder.setBussinessId(lendout.getLendOutId());
        //拣货数量
        Integer pickCnt = 0;
        List<WarehousePicking> warehousePickList = warehousePickService.getPickLendOutDetil(saleorder);
        if (null != warehousePickList) {
            for (WarehousePicking wp : warehousePickList) {
                pickCnt += wp.getNum();
            }
        }
        goods.setPickCnt(pickCnt);
        WarehouseGoodsStatus warehouseGoodsStatus = new WarehouseGoodsStatus();
        warehouseGoodsStatus.setCompanyId(1);
        warehouseGoodsStatus.setGoodsId(goods.getGoodsId());

        //商品库存信息
        SaleorderGoods saleorderGood = new SaleorderGoods();
        saleorderGood.setIsOut("0");
        saleorderGood.setBussinessId(lendout.getLendOutId());
        saleorderGood.setGoodsId(lendout.getGoodsId());
        saleorderGood.setCompanyId(1);
        //saleorderGood.setBussinessId(-1);
        saleorderGood.setBussinessType(2);
        List<WarehouseGoodsOperateLog> list = warehouseGoodsOperateLogService.getWlog(saleorderGood);
        for (WarehouseGoodsOperateLog log : list) {
//			BuyorderVo buy = new BuyorderVo();
//			buy.setBuyorderId(log.getBuyorderId());
//			buy = buyorderService.getBuyorderBarcodeVoDetail(buy,user);
//			log.setFlag(buy.getOrderType()==1 ? "1" : "0");//判断是否为订货单
            if (log.getBuyorderNo() != null) {
                String buyorderNo = log.getBuyorderNo();
                String no = buyorderNo.substring(0, 2);
                log.setFlag(no.equals("VP") ? "7" : "1");//判断是否为Vp单
            }
        }
        Integer vpNum = 0;
        Iterator<WarehouseGoodsOperateLog> iterator = list.iterator();
        while (iterator.hasNext()) {
            WarehouseGoodsOperateLog next = iterator.next();
            String flag = next.getFlag();
            Integer n = next.getNum();
            if (flag.equals("7")) {
                vpNum += n;
                iterator.remove();
            }
        }
        lendout.setWlist(list);
        //库存量
        Integer goodsStockNum = warehouseOutService.getGoodsStockByGoodsStatus(warehouseGoodsStatus);
        goods.setTotalNum(goodsStockNum - vpNum);
        List<Goods> goodsList = new ArrayList<Goods>();
        if (lendout.getLendOutNum() - pickCnt > 0 && goods.getTotalNum() - pickCnt - lendout.getDeliverNum() > 0) {
            goodsList.add(goods);
        }
//		for (AfterSalesGoodsVo afterSalesGoodsVo : list) {
//			afterSalesGoodsVo.setGoodsStock(goodsDataService.getGoodsStockNum(afterSalesGoodsVo.getGoodsId(),companyId));//库存
//			afterSalesGoodsVo.setOrderOccupy(goodsDataService.getGoodsOccupyNum(afterSalesGoodsVo.getGoodsId(),companyId));//占用
//			afterSalesGoodsVo.setAdjustableNum(goodsDataService.getGoodsNeedNum(afterSalesGoodsVo.getGoodsId(),companyId) -
//			goodsDataService.getGoodsOccupyNum(afterSalesGoodsVo.getGoodsId(),companyId));//占用
//		}
        for (int i = 0; i < goodsList.size(); i++) {
            int needCnt = goodsList.get(i).getNum() - goodsList.get(i).getPickCnt();
            List<WarehouseGoodsOperateLog> w = list;
            int allCnt = 0;
            for (int j = 0; j < w.size(); j++) {
                allCnt += w.get(j).getCnt();
                if (needCnt > 0) {
                    if (needCnt > w.get(j).getCnt()) {
                        w.get(j).setpCount(w.get(j).getCnt());
                    } else if (needCnt <= w.get(j).getCnt()) {
                        w.get(j).setpCount(needCnt);
                    }
                    needCnt -= w.get(j).getCnt();
                }
            }
            if (allCnt > needCnt) {
                goodsList.get(i).setpCountAll(goodsList.get(i).getNum() - goodsList.get(i).getPickCnt());
            } else {
                goodsList.get(i).setpCountAll(allCnt);
            }
        }
        lendout.setGoodslist(goodsList);
        mv.addObject("businessType", businessType);
        mv.addObject("user", user);
        mv.addObject("lendout", lendout);
        mv.setViewName("logistics/warehouseOut/view_lendoutPicking");
        return mv;
    }

    /**
     * <b>Description:</b><br> 外借单拣货详情页面
     *
     * @param lendout
     * @param session
     * @return
     * @Note <b>Author:</b> strange
     * <br><b>Date:</b> 2019年9月2日 上午10:42:44
     */
    @SuppressWarnings("rawtypes")
    @ResponseBody
    @RequestMapping(value = "/viewLendOutPickingDetail")
    @SystemControllerLog(operationType = "add", desc = "保存外借拣货数量")
    public ModelAndView viewLendOutPickingDetail(LendOut lendout, HttpSession session) {
        User curr_user = (User) session.getAttribute(ErpConst.CURR_USER);
        ModelAndView mv = new ModelAndView();
        LendOut lendout2 =  warehouseOutService.getLendOutInfo(lendout);
        User user = userService.getUserById(lendout2.getCreator());
        String companyName = user.getCompanyName();
        mv.addObject("companyName", companyName);

        lendout2.setCreatorName(user == null ? "" : user.getUsername());
        Long time = DateUtil.sysTimeMillis();
        String pickNums = lendout.getPickNums();
        mv.addObject("pickNums", pickNums);
        //外借订单下的产品信息
        Goods goods = new Goods();
        goods.setGoodsId(lendout2.getGoodsId());
        goods = goodsService.getGoodsById(goods);
        //已发数量
        goods.setDeliveryNum(lendout2.getDeliverNum());
        goods.setNum(lendout2.getLendOutNum());
        //拣货数量
        Integer pickCnt = warehousePickService.getLendOutPickCnt(lendout2);
        goods.setPickCnt(pickCnt);
        WarehouseGoodsStatus warehouseGoodsStatus = new WarehouseGoodsStatus();
        warehouseGoodsStatus.setCompanyId(1);
        warehouseGoodsStatus.setGoodsId(goods.getGoodsId());
        //库存量
        Integer goodsStockNum = warehouseOutService.getGoodsStockByGoodsStatus(warehouseGoodsStatus);
        goods.setTotalNum(goodsStockNum);
        List<Goods> goodsList2 = new ArrayList<Goods>();
        goodsList2.add(goods);
        //商品库存信息
        SaleorderGoods saleorderGood = new SaleorderGoods();
        saleorderGood.setIsOut("0");
        saleorderGood.setBussinessId(lendout2.getLendOutId());
        saleorderGood.setGoodsId(lendout2.getGoodsId());
        saleorderGood.setCompanyId(1);
        //saleorderGood.setBussinessId(-1);
        saleorderGood.setBussinessType(2);
        List<WarehouseGoodsOperateLog> list = warehouseGoodsOperateLogService.getWlog(saleorderGood);
        for (Goods good : goodsList2) {
            List<String> pickList = new ArrayList<>();
            List<String> batchNumberList = new ArrayList<>();
            List<String> expirationDateList = new ArrayList<>();
            List<String> relatedIdList = new ArrayList<>();
            List<String> relatedTypeList = new ArrayList<>();
            String picks[] = null;
            String values[] = null;
            String bpvalues[] = null;
            String ervalues[] = null;
            String rtvalues[] = null;
            String goodsList[] = pickNums.split("#");

            for (String st : goodsList) {
                String goodsValue[] = st.split("@");
                String gId = goodsValue[0];
                if (gId.equals(good.getGoodsId() + "")) {
                    String nums[] = goodsValue[1].split("_");
                    String pickNum = nums[0];
                    good.setNowNum(Integer.parseInt(pickNum));
                    if (nums.length > 1) {

                        picks = nums[1].split(",");
                        for (String str : picks) {
                            values = str.split("!");
                            pickList.add(values[0]);
                            bpvalues = values[1].split("%");
                            batchNumberList.add(bpvalues[0]);
                            ervalues = bpvalues[1].split("\\+");
                            expirationDateList.add(ervalues[0]);
                            rtvalues = ervalues[1].split("\\=");
                            relatedIdList.add(rtvalues[0]);
                            relatedTypeList.add(rtvalues[1]);
                        }
                    }
                    break;
                }
            }
            for (WarehouseGoodsOperateLog log : list) {
//				BuyorderVo buy = new BuyorderVo();
//				buy.setBuyorderId(log.getBuyorderId());
//				buy = buyorderService.getBuyorderBarcodeVoDetail(buy,user);
//				log.setFlag(buy.getOrderType()==1 ? "1" : "0");//判断是否为订货单
                if (log.getBuyorderNo() != null) {
                    String buyorderNo = log.getBuyorderNo();
                    String no = buyorderNo.substring(0, 2);
                    log.setFlag(no.equals("VP") ? "7" : "1");//判断是否为Vp单
                }
            }
            Integer vpNum = 0;
            Iterator<WarehouseGoodsOperateLog> iterator = list.iterator();
            while (iterator.hasNext()) {
                WarehouseGoodsOperateLog next = iterator.next();
                String flag = next.getFlag();
                Integer n = next.getNum();
                if (flag.equals("7")) {
                    vpNum += n;
                    iterator.remove();
                }
            }
            List<WarehouseGoodsOperateLog> wlist = new ArrayList<WarehouseGoodsOperateLog>();
            if (CollectionUtils.isNotEmpty(list)) {
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setpCtn(pickList.get(i));
                    if (!"-1".equals(batchNumberList.get(i))) {
                        list.get(i).setBatchNumber(batchNumberList.get(i));
                    }
                    list.get(i).setExpirationDate(Long.valueOf(expirationDateList.get(i)));
                    list.get(i).setOrderId(Integer.parseInt(relatedIdList.get(i)));
                    if (!"1".equals(relatedTypeList.get(i))) {
                        list.get(i).setYwType(2);
                    } else {
                        list.get(i).setYwType(1);
                    }
                }
                for (WarehouseGoodsOperateLog wl : list) {
                    if (Integer.parseInt(wl.getpCtn()) > 0) {
                        warehouseGoodsOperateLogService.updateWarehouselogInfo(wl);
                        wlist.add(wl);
                    }
                }
            }
            lendout2.setWlist(wlist);
        }
        lendout2.setGoodslist(goodsList2);
        Saleorder sd = new Saleorder();
        sd.setBussinessId(lendout2.getLendOutId());
        sd.setBussinessType(3);//外借单拣货标识
        sd.setLendout(lendout2);
        ResultInfo<?> result = warehousePickService.savePickRecord(sd, session);
        //拣货记录
        Saleorder saleorder = new Saleorder();
        saleorder.setBussinessId(lendout2.getLendOutId());
        saleorder.setBussinessType(3);//外借单拣货标识
        List<WarehousePicking> warehousePickList = warehousePickService.getPickDetil(saleorder);
        for (WarehousePicking warehousePicking : warehousePickList) {
            User u = userService.getUserById(warehousePicking.getCreator());
            warehousePicking.setOperator(u == null ? "" : u.getUsername());
        }
        List<String> timeArray = new ArrayList<>();
        if (null != warehousePickList) {
            for (WarehousePicking wp : warehousePickList) {
                if (wp.getCnt() == 0)
                    timeArray.add(DateUtil.convertString(wp.getAddTime(), "YYYY-MM-dd HH:mm:ss"));
            }
            HashSet<String> tArray = new HashSet<String>(timeArray);
            mv.addObject("timeArray", tArray);
        }
        mv.addObject("warehousePickList", warehousePickList);
        mv.addObject("user", user);
        mv.addObject("time", time);
        mv.addObject("userName", curr_user.getUsername());
        mv.addObject("businessType", lendout.getBusinessType());
        mv.addObject("lendout", lendout2);
        mv.setViewName("logistics/warehouseOut/view_lendoutspicking_detail");
        return mv;
    }

    /**
     * <b>Description:</b><br> 外借扫码出库
     *
     * @param LendOut
     * @param session
     * @return
     * @Note <b>Author:</b> strange
     * <br><b>Date:</b> 2019年9月2日 上午10:42:44
     */
    @ResponseBody
    @RequestMapping(value = "/warehouseSmLendOut")
    public ModelAndView warehouseSmLendOut(LendOut lendout, HttpSession session) {
//		User curr_user = (User)session.getAttribute(ErpConst.CURR_USER);
        ModelAndView mv = new ModelAndView();
        LendOut lo = LendOut.getinstance();
        lo = warehouseOutService.getLendOutInfo(lendout);
        User user = userService.getUserById(lo.getCreator());
        lo.setCreatorName(user == null ? "" : user.getUsername());
        int goodsNum = lo.getLendOutNum();
//		//拣货记录
        Saleorder saleorder = new Saleorder();
        saleorder.setBussinessId(lo.getLendOutId());
        saleorder.setBussinessType(3);
        List<WarehousePicking> warehousePickList = warehousePickService.getPickDetil(saleorder);
        if (null != warehousePickList) {
            for (WarehousePicking warehousePicking : warehousePickList) {
                User u = userService.getUserById(warehousePicking.getCreator());
                warehousePicking.setOperator(u == null ? "" : u.getUsername());
            }
            List<String> timeArray = new ArrayList<>();
            for (WarehousePicking wp : warehousePickList) {
                if (wp.getCnt() == 0)
                    timeArray.add(DateUtil.convertString(wp.getAddTime(), "YYYY-MM-dd HH:mm:ss"));
            }
            HashSet<String> tArray = new HashSet<String>(timeArray);
            mv.addObject("timeArray", tArray);
        }
        //外借订单下的产品信息
        Goods goods = new Goods();
        goods.setGoodsId(lo.getGoodsId());
        goods = goodsService.getGoodsById(goods);
        //出库数量
        lo.setBusinessType(10);
        Integer deliveryNum = warehouseOutService.getLendOutdeliveryNum(lo);
        goods.setDeliveryNum(deliveryNum);
        goods.setNum(lo.getLendOutNum());
        List<Goods> goodslist = new ArrayList<Goods>();
        goodslist.add(goods);
        lo.setGoodslist(goodslist);
        mv.addObject("goodsNum", goodsNum);
        mv.addObject("warehouseBarcodeOutList", warehousePickList);
        mv.addObject("lendout", lo);
        mv.addObject("businessType", lendout.getBusinessType());
        mv.setViewName("logistics/warehouseOut/view_sm_warehouselendout");
        return mv;
    }

    /**
     * <b>Description:</b><br> 保存外借条码出库记录
     *
     * @param afterSales
     * @param session
     * @return
     * @throws SQLException
     * @NoteH <b>Author:</b> scott
     * <br><b>Date:</b> 2017年11月7日 上午10:54:01
     */
    @MethodLock(field = "lendOutId",className = LendOut.class)
    @ResponseBody
    @RequestMapping(value = "/warehouselendoutSMEnd")
    @SystemControllerLog(operationType = "add", desc = "保存外借扫码出库数量")
    public ModelAndView warehouselendoutSMEnd(LendOut lendout, HttpSession session, Integer bussinessType) throws SQLException {
        User curr_user = (User) session.getAttribute(ErpConst.CURR_USER);
        ModelAndView mv = new ModelAndView();
//		AfterSalesVo asv  = afterSalesOrderService.getAfterSalesVoListById(afterSales);
        LendOut lo = LendOut.getinstance();
        lo = warehouseOutService.getLendOutInfo(lendout);
        User user = userService.getUserById(lo.getCreator());
        lo.setCreatorName(user == null ? "" : user.getUsername());
        Long time = DateUtil.sysTimeMillis();
        String pickNums = lendout.getPickNums();
        mv.addObject("pickNums", pickNums);
        String idCnt = lendout.getIdCnt();
        String values[] = idCnt.split("#");
        StringBuilder newidCnt = new StringBuilder();
        List<WarehouseGoodsOperateLog> wgList = new ArrayList<WarehouseGoodsOperateLog>();
        Integer num = 0;
        for (int i = 0; i < values.length; i++) {
            String nc[] = values[i].split(",");
            String warehouseGoodsOperateLogId = nc[0];
            WarehouseGoodsOperateLog w = new WarehouseGoodsOperateLog();
            w.setWarehouseGoodsOperateLogId(Integer.parseInt(warehouseGoodsOperateLogId));
            //根据id获取入库信息，设置为出库信息
            WarehouseGoodsOperateLog wl = warehouseOutService.getSMGoods(w);
            Barcode barcode = warehouseOutService.getBarcodeByWarehouseGoodsOperateLogId(w.getWarehouseGoodsOperateLogId());
            Integer barcodeIsEnable = warehouseGoodsOperateLogService.getBarcodeIsEnable(wl, 2);
            //如果有条码为vp单出库则失败
            if (barcode.getType() == 1) {
                String buyorderNo = warehouseOutService.getBuyOrderNoByBarcodeId(barcode);
                String no = buyorderNo.substring(0, 2);
                if (no.equals("VP")) {
                    return fail(mv);
                }
            }
            //判断条码是否使用过
            if ( barcodeIsEnable > 0) {
                num += Integer.parseInt(nc[1]);
                //设置售后产品id
                AfterSalesVo av = new AfterSalesVo();
                av.setCompanyId(curr_user.getCompanyId());
                av.setGoodsId(wl.getGoodsId());
                wl.setRelatedId(lendout.getLendOutId());
                wl.setNum(0 - Integer.parseInt(nc[1]));
                wl.setOperateType(10);//外借出库
                wl.setAddTime(time);
                wl.setCreator(curr_user.getUserId());
                wl.setUpdater(curr_user.getUserId());
                //wl.setWarehousePickingDetailId(Integer.parseInt(nc[2]));;
                wl.setModTime(time);
                wl.setIsEnable(1);
                wgList.add(wl);
                newidCnt.append(nc[0]).append(",").append(nc[1]).append("#");
            }
        }
        if (wgList.size() == 0) {
            return fail(mv);
        }
        //批量插入条码出库信息
        if (CollectionUtils.isNotEmpty(wgList)) {
            ResultInfo<?> result = warehouseGoodsOperateLogService.addWlogList(wgList);
        }
        //更新外借单数据
        //lendout.setBusinessType(660);//快递类型
        //Integer eNum = lendOutMapper.getdeliveryNum(lendout);//已出库数量
        lo.setNum(num);
        lo.setModTime(DateUtil.sysTimeMillis());
        Integer i = warehouseOutService.updateLendoutDeliverNum(lo);
        //出库记录清单
        Saleorder saleorder = new Saleorder();
        saleorder.setBussinessId(lo.getLendOutId());
        saleorder.setBussinessType(10);
        //获取出库记录
//	    List<WarehouseGoodsOperateLog> warehouseOutList = warehouseOutService.getOutDetil(saleorder);
        List<WarehouseGoodsOperateLogVo> warehouseOutList = warehouseOutService.getLendOutDetil(saleorder);
        List<String> timeArrayWl = new ArrayList<>();
        if (null != warehouseOutList) {
            for (WarehouseGoodsOperateLogVo wl : warehouseOutList) {
                User u = userService.getUserById(wl.getCreator());
                wl.setOpName(u == null ? "" : u.getUsername());
                timeArrayWl.add(DateUtil.convertString(wl.getAddTime(), "YYYY-MM-dd HH:mm:ss"));
            }
            HashSet<String> wArray = new HashSet<String>(timeArrayWl);
            mv.addObject("timeArrayWl", wArray);
        }

        mv.addObject("warehouseOutList", warehouseOutList);
        mv.addObject("lendout", lo);
        mv.addObject("time", time);
        mv.addObject("goodsNum", lo.getLendOutNum());
        mv.addObject("userName", curr_user.getUsername());
        mv.addObject("businessType", 660);
        mv.setViewName("logistics/warehouseOut/view_lendout_end");
        return mv;
    }
    /**
    *宝石花出库单列表
    * @Author:strange
    * @Date:13:45 2020-02-20
    */
    @ResponseBody
    @RequestMapping(value = "/flowerPrintOutListPage")
    public ModelAndView flowerPrintOutListPage(HttpServletRequest request, Saleorder saleorder,
                                            @RequestParam(required = false, defaultValue = "1") Integer pageNo, // required
                                            // false:可不传入pageNo这个参数，true必须传入，defaultValue默认值，若无默认值，使用Page类中的默认值
                                            @RequestParam(required = false) Integer pageSize, HttpSession session){
        // 获取session中user信息
        User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
        ModelAndView mv = new ModelAndView();
        //是否是销售
        Boolean saleFlag = userService.getSaleFlagUserId(session_user.getUserId());
        mv.addObject("saleFlag",saleFlag);

        Page page = getPageTag(request, pageNo, pageSize);
        //设置时间,默认时间为30天内
        setSearchTime(request,saleorder);
        if(StringUtils.isBlank(request.getParameter("secondSearch"))){
            saleorder.setSearchBegintime(DateUtil.convertLong(LocalDate.now().minusDays(30)+ " 00:00:00","yyyy-MM-dd HH:mm:ss"));
            saleorder.setSearchEndtime(DateUtil.convertLong(LocalDate.now() + " 23:59:59","yyyy-MM-dd HH:mm:ss"));
        }
        //获取宝石花出库列表
        Map<String, Object> map = saleorderService.getFlowerPrintOutListPage(request, saleorder, page);
        List<Saleorder> list = (List<Saleorder>) map.get("saleorderList");
        if(CollectionUtils.isNotEmpty(list)){
        for (int i = 0; i < list.size(); i++) {
            Map<String,BigDecimal> moneyData=saleorderService.getSaleorderDataInfo(list.get(i).getSaleorderId());
            if(moneyData!=null){
                if(moneyData.get("realAmount")!=null) {
                    list.get(i).setRealAmount(moneyData.get("realAmount"));
                }
                }
        }
        }
        mv.addObject("saleorderList",list);
        mv.addObject("page", map.get("page"));
        mv.addObject("saleorder",saleorder);
        mv.setViewName("logistics/warehouseOut/list_flowerPrintOut");
        return mv;
    }

    private void setSearchTime(HttpServletRequest request, Saleorder saleorder) {
        if (null != request.getParameter("searchBegintimeStr") && request.getParameter("searchBegintimeStr") != "") {
            saleorder.setSearchBegintime(DateUtil.convertLong(request.getParameter("searchBegintimeStr") + " 00:00:00",
                    "yyyy-MM-dd HH:mm:ss"));
        }
        if (null != request.getParameter("searchEndtimeStr") && request.getParameter("searchEndtimeStr") != "") {
            saleorder.setSearchEndtime(DateUtil.convertLong(request.getParameter("searchEndtimeStr") + " 23:59:59",
                    "yyyy-MM-dd HH:mm:ss"));
        }
    }


    @ResponseBody
    @RequestMapping("/getInLibraryBarcode")
    public ResultInfo getInLibraryBarcode(Saleorder saleorder,HttpServletRequest request,@RequestParam(value = "afterSalesId",defaultValue = "") Integer afterSalesId)throws Exception {
        User curr_user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        ResultInfo resultInfo = new ResultInfo();
        Integer position=0;
        if (null!=curr_user.getPositType()){
            position=curr_user.getPositType();
        }
        if(!curr_user.getUsername().equals("njadmin")){
            if(!position.equals(313)){//物流账户
                resultInfo.setMessage("非物流账户不可出库");
                return resultInfo;
            }
        }

        String idCnt = saleorder.getIdCnt();
        String values[] = idCnt.split("#");
        Map<String, Integer> hashMap = new HashMap<>();//防重复（入库id）
        List<WarehouseGoodsOperateLog> warehouseGoodsOperateLogList = new ArrayList<>();

        for (int i = 0; i < values.length; i++) {
            String nc[] = values[i].split(",");
            String batchNumber = nc[0].equals("") ? null : nc[0];
            Long expirationDate = Long.parseLong(nc[1].equals("")?"0":nc[1]);
            Integer goodsId = Integer.parseInt(nc[2]);
            Integer num = Integer.parseInt(nc[3]);
            Integer warehousePickingDetailId=Integer.parseInt(nc[4]);
            WarehousePicking warehousePicking=new WarehousePicking();
            warehousePicking.setWarehousePickingDetailId(warehousePickingDetailId);
            warehousePicking.setGoodsId(goodsId);
            WarehousePicking warehousePicking1=warehousePickingMapper.selectByPrimaryKey(warehousePicking);
            if(warehousePicking1==null){
                resultInfo.setMessage("商品已不存在，请重新拣货出库");
                return resultInfo;
            }

            if (saleorder.getBussinessType() == 0) {
                List<SaleorderGoods> saleorderGoodList = saleorderService.getSaleGoodsNoOutNumList(saleorder.getSaleorderId());
                boolean bool = saleorderGoodList.stream().anyMatch(a -> a.getGoodsId().equals(goodsId));//是否包含goodsid
                if(!bool){
                    log.info("表格错误，SKU为V" + goodsId+ "的商品的条码数大于需出库数！");
                    resultInfo.setMessage("表格错误，SKU为V" + goodsId + "的商品的条码数大于需出库数！");
                    return resultInfo;
                }
                for (SaleorderGoods sg : saleorderGoodList) {
                    if (sg.getGoodsId().equals(goodsId)) {
                        if (num != null) {
                            if (num > (sg.getTotalNum() - sg.getDeliveryNum())) {
                                log.info("表格错误，SKU为V" + sg.getGoodsId() + "的商品的条码数大于需出库数！");
                                resultInfo.setMessage("表格错误，SKU为V" + sg.getGoodsId() + "的商品的条码数大于需出库数！");
                                return resultInfo;
//				    throw new Exception("表格错误，SKU为V" + sg.getGoodsId() + "的商品的条码数大于需出库数！");
                            }
                        }
                    }
                }
            } else {
                List<AfterSalesGoods> asvList = afterSalesOrderService.getAfterSalesGoodsNoOutNumList(afterSalesId);
                boolean bool = asvList.stream().anyMatch(a -> a.getGoodsId().equals(goodsId));//是否包含goodsid
                if(!bool){
                    log.info("表格错误，SKU为V" + goodsId+ "的商品的条码数大于需出库数！");
                    resultInfo.setMessage("表格错误，SKU为V" + goodsId + "的商品的条码数大于需出库数！");
                    return resultInfo;
                }
                // 查询未出库的售后产品--原查询没有将相同商品总数分开
//			List<AfterSalesGoods> asvList = afterSalesOrderService
//				.getAfterSalesGoodsNoOutList(list.get(0).getAfterSalesId());
                for (AfterSalesGoods sg : asvList) {
                    if (sg.getGoodsId().equals(goodsId)) {
                        if (num != null) {
                            if (num > (sg.getNum() - sg.getDeliveryNum())) {
                                log.info("表格错误，SKU为V" + sg.getGoodsId() + "的商品的条码数大于需出库数！");
                                resultInfo.setMessage("表格错误，SKU为V" + sg.getGoodsId() + "的商品的条码数大于需出库数！");
                                return resultInfo;
//				    throw new Exception("表格错误，SKU为V" + sg.getGoodsId() + "的商品的条码数大于需出库数！");
                            }
                        }
                    }

                }
            }
            WarehouseGoodsOperateLogVo warehouseGoodsOperateLogVo = new WarehouseGoodsOperateLogVo();
            warehouseGoodsOperateLogVo.setGoodsId(goodsId);
            warehouseGoodsOperateLogVo.setBatchNumber(batchNumber);
            warehouseGoodsOperateLogVo.setExpirationDate(expirationDate);
            List<WarehouseGoodsOperateLogVo> sameBatchGoodsList = warehouseOutService.getSameBatchGoodsInfo(warehouseGoodsOperateLogVo);
            if (sameBatchGoodsList == null && sameBatchGoodsList.size() < 0) {
                resultInfo.setMessage("原拣货记录库存量不足，请重新拣货，出库");
                return resultInfo;
            }
            int sum = 0;//库存量
            if (sameBatchGoodsList != null && sameBatchGoodsList.size() > 0) {
                for (int j = 0; j < sameBatchGoodsList.size(); j++) {
                    if (sum >= num) {
                        break;
                    }
                    WarehouseGoodsOperateLog warehouseGoodsOperateLog = new WarehouseGoodsOperateLog();
                    warehouseGoodsOperateLog.setWarehouseGoodsOperateLogId(sameBatchGoodsList.get(j).getWarehouseGoodsOperateLogId());
                    Integer barcodeIsEnable = warehouseGoodsOperateLogService.getBarcodeIsEnable(warehouseGoodsOperateLog, 2);
                    /*Integer barcodeIsEnable=1;*/
                    if (barcodeIsEnable > 0) {
                        warehouseGoodsOperateLog.setNum(1);
                        Integer id = hashMap.get(sameBatchGoodsList.get(j).getWarehouseGoodsOperateLogId().toString());
                        if (id == null) {
                            sum = sum + 1;
                            warehouseGoodsOperateLogList.add(warehouseGoodsOperateLog);
                            hashMap.put(sameBatchGoodsList.get(j).getWarehouseGoodsOperateLogId().toString(), sameBatchGoodsList.get(j).getNum());
                        }
                    }

                }
            }
            if (sum < num) {
                resultInfo.setMessage("原拣货记录库存量不足，请重新拣货，出库");
                return resultInfo;
            }
        }
     //   warehouseGoodsOperateLogService.releaseDistributedLock(warehouseGoodsOperateLogList);
        resultInfo.setCode(0);
        resultInfo.setMessage("操作成功");
        resultInfo.setListData(warehouseGoodsOperateLogList);
        return resultInfo;
    }



}















