package com.report.dao;

import com.vedeng.goods.model.Goods;
import com.vedeng.logistics.model.WarehouseGoodsOperateLog;

import javax.inject.Named;
import java.util.List;

@Named("financeReportMapper")
public interface FinanceReportMapper {
    /**
     *
     * <b>Description:</b><br> 在库列表
     * @param goods
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年10月9日 上午8:41:30
     */
    List<WarehouseGoodsOperateLog> getWglList(Goods goods);
}
