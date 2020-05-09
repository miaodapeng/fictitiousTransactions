package com.smallhospital.service.impl.remote;

import com.smallhospital.dto.ElWarehouseOutDTO;
import com.vedeng.common.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 出库单的请求
 */
@Service
public class SynWarehouseOutService extends AbstractELThirdService<ElWarehouseOutDTO>{

    public static final String SEND_OUT_PRODUCT = "/api/pushSupply.action";

    @Override
    protected String encapsulaeRequestBodyParam(ElWarehouseOutDTO warehouseOutDTO) {

        JSONObject data = new JSONObject();

        data.put("supplyId",warehouseOutDTO.getSupplyId());
        data.put("purchaserId",warehouseOutDTO.getPurchaserId());
        data.put("purchaserName",warehouseOutDTO.getPurchaserName());
        data.put("deliverMan", warehouseOutDTO.getDeliverMan());
        data.put("remark",warehouseOutDTO.getRemark());
        data.put("logisticsNumber",warehouseOutDTO.getLogisticsNumber());
        data.put("logisticsCompany",warehouseOutDTO.getLogisticsCompany());
        data.put("supplyNumber",warehouseOutDTO.getSupplyId());

        JSONArray htmx = new JSONArray();

        List<ElWarehouseOutDTO.GHDMX> skuVOList = warehouseOutDTO.getGHDMX();

        skuVOList.forEach(item -> {
            JSONObject skuObj = new JSONObject();
            skuObj.put("supplyListId",item.getSupplyListId());
            skuObj.put("orderListId",item.getOrderListId());
            skuObj.put("contractListId",item.getContractListId());
            skuObj.put("productId",item.getProductId());
            skuObj.put("quantitySupplied",item.getQuantitySupplied());
            skuObj.put("remark",item.getRemark());

            skuObj.put("supplyId",item.getSupplyId());
            skuObj.put("type",item.getType());

            skuObj.put("productBatchNo", StringUtil.isEmpty(item.getProductBatchNo())?"/":item.getProductBatchNo());
            skuObj.put("batchValidityDate",item.getBatchValidityDate());

            htmx.add(skuObj);
        });

        data.put("GHDMX",htmx);

        return data.toString();
    }

    @Override
    protected String getRequestUIR() {
        return SEND_OUT_PRODUCT;
    }


}
