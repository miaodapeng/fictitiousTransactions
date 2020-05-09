package com.vedeng.goods.dao;

import org.apache.ibatis.annotations.Param;

import com.vedeng.goods.model.GoodsExpirationWarn;
import com.vedeng.goods.model.vo.GoodsExpirationWarnVo;

public interface GoodsExpirationWarnMapper {

    /**
     * <b>Description:</b><br> 新增
     * @param goodsExpirationWarn
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年12月6日 上午11:24:27
     */
    int insert(GoodsExpirationWarn goodsExpirationWarn);

    /**
     * <b>Description:</b><br> 更新
     * @param goodsExpirationWarn
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年12月6日 上午11:24:38
     */
    int updateByGoodsId(GoodsExpirationWarn goodsExpirationWarn);

	/**
	 * <b>Description:</b><br> 获取信息
	 * @param goodsId
	 * @param companyId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月6日 上午11:23:31
	 */
	GoodsExpirationWarnVo selectByGoodsId(@Param("goodsId")Integer goodsId, @Param("companyId")Integer companyId);
}