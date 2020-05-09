package com.vedeng.system.service;

import java.util.List;

import com.vedeng.authorization.model.Company;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;

/**
 * <b>Description:</b><br> 公司service
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.system.service
 * <br><b>ClassName:</b> CompanyService
 * <br><b>Date:</b> 2017年4月25日 上午11:23:34
 */
public interface CompanyService extends BaseService {
	/**
	 * <b>Description:</b><br> 分页搜索公司  
	 * @param company 公司实体
	 * @param page 分页实体
	 * @return List<Company>
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月25日 上午11:23:58
	 */
	List<Company> querylistPage(Company company,Page page);
	
	/**
	 * <b>Description:</b><br> 根据公司ID查询公司信息 
	 * @param companyId 公司ID
	 * @return Company
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月25日 上午11:26:07
	 */
	Company getCompanyByCompangId(Integer companyId);
	
	/**
	 * <b>Description:</b><br> 查询公司
	 * @param company 公司实体
	 * @return Company
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月25日 上午11:26:26
	 */
	Company getCompany(Company company);
	
	/**
	 * <b>Description:</b><br> 编辑公司信息
	 * @param company 公司实体
	 * @return 成功1 失败0
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月25日 上午11:26:49
	 */
	Integer modifyCompany(Company company);
	
	/**
	 * <b>Description:</b><br> 获取所有分公司
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年8月9日 上午10:17:42
	 */
	List<Company> getAll();
	
	List<Company> getCompanyList(Company company);
}
