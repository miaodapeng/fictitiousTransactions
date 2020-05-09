package com.newtask.service.impl;

import com.newtask.service.ReportViewGylSkuService;
import com.vedeng.report.dao.ReportViewGylSkuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("reportViewGylSkuService")
public class ReportViewGylSkuServiceImpl implements ReportViewGylSkuService {

    @Autowired
    private ReportViewGylSkuMapper reportViewGylSkuMapper;


    @Override
    public Integer insertBatch() {
        return reportViewGylSkuMapper.insertBatch();
    }

    @Override
    public Integer trunCateViewGylSku() {
        return reportViewGylSkuMapper.trunCateViewGylSku();
    }

    @Override
    public Integer insertNotInReportStatistitcsSkuList() {
        return reportViewGylSkuMapper.insertNotInReportStatistitcsSkuList();
    }
}
