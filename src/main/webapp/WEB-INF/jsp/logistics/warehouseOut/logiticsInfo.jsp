<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="物流轨迹" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="main-container">
	<div class="parts">
		<table class="table">
			<tbody>
				<c:forEach var="list" items="${ldList}" varStatus="num">
					<tr>
					    <td class="wid12">${list.dateTime}</td>
						<td class="wid10"> ${list.timeMillis}</td>
						<td>${list.detail}</td>
					</tr>
				</c:forEach>
				<c:if test="${empty ldList}">
					<tr>
						<td colspan="3">暂无信息</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>
</div>
<%@ include file="../../common/footer.jsp"%>