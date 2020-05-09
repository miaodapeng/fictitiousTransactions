package com.vedeng.aftersales.controller;

import com.vedeng.activiti.service.ActionProcdefService;
import com.vedeng.aftersales.model.AfterSalesCost;
import com.vedeng.aftersales.model.AfterSalesTrader;
import com.vedeng.aftersales.model.vo.AfterSalesCostVo;
import com.vedeng.aftersales.model.vo.AfterSalesVo;
import com.vedeng.aftersales.service.AfterSalesService;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.validator.FormToken;
import com.vedeng.finance.model.InvoiceApply;
import com.vedeng.finance.service.InvoiceService;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.system.service.UserService;
import com.vedeng.trader.model.CommunicateRecord;
import com.vedeng.trader.service.TraderCustomerService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <b>Description:</b><br> 耗材售后订单控制器
 * @author Barry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.aftersales.controller
 * <br><b>ClassName:</b> HcAfterSalesController
 * <br><b>Date:</b> 2018年12月10日 下午5:01:13
 */
@Controller
@RequestMapping("/aftersales/hcorder")
public class HcAfterSalesController extends BaseController {

    @Resource
    private AfterSalesService afterSalesOrderService;

    @Resource
    private UserService userService;

    @Resource
    private TraderCustomerService traderCustomerService;

    @Autowired // 自动装载
    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    @Autowired
    @Qualifier("invoiceService")
    private InvoiceService invoiceService;//自动注入invoiceService

    @Autowired
    @Qualifier("actionProcdefService")
    private ActionProcdefService actionProcdefService;

