package com.vedeng.logistics.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.vedeng.authorization.model.Company;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;
import com.vedeng.goods.model.Goods;
import com.vedeng.goods.model.GoodsAttachment;
import com.vedeng.goods.model.GoodsAttribute;
import com.vedeng.goods.model.GoodsOpt;
import com.vedeng.goods.model.GoodsPackage;
import com.vedeng.goods.model.GoodsRecommend;
import com.vedeng.goods.model.GoodsSysOptionAttribute;
import com.vedeng.goods.model.vo.GoodsExpirationWarnVo;
import com.vedeng.logistics.model.*;
import com.vedeng.logistics.model.vo.WarehouseGoodsOperateLogVo;
import com.vedeng.order.model.Quoteorder;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.model.SaleorderGoods;
/**
 * 
 * <b>Description:</b><br> 出库日志
 * @author scott
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.logistics.service
 * <br><b>ClassName:</b> WarehouseGoodsOperateLogService
 * <br><b>Date:</b> 2017年8月15日 下午3:44:13
 */
public interface WarehouseGoodsOperateLogService extends BaseService {
    /**
     * 
     * <b>Description:</b><br> 出库最后的操作人
     * @param sd
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年8月15日 下午3:46:30
     */
	ResultInfo<?> getCreatorId(Saleorder sd);
    /**
     * 
     * <b>Description:</b><br> 获取商品入库的详情列表
     * @param saleorderGood
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年8月16日 下午5:35:55
     */
	List<WarehouseGoodsOperateLog> getWlog(SaleorderGoods saleorderGood);
	/**
	 * 
	 * <b>Description:</b><br> 保存商品的出库记录
	 * @param sd
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年8月24日 下午3:26:55
	 */
	ResultInfo<?> saveOutRecord(Saleorder sd, HttpSession session);
	
        
	
	/**
	 * <b>Description:</b><br> 
	 * @param warehouseGoodsOperateLog 获取出入库日志列表（不分页）
	 * @return
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2017年8月18日 上午9:51:57
	 */
	List<WarehouseGoodsOperateLog> getWGOlog(WarehouseGoodsOperateLog warehouseGoodsOperateLog)throws Exception;
   
	/**
	 * <b>Description:</b><br> 
	 * @param warehouseGoodsOperateLog 获取出入库日志列表（分页）
	 * @return
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2017年8月18日 上午9:51:57
	 */
	Map<String, Object> getWGOlogList(WarehouseGoodsOperateLog warehouseGoodsOperateLog,Page page)throws Exception;
	
	/**
	 * 
	 * <b>Description:</b><br> 新增出入库日志
	 * @param warehouseGoodsOperateLogList
	 * @return
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2017年8月21日 下午1:31:39
	 */
	ResultInfo addWlogList(List<WarehouseGoodsOperateLog> warehouseGoodsOperateLogList);
	/**
	 * 
	 * <b>Description:</b><br> 修改出入库日志是否有效
	 * @param wlogList
	 * @return
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2017年8月21日 下午6:06:08
	 */
	ResultInfo<?> editIsEnableWlog(List<WarehouseGoodsOperateLog> wlogList);
	/**
	 * 
	 * <b>Description:</b><br> 修改出入库日志验收状态
	 * @param wlogList
	 * @return
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2017年8月21日 下午6:06:08
	 */
	ResultInfo<?> editWlog(List<WarehouseGoodsOperateLog> wlogList);
	/**
	 * 
	 * <b>Description:</b><br> 获取效期列表
	 * @param warehouseGoodsOperateLog
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2017年8月24日 下午3:31:05
	 */
	Map<String, Object> getExpirationDateList(WarehouseGoodsOperateLog warehouseGoodsOperateLog, Page page);
	/**
	 * 
	 * <b>Description:</b><br> 根据id获取出库记录
	 * @param warehouseGoodsOperateLog
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2017年8月24日 下午3:31:05
	 */
	List<WarehouseGoodsOperateLog> getWLById(WarehouseGoodsOperateLog w);
	/**
	 * 
	 * <b>Description:</b><br> 在库列表
	 * @param warehouseGoodsOperateLog
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2017年8月24日 下午3:31:05
	 */
	List<WarehouseGoodsOperateLog> getWglList(Goods goods);
	/**
	 * 
	 * <b>Description:</b><br> 出入库统计
	 * @param goods
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年10月11日 下午3:20:40
	 */
	List<WarehouseGoodsOperateLog> getKindList(Goods goods);
	/**
	 * 
	 * <b>Description:</b><br> 根据厂商条码获取入库记录
	 * @param warehouseGoodsOperateLog
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年12月5日 上午10:36:52
	 */
	List<WarehouseGoodsOperateLog> getWLByBarcodeFactory(WarehouseGoodsOperateLog warehouseGoodsOperateLog);
	
