<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增报价</title>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%=basePath%>/static/js/order/quote/edit_quote.js?rnd=<%=Math.random()%>'></script>
</head>
<body>
	<div class="formpublic formpublic1">
		<form method="post" id="editQuoteForm" action="updateQuoteCustomer.do">
			<input type="hidden" name="beforeParams" value='${beforeParams}'><!-- 日志 -->
			<input type="hidden" name="quoteorderId" value="${quote.quoteorderId}">
			<ul>
				<li>
					<div class="infor_name">
						<span>*</span> <label>联系人</label>
					</div>
					<div class="f_left">
						<input type="hidden" name="traderContactId" id="traderContactId"/>
						<input type="hidden" name="traderContactName" id="traderContactName"/>
						<input type="hidden" name="mobile" id="mobile"/>
						<input type="hidden" name="telephone" id="telephone"/>
						<select class="input-large f_left" name="traderContactInfo" id="traderContactInfo">
							<option value="">请选择联系人</option>
							<c:forEach var="list" items="${userList}" varStatus="status">
								<option value="${list.traderContactId}!@#${list.name}!@#${list.mobile}!@#${list.telephone}"
									<c:if test="${quote.traderContactId == list.traderContactId}">selected</c:if>
								>${list.name} / ${list.mobile} / ${list.telephone}</option>
							</c:forEach>
						</select>
						<!-- <div class="font-red"></div> -->
					</div>
				</li>
				<li>
					<div class="infor_name">
						<label>联系地址</label>
					</div>
					<div class="f_left">
						<input type="hidden" name="traderAddressId" id="traderAddressId"/>
						<select class="input-large f_left" name="address" id="address">
							<option value="">请选择联系地址</option>
							<c:forEach var="list" items="${addressList}" varStatus="status">
								<option id="${list.traderAddress.traderAddressId}" value="${list.area} ${list.traderAddress.address}"
									<c:if test="${quote.traderAddressId eq list.traderAddress.traderAddressId}">selected</c:if>
								>${list.area} ${list.traderAddress.address}</option>
							</c:forEach>
						</select>
					</div>
				</li>
				<li>
					<div class="infor_name mt0">
						<span>*</span>
						<label>联系人情况</label>
					</div>
					<div class="f_left inputfloat">
						<ul>
							<li><input type="radio" name="isPolicymakerRad" id="isPolicymaker_y" value="1" <c:if test="${quote.isPolicymaker eq 1}">checked</c:if>><label>采购关键人</label></li>
							<li><input type="radio" name="isPolicymakerRad" id="isPolicymaker_n" value="0" <c:if test="${quote.isPolicymaker eq 0}">checked</c:if>><label>非采购关键人</label></li>
						</ul>
						<input type="hidden" name="isPolicymaker" id="isPolicymaker"/>
					</div>
				</li>
				<li>
					<div class="infor_name mt0">
						<span>*</span>
						<label>采购方式</label>
					</div>
					<div class="f_left inputfloat">
						<ul>
							<c:forEach var="list" items="${purchasingTypeList}" varStatus="status">
								<li><input type="radio" name="purchasingTypeRad" value="${list.sysOptionDefinitionId}" <c:if test="${quote.purchasingType eq list.sysOptionDefinitionId}">checked</c:if>><label>${list.title}</label></li>
							</c:forEach>
						</ul>
						<input type="hidden" name="purchasingType" id="purchasingType"/>
					</div>
				</li>
				<li>
					<div class="infor_name mt0">
						<span>*</span>
						<label>付款条件</label>
					</div>
					<div class="f_left inputfloat">
						<ul>
							<c:forEach var="list" items="${paymentTermList}" varStatus="status">
								<li><input type="radio" name="paymentTermRad" value="${list.sysOptionDefinitionId}" <c:if test="${quote.paymentTerm eq list.sysOptionDefinitionId}">checked</c:if>><label>${list.title}</label></li>
							</c:forEach>
						</ul>
						<input type="hidden" name="paymentTerm" id="paymentTerm"/>
					</div>
				</li>
				<li>
					<div class="infor_name mt0">
						<span>*</span> <label>采购时间</label>
					</div>
					<div class="f_left inputfloat">
						<ul>
							<c:forEach var="list" items="${purchasingTimeList}" varStatus="status">
								<li><input type="radio" name="purchasingTimeRad" value="${list.sysOptionDefinitionId}" <c:if test="${quote.purchasingTime eq list.sysOptionDefinitionId}">checked</c:if>><label>${list.title}</label></li>
							</c:forEach>
						</ul>
						<input type="hidden" name="purchasingTime" id="purchasingTime"/>
					</div>
				</li>
				<li>
					<div class="infor_name">
						<label>项目进展情况</label>
					</div>
					<div class="f_left inputfloat">
						<input type="text" placeholder="请填写客户项目信息、预算和进展情况等" class="input-largest" name="projectProgress" id="projectProgress" value="${quote.projectProgress}">
					</div>
				</li>
			</ul>
			
			<div class="add-tijiao tcenter mt10">
				<button type="button" class="bt-bg-style bg-deep-green" onclick="updateCustomer();">提交</button>
				<button type="button" class="dele" id="cancle">取消</button>
			</div>
		</form>
	</div>
</body>
</html>