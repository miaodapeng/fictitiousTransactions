package com.vedeng.common.constant;

public enum OrderGoodsAptitudeEnum {

    ORDER_ID_IS_NULL("订单标识为空"),
    BUSINESS_CERTIFICATE_IS_NULL("营业执照缺失"),
    BUSINESS_CERTIFICATE_DATE_OUT("营业执照过期"),
    SECOND_CERTIFICATE_IS_NULL("二类医疗资质证书缺失"),
    SECOND_CERTIFICATE_DATE_OUT("二类医疗资质证书过期"),
    THIRD_CERTIFICATE_IS_NULL("三类医疗资质证书缺失"),
    THIRD_CERTIFICATE_DATE_OUT("三类医疗资质证书过期"),
    OPERATE_CERTIFICATE_IS_NULL("医疗机构执业许可证缺失"),
    OPERATE_CERTIFICATE_DATE_OUT("医疗机构执业许可证过期"),
    SECOND_MEDICAL_IS_INVALIED("二类医疗资质匹配未通过"),
    THIRD_MEDICAL_IS_INVALIED("三类医疗资质匹配未通过"),
    ORDER_IS_NULL("订单不存在或客户字段为空"),
    CUSTOMER_IS_NULL("客户不存在或客户性质为空"),
    CATEGORY_IS_NULL("商品资质类别信息缺少");

    OrderGoodsAptitudeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    OrderGoodsAptitudeEnum(String message) {
        this.message = message;
    }

    private Integer code;
    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