	/**
	 * <b>Description:</b><br> 保存效期预警天数
	 * @param list
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月6日 上午11:08:17
	 */
	ResultInfo<?> saveUploadExpirationDay(List<GoodsExpirationWarnVo> list);
	
	/**
	     * 
	     * <b>Description:</b><br> 批量的excel添加入库
	     * @param warehouseGoodsOperateLogList
	     * @return
	     * @Note
	     * <b>Author:</b> Michael
	     * <br><b>Date:</b> 2018年3月2日 下午4:42:03
	     */
	ResultInfo<?> batchAddWarehouseinList(List<WarehouseGoodsOperateLog> warehouseGoodsOperateLogList);
	/**
	 * 
	 * <b>Description:</b><br> 批量出库保存
	 * @param list
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2018年4月26日 下午5:48:37
	 */
	ResultInfo<?> batchAddWarehouseOutList(List<WarehouseGoodsOperateLog> list);
	/**
	 * 
	 * <b>Description:</b>
	 * @param saleorder
	 * @return List<WarehouseGoodsOperateLog>
	 * @Note
	 * <b>Author：</b> scott.zhu
	 * <b>Date:</b> 2019年1月25日 下午1:48:39
	 */
    List<WarehouseGoodsOperateLog> printAllOutOrder(Saleorder saleorder);

	/**
	 * @Description: 修改库存问题
	 * @Param: [wg]
	 * @return: java.lang.Integer
	 * @Author: addis
	 * @Date: 2019/8/19
	 */
	Integer updateWarehouse(List<WarehouseGoodsOperateLogVo> wg);

	/**
	 * @Description: 查询有问题goodid
	 * @Param: [warehouseGoodsOperateLogVo]
	 * @return: java.util.List<com.vedeng.logistics.model.vo.WarehouseGoodsOperateLogVo>
	 * @Author: addis
	 * @Date: 2019/8/20
	 */
	List<WarehouseGoodsOperateLogVo> getWarehouseGoodsId(WarehouseGoodsOperateLogVo warehouseGoodsOperateLogVo);

	/**
	 * @Description: 根据GoodID修改
	 * @Param: [warehouseGoodsStatus]
	 * @return: java.lang.Integer
	 * @Author: addis
	 * @Date: 2019/8/26
	 */
	Integer updateGoodId(WarehouseGoodsStatus warehouseGoodsStatus);

	/**	
	* @Description: TODO(首营的信息 产品注册证号/者备案凭证编号 ,生产企业,生产企业许可证号/备案凭证编号	,储运条件)
	* @param @param woList
	* @param @return   
	* @return List<WarehouseGoodsOperateLog>   
	* @author strange
	* @throws
	* @date 2019年9月30日
	*/
	List<WarehouseGoodsOperateLog> getfirstRegistrationInfo(List<WarehouseGoodsOperateLog> woList);
	/**
	*判断是否为活动商品出入库记录
	* @Author:strange
	* @Date:09:30 2019-12-06
	*/
	WarehouseGoodsOperateLog updateIsActionGoods(WarehouseGoodsOperateLog wlog);
	/**
	*查询出入库记录是否有效 1 有效  0 无效
	* @Author:strange
	* @Date:13:54 2019-12-16
	*/
    Integer getWarehouseIsEnable(Integer wlogId);
	/**
	*获取条码是否可以   0 不可用  1 可用
	* @Author:strange
	* @Date:14:34 2019-12-16
	 * @param type 业务类型  1入库  2出库
	 * @param wl 获取条码信息
	*/
	Integer getBarcodeIsEnable(WarehouseGoodsOperateLog wl, int type);
	/**
	*释放redis条码锁
	 * @param list 出入库记录
	 * @param type 2为
	* @Author:strange
	* @Date:14:40 2019-12-31
	*/
	void releaseDistributedLock(List<WarehouseGoodsOperateLog> list);
	/**
	*普通商品是否超过可出库数量
	* @Author:strange
	* @Date:11:39 2020-02-03
	*/
    Boolean isEnableOutForAction(List<WarehouseGoodsOperateLog> wgList);
	/**
	*更新保质期产品,库位等信息
	* @Author:strange
	* @Date:11:31 2020-03-24
	*/
    void updateWarehouselogInfo(WarehouseGoodsOperateLog wl);
}
