<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  	
<%@ taglib uri="http://com.vedeng.common.util/tags" prefix="date"%>	
<div class="content-item">
	<div class="title">近期报价</div>
	<table class="table table-td-border1">
		<tbody>
			<tr class="table-header">
				<td>订单号</td>
				<td>报价时间</td>
				<td>报价金额</td>
				<td>销售人员</td>
				<td>跟单次数</td>
			</tr>
			<c:choose>
				<c:when test="${not empty traderCustomer.quoteorderList }">
					<c:forEach items="${traderCustomer.quoteorderList }" var="quoteorder">
					<tr>
						<td><a target="_blank" href="${pageContext.request.contextPath}/order/quote/getQuoteDetail.do?quoteorderId=${quoteorder.quoteorderId}&viewType=3">${quoteorder.quoteorderNo }</a></td>
						<td><date:date value ="${quoteorder.validTime} "/></td>
						<td>￥${quoteorder.totalAmount }</td>
						<td>${quoteorder.salesName }</td>
						<td>${quoteorder.communicateNum}</td>
					</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
				<tr>
					<td colspan="5">暂无记录</td>
				</tr>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
</div>