package com.vedeng.system.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.vedeng.authorization.dao.*;
import com.vedeng.authorization.model.*;
import com.vedeng.authorization.model.vo.RRoleActionGroupVo;
import com.vedeng.authorization.model.vo.RRoleActionVo;
import com.vedeng.authorization.model.vo.RoleVo;
import com.vedeng.common.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.model.SelectModel;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.system.service.RoleService;

@Service("roleService")
public class RoleServiceImpl extends BaseServiceimpl implements RoleService {
	@Autowired
	@Qualifier("roleMapper")
	private RoleMapper roleMapper;
	
	@Autowired
	@Qualifier("actiongroupMapper")
	private ActiongroupMapper actiongroupMapper;
	
	@Autowired
	@Qualifier("actionMapper")
	private ActionMapper actionMapper;

	@Autowired
	@Qualifier("rolePlatformMapper")
	private RolePlatformMapper rolePlatformMapper;

	@Autowired
	@Qualifier("dataAuthorityMapper")
	private DataAuthorityMapper dataAuthorityMapper;

	@Autowired
	@Qualifier("rRoleActiongroupMapper")
	private RRoleActiongroupMapper rRoleActiongroupMapper;

	@Autowired
	@Qualifier("rRoleActionMapper")
	private RRoleActionMapper rRoleActionMapper;

	/**
	 * 分页搜索角色
	 * @param role
	 * @param page
	 * @return
	 */
	@Override
	public List<Role> querylistPage(Role role, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("role", role);
		map.put("page", page);
		return roleMapper.querylistPage(map);
	}

	/**
	 * 根据主键查询详情信息
	 */
	@Override
	public Role getRoleByKey(Role role) {
		return roleMapper.getRoleByKey(role);
	}

	/**
	 * 保存角色信息 
	 */
	@Override
	public ResultInfo<?> insert(Role role) {
		//验证角色信息是否重复
		int i= roleMapper.vailRole(role);
		if(i!=0){
			return new ResultInfo<>(-1, "角色名称重复，无法提交。");
		}
		int j = roleMapper.insert(role);
		if(j==1){
			return new ResultInfo<>(0, "操作成功");
		}
		return new ResultInfo<>();
	}

	/**
	 * 修改角色信息
	 */
	@Override
	public ResultInfo<?> update(Role role) {
		//验证角色信息是否重复
		int i= roleMapper.vailRole(role);
		if(i!=0){
			return new ResultInfo<>(-1, "角色名称重复，无法提交。");
		}
		int j = roleMapper.update(role);
		if(j==1){
			return new ResultInfo<>(0, "操作成功");
		}
		return new ResultInfo<>();
	}
	/**
	 * 获取所有角色 
	 */
	@Override
	public List<Role> getAllRoles(Role role) {
		return roleMapper.getAllRoles(role);
	}
	
	/**
	 * 查询用户角色
	 */
	@Override
	public List<Role> getUserRoles(Integer userId) {
		return roleMapper.getUserRoles(userId);
	}

	@Override
	public List<SelectModel> queryMenuList() {
		List<Actiongroup> groupList = actiongroupMapper.getActionGroupList();
		
		JSONArray jsonArray = (JSONArray) JSONArray.fromObject(groupList);
		
        JSONArray jsonList = treeMenuList(jsonArray,0);
        
        List<SelectModel> sellist = new ArrayList<SelectModel>();
        List<SelectModel> list = resetList(jsonList,sellist,0);
        for(int i=0;i<list.size();i++){
        	list.get(i).setList(actionMapper.selectActionList(Integer.valueOf(list.get(i).getId())));
        }
        return list;
	}

	@Override
	public List<Actiongroup> queryMenuListByPlatformId(Integer platformId) {

		List<Actiongroup> groupList = actiongroupMapper.getActionGroupListByPlatformId(platformId);

		List<Actiongroup> list = treeGroupMenuList(groupList,0);

		//读取当前平台所有的功能点
		List<Action> actions = actionMapper.getActionListByPlatformId(platformId);

		//获取功能分组和功能点Map数据
		Map<Integer, List<Action>> groupActionMap = actions.stream().collect(Collectors.groupingBy(Action::getActiongroupId));

		setActionInActionGroup(list,groupActionMap);

		return list;
	}

	/**
	  * 组装功能组和功能点数据
	  * @author wayne.liu
	  * @date  2019/6/11 14:14
	  * @param 
	  * @return 
	  */
	private void setActionInActionGroup(List<Actiongroup> list,Map<Integer, List<Action>> groupActionMap){
		list.forEach(e->{
			if(CollectionUtils.isEmpty(e.getChildNode())){
				//为空说明当前actionGroup是最底一层分组,读取list<Action>内group是当前分组下的所有功能点
				e.setActionList(groupActionMap.get(e.getActiongroupId()));
			}else {
				setActionInActionGroup(e.getChildNode(),groupActionMap);
			}
		});

	}


