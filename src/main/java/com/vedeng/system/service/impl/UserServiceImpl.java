package com.vedeng.system.service.impl;

import com.google.common.collect.Lists;
import com.vedeng.authorization.dao.*;
import com.vedeng.authorization.model.*;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.ObjectUtils;
import com.vedeng.common.util.Salt;
import com.vedeng.goods.dao.RCategoryJUserMapper;
import com.vedeng.system.service.OrgService;
import com.vedeng.order.model.Saleorder;
import com.vedeng.system.service.OrgService;
import com.vedeng.system.service.UserService;
import com.vedeng.trader.dao.RTraderJUserMapper;
import com.vedeng.trader.model.RTraderJUser;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

@Service("userService")
public class UserServiceImpl extends BaseServiceimpl implements UserService {
	@Autowired
	@Qualifier("userMapper")
	private UserMapper userMapper;

	@Autowired
	@Qualifier("userDetailMapper")
	private UserDetailMapper userDetailMapper;

	@Autowired
	@Qualifier("userAddressMapper")
	private UserAddressMapper userAddressMapper;

	@Autowired
	@Qualifier("rUserRoleMapper")
	private RUserRoleMapper rUserRoleMapper;

	@Autowired
	@Qualifier("rUserPositMapper")
	private RUserPositMapper rUserPositMapper;

	@Autowired
	@Qualifier("rCategoryJUserMapper")
	private RCategoryJUserMapper rCategoryJUserMapper;

	@Resource
	private RTraderJUserMapper rTraderJUserMapper;
	@Resource
	private UserLoginLogMapper userLoginLogMapper;

	@Autowired
	@Qualifier("organizationMapper")
	private OrganizationMapper organizationMapper;

	@Autowired
	@Qualifier("userBelongCompanyMapper")
	private UserBelongCompanyMapper userBelongCompanyMapper;

	@Autowired
	private PositionMapper positionMapper;

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private OrgService orgService;

	@Value("${b2b_business_division_id}")
	private Integer b2bBusinessDivisionId;

	@Value("${yi_xie_purchase_id}")
	private Integer yiXiePurchaseId;

	@Value("${scientific_research_training_id}")
	private Integer scientificResearchTrainingId;

	@Override
	public User getUserByNameAndPositionType(String userName, Integer positionType) {
		return userMapper.getUserByNameAndPositionType(userName,positionType);
	}

	@Override
	public User login(String username, String password, Integer companyId) {
		User user = userMapper.getByUsername(username, companyId);
		if (null != user && user.getPassword().equals(DigestUtils.md5Hex(password + user.getSalt()).toString())) {
			return user;
		}
		return null;
	}

	@Override
	public List<User> querylistPage(User user, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user", user);
		map.put("page", page);
		return userMapper.querylistPage(map);

	}

	@Override
	public Boolean changDisabled(User user) {
		// 先查询员工，通过账号状态判断是启用还是禁用
		User u = userMapper.selectByPrimaryKey(user.getUserId());
		switch (u.getIsDisabled()) {
		case 0:
			user.setIsDisabled(1);
			break;
		case 1:
			user.setIsDisabled(0);
			break;
		}
		// 更新员工信息
		Integer ref = userMapper.update(user);
		if (ref > 0) {
			return true;
		}
		return false;
	}

	@Override
	public User getUserById(Integer userId) {
		return userMapper.selectByPrimaryKey(userId);
	}

