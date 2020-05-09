package com.vedeng.system.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vedeng.authorization.model.User;
import com.vedeng.common.annotation.SystemControllerLog;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.shiro.JedisUtils;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.common.util.RequestUtils;
import com.vedeng.common.validator.FormToken;
import com.vedeng.system.dao.NoticeUserMapper;
import com.vedeng.system.model.Notice;
import com.vedeng.system.model.NoticeUser;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.system.model.vo.AdVo;
import com.vedeng.system.service.AdService;
import com.vedeng.system.service.NoticeService;

import net.sf.json.JSONArray;

/**
 * <b>Description:</b><br> 公告管理
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.system.controller
 * <br><b>ClassName:</b> NoticeController
 * <br><b>Date:</b> 2017年6月26日 上午9:19:14
 */

@Controller
@RequestMapping("/system/notice")
public class NoticeController extends BaseController {
	
	@Autowired
	@Qualifier("noticeService")
	private NoticeService noticeService;
	@Resource
	private AdService adService;
	@Autowired
	@Qualifier("noticeUserMapper")
	private NoticeUserMapper noticeUserMapper;
	/**
	 * <b>Description:</b><br> 公告列表
	 * @param request
	 * @param notice
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年6月26日 下午4:10:33
	 */
	@ResponseBody
    @RequestMapping(value = "/index")
	public ModelAndView index(HttpServletRequest request, Notice notice,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo, 
			@RequestParam(required = false) Integer pageSize,
			HttpSession session){
		User user =(User)session.getAttribute(ErpConst.CURR_USER);
		if(user.getCompanyId() > 0){
			notice.setCompanyId(user.getCompanyId());
		}
		ModelAndView mv = new ModelAndView("system/notice/index");  
		
		Page page = getPageTag(request,pageNo,pageSize);
		
		List<Notice> list = noticeService.querylistPage(notice, page);
		
		List<SysOptionDefinition> categoryList = getSysOptionDefinitionList(456);
//		if(JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST+456)){
//			String strJson=JedisUtils.get(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST+456);
//			JSONArray jsonArray=JSONArray.fromObject(strJson);
//			categoryList=(List<SysOptionDefinition>) JSONArray.toCollection(jsonArray, SysOptionDefinition.class);
//		}
		mv.addObject("categoryList", categoryList);
		if(list.size() > 0){
			if(null != categoryList){
				for(Notice n : list){
					for(SysOptionDefinition o : categoryList){
						if(o.getSysOptionDefinitionId().equals(n.getNoticeCategory())){
							n.setNoticeCategoryName(o.getTitle());
						}
					}
				}
			}
		}
		mv.addObject("notice", notice);
		mv.addObject("page", page);
		mv.addObject("noticeList", list);
		return mv;
	}
	/**
	 * 保存公告查看日志
	 * <b>Description:</b><br> 
	 * @param request
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Cooper
	 * <br><b>Date:</b> 2018年9月3日 上午10:08:46
	 */
	@ResponseBody
	@RequestMapping(value = "saveNoticeUser")
	@SystemControllerLog(operationType = "add",desc = "保存日志")
	public ResultInfo<Integer> saveNoticeUser(HttpServletRequest request, HttpSession session,String userId,String noticeId){
		NoticeUser noticeUser=new NoticeUser();
		noticeUser.setNoticeId(Integer.valueOf(noticeId));
		noticeUser.setUserId(Integer.valueOf(userId));
		noticeUser.setAddTime(DateUtil.sysTimeMillis());
		int result=noticeUserMapper.insertSelective(noticeUser);
		return new ResultInfo<Integer>(0, "操作成功",result);
	}
	/**
	 * <b>Description:</b><br> 
	 * @param notice
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年6月26日 下午4:12:30
	 */
	@FormToken(save=true)
	@ResponseBody
    @RequestMapping(value = "/add")
	public ModelAndView add(Notice notice){
		ModelAndView mv = new ModelAndView("system/notice/modify");  
		
		List<SysOptionDefinition> categoryList = getSysOptionDefinitionList(456);
//		if(JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST+456)){
//			String strJson=JedisUtils.get(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST+456);
//			JSONArray jsonArray=JSONArray.fromObject(strJson);
//			categoryList=(List<SysOptionDefinition>) JSONArray.toCollection(jsonArray, SysOptionDefinition.class);
//		}
		mv.addObject("categoryList", categoryList);
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 
	 * @param notice
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年6月26日 下午4:13:46
	 */
	@FormToken(remove=true)
	@ResponseBody
    @RequestMapping(value = "/saveadd")
	@SystemControllerLog(operationType = "add",desc = "保存新增公告")
	public ModelAndView saveAdd(Notice notice,HttpSession session){
		ModelAndView mv = new ModelAndView();
		try {
			Integer noticeId = noticeService.saveAdd(notice,session);
			if(noticeId > 0){
				mv.addObject("url","./edit.do?noticeId="+noticeId);
				return success(mv);
			}else{
				return fail(mv);
			}
		} catch (Exception e) {
			logger.error("notice saveadd:", e);
			return fail(mv);
		}
	}
	
	/**
	 * <b>Description:</b><br> 
	 * @param notice
	 * @return
	 * @throws IOException 
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年6月26日 下午4:14:36
	 */
	@ResponseBody
    @RequestMapping(value = "/edit")
	public ModelAndView edit(Notice notice) throws IOException{
		ModelAndView mv = new ModelAndView("system/notice/modify");  
		
		List<SysOptionDefinition> categoryList = getSysOptionDefinitionList(456);
//		if(JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST+456)){
//			String strJson=JedisUtils.get(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST+456);
//			JSONArray jsonArray=JSONArray.fromObject(strJson);
//			categoryList=(List<SysOptionDefinition>) JSONArray.toCollection(jsonArray, SysOptionDefinition.class);
//		}
		mv.addObject("categoryList", categoryList);
		
		Notice info = noticeService.getNotice(notice);
		
		mv.addObject("notice", info);
		mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(info)));
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 
	 * @param notice
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年6月26日 下午4:14:38
	 */
	@ResponseBody
    @RequestMapping(value = "/saveedit")
	@SystemControllerLog(operationType = "edit",desc = "保存编辑公告")
	public ModelAndView saveEdit(Notice notice,HttpSession session,String beforeParams){
		ModelAndView mv = new ModelAndView();
		if(null == notice.getNoticeId() || notice.getNoticeId() < 0){
			return fail(mv);
		}
		try {
			Integer succ = noticeService.saveEdit(notice,session);
			if(succ > 0){
				mv.addObject("url","./edit.do?noticeId="+notice.getNoticeId());
				return success(mv);
			}else{
				return fail(mv);
			}
		} catch (Exception e) {
			logger.error("notice saveedit:", e);
			return fail(mv);
		}
	}
	
