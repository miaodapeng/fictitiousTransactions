<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="话务综合统计" scope="application" />	
<%@ include file="../common/common.jsp"%>
<div class="content">
	<div class="searchfunc">
		<form
			action="${pageContext.request.contextPath}/system/call/getstatistics.do"
			method="post" id="search">
			<ul>
				<li><label class="infor_name">通话时间</label> <input
					class="Wdate f_left input-smaller96 m0" type="text"
					placeholder="请选择日期"
					onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endtime\')}'})"
					name="starttime" id="starttime" value="${starttime }">
					<div class="f_left ml1 mr1 mt4">-</div> <input
					class="Wdate f_left input-smaller96" type="text"
					placeholder="请选择日期"
					onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'starttime\')}'})"
					name="endtime" id="endtime" value="${endtime }"></li>
			</ul>
			<div class="tcenter">
				<span class="bg-light-blue bt-bg-style bt-small" onclick="search();"
					id="searchSpan">搜索</span> <span
					class="bt-small bg-light-blue bt-bg-style" onclick="reset();">重置</span>
			</div>
		</form>
	</div>
	<div>
		<table
			class="table table-bordered table-striped table-condensed table-centered">
			<thead>
				<tr>
					<th>呼入总数</th>
					<th>呼入成功数</th>
					<th>呼入成功率(%)</th>
					<th>呼入总时长(分)</th>
					<th>外呼总数</th>
					<th>外呼成功数</th>
					<th>外呼成功率(%)</th>
					<th>外呼总时长(分)</th>
					<th>转座席总数</th>
					<th>转座席成功数</th>
					<th>转座席成功率(%)</th>
					<th>平均排队时长(秒)</th>
					<th>平均应答速度(秒)</th>
				</tr>
			</thead>

			<tbody>
				<c:if test="${not empty info}">
					<tr>
						<td>${info.sysCallIn}</td>
						<td>${info.sysCallInSucc}</td>
						<td>${info.sysCallInSuccRatio}</td>
						<td>${info.sysCallInTime}</td>
						<td>${info.sysCallOut}</td>
						<td>${info.sysCallOutSucc}</td>
						<td>${info.sysCallOutSuccRatio}</td>
						<td>${info.sysCallOutTime}</td>
						<td>${info.sysIVRCust}</td>
						<td>${info.sysIVRCustSucc}</td>
						<td>${info.sysIVRCustSuccRatio}</td>
						<td>${info.sysIVRCustTime}</td>
						<td>${info.sysIVRCustTimeSucc}</td>
					</tr>
				</c:if>
				<c:if test="${empty info }">
					<!-- 查询无结果弹出 -->
					<tr>
						<td colspan="13">查询无结果！请尝试使用其他搜索条件。</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>
</div>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/call/statistics.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../common/footer.jsp"%>