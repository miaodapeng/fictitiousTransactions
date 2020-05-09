<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  	
<%@ taglib uri="http://com.vedeng.common.util/tags" prefix="date"%>
<div class="content-item">
	<div class="title">近期商机</div>
	<table class="table table-td-border1">
		<tbody>
			<tr class="table-header">
				<td>商机编号</td>
				<td>商机创建时间</td>
				<td>产品名称</td>
				<td>销售人员</td>
			</tr>
			<c:choose>
				<c:when test="${not empty traderCustomer.bussinessChanceList }">
					<c:forEach items="${traderCustomer.bussinessChanceList }" var="bussinessChance">
					<tr>
						<td><a target="_blank" href="${pageContext.request.contextPath}/order/bussinesschance/toSalesDetailPage.do?bussinessChanceId=${bussinessChance.bussinessChanceId}&traderId=${traderCustomer.traderId }&traderCustomerId=${traderCustomer.traderCustomerId }">${bussinessChance.bussinessChanceNo }</a></td>
						<td class="date-column"><date:date value ="${bussinessChance.addTime} "/></td>
						<td>${bussinessChance.goodsName }</td>
						<td>${bussinessChance.salerName }</td>
					</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
				<tr>
					<td colspan="4">暂无记录</td>
				</tr>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
</div>