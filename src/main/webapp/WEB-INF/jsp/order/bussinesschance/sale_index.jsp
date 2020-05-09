<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="商机列表" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<c:if test="${not empty method && method == 'bussinesschance' }">
	<%@ include file="../../trader/customer/customer_tag.jsp"%>
</c:if>
<style>
.customer{
	margin-left: 10px;
}
	
</style>

<div class="customer">
    <ul>
        <li>
            <c:choose>
			   <c:when test="${not empty method && method == 'bussinesschance'}">  
			         <a href="${pageContext.request.contextPath}/order/bussinesschance/saleindex.do?status=-1&isRest=1&traderId=${traderCustomer.traderId}&traderCustomerId=${traderCustomer.traderCustomerId}&traderCustomerId=${traderCustomer.traderCustomerId}&customerNature=${traderCustomer.customerNature}" 
			   </c:when>
			   <c:otherwise> 
			         <a href="${pageContext.request.contextPath}/order/bussinesschance/saleindex.do?status=-1&isRest=1" 
			   </c:otherwise>
			</c:choose>
            <c:if test="${ (empty bussinessChanceVo.status)  or (bussinessChanceVo.status=='-1') }">class="customer-color"</c:if>>全部</a>
        </li>
        <li>
            <c:choose>
			   <c:when test="${not empty method && method == 'bussinesschance'}">  
			         <a href="${pageContext.request.contextPath}/order/bussinesschance/saleindex.do?status=0&isRest=1&traderId=${traderCustomer.traderId}&traderCustomerId=${traderCustomer.traderCustomerId}&traderCustomerId=${traderCustomer.traderCustomerId}&customerNature=${traderCustomer.customerNature}" 
			   </c:when>
			   <c:otherwise> 
			         <a href="${pageContext.request.contextPath}/order/bussinesschance/saleindex.do?status=0&isRest=1" 
			   </c:otherwise>
			</c:choose>
            <c:if test="${bussinessChanceVo.status=='0' }">class="customer-color"</c:if>>未处理</a>
        </li>
        <li>
            <c:choose>
			   <c:when test="${not empty method && method == 'bussinesschance'}">  
			         <a href="${pageContext.request.contextPath}/order/bussinesschance/saleindex.do?status=2&isRest=1&traderId=${traderCustomer.traderId}&traderCustomerId=${traderCustomer.traderCustomerId}&traderCustomerId=${traderCustomer.traderCustomerId}&customerNature=${traderCustomer.customerNature}" 
			   </c:when>
			   <c:otherwise> 
			         <a href="${pageContext.request.contextPath}/order/bussinesschance/saleindex.do?status=2&isRest=1"
			   </c:otherwise>
			</c:choose>
            <c:if test="${bussinessChanceVo.status=='2' }">class="customer-color"</c:if>>已报价</a>
        </li>
        <li>
            <c:choose>
			   <c:when test="${not empty method && method == 'bussinesschance'}">  
			         <a href="${pageContext.request.contextPath}/order/bussinesschance/saleindex.do?status=1&isRest=1&traderId=${traderCustomer.traderId}&traderCustomerId=${traderCustomer.traderCustomerId}&traderCustomerId=${traderCustomer.traderCustomerId}&customerNature=${traderCustomer.customerNature}" 
			   </c:when>
			   <c:otherwise> 
			         <a href="${pageContext.request.contextPath}/order/bussinesschance/saleindex.do?status=1&isRest=1"
			   </c:otherwise>
			</c:choose>
            <c:if test="${bussinessChanceVo.status=='1' }">class="customer-color"</c:if>>报价中</a>
        </li>
        <li>
            <c:choose>
			   <c:when test="${not empty method && method == 'bussinesschance'}">  
			         <a href="${pageContext.request.contextPath}/order/bussinesschance/saleindex.do?status=3&isRest=1&traderId=${traderCustomer.traderId}&traderCustomerId=${traderCustomer.traderCustomerId}&traderCustomerId=${traderCustomer.traderCustomerId}&customerNature=${traderCustomer.customerNature}" 
			   </c:when>
			   <c:otherwise> 
			         <a href="${pageContext.request.contextPath}/order/bussinesschance/saleindex.do?status=3&isRest=1"
			   </c:otherwise>
			</c:choose>
            <c:if test="${bussinessChanceVo.status=='3' }">class="customer-color"</c:if>>已成单</a>
        </li>
        <li>
            <c:choose>
			   <c:when test="${not empty method && method == 'bussinesschance'}">  
			         <a href="${pageContext.request.contextPath}/order/bussinesschance/saleindex.do?status=4&isRest=1&traderId=${traderCustomer.traderId}&traderCustomerId=${traderCustomer.traderCustomerId}&traderCustomerId=${traderCustomer.traderCustomerId}&customerNature=${traderCustomer.customerNature}" 
			   </c:when>
			   <c:otherwise> 
			         <a href="${pageContext.request.contextPath}/order/bussinesschance/saleindex.do?status=4&isRest=1"
			   </c:otherwise>
			</c:choose>
            <c:if test="${bussinessChanceVo.status=='4' }">class="customer-color"</c:if>>已关闭</a>
        </li>
    </ul>
