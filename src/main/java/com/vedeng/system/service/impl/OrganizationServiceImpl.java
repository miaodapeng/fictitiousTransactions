package com.vedeng.system.service.impl;

import com.vedeng.authorization.dao.OrganizationMapper;
import com.vedeng.authorization.model.Organization;
import com.vedeng.system.service.OrganizationService;
import org.apache.commons.collections.CollectionUtils;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationMapper organizationMapper;


    @Override
    public Organization getOrganizationByName(String orgName) {
        Organization query=new Organization();
        query.setOrgName(orgName);
        query.setCompanyId(1);
        return organizationMapper.getByOrg(query);
    }

    @Override
    public List<Organization> getOrganizationChild(Organization organization) {
        List<Organization> organizations=new ArrayList<>();
        organizations.add(organization);
        getOrganizationByParentId(organization,organizations);
        return organizations;
    }

    /**
     * <b>Description:</b>递归查询所有集团业务部子部门<br>
     * @param
     * @return
     * @Note
     * <b>Author:calvin</b>
     * <br><b>Date:</b> 2019/11/13
     */
    private void getOrganizationByParentId(Organization organization,List<Organization> organizations){
        List<Organization> orgs=organizationMapper.findOrgsByParentId(organization.getOrgId(),organization.getCompanyId());
        if(CollectionUtils.isEmpty(orgs)){
            return;
        }
        for(Organization org:orgs){
            if(org!=null||org.getOrgId()!=null){
                organizations.add(org);
                getOrganizationByParentId(org,organizations);
            }
        }
    }
}
