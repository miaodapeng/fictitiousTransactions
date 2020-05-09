package com.vedeng.goods.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.CommonConstants;
import com.vedeng.common.page.Page;
import com.vedeng.goods.dao.SetMealExtendMapper;
import com.vedeng.goods.dao.SetMealMapper;
import com.vedeng.goods.dao.SetMealSkuMappingExtendMapper;
import com.vedeng.goods.dao.SetMealSkuMappingMapper;
import com.vedeng.goods.model.SetMeal;
import com.vedeng.goods.model.vo.SetMealSkuMappingVo;
import com.vedeng.goods.model.vo.SetMealVo;
import com.vedeng.goods.service.GoodsSetMealService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("goodsSetMealService")
public class GoodsSetMealServiceImpl implements GoodsSetMealService {

    @Autowired
    private SetMealMapper setMealMapper;

    @Autowired
    private SetMealExtendMapper setMealExtendMapper;

    @Autowired
    private SetMealSkuMappingMapper setMealSkuMappingMapper;

    @Autowired
    private SetMealSkuMappingExtendMapper setMealSkuMappingExtendMapper;

    @Override
    public List<SetMeal> getSetMealListPage(SetMealVo setMealVo, Page page) {
        Map<String,Object> map = new HashMap<>();
        map.put("setMealVo",setMealVo);
        map.put("page",page);
        return setMealExtendMapper.getSetMealListPage(map);
    }

    @Override
    public SetMeal getSetMealById(Integer setMealId) {

        return setMealMapper.selectByPrimaryKey(setMealId);
    }

