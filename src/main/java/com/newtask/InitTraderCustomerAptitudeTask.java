package com.newtask;

import com.vedeng.activiti.service.ActionProcdefService;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.StringUtil;
import com.vedeng.system.service.VerifiesRecordService;
import com.vedeng.trader.dao.TraderCertificateMapper;
import com.vedeng.trader.dao.TraderCustomerMapper;
import com.vedeng.trader.model.TraderCertificate;
import com.vedeng.trader.model.TraderCustomer;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.task.Task;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JobHandler(value = "initTraderCustomerAptitudeTask")
@Component
public class InitTraderCustomerAptitudeTask extends IJobHandler {
    Logger logger= LoggerFactory.getLogger(InitTraderCustomerAptitudeTask.class);
    @Autowired
    @Qualifier("traderCustomerMapper")
    private TraderCustomerMapper traderCustomerMapper;

    @Autowired
    @Qualifier("traderCertificateMapper")
    private TraderCertificateMapper traderCertificateMapper;


    @Autowired
    @Qualifier("actionProcdefService")
    private ActionProcdefService actionProcdefService;

    @Autowired
    @Qualifier("verifiesRecordService")
    private VerifiesRecordService verifiesRecordService;
    @Autowired // 自动装载
    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    private boolean isVerify(TraderCustomer customer){
        List<TraderCertificate> certificates=traderCertificateMapper.getTraderCertificatesByTraderId(customer);
        if(CollectionUtils.isEmpty(certificates)){
            return false;
        }
        boolean isVerify=false;
        for(TraderCertificate certificate:certificates){
            if(certificate.getSysOptionDefinitionId()== SysOptionConstant.ID_25
                    &&certificate.getBegintime()!=null&& StringUtil.isNotBlank(certificate.getUri())){
                isVerify=true;
            }
        }
        return isVerify;
    }

    @Transactional
    public ResultInfo startCheckAptitude(HttpServletRequest request, TraderCustomer traderCustomer, String taskId) throws Exception{
        try {
            Map<String, Object> variableMap = new HashMap<String, Object>();
            User user=new User();
            user.setUserId(2);
            user.setUsername("njadmin");
            // 查询当前订单的一些状态
            /*TraderCustomerVo traderBaseInfo = traderCustomerService.getTraderCustomerBaseInfo(traderCustomer);*/
            //开始生成流程(如果没有taskId表示新流程需要生成)
            if (StringUtil.isBlank(taskId) || taskId.equals("0")) {
                XxlJobLogger.log("startCheckAptitude:"+traderCustomer.getTraderCustomerId());
                variableMap.put("traderCustomer", traderCustomer);
                variableMap.put("currentAssinee", user.getUsername());
                variableMap.put("processDefinitionKey", "customerAptitudeVerify");
                variableMap.put("businessKey", "customerAptitude_" + traderCustomer.getTraderCustomerId());
                variableMap.put("relateTableKey", traderCustomer.getTraderCustomerId());
                variableMap.put("relateTable", "T_CUSTOMER_APTITUDE");
                try {
                    actionProcdefService.createProcessInstance(request, "customerAptitudeVerify", "customerAptitude_" + traderCustomer.getTraderCustomerId(), variableMap);
                }catch (Exception ex){
                    XxlJobLogger.log("流程已经提交请勿重复提交");
                    logger.error("流程已经提交："+traderCustomer.getTraderCustomerId());
                }
            }
            //默认申请人通过
            //根据BusinessKey获取生成的审核实例
            Map<String, Object> historicInfo = actionProcdefService.getHistoricForNjadmin(processEngine, "customerAptitude_" + traderCustomer.getTraderCustomerId());
            if (historicInfo.get("endStatus") != "审核完成") {
                Task taskInfo = (Task) historicInfo.get("taskInfo");
                taskId = taskInfo.getId();
                ResultInfo<?> complementStatus = actionProcdefService.complementTask(request, taskId, "", user.getUsername(), variableMap);
                if(complementStatus.getCode()==-1){
                    XxlJobLogger.log("申请人环节失败");
                    throw new Exception();
                }
                //如果未结束添加审核对应主表的审核状态
                verifiesRecordService.saveVerifiesInfo(taskId, 0);


            }

            return new ResultInfo(0, "操作成功");
        } catch (Exception e) {
            XxlJobLogger.log("任务完成失败",e);
            logger.error("初始化客户资质审核失败："+traderCustomer.getTraderCustomerId());
            throw e;
//            return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
        }
    }
    @Override
    public ReturnT<String> execute(String param) throws Exception {

        if(StringUtils.isNotBlank(param)){
            TraderCustomer trader= traderCustomerMapper.selectByPrimaryKey(Integer.parseInt(param));
            if(trader.getCustomerNature()!=465){
                XxlJobLogger.log("customerNature 不是分销");
                return ReturnT.FAIL;
            }
            if (isVerify(trader)) {
                try {
                    startCheckAptitude(null, trader, null);
                }catch (Exception ex){

                }
            }else{
                XxlJobLogger.log("不满足条件");
            }
            return SUCCESS;
        }


        Page page=new Page(1,100);
        int count=0;
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        TraderCustomer traderCustomer = new TraderCustomer();
        traderCustomer.setCustomerNature(465);
        params.put("trader", traderCustomer);
        List<TraderCustomer> traders = traderCustomerMapper.getTraderCustomerIdsListPage(params);
        for (TraderCustomer t : traders) {
            if (isVerify(t)) {
                count++;
                try {
                    startCheckAptitude(null, t, null);
                }catch (Exception ex){}
            }
        }

        int pageSum = page.getTotalPage();
        if (pageSum > 1) {
            for (int i = 2; i <= pageSum; i++) {
                page.setPageNo(i);
                params.put("page", page);
                List<TraderCustomer> traderCustomers = traderCustomerMapper.getTraderCustomerIdsListPage(params);
                for (TraderCustomer t : traderCustomers) {
                    if (isVerify(t)) {
                        count++;
                        try {
                            startCheckAptitude(null, t, null);
                        }catch (Exception ex){

                        }
                    }
                }
            }
        }
        return new ReturnT<>().SUCCESS;
    }
}
