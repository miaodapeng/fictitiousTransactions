package com.task;

import com.common.constants.Contant;
import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.logistics.model.WarehouseGoodsDayStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <b>Description:</b><br>
 * 库存产品每日状态
 * 
 * @author Jerry
 * @Note <b>ProjectName:</b> erp <br>
 * 		<b>PackageName:</b> com.task <br>
 * 		<b>ClassName:</b> GoodsDayStatus <br>
 * 		<b>Date:</b> 2017年10月17日 下午2:08:08
 */
public class GoodsDayStatus extends BaseTaskController {
	public static Logger logger = LoggerFactory.getLogger(GoodsDayStatus.class);

	/**
	 * <b>Description:</b><br>
	 * 库存产品每日状态
	 * 
	 * @Note <b>Author:</b> Jerry <br>
	 * 		<b>Date:</b> 2017年10月17日 下午2:12:10
	 */
	public void goodsDayStatusRun() {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
		};
		String url = httpUrl + "warehousegoodsdaystatus/getwarehousegoodsdaystatus.htm";
		try {
			WarehouseGoodsDayStatus goodsDayStatus = new WarehouseGoodsDayStatus();
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, goodsDayStatus, clientId, clientKey,
					TypeRef);
			Integer times = 0;
			if (result.getCode() == -1 && times <= 10) {// 失败后再次调用
				
				Thread.sleep(10000);// 休眠10s后再次执行
				HttpClientUtils.post(url, goodsDayStatus, clientId, clientKey, TypeRef);
				
				times++;
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
	}
}
