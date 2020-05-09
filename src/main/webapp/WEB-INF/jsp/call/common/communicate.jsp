<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://com.vedeng.common.util/tags" prefix="date"%>     
<div class="content-item">
	<div class="title">近期沟通</div>
	<table class="table table-td-border1">
		<tbody>
			<tr class="table-header">
				<td>时间</td>
				<td>人员</td>
			</tr>
			<c:choose>
				<c:when test="${not empty traderCustomer.communicateRecordList }">
					<c:forEach items="${traderCustomer.communicateRecordList }" var="comm">
					<tr>
						<td><date:date value ="${comm.addTime} "/></td>
						<td>${comm.creatorName }</td>
					</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
				<tr>
					<td colspan="2">暂无记录</td>
				</tr>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
</div>