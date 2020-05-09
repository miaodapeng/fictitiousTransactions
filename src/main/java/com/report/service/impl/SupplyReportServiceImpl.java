package com.report.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.common.constants.Contant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.report.dao.CommonReportMapper;
import com.report.dao.SupplyReportMapper;
import com.report.model.export.BuyExportDetail;
import com.report.model.export.GoodsExport;
import com.report.service.SupplyReportService;
import com.vedeng.authorization.model.Organization;
import com.vedeng.authorization.model.User;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.goods.model.Brand;
import com.vedeng.goods.model.Category;
import com.vedeng.order.model.vo.BuyorderVo;
import com.vedeng.system.model.ActProcinst;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("supplyReportService")
public class SupplyReportServiceImpl extends BaseServiceimpl implements SupplyReportService{
	public static Logger logger = LoggerFactory.getLogger(SupplyReportServiceImpl.class);

	@Autowired
	@Qualifier("supplyReportMapper")
	private SupplyReportMapper supplyReportMapper;
	
	@Autowired
	@Qualifier("commonReportMapper")
	private CommonReportMapper commonReportMapper;
	/**
	 * 全部导出产品分类
	 */
	@Override
	public List<Category> exportCategoryList(Category category) {
		List<Category> new_list = null; Integer parentId = null;
		if(category.getParentId()!=null){
			parentId = category.getParentId();
			category.setParentId(null);
		}
		String url = httpUrl + "report/supply/exportcategorylist.htm";
		final TypeReference<ResultInfo<List<Category>>> TypeRef = new TypeReference<ResultInfo<List<Category>>>() {};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, category, clientId, clientKey, TypeRef);
			if(result!=null && result.getCode() == 0){
				List<Category> list = (List<Category>) result.getData();
				JSONArray jsonArray = (JSONArray) JSONArray.fromObject(list);
				List<Category> sellist = new ArrayList<Category>();
				JSONArray jsonList = treeMenuList(jsonArray, parentId==null? 0 :parentId, "","",1);
				new_list = resetList(jsonList, sellist, 0);
				category.setParentId(parentId);
				//操作人员ID
				List<Integer> userIdList = new ArrayList<Integer>();
				for(int i=0;i<new_list.size();i++){
					userIdList.add(new_list.get(i).getCreator());
					userIdList.add(new_list.get(i).getUpdater());
				}
				//除去重复的操作人员
				userIdList = new ArrayList<Integer>(new HashSet<Integer>(userIdList));
				//查询操作人员
				List<User> userList = commonReportMapper.getUserByUserIds(userIdList);
				String[] strs = {};
				for(int i=0;i<new_list.size();i++){
					for(int j=0;j<userIdList.size();j++){
						if(new_list.get(i).getCreator() == userList.get(j).getUserId()){
							new_list.get(i).setCreatorNm(userList.get(j).getUsername());
						}
						if(new_list.get(i).getUpdater() == userList.get(j).getUserId()){
							new_list.get(i).setUpdaterNm(userList.get(j).getUsername());
						}
					}
					//分割一级、二级、三级分类
					strs = new_list.get(i).getCategoryNameArr().split("--");
					switch (strs.length) {
					case 1:
						new_list.get(i).setCategoryName(strs[0]);
						new_list.get(i).setCategoryNm(null);
						break;
					case 2:
						new_list.get(i).setCategoryName(strs[0]);
						new_list.get(i).setCategoryNm(strs[1]);
						break;
					case 3:
						new_list.get(i).setCategoryName(strs[0]);
						new_list.get(i).setCategoryNm(strs[1]);
						new_list.get(i).setCategoryThreeNm(strs[2]);
						break;
					}
				}
				userIdList.clear();
				return new_list;
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);return null;
		}
		return null;
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
			sm.setModTimeStr(jsonMenu.getString("modTimeStr"));
			sm.setAddTimeStr(jsonMenu.getString("addTimeStr"));
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
	
	/**
	 * 分页导出数据
	 */
	@Override
	public List<Brand> exportBrandList(Brand brand, Page page) {
		String url = httpUrl + "report/supply/exportbrandlist.htm";
		final TypeReference<ResultInfo<List<Brand>>> TypeRef = new TypeReference<ResultInfo<List<Brand>>>() {};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, brand, clientId, clientKey, TypeRef, page);
			if(result!=null && result.getCode() == 0){
				List<Brand> list = (List<Brand>) result.getData();
				if(list != null && !list.isEmpty()){
					//操作人员ID
					List<Integer> userIdList = new ArrayList<Integer>();
					for(int i=0;i<list.size();i++){
						userIdList.add(list.get(i).getCreator());
						userIdList.add(list.get(i).getUpdater());
					}
					//除去重复的操作人员
					userIdList = new ArrayList<Integer>(new HashSet<Integer>(userIdList));
					//查询操作人员
					List<User> userList = commonReportMapper.getUserByUserIds(userIdList);
					for(int i=0;i<list.size();i++){
						for(int j=0;j<userList.size();j++){
							if(list.get(i).getCreator() == userList.get(j).getUserId()){
								list.get(i).setCreatorNm(userList.get(j).getUsername());
							}
							if(list.get(i).getUpdater() == userList.get(j).getUserId()){
								list.get(i).setUpdaterNm(userList.get(j).getUsername());
							}
						}
					}
					userList.clear();
					//分页多次查询
					Page pageInfo = (Page) result.getPage();
					if(pageInfo.getPageNo() == 1){
						Integer total = pageInfo.getTotalPage();
						for (int i = 2; i <= total; i++) {
							pageInfo.setPageNo(i);
							List<Brand> result_list = this.exportBrandList(brand, pageInfo);
							list.addAll(result_list);
						}
					}
					return list;
				}
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);return null;
		}
		return null;
	}
	@Override
	public List<GoodsExport> exportGoodsList(GoodsExport goods, Page page) {
		String url = httpUrl + "report/supply/exportgoodslist.htm";
		final TypeReference<ResultInfo<List<GoodsExport>>> TypeRef = new TypeReference<ResultInfo<List<GoodsExport>>>() {};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, goods, clientId, clientKey, TypeRef, page);
			if(result != null && result.getCode() == 0 && null != result.getData()){
				List<GoodsExport> list = (List<GoodsExport>) result.getData();
				if(list != null && !list.isEmpty()){
					//操作人员ID
					List<Integer> categoryIdList = new ArrayList<Integer>();
					for(int i=0;i<list.size();i++){
						categoryIdList.add(list.get(i).getCategoryId());
					}
					//除去重复
					categoryIdList = new ArrayList<Integer>(new HashSet<Integer>(categoryIdList));
					//查询分类对应人员（产品归属人员）
					List<User> categoryUserList = commonReportMapper.getCategoryUserList(categoryIdList);
					for(int i=0;i<list.size();i++){
						for(int j=0;j<categoryUserList.size();j++){
							if(categoryUserList.get(j).getCategoryId().equals(list.get(i).getCategoryId())){
								list.get(i).setOptUserName(categoryUserList.get(j).getUsername());
							}
						}
					}
					categoryIdList.clear();
					//分页多次查询
					Page pageInfo = (Page) result.getPage();
					if(pageInfo.getPageNo() == 1){
						Integer total = pageInfo.getTotalPage();
						for (int i = 2; i <= total; i++) {
							pageInfo.setPageNo(i);
							List<GoodsExport> result_list = this.exportGoodsList(goods, pageInfo);
							list.addAll(result_list);
						}
					}
					return list;
				}
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);return null;
		}
		return null;
	}
	
	//导出供应链-采购订单列表
	@Override
	public List<BuyExportDetail> exportBuyOrderList(BuyorderVo buyorderVo, Page page) {
		String url = httpUrl + "report/supply/exportbuyorderlist.htm";
		final TypeReference<ResultInfo<List<BuyExportDetail>>> TypeRef = new TypeReference<ResultInfo<List<BuyExportDetail>>>() {};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, buyorderVo, clientId, clientKey, TypeRef, page);
			if(result != null && result.getCode() == 0 && null != result.getData()){
				List<BuyExportDetail> list = (List<BuyExportDetail>) result.getData();
				if(list != null && !list.isEmpty()){
					int list_size = list.size();
					//操作人员ID
					List<Integer> userIdList = new ArrayList<Integer>();List<Integer> traderIdList = new ArrayList<Integer>();
					List<Integer> orgIdList = new ArrayList<Integer>();List<String> buyOrderKeyList = new ArrayList<String>();
					for(int i=0;i<list_size;i++){
						userIdList.add(list.get(i).getUserId());
						traderIdList.add(list.get(i).getTraderId());
						orgIdList.add(list.get(i).getOrgId());
						buyOrderKeyList.add("buyorderVerify_" + list.get(i).getBuyorderId());
					}
					//除去重复
					userIdList = new ArrayList<Integer>(new HashSet<Integer>(userIdList));
					//查询操作人员
					List<User> userList = commonReportMapper.getUserByUserIds(userIdList);
					if(userList == null){
						userList = new ArrayList<>();
					}
					
					orgIdList = new ArrayList<Integer>(new HashSet<Integer>(orgIdList));
					List<Organization> orgList = commonReportMapper.getOrgNameByOrgIdList(orgIdList);
					if(orgList == null){
						orgList = new ArrayList<>();
					}
					
					traderIdList = new ArrayList<Integer>(new HashSet<Integer>(traderIdList));
					List<User> traderUserList = commonReportMapper.getTraderUserAndOrgList(traderIdList);
					if(traderUserList == null){
						traderUserList = new ArrayList<>();
					}
					//查询审核流程记录
					List<ActProcinst> procinstList = commonReportMapper.getActTaskList(buyOrderKeyList);
					if(procinstList == null){
						procinstList = new ArrayList<>();
					}
					if(traderUserList.size() > 0 || orgList.size() > 0 || userList.size() > 0 || procinstList.size() > 0){
						int traderUser_size = traderUserList.size();
						int orgList_size = orgList.size();
						int userList_size = userList.size();
						int procinst_size = procinstList.size();
						int count = 0;
						for(int i=0;i<list_size;i++){
							for(int j=0;j<userList_size;j++){
								if(userList.get(j).getUserId().equals(list.get(i).getUserId())){
									list.get(i).setUserName(userList.get(j).getUsername());//订单创建人员
								}
							}
							for(int j=0;j<orgList_size;j++){
								if(orgList.get(j).getOrgId().equals(list.get(i).getOrgId())){
									list.get(i).setOrgNm(orgList.get(j).getOrgName());//订单创建人员归属部门
								}
							}
							for(int j=0;j<traderUser_size;j++){
								if(traderUserList.get(j).getTraderId().equals(list.get(i).getTraderId())){
									list.get(i).setTraderUserNm(traderUserList.get(j).getUsername());//客户归属人员
									list.get(i).setTraderUserOrgNm(traderUserList.get(j).getOrgName());//客户归属人员部门
								}
							}
							for(int j=0;j<procinst_size;j++){
								if(procinstList.get(j).getBusiness_key_().indexOf(list.get(i).getBuyorderId()+"") > 0){
									if(count ==0){
										list.get(i).setAuditStatus_1("");
										list.get(i).setAuditUserNm_1(procinstList.get(j).getAssignee_());//一层审核人员
										list.get(i).setAuditTimeStr_1(procinstList.get(j).getEnd_time_str());//一层审核时间
									}else if(count == 1){
										list.get(i).setAuditStatus_2("");
										list.get(i).setAuditUserNm_2(procinstList.get(j).getAssignee_());//二层审核人员
										list.get(i).setAuditTimeStr_2(procinstList.get(j).getEnd_time_str());//二层审核时间
									}else if(count == 2){
										list.get(i).setAuditStatus_3("");
										list.get(i).setAuditUserNm_3(procinstList.get(j).getAssignee_());//三层审核人员
										list.get(i).setAuditTimeStr_3(procinstList.get(j).getEnd_time_str());//三层审核时间
									}
									count ++;
								}
							}
							count = 0;
						}
						traderIdList.clear();orgList.clear();userList.clear();procinstList.clear();
					}
					//分页多次查询
					Page pageInfo = (Page) result.getPage();
					if(pageInfo.getPageNo() == 1){
						Integer total = pageInfo.getTotalPage();
						for (int i = 2; i <= total; i++) {
							pageInfo.setPageNo(i);
							List<BuyExportDetail> result_list = this.exportBuyOrderList(buyorderVo, pageInfo);
							list.addAll(result_list);
							result_list.clear();
						}
					}
					return list;
				}
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);return null;
		}
		return null;
	}
	@Override
	public List<BuyExportDetail> exportBuyOrderDetailList(BuyorderVo buyorderVo, Page page) {
		String url = httpUrl + "report/supply/exportbuyorderdetaillist.htm";
		final TypeReference<ResultInfo<List<BuyExportDetail>>> TypeRef = new TypeReference<ResultInfo<List<BuyExportDetail>>>() {};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, buyorderVo, clientId, clientKey, TypeRef, page);
			if(result != null && result.getCode() == 0 && null != result.getData()){
				List<BuyExportDetail> list = (List<BuyExportDetail>) result.getData();
				if(list != null && !list.isEmpty()){
					int list_size = list.size();
					//操作人员ID
					List<Integer> userIdList = new ArrayList<Integer>();
					for(int i=0;i<list_size;i++){
						userIdList.add(list.get(i).getUserId());
					}
					//除去重复
					userIdList = new ArrayList<Integer>(new HashSet<Integer>(userIdList));
					//查询操作人员
					List<User> userList = commonReportMapper.getUserByUserIds(userIdList);
					if(userList == null){
						userList = new ArrayList<>();
					}
					int userList_size = userList.size();
					if(userList.size() > 0){
						for(int i=0;i<list_size;i++){
							for(int j=0;j<userList_size;j++){
								if(userList.get(j).getUserId().equals(list.get(i).getUserId())){
									list.get(i).setUserName(userList.get(j).getUsername());//订单创建人员
								}
							}
						}
					}
					userList.clear();
					//分页多次查询
					Page pageInfo = (Page) result.getPage();
					if(pageInfo.getPageNo() == 1){
						Integer total = pageInfo.getTotalPage();
						for (int i = 2; i <= total; i++) {
							pageInfo.setPageNo(i);
							List<BuyExportDetail> result_list = this.exportBuyOrderDetailList(buyorderVo, pageInfo);
							list.addAll(result_list);
						}
					}
					return list;
				}
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);return null;
		}
		return null;
	}
}
