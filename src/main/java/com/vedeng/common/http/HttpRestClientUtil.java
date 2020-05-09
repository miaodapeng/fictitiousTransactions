/*
 * Copyright 2018 Focus Technology, Co., Ltd. All rights reserved.
 */
package com.vedeng.common.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vedeng.common.constant.CommonConstants;
import com.vedeng.common.util.EmptyUtils;
import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 新商品流http请求
 * @author wayne.liu
 * @description
 * @date 2019/8/7 15:47
 */
public class HttpRestClientUtil {
    
    /**
     * Content-Type:字符默认格式
     */
    private static final String DEFAULT_APPLICATION_JSON = "application/json;charset=UTF-8";
    
    /**
     * 默认json格式
     */
    private static final String DEFAULT_VERSION = "v1";
    
    /**
     * 日志
     */
    private static Logger log = LoggerFactory.getLogger(HttpRestClientUtil.class);
    

	/**
	  * 新商品流解析String数据
	  * @author wayne.liu
	  * @date  2019/8/7 15:28
	  * @param 
	  * @return 
	  */
	public static <T> T restPost(String uri, TypeReference<?> type, Map<String, String> headers, Object paramsObj) {
		return httpForJsonByMethod(uri, "post", type, headers, paramsObj, CommonConstants.ONE);
	}

	/**
	 * 新商品流解析String数据
	 * @author franlin.wu
	 * @param uri
	 * @param type
	 * @param headers
	 * @param paramsObj
	 * @param <T>
	 * @return
	 */
	public static <T> T restGet(String uri, TypeReference<?> type, Map<String, String> headers, Object paramsObj) {
		return httpForJsonByMethod(uri, "get", type, headers, paramsObj, CommonConstants.ONE);
	}

	/**
	  * 
	  * @author wayne.liu
	  * @date  2019/8/7 15:29
	  * @param 
	  * @return 
	  */
	public static <T> T httpForJsonByMethod(String uri, String method, TypeReference<?> type, Map<String, String> headers, Object paramsObj, Integer interfaceType) {
		log.info("httpForJsonByMethod | uri:{}, method:{}, headers:{}, interfaceType:{},paramsObj:{}", uri, method, headers, interfaceType,JSON.toJSONString(paramsObj));
		if (null == type || EmptyUtils.isBlank(uri)) {
			log.debug("接口：请求地址[" + uri + "]或返回结果[" + type + "]：参数错误");
			return null;
		}
		String string = null;
		try {
			string = httpForJsonByMethod(uri, method, headers, paramsObj, interfaceType);
		} catch (ConnectTimeoutException e) {
			log.error("接口请求超时",e);
			return null;
		}catch (Exception e) {
			log.error("接口调用异常：",e);
			return null;
		}
//		log.info("-------------------------------------------------------------------");
//		log.info("httpForJsonByMethod | 响应：{}", string);
//		log.info("-------------------------------------------------------------------");
		try {
			return StringUtils.isNotBlank(string) ? JSON.parseObject(string, type.getType()) : null;
		} catch (Exception e) {
			log.error("结果数据类型转换异常：",e);
			return null;
		}
	}

