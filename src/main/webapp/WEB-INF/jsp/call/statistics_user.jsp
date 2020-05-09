<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="坐席明细统计" scope="application" />
<%@ include file="../common/common.jsp"%>
<div class="content">
	<div class="searchfunc">
		<form
			action="${pageContext.request.contextPath}/system/call/getuserdetail.do"
			method="post" id="search">
			<ul>
				<li><label class="infor_name">所属部门</label> <select
					class="input-middle f_left" name="orgId">
						<option value="0">请选择</option>
						<c:forEach items="${orgList }" var="org">
							<option value="${org.orgId }"
								<c:if test="${orgId != null and orgId == org.orgId}">selected="selected"</c:if>>
								${org.orgName }</option>
						</c:forEach>
				</select></li>
				<li><label class="infor_name">用户</label> <select
					class="input-middle" name=userNumber>
						<option value="all">全部</option>
						<c:forEach items="${userList}" var="user">
							<option value="${user.number}"
								<c:if test="${userNumber eq user.number}">selected="selected"</c:if>>${user.username}</option>
						</c:forEach>
				</select></li>
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
					<th>座席姓名</th>
					<th>应答总数</th>
					<th>应答成功次数</th>
					<th>应答成功率(%)</th>
					<th>外呼次数</th>
					<th>外呼成功次数</th>
					<th>外呼成功率(%)</th>
					<th>2分钟以上通话总数</th>
					<th>2分钟以上外呼总数</th>
					<th>1分钟以上通话总数</th>
					<th>1分钟以上外呼总数</th>
					<th>通话总时长(分)</th>
					<!-- 
					<th>话后处理总时长(分)</th>
					<th>离席次数</th>
					<th>离席总时长(分)</th>
					<th>空闲总时长(分)</th>
					 -->
				</tr>
			</thead>

			<tbody>
				<c:if test="${not empty info}">
					<c:forEach items="${info }" var="detail">
						<tr>
							<td>${detail.Name}</td>
							<td>${detail.callIn}</td>
							<td>${detail.callSucc}</td>
							<td><c:choose>
									<c:when test="${detail.callIn > 0 }">
										<fmt:formatNumber type="number"
											value="${(detail.callSucc/detail.callIn)*100 }"
											maxFractionDigits="2" />
									</c:when>
									<c:otherwise>0</c:otherwise>
								</c:choose></td>
							<td>${detail.callOut}</td>
							<td>${detail.callOutSucc}</td>
							<td><c:choose>
									<c:when test="${detail.callOut > 0 }">
										<fmt:formatNumber type="number"
											value="${(detail.callOutSucc/detail.callOut)*100 }"
											maxFractionDigits="2" />
									</c:when>
									<c:otherwise>0</c:otherwise>
								</c:choose></td>
							<td>${detail.callTimeSucc}</td>
							<td>${detail.callOutTimeSucc}</td>
							<td>${detail.callTimeOneSucc}</td>
							<td>${detail.callOutTimeOneSucc}</td>
							<td>${detail.callTime}</td>
							<!-- 
							<td>${detail.callEndTime}</td>
							<td>${detail.callLeave}</td>
							<td>${detail.callLeaveTime}</td>
							<td>${detail.callFreeTime}</td>
							 -->
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
		<c:if test="${empty info }">
			<!-- 查询无结果弹出 -->
			<div class="noresult">查询无结果！请尝试使用其他搜索条件。</div>
		</c:if>
	</div>
</div>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/call/statistics_user.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../common/footer.jsp"%>