package com.report.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.common.constants.Contant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.report.dao.CommonReportMapper;
import com.report.model.export.CapitalBillExport;
import com.report.model.export.CapitalBillExportDetail;
import com.report.model.export.InvoiceExportDetail;
import com.report.model.export.SaleOrderExportCw;
import com.report.model.export.SaleOrderExportDetail;
import com.report.service.CommonReportService;
import com.report.service.FinanceReportService;
import com.vedeng.authorization.model.Organization;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.DateUtil;
import com.vedeng.finance.model.AccountPeriod;
import com.vedeng.finance.model.BankBill;
import com.vedeng.finance.model.CapitalBill;
import com.vedeng.finance.model.Invoice;
import com.vedeng.finance.model.InvoiceApply;
import com.vedeng.finance.model.PayApply;
import com.vedeng.finance.model.TraderAccountPeriodApply;
import com.vedeng.order.model.Saleorder;


@Service("financeReportService")
public class FinanceReportServiceImpl extends BaseServiceimpl implements FinanceReportService {
	public static Logger logger = LoggerFactory.getLogger(FinanceReportServiceImpl.class);

	@Autowired
	@Qualifier("commonReportMapper")
	private CommonReportMapper commonReportMapper;
	
	@Autowired
	@Qualifier("commonReportService")
	private CommonReportService commonReportService;
	

	@Override
	public List<AccountPeriod> exportCustomerAccount(AccountPeriod ap, Page page) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<AccountPeriod>>> TypeRef = new TypeReference<ResultInfo<List<AccountPeriod>>>() {};
		String url=httpUrl + "report/finance/exportcustomeraccount.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, ap, clientId, clientKey, TypeRef, page);
			if(result != null && result.getCode() == 0){
				List<AccountPeriod> list = (List<AccountPeriod>) result.getData();
				if(null != list && !list.isEmpty()){
					List<Integer> traderIdList = new ArrayList<>();
					for(int i=0;i<list.size();i++){
						traderIdList.add(list.get(i).getTraderId());
					}
					if(traderIdList.size()>0){
						traderIdList = new ArrayList<Integer>(new HashSet<Integer>(traderIdList));
						List<User> userList = commonReportMapper.getUserByTraderIdList(traderIdList,ErpConst.ONE);//1客户，2供应商
						if(null != userList && !userList.isEmpty()){
							for(int i=0;i<list.size();i++){
								for(int j=0;j<userList.size();j++){
									if(list.get(i).getTraderId().equals(userList.get(j).getTraderId())){
										list.get(i).setTraderUserNm(userList.get(j).getUsername());
									}
								}
								if(list.get(i).getAmount()!=null){
									//账期归还金额大于等于账期支付金额
									if(list.get(i).getAmount().compareTo(list.get(i).getAccountPeriodAmount()) != -1){
										//还未发货（发票日期为空，截止日期也为空），账期已归还
										if((list.get(i).getAccountEndTime()==null || list.get(i).getAccountEndTime() == 0)
												&& list.get(i).getTraderTime()!=null && list.get(i).getTraderTime()!=0){
											list.get(i).setOverdueState(0);//未逾期
										}else if(list.get(i).getAccountEndTime() >= list.get(i).getTraderTime()){//账期截止日期大于等于归还日期
											list.get(i).setOverdueState(0);//未逾期
										}else{//账期截止日期小于归还日期
											list.get(i).setOverdueState(1);//已逾期
										}
									}else{//账期归还金额小于账期支付金额
										//还未发货（发票日期为空，截止日期也为空），账期已归还
										if((list.get(i).getAccountEndTime()==null || list.get(i).getAccountEndTime() == 0)
												&& list.get(i).getTraderTime()!=null && list.get(i).getTraderTime()!=0){
											list.get(i).setOverdueState(0);//未逾期
										}else if(list.get(i).getAccountEndTime() >= list.get(i).getTraderTime()){//账期截止日期大于归还日期
											list.get(i).setOverdueState(0);//未逾期
										}else{//账期截止日期小于归还日期
											list.get(i).setOverdueState(1);//已逾期
										}
									}
								}else{
									if(list.get(i).getDeliveryTime() == null || list.get(i).getDeliveryTime() == 0){
										list.get(i).setTradertimeStr(null);
										list.get(i).setOverdueState(-1);//无
									}else{
										if(list.get(i).getAccountEndTime() > DateUtil.gainNowDate()){//账期截止日期大于归还日期
											list.get(i).setTradertimeStr(null);
											list.get(i).setOverdueState(-1);//无
										}else{//账期截止日期小于归还日期
											list.get(i).setTradertimeStr(null);
											list.get(i).setOverdueState(1);//已逾期
										}
									}
								}
							}
						}
					}
				}
				
				//分页多次查询
				Page pageInfo = (Page) result.getPage();
				if(pageInfo.getPageNo() == 1){
					Integer total = pageInfo.getTotalPage();
					for (int i = 2; i <= total; i++) {
						pageInfo.setPageNo(i);
						List<AccountPeriod> result_list = this.exportCustomerAccount(ap, pageInfo);
						list.addAll(result_list);
					}
				}
				return list;
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return null;
	}

