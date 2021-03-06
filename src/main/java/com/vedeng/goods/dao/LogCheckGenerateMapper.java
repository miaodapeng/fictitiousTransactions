package com.vedeng.goods.dao;

import com.vedeng.goods.model.LogCheckGenerate;
import com.vedeng.goods.model.LogCheckGenerateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LogCheckGenerateMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_LOG_CHECK
     *
     * @mbggenerated Fri May 31 09:38:39 CST 2019
     */
    int countByExample(LogCheckGenerateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_LOG_CHECK
     *
     * @mbggenerated Fri May 31 09:38:39 CST 2019
     */
    int deleteByExample(LogCheckGenerateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_LOG_CHECK
     *
     * @mbggenerated Fri May 31 09:38:39 CST 2019
     */
    int deleteByPrimaryKey(Integer logId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_LOG_CHECK
     *
     * @mbggenerated Fri May 31 09:38:39 CST 2019
     */
    int insert(LogCheckGenerate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_LOG_CHECK
     *
     * @mbggenerated Fri May 31 09:38:39 CST 2019
     */
    int insertSelective(LogCheckGenerate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_LOG_CHECK
     *
     * @mbggenerated Fri May 31 09:38:39 CST 2019
     */
    List<LogCheckGenerate> selectByExample(LogCheckGenerateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_LOG_CHECK
     *
     * @mbggenerated Fri May 31 09:38:39 CST 2019
     */
    LogCheckGenerate selectByPrimaryKey(Integer logId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_LOG_CHECK
     *
     * @mbggenerated Fri May 31 09:38:39 CST 2019
     */
    int updateByExampleSelective(@Param("record") LogCheckGenerate record, @Param("example") LogCheckGenerateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_LOG_CHECK
     *
     * @mbggenerated Fri May 31 09:38:39 CST 2019
     */
    int updateByExample(@Param("record") LogCheckGenerate record, @Param("example") LogCheckGenerateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_LOG_CHECK
     *
     * @mbggenerated Fri May 31 09:38:39 CST 2019
     */
    int updateByPrimaryKeySelective(LogCheckGenerate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_LOG_CHECK
     *
     * @mbggenerated Fri May 31 09:38:39 CST 2019
     */
    int updateByPrimaryKey(LogCheckGenerate record);
}