</div>
<div class="searchfunc" <c:if test="${not empty method}">style="margin-top: -10px;"</c:if>>
	<form method="post"
		action="${pageContext.request.contextPath}/order/bussinesschance/saleindex.do"
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

			<!-- 2018-8-22 新增筛选条件(商机来源)-->
			<li><label class="infor_name">商机来源</label> <select
				class="input-middle" name="source">
					<option value="0">全部</option>
					<c:forEach items="${sourceList }" var="type">
						<option value="${type.sysOptionDefinitionId }"
							<c:if test="${type.sysOptionDefinitionId == bussinessChanceVo.source}">selected="selected"</c:if>>${type.title }</option>
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

			<li><label class="infor_name">客户分类</label>
				<select class="input-middle" name="traderCategory">
					<option value="-1">全部</option>
					<option value="1"
							<c:if test="${bussinessChanceVo.traderCategory == 1}">selected="selected"</c:if>>新客户
					</option>

				</select>
			</li>

			<li><label class="infor_name">商机等级</label> <select
				class="input-middle" name="bussinessLevel">
					<option value="0">全部</option>
					<c:forEach items="${bussinessLevelList }" var="bussinessLevel">
						<option value="${bussinessLevel.sysOptionDefinitionId }"
							<c:if test="${bussinessLevel.sysOptionDefinitionId == bussinessChanceVo.bussinessLevel}">selected="selected"</c:if>>${bussinessLevel.title }</option>
					</c:forEach>
			</select></li>
			<li><label class="infor_name">商机阶段</label> <select
				class="input-middle" name="bussinessStage">
					<option value="">全部</option>
					<c:forEach items="${bussinessStageList }" var="bussinessStage">
						<option value="${bussinessStage.sysOptionDefinitionId }"
							<c:if test="${bussinessStage.sysOptionDefinitionId == bussinessChanceVo.bussinessStage}">selected="selected"</c:if>>${bussinessStage.title }</option>
					</c:forEach>
			</select></li>
			<li><label class="infor_name">成单几率</label> <select
				class="input-middle" name="orderRate">
					<option value="">全部</option>
					<c:forEach items="${orderRateList }" var="orderRate">
						<option value="${orderRate.sysOptionDefinitionId }"
							<c:if test="${orderRate.sysOptionDefinitionId == bussinessChanceVo.orderRate}">selected="selected"</c:if>>${orderRate.title }</option>
					</c:forEach>
			</select></li>
			<li><label class="infor_name">已成单未结款</label> <select
				class="input-middle" name="isPayment">
					<option value="">全部</option>
					<option value="1" <c:if test="${bussinessChanceVo.isPayment==1}">selected="selected"</c:if>>是</option>
					<option value="2" <c:if test="${bussinessChanceVo.isPayment==2}">selected="selected"</c:if>>否</option>
			</select></li>
			<li><label class="infor_name">产品名称</label> <input type="text"
				class="input-middle" name="goodsName" id="goodsName"
				value="${bussinessChanceVo.goodsName }" /></li>
			<c:if test="${empty method}">
				<li><label class="infor_name">客户名称</label> <input type="text"
					class="input-middle" name="traderName" id="traderName"
					value="${bussinessChanceVo.traderName }" /></li>
			</c:if>
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
			<c:if test="${empty method}">
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
			</c:if>
			<li>
			    <label class="infor_name">预计成单时间</label>
			    <input class="Wdate f_left input-smaller96 m0" type="text"
				placeholder="请选择日期"
				onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'cdendtime\')}'})"
				name="cdstarttime" id="cdstarttime"
				value="${bussinessChanceVo.cdstarttime }">
				<div class="f_left ml1 mr1 mt4">-</div> <input
				class="Wdate f_left input-smaller96" type="text" placeholder="请选择日期"
				onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'cdstarttime\')}'})"
				name="cdendtime" id="cdendtime" value="${bussinessChanceVo.cdendtime }">
			</li>
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
						<option value="5"
							<c:if test="${bussinessChanceVo.timeType == 5 }">selected="selected"</c:if>>关闭时间</option>
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
			<c:if test="${empty method}">
				<li><label class="infor_name">地区</label> <select
					name="province">
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
			</c:if>
		</ul>
		<input type="hidden" id="status" name="status" value="${bussinessChanceVo.status }"/>
		<div class="tcenter">
			<c:if test="${not empty traderCustomer.traderId }">
				<input type="hidden" name="traderId"
					value="${traderCustomer.traderId }">
				<input type="hidden" name="traderCustomerId"
					value="${traderCustomer.traderCustomerId }">
			</c:if>
			<span class="bt-small bg-light-blue bt-bg-style mr20 "
				onclick="search();" id="searchSpan">搜索</span> <span
				class="bt-small bg-light-blue bt-bg-style mr20" onclick="reset();">重置</span>
			<c:if test="${not empty method && method == 'bussinesschance' }">
				<c:if test="${customerInfoByTraderCustomer.isEnable == 1  && ((customerInfoByTraderCustomer.verifyStatus != null && customerInfoByTraderCustomer.verifyStatus != 0 )|| customerInfoByTraderCustomer.verifyStatus == null)}">
					<span class="bt-small bg-light-blue bt-bg-style mr20 addtitle"
						tabTitle='{"num":"businesschanceservice<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>","link":"./order/bussinesschance/addSalesBussinessChance.do?traderId=${traderCustomer.traderId }&traderCustomerId=${traderCustomer.traderCustomerId }","title":"新增商机"}'>新增商机</span>
				</c:if>
			</c:if>
		</div>
	</form>
