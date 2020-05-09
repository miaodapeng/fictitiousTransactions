package com.vedeng.common.constant;

public class OrderGoodsAptitudeConstants {

    /**
     * 证书缺失
     */
    public static final int CERTIFICATE_IS_NULL = 1;
    /**
     * 证书过期
     */
    public static final int CERTIFICATE_DATE_OUT = 2;
    /**
     * 证书有效
     */
    public static final int CERTIFICATE_VAILD = 3;
    /**
     * 商品是医疗器械
     */
    public static final Integer GOODS_IS_INSTRUMENT = 1;
    /**
     * 客户为营利机构
     */
    public static final Integer TRADER_IS_PROFIT = 0;
    /**
     * 资质通过审核
     */
    public static final Integer APTITTUDE_IS_PASSED = 1;
    /**
     * 二类旧国标类别
     */
    public static final Integer LEVEL_SECOND_OLD_MEDICAL_CATEGORY = 239;
    /**
     * 三类旧国标类别
     */
    public static final Integer LEVEL_THIRD_OLD_MEDICAL_CATEGORY = 240;
    /**
     * 二类新国标类别
     */
    public static final Integer LEVEL_SECOND_NEW_MEDICAL_CATEGORY = 250;
    /**
     * 三类新国标类别
     */
    public static final Integer LEVEL_THIRD_NEW_MEDICAL_CATEGORY = 251;
    /**
     * 启动自动审核
     */
    public static final String KEY_AUTO_CHECK_APTITUDE = "autoCheckAptitude";

    public static final String KEY_SALE_MANAGER = "销售主管审核";
    public static final String KEY_AUTOCHECK_APTITUDE = "资质自动审核";
    public static final String KEY_OPERATE_CHECK = "运营部审核";


}
