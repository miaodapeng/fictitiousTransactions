<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="商机列表" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="searchfunc">
	<form method="post"
		action="${pageContext.request.contextPath}/order/bussinesschance/serviceindex.do"
		id="search">
		<ul>
			<li><label class="infor_name">商机编号</label> <input type="text"
				class="input-middle" name="bussinessChanceNo" id="bussinessChanceNo"
				value="${bussinessChanceVo.bussinessChanceNo }" /></li>
			<li><label class="infor_name">商机类型</label> <select
				class="input-middle" name="type">
					<option value="0">全部</option>
					<c:forEach items="${typeList }" var="type">
						<option value="${type.sysOptionDefinitionId }"
							<c:if test="${type.sysOptionDefinitionId == bussinessChanceVo.type}">selected="selected"</c:if>>${type.title }</option>
					</c:forEach>
			</select></li>

			<li><label class="infor_name">分配结果</label>
				<select class="input-middle" name="assignStatus">
					<option value="-1">全部</option>
					<option value="0"
							<c:if test="${bussinessChanceVo.assignStatus == 0}">selected="selected"</c:if>>未分配</option>
					<option value="1"
							<c:if test="${bussinessChanceVo.status == 1}">selected="selected"</c:if>>已分配</option>
				</select>
			</li>

			<li><label class="infor_name">商机状态</label>
			<select class="input-middle" name="status">
					<option value="-1">全部</option>
					<option value="0"
						<c:if test="${bussinessChanceVo.status == '0'}">selected="selected"</c:if>>未处理</option>
					<option value="1"
						<c:if test="${bussinessChanceVo.status == '1'}">selected="selected"</c:if>>报价中</option>
					<option value="2"
						<c:if test="${bussinessChanceVo.status == '2'}">selected="selected"</c:if>>已报价</option>
					<option value="3"
						<c:if test="${bussinessChanceVo.status == '3'}">selected="selected"</c:if>>已订单</option>
					<option value="4"
						<c:if test="${bussinessChanceVo.status == '4'}">selected="selected"</c:if>>已关闭</option>
			</select>
			</li>
			<li>
				<label class="infor_name">商机来源</label> 
				<select class="input-middle" name="source">
					<option value="0">全部</option>
					<c:forEach items="${sourceList }" var="source">
						<option value="${source.sysOptionDefinitionId }"
							<c:if test="${source.sysOptionDefinitionId == bussinessChanceVo.source}">selected="selected"</c:if>>${source.title }</option>
					</c:forEach>
			</select></li>

			<li>
				<label class="infor_name">咨询入口</label>
				<select class="input-middle" name="entrances">
					<option value="0">全部</option>
					<c:forEach items="${entrancesList }" var="entrances">
						<option value="${entrances.sysOptionDefinitionId }"
								<c:if test="${entrances.sysOptionDefinitionId == bussinessChanceVo.entrances}">selected="selected"</c:if>>${entrances.title }</option>
					</c:forEach>
				</select>
			</li>

			<li>
				<label class="infor_name">功能</label>
				<select class="input-middle" name="functions">
					<option value="0">全部</option>
					<c:forEach items="${functionsList }" var="functions">
						<option value="${functions.sysOptionDefinitionId }"
								<c:if test="${functions.sysOptionDefinitionId == bussinessChanceVo.functions}">selected="selected"</c:if>>${functions.title }</option>
					</c:forEach>
				</select>
			</li>

<%--	有变更，暂时不上		<li><label class="infor_name">客户分类</label>--%>
<%--				<select class="input-middle" name="traderCategory">--%>
<%--					<option value="-1">全部</option>--%>
<%--					<option value="1"--%>
<%--							<c:if test="${bussinessChanceVo.traderCategory == 1}">selected="selected"</c:if>>新客户--%>
<%--					</option>--%>

