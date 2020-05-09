package com.newtask;

import com.vedeng.authorization.model.Organization;
import com.vedeng.common.constant.TraderConstants;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.StringUtil;
import com.vedeng.order.dao.SaleorderMapper;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.model.SaleorderCountParam;
import com.vedeng.system.service.OrganizationService;
import com.vedeng.trader.dao.TraderCustomerMapper;
import com.vedeng.trader.model.TraderCustomer;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b>Description:</b>每天零点刷新客户分类<br>
 *
 * @Note <b>Author:calvin</b>
 * <br><b>Date:</b> 2019/10/29
 */
@JobHandler(value = "traderCustomerCategoryHandler")
@Component
public class TraderCustomerCategoryHandler extends IJobHandler {

    Logger logger= LoggerFactory.getLogger(TraderCustomerCategoryHandler.class);

    @Autowired
    private TraderCustomerMapper traderCustomerMapper;

    @Autowired
    private SaleorderMapper saleorderMapper;

    @Autowired
    private OrganizationService organizationService;
    @Override
    public ReturnT<String> execute(String param) throws Exception {
        if(StringUtil.isNotBlank(param)){
            if(!StringUtil.isNumeric(param)){
                XxlJobLogger.log("输入的不是数字");
                return new ReturnT<>().SUCCESS;
            }
            TraderCustomer traderCustomer=traderCustomerMapper.getTraderCustomerByTraderId(Integer.valueOf(param));
            if(traderCustomer == null || traderCustomer.getTraderId() == null || traderCustomer.getTraderCustomerId() == null){
                XxlJobLogger.log("该用户不存在");
                return new ReturnT<>().SUCCESS;
            }
            classifyCategoryBySource(traderCustomer);
            return new ReturnT<>().SUCCESS;
        }
        Organization organization=new Organization();
        organization.setOrgId(38);
        organization.setCompanyId(1);
        organization.setOrgName("医疗B2B业务部");
        List<Organization> organizations=organizationService.getOrganizationChild(organization);
        if(CollectionUtils.isNotEmpty(organizations)){
            classifyCustomerCategoryByPage(organizations,TraderConstants.ZERO);
        }
        organization.setOrgId(39);
        organization.setOrgName("医械购诊所业务部");
        organizations=organizationService.getOrganizationChild(organization);
        if(CollectionUtils.isNotEmpty(organizations)){
            classifyCustomerCategoryByPage(organizations,TraderConstants.ONE);
        }
        return new ReturnT<>().SUCCESS;
    }

    private void classifyCustomerCategoryByPage(List<Organization> organizations,int type){
        Page page = new Page(1, 200);
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("organizations",organizations);
        List<TraderCustomer> traders = traderCustomerMapper.getTraderCustomerByOrganizationsListPage(params);
        classifyCustomerCategory(traders,type);
        int pageSum = page.getTotalPage();
        if (pageSum > TraderConstants.ONE) {
            for (int i = 2; i <= pageSum; i++) {
                page.setPageNo(i);
                params.put("page", page);
                traders = traderCustomerMapper.getTraderCustomerByOrganizationsListPage(params);
                classifyCustomerCategory(traders,type);
            }
        }

    }
    /**
     * <b>Description:</b>给客户分类<br>
     *
     * @param
     * @return
     * @Note <b>Author:calvin</b>
     * <br><b>Date:</b> 2019/10/29
     */
    private void classifyCustomerCategory(List<TraderCustomer> customers,int type) {
        if (CollectionUtils.isEmpty(customers)) {
            return;
        }
        for (TraderCustomer t : customers) {
            if (t != null && t.getTraderId() != null && t.getTraderCustomerId() != null) {
             //   XxlJobLogger.log("正在处理：" + t.getTraderId());
               // logger.info("客户分类定时任务正在处理：" + t.getTraderId());
                t.setSource(type);
                classifyCategoryBySource(t);
            }
        }
    }


    /**
     * <b>Description:</b>根据用户来源给客户分类<br>
     *
     * @param
     * @return
     * @Note <b>Author:calvin</b>
     * <br><b>Date:</b> 2019/10/29
     */

    private void classifyCategoryBySource(TraderCustomer customer) {
        if (customer.getSource() != null && customer.getSource() == TraderConstants.ZERO) {
            classifyCategoryByDays(TraderConstants.ERP_PREIOD_DAYS, customer, TraderConstants.ZERO);
        } else if (customer.getSource() != null && customer.getSource() == TraderConstants.ONE) {
            classifyCategoryByDays(TraderConstants.YXG_PERIOD_DAYS, customer, TraderConstants.ONE);
        }
    }

    /**
     * <b>Description:</b>给客户分类<br>
     *
     * @param days   时间段
     * @param trader 客户信息
     * @return
     * @Note <b>Author:calvin</b>
     * <br><b>Date:</b> 2019/10/29
     */
    private void classifyCategoryByDays(int days, TraderCustomer trader, int type) {
        Date date = new Date();
        Long beginTime = DateUtil.getDateBefore(date, days);
        SaleorderCountParam param = new SaleorderCountParam();
        param.setTraderId(trader.getTraderId());
        param.setType(type);
        int hasOrder = saleorderMapper.getSaleorderCountByTime(param);
        //未交易客户：从未发生过交易注册用户
        if (hasOrder == TraderConstants.ZERO) {
            trader.setCustomerCategory(TraderConstants.CUSTOMER_CATEGORY_UN_TRADE);
            traderCustomerMapper.updateCustomerCategoryById(trader);
            return;
        }

        param.setBeginTime(beginTime);
        int hasOrderInDays = saleorderMapper.getSaleorderCountByTime(param);
        param.setBeginTime(null);
        param.setEndTime(beginTime);
        int hasOrderOutDay = saleorderMapper.getSaleorderCountByTime(param);
        //新客户
        if (hasOrderOutDay == TraderConstants.ZERO && hasOrderInDays > TraderConstants.ZERO && hasOrder < TraderConstants.NEW_CUSTOMER_COUNT) {
            trader.setCustomerCategory(TraderConstants.CUSTOMER_CATEGORY_NEW);
            traderCustomerMapper.updateCustomerCategoryById(trader);
            return;

        } else if (hasOrderOutDay > TraderConstants.ZERO && hasOrderInDays == TraderConstants.ZERO) {
            //流失客户
            trader.setCustomerCategory(TraderConstants.CUSTOMER_CATEGORY_LOSS);
            traderCustomerMapper.updateCustomerCategoryById(trader);
            return;
        }
        //留存客户
        trader.setCustomerCategory(TraderConstants.CUSTOMER_CATEGORY_STAY);
        traderCustomerMapper.updateCustomerCategoryById(trader);
    }
}
