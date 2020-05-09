
package com.vedeng.order.controller;

import com.common.constants.Contant;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.vedeng.aftersales.model.vo.AfterSalesVo;
import com.vedeng.authorization.model.Organization;
import com.vedeng.authorization.model.Role;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.*;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.model.ReqVo;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.money.OrderMoneyUtil;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.HttpRestClientUtil;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.common.util.StringUtil;
import com.vedeng.common.validator.FormToken;
import com.vedeng.finance.model.CapitalBill;
import com.vedeng.finance.model.CapitalBillDetail;
import com.vedeng.finance.model.Invoice;
import com.vedeng.finance.model.InvoiceApply;
import com.vedeng.goods.model.CoreSpuGenerate;
import com.vedeng.goods.service.VgoodsService;
import com.vedeng.logistics.model.*;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.model.SaleorderGoods;
import com.vedeng.order.model.SaleorderModifyApply;
import com.vedeng.order.model.vo.HcOrderVo;
import com.vedeng.order.model.vo.SaleorderGoodsWarrantyVo;
import com.vedeng.system.model.Attachment;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.trader.model.CommunicateRecord;
import com.vedeng.trader.model.vo.TraderCertificateVo;
import com.vedeng.trader.model.vo.TraderCustomerVo;
import com.vedeng.trader.model.vo.TraderMedicalCategoryVo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.activiti.engine.task.Task;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <b>Description: 耗材订单controller</b><br>
 * <b>Author: Franlin</b>
 *
 * @fileName HcOrderController.java <br>
 * <b>Date: 2018年10月25日 下午5:24:31 </b>
 */
@Controller
@RequestMapping("/order/hc")
public class HcOrderController extends SaleorderController {

    /**
     * 记录日志
     */
    private static final Logger LOG = LoggerFactory.getLogger(HcOrderController.class);

    @Autowired
    private VgoodsService goodsService;

