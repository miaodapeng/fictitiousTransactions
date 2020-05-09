<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="采购售后列表" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript"></script>
<div class="searchfunc">
	<form method="post" id="search"
		action="<%=basePath%>/aftersales/order/buyorderaftersaleslist.do">
		<ul>
			<li>
				<label class="infor_name">售后单号</label>
				<input type="text" class="input-middle" name="afterSalesNo" id="afterSalesNo" value="${afterSalesVo.afterSalesNo}" />
			</li>
			<li>
				<label class="infor_name">采购单号</label> 
				<input type="text" class="input-middle" name="orderNo" id="orderNo" value="${afterSalesVo.orderNo}" />
			</li>
			<li>
				<label class="infor_name">供应商名称</label> 
				<input type="text" class="input-middle" name="traderName" id="traderName" value="${afterSalesVo.traderName}" />
			</li>
			<li>
				<label class="infor_name">订单状态</label> 
				<select class="input-middle" name="atferSalesStatus" id="atferSalesStatus">
					<option value="">全部</option>
					<option <c:if test="${afterSalesVo.atferSalesStatus eq 0}">selected</c:if> value="0">待确认</option>
					<option <c:if test="${afterSalesVo.atferSalesStatus eq 1}">selected</c:if> value="1">进行中</option>
					<option <c:if test="${afterSalesVo.atferSalesStatus eq 2}">selected</c:if> value="2">已完结</option>
					<option <c:if test="${afterSalesVo.atferSalesStatus eq 3}">selected</c:if> value="3">已关闭</option>
				</select>
			</li>
			<li>
				<label class="infor_name">审核状态</label> 
				<select class="input-middle" name="status" id="status">
					<option value="">全部</option>
					<option <c:if test="${afterSalesVo.status eq 0}">selected</c:if> value="0">待确认</option>
					<option <c:if test="${afterSalesVo.status eq 1}">selected</c:if> value="1">审核中</option>
					<option <c:if test="${afterSalesVo.status eq 2}">selected</c:if> value="2">审核通过</option>
					<option <c:if test="${afterSalesVo.status eq 3}">selected</c:if> value="3">审核不通过</option>
				</select>
			</li>
			<li>
				<label class="infor_name">业务类型</label> 
				<select class="input-middle" name="type" id="type">
					<option value="">全部</option>
					<c:forEach items="${sysList }" var="sys">
						<c:if test="${sys.relatedField == '' }">
							<option value="${sys.sysOptionDefinitionId }" <c:if test="${sys.sysOptionDefinitionId == afterSalesVo.type}">selected="selected"</c:if>>${sys.title }</option>
						</c:if>
					</c:forEach>
				</select>
			</li>
			<li>
				<div class="infor_name specialinfor">
					<select name="timeType">
						<option value="1" <c:if test="${afterSalesVo.timeType == 1 }">selected="selected"</c:if>>申请时间</option>
						<option value="2" <c:if test="${afterSalesVo.timeType == 2 }">selected="selected"</c:if>>生效时间</option>
					</select>
				</div> 
					<input class="Wdate f_left input-smaller96 m0" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endtime\')}'})"
						name="starttime" id="starttime" value="${afterSalesVo.starttime }" type="text" placeholder="请选择日期">
				<div class="f_left ml1 mr1 mt4">-</div> 
					<input class="Wdate f_left input-smaller96" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'starttime\')}'})"
						name="endtime" id="endtime" value="${afterSalesVo.endtime }" type="text" placeholder="请选择日期">
			</li>
		</ul>
		<div class="tcenter">
			<span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" id="searchSpan">搜索</span> 
			<span class="bt-small bg-light-blue bt-bg-style mr20" onclick="reset();">重置</span>
			<span class="bt-small bg-light-blue bt-bg-style" onclick="exportBuyorderAfterSalesList();">导出列表</span>
		</div>
	</form>
