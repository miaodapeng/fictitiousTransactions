/*
 * Copyright 2018 Focus Technology, Co., Ltd. All rights reserved.
 */
package com.vedeng.common.util;

import java.io.IOException;
import java.util.Map;

import com.vedeng.passport.api.wechat.dto.res.ResWeChatDTO;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vedeng.common.model.ResultInfo;

/**
 * <b>Description: rest接口请求工具类</b><br>
 * <b>Author: Franlin</b>
 * 
 * @fileName HttpRestClientUtil.java <br>
 *           <b>Date: 2018-09-25 下午6:34:20 </b>
 *
 */
public class HttpRestClientUtil {

	private static final String APPLICATION_JSON = "application/json";

	private static final String CONTENT_TYPE_TEXT_JSON = "text/json";

	/**
	 * 日志
	 */
	private static Logger log = LoggerFactory.getLogger(HttpRestClientUtil.class);

	/**
	 * 
	 * <b>Description: rest统一接口返回Json字符串</b><br>
	 * 
	 * @param uri
	 *            请求uri
	 * @param method
	 *            请求方法GET,POST,PUT,DELETE
	 * @param headers
	 *            请求头参数
	 * @param params
	 *            接口入参
	 * @return <b>Author: Franlin</b> <br>
	 *         <b>Date: 2018年9月25日 上午9:06:42 </b>
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static String httpForJsonByMethod(String uri, String method, Map<String, String> headers, Object paramsObj)
			throws ClientProtocolException, IOException {
		HttpClient client = HttpClients.createDefault();

		ObjectMapper mapper = new ObjectMapper();
		// 对象转json 转换策略: Include.NON_EMPTY 空字符串忽略转换 / Include.NON_NULL null忽略转换
		mapper.setSerializationInclusion(Include.NON_EMPTY);

		String reqStr = mapper.writeValueAsString(paramsObj);
		log.info("请求参数: " + reqStr);
		// System.out.println("================ " + reqStr);
		StringEntity stringEntity = new StringEntity(reqStr, "UTF-8");
		stringEntity.setContentType(CONTENT_TYPE_TEXT_JSON);
		stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
		HttpResponse response = null;
		if (null == method || null == uri) {
			return "{\"code\":204,\"message\":\"参数异常\"}";
		}
		else if ("get".equalsIgnoreCase(method)) {
			HttpGet request = new HttpGet(uri);

			request.setHeader("Content-Type", "application/json");
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
			}
			response = client.execute(request);
		} else if ("post".equalsIgnoreCase(method)) {
			HttpPost request = new HttpPost(uri);
			request.setHeader("Content-Type", "application/json");
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
			}
			request.setEntity(stringEntity);
			response = client.execute(request);
		} else if ("put".equalsIgnoreCase(method)) {
			HttpPut request = new HttpPut(uri);
			request.setHeader("Content-Type", "application/json");
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
			}

			request.setEntity(stringEntity);
			response = client.execute(request);
		} else if ("delete".equalsIgnoreCase(method)) {
			HttpDelete request = new HttpDelete(uri);
			request.setHeader("Content-Type", "application/json");
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
			}

			response = client.execute(request);
		}
		String resultStr = EntityUtils.toString(response.getEntity(), Consts.UTF_8);
		//log.info("响应: " + resultStr);
		return resultStr;
	}

	/**
	 * 
	 * <b>Description: rest统一接口</b><br>
	 * 
	 * @param uri
	 *            请求uri
	 * @param method
	 *            请求方法GET,POST,PUT,DELETE
	 * @param type
	 *            返回类型
	 * @param headers
	 *            请求头参数
	 * @param params
	 *            接口入参
	 * @return <b>Author: Franlin</b> <br>
	 *         <b>Date: 2018年9月25日 上午9:06:42 </b>
	 */
	public static ResultInfo<?> httpForJsonByMethod(String uri, String method, TypeReference<?> type,
			Map<String, String> headers, Object paramsObj)
			throws JsonParseException, JsonMappingException, UnsupportedOperationException, IOException {
		ResultInfo<?> result = new ResultInfo<>();

		if (null == type) {
			log.debug("入参异常: " + type);
			result.setCode(204);
			result.setMessage("参数异常");
			return result;
		}

		return JsonUtils.readValueByType(httpForJsonByMethod(uri, method, headers, paramsObj), type);
	}