	@Override
	public List<User> getAllUser(User user) {
		return userMapper.getAllUser(user);
	}

	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public Integer modifyUser(HttpSession session, User user, UserDetail userDetail, UserAddress userAddress)
			throws Exception {
		User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
		Long time = DateUtil.sysTimeMillis();

		if (null != user.getUserId() && user.getUserId() > 0) {
			// 编辑
			if (user.getPassword() != "" && user.getPassword() != null) {
				Salt salt = new Salt();
				String p_salt = salt.createSalt(false);
				user.setPassword(DigestUtils.md5Hex(user.getPassword() + p_salt).toString());
				user.setSalt(p_salt);
			}
			user.setModTime(time);
			user.setUpdater(session_user.getUserId());

			//如果staff == 0，表示非贝登员工，需要所属公司
			if(user.getStaff() == 0) {
				UserBelongCompany exist = userBelongCompanyMapper.getBelongCompany(user.getBelongCompanyName());
				if (exist != null) {
					user.setUserBelongCompanyId(exist.getUserBelongCompanyId());
				} else {
					UserBelongCompany add = new UserBelongCompany();
					add.setCompanyName(user.getBelongCompanyName());
					userBelongCompanyMapper.insertBelongCompany(add);
					user.setUserBelongCompanyId(add.getUserBelongCompanyId());
				}
			}else {
				//是贝登员工给默认值,做标识用
				user.setUserBelongCompanyId(0);
			}

			Integer succ = userMapper.update(user);
			if (succ > 0) {
				// 员工详情
				UserDetail detail = userDetailMapper.getUserDetail(user.getUserId());
				if (detail != null) {
					// 编辑详情
					// UserDetail userDetail = user.getUserDetail();
					userDetail.setUserDetailId(detail.getUserDetailId());
					if(ObjectUtils.notEmpty(user.getBirthday())){
						userDetail.setBirthday(DateUtil.StringToDate(user.getBirthday(), "yyyy-MM-dd"));
					}
					userDetailMapper.update(userDetail);

				} else {
					// 新增详情
					// UserDetail userDetail = user.getUserDetail();
					userDetail.setUserId(user.getUserId());
					if(ObjectUtils.notEmpty(user.getBirthday())){
						userDetail.setBirthday(DateUtil.StringToDate(user.getBirthday(), "yyyy-MM-dd"));
					}
					userDetailMapper.insert(userDetail);
				}

				// 员工地址
				if (null != userAddress.getAddress() || user.getProvince() > 0 || user.getCity() > 0
						|| user.getZone() > 0) {

					UserAddress address = userAddressMapper.getUserAddress(user.getUserId());
					if (address != null) {
						Integer areaId = 0;
						String areaIds = "";
						if (user.getZone() > 0) {
							areaId = user.getZone();
							areaIds = 1 + "," + user.getProvince() + "," + user.getCity() + "," + user.getZone();
						} else if (user.getCity() > 0) {
							areaId = user.getCity();
							areaIds = 1 + "," + user.getProvince() + "," + user.getCity();
						} else if (user.getProvince() > 0) {
							areaId = user.getProvince();
							areaIds = 1 + "," + user.getProvince();
						}
						// UserAddress userAddress = user.getUserAddress();
						userAddress.setUserAddressId(address.getUserAddressId());
						userAddress.setAreaId(areaId);
						userAddress.setAreaIds(areaIds);
						userAddressMapper.update(userAddress);
					} else {
						Integer areaId = 0;
						String areaIds = "";
						if (user.getZone() > 0) {
							areaId = user.getZone();
							areaIds = 1 + "," + user.getProvince() + "," + user.getCity() + "," + user.getZone();
						} else if (user.getCity() > 0) {
							areaId = user.getCity();
							areaIds = 1 + "," + user.getProvince() + "," + user.getCity();
						} else if (user.getProvince() > 0) {
							areaId = user.getProvince();
							areaIds = 1 + "," + user.getProvince();
						}
						// UserAddress userAddress = user.getUserAddress();
						userAddress.setUserId(user.getUserId());
						userAddress.setAreaId(areaId);
						userAddress.setAreaIds(areaIds);
						userAddressMapper.insert(userAddress);
					}
				}

				// 职位
				rUserPositMapper.deleteByUserId(user.getUserId());
				if (null != user.getPositionIds() && user.getPositionIds() != "") {
					String[] positIdArray = user.getPositionIds().split(",");
					for (int i = 0; i < positIdArray.length; i++) {
						if (positIdArray[i] != null && positIdArray[i] != "") {
							int positId = Integer.parseInt(positIdArray[i]);
							RUserPosit rUserPosit = new RUserPosit();
							rUserPosit.setUserId(user.getUserId());
							rUserPosit.setPositionId(positId);

							rUserPositMapper.insert(rUserPosit);
						}
					}
				}

				// 角色
				rUserRoleMapper.deleteByUserId(user.getUserId());
				if (null != user.getRoleIds() && user.getRoleIds() != "") {
					String[] roleIdArray = user.getRoleIds().split(",");
					for (int i = 0; i < roleIdArray.length; i++) {
						if (roleIdArray[i] != null && roleIdArray[i] != "") {
							int roleId = Integer.parseInt(roleIdArray[i]);
							RUserRole rUserRole = new RUserRole();
							rUserRole.setUserId(user.getUserId());
							rUserRole.setRoleId(roleId);

							rUserRoleMapper.insert(rUserRole);
						}
					}
				}
				return user.getUserId();
			}
			return 0;

		} else {

			//如果staff == 0，表示非贝登员工，需要所属公司
			if(user.getStaff() == 0) {
				UserBelongCompany exist = userBelongCompanyMapper.getBelongCompany(user.getBelongCompanyName());
				if (exist != null) {
					user.setUserBelongCompanyId(exist.getUserBelongCompanyId());
				} else {
					UserBelongCompany add = new UserBelongCompany();
					add.setCompanyName(user.getBelongCompanyName());
					userBelongCompanyMapper.insertBelongCompany(add);
					user.setUserBelongCompanyId(add.getUserBelongCompanyId());
				}
			}

			// 新增
			Salt salt = new Salt();
			String p_salt = salt.createSalt(false);
			user.setCompanyId(session_user.getCompanyId());
			user.setPassword(DigestUtils.md5Hex(user.getPassword() + p_salt).toString());
			user.setSalt(p_salt);
			user.setAddTime(time);
			user.setCreator(session_user.getUserId());
			user.setModTime(time);
			user.setUpdater(session_user.getUserId());

			userMapper.insert(user);
			Integer userId = user.getUserId();

			if (userId > 0) {
				// 员工详情
				// UserDetail userDetail = user.getUserDetail();
				userDetail.setUserId(userId);
				if(ObjectUtils.notEmpty(user.getBirthday())){
					userDetail.setBirthday(DateUtil.StringToDate(user.getBirthday(), "yyyy-MM-dd"));
				}
				userDetailMapper.insert(userDetail);

				// 员工地址
				if (null != userAddress.getAddress() || user.getProvince() > 0 || user.getCity() > 0
						|| user.getZone() > 0) {
					Integer areaId = 0;
					String areaIds = "";
					if (user.getZone() > 0) {
						areaId = user.getZone();
						areaIds = 1 + "," + user.getProvince() + "," + user.getCity() + "," + user.getZone();
					} else if (user.getCity() > 0) {
						areaId = user.getCity();
						areaIds = 1 + "," + user.getProvince() + "," + user.getCity();
					} else if (user.getProvince() > 0) {
						areaId = user.getProvince();
						areaIds = 1 + "," + user.getProvince();
					}
					// UserAddress userAddress = user.getUserAddress();
					userAddress.setUserId(userId);
					userAddress.setAreaId(areaId);
					userAddress.setAreaIds(areaIds);
					userAddressMapper.insert(userAddress);
				}
				// 职位
				if (null != user.getPositionIds() && user.getPositionIds() != "") {
					String[] positIdArray = user.getPositionIds().split(",");
					for (int i = 0; i < positIdArray.length; i++) {
						if (positIdArray[i] != null && positIdArray[i] != "") {
							int positId = Integer.parseInt(positIdArray[i]);
							RUserPosit rUserPosit = new RUserPosit();
							rUserPosit.setUserId(userId);
							rUserPosit.setPositionId(positId);

							rUserPositMapper.insert(rUserPosit);
						}
					}
				}
				// 角色
				if (null != user.getRoleIds() && user.getRoleIds() != "") {
					String[] roleIdArray = user.getRoleIds().split(",");
					for (int i = 0; i < roleIdArray.length; i++) {
						if (roleIdArray[i] != null && roleIdArray[i] != "") {
							int roleId = Integer.parseInt(roleIdArray[i]);
							RUserRole rUserRole = new RUserRole();
							rUserRole.setUserId(userId);
							rUserRole.setRoleId(roleId);

							rUserRoleMapper.insert(rUserRole);
						}
					}
				}
				return userId;

			}
			return 0;
		}
	}