	/**
	 * 组装功能组和功能点数据
	 * @author wayne.liu
	 * @date  2019/6/11 14:14
	 * @param
	 * @return
	 */
	private void setActionVoInActionGroupVo(List<RRoleActionGroupVo> list,Map<Integer, List<RRoleActionVo>> groupActionMap){
		list.forEach(e->{
			if(CollectionUtils.isEmpty(e.getChildNode())){
				//为空说明当前actionGroup是最底一层分组,读取list<Action>内group是当前分组下的所有功能点
				e.setActionList(groupActionMap.get(e.getActiongroupId()));
			}else {
				setActionVoInActionGroupVo(e.getChildNode(),groupActionMap);
			}
		});

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
				actiongroup.setPlatformId(each.getPlatformId());
				actiongroup.setChildNode(child);
				temp.add(actiongroup);
			}
		});
		return temp;
	}

	/**
	 * 解析多层ActionGroup数据
	 * @author wayne.liu
	 * @date  2019/6/11 16:07
	 * @param
	 * @return
	 */
	private List<RRoleActionGroupVo> treeRoleGroupMenuList(List<RRoleActionGroupVo> groupList,int parentId) {
		List<RRoleActionGroupVo> temp = new ArrayList<>();
		groupList.forEach(each->{
			int menuId = each.getActiongroupId();
			int pid = each.getParentId();
			if (parentId == pid) {
				List<RRoleActionGroupVo> child = treeRoleGroupMenuList(groupList, menuId);
				RRoleActionGroupVo actiongroup = new RRoleActionGroupVo();
				actiongroup.setActiongroupId(menuId);
				actiongroup.setGroupName(each.getGroupName());
				actiongroup.setParentId(each.getParentId());
				actiongroup.setPlatformId(each.getPlatformId());
				actiongroup.setChildNode(child);
				temp.add(actiongroup);
			}
		});
		return temp;
	}


	private JSONArray treeMenuList(JSONArray menuList, int parentId) {
        JSONArray childMenu = new JSONArray();
        for (Object object : menuList) {
            JSONObject jsonMenu = (JSONObject) JSONObject.fromObject(object);
            int menuId = jsonMenu.getInt("actiongroupId");
            int pid = jsonMenu.getInt("parentId");
            if (parentId == pid) {
                JSONArray c_node = treeMenuList(menuList, menuId);
                jsonMenu.put("childNode", c_node);
                childMenu.add(jsonMenu);
            }
        }
        return childMenu;
    }

	public List<SelectModel> resetList(JSONArray tasklist,List<SelectModel> sellist,int num){
        String str = "";
        /*for(int i=0;i<(num*2);i++){
            str += "-";
        }*/
        for(Object obj:tasklist){
            JSONObject jsonMenu = (JSONObject) JSONObject.fromObject(obj);
            SelectModel sm = new SelectModel();
            sm.setId(jsonMenu.getString("actiongroupId"));
            sm.setText(str+jsonMenu.getString("name"));
            sm.setLevel(jsonMenu.getString("level"));
            sm.setStr(jsonMenu.getString("parentId"));
            sellist.add(sm);
            if(jsonMenu.get("childNode")!=null){
                if(JSONArray.fromObject(jsonMenu.get("childNode")).size()>0){
                    num++;
                    resetList(JSONArray.fromObject(jsonMenu.get("childNode")),sellist,num);
                    num--;
                }
            }
        }
        return sellist;
    }

	@Override
	public Integer delete(Role role) {
		return roleMapper.delete(role.getRoleId());
	}

	@Override
	public List<Role> getRoleByActiongroupId(Integer actiongroupId) {
		return roleMapper.getRoleByActiongroupId(actiongroupId);
	}

	@Override
	public List<Role> getRoleByActionId(Integer actionId) {
		return roleMapper.getRoleByActionId(actionId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	public ResultInfo<?> saveMenu(Integer roleId, List<String> groupIdList, List<String> actionIdList,Integer platformId) {
		if(roleId==null || groupIdList.isEmpty() || actionIdList.isEmpty()){
			return new ResultInfo<>(-1, "操作失败！请选择相应功能");
		}
		roleMapper.deleteAction(roleId,platformId);
		roleMapper.deleteGroup(roleId,platformId);
		int i = roleMapper.insertRoleAction(roleId,actionIdList,platformId);
		int j = roleMapper.insertRoleGroup(roleId,groupIdList,platformId);
		if(i==j && i == 1){
			return new ResultInfo<>(0, "操作成功");
		}
		return new ResultInfo<>(-1, "操作失败");
	}

	@Override
	public List<RRoleAction> getRoleAction(Role role,Integer platformId) {
		return roleMapper.getRoleAction(role,platformId);
	}
	@Override
	public List<RRoleActiongroup> getRoleGroup(Role role, Integer platformId) {
		return roleMapper.getRoleGroup(role,platformId);
	}

	@Override
	public List<RRoleActionVo> getRoleAllAction(Role role,Integer platformId) {
		return roleMapper.getChoosedRoleAction(role,platformId);
	}

	@Override
	public List<RRoleActionGroupVo> getRoleAllGroup(Role role,Integer platformId) {

		List<RRoleActionGroupVo> list = roleMapper.getChoosedRoleGroup(role,platformId);

		List<RRoleActionGroupVo> treeList = treeRoleGroupMenuList(list,0);

		//读取已选择的所有功能点
		List<RRoleActionVo> actions = roleMapper.getChoosedRoleAction(role,platformId);

		//获取功能分组和功能点Map数据
		Map<Integer, List<RRoleActionVo>> groupActionMap = actions.stream().collect(Collectors.groupingBy(RRoleActionVo::getActiongroupId));

		setActionVoInActionGroupVo(treeList,groupActionMap);

		return treeList;

	}

	@Override
	public List<Role> getUserRoles(User user) {
		List<Role> list=null;
		if("admin".equalsIgnoreCase(user.getUsername())){
			list=roleMapper.getAdminUserRoles();
		}else{
			list=roleMapper.getUserRolesByUserName(user);
		}
		return list;
	}
	
	@Override
	public List<Integer> getUserIdByRoleName(String roleName,Integer companyId) {
		return roleMapper.getUserIdByRoleName(roleName,companyId);
	}

	@Override
	public ResultInfo<?> insertRole(RoleVo roleVo) {

		if(CollectionUtils.isEmpty(roleVo.getPlatformId())){
			return new ResultInfo<>(-1, "应用系统为空，无法提交。");
		}

		Role addRole = new Role();
		addRole.setRoleName(roleVo.getRoleName());
		addRole.setCompanyId(roleVo.getCompanyId());
		ResultInfo<?> resultRole = insert(addRole);
		if(resultRole.getCode() != 0){
			return resultRole;
		}
		roleVo.setRoleId(addRole.getRoleId());
		int result = rolePlatformMapper.insertBatch(getListData(roleVo));
		return result >= 1 ? new ResultInfo<>(0, "插入成功")
				: new ResultInfo<>();
	}

	@Override
	public ResultInfo<?> updateRole(RoleVo roleVo) {

		if(CollectionUtils.isEmpty(roleVo.getPlatformId())){
			return new ResultInfo<>(-1, "应用系统为空，无法提交。");
		}

		Role updateRole = new Role();
		updateRole.setRoleId(roleVo.getRoleId());
		updateRole.setRoleName(roleVo.getRoleName());
		updateRole.setCompanyId(roleVo.getCompanyId());
		ResultInfo<?> resultRole = update(updateRole);
		if(resultRole.getCode() != 0){
			return resultRole;
		}

		//读取当前已存在的
		List<RolePlatform> exist = rolePlatformMapper.queryList(roleVo.getRoleId());

		List<Integer> oldIds = exist.stream().map(RolePlatform::getPlatformId).collect(Collectors.toList());

		//读取当前需要新增的
		List<RolePlatform> addList = new ArrayList<>();
		List<Integer> addIds = new ArrayList<>();
		for(Integer platFormId : roleVo.getPlatformId()){
			RolePlatform addRolePlat = new RolePlatform();
			addRolePlat.setRoleId(roleVo.getRoleId());
			addRolePlat.setPlatformId(platFormId);
			addList.add(addRolePlat);
			addIds.add(platFormId);
		}

		List<Integer> deleteId = new ArrayList<>();
		for(Integer id : oldIds){
			if(!addIds.contains(id)){
				deleteId.add(id);
			}
		}
		if(deleteId.size() > 0) {
			rRoleActiongroupMapper.deleteByRoleIdAndPlatformId(roleVo.getRoleId(), deleteId);
			rRoleActionMapper.deleteByRoleIdAndPlatformId(roleVo.getRoleId(), deleteId);
		}

		rolePlatformMapper.deleteByRoleId(roleVo.getRoleId());

		int result = rolePlatformMapper.insertBatch(addList);

		return result >= 1 ? new ResultInfo<>(0, "插入成功")
				: new ResultInfo<>();
	}

	@Override
	public List<Role> queryListPage(RoleVo roleVo, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("role", roleVo);
		if(CollectionUtils.isNotEmpty(roleVo.getPlatformId()) && null != roleVo.getPlatformId().get(0)) {
			map.put("platformId", roleVo.getPlatformId().get(0));
		}
		map.put("page", page);
		return roleMapper.queryListPage(map);
	}

	/**
	  * 
	  * @author wayne.liu
	  * @date  2019/6/6 13:12
	  * @param 
	  * @return 
	  */
	private List<RolePlatform> getListData(RoleVo roleVo){
		List<RolePlatform> list = Lists.newArrayList();
		for(Integer platFormId : roleVo.getPlatformId()){
			RolePlatform addRolePlat = new RolePlatform();
			addRolePlat.setRoleId(roleVo.getRoleId());
			addRolePlat.setPlatformId(platFormId);
			list.add(addRolePlat);
		}
		return list;
	}

}
