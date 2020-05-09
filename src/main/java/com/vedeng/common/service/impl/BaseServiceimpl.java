package com.vedeng.common.service.impl;

import java.util.List;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.vedeng.aftersales.dao.AfterSalesGoodsMapper;
import com.vedeng.aftersales.dao.AfterSalesMapper;
import com.vedeng.aftersales.model.AfterSales;
import com.vedeng.common.constant.OrderDataUpdateConstant;
import com.vedeng.common.util.EmptyUtils;
import com.vedeng.order.dao.BuyorderGoodsMapper;
import com.vedeng.order.dao.BuyorderMapper;
import com.vedeng.order.dao.SaleorderGoodsMapper;
import com.vedeng.order.dao.SaleorderMapper;
import org.activiti.editor.language.json.converter.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.beust.jcommander.internal.Lists;
import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.authorization.dao.OrganizationMapper;
import com.vedeng.authorization.dao.RegionMapper;
import com.vedeng.authorization.dao.UserMapper;
import com.vedeng.authorization.model.Organization;
import com.vedeng.authorization.model.Region;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.service.BaseService;
import com.vedeng.common.shiro.JedisUtils;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.common.util.ObjectUtils;
import com.vedeng.system.dao.SysOptionDefinitionMapper;
import com.vedeng.system.model.SysOptionDefinition;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.*;

@Service("baseService")
public class BaseServiceimpl implements BaseService {
	@Value("${http_url}")
	protected String httpUrl;

	@Value("${client_id}")
	protected String clientId;

	@Value("${client_key}")
	protected String clientKey;
	@Value("${orderIsGoodsPeer_Time}")
	protected Long orderIsGoodsPeerTime;

	@Value("${file_url}")
	protected String picUrl;
	@Value("${redis_dbtype}")
	protected String dbType;// 开发redis，测试redis
	@Value("${stock_url}")
	protected String stockUrl;

	// #########kingdlee#########
	@Value("${kingdlee_key}")
	protected String kingdleeKey;// 密钥
	@Value("${kingdlee_receamount_url}")
	protected String kingdleeReceamountUrl;// 收款
	@Value("${kingdlee_openinvoice_url}")
	protected String kingdleeOpeninvoiceUrl;// 开票
	@Value("${kingdlee_payamount_url}")
	protected String kingdleePayamountUrl;// 付款
	@Value("${kingdlee_receinvoice_url}")
	protected String kingdleeReceinvoiceUrl;// 收票
	// #########kingdlee#########

	@Value("${call_url}")
	protected String callUrl;

	@Value("${call_namespace}")
	protected String callNamespace;

	@Value("${api_url}")
	protected String apiUrl;

	@Value("${rest_db_url}")
	protected String restDbUrl;
	
	@Value("${mjx_url}")
	protected String mjxUrl;

	@Value("${oss_url}")
	protected String ossUrl;

//	protected String mjxUrl="http://172.16.3.100:8280/";
	@Resource
	private RegionMapper regionMapper;
	@Resource
	protected UserMapper userMapper;
	@Resource
	private OrganizationMapper organizationMapper;
	@Resource
	private SysOptionDefinitionMapper sysOptionDefinitionMapper;

	@Resource
	private SaleorderMapper saleorderMapper;
	@Resource
	private SaleorderGoodsMapper saleorderGoodsMapper;

	@Resource
	private BuyorderMapper buyorderMapper;
	@Resource
	private BuyorderGoodsMapper buyorderGoodsMapper;

	@Resource
	private AfterSalesGoodsMapper afterSalesGoodsMapper;
	@Resource
	private AfterSalesMapper afterSalesMapper;


	@Value("${printOut_trader}")
	protected  String printOutTrader;

	static int cacheSecond=3600;

	/**
	 * @Fields logger : TODO日志
	 */
	public static Logger logger = LoggerFactory.getLogger(BaseServiceimpl.class);

	/**
	 * <b>Description:</b><br>
	 * 获取地址
	 * 
	 * @param areaId
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年5月16日 下午5:30:03
	 */
	protected String getAddressByAreaId(Integer areaId) {
		if (ObjectUtils.isEmpty(areaId)) {
			return null;
		}
		Region re = getRegionByAreaId(areaId);
		StringBuffer sb = new StringBuffer();
		if(re != null) {
			if (re.getRegionType() == 3) {
				String zoneName = re.getRegionName();
				re = getRegionByAreaId(re.getParentId());
				String cityName = re.getRegionName();
				re = getRegionByAreaId(re.getParentId());
				String provName = re.getRegionName();
				sb.append(provName).append(" ").append(cityName).append(" ").append(zoneName);
			} else if (re.getRegionType() == 2) {
				String cityName = re.getRegionName();
				re = getRegionByAreaId(re.getParentId());
				String provName = re.getRegionName();
				sb.append(provName).append(" ").append(cityName);
			} else {
				String provName = re.getRegionName();
				sb.append(provName);
			}
		}
		return sb.toString();
	}

