<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>贝登CRM</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/frame.css?rnd=<%=Math.random()%>">
	<script type="text/javascript" src="http://static.zhaoqixie.com/libs/jquery/jquery-1.11.3.min.js?rnd=<%=Math.random()%>"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/frame.js?rnd=<%=Math.random()%>"></script>
</head>
<body class="frame-body">
	<div class="frame-header">
    	
		<div class="logo-container">
        	<a href="./" target="_top">	
			<h1 id="logo" class="iconfont-vedeng"></h1>
            </a>
		</div>
        
		<div class="user-box">
			<a href="./" target="main-frame" class="head-avatar"><span class="img avatar"><img src="${pageContext.request.contextPath}/static/images/demo_avatar.jpg" alt=""></span>Admin</a>
			<a href="./" target="main-frame" class="action-btn user-setting iconfont-settings"></a>
			<a href="./" target="main-frame" class="action-btn log-out iconfont-backdelete"></a>
		</div>
	</div>
    <div id="hidden-top-bar" class="hidden-top-bar active">&#9650</div>
</body>
</html>