	@Override
	public List<AccountPeriod> exportSupplierAccount(AccountPeriod ap, Page page) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<AccountPeriod>>> TypeRef = new TypeReference<ResultInfo<List<AccountPeriod>>>() {};
		String url=httpUrl + "report/finance/exportsupplieraccount.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, ap, clientId, clientKey, TypeRef, page);
			if(result != null && result.getCode() == 0){
				List<AccountPeriod> list = (List<AccountPeriod>) result.getData();
				if(null != list && !list.isEmpty()){
					List<Integer> traderIdList = new ArrayList<>();
					for(int i=0;i<list.size();i++){
						traderIdList.add(list.get(i).getTraderId());
					}
					if(traderIdList.size()>0){
						traderIdList = new ArrayList<Integer>(new HashSet<Integer>(traderIdList));
						List<User> userList = commonReportMapper.getUserByTraderIdList(traderIdList,ErpConst.TWO);//1客户，2供应商
						if(null != userList && !userList.isEmpty()){
							for(int i=0;i<list.size();i++){
								for(int j=0;j<userList.size();j++){
									if(list.get(i).getTraderId().equals(userList.get(j).getTraderId())){
										list.get(i).setTraderUserNm(userList.get(j).getUsername());
									}
								}
								if(list.get(i).getAmount()!=null){
									//账期归还金额大于等于账期支付金额
									if(list.get(i).getAmount().compareTo(list.get(i).getAccountPeriodAmount()) != -1){
										//还未发货（发票日期为空，截止日期也为空），账期已归还
										if((list.get(i).getAccountEndTime()==null || list.get(i).getAccountEndTime() == 0)
												&& list.get(i).getTraderTime()!=null && list.get(i).getTraderTime()!=0){
											list.get(i).setOverdueState(0);//未逾期
										}else if(list.get(i).getAccountEndTime() >= list.get(i).getTraderTime()){//账期截止日期大于等于归还日期
											list.get(i).setOverdueState(0);//未逾期
										}else{//账期截止日期小于归还日期
											list.get(i).setOverdueState(1);//已逾期
										}
									}else{//账期归还金额小于账期支付金额
										//还未发货（发票日期为空，截止日期也为空），账期已归还
										if((list.get(i).getAccountEndTime()==null || list.get(i).getAccountEndTime() == 0)
												&& list.get(i).getTraderTime()!=null && list.get(i).getTraderTime()!=0){
											list.get(i).setOverdueState(0);//未逾期
										}else if(list.get(i).getAccountEndTime() >= list.get(i).getTraderTime()){//账期截止日期大于归还日期
											list.get(i).setOverdueState(0);//未逾期
										}else{//账期截止日期小于归还日期
											list.get(i).setOverdueState(1);//已逾期
										}
									}
								}else{
									if(list.get(i).getDeliveryTime() == null || list.get(i).getDeliveryTime() == 0){
										list.get(i).setTradertimeStr(null);
										list.get(i).setOverdueState(-1);//无
									}else{
										if(list.get(i).getAccountEndTime() > DateUtil.gainNowDate()){//账期截止日期大于归还日期
											list.get(i).setTradertimeStr(null);
											list.get(i).setOverdueState(-1);//无
										}else{//账期截止日期小于归还日期
											list.get(i).setTradertimeStr(null);
											list.get(i).setOverdueState(1);//已逾期
										}
									}
								}
							}
						}
					}
				}
				
				//分页多次查询
				Page pageInfo = (Page) result.getPage();
				if(pageInfo.getPageNo() == 1){
					Integer total = pageInfo.getTotalPage();
					for (int i = 2; i <= total; i++) {
						pageInfo.setPageNo(i);
						List<AccountPeriod> result_list = this.exportSupplierAccount(ap, pageInfo);
						list.addAll(result_list);
					}
				}
				return list;
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return null;
	}

	@Override
	public List<Invoice> exportIncomeInvoiceList(Invoice invoice, Page page) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<Invoice>>> TypeRef = new TypeReference<ResultInfo<List<Invoice>>>() {};
		String url=httpUrl + "report/finance/exportincomeinvoiceList.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, invoice, clientId, clientKey, TypeRef, page);
			if(result != null && result.getCode() == 0){
				List<Invoice> list = (List<Invoice>) result.getData();
				if(null != list && !list.isEmpty()){
					List<Integer> userIdList = new ArrayList<>();
					for(int i=0;i<list.size();i++){
						userIdList.add(list.get(i).getCreator());
						userIdList.add(list.get(i).getValidUserId());
					}
					if(userIdList.size()>0){
						userIdList = new ArrayList<Integer>(new HashSet<Integer>(userIdList));
						List<User> userList = commonReportMapper.getUserByUserIds(userIdList);
						if(null != userList && !userList.isEmpty()){
							for(int i=0;i<list.size();i++){
								for(int j=0;j<userList.size();j++){
									if(list.get(i).getCreator().equals(userList.get(j).getUserId())){
										list.get(i).setCreatorName(userList.get(j).getUsername());
									}
									if(list.get(i).getValidUserId().equals(userList.get(j).getUserId())){
										list.get(i).setValidUserNm(userList.get(j).getUsername());
									}
								}
							}
						}
					}
					//分页多次查询
					Page pageInfo = (Page) result.getPage();
					if(pageInfo.getPageNo() == 1){
						Integer total = pageInfo.getTotalPage();
						for (int i = 2; i <= total; i++) {
							pageInfo.setPageNo(i);
							List<Invoice> result_list = this.exportIncomeInvoiceList(invoice, pageInfo);
							list.addAll(result_list);
						}
					}
					return list;
				}
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return null;
	}