	@Override
	public List<User> getUserByPositId(Integer positionId) {
		return userMapper.getUserByPositId(positionId);
	}

	@Override
	public List<User> getUserByRoleId(Integer roleId) {
		return userMapper.getUserByRoleId(roleId);
	}

	@Override
	public User getUser(User user) {
		return userMapper.getUser(user);
	}

	@Override
	public List<RUserRole> getRUserRoleListByUserId(Integer userId) {

		return rUserRoleMapper.getRUserRoleListByUserId(userId);
	}

	/**
	 * <b>Description:</b><br>
	 * 查询职位类型为311所有的员工
	 *
	 * @param positionType
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年5月16日 上午10:10:23
	 */
	@Override
	public List<User> getUserByPositType(Integer positionType, Integer companyId) {
		return userMapper.getUserByPositType(positionType, companyId);
	}
	@Override
	public List<User> getActiveUserByPositType(Integer positionType, Integer companyId) {
		return userMapper.getActiveUserByPositType(positionType, companyId);
	}

	@Override
	public List<Integer> getUserId(Integer proUserId) {
		return userMapper.getUserId(proUserId);//根据归属人查找用户id 2019-12-25
	}
	//带采购 刷新界面
	@Override
	public List<User> getUserListByOrgIddcg(Integer proOrgtId) {
		return userMapper.getUserListByOrgIdcg(proOrgtId);
	}


