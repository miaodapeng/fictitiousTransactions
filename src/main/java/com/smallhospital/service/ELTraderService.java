package com.smallhospital.service;

import com.smallhospital.dto.ELReconBillDTO;
import com.smallhospital.dto.ELTraderDto;
import com.smallhospital.model.ElAfterSalesIntention;
import com.smallhospital.model.vo.ElTraderVo;
import com.vedeng.common.page.Page;
import com.vedeng.order.model.Saleorder;
import com.vedeng.trader.model.TraderAddress;
import com.vedeng.trader.model.TraderContactGenerate;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface ELTraderService {

    List<ElTraderVo> querylistPage(ElTraderVo trader, Page page);

    void saveElTraderinfo(Saleorder saleorder, HttpSession session) throws Exception;

    ElTraderVo getElTraderByTraderId(int traderId);

    List<ElTraderVo> findCustomerByLoginId(Integer userId);

    List<ELReconBillDTO> getReconBill(ELTraderDto traderDto, Page page);

    public TraderContactGenerate getTraderDefaultContact(Integer traderId);

    /**
     * 获取客户默认的地址
     * @param traderId
     */
    public TraderAddress getTraderDefaultAddress(Integer traderId);

}
