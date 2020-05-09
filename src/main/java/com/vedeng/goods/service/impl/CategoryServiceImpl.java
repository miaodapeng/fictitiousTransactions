package com.vedeng.goods.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
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

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.goods.dao.RCategoryJUserMapper;
import com.vedeng.goods.model.Category;
import com.vedeng.goods.model.RCategoryJUser;
import com.vedeng.goods.model.StandardCategory;
import com.vedeng.goods.service.CategoryService;
import com.vedeng.homepage.service.MyHomePageService;
import com.vedeng.system.model.vo.ParamsConfigVo;
import com.vedeng.system.service.UserService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Service("categoryService")
public class CategoryServiceImpl extends BaseServiceimpl implements CategoryService{
	public static Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

	@Autowired
	@Qualifier("rCategoryJUserMapper")
	private RCategoryJUserMapper rCategoryJUserMapper;
	@Autowired
	@Qualifier("myHomePageService")
	private MyHomePageService myHomePageService;
	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Override
	public Map<String,Object> getCategoryListPage(Category category,Page page) {
		List<Category> list = null;Map<String,Object> map = new HashMap<>();
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<List<Category>>> TypeRef = new TypeReference<ResultInfo<List<Category>>>() {};
			String url=httpUrl + "goods/category/getcategorylistpage.htm";
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, category,clientId,clientKey, TypeRef,page);
			list = (List<Category>) result.getData();
			page = result.getPage();
			
			map.put("list", list);map.put("page", page);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return map;
	}

	
	@Override
	public List<Category> getCategoryIndexList(Category category) {
		List<Category> new_list = null;
		Integer parentId = null;
		if(category.getParentId()!=null){
			parentId = category.getParentId();
			category.setParentId(parentId);
		}
		//根据归属人查询
		if(null != category.getUserId() && category.getUserId() > 0){
			List<Integer> categoryIds = rCategoryJUserMapper.getCategoryIdsByUserId(category.getUserId());
			category.setCategoryIds(categoryIds);
		}
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<Category>>> TypeRef = new TypeReference<ResultInfo<List<Category>>>() {};
//		String url=httpUrl + "goods/category/getcategorylist.htm";
		String url=httpUrl + "goods/category/getcategoryindexlist.htm";
		
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, category,clientId,clientKey, TypeRef);
			List<Category> list = (List<Category>) result.getData();
			if(list!=null && !list.isEmpty()){
//				JSONArray jsonArray = (JSONArray) JSONArray.fromObject(list);
//				List<Category> sellist = new ArrayList<Category>();
//				JSONArray jsonList = treeMenuList(jsonArray, parentId==null? 0 :parentId, "","",1);
//				new_list = resetList(jsonList, sellist, 0);
//				category.setParentId(parentId);
				
				for(Category cate : list){
					cate.setUserList(rCategoryJUserMapper.getUserByCategory(cate.getCategoryId(), cate.getCompanyId()));
				}
				new_list=list;
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return new_list;
	}
	
	@Override
	public List<Category> getCategoryTreeList(Category category) {
		List<Category> new_list = null;
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<Category>>> TypeRef = new TypeReference<ResultInfo<List<Category>>>() {};
		String url=httpUrl + "goods/category/getcategorylist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, category,clientId,clientKey, TypeRef);
			List<Category> list = (List<Category>) result.getData();
			if(list!=null && !list.isEmpty()){
				JSONArray jsonArray = (JSONArray) JSONArray.fromObject(list);
				List<Category> sellist = new ArrayList<Category>();
				Integer parentId = 0;
				if(category.getParentId()!=null){
					parentId = category.getParentId();
				}
				JSONArray jsonList = treeMenuList(jsonArray, parentId, "","",0);
				new_list = resetList(jsonList, sellist, 0);
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
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
	public List<Category> resetList(JSONArray tasklist, List<Category> sellist, int num) {
		String str = "";
/*		for (int i = 0; i < (num * 2); i++) {
			str += "--";
		}*/
		for (int i = 0; i < num; i++) {
			str += "├----";
		}
		for (Object obj : tasklist) {
			JSONObject jsonMenu = (JSONObject) JSONObject.fromObject(obj);
			Category sm = new Category();
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
	public Category getCategoryById(Category category) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Category>> TypeRef = new TypeReference<ResultInfo<Category>>() {};
		String url=httpUrl + "goods/category/getcategorybyid.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, category,clientId,clientKey, TypeRef);
			category = (Category) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);return null;
		}
		return category;
	}

	@Override
	public ResultInfo<?> addCategorySave(Category category) {
		ResultInfo<?> result = new ResultInfo<>();
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Category>> TypeRef = new TypeReference<ResultInfo<Category>>() {};
		String url=httpUrl + "goods/category/addcategory.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, category,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return result;
		}
		return result;
	}

	@Override
	public ResultInfo<?> editCategory(Category category) {
		ResultInfo<?> result = new ResultInfo<>();
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Category>> TypeRef = new TypeReference<ResultInfo<Category>>() {};
		String url=httpUrl + "goods/category/editcategory.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, category,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return result;
		}
		return result;
	}

	@Override
	public ResultInfo<?> delCategoryById(Category category) {
		ResultInfo<?> result = new ResultInfo<>();
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Category>> TypeRef = new TypeReference<ResultInfo<Category>>() {};
		String url=httpUrl + "goods/category/delcategory.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, category,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return result;
		}
		return result;
	}

	@Override
	public List<Category> getCategoryList(Category category) {
		List<Category> list = null;
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<Category>>> TypeRef = new TypeReference<ResultInfo<List<Category>>>() {};
		String url=httpUrl + "goods/category/getcategorylist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, category,clientId,clientKey, TypeRef);
			list = (List<Category>) result.getData();
			for(Category cate : list){
				cate.setUserList(rCategoryJUserMapper.getUserByCategory(cate.getCategoryId(), cate.getCompanyId()));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(Contant.ERROR_MSG, e);
		}
		return list;
	}

	@Override
	public List<Category> getParentCateList(Category category) {
		List<Category> list = null;
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<Category>>> TypeRef = new TypeReference<ResultInfo<List<Category>>>() {};
		String url=httpUrl + "goods/category/getparentcatelist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, category,clientId,clientKey, TypeRef);
			list = (List<Category>) result.getData();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(Contant.ERROR_MSG, e);
		}
		return list;
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
		List<StandardCategory> list = null;
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<StandardCategory>>> TypeRef = new TypeReference<ResultInfo<List<StandardCategory>>>() {};
		String url=httpUrl + "goods/category/getparentstandardcatelist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, standardCategory,clientId,clientKey, TypeRef);
			list = (List<StandardCategory>) result.getData();
			
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return list;
	}


	@Override
	public List<StandardCategory> getStandardCategoryList(StandardCategory standardCategory) {
		List<StandardCategory> list = null;
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<StandardCategory>>> TypeRef = new TypeReference<ResultInfo<List<StandardCategory>>>() {};
		String url=httpUrl + "goods/category/getstandardcategorylist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, standardCategory,clientId,clientKey, TypeRef);
			list = (List<StandardCategory>) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return list;
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
	public List<Category> getCategoryListByParam(Category category) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<Category>>> TypeRef = new TypeReference<ResultInfo<List<Category>>>() {};
		String url=httpUrl + "goods/category/getcategorylistbyparam.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, category,clientId,clientKey, TypeRef);
			return (List<Category>) result.getData();
			
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}
}
