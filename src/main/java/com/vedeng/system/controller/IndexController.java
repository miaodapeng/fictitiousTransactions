package com.vedeng.system.controller;

import com.common.constants.Contant;
import com.vedeng.authorization.model.Action;
import com.vedeng.authorization.model.Actiongroup;
import com.vedeng.authorization.model.Position;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.util.RequestUtils;
import com.vedeng.soap.service.VedengSoapService;
import com.vedeng.system.model.Notice;
import com.vedeng.system.model.vo.AdVo;
import com.vedeng.system.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <b>Description:</b><br> 主框架控制器
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.system.controller
 * <br><b>ClassName:</b> IndexController
 * <br><b>Date:</b> 2017年4月25日 上午11:16:56
 */
@Controller()
public class IndexController extends BaseController {
	public static Logger logger = LoggerFactory.getLogger(IndexController.class);

	@Autowired
	@Qualifier("actionService")
	private ActionService actionService;
	@Autowired
	@Qualifier("roleService")	
	private RoleService roleService;
	@Autowired
	@Qualifier("positService")
	private PositService positService;
	@Autowired
	@Qualifier("noticeService")
	private NoticeService noticeService;
	@Autowired
	@Qualifier("noticeUserService")
	private NoticeUserService noticeUserService;
	@Autowired
	@Qualifier("actiongroupService")
	private ActiongroupService actiongroupService;
	
	@Resource
	private AdService adService;
	
	@Resource
	private ReadService readService;
@Resource
VedengSoapService vedengSoapService;

	@RequestMapping("/publishmonitorlogin")
	@ResponseBody
	public Object publishmonitor(){
		return "OK";
	}
 


	@RequestMapping("/checkpreload.html")
	@ResponseBody
	public void checkpreload(HttpServletResponse response) throws IOException {
		response.getWriter().write("success");
	}
	@RequestMapping("/nopower")
	public ModelAndView nopower(HttpServletResponse response) throws IOException {
		ModelAndView openOperateMv = new ModelAndView();
		openOperateMv.setViewName("common/nopower");
		return openOperateMv;
	}

	@RequestMapping("/batSyncToPhp.html")
	@ResponseBody
	public String checkpreload(String goodIds){
		String s="";
		for(String skuId:goodIds.split(",")){

			try{
				vedengSoapService.goodsSync(Integer.parseInt(skuId));
			}catch (Exception e){
				logger.error(Contant.ERROR_MSG, e);
				s+=skuId+",";
			}
		}
		return "success"+s;
	}



	/**
	 * <b>Description:</b><br> 
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月25日 上午11:17:22
	 */
	@RequestMapping("/index")
	public Object index(){
 		HttpSession session=RequestUtils.getRequest().getSession();
		if(session==null){
			return "login";
		}
		ModelAndView mv = new ModelAndView("index");
		User user = (User) session.getAttribute(Consts.SESSION_USER);
		if(user==null){
			ModelAndView mv2 = new ModelAndView("redirect:/login.do");
			return mv2;
		}
		mv.addObject("user", user);
		List<AdVo> list=new ArrayList<AdVo>();
		if(user.getPositType()!=null){
			//查询最新的发版公告
			Notice notice=noticeService.getNotice(user.getCompanyId(),458);
			//查询当前用户是否存在比该发版公告的查看日志
			int count=(int)noticeUserService.getNoticeUserCount(notice, user);
			//如果有,且用户是销售的话需要做区别处理
			if(310==user.getPositType()){
				//查询广告列表
				list = getAdVoList(user);
			}
			mv.addObject("notice",notice);
			mv.addObject("count",count);
			mv.addObject("list", list);
			mv.addObject("wsUrl", wsUrl);
		}
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 获取广告列表
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年7月5日 上午10:57:03
	 */
	private List<AdVo> getAdVoList(User user){
		AdVo adVo = new AdVo();
		adVo.setCompanyId(user.getCompanyId());
		adVo.setBannerName("销售首页广告位");
		List<AdVo> list = adService.getAdVoList(adVo);
		return list;
	}
	
	/**
	 * <b>Description:</b><br> 框架右侧
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月25日 上午11:17:30
	 */
	@RequestMapping("/main")
	public String main(){
		return "main";
	}
	
	/**
	 * <b>Description:</b><br> 菜单
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月25日 上午11:17:32
	 */
	@RequestMapping("/menu")
	public String menu(Model model,HttpSession session){
		User user =(User)session.getAttribute(ErpConst.CURR_USER);
		if(user!=null){
			List<Actiongroup> menu1List = actiongroupService.getMenuActionGroupList(user);
			model.addAttribute("menu1List", menu1List);//一级菜单				
			//当前用户所在部门
			List<Position> position = positService.getPositionByUserId(user.getUserId());	
			List<Action> menu2List = actionService.getActionList(user,null);	
			model.addAttribute("menu2List", menu2List);//二级菜单
			model.addAttribute("user", user);
			model.addAttribute("position", position);
			if(menu2List != null && menu2List.size() > 0){
				boolean flag = false;
				for (Action action : menu2List) {
					if("产品检索".equals(action.getActionDesc())){
						flag = true;
						break;
					}
				}
				if(flag){
					List<AdVo> list = getAdVoList(user);
					if(list != null && list.size() > 0){
						model.addAttribute("haveAd", 1);//有广告
					}else{
						model.addAttribute("haveAd", 0);//无广告
					}
					Integer count = readService.getReadByUserId(user);
					if(count > 0 && list != null && list.size() > 0){
						model.addAttribute("isClick", 1);//点击过
					}else{
						model.addAttribute("isClick", 0);//未点击过
					}
				}
			}
			
			
			return "common/side-bar";
		}
		return "login";
	}
	
	/**
	 * <b>Description:</b><br> 顶部
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月25日 上午11:17:34
	 */
	@RequestMapping("/top")
	public String top(){
		return "common/top-bar";
	}
	
	/**
	 * <b>Description:</b><br> 欢迎页
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月25日 上午11:17:37
	 */
	@RequestMapping("/welcome")
	public ModelAndView welcome(){
		//return "welcome";
		return new ModelAndView("redirect:/home/page/index.do");
	}

	
}
