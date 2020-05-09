package com.vedeng.goods.service.impl;

import com.common.op.Push2OpUtils;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.CommonConstants;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.goods.dao.BaseAttributeMapper;
import com.vedeng.goods.dao.BaseAttributeValueMapper;
import com.vedeng.goods.dao.BaseCategoryMapper;
import com.vedeng.goods.dao.CategoryAttrValueMappingMapper;
import com.vedeng.goods.model.BaseAttribute;
import com.vedeng.goods.model.CategoryAttrValueMapping;
import com.vedeng.goods.model.vo.BaseAttributeValueVo;
import com.vedeng.goods.model.vo.BaseAttributeVo;
import com.vedeng.goods.model.vo.BaseCategoryVo;
import com.vedeng.goods.model.vo.CategoryAttrValueMappingVo;
import com.vedeng.goods.service.BaseCategoryService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service("basecategoryService")
public class BaseCategoryServiceImpl implements BaseCategoryService {

	@Autowired
	private BaseCategoryMapper baseCategoryMapper;

	@Autowired
	private BaseAttributeMapper baseAttributeMapper;

	@Autowired
	private BaseAttributeValueMapper baseAttributeValueMapper;

	@Autowired
	private CategoryAttrValueMappingMapper categoryAttrValueMappingMapper;

	@Value("${client_id}")
	protected String clientId;

	@Value("${client_key}")
	protected String clientKey;

	@Value("${operate_url}")
	protected String operateUrl;

	/**
	 * @description 分类信息
	 * @author bill
	 * @param
	 * @date 2019/5/9
	 */
	@Override
	public BaseCategoryVo getBaseCategoryByParam(Integer baseCategoryId) {

		return baseCategoryMapper.selectByPrimaryKey(baseCategoryId);
	}

