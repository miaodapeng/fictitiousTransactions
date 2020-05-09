package com.vedeng.finance.controller;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.vedeng.logistics.service.WarehouseStockService;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.activiti.service.ActionProcdefService;
import com.vedeng.aftersales.model.AfterSales;
import com.vedeng.aftersales.model.vo.AfterSalesDetailVo;
import com.vedeng.aftersales.model.vo.AfterSalesVo;
import com.vedeng.aftersales.service.AfterSalesService;
import com.vedeng.authorization.model.Company;
import com.vedeng.authorization.model.Organization;
import com.vedeng.authorization.model.User;
import com.vedeng.common.annotation.SystemControllerLog;
import com.vedeng.common.constant.DbRestInterfaceConstant;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.exception.ShowErrorMsgException;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.DigitToChineseUppercaseNumberUtils;
import com.vedeng.common.util.HttpRestClientUtil;
import com.vedeng.common.validator.FormToken;
import com.vedeng.finance.model.BankBill;
import com.vedeng.finance.model.CapitalBill;
import com.vedeng.finance.model.CapitalBillDetail;
import com.vedeng.finance.model.PayApply;
import com.vedeng.finance.model.PayApplyDetail;
import com.vedeng.finance.service.BankBillService;
import com.vedeng.finance.service.CapitalBillService;
import com.vedeng.finance.service.InvoiceAfterService;
import com.vedeng.finance.service.PayApplyService;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.service.BuyorderService;
import com.vedeng.order.service.SaleorderService;
import com.vedeng.soap.service.VedengSoapService;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.system.service.CompanyService;
import com.vedeng.system.service.OrgService;
import com.vedeng.system.service.UserService;
import com.vedeng.system.service.VerifiesRecordService;

/**
 * <b>Description:</b><br>
 * 资金流水管理
 * 
 * @author leo.yang
 * @Note <b>ProjectName:</b> erp <br>
 *       <b>PackageName:</b> com.vedeng.finance.controller <br>
 *       <b>ClassName:</b> CapitalBillController <br>
 *       <b>Date:</b> 2017年9月12日 下午5:36:39
 */
@Controller
@RequestMapping("/finance/capitalbill")
public class CapitalBillController extends BaseController {

    @Autowired
    @Qualifier("capitalBillService")
    private CapitalBillService capitalBillService;// 自动注入capitalBillService

    @Autowired
    @Qualifier("saleorderService")
    private SaleorderService saleorderService;

    @Autowired
    @Qualifier("buyorderService")
    private BuyorderService buyorderService;

    @Autowired
    @Qualifier("userService")
    private UserService userService;// 自动注入userService

    @Autowired
    @Qualifier("orgService")
    private OrgService orgService;// 部门

    @Autowired
    @Qualifier("companyService")
    private CompanyService companyService;

    @Autowired
    @Qualifier("payApplyService")
    private PayApplyService payApplyService;

    @Autowired
    @Qualifier("bankBillService")
    private BankBillService bankBillService;

    @Autowired
    @Qualifier("actionProcdefService")
    private ActionProcdefService actionProcdefService;

    @Autowired
    @Qualifier("verifiesRecordService")
    private VerifiesRecordService verifiesRecordService;

    @Autowired
    @Qualifier("vedengSoapService")
    private VedengSoapService vedengSoapService;

    @Autowired
    @Qualifier("invoiceAfterService")
    private InvoiceAfterService invoiceAfterService;// 自动注入invoiceAfterService

    @Resource
    private AfterSalesService afterSalesOrderService;

    @Autowired
    private WarehouseStockService warehouseStockService;

