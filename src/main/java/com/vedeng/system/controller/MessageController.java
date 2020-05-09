package com.vedeng.system.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.StringUtil;
import com.vedeng.system.model.Message;
import com.vedeng.system.model.MessageUser;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.system.service.MessageService;
import com.vedeng.system.service.UserService;

@Controller
@RequestMapping("/system/message")
public class MessageController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(MessageController.class);

	@Autowired
	@Qualifier("messageService")
	private MessageService messageService;

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	/**
	 *
	 * <b>Description:</b><br>
	 * 根据用户获取消息通知列表
	 * 
	 * @param request
	 * @param message
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Note <b>Author:</b> Micheal <br>
	 *       <b>Date:</b> 2017年7月6日 下午1:27:04
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/index")
	public ModelAndView index(HttpServletRequest request, Message message,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo, // required =
																				// false:可不传入pageNo这个参数，true必须传入，defaultValue默认值，若无默认值，使用Page类中的默认值
			@RequestParam(required = false) Integer pageSize, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		// 查询消息列表
		Page page = getPageTag(request, pageNo, pageSize);
		// 获取session中user信息
		User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
		// 获取消息类型
		List<SysOptionDefinition> categoryName = getSysOptionDefinitionList(SysOptionConstant.ID_478);
		// if(JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST+SysOptionConstant.ID_478)){
		// String
		// jsonStr=JedisUtils.get(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST+SysOptionConstant.ID_478);
		// JSONArray json=JSONArray.fromObject(jsonStr);
		// categoryName=(List<SysOptionDefinition>)JSONArray.toCollection(json,
		// SysOptionDefinition.class);
		// }
		List<Message> messageList = messageService.getMessageByUserlistpage(message, page, session_user.getUserId());
		for (Message msg : messageList) {
			User user = userService.getUserById(msg.getCreator());
			msg.setUserName(user == null ? "" : user.getUsername());
			if (msg.getSourceName() == null) {
				msg.setSourceName(msg.getUserName());
			}
		}
		// 获取未读消息数量
		Message noReadMessage = new Message();
		noReadMessage.setIsView(0);
		Integer noReadCount = messageService.getMessageCount(noReadMessage, session_user.getUserId());

		mv.addObject("message", message);
		mv.addObject("userId", session_user.getUserId());
		mv.addObject("categoryName", categoryName);
		mv.addObject("messageList", messageList);
		mv.addObject("page", page);
		mv.addObject("noReadCount", noReadCount);
		mv.setViewName("system/message/index");
		return mv;
	}

	/**
	 *
	 * <b>Description:</b><br>
	 * 修改消息已读状态
	 * 
	 * @param userId
	 * @param messageUserId
	 *            用户ID，如果传的是0那么就将所有未读标记为已读
	 * @param session
	 * @return
	 * @Note <b>Author:</b> Micheal <br>
	 *       <b>Date:</b> 2017年7月7日 下午4:46:13
	 */
	@ResponseBody
	@RequestMapping(value = "/editMessageUserIsView")
	public ResultInfo editMessageUserIsView(Integer userId, Integer messageUserId) {
		ResultInfo results = messageService.editMessageUserIsView(userId, messageUserId);
		Message noReadMessage = new Message();
		noReadMessage.setIsView(0);
		Integer noReadCount = messageService.getMessageCount(noReadMessage, userId);
		results.setData(noReadCount);
		return results;
	}

	/**
	 *
	 * <b>Description:</b><br>
	 * 查询未读消息数
	 * 
	 * @param userId
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2017年12月6日 上午10:58:10
	 */
	@ResponseBody
	@RequestMapping(value = "/getMessageCount")
	public ResultInfo<Integer> getMessageCount(Integer userId) {
		ResultInfo<Integer> resultInfo = new ResultInfo<Integer>();
		Message noReadMessage = new Message();
		noReadMessage.setIsView(0);
		Integer noReadCount = messageService.getMessageCount(noReadMessage, userId);
		if (null != noReadCount) {
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
			resultInfo.setData(noReadCount);
		}
		return resultInfo;
	}

	/**
	 *
	 * <b>Description:</b><br>
	 * 查询当前用户所有未读消息
	 * 
	 * @param userId
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2019年4月11日 上午10:58:10
	 */
	@ResponseBody
	@RequestMapping(value = "/getAllMessageNoread")
	public ResultInfo<List<Message>> getAllMessageNoread(Integer userId) {
		ResultInfo<List<Message>> resultInfo = new ResultInfo<List<Message>>();
		Message noReadMessage = new Message();
		noReadMessage.setIsView(0);
		try {
			// 查询未读消息
			List<Message> list = messageService.getMessageList(userId);
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
			resultInfo.setData(list);
			return resultInfo;
		} catch (Exception e) {
			log.error("getAllMessageNoread:", e);
			return new ResultInfo(-1, "操作失败");
		}
	}

	/**
	 * @Description: 用户未读商机数
	 * @Param: [userId]
	 * @return: com.vedeng.common.model.ResultInfo<java.lang.Integer>
	 * @Author: scott.zhu
	 * @Date: 2019/4/12
	 */
	@ResponseBody
	@RequestMapping(value = "/queryNoReadMsgNum")
	public ResultInfo<Integer> queryNoReadMsgNum(Integer userId, String messageId) {
		ResultInfo<Integer> resultInfo = new ResultInfo<Integer>();
		Message noReadMessage = new Message();

		noReadMessage.setIsView(0);
		try {
			// 查询未读消息
			if (StringUtil.isNotBlank(messageId)) {
				// 将消息置为已读
				MessageUser messageUser = new MessageUser();
				messageUser.setIsView(1);// 已读
				messageUser.setUserId(userId);
				messageUser.setMessageId(Integer.parseInt(messageId));
				messageService.modifyMessageViewStatus(messageUser);
			}
			int num = messageService.queryNoReadMsgNum(userId);
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
			resultInfo.setData(num);
			return resultInfo;
		} catch (Exception e) {
			log.error("queryNoReadMsgNum:", e);
			return new ResultInfo(-1, "操作失败");
		}
	}
}