	/**
	 * <b>Description:</b><br> 发布、取消发布公告
	 * @param notice
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年6月26日 下午5:44:39
	 */
	@ResponseBody
	@RequestMapping(value="/changedenable")
	@SystemControllerLog(operationType = "edit",desc = "发布/取消发布公告")
	public ResultInfo<?> changedEnable(Notice notice){
		Boolean suc = noticeService.changedEnable(notice);
		
		ResultInfo result = new ResultInfo<>();
		if(suc){//成功
			result.setCode(0);
			result.setMessage("操作成功");
		}
		return result;
	}
	
	/**
	 * <b>Description:</b><br> 置顶、取消置顶
	 * @param notice
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年6月27日 上午10:32:03
	 */
	@ResponseBody
	@RequestMapping(value="/changedtop")
	@SystemControllerLog(operationType = "edit",desc = "置顶/取消置顶公告")
	public ResultInfo<?> changedTop(Notice notice){
		Boolean suc = noticeService.changedTop(notice);
		
		ResultInfo result = new ResultInfo<>();
		if(suc){//成功
			result.setCode(0);
			result.setMessage("操作成功");
		}
		return result;
	}
	
	/**
	 * <b>Description:</b><br> 系统帮助列表
	 * @param request
	 * @param notice
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年6月26日 下午4:10:33
	 */
	@ResponseBody
    @RequestMapping(value = "/helpNoticeListPage")
	public ModelAndView helpNoticeListPage(HttpServletRequest request, Notice notice,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo, 
			@RequestParam(required = false) Integer pageSize,
			HttpSession session){
		User user =(User)session.getAttribute(ErpConst.CURR_USER);
		if(user.getCompanyId() > 0){
			notice.setCompanyId(user.getCompanyId());
		}
		notice.setIsEnable(1);
		ModelAndView mv = new ModelAndView("/system/notice/helpNoticeList");  
		
		Page page = getPageTag(request,pageNo,pageSize);
		
		List<Notice> list = noticeService.querylistPage(notice, page);
		
		List<SysOptionDefinition> categoryList = getSysOptionDefinitionList(456);
		mv.addObject("categoryList", categoryList);
		if(list.size() > 0){
			if(null != categoryList){
				for(Notice n : list){
					for(SysOptionDefinition o : categoryList){
						if(o.getSysOptionDefinitionId().equals(n.getNoticeCategory())){
							n.setNoticeCategoryName(o.getTitle());
						}
					}
				}
			}
		}
		
		//mv.addObject("notice", notice);
		mv.addObject("page", page);
		mv.addObject("noticeList", list);
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 查看系统帮助
	 * @param request
	 * @param notice
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年6月26日 下午4:10:33
	 */
	@ResponseBody
    @RequestMapping(value = "/view")
	public ModelAndView view(HttpServletRequest request, Notice notice){
		User user =(User)request.getSession().getAttribute(ErpConst.CURR_USER);
		if(user.getCompanyId() > 0){
			notice.setCompanyId(user.getCompanyId());
		}
		notice.setIsEnable(1);
		ModelAndView mv = new ModelAndView("/system/notice/view");  		
		Notice no = noticeService.getNotice(notice);
		mv.addObject("notice", no);
		return mv;
	}
}
