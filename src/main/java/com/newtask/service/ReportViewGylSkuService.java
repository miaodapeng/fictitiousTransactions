package com.newtask.service;

public interface ReportViewGylSkuService {
    /**
     * @Description: 批量插入
     * @Author: Cooper.xu
     * @Date: 2020年01月16日
     * @return
     */
    Integer insertBatch();

    /**
     * @Description: 清空T_REPORT_VIEW_GYL_BH表中数据，使用truncate方式，提高效率
     * @param
     * @Author: Cooper.xu
     * @Date: 2020年01月16日
     * @return
     */
    Integer trunCateViewGylSku();

    /**
     * @Description: 插入未销售数量中不在已销售的表中的数据列表
     * @Author: Cooper.xu
     * @Date: 2020年01月16日
     * @return
     */
    Integer insertNotInReportStatistitcsSkuList();
}
