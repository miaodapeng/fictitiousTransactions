package com.vedeng.finance.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.common.constants.Contant;
import com.google.common.collect.Lists;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.vedeng.authorization.model.Role;
import com.vedeng.common.constant.*;
import com.vedeng.common.model.TemplateVar;
import com.vedeng.common.model.vo.ReqTemplateVariable;
import com.vedeng.common.money.OrderMoneyUtil;
import com.vedeng.common.util.*;
import com.vedeng.finance.model.*;
import com.vedeng.goods.service.VgoodsService;
import com.vedeng.trader.model.Trader;
import com.vedeng.trader.model.TraderCustomer;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.task.Task;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.omg.PortableInterceptor.INACTIVE;
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

import com.alibaba.fastjson.JSON;
import com.vedeng.activiti.service.ActionProcdefService;
import com.vedeng.aftersales.model.AfterSales;
import com.vedeng.aftersales.model.AfterSalesDetail;
import com.vedeng.aftersales.model.vo.AfterSalesVo;
import com.vedeng.aftersales.service.AfterSalesService;
import com.vedeng.authorization.model.Organization;
import com.vedeng.authorization.model.User;
import com.vedeng.common.annotation.SystemControllerLog;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.logisticmd.CallExpressService;
import com.vedeng.common.logisticmd.CallWaybillPrinter;
import com.vedeng.common.logisticmd.ZopExpressService;
import com.vedeng.common.model.FileInfo;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.putHCutil.service.HcSaleorderService;
import com.vedeng.common.validator.FormToken;
import com.vedeng.finance.service.CapitalBillService;
import com.vedeng.finance.service.InvoiceService;
import com.vedeng.finance.service.TraderAccountPeriodApplyService;
import com.vedeng.logistics.model.Express;
import com.vedeng.logistics.model.ExpressDetail;
import com.vedeng.logistics.model.Logistics;
import com.vedeng.logistics.model.WarehouseGoodsOperateLog;
import com.vedeng.logistics.service.ExpressService;
import com.vedeng.logistics.service.LogisticsService;
import com.vedeng.logistics.service.WarehouseOutService;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.model.SaleorderGoods;
import com.vedeng.order.model.vo.BuyorderVo;
import com.vedeng.order.service.BuyorderService;
import com.vedeng.order.service.SaleorderService;
import com.vedeng.soap.service.VedengSoapService;
import com.vedeng.system.model.Attachment;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.system.service.FtpUtilService;
import com.vedeng.system.service.OrgService;
import com.vedeng.system.service.UserService;
import com.vedeng.system.service.VerifiesRecordService;
import com.vedeng.trader.model.CommunicateRecord;
import com.vedeng.trader.model.vo.TraderCertificateVo;
import com.vedeng.trader.model.vo.TraderCustomerVo;
import com.vedeng.trader.model.vo.TraderFinanceVo;
import com.vedeng.trader.model.vo.TraderMedicalCategoryVo;
import com.vedeng.trader.service.TraderCustomerService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * <b>Description:</b><br>
 * 发票管理
 *
 * @author duke
 * @Note <b>ProjectName:</b> erp <br>
 *       <b>PackageName:</b> com.vedeng.finance.controller <br>
 *       <b>ClassName:</b> InvoiceController <br>
 *       <b>Date:</b> 2017年8月17日 下午5:48:20
 */
