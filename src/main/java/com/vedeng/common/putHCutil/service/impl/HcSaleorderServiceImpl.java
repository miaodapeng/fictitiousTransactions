/**
 * 
 */
package com.vedeng.common.putHCutil.service.impl;

import com.common.constants.Contant;
import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.putHCutil.service.HcSaleorderService;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.finance.model.Invoice;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.model.SaleorderGoods;
import com.vedeng.order.service.SaleorderService;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b>Description:</b><br>
 * 
 * @author cooper.xu
 * @Note <b>ProjectName:</b> erp <br>
 *       <b>PackageName:</b> com.vedeng.common.putHCutil.service.impl <br>
 *       <b>ClassName:</b> HcSaleorderServiceImpl <br>
 *       <b>Date:</b> 2018年11月26日 下午7:00:31
 */
@Service(value = "hcSaleorderService")
public class HcSaleorderServiceImpl extends BaseServiceimpl implements HcSaleorderService {
	@Autowired
	@Qualifier("saleorderService")
	protected SaleorderService saleorderService;

	public static Logger logger = LoggerFactory.getLogger(HcSaleorderServiceImpl.class);

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.vedeng.common.putHCutil.service.HcSaleorderService#putExpressToHC(
     * java.util.Map)
     */
    @SuppressWarnings("unchecked")
    @Override
    public ResultInfo<?> putExpressToHC(Map<String, Object> map) {
	// TODO Auto-generated method stub
	final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
	};
	ResultInfo<?> resultInfo = new ResultInfo<>();
	try {
	    // 封装订单信息
	    Saleorder saleOrder = (Saleorder) map.get("saleOrder");
	    Map<String, Object> mapSaleorder = new HashMap<String, Object>();
	    mapSaleorder.put("orderNo", saleOrder.getSaleorderNo());// 订单号
	    mapSaleorder.put("deliveryStatus", saleOrder.getDeliveryStatus());// 发货状态
	    mapSaleorder.put("arrivalStatus", saleOrder.getArrivalStatus());// 收货状态
	    if (saleOrder.getDeliveryStatus() == 1) {// 部分发货
		mapSaleorder.put("firstDeliveryTime", saleOrder.getDeliveryTime());// 首次发货时间
		mapSaleorder.put("lastDeliveryTime", null);// 最后一次发货时间
	    }
	    if (saleOrder.getDeliveryStatus() == 2) {// 全部发货
		mapSaleorder.put("firstDeliveryTime", saleOrder.getDeliveryTime());// 首次发货时间
		mapSaleorder.put("lastDeliveryTime", saleOrder.getDeliveryTime());// 最后一次发货时间
	    }
	    if (saleOrder.getArrivalStatus() == 1) {// 部分收货
		mapSaleorder.put("firstArrivalTime", saleOrder.getArrivalTime());// 首次收货时间
		mapSaleorder.put("lastArrivalTime", null);// 最后一次收货时间
	    }
	    if (saleOrder.getArrivalStatus() == 2) {// 全部收货
		mapSaleorder.put("firstArrivalTime", saleOrder.getArrivalTime());// 首次收货时间
		mapSaleorder.put("lastArrivalTime", saleOrder.getArrivalTime());// 最后一次收货时间
	    }
	    if ("1".equals(map.get("type"))) {// 收货推送时不需要推送发货状态
		mapSaleorder.put("deliveryStatus", null);// 发货状态
	    }
	    List<SaleorderGoods> goodsList = (List<SaleorderGoods>) map.get("goodsList");
	    // 封装goodsList信息
	    if (CollectionUtils.isNotEmpty(goodsList)) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (SaleorderGoods saleorderGoods : goodsList) {
		    Map<String, Object> mapGoods = new HashMap<String, Object>();
		    // sku
		    mapGoods.put("skuCode", saleorderGoods.getSku());
		    // deliveryStatus 发货状态：0待发货、1部分发货、2全部发货  DELIVERY_STATUS
			// deliveryNum
		    mapGoods.put("deliveryNum", saleorderGoods.getDeliveryNum());
		    mapGoods.put("deliveryStatus", saleorderGoods.getDeliveryStatus());
		    mapGoods.put("deliveryTime", saleorderGoods.getDeliveryTime());
		    mapGoods.put("arrivalNum", null);
		    mapGoods.put("arrivalStatus", saleorderGoods.getArrivalStatus());
		    mapGoods.put("arrivalTime", saleorderGoods.getArrivalTime());
		    mapGoods.put("isDelete", 0);
		    if(saleorderGoods.getIsActionGoods()==1){
				mapGoods.put("activityId",saleOrder.getActionId());
			}else{
				mapGoods.put("activityId",0);
			}
		    list.add(mapGoods);
		}
		mapSaleorder.put("orderSkuList", list);
	    }
		logger.info("putExpressToHC:",mapSaleorder.values());
	    String url = apiUrl + "order/" + saleOrder.getSaleorderNo();
	    // map转json对象
	    JSONObject jsonObject = JSONObject.fromObject(mapSaleorder);
	    // 调用接口推送数据
	    // 请求头
	    Map<String, String> header = new HashMap<String, String>();
	    header.put("version", "v1");
	    resultInfo = (ResultInfo<?>) HttpClientUtils.put(url, jsonObject.toString(), header, TypeRef);
	} catch (Exception e) {
	    logger.info("putExpressToHC | 发生异常", e);
	    return null;
	}
	return resultInfo;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.vedeng.common.putHCutil.service.HcSaleorderService#putOrderStatustoHC
     * (java.util.Map)
     */
    @Override
    public ResultInfo<?> putOrderStatustoHC(Map<String, Object> map) {
	// TODO Auto-generated method stub
	final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
	};
	// 条件封装
	Map<String, Object> param = new HashMap<String, Object>();
	// 封装订单的json对象
	param.put("orderNo", map.get("orderNo"));// 订单号
	param.put("orderStatus", map.get("orderStatus"));// 订单状态
	JSONObject jsonObject = JSONObject.fromObject(param);
	String url = apiUrl + "order/" + map.get("orderNo");
	ResultInfo<?> result = new ResultInfo<>();
	try {
	    // 请求头
	    Map<String, String> header = new HashMap<String, String>();
	    header.put("version", "v1");
	    result = (ResultInfo<?>) HttpClientUtils.put(url, jsonObject.toString(), header, TypeRef);
	} catch (Exception e) {
	    logger.error(Contant.ERROR_MSG, e);
	    return null;
	}
	return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.vedeng.common.putHCutil.service.HcSaleorderService#putInvoicetoHC(
     * java.util.Map)
     */
    @Override
    public ResultInfo<?> putInvoicetoHC(Map<String, Object> map) {
	// TODO Auto-generated method stub
	final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
	};
	ResultInfo<?> resultInfo = new ResultInfo<>();
	try {
	    // 封装订单信息
	    Saleorder saleOrder = (Saleorder) map.get("saleOrder");
	    Map<String, Object> mapSaleorder = new HashMap<String, Object>();
	    mapSaleorder.put("orderNo", saleOrder.getSaleorderNo());// 订单号
	    List<Invoice> invoiceList = (List<Invoice>) map.get("invoiceList");
	    // 封装goodsList信息
	    if (CollectionUtils.isNotEmpty(invoiceList)) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (Invoice invoice : invoiceList) {
		    Map<String, Object> mapInvoice = new HashMap<String, Object>();
		    mapInvoice.put("invoiceNo", invoice.getInvoiceNo());
		    mapInvoice.put("downloadLinks", invoice.getInvoiceHref());
		    list.add(mapInvoice);
		}
		mapSaleorder.put("invoiceList", list);
	    }
	    String url = apiUrl + "order/" + saleOrder.getSaleorderNo();
	    // map转json对象
	    JSONObject jsonObject = JSONObject.fromObject(mapSaleorder);
	    // 调用接口推送数据
	    // 请求头
	    Map<String, String> header = new HashMap<String, String>();
	    header.put("version", "v1");
	    resultInfo = (ResultInfo<?>) HttpClientUtils.put(url, jsonObject.toString(), header, TypeRef);
	} catch (Exception e) {
	    // TODO: handle exception
	    e.getMessage();
	    return null;
	}
	return resultInfo;
    }

	@Override
	public ResultInfo<?> putOrderPricetoHC(Map<String, Object> map) {
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
		};
		ResultInfo<?> resultInfo = new ResultInfo<>();
		try {
			HashMap<String, Object> dataMap = new HashMap<>();
			// 封装订单信息
			Saleorder saleOrder = (Saleorder) map.get("saleOrder");
			List<SaleorderGoods> saleorderGoods = saleorderService.getSaleorderGoods(saleOrder);

			BigDecimal totalMoney = new BigDecimal(0);
			BigDecimal realTotalMoney = new BigDecimal(0);
			BigDecimal deliverMoney = new BigDecimal(0);

			ArrayList<Map<String,Object>> goodsList = new ArrayList<>();
			if (CollectionUtils.isNotEmpty(saleorderGoods)){
				for (SaleorderGoods saleorderGood : saleorderGoods) {
					HashMap<String, Object> goodsMap = new HashMap<>();
					goodsMap.put("skuId",saleorderGood.getGoodsId());
					goodsMap.put("skuName",saleorderGood.getGoodsName());
					goodsMap.put("skuPrice",saleorderGood.getPrice());
					goodsMap.put("couponAmount",saleorderGood.getPrice());
					//订单商品实际金额
					goodsMap.put("skuAmount",saleorderGood.getMaxSkuRefundAmount());
					/**
					 * 1.运费商品不推送；
					 * 2.推送运费价格；
					 */
					if ("V127063".equals(saleorderGood.getSku())){
						deliverMoney = saleorderGood.getPrice();
					} else {
						goodsList.add(goodsMap);
					}
					realTotalMoney = realTotalMoney.add(saleorderGood.getMaxSkuRefundAmount());
				}
			}
			totalMoney = realTotalMoney.subtract(deliverMoney);
			/**
			 * 订单号
			 */
			dataMap.put("orderNo",saleOrder.getSaleorderNo());
			 //订单商品的总金额（不包含运费）
			dataMap.put("totalMoney",totalMoney);
			//订单的实际金额
			dataMap.put("realTotalMoney",realTotalMoney);
			//运费价格
			dataMap.put("deliverMoney",deliverMoney);
			//商品列表
			dataMap.put("orderSkuList",goodsList);

			String url = apiUrl + "/order/updateOrderSkuPrice" ;
			//String url = "http://172.17.1.2:8080/order/updateOrderSkuPrice" ;
			// map转json对象
			JSONObject jsonObject = JSONObject.fromObject(dataMap);
			Map<String, String> header = new HashMap<String, String>();
			header.put("version", "v1");
			logger.info("putOrderPricetoHC:" + dataMap.toString());
			resultInfo = (ResultInfo<?>) HttpClientUtils.put(url, jsonObject.toString(), header, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return resultInfo;
	}

}
