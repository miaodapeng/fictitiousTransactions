package com.vedeng.order.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.common.constants.Contant;
import com.vedeng.common.constant.*;
import com.vedeng.common.constant.CommonConstants;
import com.vedeng.common.constant.OrderDataUpdateConstant;
import com.vedeng.common.http.HttpClientUtils4Stock;
import com.vedeng.common.model.ResultInfo4Stock;
import com.vedeng.finance.dao.InvoiceMapper;
import com.vedeng.finance.model.Invoice;
import com.vedeng.finance.service.InvoiceService;
import com.vedeng.firstengage.dao.FirstEngageMapper;
import com.vedeng.firstengage.model.SimpleMedicalCategory;
import com.vedeng.common.constant.OrderConstant;
import com.vedeng.goods.model.CoreSpuGenerate;
import com.vedeng.logistics.dao.ExpressMapper;
import com.vedeng.logistics.model.Express;
import com.vedeng.logistics.model.ExpressDetail;
import com.vedeng.logistics.service.ExpressService;
import com.vedeng.logistics.service.WarehouseStockService;
import com.vedeng.order.model.*;
import com.vedeng.system.model.VerifiesInfo;
import com.vedeng.trader.dao.*;
import com.vedeng.trader.model.*;
import com.vedeng.trader.model.vo.TraderMedicalCategoryVo;
import com.xxl.job.core.log.XxlJobLogger;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.collections.CollectionUtils;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.fasterxml.jackson.core.type.TypeReference;
import com.github.pagehelper.util.StringUtil;
import com.vedeng.activiti.service.ActionProcdefService;
import com.vedeng.authorization.dao.UserMapper;
import com.vedeng.authorization.model.User;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.http.NewHttpClientUtils;
import com.vedeng.common.model.ReqVo;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.HttpRestClientUtil;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.goods.dao.GoodsSettlementPriceMapper;
import com.vedeng.goods.dao.RCategoryJUserMapper;
import com.vedeng.goods.model.Goods;
import com.vedeng.goods.model.GoodsSettlementPrice;
import com.vedeng.goods.service.GoodsChannelPriceService;
import com.vedeng.goods.service.GoodsService;
import com.vedeng.logistics.model.SaleorderWarehouseComments;
import com.vedeng.order.dao.QuoteorderMapper;
import com.vedeng.order.dao.SaleorderGoodsMapper;
import com.vedeng.order.dao.SaleorderMapper;
import com.vedeng.order.model.vo.OrderData;
import com.vedeng.order.model.vo.OrderGoodsData;
import com.vedeng.order.model.vo.SaleorderGoodsVo;
import com.vedeng.order.model.vo.SaleorderGoodsWarrantyVo;
import com.vedeng.order.model.vo.SaleorderVo;
import com.vedeng.order.model.vo.UserData;
import com.vedeng.order.service.QuoteService;
import com.vedeng.order.service.SaleorderService;
import com.vedeng.system.model.Attachment;
import com.vedeng.system.service.RegionService;
import com.vedeng.system.service.UserService;
import com.vedeng.system.service.VerifiesRecordService;
import com.vedeng.trader.model.vo.TraderAddressVo;
import com.vedeng.trader.model.vo.TraderCustomerVo;
import com.vedeng.trader.service.TraderCustomerService;

import net.sf.json.JSONObject;

@Service("saleorderService")
public class SaleorderServiceImpl extends BaseServiceimpl implements SaleorderService {

	Logger logger= LoggerFactory.getLogger(SaleorderServiceImpl.class);

	@Autowired
	@Qualifier("communicateRecordMapper")
	private CommunicateRecordMapper communicateRecordMapper;

	@Autowired
	@Qualifier("userMapper")
	private UserMapper userMapper;

	@Autowired
	@Qualifier("goodsSettlementPriceMapper")
	private GoodsSettlementPriceMapper goodsSettlementPriceMapper;

	@Autowired
	@Qualifier("goodsChannelPriceService")
	private GoodsChannelPriceService goodsChannelPriceService;

	@Autowired
	@Qualifier("traderCustomerService")
	private TraderCustomerService traderCustomerService;// 客户-交易者

	@Autowired
	@Qualifier("regionService")
	private RegionService regionService;// 自动注入regionService

	@Autowired
	@Qualifier("rCategoryJUserMapper")
	private RCategoryJUserMapper rCategoryJUserMapper;

	@Autowired
	@Qualifier("quoteService")
	private QuoteService quoteService;

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Autowired
	@Qualifier("saleorderMapper")
	private SaleorderMapper saleorderMapper;

	@Lazy
	@Autowired
	@Qualifier("goodsService")
	private GoodsService goodsService;

	@Autowired
	@Qualifier("traderMapper")
	private TraderMapper traderMapper;

	@Autowired
	@Qualifier("expressMapper")
	ExpressMapper expressMapper;

	/*@Autowired
	@Qualifier("regionMapper")
	private RegionMapper regionMapper;*/

	/*@Autowired
	@Qualifier("goodsMapper")
	private GoodsMapper goodsMapper;*/

	@Autowired
	@Qualifier("quoteorderMapper")
	private QuoteorderMapper  quoteorderMapper;

	@Autowired
	@Qualifier("verifiesRecordService")
	private VerifiesRecordService verifiesRecordService;

	@Autowired
	@Qualifier("actionProcdefService")
	protected ActionProcdefService actionProcdefService;

	@Autowired // 自动装载
	protected ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	@Autowired
	private WebAccountMapper webAccountMapper;

	@Autowired
	private InvoiceService invoiceService;

	@Autowired
	private SaleorderGoodsMapper saleorderGoodsMapper;

	@Autowired
	private WarehouseStockService warehouseStockService;

	@Autowired
	private InvoiceMapper invoiceMapper;

	@Autowired
	ExpressService expressService;

	@Autowired
	private TraderCertificateMapper traderCertificateMapper;

	@Autowired
	private TraderMedicalCategoryMapper traderMedicalCategoryMapper;

	@Autowired
	private TraderCustomerMapper traderCustomerMapper;

	@Autowired
	private FirstEngageMapper firstEngageMapper;
	@Override
	public ResultInfo checkGoodAptitude(Integer saleorderId) {
		try {
			Saleorder saleorder = saleorderMapper.getSaleOrderById(saleorderId);
			if (saleorder == null || saleorder.getTraderId() == null) {
				return new ResultInfo(-1, OrderGoodsAptitudeEnum.ORDER_IS_NULL.getMessage());
			}
			TraderCustomer traderCustomer = traderCustomerMapper.getBaseCustomerByTraderId(saleorder.getTraderId());
			if (traderCustomer == null || traderCustomer.getCustomerNature() == null || traderCustomer.getCustomerNature() == 0) {
				return new ResultInfo(-1, OrderGoodsAptitudeEnum.CUSTOMER_IS_NULL.getMessage());
			}
			List<TraderCertificate> certificates = traderCertificateMapper.getCertificateListByTraderId(saleorder.getTraderId());
			if (SysOptionConstant.ID_465.equals(traderCustomer.getCustomerNature())) {
				//处理分销
				int businessCertificateVaild = checkCertificateVaild(certificates, SysOptionConstant.ID_25);
				int secondCertificateValid = checkCertificateVaild(certificates, SysOptionConstant.ID_28);
				int thirdCertificateValid = checkCertificateVaild(certificates, SysOptionConstant.ID_29);
				if (businessCertificateVaild == OrderGoodsAptitudeConstants.CERTIFICATE_IS_NULL) {
					return new ResultInfo(-1, OrderGoodsAptitudeEnum.BUSINESS_CERTIFICATE_IS_NULL.getMessage());
				} else if (businessCertificateVaild == OrderGoodsAptitudeConstants.CERTIFICATE_DATE_OUT) {
					return new ResultInfo(-1, OrderGoodsAptitudeEnum.BUSINESS_CERTIFICATE_DATE_OUT.getMessage());
				}
				TraderMedicalCategory queryCategory = new TraderMedicalCategory();
				queryCategory.setTraderId(saleorder.getTraderId());
				queryCategory.setTraderType(1);
				List<TraderMedicalCategory> medicalCategories = traderMedicalCategoryMapper.getMedicalCategoryList(queryCategory);
				HashMap<Integer, HashSet<Integer>> categoryMap = getSetFromMedicalCategory(medicalCategories);
				List<SaleorderGoods> goods = saleorderGoodsMapper.getGoodsSkuByOrderId(saleorderId);
				if (!CollectionUtils.isEmpty(goods)) {
					for (SaleorderGoods g : goods) {
						if (g == null || StringUtil.isEmpty(g.getSku())) {
							continue;
						}
						SimpleMedicalCategory category = firstEngageMapper.getSimpleMedicalCategory(g.getSku());
						if (category == null) {
							return new ResultInfo(-1, OrderGoodsAptitudeEnum.CATEGORY_IS_NULL.getMessage());
						}
						if (OrderGoodsAptitudeConstants.GOODS_IS_INSTRUMENT.equals(category.getInstrumentType())) {
							//是医疗器械
							if (SysOptionConstant.ID_969.equals(category.getManageCategory())) {
								//二类
								if (secondCertificateValid == OrderGoodsAptitudeConstants.CERTIFICATE_IS_NULL) {
									return new ResultInfo(-1, OrderGoodsAptitudeEnum.SECOND_CERTIFICATE_IS_NULL.getMessage());
								} else if (secondCertificateValid == OrderGoodsAptitudeConstants.CERTIFICATE_DATE_OUT) {
									return new ResultInfo(-1, OrderGoodsAptitudeEnum.SECOND_CERTIFICATE_DATE_OUT.getMessage());
								}
								ResultInfo categoryResult = checkMedicalCategory(categoryMap.get(OrderGoodsAptitudeConstants.LEVEL_SECOND_OLD_MEDICAL_CATEGORY),
										category.getOldMedicalCategory(), SysOptionConstant.ID_969);
								if (categoryResult.getCode() == -1) {
									categoryResult = checkMedicalCategory(categoryMap.get(OrderGoodsAptitudeConstants.LEVEL_SECOND_NEW_MEDICAL_CATEGORY),
											category.getNewMedicalCategory(), SysOptionConstant.ID_969);
									if (categoryResult.getCode() == -1) {
										return categoryResult;
									}
								}

							} else if (SysOptionConstant.ID_970.equals(category.getManageCategory())) {
								//三类
								if (thirdCertificateValid == OrderGoodsAptitudeConstants.CERTIFICATE_IS_NULL) {
									return new ResultInfo(-1, OrderGoodsAptitudeEnum.THIRD_CERTIFICATE_IS_NULL.getMessage());
								} else if (thirdCertificateValid == OrderGoodsAptitudeConstants.CERTIFICATE_DATE_OUT) {
									return new ResultInfo(-1, OrderGoodsAptitudeEnum.THIRD_CERTIFICATE_DATE_OUT.getMessage());
								}
								ResultInfo categoryResult = checkMedicalCategory(categoryMap.get(OrderGoodsAptitudeConstants.LEVEL_THIRD_OLD_MEDICAL_CATEGORY)
										, category.getOldMedicalCategory(), SysOptionConstant.ID_970);
								if (categoryResult.getCode() == -1) {
									categoryResult = checkMedicalCategory(categoryMap.get(OrderGoodsAptitudeConstants.LEVEL_THIRD_NEW_MEDICAL_CATEGORY)
											, category.getNewMedicalCategory(), SysOptionConstant.ID_970);
									if (categoryResult.getCode() == -1) {
										return categoryResult;
									}
								}

							}
						}
					}
				}
			} else if (SysOptionConstant.ID_466.equals(traderCustomer.getCustomerNature())) {
				//处理终端
				int operateCertificateValid = checkCertificateVaild(certificates, SysOptionConstant.ID_438);
				int businessCertificateValid = checkCertificateVaild(certificates, SysOptionConstant.ID_25);
				if (OrderGoodsAptitudeConstants.TRADER_IS_PROFIT.equals(traderCustomer.getIsProfit())) {
					if (businessCertificateValid == OrderGoodsAptitudeConstants.CERTIFICATE_IS_NULL) {
						return new ResultInfo(-1, OrderGoodsAptitudeEnum.BUSINESS_CERTIFICATE_IS_NULL.getMessage());
					} else if (businessCertificateValid == OrderGoodsAptitudeConstants.CERTIFICATE_DATE_OUT) {
						return new ResultInfo(-1, OrderGoodsAptitudeEnum.BUSINESS_CERTIFICATE_DATE_OUT.getMessage());
					}
					if (operateCertificateValid == OrderGoodsAptitudeConstants.CERTIFICATE_IS_NULL) {
						return new ResultInfo(-1, OrderGoodsAptitudeEnum.OPERATE_CERTIFICATE_IS_NULL.getMessage());
					} else if (operateCertificateValid == OrderGoodsAptitudeConstants.CERTIFICATE_DATE_OUT) {
						return new ResultInfo(-1, OrderGoodsAptitudeEnum.OPERATE_CERTIFICATE_DATE_OUT.getMessage());
					}
				} else {
					if (operateCertificateValid == OrderGoodsAptitudeConstants.CERTIFICATE_IS_NULL) {
						return new ResultInfo(-1, OrderGoodsAptitudeEnum.OPERATE_CERTIFICATE_IS_NULL.getMessage());
					} else if (operateCertificateValid == OrderGoodsAptitudeConstants.CERTIFICATE_DATE_OUT) {
						return new ResultInfo(-1, OrderGoodsAptitudeEnum.OPERATE_CERTIFICATE_DATE_OUT.getMessage());
					}
				}

			}
			return new ResultInfo(0, "操作成功");
		}catch (Exception ex){
			logger.error("订单自动审核出现异常",ex);
			return new ResultInfo(-1,"自动审核失败");
		}
	}

	/**
	 * <b>Description:</b>检查医疗资质是否符合条件<br>
	 *
	 * @param
	 * @return
	 * @Note <b>Author:calvin</b>
	 * <br><b>Date:</b> 2020/3/17
	 */
	public ResultInfo checkMedicalCategory(HashSet categorySet, Integer categoryId, Integer type) {
		if (categorySet == null || categoryId == null) {
			if (SysOptionConstant.ID_969.equals(type)) {
				return new ResultInfo(-1, OrderGoodsAptitudeEnum.SECOND_MEDICAL_IS_INVALIED.getMessage());
			} else if (SysOptionConstant.ID_970.equals(type)) {
				return new ResultInfo(-1, OrderGoodsAptitudeEnum.THIRD_MEDICAL_IS_INVALIED.getMessage());
			}
		}
		if (!categorySet.contains(categoryId)) {
			if (SysOptionConstant.ID_969.equals(type)) {
				return new ResultInfo(-1, OrderGoodsAptitudeEnum.SECOND_MEDICAL_IS_INVALIED.getMessage());
			} else if (SysOptionConstant.ID_970.equals(type)) {
				return new ResultInfo(-1, OrderGoodsAptitudeEnum.THIRD_MEDICAL_IS_INVALIED.getMessage());
			}
		}
		return new ResultInfo(0, "验证有效");
	}

	/**
	 * <b>Description:</b>从列表中获取医疗资质分类的集合<br>
	 *
	 * @param
	 * @return
	 * @Note <b>Author:calvin</b>
	 * <br><b>Date:</b> 2020/3/17
	 */
	public HashMap<Integer,HashSet<Integer>> getSetFromMedicalCategory(List<TraderMedicalCategory> categoryVoList) {
		HashMap<Integer,HashSet<Integer>> resultMap=new HashMap<>();
		if (CollectionUtils.isEmpty(categoryVoList)) {
			return resultMap;
		}
		for (TraderMedicalCategory c : categoryVoList) {
			if (c == null || c.getMedicalCategoryId() == null||c.getMedicalCategoryLevel()==null) {
				continue;
			}
			if(OrderGoodsAptitudeConstants.LEVEL_SECOND_NEW_MEDICAL_CATEGORY.equals(c.getMedicalCategoryLevel())) {
				putMedicalCategory2Map(resultMap,c,OrderGoodsAptitudeConstants.LEVEL_SECOND_NEW_MEDICAL_CATEGORY);
			}else if(OrderGoodsAptitudeConstants.LEVEL_SECOND_OLD_MEDICAL_CATEGORY.equals(c.getMedicalCategoryLevel())){
				putMedicalCategory2Map(resultMap,c,OrderGoodsAptitudeConstants.LEVEL_SECOND_OLD_MEDICAL_CATEGORY);
			}else if(OrderGoodsAptitudeConstants.LEVEL_THIRD_NEW_MEDICAL_CATEGORY.equals(c.getMedicalCategoryLevel())){
				putMedicalCategory2Map(resultMap,c,OrderGoodsAptitudeConstants.LEVEL_THIRD_NEW_MEDICAL_CATEGORY);
			}else if(OrderGoodsAptitudeConstants.LEVEL_THIRD_OLD_MEDICAL_CATEGORY.equals(c.getMedicalCategoryLevel())){
				putMedicalCategory2Map(resultMap,c,OrderGoodsAptitudeConstants.LEVEL_THIRD_OLD_MEDICAL_CATEGORY);
			}
		}
		return resultMap;
	}

	/**
	 * <b>Description:</b>把分类结果装入字典中<br>
	 * @param
	 * @return
	 * @Note
	 * <b>Author:calvin</b>
	 * <br><b>Date:</b> 2020/3/19
	 */
	public void putMedicalCategory2Map(HashMap<Integer,HashSet<Integer>> resultMap,TraderMedicalCategory c,Integer type){
		if(!resultMap.containsKey(type)){
			HashSet<Integer> hashSet=new HashSet<>();
			resultMap.put(type,hashSet);
		}
		resultMap.get(type).add(c.getMedicalCategoryId());
	}
	/**
	 * <b>Description:</b>检查资质证书是否在有效期内，1代表缺失，2代表过期，3代表验证通过<br>
	 *
	 * @param
	 * @return
	 * @Note <b>Author:calvin</b>
	 * <br><b>Date:</b> 2020/3/17
	 */
	public int checkCertificateVaild(List<TraderCertificate> certificates, Integer type) {

		if (CollectionUtils.isEmpty(certificates)) {
			return OrderGoodsAptitudeConstants.CERTIFICATE_IS_NULL;
		}
		for (TraderCertificate certificate : certificates) {
			if (certificate == null || StringUtil.isEmpty(certificate.getUri()) || certificate.getSysOptionDefinitionId() == null) {
				continue;
			}
			if (type.equals(certificate.getSysOptionDefinitionId())) {
				if (certificate.getEndtime() == null || certificate.getEndtime() >= System.currentTimeMillis()) {
					return OrderGoodsAptitudeConstants.CERTIFICATE_VAILD;
				} else {
					return OrderGoodsAptitudeConstants.CERTIFICATE_DATE_OUT;
				}
			}
		}
		return OrderGoodsAptitudeConstants.CERTIFICATE_IS_NULL;
	}

