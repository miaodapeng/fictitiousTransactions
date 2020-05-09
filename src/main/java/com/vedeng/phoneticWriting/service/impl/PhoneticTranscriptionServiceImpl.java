package com.vedeng.phoneticWriting.service.impl;

import com.vedeng.authorization.model.User;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.EmptyUtils;
import com.vedeng.phoneticWriting.dao.CommentMapper;
import com.vedeng.phoneticWriting.dao.ModificationRecordMapper;
import com.vedeng.phoneticWriting.dao.PhoneticWritingMapper;
import com.vedeng.phoneticWriting.model.Comment;
import com.vedeng.phoneticWriting.model.ModificationRecord;
import com.vedeng.phoneticWriting.model.PhoneticWriting;
import com.vedeng.phoneticWriting.model.vo.ModificationRecordVo;
import com.vedeng.phoneticWriting.service.PhoneticTranscriptionService;
import com.vedeng.trader.dao.CommunicateRecordMapper;
import com.vedeng.trader.model.CommunicateRecord;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("phoneticTranscriptionService")
public class PhoneticTranscriptionServiceImpl extends BaseServiceimpl implements PhoneticTranscriptionService {

    @Autowired
    @Qualifier("commentMapper")
    private CommentMapper commentMapper;

    @Autowired
    @Qualifier("modificationRecordMapper")
    private ModificationRecordMapper modificationRecordMapper;

    @Autowired
    @Qualifier("phoneticWritingMapper")
    private PhoneticWritingMapper phoneticWritingMapper;

    @Autowired
    @Qualifier("communicateRecordMapper")
    private CommunicateRecordMapper communicateRecordMapper;

    @Override
    public int addComments(Comment comment,User curr_user) {
        comment.setAddTime(System.currentTimeMillis());
        comment.setCreator(curr_user.getUserId());
        comment.setModTime(System.currentTimeMillis());
        comment.setUpdater(curr_user.getUserId());
        int i = 0;
        // 新增点评
        i = commentMapper.insertSelective(comment);
        if(i > 0 ){
            // 将对应沟通记录设置为已点评
            CommunicateRecord communicateRecord = new CommunicateRecord();
            communicateRecord.setCommunicateRecordId(comment.getRelatedId());
            communicateRecord.setIsComment(1);
            communicateRecordMapper.update(communicateRecord);
        }
        return i;
    }

    @Override
    public int updateModificationRecord(ModificationRecord modificationRecord, User curr_user) throws Exception{

        int i = 0;
        if(modificationRecord.getIsDel()!=null && modificationRecord.getIsDel() == 1){
            // 删除争议规则
            modificationRecord.setModTime(System.currentTimeMillis());
            modificationRecord.setUpdater(curr_user.getUserId());
            i = modificationRecordMapper.updateByPrimaryKeySelective(modificationRecord);
        }else {
            // 新增争议规则
            modificationRecord.setAddTime(System.currentTimeMillis());
            modificationRecord.setCreator(curr_user.getUserId());
            i = modificationRecordMapper.insertSelective(modificationRecord);
        }
        int j = 0 ;
        /*if(i >0){
            // 查询所有的替换规则
            CommunicateRecord communicateRecord = new CommunicateRecord();
            communicateRecord.setCommunicateRecordId(modificationRecord.getRelatedId());
            // 修改文还原
            PhoneticWriting phoneticWriting = phoneticWritingMapper.getPhoneticWriting(communicateRecord.getCommunicateRecordId());
            phoneticWriting.setUpdatedContent(phoneticWriting.getOriginalContent());
            phoneticWritingMapper.updateByPrimaryKeySelective(phoneticWriting);

            List<ModificationRecord> modificationRecordList = modificationRecordMapper.getMrInfoList(communicateRecord);
            int n = 0;
            if(CollectionUtils.isNotEmpty(modificationRecordList)){
               *//* // 替换录音文件争议内容
                modificationRecord.setAddTime(null);
                modificationRecord.setCreator(null);*//*
                // 遍历所有规则进行替换
                for(int k=0;k < modificationRecordList.size();k++){
                    // 判断要修改的争议内容存不存在
                    Integer index = phoneticWritingMapper.getPhoneticWritingIndex(modificationRecordList.get(k));
                    if(index !=null && !index.equals(0)){
                        modificationRecordList.get(k).setModifyContent("<span class='active'>"+modificationRecordList.get(k).getModifyContent()+"</span>");
                        modificationRecordList.get(k).setPhoneticWritingId(phoneticWriting.getPhoneticWritingId());
                            n = phoneticWritingMapper.updatePhoneticWritingInfo(modificationRecordList.get(k));
                            j++;
                    }
                }
            }
            if(n==0){
                // 将修改文清空
                PhoneticWriting pw = phoneticWritingMapper.getPhoneticWriting(communicateRecord.getCommunicateRecordId());
                phoneticWriting.setUpdatedContent("");
                phoneticWritingMapper.updateByPrimaryKeySelective(phoneticWriting);
            }
        }*/
        return i;
    }

