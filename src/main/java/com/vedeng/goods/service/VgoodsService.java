package com.vedeng.goods.service;

import com.vedeng.authorization.model.User;
import com.vedeng.common.exception.ShowErrorMsgException;
import com.vedeng.common.model.FileInfo;
import com.vedeng.common.page.Page;
import com.vedeng.goods.command.SkuAddCommand;
import com.vedeng.goods.command.SpuAddCommand;
import com.vedeng.goods.command.SpuSearchCommand;
import com.vedeng.goods.model.CoreSkuGenerate;
import com.vedeng.goods.model.CoreSpuGenerate;
import com.vedeng.goods.model.LogCheckGenerate;
import com.vedeng.goods.model.vo.BaseAttributeVo;
import com.vedeng.goods.model.vo.CoreSkuBaseVO;
import com.vedeng.goods.model.vo.CoreSpuBaseVO;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public interface VgoodsService {


	void updateSpuOperateInfoFlag(Integer optInfoId,Integer spuId);
	void updateSkuOperateInfoFlag(Integer optInfoId,Integer skuId);
	/**
	 * SKU每页的个数
	 */
	final static int MAX_SKU_PAGE_SIZE = 5;

	/**
	 * 修改Spu
	 * 
	 * @param spuCommand
	 * @throws ShowErrorMsgException
	 */
	void saveSpu(SpuAddCommand spuCommand) throws ShowErrorMsgException;

	/**
	 * spu列表
	 * 
	 * @param spuCommand
	 * @param page
	 */
	List<CoreSpuBaseVO> selectSpuListPage(SpuSearchCommand spuCommand, Page page) throws Exception;

	/**
	 * SPU详情页初始化
	 * 
	 * @param spuCommand
	 * @throws ShowErrorMsgException
	 */
	void initSpu(SpuAddCommand spuCommand) throws Exception;

	/**
	 * SKU详情页初始化
	 * 
	 * @param skuAddCommand
	 * @throws ShowErrorMsgException
	 */
	CoreSkuGenerate initSku(SkuAddCommand skuAddCommand ) throws Exception;

	/**
	 * 提交审核操作
	 * 
	 * @param spuCommand
	 * @throws ShowErrorMsgException
	 */
	void submitToCheck(SpuAddCommand spuCommand) throws ShowErrorMsgException;

	/**
	 * 审核商品
	 * 
	 * @param spuCommand
	 * @throws ShowErrorMsgException
	 */
	void checkSpu(SpuAddCommand spuCommand) throws ShowErrorMsgException;

	/**
	 * 按照商品的审核状态查询商品
	 * 
	 * @param checkStatus
	 * @return
	 */
	Integer countSpuByCheckStatus(Integer checkStatus);

	/**
	 * 删除spu
	 * 
	 * @throws ShowErrorMsgException
	 */
	void deleteSpu(SpuSearchCommand spuCommand) throws ShowErrorMsgException;

	/**
	 * 删除sku
	 * 
	 * @throws ShowErrorMsgException
	 */
	void deleteSku(SpuSearchCommand spuCommand) throws ShowErrorMsgException;

	/**
	 * ajax获取sku
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<CoreSkuBaseVO> selectSkuListPage(SpuSearchCommand spuCommand,  Page page) throws Exception;

	/**
	 * 导出
	 * 
	 * @param spuIds
	 * @return
	 * @throws Exception
	 */
	List<CoreSpuBaseVO> exportSpuList(String spuIds) throws Exception;

	/**
	 *获取所有spu下待选择的属性与属性值
	 * @param spuId
	 * @return
	 */
	List<BaseAttributeVo> selectAllAttributeBySkuId(Integer spuId,Integer skuId) ;

	/**
	 *获取所有spu下已经选择的属性与属性值
	 * @param spuId
	 * @return
	 */
	List<BaseAttributeVo> selectCheckedAttributeBySpuId(Integer spuId) ;


	void saveSku(SkuAddCommand command, CoreSkuGenerate skuGenerate) throws UnsupportedEncodingException;

    List<BaseAttributeVo> getAttributeInfoByCategoryId(Integer categoryId);

    List<LogCheckGenerate> listSpuCheckLog(Integer spuId);

    void saveTempSku(SkuAddCommand spuCommand);


	List<Map<String, Object>>  searchFirstEngageListPage(String searchValue, Page page);

	/**
	 * 审核sku
	 * @param command
	 */
	void checkSku(SkuAddCommand command);

	public List<LogCheckGenerate> listSkuCheckLog(Integer skuId);

	public List<FileInfo>  spuFilePathList(Integer attacheType, Integer spuId);

	/**
	 * 批量设置备货
	 * @param command
	 */
    void backupSku(SkuAddCommand command);

	/**
	 * 分类修改之后修改spu的状态
	 * @param categoryId
	 */
	public void changeSpuStatusByCategoryChange(Integer categoryId, User user);

    List<Map<String, Object>> searchSkuWithDepartment(String skuName);

	/**
	 * 查询sku信息，仅限页面展示，会缓存5分钟，请不要使用在其他地方
	 * @param skuId
	 * @return
	 */
    Map<String,Object> skuTip(Integer skuId);

	/**
	 *获取订单的某个商品的成本价
	 * @param orderDetailId
	 * @return
	 */
    Map<String, Object> getCostPrice(Integer orderDetailId);
	List<Map<String, Object>> skuTipList(List<Integer> skuIds);

	List<CoreSpuGenerate> findSpuNamesBySpuIds(List<Integer> goodsIds);
}
