package com.test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.vedeng.authorization.model.Organization;
import com.vedeng.system.service.OrgService;


public class TestOrg {

	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder("UpWork");
		sb.insert(6,"-");
		sb.insert(0,"-");
		sb.insert(4,"-");
		System.out.println(sb);
		System.out.println("acB2B".contains("B2B"));
		System.out.println("acB2Baa".contains("B2B"));
		System.out.println("B2Bcc".contains("B2B"));
		int x=10;
		int y = new TestOrg().abc(x);
		System.out.println(x+y);
		int g = 3;
		System.out.println(++g*8);
	}
	int abc(int x){
		x=12;
		return x;
	}
}
