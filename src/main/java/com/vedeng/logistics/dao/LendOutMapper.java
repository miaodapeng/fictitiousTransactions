package com.vedeng.logistics.dao;

import java.util.List;
import java.util.Map;

import com.vedeng.logistics.model.LendOut;

public interface LendOutMapper {
    int deleteByPrimaryKey(Integer lendOutId);

    int insert(LendOut record);

    int insertSelective(LendOut record);

    LendOut selectByPrimaryKey(Integer lendOutId);

    int updateByPrimaryKeySelective(LendOut record);

    int updateByPrimaryKey(LendOut record);

	/**	
	* @Description: TODO( 获取已经被快递关联的出库商品数)
	* @param @param lendout
	* @param @return   
	* @return Integer   
	* @author strange
	* @throws
	* @date 2019年9月5日
	*/
	Integer getKdNum(LendOut lendout);

	/**	
	* @Description: TODO(已出库数量)
	* @param @param lendout
	* @return Integer   
	* @author strange
	* @throws
	* @date 2019年9月5日
	*/
	Integer getdeliveryNum(LendOut lendout);

	/**	
	* @Description: TODO(外借入库列表页)
	* @param @param map
	* @param @return   
	* @return List<LendOut>   
	* @author strange
	* @throws
	* @date 2019年9月16日
	*/
	List<LendOut> getlendoutListPage(Map<String, Object> map);

	/**	
	* @Description: TODO(获取外借单数据)
	* @param @param lendout
	* @param @return   
	* @return List<LendOut>   
	* @author strange
	* @throws
	* @date 2019年9月19日
	*/
	List<LendOut> getLendOutInfoList(LendOut lendout);

	/**	
	* @Description: TODO(外借单id获取)
	* @param @param lendoutIdList
	* @param @return   
	* @return List<LendOut>   
	* @author strange
	* @throws
	* @date 2019年9月21日
	*/
	List<LendOut> getLendoutByLendoutIdList(List<Integer> lendoutIdList);

	/**	
	* @Description: TODO(修改出库外借单已发数量)
	* @param @param lendout
	* @param @return   
	* @return Integer   
	* @author strange
	* @throws
	* @date 2019年9月23日
	*/
	Integer editDeliverNum(LendOut lendout);
}