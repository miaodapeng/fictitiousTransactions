package com.vedeng.phoneticWriting.service;

import com.vedeng.authorization.model.User;
import com.vedeng.common.service.BaseService;
import com.vedeng.phoneticWriting.model.Comment;
import com.vedeng.phoneticWriting.model.ModificationRecord;
import com.vedeng.phoneticWriting.model.vo.ModificationRecordVo;
import com.vedeng.trader.model.CommunicateRecord;

import java.util.List;
import java.util.Map;


public interface PhoneticTranscriptionService extends BaseService {
	/** 
	* @Description: 新增点评 
	* @Param: [comment, curr_user] 
	* @return: int 
	* @Author: scott.zhu 
	* @Date: 2019/4/25 
	*/
	int addComments(Comment comment,User curr_user);
    /** 
    * @Description: 新增争议内容、替换录音文件内容
    * @Param: [modificationRecord, curr_user] 
    * @return: int 
    * @Author: scott.zhu 
    * @Date: 2019/4/25 
    */
	int updateModificationRecord(ModificationRecord modificationRecord, User curr_user)throws Exception;
    /** 
    * @Description: 查询规则并处理
    * @Param: [communicateRecord] 
    * @return: void 
    * @Author: scott.zhu 
    * @Date: 2019/4/28 
    */
    List<ModificationRecord> processingRecords(CommunicateRecord communicateRecord)throws Exception;
    /**
    * @Description: 修改为全局的规则
    * @Param: [modificationRecord, curr_user]
    * @return: int
    * @Author: scott.zhu
    * @Date: 2019/4/28
    */
	int updateAllModificationRecord(ModificationRecord modificationRecord, User curr_user);

    List<ModificationRecordVo> getModificationRecordList(Map<String,Object> map)throws Exception;

    Boolean getTheSameRecord(String controversialContent);

	Boolean insertNewRecord(ModificationRecord record);

	Boolean updateRecord(ModificationRecord record);

	ModificationRecord getRecord(Integer id);

	Boolean delRecord(Integer id);

    boolean getTheSameRecordById(ModificationRecord modificationRecord);
    /** 
    * @Description: 转为全局争议，排除当前页
    * @Param: [controversialContent] 
    * @return: boolean 
    * @Author: scott.zhu 
    * @Date: 2019/5/6
	 * @param modificationRecord
    */
    boolean getTheSameRecordByNotId(ModificationRecord modificationRecord);
}
