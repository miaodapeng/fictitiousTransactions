package com.newtask;

import com.newtask.util.TraderCertificate2OssUtil;
import com.vedeng.trader.dao.TraderCertificateMapper;
import com.vedeng.trader.model.TraderCertificate;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Daniel
 * @date created in 2020/2/28 9:50
 */
@JobHandler(value = "traderCertificate2OssHandler")
@Component
public class TraderCertificate2OssTask extends IJobHandler {

    public static Logger logger = LoggerFactory.getLogger(TraderCertificate2OssTask.class);

    @Autowired
    private TraderCertificateMapper traderCertificateMapper;

    @Autowired
    private TraderCertificate2OssUtil traderCertificate2OssUtil;

    @Override
    public ReturnT<String> execute(String s) throws Exception {

        //s = offset,size
        int start = 0;
        int size = 10;

        if (StringUtils.isNotBlank(s)){
            String[] array = s.split(",");
            start = Integer.parseInt(array[0]);
            if (array.length > 1){
                size = Integer.parseInt(array[1]);
            }
        }

        if (StringUtils.isNotBlank(s) && s.split(",").length == 1) {
            TraderCertificate certificate = traderCertificateMapper.getTraderCertificateById(start);
            if (certificate != null) {
                traderCertificate2OssUtil.downloadFileByStream(certificate);
            }
        } else {
            //分页批量迁移
            int count = 1;
            while (count > 0){
                List<TraderCertificate> certificates = traderCertificateMapper.getTraderCertificates(start,size);
                count = certificates.size();
                for (int i = 0; i < certificates.size(); i++) {
                    traderCertificate2OssUtil.downloadFileByStream(certificates.get(i));
                    if (i == certificates.size() - 1){
                        start = certificates.get(i).getTraderCertificateId();
                    }
                }
            }
        }
        return null;
    }

}
