package com.vedeng.system.service.impl;

import com.google.common.collect.Lists;
import com.vedeng.authorization.dao.OrganizationMapper;
import com.vedeng.authorization.model.Organization;
import com.vedeng.authorization.model.User;
import com.vedeng.authorization.model.vo.OrganizationVo;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.DateUtil;
import com.vedeng.order.model.Saleorder;
import com.vedeng.system.service.OrgService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service("orgService")
public class OrgServiceImpl extends BaseServiceimpl implements OrgService {
	@Autowired
	@Qualifier("organizationMapper")
	private OrganizationMapper organizationMapper;

	@Override
	public Organization getOrgByOrgId(Integer orgId) {
		return organizationMapper.selectByPrimaryKey(orgId);
	}

	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public Integer modifyOrg(Organization org, HttpSession session) throws Exception {
		Long time = DateUtil.sysTimeMillis();
		User user =(User)session.getAttribute(ErpConst.CURR_USER);
		org.setModTime(time);
		org.setUpdater(user.getUserId());
		
		
		
		if(null != org.getOrgId() && org.getOrgId() > 0){
			//编辑
			//level维护
			Organization oldInfo = organizationMapper.selectByPrimaryKey(org.getOrgId());
			if(org.getParentId() > 0){
				
				Organization p_org = organizationMapper.selectByPrimaryKey(org.getParentId());
				org.setLevel(p_org.getLevel()+1);
				
				//部门等级发生变化
				if(oldInfo.getLevel() != (p_org.getLevel()+1)){
					//子集部门
					List<Organization> orgList = getOrgList(org.getOrgId(),org.getCompanyId(),false);
					for(Organization o : orgList){
						o.setLevel(org.getLevel()+(o.getLevel()-oldInfo.getLevel()));
						o.setModTime(time);
						o.setUpdater(user.getUserId());
						organizationMapper.update(o);
					}
				}
			}else if(oldInfo.getLevel() > 1){
				org.setLevel(1);
				//子集部门
				List<Organization> orgList = getOrgList(org.getOrgId(),org.getCompanyId(),false);
				for(Organization o : orgList){
					o.setLevel(1+(o.getLevel()-oldInfo.getLevel()));
					o.setModTime(time);
					o.setUpdater(user.getUserId());
					organizationMapper.update(o);
				}
			}
			
			return organizationMapper.update(org);
		}else{
			//新增
			//level维护
			if(org.getParentId() > 0){
				Organization p_org = organizationMapper.selectByPrimaryKey(org.getParentId());
				org.setLevel(p_org.getLevel()+1);
			}
			org.setCompanyId(user.getCompanyId());
			org.setAddTime(time);
			org.setCreator(user.getUserId());
			
			return organizationMapper.insert(org);
		}
	}


	@Override
	public Organization getOrg(Organization org) {
		return organizationMapper.getByOrg(org);
	}


	@Override
	public Integer deleteOrg(Organization org) {
		return organizationMapper.delete(org.getOrgId());
	}


	@Override
	public String getParentOrgName(Integer orgId,String join) {
		List<String> orgNameList = new ArrayList<String>();
		Organization org = organizationMapper.selectByPrimaryKey(orgId);
		String parentNameStr = "";
		Integer parentId = org.getParentId();
		orgNameList.add(org.getOrgName() + join);
		if(parentId <= 0){
			return org.getOrgName() + join;
		}
		do{
			Organization p_org = organizationMapper.selectByPrimaryKey(parentId);
			orgNameList.add(p_org.getOrgName() + join);
			parentId = p_org.getParentId();
		}while(parentId > 0);
		
		Collections.reverse(orgNameList);
		for(String name : orgNameList){
			parentNameStr += name;
		}
		
		return parentNameStr;
	}


	@Override
	public List<Organization> getOrgList(Integer parentId, Integer companyId,Boolean joinChar) {
		List<Organization> orgList = organizationMapper.getOrgList(0,companyId);
		JSONArray jsonArray = (JSONArray) JSONArray.fromObject(orgList);
		
		List<Organization> sellist = new ArrayList<Organization>();
		JSONArray jsonList = treeMenuList(jsonArray,parentId,"");
		List<Organization> list = resetList(jsonList,sellist,0,joinChar);
		return list;
	}  
	
	/**
	 * <b>Description:</b><br> 递归组装树形结构
	 * @param menuList
	 * @param parentId
	 * @param parentName
	 * @return JSONArray
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月28日 下午1:28:35
	 */
	private JSONArray treeMenuList(JSONArray menuList, int parentId,String parentName) {
        JSONArray childMenu = new JSONArray();
        for (Object object : menuList) {
            JSONObject jsonMenu = (JSONObject) JSONObject.fromObject(object);
            int menuId = jsonMenu.getInt("orgId");
            int pid = jsonMenu.getInt("parentId");
            if(parentName != ""){
            	jsonMenu.element("nameArr", parentName + "--" + jsonMenu.getString("orgName"));
            }else{
            	jsonMenu.element("nameArr", jsonMenu.getString("orgName"));
            }
            if (parentId == pid) {
                JSONArray c_node = treeMenuList(menuList, menuId,jsonMenu.getString("nameArr"));
                jsonMenu.put("childNode", c_node);
                childMenu.add(jsonMenu);
            }
        }
        return childMenu;
    }
	
