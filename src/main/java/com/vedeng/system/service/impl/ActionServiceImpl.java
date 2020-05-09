package com.vedeng.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.vedeng.authorization.dao.ActionMapper;
import com.vedeng.authorization.dao.RRoleActionMapper;
import com.vedeng.authorization.model.Action;
import com.vedeng.authorization.model.RRoleAction;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.shiro.JedisUtils;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.system.service.ActionService;

import net.sf.json.JSONArray;

@Service("actionService")
public class ActionServiceImpl extends BaseServiceimpl implements ActionService {

	@Autowired
	@Qualifier("actionMapper")
	private ActionMapper actionMapper;
	@Autowired
	@Qualifier("rRoleActionMapper")
	private RRoleActionMapper rRoleActionMapper;
	/**
	 * 永不超时
	 * 
	 * @Fields cacheSeconds : TODO
	 */
	private static final int cacheSeconds = 0;

	/**
	 * 查询节点列表数据
	 */
	@Override
	public List<Action> querylistpage(Action action, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("action", action);
		return actionMapper.querylistpage(map);
	}

	/**
	 * 根据主键查询节点详细信息
	 */
	@Override
	public Action getActionByKey(Integer actionId) {
		return actionMapper.getActionByKey(actionId);
	}

	/**
	 * 添加节点保存数据
	 */
	@Override
	public int insert(Action action) {
		action.setAddTime(DateUtil.gainNowDate());
		return actionMapper.insert(action);
	}

	/**
	 * 根据主键修改节点信息
	 */
	@Override
	public int update(Action action) {
		action.setModTime(DateUtil.gainNowDate());
		return actionMapper.update(action);
	}

	@Override
	public Integer delete(Action action) {
		return actionMapper.delete(action.getActionId());
	}

	@Override
	public List<Action> getActionByActiongroupId(Integer actiongroupId) {
		return actionMapper.selectActionList(actiongroupId);
	}

	/**
	 * <b>Description:</b><br>
	 * 根据当前用户获取菜单，此方法调用只有缓存不存在时候才会调用
	 * 
	 * @param user
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年5月4日 下午5:35:15
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Action> getActionList(User user, String roleIds) {
		List<Action> list = null;
		if (user.getIsAdmin().equals(2)) {// 超管admin登陆展示所有菜单
			if (JedisUtils.exists(dbType + ErpConst.KEY_PREFIX_MENU + user.getUsername())) {
				String json = JedisUtils.get(dbType + ErpConst.KEY_PREFIX_MENU + user.getUsername());
				JSONArray jsonArray = JSONArray.fromObject(json);
				list = (List<Action>) JSONArray.toCollection(jsonArray, Action.class);
			} else {
				list = actionMapper.getAdminMenuList();
				JedisUtils.set(dbType + ErpConst.KEY_PREFIX_MENU + user.getUsername(),
						JsonUtils.convertConllectionToJsonStr(list), cacheSeconds);
			}
		} else {
			//读取用户当前所在系统所有角色的所有功能点
			list = actionMapper.getMenuListByUserId(user.getUserId(),user.getCurrentLoginSystem());
		}
		return list;
	}

	@Override
	public List<Action> getActionListForApi(User user) {

		if(user.getIsAdmin() == 2){
			return null;
		}
		return actionMapper.getMenuListByUserId(user.getUserId(), user.getCurrentLoginSystem());
	}

	/**
	 * <b>Description:</b><br>
	 * 获取角色与功能的列表
	 * 
	 * @param actionId
	 * @return
	 * @Note <b>Author:</b> east <br>
	 * 		<b>Date:</b> 2017年5月10日 上午11:06:22
	 */
	@Override
	public List<RRoleAction> getRRoleActionListByActionId(Integer actionId) {
		return rRoleActionMapper.getRRoleActionListByActionId(actionId);
	}

	/**
	 * <b>Description:</b><br>
	 * 根据当前用户获取菜单的权限
	 * 
	 * @param user
	 * @return
	 * @Note <b>Author:</b> east <br>
	 * 		<b>Date:</b> 2017年5月4日 下午5:35:15
	 */
	@Override
	public List<Action> getActionList(User user) {
		return actionMapper.getMenuPowerListByUserId(user.getUserId());
	}

	/**
	 * <b>Description:</b><br>
	 * 根据当前用户获取菜单的权限
	 * 
	 * @param userName
	 * @return
	 * @Note <b>Author:</b> east <br>
	 * 		<b>Date:</b> 2017年5月4日 下午5:35:15
	 */
	@Override
	public List<Action> getActionListByUserName(User user) {
		List<Action> list = null;
		if ("admin".equalsIgnoreCase(user.getUsername())) {
			list = actionMapper.getAdminMenuPowerList();
		} else {
			list = actionMapper.getMenuPowerListByUserName(user);
		}
		return list;
	}

	/**
	 * <b>Description:</b><br>
	 * 跟据请求url查询功能点
	 * 
	 * @param modelName
	 * @param controllerNmae
	 * @param actionName
	 * @return
	 * @Note <b>Author:</b> east <br>
	 * 		<b>Date:</b> 2017年9月6日 下午1:48:44
	 */
	@Override
	public Integer getActionId(String modelName, String controllerNmae, String actionName) {
		Action action = new Action();
		action.setActionName(actionName);
		action.setControllerName(controllerNmae);
		action.setModuleName(modelName);
		return actionMapper.getActionId(action);
	}

	@Override
	public List<Action> getActionListByPlatformId(Integer platformId) {
		return actionMapper.getActionListByPlatformId(platformId);
	}
}