	@Override
	public List<InvoiceExportDetail> exportIncomeInvoiceDetailList(Invoice invoice, Page page) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<InvoiceExportDetail>>> TypeRef = new TypeReference<ResultInfo<List<InvoiceExportDetail>>>() {};
		String url=httpUrl + "report/finance/exportincomeinvoicedetaillist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, invoice, clientId, clientKey, TypeRef, page);
			if(result != null && result.getCode() == 0){
				List<InvoiceExportDetail> list = (List<InvoiceExportDetail>) result.getData();
				if(null != list && !list.isEmpty()){
					List<Integer> userIdList = new ArrayList<>();
					for(int i=0;i<list.size();i++){
						userIdList.add(list.get(i).getCreator());
						userIdList.add(list.get(i).getValidUserId());
					}
					if(userIdList.size()>0){
						userIdList = new ArrayList<Integer>(new HashSet<Integer>(userIdList));
						List<User> userList = commonReportMapper.getUserByUserIds(userIdList);
						if(null != userList && !userList.isEmpty()){
							for(int i=0;i<list.size();i++){
								for(int j=0;j<userList.size();j++){
									if(list.get(i).getCreator().equals(userList.get(j).getUserId())){
										list.get(i).setCreatorName(userList.get(j).getUsername());
									}
									if(list.get(i).getValidUserId().equals(userList.get(j).getUserId())){
										list.get(i).setValidUserNm(userList.get(j).getUsername());
									}
								}
							}
						}
					}
					//分页多次查询
					Page pageInfo = (Page) result.getPage();
					if(pageInfo.getPageNo() == 1){
						Integer total = pageInfo.getTotalPage();
						for (int i = 2; i <= total; i++) {
							pageInfo.setPageNo(i);
							List<InvoiceExportDetail> result_list = this.exportIncomeInvoiceDetailList(invoice, pageInfo);
							list.addAll(result_list);
						}
					}
					return list;
				}
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return null;
	}

	@Override
	public List<Invoice> exportOpenInvoiceList(Invoice invoice, Page page) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<Invoice>>> TypeRef = new TypeReference<ResultInfo<List<Invoice>>>() {};
		String url=httpUrl + "report/finance/exportopeninvoicelist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, invoice, clientId, clientKey, TypeRef, page);
			if(result != null && result.getCode() == 0){
				List<Invoice> list = (List<Invoice>) result.getData();
				if(null != list && !list.isEmpty()){
					List<Integer> userIdList = new ArrayList<>();List<Integer> traderIdList = new ArrayList<>();List<Integer> orgIdList = new ArrayList<>();
					for(int i=0;i<list.size();i++){
						userIdList.add(list.get(i).getCreator());//开票人
						if(list.get(i).getType().equals(SysOptionConstant.ID_505)){//销售
							traderIdList.add(list.get(i).getTraderId());
						}else{//售后
							userIdList.add(list.get(i).getUserId());//归属人
						}
						userIdList.add(list.get(i).getSendUserId());//寄送人
						orgIdList.add(list.get(i).getOrgId());
					}
					if(userIdList.size()>0){
						userIdList = new ArrayList<Integer>(new HashSet<Integer>(userIdList));
						List<User> userList = commonReportMapper.getUserByUserIds(userIdList);
						
						traderIdList = new ArrayList<Integer>(new HashSet<Integer>(traderIdList));
						List<User> traderUserList = commonReportMapper.getUserByTraderIdList(traderIdList,ErpConst.ONE);//1客户，2供应商
						if(null == traderUserList){
							traderUserList = new ArrayList<>();
						}
						orgIdList = new ArrayList<Integer>(new HashSet<Integer>(orgIdList));
						List<Organization> orgList = commonReportMapper.getOrgNameByOrgIdList(orgIdList);
						if(orgList == null){
							orgList = new ArrayList<>();
						}
						if(null != userList && !userList.isEmpty()){
							for(int i=0;i<list.size();i++){
								for(int j=0;j<userList.size();j++){
									if(userList.get(j).getUserId().equals(list.get(i).getCreator())){
										list.get(i).setCreatorName(userList.get(j).getUsername());
									}
									if(userList.get(j).getUserId().equals(list.get(i).getSendUserId())){
										list.get(i).setSendUserNm(userList.get(j).getUsername());
									}
									if(SysOptionConstant.ID_504.equals(list.get(i).getType())){//售后
										if(list.get(i).getUserId().equals(userList.get(j).getUserId())){
											list.get(i).setUserNm(userList.get(j).getUsername());
										}
									}
								}
								if(SysOptionConstant.ID_505.equals(list.get(i).getType())){//销售
									for(int j=0;j<traderUserList.size();j++){
										if(traderUserList.get(j).getTraderId().equals(list.get(i).getTraderId())){
											list.get(i).setUserNm(traderUserList.get(j).getUsername());
										}
									}
								}
								for(int k=0;k<orgList.size();k++){
									if(orgList.get(k).getOrgId().equals(list.get(i).getOrgId())){
										list.get(i).setOrgNm(orgList.get(k).getOrgName());
									}
								}
							}
						}
					}
					//分页多次查询
					Page pageInfo = (Page) result.getPage();
					if(pageInfo.getPageNo() == 1){
						Integer total = pageInfo.getTotalPage();
						for (int i = 2; i <= total; i++) {
							pageInfo.setPageNo(i);
							List<Invoice> result_list = this.exportOpenInvoiceList(invoice, pageInfo);
							list.addAll(result_list);
						}
					}
					return list;
				}
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return null;
	}

