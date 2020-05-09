package com.vedeng.goods.service.impl;

import com.vedeng.common.constant.CommonConstants;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.goods.dao.BaseAttributeMapper;
import com.vedeng.goods.dao.BaseAttributeValueMapper;
import com.vedeng.goods.model.BaseAttribute;
import com.vedeng.goods.model.vo.BaseAttributeValueVo;
import com.vedeng.goods.model.vo.BaseAttributeVo;
import com.vedeng.goods.model.vo.BaseCategoryVo;
import com.vedeng.goods.service.BaseAttributeService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("baseAttributeService")
public class BaseAttributeServiceImpl implements BaseAttributeService {

	@Autowired
	private BaseAttributeMapper baseAttributeMapper;

	@Autowired
	private BaseAttributeValueMapper baseAttributeValueMapper;

	@Override
	public BaseAttributeVo getBaseAttributeByParam(Map<String, Object> param) {

		return baseAttributeMapper.getBaseAttributeByParam(param);
	}


	/**
	 * 保存属性信息
	 */
	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public ResultInfo saveAttribute(BaseAttributeVo baseAttributeVo) {
		Integer updateRes = 0;//操作结果
		Date nowDate = new Date();//当前时间
		// 判断属性id是否为空,以确定是新增保存还是编辑保存
		Integer baseAttributeId = baseAttributeVo.getBaseAttributeId();
		//判断该属性在库中是否已经存在
		baseAttributeVo.setIsDeleted(CommonConstants.IS_DELETE_0);//未删除
		if (baseAttributeMapper.checkAttrubuteIsExist(baseAttributeVo) > 0){
			return new ResultInfo(CommonConstants.FAIL_CODE, "该属性与已有内容重复，无法提交");
		}
		if(null == baseAttributeId){//新增保存
			baseAttributeVo.setAddTime(nowDate);
			baseAttributeVo.setModTime(nowDate);
			updateRes = baseAttributeMapper.insertSelective(baseAttributeVo);
		}else{//编辑保存
			//判断是否修改了已引用的字段

			baseAttributeVo.setModTime(nowDate);//初始化更新时间
			updateRes = baseAttributeMapper.updateByPrimaryKeySelective(baseAttributeVo);
		}
		// 属性保存成功
		if(updateRes > 0){
			//为了保证分类与属性值的关联关系不发生变化，必须新的属性值列表做区分操作
			Map<String, Object> paramMap = new HashedMap();
			paramMap.put("nowDate",nowDate);
			Integer addAttrValRes = 0;
			if (null == baseAttributeId){//如果为新增
				// 属性id
				paramMap.put("baseAttributeVo", baseAttributeVo);
				// -- 先删除原属性值
				//baseAttributeValueMapper.deleteAttrValByParam(paramMap);
				// -- 重新添加属性值
				paramMap.put("list",baseAttributeVo.getAttrValue());
				addAttrValRes = baseAttributeValueMapper.insertAttributeValByParam(paramMap);
				if(addAttrValRes > 0){
					return new ResultInfo(CommonConstants.SUCCESS_CODE, "保存成功", baseAttributeVo.getBaseAttributeId());
				}
			}else {//如果为编辑保存
				paramMap.put("isDeleted",CommonConstants.IS_DELETE_0);//未删除
				paramMap.put("baseAttributeId",baseAttributeVo.getBaseAttributeId());
				//新的属性值列表
				List<BaseAttributeValueVo> newAttrValueList= baseAttributeVo.getAttrValue();
				//查询库中已经存在的属性值列表
				List<BaseAttributeValueVo> oldAttrValueList= baseAttributeValueMapper.getAttrValueByAttrId(paramMap);
				List<BaseAttributeValueVo> saveAttrValueList = new ArrayList<>();//新增的属性值列表
				List<BaseAttributeValueVo> updateAttrValueList = new ArrayList<>();//库中已经存在，需要更新的属性值列表
				List<BaseAttributeValueVo> delAttrValueList = new ArrayList<>();//需要删除的属性值列表
				for (BaseAttributeValueVo newAttributeValue : newAttrValueList){
					if (newAttributeValue.getBaseAttributeValueId() == null){//没有属性值ID
						saveAttrValueList.add(newAttributeValue);
						continue;
					}
					for (BaseAttributeValueVo oldAttributeValue : oldAttrValueList){
						if (newAttributeValue.getBaseAttributeValueId().equals(oldAttributeValue.getBaseAttributeValueId())){
							updateAttrValueList.add(newAttributeValue);
						}

					}
				}
				//已经存在的属性值列表去掉需要更新的属性值列表，剩下的都是要删除的属性值列表
				Integer insertResult = 0;
				Integer updateResult = 0;
				Integer delResult = 0;
				for (BaseAttributeValueVo oldAttributeValue : oldAttrValueList){
					for (BaseAttributeValueVo updateAttrValue : updateAttrValueList){
						if (updateAttrValue.getBaseAttributeValueId().equals(oldAttributeValue.getBaseAttributeValueId())){
							delAttrValueList.add(oldAttributeValue);
						}

					}
				}
				oldAttrValueList.removeAll(delAttrValueList);
				if (CollectionUtils.isNotEmpty(saveAttrValueList)){
					baseAttributeVo.setCreator(baseAttributeVo.getUpdater());
					paramMap.put("baseAttributeVo", baseAttributeVo);
					paramMap.put("list",saveAttrValueList);
					insertResult = baseAttributeValueMapper.insertAttributeValByParam(paramMap);
				}
				if (CollectionUtils.isNotEmpty(updateAttrValueList)){
					paramMap.put("baseAttributeVo", baseAttributeVo);
					paramMap.put("updater",baseAttributeVo.getUpdater());
					paramMap.put("list",updateAttrValueList);
					updateResult = baseAttributeValueMapper.updateAttributeValByParamBatch(paramMap);
				}
				if (CollectionUtils.isNotEmpty(oldAttrValueList)){
					paramMap.put("updater",baseAttributeVo.getUpdater());
					paramMap.put("list",oldAttrValueList);
					paramMap.put("isDeleted",CommonConstants.IS_DELETE_1);//已删除
					delResult = baseAttributeValueMapper.deleteAttrValByParamBatch(paramMap);
				}
				if (insertResult > 0 || updateResult > 0 || delResult > 0) {
					return new ResultInfo(CommonConstants.SUCCESS_CODE, "保存成功", baseAttributeVo.getBaseAttributeId());
				}
			}

		}
		return new ResultInfo(CommonConstants.FAIL_CODE, "属性保存失败");
	}

