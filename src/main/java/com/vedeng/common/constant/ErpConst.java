package com.vedeng.common.constant;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;

/**
 * <b>Description:</b><br>
 * erp常量
 * 
 * @author east
 * @Note <b>ProjectName:</b> erp <br>
 *       <b>PackageName:</b> com.vedeng.common.constant <br>
 *       <b>ClassName:</b> ErpConst <br>
 *       <b>Date:</b> 2017年5月18日 下午1:35:49
 */
public class ErpConst {

	public static final Integer ZERO = 0;

	/**
	 * @Fields ONE : TODO 1
	 */
	public static final Integer ONE = 1;

	/**
	 * @Fields TWO : TODO 2
	 */
	public static final Integer TWO = 2;
	/**
	 * @Fields THREE : TODO 3
	 */
	public static final Integer THREE = 3;
	/**
	 * @Fields FOUR : TODO 4
	 */
	public static final Integer FOUR = 4;
	/**
	 * @Fields FIVE : TODO 5
	 */
	public static final Integer FIVE = 5;

	/**
	 * @Fields SCOP_EMPLOYEES : TODO 字典数据
	 */
	public static final String DATA_DICTIONARY_LIST = "sysoptiondefinition/getdatadictionarylist.htm";

	/**
	 * @Fields SCOP_EMPLOYEES : TODO 供应商分页地址
	 */
	public static final String TRADER_SUPPLIER_PAGE = "tradersupplier/gettradersupplierlistpage.htm";

	/**
	 * @Fields SCOP_EMPLOYEES : TODO 供应商置顶地址
	 */
	public static final String TRADER_SUPPLIER_TOP = "tradersupplier/toptradersupplier.htm";

	/**
	 * @Fields SCOP_EMPLOYEES : TODO 供应商禁用地址
	 */
	public static final String TRADER_SUPPLIER_DISABLED = "tradersupplier/disabledtradersupplier.htm";

	/**
	 * @Fields SCOP_EMPLOYEES : TODO 客户分页地址
	 */
	public static final String TRADER_CUSTOMER_PAGE = "tradercustomer/gettradercustomerlistpage.htm";

	/**
	 * @Fields SCOP_EMPLOYEES : TODO 医械购同步税号的地址
	 */
	public static final String SYNC_TRADER_FINANCE_TO_YXG = "/account/company/updateTaxNum.do";
	/**
	 * 获取客户财务信息
	 */

	public static final String GET_TRADER_FINANCE = "tradercustomer/getTraderFinance.htm";
	/**
	 * @Fields SCOP_EMPLOYEES : TODO 客户搜索信息查询沟通记录id集合
	 */
	public static final String GET_COMUNICATE_IDS = "tradercustomer/getcommunicaterecordids.htm";

	/**
	 * @Fields SCOP_EMPLOYEES : TODO 客户置顶地址
	 */
	public static final String TRADER_CUSTOMER_TOP = "tradercustomer/toptradercustomer.htm";

	/**
	 * @Fields SCOP_EMPLOYEES : TODO 客户禁用地址
	 */
	public static final String TRADER_CUSTOMER_DISABLED = "tradercustomer/disabledtradercustomer.htm";

	/**
	 * @Fields SCOP_EMPLOYEES : TODO 客户联系人地址列表
	 */
	public static final String TRADER_CONTACTS_ADDRESS = "tradercustomer/getcontactsaddresslist.htm";

	/**
	 * @Fields SCOP_EMPLOYEES : TODO 新增客户联系人
	 */
	public static final String TRADER_CONTACTS_SAVE = "tradercustomer/addtradercontact.htm";
	/**
	 * @Fields SCOP_EMPLOYEES : TODO 转移联系人
	 */
	public static final String SAVE_TRANSFER_CONTACTS = "tradercustomer/savetransfercontact.htm";
	/**
	 * @Fields SCOP_EMPLOYEES : TODO 禁用联系人
	 */
	public static final String SAVE_DISABLED_CONTACTS = "tradercustomer/savedisabledcontact.htm";
	/**
	 * @Fields SCOP_EMPLOYEES : TODO 设置默认联系人
	 */
	public static final String SAVE_DEFAULT_CONTACTS = "tradercustomer/savedefaultcontact.htm";

