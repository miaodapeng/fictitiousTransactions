<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="去电|销售" scope="application" />
<%@ include file="./common/header.jsp"%>

<div class="layer-content call-layer-content">
	<!-- 标题 -->
	<div class="callcenter-title">
		<%@ include file="./common/call_out.jsp"%>
		<div class="right-title">
			<c:if test="${positType == 310 }">
				<span
					onclick="addComm('${phone}',${traderCustomer.traderId},${callOut.traderType },${callType },${orderId},${traderContactId },1,'${callOut.coid }');">新增联系</span>
			</c:if>
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