	/**
	 * <b>Description:</b><br> 递归分析树状结构
	 * @param tasklist
	 * @param sellist
	 * @param num
	 * @param joinChar
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月28日 下午1:29:32
	 */
	public List<Organization> resetList(JSONArray tasklist,List<Organization> sellist,int num,Boolean joinChar){
        String str = "";
        if(joinChar){
        	for(int i=0;i<(num*2);i++){
        		str += "-";
        	}
        }
        for(Object obj:tasklist){
            JSONObject jsonMenu = (JSONObject) JSONObject.fromObject(obj);
            Organization sm = new Organization();
            sm.setOrgId(Integer.valueOf(jsonMenu.getInt("orgId")));
            String orgName = "";
            if(joinChar){
            	orgName = "|-"+str+jsonMenu.getString("orgName");
            }else{
            	orgName = jsonMenu.getString("orgName");
            }
            sm.setOrgName(orgName);
            sm.setLevel(jsonMenu.getInt("level"));
            sm.setParentId(jsonMenu.getInt("parentId"));
            sm.setAddTime(jsonMenu.getLong("addTime"));
            sm.setOrgNames(jsonMenu.getString("nameArr"));
            sm.setType(jsonMenu.getInt("type"));
            sellist.add(sm);
            if(jsonMenu.get("childNode")!=null){
                if(JSONArray.fromObject(jsonMenu.get("childNode")).size()>0){
                    num++;
                    resetList(JSONArray.fromObject(jsonMenu.get("childNode")),sellist,num,joinChar);
                    num--;
                }
            }
        }
        return sellist;
    }

	public Integer deleteOrgByOrgId(List<Integer> orgId) {
		return organizationMapper.deleteByOrgId(orgId);
	}

	@Override
	public List<Organization> getSalesOrgList(Integer orgType, Integer companyId) {
		return organizationMapper.getSalesOrgList(orgType,companyId);
	}

	@Override
	public List<Organization> getQuoteOrgList(List<Integer> orgList,Integer orgType) {
		return organizationMapper.getQuoteOrgList(orgList,orgType);
	}
	
	@Override
	public Organization getOrgNameByUserId(Integer userId) {
		return organizationMapper.getOrgNameByUserId(userId);
	}
	@Override
	public String getOrgNameById(Integer orgId) {
		return organizationMapper.getOrgNameById(orgId);
	}

	@Override
	public List<Organization> getOrgListByPositType(Integer typeId,Integer companyId) {
		//获取指定类型的部门列表
		List<Organization> orgList = organizationMapper.getOrgListByPositType(typeId,companyId);
		if(orgList.size() > 0){
			//得到高级别的部门（数字越小级别越高）
			int n = 0,parentId = 0;
			for(int i=0;i<orgList.size();i++){
				if(i!=0){
					if(n<orgList.get(i).getLevel()){
						n = orgList.get(i).getLevel();
						parentId = orgList.get(i).getParentId();
					}
				}else{
					n = orgList.get(i).getLevel();
					parentId = orgList.get(i).getOrgId();
				}
			}
			Organization organization = organizationMapper.selectByPrimaryKey(parentId);
			
			JSONArray jsonArray = (JSONArray) JSONArray.fromObject(orgList);
			
			List<Organization> sellist = new ArrayList<Organization>();
			JSONArray jsonList = treeMenuList(jsonArray,organization.getParentId(),"");
			List<Organization> list = resetList(jsonList,sellist,0,true);
			return list;
		}
		return null;
	}

	@Override
	public List<Organization> getOrgByList(List<Integer> orgIdList) throws Exception {
		return organizationMapper.getOrgByList(orgIdList);
	}

	/**
	 * <b>Description:</b><br> 获取下一级部门
	 * @param organization
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年12月7日 上午11:38:53
	 */
	@Override
	public List<OrganizationVo> getOrganizationVoList(Organization organization) {
		
		return organizationMapper.getOrganizationVoList(organization);
	}

	@Override
	public List<Integer> getOrgIds(Integer orgId,Integer companyId) {
		List<Integer> ids = new ArrayList<>();
		if(orgId != 0){
			ids.add(orgId);
		}
		List<Organization> list = this.getOrgList(orgId, companyId, true);
		if(null != list && list.size() > 0){
			for(Organization organization : list){
				ids.add(organization.getOrgId());
			}
		}
		return ids;
	}
	@Override
	public List<Integer> getOrgIdsByParentId(Integer orgId,Integer companyId) {
		List<Integer> ids = new ArrayList<>();
		if(orgId != 0){
			ids.add(orgId);
		}
		List<Organization> list =organizationMapper.getOrgList(orgId,companyId);;
		if(null != list && list.size() > 0){
			for(Organization organization : list){
				ids.add(organization.getOrgId());
			}
		}
		return ids;
	}

