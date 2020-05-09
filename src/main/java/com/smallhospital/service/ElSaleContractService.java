package com.smallhospital.service;

import com.smallhospital.dto.ElBaseCategoryDTO;

import java.util.List;

/**
 * @Author: Daniel
 * @Description: 小医院合同服务接口
 * @Date created in 2019/11/21 8:46 上午
 */
public interface ElSaleContractService {

    /**
     * 获取生产厂家资格证书
     * @param manufacturerId 生产厂家id
     * @return 资格证书url列表
     */
    List<String> getCredentialsOfManufacturer(Integer manufacturerId);
}
