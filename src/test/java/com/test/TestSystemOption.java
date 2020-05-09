package com.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.authorization.model.User;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.system.model.SysOptionDefinition;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring.xml","classpath:spring-mybatis.xml"})
public class TestSystemOption {
	private static final Logger logger = LoggerFactory.getLogger(TestCompany.class);
	 @Value("${http_url}")  
	 protected String httpUrl;
	
	@Test
	public void httpPostData(){
		SysOptionDefinition sysOption = new SysOptionDefinition();
		sysOption.setScope(1001);
		
		String url = httpUrl + "sysoptiondefinition/getoptionbyscope.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<SysOptionDefinition>>> TypeRef2 = new TypeReference<ResultInfo<List<SysOptionDefinition>>>() {};
		try {
			ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, sysOption,"123","123", TypeRef2);
			List<SysOptionDefinition> sysOptionList = (List<SysOptionDefinition>) result2.getData();
			
			for(SysOptionDefinition s : sysOptionList){
				logger.info(JSON.toJSONString(s));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void httpPostData2(){
		List<Integer> scope = new ArrayList<Integer>();
		scope.add(1001);
		scope.add(1002);
		
		String url = httpUrl + "sysoptiondefinition/getoptionbyscopelist.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Map<Integer, List<SysOptionDefinition>>>> TypeRef2 = new TypeReference<ResultInfo<Map<Integer, List<SysOptionDefinition>>>>() {};
		try {
			ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, scope,"nanjing","365468123896", TypeRef2);
			Map<Integer,List<SysOptionDefinition>> sysOptionList = (Map<Integer, List<SysOptionDefinition>>) result2.getData();
			logger.info(JSON.toJSONString(sysOptionList.get(1001)));
			logger.info(JSON.toJSONString(sysOptionList.get(1001)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
