package com.vedeng.common.constant;

public class TraderConstants {

    public static final int CUSTOMER_CATEGORY_UN_TRADE = 0;
    public static final int CUSTOMER_CATEGORY_NEW = 1;
    public static final int CUSTOMER_CATEGORY_LOSS = 2;
    public static final int CUSTOMER_CATEGORY_STAY = 3;

    public static final int ERP_PREIOD_DAYS = 90;
    public static final int YXG_PERIOD_DAYS = 30;
    public static final int LEVEL_YXG_PERIOD_DAYS = 30;

    public static final int ZERO = 0;
    public static final int ONE = 1;
    public static final int NEW_CUSTOMER_COUNT = 3;
    public static final int LEVEL_S = 931;
    public static final int LEVEL_A = 146;
    public static final int LEVEL_B = 147;
    public static final int LEVEL_C = 148;
    public static final int LEVEL_D = 149;
    public static final int LEVEL_E = 150;

    public static final int LEVEL_A_S_DAYS = 4;
    public static final int LEVEL_B_C_DAYS = 2;
    public static final int LEVEL_D_DAYS = 1;
    public static final int LEVEL_A_S_AMOUNT = 10000;
    public static final int LEVEL_B_C_AMOUNT = 1500;

    public static final String LEVEL_A_S_AMOUNT_STR="10000.00";
    public static final String LEVEL_B_C_AMOUNT_STR="1500.00";

    public static final String ROW_TRADER_NAME_IS_NULL="第%d行客户名称为空，请核对后重新划拨";
    public static final String ROW_USER_NAME_IS_NULL="第%d行归属销售为空，请核对后重新划拨";
    public static final String ROW_USER_IS_NOT_EXIST="第%d行归属销售不存在或不满足条件，请核对后重新划拨";
    public static final String ROW_TRADER_IS_NOT_EXIST="第%d行客户名称不存在或不满足条件，请核对后重新划拨";

    public static final Integer SALESPERSON_POSITION_TYPE=310;

    public static final String ACCOUNT_BUSINESS_CERTIFICATE="bussinessCertificate";
    public static final String ACCOUNT_SECOND_CERTIFICATE="secondCertificate";
    public static final String ACCOUNT_THIRD_CERTIFICATE="thirdCertificate";

    /**
     * 营业执照类型
     */
    public static final int TYPE_BUSINESS_CERTIFICATE = 1;
    /**
     * 二类医疗资质类型
     */
    public static final int TYPE_SECOND_MEDICAL = 2;
    /**
     * 三类医疗资质类型
     */
    public static final int TYPE_THIRD_MEDICAL = 3;

    public static final int TRANSFER_ALL_CERTIFICATES=4;

    /**
     * 贝登医疗平台
     */
    public static final int PLATFORM_BEIDENG = 1;

    public static final String CERTIFICATE_BUSINESS_NAME="营业执照";
    public static final String CERTIFICATE_SECOND_NAME="医疗资质（二类）";
    public static final String CERTIFICATE_THIRD_NAME="医疗资质（三类）";

    /**
     * 注册用户详情页URL
     */
    public static final String WEBACCOUNT_DETAIL_URL="./aftersales/webaccount/view.do?erpAccountId=";

    /**
     * 新增注册用户资质
     */
    public static final Integer ADD_WEBACCOUNT_CERTIFICATE=1;
    /**
     * 修改注册用户资质
     */
    public static final Integer UPADATE_WEBACCOUNT_CERTIFICATE=2;
    public static final Integer APTITUDE_IN_CHECK=0;
    public static final Integer APTITUDE_PASSED=1;
    public static final Integer APTITUDE_UNPASSED=2;


    public static final String APTITUDE_CHECK_TABLE_STR="T_CUSTOMER_APTITUDE";


    public static final String BUSINESS_KEY_TRADER_SUPPLIER="traderSupplierCheck_";

    public static final String SUPPLY_ORG_NAME="供应链管理部";
}