	/**
	 * @Fields SCOP_EMPLOYEES : TODO 保存客户联地址
	 */
	public static final String TRADER_ADDRESS_SAVE = "tradercustomer/savetraderaddress.htm";
	/**
	 * @Fields SCOP_EMPLOYEES : TODO 设置地址禁用启用状态
	 */
	public static final String SAVE_DISABLED_ADDRESS = "tradercustomer/savedisabledaddress.htm";
	/**
	 * @Fields SCOP_EMPLOYEES : TODO 设置默认地址
	 */
	public static final String SAVE_DEFAULT_ADDRESS = "tradercustomer/savedefaultaddress.htm";
	/**
	 * @Fields SCOP_EMPLOYEES : TODO 查询客户联系人
	 */
	public static final String TRADER_CONTACTS_QUERY = "tradercustomer/querytradercontact.htm";
	/**
	 * @Fields SCOP_EMPLOYEES : TODO 查询联系人背景
	 */
	public static final String TRADER_CONTACTS_EXPERIENCE = "tradercustomer/getcontactexperience.htm";
	/**
	 * @Fields SCOP_EMPLOYEES : TODO 删除联系人背景
	 */
	public static final String DEL_CONTACTS_EXPERIENCE = "tradercustomer/delcontactexperience.htm";
	/**
	 * @Fields SCOP_EMPLOYEES : TODO 查询客户地址
	 */
	public static final String TRADER_ADDRESS_QUERY = "tradercustomer/querytraderaddress.htm";

	/**
	 * @Fields SCOP_EMPLOYEES : TODO 获取客户的财务与资质信息
	 */
	public static final String TRADER_FINANCE_APTITUDE = "tradercustomer/gettraderfinanceandaptitude.htm";
	/**
	 * TODO 获取客户的财务与资质信息
	 */
	public static final String NEW_TRADER_FINANCE_APTITUDE = "tradercustomer/getnewtraderfinanceandaptitude.htm";
	/**
	 * @Fields SCOP_EMPLOYEES : TODO 获取交易者的账期分页信息
	 */
	public static final String GET_AMOUNT_BILL_PAGE = "tradercustomer/getamountbilllistpage.htm";
	/**
	 * @Fields SCOP_EMPLOYEES : TODO 获取交易者的交易流水分页信息
	 */
	public static final String GET_CAPITAL_BILL_PAGE = "tradercustomer/getcapitalbilllistpage.htm";
	/**
	 * @Fields SCOP_EMPLOYEES : TODO 删除供应商的银行帐号
	 */
	public static final String DEL_SUPPLIER_BANK = "tradercustomer/delsupplierbank.htm";

	/**
	 * @Fields SCOP_EMPLOYEES : TODO 保存客户的资质信息
	 */
	public static final String SAVE_APTITUDE = "tradercustomer/saveaptitude.htm";

	/**
	 * @Fields SCOP_EMPLOYEES : TODO 获取客户财务信息
	 */
	public static final String GET_CUSTOMER_FINANCE = "tradercustomer/getcustomerfinance.htm";