	@Override
	public List<BaseAttributeVo> getBaseAttributeInfoListPage(BaseAttributeVo baseAttributeVo, Page page) {
		Map<String,Object> param = new HashMap<>();
		param.put("baseAttributeVo",baseAttributeVo);
		param.put("page",page);
		return baseAttributeMapper.getBaseAttributeInfoListPage(param);
	}


	/**
	 * @description 单位信息
	 * @author bill
	 * @param
	 * @date 2019/5/13
	 */
	@Override
	public List<Map<String, Object>> getUnitInfoMap(Map<String, Object> paramMap) {

		return baseAttributeMapper.getUnitInfoMap(paramMap);
	}

	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public ResultInfo delAttribute(Map<String, Object> paramMap) {
		// 删除属性信息
		paramMap.put("modTime", new Date());
		Integer delRes = baseAttributeMapper.delAttribute(paramMap);
		if(null != delRes && delRes > 0){
			// -- 删除属性值
			baseAttributeValueMapper.deleteAttrValByParam(paramMap);
			return new ResultInfo(CommonConstants.SUCCESS_CODE, "删除成功");
		}
		else{
			return new ResultInfo(CommonConstants.FAIL_CODE, "属性删除失败");
		}
	}

	@Override
	public List<BaseAttributeVo> getAllAttributeByParam() {
		Map<String,Object> param = new HashMap<>();
		BaseAttributeVo baseAttributeVo = new BaseAttributeVo();
		baseAttributeVo.setIsDeleted(CommonConstants.IS_DELETE_0);
		param.put("baseAttributeVo",baseAttributeVo);
		return baseAttributeMapper.getBaseAttributeInfoListPage(param);
	}

    @Override
    public List<BaseAttributeVo> getAttributeListByCategoryId(List<BaseCategoryVo> list) {
        Map<String,Object> map = new HashMap<>();
		map.put("list",list);
		map.put("isDeleted",CommonConstants.IS_DELETE_0);//未删除
		return baseAttributeMapper.getAttributeListByCategoryId(map);
    }

	@Override
	public List<BaseAttributeVo> doAttrAndValue(List<BaseAttributeVo> list, List<BaseAttributeValueVo> baseAttributeValueVoList) {
		if (CollectionUtils.isNotEmpty(list)){
			for (BaseAttributeVo baseAttributeVo : list){
				if (CollectionUtils.isNotEmpty(baseAttributeValueVoList)){
					List<BaseAttributeValueVo> attrValueList = new ArrayList<>();
					for (BaseAttributeValueVo baseAttributeValueVo : baseAttributeValueVoList){
						if (baseAttributeVo.getBaseAttributeId().equals(baseAttributeValueVo.getBaseAttributeId())){
							attrValueList.add(baseAttributeValueVo);
						}
					}
					baseAttributeVo.setAttrValue(attrValueList);
				}
			}
		}
		return list;
	}

	@Override
	public BaseAttribute selectByPrimaryKey(Integer baseAttributeVoId) {
		return baseAttributeMapper.selectByPrimaryKey(baseAttributeVoId);
	}
}
