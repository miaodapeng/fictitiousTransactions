package com.smallhospital.service.impl;

import com.smallhospital.controller.ElSaleOrderController;
import com.smallhospital.dao.ElSkuMapper;
import com.smallhospital.dto.ElBaseCategoryDTO;
import com.smallhospital.service.ElSaleContractService;
import com.vedeng.common.constant.CommonConstants;
import com.vedeng.system.dao.AttachmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Daniel
 * @Description: 小医院合同服务实现类
 * @Date created in 2019/11/21 11:22 上午
 */
@Service
public class ElSaleContractServiceImpl implements ElSaleContractService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElSaleContractServiceImpl.class);

    @Autowired
    private ElSkuMapper elSkuMapper;

    @Autowired
    private AttachmentMapper attachmentMapper;


    /**
     * 获取生产厂家资格证书
     * @param productCompanyId 生产厂家id
     * @return 资格证书url列表
     */
    @Override
    public List<String> getCredentialsOfManufacturer(Integer productCompanyId) {

        //判断是否是小医院允许的商品的生产厂家
        if (elSkuMapper.getSkuByProductCompanyId(productCompanyId) == 0) {
            LOGGER.info("生产厂家productCompanyId="+productCompanyId+",非小医院的生产厂家");
            return Collections.emptyList();
        }
        //根据商家id查询相关的资格证书
        List<Integer> attachmentFuctions = Arrays.asList(
                // 注册证附件/备案凭证附件
                CommonConstants.ATTACHMENT_FUNCTION_975,
                // 营业执照
                CommonConstants.ATTACHMENT_FUNCTION_1000
                // 生产企业卫生许可证
                //CommonConstants.ATTACHMENT_FUNCTION_977,
                // 生产企业生产许可证
                //CommonConstants.ATTACHMENT_FUNCTION_978
                // 注册登记表附件
                //CommonConstants.ATTACHMENT_FUNCTION_980
        );
        return attachmentMapper.getAttachmentsByProductCompanyId(productCompanyId,attachmentFuctions)
                    .stream()
                    .map(attachment -> "http://" + attachment.getDomain() + attachment.getUri())
                    .collect(Collectors.toList());
    }


}

