package com.vedeng.goods.service.impl;

import com.alibaba.fastjson.JSON;
import com.vedeng.common.constant.goods.GoodsCheckStatusEnum;
import com.vedeng.goods.dao.*;
import com.vedeng.goods.model.*;
import com.vedeng.goods.model.dto.CoreSkuBaseDTO;
import com.vedeng.goods.model.dto.CoreSpuBaseDTO;
import com.vedeng.goods.service.BaseGoodsService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class BaseGoodsServiceImpl implements BaseGoodsService {

	Logger logger = LoggerFactory.getLogger(BaseGoodsServiceImpl.class);
	@Autowired
	CoreSpuGenerateExtendMapper coreSpuGenerateExtendMapper;
	@Autowired
	CoreSpuSearchGenerateMapper spuSearchGenerateMapper;
	@Autowired
	CoreSkuSearchGenerateMapper skuSearchGenerateMapper;
	@Autowired
	CoreSpuGenerateMapper coreSpuGenerateMapper;
	@Autowired
	CoreSkuGenerateMapper coreSkuGenerateMapper;
	@Override
	public CoreSpuBaseDTO selectSpuBaseById(Integer spuId) {
		return coreSpuGenerateExtendMapper.selectSpuBaseBySpuId(spuId);
	}

	@Override
	public CoreSkuBaseDTO selectSkuBaseById(Integer skuId) {
		return coreSpuGenerateExtendMapper.selectSkuBaseBySkuId(skuId);
	}

	@Override
	public List<CoreSkuBaseDTO> selectSkuBaseByIds(String[] skuIds) {
		return coreSpuGenerateExtendMapper.selectSkuBaseBySkuIds(skuIds);
	}

    @Override
    public void flushSpu() {

    }

	@Override
	public void flushSku() {

	}

	@Override
	public void mergeSpu(CoreSpuGenerate spu) {
		if(spu==null){
			return;
		}
		CoreSpuSearchGenerate search=new CoreSpuSearchGenerate();
		BeanUtils.copyProperties(spu,search);
		if(spu.getSpuId()!=null){
			coreSpuGenerateMapper.updateByPrimaryKeySelective(spu);
			//审核通过才同步
			if(GoodsCheckStatusEnum.isApprove(search.getCheckStatus())){

				if(spuSearchGenerateMapper.selectByPrimaryKey(spu.getSpuId())==null){
					CoreSpuGenerate dbPo=coreSpuGenerateMapper.selectByPrimaryKey(spu.getSpuId());
					CoreSpuSearchGenerate searchInsert=new CoreSpuSearchGenerate();
					BeanUtils.copyProperties(dbPo,searchInsert);
					coreSpuGenerateExtendMapper.insertSpuSearch(searchInsert);
				}else{
					CoreSpuGenerate dbPo=coreSpuGenerateMapper.selectByPrimaryKey(spu.getSpuId());
					CoreSpuSearchGenerate searchInsert=new CoreSpuSearchGenerate();
					BeanUtils.copyProperties(dbPo,searchInsert);
					spuSearchGenerateMapper.updateByPrimaryKeySelective(searchInsert);
				}

			}
		}else{
			coreSpuGenerateMapper.insert(spu);
//			if(GoodsCheckStatusEnum.isApprove(search.getCheckStatus())){
//
//			}
		}
	}

	@Override
	public void mergeSku(CoreSkuGenerate sku) {
		if(sku==null){
			return;
		}
		CoreSkuSearchGenerate search=new CoreSkuSearchGenerate();
		BeanUtils.copyProperties(sku,search);
		if(sku.getSkuId()!=null){
		coreSkuGenerateMapper.updateByPrimaryKeySelective(sku);
		try {
			Integer spuid=coreSkuGenerateMapper.selectByPrimaryKey(sku.getSkuId()).getSpuId();
			CoreSpuGenerate updateSpu=new CoreSpuGenerate();
			updateSpu.setSpuId(spuid);
			updateSpu.setModTime(new Date());
			updateSpu.setUpdater(sku.getUpdater());
			coreSpuGenerateMapper.updateByPrimaryKeySelective(updateSpu);
		}catch (Exception e){
			//ignor
		}
		//审核通过才同步
		if(GoodsCheckStatusEnum.isApprove(search.getCheckStatus())){
			if(skuSearchGenerateMapper.selectByPrimaryKey(sku.getSkuId())==null){
				CoreSkuGenerate dbPo=coreSkuGenerateMapper.selectByPrimaryKey(sku.getSkuId());
				CoreSkuSearchGenerate searchInsert=new CoreSkuSearchGenerate();
				BeanUtils.copyProperties(dbPo,searchInsert);
				try{
				searchInsert.setMinOrder(dbPo.getMinOrder());
				}catch(Exception e){}
				coreSpuGenerateExtendMapper.insertSkuSearch(searchInsert);
			}else{
				CoreSkuGenerate dbPo=coreSkuGenerateMapper.selectByPrimaryKey(sku.getSkuId());
				CoreSkuSearchGenerate searchInsert=new CoreSkuSearchGenerate();
				BeanUtils.copyProperties(dbPo,searchInsert);try{
				searchInsert.setMinOrder(dbPo.getMinOrder());}catch(Exception e){}
				skuSearchGenerateMapper.updateByPrimaryKeySelective(searchInsert);
			}
		}
		}else{
			coreSkuGenerateMapper.insert(sku);

		}
	}

	@Override
	@Deprecated
	public void mergeSkuByIds(CoreSkuGenerate generate, List<Integer> list) {
		CoreSkuGenerateExample example = new CoreSkuGenerateExample();
		example.createCriteria().andSkuIdIn(list);
		//CoreSkuSearchGenerate search=new CoreSkuSearchGenerate();
		//BeanUtils.copyProperties(generate,search);
		List<CoreSkuGenerate> listDb=coreSkuGenerateMapper.selectByExample(example);

		if(CollectionUtils.isNotEmpty(listDb)){
			for(CoreSkuGenerate sku:listDb){
				generate.setSkuId(sku.getSkuId());
				generate.setCheckStatus(sku.getCheckStatus());
				mergeSku(generate);
			}
		}
		///CoreSkuSearchGenerateExample exampleSearch = new CoreSkuSearchGenerateExample();
		//exampleSearch.createCriteria().andSkuIdIn(list);
		//skuSearchGenerateMapper.updateByExampleSelective(search, exampleSearch);
	}

	@Override
	public int updatePushStatusBySpuId(Integer spuId, int status) {
		return coreSkuGenerateMapper.updatePushStatusBySpuId(spuId,status);
	}


	@Override
	public int getPushStatusBySkuId(Integer skuId) {
		return coreSkuGenerateMapper.getPushStatusBySkuId(skuId);
	}

	@Override
	public int updatePushStatusBySkuId(Integer skuId, int status) {
		return coreSkuGenerateMapper.updatePushStatusBySkuId(skuId,status);
	}
}
