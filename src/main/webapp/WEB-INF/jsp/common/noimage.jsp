<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="没有找到图片" scope="application" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/base.css?rnd=<%=Math.random()%>">
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/general.css?rnd=<%=Math.random()%>">
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/manage.css?rnd=<%=Math.random()%>">
<div class="fourzerofour">
	<div class="false">
		${message }<br /> <span class="jump">请联系管理员！</span>
	</div>
	<div class="false-img">
		<img src="${pageContext.request.contextPath}/static/images/false404.jpg" />
	</div>
</div>