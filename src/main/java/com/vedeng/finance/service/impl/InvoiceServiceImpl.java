package com.vedeng.finance.service.impl;

import com.alibaba.fastjson.JSON;
import com.common.constants.Contant;
import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.aftersales.model.AfterSales;
import com.vedeng.aftersales.model.vo.AfterSalesGoodsVo;
import com.vedeng.aftersales.model.vo.AfterSalesVo;
import com.vedeng.aftersales.service.AfterSalesService;
import com.vedeng.authorization.dao.UserMapper;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.CommonConstants;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.OrderDataUpdateConstant;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.key.CryptBase64Tool;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.HttpRequest;
import com.vedeng.common.util.MessageUtil;
import com.vedeng.common.util.XmlExercise;
import com.vedeng.finance.dao.InvoiceApplyMapper;
import com.vedeng.finance.dao.InvoiceMapper;
import com.vedeng.finance.model.Invoice;
import com.vedeng.finance.model.InvoiceApply;
import com.vedeng.finance.model.InvoiceApplyDetail;
import com.vedeng.finance.model.InvoiceDetail;
import com.vedeng.finance.service.InvoiceService;
import com.vedeng.goods.model.GoodsAttachment;
import com.vedeng.logistics.model.Express;
import com.vedeng.logistics.model.ExpressDetail;
import com.vedeng.logistics.service.ExpressService;
import com.vedeng.logistics.service.impl.ExpressServiceImpl;
import com.vedeng.order.dao.SaleorderGoodsMapper;
import com.vedeng.order.dao.SaleorderMapper;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.model.SaleorderGoods;
import com.vedeng.order.model.vo.BuyorderVo;
import com.vedeng.order.service.SaleorderService;
import com.vedeng.soap.service.VedengSoapService;
import com.vedeng.trader.dao.WebAccountMapper;
import com.vedeng.trader.model.Trader;
import com.vedeng.trader.model.WebAccount;
import com.vedeng.trader.model.vo.TraderCustomerVo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

@Service("invoiceService")
public class InvoiceServiceImpl extends BaseServiceimpl implements InvoiceService {

    @Resource
    private UserMapper userMapper;

    @Autowired
    @Qualifier("invoiceMapper")
    private InvoiceMapper invoiceMapper;

    @Resource
    private SaleorderGoodsMapper saleorderGoodsMapper;

    @Resource
    private WebAccountMapper webAccountMapper;

    @Resource
    private SaleorderMapper saleorderMapper;

    @Autowired
    @Qualifier("vedengSoapService")
    private VedengSoapService vedengSoapService;

    @Autowired
    @Qualifier("invoiceApplyMapper")
    private InvoiceApplyMapper invoiceApplyMapper;

    @Autowired
    private SaleorderService saleorderService;

    @Autowired
    private ExpressService expressService;

    @Autowired
    private AfterSalesService afterSalesService;

    /**
     * 记录日志
     */
    public static Logger LOG = LoggerFactory.getLogger(ExpressServiceImpl.class);


