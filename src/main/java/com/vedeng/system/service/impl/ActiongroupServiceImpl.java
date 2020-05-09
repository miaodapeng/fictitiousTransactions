package com.vedeng.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vedeng.authorization.dao.ActiongroupMapper;
import com.vedeng.authorization.dao.RRoleActiongroupMapper;
import com.vedeng.authorization.dao.RUserRoleMapper;
import com.vedeng.authorization.model.Actiongroup;
import com.vedeng.authorization.model.RRoleActiongroup;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.shiro.JedisUtils;
import com.vedeng.system.service.ActiongroupService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("actiongroupService")
public class ActiongroupServiceImpl extends BaseServiceimpl implements ActiongroupService {

	@Autowired
	@Qualifier("actiongroupMapper")
	private ActiongroupMapper actiongroupMapper;
	@Autowired
	@Qualifier("rRoleActiongroupMapper")
	private RRoleActiongroupMapper rRoleActiongroupMapper;
	@Resource
	private RUserRoleMapper rUserRoleMapper;

	//使用gson主要是由于net.sf.json没法解决List嵌套问题
	private Gson gson = new Gson();

	/**
	 * 查询部门层级结构
	 */
	@Override
	public List<Actiongroup> getActionGroupList() {
		List<Actiongroup> groupList = actiongroupMapper.getActionGroupList();

		JSONArray jsonArray = (JSONArray) JSONArray.fromObject(groupList);
		List<Actiongroup> sellist = new ArrayList<Actiongroup>();

		JSONArray jsonList = treeMenuList(jsonArray, 0, "");
		List<Actiongroup> list = resetList(jsonList, sellist, 0);
		return list;
	}

	/**
	 * 
	 * @Title: treeMenuList @Description: 递归组装树形结构 @param @param
	 * menuList @param @param parentId @param @return @return JSONArray @throws
	 */
	public JSONArray treeMenuList(JSONArray menuList, int parentId, String parentName) {
		JSONArray childMenu = new JSONArray();
		for (Object object : menuList) {
			JSONObject jsonMenu = (JSONObject) JSONObject.fromObject(object);
			int menuId = jsonMenu.getInt("actiongroupId");
			int pid = jsonMenu.getInt("parentId");
			if (parentName != "") {
				jsonMenu.element("nameArr", parentName + "--" + jsonMenu.getString("name"));
			} else {
				jsonMenu.element("nameArr", jsonMenu.getString("name"));
			}
			if (parentId == pid) {
				JSONArray c_node = treeMenuList(menuList, menuId, jsonMenu.getString("nameArr"));
				jsonMenu.put("childNode", c_node);
				childMenu.add(jsonMenu);
			}
		}
		return childMenu;
	}

	/**
	 * 
	 * @Title: resetList @Description: 递归分析树状结构 @param @param
	 * tasklist @param @param sellist @param @param num @param @return @return
	 * List<SelectModel> @throws
	 */
	public List<Actiongroup> resetList(JSONArray tasklist, List<Actiongroup> sellist, int num) {
		String str = "";
		for (int i = 0; i < (num * 2); i++) {
			str += "-";
		}
		for (Object obj : tasklist) {
			JSONObject jsonMenu = (JSONObject) JSONObject.fromObject(obj);
			Actiongroup sm = new Actiongroup();
			sm.setActiongroupId(Integer.valueOf(jsonMenu.getInt("actiongroupId")));
			sm.setName(str + "├" + jsonMenu.getString("name"));
			sm.setNameArr(jsonMenu.getString("nameArr"));
			sm.setLevel(jsonMenu.getInt("level"));
			sm.setOrderNo(jsonMenu.getInt("orderNo"));
			sm.setParentId(jsonMenu.getInt("parentId"));
			sm.setPlatformId(jsonMenu.getInt("platformId"));
			sellist.add(sm);
			if (jsonMenu.get("childNode") != null) {
				if (JSONArray.fromObject(jsonMenu.get("childNode")).size() > 0) {
					num++;
					resetList(JSONArray.fromObject(jsonMenu.get("childNode")), sellist, num);
					num--;
				}
			}
		}
		return sellist;
	}

	@Override
	public int insert(Actiongroup actiongroup) {
		// 获取新父节点级别
		Integer parent_level = actiongroupMapper.getParentLevel(actiongroup);
		if(parent_level==null){
			parent_level = 1;
		}else{
			parent_level += 1;
		}
		actiongroup.setLevel(parent_level);
		return actiongroupMapper.insert(actiongroup);
	}

	@Override
	public Actiongroup getActionGroupByKey(Actiongroup actiongroup) {
		return actiongroupMapper.getActionGroupByKey(actiongroup);
	}

