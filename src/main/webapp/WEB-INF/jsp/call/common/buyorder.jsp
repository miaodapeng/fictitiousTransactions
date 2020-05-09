<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  	
<%@ taglib uri="http://com.vedeng.common.util/tags" prefix="date"%>	
<div class="content-item">
	<div class="title">进行中的订单</div>
	<table class="table table-td-border1">
		<tbody>
			<tr class="table-header">
				<td>订单号</td>
				<td>时间</td>
				<td>产品人员</td>
				<td>总额</td>
				<td>已付</td>
			</tr>
			<c:choose>
				<c:when test="${not empty traderSupplier.buyorderList }">
					<c:forEach items="${traderSupplier.buyorderList }" var="buyorder">
					<tr>
						<td><a target="_blank" href="${pageContext.request.contextPath}/order/buyorder/viewBuyorder.do?buyorderId=${buyorder.buyorderId}">${buyorder.buyorderNo }</a></td>
						<td><date:date value ="${buyorder.validTime} "/></td>
						<td>${buyorder.buyPerson }</td>
						<td>￥${buyorder.totalAmount }</td>
						<td></td>
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