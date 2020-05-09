<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${not empty afterSalesVo.attachmentList }">
	<c:forEach items="${afterSalesVo.attachmentList }" var="att">
		<c:if test="${fn:startsWith(att.domain,'http')}">
			<a href="${att.domain}${att.uri}" target="_blank">
				<img src="${att.domain}${att.uri}" style="max-width:100px;max-height:100px">
			</a>&nbsp;&nbsp;
		</c:if>
		<c:if test="${not fn:startsWith(att.domain,'http')}">
			<a href="http://${att.domain}${att.uri}" target="_blank">
				<img src="http://${att.domain}${att.uri}" style="max-width:100px;max-height:100px">
			</a>&nbsp;
		</c:if>&nbsp;
	</c:forEach>
</c:if>