	@Override
	public List<InvoiceApply> exportInvoiceAdvanceApplyList(InvoiceApply invoiceApply, Page page) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<InvoiceApply>>> TypeRef = new TypeReference<ResultInfo<List<InvoiceApply>>>() {};
		String url=httpUrl + "report/finance/exportinvoiceadvanceapplylist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, invoiceApply, clientId, clientKey, TypeRef, page);
			if(result != null && result.getCode() == 0){
				List<InvoiceApply> list = (List<InvoiceApply>) result.getData();
				if(null != list && !list.isEmpty()){
					List<Integer> userIdList = new ArrayList<>();List<Integer> traderIdList = new ArrayList<>();List<Integer> orgIdList = new ArrayList<>();
					for(int i=0;i<list.size();i++){
						userIdList.add(list.get(i).getCreator());
						userIdList.add(list.get(i).getAdvanceValidUserid());
						traderIdList.add(list.get(i).getTraderId());
						orgIdList.add(list.get(i).getOrgId());
					}
					if(userIdList.size()>0){
						//操作人员
						userIdList = new ArrayList<Integer>(new HashSet<Integer>(userIdList));
						List<User> userList = commonReportMapper.getUserByUserIds(userIdList);
						//客户负责人
						traderIdList = new ArrayList<Integer>(new HashSet<Integer>(traderIdList));
						List<User> traderUserList = commonReportMapper.getUserByTraderIdList(traderIdList,ErpConst.ONE);//1客户，2供应商
						if(null == traderUserList){
							traderUserList = new ArrayList<>();
						}
						//部门
						orgIdList = new ArrayList<Integer>(new HashSet<Integer>(orgIdList));
						List<Organization> orgList = commonReportMapper.getOrgNameByOrgIdList(orgIdList);
						if(orgList == null){
							orgList = new ArrayList<>();
						}
						if(null != userList && !userList.isEmpty()){
							for(int i=0;i<list.size();i++){
								for(int j=0;j<userList.size();j++){
									if(userList.get(j).getUserId().equals(list.get(i).getCreator())){
										list.get(i).setCreatorNm(userList.get(j).getUsername());
									}
									if(userList.get(j).getUserId().equals(list.get(i).getAdvanceValidUserid())){
										list.get(i).setAdvanceValidUserNm(userList.get(j).getUsername());
									}
								}
								for(int j=0;j<traderUserList.size();j++){
									if(traderUserList.get(j).getUserId().equals(list.get(i).getCreator())){
										list.get(i).setUserName(traderUserList.get(j).getUsername());
									}
								}
								for(int k=0;k<orgList.size();k++){
									if(orgList.get(k).getOrgId().equals(list.get(i).getOrgId())){
										list.get(i).setOrgNm(orgList.get(k).getOrgName());
									}
								}
							}
						}
					}
					//分页多次查询
					Page pageInfo = (Page) result.getPage();
					if(pageInfo.getPageNo() == 1){
						Integer total = pageInfo.getTotalPage();
						for (int i = 2; i <= total; i++) {
							pageInfo.setPageNo(i);
							List<InvoiceApply> result_list = this.exportInvoiceAdvanceApplyList(invoiceApply, pageInfo);
							list.addAll(result_list);
						}
					}
					return list;
				}
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return null;
	}

	@Override
	public List<InvoiceApply> exportInvoiceApplyList(InvoiceApply invoiceApply, Page page) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<InvoiceApply>>> TypeRef = new TypeReference<ResultInfo<List<InvoiceApply>>>() {};
		String url=httpUrl + "report/finance/exportinvoiceapplylist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, invoiceApply, clientId, clientKey, TypeRef, page);
			if(result != null && result.getCode() == 0){
				List<InvoiceApply> list = (List<InvoiceApply>) result.getData();
				if(null != list && !list.isEmpty()){
					List<Integer> userIdList = new ArrayList<>();List<Integer> traderIdList = new ArrayList<>();List<Integer> orgIdList = new ArrayList<>();
					for(int i=0;i<list.size();i++){
						userIdList.add(list.get(i).getCreator());
						userIdList.add(list.get(i).getAdvanceValidUserid());
						traderIdList.add(list.get(i).getTraderId());
						orgIdList.add(list.get(i).getOrgId());
					}
					if(userIdList.size()>0){
						//操作人员
						userIdList = new ArrayList<Integer>(new HashSet<Integer>(userIdList));
						List<User> userList = commonReportMapper.getUserByUserIds(userIdList);
						//客户负责人
						traderIdList = new ArrayList<Integer>(new HashSet<Integer>(traderIdList));
						List<User> traderUserList = commonReportMapper.getUserByTraderIdList(traderIdList,ErpConst.ONE);//1客户，2供应商
						if(null == traderUserList){
							traderUserList = new ArrayList<>();
						}
						//部门
						orgIdList = new ArrayList<Integer>(new HashSet<Integer>(orgIdList));
						List<Organization> orgList = commonReportMapper.getOrgNameByOrgIdList(orgIdList);
						if(orgList == null){
							orgList = new ArrayList<>();
						}
						if(null != userList && !userList.isEmpty()){
							for(int i=0;i<list.size();i++){
								for(int j=0;j<userList.size();j++){
									if(userList.get(j).getUserId().equals(list.get(i).getCreator())){
										list.get(i).setCreatorNm(userList.get(j).getUsername());
									}
									if(userList.get(j).getUserId().equals(list.get(i).getAdvanceValidUserid())){
										list.get(i).setAdvanceValidUserNm(userList.get(j).getUsername());
									}
								}
								for(int j=0;j<traderUserList.size();j++){
									if(traderUserList.get(j).getUserId().equals(list.get(i).getCreator())){
										list.get(i).setUserName(traderUserList.get(j).getUsername());
									}
								}
								for(int k=0;k<orgList.size();k++){
									if(orgList.get(k).getOrgId().equals(list.get(i).getOrgId())){
										list.get(i).setOrgNm(orgList.get(k).getOrgName());
									}
								}
							}
						}
					}
					//分页多次查询
					Page pageInfo = (Page) result.getPage();
					if(pageInfo.getPageNo() == 1){
						Integer total = pageInfo.getTotalPage();
						for (int i = 2; i <= total; i++) {
							pageInfo.setPageNo(i);
							List<InvoiceApply> result_list = this.exportInvoiceApplyList(invoiceApply, pageInfo);
							list.addAll(result_list);
						}
					}
					return list;
				}
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return null;
	}

