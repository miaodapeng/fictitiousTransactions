package com.vedeng.goods.dao;

import com.vedeng.goods.model.BaseAttributeGenerate;
import com.vedeng.goods.model.BaseAttributeGenerateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BaseAttributeGenerateMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_BASE_ATTRIBUTE
     *
     * @mbggenerated Thu Jun 27 19:06:50 CST 2019
     */
    int countByExample(BaseAttributeGenerateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_BASE_ATTRIBUTE
     *
     * @mbggenerated Thu Jun 27 19:06:50 CST 2019
     */
    int deleteByExample(BaseAttributeGenerateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_BASE_ATTRIBUTE
     *
     * @mbggenerated Thu Jun 27 19:06:50 CST 2019
     */
    int deleteByPrimaryKey(Integer baseAttributeId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_BASE_ATTRIBUTE
     *
     * @mbggenerated Thu Jun 27 19:06:50 CST 2019
     */
    int insert(BaseAttributeGenerate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_BASE_ATTRIBUTE
     *
     * @mbggenerated Thu Jun 27 19:06:50 CST 2019
     */
    int insertSelective(BaseAttributeGenerate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_BASE_ATTRIBUTE
     *
     * @mbggenerated Thu Jun 27 19:06:50 CST 2019
     */
    List<BaseAttributeGenerate> selectByExample(BaseAttributeGenerateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_BASE_ATTRIBUTE
     *
     * @mbggenerated Thu Jun 27 19:06:50 CST 2019
     */
    BaseAttributeGenerate selectByPrimaryKey(Integer baseAttributeId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_BASE_ATTRIBUTE
     *
     * @mbggenerated Thu Jun 27 19:06:50 CST 2019
     */
    int updateByExampleSelective(@Param("record") BaseAttributeGenerate record, @Param("example") BaseAttributeGenerateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_BASE_ATTRIBUTE
     *
     * @mbggenerated Thu Jun 27 19:06:50 CST 2019
     */
    int updateByExample(@Param("record") BaseAttributeGenerate record, @Param("example") BaseAttributeGenerateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_BASE_ATTRIBUTE
     *
     * @mbggenerated Thu Jun 27 19:06:50 CST 2019
     */
    int updateByPrimaryKeySelective(BaseAttributeGenerate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_BASE_ATTRIBUTE
     *
     * @mbggenerated Thu Jun 27 19:06:50 CST 2019
     */
    int updateByPrimaryKey(BaseAttributeGenerate record);
}