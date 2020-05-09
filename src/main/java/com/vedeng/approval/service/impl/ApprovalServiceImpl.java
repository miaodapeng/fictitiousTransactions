package com.vedeng.approval.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vedeng.approval.dao.ApprovalMapper;
import com.vedeng.approval.model.ApprovalEntity;
import com.vedeng.approval.model.ApprovalRecordEntity;
import com.vedeng.approval.service.ApprovalService;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;

@Service("approvalService")

public class ApprovalServiceImpl implements ApprovalService{

    @Autowired
    private ApprovalMapper approvalMapper;
    
    //查询列表 单独为不再收录做一个条件查询
    @Override
    public List<ApprovalEntity> querylistPage(ApprovalEntity approvalEntity, Page page) {

        Map<String, Object> map = new HashMap<>();
        map.put("approvalEntity", approvalEntity);
        map.put("page", page);
        return approvalMapper.querylistPage(map);
    }
    
    //查询列表
    @Override
    public List<ApprovalEntity> queryListPage(ApprovalEntity approvalEntity, Page page) {

        Map<String, Object> map = new HashMap<>();
        map.put("approvalEntity", approvalEntity);
        map.put("page", page);
        return approvalMapper.queryListPage(map);
    }

    //新增主表
    @Override
    public int insertApproval(ApprovalEntity approvalEntity) {

        approvalMapper.insertApproval(approvalEntity);
        Integer approvalId = approvalEntity.getApprovalId();
        return approvalId;
    }
    
    
    //修改
    @Override
    public boolean updateApproval(ApprovalEntity approvalEntity) {
        return approvalMapper.updateApproval(approvalEntity);
    }
    

    //根据id查询一条数据
    @Override
    public ApprovalEntity queryByApprovalId(@Param("approvalId")Integer approvalId) {
        return approvalMapper.queryByApprovalId(approvalId);
    }
    

    //删除
    @Override
    public int deleteByApprovalId(ApprovalEntity approvalEntity) {
        
        return approvalMapper.deleteByApprovalId(approvalEntity.getApprovalId());
    }
    
    
    //批量删除
    @Override
    public ResultInfo<?> delApprovalBatch(List<Integer> idList) {
        
        ResultInfo<?> result = new ResultInfo<>();
        
        int success = 0;
        
//        for (int i = 0; i < idList.size(); i++) {
//            Integer approvalId = idList.get(i);
//            success = approvalMapper.delApprovalBatch(approvalId); //遍历删除
//        }
        
        //改用此方法,降低数据库压力,待检查
        success = approvalMapper.delApprovalBatchNew(idList); //遍历删除
        
        if (success > 0) {
            result.setCode(0);
            result.setMessage("操作成功");
        } else {
            result.setCode(-1);
            result.setMessage("操作失败");
        }
            return result;
    }
    
    
        //手动修改关注状态
        @SuppressWarnings("rawtypes")
        @Override
        public ResultInfo<?> updateFocusState(ApprovalEntity approvalEntity) {
        ResultInfo result = new ResultInfo();
       
            int status = approvalMapper.updateFocusState(approvalEntity);
            if (status == 1){
                result.setCode(0);
                result.setMessage("操作成功");
            }
                return result;
        }
        
        //手动修改收录状态
        @SuppressWarnings("rawtypes")
        @Override
        public ResultInfo<?> updateIsInclude(ApprovalEntity approvalEntity) {
        ResultInfo result = new ResultInfo();
       
            int status = approvalMapper.updateIsInclude(approvalEntity);
            if (status == 1){
                result.setCode(0);
                result.setMessage("操作成功");
            }
                return result;
        }
        
        //查询子表
        @Override
        public List<ApprovalRecordEntity> queryApprovalRecordByApprovalId(int approvalId) {

            return approvalMapper.queryApprovalRecordByApprovalId(approvalId);
        }

        //新增子表数据(可能多条)
        @Override
        public int addApprovalRecord(List<ApprovalRecordEntity> list) {
            
            return approvalMapper.addApprovalRecord(list);
        }

        
        //根据id删除子表数据
        @Override
        public boolean deleteApprovalRecord(int approvalId) {

            return approvalMapper.deleteApprovalRecord(approvalId);
        }

        //小弹框--彻底删除
        @Override
        public boolean deleterecord(int recordId) {
            
            return approvalMapper.deleterecord(recordId);
        }

        //弹框新增子表
        @Override
        public int addApprovalRecordTankuang(ApprovalRecordEntity approvalRecordEntity) {

            return approvalMapper.addApprovalRecordTankuang(approvalRecordEntity);
        }

        //子表直接编辑
        @Override
        public boolean updateApprovalRecord(ApprovalRecordEntity approvalRecordEntity) {

            return approvalMapper.updateApprovalRecord(approvalRecordEntity);
        }

        //无参查子表
        @Override
        public List<ApprovalRecordEntity> queryApprovalRecord(int approvalId) {

            return approvalMapper.queryApprovalRecord(approvalId);
        }

        //小分页
        @Override
        public List<ApprovalRecordEntity> querylistpage(Integer recordId, Page page) {

            Map<String, Object> map = new HashMap<>();
            map.put("recordId", recordId);
            map.put("page", page);
            return approvalMapper.querylistpage(map);
            
        }

        //同时传入page
        @Override
        public List<ApprovalRecordEntity> queryApprovalRecordByApprovalIdQueryListPage(Integer approvalId, Page page) {
           
            Map<String, Object> map = new HashMap<>();
            map.put("approvalId", approvalId);
            map.put("page", page);
            return approvalMapper.queryApprovalRecordByApprovalIdQueryListPage(map);
        }

        //查子表详情
        @Override
        public List<ApprovalRecordEntity> queryZiBiaolistpage(int approvalId, Page page) {

            Map<String, Object> map = new HashMap<>();
            map.put("approvalId", approvalId);
            map.put("page", page);
            return approvalMapper.queryZiBiaolistpage(map);
        }



}







