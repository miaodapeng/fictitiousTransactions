package com.vedeng.system.service;



import com.vedeng.authorization.model.Organization;

import java.util.List;

public interface OrganizationService {
    /**
     * <b>Description:</b>获取某个组织及其所有子部门<br>
     * @param
     * @return
     * @Note
     * <b>Author:calvin</b>
     * <br><b>Date:</b> 2019/11/13
     */
    List<Organization> getOrganizationChild(Organization organization);

    /**
     * <b>Description:</b>根据组织名称获取组织信息<br>
     * @param
     * @return
     * @Note
     * <b>Author:calvin</b>
     * <br><b>Date:</b> 2020/4/8
     */
    Organization getOrganizationByName(String orgName);
}
