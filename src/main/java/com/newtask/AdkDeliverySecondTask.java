package com.newtask;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.activiti.editor.language.json.converter.util.CollectionUtils;
import org.apache.commons.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.thymeleaf.util.ArrayUtils;

import com.alibaba.fastjson.JSON;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.order.dao.AdkLogMapper;
import com.vedeng.order.model.adk.AdkLog;
import com.vedeng.order.model.adk.AdkLogExample;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;

/**
 * 每天早上6点将补充发送艾迪康发送错误的发货数据
 * 
 * @author vedeng
 *
 */
@JobHandler(value = "adkDeliverySecondTask")
@Component
public class AdkDeliverySecondTask extends IJobHandler {

	Logger log = LoggerFactory.getLogger(AdkDeliverySecondTask.class);
	WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
	@Autowired
	AdkLogMapper adkLogMapper;
	String url = "http://adkerp.vedeng.com/order/adkbackorder";
	String sign = "WdSKRo896O5";
	String adkLogSendType = "1";
	String SECOND_SEND_FAIL = "-2";
	boolean start = false;

	// @Transactional(rollbackFor = Exception.class, readOnly = false, propagation =
	// Propagation.REQUIRED)
	@Override
	public ReturnT<String> execute(String param) throws Exception {
		log.info("addadk AdkDeliverySecondTask  start--------------------------------------------");
		// 捞取所有发货单
		// List<Map<String, Object>> list =
		// adkOrderLogGenerateMapper.selectGoodsOutBefore30Min(null);

		AdkLogExample example = new AdkLogExample();
		example.createCriteria().andAdkLogTypeEqualTo(adkLogSendType).andAdkLogStatusEqualTo("-1");
		List<AdkLog> list = adkLogMapper.selectByExampleWithBLOBs(example);
		if (CollectionUtils.isEmpty(list)) {
			return SUCCESS;
		}
		for (AdkLog adkLog : list) {
			String[] json = adkLog.getAdkLogParam().split("##");
			if (ArrayUtils.length(json) != 2) {
				adkLog.setAdkLogStatus("-2");
			} else {
				XxlJobLogger.log("addback start---------adk json param=" + json);
				String result = HttpClientUtils.postJSON(url, json[1]);
				if (result != null) {
					Map<String, Object> resultMap = JSON.parseObject(result, Map.class);
					if (StringUtils.equals(resultMap.get("status") + "", "0")) {
						adkLog.setAdkLogStatus("0");
						XxlJobLogger.log("补发发送成功=");
					} else {
						adkLog.setAdkLogStatus(SECOND_SEND_FAIL);
						XxlJobLogger.log("补发失败=" + "\t" + result);
					}
					adkLog.setAdkLogParam(result + "##" + json);
					adkLog.setUpdateTime(new Date());

				} else {
					adkLog.setAdkLogStatus(SECOND_SEND_FAIL);
					XxlJobLogger.log("补发失败" + "\t result null=" + result);
					adkLog.setAdkLogParam(result + "##" + json);
					adkLog.setUpdateTime(new Date());
				}
			}
			adkLogMapper.updateByPrimaryKeyWithBLOBs(adkLog);

		}
		log.info("addback end--------------------------------------------");
		return SUCCESS;
	}

}
