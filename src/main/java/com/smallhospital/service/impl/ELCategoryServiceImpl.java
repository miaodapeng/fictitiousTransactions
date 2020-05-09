package com.smallhospital.service.impl;

import com.smallhospital.dao.ElSkuMapper;
import com.smallhospital.service.ELCategoryService;
import com.smallhospital.service.impl.remote.SynCategoryInfoService;
import com.smallhospital.service.impl.remote.SynTradeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 同步分类信息
 */
@Service
public class ELCategoryServiceImpl implements ELCategoryService {

    @Autowired
    private SynCategoryInfoService categoryInfoService;

    @Autowired
    private ElSkuMapper skuMapper;

    @Override
    public void synCategoryInfo() {
        categoryInfoService.syncData(null);
    }

    @Override
    public List<Integer> intentionCategoryIds(){
        return skuMapper.intentionCategoryIds();
    }
}