	/**
	 * @description 保存分类信息
	 * @author cooper
	 * @param
	 * @date 2019/5/23
	 */
	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public ResultInfo saveBaseCategory(BaseCategoryVo baseCategoryVo) {
		Integer result = 0;
		Integer userId = 0;
		Date nowDate = new Date();
		//ResultInfo resultInfo = new ResultInfo();
		//判断是新增还是更新
		if (baseCategoryVo.getBaseCategoryId() == null) {//新增分类信息
			userId = baseCategoryVo.getCreator();
			baseCategoryVo.setAddTime(nowDate);
			baseCategoryVo.setModTime(nowDate);
			//保存
			result = baseCategoryMapper.insertSelective(baseCategoryVo);
			//更新treenodes和ParentId
			if (result > 0){
				if (baseCategoryVo.getBaseCategoryLevel().equals(CommonConstants.CATEGORY_LEVEL_1)){
					baseCategoryVo.setTreenodes(String.valueOf(baseCategoryVo.getBaseCategoryId()));
				}else{
					baseCategoryVo.setTreenodes(baseCategoryVo.getTreenodes()+','+baseCategoryVo.getBaseCategoryId());
				}
				baseCategoryMapper.updateByPrimaryKeySelective(baseCategoryVo);
			}
		}else{//更新分类信息
			if (CommonConstants.CATEGORY_LEVEL_3.equals(baseCategoryVo.getBaseCategoryLevel())){

				/**若该分类下存在被推送到指南针平台的商品，则同步更新指南针平台（OP）相应商品的三级分类名  */
				new Push2OpUtils().pushUpdateCategory(baseCategoryVo,clientId,clientKey,operateUrl);

				baseCategoryVo.setIsDeleted(CommonConstants.IS_DELETE_0);
				List<BaseCategoryVo> thirdCategoryList = baseCategoryMapper.getthirdCategoryListById(baseCategoryVo);
				if (CollectionUtils.isEmpty(thirdCategoryList) || thirdCategoryList.get(0) == null ||
						(thirdCategoryList != null && thirdCategoryList.size() == 1 && thirdCategoryList.get(0) != null
								&& thirdCategoryList.get(0).getBaseCategoryId() == null)){
					thirdCategoryList = null;
				}else{
					BaseCategoryVo baseCategoryVoExsit = baseCategoryMapper.selectByPrimaryKey(baseCategoryVo.getBaseCategoryId());
					for (BaseCategoryVo baseCategoryVoTemp : thirdCategoryList){
						Integer coreProductNum = baseCategoryVoTemp.getCoreProductNum() == null ? 0 : baseCategoryVoTemp.getCoreProductNum();
						Integer temporaryProductNum = baseCategoryVoTemp.getTemporaryProductNum() == null ? 0 : baseCategoryVoTemp.getTemporaryProductNum();
						Integer otherProductNum = baseCategoryVoTemp.getOtherProductNum() == null ? 0 : baseCategoryVoTemp.getOtherProductNum();
						if (coreProductNum > 0 || temporaryProductNum > 0
								|| otherProductNum > 0){
							if (! baseCategoryVoExsit.getBaseCategoryType().equals(baseCategoryVo.getBaseCategoryType())
									&& ! baseCategoryVoExsit.getBaseCategoryType().equals(0)){
								return new ResultInfo(CommonConstants.FAIL_CODE, "分类下存在商品，暂不可修改分类类型！");
							}
						}
					}
				}
			}
			userId = baseCategoryVo.getUpdater();
			//更新
			baseCategoryVo.setModTime(nowDate);
			result = baseCategoryMapper.updateByPrimaryKeySelective(baseCategoryVo);
		}
		//判断该保存的分类信息是否属于三级分类
		if (result > 0 && CommonConstants.CATEGORY_LEVEL_3.equals(baseCategoryVo.getBaseCategoryLevel())){
			//如果是三级分类，则保存引用的属性及属性值关联信息
			String[] baseAttributeId = baseCategoryVo.getBaseAttributeId();
			String[] baseAttributeValueIds = baseCategoryVo.getBaseAttributeValueIds();

			//先删除原关联信息
			List<BaseCategoryVo> baseCategoryVoList = new ArrayList<>();
			baseCategoryVoList.add(baseCategoryVo);
			Map<String,Object> map = new HashMap<>();
			map.put("isDeleted",CommonConstants.IS_DELETE_1);
			map.put("updater",userId);
			map.put("modTime",nowDate);
			map.put("list",baseCategoryVoList);
			categoryAttrValueMappingMapper.deleteCategoryAttrValueMappingByCategoryIds(map);
			List<CategoryAttrValueMapping> categoryAttrValueMappingList = new ArrayList<>();
			//解析属性以及属性值
			for (int i = 0 ; i<baseAttributeId.length;i++){
				if (baseAttributeId[i] !=null && !"".equals(baseAttributeId[i]) && baseAttributeValueIds[i] != null && !"".equals(baseAttributeValueIds[i])){
					String[] baseAttributeValueId = baseAttributeValueIds[i].split("@");
					for (int j = 0 ; j < baseAttributeValueId.length;j++){
						CategoryAttrValueMapping categoryAttrValueMapping = new CategoryAttrValueMapping();
						categoryAttrValueMapping.setBaseAttributeId(Integer.valueOf(baseAttributeId[i]));
						categoryAttrValueMapping.setBaseAttributeValueId(Integer.valueOf(baseAttributeValueId[j]));
						categoryAttrValueMapping.setCreator(userId);
						categoryAttrValueMapping.setUpdater(userId);
						categoryAttrValueMapping.setAddTime(nowDate);
						categoryAttrValueMapping.setModTime(nowDate);
						categoryAttrValueMapping.setIsDeleted(CommonConstants.IS_DELETE_0);
						categoryAttrValueMapping.setBaseCategoryId(baseCategoryVo.getBaseCategoryId());
						categoryAttrValueMappingList.add(categoryAttrValueMapping);
					}
				}
			}
			if (CollectionUtils.isNotEmpty(categoryAttrValueMappingList)){
				//批量保存关联信息
				categoryAttrValueMappingMapper.insertCategoryAttrValueMappingBatch(categoryAttrValueMappingList);
			}
		}
		return new ResultInfo(CommonConstants.SUCCESS_CODE, "保存成功", baseCategoryVo.getBaseCategoryId());
	}

	@Override
	public List<BaseCategoryVo> getCategoryListPage(BaseCategoryVo baseCategoryVo, Page page) {
		Map<String,Object> map = new HashMap<>();
		map.put("baseCategoryVo",baseCategoryVo);
		map.put("page",page);
		List<BaseCategoryVo> list = null;
		if (CommonConstants.CATEGORY_LEVEL_1.equals(baseCategoryVo.getBaseCategoryLevel())){
			list = baseCategoryMapper.getFirstCategoryListPage(map);
		}
		if (CommonConstants.CATEGORY_LEVEL_2.equals(baseCategoryVo.getBaseCategoryLevel())){
			list = baseCategoryMapper.getSecondCategoryListForNameQuery(map);
		}
		if (CommonConstants.CATEGORY_LEVEL_3.equals(baseCategoryVo.getBaseCategoryLevel())){
			list = baseCategoryMapper.getThirdCategoryListForNameQuery(map);
		}
		return list;
	}

