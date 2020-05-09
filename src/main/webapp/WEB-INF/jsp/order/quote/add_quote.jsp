<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增报价" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%=basePath%>/static/js/order/quote/add_quote.js?rnd=<%=Math.random()%>'></script>
<div class="formpublic formpublic1">
	<div>
		<div class="title-container">
			<div class="table-title ">客户相关信息</div>
		</div>
		<table
			class="table table-bordered table-striped table-condensed table-centered">
			<tbody>
				<tr>
					<td class="table-smaller">客户名称</td>
					<td>
						<div class="customername pos_rel">
							<span class="font-blue mr4"> 
								<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewcustomer${customer.traderCustomerId}",
									"link":"./trader/customer/baseinfo.do?traderCustomerId=${customer.traderCustomerId}&traderId=${customer.traderId}","title":"客户信息"}'>
									${customer.name}
								</a>
							</span> 
							<i class="iconbluemouth"></i>
							<div class="pos_abs customernameshow">
								报价次数：${customer.quoteCount} <br /> 交易次数：${customer.buyCount} <br />
								交易金额：${customer.buyMoney} <br /> 上次交易时间：
								<date:date value="${customer.lastBussinessTime}" />
								<br> 归属销售：${customer.ownerSale }
							</div>
						</div>
					</td>
					<td class="table-smaller">地区</td>
					<td>${customer.address}</td>
				</tr>
				<tr>
					<td class="table-small">客户类型</td>
					<td>${customer.customerTypeStr}</td>
					<td class="table-small">客户性质</td>
					<td>
						<c:if test="${customer.customerNature eq 465}">分销</c:if>
						<c:if test="${customer.customerNature eq 466}">终端</c:if>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="formtitle">请确认客户相关信息</div>
	<form method="post" id="addQuoteForm" action="./saveQuote.do">
		<input type="hidden" name="formToken" value="${formToken}"/>
		<!-- 商机ID -->
		<input type="hidden" name="bussinessChanceId" value="${quote.bussinessChanceId}" />
		<!-- 客户(交易者)ID -->
		<input type="hidden" name="traderId" value="${quote.traderId}" />
		<!-- 客户(交易者)名称 -->
		<input type="hidden" name="traderName" value="${customer.name}" />
		<!-- 客户(交易者)地区 -->
		<input type="hidden" name="area" value="${customer.address}" />
		<!-- 客户(交易者)类型 -->
		<input type="hidden" name="customerType" value="${customer.customerType}" />
		<!-- 客户(交易者)性质 -->
		<input type="hidden" name="customerNature" value="${customer.customerNature}" />
		<!-- 客户（等级） -->
		<input type="hidden" name="customerLevel" value="${customer.customerLevel}" />
		<!-- 标记报价入口 -->
		<input type="hidden" name="quoteSource" value="bussiness" /><!-- 报价商机入口 -->
		<ul>
			<li>
				<div class="infor_name">
					<span>*</span> <label>联系人</label>
				</div>
				<div class="f_left">
					<div class="form-blanks">
						<input type="hidden" name="traderContactId" id="traderContactId" />
						<input type="hidden" name="traderContactName" id="traderContactName" /> 
						<input type="hidden" name="mobile" id="mobile" /> 
						<input type="hidden" name="telephone" id="telephone" /> 
						<select class="input-large " name="traderContactInfo" id="traderContactInfo">
							<option value="">请选择联系人</option>
							<c:forEach var="list" items="${userList}" varStatus="status">
								<option
									value="${list.traderContactId}!@#${list.name}!@#${list.mobile}!@#${list.telephone}">${list.name}/${list.mobile}/${list.telephone}</option>
							</c:forEach>
						</select>
					</div>
				</div>
			</li>
			<li>
				<div class="infor_name">
					<label>联系地址</label>
				</div>
				<div class="f_left">
					<input type="hidden" name="traderAddressId" id="traderAddressId" />
					<select class="input-large f_left" name="address" id="address">
						<option value="">请选择联系地址</option>
						<c:forEach var="list" items="${addressList}" varStatus="status">
							<option id="${list.traderAddress.traderAddressId}"
								value="${list.area} ${list.traderAddress.address}">${list.area}
								${list.traderAddress.address}</option>
						</c:forEach>
					</select>
				</div>
			</li>
			<li>
				<div class="infor_name mt0">
					<span>*</span> <label>联系人情况</label>
				</div>
				<div class="f_left inputfloat">
					<ul>
						<li><input type="radio" name="isPolicymakerRad"
							id="isPolicymaker_y" value="1"><label>采购关键人</label></li>
						<li><input type="radio" name="isPolicymakerRad"
							id="isPolicymaker_n" value="0"><label>非采购关键人</label></li>
					</ul>
					<input type="hidden" name="isPolicymaker" id="isPolicymaker" />
				</div>
			</li>
			<li>
				<div class="infor_name mt0">
					<span>*</span> <label>采购方式</label>
				</div>
				<div class="f_left inputfloat">
					<ul>
						<c:forEach var="list" items="${purchasingTypeList}"
							varStatus="status">
							<li><input type="radio" name="purchasingTypeRad"
								value="${list.sysOptionDefinitionId}"><label>${list.title}</label></li>
						</c:forEach>
					</ul>
					<input type="hidden" name="purchasingType" id="purchasingType" />
				</div>
			</li>
			<li>
				<div class="infor_name mt0">
					<span>*</span> <label>付款条件</label>
				</div>
				<div class="f_left inputfloat">
					<ul>
						<c:forEach var="list" items="${paymentTermList}"
							varStatus="status">
							<li><input type="radio" name="paymentTermRad"
								value="${list.sysOptionDefinitionId}"><label>${list.title}</label></li>
						</c:forEach>
					</ul>
					<input type="hidden" name="paymentTerm" id="paymentTerm" />
				</div>
			</li>
			<li>
				<div class="infor_name mt0">
					<span>*</span> <label>采购时间</label>
				</div>
				<div class="f_left inputfloat">
					<ul>
						<c:forEach var="list" items="${purchasingTimeList}"
							varStatus="status">
							<li><input type="radio" name="purchasingTimeRad"
								value="${list.sysOptionDefinitionId}"><label>${list.title}</label></li>
						</c:forEach>
					</ul>
					<input type="hidden" name="purchasingTime" id="purchasingTime" />
				</div>
			</li>
			<li>
				<div class="infor_name">
					<label>项目进展情况</label>
				</div>
				<div class="f_left inputfloat">
					<input type="text" placeholder="请填写客户项目信息、预算和进展情况等"
						class="input-largest" name="projectProgress" id="projectProgress">
				</div>
			</li>
		</ul>
		<!-- <div class="line"></div> -->
		<input type="hidden" id="customerNature" value="${customer.customerNature}">
		<%-- <c:choose>
			<c:when test="${customer.customerNature != 466}">
				<!-- 466终端用户：验证非终端用户 -->
				<div class="formtitle">请确认终端信息</div>
				<span id="searchTerminalInfo">
					<ul>
						<li>
							<div class="infor_name">
								<label>终端名称</label>
							</div>
							<div class="f_left inputfloat">
								<input type="text" placeholder="请输入终端名称" class="input-largest" name="searchTraderName" id="searchTraderName"> 
								<label class="bt-bg-style bg-light-blue bt-small" onclick="searchTerminal();" id="errorMes">搜索</label> 
								<span style="display: none;"> <!-- 弹框 -->
									<div class="title-click nobor  pop-new-data" id="terminalDiv"></div>
								</span>
							</div>
						</li>
					</ul>
				</span>
				<span id="showTerminalInfo" style="display: none">
					<ul>
						<li>
							<div class="infor_name ">
								<label>终端名称</label>
							</div>
							<div class="f_left">
								<span class="mr8" id="terminalTraderNameDiv"></span> 
								<label class="bt-bg-style bg-light-blue bt-small" onclick="agingSearchTerminal();">重新搜索</label>
							</div>
						</li>
						<li>
							<div class="infor_name mt0 ">
								<label>终端类型</label>
							</div>
							<div class="f_left">
								<span class="mr8" id="terminalTraderTypeDiv"></span>
							</div>
						</li>
						<li>
							<div class="infor_name  mt0">
								<span>*</span> <label>销售区域</label>
							</div>
							<div class="f_left">
								<span class="mr8" id="salesAreaDiv"></span>
							</div>
						</li>
					</ul> 
					<input type="hidden" name="terminalTraderName" id="terminalTraderName" /> 
					<input type="hidden" name="terminalTraderType" id="terminalTraderType" /> 
					<input type="hidden" name="salesArea" id="salesArea" /> 
					<input type="hidden" name="salesAreaId" id="salesAreaId" /> 
					<input type="hidden" name="terminalTraderId" id="terminalTraderId" /> 
					<input type="hidden" name="terminalTraderType" id="terminalTraderType" />
				</span>
			</c:when>
		</c:choose> --%>
		<div class="add-tijiao tcenter mt10">
			<button type="button" class="bt-bg-style bg-deep-green" onclick="subQuote();">下一步</button>
		</div>
	</form>
</div>
<%@ include file="../../common/footer.jsp"%>