package com.vedeng.trader.dao;


import com.vedeng.trader.model.WebAccountCertificate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WebAccountCertificateMapper {

    List<WebAccountCertificate> getCertificateList(WebAccountCertificate webAccountCertificate);

    void deleteCertificateByWebAccountId(@Param("webAccountId") int webAccountId);

    int deleteByPrimaryKey(Integer webAccountCertificateId);
}