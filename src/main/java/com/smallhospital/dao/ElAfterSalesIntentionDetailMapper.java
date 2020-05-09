package com.smallhospital.dao;

import com.smallhospital.model.ElAfterSalesIntentionDetail;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Daniel
 * @date created in 2020/1/21 11:42
 */
public interface ElAfterSalesIntentionDetailMapper {

    void insertBatch(List<ElAfterSalesIntentionDetail> details);

    /**
     * 同一个sku的商品数量需要合并起来展示
     * @param intentionId
     * @return
     */
    List<ElAfterSalesIntentionDetail> getDetailsByIntentionId(Integer intentionId);
}