    /**
     * <b>Description:</b><br>
     * 资金流水列表（分页）
     * 
     * @param request
     * @param capitalBill
     * @param session
     * @param pageNo
     * @param pageSize
     * @return
     * @Note <b>Author:</b> leo.yang <br>
     *       <b>Date:</b> 2017年9月12日 下午5:50:17
     */
    @ResponseBody
    @RequestMapping(value = "getCapitalBillListPage")
    public ModelAndView getCapitalBillListPage(HttpServletRequest request, CapitalBill capitalBill, HttpSession session,
            @RequestParam(required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(required = false) Integer pageSize) {
        ModelAndView mv = new ModelAndView();
        Page page = getPageTag(request, pageNo, pageSize);
        try {
            // 获取当前日期
            Date date = new Date();
            String nowDate = DateUtil.DatePreMonth(date, 0, null);
            mv.addObject("nowDate", nowDate);
            // 获取前一个月日期
            String pre1MonthDate = DateUtil.DatePreMonth(date, -1, null);
            mv.addObject("pre1MonthDate", pre1MonthDate);

            if (null != request.getParameter("searchBegintimeStr")
                    && request.getParameter("searchBegintimeStr") != "") {
                capitalBill.setSearchBegintime(DateUtil
                        .convertLong(request.getParameter("searchBegintimeStr") + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
            }
            else {
                capitalBill.setSearchBegintime(DateUtil.convertLong(pre1MonthDate + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
            }
            if (null != request.getParameter("searchEndtimeStr") && request.getParameter("searchEndtimeStr") != "") {
                capitalBill.setSearchEndtime(DateUtil.convertLong(request.getParameter("searchEndtimeStr") + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
            }
            else {
                capitalBill.setSearchEndtime(DateUtil.convertLong(nowDate + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
            }
            if ("".equals(request.getParameter("searchBegintimeStr"))) {
                capitalBill.setSearchBegintime(null);
            }
            if ("".equals(request.getParameter("searchEndtimeStr"))) {
                capitalBill.setSearchEndtime(null);
            }

            // 交易方式
            List<SysOptionDefinition> traderModeList = getSysOptionDefinitionList(519);

            // 业务类型
            List<SysOptionDefinition> bussinessTypeList = getSysOptionDefinitionList(524);

            User user = (User) session.getAttribute(Consts.SESSION_USER);

            capitalBill.setIsCapitalBillTotal(1);// 查询收入总额,支出总额
            capitalBill.setCompanyId(user.getCompanyId());
            Map<String, Object> map = capitalBillService.getCapitalBillListPage(request, capitalBill, page);
            if (map != null) {
                List<CapitalBill> list = (List<CapitalBill>) map.get("capitalBillList");
                mv.addObject("capitalBillList", list);
            }

            mv.addObject("page", (Page) map.get("page"));
            mv.addObject("traderModeList", traderModeList);
            mv.addObject("bussinessTypeList", bussinessTypeList);
            mv.addObject("capitalBill", (CapitalBill) map.get("capitalBill"));
        }
        catch (Exception e) {
            logger.error("getCapitalBillListPage:", e);
        }
        mv.setViewName("finance/capitalbill/index_capitalbill");
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 销售订单添加交易记录初始页面
     * 
     * @param request
     * @param saleorder
     * @return
     * @Note <b>Author:</b> leo.yang <br>
     *       <b>Date:</b> 2017年9月13日 下午5:37:23
     */
    @FormToken(save = true)
    @ResponseBody
    @RequestMapping(value = "/addCapitalBill")
    public ModelAndView addCapitalBill(HttpServletRequest request, Saleorder saleorder) {
        ModelAndView mv = new ModelAndView("finance/capitalbill/add_capital_bill");
        // 获取订单基本信息
        saleorder = saleorderService.getBaseSaleorderInfo(saleorder);
        mv.addObject("saleorder", saleorder);

        mv.addObject("bussinessTypeList", getSysOptionDefinitionList(524));
        mv.addObject("traderModeList", getSysOptionDefinitionList(519));
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 支付宝提现资金流水添加初始化页面
     * 
     * @param request
     * @param saleorder
     * @return
     * @Note <b>Author:</b> duke <br>
     *       <b>Date:</b> 2018年4月16日 上午10:51:34
     */
    @FormToken(save = true)
    @ResponseBody
    @RequestMapping(value = "/addAlipayCapitalBill")
    public ModelAndView addAlipayCapitalBill(HttpServletRequest request, Saleorder saleorder) {
        ModelAndView mv = new ModelAndView("finance/capitalbill/add_alipay_capital_bill");
        // 获取订单基本信息
        saleorder = saleorderService.getBaseSaleorderInfo(saleorder);
        mv.addObject("saleorder", saleorder);

        mv.addObject("bussinessTypeList", getSysOptionDefinitionList(524));
        mv.addObject("traderModeList", getSysOptionDefinitionList(519));
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 保存支付宝提现资金流水记录
     * 
     * @param request
     * @param capitalBill
     * @return
     * @Note <b>Author:</b> duke <br>
     *       <b>Date:</b> 2018年4月16日 下午1:04:38
     */
    @FormToken(remove = true)
    @ResponseBody
    @RequestMapping(value = "/saveAlipayCapitalBill")
    @SystemControllerLog(operationType = "add", desc = "保存新增的资金流水")
    public ResultInfo<?> saveAlipayCapitalBill(HttpServletRequest request, CapitalBill capitalBill) {
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        if (user != null) {
            capitalBill.setCreator(user.getUserId());
            capitalBill.setAddTime(DateUtil.sysTimeMillis());
            capitalBill.setCompanyId(user.getCompanyId());
            capitalBill.setPayee(user.getCompanyName());
            // capitalBill.setPayer(user.getCompanyName());
        }
        // 归属销售
        User belongUser = new User();
        if (capitalBill.getCapitalBillDetail().getTraderId() != null) {
            if (capitalBill.getCapitalBillDetail().getOrderType() == 1) {
                belongUser = userService.getUserByTraderId(capitalBill.getCapitalBillDetail().getTraderId(), 1);// 1客户，2供应商
                if (belongUser != null && belongUser.getUserId() != null) {
                    belongUser = userService.getUserById(belongUser.getUserId());
                }
            }
            else if (capitalBill.getCapitalBillDetail().getOrderType() == 2) {
                belongUser = userService.getUserByTraderId(capitalBill.getCapitalBillDetail().getTraderId(), 2);// 1客户，2供应商
                if (belongUser != null && belongUser.getUserId() != null) {
                    belongUser = userService.getUserById(belongUser.getUserId());
                }
            }
        }

        capitalBill.setCurrencyUnitId(1);
        capitalBill.setAmount(capitalBill.getAmount().multiply(new BigDecimal(-1)));// 支付宝提现金额作负数
        capitalBill.setTraderTime(DateUtil.sysTimeMillis());
        capitalBill.setTraderType(capitalBill.getTraderType() == null ? 2 : capitalBill.getTraderType());// 默認支出

        CapitalBillDetail capitalBillDetail = new CapitalBillDetail();
        capitalBillDetail.setAmount(capitalBill.getAmount());
        capitalBillDetail.setBussinessType(capitalBill.getCapitalBillDetail().getBussinessType());
        capitalBillDetail.setOrderType(capitalBill.getCapitalBillDetail().getOrderType());
        capitalBillDetail.setRelatedId(capitalBill.getCapitalBillDetail().getRelatedId());
        capitalBillDetail.setOrderNo(capitalBill.getCapitalBillDetail().getOrderNo());
        capitalBillDetail.setTraderId(capitalBill.getCapitalBillDetail().getTraderId());
        capitalBillDetail.setTraderType(capitalBill.getCapitalBillDetail().getTraderType());
        // capitalBillDetail.setUserId(capitalBill.getCapitalBillDetail().getUserId());
        if (belongUser != null) {
            capitalBillDetail.setOrgName(belongUser.getOrgName());
            capitalBillDetail.setOrgId(belongUser.getOrgId());
            capitalBillDetail.setUserId(belongUser.getUserId());
        }
        // capitalBillDetails.add(capitalBillDetail);
        capitalBill.setCapitalBillDetail(capitalBillDetail);
        return capitalBillService.saveCapitalBill(capitalBill);
    }

    /**
     * <b>Description:</b><br>
     * 保存新增的资金流水
     * 
     * @param request
     * @param capitalBill
     * @return
     * @Note <b>Author:</b> leo.yang <br>
     *       <b>Date:</b> 2017年9月13日 下午5:48:07
     */
    @FormToken(remove = true)
    @ResponseBody
    @RequestMapping(value = "/saveAddCapitalBill")
    @SystemControllerLog(operationType = "add", desc = "保存新增的资金流水")
    public ResultInfo<?> saveAddCapitalBill(HttpServletRequest request, CapitalBill capitalBill) {
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        if (user != null) {
            capitalBill.setCreator(user.getUserId());
            capitalBill.setAddTime(DateUtil.sysTimeMillis());
            capitalBill.setCompanyId(user.getCompanyId());
        }
        // 归属销售
        User belongUser = new User();
        if (capitalBill.getCapitalBillDetail().getTraderId() != null) {
            if (capitalBill.getCapitalBillDetail().getOrderType() == 1) {
                belongUser = userService.getUserByTraderId(capitalBill.getCapitalBillDetail().getTraderId(), 1);// 1客户，2供应商
                if (belongUser != null && belongUser.getUserId() != null) {
                    belongUser = userService.getUserById(belongUser.getUserId());
                }
            }
            else if (capitalBill.getCapitalBillDetail().getOrderType() == 2) {
                belongUser = userService.getUserByTraderId(capitalBill.getCapitalBillDetail().getTraderId(), 2);// 1客户，2供应商
                if (belongUser != null && belongUser.getUserId() != null) {
                    belongUser = userService.getUserById(belongUser.getUserId());
                }
            }
            else if (capitalBill.getCapitalBillDetail().getOrderType() == 3) {
                // 售后类型，需要从售后单查是关联采购还是销售
                AfterSales afterSales = new AfterSales();
                afterSales.setAfterSalesId(capitalBill.getCapitalBillDetail().getRelatedId());
                afterSales.setCompanyId(user.getCompanyId());
                AfterSalesVo afterSalesVo = afterSalesOrderService.getAfterSalesVoListById(afterSales);
                if (afterSalesVo.getSubjectType() == 535) {
                    // 销售
                    belongUser = userService.getUserByTraderId(capitalBill.getCapitalBillDetail().getTraderId(), 1);// 1客户，2供应商
                    if (belongUser != null && belongUser.getUserId() != null) {
                        belongUser = userService.getUserById(belongUser.getUserId());
                    }
                }
                else if (afterSalesVo.getSubjectType() == 536) {
                    // 采购
                    belongUser = userService.getUserByTraderId(capitalBill.getCapitalBillDetail().getTraderId(), 2);// 1客户，2供应商
                    if (belongUser != null && belongUser.getUserId() != null) {
                        belongUser = userService.getUserById(belongUser.getUserId());
                    }
                }

            }

        }
        try {
            capitalBill.setCurrencyUnitId(1);
            capitalBill.setTraderTime(DateUtil.sysTimeMillis());
            capitalBill.setTraderType(capitalBill.getTraderType() == null ? 1 : capitalBill.getTraderType());// 默認收入

            // 获取当前用户公司信息
            Company companyInfo = companyService.getCompanyByCompangId(user.getCompanyId());
            capitalBill.setPayee(companyInfo.getCompanyName());

            List<CapitalBillDetail> capitalBillDetails = new ArrayList<>();
            CapitalBillDetail capitalBillDetail2 = new CapitalBillDetail();
            capitalBillDetail2.setAmount(capitalBill.getAmount());
            capitalBillDetail2.setBussinessType(capitalBill.getCapitalBillDetail().getBussinessType());
            capitalBillDetail2.setOrderType(capitalBill.getCapitalBillDetail().getOrderType());
            capitalBillDetail2.setRelatedId(capitalBill.getCapitalBillDetail().getRelatedId());
            capitalBillDetail2.setOrderNo(capitalBill.getCapitalBillDetail().getOrderNo());
            capitalBillDetail2.setTraderId(capitalBill.getCapitalBillDetail().getTraderId());
            capitalBillDetail2.setTraderType(capitalBill.getCapitalBillDetail().getTraderType());
            capitalBillDetail2.setUserId(capitalBill.getCapitalBillDetail().getUserId());
            if (belongUser != null) {
                capitalBillDetail2.setOrgName(belongUser.getOrgName());
                capitalBillDetail2.setOrgId(belongUser.getOrgId());
            }
            capitalBillDetails.add(capitalBillDetail2);
            capitalBill.setCapitalBillDetails(capitalBillDetails);

            CapitalBillDetail capitalBillDetailInfo = new CapitalBillDetail();
            capitalBillDetailInfo.setAmount(capitalBill.getAmount());
            capitalBillDetailInfo.setBussinessType(capitalBill.getCapitalBillDetail().getBussinessType());
            capitalBillDetailInfo.setOrderType(capitalBill.getCapitalBillDetail().getOrderType());
            capitalBillDetailInfo.setRelatedId(capitalBill.getCapitalBillDetail().getRelatedId());
            capitalBillDetailInfo.setOrderNo(capitalBill.getCapitalBillDetail().getOrderNo());
            capitalBillDetailInfo.setTraderId(capitalBill.getCapitalBillDetail().getTraderId());
            capitalBillDetailInfo.setTraderType(capitalBill.getCapitalBillDetail().getTraderType());
            capitalBillDetailInfo.setUserId(capitalBill.getCapitalBillDetail().getUserId());
            if (belongUser != null) {
                capitalBillDetailInfo.setOrgName(belongUser.getOrgName());
                capitalBillDetailInfo.setOrgId(belongUser.getOrgId());
            }
            capitalBill.setCapitalBillDetail(capitalBillDetailInfo);

            ResultInfo<?> saveAddCapitalBill = capitalBillService.saveAddCapitalBill(capitalBill);
            if (user.getCompanyId() == 1 && saveAddCapitalBill.getCode() == 0
                    && capitalBillDetailInfo.getOrderType() == 1) {
                vedengSoapService.orderSyncWeb(capitalBill.getCapitalBillDetail().getRelatedId());
            }
            Saleorder saleorder = new Saleorder();
            saleorder.setSaleorderId(capitalBill.getCapitalBillDetail().getRelatedId());
            //调用库存服务
            int i = warehouseStockService.updateOccupyStockService(saleorder, 0);
            return saveAddCapitalBill;

        }
        catch (Exception e) {
            logger.error("saveAddCapitalBill:", e);
        }
        return new ResultInfo<>();
    }

    /**
     * <b>Description:</b><br>
     * 保存新增售后的资金流水
     * 
     * @param request
     * @param capitalBill
     * @return
     * @Note <b>Author:</b> leo.yang <br>
     *       <b>Date:</b> 2017年9月13日 下午5:48:07
     */
    @FormToken(remove = true)
    @ResponseBody
    @RequestMapping(value = "/saveAddAfterCapitalBill")
    @SystemControllerLog(operationType = "add", desc = "售后付款保存新增的资金流水")
    public ResultInfo<?> saveAddAfterCapitalBill(HttpServletRequest request, CapitalBill capitalBill, PayApply payApply,
            String taskId, Integer paymentType) {
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        // 付款申请状态
        if (paymentType != null && paymentType == 641) {
            // 如果付款是641南京银行银企直连支付
            // 获取付款申请主表信息（根据ID）
            PayApply payApplyInfo = payApplyService.getPayApplyInfo(payApply.getPayApplyId());
            payApplyInfo.setComments(payApply.getComments());
            ResultInfo<?> res = bankBillService.addBankPayApply(payApplyInfo);
            if (res.getCode().equals(-1)) {
                return res;
            }
        }
        payApply.setValidStatus(1);
        payApply.setValidTime(DateUtil.sysTimeMillis());
        payApply.setUpdater(user.getUserId());
        payApply.setModTime(DateUtil.sysTimeMillis());
        payApply.setComments(null);
        // 修改付款申请审核状态
        ResultInfo<?> result = payApplyService.payApplyPass(payApply);

        if (user != null) {
            capitalBill.setCreator(user.getUserId());
            capitalBill.setAddTime(DateUtil.sysTimeMillis());
            capitalBill.setCompanyId(user.getCompanyId());
        }
        // 归属销售
        User belongUser = new User();
        // 安调维修工程师主键AFTER_SALES_INSTALLSTION_ID
        Integer afterSalesInstallstionId = 0;
        if (capitalBill.getCapitalBillDetail().getTraderId() != null) {
            if (capitalBill.getCapitalBillDetail().getOrderType() == 1) {
                belongUser = userService.getUserByTraderId(capitalBill.getCapitalBillDetail().getTraderId(), 1);// 1客户，2供应商
                if (belongUser != null && belongUser.getUserId() != null) {
                    belongUser = userService.getUserById(belongUser.getUserId());
                }
            }
            else if (capitalBill.getCapitalBillDetail().getOrderType() == 2) {
                belongUser = userService.getUserByTraderId(capitalBill.getCapitalBillDetail().getTraderId(), 2);// 1客户，2供应商
                if (belongUser != null && belongUser.getUserId() != null) {
                    belongUser = userService.getUserById(belongUser.getUserId());
                }
            }
            else if (capitalBill.getCapitalBillDetail().getOrderType() == 3) {
                // 售后类型，需要从售后单查是关联采购还是销售
                AfterSales afterSales = new AfterSales();
                afterSales.setAfterSalesId(capitalBill.getCapitalBillDetail().getRelatedId());
                afterSales.setCompanyId(user.getCompanyId());
                AfterSalesVo afterSalesVo = afterSalesOrderService.getAfterSalesVoListById(afterSales);
                if (afterSalesVo.getSubjectType() == 535) {
                    // 销售
                    belongUser = userService.getUserByTraderId(capitalBill.getCapitalBillDetail().getTraderId(), 1);// 1客户，2供应商
                    if (belongUser != null && belongUser.getUserId() != null) {
                        belongUser = userService.getUserById(belongUser.getUserId());
                    }
                    // 销售安调
                    if (afterSalesVo.getType() == 541 || afterSalesVo.getType() == 584) {
                        // 将安调对应的工程师查出
                        List<PayApplyDetail> payApplyDetailList =
                                payApplyService.getPayApplyDetailList(payApply.getPayApplyId());
                        if (payApplyDetailList != null && payApplyDetailList.size() != 0) {
                            afterSalesInstallstionId = payApplyDetailList.get(0).getDetailgoodsId();
                        }
                    }
                }
                else if (afterSalesVo.getSubjectType() == 536) {
                    // 采购
                    belongUser = userService.getUserByTraderId(capitalBill.getCapitalBillDetail().getTraderId(), 2);// 1客户，2供应商
                    if (belongUser != null && belongUser.getUserId() != null) {
                        belongUser = userService.getUserById(belongUser.getUserId());
                    }
                }
                else if (afterSalesVo.getSubjectType() == 537) {
                    // 第三方

                    // 销售安调
                    if (afterSalesVo.getType() == 550 || afterSalesVo.getType() == 585) {
                        // 将安调对应的工程师查出
                        List<PayApplyDetail> payApplyDetailList =
                                payApplyService.getPayApplyDetailList(payApply.getPayApplyId());
                        if (payApplyDetailList != null && payApplyDetailList.size() != 0) {
                            afterSalesInstallstionId = payApplyDetailList.get(0).getDetailgoodsId();
                        }
                    }
                }

            }

        }
        try {
            capitalBill.setCurrencyUnitId(1);
            capitalBill.setTraderTime(DateUtil.sysTimeMillis());
            capitalBill.setTraderType(capitalBill.getTraderType() == null ? 1 : capitalBill.getTraderType());// 默認收入
            // 获取当前用户公司信息
            Company companyInfo = companyService.getCompanyByCompangId(user.getCompanyId());
            if (capitalBill.getPayee() == null) {
                capitalBill.setPayee(companyInfo.getCompanyName());
            }
            else {
                capitalBill.setPayer(companyInfo.getCompanyName());
            }
            List<CapitalBillDetail> capitalBillDetails = new ArrayList<>();
            CapitalBillDetail capitalBillDetail2 = new CapitalBillDetail();
            capitalBillDetail2.setAmount(capitalBill.getAmount());
            capitalBillDetail2.setBussinessType(capitalBill.getCapitalBillDetail().getBussinessType());
            capitalBillDetail2.setOrderType(capitalBill.getCapitalBillDetail().getOrderType());
            capitalBillDetail2.setRelatedId(capitalBill.getCapitalBillDetail().getRelatedId());
            capitalBillDetail2.setOrderNo(capitalBill.getCapitalBillDetail().getOrderNo());
            capitalBillDetail2.setTraderId(capitalBill.getCapitalBillDetail().getTraderId());
            capitalBillDetail2.setTraderType(capitalBill.getCapitalBillDetail().getTraderType());
            capitalBillDetail2.setAfterSalesInstallstionId(afterSalesInstallstionId);
            if (belongUser != null && belongUser.getUserId() != null) {
                capitalBillDetail2.setUserId(belongUser.getUserId());
            }
            if (belongUser != null && belongUser.getOrgName() != null && belongUser.getOrgId() != null) {
                capitalBillDetail2.setOrgName(belongUser.getOrgName());
                capitalBillDetail2.setOrgId(belongUser.getOrgId());
            }
            capitalBillDetails.add(capitalBillDetail2);
            capitalBill.setCapitalBillDetails(capitalBillDetails);

            CapitalBillDetail capitalBillDetailInfo = new CapitalBillDetail();
            capitalBillDetailInfo.setAmount(capitalBill.getAmount());
            capitalBillDetailInfo.setBussinessType(capitalBill.getCapitalBillDetail().getBussinessType());
            capitalBillDetailInfo.setOrderType(capitalBill.getCapitalBillDetail().getOrderType());
            capitalBillDetailInfo.setRelatedId(capitalBill.getCapitalBillDetail().getRelatedId());
            capitalBillDetailInfo.setOrderNo(capitalBill.getCapitalBillDetail().getOrderNo());
            capitalBillDetailInfo.setTraderId(capitalBill.getCapitalBillDetail().getTraderId());
            capitalBillDetailInfo.setTraderType(capitalBill.getCapitalBillDetail().getTraderType());
            capitalBillDetailInfo.setAfterSalesInstallstionId(afterSalesInstallstionId);
            if (belongUser != null && belongUser.getUserId() != null) {
                capitalBillDetailInfo.setUserId(belongUser.getUserId());
            }
            if (belongUser != null && belongUser.getOrgName() != null && belongUser.getOrgId() != null) {
                capitalBillDetailInfo.setOrgName(belongUser.getOrgName());
                capitalBillDetailInfo.setOrgId(belongUser.getOrgId());
            }
            capitalBill.setCapitalBillDetail(capitalBillDetailInfo);
            ResultInfo<?> resultInfo = capitalBillService.saveAddCapitalBill(capitalBill);
            // 添加完执行流程审核
            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("pass", true);
            // 审批操作
            try {
                Integer status = 0;
                ResultInfo<?> complementStatus = actionProcdefService.complementTask(request, taskId,
                        request.getParameter("comments"), user.getUsername(), variables);
                // 如果未结束添加审核对应主表的审核状态
                if (!complementStatus.getData().equals("endEvent")) {
                    verifiesRecordService.saveVerifiesInfo(taskId, status);
                }
                return resultInfo;
            }
            catch (Exception e) {
                logger.error("saveAddAfterCapitalBill 1:", e);
                return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
            }
        }
        catch (Exception e) {
            logger.error("saveAddAfterCapitalBill 2:", e);
        }
        return new ResultInfo<>();
    }

    /**
     * <b>Description:</b><br>
     * 付款记录（分页）
     * 
     * @param request
     * @param capitalBill
     * @param session
     * @param pageNo
     * @param pageSize
     * @return
     * @Note <b>Author:</b> leo.yang <br>
     *       <b>Date:</b> 2017年9月25日 下午5:48:05
     */
    @ResponseBody
    @RequestMapping(value = "getPaymentRecordListPage")
    public ModelAndView getPaymentRecordListPage(HttpServletRequest request, CapitalBill capitalBill,
            HttpSession session, @RequestParam(required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(required = false) Integer pageSize) {
        ModelAndView mv = new ModelAndView();
        User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        Page page = getPageTag(request, pageNo, pageSize);
        try {
            // 获取当前日期
            Date date = new Date();
            String nowDate = DateUtil.DatePreMonth(date, 0, null);
            mv.addObject("nowDate", nowDate);
            // 获取当月第一天日期
            String pre1MonthDate = DateUtil.getFirstDayOfMonth(0);
            mv.addObject("pre1MonthDate", pre1MonthDate);

            if (null != request.getParameter("searchBegintimeStr")
                    && request.getParameter("searchBegintimeStr") != "") {
                capitalBill.setSearchBegintime(DateUtil
                        .convertLong(request.getParameter("searchBegintimeStr") + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
            }
            else {
                capitalBill
                        .setSearchBegintime(DateUtil.convertLong(pre1MonthDate + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
            }
            if (null != request.getParameter("searchEndtimeStr") && request.getParameter("searchEndtimeStr") != "") {
                capitalBill.setSearchEndtime(DateUtil
                        .convertLong(request.getParameter("searchEndtimeStr") + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
            }
            else {
                capitalBill.setSearchEndtime(DateUtil.convertLong(nowDate + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
            }
            if ("".equals(request.getParameter("searchBegintimeStr"))) {
                capitalBill.setSearchBegintime(null);
            }
            if ("".equals(request.getParameter("searchEndtimeStr"))) {
                capitalBill.setSearchEndtime(null);
            }

            // 交易方式
            List<SysOptionDefinition> traderModeList = getSysOptionDefinitionList(519);

            // 业务类型
            List<SysOptionDefinition> bussinessTypeList = getSysOptionDefinitionList(524);

            capitalBill.setIsPaymentRecordTotal(1);// 查询订单已付款总额，本次付款总额
            capitalBill.setCompanyId(user.getCompanyId());
            Map<String, Object> map = capitalBillService.getPaymentRecordListPage(request, capitalBill, page);
            if (map != null) {
                List<CapitalBill> list = (List<CapitalBill>) map.get("capitalBillList");
                /*
                 * for(int i=0;i<list.size();i++){ //供应商traderId Buyorder buyorder = new Buyorder();
                 * buyorder.setBuyorderId(list.get(i).getRelatedId()); BuyorderVo bv =
                 * buyorderService.getBuyorderVoDetail(buyorder,user); list.get(i).setTraderId(bv.getTraderId()); }
                 */
                mv.addObject("capitalBillList", list);
            }

            mv.addObject("page", (Page) map.get("page"));
            mv.addObject("traderModeList", traderModeList);
            mv.addObject("bussinessTypeList", bussinessTypeList);
            mv.addObject("capitalBill", (CapitalBill) map.get("capitalBill"));
        }
        catch (Exception e) {
            logger.error("getPaymentRecordListPage:", e);
        }
        mv.setViewName("finance/capitalbill/index_payment_record");
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 收款记录（分页）
     * 
     * @param request
     * @param capitalBill
     * @param session
     * @param pageNo
     * @param pageSize
     * @return
     * @Note <b>Author:</b> leo.yang <br>
     *       <b>Date:</b> 2017年9月25日 下午6:10:42
     */
    @ResponseBody
    @RequestMapping(value = "getCollectionRecordListPage")
    public ModelAndView getCollectionRecordListPage(HttpServletRequest request, CapitalBill capitalBill,
            HttpSession session, @RequestParam(required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(required = false) Integer pageSize) {
        ModelAndView mv = new ModelAndView();

        User user = (User) session.getAttribute(Consts.SESSION_USER);
        Page page = getPageTag(request, pageNo, pageSize);
        try {
            // 获取当前日期
            Date date = new Date();
            String nowDate = DateUtil.DatePreMonth(date, 0, null);
            mv.addObject("nowDate", nowDate);
            // 获取当月第一天日期
            String pre1MonthDate = DateUtil.getFirstDayOfMonth(0);
            mv.addObject("pre1MonthDate", pre1MonthDate);

            if (null != request.getParameter("searchBegintimeStr")
                    && request.getParameter("searchBegintimeStr") != "") {
                capitalBill.setSearchBegintime(DateUtil
                        .convertLong(request.getParameter("searchBegintimeStr") + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
            }
            else {
                capitalBill
                        .setSearchBegintime(DateUtil.convertLong(pre1MonthDate + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
            }
            if (null != request.getParameter("searchEndtimeStr") && request.getParameter("searchEndtimeStr") != "") {
                capitalBill.setSearchEndtime(DateUtil
                        .convertLong(request.getParameter("searchEndtimeStr") + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
            }
            else {
                capitalBill.setSearchEndtime(DateUtil.convertLong(nowDate + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
            }
            if ("".equals(request.getParameter("searchBegintimeStr"))) {
                capitalBill.setSearchBegintime(null);
            }
            if ("".equals(request.getParameter("searchEndtimeStr"))) {
                capitalBill.setSearchEndtime(null);
            }

            // 交易方式
            List<SysOptionDefinition> traderModeList = getSysOptionDefinitionList(519);

            // 业务类型
            List<SysOptionDefinition> bussinessTypeList = getSysOptionDefinitionList(524);

            // 获取销售部门
            List<Organization> orgList = orgService.getSalesOrgList(SysOptionConstant.ID_310, user.getCompanyId());
            // 获取售后部门
            List<Organization> orgList2 = orgService.getSalesOrgList(SysOptionConstant.ID_312, user.getCompanyId());
            for (int x = 0; x < orgList2.size(); x++) {
                orgList2.get(x).setOrgId(-100 - x);
            }
            orgList.addAll(orgList2);
            mv.addObject("orgList", orgList);

            // 获取当前销售用户下级职位用户
            List<Integer> positionType = new ArrayList<>();
            positionType.add(SysOptionConstant.ID_310);
            List<User> userList = userService.getMyUserList(user, positionType, false);
            // 获取售后用户
            List<Integer> positionType2 = new ArrayList<>();
            positionType2.add(SysOptionConstant.ID_312);
            List<User> userList2 = userService.getMyUserList(user, positionType2, false);
            userList.addAll(userList2);
            mv.addObject("userList", userList);// 操作人员
            // capitalBill.getSaleorder().setSaleUserList(userList);

            if (null != capitalBill.getOptUserId() && capitalBill.getOptUserId() != -1) {
                // 销售人员所属客户（即当前订单操作人员）
                List<Integer> traderIdList = userService.getTraderIdListByUserId(capitalBill.getOptUserId(), 1);// 1客户，2供应商
                if (traderIdList == null || traderIdList.isEmpty()) {
                    traderIdList.add(-1);
                }
                capitalBill.setTraderIdList(traderIdList);
            }

            capitalBill.setIsCollectionRecordTotal(1);// 查询记录总计及总金额
            capitalBill.setCompanyId(user.getCompanyId());
            Map<String, Object> map = capitalBillService.getCollectionRecordListPage(request, capitalBill, page);
            if (map != null) {
                List<CapitalBill> list = (List<CapitalBill>) map.get("capitalBillList");
                if (list != null) {
                    for (int i = 0; i < list.size(); i++) {
                        if ("2".equals(list.get(i).getOperationType())) {// 售后
                            User user2 = userService.getUserById(list.get(i).getUserId());
                            list.get(i).setOptUserName(user2 == null ? "" : user2.getUsername());
                            // 部门
                            list.get(i).setSalesDeptName(user2 == null ? "" : user2.getOrgName());
                        }
                        else {
                            // 归属销售
                            user = userService.getUserByTraderId(list.get(i).getTraderId(), 1);// 1客户，2供应商
                            list.get(i).setOptUserName(user == null ? "" : user.getUsername());

                            // 销售部门
                            list.get(i).setSalesDeptName(orgService.getOrgNameById(list.get(i).getOrgId()));
                        }
                    }
                    mv.addObject("capitalBillList", list);
                }
            }

            mv.addObject("page", (Page) map.get("page"));
            mv.addObject("traderModeList", traderModeList);
            mv.addObject("bussinessTypeList", bussinessTypeList);
            mv.addObject("capitalBill", (CapitalBill) map.get("capitalBill"));
        }
        catch (Exception e) {
            logger.error("getCollectionRecordListPage:", e);
        }
        mv.setViewName("finance/capitalbill/index_collection_record");
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 付款列表数据异步补充
     * 
     * @param request
     * @param saleorder
     * @param saleOrderIdArr
     * @return
     * @Note <b>Author:</b> duke <br>
     *       <b>Date:</b> 2018年9月12日 上午11:25:41
     */
    @ResponseBody
    @RequestMapping(value = "getCollectionRecordInfoAjax")
    public ResultInfo<?> getCollectionRecordInfoAjax(HttpServletRequest request,
            @RequestParam(required = false, value = "saleOrderIdArr") String saleOrderIdArr) {
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        if (null == user) {
            return new ResultInfo<>(-1, "用户登录失效");
        }
        try {
            List<Integer> saleOrderIdList = JSON.parseArray(saleOrderIdArr, Integer.class);
            if (saleOrderIdList != null && saleOrderIdList.size() > 0) {
                return capitalBillService.getCollectionRecordInfoAjax(saleOrderIdList);
            }
        } catch (Exception e) {
            logger.error("getCollectionRecordInfoAjax:", e);
        }
        return new ResultInfo<>();
    }

    /**
     * <b>Description:</b><br>
     * 财务退款业务操作
     * 
     * @param request
     * @param capitalBill
     * @return
     * @Note <b>Author:</b> duke <br>
     *       <b>Date:</b> 2017年11月2日 下午3:08:13
     */
    @FormToken(remove=true)
    @ResponseBody
    @RequestMapping(value = "/saveRefundCapitalBill")
    @SystemControllerLog(operationType = "add", desc = "财务退款业务")
    public ResultInfo<?> saveRefundCapitalBill(HttpServletRequest request, CapitalBill capitalBill, PayApply payApply,
            String taskId, Integer paymentType) {
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        /*
         * List<CapitalBillDetail> capitalBillDetails = new ArrayList<>(); CapitalBillDetail capitalBillDetail = new
         * CapitalBillDetail(); capitalBillDetail.setAmount(capitalBill.getAmount());
         * capitalBillDetail.setBussinessType(capitalBill.getCapitalBillDetail().getBussinessType());
         * capitalBillDetail.setOrderType(capitalBill.getCapitalBillDetail().getOrderType());
         * capitalBillDetail.setRelatedId(capitalBill.getCapitalBillDetail().getRelatedId());
         * capitalBillDetail.setOrderNo(capitalBill.getCapitalBillDetail().getOrderNo());
         * capitalBillDetails.add(capitalBillDetail); capitalBill.setCapitalBillDetails(capitalBillDetails);
         */
        ResultInfo<?> resultInfo = new ResultInfo<>();
        // add by fralin.wu for [耗材售后退款] at 2018-12-21 begin 
        // 公司ID
        Integer companyId = null == user ? 1 : user.getCompanyId();
        // 售后线上售后退款标志 默认 不是
        boolean afterSalesIsNotOnlineFlag = true;
        // add by fralin.wu for [耗材售后退款] at 2018-12-21 end 
        
        // 归属人员
        User belongUser = new User();
        if (capitalBill.getCapitalBillDetail().getTraderId() != null) {
            if (capitalBill.getCapitalBillDetail().getOrderType() == 1) {
                // 销售
                belongUser = userService.getUserByTraderId(capitalBill.getCapitalBillDetail().getTraderId(), 1);// 1客户，2供应商
                if (belongUser != null && belongUser.getUserId() != null) {
                    belongUser = userService.getUserById(belongUser.getUserId());
                }
            }
            else if (capitalBill.getCapitalBillDetail().getOrderType() == 2) {
                // 采购
                belongUser = userService.getUserByTraderId(capitalBill.getCapitalBillDetail().getTraderId(), 2);// 1客户，2供应商
                if (belongUser != null && belongUser.getUserId() != null) {
                    belongUser = userService.getUserById(belongUser.getUserId());
                }
            }
            else if (capitalBill.getCapitalBillDetail().getOrderType() == 3) {
                // 售后类型，需要从售后单查是关联采购还是销售
                AfterSales afterSales = new AfterSales();
                afterSales.setAfterSalesId(capitalBill.getCapitalBillDetail().getRelatedId());
                afterSales.setCompanyId(user.getCompanyId());
                AfterSalesVo afterSalesVo = afterSalesOrderService.getAfterSalesVoListById(afterSales);
                if (afterSalesVo.getSubjectType() == 535) {
                    // 销售
                    belongUser = userService.getUserByTraderId(capitalBill.getCapitalBillDetail().getTraderId(), 1);// 1客户，2供应商
                    if (belongUser != null && belongUser.getUserId() != null) {
                        belongUser = userService.getUserById(belongUser.getUserId());
                    }
                    // add by franlin.wu for [耗材售后线上退款] at 2018-12-21 begin
                    if(Integer.valueOf(1).equals(afterSalesVo.getSource()) && Integer.valueOf(539).equals(afterSalesVo.getType()))
                    {
                    	// 请求头
                		Map<String, String> headers = new HashMap<String, String>();
                		headers.put("version", "v1");
                		// 响应公司ID
                		afterSalesVo.setCompanyId(companyId);
                		// 返回响应结果
                		TypeReference<ResultInfo<?>> resultType = new TypeReference<ResultInfo<?>>() {};
                		// 售后线上退款接口
                		resultInfo = HttpRestClientUtil.post(restDbUrl + DbRestInterfaceConstant.DB_REST_FINANCE_OPERATE_AFTER_SALE_ONLINE_REFUND, resultType, headers, afterSalesVo);
                    	logger.info("售后线上退款："+afterSalesVo.getAfterSalesNo()+"\t"+capitalBill.getCapitalBillDetail().getRelatedId() + resultInfo);
                		// 成功  或 0为该场景业务异常场景
                        if(null != resultInfo && (Integer.valueOf(0).equals(resultInfo.getCode())))
                        {
                            afterSalesIsNotOnlineFlag = false;
                        }
                        else if(null != resultInfo && Integer.valueOf(1).equals(resultInfo.getCode()))
                        {
                            return new ResultInfo<>(-1, resultInfo.getMessage());
                        }
                    }
                    // add by franlin.wu for [售后线上退款] at 2018-12-21 end
                    
                }
                else if (afterSalesVo.getSubjectType() == 536) {
                    // 采购
                    belongUser = userService.getUserByTraderId(capitalBill.getCapitalBillDetail().getTraderId(), 2);// 1客户，2供应商
                    if (belongUser != null && belongUser.getUserId() != null) {
                        belongUser = userService.getUserById(belongUser.getUserId());
                    }
                }

            }

        }

        if (paymentType != null && paymentType == 641) {
            // 如果付款是641南京银行银企直连支付
            // 获取付款申请主表信息（根据ID）
            PayApply payApplyInfo = payApplyService.getPayApplyInfo(payApply.getPayApplyId());
            payApplyInfo.setComments(payApply.getComments());
            ResultInfo<?> res = bankBillService.addBankPayApply(payApplyInfo);
            if (res.getCode().equals(-1)) {
                return res;
            }
        }
        payApply.setValidStatus(1);
        payApply.setValidTime(DateUtil.sysTimeMillis());
        payApply.setUpdater(user.getUserId());
        payApply.setModTime(DateUtil.sysTimeMillis());
        payApply.setComments(null);
        // 修改付款申请审核状态
        ResultInfo<?> result = payApplyService.payApplyPass(payApply);
        
        // add by franlin.wu for [售后线上退款] at 2018-12-21 begin
        if(afterSalesIsNotOnlineFlag)
        {        	
        // add by franlin.wu for [售后线上退款] at 2018-12-21 end
        	// 获取当前用户公司信息
        	Company companyInfo = companyService.getCompanyByCompangId(user.getCompanyId());
        	if (user != null) {
        		capitalBill.setCreator(user.getUserId());
        		capitalBill.setAddTime(DateUtil.sysTimeMillis());
        		capitalBill.setCompanyId(user.getCompanyId());
        	}
        	capitalBill.setCurrencyUnitId(1);// 货币单位ID
        	capitalBill.setTraderTime(DateUtil.sysTimeMillis());
        	capitalBill.setTraderType(capitalBill.getTraderType() == null ? 2 : capitalBill.getTraderType());// 默認支出
        	
        	capitalBill.setPayer(companyInfo.getCompanyName());// 付款方
        	
        	if (belongUser != null) {
        		capitalBill.getCapitalBillDetail().setOrgName(belongUser.getOrgName());
        		capitalBill.getCapitalBillDetail().setOrgId(belongUser.getOrgId());
        	}
        	resultInfo = capitalBillService.saveRefundCapitalBill(capitalBill);
        }

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("pass", true);
        // 审批操作
        try {
            Integer status = 0;
            ResultInfo<?> complementStatus = actionProcdefService.complementTask(request, taskId,
                    request.getParameter("comments"), user.getUsername(), variables);
            // 如果未结束添加审核对应主表的审核状态
            if (!complementStatus.getData().equals("endEvent")) {
                verifiesRecordService.saveVerifiesInfo(taskId, status);
            }
            return resultInfo;
        }
        catch (Exception e) {
            logger.error("saveRefundCapitalBill:", e);
            return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
        }
    }

    /**
     * <b>Description:</b><br>
     * 付款申请添加流水记录
     * 
     * @param request
     * @param capitalBill
     * @return
     * @Note <b>Author:</b> duke <br>
     *       <b>Date:</b> 2017年11月8日 下午3:11:43
     */
    @FormToken(remove = true)
    @ResponseBody
    @RequestMapping(value = "/savePayCapitalBill")
    @SystemControllerLog(operationType = "add", desc = "付款申请添加流水记录")
    public ResultInfo<?> savePayCapitalBill(HttpServletRequest request, CapitalBill capitalBill, PayApply payApply,
            String taskId, Integer paymentType) {
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        if (paymentType != null && paymentType == 641) {
            // 如果付款是641南京银行银企直连支付
            // 获取付款申请主表信息（根据ID）
            PayApply payApplyInfo = payApplyService.getPayApplyInfo(payApply.getPayApplyId());
            payApplyInfo.setComments(payApply.getComments());
            ResultInfo<?> res = bankBillService.addBankPayApply(payApplyInfo);
            if (res.getCode().equals(-1)) {
                return res;
            }
        }
        payApply.setValidStatus(1);
        payApply.setValidTime(DateUtil.sysTimeMillis());
        payApply.setUpdater(user.getUserId());
        payApply.setModTime(DateUtil.sysTimeMillis());
        payApply.setComments(null);
        // 修改付款申请审核状态
        ResultInfo<?> result = payApplyService.payApplyPass(payApply);
        if (user != null) {
            capitalBill.setCreator(user.getUserId());
            capitalBill.setAddTime(DateUtil.sysTimeMillis());
        }
        capitalBill.setCurrencyUnitId(1);// 货币单位ID
        capitalBill.setTraderTime(DateUtil.sysTimeMillis());
        capitalBill.setTraderType(capitalBill.getTraderType() == null ? 2 : capitalBill.getTraderType());// 默認支出

        // 获取当前用户公司信息
        Company companyInfo = companyService.getCompanyByCompangId(user.getCompanyId());
        capitalBill.setPayer(companyInfo.getCompanyName());// 付款方

        ResultInfo<?> resultInfo = capitalBillService.savePayCapitalBill(capitalBill);

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("pass", true);
        // 审批操作
        try {
            Integer status = 0;
            ResultInfo<?> complementStatus = actionProcdefService.complementTask(request, taskId,
                    request.getParameter("comments"), user.getUsername(), variables);
            // 如果未结束添加审核对应主表的审核状态
            if (!complementStatus.getData().equals("endEvent")) {
                verifiesRecordService.saveVerifiesInfo(taskId, status);
            }
            return resultInfo;
        }
        catch (Exception e) {
            logger.error("savePayCapitalBill:", e);
            return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
        }
    }

    /**
     * <b>Description:</b><br>
     * 保存新增售后的资金流水匹配银行流水
     * 
     * @param request
     * @param payApply
     * @param taskId
     * @param bankBillId
     * @param tranFlow
     * @param paymentType
     * @return
     * @Note <b>Author:</b> Michael <br>
     *       <b>Date:</b> 2018年8月14日 下午2:58:42
     */
    @FormToken(remove = true)
    @ResponseBody
    @RequestMapping(value = "/saveAddAfterCapitalBillForBank")
    @SystemControllerLog(operationType = "add", desc = "售后付款保存新增的资金流水匹配银行流水")
    public ResultInfo<?> saveAddAfterCapitalBillForBank(HttpServletRequest request, PayApply payApply, String taskId,
            Integer bankBillId, String tranFlow, Integer paymentType) {
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);

        payApply.setValidStatus(1);
        payApply.setValidTime(DateUtil.sysTimeMillis());
        payApply.setUpdater(user.getUserId());
        payApply.setModTime(DateUtil.sysTimeMillis());
        payApply.setComments(null);
        // 修改付款申请审核状态
        ResultInfo<?> result = payApplyService.payApplyPass(payApply);

        // 获取付款申请主表信息（根据ID）
        PayApply payApplyInfo = payApplyService.getPayApplyInfo(payApply.getPayApplyId());

        // 售后类型，需要从售后单查是关联采购还是销售
        AfterSales afterSales = new AfterSales();
        afterSales.setAfterSalesId(payApplyInfo.getRelatedId());
        afterSales.setCompanyId(user.getCompanyId());
        AfterSalesVo afterSalesVo = afterSalesOrderService.getAfterSalesVoListById(afterSales);
        AfterSalesDetailVo afterDetailVo = new AfterSalesDetailVo();
        afterDetailVo.setAfterSalesId(payApplyInfo.getRelatedId());
        afterDetailVo.setPayApplyId(payApply.getPayApplyId());
        AfterSalesDetailVo afterSalesDetailVo = invoiceAfterService.getAfterCapitalBillInfo(afterDetailVo);
        // 如果不为null的话
        if (afterSalesDetailVo != null && afterSalesDetailVo.getTraderId() != null) {
            afterSalesVo.setTraderId(afterSalesDetailVo.getTraderId());
        }
        // 添加资金流水记录
        CapitalBill capitalBill = new CapitalBill();
        if (user != null) {
            capitalBill.setCreator(user.getUserId());
            capitalBill.setAddTime(DateUtil.sysTimeMillis());
            capitalBill.setCompanyId(user.getCompanyId());
        }
        // 归属销售
        User belongUser = new User();
        // 安调维修工程师主键AFTER_SALES_INSTALLSTION_ID
        Integer afterSalesInstallstionId = 0;
        if (afterSalesVo.getTraderId() != null) {
            if (afterSalesVo.getSubjectType() == 535) {
                // 销售
                belongUser = userService.getUserByTraderId(afterSalesVo.getTraderId(), 1);// 1客户，2供应商
                if (belongUser != null && belongUser.getUserId() != null) {
                    belongUser = userService.getUserById(belongUser.getUserId());
                }
                // 销售安调
                if (afterSalesVo.getType() == 541 || afterSalesVo.getType() == 584) {
                    // 将安调对应的工程师查出
                    List<PayApplyDetail> payApplyDetailList =
                            payApplyService.getPayApplyDetailList(payApply.getPayApplyId());
                    if (payApplyDetailList != null && payApplyDetailList.size() != 0) {
                        afterSalesInstallstionId = payApplyDetailList.get(0).getDetailgoodsId();
                    }
                }
            }
            else if (afterSalesVo.getSubjectType() == 536) {
                // 采购
                belongUser = userService.getUserByTraderId(afterSalesVo.getTraderId(), 2);// 1客户，2供应商
                if (belongUser != null && belongUser.getUserId() != null) {
                    belongUser = userService.getUserById(belongUser.getUserId());
                }
            }
            else if (afterSalesVo.getSubjectType() == 537) {
                // 第三方

                // 销售安调
                if (afterSalesVo.getType() == 550 || afterSalesVo.getType() == 585) {
                    // 将安调对应的工程师查出
                    List<PayApplyDetail> payApplyDetailList =
                            payApplyService.getPayApplyDetailList(payApply.getPayApplyId());
                    if (payApplyDetailList != null && payApplyDetailList.size() != 0) {
                        afterSalesInstallstionId = payApplyDetailList.get(0).getDetailgoodsId();
                    }
                }
            }

        }
        try {
            capitalBill.setCurrencyUnitId(1);
            capitalBill.setTraderTime(DateUtil.sysTimeMillis());
            // 获取当前用户公司信息
            Company companyInfo = companyService.getCompanyByCompangId(user.getCompanyId());
            if (capitalBill.getPayee() == null) {
                capitalBill.setPayee(companyInfo.getCompanyName());
            }
            else {
                capitalBill.setPayer(companyInfo.getCompanyName());
            }

            capitalBill.setBankBillId(bankBillId);
            capitalBill.setTranFlow(tranFlow);
            capitalBill.setPaymentType(paymentType);
            capitalBill.setCurrencyUnitId(1);
            capitalBill.setTraderTime(DateUtil.sysTimeMillis());
            capitalBill.setTraderType(payApplyInfo.getTraderMode() == 528 ? 5 : 2);
            capitalBill.setTraderSubject(payApplyInfo.getTraderSubject());
            capitalBill.setTraderMode(payApplyInfo.getTraderMode());
            capitalBill.setAmount(payApplyInfo.getAmount());
            capitalBill.setPayee(payApplyInfo.getTraderName());
            capitalBill.setPayeeBankAccount(payApplyInfo.getBankAccount());
            capitalBill.setPayer(companyInfo.getCompanyName());
            // capitalBill.setPayerBankAccount();
            capitalBill.setComments(request.getParameter("comments"));// 资金流水交易备注

            List<CapitalBillDetail> capitalBillDetails = new ArrayList<>();
            CapitalBillDetail capitalBillDetail2 = new CapitalBillDetail();
            capitalBillDetail2.setAmount(payApplyInfo.getAmount());
            // 销售订单退货,销售订单退款,第三方退款
            if (afterSalesDetailVo.getAfterType().intValue() == 539
                    || afterSalesDetailVo.getAfterType().intValue() == 543
                    || afterSalesDetailVo.getAfterType().intValue() == 551) {
                capitalBillDetail2.setBussinessType(531);// 退款
            }
            else {
                capitalBillDetail2.setBussinessType(525);// 订单付款
            }
            capitalBillDetail2.setOrderType(3);// 采购订单
            capitalBillDetail2.setRelatedId(payApplyInfo.getRelatedId());
            capitalBillDetail2.setOrderNo(afterSalesVo.getAfterSalesNo());
            capitalBillDetail2.setTraderId(afterSalesVo.getTraderId());
            if (afterSalesVo.getSubjectType() == 535) {
                capitalBillDetail2.setTraderType(1);
            }
            if (afterSalesVo.getSubjectType() == 536) {
                capitalBillDetail2.setTraderType(2);
            }
            capitalBillDetail2.setUserId(afterSalesVo.getServiceUserId());
            capitalBillDetail2.setAfterSalesInstallstionId(afterSalesInstallstionId);
            if (belongUser != null && belongUser.getOrgName() != null && belongUser.getOrgId() != null) {
                capitalBillDetail2.setOrgName(belongUser.getOrgName());
                capitalBillDetail2.setOrgId(belongUser.getOrgId());
            }

            capitalBillDetails.add(capitalBillDetail2);
            capitalBill.setCapitalBillDetails(capitalBillDetails);

            CapitalBillDetail capitalBillDetail = new CapitalBillDetail();
            capitalBillDetail.setAmount(payApplyInfo.getAmount());
            // 销售订单退货,销售订单退款,第三方退款
            if (afterSalesDetailVo.getAfterType().intValue() == 539
                    || afterSalesDetailVo.getAfterType().intValue() == 543
                    || afterSalesDetailVo.getAfterType().intValue() == 551) {
                capitalBillDetail.setBussinessType(531);// 退款
            }
            else {
                capitalBillDetail.setBussinessType(525);// 订单付款
            }
            capitalBillDetail.setOrderType(3);// 采购订单
            capitalBillDetail.setRelatedId(payApplyInfo.getRelatedId());
            capitalBillDetail.setOrderNo(afterSalesVo.getAfterSalesNo());
            capitalBillDetail.setTraderId(afterSalesVo.getTraderId());
            if (afterSalesVo.getSubjectType() == 535) {
                capitalBillDetail.setTraderType(1);
            }
            if (afterSalesVo.getSubjectType() == 536) {
                capitalBillDetail.setTraderType(2);
            }

            capitalBillDetail.setUserId(afterSalesVo.getServiceUserId());
            capitalBillDetail.setAfterSalesInstallstionId(afterSalesInstallstionId);
            if (belongUser != null && belongUser.getOrgName() != null && belongUser.getOrgId() != null) {
                capitalBillDetail.setOrgName(belongUser.getOrgName());
                capitalBillDetail.setOrgId(belongUser.getOrgId());
            }
            capitalBill.setCapitalBillDetail(capitalBillDetail);
            ResultInfo<?> resultInfo = new ResultInfo<>();
            // 销售订单退货,销售订单退款,第三方退款
            if (afterSalesDetailVo.getAfterType().intValue() == 539
                    || afterSalesDetailVo.getAfterType().intValue() == 543
                    || afterSalesDetailVo.getAfterType().intValue() == 551) {
                resultInfo = capitalBillService.saveRefundCapitalBill(capitalBill);
            }
            else {
                resultInfo = capitalBillService.saveAddCapitalBill(capitalBill);
            }
            // 修改订单结款匹配项目
            Integer matchedObject = 857;
            // 销售订单退货,销售订单退款,第三方退款
            if (afterSalesDetailVo.getAfterType().intValue() == 539
                    || afterSalesDetailVo.getAfterType().intValue() == 543
                    || afterSalesDetailVo.getAfterType().intValue() == 551) {
                // 客户退款
                matchedObject = 858;
                // 销售订单安调、第三方安调
            }
            else if (afterSalesDetailVo.getAfterType().intValue() == 541
                    || afterSalesDetailVo.getAfterType().intValue() == 550) {
                // 售后安调
                matchedObject = 859;
                // 销售订单维修,第三方维修
            }
            else if (afterSalesDetailVo.getAfterType().intValue() == 584
                    || afterSalesDetailVo.getAfterType().intValue() == 585) {
                // 售后维修
                matchedObject = 860;
            }
            BankBill bankBillInfo = new BankBill();
            bankBillInfo.setBankBillId(bankBillId);
            bankBillInfo.setMatchedObject(matchedObject);
            bankBillService.editBankBill(bankBillInfo);

            // 添加完执行流程审核
            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("pass", true);
            // 审批操作
            try {
                Integer status = 0;
                ResultInfo<?> complementStatus = actionProcdefService.complementTask(request, taskId,
                        request.getParameter("comments"), user.getUsername(), variables);
                // 如果未结束添加审核对应主表的审核状态
                if (!complementStatus.getData().equals("endEvent")) {
                    verifiesRecordService.saveVerifiesInfo(taskId, status);
                }
                return resultInfo;
            }
            catch (Exception e) {
                logger.error("saveAddAfterCapitalBillForBank 1:", e);
                return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
            }
        }
        catch (Exception e) {
            logger.error("saveAddAfterCapitalBillForBank 2:", e);
        }
        return new ResultInfo<>();
    }
    
    
    /**
     * 没有formToken
     * <p>Title: saveAddAfterCapitalBillForBank</p>  
     * <p>Description: </p>  
     * @param request
     * @param payApply
     * @param taskId
     * @param bankBillId
     * @param tranFlow
     * @param paymentType
     * @return  
     * @author Bill
     * @date 2019年3月14日
     */
    @FormToken(remove = true)
    @ResponseBody
    @RequestMapping(value = "/saveAddAfterCapitalBillForBankNoFormToken")
    @SystemControllerLog(operationType = "add", desc = "售后付款保存新增的资金流水匹配银行流水")
    public ResultInfo<?> saveAddAfterCapitalBillForBankNoFormToken(HttpServletRequest request, PayApply payApply, String taskId,
            Integer bankBillId, String tranFlow, Integer paymentType) {
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);

        payApply.setValidStatus(1);
        payApply.setValidTime(DateUtil.sysTimeMillis());
        payApply.setUpdater(user.getUserId());
        payApply.setModTime(DateUtil.sysTimeMillis());
        payApply.setComments(null);
        // 修改付款申请审核状态
        ResultInfo<?> result = payApplyService.payApplyPass(payApply);

        // 获取付款申请主表信息（根据ID）
        PayApply payApplyInfo = payApplyService.getPayApplyInfo(payApply.getPayApplyId());

        // 售后类型，需要从售后单查是关联采购还是销售
        AfterSales afterSales = new AfterSales();
        afterSales.setAfterSalesId(payApplyInfo.getRelatedId());
        afterSales.setCompanyId(user.getCompanyId());
        AfterSalesVo afterSalesVo = afterSalesOrderService.getAfterSalesVoListById(afterSales);
        AfterSalesDetailVo afterDetailVo = new AfterSalesDetailVo();
        afterDetailVo.setAfterSalesId(payApplyInfo.getRelatedId());
        afterDetailVo.setPayApplyId(payApply.getPayApplyId());
        AfterSalesDetailVo afterSalesDetailVo = invoiceAfterService.getAfterCapitalBillInfo(afterDetailVo);
        // 如果不为null的话
        if (afterSalesDetailVo != null && afterSalesDetailVo.getTraderId() != null) {
            afterSalesVo.setTraderId(afterSalesDetailVo.getTraderId());
        }
        // 添加资金流水记录
        CapitalBill capitalBill = new CapitalBill();
        if (user != null) {
            capitalBill.setCreator(user.getUserId());
            capitalBill.setAddTime(DateUtil.sysTimeMillis());
            capitalBill.setCompanyId(user.getCompanyId());
        }
        // 归属销售
        User belongUser = new User();
        // 安调维修工程师主键AFTER_SALES_INSTALLSTION_ID
        Integer afterSalesInstallstionId = 0;
        if (afterSalesVo.getTraderId() != null) {
            if (afterSalesVo.getSubjectType() == 535) {
                // 销售
                belongUser = userService.getUserByTraderId(afterSalesVo.getTraderId(), 1);// 1客户，2供应商
                if (belongUser != null && belongUser.getUserId() != null) {
                    belongUser = userService.getUserById(belongUser.getUserId());
                }
                // 销售安调
                if (afterSalesVo.getType() == 541 || afterSalesVo.getType() == 584) {
                    // 将安调对应的工程师查出
                    List<PayApplyDetail> payApplyDetailList =
                            payApplyService.getPayApplyDetailList(payApply.getPayApplyId());
                    if (payApplyDetailList != null && payApplyDetailList.size() != 0) {
                        afterSalesInstallstionId = payApplyDetailList.get(0).getDetailgoodsId();
                    }
                }
            }
            else if (afterSalesVo.getSubjectType() == 536) {
                // 采购
                belongUser = userService.getUserByTraderId(afterSalesVo.getTraderId(), 2);// 1客户，2供应商
                if (belongUser != null && belongUser.getUserId() != null) {
                    belongUser = userService.getUserById(belongUser.getUserId());
                }
            }
            else if (afterSalesVo.getSubjectType() == 537) {
                // 第三方

                // 销售安调
                if (afterSalesVo.getType() == 550 || afterSalesVo.getType() == 585) {
                    // 将安调对应的工程师查出
                    List<PayApplyDetail> payApplyDetailList =
                            payApplyService.getPayApplyDetailList(payApply.getPayApplyId());
                    if (payApplyDetailList != null && payApplyDetailList.size() != 0) {
                        afterSalesInstallstionId = payApplyDetailList.get(0).getDetailgoodsId();
                    }
                }
            }

        }
        try {
            capitalBill.setCurrencyUnitId(1);
            capitalBill.setTraderTime(DateUtil.sysTimeMillis());
            // 获取当前用户公司信息
            Company companyInfo = companyService.getCompanyByCompangId(user.getCompanyId());
            if (capitalBill.getPayee() == null) {
                capitalBill.setPayee(companyInfo.getCompanyName());
            }
            else {
                capitalBill.setPayer(companyInfo.getCompanyName());
            }

            capitalBill.setBankBillId(bankBillId);
            capitalBill.setTranFlow(tranFlow);
            capitalBill.setPaymentType(paymentType);
            capitalBill.setCurrencyUnitId(1);
            capitalBill.setTraderTime(DateUtil.sysTimeMillis());
            capitalBill.setTraderType(payApplyInfo.getTraderMode() == 528 ? 5 : 2);
            capitalBill.setTraderSubject(payApplyInfo.getTraderSubject());
            capitalBill.setTraderMode(payApplyInfo.getTraderMode());
            capitalBill.setAmount(payApplyInfo.getAmount());
            capitalBill.setPayee(payApplyInfo.getTraderName());
            capitalBill.setPayeeBankAccount(payApplyInfo.getBankAccount());
            capitalBill.setPayer(companyInfo.getCompanyName());
            // capitalBill.setPayerBankAccount();
            capitalBill.setComments(request.getParameter("comments"));// 资金流水交易备注

            List<CapitalBillDetail> capitalBillDetails = new ArrayList<>();
            CapitalBillDetail capitalBillDetail2 = new CapitalBillDetail();
            capitalBillDetail2.setAmount(payApplyInfo.getAmount());
            // 销售订单退货,销售订单退款,第三方退款
            if (afterSalesDetailVo.getAfterType().intValue() == 539
                    || afterSalesDetailVo.getAfterType().intValue() == 543
                    || afterSalesDetailVo.getAfterType().intValue() == 551) {
                capitalBillDetail2.setBussinessType(531);// 退款
            }
            else {
                capitalBillDetail2.setBussinessType(525);// 订单付款
            }
            capitalBillDetail2.setOrderType(3);// 采购订单
            capitalBillDetail2.setRelatedId(payApplyInfo.getRelatedId());
            capitalBillDetail2.setOrderNo(afterSalesVo.getAfterSalesNo());
            capitalBillDetail2.setTraderId(afterSalesVo.getTraderId());
            if (afterSalesVo.getSubjectType() == 535) {
                capitalBillDetail2.setTraderType(1);
            }
            if (afterSalesVo.getSubjectType() == 536) {
                capitalBillDetail2.setTraderType(2);
            }
            capitalBillDetail2.setUserId(afterSalesVo.getServiceUserId());
            capitalBillDetail2.setAfterSalesInstallstionId(afterSalesInstallstionId);
            if (belongUser != null && belongUser.getOrgName() != null && belongUser.getOrgId() != null) {
                capitalBillDetail2.setOrgName(belongUser.getOrgName());
                capitalBillDetail2.setOrgId(belongUser.getOrgId());
            }

            capitalBillDetails.add(capitalBillDetail2);
            capitalBill.setCapitalBillDetails(capitalBillDetails);

            CapitalBillDetail capitalBillDetail = new CapitalBillDetail();
            capitalBillDetail.setAmount(payApplyInfo.getAmount());
            // 销售订单退货,销售订单退款,第三方退款
            if (afterSalesDetailVo.getAfterType().intValue() == 539
                    || afterSalesDetailVo.getAfterType().intValue() == 543
                    || afterSalesDetailVo.getAfterType().intValue() == 551) {
                capitalBillDetail.setBussinessType(531);// 退款
            }
            else {
                capitalBillDetail.setBussinessType(525);// 订单付款
            }
            capitalBillDetail.setOrderType(3);// 采购订单
            capitalBillDetail.setRelatedId(payApplyInfo.getRelatedId());
            capitalBillDetail.setOrderNo(afterSalesVo.getAfterSalesNo());
            capitalBillDetail.setTraderId(afterSalesVo.getTraderId());
            if (afterSalesVo.getSubjectType() == 535) {
                capitalBillDetail.setTraderType(1);
            }
            if (afterSalesVo.getSubjectType() == 536) {
                capitalBillDetail.setTraderType(2);
            }

            capitalBillDetail.setUserId(afterSalesVo.getServiceUserId());
            capitalBillDetail.setAfterSalesInstallstionId(afterSalesInstallstionId);
            if (belongUser != null && belongUser.getOrgName() != null && belongUser.getOrgId() != null) {
                capitalBillDetail.setOrgName(belongUser.getOrgName());
                capitalBillDetail.setOrgId(belongUser.getOrgId());
            }
            capitalBill.setCapitalBillDetail(capitalBillDetail);
            ResultInfo<?> resultInfo = new ResultInfo<>();
            // 销售订单退货,销售订单退款,第三方退款
            if (afterSalesDetailVo.getAfterType().intValue() == 539
                    || afterSalesDetailVo.getAfterType().intValue() == 543
                    || afterSalesDetailVo.getAfterType().intValue() == 551) {
                resultInfo = capitalBillService.saveRefundCapitalBill(capitalBill);
            }
            else {
                resultInfo = capitalBillService.saveAddCapitalBill(capitalBill);
            }
            // 修改订单结款匹配项目
            Integer matchedObject = 857;
            // 销售订单退货,销售订单退款,第三方退款
            if (afterSalesDetailVo.getAfterType().intValue() == 539
                    || afterSalesDetailVo.getAfterType().intValue() == 543
                    || afterSalesDetailVo.getAfterType().intValue() == 551) {
                // 客户退款
                matchedObject = 858;
                // 销售订单安调、第三方安调
            }
            else if (afterSalesDetailVo.getAfterType().intValue() == 541
                    || afterSalesDetailVo.getAfterType().intValue() == 550) {
                // 售后安调
                matchedObject = 859;
                // 销售订单维修,第三方维修
            }
            else if (afterSalesDetailVo.getAfterType().intValue() == 584
                    || afterSalesDetailVo.getAfterType().intValue() == 585) {
                // 售后维修
                matchedObject = 860;
            }
            BankBill bankBillInfo = new BankBill();
            bankBillInfo.setBankBillId(bankBillId);
            bankBillInfo.setMatchedObject(matchedObject);
            bankBillService.editBankBill(bankBillInfo);

            // 添加完执行流程审核
            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("pass", true);
            // 审批操作
            try {
                Integer status = 0;
                ResultInfo<?> complementStatus = actionProcdefService.complementTask(request, taskId,
                        request.getParameter("comments"), user.getUsername(), variables);
                // 如果未结束添加审核对应主表的审核状态
                if (!complementStatus.getData().equals("endEvent")) {
                    verifiesRecordService.saveVerifiesInfo(taskId, status);
                }
                return resultInfo;
            }
            catch (Exception e) {
                logger.error("saveAddAfterCapitalBillForBankNoFormToken 1:", e);
                return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
            }
        }
        catch (Exception e) {
            logger.error("saveAddAfterCapitalBillForBankNoFormToken 2:", e);
        }
        return new ResultInfo<>();
    }
    
    
    
    

    /**
     * 
     * <b>Description:</b><br> 根据银行流水ID展示凭证
     * @param session
     * @param bankBillId
     * @return
     * @Note
     * <b>Author:</b> Michael
     * <br><b>Date:</b> 2018年7月9日 上午9:25:27
     */
    @ResponseBody
    @RequestMapping(value = "/credentials")
    public ModelAndView credentials(HttpSession session, Integer bankBillId) {
        ModelAndView mv = new ModelAndView();
        BankBill bankBill= bankBillService.getBankBillById(bankBillId);
        try {
            String amtChinese = DigitToChineseUppercaseNumberUtils.numberToChineseNumber(bankBill.getAmt());
            bankBill.setAmtChinese(amtChinese);
            DateFormat format= new SimpleDateFormat("HH:mm:ss");  
            try {
            Date date = format.parse(bankBill.getTrantime());
            SimpleDateFormat formatter = new SimpleDateFormat("HHmmss");
            String dateString = formatter.format(date);
            bankBill.setTrantime(dateString);
            } catch (ParseException e) {
                logger.error("credentials parse error:", e);
            }
            
        } catch (ShowErrorMsgException e) {
            logger.error("credentials show error msg:", e);
        }
        if(bankBill != null && bankBill.getBankTag() != null && bankBill.getBankTag() == 2){
            //南京银行
            mv.setViewName("finance/capitalbill/njCredentials");
        }
        if(bankBill != null && bankBill.getBankTag() != null && bankBill.getBankTag() == 3){
            //中国银行
            List<SysOptionDefinition> macthObjectList = getSysOptionDefinitionList(869);
            //业务类型
            String businessTypes = "";
            if(macthObjectList != null ){
                for(SysOptionDefinition s:macthObjectList){
                    if(s.getRelatedField().equals(bankBill.getCreNo())){
                        businessTypes = s.getTitle();
                    }
                }
            }
            //交易时间
            Long time = DateUtil.convertLong(bankBill.getTrandate(), "yyyy-mm-dd");
            String nowTime= DateUtil.convertString(time, "yyyy年mm月dd日");
            bankBill.setTrandate(nowTime);
            String remark = "";
            if(bankBill.getMessage() != null){
                 remark = bankBill.getMessage().substring(bankBill.getMessage().indexOf("//A:")+4, bankBill.getMessage().lastIndexOf("//U:"));
            }
            mv.addObject("remark", remark);
            String ps = "";
            if(bankBill.getMessage() != null){
                ps = bankBill.getMessage().substring(2, bankBill.getMessage().lastIndexOf("//A:"));
            }
            mv.addObject("ps", ps);
            
            
            mv.addObject("businessTypes", businessTypes);
            mv.setViewName("finance/capitalbill/chinaCredentials");
        }
            mv.addObject("bankBill", bankBill);
        
        return mv;
    }
}
