package com.vedeng.trader.service.impl;

import com.common.constants.Contant;
import com.fasterxml.jackson.core.type.TypeReference;
import com.newtask.model.YXGTraderAptitude;
import com.vedeng.activiti.service.ActionProcdefService;
import com.vedeng.authorization.dao.OrganizationMapper;
import com.vedeng.authorization.dao.RegionMapper;
import com.vedeng.authorization.dao.UserMapper;
import com.vedeng.authorization.model.Organization;
import com.vedeng.authorization.model.Position;
import com.vedeng.authorization.model.Role;
import com.vedeng.authorization.model.User;
import com.vedeng.authorization.model.vo.RoleVo;
import com.vedeng.common.constant.ApiUrlConstant;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.constant.TraderConstants;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.*;
import com.vedeng.finance.model.TraderAccountPeriodApply;
import com.vedeng.goods.dao.GoodsMapper;
import com.vedeng.logistics.model.FileDelivery;
import com.vedeng.order.dao.BussinessChanceMapper;
import com.vedeng.order.dao.SaleorderMapper;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.model.vo.SaleorderGoodsVo;
import com.vedeng.phoneticWriting.dao.PhoneticWritingMapper;
import com.vedeng.phoneticWriting.model.PhoneticWriting;
import com.vedeng.system.dao.VerifiesInfoMapper;
import com.vedeng.system.model.Tag;
import com.vedeng.system.model.VerifiesInfo;
import com.vedeng.system.model.vo.AccountSalerToGo;
import com.vedeng.system.service.RegionService;
import com.vedeng.system.service.UserService;
import com.vedeng.system.service.VerifiesRecordService;
import com.vedeng.trader.dao.*;
import com.vedeng.trader.model.*;
import com.vedeng.trader.model.vo.*;
import com.vedeng.trader.service.CommunicateService;
import com.vedeng.trader.service.TraderCustomerService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("traderCustomerService")
public class TraderCustomerServiceImpl extends BaseServiceimpl implements TraderCustomerService {
    public static Logger logger = LoggerFactory.getLogger(TraderCustomerServiceImpl.class);



    @Autowired
    @Qualifier("customerAptitudeCommentMapper")
    private CustomerAptitudeCommentMapper customerAptitudeCommentMapper;

	@Autowired
    @Qualifier("traderMapper")
	private TraderMapper traderMapper;

	@Autowired
    @Qualifier("traderCustomerMapper")
    private TraderCustomerMapper traderCustomerMapper;

    @Autowired
    @Qualifier("rTraderJUserMapper")
    private RTraderJUserMapper rTraderJUserMapper;

    @Autowired
    @Qualifier("userMapper")
    private UserMapper userMapper;

    @Resource
    private RegionMapper regionMapper;

    @Autowired
    @Qualifier("communicateRecordMapper")
    private CommunicateRecordMapper communicateRecordMapper;

    @Autowired
    @Qualifier("regionService")
    private RegionService regionService;

    @Autowired
    @Qualifier("communicateService")
    private CommunicateService communicateService;

    @Autowired
    @Qualifier("traderAddressMapper")
    private TraderAddressMapper traderAddressMapper;

    @Autowired
    @Qualifier("bussinessChanceMapper")
    private BussinessChanceMapper bussinessChanceMapper;

    @Autowired
    @Qualifier("phoneticWritingMapper")
    private PhoneticWritingMapper phoneticWritingMapper;

    @Autowired
    @Qualifier("webAccountMapper")
    private WebAccountMapper webAccountMapper;

    @Autowired
    @Qualifier("saleorderMapper")
    private SaleorderMapper saleorderMapper;

    @Autowired
    private OrganizationMapper organizationMapper;

    @Autowired
    @Qualifier("goodsMapper")
    private GoodsMapper goodsMapper;

    @Autowired
    private VerifiesInfoMapper verifiesInfoMapper;

    @Autowired
    @Qualifier("mjxAccountAddressMapper")
    private MjxAccountAddressMapper mjxAccountAddressMapper;
    @Autowired // 自动装载
    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    @Autowired
    @Qualifier("traderCertificateMapper")
    private TraderCertificateMapper traderCertificateMapper;
    @Autowired
    @Qualifier("actionProcdefService")
    private ActionProcdefService actionProcdefService;

    @Autowired
    @Qualifier("verifiesRecordService")
    private VerifiesRecordService verifiesRecordService;


    @Value("${yxg_host_url}")
    private String yxgHostUrl;

    @Value("${b2b_business_division_id}")
    private Integer b2bbusinessDivisionId;
    @Value("${yi_xie_purchase_id}")
    private Integer YiXiePurchaseId;
    @Value("${scientific_research_training_id}")
    private Integer scientificResearchTrainingId;

    @Autowired
    private UserService userService;

    @Autowired
    private TraderContactGenerateMapper traderContactGenerateMapper;

