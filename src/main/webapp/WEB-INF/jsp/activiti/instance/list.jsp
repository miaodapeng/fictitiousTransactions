<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="流程任务列表" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="content">
	<div class="searchfunc">
		<form
			action="${pageContext.request.contextPath}/activiti/instance/list.do"
			method="post" id="search">

			<ul>
				<li><label class="infor_name" for="id">实例ID</label> <input
					type="text" class="input-middle" name="id" id="id"
					value="${searchForm.id}"></li>
				<li><label class="infor_name" for="businessKey">业务标识</label> <input
					type="text" class="input-middle" name="businessKey" id="businessKey"
					value="${searchForm.id}"></li>
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
					<th class="wid8">实例ID</th>
					<th>实例名称</th>
					<th>流程定义ID</th>
					<th>流程定义名称</th>
					<th>流程定义版本</th>
					<th>部署ID</th>
					<th>发布人</th>
					<th>业务标识</th>
					<th>租户</th>
					<th class="wid18">操作</th>
				</tr>
			</thead>

			<tbody class="employeestate">
				<c:if test="${not empty list}">
					<c:forEach items="${list}" var="item" varStatus="status">
						<tr>
							<td>${item.id}</td>
							<td>${item.name}</td>
							<td>${item.processDefinitionId }</td>
							<td>${item.processDefinitionName}</td>
							<td>${item.processDefinitionVersion}</td>
							<td>${item.deploymentId}</td>
							<td>${applyUserList[status.count -1].applyUser }</td>
							<td>${item.businessKey}</td>
							<td>${item.tenantId}</td>
							<td><span class="bt-smaller bt-border-style border-blue addtitle"
								tabTitle='{"num":"activiti_instance_by_instance_${item.id}","link":"${pageContext.request.contextPath}/activiti/history/byinstance.do?piid=${item.id}","title":"查看流程—实例ID:${item.id}"}'>
											详情 </span> 
								<span class="bt-smaller bt-border-style border-blue addtitle"
								tabTitle='{"num":"activiti_flowchart_tracking_piid_${item.id}","link":"${pageContext.request.contextPath}/activiti/flowchart/tracking.do?piid=${item.id}","title":"查看流程详细进度-实例ID:${item.id}"}'>
											进度图 </span>
								<span
								class="delete" onclick="delInstance('${item.id}');">删除</span></td>
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
	var delInstance = function(id) {
		del(id, '${pageContext.request.contextPath}/activiti/instance/delete.do',
				'请确认是否删除流程实例“' + id + '”和全部相关的数据');
	}
</script>
<%@ include file="../../common/footer.jsp"%>