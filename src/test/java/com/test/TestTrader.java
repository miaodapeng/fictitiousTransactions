package com.test;

import java.io.IOException;
import java.util.List;

import com.vedeng.trader.service.TraderCustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.trader.model.TraderCustomerAttributeCategory;
import com.vedeng.trader.model.TraderCustomerCategory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring.xml","classpath:spring-mybatis.xml"})
public class TestTrader {
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TestCompany.class);
	
	 @Value("${http_url}")  
	 protected String httpUrl;

	 @Autowired
	 private TraderCustomerService traderCustomerService;

	 @Test
	 public void getCustomerBussinessInfo(){
	 	traderCustomerService.getCustomerBussinessInfo(123);
	 }


	@Test
	public void httpPostData(){
		String url = httpUrl + "trader/gettradercustomercategory.htm";
		TraderCustomerCategory cate = new TraderCustomerCategory();
		cate.setTraderCustomerCategoryId(1);
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<TraderCustomerCategory>>> TypeRef2 = new TypeReference<ResultInfo<List<TraderCustomerCategory>>>() {};
		try {
			ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, cate,"123","123", TypeRef2);
			List<TraderCustomerCategory> sysOptionList = (List<TraderCustomerCategory>) result2.getData();
			
			for(TraderCustomerCategory s : sysOptionList){
				logger.info(JSON.toJSONString(s));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void httpPostData2(){
		String url = httpUrl + "trader/gettradercustomerattribute.htm";
		TraderCustomerAttributeCategory cate = new TraderCustomerAttributeCategory();
		cate.setTraderCustomerCategoryId(3);
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<TraderCustomerAttributeCategory>>> TypeRef2 = new TypeReference<ResultInfo<List<TraderCustomerAttributeCategory>>>() {};
		try {
			ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, cate,"123","123", TypeRef2);
			List<TraderCustomerAttributeCategory> sysOptionList = (List<TraderCustomerAttributeCategory>) result2.getData();
			
			for(TraderCustomerAttributeCategory s : sysOptionList){
				logger.info(JSON.toJSONString(s));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
