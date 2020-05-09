package com.vedeng.system.service;

import java.util.List;
import java.util.Map;

import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.system.model.VerifiesInfo;
import com.vedeng.system.model.VerifiesRecord;
import org.apache.ibatis.annotations.Param;

/**
 * <b>Description:</b><br> 审核记录
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> dbcenter
 * <br><b>PackageName:</b> com.vedeng.service.system
 * <br><b>ClassName:</b> VerifiesRecordService
 * <br><b>Date:</b> 2017年5月27日 上午9:49:06
 */
public interface VerifiesRecordService {
	/**
	 * <b>Description:</b><br> 获取审核记录
	 * @param verifiesRecord
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年5月27日 上午9:50:59
	 */
	List<VerifiesRecord> getVerifiesRecord(VerifiesRecord verifiesRecord);
	
	/**
	 * <b>Description:</b><br> 获取审核记录(分页)
	 * @param verifiesRecord
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年5月27日 上午9:58:54
	 */
	Map<String, Object> getVerifiesRecordListPage(VerifiesRecord verifiesRecord,Page page);
	/**
	 * 
	 * <b>Description:</b><br> 添加审核情况，用于筛选
	 * @param verifiesInfo
	 * @return
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2017年11月25日 下午2:52:05
	 */
	ResultInfo<?> saveVerifiesInfo(String taskId,Integer status);
	/**
	 * 
	 * <b>Description:</b><br> 根据对象获取审核关联记录
	 
	 * @return
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2018年1月5日 下午4:26:45
	 */
	List<VerifiesInfo> getVerifiesList(VerifiesInfo verifiesInfo);

	Integer updateBDStatus(Integer saleorderId);

	ResultInfo<?> saveVerifiesInfoForTrader(String taskId, int status);

	/**
	 * <b>Description:</b>直接保存审核结果<br>
	 * @param
	 * @return
	 * @Note
	 * <b>Author:calvin</b>
	 * <br><b>Date:</b> 2020/3/19
	 */
	ResultInfo saveVerifiesInfoDirect(VerifiesInfo verifiesInfo);

	/**
	 * <b>Description:</b>根据关联表和关联表主键删除审核记录<br>
	 * @param
	 * @return
	 * @Note
	 * <b>Author:calvin</b>
	 * <br><b>Date:</b> 2020/3/20
	 */
	int deleteVerifiesInfoByRelateKey(Integer relateTableKey,String relateTable);
}
