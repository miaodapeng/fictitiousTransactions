package com.vedeng.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.common.constants.Contant;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.vedeng.common.model.ResultInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <b>Description:</b><br> 短信工具
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.common.util
 * <br><b>ClassName:</b> SmsUtil
 * <br><b>Date:</b> 2017年9月28日 上午9:30:56
 */
public class SmsUtil {
	private static Logger logger = LoggerFactory.getLogger(SmsUtil.class);
	/**
	 * 短信提供商开设账号时提供一下参数:
	 * 
	 * 账号、密码、通信key、IP、端口
	 */

	private static String account;
	
	private static String password;
	
	private static String veryCode;
	
	private static String http_url;
	
	
	/**
	 * 默认字符编码集
	 */
	public static final String CHARSET_UTF8 = "UTF-8";
	
	/**
	 * 查询账号余额
	 * @return 账号余额，乘以10为短信条数
	 * String xml字符串，格式请参考文档说明
	 */
	public static ResultInfo getBalance(){
		ResultInfo resultInfo = new ResultInfo<>();
		String balanceUrl = http_url + "/service/httpService/httpInterface.do?method=getAmount";
		Map<String,String> params = new HashMap<String,String>();
		params.put("username",account);
		params.put("password",password);
		params.put("veryCode",veryCode);
		String xmlString = sendHttpPost(balanceUrl, params);
		
		XmlExercise xmlExercise = new XmlExercise();
		Map result = xmlExercise.xmlToMap(xmlString);
		List resultList = (List)result.get("data");
		Map object = (Map) resultList.get(0);
		if(object.get("status").equals("0")){
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
			resultInfo.setData(object.get("account"));
		}
		return resultInfo;
	}
	/**
	 * 发送普通短信  普通短信发送需要人工审核
	 * 请求地址：
	 *   UTF-8编码：/service/httpService/httpInterface.do?method=sendUtf8Msg
	 *   GBK编码：/service/httpService/httpInterface.do?method=sendGbkMsg
	 * @param mobile 手机号码, 多个号码以英文逗号隔开,最多支持100个号码
	 * @param content 短信内容 
	 * @return  
	 * String xml字符串，格式请参考文档说明
	 */
	public static Boolean sendSms(String mobile,String content){
		logger.info("start send sms mobile: "+mobile + " content:"+content);
		String sendSmsUrl = http_url + "/service/httpService/httpInterface.do?method=sendUtf8Msg";
		Map<String,String> params = new HashMap<String,String>();
		params.put("username", account);
		params.put("password", password);
		params.put("veryCode", veryCode);
		params.put("mobile", mobile);
		params.put("content", content);
		params.put("msgtype", "1");
		params.put("code", "utf-8");
		String xmlString = sendHttpPost(sendSmsUrl, params);
		XmlExercise xmlExercise = new XmlExercise();
		Map result = xmlExercise.xmlToMap(xmlString);
		Map object = (Map)result.get("mt");
		if(object.get("status").equals("0")){
			logger.info("send sms mobile: "+ mobile +" content: "+content + " success");
			return true;
		}
		logger.error("send sms mobile: "+ mobile +" content: "+content + " error");
		return false;
	}
	
