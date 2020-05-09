package com.smallhospital.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.smallhospital.controller.ElSaleOrderController;
import com.smallhospital.dao.ElAfterSaleIntentionMapper;
import com.smallhospital.dao.ElTraderMapper;
import com.smallhospital.dto.ELReconBillDTO;
import com.smallhospital.dto.ELTraderDto;
import com.smallhospital.dto.StatementBillVo;
import com.smallhospital.model.ElAfterSalesIntention;
import com.smallhospital.model.ElTrader;
import com.smallhospital.model.vo.ElTraderVo;
import com.smallhospital.service.ELTraderService;
import com.smallhospital.service.impl.remote.SynTradeInfoService;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.DateUtil;
import com.vedeng.order.model.Saleorder;
import com.vedeng.trader.dao.TraderAddressMapper;
import com.vedeng.trader.dao.TraderContactGenerateMapper;
import com.vedeng.trader.model.TraderAddress;
import com.vedeng.trader.model.TraderContact;
import com.vedeng.trader.model.TraderContactGenerate;
import com.vedeng.trader.service.TraderCustomerService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ELTraderServiceImpl extends BaseServiceimpl implements ELTraderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ELTraderServiceImpl.class);

    @Autowired
    private ElTraderMapper traderMapper;

    @Autowired
    private TraderContactGenerateMapper traderContactGenerateMapper;

    @Autowired
    private TraderAddressMapper traderAddressMapper;

    @Autowired
    @Lazy
    private SynTradeInfoService synTradeInfoService;

    @Autowired
    private TraderCustomerService traderCustomerService;


    @Override
    public List<ElTraderVo> querylistPage(ElTraderVo trader, Page page) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("trader", trader);
        map.put("page", page);
        return traderMapper.querylistPage(map);
    }

    /**
     * 新增医疗客户信息
     *
     * @param saleorder
     * @param session
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveElTraderinfo(Saleorder saleorder, HttpSession session) throws Exception {
        User user = (User) session.getAttribute(ErpConst.CURR_USER);
        ElTrader trader = this.createELTrader(user, saleorder);
        this.traderMapper.insertSelective(trader);

        //同步客户信息给医流网
        boolean synResult = synTradeInfoService.syncData(saleorder.getTraderId());

        //推送失败,抛个异常出去
        if (!synResult) {
            throw new Exception("推送小医院客户失败");
        }
    }

    @Override
    public ElTraderVo getElTraderByTraderId(int traderId) {
        return traderMapper.getElTraderByTraderId(traderId);
    }

    @Override
    public List<ElTraderVo> findCustomerByLoginId(Integer userId) {
        return traderMapper.findCustomerByLoginId(userId);
    }

    /**
     * 获取对账单
     *
     * @param traderDto
     * @param page
     * @return
     */
    @Override
    public List<ELReconBillDTO> getReconBill(ELTraderDto traderDto, Page page) {

        List<ELReconBillDTO> reconBillList = new ArrayList<ELReconBillDTO>();

        String url = httpUrl + "report/sale/exportStatementData.htm";

        TypeReference<ResultInfo<StatementBillVo>> typeRef = new TypeReference<ResultInfo<StatementBillVo>>() {
        };

        try {
            ResultInfo<StatementBillVo> result = (ResultInfo<StatementBillVo>) HttpClientUtils.post(url, traderDto, clientId, clientKey, typeRef, page);

            if (null != result && result.getCode() == 0 && result.getData() != null && !CollectionUtils.isEmpty(result.getData().getOrderList())) {
                result.getData().getOrderList().forEach(statementBillVo -> {
                    ELReconBillDTO elReconBillDTO = new ELReconBillDTO();
                    reconBillList.add(elReconBillDTO);

                    elReconBillDTO.setOrderNo(statementBillVo.getOrderNo());
                    elReconBillDTO.setEffectiveDate(statementBillVo.getEffectiveDate());
                    elReconBillDTO.setActualAmount(statementBillVo.getActualAmount());
                    elReconBillDTO.setCollectionAmount(statementBillVo.getCollectionAmount());
                    elReconBillDTO.setAccountAmount(statementBillVo.getAccountAmount());
                    elReconBillDTO.setBillDate(statementBillVo.getBillDate());
                    elReconBillDTO.setTicketNo(statementBillVo.getTicketNo());
                    elReconBillDTO.setRepaymentDate(statementBillVo.getRepaymentDate());
                });
            }
        } catch (Exception e) {
            LOGGER.error("获取对账单数据异常", e);
        }

        return reconBillList;
    }

    /**
     * 获取客户默认的联系人
     *
     * @param traderId
     */
    public TraderContactGenerate getTraderDefaultContact(Integer traderId) {
        if (traderId == null) {
            return null;
        }

        return this.traderContactGenerateMapper.getTraderDefaultContact(traderId);
    }

    /**
     * 获取客户默认的联系人
     *
     * @param traderId
     */
    public TraderAddress getTraderDefaultAddress(Integer traderId) {
        if (traderId == null) {
            return null;
        }

        return this.traderAddressMapper.getTraderDefaultAdressInfo(traderId);
    }


    private ElTrader createELTrader(User user, Saleorder saleorder) {

        //TraderContact traderContact = traderCustomerService.getTraderContactById(saleorder.getTraderContactId());

        ElTrader trader = new ElTrader();
        Long time = DateUtil.sysTimeMillis();
        trader.setTraderId(saleorder.getTraderId());

        TraderContactGenerate traderContract = getTraderDefaultContact(saleorder.getTraderId());
        if (traderContract != null) {
            trader.setContactName(traderContract.getName());
            trader.setContactNumber1(traderContract.getTelephone());
            trader.setContactNumber2(traderContract.getMobile());
            trader.setEmail(traderContract.getEmail());
        }

        TraderAddress traderAddress = this.getTraderDefaultAddress(saleorder.getTraderId());
        if (traderAddress != null) {
            trader.setAddress(super.getAddressByAreaId(traderAddress.getAreaId()) + " " + traderAddress.getAddress());
        }

        trader.setAddTime(time);
        trader.setUpdateTime(time);
        trader.setCreator(user.getUserId());
        trader.setUpdator(user.getUserId());
        trader.setOwner(user.getUserId());
        return trader;
    }
}
