package com.vedeng.activiti.taskassign;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;

import com.vedeng.common.util.ObjectUtils;
import com.vedeng.goods.dao.GoodsMapper;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jfree.util.Log;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.vedeng.activiti.service.ActionProcdefService;
import com.vedeng.activiti.service.ActivitiAssigneeService;
import com.vedeng.aftersales.model.vo.AfterSalesVo;
import com.vedeng.aftersales.service.AfterSalesService;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.MessageUtil;
import com.vedeng.finance.model.TraderAccountPeriodApply;
import com.vedeng.goods.service.CategoryService;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.model.SaleorderGoods;
import com.vedeng.order.model.SaleorderModifyApply;
import com.vedeng.order.service.SaleorderService;
import com.vedeng.system.service.UserService;

public class SaleorderSendMessageListener implements TaskListener {
    // 运行时注入service
    WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
    private SaleorderService saleorderService = (SaleorderService) context.getBean("saleorderService");
    private GoodsMapper goodsMapper=(GoodsMapper)context.getBean("goodsMapper");
    private ActionProcdefService actionProcdefService = (ActionProcdefService) context.getBean("actionProcdefService");
    
    private ActivitiAssigneeService activitiAssigneeService = (ActivitiAssigneeService) context.getBean("activitiAssigneeService");
    
    private UserService userService = (UserService) context.getBean("userService");
    
    private CategoryService categoryService = (CategoryService) context.getBean("categoryService");
    
    @Resource
    private WebServiceContext webServiceContext;

    //给销售订单对应的采购人员发送消息
    public void notify(DelegateTask delegateTask) {
		ServletRequestAttributes ra= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request =  ra.getRequest();
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		Integer orderId = (Integer) delegateTask.getVariable("orderId");
		// 申请人名称
		String preAssignee = (String) delegateTask.getVariable("currentAssinee");
		//如果存在销售订单id推消息给对应的采购
		if (orderId != null && orderId > 0) {
			//查询该订单下的产品哪些产品成本价未填写并记录对应的采购人员
			Saleorder sale = new Saleorder();
			sale.setSaleorderId(orderId);
			sale.setCompanyId(user.getCompanyId());
			List<SaleorderGoods> saleorderGoodsLists = saleorderService.getSaleorderGoodsById(sale);
			if (CollectionUtils.isNotEmpty(saleorderGoodsLists)) {
				List<Integer> userIds = null;
				List<Integer> goodsIds=new ArrayList<>();
                for(SaleorderGoods g:saleorderGoodsLists){
                	if(g==null||g.getGoodsId()==null||g.getReferenceCostPrice().compareTo(BigDecimal.ZERO)>0){
                		continue;
					}
                	goodsIds.add(g.getGoodsId());
				}
				if(CollectionUtils.isEmpty(goodsIds)){
					return;
				}
                userIds=goodsMapper.getAssignUserIdsByGoods(goodsIds);
				if (CollectionUtils.isNotEmpty(userIds)) {
					// 消息模板编号N0086
					Integer messageTemplateId = 86;
					Map<String, String> map = new HashMap<>();
					String url = null;
					if (null != delegateTask.getVariable("saleorderInfo")) {
						Saleorder saleorder = (Saleorder) delegateTask.getVariable("saleorderInfo");
						map.put("saleorderNo", saleorder.getSaleorderNo());
						url = "./order/saleorder/view.do?saleorderId=" + saleorder.getSaleorderId();
						try {
							MessageUtil.sendMessage(messageTemplateId, userIds, map, url, preAssignee);
						}catch (Exception ex){
							Log.error("订单:"+saleorder.getSaleorderNo()+"成本价填写发送消息失败:"+userIds.get(0));
						}
					}

				}


			}

		}
    }
}
/**
 * 
 * @author John
 *
 */
