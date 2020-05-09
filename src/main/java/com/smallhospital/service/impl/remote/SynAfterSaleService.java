package com.smallhospital.service.impl.remote;


import com.alibaba.fastjson.JSONObject;
import com.smallhospital.dto.ElAfterSaleApprovalResultDto;
import org.springframework.stereotype.Service;

/**
 * @author Daniel
 * @date created in 2020/1/19 10:09
 */
@Service
public class SynAfterSaleService extends AbstractELThirdService<ElAfterSaleApprovalResultDto> {

    @Override
    protected String encapsulaeRequestBodyParam(ElAfterSaleApprovalResultDto param) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("orderListId",param.getOrderListId());
        jsonObject.put("status",param.getStatus());
        return jsonObject.toJSONString();
    }

    @Override
    protected String getRequestUIR() {
        return "/api/confirmOrderEnd.action";
    }
}
