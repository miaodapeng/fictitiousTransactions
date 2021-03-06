package com.vedeng.goods.dao;

import com.vedeng.goods.model.GoodsGysOptionAttributegenerate;
import com.vedeng.goods.model.GoodsGysOptionAttributegenerateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GoodsGysOptionAttributegenerateMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_GOODS_SYS_OPTION_ATTRIBUTE
     *
     * @mbggenerated Thu Jun 27 19:06:50 CST 2019
     */
    int countByExample(GoodsGysOptionAttributegenerateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_GOODS_SYS_OPTION_ATTRIBUTE
     *
     * @mbggenerated Thu Jun 27 19:06:50 CST 2019
     */
    int deleteByExample(GoodsGysOptionAttributegenerateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_GOODS_SYS_OPTION_ATTRIBUTE
     *
     * @mbggenerated Thu Jun 27 19:06:50 CST 2019
     */
    int deleteByPrimaryKey(Integer goodsSysOptionAttributeId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_GOODS_SYS_OPTION_ATTRIBUTE
     *
     * @mbggenerated Thu Jun 27 19:06:50 CST 2019
     */
    int insert(GoodsGysOptionAttributegenerate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_GOODS_SYS_OPTION_ATTRIBUTE
     *
     * @mbggenerated Thu Jun 27 19:06:50 CST 2019
     */
    int insertSelective(GoodsGysOptionAttributegenerate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_GOODS_SYS_OPTION_ATTRIBUTE
     *
     * @mbggenerated Thu Jun 27 19:06:50 CST 2019
     */
    List<GoodsGysOptionAttributegenerate> selectByExample(GoodsGysOptionAttributegenerateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_GOODS_SYS_OPTION_ATTRIBUTE
     *
     * @mbggenerated Thu Jun 27 19:06:50 CST 2019
     */
    GoodsGysOptionAttributegenerate selectByPrimaryKey(Integer goodsSysOptionAttributeId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_GOODS_SYS_OPTION_ATTRIBUTE
     *
     * @mbggenerated Thu Jun 27 19:06:50 CST 2019
     */
    int updateByExampleSelective(@Param("record") GoodsGysOptionAttributegenerate record, @Param("example") GoodsGysOptionAttributegenerateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_GOODS_SYS_OPTION_ATTRIBUTE
     *
     * @mbggenerated Thu Jun 27 19:06:50 CST 2019
     */
    int updateByExample(@Param("record") GoodsGysOptionAttributegenerate record, @Param("example") GoodsGysOptionAttributegenerateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_GOODS_SYS_OPTION_ATTRIBUTE
     *
     * @mbggenerated Thu Jun 27 19:06:50 CST 2019
     */
    int updateByPrimaryKeySelective(GoodsGysOptionAttributegenerate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_GOODS_SYS_OPTION_ATTRIBUTE
     *
     * @mbggenerated Thu Jun 27 19:06:50 CST 2019
     */
    int updateByPrimaryKey(GoodsGysOptionAttributegenerate record);
}