package com.task;

import com.common.constants.Contant;
import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.util.DateUtil;
import com.vedeng.order.model.Quoteorder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuoteorderInvalid extends BaseTaskController{
	public static Logger logger = LoggerFactory.getLogger(QuoteorderInvalid.class);

	/**
	 * <b>Description:</b><br> 清理过期的报价单（使之失效）
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月17日 上午11:42:39
	 */
	public void quoteExpired(){
		
		Quoteorder quote = new Quoteorder();
		quote.setUpdater(1);//默认修改人系统管理员
		quote.setModTime(DateUtil.gainNowDate());
		
		quote.setFollowOrderStatus(2);//失单
		quote.setFollowOrderStatusComments("系统判定报价超时");
		quote.setFollowOrderTime(DateUtil.gainNowDate());
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		String url=httpUrl + "order/quote/editquoteexpired.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, quote,clientId,clientKey, TypeRef);
			if(result.getCode()==-1){//失败后再次调用
				Thread.sleep(10000);//休眠10s后再次执行
				HttpClientUtils.post(url, quote,clientId,clientKey, TypeRef);
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
	}

}
