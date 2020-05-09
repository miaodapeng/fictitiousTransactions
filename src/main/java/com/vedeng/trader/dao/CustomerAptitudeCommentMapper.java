package com.vedeng.trader.dao;

import com.vedeng.trader.model.CustomerAptitudeComment;
import org.apache.ibatis.annotations.Param;

import javax.inject.Named;

@Named("customerAptitudeCommentMapper")
public interface CustomerAptitudeCommentMapper {

    /**
     * <b>Description:</b><br> 插入客户资质备注
     * @param comment 备注信息
     * @return
     * @Note
     * <b>Author:calvin</b>
     * <br><b>Date:2019/9/11</b>
     */
    int insert(CustomerAptitudeComment comment);

    /**
     * <b>Description:</b><br> 更新一条客户信息
     * @param comment 备注信息
     * @return
     * @Note
     * <b>Author:calvin</b>
     * <br><b>Date:2019/9/11</b>
     */
    int updateByTraderId(CustomerAptitudeComment comment);

    /**
     * <b>Description:</b><br> 根据客户标识查询客户备注信息
     * @param traderId 客户标识
     * @return
     * @Note
     * <b>Author:</b>
     * <br><b>Date:</b>
     */
    CustomerAptitudeComment selectByCustomerId(@Param(value = "traderId") Integer traderId);
}
