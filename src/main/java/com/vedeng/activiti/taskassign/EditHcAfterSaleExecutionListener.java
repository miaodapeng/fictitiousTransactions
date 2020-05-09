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
import com.vedeng.common.service.impl.BaseServiceimpl;
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

public class EditHcAfterSaleExecutionListener extends BaseServiceimpl implements ExecutionListener {
	public static Logger logger = LoggerFactory.getLogger(EditHcAfterSaleExecutionListener.class);

    // 运行时注入service
    WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
    private AfterSalesService afterSalesOrderService = (AfterSalesService) context.getBean("afterSalesOrderService");
    private BaseService baseService = (BaseService) context.getBean("baseService");
    @Resource
    private WebServiceContext webServiceContext;


    //售后审核触发器
    //根据穿参通用回写主表中状态
    public void notify(DelegateExecution execution) throws Exception {
		ServletRequestAttributes ra= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request =  ra.getRequest();
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		AfterSalesVo afterSales = (AfterSalesVo) execution.getVariable("afterSalesInfo");

		/****** 2018-12-12 Barry 审核流程-审核完成-判断是耗材订单，推送售后金额给耗材数据库 ******/
		//判断是否是耗材订单
		if(null != afterSales && null != afterSales.getSource() && afterSales.getSource() == 1){

			BigDecimal total = new BigDecimal(0);
			if (afterSales.getAfterSalesGoodsList().size()>0){
				List<AfterSalesGoodsVo>list =  afterSales.getAfterSalesGoodsList();
				for (AfterSalesGoodsVo goods : list){
					//判断是否是商品
					if (goods.getGoodsType() == 0){
						total = total.add((goods.getSaleorderPrice().multiply(new BigDecimal(goods.getNum()))));
					}
				}
			}
			// 请求头
			Map<String, String> header = new HashMap<String, String>();
			header.put("version", "v1");

			Map<String, Object> param = new HashMap<String, Object>();
			param.put("backMoney",total);
			param.put("orderNo",afterSales.getOrderNo());
			JSONObject jsonObject = JSONObject.fromObject(param);
			// 定义反序列化 数据格式
			String url = baseService.getApiUri() + "/order/after/updateBackMoney";
			try {
				final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
				ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.put(url, jsonObject.toString(), header, TypeRef);

			}catch (Exception e){
				logger.error(Contant.ERROR_MSG, e);
			}
		}

    }
}
