
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="确认客户" scope="application" />	
<%@ include file="../../common/common.jsp"%>

<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/order/bussinesschance/confirm_customer.js?rnd=<%=Math.random()%>"></script>
<div class="formpublic">
    
	<form action="${pageContext.request.contextPath}/order/bussinesschance/getCustomersByName.do" id="myform1" method="post">
		
	<div class='mb10 overflow-hidden'>
		<div class="infor_name">
			<span>*</span>客户名称 
		</div>
		<div class='f_left'>
			<div class='inputfloat'>
					<input type="text" name="customerName" id="name" value="${customerName}" class="input-large">
				<span class="bg-light-blue bt-bg-style bt-small"  id="search">搜索</span>
				<input type="hidden" name="bussinessChanceId" id="bussinessChanceId" value="${bussinessChance.bussinessChanceId}"/>
				<input type="hidden" name="traderCustomerId" value="${traderCustomer.traderCustomerId}" id="">
				<input type="hidden" name="traderId" value="${traderCustomer.traderId}" id="">
			</div>
		</div>
	</div>
	</form>
	<div>
		<table class="table table-bordered table-striped table-condensed table-centered" id="cus">
			<thead>
				<tr>
					<th>客户名称</th>
					<th>地区</th>
					<th>创建时间</th>
					<th>归属销售</th>
					<th>选择</th>
				</tr>
			</thead>
			<tbody class="employeestate">
				<c:if test="${not empty list}">
					<c:forEach items="${list}" var="traderCustomerVo" varStatus="status">
						<tr>
							<td class="text-left">${traderCustomerVo.name }</td>
							<td>${traderCustomerVo.address }</td>
							<td><date:date value ="${traderCustomerVo.addTime} "/></td>
							<td>${traderCustomerVo.personal}</td>
							<td>
								<c:if test="${traderCustomerVo.isView eq 1}">
									<a class='setWidth' 
									href="${pageContext.request.contextPath}/order/bussinesschance/viewConfirmCustomer.do?bussinessChanceId=${bussinessChance.bussinessChanceId}&traderCustomerId=${traderCustomerVo.traderCustomerId}&traderId=${traderCustomerVo.traderId}">选择</a>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
		<c:if test="${empty list }">
			<!-- 查询无结果弹出 -->
         	<div class="noresult"> 查询无结果！请尝试使用其他搜索条件或
         		<span class="font-blue addtitle1" tabTitle='{"num":"customer","link":"./trader/customer/add.do","title":"新增客户"}'>新增客户</span></div>
		</c:if>
		<tags:page page="${page}"/>
	</div>
    </div>
</body>

</html>