    @RequestMapping("/getHcAfterSalesPage")
    @ResponseBody
    public ModelAndView getAfterSalesPage(HttpServletRequest request, AfterSalesVo afterSalesVo,
            @RequestParam(required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(required = false, defaultValue = "20") Integer pageSize){
        User sessUser = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        ModelAndView mav =new ModelAndView("/aftersales/hcorder/index");
        Page page = getPageTag(request, pageNo, pageSize);
        // 售后人员
        List<User> serviceUserList = userService.getUserListByPositType(SysOptionConstant.ID_312,sessUser.getCompanyId());
        mav.addObject("serviceUserList", serviceUserList);
        //业务类型
//        List<SysOptionDefinition> sysList1 = getSysOptionDefinitionList(908);//耗材
        List<SysOptionDefinition> sysList1 = getSysOptionDefinitionList(535);//售后
        //List<SysOptionDefinition> sysList2 = getSysOptionDefinitionList(536);//采购
//        List<SysOptionDefinition> sysList3 = getSysOptionDefinitionList(537);//第三方
        List<SysOptionDefinition> sysList = new ArrayList<>();
        sysList.addAll(sysList1);
//        sysList.addAll(sysList3);
        mav.addObject("sysList", sysList);

        List<Integer> serviceUserIdList = new ArrayList<>();
        if(afterSalesVo != null && afterSalesVo.getServiceUserId() != null){
            serviceUserIdList.add(afterSalesVo.getServiceUserId());
            afterSalesVo.setServiceUserIdList(serviceUserIdList);
        }else{
            for (User user : serviceUserList) {
                serviceUserIdList.add(user.getUserId());
            }
            afterSalesVo.setServiceUserIdList(serviceUserIdList);
        }
        String search = request.getParameter("search");
        String nowDate = "";
        String pre1MonthDate = "";
        //获取当前日期
        Date date = new Date();
        nowDate = DateUtil.DatePreMonth(date, 0, null);
        //获取往前推一个月
        pre1MonthDate = DateUtil.getDayOfMonth(-1);
        if(search == null || "".equals(search)){
            mav.addObject("nowDate", nowDate);
            mav.addObject("pre1MonthDate", pre1MonthDate);
        }
        if(null != request.getParameter("starttime")){
            afterSalesVo.setSearchStartTime(DateUtil.convertLong(afterSalesVo.getStarttime(), "yyyy-MM-dd"));
        } else {
            afterSalesVo.setTimeType(1);
            afterSalesVo.setSearchStartTime(DateUtil.convertLong(pre1MonthDate, "yyyy-MM-dd"));
        }
        if(null != request.getParameter("endtime") && request.getParameter("endtime") != ""){
            afterSalesVo.setSearchEndTime(DateUtil.convertLong(afterSalesVo.getEndtime()+" 23:59:59", "yyyy-MM-dd HH:mm:ss"));
        } else {
            afterSalesVo.setSearchEndTime(DateUtil.convertLong(nowDate + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
        }

        afterSalesVo.setCompanyId(sessUser.getCompanyId());
        //设定售后来源 0:erp 1:耗材
        afterSalesVo.setSource(1);
        Map<String, Object> map = afterSalesOrderService.getAfterSalesPage(afterSalesVo, page,serviceUserList);
        if(map!=null){
            mav.addObject("list", map.get("list"));
            mav.addObject("page", map.get("page"));
        }
        User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        mav.addObject("loginUser", user);
        mav.addObject("afterSalesVo", afterSalesVo);
        return mav;
    }

    /**
     * <b>Description:</b><br> 查看售后订单详情
     * @param request
     * @param afterSales
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年10月25日 下午4:24:34
     */
    @FormToken(save=true)
    @ResponseBody
    @RequestMapping("/viewAfterSalesDetail")
    public ModelAndView viewAfterSalesDetail(HttpServletRequest request, AfterSalesVo afterSales){
        User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        afterSales.setCompanyId(user.getCompanyId());
        ModelAndView mav = new ModelAndView();
        AfterSalesVo afterSalesVo= afterSalesOrderService.getAfterSalesVoDetail(afterSales);
        //查询关闭原因
        afterSalesVo.setAfterSalesStatusResonName((getSysOptionDefinition(afterSalesVo.getAfterSalesStatusReson()).getComments()));
        //mav.addObject("curr_user", user);
        mav.addObject("afterSalesVo", afterSalesVo);

        String sku = "";
        if(afterSalesVo.getAfterSalesGoodsList().size() > 0){
            for (int i = 0; i < afterSalesVo.getAfterSalesGoodsList().size(); i++) {
                if(i == afterSalesVo.getAfterSalesGoodsList().size() - 1){
                    sku += afterSalesVo.getAfterSalesGoodsList().get(i).getSku();
                }else{
                    sku += afterSalesVo.getAfterSalesGoodsList().get(i).getSku() + ",";
                }
            }
        }
        if(null != sku && !("".equals(sku))){
            mav.addObject("sku", sku);
        }

        //		查询订单的费用类型以及费用列表
        AfterSalesCost afterSalesCost = new AfterSalesCost();
        afterSalesCost.setAfterSalesId(afterSalesVo.getAfterSalesId());
        List<AfterSalesCostVo> costList = afterSalesOrderService.getAfterSalesCostListById(afterSalesCost);
        for(AfterSalesCostVo cost:costList){
            cost.setCostTypeName(getSysOptionDefinition(cost.getType()).getComments());
            cost.setLastModTime(DateUtil.convertString(cost.getModTime(),""));
        }
        mav.addObject("costList",costList);

        if(afterSalesVo.getType() != 544 && afterSalesVo.getType() != 545 && afterSalesVo.getType() != 552 && afterSalesVo.getType() != 553){
            CommunicateRecord communicateRecord = new CommunicateRecord();
            communicateRecord.setAfterSalesId(afterSalesVo.getAfterSalesId());
            List<CommunicateRecord> crList = traderCustomerService.getCommunicateRecordList(communicateRecord);
            //遍历结果集循环获取客户名称
            for(CommunicateRecord cr:crList){
                AfterSalesTrader afterSalesTrader = new AfterSalesTrader();
                afterSalesTrader.setAfterSalesTraderId(cr.getAfterSalesTraderId());
                AfterSalesTrader ast = afterSalesOrderService.getAfterSalesTrader(afterSalesTrader);
                if (ast != null) {
                    cr.setAfterSalesTraderName(ast.getTraderName());
                }
            }
            mav.addObject("communicateList", crList);
        }
        //耗材订单
        if(afterSalesVo.getSubjectType() == 535){

            if(afterSalesVo.getType()==539 && (afterSalesVo.getStatus() == 0||afterSalesVo.getStatus() == 3)){
                mav.setViewName("order/saleorder/view_afterSales_th");
            }else if(afterSalesVo.getType()==539 && afterSalesVo.getStatus() == 1){
                mav.setViewName("order/saleorder/view_afterSales_th_shz");
            }else if(afterSalesVo.getType()==539 && afterSalesVo.getStatus() == 2){
                mav.setViewName("order/saleorder/view_afterSales_th_shwc");
            }else if(afterSalesVo.getType()==540 && (afterSalesVo.getStatus() == 0||afterSalesVo.getStatus() == 3)){
                mav.setViewName("order/saleorder/view_afterSales_hh");
            }else if(afterSalesVo.getType()==540 && afterSalesVo.getStatus() == 1){
                mav.setViewName("order/saleorder/view_afterSales_hh_shz");
            }else if(afterSalesVo.getType()==540 && afterSalesVo.getStatus() == 2){
                mav.setViewName("order/saleorder/view_afterSales_hh_shwc");
            }

        }
        // 判断是否有正在审核中的付款申请
        Integer isPayApplySh = 0;
        Integer payApplyId = 0;
        if(afterSalesVo.getAfterPayApplyList() != null){
            for (int i = 0; i < afterSalesVo.getAfterPayApplyList().size(); i++) {
                if (afterSalesVo.getAfterPayApplyList().get(i).getValidStatus() == 0 || afterSalesVo.getAfterPayApplyList().get(i).getValidStatus() == 2) {
                    if (afterSalesVo.getAfterPayApplyList().get(i).getValidStatus() == 0) {
                        isPayApplySh = 1;
                    }
                    // payApplyId = afterSalesVo.getAfterPayApplyList().get(i).getPayApplyId();
                    break;
                }
            }
            if (!afterSalesVo.getAfterPayApplyList().isEmpty() && payApplyId == 0) {
                payApplyId = afterSalesVo.getAfterPayApplyList().get(0).getPayApplyId();
            }
        }
        mav.addObject("isPayApplySh", isPayApplySh);

        Map<String, Object> historicInfoPay=actionProcdefService.getHistoric(processEngine, "paymentVerify_"+ payApplyId);
        Task taskInfoPay = (Task) historicInfoPay.get("taskInfo");
        mav.addObject("taskInfoPay", taskInfoPay);
        mav.addObject("startUserPay", historicInfoPay.get("startUser"));
        // 最后审核状态
        mav.addObject("endStatusPay",historicInfoPay.get("endStatus"));
        mav.addObject("historicActivityInstancePay", historicInfoPay.get("historicActivityInstance"));
        mav.addObject("commentMapPay", historicInfoPay.get("commentMap"));
        mav.addObject("candidateUserMapPay", historicInfoPay.get("candidateUserMap"));
        String verifyUsersPay = null;
        if(null!=taskInfoPay){
            Map<String, Object> taskInfoVariables= actionProcdefService.getVariablesMap(taskInfoPay);
            verifyUsersPay = (String) taskInfoVariables.get("verifyUsers");
        }
        mav.addObject("verifyUsersPay", verifyUsersPay);


        Map<String, Object> historicInfo=actionProcdefService.getHistoric(processEngine, "afterSalesVerify_"+ afterSalesVo.getAfterSalesId());
        Task taskInfo = (Task) historicInfo.get("taskInfo");
        mav.addObject("taskInfo", historicInfo.get("taskInfo"));
        mav.addObject("startUser", historicInfo.get("startUser"));
        mav.addObject("candidateUserMap", historicInfo.get("candidateUserMap"));
        // 最后审核状态
        mav.addObject("endStatus",historicInfo.get("endStatus"));
        mav.addObject("historicActivityInstance", historicInfo.get("historicActivityInstance"));
        mav.addObject("commentMap", historicInfo.get("commentMap"));
        String verifyUsers = null;
        if(null!=taskInfo){
            Map<String, Object> taskInfoVariables= actionProcdefService.getVariablesMap(taskInfo);
            verifyUsers = (String) taskInfoVariables.get("verifyUsers");
        }
        mav.addObject("verifyUsers", verifyUsers);


        //售后单完结审核信息
        Map<String, Object> historicInfoOver=actionProcdefService.getHistoric(processEngine, "overAfterSalesVerify_"+ afterSales.getAfterSalesId());
        Task taskInfoOver = (Task) historicInfoOver.get("taskInfo");
        mav.addObject("taskInfoOver", taskInfoOver);
        mav.addObject("startUserOver", historicInfoOver.get("startUser"));
        // 最后审核状态
        mav.addObject("endStatusOver",historicInfoOver.get("endStatus"));
        mav.addObject("historicActivityInstanceOver", historicInfoOver.get("historicActivityInstance"));
        mav.addObject("commentMapOver", historicInfoOver.get("commentMap"));
        mav.addObject("candidateUserMapOver", historicInfoOver.get("candidateUserMap"));
        String verifyUsersOver = null;
        if(null!=taskInfoOver){
            Map<String, Object> taskInfoVariables= actionProcdefService.getVariablesMap(taskInfoOver);
            verifyUsersOver = (String) taskInfoVariables.get("verifyUsers");
        }
        mav.addObject("verifyUsersOver", verifyUsersOver);


        //开票申请审核信息
        InvoiceApply invoiceApplyInfo= invoiceService.getInvoiceApplyByRelatedId(afterSalesVo.getAfterSalesId(),SysOptionConstant.ID_504,afterSalesVo.getCompanyId());
        mav.addObject("invoiceApply", invoiceApplyInfo);
        if(invoiceApplyInfo != null) {
            Map<String, Object> historicInfoInvoice=actionProcdefService.getHistoric(processEngine, "invoiceVerify_"+ invoiceApplyInfo.getInvoiceApplyId());
            mav.addObject("taskInfoInvoice", historicInfoInvoice.get("taskInfo"));
            mav.addObject("startUserInvoice", historicInfoInvoice.get("startUser"));
            mav.addObject("candidateUserMapInvoice", historicInfoInvoice.get("candidateUserMap"));
            // 最后审核状态
            mav.addObject("endStatusInvoice",historicInfoInvoice.get("endStatus"));
            mav.addObject("historicActivityInstanceInvoice", historicInfoInvoice.get("historicActivityInstance"));
            mav.addObject("commentMapInvoice", historicInfoInvoice.get("commentMap"));

            Task taskInfoInvoice = (Task) historicInfoInvoice.get("taskInfo");
            //当前审核人
            String verifyUsersInvoice = null;
            if(null!=taskInfoInvoice){
                Map<String, Object> taskInfoVariables= actionProcdefService.getVariablesMap(taskInfoInvoice);
                verifyUsersInvoice = (String) taskInfoVariables.get("verifyUsers");
            }
            mav.addObject("verifyUsersInvoice", verifyUsersInvoice);
        }

        return mav;
    }

    /**
     * <b>Description:</b><br>
     * 审核弹层页面
     *
     * @param session
     * @param taskId
     * @param pass
     * @param type
     * @return
     * @Note <b>Author:</b> leo.yang <br>
     *       <b>Date:</b> 2018年1月5日 下午3:16:51
     */
    @FormToken(save = true)
    @ResponseBody
    @RequestMapping(value = "/complement")
    public ModelAndView complement(HttpSession session, String taskId, Boolean pass, Integer type) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("taskId", taskId);
        mv.addObject("pass", pass);
        mv.addObject("type", type);
        mv.setViewName("order/saleorder/complement");
        return mv;
    }
}