	@Override
	public List<CapitalBill> exportCapitalBillList(CapitalBill capitalBill, Page page) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<CapitalBill>>> TypeRef = new TypeReference<ResultInfo<List<CapitalBill>>>() {};
		String url=httpUrl + "report/finance/exportcapitalbilllist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, capitalBill, clientId, clientKey, TypeRef, page);
			if(result != null && result.getCode() == 0){
				List<CapitalBill> list = (List<CapitalBill>) result.getData();
				if(null != list && !list.isEmpty()){
					List<Integer> userIdList = new ArrayList<>();
					for(int i=0;i<list.size();i++){
						userIdList.add(list.get(i).getCreator());
					}
					if(userIdList.size()>0){
//						List<SysOptionDefinition> sysOptionDefinitionList = getSysOptionDefinitionList(SysOptionConstant.ID_519);
						//操作人员
						userIdList = new ArrayList<Integer>(new HashSet<Integer>(userIdList));
						List<User> userList = commonReportMapper.getUserByUserIds(userIdList);
						if(null != userList && !userList.isEmpty()){
							for(int i=0;i<list.size();i++){
								for(int j=0;j<userList.size();j++){
									if(userList.get(j).getUserId().equals(list.get(i).getCreator())){
										list.get(i).setCreatorName(userList.get(j).getUsername());
									}
								}
							}
						}
					}
					//分页多次查询
					Page pageInfo = (Page) result.getPage();
					if(pageInfo.getPageNo() == 1){
						Integer total = pageInfo.getTotalPage();
						for (int i = 2; i <= total; i++) {
							pageInfo.setPageNo(i);
							List<CapitalBill> result_list = this.exportCapitalBillList(capitalBill, pageInfo);
							list.addAll(result_list);
						}
					}
					return list;
				}
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return null;
	}

	@Override
	public List<BankBill> exportBankBillList(BankBill bankBill, Page page) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<BankBill>>> TypeRef = new TypeReference<ResultInfo<List<BankBill>>>() {};
		String url=httpUrl + "report/finance/exportbankbilllist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, bankBill, clientId, clientKey, TypeRef, page);
			if(result != null && result.getCode() == 0){
				List<BankBill> list = (List<BankBill>) result.getData();
				if(null != list && !list.isEmpty()){
					//分页多次查询
					Page pageInfo = (Page) result.getPage();
					if(pageInfo.getPageNo() == 1){
						Integer total = pageInfo.getTotalPage();
						for (int i = 2; i <= total; i++) {
							pageInfo.setPageNo(i);
							List<BankBill> result_list = this.exportBankBillList(bankBill, pageInfo);
							list.addAll(result_list);
						}
					}
					return list;
				}
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return null;
	}

	@Override
	public List<CapitalBillExport> exportReceiveCapitalBillList(CapitalBill capitalBill, Page page) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<CapitalBillExport>>> TypeRef = new TypeReference<ResultInfo<List<CapitalBillExport>>>() {};
		String url=httpUrl + "report/finance/exportreceivecapitalbilllist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, capitalBill, clientId, clientKey, TypeRef, page);
			if(result != null && result.getCode() == 0){
				List<CapitalBillExport> list = (List<CapitalBillExport>) result.getData();
				if(null != list && !list.isEmpty()){
					List<Integer> userIdList = new ArrayList<>();List<Integer> traderIdList = new ArrayList<>();
					List<Integer> areaIdList = new ArrayList<>();List<Integer> businessUserIdList = new ArrayList<>();
					for(int i=0;i<list.size();i++){
						userIdList.add(list.get(i).getCreator());//财务操作人员
						userIdList.add(list.get(i).getUserId());//订单创建人员
						userIdList.add(list.get(i).getBusinessUserId());//资金交易时归属销售
						traderIdList.add(list.get(i).getTraderId());//客户
						areaIdList.add(list.get(i).getAreaId());
						businessUserIdList.add(list.get(i).getBusinessUserId());//资金交易时归属销售
					}
					if(userIdList.size() > 0 || traderIdList.size() > 0){
						//操作人员
						userIdList = new ArrayList<Integer>(new HashSet<Integer>(userIdList));
						List<User> userList = commonReportMapper.getUserByUserIds(userIdList);
//						//客户负责人
						traderIdList = new ArrayList<Integer>(new HashSet<Integer>(traderIdList));
						List<User> traderUserList = commonReportMapper.getTraderUserAndOrgList(traderIdList);
						if(null == traderUserList){
							traderUserList = new ArrayList<>();
						}
						//根据用户ID查询用户所在部门
						businessUserIdList = new ArrayList<Integer>(new HashSet<Integer>(businessUserIdList));
						List<User> businessUserList = commonReportMapper.getUserOrgByUserIdList(businessUserIdList);
						areaIdList = new ArrayList<Integer>(new HashSet<Integer>(areaIdList));
						//客户地区
						Map<Integer,String> regionStrMap = new HashMap<>();
						for(int a=0;a<areaIdList.size();a++){
							Object object = commonReportService.getRegion(areaIdList.get(a),2);
							if(object!=null){
								regionStrMap.put(areaIdList.get(a),object.toString());
							}
						}
						if(null != userList && !userList.isEmpty()){
							for(int i=0;i<list.size();i++){
								for(int j=0;j<userList.size();j++){
									if(userList.get(j).getUserId().equals(list.get(i).getCreator())){
										list.get(i).setCreatorName(userList.get(j).getUsername());//财务操作人员
									}
									if(userList.get(j).getUserId().equals(list.get(i).getUserId())){
										list.get(i).setUserName(userList.get(j).getUsername());//订单创建人员
									}
									if(userList.get(j).getUserId().equals(list.get(i).getBusinessUserId())){
										list.get(i).setBusinessUserName(userList.get(j).getUsername());//资金交易时归属销售
									}
								}
								for(int j=0;j<traderUserList.size();j++){
									if(traderUserList.get(j).getTraderId().equals(list.get(i).getTraderId())){
										list.get(i).setOptUserName(traderUserList.get(j).getUsername());//当前客户归属人员
										list.get(i).setCapitalTradeUserNm(traderUserList.get(j).getOrgName());//当前客户归属人员所属部门
									}
								}
								/*for(int j=0;j<businessUserList.size();j++){
									if(businessUserList.get(j).getUserId().equals(list.get(i).getBusinessUserId())){
										list.get(i).setCapitalTradeUserNm(businessUserList.get(j).getOrgName());//交易时归属销售所属部门
									}
								}*/
								
								for(Integer key : regionStrMap.keySet()){
									if(key.equals(list.get(i).getAreaId())){
										if(regionStrMap.get(key).split(" ").length >= 2){
											list.get(i).setProvinceStr(regionStrMap.get(key).split(" ")[1]);
										}
										if(regionStrMap.get(key).split(" ").length >= 3){
											list.get(i).setCityStr(regionStrMap.get(key).split(" ")[2]);
										}
									}
								}
							}
						}
					}
					//分页多次查询
					Page pageInfo = (Page) result.getPage();
					if(pageInfo.getPageNo() == 1){
						Integer total = pageInfo.getTotalPage();
						for (int i = 2; i <= total; i++) {
							pageInfo.setPageNo(i);
							List<CapitalBillExport> result_list = this.exportReceiveCapitalBillList(capitalBill, pageInfo);
							list.addAll(result_list);
						}
					}
					return list;
				}
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return null;
	}

