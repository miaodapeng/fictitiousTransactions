package com.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.vedeng.authorization.model.Company;
import com.vedeng.system.service.CompanyService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring.xml","classpath:spring-mybatis.xml"})
public class TestCompany {
	private static final Logger logger = LoggerFactory.getLogger(TestCompany.class);
	private CompanyService companyService;
	
	@Test
	public void testGetAll(){
//		List<Company> companys = companyService.selectAll();
//		logger.info(JSON.toJSON(companys));
	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	@Autowired
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}
	@Test
	public void testTxt(){
		StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader("E://abc.txt"));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                result.append(System.lineSeparator()+s.trim());
            }
            br.close();
            System.out.println(result.toString().trim());
            System.out.println(result.toString().getBytes("utf-8").length);
            System.out.println(result.toString().getBytes("GBK").length);
            System.out.println(result.toString().getBytes().length);
            System.out.println(byteArrayToInt(result.toString().getBytes()));
            int length = Integer.parseInt(new String(result.toString().getBytes()));
        }catch(Exception e){
            e.printStackTrace();
        }
	}
	
	public int byteArrayToInt(byte[] b){
	    return b[3]&0xFF | (b[2]&0xFF) << 8 | (b[1]&0xFF) << 16 | (b[0]&0xFF) << 24; 
	}
}