@Controller
@RequestMapping("/finance/invoice")
public class InvoiceController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(InvoiceController.class);

    // @Autowired // 自动装载
    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    @Autowired
    @Qualifier("invoiceService")
    private InvoiceService invoiceService;// 自动注入invoiceService

    @Autowired
    @Qualifier("accountPeriodService")
    private TraderAccountPeriodApplyService accountPeriodService;

    @Autowired
    @Qualifier("userService")
    private UserService userService;// 自动注入userService

    @Autowired
    @Qualifier("orgService")
    private OrgService orgService;// 部门

    @Autowired
    @Qualifier("expressService")
    private ExpressService expressService;// 部门

    @Autowired
    @Qualifier("logisticsService")
    private LogisticsService logisticsService;// 部门

    @Autowired
    @Qualifier("capitalBillService")
    private CapitalBillService capitalBillService;

    @Autowired
    @Qualifier("afterSalesOrderService")
    private AfterSalesService afterSalesOrderService;

    @Autowired
    @Qualifier("actionProcdefService")
    private ActionProcdefService actionProcdefService;

    @Autowired
    @Qualifier("verifiesRecordService")
    private VerifiesRecordService verifiesRecordService;

    @Resource
    private BuyorderService buyorderService;

    @Autowired
    @Qualifier("vedengSoapService")
    private VedengSoapService vedengSoapService;

    @Autowired
    @Qualifier("ftpUtilService")
    private FtpUtilService ftpUtilService;

    @Autowired
    @Qualifier("warehouseOutService")
    private WarehouseOutService warehouseOutService;

    @Autowired
    @Qualifier("hcSaleorderService")
    private HcSaleorderService hcSaleorderService;

    @Autowired
    private VgoodsService vGoodsService;

    /**
     * <b>Description:</b><br>
     * 采购发票录入页面初始化
     *
     * @param request
     * @param invoice
     * @return
     * @Note <b>Author:</b> duke <br>
     *       <b>Date:</b> 2017年8月16日 下午4:26:15
     */
    @FormToken(save = true)
    @ResponseBody
    @RequestMapping(value = "buyInvoiceInput")
    public ModelAndView buyInvoiceInput(HttpServletRequest request, BuyorderVo bo, Invoice invoice) {
        ModelAndView mv = new ModelAndView();
        try {
            // 发票类型
            List<SysOptionDefinition> invoiceTypeList = getSysOptionDefinitionList(SysOptionConstant.ID_428);
            mv.addObject("invoiceTypeList", invoiceTypeList);
            if (bo != null && (StringUtils.isNotBlank(bo.getModel()) || StringUtils.isNotBlank(bo.getTraderName())
                    || StringUtils.isNotBlank(bo.getGoodsName()) || StringUtils.isNotBlank(bo.getBrandName())
                    || StringUtils.isNotBlank(bo.getBuyorderNo()) || bo.getInvoiceType() != null)) {
                User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
                bo.setCompanyId(user.getCompanyId());
                invoice.setCompanyId(user.getCompanyId());
                Map<String, Object> map = invoiceService.getInvoiceListByBuyorder(bo, invoice, 1);
                List<BuyorderVo> buyOrderLists = (List<BuyorderVo>) map.get("buyorderList");
                mv.addObject("buyorderList",buyOrderLists);

                //新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
                if(!CollectionUtils.isEmpty(buyOrderLists)){
                    List<Integer> skuIds = new ArrayList<>();
                    buyOrderLists.stream().forEach(buyOrder -> {
                        if(!CollectionUtils.isEmpty(buyOrder.getBuyorderGoodsVoList())){
                            buyOrder.getBuyorderGoodsVoList().stream().forEach(buyOrderGoods->{
                                skuIds.add(buyOrderGoods.getGoodsId());
                            });
                        }
                    });
                    List<Map<String,Object>> skuTipsMap = this.vGoodsService.skuTipList(skuIds);
                    Map<String,Map<String,Object>> newSkuInfosMap = skuTipsMap.stream().collect(Collectors.toMap(key->key.get("SKU_NO").toString(), v -> v, (k, v) -> v));
                    mv.addObject("newSkuInfosMap", newSkuInfosMap);
                }
                //新商品流切换 VDERP-1970 add by brianna 2020/3/16 end

                mv.addObject("invoiceDetailList", (List<InvoiceDetail>) map.get("invoiceDetailList"));
                mv.addObject("inputInvoiceType", 1);
                mv.addObject("bo", bo);
            }
        } catch (Exception e) {
            logger.error("buyInvoiceInput:", e);
            return fail(mv);
        }

        mv.setViewName("finance/invoice/buy_invoice_input");
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 售后录票页面初始化
     *
     * @param request
     * @param bo
     * @param invoice
     * @return
     * @Note <b>Author:</b> duke <br>
     *       <b>Date:</b> 2018年5月14日 下午5:45:12
     */
    @FormToken(save = true)
    @ResponseBody
    @RequestMapping(value = "afterInvoiceInput")
    public ModelAndView afterInvoiceInput(HttpServletRequest request, BuyorderVo bo, Invoice invoice) {
        ModelAndView mv = new ModelAndView();
        try {
            // 发票类型
            List<SysOptionDefinition> invoiceTypeList = getSysOptionDefinitionList(SysOptionConstant.ID_428);
            mv.addObject("invoiceTypeList", invoiceTypeList);
            if (bo != null
                    && (StringUtils.isNotBlank(bo.getTraderName()) || StringUtils.isNotBlank(bo.getBuyorderNo()))) {
                User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
                bo.setCompanyId(user.getCompanyId());
                invoice.setCompanyId(user.getCompanyId());
                Map<String, Object> map = invoiceService.getInvoiceListByBuyorder(bo, invoice, 2);// 2:售后
                mv.addObject("afterOrderList", (List<AfterSalesVo>) map.get("afterOrderList"));

                //新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
                //售后单的列表
                List<AfterSalesVo> afterSaleOrderList = (List<AfterSalesVo>)map.get("afterOrderList");
                if(!CollectionUtils.isEmpty(afterSaleOrderList)){

                    List<Integer> skuIds = new ArrayList<>();

                    afterSaleOrderList.stream().forEach(afterSaleOrder -> {

                        //售后单对应的商品
                        if(!CollectionUtils.isEmpty(afterSaleOrder.getAfterSalesGoodsList())){
                            afterSaleOrder.getAfterSalesGoodsList().stream().forEach(afterSaleOrderGood -> {
                                skuIds.add(afterSaleOrderGood.getGoodsId());
                            });
                        }

                    });

                    List<Map<String,Object>> skuTipsMap = this.vGoodsService.skuTipList(skuIds);
                    Map<String,Map<String,Object>> newSkuInfosMap = skuTipsMap.stream().collect(Collectors.toMap(key->key.get("SKU_NO").toString(), v -> v, (k, v) -> v));
                    mv.addObject("newSkuInfosMap", newSkuInfosMap);
                }
                //新商品流切换 VDERP-1970 add by brianna 2020/3/16 end

                mv.addObject("invoiceDetailList", (List<InvoiceDetail>) map.get("invoiceDetailList"));
                mv.addObject("inputInvoiceType", 2);
                mv.addObject("bo", bo);
            }
        } catch (Exception e) {
            logger.error("afterInvoiceInput:", e);
            return fail(mv);
        }

        mv.setViewName("finance/invoice/after_invoice_input");
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 采购录票信息添加保存
     *
     * @param request
     * @param invoice
     * @param relatedIdArr
     * @param invoiceNumArr
     * @param invoicePriceArr
     * @return
     * @Note <b>Author:</b> duke <br>
     *       <b>Date:</b> 2017年8月21日 下午5:54:12
     */
    @FormToken(remove = true)
    @ResponseBody
    @RequestMapping(value = "saveBuyorderInvoice")
    @SystemControllerLog(operationType = "add", desc = "采购录票信息添加保存")
    public ResultInfo<?> saveBuyorderInvoice(HttpServletRequest request, Invoice invoice,
                                             @RequestParam(required = false, value = "saveInvoiceType") Integer saveInvoiceType,
                                             @RequestParam(required = false, value = "relatedIdArr") String relatedIdArr,
                                             @RequestParam(required = false, value = "detailGoodsIdArr") String detailGoodsIdArr,
                                             @RequestParam(required = false, value = "invoiceNumArr") String invoiceNumArr,
                                             @RequestParam(required = false, value = "invoicePriceArr") String invoicePriceArr,
                                             @RequestParam(required = false, value = "invoiceTotleAmountArr") String invoiceTotleAmountArr) {
        try {
            List<Integer> relatedIdList = JSON.parseArray("[" + relatedIdArr + "]", Integer.class);
            List<Integer> detailGoodsIdList = JSON.parseArray("[" + detailGoodsIdArr + "]", Integer.class);
            List<BigDecimal> invoiceNumList = JSON.parseArray("[" + invoiceNumArr + "]", BigDecimal.class);
            List<BigDecimal> invoicePriceList = JSON.parseArray("[" + invoicePriceArr + "]", BigDecimal.class);
            List<BigDecimal> invoiceTotleAmountList = JSON.parseArray("[" + invoiceTotleAmountArr + "]",
                    BigDecimal.class);

            invoice.setTag(2);// 录票
            invoice.setRelatedIdList(relatedIdList);
            invoice.setDetailGoodsIdList(detailGoodsIdList);
            invoice.setInvoiceNumList(invoiceNumList);
            invoice.setInvoicePriceList(invoicePriceList);
            invoice.setInvoiceTotleAmountList(invoiceTotleAmountList);
            User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
            if (user != null) {
                invoice.setCreator(user.getUserId());
                invoice.setAddTime(DateUtil.gainNowDate());
                invoice.setCompanyId(user.getCompanyId());
                invoice.setUpdater(user.getUserId());
                invoice.setModTime(DateUtil.gainNowDate());
            }
            if (saveInvoiceType.equals(2)) {// 售后
                invoice.setType(SysOptionConstant.ID_504);// 售后录票
            } else {
                invoice.setType(SysOptionConstant.ID_503);// 采购录票
            }
            return invoiceService.saveInvoice(invoice);
        } catch (Exception e) {
            logger.error("saveBuyorderInvoice:", e);
            return new ResultInfo<>();
        }
    }

    /**
     * <b>Description:</b><br>
     * 采购录票确认列表
     *
     * @param request
     * @param invoice
     * @return
     * @Note <b>Author:</b> duke <br>
     *       <b>Date:</b> 2018年5月4日 下午1:30:48
     */
    @ResponseBody
    @RequestMapping(value = "buyInvoiceConfirmList")
    public ModelAndView buyInvoiceConfirmList(HttpServletRequest request, Invoice invoice,
                                              @RequestParam(required = false, defaultValue = "1") Integer pageNo,
                                              @RequestParam(required = false) Integer pageSize) {
        ModelAndView mv = new ModelAndView();
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        try {
            Page page = getPageTag(request, pageNo, pageSize);
            if (null != user) {
                invoice.setCompanyId(user.getCompanyId());
            }
            Map<String, Object> map = invoiceService.getBuyInvoiceConfirmListPage(invoice, page);
            if (null != map) {
                mv.addObject("invoiceList", map.get("invoiceList"));
                mv.addObject("page", map.get("page"));
            }
        } catch (Exception e) {
            logger.error("buyInvoiceConfirmList:", e);
        }
        mv.setViewName("finance/invoice/buy_invoice_confirm_list");
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 收票审核列表
     *
     * @param request
     * @param invoice
     * @return
     * @Note <b>Author:</b> duke <br>
     *       <b>Date:</b> 2017年8月22日 下午6:37:37
     */
    @ResponseBody
    @RequestMapping(value = "buyInvoiceAuditList")
    public ModelAndView buyInvoiceAuditList(HttpServletRequest request, Invoice invoice,
                                            @RequestParam(required = false, value = "startTime") String startTime,
                                            @RequestParam(required = false, value = "endTime") String endTime,
                                            @RequestParam(required = false, defaultValue = "1") Integer pageNo,
                                            @RequestParam(required = false) Integer pageSize) {
        ModelAndView mv = new ModelAndView();
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        try {
            if (StringUtils.isNotBlank(startTime)) {
                invoice.setStartDate(DateUtil.convertLong(startTime + " 00:00:00", ""));
            }
            if (StringUtils.isNotBlank(endTime)) {
                invoice.setEndDate(DateUtil.convertLong(endTime + " 23:59:59", ""));
            }

            Page page = getPageTag(request, pageNo, pageSize);
            // invoice.setType(SysOptionConstant.ID_503);//采购发票
            invoice.setValidStatus(0);// 待审核
            invoice.setCompanyId(user.getCompanyId());
            invoice.setTag(2);// 收票
            Map<String, Object> map = invoiceService.getBuyInvoiceAuditList(invoice,page);// 获取采购发票待审核列表
            if (map != null) {
                mv.addObject("invoiceList", map.get("invoiceList"));

                mv.addObject("invoice", (Invoice) map.get("invoice"));
//				mv.addObject("userList", (List<User>) map.get("userList"));
                mv.addObject("page", map.get("page"));
            }
            mv.addObject("startTime", startTime);
            mv.addObject("endTime", endTime);
            mv.setViewName("finance/invoice/buy_invoice_audit_list");
            // 发票类型
            List<SysOptionDefinition> invoiceTypeList = getSysOptionDefinitionList(SysOptionConstant.ID_428);
            mv.addObject("invoiceTypeList", invoiceTypeList);
        } catch (Exception e) {
            logger.error("buyInvoiceAuditList:", e);
        }
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 根据发票号查询待审核发票的详细信息
     *
     * @param request
     * @param invoice
     * @return
     * @Note <b>Author:</b> duke <br>
     *       <b>Date:</b> 2017年11月9日 下午4:17:44
     */
    @FormToken(save = true)
    @ResponseBody
    @RequestMapping(value = "buyInvoiceAudit")
    public ModelAndView buyInvoiceAudit(HttpServletRequest request, Invoice invoice) {
        ModelAndView mv = new ModelAndView();
        try {
            User user_session = (User) request.getSession().getAttribute(Consts.SESSION_USER);
            if (null != user_session) {
                mv.addObject("positType", user_session.getPositType());
            }
            if (null == invoice.getType() || invoice.getType().equals(0)) {
                invoice.setType(SysOptionConstant.ID_503);// 采购发票
            }
            invoice.setValidStatus(0);// 待审核
            try {
                if (invoice != null && StringUtils.isNotBlank(invoice.getInvoiceNo())) {
                    Map<String, Object> map = invoiceService.getInvoiceAuditListByInvoiceNo(invoice);
                    if (map != null && map.size() > 0) {
                        invoice = (Invoice) map.get("invoice");

                        User user = userService.getUserById(invoice.getCreator());
                        if(user!=null) {
                            invoice.setCreatorName(user.getUsername());
                        }
                        mv.addObject("invoice", invoice);
                        mv.addObject("invoiceGoodsList", map.get("invoiceGoodsList"));
                        mv.addObject("attachmentList", map.get("attachmentList"));

                        // 日志
                        mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(invoice)));
                    }


                    //新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
                    List<InvoiceDetail> invoiceDetailsList = (List<InvoiceDetail>)map.get("invoiceGoodsList");
                    if(!CollectionUtils.isEmpty(invoiceDetailsList)){
                        List<Integer> skuIds = new ArrayList<>();
                        invoiceDetailsList.stream().forEach(invoiceDetail -> {
                            skuIds.add(invoiceDetail.getGoodsId());
                        });
                        List<Map<String,Object>> skuTipsMap = this.vGoodsService.skuTipList(skuIds);
                        Map<String,Map<String,Object>> newSkuInfosMap = skuTipsMap.stream().collect(Collectors.toMap(key->key.get("SKU_NO").toString(), v -> v, (k, v) -> v));
                        mv.addObject("newSkuInfosMap", newSkuInfosMap);
                    }
                    //新商品流切换 VDERP-1970 add by brianna 2020/3/16 end

                }
            } catch (Exception e) {
                logger.error("buyInvoiceAudit 1:", e);
            }
            mv.setViewName("finance/invoice/buy_invoice_audit");
            // 发票类型
            List<SysOptionDefinition> invoiceTypeList = getSysOptionDefinitionList(SysOptionConstant.ID_428);
            mv.addObject("invoiceTypeList", invoiceTypeList);
        } catch (Exception e) {
            logger.error("buyInvoiceAudit 2:", e);
        }
        return mv;
    }

    /**
     * <b>Description:</b> 收票审核不通过原因填写
     * @param request
     * @param invoice
     * @return ModelAndView
     * @Note
     * <b>Author：</b> duke.li
     * <b>Date:</b> 2019年2月14日 下午2:17:01
     */
    @FormToken(save = true)
    @ResponseBody
    @RequestMapping(value = "auditInvoiceReason")
    public ModelAndView auditInvoiceReason(HttpServletRequest request, Invoice invoice) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("invoice", invoice);
        mv.setViewName("finance/invoice/invoice_audit_reason");
        return mv;
    }
    /**
     * <b>Description:</b><br>
     * 保存收票记录审核结果
     *
     * @param request
     * @param invoice
     * @return
     * @Note <b>Author:</b> duke <br>
     *       <b>Date:</b> 2017年8月24日 下午3:04:47
     */
    @FormToken(remove = true)
    @ResponseBody
    @RequestMapping(value = "saveInvoiceAudit")
    @SystemControllerLog(operationType = "edit", desc = "保存收票记录审核结果")
    public ResultInfo<?> saveInvoiceAudit(HttpServletRequest request, String beforeParams, Invoice invoice) {
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        if (null != user) {
            invoice.setUpdater(user.getUserId());
            invoice.setModTime(DateUtil.gainNowDate());

            invoice.setValidUserId(user.getUserId());
            invoice.setValidTime(DateUtil.gainNowDate());

            invoice.setValidTime(DateUtil.gainNowDate());
            invoice.setValidUserId(user.getUserId());
        }
        return invoiceService.saveInvoiceAudit(invoice);
    }

    /**
     * <b>Description:</b><br>
     * 查询收票记录列表
     *
     * @param request
     * @param invoice
     * @return
     * @Note <b>Author:</b> duke <br>
     *       <b>Date:</b> 2017年8月24日 下午4:19:28
     */
    @ResponseBody
    @RequestMapping(value = "getInvoiceListPage")
    public ModelAndView getInvoiceListPage(HttpServletRequest request, Invoice invoice,
                                           @RequestParam(required = false, value = "searchDateType") String searchDateType, // 效验是否新打开查询页
                                           @RequestParam(required = false, value = "startTime") String startTime,
                                           @RequestParam(required = false, value = "endTime") String endTime,
                                           @RequestParam(required = false, defaultValue = "1") Integer pageNo,
                                           @RequestParam(required = false) Integer pageSize) {
        ModelAndView mv = new ModelAndView();
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        Page page = getPageTag(request, pageNo, pageSize);

        if (StringUtils.isBlank(searchDateType)) {// 新打开
            startTime = DateUtil.getFirstDayOfMonth(0);// 当月第一天
            endTime = DateUtil.getNowDate("yyyy-MM-dd");// 当前日期
        } else {
            mv.addObject("de_startTime", DateUtil.getFirstDayOfMonth(0));
            mv.addObject("de_endTime", DateUtil.getNowDate("yyyy-MM-dd"));
        }
        mv.addObject("searchDateType", searchDateType);
        if (StringUtils.isNotBlank(startTime)) {
            invoice.setStartDate(DateUtil.convertLong(startTime + " 00:00:00", ""));
        }
        if (StringUtils.isNotBlank(endTime)) {
            invoice.setEndDate(DateUtil.convertLong(endTime + " 23:59:59", ""));
        }
        if (invoice.getDateType() == null) {
            invoice.setDateType(1);// 默认申请日期
        }
        mv.addObject("startTime", startTime);
        mv.addObject("endTime", endTime);
        invoice.setType(SysOptionConstant.ID_503);
//		invoice.setValidStatus(1);// 审核通过
        invoice.setCompanyId(user.getCompanyId());
        invoice.setTag(2);// 收票
        Map<String, Object> map = invoiceService.getInvoiceListPage(invoice, page);
        if (map != null && map.size() > 0) {
            mv.addObject("auditUserList", map.get("auditUserList"));
            mv.addObject("inputUserList", map.get("inputUserList"));

            mv.addObject("invoiceList", map.get("invoiceList"));
            mv.addObject("page", (Page) map.get("page"));
            mv.addObject("invoice", (Invoice) map.get("invoice"));
        } else {
            mv.addObject("invoice", invoice);
        }

        // 发票类型
        List<SysOptionDefinition> invoiceTypeList = getSysOptionDefinitionList(SysOptionConstant.ID_428);
        mv.addObject("invoiceTypeList", invoiceTypeList);

        mv.setViewName("finance/invoice/list_buy_invoice");
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 批量认证
     *
     * @param request
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2018年5月28日 下午3:03:00
     */
    @ResponseBody
    @RequestMapping(value = "toBatchAuthenticationPage")
    public ModelAndView toBatchAuthenticationPage(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("finance/invoice/uplode_batch_authentication");
        return mav;
    }

    /**
     * <b>Description:</b><br>
     * 保存批量认证
     *
     * @param request
     * @param lwfile
     * @return
     * @throws IOException
     * @throws FileNotFoundException
     * @throws InvalidFormatException
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2018年5月28日 下午3:13:45
     */
    @ResponseBody
    @RequestMapping("saveBatchAuthentication")
    public ResultInfo<?> saveBatchAuthentication(HttpServletRequest request,
                                                 @RequestParam("lwfile") MultipartFile lwfile) throws IOException {
        ResultInfo<?> resultInfo = new ResultInfo<>();

        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        // 临时文件存放地址
        String path = request.getSession().getServletContext().getRealPath("/upload/finance");
        FileInfo fileInfo = ftpUtilService.fileUploadServe(path, lwfile);
        if (fileInfo.getCode() == 0) {
            InputStream inputStream = null;
            Workbook workbook = null;
            try {
                List<Invoice> list = new ArrayList<>();
                Set<String> invoiceSet = new HashSet<>();
                // 获取excel路径
                inputStream = new FileInputStream(new File(fileInfo.getFilePath()));
                workbook = WorkbookFactory.create(inputStream);
                // 获取第一面sheet
                Sheet sheet = workbook.getSheetAt(0);
                // 起始行
                int startRowNum = sheet.getFirstRowNum() + 1;
                int endRowNum = sheet.getLastRowNum();// 结束行
                Invoice invoice = null;
                for (int rowNum = startRowNum; rowNum <= endRowNum; rowNum++) {// 循环行数
                    invoice = new Invoice();
                    invoice.setCompanyId(user.getCompanyId());
                    Row row = sheet.getRow(rowNum);
                    int startCellNum = row.getFirstCellNum();// 起始列
                    // int endCellNum = row.getLastCellNum() - 1;// 结束列
                    // 获取excel的值

                    for (int cellNum = startCellNum; cellNum <= 2; cellNum++) {// 循环列数（下表从0开始）--注意---此处的6是根据表格的列数来确定的，表格列数修改此处要跟着修改
                        Cell cell = row.getCell(cellNum);

                        if (cellNum == 0) {// 发票号
                            // 若excel中无内容，而且没有空格，cell为空--默认3，空白
                            if (cell == null) {
                                resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列发票号不允许为空！");
                                return resultInfo;
                            } else {
                                cell.setCellType(Cell.CELL_TYPE_STRING);
                                if (!invoiceSet.isEmpty() && invoiceSet.contains(cell.getStringCellValue().trim())) {
                                    resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列发票号已存在！");
                                    return resultInfo;
                                } else {
                                    invoiceSet.add(cell.getStringCellValue().trim());
                                    invoice.setInvoiceNo(cell.getStringCellValue().trim());
                                }
                            }
                        }
                        if (cellNum == 1) {// 认证时间
                            if (cell == null) {
                                resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列认证时间不能为空！");
                                return resultInfo;
                            } else if (cell != null && cell.getDateCellValue() != null) {
                                // Date date = cell.getDateCellValue();
                                try {
                                    // String authTime =
                                    // cell.getStringCellValue().trim();
                                    long authTimes = cell.getDateCellValue().getTime();
                                    invoice.setAuthTime(authTimes);
                                } catch (Exception e) {
                                    logger.error(Contant.ERROR_MSG, e);
                                    resultInfo
                                            .setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列认证时间格式错误！");
                                    return resultInfo;
                                }
                            }
                        }
                    }
                    if (ObjectUtils.notEmpty(invoice.getInvoiceNo())) {
                        list.add(invoice);
                    }
                }
                // 保存更新
                resultInfo = invoiceService.saveBatchAuthentication(list);
            } catch (Exception e) {
                logger.error("saveBatchAuthentication:", e);
            } finally {
                inputStream.close();
            }
        } else {
            resultInfo.setMessage("上传失败");
        }
        return resultInfo;
    }

    /**
     * <b>Description:</b><br>
     * 开票申请
     *
     * @param request
     * @return
     * @Note <b>Author:</b> duke <br>
     *       <b>Date:</b> 2017年8月28日 上午11:20:24
     */
    @FormToken(save = true)
    @ResponseBody
    @RequestMapping(value = "openInvoiceApply")
    public ModelAndView openInvoiceApply(HttpServletRequest request, InvoiceApply invoiceApply,
                                         @RequestParam(required = false) String comment) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("invoiceApply", invoiceApply);
        try {
            if (comment != null) {
                comment = java.net.URLDecoder.decode(comment, "UTF-8");
            }
            Saleorder order = new Saleorder();
            order.setSaleorderId(invoiceApply.getRelatedId());
            order = saleorderService.getBaseSaleorderInfoNew(order);
            invoiceApply.setInvoiceType(order.getInvoiceType());
            invoiceApply.setIsAuto(order.getInvoiceMethod());
            mv.addObject("comment", comment);
        } catch (Exception e) {
            logger.error("openInvoiceApply:", e);
            return fail(mv);
        }
        mv.setViewName("finance/invoice/sale_invoice_apply");
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 提前开票申请
     *
     * @param request
     * @return
     * @Note <b>Author:</b> duke <br>
     *       <b>Date:</b> 2017年8月28日 上午11:20:24
     */
    @FormToken(save = true)
    @ResponseBody
    @RequestMapping(value = "advanceOpenInvoiceApply")
    public ModelAndView advanceOpenInvoiceApply(HttpServletRequest request, InvoiceApply invoiceApply,
                                                @RequestParam(required = false) String comment) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("invoiceApply", invoiceApply);
        try {
            if (comment != null) {
                comment = java.net.URLDecoder.decode(comment, "UTF-8");
            }
            Saleorder order = new Saleorder();
            order.setSaleorderId(invoiceApply.getRelatedId());
            order = saleorderService.getBaseSaleorderInfoNew(order);
            invoiceApply.setInvoiceType(order.getInvoiceType());
            invoiceApply.setIsAuto(order.getInvoiceMethod());
            mv.addObject("comment", comment);
        } catch (Exception e) {
            logger.error("advanceOpenInvoiceApply:", e);
            return fail(mv);
        }
        mv.setViewName("finance/invoice/advance_sale_invoice_apply");
        return mv;
    }

    /**
     * @description: VDERP-1325 分批开票 选择/查看开票商品.
     * @version: 1.0.
     * @date: 2019/10/29 13:58.
     * @author: Tomcat.Hui.
     */
    @FormToken(save = true)
    @ResponseBody
    @RequestMapping(value = "selectInvoiceItems")
    @SystemControllerLog(operationType = "add", desc = "选择/查看开票商品")
    public ModelAndView selectInvoiceItems(HttpServletRequest request, InvoiceApply invoiceApply, Boolean editFlag){
        ModelAndView mv = new ModelAndView();
        mv.addObject("invoiceApply", invoiceApply);

        try {
            Saleorder order = new Saleorder();
            order.setSaleorderId(invoiceApply.getRelatedId());
            order.setOptType("orderDetail");
            order = saleorderService.getBaseSaleorderInfo(order);
            Saleorder sale = new Saleorder();
            sale.setSaleorderId(order.getSaleorderId());
            List<SaleorderGoods> goodsList =  saleorderService.getSaleorderGoodsById(sale);

            order.setGoodsList(goodsList);
            invoiceApply.setInvoiceType(order.getInvoiceType());
            invoiceApply.setIsAuto(order.getInvoiceMethod());

            // 获取交易信息（订单实际金额，客户已付款金额）
            Map<String, BigDecimal> saleorderDataInfo = saleorderService.getSaleorderDataInfo(invoiceApply.getRelatedId());
            mv.addObject("saleorderDataInfo", saleorderDataInfo);

            // 发票类型字典列表
            List<SysOptionDefinition> invoiceTypes = getSysOptionDefinitionList(428);
            mv.addObject("invoiceTypes", invoiceTypes);
            mv.addObject("saleOrder",order);

            //新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
            if(!CollectionUtils.isEmpty(order.getGoodsList())){
                List<Integer> skuIds = new ArrayList<>();
                order.getGoodsList().stream().forEach(saleGood -> {
                    skuIds.add(saleGood.getGoodsId());
                });
                List<Map<String,Object>> skuTipsMap = this.vGoodsService.skuTipList(skuIds);
                Map<String,Map<String,Object>> newSkuInfosMap = skuTipsMap.stream().collect(Collectors.toMap(key->key.get("SKU_NO").toString(), v -> v, (k, v) -> v));
                mv.addObject("newSkuInfosMap", newSkuInfosMap);
            }
            //新商品流切换 VDERP-1970 add by brianna 2020/3/16 end

            //已开票数量 申请中数量
            Map<Integer , Map<String, Object>> taxNumsMap = invoiceService.getInvoiceNums(order);
            if (null != taxNumsMap) {
                goodsList.stream().forEach(g -> {
                    g.setAppliedNum(new BigDecimal(taxNumsMap.get(g.getSaleorderGoodsId()).get("APPLY_NUM").toString()));
                    g.setInvoicedNum(new BigDecimal(taxNumsMap.get(g.getSaleorderGoodsId()).get("INVOICE_NUM").toString()));
                    g.setInvoicedAmount(new BigDecimal(taxNumsMap.get(g.getSaleorderGoodsId()).get("INVOICE_AMOUNT").toString()));
                    g.setAppliedAmount(new BigDecimal(taxNumsMap.get(g.getSaleorderGoodsId()).get("APPLY_AMOUNT").toString()));
                });
            }

            /** 可开票数量计算
             * 某订单某sku“满足开票条件”的数量取值
             * （1）若不满足“该订单全部收款”，则值为0；若满足，则判断下一个条件
             * （2）若不满足“对公收款的金额（且交易名称需要跟合同名称一致）≥订单实际金额”条件，则值为0；
             * 若满足，则值取“该订单该sku的发货数量、该订单该sku的收货数量”这两个值中的最小值
             * getMeetInvoiceConditions = 1 为满足开票条件*/
            if (order.getPaymentStatus().equals(2) && null != order.getMeetInvoiceConditions() && (order.getMeetInvoiceConditions().equals(1))) {
                final List<ExpressDetail> expresseList = expressService.getSEGoodsNum(goodsList.stream().map(SaleorderGoods::getSaleorderGoodsId).collect(Collectors.toList()));
                if (CollectionUtils.isNotEmpty(expresseList)) {
                    goodsList.stream().forEach(
                            g -> expresseList.stream().filter(e -> e.getSaleOrderGoodsId().equals(g.getSaleorderGoodsId())).findFirst()
                                    .map(
                                            express -> {
                                                //返回收货数量和发货数量的最小值
                                                g.setCanInvoicedNum(express.getArriveNum() <= express.getSendNum() ? express.getArriveNum() : express.getSendNum());
                                                return g;
                                            })
                    );
                }
            } else {
                goodsList.stream().forEach(g -> g.setCanInvoicedNum(0));
            }

            if (!editFlag) {
                //查看
                InvoiceApply applyRecord = invoiceService.getApplyDetailById(invoiceApply);
                mv.addObject("applyRecord",applyRecord);
            }

        }catch (Exception e){
            logger.error("选择/查看开票申请商品接口出现异常:",e);
            return fail(mv);
        }
        if (!editFlag) {
            mv.setViewName("/finance/invoice/view_invoice_apply_items");
        } else {
            mv.setViewName("/finance/invoice/select_invoice_items");
        }
        return mv;
    }
    /*新商品流*/
    @FormToken(save = true)
    @ResponseBody
    @RequestMapping(value = "selectInvoiceItemsNew")
    @SystemControllerLog(operationType = "add", desc = "选择/查看开票商品")
    public ModelAndView selectInvoiceItemsNew(HttpServletRequest request, InvoiceApply invoiceApply, Boolean editFlag){
        ModelAndView mv = new ModelAndView();
        mv.addObject("invoiceApply", invoiceApply);

        try {
            Saleorder order = new Saleorder();
            order.setSaleorderId(invoiceApply.getRelatedId());
            order.setOptType("orderDetail");
            order = saleorderService.getBaseSaleorderInfo(order);
            Saleorder sale = new Saleorder();
            sale.setSaleorderId(order.getSaleorderId());
            List<SaleorderGoods> goodsList =  saleorderService.getSaleorderGoodsByIdOther(sale);

            order.setGoodsList(goodsList);
            invoiceApply.setInvoiceType(order.getInvoiceType());
            invoiceApply.setIsAuto(order.getInvoiceMethod());

            // 获取交易信息（订单实际金额，客户已付款金额）
            Map<String, BigDecimal> saleorderDataInfo = saleorderService.getSaleorderDataInfo(invoiceApply.getRelatedId());
            mv.addObject("saleorderDataInfo", saleorderDataInfo);

            // 发票类型字典列表
            List<SysOptionDefinition> invoiceTypes = getSysOptionDefinitionList(428);
            mv.addObject("invoiceTypes", invoiceTypes);
            mv.addObject("saleOrder",order);

            //已开票数量 申请中数量
            Map<Integer , Map<String, Object>> taxNumsMap = invoiceService.getInvoiceNums(order);
            if (null != taxNumsMap) {
                goodsList.stream().forEach(g -> {
                    g.setAppliedNum(new BigDecimal(taxNumsMap.get(g.getSaleorderGoodsId()).get("APPLY_NUM").toString()));
                    g.setInvoicedNum(new BigDecimal(taxNumsMap.get(g.getSaleorderGoodsId()).get("INVOICE_NUM").toString()));
                    g.setInvoicedAmount(new BigDecimal(taxNumsMap.get(g.getSaleorderGoodsId()).get("INVOICE_AMOUNT").toString()));
                    g.setAppliedAmount(new BigDecimal(taxNumsMap.get(g.getSaleorderGoodsId()).get("APPLY_AMOUNT").toString()));
                });
            }

            /** 可开票数量计算
             * 某订单某sku“满足开票条件”的数量取值
             * （1）若不满足“该订单全部收款”，则值为0；若满足，则判断下一个条件
             * （2）若不满足“对公收款的金额（且交易名称需要跟合同名称一致）≥订单实际金额”条件，则值为0；
             * 若满足，则值取“该订单该sku的发货数量、该订单该sku的收货数量”这两个值中的最小值
             * getMeetInvoiceConditions = 1 为满足开票条件*/
            if (order.getPaymentStatus().equals(2) && null != order.getMeetInvoiceConditions() && (order.getMeetInvoiceConditions().equals(1))) {
                final List<ExpressDetail> expresseList = expressService.getSEGoodsNum(goodsList.stream().map(SaleorderGoods::getSaleorderGoodsId).collect(Collectors.toList()));
                if (CollectionUtils.isNotEmpty(expresseList)) {
                    goodsList.stream().forEach(
                            g -> expresseList.stream().filter(e -> e.getSaleOrderGoodsId().equals(g.getSaleorderGoodsId())).findFirst()
                                    .map(
                                            express -> {
                                                //返回收货数量和发货数量的最小值
                                                g.setCanInvoicedNum(express.getArriveNum() <= express.getSendNum() ? express.getArriveNum() : express.getSendNum());
                                                return g;
                                            })
                    );
                }
            } else {
                goodsList.stream().forEach(g -> g.setCanInvoicedNum(0));
            }

            if (!editFlag) {
                //查看
                InvoiceApply applyRecord = invoiceService.getApplyDetailById(invoiceApply);
                mv.addObject("applyRecord",applyRecord);
            }

        }catch (Exception e){
            logger.error("选择/查看开票申请商品接口出现异常:",e);
            return fail(mv);
        }
        if (!editFlag) {
            mv.setViewName("/finance/invoice/view_invoice_apply_items");
        } else {
            mv.setViewName("/finance/invoice/select_invoice_items_new");
        }
        return mv;
    }

    /** @description: viewInvoicedItems.
     * @notes: VDERP-1325 分批开票 查看已开发票详情信息.
     * @author: Tomcat.Hui.
     * @date: 2019/11/21 9:14.
     * @param request
     * @param invoice
     * @return: org.springframework.web.servlet.ModelAndView.
     * @throws: .
     */
    @ResponseBody
    @RequestMapping(value = "viewInvoicedItems")
    @SystemControllerLog(operationType = "add", desc = "查看已开票信息")
    public ModelAndView viewInvoicedItems(HttpServletRequest request,Invoice invoice){
        ModelAndView mv = new ModelAndView();
        Invoice result = null;
        Saleorder order = new Saleorder();
        try{
            result = invoiceService.getInvoiceDetailById(invoice);

            //查询商品信息
            order.setSaleorderId(result.getRelatedId());
            order.setOptType("orderDetail");
            order = saleorderService.getBaseSaleorderInfo(order);
            Saleorder sale = new Saleorder();
            sale.setSaleorderId(result.getRelatedId());
            List<SaleorderGoods> goodsList =  saleorderService.getSaleorderGoodsById(sale);
            order.setGoodsList(goodsList);

        }catch (Exception e) {
            logger.error("查看开票商品接口出现异常:",e);
            return fail(mv);
        }
        mv.addObject("invoice",result);
        mv.addObject("saleOrder",order);
        mv.setViewName("/finance/invoice/view_invoiced_items");
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 保存开票申请（包括提前开票）
     *
     * @param request
     * @param invoiceApply
     * @return
     * @Note <b>Author:</b> duke <br>
     *       <b>Date:</b> 2017年8月28日 下午2:05:53
     */
    @FormToken(remove = true)
    @ResponseBody
    @RequestMapping(value = "saveOpenInvoceApply")
    @SystemControllerLog(operationType = "add", desc = "保存开票申请（包括提前开票）")
    public ResultInfo<?> saveOpenInvoceApply(HttpServletRequest request, InvoiceApply invoiceApply ,@RequestParam(required = false) String detailString) {
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        List<InvoiceApplyDetail> details = Lists.newArrayList();
        if (user != null) {
            invoiceApply.setCreator(user.getUserId());
            invoiceApply.setAddTime(DateUtil.gainNowDate());
            invoiceApply.setCompanyId(user.getCompanyId());
            invoiceApply.setUpdater(user.getUserId());
            invoiceApply.setModTime(DateUtil.gainNowDate());
            // if (user.getCompanyId() != 1) {
            invoiceApply.setYyValidStatus(1);// 默认无需运营审核--2018年11月12日需求
            // }
        }
        if (StringUtil.isNotBlank(detailString)) {
            details = JSON.parseArray(detailString,InvoiceApplyDetail.class);
            invoiceApply.setInvoiceApplyDetails(details);
        }

        // add by Tomcat.Hui 2020/1/16 19:30 .Desc: VDERP-1874 开票中和已开票数量、金额的计算规则变更. start


        try {
            //根据已占用金额限制申请
            Saleorder saleorder = new Saleorder();
            saleorder.setSaleorderId(invoiceApply.getRelatedId());
            Saleorder saleorderInfo = saleorderService.getBaseSaleorderInfo(saleorder);
            Map<Integer,BigDecimal> invoiceOccupiedAmount = invoiceService.getInvoiceOccupiedAmount(saleorderInfo);
            // 获取交易信息（订单实际金额，客户已付款金额）
//            Map<String, BigDecimal> saleorderDataInfo = saleorderService.getSaleorderDataInfo(invoiceApply.getRelatedId());
            List<SaleorderGoods> saleorderGoodsList = saleorderService.getSaleorderGoodsById(saleorder);

            if (null != saleorderGoodsList && saleorderGoodsList.size() > 0) {
                for (SaleorderGoods g : saleorderGoodsList) {
                    BigDecimal goodsAmount = BigDecimal.ZERO;

                    if (saleorderInfo.getOrderType().equals(5)) {
                        goodsAmount = g.getMaxSkuRefundAmount().subtract(g.getAfterReturnAmount());
                    } else {
                        Integer goodsNum = g.getNum() - g.getAfterReturnNum();
                        goodsAmount = g.getPrice().multiply(new BigDecimal(goodsNum));
                    }

                    Optional<InvoiceApplyDetail> optional = invoiceApply.getInvoiceApplyDetails().stream().filter(a -> a.getDetailgoodsId().equals(g.getSaleorderGoodsId())).findFirst();
                    BigDecimal applyAmount = optional.isPresent() ? optional.get().getTotalAmount() : BigDecimal.ZERO;
                    BigDecimal alreadyAmount = invoiceOccupiedAmount.containsKey(g.getSaleorderGoodsId()) ? invoiceOccupiedAmount.get(g.getSaleorderGoodsId()) : BigDecimal.ZERO;
                    log.info("订单ID：" + invoiceApply.getRelatedId() + " 订单商品: " + g.getSku() + " 实际金额：" + goodsAmount + " 已开票金额：" + alreadyAmount + " 本次申请金额：" + applyAmount);

                    if (applyAmount.add(alreadyAmount).compareTo(goodsAmount) > 0) {
                        return new ResultInfo(-1, "SKU: " + g.getSku() + " 实际开票金额大于等于实际金额");
                    }
                }
            } else {
                return new ResultInfo<>(-1,"未查询到订单商品");
            }

            // add by Tomcat.Hui 2019/12/3 20:19 .Desc: VDERP-1325 分批开票 增加开票方式为手动申请. start
            invoiceApply.setApplyMethod(0);
            // add by Tomcat.Hui 2019/12/3 20:19 .Desc: VDERP-1325 分批开票 增加开票方式为手动申请. end

            ResultInfo<?> res = invoiceService.saveOpenInvoceApply(invoiceApply);
            if (user.getCompanyId() == 1) {
                return res;
            } else {
                if (res.getCode() == 0) {

                    InvoiceApply invoiceApplyInfo = invoiceService.getInvoiceApplyByRelatedId(invoiceApply.getRelatedId(),
                            SysOptionConstant.ID_505, invoiceApply.getCompanyId());
                    if (saleorderInfo != null) {
                        invoiceApplyInfo.setSaleorderNo(saleorderInfo.getSaleorderNo());
                    }
                    try {
                        Map<String, Object> variableMap = new HashMap<String, Object>();
                        // 开始生成流程(如果没有taskId表示新流程需要生成)
                        variableMap.put("invoiceApply", invoiceApplyInfo);
                        variableMap.put("currentAssinee", user.getUsername());
                        variableMap.put("processDefinitionKey", "invoiceVerify");
                        variableMap.put("businessKey", "invoiceVerify_" + invoiceApplyInfo.getInvoiceApplyId());
                        variableMap.put("relateTableKey", invoiceApplyInfo.getInvoiceApplyId());
                        variableMap.put("relateTable", "T_INVOICE_APPLY");

                        actionProcdefService.createProcessInstance(request, "invoiceVerify",
                                "invoiceVerify_" + invoiceApplyInfo.getInvoiceApplyId(), variableMap);
                        // 默认申请人通过
                        // 根据BusinessKey获取生成的审核实例
                        Map<String, Object> historicInfo = actionProcdefService.getHistoric(processEngine,
                                "invoiceVerify_" + invoiceApplyInfo.getInvoiceApplyId());
                        if (historicInfo.get("endStatus") != "审核完成") {
                            Task taskInfo = (Task) historicInfo.get("taskInfo");
                            String taskId = taskInfo.getId();
                            Authentication.setAuthenticatedUserId(user.getUsername());
                            Map<String, Object> variables = new HashMap<String, Object>();
                            // 设置审核完成监听器回写参数
                            variables.put("tableName", "T_INVOICE_APPLY");
                            variables.put("id", "INVOICE_APPLY_ID");
                            variables.put("idValue", invoiceApplyInfo.getInvoiceApplyId());
                            variables.put("key", "VALID_STATUS");
                            variables.put("value", 1);
                            // 回写数据的表在db中
                            variables.put("db", 2);
                            // 默认审批通过
                            ResultInfo<?> complementStatus = actionProcdefService.complementTask(request, taskId, "",
                                    user.getUsername(), variables);
                            // 如果未结束添加审核对应主表的审核状态
                            if (!complementStatus.getData().equals("endEvent")) {
                                verifiesRecordService.saveVerifiesInfo(taskId, 0);
                            }
                        }
                        return res;
                    } catch (Exception e) {
                        logger.error("saveOpenInvoceApply:", e);
                        return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
                    }
                } else {
                    return res;
                }
            }

        }catch (Exception e){
            logger.error("生成订单开票申请出现异常: {}",e);
            return new ResultInfo<>(-1,"生成订单开票申请出现异常");
        }
        // add by Tomcat.Hui 2020/1/16 19:30 .Desc: VDERP-1874 开票中和已开票数量、金额的计算规则变更. end

    }

    /**
     * <b>Description:</b><br>
     * 运营开票申请审核列表
     *
     * @param request
     * @param invoiceApply
     * @return
     * @Note <b>Author:</b> duke <br>
     *       <b>Date:</b> 2018年3月29日 下午2:53:03
     */
    @ResponseBody
    @RequestMapping(value = "getSaleYyInvoiceApplyListPage")
    public ModelAndView getSaleYyInvoiceApplyListPage(HttpServletRequest request, InvoiceApply invoiceApply,
                                                      @RequestParam(required = false, defaultValue = "1") Integer pageNo,
                                                      @RequestParam(required = false) Integer pageSize) {
        ModelAndView mv = new ModelAndView();
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        invoiceApply.setCompanyId(user.getCompanyId());
        // 获取销售部门
        List<Organization> searchOrgList = orgService.getSalesOrgList(SysOptionConstant.ID_310, user.getCompanyId());
        mv.addObject("searchOrgList", searchOrgList);

        // 获取当前销售用户下级职位用户
        List<Integer> positionType = new ArrayList<>();
        positionType.add(SysOptionConstant.ID_310);
        List<User> searchTraderUserList = userService.getMyUserList(user, positionType, false);// 归属销售
        mv.addObject("searchTraderUserList", searchTraderUserList);

        // 发票类型
        List<SysOptionDefinition> invoiceTypeList = getSysOptionDefinitionList(SysOptionConstant.ID_428);
        mv.addObject("invoiceTypeList", invoiceTypeList);

        Page page = getPageTag(request, pageNo, pageSize);
        if (invoiceApply.getTraderUserId() != null) {
            // 销售人员所属客户
            List<Integer> traderIdList = userService.getTraderIdListByUserId(invoiceApply.getTraderUserId(), 1);// 1客户，2供应商
            if (traderIdList == null || traderIdList.isEmpty()) {// 销售人员无客户，默认不出数据
                traderIdList.add(-1);
            }
            invoiceApply.setTraderIdList(traderIdList);
        }
        if (invoiceApply.getValidStatus() == null) {
            invoiceApply.setValidStatus(0);// 默认审核中
        }
        invoiceApply.setType(SysOptionConstant.ID_505);// 销售开票
        invoiceApply.setCompanyId(user.getCompanyId());
        invoiceApply.setOptType("yy");
        Map<String, Object> map = invoiceService.getSaleInvoiceApplyListPage(invoiceApply, page);

        mv.addObject("openInvoiceApplyList", (List<InvoiceApply>) map.get("openInvoiceApplyList"));
        mv.addObject("page", (Page) map.get("page"));

        mv.addObject("userList", (List<User>) map.get("userList"));
        // mv.addObject("orgList", (List<Organization>)map.get("orgList"));
        mv.addObject("traderUserList", (List<User>) map.get("traderUserList"));

        mv.addObject("invoiceApply", (InvoiceApply) map.get("invoiceApply"));
        mv.setViewName("finance/invoice/list_sale_yy_invoice_apply");
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 开票申请列表页
     *
     * @param request
     * @return
     * @Note <b>Author:</b> duke <br>
     *       <b>Date:</b> 2017年8月28日 上午10:53:37
     */
    @FormToken(save = true)
    @ResponseBody
    @RequestMapping(value = "getSaleInvoiceApplyListPage")
    public ModelAndView getSaleInvoiceApplyListPage(HttpServletRequest request, InvoiceApply invoiceApply,
                                                    @RequestParam(required = false, defaultValue = "1") Integer pageNo,
                                                    @RequestParam(required = false) Integer pageSize) {
        ModelAndView mv = new ModelAndView();
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        invoiceApply.setCompanyId(user.getCompanyId());
        if(invoiceApply.getIsSign() == null){
            invoiceApply.setIsSign(0);// 默认未标记
        }

        // 获取销售部门
        List<Organization> searchOrgList = orgService.getSalesOrgList(SysOptionConstant.ID_310, user.getCompanyId());
        mv.addObject("searchOrgList", searchOrgList);

        // 获取当前销售用户下级职位用户
        List<Integer> positionType = new ArrayList<>();
        positionType.add(SysOptionConstant.ID_310);
        List<User> searchTraderUserList = userService.getMyUserList(user, positionType, false);// 归属销售
        mv.addObject("searchTraderUserList", searchTraderUserList);

        // 发票类型
        List<SysOptionDefinition> invoiceTypeList = getSysOptionDefinitionList(SysOptionConstant.ID_428);
        mv.addObject("invoiceTypeList", invoiceTypeList);

        // 客户等级
        List<SysOptionDefinition> customerLevelList = getSysOptionDefinitionList(SysOptionConstant.ID_11);
        mv.addObject("customerLevelList", customerLevelList);

        Page page = getPageTag(request, pageNo, pageSize);
        if (invoiceApply.getTraderUserId() != null) {
            // 销售人员所属客户
            List<Integer> traderIdList = userService.getTraderIdListByUserId(invoiceApply.getTraderUserId(), 1);// 1客户，2供应商
            if (traderIdList == null || traderIdList.isEmpty()) {// 销售人员无客户，默认不出数据
                traderIdList.add(-1);
            }
            invoiceApply.setTraderIdList(traderIdList);
        }
        if (invoiceApply.getValidStatus() == null) {
            invoiceApply.setValidStatus(0);// 默认审核中
        }
        invoiceApply.setType(SysOptionConstant.ID_505);// 销售开票
        invoiceApply.setCompanyId(user.getCompanyId());
        Map<String, Object> map = invoiceService.getSaleInvoiceApplyListPage(invoiceApply, page);
        //条件查询所有数据
        mv.addObject("applyAmountList",map.get("applyAmountList"));

        mv.addObject("openInvoiceApplyList", map.get("openInvoiceApplyList"));
        mv.addObject("page", map.get("page"));

        mv.addObject("userList", map.get("userList"));
        mv.addObject("orgList", map.get("orgList"));
        mv.addObject("traderUserList", map.get("traderUserList"));

        mv.addObject("invoiceApply", map.get("invoiceApply"));
        //总金额订单
        mv.addObject("applyAmountMoney",map.get("applyAmountMoney"));
        mv.setViewName("finance/invoice/list_sale_invoice_apply");
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 分公司-开票申请待审核列表（审核流程）
     *
     * @param request
     * @param pageNo
     * @param pageSize
     * @return
     * @Note <b>Author:</b> duke <br>
     *       <b>Date:</b> 2018年5月29日 下午2:52:02
     */
    @ResponseBody
    @RequestMapping(value = "getInvoiceApplyVerifyListPage")
    public ModelAndView getInvoiceApplyVerifyListPage(HttpServletRequest request, InvoiceApply invoiceApply,
                                                      @RequestParam(required = false, defaultValue = "1") Integer pageNo,
                                                      @RequestParam(required = false) Integer pageSize) {
        ModelAndView mv = new ModelAndView();
        if (invoiceApply.getValidStatus() == null) {
            invoiceApply.setValidStatus(0);// 默认审核中
        }
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        invoiceApply.setCompanyId(user.getCompanyId());
        mv.addObject("user", user);

        // 获取销售部门
        List<Organization> searchOrgList = orgService.getSalesOrgList(SysOptionConstant.ID_310, user.getCompanyId());
        mv.addObject("searchOrgList", searchOrgList);

        // 获取当前销售用户下级职位用户
        List<Integer> positionType = new ArrayList<>();
        positionType.add(SysOptionConstant.ID_310);
        List<User> searchTraderUserList = userService.getMyUserList(user, positionType, false);// 归属销售
        mv.addObject("searchTraderUserList", searchTraderUserList);

        // 发票类型
        List<SysOptionDefinition> invoiceTypeList = getSysOptionDefinitionList(SysOptionConstant.ID_428);
        mv.addObject("invoiceTypeList", invoiceTypeList);

        Page page = getPageTag(request, pageNo, pageSize);
        if (invoiceApply.getTraderUserId() != null) {
            // 销售人员所属客户
            List<Integer> traderIdList = userService.getTraderIdListByUserId(invoiceApply.getTraderUserId(), 1);// 1客户，2供应商
            if (traderIdList == null || traderIdList.isEmpty()) {// 销售人员无客户，默认不出数据
                traderIdList.add(-1);
            }
            invoiceApply.setTraderIdList(traderIdList);
        }
        if (invoiceApply.getValidStatus() == null) {
            invoiceApply.setValidStatus(0);// 默认审核中
        }
        invoiceApply.setType(SysOptionConstant.ID_505);// 销售开票
        invoiceApply.setCompanyId(user.getCompanyId());
        Map<String, Object> map = invoiceService.getInvoiceApplyVerifyListPage(invoiceApply, page);

        mv.addObject("openInvoiceApplyList", (List<InvoiceApply>) map.get("openInvoiceApplyList"));
        mv.addObject("page", (Page) map.get("page"));

        mv.addObject("userList", (List<User>) map.get("userList"));
        mv.addObject("traderUserList", (List<User>) map.get("traderUserList"));

        mv.addObject("invoiceApply", invoiceApply);
        mv.setViewName("finance/invoice/list_sale_invoice_apply_verify");
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 运营审核开票申请状态
     *
     * @param request
     * @param invoiceApply
     * @return
     * @Note <b>Author:</b> duke <br>
     *       <b>Date:</b> 2018年3月29日 下午4:16:10
     */
    @ResponseBody
    @RequestMapping(value = "auditYyOpenInvoice")
    public ModelAndView auditYyOpenInvoice(HttpServletRequest request, InvoiceApply invoiceApply) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("invoiceApply", invoiceApply);
        mv.setViewName("finance/invoice/audit_yy_sale_invoice_reason");
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 开票审核原因初始化
     *
     * @param request
     * @param invoiceApply
     * @return
     * @Note <b>Author:</b> duke <br>
     *       <b>Date:</b> 2017年8月29日 下午2:10:04
     */
    @ResponseBody
    @RequestMapping(value = "auditOpenInvoice")
    public ModelAndView auditOpenInvoice(HttpServletRequest request, InvoiceApply invoiceApply) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("invoiceApply", invoiceApply);
        mv.setViewName("finance/invoice/audit_sale_invoice_reason");
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 保存开票申请审核结果
     *
     * @param request
     * @param invoiceApply
     * @return
     * @Note <b>Author:</b> duke <br>
     *       <b>Date:</b> 2017年8月29日 下午2:22:40
     */
    @ResponseBody
    @RequestMapping(value = "saveOpenInvoiceAudit")
    @SystemControllerLog(operationType = "edit", desc = "保存开票申请审核结果")
    public ResultInfo<?> saveOpenInvoiceAudit(HttpServletRequest request, InvoiceApply invoiceApply) {
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        if (user != null) {
            if (invoiceApply.getIsAdvance() != null && invoiceApply.getIsAdvance().intValue() == 1) {// 提前开票
                invoiceApply.setAdvanceValidTime(DateUtil.gainNowDate());
                invoiceApply.setAdvanceValidUserid(user.getUserId());
            } else {
                invoiceApply.setValidTime(DateUtil.gainNowDate());
                invoiceApply.setValidUserId(user.getUserId());
            }

            invoiceApply.setUpdater(user.getUserId());
            invoiceApply.setModTime(DateUtil.gainNowDate());
        }
        return invoiceService.saveOpenInvoiceAudit(invoiceApply);
    }

    /**
     * <b>Description:</b><br>
     * 保存运营审核开票申请
     *
     * @param request
     * @param invoiceApply
     * @return
     * @Note <b>Author:</b> duke <br>
     *       <b>Date:</b> 2018年3月29日 下午4:25:29
     */
    @ResponseBody
    @RequestMapping(value = "saveYyOpenInvoiceAudit")
    @SystemControllerLog(operationType = "edit", desc = "保存运营开票申请审核结果")
    public ResultInfo<?> saveYyOpenInvoiceAudit(HttpServletRequest request, InvoiceApply invoiceApply) {
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        if (user != null) {
            invoiceApply.setYyValidTime(DateUtil.gainNowDate());
            invoiceApply.setYyValidUserid(user.getUserId());

            invoiceApply.setUpdater(user.getUserId());
            invoiceApply.setModTime(DateUtil.gainNowDate());
        }
        return invoiceService.saveOpenInvoiceAudit(invoiceApply);
    }

    /**
     * <b>Description:</b><br>
     * 提前开票申请记录
     *
     * @param request
     * @param invoiceApply
     * @param pageNo
     * @param pageSize
     * @return
     * @Note <b>Author:</b> duke <br>
     *       <b>Date:</b> 2017年8月29日 下午4:18:56
     */
    @ResponseBody
    @RequestMapping(value = "getAdvanceInvoiceApplyListPage")
    public ModelAndView getAdvanceInvoiceApplyListPage(HttpServletRequest request, InvoiceApply invoiceApply,
                                                       @RequestParam(required = false, value = "searchDateType") String searchDateType, // 效验是否新打开查询页
                                                       @RequestParam(required = false, defaultValue = "1") Integer pageNo,
                                                       @RequestParam(required = false) Integer pageSize,
                                                       @RequestParam(required = false, value = "startTime") String startTime,
                                                       @RequestParam(required = false, value = "endTime") String endTime) {
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        ModelAndView mv = new ModelAndView();
        // 获取销售部门
        List<Organization> searchOrgList = orgService.getSalesOrgList(SysOptionConstant.ID_310, user.getCompanyId());
        mv.addObject("searchOrgList", searchOrgList);

        // 获取当前销售用户下级职位用户
        List<Integer> positionType = new ArrayList<>();
        positionType.add(SysOptionConstant.ID_310);
        List<User> searchTraderUserList = userService.getMyUserList(user, positionType, false);// 归属销售
        mv.addObject("searchTraderUserList", searchTraderUserList);

        // 发票类型
        List<SysOptionDefinition> invoiceTypeList = getSysOptionDefinitionList(SysOptionConstant.ID_428);
        mv.addObject("invoiceTypeList", invoiceTypeList);

        if (StringUtils.isBlank(searchDateType)) {// 新打开
            startTime = DateUtil.getDayOfMonth(-1);// 当前日期的前一个月的日期
            endTime = DateUtil.getNowDate("yyyy-MM-dd");// 当前日期
        } else {
            mv.addObject("de_startTime", DateUtil.getDayOfMonth(-1));
            mv.addObject("de_endTime", DateUtil.getNowDate("yyyy-MM-dd"));
        }
        mv.addObject("searchDateType", searchDateType);
        if (invoiceApply.getDateType() == null) {// 日期查询类型
            invoiceApply.setDateType(1);
        }
        if (StringUtils.isNotBlank(startTime)) {
            invoiceApply.setStartDate(DateUtil.convertLong(startTime + " 00:00:00", ""));
        }
        if (StringUtils.isNotBlank(endTime)) {
            invoiceApply.setEndDate(DateUtil.convertLong(endTime + " 23:59:59", ""));
        }
        mv.addObject("startTime", startTime);
        mv.addObject("endTime", endTime);

        Page page = getPageTag(request, pageNo, pageSize);
        if (invoiceApply.getTraderUserId() != null) {
            // 销售人员所属客户
            List<Integer> traderIdList = userService.getTraderIdListByUserId(invoiceApply.getTraderUserId(), 1);// 1客户，2供应商
            if (traderIdList != null && !traderIdList.isEmpty()) {// 销售人员无客户，默认不出数据
                invoiceApply.setTraderIdList(traderIdList);
            }
        }
        // 初始化默认选择审核中
        if (invoiceApply.getAdvanceValidStatus() == null) {
            invoiceApply.setAdvanceValidStatus(0);// 默认审核中
        } else if (invoiceApply.getAdvanceValidStatus() == -1) {
            invoiceApply.setAdvanceValidStatus(null);// 全部
        }
        invoiceApply.setType(SysOptionConstant.ID_505);// 销售开票
        invoiceApply.setCompanyId(user.getCompanyId());
        Map<String, Object> map = invoiceService.getAdvanceInvoiceApplyListPage(invoiceApply, page);
        List<InvoiceApply> openInvoiceApplyList = (List<InvoiceApply>) map.get("openInvoiceApplyList");
        mv.addObject("openInvoiceApplyList", openInvoiceApplyList);
        mv.addObject("page", (Page) map.get("page"));

        mv.addObject("userList", (List<User>) map.get("userList"));
        // mv.addObject("orgList", (List<Organization>)map.get("orgList"));
        mv.addObject("traderUserList", (List<User>) map.get("traderUserList"));
        // mv.addObject("totalMoney",
        // Integer.valueOf(map.get("totalMoney").toString()));

        mv.addObject("invoiceApply", (InvoiceApply) map.get("invoiceApply"));
        mv.setViewName("finance/invoice/list_advance_sale_invoice");
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 批量审核开票记录（提前开票）
     *
     * @param request
     * @param invoiceApply
     * @return
     * @Note <b>Author:</b> duke <br>
     *       <b>Date:</b> 2017年8月29日 下午5:41:33
     */
    @ResponseBody
    @RequestMapping(value = "saveOpenInvoiceAuditBatch")
    @SystemControllerLog(operationType = "edit", desc = "保存批量审核开票记录（提前开票）")
    public ResultInfo<?> saveOpenInvoiceAuditBatch(HttpServletRequest request, InvoiceApply invoiceApply,
                                                   @RequestParam(required = false, value = "invoiceApplyIdArr") String invoiceApplyIdArr) {
        try {
            User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
            if (user != null) {
                invoiceApply.setAdvanceValidTime(DateUtil.gainNowDate());

                invoiceApply.setUpdater(user.getUserId());
                invoiceApply.setModTime(DateUtil.gainNowDate());

                List<Integer> invoiceApplyIdList = JSON.parseArray(invoiceApplyIdArr, Integer.class);
                if (invoiceApplyIdList != null && invoiceApplyIdList.size() > 0) {
                    invoiceApply.setInvoiceApplyIdList(invoiceApplyIdList);
                    return invoiceService.saveOpenInvoiceAuditBatch(invoiceApply);
                }
            }
        } catch (Exception e) {
            logger.error("saveOpenInvoiceAuditBatch:", e);
            return new ResultInfo<>();
        }
        return new ResultInfo<>();
    }

    @Autowired
    @Qualifier("saleorderService")
    private SaleorderService saleorderService;

    @Autowired
    @Qualifier("traderCustomerService")
    private TraderCustomerService traderCustomerService;// 客户-交易者

    /**
     * <b>Description:</b><br>
     * 销售订单列表
     *
     * @param request
     * @param saleorder
     * @param session
     * @param pageNo
     * @param pageSize
     * @return
     * @Note <b>Author:</b> duke <br>
     *       <b>Date:</b> 2017年8月30日 下午2:28:15
     */
    @ResponseBody
    @RequestMapping(value = "getSaleorderListPage")
    public ModelAndView getSaleorderListPage(HttpServletRequest request, Saleorder saleorder, HttpSession session,
                                             @RequestParam(required = false, defaultValue = "1") Integer pageNo,
                                             @RequestParam(required = false) Integer pageSize) {
        ModelAndView mv = new ModelAndView();
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        saleorder.setOrderType(-2);
        Page page = getPageTag(request, pageNo, pageSize);

        if (EmptyUtils.isNotBlank(request.getParameter("searchBegintimeStr"))) {
            saleorder.setSearchBegintime(DateUtil.convertLong(request.getParameter("searchBegintimeStr") + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
        }
        if (EmptyUtils.isNotBlank(request.getParameter("searchEndtimeStr"))) {
            saleorder.setSearchEndtime(DateUtil.convertLong(request.getParameter("searchEndtimeStr") + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
        }
        // 发票类型
        List<SysOptionDefinition> invoiceTypeList = getSysOptionDefinitionList(SysOptionConstant.ID_428);

        // 获取销售部门
        List<Organization> orgList = orgService.getSalesOrgList(SysOptionConstant.ID_310, user.getCompanyId());
        mv.addObject("orgList", orgList);

        // 获取当前销售用户下级职位用户
        List<Integer> positionType = new ArrayList<>();
        positionType.add(SysOptionConstant.ID_310);
        List<User> userList = userService.getMyUserList(user, positionType, false);
        mv.addObject("userList", userList);// 操作人员

        List<User> saleUserList = new ArrayList<>();
        //获取该部门下是否有其他相应类型的部门
        if (null != saleorder.getOrgId() && saleorder.getOrgId() != -1) {
            List<User> userList2 =  userService.getOrgUserList(saleorder,user.getCompanyId());
            saleUserList.addAll(userList2);
            saleorder.setSaleUserList(saleUserList);
        }
        if (null != saleorder.getOptUserId() && saleorder.getOptUserId() != -1) {


            User saleUser = new User();
            saleUser.setUserId(saleorder.getOptUserId());
            saleUserList.clear();
            saleUserList.add(saleUser);
            saleorder.setSaleUserList(saleUserList);
        } else if (null != user.getPositType() && user.getPositType() == 310) {// 销售

            saleorder.setSaleUserList(userList);
        }

        saleorder.setIsSearchCount(1);// 查询记录总计及总金额
        saleorder.setCompanyId(user.getCompanyId());
        saleorder.setOptType("orderIndex");
        Map<String, Object> map = saleorderService.getSaleorderListPage(request, saleorder, page,0);

        if (map != null) {
            List<Saleorder> list = (List<Saleorder>) map.get("saleorderList");
            List<Integer> saleorderIdList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                // 归属销售
                user = orgService.getTraderUserAndOrgByTraderId(list.get(i).getTraderId(), 1);// 1客户，2供应商
                list.get(i).setOptUserName(user == null ? "" : user.getUsername());
                list.get(i).setSalesDeptName(user == null ? "" : user.getOrgName());
                // 销售部门
                // list.get(i).setSalesDeptName(orgService.getOrgNameById(list.get(i).getOrgId()));

                // 可否开票判断
                Saleorder invoiceSaleorder = new Saleorder();
                invoiceSaleorder.setSaleorderId(list.get(i).getSaleorderId());
                invoiceSaleorder.setOptType("orderIndex");
                invoiceSaleorder = saleorderService.getBaseSaleorderInfo(invoiceSaleorder);
                list.get(i).setIsOpenInvoice(invoiceSaleorder.getIsOpenInvoice());

                saleorderIdList.add(list.get(i).getSaleorderId());
                Map<String,BigDecimal> data=saleorderService.getSaleorderDataInfo(list.get(i).getSaleorderId());
                if(data!=null){
                    if(data.get("realAmount")!=null) {
                        list.get(i).setRealAmount(data.get("realAmount"));
                    }
                    if(data.get("paymentAmount")!=null){
                        list.get(i).setPaymentAmount(OrderMoneyUtil.getPaymentAmount(data));
                    }
                }

            }
            mv.addObject("saleorderList", list);
            // 根据销售订单ID查询账期付款总额-订单未还账期款---换成Jerry写的方法
            List<SaleorderData> capitalBillList = capitalBillService.getCapitalListBySaleorderId(saleorderIdList);

            // add by Tomcat.Hui 2019/9/5 10:44 .Desc: VDERP-1053 每笔订单做实际金额运算. start
            if (CollectionUtils.isNotEmpty(capitalBillList)){
                capitalBillList.stream().forEach(order -> {
                    try{
                        if (null != order.getSaleorderId()){
                            BigDecimal periodAmount = saleorderService.getPeriodAmount(order.getSaleorderId());
                            BigDecimal lackAccountPeriodAmount = saleorderService.getLackAccountPeriodAmount(order.getSaleorderId());
                            BigDecimal refundBalanceAmount = afterSalesOrderService.getRefundBalanceAmountBySaleorderId(order.getSaleorderId());
                            order.setPeriodAmount(periodAmount == null ? BigDecimal.ZERO : periodAmount);
                            order.setLackAccountPeriodAmount(lackAccountPeriodAmount == null ? BigDecimal.ZERO : lackAccountPeriodAmount);
                            order.setRefundBalanceAmount(refundBalanceAmount == null ? BigDecimal.ZERO : refundBalanceAmount);
                        }
                    }catch (Exception e){
                        log.info("订单实际金额运算出现异常: {}",e);
                    }
                });
            }
            // add by Tomcat.Hui 2019/9/5 10:44 .Desc: VDERP-1053 每笔订单做实际金额运算. end

            mv.addObject("capitalBillList", capitalBillList);
        }
        mv.addObject("page", (Page) map.get("page"));
        mv.addObject("invoiceTypeList", invoiceTypeList);
        mv.addObject("saleorder", (Saleorder) map.get("saleorder"));
        mv.setViewName("finance/invoice/index_sale_order");
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 销售订单查看
     *
     * @param request
     * @param saleorder
     * @return
     * @Note <b>Author:</b> duke <br>
     *       <b>Date:</b> 2017年8月30日 下午5:37:10
     */
    @ResponseBody
    @RequestMapping(value = "viewSaleorder")
    public ModelAndView viewSaleorder(HttpServletRequest request, Saleorder saleorder) {
        User curr_user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        ModelAndView mv = new ModelAndView();
        mv.addObject("curr_user", curr_user);
        Integer saleorderId = saleorder.getSaleorderId();
        // 获取订单基本信息
        saleorder = saleorderService.getBaseSaleorderInfo(saleorder);
        try {
            // 根据客户ID查询客户信息
            TraderCustomerVo customer = traderCustomerService.getCustomerBussinessInfo(saleorder.getTraderId());
            mv.addObject("customer", customer);
            User user = null;
            // 销售人员名称
            user = userService.getUserById(saleorder.getUserId());
            saleorder.setSalesName(user == null ? "" : user.getUsername());
            // 创建人员
            user = userService.getUserById(saleorder.getCreator());
            saleorder.setCreatorName(user == null ? "" : user.getUsername());

            // 归属销售
            user = userService.getUserByTraderId(saleorder.getTraderId(), 1);// 1客户，2供应商
            saleorder.setOptUserName(user == null ? "" : user.getUsername());

            // 销售部门（若一个多个部门，默认取第一个部门）
            User userOrg = orgService.getTraderUserAndOrgByTraderId(saleorder.getTraderId(), 1);
            saleorder.setSalesDeptName(userOrg == null ? "" : userOrg.getOrgName());

            // 获取订单客户信息
            // 客户类型
            saleorder.setCustomerTypeStr(getSysOptionDefinition(saleorder.getCustomerType()).getTitle());
            // 客户性质
            saleorder.setCustomerNatureStr(getSysOptionDefinition(saleorder.getCustomerNature()).getTitle());
            // 客户资质信息
            // 资质信息
            Map<String, Object> map = accountPeriodService.getTraderAptitudeInfo(saleorder.getTraderId());
            if (map != null) {
                TraderCertificateVo bus = null;
                // 营业执照信息
                if (map.containsKey("business")) {
                    JSONObject json = JSONObject.fromObject(map.get("business"));
                    bus = (TraderCertificateVo) JSONObject.toBean(json, TraderCertificateVo.class);
                    mv.addObject("business", bus);
                }
                // 税务登记信息
                TraderCertificateVo tax = null;
                if (map.containsKey("tax")) {
                    JSONObject json = JSONObject.fromObject(map.get("tax"));
                    tax = (TraderCertificateVo) JSONObject.toBean(json, TraderCertificateVo.class);
                    mv.addObject("tax", tax);
                }
                // 组织机构信息
                TraderCertificateVo orga = null;
                if (map.containsKey("orga")) {
                    JSONObject json = JSONObject.fromObject(map.get("orga"));
                    orga = (TraderCertificateVo) JSONObject.toBean(json, TraderCertificateVo.class);
                    mv.addObject("orga", orga);
                }
                // 客户
                mv.addObject("customerProperty", saleorder.getCustomerNature());
                if (saleorder.getCustomerNature().intValue() == 456) {// 分销
                    // 二类医疗资质
                    List<TraderCertificateVo> twoMedicalList = null;
                    if (map.containsKey("twoMedical")) {
                        // JSONObject json = JSONObject.fromObject();
                        twoMedicalList = (List<TraderCertificateVo>) map.get("twoMedical");
                        mv.addObject("twoMedicalList", twoMedicalList);
                    }
                    // 二类医疗资质详情
                    List<TraderMedicalCategoryVo> list = null;
                    if (map.containsKey("medicalCertificate")) {
                        JSONArray jsonArray = JSONArray.fromObject(map.get("medicalCertificate"));
                        list = (List<TraderMedicalCategoryVo>) JSONArray.toCollection(jsonArray,
                                TraderMedicalCategoryVo.class);
                        mv.addObject("medicalCertificates", list);
                    }
                    // 三类医疗资质
                    TraderCertificateVo threeMedical = null;
                    if (map.containsKey("threeMedical")) {
                        JSONObject json = JSONObject.fromObject(map.get("threeMedical"));
                        threeMedical = (TraderCertificateVo) JSONObject.toBean(json, TraderCertificateVo.class);
                        mv.addObject("threeMedical", threeMedical);
                    }
                } else {// 终端
                    // 医疗机构执业许可证
                    List<TraderCertificateVo> practiceList = null;
                    if (map.containsKey("practice")) {
                        // JSONObject
                        // json=JSONObject.fromObject(map.get("practice"));
                        practiceList = (List<TraderCertificateVo>) map.get("practice");
                        mv.addObject("practiceList", practiceList);
                    }
                }
            }
            Page page = Page.newBuilder(null, null, null);

            // 获取订单收票信息
            TraderFinanceVo tf = new TraderFinanceVo();
            tf.setTraderId(saleorder.getTraderId());
            tf.setTraderType(ErpConst.ONE);
            TraderFinanceVo traderFinance = traderCustomerService.getTraderFinanceByTraderId(tf);
            mv.addObject("traderFinance", traderFinance);
            // 查询此订单最近一次开票申请记录--验证是否允许开票
            InvoiceApply invoiceApply = invoiceService.getInvoiceApplyByRelatedId(saleorder.getSaleorderId(),
                    SysOptionConstant.ID_505, saleorder.getCompanyId());
            mv.addObject("invoiceApply", invoiceApply);
            // 开票申请
            if (saleorder.getCompanyId().equals(1)) {// 贝登
                List<InvoiceApply> saleInvoiceApplyList = invoiceService
                        .getSaleInvoiceApplyList(saleorder.getSaleorderId(), SysOptionConstant.ID_505, -1);
                mv.addObject("saleInvoiceApplyList", saleInvoiceApplyList);
            }
            // 获取发票信息
            List<Invoice> saleInvoiceList = invoiceService.getInvoiceInfoByRelatedId(saleorder.getSaleorderId(),
                    SysOptionConstant.ID_505);
            mv.addObject("saleInvoiceList", saleInvoiceList);

            // 终端类型
            SysOptionDefinition sysOptionDefinition = getSysOptionDefinition(saleorder.getTerminalTraderType());
            saleorder.setTerminalTraderTypeStr(sysOptionDefinition == null ? "" : sysOptionDefinition.getTitle());

            // 获取订单产品信息
            Saleorder sale = new Saleorder();
            sale.setSaleorderId(saleorderId);
            sale.setOptor("finance");
            sale.setCompanyId(curr_user.getCompanyId());
            List<SaleorderGoods> saleorderGoodsList = saleorderService.getSaleorderGoodsById(sale);

            // add by Tomcat.Hui 2019/11/28 15:40 .Desc: VDERP-1325 分批开票 已开票数量/开票中数量/该订单该sku的数量. start
            //已开票数量 申请中数量
            Map<Integer , Map<String, Object>> taxNumsMap = invoiceService.getInvoiceNums(saleorder);
            if (null != taxNumsMap) {
                saleorderGoodsList.stream().forEach(g -> {
                    g.setAppliedNum(new BigDecimal(taxNumsMap.get(g.getSaleorderGoodsId()).get("APPLY_NUM").toString()));
                    g.setInvoicedNum(new BigDecimal(taxNumsMap.get(g.getSaleorderGoodsId()).get("INVOICE_NUM").toString()));
                    g.setInvoicedAmount(new BigDecimal(taxNumsMap.get(g.getSaleorderGoodsId()).get("INVOICE_AMOUNT").toString()));
                    g.setAppliedAmount(new BigDecimal(taxNumsMap.get(g.getSaleorderGoodsId()).get("APPLY_AMOUNT").toString()));
                });
            }
            // add by Tomcat.Hui 2019/11/28 15:40 .Desc: VDERP-1325 分批开票 已开票数量/开票中数量/该订单该sku的数量. end

            List<ExpressDetail> expresseList = expressService.getSEGoodsNum(saleorderGoodsList.stream().map(SaleorderGoods::getSaleorderGoodsId).collect(Collectors.toList()));
            mv.addObject("expresseList", expresseList);

            mv.addObject("saleorderGoodsList", saleorderGoodsList);

            //新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
            if(!CollectionUtils.isEmpty(saleorderGoodsList)){
                List<Integer> skuIds = new ArrayList<>();
                saleorderGoodsList.stream().forEach(saleGood -> {
                    skuIds.add(saleGood.getGoodsId());
                });
                List<Map<String,Object>> skuTipsMap = this.vGoodsService.skuTipList(skuIds);
                Map<String,Map<String,Object>> newSkuInfosMap = skuTipsMap.stream().collect(Collectors.toMap(key->key.get("SKU_NO").toString(), v -> v, (k, v) -> v));
                mv.addObject("newSkuInfosMap", newSkuInfosMap);
            }
            //新商品流切换 VDERP-1970 add by brianna 2020/3/16 end

            // 付款方式列表
            List<SysOptionDefinition> paymentTermList = getSysOptionDefinitionList(SysOptionConstant.ID_418);
            mv.addObject("paymentTermList", paymentTermList);

            // 发货方式
            List<SysOptionDefinition> deliveryTypes = getSysOptionDefinitionList(480);
            mv.addObject("deliveryTypes", deliveryTypes);

            // 获取物流公司列表
            List<Logistics> logisticsList = getLogisticsList(curr_user.getCompanyId());
            mv.addObject("logisticsList", logisticsList);

            // 运费说明
            List<SysOptionDefinition> freightDescriptions = getSysOptionDefinitionList(469);
            mv.addObject("freightDescriptions", freightDescriptions);

            // 发票类型
            List<SysOptionDefinition> invoiceTypes = getSysOptionDefinitionList(428);
            mv.addObject("invoiceTypes", invoiceTypes);

            // 沟通类型为商机和报价和销售订单
            CommunicateRecord cr = new CommunicateRecord();
            if (saleorder.getQuoteorderId() == null || saleorder.getQuoteorderId().equals(0)) {
                cr.setQuoteorderId(null);
            } else {
                cr.setQuoteorderId(saleorder.getQuoteorderId());
            }
            if (saleorder.getBussinessChanceId() == null || saleorder.getBussinessChanceId().equals(0)) {
                cr.setBussinessChanceId(null);
            } else {
                cr.setBussinessChanceId(saleorder.getBussinessChanceId());
            }

            cr.setSaleorderId(saleorder.getSaleorderId());
            List<CommunicateRecord> communicateList = traderCustomerService.getCommunicateRecordListPage(cr, page);
            if (!communicateList.isEmpty()) {
                // 沟通内容
                mv.addObject("communicateList", communicateList);
                mv.addObject("page", page);
            }

            // 获取订单合同回传&订单送货单回传列表
            List<Attachment> saleorderAttachmentList = saleorderService.getSaleorderAttachmentList(saleorderId);
            mv.addObject("saleorderAttachmentList", saleorderAttachmentList);

            // 售后订单列表
            AfterSalesVo as = new AfterSalesVo();
            as.setOrderId(saleorderId);
            as.setSubjectType(SysOptionConstant.ID_535);
            List<AfterSalesVo> asList = afterSalesOrderService.getAfterSalesVoListByOrderId(as);
            if (asList != null && asList.size() > 0) {
                if (asList.get(0).getStatus() == 2 || asList.get(0).getStatus() == 3) {
                    mv.addObject("addAfterSales", 1);
                } else {
                    mv.addObject("addAfterSales", 0);
                }
            } else {
                mv.addObject("addAfterSales", 1);
            }
            mv.addObject("asList", asList);

            // 获取交易信息数据
            CapitalBill capitalBill = new CapitalBill();
            capitalBill.setOperationType("finance_sale_detail");
            CapitalBillDetail capitalBillDetail = new CapitalBillDetail();
            capitalBillDetail.setOrderType(ErpConst.ONE);// 销售订单类型
            capitalBillDetail.setOrderNo(saleorder.getSaleorderNo());
            capitalBillDetail.setRelatedId(saleorderId);
            capitalBill.setCapitalBillDetail(capitalBillDetail);
            List<CapitalBill> capitalBillList = capitalBillService.getCapitalBillList(capitalBill);
            mv.addObject("capitalBillList", capitalBillList);

            // 资金流水交易方式
            List<SysOptionDefinition> traderModes = getSysOptionDefinitionList(519);
            mv.addObject("traderModes", traderModes);

            // 资金流水交易方式
            List<SysOptionDefinition> bussinessTypes = getSysOptionDefinitionList(524);
            mv.addObject("bussinessTypes", bussinessTypes);

            // 获取交易信息（订单实际金额，客户已付款金额）
            Map<String, BigDecimal> saleorderDataInfo = saleorderService.getSaleorderDataInfo(saleorderId);
            mv.addObject("saleorderDataInfo", saleorderDataInfo);
            // 提前采购审核信息
            Map<String, Object> historicInfoEarly = actionProcdefService.getHistoric(processEngine,
                    "earlyBuyorderVerify_" + saleorderId);
            mv.addObject("taskInfoEarly", historicInfoEarly.get("taskInfo"));
            mv.addObject("startUserEarly", historicInfoEarly.get("startUser"));
            mv.addObject("endStatusEarly", historicInfoEarly.get("endStatus"));
            mv.addObject("historicActivityInstanceEarly", historicInfoEarly.get("historicActivityInstance"));
            mv.addObject("commentMapEarly", historicInfoEarly.get("commentMap"));
            mv.addObject("candidateUserMapEarly", historicInfoEarly.get("candidateUserMap"));
            Task taskInfoEarly = (Task) historicInfoEarly.get("taskInfo");
            // 当前审核人
            String verifyUsersEarly = null;
            List<String> verifyUserListEarly = new ArrayList<>();
            if (null != taskInfoEarly) {
                Map<String, Object> taskInfoVariablesEarly = actionProcdefService.getVariablesMap(taskInfoEarly);
                verifyUsersEarly = (String) taskInfoVariablesEarly.get("verifyUsers");
                String verifyUserEarly = (String) taskInfoVariablesEarly.get("verifyUserList");
                if (null != verifyUserEarly) {
                    verifyUserListEarly = Arrays.asList(verifyUserEarly.split(","));
                }
            }
            mv.addObject("verifyUsersEarly", verifyUsersEarly);
            mv.addObject("verifyUserListEarly", verifyUserListEarly);
            // 开票申请审核信息
            if (invoiceApply != null) {
                Map<String, Object> historicInfo = actionProcdefService.getHistoric(processEngine,
                        "invoiceVerify_" + invoiceApply.getInvoiceApplyId());
                mv.addObject("taskInfo", historicInfo.get("taskInfo"));
                mv.addObject("startUser", historicInfo.get("startUser"));
                mv.addObject("candidateUserMap", historicInfo.get("candidateUserMap"));
                // 最后审核状态
                mv.addObject("endStatus", historicInfo.get("endStatus"));
                mv.addObject("historicActivityInstance", historicInfo.get("historicActivityInstance"));
                mv.addObject("commentMap", historicInfo.get("commentMap"));

                Task taskInfo = (Task) historicInfo.get("taskInfo");
                // 当前审核人
                String verifyUsers = null;
                if (null != taskInfo) {
                    Map<String, Object> taskInfoVariables = actionProcdefService.getVariablesMap(taskInfo);
                    verifyUsers = (String) taskInfoVariables.get("verifyUsers");
                }
                mv.addObject("verifyUsers", verifyUsers);
            }

            // 物流信息
            if (saleorderGoodsList.size() > 0) {
                Express express = new Express();
                express.setBusinessType(SysOptionConstant.ID_496);
                express.setCompanyId(curr_user.getCompanyId());
                List<Integer> relatedIds = new ArrayList<Integer>();
                for (SaleorderGoods sg : saleorderGoodsList) {
                    relatedIds.add(sg.getSaleorderGoodsId());
                }
                express.setRelatedIds(relatedIds);
                try {
                    List<Express> expressList = expressService.getExpressList(express);
                    mv.addObject("expressList", expressList);
                } catch (Exception e) {
                    logger.error("viewSaleorder 1:", e);
                }
            }

            // 出库记录
            saleorder.setBussinessType(2);
            // 出库记录清单
//            List<WarehouseGoodsOperateLog> warehouseOutList = warehouseOutService.getOutDetil(saleorder);
//            List<String> timeArrayWl = new ArrayList<>();
//            if (null != warehouseOutList) {
//                for (WarehouseGoodsOperateLog wl : warehouseOutList) {
//                    User u = userService.getUserById(wl.getCreator());
//                    wl.setOpName(u.getUsername());
//                    timeArrayWl.add(DateUtil.convertString(wl.getAddTime(), "YYYY-MM-dd HH:mm:ss"));
//                }
//                HashSet<String> wArray = new HashSet<String>(timeArrayWl);
//                mv.addObject("timeArrayWl", wArray);
//            }
//            mv.addObject("warehouseOutList", warehouseOutList);
        } catch (Exception e) {
            logger.error("viewSaleorder 2:", e);
        }
        mv.addObject("saleorder", saleorder);
        mv.setViewName("finance/invoice/view_saleorder_detail");
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 新增开票记录
     *
     * @param request
     * @param invoice
     * @return
     * @Note <b>Author:</b> duke <br>
     *       <b>Date:</b> 2017年8月30日 上午10:41:54
     */
    @FormToken(save = true)
    @ResponseBody
    @RequestMapping(value = "addSaleInvoice")
    public ModelAndView addSaleInvoice(HttpServletRequest request, Invoice invoice) {
        ModelAndView mv = new ModelAndView();
        try {
            invoice.setType(SysOptionConstant.ID_505);
            Map<String, Object> map = invoiceService.getSaleInvoiceList(invoice);// 获取可开票记录

            // add by Tomcat.Hui 2019/11/22 15:39 .Desc: VDERP-1325 分批开票 最大录取数量改为申请数量. start
            InvoiceApply query = new InvoiceApply();
            query.setInvoiceApplyId(invoice.getInvoiceApplyId());
            InvoiceApply apply = invoiceService.getApplyDetailById(query);

            List<InvoiceDetail> invoiceDetailList = null;
            if (map != null) {
                invoiceDetailList = (List<InvoiceDetail>) map.get("invoiceDetailList");
                mv.addObject("invoiceDetailList", invoiceDetailList);
                mv.addObject("invoiceApply", apply);
            }
            // add by Tomcat.Hui 2019/11/22 15:39 .Desc: VDERP-1325 分批开票 最大录取数量改为申请数量. end
            // 发票类型

            //新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
            if(!CollectionUtils.isEmpty(invoiceDetailList)){
                List<Integer> skuIds = new ArrayList<>();
                invoiceDetailList.stream().forEach(invoiceDetail -> {
                    skuIds.add(invoiceDetail.getGoodsId());
                });
                List<Map<String,Object>> skuTipsMap = this.vGoodsService.skuTipList(skuIds);
                Map<String,Map<String,Object>> newSkuInfosMap = skuTipsMap.stream().collect(Collectors.toMap(key->key.get("SKU_NO").toString(), v -> v, (k, v) -> v));
                mv.addObject("newSkuInfosMap", newSkuInfosMap);
            }
            //新商品流切换 VDERP-1970 add by brianna 2020/3/16 end

            List<SysOptionDefinition> invoiceTypeList = getSysOptionDefinitionList(SysOptionConstant.ID_428);
            mv.addObject("invoiceTypeList", invoiceTypeList);
        } catch (Exception e) {
            logger.error("addSaleInvoice:", e);
            return fail(mv);
        }

        mv.setViewName("finance/invoice/add_sale_invoice");
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 销售发票添加保存
     *
     * @param request
     * @param invoice
     * @return
     * @Note <b>Author:</b> duke <br>
     *       <b>Date:</b> 2017年8月31日 下午2:19:40
     */
    @FormToken(remove = true)
    @ResponseBody
    @RequestMapping(value = "saveSaleorderInvoice")
    @SystemControllerLog(operationType = "add", desc = "保存销售发票")
    public ResultInfo saveSaleorderInvoice(HttpServletRequest request, Invoice invoice,
                                             @RequestParam(required = false, value = "saleorderId") Integer saleorderId,
                                             @RequestParam(required = false, value = "relatedIdArr") String relatedIdArr,
                                             @RequestParam(required = false, value = "detailGoodsIdArr") String detailGoodsIdArr,
                                             @RequestParam(required = false, value = "invoiceNumArr") String invoiceNumArr,
                                             @RequestParam(required = false, value = "invoicePriceArr") String invoicePriceArr,
                                             @RequestParam(required = false, value = "invoiceTotleAmountArr") String invoiceTotleAmountArr) {

        try {
            invoice.setRelatedId(saleorderId);
            List<Integer> relatedIdList = JSON.parseArray("[" + relatedIdArr + "]", Integer.class);
            List<Integer> detailGoodsIdList = JSON.parseArray("[" + detailGoodsIdArr + "]", Integer.class);
            List<BigDecimal> invoiceNumList = JSON.parseArray("[" + invoiceNumArr + "]", BigDecimal.class);
            List<BigDecimal> invoicePriceList = JSON.parseArray("[" + invoicePriceArr + "]", BigDecimal.class);
            List<BigDecimal> invoiceTotleAmountList = JSON.parseArray("[" + invoiceTotleAmountArr + "]",
                    BigDecimal.class);

            invoice.setRelatedIdList(relatedIdList);
            invoice.setDetailGoodsIdList(detailGoodsIdList);
            invoice.setInvoiceNumList(invoiceNumList);
            invoice.setInvoicePriceList(invoicePriceList);
            invoice.setInvoiceTotleAmountList(invoiceTotleAmountList);
            User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
            if (user != null) {
                invoice.setCreator(user.getUserId());
                invoice.setAddTime(DateUtil.gainNowDate());

                invoice.setCompanyId(user.getCompanyId());

                invoice.setUpdater(user.getUserId());
                invoice.setModTime(DateUtil.gainNowDate());

                invoice.setType(SysOptionConstant.ID_505);// 销售录票

                // 销售默认审核通过
                invoice.setValidTime(DateUtil.gainNowDate());
                invoice.setValidUserId(user.getUserId());
                invoice.setValidStatus(1);// 审核通过

                ResultInfo<?> result = invoiceService.saveInvoice(invoice);
                if (result.getCode() == 0) {
                    // 同步开票
                    if (user.getCompanyId().equals(1)) {
                        Saleorder saleorder = (Saleorder) result.getData();
                        List<Integer> invoiceIds = saleorder.getInvoiceId();
                        if (invoiceIds.size() > 0) {
                            for (Integer invoiceId : invoiceIds) {
                                vedengSoapService.invoiceSync(invoiceId);
                            }
                        }
                    }
                    return new ResultInfo(0,"保存成功");
                } else {
                    return new ResultInfo(-1,"保存发票返回失败");
                }
            }
        } catch (Exception e) {
            logger.error("saveSaleorderInvoice:", e);
            return new ResultInfo(-1,"保存发票出现异常");
        }
        return new ResultInfo(-1,"未保存发票");
    }

    /**
     * <b>Description:</b><br>
     * 寄送发票
     *
     * @param request
     * @param invoice
     * @return
     * @Note <b>Author:</b> duke <br>
     *       <b>Date:</b> 2017年9月1日 上午11:18:09
     */
    @ResponseBody
    @RequestMapping(value = "sendSaleInvoice")
    public ModelAndView sendSaleInvoice(HttpServletRequest request, Invoice invoice,
                                        @RequestParam(required = false, value = "invoiceIdArr") String invoiceIdArr) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("invoiceIdArr", invoiceIdArr);

        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        if (user != null) {
            List<Logistics> logisticsList = logisticsService.getLogisticsList(user.getCompanyId());
            mv.addObject("logisticsList", logisticsList);
        }
        mv.addObject("invoice", invoice);
        mv.setViewName("finance/invoice/send_sale_invoice");
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 发票快递寄送保存
     *
     * @param request
     * @param express
     * @return
     * @Note <b>Author:</b> duke <br>
     *       <b>Date:</b> 2017年9月1日 下午2:35:43
     */
    @ResponseBody
    @RequestMapping(value = "saveExpress")
    @SystemControllerLog(operationType = "add", desc = "保存发票快递寄送")
    public ResultInfo<?> saveExpress(HttpServletRequest request, Express express,
                                     @RequestParam(required = false, value = "invoiceIdArr") String invoiceIdArr,
                                     @RequestParam(required = false, value = "invoiceNo") String invoiceNo) {
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        if (user != null) {
            List<Integer> invoiceIdList = JSON.parseArray(invoiceIdArr, Integer.class);

            express.setCreator(user.getUserId());
            express.setAddTime(DateUtil.gainNowDate());

            express.setUpdater(user.getUserId());
            express.setModTime(DateUtil.gainNowDate());

            express.setCompanyId(user.getCompanyId());

            express.setIsEnable(1);// 有效

            List<ExpressDetail> list = new ArrayList<ExpressDetail>();
            /*
             * 单个发票在db处理 if(invoiceNo!=null && (invoiceIdList==null ||
             * invoiceIdList.size()==0)){ ExpressDetail ed = new
             * ExpressDetail(); ed.setNum(1);
             * ed.setBusinessType(SysOptionConstant.ID_497);
             * ed.setRelatedNo(invoiceNo); list.add(ed); }else
             */
            if (StringUtils.isBlank(invoiceNo) && invoiceIdList != null && invoiceIdList.size() > 0) {
                for (Integer i : invoiceIdList) {
                    ExpressDetail ed = new ExpressDetail();
                    ed.setNum(1);
                    ed.setBusinessType(SysOptionConstant.ID_497);
                    ed.setRelatedId(i);
                    list.add(ed);
                }
            }

            express.setDeliveryFrom(1838);
            express.setDeliveryTime(DateUtil.gainNowDate());

            express.setExpressDetail(list);

            express.setRelatedIds(invoiceIdList);
            express.setFpNo(invoiceNo);
            ResultInfo<?> resultInfo = invoiceService.saveExpressInvoice(express);
            logger.info("saveExpress | 异步调用微信 发送微信消息 开始 ........................");
            new Thread() {// 异步推送数据
                @Override
                public void run() {
                    //如果发票保存快递单成功
                    if(null != resultInfo && resultInfo.getCode().equals(0)){
                        //发票寄送后给对应的用户发微信消息
                        if(express.getRelatedId() != null && express.getRelatedId() !=0){
                            //发票寄送后给对应的用户发微信消息
                            List<Integer> invoiceIdList = new ArrayList<>();
                            invoiceIdList.add(express.getRelatedId());
                            List<Invoice> invoiceList = invoiceService.getInvoiceListByInvoiceIdList(invoiceIdList);
                            ResultInfo frResul = invoiceService.sendWxMessageForInvoice(invoiceList);
                            // 医械购
                            // add by franlin.wu for [微信模板消息推送 ] at 2019-09-20 begin
                            if(CommonConstants.ONE.equals(sendYxgWxTempMsgFlag)) {
                                wxSendMsgForInvoice(invoiceList);
                            }
                            // add by franlin.wu for [微信模板消息推送 ] at 2019-09-20 end
                            //贝登发票寄出微信提醒
                            wxSendMsgForInoice2(invoiceList);
                        }
                    }
                }
            }.start();
            /*
             * //同步 if(resultInfo.getCode().equals(0) &&
             * user.getCompanyId().equals(1)){ Express data = (Express)
             * resultInfo.getData(); if(data.getExpressId() != null &&
             * data.getExpressId() > 0){
             * vedengSoapService.expressSync(data.getExpressId()); } }
             */
            return resultInfo;
        }

        return new ResultInfo<>();
    }

    /**
     * <b>Description: 编辑 发票快递页面</b><br>
     *
     * @param request
     * @param express
     * @param invoiceId
     * @param invoiceNo
     * @return <b>Author: Franlin</b> <br>
     *         <b>Date: 2018年5月7日 下午1:09:08 </b>
     */
    @ResponseBody
    @RequestMapping(value = "/editExpressView")
    public ModelAndView editExpressView(HttpServletRequest request, Express express,
                                        @RequestParam(required = false, value = "invoiceId") String invoiceId,
                                        @RequestParam(required = false, value = "invoiceNo") String invoiceNo) {
        ModelAndView view = new ModelAndView();

        User curr_user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        Map<Integer, Object> map = new HashMap<Integer, Object>();
        try {
            List<Express> expressList = expressService.getExpressList(express);
            // 此处为 发票 快递信息,不必展示详情 TODO
            // if (null != expressList.get(0))
            // {
            // if (null != expressList.get(0).getExpressDetail())
            // {
            // // 循环计算每件产品发货数量
            // for (ExpressDetail ed : expressList.get(0).getExpressDetail())
            // {
            // Integer num = 0;
            // num = (Integer) map.get(ed.getRelatedId());
            // num = num + ed.getNum();
            // map.put(ed.getRelatedId(), num);
            // }
            // }
            // }
            view.addObject("expressList", expressList.get(0));

            view.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(expressList.get(0))));
        } catch (Exception e) {
            logger.error("editExpressView:", e);
        }
        // 获取物流公司列表
        List<Logistics> logisticsList = logisticsService.getLogisticsList(curr_user.getCompanyId());
        view.addObject("logisticsList", logisticsList);
        view.addObject("invoiceId", invoiceId);
        // 设置返回页面
        view.setViewName("finance/invoice/edit_express");

        return view;
    }

    /**
     * <b>Description: 编辑并保存发票的快递信息</b><br>
     *
     * @param request
     * @param express
     * @param invoiceId
     * @return <b>Author: Franlin</b> <br>
     *         <b>Date: 2018年5月7日 下午1:32:39 </b>
     */
    @ResponseBody
    @RequestMapping(value = "updateExpress")
    @SystemControllerLog(operationType = "edit", desc = "保存或编辑快递信息")
    public ResultInfo<?> updateExpress(HttpServletRequest request, Express express,
                                       @RequestParam(required = false, value = "invoiceId") String invoiceId,
                                       @RequestParam(required = false, value = "deliveryTimes") String deliveryTimes) {
        ResultInfo<?> result = new ResultInfo<>();
        User curr_user = (User) request.getSession().getAttribute(Consts.SESSION_USER);

        // 主要更新快递主表信息
        // 修改时间
        express.setModTime(DateUtil.sysTimeMillis());
        // 更新者userId
        express.setUpdater(curr_user.getUserId());
        // 发货时间
        express.setDeliveryTime(DateUtil.convertLong(deliveryTimes, "yyyy-MM-dd"));
        // 设置快递类型 497
        express.setBusinessType(SysOptionConstant.ID_497);
        // logisticsNo快递单号, 快递公司 --> express

        List<Express> epList = new LinkedList<Express>();
        epList.add(express);
        result = expressService.batchUpdateExpress(epList);
        return result;
    }

    /**
     * <b>Description:</b><br>
     * 开票记录列表
     *
     * @param request
     * @param invoice
     * @return
     * @Note <b>Author:</b> duke <br>
     *       <b>Date:</b> 2017年8月31日 下午4:36:34
     */
    @ResponseBody
    @RequestMapping(value = "getSaleInvoiceListPage")
    public ModelAndView getSaleInvoiceListPage(HttpServletRequest request, Invoice invoice,
                                               @RequestParam(required = false, value = "startTime") String startTime,
                                               @RequestParam(required = false, value = "endTime") String endTime,
                                               @RequestParam(required = false, value = "searchDateType") String searchDateType, // 效验是否新打开查询页
                                               @RequestParam(required = false, defaultValue = "1") Integer pageNo,
                                               @RequestParam(required = false) Integer pageSize) {
        ModelAndView mv = new ModelAndView();
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        if (StringUtils.isBlank(searchDateType)) {// 新打开
            startTime = DateUtil.getFirstDayOfMonth(0);// 当月第一天
            endTime = DateUtil.getNowDate("yyyy-MM-dd");// 当前日期
        } else {
            mv.addObject("de_startTime", DateUtil.getFirstDayOfMonth(0));
            mv.addObject("de_endTime", DateUtil.getNowDate("yyyy-MM-dd"));
        }
        mv.addObject("searchDateType", searchDateType);
        if (invoice.getDateType() == null) {// 日期查询类型
            invoice.setDateType(1);
        }
        if (StringUtils.isNotBlank(startTime)) {
            invoice.setStartDate(DateUtil.convertLong(startTime + " 00:00:00", ""));
        }
        if (StringUtils.isNotBlank(endTime)) {
            invoice.setEndDate(DateUtil.convertLong(endTime + " 23:59:59", ""));
        }
        mv.addObject("startTime", startTime);
        mv.addObject("endTime", endTime);

        Page page = getPageTag(request, pageNo, pageSize);

        // 发票类型
        List<SysOptionDefinition> invoiceTypeList = getSysOptionDefinitionList(SysOptionConstant.ID_428);

        mv.addObject("invoiceTypeList", invoiceTypeList);

        // invoice.setType(SysOptionConstant.ID_505);//销售开票
        invoice.setTag(1);// 开票
        invoice.setCompanyId(user.getCompanyId());
        Map<String, Object> map = invoiceService.getSaleInvoiceListPage(invoice, page);
        if (map != null && map.size() > 0) {
            // List<Invoice> saleInvoiceList=
            // (List<Invoice>)map.get("saleInvoiceList");
            mv.addObject("saleInvoiceList", (List<Invoice>) map.get("saleInvoiceList"));
            mv.addObject("page", (Page) map.get("page"));
            mv.addObject("invoice", (Invoice) map.get("invoice"));

            mv.addObject("traderList", (List<User>) map.get("traderList"));
            mv.addObject("userList", (List<User>) map.get("userList"));
            mv.addObject("saleAfterList", map.get("saleAfterList"));
        }

        if (user != null) {
            // 获取物流公司列表
            List<Logistics> logisticsList = getLogisticsList(user.getCompanyId());
            mv.addObject("logisticsList", logisticsList);
        }

        mv.setViewName("finance/invoice/list_sale_invoice");
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 打印寄送发票的快递单
     *
     * @param session
     * @param request
     * @param logisticsId
     * @param invoiceNo
     * @return
     * @Note <b>Author:</b> scott <br>
     *       <b>Date:</b> 2017年12月9日 下午5:08:24
     */
    @ResponseBody
    @RequestMapping(value = "/printExpress")
    public ModelAndView printExpress(HttpSession session, HttpServletRequest request, Integer logisticsId,
                                     String invoiceNo, Integer type, Integer expressId, String logisticsName) {
        ModelAndView mv = new ModelAndView();
        User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
        // Integer btype = 0;
        Long currTime = DateUtil.sysTimeMillis();
        mv.addObject("currTime", currTime);
        // 根据票的NO获取票的详情
        Invoice ic = new Invoice();
        ic.setInvoiceNo(invoiceNo);
        ic.setType(type);
        Invoice invoice = invoiceService.getInvoiceByNo(ic);
        Saleorder saleorder = new Saleorder();
        if (invoice.getType() == 504) {// 售后开票
            AfterSales as = new AfterSales();
            as.setAfterSalesId(invoice.getRelatedId());
            AfterSales afterSales = afterSalesOrderService.selectById(as);
            // 获取售后详情
            AfterSalesDetail av = afterSalesOrderService.selectadtById(as);
            mv.addObject("av", av);
            mv.addObject("btype", 3);
            if (afterSales.getSubjectType() == 539) {// 销售
                saleorder.setSaleorderId(afterSales.getOrderId());
                saleorder = saleorderService.getBaseSaleorderInfo(saleorder);
                mv.addObject("saleorder", saleorder);
                mv.addObject("btype", 3);
            }
        } else if (invoice.getType() == 505) {// 销售开票
            saleorder.setSaleorderId(invoice.getRelatedId());
            saleorder = saleorderService.getBaseSaleorderInfo(saleorder);
            mv.addObject("saleorder", saleorder);
            mv.addObject("btype", 3);
        }

        // 同步
        if (session_user.getCompanyId().equals(1)) {
            if (expressId != null && expressId > 0) {
                vedengSoapService.expressSync(expressId);
                new Thread() {// 异步推送数据
                    @Override
                    public void run() {
                        //如果是销售的话
                        if(invoice.getType() == 505){
                            Saleorder saleorderInfo = new Saleorder();
                            saleorderInfo.setSaleorderId(invoice.getRelatedId());
                            Saleorder saleorderBaseInfo = saleorderService.getBaseSaleorderInfo(saleorderInfo);
                            logger.info(" sync.printExpress | 当前订单查询 | saleorderBaseInfo ：{}", saleorderBaseInfo);
                            if(null == saleorderBaseInfo) {
                                return;
                            }
                            List<Integer> invoiceIdList = new ArrayList<>();
                            invoiceIdList.add(invoice.getInvoiceId());
                            List<Invoice> invoiceList = invoiceService.getInvoiceListByInvoiceIdList(invoiceIdList);
                            //如果订单是VS，DH，JX
                            if(saleorderBaseInfo.getOrderType() == 0 || saleorderBaseInfo.getOrderType() == 3 || saleorderBaseInfo.getOrderType() == 4){
                                //发票寄送后给对应的用户发微信消息
                                ResultInfo frResul = invoiceService.sendWxMessageForInvoice(invoiceList);
                            }
                            // 医械购
                            if(OrderConstant.ORDER_TYPE_HC.equals(saleorderBaseInfo.getOrderType())) {
                                // add by franlin.wu for [微信模板消息推送 ] at 2019-09-20 begin
                                if(CommonConstants.ONE.equals(sendYxgWxTempMsgFlag)) {
                                    wxSendMsgForInvoice(invoiceList);
                                }
                                // add by franlin.wu for [微信模板消息推送 ] at 2019-09-20 end
                                //贝登发票寄出微信提醒
                                wxSendMsgForInoice2(invoiceList);
                            }
                        }
                    }
                }.start();
            }
        }

        mv.addObject("stype", invoice.getType());
        mv.addObject("type", 1);
        mv.addObject("companyId", session_user.getCompanyId());
        mv.setViewName("logistics/warehouseOut/print_express_" + logisticsId);
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 顺丰、中通面单打印
     *
     * @param session
     * @param request
     * @param logisticsId
     * @param invoiceNo
     * @param type
     * @param expressId
     * @param logisticsName
     * @return
     * @Note <b>Author:</b> scott <br>
     *       <b>Date:</b> 2018年5月17日 上午11:39:27
     */
    @ResponseBody
    @RequestMapping(value = "printExpressSf")
    public ResultInfo<?> printExpressSf(HttpSession session, HttpServletRequest request, Integer logisticsId,
                                        String invoiceNo, Integer type, Integer expressId, String logisticsName, String logisticsNo,
                                        String invoiceIdArr, Express express,String invoiceNos,String types) {

        ResultInfo<?> result = new ResultInfo<>();
        User curr_user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        String ip = super.getIpAddress(request);
        String expressNo = "";
        Integer cwtype = 0;// 销售单下的直发、普发
        Integer btype = 0;// 业务类型
        // 丰桥账号
        List<SysOptionDefinition> apiList = getSysOptionDefinitionList(689);
        // 中通账号
        List<SysOptionDefinition> zotApiList = getSysOptionDefinitionList(701);
        Express ep = new Express();
             // 根据票的NO获取票的详情
             Invoice ic = new Invoice();
             ic.setInvoiceNo(invoiceNo);//发票号码
             ic.setType(type);//开票申请类型
             Invoice invoice = invoiceService.getInvoiceByNo(ic); //根据单号获取发票信息
            logger.info("查询是否范文invoice========"+invoice);
             Saleorder saleorder = new Saleorder();
             AfterSalesDetail av = new AfterSalesDetail();//售后细节实体类

             if (invoice.getType() == 504) {// 售后开票
                 AfterSales as = new AfterSales();
                 as.setAfterSalesId(invoice.getRelatedId());//RelatedId与售后T_AFTER_SALES_DETAIL表的AfterSalesId关联
                 av = afterSalesOrderService.selectadtById(as);
                 cwtype = 3;
                 btype = 3;
             } else if (invoice.getType() == 505) {// 销售开票
                 saleorder.setSaleorderId(invoice.getRelatedId());
                 saleorder = saleorderService.getBaseSaleorderInfo(saleorder);//RelatedId与销售订单id相关联
                 cwtype = 3;
                 btype = 1;
             }
             if ("顺丰速运".equals(logisticsName) || "中通快递".equals(logisticsName)) {
                 /******** 在线下单打印面单 ***********/
                 String xmlStr = "";
                 String jsonStr = "";
                 String image = "";
                 ResultInfo<?> res = null;

                 Map<String, String> map = new HashMap<>();
                 if ((logisticsNo == null || "".equals(logisticsNo))) {
                     if ("顺丰速运".equals(logisticsName)) {
                         // 下单请求报文
                         res = new ExpressUtil().createXml(saleorder, cwtype, curr_user.getCompanyId(), btype, apiList, av,
                                 0);
                         if (null == res || res.getCode() != 0) {
                             ResultInfo<?> r = new ResultInfo();
                             r.setMessage(res.getMessage());
                             return r;
                         }
                         xmlStr = (String) res.getData();
                         // 生成快递单号
                         String ExpressXml = CallExpressService.getExpressNo(xmlStr, apiList);
                         expressNo = ExpressUtil.parserXML(ExpressXml);
                     } else if ("中通快递".equals(logisticsName)) {
                         // 获取快递单号请求的json
                         saleorder.setBussinessNo(System.currentTimeMillis() + "");
                         av.setBussinessNo(System.currentTimeMillis() + "");
                         res = new ExpressUtil().createJson(saleorder, ep, cwtype, curr_user.getCompanyId(), btype, av, 0, 1,
                                 map, zotApiList, 0);
                         if (null == res || res.getCode() != 0) {
                             ResultInfo<?> r = new ResultInfo();
                             r.setMessage(res.getMessage());
                             return r;
                         }
                         jsonStr = (String) res.getData();
                         // 生成快递单号
                         try {
                             String ExpressJson = ZopExpressService.getZopInfo(1, jsonStr, zotApiList);

                             logger.info("请求快递号==单号："+saleorder.getSaleorderNo()+"客户名称："+saleorder.getInvoiceTraderName()+av.getInvoiceTraderName()+"接口返回结果:"+ExpressJson);

                             ResultInfo<?> r = ExpressUtil.parserJsonExpressNo(ExpressJson);
                             ResultInfo<?> re = new ResultInfo();
                             if (r == null) {
                                 logger.error("无法解析快递单号"+saleorder.getSaleorderNo()+"\t"+jsonStr+"\t"+ExpressJson);
                                 delInvoiceExpress(invoiceIdArr, invoiceNo, express, curr_user);
                                 re.setMessage("获取单号接口错误"+saleorder.getSaleorderNo());
                                 return re;
                             }
                             expressNo = r.getData().toString();


                         } catch (Exception e) {
                             logger.error("printExpressSf 1:", e);
                         }
                     }
                     express.setLogisticsNo(expressNo);
                     express.setExpressId(expressId);
                     // 将快递单号回写到快递表
                     ep = expressService.updateExpressInfoById(express);
                 }

                 if ((logisticsNo != null && !"".equals(logisticsNo))) {
                     express.setExpressId(expressId);
                     ep = expressService.updateExpressInfoById(express);
                 }


                 ResultInfo<?> resInfo = new ResultInfo<>();
                 try {
                     /************ 打印面单 ***************/
                     if ("顺丰速运".equals(logisticsName)) {
                         logger.info("顺丰快递开始打印");
                         resInfo = new CallWaybillPrinter().WayBillPrinterTools(saleorder, ep, cwtype,
                                 curr_user.getCompanyId(), btype, ip, apiList, av, 0);
                     } else if ("中通快递".equals(logisticsName)) {
                         // 获取大头笔和集散地请求的json
                         res = new ExpressUtil().createJson(saleorder, ep, cwtype, curr_user.getCompanyId(), btype, av, 0, 2,
                                 map, zotApiList, 0);

                         logger.info("请求打印==单号："+saleorder.getSaleorderNo()+"客户名称："+saleorder.getInvoiceTraderName()+av.getInvoiceTraderName()+"接口返回结果:"+res);

                         if (null == res || res.getCode() != 0) {
                             ResultInfo<?> r = new ResultInfo();
                             r.setMessage(res.getMessage());
                             return r;
                         }
                         jsonStr = (String) res.getData();
                         String bigInfoJson = ZopExpressService.getZopInfo(2, jsonStr, zotApiList);
                         ResultInfo<?> r = ExpressUtil.parserJsonMark(bigInfoJson);
                         if (r == null) {
                             if ((logisticsNo == null || "".equals(logisticsNo))) {
                                 delInvoiceExpress(invoiceIdArr, invoiceNo, express, curr_user);
                             }
                             ResultInfo<?> rest = new ResultInfo();
                             rest.setMessage("获取大头笔接口错误");
                             return rest;
                         }
                         map = (Map<String, String>) r.getData();
                         // 云打印快递面单
                         try {
                             // 生成请求打印的json
                             res = new ExpressUtil().createJson(saleorder, ep, cwtype, curr_user.getCompanyId(), btype, av,
                                     0, 3, map, zotApiList, 0);

                             logger.info("请求打印==单号："+saleorder.getSaleorderNo()+"客户名称："+saleorder.getInvoiceTraderName()+av.getInvoiceTraderName()+"接口返回结果:"+res);

                             if (null == res || res.getCode() != 0) {
                                 delInvoiceExpress(invoiceIdArr, invoiceNo, express, curr_user);
                                 ResultInfo<?> ro = new ResultInfo();
                                 ro.setMessage(res.getMessage());
                                 return ro;
                             }
                             jsonStr = (String) res.getData();
                             String ExpressJson = ZopExpressService.getZopInfo(3, jsonStr, zotApiList);
                             ResultInfo<?> rp = ExpressUtil.parserJsonPrint(ExpressJson);
                             if (rp == null) {
                                 if ((logisticsNo == null || "".equals(logisticsNo))) {
                                     delInvoiceExpress(invoiceIdArr, invoiceNo, express, curr_user);
                                 }
                                 ResultInfo<?> rest = new ResultInfo();
                                 rest.setMessage("云打印接口错误");
                                 return rest;
                             }
                             resInfo = new ResultInfo(0, "操作成功");
                         } catch (Exception e) {
                             logger.error("printExpressSf 2:", e);
                         }
                     }
                     if (null == resInfo || resInfo.getCode() != 0) {
                         ResultInfo<?> r = new ResultInfo();
                         r.setMessage(resInfo.getMessage());
                         return r;
                     }
                 } catch (Exception e) {
                     logger.error("printExpressSf 3:", e);
                 }
                 // 同步
                 if (curr_user.getCompanyId().equals(1)) {
                     if (expressId != null && expressId > 0) {
                         vedengSoapService.expressSync(expressId);
                         List<String> invoiceNosList = JSON.parseArray(invoiceNos, String.class);
                         List<Integer> typesList = JSON.parseArray(types, Integer.class);
                         List<Integer> invoiceIdList2 = new ArrayList<>();
                         for(int i=0;i<invoiceNosList.size();i++){
                             // 根据票的NO获取票的详情
                             Invoice ic2 = new Invoice();
                             ic2.setInvoiceNo(invoiceNosList.get(i).toString());//发票号码
                             ic2.setType(typesList.get(i));//开票申请类型
                             Invoice invoice2 = invoiceService.getInvoiceByNo(ic2); //根据单号获取发票信息
                             invoiceIdList2.add(invoice2.getInvoiceId());
                         }
                         List<Invoice> invoiceList2 = invoiceService.getInvoiceListByInvoiceIdList(invoiceIdList2);

                         new Thread() {// 异步推送数据
                             @Override
                             public void run() {
                                 try{
                                     if(invoice.getType() == 505){
                                         Saleorder saleorderInfo = new Saleorder();
                                         saleorderInfo.setSaleorderId(invoice.getRelatedId());
                                         Saleorder saleorderBaseInfo = saleorderService.getBaseSaleorderInfo(saleorderInfo);

                                         if(null == saleorderBaseInfo ) {
                                             return;
                                         }
                                         List<Integer> invoiceIdList = new ArrayList<>();
                                         invoiceIdList.add(invoice.getInvoiceId());
                                         List<Invoice> invoiceList = invoiceService.getInvoiceListByInvoiceIdList(invoiceIdList);


                                         //如果订单是VS，DH，JX
//                                if(saleorderBaseInfo.getOrderType() == 0 || saleorderBaseInfo.getOrderType() == 3 || saleorderBaseInfo.getOrderType() == 4){
//                                    //发票寄送后给对应的用户发微信消息
//                                    ResultInfo frResul = invoiceService.sendWxMessageForInvoice(invoiceList);
//                                }
                                         //贝登发票寄出微信提醒
                                         wxSendMsgForInoice2(invoiceList2);
                                         // 医械购
                                         if(OrderConstant.ORDER_TYPE_HC.equals(saleorderBaseInfo.getOrderType())) {
                                             if(CommonConstants.ONE.equals(sendYxgWxTempMsgFlag)) {
                                                 // add by franlin.wu for [微信模板消息推送 ] at 2019-09-20 begin
                                                 wxSendMsgForInvoice(invoiceList);
                                                 // add by franlin.wu for [微信模板消息推送 ] at 2019-09-20 end

                                             }
                                         }
                                     }

                                 }catch (Exception e){
                                     log.error("============================================消息推送异常",e);
                                 }
                             }
                         }.start();
                     }
                 }

             }
        return new ResultInfo(0, "成功",ep.getLogisticsNo());
    }




    /**
     * <b>Description:</b><br>
     * 删除发票的快递信息
     *
     * @param invoiceIdArr
     * @param invoiceNo
     * @param express
     * @param curr_user
     * @Note <b>Author:</b> scott <br>
     *       <b>Date:</b> 2018年7月3日 上午10:03:19
     */
    private void delInvoiceExpress(String invoiceIdArr, String invoiceNo, Express express, User curr_user) {
        logger.info("delInvoiceExpress:"+invoiceIdArr);
        List<Integer> invoiceIdList = JSON.parseArray(invoiceIdArr, Integer.class);

        List<ExpressDetail> list = new ArrayList<ExpressDetail>();
        if (StringUtils.isBlank(invoiceNo) && invoiceIdList != null && invoiceIdList.size() > 0) {
            for (Integer i : invoiceIdList) {
                ExpressDetail ed = new ExpressDetail();
                ed.setNum(1);
                ed.setBusinessType(SysOptionConstant.ID_497);
                ed.setRelatedId(i);
                list.add(ed);
            }
        }
        express.setExpressDetail(list);
        express.setRelatedIds(invoiceIdList);
        express.setFpNo(invoiceNo);
        ResultInfo<?> resultInfo = invoiceService.updateExpressInvoice(express);
        // 同步
        /*
         * if(resultInfo.getCode().equals(0) &&
         * curr_user.getCompanyId().equals(1)){ Express data = (Express)
         * resultInfo.getData(); if(data.getExpressId() != null &&
         * data.getExpressId() > 0){
         * vedengSoapService.expressSync(data.getExpressId()); } }
         */
    }

    /**
     * <b>Description:</b><br>
     * 售后开票申请列表
     *
     * @param request
     * @return
     * @Note <b>Author:</b> duke <br>
     *       <b>Date:</b> 2018年4月16日 下午4:06:54
     */
    @ResponseBody
    @RequestMapping(value = "/getAfterOpenInvoiceListPage")
    public ModelAndView getAfterOpenInvoiceListPage(HttpServletRequest request, InvoiceApply invoiceApply,
                                                    @RequestParam(required = false, defaultValue = "1") Integer pageNo,
                                                    @RequestParam(required = false) Integer pageSize) {
        ModelAndView mv = new ModelAndView();
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        invoiceApply.setCompanyId(user.getCompanyId());

        // 发票类型
        List<SysOptionDefinition> invoiceTypeList = getSysOptionDefinitionList(SysOptionConstant.ID_428);
        mv.addObject("invoiceTypeList", invoiceTypeList);

        Page page = getPageTag(request, pageNo, pageSize);
        if (invoiceApply.getValidStatus() == null) {
            invoiceApply.setValidStatus(0);// 默认审核中
        }
        invoiceApply.setType(SysOptionConstant.ID_504);// 售后开票
        invoiceApply.setCompanyId(user.getCompanyId());
        Map<String, Object> map = invoiceService.getAfterOpenInvoiceListPage(invoiceApply, page);

        mv.addObject("openInvoiceApplyList", (List<InvoiceApply>) map.get("openInvoiceApplyList"));
        mv.addObject("page", (Page) map.get("page"));
        mv.addObject("userList", map.get("userList"));

        mv.setViewName("finance/invoice/list_after_open_invoice_apply");
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 开票审核弹层页面
     *
     * @Note <b>Author:</b> Michael <br>
     *       <b>Date:</b> 2018年05月30日 下午1:39:42
     */
    @FormToken(save = true)
    @ResponseBody
    @RequestMapping(value = "/complement")
    public ModelAndView complement(HttpSession session, Integer traderCustomerId, String taskId, Boolean pass) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("taskId", taskId);
        mv.addObject("pass", pass);
        mv.addObject("traderCustomerId", traderCustomerId);
        mv.setViewName("finance/invoice/complement");
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 开票审核操作
     *
     * @Note <b>Author:</b> Michael <br>
     *       <b>Date:</b> 2018年05月30日 下午1:39:42
     */
    @FormToken(remove = true)
    @ResponseBody
    @RequestMapping(value = "/complementTask")
    @SystemControllerLog(operationType = "edit", desc = "客户审核操作")
    public ResultInfo<?> complementTask(HttpServletRequest request, Integer traderCustomerId, String taskId,
                                        String comment, Boolean pass, HttpSession session) {
        User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("pass", pass);
        variables.put("updater", user.getUserId());
        try {
            // 如果审核没结束添加审核对应主表的审核状态
            Integer status = 0;
            if (pass) {
                // 如果审核通过
                status = 0;
            } else {
                // 如果审核不通过
                status = 2;
                // 如果审核通过
                TaskService taskService = processEngine.getTaskService();// 获取任务的Service，设置和获取流程变量
                String tableName = (String) taskService.getVariable(taskId, "tableName");
                String id = (String) taskService.getVariable(taskId, "id");
                Integer idValue = (Integer) taskService.getVariable(taskId, "idValue");
                String key = (String) taskService.getVariable(taskId, "key");
                if (tableName != null && id != null && idValue != null && key != null) {
                    actionProcdefService.updateInfo(tableName, id, idValue, key, 2, 2);
                }
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
            logger.error("invoice complementTask:", e);
            return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
        }

    }

    /**
     * 开票记录发送至金蝶 <b>Description:</b><br>
     *
     * @param request
     * @param session
     * @return
     * @throws Exception
     * @Note <b>Author:</b> Bill <br>
     *       <b>Date:</b> 2018年5月30日 下午1:35:55
     */
    @ResponseBody
    @RequestMapping(value = "/sendopeninvoicelist")
    public ResultInfo sendOpenInvoicelist(HttpServletRequest request, HttpSession session) throws Exception {
        ResultInfo resultInfo = new ResultInfo(-1,"操作失败");
        try {
            Page page = getPageTag(request, 1, 1000);
            Invoice invoice = new Invoice();
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            // 获取选取日期的开始时间和结束时间
            invoice.setStartDate(DateUtil.convertLong(startDate + " 00:00:00", ""));
            invoice.setEndDate(DateUtil.convertLong(endDate + " 23:59:59", ""));

            User user = (User) session.getAttribute(Consts.SESSION_USER);
            invoice.setCompanyId(user.getCompanyId());
            invoice.setUserId(user.getUserId());
            resultInfo = invoiceService.sendOpenInvoicelist(invoice, page, session);
        } catch (Exception e) {
            logger.error(Contant.ERROR_MSG, e);
            logger.error("sendOpenInvoicelist:推送金蝶数据失败：" ,e);
        }

        return resultInfo;
    }

    /**
     * 收票记录发送至金蝶 <b>Description:</b><br>
     *
     * @param request
     * @param session
     * @return
     * @throws Exception
     * @Note <b>Author:</b> Bill <br>
     *       <b>Date:</b> 2018年5月30日 下午1:36:08
     */
    @ResponseBody
    @RequestMapping(value = "/sendincomeinvoicelist")
    public ResultInfo sendIncomeInvoiceList(HttpServletRequest request, HttpSession session) throws Exception {
        Page page = getPageTag(request, 1, 1000);
        Invoice invoice = new Invoice();

        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        // 获取当月第一天和最后一天
        invoice.setStartDate(DateUtil.convertLong(startDate + " 00:00:00", ""));// 当月第一天
        invoice.setEndDate(DateUtil.convertLong(endDate + " 23:59:59", ""));

        User user = (User) session.getAttribute(Consts.SESSION_USER);
        invoice.setCompanyId(user.getCompanyId());
        invoice.setUserId(user.getUserId());
        ResultInfo resultInfo = invoiceService.sendIncomeInvoiceList(invoice, page, session);

        return resultInfo;
    }

    /**
     * <b>Description:</b><br>
     * 售后开票申请待审核列表（分公司流程）
     *
     * @param request
     * @param invoiceApply
     * @param pageNo
     * @param pageSize
     * @return
     * @Note <b>Author:</b> duke <br>
     *       <b>Date:</b> 2018年5月30日 下午2:52:31
     */
    @ResponseBody
    @RequestMapping(value = "/getAfterInvoiceApplyVerifyListPage")
    public ModelAndView getAfterInvoiceApplyVerifyListPage(HttpServletRequest request, InvoiceApply invoiceApply,
                                                           @RequestParam(required = false, defaultValue = "1") Integer pageNo,
                                                           @RequestParam(required = false) Integer pageSize) {
        ModelAndView mv = new ModelAndView();
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        invoiceApply.setCompanyId(user.getCompanyId());
        mv.addObject("user", user);

        // 发票类型
        List<SysOptionDefinition> invoiceTypeList = getSysOptionDefinitionList(SysOptionConstant.ID_428);
        mv.addObject("invoiceTypeList", invoiceTypeList);

        Page page = getPageTag(request, pageNo, pageSize);
        if (invoiceApply.getValidStatus() == null) {
            invoiceApply.setValidStatus(0);// 默认审核中
        }
        invoiceApply.setType(SysOptionConstant.ID_504);// 售后开票
        invoiceApply.setCompanyId(user.getCompanyId());
        Map<String, Object> map = invoiceService.getAfterInvoiceApplyVerifyListPage(invoiceApply, page);

        mv.addObject("openInvoiceApplyList", (List<InvoiceApply>) map.get("openInvoiceApplyList"));
        mv.addObject("page", (Page) map.get("page"));
        mv.addObject("userList", map.get("userList"));

        mv.setViewName("finance/invoice/list_after_invoice_apply_verify");
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 推送-开具电子发票
     *
     * @param request
     * @param invoiceApply
     * @return
     * @Note <b>Author:</b> duke <br>
     *       <b>Date:</b> 2018年6月15日 上午9:23:55
     */
    @FormToken(remove = true)
    @ResponseBody
    @RequestMapping(value = "/openEInvoicePush")
    public ResultInfo<?> openEInvoicePush(HttpServletRequest request, InvoiceApply invoiceApply) {
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        if (null != user) {
            invoiceApply.setCompanyId(user.getCompanyId());
            invoiceApply.setUpdater(user.getUserId());
            invoiceApply.setModTime(DateUtil.gainNowDate());
        }
        ResultInfo<?> result = invoiceService.openEInvoicePush(invoiceApply);
        //开票成功改变开据发票状态
        if (result!=null) {
            logger.info("返回状态是否正确");
            if (result.getCode().equals(0)) {
                try {
                    expressService.changeIsinvoicing(invoiceApply.getInvoiceApplyId());
                } catch (Exception e) {
                    /*logger.error(Contant.ERROR_MSG, e);*/
                    logger.error("开据电子票时改变是否开据发票状态失败：", e);
                }

            }
        }
        return result;
    }

    /**
     * 批量开发票
     * @param request

     * @return
     */
    @FormToken(remove = true)
    @ResponseBody
    @RequestMapping(value = "/openEInvoiceBatchPush")
    public ResultInfo<?> openEInvoiceBatchPush(HttpServletRequest request, String invoiceApplyIdArr,InvoiceApply invoiceApply) {

        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        if (null != user) {
            invoiceApply.setCompanyId(user.getCompanyId());
            invoiceApply.setUpdater(user.getUserId());
            invoiceApply.setModTime(DateUtil.gainNowDate());
        }
        ResultInfo<?> result = invoiceService.openEInvoiceBatchPush(invoiceApply,invoiceApplyIdArr);
        return result;
        /*System.out.println(invoiceApplyIdArr);
        List<Integer> invoiceApplyIdList = JSON.parseArray(invoiceApplyIdArr, Integer.class);
        *//*List<String> invoiceApplyIdList=Arrays.asList(invoiceApplyIdArr);*//*
        *//*InvoiceApply invoiceApply=new InvoiceApply();*//*
        System.out.println(invoiceApplyIdList);
        System.out.println(invoiceApplyIdList.size());
        ResultInfo<?> result=null;
        for (Integer invoiceApplyId:invoiceApplyIdList) {
            *//*invoiceApply.setInvoiceApplyId(Integer.parseInt(invoiceApplyId));*//*
            InvoiceApply invoiceApply=invoiceService.getInvoiceApply(invoiceApplyId);
            User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
            if (null != user) {
                invoiceApply.setCompanyId(user.getCompanyId());
                invoiceApply.setUpdater(user.getUserId());
                invoiceApply.setModTime(DateUtil.gainNowDate());
            }
            System.out.println(invoiceApply);
            System.out.println(invoiceApply.getRelatedId());
            result = invoiceService.openEInvoicePush(invoiceApply);

        }
        return result;*/

        /*User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        if (null != user) {
            invoiceApply.setCompanyId(user.getCompanyId());
            invoiceApply.setUpdater(user.getUserId());
            invoiceApply.setModTime(DateUtil.gainNowDate());
        }
        ResultInfo<?> result = invoiceService.openEInvoicePush(invoiceApply);
        return result;*/

    }

    /**
     * <b>Description:</b><br>
     * 电子发票作废
     *
     * @param request
     * @param invoice
     * @return
     * @Note <b>Author:</b> duke <br>
     *       <b>Date:</b> 2018年6月27日 下午3:18:02
     */
    @ResponseBody
    @RequestMapping(value = "/cancelEInvoicePush")
    public ResultInfo<?> cancelEInvoicePush(HttpServletRequest request, Invoice invoice) {
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        if (null != user) {
            invoice.setCompanyId(user.getCompanyId());
            invoice.setCreator(user.getUserId());
            invoice.setAddTime(DateUtil.gainNowDate());
        }
        invoice.setType(SysOptionConstant.ID_505);
        return invoiceService.cancelEInvoicePush(invoice);
    }

    /**
     * <b>Description:</b><br>
     * 电子发票下载
     *
     * @param request
     * @return
     * @Note <b>Author:</b> duke <br>
     *       <b>Date:</b> 2018年6月25日 上午10:23:50
     */
    @ResponseBody
    @RequestMapping(value = "/batchDownEInvoice")
    public ResultInfo<?> batchDownEInvoice(HttpServletRequest request, Invoice invoice) {
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        if (null != user) {
            invoice.setCompanyId(user.getCompanyId());
        }
        invoice.setType(SysOptionConstant.ID_505);
        ResultInfo<?> resultInfo = invoiceService.batchDownEInvoice(invoice);
        if (resultInfo != null && resultInfo.getData() != null) {// 例如：10个待下载，2个下载成功，第三个下载失败，前两个也要推送
            List<Integer> invoiceIdList = (List<Integer>) resultInfo.getData();
            if (invoiceIdList != null && invoiceIdList.size() > 0) {
                for (Integer invoiceId : invoiceIdList) {
                    vedengSoapService.invoiceSync(invoiceId);
                }
                new Thread() {// 异步推送发票信息
                    @Override
                    public void run() {
                        // 根据发票ID查询订单列表，确定是否是耗材商城的销售订单
                        List<Saleorder> saleorderList = saleorderService.getSaleorderListByInvoiceIds(invoiceIdList);
                        if (CollectionUtils.isNotEmpty(saleorderList)) {
                            for (Saleorder saleorder : saleorderList) {
                                // 获取该订单下的发票列表
                                List<Invoice> invoiceList = invoiceService.getInvoiceInfoByRelatedId(
                                        saleorder.getSaleorderId(), SysOptionConstant.ID_505);
                                List<Invoice> invoiceLastList = new ArrayList<Invoice>();// 实际要推送的发票列表
                                for (Invoice invoice : invoiceList) {
                                    if (StringUtil.isNotBlank(invoice.getInvoiceHref())) {// 电子发票下载连接不为空
                                        invoiceLastList.add(invoice);
                                    }
                                }
                                if (CollectionUtils.isNotEmpty(invoiceLastList)) {
                                    Map<String, Object> map = new HashMap<String, Object>();
                                    map.put("saleOrder", saleorder);
                                    map.put("invoiceList", invoiceList);
                                    hcSaleorderService.putInvoicetoHC(map);
                                }
                            }
                        }
                    }
                }.start();
            }
        }
        return resultInfo;
    }

    /**
     * <b>Description:</b><br>
     * 电子票推送短信和邮件页面选择初始化
     *
     * @param request
     * @param invoice
     * @return
     * @Note <b>Author:</b> duke <br>
     *       <b>Date:</b> 2018年8月2日 下午1:41:11
     */
    @FormToken(save = true)
    @ResponseBody
    @RequestMapping(value = "/invoiceSmsAndMailInit")
    public ModelAndView invoiceSmsAndMailInit(HttpServletRequest request, Invoice invoice) {
        ModelAndView mv = new ModelAndView();
        // 获取发票信息
        List<Invoice> invoiceList = invoiceService.getInvoiceInfoByRelatedId(invoice.getRelatedId(),
                SysOptionConstant.ID_505);
        mv.addObject("invoiceList", invoiceList);
        mv.setViewName("finance/invoice/invoice_message_send");
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 电子票推送短信和邮箱
     *
     * @param request
     * @param invoice
     * @return
     * @Note <b>Author:</b> duke <br>
     *       <b>Date:</b> 2018年7月20日 下午6:42:37
     */
    @FormToken(remove = true)
    @ResponseBody
    @RequestMapping(value = "/sendInvoiceSmsAndMail")
    public ResultInfo<?> sendInvoiceSmsAndMail(HttpServletRequest request, Invoice invoice,
                                               @RequestParam(required = false, value = "invoiceIdArr") String invoiceIdArr) {
        List<Integer> invoiceIdList = JSON.parseArray(invoiceIdArr, Integer.class);
        if (invoiceIdList == null || invoiceIdList.size() == 0) {
            return new ResultInfo<>(-1, "参数错误");
        }
        invoice.setInvoiceIdList(invoiceIdList);
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        if (null != user) {
            invoice.setCompanyId(user.getCompanyId());
        }
        if (invoice != null && invoice.getInvoiceIdList() != null) {
            for (int i = 0; i < invoice.getInvoiceIdList().size(); i++) {
                if (invoice.getInvoiceIdList().get(i).intValue() > 0) {
                    vedengSoapService.invoiceSync(invoice.getInvoiceIdList().get(i));
                }
            }
        }
        return invoiceService.sendInvoiceSmsAndMail(invoice);
    }

    /* *
     * 功能描述: 查询所有集中开票客户
     * @param: [request]
     * @return: org.springframework.web.servlet.ModelAndView
     * @auther: duke.li
     * @date: 2019/4/12 9:43
     */
    @ResponseBody
    @RequestMapping(value = "/getCollectInvoiceTraderName")
    public ModelAndView getCollectInvoiceTraderName(HttpServletRequest request,@RequestParam(required = false, value = "traderName") String traderName) {
        ModelAndView mv = new ModelAndView();
        // 获取发票信息
        List<TraderCustomerVo> traderCustomerList = invoiceService.getCollectInvoiceTraderName(traderName);
        mv.addObject("traderCustomerList", traderCustomerList);
        mv.addObject("traderName", EmptyUtils.isNotBlank(traderName)?traderName:"");
        mv.setViewName("finance/invoice/collect_invoice_traderName");
        return mv;
    }


    /* *
     * 功能描述: 添加集中开票初始化
     * @param: [request]
     * @return: org.springframework.web.servlet.ModelAndView
     * @auther: duke.li
     * @date: 2019/4/12 10:03
     */
    @ResponseBody
    @RequestMapping(value = "/batchCollectTraderInit")
    public ModelAndView batchCollectTraderInit(HttpServletRequest request) {
        return new ModelAndView("finance/invoice/batch_collect_trader");
    }


    /* *
     * 功能描述: 批量删除集中开票客户
     * @param: [request, traderCustomerVo, traderCustomerIdArr]
     * @return: com.vedeng.common.model.ResultInfo<?>
     * @auther: duke.li
     * @date: 2019/4/12 17:05
     */
    @ResponseBody
    @RequestMapping(value = "/delCollectInvoiceTrader")
    public ResultInfo<?> delCollectInvoiceTrader(HttpServletRequest request, TraderCustomerVo traderCustomerVo,
                                                 @RequestParam("traderCustomerIdArr") String traderCustomerIdArr) {
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        if (null != user) {
            traderCustomerVo.setCompanyId(user.getCompanyId());
            traderCustomerVo.setUpdater(user.getUserId());
            traderCustomerVo.setModTime(DateUtil.gainNowDate());
        }
        List<Integer> traderCustomerIdList = JSON.parseArray(traderCustomerIdArr, Integer.class);
        traderCustomerVo.setTraderList(traderCustomerIdList);
        return invoiceService.delCollectInvoiceTrader(traderCustomerVo);
    }

    /* *
     * 功能描述: 集中开票客户导入
     * @param: [request, lwfile]
     * @return: com.vedeng.common.model.ResultInfo<?>
     * @auther: duke.li
     * @date: 2019/4/12 13:03
     */
    @ResponseBody
    @RequestMapping("batchCollectTraderSave")
    @SystemControllerLog(operationType = "import", desc = "导入集中开票客户")
    public ResultInfo<?> batchCollectTraderSave(HttpServletRequest request, @RequestParam("lwfile") MultipartFile lwfile) {
        ResultInfo<?> resultInfo = new ResultInfo<>();
        try {
            User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
            // 临时文件存放地址
            String path = request.getSession().getServletContext().getRealPath("/upload/trader");
            FileInfo fileInfo = ftpUtilService.fileUploadServe(path, lwfile);
            if (fileInfo.getCode() == 0) {
                List<Trader> list = new ArrayList<>();
                // 获取excel路径
                Workbook workbook = WorkbookFactory.create(new FileInputStream(new File(fileInfo.getFilePath())));
                // 获取第一面sheet
                Sheet sheet = workbook.getSheetAt(0);
                // 起始行
                int startRowNum = sheet.getFirstRowNum() + 1;
                int endRowNum = sheet.getLastRowNum();// 结束行

                for (int rowNum = startRowNum; rowNum <= endRowNum; rowNum++) {// 循环行数
                    Row row = sheet.getRow(rowNum);
                    if(row == null){
                        resultInfo.setMessage("第:" + (rowNum + 1) + "行，第:1列数据不符合要求，请验证！");
                        throw new Exception("第" + (rowNum + 1) + "行，第:1列数据不符合要求！");
                    }
                    int startCellNum = row.getFirstCellNum();// 起始列
                    int endCellNum = row.getLastCellNum() - 1;// 结束列
                    // 获取excel的值
                    Trader trader = new Trader();
                    if (user != null) {
                        trader.setCompanyId(user.getCompanyId());
                        trader.setModTime(DateUtil.gainNowDate());
                        trader.setUpdater(user.getUserId());
                    }
                    for (int cellNum = startCellNum; cellNum <= endCellNum; cellNum++) {// 循环列数（下表从0开始）
                        Cell cell = row.getCell(cellNum);

                        if (cellNum == 0) {// 第一列数据cellNum==startCellNum
                            // 若excel中无内容，而且没有空格，cell为空--默认3，空白
                            if (cell == null || cell.getCellType() != 1) {
                                resultInfo.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列非字符类型，请验证！");
                                throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列非字符类型，请验证！");
                            } else {
                                trader.setTraderName(cell.getStringCellValue());
                            }
                        }

                        // null值判断
                        if (EmptyUtils.isBlank(trader.getTraderName())) {
                            resultInfo.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列非字符类型，请验证！");
                            throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列非字符类型，请验证！");
                        } else {
                            trader.setTraderName(trader.getTraderName().trim());
                        }
                    }
                    list.add(trader);
                }
                // 保存更新
                resultInfo = invoiceService.updateCollectInvoiceTrader(list);
            }
        } catch (Exception e) {
            logger.error("batchCollectTraderSave:", e);
            return resultInfo;
        }
        return resultInfo;
    }

    /* *
     * 功能描述: 修改发票申请标记状态
     * @param: [request, invoiceApply, invoiceApplyIdArr]
     * @return: com.vedeng.common.model.ResultInfo<?>
     * @auther: duke.li
     * @date: 2019/4/13 14:47
     */
    @ResponseBody
    @RequestMapping(value = "/updateInvoiceApplySign")
    public ResultInfo<?> updateInvoiceApplySign(HttpServletRequest request, InvoiceApply invoiceApply,
                                                @RequestParam("invoiceApplyIdArr") String invoiceApplyIdArr) {
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        if (null != user) {
            invoiceApply.setCompanyId(user.getCompanyId());
            invoiceApply.setUpdater(user.getUserId());
            invoiceApply.setModTime(DateUtil.gainNowDate());
        }
        List<Integer> invoiceApplyIdList = JSON.parseArray(invoiceApplyIdArr, Integer.class);
        invoiceApply.setInvoiceApplyIdList(invoiceApplyIdList);
        return invoiceService.updateInvoiceApplySign(invoiceApply);
    }

    /**
     * <b>Description: 医械购 发票微信 推送模板消息</b>
     * @param invoList   请求url
     * <b>Author：</b> franlin.wu
     * <b>Date:</b> 2019年6月19日
     * @return
     */
    protected void wxSendMsgForInvoice(List<Invoice> invoList) {
        logger.info("wxSendMsgForInvoice | invoList :{}", invoList);
        // add by franlin.wu for [微信模板消息推送 ] at 2019-09-20 begin
        if(CollectionUtils.isNotEmpty(invoList)) {
            for(Invoice invo : invoList) {
                // 非医械购 发票
                if(null == invo || !OrderConstant.ORDER_TYPE_HC.equals(invo.getOrderType())) {
                    continue;
                }
                // 订单联系人
                String traderConMobile = invo.getTraderContactMobile();
                logger.info("wxSendMsgForInvoice | traderConMobile:{}", traderConMobile);
                // 订单联系人 为空
                if(EmptyUtils.isBlank(traderConMobile)) {
                    continue;
                }
                ReqTemplateVariable reqTemp = new ReqTemplateVariable();
                // 订单客户联系人
                reqTemp.setPhoneNumber(traderConMobile);
                // 模板消息数字字典Id  发票寄出 提醒
                reqTemp.setTemplateId(WeChatSendTemplateUtil.TEMPLATE_INVOICE_REMINDER);

                // 头
                TemplateVar first = new TemplateVar();
                String firstStr = getConfigStringByDefault("尊敬的客户，您的发票已寄出，请注意查收。", SysOptionConstant.ID_TEMPLATE_INVOICE_REMINDER_FIRST);
                first.setValue(firstStr + "\r\n");
                TemplateVar remark = new TemplateVar();
                String remarkStr = getConfigStringByDefault("感谢您对医械购的支持与信任，如有疑问请联系：18651638763", SysOptionConstant.ID_WECHAT_TEMPLATE_REMARK);
                remark.setValue(remarkStr);

                TemplateVar keyword1 = new TemplateVar();
                TemplateVar keyword2 = new TemplateVar();
                TemplateVar keyword3 = new TemplateVar();
                TemplateVar keyword4 = new TemplateVar();

                keyword1.setValue(invo.getTraderName());
                keyword2.setValue(null == invo.getAmount() ? "0.00" : invo.getAmount().toString() + "元");
                String invoiceNo = invo.getInvoiceNo();
                // 物流公司 + 快递单号
                keyword3.setValue(invo.getLogisticsName() + " " + invo.getLogisticsNo() + "\r\n包含发票号:" + invoiceNo + "\r\n");
                keyword4.setValue(invoiceNo);

                reqTemp.setFirst(first);
                reqTemp.setKeyword1(keyword1);
                reqTemp.setKeyword2(keyword2);
                reqTemp.setKeyword3(keyword3);
                reqTemp.setKeyword4(keyword4);
                reqTemp.setRemark(remark);
                // 发票寄出 提醒
                WeChatSendTemplateUtil.sendTemplateMsg(apiUrl + ApiUrlConstant.API_WECHAT_SEND_TEMPLATE_MSG, reqTemp);
            }
        }
    }

    /**
     * 批量标记导表格
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/uplodebatchsign")
    public ModelAndView uplodeBatchCustomer(HttpServletRequest request) {

        ModelAndView mv = new ModelAndView("finance/invoice/uplode_batch_sign");
        return mv;
    }

    /**
     * 批量导入标记数据
     * @param request
     * @param lwfile
     * @return
     */
    @ResponseBody
    @RequestMapping("saveuplodebatchsign")
    @SystemControllerLog(operationType = "import", desc = "导入批量标记数据")
    public ResultInfo<?> saveUplodeTaxCategoryNo(HttpServletRequest request,
                                                 @RequestParam("lwfile") MultipartFile lwfile) {


        ResultInfo<?> resultInfo = new ResultInfo<>();
        InputStream inputStream=null;
       try {
           logger.info("开始批量标记");
            User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
            // 临时文件存放地址
            String path = request.getSession().getServletContext().getRealPath("/upload/signs");
            FileInfo fileInfo = ftpUtilService.fileUploadServe(path, lwfile);
            if (fileInfo.getCode() == 0) {
                // 获取excel路径
                inputStream = new FileInputStream(new File(fileInfo.getFilePath()));
                Workbook workbook = WorkbookFactory.create(inputStream);
                // 获取第一面sheet
                Sheet sheet = workbook.getSheetAt(0);

                // 起始行
                int startRowNum = sheet.getFirstRowNum() + 1;
                int endRowNum = sheet.getLastRowNum();// 结束行
                if (endRowNum<1){
                    resultInfo.setCode(0);
                    resultInfo.setMessage("表格数据为空，操作失败");
                }else {
                    Integer sum = 0;
                    //表格符合界面的订单
                    List allSaleOrderNoSuccess = new ArrayList();
                    //表格不符合界面的订单
                    List allSaleOrderNoFaile = new ArrayList();
                    //界面所有的订单对象集合
                    List<InvoiceApply> allSaleInvoiceApplyList = invoiceService.getAllSaleInvoiceApplyList();
                    //面所有的订单号集合
                    List saleOrderNo = new ArrayList();
                    //所有的开票申请id
                    List invoiveApplyIdList = new ArrayList<>();
                    for (int i = 0; i < allSaleInvoiceApplyList.size(); i++) {
                        saleOrderNo.add(allSaleInvoiceApplyList.get(i).getSaleorderNo());
                        invoiveApplyIdList.add(allSaleInvoiceApplyList.get(i).getInvoiceApplyId());
                    }
                    for (int rowNum = startRowNum; rowNum <= endRowNum; rowNum++) {// 循环行数
                        Row row = sheet.getRow(rowNum);
                        for (int cellNum = 0; cellNum <= 0; cellNum++) {// 循环列数（下表从0开始）
                            //取出单元格数据
                            if (row != null) {
                                Cell cell = row.getCell(cellNum);
                                if (cell != null) {
                                    if (cell.getCellType() == 0) {
                                        DecimalFormat decimalFormat = new DecimalFormat("#########################################################.##############################");
                                        allSaleOrderNoFaile.add(decimalFormat.format(cell.getNumericCellValue()));
                                    }
                                    if (cell != null && cell.getCellType() != 3 && cell.getCellType() != 0) {
                                        //单元格数据满足条件，添加至allSaleOrderNoSuccess，不满足条件添加至allSaleOrderNoFaile
                                        if (saleOrderNo.contains(cell.getStringCellValue())) {
                                            sum++;
                                            allSaleOrderNoSuccess.add(cell.getStringCellValue());
                                        } else {
                                            allSaleOrderNoFaile.add(cell.getStringCellValue());
                                        }
                                    }
                                }
                                //
                            }
                        }
                    }
                    //如果都不满足条件则全部显示失败
                    if (sum.equals(0)) {
                        allSaleOrderNoFaile = (List) allSaleOrderNoFaile.stream().distinct().collect(Collectors.toList());
                        //用于拼接字符串，没有意义
                        String s = "";
                        for (int o = 0; o < allSaleOrderNoFaile.size(); o++) {
                            s = s  + allSaleOrderNoFaile.get(o) + ";";
                        }
                        //去掉截取字符串最后一个字符
                        if (s.length()>0) {
                            s = s.substring(0, s.length() - 1);
                        }
                        resultInfo.setCode(0);
                        resultInfo.setMessage("以下订单不存在: " + s);
                        /*throw new Exception("全部不符合！"+q);*/
                    } else if (0 < sum && sum < endRowNum) {
                        allSaleOrderNoSuccess = (List) allSaleOrderNoSuccess.stream().distinct().collect(Collectors.toList());
                        Integer count = allSaleOrderNoSuccess.size();
                        allSaleOrderNoFaile = (List) allSaleOrderNoFaile.stream().distinct().collect(Collectors.toList());
                        //用于拼接字符串，没有意义
                        String s = "";
                        for (int o = 0; o < allSaleOrderNoFaile.size(); o++) {
                            s = s  + allSaleOrderNoFaile.get(o) + ";";
                        }
                        //去掉截取字符串最后一个字符
                        if (s.length()>0) {
                            s = s.substring(0, s.length() - 1);
                        }
                        //根据查询的开票申请与界面比较获得界面有的开票申请
                        List<Integer> invoiceApplyIdsList = invoiceService.getInvoiceApplyIdsBySaleOrderIds(allSaleOrderNoSuccess);
                            /*for (int i = 0; i <invoiceApplyIdsList.size() ; i++) {
                                System.out.println(invoiceApplyIdsList.get(i)+"qqqqqqqqqqqqqqqqqqqqqqqqqqq");
                            }*/
                        //根据查询的开票申请与界面比较获得界面有的开票申请
                        List<Integer> endInvoiceApplyIds = new ArrayList<>();
                        for (Integer integer : invoiceApplyIdsList) {
                            if (invoiveApplyIdList.contains(integer)) {
                                endInvoiceApplyIds.add(integer);
                            }
                        }
                        //改变标记状态
                        int res = invoiceService.changeIsSign(endInvoiceApplyIds);
                        resultInfo.setCode(0);
                        resultInfo.setMessage("已成功提交" + count + "个订单，" + "以下订单不存在: " + s);
                    } else if (sum == endRowNum) {
                        //根据表格提交的数据查询界面含有的开票申请
                        //根据表格符合界面的订单号查询开票申请
                        List<Integer> invoiceApplyIdsList = invoiceService.getInvoiceApplyIdsBySaleOrderIds(allSaleOrderNoSuccess);
                        /*for (int i = 0; i < invoiceApplyIdsList.size(); i++) {
                            System.out.println(invoiceApplyIdsList.get(i) + "qqqqqqqqqqqqqqqqqqqqqqqqqqq");
                        }*/
                        //根据查询的开票申请与界面比较获得界面有的开票申请
                        List<Integer> endInvoiceApplyIds = new ArrayList<>();
                        for (Integer integer : invoiceApplyIdsList) {
                            if (invoiveApplyIdList.contains(integer)) {
                                endInvoiceApplyIds.add(integer);
                            }
                        }
                        //改变标记状态
                        int res = invoiceService.changeIsSign(endInvoiceApplyIds);
                        resultInfo.setCode(0);
                        resultInfo.setMessage("提交成功!!!");
                    }
                }
            }
       }catch (Exception e){
            return resultInfo;
        }finally {
           try {
               inputStream.close();
           } catch (IOException e) {
               logger.error("关闭批量结款流出现异常:", e);
           }
       }

        return resultInfo;
    }
}
