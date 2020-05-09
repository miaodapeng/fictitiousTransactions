package com.report.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.report.dao.CommonReportMapper;
import com.report.service.CommonReportService;
import com.vedeng.authorization.model.Organization;
import com.vedeng.authorization.model.Region;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.shiro.JedisUtils;
import com.vedeng.system.service.OrgService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("commonReportService")
public class CommonReportServiceImpl extends BaseServiceimpl implements CommonReportService {
	@Autowired
	@Qualifier("commonReportMapper")
	private CommonReportMapper commonReportMapper;

	@Override
	public List<User> getMyUserList(HttpSession session, List<Integer> positionType, boolean haveDisabeldUser) {
		List<User> userList = null;// 返回的用户列表
		User user = (User) session.getAttribute(Consts.SESSION_USER); // 当前用户

		User s_u = new User();// 需要检索的目标用户
		if (!user.getIsAdmin().equals(2)) {// 非超级管理员
			s_u.setCompanyId(user.getCompanyId());
		}

		if (!haveDisabeldUser) {// 非禁用用户
			s_u.setIsDisabled(0);
		}

		if (positionType != null && positionType.size() > 0) {// 目标职位集合
			s_u.setPositionTypes(positionType);
		}

		List<User> userByPositTypes = commonReportMapper.getUserByPositTypes(s_u);

		if (positionType != null && positionType.size() > 0) {
			if (!user.getIsAdmin().equals(2)) {// 非超级管理员
				Boolean isInPosit = false;// 自己当前职位是否在职位类型内
				for (Integer p : positionType) {
					if (p.equals(user.getPositType())) {
						isInPosit = true;
					}
				}

				if (isInPosit) {// 查询自己下面的用户
					List<User> treeUser = null;
					JSONArray jsonArray = (JSONArray) JSONArray.fromObject(userByPositTypes);
					List<User> sellist = new ArrayList<User>();

					JSONArray jsonList = treeMenuList(jsonArray, user.getUserId(), "");
					treeUser = resetList(jsonList, sellist, 0);
					treeUser.add(user);
					if (treeUser.size() > 1) {// 名称排序
						List<Integer> userIds = new ArrayList<>();
						for (User u : treeUser) {
							userIds.add(u.getUserId());
						}
						userList = commonReportMapper.getUserByUserIds(userIds);

					} else {
						userList = treeUser;
					}
				} else {
					userList = userByPositTypes;
				}
			} else {
				userList = userByPositTypes;
			}
		} else {
			userList = userByPositTypes;
		}

		return userList;
	}

	/**
	 * 
	 * @Title: treeMenuList @Description: 递归组装树形结构 @param @param
	 *         menuList @param @param parentId @param @return @return
	 *         JSONArray @throws
	 */
	public JSONArray treeMenuList(JSONArray menuList, int parentId, String parentName) {
		JSONArray childMenu = new JSONArray();
		for (Object object : menuList) {
			JSONObject jsonMenu = (JSONObject) JSONObject.fromObject(object);
			int menuId = jsonMenu.getInt("userId");
			int pid = jsonMenu.getInt("parentId");
			if (parentName != "") {
				jsonMenu.element("nameArr", parentName + "--" + jsonMenu.getString("username"));
			} else {
				jsonMenu.element("nameArr", jsonMenu.getString("username"));
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
	 *         tasklist @param @param sellist @param @param
	 *         num @param @return @return List<SelectModel> @throws
	 */
	public List<User> resetList(JSONArray tasklist, List<User> sellist, int num) {
		String str = "";
		for (int i = 0; i < (num * 2); i++) {
			str += "-";
		}
		for (Object obj : tasklist) {
			JSONObject jsonMenu = (JSONObject) JSONObject.fromObject(obj);
			User sm = new User();
			sm.setUserId(Integer.valueOf(jsonMenu.getInt("userId")));
			sm.setUsername(str + "├" + jsonMenu.getString("username"));
			sm.setParentId(jsonMenu.getInt("parentId"));
			sm.setCcNumber(jsonMenu.getString("ccNumber"));
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
	public List<Integer> getCommunicateRecordByDate(Long beginDate, Long endDate, String communicateType) {
		return commonReportMapper.getCommunicateRecordByDate(beginDate,endDate,communicateType);
	}

	@Override
	public List<Organization> getSalesOrgList(Integer orgType) {
		return commonReportMapper.getSalesOrgList(orgType);
	}

	@Override
	public List<Integer> getTraderIdListByUserList(List<Integer> userIdList, Integer traderType) {
		return commonReportMapper.getTraderIdListByUserList(userIdList,traderType);
	}

	@Override
	public Object getRegion(Integer regionId, Integer returnType) {
		if(regionId <= 0){
			return null;
		}
		
		List<Region> returnList = new ArrayList<Region>();
		List<String> regionName = new ArrayList<>();
		String returnString = "";
		
		//查询当前地区信息
		Region region = null;
		if(JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_REGION_OBJECT+regionId)){
			String json = JedisUtils.get(dbType+ErpConst.KEY_PREFIX_REGION_OBJECT+regionId);
			JSONObject jsonObject = JSONObject.fromObject(json);
			region = (Region) JSONObject.toBean(jsonObject, Region.class);
		}else{
			region = commonReportMapper.getRegionById(regionId);
		}
		
		Integer parentId = region.getParentId();
		returnList.add(region);
		regionName.add(region.getRegionName());
		do{
			Region regionInfo = null;
			if(JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_REGION_OBJECT+parentId)){
				String json = JedisUtils.get(dbType+ErpConst.KEY_PREFIX_REGION_OBJECT+parentId);
				JSONObject jsonObject = JSONObject.fromObject(json);
				regionInfo = (Region) JSONObject.toBean(jsonObject, Region.class);
			}else{
				regionInfo = commonReportMapper.getRegionById(parentId);
			}
			parentId = regionInfo.getParentId();
			returnList.add(regionInfo);
			regionName.add(regionInfo.getRegionName());
		}while(parentId > 0);
		
		Collections.reverse(returnList);
		Collections.reverse(regionName);
		
		for(String name : regionName){
			returnString += name + " ";
		}
		
		if (returnType == 1) {
            return returnList;
        } else {
            return returnString;
        }
	}

	@Autowired
	@Qualifier("orgService")
	private OrgService orgService;
	
	@Override
	public List<Integer> getDeptUserIdList(Integer parentId, HttpServletRequest request) {
		List<Integer> userIds = new ArrayList<>();
		if (parentId != null && parentId != 0) {
			User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
			List<Integer> orgIds = new ArrayList<>();
			List<Organization> list = orgService.getOrgList(parentId, user.getCompanyId(), false);
			if (list != null && list.size() > 0) {
				for (Organization org : list) {
					orgIds.add(org.getOrgId());
				}
			}
			if (orgIds.size() > 0) {
				List<User> uList = commonReportMapper.getUserListByOrgIdList(orgIds, user.getCompanyId());
				if (uList != null && uList.size() > 0) {
					for (User us : uList) {
						userIds.add(us.getUserId());
					}
				}
			}
		}
		return userIds;
	}

	@Override
	public List<Integer> getCategoryIdListByUserId(Integer goodsUserId) {
		return commonReportMapper.getCategoryIdListByUserId(goodsUserId);
	}
	
}
