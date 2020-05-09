package com.vedeng.firstengage.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.common.constants.Contant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.StringUtil;
import com.vedeng.firstengage.dao.FirstengageCategoryMapper;
import com.vedeng.firstengage.model.FirstengageCategory;
import com.vedeng.firstengage.service.FirstengageCategoryService;
import com.vedeng.goods.dao.RCategoryJUserMapper;
import com.vedeng.goods.model.RCategoryJUser;
import com.vedeng.goods.model.StandardCategory;
import com.vedeng.homepage.service.MyHomePageService;
import com.vedeng.system.dao.StandardCategoryMapper;
import com.vedeng.system.model.vo.ParamsConfigVo;
import com.vedeng.system.service.UserService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Service("firstengageCategoryService")
public class FirstengageCategoryServiceImpl extends BaseServiceimpl implements FirstengageCategoryService{
	public static Logger logger = LoggerFactory.getLogger(FirstengageCategoryServiceImpl.class);


	@Autowired
	@Qualifier("rCategoryJUserMapper")
	private RCategoryJUserMapper rCategoryJUserMapper;
	@Autowired
	@Qualifier("myHomePageService")
	private MyHomePageService myHomePageService;
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	@Autowired
	@Qualifier("firstengageCategoryMapper")
	private FirstengageCategoryMapper categoryMapper;
	
	@Autowired
	@Qualifier("standardCategoryMapper")
	private StandardCategoryMapper standardCategoryMapper;

	@Override
	public Map<String,Object> getCategoryListPage(FirstengageCategory category,Page page) {
		List<FirstengageCategory> list = null;
		Map<String,Object> resultMap = new HashMap<>();
		Map<String,Object> queryMap = new HashMap<>();

		queryMap.put("page", page);
		queryMap.put("category", category);
		list =  categoryMapper.getCategorylistpage(queryMap);
			
		resultMap.put("list", list);
		resultMap.put("page", page);
		
		return resultMap;
	}

	
	@Override
	public List<FirstengageCategory> getCategoryIndexList(FirstengageCategory category) {
    	List<FirstengageCategory> list = null;
    	List<FirstengageCategory> listLevel = new ArrayList<>();
    	FirstengageCategory categoryAll = new FirstengageCategory();
       categoryAll.setCompanyId(category.getCompanyId());
       if(category.getCategoryName() != null && category.getCategoryName() != ""){
	   categoryAll.setCategoryName(category.getCategoryName());
       }
       if(category.getCategoryIds() != null){
	   categoryAll.setCategoryIds(category.getCategoryIds());
       }
       categoryAll.setParentId(null);
	//根据分类名称查询分类ID
	list = categoryMapper.getCategoryIndexList(categoryAll);
	
	Map<Integer, Object> Map = new HashMap<Integer, Object>();
	Map<Integer, Object> mapList = new HashMap<Integer, Object>();
	
	for(int j=0;j<list.size();j++){
		Integer parentIdNum = (Integer)Map.get(list.get(j).getParentId());
		if(parentIdNum!=null){
			Integer newnum= parentIdNum.intValue()+(list.get(j).getGoodsNum()==null?0:list.get(j).getGoodsNum().intValue());
		   	Map.put(list.get(j).getParentId(), newnum);
		}else{
			Map.put(list.get(j).getParentId(), list.get(j).getGoodsNum()==null?0:list.get(j).getGoodsNum());
		}
		mapList.put(list.get(j).getCategoryId(), list.get(j));
	}
	for(Integer key:Map.keySet()){
		FirstengageCategory categoryInfo = (FirstengageCategory) mapList.get(key);
	    	if(categoryInfo !=null ){
	    	    Map.put(categoryInfo.getParentId(),Integer.valueOf(Map.get(key)+"").intValue()+(Map.get(categoryInfo.getParentId())==null?0:(Integer)Map.get(categoryInfo.getParentId())));
	    	}
	    	
	}
	for(int k=0;k<list.size();k++){
	    Integer numf = (Integer)Map.get(list.get(k).getCategoryId());
	    if(list.get(k)!= null && numf!=null){
		list.get(k).setGoodsNum(numf+(list.get(k).getGoodsNum()==null?0:list.get(k).getGoodsNum().intValue()));
	    }
	}
	if(category.getParentId() !=null){
	    for(int i=0;i<list.size();i++){
		if(list.get(i).getParentId().equals(category.getParentId()) || (category.getCategoryName()!=null && category.getCategoryName()!="") || category.getCategoryIds()!=null){
			listLevel.add(list.get(i));
		}
	    }
	    return listLevel;
	}
	return list;
	}
	
