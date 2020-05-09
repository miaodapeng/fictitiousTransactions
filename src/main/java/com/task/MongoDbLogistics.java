package com.task;

import com.common.constants.Contant;
import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MongoDbLogistics extends BaseTaskController{
	public static Logger logger = LoggerFactory.getLogger(MongoDbLogistics.class);

	
	/**
	 * <b>Description:</b><br> 同步快递单数据
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年12月26日 上午9:24:13
	 */
	public void syncExpress() {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		String url=httpUrl + "mongodb/logistics/syncexpress.htm";
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
	
	/**
	 * <b>Description:</b><br> 同步快递单详细
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年12月26日 下午3:45:45
	 */
	public void syncExpressDetail() {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		String url=httpUrl + "mongodb/logistics/syncexpressdetail.htm";
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
	
	/**
	 * <b>Description:</b><br> 同步产品库存状态
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年12月26日 下午3:46:47
	 */
	public void syncWarehouseGoodsStatus() {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		String url=httpUrl + "mongodb/logistics/syncwarehousegoodsstatus.htm";
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
	
	/**
	 * <b>Description:</b><br> 同步产品出入库日志
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年12月26日 下午3:49:18
	 */
	public void syncWarehouseGoodsOperateLog() {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		String url=httpUrl + "mongodb/logistics/syncwarehousegoodsoperatelog.htm";
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
