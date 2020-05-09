<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>操作失败</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/content.css?rnd=<%=Math.random()%>" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/general.css?rnd=<%=Math.random()%>" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/manage.css?rnd=<%=Math.random()%>" />
    <c:if test="${not empty url }">
    <meta http-equiv="refresh" content ="3;url=${url}">
    </c:if>
</head>

<body>
    <div class="operate">
        <div class="false">
                            操作失败！<br/>
            <c:if test="${not empty url }">
				<span class="jump">如果浏览器没有跳转，请点击<a href="${url}">跳转链接</a></span>
            </c:if>
            <c:if test="${not empty message}">
				<span class="jump">${message}</span>
            </c:if>
            
        </div>
        <div class="false-img"><img src="${pageContext.request.contextPath}/static/images/operatefalse.jpg" /></div>
    </div>
</body>

</html>
    