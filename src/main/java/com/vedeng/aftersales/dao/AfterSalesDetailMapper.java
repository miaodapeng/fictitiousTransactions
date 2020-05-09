package com.vedeng.aftersales.dao;

import java.util.List;

import com.vedeng.aftersales.model.AfterSales;
import com.vedeng.aftersales.model.AfterSalesDetail;
import com.vedeng.aftersales.model.vo.AfterSalesDetailVo;


public interface AfterSalesDetailMapper {
    int deleteByPrimaryKey(Integer afterSalesDetailId);

    int insert(AfterSalesDetail record);

    int insertSelective(AfterSalesDetail record);

    AfterSalesDetail selectByPrimaryKey(Integer afterSalesDetailId);

    int updateByPrimaryKeySelective(AfterSalesDetail record);

    int updateByPrimaryKey(AfterSalesDetail record);
    
    /**
     * <b>Description:</b><br> 查询退款信息
     * @param afterSalesDetail
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年10月18日 下午2:00:32
     */
    List<AfterSalesDetailVo> getAfterSalesDetailVoList(AfterSalesDetail afterSalesDetail);
    
    /**
     * <b>Description:</b><br> 根据afterSalesId删除关联的详情
     * @param afterSalesId
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年10月20日 下午1:24:44
     */
    int delAfterSalesDetailByafterSalesId(Integer afterSalesId);
    
    /**
     * <b>Description:</b><br> 主键查询
     * @param afterSalesDetailId
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2018年4月16日 下午1:39:33
     */
    AfterSalesDetailVo getAfterSalesDetailVoById(Integer afterSalesDetailId);
    /**
     * 
     * <b>Description:</b><br> 查询售后详情
     * @param afterSales
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2018年5月25日 下午12:20:08
     */
	AfterSalesDetail selectadtbyid(AfterSales afterSales);

}