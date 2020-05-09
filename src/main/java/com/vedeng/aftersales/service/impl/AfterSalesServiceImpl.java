package com.vedeng.aftersales.service.impl;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.common.constants.Contant;
import com.vedeng.aftersales.dao.AfterSalesDetailMapper;
import com.vedeng.aftersales.dao.AfterSalesMapper;
import com.vedeng.common.constant.OrderDataUpdateConstant;
import com.vedeng.common.http.NewHttpClientUtils;
import com.vedeng.logistics.model.WarehouseStock;
import com.vedeng.logistics.service.WarehouseStockService;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.model.vo.OrderData;
import com.vedeng.trader.dao.WebAccountMapper;
import com.vedeng.trader.model.WebAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.aftersales.dao.AfterSalesGoodsMapper;
import com.vedeng.aftersales.dao.AfterSalesMapper;
import com.vedeng.aftersales.model.AfterSales;
import com.vedeng.aftersales.model.AfterSalesCost;
import com.vedeng.aftersales.model.AfterSalesDetail;
import com.vedeng.aftersales.model.AfterSalesGoods;
import com.vedeng.aftersales.model.AfterSalesInstallstion;
import com.vedeng.aftersales.model.AfterSalesInvoice;
import com.vedeng.aftersales.model.AfterSalesRecord;
import com.vedeng.aftersales.model.AfterSalesTrader;
import com.vedeng.aftersales.model.CostType;
import com.vedeng.aftersales.model.RInstallstionJGoods;
import com.vedeng.aftersales.model.vo.AfterSalesCostVo;
import com.vedeng.aftersales.model.vo.AfterSalesDetailVo;
import com.vedeng.aftersales.model.vo.AfterSalesGoodsVo;
import com.vedeng.aftersales.model.vo.AfterSalesInstallstionVo;
import com.vedeng.aftersales.model.vo.AfterSalesInvoiceVo;
import com.vedeng.aftersales.model.vo.AfterSalesRecordVo;
import com.vedeng.aftersales.model.vo.AfterSalesVo;
import com.vedeng.aftersales.model.vo.EngineerVo;
import com.vedeng.aftersales.service.AfterSalesService;
import com.vedeng.authorization.dao.UserMapper;
import com.vedeng.authorization.model.Organization;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.common.util.MessageUtil;
import com.vedeng.common.util.ObjectUtils;
import com.vedeng.common.util.SmsUtil;
import com.vedeng.finance.model.CapitalBill;
import com.vedeng.finance.model.InvoiceApply;
import com.vedeng.finance.model.PayApply;
import com.vedeng.finance.model.vo.PayApplyVo;
import com.vedeng.goods.dao.RCategoryJUserMapper;
import com.vedeng.goods.dao.RManageCategoryJUserMapper;
import com.vedeng.logistics.dao.WarehouseGoodsOperateLogMapper;
import com.vedeng.logistics.dao.WarehouseGoodsStatusMapper;
import com.vedeng.logistics.model.WarehouseGoodsOperateLog;
import com.vedeng.logistics.model.WarehouseGoodsStatus;
import com.vedeng.order.dao.SaleorderMapper;
import com.vedeng.system.dao.ParamsConfigMapper;
import com.vedeng.system.model.Attachment;
import com.vedeng.system.model.vo.ParamsConfigVo;
import com.vedeng.system.service.OrgService;
import com.vedeng.system.service.RegionService;
import com.vedeng.trader.dao.RTraderJUserMapper;
import com.vedeng.trader.model.RTraderJUser;
import com.vedeng.trader.model.TraderAddress;
import com.vedeng.trader.model.vo.TraderAddressVo;
import com.vedeng.trader.model.vo.TraderVo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.util.CollectionUtils;

/**
 * <b>Description:</b><br> 售后订单服务层
 * @author east
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.aftersales.service.impl
 * <br><b>ClassName:</b> AfterSalesServiceImpl
 * <br><b>Date:</b> 2017年10月9日 上午11:26:20
 */
@Service("afterSalesOrderService")
public class AfterSalesServiceImpl extends BaseServiceimpl implements AfterSalesService {
	public static Logger logger = LoggerFactory.getLogger(AfterSalesServiceImpl.class);

	// add by Tomcat.Hui 2020/3/6 11:41 上午 .Desc: VDERP-2057 BD订单流程优化-ERP部分
	@Value("${mjx_url}")
	private String mjxurl;

	@Autowired
	@Qualifier("webAccountMapper")
	private WebAccountMapper webAccountMapper;

	//add by Tomcat.Hui 2019/9/5 10:44 .Desc: VDERP-1053 add mapper. start
	@Autowired
	@Qualifier("afterSalesMapper")
	private AfterSalesMapper afterSalesMapper;
	//add by Tomcat.Hui 2019/9/5 10:44 .Desc: VDERP-1053 add mapper. end

	@Autowired
	@Qualifier("userMapper")
	private UserMapper userMapper;
	
	@Resource
	private RegionService regionService;
	
	@Resource
	private RManageCategoryJUserMapper rManageCategoryJUserMapper;
	
	@Autowired
	@Qualifier("rCategoryJUserMapper")
	private RCategoryJUserMapper rCategoryJUserMapper;
	
	@Resource
	private OrgService orgService;
	
	@Resource
	private ParamsConfigMapper paramsConfigMapper;
	
	@Resource
	private RTraderJUserMapper rTraderJUserMapper;

	
	@Autowired
	@Qualifier("afterSalesGoodsMapper")
	private AfterSalesGoodsMapper afterSalesGoodsMapper;
	
	@Autowired
	@Qualifier("warehouseGoodsOperateLogMapper")
	private WarehouseGoodsOperateLogMapper warehouseGoodsOperateLogMapper;

	@Autowired
	private WarehouseStockService warehouseStockService;
	
	@Autowired
	@Qualifier("saleorderMapper")
	private SaleorderMapper saleorderMapper;
	
	@Autowired
	@Qualifier("warehouseGoodsStatusMapper")
	private WarehouseGoodsStatusMapper warehouseGoodsStatusMapper;

