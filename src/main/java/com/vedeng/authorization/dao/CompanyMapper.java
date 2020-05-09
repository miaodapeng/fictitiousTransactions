package com.vedeng.authorization.dao;

import java.util.List;
import java.util.Map;

import com.vedeng.authorization.model.Company;


/**
 * <b>Description:</b><br> 公司Mapper
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.authorization.dao
 * <br><b>ClassName:</b> CompanyMapper
 * <br><b>Date:</b> 2017年4月25日 上午9:45:09
 */
public interface CompanyMapper {

	/**
	 * 
	 * <b>Description:</b><br> 添加公司
	 * @param company 公司bean
	 * @return 成功1 失败0
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月25日 上午9:45:29
	 */
    int insertSelective(Company company);

    
    /**
     * <b>Description:</b><br> 根据主键查询公司信息 
     * @param companyId 公司ID
     * @return Company
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年4月25日 上午9:45:46
     */
    Company selectByPrimaryKey(Integer companyId);
    
    
    /**
     * <b>Description:</b><br> 查询公司信息 
     * @param company 公司bean
     * @return Company
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年4月25日 上午9:46:16
     */
    Company selectByCompany(Company company);

    /**
     * <b>Description:</b><br> 编辑公司信息 
     * @param company 公司bean
     * @return 成功1 失败0
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年4月25日 上午9:46:29
     */
    int updateByPrimaryKeySelective(Company company);

    /**
     * <b>Description:</b><br> 分页搜索公司 
     * @param map company&page集合
     * @return List<Company>
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年4月25日 上午9:47:29
     */
    List<Company> querylistPage(Map<String, Object> map);
    
    /**
     * <b>Description:</b><br> 获取所有分公司
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年8月9日 上午10:16:12
     */
    List<Company> getAll();
    
    List<Company> getCompanyList(Company company);
}