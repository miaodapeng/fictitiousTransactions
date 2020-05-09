package com.smallhospital.service.impl.remote;

import com.smallhospital.dao.ElSkuMapper;
import com.smallhospital.dto.ELSkuDetailInfo;
import com.smallhospital.dto.ElBaseCategoryDTO;
import com.smallhospital.model.ElContractSku;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 同步分类信息
 */
@Service
public class SynCategoryInfoService extends AbstractELThirdService{

    public static final String CATEGORY_UIR = "/api/pushBaseCategoryArr.action";

    @Autowired
    private ElSkuMapper elSkuMapper;

    @Override
    protected String encapsulaeRequestBodyParam(Object param) {

        List<ElBaseCategoryDTO> categorys = elSkuMapper.getCategoryInfo();

        JSONArray skuArray = new JSONArray();

        elSkuMapper.getCategoryInfo().forEach((ElBaseCategoryDTO categoryDTO) -> {

            JSONObject skuObj = new JSONObject();

            skuObj.put("baseCategoryId",categoryDTO.getBaseCategoryId());
            skuObj.put("baseCategoryName",categoryDTO.getBaseCategoryName());
            skuObj.put("baseCategoryLevel",categoryDTO.getBaseCategoryLevel());
            skuObj.put("parentId",categoryDTO.getParentId());

            skuArray.add(skuObj);
        });

        return skuArray.toString();
    }

    @Override
    protected String getRequestUIR() {
        return CATEGORY_UIR;
    }

}
