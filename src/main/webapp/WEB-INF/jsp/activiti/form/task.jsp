<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="流程任务列表" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="content">
	<div class="searchfunc">
		<form action="" method="post" id="search"></form>
	</div>
	<div class="parts list-page normal-list-page">

		<table
			class="table table-bordered table-striped table-condensed table-centered">
			<thead>
				<tr>
					<th>字段ID</th>
					<th>名称</th>
					<th>值</th>
					<th>数型</th>
					<th class="wid10">操作</th>
				</tr>
			</thead>

			<tbody class="employeestate">
				<c:if test="${not empty list}">
					<c:forEach items="${list}" var="item" varStatus="status">
						<tr>
							<td>${item.id}</td>
							<td>${item.name }</td>
							<td><input name="${item.id}" type = "text" class="input-middle" value="${item.value}"/></td>
							<td>${item.type.name }</td>
							<td></td>
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
	var delTask = function(id) {
		del(id, '${pageContext.request.contextPath}/activiti/task/delete.do',
				'请确认是否删除任务“' + id + '”和全部相关的数据');
	}
	var completeTask = function(id) {
		if (id > 0) {
			var api = '${pageContext.request.contextPath}/activiti/task/complete.do';
			var msg = '请确认是否完成当前流程。';
			layer.confirm(msg, {
				btn : [ '确定', '取消' ]
			// 按钮
			}, function() {
				layer.closeAll('dialog');
				$.ajax({
					type : "POST",
					url : api,
					data : {
						'id' : id
					},
					dataType : 'json',
					success : function(data) {
						layer.closeAll('dialog');
						refreshNowPageList(data);
					},
					error : function(XMLHttpRequest, textStatus) {
						checkLogin();
					}
				});
			}, function() {
			});
		}
	}
</script>
<%@ include file="../../common/footer.jsp"%>