    @Override
    public Map<String, Object> getInvoiceListByBuyorder(BuyorderVo bo, Invoice invoice, Integer inputInvoiceType)
            throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        if (inputInvoiceType.equals(2)) {// 售后
            invoice.setType(SysOptionConstant.ID_504);
            AfterSalesGoodsVo asv = new AfterSalesGoodsVo();
            asv.setCompanyId(bo.getCompanyId());
            asv.setModel(bo.getModel());
            asv.setTraderName(bo.getTraderName());
            asv.setGoodsName(bo.getGoodsName());
            asv.setBrandName(bo.getBrandName());
            asv.setBuyorderNo(bo.getBuyorderNo());
            asv.setInvoiceType(bo.getInvoiceType());
            List<AfterSalesVo> afterOrderList = null;
            // 定义反序列化 数据格式
            final TypeReference<ResultInfo<List<AfterSalesVo>>> TypeRef =
                    new TypeReference<ResultInfo<List<AfterSalesVo>>>() {};
            String url = httpUrl + "finance/invoice/getafterinvoiceorderlist.htm";
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, asv, clientId, clientKey, TypeRef);
            if (result != null && result.getCode() == 0) {
                afterOrderList = (List<AfterSalesVo>) result.getData();
            }
            List<Integer> list = new ArrayList<>();
            if (afterOrderList != null && !afterOrderList.isEmpty()) {
                for (int i = 0; i < afterOrderList.size(); i++) {
                    list.add(afterOrderList.get(i).getAfterSalesId());
                }
                invoice.setRelatedIdList(list);
                // 定义反序列化 数据格式
                final TypeReference<ResultInfo<List<InvoiceDetail>>> TypeRef2 =
                        new TypeReference<ResultInfo<List<InvoiceDetail>>>() {};
                String url2 = httpUrl + "finance/invoice/getinvoicelistbyrelatedid.htm";
                invoice.setTag(2);// 录票/开票 1开票 2录票
                ResultInfo<?> result2 =
                        (ResultInfo<?>) HttpClientUtils.post(url2, invoice, clientId, clientKey, TypeRef2);
                if (result2 != null && result2.getCode() == 0) {
                    List<InvoiceDetail> invoiceDetailList = (List<InvoiceDetail>) result2.getData();
                    map.put("invoiceDetailList", invoiceDetailList);
                    if (invoiceDetailList != null && afterOrderList != null) {
                        for (int i = 0; i < invoiceDetailList.size(); i++) {
                            for (int j = afterOrderList.size() - 1; j >= 0; j--) {
                                int size = afterOrderList.get(j).getAfterSalesGoodsList().size();
                                for (int m = 0; m < size; m++) {
                                    if (invoiceDetailList.get(i).getRelatedId().intValue() == afterOrderList.get(j)
                                            .getAfterSalesId().intValue()
                                            && invoiceDetailList.get(i).getDetailgoodsId().intValue() == afterOrderList
                                                    .get(j).getAfterSalesGoodsList().get(m).getAfterSalesGoodsId()
                                                    .intValue()) {
                                        if (invoiceDetailList.get(i).getNum().doubleValue() == afterOrderList.get(j)
                                                .getAfterSalesGoodsList().get(m).getNum().doubleValue()) {
                                            afterOrderList.remove(j);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    map.put("afterOrderList", afterOrderList);
                }
            }
        }
        else {
            invoice.setType(SysOptionConstant.ID_503);
            List<BuyorderVo> buyorderList = null;
            // 定义反序列化 数据格式
            final TypeReference<ResultInfo<List<BuyorderVo>>> TypeRef =
                    new TypeReference<ResultInfo<List<BuyorderVo>>>() {};
            String url = httpUrl + "order/buyorder/getinvoicebuyorderlist.htm";
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, bo, clientId, clientKey, TypeRef);
            if (result != null && result.getCode() == 0) {
                buyorderList = (List<BuyorderVo>) result.getData();
            }
            List<Integer> list = new ArrayList<>();
            if (buyorderList != null && !buyorderList.isEmpty()) {
                for (int i = 0; i < buyorderList.size(); i++) {
                    list.add(buyorderList.get(i).getBuyorderId());
                }
                invoice.setRelatedIdList(list);
                // 定义反序列化 数据格式
                final TypeReference<ResultInfo<List<InvoiceDetail>>> TypeRef2 =
                        new TypeReference<ResultInfo<List<InvoiceDetail>>>() {};
                String url2 = httpUrl + "finance/invoice/getinvoicelistbyrelatedid.htm";

                ResultInfo<?> result2 =
                        (ResultInfo<?>) HttpClientUtils.post(url2, invoice, clientId, clientKey, TypeRef2);
                if (result2 != null && result2.getCode() == 0) {
                    List<InvoiceDetail> invoiceDetailList = (List<InvoiceDetail>) result2.getData();
                    map.put("invoiceDetailList", invoiceDetailList);
                    // if(invoiceDetailList!=null && invoiceDetailList.size() > 0 && buyorderList!=null &&
                    // buyorderList.size()>0){
                    // for(int i=0;i<invoiceDetailList.size();i++){
                    // for(int j=buyorderList.size()-1;j>=0;j--){
                    // if(buyorderList.get(j).getBuyorderGoodsVoList() != null &&
                    // buyorderList.get(j).getBuyorderGoodsVoList().size() > 0){
                    // int size = buyorderList.get(j).getBuyorderGoodsVoList().size();
                    // for(int m=0;m<size;m++){
                    // if(buyorderList.size() == 0){
                    // break;
                    // }
                    // if(invoiceDetailList.get(i).getRelatedId().intValue()==buyorderList.get(j).getBuyorderId().intValue()
                    // && invoiceDetailList.get(i).getDetailgoodsId().intValue() ==
                    // buyorderList.get(j).getBuyorderGoodsVoList().get(m).getBuyorderGoodsId().intValue()){
                    // if(invoiceDetailList.get(i).getNum().doubleValue() ==
                    // buyorderList.get(j).getBuyorderGoodsVoList().get(m).getArrivalNum().doubleValue()){
                    // buyorderList.remove(j);
                    // }
                    // }
                    // }
                    // }
                    // }
                    // }
                    // }
                    map.put("buyorderList", buyorderList);
                }
            }
        }
        return map;
    }

    @Override
    public ResultInfo<?> saveInvoice(Invoice invoice) throws Exception {
        ResultInfo<?> result = null;

        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<Saleorder>> TypeRef = new TypeReference<ResultInfo<Saleorder>>() {};
        String url = httpUrl + "finance/invoice/saveinvoice.htm";
        try {
            result = (ResultInfo<?>) HttpClientUtils.post(url, invoice, clientId, clientKey, TypeRef);
            if (result != null) {
                if (result.getCode() == 0 && result.getData() != null && invoice.getType().intValue() == 505) {// 销售
                    Saleorder saleorder = (Saleorder) result.getData();
                    // 根据客户Id查询客户负责人
                    List<Integer> userIdList =
                            userMapper.getUserIdListByTraderId(saleorder.getTraderId(), ErpConst.ONE);
                    Map<String, String> map = new HashMap<>();
                    map.put("saleorderNo", saleorder.getSaleorderNo());
                    // 銷售開票后發送消息給銷售負責人
                    MessageUtil.sendMessage(11, userIdList, map,
                            "./order/saleorder/view.do?saleorderId=" + saleorder.getSaleorderId());
                    //更新订单updateDataTime
                    updateSaleOrderDataUpdateTime(saleorder.getSaleorderId(),null, OrderDataUpdateConstant.SALE_ORDER_INVOICE);
                }
                if(result != null && result.getCode().equals(0) && invoice.getType() != null){
                    List<Integer> relatedIdList = invoice.getRelatedIdList();
                    //售后
                    if(invoice.getType().equals(504)){
                        for (Integer relatedId : relatedIdList) {
                            updateAfterOrderDataUpdateTime(relatedId,null,OrderDataUpdateConstant.AFTER_ORDER_INVOICE);
                        }
                        //采购
                    }else if(invoice.getType().equals(503)){
                        for (Integer relatedId : relatedIdList) {
                            updateBuyOrderDataUpdateTime(relatedId,null,OrderDataUpdateConstant.BUY_ORDER_INVOICE);
                        }
                    }
                }
            }
        }
        catch (IOException e) {
            LOG.error(Contant.ERROR_MSG, e);
            return new ResultInfo<>();
        }
        return result;

    }

    @Override
    public Map<String, Object> getInvoiceAuditListByInvoiceNo(Invoice invoice) {
        Map<String, Object> map = new HashMap<>();
        ResultInfo<?> result = null;
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<Map<String, Object>>> TypeRef =
                new TypeReference<ResultInfo<Map<String, Object>>>() {};
        String url = httpUrl + "finance/invoice/getinvoiceauditlistbyinvoiceno.htm";
        try {
            result = (ResultInfo<?>) HttpClientUtils.post(url, invoice, clientId, clientKey, TypeRef);
            if (result != null && result.getCode() == 0) {
                Map<String, Object> result_map = (Map<String, Object>) result.getData();
                if (result_map != null) {
                    net.sf.json.JSONArray json = null;
                    if (result_map.get("goods_list") != null) {
                        String goodsListStr = result_map.get("goods_list").toString();
                        json = net.sf.json.JSONArray.fromObject(goodsListStr);
                        List<InvoiceDetail> invoiceGoodsList = (List<InvoiceDetail>) json.toCollection(json, InvoiceDetail.class);
                        
                        json = null;
                        String attachmentStr = result_map.get("attachmentList").toString();
                        json = net.sf.json.JSONArray.fromObject(attachmentStr);
                        List<GoodsAttachment> attachmentList = (List<GoodsAttachment>) json.toCollection(json, GoodsAttachment.class);
                        map.put("attachmentList", attachmentList);
                        /*List<orderInfo> orderList = null;
                        for (int i = 0; i < invoiceGoodsList.size(); i++) {
                            json = net.sf.json.JSONArray.fromObject(result_map.get(invoiceGoodsList.get(i).getInvoiceDetailId().toString()));
                            orderList = (List<orderInfo>) json.toCollection(json, orderInfo.class);
                            if (orderList != null && !orderList.isEmpty()) {
                                for (int j = 0; j < orderList.size(); j++) {
                                    orderList.get(j)
                                            .setUserName(userMapper.getUserNameByUserId(orderList.get(j).getUserId()));
                                }
                                invoiceGoodsList.get(i).setOrderList(orderList);
                            }
                        }*/
                        map.put("invoiceGoodsList", invoiceGoodsList);
                    }

                    /*if (result_map.get("invoiceDetailList") != null) {
                        String invoiceDetailStr = result_map.get("invoiceDetailList").toString();
                        json = net.sf.json.JSONArray.fromObject(invoiceDetailStr);

                        List<InvoiceDetail> invoiceDetailList =
                                (List<InvoiceDetail>) json.toCollection(json, InvoiceDetail.class);
                        map.put("invoiceDetailList", invoiceDetailList);
                    }*/

                    invoice = (Invoice) JSONObject.toBean(JSONObject.fromObject(result_map.get("invoice")), Invoice.class);
                    map.put("invoice", invoice);
                }
            }
            return map;
        }
        catch (Exception e) {
            LOG.error(Contant.ERROR_MSG, e);
            return null;
        }
    }

    @Override
    public ResultInfo<?> saveInvoiceAudit(Invoice invoice) {
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
        String url = httpUrl + "finance/invoice/saveinvoiceaudit.htm";
        try {
            return (ResultInfo<?>) HttpClientUtils.post(url, invoice, clientId, clientKey, TypeRef);
        }
        catch (Exception e) {
            LOG.error(Contant.ERROR_MSG, e);
            return new ResultInfo<>();
        }
    }

    @Override
    public Map<String, Object> getInvoiceListPage(Invoice invoice, Page page) {
        Map<String, Object> map = new HashMap<>();
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<Map<String, Object>>> TypeRef =
                new TypeReference<ResultInfo<Map<String, Object>>>() {};
        String url = httpUrl + "finance/invoice/getinvoicelistpage.htm";
        try {
            ResultInfo<?> result =
                    (ResultInfo<?>) HttpClientUtils.post(url, invoice, clientId, clientKey, TypeRef, page);
            if (result != null && result.getCode() == 0) {
                map.put("page", result.getPage());

                Map<String, Object> result_map = (Map<String, Object>) result.getData();
                if (result_map != null && result_map.get("invoiceList") != null) {

                    net.sf.json.JSONArray json = null;
                    String invoiceListStr = result_map.get("invoiceList").toString();
                    json = net.sf.json.JSONArray.fromObject(invoiceListStr);
                    List<Invoice> list = (List<Invoice>) json.toCollection(json, Invoice.class);

                    invoice = (Invoice) JSONObject.toBean(JSONObject.fromObject(result_map.get("invoice")),
                            Invoice.class);
                    map.put("invoice", invoice);
                    // List<Invoice> list = (List<Invoice>)result.getData();

                    if (list != null && !list.isEmpty()) {// 查询录票人员和审核人
                        map.put("invoiceList", list);
                        List<Integer> inputUserIdList = new ArrayList<>();
                        List<Integer> auditUserIdList = new ArrayList<>();
                        for (int i = 0; i < list.size(); i++) {
                            inputUserIdList.add(list.get(i).getCreator());
                            auditUserIdList.add(list.get(i).getUpdater());
                            auditUserIdList.add(list.get(i).getValidUserId());
                        }
                        List<User> inputUserList = userMapper.getUserByUserIds(inputUserIdList);
                        List<User> auditUserList = userMapper.getUserByUserIds(auditUserIdList);
                        map.put("inputUserList", inputUserList);
                        map.put("auditUserList", auditUserList);
                    }
                }
            }
        }
        catch (Exception e) {
            LOG.error(Contant.ERROR_MSG, e);
            return null;
        }
        return map;
    }

    @Override
    public Map<String, Object> exportIncomeInvoiceList(Invoice invoice) {
        Map<String, Object> map = new HashMap<>();
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<Map<String, Object>>> TypeRef =
                new TypeReference<ResultInfo<Map<String, Object>>>() {};
        String url = httpUrl + "finance/invoice/exportincomeinvoicelist.htm";
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, invoice, clientId, clientKey, TypeRef);
            if (result != null && result.getCode() == 0) {
                map.put("page", result.getPage());

                Map<String, Object> result_map = (Map<String, Object>) result.getData();
                if (result_map != null && result_map.get("invoiceList") != null) {

                    net.sf.json.JSONArray json = null;
                    String invoiceListStr = result_map.get("invoiceList").toString();
                    json = net.sf.json.JSONArray.fromObject(invoiceListStr);
                    List<Invoice> list = (List<Invoice>) json.toCollection(json, Invoice.class);

                    invoice = (Invoice) JSONObject.toBean(JSONObject.fromObject(result_map.get("invoice")),
                            Invoice.class);
                    map.put("invoice", invoice);
                    if (list != null && !list.isEmpty()) {// 查询录票人员和审核人
                        map.put("invoiceList", list);
                        List<Integer> inputUserIdList = new ArrayList<>();
                        List<Integer> auditUserIdList = new ArrayList<>();
                        for (int i = 0; i < list.size(); i++) {
                            inputUserIdList.add(list.get(i).getCreator());
                            auditUserIdList.add(list.get(i).getCreator());
                        }
                        List<User> inputUserList = userMapper.getUserByUserIds(inputUserIdList);
                        List<User> auditUserList = userMapper.getUserByUserIds(auditUserIdList);
                        map.put("inputUserList", inputUserList);
                        map.put("auditUserList", auditUserList);
                    }
                }
            }
        }
        catch (Exception e) {
            LOG.error(Contant.ERROR_MSG, e);
            return null;
        }
        return map;
    }

    @Override
    public ResultInfo<?> saveOpenInvoceApply(InvoiceApply invoiceApply) {
        try {
            // 定义反序列化 数据格式
            final TypeReference<ResultInfo<Integer>> TypeRef = new TypeReference<ResultInfo<Integer>>() {};
            String url = httpUrl + "finance/invoice/saveopeninvoceapply.htm";
            ResultInfo<?> result =
                    (ResultInfo<?>) HttpClientUtils.post(url, invoiceApply, clientId, clientKey, TypeRef);
            if (result != null) {
                return result;
            }
        }
        catch (Exception e) {
            LOG.error(Contant.ERROR_MSG, e);
            return new ResultInfo<>(-1, "操作异常");
        }
        return new ResultInfo<>(-1, "操作失败");
    }

    @Override
    public Map<String, Object> getSaleInvoiceApplyListPage(InvoiceApply invoiceApply, Page page) {
        Map<String, Object> map = null;
        try {
            // 定义反序列化 数据格式
            final TypeReference<ResultInfo<Map<String, Object>>> TypeRef =
                    new TypeReference<ResultInfo<Map<String, Object>>>() {};
            String url = httpUrl + "finance/invoice/getsaleinvoiceapplylistpage.htm";
            ResultInfo<?> result =
                    (ResultInfo<?>) HttpClientUtils.post(url, invoiceApply, clientId, clientKey, TypeRef, page);
            if (result != null && result.getCode() == 0) {
                Map<String, Object> result_map = (Map<String, Object>) result.getData();
                if (result_map != null && result_map.size() > 0) {

                    map = new HashMap<String, Object>();
                    net.sf.json.JSONArray json = null;
                    String openInvoiceApplyStr = result_map.get("openInvoiceApplyList").toString();
                    json = net.sf.json.JSONArray.fromObject(openInvoiceApplyStr);

                    List<InvoiceApply> openInvoiceApplyList =
                            (List<InvoiceApply>) json.toCollection(json, InvoiceApply.class);

                    map.put("openInvoiceApplyList", openInvoiceApplyList);



                    //计算订单总额
                    net.sf.json.JSONArray json1 = null;
                    String applyAmountListStr = result_map.get("applyAmountList").toString();
                    json1 = net.sf.json.JSONArray.fromObject(applyAmountListStr);

                    List<InvoiceApply> applyAmountList =
                            (List<InvoiceApply>) json1.toCollection(json1, InvoiceApply.class);
                    //double applyAmountMoney=0;
                    BigDecimal applyAmountMoney=BigDecimal.valueOf(0.00);
                    for (InvoiceApply invoiceApply1:applyAmountList) {
                        if (invoiceApply1.getApplyAmount()==null){
                            invoiceApply1.setApplyAmount(invoiceApply1.getTotalAmount());
                        }
                        //applyAmountMoney=applyAmountMoney+invoiceApply1.getApplyAmount().doubleValue();
                        applyAmountMoney=applyAmountMoney.add(invoiceApply1.getApplyAmount());
                    }
                    map.put("applyAmountMoney",applyAmountMoney);
                    //
                    //根据条件查询所有数据
                    map.put("applyAmountList",applyAmountList);

                    invoiceApply = (InvoiceApply) JSONObject
                            .toBean(JSONObject.fromObject(result_map.get("invoiceApply")), InvoiceApply.class);
                    map.put("invoiceApply", invoiceApply);

                    map.put("page", result.getPage());

                    // map.put("totalMoney", result_map.get("totalMoney"));

                    List<Integer> userIdList = new ArrayList<>();
                    // List<Integer> orgIdList = new ArrayList<>();
                    List<Integer> traderIdList = new ArrayList<>();
                    if (openInvoiceApplyList != null && !openInvoiceApplyList.isEmpty()) {
                        for (int i = 0; i < openInvoiceApplyList.size(); i++) {
                            userIdList.add(openInvoiceApplyList.get(i).getCreator());
                            userIdList.add(openInvoiceApplyList.get(i).getValidUserId());
                            userIdList.add(openInvoiceApplyList.get(i).getUpdater());

                            // orgIdList.add(openInvoiceApplyList.get(i).getOrgId());

                            traderIdList.add(openInvoiceApplyList.get(i).getTraderId());
                        }
                        userIdList = new ArrayList<Integer>(new HashSet<Integer>(userIdList));
                        List<User> userList = userMapper.getUserByUserIds(userIdList);
                        map.put("userList", userList);// 申请人，审核人

                        // List<Organization> orgList = organizationMapper.getOrgByList(orgIdList);
                        // map.put("orgList", orgList);//销售部门

                        List<User> traderUserList = userMapper.getTraderUserAndOrgList(traderIdList);// 1客户，2供应商
                        map.put("traderUserList", traderUserList);// 归属销售
                    }
                    return map;
                }

            }
        }
        catch (Exception e) {
            LOG.error(Contant.ERROR_MSG, e);
            return null;
        }
        return map;
    }

    @Override
    public Map<String, Object> getInvoiceApplyVerifyListPage(InvoiceApply invoiceApply, Page page) {
        Map<String, Object> map = null;
        try {
            // 定义反序列化 数据格式
            final TypeReference<ResultInfo<List<InvoiceApply>>> TypeRef =
                    new TypeReference<ResultInfo<List<InvoiceApply>>>() {};
            String url = httpUrl + "finance/invoice/getinvoiceapplyverifylistpage.htm";
            ResultInfo<?> result =
                    (ResultInfo<?>) HttpClientUtils.post(url, invoiceApply, clientId, clientKey, TypeRef, page);
            if (result != null && result.getCode() == 0) {
                List<InvoiceApply> openInvoiceApplyList = (List<InvoiceApply>) result.getData();
                if (openInvoiceApplyList != null && openInvoiceApplyList.size() > 0) {
                    map = new HashMap<String, Object>();
                    map.put("page", result.getPage());

                    List<Integer> userIdList = new ArrayList<>();
                    List<Integer> traderIdList = new ArrayList<>();
                    if (openInvoiceApplyList != null && !openInvoiceApplyList.isEmpty()) {
                        for (int i = 0; i < openInvoiceApplyList.size(); i++) {
                            userIdList.add(openInvoiceApplyList.get(i).getCreator());
                            userIdList.add(openInvoiceApplyList.get(i).getValidUserId());
                            userIdList.add(openInvoiceApplyList.get(i).getUpdater());

                            traderIdList.add(openInvoiceApplyList.get(i).getTraderId());
                        }
                        userIdList = new ArrayList<Integer>(new HashSet<Integer>(userIdList));
                        List<User> userList = userMapper.getUserByUserIds(userIdList);
                        map.put("userList", userList);// 申请人，审核人
                        List<User> traderUserList = userMapper.getTraderUserAndOrgList(traderIdList);// 1客户，2供应商
                        map.put("traderUserList", traderUserList);// 归属销售

                        map.put("openInvoiceApplyList", openInvoiceApplyList);// 归属销售
                    }
                    return map;
                }
                else {
                    map = new HashMap<String, Object>();
                    map.put("page", result.getPage());
                    map.put("openInvoiceApplyList", new ArrayList<>());// 归属销售
                }

            }
        }
        catch (Exception e) {
            LOG.error(Contant.ERROR_MSG, e);
            return null;
        }
        return map;
    }

    @Override
    public ResultInfo<?> saveOpenInvoiceAudit(InvoiceApply invoiceApply) {
        try {
            // 定义反序列化 数据格式
            final TypeReference<ResultInfo<Saleorder>> TypeRef = new TypeReference<ResultInfo<Saleorder>>() {};
            String url = httpUrl + "finance/invoice/saveopeninvoiceaudit.htm";
            ResultInfo<?> result =
                    (ResultInfo<?>) HttpClientUtils.post(url, invoiceApply, clientId, clientKey, TypeRef);
            if (result != null) {
                // 发送消息--开票申请审核不通过
                if (result.getCode() == 0 && result.getData() != null) {
                    if ((invoiceApply.getValidStatus() != null && invoiceApply.getValidStatus().equals(2))
                            || (invoiceApply.getYyValidStatus() != null && invoiceApply.getYyValidStatus().equals(2))) {
                        Saleorder saleorder = (Saleorder) result.getData();
                        // 根据客户Id查询客户负责人
                        List<Integer> userIdList =
                                userMapper.getUserIdListByTraderId(saleorder.getTraderId(), ErpConst.ONE);
                        Map<String, String> map = new HashMap<>();
                        map.put("saleorderNo", saleorder.getSaleorderNo());
                        MessageUtil.sendMessage(21, userIdList, map,
                                "./order/saleorder/view.do?saleorderId=" + saleorder.getSaleorderId());//
                    }
                }
                return result;
            }
        }
        catch (Exception e) {
            LOG.error(Contant.ERROR_MSG, e);
            return new ResultInfo<>(-1, "操作异常");
        }
        return new ResultInfo<>();
    }

    @Override
    public ResultInfo<?> saveOpenInvoiceAuditBatch(InvoiceApply invoiceApply) {
        try {
            // 定义反序列化 数据格式
            final TypeReference<ResultInfo<String>> TypeRef = new TypeReference<ResultInfo<String>>() {};
            String url = httpUrl + "finance/invoice/saveopeninvoiceauditbatch.htm";
            ResultInfo<?> result =
                    (ResultInfo<?>) HttpClientUtils.post(url, invoiceApply, clientId, clientKey, TypeRef);
            if (result != null && result.getCode() == 0) {
                return result;
            }
        }
        catch (Exception e) {
            LOG.error(Contant.ERROR_MSG, e);
            return new ResultInfo<>(-1, "操作异常");
        }
        return new ResultInfo<>();
    }

    @Override
    public Map<String, Object> getAdvanceInvoiceApplyListPage(InvoiceApply invoiceApply, Page page) {
        Map<String, Object> map = null;
        try {
            // 定义反序列化 数据格式
            final TypeReference<ResultInfo<Map<String, Object>>> TypeRef =
                    new TypeReference<ResultInfo<Map<String, Object>>>() {};
            String url = httpUrl + "finance/invoice/getadvanceinvoiceapplylistpage.htm";
            ResultInfo<?> result =
                    (ResultInfo<?>) HttpClientUtils.post(url, invoiceApply, clientId, clientKey, TypeRef, page);
            if (result != null && result.getCode() == 0) {
                Map<String, Object> result_map = (Map<String, Object>) result.getData();
                if (result_map != null && result_map.size() > 0) {

                    map = new HashMap<String, Object>();
                    net.sf.json.JSONArray json = null;
                    String openInvoiceApplyStr = result_map.get("openInvoiceApplyList").toString();
                    json = net.sf.json.JSONArray.fromObject(openInvoiceApplyStr);

                    List<InvoiceApply> openInvoiceApplyList =
                            (List<InvoiceApply>) json.toCollection(json, InvoiceApply.class);

                    invoiceApply = (InvoiceApply) JSONObject
                            .toBean(JSONObject.fromObject(result_map.get("invoiceApply")), InvoiceApply.class);
                    map.put("invoiceApply", invoiceApply);

                    map.put("page", result.getPage());

                    // map.put("totalMoney", result_map.get("totalMoney"));

                    List<Integer> userIdList = new ArrayList<>();
                    List<Integer> orgIdList = new ArrayList<>();
                    List<Integer> traderIdList = new ArrayList<>();
                    if (openInvoiceApplyList != null && !openInvoiceApplyList.isEmpty()) {
                        for (int i = 0; i < openInvoiceApplyList.size(); i++) {
                            userIdList.add(openInvoiceApplyList.get(i).getCreator());
                            // userIdList.add(openInvoiceApplyList.get(i).getUpdater());

                            orgIdList.add(openInvoiceApplyList.get(i).getOrgId());

                            traderIdList.add(openInvoiceApplyList.get(i).getTraderId());

                        }
                        map.put("openInvoiceApplyList", openInvoiceApplyList);
                        userIdList = new ArrayList<Integer>(new HashSet<Integer>(userIdList));
                        List<User> userList = userMapper.getUserByUserIds(userIdList);
                        map.put("userList", userList);// 申请人，审核人

                        // List<Organization> orgList = organizationMapper.getOrgByList(orgIdList);
                        // map.put("orgList", orgList);//销售部门

                        List<User> traderUserList = userMapper.getTraderUserAndOrgList(traderIdList);
                        map.put("traderUserList", traderUserList);// 归属销售
                    }
                    return map;
                }

            }
        }
        catch (Exception e) {
            LOG.error(Contant.ERROR_MSG, e);
            return null;
        }
        return map;
    }

    @Override
    public Map<String, Object> getSaleInvoiceList(Invoice invoice) {
        Map<String, Object> map = null;
        try {
            // 定义反序列化 数据格式
            final TypeReference<ResultInfo<Map<String, Object>>> TypeRef =
                    new TypeReference<ResultInfo<Map<String, Object>>>() {};
            String url = httpUrl + "finance/invoice/getsaleinvoicelist.htm";
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, invoice, clientId, clientKey, TypeRef);
            if (result != null && result.getCode() == 0) {
                Map<String, Object> result_map = (Map<String, Object>) result.getData();
                map = new HashMap<String, Object>();

                net.sf.json.JSONArray json = null;
                String saleorderGoodsStr = result_map.get("saleorderGoodsList").toString();
                json = net.sf.json.JSONArray.fromObject(saleorderGoodsStr);

                List<InvoiceDetail> saleorderGoodsList =
                        (List<InvoiceDetail>) json.toCollection(json, InvoiceDetail.class);
                map.put("saleorderGoodsList", saleorderGoodsList);

                String invoiceDetailStr = result_map.get("invoiceDetailList").toString();
                json = net.sf.json.JSONArray.fromObject(invoiceDetailStr);
                List<InvoiceDetail> invoiceDetailList =
                        (List<InvoiceDetail>) json.toCollection(json, InvoiceDetail.class);
                map.put("invoiceDetailList", invoiceDetailList);

                return map;
            }
        }
        catch (Exception e) {
            LOG.error(Contant.ERROR_MSG, e);
            return null;
        }
        return map;
    }