    @Override
    public List<SetMealSkuMappingVo> getSetMealSkuMappingVoList(SetMealSkuMappingVo setMealSkuMappingVo) {
        //获取商品关联信息列表
        List<SetMealSkuMappingVo> setMealSkuMappingVoList = setMealSkuMappingExtendMapper.getSetMealSkuMappingVoList(setMealSkuMappingVo);
        //批量查询科室名称信息,预先处理选中的科室ID列表
        if (CollectionUtils.isNotEmpty(setMealSkuMappingVoList)){
            //查询商品绑定的所有科室，方便前端回显
            this.getDepartmentByskuIds(setMealSkuMappingVoList);
            List<SetMealSkuMappingVo> setMealSkuMappingVos = new ArrayList<>();
            for (SetMealSkuMappingVo mealSkuMappingVo : setMealSkuMappingVoList){
                if (mealSkuMappingVo.getDepartmentIds()!=null && !"".equals(mealSkuMappingVo.getDepartmentIds())){
                    String[] departmentIdArray = mealSkuMappingVo.getDepartmentIds().split(",");
                    for (String departmentId : departmentIdArray){
                        SetMealSkuMappingVo skuMappingVo = new SetMealSkuMappingVo();
                        skuMappingVo.setSkuSetMealMappingId(mealSkuMappingVo.getSkuSetMealMappingId());
                        skuMappingVo.setDepartmentId(Integer.valueOf(departmentId));
                        setMealSkuMappingVos.add(skuMappingVo);
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(setMealSkuMappingVos)){
                //查询科室名称信息
                List<SetMealSkuMappingVo> skuMappingVoList = setMealSkuMappingExtendMapper.getSetMealSkuDepartmentVoList(setMealSkuMappingVos);
                //处理科室信息，拼接科室名称，以、分割
                if (CollectionUtils.isNotEmpty(skuMappingVoList)){
                    for (SetMealSkuMappingVo skuMappingVo : setMealSkuMappingVoList){
                        String[] departmentIdArray = skuMappingVo.getDepartmentIds().split(",");
                        for (SetMealSkuMappingVo mealSkuMappingVo : skuMappingVoList){
                            if (Arrays.asList(departmentIdArray).contains(String.valueOf(mealSkuMappingVo.getDepartmentId()))){
                                if (skuMappingVo.getDepartmentNames()!=null && !"".equals(skuMappingVo.getDepartmentNames())){
                                    skuMappingVo.setDepartmentNames(skuMappingVo.getDepartmentNames()+"、"+mealSkuMappingVo.getDepartmentName());
                                }else{
                                    skuMappingVo.setDepartmentNames(mealSkuMappingVo.getDepartmentName());
                                }
                            }
                        }

                    }
                }
            }
            for (SetMealSkuMappingVo skuMappingVo : setMealSkuMappingVoList){
                //自身转化为json串，为了适应前端页面显示
                skuMappingVo.setObjectJson(JSONArray.toJSONString(skuMappingVo));
            }
        }
        return setMealSkuMappingVoList;
    }

    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public Integer saveSetMeal(SetMealVo setMealVo, User user) {
        Date nowDate = new Date();
        Integer result;
        if (setMealVo.getSetMealId()!=null){//编辑保存,执行更新操作
            setMealVo.setUpdater(user.getUserId());
            setMealVo.setModTime(nowDate);
            result = setMealMapper.updateByPrimaryKeySelective(setMealVo);
        }else{//新增保存,执行插入操作
            setMealVo.setCreator(user.getUserId());
            setMealVo.setUpdater(user.getUserId());
            setMealVo.setAddTime(nowDate);
            setMealVo.setModTime(nowDate);
            result = setMealMapper.insertSelective(setMealVo);
        }
        if (result > 0){//套餐保存成功，插入套餐明细列表
            //删除原套餐详情列表
            Map<String,Object> map = new HashMap<>();
            List<Integer> setMealIdList = new ArrayList<>();
            setMealIdList.add(setMealVo.getSetMealId());
            map.put("isDeleted", CommonConstants.IS_DELETE_1);
            map.put("list",setMealIdList);
            map.put("userId",user.getUserId());
            map.put("nowDate",nowDate);
            setMealSkuMappingExtendMapper.deleteSetMealSkuMapping(map);
            List<SetMealSkuMappingVo> setMealSkuMappingVoList = setMealVo.getSetMealSkuMappingVoList();
            if (CollectionUtils.isNotEmpty(setMealSkuMappingVoList)){
                //插入新的套餐详情列表
                for (SetMealSkuMappingVo setMealSkuMappingVo : setMealSkuMappingVoList){
                    setMealSkuMappingVo.setSetMealId(setMealVo.getSetMealId());
                    setMealSkuMappingVo.setCreator(user.getUserId());
                    setMealSkuMappingVo.setUpdater(user.getUserId());
                    setMealSkuMappingVo.setAddTime(nowDate);
                    setMealSkuMappingVo.setModTime(nowDate);
                    setMealSkuMappingVo.setIsDeleted(CommonConstants.IS_DELETE_0);
                }
                setMealSkuMappingExtendMapper.insertBatch(setMealSkuMappingVoList);
            }
        }
        return setMealVo.getSetMealId();
    }

    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public Integer deleteSetMeal(String setMealIds,String deletedReason, User user) {
        Date nowDate = new Date();
        String[] setMealIdArray = setMealIds.split(",");
        List<Integer> setMealIdList = new ArrayList<>();
        for (String setMealId : setMealIdArray){
            setMealIdList.add(Integer.valueOf(setMealId));
        }
        Map<String,Object> map = new HashMap<>();
        map.put("isDeleted", CommonConstants.IS_DELETE_1);
        map.put("list",setMealIdList);
        map.put("userId",user.getUserId());
        map.put("nowDate",nowDate);
        map.put("deletedReason",deletedReason);
        //删除套餐信息
        Integer result = setMealExtendMapper.deleteSetMeal(map);
        if (result > 0){
            //删除套餐详情列表信息
            setMealSkuMappingExtendMapper.deleteSetMealSkuMapping(map);
        }
        return result;
    }

    @Override
    public List<SetMeal> getSetMealByIds(String setMealIds) {
        String[] setMealIdArray = setMealIds.split(",");
        List<Integer> setMealIdList = new ArrayList<>();
        for (String setMealId : setMealIdArray){
            setMealIdList.add(Integer.valueOf(setMealId));
        }
        return setMealExtendMapper.getSetMealByIds(setMealIdList);
    }

    @Override
    public void getDepartmentByskuIds(List<SetMealSkuMappingVo> setMealSkuMappingVoList) {
        if (CollectionUtils.isNotEmpty(setMealSkuMappingVoList)){
            //获取科室信息
            List<Map<String, Object>> objectMap = setMealSkuMappingExtendMapper.getDepartmentByskuIds(setMealSkuMappingVoList);
            if (CollectionUtils.isNotEmpty(objectMap)){
                for (SetMealSkuMappingVo setMealSkuMappingVo : setMealSkuMappingVoList){
                    List<Map<String,Object>> departments = Lists.newArrayList();
                    for (Map<String,Object> stringObjectMap : objectMap){
                        if (setMealSkuMappingVo.getSkuId().equals(stringObjectMap.get("SKU_ID"))){
                            Map<String,Object> departmentMap = Maps.newHashMap();
                            departmentMap.put("departmentName",stringObjectMap.get("DEPARTMENT_NAME"));
                            departmentMap.put("departmentId",stringObjectMap.get("DEPARTMENT_ID"));
                            departments.add(departmentMap);
                        }
                    }
                    setMealSkuMappingVo.setDepartments(departments);
                }
            }
        }
    }

    @Override
    public SetMeal getSetMealByName(String setMealName,Integer setMealId) {

        return setMealExtendMapper.getSetMealByName(setMealName, setMealId);
    }
}
