package com.smallhospital.service.impl;

import com.smallhospital.dao.ElSkuMapper;
import com.smallhospital.dto.ELSkuBasicInfo;
import com.smallhospital.dto.ELSkuDetailInfo;
import com.smallhospital.dto.ELSkuDto;
import com.smallhospital.model.ElSku;
import com.smallhospital.model.vo.ELSkuVO;
import com.smallhospital.service.ElSkuService;
import com.vedeng.common.page.Page;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ElSkuServiceImpl implements ElSkuService {

    @Autowired
    private ElSkuMapper skuMapper;

    @Override
    public boolean insert(ElSku record) {
        try {
            this.skuMapper.insertSelective(record);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public ElSku findElSkuById(Integer skuId) {
        return skuMapper.selectByPrimaryKey(skuId);
    }

    @Override
    public List<ELSkuVO> querylistPage(ELSkuVO skuVO, Page page) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sku", skuVO);
        map.put("page", page);
        return skuMapper.querylistPage(map);
    }

    @Override
    public void batchAddSkus(List<ElSku> skuLists) {
        this.skuMapper.batchAddSkus(skuLists);
    }

    @Override
    public void deleteById(Integer elSkuId) {
        skuMapper.deleteByPrimaryKey(elSkuId);
    }

    @Override
    public List<Integer> findAllSkuIds() {
        return skuMapper.findAllSkuIds();
    }

    @Override
    public List<ELSkuBasicInfo> findElSkulistPage(ELSkuDto skuDto,Page page) {
        Map<String, Object> map = new HashMap<String, Object>();

        if(!StringUtils.isEmpty(skuDto.getBaseCategoryId())){
            List<Integer> categoryIds = new ArrayList<>();
            findChildCategoryId(Integer.valueOf(skuDto.getBaseCategoryId()),categoryIds);
            map.put("categoryIds", categoryIds);
        }

        map.put("skuDto", skuDto);
        map.put("page",page);
        return skuMapper.findElSkulistPage(map);
    }

    /**
     * 递归查询当分类的子分类id
     * @param baseCategoryId
     * @param categoryIds
     */
    private void findChildCategoryId(Integer baseCategoryId, List<Integer> categoryIds) {
        categoryIds.add(baseCategoryId);
        List<Integer> childCategoryIds = skuMapper.findChildCategoryId(baseCategoryId);
        if(CollectionUtils.isEmpty(childCategoryIds)){
            return;
        }
        childCategoryIds.forEach(categoryId -> {
            findChildCategoryId(categoryId,categoryIds);
        });
    }


    @Override
    public ELSkuDetailInfo findDetailSkuInfo(Integer skuId) {
        return skuMapper.findDetailSkuInfo(skuId);
    }

    @Override
    public boolean checkIsValidSku(Integer skuId) {
        return skuMapper.findValidSku(skuId) > 0;
    }
}