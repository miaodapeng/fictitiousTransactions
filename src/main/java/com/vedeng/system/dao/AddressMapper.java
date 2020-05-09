package com.vedeng.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.vedeng.system.model.Address;
import com.vedeng.system.model.vo.AddressVo;
import com.vedeng.system.model.vo.ParamsConfigVo;

public interface AddressMapper {
    int deleteByPrimaryKey(Integer addressId);

    int insert(Address record);

    int insertSelective(Address record);

    Address selectByPrimaryKey(Integer addressId);

    int updateByPrimaryKeySelective(Address record);

    int updateByPrimaryKey(Address record);
    
    /**
     * <b>Description:</b><br> 查询列表
     * @param addressIds
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年8月22日 下午5:25:46
     */
    List<AddressVo> getAddressVoList(@Param("addressIds")List<Integer> addressIds);
    
    /**
     * <b>Description:</b><br> 根据配置参数查询发货信息
     * @param paramsConfigVo
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年12月5日 下午5:49:38
     */
    AddressVo getAddressVoByConfigParams(ParamsConfigVo paramsConfigVo);
    
    /**
     * <b>Description:</b><br> 根据配置参数查询收货信息
     * @param paramsConfigVo
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年12月5日 下午5:49:38
     */
    List<AddressVo> getAddressVoListByConfigParams(ParamsConfigVo paramsConfigVo);
    
    /**
     * <b>Description:</b><br> 查询所有收货地址
     * @param 
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年12月5日 下午5:49:38
     */
    List<AddressVo> getAddressVoListByParam(@Param("companyId")Integer companyId);
}