	/**
	 * <b>Description:</b><br>
	 * 根据userid获取dbcenter的trader表的主键
	 *
	 * @param userId
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年5月16日 下午1:18:48
	 */
	@Override
	public List<Integer> getTraderIdListByUserId(Integer userId, Integer traderType) {
		RTraderJUser rTraderJUser =new RTraderJUser();
		rTraderJUser.setUserId(userId);
		rTraderJUser.setTraderType(traderType);
		List<Integer> traList = rTraderJUserMapper.getRTraderJUserListByUserId(rTraderJUser);
		return traList;
	}

	/**
	 * <b>Description:</b><br>
	 * 保存或更新
	 *
	 * @param user
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年5月22日 上午9:52:08
	 */
	@Transactional
	@Override
	public int saveOrUpdate(User user, String ip) {
		int res = 0;
		user.setLastLoginIp(ip);
		user.setLastLoginTime(System.currentTimeMillis());
		if (null != user && null != user.getUserId()) {
			res = userMapper.update(user);
		} else {
			res = userMapper.insert(user);
		}
		return res;
	}

	@Override
	public List<User> getUserByUserIds(List<Integer> userIds) {
		return userMapper.getUserByUserIds(userIds);
	}

	@Override
	public List<User> getUserListByOrgId(Integer orgId) {
		return userMapper.getUserListByOrgId(orgId);
	}

	@Override
	public List<User> getUserListByOrgIdcg(Integer orgId) {
		return userMapper.getUserListByOrgIdcg(orgId);
	}

	@Override
	public List<Integer> getUserIdListByOrgId(Integer orgId) {
		return userMapper.getUserIdListByOrgId(orgId);
	}

	@Override
	public List<String> queryUserListByOrgId(Integer orgId) {
		return userMapper.queryUserListByOrgId(orgId);
	}

	@Override
	public int modifyPassowrd(User user) {
		user.setModTime(System.currentTimeMillis());
		return userMapper.update(user);
	}

	@Override
	public List<User> getUserListByPositType(Integer typeId,Integer companyId) {
		return userMapper.getUserListByPositType(typeId,companyId);
	}



	/**
	 * <b>Description:</b><br>
	 * 根据部门id集合查询所有员工
	 *
	 * @param orgIds
	 * @param companyId
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年7月13日 下午1:13:55
	 */
	@Override
	public List<User> getUserListByOrgIds(List<Integer> orgIds, Integer companyId) {
		return userMapper.getUserListByOrgIdList(orgIds, companyId);
	}

