package com.vedeng.logistics.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.common.constants.Contant;
import com.ctrip.framework.apollo.core.utils.StringUtils;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.util.StringUtil;
import com.vedeng.logistics.dao.*;
import com.vedeng.logistics.model.*;
import com.vedeng.logistics.service.ExpressService;
import com.vedeng.order.dao.SaleorderGoodsMapper;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.aftersales.dao.AfterSalesDetailMapper;
import com.vedeng.aftersales.dao.AfterSalesMapper;
import com.vedeng.aftersales.model.AfterSales;
import com.vedeng.aftersales.model.AfterSalesDetail;
import com.vedeng.aftersales.model.vo.AfterSalesVo;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.OrderNoDict;
import com.vedeng.goods.dao.GoodsMapper;
import com.vedeng.logistics.model.vo.WarehouseGoodsOperateLogVo;
import com.vedeng.logistics.service.WarehouseOutService;
import com.vedeng.order.dao.SaleorderMapper;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.model.SaleorderGoods;
import com.vedeng.trader.dao.TraderMapper;
import com.vedeng.trader.model.Trader;

@Service("warehouseOutService")
public class WarehouseOutServiceImpl extends BaseServiceimpl implements WarehouseOutService {
	public static Logger logger = LoggerFactory.getLogger(WarehouseOutServiceImpl.class);

	@Autowired
	@Qualifier("saleorderMapper")
	private SaleorderMapper saleorderMapper;

	@Autowired
	private LendOutMapper lendOutMapper;

	@Autowired
	@Qualifier("afterSalesMapper")
	private AfterSalesMapper afterSalesMapper;

	@Autowired
	@Qualifier("traderMapper")
	private TraderMapper traderMapper;
	
	@Autowired
	@Qualifier("afterSalesDetailMapper")
	private AfterSalesDetailMapper afterSalesDetailMapper;
	
	@Autowired
	@Qualifier("goodsMapper")
	private GoodsMapper goodsMapper;
	@Autowired
	@Qualifier("barcodeMapper")
	private BarcodeMapper barcodeMapper;

	@Autowired
	private ExpressMapper expressMapper;

	@Autowired
	private ExpressService expressService;

	@Autowired
	private SaleorderGoodsMapper saleorderGoodsMapper;

	@Autowired
	@Qualifier("warehouseGoodsOperateLogMapper")
	private WarehouseGoodsOperateLogMapper warehouseGoodsOperateLogMapper;

