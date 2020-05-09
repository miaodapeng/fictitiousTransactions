package com.vedeng.logistics.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.vedeng.authorization.model.User;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;
import com.vedeng.goods.model.Goods;
import com.vedeng.logistics.model.Barcode;
import com.vedeng.logistics.model.Express;
import com.vedeng.logistics.model.LendOut;
import com.vedeng.logistics.model.WarehouseGoodsOperateLog;
import com.vedeng.order.model.vo.BuyorderVo;
import com.vedeng.system.model.Attachment;

public interface WarehouseInService extends BaseService{
    /**
     * 
     * <b>Description:</b><br> 获得采购入库列表
     * @param buyorderVo
     * @param page
     * @param productUserList 
     * @return
     * @Note
     * <b>Author:</b> Administrator
     * <br><b>Date:</b> 2017年8月14日 下午5:49:30
     */
    Map<String, Object> getWarehouseinList(BuyorderVo buyorderVo, Page page, List<User> productUserList)throws Exception;
    /**
     * 
     * <b>Description:</b><br>新增二维码 
     * @param num
     * @param barcode
     * @return
     * @throws Exception
     * @Note
     * <b>Author:</b> Administrator
     * <br><b>Date:</b> 2017年8月14日 下午5:49:20
     */
    ResultInfo<?> addBarcode(Integer num, Barcode barcode) throws Exception;
    /**
     * 
     * <b>Description:</b><br> 获取有效的二维码数量
     * @param buyorderGoods
     * @return
     * @Note
     * <b>Author:</b> Michael
     * <br><b>Date:</b> 2017年8月14日 下午5:47:29
     */
    List<Barcode> getBarcode(Barcode barcode)throws Exception;
    /**
     * 入库附件上传
     * <b>Description:</b><br> 
     * @param attachment
     * @return
     * @Note
     * <b>Author:</b> Cooper
     * <br><b>Date:</b> 2018年8月1日 上午9:18:24
     */
    ResultInfo<?> saveWarehouseinAttachment(Attachment attachment);
    /**
     * 入库附件删除
     * <b>Description:</b><br> 
     * @param attachment
     * @return
     * @Note
     * <b>Author:</b> Cooper
     * <br><b>Date:</b> 2018年8月1日 下午1:51:34
     */
    ResultInfo<?> delWarehouseinAttachment(Attachment attachment);
    /**
     * 获取入库附件列表
     * <b>Description:</b><br> 
     * @param buyorderId
     * @return
     * @Note
     * <b>Author:</b> Cooper
     * <br><b>Date:</b> 2018年8月1日 上午10:20:35
     */
    List<Attachment> getAttachmentList(Attachment attachment);
    /**
     * 
     * <b>Description:</b><br>检查二维码入库是否入库 
     * @param barcodeList
     * @return
     * @throws Exception
     * @Note
     * <b>Author:</b> Cooper
     * <br><b>Date:</b> 2018年5月22日 下午4:37:00
     */
    ResultInfo<?> checkBarcode(Barcode barcode)throws Exception;
    /**
     * <b>Description:</b><br> 修改二维码/可以批量修改
     * @param barcode
     * @return
     * @Note
     * <b>Author:</b> Michael
     * <br><b>Date:</b> 2017年8月15日 下午5:19:16
     */
    ResultInfo<?> editBarcode(List<Barcode> barcodeList)throws Exception;
    /**
     * 
     * <b>Description:</b><br> 补充老数据中的二维码
     * @param bd
     * @return
     * @Note
     * <b>Author:</b> Administrator
     * <br><b>Date:</b> 2017年8月31日 上午11:32:29
     */
    ResultInfo<?> supplementBarcode(List<Barcode> barcodeList)throws Exception;
    /**
     * 
     * <b>Description:</b><br> 批量生产条形码
     * @param barcode
     * @return
     * @throws Exception
     * @Note
     * <b>Author:</b> Michael
     * <br><b>Date:</b> 2018年2月26日 下午3:10:35
     */
    ResultInfo<?> addBarcodes(List<Barcode> barcode) throws Exception;
    /**
     * 
     * <b>Description:</b><br> 根据采购单号获取未入库的产品信息和条码
     * @param buyorderVo
     * @return
     * @Note
     * <b>Author:</b> Michael
     * <br><b>Date:</b> 2018年3月1日 下午3:18:32
     */
    List<WarehouseGoodsOperateLog> getNoWarehousein(BuyorderVo buyorderVo);
    /**
     * 
     * <b>Description:</b><br> 根据采购单号获取所有入库条码信息
     * @param barcodeEnable
     * @return
     * @Note
     * <b>Author:</b> Michael
     * <br><b>Date:</b> 2018年3月9日 下午3:17:18
     */
    List<Barcode> getWarehouseInBarcode(Barcode barcodeEnable);
    
    /**
     * 
     * <b>Description:</b><br> 批量生产生成二维码(不生成图片)
     * @param barcode
     * @return
     * @throws Exception
     * @Note
     * <b>Author:</b> Michael
     * <br><b>Date:</b> 2018年7月27日 上午11:23:57
     */
    ResultInfo<?> addBarcodeNoImg(List<Barcode> barcode) throws Exception;

    /**
     *
     * <b>Description:</b><br> 发送每年的订单
     * @param express
     * @return
     * @throws Exception
     * @Note
     * <b>Author:</b> Bert
     * <br><b>Date:</b> 2018年11月12日 上午11:23:57
     */
    String sendMeinianOrders(Express express);
	/**	
	* @Description: TODO商品归还入库列表页
	* @param @param request
	* @param @param lendout
	* @param @param page
	* @param @return   
	* @return Map<String,Object>   
	* @author strange
	* @throws
	* @date 2019年9月16日
	*/
	Map<String, Object> getlendoutListPage(HttpServletRequest request, LendOut lendout, Page page);
	/**	
	* @Description: TODO(获取外接单商品信息)
	* @param @param afterSalesGoodsId
	* @param @return   
	* @return Goods   
	* @author strange
	* @throws
	* @date 2019年9月17日
	*/
	Goods getLendoutGoodsInfo(Integer lendOutId);
    /**
    *生成sku二维码
    * @Author:strange
    * @Date:16:28 2020-03-20
    */
    ResultInfo saveSkuBarcode(List<Goods> goodsList, User user);
    /**
    *筛选排除已生成过的sku
    * @Author:strange
    * @Date:16:49 2020-03-20
    */
    List<Goods> checkSkuBarcode(List<Integer> goodsIdList);
    /**
    *获取sku条码信息
    * @Author:strange
    * @Date:10:42 2020-03-23
    */
    Attachment getSkuBarcode(Integer goodsId);
    /**
    *获取sku条码信息
    * @Author:strange
    * @Date:15:05 2020-03-23
    */
    List<Attachment> getSkuBarcodeList(List<Integer> goodsIds);
}