	/**
	 * @Fields SCOP_EMPLOYEES : TODO 保存客户财务信息
	 */
	public static final String SAVE_CUSTOMER_FINANCE = "tradercustomer/savecustomerfinance.htm";
	/**
	 * @Fields SCOP_EMPLOYEES : TODO查询客户属于分销还是终端
	 */
	public static final String GET_CUSTOMER_CATEGORY = "tradercustomer/getcustomercategory.htm";
	/**
	 * @Fields SCOP_EMPLOYEES : TODO根据traderId查询客户信息
	 */
	public static final String GET_CUSTOMER_INFO = "trader/getcustomeinfobytraderid.htm";
	/**
	 * @Fields SCOP_EMPLOYEES : TODO根据联系人主键查询联系人的详情和行业背景
	 */
	public static final String GET_CONTACT_DETAIL = "tradercustomer/getcontactdetail.htm";
	/**
	 * @Fields SCOP_EMPLOYEES : TODO保存联系人行业背景
	 */
	public static final String SAVE_CONTACT_EXPERIENCR = "tradercustomer/savecontactexperience.htm";
	/**
	 * @Fields SCOP_EMPLOYEES : TODO 保存商机
	 */
	public static final String SAVE_BUSSNESS_CHANCE = "order/bussinesschance/savebussinesschance.htm";
	/**
	 * @Fields SCOP_EMPLOYEES : TODO 查询售后商机详情
	 */
	public static final String GET_BUSSNESS_CHANCE_DETAIL = "order/bussinesschance/getbussinesschancedetail.htm";
	/**
	 * @Fields SCOP_EMPLOYEES : TODO 保存商机确认后客户的信息
	 */
	public static final String SAVE_CONFIRM_CUSTOMER = "order/bussinesschance/saveconfirmcustomer.htm";
	/**
	 * @Fields SCOP_EMPLOYEES : TODO 查询采购订单的分页列表
	 */
	public static final String GET_BUYORDER_PAGE = "order/buyorder/getbuyorderlistpage.htm";
	/**
	 * @Fields SCOP_EMPLOYEES : TODO 查询已忽略订单的分页列表
	 */
	public static final String GET_IGNORE_SALEORDER_PAGE = "order/buyorder/getignoresaleorderlistpage.htm";
	/**
	 * @Fields SCOP_EMPLOYEES : TODO 查询采购订单的详情信息
	 */
	public static final String GET_BUYORDER_DETAIL = "order/buyorder/getbuyorderdetail.htm";
	/**
	 * @Fields SCOP_EMPLOYEES : TODO 查询采购订单的详情信息(分页)
	 */
	public static final String GET_BUYORDER_DETAIL_NEW = "order/buyorder/getbuyorderdetaillistnew.htm";
	/**
	 * @Fields SCOP_EMPLOYEES : TODO 页面加载完成后通过AJAX异步补充销售订单的数据（采购订单列表详情页）
	 */
	public static final String GET_SALEORDER_NUM_AJAX = "order/buyorder/getsalebuynumbyajax.htm";
	/**
	 * @Fields SCOP_EMPLOYEES : TODO 忽略待采购订单的产品
	 */
	public static final String SAVE_IGNORE = "order/buyorder/saveignore.htm";
	/**
	 * @Fields SCOP_EMPLOYEES : TODO 新增采购订单
	 */
	public static final String ADD_BUYORDER_PAGE = "order/buyorder/addbuyorderpage.htm";
	/**
	 * @Fields SCOP_EMPLOYEES : TODO 获取加入采购订单页面的产品列表信息
	 */
	public static final String GET_GOODSVO_LIST = "order/buyorder/getgoodsvolist.htm";
	/**
	 * @Fields SCOP_EMPLOYEES : TODO 保存新增采购订单
	 */
	public static final String SAVE_ADD_BUYORDER = "order/buyorder/saveaddbuyorder.htm";
	/**
	 * @Fields SCOP_EMPLOYEES : TODO 保存编辑采购订单
	 */
	public static final String SAVE_EDIT_BUYORDER = "order/buyorder/saveeditbuyorder.htm";
	/**
	 * @Fields SCOP_EMPLOYEES : TODO 保存加入已存在的采购订单
	 */
	public static final String SAVE_HAVED_BUYORDER = "order/buyorder/savehavedbuyorder.htm";
	/**
	 * @Fields SCOP_EMPLOYEES : TODO 查询待采购订单的列表
	 */
	public static final String GET_SALESORDERGOODS_LIST = "order/buyorder/getsalesordergoodslist.htm";
	/**
	 * @Fields SCOP_EMPLOYEES : TODO 循环拉取待采购订单的列表
	 */
	public static final String GET_PURCHASE_LIST = "order/buyorder/getpurchaselist.htm";

