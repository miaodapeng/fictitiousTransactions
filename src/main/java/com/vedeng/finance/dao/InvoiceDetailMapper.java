package com.vedeng.finance.dao;

import com.vedeng.common.model.ResultInfo;
import com.vedeng.finance.model.Invoice;
import com.vedeng.finance.model.InvoiceDetail;
import com.vedeng.finance.model.orderInfo;
import com.vedeng.finance.model.vo.InvoiceDetailVo;
import org.apache.ibatis.annotations.Param;

import javax.inject.Named;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Named("invoiceDetailMapper")
public interface InvoiceDetailMapper {
	
    int deleteByPrimaryKey(Integer invoiceDetailId);

    int insert(InvoiceDetail record);

    int insertSelective(InvoiceDetail record);

    InvoiceDetail selectByPrimaryKey(Integer invoiceDetailId);

    int updateByPrimaryKeySelective(InvoiceDetail record);

    int updateByPrimaryKey(InvoiceDetail record);


    /**
     * <b>Description:</b><br> 根据发票号和订单产品ID查询订单信息
     * @param detailgoodsId
     * @param invoiceNo
     * @return
     * @Note
     * <b>Author:</b> duke
     * <br><b>Date:</b> 2017年8月24日 上午11:10:50
     */
    List<orderInfo> getBuyorderByDetailGoodsId(@Param(value = "detailgoodsId") Integer detailgoodsId, @Param(value = "invoiceNo") String invoiceNo, @Param(value = "invoiceType") Integer invoiceType, @Param(value = "afterSubjectType") Integer afterSubjectType);

    /**
     * <b>Description:</b><br> 查询发票录入数量
     * @param detailGoodsIdList
     * @param type
     * @return
     * @Note
     * <b>Author:</b> duke
     * <br><b>Date:</b> 2017年8月24日 上午11:11:36
     */
    List<InvoiceDetail> getInvoiceInputNum(@Param(value = "detailGoodsIdList") List<Integer> detailGoodsIdList, @Param(value = "type") Integer type);

	/**
	 * <b>Description:</b><br> 根据销售订单ID获取销售产品列表
	 * @param relatedId
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年8月31日 上午9:31:28
	 */
	List<InvoiceDetail> getSaleorderGoodsList(@Param(value = "relatedId") Integer relatedId);


	/**
	 * <b>Description:</b><br> 查询同一订单下，相同产品已录票数量
	 * @param goods_list
	 * @param type
	 * @return
	 * @Note
	 * <b>Author:</b> Administrator
	 * <br><b>Date:</b> 2017年10月16日 上午11:39:19
	 */
	List<InvoiceDetail> getInvoiceEnterNum(@Param(value = "goods_list") List<InvoiceDetail> goods_list, @Param(value = "type") Integer type);

	/**
	 * <b>Description:</b><br> 插入发票子表--蓝字作废发票记录，根据原有发票ID
	 * @param old_invoiceId
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年10月27日 上午10:41:38
	 */
	int insertInvoiceDetail(@Param(value = "oldInvoiceId") Integer oldInvoiceId, @Param(value = "newInvoiceId") Integer newInvoiceId);

	/**
	 * <b>Description:</b><br> 查询采购订单的产品的发票列表（蓝字有效）
	 * @param relatedId
	 * @return
	 * @Note
	 * <b>Author:</b>
	 * <br><b>Date:</b> 2017年8月31日 上午9:31:28
	 */
	List<InvoiceDetailVo> getInvoiceDetailVoList(InvoiceDetailVo invoiceDetailVo);
/*	*//**
	 * 查询采购订单的产品的发票列表（蓝字有效）（批量）
	 * <b>Description:</b><br>
	 * @param invoiceDetailVo
	 * @return
	 * @Note
	 * <b>Author:</b> Administrator
	 * <br><b>Date:</b> 2018年7月2日 下午3:46:39
	 *//*
	List<InvoiceDetailVo> getInvoiceDetailVoListByMap(Map<String,Object> map);*/
	/**
	 * <b>Description:</b><br> 获取发票详情
	 * @param invoice
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年1月16日 下午3:41:47
	 */
	List<InvoiceDetail> getInvoiceDetails(Invoice invoice);

	/**
	 * <b>Description:</b><br> 修改销售订单发票开具状态
	 * @param relatedId
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年2月7日 下午4:39:13
	 */
	int updateSaleOrderInvoiceStatus(@Param(value = "saleorderId") Integer saleorderId, @Param(value = "sysdate") Long sysdate, @Param(value = "invoiceStatus") Integer invoiceStatus);

	List<InvoiceDetail> getInvoiceDetailsBySaleorderId(Integer saleorderId);

//	/**
//	 * <b>Description:</b><br> 批量保存发票详细
//	 * @param invoiceDetailList
//	 * @return
//	 * @Note
//	 * <b>Author:</b> duke
//	 * <br><b>Date:</b> 2018年6月25日 上午9:41:20
//	 */
//	ResultInfo batchSaveEinvoice(@Param(value = "invoiceDetailList") List<InvoiceDetail> invoiceDetailList);

	/** @description: VDERP-1325 分批开票 查询已开票发票.
	 * @notes: 返回该订单下所有已开发票,数量可以遍历计算.
	 * @author: Tomcat.Hui.
	 * @date: 2019/11/7 9:29.
	 * @param saleorderId
	 * @return: java.util.List<com.vedeng.finance.model.Invoice>.
	 * @throws: .
	 */
	public List<Invoice> getInvoicedEntitiesBySaleorderId(Integer saleorderId);

	/** @description: VDERP-1325 分批开票.
	 * @notes: 获取已开票数量.
	 * @author: Tomcat.Hui.
	 * @date: 2019/11/8 10:37.
	 * @param saleorderNo
	 * @return: java.util.List<java.util.Map < java.lang.String , java.lang.Long>>.
	 * @throws: .
	 */
	public List<Map<String,Object>> getInvoicedTaxNum(String saleorderNo);

	/** @description: VDERP-1325 分批开票.
	 * @notes: 获取已申请数量(新数据).
	 * @author: Tomcat.Hui.
	 * @date: 2019/11/8 10:37.
	 * @param saleorderNo
	 * @return: java.util.List<java.util.Map < java.lang.String , java.lang.Long>>.
	 * @throws: .
	 */
	public List<Map<String, Object>> getAppliedTaxNum(String saleorderNo);
}