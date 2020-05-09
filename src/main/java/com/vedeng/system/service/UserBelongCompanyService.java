package com.vedeng.system.service;

import com.vedeng.authorization.model.UserBelongCompany;
import com.vedeng.common.service.BaseService;

import java.util.List;

/**
 * @Author wayne.liu
 * @Description 数据权限
 * @Date 2019/6/6 9:32
 */
public interface UserBelongCompanyService extends BaseService {

    /**
      * 读取当前所属公司
      * @author wayne.liu
      * @date  2019/6/12 11:16
      * @param
      * @return
      */
    UserBelongCompany getUserCompanyById(Integer id);

    /**
     * 读取当前所属公司
     * @author wayne.liu
     * @date  2019/6/12 11:16
     * @param
     * @return
     */
    List<UserBelongCompany> queryAll();

    /**
     * 读取当前所属公司
     * @author wayne.liu
     * @date  2019/6/12 11:16
     * @param
     * @return
     */
    UserBelongCompany getUserCompanyByName(String name);

}
