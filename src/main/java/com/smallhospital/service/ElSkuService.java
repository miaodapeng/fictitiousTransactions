package com.smallhospital.service;

import com.smallhospital.dto.ELSkuBasicInfo;
import com.smallhospital.dto.ELSkuDetailInfo;
import com.smallhospital.dto.ELSkuDto;
import com.smallhospital.model.ElSku;
import com.smallhospital.model.vo.ELSkuVO;
import com.vedeng.common.page.Page;

import java.util.List;

public interface ElSkuService {

    boolean insert(ElSku record);

    ElSku findElSkuById(Integer skuId);

    List<ELSkuVO> querylistPage(ELSkuVO skuVO, Page page);

    void batchAddSkus(List<ElSku> skuLists);

    void deleteById(Integer elSkuId);

    List<Integer> findAllSkuIds();

    List<ELSkuBasicInfo> findElSkulistPage(ELSkuDto skuDto,Page page);

    ELSkuDetailInfo findDetailSkuInfo(Integer skuId);

    boolean checkIsValidSku(Integer num);
}
