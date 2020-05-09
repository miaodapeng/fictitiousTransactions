package com.vedeng.approval.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.vedeng.approval.model.ApprovalEntity;
import com.vedeng.approval.model.ApprovalRecordEntity;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;

public interface ApprovalService {

    /**
     * <b>Description:</b>查询列表 单独为不再收录做一个查询
     * @param approvalEntity
     * @param page
     * @return List<ApprovalEntity>
     * @Note
     * <b>Author：</b>hugh.yu
     * <b>Date:</b> 2019年1月16日 下午7:36:10
     */
    List<ApprovalEntity> querylistPage(ApprovalEntity approvalEntity, Page page);
    
    /**
     * <b>Description:</b>查询列表
     * @param approvalEntity
     * @param page
     * @return List<ApprovalEntity>
     * @Note
     * <b>Author：</b>hugh.yu
     * <b>Date:</b> 2019年1月16日 下午7:36:24
     */
    List<ApprovalEntity> queryListPage(ApprovalEntity approvalEntity, Page page);
    
    /**
     * <b>Description:</b>根据Id查询一条
     * @param approvalId
     * @return ApprovalEntity
     * @Note
     * <b>Author：</b>hugh.yu
     * <b>Date:</b> 2019年1月16日 下午7:36:31
     */
    ApprovalEntity queryByApprovalId(Integer approvalId);
    
    /**
     * <b>Description:</b>新增
     * @param approvalEntity
     * @return int
     * @Note
     * <b>Author：</b>hugh.yu
     * <b>Date:</b> 2019年1月16日 下午7:36:41
     */
    int insertApproval(ApprovalEntity approvalEntity);

    /**
     * <b>Description:</b>修改
     * @param approvalEntity
     * @return boolean
     * @Note
     * <b>Author：</b>hugh.yu
     * <b>Date:</b> 2019年1月16日 下午7:36:54
     */
    boolean updateApproval(ApprovalEntity approvalEntity);

    /**
     * <b>Description:</b>根据Id删除一条主表
     * @param approvalEntity
     * @return int
     * @Note
     * <b>Author：</b>hugh.yu
     * <b>Date:</b> 2019年1月16日 下午7:37:05
     */
    int deleteByApprovalId(ApprovalEntity approvalEntity);

    /**
     * <b>Description:</b>批量删除
     * @param idList
     * @return ResultInfo<?>
     * @Note
     * <b>Author：</b>hugh.yu
     * <b>Date:</b> 2019年1月16日 下午7:37:11
     */
    ResultInfo<?> delApprovalBatch(List<Integer> idList);
    
    /**
     * <b>Description:</b>手动修改 关注状态
     * @param approvalEntity
     * @return ResultInfo<?>
     * @Note
     * <b>Author：</b>hugh.yu
     * <b>Date:</b> 2019年1月16日 下午7:37:25
     */
    ResultInfo<?> updateFocusState(ApprovalEntity approvalEntity);
    
    /**
     * <b>Description:</b>手动修改 收录状态
     * @param approvalEntity
     * @return ResultInfo<?>
     * @Note
     * <b>Author：</b>hugh.yu
     * <b>Date:</b> 2019年1月16日 下午7:37:32
     */
    ResultInfo<?> updateIsInclude(ApprovalEntity approvalEntity);

     // -----------------------------------------------------------------------------------
    
    /**
     * <b>Description:</b>查询子表
     * @param approvalId
     * @return List<ApprovalRecordEntity>
     * @Note
     * <b>Author：</b>hugh.yu
     * <b>Date:</b> 2019年1月16日 下午7:37:43
     */
    List<ApprovalRecordEntity> queryApprovalRecordByApprovalId(int approvalId);

    /**
     * <b>Description:</b>新增子表(多条)
     * @param list
     * @return int
     * @Note
     * <b>Author：</b>hugh.yu
     * <b>Date:</b> 2019年1月16日 下午7:37:48
     */
    int addApprovalRecord(List<ApprovalRecordEntity> list);

    /**
     * <b>Description:</b>根据id删除子表
     * @param approvalId
     * @return boolean
     * @Note
     * <b>Author：</b>hugh.yu
     * <b>Date:</b> 2019年1月16日 下午7:37:56
     */
    boolean deleteApprovalRecord(int approvalId);

    /**
     * <b>Description:</b>先删 (小弹框--彻底删除)
     * @param recordId
     * @return boolean
     * @Note
     * <b>Author：</b>hugh.yu
     * <b>Date:</b> 2019年1月16日 下午7:38:02
     */
    boolean deleterecord(int recordId);

    /**
     * <b>Description:</b>小弹框新增子表
     * @param approvalRecordEntity
     * @return int
     * @Note
     * <b>Author：</b>hugh.yu
     * <b>Date:</b> 2019年1月16日 下午7:38:07
     */
    int addApprovalRecordTankuang(ApprovalRecordEntity approvalRecordEntity);

    /**
     * <b>Description:</b>子表直接编辑
     * @param approvalRecordEntity
     * @return boolean
     * @Note
     * <b>Author：</b>hugh.yu
     * <b>Date:</b> 2019年1月16日 下午7:38:15
     */
    boolean updateApprovalRecord(ApprovalRecordEntity approvalRecordEntity);

    /**
     * <b>Description:</b>无参查子表
     * @param approvalId
     * @return List<ApprovalRecordEntity>
     * @Note
     * <b>Author：</b>hugh.yu
     * <b>Date:</b> 2019年1月16日 下午7:38:20
     */
    List<ApprovalRecordEntity> queryApprovalRecord(int approvalId);

    /**
     * <b>Description:</b>小分页
     * @param recordId
     * @param page
     * @return List<ApprovalRecordEntity>
     * @Note
     * <b>Author：</b>hugh.yu
     * <b>Date:</b> 2019年1月16日 下午7:38:25
     */
    List<ApprovalRecordEntity> querylistpage(Integer recordId, Page page);

    /**
     * <b>Description:</b>同时传入page
     * @param approvalId
     * @param page
     * @return List<ApprovalRecordEntity>
     * @Note
     * <b>Author：</b>hugh.yu
     * <b>Date:</b> 2019年1月16日 下午7:38:33
     */
    List<ApprovalRecordEntity> queryApprovalRecordByApprovalIdQueryListPage(Integer approvalId, Page page);

    /**
     * <b>Description:</b>查子表详情
     * @param approvalId
     * @param page
     * @return List<ApprovalRecordEntity>
     * @Note
     * <b>Author：</b>hugh.yu
     * <b>Date:</b> 2019年1月16日 下午7:38:41
     */
    List<ApprovalRecordEntity> queryZiBiaolistpage(int approvalId, Page page);

    
}
