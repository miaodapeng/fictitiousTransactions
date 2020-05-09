package com.task;

import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.util.DateUtil;
import com.vedeng.order.model.Quoteorder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MongoDbTrader extends BaseTaskController{
	public static Logger logger = LoggerFactory.getLogger(MongoDbTrader.class);
	/**
	 * <b>Description:</b><br> 同步交易者客户数据
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年11月24日 下午2:30:41
	 */
	//@Test
	public void syncTraderCustomer() {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		String url=httpUrl + "mongodb/trader/synctradercustomer.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, null,clientId, clientKey, TypeRef);
			if(result.getCode()==-1){//失败后再次调用
				Thread.sleep(10000);//休眠10s后再次执行
				HttpClientUtils.post(url, null,clientId, clientKey, TypeRef);
			}
		} catch (Exception e) {
			logger.error("MongoDbTrader.syncTraderCustomer() error,:{}",e);
		}
	}	
	

}
