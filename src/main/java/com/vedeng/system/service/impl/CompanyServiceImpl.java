package com.vedeng.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.vedeng.authorization.dao.CompanyMapper;
import com.vedeng.authorization.model.Company;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.DateUtil;
import com.vedeng.system.service.CompanyService;

@Service("companyService")
public class CompanyServiceImpl extends BaseServiceimpl implements CompanyService {
	@Autowired
	@Qualifier("companyMapper")
	private CompanyMapper companyMapper;

	@Override
	public List<Company> querylistPage(Company company, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("company", company);
		map.put("page", page);
		return companyMapper.querylistPage(map);
	}

	@Override
	public Company getCompanyByCompangId(Integer companyId) {
		return companyMapper.selectByPrimaryKey(companyId);
	}

	@Override
	public Company getCompany(Company company) {
		return companyMapper.selectByCompany(company);
	}

	@Override
	public Integer modifyCompany(Company company) {
		Long time = DateUtil.sysTimeMillis();
		company.setModTime(time);
		company.setUpdater(1);
		if(null != company.getCompanyId()
				&& company.getCompanyId() > 0){
			//编辑
			return companyMapper.updateByPrimaryKeySelective(company);
		}else{
			//新增
			
			company.setAddTime(time);
			company.setCreator(1);
			return companyMapper.insertSelective(company);
		}
		
	}

	@Override
	public List<Company> getAll() {
		return companyMapper.getAll();
	}

	@Override
	public List<Company> getCompanyList(Company company) {
		// TODO Auto-generated method stub
		return companyMapper.getCompanyList(company);
	}


}
