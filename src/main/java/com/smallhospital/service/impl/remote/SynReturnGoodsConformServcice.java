package com.smallhospital.service.impl.remote;

import com.alibaba.fastjson.JSONObject;
import com.smallhospital.dto.ElAfterSaleApprovalDTO;
import com.smallhospital.model.vo.ElTraderVo;
import com.smallhospital.service.ELTraderService;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.trader.dao.TraderCertificateMapper;
import com.vedeng.trader.model.TraderCertificate;
import com.vedeng.trader.model.TraderCustomer;
import com.vedeng.trader.model.vo.TraderCustomerVo;
import com.vedeng.trader.service.TraderCustomerService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SynReturnGoodsConformServcice extends AbstractELThirdService<ElAfterSaleApprovalDTO> {

    public static final String RETURN_GOODS_CONFIRM_UIR = "/api/confirmReturn.action";

    @Override
    protected String encapsulaeRequestBodyParam(ElAfterSaleApprovalDTO afterSaleApprovalDTO) {

        JSONObject dataObj = new JSONObject();
        dataObj.put("returnId",afterSaleApprovalDTO.getReturnId());
        dataObj.put("status",afterSaleApprovalDTO.getStatus());
        dataObj.put("message",afterSaleApprovalDTO.getMessage());
        return dataObj.toJSONString();
    }

    @Override
    protected String getRequestUIR() {
        return RETURN_GOODS_CONFIRM_UIR;
    }

}