    /**
     * <b>Description: 耗材商城订单列表页面</b>
     *
     * @param request
     * @param saleorder [必填]销售订单信息
     * @param pageNo    [必填]页数
     * @param pageSize  [必填]每页数量
     * @return ModelAndView
     * @Note <b>Author：</b> franlin.wu <b>Date:</b> 2018年10月25日 下午5:27:52
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "hcOrderListPage")
    public ModelAndView hcOrderListPage(HttpServletRequest request, Saleorder saleorder,
                                        @RequestParam(required = false, defaultValue = "1") Integer pageNo,
                                        @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        ModelAndView hcOrderListView = new ModelAndView("order/hc/hc_order_list");
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);

        // 参数
        getSaleorderHcOrderReqParams(request, saleorder);
        // 分页
        Page page = getPageTag(request, pageNo, pageSize);
        // 获取订单分页接口
        ReqVo<Saleorder> reqVo = new ReqVo<Saleorder>();
        reqVo.setPage(page);
        reqVo.setReq(saleorder);
        TypeReference<ResultInfo<HcOrderVo>> returnResultType = new TypeReference<ResultInfo<HcOrderVo>>() {
        };
        //筛选条件处理
        List<User> saleUserList = new ArrayList<>();
        //获取该部门下是否有其他相应类型的部门
        if (null != saleorder.getOrgId() && saleorder.getOrgId() != -1) {
            List<User> userList =  userService.getOrgUserList(saleorder,user.getCompanyId());
            saleUserList.addAll(userList);
            saleorder.setSaleUserList(saleUserList);
        }
        //处理归属销售筛选条件
        if (null != saleorder.getOptUserId() && saleorder.getOptUserId() != -1) {
            User saleUser = new User();
            saleUser.setUserId(saleorder.getOptUserId());
            saleUserList.clear();
            saleUserList.add(saleUser);
            saleorder.setSaleUserList(saleUserList);
        }
        ResultInfo<HcOrderVo> result = null;
        try {
            result = (ResultInfo<HcOrderVo>) HttpRestClientUtil
                    .post(restDbUrl + DbRestInterfaceConstant.HC_ORDER_LIST_URI, returnResultType, reqVo);
        } catch (Exception e) {
            LOG.error("耗材商城的erp的订单列表异常", e);
        }

        if (null != result && result.getCode() == 0 && null != result.getData()) {
            HcOrderVo hcOrderVo = result.getData();
            List<Saleorder> orderList = hcOrderVo.getOrderList();
            for (Saleorder order : orderList) {
                if (order == null) {
                    continue;
                }
                Map<String, BigDecimal> moneyData = saleorderService.getSaleorderDataInfo(order.getSaleorderId());
                if (moneyData != null) {
                    order.setRealAmount(moneyData.get("realAmount"));
                    order.setPaymentAmount(OrderMoneyUtil.getPaymentAmount(moneyData));
                }
            }

            hcOrderListView.addObject("saleorderList", orderList);
            hcOrderListView.addObject("page", result.getPage());
        }

        if (null != user) {
            //获取销售部门
            List<Organization> orgList = orgService.getAllOrganizationListByType(SysOptionConstant.ID_310,
                    user.getCompanyId());
            hcOrderListView.addObject("orgList", orgList);
            hcOrderListView.addObject("loginUser", user);
            //归属部门筛选，获取当前部门下的所有销售
            Integer parentId = 0;
            if (saleorder.getOrgId() != null) {
                if (saleorder.getOrgId() != -1) {
                    parentId = saleorder.getOrgId();
                }
            }
            List<User> userList = orgService.getUserListBtOrgId(parentId, SysOptionConstant.ID_310, user.getCompanyId());
            hcOrderListView.addObject("userList", userList);
        }
        hcOrderListView.addObject("saleorder", saleorder);

        return hcOrderListView;
    }

    /**
     * <b>Description: 耗材商城订单详情</b>
     *
     * @param request
     * @return saleorder
     * @Note <b>Author：</b> franlin.wu <b>Date:</b> 2018年10月25日 下午7:10:47
     */
    @FormToken(save = true)
    @RequestMapping(value = "hcOrderDetailsPage")
    public ModelAndView hcOrderDetailsPage(HttpServletRequest request, Saleorder saleorder) {
        ModelAndView hcOrderDetailsView = new ModelAndView("order/hc/hc_order_details");
        User loginUser = (User) request.getSession().getAttribute(Consts.SESSION_USER);

        // 当前公司Id
        Integer companyId = null;
        // 当前操作人
        // Integer userId = null;
        if (null != loginUser) {
            // curr_user
            hcOrderDetailsView.addObject(Consts.SESSION_USER, loginUser);
            companyId = loginUser.getCompanyId();
            // userId = loginUser.getUserId();
        }
        saleorder.setCompanyId(companyId);

        // 当前销售订单ID
        Integer saleorderId = saleorder.getSaleorderId();

        // 获取订单基本信息
        saleorder.setOptType("orderDetail");
        saleorder = saleorderService.getBaseSaleorderInfo(saleorder);

        User user = null;
        // add by franlin.wu for[耗材商城订单] at 2018-11-24 begin
        // 存在订单归属userId
        if (null != saleorder
                && !(null == saleorder.getOwnerUserId() || ErpConst.ZERO.equals(saleorder.getOwnerUserId()))) {
            // 根据userId查询user
            user = userService.getUserById(saleorder.getOwnerUserId());
            // 非null
            if (null != user) {
                // 订单归属人
                saleorder.setOwnerUserName(user.getUsername());
            }
        }
        // add by franlin.wu for[耗材商城订单] at 2018-11-24 end
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

        try {
            // --------------------------- NO 客户信息 begin
            // 根据客户ID查询客户信息
            TraderCustomerVo customer = traderCustomerService.getCustomerBussinessInfo(saleorder.getTraderId());
            hcOrderDetailsView.addObject("customer", customer);
            // 获取订单客户信息
            saleorder.setCustomerTypeStr(getSysOptionDefinition(saleorder.getCustomerType()).getTitle());
            // 客户性质
            saleorder.setCustomerNatureStr(getSysOptionDefinition(saleorder.getCustomerNature()).getTitle());
            // 客户资质信息
            Map<String, Object> map = accountPeriodService.getTraderAptitudeInfo(saleorder.getTraderId());
            if (map != null) {
                TraderCertificateVo bus = null;
                // 营业执照信息
                if (map.containsKey("business")) {
                    JSONObject json = JSONObject.fromObject(map.get("business"));
                    bus = (TraderCertificateVo) JSONObject.toBean(json, TraderCertificateVo.class);
                    hcOrderDetailsView.addObject("business", bus);
                }
                // 税务登记信息
                TraderCertificateVo tax = null;
                if (map.containsKey("tax")) {
                    JSONObject json = JSONObject.fromObject(map.get("tax"));
                    tax = (TraderCertificateVo) JSONObject.toBean(json, TraderCertificateVo.class);
                    hcOrderDetailsView.addObject("tax", tax);
                }
                // 组织机构信息
                TraderCertificateVo orga = null;
                if (map.containsKey("orga")) {
                    JSONObject json = JSONObject.fromObject(map.get("orga"));
                    orga = (TraderCertificateVo) JSONObject.toBean(json, TraderCertificateVo.class);
                    hcOrderDetailsView.addObject("orga", orga);
                }
                // 客户
                hcOrderDetailsView.addObject("customerProperty", saleorder.getCustomerNature());
                if (saleorder.getCustomerNature().intValue() == 465) {// 分销
                    // 二类医疗资质
                    List<TraderCertificateVo> twoMedicalList = null;
                    if (map.containsKey("twoMedical")) {
                        // JSONObject json = JSONObject.fromObject(map.get("twoMedical"));
                        twoMedicalList = (List<TraderCertificateVo>) map.get("twoMedical");
                        hcOrderDetailsView.addObject("twoMedicalList", twoMedicalList);
                    }
                    // 二类医疗资质详情
                    List<TraderMedicalCategoryVo> list = null;
                    if (map.containsKey("medicalCertificate")) {
                        JSONArray jsonArray = JSONArray.fromObject(map.get("medicalCertificate"));
                        list = (List<TraderMedicalCategoryVo>) JSONArray.toCollection(jsonArray,
                                TraderMedicalCategoryVo.class);
                        hcOrderDetailsView.addObject("medicalCertificates", list);
                    }
                    // 三类医疗资质
                    TraderCertificateVo threeMedical = null;
                    if (map.containsKey("threeMedical")) {
                        JSONObject json = JSONObject.fromObject(map.get("threeMedical"));
                        threeMedical = (TraderCertificateVo) JSONObject.toBean(json, TraderCertificateVo.class);
                        hcOrderDetailsView.addObject("threeMedical", threeMedical);
                    }
                } else {// 终端
                    // 医疗机构执业许可证
                    List<TraderCertificateVo> practiceList = null;
                    if (map.containsKey("practice")) {
                        practiceList = (List<TraderCertificateVo>) map.get("practice");
                        hcOrderDetailsView.addObject("practiceList", practiceList);
                    }
                }
            }
            // --------------------------- NO 客户信息 end
            // --------------------------- NO 终端类型 begin
            // 终端类型
            saleorder.setTerminalTraderTypeStr(getSysOptionDefinition(saleorder.getTerminalTraderType()).getTitle());
            // --------------------------- NO 终端类型 end
            // --------------------------- NO 订单产品 begin
            // 获取订单产品信息,以及所有产品的手填产品成本总和totalAmount
            Saleorder sale = new Saleorder();
            sale.setSaleorderId(saleorderId);
            sale.setTraderId(saleorder.getTraderId());
            sale.setCompanyId(companyId);
            sale.setReqType(1);
            // add by franlin.wu for[耗材商城订单类型] at 2018-12-05 begin
            sale.setOrderType(OrderConstant.ORDER_TYPE_HC);
            // add by franlin.wu for[耗材商城订单类型] at 2018-12-05 end
            List<SaleorderGoods> saleorderGoodsList = saleorderService.getSaleorderGoodsById(sale);
            // 所有产品的手填成本总价
            hcOrderDetailsView.addObject("totalReferenceCostPrice", sale.getFiveTotalAmount());
            // 产品结算价
            saleorderGoodsList = goodsSettlementPriceService.getGoodsSettlePriceBySaleorderGoodsList(companyId,
                    saleorderGoodsList);
            // 计算核价信息
            if (customer != null) {
                saleorderGoodsList = goodsChannelPriceService.getSaleChannelPriceList(saleorder.getSalesAreaId(),
                        saleorder.getCustomerNature(), customer.getOwnership(), saleorderGoodsList);
            }

            //新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
            if(!CollectionUtils.isEmpty(saleorderGoodsList)){
                List<Integer> skuIds = new ArrayList<>();
                saleorderGoodsList.stream().forEach(saleGood -> {
                    skuIds.add(saleGood.getGoodsId());
                });
                List<Map<String,Object>> skuTipsMap = this.goodsService.skuTipList(skuIds);
                Map<String,Map<String,Object>> newSkuInfosMap = skuTipsMap.stream().collect(Collectors.toMap(key->key.get("SKU_NO").toString(), v -> v, (k, v) -> v));
                hcOrderDetailsView.addObject("newSkuInfosMap", newSkuInfosMap);
            }
            //新商品流切换 VDERP-1970 add by brianna 2020/3/16 end

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

            saleorder = saleorderService.updateisOpenInvoice(saleorder,saleorderGoodsList);
            // add by Tomcat.Hui 2019/11/28 15:40 .Desc: VDERP-1325 分批开票 已开票数量/开票中数量/该订单该sku的数量. end


            List<ExpressDetail> expresseList = expressService.getSEGoodsNum(saleorderGoodsList.stream().map(SaleorderGoods::getSaleorderGoodsId).collect(Collectors.toList()));
            hcOrderDetailsView.addObject("expresseList", expresseList);

            hcOrderDetailsView.addObject("saleorderGoodsList", saleorderGoodsList);

            // --------------------------- NO 物流 begin
            // 物流信息
            List<Express> expressList = null;
            if (saleorderGoodsList.size() > 0) {
                Express express = new Express();
                express.setBusinessType(SysOptionConstant.ID_496);
                express.setCompanyId(companyId);
                List<Integer> relatedIds = new ArrayList<Integer>();
                for (SaleorderGoods sg : saleorderGoodsList) {
                    relatedIds.add(sg.getSaleorderGoodsId());
                }
                express.setRelatedIds(relatedIds);
                try {
                    expressList = expressService.getExpressList(express);
                    hcOrderDetailsView.addObject("expressList", expressList);
                } catch (Exception e) {
                    LOG.error(Contant.ERROR_MSG, e);
                }
            }

            //新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
            if(!CollectionUtils.isEmpty(expressList)){

                List<Integer> goodsIds = new ArrayList<>();

                expressList.stream().forEach(expressItem->{
                    if(!CollectionUtils.isEmpty(expressItem.getExpressDetail())){
                        expressItem.getExpressDetail().stream().forEach(expressDetail -> {
                            goodsIds.add( expressDetail.getGoodsId());
                        });
                    }
                });

                if(CollectionUtils.isNotEmpty(goodsIds)){
                    List<CoreSpuGenerate> spuLists = this.goodsService.findSpuNamesBySpuIds(goodsIds);
                    Map<Integer,String> spuMap = spuLists.stream().collect(Collectors.toMap(k->k.getSpuId(), v -> v.getSpuName(), (k, v) -> v));
                    hcOrderDetailsView.addObject("spuMap", spuMap);
                }
            }
            //新商品流切换 VDERP-1970 add by brianna 2020/3/16 end

            // --------------------------- NO 物流 end
            // --------------------------- NO 收票信息 begin

            // add by Tomcat.Hui 2019/12/30 16:29 .Desc: VDERP-1039 票货同行. start

            /**
             * （1）HC订单
             * （2）开票方式为电子发票
             * （3）订单中全部商品的的发货方式为“普发”
             * （4）订单存在非“已关闭”状态的“销售订单退货”或“销售订单退票”的售后单
             * */
            boolean phtxFlag = invoiceService.getOrderIsGoodsPeer(saleorder.getSaleorderId());

            boolean allSendFlag = true;
            /** 商品全部寄出后，若订单存在需要提交开票的商品，可在订单详情页申请开票，此时订单详情页展示“申请开票”或“申请提前开票”按钮. */
            if (!phtxFlag && CollectionUtils.isEmpty(expressList)) {
                allSendFlag = false;
            } else {
                //已发商品map
                Map<Integer, Integer> sendMap = new HashMap<>();
                expressList.stream().forEach(e -> e.getExpressDetail().stream().forEach(d -> {
                    if (null != sendMap.get(d.getRelatedId())) {
                        sendMap.put(d.getRelatedId(), d.getNum() + sendMap.get(d.getRelatedId()));
                    } else {
                        sendMap.put(d.getRelatedId(), d.getNum());
                    }
                }));

                //遍历匹配 排除运费
                for (SaleorderGoods goods : saleorderGoodsList) {
                    if(goods==null|| StringUtils.isBlank(goods.getSku())){
                        continue;
                    }
                    if (!goods.getSku().equals("V127063")) {
                        if(!sendMap.containsKey(goods.getSaleorderGoodsId())){
                            log.info("订单 已发商品 map不包含" + saleorder.getSaleorderNo() + " 商品: " + goods.getSaleorderGoodsId() + "已发货数量: "
                                    + 0 + " 实际数量: " + 0 + " 未全部寄出");
                            allSendFlag = false;
                            continue;
                        }
                        //非运费商品商品数量不等于寄送数量算作未全部寄出
                        Integer sendNum = sendMap.get(goods.getSaleorderGoodsId()) - goods.getAfterReturnNum();
                        Integer realNum = goods.getNum() - goods.getAfterReturnNum();
                        if (null == sendMap.get(goods.getSaleorderGoodsId()) || !(sendNum).equals(realNum)) {
                            log.info("订单" + saleorder.getSaleorderNo() + " 商品: " + goods.getSaleorderGoodsId() + "已发货数量: "
                                    + sendNum + " 实际数量: " + realNum + " 未全部寄出");
                            allSendFlag = false;
                        }
                    }
                }
            }

            hcOrderDetailsView.addObject("phtxFlag", phtxFlag);
            hcOrderDetailsView.addObject("allSendFlag", allSendFlag);
            // add by Tomcat.Hui 2019/12/30 16:29 .Desc: VDERP-1039 票货同行. end

            // 获取发票信息
            List<Invoice> saleInvoiceList = invoiceService.getInvoiceInfoByRelatedId(saleorder.getSaleorderId(),
                    SysOptionConstant.ID_505);
            hcOrderDetailsView.addObject("saleInvoiceList", saleInvoiceList);
            // --------------------------- NO 收票信息 end
            // --------------------------- NO 开票申请 begin
            // 开票申请
            List<InvoiceApply> saleInvoiceApplyList = invoiceService.getSaleInvoiceApplyList(saleorder.getSaleorderId(),
                    SysOptionConstant.ID_505, -1);
            hcOrderDetailsView.addObject("saleInvoiceApplyList", saleInvoiceApplyList);
            // --------------------------- NO 开票申请 end

            // --------------------------- NO 出库记录 begin
            // 出库记录
            saleorder.setBussinessType(2);
            // 出库记录清单
            List<WarehouseGoodsOperateLog> warehouseOutList = warehouseOutService.getOutDetil(saleorder);
            List<String> timeArrayWl = new ArrayList<>();
            if (null != warehouseOutList) {
                for (WarehouseGoodsOperateLog wl : warehouseOutList) {
                    User u = userService.getUserById(wl.getCreator());
                    wl.setOpName(u.getUsername());
                    timeArrayWl.add(DateUtil.convertString(wl.getAddTime(), "YYYY-MM-dd HH:mm:ss"));
                }
                HashSet<String> wArray = new HashSet<String>(timeArrayWl);
                hcOrderDetailsView.addObject("timeArrayWl", wArray);
            }
            hcOrderDetailsView.addObject("warehouseOutList", warehouseOutList);
            // --------------------------- NO 出库记录 end

            // 付款方式列表
            List<SysOptionDefinition> paymentTermList = getSysOptionDefinitionList(SysOptionConstant.ID_418);
            hcOrderDetailsView.addObject("paymentTermList", paymentTermList);

            // 发货方式
            List<SysOptionDefinition> deliveryTypes = getSysOptionDefinitionList(480);
            hcOrderDetailsView.addObject("deliveryTypes", deliveryTypes);

            // 获取物流公司列表
            List<Logistics> logisticsList = getLogisticsList(companyId);
            hcOrderDetailsView.addObject("logisticsList", logisticsList);

            // 运费说明
            List<SysOptionDefinition> freightDescriptions = getSysOptionDefinitionList(469);
            hcOrderDetailsView.addObject("freightDescriptions", freightDescriptions);
            // 发票类型
            List<SysOptionDefinition> invoiceTypes = getSysOptionDefinitionList(428);
            hcOrderDetailsView.addObject("invoiceTypes", invoiceTypes);
            // 沟通类型为商机和报价和销售订单
            CommunicateRecord cr = new CommunicateRecord();
            if (saleorder.getQuoteorderId() != null && saleorder.getQuoteorderId() != 0) {
                cr.setQuoteorderId(saleorder.getQuoteorderId());
            }
            if (saleorder.getBussinessChanceId() != null && saleorder.getBussinessChanceId() != 0) {
                cr.setBussinessChanceId(saleorder.getBussinessChanceId());
            }
            cr.setSaleorderId(saleorder.getSaleorderId());
            cr.setCompanyId(companyId);
            Page page = Page.newBuilder(null, null, null);
            List<CommunicateRecord> communicateList = traderCustomerService.getCommunicateRecordListPage(cr, page);
            if (!communicateList.isEmpty()) {
                // 沟通内容
                hcOrderDetailsView.addObject("communicateList", communicateList);
                hcOrderDetailsView.addObject("page", page);
            }

            // 获取订单合同回传&订单送货单回传列表
            List<Attachment> saleorderAttachmentList = saleorderService.getSaleorderAttachmentList(saleorderId);
            hcOrderDetailsView.addObject("saleorderAttachmentList", saleorderAttachmentList);

            // 售后订单列表
            AfterSalesVo as = new AfterSalesVo();
            as.setOrderId(saleorderId);
            as.setSubjectType(535);
            List<AfterSalesVo> asList = afterSalesOrderService.getAfterSalesVoListByOrderId(as);
            hcOrderDetailsView.addObject("asList", asList);
            // 获取交易信息数据
            CapitalBill capitalBill = new CapitalBill();
            capitalBill.setOperationType("finance_sale_detail");
            CapitalBillDetail capitalBillDetail = new CapitalBillDetail();
            capitalBillDetail.setOrderType(ErpConst.ONE);// 销售订单类型
            capitalBillDetail.setOrderNo(saleorder.getSaleorderNo());
            capitalBillDetail.setRelatedId(saleorderId);
            capitalBill.setCapitalBillDetail(capitalBillDetail);
            List<CapitalBill> capitalBillList = capitalBillService.getCapitalBillList(capitalBill);
            hcOrderDetailsView.addObject("capitalBillList", capitalBillList);
            // 资金流水交易方式
            List<SysOptionDefinition> traderModes = getSysOptionDefinitionList(519);
            hcOrderDetailsView.addObject("traderModes", traderModes);

            // 资金流水业务类型
            List<SysOptionDefinition> bussinessTypes = getSysOptionDefinitionList(524);
            hcOrderDetailsView.addObject("bussinessTypes", bussinessTypes);
            // 订单修改申请列表（不分页）
            SaleorderModifyApply saleorderModifyApply = new SaleorderModifyApply();
            saleorderModifyApply.setSaleorderId(saleorderId);
            List<SaleorderModifyApply> saleorderModifyApplyList = saleorderService
                    .getSaleorderModifyApplyList(saleorderModifyApply);
            hcOrderDetailsView.addObject("saleorderModifyApplyList", saleorderModifyApplyList);

            // 录保卡
            List<SaleorderGoodsWarrantyVo> goodsWarrantys = saleorderService.getSaleorderGoodsWarrantys(saleorderId);
            hcOrderDetailsView.addObject("goodsWarrantys", goodsWarrantys);
            // 查询当前订单的一些状态
            User userInfo = userService.getUserByTraderId(saleorder.getTraderId(), 1);// 1客户，2供应商
            saleorder.setOptUserName(userInfo == null ? "" : userInfo.getUsername());
            Map<String, Object> historicInfo = actionProcdefService.getHistoric(processEngine,
                    "saleorderVerify_" + saleorderId);
            hcOrderDetailsView.addObject("taskInfo", historicInfo.get("taskInfo"));
            hcOrderDetailsView.addObject("startUser", historicInfo.get("startUser"));
            // 最后审核状态
            hcOrderDetailsView.addObject("endStatus", historicInfo.get("endStatus"));
            hcOrderDetailsView.addObject("historicActivityInstance", historicInfo.get("historicActivityInstance"));
            hcOrderDetailsView.addObject("commentMap", historicInfo.get("commentMap"));
            hcOrderDetailsView.addObject("candidateUserMap", historicInfo.get("candidateUserMap"));
            Task taskInfo = (Task) historicInfo.get("taskInfo");
            // 当前审核人
            String verifyUsers = null;
            List<String> verifyUserList = new ArrayList<>();
            if (null != taskInfo) {
                Map<String, Object> taskInfoVariables = actionProcdefService.getVariablesMap(taskInfo);
                verifyUsers = (String) taskInfoVariables.get("verifyUsers");
                String verifyUser = (String) taskInfoVariables.get("verifyUserList");
                if (null != verifyUser) {
                    verifyUserList = Arrays.asList(verifyUser.split(","));
                }
            }
            List<String> verifyUsersList = new ArrayList<>();
            if (verifyUsers != null && !verifyUserList.isEmpty()) {
                verifyUsersList = Arrays.asList(verifyUsers.split(","));
            }
            hcOrderDetailsView.addObject("verifyUsers", verifyUsers);
            hcOrderDetailsView.addObject("verifyUserList", verifyUserList);
            hcOrderDetailsView.addObject("verifyUsersList", verifyUsersList);
            // 提前采购审核信息
            Map<String, Object> historicInfoEarly = actionProcdefService.getHistoric(processEngine,
                    "earlyBuyorderVerify_" + saleorderId);
            hcOrderDetailsView.addObject("taskInfoEarly", historicInfoEarly.get("taskInfo"));
            hcOrderDetailsView.addObject("startUserEarly", historicInfoEarly.get("startUser"));
            hcOrderDetailsView.addObject("endStatusEarly", historicInfoEarly.get("endStatus"));
            hcOrderDetailsView.addObject("historicActivityInstanceEarly",
                    historicInfoEarly.get("historicActivityInstance"));
            hcOrderDetailsView.addObject("commentMapEarly", historicInfoEarly.get("commentMap"));
            hcOrderDetailsView.addObject("candidateUserMapEarly", historicInfoEarly.get("candidateUserMap"));
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
            List<String> verifyUsersListEarly = new ArrayList<>();
            if (verifyUsersEarly != null && !verifyUserListEarly.isEmpty()) {
                verifyUsersListEarly = Arrays.asList(verifyUsersEarly.split(","));
            }
            hcOrderDetailsView.addObject("verifyUsersEarly", verifyUsersEarly);
            hcOrderDetailsView.addObject("verifyUserListEarly", verifyUserListEarly);
            hcOrderDetailsView.addObject("verifyUsersListEarly", verifyUsersListEarly);

            // 合同回传审核信息
            Map<String, Object> historicInfoContractReturn = actionProcdefService.getHistoric(processEngine,
                    "contractReturnVerify_" + saleorderId);
            Task taskInfoContractReturn = (Task) historicInfoContractReturn.get("taskInfo");
            hcOrderDetailsView.addObject("taskInfoContractReturn", taskInfoContractReturn);
            hcOrderDetailsView.addObject("startUserContractReturn", historicInfoContractReturn.get("startUser"));
            // 最后审核状态
            hcOrderDetailsView.addObject("endStatusContractReturn", historicInfoContractReturn.get("endStatus"));
            hcOrderDetailsView.addObject("historicActivityInstanceContractReturn",
                    historicInfoContractReturn.get("historicActivityInstance"));
            hcOrderDetailsView.addObject("commentMapContractReturn", historicInfoContractReturn.get("commentMap"));
            hcOrderDetailsView.addObject("candidateUserMapContractReturn",
                    historicInfoContractReturn.get("candidateUserMap"));
            String verifyUsersContractReturn = null;
            if (null != taskInfoContractReturn) {
                Map<String, Object> taskInfoVariablesContractReturn = actionProcdefService
                        .getVariablesMap(taskInfoContractReturn);
                verifyUsersContractReturn = (String) taskInfoVariablesContractReturn.get("verifyUsers");
            }
            hcOrderDetailsView.addObject("verifyUsersContractReturn", verifyUsersContractReturn);

            // 获取交易信息（订单实际金额，客户已付款金额）
            Map<String, BigDecimal> saleorderDataInfo = saleorderService.getSaleorderDataInfo(saleorderId);
            hcOrderDetailsView.addObject("saleorderDataInfo", saleorderDataInfo);

            /*锁校验*/
            Boolean AftFlag=false;
            if (asList!=null && asList.size()!=0){
                for (AfterSalesVo aft:asList) {
                    if ((aft.getAtferSalesStatus()==0) || (aft.getAtferSalesStatus()==1)){
                        AftFlag=true;
                    }
                }
            }
            if (AftFlag){
                saleorder.setLockedStatus(1);
                saleorderService.updateLockedStatus(saleorder.getSaleorderId());
            }
        } catch (Exception e) {
            LOG.error(Contant.ERROR_MSG, e);
        }
        // 计算是否是当月
        String nextMonthFirst = null;
        // 跨月表示（0表示未跨月 1表示跨月）
        Integer isCrossMonth = 0;
        try {
            // 如果有生效时间
            if (saleorder.getValidTime() != null && saleorder.getValidTime() != 0) {
                nextMonthFirst = DateUtil
                        .getNextMonthFirst(DateUtil.convertString(saleorder.getValidTime(), "yyyy-MM-dd"));
                String nowTimeDate = DateUtil.getNowDate("yyyy-MM-dd");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                // 下个月1号
                Date nmf = sdf.parse(nextMonthFirst);
                // 当前时间
                Date ntd = sdf.parse(nowTimeDate);
                // 当前时间小于 跨月时间
                if (!ntd.before(nmf)) {
                    isCrossMonth = 1;
                }
            }
        } catch (ParseException e) {
            LOG.error("耗材商城的erp的订单详情异常", e);
        }
        hcOrderDetailsView.addObject("isCrossMonth", isCrossMonth);
        hcOrderDetailsView.addObject("saleorder", saleorder);
        // 非空
        if (null != saleorder) {
            // 订单类型
            hcOrderDetailsView.addObject("orderType", saleorder.getOrderType());
        }

        return hcOrderDetailsView;
    }