	@Override
	public Map<String, Object> getSaleorderListPage(HttpServletRequest request, Saleorder saleorder, Page page,Integer i) {
		if(i==1) {
			saleorder.setOnlineFlag(true);
		}else {
			saleorder.setOnlineFlag(false);
		}
		Map<String, Object> map = null;
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<Map<String, Object>>> TypeRef = new TypeReference<ResultInfo<Map<String, Object>>>() {
			};
			String url = httpUrl + "order/saleorder/getsaleorderlistpage.htm";

			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, saleorder, clientId, clientKey, TypeRef,
					page);
			if (result != null && result.getCode() == 0) {
				Map<String, Object> result_map = (Map<String, Object>) result.getData();
				if (result_map != null && result_map.size() > 0) {
					map = new HashMap<String, Object>();
					net.sf.json.JSONArray json = null;
					String openInvoiceApplyStr = result_map.get("saleorderList").toString();
					json = net.sf.json.JSONArray.fromObject(openInvoiceApplyStr);

					List<Saleorder> saleorderList = (List<Saleorder>) json.toCollection(json, Saleorder.class);
					map.put("saleorderList", saleorderList);

					saleorder = (Saleorder) JSONObject.toBean(JSONObject.fromObject(result_map.get("saleorder")),
							Saleorder.class);
					// 订单总金额数
					map.put("total_amount", result_map.get("total_amount").toString());

					map.put("saleorder", saleorder);

					page = result.getPage();
					map.put("page", page);
				}
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
		return map;
	}

	@Override
	public List<CommunicateRecord> getCommunicateRecord(List<Integer> list, String communicateType) {
		return communicateRecordMapper.getCommunicateRecord(list, communicateType);
	}