	/**
	 * @Fields SCOP_EMPLOYEES : TODO 查询字典表的列表
	 */
	public static final String GET_SYSTEM_OPTION_LIST = "sysoptiondefinition/getsystemoptionlist.htm";
	/**
	 * @Fields SCOP_EMPLOYEES : TODO 查询字典表的对象
	 */
	public static final String GET_SYSTEM_OPTION_OBJECT = "sysoptiondefinition/getsystemoption.htm";
	/**
	 * @Fields GET_AFTERSALES_PAGE : TODO 查询售后分页信息
	 */
	public static final String GET_AFTERSALES_PAGE = "aftersales/order/getaftersalespage.htm";
	/**
	 * @Fields GET_AFTERSALES_PAGE : TODO 根据orderid查询售后列表信息
	 */
	public static final String GET_AFTERSALESLIST_BYORDERID = "aftersales/order/getaftersaleslistbyorderid.htm";
	/**
	 * @Fields GET_AFTERSALES_PAGE : TODO 保存新增售后
	 */
	public static final String SAVE_ADD_AFTERSALES = "aftersales/order/saveaddaftersales.htm";
	/**
	 * @Fields GET_AFTERSALES_PAGE : TODO 保存编辑售后
	 */
	public static final String SAVE_EDIT_AFTERSALES = "aftersales/order/saveeditaftersales.htm";
	/**
	 * @Fields GET_AFTERSALES_PAGE : TODO 查看售后的详情
	 */
	public static final String VIEW_AFTERSALES_DETAIL = "aftersales/order/viewaftersalesdetail.htm";
	/**
	 * @Fields GET_AFTERSALES_PAGE : TODO 申请审核
	 */
	public static final String APPLY_AFTERSALES_AUDIT = "aftersales/order/applyfatersalesaudit.htm";
	/**
	 * @Fields GET_AFTERSALES_PAGE : TODO 关闭订单
	 */
	public static final String SAVE_AFTERSALES_CLOSE = "aftersales/order/saveaftersalesclose.htm";
	/**
	 * @Fields GET_AFTERSALES_PAGE : TODO 保存编辑的退换货手续费
	 */
	public static final String SAVE_AFTERSALES_REFUNDFEE = "aftersales/order/saveaftersalesrefundfee.htm";
	/**
	 * @Fields GET_AFTERSALES_PAGE : TODO 保存编辑的退票信息
	 */
	public static final String SAVE_AFTERSALES_REFUNDTICKET = "aftersales/order/saveaftersalesrefundticket.htm";
	/**
	 * @Fields GET_AFTERSALES_PAGE : TODO 保存新增或更新的售后过程
	 */
	public static final String SAVE_AFTERSALES_RECORD = "aftersales/order/saveaftersalesrecord.htm";
	/**
	 * @Fields GET_AFTERSALES_PAGE : TODO 获取售后过程
	 */
	public static final String GET_AFTERSALES_RECORD = "aftersales/order/getaftersalesrecord.htm";
	/**
	 * @Fields GET_AFTERSALES_PAGE : TODO 保存编辑的安调信息
	 */
	public static final String SAVE_AFTERSALES_INSTALLSTION = "aftersales/order/saveaftersalesinstallstion.htm";
	/**
	 * @Fields GET_AFTERSALES_PAGE : TODO 新增获取工程师和维修产品
	 */
	public static final String GET_ENIGNEER_INSTALLSTION = "aftersales/order/getenigneerinstallstion.htm";
	/**
	 * @Fields GET_AFTERSALES_PAGE : TODO 编辑获取工程师和维修产品
	 */
	public static final String EDIT_ENIGNEER_INSTALLSTION = "aftersales/order/editenigneerinstallstion.htm";
	/**
	 * @Fields GET_AFTERSALES_PAGE : TODO 获取工程师分页信息
	 */
	public static final String GET_ENIGNEER_PAGE = "aftersales/order/getenigneerpage.htm";
	/**
	 * @Fields GET_AFTERSALES_PAGE : TODO 查询交易者的联系人和财务信息
	 */
	public static final String GET_CUSTOMER_CONTACTANDFINCAE = "aftersales/order/getcustomercontactandfinace.htm";
	/**
	 * @Fields GET_AFTERSALES_PAGE : TODO 保存新增的工程师与售后产品的关系
	 */
	public static final String SAVE_AFTERSALES_ENGINEER = "aftersales/order/saveaftersalesengineer.htm";
	/**
	 * @Fields GET_AFTERSALES_PAGE : TODO 保存编辑的售后安调信息
	 */
	public static final String SAVE_UPDATE_INSTALLSTION = "aftersales/order/saveupdateinstallstion.htm";
	/**
	 * @Fields GET_AFTERSALES_PAGE : TODO 查询售后安调维修申请付款信息
	 */
	public static final String GET_AFTERSALES_APPLYPAY = "aftersales/order/getaftersalesapplypay.htm";
	/**
	 * @Fields GET_AFTERSALES_PAGE : TODO 保存售后安调维修申请付款信息
	 */
	public static final String SAVE_AFTERSALES_APPLYPAY = "aftersales/order/saveaftersalesapplypay.htm";
	/**
	 * @Fields GET_AFTERSALES_PAGE : TODO 执行退款运算操作--退到余额
	 */
	public static final String EXECUTE_REFUND_OPERATION = "aftersales/order/executerefundoperation.htm";
	/**
	 * @Fields GET_AFTERSALES_PAGE : TODO 保存退货的申请付款
	 */
	public static final String SAVE_RUEFUND_APPLY_PAY = "aftersales/order/saverefundapplypay.htm";
	/**
	 * @Fields GET_AFTERSALES_PAGE : TODO 根据saleorderid查询产品信息列表
	 */
	public static final String GET_SALEORDERGOODS_BYSALEORDERID = "order/saleorder/getsaleordergoodsbysaleorderid.htm";
	/**
	 * @Fields GET_AFTERSALES_PAGE : TODO 查询echarts的数据
	 */
	public static final String GET_ECHARTSVO = "home/page/getecharts.htm";
	/**
	 * @Fields GET_AFTERSALES_PAGE : TODO 获取物流数据中心数据
	 */
	public static final String GET_GETLOGISTICECHARTS = "datacenter/mdtrader/getlogisticsechartsdatavo.htm";
	/**
	 * @Fields GET_AFTERSALES_PAGE : TODO 获取售后数据中心数据
	 */
	public static final String GET_GETAFTERSALESECHARTS = "datacenter/mdtrader/getaftersalesechartsdatavo.htm";
	/**
	 * @Fields GET_AFTERSALES_PAGE : TODO 查询echarts首页地图的客户数据
	 */
	public static final String GET_ECHARTSVO_CUSTOMER = "home/page/getechartscustomer.htm";
	/**
	 * @Fields GET_AFTERSALES_PAGE : TODO 查询售后总监的首页数据
	 */
	public static final String GET_AFTERSALES_DATAVO = "home/page/getaftersalesdatavo.htm";
	/**
	 * @Fields GET_AFTERSALES_PAGE : TODO 销售工程师个人首页-商机跟进列表
	 */
	public static final String GET_SALE_ENGINEER_DATA = "home/page/getbussinesschancevolistpage.htm";
	/**
	 * @Fields GET_AFTERSALES_PAGE : TODO 查询销售工程师个人首页数据
	 */
	public static final String GET_SALEENGINEERDATAVO = "report/homepage/getsaleengineerdatavo.htm";
	/**
	 * @Fields GET_AFTERSALES_PAGE : TODO 保存新增或修改的快递公司
	 */
	public static final String SAVE_LOGISTICE = "logistics/express/saveaddorupdatelogistics.htm";
	/**
	 * @Fields GET_AFTERSALES_PAGE : TODO 设置默认的快递公司
	 */
	public static final String SAVE_SET_DEFAULT_LOGISTICE = "logistics/express/savesetdefaultlogistics.htm";
	/**
	 * @Fields GET_AFTERSALES_PAGE : TODO 查询供应商沟通记录的主键
	 */
	public static final String GET_TAG_TRADERLIST = "tradersupplier/gettagtraderidlist.htm";
	/**
	 * @Fields GET_AFTERSALES_PAGE : TODO 保存提前采购申请
	 */
	public static final String SAVE_APPLY_PURCHASE = "order/saleorder/saveapplypurchase.htm";
	/**
	 * @Fields CURR_USER : TODO session中的user
	 */
	public static final String CURR_USER = "curr_user";

