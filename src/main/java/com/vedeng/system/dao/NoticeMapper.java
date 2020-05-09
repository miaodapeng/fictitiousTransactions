package com.vedeng.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.vedeng.system.model.Notice;

public interface NoticeMapper {

    /**
     * <b>Description:</b><br> 新增公告
     * @param notice
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年6月27日 上午8:59:03
     */
    int insert(Notice notice);

    /**
     * <b>Description:</b><br> 更新公告
     * @param record
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年6月26日 下午5:48:20
     */
    int update(Notice notice);
    
    /**
     * <b>Description:</b><br> 公告列表
     * @param map
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年6月26日 下午4:21:40
     */
    List<Notice> querylistPage(Map<String, Object> map);
    
    /**
     * <b>Description:</b><br> 查询公告
     * @param noticeId
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年6月26日 下午5:46:05
     */
    Notice selectByPrimaryKey(Integer noticeId);
    /**
     * 查询最新的公告信息
     * <b>Description:</b><br> 
     * @return
     * @Note
     * <b>Author:</b> Cooper
     * <br><b>Date:</b> 2018年9月3日 上午10:49:33
     */
    Notice selectLastNotice(@Param("companyId") Integer CompanyId,@Param("noticeCategory") Integer noticeCategory);
}