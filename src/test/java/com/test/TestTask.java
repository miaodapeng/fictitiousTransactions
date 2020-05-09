package com.test;

import java.util.List;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.newtask.BDSaleorderTask;
import com.vedeng.common.http.NewHttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.order.dao.SaleorderMapper;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.model.vo.OrderData;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.log.XxlJobLogger;

import net.sf.json.JSONObject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring.xml","classpath:spring-mybatis.xml"})
public class TestTask {

	private Logger logger = LoggerFactory.getLogger(BDSaleorderTask.class);
	
	public SaleorderMapper getSaleorderMapper() {
		return saleorderMapper;
	}
	@Autowired
	public void setSaleorderMapper(SaleorderMapper saleorderMapper) {
		this.saleorderMapper = saleorderMapper;
	}
	
	private SaleorderMapper saleorderMapper;
	@Test
	public ResultInfo UserValidationStatus() {
		XxlJobLogger.log("BDSaleorderTask.UserValidationStatus | begin ...............");
		logger.info("BDSaleorderTask.UserValidationStatus | begin ...............");
		// 查询订单状态为4订单类型为1的订单
		Integer orderType =1;
		Integer status =4 ;
		List<Saleorder> saleorderlist = saleorderMapper.getSaleorderListByStatus(status,orderType);
		Long time = DateUtil.sysTimeMillis();
		logger.info("BDSaleorderTask.UserValidationStatus | num:{} ...............");
		Long t = null;
		OrderData orderData = new OrderData();
		ResultInfo r = new ResultInfo();
		Integer i = 0;
		for (Saleorder saleorder : saleorderlist) {
			t = time-Long.valueOf(saleorder.getModel());
			//超过14天未确认关闭订单
			if(t>10000) {//1209600000
				saleorder.setStatus(3);
				saleorder.setCloseComments("客户超时未确认");
				saleorder.setModTime(time);
				 i+= saleorderMapper.updateByPrimaryKeySelective(saleorder);
				r.setMessage(i.toString());
				r.setCode(1);
				try {
					orderData.setOrderStatus(6);//订单关闭
					orderData.setOrderNo(saleorder.getSaleorderNo());
					String url = "http://172.17.0.188:8280/order/updateOrderConfirmFinish";
					String json = JsonUtils.translateToJson(orderData);
					logger.info(json);
					CloseableHttpClient httpClient = HttpClientBuilder.create().build();
					JSONObject result2 = NewHttpClientUtils.httpPost(url, JSONObject.fromObject(orderData));
					logger.info(result2.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	    XxlJobLogger.log("WeChatSendOrder.sendWxTemplateForOrder | end ...............");
        logger.info("WeChatSendOrder.sendWxTemplateForOrder | end ...............");
        return r;
	}
}