	@Autowired
	private AfterSalesDetailMapper afterSalesDetailMapper;
	/**
	 * <b>Description:</b><br> 查询售后订单分页信息
	 * @param afterSalesVo
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月9日 上午11:22:12
	 */
	@Override
	public Map<String, Object> getAfterSalesPage(AfterSalesVo afterSalesVo, Page page,List<User> userList) {
		String url = httpUrl + ErpConst.GET_AFTERSALES_PAGE;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<AfterSalesVo>>> TypeRef2 = new TypeReference<ResultInfo<List<AfterSalesVo>>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, afterSalesVo, clientId, clientKey, TypeRef2,
					page);
			if(result == null || result.getCode() ==-1){
				return null;
			}
			@SuppressWarnings("unchecked")
			List<AfterSalesVo> asvList = (List<AfterSalesVo>) result.getData();
			page = result.getPage();
			if(asvList != null && asvList.size() > 0){
				for (AfterSalesVo asv : asvList) {
					if(asv.getCreator() != 0){
						asv.setCreatorName(getUserNameByUserId(asv.getCreator()));
						asv.setOrgName(getOrgaNameByUserId(asv.getCreator()));
					}
					if(asv.getServiceUserId() != 0){
						asv.setServiceUserName(getUserNameByUserId(asv.getServiceUserId()));
					}
//					for (User user : userList) {
//						if(asv.getServiceUserId().equals(user.getUserId())){
//							asv.setIsView(1);
//							break;
//						}
//					}
					//审核人
					if(null != asv.getVerifyUsername()){
					    List<String> verifyUsernameList = Arrays.asList(asv.getVerifyUsername().split(","));  
					    asv.setVerifyUsernameList(verifyUsernameList);
					}
				}
			}
			Map<String, Object> map = new HashMap<>();
			map.put("list", asvList);
			map.put("page", page);
			return map;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getbuyorderAfterSalesList(AfterSalesVo afterSalesVo, Page page) {
		String url = httpUrl + "aftersales/order/getbuyorderaftersaleslistpage.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<AfterSalesVo>>> TypeRef2 = new TypeReference<ResultInfo<List<AfterSalesVo>>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, afterSalesVo, clientId, clientKey, TypeRef2,
					page);
			if(result == null || result.getCode() ==-1){
				return null;
			}
			Map<String, Object> map = new HashMap<>();
			List<AfterSalesVo> asvList = (List<AfterSalesVo>) result.getData();
			page = result.getPage();
			if(asvList.size() > 0){
				List<Integer> userIds = new ArrayList<>();
				for(AfterSalesVo vo : asvList){
					if(vo.getCreator() > 0){
						userIds.add(vo.getCreator());
					}
				}
				if (userIds.size() > 0) {
					List<User> userList = userMapper.getUserByUserIds(userIds);
					for(AfterSalesVo afterSales : asvList){
						if(afterSales.getCreator() > 0){
							for(User u : userList){
								if(u.getUserId().equals(afterSales.getCreator())){
									afterSales.setCreatorName(u.getUsername());
									break;
								}
							}
						}
					}
				}
			}
			