	/**
	 * @description 获取属性信息
	 * @author bill
	 * @param
	 * @date 2019/5/17
	 */
	@Override
	public List<BaseAttribute> getAttributeInfo(Integer baseCategoryId) {

		return baseAttributeMapper.getAttributeInfoByCategory(baseCategoryId);
	}

	@Override
	public List<BaseCategoryVo> getBaseCategoryListPageByAttr(Integer attrId, Page page) {
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("attrId",attrId);
		paramMap.put("isDeleted",CommonConstants.IS_DELETE_0);
		paramMap.put("page",page);
		return baseCategoryMapper.getBaseCategoryListPage(paramMap);
	}

	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public Integer deleteCategory(List<BaseCategoryVo> firstCategoryVoList,
			List<BaseCategoryVo> secondCategoryVoList, List<BaseCategoryVo> thirdBasegoryVoList,User user) {
		Integer result = 0;
		Date nowDate = new Date();
		Map<String,Object> map = new HashMap<>();
		map.put("isDeleted",CommonConstants.IS_DELETE_1);
		map.put("modTime",nowDate);
		map.put("updater",user.getUserId());
		//删除三级分类
		if (CollectionUtils.isNotEmpty(thirdBasegoryVoList)){
			map.put("list",thirdBasegoryVoList);
			result = categoryAttrValueMappingMapper.deleteCategoryAttrValueMappingByCategoryIds(map);
			result = baseCategoryMapper.deleteCategory(map);
		}
		//删除二级分类
		if (CollectionUtils.isNotEmpty(secondCategoryVoList)){
			map.put("list",secondCategoryVoList);
			result = baseCategoryMapper.deleteCategory(map);
		}
		//删除一级分类
		if (CollectionUtils.isNotEmpty(firstCategoryVoList)){
			map.put("list",firstCategoryVoList);
			result = baseCategoryMapper.deleteCategory(map);
		}
		return result;
	}

	@Override
	public List<CategoryAttrValueMappingVo> getCategoryAttrValueMappingVoList(List<BaseCategoryVo> list) {
		Map<String,Object> map = new HashMap<>();
		map.put("isDeleted",CommonConstants.IS_DELETE_0);
		map.put("list",list);
		return categoryAttrValueMappingMapper.getCategoryAttrValueMappingVoList(map);
	}

    @Override
    public List<BaseAttributeVo> doAttrAndValueToString(List<BaseAttributeVo> list, List<BaseAttributeValueVo> baseAttributeValueVoList) {
		if (CollectionUtils.isNotEmpty(list)){
			for (BaseAttributeVo baseAttributeVo : list){
				if (CollectionUtils.isNotEmpty(baseAttributeValueVoList)){
					for (BaseAttributeValueVo baseAttributeValueVo : baseAttributeValueVoList){
						if (baseAttributeVo.getBaseAttributeId().equals(baseAttributeValueVo.getBaseAttributeId())){
							if (baseAttributeVo.getBaseAttributeValueIds() == null || "".equals(baseAttributeVo.getBaseAttributeValueIds())){
								baseAttributeVo.setBaseAttributeValueIds(String.valueOf(baseAttributeValueVo.getBaseAttributeValueId()));
							}else{
								baseAttributeVo.setBaseAttributeValueIds(baseAttributeVo.getBaseAttributeValueIds()+"@"+baseAttributeValueVo.getBaseAttributeValueId());
							}
						}
					}
				}
			}
		}
		return list;
    }

