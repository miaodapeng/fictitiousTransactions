package com.vedeng.system.dao;

import org.springframework.stereotype.Component;

import com.vedeng.system.model.QCellCore;

@Component("qCellCoreMapper")
public interface QCellCoreMapper {
    /**
     * <b>Description:</b><br> 查询号码归属信息
     * @param phone
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年7月11日 上午9:11:15
     */
    QCellCore getQCellCoreByPhone(String phone);
    
    /**
     * <b>Description:</b><br> 查询区号信息
     * @param code
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年7月20日 下午1:32:59
     */
    QCellCore getQCellCoreByCode(String code);
}