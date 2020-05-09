<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="付款记录" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/finance/capitalbill/index_payment_record.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src="<%= basePath %>static/libs/jquery/plugins/DatePicker/WdatePicker.js?rnd=<%=Math.random()%>"></script>
	<div class="content">
		<div class="searchfunc">		
			<form action="${pageContext.request.contextPath}/finance/capitalbill/getPaymentRecordListPage.do" method="post" id="search">
				<ul>
					<li>
	                    <label class="infor_name">订单号</label>
	                    <input type="text" class="input-middle" name="capitalBillDetail.orderNo" id="" value="${capitalBill.capitalBillDetail.orderNo}">
	                </li>
	                <li>
	                    <label class="infor_name">合同名称</label>
	                    <input type="text" class="input-middle" name="buyorder.traderName" id="" value="${capitalBill.buyorder.traderName}">
	                </li>
	                <li>
	                    <label class="infor_name">收款名称</label>
	                    <input type="text" class="input-middle" name="payee" id="" value="${capitalBill.payee}">
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
	                    <label class="infor_name">业务类型</label>
	                    <select class="input-middle f_left" name="capitalBillDetail.bussinessType">
	                    	<option value="-1">全部</option>
		                    <c:forEach var="list" items="${bussinessTypeList}">
		                    	<option value="${list.sysOptionDefinitionId}" <c:if test="${capitalBill.capitalBillDetail.bussinessType == list.sysOptionDefinitionId}">selected="selected"</c:if> >${list.title}</option>
		                    </c:forEach>
	                    </select>
                    </li>
	                <li>
	                    <label class="infor_name">付款备注</label>
	                    <input type="text" class="input-middle" name="comments" id="" value="${capitalBill.comments}">
	                </li>
	                	<li>
						<label class="infor_name">交易金额</label>
						<input class="f_left input-smaller96 mr5" type="text" name="searchBeginAmount" id="searchBeginAmount" value='${capitalBill.searchBeginAmount}'>
	                    <div class="gang">-</div>
	                    <input class="f_left input-smaller96" type="text" name="searchEndAmount" id="searchEndAmount" value='${capitalBill.searchEndAmount}'>
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
            		<!-- <span class="bg-light-blue bt-bg-style bt-small" onclick="exportPayCapitalBillList()">导出列表</span> -->
            	</div>
			</form>
		</div>
		<div class="fixdiv">
        <div class="superdiv" style="width:100%">
			<table class="table table-bordered table-striped table-condensed table-centered">
				<thead>
					<tr>
                        <th class="wid12">订单号</th>
                        <th>供应商名称</th>
                        <th>收款名称</th>
                        <th class="wid7">交易主体</th>
                        <th class="wid7">交易方式</th>
                        <th class="wid8">业务类型</th>
                        <th class="wid8">订单金额</th>
                        <th class="wid10">订单实际金额</th>
                        <th class="wid8">已付款</th>
                        <th class="wid8">本次付款</th>
                        <th class="wid8">交易时间</th>
                        <th>付款备注</th>
                        <th class="wid10">操作人</th>
                        <th class="wid12">关联单号</th>
					</tr>
				</thead>
			
				<tbody class="goods">
				<c:if test="${not empty capitalBillList}">
					<c:set var="pageNum" value="0"></c:set>
					<c:set var="pageAmount2" value="0.00"></c:set>
                	<c:forEach items="${capitalBillList}" var="list" varStatus="status">
                		<c:set var="pageNum" value="${status.count}"></c:set>
						<c:set var="pageAmount2" value="${pageAmount2 + list.amount}"></c:set>
	                    <tr>
	                        <td>
                        		<c:if test="${list.operationType eq 1}"><!-- 采购 -->
	                        		<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewfinancebuyorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
	                        			"link":"./finance/buyorder/viewBuyorder.do?buyorderId=${list.relatedId}","title":"订单信息"}'>${list.orderNo}</a>
                        		</c:if>
	                        	<c:if test="${list.operationType eq 2}"><!-- 售后 -->
                        			<span class="font-blue addtitle" tabtitle='{"num":"view_invoice_after${list.relatedId}",
										"link":"./finance/after/getFinanceAfterSaleDetail.do?afterSalesId=${list.relatedId}","title":"财务售后订单"}'>
										${list.orderNo}
									</span>
                        		</c:if>
	                        </td>
	                        <td class="text-left">
	                        	<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewsupplier${list.traderId}",
									"link":"./trader/supplier/baseinfo.do?traderId=${list.traderId}","title":"供应商信息"}'>${list.traderName}</a>
	                        </td>
	                        <td>${list.payee}</td>
	                        <td>
	                        	<c:if test="${list.traderSubject eq 1}">对公</c:if>
	                        	<c:if test="${list.traderSubject eq 2}">对私</c:if>
	                        </td>
	                        <td>
	                        	<c:forEach var="modeList" items="${traderModeList}" varStatus="">
									<c:if test="${modeList.sysOptionDefinitionId eq list.traderMode}">${modeList.title}</c:if>
								</c:forEach>
	                        </td>
	                        <td>
	                        	<c:forEach var="typeList" items="${bussinessTypeList}" varStatus="">
									<c:if test="${typeList.sysOptionDefinitionId eq list.bussinessType}">${typeList.title}</c:if>
								</c:forEach>
	                        </td>
	                        <td><fmt:formatNumber type="number" value="${list.totalAmount}" pattern="0.00" maxFractionDigits="2" /></td>
	                        <td><fmt:formatNumber type="number" value="${list.realAmount}" pattern="0.00" maxFractionDigits="2" /></td>
	                        <td><fmt:formatNumber type="number" value="${list.orderPaymentTotalAmount}" pattern="0.00" maxFractionDigits="2" /></td>
	                        <td><fmt:formatNumber type="number" value="${list.amount}" pattern="0.00" maxFractionDigits="2" /></td>
	                        <td><date:date value ="${list.traderTime}"/></td>
	                        <td>${list.comments}</td>
	                        <td>${list.creatorName}</td>
	                        <td>
                        		<!-- operationType:1此条记录是采购，关联单号为售后，反之：2，此条记录是售后，关联单号为采购或销售 -->
                        		<c:if test="${list.operationType eq 2}">
                        			<c:choose>
                        				<c:when test="${list.afterSalesPaymentType eq 535}">
			                        		<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewinvoicesaleorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
		                        				"link":"./finance/invoice/viewSaleorder.do?saleorderId=${list.relatedOrderId}","title":"订单信息"}'>${list.relatedOrderNo}</a>
                        				</c:when>
                        				<c:otherwise>
			                        		<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewfinancebuyorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
			                        			"link":"./finance/buyorder/viewBuyorder.do?buyorderId=${list.relatedOrderId}","title":"订单信息"}'>${list.relatedOrderNo}</a>
                        				</c:otherwise>
                        			</c:choose>
                        		</c:if>
	                        	<c:if test="${list.operationType eq 1}">
                        			<span class="font-blue addtitle" tabtitle='{"num":"view_invoice_after${list.relatedOrderId}",
										"link":"./finance/after/getFinanceAfterSaleDetail.do?afterSalesId=${list.relatedOrderId}","title":"财务售后订单"}'>
										${list.relatedOrderNo}
									</span>
                        		</c:if>
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
					【全部结果 订单数：${page.totalRecord} 订单总额：<fmt:formatNumber type="number" value="${capitalBill.orderPaymentTotalAmount}" pattern="0.00" maxFractionDigits="2" /> 订单已付款总额：<fmt:formatNumber type="number" value="${capitalBill.capitalBillCollectionAmount}" pattern="0.00" maxFractionDigits="2" /> 本次付款总额：<fmt:formatNumber type="number" value="${capitalBill.thisPaymentTotalAmount}" pattern="0.00" maxFractionDigits="2" />】
					【本页统计 订单数：${pageNum} 总额：<fmt:formatNumber type="number" value="" pattern="0.00" maxFractionDigits="2" /> 订单已付款总额：<fmt:formatNumber type="number" value="${capitalBill.thisPaymentTotalAmount}" pattern="0.00" maxFractionDigits="2" /> 本次付款总额：<fmt:formatNumber type="number" value="${pageAmount2}" pattern="0.00" maxFractionDigits="2" />】
				</div>
			</c:if>
		</div>
	</div>
<%@ include file="../../common/footer.jsp"%>