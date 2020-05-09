package com.vedeng.phoneticWriting.dao;

import com.vedeng.phoneticWriting.model.ModificationRecord;
import com.vedeng.phoneticWriting.model.PhoneticWriting;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PhoneticWritingMapper {
    int deleteByPrimaryKey(Integer phoneticWritingId);

    int insert(PhoneticWriting record);

    int insertSelective(PhoneticWriting record);

    PhoneticWriting selectByPrimaryKey(Integer phoneticWritingId);

    int updateByPrimaryKeySelective(PhoneticWriting record);

    int updateByPrimaryKey(PhoneticWriting record);

    /**
     * 功能描述: 批量新增任务记录
     * @param: [list]
     * @return: int
     * @auther: Barry.Xu
     * @date: 2019/4/24 15:34
     */
    int insertBatch(List<PhoneticWriting> list);

    /**
     * 功能描述: 查询待转换的任务
     * @param: []
     * @return: java.util.List<com.vedeng.phoneticWriting.model.PhoneticWriting>
     * @auther: barry.xu
     * @date: 2019/4/25 11:04
     */
    List<PhoneticWriting> selectWritingTask();

    /**
     * 功能描述: 更新转写内容
     * @param: [record]
     * @return: int
     * @auther: barry.xu
     * @date: 2019/4/25 17:05
     */
    int updateContent(PhoneticWriting record);

    /**
    * @Description: 文本修改
    * @Param: [modificationRecord]
    * @return: int
    * @Author: scott.zhu
    * @Date: 2019/4/25
    */
    int updatePhoneticWritingInfo(ModificationRecord modificationRecord);
    /**
    * @Description: 查询转译的文本
    * @Param: [communicateRecordId]
    * @return: com.vedeng.phoneticWriting.model.PhoneticWriting
    * @Author: scott.zhu
    * @Date: 2019/4/25
    */
    PhoneticWriting getPhoneticWriting(Integer communicateRecordId);
    /** 
    * @Description: 判断争议内容在文本中的位置
    * @Param: [modificationRecord] 
    * @return: int 
    * @Author: scott.zhu 
    * @Date: 2019/5/7 
    */
    Integer getPhoneticWritingIndex(ModificationRecord modificationRecord);


    List<PhoneticWriting> getPhoneticWritingList(@Param("communicateRecordIdList") List<Integer> communicateRecordIdList);
}