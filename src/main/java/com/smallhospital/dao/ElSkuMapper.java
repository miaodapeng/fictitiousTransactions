package com.smallhospital.dao;

import com.smallhospital.dto.ELSkuBasicInfo;
import com.smallhospital.dto.ELSkuDetailInfo;
import com.smallhospital.dto.ELSkuDto;
import com.smallhospital.dto.ElBaseCategoryDTO;
import com.smallhospital.model.ElSku;
import com.smallhospital.model.vo.ELSkuVO;
import com.vedeng.logistics.model.ExpressDetail;
import com.vedeng.logistics.model.WarehouseGoodsOperateLog;
import com.vedeng.order.model.Saleorder;

import javax.inject.Named;
import java.util.List;
import java.util.Map;

@Named
public interface ElSkuMapper {

    int deleteByPrimaryKey(Integer elSkuId);

    int insert(ElSku record);

    int insertSelective(ElSku record);

    ElSku selectByPrimaryKey(Integer elSkuId);

    int updateByPrimaryKeySelective(ElSku record);

    int updateByPrimaryKey(ElSku record);

    /**
     * 获取产品分类信息列表
     * @return 分类列表
     */
    List<ElBaseCategoryDTO> getCategoryInfo();

    List<Integer> findChildCategoryId(Integer categoryId);

    /**
     * 根据saleorder_goods_id来获取商品出库信息
     * @param list 商品列表
     * @return 结果
     */
    List<WarehouseGoodsOperateLog> getWarehouseOutInfoByGoods(List<Integer> list);


    /**
     * 获取物流订单号下的物流详情
     * @param logisticsNo 物流单号
     * @return 结果
     */
    List<ExpressDetail> getExpressDetailByLogisticsNo(String logisticsNo);


    Integer getSkuByProductCompanyId(Integer productCompanyId);


    Saleorder getSaleOrderByGoodsId(Integer saleOrderGoodsId);

    List<Integer> getSaleOrderIdByGoodsIdList(List<Integer> list);

    /**
     * 根据医疗机构id获取销售id
     * @param traderId 医疗机构id
     * @return 销售id
     */
    Integer getServerIdByTraderId(Integer traderId);


    void updateSaleorderGoodsLock(List<Integer> list);



    List<ELSkuVO> querylistPage(Map<String, Object> map);

    void batchAddSkus(List<ElSku> list);

    List<Integer> findAllSkuIds();

    List<ELSkuBasicInfo> findElSkulistPage(Map<String, Object> skuDtoMap);

    ELSkuDetailInfo findDetailSkuInfo(Integer skuId);

    Integer findValidSku(Integer skuId);

    List<Integer> intentionCategoryIds();
}