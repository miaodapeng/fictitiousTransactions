package com.vedeng.order.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.common.constants.Contant;
import com.vedeng.common.constant.OrderDataUpdateConstant;
import com.vedeng.common.util.StringUtil;
import com.vedeng.system.dao.ProcinstMapper;
import com.vedeng.system.dao.VerifiesInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.authorization.dao.OrganizationMapper;
import com.vedeng.authorization.dao.UserMapper;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.common.util.ObjectUtils;
import com.vedeng.finance.model.Invoice;
import com.vedeng.finance.model.PayApply;
import com.vedeng.goods.dao.GoodsChannelPriceMapper;
import com.vedeng.goods.dao.GoodsSafeStockMapper;
import com.vedeng.goods.dao.RCategoryJUserMapper;
import com.vedeng.goods.model.Goods;
import com.vedeng.goods.model.GoodsChannelPrice;
import com.vedeng.goods.model.GoodsSafeStock;
import com.vedeng.goods.model.vo.GoodsVo;
import com.vedeng.logistics.model.Express;
import com.vedeng.logistics.model.vo.WarehouseGoodsOperateLogVo;
import com.vedeng.order.model.Buyorder;
import com.vedeng.order.model.BuyorderGoods;
import com.vedeng.order.model.BuyorderModifyApply;
import com.vedeng.order.model.RBuyorderSaleorder;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.model.SaleorderGoods;
import com.vedeng.order.model.vo.BuyorderGoodsVo;
import com.vedeng.order.model.vo.BuyorderModifyApplyGoodsVo;
import com.vedeng.order.model.vo.BuyorderModifyApplyVo;
import com.vedeng.order.model.vo.BuyorderVo;
import com.vedeng.order.model.vo.SaleorderGoodsVo;
import com.vedeng.order.model.vo.SaleorderVo;
import com.vedeng.order.service.BuyorderService;
import com.vedeng.system.dao.AddressMapper;
import com.vedeng.system.model.Address;
import com.vedeng.system.model.Attachment;
import com.vedeng.system.model.ParamsConfigValue;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.system.model.vo.AddressVo;
import com.vedeng.system.service.AddressService;
import com.vedeng.system.service.ParamsConfigValueService;
import com.vedeng.trader.model.TraderAddress;
import com.vedeng.trader.model.vo.TraderAddressVo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * <b>Description:</b><br>
 * 采购订单service接口实现
 * 
 * @author east
 * @Note <b>ProjectName:</b> erp <br>
 *       <b>PackageName:</b> com.vedeng.order.service.impl <br>
 *       <b>ClassName:</b> BuyorderServiceImpl <br>
 *       <b>Date:</b> 2017年7月11日 上午9:20:01
 */
@Service("buyorderService")
public class BuyorderServiceImpl extends BaseServiceimpl implements BuyorderService {
    public static Logger logger = LoggerFactory.getLogger(BuyorderServiceImpl.class);

    @Resource
    private UserMapper userMapper;
    @Resource
    private OrganizationMapper organizationMapper;
    @Autowired
    @Qualifier("paramsConfigValueService")
    private ParamsConfigValueService paramsConfigValueService;
    @Autowired
    @Qualifier("addressService")
    private AddressService addressService;

    @Autowired
    @Qualifier("rCategoryJUserMapper")
    private RCategoryJUserMapper rCategoryJUserMapper;

    @Autowired
    @Qualifier("goodsChannelPriceMapper")
    private GoodsChannelPriceMapper goodsChannelPriceMapper;

    @Autowired
    @Qualifier("goodsSafeStockMapper")
    private GoodsSafeStockMapper goodsSafeStockMapper;

    @Resource
    private AddressMapper addressMapper;

    @Resource
    private ProcinstMapper procinstMapper;

    @Autowired
    private VerifiesInfoMapper verifiesInfoMapper;

