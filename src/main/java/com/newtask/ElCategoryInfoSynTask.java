package com.newtask;

import com.smallhospital.service.ELCategoryService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@JobHandler(value = "elCategoryInfoSynTask")
@Component
public class ElCategoryInfoSynTask extends IJobHandler {

    Logger logger = LoggerFactory.getLogger(InitCateOwnerHandler.class);

    @Autowired
    private ELCategoryService categoryService;

    @Override
    public ReturnT<String> execute(String s) throws Exception {
        XxlJobLogger.log("小医院同步分类信息start-----------");
        logger.info("小医院同步分类信息start-----------");
        categoryService.synCategoryInfo();
        logger.info("小医院同步分类信息end-----------");
        XxlJobLogger.log("小医院同步分类信息end-----------");
        return SUCCESS;
    }
}
