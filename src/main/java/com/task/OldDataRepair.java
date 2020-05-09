package com.task;

import com.common.constants.Contant;
import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.util.DateUtil;
import com.vedeng.order.model.Quoteorder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OldDataRepair extends BaseTaskController{
	public static Logger logger = LoggerFactory.getLogger(OldDataRepair.class);

	/**
	 * <b>Description:</b><br> 修复销售订单账期订单 已触发账期没有账期支付交易记录的
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2018年2月26日 下午2:19:14
	 */
	@Test
	public void repairSaleorderCapitailBill() {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		String url=httpUrl + "olddata/repairsaleordercapitailbill.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, null,clientId, clientKey, TypeRef);
			if(result.getCode()==-1){//失败后再次调用
				//Thread.sleep(10000);//休眠10s后再次执行
				//HttpClientUtils.post(url, null,client_id, client_key, TypeRef);
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
	}	
	

}
