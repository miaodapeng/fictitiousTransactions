<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/base.css?rnd=<%=Math.random()%>">
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/general.css?rnd=<%=Math.random()%>">
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/manage.css?rnd=<%=Math.random()%>">
<div class="noright">
    <div class="noright-operate">
        <div class="pl180">您没有操作权限！<br/>
    	   <span class="noright-jump"><a href="#">您暂时还未开通此权限，请联系研发部开通权限。</a></span>
        </div>
     <div class="noright-img"><img src="${pageContext.request.contextPath}/static/images/noright.jpg"/></div>
    </div>
</div>