	/**
	 * 模版短信,无需人工审核，直接发送
	 *   (短信模版的创建参考客户端操作手册)
	 *   模版：@1@会员，动态验证码@2@(五分钟内有效)。请注意保密，勿将验证码告知他人。
	 *   参数值:@1@=某某,@2@=4293
	 *   手机接收内容：【短信签名】某某会员，动态验证码4293(五分钟内有效)。请注意保密，勿将验证码告知他人。
	 *   
	 *   请求地址：
	 *   UTF-8编码：/service/httpService/httpInterface.do?method=sendUtf8Msg
	 *   GBK编码：/service/httpService/httpInterface.do?method=sendGbkMsg
	 *   注意:
		 1.发送模板短信变量值不能包含英文逗号和等号（, =）
		 2.采用此方式特殊字符需要转义
	 * 		+   %2b  
	 *  	空格    %20  
	 * 		&   %26
	 * 		%	%25
	 * @param mobile 手机号码
	 * @param tplId 模板编号，对应客户端模版编号
	 * @param content 模板参数值，以英文逗号隔开，如：@1@=某某,@2@=4293
	 * @return xml字符串，格式请参考文档说明
	 */
	public static Boolean sendTplSms(String mobile,String tplId,String content){
		logger.info("start send sendTplSms mobile: "+mobile + " tplId:"+tplId);
		String sendTplSmsUrl = http_url + "/service/httpService/httpInterface.do?method=sendUtf8Msg";
		Map<String,String> params = new HashMap<String,String>();
		params.put("username", account);
		params.put("password", password);
		params.put("veryCode", veryCode);
		params.put("mobile", mobile);
		params.put("content", content);	//变量值，以英文逗号隔开
		params.put("msgtype", "2");		//2-模板短信
		params.put("tempid", tplId);	//模板编号
		params.put("code", "utf-8");
		String xmlString = sendHttpPost(sendTplSmsUrl, params);
		XmlExercise xmlExercise = new XmlExercise();
		Map result = xmlExercise.xmlToMap(xmlString);
		Map object = (Map)result.get("mt");
		if(object.get("status").equals("0")){
			logger.info("send sendTplSms mobile: "+ mobile +" tplId: "+tplId + " success");
			return true;
		}
		logger.error("send sendTplSms mobile: "+ mobile +" tplId: "+tplId + " error");
		return false;
	}
	
	/***
	 * 查询下发短信的状态报告
	 * @return
	 * String  xml字符串，格式请参考文档说明
	 */
	public static String queryReport(){
		String reportUrl = http_url + "/service/httpService/httpInterface.do?method=queryReport";
		Map<String,String> params = new HashMap<String,String>();
		params.put("username", account);
		params.put("password", password);
		params.put("veryCode", veryCode);
		String result = sendHttpPost(reportUrl, params);
		return result;
	}
	/**
	 * 查询上行回复短信
	 * @return
	 * String  xml字符串，格式请参考文档说明
	 */
	public static String queryMo(){
		String moUrl = http_url + "/service/httpService/httpInterface.do?method=queryMo";
		Map<String,String> params = new HashMap<String,String>();
		params.put("username", account);
		params.put("password", password);
		params.put("veryCode", veryCode);
		String result = sendHttpPost(moUrl, params);
		return result;
	}
	
	
	/**
	 * 
	 * @param apiUrl 接口请求地址
	 * @param paramsMap 请求参数集合
	 * @return xml字符串，格式请参考文档说明
	 * String
	 */
    private static String sendHttpPost(String apiUrl, Map<String, String> paramsMap) {
        CloseableHttpClient client = HttpClients.createDefault();
        String responseText = "";
        CloseableHttpResponse response = null;
        try {
            HttpPost method = new HttpPost(apiUrl);
            if (paramsMap != null) {
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> param : paramsMap.entrySet()) {
                    NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
                    paramList.add(pair);
                }
                method.setEntity(new UrlEncodedFormEntity(paramList, CHARSET_UTF8));
            }
            response = client.execute(method);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseText = EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            logger.error(Contant.ERROR_MSG, e);
        } finally {
            try {
                response.close();
            } catch (Exception e) {
                logger.error(Contant.ERROR_MSG, e);
            }
        }
        return responseText;
    }
    
	
	
	
	public static String getAccount() {
		return account;
	}

	public static void setAccount(String account) {
		SmsUtil.account = account;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		SmsUtil.password = password;
	}

	public static String getVeryCode() {
		return veryCode;
	}

	public static void setVeryCode(String veryCode) {
		SmsUtil.veryCode = veryCode;
	}

	public static String getHttp_url() {
		return http_url;
	}

	public static void setHttp_url(String http_url) {
		SmsUtil.http_url = http_url;
	}

	public static void main(String[] args) {
//		ResultInfo balance = SmsUtil.getBalance();
//		System.out.println(balance);
		SmsUtil.sendSms("15151831109", "尊敬的用户您好，您本次验证码为12345678。您正在进行注册操作，请妥善保存验证码。");
	}
}