</div>
<div class="content">
		<div class="normal-list-page" >
			<table class="table table-bordered table-striped table-condensed table-centered">
				<thead>
					<tr>
						<th class="wid4">序号</th>
						<th class="wid8">售后单号</th>
						<th class="wid12">对应订单号</th>
						<th class="wid12">供应商名称</th>
						<th class="wid8">业务类型</th>
						<th class="wid8">创建人</th>
						<th class="wid8">订单状态</th>
						<th class="wid14">申请时间</th>
						<th class="wid14">生效时间</th>
						<th class="wid8">审核状态</th>
						<th class="wid8">售后状态</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="aftersales" items="${list}" varStatus="num">
						<tr>
							<td>${num.count}</td>
							<td>
								<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewbuyorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
												"link":"<%=basePath%>/aftersales/order/viewAfterSalesDetail.do?afterSalesId=${aftersales.afterSalesId}&traderType=2","title":"售后订单"}'>
									<c:if test="${aftersales.atferSalesStatus eq 0}"><span class="orangecircle"></span></c:if>
									<c:if test="${aftersales.atferSalesStatus eq 1}"><span class="greencircle"></span></c:if> 
									<c:if test="${aftersales.atferSalesStatus eq 2}"><span class="bluecircle"></span></c:if> 
									<c:if test="${aftersales.atferSalesStatus eq 3}"><span class="greycircle"></span></c:if>
									${aftersales.afterSalesNo}${aftersales.verifyStatus  == 0 and fn:contains(aftersales.verifyUsername, curr_user.username) ?"<font color='red'>[审]</font>":""}
								</a>
							</td>
							<td>
								<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewbuyorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
									"link":"${pageContext.request.contextPath}/order/buyorder/viewBuyordersh.do?buyorderId=${aftersales.orderId}","title":"订单信息"}'>${aftersales.orderNo}</a>
							</td>
							<td>
								<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewsupplier<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
								"link":"./trader/supplier/baseinfo.do?traderId=${aftersales.traderId}","title":"供应商信息"}'>${aftersales.traderName}</a>
								<%-- ${traderSupplierVo.verifyStatus eq 0 && fn:contains(traderSupplierVo.verifyUsername, curr_user.username) ?"<font color='red'>[审]</font>":""} --%>
							</td>
							<td>${aftersales.typeName}</td>
							<td>${aftersales.creatorName}</td>
							<td>
								<c:if test="${aftersales.atferSalesStatus eq 0}">待确认</c:if>
								<c:if test="${aftersales.atferSalesStatus eq 1}">进行中</c:if> 
								<c:if test="${aftersales.atferSalesStatus eq 2}">已完结</c:if> 
								<c:if test="${aftersales.atferSalesStatus eq 3}">已关闭</c:if>
							</td>
							<td><date:date value="${aftersales.addTime}" /></td>
							<td><date:date value="${aftersales.validTime}" /></td>
							<td><c:if test="${aftersales.status eq 0}">待确认</c:if> <c:if
									test="${aftersales.status eq 1}">审核中</c:if> <c:if
									test="${aftersales.status eq 2}">审核通过</c:if> <c:if
									test="${aftersales.status eq 3}">审核不通过</c:if></td>
							<td><c:if test="${aftersales.atferSalesStatus eq 0}">待确认</c:if> <c:if
									test="${aftersales.atferSalesStatus eq 1}">进行中</c:if> <c:if
									test="${aftersales.atferSalesStatus eq 2}">已完结</c:if> <c:if
									test="${aftersales.atferSalesStatus eq 3}">已关闭</c:if></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<c:if test="${empty list}">
				<!-- 查询无结果弹出 -->
				<div class="noresult">查询无结果！请尝试使用其他搜索条件。</div>
			</c:if>
		</div>
	<tags:page page="${page}" />
</div>
<script type="text/javascript"
	src='<%=basePath%>static/js/aftersales/order/buyorderaftersales_list.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>
