package com.smallhospital.service.impl;

import com.smallhospital.model.ElAfterSalesIntention;

/**
 * @author Daniel
 * @date created in 2020/1/19 9:55
 */
public interface ElAfterSaleService {

    Boolean syncAfterSaleApproval(ElAfterSalesIntention intention);
}