	@Override
	public List<CapitalBillExportDetail> exportReceiveCapitalDetailList(CapitalBill capitalBill, Page page) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<CapitalBillExportDetail>>> TypeRef = new TypeReference<ResultInfo<List<CapitalBillExportDetail>>>() {};
		String url=httpUrl + "report/finance/exportreceivecapitaldetaillist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, capitalBill, clientId, clientKey, TypeRef, page);
			if(result != null && result.getCode() == 0){
				List<CapitalBillExportDetail> list = (List<CapitalBillExportDetail>) result.getData();
				if(null != list && !list.isEmpty()){
					List<Integer> userIdList = new ArrayList<>();List<Integer> traderIdList = new ArrayList<>();List<Integer> orgIdList = new ArrayList<>();
					for(int i=0;i<list.size();i++){
						userIdList.add(list.get(i).getCreator());
						traderIdList.add(list.get(i).getTraderId());
						orgIdList.add(list.get(i).getOrgId());
					}
					if(userIdList.size() > 0 || traderIdList.size() > 0 || orgIdList.size() > 0){
						//操作人员
						userIdList = new ArrayList<Integer>(new HashSet<Integer>(userIdList));
						List<User> userList = commonReportMapper.getUserByUserIds(userIdList);
//						//客户负责人
						traderIdList = new ArrayList<Integer>(new HashSet<Integer>(traderIdList));
						List<User> traderUserList = commonReportMapper.getUserByTraderIdList(traderIdList,ErpConst.ONE);//1客户，2供应商
						if(null == traderUserList){
							traderUserList = new ArrayList<>();
						}
						//部门
						orgIdList = new ArrayList<Integer>(new HashSet<Integer>(orgIdList));
						List<Organization> orgList = commonReportMapper.getOrgNameByOrgIdList(orgIdList);
						if(orgList == null){
							orgList = new ArrayList<>();
						}
						if(null != userList && !userList.isEmpty()){
							for(int i=0;i<list.size();i++){
								for(int j=0;j<userList.size();j++){
									if(userList.get(j).getUserId().equals(list.get(i).getCreator())){
										list.get(i).setCreatorName(userList.get(j).getUsername());
									}
								}
								for(int j=0;j<traderUserList.size();j++){
									if(traderUserList.get(j).getTraderId().equals(list.get(i).getTraderId())){
										list.get(i).setOptUserName(traderUserList.get(j).getUsername());
									}
								}
								for(int j=0;j<orgList.size();j++){
									if(orgList.get(j).getOrgId().equals(list.get(i).getOrgId())){
										list.get(i).setSalesDeptName(orgList.get(j).getOrgName());
									}
								}
							}
						}
					}
					//分页多次查询
					Page pageInfo = (Page) result.getPage();
					if(pageInfo.getPageNo() == 1){
						Integer total = pageInfo.getTotalPage();
						for (int i = 2; i <= total; i++) {
							pageInfo.setPageNo(i);
							List<CapitalBillExportDetail> result_list = this.exportReceiveCapitalDetailList(capitalBill, pageInfo);
							list.addAll(result_list);
						}
					}
					return list;
				}
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return null;
	}

	@Override
	public List<CapitalBill> exportPayCapitalBillList(CapitalBill capitalBill, Page page) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<CapitalBill>>> TypeRef = new TypeReference<ResultInfo<List<CapitalBill>>>() {};
		String url=httpUrl + "report/finance/exportpaycapitalbilllist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, capitalBill, clientId, clientKey, TypeRef, page);
			if(result != null && result.getCode() == 0){
				List<CapitalBill> list = (List<CapitalBill>) result.getData();
				if(null != list && !list.isEmpty()){
					List<Integer> userIdList = new ArrayList<>();
					for(int i=0;i<list.size();i++){
						userIdList.add(list.get(i).getCreator());
					}
					if(userIdList.size() > 0){
						//操作人员
						userIdList = new ArrayList<Integer>(new HashSet<Integer>(userIdList));
						List<User> userList = commonReportMapper.getUserByUserIds(userIdList);
						if(null != userList && !userList.isEmpty()){
							for(int i=0;i<list.size();i++){
								for(int j=0;j<userList.size();j++){
									if(userList.get(j).getUserId().equals(list.get(i).getCreator())){
										list.get(i).setCreatorName(userList.get(j).getUsername());
									}
								}
							}
						}
					}
					//分页多次查询
					Page pageInfo = (Page) result.getPage();
					if(pageInfo.getPageNo() == 1){
						Integer total = pageInfo.getTotalPage();
						for (int i = 2; i <= total; i++) {
							pageInfo.setPageNo(i);
							List<CapitalBill> result_list = this.exportPayCapitalBillList(capitalBill, pageInfo);
							list.addAll(result_list);
						}
					}
					return list;
				}
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return null;
	}