</div>
<div class="content">
	<div class="fixdiv">
		<div class="superdiv">
			<table
				class="table table-bordered table-striped table-condensed table-centered">
				<thead>
					<tr>
						<th class="wid7">商机编号</th>
						<th class="wid7">商机类型</th>
						<!-- 2018-8-22 新增(商机来源)-->
						<th class="wid7">商机来源</th>
						<th class="wid8">咨询入口</th>
						<th class="wid8">功能</th>
						<th class="wid9">商机等级</th>
						<th class="wid13">预计金额</th>
						<th class="wid9">成单几率</th>
						<th class="wid10">分配销售部门</th>
						<th class="wid10">分配销售</th>
						<th class="wid15" >客户名称</th>
						<th class="wid15">询价产品</th>
						<th class="wid7">商机状态</th>
						<th class="wid8">报价单号</th>
						<th class="wid8">订单号</th>
						<th class="wid10">分配时间</th>
						<th class="wid12">商机初次查看时间</th>
						<th class="wid8">响应时长（分）</th>
						<th class="wid12">关闭原因</th>
						<th class="wid8">附件</th>
						<th class="wid12">备注</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty bussinessChanceList}">
						<c:forEach items="${bussinessChanceList }" var="bussinessChance">
							<tr>
								<td>
								
									<div class="font-blue">
									<c:if test="${bussinessChance.flag eq 1}">
									<span style="color: red;font-size: 16px">！</span>
									</c:if>
										<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"view<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
												"link":"./order/bussinesschance/toSalesDetailPage.do?bussinessChanceId=${bussinessChance.bussinessChanceId}&traderId=${bussinessChance.traderId }",
												"title":"销售商机详情"}'>${bussinessChance.bussinessChanceNo }${bussinessChance.verifyStatus == 0 && fn:contains(bussinessChance.verifyUsername, curr_user.username) ?"<font color='red'>[审]</font>":""}</a>
									</div>
								</td>
								<td>${bussinessChance.typeName }</td>
								<!-- 2018-8-22 新增(商机来源)-->
								<td>${bussinessChance.sourceName }</td>

								<td>${bussinessChance.entranceName}</td>
								<td>${bussinessChance.functionName}</td>

								<td>
								  <c:forEach items="${bussinessLevelList }" var="bussinessLevel">
									<c:if test="${bussinessLevel.sysOptionDefinitionId == bussinessChance.bussinessLevel}">${bussinessLevel.title }</c:if>
								  </c:forEach>
								</td>
								<td>${bussinessChance.amount }</td>
								<td>
									<c:forEach items="${orderRateList }" var="orderRate">
									<c:if test="${orderRate.sysOptionDefinitionId == bussinessChance.orderRate}">${orderRate.title }</c:if>
								  </c:forEach>
								</td>
								<td>${bussinessChance.saleDeptName }</td>
								<td>${bussinessChance.saleUser }</td>
								<td>${bussinessChance.traderName}</td>
								<td style="word-wrap:break-word;">${bussinessChance.content }</td>
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
									<c:choose>
										<c:when test="${bussinessChance.quoteValidStatus eq 0}">
											<a class="addtitle"
												tabtitle="{&quot;num&quot;:&quot;viewQuote2<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>&quot;,&quot;link&quot;:&quot;./order/quote/getQuoteDetail.do?quoteorderId=${bussinessChance.quoteorderId}&viewType=2&quot;,&quot;title&quot;:&quot;编辑报价&quot;}">${bussinessChance.quoteorderNo}</a>
										</c:when>
										<c:otherwise>
											<a class="addtitle"
												tabtitle="{&quot;num&quot;:&quot;viewQuote3<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>&quot;,&quot;link&quot;:&quot;./order/quote/getQuoteDetail.do?quoteorderId=${bussinessChance.quoteorderId}&viewType=3&quot;,&quot;title&quot;:&quot;报价信息&quot;}">${bussinessChance.quoteorderNo}</a>
										</c:otherwise>
									</c:choose>
									<%-- <a class="addtitle"
										tabtitle="{&quot;num&quot;:&quot;viewQuote3<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>&quot;,&quot;link&quot;:&quot;./order/quote/getQuoteDetail.do?quoteorderId=${bussinessChance.quoteorderId}&viewType=3&quot;,&quot;title&quot;:&quot;报价信息&quot;}">${bussinessChance.quoteorderNo }</a> --%>
								</td>
								<td>
									<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewsaleorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
										"link":"${pageContext.request.contextPath}/order/saleorder/view.do?saleorderId=${bussinessChance.saleorderId}","title":"订单信息"}'>${bussinessChance.saleorderNo}</a>
								</td>
								<td><date:date value="${bussinessChance.assignTime} " /></td>
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
							<td colspan="14">查询无结果！</td>
						</tr>
					</c:if>
				</tbody>
			</table>
			
		</div>
	</div>
	<div>
		<tags:page page="${page}" />
	</div>
	<div class="clear"></div>
				<div class="fixtablelastline">
					【商机预计金额之和：<fmt:formatNumber type="number" value="${totalAmount}" pattern="0.00" maxFractionDigits="2" /> 元】
				</div>
</div>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/order/bussinesschance/service_index.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/region/index.js?rnd=<%=Math.random()%>"></script>
<input type="hidden" id="rest" value="${bussinessChanceVo.isRest }"/>
<%@ include file="../../common/footer.jsp"%>
