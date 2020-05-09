package com.smallhospital.service.impl.remote;

import com.smallhospital.controller.ELContractController;
import com.smallhospital.dto.ELSkuDetailInfo;
import com.smallhospital.model.ElContractSku;
import com.smallhospital.service.ELContractSkuService;
import com.smallhospital.service.ElSkuService;
import com.smallhospital.service.impl.remote.AbstractELThirdService;
import com.vedeng.common.constant.CommonConstants;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.EmptyUtils;
import com.vedeng.common.util.StringUtil;
import com.vedeng.goods.dao.CoreSkuGenerateMapper;
import com.vedeng.goods.dao.GoodsAttachmentGenerateExtendMapper;
import com.vedeng.goods.model.CoreSkuGenerate;
import com.vedeng.goods.model.GoodsAttachment;
import com.vedeng.goods.service.VgoodsService;
import com.vedeng.system.dao.AttachmentMapper;
import com.vedeng.system.model.Attachment;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 同步合同信息
 */
@Service
public class SynProductInfoService extends AbstractELThirdService<Integer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SynProductInfoService.class);

    public static final String PRODUCT_UIR = "/api/pushProductArr.action";

    @Autowired
    GoodsAttachmentGenerateExtendMapper goodsAttachmentGenerateExtendMapper;

    @Autowired
    private ELContractSkuService contractSkuService;

    @Autowired
    @Qualifier("attachmentMapper")
    private AttachmentMapper attachmentMapper;

    @Autowired
    private ElSkuService skuService;

    @Value("${api_http}")
    protected String api_http;

    /**
     * 同步合同中的产品详细信息
     * @param contractId
     * @return
     */
    @Override
    protected String encapsulaeRequestBodyParam(Integer contractId) {

        JSONArray skuArray = new JSONArray();

        contractSkuService.findByContractId(contractId).forEach((ElContractSku sku) -> {
            JSONObject skuObj = new JSONObject();

            ELSkuDetailInfo skuDetailInfo = skuService.findDetailSkuInfo(sku.getSkuId());

            if(skuDetailInfo == null){
                LOGGER.info("同步产品接口,skuId"+sku.getSkuId()+"对应的详情为null");
            }

            skuObj.put("productId",sku.getSkuId());
            skuObj.put("contractId",contractId);
            skuObj.put("productCode",skuDetailInfo.getProductCode());
            skuObj.put("brand",skuDetailInfo.getBrand());
            skuObj.put("categoryId",skuDetailInfo.getCategoryId());
            skuObj.put("spec", precessDefaultValue(skuDetailInfo.getSpec(),"/"));
            skuObj.put("unit", precessDefaultValue(skuDetailInfo.getUnit(),"/"));
            skuObj.put("remark","");

            //处理产品图片
            //processProductPic(skuObj,sku.getSkuId());

            skuObj.put("approvalNumber",precessDefaultValue(skuDetailInfo.getApprovalNumber(),"/"));
            skuObj.put("registrationCertificateProductName",precessDefaultValue(skuDetailInfo.getRegistrationCertificateProductName(),"/"));

            skuObj.put("productName",precessDefaultValue(skuDetailInfo.getProductName(),"/"));
            skuObj.put("genericName",precessDefaultValue(skuDetailInfo.getGenericName(),"/"));

            skuObj.put("productionAddress",precessDefaultValue(skuDetailInfo.getProductionAddress(),"/"));
            skuObj.put("validityDate",precessDefaultValue(skuDetailInfo.getValidityDate(),"0"));
            skuObj.put("companyName",precessDefaultValue(skuDetailInfo.getCompanyName(),"/"));
            skuObj.put("enterpriseLicenseRecordNumber",precessDefaultValue(skuDetailInfo.getEnterpriseLicenseRecordNumber(),"/"));
            skuObj.put("registeredAddress",precessDefaultValue(skuDetailInfo.getRegisteredAddress(),"/"));
            skuObj.put("manufacturerId",precessDefaultValue(skuDetailInfo.getManufacturerId(),("V"+ sku.getSkuId())));

            skuObj.put("companyAbbr","/");
            skuObj.put("uniformCreditCode","/");
            skuObj.put("packageUnit","/");

            skuObj.put("validityDateStart", formatDate(skuDetailInfo.getIssuingDate()));
            skuObj.put("validityDateEnd", formatDate(skuDetailInfo.getEffectiveDate()));


            //处理证照图片
            processCrtificatePicAddress(skuObj,skuDetailInfo.getRegistrationNumberId());

            //有批准文号传1 否则传2
            skuObj.put("documentType",StringUtil.isEmpty(skuDetailInfo.getApprovalNumber()) ? "2" : "1");

            skuArray.add(skuObj);
        });

        return skuArray.toString();
    }


    private String formatDate(Long dateTime){
        if(dateTime == null || dateTime == 0L){
            return "";
        }
        return DateUtil.convertString(dateTime,"yyyy-MM-dd");
    }

    /**
     * 处理产品的图片
     * @param skuObj
     * @param skuId
     */
    private void processProductPic(JSONObject skuObj, Integer skuId) {

        if(skuId == null){
            return;
        }

        List<String> picUrls = new ArrayList<>();

        GoodsAttachment goodsAttachment = new GoodsAttachment();
        goodsAttachment.setAttachmentType(CommonConstants.ATTACHMENT_TYPE_SKU_1001);
        goodsAttachment.setStatus(CommonConstants.STATUS_1);
        goodsAttachment.setGoodsId(skuId);

        List<GoodsAttachment> skuPictures = goodsAttachmentGenerateExtendMapper.getGoodsAttachment(goodsAttachment);

        skuPictures.stream().forEach(sta ->{
            picUrls.add("http://"+sta.getDomain()+sta.getUri());
        });

        skuObj.put("productPic",picUrls);
    }


    public String precessDefaultValue(String value,String defaultValue){
        return StringUtil.isEmpty(value) ? defaultValue : value;
    }

    /**
     * 处理产品的证照图片地址
     * @param skuObj
     */
    private void processCrtificatePicAddress(JSONObject skuObj,Integer registerNumberId) {

        if(registerNumberId == null){
            return;
        }

        // 所有附件
        List<Integer> attachmentFunction = Arrays.asList(
                // 注册证附件/备案凭证附件
                CommonConstants.ATTACHMENT_FUNCTION_975,
                // 说明书
                CommonConstants.ATTACHMENT_FUNCTION_976,
                // 营业执照
                CommonConstants.ATTACHMENT_FUNCTION_1000,
                // 生产企业卫生许可证
                CommonConstants.ATTACHMENT_FUNCTION_977,
                // 生产企业生产许可证
                CommonConstants.ATTACHMENT_FUNCTION_978,
                // 商标注册证
                CommonConstants.ATTACHMENT_FUNCTION_979,
                // 注册登记表附件
                CommonConstants.ATTACHMENT_FUNCTION_980,
                // 产品图片（单包装/大包装）
                CommonConstants.ATTACHMENT_FUNCTION_981
        );

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("attachmentType", CommonConstants.ATTACHMENT_TYPE_974);
        paramMap.put("attachmentFunction", attachmentFunction);
        paramMap.put("registrationNumberId", registerNumberId);

        List<Attachment> attachments = attachmentMapper.getAttachmentsList(paramMap);

        // 注册证附件/备案凭证附件
        List<Attachment> zczAttachments = new ArrayList<>();

        // 产品图片（单包装/大包装）
        List<Attachment> picAttachments = new ArrayList<>();

        //生产厂家图片
        List<Attachment> manufacturerAttachments = new ArrayList<>();

        for (int i = 0; i < attachments.size(); i++) {

            Attachment attachment = attachments.get(i);

            if(EmptyUtils.isEmpty(attachment.getUri())){
                continue;
            }

            // 将注册证分组
            if(CommonConstants.ATTACHMENT_FUNCTION_975.equals(attachment.getAttachmentFunction())){
                zczAttachments.add(attachment);
            }

            // 产品图片（单包装/大包装）
            if(CommonConstants.ATTACHMENT_FUNCTION_981.equals(attachment.getAttachmentFunction())){
                picAttachments.add(attachment);
            }

            // 营业执照
            if(CommonConstants.ATTACHMENT_FUNCTION_1000.equals(attachment.getAttachmentFunction())){
                manufacturerAttachments.add(attachment);
            }
            // 说明书
            if(CommonConstants.ATTACHMENT_FUNCTION_976.equals(attachment.getAttachmentFunction())){
                manufacturerAttachments.add(attachment);
            }
            // 生产企业卫生许可证
            if(CommonConstants.ATTACHMENT_FUNCTION_977.equals(attachment.getAttachmentFunction())){
                manufacturerAttachments.add(attachment);
            }
            // 生产企业生产许可证
            if(CommonConstants.ATTACHMENT_FUNCTION_978.equals(attachment.getAttachmentFunction())){
                manufacturerAttachments.add(attachment);
            }
            // 商标注册证
            if(CommonConstants.ATTACHMENT_FUNCTION_979.equals(attachment.getAttachmentFunction())){
                manufacturerAttachments.add(attachment);
            }
            // 注册登记表附件
            if(CommonConstants.ATTACHMENT_FUNCTION_980.equals(attachment.getAttachmentFunction())){
                manufacturerAttachments.add(attachment);
            }
        }

        // 注册证附件/备案凭证附件
        List<String> zczUrls = new ArrayList<>();
        zczAttachments.stream().forEach(attachment -> {
            zczUrls.add(this.api_http+attachment.getDomain()+attachment.getUri());
        });
        skuObj.put("crtificatePicAddress",zczUrls);

        // 产品图片（单包装/大包装）
        List<String> picUrls = new ArrayList<>();
        picAttachments.stream().forEach(attachment -> {
            picUrls.add(this.api_http+attachment.getDomain()+attachment.getUri());
        });
        skuObj.put("productPic",picUrls);

        //生产厂家图片
        List<String> manufacturerPicUrls = new ArrayList<>();
        manufacturerAttachments.stream().forEach(attachment -> {
            manufacturerPicUrls.add(this.api_http+attachment.getDomain()+attachment.getUri());
        });
        skuObj.put("manufacturerPicUrls",manufacturerPicUrls);
    }

    @Override
    protected String getRequestUIR() {
        return PRODUCT_UIR;
    }
}