	@Override
	public Saleorder getBaseSaleorderInfo(Saleorder saleorder) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Saleorder>> TypeRef = new TypeReference<ResultInfo<Saleorder>>() {
		};
		String url = httpUrl + "order/saleorder/getbasesaleorderinfo.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, saleorder, clientId, clientKey, TypeRef);
			saleorder = (Saleorder) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
		return saleorder;
	}

	@Override
	public List<SaleorderGoods> getSaleorderGoodsById(Saleorder saleorder) {
		List<SaleorderGoods> list = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<SaleorderGoods>>> TypeRef = new TypeReference<ResultInfo<List<SaleorderGoods>>>() {
		};
		String url = httpUrl + "order/saleorder/getsaleordergoodsbyid.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, saleorder, clientId, clientKey, TypeRef);
			list = (List<SaleorderGoods>) result.getData();
			if (null != list && list.size() > 0) {
				// 手填总成本
				BigDecimal totalReferenceCostPrice = new BigDecimal(0);
				for (SaleorderGoods g : list) {
					if (null == g)
						continue;
					g.setGoodsUserNm(rCategoryJUserMapper.getUserByCategoryNm(g.getGoods().getCategoryId(),
							g.getGoods().getCompanyId()));
					g.getGoods().setUserList(rCategoryJUserMapper.getUserByCategory(g.getGoods().getCategoryId(),
							g.getGoods().getCompanyId()));
					// modify by Franlin at 2018-08-13 for[修改最小原则] begin
					if (null != saleorder.getReqType() && 1 == saleorder.getReqType()
							&& null != g.getReferenceCostPrice() && null != g.geteNum()) {
						BigDecimal mllNum = new BigDecimal(g.getNum()).subtract(new BigDecimal(g.getAfterReturnNum()));
						totalReferenceCostPrice = totalReferenceCostPrice
								.add(g.getReferenceCostPrice().multiply(mllNum));
					}
					// modify by Franlin at 2018-08-13 for[修改最小原则] end
				}
				saleorder.setFiveTotalAmount(totalReferenceCostPrice);
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return list;
	}
	/*新商品流*/
	@Override
	public List<SaleorderGoods> getSaleorderGoodsByIdOther(Saleorder saleorder) {
		List<SaleorderGoods> list = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<SaleorderGoods>>> TypeRef = new TypeReference<ResultInfo<List<SaleorderGoods>>>() {
		};
		String url = httpUrl + "order/saleorder/getsaleordergoodsbyidOther.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, saleorder, clientId, clientKey, TypeRef);
			list = (List<SaleorderGoods>) result.getData();
			if (null != list && list.size() > 0) {
				// 手填总成本
				BigDecimal totalReferenceCostPrice = new BigDecimal(0);
				for (SaleorderGoods g : list) {
					if (null == g)
						continue;
					g.setGoodsUserNm(rCategoryJUserMapper.getUserByCategoryNm(g.getGoods().getCategoryId(),
							g.getGoods().getCompanyId()));
					g.getGoods().setUserList(rCategoryJUserMapper.getUserByCategory(g.getGoods().getCategoryId(),
							g.getGoods().getCompanyId()));
					// modify by Franlin at 2018-08-13 for[修改最小原则] begin
					if (null != saleorder.getReqType() && 1 == saleorder.getReqType()
							&& null != g.getReferenceCostPrice() && null != g.geteNum()) {
						BigDecimal mllNum = new BigDecimal(g.getNum()).subtract(new BigDecimal(g.getAfterReturnNum()));
						totalReferenceCostPrice = totalReferenceCostPrice
								.add(g.getReferenceCostPrice().multiply(mllNum));
					}
					// modify by Franlin at 2018-08-13 for[修改最小原则] end
				}
				saleorder.setFiveTotalAmount(totalReferenceCostPrice);
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return list;
	}

    @Override
    public List<SaleorderGoods> getSaleorderGoodsInfo(Saleorder saleorder) {
        List<SaleorderGoods> list = null;
        // 定义反序列化 数据格式
        saleorder.setBussinessType(2);
        saleorder.setExtraType("warehouse");
        final TypeReference<ResultInfo<List<SaleorderGoods>>> TypeRef = new TypeReference<ResultInfo<List<SaleorderGoods>>>() {
        };
        final TypeReference<ResultInfo4Stock<Map<String,Integer>>> typeReference = new TypeReference<ResultInfo4Stock<Map<String,Integer>>>() {
        };
        String url = null;
        try {
            url = httpUrl + "order/saleorder/getsaleordergoodsinfo.htm";
        } catch (Exception e) {
            logger.error(Contant.ERROR_MSG, e);
        }
        try {

            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, saleorder, clientId, clientKey, TypeRef);
            list = (List<SaleorderGoods>) result.getData();
            /**
             * 如果是活动商品 获取商品的锁定库存信息
             */
			Optional skuNo = list.stream()
								.filter(item -> item.getIsActionGoods() > 0)
								.map(SaleorderGoods::getSku)
								.reduce((s1,s2) -> s1 + "," + s2);
			if (skuNo.isPresent()) {
				String queryUrl = stockUrl + "/promotion/action/lockedCount/sku?skuNo=" + skuNo.get();
				ResultInfo4Stock<?> resultInfo = (ResultInfo4Stock<?>) HttpClientUtils4Stock.get(queryUrl, null, clientId, clientKey, typeReference);
				if (resultInfo != null) {
					Map<String, Integer> actionLockCountMap = (Map<String, Integer>) resultInfo.getData();
					list.forEach(
							item -> item.setActionLockCount(actionLockCountMap.get(item.getSku()))
					);
				}else{
				    list.forEach(item -> item.setActionLockCount(0));
                }
			}

        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
        }
        return list;
    }


	@Override
	public Saleorder saveEditSaleorderInfo(Saleorder saleorder, HttpServletRequest request, HttpSession session) {
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		Long time = DateUtil.sysTimeMillis();

		saleorder.setModTime(time);
		saleorder.setUpdater(user.getUserId());
//		if(null!=saleorder&&null!=saleorder.getOrderType()) {
//			if(saleorder.getOrderType()==1&&saleorder.getDeliveryDirect()==null) {
//				saleorder.setDeliveryDirect(0);
//			}
//		}
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Saleorder>> TypeRef2 = new TypeReference<ResultInfo<Saleorder>>() {
		};
		try {
			ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(
					httpUrl + "order/saleorder/saveeditsaleorderinfo.htm", saleorder, clientId, clientKey, TypeRef2);
			Saleorder res = (Saleorder) result2.getData();
			if(result2 != null && result2.getCode().equals(0)){
				updateSaleOrderDataUpdateTime(saleorder.getSaleorderId(),null, OrderDataUpdateConstant.SALE_ORDER_VAILD);
			}
			return res;
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public Saleorder saveEditSaleorderInfo(Saleorder saleorder) {
		Long time = DateUtil.sysTimeMillis();
		saleorder.setModTime(time);
		saleorder.setUpdater(0);
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Saleorder>> TypeRef2 = new TypeReference<ResultInfo<Saleorder>>() {
		};
		try {
			ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(
					httpUrl + "order/saleorder/saveeditsaleorderinfo.htm", saleorder, clientId, clientKey, TypeRef2);
			Saleorder res = (Saleorder) result2.getData();
			if(result2 != null && result2.getCode().equals(0)){
				updateSaleOrderDataUpdateTime(saleorder.getSaleorderId(),null,OrderDataUpdateConstant.SALE_ORDER_EDIT);
			}
			return res;
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public ResultInfo<?> saveSaleorderGoods(SaleorderGoods saleorderGoods) {
		ResultInfo<?> result = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
		};
		String url = httpUrl + "order/saleorder/savesaleordergoods.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, saleorderGoods, clientId, clientKey, TypeRef);
			if(result != null && result.getCode().equals(0)){
				updateSaleOrderDataUpdateTime(null,saleorderGoods.getSaleorderGoodsId(),OrderDataUpdateConstant.SALE_ORDER_GOODS_EDIT);
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

	@Override
	public SaleorderGoods getSaleorderGoodsInfoById(Integer saleorderGoodsId) {
		SaleorderGoods saleorderGoods = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<SaleorderGoods>> TypeRef = new TypeReference<ResultInfo<SaleorderGoods>>() {
		};
		String url = httpUrl + "order/saleorder/getsaleordergoodsinfobyidnew.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, saleorderGoodsId, clientId, clientKey,
					TypeRef);
			saleorderGoods = (SaleorderGoods) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return saleorderGoods;
	}

	@Override
	public ResultInfo<?> delSaleorderGoodsById(SaleorderGoods saleorderGoods) {
		ResultInfo<?> result = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
		};
		String url = httpUrl + "order/saleorder/delsaleordergoodsbyid.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, saleorderGoods, clientId, clientKey, TypeRef);
			if(result != null && result.getCode().equals(0)){
				updateSaleOrderDataUpdateTime(null,saleorderGoods.getSaleorderGoodsId(),OrderDataUpdateConstant.SALE_ORDER_GOODS_EDIT);
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

	@Override
	public ResultInfo<?> closeSaleorder(Saleorder saleorder) {
		ResultInfo<?> result = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
		};
		String url = httpUrl + "order/saleorder/closesaleorder.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, saleorder, clientId, clientKey, TypeRef);
			if(result != null && result.getCode().equals(0)){
			    //更新订单updatatime
                updateSaleOrderDataUpdateTime(saleorder.getSaleorderId(),null,OrderDataUpdateConstant.SALE_ORDER_CLOSE);
            }
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

	@Override
	public ResultInfo<?> editSaleorderHaveCommunicate(Saleorder saleorder) {
		ResultInfo<?> result = null;

		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
		};
		String url = httpUrl + "order/saleorder/editsaleorderhavecommunicate.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, saleorder, clientId, clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

	@Override
	public Saleorder quoteorderToSaleorder(Integer quoteorderId, User user) {
		Map<String, Object> map = new HashMap<>();
		// 获取session
		ServletRequestAttributes ra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ra.getRequest();
		Quoteorder quoteorderInfo = quoteService.getQuoteInfoByKey(quoteorderId);
		if (quoteorderInfo != null) {
			Map<String, Object> quoteGoodsmap = quoteService.getQuoteGoodsByQuoteId(quoteorderId,
					quoteorderInfo.getCompanyId(), request.getSession(), 2, quoteorderInfo.getTraderId());
			List<QuoteorderGoods> quoteGoodsList = (List<QuoteorderGoods>) quoteGoodsmap.get("quoteGoodsList");
			// 根据客户ID查询客户信息
			TraderCustomerVo customer = traderCustomerService.getTraderCustomerInfo(quoteorderInfo.getTraderId());
			// 产品核算价
			quoteGoodsList = goodsChannelPriceService.getQuoteChannelPriceList(quoteorderInfo.getSalesAreaId(),
					quoteorderInfo.getCustomerNature(), customer.getOwnership(), quoteGoodsList);
			for (QuoteorderGoods qg : quoteGoodsList) {
				// 如果参考报价为0，核价的成本>0
				if (qg.getReferenceCostPrice() != null && qg.getReferenceCostPrice().compareTo(BigDecimal.ZERO) == 0
						&& qg.getCostPrice() != null && qg.getCostPrice().compareTo(BigDecimal.ZERO) == 1) {
					qg.setReferenceCostPrice(qg.getCostPrice());
				}
			}
			quoteorderInfo.setQuoteorderGoods(quoteGoodsList);
			// 将参考报价修改为核价成本的值
			quoteService.editQuoteOrderGoods(quoteorderInfo);
		}
		Saleorder saleorderPar = new Saleorder();
		saleorderPar.setQuoteorderId(quoteorderId);

        // 归属销售
        User belongUser = new User();
        if (quoteorderInfo.getTraderId() != null) {
            belongUser = userService.getUserInfoByTraderId(quoteorderInfo.getTraderId(), 1);// 1客户，2供应商
            if (belongUser != null && belongUser.getUserId() != null) {
                saleorderPar.setUserId(belongUser.getUserId());
            }
            if (belongUser != null && belongUser.getOrgId() != null) {
                saleorderPar.setOrgId(belongUser.getOrgId());
            }
            if (belongUser != null && belongUser.getOrgName() != null) {
                saleorderPar.setOrgName(belongUser.getOrgName());
            }
        }
        map.put("saleorder", saleorderPar);
        map.put("user", user);

        Saleorder saleorder = null;
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<Saleorder>> TypeRef = new TypeReference<ResultInfo<Saleorder>>() {
        };
        String url = httpUrl + "order/saleorder/quoteordertosaleorder.htm";
        try {
            ResultInfo<Saleorder> result = (ResultInfo<Saleorder>) HttpClientUtils.post(url, map, clientId, clientKey,
                    TypeRef);
            saleorder = (Saleorder) result.getData();
            if(saleorder != null){
            	//VDERP-2263   订单售后采购改动通知
				updateSaleOrderDataUpdateTime(saleorder.getSaleorderId(),null,OrderDataUpdateConstant.SALE_ORDER_GENERATE);
			}
        } catch (Exception e) {
            logger.error(Contant.ERROR_MSG, e);
        }
        return saleorder;
    }

    @Override
    public List<Integer> getCommunicateRecordByDate(Long beginDate, Long endDate, String communicateType) {
        return communicateRecordMapper.getCommunicateRecordByDate(beginDate, endDate, communicateType);
    }

	@Override
	public ResultInfo<?> saveSaleorderAttachment(Attachment attachment) {
		ResultInfo<?> result = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Attachment>> TypeRef = new TypeReference<ResultInfo<Attachment>>() {
		};
		String url = httpUrl + "order/saleorder/savesaleorderattachment.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, attachment, clientId, clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}


    @Override
    public ResultInfo<?> delSaleorderAttachment(Attachment attachment) {
        ResultInfo<?> result = null;
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
        };
        String url = httpUrl + "order/saleorder/delsaleorderattachment.htm";
        try {
            result = (ResultInfo<?>) HttpClientUtils.post(url, attachment, clientId, clientKey, TypeRef);
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
        }
        return result;
    }

    @Override
    public List<Attachment> getSaleorderAttachmentList(Integer saleorderId) {
        List<Attachment> list = null;
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<List<Attachment>>> TypeRef = new TypeReference<ResultInfo<List<Attachment>>>() {
        };
        String url = httpUrl + "order/saleorder/getsaleorderattachmentbyid.htm";
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, saleorderId, clientId, clientKey, TypeRef);
            list = (List<Attachment>) result.getData();

            // 操作人信息补充
            if (list.size() > 0) {
                List<Integer> userIds = new ArrayList<>();
                for (Attachment b : list) {
                    if (b.getCreator() > 0) {
                        userIds.add(b.getCreator());
                    }
                }

                if (userIds.size() > 0) {
                    List<User> userList = userMapper.getUserByUserIds(userIds);

                    for (Attachment b : list) {
                        for (User u : userList) {
                            if (u.getUserId().equals(b.getCreator())) {
                                b.setUsername(u.getUsername());
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
        }
        return list;
    }

    @Override
    public int getSaleorderCommunicateRecordCount(CommunicateRecord cr) {
        return communicateRecordMapper.getSaleorderCommunicateRecordCount(cr);
    }

	@Override
	public ResultInfo<?> validSaleorder(Saleorder saleorder) {
		ResultInfo<?> result = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
		};
		String url = httpUrl + "order/saleorder/validsaleorder.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, saleorder, clientId, clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

    @Override
    public ResultInfo<?> isvalidSaleorder(Saleorder saleorder) {
        ResultInfo<?> result = null;
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
        };
        String url = httpUrl + "order/saleorder/isvalidsaleorder.htm";
        try {
            result = (ResultInfo<?>) HttpClientUtils.post(url, saleorder, clientId, clientKey, TypeRef);
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
        }
        return result;
    }

    @Override
    public ResultInfo<?> noValidSaleorder(Saleorder saleorder) {
        ResultInfo<?> result = null;
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
        };
        String url = httpUrl + "order/saleorder/novalidsaleorder.htm";
        try {
            result = (ResultInfo<?>) HttpClientUtils.post(url, saleorder, clientId, clientKey, TypeRef);
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
        }
        return result;
    }


    @Override
    public ResultInfo<?> preQuoteorderToSaleorder(Integer quoteorderId) {
        ResultInfo<?> result = null;
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
        };
        String url = httpUrl + "order/saleorder/prequoteordertosaleorder.htm";
        try {
            result = (ResultInfo<?>) HttpClientUtils.post(url, quoteorderId, clientId, clientKey, TypeRef);
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
        }
        return result;
    }

    @Override
    public Saleorder saveAddBhSaleorder(Saleorder saleorder, HttpServletRequest request, HttpSession session) {
        User user = (User) session.getAttribute(ErpConst.CURR_USER);
        Long time = DateUtil.sysTimeMillis();
        saleorder.setCompanyId(user.getCompanyId());
        saleorder.setOrgId(user.getOrgId());
        saleorder.setUserId(user.getUserId());
        saleorder.setAddTime(time);
        saleorder.setCreator(user.getUserId());
        saleorder.setModTime(time);
        saleorder.setUpdater(user.getUserId());

        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<Saleorder>> TypeRef2 = new TypeReference<ResultInfo<Saleorder>>() {
        };
        try {
            ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils
                    .post(httpUrl + "order/saleorder/saveaddbhsaleorder.htm", saleorder, clientId, clientKey, TypeRef2);
            Saleorder res = (Saleorder) result2.getData();
            if(res != null){
                updateSaleOrderDataUpdateTime(res.getSaleorderId(),null,OrderDataUpdateConstant.SALE_ORDER_GENERATE);
            }
            return res;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public ResultInfo<?> saveEditBhSaleorder(Saleorder saleorder) {
        ResultInfo<?> result = null;
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
        };
        String url = httpUrl + "order/saleorder/saveeditbhsaleorder.htm";
        try {
            result = (ResultInfo<?>) HttpClientUtils.post(url, saleorder, clientId, clientKey, TypeRef);
            if(result != null && result.getCode().equals(0)){
                updateSaleOrderDataUpdateTime(saleorder.getSaleorderId(),null,OrderDataUpdateConstant.SALE_ORDER_EDIT);
            }
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
        }
        return result;
    }

    @Override
    public Saleorder getSaleorderBySaleorderNo(Saleorder saleorder) {
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<Saleorder>> TypeRef = new TypeReference<ResultInfo<Saleorder>>() {
        };
        String url = httpUrl + "order/saleorder/getsaleorderbysaleorderno.htm";
        try {
            ResultInfo result = (ResultInfo<?>) HttpClientUtils.post(url, saleorder, clientId, clientKey, TypeRef);
            if (result.getCode().equals(0)) {
                Saleorder res = (Saleorder) result.getData();
                return res;
            } else {
                return null;
            }
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            return null;
        }
    }

    @Override
    public Map<String, Object> getSaleorderGoodsListPage(HttpServletRequest request, Saleorder saleorder, Page page) {
        Map<String, Object> map = new HashMap<>();
        try {
            // 定义反序列化 数据格式
            final TypeReference<ResultInfo<List<Saleorder>>> TypeRef = new TypeReference<ResultInfo<List<Saleorder>>>() {
            };
            String url = httpUrl + "order/saleorder/getsaleordergoodslistpage.htm";

            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, saleorder, clientId, clientKey, TypeRef,
                    page);
            List<Saleorder> saleorderList = (List<Saleorder>) result.getData();
            page = result.getPage();
            map.put("saleorderList", saleorderList);
            map.put("page", page);
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
        }
        return map;
    }

    @Override
    public ResultInfo<?> batchSaveBhSaleorderGoods(List<SaleorderGoods> list, Saleorder saleorder) throws Exception {
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
        };
        String url = httpUrl + "order/saleorder/batchsavebhsaleordergoods.htm";
        Map<String, Object> map = new HashMap<>();
        map.put("saleGoodsList", list);
        map.put("saleorder", saleorder);
        ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, map, clientId, clientKey, TypeRef);
        if (result != null) {
            return result;
        } else {
            return new ResultInfo<>();
        }
    }

    @Override
    public Saleorder getSaleorderFlag(Saleorder saleorder) {
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<Saleorder>> TypeRef2 = new TypeReference<ResultInfo<Saleorder>>() {
        };
        try {
            ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils
                    .post(httpUrl + "order/saleorder/getsaleorderflag.htm", saleorder, clientId, clientKey, TypeRef2);
            Saleorder res = (Saleorder) result2.getData();
            return res;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public List<SaleorderGoodsVo> getSdList(Goods goods) {
        List<SaleorderGoodsVo> list = null;
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<List<SaleorderGoodsVo>>> TypeRef = new TypeReference<ResultInfo<List<SaleorderGoodsVo>>>() {
        };
        String url = httpUrl + "order/saleorder/getSdList.htm";
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, goods, clientId, clientKey, TypeRef);
            list = (List<SaleorderGoodsVo>) result.getData();
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
        }
        return list;
    }

    /**
     * <b>Description:</b><br>
     * 根据销售订单的id获取销售订单产品的列表
     *
     * @param saleorder
     * @return
     * @Note <b>Author:</b> east <br>
     * <b>Date:</b> 2017年10月10日 下午1:00:18
     */
    @Override
    public SaleorderVo getSaleorderGoodsVoList(Saleorder saleorder) {
        try {
            // 定义反序列化 数据格式
            final TypeReference<ResultInfo<SaleorderVo>> TypeRef = new TypeReference<ResultInfo<SaleorderVo>>() {
            };
            String url = httpUrl + ErpConst.GET_SALEORDERGOODS_BYSALEORDERID;
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, saleorder, clientId, clientKey, TypeRef);
            JSONObject json = JSONObject.fromObject(result.getData());
            SaleorderVo sv = JsonUtils.readValue(json.toString(), SaleorderVo.class);
            if ("hh".equals(saleorder.getFlag())) {
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
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            return null;
        }

	}

    @Override
    public List<SaleorderGoodsWarrantyVo> getSaleorderGoodsWarrantys(Integer saleorderId) {
        try {
            // 定义反序列化 数据格式
            final TypeReference<ResultInfo<List<SaleorderGoodsWarrantyVo>>> TypeRef = new TypeReference<ResultInfo<List<SaleorderGoodsWarrantyVo>>>() {
            };
            String url = httpUrl + "order/saleorder/getsaleordergoodswarrantys.htm";
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, saleorderId, clientId, clientKey, TypeRef);
            List<SaleorderGoodsWarrantyVo> sgw = (List<SaleorderGoodsWarrantyVo>) result.getData();
            if (sgw != null && sgw.size() > 0) {
                List<Integer> userIds = new ArrayList<>();
                for (SaleorderGoodsWarrantyVo s : sgw) {
                    if (s.getCreator() > 0) {
                        userIds.add(s.getCreator());
                    }

				}

                if (userIds.size() > 0) {
                    List<User> userList = userMapper.getUserByUserIds(userIds);
                    for (SaleorderGoodsWarrantyVo s : sgw) {
                        for (User u : userList) {
                            if (u.getUserId().equals(s.getCreator())) {
                                s.setCreateName(u.getUsername());
                            }
                        }
                    }
                }
            }
            return sgw;
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            return null;
        }
    }

    @Override
    public Map<String, BigDecimal> getSaleorderDataInfo(Integer saleorderId) {
        Map<String, BigDecimal> resultMap = new HashMap<>();
        Map<String, BigDecimal> map = new HashMap<>();
        try {
            // 定义反序列化 数据格式
            final TypeReference<ResultInfo<Map<String, BigDecimal>>> TypeRef = new TypeReference<ResultInfo<Map<String, BigDecimal>>>() {
            };
            String url = httpUrl + "order/saleorder/getsaleorderdatainfo.htm";

            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, saleorderId, clientId, clientKey, TypeRef);
            resultMap = (Map<String, BigDecimal>) result.getData();
            if (null != resultMap) {
                map.put("paymentAmount", resultMap.get("paymentAmount"));// 订单已收款金额(不含账期)
                map.put("periodAmount", resultMap.get("periodAmount"));// 订单账期金额
                map.put("realAmount", resultMap.get("realAmount"));// 订单实际金额
                map.put("lackAccountPeriodAmount", resultMap.get("lackAccountPeriodAmount"));// 订单实际金额
                map.put("refundBalanceAmount", resultMap.get("refundBalanceAmount"));// 订单实际金额
                map.put("receivedAmount", resultMap.get("receivedAmount"));// 订单已收款金额
            } else {
                map.put("paymentAmount", new BigDecimal(0.00));
                map.put("periodAmount", new BigDecimal(0.00));
                map.put("realAmount", new BigDecimal(0.00));
                map.put("lackAccountPeriodAmount", new BigDecimal(0.00));
                map.put("refundBalanceAmount", new BigDecimal(0.00));
                map.put("receivedAmount", new BigDecimal(0.00));
            }
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            return null;
        }
        return map;
    }

    @Override
    public List<SaleorderGoodsWarrantyVo> getAllSaleorderGoodsWarrantys(Saleorder saleorder) {
        try {
            // 定义反序列化 数据格式
            final TypeReference<ResultInfo<List<SaleorderGoodsWarrantyVo>>> TypeRef = new TypeReference<ResultInfo<List<SaleorderGoodsWarrantyVo>>>() {
            };
            String url = httpUrl + "order/saleorder/getallsaleordergoodswarrantys.htm";
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, saleorder, clientId, clientKey, TypeRef);
            List<SaleorderGoodsWarrantyVo> sgw = (List<SaleorderGoodsWarrantyVo>) result.getData();
            if (sgw != null && sgw.size() > 0) {
                List<Integer> userIds = new ArrayList<>();
                for (SaleorderGoodsWarrantyVo s : sgw) {
                    if (null != s.getCreator() && s.getCreator() > 0) {
                        userIds.add(s.getCreator());
                    }

                }

                if (userIds.size() > 0) {
                    List<User> userList = userMapper.getUserByUserIds(userIds);
                    for (SaleorderGoodsWarrantyVo s : sgw) {
                        for (User u : userList) {
                            if (u.getUserId().equals(s.getCreator())) {
                                s.setCreateName(u.getUsername());
                            }
                        }
                    }
                }
            }
            return sgw;
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            return null;
        }
    }

    @Override
    public SaleorderGoodsWarrantyVo getSaleorderGoodsInfoForWarranty(SaleorderGoodsWarrantyVo goodsWarrantyVo) {
        try {
            // 定义反序列化 数据格式
            final TypeReference<ResultInfo<SaleorderGoodsWarrantyVo>> TypeRef = new TypeReference<ResultInfo<SaleorderGoodsWarrantyVo>>() {
            };
            String url = httpUrl + "order/saleorder/getsaleordergoodsinfoforwarranty.htm";
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, goodsWarrantyVo, clientId, clientKey,
                    TypeRef);
            SaleorderGoodsWarrantyVo sgw = (SaleorderGoodsWarrantyVo) result.getData();
            return sgw;
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            return null;
        }
    }

    @Override
    public SaleorderGoodsWarrantyVo saveAddGoodsWarranty(HttpServletRequest request, HttpSession session,
                                                         SaleorderGoodsWarrantyVo goodsWarrantyVo) {
        User user = (User) session.getAttribute(ErpConst.CURR_USER);
        Long time = DateUtil.sysTimeMillis();
        goodsWarrantyVo.setCreator(user.getUserId());
        goodsWarrantyVo.setAddTime(time);
        goodsWarrantyVo.setModTime(time);
        goodsWarrantyVo.setUpdater(user.getUserId());

        if (Integer.parseInt(request.getParameter("zone")) > 0) {
            goodsWarrantyVo.setAreaId(Integer.parseInt(request.getParameter("zone")));
        } else {
            goodsWarrantyVo.setAreaId(Integer.parseInt(request.getParameter("city")));
        }

        // 接口调用
        String url = httpUrl + "order/saleorder/saveaddgoodswarranty.htm";

        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<SaleorderGoodsWarrantyVo>> TypeRef2 = new TypeReference<ResultInfo<SaleorderGoodsWarrantyVo>>() {
        };
        try {
            ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, goodsWarrantyVo, clientId, clientKey,
                    TypeRef2);
            SaleorderGoodsWarrantyVo res = (SaleorderGoodsWarrantyVo) result2.getData();
            return res;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public SaleorderGoodsWarrantyVo getGoodsWarrantyInfo(SaleorderGoodsWarrantyVo goodsWarrantyVo) {
        // 接口调用
        String url = httpUrl + "order/saleorder/getgoodswarrantyinfo.htm";

        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<SaleorderGoodsWarrantyVo>> TypeRef2 = new TypeReference<ResultInfo<SaleorderGoodsWarrantyVo>>() {
        };
        try {
            ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, goodsWarrantyVo, clientId, clientKey,
                    TypeRef2);
            SaleorderGoodsWarrantyVo res = (SaleorderGoodsWarrantyVo) result2.getData();
            if (null != res && res.getCreator() != null && res.getCreator() > 0) {
                res.setCreateName(getUserNameByUserId(res.getCreator()));
            }
            if (null != res && res.getAreaId() != null && res.getAreaId() > 0) {
                String area = (String) regionService.getRegion(res.getAreaId(), 2);
                res.setArea(area);
            }
            return res;
        } catch (IOException e) {
            return null;
        }
    }
    /*新商品流*/
	@Override
	public SaleorderGoodsWarrantyVo getGoodsWarrantyInfoNew(SaleorderGoodsWarrantyVo goodsWarrantyVo) {
		// 接口调用
		String url = httpUrl + "order/saleorder/getgoodswarrantyinfoNew.htm";

		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<SaleorderGoodsWarrantyVo>> TypeRef2 = new TypeReference<ResultInfo<SaleorderGoodsWarrantyVo>>() {
		};
		try {
			ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, goodsWarrantyVo, clientId, clientKey,
					TypeRef2);
			SaleorderGoodsWarrantyVo res = (SaleorderGoodsWarrantyVo) result2.getData();
			if (null != res && res.getCreator() != null && res.getCreator() > 0) {
				res.setCreateName(getUserNameByUserId(res.getCreator()));
			}
			if (null != res && res.getAreaId() != null && res.getAreaId() > 0) {
				String area = (String) regionService.getRegion(res.getAreaId(), 2);
				res.setArea(area);
			}
			return res;
		} catch (IOException e) {
			return null;
		}
	}

    @Override
    public SaleorderGoodsWarrantyVo saveEditGoodsWarranty(HttpServletRequest request, HttpSession session,
                                                          SaleorderGoodsWarrantyVo goodsWarrantyVo) {
        User user = (User) session.getAttribute(ErpConst.CURR_USER);
        Long time = DateUtil.sysTimeMillis();
        goodsWarrantyVo.setModTime(time);
        goodsWarrantyVo.setUpdater(user.getUserId());

        if (Integer.parseInt(request.getParameter("zone")) > 0) {
            goodsWarrantyVo.setAreaId(Integer.parseInt(request.getParameter("zone")));
        } else {
            goodsWarrantyVo.setAreaId(Integer.parseInt(request.getParameter("city")));
        }

        // 接口调用
        String url = httpUrl + "order/saleorder/saveeditgoodswarranty.htm";

        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<SaleorderGoodsWarrantyVo>> TypeRef2 = new TypeReference<ResultInfo<SaleorderGoodsWarrantyVo>>() {
        };
        try {
            ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, goodsWarrantyVo, clientId, clientKey,
                    TypeRef2);
            SaleorderGoodsWarrantyVo res = (SaleorderGoodsWarrantyVo) result2.getData();
            return res;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public ResultInfo<?> vailSaleorderGoodsRepeat(SaleorderGoods saleorderGoods) {
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
        };
        String url = httpUrl + "order/saleorder/vailsaleordergoodsrepeat.htm";
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, saleorderGoods, clientId, clientKey,
                    TypeRef);
            if (result == null) {
                return new ResultInfo<>();
            }
            return result;
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            return new ResultInfo<>();
        }
    }

	@Override
	public SaleorderVo getPrintOrderInfo(Saleorder saleorder) {
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<SaleorderVo>> TypeRef = new TypeReference<ResultInfo<SaleorderVo>>() {
			};
			String url = httpUrl + "order/saleorder/getprintorderinfo.htm";
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, saleorder, clientId, clientKey, TypeRef);
			SaleorderVo sgw = (SaleorderVo) result.getData();
			return sgw;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}

    /**
     * <b>Description:</b><br>
     * 根据产品ID获取结算价格
     *
     * @return
     * @Note <b>Author:</b> Michael <br>
     * <b>Date:</b> 2017年12月18日 上午10:49:02
     */
    @Override
    public BigDecimal getSaleorderGoodsSettlementPrice(Integer goodsId, Integer companyId) {
        return goodsSettlementPriceMapper.getSaleorderGoodsSettlementPrice(goodsId, companyId);
    }

    @Override
    public Saleorder saveAddSaleorderInfo(Saleorder saleorder, HttpServletRequest request, HttpSession session) {
        User user = (User) session.getAttribute(ErpConst.CURR_USER);
        Long time = DateUtil.sysTimeMillis();

        saleorder.setAddTime(time);
        saleorder.setCreator(user.getUserId());
        saleorder.setCreatorOrgId(user.getOrgId());
        saleorder.setCreatorOrgName(user.getOrgName());
        saleorder.setCompanyId(user.getCompanyId());
        saleorder.setOrderType(0);
        saleorder.setStatus(0);
        // saleorder.setOrgId(user.getOrgId());
        // saleorder.setUserId(user.getUserId());

        // 归属销售
        User belongUser = new User();
        if (saleorder.getTraderId() != null) {
            belongUser = userService.getUserInfoByTraderId(saleorder.getTraderId(), 1);// 1客户，2供应商
            if (belongUser != null && belongUser.getUserId() != null) {
                saleorder.setUserId(belongUser.getUserId());
            }
            if (belongUser != null && belongUser.getOrgId() != null) {
                saleorder.setOrgId(belongUser.getOrgId());
            }
            if (belongUser != null && belongUser.getOrgName() != null) {
                saleorder.setOrgName(belongUser.getOrgName());
            }
        }

        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<Saleorder>> TypeRef2 = new TypeReference<ResultInfo<Saleorder>>() {
        };
        try {
            ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(
                    httpUrl + "order/saleorder/saveaddsaleorderinfo.htm", saleorder, clientId, clientKey, TypeRef2);
            Saleorder res = (Saleorder) result2.getData();
            if(res != null){
				//VDERP-2263   订单售后采购改动通知
            	updateSaleOrderDataUpdateTime(res.getSaleorderId(),null,OrderDataUpdateConstant.SALE_ORDER_GENERATE);
			}
            return res;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public Saleorder confirmArrival(Saleorder saleorder, HttpServletRequest request, HttpSession session) {
        User user = (User) session.getAttribute(ErpConst.CURR_USER);
        Long time = DateUtil.sysTimeMillis();
        List<SaleorderGoods> saleorderGoodsList = new ArrayList<>();

        if (null != request.getParameter("id_str")) {
            String[] categoryAttributeIds2 = request.getParameter("id_str").split("_");
            for (String attIds2 : categoryAttributeIds2) {
                if (attIds2 == "" || null == request.getParameter("arrivalStatus_" + attIds2))
                    continue;
                SaleorderGoods saleorderGoods = new SaleorderGoods();

                saleorderGoods.setSaleorderGoodsId(Integer.parseInt(attIds2));
                saleorderGoods.setArrivalStatus(Integer.parseInt(request.getParameter("arrivalStatus_" + attIds2)));
                saleorderGoods.setArrivalUserId(user.getUserId());
                saleorderGoods.setArrivalTime(time);

                saleorderGoodsList.add(saleorderGoods);
            }

        }
        saleorder.setGoodsList(saleorderGoodsList);

        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<Saleorder>> TypeRef2 = new TypeReference<ResultInfo<Saleorder>>() {
        };
        try {
            ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(httpUrl + "order/saleorder/confirmarrival.htm",
                    saleorder, clientId, clientKey, TypeRef2);
            Saleorder res = (Saleorder) result2.getData();
            if(result2 != null && result2.getCode().equals(0)){
                updateSaleOrderDataUpdateTime(saleorder.getSaleorderId(),null,OrderDataUpdateConstant.SALE_ORDER_EXPRESS_END);
            }
            return res;
        } catch (IOException e) {
            return null;
        }

    }

	@Override
	public SaleorderModifyApply convertModifyApply(SaleorderModifyApply saleorderModifyApply, HttpServletRequest request, HttpSession session) {
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		Long time = DateUtil.sysTimeMillis();
		saleorderModifyApply.setCompanyId(user.getCompanyId());
		saleorderModifyApply.setAddTime(time);
		saleorderModifyApply.setCreator(user.getUserId());

		List<SaleorderModifyApplyGoods> saleorderGoodsList = new ArrayList<>();

		if (null != request.getParameter("id_str")) {
			String[] categoryAttributeIds2 = request.getParameter("id_str").split("_");
			for (String attIds2 : categoryAttributeIds2) {
				if (attIds2 == "" || null == request.getParameter("deliveryDirect_" + attIds2))
					continue;
				SaleorderModifyApplyGoods saleorderGoods = new SaleorderModifyApplyGoods();

				saleorderGoods.setSaleorderGoodsId(Integer.parseInt(attIds2));
				saleorderGoods.setDeliveryDirect(Integer.parseInt(request.getParameter("deliveryDirect_" + attIds2)));
				saleorderGoods.setDeliveryDirectComments(request.getParameter("deliveryDirectComments_" + attIds2));
				saleorderGoods.setGoodsComments(request.getParameter("goodsComments_" + attIds2));
				saleorderGoods
						.setOldDeliveryDirect(Integer.parseInt(request.getParameter("oldDeliveryDirect_" + attIds2)));
				saleorderGoods
						.setOldDeliveryDirectComments(request.getParameter("oldDeliveryDirectComments_" + attIds2));
				saleorderGoods.setOldGoodsComments(request.getParameter("oldGoodsComments_" + attIds2));

				saleorderGoodsList.add(saleorderGoods);
			}

		}
		saleorderModifyApply.setGoodsList(saleorderGoodsList);
		return saleorderModifyApply;
	}


	@Override
    public SaleorderModifyApply modifyApplySave(SaleorderModifyApply saleorderModifyApply, HttpServletRequest request,
                                                HttpSession session) {
        User user = (User) session.getAttribute(ErpConst.CURR_USER);
        Long time = DateUtil.sysTimeMillis();
        saleorderModifyApply.setCompanyId(user.getCompanyId());
        saleorderModifyApply.setAddTime(time);
        saleorderModifyApply.setCreator(user.getUserId());

        List<SaleorderModifyApplyGoods> saleorderGoodsList = new ArrayList<>();

        if (null != request.getParameter("id_str")) {
            String[] categoryAttributeIds2 = request.getParameter("id_str").split("_");
            for (String attIds2 : categoryAttributeIds2) {
                if (attIds2 == "" || null == request.getParameter("deliveryDirect_" + attIds2))
                    continue;
                SaleorderModifyApplyGoods saleorderGoods = new SaleorderModifyApplyGoods();

                saleorderGoods.setSaleorderGoodsId(Integer.parseInt(attIds2));
                saleorderGoods.setDeliveryDirect(Integer.parseInt(request.getParameter("deliveryDirect_" + attIds2)));
                saleorderGoods.setDeliveryDirectComments(request.getParameter("deliveryDirectComments_" + attIds2));
                saleorderGoods.setGoodsComments(request.getParameter("goodsComments_" + attIds2));
                saleorderGoods
                        .setOldDeliveryDirect(Integer.parseInt(request.getParameter("oldDeliveryDirect_" + attIds2)));
                saleorderGoods
                        .setOldDeliveryDirectComments(request.getParameter("oldDeliveryDirectComments_" + attIds2));
                saleorderGoods.setOldGoodsComments(request.getParameter("oldGoodsComments_" + attIds2));

                saleorderGoodsList.add(saleorderGoods);
            }

        }
        saleorderModifyApply.setGoodsList(saleorderGoodsList);

        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<SaleorderModifyApply>> TypeRef2 = new TypeReference<ResultInfo<SaleorderModifyApply>>() {
        };
        try {
            ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(
                    httpUrl + "order/saleorder/modifyapplysave.htm", saleorderModifyApply, clientId, clientKey,
                    TypeRef2);
            SaleorderModifyApply res = (SaleorderModifyApply) result2.getData();
            return res;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * <b>Description:</b><br>
     * 保存申请提前采购
     *
     * @param saleorder
     * @param user
     * @return
     * @Note <b>Author:</b> east <br>
     * <b>Date:</b> 2018年1月17日 上午9:56:21
     */
    @Override
    public ResultInfo<?> saveApplyPurchase(Saleorder saleorder, User user) {
        saleorder.setHaveAdvancePurchase(1);// 有提前采购
        saleorder.setAdvancePurchaseStatus(2);// 审核通过
        saleorder.setAdvancePurchaseTime(DateUtil.sysTimeMillis());
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
        };
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(httpUrl + ErpConst.SAVE_APPLY_PURCHASE,
                    saleorder, clientId, clientKey, TypeRef);
            if (result == null || result.getCode() == -1) {
                return new ResultInfo<>(-1, "操作失败");
            }
            return result;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Map<String, Object> getSaleorderModifyApplyListPage(HttpServletRequest request,
                                                               Saleorder saleorderModifyApply, Page page) {
        Map<String, Object> map = null;
        try {
            // 定义反序列化 数据格式
            final TypeReference<ResultInfo<Map<String, Object>>> TypeRef = new TypeReference<ResultInfo<Map<String, Object>>>() {
            };
            String url = httpUrl + "order/saleorder/getsaleordermodifyapplylistpage.htm";

            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, saleorderModifyApply, clientId, clientKey,
                    TypeRef, page);
            if (result != null && result.getCode() == 0) {
                Map<String, Object> result_map = (Map<String, Object>) result.getData();
                if (result_map != null && result_map.size() > 0) {
                    map = new HashMap<String, Object>();

                    net.sf.json.JSONArray json = null;
                    String openInvoiceApplyStr = result_map.get("saleorderList").toString();
                    json = net.sf.json.JSONArray.fromObject(openInvoiceApplyStr);

                    List<Saleorder> saleorderList = (List<Saleorder>) json.toCollection(json, Saleorder.class);
                    map.put("saleorderList", saleorderList);

                    saleorderModifyApply = (Saleorder) JSONObject
                            .toBean(JSONObject.fromObject(result_map.get("saleorder")), Saleorder.class);
                    map.put("saleorder", saleorderModifyApply);

                    page = result.getPage();
                    map.put("page", page);
                }
            }
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            return null;
        }
        return map;
    }


    @Override
    public SaleorderModifyApply getSaleorderModifyApplyInfo(SaleorderModifyApply saleorderModifyApply) {
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<SaleorderModifyApply>> TypeRef = new TypeReference<ResultInfo<SaleorderModifyApply>>() {
        };
        String url = httpUrl + "order/saleorder/getsaleordermodifyapplyinfo.htm";
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, saleorderModifyApply, clientId, clientKey,
                    TypeRef);
            saleorderModifyApply = (SaleorderModifyApply) result.getData();
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            return null;
        }
        return saleorderModifyApply;
    }

    @Override
    public List<SaleorderModifyApplyGoods> getSaleorderModifyApplyGoodsById(SaleorderModifyApply saleorderModifyApply) {
        List<SaleorderModifyApplyGoods> list = null;
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<List<SaleorderModifyApplyGoods>>> TypeRef = new TypeReference<ResultInfo<List<SaleorderModifyApplyGoods>>>() {
        };
        String url = httpUrl + "order/saleorder/getsaleordermodifyapplygoodsbyid.htm";
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, saleorderModifyApply, clientId, clientKey,
                    TypeRef);
            list = (List<SaleorderModifyApplyGoods>) result.getData();
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
        }
        return list;
    }

    @Override
    public ResultInfo<?> saveSaleorderModifyApplyToSaleorder(SaleorderModifyApply saleorderModifyApply) {
        ResultInfo<?> result = null;
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
        };
        String url = httpUrl + "order/saleorder/savesaleordermodifyapplytosaleorder.htm";
        try {
            result = (ResultInfo<?>) HttpClientUtils.post(url, saleorderModifyApply, clientId, clientKey, TypeRef);
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
        }
        return result;
    }

    @Override
    public List<SaleorderModifyApply> getSaleorderModifyApplyList(SaleorderModifyApply saleorderModifyApply) {
        List<SaleorderModifyApply> list = null;
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<List<SaleorderModifyApply>>> TypeRef = new TypeReference<ResultInfo<List<SaleorderModifyApply>>>() {
        };
        String url = httpUrl + "order/saleorder/getsaleordermodifyapplylist.htm";
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, saleorderModifyApply, clientId, clientKey,
                    TypeRef);
            list = (List<SaleorderModifyApply>) result.getData();
            if (list != null && list.size() > 0) {
                List<Integer> userIds = new ArrayList<>();
                for (SaleorderModifyApply s : list) {
                    if (null != s.getCreator() && s.getCreator() > 0) {
                        userIds.add(s.getCreator());
                    }

                }

                if (userIds.size() > 0) {
                    List<User> userList = userMapper.getUserByUserIds(userIds);
                    for (SaleorderModifyApply s : list) {
                        for (User u : userList) {
                            if (u.getUserId().equals(s.getCreator())) {
                                s.setCreateName(u.getUsername());
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
        }
        return list;
    }


    @Override
    public ResultInfo<?> getSaleorderGoodsExtraInfo(Saleorder saleorder) {
        ResultInfo<?> result = null;
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
        };
        String url = httpUrl + "order/saleorder/getsaleordergoodsextrainfo.htm";
        try {
            result = (ResultInfo<?>) HttpClientUtils.post(url, saleorder, clientId, clientKey, TypeRef);
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
        }
        return result;
    }

    @Override
    public SaleorderVo getSaleorderForSync(Integer saleorderId) {
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<SaleorderVo>> TypeRef = new TypeReference<ResultInfo<SaleorderVo>>() {
        };
        String url = httpUrl + "order/saleorder/getsaleorderforsync.htm";
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, saleorderId, clientId, clientKey, TypeRef);
            SaleorderVo saleorder = (SaleorderVo) result.getData();
            return saleorder;
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            return null;
        }
    }

    @Override
    public SaleorderVo getMessageInfoForSync(Integer orderId) {
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<SaleorderVo>> TypeRef = new TypeReference<ResultInfo<SaleorderVo>>() {
        };
        String url = httpUrl + "order/saleorder/getmessageinfoforsync.htm";
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, orderId, clientId, clientKey, TypeRef);
            SaleorderVo saleorder = (SaleorderVo) result.getData();
            return saleorder;
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            return null;
        }
    }

    @Override
    public ResultInfo<?> saveBatchAddSaleGoods(Saleorder saleorder) {
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<SaleorderVo>> TypeRef = new TypeReference<ResultInfo<SaleorderVo>>() {
        };
        String url = httpUrl + "order/saleorder/savebatchaddsalegoods.htm";
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, saleorder, clientId, clientKey, TypeRef);
            return result;
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            return null;
        }
    }

    @Override
    public List<SaleorderGoods> getSaleorderGoodNoOutList(Integer saleorderId) {
        List<SaleorderGoods> list = null;
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<List<SaleorderGoods>>> TypeRef = new TypeReference<ResultInfo<List<SaleorderGoods>>>() {
        };
        String url = httpUrl + "order/saleorder/getsaleordergoodnooutlist.htm";
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, saleorderId, clientId, clientKey, TypeRef);
            list = (List<SaleorderGoods>) result.getData();
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
        }
        return list;
    }

	@Override
	public ResultInfo<?> saveBatchReferenceCostPrice(Saleorder saleorder) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<SaleorderVo>> TypeRef = new TypeReference<ResultInfo<SaleorderVo>>() {
		};
		String url = httpUrl + "order/saleorder/savebatchreferencecostprice.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, saleorder, clientId, clientKey, TypeRef);
			return result;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}

    @Override
    public Map<Integer, Object> getSaleorderGoodsSettlementPriceByGoodsIds(List<Integer> goodsIds, Integer companyId) {
        // 查询产品ID和产品结算价的集合
        Map<Integer, Object> map = new HashMap<>();
        List<GoodsSettlementPrice> gspList = goodsSettlementPriceMapper
                .getSaleorderGoodsSettlementPriceByGoodsIds(goodsIds, companyId);
        for (GoodsSettlementPrice gsp : gspList) {
            map.put(gsp.getGoodsId(), gsp.getSettlementPrice());
        }
        return map;
    }


	@Override
	public Saleorder getBaseSaleorderInfoNew(Saleorder saleorder) {
		// TODO Auto-generated method stub
		final TypeReference<ResultInfo<Saleorder>> TypeRef = new TypeReference<ResultInfo<Saleorder>>() {
		};
		String url = httpUrl + "order/saleorder/getbasesaleorderinfonew.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, saleorder, clientId, clientKey, TypeRef);
			saleorder = (Saleorder) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
		return saleorder;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<SaleorderGoods> getSaleorderGoodsByIdNew(Saleorder saleorder) {
		// TODO Auto-generated method stub
		final TypeReference<ResultInfo<List<SaleorderGoods>>> TypeRef = new TypeReference<ResultInfo<List<SaleorderGoods>>>() {
		};
		String url = httpUrl + "order/saleorder/getsaleordergoodsbyidnew.htm";
		List<SaleorderGoods> saleorderGoodsList = null;
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, saleorder, clientId, clientKey, TypeRef);
			saleorderGoodsList = (List<SaleorderGoods>) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
		return saleorderGoodsList;
	}

    @Override
    public ResultInfo<?> updateSaleGoodsSave(SaleorderGoods saleorderGoods) {
        final TypeReference<ResultInfo<SaleorderVo>> TypeRef = new TypeReference<ResultInfo<SaleorderVo>>() {
        };
        String url = httpUrl + "order/saleorder/updatesalegoodssave.htm";
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, saleorderGoods, clientId, clientKey,
                    TypeRef);
            return result;
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            return new ResultInfo<>();
        }
    }

    @Override
    public ResultInfo<?> updateSaleGoodsByAllSpecialGoods(Integer saleorderId) {
        ResultInfo<?> result = null;
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
        };
        String url = httpUrl + "order/saleorder/updatesalegoodsbyallspecialgoods.htm";
        try {
            result = (ResultInfo<?>) HttpClientUtils.post(url, saleorderId, clientId, clientKey, TypeRef);
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
        }
        return result;
    }


    @Override
    public Saleorder getSaleOrderInfo(Saleorder saleorder) {
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<Saleorder>> TypeRef = new TypeReference<ResultInfo<Saleorder>>() {
        };
        String url = httpUrl + "order/saleorder/getsaleorderinfo.htm";
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, saleorder, clientId, clientKey, TypeRef);
            if (result != null && result.getCode() == 0) {
                saleorder = (Saleorder) result.getData();
            }
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            return null;
        }
        return saleorder;
    }

	@Override
	public List<SaleorderGoods> getSaleOrderGoodsList(Saleorder sale) {
		final TypeReference<ResultInfo<List<SaleorderGoods>>> TypeRef = new TypeReference<ResultInfo<List<SaleorderGoods>>>() {
		};
		String url = httpUrl + "order/saleorder/getsaleordergoodslist.htm";
		List<SaleorderGoods> saleorderGoodsList = null;
		try {

			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, sale, clientId, clientKey, TypeRef);
			saleorderGoodsList = (List<SaleorderGoods>) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
		return saleorderGoodsList;
	}

    /*
     * (non-Javadoc)
     *
     * @see com.vedeng.order.service.SaleorderService#getSaleorderListByInvoiceIds(
     * java.util.List)
     */
    @Override
    public List<Saleorder> getSaleorderListByInvoiceIds(List<Integer> invoiceIdList) {
        String url = restDbUrl + "rest/order/hc/v1/search/getSaleOrderList";
        final TypeReference<ResultInfo<List<SaleorderGoods>>> TypeRef = new TypeReference<ResultInfo<List<SaleorderGoods>>>() {
        };
        ReqVo<List<Integer>> reqVo = new ReqVo<List<Integer>>();
        reqVo.setReq(invoiceIdList);
        List<Saleorder> saleorderList = null;
        ResultInfo<?> result = (ResultInfo<?>) HttpRestClientUtil.post(url, TypeRef, reqVo);
        if (result.getCode() == 0) {
            saleorderList = (List<Saleorder>) result.getData();
        }
        return saleorderList;
    }


	@Override
	public List<SaleorderWarehouseComments> getListComments(Saleorder saleorder) {
		List<SaleorderWarehouseComments> list = null;
		final TypeReference<ResultInfo<List<SaleorderWarehouseComments>>> TypeRef = new TypeReference<ResultInfo<List<SaleorderWarehouseComments>>>() {
		};
		String url = httpUrl + "order/saleorder/getlistcomments.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, saleorder, clientId, clientKey, TypeRef);
			list = (List<SaleorderWarehouseComments>) result.getData();
			if (list != null && list.size() > 0) {
				for (SaleorderWarehouseComments sc : list) {
					sc.setOpterName(getUserNameByUserId(sc.getCreator()));
				}
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return list;
	}


	@Override
	public ResultInfo<?> updateComments(SaleorderWarehouseComments saleorderWarehouseComments) {
		String url = httpUrl + "order/saleorder/updateComments.htm";
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, saleorderWarehouseComments, clientId,
					clientKey, TypeRef);
			// 接口返回条码生成的记录
			if (result.getCode() == 0) {
				return new ResultInfo(0, "操作成功");
			} else {
				return new ResultInfo();
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return new ResultInfo();
		}
	}

	@Override
	public SaleorderWarehouseComments getSaleorderWarehouseComments(
			SaleorderWarehouseComments saleorderWarehouseComments) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<SaleorderWarehouseComments>> TypeRef = new TypeReference<ResultInfo<SaleorderWarehouseComments>>() {
		};
		String url = httpUrl + "order/saleorder/getsaleorderwarehousecomments.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, saleorderWarehouseComments, clientId,
					clientKey, TypeRef);
			if (result != null && result.getCode() == 0) {
				saleorderWarehouseComments = (SaleorderWarehouseComments) result.getData();
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
		return saleorderWarehouseComments;
	}

    /**
     * <b>Description:</b><br>
     * 订单状态的同步
     *
     * @param :
     * @return :
     * @Note <b>Author:</b> Bert <br>
     * <b>Date:</b> 2019/2/1 11:46
     */
    @Override
    public void synchronousOrderStatus(HttpServletRequest request, Saleorder saleorder) {
        User curr_user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        // 得到一个销售单
        Saleorder sv = getBaseSaleorderInfo(saleorder);
        SaleorderVo saleorderVo = new SaleorderVo();
        // 解决空指针问题
        if (!StringUtils.isEmpty(sv)) {
            // 普发的(0是普发,1是直发)
            if (sv.getDeliveryDirect().equals(ErpConst.ZERO)) {
                if (!sv.getStatus().equals(ErpConst.THREE)) {
                    // 这边正对于部分发货(对应的部分收货和未收货的状态值)
                    if ((sv.getDeliveryStatus().equals(ErpConst.ONE) && (sv.getArrivalStatus().equals(ErpConst.ZERO)
                            || sv.getArrivalStatus().equals(ErpConst.ONE)))
                            || (sv.getDeliveryStatus().equals(ErpConst.TWO)
                            && !sv.getArrivalStatus().equals(ErpConst.TWO))) {
                        // 计算数据收货状态值(计算公式)
                        // 获取订单产品信息,以及所有产品的手填产品成本总和totalAmount
                        Saleorder sale = new Saleorder();
                        // 订单的ID
                        sale.setSaleorderId(sv.getSaleorderId());
                        // 交易者的ID
                        sale.setTraderId(saleorder.getTraderId());
                        sale.setCompanyId(curr_user.getCompanyId());
                        sale.setReqType(1);
                        // 获取订单产品信息,以及所有产品的手填产品成本总和totalAmount
                        List<SaleorderGoods> saleorderGoodsList = getSaleorderGoodsById(sale);
                        // 销售订单退货数量
                        Optional<List<SaleorderGoods>> saleorderGoods = Optional.ofNullable(saleorderGoodsList);
                        boolean[] isSendStatus = {true};
                        if (saleorderGoods.isPresent() && !saleorderGoods.get().isEmpty()) {
                            saleorderGoods.get().stream().forEach(x -> {
                                // 判断收货的(已发货减去实际退货入库的)
                                // 该产品已发数量 – 该产品退回数量（售后入库数量）
                                Integer temp = x.getDeliveryNum() - x.getWarehouseReturnNum();
                                // 当产品退货后的数量（该产品数量-该产品退货数量）
                                saleorderVo.setCompanyId(saleorder.getCompanyId());
                                saleorderVo.setSaleorderGoodId(x.getSaleorderGoodsId());
                                saleorderVo.setSaleorderNo(sv.getSaleorderNo());
                                Integer goodsAfterReturnNum = getGoodsAfterReturnNum(saleorderVo);
                                Integer temp1 = x.getNum() - goodsAfterReturnNum;

                                if (!temp.equals(temp1)) {
                                    isSendStatus[0] = false;
                                }
                            });
                        }
                        // 如果满足条件就进行更新操作
                        if (isSendStatus[0]) {
                            // 更新收款状态
                            Saleorder updateSaleorderStatus = new Saleorder();
                            // updateSaleorderStatus.setSaleorderNo(saleorder.getSaleorderNo());
                            updateSaleorderStatus.setDeliveryStatus(ErpConst.TWO);
                            if (sv.getArrivalStatus().equals(ErpConst.ONE)) {
                                updateSaleorderStatus.setArrivalStatus(ErpConst.TWO);
                            }
                            updateSaleorderStatus.setSaleorderNo(sv.getSaleorderNo());
                            updateSalderStatusDelivery(request, updateSaleorderStatus);
                        }
                    }
                }
            }
            // 直发的目前不用考虑
            /*
             * else {
             *
             * }
             */
        }

        try{
			updateState(saleorder);
			InvoiceState(saleorder.getSaleorderId(),curr_user.getUserId());
		}catch (Exception e){
        	logger.error("售后完结更新收发货开票状态失败",e);
		}



    }


    /**
     * <b>Description:</b><br>
     *
     * @param :a
     * @return :a
     * @Note <b>Author:</b> Bert <br>
     * <b>Date:</b> 2019/2/14 15:11
     */
    @Override
    public String updateSalderStatusDelivery(HttpServletRequest request, Saleorder saleorder) {
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<SaleorderWarehouseComments>> TypeRef = new TypeReference<ResultInfo<SaleorderWarehouseComments>>() {
        };
        // 数据更新的仓库地址
        String url = httpUrl + "order/saleorder/updateSalderStatusDelivery.htm";
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, saleorder, clientId, clientKey, TypeRef);
            if (result != null && result.getCode() == 0) {
                return ErpConst.SEND_DATA_SUCCESS;
            }
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            return null;
        }
        return ErpConst.SEND_DATA_FAIL;
    }

	/**
	 * <b>Description:</b><br>
	 *
	 * @param :a
	 * @return :a
	 * @Note <b>Author:</b> Bert <br>
	 *       <b>Date:</b> 2019/2/15 11:21
	 */
	@Override
	public Integer getGoodsAfterReturnNum(SaleorderVo saleorder) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Integer>> TypeRef = new TypeReference<ResultInfo<Integer>>() {
		};
		// 数据更新的仓库地址
		String url = httpUrl + "order/saleorder/seachAfterSaleNum.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, saleorder, clientId, clientKey, TypeRef);
			if (result != null && result.getCode() == 0) {
				return new Integer(result.getData().toString());
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
		// 默认返回0
		return 0;
	}

    @Override
    public List<Integer> getSaleOrderIdListByParam(Map<String, Object> paraMap) {

        return saleorderMapper.getSaleOrderIdListByParam(paraMap);
    }

    @Override
    public List<Saleorder> getOrderListInfoById(Map<String, Object> paraMap) {

        return saleorderMapper.getOrderListInfoById(paraMap);
    }

    @Override
    public Map<String, Object> getContractReturnOrderListPage(SaleorderContract saleOrderContract, Page page,
                                                              String searchType) {

        Map<String, Object> paraMap = new HashMap<>();

        paraMap.put("saleOrderContract", saleOrderContract);
        paraMap.put("page", page);

        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<SaleorderContract> list = null;
        Integer listCount = 0;
        if ("1".equals(searchType)) {
            list = saleorderMapper.getContractReturnOrderListPage(paraMap);
            //listCount = saleorderMapper.getContractReturnOrderListCount(paraMap);
        } else {
            list = saleorderMapper.getContractReturnOrderNoqualityListPage(paraMap);
            //listCount = saleorderMapper.getContractReturnOrderNoqualityListCount(paraMap);
        }

        //page.setTotalRecord(listCount);

        resultMap.put("page", page);
        resultMap.put("list", list);

        return resultMap;
    }

    @Override
    public ResultInfo saveOrderRatioEdit(Integer orderId, Integer invoiceType) {
        int i = saleorderMapper.saveOrderRatioEdit(orderId, invoiceType);
        if (i == 1) {
            return new ResultInfo(0, "操作成功");
        } else {
            return new ResultInfo();
        }
    }

    @Override
    public List<Saleorder> getHcOrderList(Saleorder saleorder) {
        return saleorderMapper.getHcOrderList(saleorder);
    }

    @Override
    public Saleorder saveBDAddSaleorder(OrderData orderData) {
        Long time = DateUtil.sysTimeMillis();
        Saleorder saleorder = new Saleorder();
        saleorder.setSaleorderNo(orderData.getOrderNo()); //订单号
        saleorder.setOrderType(1);  //订单类型
        saleorder.setTraderContactName(orderData.getUsername());//联系人
        saleorder.setTraderContactMobile(orderData.getPhone());//手机
        saleorder.setTakeTraderName(orderData.getUsername());//收货公司
        saleorder.setTakeTraderContactName(orderData.getDeliveryUserName());//收货联系人名称
        saleorder.setTakeTraderArea(orderData.getDeliveryUserArea());//收货地区
        saleorder.setTakeTraderAddress(orderData.getDeliveryUserAddress());//收货地址
        saleorder.setTakeTraderContactMobile(orderData.getDeliveryUserPhone());//收货人手机
        saleorder.setTakeTraderContactTelephone(orderData.getDeliveryUserTel());//收货人电话
        saleorder.setBdtraderComments(orderData.getRemakes());//客户备注
        saleorder.setTotalAmount(orderData.getJxSalePrice());//订单总价
        saleorder.setPaymentMode(1);//支付方式
        saleorder.setPaymentType(419);//付款方式
        saleorder.setTraderName(orderData.getUsername());//如果没有关联erp客户就显示用户名称为注册时的企业名称
        saleorder.setStatus(orderData.getOrderStatus());//订单状态
        saleorder.setQuoteorderNo(orderData.getOrderNo());//报价单号
        saleorder.setSource(1);//订单来源BD
        saleorder.setValidStatus(0);//生效状态
        saleorder.setValidTime(0L);//生效时间
        saleorder.setAddTime(time);
        saleorder.setParentId(0);
        saleorder.setCompanyId(1);//南京公司
        saleorder.setPurchaseStatus(0);//采购状态0未采购（默认）
        saleorder.setLockedStatus(0);//锁定状态0未锁定
        saleorder.setInvoiceStatus(0);//开票状态0未开票
        saleorder.setInvoiceTime(0L);//开票时间
        saleorder.setPaymentStatus(0);//付款状态 0未付款
        saleorder.setPaymentTime(0L);//付款时间
        saleorder.setDeliveryStatus(0);//发货状态0未发货
        saleorder.setDeliveryTime(0L);//发货时间
        saleorder.setArrivalTime(0L);//客户收货时间
        saleorder.setServiceStatus(0);//售后状态 0无
        saleorder.setArrivalStatus(0);//客户收货状态0未收货
        saleorder.setHaveAdvancePurchase(0);//有提前采购 0
        saleorder.setAdvancePurchaseStatus(0);//0无提前采购需求
        saleorder.setAdvancePurchaseTime(0L);//提前采购申请时间
        saleorder.setIsUrgent(0);//是否加急0否1是
        BigDecimal a = new BigDecimal(0);
        saleorder.setUrgentAmount(a);//加急费用
        saleorder.setHaveCommunicate(0);//有无沟通记录
        saleorder.setSyncStatus(0);//0未同步
        saleorder.setIsSendInvoice(1);//是否寄送发票
		// add by Tomcat.Hui 2020/2/4 3:35 下午 .Desc: VDERP-1889 线上BD订单默认发票类型修改. start
		//线上订单（BD订单）生成后，默认发票类型改为【13%增值税专用发票（寄送）】
		saleorder.setInvoiceType(972);
		// add by Tomcat.Hui 2020/2/4 3:35 下午 .Desc: VDERP-1889 线上BD订单默认发票类型修改. end
        saleorder.setIsDelayInvoice(0);//是否延迟开票0否1是'
        saleorder.setPrepaidAmount(orderData.getJxSalePrice());//预付金额
        User user = userMapper.getByUsername("jx.vedeng", 1);
        saleorder.setCreator(user.getUserId());
        if (orderData.getDeliveryLevel3Id() != null) {
            saleorder.setTakeTraderAreaId(Integer.valueOf(orderData.getDeliveryLevel3Id()));//地区id
        }
        saleorder.setCreateMobile(orderData.getPhone());
        // BD订单归属销售
        User belongUser = new User();
        if (orderData.getPhone() != null) {
            belongUser = userService.getBDUserInfoByMobile(orderData.getPhone());
            if (belongUser != null && belongUser.getUserId() != null) {
                saleorder.setUserId(belongUser.getUserId());
            }

            if (belongUser != null && belongUser.getOrgId() != null) {
                saleorder.setOrgId(belongUser.getOrgId());
            }
            if (belongUser != null && belongUser.getOrgName() != null) {
                saleorder.setOrgName(belongUser.getOrgName());
            }
            if (belongUser != null && belongUser.getTraderId() != null && belongUser.getTraderId() != 0) {
                //判断交易者下有无地址
                Integer deliveryLevel1Id = 0;
                if (orderData.getDeliveryLevel1Id() != null) {
                    deliveryLevel1Id = Integer.valueOf(orderData.getDeliveryLevel1Id());
                }
                Integer deliveryLevel2Id = 0;
                if (orderData.getDeliveryLevel2Id() != null) {
                    deliveryLevel2Id = Integer.valueOf(orderData.getDeliveryLevel2Id());
                }
                Integer deliveryLevel3Id = 0;
                if (orderData.getDeliveryLevel3Id() != null) {
                    deliveryLevel3Id = Integer.valueOf(orderData.getDeliveryLevel3Id());
                }
                Integer traderAddressId = vailTraderAddress(belongUser.getTraderId(), deliveryLevel1Id, deliveryLevel2Id, deliveryLevel3Id, orderData.getDeliveryUserAddress());
                saleorder.setTakeTraderAddressId(traderAddressId);
                saleorder.setTakeTraderAreaId(deliveryLevel3Id);
                //判断交易者联系人是否存在并保存
                saleorder.setTraderId(belongUser.getTraderId());
                Integer traderContactId = vailTraderContact(belongUser.getTraderId(), orderData.getDeliveryUserName(), orderData.getDeliveryUserTel(), orderData.getDeliveryUserPhone());
                saleorder.setTakeTraderContactId(traderContactId);
                //获取交易者信息
                Trader trader = traderMapper.getTraderInfoByTraderId(belongUser.getTraderId());

                if (trader != null) {
                    saleorder.setTraderName(trader.getTraderName());
                    saleorder.setCustomerType(trader.getCustomerType());
                    saleorder.setCustomerNature(trader.getCustomerNature());
                    if (trader.getCustomerNature().equals(466)) {//终端
                        saleorder.setTerminalTraderType(trader.getCustomerType());
                        saleorder.setTerminalTraderName(trader.getTraderName());
                        saleorder.setTerminalTraderId(trader.getTraderId());
                        //获取交易者经营区域
//						String areaIds = traderMapper.getTraderBussinessAreaByTraderId(belongUser.getTraderId());
                        String areaIds = trader.getAreaIds();
                        if (org.apache.commons.lang.StringUtils.isNotBlank(areaIds)) {
                            //查询地区
                            String traderBussinessArea = regionService.getRegionNameStringByMinRegionIds(areaIds);
                            String[] areaid = areaIds.split(",");
                            Integer areaid2 = Integer.valueOf(areaid[areaid.length - 1]);
                            saleorder.setSalesAreaId(areaid2);
                            saleorder.setSalesArea(traderBussinessArea);
                        }
                    }
                    //Integer traderContactId = vailTraderContact(trader.getTraderId(),orderData.getDeliveryUserName(),orderData.getDeliveryUserPhone(),orderData.getDeliveryUserTel());
                    saleorder.setTraderContactId(trader.getTraderContactId());
                    if (trader.getTraderContactName() != null && !trader.getTraderContactName().isEmpty()) {
                        saleorder.setTraderContactName(trader.getTraderContactName());
                    }
                    if (trader.getTraderContactMobile() != null && !trader.getTraderContactMobile().isEmpty()) {
                        saleorder.setTraderContactMobile(trader.getTraderContactMobile());
                    }
                    saleorder.setTraderContactTelephone(trader.getTraderContactTelephone());
                    saleorder.setTraderAreaId(trader.getAreaId());
                    saleorder.setTraderAddressId(trader.getTraderAddressId());
                    saleorder.setTraderAddress(trader.getTraderAddress());
                    saleorder.setCustomerLevelStr(trader.getCustomerLevelStr());
                    //查询地区
                    String area = regionService.getRegionNameStringByMinRegionIds(trader.getAreaIds());
                    saleorder.setTraderArea(area);
                }
            }
        }
        //保存订单商品
        List<OrderGoodsData> goodsList = orderData.getGoodsList();
        List<SaleorderGoods> salegoodsList = new ArrayList<SaleorderGoods>();
        for (OrderGoodsData orderGoodsData : goodsList) {
            SaleorderGoods saleorderGoods = new SaleorderGoods();
            saleorderGoods.setSku(orderGoodsData.getSkuNo());
            if (belongUser != null && belongUser.getUserId() != null) {
                saleorderGoods.setCreator(belongUser.getUserId());
                saleorderGoods.setUpdater(belongUser.getUserId());
            }
            saleorderGoods.setAddTime(DateUtil.sysTimeMillis());
            saleorderGoods.setNum(orderGoodsData.getProductNum());
            saleorderGoods.setModTime(DateUtil.sysTimeMillis());
            saleorderGoods.setGoodsId(goodsService.getGoodsIdBySku(orderGoodsData.getSkuNo()));
            saleorderGoods.setDeliveryDirect(0);
            saleorderGoods.setPrice(orderGoodsData.getJxSalePrice());
            saleorderGoods.setJxSalePrice(orderGoodsData.getJxSalePrice());
            salegoodsList.add(saleorderGoods);
        }
        saleorder.setGoodsList(salegoodsList);
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<Saleorder>> TypeRef2 = new TypeReference<ResultInfo<Saleorder>>() {
        };
        try {
            ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(
                    httpUrl + "order/saleorder/saveBDAddSaleorder.htm", saleorder, clientId, clientKey, TypeRef2);
            Saleorder res = (Saleorder) result2.getData();
            if(res != null){
				//VDERP-2263   订单售后采购改动通知
				updateSaleOrderDataUpdateTime(res.getSaleorderId(),null,OrderDataUpdateConstant.SALE_ORDER_GENERATE);
			}
            if (belongUser != null && belongUser.getUserId() != null && belongUser.getUserId() != 0) {
                //判读端erp客户是否禁用
                quoteService.getIsDisabled(res.getCreateMobile(), belongUser.getUserId(), res.getSaleorderNo(), res.getSaleorderId());
            } else {
                //发送消息推送
                quoteService.sendAllocation(orderData.getPhone(), null);
            }
            return res;
        } catch (IOException e) {
            logger.error("saveBDAddSaleorder", e);
            return null;
        }
    }

    @Override
    public Integer updateBDSaleStatus(OrderData orderData) {
        Saleorder saleorder = new Saleorder();
        Long time = System.currentTimeMillis();
        saleorder.setSaleorderNo(orderData.getOrderNo());
        if (orderData.getOrderStatus() == 1) {
            saleorder.setStatus(1);//订单状态--进行中
            saleorder.setValidStatus(1);//生效状态
            saleorder.setValidTime(time);//生效时间
            saleorder.setModTime(time);
            logger.info(JSONObject.fromObject(saleorder).toString());
        }
        if (orderData.getOrderStatus() == 3) {
            saleorder.setStatus(3);//订单状态--关闭
            saleorder.setCloseComments(orderData.getCloseComments());//订单关闭原因
        }
        if (saleorder != null) {
            Saleorder saleorderInfo = saleorderMapper.getSaleorderBySaleorderNo(saleorder);
            saleorder.setSaleorderId(saleorderInfo.getSaleorderId());
            int i = saleorderMapper.updateByPrimaryKeySelective(saleorder);
            //更新库存服务数据
            int i1 = warehouseStockService.updateOccupyStockService(saleorder, 0);
            return i;
        } else {
            return null;
        }
    }

    /* (non-Javadoc)
     * @see com.vedeng.order.service.SaleorderService#updateBDChangeErp(com.vedeng.trader.model.WebAccount, java.lang.Integer, java.lang.Integer)
     * BD订单更改erp
     */
    @Override
    public Integer updateBDChangeErp(WebAccount erpAccount, Integer traderId, Integer saleorderId) {
        logger.info("updateBDChangeErp----------------------------start");
        int i = 0;
        int j = 0;
        try {
            //BD订单更改erp客户和归属销售
            WebAccount webAccountInfo = webAccountMapper.getWebAccountInfo(erpAccount.getErpAccountId());
            List<Saleorder> saleorderList = saleorderMapper.getSaleOrderlistByStatusMobile(webAccountInfo.getMobile());
            User belongUser = userMapper.getBDUserInfoById(erpAccount.getErpAccountId());
            //获取交易者客户信息
            Trader traderName = traderMapper.getTraderNameByTraderContactId(erpAccount.getTraderContactId());
            //获取交易者公司信息
            Trader traderCompany = traderMapper.getTraderCompanyByTraderId(traderId, erpAccount.getTraderAddressId());
            //获取交易者经营区域
            String areaIds = traderMapper.getTraderBussinessAreaByTraderId(traderId);
            //保存到报价表
            Quoteorder record = new Quoteorder();
//			Saleorder sale = new Saleorder();
//			if(saleorderId!=null&&saleorderId>0) {
//				//如果之前没有traderId则使用订单id查找修改此订单
//				Saleorder saleorder2 =  getPojo(belongUser,traderName,sale,traderCompany,areaIds,traderId);
//				i = saleorderMapper.updateByPrimaryKeySelective(saleorder2);
//				record.setQuoteorderId(saleorder2.getQuoteorderId());
//				record = quoteorderMapper.getQuoteInfoByQuoteordeId(record);
//				record = getrecordPOJO(record,saleorder2,belongUser,traderId,traderCompany,traderName,areaIds);
//				j = quoteorderMapper.updateQuote(record);
//				if(i+j<2) {
//					i=0;
//					j=0;
//				}
            if (saleorderList != null) {
//				logger.info("saleorderList+++"+saleorderList.toString());
                //有traderId获得此用户所有订单批量修改
                for (Saleorder saleorder : saleorderList) {
                    Saleorder s = getPojo(belongUser, traderName, saleorder, traderCompany, areaIds, traderId);
                    s.setUserId(erpAccount.getUserId());
                    s.setTraderId(traderId);
                    i += saleorderMapper.updateByPrimaryKeySelective(s);
                    //更新报价单信息
                    record.setQuoteorderId(s.getQuoteorderId());
                    record = quoteorderMapper.getQuoteInfoByQuoteordeId(record);
                    record = getrecordPOJO(record, s, belongUser, traderId, traderCompany, traderName, areaIds);
                    record.setTraderId(traderId);
                    j += quoteorderMapper.updateQuote(record);
                }
                if (i < saleorderList.size() || j < saleorderList.size()) {
                    i = 0;
                    j = 0;
                }
            }

            // 定义反序列化 数据格式
            final TypeReference<ResultInfo<Saleorder>> TypeRef2 = new TypeReference<ResultInfo<Saleorder>>() {
            };
            try {
                UserData userData = new UserData();
                userData.setAccountId(webAccountInfo.getWebAccountId());
                if (traderCompany != null && traderCompany.getTraderName() != null) {


                    userData.setCompanyName(traderCompany.getTraderName());
                    String json = JsonUtils.translateToJson(userData);
//					logger.info(json);
//					String url = "http://wxtest.vedeng.com/user/updateCompanyName";
                    String url = mjxUrl + "/user/updateCompanyName";
                    CloseableHttpClient httpClient = HttpClientBuilder.create().build();
                    JSONObject result2 = NewHttpClientUtils.httpPost(url, JSONObject.fromObject(userData));
                    logger.info(result2.toString());
                }
            } catch (Exception e) {
//					e.printStackTrace();
                logger.error("updateBDChangeErp", e);
            }
            logger.info("updateBDChangeErp----------------------------END");
            return j + i;
//			}
        } catch (Exception e) {
            logger.error("updateBDChangeErp:操作异常：", e);
            logger.error(Contant.ERROR_MSG, e);
        }
        logger.info("updateBDChangeErp----------------------------END");
        return i + j;
    }

    private Quoteorder getrecordPOJO(Quoteorder s, Saleorder saleorder2, User belongUser, Integer traderId, Trader traderCompany, Trader traderName, String areaIds) {
        logger.info("getrecordPOJO--------------------------start----------");
        try {
            if (belongUser != null && belongUser.getOrgId() != null) {
                s.setOrgId(belongUser.getOrgId());
            }
            if (belongUser != null && belongUser.getUserId() != null) {
                s.setUserId(belongUser.getUserId());
            }
            s.setTraderId(traderId);
            if (traderCompany != null && traderCompany.getTraderName() != null) {
                s.setTraderName(traderCompany.getTraderName());
            }
            if (traderName != null && traderName.getTraderContactId() != null) {
                s.setTraderContactId(traderName.getTraderContactId());
            }
            if (traderName != null && traderName.getTraderContactName() != null) {
                s.setTraderContactName(traderName.getTraderContactName());
            }
            if (traderName != null && traderName.getTraderContactMobile() != null) {
                s.setMobile(traderName.getTraderContactMobile());
            }
            if (traderName != null && traderName.getTraderContactTelephone() != null) {
                s.setTelephone(traderName.getTraderContactTelephone());
            }
            if (traderCompany != null && traderCompany.getTraderAddressId() != null) {
                s.setTraderAddressId(traderCompany.getTraderAddressId());
            }
            if (traderCompany != null && traderCompany.getTraderAddress() != null) {
                s.setAddress(traderCompany.getTraderAddress());
            }
            if (traderCompany != null && traderCompany.getCustomerType() != null) {
                s.setCustomerType(traderCompany.getCustomerType());

            }
            if (traderCompany != null && traderCompany.getCustomerNature() != null) {
                s.setCustomerNature(traderCompany.getCustomerNature());
                if (traderCompany.getCustomerNature().equals(466)) {//终端
                    s.setTerminalTraderId(traderId);
                    s.setTerminalTraderName(traderCompany.getTraderName());
                    s.setTerminalTraderType(traderCompany.getCustomerType());
                    if (traderCompany.getAreaIds() != null) {
                        //查询经营地区
                        String traderBussinessArea = regionService.getRegionNameStringByMinRegionIds(traderCompany.getAreaIds());
                        String[] areaid = traderCompany.getAreaIds().split(",");
                        Integer areaid2 = Integer.valueOf(areaid[areaid.length - 1]);
                        s.setSalesAreaId(areaid2);
                        s.setSalesArea(traderBussinessArea);
                    }
                }
                if (traderCompany.getCustomerNature().equals(465)) {
                    s.setTerminalTraderId(0);
                    s.setTerminalTraderName("");
                    s.setTerminalTraderType(0);
                    s.setSalesAreaId(0);
                    s.setSalesArea("");
                }
            }
            //查询地区
            if (traderCompany != null && traderCompany.getAreaIds() != null) {
                String area = regionService.getRegionNameStringByMinRegionIds(traderCompany.getAreaIds());
                if (area != null) {
                    s.setArea(area);
                }
            }
            if (areaIds != null) {
                //查询经营地区
                String traderBussinessArea = regionService.getRegionNameStringByMinRegionIds(areaIds);
                String[] areaid = areaIds.split(",");
                Integer areaid2 = Integer.valueOf(areaid[areaid.length - 1]);
                s.setSalesAreaId(areaid2);
                s.setSalesArea(traderBussinessArea);
            }
            s.setModTime(System.currentTimeMillis());

        } catch (Exception e) {
            logger.error("getrecordPOJO:操作异常：", e);
            logger.error(Contant.ERROR_MSG, e);
        }
        logger.info("getrecordPOJO--------------------------END----------");
        return s;
    }

    private Saleorder getPojo(User belongUser, Trader traderName, Saleorder saleorder, Trader traderCompany, String areaIds, Integer traderId) {
        logger.info("getPojo--------------------------start----------");
        try {
            if (belongUser != null && belongUser.getUserId() != null) {
                saleorder.setUserId(belongUser.getUserId());
            }
            if (belongUser != null && belongUser.getOrgId() != null) {
                saleorder.setOrgId(belongUser.getOrgId());
            }
            if (belongUser != null && belongUser.getOrgName() != null) {
                saleorder.setOrgName(belongUser.getOrgName());
            }
            saleorder.setTraderId(traderId);
            saleorder.setTakeTraderContactId(traderId);
            saleorder.setTakeTraderId(traderId);

            if (traderCompany != null && traderCompany.getTraderName() != null) {
                saleorder.setTraderName(traderCompany.getTraderName());
            }
            if (traderCompany != null && traderCompany.getCustomerType() != null) {
                saleorder.setCustomerType(traderCompany.getCustomerType());

            }
            if (traderCompany != null && traderCompany.getCustomerNature() != null) {
                saleorder.setCustomerNature(traderCompany.getCustomerNature());
                if (traderCompany.getCustomerNature().equals(466)) {//终端
                    saleorder.setTerminalTraderId(traderId);
                    saleorder.setTerminalTraderName(traderCompany.getTraderName());
                    saleorder.setTerminalTraderType(traderCompany.getCustomerType());
                    if (traderCompany.getAreaIds() != null) {
                        //查询经营地区
                        String traderBussinessArea = regionService.getRegionNameStringByMinRegionIds(traderCompany.getAreaIds());
                        String[] areaid = traderCompany.getAreaIds().split(",");
                        Integer areaid2 = Integer.valueOf(areaid[areaid.length - 1]);
                        saleorder.setSalesAreaId(areaid2);
                        saleorder.setSalesArea(traderBussinessArea);
                    }
                }
                if (traderCompany.getCustomerNature().equals(465)) {
                    saleorder.setTerminalTraderId(0);
                    saleorder.setTerminalTraderName("");
                    saleorder.setTerminalTraderType(0);
                    saleorder.setSalesAreaId(0);
                    saleorder.setSalesArea("");
                }
            }
            if (traderName != null && traderName.getTraderContactId() != null) {
                saleorder.setTraderContactId(traderName.getTraderContactId());
            }
            if (traderName != null && traderName.getTraderContactName() != null) {
                saleorder.setTraderContactName(traderName.getTraderContactName());
            }
            if (traderName != null && traderName.getTraderContactMobile() != null) {
                saleorder.setTraderContactMobile(traderName.getTraderContactMobile());
            }
            if (traderName != null && traderName.getTraderContactTelephone() != null) {
                saleorder.setTraderContactTelephone(traderName.getTraderContactTelephone());
            }
            if (traderCompany != null && traderCompany.getAreaId() != null) {
                saleorder.setTraderAreaId(traderCompany.getAreaId());
            }
            if (traderCompany != null && traderCompany.getTraderAddressId() != null) {
                saleorder.setTraderAddressId(traderCompany.getTraderAddressId());
            }
            if (traderCompany != null && traderCompany.getTraderAddress() != null) {
                saleorder.setTraderAddress(traderCompany.getTraderAddress());
            }
            saleorder.setInvoiceTraderId(traderId);
            if (traderCompany != null && traderCompany.getTraderName() != null) {
                saleorder.setInvoiceTraderName(traderCompany.getTraderName());
                saleorder.setTakeTraderName(traderCompany.getTraderName());
            }
            if (traderName != null && traderName.getTraderContactId() != null) {
                saleorder.setInvoiceTraderContactId(traderName.getTraderContactId());
            }
            if (traderName != null && traderName.getTraderContactName() != null) {
                saleorder.setInvoiceTraderContactName(traderName.getTraderContactName());
            }
            if (traderName != null && traderName.getTraderContactMobile() != null) {
                saleorder.setInvoiceTraderContactMobile(traderName.getTraderContactMobile());
            }
            if (traderName != null && traderName.getTraderContactTelephone() != null) {
                saleorder.setInvoiceTraderContactTelephone(traderName.getTraderContactTelephone());
            }
            if (traderCompany != null && traderCompany.getTraderAddressId() != null) {
                saleorder.setInvoiceTraderAddressId(traderCompany.getTraderAddressId());
            }
            if (traderCompany != null && traderCompany.getTraderName() != null) {
                saleorder.setTakeTraderName(traderCompany.getTraderName());
            }
            //查询地区
            if (null != traderCompany && traderCompany.getAreaIds() != null) {
                String area = regionService.getRegionNameStringByMinRegionIds(traderCompany.getAreaIds());
                if (area != null) {
                    saleorder.setTraderArea(area);
                }
            }
            logger.info("getPojo-------------------------------END");
        } catch (Exception e) {
            //e.printStackTrace();
            logger.error("getPojo:操作异常：", e);
        }
        return saleorder;
    }



    @Override
    public Saleorder getsaleorderbySaleorderId(Integer saleorderId) {
        return saleorderMapper.getSaleOrderById(saleorderId);
    }

    /* (non-Javadoc)
     * @see com.vedeng.order.service.SaleorderService#updateVedengJX(java.lang.Integer)
     * BD订单审核完成推送数据
     */
    @Override
    public JSONObject updateVedengJX(Integer saleorderId) throws Exception {
        Saleorder saleorder = saleorderMapper.getSaleOrderById(saleorderId);
        //修改订单状态
        long time = System.currentTimeMillis();
        // changed by Tomcat.Hui 2020/3/3 7:45 下午 .Desc: VDERP-2067 删除用户确认流程 .
		//在前置的流程里,会使用verifiesInfoMapper更新主表T_SALEORDER的STATUS为1,这里只需去掉更新为4即可
        //saleorder.setStatus(4);
        saleorder.setModTime(time);
        saleorder.setBdMobileTime(time);
        OrderData orderData = new OrderData();
        orderData.setOrderNo(saleorder.getSaleorderNo());

		// changed by Tomcat.Hui 2020/3/9 10:38 上午 .Desc:  VDERP-2057 BD订单流程优化-ERP部分
		// 因为自定义首付款（0%）审核完成默认付账期变为部分付款 这里需要传订单状态给mjx .
		if (saleorder.getPaymentStatus() == 2) {
			orderData.setOrderStatus(3);
		} else if (saleorder.getPaymentStatus() == 1) {
			orderData.setOrderStatus(7);
		} else if (saleorder.getPaymentStatus() == 0) {
			orderData.setOrderStatus(2);
		}

        if (saleorder.getCreateMobile() == null || saleorder.getCreateMobile().isEmpty()) {
            ResultInfo r = new ResultInfo(-1, "创建人手机号为空");
            String result = r.toString();
            return JSONObject.fromObject(result);
        } else {
            WebAccount web = webAccountMapper.getWenAccountInfoByMobile(saleorder.getCreateMobile());
            orderData.setAccountId(web.getWebAccountId());//待定  =用户ID
        }
        orderData.setDeliveryUserName(saleorder.getTakeTraderContactName());
        orderData.setDeliveryUserArea(saleorder.getTakeTraderArea());
        orderData.setDeliveryUserAddress(saleorder.getTakeTraderAddress());
        orderData.setDeliveryUserPhone(saleorder.getTakeTraderContactMobile());
        orderData.setDeliveryUserTel(saleorder.getTakeTraderContactTelephone());
        orderData.setInvoiceTraderContactId(saleorder.getInvoiceTraderContactId());
        orderData.setInvoiceTraderContactName(saleorder.getInvoiceTraderContactName());
        orderData.setInvoiceTraderContactMobile(saleorder.getInvoiceTraderContactMobile());
        orderData.setInvoiceTraderContactTelephone(saleorder.getInvoiceTraderContactTelephone());
        orderData.setInvoiceTraderArea(saleorder.getInvoiceTraderArea());
        orderData.setInvoiceTraderAddress(saleorder.getInvoiceTraderAddress());
        orderData.setInvoiceUserPhone(saleorder.getInvoiceTraderContactTelephone());
        orderData.setAdditionalClause(saleorder.getAdditionalClause());//附加条款
        orderData.setIsSendInvoice(saleorder.getIsSendInvoice());//是否寄送发票
        orderData.setInvoiceType(saleorder.getInvoiceType());
        orderData.setCompanyName(saleorder.getTraderName());
        orderData.setCompanyId(saleorder.getTraderId());
        TraderAddress traderAddress = traderAddressMapper.getAddressInfoById(saleorder.getTakeTraderAddressId(), 1);
        orderData.setDeliveryAddressId(saleorder.getTakeTraderAddressId());
//		orderData.setDeliveryAreaIds(traderAddress.getAreaIds());
        List<SaleorderGoods> goodsList = goodsService.getGoodsPriceList(saleorderId);
        List<OrderGoodsData> goodsList2 = new ArrayList<OrderGoodsData>();
        BigDecimal m = null;
        Integer num = 0;
        for (SaleorderGoods s : goodsList) {
            OrderGoodsData orderGoodsData = new OrderGoodsData();
            orderGoodsData.setSkuNo(s.getSku());
            orderGoodsData.setJxSalePrice(s.getPrice());
            m = new BigDecimal(s.getNum());
            m = m.multiply(s.getPrice());
            orderGoodsData.setMarketMomey(m);
            orderGoodsData.setProductNum(s.getNum());
            orderGoodsData.setStoreRemarks(s.getGoodsComments());
            num += s.getNum();
            goodsList2.add(orderGoodsData);
        }
        orderData.setJxSalePrice(saleorder.getTotalAmount());
        orderData.setGoodsList(goodsList2);
        orderData.setOrderType(1);
		// changed by Tomcat.Hui 2020/3/6 9:10 上午 .Desc: VDERP-2057 BD订单流程优化-ERP部分 取消原有待确认状态 改为mjx查询order服务获取 .
        //orderData.setOrderStatus(4);//待用户确认
        orderData.setProductNum(num);
        try {
            String url = mjxUrl + "/order/verifyOrder";
            String json = JsonUtils.translateToJson(orderData);
            logger.info(json);
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            JSONObject result2 = NewHttpClientUtils.httpPost(url, JSONObject.fromObject(orderData));
            int i = saleorderMapper.updateByPrimaryKeySelective(saleorder);
            return result2;
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            logger.info("updateVedengJXErro+++" + e);
            return null;
        }

    }

    /* (non-Javadoc)
     * @see com.vedeng.order.service.SaleorderService#returnBDStatus(java.lang.Integer)
     * BD订单撤回
     */
    @Override
    public ResultInfo returnBDStatus(Integer saleorderId) {
        ResultInfo resultInfo = new ResultInfo();
        Saleorder saleorder = new Saleorder();
        saleorder.setSaleorderId(saleorderId);
        saleorder.setValidStatus(0);
        saleorder.setStatus(0);
        saleorder.setModTime(System.currentTimeMillis());
        saleorder.setBdMobileTime(0L);
        Saleorder saleOrderById = saleorderMapper.getSaleOrderById(saleorderId);
        //修改订单状态
        int i = saleorderMapper.updateByPrimaryKeySelective(saleorder);
        //修改审核状态
        int j = verifiesRecordService.updateBDStatus(saleorderId);
        //删除流程
        Map<String, Object> historic = actionProcdefService.getHistoric(processEngine,
                "saleorderVerify_" + saleorderId);
        HistoryService historyService = processEngine.getHistoryService();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        String key = "saleorderVerify_" + saleorderId;

        // add by Tomcat.Hui 2019/11/22 13:57 .Desc: VDERP-1448 BD订单在后台点击撤回订单，状态已更新，但无相应的操作按钮. start
        List<HistoricProcessInstance> hpis = historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(key).list();
        if (!CollectionUtils.isEmpty(hpis)) {
            for (HistoricProcessInstance hpi : hpis) {
                if (hpi != null) {
                    String processInstanceId = hpi.getId(); //流程实例ID
                    ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();// 使用流程实例ID查询
                    if (pi == null) {
                        //该流程实例已经完成了
                        historyService.deleteHistoricProcessInstance(processInstanceId);
                    } else {
                        //该流程实例未结束的
                        runtimeService.deleteProcessInstance(processInstanceId, "撤回订单");
                        historyService.deleteHistoricProcessInstance(processInstanceId);//(顺序不能换)
                    }
                }
            }
        }
        // add by Tomcat.Hui 2019/11/22 13:57 .Desc: VDERP-1448 BD订单在后台点击撤回订单，状态已更新，但无相应的操作按钮. end

        OrderData orderData = new OrderData();
        orderData.setOrderStatus(0);//订单变为待确认
        orderData.setOrderNo(saleOrderById.getSaleorderNo());
        WebAccount web = webAccountMapper.getWenAccountInfoByMobile(saleOrderById.getCreateMobile());
        orderData.setAccountId(web.getWebAccountId());
        try {
            String url = mjxUrl + "/order/updateOrderConfirmFinish";
            String json = JsonUtils.translateToJson(orderData);
            logger.info(json);
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            JSONObject result2 = NewHttpClientUtils.httpPost(url, JSONObject.fromObject(orderData));
            resultInfo.setData(result2);
        } catch (Exception e) {
            logger.error(Contant.ERROR_MSG, e);
            logger.info("returnBDStatusErro:", e);
        }
        if (i > 0 && j > 0) {
            resultInfo.setCode(0);
            resultInfo.setMessage("成功");
        } else {
            resultInfo.setCode(1);
            resultInfo.setMessage("失败");
        }
        return resultInfo;
    }

    /* (non-Javadoc)
     * @see com.vedeng.order.service.SaleorderService#ChangeEditSaleOrder(com.vedeng.order.model.Saleorder)
     * BD订单申请修改
     */
    @Override
    public JSONObject ChangeEditSaleOrder(Saleorder saleorder) {
        OrderData orderData = new OrderData();
        orderData.setOrderNo(saleorder.getSaleorderNo());
        WebAccount web = webAccountMapper.getWenAccountInfoByMobile(saleorder.getCreateMobile());
        orderData.setAccountId(web.getWebAccountId());//待定  =用户ID
        orderData.setDeliveryUserName(saleorder.getTakeTraderContactName());
        orderData.setDeliveryUserArea(saleorder.getTakeTraderArea());
        orderData.setDeliveryUserAddress(saleorder.getTakeTraderAddress());
        orderData.setDeliveryUserPhone(saleorder.getTakeTraderContactMobile());
        orderData.setDeliveryUserTel(saleorder.getTakeTraderContactTelephone());
        orderData.setInvoiceTraderContactId(saleorder.getInvoiceTraderContactId());
        orderData.setInvoiceTraderContactName(saleorder.getInvoiceTraderContactName());
        orderData.setInvoiceTraderContactMobile(saleorder.getInvoiceTraderContactMobile());
        orderData.setInvoiceTraderContactTelephone(saleorder.getInvoiceTraderContactTelephone());
        orderData.setInvoiceTraderArea(saleorder.getInvoiceTraderArea());
        orderData.setInvoiceTraderAddress(saleorder.getInvoiceTraderAddress());
        orderData.setInvoiceUserPhone(saleorder.getInvoiceTraderContactTelephone());
        orderData.setAdditionalClause(saleorder.getAdditionalClause());
        orderData.setIsSendInvoice(saleorder.getIsSendInvoice());//是否寄送发票
        orderData.setCompanyName(saleorder.getTraderName());
        orderData.setInvoiceType(saleorder.getInvoiceType());
        orderData.setCompanyId(saleorder.getTraderId());
        TraderAddress traderAddress = traderAddressMapper.getAddressInfoById(saleorder.getTakeTraderAddressId(), 1);
        orderData.setDeliveryAddressId(saleorder.getTakeTraderAddressId());
//		orderData.setDeliveryAreaIds(traderAddress.getAreaIds());
        List<SaleorderGoods> goodsList = getSaleorderGoodsById(saleorder);
        List<OrderGoodsData> goodsList2 = null;
        for (SaleorderGoods s : goodsList) {
            goodsList2 = new ArrayList<OrderGoodsData>();
            OrderGoodsData orderGoodsData = new OrderGoodsData();
            orderGoodsData.setSkuNo(s.getSku());
            orderGoodsData.setStoreRemarks(s.getGoodsComments());
            goodsList2.add(orderGoodsData);
        }
        orderData.setGoodsList(goodsList2);
        JSONObject result2 = new JSONObject();
        try {
            String url = mjxUrl + "/order/updateErpOrder";
            String json = JsonUtils.translateToJson(orderData);
            logger.info(json);
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            result2 = NewHttpClientUtils.httpPost(url, JSONObject.fromObject(orderData));
            logger.info(result2.toString());
        } catch (Exception e) {
            logger.error(Contant.ERROR_MSG, e);
            logger.info("ChangeEditSaleOrderErro+++" + e);
        }
        return result2;
    }

    @Override
    public List<User> getUserByCategory(Integer categoryId, Integer companyId) {
        return rCategoryJUserMapper.getUserByCategory(categoryId, companyId);
    }

    @Override
    public List<Saleorder> selectSaleorderNo(Saleorder saleorder) {
        return saleorderMapper.selectSaleorderNo(saleorder);
    }

    @Autowired
    private TraderAddressMapper traderAddressMapper;

    @Autowired
    private TraderContactGenerateMapper traderContactGenerateMapper;

    /**
     * 功能描述: 验证客户地址是否存在
     *
     * @param:
     * @return:
     * @auther: duke.li
     * @date: 2019/8/13 17:08
     */
    public Integer vailTraderAddress(Integer traderId, Integer provinceId, Integer cityId, Integer areaId, String address) {
        try {
            // 验证地址是否存在
            TraderAddress traderAddress = traderAddressMapper.getAddressInfoByAddress(traderId, CommonConstants.TRADER_TYPE_1, provinceId + "," + cityId + "," + areaId, areaId, address);
            if (traderAddress == null || traderAddress.getTraderAddressId() == null) {
                traderAddress = new TraderAddress();
                traderAddress.setTraderId(traderId);
                traderAddress.setTraderType(CommonConstants.TRADER_TYPE_1);
                traderAddress.setIsEnable(CommonConstants.ENABLE);
                traderAddress.setAreaId(areaId);
                traderAddress.setAreaIds(provinceId + "," + cityId + "," + areaId);
                traderAddress.setAddress(address);
                traderAddress.setCreator(548);//精选用户
                traderAddress.setAddTime(DateUtil.gainNowDate());
                traderAddress.setUpdater(548);
                traderAddress.setModTime(DateUtil.gainNowDate());
                traderAddressMapper.insertSelective(traderAddress);
            }
            return traderAddress.getTraderAddressId();
        } catch (Exception e) {
            logger.error("验证客户地址是否存在vailTraderAddress发生异常", e);
        }
        return null;
    }

    /**
     * 功能描述: 验证客户联系人是否存在
     *
     * @param: [traderId, name, telephone, mobile]
     * @return: java.lang.Integer
     * @auther: duke.li
     * @date: 2019/8/13 17:36
     */
    public Integer vailTraderContact(Integer traderId, String name, String telephone, String mobile) {
        try {
            TraderContactGenerate contactInfo = traderContactGenerateMapper.getContactInfo(traderId, CommonConstants.TRADER_TYPE_1, name, telephone, mobile);
            if (contactInfo == null || contactInfo.getTraderContactId() == null) {
                contactInfo = new TraderContactGenerate();
                contactInfo.setTraderId(traderId);
                contactInfo.setTraderType(CommonConstants.TRADER_TYPE_1);
                contactInfo.setIsEnable(CommonConstants.ENABLE);
                contactInfo.setName(name);
                contactInfo.setTelephone(telephone);
                contactInfo.setMobile(mobile);
                contactInfo.setSex(2);
                contactInfo.setIsOnJob(1);
                contactInfo.setCreator(548);//精选用户
                contactInfo.setAddTime(DateUtil.gainNowDate());
                contactInfo.setUpdater(548);
                contactInfo.setModTime(DateUtil.gainNowDate());
                traderContactGenerateMapper.insertSelective(contactInfo);
            }
            return contactInfo.getTraderContactId();
        } catch (Exception e) {
            logger.error("验证客户联系人是否存在vailTraderContact发生异常", e);
        }
        return null;
    }

    @Override
