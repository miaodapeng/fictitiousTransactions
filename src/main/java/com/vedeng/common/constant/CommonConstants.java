package com.vedeng.common.constant;

/**
 * 常用常量
 * <p>
 * Title: CommonConstants
 * </p>
 * <p>
 * Description:
 * </p>
 *
 * @author Bill,Hank
 * @date 2019年3月4日
 */
public interface CommonConstants {

    /**
     * 返回成功标识
     */
    public final static Integer RESULTINFO_CODE_SUCCESS_0 = 0;

    /**
     * 返回失败标识
     */
    public final static Integer RESULTINFO_CODE_FAIL_1 = -1;

    /**
     * 南京公司ID
     */
    public final static Integer COMPANY_ID_1 = 1;

    Integer SUCCESS_CODE = 0;

    Integer FAIL_CODE = -1;

    String FAIL_MSG = "操作失败";

    String SUCCESS_MSG = "操作成功";


    public final static Integer TRADER_TYPE_1 = 1;

    public final static Integer ENABLE = 1;
    public final static Integer DISABLE = 0;

    public static final String TAKE_ADDRESS_COMMENT = "收货地址";
    public static final String INVOICE_ADDRESS_COMMENT = "收票地址";
    public static final String ADK_LOG_SEND = "1";
    public static final String ADK_LOG_RECEIVE = "2";

    /**
     * 品牌(NEW)附件信息
     */
    public static final Integer ATTACHMENT_TYPE_987 = 987;

    /**
     * 授权证明
     */
    public static final Integer ATTACHMENT_FUNCTION_988 = 988;

    /**
     * 首营附件信息模块
     */
    public static final Integer ATTACHMENT_TYPE_974 = 974;

    /**
     *  注册证附件/备案凭证附件
     */
    public static final Integer ATTACHMENT_FUNCTION_975 = 975;

    /**
     * 说明书
     */
    public static final Integer ATTACHMENT_FUNCTION_976 = 976;

    /**
     * 生产企业卫生许可证
     */
    public static final Integer ATTACHMENT_FUNCTION_977 = 977;

    /**
     * 商品SPU图片
     */
    public static final Integer ATTACHMENT_TYPE_SPU_1002 = 1002;

    /**
     * 商品SKU图片
     */
    public static final Integer ATTACHMENT_TYPE_SKU_1001 = 1001;

    /**
     * 0--销售订单
     */
    Integer SALEORDER_TYPE_ZERO = 0;
    /**
     * 2--备货订单
     */
    Integer SALEORDER_TYPE_TWO = 2;
    /**
     * 3--订货订单
     */
    Integer SALEORDER_TYPE_THREE = 3;
    /**
     * 4--经销商订单
     */
    Integer SALEORDER_TYPE_FOUR = 4;
    /**
     * 5--耗材商城
     */
    Integer SALEORDER_TYPE_FIVE = 5;

    /**
     * 物流公司 其他
     */
    Integer LOGISTICS_ID_FOUR = 4;
    /**
     * 物流公司 客户自提
     */
    Integer LOGISTICS_ID_SEVEN = 7;
    /**
     * 物流公司 中铁物流
     */
    Integer LOGISTICS_ID_EIGHT = 8;

    /**
     * 生产企业生产许可证
     */
    public static final Integer ATTACHMENT_FUNCTION_978 = 978;

    /**
     * 商标注册证
     */
    public static final Integer ATTACHMENT_FUNCTION_979 = 979;

    /**
     * 注册登记表附件
     */
    public static final Integer ATTACHMENT_FUNCTION_980 = 980;

    /**
     * 产品图片（单包装/大包装）
     */
    public static final Integer ATTACHMENT_FUNCTION_981 = 981;

    /**
     * 营业执照
     */
    public static final Integer ATTACHMENT_FUNCTION_1000 = 1000;

    /**
     * 管理类别
     */
    public static final Integer SYS_OPTION_DEFINITION_ID_967 = 967;

    /**
     * 管理类别
     * 作用域
     */
    public static final Integer SCOPE_1090 = 1090;

