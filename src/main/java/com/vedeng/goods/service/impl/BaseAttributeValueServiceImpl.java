package com.vedeng.goods.service.impl;

import com.vedeng.common.constant.CommonConstants;
import com.vedeng.goods.dao.BaseAttributeValueMapper;
import com.vedeng.goods.model.vo.BaseAttributeValueVo;
import com.vedeng.goods.model.vo.BaseAttributeVo;
import com.vedeng.goods.model.vo.BaseCategoryVo;
import com.vedeng.goods.service.BaseAttributeValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("baseAttributeValueService")
public class BaseAttributeValueServiceImpl implements BaseAttributeValueService {

    @Autowired
    private BaseAttributeValueMapper baseAttributeValueMapper;

    @Override
    public List<BaseAttributeValueVo> getBaseAttributeValueVoList(BaseAttributeValueVo baseAttributeValueVo,List<BaseAttributeVo> list) {
        Map<String,Object> map = new HashMap<>();
        map.put("baseAttributeValueVo",baseAttributeValueVo);
        map.put("list",list);
        return baseAttributeValueMapper.getBaseAttributeValueVoList(map);
    }

    @Override
    public List<BaseAttributeValueVo> getBaseAttributeValueVoListByAttrId(BaseAttributeValueVo baseAttributeValueVo) {

        return baseAttributeValueMapper.getBaseAttributeValueVoListByAttrId(baseAttributeValueVo);
    }

    @Override
    public List<BaseAttributeValueVo> getAttrValueListByCategoryId(List<BaseCategoryVo> list) {
        Map<String,Object> map = new HashMap<>();
        map.put("list",list);
        map.put("isDeleted", CommonConstants.IS_DELETE_0);//未删除
        return baseAttributeValueMapper.getAttrValueListByCategoryId(map);
    }

    @Override
    public List<BaseAttributeValueVo> getBaseAttributeValueVoListASC(BaseAttributeValueVo baseAttributeValueVo, List<BaseAttributeVo> list) {
        Map<String,Object> map = new HashMap<>();
        map.put("baseAttributeValueVo",baseAttributeValueVo);
        map.put("list",list);
        return baseAttributeValueMapper.getBaseAttributeValueVoListASC(map);
    }
}