			map.put("list", asvList);
			map.put("page", page);
			return map;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}
	/**
	 * <b>Description:</b><br> 根据订单id查询对应的售后订单列表
	 * @param afterSalesVo
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月10日 上午9:12:45
	 */
	@Override
	public List<AfterSalesVo> getAfterSalesVoListByOrderId(AfterSalesVo afterSalesVo) {
		String url = httpUrl + ErpConst.GET_AFTERSALESLIST_BYORDERID;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<AfterSalesVo>>> TypeRef2 = new TypeReference<ResultInfo<List<AfterSalesVo>>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, afterSalesVo, clientId, clientKey, TypeRef2);
			if(result == null || result.getCode() ==-1){
				return null;
			}
			@SuppressWarnings("unchecked")
			List<AfterSalesVo> asvList = (List<AfterSalesVo>) result.getData();
			if(asvList != null && asvList.size() > 0){
				for (AfterSalesVo asv : asvList) {
					if(asv.getCreator() != 0){
						asv.setCreatorName(getUserNameByUserId(asv.getCreator()));
					}
				}
			}
			return asvList;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}
	/**
	 * <b>Description:</b><br> 保存新增售后
	 * @param afterSalesVo
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月11日 下午4:28:14
	 */
	@Override
	public ResultInfo<?> saveAddAfterSales(AfterSalesVo afterSalesVo, User user) {
		afterSalesVo.setCreator(user.getUserId());
		afterSalesVo.setAddTime(DateUtil.sysTimeMillis());
		afterSalesVo.setUpdater(user.getUserId());
		afterSalesVo.setModTime(DateUtil.sysTimeMillis());
		if(afterSalesVo.getSubjectType() == 535){
			afterSalesVo.setServiceUserId(getAfterSalesServiceUser(user));//根据当前的销售人员获取相应的售后人员
		}
		if(ObjectUtils.notEmpty(afterSalesVo.getZone())){
			afterSalesVo.setArea(getAddressByAreaId(afterSalesVo.getZone()));
		}else if(ObjectUtils.notEmpty(afterSalesVo.getCity()) && ObjectUtils.isEmpty(afterSalesVo.getZone())){
			afterSalesVo.setArea(getAddressByAreaId(afterSalesVo.getCity()));
		}else if(ObjectUtils.notEmpty(afterSalesVo.getProvince()) && ObjectUtils.isEmpty(afterSalesVo.getCity()) && ObjectUtils.isEmpty(afterSalesVo.getZone())){
			afterSalesVo.setArea(getAddressByAreaId(afterSalesVo.getProvince()));
		}
		
		String url = httpUrl + ErpConst.SAVE_ADD_AFTERSALES;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, afterSalesVo, clientId, clientKey, TypeRef2);
			if(result != null && result.getCode().equals(0)){
				Integer afterSaleorderId = (Integer)result.getData();
				updateAfterOrderDataUpdateTime(afterSaleorderId,null, OrderDataUpdateConstant.AFTER_ORDER_GENERATE);
			}
			return result;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}
	
	/**
	 * <b>Description:</b><br> 根据当前的销售人员获取相应的售后人员
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年12月25日 下午3:38:32
	 */
	private Integer getAfterSalesServiceUser(User user){
		//获取当前销售人员的一级部门的orgid
		Integer userId = 0;
		Integer orgId = user.getOrgId();
		
//		List<Organization> orgList = orgService.getParentOrgList(user.getOrgId());//根据当前部门ID查询所有上级集合
//		for (Organization org : orgList) {
//			if(org.getLevel() == 5 || org.getLevel() == 4){
//				orgId = org.getOrgId();
//				break;
//			}
//		}
		
		// add by franlin.wu for [避免空指针异常] at 2018-12-15 begin
		if(null != orgId && orgId != 0)
		// add by franlin.wu for [避免空指针异常] at 2018-12-15 end
		{
			ParamsConfigVo paramsConfigVo = new ParamsConfigVo();
			paramsConfigVo.setCompanyId(user.getCompanyId());
			paramsConfigVo.setParamsKey(109);
			paramsConfigVo.setTitle(orgId.toString());
			ParamsConfigVo aftersales = paramsConfigMapper.getParamsConfigVoByParamsKey(paramsConfigVo);
			if(aftersales == null){
				paramsConfigVo.setTitle(null);
				paramsConfigVo.setParamsKey(108);
				aftersales = paramsConfigMapper.getParamsConfigVoByParamsKey(paramsConfigVo);
			}
			if(aftersales != null){
				User us = userMapper.getByUsername(aftersales.getParamsValue(), user.getCompanyId());
				userId = us == null?0:us.getUserId();
			}
		}
		return userId;
	}
	
	/**
	 * <b>Description:</b><br> 保存编辑售后
	 * @param afterSalesVo
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月11日 下午4:28:14
	 */
	@Override
	public ResultInfo<?> saveEditAfterSales(AfterSalesVo afterSalesVo, User user) {
		afterSalesVo.setUpdater(user.getUserId());
		afterSalesVo.setModTime(DateUtil.sysTimeMillis());
		if(ObjectUtils.notEmpty(afterSalesVo.getZone())){
			afterSalesVo.setArea(getAddressByAreaId(afterSalesVo.getZone()));
		}else if(ObjectUtils.notEmpty(afterSalesVo.getCity()) && ObjectUtils.isEmpty(afterSalesVo.getZone())){
			afterSalesVo.setArea(getAddressByAreaId(afterSalesVo.getCity()));
		}else if(ObjectUtils.notEmpty(afterSalesVo.getProvince()) && ObjectUtils.isEmpty(afterSalesVo.getCity()) && ObjectUtils.isEmpty(afterSalesVo.getZone())){
			afterSalesVo.setArea(getAddressByAreaId(afterSalesVo.getProvince()));
		}
		
		String url = httpUrl + ErpConst.SAVE_EDIT_AFTERSALES;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, afterSalesVo, clientId, clientKey, TypeRef2);
			if(result != null && result.getCode().equals(0)){
			Integer afterId = (Integer) result.getData();
				//更新售后单updataTime
				updateAfterOrderDataUpdateTime(afterId,null,OrderDataUpdateConstant.AFTER_ORDER_GENERATE);
			}
			return result;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}

	/**
	 * <b>Description:</b><br> 获取售后的订单详情
	 * @param afterSales
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月17日 下午2:01:58
	 */
	@Override
	public AfterSalesVo getAfterSalesVoDetail(AfterSales afterSales) {
		String url = httpUrl + ErpConst.VIEW_AFTERSALES_DETAIL;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, afterSales, clientId, clientKey, TypeRef2);
			if(result == null || result.getCode() == -1){
				return null;
			}
			JSONObject json = JSONObject.fromObject(result.getData());
			AfterSalesVo afterSalesVo = JsonUtils.readValue(json.toString(), AfterSalesVo.class);
			if(afterSalesVo != null){
				afterSalesVo.setCreatorName(getUserNameByUserId(afterSalesVo.getCreator()));
				//查询关闭人名字
				afterSalesVo.setAfterSalesStatusUserName(getUserNameByUserId(afterSalesVo.getAfterSalesStatusUser()));
				afterSalesVo.setUserName(getUserNameByUserId(afterSalesVo.getUserId()));
				afterSalesVo.setOrgName(getOrgNameByOrgId(afterSalesVo.getOrgId()));
				if(afterSalesVo.getAfterSalesGoodsList() != null){
					for (AfterSalesGoodsVo asgv : afterSalesVo.getAfterSalesGoodsList()) {
						asgv.setUserList(rCategoryJUserMapper.getUserByCategory(asgv.getCategoryId(), afterSales.getCompanyId()));
					}
				}
				if(afterSalesVo.getAfterSalesRecordVoList() != null){
					for (AfterSalesRecordVo asrv : afterSalesVo.getAfterSalesRecordVoList()) {
						asrv.setOptName(getUserNameByUserId(asrv.getCreator()));
					}
				}
				if(afterSalesVo.getAfterSalesInvoiceVoList() != null){//退票
					for (AfterSalesInvoiceVo asi : afterSalesVo.getAfterSalesInvoiceVoList()) {
						asi.setCreatorName(getUserNameByUserId(asi.getCreator()));;
						asi.setValidUsername(getUserNameByUserId(asi.getValidUserId()));
					}
				}
				if(afterSalesVo.getAfterContractAttachmentList() != null){
					for (Attachment aca : afterSalesVo.getAfterContractAttachmentList()) {
						aca.setUsername(getUserNameByUserId(aca.getCreator()));
					}
				}
				if(afterSalesVo.getAfterInvoiceAttachmentList() != null){
					for (Attachment aca : afterSalesVo.getAfterInvoiceAttachmentList()) {
						aca.setUsername(getUserNameByUserId(aca.getCreator()));
					}
				}
				if(afterSalesVo.getAfterReturnInstockList() != null){
					for (WarehouseGoodsOperateLog aca : afterSalesVo.getAfterReturnInstockList()) {
						aca.setOperator(getUserNameByUserId(aca.getCreator()));
					}
				}
				if(afterSalesVo.getAfterReturnOutstockList() != null){
					for (WarehouseGoodsOperateLog aca : afterSalesVo.getAfterReturnOutstockList()) {
						aca.setOperator(getUserNameByUserId(aca.getCreator()));
					}
				}
				if(afterSalesVo.getAfterPayApplyList() != null){
					for (PayApply app : afterSalesVo.getAfterPayApplyList()) {
						app.setCreatorName(getUserNameByUserId(app.getCreator()));
					}
				}
				if(afterSalesVo.getAfterOpenInvoiceList() != null){//退票
					for (AfterSalesInvoiceVo asi : afterSalesVo.getAfterOpenInvoiceList()) {
						asi.setCreatorName(getUserNameByUserId(asi.getCreator()));
						asi.setValidUsername(getUserNameByUserId(asi.getValidUserId()));
					}
				}
				if(afterSalesVo.getAfterCapitalBillList() != null){//交易信息
					for (CapitalBill asi : afterSalesVo.getAfterCapitalBillList()) {
						asi.setCreatorName(getUserNameByUserId(asi.getCreator()));;
					}
				}
			}
			return afterSalesVo;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}

	@Override
	public Map<String, Object> getStorageAftersalesPage(AfterSalesVo afterSalesVo, Page page) {
		String url = httpUrl + "aftersales/order/getstorageaftersalespage.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<AfterSalesVo>>> TypeRef2 = new TypeReference<ResultInfo<List<AfterSalesVo>>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, afterSalesVo, clientId, clientKey, TypeRef2,
					page);
			if(result == null || result.getCode() ==-1){
				return null;
			}
			Map<String, Object> map = new HashMap<>();
			List<AfterSalesVo> asvList = (List<AfterSalesVo>) result.getData();
			page = result.getPage();
			map.put("list", asvList);
			map.put("page", page);
			return map;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}

	@Override
	public List<AfterSalesGoodsVo> getAfterSalesGoodsVoList(AfterSalesVo asv,HttpSession hs) {
		String url = httpUrl + "aftersales/order/getaftersalesgoodsvolist.htm";
		User user = (User) hs.getAttribute(ErpConst.CURR_USER);
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<AfterSalesGoodsVo>>> TypeRef2 = new TypeReference<ResultInfo<List<AfterSalesGoodsVo>>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, asv, clientId, clientKey, TypeRef2);
			if(result == null || result.getCode() ==-1){
				return null;
			}
			@SuppressWarnings("unchecked")
			List<AfterSalesGoodsVo> asvList = (List<AfterSalesGoodsVo>) result.getData();
			for (AfterSalesGoodsVo aVo : asvList) {
				aVo.setGoodsHeader(rCategoryJUserMapper.getUserByCategoryNm(aVo.getCategoryId(), user.getCompanyId()));
				/**
				 * 调用接口获取库存信息
				 */
				List<String> skus = Arrays.asList(aVo.getSku());
				Map<String, WarehouseStock> stockInfo = warehouseStockService.getStockInfo(skus);
				if(!CollectionUtils.isEmpty(stockInfo)) {
				    if(stockInfo.get(aVo.getSku()) == null){
                        aVo.setTotalNum(0);
                        aVo.setActionLockCount(0);
                    }else {
                        aVo.setTotalNum(stockInfo.get(aVo.getSku()).getStockNum());
                        aVo.setActionLockCount(stockInfo.get(aVo.getSku()).getActionLockNum());
                    }
				}else{
					aVo.setTotalNum(0);
					aVo.setActionLockCount(0);
				}
			}
			return asvList;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}

	@Override
	public AfterSalesVo getAfterSalesVoListById(AfterSales afterSales) {
		String url = httpUrl + "aftersales/order/getaftersalesvolistbyid.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<AfterSalesVo>> TypeRef2 = new TypeReference<ResultInfo<AfterSalesVo>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, afterSales, clientId, clientKey, TypeRef2);
			AfterSalesVo avs = (AfterSalesVo) result.getData();
			return avs;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}

	@Override
	public AfterSalesGoodsVo getAfterSalesGoodsInfo(AfterSalesGoods afterSalesGoods) {
		String url = httpUrl + "aftersales/order/getaftersalesgoodsinfo.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<AfterSalesGoodsVo>> TypeRef2 = new TypeReference<ResultInfo<AfterSalesGoodsVo>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, afterSalesGoods, clientId, clientKey, TypeRef2);
			AfterSalesGoodsVo avs = (AfterSalesGoodsVo) result.getData();
			return avs;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}
	/**
	 * <b>Description:</b><br> 申请审核
	 * @param afterSales
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月19日 上午11:02:23
	 */
	@Override
	public ResultInfo<?> editApplyAudit(AfterSalesVo afterSales) {
		String url = httpUrl + ErpConst.APPLY_AFTERSALES_AUDIT;
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<AfterSalesDetailVo>> TypeRef2 = new TypeReference<ResultInfo<AfterSalesDetailVo>>() {};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, afterSales, clientId, clientKey, TypeRef2);
			if(result != null){
				if(result.getCode() == 0 && result.getData() != null){
					//审核通过--进行中
					if(afterSales.getStatus() == 2 && afterSales.getAtferSalesStatus() == 1){//审核通过，退余额，退账期
						//销售--退货及退款
						if(afterSales.getSubjectType() != null && afterSales.getSubjectType()==535 && (afterSales.getType() == 539 || afterSales.getType() == 543)){
							//发送消息--售后销售订单退货-退款审核通过（钱退到账户余额）
							AfterSalesDetailVo new_afterSales = (AfterSalesDetailVo)result.getData();
							if(new_afterSales != null && new_afterSales.getTraderId() != null){
								//根据客户Id查询客户负责人
								List<Integer> userIdList = userMapper.getUserIdListByTraderId(new_afterSales.getTraderId(),ErpConst.ONE);
								Map<String,String> map = new HashMap<>();
								map.put("afterorderNo", new_afterSales.getAfterSalesNo());
								MessageUtil.sendMessage(40, userIdList, map, "./order/saleorder/viewAfterSalesDetail.do?afterSalesId="+new_afterSales.getAfterSalesId());//
							}
						}
					}
				}
			}
			return result;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}
	
	/**
	 * <b>Description:</b><br> 关闭售后订单
	 * @param afterSales
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月23日 上午10:16:45
	 */
	@Override
	public ResultInfo<?> saveCloseAfterSales(AfterSalesVo afterSales, User user) {
		String url = httpUrl + ErpConst.SAVE_AFTERSALES_CLOSE;
		afterSales.setAtferSalesStatus(3);//关闭
		afterSales.setCompanyId(user.getCompanyId());
		afterSales.setModTime(DateUtil.sysTimeMillis());
		afterSales.setUpdater(user.getUserId());
		//设置关闭人Id
		afterSales.setAfterSalesStatusUser(user.getUserId());
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, afterSales, clientId, clientKey, TypeRef2);
			if(result != null && result.getCode().equals(0)){
				//更新售后updatatime
				updateAfterOrderDataUpdateTime(afterSales.getAfterSalesId(),null,OrderDataUpdateConstant.AFTER_ORDER_CLOSE);
			}
			return result;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}
	/**
	 * <b>Description:</b><br> 保存售后的退换货手续费
	 * @param afterSales
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月23日 上午11:38:35
	 */
	@Override
	public ResultInfo<?> saveEditRefundFee(AfterSalesVo afterSales, User user) {
		String url = httpUrl + ErpConst.SAVE_AFTERSALES_REFUNDFEE;
		afterSales.setModTime(DateUtil.sysTimeMillis());
		afterSales.setUpdater(user.getUserId());
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, afterSales, clientId, clientKey, TypeRef2);
			return result;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}
	/**
	 * <b>Description:</b><br> 保存编辑的退票信息
	 * @param afterSales
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月23日 上午11:38:35
	 */
	@Override
	public ResultInfo<?> saveEditRefundTicket(AfterSalesInvoice afterSalesInvoice) {
		String url = httpUrl + ErpConst.SAVE_AFTERSALES_REFUNDTICKET;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, afterSalesInvoice, clientId, clientKey, TypeRef2);
			return result;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}

	/**
	 * <b>Description:</b><br> 保存新增或更新的售后过程
	 * @param afterSalesInvoice
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月23日 下午5:35:59
	 */
	@Override
	public ResultInfo<?> saveAfterSalesRecord(AfterSalesRecord afterSalesRecord, User user) {
		String url = httpUrl + ErpConst.SAVE_AFTERSALES_RECORD;
		if(ObjectUtils.isEmpty(afterSalesRecord.getAfterSalesRecordId())){
			afterSalesRecord.setAddTime(DateUtil.sysTimeMillis());
			afterSalesRecord.setCreator(user.getUserId());
		}
		afterSalesRecord.setModTime(DateUtil.sysTimeMillis());
		afterSalesRecord.setUpdater(user.getUserId());
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Integer>> TypeRef2 = new TypeReference<ResultInfo<Integer>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, afterSalesRecord, clientId, clientKey, TypeRef2);
			return result;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}
	/**
	 * <b>Description:</b><br> 查询新增产品与工程师页面所需数据
	 * @param afterSalesInstallstion
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月24日 下午3:23:15
	 */
	@Override
	public List<AfterSalesGoodsVo> getAfterSalesInstallstionVoByParam(AfterSalesVo afterSales) {
		String url = httpUrl + ErpConst.GET_ENIGNEER_INSTALLSTION;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, afterSales, clientId, clientKey, TypeRef2);
			if(result == null || result.getCode() ==-1){
				return null;
			}
			JSONArray json = JSONArray.fromObject(result.getData());
			@SuppressWarnings("unchecked")
			List<AfterSalesGoodsVo> list = (List<AfterSalesGoodsVo>) JSONArray.toCollection(json, AfterSalesGoodsVo.class);
			return list;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}
	/**
	 * <b>Description:</b><br> 保存编辑的安调信息
	 * @param afterSalesInvoice
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月23日 下午5:35:59
	 */
	@Override
	public ResultInfo<?> saveEditInstallstion(AfterSalesDetail afterSalesDetail) {
		String url = httpUrl + ErpConst.SAVE_AFTERSALES_INSTALLSTION;
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, afterSalesDetail, clientId, clientKey, TypeRef2);
			return result;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}
	/**
	 * <b>Description:</b><br> 查询工程师分页信息
	 * @param afterSalesInstallstion
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月24日 下午3:23:15
	 */
	@Override
	public Map<String, Object> getEngineerPage(AfterSalesVo afterSales, Page page) {
		String url = httpUrl + ErpConst.GET_ENIGNEER_PAGE;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, afterSales, clientId, clientKey, TypeRef2,
					page);
			if(result == null || result.getCode() ==-1){
				return null;
			}
			JSONArray json = JSONArray.fromObject(result.getData());
			@SuppressWarnings("unchecked")
			List<EngineerVo> list = (List<EngineerVo>) JSONArray.toCollection(json, EngineerVo.class);
			if(list.size() > 0){
				for (EngineerVo ev : list) {
					ev.setAreaStr(getAddressByAreaId(ev.getAreaId()));
				}
			}
			page = result.getPage();
			Map<String, Object> map = new HashMap<>();
			map.put("list", list);
			map.put("page", page);
			return map;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}
	/**
	 * <b>Description:</b><br> 保存新增或编辑的工程师与售后产品的关系
	 * @param afterSalesInstallstionVo
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月27日 下午5:57:28
	 */
	@Override
	public ResultInfo<?> saveAfterSalesEngineer(AfterSalesInstallstionVo afterSalesInstallstionVo,User user) {
		if(ObjectUtils.isEmpty(afterSalesInstallstionVo.getAfterSalesInstallstionId())){
			afterSalesInstallstionVo.setAddTime(DateUtil.sysTimeMillis());
			afterSalesInstallstionVo.setCreator(user.getUserId());
		}
		afterSalesInstallstionVo.setModTime(DateUtil.sysTimeMillis());
		afterSalesInstallstionVo.setUpdater(user.getUserId());
		String url = httpUrl + ErpConst.SAVE_AFTERSALES_ENGINEER;
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, afterSalesInstallstionVo, clientId, clientKey, TypeRef2);
			return result;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}
	/**
	 * <b>Description:</b><br> 获取编辑工程师与售后产品的信息
	 * @param afterSalesInstallstionVo
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月31日 下午1:01:03
	 */
	@Override
	public AfterSalesInstallstionVo getAfterSalesInstallstionVo(AfterSalesInstallstionVo afterSalesInstallstionVo) {
		String url = httpUrl + ErpConst.EDIT_ENIGNEER_INSTALLSTION;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, afterSalesInstallstionVo, clientId, clientKey, TypeRef2);
			if(result == null || result.getCode() ==-1){
				return null;
			}
			JSONObject json = JSONObject.fromObject(result.getData());
			//AfterSalesInstallstionVo asiv =  (AfterSalesInstallstionVo) JSONObject.toBean(json, AfterSalesInstallstionVo.class);
			AfterSalesInstallstionVo asiv = JsonUtils.readValue(json.toString(), AfterSalesInstallstionVo.class);
			if(asiv != null && asiv.getRiInstallstionJGoodList() != null){
				for (RInstallstionJGoods ri : asiv.getRiInstallstionJGoodList()) {
					
				}
			}
			
			return asiv;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}
	/**
	 * <b>Description:</b><br> 查询客户的联系人和财务信息
	 * @param traderVo
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年11月3日 下午1:08:22
	 */
	@Override
	public Map<String, Object> getCustomerContactAndFinace(TraderVo traderVo) {
		String url = httpUrl + ErpConst.GET_CUSTOMER_CONTACTANDFINCAE;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, traderVo, clientId, clientKey, TypeRef2);
			if(result == null || result.getCode() ==-1){
				return null;
			}
			return (Map<String, Object>) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}
	/**
	 * <b>Description:</b><br> 获取售后安调维修申请付款页面的信息--工程师和安调维修费用
	 * @param afterSalesVo
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年11月7日 下午1:57:19
	 */
	@Override
	public AfterSalesVo getAfterSalesApplyPay(AfterSalesVo afterSalesVo) {
		String url = httpUrl + ErpConst.GET_AFTERSALES_APPLYPAY;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, afterSalesVo, clientId, clientKey, TypeRef2);
			if(result == null || result.getCode() ==-1){
				return null;
			}
			JSONObject json = JSONObject.fromObject(result.getData());
			AfterSalesVo as = JsonUtils.readValue(json.toString(), AfterSalesVo.class);
			return as;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}
	/**
	 * <b>Description:</b><br> 保存申请付款
	 * @param afterSalesVo
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月11日 下午4:28:14
	 */
	@Override
	public ResultInfo<?> saveApplyPay(PayApplyVo payApplyVo, User user) {
		payApplyVo.setAddTime(DateUtil.sysTimeMillis());
		payApplyVo.setCreator(user.getUserId());
		payApplyVo.setModTime(DateUtil.sysTimeMillis());
		payApplyVo.setUpdater(user.getUserId());
		payApplyVo.setCompanyId(user.getCompanyId());
		payApplyVo.setPayType(518);//售后
		String url = httpUrl + ErpConst.SAVE_AFTERSALES_APPLYPAY;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, payApplyVo, clientId, clientKey, TypeRef2);
			return result;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}
	/**
	 * <b>Description:</b><br> 获取售后过程
	 * @param afterSalesVo
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月11日 下午4:28:14
	 */
	@Override
	public AfterSalesRecord getAfterSalesRecord(AfterSalesRecord afterSalesRecord) {
		String url = httpUrl + ErpConst.GET_AFTERSALES_RECORD;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, afterSalesRecord, clientId, clientKey, TypeRef2);
			if(result == null || result.getCode() == -1){
				return null;
			}
			JSONObject json = JSONObject.fromObject(result.getData());
			AfterSalesRecord as = JsonUtils.readValue(json.toString(), AfterSalesRecord.class);
			return as;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}

	@Override
	public AfterSales selectById(AfterSales as) {
		String url = httpUrl + "aftersales/order/selectbyid.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, as, clientId, clientKey, TypeRef2);
			if(result == null || result.getCode() ==-1){
				return null;
			}
			JSONObject json = JSONObject.fromObject(result.getData());
			AfterSales sa = JsonUtils.readValue(json.toString(), AfterSales.class);
			return sa;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}
	
	/**
	 * <b>Description:</b><br> 执行退款运算操作
	 * @param afterSalesVo
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月11日 下午4:28:14
	 */
	@Override
	public ResultInfo<?> executeRefundOperation(AfterSalesVo afterSalesVo, User user) {
		if(afterSalesVo != null && ObjectUtils.notEmpty(afterSalesVo.getTraderId())){
			RTraderJUser rTraderJUser = new RTraderJUser();
			rTraderJUser.setTraderId(afterSalesVo.getTraderId());
			rTraderJUser.setTraderType(1);
			List<RTraderJUser> users = rTraderJUserMapper.getUserTrader(rTraderJUser);
			if(users != null && users.size() > 0){
				afterSalesVo.setUserId(users.get(0).getUserId());
				Organization org = orgService.getOrgNameByUserId(users.get(0).getUserId());
				if(org != null){
					afterSalesVo.setOrgId(org.getOrgId());
					afterSalesVo.setOrgName(org.getOrgName());
				}
			}
		}
		afterSalesVo.setUpdater(user.getUserId());
		afterSalesVo.setModTime(DateUtil.sysTimeMillis());
		String url = httpUrl + ErpConst.EXECUTE_REFUND_OPERATION;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, afterSalesVo, clientId, clientKey, TypeRef2);
			if(result != null && result.getCode().equals(0)){
				//更新售后updataTime
				updateAfterOrderDataUpdateTime(afterSalesVo.getAfterSalesId(),null,OrderDataUpdateConstant.AFTER_ORDER_REFUND);
			}
			return result;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}
	/**
	 * <b>Description:</b><br> 保存退货的申请付款
	 * @param afterSalesVo
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月11日 下午4:28:14
	 */
	@Override
	public ResultInfo<?> saveRefundApplyPay(PayApplyVo payApplyVo, User user) {
		payApplyVo.setAddTime(DateUtil.sysTimeMillis());
		payApplyVo.setCreator(user.getUserId());
		payApplyVo.setModTime(DateUtil.sysTimeMillis());
		payApplyVo.setUpdater(user.getUserId());
		payApplyVo.setCompanyId(user.getCompanyId());
		payApplyVo.setPayType(518);//售后
		String url = httpUrl + ErpConst.SAVE_RUEFUND_APPLY_PAY;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, payApplyVo, clientId, clientKey, TypeRef2);
			return result;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}

	@Override
	public ResultInfo sendInstallstionSms(AfterSalesInstallstionVo afterSalesInstallstionVo, User user) {
		ResultInfo resultInfo = new ResultInfo<>();
		//获取安调信息
		String url = httpUrl + "aftersalesinstallstion/getinstallstioninfo.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<AfterSalesInstallstionVo>> TypeRef2 = new TypeReference<ResultInfo<AfterSalesInstallstionVo>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, afterSalesInstallstionVo, clientId, clientKey, TypeRef2);
			if(null != result){
				AfterSalesInstallstionVo installstionInfo = (AfterSalesInstallstionVo) result.getData();
				if(null != installstionInfo){
					String afterSalesNo = installstionInfo.getAfterSalesNo();
					Integer type = installstionInfo.getType();
					String traderContactName = installstionInfo.getTraderContactName();
					String traderContactMobile = installstionInfo.getTraderContactMobile();
					
					String name = installstionInfo.getName();
					String mobile = installstionInfo.getMobile();
					String typeName = "";
					switch (type) {
					case 541://安调
						typeName = "安调";
						break;
					case 550://安调
						typeName = "安调";
						break;
					case 584://维修
						typeName = "维修";
						break;
					case 585://维修
						typeName = "维修";
						break;
					default:
						break;
					}
					
					//发个工程师
					String terminal = afterSalesInstallstionVo.getTerminal();
					String equipment = afterSalesInstallstionVo.getEquipment();
					String comments = afterSalesInstallstionVo.getComments();
					if(null != mobile && !mobile.equals("")
							&& null != afterSalesNo && !afterSalesNo.equals("")
							&& null != typeName && !typeName.equals("")
							&& null != traderContactName && !traderContactName.equals("")
							&& null != traderContactMobile && !traderContactMobile.equals("")
							&& null != terminal && !terminal.equals("")
							&& null != equipment && !equipment.equals("")
							&& null != comments && !comments.equals("")
							){
						
						String content = "@1@="+afterSalesNo+",@2@="+typeName+",@3@="+traderContactName+",@4@="+traderContactMobile+",@5@="+terminal+",@6@="+equipment+",@7@="+comments;
						Boolean sendTplSms = SmsUtil.sendTplSms(mobile, "JSM40187-0016", content);
						if(!sendTplSms){
							resultInfo.setMessage("发送给工程师短信失败");
							return resultInfo;
						}
					}
					
					//发给客户
					if(null != traderContactMobile && !traderContactMobile.equals("")
							&& null != afterSalesNo && !afterSalesNo.equals("")
							&& null != typeName && !typeName.equals("")
							&& null != name && !name.equals("")
							&& null != mobile && !mobile.equals("")
							){
						String traderContent = "@1@="+afterSalesNo+",@2@="+typeName+",@3@="+name+",@4@="+mobile;
						Boolean sendTplSms = SmsUtil.sendTplSms(traderContactMobile, "JSM40187-0017", traderContent);
						if(!sendTplSms){
							resultInfo.setMessage("发送给客户短信失败");
							return resultInfo;
						}
					}
					
					AfterSalesInstallstion asiv = new AfterSalesInstallstion();
					asiv.setAfterSalesInstallstionId(afterSalesInstallstionVo.getAfterSalesInstallstionId());
					Long time = DateUtil.sysTimeMillis();
					asiv.setLastNoticeTime(time);
					asiv.setModTime(time);
					asiv.setUpdater(user.getUserId());
					asiv.setNoticeTimes(afterSalesInstallstionVo.getNoticeTimes() + 1);
					String url2 = httpUrl + ErpConst.SAVE_UPDATE_INSTALLSTION;
					
					// 定义反序列化 数据格式
					final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
					};
					ResultInfo<?> res = (ResultInfo<?>) HttpClientUtils.post(url2, asiv, clientId, clientKey, TypeRef);
					resultInfo.setCode(0);
					resultInfo.setMessage("操作成功");
				}
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		
		return resultInfo;
	}
	
	/**
	 * <b>Description:</b><br> 保存售后开票申请
	 * @param invoiceApply
	 * @return
	 * @throws Exception 
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年8月28日 下午2:08:31
	 */
	@Override
	public ResultInfo<?> saveOpenInvoceApply(InvoiceApply invoiceApply) {
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
			String url=httpUrl + "aftersales/order/saveopeninvoceapply.htm";
			ResultInfo<?> result = (ResultInfo<?>)HttpClientUtils.post(url, invoiceApply,clientId,clientKey, TypeRef);
			if(result!=null){
				return result;
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			return new ResultInfo<>(-1, "操作异常");
		}
		return new ResultInfo<>(-1, "操作失败");
	}
	
	/**
	 * <b>Description:</b><br> 售后直发产品确认全部收货
	 * @param invoiceApply
	 * @return
	 * @throws Exception 
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年8月28日 下午2:08:31
	 */
	@Override
	public ResultInfo<?> updateAfterSalesGoodsArrival(AfterSalesGoods afterSalesGoods) {
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
			String url=httpUrl + "aftersales/order/updateaftersalesgoodsarrival.htm";
			ResultInfo<?> result = (ResultInfo<?>)HttpClientUtils.post(url, afterSalesGoods,clientId,clientKey, TypeRef);
			if(result!=null){
				return result;
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			return new ResultInfo<>(-1, "操作异常");
		}
		return new ResultInfo<>(-1, "操作失败");
	}

	/**
	 * <b>Description:</b><br> 查询售后服务费信息
	 * @param afterSalesDetail
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年4月16日 上午11:04:41
	 */
	@Override
	public AfterSalesDetailVo getAfterSalesDetailVoByParam(AfterSalesDetail afterSalesDetail) {
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
			String url=httpUrl + "aftersales/order/getaftersalesorderdetailvo.htm";
			ResultInfo<?> result = (ResultInfo<?>)HttpClientUtils.post(url, afterSalesDetail,clientId,clientKey, TypeRef);
			if(result == null  || result.getCode() == -1){
				return null;
			}
			JSONObject json = JSONObject.fromObject(result.getData());
			AfterSalesDetailVo sa = JsonUtils.readValue(json.toString(), AfterSalesDetailVo.class);
			if(sa != null){
				List<TraderAddress> taList = sa.getTaList();
				if(taList != null && taList.size() > 0){
					List<TraderAddressVo> list = new ArrayList<>();
					TraderAddressVo tav = null;
					for (TraderAddress ta : taList) {
						tav = new TraderAddressVo();
						tav.setTraderAddress(ta);
						tav.setArea(getAddressByAreaId(ta.getAreaId()));
						list.add(tav);
					}
					sa.setTavList(list);
				}
			}
			
			return sa;
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}

	@Override
	public AfterSalesDetail selectadtById(AfterSales as) {
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
			String url=httpUrl + "aftersales/order/selectadtbyid.htm";
			ResultInfo<?> result = (ResultInfo<?>)HttpClientUtils.post(url, as,clientId,clientKey, TypeRef);
			if(result == null  || result.getCode() == -1){
				return null;
			}
			JSONObject json = JSONObject.fromObject(result.getData());
			AfterSalesDetail sa = JsonUtils.readValue(json.toString(), AfterSalesDetail.class);
			return sa;
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}

	@Override
	public List<AfterSalesGoods> getAfterSalesGoodsNoOutList(Integer afterSalesId) {
		String url = httpUrl + "aftersales/order/getaftersalesgoodsnooutlist.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<AfterSalesGoods>>> TypeRef2 = new TypeReference<ResultInfo<List<AfterSalesGoods>>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, afterSalesId, clientId, clientKey, TypeRef2);
			if(result == null || result.getCode() ==-1){
				return null;
			}
			JSONArray json = JSONArray.fromObject(result.getData());
			@SuppressWarnings("unchecked")
			List<AfterSalesGoods> list = (List<AfterSalesGoods>) JSONArray.toCollection(json, AfterSalesGoods.class);
			return list;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}
	
	/**
	 * 
	 * <b>Description:</b>获取费用类型(废弃)<br> 
	 * @param 
	 * @return
	 * @Note
	 * <b>Author:</b> Barry
	 * <br><b>Date:</b> 2018年07月24日 19:53
	 */
	@Override
	public List<CostType> getCostTypeById(Integer costType){
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<List<CostType>>> TypeRef = new TypeReference<ResultInfo<List<CostType>>>() {};
			String url=httpUrl + "aftersales/order/getCostTypeById.htm";
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, costType,clientId,clientKey, TypeRef);
			if(result == null  || result.getCode() == -1){
				return null;
			}
			List<CostType> costTypes = (List<CostType>)result.getData();
			return costTypes;
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}

	@Override
	public List<AfterSalesCostVo> getAfterSalesCostListById(AfterSalesCost afterSalesCost) {
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<List<AfterSalesCostVo>>> TypeRef = new TypeReference<ResultInfo<List<AfterSalesCostVo>>>() {};
			String url=httpUrl + "aftersales/order/getAfterSalesCostListById.htm";
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, afterSalesCost,clientId,clientKey, TypeRef);
			if(result == null  || result.getCode() == -1){
				return null;
			}
			@SuppressWarnings("unchecked")
			List<AfterSalesCostVo> costList = (List<AfterSalesCostVo>)result.getData();
			for(AfterSalesCostVo cost:costList){
				//根据最后编辑人Id查询用户名字
				cost.setUserName(userMapper.selectByPrimaryKey(cost.getUpdater()).getUsername());
			}
			return  costList;
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}

	@Override
	public ResultInfo addAfterSalesCost(AfterSalesCost afterSalesCost) {
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
			String url=httpUrl + "aftersales/order/addAfterSalesCost.htm";
			ResultInfo<?> result = (ResultInfo<?>)HttpClientUtils.post(url, afterSalesCost,clientId,clientKey, TypeRef);
			return result;
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
		
	}

	@Override
	public ResultInfo deleteAfterSalesCostById(AfterSalesCost afterSalesCost) {
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
			String url=httpUrl + "aftersales/order/deleteAfterSalesCostById.htm";
			ResultInfo<?> result = (ResultInfo<?>)HttpClientUtils.post(url, afterSalesCost,clientId,clientKey, TypeRef);
			return result;
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
		
	}

	@Override
	public ResultInfo updateAfterSalesCostById(AfterSalesCost afterSalesCost) {
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
			String url=httpUrl + "aftersales/order/updateAfterSalesCostById.htm";
			ResultInfo<?> result = (ResultInfo<?>)HttpClientUtils.post(url, afterSalesCost,clientId,clientKey, TypeRef);
			return result;
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}
	
	@Override
	public AfterSalesCostVo getAfterSalesCostBycostId(AfterSalesCost afterSalesCost) {
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
			String url=httpUrl + "aftersales/order/getAfterSalesCostBycostId.htm";
			ResultInfo<?> result = (ResultInfo<?>)HttpClientUtils.post(url, afterSalesCost,clientId,clientKey, TypeRef);
			if(result == null  || result.getCode() == -1){
				return null;
			}
			JSONObject json = JSONObject.fromObject(result.getData());
			AfterSalesCostVo costVo = JsonUtils.readValue(json.toString(), AfterSalesCostVo.class);			
			return costVo;
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}

	/**
	 * <b>Description:</b><br>新增或编辑售后对象 
	 * @param afterSalesTrader
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年7月31日 下午6:12:18
	 */
	@Override
	public ResultInfo saveOrUpdateAfterTradder(AfterSalesTrader afterSalesTrader) {
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
			String url=httpUrl + "aftersales/order/saveorupdateaftertrader.htm";
			ResultInfo<?> result = (ResultInfo<?>)HttpClientUtils.post(url, afterSalesTrader,clientId,clientKey, TypeRef);
			return result;
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}
	
	/**
	 * <b>Description:</b><br> 查询售后对象
	 * @param afterSalesTrader
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年8月1日 上午8:50:16
	 */
	@Override
	public AfterSalesTrader getAfterSalesTrader(AfterSalesTrader afterSalesTrader) {
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<AfterSalesTrader>> TypeRef = new TypeReference<ResultInfo<AfterSalesTrader>>() {};
			String url=httpUrl + "aftersales/order/getaftertrader.htm";
			@SuppressWarnings("unchecked")
			ResultInfo<AfterSalesTrader> result = (ResultInfo<AfterSalesTrader>)HttpClientUtils.post(url, afterSalesTrader,clientId,clientKey, TypeRef);
			return result.getData();
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}

	/**
	 * 
	 * <b>Description: 获取售后单对应的售后对象列表</b><br> 
	 * @param 
	 * @return
	 * @Note
	 * <b>Author:</b> Barry
	 * <br><b>Date:</b> 2018年08月09日 09:32
	 */
	@Override
	public List<AfterSalesTrader> getAfterSalesTraderList(Integer afterSalesId) {
		// TODO 获取售后单对应的售后对象列表
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<List<AfterSalesTrader>>> TypeRef = new TypeReference<ResultInfo<List<AfterSalesTrader>>>() {};
			String url=httpUrl + "aftersales/order/getaftertraderlist.htm";
			ResultInfo<?> result = (ResultInfo<?>)HttpClientUtils.post(url, afterSalesId,clientId,clientKey, TypeRef);
			@SuppressWarnings("unchecked")
			List<AfterSalesTrader> list = (List<AfterSalesTrader>) result.getData();
			return list;
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}
	
	
	@Override
	public ResultInfo<?> updateAfterSalesById(AfterSalesVo afterSales) {
		String url=httpUrl + "aftersales/order/updateaftersalesbyid.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<AfterSalesVo>> TypeRef2 = new TypeReference<ResultInfo<AfterSalesVo>>() {};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, afterSales, clientId, clientKey, TypeRef2);
			return result;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return new ResultInfo<>();
		}
	}

	/**
	 * <b>Description:</b><br> 销售执行退款前验证财务是否已确认退票
	 * @param afterSalesVo
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年9月4日 下午2:37:34
	 */
	@Override
	public ResultInfo<?> isAllReturnTicket(AfterSalesVo afterSalesVo) {
		String url=httpUrl + "aftersales/order/isallreturnticket.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, afterSalesVo, clientId, clientKey, TypeRef2);
			return result;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return new ResultInfo<>();
		}
	}

	@Override
	public void getNoLockSaleorderGoodsVo(AfterSalesVo afterSalesVo) {
		String url=httpUrl + "aftersales/order/outofSaleorderGoodsLock.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, afterSalesVo, clientId, clientKey, TypeRef2);
			
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
	}

	/** @description: getRefundBalanceAmountBySaleorderId.
	 * @notes: add by Tomcat.Hui 2019/9/5 10:44 .Desc: VDERP-1053.
	 * @author: Tomcat.Hui.
	 * @date: 2019/9/5 11:45.
	 * @param saleorderId
	 * @return: java.math.BigDecimal.
	 * @throws: .
	 */
	@Override
	public BigDecimal getRefundBalanceAmountBySaleorderId(Integer saleorderId) {
		return afterSalesMapper.getRefundBalanceAmountBySaleorderId(saleorderId);
	}
	@Override
	public Map<String, Object> searchAfterOrder(AfterSalesVo afterSalesVo, Page page) {
		Map<String, Object> map = new HashMap<>();
		map.put("page", page);
		map.put("afterSalesVo",afterSalesVo);
		List<AfterSalesVo> list = afterSalesMapper.searchAfterOrderListPage(map);
		WarehouseGoodsOperateLog wl = new WarehouseGoodsOperateLog();
		WarehouseGoodsStatus warehouseGoodsStatus = new WarehouseGoodsStatus();
		for (AfterSalesVo asgv : list) {
			wl.setRelatedId(asgv.getAfterSalesGoodsId());
			wl.setOperateType(3);//销售换货入库
			Integer exchangeReturnNum =  warehouseGoodsOperateLogMapper.getAftersalesGoodsSum(wl);
			if(warehouseGoodsOperateLogMapper.getAftersalesGoodsSum(wl)==null) {
				exchangeReturnNum = 0;
			}
			asgv.setExchangeReturnNum(exchangeReturnNum);//已退回数量
			wl.setOperateType(4);//销售换货出库
			Integer exchangeDeliverNum =  warehouseGoodsOperateLogMapper.getAftersalesGoodsSum(wl);
			if(warehouseGoodsOperateLogMapper.getAftersalesGoodsSum(wl)==null) {
				exchangeDeliverNum = 0;
			}
			if(asgv.getAfterGoodsNum()>exchangeDeliverNum) {
				asgv.seteFlag("1");
			}
			asgv.setExchangeDeliverNum(exchangeDeliverNum);//已重发数量
			warehouseGoodsStatus.setCompanyId(afterSalesVo.getCompanyId());
			warehouseGoodsStatus.setGoodsId(asgv.getGoodsId());
			Integer goodsStockNum = warehouseGoodsStatusMapper.getGoodsStock(warehouseGoodsStatus);//库存量
			Integer goodsOccupyNum = saleorderMapper.getGoodsOccupyNum(asgv.getGoodsId());//暂用数量
			Integer canUseGoodsStock = goodsStockNum- goodsOccupyNum;//可用库存
			asgv.setGoodsStockNum(goodsStockNum);
			asgv.setCanUseGoodsStock(canUseGoodsStock);
		}
		map.put("list",list);
		return map;
	}

	@Override
	public List<AfterSalesGoods> getAfterSalesGoodsNoOutNumList(Integer afterSalesId) {
		List<AfterSalesGoods> list  = afterSalesGoodsMapper.getAfterSalesGoodsNoOutNumList(afterSalesId);
		return list;
	}

	@Override
	public List<AfterSales> getAfterSaleListById(AfterSales afterSales) {

		return afterSalesMapper.getAfterSaleListById(afterSales);
	}

	@Override
	public void notifyStatusToMjx(Integer saleorderId,Integer afterSalesId){
		// add by Tomcat.Hui 2020/3/6 1:31 下午 .Desc: VDERP-2057 BD订单流程优化-ERP部分  售后产生的付款状态变更需要通知mjx. start
		try {
			Saleorder sv = saleorderMapper.getSaleOrderById(saleorderId);
			//BD订单
			if (sv.getOrderType().equals(1)) {
				AfterSales afterSales = afterSalesMapper.getAfterSalesById(afterSalesId);

				//售后单已完结
				if (null != afterSales && null != afterSales.getAtferSalesStatus() && afterSales.getAtferSalesStatus().equals(2)) {
					OrderData orderData = new OrderData();
					orderData.setOrderNo(sv.getSaleorderNo());
					WebAccount web = webAccountMapper.getWenAccountInfoByMobile(sv.getCreateMobile());
					orderData.setAccountId(web.getWebAccountId());
					orderData.setGoodsList(new ArrayList<>());
					if (sv.getPaymentStatus() == 2) {
						orderData.setOrderStatus(3);//发货
					} else if (sv.getPaymentStatus() == 1) {
						orderData.setOrderStatus(7);
					} else if (sv.getPaymentStatus() == 0) {
						orderData.setOrderStatus(2);
					}
					String url = mjxurl + "/order/updateOrderConfirmFinish";
					logger.info("通知mjx订单付款状态请求信息：" + JSONObject.fromObject(orderData));
					JSONObject result2 = NewHttpClientUtils.httpPost(url, JSONObject.fromObject(orderData));
					String r = result2.toString();
					logger.info("通知mjx订单付款状态返回结果：" + r);
				}
			}
		}catch(Exception e){
			logger.error("通知mjx订单付款状态变更出现异常：" ,e);
		}

		// add by Tomcat.Hui 2020/3/6 1:31 下午 .Desc: VDERP-2057 BD订单流程优化-ERP部分  售后产生的付款状态变更需要通知mjx. end
	}


	@Override
	public AfterSalesDetail getAfterSalesDetailByAfterSaleDetailId(Integer afterSalesDetailId){
		return afterSalesDetailMapper.selectByPrimaryKey(afterSalesDetailId);
	}

	@Override
	public AfterSales getAfterSalesById(Integer afterSalesId) {
		return afterSalesMapper.getAfterSalesById(afterSalesId);
	}
}
