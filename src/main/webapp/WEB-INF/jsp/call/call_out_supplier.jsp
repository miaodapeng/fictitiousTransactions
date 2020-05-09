<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="去电|产品" scope="application" />
<%@ include file="./common/header.jsp"%>

<div class="layer-content call-layer-content">
	<!-- 标题 -->
	<div class="callcenter-title">
		<%@ include file="./common/call_out.jsp"%>
		<div class="right-title">
			<c:if test="${positType == 311 }">
				<span
					onclick="addComm('${phone}',${traderSupplier.traderId},${callOut.traderType },${callType },${orderId},${traderContactId },1,'${callOut.coid }');">新增联系</span>
			</c:if>
			<i class="iconclosetitle" onclick="window.parent.closeScreenAll();"></i>
		</div>
	</div>
	<!-- 表格信息 -->
	<div class="content-box">
		<div class="content-colum content-colum2">
			<%@ include file="./common/supplier_info.jsp"%>
			<%@ include file="./common/communicate_supplier.jsp"%>
			<%@ include file="./common/buyorder.jsp"%>
		</div>
		<div class="clear"></div>
	</div>
</div>

<%@ include file="../common/footer.jsp"%>