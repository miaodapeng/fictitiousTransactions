<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="售后开票审核列表" scope="application" />
<%@ include file="../../common/common.jsp"%>
<%-- <script type="text/javascript" src='<%=basePath%>static/js/finance/invoice/list_after_open_invoice_apply.js'></script> --%>
	<div class="main-container">
		<div class="list-pages-search">
			<form method="post" id="search" action="<%=basePath%>finance/invoice/getAfterInvoiceApplyVerifyListPage.do">
				<ul>
					<li>
						<label class="infor_name">发票类型</label>
						<select class="input-middle" name="invoiceType" id="invoiceType">
							<option value="">全部</option>
							<c:forEach var="list" items="${invoiceTypeList}" varStatus="status">
								<option value="${list.sysOptionDefinitionId}" <c:if test="${list.sysOptionDefinitionId eq invoiceApply.invoiceType}">selected</c:if>>${list.title}</option>
							</c:forEach>
						</select>
					</li>
					<li>
						<label class="infor_name">订单号</label>
						<input type="text" class="input-middle" name="afterSalesNo" id="afterSalesNo" value="${invoiceApply.afterSalesNo}" />
					</li>
					<li>
						<label class="infor_name">客户公司</label>
						<input type="text" class="input-middle" name="traderName" id="traderName" value="${invoiceApply.traderName}" />
					</li>
				</ul>
				<div class="tcenter">
					<span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" >搜索</span>
					<span class="bt-small bg-light-blue bt-bg-style mr20" onclick="reset();">重置</span>
				</div>
			</form>
		</div>
		<div class="list-page normal-list-page">
			<table class="table table-bordered table-striped table-condensed table-centered">
				<thead>
					<tr>
						<th class="wid5">序号</th>
						<th class="wid20">订单号</th>
						<th>客户名称</th>
						<th class="wid20">票种</th>
						<th class="wid10">开票方式</th>
						<th class="wid15">订单金额</th>
						<th class="wid20">申请时间</th>
						<th class="wid15">申请人</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="list" items="${openInvoiceApplyList}" varStatus="num">
						<tr>
							<td>${num.count}</td>
							<td>
								<a class="addtitle" href="javascript:void(0);" tabtitle='{"num":"viewfinancesaleorder${list.relatedId}","link":"./finance/after/getFinanceAfterSaleDetail.do?afterSalesId=${list.relatedId}&eFlag=cw","title":"订单信息"}'>${list.afterSalesNo}</a>
								${(list.verifyStatus != 1 && fn:contains(list.verifyUsername,user.userId))?"<font color='red'>[审]</font>":""}
							</td>
							<td>
								<a class="addtitle" href="javascript:void(0);" tabtitle='{"num":"viewfinancecustomer${list.traderId}", "link":"./trader/customer/baseinfo.do?traderId=${list.traderId}", "title":"客户信息"}'>${list.traderName}</a>
							</td>
							<td>${list.invoiceTypeStr}</td>
							<td>
								<c:choose>
									<c:when test="${list.isAuto eq 1}"><span style="color:red;">手动</span></c:when>
									<c:when test="${list.isAuto eq 2}">自动</c:when>
									<c:when test="${list.isAuto eq 3}">电子</c:when>
									<c:otherwise>--</c:otherwise>
								</c:choose>
							</td>
							<td><fmt:formatNumber type="number" value="${list.totalAmount}" pattern="0.00" maxFractionDigits="2" /></td>
							<td><date:date value="${list.addTime}" /></td>
							<td>
								<c:forEach varStatus="userNum" var="user" items="${userList}">
									<c:if test="${list.creator eq user.userId}">
										${user.username}
									</c:if>
								</c:forEach>
							</td>
						</tr>
					</c:forEach>
					<c:if test="${empty openInvoiceApplyList}">
						<tr>
							<td colspan="8">
								<!-- 查询无结果弹出 --> 查询无结果！请尝试使用其他搜索条件。
							</td>
						</tr>
					</c:if>
				</tbody>
			</table>
			<tags:page page="${page}" />
			<div class="clear"></div>
		</div>
	</div>
<%@ include file="../../common/footer.jsp"%>