    /**
     * <b>Description: 获取</b>
     *
     * @param request
     * @param saleorder [必填]销售订单信息
     * @return Saleorder
     * @Note <b>Author：</b> franlin.wu <b>Date:</b> 2018年10月26日 上午9:15:07
     */
    private void getSaleorderHcOrderReqParams(HttpServletRequest request, Saleorder saleorder) {
        if (null == request || null == saleorder) {
            return;
        }

        // 默认订单类型 5 耗材商城
        saleorder.setOrderType(OrderConstant.ORDER_TYPE_HC);

        // 搜索开始时间
        String searchBegintimeStr = request.getParameter("searchBegintimeStr");
        // 搜索结束时间
        String searchEndtimeStr = request.getParameter("searchEndtimeStr");
        // 搜索时间类型:1-创建时间/2-生效时间/3-付款时间/4-发货时间/5-收货时间/6-开票时间
        Integer searchDateType = saleorder.getSearchDateType();

        if (null == searchDateType) {
            // 默认生效时间
            saleorder.setSearchDateType(ErpConst.TWO);
        }

        if (StringUtil.isNotBlank(searchBegintimeStr)) {
            saleorder.setSearchBegintime(DateUtil.convertLong(searchBegintimeStr + " 00:00:00", DateUtil.TIME_FORMAT));
        }
        if (StringUtil.isNotBlank(searchEndtimeStr)) {
            saleorder.setSearchEndtime(DateUtil.convertLong(searchEndtimeStr + " 23:59:59", DateUtil.TIME_FORMAT));
        }

    }