    @Override
    @Transactional
    public List<ModificationRecord> processingRecords(CommunicateRecord communicateRecord) throws Exception {

        // 将修改文还原
        PhoneticWriting phoneticWriting = phoneticWritingMapper.getPhoneticWriting(communicateRecord.getCommunicateRecordId());
        phoneticWriting.setUpdatedContent(phoneticWriting.getOriginalContent());
        phoneticWritingMapper.updateByPrimaryKeySelective(phoneticWriting);

        // 查询此录音下所有的争议规则
        List<ModificationRecord> mrList = new ArrayList<>();

        // 保存当前文本的所有的规则
        List<ModificationRecord> mrUseList = new ArrayList<>();
        mrList = modificationRecordMapper.getMrInfoList(communicateRecord);
        int j = 0;
        if (CollectionUtils.isNotEmpty(mrList)){

            // 遍历所有规则进行替换
            for(int i=0;i < mrList.size();i++){

                // 判断要修改的争议内容存不存在
                mrList.get(i).setModifyContent("<span class='active'>"+mrList.get(i).getModifyContent()+"</span>");
                mrList.get(i).setPhoneticWritingId(phoneticWriting.getPhoneticWritingId());
                Integer index = phoneticWritingMapper.getPhoneticWritingIndex(mrList.get(i));

                // 属于当前沟通记录
                if(mrList.get(i).getRelatedId().equals(communicateRecord.getCommunicateRecordId())){
                    j = phoneticWritingMapper.updatePhoneticWritingInfo(mrList.get(i));
                    mrUseList.add(mrList.get(i));
                }else if(index !=null && !index.equals(0)){// 非当前沟通记录
                    j = phoneticWritingMapper.updatePhoneticWritingInfo(mrList.get(i));
                    // 进行了修改则保存修改规则
                    if(j > 0 ){
                        mrUseList.add(mrList.get(i));
                    }
                }
            }
        }
        if(j==0){
            // 将修改文清空
            PhoneticWriting pw = phoneticWritingMapper.getPhoneticWriting(communicateRecord.getCommunicateRecordId());
            phoneticWriting.setUpdatedContent("");
            phoneticWritingMapper.updateByPrimaryKeySelective(phoneticWriting);
        }
        return mrUseList;
    }

    @Override
    public int updateAllModificationRecord(ModificationRecord modificationRecord, User curr_user) {

        // 将当前的单页规则的外键id更新为0
        //modificationRecord.setModTime(System.currentTimeMillis());
        //modificationRecord.setUpdater(curr_user.getUserId());
        modificationRecord.setAddTime(System.currentTimeMillis());
        //modificationRecord.setRelatedId(0);
        int i = modificationRecordMapper.updateByPrimaryKeySelective(modificationRecord);
        return i;
    }

    /**
     * 功能描述: 查询所有争议记录
     * @param: [map]
     * @return: java.util.List<com.vedeng.phoneticWriting.model.vo.ModificationRecordVo>
     * @auther: barry.xu
     * @date: 2019/4/29 14:48
     */
    @Override
    public List<ModificationRecordVo> getModificationRecordList(Map<String,Object> map) {

        return modificationRecordMapper.getModificationRecordListPage(map);
    }

    /**
     * 功能描述: 查询是否有相同的争议内容
     * @param: [controversialContent]
     * @return: int
     * @auther: barry.xu
     * @date: 2019/4/29 14:50
     */
    @Override
    public Boolean getTheSameRecord(String controversialContent) {

        //入参判断
        if (EmptyUtils.isNotBlank(controversialContent)){
            int count = modificationRecordMapper.getTheSameRecord(controversialContent);

            if (count == 0){
                return false;
            }
        }

        return true;
    }

    /**
     * 功能描述: 新增修正记录
     * @param: [record]
     * @return: java.lang.Boolean
     * @auther: barry.xu
     * @date: 2019/4/29 15:11
     */
    @Override
    public Boolean insertNewRecord(ModificationRecord record) {

        int status = modificationRecordMapper.insertSelective(record);

        if (status < 0 ){
            return false;
        }
        return true;
    }

    @Override
    public Boolean updateRecord(ModificationRecord record) {

        int status = modificationRecordMapper.updateByPrimaryKeySelective(record);

        if (status < 0 ){
            return false;
        }
        return true;
    }

    @Override
    public ModificationRecord getRecord(Integer id) {
        return modificationRecordMapper.selectByPrimaryKey(id);
    }

    @Override
    public Boolean delRecord(Integer id) {
        int status = modificationRecordMapper.delRecord(id);
        if (status < 0 ){
            return false;
        }
        return true;
    }

    @Override
    public boolean getTheSameRecordById(ModificationRecord modificationRecord) {

        //入参判断
        if (EmptyUtils.isNotBlank(modificationRecord.getControversialContent())){
            int count = modificationRecordMapper.getTheSameRecordById(modificationRecord);

            if (count == 0){
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean getTheSameRecordByNotId(ModificationRecord modificationRecord) {

        //入参判断
        if (EmptyUtils.isNotBlank(modificationRecord.getControversialContent())){
            int count = modificationRecordMapper.getTheSameRecordByNotId(modificationRecord);

            if (count == 0){
                return false;
            }
        }

        return true;
    }

}