	/**
	 * @Fields SYSTEM_ERROR : TODO返回信息
	 */
	public final static String SYSTEM_ERROR_MSG = "系统错误";

	/**
	 * @Fields SYSTEM_ERROR : TODO返回信息
	 */
	public final static String USERNAME_PWD_NULL_MSG = "用户名或密码不能为空";

	public final static String USERNAME_COMPANY_NULL_MSG = "分公司不能为空";

	public final static String CODE_ERROR_MSG = "验证码错误";

	/**
	 * @Fields USERNAME_PWD_ERROR_MSG : TODO 登录错误
	 */
	public final static String USERNAME_PWD_ERROR_MSG = "用户名或密码错误";

	/**
	 * @Fields SYSTEM_ERROR : TODO返回信息
	 */
	public final static String USER_DISABLED_ERROR_MSG = "用户已被禁用";

	/**
	 * @Fields SYSTEM_ERROR : TODO返回信息
	 */
	public final static String USER_NOT_LOGIN_SYSTEM_MSG = "用户不能登陆此系统";

	/**
	 * @Fields KEY_PREFIX_SESSION : TODO session前置key
	 */
	public final static String KEY_PREFIX_SESSION = "shiro_redis_session:";

	/**
	 * @Fields KEY_PREFIX_USERID_SESSIONID : TODO usderId和sessionId存储redis的前置key
	 */
	public final static String KEY_PREFIX_USERID_SESSIONID = "redis_sessionId_userId_";

