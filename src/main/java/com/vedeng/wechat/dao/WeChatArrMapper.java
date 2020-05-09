package com.vedeng.wechat.dao;

import com.vedeng.wechat.model.WeChatArr;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface WeChatArrMapper {

    int deleteAll(@Param("platfromId") Integer platfromId);

    int deleteByKey(Integer arrId);

    int insertSelective(WeChatArr record);

    List<WeChatArr> selectAllList(WeChatArr weChatArr);

    int updateByPrimaryKeySelective(WeChatArr record);

    int insertBatch(@Param("weChatArrList") List<WeChatArr> weChatArrList);

}