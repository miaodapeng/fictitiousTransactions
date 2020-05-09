package com.newtask;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.activiti.editor.language.json.converter.util.CollectionUtils;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;
import com.beust.jcommander.internal.Maps;
import com.newtask.dto.AdkDeliverPO;
import com.newtask.dto.AdkDeliverProductPO;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.util.DateUtil;
import com.vedeng.order.dao.AdkLogMapper;
import com.vedeng.order.model.adk.AdkLog;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;

/**
 * 每隔半个小时，将发货单信息发送给艾迪康
 * 
 * @author vedeng adkDeliverySyncTask
 *
 */
@JobHandler(value = "adkDeliverySyncTask")
@Component
public class AdkDeliverySyncTask extends IJobHandler {
	Logger log = LoggerFactory.getLogger(AdkDeliverySyncTask.class);
	WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();

	@Autowired
	AdkLogMapper adkLogMapper;
	String url = "http://adkerp.vedeng.com/order/adkbackorder";
	String sign = "WdSKRo896O5";
	String adkLogSendType = "1";
	boolean start = false;

	// @Transactional(rollbackFor = Exception.class, readOnly = false, propagation =
	// Propagation.REQUIRED)
	@Override
	public ReturnT<String> execute(String param) throws Exception {
		XxlJobLogger.log("addback start-------------------------------------------- ");
		log.info("addback start--------------------------------------------");
		// 捞取所有发货单
		List<Map<String, Object>> list = adkLogMapper.selectGoodsOutBefore30Min(null);
		if (CollectionUtils.isEmpty(list)) {
			return SUCCESS;
		}
		Map<String, AdkDeliverPO> adkOrderMap = Maps.newHashMap();
		log.info("addback start---------adk deliverysize=" + list.size());
		XxlJobLogger.log("addback start---------adk deliverysize=" + list.size());
		for (Map<String, Object> mList : list) {
			AdkDeliverProductPO prod = new AdkDeliverProductPO();
			String adkOrderNo = mList.get("ADK_SALEORDER_NO") + "";
			AdkDeliverPO delive = new AdkDeliverPO();
			if (adkOrderMap.containsKey(adkOrderNo)) {
				delive = adkOrderMap.get(adkOrderNo);
			} else {
				// 订单号+贝登医疗+自定义key WdSKRo896O5
				delive.setSign(DigestUtils.md5Hex(adkOrderNo + "贝登医疗" + sign));
				delive.setfCom(String.valueOf(mList.get("TRADER_NAME")));
				delive.setfDate(DateUtil.DateToString(new Date(), "yyyy-MM-dd"));
				delive.setOrderNo(adkOrderNo);
				adkOrderMap.put(adkOrderNo, delive);
			}
			prod.setGoodsCode(String.valueOf(mList.get("ADK_GOODS_CODE")));
			prod.setGoodsName(String.valueOf(mList.get("ADK_GOODS_NAME")));
			prod.setUnitName(String.valueOf(mList.get("UNIT_NAME")));
			try {
				prod.setNum(new BigDecimal(String.valueOf(mList.get("NUM"))));
				prod.setBeiNum(new BigDecimal(String.valueOf(mList.get("NUM"))));
			} catch (Exception e) {
				prod.setNum(new BigDecimal(NumberUtils.toInt(String.valueOf(mList.get("NUM")))));
				prod.setBeiNum(new BigDecimal(NumberUtils.toInt(String.valueOf(mList.get("NUM")))));
			}
			prod.setBatchNumber(mList.get("BATCH_NUMBER") == null ? "" : String.valueOf(mList.get("BATCH_NUMBER")));
			prod.setExpirationDate(
					mList.get("EXPIRATION_DATE") == null ? "" : String.valueOf(mList.get("EXPIRATION_DATE")));
			prod.setManufacturer(mList.get("MANUFACTURER") == null ? "" : String.valueOf(mList.get("MANUFACTURER")));
			prod.setProductionLicense(
					mList.get("PRODUCTION_LICENSE") == null ? "" : String.valueOf(mList.get("PRODUCTION_LICENSE")));
			prod.setLicenseNumber(String.valueOf(mList.get("LICENSE_NUMBER")));
			prod.setRecordNumber(mList.get("RECORD_NUMBER") == null ? "" : String.valueOf(mList.get("RECORD_NUMBER")));
			prod.setStorageRequirements(
					mList.get("STORAGE_REQUIREMENTS") == null ? "" : String.valueOf(mList.get("STORAGE_REQUIREMENTS")));

			delive.add(prod);
		}

		for (AdkDeliverPO order : adkOrderMap.values()) {
			String json = JSON.toJSONString(order);
			XxlJobLogger.log("addback start---------adk json param=" + json);
			log.info("json:" + json);
			String result = HttpClientUtils.postJSON(url, json);
			XxlJobLogger.log("addback start---------adk json result=" + result);
			AdkLog adkLog = new AdkLog();
			adkLog.setAdkLogType(adkLogSendType);//
			if (result != null) {
				Map<String, Object> resultMap = JSON.parseObject(result, Map.class);

				if (StringUtils.equals(resultMap.get("status") + "", "0")) {
					adkLog.setAdkLogStatus("0");
					XxlJobLogger.log("发送成功=" + order.getfCom());
				} else {
					adkLog.setAdkLogStatus("-1");
					XxlJobLogger.log("发送失败=" + order.getfCom() + "\t" + result);
				}
				adkLog.setAdkLogParam(result + "##" + json);
				adkLog.setUpdateTime(new Date());
				adkLogMapper.insert(adkLog);
			} else {
				adkLog.setAdkLogStatus("-1");
				XxlJobLogger.log("发送失败" + order.getfCom() + "\t result null=" + result);
				adkLog.setAdkLogParam(result + "##" + json);
				adkLog.setUpdateTime(new Date());
				adkLogMapper.insert(adkLog);
			}

		}
		XxlJobLogger.log("addback end--------------------------------------------");
		return SUCCESS;
	}

}