	@Override
	public List<PayApply> exportPayApplyList(PayApply payApply, Page page) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<PayApply>>> TypeRef = new TypeReference<ResultInfo<List<PayApply>>>() {};
		String url=httpUrl + "report/finance/exportpayapplylist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, payApply, clientId, clientKey, TypeRef, page);
			if(result != null && result.getCode() == 0){
				List<PayApply> list = (List<PayApply>) result.getData();
				if(null != list && !list.isEmpty()){
					List<Integer> userIdList = new ArrayList<>();
					for(int i=0;i<list.size();i++){
						userIdList.add(list.get(i).getCreator());
						userIdList.add(list.get(i).getValidUserId());
					}
					if(userIdList.size() > 0){
						//操作人员
						userIdList = new ArrayList<Integer>(new HashSet<Integer>(userIdList));
						List<User> userList = commonReportMapper.getUserByUserIds(userIdList);
						if(null != userList && !userList.isEmpty()){
							for(int i=0;i<list.size();i++){
								for(int j=0;j<userList.size();j++){
									if(userList.get(j).getUserId().equals(list.get(i).getCreator())){
										list.get(i).setCreatorName(userList.get(j).getUsername());
									}
									if(userList.get(j).getUserId().equals(list.get(i).getValidStatus())){
										list.get(i).setValidUserName(userList.get(j).getUsername());
									}
								}
							}
						}
					}
					//分页多次查询
					Page pageInfo = (Page) result.getPage();
					if(pageInfo.getPageNo() == 1){
						Integer total = pageInfo.getTotalPage();
						for (int i = 2; i <= total; i++) {
							pageInfo.setPageNo(i);
							List<PayApply> result_list = this.exportPayApplyList(payApply, pageInfo);
							list.addAll(result_list);
						}
					}
					return list;
				}
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return null;
	}

	@Override
	public List<TraderAccountPeriodApply> exportAccountPeriodList(TraderAccountPeriodApply tapa, Page page) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<TraderAccountPeriodApply>>> TypeRef = new TypeReference<ResultInfo<List<TraderAccountPeriodApply>>>() {};
		String url=httpUrl + "report/finance/exportaccountperiodlist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, tapa, clientId, clientKey, TypeRef, page);
			if(result != null && result.getCode() == 0){
				List<TraderAccountPeriodApply> list = (List<TraderAccountPeriodApply>) result.getData();
				if(null != list && !list.isEmpty()){
					List<Integer> traderIdList = new ArrayList<>();
					for(int i=0;i<list.size();i++){
						traderIdList.add(list.get(i).getTraderId());
					}
					if(traderIdList.size() > 0){
						//操作人员
						traderIdList = new ArrayList<Integer>(new HashSet<Integer>(traderIdList));
						List<User> traderUserList = commonReportMapper.getUserByTraderIdList(traderIdList,ErpConst.ONE);//1客户，2供应商
						if(null != traderUserList && !traderUserList.isEmpty()){
							for(int i=0;i<list.size();i++){
								for(int j=0;j<traderUserList.size();j++){
									if(traderUserList.get(j).getTraderId().equals(list.get(i).getTraderId())){
										list.get(i).setCreatorNm(traderUserList.get(j).getUsername());
									}
								}
							}
						}
					}
					//分页多次查询
					Page pageInfo = (Page) result.getPage();
					if(pageInfo.getPageNo() == 1){
						Integer total = pageInfo.getTotalPage();
						for (int i = 2; i <= total; i++) {
							pageInfo.setPageNo(i);
							List<TraderAccountPeriodApply> result_list = this.exportAccountPeriodList(tapa, pageInfo);
							list.addAll(result_list);
						}
					}
					return list;
				}
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return null;
	}

	@Override
	public List<SaleOrderExportCw> exportFinanceSaleOrderList(Saleorder saleorder, Page page) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<SaleOrderExportCw>>> TypeRef = new TypeReference<ResultInfo<List<SaleOrderExportCw>>>() {};
		String url=httpUrl + "report/finance/exportfinancesaleorderlist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, saleorder, clientId, clientKey, TypeRef, page);
			if(result != null && result.getCode() == 0){
				List<SaleOrderExportCw> list = (List<SaleOrderExportCw>) result.getData();
				if(null != list && !list.isEmpty()){
					List<Integer> traderIdList = new ArrayList<>();List<Integer> orgIdList = new ArrayList<>();
					for(int i=0;i<list.size();i++){
						traderIdList.add(list.get(i).getTraderId());
						orgIdList.add(list.get(i).getOrgId());
					}
					if(traderIdList.size() > 0){
						//操作人员
						traderIdList = new ArrayList<Integer>(new HashSet<Integer>(traderIdList));
						List<User> traderUserList = commonReportMapper.getUserByTraderIdList(traderIdList,ErpConst.ONE);//1客户，2供应商
						
						//部门
						orgIdList = new ArrayList<Integer>(new HashSet<Integer>(orgIdList));
						List<Organization> orgList = commonReportMapper.getOrgNameByOrgIdList(orgIdList);
						if(orgList == null){
							orgList = new ArrayList<>();
						}
						
						if((null != traderUserList && !traderUserList.isEmpty()) || (null != orgList && !orgList.isEmpty())){
							for(int i=0;i<list.size();i++){
								for(int j=0;j<traderUserList.size();j++){
									if(traderUserList.get(j).getTraderId().equals(list.get(i).getTraderId())){
										list.get(i).setOptUserName(traderUserList.get(j).getUsername());
									}
								}
								for(int j=0;j<orgList.size();j++){
									if(orgList.get(j).getOrgId().equals(list.get(i).getOrgId())){
										list.get(i).setSalesDeptName(orgList.get(j).getOrgName());
									}
								}
							}
						}
					}
					//分页多次查询
					Page pageInfo = (Page) result.getPage();
					if(pageInfo.getPageNo() == 1){
						Integer total = pageInfo.getTotalPage();
						for (int i = 2; i <= total; i++) {
							pageInfo.setPageNo(i);
							List<SaleOrderExportCw> result_list = this.exportFinanceSaleOrderList(saleorder, pageInfo);
							list.addAll(result_list);
						}
					}
					return list;
				}
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return null;
	}

