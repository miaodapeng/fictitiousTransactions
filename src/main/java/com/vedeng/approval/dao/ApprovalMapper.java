package com.vedeng.approval.dao;

import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.apache.ibatis.annotations.Param;

import com.vedeng.approval.model.ApprovalEntity;
import com.vedeng.approval.model.ApprovalRecordEntity;

@Named("approvalMapper")
public interface ApprovalMapper {
    
    
    /**
     * <b>Description:</b>查询列表
     * @param map
     * @return List<ApprovalEntity>
     * @Note
     * <b>Author：</b>hugh.yu
     * <b>Date:</b> 2019年1月16日 下午7:39:18
     */
    List<ApprovalEntity> queryListPage(Map<String, Object> map);
    
    /**
     * <b>Description:</b>根据Id查询一条
     * @param approvalId
     * @return ApprovalEntity
     * @Note
     * <b>Author：</b>hugh.yu
     * <b>Date:</b> 2019年1月16日 下午7:39:26
     */
    ApprovalEntity queryByApprovalId(@Param("approvalId")Integer approvalId);

    /**
     * <b>Description:</b>新增
     * @param approvalEntity
     * @return int
     * @Note
     * <b>Author：</b>hugh.yu
     * <b>Date:</b> 2019年1月16日 下午7:39:31
     */
    int insertApproval(ApprovalEntity approvalEntity);

    /**
     * <b>Description:</b>修改
     * @param approvalEntity
     * @return boolean
     * @Note
     * <b>Author：</b>hugh.yu
     * <b>Date:</b> 2019年1月16日 下午7:39:42
     */
    boolean updateApproval(ApprovalEntity approvalEntity);

    /**
     * <b>Description:</b>根据Id删除一=条
     * @param approvalId
     * @return int
     * @Note
     * <b>Author：</b>hugh.yu
     * <b>Date:</b> 2019年1月16日 下午7:39:51
     */
    int deleteByApprovalId(Integer approvalId);
    
    /**
     * <b>Description:</b>批量删除 (改用下面一个方法)
     * @param approvalId
     * @return int
     * @Note
     * <b>Author：</b>hugh.yu
     * <b>Date:</b> 2019年1月16日 下午7:39:59
     */
    int delApprovalBatch(Integer approvalId);
    
    /**
     * <b>Description:</b>改用此方法,降低数据库压力
     * @param idList
     * @return int
     * @Note
     * <b>Author：</b>hugh.yu
     * <b>Date:</b> 2019年1月16日 下午7:40:19
     */
    int delApprovalBatchNew(List<Integer> idList);

    /**
     * <b>Description:</b>手动修改 关注状态
     * @param approvalEntity
     * @return int
     * @Note
     * <b>Author：</b>hugh.yu
     * <b>Date:</b> 2019年1月16日 下午7:40:33
     */
    int updateFocusState(ApprovalEntity approvalEntity);
    
    /**
     * <b>Description:</b>手动修改 收录状态
     * @param approvalEntity
     * @return int
     * @Note
     * <b>Author：</b>hugh.yu
     * <b>Date:</b> 2019年1月16日 下午7:40:39
     */
    int updateIsInclude(ApprovalEntity approvalEntity);

    // ---------------------------------------------------------------------------
    
    /**
     * <b>Description:</b>单独为不再收录做一个条件查询
     * @param map
     * @return List<ApprovalEntity>
     * @Note
     * <b>Author：</b>hugh.yu
     * <b>Date:</b> 2019年1月16日 下午7:39:06
     */
    List<ApprovalEntity> querylistPage(Map<String, Object> map);
    
    /**
     * <b>Description:</b>查询子表
     * @param approvalId
     * @return List<ApprovalRecordEntity>
     * @Note
     * <b>Author：</b>hugh.yu
     * <b>Date:</b> 2019年1月16日 下午7:40:49
     */
    List<ApprovalRecordEntity> queryApprovalRecordByApprovalId(int approvalId);

    /**
     * <b>Description:</b>新增子表数据(可能多条)
     * @param list
     * @return int
     * @Note
     * <b>Author：</b>hugh.yu
     * <b>Date:</b> 2019年1月16日 下午7:40:55
     */
    int addApprovalRecord(List<ApprovalRecordEntity> list);

    /**
     * <b>Description:</b>删除子表数据
     * @param approvalId
     * @return boolean
     * @Note
     * <b>Author：</b>hugh.yu
     * <b>Date:</b> 2019年1月16日 下午7:41:04
     */
    boolean deleteApprovalRecord(int approvalId);

    /**
     * <b>Description:</b>小弹框的删除
     * @param recordId
     * @return boolean
     * @Note
     * <b>Author：</b>hugh.yu
     * <b>Date:</b> 2019年1月16日 下午7:41:09
     */
    boolean deleterecord(int recordId);

    /**
     * <b>Description:</b>弹框新增子表
     * @param approvalRecordEntity
     * @return int
     * @Note
     * <b>Author：</b>hugh.yu
     * <b>Date:</b> 2019年1月16日 下午7:41:15
     */
    int addApprovalRecordTankuang(ApprovalRecordEntity approvalRecordEntity);

    /**
     * <b>Description:</b>子表直接编辑
     * @param approvalRecordEntity
     * @return boolean
     * @Note
     * <b>Author：</b>hugh.yu
     * <b>Date:</b> 2019年1月16日 下午7:41:20
     */
    boolean updateApprovalRecord(ApprovalRecordEntity approvalRecordEntity);

    /**
     * <b>Description:</b>无参查子表
     * @param approvalId
     * @return List<ApprovalRecordEntity>
     * @Note
     * <b>Author：</b>hugh.yu
     * <b>Date:</b> 2019年1月16日 下午7:41:26
     */
    List<ApprovalRecordEntity> queryApprovalRecord(int approvalId);

    /**
     * <b>Description:</b>小分页
     * @param map
     * @return List<ApprovalRecordEntity>
     * @Note
     * <b>Author：</b>hugh.yu
     * <b>Date:</b> 2019年1月16日 下午7:41:32
     */
    List<ApprovalRecordEntity> querylistpage(Map<String, Object> map);

    /**
     * <b>Description:</b>同时传入page
     * @param map
     * @return List<ApprovalRecordEntity>
     * @Note
     * <b>Author：</b>hugh.yu
     * <b>Date:</b> 2019年1月16日 下午7:41:37
     */
    List<ApprovalRecordEntity> queryApprovalRecordByApprovalIdQueryListPage(Map<String, Object> map);

    /**
     * <b>Description:</b>查子表详情
     * @param map
     * @return List<ApprovalRecordEntity>
     * @Note
     * <b>Author：</b>hugh.yu
     * <b>Date:</b> 2019年1月16日 下午7:41:42
     */
    List<ApprovalRecordEntity> queryZiBiaolistpage(Map<String, Object> map);


    

    



    
    
}
