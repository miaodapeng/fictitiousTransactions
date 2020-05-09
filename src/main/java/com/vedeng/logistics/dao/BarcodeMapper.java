package com.vedeng.logistics.dao;

import java.util.List;
import java.util.Map;

import com.vedeng.logistics.model.Barcode;
import com.vedeng.logistics.model.WarehouseGoodsOperateLog;


public interface BarcodeMapper {
    int deleteByPrimaryKey(Integer barcodeId);

    int insert(Barcode record);

    int insertSelective(Barcode record);
    
    Barcode selectByPrimaryKey(Integer barcodeId);

    int updateByPrimaryKeySelective(Barcode record);
    /**
     * 
     * <b>Description:</b><br> 批量更新二维码
     * @param barcodes
     * @return
     * @Note
     * <b>Author:</b> Michael
     * <br><b>Date:</b> 2017年8月14日 上午11:19:46
     */
    int updateBarcodes(List<Barcode> barcodes);
    /**
     * 获取二维码信息列表
     * <b>Description:</b><br> 
     * @param barcode
     * @return
     * @Note
     * <b>Author:</b> Michael
     * <br><b>Date:</b> 2017年8月14日 下午6:21:56
     */
    List<Barcode> getBarcode(Barcode barcode);
    
    int updateByPrimaryKey(Barcode record);
    /**
     *  获取二维码信息列表
     * <b>Description:</b><br> 
     * @param barcode
     * @return
     * @Note
     * <b>Author:</b> Michael
     * <br><b>Date:</b> 2018年3月9日 下午2:19:51
     */
    List<Barcode> getWarehouseInBarcode(Barcode barcode);
    /**
     * 
     * <b>Description:</b><br> 查询条码是否入库
     * @param b
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2018年4月9日 上午8:48:41
     */
    WarehouseGoodsOperateLog getBarcodeInCnt(Barcode b);
     /**
      * 
      * <b>Description:</b><br> 批量插入条码信息
      * @param barcodeLists
      * @return
      * @Note
      * <b>Author:</b> Michael
      * <br><b>Date:</b> 2018年6月7日 上午9:30:50
      */
     Integer insertSelectiveBatch(List<Barcode> barcodeLists);
     /**
      * 
      * <b>Description:</b><br> 
      * @param barcodeLists
      * @return
      * @Note
      * <b>Author:</b> Michael
      * <br><b>Date:</b> 2018年6月7日 下午4:07:27
      */
     int updateByPrimaryKeySelectiveBatch(List<Barcode> barcodeLists);
    /**
     * 
     * <b>Description:</b><br> 售后的入库条码
     * @param barcode
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2018年7月16日 上午11:47:49
     */
	List<Barcode> getWarehouseShInBarcode(Barcode barcode);

	/**	
	* @Description: TODO(根据入库id获取条码信息)
	* @param @param warehouseGoodsOperateLogId
	* @param @return   
	* @return Barcode   
	* @author strange
	* @throws
	* @date 2019年9月24日
	*/
	Barcode getBarcodeByWarehouseGoodsOperateLogId(Integer id);

	/**	
	* @Description: TODO(通过入库条码的id查找入库单号)
	* @param @param barcode
	* @param @return   
	* @return String   
	* @author strange
	* @throws
	* @date 2019年10月17日
	*/
	String getBuyOrderNoByBarcodeId(Barcode barcode);
	/**
	* 条码获取条码信息
	* @Author:strange
	* @Date:18:15 2019-12-16
	*/
	Barcode getBarcodeByBarcode(String barcode);
}