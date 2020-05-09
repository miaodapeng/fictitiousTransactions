package com.vedeng.common.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.common.key.CryptBase64Tool;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.JsonUtils;

public class HttpClientUtils {

	private static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

	/**
	 * <b>Description:</b><br>
	 * post方式请求Api(包含分页信息)
	 * 
	 * @param url
	 * @param paraMap
	 * @param type
	 * @return
	 * @throws IOException
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2017年5月8日 下午6:45:27
	 */
	public static Object post(String url, Object paraMap, String clientId, String clientKey, TypeReference<?> type,
							  Page page) throws IOException {

		// 序列化成 json
		String data = JSONObject.toJSONString(paraMap);
		//logger.info("start get dbcenter data:" + url+data);
		// 准备发送的参数
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("systime", System.currentTimeMillis() + "");
		paramMap.put("data", data);
		paramMap.put("clientId", clientId);
		paramMap.put("clientKey", clientKey);
		// 分页信息
		String pageJson = JsonUtils.translateToJson(page == null ? "" : page);
		paramMap.put("page", pageJson);

		// http post 参数
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		Iterator<Map.Entry<String, String>> $it = paramMap.entrySet().iterator();
		while ($it.hasNext()) {
			Map.Entry<String, String> entry = $it.next();
			params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		// http post 请求
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
		CloseableHttpResponse response = httpclient.execute(httpPost);

		int status = response.getStatusLine().getStatusCode();
		//logger.info(status+"end get dbcenter data:" + url+data+status);
		// logger.debug("Http Post Status:" + status);

		if (status != 200) {
			logger.warn("Http Post Status:" +url+ status+EntityUtils.toString(response.getEntity(), Consts.UTF_8));
			return null;
		}
		try {
			// 返回结果 格式转换
			String result = EntityUtils.toString(response.getEntity(), Consts.UTF_8);
			//logger.info(status+"end get dbcenter data:" + url+data+"\t"+result);
			JSONObject jsStr = JSONObject.parseObject(result);
			if (jsStr.getIntValue("code") != -1 && jsStr.get("data") != null
					&& StringUtils.isNotBlank(jsStr.getString("data"))) {
				JSONObject param = JSONObject.parseObject(jsStr.getString("param"));
				String sysdate = param.getString("systime");
				String skey = param.getString("skey");
				String result_data = (CryptBase64Tool.desDecrypt(jsStr.getString("data"), skey + sysdate)).toString();
				jsStr.put("data", JSON.parse(result_data));
				jsStr.put("page", JSON.parse(param.getString("page")));
				result = jsStr.toJSONString();
			}
			return JsonUtils.readValueByType(result, type);

		} catch (Exception e) {
			logger.error(status+"error get dbcenter data:" + url+data+"\t");
			return new ResultInfo<>(-1, "客户端解析数据失败");
		} finally {
			response.close();
		}
	}

	/**
	 * <b>Description:</b><br>
	 * post方式请求Api
	 *
	 * @param url
	 * @param paraMap
	 * @param type
	 * @return
	 * @throws IOException
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2017年5月8日 下午6:45:27
	 */
	public static Object post(String url, Object paraMap, String clientId, String clientKey, TypeReference<?> type)
			throws IOException {

		// 序列化成 json
		String data = JSONObject.toJSONString(paraMap);
		// 准备发送的参数
		Map<String, String> paramMap = new HashMap<String, String>();

		paramMap.put("systime", System.currentTimeMillis() + "");
		paramMap.put("data", data);

		paramMap.put("clientId", clientId);
		paramMap.put("clientKey", clientKey);

		// http post 参数
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		Iterator<Map.Entry<String, String>> $it = paramMap.entrySet().iterator();
		while ($it.hasNext()) {
			Map.Entry<String, String> entry = $it.next();
			params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}

		// logger.debug("Http Post Para:" + paramMap);

		// http post 请求
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
		CloseableHttpResponse response = httpclient.execute(httpPost);

		int status = response.getStatusLine().getStatusCode();

		// logger.debug("Http Post Status:" + status);

		if (status != 200) {
			logger.error("Http Post Status:500:" +url+ status+EntityUtils.toString(response.getEntity(), Consts.UTF_8));
			return null;
		}

		try {
			// 返回结果 格式转换
			String result = EntityUtils.toString(response.getEntity(), Consts.UTF_8);

			// logger.debug("Http Post Result:" + result);

			JSONObject jsStr = JSONObject.parseObject(result);
			if (jsStr.getIntValue("code") != -1 && jsStr.get("data") != null
					&& StringUtils.isNotBlank(jsStr.getString("data"))) {

				JSONObject param = JSONObject.parseObject(jsStr.getString("param"));

				String sysdate = param.getString("systime");
				String skey = param.getString("skey");// String random =
														// param.getString("random");
				String result_data = (CryptBase64Tool.desDecrypt(jsStr.getString("data"), skey + sysdate)).toString();
				/*
				 * String result_data = ""; if (!logger.isDebugEnabled()){//非debug模式--进行解密
				 * result_data = (CryptBase64Tool.desDecrypt(jsStr.getString("data"), skey +
				 * sysdate)).toString(); }else{ result_data = jsStr.getString("data"); }
				 */
				// jsStr.fluentPut("data", JSONObject.parseObject(result_data));
				jsStr.put("data", JSON.parse(result_data));
				result = jsStr.toJSONString();
			}

			return JsonUtils.readValueByType(result, type);
		} catch (Exception e) {
			logger.error("Http Post Status:解析数据失败！",e);
			return new ResultInfo<>(-1, "客户端解析数据失败");
		} finally {
			response.close();
		}
	}

	/**
	 * <b>Description:</b><br>
	 * 压缩post方式请求Api
	 *
	 * @param url
	 * @param paraMap
	 * @param type
	 * @return
	 * @throws IOException
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2017年5月8日 下午6:45:27
	 */
	public static Object compressPost(String url, Object paraMap, String clientId, String clientKey,
			TypeReference<?> type) throws IOException {

		logger.debug("Http Post Url:" + url);

		// 序列化成 json
		String data = JSONObject.toJSONString(paraMap);
		// 准备发送的参数
		Map<String, String> paramMap = new HashMap<String, String>();

		paramMap.put("systime", System.currentTimeMillis() + "");
		paramMap.put("data", data);

		paramMap.put("clientId", clientId);
		paramMap.put("clientKey", clientKey);

		// http post 参数
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		Iterator<Map.Entry<String, String>> $it = paramMap.entrySet().iterator();
		while ($it.hasNext()) {
			Map.Entry<String, String> entry = $it.next();
			params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}

		// logger.debug("Http Post Para:" + paramMap);

		// http post 请求
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
		CloseableHttpResponse response = httpclient.execute(httpPost);

		int status = response.getStatusLine().getStatusCode();

		// logger.debug("Http Post Status:" + status);

		if (status != 200) {
			logger.warn("Http Post Status:" +url+ status+EntityUtils.toString(response.getEntity(), Consts.UTF_8));
			return null;
		}

		try {
			// 返回结果 格式转换
			String result = EntityUtils.toString(response.getEntity(), Consts.UTF_8);

			// logger.debug("Http Post Result:" + result);

			JSONObject jsStr = JSONObject.parseObject(result);
			if (jsStr.getIntValue("code") != -1 && jsStr.get("data") != null
					&& StringUtils.isNotBlank(jsStr.getString("data"))) {

				JSONObject param = JSONObject.parseObject(jsStr.getString("param"));

				String sysdate = param.getString("systime");
				String skey = param.getString("skey");// String random =
														// param.getString("random");
				String result_data = (CryptBase64Tool.desDecrypt(jsStr.getString("data"), skey + sysdate)).toString();
				/*
				 * String result_data = ""; if (!logger.isDebugEnabled()){//非debug模式--进行解密
				 * result_data = (CryptBase64Tool.desDecrypt(jsStr.getString("data"), skey +
				 * sysdate)).toString(); }else{ result_data = jsStr.getString("data"); }
				 */
				// jsStr.fluentPut("data", JSONObject.parseObject(result_data));
				jsStr.put("data", result_data);
				result = jsStr.toJSONString();
			}

			return result;
		} catch (Exception e) {
			logger.error("Http Post Status:解析数据失败！");
			return new ResultInfo<>(-1, "客户端解析数据失败");
		} finally {
			response.close();
		}
	}
	public static Object getForYxg(String url, Object paraMap, String clientId, String clientKey, TypeReference<?> type)
			throws IOException {

		logger.debug("Http Get Url:" + url);

		// 序列化成 json
		String data = JSONObject.toJSONString(paraMap);
		// 准备发送的参数
		Map<String, String> paramMap = new HashMap<String, String>();

		paramMap.put("timestamp", System.currentTimeMillis() + "");
		paramMap.put("data", data);

		paramMap.put("clientId", clientId);
		paramMap.put("clientKey", clientKey);

		// http post 参数
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		Iterator<Map.Entry<String, String>> $it = paramMap.entrySet().iterator();
		while ($it.hasNext()) {
			Map.Entry<String, String> entry = $it.next();
			// params.add(new BasicNameValuePair(entry.getKey(),
			// entry.getValue()));
			params.add(new BasicNameValuePair(entry.getKey(),
					new String(entry.getValue().getBytes("ISO-8859-1"), "UTF-8")));
		}

		// http post 请求
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = httpclient.execute(httpGet);

		int status = response.getStatusLine().getStatusCode();

		if (status != 200) {
			return null;
		}

		try {
			// 返回结果 格式转换
			String result = EntityUtils.toString(response.getEntity(), Consts.UTF_8);
			logger.debug("Http Get Result:" + result);

			JSONObject jsStr = JSONObject.parseObject(result);
			if (jsStr.getIntValue("code") != -1) {
				result = jsStr.toJSONString();
			}
			return JsonUtils.readValueByType(result, type);
		} catch (Exception e) {
			logger.debug("Http Post Status:解析数据失败！");
			e.printStackTrace();
			return new ResultInfo<>(-1, "客户端解析数据失败");
		} finally {
			response.close();
		}
	}

	/**
	 * <b>Description:</b><br>
	 * get方式请求Api
	 *
	 * @param url
	 * @param paraMap
	 * @param type
	 * @return
	 * @throws IOException
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2017年5月9日 上午9:00:19
	 */
	public static Object get(String url, Object paraMap, String clientId, String clientKey, TypeReference<?> type)
			throws IOException {


		// 序列化成 json
		String data = JSONObject.toJSONString(paraMap);
		// 准备发送的参数
		Map<String, String> paramMap = new HashMap<String, String>();

		paramMap.put("timestamp", System.currentTimeMillis() + "");
		paramMap.put("data", data);

		paramMap.put("clientId", clientId);
		paramMap.put("clientKey", clientKey);

		// http post 参数
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		Iterator<Map.Entry<String, String>> $it = paramMap.entrySet().iterator();
		while ($it.hasNext()) {
			Map.Entry<String, String> entry = $it.next();
			// params.add(new BasicNameValuePair(entry.getKey(),
			// entry.getValue()));
			params.add(new BasicNameValuePair(entry.getKey(),
					new String(entry.getValue().getBytes("ISO-8859-1"), "UTF-8")));
		}

		// http post 请求
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = httpclient.execute(httpGet);

		int status = response.getStatusLine().getStatusCode();

		if (status != 200) {
			logger.warn("Http Post Status:" +url+ status+EntityUtils.toString(response.getEntity(), Consts.UTF_8));
			return null;
		}

		try {
			// 返回结果 格式转换
			String result = EntityUtils.toString(response.getEntity(), Consts.UTF_8);

			JSONObject jsStr = JSONObject.parseObject(result);
			if (jsStr.getIntValue("code") != -1) {

				JSONObject param = JSONObject.parseObject(jsStr.getString("param"));

				String sysdate = param.getString("systime");
				String skey = param.getString("skey");// String random =
														// param.getString("random");
				String result_data = (CryptBase64Tool.desDecrypt(jsStr.getString("data"), skey + sysdate)).toString();
				/*
				 * String result_data = ""; if (!logger.isDebugEnabled()){//非debug模式--进行解密
				 * result_data = (CryptBase64Tool.desDecrypt(jsStr.getString("data"), skey +
				 * sysdate)).toString(); }else{ result_data = jsStr.getString("data"); }
				 */
				// jsStr.fluentPut("data", JSONObject.parseObject(result_data));
				jsStr.put("data", JSON.parse(result_data));
				result = jsStr.toJSONString();
			}

			return JsonUtils.readValueByType(result, type);
		} catch (Exception e) {
			logger.error("Http Post Status:解析数据失败！",e);
			return new ResultInfo<>(-1, "客户端解析数据失败");
		} finally {
			response.close();
		}
	}

	/**
	 *
	 * <b>Description:PUT请求</b>
	 *
	 * @param url
	 * @param paraMap
	 * @param type
	 * @return
	 * @throws IOException
	 *             Object
	 * @Note <b>Author：</b> cooper.xu <b>Date:</b> 2018年11月23日 上午11:08:08
	 */
	public static Object put(String url, Object paraMap, Map<String, String> headers, TypeReference<?> type)
			throws IOException {
		logger.debug("Http Post Url:" + url);
		StringEntity stringEntity = new StringEntity((String) paraMap, "UTF-8");
		HttpPut request = new HttpPut(url);
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
			request.setEntity(stringEntity);
		}
		// http post 请求
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = httpclient.execute(request);
		int status = response.getStatusLine().getStatusCode();
		if (status != 200) {
			return null;
		}
		try {
			// 返回结果 格式转换
			String result = EntityUtils.toString(response.getEntity(), Consts.UTF_8);
			return JsonUtils.readValueByType(result, type);
		} catch (Exception e) {
			logger.error("Http Post Status:解析数据失败！",e);
			return new ResultInfo<>(-1, "客户端解析数据失败");
		} finally {
			response.close();
		}
	}

	/**
	 * <b>Description:</b><br>
	 * post方式请求Api(包含分页信息)
	 *
	 * @param url
	 * @return
	 * @throws IOException
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2017年5月8日 下午6:45:27
	 */
	public static String postJSON(String url, String json) throws IOException {
		// http post 请求
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
		StringEntity s = new StringEntity(json.toString(), "UTF-8");
		s.setContentEncoding("UTF-8");
		s.setContentType("application/json");// 发送json数据需要设置contentType
		httpPost.setEntity(s);
		CloseableHttpResponse response = httpclient.execute(httpPost);
		try {
			// 返回结果 格式转换
			String result = EntityUtils.toString(response.getEntity(), Consts.UTF_8);
			return result;

		} catch (Exception e) {
			logger.error("HpostJSON:" +url,e);
		} finally {
			response.close();
		}
		return null;
	}
}
