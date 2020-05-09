package com.vedeng.activiti.taskassign;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.task.IdentityLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.vedeng.activiti.service.ActionProcdefService;
import com.vedeng.aftersales.model.vo.AfterSalesVo;
import com.vedeng.authorization.model.Position;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.util.MessageUtil;
import com.vedeng.finance.model.InvoiceApply;
import com.vedeng.finance.model.PayApply;
import com.vedeng.finance.model.TraderAccountPeriodApply;
import com.vedeng.goods.model.Goods;
import com.vedeng.logistics.model.FileDelivery;
import com.vedeng.order.model.BussinessChance;
import com.vedeng.order.model.Quoteorder;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.model.SaleorderModifyApply;
import com.vedeng.order.model.vo.BuyorderModifyApplyVo;
import com.vedeng.order.model.vo.BuyorderVo;
import com.vedeng.system.service.UserService;
import com.vedeng.trader.model.Trader;
import com.vedeng.trader.model.vo.TraderCustomerVo;
import com.vedeng.trader.model.vo.TraderSupplierVo;

public class TaskMessageListener implements TaskListener {
    private static final long serialVersionUID = 1L;

    private FixedValue myName; // 监听注入属性
    private FixedValue dynamicName; // 监听注入属性
    private FixedValue dynamicName2; // 监听注入属性

    // 运行时注入service
    WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
    private UserService userService = (UserService) context.getBean("userService");

    private ActionProcdefService actionProcdefService = (ActionProcdefService) context.getBean("actionProcdefService");

