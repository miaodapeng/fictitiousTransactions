<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="资金流水表" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/finance/capitalbill/index.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src="<%= basePath %>static/libs/jquery/plugins/DatePicker/WdatePicker.js?rnd=<%=Math.random()%>"></script>
	<div class="content">
		<div class="searchfunc">		
			<form action="${pageContext.request.contextPath}/finance/capitalbill/getCapitalBillListPage.do" method="post" id="search">
				<ul>
					<li>
	                    <label class="infor_name">记账编号</label>
	                    <input type="text" class="input-middle" name="capitalBillNo" id="" value="${capitalBill.capitalBillNo}">
	                </li>
	                <li>
	                    <label class="infor_name">流水号</label>
	                    <input type="text" class="input-middle" name="tranFlow" id="" value="${capitalBill.tranFlow}">
	                </li>
	                <li>
	                    <label class="infor_name">业务类型</label>
	                    <select class="input-middle f_left" name="capitalBillDetail.bussinessType">
	                    	<option value="-1">全部</option>
		                    <c:forEach var="list" items="${bussinessTypeList}">
		                    	<option value="${list.sysOptionDefinitionId}" <c:if test="${capitalBill.capitalBillDetail.bussinessType == list.sysOptionDefinitionId}">selected="selected"</c:if> >${list.title}</option>
		                    </c:forEach>
	                    </select>
                    </li>
                    <li>
	                    <label class="infor_name">业务单据</label>
	                    <input type="text" class="input-middle" name="capitalBillDetail.orderNo" id="" value="${capitalBill.capitalBillDetail.orderNo}">
	                </li>
	                <li>
	                    <label class="infor_name">交易名称</label>
	                    <input type="text" class="input-middle" name="payee" id="" value="${capitalBill.payee}">
	                </li>
	                <li>
	                    <label class="infor_name">交易帐号</label>
	                    <input type="text" class="input-middle" name="payeeBankAccount" id="" value="${capitalBill.payeeBankAccount}">
	                </li>
	                <li>
	                    <label class="infor_name">交易类型</label>
	                    <select class="input-middle f_left" name="traderType">
	                    	<option value="-1">全部</option>
	                    	<option value="1" <c:if test="${capitalBill.traderType == 1}">selected="selected"</c:if> >收入</option>
	                    	<option value="2" <c:if test="${capitalBill.traderType == 2}">selected="selected"</c:if> >支出</option>
	                    	<option value="3" <c:if test="${capitalBill.traderType == 3}">selected="selected"</c:if> >转移</option>
	                    </select>
                    </li>
                    <li>
	                    <label class="infor_name">交易主体</label>
	                    <select class="input-middle f_left" name="traderSubject">
	                    	<option value="-1">全部</option>
	                    	<option value="1" <c:if test="${capitalBill.traderSubject == 1}">selected="selected"</c:if> >对公</option>
	                    	<option value="2" <c:if test="${capitalBill.traderSubject == 2}">selected="selected"</c:if> >对私</option>
	                    </select>
                    </li>
                    <li>
	                    <label class="infor_name">交易方式</label>
	                    <select class="input-middle f_left" name="traderMode">
	                    	<option value="-1">全部</option>
	                    	<c:forEach var="list" items="${traderModeList}">
		                    	<option value="${list.sysOptionDefinitionId}" <c:if test="${capitalBill.traderMode == list.sysOptionDefinitionId}">selected="selected"</c:if> >${list.title}</option>
		                    </c:forEach>
	                    </select>
                    </li>
	                <li>
						<label class="infor_name">交易金额</label>
						<input class="f_left input-smaller96 mr5" type="text" name="searchBeginAmount" id="searchBeginAmount" value='${capitalBill.searchBeginAmount}'>
	                    <div class="gang">-</div>
	                    <input class="f_left input-smaller96" type="text" name="searchEndAmount" id="searchEndAmount" value='${capitalBill.searchEndAmount}'>
					</li>
	                <li>
	                    <label class="infor_name">交易备注</label>
	                    <input type="text" class="input-middle" name="comments" id="" value="${capitalBill.comments}">
	                </li>
	                <li>
						<div class="infor_name">
							交易时间
						</div>
						<input class="Wdate f_left input-smaller96 mr5" type="text" placeholder="请选择日期" onClick="WdatePicker()" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'searchEndtimeStr\')}'})" name="searchBegintimeStr" id="searchBegintimeStr" value='<c:choose>
							<c:when test="${capitalBill.searchBegintime > 0}">
								<date:date value ="${capitalBill.searchBegintime}" format="yyyy-MM-dd"/>
							</c:when>
							<c:when test="${capitalBill.searchBegintime == null}">
							</c:when>
							<c:otherwise>
								${pre1MonthDate}
							</c:otherwise>
						</c:choose>'>
	                    <div class="gang">-</div>
	                    <input class="Wdate f_left input-smaller96" type="text" placeholder="请选择日期" onClick="WdatePicker()" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'searchBegintimeStr\')}'})" name="searchEndtimeStr" id="searchEndtimeStr" value='<c:choose>
							<c:when test="${capitalBill.searchEndtime > 0}">
								<date:date value ="${capitalBill.searchEndtime}" format="yyyy-MM-dd"/>
							</c:when>
							<c:when test="${capitalBill.searchEndtime == null}">
							</c:when>
							<c:otherwise>
								${nowDate}
							</c:otherwise>
						</c:choose>'>
					</li>
            	</ul>
            	<div class="tcenter">
            		<input type="hidden" name="pre1MonthDate" value="${pre1MonthDate}">
           			<input type="hidden" name="nowDate" value="${nowDate}">
	                <span class="bg-light-blue bt-bg-style bt-small" onclick="search();" id="searchSpan">搜索</span>
	                <span class="bt-small bg-light-blue bt-bg-style" onclick="searchReset();">重置</span>
            		<span class="bg-light-blue bt-bg-style bt-small" onclick="exportCapitalBillList()">导出列表</span>
            	
            	</div>
			</form>
		</div>
		<div class="fixdiv">
        <div class="superdiv">
			<table class="table table-bordered table-striped table-condensed table-centered">
				<thead>
					<tr>
                        <th class="wid18">记账编号</th>
                        <th class="wid18">流水号</th>
                        <th class="wid18">交易时间</th>
                        <th class="wid8">交易金额</th>
                        <th class="wid18">付款方及帐号</th>
                        <th class="wid18">收款方及帐号</th>
                        <th class="wid8">交易类型</th>
                        <th class="wid8">交易主体</th>
                        <th class="wid8">交易方式</th>
                        <th>交易备注</th>
                        <th class="wid8">操作人</th>
                        <th style="width:300px;">业务单据</th>
					</tr>
				</thead>
			
				<tbody class="goods">
				<c:if test="${not empty capitalBillList}">
					<c:set var="pageNum" value="0"></c:set>
					<c:set var="pageAmount1" value="0.00"></c:set>
					<c:set var="pageAmount2" value="0.00"></c:set>
					<c:set var="pageAmount3" value="0.00"></c:set>
                	<c:forEach items="${capitalBillList}" var="list" varStatus="status">
                		<c:set var="pageNum" value="${status.count}"></c:set>
                		<c:if test="${list.traderType eq 1}">
							<c:set var="pageAmount1" value="${pageAmount1 + list.amount}"></c:set>
						</c:if>
						<c:if test="${list.traderType eq 2}">
							<c:set var="pageAmount2" value="${pageAmount2 + list.amount}"></c:set>
						</c:if>
						<c:if test="${list.traderType eq 3}">
							<c:set var="pageAmount3" value="${pageAmount3 + list.amount}"></c:set>
						</c:if>
	                    <tr>
	                        <td>${list.capitalBillNo}</td>
	                        <td>${list.tranFlow}</td>
	                        <td><date:date value ="${list.traderTime}"/></td>
	                        <td>
	                        	<c:choose>
									<c:when test="${list.traderType eq 1}">
										<span class="success-color1"><fmt:formatNumber type="number" value="${list.amount}" pattern="0.00" maxFractionDigits="2" /></span>
									</c:when>
									<c:when test="${list.traderType eq 2}">
										<span class="remind-color1"><fmt:formatNumber type="number" value="${list.amount}" pattern="0.00" maxFractionDigits="2" /></span>
									</c:when>
									<c:otherwise>
										<fmt:formatNumber type="number" value="${list.amount}" pattern="0.00" maxFractionDigits="2" />
									</c:otherwise>
								</c:choose>
	                        </td>
	                        <td>
	                        	${list.payer}<br>
	                        	${list.payerBankName}<br>
	                        	${list.payerBankAccount}
	                        </td>
	                        <td>
	                        	${list.payee}<br>
	                        	${list.payeeBankName}<br>
	                        	${list.payeeBankAccount}
	                        </td>
	                        <td>
	                        	<c:if test="${list.traderType eq 1}"><span class="success-color1">收入</span></c:if>
	                        	<c:if test="${list.traderType eq 2}"><span class="remind-color1">支出</span></c:if>
	                        	<c:if test="${list.traderType eq 3}">转移</c:if>
	                        	<c:if test="${list.traderType eq 4}">转入</c:if>
	                        	<c:if test="${list.traderType eq 5}">转出</c:if>
	                        </td>
	                        <td>
	                        	<c:if test="${list.traderSubject eq 1}">对公</c:if>
	                        	<c:if test="${list.traderSubject eq 2}">对私</c:if>
	                        </td>
	                        <td>
	                        	<c:forEach var="modeList" items="${traderModeList}" varStatus="">
									<c:if test="${modeList.sysOptionDefinitionId eq list.traderMode}">${modeList.title}</c:if>
								</c:forEach>
	                        </td>
	                        <td>${list.comments}</td>
	                        <td>${list.creatorName}</td>
	                        <td>
	                        	<c:if test="${list.capitalBillDetail.orderType eq 1}">
                        		<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewinvoicesaleorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>","link":"./finance/invoice/viewSaleorder.do?saleorderId=${list.capitalBillDetail.relatedId}","title":"订单信息"}'>${list.capitalBillDetail.orderNo}</a>
                        		</c:if>
                        		<c:if test="${list.capitalBillDetail.orderType eq 2}">
                        		<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewfinancebuyorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>","link":"./finance/buyorder/viewBuyorder.do?buyorderId=${list.capitalBillDetail.relatedId}","title":"订单信息"}'>${list.capitalBillDetail.orderNo}</a>
                        		</c:if>
                        		<c:if test="${list.capitalBillDetail.orderType eq 3}">
                        		<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"view_invoice_after<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",	"link":"./finance/after/getFinanceAfterSaleDetail.do?afterSalesId=${list.capitalBillDetail.relatedId}","title":"售后详情"}'>${list.capitalBillDetail.orderNo}</a>
                        		</c:if> | 
                        		<c:forEach var="bussinessList" items="${bussinessTypeList}" varStatus="">
									<c:if test="${bussinessList.sysOptionDefinitionId eq list.capitalBillDetail.bussinessType}">${bussinessList.title}</c:if>
								</c:forEach> | 
	                        	<fmt:formatNumber type="number" value="${list.capitalBillDetail.amount}" pattern="0.00" maxFractionDigits="2" />
	                        </td>
	                    </tr>
                	</c:forEach>
        		</c:if>
				</tbody>
			</table>
			
			<c:if test="${empty capitalBillList}">
				
				<!-- 查询无结果弹出 -->
           		<div class="noresult">查询无结果！请尝试使用其他搜索条件。</div>
			</c:if>
			</div>
		</div>
		<div>
			<tags:page page="${page}"/>
			<div class="clear"></div>
			<c:if test="${not empty capitalBillList}">
				<div class="fixtablelastline">
					【全部结果 条目：${page.totalRecord} 收入总额：<span class="success-color1"><fmt:formatNumber type="number" value="${capitalBill.capitalBillCollectionAmount}" pattern="0.00" maxFractionDigits="2" /></span> 支出总额：<span class="remind-color1"><fmt:formatNumber type="number" value="${capitalBill.capitalBillPaymentAmount}" pattern="0.00" maxFractionDigits="2" /></span>】
					【本页统计 条目：${pageNum} 收入总额：<span class="success-color1"><fmt:formatNumber type="number" value="${pageAmount1}" pattern="0.00" maxFractionDigits="2" /></span> 支出总额：<span class="remind-color1"><fmt:formatNumber type="number" value="${pageAmount2}" pattern="0.00" maxFractionDigits="2" /></span>】
				</div>
			</c:if>
		</div>
	</div>
<%@ include file="../../common/footer.jsp"%>