	/**
	 * 递归查询当前用户所有下级的用户
	 *
	 * @param userId
	 *            用户ID
	 * @param companyId
	 *            用户的公司ID
	 * @param haveMyself
	 *            返回值是否包含自己
	 * @param positionLevel
	 *            当前用户职位等级
	 * @param positionType
	 *            需要检索的职位类型 0 所有用户 1查询子集名下的用户
	 */
	/*
	 * @Override public List<User> getNextAllUserList(Integer userId, Integer
	 * companyId, boolean haveMyself, Integer positionLevel, Integer
	 * positionType) { List<User> userList = null; List<User> list = null;
	 *
	 * Boolean mySelf = false;// 是否查询自己名下的人员 if (null == positionType ||
	 * positionType.equals(0) || positionType.equals(1)) { if
	 * (positionType.equals(1)) { mySelf = true; } userList =
	 * userMapper.getAllUserList(companyId); if (!haveMyself) { for (User u :
	 * userList) { if (u.getUserId().equals(userId)) { userList.remove(u); } } }
	 * } else { if (positionType.equals(SysOptionConstant.ID_310)) {// 销售 if
	 * ((positionLevel.equals(443) || positionLevel.equals(445) ||
	 * positionLevel.equals(444)) && userId != 1) {// 销售高级主管、销售主管、销售工程师 //
	 * 查询自己下面的人员 mySelf = true; } else { userList =
	 * getUserByPositType(SysOptionConstant.ID_310, companyId); } } if
	 * (positionType.equals(SysOptionConstant.ID_311)) {// 采购 if
	 * ((positionLevel.equals(448) || positionLevel.equals(449) ) && userId !=
	 * 1) {// 采购主管、采购人员 mySelf = true; } else { userList =
	 * getUserByPositType(SysOptionConstant.ID_311, companyId); } } if
	 * (positionType.equals(SysOptionConstant.ID_312)) {// 售后 if
	 * ((positionLevel.equals(454) || positionLevel.equals(455)) && userId != 1)
	 * {// 售后主管、售前支持 mySelf = true; } else { userList =
	 * getUserByPositType(SysOptionConstant.ID_312, companyId); } } if
	 * (positionType.equals(SysOptionConstant.ID_313)) {// 物流 if
	 * ((positionLevel.equals(452) || positionLevel.equals(453)) && userId != 1)
	 * {// 物流主管、人员 mySelf = true; } else { userList =
	 * getUserByPositType(SysOptionConstant.ID_313, companyId); } } if
	 * (positionType.equals(SysOptionConstant.ID_314)) {// 财务 if
	 * ((positionLevel.equals(451)) && userId != 1) {// 财务人员 mySelf = true; }
	 * else { userList = getUserByPositType(SysOptionConstant.ID_314,
	 * companyId); } }
	 *
	 * }
	 *
	 * if (mySelf) { if (userList != null) { JSONArray jsonArray = (JSONArray)
	 * JSONArray.fromObject(userList); List<User> sellist = new
	 * ArrayList<User>();
	 *
	 * JSONArray jsonList = treeMenuList(jsonArray, userId, ""); list =
	 * resetList(jsonList, sellist, 0); } else { list = new ArrayList<User>(); }
	 * } else { list = userList; }
	 *
	 * if (haveMyself) { Boolean have = false; for(User u : list){
	 * if(u.getUserId().equals(userId)){ have = true; } } if(!have){ User user =
	 * userMapper.selectByPrimaryKey(userId); list.add(user); } }
	 *
	 * return list; }
	 */

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
	public List<Integer> getTraderIdListByUserList(List<User> userList, String traderType) {
		return userMapper.getTraderIdListByUserList(userList, traderType);
	}

	@Override
	public List<Integer> getTraderIdsByUserList(List<User> userList, Integer traderType) {
		return userMapper.getTraderIdsByUserList(userList, traderType);
	}

	@Override
	public User getUserByTraderId(Integer traderId, Integer traderType) {
		return userMapper.getUserByTraderId(traderId, traderType);
	}

	@Override
	public List<Integer> getUserIdListByTraderId(Integer traderId, Integer traderType) {
		return userMapper.getUserIdListByTraderId(traderId, traderType);
	}

	@Override
	public List<User> getMyUserList(User user, List<Integer> positionType, boolean haveDisabeldUser) {
		List<User> userList = new ArrayList<>();// 返回的用户列表
		//User user = (User) session.getAttribute(Consts.SESSION_USER); // 当前用户

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

		List<User> userByPositTypes = userMapper.getUserByPositTypes(s_u);

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
						userList = userMapper.getUserByUserIds(userIds);

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

		if(userList != null && userList.size() > 0){
			Boolean isE = false;
			for(User u : userList){
				if(u.getUserId().equals(user.getUserId())){
					isE = true;
					break;
				}
			}
			if(!isE){
				userList.add(user);
			}
		}else{
			userList.add(user);
		}
		return userList;
	}