    @Autowired // 自动装载
    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    public void notify(DelegateTask delegateTask) {
        ServletRequestAttributes ra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ra.getRequest();
        // 获取订单审核信息
        TaskService taskService = processEngine.getTaskService(); // 任务相关service
        User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        // 申请人名称
        String preAssignee = delegateTask.getVariable("currentAssinee").toString();
        User assigneeInfo = userService.getByUsername(preAssignee, user.getCompanyId());
        HistoryService historyService = processEngine.getHistoryService(); // 任务相关service
        String processInstanceId = delegateTask.getProcessInstanceId();
        HistoricProcessInstance historicProcessInstance =
                historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        String processDefinitionKey = historicProcessInstance.getProcessDefinitionKey();

        Map<String, Object> variables = delegateTask.getVariables();
        // 获取当前审核候选人
        List<IdentityLink> candidateUserList = taskService.getIdentityLinksForTask(delegateTask.getId());

        List<Integer> varifyUserList = new ArrayList<>();
        // 如果审核人候选组不为空的时候
        if (!candidateUserList.isEmpty()) {
            for (IdentityLink il : candidateUserList) {
                User userInfo = userService.getByUsername(il.getUserId(), user.getCompanyId());
                if (null != userInfo && null != userInfo.getUserId()) {
                    varifyUserList.add(userInfo.getUserId());
                }
            }
        } else {
            if (null != delegateTask && null != delegateTask.getAssignee() && delegateTask.getAssignee() != "") {
                User userInfo = userService.getByUsername(delegateTask.getAssignee(), user.getCompanyId());
                if (null != userInfo && null != userInfo.getUserId()) {
                    varifyUserList.add(userInfo.getUserId());
                }
            }
        }

        // 准备发送参数
        // 模板ID
        Integer messageTemplateId = null;
        Map<String, String> map = new HashMap<>();
        String url = null;
        if (processDefinitionKey.equals("traderCustomerVerify")) {
            // 消息模板编号N001
            messageTemplateId = 1;
            if (null != variables.get("traderCustomerVo")) {
                TraderCustomerVo traderCustomerVo = (TraderCustomerVo) variables.get("traderCustomerVo");
                map.put("traderName", traderCustomerVo.getTrader().getTraderName());
                url = "./trader/customer/baseinfo.do?traderId=" + traderCustomerVo.getTraderId();
            }

        } else if (processDefinitionKey.equals("traderSupplierVerify")) {
            // 消息模板编号N061
            messageTemplateId = 61;
            if (null != variables.get("traderSupplierVo")) {
                TraderSupplierVo traderSupplierVo = (TraderSupplierVo) variables.get("traderSupplierVo");
                map.put("traderName", traderSupplierVo.getTrader().getTraderName());
                url = "./trader/supplier/baseinfo.do?traderId=" + traderSupplierVo.getTrader().getTraderId();
            }

        } else if (processDefinitionKey.equals("quoteVerify")) {
            Integer type = null;
            // 消息模板编号N003
            messageTemplateId = 3;
            type = 2;

            if (null != variables.get("quoteorder")) {
                Quoteorder quoteorder = (Quoteorder) variables.get("quoteorder");
                map.put("quoteorderNo", quoteorder.getQuoteorderNo());
                url = "./order/quote/getQuoteDetail.do?quoteorderId=" + quoteorder.getQuoteorderId() + "&viewType="
                        + type;
            }
        } else if (processDefinitionKey.equals("saleorderVerify")) {
            // 消息模板编号N006
            messageTemplateId = 6;
            if (null != variables.get("saleorderInfo")) {
                Saleorder saleorder = (Saleorder) variables.get("saleorderInfo");
                map.put("saleorderNo", saleorder.getSaleorderNo());
                url = "./order/saleorder/view.do?saleorderId=" + saleorder.getSaleorderId();
                // 2019-02-25 耗材订单跳转不同duke
                if(saleorder.getOrderType() != null && "5".equals(saleorder.getOrderType().toString())) {
                	url = "./order/hc/hcOrderDetailsPage.do?saleorderId=" + saleorder.getSaleorderId();
                }
            }
        } else if (processDefinitionKey.equals("goodsVerify")) {
            // 消息模板编号N012
            messageTemplateId = 12;
            if (null != variables.get("goods")) {
                Goods goods = (Goods) variables.get("goods");
                map.put("sku", goods.getSku());
                url = "./goods/goods/viewbaseinfo.do?goodsId=" + goods.getGoodsId();
            }
        } else if (processDefinitionKey.equals("customerPeriodVerify")) {
            if (null != variables.get("traderType")) {
                TraderAccountPeriodApply traderAccountPeriodApply =
                        (TraderAccountPeriodApply) variables.get("traderAccountPeriodApply");
                map.put("traderName", traderAccountPeriodApply.getTraderName());
                if (variables.get("traderType").equals(1)) {
                    // 消息模板编号N044
                    messageTemplateId = 44;
                    url = "./finance/accountperiod/getAccountPeriodApply.do?traderAccountPeriodApplyId="
                            + traderAccountPeriodApply.getTraderAccountPeriodApplyId();

                } else {
                    // 消息模板编号N044
                    messageTemplateId = 44;
                    url = "./finance/accountperiod/getAccountPeriodApply.do?traderAccountPeriodApplyId="
                            + traderAccountPeriodApply.getTraderAccountPeriodApplyId();
                }

            }

        } else if (processDefinitionKey.equals("afterSalesVerify")) {
            // 消息模板编号N027
            messageTemplateId = 27;
            if (null != variables.get("afterSalesInfo")) {
                AfterSalesVo afterSalesVo = (AfterSalesVo) variables.get("afterSalesInfo");
                map.put("afterSalesNo", afterSalesVo.getAfterSalesNo());
                List<User> userList = userService.getUserByUserIds(varifyUserList);
                Integer type = 0;
                for (User u : userList) {
                    for (Position p : u.getPositions()) {
                        type = p.getType();
                    }
                }
                // 发送人是销售
                if (type == 310) {
                    url = "./order/saleorder/viewAfterSalesDetail.do?afterSalesId=" + afterSalesVo.getAfterSalesId();
                    // 发送人是采购
                } else if (type == 311) {
                    url = "./aftersales/order/viewAfterSalesDetail.do?afterSalesId=" + afterSalesVo.getAfterSalesId()
                            + "&traderType=2";
                } else {
                    // 类型是销售
                    if (afterSalesVo.getSubjectType() == 535) {
                        url = "./aftersales/order/viewAfterSalesDetail.do?afterSalesId="
                                + afterSalesVo.getAfterSalesId() + "&traderType=1";
                        // 类型是采购
                    } else if (afterSalesVo.getSubjectType() == 536) {
                        url = "./aftersales/order/viewAfterSalesDetail.do?afterSalesId="
                                + afterSalesVo.getAfterSalesId() + "&traderType=2";
                    } else {
                        url = "./aftersales/order/viewAfterSalesDetail.do?afterSalesId="
                                + afterSalesVo.getAfterSalesId();
                    }
                }
            }
        } else if (processDefinitionKey.equals("bhSaleorderVerify")) {
            // 消息模板编号N035
            messageTemplateId = 35;
            if (null != variables.get("saleorderInfo")) {
                Saleorder saleorder = (Saleorder) variables.get("saleorderInfo");
                map.put("saleorderNo", saleorder.getSaleorderNo());
                url = "./order/saleorder/viewBhSaleorder.do?saleorderId=" + saleorder.getSaleorderId();
            }
        } else if (processDefinitionKey.equals("closeQuoteorderVerify")) {
            // 消息模板编号N045
            messageTemplateId = 45;
            if (null != variables.get("quoteorder")) {
                Quoteorder quoteorder = (Quoteorder) variables.get("quoteorder");
                map.put("quoteorderNo", quoteorder.getQuoteorderNo());
                url = "./order/quote/getQuoteDetail.do?quoteorderId=" + quoteorder.getQuoteorderId() + "&viewType=2";
            }
        } else if (processDefinitionKey.equals("closeBussinesschanceVerify")) {
            // 消息模板编号N048
            messageTemplateId = 48;
            if (null != variables.get("bussinessChance")) {
                BussinessChance bussinessChance = (BussinessChance) variables.get("bussinessChance");
                map.put("bussinessChanceNo", bussinessChance.getBussinessChanceNo());
                url = "./order/bussinesschance/toSalesDetailPage.do?bussinessChanceId="
                        + bussinessChance.getBussinessChanceId() + "&traderId=" + bussinessChance.getTraderId();
            }
        } else if (processDefinitionKey.equals("buyorderVerify")) {
            // 消息模板编号N014
            messageTemplateId = 14;
            if (null != variables.get("buyorderInfo")) {
                BuyorderVo buyorderInfo = (BuyorderVo) variables.get("buyorderInfo");
                map.put("buyorderNo", buyorderInfo.getBuyorderNo());
                url = "./order/buyorder/viewBuyorder.do?buyorderId=" + buyorderInfo.getBuyorderId();
            }
        } else if (processDefinitionKey.equals("fileDeliveryVerify")) {
            // 消息模板编号N051
            messageTemplateId = 51;
            if (null != variables.get("fileDelivery")) {
                FileDelivery fileDelivery = (FileDelivery) variables.get("fileDelivery");
                map.put("fileDeliveryNo", fileDelivery.getFileDeliveryNo());
                url = "./logistics/filedelivery/getFileDeliveryDetail.do?fileDeliveryId="
                        + fileDelivery.getFileDeliveryId();
            }
        } else if (processDefinitionKey.equals("paymentVerify")) {
            // 消息模板编号N054
            messageTemplateId = 54;
            if (null != variables.get("payApply")) {
                PayApply payApply = (PayApply) variables.get("payApply");
                map.put("buyorderNo", payApply.getOrderNo());
                String no = payApply.getOrderNo().substring(0, 2);
                if (no.equals("SH")) {
                    url = "./finance/after/getFinanceAfterSaleDetail.do?afterSalesId=" + payApply.getRelatedId();
                } else {
                    if (delegateTask.getName().equals("财务审核") || delegateTask.getName().equals("财务制单")) {
                        url = "./finance/buyorder/viewBuyorder.do?buyorderId=" + payApply.getRelatedId();
                    } else {
                        url = "./order/buyorder/viewBuyordersh.do?buyorderId=" + payApply.getRelatedId();
                    }
                }
            }
        } else if (processDefinitionKey.equals("editSaleorderVerify")) {
            // 消息模板编号N064
            messageTemplateId = 64;
            if (null != variables.get("saleorderModifyApplyInfo")) {
                SaleorderModifyApply saleorderModifyApply =
                        (SaleorderModifyApply) variables.get("saleorderModifyApplyInfo");
                map.put("saleorderNo", saleorderModifyApply.getSaleorderModifyApplyNo());
                url = "./order/saleorder/viewModifyApply.do?saleorderModifyApplyId="
                        + saleorderModifyApply.getSaleorderModifyApplyId() + "&saleorderId="
                        + saleorderModifyApply.getSaleorderId();
            }
        } else if (processDefinitionKey.equals("earlyBuyorderVerify")) {
            // 消息模板编号N057
            messageTemplateId = 57;
            if (null != variables.get("saleorderInfo")) {
                Saleorder saleorder = (Saleorder) variables.get("saleorderInfo");
                map.put("saleorderNo", saleorder.getSaleorderNo());
                url = "./order/saleorder/view.do?saleorderId=" + saleorder.getSaleorderId();
            }
        } else if (processDefinitionKey.equals("invoiceVerify")) {
            // 消息模板编号N071
            messageTemplateId = 71;
            if (null != variables.get("invoiceApply")) {
                InvoiceApply invoiceApply = (InvoiceApply) variables.get("invoiceApply");
                if (invoiceApply.getType().equals(504)) {
                    // 消息模板编号N071
                    messageTemplateId = 71;
                    map.put("orderNo", invoiceApply.getAfterSalesNo());
                    url = "./finance/after/getFinanceAfterSaleDetail.do?afterSalesId=" + invoiceApply.getRelatedId()
                            + "&eFlag=cw";

                } else {
                    // 消息模板编号N071
                    messageTemplateId = 71;
                    map.put("orderNo", invoiceApply.getSaleorderNo());
                    url = "./finance/invoice/viewSaleorder.do?saleorderId=" + invoiceApply.getRelatedId();
                }

            }
        } else if (processDefinitionKey.equals("editTraderCustomerName")) {
            // 消息模板编号N072
            messageTemplateId = 72;
            if (null != variables.get("trader")) {
                Trader trader = (Trader) variables.get("trader");
                map.put("traderName", trader.getTraderNameBefore());
                url = "./trader/customer/baseinfo.do?traderId=" + trader.getTraderId();
            }
        } else if (processDefinitionKey.equals("editBuyorderVerify")) {
            // 消息模板编号N076
            messageTemplateId = 76;
            if (null != variables.get("buyorderModifyApplyInfo")) {
                BuyorderModifyApplyVo buyorderModifyApply =
                        (BuyorderModifyApplyVo) variables.get("buyorderModifyApplyInfo");
                map.put("buyorderNo", buyorderModifyApply.getBuyorderModifyApplyNo());
                url = "./order/buyorder/viewModifyApply.do?buyorderModifyApplyId="
                        + buyorderModifyApply.getBuyorderModifyApplyId();
            }
        } else if (processDefinitionKey.equals("overAfterSalesVerify")) {
            if (null != variables.get("afterSalesVo")) {
                AfterSalesVo afterSalesInfo = (AfterSalesVo) variables.get("afterSalesVo");
                if (afterSalesInfo != null && afterSalesInfo.getVerifiesType().equals(0)) {
                    // 关闭
                    messageTemplateId = 80;
                } else if (afterSalesInfo != null && afterSalesInfo.getVerifiesType().equals(1)) {
                    // 完成
                    messageTemplateId = 83;
                }
                map.put("afterSalesNo", afterSalesInfo.getAfterSalesNo());
                // 类型是销售
                if (afterSalesInfo.getSubjectType() == 535) {
                    url = "./aftersales/order/viewAfterSalesDetail.do?afterSalesId=" + afterSalesInfo.getAfterSalesId()
                            + "&traderType=1";
                    // 类型是采购
                } else if (afterSalesInfo.getSubjectType() == 536) {
                    url = "./aftersales/order/viewAfterSalesDetail.do?afterSalesId=" + afterSalesInfo.getAfterSalesId()
                            + "&traderType=2";
                } else {
                    url = "./aftersales/order/viewAfterSalesDetail.do?afterSalesId=" + afterSalesInfo.getAfterSalesId();
                }
            }
            /* 合同回传审核 */
        } else if (processDefinitionKey.equals("contractReturnVerify")) {
            // 消息模板编号N087
            messageTemplateId = 87;
            if (null != variables.get("saleorderInfo")) {
                Saleorder saleorder = (Saleorder) variables.get("saleorderInfo");
                map.put("saleorderNo", saleorder.getSaleorderNo());
                url = "./order/saleorder/view.do?saleorderId=" + saleorder.getSaleorderId();
            }
        }
        MessageUtil.sendMessage(messageTemplateId, varifyUserList, map, url, preAssignee);//

    }

}