    /** 
    * @Description: 微信消息推送返回值接受 
    * @Param: [uri, method, type, headers, paramsObj] 
    * @return: com.vedeng.passport.api.wechat.dto.res.ResWeChatDTO 
    * @Author: addis
    * @Date: 2019/10/8 
    */
	public static ResWeChatDTO httpForJsonByMethod2(String uri, String method, TypeReference<?> type,
													Map<String, String> headers, Object paramsObj)
			throws JsonParseException, JsonMappingException, UnsupportedOperationException, IOException {
		ResWeChatDTO result = new ResWeChatDTO();

		if (null == type) {
			log.debug("入参异常: " + type);
			result.setCode("204");
			result.setMessage("参数异常");
			return result;
		}

		return JsonUtils.readValueByType(httpForJsonByMethod(uri, method, headers, paramsObj), type);
	}
	/**
	 * 
	 * <b>Description: post接口</b><br>
	 * 
	 * @param uri
	 *            请求uri
	 * @param type
	 *            返回类型
	 * @param headers
	 *            请求头参数
	 * @param params
	 *            接口入参
	 * @return <b>Author: Franlin</b> <br>
	 *         <b>Date: 2018年9月25日 上午9:06:42 </b>
	 */
	public static ResultInfo<?> post(String uri, TypeReference<?> type, Map<String, String> headers, Object paramsObj) {
		ResultInfo<?> result = null;
		try {

			result = httpForJsonByMethod(uri, "post", type, headers, paramsObj);
		} catch (Exception e) {
			log.error("接口调用异常: ", e);
			result = new ResultInfo<>();
			result.setCode(205);
			result.setMessage("接口调用异常");
		}

		return result;
	}
	
	/** 
	* @Description: 微信发送请求 返回值与微信返回一致 
	* @Param: [uri, type, headers, paramsObj] 
	* @return: com.vedeng.passport.api.wechat.dto.res.ResWeChatDTO 
	* @Author: addis
	* @Date: 2019/10/8 
	*/ 
	public static ResWeChatDTO post2(String uri, TypeReference<?> type, Map<String, String> headers, Object paramsObj) {
		ResWeChatDTO result = null;
		try {

			result = httpForJsonByMethod2(uri, "post", type, headers, paramsObj);
		} catch (Exception e) {
			log.error("接口调用异常: ", e);
			result = new ResWeChatDTO();
			result.setCode("205");
			result.setMessage("接口调用异常");
		}

		return result;
	}

	/**
	 * 
	 * <b>Description: post接口</b><br>
	 * 
	 * @param uri
	 *            请求uri
	 * @param type
	 *            返回类型
	 * @param params
	 *            接口入参
	 * @return <b>Author: Franlin</b> <br>
	 *         <b>Date: 2018年9月25日 上午9:06:42 </b>
	 */
	public static ResultInfo<?> post(String uri, TypeReference<?> type, Object paramsObj) {
		ResultInfo<?> result = null;
		try {

			result = httpForJsonByMethod(uri, "post", type, null, paramsObj);
		} catch (Exception e) {
			log.error("接口调用异常: ", e);
			result = new ResultInfo<>();
			result.setCode(205);
			result.setMessage("接口调用异常");
		}

		return result;
	}

	/**
	 * 
	 * <b>Description: put接口</b><br>
	 * 
	 * @param uri
	 *            请求uri
	 * @param type
	 *            返回类型
	 * @param headers
	 *            请求头参数
	 * @param params
	 *            接口入参
	 * @return <b>Author: Franlin</b> <br>
	 *         <b>Date: 2018年9月25日 上午9:06:42 </b>
	 */
	public static ResultInfo<?> put(String uri, TypeReference<?> type, Map<String, String> headers, Object paramsObj) {
		ResultInfo<?> result = null;
		try {

			result = httpForJsonByMethod(uri, "put", type, headers, paramsObj);
		} catch (Exception e) {
			log.error("接口调用异常: ", e);
			result = new ResultInfo<>();
			result.setCode(205);
			result.setMessage("接口调用异常");
		}

		return result;
	}

