package com.task;

import com.common.constants.Contant;
import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.util.DateUtil;
import com.vedeng.logistics.model.WarehouseGoodsDayStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <b>Description:</b><br> 自动开票申请
 * @author leo.yang
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.task
 * <br><b>ClassName:</b> AutoInvoiceApply
 * <br><b>Date:</b> 2018年6月28日 上午10:09:59
 */
public class AutoInvoiceApply extends BaseTaskController {
	/**
	 * <b>Description:</b><br> 自动开票申请
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2018年6月28日 上午10:13:16
	 */
	public static Logger logger = LoggerFactory.getLogger(AutoInvoiceApply.class);

	@Test
	public void autoInvoiceApplyList() {
		
		System.out.println("AutoInvoiceApply task run"+DateUtil.gainNowDate());
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
		};
		String url = httpUrl + "finance/invoice/autoinvoiceapplylist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, null, clientId, clientKey,
					TypeRef);
			/*Integer times = 0;
			if (result.getCode() == -1 && times <= 10) {// 失败后再次调用
				
				Thread.sleep(10000);// 休眠10s后再次执行
				HttpClientUtils.post(url, goodsDayStatus, clientId, clientKey, TypeRef);
				
				times++;
			}*/
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
	}
}
