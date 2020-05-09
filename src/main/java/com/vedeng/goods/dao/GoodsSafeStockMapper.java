package com.vedeng.goods.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.vedeng.goods.model.GoodsSafeStock;

public interface GoodsSafeStockMapper {

    /**
     * <b>Description:</b><br> 增加记录
     * @param goodsSafeStock
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年11月22日 下午4:25:45
     */
    int insert(GoodsSafeStock goodsSafeStock);

    /**
     * <b>Description:</b><br> 根据产品查询
     * @param goodsId
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年11月22日 下午4:25:57
     */
    GoodsSafeStock selectByGoodsId(@Param("goodsId")Integer goodsId,@Param("companyId")Integer companyId);
    
    /**
     * <b>Description:</b><br> 批量查询
     * @param goodsIds
     * @param companyId
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年11月30日 下午4:56:33
     */
    List<GoodsSafeStock> getGoodsSafeStock(@Param("goodsIds")List<Integer> goodsIds, @Param("companyId")Integer companyId);

    /**
     * <b>Description:</b><br> 根据产品更新
     * @param goodsSafeStock
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年11月22日 下午4:26:07
     */
    int updateByGoodsId(GoodsSafeStock goodsSafeStock);

}