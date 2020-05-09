package com.vedeng.activiti.taskassign;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;

import com.vedeng.common.putHCutil.service.HcSaleorderService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.vedeng.activiti.service.ActionProcdefService;
import com.vedeng.aftersales.model.vo.AfterSalesVo;
import com.vedeng.aftersales.service.AfterSalesService;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.util.DateUtil;
import com.vedeng.finance.model.CapitalBill;
import com.vedeng.finance.model.CapitalBillDetail;
import com.vedeng.finance.model.TraderAccountPeriodApply;
import com.vedeng.finance.service.CapitalBillService;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.model.SaleorderModifyApply;
import com.vedeng.order.service.SaleorderService;
import com.vedeng.soap.service.VedengSoapService;
import com.vedeng.system.service.UserService;

public class EditSaleorderExecutionListener implements ExecutionListener {
    // 运行时注入service
    WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
    private VedengSoapService vedengSoapService = (VedengSoapService) context.getBean("vedengSoapService");
    private ActionProcdefService actionProcdefService = (ActionProcdefService) context.getBean("actionProcdefService");
    private SaleorderService saleorderService = (SaleorderService) context.getBean("saleorderService");
    private CapitalBillService capitalBillService = (CapitalBillService) context.getBean("capitalBillService");
    private HcSaleorderService hcSaleorderService = (HcSaleorderService) context.getBean("hcSaleorderService");
    private UserService userService = (UserService) context.getBean("userService");
    @Resource
    private WebServiceContext webServiceContext;
    //订单审核触发器
    //根据穿参通用回写主表中状态
    public void notify(DelegateExecution execution) throws Exception {
		ServletRequestAttributes ra= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request =  ra.getRequest();
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		Saleorder saleorderInfo = (Saleorder) execution.getVariable("saleorderInfo");
        /**
         * 如果为非优惠券订单，推送到医械购前台
         */
        if (saleorderInfo.getOrderType() == 5 && saleorderInfo.getIsCoupons() == 0){
            HashMap<String, Object> hcOrder = new HashMap<>();
            hcOrder.put("saleOrder",saleorderInfo);
            hcSaleorderService.putOrderPricetoHC(hcOrder);
        }
		if(saleorderInfo.getOrderType() == 3 ){
		     vedengSoapService.orderSync(saleorderInfo.getSaleorderId());
		}
		if(saleorderInfo.getOrderType() == 0  || saleorderInfo.getOrderType()== 4){
		     vedengSoapService.orderSyncWeb(saleorderInfo.getSaleorderId());
		     if(saleorderInfo.getOrderType() == 0){
		    	 //订单推web调用消息
		    	 vedengSoapService.messageSyncWeb(2, saleorderInfo.getSaleorderId(), 1);
		     }
		}
		
		actionProcdefService.updateInfo("T_SALEORDER", "SALEORDER_ID", saleorderInfo.getSaleorderId(), "LOCKED_STATUS", 0,2);
		
		// 归属销售
	    User belongUser = new User();
	    if(saleorderInfo.getTraderId() != null ){
    		belongUser = userService.getUserInfoByTraderId(saleorderInfo.getTraderId(), 1);// 1客户，2供应商
    		/*if(belongUser != null && belongUser.getUserId() != null){
    		    belongUser = userService.getUserById(belongUser.getUserId());
    		}*/
	    }
	    
		//如果预付款金额等于0，添加触发账期流水
		if(saleorderInfo.getPrepaidAmount().compareTo(BigDecimal.ZERO) == 0){
		    Saleorder saleorder = new Saleorder();
		    saleorder.setSaleorderId(saleorderInfo.getSaleorderId());
		    if(saleorderInfo.getRetainageAmount().compareTo(BigDecimal.ZERO) == 0){
			//如果尾款等于0.付款状态为全部付款
			saleorder.setPaymentStatus(2);
		    }else{
			//如果尾款不等于0.付款状态为部分付款
			saleorder.setPaymentStatus(1);
		    }
		    saleorder.setAccountPeriodAmount(saleorderInfo.getAccountPeriodAmount());
		    saleorder.setSatisfyDeliveryTime(DateUtil.sysTimeMillis());
		    saleorder.setPaymentTime(DateUtil.sysTimeMillis());
		    //添加啊流水
		    
		    
		    CapitalBill capitalBill = new CapitalBill();
		    capitalBill.setCompanyId(user.getCompanyId());
		    //信用支付
		    capitalBill.setTraderMode(527);
		    capitalBill.setCurrencyUnitId(1);
		    capitalBill.setTraderTime(DateUtil.sysTimeMillis());
		    //交易类型 转移
		    capitalBill.setTraderType(3);
		    capitalBill.setPayer(saleorderInfo.getTraderName());
		    capitalBill.setPayee(user.getCompanyName());

		    List<CapitalBillDetail> capitalBillDetails = new ArrayList<>();
		    CapitalBillDetail capitalBillDetail = new CapitalBillDetail();
		    //订单类型   销售订单
		    capitalBillDetail.setOrderType(1);
		    capitalBillDetail.setOrderNo(saleorderInfo.getSaleorderNo());
		    capitalBillDetail.setRelatedId(saleorderInfo.getSaleorderId());
		    //所属类型  经销商（包含终端）
		    capitalBillDetail.setTraderType(1);
		    capitalBillDetail.setTraderId(saleorderInfo.getTraderId());
		    capitalBillDetail.setUserId(saleorderInfo.getUserId());
		    //业务类型  订单收款
		    capitalBillDetail.setBussinessType(526);
		    capitalBillDetail.setAmount(saleorderInfo.getAccountPeriodAmount());
		    if(belongUser != null && belongUser.getOrgName() != null && belongUser.getOrgId() != null){
				capitalBillDetail.setOrgName(belongUser.getOrgName());
				capitalBillDetail.setOrgId(belongUser.getOrgId());
		    }
		    capitalBillDetails.add(capitalBillDetail);
		    
		    capitalBill.setCapitalBillDetails(capitalBillDetails);
		    CapitalBillDetail capitalBillDetailInfo = new CapitalBillDetail();
		    capitalBillDetailInfo.setOrderType(1);
		    capitalBillDetailInfo.setOrderNo(saleorderInfo.getSaleorderNo());
		    capitalBillDetailInfo.setRelatedId(saleorderInfo.getSaleorderId());
		    capitalBillDetailInfo.setTraderType(1);
		    capitalBillDetailInfo.setTraderId(saleorderInfo.getTraderId());
		    capitalBillDetailInfo.setUserId(saleorderInfo.getUserId());
		  //业务类型  订单收款
		    capitalBillDetailInfo.setBussinessType(526);
		    capitalBillDetailInfo.setAmount(saleorderInfo.getAccountPeriodAmount());
		    if(belongUser != null && belongUser.getOrgName() != null && belongUser.getOrgId() != null){
				capitalBillDetailInfo.setOrgName(belongUser.getOrgName());
				capitalBillDetailInfo.setOrgId(belongUser.getOrgId());
		    }
		    capitalBill.setCapitalBillDetail(capitalBillDetailInfo);
		    //添加当前登陆人
		    capitalBill.setCreator(user.getUserId());
		    saleorderService.saveEditSaleorderInfo(saleorder, request, request.getSession());
		    saleorderService.updateSaleGoodsByAllSpecialGoods(saleorderInfo.getSaleorderId());
			capitalBillService.saveCapitalBill(capitalBill);
		}
		
		//销售订单生效更新（VALID_ORG_ID，VALID_ORG_NAME，VALID_USER_ID）
		Saleorder validSaleorder = new Saleorder();
		validSaleorder.setSaleorderId(saleorderInfo.getSaleorderId());
		if(belongUser != null && belongUser.getUserId() != null){
			validSaleorder.setValidUserId(belongUser.getUserId());
		}
		if(belongUser != null && belongUser.getOrgId() != null){
			validSaleorder.setValidOrgId(belongUser.getOrgId());
		}
		if(belongUser != null && belongUser.getOrgName() != null){
			validSaleorder.setValidOrgName(belongUser.getOrgName());
		}
		saleorderService.saveEditSaleorderInfo(validSaleorder, request, request.getSession());
    }
}

