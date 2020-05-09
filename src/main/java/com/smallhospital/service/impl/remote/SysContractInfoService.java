package com.smallhospital.service.impl.remote;

import com.smallhospital.model.vo.ELContractVO;
import com.smallhospital.model.vo.ElContractSkuVO;
import com.smallhospital.service.ELContractService;
import com.smallhospital.service.ELContractSkuService;
import com.smallhospital.service.impl.remote.AbstractELThirdService;
import com.vedeng.common.util.DateUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 同步合同信息
 */
@Service
public class SysContractInfoService extends AbstractELThirdService<Integer> {

    public static final String CONTRACT_UIR = "/api/pushContract.action";

    @Autowired
    private ELContractService contractService;

    @Autowired
    private ELContractSkuService contractSkuService;

    @Override
    protected String encapsulaeRequestBodyParam(Integer contractId) {
        JSONObject data = new JSONObject();
        ELContractVO contract = contractService.findById(contractId);

        data.put("contractId",contractId);
        data.put("contractNumber",contract.getContractNumber());
        data.put("purchaserId",contract.getTraderId());
        data.put("signDate", DateUtil.convertString(contract.getSignDate(),DateUtil.DATE_FORMAT));
        data.put("contractValidityDateStart",DateUtil.convertString(contract.getContractValidityDateStart(),DateUtil.DATE_FORMAT));
        data.put("contractValidityDateEnd",DateUtil.convertString(contract.getContractValidityDateEnd(),DateUtil.DATE_FORMAT));
        data.put("remark",contract.getRemark());
        data.put("contractPic","");

        JSONArray htmx = new JSONArray();
        List<ElContractSkuVO> skuVOList = this.contractSkuService.findByContractId(contractId);

        skuVOList.forEach(sku -> {
            JSONObject skuObj = new JSONObject();
            skuObj.put("contractListId",sku.getElContractSkuId());
            skuObj.put("contractId",contractId);
            skuObj.put("productId",sku.getSkuId());
            skuObj.put("contractPrice",sku.getContractPrice());
            skuObj.put("remark",sku.getRemark());
            htmx.add(skuObj);
        });

        data.put("HTMX",htmx);
        return data.toString();
    }

    @Override
    protected String getRequestUIR() {
        return CONTRACT_UIR;
    }
}
