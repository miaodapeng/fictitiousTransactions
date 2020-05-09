package com.vedeng.authorization.dao;

import java.util.List;
import java.util.Map;

import com.vedeng.authorization.model.Position;
import org.apache.ibatis.annotations.Param;

public interface PositionMapper {
	
	/**
	 * @Title: getPositionByKey 
	 * @Description: 新增数据保存 
	 * @param @param positionId
	 * @param @return
	 * @return Position
	 * @throws
	 */
    int insert(Position record);

    /**
     * @Title: insert 
     * @Description: 根据主键查询对应详细信息  
     * @param @param record
     * @param @return
     * @return int
     * @throws
     */
    Position getPositionByKey(Position positionId);

    /**
     * @Title: update 
     * @Description: 修改职位信息 
     * @param @param record
     * @param @return
     * @return int
     * @throws
     */
    int update(Position record);

    
    /**
	 * 根据部门获取职位
	 * @param orgId
	 * @return
	 */
    List<Position> getPositByOrgId(Integer orgId);
    
    /**
     * 分页展示职位
     * @param map
     * @return
     */
    List<Position> querylistPage(Map<String, Object> map);
    
    /**
     * 
     * @Title: getPositionByUserId 
     * @Description: 查询用户职位 
     * @param @param userId
     * @param @return
     * @return List<Position>
     * @throws
     */
    List<Position> getPositionByUserId(Integer userId);

    /**
      *  查询用户的某个职位，针对可能出现的多职位情况
      * @author wayne.liu
      * @date  2019/6/20 16:48
      * @param  userId 用户Id
      * @return
      */
    List<Position> getPositionByUserIdAndCompanyId(@Param("userId") Integer userId, @Param("companyId")Integer companyId);
    
    /**
     * 
     * @Title: vailposition 
     * @Description: 验证职位 
     * @param @param record
     * @param @return
     * @return int
     * @throws
     */
    int vailPosition(Position record);
    
    /**
     * 
     * @Title: getPositByOrgIds 
     * @Description: 部门集合查询职位  
     * @param @param orgIds
     * @param @return
     * @return List<Position>
     * @throws
     */
    List<Position> getPositByOrgIds(List<Integer> orgIds);
    
    /**
     * <b>Description:</b><br> 删除职位
     * @param positId 职位ID
     * @return 成功1 失败0
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年4月25日 下午1:01:48
     */
    int delete(Integer positId);


    List<Position> getOrgListOfUsers(List<Integer> userList);

}