	@Override
	public List<FirstengageCategory> getCategoryTreeList(FirstengageCategory category) {
		List<FirstengageCategory> new_list = null;

		List<FirstengageCategory> returnList = null;
		if(null == category || category.getInterfaceType() == 0){				
			returnList = categoryMapper.getCategoryList(category);
		}else{
			returnList = categoryMapper.getCategoryListByCategoryName(category);
			// 去掉开头和结尾的->
			for(FirstengageCategory cg : returnList)
			{
				if(null == cg || null == cg.getCategoryName())
					continue;
				cg.setCategoryName(StringUtil.removeStrBeginStrOrEndStr(cg.getCategoryName(), "->"));
			}
			
		}
		
		if(returnList!=null && !returnList.isEmpty()){
			JSONArray jsonArray = (JSONArray) JSONArray.fromObject(returnList);
			List<FirstengageCategory> sellist = new ArrayList<FirstengageCategory>();
			Integer parentId = 0;
			if(category.getParentId()!=null){
				parentId = category.getParentId();
			}
			JSONArray jsonList = treeMenuList(jsonArray, parentId, "","",0);
			new_list = resetList(jsonList, sellist, 0);
		}
		
		return new_list;
	}
	

	/**
	 * <b>Description:</b><br> 递归组装树形结构
	 * @param menuList
	 * @param parentId
	 * @param parentName
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年5月15日 上午9:09:25
	 */
	public JSONArray treeMenuList(JSONArray menuList, int parentId, String parentName,String parentIdArr,int num) {
		JSONArray childMenu = new JSONArray();

		for (Object object : menuList) {
			JSONObject jsonMenu = (JSONObject) JSONObject.fromObject(object);
			int menuId = jsonMenu.getInt("categoryId");
			int pid = jsonMenu.getInt("parentId");
			if (parentName != "") {
				jsonMenu.element("categoryNameArr", parentName + "--" + jsonMenu.getString("categoryName"));
			} else {
				jsonMenu.element("categoryNameArr", jsonMenu.getString("categoryName"));
			}
			jsonMenu.element("categoryIdArr", parentIdArr + "--" + jsonMenu.getString("categoryId"));
			if( parentId!=0){
				if(num == 1 && parentId == menuId){
					JSONArray c_node = treeMenuList(menuList, menuId, jsonMenu.getString("categoryNameArr"),jsonMenu.getString("categoryIdArr"),0);
					jsonMenu.put("childNode", c_node);
					childMenu.add(jsonMenu);
				}
				if (num ==0 && parentId == pid) {
					JSONArray c_node = treeMenuList(menuList, menuId, jsonMenu.getString("categoryNameArr"),jsonMenu.getString("categoryIdArr"),0);
					jsonMenu.put("childNode", c_node);
					childMenu.add(jsonMenu);
				}
			}else{
				if (parentId == pid) {
					JSONArray c_node = treeMenuList(menuList, menuId, jsonMenu.getString("categoryNameArr"),jsonMenu.getString("categoryIdArr"),0);
					jsonMenu.put("childNode", c_node);
					childMenu.add(jsonMenu);
				}
			}
		}
		return childMenu;
	}
	