	@Override
	public String doAttrAndValueToJson(List<BaseAttributeVo> list, List<BaseAttributeValueVo> baseAttributeValueVoList) {
		String attrAndValueJson = "[";
		if (CollectionUtils.isNotEmpty(list)){
			for (BaseAttributeVo baseAttributeVo : list){
				attrAndValueJson = attrAndValueJson + "{\"attrName\":\""
						+baseAttributeVo.getBaseAttributeName()+"\",\"attrId\":\""
						+baseAttributeVo.getBaseAttributeId()+ "\",\"attrValue\":[";
				String attrAndValueJsonTemp = "";
				if (CollectionUtils.isNotEmpty(baseAttributeValueVoList)){
					for (BaseAttributeValueVo baseAttributeValueVo : baseAttributeValueVoList){
						if (baseAttributeVo.getBaseAttributeId().equals(baseAttributeValueVo.getBaseAttributeId())){
							attrAndValueJsonTemp = attrAndValueJsonTemp +"{\"attrValueName\":\""+baseAttributeValueVo.getAttrValue();
							if (CommonConstants.STATUS_1.equals(baseAttributeVo.getIsUnit())) {
								attrAndValueJsonTemp = attrAndValueJsonTemp +" "+baseAttributeValueVo.getUnitName();
							}
							attrAndValueJsonTemp = attrAndValueJsonTemp +"\",\"attrValueId\":\""+baseAttributeValueVo.getBaseAttributeValueId()+"\"},";
						}
					}
				}
				attrAndValueJson =  "".equals(attrAndValueJsonTemp) ?  attrAndValueJson +"]}," : attrAndValueJson + attrAndValueJsonTemp.substring(0,attrAndValueJsonTemp.length()-1) + "]},";
			}
		}
		attrAndValueJson = attrAndValueJson.length() == 1 ? attrAndValueJson + "]" : attrAndValueJson.substring(0,attrAndValueJson.length()-1) + "]";
		return attrAndValueJson;
	}

	@Override
	public List<BaseAttributeVo> doAttrAndValueToList(BaseCategoryVo baseCategoryVo) {
		String[] baseAttributeId = baseCategoryVo.getBaseAttributeId();
		String[] baseAttributeValueIds = baseCategoryVo.getBaseAttributeValueIds();
		if (baseAttributeId.length>0 || baseAttributeValueIds.length>0){
			List<BaseAttributeVo> baseAttributeVoList = new ArrayList<>();
			for (int i = 0 ; i<baseAttributeId.length;i++){
				BaseAttributeVo baseAttributeVo = new BaseAttributeVo();
				if (baseAttributeId[i]==null || "".equals(baseAttributeId[i])){
					baseAttributeVo.setBaseAttributeId(null);
				}else{
					baseAttributeVo.setBaseAttributeId(Integer.valueOf(baseAttributeId[i]));
				}
				if (baseAttributeValueIds.length>0 ){
					if (baseAttributeValueIds[i]!=null&& !"".equals(baseAttributeValueIds[i])){
						baseAttributeVo.setBaseAttributeValueIds(baseAttributeValueIds[i]);
					}else{
						baseAttributeVo.setBaseAttributeValueIds(null);
					}
				}else{
					baseAttributeVo.setBaseAttributeValueIds(null);
				}

				baseAttributeVoList.add(baseAttributeVo);
			}
			return baseAttributeVoList;
		}
		return null;
	}