<%--				</select>--%>
<%--			</li>--%>

			<li><label class="infor_name">产品名称</label> <input type="text"
				class="input-middle" name="goodsName" id="goodsName"
				value="${bussinessChanceVo.goodsName }" /></li>
			<li><label class="infor_name">客户名称</label> <input type="text"
				class="input-middle" name="traderName" id="traderName"
				value="${bussinessChanceVo.traderName }" /></li>
			<li><label class="infor_name">联系方式</label> <input type="text"
				class="input-middle" name="traderContactName" id="traderContactName"
				value="${bussinessChanceVo.traderContactName }"
				placeholder="联系人/手机号/电话/其他联系方式" /></li>
			<li><label class="infor_name">询价产品</label> <input type="text"
				class="input-middle" name="content" id="content"
				value="${bussinessChanceVo.content }" /></li>
			<li><label class="infor_name">报价单号</label> <input type="text"
				class="input-middle" name="quoteorderNo" id="quoteorderNo"
				value="${bussinessChanceVo.quoteorderNo }" /></li>
			<li><label class="infor_name">订单号</label> <input type="text"
				class="input-middle" name="saleorderNo" id="saleorderNo"
				value="${bussinessChanceVo.saleorderNo }" /></li>
			<li><label class="infor_name">归属销售</label> <select
				class="input-middle" name="userId">
					<option value="0">全部</option>
					<c:if test="${not empty userList }">
						<c:forEach items="${userList }" var="user">
							<option value="${user.userId }"
								<c:if test="${bussinessChanceVo.userId eq user.userId }">selected="selected"</c:if>>${user.username }</option>
						</c:forEach>
					</c:if>
			</select></li>
			<li>
				<div class="infor_name specialinfor">
					<select name="timeType">
						<option value="1"
							<c:if test="${bussinessChanceVo.timeType == 1 }">selected="selected"</c:if>>创建时间</option>
						<option value="2"
							<c:if test="${bussinessChanceVo.timeType == 2 }">selected="selected"</c:if>>分配时间</option>
						<option value="3"
							<c:if test="${bussinessChanceVo.timeType == 3 }">selected="selected"</c:if>>报价时间</option>
						<option value="4"
							<c:if test="${bussinessChanceVo.timeType == 4 }">selected="selected"</c:if>>订单时间</option>
					</select>
				</div> <input class="Wdate f_left input-smaller96 m0" type="text"
				placeholder="请选择日期"
				onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endtime\')}'})"
				name="starttime" id="starttime"
				value="${bussinessChanceVo.starttime }">
				<div class="f_left ml1 mr1 mt4">-</div> <input
				class="Wdate f_left input-smaller96" type="text" placeholder="请选择日期"
				onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'starttime\')}'})"
				name="endtime" id="endtime" value="${bussinessChanceVo.endtime }">
			</li>
			<li><label class="infor_name">地区</label> <select name="province">
					<option value="0">请选择</option>
					<c:if test="${not empty provinceList }">
						<c:forEach items="${provinceList }" var="province">
							<option value="${province.regionId }"
								<c:if test="${ not empty bussinessChanceVo &&  province.regionId == bussinessChanceVo.province }">selected="selected"</c:if>>${province.regionName }</option>
						</c:forEach>
					</c:if>
			</select> <select name="city">
					<option value="0">请选择</option>
					<c:if test="${not empty cityList }">
						<c:forEach items="${cityList }" var="city">
							<option value="${city.regionId }"
								<c:if test="${ not empty bussinessChanceVo &&  city.regionId == bussinessChanceVo.city }">selected="selected"</c:if>>${city.regionName }</option>
						</c:forEach>
					</c:if>
			</select> <select name="zone">
					<option value="0">请选择</option>
					<c:if test="${not empty zoneList }">
						<c:forEach items="${zoneList }" var="zone">
							<option value="${zone.regionId }"
								<c:if test="${ not empty bussinessChanceVo &&  zone.regionId == bussinessChanceVo.zone }">selected="selected"</c:if>>${zone.regionName }</option>
						</c:forEach>
					</c:if>
			</select></li>
		</ul>
		<div class="tcenter">
			<span class="bt-small bg-light-blue bt-bg-style mr20 "
				onclick="search();" id="searchSpan">搜索</span> <span
				class="bt-small bg-light-blue bt-bg-style mr20" onclick="reset();">重置</span>
			<span class="bt-small bg-light-blue bt-bg-style mr20 addtitle"
				tabTitle='{"num":"businesschanceservice<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>","link":"./order/bussinesschance/addServiceBussinessChance.do","title":"新增商机"}'>新增商机</span>
			<!-- <span class="bg-light-blue bt-bg-style bt-small" onclick="exportList()">导出列表</span> -->
		</div>
	</form>