	/**
	 * <b>Description:</b><br>
	 * 根据角色的集合查询关联用户的id集合
	 *
	 * @param roleIdList
	 * @return
	 * @Note <b>Author:</b> east <br>
	 * 		<b>Date:</b> 2017年8月2日 下午1:23:16
	 */
	@Override
	public List<Integer> getUserIdList(List<Integer> roleIdList) {
		return rUserRoleMapper.getUserIdList(roleIdList);
	}

	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public Integer addUser(HttpSession session, User user, UserDetail userDetail) throws Exception {
		User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
		Long time = DateUtil.sysTimeMillis();
		Salt salt = new Salt();
		String p_salt = salt.createSalt(false);
		user.setCompanyId(user.getCompanyId());
		user.setPassword(DigestUtils.md5Hex(user.getPassword() + p_salt).toString());
		user.setSalt(p_salt);
		user.setAddTime(time);
		user.setCreator(session_user.getUserId());
		user.setModTime(time);
		user.setUpdater(session_user.getUserId());

		userMapper.insert(user);
		Integer userId = user.getUserId();
		if (userId > 0) {
			// 员工详情
			userDetail.setUserId(userId);

			userDetailMapper.insert(userDetail);

			// 角色
			if (null != user.getRoleIds() && user.getRoleIds() != "") {
				String[] roleIdArray = user.getRoleIds().split(",");
				for (int i = 0; i < roleIdArray.length; i++) {
					if (roleIdArray[i] != null && roleIdArray[i] != "") {
						int roleId = Integer.parseInt(roleIdArray[i]);
						RUserRole rUserRole = new RUserRole();
						rUserRole.setUserId(userId);
						rUserRole.setRoleId(roleId);

						rUserRoleMapper.insert(rUserRole);
					}
				}
			}
			return userId;
		}

		return 0;
	}

	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public Integer editUser(HttpSession session, User user, UserDetail userDetail) throws Exception {
		User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
		Long time = DateUtil.sysTimeMillis();
		// 编辑
		if (user.getPassword() != "" && user.getPassword() != null) {
			Salt salt = new Salt();
			String p_salt = salt.createSalt(false);
			user.setPassword(DigestUtils.md5Hex(user.getPassword() + p_salt).toString());
			user.setSalt(p_salt);
		}
		user.setModTime(time);
		user.setUpdater(session_user.getUserId());

		Integer succ = userMapper.update(user);
		if (succ > 0) {
			// 员工详情
			UserDetail detail = userDetailMapper.getUserDetail(user.getUserId());
			if (detail != null) {
				// 编辑详情
				// UserDetail userDetail = user.getUserDetail();
				userDetail.setUserDetailId(detail.getUserDetailId());
				//userDetail.setBirthday(DateUtil.StringToDate(user.getBirthday(), "yyyy-MM-dd"));
				userDetailMapper.update(userDetail);

			} else {
				// 新增详情
				// UserDetail userDetail = user.getUserDetail();
				userDetail.setUserId(user.getUserId());
				//userDetail.setBirthday(DateUtil.StringToDate(user.getBirthday(), "yyyy-MM-dd"));
				userDetailMapper.insert(userDetail);
			}
			// 角色
			rUserRoleMapper.deleteByUserId(user.getUserId());
			if (null != user.getRoleIds() && user.getRoleIds() != "") {
				String[] roleIdArray = user.getRoleIds().split(",");
				for (int i = 0; i < roleIdArray.length; i++) {
					if (roleIdArray[i] != null && roleIdArray[i] != "") {
						int roleId = Integer.parseInt(roleIdArray[i]);
						RUserRole rUserRole = new RUserRole();
						rUserRole.setUserId(user.getUserId());
						rUserRole.setRoleId(roleId);

						rUserRoleMapper.insert(rUserRole);
					}
				}
			}
			return user.getUserId();
		}
		return 0;
	}

	@Override
	public String getUserNameByUserId(Integer userId){
		return userMapper.getUserNameByUserId(userId);
	}

	@Override
	public String getRedisDbType() {
		return dbType;
	}

	@Override
	public User getUserParentInfo(String username, Integer companyId) {
		return userMapper.getUserParentInfo(username,companyId);
	}

	@Override
	public List<User> getUserByPositLevel(Integer orgId,Integer level) {
		//查询出所有上级部门 含当前部门
		List<Integer> orgIds = new ArrayList<>();

		Organization org = new Organization();
		org.setOrgId(orgId);
		Organization orgInfo = organizationMapper.getByOrg(org);
		Integer parentId = 0;
		if(null != orgInfo && orgInfo.getParentId() != null && orgInfo.getParentId() > 0){
			parentId = orgInfo.getParentId();
			orgIds.add(orgId);
		}
		//迭代查询上级部门
		do{
			Organization orgParent = new Organization();
			orgParent.setOrgId(parentId);
			Organization orgInfoParent = organizationMapper.getByOrg(orgParent);
			if(null != orgInfoParent && orgInfoParent.getParentId() != null && orgInfoParent.getParentId() > 0){
				parentId = orgInfoParent.getParentId();
				orgIds.add(orgInfoParent.getOrgId());
			}else{
				parentId = 0;
			}

		}while(parentId > 0);

		if(orgIds.size() > 0){
			return userMapper.getUserByOrgIdsAndPositLevel(orgIds, level);
		}
		return null;
	}

