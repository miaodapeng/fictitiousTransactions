package com.task;

import com.common.constants.Contant;
import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MongoDbAfterSales extends BaseTaskController{
	public static Logger logger = LoggerFactory.getLogger(MongoDbAfterSales.class);

	
	/**
	 * <b>Description:</b><br> 同步售后主数据（T_AFTER_SALES联合T_AFTER_SALES_DETAIL）
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年12月25日 上午11:26:37
	 */
	public void syncAfterSales() {
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		String url= httpUrl + "mongodb/aftersales/syncaftersales.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, null, clientId, clientKey, TypeRef);
			if(result.getCode()==-1){//失败后再次调用
				Thread.sleep(10000);//休眠10s后再次执行
				HttpClientUtils.post(url, null, clientId, clientKey, TypeRef);
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
	}
	
	/**
	 * <b>Description:</b><br> 同步售后安调数据
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年12月25日 上午11:29:46
	 */
	public void syncAfterSalesInstallstion() {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		String url= httpUrl + "mongodb/aftersales/syncaftersalesinstallstion.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, null,clientId, clientKey, TypeRef);
			if(result.getCode()==-1){//失败后再次调用
				Thread.sleep(10000);//休眠10s后再次执行
				HttpClientUtils.post(url, null,clientId, clientKey, TypeRef);
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
	}
	

}