	/**
	 * <b>Description:</b><br> 递归分析树状结构
	 * @param tasklist
	 * @param sellist
	 * @param num
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年5月15日 上午9:11:44
	 */
	public List<FirstengageCategory> resetList(JSONArray tasklist, List<FirstengageCategory> sellist, int num) {
		String str = "";
/*		for (int i = 0; i < (num * 2); i++) {
			str += "--";
		}*/
		for (int i = 0; i < num; i++) {
			str += "├----";
		}
		for (Object obj : tasklist) {
			JSONObject jsonMenu = (JSONObject) JSONObject.fromObject(obj);
			FirstengageCategory sm = new FirstengageCategory();
			sm.setCategoryId(Integer.valueOf(jsonMenu.getInt("categoryId")));
			sm.setCategoryName(str + "├" + jsonMenu.getString("categoryName"));
			sm.setCategoryNm(jsonMenu.getString("categoryName"));
			sm.setCategoryNameArr(jsonMenu.getString("categoryNameArr"));
			sm.setCategoryIdArr(jsonMenu.getString("categoryIdArr"));
			sm.setLevel(jsonMenu.getInt("level"));
			sm.setSort(jsonMenu.getInt("sort"));
			sm.setParentId(jsonMenu.getInt("parentId"));
			sm.setCompanyId(jsonMenu.getInt("companyId"));
			sm.setStatus(jsonMenu.getInt("status"));
			sm.setAddTime(jsonMenu.getLong("addTime"));
			sm.setCreator(jsonMenu.getInt("creator"));
			sm.setUpdater(jsonMenu.getInt("updater"));
			sm.setModTime(jsonMenu.getLong("modTime"));
			sm.setGoodsNum(jsonMenu.getInt("goodsNum"));
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
	public FirstengageCategory getCategoryById(FirstengageCategory category) {
		 return categoryMapper.getCategoryById(category);
	}

	@Override
	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)//事物
	public ResultInfo<?> addCategorySave(FirstengageCategory category) {
		ResultInfo result = new ResultInfo();
		try {
			//验证父级目录是否大于等于3级
			Integer level = categoryMapper.vailParentLevel(category);
			if(level!=null && level>=3){
				result.setMessage("分类层级不允许超过三级");
				return result;
			}
			//验证同级目录下名称是否重复
			Integer n= categoryMapper.vailCategoryName(category);
			if(n==0){
				//验证父级目录是否为最底层目录
//				Integer isBottom = categoryMapper.vailCateBottom(category);

				// 获取新节点父级别
				Integer parent_level = categoryMapper.getParentLevel(category);
				if(parent_level==null){
					parent_level = 1;
				}else{
					parent_level += 1;
				}
				category.setLevel(parent_level);
				int i = categoryMapper.addCategory(category);
				if(i==1){
					if(category.getParentId()==0){
						category.setTreenodes(category.getCategoryId().toString());
					}else{
						FirstengageCategory cg = new FirstengageCategory();
						cg.setCategoryId(category.getParentId());
						cg.setCompanyId(category.getCompanyId());
						cg = categoryMapper.getCategoryById(cg);
						category.setTreenodes(cg.getTreenodes()+","+category.getCategoryId());
					}
					int j = categoryMapper.updateCategoryTreenodes(category);
					if(j==1){
						result.setCode(0);result.setMessage("操作成功");
						result.setData(category);
						/*if(isBottom==null){//最底层
							//将原来挂在此分类下的产品移动到新分类下
							categoryMapper.updateGoodsCategory(category.getParentId(),category.getCategoryId(),category.getModTime(),category.getUpdater());
						}*/
					}
				}
			}else{
				result.setCode(-1);result.setMessage("分类名称不允许重复");
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			return result;
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)//事物
	public ResultInfo<?> editCategory(FirstengageCategory category) {
		ResultInfo result = new ResultInfo();
		try {
			//验证新父级目录是否大于等于3级
			Integer par_level = categoryMapper.vailParentLevel(category);
			if(par_level!=null && par_level>=3){
				result.setMessage("分类层级不允许超过三级");
				return result;
			}else{
				par_level = 0;//顶级分类
			}
			//获取修改记录的层级
			Integer level = categoryMapper.vailCategoryLevel(category);
			if(((par_level==null?0:par_level)+level) > 3){
				result.setMessage("分类层级不允许超过三级");
				return result;
			}
			
			//验证同级目录下名称是否重复
			Integer n= categoryMapper.vailCategoryName(category);
			if(n==0){
				//验证父级目录是否为最底层目录
//				Integer isBottom = categoryMapper.vailCateBottom(category);
				
				int old_level = category.getLevel();
				Integer new_level = null;
				if (category.getParentId() == 0) {
					new_level = 1;
				} else {
					// 获取新父节点级别
					new_level = categoryMapper.getParentLevel(category);
					if(new_level==null){
						new_level = 1;
					}else{
						new_level += 1;
					}
				}
				category.setLevel(new_level);
				int i = categoryMapper.editCategory(category);
				if(i==1){
					//修改当前节点之前的所有节点，逗号拼接(冗余)
					int m = categoryMapper.editCategoryTreenodes(category);
					if(m==1){
						category = categoryMapper.getCategoryById(category);
						List<FirstengageCategory> groupList = categoryMapper.getCategoryList(new FirstengageCategory());
						JSONArray jsonArray = (JSONArray) JSONArray.fromObject(groupList);
						JSONArray jsonList = treeMenuList(jsonArray, category.getCategoryId());
						List<Map<String, Object>> level_list = new ArrayList<Map<String, Object>>();
						List<Map<String, Object>> tree_list = new ArrayList<Map<String, Object>>();
						Map<String, Object> level_map = null;Map<String, Object> tree_map = null;
						String categoryId = category.getCategoryId().toString();
						for (Object object : jsonList) {
							level_map = new HashMap<String, Object>();
							JSONObject jsonMenu = (JSONObject) JSONObject.fromObject(object);
							level_map.put(String.valueOf(jsonMenu.getInt("categoryId")),Integer.valueOf(jsonMenu.getInt("level")) - old_level + new_level);
							level_list.add(level_map);
							if(jsonMenu.getString("treenodes").split(categoryId).length>1){
								tree_map = new HashMap<String, Object>();
								tree_map.put(String.valueOf(jsonMenu.getInt("categoryId")), category.getTreenodes()+jsonMenu.getString("treenodes").split(categoryId)[1]);
								tree_list.add(tree_map);
							}
						}
						if (!level_list.isEmpty()) {
							n = categoryMapper.batchUpdateLevel(level_list);
						}
						if (!tree_list.isEmpty()) {
							n = categoryMapper.batchUpdateTreenodes(tree_list);
						}
					}
					result.setCode(0);result.setMessage("操作成功");
				}
			}else{
				result.setMessage("分类名称不允许重复");
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			return result;
		}
		return result;
	}
	
	public JSONArray treeMenuList(JSONArray menuList, int parentId) throws Exception {
		JSONArray childMenu = new JSONArray();
		for (Object object : menuList) {
			JSONObject jsonMenu = (JSONObject) JSONObject.fromObject(object);
			int menuId = jsonMenu.getInt("categoryId");
			int pid = jsonMenu.getInt("parentId");
			if (parentId == pid) {
				JSONArray c_node = treeMenuList(menuList, menuId);
				jsonMenu.put("childNode", c_node);
				childMenu.add(jsonMenu);
			}
		}
		return childMenu;
	}


	@Override
	public ResultInfo<?> delCategoryById(FirstengageCategory category) {
		ResultInfo result = new ResultInfo();
		//验证商品表中是否正在使用当前分类
		int n = categoryMapper.vailCategoryToGoods(category);
		if(n==0){
			//验证分类子集是否有绑定产品
			FirstengageCategory categoryInfo= categoryMapper.getCategoryById(category);
		    List<Integer> idList = new ArrayList<>();
		    if(categoryInfo.getLevel() == 3){
			idList.add(categoryInfo.getCategoryId());
		    }else{
			List<FirstengageCategory> categoryList = categoryMapper.getCategoryByTreenodes(categoryInfo);
			if(!categoryList.isEmpty()){
			    for(FirstengageCategory c:categoryList){
				idList.add(c.getCategoryId());
			    }
			}else{
			    idList.add(categoryInfo.getCategoryId());
			}
		    }
			//List<Integer> idList = getCategoryIdList(category.getCategoryId());
			int m = categoryMapper.vailGoodsBindCate(idList);
			if(m>0){
				result.setMessage("该分类下存在管理产品，无法删除！");
				return result;
			}
			int i = categoryMapper.delCategory(category);
			if(i==1){
				result.setCode(0);result.setMessage("操作成功");
			}
		}else{
			result.setMessage("该分类下存在管理产品，无法删除！");
		}
		return result;
	}

	@Override
	public List<FirstengageCategory> getCategoryList(FirstengageCategory category) {
		
		List<FirstengageCategory> returnList = null;
		
		if(null == category || category.getInterfaceType() == 0){				
			returnList = categoryMapper.getCategoryList(category);
		}else{
			returnList = categoryMapper.getCategoryListByCategoryName(category);
		}

		for(FirstengageCategory cate : returnList){
			cate.setUserList(rCategoryJUserMapper.getUserByCategory(cate.getCategoryId(), cate.getCompanyId()));
		}
		
		return returnList;
	}

	@Override
	public List<FirstengageCategory> getParentCateList(FirstengageCategory category) {
		return categoryMapper.getParentCateList(category);
	}


	@Override
	public List<User> getUserByCategory(Integer categoryId, Integer companyId) {
		return rCategoryJUserMapper.getUserByCategory(categoryId,companyId);
	}


	@Override
	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	public Boolean saveEditCategoryOwner(List<Integer> userId, String categoryIds, HttpSession session) {
		if(userId.size() == 0 || null == userId || null == categoryIds || categoryIds.equals("")){
			return false;
		}
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		String[] categoryList = categoryIds.split(",");
		for(String category : categoryList){
			//删除归属
			rCategoryJUserMapper.deleteByCateCompany(Integer.parseInt(category),user.getCompanyId());
			
			for(Integer id : userId){
				RCategoryJUser rCategoryJUser = new RCategoryJUser();
				rCategoryJUser.setCategoryId(Integer.parseInt(category));
				rCategoryJUser.setUserId(id);
				
				rCategoryJUserMapper.insert(rCategoryJUser);
			}
			
		}
		return true;
	}


	@Override
	public List<StandardCategory> getParentStandardCateList(StandardCategory standardCategory) {
		return standardCategoryMapper.getParentStandardCateList(standardCategory);
	}


	@Override
	public List<StandardCategory> getStandardCategoryList(StandardCategory standardCategory) {
		List<StandardCategory> scList = null;
		
		if(null == standardCategory || standardCategory.getInterfaceType() == 0){
			scList =  standardCategoryMapper.getStandardCategoryList(standardCategory);
		}else{
			scList = standardCategoryMapper.getNewStandardCategoryList(standardCategory);
			// 去掉开头和结尾的->
			for(StandardCategory sc : scList){
				if(null == sc || null == sc.getCategoryName())
					continue;
				
				sc.setCategoryName(StringUtil.removeStrBeginStrOrEndStr(sc.getCategoryName(), "->"));			    
			}
		}
		return scList;
	}


	@Override
	public List<User> getCategoryOwner(List<Integer> categoryIds, Integer companyId) {
		//如果分类不为空，去查询分类对应归属
		List<User> userList = new ArrayList<>();
		List<String> users = new ArrayList<>();
			 userList=rCategoryJUserMapper.getGoodsCategoryUserList(categoryIds,companyId);
			 //如果有分类没有归属
			 if(userList.size() != categoryIds.size()){
			     	//差异的categoryId
			     	List<Integer> differentList = new ArrayList<>();
			     	if(userList.size()>0){
			     	    //获得查到的用户归属分类id
			     	    List<Integer> userCategoryId = new ArrayList<>();
			     	    for (User user : userList) {
			     		userCategoryId.add(user.getCategoryId());
			     	    }
			     	    //differentList是categoryIds中有的ID但是userCategoryId中没有的
			     	    Map<Integer,Integer> map = new HashMap<Integer,Integer>(userCategoryId.size());
			     	    for(Integer resource : userCategoryId){
			     		map.put(resource,1);
			     	    }
			     	    for(Integer resource1 : categoryIds){
			     		if(map.get(resource1)==null){
			     		    differentList.add(resource1);
			     		}
			     	    }
			     	}else{
			     	     differentList = categoryIds;
			     	}
			     	
			     	
			     	ParamsConfigVo paramsConfigVo = new ParamsConfigVo();
				paramsConfigVo.setCompanyId(companyId);
				paramsConfigVo.setParamsKey(107);
				ParamsConfigVo quote = myHomePageService.getParamsConfigVoByParamsKey(paramsConfigVo);
				User defaultUser = userService.getUserById(Integer.parseInt(quote.getParamsValue()));
				defaultUser.setOwners(defaultUser.getUsername());
			     	for(Integer c:differentList){
			     	    User userInfo = new User();
			     	    userInfo = (User) defaultUser.clone();
			     	    userInfo.setCategoryId(c);
			     	    userList.add(userInfo);
			     	}
				 
			 }
			 
			 return userList;
	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FirstengageCategory> getCategoryListByParam(FirstengageCategory category) {
		if(category.getCategoryName() != null && !"".equals(category.getCategoryName())){
			List<FirstengageCategory> list = categoryMapper.getCategoryListByName(category);
			//查询所有一级二级的分类
			List<FirstengageCategory> oneTwoList = categoryMapper.getOneAndTwoCategoryList(category);
			for (FirstengageCategory cat : list) {
				if(cat.getTreenodes().contains(",") && cat.getTreenodes().split(",").length == 2){
					for (FirstengageCategory ca : oneTwoList) {
						if(ca.getLevel() == 1 && ca.getCategoryId().equals(Integer.valueOf(cat.getTreenodes().split(",")[0]))){
							cat.setCategoryName(ca.getCategoryName()+">"+cat.getCategoryName());
							break;
						}
					}
				}else if(cat.getTreenodes().contains(",") && cat.getTreenodes().split(",").length == 3){
					StringBuffer sb = new StringBuffer();
					for (FirstengageCategory ca : oneTwoList) {
						if(ca.getLevel() == 1 && ca.getCategoryId().equals(Integer.valueOf(cat.getTreenodes().split(",")[0]))){
							sb = sb.append(ca.getCategoryName()).append(">");
							//cat.setCategoryName(ca.getCategoryName()+">"+cat.getCategoryName());
							break;
						}
					}
					for (FirstengageCategory ca : oneTwoList) {
						if(ca.getLevel() == 2 && ca.getCategoryId().equals(Integer.valueOf(cat.getTreenodes().split(",")[1]))){
							sb = sb.append(ca.getCategoryName()).append(">");
							//cat.setCategoryName(ca.getCategoryName()+">"+cat.getCategoryName());
							break;
						}
					}
					cat.setCategoryName(sb.append(cat.getCategoryName()).toString());
				}
			}
			return list;
		}else{
			return categoryMapper.getCategoryListByParam(category);
		}
		
	}
}
