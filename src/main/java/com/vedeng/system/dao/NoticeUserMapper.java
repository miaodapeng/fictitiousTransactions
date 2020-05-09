package com.vedeng.system.dao;

import org.apache.ibatis.annotations.Param;

import com.vedeng.authorization.model.User;
import com.vedeng.system.model.Notice;
import com.vedeng.system.model.NoticeUser;

public interface NoticeUserMapper {
    int deleteByPrimaryKey(Integer noticeUserId);

    int insert(NoticeUser record);

    int insertSelective(NoticeUser record);

    NoticeUser selectByPrimaryKey(Integer noticeUserId);

    int updateByPrimaryKeySelective(NoticeUser record);

    int updateByPrimaryKey(NoticeUser record);
    /**
     * 查询用户是否有查看该公告的最新日志
     * <b>Description:</b><br> 
     * @param notice
     * @param user
     * @return
     * @Note
     * <b>Author:</b> Cooper
     * <br><b>Date:</b> 2018年9月3日 上午11:12:22
     */
    int getNoticeUserCount(@Param("notice") Notice notice,@Param("user") User user);
}