</div>
<div class="content">
	<div class="fixdiv">
		<div class="superdiv" style="width: 3000px;">
			<table
				class="table table-bordered table-striped table-condensed table-centered">
				<thead>
					<tr>
						<th class="wid4">选择</th>
						<th class="wid8">商机编号</th>
						<th class="wid8">商机类型</th>
						<th class="wid8">商机来源</th>
						<th class="wid8">咨询入口</th>
						<th class="wid8">功能</th>
						<th class="wid8">询价方式</th>
						<th class="wid15">询价产品</th>
						<th class="wid10">分配销售部门</th>
						<th class="wid8">分配销售</th>
						<th class="wid15">客户名称</th>
						<th class="wid8">联系人</th>
						<th class="wid7">联系方式</th>
						<th class="wid12">商机创建时间</th>
						<th class="wid12">分配时间</th>
						<th class="wid7">商机状态</th>
						<th class="wid7">报价单号</th>
						<th class="wid12">报价生效时间</th>
						<th class="wid7">报价金额</th>
						<th class="wid7">订单号</th>
						<th class="wid12">订单生效时间</th>
						<th class="wid8">订单金额</th>
						<th class="wid6">订单状态</th>
						<th class="wid12">商机初次查看时间</th>
						<th class="wid8">响应时长（分）</th>
						<th class="wid15">关闭原因</th>
						<th class="wid8">附件</th>
						<th class="wid13">备注</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty bussinessChanceList}">
						<c:forEach items="${bussinessChanceList }" var="bussinessChance">
							<tr>
								<td><c:if test="${bussinessChance.status eq 0 && bussinessChance.traderId eq 0}">
										<input type="checkbox" name="bussinessChanceId" value="${bussinessChance.bussinessChanceId }" id="${bussinessChance.bussinessChanceNo}">
									</c:if></td>
								<td>
									<div class="font-blue">
										<a class="addtitle" href="javascript:void(0);"
											tabTitle='{"num":"view${bussinessChance.bussinessChanceId}",
										"link":"./order/bussinesschance/toAfterSalesDetailPage.do?bussinessChanceId=${bussinessChance.bussinessChanceId}",
										"title":"售后商机详情"}'>${bussinessChance.bussinessChanceNo }</a>
									</div>
								</td>
								<td>${bussinessChance.typeName }</td>
								<td>${bussinessChance.sourceName }</td>
								<td>${bussinessChance.entranceName}</td>
								<td>${bussinessChance.functionName}</td>
								<td>${bussinessChance.communicationName }</td>
								<td style="word-wrap:break-word;">${bussinessChance.content }</td>
								
								<td>${bussinessChance.saleDeptName }</td>
								<td class="saleUser">${bussinessChance.saleUser }</td>
								<td ><c:choose>
										<c:when test="${not empty bussinessChance.checkTraderName }">
			                    		${bussinessChance.checkTraderName }
			                    	</c:when>
										<c:otherwise>
			                    		${bussinessChance.traderName }
			                    	</c:otherwise>
									</c:choose></td>
								<td><c:choose>
										<c:when
											test="${not empty bussinessChance.checkTraderContactName }">
			                    		${bussinessChance.checkTraderContactName }
			                    	</c:when>
										<c:otherwise>
			                    		${bussinessChance.traderContactName }
			                    	</c:otherwise>
									</c:choose></td>
								<td><c:choose>
										<c:when test="${not empty bussinessChance.checkMobile }">
			                    		${bussinessChance.checkMobile }
			                    	</c:when>
										<c:otherwise>
			                    		${bussinessChance.mobile }
			                    	</c:otherwise>
									</c:choose>
								</td>
								<td><date:date value="${bussinessChance.addTime} " /></td>
								<td><date:date value="${bussinessChance.assignTime} " /></td>
								<td>
									<c:choose>
											<c:when test="${bussinessChance.status eq '0'}">
					                    		<span class="warning-color1">未处理</span>
					                    	</c:when>
											<c:when test="${bussinessChance.status eq '1'}">
				                    	<span class="success-color1">报价中</span>
				                    	</c:when>
											<c:when test="${bussinessChance.status eq '2'}">
				                    	<span class="success-color1">已报价</span>
				                    	</c:when>
											<c:when test="${bussinessChance.status eq '3'}">
				                    	<span class="success-color1">已订单</span>
				                    	</c:when>
											<c:when test="${bussinessChance.status eq '4'}">
				                    	已关闭
				                    	</c:when>
									</c:choose>
								</td>
								<td>
									<a class="addtitle"
										tabtitle="{&quot;num&quot;:&quot;viewQuote3<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>&quot;,&quot;link&quot;:&quot;./order/quote/getQuoteDetail.do?quoteorderId=${bussinessChance.quoteorderId}&viewType=3&quot;,&quot;title&quot;:&quot;报价信息&quot;}">${bussinessChance.quoteorderNo }</a>
								</td>
								<td><date:date
										value="${bussinessChance.quoteorderAddTime} " /></td>
								<td>${bussinessChance.quoteorderTotalAmount }</td>
								<td>
									<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewsaleorder${list.saleorderId}",
										"link":"${pageContext.request.contextPath}/order/saleorder/view.do?saleorderId=${bussinessChance.saleorderId}","title":"订单信息"}'>${bussinessChance.saleorderNo}</a>
								</td>
								<td><date:date value="${bussinessChance.saleorderAddTime} " /></td>
								<td>${bussinessChance.saleorderTotalAmount }</td>
								<td><c:choose>
										<c:when test="${bussinessChance.saleorderstatus eq '0' }">
			                    	<span class="warning-color1">待确认</span>
			                    	</c:when>
										<c:when test="${bussinessChance.saleorderstatus eq '1' }">
			                    	<span class="warning-color1">进行中</span>
			                    	</c:when>
										<c:when test="${bussinessChance.saleorderstatus eq '2' }">
			                    	<span class="success-color1">已完结</span>
			                    	</c:when>
										<c:when test="${bussinessChance.saleorderstatus eq '3' }">
			                    	已关闭
			                    	</c:when>
									</c:choose></td>
								<td><date:date value="${bussinessChance.firstViewTime} " /></td>
								<td><c:if
										test="${bussinessChance.firstViewTime > 0 && bussinessChance.assignTime > 0}">
										<fmt:formatNumber type="number" maxFractionDigits="1"
											value="${((bussinessChance.firstViewTime-bussinessChance.assignTime)/1000)/60}" />
									</c:if></td>
								<td>${bussinessChance.closeReason }</td>
								<td>
									<c:if test="${not empty bussinessChance.attachmentDomain and not empty bussinessChance.attachmentUri}">
										<a href="<c:url value="http://${bussinessChance.attachmentDomain}${bussinessChance.attachmentUri}"/>" target="_blank">
											附件
										</a>
									</c:if>
								</td>

								<td>${bussinessChance.comments }</td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${empty bussinessChanceList }">
						<!-- 查询无结果弹出 -->
						<tr>
							<td colspan="25">查询无结果！</td>
						</tr>
					</c:if>
				</tbody>
			</table>
			
		</div>
	</div>
	<div>
		<div class="inputfloat f_left">
			<input type="checkbox" class="mt6 mr4" id="selectAll"
				onclick="selectAll(this);"> <label class="mr10 mt4">全选</label>
			<select id="toSaler">
				<option value="0">请选择</option>
				<c:if test="${not empty userList }">
					<c:forEach items="${userList }" var="user">
						<option value="${user.userId }">${user.username }</option>
					</c:forEach>
				</c:if>
			</select> <span class="bt-bg-style bg-light-blue bt-small mr10"
				onclick="assignAll();">批量分配</span>
		</div>
		<tags:page page="${page}" />
	</div>
</div>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/order/bussinesschance/service_index.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/region/index.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../../common/footer.jsp"%>
