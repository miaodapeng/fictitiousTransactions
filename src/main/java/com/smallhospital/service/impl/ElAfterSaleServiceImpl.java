package com.smallhospital.service.impl;

import com.smallhospital.dao.ElAfterSaleIntentionMapper;
import com.smallhospital.dto.ElAfterSaleApprovalDTO;
import com.smallhospital.dto.ElAfterSaleApprovalResultDto;
import com.smallhospital.model.ElAfterSalesIntention;
import com.smallhospital.service.impl.remote.SynAfterSaleService;
import com.smallhospital.service.impl.remote.SynReturnGoodsConformServcice;
import com.vedeng.common.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * @author Daniel
 * @date created in 2020/1/19 9:59
 */
@Service
public class ElAfterSaleServiceImpl implements ElAfterSaleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElAfterSaleServiceImpl.class);

    @Autowired
    private ElAfterSaleIntentionMapper elAfterSaleIntentionMapper;

    @Autowired
    private SynAfterSaleService synAfterSaleService;

    @Autowired
    private SynReturnGoodsConformServcice synReturnGoodsConformServcice;


    /**
     * 将小医院的售后审批结果同步到医流网
     * @return 同步结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean syncAfterSaleApproval(ElAfterSalesIntention intention){
        LOGGER.info("将小医院的售后审批结果同步到医流网，{}",intention.toString());
        intention.setUpdateTime(DateUtil.sysTimeMillis());
        ElAfterSalesIntention elAfterSalesIntention = elAfterSaleIntentionMapper.getIntentionById(intention.getElAfterSaleIntentionId());
        try {
            if (elAfterSalesIntention != null) {
                //同步医流网系统
                if (StringUtils.isNotBlank(elAfterSalesIntention.getElAfterSaleId())){
                    boolean syncResult = false;
                    if (elAfterSalesIntention.getType() == 0){
                        //退货申请
                        ElAfterSaleApprovalDTO afterSaleApprovalDTO = new ElAfterSaleApprovalDTO(elAfterSalesIntention.getElAfterSaleId(),intention.getStatus() == 1 ? 1 : 0);
                        syncResult = synReturnGoodsConformServcice.syncData(afterSaleApprovalDTO);
                    } else {
                        //订单终止申请
                        String[] elAfterSaleIdArray = elAfterSalesIntention.getElAfterSaleId().split(",");
                        for (String s : elAfterSaleIdArray) {
                            ElAfterSaleApprovalResultDto afterSaleApprovalResultDto = new ElAfterSaleApprovalResultDto(Integer.valueOf(s), intention.getStatus() == 1 ? 1 : 0);
                            syncResult = synAfterSaleService.syncData(afterSaleApprovalResultDto);
                        }
                    }
                    if (syncResult){
                        elAfterSaleIntentionMapper.updateStatus(intention);
                    }
                    return syncResult;
                } else {
                    LOGGER.error("同步售后审批结果到医流网失败，错误信息：{}","医流网订单清单id不存在");
                    return false;
                }
            }
        } catch (Exception e){
            LOGGER.error("同步售后审批结果到医流网失败");
            return false;
        }
        return false;
    }
}
