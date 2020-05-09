package com.vedeng.goods.dao;

import com.vedeng.goods.model.*;
import com.vedeng.goods.model.dto.CoreSkuBaseDTO;
import com.vedeng.goods.model.dto.CoreSpuBaseDTO;
import com.vedeng.goods.model.dto.SpuSkuIdForListDTO;
import com.vedeng.goods.model.vo.BaseAttributeValueVo;
import com.vedeng.goods.model.vo.BaseAttributeVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CoreSpuGenerateExtendMapper {

	int insertSpu(CoreSpuGenerate record);
	int insertSku(CoreSkuGenerate record);
	int insertSpuSearch(CoreSpuSearchGenerate record);
	int insertSkuSearch(CoreSkuSearchGenerate record);
	int insertGoods(GoodsGenerateWithBLOBs record);
	int selectCateId(@Param("name3") String name3,@Param("name2") String name2,@Param("name1") String name1);
	int selectOldCateId(@Param("name3") String name3,@Param("name2") String name2,@Param("name1") String name1);


	List<BaseAttributeValueVo> selectOldCateIdAttr(@Param("goodsId")Integer goodsId);

	CoreSpuBaseDTO selectSpuBaseBySpuId(@Param("spuId") Integer spuId);

	List<SpuSkuIdForListDTO> selectSpuListPage(Map<String, Object> map);

	CoreSkuBaseDTO selectSkuBaseBySkuId(@Param("skuId") Integer skuId);

	List<CoreSkuBaseDTO> selectSkuBaseBySkuIds(@Param("skuIds") String[] skuIds);

	List<BaseAttributeVo> selectAllAttributeBySpuId(@Param("spuId") Integer spuId);

	List<BaseAttributeValueVo> selectAllAttributeValueByAttrId(@Param("baseAttributeId") Integer baseAttributeId,@Param("baseCategoryId") Integer baseCategoryId);



    void submitToCheck(@Param("spuId")Integer spuId,@Param("userId") Integer userId);


    List<BaseAttributeVo> selectAllAttributeByCategoryId(@Param("categoryId")Integer categoryId);

    List<Map<String,Object>> searchFirstEngageListPage(Map<String, Object> map );

    int countSkuBySpuIdAndSkuInfo(@Param("spuId")Integer spuId,@Param("model") String model,@Param("spec")String spec);

    List<Map<String, String>> searchSkuWithDepartment(@Param("skuName")String skuName);
}