	@Override
	public List<Organization> getParentOrgList(Integer orgId) {
		List<Organization> orgList = new ArrayList<>();
		Organization org = organizationMapper.selectByPrimaryKey(orgId);
		if(null != org){
			orgList.add(org);
			Integer parentId = org.getParentId();
			
			do{
				Organization p_org = organizationMapper.selectByPrimaryKey(parentId);
				if(null != p_org){
					orgList.add(p_org);
					parentId = p_org.getParentId();
				}
			}while(parentId > 0);
			
			Collections.reverse(orgList);
		}
		return orgList;
	}

	@Override
	public List<User> getUserByTraderIdList(List<Integer> traderIdList, Integer traderType) {
		return organizationMapper.getUserByTraderIdList(traderIdList,traderType);
	}

	@Override
	public User getTraderUserAndOrgByTraderId(Integer traderId, Integer traderType) {
		return organizationMapper.getTraderUserAndOrgByTraderId(traderId,traderType);
	}

	@Override
	public List<Organization> getAllOrganizationListByType(Integer typeId, Integer companyId) {
		//获取指定类型的部门列表
		List<Organization> orgList = organizationMapper.getOrgListByPositType(typeId,companyId);
		if (CollectionUtils.isNotEmpty(orgList)){
			//找出部门列表中最顶级的部门列表
			List<Organization> upOrgList = Lists.newArrayList();
			for (int i=0;i<orgList.size();i++){
				int count = 0;
				for (int j= 0;j<orgList.size();j++){
					if (orgList.get(i).getParentId().equals(orgList.get(j).getOrgId())){
						break;
					}
					count++;
				}
				if (count==orgList.size()){
					upOrgList.add(orgList.get(i));
				}
			}
			//根据顶级部门查出他们的上级部门
			List<Organization> organizationList = organizationMapper.getParentOrgListByList(upOrgList);
			orgList.addAll(organizationList);
			//得到高级别的部门（数字越小级别越高）
			int n = 0,parentId = 0;
			for(int i=0;i<orgList.size();i++){
				if(i!=0){
					if(n<orgList.get(i).getLevel()){
						n = orgList.get(i).getLevel();
						parentId = orgList.get(i).getParentId();
					}
				}else{
					n = orgList.get(i).getLevel();
					parentId = orgList.get(i).getOrgId();
				}
			}
			Organization organization = organizationMapper.selectByPrimaryKey(parentId);

			JSONArray jsonArray = (JSONArray) JSONArray.fromObject(orgList);

			List<Organization> sellist = new ArrayList<Organization>();
			JSONArray jsonList = treeMenuList(jsonArray,organization.getParentId(),"");
			List<Organization> list = resetList(jsonList,sellist,0,true);
			return list;
		}
		return null;
	}

	@Override
	public List<User> getUserListBtOrgId(Integer orgId,Integer type,Integer companyId) {
		//获取该部门下是否有其他相应类型的部门
		List<Organization> list = this.getOrgList(orgId, companyId, true);
		List<Integer> orgIdList = Lists.newArrayList();
		if(null != list && list.size() > 0){
			for(Organization organization : list){
				if (type.equals(organization.getType())){
					orgIdList.add(organization.getOrgId());
				}
			}
		}
		orgIdList.add(orgId);
		return organizationMapper.getUserListBtOrgId(orgIdList,type,companyId);
	}
	//待采购下拉框
	@Override
	public List<Organization> getOrgListByPositTypes(Integer companyId) {
		return organizationMapper.getOrgListByPositTypes(companyId);
	}


	@Override
	public List<Integer> getChildrenByParentId(Integer parentId, Integer companyId){
		List<Organization> organizations = organizationMapper.getAllOrganizationOfCompany(companyId);
		List<Integer> children = new ArrayList<>();
		getAllChildrenByParentId(organizations,parentId,children);
		return children;
	}


	private void getAllChildrenByParentId(List<Organization> organizations, Integer parentId, List<Integer> children){
		for (Organization item : organizations){
			if (parentId.equals(item.getParentId())) {
				children.add(item.getOrgId());
				getAllChildrenByParentId(organizations,item.getOrgId(),children);
			}
		}
	}



	@Override
	public Boolean getKYGOrgFlagByTraderId(Saleorder saleorder) {
		 boolean resulyfalg = false;
		 //获取用户部门信息
		List<User> list = getUserOrgList(saleorder);
		if(CollectionUtils.isNotEmpty(list)) {
			for (User user : list) {
				if(user!=null&&user.getOrgName()!=null&&user.getOrgName().contains("科研购")){
					resulyfalg = true;
					break;
				}
			}
		}
		return resulyfalg;
	}
	/**
	*获取用户部门信息
	* @Author:strange
	* @Date:13:01 2020-02-26
	*/
	private List<User> getUserOrgList(Saleorder saleorder) {
		List<User> list = null;
		if(saleorder != null && saleorder.getTraderId() != null) {
			//1客户 2供应商
			list = organizationMapper.getOrgByTraderId(saleorder.getTraderId(), 1);
		}else{
			//获取用户所属全部部门
			list = organizationMapper.getOrgNameListByUserId(saleorder.getUserId());
		}
		return list;
	}

}