	/**
	 * <b>Description:</b> 对外接口请求方式（公用）
	 * @param uri
	 * @param method
	 * @param headers
	 * @param paramsObj
	 * @param interfaceType [1走rest风格]
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException String
	 * @Note
	 * <b>Author：</b> duke.li
	 * <b>Date:</b> 2018年11月4日 下午2:32:24
	 */
	public static String httpForJsonByMethod(String uri, String method, Map<String, String> headers, Object paramsObj, Integer interfaceType)
			throws ClientProtocolException, IOException {
		CloseableHttpClient client = HttpClients.createDefault();

		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(3000) // setConnectTimeout：设置连接超时时间，单位毫秒
				.setConnectionRequestTimeout(1000) // setConnectionRequestTimeout:设置从connect Manager(连接池)获取Connection 超时时间，单位毫秒
				.setSocketTimeout(3000).build(); // setSocketTimeout:请求获取数据的超时时间(即响应时间)，单位毫秒

		ObjectMapper mapper = new ObjectMapper();
		// 对象转json 转换策略: Include.NON_EMPTY 空字符串忽略转换 / Include.NON_NULL null忽略转换
		mapper.setSerializationInclusion(Include.NON_NULL);

		// 接口参数
		StringEntity stringEntity = null;

		if(CommonConstants.ONE.equals(interfaceType)) {
			String reqStr = mapper.writeValueAsString(paramsObj);
			stringEntity = new StringEntity(reqStr, Consts.UTF_8);
			stringEntity.setContentType(DEFAULT_APPLICATION_JSON);
			stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, DEFAULT_APPLICATION_JSON));
		}
		else {
			String data = JSONObject.toJSONString(paramsObj);
			log.debug("请求参数: " + data);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("data", data));
			stringEntity = new UrlEncodedFormEntity(params, Consts.UTF_8);
		}
		HttpResponse response = null;
		if (EmptyUtils.isBlank(method) || EmptyUtils.isBlank(uri)) {
			return "{\"code\":-1,\"message\":\"参数异常\"}";
		} else if ("get".equalsIgnoreCase(method)) {
			if(paramsObj != null){
				if (!(paramsObj instanceof Map)) {
					log.error("GET请求参数类型错误（只支持Map类型）");
					return "{\"code\":-1,\"message\":\"GET请求参数类型错误（支持Map类型）\"}";
				}
				@SuppressWarnings("unchecked")
				Map<Object, Object> map = (Map<Object, Object>) paramsObj;
				uri = urlParamterStringer(uri,map);
			}
			HttpGet httpGet = new HttpGet(uri);
			httpGet.setConfig(requestConfig);
			// 参数中指定了接口自定义头部，则进行处理
			httpQequestHead(httpGet, headers, method);
			// 发送请求
			response = client.execute(httpGet);
		} else if ("post".equalsIgnoreCase(method)) {
			HttpPost httpPost = new HttpPost(uri);
			// 参数中指定了接口自定义头部，则进行处理
			httpQequestHead(httpPost, headers, method);
			// 封装参数
			httpPost.setEntity(stringEntity);
			// 发送请求
			response = client.execute(httpPost);
		} else if ("put".equalsIgnoreCase(method)) {
			HttpPut httpPut = new HttpPut(uri);
			// 参数中指定了接口自定义头部，则进行处理
			httpQequestHead(httpPut, headers, method);
			// 封装参数
			httpPut.setEntity(stringEntity);
			// 发送请求
			response = client.execute(httpPut);
		} else if ("delete".equalsIgnoreCase(method)) {
			HttpDelete httpDelete = new HttpDelete(uri);
			// 参数中指定了接口自定义头部，则进行处理
			httpQequestHead(httpDelete, headers, method);
			// 发送请求
			response = client.execute(httpDelete);
		}
		return EntityUtils.toString(response.getEntity(), Consts.UTF_8);
	}

	/**
	 * <b>Description:</b> 请求头参数封装
	 * @param request
	 * @param headers
	 * @param method void
	 * @Note
	 * <b>Author：</b> duke.li
	 * <b>Date:</b> 2018年11月22日 下午2:31:36
	 */
	public static void httpQequestHead(HttpRequestBase request, Map<String, String> headers, String method){
		if (null != headers) {
			for (Map.Entry<String, String> entry : headers.entrySet()) {
				if (null == entry || null == entry.getKey()) {
					continue;
				}
				String key = entry.getKey();
				if (null == key) {
					continue;
				}
				String value = entry.getValue();
				value = null == value ? "" : value;
				// 避免被覆盖
				if ("Accept".equals(key)) {
					value = "application/json;" + value;
				} else if ("Content-Type".equals(key)) {
					value = "application/json;" + value;
				}
				request.setHeader(key, value);
			}
		} else {
			// 默认
			request.setHeader("Content-Type", "application/json");
		}
	}

	/**
	 * <b>Description:</b> GET 请求参数拼接
	 * @param url
	 * @param map
	 * @return String
	 * @Note
	 * <b>Author：</b> duke.li
	 * <b>Date:</b> 2018年11月22日 下午2:31:54
	 */
	public static <K, V> String urlParamterStringer(String url, Map<K, V> map) {
		if (EmptyUtils.isEmpty(map)) {
			return url;
		}
		int capacity = map.size() * 30; // 设置表单长度30字节*N个请求参数
		// 参数不为空，在URL后面添加（"?"）
		StringBuilder buffer = new StringBuilder(capacity);
		buffer.append(url + "?");

		// 取出Map里面的请求参数，添加到表单String中。每个参数之间键值对之间用“=”连接，参数与参数之间用“&”连接
		Iterator<Map.Entry<K, V>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<K, V> entry = it.next();
			buffer.append(entry.getKey() + "=" + entry.getValue());
			if (it.hasNext()) {
				buffer.append("&");
			}
		}
		return buffer.toString();
	}

	/**
	 * 一次性接口
	 * 新商品流解析String数据
	 * @return
	 */
	public static String restGetOneTime(String uri) throws Exception{

		CloseableHttpClient client = HttpClients.createDefault();

		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(3000) // setConnectTimeout：设置连接超时时间，单位毫秒
				.setConnectionRequestTimeout(1000) // setConnectionRequestTimeout:设置从connect Manager(连接池)获取Connection 超时时间，单位毫秒
				.setSocketTimeout(3000).build(); // setSocketTimeout:请求获取数据的超时时间(即响应时间)，单位毫秒

		HttpResponse response = null;
		HttpGet httpGet = new HttpGet(uri);
		httpGet.setConfig(requestConfig);
		// 参数中指定了接口自定义头部，则进行处理
		httpQequestHead(httpGet, null, null);
		// 发送请求
		response = client.execute(httpGet);

		return EntityUtils.toString(response.getEntity(), Consts.UTF_8);
	}
}
