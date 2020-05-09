package com.vedeng.order.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.vedeng.goods.model.Goods;
import com.vedeng.order.model.adk.TAdkGoods;
import com.vedeng.order.model.adk.TAdkGoodsExample;

public interface AdkGoodsMapper {
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table T_ADK_GOODS
	 *
	 * @mbg.generated Tue Apr 09 20:42:17 CST 2019
	 */
	long countByExample(TAdkGoodsExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table T_ADK_GOODS
	 *
	 * @mbg.generated Tue Apr 09 20:42:17 CST 2019
	 */
	int deleteByExample(TAdkGoodsExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table T_ADK_GOODS
	 *
	 * @mbg.generated Tue Apr 09 20:42:17 CST 2019
	 */
	int deleteByPrimaryKey(Integer adkGoodsId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table T_ADK_GOODS
	 *
	 * @mbg.generated Tue Apr 09 20:42:17 CST 2019
	 */
	int insert(TAdkGoods record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table T_ADK_GOODS
	 *
	 * @mbg.generated Tue Apr 09 20:42:17 CST 2019
	 */
	int insertSelective(TAdkGoods record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table T_ADK_GOODS
	 *
	 * @mbg.generated Tue Apr 09 20:42:17 CST 2019
	 */
	List<TAdkGoods> selectByExample(TAdkGoodsExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table T_ADK_GOODS
	 *
	 * @mbg.generated Tue Apr 09 20:42:17 CST 2019
	 */
	TAdkGoods selectByPrimaryKey(Integer adkGoodsId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table T_ADK_GOODS
	 *
	 * @mbg.generated Tue Apr 09 20:42:17 CST 2019
	 */
	int updateByExampleSelective(@Param("record") TAdkGoods record, @Param("example") TAdkGoodsExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table T_ADK_GOODS
	 *
	 * @mbg.generated Tue Apr 09 20:42:17 CST 2019
	 */
	int updateByExample(@Param("record") TAdkGoods record, @Param("example") TAdkGoodsExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table T_ADK_GOODS
	 *
	 * @mbg.generated Tue Apr 09 20:42:17 CST 2019
	 */
	int updateByPrimaryKeySelective(TAdkGoods record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table T_ADK_GOODS
	 *
	 * @mbg.generated Tue Apr 09 20:42:17 CST 2019
	 */
	int updateByPrimaryKey(TAdkGoods record);

	List<Goods> batchVailGoodsSku(@Param("skuList") List<String> skuList);

}