	@Autowired
	private WarehouseGoodsStatusMapper warehouseGoodsStatusMapper;
	@Override
	public List<WarehouseGoodsOperateLog> getOutDetil(Saleorder saleorder) {
		List<WarehouseGoodsOperateLog> list = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<WarehouseGoodsOperateLog>>> TypeRef = new TypeReference<ResultInfo<List<WarehouseGoodsOperateLog>>>() {};
		String url=httpUrl + "warehouseout/getwarehouseoutlist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url,saleorder,clientId,clientKey, TypeRef);
			if(result != null){
				list = (List<WarehouseGoodsOperateLog>) result.getData();
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return list;
	}

	@Override
	public List<WarehouseGoodsOperateLog> getBarcodeOutDetil(Saleorder saleorder) {
		List<WarehouseGoodsOperateLog> list = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<WarehouseGoodsOperateLog>>> TypeRef = new TypeReference<ResultInfo<List<WarehouseGoodsOperateLog>>>() {};
		String url=httpUrl + "warehouseout/getwarehousebarcodeoutlist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url,saleorder,clientId,clientKey, TypeRef);
			list = (List<WarehouseGoodsOperateLog>) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return list;
	}

	@Override
	public WarehouseGoodsOperateLog getSMGoods(WarehouseGoodsOperateLog warehouseGoodsOperateLog) {
		// 接口调用
		String url = httpUrl + "warehousegoodsoperatelog/getsmwgolog.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<WarehouseGoodsOperateLog>> TypeRef2 = new TypeReference<ResultInfo<WarehouseGoodsOperateLog>>() {};
		try {
			ResultInfo<WarehouseGoodsOperateLog> result2 = (ResultInfo<WarehouseGoodsOperateLog>) HttpClientUtils.post(url, warehouseGoodsOperateLog, clientId, clientKey,TypeRef2);
			if (null == result2) {
				return null;
			}
			WarehouseGoodsOperateLog res = (WarehouseGoodsOperateLog) result2.getData();
			return res;
		} catch (IOException e) {
			return null;
		}
	}
	
	@Override
	public List<WarehouseGoodsOperateLog> getSGoods(WarehouseGoodsOperateLog warehouseGoodsOperateLog) {
		List<WarehouseGoodsOperateLog> list = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<WarehouseGoodsOperateLog>>> TypeRef = new TypeReference<ResultInfo<List<WarehouseGoodsOperateLog>>>() {};
		String url=httpUrl + "warehousegoodsoperatelog/getsgoods.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, warehouseGoodsOperateLog,clientId,clientKey, TypeRef);
			list = (List<WarehouseGoodsOperateLog>) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return list;
	}

	@Override
	public List<WarehouseGoodsOperateLog> getOutDetilList(Saleorder saleorder) {
		List<WarehouseGoodsOperateLog> list = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<WarehouseGoodsOperateLog>>> TypeRef = new TypeReference<ResultInfo<List<WarehouseGoodsOperateLog>>>() {};
		String url=httpUrl + "warehouseout/getwarehouseshoutlist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url,saleorder,clientId,clientKey, TypeRef);
			list = (List<WarehouseGoodsOperateLog>) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return list;
	}

	@Override
	@Transactional
	public LendOut saveLendOut(LendOut lendout) {
		lendout.setModTime(DateUtil.gainNowDate());
		lendout.setAddTime(DateUtil.gainNowDate());
		lendout.setLendOutStatus(0);
		SaleorderGoods goods = goodsMapper.getGoodsInfoById(lendout.getGoodsId());
		lendout.setGoodsName(goods.getGoodsName());
		lendout.setSku(goods.getSku());
		if(lendout.getLendOutType()==1) {//样品外借
			Trader trader = traderMapper.getTraderByTraderId(lendout.getTraderId());
			lendout.setTraderName(trader.getTraderName());
//			Integer type =traderMapper.getTraderTypeById(lendout.getTraderId());
//			lendout.setTraderType(type);//1经销商  2供应商
			lendout.setAfterSalesId(0);
			lendout.setAfterSalesNo("");
			Integer i = lendOutMapper.insertSelective(lendout);
			lendout.setLendOutNo(OrderNoDict.getOrderNum(lendout.getLendOutId(), OrderNoDict.LEND_OUT_TYPE));
			Integer j = lendOutMapper.updateByPrimaryKeySelective(lendout);
		}else {//售后垫货
			AfterSales aftersales = new AfterSales();
			aftersales.setAfterSalesNo(lendout.getAfterSalesNo());
			AfterSales aftersaleInfo = afterSalesMapper.getAfterSalesByNo(lendout.getAfterSalesNo());
			AfterSalesDetail afterSalesDetail = afterSalesDetailMapper.selectadtbyid(aftersaleInfo);
			lendout.setTraderName(afterSalesDetail.getTraderName());
			lendout.setAfterSalesId(aftersaleInfo.getAfterSalesId());
			lendout.setTraderId(afterSalesDetail.getTraderId());
			lendout.setTakeTraderAddressId(afterSalesDetail.getAddressId());
			lendout.setTakeTraderAreaId(afterSalesDetail.getAreaId());
			lendout.setTakeTraderContactId(afterSalesDetail.getTraderContactId());
//			Integer type =traderMapper.getTraderTypeById(lendout.getTraderId());
			lendout.setTraderType(1);//1经销商  2供应商
			Saleorder saleorderInfo = saleorderMapper.getSaleOrderById(aftersaleInfo.getOrderId());
			lendout.setLogisticsId(saleorderInfo.getLogisticsId());//物流公司id
			lendout.setLogisticsComments(saleorderInfo.getLogisticsComments());//物流备注
			lendout.setFreightDescription(saleorderInfo.getFreightDescription());//运费说明
			Integer i = lendOutMapper.insertSelective(lendout);
			lendout.setLendOutNo(OrderNoDict.getOrderNum(lendout.getLendOutId(), OrderNoDict.LEND_OUT_TYPE));
			Integer j = lendOutMapper.updateByPrimaryKeySelective(lendout);
		}
		LendOut lendoutResult = lendOutMapper.selectByPrimaryKey(lendout.getLendOutId());
		logger.info("saveLendOut:"+lendoutResult);
		return lendoutResult;
	}

	@Override
	public LendOut getLendOutInfo(LendOut lendout) {
		return lendOutMapper.selectByPrimaryKey(lendout.getLendOutId());
	}

	@Override
	public List<WarehouseGoodsOperateLogVo> getLendOutDetil(Saleorder saleorder) {
		List<WarehouseGoodsOperateLogVo> list = warehouseGoodsOperateLogMapper.getWarehouseLendOutList(saleorder);
		return list;
	}
	
	@Override
	public List<LendOut> getLendOutInfoList(AfterSalesVo afterSalesVo) {
		LendOut lendout = LendOut.getinstance();
		lendout.setAfterSalesId(afterSalesVo.getAfterSalesId());
		lendout.setAfterSalesNo(afterSalesVo.getAfterSalesNo());
		lendout.setLendOutStatus(0);//进行中
		lendout.setLendOutType(2);//售后换货
		return lendOutMapper.getLendOutInfoList(lendout);
	}

	@Override
	public Integer getkdNum(LendOut lendout) {
		lendout.setBusinessType(660);
		return lendOutMapper.getKdNum(lendout);
	}

	@Override
	public Integer getdeliveryNum(LendOut lendout) {
		Integer num = lendOutMapper.getdeliveryNum(lendout);
		return -num;
	}

	@Override
	public Integer updateLendoutDeliverNum(LendOut lendout) {
		return lendOutMapper.editDeliverNum(lendout);
	}
	/***
	 * 根据入库id获取条码信息
	 */
	@Override
	public Barcode getBarcodeByWarehouseGoodsOperateLogId(Integer warehouseGoodsOperateLogId) {
		Barcode barcode = barcodeMapper.getBarcodeByWarehouseGoodsOperateLogId(warehouseGoodsOperateLogId);
		return barcode;
	}

	@Override
	public String getBuyOrderNoByBarcodeId(Barcode barcode) {
		String orderNo= barcodeMapper.getBuyOrderNoByBarcodeId(barcode);
		return orderNo;
	}

	@Override
	public Integer updatefirstRegistraionInfo(List<WarehouseGoodsOperateLog> woList, List<WarehouseGoodsOperateLog> firstInfo, Integer titleType, String type_f, Integer printFlag) {
		StringBuffer sb = new StringBuffer();
		//更新为首营的信息
		for (WarehouseGoodsOperateLog log : woList) {
			for (WarehouseGoodsOperateLog flog : firstInfo) {
				if (log.getWarehouseGoodsOperateLogId().equals(flog.getWarehouseGoodsOperateLogId()) &&
						log.getGoodsId().equals(flog.getGoodsId())) {
					//存储温度
					log.setTemperaTure(flog.getTemperaTure());
					//产品注册证号/备案凭证编号
					log.setRegistrationNumber(flog.getRegistrationNumber());
					//产品注册证号/备案凭证编号
					log.setRecordNumber(flog.getRegistrationNumber());
					//生产企业名称
					log.setManufacturer(flog.getManufacturer());
					//生产企业许可证号或备案凭证编号'
					log.setProductCompanyLicence(flog.getProductCompanyLicence());
					//储运条件字典表
					log.setConditionOne(flog.getConditionOne());
					continue;
				}
			}
			try {
				BigDecimal num = new BigDecimal(-log.getNum());
				//排除外借出库
				if (log.getOperateType() != 10) {
					log.setMaxSkuRefundAmount(log.getPrice().multiply(num));
				}
				sb.replace(0, sb.length() + 1, "");
				//判断是否为医疗器械
				if (StringUtil.isNotEmpty(log.getRegistrationNumber()) && log.getRegistrationNumber().length() > 0) {
					if(log.getTitle() != null) {
						titleType = log.getTitle().equals("一类") ? 1 : 2;
					}else{
						titleType = 2;
					}
				} else {
					log.setRegistrationNumber(" ");
					log.setRecordNumber(" ");
					log.setManufacturer(" ");
					log.setProductionLicenseNumber(" ");
				}

				if (log.getConditionOne() != null && log.getConditionOne() == 983) {
					sb.append("常温  ");
				} else if (log.getConditionOne() != null && log.getConditionOne() == 984) {
					sb.append("冷藏  ");
				} else if (log.getConditionOne() != null && log.getConditionOne() == 985) {
					sb.append("冷冻  ");
				}
				if (StringUtil.isNotEmpty(log.getTemperaTure()) && log.getTemperaTure() != null) {
					sb.append(log.getTemperaTure()).append("度");
				}
				log.setTemperaTure(sb.toString());
				//是否是医疗器械
				Boolean ismachine = isMachineByGoodsId(log.getGoodsId());
				if(type_f.equals("2")&&!ismachine){
					updateBlankfield(log);
				}
				if(printFlag.equals(ErpConst.PRICE_PRINT_ORDERTYPE) || printFlag.equals(ErpConst.NOPRICE_PRINT_ORDERTYPE)){
					updateBlankfield2(log);
				}
			} catch (Exception e) {
				logger.error("打印出库单error:{}", e);
			}
		}
		return titleType;
	}
	/**
	* 是否是医疗器械 ture 是  false 否
	* @Author:strange
	* @Date:16:24 2020-03-12
	*/
	private Boolean isMachineByGoodsId(Integer goodsId) {
		Integer spuType = goodsMapper.getGoodsSpuType(goodsId);
		if(spuType!= null && spuType.equals(316)){
			return true;
		}
		return false;
	}

	private void updateBlankfield2(WarehouseGoodsOperateLog log) {
		if (StringUtils.isBlank(log.getRegistrationNumber())) {
			log.setRegistrationNumber("\\");
		}
		if (StringUtils.isBlank(log.getRecordNumber())) {
			log.setRecordNumber("\\");
		}
		if (StringUtils.isBlank(log.getManufacturer())) {
			log.setManufacturer("\\");
		}
		if (StringUtils.isBlank(log.getProductionLicenseNumber())) {
			log.setProductionLicenseNumber("\\");
		}
		if (StringUtils.isBlank(log.getTemperaTure())) {
			log.setTemperaTure("\\");
		}
		if (StringUtils.isBlank(log.getBatchNumber())) {
			log.setBatchNumber("\\");
		}
		if (StringUtils.isBlank(log.getMaterialCode())) {
			log.setMaterialCode("\\");
		}
		if(StringUtils.isBlank(log.getProductCompanyLicence())){
			log.setProductCompanyLicence("\\");
		}
		if(StringUtils.isBlank(log.getModel())){
			log.setModel("\\");
		}
		if(log.getExpirationDate() == null || log.getExpirationDate().equals(0L)){
			log.setTitle("\\");
		}
		if(log.getProductDate() == null || log.getProductDate().equals(0L)){
			log.setProductDateStr("\\");
		}
	}

	private void updateBlankfield(WarehouseGoodsOperateLog log) {
		if (StringUtils.isBlank(log.getRegistrationNumber())) {
			log.setRegistrationNumber("非医疗器械");
		}
		if (StringUtils.isBlank(log.getRecordNumber())) {
			log.setRecordNumber("非医疗器械");
		}
		if (StringUtils.isBlank(log.getManufacturer())) {
			log.setManufacturer("非医疗器械");
		}
		if (StringUtils.isBlank(log.getProductionLicenseNumber())) {
			log.setProductionLicenseNumber("非医疗器械");
		}
		if (StringUtils.isBlank(log.getTemperaTure())) {
			log.setTemperaTure("非医疗器械");
		}
		if (StringUtils.isBlank(log.getBatchNumber())) {
			log.setBatchNumber("非医疗器械");
		}
		if (StringUtils.isBlank(log.getMaterialCode())) {
			log.setMaterialCode("非医疗器械");
		}
		if(StringUtils.isBlank(log.getProductCompanyLicence())){
			log.setProductCompanyLicence("非医疗器械");
		}
		if(StringUtils.isBlank(log.getModel())){
			log.setModel("非医疗器械");
		}
		if(log.getExpirationDate() == null || log.getExpirationDate().equals(0L)){
			log.setTitle("非医疗器械");
		}
	}

	/**
	*保存快递详情和出入库记录关联关系
	* @Author:strange
	* @Date:14:14 2020-02-10
	*/
	@Override
	public void addExpressDeatilsWarehouse(Express express1) {

		List<ExpressDetail> expressDetailList = express1.getExpressDetail();
		if(CollectionUtils.isNotEmpty(expressDetailList)) {
			for (ExpressDetail expressDetail : expressDetailList) {
				if(expressDetail.getBusinessType() != null && expressDetail.getBusinessType().equals(496)){
					//获取未关联快递详情的出入库记录id
					List<Integer> warehouseLogIdList = warehouseGoodsOperateLogMapper.getWarehouseIdByExpressDetail(expressDetail);
					if(CollectionUtils.isNotEmpty(warehouseLogIdList)) {
						for (Integer warehouseLogId : warehouseLogIdList) {
							warehouseGoodsOperateLogMapper.updateExpressWarehouse(warehouseLogId);
							int i = warehouseGoodsOperateLogMapper.insertExpressWarehouse(warehouseLogId, expressDetail.getExpressDetailId());
						}
					}
				}
			}
		}
	}
	/**
	 *判断此出入库记录是否关联快递单
	 * @Author:strange
	 * @Date:14:39 2020-02-10
	 */
	@Override
	public Boolean isEnableExpress(Integer wlogId) {
		Integer expressId = expressMapper.getExpressIdByWlogId(wlogId);
		if(expressId != null){
			return true;
		}
		return false;
	}
	/**
	 *获取关联快递的出入库记录id
	 * @Author:strange
	 * @Date:19:28 2020-02-10
	 */
	@Override
	public List<Integer> getExpressWlogIds(Integer expressId) {

		return warehouseGoodsOperateLogMapper.getExpressWlogIds(expressId);
	}

	@Override
	public HashMap<String,BigDecimal> addMvAmoutinfo(List<WarehouseGoodsOperateLog> woList, Saleorder saleorder) {
		HashMap<String,BigDecimal> result = new HashMap<>();
		List<SaleorderGoods> saleorderGoodsList = saleorderGoodsMapper.getSaleorderGoodsById(saleorder);

		Map<Integer, SaleorderGoods> saleorderGoodsMap = saleorderGoodsList.stream().collect(Collectors.toMap(SaleorderGoods::getSaleorderGoodsId, goods -> goods));
		//获取物流信息
		List<Express> expressList = getExpressListBySaleOrderGoodsList(saleorderGoodsList);
		Integer expressId = saleorder.getExpressId();
		List<ExpressDetail> nowExpressDetail = expressMapper.getExpressDetailByExpressId(expressId);
		//获取运费
		BigDecimal expressPrice = getExpressPrice(saleorderGoodsList,expressId,expressList);
		//总金额
		BigDecimal totalPrice = BigDecimal.ZERO;
		for (WarehouseGoodsOperateLog warehouseGoodsOperateLog : woList) {
			totalPrice = totalPrice.add(warehouseGoodsOperateLog.getRealPrice().multiply(new BigDecimal(-warehouseGoodsOperateLog.getNum())));
		}
		//获取原有快递中商品数量
		HashMap<Integer,Integer> oldExpressDetailNumMap = getOldExpressDetailNumMap(expressId,expressList);

		//实付金额
		BigDecimal realTotalPrice = getRealTotalPrice(saleorderGoodsMap,oldExpressDetailNumMap,nowExpressDetail,expressId);

		//优惠券价格
		BigDecimal couponsPrice = totalPrice.subtract(realTotalPrice);

		realTotalPrice = realTotalPrice.add(expressPrice);

		result.put("expressPrice",expressPrice);
		result.put("totalPrice",totalPrice);
		result.put("realTotalPrice",realTotalPrice);
		result.put("couponsPrice",couponsPrice);

		return result;
	}

    private HashMap<Integer, Integer> getOldExpressDetailNumMap(Integer expressId, List<Express> expressList) {
        HashMap<Integer, Integer> oldExpressDetailNumMap = new HashMap<>(16);
        expressList.forEach(express -> {
            if(!express.getExpressId().equals(expressId) && express.getExpressId() < expressId){
                List<ExpressDetail> expressDetaillist = express.getExpressDetail();
                expressDetaillist.forEach(detail -> {
                    Integer oldNum = oldExpressDetailNumMap.get(detail.getRelatedId());
                    if (oldNum == null) {
                        oldNum = 0;
                    }
                    oldExpressDetailNumMap.put(detail.getRelatedId(), oldNum + detail.getNum());
                });
            }
        });
        return oldExpressDetailNumMap;
    }

    private BigDecimal getRealTotalPrice(Map<Integer, SaleorderGoods> saleorderGoodsMap, HashMap<Integer, Integer> oldExpressDetailNumMap, List<ExpressDetail> nowExpressDetail, Integer expressId) {
		BigDecimal realTotalPrice = BigDecimal.ZERO;
		for (ExpressDetail expressDetail : nowExpressDetail) {
			Integer nowDetailNum = expressDetail.getNum();
			SaleorderGoods saleorderGoods = saleorderGoodsMap.get(expressDetail.getRelatedId());
			Integer saleGoodsNum = saleorderGoods.getNum()- saleorderGoods.getAfterReturnNum();
			Integer oldDetailNum = oldExpressDetailNumMap.get(expressDetail.getRelatedId());
			if(oldDetailNum == null){
				oldDetailNum = 0;
			}
			//本次快递数量+原有快递数量 < 商品总数 则实付为单价 * 数量
			if(nowDetailNum + oldDetailNum < saleGoodsNum){
				realTotalPrice = realTotalPrice.add(saleorderGoods.getPrice().multiply(new BigDecimal(nowDetailNum)));

			}else if((nowDetailNum + oldDetailNum) >= saleGoodsNum ){
				//本次快递数量+原有快递数量 >= 商品总数 则实付为总价-原快递内的总价
				BigDecimal oldTotalPrice = saleorderGoods.getPrice().multiply(new BigDecimal(oldDetailNum));
				realTotalPrice = realTotalPrice.add(saleorderGoods.getMaxSkuRefundAmount().subtract(oldTotalPrice));
			}
		}
		return realTotalPrice;
	}

	private BigDecimal getExpressPrice(List<SaleorderGoods> saleorderGoodsList, Integer expressId,List<Express> expressList) {
		//当前快递运费价格
		BigDecimal expressPrice = BigDecimal.ZERO;
		for (SaleorderGoods saleorderGoods : saleorderGoodsList) {
			if(saleorderGoods.getSku().equals("V127063")){
				expressPrice = saleorderGoods.getPrice();
				continue;
			}
		}
		//如果不是第一个快递单则运费为0
		for (Express express : expressList) {
			if(express.getExpressId() < expressId){
				expressPrice = BigDecimal.ZERO;
				break;
			}
		}
		return expressPrice;
	}

	private List<Express> getExpressListBySaleOrderGoodsList(List<SaleorderGoods> saleorderGoodsList) {
		//获取订单内商品快递
		List<Express> expressList = null;
		// 物流信息
		Express express = new Express();
		express.setBusinessType(SysOptionConstant.ID_496);
		express.setCompanyId(1);
		List<Integer> relatedIds = new ArrayList<Integer>();
		for (SaleorderGoods sg : saleorderGoodsList) {
			relatedIds.add(sg.getSaleorderGoodsId());
		}

		if (relatedIds != null && relatedIds.size() > 0) {
			express.setRelatedIds(relatedIds);
			try {
				expressList = expressService.getExpressList(express);
			} catch (Exception e) {
				logger.error("addMvAmoutinfo:{}", e);
			}
		}
		return expressList;
	}
    @Override
    public int getWarehouseoutRecordCounts(int saleorderId) {
        return this.warehouseGoodsOperateLogMapper.getWarehouseoutRecordCounts(saleorderId);
    }

	@Override
	public Long getLastOutTime(WarehouseGoodsOperateLog w) {
		List<Integer> idList = w.getIdList();
		if(CollectionUtils.isNotEmpty(idList)){
			Long lastOutTime = warehouseGoodsOperateLogMapper.getLastOutTime(w);
			return lastOutTime;
		}
		return DateUtil.sysTimeMillis();
	}
	/**
	 *获取订单下有效出库记录
	 * @Author:strange
	 * @Date:13:48 2020-02-26
	 */
	@Override
	public List<Integer> getWarehouseLogIdBy(Integer saleorderId) {

		return warehouseGoodsOperateLogMapper.getWarehouseLogIdBy(saleorderId);
	}

	@Override
	public BigDecimal getPrintOutTotalPrice(List<WarehouseGoodsOperateLog> woList) {
		BigDecimal reslut = BigDecimal.ZERO;
		if(CollectionUtils.isNotEmpty(woList)){
			for (WarehouseGoodsOperateLog warehouseGoodsOperateLog : woList) {
				reslut = reslut.add(warehouseGoodsOperateLog.getAmount());
			}
		}
		return reslut;
	}
	/**
	 *组装出库单内出库记录id
	 * @Author:strange
	 * @Date:17:45 2020-02-26
	 */
	@Override
	public List<Integer> getPrintOutIdListByType(Saleorder saleorder) {
		List<Integer> idList = new ArrayList<>();
		String type_f = saleorder.getOptType();
		if(StringUtils.isBlank(type_f)){
			return idList;
		}
		String wdlIds = saleorder.getSearch();
		if(type_f.equals(ErpConst.PRINT_EXPRESS_HC_TYPE_F) ||
				type_f.equals(ErpConst.PRINT_EXPRESS_KYG_TYPE_F) ||
				type_f.equals(ErpConst.PRINT_EXPRESS_PRICE_TYPE_F) ||
				type_f.equals(ErpConst.PRINT_EXPRESS_NOPRICE_TYPE_F)){

			Integer expressId = saleorder.getExpressId();
			idList = getExpressWlogIds(expressId);

		}else if(type_f.equals(ErpConst.PRINT_OUT_TYPE_F)||
				type_f.equals(ErpConst.PRINT_EXPIRATIONDATE_TYPE_F) ||
				type_f.equals(ErpConst.PRINT_HC_TYPE_F) ||
				type_f.equals(ErpConst.PRINT_KYG_TYPE_F) ||
				type_f.equals(ErpConst.PRINT_PRICE_TYPE_F) ||
				type_f.equals(ErpConst.PRINT_NOPRICE_TYPE_F)){

			if (null != wdlIds) {
				// 切割拼接成的字符串
				String[] p = wdlIds.split("#");
				if (p != null && p.length > 1) {
					String[] ids = p[0].split("_");
					for (String id : ids) {
						idList.add(Integer.valueOf(id));
					}
				}
			}
		}else if(type_f.equals(ErpConst.PRINT_FLOWERORDER_TYPE_F)){
			idList = getWarehouseLogIdBy(saleorder.getSaleorderId());
		}
		return idList;
	}

	@Override
	public List<WarehouseGoodsOperateLog> getPrintOutSkuAmount(List<WarehouseGoodsOperateLog> woList) {
		if(CollectionUtils.isNotEmpty(woList)){
			for (WarehouseGoodsOperateLog warehouseGoodsOperateLog : woList) {
				Integer num = warehouseGoodsOperateLog.getNum();
				BigDecimal price = warehouseGoodsOperateLog.getPrice();
				warehouseGoodsOperateLog.setAmount(price.multiply(new BigDecimal(-num)));
			}
		}
		return woList;
	}

	@Override
	public List<WarehouseGoodsOperateLogVo> getSameBatchGoodsInfo(WarehouseGoodsOperateLogVo warehouseGoodsOperateLogVo) {
		return warehouseGoodsOperateLogMapper.getSameBatchGoodsInfo(warehouseGoodsOperateLogVo);
	}

	@Override
	public Integer getLendOutdeliveryNum(LendOut lo) {
		return lendOutMapper.getdeliveryNum(lo);
	}

	@Override
	public Integer getGoodsStockByGoodsStatus(WarehouseGoodsStatus warehouseGoodsStatus) {
		return  warehouseGoodsStatusMapper.getGoodsStock(warehouseGoodsStatus);
	}

	@Override
	public Integer updateLendOutInfo(LendOut lendout) {
		return lendOutMapper.updateByPrimaryKeySelective(lendout);
	}

	@Override
	public Integer getLendOutKdNum(LendOut lendout) {
		return lendOutMapper.getKdNum(lendout);
	}
}