    /**
     * <b>Description:</b><br>
     * 查询分页信息
     * 
     * @param buyorderVo
     * @param page
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年7月11日 上午9:25:35
     */
    @Override
    public Map<String, Object> getBuyorderVoPage(BuyorderVo buyorderVo, Page page) {

        Map<String, Object> map = null;
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<Map<String, Object>>> TypeRef =
                new TypeReference<ResultInfo<Map<String, Object>>>() {};
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(httpUrl + ErpConst.GET_BUYORDER_PAGE,
                    buyorderVo, clientId, clientKey, TypeRef, page);
            if (result != null && result.getCode() == 0) {
                Map<String, Object> result_map = (Map<String, Object>) result.getData();
                if (result_map != null && result_map.size() > 0) {
                    map = new HashMap<String, Object>();

                    net.sf.json.JSONArray json = null;
                    String openInvoiceApplyStr = result_map.get("list").toString();
                    json = net.sf.json.JSONArray.fromObject(openInvoiceApplyStr);

                    List<BuyorderVo> list = (List<BuyorderVo>) json.toCollection(json, BuyorderVo.class);
                    // map.put("buyorderList", list);

                    if (list != null && list.size() > 0) {
                        for (BuyorderVo bv : list) {
                            // 采购单归属：创建人
                            bv.setUserName(getUserNameByUserId(bv.getUserId()));

                            // 归属采购：供应商的归属
                            if (ObjectUtils.notEmpty(bv.getTraderId())) {
                                User user = userMapper.getUserByTraderId(bv.getTraderId(), ErpConst.TWO);
                                if (user != null) {
                                    bv.setHomePurchasing(getUserNameByUserId(user.getUserId()));
                                }
                            }
                            // 采购部门
                            if (ObjectUtils.notEmpty(bv.getOrgId())) {
                                bv.setBuyDepartmentName(
                                        organizationMapper.selectByPrimaryKey(bv.getOrgId()).getOrgName());
                            }
                            // 采购人员
                            if (ObjectUtils.notEmpty(bv.getUserId())) {
                                bv.setBuyPerson(getUserNameByUserId(bv.getUserId()));
                            }

                        }
                    }
                    map.put("list", list);

                    buyorderVo = (BuyorderVo) JSONObject.toBean(JSONObject.fromObject(result_map.get("buyorder")),
                            BuyorderVo.class);
                    map.put("buyorderVo", buyorderVo);

                    page = result.getPage();
                    map.put("page", page);
                }
            }
            return map;
        }
        catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * <b>Description:</b><br>
     * 获取新增页采购订单的详情
     * 
     * @param buyorder
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年7月19日 上午10:13:30
     */
    @Override
    public BuyorderVo getAddBuyorderVoDetail(Buyorder buyorder, User user) {
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
        try {
            BuyorderVo buyorderVo = new BuyorderVo();
            buyorderVo.setCompanyId(user.getCompanyId());
            buyorderVo.setBuyorderId(buyorder.getBuyorderId());
            buyorderVo.setBuyorderNo(buyorder.getBuyorderNo());
            buyorderVo.setCompanyId(user.getCompanyId());
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(httpUrl + ErpConst.GET_BUYORDER_DETAIL,
                    buyorderVo, clientId, clientKey, TypeRef);
            if (result == null || result.getCode() == -1) {
                return null;
            }
            JSONObject json = JSONObject.fromObject(result.getData());
            BuyorderVo bv = JsonUtils.readValue(json.toString(), BuyorderVo.class);
            if (bv != null && bv.getBuyorderGoodsVoList() != null && bv.getBuyorderGoodsVoList().size() > 0) {
                bv.setCreateName(getUserNameByUserId(bv.getCreator()));
                bv.setHomePurchasing(getUserNameByUserId(bv.getUserId()));
                bv.setBuyDepartmentName(getOrgNameByOrgId(bv.getOrgId()));
                List<BuyorderGoodsVo> list = bv.getBuyorderGoodsVoList();
                Integer bgSum = 0;
                BigDecimal bgAmount = new BigDecimal("0.00");
                for (BuyorderGoodsVo bgv : list) {
                    Integer buySum = 0;
                    if (bgv.getSaleorderGoodsVoList() != null && bgv.getSaleorderGoodsVoList().size() > 0) {
                        List<SaleorderGoodsVo> saleList = bgv.getSaleorderGoodsVoList();
                        for (SaleorderGoodsVo sgv : saleList) {
                            sgv.setApplicantName(getUserNameByUserId(sgv.getUserId()));
                            if (sgv.getDeliveryDirect() != null && sgv.getDeliveryDirect() == 1) {
                                buySum += sgv.getNum() - (sgv.getBuyNum() == null ? 0 : sgv.getBuyNum());
                            }
                            else {
                                buySum += sgv.getNeedBuyNum() == null ? 0 : sgv.getNeedBuyNum();
                            }
                            bv.setSaleorderNo(sgv.getSaleorderNo());
                            // 备货单采购价取默认备货单的采购价
                            if ("BH".equals(sgv.getSaleorderNo().substring(0, 2))) {
                                bgv.setPrice(sgv.getPrice());
                            }
                        }
                        bgv.setBuySum(buySum);
                        bgSum += buySum;
                    }
                    if (bgv.getNum() == null || bgv.getNum() == 0) {
                        bgv.setNum(buySum);
                    }
                    if (bgv.getPrice() != null) {
                        BigDecimal b1 = new BigDecimal(bgv.getNum());
                        // BigDecimal b2 = new BigDecimal(bgv.getPrice());
                        BigDecimal bgvAmount = b1.multiply(bgv.getPrice());
                        bgv.setOneBuyorderGoodsAmount(bgvAmount);
                    }
                    bgAmount = bgAmount.add(bgv.getOneBuyorderGoodsAmount());
                    bgv.setUserList(rCategoryJUserMapper.getUserByCategory(bgv.getCategoryId(), bgv.getCompanyId()));
                }
                bv.setBuyorderAmount(bgAmount);
                bv.setBuyorderSum(bgSum);
            }
            return bv;
        }
        catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * <b>Description:</b><br>
     * 获取采购订单的详情
     * 
     * @param buyorder
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年7月19日 上午10:13:30
     */
    @Override
    public BuyorderVo getBuyorderVoDetail(Buyorder buyorder, User user) {
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
        try {
            BuyorderVo buyorderVo = new BuyorderVo();
            buyorderVo.setCompanyId(user.getCompanyId());
            buyorderVo.setBuyorderId(buyorder.getBuyorderId());
            buyorderVo.setBuyorderNo(buyorder.getBuyorderNo());
            buyorderVo.setFlag(buyorder.getFlag());
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(httpUrl + ErpConst.GET_BUYORDER_DETAIL,
                    buyorderVo, clientId, clientKey, TypeRef);
            if (result == null || result.getCode() == -1) {
                return null;
            }
            JSONObject json = JSONObject.fromObject(result.getData());
            BuyorderVo bv = JsonUtils.readValue(json.toString(), BuyorderVo.class);
            List<User> userList = userMapper.getAllUserList(user.getCompanyId());
            if (bv != null && bv.getBuyorderGoodsVoList() != null && bv.getBuyorderGoodsVoList().size() > 0) {
                for (User us : userList) {
                    if (us.getUserId().equals(bv.getCreator())) {
                        bv.setCreateName(us.getUsername());
                    }
                    if (us.getUserId().equals(bv.getUserId())) {
                        bv.setHomePurchasing(us.getUsername());
                    }
                }
                bv.setBuyDepartmentName(getOrgNameByOrgId(bv.getOrgId()));
                List<BuyorderGoodsVo> list = bv.getBuyorderGoodsVoList();
                Integer bgSum = 0;
                BigDecimal bgAmount = new BigDecimal("0.00");
                for (BuyorderGoodsVo bgv : list) {
                    Integer buySum = 0;
                    if (bgv.getSaleorderGoodsVoList() != null && bgv.getSaleorderGoodsVoList().size() > 0) {
                        List<SaleorderGoodsVo> saleList = bgv.getSaleorderGoodsVoList();
                        for (SaleorderGoodsVo sgv : saleList) {
                            for (User us : userList) {
                                if (us.getUserId().equals(sgv.getUserId())) {
                                    sgv.setApplicantName(us.getUsername());
                                    break;
                                }

                            }

                            buySum += sgv.getNum() - (sgv.getBuyNum() == null ? 0 : sgv.getBuyNum());
                            bv.setSaleorderNo(sgv.getSaleorderNo());
                        }
                        bgv.setBuySum(buySum);
                    }
                    if (bgv.getPrice() != null && bgv.getNum() != null) {
                        BigDecimal b1 = new BigDecimal(bgv.getNum());
                        BigDecimal bgvAmount = b1.multiply(bgv.getPrice());
                        bgv.setOneBuyorderGoodsAmount(bgvAmount);
                    }
                    if (bgv.getNum() != 0) {
                        bgSum += bgv.getNum();
                    }
                    else {
                        // bgv.setNum(buySum);
                        // bgSum += buySum;
                    }
                    bgAmount = bgAmount.add(bgv.getOneBuyorderGoodsAmount());
                    bgv.setUserList(rCategoryJUserMapper.getUserByCategory(bgv.getCategoryId(), bgv.getCompanyId()));

                }
                bv.setBuyorderAmount(bgAmount);
                bv.setBuyorderSum(bgSum);
            }
            if (bv != null && bv.getAttachmentList() != null && bv.getAttachmentList().size() > 0) {
                List<Attachment> list = bv.getAttachmentList();
                for (Attachment attachment : list) {
                    for (User us : userList) {
                        if (us.getUserId().equals(attachment.getCreator())) {
                            attachment.setUsername(us.getUsername());
                            break;
                        }
                    }

                }
            }
            if (bv != null && bv.getWarehouseGoodsOperateLogVoList() != null
                    && bv.getWarehouseGoodsOperateLogVoList().size() > 0) {
                List<WarehouseGoodsOperateLogVo> list = bv.getWarehouseGoodsOperateLogVoList();
                for (WarehouseGoodsOperateLogVo av : list) {
                    for (User us : userList) {
                        if (us.getUserId().equals(av.getCreator())) {
                            av.setOperaterName(us.getUsername());
                            break;
                        }
                    }

                }
            }
            if (bv != null && bv.getExpressList() != null && bv.getExpressList().size() > 0) {
                List<Express> list = bv.getExpressList();
                for (Express express : list) {
                    for (User us : userList) {
                        if (us.getUserId().equals(express.getCreator())) {
                            express.setUpdaterUsername(us.getUsername());
                            break;
                        }
                    }
                }
            }

            if (bv != null && bv.getInvoiceList() != null && bv.getInvoiceList().size() > 0) {
                List<Invoice> list = bv.getInvoiceList();
                for (Invoice invoice : list) {
                    for (User us : userList) {
                        if (us.getUserId().equals(invoice.getCreator())) {
                            invoice.setCreatorName(us.getUsername());
                            break;
                        }
                    }

                }
            }
            // 采购人员
            if (ObjectUtils.notEmpty(bv.getUserId())) {
                for (User us : userList) {
                    if (us.getUserId().equals(bv.getUserId())) {
                        bv.setBuyPerson(us.getUsername());
                        break;
                    }
                }
            }
            return bv;
        }
        catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * <b>Description:</b><br>
     * 保存新增采购订单
     * 
     * @param buyorder
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年7月11日 上午9:27:14
     */
    @Override
    // @SystemServiceLog(operationType = "add",desc = "保存新增采购订单")
    public ResultInfo<?> saveOrUpdateBuyorderVo(BuyorderVo buyorderVo, User user) {
        if (buyorderVo != null && buyorderVo.getBuyorderId() == null) {
            buyorderVo.setCreator(user.getUserId());
            // 订单归属人和归属部门指产品负责人和产品负责人部门
            buyorderVo.setUserId(user.getUserId());
            buyorderVo.setOrgId(user.getOrgId());
            buyorderVo.setAddTime(DateUtil.sysTimeMillis());
            buyorderVo.setCreator(user.getUserId());
            buyorderVo.setCompanyId(user.getCompanyId());
        }
        buyorderVo.setUpdater(user.getUserId());
        buyorderVo.setModTime(DateUtil.sysTimeMillis());
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
        try {

            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(httpUrl + ErpConst.SAVE_ADD_BUYORDER,
                    buyorderVo, clientId, clientKey, TypeRef);
            if(result != null && result.getCode().equals(0)){
                //更新采购单updataTime
                Integer buyOrderId = (Integer) result.getData();
                String operateType = OrderDataUpdateConstant.BUY_VP_ORDER_GENERATE;
                if(buyorderVo.getOrderType().equals(1)){
                    operateType = OrderDataUpdateConstant.BUY_VB_ORDER_GENERATE;
                }
                updateBuyOrderDataUpdateTime(buyOrderId,null,operateType);
            }
            return result;
        }
        catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /**
     * <b>Description:</b><br>
     * 查询待采购列表
     * 
     * @param saleorderGoodsVo
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年7月13日 下午4:51:06
     */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> getSaleorderGoodsVoList(GoodsVo goodsVo) {
        long l1 = System.currentTimeMillis();
        List<GoodsVo> gvList = new ArrayList<>();
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<List<GoodsVo>>> TypeRef = new TypeReference<ResultInfo<List<GoodsVo>>>() {};
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(httpUrl + ErpConst.GET_SALESORDERGOODS_LIST,
                    goodsVo, clientId, clientKey, TypeRef);

            if (result == null || result.getCode() == -1) {
                return null;
            }
            List<GoodsVo> list = (List<GoodsVo>) result.getData();
            gvList.addAll(list);
            if (list != null && list.size() > 0 && result.getStatus() > 20) {
                int size = (int) Math.ceil((double) result.getStatus() / (double) 20);
                Integer currentCount = 2;
                for (int i = 0; i < size - 1; i++) {
                    goodsVo.setCurrentCount(currentCount);
                    result = (ResultInfo<?>) HttpClientUtils.post(httpUrl + ErpConst.GET_PURCHASE_LIST, goodsVo,
                            clientId, clientKey, TypeRef);
                    if (result == null || result.getCode() == -1) {
                        return null;
                    }
                    list = (List<GoodsVo>) result.getData();
                    gvList.addAll(list);
                    currentCount++;
                }

            }

            // 含有压缩方式
            // String result = (String) HttpClientUtils.compressPost(httpUrl +
            // ErpConst.GET_SALESORDERGOODS_LIST,
            // goodsVo, clientId, clientKey, TypeRef);
            // if (result == null || "".equals(result)) {
            // return null;
            // }
            // JSONObject json = JSONObject.fromObject(result);
            // String str = json.getString("data");
            // str = GZipUtils.unCompress(json.getString("data").toString());
            // JSONArray jsonArray = JSONArray.fromObject(str);
            // List<GoodsVo> list =
            // (List<GoodsVo>)JSONArray.toCollection(jsonArray, GoodsVo.class);
            // gvList.addAll(list);
            // Integer listSize = Integer.valueOf(json.getString("status"));
            // if(list != null && list.size() > 0 && listSize > 30){
            // int size = (int) Math.ceil((double)listSize/(double)30);
            // Integer currentCount = 2;
            // for(int i = 0;i<size-1;i++){
            // goodsVo.setCurrentCount(currentCount);
            // result = (String) HttpClientUtils.compressPost(httpUrl +
            // ErpConst.GET_PURCHASE_LIST,
            // goodsVo, clientId, clientKey, TypeRef);
            // if (result == null || "".equals(result)) {
            // return null;
            // }
            // json = JSONObject.fromObject(result);
            // str = json.getString("data");
            // str = GZipUtils.unCompress(json.getString("data").toString());
            // jsonArray = JSONArray.fromObject(str);
            // list = (List<GoodsVo>)JSONArray.toCollection(jsonArray,
            // GoodsVo.class);
            // gvList.addAll(list);
            // currentCount ++;
            // }
            //
            // }
            if (gvList != null && gvList.size() > 0) {
                Integer buySum = 0;// 待采购订单产品数量总计
                List<User> userList = rCategoryJUserMapper.batchUserByCategory(gvList, goodsVo.getCompanyId());
                List<User> allUserList = userMapper.getAllUserList(goodsVo.getCompanyId());
                for (GoodsVo gv : gvList) {
                    JSONArray jsonarray = JSONArray.fromObject(gv.getSgvList());
                    List<SaleorderGoodsVo> svList =
                            (List<SaleorderGoodsVo>) JSONArray.toCollection(jsonarray, SaleorderGoodsVo.class);
                    gv.setSgvList(svList);
                    // List<SaleorderGoodsVo> svList = gv.getSgvList();
                    Integer proBuySum = 0;
                    for (SaleorderGoodsVo sv : svList) {
                        for (User u : allUserList) {
                            if (u.getUserId().intValue() == sv.getUserId()) {
                                sv.setApplicantName(u.getUsername());
                                break;
                            }

                        }

                        proBuySum += sv.getNum() - sv.getBuyNum();
                    }
                    buySum += proBuySum;
                    gv.setProBuySum(proBuySum);

                    // gv.setUserList(rCategoryJUserMapper.getUserByCategory(gv.getCategoryId(),
                    // gv.getCompanyId()));

                    for (User u : userList) {
                        if (gv.getCategoryId().intValue() == u.getCategoryId().intValue()) {
                            if (gv.getUserList() == null) {
                                List<User> uList = new ArrayList<>();
                                uList.add(u);
                                gv.setUserList(uList);
                            }
                            else {
                                gv.getUserList().add(u);
                            }
                        }
                    }

                }
                Map<String, Object> map = new HashMap<>();
                map.put("buySum", buySum);
                map.put("list", gvList);
                long l2 = System.currentTimeMillis();
                System.out.println(l2 - l1);
                return map;
            }
            return null;
        }
        catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * <b>Description:</b><br>
     * 查询待采购列表分页
     * 
     * @param saleorderGoodsVo
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年7月13日 下午4:51:06
     */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> getSaleorderGoodsVoListPage(GoodsVo goodsVo, Page page) {

        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<List<SaleorderVo>>> TypeRef =
                new TypeReference<ResultInfo<List<SaleorderVo>>>() {};
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(httpUrl + ErpConst.GET_SALESORDERGOODS_LIST,
                    goodsVo, clientId, clientKey, TypeRef, page);
            if (result == null || result.getCode() == -1) {
                return null;
            }
            List<SaleorderVo> list = (List<SaleorderVo>) result.getData();
            page = result.getPage();

            if (list != null && list.size() > 0) {
                Integer buySum = 0;// 待采购订单产品数量总计

                List<User> allUserList = userMapper.getAllUserList(goodsVo.getCompanyId());

                for (SaleorderVo gv : list) {

                    List<User> userList = rCategoryJUserMapper.batchUserCategoryBySaleorderVo(gv.getSgvList(),
                            goodsVo.getCompanyId());
                    Integer proBuySum = 0;
                    for (User u : allUserList) {
                        if (gv.getOrderType() == 2 && gv.getCreator().intValue() == u.getUserId().intValue()) {// 备货单的申请人取创建人
                            gv.setApplicantName(u.getUsername());
                            break;
                        }
                        else if (gv.getOrderType() != 2 && u.getUserId().intValue() == gv.getValidUserId().intValue()) {
                            gv.setApplicantName(u.getUsername());
                            break;
                        }
                        else if (gv.getOrderType() != 2 && gv.getValidUserId().intValue() == 0) {
                            gv.setApplicantName(userMapper.getUserByTraderId(gv.getTraderId(), 1) == null ? ""
                                    : userMapper.getUserByTraderId(gv.getTraderId(), 1).getUsername());
                            break;
                        }

                    }
                    for (SaleorderGoodsVo sv : gv.getSgvList()) {
                        if (sv.getDeliveryDirect() == 0) {
                            proBuySum += sv.getNeedBuyNum() == null ? 0 : sv.getNeedBuyNum();
                        }
                        else {
                            proBuySum += sv.getNum() - (sv.getBuyNum() == null ? 0 : sv.getBuyNum());
                        }
                        for (User u : userList) {
                            if (sv.getCategoryId() != null
                                    && sv.getCategoryId().intValue() == u.getCategoryId().intValue()) {
                                if (sv.getUserList() == null) {
                                    List<User> uList = new ArrayList<>();
                                    uList.add(u);
                                    sv.setUserList(uList);
                                }
                                else {
                                    sv.getUserList().add(u);
                                }
                            }
                        }
                    }
                    buySum += proBuySum;
                    gv.setProBuySum(proBuySum);
                }
                Map<String, Object> map = new HashMap<>();
                map.put("buySum", buySum);
                map.put("list", list);
                map.put("page", page);
                return map;
            }
            return null;
        }
        catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * <b>Description:</b><br>
     * 忽略待采购订单
     * 
     * @param saleorderGoodsIds
     * @param user
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年7月21日 下午5:23:38
     */
    @Override
    // @SystemServiceLog(operationType = "eidt",desc = "忽略待采购订单")
    public ResultInfo<?> saveIgnore(String saleorderGoodsIds, User user) {
        if (saleorderGoodsIds == null || "".equals(saleorderGoodsIds) || saleorderGoodsIds.split(",").length == 0) {
            return null;
        }
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
        try {
            List<SaleorderGoods> list = new ArrayList<>();
            SaleorderGoods saleorderGoods = null;
            String saleorderGoodsIDs[] = saleorderGoodsIds.split(",");
            for (int i = 0; i < saleorderGoodsIDs.length; i++) {
                saleorderGoods = new SaleorderGoods();
                saleorderGoods.setUpdater(user.getUserId());
                saleorderGoods.setModTime(DateUtil.sysTimeMillis());
                saleorderGoods.setIgnoreTime(DateUtil.sysTimeMillis());
                saleorderGoods.setIgnoreUserId(user.getUserId());
                saleorderGoods.setIsIgnore(ErpConst.ONE);
                saleorderGoods.setSaleorderGoodsId(Integer.valueOf(saleorderGoodsIDs[i].split("\\|")[1]));
                list.add(saleorderGoods);
            }
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(httpUrl + ErpConst.SAVE_IGNORE, list, clientId,
                    clientKey, TypeRef);
            return result;
        }
        catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * <b>Description:</b><br>
     * 查询已忽略订单列表分页信息
     * 
     * @param saleorderGoodsVo
     * @param page
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年7月11日 上午9:25:35
     */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> getIgnoreSaleorderPage(SaleorderGoodsVo saleorderGoodsVo, Page page) {
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<List<SaleorderGoodsVo>>> TypeRef =
                new TypeReference<ResultInfo<List<SaleorderGoodsVo>>>() {};
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(httpUrl + ErpConst.GET_IGNORE_SALEORDER_PAGE,
                    saleorderGoodsVo, clientId, clientKey, TypeRef, page);
            if (result == null || result.getCode() == -1) {
                return null;
            }
            List<SaleorderGoodsVo> list = (List<SaleorderGoodsVo>) result.getData();
            for (SaleorderGoodsVo sgv : list) {
                sgv.setApplicantName(getUserNameByUserId(sgv.getUserId()));
                sgv.setIgnoreName(getUserNameByUserId(sgv.getIgnoreUserId()));

            }
            Map<String, Object> map = new HashMap<>();
            map.put("list", list);
            page = result.getPage();
            map.put("page", page);
            return map;
        }
        catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * <b>Description:</b><br>
     * 新增采购页面提交
     * 
     * @param buyorder
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年7月11日 上午9:27:14
     */
    @Override
    public ResultInfo<?> saveEditBuyorderAndBuyorderGoods(Buyorder buyorder, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        buyorder.setUpdater(user.getUserId());
        buyorder.setModTime(DateUtil.sysTimeMillis());
        if (!buyorder.getStatus().equals(ErpConst.THREE) && !buyorder.getStatus().equals(ErpConst.ONE)) {
            List<BuyorderGoods> bgList = getBuyorderGoods(request, user);
            List<RBuyorderSaleorder> sgList = getSaleorderGoods(request, user);
            map.put("bgList", bgList);
            map.put("sgList", sgList);

            // 获取页面提交的供应商联系人数据
            String SupplierContacts = request.getParameter("traderContactStr");
            if (ObjectUtils.notEmpty(SupplierContacts)) {
                String[] contacts = SupplierContacts.split("\\|");
                if (contacts.length > 0) {
                    buyorder.setTraderContactId(Integer.valueOf(contacts[0]));
                }
                if (contacts.length > 1) {
                    buyorder.setTraderContactName(contacts[1]);
                }
                if (contacts.length > 2) {
                    buyorder.setTraderContactMobile(contacts[2]);
                }
                if (contacts.length > 3) {
                    buyorder.setTraderContactTelephone(contacts[3]);
                }
            }

            // 获取页面提交的供应商地址数据
            String supplierAddress = request.getParameter("traderAddressStr");
            if (ObjectUtils.notEmpty(supplierAddress)) {
                String[] addresss = supplierAddress.split("\\|");
                if (addresss.length > 0) {
                    buyorder.setTraderAddressId(Integer.valueOf(addresss[0]));
                }
                if (addresss.length > 1) {
                    buyorder.setTraderArea(addresss[1]);
                }
                if (addresss.length > 2) {
                    buyorder.setTraderAddress(addresss[2]);
                }
            }
            if (buyorder.getDeliveryDirect() == 0) {
                String takeAddressId = request.getParameter("takeAddressId");
                if (ObjectUtils.notEmpty(takeAddressId) && takeAddressId.split("\\|").length > 2) {
                    Integer addressId = Integer.valueOf(takeAddressId.split("\\|")[0]);
                    Address add = addressService.getAddressById(addressId);
                    buyorder.setTakeTraderArea(takeAddressId.split("\\|")[2]);
                    buyorder.setTakeTraderName(takeAddressId.split("\\|")[1]);
                    if (add != null) {
                        buyorder.setTakeTraderAddress(add.getAddress());
                        buyorder.setTakeTraderContactName(add.getContactName());
                        buyorder.setTakeTraderContactMobile(add.getMobile());
                        buyorder.setTakeTraderContactTelephone(add.getTelephone());
                        buyorder.setTakeTraderAddressId(add.getAddressId());
                    }
                }
            }
        }
        map.put("buyorder", buyorder);
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
        try {

            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(httpUrl + ErpConst.SAVE_EDIT_BUYORDER, map,
                    clientId, clientKey, TypeRef);
            if(result!= null && result.getCode().equals(0)){
                updateBuyOrderDataUpdateTime(buyorder.getBuyorderId(),null, OrderDataUpdateConstant.BUY_ORDER_EDIT);
            }
            return result;// 操作成功
        }
        catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /**
     * <b>Description:</b><br>
     * 分解销售商品对象
     * 
     * @param request
     * @param user
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年8月4日 下午3:31:15
     */
    private List<RBuyorderSaleorder> getSaleorderGoods(HttpServletRequest request, User user) {
        List<RBuyorderSaleorder> sgList = new ArrayList<>();
        String[] dbBuyNums = request.getParameterValues("dbBuyNum");
        if (dbBuyNums != null && dbBuyNums.length > 0) {
            for (int i = 0; i < dbBuyNums.length; i++) {
                RBuyorderSaleorder sg = new RBuyorderSaleorder();
                sg.setBuyorderGoodsId(Integer.valueOf(dbBuyNums[i].split("\\|")[0]));
                sg.setSaleorderGoodsId(Integer.valueOf(dbBuyNums[i].split("\\|")[1]));
                sg.setNum(Integer.valueOf(dbBuyNums[i].split("\\|")[2]));
                sgList.add(sg);
            }
        }
        return sgList;
    }

    /**
     * <b>Description:</b><br>
     * 分解采购商品对象
     * 
     * @param request
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年8月3日 上午10:30:46
     */
    private List<BuyorderGoods> getBuyorderGoods(HttpServletRequest request, User user) {
        List<BuyorderGoods> bgList = new ArrayList<>();

        String[] buySums = request.getParameterValues("buySum");
        String[] prices = request.getParameterValues("price");
        String[] insideComments = request.getParameterValues("insideComments");
        String[] deliveryCycle = request.getParameterValues("deliveryCycle");
        String[] installation = request.getParameterValues("installation");
        String[] goodsComments = request.getParameterValues("goodsComments");
        
        if (buySums != null && buySums.length > 0) {
            for (int i = 0; i < buySums.length; i++) {
                BuyorderGoods bg = new BuyorderGoods();
                bg.setBuyorderGoodsId(Integer.valueOf(buySums[i].split("\\|")[0]));
                bg.setNum(Integer.valueOf(buySums[i].split("\\|")[1]));
                for (int j = 0; j < prices.length; j++) {
                    if (bg.getBuyorderGoodsId().equals(Integer.valueOf(prices[i].split("\\|")[0]))) {
                        bg.setPrice(BigDecimal.valueOf(Double.valueOf(prices[i].split("\\|")[1])));
                        break;
                    }
                }
                if (insideComments != null && insideComments.length > 0) {
                    for (int j = 0; j < insideComments.length; j++) {
                        if (i<insideComments.length&&bg.getBuyorderGoodsId().equals(Integer.valueOf(insideComments[i].split("\\|")[0]))) {
                        	if( insideComments[i].split("\\|").length > 1 ) {
                        		 bg.setInsideComments(insideComments[i].split("\\|")[1]);
                        	}else {
                        		bg.setInsideComments("");
                        	}
                            break;
                        }
                    }
                }
                if (deliveryCycle != null && deliveryCycle.length > 0) {
                    for (int j = 0; j < deliveryCycle.length; j++) {
                        if (i<deliveryCycle.length && StringUtil.isNotBlank(deliveryCycle[i]) &&
                                bg.getBuyorderGoodsId().equals(Integer.valueOf(deliveryCycle[i].split("\\|")[0]))) {
                        	if(deliveryCycle[i].split("\\|").length > 1 ) {
                        		bg.setDeliveryCycle(deliveryCycle[i].split("\\|")[1]);
	                       	}else {
	                       		bg.setDeliveryCycle("");
	                       	}
                           break;
                        }
                    }
                }
                if (installation != null && installation.length > 0) {
                    for (int j = 0; j < installation.length; j++) {
                        if (i<installation.length && bg.getBuyorderGoodsId().equals(Integer.valueOf(installation[i].split("\\|")[0]))) {
                        	if(installation[i].split("\\|").length > 1) {
                        		bg.setInstallation(installation[i].split("\\|")[1]);
	                       	}else {
	                       		bg.setInstallation("");
	                       	}
                            break;
                        }
                    }
                }
                if (goodsComments != null && goodsComments.length > 0) {
                    for (int j = 0; j < goodsComments.length; j++) {
                        if (i<goodsComments.length&&bg.getBuyorderGoodsId().equals(Integer.valueOf(goodsComments[i].split("\\|")[0]))) {
                        	if(goodsComments[i].split("\\|").length > 1) {
                        		bg.setGoodsComments(goodsComments[i].split("\\|")[1]);
	                       	}else {
	                       		bg.setGoodsComments("");
	                       	}
                            break;
                        }
                    }
                }
                bg.setUpdater(user.getUserId());
                bg.setModTime(DateUtil.sysTimeMillis());
                bgList.add(bg);
            }
        }
        return bgList;
    }

    /**
     * <b>Description:</b><br>
     * 获取加入采购订单页面的列表信息
     * 
     * @param buyorderVo
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年8月8日 上午11:21:00
     */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> getGoodsVoList(BuyorderVo buyorderVo) {
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
        try {

            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(httpUrl + ErpConst.GET_GOODSVO_LIST, buyorderVo,
                    clientId, clientKey, TypeRef);
            if (result == null || result.getCode() == -1) {
                return null;
            }else if(result.getCode() == -2){
                Map<String, Object> map = new HashMap<>();
                map.put("-2", -2);
                return map;
            }
            JSONArray json = JSONArray.fromObject(result.getData());
            List<GoodsVo> gvList = (List<GoodsVo>) JSONArray.toCollection(json, GoodsVo.class);
            Integer sum = 0;
            for (GoodsVo gv : gvList) {
                Integer num = 0;
                JSONArray jsonArray = JSONArray.fromObject(gv.getSgvList());
                List<SaleorderGoodsVo> list =
                        (List<SaleorderGoodsVo>) JSONArray.toCollection(jsonArray, SaleorderGoodsVo.class);
                for (SaleorderGoodsVo sgv : list) {
                    sgv.setApplicantName(getUserNameByUserId(sgv.getUserId()));
                    if (sgv.getDeliveryDirect() == 1) {
                        num += sgv.getNum() - (sgv.getBuyNum() == null ? 0 : sgv.getBuyNum());
                    }
                    else {
                        num += sgv.getNeedBuyNum() == null ? 0 : sgv.getNeedBuyNum();
                    }
                    gv.setSaleorderNo(sgv.getSaleorderNo());
                }
                sum += num;
                gv.setProBuySum(num);
                gv.setSgvList(list);
            }
            Map<String, Object> map = new HashMap<>();
            map.put("sum", sum);
            map.put("gvList", gvList);
            return map;
        }
        catch (Exception e) {
            logger.error(Contant.ERROR_MSG, e);
            throw new RuntimeException();
        }
    }

    /**
     * <b>Description:</b><br>
     * 加入已存在采购订单
     * 
     * @param buyorder
     * @param request
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年8月9日 上午10:34:46
     */
    @Override
    // @SystemServiceLog(operationType = "eidt",desc = "加入已存在采购订单")
    public ResultInfo<?> saveAddHavedBuyorder(Map<String, Object> map) {
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
        try {

            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(httpUrl + ErpConst.SAVE_HAVED_BUYORDER, map,
                    clientId, clientKey, TypeRef);
            return result;
        }
        catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<AddressVo> getAddressVoList(ParamsConfigValue paramsConfigValue) {
        List<Integer> addressIds = paramsConfigValueService.getParamsValueList(paramsConfigValue);
        List<AddressVo> list = new ArrayList<>();
        AddressVo adv = null;
        if (addressIds != null && addressIds.size() > 0) {
            list = addressMapper.getAddressVoList(addressIds);
            if (list != null && list.size() > 0) {
                adv = list.get(0);
            }
        }

        List<AddressVo> adList = addressMapper.getAddressVoListByParam(paramsConfigValue.getCompanyId());
        for (AddressVo av : adList) {
            if (adv != null && adv.getAddressId().intValue() == av.getAddressId().intValue()) {
                av.setIsDefault(1);
            }
            else {
                av.setIsDefault(0);
            }
            String area = getAddressByAreaId(av.getAreaId());
            av.setAreas(area.replace(" ", ""));
        }
        return adList;
    }

    @Override
    public ResultInfo<?> saveApplyPayment(PayApply payApply) {
        ResultInfo<?> result = null;
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<Integer>> TypeRef = new TypeReference<ResultInfo<Integer>>() {};
        String url = httpUrl + "order/buyorder/saveapplypayment.htm";
        try {
            result = (ResultInfo<Integer>) HttpClientUtils.post(url, payApply, clientId, clientKey, TypeRef);
        }
        catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
        }
        return result;
    }

    /**
     * <b>Description:</b><br>
     * 申请审核
     * 
     * @param buyorder
     * @param request
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年9月7日 上午9:31:36
     */
    @Override
    // @SystemServiceLog(operationType = "eidt",desc = "采购订单申请审核")
    public ResultInfo<?> saveApplyReview(Buyorder buyorder) {
        Map<String, Object> map = new HashMap<>();
        map.put("buyorder", buyorder);
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
        try {

            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(httpUrl + ErpConst.SAVE_EDIT_BUYORDER, map,
                    clientId, clientKey, TypeRef);
            return result;
        }
        catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /**
     * <b>Description:</b><br>
     * 关闭采购订单
     * 
     * @param buyorder
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年9月7日 上午9:38:08
     */
    @Override
    // @SystemServiceLog(operationType = "eidt",desc = "关闭采购订单")
    public ResultInfo<?> saveCloseBuyorder(Buyorder buyorder) {
        Map<String, Object> map = new HashMap<>();
        map.put("buyorder", buyorder);
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(httpUrl + ErpConst.SAVE_EDIT_BUYORDER, map,
                    clientId, clientKey, TypeRef);
            if(result != null && result.getCode().equals(0)){
                updateBuyOrderDataUpdateTime(buyorder.getBuyorderId(),null,OrderDataUpdateConstant.BUY_ORDER_CLOSE);
            }
            return result;
        }
        catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public BuyorderVo getBuyorderGoodsVoList(Buyorder buyorder) {
        try {
            // 定义反序列化 数据格式
            final TypeReference<ResultInfo<BuyorderVo>> TypeRef = new TypeReference<ResultInfo<BuyorderVo>>() {};
            String url = httpUrl + "order/buyorder/getbuyordergoodsbybuyorderid.htm";
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, buyorder, clientId, clientKey, TypeRef);
            JSONObject json = JSONObject.fromObject(result.getData());
            BuyorderVo sv = JsonUtils.readValue(json.toString(), BuyorderVo.class);
            if ("hh".equals(buyorder.getFlag()) || "th".equals(buyorder.getFlag())) {
                List<TraderAddress> list = sv.getTavList();
                if (list != null && list.size() > 0) {
                    List<TraderAddressVo> tavList = new ArrayList<>();
                    TraderAddressVo tav = null;
                    for (TraderAddress ta : list) {
                        tav = new TraderAddressVo();
                        tav.setTraderAddress(ta);
                        tav.setArea(getAddressByAreaId(ta.getAreaId()));
                        tavList.add(tav);
                    }
                    sv.setTraderAddressVoList(tavList);
                }
            }
            return sv;
        }
        catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            return null;
        }
    }

    /**
     * 保存采购单主表信息
     */
    @Override
    public ResultInfo<?> saveBuyorder(Buyorder buyorder) {
        String url = httpUrl + "order/buyorder/savebuyorderinfo.htm";
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, buyorder, clientId, clientKey, TypeRef);
            if (result.getCode() == 0) {
                return new ResultInfo(0, "操作成功");
            }
            else {
                return new ResultInfo();
            }
        }
        catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            return new ResultInfo();
        }
    }

    @Override
    public Map<String, Object> getBHManageList(GoodsVo goodsVo, Page page) {
        String url = httpUrl + "order/buyorder/getbhmanagelist.htm";
        final TypeReference<ResultInfo<Map<String, Object>>> TypeRef =
                new TypeReference<ResultInfo<Map<String, Object>>>() {};
        try {
            ResultInfo<?> result =
                    (ResultInfo<?>) HttpClientUtils.post(url, goodsVo, clientId, clientKey, TypeRef, page);
            Map<String, Object> mapData = (Map<String, Object>) result.getData();
            String listStr = (String) mapData.get("list");
            // String allListStr = (String) mapData.get("allList");

            List<GoodsVo> list = null;
            // List<GoodsVo> allList = null;

            if (!"[]".equals(listStr)) {
                net.sf.json.JSONArray json = net.sf.json.JSONArray.fromObject(listStr);
                list = (List<GoodsVo>) json.toCollection(json, GoodsVo.class);
            }
            else {
                list = new ArrayList<>();
            }
            // if(!"[]".equals(allList)){
            // net.sf.json.JSONArray allListStrjson =
            // net.sf.json.JSONArray.fromObject(allListStr);
            // allList = (List<GoodsVo>)
            // allListStrjson.toCollection(allListStrjson, GoodsVo.class);
            // }else{
            // allList = new ArrayList<>();
            // }

            if (null != list && list.size() > 0) {
                List<Integer> goodsIds = new ArrayList<>();
                List<Integer> categoryIds = new ArrayList<>();
                for (GoodsVo goods : list) {
                    goodsIds.add(goods.getGoodsId());
                    if (null != goods.getCategoryId() && goods.getCategoryId() > 0) {
                        categoryIds.add(goods.getCategoryId());
                    }

                    if (null != goods.getGoodsLevel() && goods.getGoodsLevel() > 0) {
                        SysOptionDefinition sysOptionDefinition = getSysOptionDefinitionById(goods.getGoodsLevel());
                        if (null != sysOptionDefinition) {
                            goods.setGoodsLevelName(sysOptionDefinition.getTitle());
                        }
                    }
                }

                if (goodsIds.size() > 0) {
                    // 安全库存
                    List<GoodsSafeStock> goodsSafeStock =
                            goodsSafeStockMapper.getGoodsSafeStock(goodsIds, goodsVo.getCompanyId());
                    if (null != goodsSafeStock && goodsSafeStock.size() > 0) {
                        for (GoodsSafeStock stock : goodsSafeStock) {
                            for (GoodsVo goods : list) {
                                if (stock.getGoodsId().equals(goods.getGoodsId())) {
                                    goods.setSafeNum(stock.getNum());
                                }
                            }
                        }
                    }
                    // 采购价
                    List<GoodsChannelPrice> purchasePrice = goodsChannelPriceMapper.getPurchasePrice(goodsIds);
                    if (null != purchasePrice && purchasePrice.size() > 0) {
                        for (GoodsChannelPrice channelPrice : purchasePrice) {
                            for (GoodsVo goods : list) {
                                if (channelPrice.getGoodsId().equals(goods.getGoodsId())) {
                                    goods.setPurchasePrice(channelPrice.getPublicPrice());
                                }
                            }
                        }
                    }
                }

                if (categoryIds.size() > 0) {
                    // 归属
                    List<User> userList =
                            rCategoryJUserMapper.getUserByCategoryIds(categoryIds, goodsVo.getCompanyId());
                    if (null != userList && userList.size() > 0) {
                        for (User u : userList) {
                            for (GoodsVo goods : list) {
                                if (u.getCategoryId().equals(goods.getCategoryId())) {
                                    goods.setOwner(u.getOwners());
                                }
                            }
                        }
                    }
                }
            }

            // Integer maybeSaleNum = 0;
            // BigDecimal maybeOccupyAmount = new BigDecimal(0);
            // if(null != allList && allList.size() > 0){
            // List<Integer> goodsIdsList = new ArrayList<>();
            // for(GoodsVo goods : allList){
            // goodsIdsList.add(goods.getGoodsId());
            // maybeSaleNum += goods.getMaybeSaleNum30();
            // }
            // //采购价
            // if(null != goodsIdsList && goodsIdsList.size() > 0){
            // List<GoodsChannelPrice> purchasePriceList =
            // goodsChannelPriceMapper.getPurchasePrice(goodsIdsList);
            // if(null != purchasePriceList && purchasePriceList.size() > 0){
            // for(GoodsChannelPrice channelPrice : purchasePriceList){
            // for(GoodsVo goods : allList){
            // if(channelPrice.getGoodsId().equals(channelPrice.getGoodsId())){
            // if(null != channelPrice.getPrivatePrice()){
            // maybeOccupyAmount =
            // maybeOccupyAmount.add(channelPrice.getPublicPrice().multiply(new
            // BigDecimal(goods.getMaybeSaleNum30())));
            // }
            // }
            // }
            // }
            // }
            // }
            // }

            page = result.getPage();
            Map<String, Object> map = new HashMap<>();
            map.put("list", list);
            map.put("page", page);
            // map.put("maybeSaleNum", maybeSaleNum);
            // map.put("maybeOccupyAmount", maybeOccupyAmount);
            return map;
        }
        catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            throw new RuntimeException();
        }
    }