	/**
	 * @Fields REDIS_USERID_SESSIONID_TIMEOUT : TODO session过期时间8小时（临时）
	 */
	public final static Integer REDIS_USERID_SESSIONID_TIMEOUT = 8 * 60 * 60;

	/**
	 * @Fields KEY_PREFIX_DATA_DICTIONARY : TODO redis数据字典list的前置key
	 */
	public final static String KEY_PREFIX_DATA_DICTIONARY_LIST = "redis_dictionary_list:";
	public final static String KEY_PREFIX_DATA_DICTIONARY_TYPE = "redis_dictionary_type:";
	/**
	 * @Fields KEY_PREFIX_DATA_DICTIONARY : TODO redis数据字典object的前置key
	 */
	public final static String KEY_PREFIX_DATA_DICTIONARY_OBJECT = "redis_dictionary_obj:";
	/**
	 * @Fields KEY_PREFIX_DATA_DICTIONARY : TODO redis物流公司列表的前置key
	 */
	public final static String KEY_PREFIX_LOGISTICS_LIST = "redis_logistics_list:";
	/**
	 * @Fields KEY_PREFIX_DATA_DICTIONARY : TODO redis菜单的前置key
	 */
	public final static String KEY_PREFIX_MENU = "curr_user_menu_:";
	/**
	 * @Fields KEY_PREFIX_DATA_DICTIONARY : TODO redis一级菜单的前置key
	 */
	public final static String KEY_PREFIX_GROUP_MENU = "curr_group_menu_:";
	/**
	 * @Fields KEY_PREFIX_DATA_DICTIONARY : TODO redis地区列表的前置key
	 */
	public final static String KEY_PREFIX_REGION_LIST = "redis_regin_list:";
	/**
	 * @Fields KEY_PREFIX_DATA_DICTIONARY : TODO redis地区对象的前置key
	 */
	public final static String KEY_PREFIX_REGION_OBJECT = "redis_regin_obj:";
	/**
	 * @Fields KEY_PREFIX_DATA_DICTIONARY : TODO 当前用户角色前置key
	 */
	public final static String KEY_PREFIX_USER_ROLES = "redis_user_roles:";

	/**
	 * @Fields KEY_PREFIX_DATA_DICTIONARY : TODO 当前用户权限的前置key
	 */
	public final static String KEY_PREFIX_USER_PERMISSIONS = "redis_usde_permissions:";
	public final static String KEY_PREFIX_SPU_NAME = "redis_latestname:";

	/**
	 * @Fields LOGIN_AUTH_CODE : TODO 当前用户登录验证码
	 */
	public final static String LOGIN_AUTH_CODE = "login_auth_code";

	/**
	 * @Fields EXPORT_DATA_LIMIT : TODO 批量导出数据每页数据
	 */
	public static final Integer EXPORT_DATA_LIMIT = 1000;

	public static final Integer EXPORT_DATA_LIMIT_2 = 2000;

	public static final Integer EXPORT_DATA_LIMIT_3 = 3000;

	public static final Integer EXPORT_DATA_LIMIT_5 = 5000;

	/************************* redis过期时间start ***************************/
	public static final Integer REDIS_EXPORT_DATA_ = 300;
	/************************* redis过期时间end ***************************/
	/**
	 * 美年接口推送成功与失败的状态码
	 */
	public static final String SEND_DATA_SUCCESS = "S";
	public static final String SEND_DATA_FAIL = "E";

