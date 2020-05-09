package com.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.util.DateUtil;
import com.vedeng.order.model.Quoteorder;

@Component
//@Lazy(false)
public class TaskTest extends BaseTaskController{

	/**
	 * 定时任务执行方法
	 * 每天下午14:55分触发
	 */
	/*@Scheduled(cron="0 30 13 * * ?")
	public void taskRun(){
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
	}*/
}