	@Override
	public ResultInfo checkCategoryField(BaseCategoryVo baseCategoryVo) {
		if (baseCategoryVo == null){
			return new ResultInfo(CommonConstants.FAIL_CODE, "分类信息填写不完整，无法提交");
		}else{
			if (baseCategoryVo.getBaseCategoryName()==null || "".equals(baseCategoryVo.getBaseCategoryName())){
				return new ResultInfo(CommonConstants.FAIL_CODE, "分类名称填写不完整，无法提交");
			}
			if (CommonConstants.CATEGORY_LEVEL_3.equals(baseCategoryVo.getBaseCategoryLevel())){
				if (baseCategoryVo.getBaseCategoryType()==null || "".equals(baseCategoryVo.getBaseCategoryType())){
					return new ResultInfo(CommonConstants.FAIL_CODE, "分类类型为空，无法提交");
				}
				String[] baseAttributeId = baseCategoryVo.getBaseAttributeId();
				String[] baseAttributeValueIds = baseCategoryVo.getBaseAttributeValueIds();
				List<Integer> baseAttributeIdList = new ArrayList<>();
				List<Integer> baseAttributeValueIdList = new ArrayList<>();
				/*if (baseAttributeId.length==0 && baseAttributeValueIds.length == 0){
					return new ResultInfo(CommonConstants.FAIL_CODE, "属性与属性值少于一条，无法提交");
				}*/
				if (baseAttributeId.length!=0 && baseAttributeValueIds.length == 0){
					return new ResultInfo(CommonConstants.FAIL_CODE, "属性与属性值填写不完整，无法提交");
				}
				for (int i = 0 ; i < baseAttributeId.length ; i ++){
					if(baseAttributeId[i] !=null && !"".equals(baseAttributeId[i])){
						if (baseAttributeValueIds[i] == null || "".equals(baseAttributeValueIds[i])){
							return new ResultInfo(CommonConstants.FAIL_CODE, "属性与属性值填写不完整，无法提交");
						}
						for (int j = i+1 ; j < baseAttributeId.length ; j ++){
							if (baseAttributeId[i].equals(baseAttributeId[j])){
								return new ResultInfo(CommonConstants.FAIL_CODE, "属性存在重复，无法提交");
							}
						}
						baseAttributeIdList.add(Integer.valueOf(baseAttributeId[i]));
						String[] baseAttributeValueIdArray = baseAttributeValueIds[i].split("@");
						for (int m = 0;m<baseAttributeValueIdArray.length;m++){
							baseAttributeValueIdList.add(Integer.valueOf(baseAttributeValueIdArray[m]));
						}
					}
				}
				if (CollectionUtils.isNotEmpty(baseAttributeIdList)){
					Map<String,Object> map = new HashMap<>();
					map.put("isDeleted",CommonConstants.IS_DELETE_1);//已删除
					map.put("list",baseAttributeIdList);
					if (baseAttributeMapper.getDeletedAttrNumByIds(map)>0){
						return new ResultInfo(CommonConstants.FAIL_CODE, "存在已删除的属性，无法提交");
					}
				}
				if (CollectionUtils.isNotEmpty(baseAttributeValueIdList)) {
					Map<String,Object> map = new HashMap<>();
					map.put("isDeleted",CommonConstants.IS_DELETE_1);//已删除
					map.put("list",baseAttributeValueIdList);
					if (baseAttributeValueMapper.getDeletedAttrValueNumByIds(map) > 0) {
						return new ResultInfo(CommonConstants.FAIL_CODE, "存在已删除的属性值，无法提交");
					}
				}
			}
			/**检查欲保存的分类信息是否已经存在
			 * 检查规则：1、一级或二级分类与库中所有未删除的分类都不可重复
			 *          2、三级分类与库中所有未删除的相同分类类型不可重复
			 */
			if (baseCategoryMapper.checkRepeatCategory(baseCategoryVo)>0){
				return new ResultInfo(CommonConstants.FAIL_CODE, "分类与已有内容重复，无法提交");
			}
		}
		return new ResultInfo(CommonConstants.SUCCESS_CODE, "校验通过");
	}

	@Override
	public String getOrganizedCategoryNameById(Integer thirdCategoryId) {

		return baseCategoryMapper.getOrganizedCategoryNameById(thirdCategoryId);
	}

	@Override
	public List<CategoryAttrValueMapping> getCategoryAttrValueMappingVoList(Integer thirdCategoryId) {
		Map<String,Object> map = new HashMap<>();
		map.put("isDeleted",CommonConstants.IS_DELETE_0);
		map.put("baseCategoryId",thirdCategoryId);
		return categoryAttrValueMappingMapper.getCategoryAttrValueMappingList(map);
	}

	@Override
	public List<BaseCategoryVo> getCategoryListByKeyWords(String keyWords) {
		//先查询出满足该关键词的商品ID
		List<Integer> idList = baseCategoryMapper.getCategoryIdByKeyWords(keyWords);
		return baseCategoryMapper.getCategoryListByKeyWords(keyWords,idList);
	}

	@Override
	public List<BaseCategoryVo> getCategoryListByIds(List<BaseCategoryVo> list,Integer level) {
		Map<String,Object> map = new HashMap<>();
		map.put("level",level);
		map.put("isDeleted",CommonConstants.IS_DELETE_0);
		map.put("list",list);
		return baseCategoryMapper.getCategoryListByIds(map);
	}

	@Override
	public List<BaseCategoryVo> getthirdCategoryListById(BaseCategoryVo baseCategoryVo) {
		baseCategoryVo.setIsDeleted(CommonConstants.IS_DELETE_0);
		return baseCategoryMapper.getthirdCategoryListById(baseCategoryVo);
	}

}
