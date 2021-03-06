package com.vedeng.goods.dao;

import com.vedeng.goods.model.CoreSpuSearchGenerate;
import com.vedeng.goods.model.CoreSpuSearchGenerateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CoreSpuSearchGenerateMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_CORE_SPU_SEARCH
     *
     * @mbggenerated Thu Jun 27 19:06:50 CST 2019
     */
    int countByExample(CoreSpuSearchGenerateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_CORE_SPU_SEARCH
     *
     * @mbggenerated Thu Jun 27 19:06:50 CST 2019
     */
    int deleteByExample(CoreSpuSearchGenerateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_CORE_SPU_SEARCH
     *
     * @mbggenerated Thu Jun 27 19:06:50 CST 2019
     */
    int deleteByPrimaryKey(Integer spuId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_CORE_SPU_SEARCH
     *
     * @mbggenerated Thu Jun 27 19:06:50 CST 2019
     */
    int insert(CoreSpuSearchGenerate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_CORE_SPU_SEARCH
     *
     * @mbggenerated Thu Jun 27 19:06:50 CST 2019
     */
    int insertSelective(CoreSpuSearchGenerate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_CORE_SPU_SEARCH
     *
     * @mbggenerated Thu Jun 27 19:06:50 CST 2019
     */
    List<CoreSpuSearchGenerate> selectByExample(CoreSpuSearchGenerateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_CORE_SPU_SEARCH
     *
     * @mbggenerated Thu Jun 27 19:06:50 CST 2019
     */
    CoreSpuSearchGenerate selectByPrimaryKey(Integer spuId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_CORE_SPU_SEARCH
     *
     * @mbggenerated Thu Jun 27 19:06:50 CST 2019
     */
    int updateByExampleSelective(@Param("record") CoreSpuSearchGenerate record, @Param("example") CoreSpuSearchGenerateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_CORE_SPU_SEARCH
     *
     * @mbggenerated Thu Jun 27 19:06:50 CST 2019
     */
    int updateByExample(@Param("record") CoreSpuSearchGenerate record, @Param("example") CoreSpuSearchGenerateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_CORE_SPU_SEARCH
     *
     * @mbggenerated Thu Jun 27 19:06:50 CST 2019
     */
    int updateByPrimaryKeySelective(CoreSpuSearchGenerate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_CORE_SPU_SEARCH
     *
     * @mbggenerated Thu Jun 27 19:06:50 CST 2019
     */
    int updateByPrimaryKey(CoreSpuSearchGenerate record);
}