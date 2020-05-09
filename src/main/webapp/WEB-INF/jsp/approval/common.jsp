<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://com.vedeng.common.util/tags" prefix="date"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="datefmt"%>
<%@ page trimDirectiveWhitespaces="true" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<c:set var="path" value="<%=basePath%>" scope="application" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<title>${title}</title>
<!--  <link rel="stylesheet" href="<%=basePath%>static/css/content.css">-->
<link rel="stylesheet" href="<%=basePath%>static/css/general.css?rnd=<%=Math.random()%>" />
<link rel="stylesheet" href="<%=basePath%>static/css/manage.css?rnd=<%=Math.random()%>" />
<!-- 模糊搜索下拉框css引入 -->
<link rel="stylesheet" href="<%=basePath%>static/libs/searchableSelect/jquery.searchableSelect.css?rnd=<%=Math.random()%>" />

<script type="text/javascript" src='<%=basePath%>static/js/jquery.min.js?rnd=<%=Math.random()%>'></script>
<%-- <script type="text/javascript" src="<%=basePath%>static/js/jquery/validation/jquery-form.js?rnd=<%=Math.random()%>"></script> --%>
<script type="text/javascript" src='<%=basePath%>static/libs/jquery/plugins/layer/layer.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src="<%=basePath%>static/libs/jquery/plugins/DatePicker/WdatePicker.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript" charset="UTF-8" src='<%=basePath%>static/js/form.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" charset="UTF-8" src='<%=basePath%>static/js/closable-tab.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src='<%=basePath%>static/js/common.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src='<%= basePath %>static/js/movinghead.js?rnd=<%=Math.random()%>'></script>
<!-- 模糊搜索下拉框js引入 -->
<script type="text/javascript" src='<%=basePath%>static/libs/searchableSelect/jquery.searchableSelect.js?rnd=<%=Math.random()%>'></script>
<!-- 模糊搜索下拉框 -->
</head>
<body>