    @Override
    public Map<String, Object> getSaleInvoiceListPage(Invoice invoice, Page page) {
        Map<String, Object> map = null;
        try {
            // 定义反序列化 数据格式
            final TypeReference<ResultInfo<Map<String, Object>>> TypeRef =
                    new TypeReference<ResultInfo<Map<String, Object>>>() {};
            String url = httpUrl + "finance/invoice/getsaleinvoicelistpage.htm";
            ResultInfo<?> result =
                    (ResultInfo<?>) HttpClientUtils.post(url, invoice, clientId, clientKey, TypeRef, page);
            if (result != null && result.getCode() == 0) {
                Map<String, Object> result_map = (Map<String, Object>) result.getData();
                map = new HashMap<String, Object>();
                net.sf.json.JSONArray json = null;
                String saleInvoiceStr = result_map.get("saleInvoiceList").toString();
                json = net.sf.json.JSONArray.fromObject(saleInvoiceStr);
                List<Invoice> saleInvoiceList = (List<Invoice>) json.toCollection(json, Invoice.class);
                map.put("saleInvoiceList", saleInvoiceList);
                List<Integer> userIdList = new ArrayList<>();
                List<Integer> traderIdList = new ArrayList<>();
                for (int i = 0; i < saleInvoiceList.size(); i++) {
                    userIdList.add(saleInvoiceList.get(i).getCreator());
                    userIdList.add(saleInvoiceList.get(i).getSendUserId());
                    traderIdList.add(saleInvoiceList.get(i).getTraderId());
                    if (saleInvoiceList.get(i).getExpressId() != null
                            && !saleInvoiceList.get(i).getExpressId().equals(0)) {
                        Express express = new Express();
                        express.setExpressId(saleInvoiceList.get(i).getExpressId());
                        express =  getExpressInfoById(express);
                        saleInvoiceList.get(i).setExpress(express);
                    }
                }
                if (userIdList.size() > 0) {
                    userIdList = new ArrayList<Integer>(new HashSet<Integer>(userIdList));
                    List<User> userList = userMapper.getUserByUserIds(userIdList);
                    map.put("userList", userList);
                }
                if (traderIdList.size() > 0) {
                    traderIdList = new ArrayList<Integer>(new HashSet<Integer>(traderIdList));
                    List<User> traderList = userMapper.getUserByTraderIdList(traderIdList, 1);
                    map.put("traderList", traderList);
                }

                map.put("page", (Page) result.getPage());

                invoice = (Invoice) JSONObject.toBean(JSONObject.fromObject(result_map.get("invoice")), Invoice.class);
                map.put("invoice", invoice);

                if (result_map.get("saleAfterList") != null) {
                    String saleAfterListStr = result_map.get("saleAfterList").toString();
                    json = net.sf.json.JSONArray.fromObject(saleAfterListStr);
                    List<AfterSales> saleAfterList = (List<AfterSales>) json.toCollection(json, AfterSales.class);
                    map.put("saleAfterList", saleAfterList);
                }

                return map;
            }
        }
        catch (Exception e) {
            LOG.error(Contant.ERROR_MSG, e);
            return null;
        }
        return map;
    }

