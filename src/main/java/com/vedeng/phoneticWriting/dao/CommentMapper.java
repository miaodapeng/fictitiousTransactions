package com.vedeng.phoneticWriting.dao;

import com.vedeng.phoneticWriting.model.Comment;
import com.vedeng.trader.model.CommunicateRecord;

import java.util.List;

public interface CommentMapper {
    int deleteByPrimaryKey(Integer commentId);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Integer commentId);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

    List<Comment> getComList(CommunicateRecord communicateRecord);
    /** 
    * @Description: 查询当前沟通记录下的所有点评 
    * @Param: [comment] 
    * @return: java.util.List<com.vedeng.phoneticWriting.model.Comment> 
    * @Author: scott.zhu 
    * @Date: 2019/4/25 
    */
    List<Comment> getCommentList(Comment comment);
}