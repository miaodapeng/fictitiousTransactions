package com.vedeng.goods.dao;

import com.vedeng.goods.model.CoreSpuGenerate;
import com.vedeng.goods.model.CoreSpuGenerateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CoreSpuGenerateMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_CORE_SPU
     *
     * @mbggenerated Thu Jun 27 19:06:50 CST 2019
     */
    int countByExample(CoreSpuGenerateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_CORE_SPU
     *
     * @mbggenerated Thu Jun 27 19:06:50 CST 2019
     */
    int deleteByExample(CoreSpuGenerateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_CORE_SPU
     *
     * @mbggenerated Thu Jun 27 19:06:50 CST 2019
     */
    int deleteByPrimaryKey(Integer spuId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_CORE_SPU
     *
     * @mbggenerated Thu Jun 27 19:06:50 CST 2019
     */
    int insert(CoreSpuGenerate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_CORE_SPU
     *
     * @mbggenerated Thu Jun 27 19:06:50 CST 2019
     */
    int insertSelective(CoreSpuGenerate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_CORE_SPU
     *
     * @mbggenerated Thu Jun 27 19:06:50 CST 2019
     */
    List<CoreSpuGenerate> selectByExample(CoreSpuGenerateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_CORE_SPU
     *
     * @mbggenerated Thu Jun 27 19:06:50 CST 2019
     */
    CoreSpuGenerate selectByPrimaryKey(Integer spuId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_CORE_SPU
     *
     * @mbggenerated Thu Jun 27 19:06:50 CST 2019
     */
    int updateByExampleSelective(@Param("record") CoreSpuGenerate record, @Param("example") CoreSpuGenerateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_CORE_SPU
     *
     * @mbggenerated Thu Jun 27 19:06:50 CST 2019
     */
    int updateByExample(@Param("record") CoreSpuGenerate record, @Param("example") CoreSpuGenerateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_CORE_SPU
     *
     * @mbggenerated Thu Jun 27 19:06:50 CST 2019
     */
    int updateByPrimaryKeySelective(CoreSpuGenerate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_CORE_SPU
     *
     * @mbggenerated Thu Jun 27 19:06:50 CST 2019
     */
    int updateByPrimaryKey(CoreSpuGenerate record);
}