	/**
	 * 
	 * <b>Description: put接口</b><br>
	 * 
	 * @param uri
	 *            请求uri
	 * @param type
	 *            返回类型
	 * @param headers
	 *            请求头参数
	 * @param params
	 *            接口入参
	 * @return <b>Author: Franlin</b> <br>
	 *         <b>Date: 2018年9月25日 上午9:06:42 </b>
	 */
	public static ResultInfo<?> put(String uri, TypeReference<?> type, Object paramsObj) {
		ResultInfo<?> result = null;
		try {

			result = httpForJsonByMethod(uri, "put", type, null, paramsObj);
		} catch (Exception e) {
			log.error("接口调用异常: ", e);
			result = new ResultInfo<>();
			result.setCode(205);
			result.setMessage("接口调用异常");
		}

		return result;
	}

	/**
	 * 
	 * <b>Description: get接口</b><br>
	 * 
	 * @param uri
	 *            请求uri
	 * @param type
	 *            返回类型
	 * @param headers
	 *            请求头参数
	 * @param params
	 *            接口入参
	 * @return <b>Author: Franlin</b> <br>
	 *         <b>Date: 2018年9月25日 上午9:06:42 </b>
	 */
	public static ResultInfo<?> get(String uri, TypeReference<?> type, Map<String, String> headers, Object paramsObj) {
		ResultInfo<?> result = null;
		try {

			result = httpForJsonByMethod(uri, "get", type, headers, paramsObj);
		} catch (Exception e) {
			log.error("接口调用异常: ", e);
			result = new ResultInfo<>();
			result.setCode(205);
			result.setMessage("接口调用异常");
		}

		return result;
	}

	/**
	 * 
	 * <b>Description: get接口</b><br>
	 * 
	 * @param uri
	 *            请求uri
	 * @param type
	 *            返回类型
	 * @param params
	 *            接口入参
	 * @return <b>Author: Franlin</b> <br>
	 *         <b>Date: 2018年9月25日 上午9:06:42 </b>
	 */
	public static ResultInfo<?> get(String uri, TypeReference<?> type) {
		ResultInfo<?> result = null;
		try {

			result = httpForJsonByMethod(uri, "get", type, null, null);
		} catch (Exception e) {
			log.error("接口调用异常: ", e);
			result = new ResultInfo<>();
			result.setCode(205);
			result.setMessage("接口调用异常");
		}

		return result;
	}

	/**
	 * 
	 * <b>Description: delete接口</b><br>
	 * 
	 * @param uri
	 *            请求uri
	 * @param type
	 *            返回类型
	 * @param params
	 *            接口入参
	 * @return <b>Author: Franlin</b> <br>
	 *         <b>Date: 2018年9月25日 上午9:06:42 </b>
	 */
	public static ResultInfo<?> delete(String uri, TypeReference<?> type, Map<String, String> headers,
			Object paramsObj) {
		ResultInfo<?> result = null;
		try {
			result = httpForJsonByMethod(uri, "delete", type, headers, paramsObj);
		} catch (Exception e) {
			log.error("接口调用异常: ", e);
			result = new ResultInfo<>();
			result.setCode(205);
			result.setMessage("接口调用异常");
		}

		return result;
	}

	/**
	 * 
	 * <b>Description: delete接口</b><br>
	 * 
	 * @param uri
	 *            请求uri
	 * @param type
	 *            返回类型
	 * @return <b>Author: Franlin</b> <br>
	 *         <b>Date: 2018年9月25日 上午9:06:42 </b>
	 */
	public static ResultInfo<?> delete(String uri, TypeReference<?> type) {
		ResultInfo<?> result = null;
		try {
			result = httpForJsonByMethod(uri, "delete", type, null, null);
		} catch (Exception e) {
			log.error("接口调用异常: ", e);
			result = new ResultInfo<>();
			result.setCode(205);
			result.setMessage("接口调用异常");
		}

		return result;
	}

}
