package com.vedeng.system.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.apache.ibatis.annotations.Param;

import com.vedeng.system.model.VerifiesInfo;


@Named("verifiesInfoMapper")
public interface VerifiesInfoMapper {
    int deleteByPrimaryKey(Integer verifiesInfoId);

    int insert(VerifiesInfo record);

    int insertSelective(VerifiesInfo record);

    VerifiesInfo selectByPrimaryKey(Integer verifiesInfoId);

    int updateByPrimaryKeySelective(VerifiesInfo record);

    int updateByPrimaryKey(VerifiesInfo record);
    
    List<VerifiesInfo> getVerifiesInfo(VerifiesInfo verifiesInfo);
    
    /**
     * <b>Description:</b><br> 查询付款记录的关联审核状态
     * @param verifiesInfo
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2018年1月4日 下午3:34:57
     */
    VerifiesInfo getVerifiesInfoByParam(VerifiesInfo verifiesInfo);

    Integer updateInfo(@Param("tableName")String tableName,@Param("id") String id,@Param("idValue") Integer idValue, @Param("key")String key, @Param("value")Integer value);

    Integer updateInfoTime(@Param("tableName")String tableName,@Param("id") String id,@Param("idValue") Integer idValue, @Param("key")String key, @Param("value")Integer value,@Param("validTime")long validTime, @Param("modTime")long modTime);
    
    Integer updateVerifyInfo(@Param("tableName")String tableName,@Param("idValue") Integer idValue,@Param("value")Integer value);
    
    int deleteVerifiesInfo(@Param("relateTableKey")Integer relateTableKey,@Param("relateTable")String relateTable);


    List<Integer> getBuyorderListUnVerified(Map<String, Object> map);
    
}