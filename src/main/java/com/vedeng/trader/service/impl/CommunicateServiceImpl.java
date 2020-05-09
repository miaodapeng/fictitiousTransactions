package com.vedeng.trader.service.impl;

import javax.servlet.http.HttpSession;

import com.common.constants.Contant;
import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.order.model.Quoteorder;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.service.QuoteService;
import com.vedeng.order.service.SaleorderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.DateUtil;
import com.vedeng.trader.dao.CommunicateRecordMapper;
import com.vedeng.trader.model.CommunicateRecord;
import com.vedeng.trader.service.CommunicateService;

import java.io.IOException;

@Service("communicateService")
public class CommunicateServiceImpl extends BaseServiceimpl implements CommunicateService {

	@Autowired
	@Qualifier("communicateRecordMapper")
	private CommunicateRecordMapper communicateRecordMapper;
	//循环依赖

//	@Autowired
//	@Qualifier("saleorderService")
//	protected SaleorderService saleorderService;

	@Autowired
	@Qualifier("quoteService")
	private QuoteService quoteService;

	@Override
	public Integer updateCommunicateDone(CommunicateRecord communicateRecord, HttpSession session) {
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		Long time = DateUtil.sysTimeMillis();
		
		communicateRecord.setModTime(time);
		communicateRecord.setUpdater(user.getUserId());
		return communicateRecordMapper.updateCommunicateDone(communicateRecord);
	}

	@Override
	public Integer addCommunicate(CommunicateRecord communicateRecord, HttpSession session) {

		//新增沟通记录
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		Long time = DateUtil.sysTimeMillis();

		communicateRecord.setAddTime(time);
		communicateRecord.setCreator(user.getUserId());
		communicateRecord.setModTime(time);
		communicateRecord.setUpdater(user.getUserId());

		int insertRows = communicateRecordMapper.insert(communicateRecord);

		//add by brianna 2020/3/11 VDERP-1964-------------start
		//销售订单
		if(communicateRecord.getCommunicateType() == SysOptionConstant.ID_246){
			//修改订单报价(有沟通记录0无 1有)
			Saleorder saleorder = new Saleorder();
			saleorder.setUpdater(user.getUserId());
			saleorder.setModTime(DateUtil.sysTimeMillis());
			saleorder.setHaveCommunicate(1);
			saleorder.setSaleorderId(communicateRecord.getRelatedId());
			//saleorderService.editSaleorderHaveCommunicate(saleorder);
			final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
			};
			String url = httpUrl + "order/saleorder/editsaleorderhavecommunicate.htm";
			try {
				 HttpClientUtils.post(url, saleorder, clientId, clientKey, TypeRef);
			} catch (IOException e) {
				logger.error(Contant.ERROR_MSG, e);
			}
		}

		//报价单
		if (communicateRecord.getCommunicateType() == SysOptionConstant.ID_245) {
			//修改报价主表信息(有沟通记录0无 1有)
			Quoteorder quote = new Quoteorder();
			quote.setUpdater(user.getUserId());
			quote.setModTime(DateUtil.sysTimeMillis());
			quote.setQuoteorderId(communicateRecord.getRelatedId());
			quoteService.editQuoteHaveCommunicate(quote);
		}
		//add by brianna 2020/3/11 VDERP-1964-------------end

		return insertRows;
	}

}
