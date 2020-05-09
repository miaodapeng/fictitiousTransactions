package com.vedeng.trader.dao;

import com.vedeng.trader.model.TraderCertificate;
import com.vedeng.trader.model.TraderCustomer;
import org.apache.ibatis.annotations.Param;

import javax.inject.Named;
import java.util.List;

@Named("traderCertificateMapper")
public interface TraderCertificateMapper {

    int insertSelective(TraderCertificate record);

    List<TraderCertificate> getTraderCertificatesByTraderId(TraderCustomer customer);

    /**
     * <b>Description:</b>根据客户标识获取资质证书<br>
     * @param
     * @return
     * @Note
     * <b>Author:calvin</b>
     * <br><b>Date:</b> 2020/3/17
     */
    List<TraderCertificate> getCertificateListByTraderId(@Param("traderId")Integer traderId);

    List<TraderCertificate> getTraderCertificates(@Param("start") Integer start, @Param("limit") Integer limit);

    void updateTraderCertificate(TraderCertificate certificate);


    TraderCertificate getTraderCertificateById(Integer id);

    void delTraderCertificateAndByTypeId(@Param("traderId") Integer traderId, @Param("typeId") Integer typeId);
}
