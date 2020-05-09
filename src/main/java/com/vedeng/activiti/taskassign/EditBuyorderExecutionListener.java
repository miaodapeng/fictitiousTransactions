package com.vedeng.activiti.taskassign;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;

import com.vedeng.common.constant.OrderDataUpdateConstant;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.util.DateUtil;
import com.vedeng.finance.model.CapitalBill;
import com.vedeng.finance.model.CapitalBillDetail;
import com.vedeng.finance.service.CapitalBillService;

import com.vedeng.order.model.vo.BuyorderVo;
import com.vedeng.order.service.BuyorderService;

import com.vedeng.system.service.UserService;

public class EditBuyorderExecutionListener implements ExecutionListener {

	Logger logger= LoggerFactory.getLogger(EditBuyorderExecutionListener.class);
    // 运行时注入service
    WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
    private BuyorderService buyorderService = (BuyorderService) context.getBean("buyorderService");
    private CapitalBillService capitalBillService = (CapitalBillService) context.getBean("capitalBillService");
    private UserService userService = (UserService) context.getBean("userService");
    @Resource
    private WebServiceContext webServiceContext;
    //采购订单审核触发器
    //根据穿参通用回写主表中状态
    public void notify(DelegateExecution execution) throws Exception {
    	logger.info("准备设置采购订单的状态");
//		ServletRequestAttributes ra= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//		HttpServletRequest request =  ra.getRequest();

		BuyorderVo buyorderInfo = (BuyorderVo) execution.getVariable("buyorderInfo");
		//如果预付款金额等于0，添加触发账期流水
		if(buyorderInfo.getPrepaidAmount().compareTo(BigDecimal.ZERO) == 0){
			User user = getUser();
		    BuyorderVo buyorder = new BuyorderVo();
		    buyorder.setBuyorderId(buyorderInfo.getBuyorderId());
		    if(buyorderInfo.getRetainageAmount().compareTo(BigDecimal.ZERO) == 0){
			//如果尾款等于0.付款状态为全部付款
			buyorder.setPaymentStatus(2);
		    }else{
			//如果尾款不等于0.付款状态为部分付款
			buyorder.setPaymentStatus(1);
		    }
		    buyorder.setPaymentTime(DateUtil.sysTimeMillis());
		    buyorder.setSatisfyDeliveryTime(DateUtil.sysTimeMillis());
		    //添加啊流水
		    // 归属销售
        	    User belongUser = new User();
        	    if(buyorderInfo.getTraderId() != null ){
        		belongUser = userService.getUserByTraderId(buyorderInfo.getTraderId(), 2);// 1客户，2供应商
        		if(belongUser != null && belongUser.getUserId() != null){
        		    belongUser = userService.getUserById(belongUser.getUserId());
        		}
        	    }
		    CapitalBill capitalBill = new CapitalBill();
		    capitalBill.setCompanyId(user.getCompanyId());
		    //信用支付
		    capitalBill.setTraderMode(527);
		    capitalBill.setCurrencyUnitId(1);
		    capitalBill.setTraderTime(DateUtil.sysTimeMillis());
		    //交易类型 转移
		    capitalBill.setTraderType(3);
		    capitalBill.setPayee(buyorderInfo.getTraderName());
		    capitalBill.setPayer(user.getCompanyName());

		    List<CapitalBillDetail> capitalBillDetails = new ArrayList<>();
		    CapitalBillDetail capitalBillDetail = new CapitalBillDetail();
		    //订单类型   采购订单
		    capitalBillDetail.setOrderType(2);
		    capitalBillDetail.setOrderNo(buyorderInfo.getBuyorderNo());
		    capitalBillDetail.setRelatedId(buyorderInfo.getBuyorderId());
		    //所属类型  经销商（包含终端）
		    capitalBillDetail.setTraderType(2);
		    capitalBillDetail.setTraderId(buyorderInfo.getTraderId());
		    capitalBillDetail.setUserId(buyorderInfo.getUserId());
		    //业务类型  订单收款
		    capitalBillDetail.setBussinessType(525);
		    capitalBillDetail.setAmount(buyorderInfo.getAccountPeriodAmount());
		    if(belongUser != null && belongUser.getOrgName() != null && belongUser.getOrgId() != null){
			capitalBillDetail.setOrgName(belongUser.getOrgName());
			capitalBillDetail.setOrgId(belongUser.getOrgId());
		    }
		    capitalBillDetails.add(capitalBillDetail);
		    
		    capitalBill.setCapitalBillDetails(capitalBillDetails);
		    CapitalBillDetail capitalBillDetailInfo = new CapitalBillDetail();
		    capitalBillDetailInfo.setOrderType(2);
		    capitalBillDetailInfo.setOrderNo(buyorderInfo.getBuyorderNo());
		    capitalBillDetailInfo.setRelatedId(buyorderInfo.getBuyorderId());
		    capitalBillDetailInfo.setTraderType(2);
		    capitalBillDetailInfo.setTraderId(buyorderInfo.getTraderId());
		    capitalBillDetailInfo.setUserId(buyorderInfo.getUserId());
		    //业务类型  订单收款
		    capitalBillDetailInfo.setBussinessType(525);
		    capitalBillDetailInfo.setAmount(buyorderInfo.getAccountPeriodAmount());
		    if(belongUser != null && belongUser.getOrgName() != null && belongUser.getOrgId() != null){
			capitalBillDetailInfo.setOrgName(belongUser.getOrgName());
			capitalBillDetailInfo.setOrgId(belongUser.getOrgId());
		    }
		    capitalBill.setCapitalBillDetail(capitalBillDetailInfo);
		    //添加当前登陆人
		    capitalBill.setCreator(user.getUserId());
		    capitalBillService.saveCapitalBill(capitalBill);
		    buyorder.setStatus(1);
			logger.info("准备设置采购订单的状态"+buyorderInfo.getBuyorderId());
		    buyorderService.saveBuyorder(buyorder);
		    //更新采购单updataTime
		    buyorderService.updateBuyOrderDataUpdateTime(buyorderInfo.getBuyorderId(),null, OrderDataUpdateConstant.BUY_ORDER_VAILD);
		}else{
		    BuyorderVo buyorder = new BuyorderVo();
		    buyorder.setBuyorderId(buyorderInfo.getBuyorderId());
		    buyorder.setStatus(1);
			logger.info("准备设置采购订单的状态"+buyorderInfo.getBuyorderId());
		    buyorderService.saveBuyorder(buyorder);
			//更新采购单updataTime
			buyorderService.updateBuyOrderDataUpdateTime(buyorderInfo.getBuyorderId(),null,OrderDataUpdateConstant.BUY_ORDER_VAILD);
		}
    }

    private User getUser(){
		User user = null;
		ServletRequestAttributes ra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (ra != null) {
			HttpServletRequest request = ra.getRequest();
			if (request != null && request.getSession() != null) {
				user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
			}
		}
		if(user==null){
			user= new User();
			user.setCompanyId(1);
			user.setUserId(2);
			user.setCompanyName("南京贝登医疗有限公司");
			user.setUsername("njadmin");
		}
		return user;
	}
}
/**
 * 
 * @author John
 *
 */