	/**
	 * <b>Description:</b><br>
	 * 获取当前地区
	 * 
	 * @param areaId
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年10月26日 下午2:05:46
	 */
	private Region getRegionByAreaId(Integer areaId) {
		Region region = null;
		if (JedisUtils.exists(dbType + ErpConst.KEY_PREFIX_REGION_OBJECT + areaId)) {
			String json = JedisUtils.get(dbType + ErpConst.KEY_PREFIX_REGION_OBJECT + areaId);
			if (json == null || "".equals(json)) {
				return null;
			}
			JSONObject jsonObject = JSONObject.fromObject(json);
			region = (Region) JSONObject.toBean(jsonObject, Region.class);
		} else {
			region = regionMapper.getRegionById(areaId);
			if (region != null) {
				JedisUtils.set(dbType + ErpConst.KEY_PREFIX_REGION_OBJECT + areaId,
						JsonUtils.convertObjectToJsonStr(region), ErpConst.ZERO);
			}
		}
		return region;
	}

	/**
	 * <b>Description:</b><br>
	 * 查询用户名
	 * 
	 * @param userId
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年8月25日 上午10:59:41
	 */
	protected String getUserNameByUserId(Integer userId) {
		if (ObjectUtils.notEmpty(userId)) {
			User us = userMapper.selectByPrimaryKey(userId);
			if (us == null) {
				return null;
			} else {
				return us.getUsername();
			}
		} else {
			return null;
		}
	}

	/**
	 * <b>Description:</b><br>
	 * 查询部门名称
	 * 
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年8月25日 上午10:59:41
	 */
	protected String getOrgNameByOrgId(Integer orgId) {
		if (ObjectUtils.notEmpty(orgId)) {
			Organization org = organizationMapper.selectByPrimaryKey(orgId);
			if (org == null) {
				return null;
			} else {
				return org.getOrgName();
			}
		} else {
			return null;
		}
	}

	/**
	 * <b>Description:</b><br>
	 * 根据人员查询部门名称
	 * 
	 * @param userId
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年8月25日 上午10:59:41
	 */
	protected String getOrgaNameByUserId(Integer userId) {
		if (ObjectUtils.notEmpty(userId)) {
			Organization org = organizationMapper.getOrgNameByUserId(userId);
			if (org == null) {
				return null;
			} else {
				return org.getOrgName();
			}
		} else {
			return null;
		}
	}

