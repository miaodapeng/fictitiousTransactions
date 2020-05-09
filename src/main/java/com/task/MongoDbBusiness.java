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

/**
 * <b>Description:</b><br> 同步运营数据（商机，报价，订单）
 * @author leo.yang
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.task
 * <br><b>ClassName:</b> MongoDbBusiness
 * <br><b>Date:</b> 2017年12月4日 下午6:01:56
 */
public class MongoDbBusiness extends BaseTaskController{
	public static Logger logger = LoggerFactory.getLogger(MongoDbBusiness.class);


	/**
	 * <b>Description:</b><br> 同步商机数据
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年11月24日 下午2:30:41
	 */
	public void syncBusinessChance() {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		String url=httpUrl + "mongodb/business/syncbusinesschance.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, null,clientId, clientKey, TypeRef);
			if(result.getCode()==-1){//失败后再次调用
				Thread.sleep(10000);//休眠10s后再次执行
				HttpClientUtils.post(url, null,clientId, clientKey, TypeRef);
			}
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	public void syncQuoteorder() {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		String url=httpUrl + "mongodb/business/syncquoteorder.htm";
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
	
	public void syncQuoteorderGoods() {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		String url=httpUrl + "mongodb/business/syncquoteordergoods.htm";
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
	
	public void syncSaleorder() {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		String url=httpUrl + "mongodb/business/syncsaleorder.htm";
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
	
	public void syncSaleorderGoods() {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		String url=httpUrl + "mongodb/business/syncsaleordergoods.htm";
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
	
	
	public void syncBuyorder() {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		String url=httpUrl + "mongodb/business/syncbuyorder.htm";
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
	
	public void syncBuyorderGoods() {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		String url=httpUrl + "mongodb/business/syncbuyordergoods.htm";
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
