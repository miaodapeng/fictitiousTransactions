package com.vedeng.activiti.taskassign;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.history.HistoricProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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

public class TaskMessageExecutionListener implements ExecutionListener {
    WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
    private UserService userService = (UserService) context.getBean("userService");

    @Autowired // 自动装载
    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    @Resource
    private WebServiceContext webServiceContext;

    // 根据穿参通用回写主表中状态
    public void notify(DelegateExecution execution) throws Exception {
        ServletRequestAttributes ra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ra.getRequest();
        User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        // 申请人名称
        String preAssignee = execution.getVariable("currentAssinee").toString();
        User assigneeInfo = userService.getByUsername(preAssignee, user.getCompanyId());
        HistoryService historyService = processEngine.getHistoryService(); // 任务相关service
        String processInstanceId = execution.getProcessInstanceId();
        HistoricProcessInstance historicProcessInstance =
                historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        String processDefinitionKey = historicProcessInstance.getProcessDefinitionKey();
        // 把list转为Map
        Map<String, Object> variables = execution.getVariables();

        List<Integer> varifyUserList = new ArrayList<>();

        // 准备发送参数
        // 模板ID
        Integer messageTemplateId = null;
        Map<String, String> map = new HashMap<>();
        String url = null;
        if (processDefinitionKey.equals("traderCustomerVerify")) {
            if (execution.getCurrentActivityName().equals("驳回")) {
                // 消息模板编号N002
                varifyUserList.add(assigneeInfo.getUserId());
                messageTemplateId = 2;
            } else if (execution.getCurrentActivityName().equals("审核完成")) {
                // 消息模板编号N001
                varifyUserList.add(assigneeInfo.getUserId());
                messageTemplateId = 60;
            }
            if (null != variables.get("traderCustomerVo")) {
                TraderCustomerVo traderCustomerVo = (TraderCustomerVo) variables.get("traderCustomerVo");
                map.put("traderName", traderCustomerVo.getTrader().getTraderName());
                url = "./trader/customer/baseinfo.do?traderId=" + traderCustomerVo.getTraderId();
            }
        } else if (processDefinitionKey.equals("traderSupplierVerify")) {
            if (execution.getCurrentActivityName().equals("驳回")) {
                // 消息模板编号N002
                varifyUserList.add(assigneeInfo.getUserId());
                messageTemplateId = 63;
            } else if (execution.getCurrentActivityName().equals("审核完成")) {
                // 消息模板编号N001
                varifyUserList.add(assigneeInfo.getUserId());
                messageTemplateId = 62;
            }
            if (null != variables.get("traderSupplierVo")) {
                TraderSupplierVo traderSupplierVo = (TraderSupplierVo) variables.get("traderSupplierVo");
                map.put("traderName", traderSupplierVo.getTrader().getTraderName());
                url = "./trader/supplier/baseinfo.do?traderId=" + traderSupplierVo.getTrader().getTraderId();
            }
        } else if (processDefinitionKey.equals("quoteVerify")) {
            Integer type = null;
            if (execution.getCurrentActivityName().equals("驳回")) {
                // 消息模板编号N005
                varifyUserList.add(assigneeInfo.getUserId());
                messageTemplateId = 5;
                type = 2;
            } else if (execution.getCurrentActivityName().equals("审核完成")) {
                // 消息模板编号N004
                varifyUserList.add(assigneeInfo.getUserId());
                messageTemplateId = 4;
                type = 3;
            }
            if (null != variables.get("quoteorder")) {
                Quoteorder quoteorder = (Quoteorder) variables.get("quoteorder");
                map.put("quoteorderNo", quoteorder.getQuoteorderNo());
                url = "./order/quote/getQuoteDetail.do?quoteorderId=" + quoteorder.getQuoteorderId() + "&viewType="
                        + type;
            }
        } else if (processDefinitionKey.equals("saleorderVerify")) {
            if (execution.getCurrentActivityName().equals("驳回")) {
                // 消息模板编号N008
                varifyUserList.add(assigneeInfo.getUserId());
                messageTemplateId = 8;
            } else if (execution.getCurrentActivityName().equals("审核完成")) {
                // 消息模板编号N007
                varifyUserList.add(assigneeInfo.getUserId());
                messageTemplateId = 7;
            }
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
            if (execution.getCurrentActivityName().equals("驳回")) {
                varifyUserList.add(assigneeInfo.getUserId());
                // 消息模板编号N013
                messageTemplateId = 13;
            } else if (execution.getCurrentActivityName().equals("审核完成")) {
                varifyUserList.add(assigneeInfo.getUserId());
                // 消息模板编号N056
                messageTemplateId = 56;
            }
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
                    // 经销商（客户）
                    if (execution.getCurrentActivityName().equals("驳回")) {
                        varifyUserList.add(assigneeInfo.getUserId());
                        // 消息模板编号N023
                        messageTemplateId = 23;
                        url = "./trader/customer/baseinfo.do?traderId=" + traderAccountPeriodApply.getTraderId();

                    } else if (execution.getCurrentActivityName().equals("审核完成")) {
                        varifyUserList.add(assigneeInfo.getUserId());
                        // 消息模板编号N022
                        messageTemplateId = 22;
                        url = "./trader/customer/baseinfo.do?traderId=" + traderAccountPeriodApply.getTraderId();
                    }
                } else {
                    // 供应商
                    if (execution.getCurrentActivityName().equals("驳回")) {
                        varifyUserList.add(assigneeInfo.getUserId());
                        // 消息模板编号N025
                        messageTemplateId = 25;
                        url = "./trader/supplier/baseinfo.do?traderId=" + traderAccountPeriodApply.getTraderId();
                    } else if (execution.getCurrentActivityName().equals("审核完成")) {
                        varifyUserList.add(assigneeInfo.getUserId());
                        // 消息模板编号N024
                        messageTemplateId = 24;
                        url = "./trader/supplier/baseinfo.do?traderId=" + traderAccountPeriodApply.getTraderId();
                    }

                }

            }

        } else if (processDefinitionKey.equals("afterSalesVerify")) {
            if (execution.getCurrentActivityName().equals("驳回")) {
                varifyUserList.add(assigneeInfo.getUserId());
                // 消息模板编号N029
                messageTemplateId = 29;
            } else if (execution.getCurrentActivityName().equals("审核完成")) {
                varifyUserList.add(assigneeInfo.getUserId());
                // 消息模板编号N028
                messageTemplateId = 28;
            }
            List<User> userList = userService.getUserByUserIds(varifyUserList);
            Integer type = 0;
            for (User u : userList) {
                for (Position p : u.getPositions()) {
                    type = p.getType();
                }
            }
            if (null != variables.get("afterSalesInfo")) {
                AfterSalesVo afterSalesVo = (AfterSalesVo) variables.get("afterSalesInfo");
                map.put("afterSalesNo", afterSalesVo.getAfterSalesNo());
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
            if (execution.getCurrentActivityName().equals("驳回")) {
                varifyUserList.add(assigneeInfo.getUserId());
                // 消息模板编号N037
                messageTemplateId = 37;
            } else if (execution.getCurrentActivityName().equals("审核完成")) {
                varifyUserList.add(assigneeInfo.getUserId());
                // 消息模板编号N036
                messageTemplateId = 36;
            }
            if (null != variables.get("saleorderInfo")) {
                Saleorder saleorder = (Saleorder) variables.get("saleorderInfo");
                map.put("saleorderNo", saleorder.getSaleorderNo());
                url = "./order/saleorder/viewBhSaleorder.do?saleorderId=" + saleorder.getSaleorderId();
            }
        } else if (processDefinitionKey.equals("closeQuoteorderVerify")) {
            if (execution.getCurrentActivityName().equals("驳回")) {
                varifyUserList.add(assigneeInfo.getUserId());
                // 消息模板编号N047
                messageTemplateId = 47;
            } else if (execution.getCurrentActivityName().equals("审核完成")) {
                varifyUserList.add(assigneeInfo.getUserId());
                // 消息模板编号N046
                messageTemplateId = 46;
            }
            if (null != variables.get("quoteorder")) {
                Quoteorder quoteorder = (Quoteorder) variables.get("quoteorder");
                map.put("quoteorderNo", quoteorder.getQuoteorderNo());
                url = "./order/quote/getQuoteDetail.do?quoteorderId=" + quoteorder.getQuoteorderId() + "&viewType=2";
            }
        } else if (processDefinitionKey.equals("closeBussinesschanceVerify")) {
            if (execution.getCurrentActivityName().equals("驳回通知销售")) {
                varifyUserList.add(assigneeInfo.getUserId());
                // 消息模板编号N050
                messageTemplateId = 50;
            } else if (execution.getCurrentActivityName().equals("审核完成")) {
                varifyUserList.add(assigneeInfo.getUserId());
                // 消息模板编号N049
                messageTemplateId = 49;
            } else if (execution.getCurrentActivityName().equals("驳回通知主管")) {
                User parent = userService.getUserParentInfo(assigneeInfo.getUsername(), user.getCompanyId());
                varifyUserList.add(parent.getpUserId());
                // 消息模板编号N050
                messageTemplateId = 50;
            }
            if (null != variables.get("bussinessChance")) {
                BussinessChance bussinessChance = (BussinessChance) variables.get("bussinessChance");
                map.put("bussinessChanceNo", bussinessChance.getBussinessChanceNo());
                url = "./order/bussinesschance/toSalesDetailPage.do?bussinessChanceId="
                        + bussinessChance.getBussinessChanceId() + "&traderId=" + bussinessChance.getTraderId();
            }
        } else if (processDefinitionKey.equals("buyorderVerify")) {
            if (execution.getCurrentActivityName().equals("驳回")) {
                varifyUserList.add(assigneeInfo.getUserId());
                // 消息模板编号N016
                messageTemplateId = 16;
            } else if (execution.getCurrentActivityName().equals("审核完成")) {
                varifyUserList.add(assigneeInfo.getUserId());
                // 消息模板编号N015
                messageTemplateId = 15;
            }
            if (null != variables.get("buyorderInfo")) {
                BuyorderVo buyorderInfo = (BuyorderVo) variables.get("buyorderInfo");
                map.put("buyorderNo", buyorderInfo.getBuyorderNo());
                if (execution.getCurrentActivityName().equals("驳回")) {

                    url = "./order/buyorder/viewBuyorder.do?buyorderId=" + buyorderInfo.getBuyorderId();

                } else if (execution.getCurrentActivityName().equals("审核完成")) {

                    url = "./order/buyorder/viewBuyordersh.do?buyorderId=" + buyorderInfo.getBuyorderId();
                }
            }
        } else if (processDefinitionKey.equals("fileDeliveryVerify")) {
            if (execution.getCurrentActivityName().equals("驳回")) {
                varifyUserList.add(assigneeInfo.getUserId());
                // 消息模板编号N053
                messageTemplateId = 53;
            } else if (execution.getCurrentActivityName().equals("审核完成")) {
                varifyUserList.add(assigneeInfo.getUserId());
                // 消息模板编号N052
                messageTemplateId = 52;
            }
            if (null != variables.get("fileDelivery")) {
                FileDelivery fileDelivery = (FileDelivery) variables.get("fileDelivery");
                map.put("fileDeliveryNo", fileDelivery.getFileDeliveryNo());
                url = "./logistics/filedelivery/getFileDeliveryDetail.do?fileDeliveryId="
                        + fileDelivery.getFileDeliveryId();
            }
        } else if (processDefinitionKey.equals("paymentVerify")) {
            if (execution.getCurrentActivityName().equals("驳回")) {
                varifyUserList.add(assigneeInfo.getUserId());
                // 消息模板编号N055
                messageTemplateId = 55;
            } else if (execution.getCurrentActivityName().equals("审核完成")) {
                varifyUserList.add(assigneeInfo.getUserId());
                // 消息模板编号N070
                messageTemplateId = 70;
            }
            if (null != variables.get("payApply")) {
                PayApply payApply = (PayApply) variables.get("payApply");
                map.put("buyorderNo", payApply.getOrderNo());
                String no = payApply.getOrderNo().substring(0, 2);
                if (no.equals("SH")) {
                    if (payApply.getOrderNo().isEmpty()) {
                        url = "./aftersales/order/viewAfterSalesDetail.do?afterSalesId=" + payApply.getRelatedId();
                    } else {
                        url = "./aftersales/order/viewAfterSalesDetail.do?afterSalesId=" + payApply.getRelatedId()
                                + "&traderType=1";
                    }
                } else {
                    if (execution.getCurrentActivityName().equals("财务审核")
                            || execution.getCurrentActivityName().equals("财务制单")) {
                        url = "./finance/buyorder/viewBuyorder.do?buyorderId=" + payApply.getRelatedId();
                    } else {
                        url = "./order/buyorder/viewBuyordersh.do?buyorderId=" + payApply.getRelatedId();
                    }
                }
            }
        } else if (processDefinitionKey.equals("editSaleorderVerify")) {
            if (execution.getCurrentActivityName().equals("驳回")) {
                varifyUserList.add(assigneeInfo.getUserId());
                // 消息模板编号N066
                messageTemplateId = 66;
            } else if (execution.getCurrentActivityName().equals("审核完成")) {
                varifyUserList.add(assigneeInfo.getUserId());
                // 消息模板编号N065
                messageTemplateId = 65;
            }
            if (null != variables.get("saleorderModifyApplyInfo")) {
                SaleorderModifyApply saleorderModifyApply =
                        (SaleorderModifyApply) variables.get("saleorderModifyApplyInfo");
                map.put("saleorderNo", saleorderModifyApply.getSaleorderModifyApplyNo());
                url = "./order/saleorder/viewModifyApply.do?saleorderModifyApplyId="
                        + saleorderModifyApply.getSaleorderModifyApplyId() + "&saleorderId="
                        + saleorderModifyApply.getSaleorderId();
            }
        } else if (processDefinitionKey.equals("earlyBuyorderVerify")) {
            if (execution.getCurrentActivityName().equals("驳回")) {
                varifyUserList.add(assigneeInfo.getUserId());
                // 消息模板编号N059
                messageTemplateId = 59;
            } else if (execution.getCurrentActivityName().equals("审核完成")) {
                varifyUserList.add(assigneeInfo.getUserId());
                // 消息模板编号N058
                messageTemplateId = 58;
            }
            if (null != variables.get("saleorderInfo")) {
                Saleorder saleorder = (Saleorder) variables.get("saleorderInfo");
                map.put("saleorderNo", saleorder.getSaleorderNo());
                url = "./order/saleorder/view.do?saleorderId=" + saleorder.getSaleorderId();
            }
        } else if (processDefinitionKey.equals("invoiceVerify")) {
            if (execution.getCurrentActivityName().equals("驳回")) {
                varifyUserList.add(assigneeInfo.getUserId());
                // 消息模板编号N021
                messageTemplateId = 21;
            } else if (execution.getCurrentActivityName().equals("审核完成")) {
                varifyUserList.add(assigneeInfo.getUserId());
                // 消息模板编号N011
                messageTemplateId = 11;
            }
            if (null != variables.get("invoiceApply")) {
                InvoiceApply invoiceApply = (InvoiceApply) variables.get("invoiceApply");
                if (invoiceApply.getType().equals(504)) {
                    map.put("saleorderNo", invoiceApply.getAfterSalesNo());
                    url = "./finance/after/getFinanceAfterSaleDetail.do?afterSalesId=" + invoiceApply.getRelatedId()
                            + "&eFlag=cw";
                } else {
                    map.put("saleorderNo", invoiceApply.getSaleorderNo());
                    url = "./finance/invoice/viewSaleorder.do?saleorderId=" + invoiceApply.getRelatedId();
                }
            }
        } else if (processDefinitionKey.equals("editTraderCustomerName")) {
            if (execution.getCurrentActivityName().equals("驳回")) {
                varifyUserList.add(assigneeInfo.getUserId());
                // 消息模板编号N074
                messageTemplateId = 74;
            } else if (execution.getCurrentActivityName().equals("审核完成")) {
                varifyUserList.add(assigneeInfo.getUserId());
                // 消息模板编号N073
                messageTemplateId = 73;
            }
            if (null != variables.get("trader")) {
                Trader trader = (Trader) variables.get("trader");
                map.put("traderName", trader.getTraderNameBefore());
                url = "./trader/customer/baseinfo.do?traderId=" + trader.getTraderId();
            }
        } else if (processDefinitionKey.equals("editBuyorderVerify")) {
            if (execution.getCurrentActivityName().equals("驳回")) {
                varifyUserList.add(assigneeInfo.getUserId());
                // 消息模板编号N078
                messageTemplateId = 78;
            } else if (execution.getCurrentActivityName().equals("审核完成")) {
                varifyUserList.add(assigneeInfo.getUserId());
                // 消息模板编号N077
                messageTemplateId = 77;
            }
            if (null != variables.get("buyorderModifyApplyInfo")) {
                BuyorderModifyApplyVo buyorderModifyApplyInfo =
                        (BuyorderModifyApplyVo) variables.get("buyorderModifyApplyInfo");
                map.put("buyorderNo", buyorderModifyApplyInfo.getBuyorderModifyApplyNo());
                url = "./order/buyorder/viewModifyApply.do?buyorderModifyApplyId="
                        + buyorderModifyApplyInfo.getBuyorderModifyApplyId();
            }
        } else if (processDefinitionKey.equals("overAfterSalesVerify")) {
            if (null != variables.get("afterSalesVo")) {
                AfterSalesVo afterSalesInfo = (AfterSalesVo) variables.get("afterSalesVo");
                if (execution.getCurrentActivityName().equals("驳回")) {
                    if (afterSalesInfo != null && afterSalesInfo.getVerifiesType().equals(0)) {
                        // 关闭
                        messageTemplateId = 82;
                    } else if (afterSalesInfo != null && afterSalesInfo.getVerifiesType().equals(1)) {
                        // 完成
                        messageTemplateId = 85;
                    }
                } else if (execution.getCurrentActivityName().equals("审核完成")) {
                    if (afterSalesInfo != null && afterSalesInfo.getVerifiesType().equals(0)) {
                        // 关闭
                        messageTemplateId = 81;
                    } else if (afterSalesInfo != null && afterSalesInfo.getVerifiesType().equals(1)) {
                        // 完成
                        messageTemplateId = 84;
                    }
                }
                varifyUserList.add(assigneeInfo.getUserId());
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

        } else if (processDefinitionKey.equals("contractReturnVerify")) {
            if (execution.getCurrentActivityName().equals("驳回")) {
                varifyUserList.add(assigneeInfo.getUserId());
                // 消息模板编号N088
                messageTemplateId = 88;
            }
            if (null != variables.get("saleorderInfo")) {
                Saleorder saleorder = (Saleorder) variables.get("saleorderInfo");
                map.put("saleorderNo", saleorder.getSaleorderNo());
                url = "./order/saleorder/view.do?saleorderId=" + saleorder.getSaleorderId();
            }
        }
        MessageUtil.sendMessage(messageTemplateId, varifyUserList, map, url, preAssignee);//

    }
}
/**
 * @author John
 */
