package com.vedeng.phoneticWriting.dao;

import com.vedeng.phoneticWriting.model.ModificationRecord;
import com.vedeng.phoneticWriting.model.vo.ModificationRecordVo;
import com.vedeng.trader.model.CommunicateRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ModificationRecordMapper {
    int deleteByPrimaryKey(Integer modificationRecordId);

    int insert(ModificationRecord record);

    int insertSelective(ModificationRecord record);

    ModificationRecord selectByPrimaryKey(Integer modificationRecordId);

    int updateByPrimaryKeySelective(ModificationRecord record);

    int updateByPrimaryKey(ModificationRecord record);

    /*List<ModificationRecord> getMrList(CommunicateRecord communicateRecord);*/

    /**
     * 功能描述: 修改记录列表
     * @param: []
     * @return: java.util.List<com.vedeng.phoneticWriting.model.vo.ModificationRecordVo>
     * @auther: barry.xu
     * @date: 2019/4/29 9:29
     */
    List<ModificationRecordVo> getModificationRecordListPage(@Param("map") Map<String,Object> map);

    /** 
    * @Description: 查询当前记录下的所有争议规则
    * @Param: [communicateRecord] 
    * @return: java.util.List<com.vedeng.phoneticWriting.model.ModificationRecord> 
    * @Author: scott.zhu 
    * @Date: 2019/4/28 
    */
    List<ModificationRecord> getMrInfoList(CommunicateRecord communicateRecord);

    /**
     * 功能描述: 查询是否有相同的争议内容
     * @param: [controversialContent]
     * @return: int
     * @auther: barry.xu
     * @date: 2019/4/29 14:52
     */
    Integer getTheSameRecord(@Param("controversialContent") String controversialContent);

    /**
     * 功能描述: 删除修改记录
     * @param: [id]
     * @return: java.lang.Integer
     * @auther: barry.xu
     * @date: 2019/4/29 18:12
     */
    Integer delRecord(Integer id);
    /** 
    * @Description: 判断在当前沟通记录下是否存在相同的争议内容
    * @Param: [record] 
    * @return: int 
    * @Author: scott.zhu 
    * @Date: 2019/5/5 
    */
    int getTheSameRecordById(ModificationRecord record);
    /** 
    * @Description: 判断当前争议内容除了在本文本下，在全局存不存在
    * @Param: [modificationRecord] 
    * @return: int 
    * @Author: scott.zhu 
    * @Date: 2019/5/6 
    */
    int getTheSameRecordByNotId(ModificationRecord modificationRecord);
}