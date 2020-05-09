<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<html lang="en">
<head>
    <meta charset="UTF-8">
    <!-- <title>操作成功</title> -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/content.css?rnd=<%=Math.random()%>" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/general.css?rnd=<%=Math.random()%>" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/manage.css?rnd=<%=Math.random()%>" />
    <c:if test="${not empty url }">
    <meta http-equiv="refresh" content ="1;url=${url}">
    </c:if>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.min.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/static/js/form.js?rnd=<%=Math.random()%>"></script>
	<script type="text/javascript">
		var flag = false;
		//是否关闭自己--是否刷新自己--是否刷新父级页面
		var closeSelf = false,freshSelf = false,freshFrontPage = false;
		$(document).ready(function(){
			var refresh = ('${refresh}');//控制器传入参数
			if(refresh != undefined && refresh != ""){
				var arr = refresh.split("_");
				if(arr.length==3){
					setTimeout(function(){
						flag = true;
						closeSelf = (arr[0]=="false"?false:true);
						freshSelf = (arr[1]=="false"?false:true);
						freshFrontPage = (arr[2]=="false"?false:true);
						pagesContrlpages(closeSelf,freshSelf,freshFrontPage);
					}, 500);
				}
			}
		});
		
		function jumpUrl(url){
			if(flag){
				pagesContrlpages(closeSelf,freshSelf,freshFrontPage);
			}
			self.location=url; 
		}
	</script>
</head>

<body>
    <div class="operate">
        <div class="success">
        	操作成功！<br/>
        	<c:if test="${not empty url }">
        	<span class="jump">如果浏览器没有跳转，请点击<a href="javaScript:void();" onclick="jumpUrl('${url}');">跳转链接</a></span>
        	</c:if>
        </div>
        <div class="success-img"><img src="${pageContext.request.contextPath}/static/images/success404.jpg"/></div>
    </div>
</body>

</html>