//	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public ResultInfo<?> closeBDSaleorder(Saleorder saleorder) {
        OrderData orderData = new OrderData();
        JSONObject result2 = null;
        try {
            saleorder = saleorderMapper.getSaleorderBySaleorderNo(saleorder);
            orderData.setCancelType(OrderConstant.CANCEL_BD_HAND);//手动关闭
            orderData.setOrderStatus(6);//订单关闭
            orderData.setOrderNo(saleorder.getSaleorderNo());
            WebAccount web = webAccountMapper.getWenAccountInfoByMobile(saleorder.getCreateMobile());
            orderData.setAccountId(web.getWebAccountId());
            List a = new ArrayList<>();
            orderData.setGoodsList(a);
            String url = mjxUrl + "/order/updateOrderConfirmFinish";
            XxlJobLogger.log("mjxurl++++" + url);
            String json = JsonUtils.translateToJson(orderData);
            logger.info("closeBDSaleorder+++++" + json);
            XxlJobLogger.log("json++++" + json);
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            result2 = NewHttpClientUtils.httpPost(url, JSONObject.fromObject(orderData));
            XxlJobLogger.log("result2+++++" + result2);
            logger.info("closeBDSaleorder++++" + result2.toString());
        } catch (Exception e) {
            logger.error(Contant.ERROR_MSG, e);
            XxlJobLogger.log("closeBDSaleorderErro++++" + e);
        }
        if (result2 != null) {
            return new ResultInfo(0, "操作成功");
        } else {
            return new ResultInfo();
        }
    }

    @Override
    public Integer isExistQuoteorderId(Integer quoteorderId) {
        return saleorderMapper.isExistQuoteorderId(quoteorderId);
    }


    /**
     * @param saleorderId
     * @description: getLackAccountPeriodAmount.
     * @notes: add by Tomcat.Hui 2019/9/5 10:44 .Desc: VDERP-1053  .
     * @author: Tomcat.Hui.
     * @date: 2019/9/5 11:42.
     * @return: java.math.BigDecimal.
     * @throws: .
     */
    @Override
    public BigDecimal getLackAccountPeriodAmount(Integer saleorderId) {
        BigDecimal lackAccountPeriodAmount = saleorderMapper.getSaleorderLackAccountPeriodAmount(saleorderId);
        return lackAccountPeriodAmount;
    }

    /**
     * @param saleorderId
     * @description: getPeriodAmount.
     * @notes: add by Tomcat.Hui 2019/9/5 10:44 .Desc: VDERP-1053  .
     * @author: Tomcat.Hui.
     * @date: 2019/9/5 14:41.
     * @return: java.math.BigDecimal.
     * @throws: .
     */
    @Override
    public BigDecimal getPeriodAmount(Integer saleorderId) {
        return saleorderMapper.getPeriodAmount(saleorderId);
    }

    /* (non-Javadoc)
     * @see com.vedeng.order.service.SaleorderService#getSaleOrderInfoBySaleorderNo(com.vedeng.order.model.Saleorder)
     * 订单状态，付款状态，发货状态，收货状态，审核状态，生效状态，开票状态
     * ，支付方式（0线上、1线下），支付类型:(1支付宝、2微信、3银行) 接口
     */
    @Override
    public List<Saleorder> getSaleOrderInfoBySaleorderNo(ArrayList<String> orderNoList,Integer type) {
        logger.info("getSaleOrderInfoBySaleorderNo:开始");
        List<Saleorder> saleorderList = new ArrayList<>();
        saleorderList = saleorderMapper.getSaleorderBySaleorderNoList(orderNoList);
        //将订单中非直发商品的发货状态排除后,重新判断订单发货状态
		if(type!=1) {
			for (Saleorder saleorder : saleorderList) {
				List<SaleorderGoods> orderGoods = saleorderGoodsMapper.getSaleorderGoodsById(saleorder);
				Integer i = 0;//全部发货数量
				Integer j = 0;//部分发货数量
				Integer k = 0;//订单普发商品数
				Integer l = 0;//直发商品数
				Integer a = 0;//商品总数
				for (SaleorderGoods goods : orderGoods) {//排除直发商品,判断订单发货状态
					a = a + 1;
					if (goods.getDeliveryDirect().equals(ErpConst.ZERO)) {//普发商品
						k = k + 1;
						if (goods.getSku().equals("V127063")) {//运费
							goods.setDeliveryStatus(ErpConst.TWO);
						}
						if (goods.getDeliveryStatus().equals(ErpConst.TWO)) {//全部发货
							i = i + 1;
						} else if (goods.getDeliveryStatus().equals(ErpConst.ONE)) {//部分发货
							j = j + 1;
						}
					} else if (goods.getDeliveryDirect().equals(ErpConst.ONE)) {
						if (goods.getNum() != 0) {
							goods.setBuyorderStatus(2);//数量不为0的直发商品采购状态为全部采购
						}
//						Integer num = goods.getNum();
//						if(num == null){
//							num = 0;
//						}
//						Integer buyNum = saleorderGoodsMapper.getGoodsBuyWarehouseStatusBySaleorderGoodsId(goods.getSaleorderGoodsId());
//						if(buyNum == null || buyNum.equals(0)){
//							goods.setBuyorderStatus(0);
//						}else if(buyNum < num){
//							goods.setBuyorderStatus(1);
//						}else if(buyNum.equals(num)){
//							goods.setBuyorderStatus(2);
//						}
						l = l + 1;
					}
				}
				saleorder.setGoodsList(orderGoods);
				//订单发货状态
				if (i.equals(k) || a.equals(l)) {
					saleorder.setDeliveryStatus(ErpConst.TWO);//全部发货
				} else if (j > 0) {
					saleorder.setDeliveryStatus(ErpConst.ONE);//部分发货
				} else if (i.equals(ErpConst.ZERO) && j.equals(ErpConst.ZERO)) {
					saleorder.setDeliveryStatus(ErpConst.ZERO);//未发货
				}
				logger.info("订单号为:" + saleorder.getSaleorderNo() + "全部发货数量:" + i + "," + "部分发货数量:" + j + "," + "订单普发商品数:" + k + "," + "直发商品数:" + l + "," + "商品总数:" + a);
			}
		}
        logger.info("getSaleOrderInfoBySaleorderNo:结束");
        return saleorderList;
    }

    @Override
    public Integer updateOrderDeliveryStatus(Saleorder saleorder) {
        logger.info("HC订单修改订单收货状态开始");
        Integer i = 0;
        Long time = DateUtil.gainNowDate();
        saleorder.setModTime(time);
        if (StringUtil.isNotEmpty(saleorder.getSaleorderNo())) {
			//全部收货
            if (saleorder.getArrivalStatus() != null && saleorder.getArrivalStatus().equals(ErpConst.TWO)) {
                saleorder.setWebTakeDeliveryTime(time);
				//全部发货
                saleorder.setDeliveryStatus(ErpConst.TWO);
                i = saleorderMapper.updateDeliveryStatusBySaleorderNo(saleorder);
            }
            Saleorder saleorder2 = saleorderMapper.getSaleorderBySaleorderNo(saleorder);
            List<SaleorderGoods> orderGoods = saleorderGoodsMapper.getSaleorderGoodsById(saleorder2);
            for (SaleorderGoods goods : orderGoods) {
                goods.setDeliveryStatus(ErpConst.TWO);//全部发货
                goods.setArrivalStatus(ErpConst.TWO);//全部收货
                goods.setModTime(time);
                int j = saleorderGoodsMapper.updateByPrimaryKey(goods);
            }
        }
        logger.info("HC订单修改订单收货状态结束");
        return i;
    }

    @Override
    public List<SaleorderGoodsVo> getNewSdList(Goods goods) {
        goods.setGoodsType(1);
        List<SaleorderGoodsVo> list = saleorderGoodsMapper.getNewSdList(goods);
        return sortSaleOrderList(list);
    }

    @Override
    public List<SaleorderGoodsVo> getactionLockList(Goods goods) {
        goods.setGoodsType(2);
        List<SaleorderGoodsVo> list = saleorderGoodsMapper.getNewSdList(goods);
        return sortSaleOrderList(list);
    }

    private List<SaleorderGoodsVo> sortSaleOrderList(List<SaleorderGoodsVo> list) {
        List<SaleorderGoodsVo> list2 = new ArrayList<>();
        List<SaleorderGoodsVo> list3 = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            for (SaleorderGoodsVo s : list) {
                if (s.getPaymentStatus() != null && s.getPaymentStatus() == 2) {
                    list2.add(s);
                } else {
                    list3.add(s);
                }
            }
            Collections.sort(list2, new Comparator<SaleorderGoodsVo>() {
                @Override
                public int compare(SaleorderGoodsVo o1, SaleorderGoodsVo o2) {
					Long paymentTime1 = o1.getPaymentTime();
					if(paymentTime1 == null){
						paymentTime1 = 0L;
					}
					Long paymentTime2 = o2.getPaymentTime();
					if(paymentTime2 == null){
						paymentTime2 = 0L;
					}
					Long time = paymentTime1 - paymentTime2;
                    if (time > 0) {
                        return 1;
                    } else if (time < 0) {
                        return -1;
                    }
                    return 0;
                }
            });
            Collections.sort(list3, new Comparator<SaleorderGoodsVo>() {
                @Override
                public int compare(SaleorderGoodsVo o1, SaleorderGoodsVo o2) {
					Long addTime1 = o1.getAddTime();
					if(addTime1 == null){
						addTime1 = 0L;
					}
					Long addTime2 = o2.getAddTime();
					if(addTime2 == null){
						addTime2 = 0L;
					}
					Long time = addTime1 - addTime2;
                    if (time > 0) {
                        return 1;
                    } else if (time < 0) {
                        return -1;
                    }
                    return 0;
                }
            });
            list.clear();
            list.addAll(list2);
            list.addAll(list3);
        }
        return list;
    }

    @Override
    public List<SaleorderGoods> getSaleorderGoods(Saleorder saleorder) {
        List<SaleorderGoods> saleorderGoods = saleorderGoodsMapper.getSaleorderGoodsById(saleorder);
        return saleorderGoods;
    }


