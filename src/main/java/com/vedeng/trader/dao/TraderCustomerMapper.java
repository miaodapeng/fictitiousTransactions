package com.vedeng.trader.dao;

import com.vedeng.authorization.model.Organization;
import com.vedeng.trader.model.TraderCustomer;
import com.vedeng.trader.model.vo.TraderCustomerVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface TraderCustomerMapper {

    TraderCustomer selectByPrimaryKey(Integer traderCustomerId);

    /**
     * <b>Description:</b>更新是否为盈利机构的状态<br>
     * @param traderId 客户的标识
     * @param isProfit 是否为盈利机构
     * @return
     * @Note
     * <b>Author:calvin</b>
     * <br><b>Date:2019/9/12</b>
     */
    int updateCustomerIsProfit(@Param("traderId") Integer traderId, @Param("isProfit")Integer isProfit);

    /**
     * <b>Description:</b>分页查询客户列表信息，根据资质审核状态删选<br>
     * @param
     * @return
     * @Note
     * <b>Author:calvin</b>
     * <br><b>Date:</b> 2019/9/12
     */
    List<TraderCustomer> getTraderCustomerIdsListPage(Map<String,Object> paramMap);
    /**
     * <b>Description:</b>根据组织部门分页获取客户信息<br>
     * @param
     * @return
     * @Note
     * <b>Author:calvin</b>
     * <br><b>Date:</b> 2019/11/26
     */
    List<TraderCustomer> getTraderCustomerByOrganizationsListPage(Map<String,Object> paramMap);

    TraderCustomer getTraderCustomerByTraderId(@Param("traderId")Integer traderId);

    TraderCustomer getBaseCustomerByTraderId(@Param("traderId")Integer traderId);
    /**
     * <b>Description:分页查询客户列表</b><br>
     * @param paramMap 分页信息
     * @return 客户列表信息
     * @Note
     * <b>Author:calvin</b>
     * <br><b>Date:2019/10/29</b>
     */
    List<TraderCustomer> getTraderCustomerListPage(Map<String,Object> paramMap);
    /**
     * <b>Description:分页查询客户列表</b><br>
     * @param paramMap 分页信息
     * @return 客户列表信息
     * @Note
     * <b>Author:calvin</b>
     * <br><b>Date:2019/10/29</b>
     */
    List<TraderCustomer> getYXGCustomerListPage(Map<String,Object> paramMap);

    /**
     * <b>Description:</b>根据客户标识更新客户分类字段<br>
     * @param trader 客户信息
     * @return
     * @Note
     * <b>Author:calvin</b>
     * <br><b>Date:</b> 2019/10/30
     */
    int updateCustomerCategoryById(TraderCustomer trader);

    /**
     * <b>Description:</b>根据客户标识更新客户等级<br>
     * @param
     * @return
     * @Note
     * <b>Author:calvin</b>
     * <br><b>Date:</b> 2019/10/30
     */
    int updateCustomerLevelById(TraderCustomer traderCustomer);

    /**
     * <b>Description:</b><br> 获取客户信息
     * @param traderId
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年6月26日 下午2:02:46
     */
    TraderCustomerVo getCustomerInfo(@Param("traderId")Integer traderId);

    TraderCustomer getTraderCustomerId(@Param("traderId") Integer traderId);
    /**
    *   获取符合条件的会员
    * @Author:strange
    * @Date:18:25 2020-04-08
    */
    List<Integer> getTraderCustomerIsMember();
    /**
    *更新为会员
    * @Author:strange
    * @Date:18:43 2020-04-08
    */
    Integer updateIsVedengMember(@Param("list")List<Integer> customerIdList,@Param("status")  Integer status);
    TraderCustomer getTraderCustomerById(@Param("traderId") Integer traderId);


    TraderCustomer getTraderCustomerByPayApply(Integer payApplyId);

    void updateTraderCustomerAmount(TraderCustomer tc);
}
