<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="来电|销售" scope="application" />
<%@ include file="./common/header.jsp"%>

<div class="layer-content call-layer-content">
	<!-- 标题 -->
	<div class="callcenter-title">
		<%@ include file="./common/call_in.jsp"%>
		<div class="right-title">
				<span
					onclick="addComm('${phone}',${traderCustomer.traderId},${callOut.traderType },0,0,0,0,'${callOut.coid }');">新增联系</span>
				<span
					onclick="showEnquiry('${phone}');">查看商机</span>
			<i class="iconclosetitle" onclick="window.parent.closeScreenAll();"></i>
		</div>
	</div>
	<!-- 表格信息 -->
	<div class="content-box">
		<div class="content-colum content-colum2">
			<%@ include file="./common/customer_info.jsp"%>
			<%@ include file="./common/communicate.jsp"%>
			<%@ include file="./common/bussiness_chance.jsp"%>
			<%@ include file="./common/quoteorder.jsp"%>
			<%@ include file="./common/saleorder.jsp"%>


		</div>
		<div class="clear"></div>
	</div>
</div>

<%@ include file="../common/footer.jsp"%>
