package com.newtask;

import com.alibaba.fastjson.JSON;
import com.task.model.ReadFirst;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.EmptyUtils;
import com.vedeng.firstengage.service.FirstEngageService;
import com.vedeng.soap.service.VedengSoapService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.io.Resources;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author bill
 * @description 首营信息导入
 * @date $
 */
@JobHandler(value = "syncPhp")
@Component
public class SyncPhp extends IJobHandler {

    @Autowired
    VedengSoapService vedengSoapService;

    @Transactional
    public ReturnT<String> execute(String param) throws Exception {
        for (String id : StringUtils.split(param, ",")) {
            try {
                vedengSoapService.goodsSync(Integer.parseInt(id));
            } catch (Exception e) {
                XxlJobLogger.log("同步失败" + id, e);
            }
        }
        return SUCCESS;
    }

}