	@Override
	public List<Integer> getCategoryIdListByUserId(Integer goodsUserId) {
		List<Integer> catList = rCategoryJUserMapper.getCategoryIdsByUserId(goodsUserId);
		return catList;
	}

	@Override
	public List<Integer> getCategoryIdListByUserList(List<User> userList) {
		List<Integer> catList = rCategoryJUserMapper.getCategoryIdsByUserList(userList);
		return catList;
	}

	public User getByUsername(String username,Integer companyId){
	    return userMapper.getByUsername(username,companyId);
	}

	@Override
	public User getUserInfoByTraderId(Integer traderId, Integer traderType) {
		return userMapper.getUserInfoByTraderId(traderId,traderType);
	}

	@Override
	public List<User> getTraderUserAndOrgList(List<Integer> traderIdList) {
		return userMapper.getTraderUserAndOrgList(traderIdList);
	}

	@Override
	public List<User> getUserByTraderIdList(List<Integer> traderIdList, Integer traderType) {
		return userMapper.getUserByTraderIdList(traderIdList,traderType);
	}

	@Override
	public List<Organization> getOrgNameByOrgIdList(List<Integer> orgIdList, Integer companyId) {
		return userMapper.getOrgNameByOrgIdList(orgIdList,companyId);
	}

	@Override
	public List<User> getCategoryUserList(List<Integer> categoryIdList,Integer companyId) {
		return userMapper.getCategoryUserList(categoryIdList,companyId);
	}

	@Override
	public UserDetail getUserDetailByTraderId(Integer traderId, Integer type) {
		return userDetailMapper.getUserDetailByTraderId(traderId,type);
	}

	@Override
	public List<User> getSaleUserOrgList(Integer companyId) {
		return userMapper.getSaleUserOrgList(companyId);
	}

	@Override
	public List<User> getSaleUserOrgListAll(Integer companyId) {
		return userMapper.getSaleUserOrgListAll(companyId);
	}

	@Override
	public UserDetail queryUserDetailByUserId(Integer userId)
	{
		if(null == userId)
		{
			return null;
		}
		return userDetailMapper.getUserDetail(userId);
	}

	@Override
	public UserLoginLog queryLoginOrOutInfo(UserLoginLog userLoginInfo)
	{
		if(null == userLoginInfo)
		{
			return null;
		}
		return userLoginLogMapper.queryUserLogOrOutInfo(userLoginInfo);
	}

	@Override
	public List<User> getUserListByUserIdList(List<Integer> userIdList)
	{
		return userMapper.getUserListByUserIdList(userIdList);
	}

	@Override
	public List<User> selectAllAssignUser() {
		return userMapper.selectAllAssignUser();
	}

	@Override
	public Set<User> getSubUserListForSaleApi(User user) {

		Set<User> userList = new HashSet<>();

		//获取用户的当前所有职位,正常只有一个
		List<Position> positionList = positionMapper.getPositionByUserIdAndCompanyId(user.getUserId(), user.getCompanyId());

		for (Position each : positionList) {

			if(SysOptionConstant.ID_310.equals(each.getType())){
				//读取当前部门的下级部门
				List<Organization> orgList = organizationMapper.getOrgList(each.getOrgId(), user.getCompanyId());

				if (CollectionUtils.isEmpty(orgList)) {
					//为空代表此部门是最低一级部门
					if (445 == each.getLevel()) {
						//该职位的职级为445，代表普通员工
						userList.add(getUserById(user.getUserId()));
					} else {
						//非445 说明是主管级别
						userList.addAll(getUserListByOrgId(each.getOrgId()));
					}
				} else {
					//下级部门非空的时候，需要读取下级全部员工
					userList.addAll(getUserListByOrgIds(orgList.stream().map(Organization::getOrgId).collect(Collectors.toList()),
							user.getCompanyId()));
				}
			}
		}
		return userList;
	}

	@Override
	public User getUserInfoByMobile(String traderContactMobile, Integer traderType) {
		return userMapper.getUserInfoByMobile(traderContactMobile,traderType);
	}


