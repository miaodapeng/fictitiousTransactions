package com.vedeng.system.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;
import com.vedeng.system.model.Notice;

public interface NoticeService extends BaseService {
	/**
	 * <b>Description:</b><br> 公告列表 
	 * @param notice
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年6月26日 下午4:19:04
	 */
	List<Notice> querylistPage(Notice notice, Page page);
	
	/**
	 * <b>Description:</b><br> 发布、取消发布公告
	 * @param notice
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年6月26日 下午5:44:23
	 */
	Boolean changedEnable(Notice notice);
	
	/**
	 * <b>Description:</b><br> 置顶、取消置顶
	 * @param notice
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年6月27日 上午10:32:27
	 */
	Boolean changedTop(Notice notice);
	
	/**
	 * <b>Description:</b><br> 保存添加公告
	 * @param notice
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年6月27日 上午8:50:53
	 */
	Integer saveAdd(Notice notice,HttpSession session);
	
	/**
	 * <b>Description:</b><br> 查询公告
	 * @param notice
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年6月27日 上午8:52:05
	 */
	Notice getNotice(Notice notice);
	/**
	 * 查询最新的公告
	 * <b>Description:</b><br> 
	 * @return
	 * @Note
	 * <b>Author:</b> Cooper
	 * <br><b>Date:</b> 2018年9月3日 上午10:31:26
	 */
	Notice getNotice(Integer CompanyId,Integer noticeCategory);
	/**
	 * <b>Description:</b><br> 保存编辑公告
	 * @param notice
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年6月27日 上午8:52:38
	 */
	Integer saveEdit(Notice notice,HttpSession session);
}
