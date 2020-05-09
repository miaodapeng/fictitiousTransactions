package com.vedeng.authorization.dao;

import com.vedeng.authorization.model.UserBelongCompany;
import org.apache.ibatis.annotations.Param;

import javax.inject.Named;
import java.util.List;

/**
 * @Author wayne.liu
 * @Description
 * @Date 2019/6/6 9:45 
 */
@Named("userBelongCompanyMapper")
public interface UserBelongCompanyMapper {

    /**
      * 保存公司信息
      * @author wayne.liu
      * @date  2019/6/12 9:14
      * @param
      * @return
      */
    int insertBelongCompany(UserBelongCompany userBelongCompany);

    /**
     * 读取公司信息,根据公司名称精确读取，不支持模糊读取
     * @author wayne.liu
     * @date  2019/6/12 9:14
     * @param
     * @return
     */
    UserBelongCompany getBelongCompany(@Param("companyName") String companyName);

    /**
     * 读取公司信息
     * @author wayne.liu
     * @date  2019/6/12 9:14
     * @param
     * @return
     */
    UserBelongCompany getBelongCompanyById(@Param("id") Integer id);

    /**
      * 查询全部
      * @author wayne.liu
      * @date  2019/6/12 15:02
      * @param
      * @return
      */
    List<UserBelongCompany> queryAll();

}