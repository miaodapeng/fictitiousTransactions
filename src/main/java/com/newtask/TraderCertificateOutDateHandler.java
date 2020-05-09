package com.newtask;

import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.TraderConstants;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.MessageUtil;
import com.vedeng.common.util.StringUtil;
import com.vedeng.trader.dao.RTraderJUserMapper;
import com.vedeng.trader.dao.TraderCertificateMapper;
import com.vedeng.trader.dao.TraderCustomerMapper;
import com.vedeng.trader.dao.TraderMapper;
import com.vedeng.trader.model.RTraderJUser;
import com.vedeng.trader.model.Trader;
import com.vedeng.trader.model.TraderCertificate;
import com.vedeng.trader.model.TraderCustomer;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.apache.commons.collections.CollectionUtils;
import org.apache.lucene.util.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * <b>Description:</b>处理客户资质信息过期<br>
 *
 * @Note <b>Author:calvin</b>
 * <br><b>Date:</b> 2020/3/1
 */
@JobHandler(value = "traderCertificateOutDateHandler")
@Component
public class TraderCertificateOutDateHandler extends IJobHandler {

    Logger logger= LoggerFactory.getLogger(TraderCertificateOutDateHandler.class);

    @Autowired
    private TraderCertificateMapper traderCertificateMapper;

    @Autowired
    private RTraderJUserMapper rTraderJUserMapper;

    @Autowired
    private TraderMapper traderMapper;

    private Long nowDateTime=0L;

    @Override
    public ReturnT<String> execute(String param) throws Exception {
        try {
            nowDateTime = System.currentTimeMillis();
            if (StringUtil.isNotBlank(param)) {
                if (!StringUtil.isNumeric(param)) {
                    XxlJobLogger.log("输入的不是数字");
                    return new ReturnT<>().SUCCESS;
                }
                Trader trader = traderMapper.getTraderAptitudeCheckedByTraderId(Integer.valueOf(param));
                if (trader == null || trader.getTraderId() == null) {
                    XxlJobLogger.log("该用户不存在或资质审核状态不符合要求");
                    return new ReturnT<>().SUCCESS;
                }
                resolveCertificateOutDate(trader);
                return new ReturnT<>().SUCCESS;
            }
            resolveCertificateOutDateByPage();
            return ReturnT.SUCCESS;
        } catch (Exception ex) {
            return ReturnT.FAIL;
        }
    }

    /**
     * <b>Description:</b>分页处理资质过期的客户<br>
     *
     * @param
     * @return
     * @Note <b>Author:calvin</b>
     * <br><b>Date:</b> 2020/2/29
     */
    private void resolveCertificateOutDateByPage() {
        Page page = new Page(1, 200);
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        List<Trader> traders = traderMapper.getTraderListPage(params);
        resolveCertificateOutDateByList(traders);
        int pageSum = page.getTotalPage();
        if (pageSum > TraderConstants.ONE) {
            for (int i = 2; i <= pageSum; i++) {
                page.setPageNo(i);
                params.put("page", page);
                traders = traderMapper.getTraderListPage(params);
                resolveCertificateOutDateByList(traders);
            }
        }
    }

    /**
     * <b>Description:</b>批量处理客户资质过期<br>
     *
     * @param
     * @return
     * @Note <b>Author:calvin</b>
     * <br><b>Date:</b> 2020/2/29
     */
    private void resolveCertificateOutDateByList(List<Trader> traders) {
        if (CollectionUtils.isNotEmpty(traders)) {
            for (Trader trader : traders) {
                resolveCertificateOutDate(trader);
            }
        }
    }

    /**
     * <b>Description:</b>处理客户资质过期<br>
     *
     * @param
     * @return
     * @Note <b>Author:calvin</b>
     * <br><b>Date:</b> 2020/2/29
     */
    private void resolveCertificateOutDate(Trader trader) {
        try {
            if (trader == null || trader.getTraderId() == null || StringUtil.isEmpty(trader.getTraderName())) {
                return;
            }

            TraderCustomer traderCustomer = new TraderCustomer();
            traderCustomer.setTraderId(trader.getTraderId());
            List<TraderCertificate> certificates = traderCertificateMapper.getTraderCertificatesByTraderId(traderCustomer);
            if (CollectionUtils.isNotEmpty(certificates)) {
                for (TraderCertificate c : certificates) {
                    if (c != null && c.getEndtime() != null && c.getEndtime() < nowDateTime) {
                        XxlJobLogger.log("正在处理客户：" + trader.getTraderName() + "资质过期信息");
                        sendMessage(trader);
                        return;
                    }
                }
            }
        }catch (Exception ex){
            XxlJobLogger.log(ex);
        }
    }

    /**
     * <b>Description:</b>客户资质过期给归属销售发送消息<br>
     *
     * @param
     * @return
     * @Note <b>Author:calvin</b>
     * <br><b>Date:</b> 2020/2/29
     */
    private void sendMessage(Trader trader) {
        String[] createUser = {"njadmin", "2"};
        Map<String, String> map = new HashMap<>();
        map.put("traderName", trader.getTraderName());
        RTraderJUser queryParam = new RTraderJUser();
        queryParam.setTraderId(trader.getTraderId());
        List<Integer> userIds = new ArrayList<>();
        List<RTraderJUser> rTraderJUsers = rTraderJUserMapper.getUserTrader(queryParam);
        if (CollectionUtils.isEmpty(rTraderJUsers)) {
            return;
        }
        for (RTraderJUser r : rTraderJUsers) {
            if (r != null && r.getUserId() != null && r.getUserId() > 0) {
                userIds.add(r.getUserId());
            }
        }
        String url = ErpConst.GET_FINANCE_AND_APTITUDE_URL + trader.getTraderId();
        MessageUtil.sendMessage(102, userIds, map, url, createUser);
    }
}
