package com.test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.authorization.model.User;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.system.service.UserService;

import net.sf.json.JSONObject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring.xml","classpath:spring-mybatis.xml"})
public class TestUser {
	private static final Logger logger = LoggerFactory.getLogger(TestCompany.class);
	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Test
	public void testlogin(){
//		User user = userService.login("admin", "123456");
//		logger.info(JSON.toJSONString(user));
	}
	@Test
	public void testshowUserByPage(){
//		Map<String,Object> params = new HashMap<String,Object>(); 
//		params.put("startIndex", 0);  
//        params.put("endIndex", 5);
//        params.put("username","admin20");
//		List<User> list =userService.showUserByPage(params);
//		logger.info(JSON.toJSONString(list));
	}
	
	@Test
	public void testTime(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
		try {
			date = simpleDateFormat.parse("2017-04-11 12:23:33");
			long ts = date.getTime();
			String res = String.valueOf(ts);
			System.out.println(res);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetAllUser(){
//		List<User> userList = userService.getAllUser();
//		logger.info(JSON.toJSONString(userList,SerializerFeature.WriteMapNullValue));  //SerializerFeature.WriteMapNullValue  null字段可以输出
	}
	
	@Test
	public void HttpGetData() {
		try {
			HttpClient httpclient = new DefaultHttpClient();
			String url = "http://172.16.0.70:8900/dbcenter/api/user/getUserById.htm?userId=18";
			// HttpPost httppost = new HttpPost(url);
			HttpGet httpget = new HttpGet(url);

			HttpResponse response = httpclient.execute(httpget);
			// 检验状态码，如果成功接收数据
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String rev = EntityUtils.toString(response.getEntity());// 返回json格式： {"id":"","name":""}
				JSONObject obj = JSONObject.fromObject(rev);
				User user = (User) JSONObject.toBean(obj, User.class);
				System.out.println("返回数据===" + user.toString());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			System.out.println("-----");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("--+++---");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("++++++++++");
		}
	}
	
	 @Value("${http_url}")  
	 protected String httpUrl;
	
	@Test
	public void httpPostData(){
		
		Map<String,Object> map = new HashMap<>();
		map.put("id", "18");map.put("name", "nihao");
		System.out.println(JSONObject.fromObject(map));
		
		User user = new User();
		user.setUserId(18);user.setOrgName("select * from abc;'or 1=1'");
		String url = httpUrl + "usertest/getUser2ById.htm";
		// 定义反序列化 数据格式
/*		final TypeReference<ResultInfo<List<User>>> TypeRef = new TypeReference<ResultInfo<List<User>>>() {};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, user,"123","123", TypeRef);
			user = (User) result.getData();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<User>> TypeRef2 = new TypeReference<ResultInfo<User>>() {};
		try {
			ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, user,"123","123", TypeRef2);
			user = (User) result2.getData();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
