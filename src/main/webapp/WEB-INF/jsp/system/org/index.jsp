<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="部门管理" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<div class="content">
	<div class="searchfunc searchfuncpl0">
		<span class="bt-small bg-light-blue bt-bg-style pop-new-data"
			layerParams='{"width":"550px","height":"210px","title":"新增部门","link":"./modifyorg.do"}'>新增部门</span>
	</div>
	<div class="normal-list-page">
		<table
			class="table table-bordered table-striped table-condensed table-centered">
			<thead>
				<tr>
					<th class="sorts">序号</th>
					<th>部门名称</th>
					<th>部门类型</th>
					<th>创建时间</th>
					<th style="width: 15%;">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty orgList}">
					<c:forEach items="${orgList}" var="org" varStatus="status">
						<tr>
							<td>${status.count}</td>
							<td class="textindent8">${org.orgNames}</td>
							<td>${org.typeName}</td>
							<td>
							<date:date value="${org.addTime} " />
							</td>
							<td><span class="edit-user pop-new-data"
								layerParams='{"width":"550px","height":"210px","title":"编辑部门","link":"./modifyorg.do?orgId=${org.orgId}"}'>
									编辑 </span> <span class="delete" onclick="delOrg(${org.orgId});">删除</span>
							</td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
		<c:if test="${empty orgList}">
			<div class="noresult">查询无结果！</div>
		</c:if>
	</div>
</div>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/org/index.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../../common/footer.jsp"%>