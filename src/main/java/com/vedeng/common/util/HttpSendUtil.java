package com.vedeng.common.util;

import java.io.IOException;

import com.common.constants.Contant;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * <b>Description:</b><br> 天眼查接口调用
 * @author scott
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.common.util
 * <br><b>ClassName:</b> HttpSendUtil
 * <br><b>Date:</b> 2018年1月11日 上午8:51:03
 */
public class HttpSendUtil {
	public static Logger logger = LoggerFactory.getLogger(HttpSendUtil.class);

	private static String DETAILURL = "https://open.api.tianyancha.com/services/v4/open/baseinfoV2.json?name=";
	private static String CUSTOMERLISTURL = "https://open.api.tianyancha.com/services/v4/open/searchV2.json?word=";
	
	/**
	 * 
	 * <b>Description:</b><br> 调用接口
	 * @param type
	 * @param customerName
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2018年1月11日 上午9:03:12
	 */
	public static String queryDetails(Integer type,String customerName)  
    {  
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = null;
		if(type==1){
			httpGet = new HttpGet(CUSTOMERLISTURL+customerName);
		}else{
			httpGet = new HttpGet(DETAILURL+customerName);
		}
	    httpGet.setHeader("Authorization", "7b3b6f97-96e2-47bf-b41f-a4f4d29da33f");
	    CloseableHttpResponse response1 = null; 
	    String result= "";  
	    try {
	    	response1 = httpclient.execute(httpGet);
	        HttpEntity entity1 = response1.getEntity();
	        result = EntityUtils.toString(entity1);
	    } catch (ClientProtocolException e) {
			logger.error(Contant.ERROR_MSG, e);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		} finally {
	        try {
				response1.close();
			} catch (IOException e) {
				logger.error(Contant.ERROR_MSG, e);
			}
	    }
	    return result;  
    }  
}
