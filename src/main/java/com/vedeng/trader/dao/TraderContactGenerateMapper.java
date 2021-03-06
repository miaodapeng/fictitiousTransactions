package com.vedeng.trader.dao;

import com.vedeng.trader.model.TraderContactGenerate;
import com.vedeng.trader.model.TraderContactGenerateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TraderContactGenerateMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_TRADER_CONTACT
     *
     * @mbg.generated Sat Apr 20 17:16:41 CST 2019
     */
    long countByExample(TraderContactGenerateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_TRADER_CONTACT
     *
     * @mbg.generated Sat Apr 20 17:16:41 CST 2019
     */
    int deleteByExample(TraderContactGenerateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_TRADER_CONTACT
     *
     * @mbg.generated Sat Apr 20 17:16:41 CST 2019
     */
    int deleteByPrimaryKey(Integer traderContactId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_TRADER_CONTACT
     *
     * @mbg.generated Sat Apr 20 17:16:41 CST 2019
     */
    int insert(TraderContactGenerate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_TRADER_CONTACT
     *
     * @mbg.generated Sat Apr 20 17:16:41 CST 2019
     */
    int insertSelective(TraderContactGenerate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_TRADER_CONTACT
     *
     * @mbg.generated Sat Apr 20 17:16:41 CST 2019
     */
    List<TraderContactGenerate> selectByExample(TraderContactGenerateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_TRADER_CONTACT
     *
     * @mbg.generated Sat Apr 20 17:16:41 CST 2019
     */
    TraderContactGenerate selectByPrimaryKey(Integer traderContactId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_TRADER_CONTACT
     *
     * @mbg.generated Sat Apr 20 17:16:41 CST 2019
     */
    int updateByExampleSelective(@Param("record") TraderContactGenerate record, @Param("example") TraderContactGenerateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_TRADER_CONTACT
     *
     * @mbg.generated Sat Apr 20 17:16:41 CST 2019
     */
    int updateByExample(@Param("record") TraderContactGenerate record, @Param("example") TraderContactGenerateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_TRADER_CONTACT
     *
     * @mbg.generated Sat Apr 20 17:16:41 CST 2019
     */
    int updateByPrimaryKeySelective(TraderContactGenerate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_TRADER_CONTACT
     *
     * @mbg.generated Sat Apr 20 17:16:41 CST 2019
     */
    int updateByPrimaryKey(TraderContactGenerate record);

    /**
     * 功能描述: 根据条件查询客户联系人是否存在
     * @param: [traderId, traderType1, name, telephone, mobile]
     * @return: com.vedeng.trader.model.TraderContactGenerate
     * @auther: duke.li
     * @date: 2019/8/13 17:31
     */
    TraderContactGenerate getContactInfo(@Param("traderId")Integer traderId,@Param("traderType") Integer traderType,@Param("name") String name,@Param("telephone") String telephone,@Param("mobile") String mobile);

    TraderContactGenerate getTraderDefaultContact(@Param("traderId") Integer traderId);
}