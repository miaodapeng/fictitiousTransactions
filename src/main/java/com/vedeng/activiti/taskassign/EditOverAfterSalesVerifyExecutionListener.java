package com.vedeng.activiti.taskassign;

import com.common.constants.Contant;
import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.aftersales.model.vo.AfterSalesGoodsVo;
import com.vedeng.aftersales.model.vo.AfterSalesVo;
import com.vedeng.aftersales.service.AfterSalesService;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.service.BaseService;
import net.sf.json.JSONObject;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditOverAfterSalesVerifyExecutionListener implements ExecutionListener {
    // 运行时注入service
    WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
    private AfterSalesService afterSalesOrderService = (AfterSalesService) context.getBean("afterSalesOrderService");
    private BaseService baseService = (BaseService) context.getBean("baseService");
	public static Logger logger = LoggerFactory.getLogger(EditOverAfterSalesVerifyExecutionListener.class);

    @Resource
    private WebServiceContext webServiceContext;
    //修改售后单结束触发器
    //根据穿参通用回写主表中状态
    public void notify(DelegateExecution execution) throws Exception {
		ServletRequestAttributes ra= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request =  ra.getRequest();
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		AfterSalesVo afterSalesInfo = (AfterSalesVo) execution.getVariable("afterSalesVo");

		if(afterSalesInfo.getVerifiesType() != null && afterSalesInfo.getVerifiesType() == 0){
		    //售后单关闭的触发器
		    user.setUserId(afterSalesInfo.getAfterSalesStatusUser());
		    ResultInfo<?> res = afterSalesOrderService.saveCloseAfterSales(afterSalesInfo, user);

			/****** 2018-12-12 Barry 审核流程-审核完成-判断是耗材订单，推送售后金额给耗材数据库 ******/
			afterSalesInfo.setTraderType(1);
			AfterSalesVo afterSales = afterSalesOrderService.getAfterSalesVoDetail(afterSalesInfo);

			//判断是否是耗材订单
			if(afterSales.getSource() == 1){

				BigDecimal total = new BigDecimal(0);
				if (afterSales.getAfterSalesGoodsList().size()>0){
					List<AfterSalesGoodsVo> list =  afterSales.getAfterSalesGoodsList();
					for (AfterSalesGoodsVo goods : list){
						//判断是否是商品
						if (goods.getGoodsType() == 0){
							total = total.add((goods.getSaleorderPrice().multiply(new BigDecimal(goods.getNum()))));
						}
					}
				}

				Map<String, Object> param = new HashMap<String, Object>();
				//查询当前订单的售后列表
				List<AfterSalesVo> afterSalesList = afterSalesOrderService.getAfterSalesVoListByOrderId(afterSales);

				if (afterSalesList.size()>0){
					int num = 0;
					for (AfterSalesVo asv : afterSalesList){
						//如果有待确认和进行中的订单
						if (asv.getAtferSalesStatus() == 0 || asv.getAtferSalesStatus() == 1){
							num++;
						}
					}

					if (num>0){
						param.put("isRefund",1);
					}else {
						param.put("isRefund",0);
					}
				}else {
					param.put("isRefund",0);
				}

				// 请求头
				Map<String, String> header = new HashMap<String, String>();
				header.put("version", "v1");

				param.put("backMoney",total);
				param.put("orderNo",afterSales.getOrderNo());
				JSONObject jsonObject = JSONObject.fromObject(param);
				// 定义反序列化 数据格式
				String url = baseService.getApiUri() + "/order/after/colseBackMoney";

				try {
					final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
					ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.put(url, jsonObject.toString(), header, TypeRef);

				}catch (Exception e){
					logger.error(Contant.ERROR_MSG, e);
				}
			}

		}else if(afterSalesInfo.getVerifiesType() != null && afterSalesInfo.getVerifiesType() == 1){
		    //售后单完成的触发器
		    AfterSalesVo afterSales = (AfterSalesVo) execution.getVariable("afterSales");
		    ResultInfo<?> res = afterSalesOrderService.editApplyAudit(afterSales);

			/****** 2018-12-12 Barry 审核流程-审核完成-判断是耗材订单，推送售后金额给耗材数据库 ******/
			afterSalesInfo.setTraderType(1);
			AfterSalesVo afterSalesVo = afterSalesOrderService.getAfterSalesVoDetail(afterSalesInfo);
			//判断是否是耗材订单
			if(afterSalesVo.getSource() == 1){
				Map<String, Object> param = new HashMap<String, Object>();
				//查询当前订单的售后列表
				List<AfterSalesVo> afterSalesList = afterSalesOrderService.getAfterSalesVoListByOrderId(afterSalesVo);

				if (afterSalesList.size()>0){
					int num = 0;
					for (AfterSalesVo asv : afterSalesList){
						//如果有待确认和进行中的订单
						if (asv.getAtferSalesStatus() == 0 || asv.getAtferSalesStatus() == 1){
							num++;
						}
					}

					if (num>0){
						param.put("isRefund",1);
					}else {
						param.put("isRefund",0);
					}
				}else {
					param.put("isRefund",0);
				}

				// 请求头
				Map<String, String> header = new HashMap<String, String>();
				header.put("version", "v1");

				param.put("orderNo",afterSalesVo.getOrderNo());
				JSONObject jsonObject = JSONObject.fromObject(param);
				// 定义反序列化 数据格式
				String url = baseService.getApiUri() + "/order/after/completeBackMoney";
				try {
					final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
					ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.put(url, jsonObject.toString(), header, TypeRef);

				}catch (Exception e){
					logger.error(Contant.ERROR_MSG, e);
				}
			}

		}
    }
}