//    @Override
//    public Saleorder getWebAccountId(Integer saleorderId) {
//        return saleorderMapper.getWebAccountId(saleorderId);
//    }
	@Override
	public List<Integer> getSaleOrderGoodsIdListByUserId(Integer proUserId) {
		return saleorderGoodsMapper.getSaleOrderGoodsIdListByUserId(proUserId);
	}

    @Override
    public List<SaleorderGoods> getSaleGoodsNoOutNumList(Integer saleorderId) {
        List<SaleorderGoods> list = saleorderGoodsMapper.getSaleGoodsNoOutNumList(saleorderId);
        return list;
    }


    @Override
    public Saleorder updateisOpenInvoice(Saleorder saleorder, List<SaleorderGoods> saleorderGoodsList) {
        try {
            Map<Integer, Map<String, Object>> invoiceNums = invoiceService.getInvoiceNums(saleorder);
            // changed by Tomcat.Hui 2019/12/2 21:13 .Desc: VDERP-1325 分批开票 APPLY_NUM改为申请中的数量. start
            BigDecimal applyNum = BigDecimal.ZERO;//有效的已申请数量
            Integer num = 0;//商品总数

            // add by Tomcat.Hui 2020/2/6 6:40 下午 .Desc: VDERP-1882 以订单商品总金额判断 而非订单商品总数量. start
			Map<Integer,BigDecimal> invoiceOccupiedAmount = invoiceService.getInvoiceOccupiedAmount(saleorder);
			BigDecimal alreadyTotalAmount = BigDecimal.ZERO;
			BigDecimal realAmount = BigDecimal.ZERO;
			for (SaleorderGoods g : saleorderGoodsList) {
				BigDecimal goodsAmount = BigDecimal.ZERO;

				if (saleorder.getOrderType().equals(5)) {
					goodsAmount = g.getMaxSkuRefundAmount().subtract(g.getAfterReturnAmount());
				} else {
					Integer goodsNum = g.getNum() - g.getAfterReturnNum();
					goodsAmount = g.getPrice().multiply(new BigDecimal(goodsNum));
				}

				BigDecimal alreadyAmount = invoiceOccupiedAmount.containsKey(g.getSaleorderGoodsId()) ? invoiceOccupiedAmount.get(g.getSaleorderGoodsId()) : BigDecimal.ZERO;
				alreadyTotalAmount = alreadyTotalAmount.add(alreadyAmount);
				realAmount = realAmount.add(goodsAmount);
				logger.info("订单ID：" + saleorder.getSaleorderNo() + " 订单商品: " + g.getSku() + " 实际金额：" + goodsAmount + " 已开票金额：" + alreadyAmount);
			}

            // add by Tomcat.Hui 2020/2/6 6:40 下午 .Desc: VDERP-1882 以订单商品总金额判断 而非订单商品总数量. end

			// 原有规则:1允许开票2存在开票申请待审核-不允许;;;;:3允许提前开票，4存在提前开票待审核-不允许::::5全部开票----0全部售后
            //现在 当为2和4 符合所有有效的开票数量小于订单商品数也可以
            if (alreadyTotalAmount.compareTo(realAmount) < 0) {
                if (saleorder.getIsOpenInvoice() != null) {
                    if (saleorder.getIsOpenInvoice().equals(2)) {
                        saleorder.setIsOpenInvoice(1);
                    } else if (saleorder.getIsOpenInvoice().equals(4)) {
                        saleorder.setIsOpenInvoice(3);
                    }
                }
            }
            // changed by Tomcat.Hui 2019/12/2 21:13 .Desc: VDERP-1325 分批开票 APPLY_NUM改为申请中的数量. end

        } catch (Exception e) {
            logger.error("计算是否允许开票出现异常 error:{}", e);
        }
        return saleorder;
    }

    @Override
    public ResultInfo updateOrderStatusByOrderNo(Saleorder saleorder) {
        int i = saleorderMapper.updateOrderStatusByOrderNo(saleorder);
        if (i == 1) {
            Saleorder s1 = saleorderMapper.getSaleorderBySaleorderNo(saleorder);
            //调用库存服务
            //关闭订单
            s1.setOperateType(0);
            warehouseStockService.updateOccupyStockService(s1, 0);
            logger.info("updateOrderStatusByOrderNo关闭成功,订单号:{}",saleorder.getSaleorderNo());
            return new ResultInfo(0, "操作成功");
        } else {
            return new ResultInfo();
        }
    }

	@Override
	public Integer queryOutBoundQuantity(SaleorderVo saleorderVo) {
    	saleorderVo.setType(1);
		List<SaleorderGoods> saleorderGoodslist = saleorderGoodsMapper.getActionGoodsInfo(saleorderVo);
		if(!CollectionUtils.isEmpty(saleorderGoodslist)) {
			Integer deliveryNum = 0;
			for (SaleorderGoods saleorderGoods : saleorderGoodslist) {
				deliveryNum += saleorderGoods.getDeliveryNum();
			}
			return deliveryNum;
		}
		return 0;
	}

	@Override
	public Saleorder getsaleorderId(String saleorderNo) {
		return saleorderMapper.getSaleOrderId(saleorderNo);
	}
    @Override
	public void updateOrderdeliveryDirect(Saleorder s) {
        List<SaleorderGoods> saleorderGoodslist = saleorderGoodsMapper.getSaleorderGoodsById(s);
        for (SaleorderGoods saleorderGoods : saleorderGoodslist) {
            if (saleorderGoods.getDeliveryDirect().equals(1)) {
                s.setDeliveryDirect(1);
                break;
            } else {
                s.setDeliveryDirect(0);
            }
        }
        saleorderMapper.updateDeliveryStatusBySaleorderNo(s);

    }


	@Override
	public Spu getSpu(String sku) {
		return saleorderGoodsMapper.getSku(sku);
	}

	public CoreSpuGenerate getSpuBase(Integer skuId) {
		return saleorderGoodsMapper.getSpuBase(skuId);
	}

	@Override
	public List<Integer> getSaleOrderGoodsIdListByUserIds(List<User> userList) {
		return saleorderGoodsMapper.getSaleOrderGoodsIdListByUserIds(userList);
	}
	//第一次物流改变评论
	@Override
	public int updateLogisticsComments(Integer saleorderId, String s) {
		return saleorderMapper.updateLogisticsComments(saleorderId,s);
	}

	@Override
	public Saleorder getWebAccountId(Integer saleorderId) {
		return saleorderMapper.getWebAccountId(saleorderId);
	}



     @Override
	public Integer getContractReturnOrderCount(SaleorderContract saleOrderContract,
											   String searchType) {

		Map<String, Object> paraMap = new HashMap<>();

		paraMap.put("saleOrderContract", saleOrderContract);


		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<SaleorderContract> list = null;
		Integer listCount = 0;
		if ("1".equals(searchType)) {

			listCount = saleorderMapper.getContractReturnOrderListCount(paraMap);
		} else {

			listCount = saleorderMapper.getContractReturnOrderNoqualityListCount(paraMap);
		}

		return listCount;
	}
	/*校验锁的状态*/
	@Override
	public void updateLockedStatus(Integer saleorderId) {
		saleorderMapper.updateLockedStatus(saleorderId);
	}

	/**
	* @Description: 更新收发货和发票状态
	* @Param:
	* @return:
	* @Author: addis
	* @Date: 2020/2/5
	*/

	public void updateState(Saleorder saleorder){
// 获取订单产品信息
		List<SaleorderGoods> goodsList =
				saleorderGoodsMapper.getSaleorderGoodsById(saleorder);
		List<ExpressDetail> expresseList = expressService.getSEGoodsNum(goodsList.stream().map(SaleorderGoods::getSaleorderGoodsId).collect(Collectors.toList()));

		if (null != goodsList) {
			// 销售商品只要有一个确认收货，当前订单中的特殊商品就自动改成已发货，已收货
			boolean arrStatus = false;
			Integer arrivalUserId = 0;
			for (SaleorderGoods sgs : goodsList) {
				if(sgs.getGoodsId() == 127063 || sgs.getGoodsId() == 251526 || sgs.getGoodsId() == 256675
						|| sgs.getGoodsId() == 253620 || sgs.getGoodsId() == 251462 || sgs.getGoodsId() == 140633){
					continue;
				}
				SaleorderGoods newGoods = new SaleorderGoods();
				Integer agvNum=sgs.getNum()-sgs.getAfterReturnNum(); //商品数量-售后已完成得数量
				if(expresseList!=null&&expresseList.size()>0){
					for(ExpressDetail expressDetail:expresseList){
						if(sgs.getSaleorderGoodsId().equals(expressDetail.getSaleOrderGoodsId())){

							if(expressDetail.getSendNum().equals(0)){
								newGoods.setDeliveryStatus(0);//未发货
							}else if(0<expressDetail.getSendNum()&&expressDetail.getSendNum()<agvNum){
								newGoods.setDeliveryStatus(1);//部分发货
							}else{
								newGoods.setDeliveryStatus(2);//全部发货
							}
							/*Express express = new Express();
							express.setOrderGoodsId(sgs.getSaleorderGoodsId());
							Express e = expressMapper.getSEGoodsNum(express);*/
							if (expressDetail.getArriveNum() == 0) {
								// 未签收
								newGoods.setArrivalStatus(0);
							} else if (0<expressDetail.getArriveNum()&&expressDetail.getArriveNum()<agvNum) {
								newGoods.setArrivalStatus(1);// 部分签收
							} else {
								// 全部签收
								newGoods.setArrivalStatus(2);

							}
							if (expressDetail.getSendNum()>0&&( newGoods.getDeliveryStatus()==1||newGoods.getDeliveryStatus()==2)) {
								arrStatus = true;
							}
							arrivalUserId = sgs.getArrivalUserId();

							newGoods.setSaleorderGoodsId(sgs.getSaleorderGoodsId());
							newGoods.setArrivalUserId(sgs.getArrivalUserId());
							newGoods.setArrivalTime(sgs.getArrivalTime());

							newGoods.setDeliveryTime(sgs.getArrivalTime());

							saleorderGoodsMapper.updateByPrimaryKeySelective(newGoods);
						}
					}
				}





			}
			if (arrStatus && saleorder.getSaleorderId() > 0) {
				// 查询销售单下的所有特殊商品
				SaleorderGoods sgs = new SaleorderGoods();
				sgs.setSaleorderId(saleorder.getSaleorderId());
				sgs.setArrivalStatus(2);
				sgs.setArrivalUserId(arrivalUserId);
				sgs.setArrivalTime(DateUtil.sysTimeMillis());
				sgs.setDeliveryStatus(2);
				sgs.setDeliveryTime(DateUtil.sysTimeMillis());
				saleorderGoodsMapper.updateTsSaleorderGoodsByParam(sgs);

			}else{
				// 查询销售单下的所有特殊商品
				SaleorderGoods sgs = new SaleorderGoods();
				sgs.setSaleorderId(saleorder.getSaleorderId());
				sgs.setArrivalStatus(0);
				sgs.setArrivalUserId(arrivalUserId);
				sgs.setArrivalTime(DateUtil.sysTimeMillis());
				sgs.setDeliveryStatus(0);
				sgs.setDeliveryTime(DateUtil.sysTimeMillis());
				saleorderGoodsMapper.updateTsSaleorderGoodsByParam(sgs);
			}

		}

		List<SaleorderGoods> sgList = saleorderGoodsMapper.getSaleorderGoodsNoSH(saleorder);
		Integer saleorderArrivalStatus = 1;
		Integer saleorderDeliveryStatus = 1;
		Integer arrivalStatus0Num = 0;// 未到货数量
		Integer arrivalStatus2Num = 0;// 全部到货数量
		Integer deliveryStatus0Num = 0;// 未发货数量
		Integer deliverytatus2Num = 0;// 全发货数量

		Integer size = sgList.size();
		for (SaleorderGoods sg : sgList) {
			// 判断直发商品的收货状态(直发商品的收货状态 == 普发商品的发货状态)
			if (sg.getDeliveryDirect() == 1
					|| (sg.getGoodsId() == 127063 || sg.getGoodsId() == 251526 || sg.getGoodsId() == 256675
					|| sg.getGoodsId() == 253620 || sg.getGoodsId() == 251462 || sg.getGoodsId() == 140633)) {

				//未收货
				if (sg.getArrivalStatus() == 0) {
					arrivalStatus0Num++;
					deliveryStatus0Num++;
				}

				//已收货
				if (sg.getArrivalStatus() == 2) {
					arrivalStatus2Num++;
					deliverytatus2Num++;
				}
			}
			// 判断普发商品收发货状态
			else if (sg.getGoodsId() != 127063 && sg.getGoodsId() != 251526 && sg.getGoodsId() != 256675
					&& sg.getGoodsId() != 253620 && sg.getGoodsId() != 251462 && sg.getGoodsId() != 140633) {
				/******************** 判断收货状态 **********************/
				Express express = new Express();
				express.setOrderGoodsId(sg.getSaleorderGoodsId());
				Express e = expressMapper.getSEGoodsNum(express);
				if (e.getAllnum() == 0) {
					size--;
					continue;
					// arrivalStatus2Num++;
				} else {
					if (e.getFnum() == 0) {
						// 未签收
						arrivalStatus0Num++;
					} else if (e.getAllnum() > e.getFnum()) {
						// 部分签收
					} else {
						// 全部签收
						arrivalStatus2Num++;
					}
				}
				/********************** 判断发货状态 **************************/
				if (sg.getDeliveryStatus() == 0) {
					deliveryStatus0Num++;
				}
				if (sg.getDeliveryStatus() == 2) {
					deliverytatus2Num++;
				}
			}

		}
		if (arrivalStatus0Num.intValue() == size) {
			saleorderArrivalStatus = 0;
		}
		if(size!=0){
			if (arrivalStatus2Num.intValue() == size) {
				saleorderArrivalStatus = 2;
			}
		}

		if (deliveryStatus0Num.intValue() == size) {
			saleorderDeliveryStatus = 0;
		}
		if(size!=0){
			if (deliverytatus2Num.intValue() == size) {
				saleorderDeliveryStatus = 2;
			}
		}


		Saleorder saleorderExtra = new Saleorder();
		saleorderExtra.setSaleorderId(saleorder.getSaleorderId());
		saleorderExtra.setArrivalStatus(saleorderArrivalStatus);
		saleorderExtra.setArrivalTime(DateUtil.sysTimeMillis());
		saleorderExtra.setDeliveryStatus(saleorderDeliveryStatus);
		saleorderExtra.setDeliveryTime(DateUtil.sysTimeMillis());
		saleorderMapper.updateByPrimaryKeySelective(saleorderExtra);
	}



	/**
	* @Description: 更新发票状态
	* @Param:
	* @return:
	* @Author: addis
	* @Date: 2020/2/5
	*/

	public void InvoiceState(Integer SaleorderId,Integer userId){
		Invoice invoice=new Invoice();
		invoice.setRelatedId(SaleorderId);
		invoice.setUpdater(userId);
		invoice.setModTime(DateUtil.gainNowDate());
		// 查询已开票金额
		BigDecimal openInvoiceAmount =
				invoiceMapper.getSaleOpenInvoiceAmount(invoice.getRelatedId());
		// 修改销售订单发票状态
		if(openInvoiceAmount==null){
			invoice.setAmountCount(new BigDecimal(0));
		}else{
			invoice.setAmountCount(openInvoiceAmount);
		}

		//logger.info("订单号: " + saleorderParam.getSaleorderNo() + " 开始判断是否全部开票,已开票金额: " + openInvoiceAmount );

		// add by Tomcat.Hui 2019/12/16 13:40 .Desc: VDERP-1686 开票申请是否开完票，改为根据开票金额与开票申请的金额进行比较来判断. start
		//加入订单类型,SQL里有用到
		Saleorder order = saleorderMapper.getSaleOrderById(invoice.getRelatedId());
		invoice.setOrderType(order.getOrderType());
		// add by Tomcat.Hui 2019/12/16 13:40 .Desc: VDERP-1686 开票申请是否开完票，改为根据开票金额与开票申请的金额进行比较来判断. end
		invoiceMapper.updateSaleInvoiceStatus(invoice);// 多条sql返回结果不确定
	}


	@Override
	public Map<String, Object> getFlowerPrintOutListPage(HttpServletRequest request, Saleorder saleorder, Page page) {
		String[] split = printOutTrader.split(",");
		List<String> traderNameList = new ArrayList<>();
		for (String traderName : split) {
			if(!StringUtils.isEmpty(traderName))
			traderNameList.add(traderName);
		}
		//获取宝石花客户id
    	List<Integer> traderIdList = traderMapper.getFlowerTraderId(traderNameList);
		saleorder.setTraderIdList(traderIdList);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("saleorder", saleorder);
		List<Saleorder> list = saleorderMapper.getFlowerPrintOutListPage(map);
		map.put("saleorderList",list);
		return map;
	}


}