	// 语音文件处理状态，0-未处理
	public static final Integer NO_TRANSFORMATION = 0;
	// 语音文件处理状态，1-处理中
	public static final Integer WAIT_TRANSFORMATION = 1;
	// 语音文件处理状态，2-处理完成
	public static final Integer DONE_TRANSFORMATION = 2;

	public static final Integer TYPE_0 = 0;

	public static final Integer TYPE_1 = 1;
	public static final String ERP_DOMAIN = "vedeng.com";
	public static final String TAOBAO="支付宝（中国）网络技术有限公司";
	public static final String WEIXIN="财付通支付科技有限公司";
	public static final String WLOG = "Wlog_Id";
	public static final String WAREHOUSE_GOODS_OPERATE_LOG_ID ="warehouse_goods_operate_log_id";
	public static final String BARCODE ="barcode_Id";
	public static final String RETURN_BARCODE ="Return_Barcode";
	public static final String METHODLOCK_REDISKEY="MethodLock_Rediskey";


	/**
	 * erp客户资质资质
	 */
	public static final String GET_FINANCE_AND_APTITUDE_URL="./trader/customer/baseinfo.do?traderId=";
	/**
	 * erp供应商信息url
	 */
	public static final String SUPPLIER_BASE_URL="./trader/supplier/baseinfo.do?traderId=";
	/**
	*售后主体类型为销售单
	* @Author:strange
	* @Date:19:10 2019-12-05
	*/
	public static final Integer AFTER_SALEORDER_TYPE=535;
	/**
	*销售订单换货
	* @Author:strange
	* @Date:20:03 2019-12-05
	*/
	public static final Integer AFTER_SALEORDERRETURN_TYPE=540;


	/**
	 * 查询OP分类下的产品数量信息
	 */
	public static final String GET_GOODSNUM_URL="operationclassify/getGoodsNumByCategoryId?erpcategoryId=";

	/**
	 * 向OP系统中推送三级分类信息
	 */
	public static final String SAVE_CATEGORY_URL="operationclassify/pushupdateCategory?erpcategoryId=";

	/**
	 * 贝登前台认证新增资质
	 */
	public static final String BD_CETIFICATE_ADD_URL="webaccount/certificate/add.htm";

	/**
	 * 贝登前台认证更新资质
	 */
	public static final String BD_CETIFICATE_UPDATE_URL="webaccount/certificate/update.htm";

	//小医院订单type
	public static final Integer EL_ORDER_TYPE = 6;

	public static final  Integer PRINT_ORDER = 101;//出库单
	public static final  String PRINT_OUT_TYPE_F = "0";//出库单
	public static final  String  PRINT_EXPIRATIONDATE_TYPE_F= "1";//带效期出库单
	public static final  String PRINT_HC_TYPE_F = "2";//医械购出库单
	public static final  String PRINT_EXPRESS_HC_TYPE_F = "3";//医械购出库单(物流模块)
	public static final  String PRINT_KYG_TYPE_F = "4";//科研购出库单
	public static final  String PRINT_PRICE_TYPE_F = "5";//带价格出库单
	public static final  String PRINT_NOPRICE_TYPE_F = "6";//不带价格出库单
	public static final  String PRINT_EXPRESS_KYG_TYPE_F = "7";//科研购出库单(物流模块)
	public static final  String PRINT_EXPRESS_PRICE_TYPE_F = "8";//带价格出库单(物流模块)
	public static final  String PRINT_EXPRESS_NOPRICE_TYPE_F = "9";//不带价格出库单
	public static final  String PRINT_FLOWERORDER_TYPE_F = "10";//不带价格出库单


	public static final  Integer KYG_PRINT_ORDERTYPE = 4;//科研购出库单
	public static final  Integer PRICE_PRINT_ORDERTYPE = 5;//带价格出库单
	public static final  Integer NOPRICE_PRINT_ORDERTYPE = 6;//不带价格出库单

	//采购类型
	public static final Integer BUY_ORDER_TYPE=517;

	//售后类型
	public static final Integer AFTERSALES_ORDER_TYPE=518;

    //资质类型（分销）
	public static final Integer CUSTOME_RNATURE=465;
}