	@Override
	public User getBDUserInfoByMobile(String phone) {
		// TODO Auto-generated method stub
		return userMapper.getBDUserInfoByMobile(phone);
	}
	/**
	 *是否是销售  true 是 false 不是
	 * @Author:strange
	 * @Date:16:50 2020-02-21
	 */
	@Override
	public Boolean getSaleFlagUserId(Integer userId) {
        List<Role> roleList = roleMapper.getUserRoles(userId);
		boolean resultFlag = false;
		if(CollectionUtils.isNotEmpty(roleList)) {
			for (Role role : roleList) {
				String rn = role.getRoleName();
				if (rn.equals("销售总监") || rn.equals("销售主管") || rn.equals("销售工程师")
						|| rn.equals("南京贝登医疗股份有限公司管理员") || rn.equals("管理员")) {
					resultFlag = true;
					break;
				}
			}
		}
		return resultFlag;
	}
	/**
	 *是否是物流人员  true 是 false 不是
	 * @Author:strange
	 * @Date:09:07 2020-02-26
	 */
	@Override
	public List<User> getOrgUserList(Saleorder saleorder, Integer companyId) {
		List<Organization> list = orgService.getOrgList(saleorder.getOrgId(), companyId, true);
		List<Integer> orgIdList = Lists.newArrayList();
		if (null != list && list.size() > 0) {
			for (Organization organization : list) {
				if (SysOptionConstant.ID_310.equals(organization.getType())) {
					orgIdList.add(organization.getOrgId());
				}
			}
		}
		orgIdList.add(saleorder.getOrgId());
		saleorder.setOrgIdList(orgIdList);
		List<User> userList = orgService.getUserListBtOrgId(saleorder.getOrgId(), SysOptionConstant.ID_310,
				companyId);
		return userList;
	}

	@Override
    public Boolean getLogisticeFlagByUserId(Integer userId) {
        List<Role> roleList = roleMapper.getUserRoles(userId);
        boolean resultFlag = false;
        if(CollectionUtils.isNotEmpty(roleList)) {
            for (Role role : roleList) {
                String rn = role.getRoleName();
                if (rn.equals("物流总监") || rn.equals("物流专员") || rn.equals("南京贝登医疗股份有限公司管理员") || rn.equals("管理员")) {
                    resultFlag = true;
                    break;
                }
            }
        }

        return resultFlag;
    }

    @Override
    public List<Role> getRoleByUserId(Integer userId) {
        return roleMapper.getUserRoles(userId);
    }

	@Override
	public List<User> getLeadersByParentId(Integer parentId, Integer positionType) {
		List<User> leaders = new ArrayList<>();
		getLeader(parentId, positionType, leaders);
		return leaders;
	}


	/**
	 * 获取销售的归属平台
	 * VDERP-2297：去除集团业务部的判断
	 * @param userId 用户id
	 * @param companyId
	 * @return
	 */
	@Override
	public Integer getBelongPlatformOfUser(Integer userId, Integer companyId) {
		Set<Integer> b2bSet = getChildrenSetOfOrg(b2bBusinessDivisionId,companyId);
		Set<Integer> yxgSet = getChildrenSetOfOrg(yiXiePurchaseId,companyId);
		Set<Integer> kygSet = getChildrenSetOfOrg(scientificResearchTrainingId,companyId);
		List<Position> userOrgInfo = positionMapper.getOrgListOfUsers(Collections.singletonList(userId));
		Integer orgId = Integer.valueOf(userOrgInfo.get(0).getPositionName().split(",")[0]);
		if (b2bSet.contains(orgId)){
			return 1;
		} else if (yxgSet.contains(orgId)){
			return 2;
		} else if (kygSet.contains(orgId)){
			return 3;
		} else {
			return 5;
		}
	}


	private Set<Integer> getChildrenSetOfOrg(Integer parentOrgId, Integer companyId){
		List<Integer> childrenOrg = orgService.getChildrenByParentId(parentOrgId,companyId);
		//将父节点加入到相应子节点集合中
		childrenOrg.add(parentOrgId);
		//将部门节点转换为Map数据结构
		return new HashSet<>(childrenOrg);
	}


	private void getLeader(Integer parentId, Integer positionType, List<User> leaders) {
		if (parentId != null && parentId > 0) {
			User user = userMapper.getUserByIdAndPositionType(parentId, positionType);
			if (user != null) {
				leaders.add(user);
				getLeader(user.getParentId(), positionType, leaders);
			}
		}
	}

	@Override
	public User getUserByName(User u) {
		return userMapper.getUserByName2(u);
	}

	@Override
	public User getUserByNumber(User u) {
		return userMapper.getUserByNumber(u);
	}
}