    @Override
    public Map<String, Object> getBHManageStat(GoodsVo goodsVo) {
        String url = httpUrl + "order/buyorder/getbhmanagestat.htm";
        final TypeReference<ResultInfo<Map<String, Object>>> TypeRef =
                new TypeReference<ResultInfo<Map<String, Object>>>() {};
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, goodsVo, clientId, clientKey, TypeRef);
            Map<String, Object> mapData = (Map<String, Object>) result.getData();
            // String listStr = (String) mapData.get("list");
            String allListStr = (String) mapData.get("allList");

            List<GoodsVo> allList = null;

            if (!"[]".equals(allList)) {
                net.sf.json.JSONArray allListStrjson = net.sf.json.JSONArray.fromObject(allListStr);
                allList = (List<GoodsVo>) allListStrjson.toCollection(allListStrjson, GoodsVo.class);
            }
            else {
                allList = new ArrayList<>();
            }

            Integer maybeSaleNum = 0;
            BigDecimal maybeOccupyAmount = new BigDecimal(0);
            if (null != allList && allList.size() > 0) {
                List<Integer> goodsIdsList = new ArrayList<>();
                for (GoodsVo goods : allList) {
                    goodsIdsList.add(goods.getGoodsId());
                    maybeSaleNum += goods.getMaybeSaleNum30();
                }
                // 采购价
                if (null != goodsIdsList && goodsIdsList.size() > 0) {
                    List<GoodsChannelPrice> purchasePriceList = goodsChannelPriceMapper.getPurchasePrice(goodsIdsList);
                    if (null != purchasePriceList && purchasePriceList.size() > 0) {
                        for (GoodsChannelPrice channelPrice : purchasePriceList) {
                            for (GoodsVo goods : allList) {
                                if (channelPrice.getGoodsId().equals(channelPrice.getGoodsId())) {
                                    if (null != channelPrice.getPrivatePrice()) {
                                        maybeOccupyAmount = maybeOccupyAmount.add(channelPrice.getPublicPrice()
                                                .multiply(new BigDecimal(goods.getMaybeSaleNum30())));
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Map<String, Object> map = new HashMap<>();
            map.put("maybeSaleNum", maybeSaleNum);
            map.put("maybeOccupyAmount", maybeOccupyAmount);
            return map;
        }
        catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            throw new RuntimeException();
        }
    }

    @Override
    public ResultInfo batchAddBhSaleorderGoods(List<Integer> list, Saleorder bhSaleorder, HttpSession session) {
        User user = (User) session.getAttribute(Consts.SESSION_USER);
        Long time = DateUtil.sysTimeMillis();
        List<SaleorderGoods> saleorderGoods = new ArrayList<>();
        for (Integer goodsId : list) {
            SaleorderGoods sGoods = new SaleorderGoods();
            sGoods.setSaleorderId(bhSaleorder.getSaleorderId());
            sGoods.setGoodsId(goodsId);
            // 获取产品核价里面的采购价
            GoodsChannelPrice goodsChannelPrice = goodsChannelPriceMapper.getPurchasePriceByGoodsID(goodsId);
            if (null != goodsChannelPrice) {
                sGoods.setPurchasingPrice(goodsChannelPrice.getPublicPrice().toString());
                sGoods.setPrice(goodsChannelPrice.getPublicPrice());
            }
            sGoods.setCurrencyUnitId(1);
            sGoods.setAddTime(time);
            sGoods.setCreator(user.getUserId());
            sGoods.setModTime(time);
            sGoods.setUpdater(user.getUserId());
            Goods g = new Goods();
            g.setCompanyId(user.getCompanyId());
            g.setGoodsId(goodsId);
            sGoods.setGoods(g);

            saleorderGoods.add(sGoods);
        }

        String url = httpUrl + "order/buyorder/batchaddbhsaleordergoods.htm";
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
        try {
            ResultInfo<?> result =
                    (ResultInfo<?>) HttpClientUtils.post(url, saleorderGoods, clientId, clientKey, TypeRef);

            if (result != null) {
                return result;
            }
            else {
                return new ResultInfo<>();
            }
        }
        catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            throw new RuntimeException();
        }
    }

    @Override
    public BuyorderVo getBuyOrderPrintInfo(Buyorder buyorder) {
        try {
            // 定义反序列化 数据格式
            final TypeReference<ResultInfo<BuyorderVo>> TypeRef = new TypeReference<ResultInfo<BuyorderVo>>() {};
            String url = httpUrl + "order/buyorder/getbuyorderprintinfo.htm";
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, buyorder, clientId, clientKey, TypeRef);
            BuyorderVo sgw = (BuyorderVo) result.getData();
            return sgw;
        }
        catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            return null;
        }
    }

    @Override
    public List<Integer> getBuyorderUserBySaleorderGoodsIds(List<Integer> saleorderGoodsIds) {
        try {
            // 定义反序列化 数据格式
            final TypeReference<ResultInfo<List<Integer>>> TypeRef = new TypeReference<ResultInfo<List<Integer>>>() {};
            String url = httpUrl + "order/buyorder/getbuyorderuserbysaleordergoodsids.htm";
            ResultInfo<?> result =
                    (ResultInfo<?>) HttpClientUtils.post(url, saleorderGoodsIds, clientId, clientKey, TypeRef);
            List<Integer> buyorderUserNames = (List<Integer>) result.getData();
            return buyorderUserNames;
        }
        catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            return null;
        }
    }

