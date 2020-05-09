<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="流程定义列表" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="content">
	<div class="searchfunc">
		<form action="${pageContext.request.contextPath}/activiti/definition/list.do" method="post" id="search">

			<ul>
				<li><label class="infor_name" for="id">ID</label> <input
					type="text" class="input-middle" name="id" id="id"
					value="${searchForm.id}"></li>

				<li><label class="infor_name" for="key">标识</label> <input
					type="text" class="input-middle" placeholder="流程定义标识，全匹配搜索"
					name="key" id="key" value="${searchForm.key}"></li>

				<li><label class="infor_name" for="name">名称</label> <input
					type="text" class="input-middle" placeholder="流程定义名称" name="name"
					id="name" value="${searchForm.name}"></li>
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
					<th>定义ID</th>
					<th class="wid10">部署ID</th>
					<th class="wid6">版本</th>
					<th>标识</th>
					<th>名称</th>
					<th>部署名称</th>
					<th>发布时间</th>
					<th>租户</th>
					<th class="wid22">操作</th>
				</tr>
			</thead>

			<tbody class="employeestate">
				<c:if test="${not empty list}">
					<c:forEach items="${list}" var="item" varStatus="status">
						<tr>
							<td>${item.id}</td>
							<td>${item.deploymentId}</td>
							<td>${item.version}</td>
							<td>${item.key}</td>
							<td>${item.name }</td>
							<td>${pdeList[status.count -1].deploymentName }</td>
							<!--
								<td>${item.resourceName}</td>
								<td>${item.diagramResourceName}</td>
								-->
							<td><fmt:formatDate value="${pdeList[status.count -1].deploymentTime}"
									pattern="yyyy-MM-dd HH:mm" /></td>
							<td>${item.tenantId }</td>

							<td><span
								class="bt-smaller bt-border-style border-blue pop-new-data"
								layerparams='{"width":"400px","height":"240px","title":"启动流程","link":"${pageContext.request.contextPath}/activiti/definition/start.do?pdid=${item.id }&pdname=${item.name }&dname=${pdeList[status.count -1].deploymentName }"}'>启动</span>
								<span
								class="bt-smaller bt-border-style border-blue pop-new-data"
								layerparams='{"width":"400px","height":"240px","title":"绑定业务功能","link":"${pageContext.request.contextPath}/activiti/definition/bind_action.do?pdid=${item.id }"}'>绑定</span>
								<span
								class="bt-smaller bt-border-style border-blue addtitle"
								tabTitle='{"num":"activiti_model_png_${item.deploymentId}","link":"${pageContext.request.contextPath}/activiti/definition/png.do?did=${item.deploymentId}","title":"使用的流程图"}'>流程图</span>

								<span class="delete"
								onclick="delProcessDefinition('${item.deploymentId}');">删除</span>
							</td>
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
	var delProcessDefinition = function(id) {
		del(
				id,
				'${pageContext.request.contextPath}/activiti/definition/delete.do',
				'即将删除流程定义“' + id + '”和全部相关的数据');
	}
</script>
<%@ include file="../../common/footer.jsp"%>