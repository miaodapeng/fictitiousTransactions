package com.vedeng.system.dao;

import java.util.List;
import java.util.Map;
import com.vedeng.system.model.Attachment;
import org.apache.ibatis.annotations.Param;

import javax.inject.Named;

@Named
public interface AttachmentMapper {
    int deleteByPrimaryKey(Integer attachmentId);

    int insert(Attachment record);

    int insertSelective(Attachment record);

    Attachment selectByPrimaryKey(Integer attachmentId);

    int updateByPrimaryKeySelective(Attachment record);

    int updateByPrimaryKey(Attachment record);

    /**
     * 附件信息
     * <p>Title: getAttachmentsList</p>  
     * <p>Description: </p>  
     * @param paramMap
     * @return  
     * @author Bill
     * @date 2019年4月1日
     */
	List<Attachment> getAttachmentsList(Map<String, Object> paramMap);

	/**
	 * 根据参数删除
	 * <p>Title: deleteByParam</p>  
	 * <p>Description: </p>  
	 * @param attachmentMap
	 * @return  
	 * @author Bill
	 * @date 2019年4月2日
	 */
	Integer deleteByParam(Map<String, Object> attachmentMap);

	/**
	 * 添加注册证
	 * <p>Title: insertAttachmentList</p>  
	 * <p>Description: </p>  
	 * @param attachmentMap
	 * @return  
	 * @author Bill
	 * @date 2019年4月2日
	 */
	Integer insertAttachmentList(Map<String, Object> attachmentMap);


	List<Attachment> getAttachmentsByProductCompanyId(@Param("productCompanyId") Integer productCompanyId,@Param("attachmentFunction") List<Integer> attachmentFunction);
	/**
	*获取当前商品信息
	* @Author:strange
	* @Date:17:31 2020-03-20
	*/
	Attachment getSkuBarcodeByGoodsId(Integer goodsId);
}