package com.vedeng.activiti.taskassign;

import java.math.BigDecimal;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.vedeng.aftersales.model.vo.AfterSalesVo;
import com.vedeng.aftersales.service.AfterSalesService;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.util.DateUtil;
import com.vedeng.finance.model.TraderAccountPeriodApply;
import com.vedeng.goods.model.Goods;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.model.SaleorderModifyApply;
import com.vedeng.order.service.SaleorderService;
import com.vedeng.soap.service.VedengSoapService;

public class EditGoodsExecutionListener implements ExecutionListener {
    // 运行时注入service
    WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
    private VedengSoapService vedengSoapService = (VedengSoapService) context.getBean("vedengSoapService");
    @Resource
    private WebServiceContext webServiceContext;
    //产品审核触发器
    //根据穿参通用回写主表中状态
    public void notify(DelegateExecution execution) throws Exception {
		ServletRequestAttributes ra= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request =  ra.getRequest();
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		Goods goods = (Goods) execution.getVariable("goods");
		ResultInfo<?> res = vedengSoapService.goodsSync(goods.getGoodsId());
    }
}
/**
 * 
 * @author John
 *
 */