    @Override
    public List<Invoice> getInvoiceInfoByRelatedId(Integer relatedId, Integer id) {
        try {
            Invoice invoice = new Invoice();
            invoice.setRelatedId(relatedId);
            invoice.setType(id);
            // 定义反序列化 数据格式
            final TypeReference<ResultInfo<List<Invoice>>> TypeRef = new TypeReference<ResultInfo<List<Invoice>>>() {};
            String url = httpUrl + "finance/invoice/getinvoiceinfobyrelatedid.htm";
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, invoice, clientId, clientKey, TypeRef);
            if (result != null && result.getCode() == 0) {
                List<Invoice> list = (List<Invoice>) result.getData();
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setCreatorName(userMapper.getUserNameByUserId(list.get(i).getCreator()));
                    list.get(i).setValidUserName(userMapper.getUserNameByUserId(list.get(i).getValidUserId()));
                }
                return list;
            }
        }
        catch (IOException e) {
            LOG.error(Contant.ERROR_MSG, e);
            return null;
        }
        return null;
    }

    @Override
    public List<InvoiceApply> getSaleInvoiceApplyList(Integer saleorderId, Integer id505, Integer isAdvance) {
        try {
            InvoiceApply invoiceApply = new InvoiceApply();
            invoiceApply.setRelatedId(saleorderId);
            invoiceApply.setType(id505);
            if (isAdvance != -1) {
                invoiceApply.setIsAdvance(isAdvance);
            }
            // 定义反序列化 数据格式
            final TypeReference<ResultInfo<List<InvoiceApply>>> TypeRef =
                    new TypeReference<ResultInfo<List<InvoiceApply>>>() {};
            String url = httpUrl + "finance/invoice/getsaleinvoiceapplylist.htm";
            ResultInfo<?> result =
                    (ResultInfo<?>) HttpClientUtils.post(url, invoiceApply, clientId, clientKey, TypeRef);
            if (result != null && result.getCode() == 0) {
                List<InvoiceApply> list = (List<InvoiceApply>) result.getData();
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setUserName(userMapper.getUserNameByUserId(list.get(i).getUpdater()));
                    list.get(i).setCreatorNm(userMapper.getUserNameByUserId(list.get(i).getCreator()));
                }
                return list;
            }
        }
        catch (IOException e) {
            LOG.error(Contant.ERROR_MSG, e);
            return null;
        }
        return null;
    }

    @Override
    public InvoiceApply getInvoiceApplyByRelatedId(Integer saleorderId, Integer type, Integer companyId) {
        try {
            InvoiceApply invoiceApply = new InvoiceApply();
            invoiceApply.setRelatedId(saleorderId);
            invoiceApply.setType(type);
            invoiceApply.setCompanyId(companyId);
            // 定义反序列化 数据格式
            final TypeReference<ResultInfo<InvoiceApply>> TypeRef = new TypeReference<ResultInfo<InvoiceApply>>() {};
            String url = httpUrl + "finance/invoice/getinvoiceapplybyrelatedid.htm";
            ResultInfo<?> result =
                    (ResultInfo<?>) HttpClientUtils.post(url, invoiceApply, clientId, clientKey, TypeRef);
            if (result != null && result.getCode() == 0) {
                return (InvoiceApply) result.getData();
            }
        }
        catch (IOException e) {
            LOG.error(Contant.ERROR_MSG, e);
            return null;
        }
        return null;
    }

    @Override
    public ResultInfo<?> saveExpressInvoice(Express express) {
        try {
            // 定义反序列化 数据格式
            final TypeReference<ResultInfo<Express>> TypeRef = new TypeReference<ResultInfo<Express>>() {};
            String url = httpUrl + "finance/invoice/saveexpressinvoice.htm";
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, express, clientId, clientKey, TypeRef);
            if (result != null) {
                return result;
            }
        }
        catch (IOException e) {
            LOG.error(Contant.ERROR_MSG, e);
            return new ResultInfo<>(-1, "操作异常");
        }
        return new ResultInfo<>();
    }

    @Override
    public Map<String, Object> getBuyInvoiceAuditList(Invoice invoice, Page page) {
        Map<String, Object> map = null;
        try {
            // 定义反序列化 数据格式
            final TypeReference<ResultInfo<Map<String, Object>>> TypeRef =
                    new TypeReference<ResultInfo<Map<String, Object>>>() {};
            String url = httpUrl + "finance/invoice/getbuyinvoiceauditlist.htm";
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, invoice, clientId, clientKey, TypeRef, page);
            if (result != null && result.getCode() == 0) {
                map = new HashMap<>();
                Map<String, Object> result_map = (Map<String, Object>) result.getData();

                net.sf.json.JSONArray json = null;
                String invoiceStr = result_map.get("invoiceList").toString();
                json = net.sf.json.JSONArray.fromObject(invoiceStr);
                List<Invoice> invoiceList = (List<Invoice>) json.toCollection(json, Invoice.class);
                map.put("invoiceList", invoiceList);

                /*List<Integer> userIdList = new ArrayList<>();
                for (int i = 0; i < invoiceList.size(); i++) {
                    userIdList.add(invoiceList.get(i).getCreator());
                }
                if (userIdList != null && userIdList.size() > 0) {
                    userIdList = new ArrayList<Integer>(new HashSet<Integer>(userIdList));
                    List<User> userList = userMapper.getUserByUserIds(userIdList);
                    map.put("userList", userList);
                }*/

                invoice = (Invoice) JSONObject.toBean(JSONObject.fromObject(result_map.get("invoice")), Invoice.class);
                map.put("invoice", invoice);
                map.put("page", result.getPage());
            }
        } catch (IOException e) {
            LOG.error(Contant.ERROR_MSG, e);
            return null;
        }
        return map;
    }

    @Override
    public Invoice getInvoiceByNo(Invoice ic) {
        try {
            // 定义反序列化 数据格式
            final TypeReference<ResultInfo<Invoice>> TypeRef = new TypeReference<ResultInfo<Invoice>>() {};
            String url = httpUrl + "finance/invoice/getinvoicebyno.htm";
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, ic, clientId, clientKey, TypeRef);
            if (result != null && result.getCode() == 0) {
                return (Invoice) result.getData();
            }
        }
        catch (IOException e) {
            logger.error("getInvoiceByNo 错误",e);
            return null;
        }
        return null;
    }

    @Override
    public Map<String, Object> getAfterOpenInvoiceListPage(InvoiceApply invoiceApply, Page page) {
        Map<String, Object> map = new HashMap<>();
        try {
            // 定义反序列化 数据格式
            final TypeReference<ResultInfo<List<InvoiceApply>>> TypeRef =
                    new TypeReference<ResultInfo<List<InvoiceApply>>>() {};
            String url = httpUrl + "finance/invoice/getafteropeninvoicelistpage.htm";
            ResultInfo<?> result =
                    (ResultInfo<?>) HttpClientUtils.post(url, invoiceApply, clientId, clientKey, TypeRef, page);
            if (result != null && result.getCode() == 0) {
                List<InvoiceApply> openInvoiceApplyList = (List<InvoiceApply>) result.getData();
                List<Integer> userIdList = new ArrayList<>();
                for (int i = 0; i < openInvoiceApplyList.size(); i++) {
                    userIdList.add(openInvoiceApplyList.get(i).getCreator());
                }
                if (userIdList != null && userIdList.size() > 0) {
                    userIdList = new ArrayList<Integer>(new HashSet<Integer>(userIdList));
                    List<User> userList = userMapper.getUserByUserIds(userIdList);
                    map.put("userList", userList);
                }
                map.put("openInvoiceApplyList", openInvoiceApplyList);
                map.put("page", result.getPage());
            }
        }
        catch (Exception e) {
            LOG.error(Contant.ERROR_MSG, e);
            return null;
        }
        return map;
    }

    @Override
    public Map<String, Object> getAfterInvoiceApplyVerifyListPage(InvoiceApply invoiceApply, Page page) {
        Map<String, Object> map = new HashMap<>();
        try {
            // 定义反序列化 数据格式
            final TypeReference<ResultInfo<List<InvoiceApply>>> TypeRef =
                    new TypeReference<ResultInfo<List<InvoiceApply>>>() {};
            String url = httpUrl + "finance/invoice/getafterinvoiceapplyverifylistpage.htm";
            ResultInfo<?> result =
                    (ResultInfo<?>) HttpClientUtils.post(url, invoiceApply, clientId, clientKey, TypeRef, page);
            if (result != null && result.getCode() == 0) {
                List<InvoiceApply> openInvoiceApplyList = (List<InvoiceApply>) result.getData();
                List<Integer> userIdList = new ArrayList<>();
                for (int i = 0; i < openInvoiceApplyList.size(); i++) {
                    userIdList.add(openInvoiceApplyList.get(i).getCreator());
                }
                if (userIdList != null && userIdList.size() > 0) {
                    userIdList = new ArrayList<Integer>(new HashSet<Integer>(userIdList));
                    List<User> userList = userMapper.getUserByUserIds(userIdList);
                    map.put("userList", userList);
                }
                map.put("openInvoiceApplyList", openInvoiceApplyList);
                map.put("page", result.getPage());
            }
        }
        catch (Exception e) {
            LOG.error(Contant.ERROR_MSG, e);
            return null;
        }
        return map;
    }

    @Override
    public Map<String, Object> getBuyInvoiceConfirmListPage(Invoice invoice, Page page) {
        Map<String, Object> map = new HashMap<>();
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<List<Invoice>>> typeRef = new TypeReference<ResultInfo<List<Invoice>>>() {};
        String url = httpUrl + "finance/invoice/getbuyinvoiceconfirmlistpage.htm";
        try {
            ResultInfo<?> result =
                    (ResultInfo<?>) HttpClientUtils.post(url, invoice, clientId, clientKey, typeRef, page);
            if (result != null && result.getCode() == 0) {
                map.put("invoiceList", (List<Invoice>) result.getData());
                map.put("page", result.getPage());
            }
        }
        catch (Exception e) {
            LOG.error(Contant.ERROR_MSG, e);
            return null;
        }
        return map;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ResultInfo sendOpenInvoicelist(Invoice invoice, Page page, HttpSession session) throws Exception {
        ResultInfo result = new ResultInfo();
        List<Invoice> mapInvoiceList = null;
        // 获取发送至金蝶银行流水
        ResultInfo<List<Invoice>> resultMap = (ResultInfo<List<Invoice>>) this.sendOpenInvoicelistPage(invoice, page);

        mapInvoiceList = (List<Invoice>) resultMap.getData();
        if (null != mapInvoiceList && mapInvoiceList.size() > 0) {
            // 根据客户id获取归属销售
            getUserByTraderIds(invoice, mapInvoiceList);
        }

        Integer pageCount = resultMap.getPage().getTotalPage();
        for (int i = 2; i <= pageCount; i++) {
            page.setPageNo(i);
            ResultInfo<List<Invoice>> listMap = (ResultInfo<List<Invoice>>) this.sendOpenInvoicelistPage(invoice, page);
            List<Invoice> list = (List<Invoice>) listMap.getData();
            if (null != list && list.size() > 0) {
                getUserByTraderIds(invoice, list);
                // 根据客户id获取归属销售
                mapInvoiceList.addAll(list);
            }
        }

        // 保存发送至金蝶数据至本地库
        if (null != mapInvoiceList && mapInvoiceList.size() > 0) {

            XmlExercise xmlExercise = new XmlExercise();
            Map data = new LinkedHashMap<>();
            data.put("companyid", 1);

            List<Map<String, Object>> dataList = new ArrayList<>();
            for (Invoice invoice2 : mapInvoiceList) {
                // 不允许traderId为null
                if (null == invoice2.getTraderId() || invoice2.getTraderId() == 0) {
                    result.setMessage("推送失败，不允许存在traderId为空的值！");
                    result.setCode(-1);
                    return result;
                }
                invoice2.setUserId(invoice.getUserId());
                Map data1 = new LinkedHashMap<>();
                Map data2 = new HashMap<>();
                data1.put("id", invoice2.getInvoiceId());
                data1.put("date", DateUtil.convertString(invoice2.getAddTime(), "yyyy-MM-dd"));
                invoice2.setAddTimeStr(DateUtil.convertString(invoice2.getAddTime(), "yyyy-MM-dd"));
                data1.put("orderNo", invoice2.getSaleorderNo());
                data1.put("invoiceNo", invoice2.getInvoiceNo());
                data1.put("saler", invoice2.getUserName());
                data1.put("invoiceAmount", invoice2.getAmount());
                data1.put("taxAmount", invoice2.getRatioAmount());
                data1.put("amount", invoice2.getNoRatioAmount());
                data1.put("traderName", invoice2.getTraderName());
                data1.put("traderId", invoice2.getTraderId());
                data1.put("remark", invoice2.getComments());
                data2.put("info", data1);

                dataList.add(data2);
            }
            data.put("list", dataList);
            String dataXmlStr = xmlExercise.mapToListXml(data, "data");
            String params = CryptBase64Tool.desEncrypt(dataXmlStr, kingdleeKey);
            try {
                logger.info(DateUtil.getNowDate("yyyy-MM-dd HH:mm:ss") + "推送金蝶数据:" + URLEncoder.encode(params, "utf-8"));

                String sendPost = HttpRequest.sendPost(kingdleeOpeninvoiceUrl, "msg=" + URLEncoder.encode(params, "utf-8"));
                Map info = xmlExercise.xmlToMap(sendPost);
                logger.info(DateUtil.getNowDate("yyyy-MM-dd HH:mm:ss") + "推送金蝶数据返回结果:" + JSON.toJSONString(info));
                if (null != info && info.get("code").equals("0")) {
                    // 分批保存，防止数据过多，保存失败2019-08-02
                    if(mapInvoiceList.size() > 500){
                        int size = mapInvoiceList.size();
                        int toIndex = 500;
                        for(int i = 0;i < size;i += 500){
                            if(i + 500 > size){//作用为toIndex最后没有500条数据则剩余几条newList中就装几条
                                toIndex = size - i;
                            }
                            List<Invoice> newList = mapInvoiceList.subList(i, i + toIndex);
                            result = this.saveInvoiceList(newList);
                        }
                    } else {
                        result = this.saveInvoiceList(mapInvoiceList);
                    }
                    page.setTotalRecord(mapInvoiceList.size());
                    result.setPage(page);

                    /*page.setTotalRecord(mapInvoiceList.size());
                    result = this.saveInvoiceList(mapInvoiceList);
                    result.setPage(page);*/
                }
            } catch (Exception e) {
                logger.error("推送失败，对方接口异常！",e);
                result.setCode(-1);
                result.setMessage("推送失败，对方接口异常！");
                return result;
            }
        }
        return result;
    }

    // 根据客户id获取归属销售
    private void getUserByTraderIds(Invoice invoice, List<Invoice> mapInvoiceList) {
        List<Integer> traderIds = new ArrayList<>();
        for (Invoice invoice2 : mapInvoiceList) {
            invoice2.setCompanyId(invoice.getCompanyId());

            // 设置状态
            String status = "";
            switch (invoice2.getColorType()) {
                case 1 :
                    status = "红字";
                    break;
                case 2 :
                    status = "蓝字";
                    break;
            }
            switch (invoice2.getIsEnable()) {
                case 1 :
                    status += "有效";
                    break;
                case 0 :
                    status += "作废";
                    break;
            }
            // 备注
            invoice2.setComments(invoice2.getSaleorderNo() + "+" + invoice2.getInvoiceNo() + "#+" + status);

            // 根据traderId查询销售人员
            if (invoice2.getTraderId() != null && invoice2.getTraderId() > 0) {
                traderIds.add(invoice2.getTraderId());
            }
        }
        if (traderIds.size() > 0) {
            List<User> traderUserAndOrgList = userMapper.getTraderUserAndOrgList(traderIds);
            if (null != traderUserAndOrgList && traderUserAndOrgList.size() > 0) {
                for (Invoice invoice2 : mapInvoiceList) {
                    for (User user : traderUserAndOrgList) {
                        if (invoice2.getTraderId() != null) {
                            if (invoice2.getTraderId().equals(user.getTraderId())) {
                                invoice2.setUserName(user.getUsername());
                                // 设置状态
                                String status = "";
                                switch (invoice2.getColorType()) {
                                    case 1 :
                                        status = "红字";
                                        break;
                                    case 2 :
                                        status = "蓝字";
                                        break;
                                }
                                switch (invoice2.getIsEnable()) {
                                    case 1 :
                                        status += "有效";
                                        break;
                                    case 0 :
                                        status += "作废";
                                        break;
                                }
                                // 备注
                                invoice2.setComments(invoice2.getSaleorderNo() + "+" + invoice2.getInvoiceNo() + "#+"
                                        + status + "+" + invoice2.getUserName());
                            }
                        }
                    }
                }
            }
        }
    }

    // 保存发送至金蝶数据至本地库
    private ResultInfo saveInvoiceList(List<Invoice> mapInvoiceList) {
        ResultInfo<?> result = null;
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<Map<String, Object>>> TypeRef =
                new TypeReference<ResultInfo<Map<String, Object>>>() {};
        String url = httpUrl + "finance/invoice/saveinvoicelist.htm";
        try {
            result = (ResultInfo<?>) HttpClientUtils.post(url, mapInvoiceList, clientId, clientKey, TypeRef);
            if (result != null) {
                if (result.getCode() == 0) {
                    return result;
                }
            }
        }
        catch (IOException e) {
            LOG.error(Contant.ERROR_MSG, e);
        }
        return result;
    }

    private ResultInfo<?> sendOpenInvoicelistPage(Invoice invoice, Page page) {
        String url = httpUrl + "finance/invoice/getsendopeninvoicelistpage.htm";
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<List<Invoice>>> TypeRef = new TypeReference<ResultInfo<List<Invoice>>>() {};
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, invoice, clientId, clientKey, TypeRef, page);

            return result;
        }
        catch (IOException e) {
            LOG.error(Contant.ERROR_MSG, e);
        }
        return new ResultInfo<>();
    }

    @SuppressWarnings("unchecked")
    @Override
    public ResultInfo sendIncomeInvoiceList(Invoice invoice, Page page, HttpSession session) throws Exception {
        ResultInfo result = new ResultInfo();
        List<Invoice> mapInvoiceList = null;

        // 获取发送至金蝶银行流水
        ResultInfo<List<Invoice>> resultMap = (ResultInfo<List<Invoice>>) this.sendIncomeInvoicelistPage(invoice, page);
        mapInvoiceList = (List<Invoice>) resultMap.getData();
        if (null != mapInvoiceList && mapInvoiceList.size() > 0) {
            for (Invoice invoice2 : mapInvoiceList) {
                String[] noString = invoice2.getBuyorderNo().split(",");
                if (noString.length > 1) {
                    String buyorderNo = "";
                    buyorderNo += noString[0] + "/";
                    buyorderNo += noString[1];
                    invoice2.setBuyorderNo(buyorderNo);

                    invoice2.setComments(buyorderNo + "+" + invoice2.getInvoiceNo() + "#");
                }
                else {
                    // 备注
                    invoice2.setComments(invoice2.getBuyorderNo() + "+" + invoice2.getInvoiceNo() + "#");
                }
            }
        }
        Integer pageCount = resultMap.getPage().getTotalPage();
        for (int i = 2; i <= pageCount; i++) {
            page.setPageNo(i);
            ResultInfo<List<Invoice>> listMap =
                    (ResultInfo<List<Invoice>>) this.sendIncomeInvoicelistPage(invoice, page);
            List<Invoice> list = (List<Invoice>) listMap.getData();
            if (null != list && list.size() > 0) {
                for (Invoice invoice2 : list) {
                    String[] noString = invoice2.getBuyorderNo().split(",");
                    if (noString.length > 1) {
                        String buyorderNo = "";
                        buyorderNo += noString[0] + "/";
                        buyorderNo += noString[1];
                        invoice2.setBuyorderNo(buyorderNo);

                        invoice2.setComments(buyorderNo + "+" + invoice2.getInvoiceNo() + "#");
                    }
                    else {
                        // 备注
                        invoice2.setComments(invoice2.getBuyorderNo() + "+" + invoice2.getInvoiceNo() + "#");
                    }
                }
                mapInvoiceList.addAll(list);
            }
        }

        // 保存发送至金蝶数据至本地库
        if (null != mapInvoiceList && mapInvoiceList.size() > 0) {

            XmlExercise xmlExercise = new XmlExercise();
            Map data = new LinkedHashMap<>();
            data.put("companyid", 1);

            List<Map<String, Object>> dataList = new ArrayList<>();
            for (Invoice invoice2 : mapInvoiceList) {
                // 不允许traderId为null
                if (null == invoice2.getTraderId() || invoice2.getTraderId() == 0) {
                    result.setMessage("推送失败，不允许存在traderId为空的值！");
                    result.setCode(-1);
                    return result;
                }
                invoice2.setUserId(invoice.getUserId());
                Map data1 = new LinkedHashMap<>();
                Map data2 = new HashMap<>();
                data1.put("id", invoice2.getInvoiceId());
                data1.put("date", DateUtil.convertString(invoice2.getValidTime(), "yyyy-MM-dd"));
                data1.put("type", invoice2.getType());
                invoice2.setAddTimeStr(DateUtil.convertString(invoice2.getValidTime(), "yyyy-MM-dd"));
                data1.put("orderNo", invoice2.getBuyorderNo());
                data1.put("invoiceNo", invoice2.getInvoiceNo());
                data1.put("invoiceAmount", invoice2.getAmount());
                data1.put("taxAmount", invoice2.getRatioAmount());
                data1.put("amount", invoice2.getNoRatioAmount());
                data1.put("traderName", invoice2.getTraderName().replaceAll(" ", ""));
                data1.put("traderId", invoice2.getTraderId());
                data1.put("remark", invoice2.getComments());
                data1.put("ismark", invoice2.getIsAuth());

                data2.put("info", data1);

                dataList.add(data2);
            }

            data.put("list", dataList);
            String dataXmlStr = xmlExercise.mapToListXml(data, "data");
            String params = CryptBase64Tool.desEncrypt(dataXmlStr, kingdleeKey);

            try {
                String sendPost =
                        HttpRequest.sendPost(kingdleeReceinvoiceUrl, "msg=" + URLEncoder.encode(params, "utf-8"));
                Map info = xmlExercise.xmlToMap(sendPost);
                if (null != info && info.get("code").equals("0")) {
                    page.setTotalRecord(mapInvoiceList.size());
                    result = this.saveIncomeInvoicelist(mapInvoiceList);
                    result.setPage(page);
                }
            }
            catch (Exception e) {
                result.setCode(-1);
                result.setMessage("推送失败，对方接口异常！");
                return result;
            }
        }
        return result;
    }

    private ResultInfo saveIncomeInvoicelist(List<Invoice> mapInvoiceList) {
        ResultInfo<?> result = null;
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<Map<String, Object>>> TypeRef =
                new TypeReference<ResultInfo<Map<String, Object>>>() {};
        String url = httpUrl + "finance/invoice/savereceiveinvoicelist.htm";
        try {
            result = (ResultInfo<?>) HttpClientUtils.post(url, mapInvoiceList, clientId, clientKey, TypeRef);
            if (result != null) {
                if (result.getCode() == 0 && result.getData() != null) {
                    return result;
                }
            }
        }
        catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
        }
        return result;
    }

    private ResultInfo<?> sendIncomeInvoicelistPage(Invoice invoice, Page page) {
        String url = httpUrl + "finance/invoice/getsendreceiveinvoicelistpage.htm";
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<List<Invoice>>> TypeRef = new TypeReference<ResultInfo<List<Invoice>>>() {};
        try {
            ResultInfo<?> result =
                    (ResultInfo<?>) HttpClientUtils.post(url, invoice, clientId, clientKey, TypeRef, page);

            return result;
        }
        catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
        }
        return new ResultInfo<>();
    }

    /**
     * <b>Description:</b><br>
     * 保存批量认证
     * 
     * @param invoices
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2018年5月28日 下午4:56:51
     */
    @Override
    public ResultInfo<?> saveBatchAuthentication(List<Invoice> invoices) {
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<List<Invoice>>> typeRef = new TypeReference<ResultInfo<List<Invoice>>>() {};
        String url = httpUrl + "finance/invoice/savebatchauthentication.htm";
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, invoices, clientId, clientKey, typeRef);
            return result;
        }
        catch (Exception e) {
            logger.error(Contant.ERROR_MSG, e);
            return null;
        }
    }

    @Override
    public ResultInfo<?> openEInvoicePush(InvoiceApply invoiceApply) {
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<Saleorder>> typeRef = new TypeReference<ResultInfo<Saleorder>>() {};
        String url = httpUrl + "finance/sheinvoice/openeinvoicepush.htm";
        try {
            ResultInfo<?> result =
                    (ResultInfo<?>) HttpClientUtils.post(url, invoiceApply, clientId, clientKey, typeRef);
            if (result != null) {
                if (result.getCode() == 0 && result.getData() != null) {// 销售
                    Saleorder saleorder = (Saleorder) result.getData();
                    // 根据客户Id查询客户负责人..
                    List<Integer> userIdList =
                            userMapper.getUserIdListByTraderId(saleorder.getTraderId(), ErpConst.ONE);
                    Map<String, String> map = new HashMap<>();
                    map.put("saleorderNo", saleorder.getSaleorderNo());
                    // 銷售開票后發送消息給銷售負責人
                    MessageUtil.sendMessage(11, userIdList, map,
                            "./order/saleorder/view.do?saleorderId=" + saleorder.getSaleorderId());
                    //更新订单updateDataTime
                    updateSaleOrderDataUpdateTime(saleorder.getSaleorderId(),null,OrderDataUpdateConstant.SALE_ORDER_INVOICE);
                }
            }
            return result;
        }
        catch (Exception e) {
            logger.error(Contant.ERROR_MSG, e);
            return new ResultInfo<>();
        }
    }

    @Override
    public ResultInfo<?> batchDownEInvoice(Invoice invoice) {
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<?>> typeRef = new TypeReference<ResultInfo<?>>() {};
        String url = httpUrl + "finance/sheinvoice/batchdowneinvoice.htm";
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, invoice, clientId, clientKey, typeRef);
            return result;
        }
        catch (Exception e) {
            logger.error(Contant.ERROR_MSG, e);
            return new ResultInfo<>();
        }
    }

    @Override
    public ResultInfo<?> cancelEInvoicePush(Invoice invoice) {
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<?>> typeRef = new TypeReference<ResultInfo<?>>() {};
        String url = httpUrl + "finance/sheinvoice/canceleinvoicepush.htm";
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, invoice, clientId, clientKey, typeRef);
            return result;
        }
        catch (Exception e) {
            logger.error(Contant.ERROR_MSG, e);
            return new ResultInfo<>();
        }
    }

    @Override
    public ResultInfo<?> updateExpressInvoice(Express express) {
        try {
            // 定义反序列化 数据格式
            final TypeReference<ResultInfo<Express>> TypeRef = new TypeReference<ResultInfo<Express>>() {};
            String url = httpUrl + "finance/invoice/updateexpressinvoice.htm";
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, express, clientId, clientKey, TypeRef);
            if (result != null) {
                return result;
            }
        }
        catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            return new ResultInfo<>(-1, "操作异常");
        }
        return new ResultInfo<>();
    }

    @Override
    public ResultInfo<?> sendInvoiceSmsAndMail(Invoice invoice) {
        try {
            // 定义反序列化 数据格式
            final TypeReference<ResultInfo<Express>> TypeRef = new TypeReference<ResultInfo<Express>>() {};
            String url = httpUrl + "finance/sheinvoice/sendinvoicesmsandmail.htm";
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, invoice, clientId, clientKey, TypeRef);
            if (result != null) {
                return result;
            }
        }
        catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            return new ResultInfo<>(-1, "操作异常");
        }
        return new ResultInfo<>();
    }

    @Override
    public List<TraderCustomerVo> getCollectInvoiceTraderName(String traderName) {
        try {
            // 定义反序列化 数据格式
            final TypeReference<ResultInfo<List<TraderCustomerVo>>> TypeRef = new TypeReference<ResultInfo<List<TraderCustomerVo>>>() {};
            String url = httpUrl + "finance/invoice/getcollectinvoicetradername.htm";
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, traderName, clientId, clientKey, TypeRef);
            if (result != null) {
                List<TraderCustomerVo> traderCustomerList = (List<TraderCustomerVo>) result.getData();
                return traderCustomerList;
            }
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
        }
        return null;
    }

    @Override
    public ResultInfo<?> updateCollectInvoiceTrader(List<Trader> list) {
        try {
            // 定义反序列化 数据格式
            final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
            String url = httpUrl + "finance/invoice/updatecollectinvoicetrader.htm";
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, list, clientId, clientKey, TypeRef);
            if (result != null) {
                return result;
            }
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            return new ResultInfo<>(-1, "操作异常");
        }
        return new ResultInfo<>();
    }

    @Override
    public ResultInfo<?> delCollectInvoiceTrader(TraderCustomerVo traderCustomerVo) {
        try {
            // 定义反序列化 数据格式
            final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
            String url = httpUrl + "finance/invoice/delcollectinvoicetrader.htm";
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, traderCustomerVo, clientId, clientKey, TypeRef);
            if (result != null) {
                return result;
            }
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            return new ResultInfo<>(-1, "操作异常");
        }
        return new ResultInfo<>();
    }

    @Override
    public ResultInfo<?> updateInvoiceApplySign(InvoiceApply invoiceApply) {
        try {
            // 定义反序列化 数据格式
            final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
            String url = httpUrl + "finance/invoice/updateinvoiceapplysign.htm";
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, invoiceApply, clientId, clientKey, TypeRef);
            if (result != null) {
                return result;
            }
        } catch (IOException e) {
            logger.info("操作失败", e);
            return new ResultInfo<>(-1, "操作异常");
        }
        return new ResultInfo<>();
    }

    @Override public ResultInfo<?> sendWxMessageForInvoice(List<Invoice> invoiceList) {
        ResultInfo resultInfo = new ResultInfo();
        try {
            if(null != invoiceList && invoiceList.size() > 0){
                LOG.info("发票寄送发送微信模版消息开始....start....invoiceIdList="+ JSONArray.fromObject(invoiceList).toString());
                for(Invoice invoice:invoiceList){
                    // 存在 traderContactId
                    if(null == invoice || null == invoice.getTraderContactId() || CommonConstants.ZERO.equals(invoice.getTraderContactId())) {
                        continue;
                    }
                    WebAccount webaccount = new WebAccount();
                    webaccount.setTraderContactId(invoice.getTraderContactId());
                    //获取销售单联系人关联的注册用户信息
                    List<WebAccount> webAccountList=webAccountMapper.getWebAccountListByParam(webaccount);
                        //    webAccountService.getWebAccountByTraderContactId(webaccount);
                    for(WebAccount wa:webAccountList){
                        Map data = new HashMap<>();//消息数据
                        //公司名称
                        data.put("companyName",invoice.getTraderName());
                        //开票金额
                        data.put("invoiceAmount",invoice.getAmount());
                        //商品总数量（除去售后数量）
                        data.put("logisticsNo",invoice.getLogisticsNo());
                        data.put("invoiceNo",invoice.getInvoiceNo());

                        //推送微信消息
                        ResultInfo res = vedengSoapService.sendWxMessage(data, CommonConstants.WX_TEMPLATE_NO_FP,wa.getUuid());
                        //如果推送成功则回写T_SALEORDER中的LOGISTICS_WXSEND_SYNC字段
                        if(res.getCode().equals(0)){
                            LOG.info("发票寄送发送微信模版消息发送成功.......InvoiceId="+invoice.getInvoiceId());
                        }else{
                            LOG.error("发票寄送发送微信模版消息发送失败.......InvoiceId="+invoice.getInvoiceId());
                        }
                    }
                }
                resultInfo.setData(invoiceList);
                LOG.info("发票寄送发送微信模版消息结束....end....");
            }

            return resultInfo;
        }catch (Exception e){
            LOG.error("发票寄送发送微信模版消息失败 ", e);
            return resultInfo;
        }
    }

    @Override
    public List<Invoice> getInvoiceListByInvoiceIdList(List<Integer> invoiceIdLis) {
        if(null == invoiceIdLis || invoiceIdLis.size() == 0) {
            return null;
        }
        return invoiceMapper.getInvoiceListByInvoiceIdList(invoiceIdLis);
    }

    /** @description: getInvoiceNums.
     * @notes: VDERP-1325 分批开票 获取已开票数量和未开票数量.
     * @author: Tomcat.Hui.
     * @date: 2019/11/8 15:15.
     * @param saleorderNo
     * @return: java.util.Map<java.lang.Integer , java.util.Map < java.lang.String , java.math.BigDecimal>>.
     * @throws: .
     */
    @Override
    public Map<Integer, Map<String, Object>> getInvoiceNums(Saleorder saleorder) {

        try {
            // 定义反序列化 数据格式
            final TypeReference<ResultInfo<Map<Integer, Map<String, Object>>>> TypeRef = new TypeReference<ResultInfo<Map<Integer, Map<String, Object>>>>() {
            };
            String url = httpUrl + "finance/invoice/getInvoiceNums.htm";
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, saleorder, clientId, clientKey, TypeRef);
            if (result != null && result.getCode() == 0) {
                return (Map<Integer, Map<String, Object>>)result.getData();
            }
        } catch (IOException e) {
            logger.info("操作失败", e);
            return null;
        }
        return null;
    }

    /** @description: getApplyDetailById.
     * @notes: VDERP-1325 分批开票 获取开票申请详情.
     * @author: Tomcat.Hui.
     * @date: 2019/11/21 9:58.
     * @param invoiceApply
     * @return: com.vedeng.finance.model.InvoiceApply.
     * @throws: .
     */
    @Override
    public InvoiceApply getApplyDetailById(InvoiceApply invoiceApply) {
        try {
            // 定义反序列化 数据格式
            final TypeReference<ResultInfo<InvoiceApply>> TypeRef = new TypeReference<ResultInfo<InvoiceApply>>() {
            };
            String url = httpUrl + "finance/invoice/getApplyDetailById.htm";
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, invoiceApply, clientId, clientKey, TypeRef);
            if (result != null && result.getCode() == 0) {
                return (InvoiceApply)result.getData();
            }
        } catch (IOException e) {
            logger.info("操作失败", e);
            return null;
        }
        return null;
    }

    /** @description: getInvoiceDetailById.
     * @notes:  VDERP-1325 分批开票 获取发票明细详情.
     * @author: Tomcat.Hui.
     * @date: 2019/11/21 10:03.
     * @param invoice
     * @return: com.vedeng.finance.model.Invoice.
     * @throws: .
     */
    @Override
    public Invoice getInvoiceDetailById(Invoice invoice){
        try {
            // 定义反序列化 数据格式
            final TypeReference<ResultInfo<Invoice>> TypeRef = new TypeReference<ResultInfo<Invoice>>() {
            };
            String url = httpUrl + "finance/invoice/getInvoicedDetailById.htm";
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, invoice, clientId, clientKey, TypeRef);
            if (result != null && result.getCode() == 0) {
                return (Invoice)result.getData();
            }
        } catch (IOException e) {
            logger.info("操作失败", e);
            return null;
        }
        return null;
    }
    /**
     *获取当前订单开票审核状态 1 存在审核中 0 不存在
     * @Author:strange
     * @Date:10:25 2019-11-23
     */
    @Override
    public Integer getInvoiceApplyNowAuditStatus(Integer saleorderId) {
        List<Integer> invoiceApallyIdList = invoiceMapper.getInvoiceApllyNum(saleorderId);
        if(invoiceApallyIdList!=null && invoiceApallyIdList.size()>0){
            return 1;
        }
        return 0;
    }

    public Express getExpressInfoById(Express ex) {
        // 接口调用
        String url = httpUrl + "logistics/express/getexpressinfobyid.htm";
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<Map<String, Object>>> TypeRef = new TypeReference<ResultInfo<Map<String, Object>>>() {
        };
        try {
            ResultInfo<Express> result2 = (ResultInfo<Express>) HttpClientUtils.post(url, ex, clientId, clientKey,
                    TypeRef);
            if (null == result2) {
                return null;
            }
            Map<String, Object> res = (Map<String, Object>) result2.getData();
            JSONObject jsonObject = JSONObject.fromObject(res.get("express"));
            Express expressInfo = (Express) JSONObject.toBean(jsonObject, Express.class);
            JSONArray jsonArray = JSONArray.fromObject(res.get("expressDetails"));
            List<ExpressDetail> expressDetails = (List<ExpressDetail>) JSONArray.toCollection(jsonArray,
                    ExpressDetail.class);
            expressInfo.setExpressDetail(expressDetails);
            return expressInfo;
        } catch (IOException e) {
            return null;
        }
    }
    @Override
    public InvoiceApply getInvoiceApply(Integer InvoiceApplyId) {
        return invoiceApplyMapper.selectByPrimaryKey(InvoiceApplyId);
    }

    @Override
    public ResultInfo<?> openEInvoiceBatchPush(InvoiceApply invoiceApply, String invoiceApplyIdArr) {

        List<Integer> invoiceApplyIdList = JSON.parseArray(invoiceApplyIdArr, Integer.class);

        final TypeReference<ResultInfo<Saleorder>> typeRef = new TypeReference<ResultInfo<Saleorder>>() {};
        String url = httpUrl + "finance/sheinvoice/openeinvoicepush.htm";
        ResultInfo<?> result =null;
        try {
            for (Integer invoiceApplyId:invoiceApplyIdList ) {
                InvoiceApply invoiceBatchApply=this.getInvoiceApply(invoiceApplyId);
                invoiceApply.setInvoiceApplyId(invoiceBatchApply.getInvoiceApplyId());
                invoiceApply.setRelatedId(invoiceBatchApply.getRelatedId());

                result =
                        (ResultInfo<?>) HttpClientUtils.post(url, invoiceApply, clientId, clientKey, typeRef);
                if (result != null) {
                    if (result.getCode() == 0 && result.getData() != null) {// 销售
                        Saleorder saleorder = (Saleorder) result.getData();
                        // 根据客户Id查询客户负责人
                        List<Integer> userIdList =
                                userMapper.getUserIdListByTraderId(saleorder.getTraderId(), ErpConst.ONE);
                        Map<String, String> map = new HashMap<>();
                        map.put("saleorderNo", saleorder.getSaleorderNo());
                        // 銷售開票后發送消息給銷售負責人
                        MessageUtil.sendMessage(11, userIdList, map,
                                "./order/saleorder/view.do?saleorderId=" + saleorder.getSaleorderId());
                        //更新订单updateDataTime
                        updateSaleOrderDataUpdateTime(saleorder.getSaleorderId(),null,OrderDataUpdateConstant.SALE_ORDER_INVOICE);
                    }
                }
            }
        }
        catch (Exception e) {
            logger.error("批量打印发票操作失败",e);
            return new ResultInfo<>();
        }
        return result;
    }
    /**
     *是否是票货同行订单
     * @param saleorderId
     * @return true 是票货同行订单
     * @Author:strange
     * @Date:13:54 2019-12-30
     */
    @Override
    public boolean getOrderIsGoodsPeer(Integer saleorderId) {
        if(saleorderId == null){
            return false;
        }
        Saleorder saleOrder = saleorderMapper.getSaleOrderById(saleorderId);
        //“票货同行”条件：
        //（1）HC订单
        //（2）开票方式为电子发票
        //（3）订单中全部商品的的发货方式为“普发”
        // (4) 无退票,退货售后单
        if(saleOrder != null) {
            if (saleOrder.getOrderType() != 5 || saleOrder.getInvoiceMethod() != 3 || saleOrder.getAddTime() < orderIsGoodsPeerTime) {
                return false;
            }
        }else{
            return false;
        }
        List<SaleorderGoods> saleorderGoodsList = saleorderGoodsMapper.getSaleorderGoodsListBySaleorderId(saleOrder.getSaleorderId());
        if(CollectionUtils.isNotEmpty(saleorderGoodsList)) {
            for (SaleorderGoods saleorderGoods : saleorderGoodsList) {
                if(saleorderGoods.getIsDelete() != null && saleorderGoods.getIsDelete().equals(1)){
                    continue;
                }
                if (saleorderGoods.getDeliveryDirect() != null && saleorderGoods.getDeliveryDirect().equals(1)) {
                    return false;
                }
            }
        }
        AfterSales afterSales = new AfterSales();
        afterSales.setOrderId(saleorderId);
        List<AfterSales> afterSalesList = afterSalesService.getAfterSaleListById(afterSales);
        if(CollectionUtils.isNotEmpty(afterSalesList)){
            return false;
        }
        return true;
    }
    /**
     * 更新开票申请信息
     * @Author:strange
     * @Date:15:40 2019-12-30
     */
    @Override
    public InvoiceApply updateInvoiceApplyInfo(InvoiceApply invoiceApply) {
        try {
        logger.info("updateInvoiceApplyInfo start expressId:{}",invoiceApply.getExpressId());
        List<InvoiceApplyDetail> invoiceApplyDetailList = invoiceApply.getInvoiceApplyDetails();
        Saleorder saleorder = saleorderMapper.getSaleOrderById(invoiceApply.getRelatedId());
        //开票申请备注
        invoiceApply.setComments(saleorder.getInvoiceComments() != null ? saleorder.getInvoiceComments() :"");
        saleorder.setSaleorderId(invoiceApply.getRelatedId());
        List<SaleorderGoods> saleorderGoodsList =  saleorderGoodsMapper.getSaleorderGoodsById(saleorder);

        //获取商品信息
        Map<Integer,SaleorderGoods> saleorderGoodsMap = saleorderGoodsList.stream().collect(Collectors.toMap(SaleorderGoods::getSaleorderGoodsId, goods->goods));
        Map<Integer, Map<String, Object>> taxNumsMap = getInvoiceNums(saleorder);
        saleorder.setExpressId(invoiceApply.getExpressId());
        //已发货快递数量
        Map<Integer,Integer> expressDetailMap = expressService.getExpressDetailNumInfo(saleorder);

        //首次开票申请将运费加入开票申请内
        Integer expressMenonySkuId = addFirstFreightInvoiceApply(taxNumsMap,saleorderGoodsMap,invoiceApplyDetailList);

        for (int i = 0; i < invoiceApplyDetailList.size(); i++) {
            InvoiceApplyDetail invoiceApplyDetail = invoiceApplyDetailList.get(i);
            //跳过运费
            if(invoiceApplyDetail.getDetailgoodsId().equals(expressMenonySkuId)){
                continue;
            }
            //本次申请数量
            BigDecimal nowNum = invoiceApplyDetail.getNum();
            //已申请数量
            Integer oldN = expressDetailMap.get(invoiceApplyDetail.getDetailgoodsId());
            if(oldN == null){
                oldN = 0;
            }
            BigDecimal oldNum = new BigDecimal(oldN);
            SaleorderGoods saleorderGoods = saleorderGoodsMap.get(invoiceApplyDetail.getDetailgoodsId());
            invoiceApplyDetail.setPrice(saleorderGoods.getPrice());
            //总商品数
            BigDecimal allNum = new BigDecimal(saleorderGoods.getNum());
            //本次申请数量+已申请数量>=总商品数   总价 = axSkuRefundAmount-(已申请数量*单价)
            if(nowNum.add(oldNum).compareTo(allNum) >= 0){
                BigDecimal totalAmount = saleorderGoods.getMaxSkuRefundAmount().subtract(oldNum.multiply(saleorderGoods.getPrice()));
                invoiceApplyDetail.setTotalAmount(totalAmount);
            }else{
                //否则  总价 = 单价*本次申请数量
                BigDecimal totalAmount = nowNum.multiply(saleorderGoods.getPrice());
                invoiceApplyDetail.setTotalAmount(totalAmount);
            }

            //判断以申请已开金额+当前申请金额不得大于订单总金额
            Map<String, Object> map = taxNumsMap.get(invoiceApplyDetail.getDetailgoodsId());
            BigDecimal invoiceAmount = new BigDecimal(map.get("INVOICE_AMOUNT").toString());
            BigDecimal applyAmount = new BigDecimal(map.get("APPLY_AMOUNT").toString());
            if(invoiceAmount.add(applyAmount).add(invoiceApplyDetail.getTotalAmount()).compareTo(saleorderGoods.getMaxSkuRefundAmount()) > 0){
                return null;
            }
        }
        Iterator<InvoiceApplyDetail> iterator = invoiceApplyDetailList.iterator();
        while(iterator.hasNext()){
            InvoiceApplyDetail invoiceApplyDetail = iterator.next();
            //如果申请总价为0,则不加入申请中
            if(invoiceApplyDetail.getTotalAmount().compareTo(BigDecimal.ZERO) == 0 ){
                iterator.remove();
            }
            logger.info("updateInvoiceApplyInfo end 开票申请详情:{}",invoiceApplyDetail.toString());
        }
        }catch (Exception e){
            logger.error("updateInvoiceApplyInfo expressId:{} ,error",invoiceApply.getExpressId(),e);
        }
        logger.info("updateInvoiceApplyInfo end expressId:{}",invoiceApply.getExpressId());
        return invoiceApply;
    }
    /**
    * 首次开票申请将运费加入开票申请内
    * @Author:strange
    * @Date:10:04 2020-01-14
    */
    private Integer addFirstFreightInvoiceApply(Map<Integer, Map<String, Object>> taxNumsMap, Map<Integer, SaleorderGoods> saleorderGoodsMap,
                                                List<InvoiceApplyDetail> invoiceApplyDetailList) {
        Integer expressMenonySkuId = 0;
        if(CollectionUtils.isNotEmpty(Collections.singleton(taxNumsMap))){
            //首次开票申请将运费加入开票申请内
            Set<Integer> integers = saleorderGoodsMap.keySet();
            for (Integer saleorderGoodsId : integers) {
                SaleorderGoods saleorderGoods = saleorderGoodsMap.get(saleorderGoodsId);
                if(saleorderGoods.getSku().equals("V127063")){
                    expressMenonySkuId = saleorderGoods.getSaleorderGoodsId();
                    break;
                }
            }
            if(expressMenonySkuId != 0) {
                Map<String, Object> map = taxNumsMap.get(expressMenonySkuId);
                if(CollectionUtils.isNotEmpty(Collections.singleton(map))){
                BigDecimal applyNum = new BigDecimal(map.get("APPLY_NUM").toString());
                BigDecimal invoiceNum = new BigDecimal(map.get("INVOICE_NUM").toString());
                if(applyNum.add(invoiceNum).compareTo(BigDecimal.ZERO) == 0) {
                    InvoiceApplyDetail invoiceApplyDetail = new InvoiceApplyDetail();
                    invoiceApplyDetail.setNum(new BigDecimal(1));
                    invoiceApplyDetail.setDetailgoodsId(expressMenonySkuId);
                    SaleorderGoods saleorderGoods = saleorderGoodsMap.get(expressMenonySkuId);
                    invoiceApplyDetail.setPrice(saleorderGoods.getPrice());
                    invoiceApplyDetail.setTotalAmount(saleorderGoods.getMaxSkuRefundAmount());
                    if (invoiceApplyDetail.getTotalAmount().compareTo(BigDecimal.ZERO) > 0) {
                        invoiceApplyDetailList.add(invoiceApplyDetail);
                    }
                }
                }
            }
        }
        return expressMenonySkuId;
    }

    /**
     * 票货同行快递单是否可编辑
     * @Author:strange
     * @Date:09:59 2020-01-06
     */
    @Override
    public Boolean getInvoiceApplyByExpressId(Integer expressId) {
      List<InvoiceApply> list =   invoiceApplyMapper.getInvoiceApplyByExpressId(expressId);
      if(CollectionUtils.isNotEmpty(list)) {
          for (InvoiceApply invoiceApply : list) {
              if (invoiceApply.getValidStatus().equals(1)){
                  return true;
              }
          }
      }
        return false;
    }
    /**
     *驳回票货同行未开票申请
     * @Author:strange
     * @Date:10:01 2020-01-06
     */
    @Override
    public void delInvoiceApplyByExpressId(Integer expressId) {
        logger.info("delInvoiceApplyByExpressId 驳回开票申请expressId:{}",expressId);
        List<InvoiceApply> list =   invoiceApplyMapper.getInvoiceApplyByExpressId(expressId);
        if(CollectionUtils.isNotEmpty(list)) {
            for (InvoiceApply invoiceApply : list) {
                logger.info("delInvoiceApplyByExpressId 驳回开票申请expressId:{},InvoiceApplyId:{}",expressId,invoiceApply.getInvoiceApplyId());
                invoiceApply.setValidStatus(2);
                invoiceApplyMapper.update(invoiceApply);
            }
        }
    }

    /** @description: getInvoiceApplyByExpressId.
     * @notes: VDERP-1039 票货同行 根据快递单ID查询开票申请.
     * @author: Tomcat.Hui.
     * @date: 2020/1/3 17:13.
     * @param expressId
     * @return: com.vedeng.model.finance.InvoiceApply.
     * @throws: .
     */
    @Override
    public List<InvoiceApply> getInvoiceApplyInfoByExpressId(InvoiceApply invoiceApply) {
        try {
            // 定义反序列化 数据格式
            final TypeReference<ResultInfo<List<InvoiceApply>>> TypeRef = new TypeReference<ResultInfo<List<InvoiceApply>>>() {
            };
            String url = httpUrl + "finance/invoice/getInvoiceApplyByExpressId.htm";
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, invoiceApply, clientId, clientKey, TypeRef);
            if (result != null && result.getCode() == 0) {
                return (List<InvoiceApply>)result.getData();
            }
        } catch (IOException e) {
            logger.info("操作失败", e);
            return null;
        }
        return null;
    }

    /** @description: getInvoiceByApplyId.
     * @notes: VDERP-1039 票货同行 根据开票申请ID获取发票信息.
     * @author: Tomcat.Hui.
     * @date: 2020/1/3 18:09.
     * @param invoiceApplyId
     * @return: com.vedeng.finance.model.InvoiceApply.
     * @throws: .
     */
    @Override
    public List<Invoice> getInvoiceByApplyId(Integer invoiceApplyId) {
        try {
            // 定义反序列化 数据格式
            final TypeReference<ResultInfo<List<Invoice>>> TypeRef = new TypeReference<ResultInfo<List<Invoice>>>() {
            };
            String url = httpUrl + "finance/invoice/getInvoiceByApplyId.htm";
            Invoice invoice = new Invoice();
            invoice.setInvoiceApplyId(invoiceApplyId);
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, invoice, clientId, clientKey, TypeRef);
            if (result != null && result.getCode() == 0) {
                return (List<Invoice>)result.getData();
            }
        } catch (IOException e) {
            logger.info("操作失败", e);
            return null;
        }
        return null;
    }

    @Override
    public boolean isDelExpressByExpressId(Express express) {
        // 496 销售订单
        if(express.getSaleorderId() != null && express.getBusinessType() != null && express.getBusinessType() == 496){
            boolean orderIsGoodsPeer = getOrderIsGoodsPeer(express.getSaleorderId());
            if(orderIsGoodsPeer) {
                List<Express> expressList = new ArrayList<>();
                expressList.add(express);
                List<Express> expressInvoiceInfo = getExpressInvoiceInfo(expressList);
                if (CollectionUtils.isNotEmpty(expressInvoiceInfo)) {
                    for (Express express1 : expressInvoiceInfo) {
                        //以开票则不可删除
                        if (CollectionUtils.isNotEmpty(express1.getInvoiceList())
                                || (express1.getInvoiceApply() != null &&
                                express1.getInvoiceApply().getValidStatus() != null && express1.getInvoiceApply().getValidStatus().equals(1))) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }


    /** @description: getInvoicedData.
     * @notes: VDERP-1874 开票中和已开票数量、金额的计算规则变更 获取已被占用数量(已申请+已开票).
     * @author: Tomcat.Hui.
     * @date: 2020/1/16 19:23.
     * @param saleorder
     * @return: java.util.Map<java.lang.Integer , java.math.BigDecimal>.
     * @throws: .
     */
    @Override
    public Map<Integer, BigDecimal> getInvoiceOccupiedAmount(Saleorder saleorder) {

        try {
            // 定义反序列化 数据格式
            final TypeReference<ResultInfo<Map<Integer, BigDecimal>>> TypeRef = new TypeReference<ResultInfo<Map<Integer, BigDecimal>>>() {
            };
            String url = httpUrl + "finance/invoice/getInvoiceOccupiedAmount.htm";
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, saleorder, clientId, clientKey, TypeRef);
            if (result != null && result.getCode() == 0) {
                return (Map<Integer, BigDecimal>)result.getData();
            }
        } catch (IOException e) {
            logger.info("操作失败", e);
            return null;
        }
        return null;
    }
    @Override
    public List<Express> getExpressInvoiceInfo(List<Express> expressList) {
        if (CollectionUtils.isNotEmpty(expressList)) {
            //获得开票申请
            expressList.stream().forEach( e -> {
                InvoiceApply query = new InvoiceApply();
                query.setExpressId(e.getExpressId());
                List<InvoiceApply> invoiceApplyList = this.getInvoiceApplyInfoByExpressId(query);
                if (null != invoiceApplyList && invoiceApplyList.size() == 1) {
                    InvoiceApply invoiceApply = invoiceApplyList.get(0);
                    e.setInvoiceApply(invoiceApply);
                    if (null != invoiceApply && invoiceApply.getValidStatus().equals(1)){
                        //查询发票信息
                        List<Invoice> invoiceList = this.getInvoiceByApplyId(invoiceApply.getInvoiceApplyId());
                        if (CollectionUtils.isNotEmpty(invoiceList)) {
                            e.setInvoiceList(invoiceList);
                        }
                    }
                    e.setInvoiceApply(invoiceApply);
                }
            });
        }
        return expressList;
    }

    /**
     * 快递是否关联发票
     * @param expressId
     * @return
     */
    @Override
    public Boolean getInvoiceApplyByExpressIdNo(Integer expressId) {
        List<InvoiceApply> list =   invoiceApplyMapper.getInvoiceApplyByExpressId(expressId);
        if(list==null || list.size()==0) {
            return true;
        }
        return false;
    }

    /**
     * 快递是否关联都为不通过发票
     * @param expressId
     * @return
     */
    @Override
    public Boolean getInvoiceApplyByExpressIdFaile(Integer expressId) {
        List<InvoiceApply> list =   invoiceApplyMapper.getInvoiceApplyByExpressIdFaile(expressId);
        if(CollectionUtils.isEmpty(list)) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否为待审核
     * @param expressId
     * @return
     */
    @Override
    public Boolean getInvoiceApplyByExpressIdByValidIsZero(Integer expressId) {
        List<InvoiceApply> list =   invoiceApplyMapper.getInvoiceApplyByExpressId(expressId);
        if(CollectionUtils.isNotEmpty(list)) {
            for (InvoiceApply invoiceApply : list) {
                if (invoiceApply.getValidStatus().equals(0)){
                    return true;
                }
            }
        }
        return false;
    }
    //查询所有开票申请
    @Override
    public List<InvoiceApply> getAllSaleInvoiceApplyList() {

        return invoiceApplyMapper.getAllSaleInvoiceApplyList();
    }
    //根据订单id查找开票记录
    @Override
    public List<Integer> getInvoiceApplyIdsBySaleOrderIds(List saleOrderNoList) {
        return invoiceApplyMapper.getInvoiceApplyIdsBySaleOrderIds(saleOrderNoList);
    }
    //改变标记状态
    @Override
    public int changeIsSign(List<Integer> endInvoiceApplyIds) {
        return invoiceApplyMapper.changeIsSign(endInvoiceApplyIds);
    }
}
