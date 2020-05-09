<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="流程任务列表" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="content">
	<div class="searchfunc">
		<form action="${pageContext.request.contextPath}/activiti/myhistory/runlist.do" method="post" id="search">

			<ul>
				<li><label class="infor_name" for="id">实例ID</label> <input
					type="text" class="input-middle" name="id" id="id"
					value="${searchForm.id}"></li>
			</ul>
			<div class="tcenter">
				<span class="bg-light-blue bt-bg-style bt-small" id="searchSpan">搜索</span>
				<span class="bt-small bg-light-blue bt-bg-style" onclick="reset();">重置</span>

			</div>
		</form>
	</div>
	<div class="parts list-page normal-list-page">

		<table
			class="table table-bordered table-striped table-condensed table-centered">
			<thead>
				<tr>
					<th class="wid8">实例ID</th>
					<th>流程定义ID</th>
					<th>名称</th>
					<th>开始时间</th>
					<th>结束时间</th>
					<th>持续时间</th>
					<th class="wid12">操作</th>
				</tr>
			</thead>

			<tbody class="employeestate">
				<c:if test="${not empty list}">
					<c:forEach items="${list}" var="item" varStatus="status">
						<tr>
							<td>${item.id}</td>
							<td>${item.processDefinitionId }</td>
							<td>${item.name}</td>
							<td><fmt:formatDate value="${item.startTime}"
									pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td><fmt:formatDate value="${item.endTime}"
									pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td><date:wordTime value="${item.durationInMillis}" /></td>
							<td><span
								class="bt-smaller bt-border-style border-blue addtitle"
								tabTitle='{"num":"activiti_history_by_instance_${item.id}","link":"${pageContext.request.contextPath}/activiti/history/byinstance.do?piid=${item.id}","title":"查看历史流程—实例ID:${item.id}"}'>
									详情 </span> <c:choose>
									<c:when test="${empty item.endTime}">
										<span class="bt-smaller bt-border-style border-blue addtitle"
											tabTitle='{"num":"activiti_flowchart_tracking_piid_${item.id}","link":"${pageContext.request.contextPath}/activiti/flowchart/tracking.do?piid=${item.id}","title":"查看流程详细进度-实例ID:${item.id}"}'>
											进度图 </span>
									</c:when>
									<c:otherwise>
										<span class="bt-smaller bt-border-style border-blue addtitle"
											tabTitle='{"num":"activiti_model_png_${item.deploymentId}","link":"${pageContext.request.contextPath}/activiti/definition/png.do?did=${item.deploymentId}","title":"查看流程图"}'>流程图</span>
									</c:otherwise>
								</c:choose></td>
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
<script type="text/javascript">
	
</script>
<%@ include file="../../common/footer.jsp"%>