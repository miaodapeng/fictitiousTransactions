<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="流程图列表" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="content">
	<div class="searchfunc">
		<form action="${pageContext.request.contextPath}/activiti/model/list.do" method="post" id="search">

			<ul>
				<li><label class="infor_name" for="id">ID</label> <input
					type="text" class="input-middle" name="id" id="id"
					value="${searchForm.id}"></li>

				<li><label class="infor_name" for="key">标识</label> <input
					type="text" class="input-middle" placeholder="流程图标识，全匹配搜索"
					name="key" id="key" value="${searchForm.key}"></li>

				<li><label class="infor_name" for="name">名称</label> <input
					type="text" class="input-middle" placeholder="流程图名称" name="name"
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
				<span class="pop-new-data bt-small bt-bg-style bg-light-blue"
					layerparams='{"width":"400px","height":"230px","title":"新增流程图模型","link":"${pageContext.request.contextPath}/activiti/model/create.do"}'>新增</span>
			</div>
		</form>
	</div>
	<div class="parts list-page normal-list-page">
		<table
			class="table table-bordered table-striped table-condensed table-centered">
			<thead>
				<tr>
					<th class="wid10">ID</th>
					<th class="wid8">版本</th>
					<th>标识</th>
					<th>名称</th>
					<!-- 
						<th>分类</th>
						<th>数据源ID</th>
						<th>数据扩展ID</th>
						<th>发布ID</th>
						 -->
					<th>创建时间</th>
					<th>更新时间</th>
					<th>租户</th>
					<th class="wid22">操作</th>
				</tr>
			</thead>

			<tbody class="employeestate">
				<c:if test="${not empty list}">
					<c:forEach items="${list}" var="processDef" varStatus="status">
						<tr>
							<td>${processDef.id}</td>
							<td>${processDef.version}</td>
							<td>${processDef.key}</td>
							<td>${processDef.name }</td>
							<!--
								<td>${processDef.category}</td>
								<td>${processDef.editorSourceValueId}</td>
								<td>${processDef.editorSourceExtraValueId}</td>
								<td>${processDef.deploymentId}</td>
								-->
							<td><fmt:formatDate value="${processDef.createTime}"
									pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td><fmt:formatDate value="${processDef.lastUpdateTime}"
									pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td>${processDef.tenantId }</td>

							<td><c:if
									test="${!empty processDef.editorSourceExtraValueId}">
									<span class="bt-smaller bt-border-style border-blue"
										onclick="deployProcessModel('${processDef.id}');">发布</span>
									<span class="bt-smaller bt-border-style border-blue"
										onclick="exportModel('${processDef.id}');">导出</span>
								</c:if> <span class="bt-smaller bt-border-style border-blue addtitle"
								tabTitle='{"num":"activiti_processModel_editor_${processDef.id}","link":"./modeler.html?modelId=${processDef.id}","title":"编辑流程定义"}'>
									设计 </span> <span class="delete"
								onclick="delProcessModel('${processDef.id}','${processDef.name }');">删除</span>
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
	var delProcessModel = function(id, key) {
		delAll(id,
				'${pageContext.request.contextPath}/activiti/model/delete.do',
				'请确认是否删除流程图“' + key + '(ID:' + id + ')”和全部相关的数据。');
	}

	var deployProcessModel = function(id) {

		if (id > 0) {
			var api = '${pageContext.request.contextPath}/activiti/model/deploy.do';
			var msg = '请确认是否发布该流程，流程被发布后不能再删除。';
			layer.confirm(msg, {
				btn : [ '确定', '取消' ]
			// 按钮
			}, function() {
				layer.closeAll('dialog');
				$.ajax({
					type : "POST",
					url : api,
					data : {
						'modelId' : id
					},
					dataType : 'json',
					success : function(data) {
						if (data.code == 0) {
							alert("发布成功！");
						}
						return data;
					},
					error : function(XMLHttpRequest, textStatus) {
						checkLogin();
					}
				});
			}, function() {
			});
		}
	}

	var exportModel = function(id) {
		$("#downloadHandel").attr(
				"src",
				"${pageContext.request.contextPath}/activiti/model/export/"
						+ id + ".do?=" + Date.now());
	}
</script>
<%@ include file="../../common/footer.jsp"%>
<iframe id="downloadHandel" src="" style="display: none"></iframe>