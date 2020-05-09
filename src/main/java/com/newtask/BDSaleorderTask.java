package com.newtask;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.vedeng.common.constant.OrderConstant;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.controller.BaseSonContrller;
import com.vedeng.logistics.service.ExpressService;
import com.vedeng.passport.api.wechat.dto.req.ReqTemplateVariable;
import com.vedeng.passport.api.wechat.dto.template.TemplateVar;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.vedeng.common.http.NewHttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.order.dao.SaleorderMapper;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.model.vo.OrderData;
import com.vedeng.trader.dao.WebAccountMapper;
import com.vedeng.trader.model.WebAccount;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;

import net.sf.json.JSONObject;

import static com.vedeng.common.controller.BaseSonContrller.sendTemplateMsg;

/**
* @ClassName: BDSaleorderTask
* @Description: TODO(BD订单待用户确认订单定时任务)
* @author strange
* @date 2019年8月1日
*
*/

@Component
@JobHandler(value="BDSaleorderTask")
public class BDSaleorderTask extends IJobHandler{
	
	private Logger logger = LoggerFactory.getLogger(BDSaleorderTask.class);
	@Value("${mjx_url}")
	private String mjxUrl;
	
	@Autowired
	private SaleorderMapper saleorderMapper;
	@Autowired
	@Qualifier("expressService")
	protected ExpressService expressService;


	
	@Autowired
	private WebAccountMapper webAccountMapper;
	
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
		XxlJobLogger.log("saleorderlist++++"+saleorderlist);
		OrderData orderData = new OrderData();
		ResultInfo r = new ResultInfo();
		Integer i = 0;
		if(saleorderlist!=null) {
		for (Saleorder saleorder : saleorderlist) {
			t = time-saleorder.getBdMobileTime();
			//超过14天未确认关闭订单
			if(t>1209600000) {//1209600000
				saleorder.setStatus(3);
				saleorder.setCloseComments("客户超时未确认");
				saleorder.setModTime(time);
				 i+= saleorderMapper.updateByPrimaryKeySelective(saleorder);
				 if(i>0){
					 Map sTempMap = null;
					 ResultInfo senResult = expressService.sendWxMessageForArrival(saleorder.getSaleorderId());
					 if(null != senResult) {
						 sTempMap = (Map)senResult.getData();
						 // changed by Tomcat.Hui 2020/3/10 6:32 下午 .Desc: VDERP-2057 BD订单流程优化-ERP部分 超过14天的订单关闭未取消消息推送 .
                         //expressService.sendOrderConfirmedClose(saleorder,sTempMap);
						 // changed by Tomcat.Hui 2020/3/10 6:32 下午 .Desc: VDERP-2057 BD订单流程优化-ERP部分 超过14天的订单关闭未取消消息推送 .
					 }
				 }
//				r.setMessage(i.toString());
//				r.setCode(1);
				try {
					orderData.setCancelType(OrderConstant.CANCEL_BD_TIMEOUT);
					orderData.setOrderStatus(6);//订单关闭
					orderData.setOrderNo(saleorder.getSaleorderNo());
					WebAccount web =webAccountMapper.getWenAccountInfoByMobile(saleorder.getCreateMobile());
					orderData.setAccountId(web.getWebAccountId());
					List a =new ArrayList<>();
					orderData.setGoodsList(a);
					String url = mjxUrl+"/order/updateOrderConfirmFinish";
					XxlJobLogger.log("mjxurl++++",url);
					String json = JsonUtils.translateToJson(orderData);
					logger.info(json);
					XxlJobLogger.log("json++++",json);
					CloseableHttpClient httpClient = HttpClientBuilder.create().build();
					JSONObject result2 = NewHttpClientUtils.httpPost(url, JSONObject.fromObject(orderData));
					XxlJobLogger.log("result2+++++",result2);
					logger.info(result2.toString());
				} catch (Exception e) {
					XxlJobLogger.log("erro++++",e);
				}
			}
		}
		}
	    XxlJobLogger.log("WeChatSendOrder.sendWxTemplateForOrder | end ...............");
        logger.info("WeChatSendOrder.sendWxTemplateForOrder | end ...............");
        return r;
	}
	@Override
	public ReturnT<String> execute(String param) throws Exception {
		XxlJobLogger.log("XXL-JOB, Hello World.");
		UserValidationStatus();
		return SUCCESS;
	}



}
