<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="流程任务列表" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="content">
	<div class="searchfunc"></div>
	<div class="parts list-page normal-list-page">

		<table
			class="table table-bordered table-striped table-condensed table-centered">
			<thead>
				<tr>
					<th class="wid8">任务ID</th>
					<th>实例ID</th>
					<th>任务名称</th>
					<th>备注</th>
					<th>办理人</th>
					<th>开始时间</th>
					<th>结束时间</th>
					<th class="wid12">持续时间</th>
				</tr>
			</thead>

			<tbody class="employeestate">
				<c:if test="${not empty list}">
					<c:forEach items="${list}" var="item" varStatus="status">
						<tr>
							<td>${item.taskId}</td>
							<td>${item.processInstanceId }</td>
							<td><c:choose>
									<c:when test="${item.activityType == 'startEvent'}"> 
							开始
							</c:when>
									<c:when test="${item.activityType == 'intermediateThrowEvent'}"> 
							结束
							</c:when>
									<c:otherwise>   
									${item.activityName}  
									</c:otherwise>
								</c:choose></td>
							<td>${mapList[status.count - 1].comment}</td>
							<td>${item.assignee}</td>
							<td><fmt:formatDate value="${item.startTime}"
									pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td><fmt:formatDate value="${item.endTime}"
									pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td><date:wordTime value="${item.durationInMillis}" /></td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
		<c:if test="${empty list }">
			<!-- 查询无结果弹出 -->
			<div class="noresult">查询无结果！请尝试使用其他搜索条件。</div>
		</c:if>
		<c:if test="${not empty list}">
			<tags:page page="${page}" />
		</c:if>

	</div>

</div>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/common/general.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../../common/footer.jsp"%>