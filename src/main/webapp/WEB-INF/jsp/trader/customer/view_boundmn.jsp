<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="基本信息" scope="application" />
<%@ include file="../../common/common.jsp"%>
<link href="<%= basePath %>/static/css/meinian/layui.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="<%= basePath %>static/js/order/meinian/layui.js"></script>
<script type="text/javascript" src="<%= basePath %>static/js/order/meinian/nicePage.js"></script>
<script type="text/javascript" src="<%= basePath %>static/js/order/meinian/meinian.js"></script>
<script type="text/javascript" src='<%= basePath %>static/js/customer/view_baseinfo.js?rnd=<%=Math.random()%>'></script>


<div class="content">
	<input type="hidden" name="traderCustomerId" id="traderCustomerId" style="display:none" value="${traderCustomerId}" />
	<center style="padding-top: 40px">
		<a style="font-size: 28px;font-weight: 500">绑定客户</a>
		</br>
		<div style="width: 80%;padding-top: 20px">
			<!--以下为两个必须div元素-->
			<div id="table"></div>
			<div id="pageBar"></div>
		</div>

	</center>

</div>
<%@ include file="../../common/footer.jsp"%>