	/**
	 * <b>Description:</b><br>
	 * 根据父id查询子列表
	 * 
	 * @param parentId
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年9月15日 上午9:21:48
	 */
	@Override
	public List<SysOptionDefinition> getSysOptionDefinitionListByParentId(Integer parentId) {
		if (parentId == null || parentId == 0) {
			return null;
		}
		SysOptionDefinition sod = new SysOptionDefinition();
		sod.setParentId(parentId);
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<SysOptionDefinition>>> TypeRef2 = new TypeReference<ResultInfo<List<SysOptionDefinition>>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(httpUrl + ErpConst.GET_SYSTEM_OPTION_LIST, sod,
					clientId, clientKey, TypeRef2);
			if (result == null || result.getCode() != 0) {
				return null;
			}
			List<SysOptionDefinition> list = (List<SysOptionDefinition>) result.getData();
			JedisUtils.set(dbType + ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST + parentId,
					JsonUtils.convertConllectionToJsonStr(list), cacheSecond);
			return list;
		} catch (Exception e) {
			logger.error("------BaseServiceimpl------字典数据集合加载失败!---------",e);
			throw new RuntimeException();

		}
	}

	/**
	 * <b>Description:</b><br>
	 * 查询字典表对象
	 * 
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年9月15日 上午11:01:34
	 */
	@Override
	public SysOptionDefinition getSysOptionDefinitionById(Integer sysOptionDefinitionId) {
		if (sysOptionDefinitionId == null || sysOptionDefinitionId == 0) {
			return new SysOptionDefinition();
		}
		if (JedisUtils.exists(dbType + ErpConst.KEY_PREFIX_DATA_DICTIONARY_OBJECT + sysOptionDefinitionId)) {
			String jsonStr = JedisUtils
					.get(dbType + ErpConst.KEY_PREFIX_DATA_DICTIONARY_OBJECT + sysOptionDefinitionId);
			JSONObject json = JSONObject.fromObject(jsonStr);
			SysOptionDefinition sysOptionDefinition = (SysOptionDefinition) JSONObject.toBean(json,
					SysOptionDefinition.class);
			return sysOptionDefinition;
		} else {
			SysOptionDefinition sod = new SysOptionDefinition();
			sod.setSysOptionDefinitionId(sysOptionDefinitionId);
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<SysOptionDefinition>> TypeRef2 = new TypeReference<ResultInfo<SysOptionDefinition>>() {
			};
			try {
				ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(httpUrl + ErpConst.GET_SYSTEM_OPTION_OBJECT,
						sod, clientId, clientKey, TypeRef2);
				if (result == null || result.getCode() != 0) {
					return new SysOptionDefinition();
				}
				SysOptionDefinition sysOptionDefinition = (SysOptionDefinition) result.getData();
				JedisUtils.set(dbType + ErpConst.KEY_PREFIX_DATA_DICTIONARY_OBJECT + sysOptionDefinitionId,
						JsonUtils.convertObjectToJsonStr(sysOptionDefinition), cacheSecond);
				return sysOptionDefinition;
			} catch (Exception e) {
				logger.error("------BaseServiceimpl------字典数据对象加载失败!---------");
				throw new RuntimeException();

			}
		}

	}

	/**
	 * <b>Description:</b><br>
	 * 取缓存数据（字典库）
	 * 
	 * @param parentId
	 * @return
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2017年12月11日 下午1:11:33
	 */
	public List<SysOptionDefinition> getSysOptionDefinitionList(Integer parentId) {
		if (JedisUtils.exists(dbType + ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST + parentId)) {
			String jsonStr = JedisUtils.get(dbType + ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST + parentId);
			JSONArray json = JSONArray.fromObject(jsonStr);
			List<SysOptionDefinition> list = (List<SysOptionDefinition>) JSONArray.toCollection(json,
					SysOptionDefinition.class);
			return list;
		} else {
			List<SysOptionDefinition> list = this.getSysOptionDefinitionListByParentId(parentId);
			return list;
		}
	}
	@Override
	public List<SysOptionDefinition> getSysOptionDefinitionList(String optionType) {
		if (JedisUtils.exists(dbType + ErpConst.KEY_PREFIX_DATA_DICTIONARY_TYPE + optionType)) {
			String jsonStr = JedisUtils.get(dbType + ErpConst.KEY_PREFIX_DATA_DICTIONARY_TYPE + optionType);
			JSONArray json = JSONArray.fromObject(jsonStr);
			List<SysOptionDefinition> list = (List<SysOptionDefinition>) JSONArray.toCollection(json,
					SysOptionDefinition.class);
			return list;
		} else {

			List<SysOptionDefinition> list = sysOptionDefinitionMapper.getSysOptionDefinitionByOptionType(optionType);
			JedisUtils.set(dbType + ErpConst.KEY_PREFIX_DATA_DICTIONARY_TYPE + optionType,
					JsonUtils.convertConllectionToJsonStr(list), cacheSecond);
			return list;
		}
	}
	@Override
	public SysOptionDefinition getFirstSysOptionDefinitionList(String optionType) {
		List<SysOptionDefinition> result;
		if (JedisUtils.exists(dbType + ErpConst.KEY_PREFIX_DATA_DICTIONARY_TYPE + optionType)) {
			String jsonStr = JedisUtils.get(dbType + ErpConst.KEY_PREFIX_DATA_DICTIONARY_TYPE + optionType);
			JSONArray json = JSONArray.fromObject(jsonStr);
			List<SysOptionDefinition> list = (List<SysOptionDefinition>) JSONArray.toCollection(json,
					SysOptionDefinition.class);
			result = list;
		} else {
			List<SysOptionDefinition> list = sysOptionDefinitionMapper.getSysOptionDefinitionByOptionType(optionType);
			if (CollectionUtils.isEmpty(list)) {
				list = Lists.newArrayList();
			}
			JedisUtils.set(dbType + ErpConst.KEY_PREFIX_DATA_DICTIONARY_TYPE + optionType,
					JsonUtils.convertConllectionToJsonStr(list), cacheSecond);
			result = list;
		}
		if (CollectionUtils.isEmpty(result)) {
			return new SysOptionDefinition();
		}
		return result.get(0);
	}


	/**
	 * 获取配置apiUrl
	 * 
	 * @return
	 */
	@Override
	public String getApiUri() {
		return apiUrl;
	}


	/**
	 *
	 * <b>Description: 根据id查询数字字典值</b><br>
	 * @param defaultValue  默认值
	 * @param optionType
	 * @return
	 * <b>Author: Franlin.wu</b>
	 * <br><b>Date: 2018年12月14日 下午2:37:40 </b>
	 */
	public String getConfigStringByDefault(String defaultValue, String optionType) {
		logger.debug("查询id：{}, 默认值：{}", optionType, defaultValue);

		try
		{
			// 根据id查询数字字典值
			SysOptionDefinition option = getFirstSysOptionDefinitionList(optionType);
			if(null != option && EmptyUtils.isNotBlank(option.getTitle())) {
				defaultValue = option.getTitle();
			}
		}
		catch(Exception e)
		{
			logger.error("根据id查询数字字典配置发生异常", e);
		}

		return defaultValue;
	}

	private  ExecutorService service = new ThreadPoolExecutor(4, 4,
			0L, TimeUnit.MILLISECONDS,  new LinkedBlockingQueue<Runnable>(1024),
			new ThreadFactoryBuilder().setNameFormat("updateOrderDataTime-pool-%d").build(), new ThreadPoolExecutor.AbortPolicy());
	/**
	 * 1.订单生成<p>-
	 * 2.订单生效审核<p>-
	 * 3.订单结款<p>-
	 * 4.订单出库<p>-
	 * 5.订单完结<p>订单完结逻辑在db开票及快递签收中因此可以合并.方法为saleorderDataService.getIsEnd
	 * 6.订单关闭<p>-
	 * 7.订单快递签收<p>-
	 * 8.订单开票<p>-自动纸质票开票方法:ST_INVOICE,为航信软件直调db
	 * 9.订单编辑<p>-
	 * 10.订单商品编辑<p>-
	 * 11.生成关联采购单<p>-
	 * 12.销售单关联退货换货售后单操作<p>-
	 * 更新销售单updateDataTime
	 * @param operateType 和上述编号一致
	 *  @Author:strange
	 *  @Date:10:15 2020-04-06
	 */
	@Override
	public void updateSaleOrderDataUpdateTime(Integer orderId, Integer orderDetailId, String operateType) {
		service.execute(new Runnable() {
			@Override
			public void run() {
				try {
                    logger.info("销售单更新updataTime info:{}", orderId + "," + orderDetailId + "," + operateType);
					//如果是订单维度更新则订单和订单商品表一起更新
					//如果是订单商品更新则更新订单和相应商品
					if (orderId == null && orderDetailId == null) {
						return;
					}
					if (orderId != null && orderDetailId == null) {
						//更新订单和商品
						saleorderMapper.updateDataTimeByOrderId(orderId);
						saleorderGoodsMapper.updateDataTimeByOrderId(orderId);
					}else if (orderDetailId != null) {
						//更新订单和当前商品
						saleorderGoodsMapper.updateDataTimeByDetailId(orderDetailId);
						saleorderMapper.updateDataTimeByDetailId(orderDetailId);
					}
				} catch (Exception e) {
					logger.error("销售单更新updataTime info  error"+orderId + "," + orderDetailId + "," + operateType, e);
				}
			}
		});
	}
	/**
	 * 1.备货单生成<p>-
	 * 2.采购单生成-更新销售单<p>-
	 * 3.采购单审核<p>-
	 * 4.采购单付款<p>-
	 * 5.采购单入库<p>-
	 * 6.采购单完成<p>
	 * 7.采购单关闭<p>-
	 * 8.采购录票<p>-
	 * 9.采购单编辑<p>-
	 * 10.采购单确认收货<p>-
	 * 11.采购单关联售后单操作<p>-
	 * 更新采购单updateDataTime
	 * @param operateType 和上述编号一致
	 * @Author:strange
	 * @Date:10:15 2020-04-06
	 */
	@Override
	public void updateBuyOrderDataUpdateTime(Integer orderId, Integer orderDetailId, String operateType) {
		service.execute(new Runnable() {
			@Override
			public void run() {
				try {
                    logger.info("采购单更新updataTime info:{}", orderId + "," + orderDetailId + "," + operateType);
					if (orderId == null && orderDetailId == null) {
						return;
					}
					if(orderId != null && orderDetailId == null){
						buyorderMapper.updateDataTimeByOrderId(orderId);
						buyorderGoodsMapper.updateDataTimeByOrderId(orderId);
					}else if(orderDetailId != null){
						buyorderMapper.updateDataTimeByDetailId(orderDetailId);
						buyorderGoodsMapper.updateDataTimeByDetailId(orderDetailId);
					}
					//采购单关联订单更新订单
					if(operateType.equals("2")){
						List<Integer> saleorderGoodsIdList = buyorderMapper.getSaleorderGooodsIdByBuyorderId(orderId);
						if(CollectionUtils.isNotEmpty(saleorderGoodsIdList)){
							for (Integer saleGoodsId : saleorderGoodsIdList) {
								updateSaleOrderDataUpdateTime(null,saleGoodsId, OrderDataUpdateConstant.SALE_ORDER_BUYORDER_OPT);
							}
						}
					}
				}catch (Exception e){
					logger.error("采购单更新updataTime info,error"+ orderId + "," + orderDetailId + "," + operateType, e);
				}
			}
		});
	}

	/**
	 *处理售后换货,售后退货
	 * 采购单和销售单售后情况
	 * 1.生成售后单<p>-
	 * 2.售后单生效审核<p>-
	 * 3.销售换货入库<p>-
	 * 4.销售换货出库<p>-
	 * 5.销售退货入库<p>-
	 * 6.采购退货出库<p>-
	 * 7.采购换货出库<p>-
	 * 8.采购换货入库<p>-
	 * 9.售后单执行退款运算<p>-
	 * 10.售后单完结<p>-
	 * 11.售后单关闭<p>-
	 * 12.售后单结款<p>-
	 * 13.售后录票<p>-
	 * 更新售后单updateDataTime
	 * @param operateType 和上述编号一致
	* @Author:strange
	* @Date:10:15 2020-04-06
	*/
	@Override
	public void updateAfterOrderDataUpdateTime(Integer afterSaleOrderId, Integer afterSaleOrderDetailId, String operateType) {
		service.execute(new Runnable() {
			@Override
			public void run() {
				try {
                    logger.info("售后单更新updataTime info:{}", afterSaleOrderId + "," + afterSaleOrderDetailId + "," + operateType);
					AfterSales afterSales = null;
					if (afterSaleOrderId == null && afterSaleOrderDetailId == null) {
						return;
					}
					if(afterSaleOrderId != null && afterSaleOrderDetailId == null){
						afterSalesMapper.updateDataTimeByOrderId(afterSaleOrderId);
						afterSalesGoodsMapper.updateDataTimeByOrderId(afterSaleOrderId);
						afterSales = afterSalesMapper.getAfterSalesById(afterSaleOrderId);
					}else if(afterSaleOrderDetailId != null){
						afterSalesMapper.updateDataTimeByDetailId(afterSaleOrderDetailId);
						afterSalesGoodsMapper.updateDataTimeByDetailId(afterSaleOrderDetailId);
						afterSales = afterSalesMapper.getAfterSalesTypeByAfterSalesGoodsId(afterSaleOrderDetailId);
					}
					//subjectType 535 销售 type 542 销售订单退票  540 销售订单换货  539 销售订单退货  543 销售订单退款
					//subjectType 536 采购 type 546 采购订单退货  547 采购订单换货
					if(afterSales != null){
						Integer subjectType = afterSales.getSubjectType();
						Integer type = afterSales.getType();
						if(subjectType.equals(535)){
							if(type.equals(542) || type.equals(540) || type.equals(539) || type.equals(543)) {
								updateSaleOrderDataUpdateTime(afterSales.getOrderId(), null, OrderDataUpdateConstant.SALE_ORDER_AFTERODER_OPT);
							}
						}else if(subjectType.equals(536)){
							if(type.equals(546) || type.equals(547)) {
								updateBuyOrderDataUpdateTime(afterSales.getOrderId(), null, OrderDataUpdateConstant.BUY_ORDER_AFTERORDER_OPY);
							}
						}
					}
				}catch (Exception e){
					logger.error("售后单更新updataTime info  error"+ afterSaleOrderId + "," + afterSaleOrderDetailId + "," + operateType, e);
				}
			}
		});
	}

	@Override
	public void updateTraderDataUpdateTime(Integer traderId, String operateType) {

	}
	@PreDestroy
	public void destory() {
		service.shutdown();
	}
}
