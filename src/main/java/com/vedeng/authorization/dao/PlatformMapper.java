package com.vedeng.authorization.dao;

import com.vedeng.authorization.model.Platform;
import org.apache.ibatis.annotations.Param;

import javax.inject.Named;
import java.util.List;

/**
 * @Author wayne.liu
 * @Description
 * @Date 2019/6/6 9:45 
 */
@Named("platformMapper")
public interface PlatformMapper {

    /**
      * 查询应用平台列表
      * @Author wayne.liu
      * @Date  2019/6/6 9:46
      * @Param
      * @return
      */
    List<Platform> queryPlatformList();

    /**
     * 查询特定应用平台
     * @Author wayne.liu
     * @Date  2019/6/6 9:46
     * @Param
     * @return
     */
    Platform queryPlatformById(@Param("id") Integer id);

}