    /**
     * <b>Description:</b><br>
     * 审核弹层页面
     *
     * @param session
     * @param taskId
     * @param pass
     * @param type
     * @return
     * @Note <b>Author:</b> leo.yang <br>
     * <b>Date:</b> 2018年1月5日 下午3:16:51 as to 20190115 添加参数saleorderId用于同步订单状态
     */
    @FormToken(save = true)
    @ResponseBody
    @RequestMapping(value = "/complement")
    public ModelAndView complement(HttpSession session, String taskId, Boolean pass, Integer type,
                                   Integer saleorderId) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("taskId", taskId);
        mv.addObject("saleorderId", saleorderId);
        mv.addObject("pass", pass);
        mv.addObject("type", type);
        mv.setViewName("order/saleorder/complement");
        return mv;
    }

    /**
     * 根据部门ID获取相应类型的职位的用户列表
     *
     * @param session
     * @param orgId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getUserListByOrgId")
    public ResultInfo getUserListByOrgId(HttpSession session, Integer orgId) {
        try {
            User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
            List<User> userList = orgService.getUserListBtOrgId(orgId, SysOptionConstant.ID_310,
                    session_user.getCompanyId());
            return new ResultInfo(CommonConstants.SUCCESS_CODE, CommonConstants.SUCCESS_MSG, userList);
        } catch (Exception e) {
            logger.error("根据部门ID获取相应类型的职位的用户列表", e);
            return new ResultInfo(CommonConstants.FAIL_CODE, CommonConstants.FAIL_MSG);
        }
    }
    @ResponseBody
    @RequestMapping(headers = "version=v1",value="/updateorderstatus", method = RequestMethod.POST)
    public ResultInfo updateOrderStatus(HttpServletRequest request, HttpServletResponse response){
        String param_data = request.getParameter("data");
        try{
            //获取参数
            Map<String, Object> map = JsonUtils.readValue(param_data, Map.class);
            String orderNo = map.get("orderNo").toString();
            Integer accountId = Integer.valueOf(map.get("accountId").toString());
            //参数校验
            if(null == orderNo || "".equals(orderNo)){
                return new ResultInfo(-1, "入参错误，订单号为空");
            }
            if(null == accountId){
                return new ResultInfo(-1, "入参错误，用户ID为空");
            }
            log.info("取消订单updateOrderStatus订单号:{}",orderNo);
            //新建参数
            Saleorder saleorder = new Saleorder();
            saleorder.setSaleorderNo(orderNo);//订单号
            saleorder.setUpdater(accountId);//更新人ID
            saleorder.setModTime(DateUtil.sysTimeMillis());//更新时间
            saleorder.setStatus(3);//订单状态设为关闭
            //更新订单状态
            return saleorderService.updateOrderStatusByOrderNo(saleorder);
        }catch(Exception e){
           log.error("取消订单失败updateOrderStatus:{}",e);
            return new ResultInfo(-1, "更新订单状态：操作失败：" + e.getMessage());
        }
    }

}
