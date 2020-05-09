package com.vedeng.logistics.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.common.constants.Contant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.authorization.model.User;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.OrderNoDict;
import com.vedeng.logistics.dao.FileDeliveryMapper;
import com.vedeng.logistics.model.Express;
import com.vedeng.logistics.model.FileDelivery;
import com.vedeng.logistics.service.ExpressService;
import com.vedeng.logistics.service.FileDeliveryService;
import com.vedeng.system.model.VerifiesInfo;
import com.vedeng.system.service.VerifiesRecordService;
@Service("fileDeliveryService")
public class FileDeliveryServiceImpl extends BaseServiceimpl implements FileDeliveryService {

	public static Logger logger = LoggerFactory.getLogger(FileDeliveryServiceImpl.class);

	@Autowired
	@Qualifier("fileDeliveryMapper")
	private FileDeliveryMapper fileDeliveryMapper;
	
	@Autowired
	@Qualifier("expressService")
	private ExpressService expressService;
	
	@Autowired
	@Qualifier("verifiesRecordService")
	private VerifiesRecordService verifiesRecordService;
	/**
	 * 获取文件寄送列表
	 */
	@Override
	public List<FileDelivery> getFileDeliveryList(FileDelivery fileDelivery,Express express,List<Integer> creatorIds, Page page) throws Exception{
	    	List<Express> expressList = null;
	    	// 业务类型为文件寄送
//	    	express.setBusinessType(498);
//	    	expressList = expressService.getExpressList(express);
//		List<Integer> relatedIds= new ArrayList<Integer>();
//		//如果有查询结果，把查询结果的ID组成List去FileDelivery中查
//		if(expressList != null){
//		    
//		    for (Express e : expressList) {
//			relatedIds.add(e.getExpressDetail().get(0).getRelatedId());  
//		    }
//		    
//		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("creatorIds", creatorIds);
//		map.put("relatedIds", relatedIds);
		map.put("fileDelivery", fileDelivery);
		map.put("page", page);
		List<FileDelivery> fileDeliverylistpage = fileDeliveryMapper.getFileDeliverylistpage(map);
		List<VerifiesInfo> verifiesInfoList = new ArrayList<>();
		if(!fileDeliverylistpage.isEmpty()){
		    List<Integer> fileDeliveryIds = new ArrayList<Integer>();
		    for(FileDelivery fileDeliverys:fileDeliverylistpage){
			fileDeliveryIds.add(fileDeliverys.getFileDeliveryId());
		    }
		    VerifiesInfo verifiesInfo = new VerifiesInfo();
		    verifiesInfo.setRelateTable("T_FILE_DELIVERY");
		    verifiesInfo.setVerifiesType(615);
		    verifiesInfo.setStatus(0);
		    verifiesInfo.setRelateTableKeys(fileDeliveryIds);
		    verifiesInfoList = verifiesRecordService.getVerifiesList(verifiesInfo);
		}
		for(FileDelivery fileDeliverys:fileDeliverylistpage){
//		    for (Express e : expressList) {
//			if(fileDeliverys.getFileDeliveryId().equals(e.getExpressDetail().get(0).getRelatedId()))
//			{
//			    fileDeliverys.setLogisticsId(e.getLogisticsId());
//			    fileDeliverys.setLogisticsNo(e.getLogisticsNo());
//			    fileDeliverys.setLogisticsName(e.getLogisticsName());
//			    fileDeliverys.setNum(e.getExpressDetail().get(0).getNum());
//			    fileDeliverys.setAmount(e.getExpressDetail().get(0).getAmount());
//			    fileDeliverys.setVerifiesType(e.getVerifiesType());
//			};  
//		    }
		    for(VerifiesInfo vi:verifiesInfoList){
			if(vi.getRelateTableKey().equals(fileDeliverys.getFileDeliveryId())){
			    //审核人
			    if(null != vi.getVerifyUsername()){
				List<String> verifyUsernameList = Arrays.asList(vi.getVerifyUsername().split(","));  
				fileDeliverys.setVerifyUsernameList(verifyUsernameList);
				//fileDeliverys.setVerifyStatus(vi.getStatus());
			    }
			}
		    }
		    
		    //获取字典库的寄送形式  SendType
//		    if(JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_OBJECT+fileDeliverys.getSendType())){
//			JSONObject jsonObject=JSONObject.fromObject(JedisUtils.get(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_OBJECT+fileDeliverys.getSendType()));
//			SysOptionDefinition sod=(SysOptionDefinition) JSONObject.toBean(jsonObject, SysOptionDefinition.class);
//			fileDeliverys.setSendTypeName(sod.getTitle());
//		    }
		    fileDeliverys.setSendTypeName(getSysOptionDefinitionById(fileDeliverys.getSendType()).getTitle());
		    
		  //获取审核状态  VerifyStatus
		    if(fileDeliverys.getVerifyStatus() == 0){
			fileDeliverys.setVerifyStatusName("待审核");
		    }else if(fileDeliverys.getVerifyStatus() == 1){
			fileDeliverys.setVerifyStatusName("审核中");
		    }else if(fileDeliverys.getVerifyStatus() == 2){
			fileDeliverys.setVerifyStatusName("审核通过");
		    }else if(fileDeliverys.getVerifyStatus() == 3){
			fileDeliverys.setVerifyStatusName("审核不通过");
		    }
		    
		  //获取字典库的寄送状态 DeliveryStatus
		    if(fileDeliverys.getDeliveryStatus() == 0){
			fileDeliverys.setDeliveryStatusName("未寄送");
		    }else if(fileDeliverys.getDeliveryStatus() == 1){
			fileDeliverys.setDeliveryStatusName("已寄送");
		    }
		    
		}
		return fileDeliverylistpage;
	}
	