    @Override
    public Buyorder confirmArrival(Buyorder buyorder, HttpServletRequest request, HttpSession session) {
        User user = (User) session.getAttribute(ErpConst.CURR_USER);
        Long time = DateUtil.sysTimeMillis();
        List<BuyorderGoods> buyorderGoodsList = new ArrayList<>();

        if (null != request.getParameter("id_str")) {
            String[] categoryAttributeIds2 = request.getParameter("id_str").split("_");
            for (String attIds2 : categoryAttributeIds2) {
                if (attIds2 == "" || null == request.getParameter("arrivalStatus_" + attIds2))
                    continue;
                BuyorderGoods buyorderGoods = new BuyorderGoods();

                buyorderGoods.setBuyorderGoodsId(Integer.parseInt(attIds2));
                buyorderGoods.setArrivalStatus(Integer.parseInt(request.getParameter("arrivalStatus_" + attIds2)));
                buyorderGoods.setArrivalTime(time);

                buyorderGoodsList.add(buyorderGoods);
            }

        }
        buyorder.setGoodsList(buyorderGoodsList);

        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<Buyorder>> TypeRef2 = new TypeReference<ResultInfo<Buyorder>>() {};
        try {
            ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(httpUrl + "order/buyorder/confirmarrival.htm",
                    buyorder, clientId, clientKey, TypeRef2);
            Buyorder res = (Buyorder) result2.getData();
            if(res != null){
                updateBuyOrderDataUpdateTime(res.getBuyorderId(),null,OrderDataUpdateConstant.BUY_ORDER_CONFIRM);
            }
            return res;
        }
        catch (IOException e) {
            return null;
        }
    }

