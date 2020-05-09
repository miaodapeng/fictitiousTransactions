package com.newtask;

import com.fasterxml.jackson.core.type.TypeReference;
import com.task.service.SalesPerformanceTaskService;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.model.SearchModel;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.DateUtil;
import com.vedeng.system.service.UserService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.*;

@JobHandler(value = "salesPerformanceCallTask")
@Component
public class SalesPerformanceCallTask extends IJobHandler {
 	@Autowired
	private SalesPerformanceTaskService salesPerformanceTaskService ;
	@Autowired
	private UserService userService ;
	@Value("${http_url}")
	protected String httpUrl;

	@Value("${client_id}")
	protected String clientId;

	@Value("${client_key}")
	protected String clientKey;
	@Override
	public ReturnT<String> execute(String s) throws Exception {
		this.callRecordInfoSync();
		return SUCCESS;
	}
	private void callRecordInfoSync() throws Exception {
		SearchModel searchModel = new SearchModel();
		Page page = new Page(1, ErpConst.EXPORT_DATA_LIMIT);
		//获取前一天的月份第一天
		//获取前一天的日
		String dateString = DateUtil.convertString(DateUtil.getDateBefore(new Date(), 1), "yyyy-MM-dd");
		//获取前一天的所在月的第一日
		String yearMonth = DateUtil.getFirstDayOfGivenMonth(dateString, "yyyy-MM-dd");
		long startTime = DateUtil.convertLong(yearMonth, "yyyy-MM-dd");
		long endTime = DateUtil.sysTimeMillis();

		searchModel.setStartDateLong(startTime);
//		searchModel.setStartDateLong(new Long("1518167093852"));
		searchModel.setEndDateLong(endTime);
		salesPerformanceTaskService.callRecordInfoSync(searchModel,page);
	}


}