	@Override
	public List<SaleOrderExportDetail> exportFinanceSaleOrderDetailList(Saleorder saleorder, Page page) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<SaleOrderExportDetail>>> TypeRef = new TypeReference<ResultInfo<List<SaleOrderExportDetail>>>() {};
		String url=httpUrl + "report/finance/exportfinancesaleorderdetaillist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, saleorder, clientId, clientKey, TypeRef, page);
			if(result != null && result.getCode() == 0){
				List<SaleOrderExportDetail> list = (List<SaleOrderExportDetail>) result.getData();
				if(null != list && !list.isEmpty()){
					List<Integer> traderIdList = new ArrayList<>();
					for(int i=0;i<list.size();i++){
						traderIdList.add(list.get(i).getTraderId());
					}
					if(traderIdList.size() > 0){
						//操作人员
						traderIdList = new ArrayList<Integer>(new HashSet<Integer>(traderIdList));
						List<User> traderUserList = commonReportMapper.getTraderUserAndOrgList(traderIdList);
						
						
						if(null != traderUserList && !traderUserList.isEmpty()){
							for(int i=0;i<list.size();i++){
								for(int j=0;j<traderUserList.size();j++){
									if(traderUserList.get(j).getTraderId().equals(list.get(i).getTraderId())){
										list.get(i).setOptUserNm(traderUserList.get(j).getUsername());
										list.get(i).setOrgNm(traderUserList.get(j).getOrgName());
									}
								}
							}
						}
					}
					//分页多次查询
					Page pageInfo = (Page) result.getPage();
					if(pageInfo.getPageNo() == 1){
						Integer total = pageInfo.getTotalPage();
						for (int i = 2; i <= total; i++) {
							pageInfo.setPageNo(i);
							List<SaleOrderExportDetail> result_list = this.exportFinanceSaleOrderDetailList(saleorder, pageInfo);
							list.addAll(result_list);
						}
					}
					return list;
				}
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return null;
	}

	/*@Override
	public List<InvoiceExportDetail> exportOpenInvoiceDetailList(Invoice invoice, Page page) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<InvoiceExportDetail>>> TypeRef = new TypeReference<ResultInfo<List<InvoiceExportDetail>>>() {};
		String url=httpUrl + "report/finance/exportopeninvoicedetaillist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, invoice, clientId, clientKey, TypeRef, page);
			if(result != null && result.getCode() == 0){
				List<InvoiceExportDetail> list = (List<InvoiceExportDetail>) result.getData();
				if(null != list && !list.isEmpty()){
					List<Integer> userIdList = new ArrayList<>();
					List<Integer> traderIdList = new ArrayList<>();
					List<Integer> orgIdList = new ArrayList<>();
					for(InvoiceExportDetail detail : list){
						if(null != detail.getCreator() && detail.getCreator() > 0){
							userIdList.add(detail.getCreator());//开票人
						}
						if(null != detail.getExpressUserId() && detail.getExpressUserId() > 0){
							userIdList.add(detail.getExpressUserId());//寄送人
						}
						if(null != detail.getUserId() && detail.getUserId() > 0){
							userIdList.add(detail.getUserId());//归属人
						}
						if(null != detail.getOrgId() && detail.getOrgId() > 0){
							orgIdList.add(detail.getOrgId());//部门
						}
					}
					
					if(userIdList.size()>0){
						List<User> userList = commonReportMapper.getUserByUserIds(userIdList);
						
						if(null != userList && !userList.isEmpty()){
							for(InvoiceExportDetail detail : list){
								for(User u : userList){
									if(u.getUserId().equals(detail.getCreator())){
										detail.setCreatorName(u.getUsername());
									}
									if(u.getUserId().equals(detail.getExpressUserId())){
										detail.setSendUserNm(u.getUsername());
									}
									if(u.getUserId().equals(detail.getUserId())){
										detail.setUserNm(u.getUsername());
									}
								}
							}
							
						}
					}
					
					if(orgIdList.size() > 0){
						List<Organization> orgList = commonReportMapper.getOrgNameByOrgIdList(orgIdList);
						if(null != orgList && orgList.size() > 0){
							for(InvoiceExportDetail detail : list){
								for(Organization organization : orgList){
									if(organization.getOrgId().equals(detail.getOrgId())){
										detail.setOrgNm(organization.getOrgName());
									}
								}
							}
						}
					}
					//分页多次查询
					Page pageInfo = (Page) result.getPage();
					if(pageInfo.getPageNo() == 1){
						Integer total = pageInfo.getTotalPage();
						for (int i = 2; i <= total; i++) {
							pageInfo.setPageNo(i);
							List<InvoiceExportDetail> result_list = this.exportOpenInvoiceDetailList(invoice, pageInfo);
							list.addAll(result_list);
						}
					}
					return list;
				}
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return null;
	}*/

}
