package com.task;

import com.common.constants.Contant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.trader.model.TraderCustomer;
import com.vedeng.trader.model.vo.TraderCustomerVo;

/**
 * 
 * <b>Description:</b><br> 每日刷新客户等级
 * @param traderId 如果传了traderId则刷单个客户
 * @Note
 * <b>Author:</b> Michael
 * <br><b>Date:</b> 2018年2月27日 下午1:34:37
 */
public class CustmoerLevel extends BaseTaskController {
	public static Logger logger = LoggerFactory.getLogger(CustmoerLevel.class);

   
	public void custmoerLevel() {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
		};
		String url = httpUrl + "tradercustomer/editcustomerlevel.htm";
		try {
		    	TraderCustomer traderCustomer = new TraderCustomer();
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, traderCustomer, clientId, clientKey,
					TypeRef);
			Integer times = 0;
			if (result.getCode() == -1 && times <= 10) {// 失败后再次调用
				
				Thread.sleep(10000);// 休眠10s后再次执行
				HttpClientUtils.post(url, traderCustomer, clientId, clientKey, TypeRef);
				
				times++;
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
	}
}