    /**
     * <b>Description:</b><br>
     * 获取采购入库采购订单的详情(已拦截特殊商品)
     * 
     * @param buyorder
     * @param user
     * @return
     * @Note <b>Author:</b> Michael <br>
     *       <b>Date:</b> 2018年2月11日 下午1:18:30
     */
    @Override
    public BuyorderVo getBuyorderInDetail(BuyorderVo buyorder, User user) {
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
        try {
            BuyorderVo buyorderVo = new BuyorderVo();
            buyorderVo.setCompanyId(user.getCompanyId());
            buyorderVo.setBuyorderId(buyorder.getBuyorderId());
            buyorderVo.setBuyorderNo(buyorder.getBuyorderNo());
            buyorderVo.setBuyorderIdList(buyorder.getBuyorderIdList());
            String url = httpUrl + "order/buyorder/getbuyorderindetail.htm";
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, buyorderVo, clientId, clientKey, TypeRef);
            if (result == null || result.getCode() == -1) {
                return null;
            }
            JSONObject json = JSONObject.fromObject(result.getData());
            BuyorderVo bv = JsonUtils.readValue(json.toString(), BuyorderVo.class);
            if (bv != null && bv.getBuyorderGoodsVoList() != null && bv.getBuyorderGoodsVoList().size() > 0) {
                bv.setCreateName(getUserNameByUserId(bv.getCreator()));
                bv.setHomePurchasing(getUserNameByUserId(bv.getUserId()));
                bv.setBuyDepartmentName(getOrgNameByOrgId(bv.getOrgId()));
            }

            // 采购人员
            if (ObjectUtils.notEmpty(bv.getUserId())) {
                bv.setBuyPerson(getUserNameByUserId(bv.getUserId()));
            }
            return bv;
        }
        catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * <b>Description:</b><br>
     * 根据采购单查询运费
     * 
     * @param buyorderGoodsVo
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2018年3月6日 上午10:14:12
     */
    @Override
    public BuyorderGoodsVo getFreightByBuyorderId(BuyorderGoodsVo buyorderGoodsVo) {
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
        try {
            String url = httpUrl + "order/buyorder/getfreightbybuyorderid.htm";
            ResultInfo<?> result =
                    (ResultInfo<?>) HttpClientUtils.post(url, buyorderGoodsVo, clientId, clientKey, TypeRef);
            if (result == null || result.getCode() == -1) {
                return null;
            }
            JSONObject json = JSONObject.fromObject(result.getData());
            BuyorderGoodsVo bv = JsonUtils.readValue(json.toString(), BuyorderGoodsVo.class);
            return bv;
        }
        catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * <b>Description:</b><br>
     * 保存采购订单的运费
     * 
     * @param buyorderGoodsVo
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2018年3月6日 上午11:01:46
     */
    @Override
    public ResultInfo<?> saveBuyorderFreight(BuyorderGoodsVo buyorderGoodsVo, User user) {
        if (buyorderGoodsVo != null && ObjectUtils.isEmpty(buyorderGoodsVo.getBuyorderGoodsId())) {
            buyorderGoodsVo.setAddTime(DateUtil.sysTimeMillis());
            buyorderGoodsVo.setCreator(user.getUserId());
            buyorderGoodsVo.setModTime(DateUtil.sysTimeMillis());
            buyorderGoodsVo.setUpdater(user.getUserId());
        }
        else {
            buyorderGoodsVo.setModTime(DateUtil.sysTimeMillis());
            buyorderGoodsVo.setUpdater(user.getUserId());
        }
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
        try {
            String url = httpUrl + "order/buyorder/savebuyorderfreight.htm";
            ResultInfo<?> result =
                    (ResultInfo<?>) HttpClientUtils.post(url, buyorderGoodsVo, clientId, clientKey, TypeRef);
            return result;
        }
        catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public BuyorderVo getBuyorderVoDetailByAjax(Buyorder buyorder, User user) {
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
        try {
            String url = httpUrl + "order/buyorder/getbuyorderdetailbyajax.htm";
            BuyorderVo buyorderVo = new BuyorderVo();
            buyorderVo.setCompanyId(user.getCompanyId());
            buyorderVo.setBuyorderId(buyorder.getBuyorderId());
            buyorderVo.setBuyorderNo(buyorder.getBuyorderNo());
            buyorderVo.setSearchUserId(user.getUserId());
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, buyorderVo, clientId, clientKey, TypeRef);
            if (result == null || result.getCode() == -1) {
                return null;
            }
            JSONObject json = JSONObject.fromObject(result.getData());
            BuyorderVo bv = JsonUtils.readValue(json.toString(), BuyorderVo.class);
            List<User> userList = userMapper.getAllUserList(user.getCompanyId());

            for (User us : userList) {
                if (us.getUserId().equals(bv.getCreator())) {
                    bv.setCreateName(us.getUsername());
                }
                if (us.getUserId().equals(bv.getUserId())) {
                    bv.setHomePurchasing(us.getUsername());
                }
            }
            bv.setBuyDepartmentName(getOrgNameByOrgId(bv.getOrgId()));

            if (bv != null && bv.getAttachmentList() != null && bv.getAttachmentList().size() > 0) {
                List<Attachment> list = bv.getAttachmentList();
                for (Attachment attachment : list) {
                    for (User us : userList) {
                        if (us.getUserId().equals(attachment.getCreator())) {
                            attachment.setUsername(us.getUsername());
                            break;
                        }
                    }

                }
            }

            if (bv != null && bv.getExpressList() != null && bv.getExpressList().size() > 0) {
                List<Express> list = bv.getExpressList();
                for (Express express : list) {
                    for (User us : userList) {
                        if (us.getUserId().equals(express.getCreator())) {
                            express.setUpdaterUsername(us.getUsername());
                            break;
                        }
                    }
                }
            }

            if (bv != null && bv.getInvoiceList() != null && bv.getInvoiceList().size() > 0) {
                List<Invoice> list = bv.getInvoiceList();
                for (Invoice invoice : list) {
                    for (User us : userList) {
                        if (us.getUserId().equals(invoice.getCreator())) {
                            invoice.setCreatorName(us.getUsername());
                            break;
                        }
                    }

                }
            }
            // 采购人员
            if (ObjectUtils.notEmpty(bv.getUserId())) {
                for (User us : userList) {
                    if (us.getUserId().equals(bv.getUserId())) {
                        bv.setBuyPerson(us.getUsername());
                        break;
                    }
                }
            }
            return bv;
        }
        catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * <b>Description:</b><br>
     * 获取采购订单的详情异步
     * 
     * @param buyorder
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年7月19日 上午10:13:30
     */
    @Override
    public List<BuyorderGoodsVo> getBuyorderGoodsVoListByAjax(BuyorderVo buyorderVo, User user) {
        try {
            // int size = (int)
            // Math.ceil((double)buyorderVo.getBuyorderGoodsCount()/(double)10);
            if (ObjectUtils.notEmpty(buyorderVo.getCurrentCount())) {
                buyorderVo.setCurrentCount(2);
            }
            final TypeReference<ResultInfo<List<BuyorderGoodsVo>>> typeRef =
                    new TypeReference<ResultInfo<List<BuyorderGoodsVo>>>() {};
            String urlFor = httpUrl + "order/buyorder/getbuyordergoodslistbyajax.htm";
            ResultInfo<?> result = (ResultInfo<List<BuyorderGoodsVo>>) HttpClientUtils.post(urlFor, buyorderVo,
                    clientId, clientKey, typeRef);
            if (result == null || result.getCode() == -1) {
                return null;
            }
            List<BuyorderGoodsVo> bvlist = (List<BuyorderGoodsVo>) result.getData();

            return bvlist;
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            logger.error(Contant.ERROR_MSG, e);
            return null;
        }
    }

    /**
     * <b>Description:</b><br>
     * 获取出入库记录
     * 
     * @param buyorderVo
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2018年6月1日 上午9:14:55
     */
    @Override
    public List<WarehouseGoodsOperateLogVo> getWarehouseGoodsOperateLogVoListPage(BuyorderVo buyorderVo) {
        try {
            final TypeReference<ResultInfo<List<WarehouseGoodsOperateLogVo>>> typeRef =
                    new TypeReference<ResultInfo<List<WarehouseGoodsOperateLogVo>>>() {};
            String urlFor = httpUrl + "order/buyorder/getwarehousegoodsoperateloglist.htm";
            ResultInfo<?> result = (ResultInfo<List<WarehouseGoodsOperateLogVo>>) HttpClientUtils.post(urlFor,
                    buyorderVo, clientId, clientKey, typeRef);
            if (result == null || result.getCode() == -1) {
                return null;
            }
            List<WarehouseGoodsOperateLogVo> bvlist = (List<WarehouseGoodsOperateLogVo>) result.getData();
            if (bvlist != null && bvlist.size() > 0) {
                List<User> userList = userMapper.getAllUserList(buyorderVo.getCompanyId());
                for (WarehouseGoodsOperateLogVo av : bvlist) {
                    for (User us : userList) {
                        if (us.getUserId().equals(av.getCreator())) {
                            av.setOperaterName(us.getUsername());
                            break;
                        }
                    }
                }
            }
            return bvlist;
        }
        catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            return null;
        }
    }

    @Override
    public BuyorderVo getBuyorderGoodsVoListPage(BuyorderVo buyorderVo) {
        try {
            final TypeReference<ResultInfo<List<BuyorderGoodsVo>>> typeRef =
                    new TypeReference<ResultInfo<List<BuyorderGoodsVo>>>() {};
            String urlFor = httpUrl + "order/buyorder/getbuyordergoodsvolist.htm";
            ResultInfo<?> result = (ResultInfo<List<BuyorderGoodsVo>>) HttpClientUtils.post(urlFor, buyorderVo,
                    clientId, clientKey, typeRef);
            if (result == null || result.getCode() == -1) {
                return null;
            }
            List<BuyorderGoodsVo> bglist = (List<BuyorderGoodsVo>) result.getData();
            BuyorderVo bv = null;
            if (bglist != null && bglist.size() > 0) {
                bv = new BuyorderVo();
                List<User> userList = userMapper.getAllUserList(buyorderVo.getCompanyId());
                try {
                    Integer bgSum = 0;
                    BigDecimal bgAmount = new BigDecimal("0.00");
                    bv.setAftersaleCanClose(1);// 默认可以
                    boolean flag = false;
                    for (BuyorderGoodsVo bgv : bglist) {
                        // 订单的所有商品都退货后，订单可以关闭
                        // 只要有一个订单产品的数量跟售后退货产品数量不相等，该订单就不能关闭
                        if ((bgv.getAfterReturnNum() != null
                                && bgv.getNum().intValue() > bgv.getAfterReturnNum().intValue())
                                || bgv.getAfterReturnNum() == null) {
                            flag = true;
                            break;
                        }
                    }
                    if (flag) {
                        bv.setAftersaleCanClose(0);
                    }

                    for (BuyorderGoodsVo bgv : bglist) {
                        Integer buySum = 0;
                        if (bgv.getSaleorderGoodsVoList() != null && bgv.getSaleorderGoodsVoList().size() > 0) {
                            List<SaleorderGoodsVo> saleList = bgv.getSaleorderGoodsVoList();
                            for (SaleorderGoodsVo sgv : saleList) {
                                for (User us : userList) {
                                    if (us.getUserId().equals(sgv.getUserId())) {
                                        sgv.setApplicantName(us.getUsername());
                                        break;
                                    }

                                }

                                buySum += sgv.getNum() - (sgv.getBuyNum() == null ? 0 : sgv.getBuyNum());
                                bv.setSaleorderNo(sgv.getSaleorderNo());
                            }
                            bgv.setBuySum(buySum);
                        }
                        if (bgv.getPrice() != null && bgv.getNum() != null) {
                            BigDecimal b1 = new BigDecimal(bgv.getNum());
                            BigDecimal bgvAmount = b1.multiply(bgv.getPrice());
                            bgv.setOneBuyorderGoodsAmount(bgvAmount);
                        }
                        if (bgv.getNum() != 0) {
                            bgSum += bgv.getNum();
                        }
                        else {
                            // bgv.setNum(buySum);
                            // bgSum += buySum;
                        }
                        bgAmount = bgAmount.add(bgv.getOneBuyorderGoodsAmount());
                        bgv.setUserList(
                                rCategoryJUserMapper.getUserByCategory(bgv.getCategoryId(), bgv.getCompanyId()));
                    }
                    bv.setBuyorderAmount(bgAmount);
                    bv.setBuyorderSum(bgSum);
                }
                catch (Exception e) {
                    logger.error(Contant.ERROR_MSG, e);
                }
                bv.setBuyorderGoodsVoList(bglist);
            }
            return bv;
        }
        catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            return null;
        }
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public BuyorderVo getBuyorderVoDetailNew(Buyorder buyorder, User user) {
        // TODO Auto-generated method stub
        // Map<String,Object> map=new HashMap<String,Object>();
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<Map<String, Object>>> TypeRef =
                new TypeReference<ResultInfo<Map<String, Object>>>() {};
        try {
            BuyorderVo buyorderVo = new BuyorderVo();
            buyorderVo.setCompanyId(user.getCompanyId());
            buyorderVo.setBuyorderId(buyorder.getBuyorderId());
            buyorderVo.setBuyorderNo(buyorder.getBuyorderNo());
            ResultInfo<BuyorderVo> result = (ResultInfo<BuyorderVo>) HttpClientUtils
                    .post(httpUrl + ErpConst.GET_BUYORDER_DETAIL_NEW, buyorderVo, clientId, clientKey, TypeRef);
            if (result == null || result.getCode() == -1) {
                return null;
            }
            // map= result.getData();
            // page=result.getPage();
            JSONObject json = JSONObject.fromObject(result.getData());
            BuyorderVo bv = JsonUtils.readValue(json.toString(), BuyorderVo.class);
            // BuyorderVo bv = result.getData();
            //
            bv.setBuyDepartmentName(getOrgNameByOrgId(bv.getOrgId()));
            // 创建人名称
            bv.setCreateName(userMapper.getUserNameByUserId(bv.getCreator()));
            // 归属人名称
            bv.setHomePurchasing(userMapper.getUserNameByUserId(bv.getUserId()));
            // 销售订单申请人
            List<BuyorderGoodsVo> list = bv.getBuyorderGoodsVoList();
            List<User> userList = userMapper.getAllUserList(bv.getCompanyId());
            // Integer bgSum = 0;
            // BigDecimal bgAmount = new BigDecimal("0.00");
            /*
             * List<User> bgvUserList=new ArrayList<User>(); List<Integer> Ids=new ArrayList<Integer>();
             */
            for (BuyorderGoodsVo bgv : list) {
                // Integer buySum = 0;
                if (bgv.getSaleorderGoodsVoList() != null && bgv.getSaleorderGoodsVoList().size() > 0) {
                    List<SaleorderGoodsVo> saleList = bgv.getSaleorderGoodsVoList();
                    for (SaleorderGoodsVo sgv : saleList) {
                        // sgv.setApplicantName(userMapper.getUserNameByUserId(sgv.getUserId()));
                        for (User us : userList) {
                            if (us.getUserId().equals(sgv.getUserId())) {
                                sgv.setApplicantName(us.getUsername());
                                break;
                            }

                        }
                        // buySum += sgv.getNum() - (sgv.getBuyNum() == null ? 0
                        // : sgv.getBuyNum());
                        bv.setSaleorderNo(sgv.getSaleorderNo());
                    }
                    bgv.setSaleorderGoodsVoList(saleList);
                    // Ids.add(bgv.getCategoryId());
                    bgv.setUserList(rCategoryJUserMapper.getUserByCategory(bgv.getCategoryId(), user.getCompanyId()));
                    // bgv.setBuySum(buySum);//没用上，查了干嘛？？？？
                }
            }
            /*
             * bgvUserList=rCategoryJUserMapper.getUserByCategoryIds(Ids, user.getCompanyId()); for (BuyorderGoodsVo bgv
             * : list) { for(user) bgv.setUserList(); }
             */
            bv.setBuyorderGoodsVoList(list);
            // 合同回传
            if (bv != null && bv.getAttachmentList() != null && bv.getAttachmentList().size() > 0) {
                List<Attachment> lists = bv.getAttachmentList();
                for (Attachment attachment : lists) {
                    // attachment.setUsername(userMapper.getUserNameByUserId(attachment.getCreator()));
                    for (User us : userList) {
                        if (us.getUserId().equals(attachment.getCreator())) {
                            attachment.setUsername(us.getUsername());
                            break;
                        }
                    }
                }
                bv.setAttachmentList(lists);
            }
            // 入库记录
            /*
             * if(bv != null && bv.getWarehouseGoodsOperateLogVoList() != null &&
             * bv.getWarehouseGoodsOperateLogVoList().size() > 0){ List<WarehouseGoodsOperateLogVo> lists =
             * bv.getWarehouseGoodsOperateLogVoList(); for (WarehouseGoodsOperateLogVo av : lists) {
             * av.setOperaterName(userMapper.getUserNameByUserId(av.getCreator() )); for (User us : userList) {
             * if(us.getUserId().equals(av.getCreator())){ av.setOperaterName(us.getUsername()); break; } } }
             * bv.setWarehouseGoodsOperateLogVoList(lists); }
             */
            // 物流信息
            /*
             * if(bv != null && bv.getExpressList() !=null && bv.getExpressList().size()>0){ List<Express> lists =
             * bv.getExpressList(); for (Express express : lists) {
             * express.setUpdaterUsername(userMapper.getUserNameByUserId(express .getCreator())); for (User us :
             * userList) { if(us.getUserId().equals(express.getCreator())){
             * express.setUpdaterUsername(us.getUsername()); break; } } } bv.setExpressList(lists); }
             */
            // 发票列表
            if (bv != null && bv.getInvoiceList() != null && bv.getInvoiceList().size() > 0) {
                List<Invoice> lists = bv.getInvoiceList();
                for (Invoice invoice : lists) {
                    // invoice.setCreatorName(userMapper.getUserNameByUserId(invoice.getCreator()));
                    for (User us : userList) {
                        if (us.getUserId().equals(invoice.getCreator())) {
                            invoice.setCreatorName(us.getUsername());
                            break;
                        }
                    }
                }
                bv.setInvoiceList(lists);
            }
            // 采购人员名称
            bv.setBuyPerson(userMapper.getUserNameByUserId(bv.getUserId()));

            // map.put("buyorderVo", bv);
            // map.put("page", page);
            // return new ResultInfo(0, "查询成功", map);
            return bv;
        }
        catch (Exception e) {
            e.getMessage();
            throw new RuntimeException();
        }
    }

    @Override
    public BuyorderVo getBuyorderVoDetail(Buyorder buyorder) {
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<BuyorderVo>> TypeRef = new TypeReference<ResultInfo<BuyorderVo>>() {};
        try {
            String url = httpUrl + "order/buyorder/getapplybuyorderdetail.htm";
            @SuppressWarnings("unchecked")
            ResultInfo<BuyorderVo> result =
                    (ResultInfo<BuyorderVo>) HttpClientUtils.post(url, buyorder, clientId, clientKey, TypeRef);
            if (result == null || result.getCode() == -1) {
                return null;
            }

            return result.getData();
        }
        catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public BuyorderVo getSaleBuyNumByAjax(Buyorder buyorder, User user) {
        // TODO Auto-generated method stub
        // Map<String,Object> map=new HashMap<String,Object>();
        // Buyorder bv=new Buyorder();
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<BuyorderVo>> TypeRef = new TypeReference<ResultInfo<BuyorderVo>>() {};
        try {
            ResultInfo<BuyorderVo> result = (ResultInfo<BuyorderVo>) HttpClientUtils
                    .post(httpUrl + ErpConst.GET_SALEORDER_NUM_AJAX, buyorder, clientId, clientKey, TypeRef);
            if (result == null || result.getCode() == -1) {
                return null;
            }
            BuyorderVo bv = result.getData();
            List<User> userList = userMapper.getAllUserList(user.getCompanyId());
            // 物流信息
            if (bv != null && bv.getExpressList() != null && bv.getExpressList().size() > 0) {
                List<Express> lists = bv.getExpressList();
                for (Express express : lists) {
                    // express.setUpdaterUsername(userMapper.getUserNameByUserId(express.getCreator()));
                    for (User us : userList) {
                        if (us.getUserId().equals(express.getCreator())) {
                            express.setUpdaterUsername(us.getUsername());
                            break;
                        }
                    }
                }
                bv.setExpressList(lists);
            }
            if (bv != null && bv.getWarehouseGoodsOperateLogVoList() != null
                    && bv.getWarehouseGoodsOperateLogVoList().size() > 0) {
                List<WarehouseGoodsOperateLogVo> lists = bv.getWarehouseGoodsOperateLogVoList();
                for (WarehouseGoodsOperateLogVo av : lists) {
                    // av.setOperaterName(userMapper.getUserNameByUserId(av.getCreator()));
                    for (User us : userList) {
                        if (us.getUserId().equals(av.getCreator())) {
                            av.setOperaterName(us.getUsername());
                            break;
                        }
                    }
                }
                bv.setWarehouseGoodsOperateLogVoList(lists);
            }
            return bv;
        }
        catch (Exception e) {
            // TODO: handle exception
            throw new RuntimeException();
        }
    }

    /**
     * <b>Description:</b><br>
     * 保存采购修改单生效状态
     * 
     * @param buyorderGoodsVo
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2018年3月6日 上午11:01:46
     */
    @Override
    public ResultInfo<?> saveApplyBuyorderModfiyValidStatus(BuyorderModifyApply buyorderModifyApply) {

        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
        try {
            String url = httpUrl + "order/buyorder/saveapplybuyordermodfiystatus.htm";
            ResultInfo<?> result =
                    (ResultInfo<?>) HttpClientUtils.post(url, buyorderModifyApply, clientId, clientKey, TypeRef);
            return result;
        }
        catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            // throw new RuntimeException();
            return new ResultInfo<>();
        }
    }

    /**
     * <b>Description:</b><br>
     * 获取采购订单申请修改的详情
     * 
     * @param buyorder
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年7月19日 上午10:13:30
     */
    @Override
    public BuyorderVo getBuyorderVoApplyUpdateDetail(Buyorder buyorder) {
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<BuyorderVo>> TypeRef = new TypeReference<ResultInfo<BuyorderVo>>() {};
        try {
            String url = httpUrl + "order/buyorder/getapplybuyorderdetail.htm";
            @SuppressWarnings("unchecked")
            ResultInfo<BuyorderVo> result =
                    (ResultInfo<BuyorderVo>) HttpClientUtils.post(url, buyorder, clientId, clientKey, TypeRef);
            if (result == null || result.getCode() == -1) {
                return null;
            }
            BuyorderVo bv = result.getData();
            if (bv != null && bv.getBuyorderGoodsVoList() != null && bv.getBuyorderGoodsVoList().size() > 0) {
                List<BuyorderGoodsVo> list = bv.getBuyorderGoodsVoList();
                for (BuyorderGoodsVo bgv : list) {
                    bgv.setUserList(rCategoryJUserMapper.getUserByCategory(bgv.getCategoryId(), bgv.getCompanyId()));
                }
            }
            return result.getData();
        }
        catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            return null;
        }
    }

