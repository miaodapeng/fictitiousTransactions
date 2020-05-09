//package com.newtask;
//
//import com.alibaba.fastjson.JSON;
//import com.beust.jcommander.internal.Maps;
//import com.google.common.collect.Lists;
//import com.meinian.model.DeliveryOrderDetails;
//import com.meinian.model.LogisticsVo;
//import com.meinian.model.vo.SendData;
//import com.meinian.service.MeinianOderService;
//import com.newtask.dto.AdkDeliverPO;
//import com.newtask.dto.AdkDeliverProductPO;
//import com.vedeng.common.constant.ErpConst;
//import com.vedeng.common.http.HttpClientUtils;
//import com.vedeng.common.util.DateUtil;
//import com.vedeng.order.dao.AdkLogMapper;
//import com.vedeng.order.model.adk.AdkLog;
//import com.xxl.job.core.biz.model.ReturnT;
//import com.xxl.job.core.handler.IJobHandler;
//import com.xxl.job.core.handler.annotation.JobHandler;
//import com.xxl.job.core.log.XxlJobLogger;
//import org.activiti.editor.language.json.converter.util.CollectionUtils;
//import org.apache.commons.codec.digest.DigestUtils;
//import org.apache.commons.lang.StringUtils;
//import org.apache.commons.lang.math.NumberUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.ContextLoader;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.math.BigDecimal;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
///**
// *  方案搁置
// *
// * @author vedeng adkDeliverySyncTask
// *
// */
//@JobHandler(value = "meinianDeliverySyncTask")
//@Component
//@Deprecated
//public class MeinianDeliverySyncTask extends IJobHandler {
//	Logger log = LoggerFactory.getLogger(MeinianDeliverySyncTask.class);
//@Autowired
//AdkLogMapper mapper;
//@Autowired
//MeinianOderService meinianOderService;
//	// @Transactional(rollbackFor = Exception.class, readOnly = false, propagation =
//	// Propagation.REQUIRED)
//	@Override
//	public ReturnT<String> execute(String param) throws Exception {
//		XxlJobLogger.log("meinian start-------------------------------------------- ");
//		LogisticsVo logistics = new LogisticsVo();
//		List<com.meinian.model.vo.SendData>list2=Lists.newArrayList();
//		if(StringUtils.isNotBlank(param)){
//			List<String> list=Lists.newArrayList();
//			for(String a:StringUtils.split(param,",")){
//				if(StringUtils.isNotBlank(a)){
//					list.add(StringUtils.trim(a));
//				}
//			}
//			list2=mapper.selectAllMeinianByNo(list	);
//		}else{
//			list2=
//					mapper.selectAllMeinianDeliveryStatus();
//		}
//		if(CollectionUtils.isNotEmpty(list2)){
//			for(SendData x:list2){
//				List<DeliveryOrderDetails> list= Lists.newArrayList();
//				DeliveryOrderDetails deliveryOrderDetails =  new DeliveryOrderDetails();
//				//设置所需属性值（价格,批次，批次失效，备注，供货数量 , 订单号 , 产品编码, 供方货号/供货平台产品ID , 产品型号）
//				deliveryOrderDetails.setPrice(x.getPrice().doubleValue());
//				deliveryOrderDetails.setBatchNumber(x.getBatchNumber());
//				if (x.getBatchExpiryTime() != 0){
//					deliveryOrderDetails.setBatchExpiryTime(DateUtil.convertString(x.getBatchExpiryTime(), "yyyy-MM-dd"));
//				}else{
//					//deliveryOrderDetails.setBatchExpiryTime(DateUtil.convertString(System.currentTimeMillis(), "yyyy-MM-dd"));
//				}
//				deliveryOrderDetails.setRemark(x.getRemark());
//				deliveryOrderDetails.setSupplyNumber(x.getSupplyNumber().longValue());
//				deliveryOrderDetails.setOrderNumber(x.getOrderNumber());
//				deliveryOrderDetails.setProductCode(x.getProductCode());
//				deliveryOrderDetails.setSupplierNumber(x.getSupplierNumber());
//				if(null != x.getSerialNumber() && !"".equals(x.getSerialNumber())){
//					deliveryOrderDetails.setSerialNumber(x.getSerialNumber());
//				}
//				list.add(deliveryOrderDetails);
//				logistics.setDetails(list);
//				String result = meinianOderService.sendToMeinianSupply(logistics);
//				//成功返回S 失败返回E
//				if (ErpConst.SEND_DATA_SUCCESS.equalsIgnoreCase(result)) {
//					XxlJobLogger.log("send meinian order success :" + x.getSupplierNumber());
//					log.info("补数据"+ x.getSupplierNumber());
//				}else{
//					XxlJobLogger.log("send meinian order error :" + x.getSupplierNumber()+result);
//				}
//				log.info("当前处理数据"+ x.getSupplierNumber());
//			}
//
//
//		}
//
//		return SUCCESS;
//	}
//
//}
