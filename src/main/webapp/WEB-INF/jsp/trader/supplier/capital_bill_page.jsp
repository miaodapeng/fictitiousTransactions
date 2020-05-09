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
<link rel="stylesheet" href="<%=basePath%>static/css/general.css?rnd=<%=Math.random()%>" />
<link rel="stylesheet" href="<%=basePath%>static/css/manage.css?rnd=<%=Math.random()%>" />

<script type="text/javascript" src='<%=basePath%>static/js/jquery.min.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src="<%=basePath%>static/js/jquery/validation/jquery-form.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript" src='<%=basePath%>static/libs/jquery/plugins/layer/layer.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src="<%=basePath%>static/libs/jquery/plugins/DatePicker/WdatePicker.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript" charset="UTF-8" src='<%=basePath%>static/js/form.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" charset="UTF-8" src='<%=basePath%>static/js/closable-tab.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src='<%= basePath %>static/js/movinghead.js?rnd=<%=Math.random()%>'></script>
</head>
<body>
<div class="parts">
			<div class="title-container">
				<div class="table-title nobor">资金流水</div>
			</div>
			<table class="table">
				<thead>
					<tr>
						<th>记账编号</th>
						<th>交易时间</th>
						<th>交易金额</th>
						<th>业务类型</th>
						<th>交易主体</th>
						<th>交易方式</th>
						<th>业务单据</th>
						<th>交易备注</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty capitalBill}">
						<c:forEach items="${capitalBill}" var="acb">
							<tr>
								<td>${acb.capitalBillNo}</td>
								<td>
									<c:if test="${acb.traderTime != 0}">
										<date:date value="${acb.traderTime}" />
									</c:if>
								</td>
								<td><fmt:formatNumber type="number" value="${acb.amount}" pattern="0.00" maxFractionDigits="2" /></td>
								<td>
									<c:if test="${acb.capitalBillDetail.bussinessType eq 525}">订单付款</c:if>
									<c:if test="${acb.capitalBillDetail.bussinessType eq 526}">订单收款</c:if>
									<c:if test="${acb.capitalBillDetail.bussinessType eq 531}">退款</c:if>
									<c:if test="${acb.capitalBillDetail.bussinessType eq 532}">资金转移</c:if>
									<c:if test="${acb.capitalBillDetail.bussinessType eq 533}">信用还款</c:if>
								</td>
								<td>
									<c:if test="${acb.traderSubject == 1}">
		                        		对公
		                        	</c:if> 
		                        	<c:if test="${acb.traderSubject == 2}">
		                        		对私
		                        	</c:if>
		                        </td>
								<td>
									<c:if test="${acb.traderMode eq 520}">支付宝</c:if>
									<c:if test="${acb.traderMode eq 521}">银行</c:if>
									<c:if test="${acb.traderMode eq 522}">微信</c:if>
									<c:if test="${acb.traderMode eq 522}">现金</c:if>
									<c:if test="${acb.traderMode eq 527}">信用支付</c:if>
									<c:if test="${acb.traderMode eq 528}">余额支付</c:if>
									<c:if test="${acb.traderMode eq 529}">退还信用</c:if>
									<c:if test="${acb.traderMode eq 530}">退还余额</c:if>
								</td>
								<td>
									<c:if test="${acb.capitalBillDetail.orderType eq 2 }">
										<a class="addtitle1" href="javascript:void(0);" tabTitle='{"num":"viewbuyorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
										"link":"<%= basePath %>/order/buyorder/viewBuyordersh.do?buyorderId=${acb.capitalBillDetail.relatedId}","title":"订单信息"}'>${acb.capitalBillDetail.orderNo}</a>
									</c:if>
									<c:if test="${acb.capitalBillDetail.orderType eq 3 }">
										<a class="addtitle1" href="javascript:void(0);" tabTitle='{"num":"viewbuyorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
										"link":"<%= basePath %>/order/buyorder/viewAfterSalesDetail.do?afterSalesId=${acb.capitalBillDetail.relatedId}","title":"订单信息"}'>${acb.capitalBillDetail.orderNo}</a>
									</c:if>
								
								</td>
								<td class="text-left">${acb.comments}</td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
			<c:if test="${empty capitalBill }">
				<!-- 查询无结果弹出 -->
       			<div class="noresult">查询无结果！</div>
			</c:if>
		</div>
		<tags:page page="${page}"/>
<%@ include file="../../common/footer.jsp"%>