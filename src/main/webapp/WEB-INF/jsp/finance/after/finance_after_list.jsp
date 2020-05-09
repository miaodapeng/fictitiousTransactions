<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="财务售后列表" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/finance/after/invoice_after_list.js?rnd=<%=Math.random()%>'></script>
<div class="main-container">
	<div class="list-pages-search">
		<form method="post" id="search" action="<%= basePath %>/finance/after/getFinanceAfterListPage.do">
			<ul>
				<li>
					<label class="infor_name">售后单号</label> 
					<input type="text" class="input-middle" name="afterSalesNo" id="afterSalesNo" value="${invoiceAfter.afterSalesNo}" />
				</li>
				<li>
					<label class="infor_name">对应订单号</label> 
					<input type="text" class="input-middle" name="orderNo" id="orderNo" value="${invoiceAfter.orderNo}" />
				</li>
				<li>
					<label class="infor_name">合同客户</label> 
					<input type="text" class="input-middle" name="traderName" id="traderName" value="${invoiceAfter.traderName}" />
				</li>
				<li>
					<label class="infor_name">售后状态</label> 
					<select class="input-middle" name="atferSalesStatus" id="atferSalesStatus">
						<option value="">全部</option>
						<option <c:if test="${invoiceAfter.atferSalesStatus eq 0}">selected</c:if> value="0">待确认</option>
						<option	<c:if test="${invoiceAfter.atferSalesStatus eq 1}">selected</c:if> value="1">进行中</option>
						<option	<c:if test="${invoiceAfter.atferSalesStatus eq 2}">selected</c:if> value="2">已完结</option>
						<option <c:if test="${invoiceAfter.atferSalesStatus eq 3}">selected</c:if> value="3">已关闭</option>
					</select>
				</li>
				
				<li>
					<label class="infor_name">开票状态</label> 
					<select class="input-middle" name="openInvoiceStatus" id="openInvoiceStatus">
						<option value="">全部</option>
						<option <c:if test="${invoiceAfter.openInvoiceStatus eq 0}">selected</c:if> value="0">未开票</option>
						<option	<c:if test="${invoiceAfter.openInvoiceStatus eq 1}">selected</c:if> value="1">部分开票</option>
						<option	<c:if test="${invoiceAfter.openInvoiceStatus eq 2}">selected</c:if> value="2">全部开票</option>
					</select>
				</li>
				<li>
					<label class="infor_name">收款状态</label> 
					<select class="input-middle" name="receivePaymentStatus" id="receivePaymentStatus">
						<option value="">全部</option>
						<option <c:if test="${invoiceAfter.receivePaymentStatus eq 0}">selected</c:if> value="0">未收款</option>
						<option	<c:if test="${invoiceAfter.receivePaymentStatus eq 1}">selected</c:if> value="1">部分收款</option>
						<option	<c:if test="${invoiceAfter.receivePaymentStatus eq 2}">selected</c:if> value="2">全部收款</option>
					</select>
				</li>
				<%-- <li>
					<label class="infor_name">审核状态</label> 
					<select class="input-middle" name="status" id="status">
						<option value="">全部</option>
						<option <c:if test="${invoiceAfter.status eq 0}">selected</c:if> value="0">待确认</option>
						<option <c:if test="${invoiceAfter.status eq 1}">selected</c:if> value="1">审核中</option>
						<option <c:if test="${invoiceAfter.status eq 2}">selected</c:if> value="2">审核通过</option>
						<option <c:if test="${invoiceAfter.status eq 3}">selected</c:if> value="3">审核不通过</option>
					</select>
				</li> --%>
				<li>
					<label class="infor_name">业务类型</label> 
					<select class="input-middle" name="type" id="type">
						<option value="">全部</option>
						<c:forEach items="${sysList}" var="sys">
							<option value="${sys.sysOptionDefinitionId }"
								<c:if test="${sys.sysOptionDefinitionId == invoiceAfter.type}">selected="selected"</c:if>>${sys.title }</option>
						</c:forEach>
					</select>
				</li>
				<li>
					<label class="infor_name">售后人员</label> 
					<select class="input-middle" name="serviceUserId" id="serviceUserId">
						<option value="">全部</option>
						<c:forEach items="${serviceUserList }" var="su">
							<option value="${su.userId }"
								<c:if test="${su.userId == invoiceAfter.serviceUserId}">selected="selected"</c:if>>${su.username}</option>
						</c:forEach>
					</select>
				</li>
				<%-- <li>
					<div class="infor_name specialinfor">
						<select name="timeType">
							<option value="1" <c:if test="${invoiceAfter.timeType == 1 }">selected="selected"</c:if>>申请时间</option>
							<option value="2" <c:if test="${invoiceAfter.timeType == 2 }">selected="selected"</c:if>>生效时间</option>
						</select>
					</div> 
					<input class="Wdate f_left input-smaller96 m0" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')}'})" name="startTime" id="startTime" value="${invoiceAfter.startTime}" type="text" placeholder="请选择日期">
					<div class="f_left ml1 mr1 mt4">-</div> 
					<input class="Wdate f_left input-smaller96" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}'})" name="endTime" id="endTime" value="${invoiceAfter.endTime}" type="text" placeholder="请选择日期">
				</li> --%>
			</ul>
			<div class="tcenter">
				<span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" id="searchSpan">搜索</span> 
				<span class="bt-small bg-light-blue bt-bg-style mr20" onclick="reset();">重置</span>
			</div>
		</form>
	</div>
	<div class="list-page normal-list-page">
		<!-- <div class="fixdiv">
			<div class="superdiv"> -->
				<table class="table">
					<thead>
						<tr>
							<th class="wid4">序号</th>
							<th class="wid10">售后单号</th>
							<th class="wid12">对应订单号</th>
							<th>合同客户</th>
							<th class="wid12">业务类型</th>
							<th class="wid8">创建人</th>
							<th class="wid8">售后人员</th>
							<th>售后单状态</th>
							<th>退款状态</th>
							<th>收款状态</th>
							<th>退票状态</th>
							<th>开票状态</th>
							<th class="wid14">生效时间</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="list" items="${invoiceAfterList}" varStatus="num">
							<tr>
								<td>${num.count}</td>
								<td>
									<span class="font-blue addtitle" tabTitle='{"num":"view_invoice_after${list.afterSalesId}",
											"link":"./finance/after/getFinanceAfterSaleDetail.do?afterSalesId=${list.afterSalesId}&subjectType=${list.subjectType}&type=${list.type}","title":"财务售后订单"}'>
										${list.afterSalesNo}
									</span>
								</td>
								<td>
									<c:choose>
										<c:when test="${list.subjectType eq 535}"><!-- 销售 -->
											<a class="font-blue addtitle" href="javascript:void(0);" tabtitle='{"num":"viewsaleorder${list.orderId}","link":"./finance/invoice/viewSaleorder.do?saleorderId=${list.orderId}","title":"订单信息"}'>${list.orderNo}</a>
										</c:when>
										<c:when test="${list.subjectType eq 536}"><!-- 采购 -->
											<a class="font-blue addtitle" href="javascript:void(0);" tabtitle='{"num":"viewfinancebuyorder${list.orderId}","link":"./finance/buyorder/viewBuyorder.do?buyorderId=${list.orderId}","title":"订单信息"}'>${list.orderNo}</a>
										</c:when>
										<c:otherwise>
											${list.orderNo}
										</c:otherwise>
									</c:choose>
									
								</td>
								<td>
									<a class="addtitle" href="javascript:void(0);"
										tabTitle='{"num":"viewcustomer<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
											"link":"./trader/customer/baseinfo.do?traderId=${list.traderId}",
											"title":"客户信息"}'>${list.traderName}</a>
								</td>
								<td>${list.typeName}</td>
								<td>
									<c:forEach var="user" items="${userList}">
										<c:if test="${user.userId eq list.creator}">${user.username}</c:if>
									</c:forEach>
								</td>
								<td>
									<c:forEach var="user" items="${userList}">
										<c:if test="${user.userId eq list.serviceUserId}">${user.username}</c:if>
									</c:forEach>
								</td>
								<td>
									<c:choose>
										<c:when test="${list.atferSalesStatus eq 0}">待确认</c:when>
										<c:when test="${list.atferSalesStatus eq 1}">进行中</c:when>
										<c:when test="${list.atferSalesStatus eq 2}">已完结</c:when>
										<c:when test="${list.atferSalesStatus eq 3}">已关闭</c:when>
									</c:choose>
								</td>
								<td>
									<c:choose>
										<c:when test="${list.refundAmountStatus eq 0}">无退款</c:when>
										<c:when test="${list.refundAmountStatus eq 1}">未退款</c:when>
										<c:when test="${list.refundAmountStatus eq 2}">部分退款</c:when>
										<c:when test="${list.refundAmountStatus eq 3}">已退款</c:when>
									</c:choose>
								</td>
								<td>
									<c:choose>
										<c:when test="${list.receivePaymentStatus eq 0}">未收款</c:when>
										<c:when test="${list.receivePaymentStatus eq 1}">部分收款</c:when>
										<c:when test="${list.receivePaymentStatus eq 2}">全部收款</c:when>
									</c:choose>
								</td>
								<td>
									<c:if test="${list.isRefundInvoice eq 1}"><!-- 需要退票 -->
										<c:choose>
											<c:when test="${list.refundInvoiceStatus eq 0}">未退票</c:when>
											<c:when test="${list.refundInvoiceStatus eq 1}">已退票</c:when>
											<c:when test="${list.refundInvoiceStatus eq 2}">部分退票</c:when>
										</c:choose>
									</c:if>
									<c:if test="${(empty list.isRefundInvoice) or (list.isRefundInvoice eq 0)}">无退票</c:if>
								</td>
								<td>
									<c:choose>
										<c:when test="${list.openInvoiceStatus eq 1}">部分开票</c:when>
										<c:when test="${list.openInvoiceStatus eq 2}">全部开票</c:when>
										<c:otherwise>未开票</c:otherwise>
									</c:choose>
								</td>
								<td><date:date value="${list.validTime}" /></td>
							</tr>
						</c:forEach>
						<%-- <c:if test="${empty invoiceAfterList}">
							<tr>
								<td colspan="16">
									<!-- 查询无结果弹出 --> 查询无结果！请尝试使用其他搜索条件。
								</td>
							</tr>
						</c:if> --%>
					</tbody>
				</table>
				<c:if test="${empty invoiceAfterList}">
					<!-- 查询无结果弹出 -->
					<div class="noresult">查询无结果！请尝试使用其他搜索条件。</div>
				</c:if>
<!-- 			</div>
		</div> -->
		<tags:page page="${page}" />
	</div>
</div>
<%@ include file="../../common/footer.jsp"%>