    /**
     * 首营附件信息
     * 作用域
     */
    public static final Integer SCOPE_1091 = 1091;

    /**
     * 产品类型
     * 作用域
     */
    public static final Integer SCOPE_1035 = 1035;

    /**
     * 存储条件
     */
    public static final Integer SCOPE_1092 = 1092;

    /**
     * 医疗类别旧国标分类
     */
    public static final Integer SCOPE_1020 = 1020;

    /**
     * 禁用状态
     */
    public static final Integer STATUS_0 = 0;

    /**
     * 启用状态
     */
    public static final Integer STATUS_1 = 1;

    /**
     * 是否删除状态 0  未删除
     */
    public static final Integer IS_DELETE_0 = 0;

    /**
     * 1 删除
     */
    public static final Integer IS_DELETE_1 = 1;

    /**
     * 1 删除操作
     */
    public static final Integer DO_DELETED_1 = 1;

    /**
     * 2 编辑操作
     */
    public static final Integer DO_EDIT_2 = 2;

    /**
     * 1 商品分类一级分类
     */
    public static final Integer CATEGORY_LEVEL_1 = 1;

    /**
     * 2 商品分类二级分类
     */
    public static final Integer CATEGORY_LEVEL_2 = 2;

    /**
     * 3 商品分类三级分类
     */
    public static final Integer CATEGORY_LEVEL_3 = 3;

    /**
     * 首营审核状态 审核通过
     */
    public static final Integer FIRST_ENGAGE_STATUS_3 = 3;

    /**
     * 发货的微信模版消息号
     */
    Integer WX_TEMPLATE_NO_FH = 1;

    /**
     * 签收的微信模版消息号
     */
    Integer WX_TEMPLATE_NO_QS = 2;

    /**
     * 发票的微信模版消息号
     */
    Integer WX_TEMPLATE_NO_FP = 3;

    /**
     * 申请加入贝登精选的微信模版消息号
     */
    Integer WX_TEMPLATE_NO_SQ = 4;

    /**
     * 首营审核状态 待审核
     */
    public static final Integer FIRST_ENGAGE_STATUS_1 = 1;

    /**
     * 首营审核状态 审和不通过
     */
    public static final Integer FIRST_ENGAGE_STATUS_2 = 2;

    /**
     * 天
     */
    public static final Integer EFFECTIVE_DAY_UNIT_1 = 1;
    /**
     * 月
     */
    public static final Integer EFFECTIVE_DAY_UNIT_2 = 2;
    /**
     * 年
     */
    public static final Integer EFFECTIVE_DAY_UNIT_3 = 3;

    public static final String PIC_SPLIT ="," ;

    /**
     * 运营信息所属类型--SPU
     */
    public static final Integer OPERATE_INFO_TYPE_SPU_1 = 1;

    /**
     * 运营信息所属类型--SKU
     */
    public static final Integer OPERATE_INFO_TYPE_SKU_2 = 2;

    /**
     * 重审
     */
    public static final Integer CHECKA_GAIN_1 = 1;

    /**
     * 套餐类型  2-根据科室
     */
    public static final Integer SET_MEAL_TYPE_2 = 2;

    /**
     * 商品审核通过状态  3
     */
    public static final Integer GOODS_CHECK_STATUS_3 = 3;
    public static final Integer CATEGORY_TYPE_YILIAO =1 ;
	
	
	 /**
     * 接口请求
     * version
     */
    public static final String INTER_VERSION = "version";

    /**
     * 请求请求version
     * v1
     */
    public static final String INTER_VERSION_VALUE = "v1";

    /**
     * 0
     */
    public static final Integer ZERO = 0;

    /**
     * 1
     */
    public static final Integer ONE = 1;

    //是否是活动商品(是)
    public static  final Integer IS_ACTIVIT_GOODS=1;



    /**
     * 资金流水交易方式 - 退还信用
     */
    Integer CAPITALBILL_TRADER_MODE_529 = 529;

    /**
     * 资金流水交易方式 - 退还余额
     */
    Integer CAPITALBILL_TRADER_MODE_530 = 530;


}
