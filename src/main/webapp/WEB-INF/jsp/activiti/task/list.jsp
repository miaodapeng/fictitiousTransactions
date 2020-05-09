<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="流程任务列表" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="content">
	<div class="searchfunc">
		<form action="${pageContext.request.contextPath}/activiti/task/list.do" method="post" id="search">

			<ul>
				<li><label class="infor_name" for="id">任务ID</label> <input
					type="text" class="input-middle" name="id" id="id"
					value="${searchForm.id}"></li>
				<li><label class="infor_name" for="name">名称</label> <input
					type="text" class="input-middle" placeholder="任务名称" name="name"
					id="name" value="${searchForm.name}"></li>
				<li><label class="infor_name" for="assignee">办理人</label> <input
					type="text" class="input-middle" placeholder="办理人" name="assignee"
					id="assignee" value="${searchForm.assignee}"></li>
				<li><label class="infor_name" for="tenantId">分公司</label> <select
					id="tenantId" name="tenantId" class="font-black">
						<option value="0">选择分公司</option>
						<c:if test="${not empty companyList}">
							<c:forEach items="${companyList}" var="company"
								varStatus="status">
								<c:set var="fullTenantId" value="erp:company:${company.companyId}" scope="application" />
								<option value="${fullTenantId}"
									<c:if test='${searchForm.tenantId == fullTenantId}'> selected="true"</c:if>>${company.companyName}</option>
							</c:forEach>
						</c:if>
				</select></li>
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
					<th class="wid8">任务ID</th>
					<th class="wid10">实例ID</th>
					<th>名称</th>
					<th>办理人</th>
					<th>创建时间</th>
					<th>租户</th>
					<th class="wid24">操作</th>
				</tr>
			</thead>

			<tbody class="employeestate">
				<c:if test="${not empty list}">
					<c:forEach items="${list}" var="item" varStatus="status">
						<tr>
							<td>${item.id}</td>
							<td>${item.processInstanceId}</td>
							<td>${item.name }</td>
							<td>${item.assignee }</td>
							<td><fmt:formatDate value="${item.createTime}"
									pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td>${item.tenantId }</td>
							<td><span
								class="bt-smaller bt-border-style border-blue pop-new-data"
								layerparams='{"width":"400px","height":"180px","title":"审批流程","link":"${pageContext.request.contextPath}/activiti/task/complete.do?tid=${item.id}&piid=${item.processInstanceId}&pass=true"}'>同意</span>
								<span class="bt-smaller bt-border-style delete pop-new-data"
								layerparams='{"width":"400px","height":"180px","title":"审批流程","link":"${pageContext.request.contextPath}/activiti/task/complete.do?tid=${item.id}&piid=${item.processInstanceId}&pass=false"}'>退回</span>
								<span class="bt-smaller bt-border-style border-blue addtitle"
								tabTitle='{"num":"activiti_history_by_instance_${item.processInstanceId}","link":"${pageContext.request.contextPath}/activiti/history/byinstance.do?piid=${item.processInstanceId}","title":"查看流程详细进度-实例ID:${item.processInstanceId}"}'>
									详情 </span> <span
								class="bt-smaller bt-border-style border-blue addtitle"
								tabTitle='{"num":"activiti_flowchart_tracking_tid_${item.id}","link":"${pageContext.request.contextPath}/activiti/flowchart/tracking.do?tid=${item.id}","title":"查看流程详细进度-实例ID:${item.processInstanceId}"}'>
									进度图 </span></td>
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
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/activiti/task/complete.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../../common/footer.jsp"%>