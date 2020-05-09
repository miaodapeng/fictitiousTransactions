/**
 *
 */
package com.newtask;

import com.vedeng.common.util.DateUtil;
import com.vedeng.firstengage.model.RegistrationNumber;
import com.vedeng.firstengage.service.FirstEngageService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 *
 */
@JobHandler(value="refreshValidStatus")
@Component
public class RefreshValidStatus extends IJobHandler{

	WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();

	@Autowired
	private FirstEngageService firstEngageService;

	@Override
	public ReturnT<String> execute(String s) throws Exception {
		XxlJobLogger.log("XXL-JOB, Hello World.");
		// 参数
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("registrationEndTime", DateUtil.convertLong(DateUtil.getDayOfMonth(6), DateUtil.DATE_FORMAT));
		// 获取所有半年内的注册证信息
		List<RegistrationNumber> list = firstEngageService.getRefreshFirstList(paramMap);
		// 刷状态
		if(CollectionUtils.isNotEmpty(list)){
			firstEngageService.refreshFirstList(list);
		}
		return SUCCESS;
	}
}