	/**
	 * @Transactional 标记方法添加事物
	 *                readOnly：该属性用于设置当前事务是否为只读事务，设置为true表示只读，false则表示可读写，
	 *                默认值为false。
	 *                rollbackFor：该属性用于设置需要进行回滚的异常类数组，当方法中抛出指定异常数组中的异常时，
	 *                则进行事务回滚rollbackFor={RuntimeException.class,
	 *                Exception.class}) propagation =
	 *                Propagation.REQUIRED：如果有事务, 那么加入事务, 没有的话新建一个(默认情况下)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	public int update(Actiongroup actiongroup) throws Exception {
		List<Actiongroup> groupList = actiongroupMapper.getActionGroupList();
		JSONArray jsonArray = (JSONArray) JSONArray.fromObject(groupList);
		JSONArray jsonList = treeMenuList(jsonArray, actiongroup.getActiongroupId(), "");
		int old_level = actiongroup.getLevel();
		Integer new_level = null;
		if (actiongroup.getParentId() == 0) {
			new_level = 1;
		} else {
			// 获取新父节点级别
			new_level = actiongroupMapper.getParentLevel(actiongroup);
			if(new_level==null){
				new_level = 1;
			}else{
				new_level += 1;
			}
		}
		actiongroup.setLevel(new_level);
		int n = actiongroupMapper.update(actiongroup);
		if (n == 1) {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for (Object object : jsonList) {
				Map<String, Object> map = new HashMap<String, Object>();
				JSONObject jsonMenu = (JSONObject) JSONObject.fromObject(object);
				map.put(String.valueOf(jsonMenu.getInt("actiongroupId")),
						Integer.valueOf(jsonMenu.getInt("level")) - old_level + new_level);
				list.add(map);
			}
			if (!list.isEmpty()) {
				n = actiongroupMapper.batchUpdateLevel(list);
				/*
				 * if(i>0){ return actiongroupMapper.update(actiongroup); }
				 */
			}
		}
		return n;
	}

	@Override
	public Integer delete(Actiongroup actiongroup) {
		return actiongroupMapper.delete(actiongroup.getActiongroupId());
	}
	/**
	 * <b>Description:</b><br>跟据actiongroupid获取角色列表
	 * @param actiongroupId
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年5月10日 上午11:24:55
	 */
	@Override
	public List<RRoleActiongroup> getRRoleActiongroupListByActiongroupId(Integer actiongroupId) {
		
		return rRoleActiongroupMapper.getRRoleActiongroupListByActiongroupId(actiongroupId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Actiongroup> getMenuActionGroupList(User user) {
		List<Actiongroup> list = null;
		if(user.getIsAdmin().equals(2)){//超级管理员
			if(JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_GROUP_MENU+user.getUsername())){
				list = gson.fromJson(JedisUtils.get(dbType+ErpConst.KEY_PREFIX_GROUP_MENU+user.getUsername()), new TypeToken<List<Actiongroup>>() {}.getType());
			}else{
				List<Actiongroup>tempList = actiongroupMapper.getActionGroupList();
				//组装下级数据
				list = treeGroupMenuList(tempList,0);
				JedisUtils.set(dbType+ErpConst.KEY_PREFIX_GROUP_MENU + user.getUsername(),  gson.toJson(list), 0);
			}
		}else{
			if(JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_GROUP_MENU+user.getUserId())){
				list= gson.fromJson(JedisUtils.get(dbType+ErpConst.KEY_PREFIX_GROUP_MENU+user.getUserId()), new TypeToken<List<Actiongroup>>() {}.getType());
			}else{
				List<Integer> roleList = rUserRoleMapper.getRoleIdListByUserId(user.getUserId());
				List<Actiongroup>tempList = actiongroupMapper.getActionGroupListByRoleIds(roleList,user.getCurrentLoginSystem());
				//组装下级数据
				list = treeGroupMenuList(tempList,0);
				JedisUtils.set(dbType+ErpConst.KEY_PREFIX_GROUP_MENU + user.getUserId(), gson.toJson(list), 0);
			}
		}
		return list;
	}

	@Override
	public List<Actiongroup> getMenuActionGroupListForApi(User user) {


		if(user.getIsAdmin() == 2){
			//针对Api接口不能访问超级管理员的菜单项
			return null;
		}

		List<Integer> roleList = rUserRoleMapper.getRoleIdListByUserId(user.getUserId());
		List<Actiongroup> tempList = actiongroupMapper.getActionGroupListByRoleIds(roleList, user.getCurrentLoginSystem());
		//组装下级数据
		return treeGroupMenuList(tempList, 0);
	}


	/**
	 * 解析多层ActionGroup数据
	 * @author wayne.liu
	 * @date  2019/6/11 16:07
	 * @param
	 * @return
	 */
	private List<Actiongroup> treeGroupMenuList(List<Actiongroup> groupList,int parentId) {
		List<Actiongroup> temp = new ArrayList<>();
		groupList.forEach(each->{
			int menuId = each.getActiongroupId();
			int pid = each.getParentId();
			if (parentId == pid) {
				List<Actiongroup> child = treeGroupMenuList(groupList, menuId);
				Actiongroup actiongroup = new Actiongroup();
				actiongroup.setActiongroupId(menuId);
				actiongroup.setName(each.getName());
				actiongroup.setLevel(each.getLevel());
				actiongroup.setParentId(each.getParentId());
				actiongroup.setChildNode(child);
				temp.add(actiongroup);
			}
		});
		return temp;
	}


	@Override
	public List<Actiongroup> getActionGroupListByPlatform(Integer platformId) {
		List<Actiongroup> groupList = actiongroupMapper.getActionGroupListByPlatformId(platformId);

		JSONArray jsonArray = (JSONArray) JSONArray.fromObject(groupList);
		List<Actiongroup> sellist = new ArrayList<Actiongroup>();

		JSONArray jsonList = treeMenuList(jsonArray, 0, "");
		List<Actiongroup> list = resetList(jsonList, sellist, 0);
		return list;
	}
}