    @Override
    public TraderFinanceVo getTraderFinance(TraderFinanceVo finance) {
        String url=httpUrl+ErpConst.GET_TRADER_FINANCE;
        final TypeReference<ResultInfo<TraderFinanceVo>> TypeRef2 = new TypeReference<ResultInfo<TraderFinanceVo>>() {
        };
        try {
            ResultInfo<TraderFinanceVo> result2 = (ResultInfo<TraderFinanceVo>) HttpClientUtils.post(url, finance, clientId,
                    clientKey, TypeRef2);
            return (TraderFinanceVo)result2.getData();
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public ResultInfo syncTraderFinanceToPlatformOfYxg(TraderFinance traderFinance) {
        String taxNum = traderFinance.getTaxNum() == null ? "" : traderFinance.getTaxNum();
        String url = yxgHostUrl + ErpConst.SYNC_TRADER_FINANCE_TO_YXG + "?traderId=" + traderFinance.getTraderId() + "&taxNum=" + taxNum;
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo> TypeRef2 = new TypeReference<ResultInfo>() {
        };
        try {
            ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.getForYxg(url, null, clientId,
                    clientKey, TypeRef2);
            return result2;
        } catch (IOException e) {
            logger.error("同步至医械购平台错误：",e);
            return new ResultInfo(-1, "操作失败");
        }
    }

    @Override
    public int changeTraderOwner(RTraderJUser rTraderJUser) {
  //     Integer belongPlatform=userIdPlatFromId(rTraderJUser.getUserId(),ErpConst.ONE);
       Integer belongPlatform= userService.getBelongPlatformOfUser(rTraderJUser.getUserId(),ErpConst.ONE);
        RTraderJUser traderJUser = new RTraderJUser();
        traderJUser.setTraderType(ErpConst.ONE);
        traderJUser.setTraderId(rTraderJUser.getTraderId());
        rTraderJUserMapper.deleteRTraderJUser(traderJUser);
        int insert = rTraderJUserMapper.insert(rTraderJUser);
        Trader trader=new Trader();
        trader.setTraderId(rTraderJUser.getTraderId());
        trader.setBelongPlatform(belongPlatform);
        traderMapper.updateTrader(trader);
        if(insert > 0){
            WebAccountVo webAccountVo = new WebAccountVo();
            webAccountVo.setUserId(rTraderJUser.getUserId());
            webAccountVo.setTraderId(rTraderJUser.getTraderId());
            webAccountVo.setBelongPlatform(belongPlatform);
            webAccountVo.setModTime(new Date());
            int i = webAccountMapper.updateErpUserId(webAccountVo);
            //更新贝登会员
            updateVedengMember();
            return 1;
        }
        return 0;
    }

    @Override
    public String getOrderMedicalOfficeStr(TraderOrderGoods param) {

        List<String> skus=saleorderMapper.getOrderGoodsSkuByTraderId(param);
        if(CollectionUtils.isEmpty(skus)){
            return null;
        }
        List<String> departments=goodsMapper.getGoodsMedicalOffice(skus);
        if(CollectionUtils.isEmpty(departments)){
            return null;
        }
        StringBuilder stringBuilder=new StringBuilder();
        for(int i=0;i<departments.size();i++){

            if(StringUtil.isBlank(departments.get(i))){
                continue;
            }
            if(i==departments.size()-1){
                stringBuilder.append(departments.get(i));
                continue;
            }
            stringBuilder.append(departments.get(i)+"、");

        }
        return stringBuilder.toString();
    }

    @Override
    public CustomerAptitudeComment getCustomerAptitudeCommentByTraderId(Integer traderId) {
        return customerAptitudeCommentMapper.selectByCustomerId(traderId);
    }

    @Override
    public int addCustomerAptitudeComment(CustomerAptitudeComment addComment) {
        CustomerAptitudeComment comment=customerAptitudeCommentMapper.selectByCustomerId(addComment.getTraderCustomerId());
        if(comment!=null&&comment.getCommentId()!=null){
            customerAptitudeCommentMapper.updateByTraderId(addComment);
        }else if(StringUtil.isNotBlank(addComment.getComment())){
           customerAptitudeCommentMapper.insert(addComment);
        }
        return 1;
    }

    @Override
    public User getPersonalUser(Integer traderId) {
        User user = userMapper.getUserByTraderId(traderId, ErpConst.ONE);
        return user;
    }

    @Override
    public List<TraderCustomerCategory> getTraderCustomerCategoryByParentId(
            TraderCustomerCategory traderCustomerCategory) {
        String url = httpUrl + "trader/gettradercustomercategory.htm";
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<List<TraderCustomerCategory>>> TypeRef2 = new TypeReference<ResultInfo<List<TraderCustomerCategory>>>() {
        };
        try {
            ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, traderCustomerCategory, clientId,
                    clientKey, TypeRef2);
            List<TraderCustomerCategory> categoryList = (List<TraderCustomerCategory>) result2.getData();
            return categoryList;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public List<TraderCustomerAttributeCategory> getTraderCustomerAttributeByCategoryId(
            TraderCustomerAttributeCategory traderCustomerAttributeCategory) {
        String url = httpUrl + "trader/gettradercustomerattribute.htm";

        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<List<TraderCustomerAttributeCategory>>> TypeRef2 = new TypeReference<ResultInfo<List<TraderCustomerAttributeCategory>>>() {
        };
        try {
            ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, traderCustomerAttributeCategory, clientId,
                    clientKey, TypeRef2);
            List<TraderCustomerAttributeCategory> attributeList = (List<TraderCustomerAttributeCategory>) result2
                    .getData();
            return attributeList;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public List<TraderCustomerAttributeCategory> getTraderCustomerChildAttribute(
            TraderCustomerAttributeCategory traderCustomerAttributeCategory) {
        String url = httpUrl + "trader/gettradercustomerchildattribute.htm";

        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<List<TraderCustomerAttributeCategory>>> TypeRef2 = new TypeReference<ResultInfo<List<TraderCustomerAttributeCategory>>>() {
        };
        try {
            ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, traderCustomerAttributeCategory, clientId,
                    clientKey, TypeRef2);
            List<TraderCustomerAttributeCategory> attributeList = (List<TraderCustomerAttributeCategory>) result2
                    .getData();
            return attributeList;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public TraderCustomer saveCustomer(Trader trader, HttpServletRequest request, HttpSession session) {
        User user = (User) session.getAttribute(ErpConst.CURR_USER);
        Long time = DateUtil.sysTimeMillis();

        // 交易者基本信息
        trader.setCompanyId(user.getCompanyId());
        trader.setIsEnable(ErpConst.ONE);

        if(Integer.parseInt(request.getParameter("zone")) > 0){
            trader.setAreaId(Integer.parseInt(request.getParameter("zone")));
            trader.setAreaIds(request.getParameter("province")+","+request.getParameter("city")+","+request.getParameter("zone"));
        }else{
            trader.setAreaId(Integer.parseInt(request.getParameter("city")));
            trader.setAreaIds(request.getParameter("province")+","+request.getParameter("city")+","+request.getParameter("zone"));
        }

        trader.setAddTime(time);
        trader.setCreator(user.getUserId());
        trader.setModTime(time);
        trader.setUpdater(user.getUserId());

        // 客户
        TraderCustomer traderCustomer = trader.getTraderCustomer();

        //冗余字段 客户类型 客户性质
        if(null != request.getParameter("traderCustomerCategoryIds")){
            String ids = request.getParameter("traderCustomerCategoryIds");
            String[] idsArr = ids.split(",");
            if(null != idsArr[0]){
                if(idsArr[0].equals("1")){
                    traderCustomer.setCustomerType(SysOptionConstant.ID_426);
                }
                if(idsArr[0].equals("2")){
                    traderCustomer.setCustomerType(SysOptionConstant.ID_427);
                }
            }
            if(null != idsArr[1]){
                if(idsArr[1].equals("3") || idsArr[1].equals("5")){
                    traderCustomer.setCustomerNature(SysOptionConstant.ID_465);
                }
                if(idsArr[1].equals("4") || idsArr[1].equals("6")){
                    traderCustomer.setCustomerNature(SysOptionConstant.ID_466);
                }
            }
        }

        traderCustomer.setIsEnable(ErpConst.ONE);

        if (traderCustomer.getRegisteredDateStr()!=null && traderCustomer.getRegisteredDateStr() != "") {
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                traderCustomer.setRegisteredDate(dateformat.parse(traderCustomer.getRegisteredDateStr()));
            } catch (ParseException e) {
                return null;
            }
        }

        traderCustomer.setAddTime(time);
        traderCustomer.setCreator(user.getUserId());
        traderCustomer.setModTime(time);
        traderCustomer.setUpdater(user.getUserId());

        // 客户代理品牌
        if (null != request.getParameterValues("agentBrandId")) {
            String[] agentBrandIds = request.getParameterValues("agentBrandId");
            List<TraderCustomerAgentBrand> agentBrands = new ArrayList<>();
            for (String brandId : agentBrandIds) {
                TraderCustomerAgentBrand traderCustomerAgentBrand = new TraderCustomerAgentBrand();
                traderCustomerAgentBrand.setBrandId(Integer.parseInt(brandId));
                agentBrands.add(traderCustomerAgentBrand);
            }

            traderCustomer.setTraderCustomerAgentBrands(agentBrands);
        }

        //基础医疗经销商
        Integer basicMedicalDealer = 0;
        String lcfx = null;
        if(null != request.getParameter("isLcfx")){
            lcfx = request.getParameter("isLcfx");
        }
        // 客户属性
        if (null != request.getParameterValues("attributeId")) {
            String[] attributeIds = request.getParameterValues("attributeId");
            List<TraderCustomerAttribute> customerAttributes = new ArrayList<>();
            for (String attIds : attributeIds) {

                TraderCustomerAttribute customerAttribute = new TraderCustomerAttribute();
                String[] attArr = attIds.split("_");

                customerAttribute.setAttributeCategoryId(Integer.parseInt(attArr[0]));
                customerAttribute.setAttributeId(Integer.parseInt(attArr[1]));

                String pName = "attributedesc_" + attIds;
                if (null != request.getParameter(pName)) {
                    customerAttribute.setAttributeOther((String) request.getParameter(pName));
                }

                customerAttributes.add(customerAttribute);

                //基础医疗经销商
                if(lcfx != null && lcfx.equals("1")
                        && (Integer.parseInt(attArr[1]) == 95
                        || Integer.parseInt(attArr[1]) == 96
                        || Integer.parseInt(attArr[1]) == 97
                        || Integer.parseInt(attArr[1]) == 99
                        || Integer.parseInt(attArr[1]) == 100)){
                    basicMedicalDealer = 1;
                }
            }

            traderCustomer.setTraderCustomerAttributes(customerAttributes);

        }

        traderCustomer.setBasicMedicalDealer(basicMedicalDealer);


        // 客户经营区域
        if (null != request.getParameterValues("bussinessAreaId")) {
            String[] bussinessAreaIds = request.getParameterValues("bussinessAreaId");
            String[] bussinessAreaIdsStr = request.getParameterValues("bussinessAreaIds");
            List<TraderCustomerBussinessArea> bussinessAreas = new ArrayList<>();
            for (Integer i = 0; i < bussinessAreaIds.length; i++) {
                TraderCustomerBussinessArea traderCustomerBussinessArea = new TraderCustomerBussinessArea();
                traderCustomerBussinessArea.setAreaId(Integer.parseInt(bussinessAreaIds[i]));
                traderCustomerBussinessArea.setAreaIds(bussinessAreaIdsStr[i]);
                bussinessAreas.add(traderCustomerBussinessArea);
            }

            traderCustomer.setTraderCustomerBussinessAreas(bussinessAreas);
        }

        // 客户经营品牌
        if (null != request.getParameterValues("bussinessBrandId")) {
            String[] bussinessBrandIds = request.getParameterValues("bussinessBrandId");
            List<TraderCustomerBussinessBrand> bussinessBrands = new ArrayList<>();
            for (String brandId : bussinessBrandIds) {
                TraderCustomerBussinessBrand traderCustomerBussinessBrand = new TraderCustomerBussinessBrand();
                traderCustomerBussinessBrand.setBrandId(Integer.parseInt(brandId));
                bussinessBrands.add(traderCustomerBussinessBrand);
            }

            traderCustomer.setTraderCustomerBussinessBrands(bussinessBrands);
        }

        // 财务信息
        TraderFinance traderFinance = trader.getTraderFinance();
        if(traderFinance!=null){
            traderFinance.setTraderType(1);
            traderFinance.setAddTime(time);
            traderFinance.setCreator(user.getUserId());
            traderFinance.setModTime(time);
            traderFinance.setUpdater(user.getUserId());
        }
        // 接口调用
        String url = httpUrl + "trader/addcustomerinfo.htm";

        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<TraderCustomer>> TypeRef2 = new TypeReference<ResultInfo<TraderCustomer>>() {
        };
        try {
            ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, trader, clientId, clientKey, TypeRef2);
            TraderCustomer res = (TraderCustomer) result2.getData();

            if (null != res) {
                // 保存归属
                RTraderJUser rTraderJUser = new RTraderJUser();
                rTraderJUser.setTraderId(res.getTraderId());
                rTraderJUser.setUserId(user.getUserId());
                rTraderJUser.setTraderType(ErpConst.ONE);

                rTraderJUserMapper.insert(rTraderJUser);

                //根据客户的归属销售，更新客户的归属平台
                int belongPlatform = userService.getBelongPlatformOfUser(user.getUserId(),user.getCompanyId());
                Trader var = new Trader();
                var.setTraderId(res.getTraderId());
                var.setBelongPlatform(belongPlatform);
                traderMapper.updateTrader(var);
            }
            return res;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public TraderCustomerVo getTraderCustomerBaseInfo(TraderCustomer traderCustomer) {
        // 接口调用
        String url = httpUrl + "trader/getcustomerbaseinfo.htm";

        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<TraderCustomerVo>> TypeRef2 = new TypeReference<ResultInfo<TraderCustomerVo>>() {
        };
        try {
            ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, traderCustomer, clientId, clientKey,
                    TypeRef2);
            TraderCustomerVo res = (TraderCustomerVo) result2.getData();
            if(null != res.getTraderSupplierVo()){
                TraderSupplierVo traderSupplierVo = res.getTraderSupplierVo();
                User sale = userMapper.getUserByTraderId(traderSupplierVo.getTraderId(), ErpConst.TWO);
                if(null != sale){
                    traderSupplierVo.setOwnerSale(sale.getUsername());
                }
            }
            return res;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public TraderCustomerVo getTraderCustomerManageInfo(TraderCustomer traderCustomer,HttpSession session) {
        User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
        // 接口调用
        String url = httpUrl + "trader/getcustomermanageinfo.htm";

        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<TraderCustomerVo>> TypeRef2 = new TypeReference<ResultInfo<TraderCustomerVo>>() {
        };
        try {
            ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, traderCustomer, clientId, clientKey,
                    TypeRef2);
            TraderCustomerVo res = (TraderCustomerVo) result2.getData();
            if (null != res) {
                User sale = userMapper.getUserByTraderId(res.getTraderId(), ErpConst.ONE);
                if (null != sale) {
                    res.setOwnerSale(sale.getUsername());
                }

                CommunicateRecord communicateRecord = new  CommunicateRecord();
                communicateRecord.setTraderId(res.getTraderId());
                communicateRecord.setCompanyId(session_user.getCompanyId());
                communicateRecord.setTraderType(ErpConst.ONE);

                CommunicateRecord customerCommunicateCount = communicateRecordMapper.getTraderCommunicateCount(communicateRecord);
                if(null != customerCommunicateCount){
                    res.setCommuncateCount(customerCommunicateCount.getCommunicateCount());
                    res.setLastCommuncateTime(customerCommunicateCount.getLastCommunicateTime());
                }
                res.setCustomerProperty(getCustomerCategory(res.getTraderCustomerId()));
                User user = null;
                user = userMapper.getUserByTraderId(res.getTraderId(), ErpConst.ONE);
                if (null != user) {
                    res.setPersonal(user.getUsername());
                }
				/*//客户
				List<Integer> traderCustomerIds = new ArrayList<>();
				traderCustomerIds.add(res.getTraderCustomerId());
				communicateRecord.setTraderCustomerIds(traderCustomerIds);
				//商机
				if(null != res.getEnquiryOrderIds() && res.getEnquiryOrderIds().size() > 0){
					communicateRecord.setEnquiryOrderIds(res.getEnquiryOrderIds());
				}
				//报价
				if(null != res.getQuoteOrderIds() && res.getQuoteOrderIds().size() > 0){
					communicateRecord.setQuoteOrderIds(res.getQuoteOrderIds());
				}
				//订单
				if(null != res.getSaleOrderIds() && res.getSaleOrderIds().size() > 0){
					communicateRecord.setSaleOrderIds(res.getSaleOrderIds());
				}
				//售后
				if(null != res.getServiceOrderIds() && res.getServiceOrderIds().size() > 0){
					communicateRecord.setServiceOrderIds(res.getServiceOrderIds());
				}

				CommunicateRecord customerCommunicateCount = communicateRecordMapper.getCustomerCommunicateCount(communicateRecord);
				 */

            }
            return res;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public TraderCustomerVo getOrderCoverInfo(TraderOrderGoods traderOrderGoods) {
        ResultInfo<?> result = null;
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<TraderCustomerVo>> TypeRef = new TypeReference<ResultInfo<TraderCustomerVo>>() {};
        String url = httpUrl + "trader/getordercoverinfo.htm";
        try {
            result = (ResultInfo<?>) HttpClientUtils.post(url, traderOrderGoods, clientId, clientKey, TypeRef);
            TraderCustomerVo res = (TraderCustomerVo) result.getData();

            //去重订单覆盖品类，订单覆盖品牌，订单覆盖产品+
            List<String> categoryNameList = new ArrayList<>();
            List<String> brandNameList = new ArrayList<>();
            List<String> areaList = new ArrayList<>();
            List<Integer> areaIdList = new ArrayList<>();

            List<TraderOrderGoods> traderOrderGoodsList = res.getTraderOrderGoodsList();
            if(traderOrderGoodsList != null && traderOrderGoodsList.size() > 0){
                for(TraderOrderGoods traderOrderGoods1 : traderOrderGoodsList){
                    categoryNameList.add(traderOrderGoods1.getCategoryName());
                    brandNameList.add(traderOrderGoods1.getBrandName());

                    if(null != traderOrderGoods1.getAreaId() && traderOrderGoods1.getAreaId() > 0){
                        areaIdList.add(traderOrderGoods1.getAreaId());

                    }

//					if(null != traderOrderGoods1.getAreaName().trim() && !"".equals(traderOrderGoods1.getAreaName().trim())){
//						areaList.add(traderOrderGoods1.getAreaName());
//					}

//					if(traderOrderGoods1.getAreaId() != null && traderOrderGoods1.getAreaId() > 0){
//						areaList.add((String)regionService.getRegion(traderOrderGoods1.getAreaId(), 2));
//					}
                }
                areaIdList = new ArrayList(new HashSet(areaIdList));
                if(areaIdList.size() > 0){
                    for(Integer areaId : areaIdList){
                        String regionName = (String) regionService.getRegion(areaId, 2);
                        if(null != regionName){
                            areaList.add(regionName);
                        }
                    }
                }
            }
            res.setCategoryNameList(new ArrayList(new HashSet(categoryNameList)));
            res.setBrandNameList(new ArrayList(new HashSet(brandNameList)));
            res.setAreaList(new ArrayList(new HashSet(areaList)));

            return res;
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
        }
        return null;
    }

    @Override
    public TraderCustomer saveEditManageInfo(TraderCustomer traderCustomer, HttpServletRequest request,
                                             HttpSession session) {
        User user = (User) session.getAttribute(ErpConst.CURR_USER);
        Long time = DateUtil.sysTimeMillis();

        Trader trader = new Trader();
        trader.setCompanyId(user.getCompanyId());

        traderCustomer.setModTime(time);
        traderCustomer.setUpdater(user.getUserId());
        traderCustomer.setTrader(trader);

        // 客户属性(战略合作伙伴)
        if (null != request.getParameterValues("attributeId")) {
            String[] attributeIds = request.getParameterValues("attributeId");
            List<TraderCustomerAttribute> customerAttributes = new ArrayList<>();
            for (String attIds : attributeIds) {
                TraderCustomerAttribute customerAttribute = new TraderCustomerAttribute();

                customerAttribute.setAttributeCategoryId(SysOptionConstant.ID_CUSTOMER_ATTRIBUTE_CATEGORY_26);
                customerAttribute.setAttributeId(Integer.parseInt(attIds));
                customerAttribute.setTraderCustomerId(traderCustomer.getTraderCustomerId());
                customerAttributes.add(customerAttribute);
            }

            traderCustomer.setTraderCustomerAttributes(customerAttributes);

        }

        // 标签
        if (null != request.getParameterValues("tagId")) {// 标签库标签
            String[] tagIds = request.getParameterValues("tagId");
            List<Tag> tagList = new ArrayList<>();
            for (String tagId : tagIds) {
                Tag tag = new Tag();
                tag.setTagType(SysOptionConstant.ID_30);
                tag.setTagId(Integer.parseInt(tagId));
                tag.setCompanyId(user.getCompanyId());
                tagList.add(tag);
            }

            traderCustomer.setTag(tagList);
        }
        if (null != request.getParameterValues("tagName")) {// 自定义标签
            String[] tagNames = request.getParameterValues("tagName");
            List<String> tagNameList = new ArrayList<>();
            for (String tagName : tagNames) {
                tagNameList.add(tagName);
            }

            traderCustomer.setTagName(tagNameList);
        }

        // 接口调用
        String url = httpUrl + "trader/saveeditmanageinfo.htm";

        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<TraderCustomer>> TypeRef2 = new TypeReference<ResultInfo<TraderCustomer>>() {
        };
        try {
            ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, traderCustomer, clientId, clientKey,
                    TypeRef2);
            TraderCustomer res = (TraderCustomer) result2.getData();

            return res;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * <b>Description:</b><br>
     * 客户分页
     *
     * @param traderCustomerVo
     * @param page
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年5月18日 下午1:22:49
     */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> getTraderCustomerVoPage(TraderCustomerVo traderCustomerVo, Page page,List<User> userList) {
        //long l1 = System.currentTimeMillis();
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<List<TraderCustomerVo>>> TypeRef = new TypeReference<ResultInfo<List<TraderCustomerVo>>>() {
        };
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(httpUrl + ErpConst.TRADER_CUSTOMER_PAGE, getComIds(traderCustomerVo), clientId, clientKey, TypeRef, page);
            if (result == null || result.getCode() == -1) {
                return null;
            }

            List<TraderCustomerVo> list = (List<TraderCustomerVo>) result.getData();
            //User user = null;

            if(list != null && list.size() > 0){
                CommunicateRecord communicateRecord=new CommunicateRecord();
                communicateRecord.setCompanyId(traderCustomerVo.getCompanyId());
                communicateRecord.setTraderCustomerVos(list);
                communicateRecord.setTraderType(ErpConst.ONE);

                //批量查询客户的沟通次数
                List<CommunicateRecord> crList = communicateRecordMapper.getTraderCommunicateCountList(communicateRecord);
                //所有销售
                //List<User> saleUserList  = userMapper.getUserByPositType(SysOptionConstant.ID_310, traderCustomerVo.getCompanyId());
                for (TraderCustomerVo tcv : list) {
                    //取客户的沟通次数
                    for (CommunicateRecord cr : crList) {
                        if(ObjectUtils.notEmpty(cr.getTraderId()) && cr.getTraderId().intValue() == tcv.getTraderId().intValue()){
                            tcv.setCommuncateCount(cr.getCommunicateCount() == null ? 0 : cr.getCommunicateCount());
                            break;
                        }
                    }

                    User user = userMapper.getUserByTraderId(tcv.getTraderId(), ErpConst.ONE);
                    if (null != user) {
                        tcv.setPersonal(user.getUsername());
                        if(userList !=null && userList.size() > 0){
                            for (User u : userList) {
                                if(user.getUserId().equals(u.getUserId())){
                                    tcv.setIsView(ErpConst.ONE);
                                    break;
                                }
                            }
                        }
                    }

                    if(null != tcv.getAreaId() && tcv.getAreaId() > 0){
                        tcv.setAddress(getAddressByAreaId(tcv.getAreaId()));
                    }

                }

            }
            page = result.getPage();
            Map<String, Object> map = new HashMap<>();
            map.put("list", list);
            map.put("page", page);
            return map;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * <b>Description:</b><br> 根据信息搜索查询沟通记录id集合获取到traderId的集合，封装到traderCustomerVo
     * @param traderCustomerVo
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年7月18日 上午11:41:38
     */
    private TraderCustomerVo getComIds(TraderCustomerVo traderCustomerVo){
        if(traderCustomerVo!=null&&traderCustomerVo.getSearchMsg()!=null&&!"".equals(traderCustomerVo.getSearchMsg())){
            // 定义反序列化 数据格式
            final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
            };
            try {
                ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(httpUrl + ErpConst.GET_COMUNICATE_IDS,
                        traderCustomerVo, clientId, clientKey, TypeRef);
                if (result == null || result.getCode() == -1) {
                    return traderCustomerVo;
                }
                List<Integer> cmIds=(List<Integer>) result.getData();
                if(cmIds != null && cmIds.size() > 0){
                    List<Integer> traderIdList = traderCustomerVo.getTraderList();
                    Map<String, Object> map = new HashMap<>();
                    map.put("companyId", traderCustomerVo.getCompanyId());
                    map.put("traderType", 1);
                    map.put("comIdList", cmIds);
                    List<Integer> traderIds = communicateRecordMapper.getTraderIdListByList(map);
                    if(traderIdList!=null&&traderIdList.size()>0){
                        traderIdList.addAll(traderIds);
                    }else{
                        traderIdList=new ArrayList<>();
                        traderIdList.addAll(traderIds);
                    }
                    traderCustomerVo.setTraderList(traderIdList);
                }
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }
        return traderCustomerVo;
    }




    /**
     * <b>Description:</b><br>
     * 是否置顶
     *
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年5月17日 上午10:41:36
     */
    @SuppressWarnings("rawtypes")
    @Override
    public ResultInfo isStick(TraderCustomer traderCustomer, User user) {
        //TraderCustomer ts = new TraderCustomer();
//		ts.setTraderCustomerId(id);
//		ts.setIsTop(isTop);
        traderCustomer.setUpdater(user.getUserId());
        traderCustomer.setModTime(DateUtil.sysTimeMillis());
        return update(traderCustomer, httpUrl + ErpConst.TRADER_CUSTOMER_TOP);
    }

    /**
     * <b>Description:</b><br>
     * 是否禁用客户
     *
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年5月17日 上午10:41:36
     */
    @SuppressWarnings("rawtypes")
    @Override
    public ResultInfo isDisabled(User user, TraderCustomer traderCustomer) {
//		TraderCustomer ts = new TraderCustomer();
//		ts.setTraderCustomerId(id);
//		ts.setIsEnable(isDisabled);
        traderCustomer.setUpdater(user.getUserId());
        traderCustomer.setModTime(System.currentTimeMillis());
        if (traderCustomer.getIsEnable() == 0) {
            traderCustomer.setDisableTime(System.currentTimeMillis());
            //ts.setDisableReason(disabledReason);
        } else {
            //ts.setDisableTime((long) 0);
            //ts.setDisableReason("");
        }
        return update(traderCustomer, httpUrl + ErpConst.TRADER_CUSTOMER_DISABLED);
    }

    /**
     * <b>Description:</b><br>
     * 更新
     *
     * @param url
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年5月17日 上午11:47:29
     */
    @SuppressWarnings("rawtypes")
    public ResultInfo update(TraderCustomer tc, String url) {
        final TypeReference<ResultInfo<TraderCustomer>> TypeRef = new TypeReference<ResultInfo<TraderCustomer>>() {
        };
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, tc, clientId, clientKey, TypeRef);
            return result;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public TraderCustomer getTraderCustomerEditBaseInfo(TraderCustomer traderCustomer) {
        // 接口调用
        String url = httpUrl + "trader/getcustomereditbaseinfo.htm";

        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<TraderCustomer>> TypeRef2 = new TypeReference<ResultInfo<TraderCustomer>>() {
        };
        try {
            ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, traderCustomer, clientId, clientKey,
                    TypeRef2);
            TraderCustomer res = (TraderCustomer) result2.getData();
            return res;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public TraderCustomer saveEditBaseInfo(Trader trader, HttpServletRequest request, HttpSession session) {
        User user = (User) session.getAttribute(ErpConst.CURR_USER);
        Long time = DateUtil.sysTimeMillis();
        String s = request.getParameter("zone");
        if(request.getParameter("zone")!=null && !"".equals(request.getParameter("zone"))){
            if(Integer.parseInt(request.getParameter("zone")) > 0){
                trader.setAreaId(Integer.parseInt(request.getParameter("zone")));
                trader.setAreaIds(request.getParameter("province")+","+request.getParameter("city")+","+request.getParameter("zone"));
            }else{
                trader.setAreaId(Integer.parseInt(request.getParameter("city")));
                trader.setAreaIds(request.getParameter("province")+","+request.getParameter("city")+","+request.getParameter("zone"));
            }
        }
        trader.setCompanyId(user.getCompanyId());

        trader.setModTime(time);
        trader.setUpdater(user.getUserId());

        // 客户
        TraderCustomer traderCustomer = trader.getTraderCustomer();

        if(request.getParameter("traderCustomer.registeredCapital") != null && request.getParameter("traderCustomer.registeredCapital").equals("")){
            traderCustomer.setRegisteredCapital("0");
        }
        //冗余字段 客户类型 客户性质
        if(null != request.getParameter("traderCustomerCategoryIds")){
            String ids = request.getParameter("traderCustomerCategoryIds");
            String[] idsArr = ids.split(",");
            if(idsArr != null && idsArr.length > 0 && null != idsArr[0]){
                if(idsArr[0].equals("1")){
                    traderCustomer.setCustomerType(SysOptionConstant.ID_426);
                }
                if(idsArr[0].equals("2")){
                    traderCustomer.setCustomerType(SysOptionConstant.ID_427);
                }
            }
            if(idsArr != null && idsArr.length > 1 && null != idsArr[1]){
                if(idsArr[1].equals("3") || idsArr[1].equals("5")){
                    traderCustomer.setCustomerNature(SysOptionConstant.ID_465);
                }
                if(idsArr[1].equals("4") || idsArr[1].equals("6")){
                    traderCustomer.setCustomerNature(SysOptionConstant.ID_466);
                }
            }
        }
        if (traderCustomer.getRegisteredDateStr() != "") {
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                traderCustomer.setRegisteredDate(dateformat.parse(traderCustomer.getRegisteredDateStr()));
            } catch (ParseException e) {
                return null;
            }
        }

        traderCustomer.setModTime(time);
        traderCustomer.setUpdater(user.getUserId());

        // 客户代理品牌
        if (null != request.getParameterValues("agentBrandId")) {
            String[] agentBrandIds = request.getParameterValues("agentBrandId");
            List<TraderCustomerAgentBrand> agentBrands = new ArrayList<>();
            for (String brandId : agentBrandIds) {
                TraderCustomerAgentBrand traderCustomerAgentBrand = new TraderCustomerAgentBrand();
                traderCustomerAgentBrand.setBrandId(Integer.parseInt(brandId));
                agentBrands.add(traderCustomerAgentBrand);
            }

            traderCustomer.setTraderCustomerAgentBrands(agentBrands);
        }

        //基础医疗经销商
        Integer basicMedicalDealer = 0;
        String lcfx = null;
        if(null != request.getParameter("isLcfx")){
            lcfx = request.getParameter("isLcfx");
        }
        // 客户属性
        if (null != request.getParameterValues("attributeId")) {
            String[] attributeIds = request.getParameterValues("attributeId");
            List<TraderCustomerAttribute> customerAttributes = new ArrayList<>();
            for (String attIds : attributeIds) {
                TraderCustomerAttribute customerAttribute = new TraderCustomerAttribute();
                String[] attArr = attIds.split("_");

                customerAttribute.setAttributeCategoryId(Integer.parseInt(attArr[0]));
                customerAttribute.setAttributeId(Integer.parseInt(attArr[1]));

                String pName = "attributedesc_" + attIds;
                if (null != request.getParameter(pName)) {
                    customerAttribute.setAttributeOther((String) request.getParameter(pName));
                }

                customerAttributes.add(customerAttribute);

                //基础医疗经销商
                if(lcfx!= null && lcfx.equals("1")
                        && (Integer.parseInt(attArr[1]) == 95
                        || Integer.parseInt(attArr[1]) == 96
                        || Integer.parseInt(attArr[1]) == 97
                        || Integer.parseInt(attArr[1]) == 99
                        || Integer.parseInt(attArr[1]) == 100)){
                    basicMedicalDealer = 1;
                }
            }

            traderCustomer.setTraderCustomerAttributes(customerAttributes);

        }

        traderCustomer.setBasicMedicalDealer(basicMedicalDealer);

        // 客户经营区域
        if (null != request.getParameterValues("bussinessAreaId")) {
            String[] bussinessAreaIds = request.getParameterValues("bussinessAreaId");
            String[] bussinessAreaIdsStr = request.getParameterValues("bussinessAreaIds");
            List<TraderCustomerBussinessArea> bussinessAreas = new ArrayList<>();
            for (Integer i = 0; i < bussinessAreaIds.length; i++) {
                TraderCustomerBussinessArea traderCustomerBussinessArea = new TraderCustomerBussinessArea();
                traderCustomerBussinessArea.setAreaId(Integer.parseInt(bussinessAreaIds[i]));
                traderCustomerBussinessArea.setAreaIds(bussinessAreaIdsStr[i]);
                bussinessAreas.add(traderCustomerBussinessArea);
            }

            traderCustomer.setTraderCustomerBussinessAreas(bussinessAreas);
        }

        // 客户经营品牌
        if (null != request.getParameterValues("bussinessBrandId")) {
            String[] bussinessBrandIds = request.getParameterValues("bussinessBrandId");
            List<TraderCustomerBussinessBrand> bussinessBrands = new ArrayList<>();
            for (String brandId : bussinessBrandIds) {
                TraderCustomerBussinessBrand traderCustomerBussinessBrand = new TraderCustomerBussinessBrand();
                traderCustomerBussinessBrand.setBrandId(Integer.parseInt(brandId));
                bussinessBrands.add(traderCustomerBussinessBrand);
            }

            traderCustomer.setTraderCustomerBussinessBrands(bussinessBrands);
        }
        // 财务信息
        TraderFinance traderFinance = trader.getTraderFinance();
        if(traderFinance!=null){
            traderFinance.setTraderType(1);
            traderFinance.setAddTime(time);
            traderFinance.setCreator(user.getUserId());
            traderFinance.setModTime(time);
            traderFinance.setUpdater(user.getUserId());
        }

        // 接口调用
        String url = httpUrl + "trader/saveeditbaseinfo.htm";

        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<TraderCustomer>> TypeRef2 = new TypeReference<ResultInfo<TraderCustomer>>() {
        };
        try {
            ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, trader, clientId, clientKey, TypeRef2);
            TraderCustomer res = (TraderCustomer) result2.getData();
            //更新贝登商城会员
            if(result2 != null &&result2.getCode().equals(0)){
                updateVedengMember();
            }
            return res;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public Trader getTraderByTraderName(Trader trader, HttpSession session) {
        User user = (User) session.getAttribute(ErpConst.CURR_USER);
        trader.setCompanyId(user.getCompanyId());

        // 接口调用
        String url = httpUrl + "trader/gettraderbytradername.htm";

        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<Trader>> TypeRef2 = new TypeReference<ResultInfo<Trader>>() {
        };
        try {
            ResultInfo<Trader> result2 = (ResultInfo<Trader>) HttpClientUtils.post(url, trader, clientId, clientKey, TypeRef2);
            if (null == result2) {
                return null;
            }

            Trader res = (Trader) result2.getData();

            return res;
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            return null;
        }
    }

    /**
     * <b>Description:</b><br>
     * 获取当前客户的联系人
     *
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年5月23日 上午10:00:55
     */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> getTraderContactVoList(TraderContactVo traderContactVo) {
        final TypeReference<ResultInfo<Map<String, Object>>> TypeRef = new TypeReference<ResultInfo<Map<String, Object>>>() {};
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(httpUrl + ErpConst.TRADER_CONTACTS_ADDRESS, traderContactVo,
                    clientId, clientKey, TypeRef);
            Map<String, Object> map = (Map<String, Object>) result.getData();
            String tastr = (String) map.get("address");
            if(!"[]".equals(tastr)){
                net.sf.json.JSONArray json = net.sf.json.JSONArray.fromObject(tastr);
                List<TraderAddress> list = (List<TraderAddress>) json.toCollection(json, TraderAddress.class);
                if (null != list && list.size() > 0) {
                    List<TraderAddressVo> tavList = new ArrayList<>();
                    TraderAddressVo tav = null;
                    for (TraderAddress ta : list) {
                        if (ta == null || ta.getAreaId() == null) {
                            continue;
                        }
                        tav = new TraderAddressVo();
                        tav.setTraderAddress(ta);
                        if(ta.getAreaId() > 0){
                            tav.setArea(getAddressByAreaId(ta.getAreaId()));
                        }
                        tavList.add(tav);
                    }
                    map.put("address", tavList);
                }
            }else{
                List<TraderAddressVo> tavList = new ArrayList<>();
                map.put("address", tavList);
            }
            return map;
        } catch (IOException e) {
            throw new RuntimeException();
        }

    }

    /**
     * <b>Description:</b><br>
     * 保存联系人
     *
     * @param traderContact
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年5月23日 下午3:56:30
     */
    @Override
    public Integer saveTraderContact(TraderContact traderContact, User user) {
        if (null == traderContact.getTraderContactId()) {
            traderContact.setAddTime(System.currentTimeMillis());
            traderContact.setCreator(user.getUserId());
            traderContact.setModTime(System.currentTimeMillis());
            traderContact.setUpdater(user.getUserId());
            traderContact.setIsOnJob(1);
            traderContact.setIsDefault(0);
            traderContact.setIsEnable(1);
            if(traderContact.getSex()==null){
                traderContact.setSex(2);
            }
        } else {
            traderContact.setUpdater(user.getUserId());
            traderContact.setModTime(System.currentTimeMillis());
        }
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
        };
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(httpUrl + ErpConst.TRADER_CONTACTS_SAVE,
                    traderContact, clientId, clientKey, TypeRef);
            if(result ==null){
                return 0;
            }else if(result!=null && result.getCode() ==1){
                return -1;
            }
            return Integer.valueOf(result.getData().toString()) ;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * <b>Description:</b><br>
     * 保存地址
     *
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年5月23日 下午3:56:30
     */
    @Override
    public ResultInfo saveTraderAddress(TraderAddress traderAddress, User user) {
        if (null == traderAddress.getTraderAddressId()) {
            traderAddress.setAddTime(System.currentTimeMillis());
            traderAddress.setCreator(user.getUserId());
            traderAddress.setModTime(System.currentTimeMillis());
            traderAddress.setUpdater(user.getUserId());
            traderAddress.setIsDefault(0);
            traderAddress.setIsEnable(1);
        } else {
            traderAddress.setUpdater(user.getUserId());
            traderAddress.setModTime(System.currentTimeMillis());
        }
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
        };
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(httpUrl + ErpConst.TRADER_ADDRESS_SAVE,
                    traderAddress, clientId, clientKey, TypeRef);
            return result;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * <b>Description:</b><br>
     * 获取联系人
     *
     * @param traderContactId
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年5月24日 下午2:28:20
     */
    @Override
    public TraderContact getTraderContactById(Integer traderContactId) {
        TraderContact traderContact = new TraderContact();
        traderContact.setTraderContactId(traderContactId);
        final TypeReference<ResultInfo<TraderContact>> TypeRef = new TypeReference<ResultInfo<TraderContact>>() {
        };
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(httpUrl + ErpConst.TRADER_CONTACTS_QUERY,
                    traderContact, clientId, clientKey, TypeRef);
            TraderContact tc = (TraderContact) result.getData();
            return tc;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
    /**
     * <b>Description:</b><br>
     * 转移联系人
     *
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年5月17日 上午10:41:36
     */
    @Override
    public ResultInfo transferContact(TraderContact traderContact, User user) {
        traderContact.setUpdater(user.getUserId());
        traderContact.setModTime(System.currentTimeMillis());
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
        };
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(httpUrl + ErpConst.SAVE_TRANSFER_CONTACTS,
                    traderContact, clientId, clientKey, TypeRef);
            return result;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
    /**
     * <b>Description:</b><br>
     * 是否禁用联系人
     *
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年5月17日 上午10:41:36
     */
    @Override
    public ResultInfo isDisabledContact(TraderContact traderContact, User user) {
        traderContact.setUpdater(user.getUserId());
        traderContact.setModTime(System.currentTimeMillis());
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
        };
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(httpUrl + ErpConst.SAVE_DISABLED_CONTACTS,
                    traderContact, clientId, clientKey, TypeRef);
            return result;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
    /**
     * <b>Description:</b><br>
     * 设置默认联系人
     *
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年5月17日 上午10:41:36
     */
    @Override
    public ResultInfo isDefaultContact(TraderContact traderContact, User user) {
        traderContact.setUpdater(user.getUserId());
        traderContact.setModTime(System.currentTimeMillis());
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
        };
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(httpUrl + ErpConst.SAVE_DEFAULT_CONTACTS,
                    traderContact, clientId, clientKey, TypeRef);
            return result;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * <b>Description:</b><br>
     * 是否禁用地址
     *
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年5月17日 上午10:41:36
     */
    @Override
    public ResultInfo isDisabledAddress(TraderAddress traderAddresst, User user) {
        traderAddresst.setUpdater(user.getUserId());
        traderAddresst.setModTime(System.currentTimeMillis());
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
        };
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(httpUrl + ErpConst.SAVE_DISABLED_ADDRESS,
                    traderAddresst, clientId, clientKey, TypeRef);
            return result;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * <b>Description:</b><br>
     * 设置默认地址
     *
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年5月17日 上午10:41:36
     */
    @Override
    public ResultInfo isDefaultAddress(TraderAddress traderAddress, User user) {
        traderAddress.setUpdater(user.getUserId());
        traderAddress.setModTime(System.currentTimeMillis());
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
        };
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(httpUrl + ErpConst.SAVE_DEFAULT_ADDRESS,
                    traderAddress, clientId, clientKey, TypeRef);
            return result;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<CommunicateRecord> getCommunicateRecordListPage(CommunicateRecord communicateRecord, Page page) {
		/*//客户沟通记录
		if (communicateRecord.getTraderCustomerId() != null && communicateRecord.getTraderCustomerId() > 0) {
			List<Integer> traderCustomerIdList = new ArrayList<>();
			traderCustomerIdList.add(communicateRecord.getTraderCustomerId());
			communicateRecord.setTraderCustomerIds(traderCustomerIdList);
		}
		//商机沟通记录
		if(null != communicateRecord.getBussinessChanceId() && communicateRecord.getBussinessChanceId() > 0){
			communicateRecord.setBussinessChanceId(communicateRecord.getBussinessChanceId());
		}
		//报价
		if(null != communicateRecord.getQuoteorderId() && communicateRecord.getQuoteorderId() > 0){
			communicateRecord.setQuoteorderId(communicateRecord.getQuoteorderId());
		}
		//订单
		if(null != communicateRecord.getSaleorderId() && communicateRecord.getSaleorderId() > 0){
			communicateRecord.setQuoteorderId(communicateRecord.getSaleorderId());
		}*/

        // 后期调用接口查询询价、报价、订单、售后 沟通记录
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("communicateRecord", communicateRecord);
        map.put("page", page);
        List<CommunicateRecord> communicateRecordList = communicateRecordMapper.getCommunicateRecordList(map);

        // 调用接口补充信息（联系人，沟通目的、方式 ，沟通内容（标签））、商机、报价、订单
        String url = httpUrl + "trader/tradercommunicaterecord.htm";
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<List<CommunicateRecord>>> TypeRef2 = new TypeReference<ResultInfo<List<CommunicateRecord>>>() {
        };
        try {
            ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, communicateRecordList, clientId, clientKey, TypeRef2);


            List<Integer> relatedList = new ArrayList<>();
            List<CommunicateRecord> list = (List<CommunicateRecord>) result2.getData();
            for (CommunicateRecord newcom : list) {
                for(CommunicateRecord oldcom:communicateRecordList){
                    if(newcom.getCommunicateRecordId().equals(oldcom.getCommunicateRecordId())  ){
                        if(StringUtil.isNotBlank(oldcom.getContact()) && StringUtil.isNotBlank(oldcom.getContactMob())){
                            newcom.setContactName(oldcom.getContact());
                            newcom.setPhone(oldcom.getContactMob());
                            newcom.setContactContent(oldcom.getContactContent());
                        }
                        break;
                    }
                }
                relatedList.add(newcom.getCommunicateRecordId());


                /*//判断是否转译完成
                PhoneticWriting phoneticWriting = phoneticWritingMapper.getPhoneticWriting(newcom.getCommunicateRecordId());
                if(phoneticWriting != null){
                    if(StringUtils.isNotBlank(phoneticWriting.getOriginalContent())){
                        newcom.setIsTranslation(1);
                    }else {
                        newcom.setIsTranslation(0);
                    }
                }else{
                    newcom.setIsTranslation(0);
                }*/
            }

            if(relatedList != null && relatedList.size() > 0){
                List<PhoneticWriting> phoneticWritingList = phoneticWritingMapper.getPhoneticWritingList(relatedList);
                if(phoneticWritingList != null && phoneticWritingList.size() > 0){
                    for(int i=0;i<list.size();i++){
                        for(int j=0;j<phoneticWritingList.size();j++){
                            if(list.get(i).getCommunicateRecordId().equals(phoneticWritingList.get(j).getRelatedId())){
                                if(phoneticWritingList.get(j) != null){
                                    if(StringUtils.isNotBlank(phoneticWritingList.get(j).getOriginalContent())){
                                        list.get(i).setIsTranslation(1);
                                    }else {
                                        list.get(i).setIsTranslation(0);
                                    }
                                }else{
                                    list.get(i).setIsTranslation(0);
                                }
                            }
                        }
                    }
                }
            }
            return list;
        } catch (IOException e) {
            return null;
        }

    }

    @Override
    public List<TraderContact> getTraderContact(TraderContact traderContact) {
        String url = httpUrl + "trader/gettradercontact.htm";
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<List<TraderContact>>> TypeRef2 = new TypeReference<ResultInfo<List<TraderContact>>>() {
        };
        try {
            ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, traderContact, clientId, clientKey,
                    TypeRef2);

            List<TraderContact> list = (List<TraderContact>) result2.getData();
            return list;
        } catch (IOException e) {
            return null;
        }
    }
    @Override
    public TraderAddress getTraderAddress(TraderAddress traderAddress) {
        String url = httpUrl + ErpConst.TRADER_ADDRESS_QUERY;
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<TraderAddress>> TypeRef2 = new TypeReference<ResultInfo<TraderAddress>>() {
        };
        try {
            ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, traderAddress, clientId, clientKey,
                    TypeRef2);

            TraderAddress ta = (TraderAddress) result2.getData();
            return ta;
        } catch (IOException e) {
            return null;
        }
    }

    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public Boolean saveAddCommunicate(CommunicateRecord communicateRecord, HttpServletRequest request,
                                      HttpSession session) throws Exception {
        User user = (User) session.getAttribute(ErpConst.CURR_USER);
        Long time = DateUtil.sysTimeMillis();

        communicateRecord.setCompanyId(user.getCompanyId());
        String begin = request.getParameter("begin");
        String end = request.getParameter("end");
        if(StringUtils.isNotBlank(end)){
            communicateRecord.setEndtime(DateUtil.convertLong(end, "yyyy-MM-dd HH:mm:ss"));
        }
        communicateRecord.setBegintime(DateUtil.convertLong(begin, "yyyy-MM-dd HH:mm:ss"));

        if (request.getParameter("nextDate") != "") {
            communicateRecord.setNextContactDate(request.getParameter("nextDate"));
            communicateRecord.setIsDone(ErpConst.ZERO);
        }

        communicateRecord.setTraderType(ErpConst.ONE);
        communicateRecord.setAddTime(time);
        communicateRecord.setCreator(user.getUserId());
        communicateRecord.setModTime(time);
        communicateRecord.setUpdater(user.getUserId());
        communicateRecord.setContactContent(request.getParameter("content"));

        if (null != request.getParameterValues("tagId")) {
            communicateRecord.setContactContent("1");
        }
        if (null != request.getParameterValues("tagName")) {
            communicateRecord.setContactContent("1");
        }

        communicateRecord.setContact(request.getParameter("name"));
        communicateRecord.setContactMob(request.getParameter("telephone"));

        //历史沟通信息处理
        CommunicateRecord old = new CommunicateRecord();

        old.setCommunicateType(communicateRecord.getCommunicateType());
        old.setRelatedId(communicateRecord.getRelatedId());
        communicateService.updateCommunicateDone(communicateRecord, session);

        communicateRecordMapper.insert(communicateRecord);
        Integer communicateRecordId = communicateRecord.getCommunicateRecordId();

        //更新对应商机的备注信息
		/*if(communicateRecord.getRelatedId()!=null && org.apache.commons.lang.StringUtils.isNotBlank(communicateRecord.getContactContent())){
			BussinessChance bussinessChance = new BussinessChance();
			bussinessChance.setBussinessChanceId(communicateRecord.getRelatedId());
			bussinessChance.setComments(communicateRecord.getContactContent());
			bussinessChanceMapper.updateByPrimaryKeySelective(bussinessChance);
		}*/

        if (communicateRecordId > 0) {
            // 标签
            if (null != request.getParameterValues("tagId")) {// 标签库标签
                String[] tagIds = request.getParameterValues("tagId");
                List<Tag> tagList = new ArrayList<>();
                for (String tagId : tagIds) {
                    Tag tag = new Tag();
                    tag.setTagType(SysOptionConstant.ID_32);
                    tag.setTagId(Integer.parseInt(tagId));
                    tag.setCompanyId(user.getCompanyId());
                    tagList.add(tag);
                }

                communicateRecord.setTag(tagList);
            }
            if (null != request.getParameterValues("tagName")) {// 自定义标签
                String[] tagNames = request.getParameterValues("tagName");
                List<String> tagNameList = new ArrayList<>();
                for (String tagName : tagNames) {
                    tagNameList.add(tagName);
                }

                communicateRecord.setTagName(tagNameList);
            }
            if (StringUtils.isBlank(request.getParameter("content"))) {
                // 接口调用
                String url = httpUrl + "trader/saveaddcommunicatetag.htm";

                // 定义反序列化 数据格式
                final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {
                };
                try {
                    ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, communicateRecord, clientId,
                            clientKey, TypeRef2);
                    Integer res = (Integer) result2.getCode();

                    if (res.equals(0)) {
                        return true;
                    }
                    return false;
                } catch (IOException e) {
                    return false;
                }
            }

        }
        return true;
    }

    @Override
    public CommunicateRecord getCommunicate(CommunicateRecord communicateRecord) {
        CommunicateRecord communicate = communicateRecordMapper.getCommunicate(communicateRecord);
        // 接口调用
        String url = httpUrl + "trader/tradercommunicateinfo.htm";

        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<CommunicateRecord>> TypeRef2 = new TypeReference<ResultInfo<CommunicateRecord>>() {
        };
        try {
            ResultInfo<CommunicateRecord> result2 = (ResultInfo<CommunicateRecord>) HttpClientUtils.post(url,
                    communicate, clientId, clientKey, TypeRef2);
            CommunicateRecord res1 = (CommunicateRecord) result2.getData();
            CommunicateRecord res = new CommunicateRecord();
            res = communicateRecordMapper.getCommunicate(res1);
            res.setTag(res1.getTag());
            return res;
        } catch (IOException e) {
            return null;
        }
    }
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public Boolean saveEditCommunicate(CommunicateRecord communicateRecord, HttpServletRequest request,
                                       HttpSession session) throws Exception {
        User user = (User) session.getAttribute(ErpConst.CURR_USER);
        Long time = DateUtil.sysTimeMillis();

        communicateRecord.setCompanyId(user.getCompanyId());
        String begin = request.getParameter("begin");
        String end = request.getParameter("end");
        communicateRecord.setBegintime(DateUtil.convertLong(begin, "yyyy-MM-dd HH:mm:ss"));
        if(StringUtils.isNotBlank(end)){
            communicateRecord.setEndtime(DateUtil.convertLong(end, "yyyy-MM-dd HH:mm:ss"));
        }

        if (request.getParameter("nextDate") != "") {
            communicateRecord.setNextContactDate(request.getParameter("nextDate"));
        }

        communicateRecord.setModTime(time);
        communicateRecord.setUpdater(user.getUserId());
        communicateRecord.setContactContent(request.getParameter("content"));

        if (null != request.getParameterValues("tagId")) {
            communicateRecord.setContactContent("1");
        }
        if (null != request.getParameterValues("tagName")) {
            communicateRecord.setContactContent("1");
        }

        Integer succ = 0;
        if(null != communicateRecord.getCoid() && communicateRecord.getCoid() != ""){//呼叫中心编辑沟通记录
            communicateRecord.setCreator(user.getUserId());
            succ = communicateRecordMapper.updateByCoidAUserId(communicateRecord);
        }else{

            succ = communicateRecordMapper.update(communicateRecord);
            //更新对应商机的备注信息
			/*if(communicateRecord.getRelatedId()!=null && org.apache.commons.lang.StringUtils.isNotBlank(communicateRecord.getContactContent())){
				BussinessChance bussinessChance = new BussinessChance();
				bussinessChance.setBussinessChanceId(communicateRecord.getRelatedId());
				bussinessChance.setComments(communicateRecord.getContactContent());
				bussinessChanceMapper.updateByPrimaryKeySelective(bussinessChance);
			}*/
        }
        if (succ > 0) {

            // 标签
            if (null != request.getParameterValues("tagId")) {// 标签库标签
                String[] tagIds = request.getParameterValues("tagId");
                List<Tag> tagList = new ArrayList<>();
                for (String tagId : tagIds) {
                    Tag tag = new Tag();
                    tag.setTagType(SysOptionConstant.ID_32);
                    tag.setTagId(Integer.parseInt(tagId));
                    tag.setCompanyId(user.getCompanyId());
                    tagList.add(tag);
                }

                communicateRecord.setTag(tagList);
            }
            if (null != request.getParameterValues("tagName")) {// 自定义标签
                String[] tagNames = request.getParameterValues("tagName");
                List<String> tagNameList = new ArrayList<>();
                for (String tagName : tagNames) {
                    tagNameList.add(tagName);
                    communicateRecord.setContactContent(tagName+",");
                }

                communicateRecord.setTagName(tagNameList);
            }

            // 接口调用
            String url = httpUrl + "trader/saveaddcommunicatetag.htm";

            // 定义反序列化 数据格式
            final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {};
            try {
                ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, communicateRecord, clientId, clientKey, TypeRef2);
                Integer res = (Integer) result2.getCode();

                if (res.equals(0)) {
                    return true;
                }
                return false;
            } catch (IOException e) {
                return false;
            }
        }
        return false;
    }

    /**
     * <b>Description:</b><br>
     * 获取客户的财务与资质信息
     *
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年5月31日 上午10:27:30
     */
    @Override
    public Map<String, Object> getFinanceAndAptitudeByTraderId(TraderCertificateVo traderCertificateVo, String queryType) {

        // 此处赋值参数仅作为编辑时区分不同查询，1-单独查询资质信息；2-单独查询财务信息；3-申请账期
        if ("all".equals(queryType)) {
            traderCertificateVo.setCreator(0);
        } else if ("zz".equals(queryType)) {// 资质
            traderCertificateVo.setCreator(1);
        } else if ("cw".equals(queryType)) {// 财务
            traderCertificateVo.setCreator(2);
        } else {// 账期
            traderCertificateVo.setCreator(3);
        }
        final TypeReference<ResultInfo<Map<String, Object>>> TypeRef = new TypeReference<ResultInfo<Map<String, Object>>>() {
        };
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(httpUrl + ErpConst.NEW_TRADER_FINANCE_APTITUDE, traderCertificateVo, clientId, clientKey, TypeRef);
            if(result == null || result.getCode() == -1){
                return null;
            }
            Map<String, Object> map = (Map<String, Object>) result.getData();
            //帐期列表
            if(map != null && map.containsKey("billList")){
                JSONArray jsonArray=JSONArray.fromObject(map.get("billList"));
                List<TraderAccountPeriodApply> billList =(List<TraderAccountPeriodApply>) JSONArray.toCollection(jsonArray, TraderAccountPeriodApply.class);
                if(billList != null && billList.size() > 0){
                    for (TraderAccountPeriodApply tb : billList) {
                        tb.setCreatorNm(getUserNameByUserId(tb.getCreator()));
                    }
                    map.put("billList", billList);
                }
            }

            return map;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
    /**
     * <b>Description:</b><br> 保存资质
     * @param tcvList
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年6月5日 下午4:58:30
     */
    @Override
    public ResultInfo saveMedicaAptitude(TraderVo traderVo,List<TraderCertificateVo> tcvList,
                                         List<TraderMedicalCategory> twomcList,List<TraderMedicalCategory> threemcList) {
        Map<String, Object> map=new HashMap<>();
        for (TraderCertificateVo tcv : tcvList) {
            if(StringUtil.isNotBlank(tcv.getUri())&&tcv.getUri().startsWith("/file/display")){
                tcv.setDomain("file.vedeng.com");
            }else{
                tcv.setDomain(picUrl);
            }
        }
        map.put("traderVo", traderVo);
        map.put("tcv", tcvList);
        map.put("two", twomcList);
        map.put("three", threemcList);
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(httpUrl + ErpConst.SAVE_APTITUDE,
                    map, clientId, clientKey, TypeRef);
            return result;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public ResultInfo<?> saveNewMedicaAptitude(TraderVo traderVo, List<TraderCertificateVo> tcvList, List<TraderMedicalCategory> twomcList, List<TraderMedicalCategory> threemcList, List<TraderMedicalCategory> newTwomcList, List<TraderMedicalCategory> newThreemcList) {
        Map<String, Object> map=new HashMap<>();
        for (TraderCertificateVo tcv : tcvList) {
            if(tcv.getUri().startsWith("/file/display")){
                tcv.setDomain("file.vedeng.com");
            }else{
                tcv.setDomain(picUrl);
            }
        }
        map.put("traderVo", traderVo);
        map.put("tcv", tcvList);
        map.put("two", twomcList);
        map.put("three", threemcList);
        map.put("newTwo",newTwomcList);
        map.put("newThree",newThreemcList);
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(httpUrl + ErpConst.SAVE_APTITUDE,
                    map, clientId, clientKey, TypeRef);
            return result;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Map<String, Object> getUserTraderByTraderNameListPage(RTraderJUser rTraderJUser, Page page) {
        List<RTraderJUser> rTraderJUserList = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        try {

            List<TraderCustomer> traderList = null;
            Trader trader = new Trader();
            trader.setCompanyId(rTraderJUser.getCompanyId());
            trader.setTraderName(rTraderJUser.getTraderName());

            // 定义反序列化 数据格式
            final TypeReference<ResultInfo<List<TraderCustomer>>> TypeRef = new TypeReference<ResultInfo<List<TraderCustomer>>>() {};
            String url=httpUrl + "tradercustomer/getusercustomerbytradernamelistpage.htm";
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, trader,clientId,clientKey, TypeRef,page);
            traderList = (List<TraderCustomer>) result.getData();
            page = result.getPage();

            if(traderList.size() > 0){
                for(TraderCustomer t : traderList){
                    RTraderJUser traderJUser = new RTraderJUser();
                    traderJUser.setTraderId(t.getTraderId());
                    traderJUser.setTraderName(t.getTrader().getTraderName());
                    traderJUser.setChangeTimes(t.getBuyCount());
                    traderJUser.setLevel(t.getCustomerLevelStr());

                    String region = (String) regionService.getRegion(t.getTrader().getAreaId(), 2);
                    if(null != region){
                        traderJUser.setAreaStr(region);
                    }

                    User sale = userMapper.getUserByTraderId(t.getTraderId(), ErpConst.ONE);
                    if(null != sale){
                        traderJUser.setOwnerUser(sale.getUsername());
                    }

                    rTraderJUserList.add(traderJUser);
                }
            }

            map.put("list", rTraderJUserList);map.put("page", page);
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
        }
        return map;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Boolean assignSingleCustomer(Integer traderId, Integer single_to_user,Integer companyId) {
       Integer belongPlatform=userService.getBelongPlatformOfUser(single_to_user,companyId);
       // Integer belongPlatform=userIdPlatFromId(single_to_user,ErpConst.ONE);
        RTraderJUser rTraderJUser = new RTraderJUser();
        rTraderJUser.setTraderType(ErpConst.ONE);
        rTraderJUser.setTraderId(traderId);
        rTraderJUserMapper.deleteRTraderJUser(rTraderJUser);
        RTraderJUser traderJUser = new RTraderJUser();
        traderJUser.setTraderType(ErpConst.ONE);
        traderJUser.setTraderId(traderId);
        traderJUser.setUserId(single_to_user);
        int insert = rTraderJUserMapper.insert(traderJUser);
        Trader trader=new Trader();
        trader.setTraderId(traderId);
        trader.setBelongPlatform(belongPlatform);
        traderMapper.updateTrader(trader);
        if(insert > 0){
            WebAccountVo webAccountVo = new WebAccountVo();
            webAccountVo.setUserId(single_to_user);
            webAccountVo.setTraderId(traderId);
            webAccountVo.setBelongPlatform(belongPlatform);
            webAccountVo.setModTime(new Date());
            int i = webAccountMapper.updateErpUserId(webAccountVo);
            SendMessageTreader(null,single_to_user,traderId);
            return true;
        }
        return false;
    }




    /**
    * @Description: 归属平台Id
    * @Param:
    * @return:
    * @Author: addis
    * @Date: 2020/3/2
    */
    @Override
    public Integer userIdPlatFromId(Integer userId,Integer companyId){
       List<Position> positionList= userMapper.userPositionOrganization(userId,companyId);
       if(positionList!=null && positionList.size()>0){
           Map<String,Integer> orgId=new HashMap<>();
           orgId.put("B2B事业部",b2bbusinessDivisionId);
           orgId.put("医械购诊所业务部",YiXiePurchaseId);
           orgId.put("科研购业务部",scientificResearchTrainingId);
           Integer orgIdTwo=positionList.get(0).getOrgId();

           if(orgIdTwo.equals(b2bbusinessDivisionId)){
               return 1;//贝登医疗
           }else if(orgIdTwo.equals(YiXiePurchaseId)){
               return 2;//医械购
           }else if(orgIdTwo.equals(scientificResearchTrainingId)){
               return 3;//科研购
           }
           Organization organization=new Organization();
           organization.setOrgId(orgIdTwo);
           Map<String,Integer> orgMap=childOrganization(organization,orgId);
           if(orgMap==null){
               return 5;
           }
           for(Map.Entry<String,Integer> map :orgMap.entrySet()){
               if("B2B事业部".equals(map.getKey())){
                   return 1;//贝登医疗
               }else if ("医械购诊所业务部".equals(map.getKey())){
                   return 2;//医械购
               }else if("科研购业务部".equals(map.getKey())){
                   return 3;//科研购
               }else{
                   return 5;//其他
               }
           }
       }
        return 5;
    }


    @Override
    public Boolean assignBatchCustomer(Integer companyId,Integer from_user, Integer batch_to_user,Integer areaId,String areaIds) {
        Trader trader = new Trader();
        trader.setCompanyId(companyId);
        trader.setAreaId(areaId);
        //trader.setAreaIds(areaIds);

        List<Integer> rTraderJUserIds = new ArrayList<>();
        List<Integer> traderIdList = new ArrayList<>();

        Integer belongPlatform = userService.getBelongPlatformOfUser(batch_to_user,companyId);

        try {
            // 定义反序列化 数据格式
            final TypeReference<ResultInfo<List<TraderCustomer>>> TypeRef = new TypeReference<ResultInfo<List<TraderCustomer>>>() {};
            String url=httpUrl + "tradercustomer/getusercustomernum.htm";
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, trader,clientId,clientKey, TypeRef);
            List<TraderCustomer> list = (List<TraderCustomer>) result.getData();
            if(null == list || list.size() == 0){
                return false;
            }

            List<Integer> traderIds = new ArrayList<>();

            for(TraderCustomer t:list){
                traderIds.add(t.getTraderId());
            }

            RTraderJUser traderJUser = new RTraderJUser();
            traderJUser.setTraderType(ErpConst.ONE);
            traderJUser.setTraderIds(traderIds);
            traderJUser.setUserId(from_user);

            List<RTraderJUser> traderList = rTraderJUserMapper.getUserTrader(traderJUser);
            if(traderList.size() == 0){
                return false;
            }

            for(RTraderJUser r : traderList){
                trader.setTraderId(r.getTraderId());
                trader.setBelongPlatform(belongPlatform);
                traderMapper.updateTrader(trader);
                rTraderJUserIds.add(r.getrTraderJUserId());
                traderIdList.add(r.getTraderId());
            }
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
        }
        int update = rTraderJUserMapper.updateByKey(batch_to_user, rTraderJUserIds);


        if(update > 0){
            for(Integer traderId: traderIdList){
                WebAccountVo webAccountVo = new WebAccountVo();
                webAccountVo.setUserId(batch_to_user);
                webAccountVo.setTraderId(traderId);
                webAccountVo.setBelongPlatform(belongPlatform);
                webAccountVo.setModTime(new Date());
                webAccountMapper.updateErpUserId(webAccountVo);
                //更新贝登会员
                updateVedengMember();
            }
          SendMessageTreader(rTraderJUserIds,batch_to_user,null);
            return true;
        }
        return false;
    }

    @Override
    public List<RTraderJUser> getUserCustomerNum(RTraderJUser rTraderJUser,Integer userId) {
        Trader trader = new Trader();
        trader.setCompanyId(rTraderJUser.getCompanyId());
        trader.setAreaId(rTraderJUser.getAreaId());
        trader.setAreaIds(rTraderJUser.getAreaIds());

        List<RTraderJUser> traderList = null;
        try {
            // 定义反序列化 数据格式
            final TypeReference<ResultInfo<List<TraderCustomer>>> TypeRef = new TypeReference<ResultInfo<List<TraderCustomer>>>() {};
            String url=httpUrl + "tradercustomer/getusercustomernum.htm";
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, trader,clientId,clientKey, TypeRef);
            List<TraderCustomer> list = (List<TraderCustomer>) result.getData();
            if(null == list || list.size() == 0){
                return null;
            }
            List<Integer> traderIds = new ArrayList<>();

            for(TraderCustomer t:list){
                traderIds.add(t.getTraderId());
            }

            RTraderJUser traderJUser = new RTraderJUser();
            traderJUser.setTraderType(ErpConst.ONE);
            traderJUser.setTraderIds(traderIds);
            traderJUser.setUserId(userId);

            traderList = rTraderJUserMapper.getUserTrader(traderJUser);

        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
        }
        return traderList;
    }
    /**
     * <b>Description:</b><br> 获取客户财务信息
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年6月7日 下午3:16:24
     */
    @Override
    public TraderFinanceVo getTraderFinanceByTraderId(TraderFinanceVo traderFinance) {
        final TypeReference<ResultInfo<TraderFinanceVo>> TypeRef = new TypeReference<ResultInfo<TraderFinanceVo>>() {
        };
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(httpUrl + ErpConst.GET_CUSTOMER_FINANCE, traderFinance,
                    clientId, clientKey, TypeRef);
            TraderFinanceVo tf = (TraderFinanceVo) result.getData();
            return tf;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
    /**
     * <b>Description:</b><br> 保存客户财务信息
     * @param traderFinance
     * @param user
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年6月7日 下午4:12:18
     */
    @Override
    public ResultInfo saveCustomerFinance(TraderFinance traderFinance, User user) {
        traderFinance.setAverageTaxpayerDomain(picUrl);
        if(traderFinance.getTraderFinanceId()==null||"".equals(traderFinance.getTraderFinanceId())){
            traderFinance.setCreator(user.getUserId());
            traderFinance.setAddTime(System.currentTimeMillis());
            traderFinance.setModTime(System.currentTimeMillis());
            traderFinance.setUpdater(user.getUserId());
        }else{
            traderFinance.setUpdater(user.getUserId());
            traderFinance.setModTime(System.currentTimeMillis());
        }

        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(httpUrl + ErpConst.SAVE_CUSTOMER_FINANCE,
                    traderFinance, clientId, clientKey, TypeRef);
            return result;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public TraderCustomer getTraderCustomerForAccountPeriod(TraderCustomer traderCustomer) {
        // 接口调用
        String url = httpUrl + "trader/gettradercustomerforaccountperiod.htm";

        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<TraderCustomer>> TypeRef2 = new TypeReference<ResultInfo<TraderCustomer>>() {
        };
        try {
            ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, traderCustomer, clientId, clientKey,
                    TypeRef2);
            TraderCustomer res = (TraderCustomer) result2.getData();
            return res;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public TraderAccountPeriodApply getTraderCustomerLastAccountPeriodApply(Integer traderId) {
        TraderAccountPeriodApply accountPeriodApply = new TraderAccountPeriodApply();
        accountPeriodApply.setTraderId(traderId);
        accountPeriodApply.setTraderType(ErpConst.ONE);
        // 接口调用
        String url = httpUrl + "trader/gettraderlastaccountperiodapply.htm";

        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<TraderAccountPeriodApply>> TypeRef2 = new TypeReference<ResultInfo<TraderAccountPeriodApply>>() {
        };
        try {
            ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, accountPeriodApply, clientId, clientKey,
                    TypeRef2);
            TraderAccountPeriodApply res = (TraderAccountPeriodApply) result2.getData();
            return res;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public ResultInfo saveAccountPeriodApply(TraderAccountPeriodApply traderAccountPeriodApply,HttpSession session) {
        User user = (User) session.getAttribute(ErpConst.CURR_USER);
        Long time = DateUtil.sysTimeMillis();

        traderAccountPeriodApply.setAddTime(time);
        traderAccountPeriodApply.setModTime(time);
        traderAccountPeriodApply.setCreator(user.getUserId());
        traderAccountPeriodApply.setUpdater(user.getUserId());

        // 接口调用
        String url = httpUrl + "trader/saveaccountperiodapply.htm";

        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<Integer>> TypeRef2 = new TypeReference<ResultInfo<Integer>>() {
        };
        try {
            ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, traderAccountPeriodApply, clientId, clientKey,
                    TypeRef2);
            //ResultInfo res = (ResultInfo) result2.getData();
            return result2;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public Integer getCustomerCategory(Integer traderCustomerId) {
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {
        };
        try {
            ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(httpUrl+ErpConst.GET_CUSTOMER_CATEGORY, traderCustomerId, clientId, clientKey,
                    TypeRef2);
            if(result2 ==null || result2.getCode() !=0){
                return 0;
            }
            return (Integer) result2.getData();
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public TraderCustomerVo getCustomerBussinessInfo(Integer traderId) {
        // 接口调用
        String url = httpUrl + "tradercustomer/getcustomerbussinessinfo.htm";

        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<TraderCustomerVo>> TypeRef2 = new TypeReference<ResultInfo<TraderCustomerVo>>() {};
        try {
            ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, traderId, clientId, clientKey, TypeRef2);
            TraderCustomerVo res = (TraderCustomerVo) result2.getData();

            if(null != res){
                //数据处理(地区)
                Integer areaId = res.getAreaId();
                if(areaId > 0){
                    String region = (String) regionService.getRegion(areaId, 2);
                    res.setAddress(region);
                }
                //归属销售
                User sale = userMapper.getUserByTraderId(res.getTraderId(), ErpConst.ONE);
                if (null != sale) {
                    res.setOwnerSale(sale.getUsername());
                }
            }

            return res;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public Map<String,Object> getTerminalPageList(TraderCustomerVo vo,Page page) {
        List<TraderCustomerVo> terminalList = null;Map<String,Object> map = new HashMap<>();
        String url = httpUrl + "tradercustomer/getterminalpagelist.htm";
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<List<TraderCustomerVo>>> TypeRef2 = new TypeReference<ResultInfo<List<TraderCustomerVo>>>() {};
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, vo,clientId,clientKey, TypeRef2, page);
            terminalList = (List<TraderCustomerVo>) result.getData();
            page = result.getPage();

            if(!terminalList.isEmpty()){
                for(int i=0;i<terminalList.size();i++){
                    if(terminalList.get(i)!=null){
                        if(terminalList.get(i).getAreaId()!=null){
                            terminalList.get(i).setAddress(regionService.getRegion(terminalList.get(i).getAreaId(), 2).toString());
                        }
                        if(terminalList.get(i).getTraderId()!=null){
                            terminalList.get(i).setPersonal(userMapper.getUserNameByTraderId(terminalList.get(i).getTraderId()));
                        }
                    }
                }
            }
            map.put("terminalList", terminalList);map.put("page", page);
        } catch (IOException e) {
            return map;
        }
        return map;
    }
    /**
     * <b>Description:</b><br> 根据traderId查询客户信息
     * @param traderCustomer
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年7月5日 下午1:40:05
     */
    @Override
    public TraderCustomerVo getTraderCustomerVo(TraderCustomer traderCustomer) {
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {
        };
        try {
            ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(httpUrl+ErpConst.GET_CUSTOMER_INFO, traderCustomer, clientId, clientKey,
                    TypeRef2);
            JSONObject json=JSONObject.fromObject(result2.getData());
            TraderCustomerVo tcv =(TraderCustomerVo) JSONObject.toBean(json, TraderCustomerVo.class);
            tcv.setAddress(getAddressByAreaId(tcv.getAreaId()));
            tcv.setCustomerProperty(getCustomerCategory(tcv.getTraderCustomerId()));
            return tcv;
        } catch (IOException e) {
            return null;
        }
    }
    /**
     * <b>Description:</b><br> 查看联系人的详情包括行业背景
     * @param traderContact
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年7月5日 下午2:49:21
     */
    @Override
    public Map<String, Object> viewTraderContact(TraderContact traderContact) {
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {
        };
        try {
            ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(httpUrl+ErpConst.GET_CONTACT_DETAIL, traderContact, clientId, clientKey,
                    TypeRef2);
            Map<String, Object> map=(Map<String, Object>) result2.getData();
            if(map.containsKey("experience")){
                JSONArray jsonArray=JSONArray.fromObject(map.get("experience"));
                List<TraderContactExperienceVo> tceList=(List<TraderContactExperienceVo>) JSONArray.toCollection(jsonArray, TraderContactExperienceVo.class);
                for (TraderContactExperienceVo traderContactExperienceVo : tceList) {
                    if(traderContactExperienceVo.getAreas()!=null){
                        String [] areaId = traderContactExperienceVo.getAreas().split(",");
                        StringBuffer sb=new StringBuffer();
                        for (String areaid : areaId) {
                            sb.append(getAddressByAreaId(Integer.valueOf(areaid))).append("、");
                            traderContactExperienceVo.setAddress(sb.toString());
                        }
                    }
                }
                map.put("experience", tceList);
            }
            return map;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
    /**
     * <b>Description:</b><br> 保存新增客户联系人的行业背景
     * @param traderContactExperience
     * @param tcebaList
     * @param tcebbList
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年7月6日 下午5:14:59
     */
    @Override
    public ResultInfo saveAddContactExperience(TraderContactExperience traderContactExperience, User user,
                                               List<TraderContactExperienceBussinessArea> tcebaList,
                                               List<TraderContactExperienceBussinessBrand> tcebbList) {
        if(traderContactExperience!=null&&(traderContactExperience.getTraderContactExperienceId()==null||traderContactExperience.getTraderContactExperienceId()==0)){
            traderContactExperience.setAddTime(DateUtil.sysTimeMillis());
            traderContactExperience.setCreator(user.getUserId());
        }
        traderContactExperience.setModTime(DateUtil.sysTimeMillis());
        traderContactExperience.setUpdater(user.getUserId());
        Map<String, Object> map=new HashMap<>();
        map.put("traderContactExperience", traderContactExperience);
        if(tcebaList!=null&&tcebaList.size()>0){
            map.put("tcebaList", tcebaList);
        }
        if(tcebbList!=null&&tcebbList.size()>0){
            map.put("tcebbList", tcebbList);
        }
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {
        };
        try {
            ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(httpUrl+ErpConst.SAVE_CONTACT_EXPERIENCR, map, clientId, clientKey,
                    TypeRef2);

            return result2;
        } catch (IOException e) {
            return null;
        }
    }



    /**
     * <b>Description:</b><br> 联系人背景
     * @param traderContactExperience
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年5月23日 下午3:21:28
     */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> getTraderContactExperience(TraderContactExperience traderContactExperience) {
        String url = httpUrl + ErpConst.TRADER_CONTACTS_EXPERIENCE;
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {
        };
        try {
            ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, traderContactExperience, clientId, clientKey,
                    TypeRef2);



            Map<String, Object> map = (Map<String, Object>) result2.getData();
            if(map.containsKey("tcebaList")){
                List<TraderContactExperienceBussinessAreaVo> tcebaList =new ArrayList<>();
                JSONArray jsonArray = JSONArray.fromObject(map.get("tcebaList"));
                List<TraderContactExperienceBussinessArea> list=
                        (List<TraderContactExperienceBussinessArea>) JSONArray.toCollection(jsonArray, TraderContactExperienceBussinessArea.class);
                TraderContactExperienceBussinessAreaVo tcebav = null;
                for (TraderContactExperienceBussinessArea tceba : list) {
                    tcebav = new TraderContactExperienceBussinessAreaVo();
                    tcebav.setAreaId(tceba.getAreaId());
                    tcebav.setAreaIds(tceba.getAreaIds());
                    tcebav.setTraderContactExperienceBussinessAreaId(tceba.getTraderContactExperienceBussinessAreaId());
                    tcebav.setTraderContactExperienceId(tceba.getTraderContactExperienceId());
                    tcebav.setAddress(getAddressByAreaId(tceba.getAreaId()));
                    tcebaList.add(tcebav);
                }
                map.put("tcebaList", tcebaList);
            }
            return map;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
    /**
     * <b>Description:</b><br> 删除联系人的行业背景
     * @param traderContactExperience
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年7月6日 下午5:14:59
     */
    @Override
    public ResultInfo<?> delContactExperience(TraderContactExperience traderContactExperience) {
        String url = httpUrl + ErpConst.DEL_CONTACTS_EXPERIENCE;
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {
        };
        try {
            ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, traderContactExperience, clientId, clientKey,
                    TypeRef2);
            return result2;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Map<String, Object> searchCustomerPageList(TraderCustomerVo vo, Page page) {
        List<TraderCustomerVo> searchCustomerList = null;Map<String,Object> map = new HashMap<>();
        String url = httpUrl + "tradercustomer/searchcustomerpagelist.htm";
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<List<TraderCustomerVo>>> TypeRef2 = new TypeReference<ResultInfo<List<TraderCustomerVo>>>() {};
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, vo,clientId,clientKey, TypeRef2, page);
            searchCustomerList = (List<TraderCustomerVo>) result.getData();
            page = result.getPage();

            if(!searchCustomerList.isEmpty()){
                for(int i=0;i<searchCustomerList.size();i++){
                    if(searchCustomerList.get(i)!=null){
                        if(searchCustomerList.get(i).getAreaId()!=null && searchCustomerList.get(i).getAreaId() != 0){
                            searchCustomerList.get(i).setAddress(regionService.getRegion(searchCustomerList.get(i).getAreaId(), 2).toString());
                        }
                        if(searchCustomerList.get(i).getTraderId()!=null){
                            searchCustomerList.get(i).setPersonal(userMapper.getUserNameByTraderId(searchCustomerList.get(i).getTraderId()));
                        }
                    }
                }
            }
            map.put("searchCustomerList", searchCustomerList);map.put("page", page);
        } catch (IOException e) {
            return map;
        }
        return map;
    }

    @Override
    public TraderCustomerVo getCustomerInfoByTraderCustomer(TraderCustomer traderCustomer) {
        // 接口调用
        String url = httpUrl + "tradercustomer/getcustomerinfobytradercustomer.htm";

        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<TraderCustomerVo>> TypeRef2 = new TypeReference<ResultInfo<TraderCustomerVo>>() {
        };
        try {
            ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, traderCustomer, clientId, clientKey,
                    TypeRef2);
            TraderCustomerVo res = (TraderCustomerVo) result2.getData();
            return res;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public TraderCustomerVo getTraderCustomerInfo(Integer traderId) {
        // 接口调用
        String url = httpUrl + "tradercustomer/gettradercustomerinfo.htm";

        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<TraderCustomerVo>> TypeRef = new TypeReference<ResultInfo<TraderCustomerVo>>() {};
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, traderId, clientId, clientKey, TypeRef);
            if(result!=null && result.getCode()==0){
                TraderCustomerVo res = (TraderCustomerVo) result.getData();
                if(res!=null){
                    //数据处理(地区)
                    if(null != res.getAreaId() && res.getAreaId() > 0){
                        String region = (String) regionService.getRegion(res.getAreaId(), 2);
                        res.setAddress(region);
                    }
                    //归属销售
                    User sale = userMapper.getUserByTraderId(res.getTraderId(), ErpConst.ONE);
                    if (null != sale) {
                        res.setOwnerSale(sale.getUsername());
                    }
                }
                return res;
            }
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            return null;
        }
        return null;
    }

    @Override
    public List<CommunicateRecord> getCommunicateRecordList(CommunicateRecord communicateRecord) {
        // 后期调用接口查询询价、报价、订单、售后 沟通记录
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("communicateRecord", communicateRecord);
        List<CommunicateRecord> communicateRecordList = communicateRecordMapper.getCommunicateRecordList(map);

        // 调用接口补充信息（联系人，沟通目的、方式 ，沟通内容（标签））、商机、报价、订单
        String url = httpUrl + "trader/tradercommunicaterecord.htm";
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<List<CommunicateRecord>>> TypeRef2 = new TypeReference<ResultInfo<List<CommunicateRecord>>>() {
        };
        try {
            ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, communicateRecordList, clientId,
                    clientKey, TypeRef2);

            List<CommunicateRecord> list = (List<CommunicateRecord>) result2.getData();
            for(int i=0;i<list.size();i++){
                //判断是否转译完成
                PhoneticWriting phoneticWriting = phoneticWritingMapper.getPhoneticWriting(list.get(i).getCommunicateRecordId());
                if(phoneticWriting != null){
                    if(StringUtils.isNotBlank(phoneticWriting.getOriginalContent())){
                        list.get(i).setIsTranslation(1);
                    }else {
                        list.get(i).setIsTranslation(0);
                    }
                }else{
                    list.get(i).setIsTranslation(0);
                }
            }
            return list;
        } catch (IOException e) {
            return null;
        }

    }
    /**
     * <b>Description:</b><br> 查询交易者的账期分页信息
     * @param traderVo
     * @param page
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年11月10日 下午4:13:12
     */
    @Override
    public Map<String, Object> getAmountBillListPage(TraderVo traderVo, Page page) {
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
        };
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(httpUrl + ErpConst.GET_AMOUNT_BILL_PAGE, traderVo, clientId, clientKey, TypeRef, page);
            if(result == null || result.getCode() == -1){
                return null;
            }
            Map<String, Object> map = new HashMap<>();
            JSONArray jsonArray=JSONArray.fromObject( result.getData());
            List<TraderAmountBillVo> billList=(List<TraderAmountBillVo>) JSONArray.toCollection(jsonArray, TraderAmountBillVo.class);
            if(null != billList && billList.size() > 0){
                for(TraderAmountBillVo amountBillVo : billList){
                    amountBillVo.setCreatorUser(getUserNameByUserId(amountBillVo.getCreator()));
                }
            }
            page = result.getPage();
            map.put("page", page);
            map.put("list", billList);
            return map;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
    /**
     * <b>Description:</b><br> 查询交易者的交易流水分页信息
     * @param traderVo
     * @param page
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年11月10日 下午4:13:12
     */
    @Override
    public Map<String, Object> getCapitalBillListPage(TraderVo traderVo, Page page) {
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
        };
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(httpUrl + ErpConst.GET_CAPITAL_BILL_PAGE, traderVo, clientId, clientKey, TypeRef, page);
            if(result == null || result.getCode() == -1){
                return null;
            }
            Map<String, Object> map = new HashMap<>();
            List<TraderAmountBillVo> list = (List<TraderAmountBillVo>) result.getData();
            page = result.getPage();
            map.put("page", page);
            map.put("list", list);
            return map;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public ResultInfo saveUplodeBatchCustomer(List<Trader> list) {
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
        };
        try {
            String url = httpUrl + "trader/saveuplodebatchcustomer.htm";
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, list, clientId, clientKey, TypeRef);
            return result;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public TraderAccountPeriodApply getAccountPeriodDaysApplyInfo(Integer accountPeriodDaysApplyId) {
        // 接口调用
        String url = httpUrl + "trader/getaccountperioddaysapplybyid.htm";

        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<TraderAccountPeriodApply>> TypeRef = new TypeReference<ResultInfo<TraderAccountPeriodApply>>() {};
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, accountPeriodDaysApplyId, clientId, clientKey, TypeRef);
            if(result!=null && result.getCode()==0){
                TraderAccountPeriodApply res = (TraderAccountPeriodApply) result.getData();
                return res;
            }
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            return null;
        }
        return null;
    }

    /**
     * <b>Description:</b><br> 删除供应商的银行帐号
     * @param traderFinance
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年12月21日 下午5:06:45
     */
    @Override
    public ResultInfo<?> delSupplierBank(TraderFinance traderFinance) {
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
        };
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(httpUrl + ErpConst.DEL_SUPPLIER_BANK, traderFinance, clientId, clientKey, TypeRef);
            if(result == null || result.getCode() == -1){
                return null;
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<TraderFinance> getTraderCustomerFinanceList(TraderFinance tf) {
        List<TraderFinance> list = null;
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<List<TraderFinance>>> TypeRef = new TypeReference<ResultInfo<List<TraderFinance>>>() {};
        String url=httpUrl + "tradercustomer/gettradercustomerfinancelist.htm";
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, tf,clientId,clientKey, TypeRef);
            list = (List<TraderFinance>) result.getData();
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
        }
        return list;
    }


    @Override
    public TraderInfoTyc getTycInfo(int type, String traderName) {
        String url = httpUrl + "trader/gettraderinfotycbytradername.htm";
        TraderInfoTyc res = new TraderInfoTyc();
        int dayCnt = 0;
        final TypeReference<ResultInfo<TraderInfoTyc>> TypeRef = new TypeReference<ResultInfo<TraderInfoTyc>>() {};
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, traderName, clientId, clientKey, TypeRef);
            if(result!=null && result.getCode()==0){
                res = (TraderInfoTyc) result.getData();
            }
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
        }
        if(res!=null){
            dayCnt = (int) ((res.getSyncTime()- DateUtil.sysTimeMillis())/(1000*60*60*24));
        }
        if(res==null || dayCnt>=15){

            //超过15天查询天眼查接口
            String result = HttpSendUtil.queryDetails(2, traderName);
            JSONObject jsonObject = JSONObject.fromObject(result);
            String code = jsonObject.getString("error_code");
            if("300000".equals(code)){
                res = new TraderInfoTyc();
                res.setCodeType(2);//查无数据
                return res;
            }else if("300006".equals(code)){
                res = new TraderInfoTyc();
                res.setCodeType(3);//余额不足
                return res;
            }else{
                JSONObject json = JSONObject.fromObject(jsonObject.get("result"));

                TraderInfoTyc tycInfo = new TraderInfoTyc();
                if(json.containsKey("updatetime")){
                    tycInfo.setUpdateTime(Long.parseLong(json.getString("updatetime")));
                }
                if(json.containsKey("fromTime")){
                    tycInfo.setFromTime(Long.parseLong(json.getString("fromTime")));
                }
                if(json.containsKey("categoryScore")){
                    tycInfo.setCategoryScore(Integer.parseInt(json.getString("categoryScore")));
                }
                if(json.containsKey("type")){
                    tycInfo.setType(Integer.parseInt(json.getString("type")));
                }
                if(json.containsKey("id")){
                    tycInfo.setId(Long.parseLong(json.getString("id")));
                }
                if(json.containsKey("percentileScore")){
                    tycInfo.setPercentileScore(Integer.parseInt(json.getString("percentileScore")));
                }
                if(json.containsKey("regNumber")){
                    tycInfo.setRegNumber(json.getString("regNumber"));
                }
                if(json.containsKey("phoneNumber")){
                    tycInfo.setPhoneNumber(json.getString("phoneNumber"));
                }
                if(json.containsKey("regCapital")){
                    tycInfo.setRegCapital(json.getString("regCapital"));
                }
                if(json.containsKey("name")){
                    tycInfo.setName(json.getString("name"));
                }
                if(json.containsKey("regInstitute")){
                    tycInfo.setRegInstitute(json.getString("regInstitute"));
                }
                if(json.containsKey("regLocation")){
                    tycInfo.setRegLocation(json.getString("regLocation"));
                }
                if(json.containsKey("industry")){
                    tycInfo.setIndustry(json.getString("industry"));
                }
                if(json.containsKey("approvedTime")){
                    tycInfo.setApprovedTime(Long.parseLong(json.getString("approvedTime")));
                }
                if(json.containsKey("orgApprovedInstitute")){
                    tycInfo.setOrgApprovedInstitute(json.getString("orgApprovedInstitute"));
                }
                if(json.containsKey("logo")){
                    tycInfo.setLogo(json.getString("logo"));
                }
                if(json.containsKey("taxNumber")){
                    tycInfo.setTaxNumber(json.getString("taxNumber"));
                }
                if(json.containsKey("businessScope")){
                    tycInfo.setBusinessScope(json.getString("businessScope"));
                }
                if(json.containsKey("orgNumber")){
                    tycInfo.setOrgNumber(json.getString("orgNumber"));
                }
                if(json.containsKey("estiblishTime")){
                    tycInfo.setEstiblishTime(Long.parseLong(json.getString("estiblishTime")));
                }
                if(json.containsKey("regStatus")){
                    tycInfo.setRegStatus(json.getString("regStatus"));
                }
                if(json.containsKey("legalPersonName")){
                    tycInfo.setLegalPersonName(json.getString("legalPersonName"));
                }
                if(json.containsKey("websiteList")){
                    tycInfo.setWebsiteList(json.getString("websiteList"));
                }
                if(json.containsKey("toTime")){
                    tycInfo.setToTime(Long.parseLong(json.getString("toTime")));
                }
                if(json.containsKey("legalPersonId")){
                    tycInfo.setLegalPersonId(Long.parseLong(json.getString("legalPersonId")));
                }
                if(json.containsKey("sourceFlag")){
                    tycInfo.setSourceFlag(json.getString("sourceFlag"));
                }
                if(json.containsKey("actualCapital")){
                    tycInfo.setActualCapital(json.getString("actualCapital"));
                }
                if(json.containsKey("flag")){
                    tycInfo.setFlag(Integer.parseInt(json.getString("flag")));
                }
                if(json.containsKey("correctCompanyId")){
                    tycInfo.setCorrectCompanyId(json.getString("correctCompanyId"));
                }
                if(json.containsKey("companyOrgType")){
                    tycInfo.setCompanyOrgType(json.getString("companyOrgType"));
                }
                if(json.containsKey("updateTimes")){
                    tycInfo.setUpdateTimes(Long.parseLong(json.getString("updateTimes")));
                }
                if(json.containsKey("base")){
                    tycInfo.setBase(json.getString("base"));
                }
                if(json.containsKey("companyType")){
                    tycInfo.setCompanyType(Integer.parseInt(json.getString("companyType")));
                }
                if(json.containsKey("creditCode")){
                    tycInfo.setCreditCode(json.getString("creditCode"));
                }
                if(json.containsKey("companyId")){
                    tycInfo.setCompanyId(Long.parseLong(json.getString("companyId")));
                }
                if(json.containsKey("historyNames")){
                    tycInfo.setHistoryNames(json.getString("historyNames"));
                }
                tycInfo.setJsonData(json.toString());
                tycInfo.setSyncTime( DateUtil.sysTimeMillis());
                //更新天眼查信息
                final TypeReference<ResultInfo<TraderInfoTyc>> TypeRef2 = new TypeReference<ResultInfo<TraderInfoTyc>>() {};
                String uri=httpUrl + "trader/savetycinfo.htm";
                ResultInfo<?> rs = null;
                try {
                    rs = (ResultInfo<?>) HttpClientUtils.post(uri, tycInfo,clientId,clientKey, TypeRef2);
                } catch (IOException e) {
                    logger.error(Contant.ERROR_MSG, e);
                }
                if(rs!=null && rs.getCode()==0){
                    return tycInfo;
                }else{
                    return null;
                }
            }

        }else{
            return res;
        }
    }

    @Override
    public ResultInfo restVerify(TraderCustomer traderCustomer) {
        //更新天眼查信息
        final TypeReference<ResultInfo> TypeRef2 = new TypeReference<ResultInfo>() {};
        String uri=httpUrl + "verifiesrecord/restverify.htm";
        ResultInfo<?> rs = null;
        try {
            VerifiesInfo verifiesInfo = new VerifiesInfo();
            verifiesInfo.setRelateTable("T_TRADER_CUSTOMER");
            verifiesInfo.setRelateTableKey(traderCustomer.getTraderCustomerId());
            rs = (ResultInfo<?>) HttpClientUtils.post(uri, verifiesInfo,clientId,clientKey, TypeRef2);

        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
        }

        return rs;
    }

    @Override
    public Map<String, Object> getBusinessListPage(SaleorderGoodsVo saleorderGoodsVo, Page page) {
        // 调用接口
        String url = httpUrl + "tradercustomer/getbusinesslistpage.htm";
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<List<SaleorderGoodsVo>>> TypeRef = new TypeReference<ResultInfo<List<SaleorderGoodsVo>>>() {
        };
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, saleorderGoodsVo, clientId, clientKey,
                    TypeRef, page);
            if (result == null || result.getCode() == -1) {
                return null;
            }
            List<SaleorderGoodsVo> list = (List<SaleorderGoodsVo>) result.getData();
            page = result.getPage();
            Map<String, Object> map = new HashMap<>();
            map.put("list", list);
            map.put("page", page);
            return map;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public TraderCustomerVo getTraderCustomerManageInfoSeconed(TraderCustomer traderCustomer, HttpSession session) {
        // TODO Auto-generated method stub
        User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
        // 接口调用
        String url = httpUrl + "trader/getcustomermanageinfo.htm";

        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<TraderCustomerVo>> TypeRef2 = new TypeReference<ResultInfo<TraderCustomerVo>>() {
        };
        try {
            ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, traderCustomer, clientId, clientKey,
                    TypeRef2);
            TraderCustomerVo res = (TraderCustomerVo) result2.getData();
            if (null != res) {
                User sale = userMapper.getUserByTraderId(res.getTraderId(), ErpConst.ONE);
                if (null != sale) {
                    res.setOwnerSale(sale.getUsername());
                }

                CommunicateRecord communicateRecord = new  CommunicateRecord();
                communicateRecord.setTraderId(res.getTraderId());
                communicateRecord.setCompanyId(session_user.getCompanyId());
                communicateRecord.setTraderType(ErpConst.ONE);

                CommunicateRecord customerCommunicateCount = communicateRecordMapper.getTraderCommunicateCount(communicateRecord);
                if(null != customerCommunicateCount){
                    res.setCommuncateCount(customerCommunicateCount.getCommunicateCount());
                    res.setLastCommuncateTime(customerCommunicateCount.getLastCommunicateTime());
                }
                res.setCustomerProperty(getCustomerCategory(res.getTraderCustomerId()));
                User user = null;
                user = userMapper.getUserByTraderId(res.getTraderId(), ErpConst.ONE);
                if (null != user) {
                    res.setPersonal(user.getUsername());
                }

            }
            return res;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public ResultInfo saveTraderName(Trader trader){
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
        };
        try {
            // 接口调用
            String url = httpUrl + "trader/savetradername.htm";
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, trader, clientId, clientKey, TypeRef);
            if(result == null || result.getCode() == -1){
                return null;
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public TraderCustomerVo getCustomerInfo(Integer traderId) {
        // 接口调用
        String url = httpUrl + "tradercustomer/getcustomerinfo.htm";

        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<TraderCustomerVo>> TypeRef = new TypeReference<ResultInfo<TraderCustomerVo>>() {};
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, traderId, clientId, clientKey, TypeRef);
            if(result!=null && result.getCode()==0){
                TraderCustomerVo res = (TraderCustomerVo) result.getData();
                if(res!=null){
                    //数据处理(地区)
                    if(null != res.getAreaId() && res.getAreaId() > 0){
                        String region = (String) regionService.getRegion(res.getAreaId(), 2);
                        res.setAddress(region);
                    }
                    //归属销售
					/*User sale = userMapper.getUserByTraderId(res.getTraderId(), ErpConst.ONE);
					if (null != sale) {
						res.setOwnerSale(sale.getUsername());
					}*/
                }
                return res;
            }
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            return null;
        }
        return null;
    }

    @Override
    public TraderContact getTraderContactInfo(FileDelivery fileDelivery) {
        //联系人默认联系电话
        TraderAddress traderInfo = traderAddressMapper.getTraderContactInfo(fileDelivery);
        //联系人默认地址
        TraderAddress traderAddressInfo = traderAddressMapper.getTraderContactLxInfo(fileDelivery);
        TraderContact tc = new TraderContact();
        //联系信息赋值
        if(traderInfo != null){
            tc.setName(traderInfo.getUserName());
            tc.setMobile(traderInfo.getMobile());
            tc.setTelephone(traderInfo.getTelephone());
        }
        //联系地址赋值
        if(traderAddressInfo != null){
            tc.setAreaId(traderAddressInfo.getAreaId());
        }
        return tc;
    }
    
    @Override
	public Trader getBaseTraderByTraderId(Integer traderId) {
		if(null == traderId) {
			return null;
		}
		return traderMapper.getTraderByTraderId(traderId);
	}

    @Override
    public ResultInfo<?> putTraderSaleUserIdtoHC(Map<String, Object> map) {
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
        };
        ResultInfo<?> resultInfo = new ResultInfo<>();
        // map转json对象
        JSONObject jsonObject = JSONObject.fromObject(map);
        //System.out.println(jsonObject.toString());
        // 调用接口推送数据
        // 请求头
        Map<String, String> header = new HashMap<String, String>();
        String url = apiUrl + ApiUrlConstant.API_TRADER_SALER_PUT_TO_HC;
        header.put("version", "v1");
        try {
            resultInfo = (ResultInfo<?>) HttpClientUtils.put(url, jsonObject.toString(), header, TypeRef);
        } catch (Exception e) {
            logger.error("客户分配推送至耗材商城异常：",e);
        }
        return resultInfo;
    }

    @Override
    public List<AccountSalerToGo> getAccountSaler(List<Integer> traderIdList) {
        return traderMapper.getAccountSaler(traderIdList);
    }

    @Override
	public Trader getTraderByTraderName(Trader trader, Integer comId) {
		trader.setCompanyId(comId);
		String url = httpUrl + "trader/gettraderbytradername.htm";
		final TypeReference<ResultInfo<Trader>> TypeRef2 = new TypeReference<ResultInfo<Trader>>() {
		};
		try {
			ResultInfo<Trader> result2 = (ResultInfo<Trader>) HttpClientUtils.post(url, trader, clientId, clientKey,
					TypeRef2);
			if (null == result2) {
				return null;
			}

			Trader res = (Trader) result2.getData();

			return res;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}

    public void SendMessageTreader(List<Integer> rTraderJUserIds,Integer batch_to_user,Integer traderId) {
        WebAccount webAccount = new WebAccount();
        List<Integer> rTrader = new ArrayList<>();
        if (null != rTraderJUserIds && rTraderJUserIds.size() > 0) {
            webAccount.setTraders(rTraderJUserIds);
        }
        if (traderId != null) {
            rTrader.add(traderId);
            webAccount.setTraders(rTrader);
        }
        List<WebAccount> webAccountList = webAccountMapper.getWebAccontTrader(webAccount);
        List<String> phone = new ArrayList<>();
        if (null != webAccountList && webAccountList.size() > 0) {
            for (WebAccount webAccount1 : webAccountList) {
                if (webAccount1.getUserId() != batch_to_user) {
                    if (StringUtil.isNotBlank(webAccount1.getMobile())) {
                        phone.add(webAccount1.getMobile());
                    }
                }
            }
            Map<String, String> hashMap = new HashMap<>();
            List<Saleorder> saleorderList = new ArrayList<>();
            Saleorder saleorder = new Saleorder();
            saleorder.setCreateMobileList(phone);
            if (null != saleorder && phone.size() > 0) {
                saleorderList = saleorderMapper.selectSaleorderNo(saleorder);
            }
            if (null != saleorderList && saleorderList.size() > 0) {
                List<Integer> userList = new ArrayList<>();
                userList.add(batch_to_user);
                for (Saleorder saleorder1 : saleorderList) {
                    hashMap.put("saleorderNo", saleorder1.getSaleorderNo());
                    MessageUtil.sendMessage2(96, userList, hashMap, "./order/saleorder/view.do?saleorderId=" + saleorder1.getSaleorderId());
                }
            }
        }
    }

	@Override
	public ResultInfo saveMjxContactAdders(TraderMjxContactAdderss t) {
		ResultInfo resultInfo = new ResultInfo<>();
		try {
			WebAccount web = webAccountMapper.getWenAccountInfoByMobile(t.getPhone());
			if(web==null ||( web.getTraderId()==0 && web.getTraderContactId()==0)) {
				resultInfo.setCode(0);
				resultInfo.setMessage("没有关联erp客户");
				return resultInfo;
			}else {
				MjxAccountAddress mjx = new MjxAccountAddress();
				mjx.setTitleName(t.getTitleName());
				mjx.setTraderId(web.getTraderId());
				mjx.setAddress(t.getDeliveryUserAddress());
				mjx.setDeliveryName(t.getDeliveryUserName());
				String areaIds = t.getDeliveryLevel1Id()+","+t.getDeliveryLevel2Id()+","+t.getDeliveryLevel3Id();
				mjx.setAreaIds(areaIds);
				mjx.setIsDeliveryDefault(t.getIsDeliveryDefault());
				mjx.setIsInvoiceDefault(t.getIsInvoiceDefault());
				mjx.setTelphone(t.getDeliveryUserPhone());
				mjx.setModTime(DateUtil.gainNowDate());
				String area = regionService.getRegionNameStringByMinRegionIds(areaIds);
				mjx.setArea(area);
				mjx.setIsEnable(t.getIsEnable());
				mjx.setMjxContactAdderssId(t.getMjxContactAdderssId());
				MjxAccountAddress mjxAccountAddress = mjxAccountAddressMapper.getAddressInfoByParam(mjx);
				if(mjxAccountAddress==null) {
					mjx.setCreator(t.getPhone());
					mjx.setAddTime(DateUtil.gainNowDate());
					int insertCount = mjxAccountAddressMapper.insertSelective(mjx);
					resultInfo.setCode(0);
					resultInfo.setMessage("新增成功");
					resultInfo.setData(insertCount);
				}else {
					mjx.setAddressId(mjxAccountAddress.getAddressId());
					int updateCount = mjxAccountAddressMapper.updateByPrimaryKeySelective(mjx);
					resultInfo.setCode(0);
					resultInfo.setMessage("更新成功");
					resultInfo.setData(updateCount);
				}
				logger.info("saveMjxContactAdders: pojo:"+mjx);
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			logger.info("saveMjxContactAddersErro:");
		}
		return resultInfo;
	}

	@Override
	public List<MjxAccountAddress> getMjxAccountAddressList(TraderContactVo traderContactVo) {
		List<MjxAccountAddress> list = null;
		try {
			MjxAccountAddress mjx = new MjxAccountAddress();
			mjx.setTraderId(traderContactVo.getTraderId());
			mjx.setIsEnable(ErpConst.ONE);
			list = mjxAccountAddressMapper.getMjxAccountAddressList(mjx);
			String deliveryDefault = null;
			String invoiceDefault = null;
			for (MjxAccountAddress m : list) {
				deliveryDefault =(m.getIsDeliveryDefault()==1 ?"默认的收货地址;":"");
				invoiceDefault =(m.getIsInvoiceDefault()==1 ?"默认的收票地址;":"" );
				m.setDefaultAddress(deliveryDefault+invoiceDefault);
			}
		} catch (Exception e) {
			logger.info("getMjxAccountAddressListErro:"+e);
		}
		return list;
	}


    @Override
    public int updateCustomerIsProfit(Integer traderId, Integer isProfit) {
        return traderCustomerMapper.updateCustomerIsProfit(traderId,isProfit);
    }

    @Override
    public ResultInfo initCustomerAptitudeCheck(HttpServletRequest request,Page page) {
//        try {
            int count=0;
            Map<String, Object> params = new HashMap<>();
            params.put("page", page);
            TraderCustomer traderCustomer = new TraderCustomer();
            traderCustomer.setCustomerNature(465);
            params.put("trader", traderCustomer);
            List<TraderCustomer> traders = traderCustomerMapper.getTraderCustomerIdsListPage(params);
            for (TraderCustomer t : traders) {
                if (isVerify(t)) {
                    count++;
                    startCheckAptitude(request, t, null);
                }
            }

            int pageSum = page.getTotalPage();
            if (pageSum > 1) {
                for (int i = 2; i <= pageSum; i++) {
                    page.setPageNo(i);
                    params.put("page", page);
                    List<TraderCustomer> traderCustomers = traderCustomerMapper.getTraderCustomerIdsListPage(params);
                    for (TraderCustomer t : traderCustomers) {
                        if (isVerify(t)) {
                            count++;
                            startCheckAptitude(request, t, null);
                        }
                    }
                }
            }
//        }catch (Exception ex){
//            return new ResultInfo(-1,"操作失败");
//        }

        return new ResultInfo(1,"操作成功"+count);
    }

   @Override
    public TraderCustomer getTraderCustomerId(Integer traderId) {
        return traderCustomerMapper.getTraderCustomerById(traderId);
    }

    @Override
    public Map childOrganization(Organization organization, Map<String, Integer> orgHashMap) {
        if (organization != null && orgHashMap != null) {
            Map returnMap=new HashMap();
            for (Map.Entry<String, Integer> entry : orgHashMap.entrySet()) {
                List<Organization> organizationList = organizationMapper.childOrganization(entry.getValue());
                for(Organization organization1:organizationList){
                     returnMap= getTree(organization,organizationList,entry.getKey(),entry.getValue());
                     if(returnMap!=null){
                         return returnMap;
                     }
                }
            }

        }
        return null;
    }

    private Map getTree(Organization organization,List<Organization> orgIds,String key,Integer value) {
        Integer orgId = organization.getOrgId();
        List<Organization> childs = null;
        Iterator<Organization> iterator = orgIds.iterator();
        while (iterator.hasNext()) {
            Organization m = iterator.next();
            if (m.getOrgId().equals(orgId)) {
                return returnMap(key, value);
            }
                childs = m.getOrganizations();
                if (childs != null) {
                    getTree(organization, childs, key, value);
                }
        }
       return null;
    }

    public Map returnMap(String key,Integer value){
        Map<String ,Integer> hashMap=new HashMap<>();
        hashMap.put(key,value);
        return hashMap;
    }

    /**
     * <b>Description:验证客户资质是否全</b><br>
     * @param
     * @return
     * @Note
     * <b>Author:calvin</b>
     * <br><b>Date:</b> ${date} ${time}
     */
    private boolean isVerify(TraderCustomer customer){
         List<TraderCertificate> certificates=traderCertificateMapper.getTraderCertificatesByTraderId(customer);
         if(CollectionUtils.isEmpty(certificates)){
             return false;
         }
         boolean isVerify=false;
         for(TraderCertificate certificate:certificates){
             if(certificate.getSysOptionDefinitionId()==SysOptionConstant.ID_25
             &&certificate.getBegintime()!=null&&StringUtil.isNotBlank(certificate.getUri())){
                 isVerify=true;
             }
         }
         return isVerify;
    }


    /**
     * <b>Description:</b><br> 开始审核资质
     *
     * @param traderCustomer 客户信息
     * @return
     * @Note <b>Author:calvin</b>
     * <br><b>Date:2019/9/5</b>
     */
    public ResultInfo startCheckAptitude(HttpServletRequest request, TraderCustomer traderCustomer, String taskId) {
        try {
            Map<String, Object> variableMap = new HashMap<String, Object>();
            User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
            // 查询当前订单的一些状态
            /*TraderCustomerVo traderBaseInfo = traderCustomerService.getTraderCustomerBaseInfo(traderCustomer);*/
            //开始生成流程(如果没有taskId表示新流程需要生成)
            if (StringUtil.isBlank(taskId) || taskId.equals("0")) {
                variableMap.put("traderCustomer", traderCustomer);
                variableMap.put("currentAssinee", user.getUsername());
                variableMap.put("processDefinitionKey", "customerAptitudeVerify");
                variableMap.put("businessKey", "customerAptitude_" + traderCustomer.getTraderCustomerId());
                variableMap.put("relateTableKey", traderCustomer.getTraderCustomerId());
                variableMap.put("relateTable", "T_CUSTOMER_APTITUDE");
                actionProcdefService.createProcessInstance(request, "customerAptitudeVerify", "customerAptitude_" +traderCustomer.getTraderCustomerId(), variableMap);
            }
            //默认申请人通过
            //根据BusinessKey获取生成的审核实例
            Map<String, Object> historicInfo = actionProcdefService.getHistoric(processEngine, "customerAptitude_" + traderCustomer.getTraderCustomerId());
            if (historicInfo.get("endStatus") != "审核完成") {
                Task taskInfo = (Task) historicInfo.get("taskInfo");
                taskId = taskInfo.getId();
                ResultInfo<?> complementStatus = actionProcdefService.complementTask(request, taskId, "", user.getUsername(), variableMap);
                //如果未结束添加审核对应主表的审核状态
                if (!complementStatus.getData().equals("endEvent")) {
                    verifiesRecordService.saveVerifiesInfoForTrader(taskId, 0);
                }

            }

            return new ResultInfo(0, "操作成功");
        } catch (Exception e) {
            logger.error("editApplyValidCustomer:", e);
            return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
        }
    }

    @Override
    public void setCustomerAptitudeUncheck(Integer traderCustomerId) {
        VerifiesInfo verifiesInfo=getCustomerAptitudeVerifiesInfo(traderCustomerId);
		if(verifiesInfo==null||verifiesInfo.getStatus()==null||verifiesInfo.getVerifiesInfoId()==null){
			return;
		}
		if(verifiesInfo.getStatus()==0){
			String taskId=getCustomerAptitudeTaskId(traderCustomerId);
			if(StringUtil.isBlank(taskId)){
				verifiesInfoMapper.deleteByPrimaryKey(verifiesInfo.getVerifiesInfoId());
				return;
			}
			recallChangeAptitude(taskId);
		}else{
			verifiesInfoMapper.deleteByPrimaryKey(verifiesInfo.getVerifiesInfoId());
		}
    }

    @Override
    public ResultInfo recallChangeAptitude(String taskId){
        ResultInfo resultUpdateTable=verifiesRecordService.saveVerifiesInfoForTrader(taskId, 3);
        ResultInfo resultDeleteInstance=actionProcdefService.deleteProcessInstance(taskId);
        if(resultUpdateTable.getCode()==0&&resultDeleteInstance.getCode()==0){
            return resultDeleteInstance;
        }
        return new ResultInfo(-1,"操作失败");
    }

    public String getCustomerAptitudeTaskId(Integer traderCustomerId){
        try {
            Map<String, Object> historicInfo = actionProcdefService.getHistoric(processEngine, "customerAptitude_" + traderCustomerId);
            if (historicInfo != null) {
                TaskInfo taskInfo = (TaskInfo) historicInfo.get("taskInfo");
                return taskInfo.getId();
            }
        }catch (Exception ex){

        }
        return null;
    }


    @Override
    public List<Integer> updateVedengMember() {
        //全部满足则为贝登会员
        //（1）注册账号归属平台为“贝登医疗”
        //（2）注册账号在ERP中已关联公司
        //（3）关联公司的客户性质为“分销”
        //（4）关联公司的“客户资质审核”为“审核通过”（注意是“资质审核状态”，而不是“公司审核状态”）
        //获取符合变更为贝登会员的用户
        List<Integer> webAccountList = new ArrayList<>();
        try {
            webAccountList = webAccountMapper.getWebAccountIsMember();
            if(CollectionUtils.isNotEmpty(webAccountList)){
                Integer num =  webAccountMapper.updateVedengMember(webAccountList);
                Integer num2 =  webAccountMapper.updateVedengMemberTime(webAccountList);
            }
            //判断已经为会员的现在是否满足条件
            updateVedengMember2();
//            //更新客户是否是会员
            updateCustomerMember();
        }catch (Exception e){
            logger.error("updateVedengMember  error:{}",e);
        }
        return webAccountList;
    }

    private void updateCustomerMember() {
        //获取符合条件的客户会员
        List<Integer> customerIdList = traderCustomerMapper.getTraderCustomerIsMember();
        if(CollectionUtils.isNotEmpty(customerIdList)){
            traderCustomerMapper.updateIsVedengMember(customerIdList,1);
        }
        //将不符合条件的客户置为非会员
        updateCustomerNoMember();

    }

    private void updateCustomerNoMember() {
        //获取是贝登会员的客户信息
        List<WebAccount> list =  webAccountMapper.getTraderCustomerMemberInfo();
        List<Integer> noMemberidList = getnoMemberIds(list);
        if(CollectionUtils.isNotEmpty(noMemberidList)){
            traderCustomerMapper.updateIsVedengMember(noMemberidList,0);
        }
    }

    private List<Integer> getnoMemberIds(List<WebAccount> list) {
        List<Integer> noMemberidList = new ArrayList<>();
        for (WebAccount webAccount : list) {
            if(isVedengMember(webAccount)){
                continue;
            }
            noMemberidList.add(webAccount.getErpAccountId());
        }
        return noMemberidList;
    }

    private void updateVedengMember2() {
        //获取是贝登会员的用户信息
      List<WebAccount> list =  webAccountMapper.getWebAccountMemberInfo();
        //判断哪些用户不符合条件
        List<Integer> noMemberidList = getnoMemberIds(list);
          if(CollectionUtils.isNotEmpty(noMemberidList)) {
              Integer num = webAccountMapper.updateVedengNoMember(noMemberidList);
          }
    }

    private boolean isVedengMember(WebAccount webAccount) {
        if(webAccount.getBelongPlatform() == null || !webAccount.getBelongPlatform().equals(1)){
            return false;
        }
        if(!webAccount.getCustomerNature().equals(465)){
            return false;
        }
        if(!webAccount.getStatus().equals(1)){
            return false;
        }
        if(webAccount.getTraderId() == null || webAccount.getTraderId().equals(0)){
            return false;
        }
        return true;
    }

    @Override
    public VerifiesInfo getCustomerAptitudeVerifiesInfo(Integer traderCustomerId){
        VerifiesInfo verifiesInfo=new VerifiesInfo();
        verifiesInfo.setRelateTable("T_CUSTOMER_APTITUDE");
        verifiesInfo.setRelateTableKey(traderCustomerId);
        List<VerifiesInfo> verifiesInfos=verifiesRecordService.getVerifiesList(verifiesInfo);
        if(!org.springframework.util.CollectionUtils.isEmpty(verifiesInfos)){
            verifiesInfo=verifiesInfos.get(0);
            if(verifiesInfo.getVerifiesInfoId()>0){
                return verifiesInfo;
            }
        }
        return null;
    }

    @Override
    public TraderCustomer getSimpleCustomer(Integer traderId) {
        return traderCustomerMapper.getBaseCustomerByTraderId(traderId);
    }

    @Override
    public ResultInfo syncYxgTraderStatus(YXGTraderAptitude aptitudeStatus) {
        if(aptitudeStatus==null||aptitudeStatus.getTraderId()==null
                ||aptitudeStatus.getStatus()==null){
            return new ResultInfo(-1,"操作失败，客户标识或审核状态为空");
        }
        TraderCustomer traderCustomer=traderCustomerMapper.getBaseCustomerByTraderId(aptitudeStatus.getTraderId());
        if(traderCustomer==null||traderCustomer.getTraderCustomerId()==null){
            return new ResultInfo(-1,"该客户不存在");
        }
        VerifiesInfo verifiesInfo=getCustomerAptitudeVerifiesInfo(traderCustomer.getTraderCustomerId());
        if(verifiesInfo==null||verifiesInfo.getVerifiesInfoId()==null){
            verifiesRecordService.saveVerifiesInfoDirect(initAptitudeVerifiesInfo(null,
                    traderCustomer.getTraderCustomerId(),aptitudeStatus.getStatus()));
        }else{
            verifiesRecordService.saveVerifiesInfoDirect(initAptitudeVerifiesInfo(verifiesInfo.getVerifiesInfoId(),
                    traderCustomer.getTraderCustomerId(),aptitudeStatus.getStatus()));
        }
        return new ResultInfo(0,"操作成功");
    }

    /**
     * <b>Description:</b>初始化客户资质审核记录信息<br>
     * @param
     * @return
     * @Note
     * <b>Author:calvin</b>
     * <br><b>Date:</b> 2020/3/23
     */
    private VerifiesInfo initAptitudeVerifiesInfo(Integer verifiesId,Integer traderCustomerId,Integer status){
        VerifiesInfo verifiesInfo=new VerifiesInfo();
        verifiesInfo.setRelateTableKey(traderCustomerId);
        verifiesInfo.setVerifiesInfoId(verifiesId);
        verifiesInfo.setRelateTable("T_CUSTOMER_APTITUDE");
        verifiesInfo.setStatus(status);
        long time=System.currentTimeMillis();
        verifiesInfo.setAddTime(time);
        verifiesInfo.setModTime(time);
        verifiesInfo.setVerifyUsername("医械购");
        return verifiesInfo;
    }

    @Override
    public TraderCustomer getTraderByPayApply(Integer payApplyId){
        return traderCustomerMapper.getTraderCustomerByPayApply(payApplyId);
    }


    @Override
    public void updateTraderAmount(Integer traderId, BigDecimal amount){
        TraderCustomer tc = new TraderCustomer();
        tc.setTraderId(traderId);
        tc.setAmount(amount);
        tc.setModTime(DateUtil.sysTimeMillis());
        traderCustomerMapper.updateTraderCustomerAmount(tc);
    }

    @Override
    public TraderContactGenerate getTraderContactByTraderContactId(Integer takeTraderContactId) {
        return  traderContactGenerateMapper.selectByPrimaryKey(takeTraderContactId);
    }

    @Override
    public TraderAddress getTraderAddressById(Integer takeTraderAddressId) {
        return traderAddressMapper.getAddressInfoById(takeTraderAddressId, 1);
    }

    @Override
    public Trader getTraderByTraderId(Integer traderId) {
        return  traderMapper.getTraderByTraderId(traderId);
    }
}
