package com.vedeng.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.DateUtil;
import com.vedeng.system.dao.NoticeMapper;
import com.vedeng.system.model.Notice;
import com.vedeng.system.service.NoticeService;

@Service("noticeService")
public class NoticeServiceImpl extends BaseServiceimpl implements NoticeService {
	
	@Autowired
	@Qualifier("noticeMapper")
	private NoticeMapper noticeMapper;

	@Override
	public List<Notice> querylistPage(Notice notice, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("notice", notice);
		map.put("page", page);
		return noticeMapper.querylistPage(map);
	}

	@Override
	public Boolean changedEnable(Notice notice) {
		Notice info = noticeMapper.selectByPrimaryKey(notice.getNoticeId());
		switch(info.getIsEnable()){
		case 0:
			notice.setIsEnable(1);
			break;
		case 1:
			notice.setIsEnable(0);
			break;
		
		}
		
		int ref = noticeMapper.update(notice);
		if (ref > 0) {
			return true;
		}
		return false;
	}

	@Override
	public Integer saveAdd(Notice notice, HttpSession session) {
		User user =(User)session.getAttribute(ErpConst.CURR_USER);
		Long time = DateUtil.sysTimeMillis();
		
		notice.setCompanyId(user.getCompanyId());
		notice.setAddTime(time);
		notice.setCreator(user.getUserId());
		notice.setModTime(time);
		notice.setUpdater(user.getUserId());
		
		noticeMapper.insert(notice);
		Integer noticeId = notice.getNoticeId();
		return noticeId;
	}

	@Override
	public Notice getNotice(Notice notice) {
		return noticeMapper.selectByPrimaryKey(notice.getNoticeId());
	}

	@Override
	public Integer saveEdit(Notice notice, HttpSession session) {
		User user =(User)session.getAttribute(ErpConst.CURR_USER);
		Long time = DateUtil.sysTimeMillis();
		
		notice.setModTime(time);
		notice.setUpdater(user.getUserId());
		int update = noticeMapper.update(notice);
		return update;
	}

	@Override
	public Boolean changedTop(Notice notice) {
		Notice info = noticeMapper.selectByPrimaryKey(notice.getNoticeId());
		switch(info.getIsTop()){
		case 0:
			notice.setIsTop(1);
			break;
		case 1:
			notice.setIsTop(0);
			break;
		
		}
		
		int ref = noticeMapper.update(notice);
		if (ref > 0) {
			return true;
		}
		return false;
	}

	@Override
	public Notice getNotice(Integer CompanyId,Integer noticeCategory) {
		return noticeMapper.selectLastNotice(CompanyId,noticeCategory);
	}

}