    /**
     * <b>Description:</b><br>
     * 保存采购订单的申请修改
     * 
     * @param buyorderGoodsVo
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2018年3月6日 上午11:01:46
     */
    @Override
    public ResultInfo<?> saveBuyorderApplyUpdate(BuyorderModifyApply buyorderModifyApply,
            BuyorderModifyApplyGoodsVo buyorderModifyApplyGoodsVo) {
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("buyorderModifyApply", buyorderModifyApply);
            map.put("buyorderModifyApplyGoodsVo", buyorderModifyApplyGoodsVo);
            String url = httpUrl + "order/buyorder/savebuyorderapplyupdate.htm";
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, map, clientId, clientKey, TypeRef);
            return result;
        }
        catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            return new ResultInfo<>();
        }
    }

    /**
     * <b>Description:</b><br>
     * 采购订单修改列表分页
     * 
     * @param buyorderModifyApplyVo
     * @param page
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2018年7月18日 上午11:13:55
     */
    @Override
    public Map<String, Object> getBuyorderModifyApplyListPage(BuyorderModifyApplyVo buyorderModifyApplyVo, Page page) {
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<List<BuyorderModifyApplyVo>>> TypeRef =
                new TypeReference<ResultInfo<List<BuyorderModifyApplyVo>>>() {};
        try {
            String url = httpUrl + "order/buyorder/getbuyordermodifyapplylistpage.htm";
            ResultInfo<List<BuyorderModifyApplyVo>> result = (ResultInfo<List<BuyorderModifyApplyVo>>) HttpClientUtils
                    .post(url, buyorderModifyApplyVo, clientId, clientKey, TypeRef, page);
            if (result == null || result.getCode() == -1) {
                return null;
            }
            List<BuyorderModifyApplyVo> list = result.getData();
            if (list != null) {
                for (BuyorderModifyApplyVo bmav : list) {
                    // 采购部门
                    if (ObjectUtils.notEmpty(bmav.getOrgId())) {
                        bmav.setOrgName(organizationMapper.selectByPrimaryKey(bmav.getOrgId()).getOrgName());
                    }
                    // 采购人员
                    if (ObjectUtils.notEmpty(bmav.getUserId())) {
                        bmav.setUserName(getUserNameByUserId(bmav.getUserId()));
                    }
                }
            }
            Map<String, Object> map = new HashMap<>();
            map.put("list", result.getData());
            map.put("page", result.getPage());
            return map;
        }
        catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            return null;
        }
    }

    /**
     * <b>Description:</b><br>
     * 
     * @param buyorderModifyApply
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2018年7月18日 下午2:34:24
     */
    @Override
    public BuyorderModifyApplyVo getBuyorderModifyApplyVoDetail(BuyorderModifyApply buyorderModifyApply) {
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<BuyorderModifyApplyVo>> TypeRef =
                new TypeReference<ResultInfo<BuyorderModifyApplyVo>>() {};
        try {
            String url = httpUrl + "order/buyorder/getbuyordermodifyapplydetail.htm";
            ResultInfo<BuyorderModifyApplyVo> result = (ResultInfo<BuyorderModifyApplyVo>) HttpClientUtils.post(url,
                    buyorderModifyApply, clientId, clientKey, TypeRef);
            if (result == null || result.getCode() == -1) {
                return null;
            }
            BuyorderModifyApplyVo bv = result.getData();
            if (bv != null && bv.getBgvList() != null && bv.getBgvList().size() > 0) {
                List<BuyorderGoodsVo> list = bv.getBgvList();
                for (BuyorderGoodsVo bgv : list) {
                    bgv.setUserList(rCategoryJUserMapper.getUserByCategory(bgv.getCategoryId(), bgv.getCompanyId()));
                }
            }
            return bv;
        }
        catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            return null;
        }
    }

    @Override
    public List<BuyorderModifyApply> getBuyorderModifyApplyList(BuyorderModifyApply buyorderModifyApply) {
        List<BuyorderModifyApply> list = null;
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<List<BuyorderModifyApply>>> TypeRef =
                new TypeReference<ResultInfo<List<BuyorderModifyApply>>>() {};
        String url = httpUrl + "order/buyorder/getbuyordermodifyapplylist.htm";
        try {
            ResultInfo<?> result =
                    (ResultInfo<?>) HttpClientUtils.post(url, buyorderModifyApply, clientId, clientKey, TypeRef);
            list = (List<BuyorderModifyApply>) result.getData();
            if (list != null && list.size() > 0) {
                List<Integer> userIds = new ArrayList<>();
                for (BuyorderModifyApply s : list) {
                    if (null != s.getCreator() && s.getCreator() > 0) {
                        userIds.add(s.getCreator());
                    }

                }

                if (userIds.size() > 0) {
                    List<User> userList = userMapper.getUserByUserIds(userIds);
                    for (BuyorderModifyApply s : list) {
                        for (User u : userList) {
                            if (u.getUserId().equals(s.getCreator())) {
                                s.setCreatorName(u.getUsername());
                            }
                        }
                    }
                }
            }
        }
        catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
        }
        return list;
    }

    @Override
    public BuyorderVo getBuyorderBarcodeVoDetail(BuyorderVo buyorder, User user) {
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
        try {
            BuyorderVo buyorderVo = new BuyorderVo();
            buyorderVo.setCompanyId(user.getCompanyId());
            buyorderVo.setBuyorderId(buyorder.getBuyorderId());
            buyorderVo.setBuyorderNo(buyorder.getBuyorderNo());
            String url = httpUrl + "order/buyorder/getbuyorderbarcodedetail.htm";
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, buyorderVo, clientId, clientKey, TypeRef);
            if (result == null || result.getCode() == -1) {
                return null;
            }
            JSONObject json = JSONObject.fromObject(result.getData());
            BuyorderVo bv = JsonUtils.readValue(json.toString(), BuyorderVo.class);
            if (bv != null && bv.getBuyorderGoodsVoList() != null && bv.getBuyorderGoodsVoList().size() > 0) {
                bv.setCreateName(getUserNameByUserId(bv.getCreator()));
                bv.setHomePurchasing(getUserNameByUserId(bv.getUserId()));
                bv.setBuyDepartmentName(getOrgNameByOrgId(bv.getOrgId()));
            }

            // 采购人员
            if (ObjectUtils.notEmpty(bv.getUserId())) {
                bv.setBuyPerson(getUserNameByUserId(bv.getUserId()));
            }
            return bv;
        }
        catch (IOException e) {
            throw new RuntimeException();
        }
    }

//    @Override
//    public List<Integer> getBuyOrderIdsByCurrentOperateUser(Page page, String currentOperateUser) {
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("currentOperateUser", currentOperateUser);
//        map.put("page", page);
//        List<String> processIds = procinstMapper.getBuyOrderIdsByCurrentOperateUser(map);
//        List<String>  buyOrderIdsByCurrentOperateUser = new ArrayList<>();
//        if (processIds.size() > 0){
//            map.put("processIds", processIds);
//            buyOrderIdsByCurrentOperateUser = procinstMapper.getBuyOrderIdsByProcessIds(map);
//        }
//        List<Integer> buyOrderIdsList = new ArrayList<>();
//        for(String str :buyOrderIdsByCurrentOperateUser){
//            String buyOrderId = str.split("_")[1];
//            buyOrderIdsList.add(Integer.parseInt(buyOrderId));
//        }
//        return buyOrderIdsList;
//    }


    @Override
    public List<Integer> getBuyOrderIdsByCurrentOperateUser(Page page, String currentOperateUser) {
        Map<String, Object> map = new HashMap<>();
        map.put("currentOperateUser", currentOperateUser);
        map.put("page", page);
        return verifiesInfoMapper.getBuyorderListUnVerified(map);
    }

}