	/**
	 * 保存文件寄送
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	public Integer saveFileDelivery(FileDelivery fileDelivery) throws Exception{
	    Integer i = fileDeliveryMapper.insertSelective(fileDelivery);
	    if(i==1){
		Integer fileDeliveryId = fileDelivery.getFileDeliveryId();
		//生成文件寄送单号
		FileDelivery fileDeliveryExtra = new FileDelivery();
		fileDeliveryExtra.setFileDeliveryId(fileDeliveryId);
		fileDeliveryExtra.setFileDeliveryNo(OrderNoDict.getOrderNum(fileDeliveryId,11));
		fileDeliveryMapper.updateByPrimaryKeySelective(fileDeliveryExtra);
			return fileDeliveryId;
        	} else {
        		return null;
        	}
	}
	/**
	 * 根据主键获取文件寄送信息
	 */
	@Override
	public FileDelivery getFileDeliveryInfoById(Integer fileDeliveryId) throws Exception{
	    return fileDeliveryMapper.selectByPrimaryKey(fileDeliveryId);
	}
	
	/**
	 * 保存文件寄送操作（寄快递）
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	public ResultInfo<?> saveExpress(Express express) throws Exception {
	    	ResultInfo<?> result = new ResultInfo<>();
	    	//更新成功的返回值
	    	int fd = 0;
	    	if(express.getLogisticsId() != null){
	    	    	//如果是快递寄送
        	    	// 调用接口保存快递单信息
        	    	String url = httpUrl + "logistics/express/saveexpressinfo.htm";
        	    	// 定义反序列化 数据格式
        	    	List<Express> expressList = null;
        	    	final TypeReference<ResultInfo<Express>> TypeRef = new TypeReference<ResultInfo<Express>>() {};
                	 try {
                	      result = (ResultInfo<?>) HttpClientUtils.post(url, express,clientId,clientKey, TypeRef);
                	 } catch (IOException e) {
                	 	logger.error(Contant.ERROR_MSG, e);
                	 }
	    	}else{
	    	    //如果不是快递寄送，就不走接口直接插入
	    	    result.setCode(0);
	    	}
	    	//如果接口成功调取更新本地数据
	    	if(result.getCode() == 0){
    	    		FileDelivery fileDelivery = new FileDelivery();
        	 	fileDelivery.setDeliveryStatus(1);
        	 	fileDelivery.setDeliveryTime(DateUtil.sysTimeMillis());
        	 	fileDelivery.setDeliveryUserId(express.getCreator());
        	 	fileDelivery.setUpdater(express.getCreator());
        	 	fileDelivery.setModTime(DateUtil.sysTimeMillis());
        	 	fileDelivery.setFileDeliveryId(express.getRelatedId());
        	 	fd = fileDeliveryMapper.updateByPrimaryKeySelective(fileDelivery);
	    	}
	    	//如果本地更新成功返回result
	    	ResultInfo<?> resultInfo = new ResultInfo<>();
	    	if(fd == 1){
	    	    resultInfo.setCode(0);
	    	    resultInfo.setMessage("操作成功");
	    	}else{
	    	    resultInfo.setCode(-1);
	    	    resultInfo.setMessage("操作失败");
	    	}
    	 	return resultInfo;
	}
	/**
	 * 更新文件寄送
	 */
	@Override
	public int updateFileDelivery(FileDelivery fileDelivery) {
	    return fileDeliveryMapper.updateByPrimaryKeySelective(fileDelivery);
	}

	@Override
	public List<FileDelivery> getFileDeliveryListByUName(User user) {
		 return fileDeliveryMapper.getFileDeliveryListByUName(user);
	}
	
	@Override
	public Integer updateDeliveryCloseStatus( FileDelivery fileDelivery ) {
		fileDelivery.setModTime(System.currentTimeMillis());
		//修改状态
		fileDelivery.setIsClosed(1);
		return fileDeliveryMapper.updateDeliveryCloseStatus(fileDelivery);
	}
	
}
