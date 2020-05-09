package com.smallhospital.controller;

import com.alibaba.fastjson.JSON;
import com.smallhospital.dto.*;
import com.smallhospital.model.ElContractSku;
import com.smallhospital.model.vo.ELContractVO;
import com.smallhospital.service.*;
import com.vedeng.authorization.model.User;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.util.DateUtil;
import com.vedeng.goods.dao.CoreSkuGenerateMapper;
import com.vedeng.goods.dao.CoreSpuGenerateMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Daniel
 * @Description: 小医院合同、产品控制类
 * @Date created in 2019/11/20 6:48 下午
 */
@RestController
@RequestMapping("/el")
public class ElSaleContractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElSaleOrderController.class);

    public static final Integer SYSTEM_CREATOR_USER = 0;

    public static final Integer SYN_USER_SUCCESS = 1;
    public static final Integer SYN_USER_ERROR = 2;


    public static final Integer CONFIRM_SUCCESS = 1;

    @Autowired
    private ElSaleContractService elSaleContractService;

    @Autowired
    private ELContractService contractService;

    @Autowired
    private ELContractSkuService contractSkuService;

    @Autowired
    private ELTraderService traderService;

    @Autowired
    private ElSkuService skuService;

    @Autowired
    @Qualifier("coreSkuGenerateMapper")
    private CoreSkuGenerateMapper coreSkuGenerateMapper;

    @Autowired
    @Qualifier("coreSpuGenerateMapper")
    private CoreSpuGenerateMapper coreSpuGenerateMapper;

    /**
     * 小医院系统推送用户意向合同申请，将该意向合同保存到T_EL_CONTRACT表中，状态为0
     * @return 结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "/contract/intention")
    public ElResultDTO addElContractIntention(HttpServletRequest request,@RequestBody IntentionContractVo contractVo){

        if(contractVo == null || contractVo.getHospitalId() == null || contractVo.getProductId() == null){
            return ElResultDTO.error("参数不能为空");
        }

        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);

        //创建意向合同
        ELContractVO contract = new ELContractVO();
        contract.setTraderId(contractVo.getHospitalId());
        contract.setOwner(SYSTEM_CREATOR_USER);
        contract.setCreator(SYSTEM_CREATOR_USER);
        contract.setUpdator(SYSTEM_CREATOR_USER);
        contract.setStatus(0);
        int contractId = contractService.saveContractInfo(contract);

        List<ElContractSku> skuLists = new ArrayList<ElContractSku>();// 保存Excel中读取的数据

        for(int i = 0;i < contractVo.getProductId().length;i++){
            // 获取excel的值
            ElContractSku sku = new ElContractSku();
            sku.setContractId(contractId);
            sku.setAddTime(DateUtil.gainNowDate());
            sku.setUpdateTime(DateUtil.gainNowDate());
            sku.setCreator(SYSTEM_CREATOR_USER);
            sku.setUpdator(SYSTEM_CREATOR_USER);
            sku.setSkuId(contractVo.getProductId()[i]);
            skuLists.add(sku);
        }
        contractSkuService.batchAddContractSkus(skuLists);

        return ElResultDTO.ok();
    }

    /**
     * 合同确认
     * @return 结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "/contract/confirm")
    public ElResultDTO confirm(@RequestBody ContractConfirmDto confirmDto){
        try{

            if(confirmDto == null || confirmDto.getContractId() == null){
                return ElResultDTO.error("参数不能为空");
            }

            ELContractVO contractVO = new ELContractVO();
            contractVO.setElContractId(confirmDto.getContractId());
            contractVO.setConfirmStatus(confirmDto.getConfirm());

            contractService.updateContract(contractVO);

        }catch (Exception e){
            return ElResultDTO.error("合同确认失败");
        }
        return ElResultDTO.ok();
    }

    /**
     * 合同产品推送
     * @param confirmDto
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/contractSku/push")
    public ElResultDTO pushSku(@RequestBody ContractPushDto confirmDto){
        try{

            if(confirmDto == null || confirmDto.getContractId() == null){
                return ElResultDTO.error("参数不能为空");
            }


            LOGGER.info("合同产品推送回调结果入参:" + JSON.toJSONString(confirmDto));

            ELContractVO contractVO = new ELContractVO();
            contractVO.setElContractId(confirmDto.getContractId());
            contractVO.setProductSynStatus(confirmDto.getStatus( )== 1? SYN_USER_SUCCESS : SYN_USER_ERROR);

            contractService.updateContract(contractVO);

        }catch (Exception e){
            return ElResultDTO.error("合同推送失败");
        }
        return ElResultDTO.ok();
    }

    /**
     * 合同推送
     * @param confirmDto
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/contract/push")
    public ElResultDTO push(@RequestBody ContractPushDto confirmDto){
        try{

            LOGGER.info("合同推送回调结果入参:" + JSON.toJSONString(confirmDto));

            if(confirmDto == null || confirmDto.getContractId() == null){
                return ElResultDTO.error("参数不能为空");
            }

            ELContractVO contractVO = new ELContractVO();
            contractVO.setElContractId(confirmDto.getContractId());
            contractVO.setContractSynStatus(confirmDto.getStatus( )== 1? SYN_USER_SUCCESS : SYN_USER_ERROR);

            contractService.updateContract(contractVO);

        }catch (Exception e){
            return ElResultDTO.error("合同推送失败");
        }
        return ElResultDTO.ok();
    }

    /**
     * 小医院系统获取生产商家资格证书
     * @param credentialsDto 商家
     * @return 结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "/manufacturer/credentials")
    public ElResultDTO getCredentialsOfManufacturer(@RequestBody CredentialsDto credentialsDto){
        if (credentialsDto.getManufacturerId() == null){
            return ElResultDTO.error("参数错误");
        }

        LOGGER.info("生产厂家证书接口入参：" + JSON.toJSONString(credentialsDto));

        ElResultDTO dto = ElResultDTO.ok();
        dto.setData(elSaleContractService.getCredentialsOfManufacturer(credentialsDto.getManufacturerId()));

        LOGGER.info("生产厂家证书接口响应：" + JSON.toJSONString(dto));
        return dto;
    }
}