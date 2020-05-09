package com.smallhospital.dao;

import com.smallhospital.model.ElAfterSalesIntention;
import java.util.List;
import java.util.Map;

public interface ElAfterSaleIntentionMapper {

    void insert(ElAfterSalesIntention intention);

    void insertBatch(List<ElAfterSalesIntention> intentions);

    List<ElAfterSalesIntention> getIntentionsUnHandledlistPage(Map<String,Object> map);

    void updateStatus(ElAfterSalesIntention intention);

    ElAfterSalesIntention getIntentionById(Integer intentionId);
}
