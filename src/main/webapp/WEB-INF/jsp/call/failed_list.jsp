<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="漏接来电" scope="application" />	
<%@ include file="../common/common.jsp"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/call/failed_list.js?rnd=<%=Math.random()%>"></script>
<div class="content">
	<div class="searchfunc">
		<form
			action="${pageContext.request.contextPath}/system/call/getcallfailed.do"
			method="post" id="search">
			<ul>
				<li><label class="infor_name">主叫号码</label> <input type="text"
					class="input-middle" name="callerNumber" id="callerNumber"
					value="${callFailed.callerNumber }"></li>
				<li><label class="infor_name">公司名称</label> <input type="text"
					class="input-middle" name="traderName" id="traderName"
					value="${callFailed.traderName }"></li>
				<li><label class="infor_name">话务人员</label> <select
					class="input-middle" name=dialBackUserId>
						<option value="0">全部</option>
						<c:forEach items="${dialBackUserList}" var="user">
							<option value="${user.dialBackUserId}"
								<c:if test="${callFailed.dialBackUserId eq user.dialBackUserId}">selected="selected"</c:if>>${user.dialBackUserUsername}</option>
						</c:forEach>
				</select></li>
				<li><label class="infor_name">通话时间</label> <input
					class="Wdate f_left input-smaller96 m0" type="text"
					placeholder="请选择日期"
					onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endtime\')}'})"
					name="starttime" id="starttime" value="${callFailed.starttime }">
					<div class="f_left ml1 mr1 mt4">-</div> <input
					class="Wdate f_left input-smaller96" type="text"
					placeholder="请选择日期"
					onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'starttime\')}'})"
					name="endtime" id="endtime" value="${callFailed.endtime }"></li>
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
					<th>来电时间</th>
					<th>主叫号码</th>
					<th>号码归属地</th>
					<th>公司名称</th>
					<th>归属用户</th>
					<th>是否回拨</th>
					<th>回拨结果</th>
					<th>话务人员</th>
					<th>通话时长(秒)</th>
					<th>录音（点击播放）</th>
				</tr>
			</thead>

			<tbody>
				<c:if test="${not empty callFailedList}">
					<c:forEach items="${callFailedList }" var="list">
						<tr>
							<td><date:date value="${list.addTime} " /></td>
							<td><c:if test="${list.isDialBack == 0}">
									<i class="icontel cursor-pointer" title="点击拨号"
										onclick="dialBack(${list.callFailedId},'${list.callerNumber}');"></i>
								</c:if> ${list.callerNumber}</td>
							<td>${list.phoneArea}</td>
							<td>${list.traderName}</td>
							<td>${list.username}</td>
							<c:choose>
								<c:when test="${list.isDialBack == 1}">
									<td class="font-green">是</td>
								</c:when>
								<c:otherwise>
									<td class="font-red">否</td>
								</c:otherwise>
							</c:choose>
							<td><c:if test="${list.isDialBack == 1}">
									<c:choose>
										<c:when test="${list.filelen > 0}">
											<span class="font-green">接通</span>
										</c:when>
										<c:otherwise>
											<sapn class="font-red">未接通</sapn>
										</c:otherwise>
									</c:choose>
								</c:if></td>
							<td>${list.dialBackUserUsername}</td>
							<td>${list.filelen}</td>
							<td><c:if test="${not empty list.url}">
									<span class="edit-user" onclick="playrecord('${list.url}');">播放</span>
								</c:if></td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
		<c:if test="${empty callFailedList }">
			<!-- 查询无结果弹出 -->
			<div class="noresult">查询无结果！请尝试使用其他搜索条件。</div>
		</c:if>
		<tags:page page="${page}" />
	</div>
</div>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/call/failed_list.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../common/footer.jsp"%>