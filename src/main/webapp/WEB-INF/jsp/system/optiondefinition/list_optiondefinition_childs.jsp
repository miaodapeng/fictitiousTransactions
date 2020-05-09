<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="子集数据字典" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<div class="main-container">
	<div class="searchfunc searchfuncpl0">
		<span class="bt-small bg-light-blue bt-bg-style pop-new-data"
			layerParams='{"width":"500px","height":"260px","title":"新增子集数据字典","link":"./addchild.do?sysOptionDefinitionId=${sysOptionDefinition.sysOptionDefinitionId }"}'>新增子集数据字典</span>
	</div>
	<div>
		<table
			class="table table-bordered table-striped table-condensed table-centered">
			<thead>
				<tr>
					<th class="sorts">序号</th>
					<th>ID</th>
					<th>名称</th>
					<th>类别</th>
					<th>排序</th>
					<th>状态</th>
					<th>备注</th>
					<th style="width: 20%">操作</th>
				</tr>
			</thead>
			<tbody class="company">
				<c:if test="${not empty list}">
					<c:forEach items="${list}" var="list" varStatus="status">
						<tr>
							<td>${status.count}</td>
							<td>${list.sysOptionDefinitionId }</td>
							<td>${list.title }</td>
							<td>${list.optionType }</td>
							<td>${list.sort }</td>
							<td><c:choose>
									<c:when test="${list.status == 1 }">
										<span class="onstate">启用</span>
									</c:when>
									<c:otherwise>
										<span class="offstate">禁用</span>
									</c:otherwise>
								</c:choose></td>
							<td title="${list.comments }">${list.comments }</td>
							<td>
								<span class="edit-user pop-new-data" layerParams='{"width":"500px","height":"260px","title":"编辑数据字典","link":"./editchild.do?sysOptionDefinitionId=${list.sysOptionDefinitionId}"}'>
									编辑
								</span>
								<c:choose>
									<c:when test="${list.status eq 0 }">
										<span class="edit-user"
												onclick="setDisabled(${list.sysOptionDefinitionId},1,${list.parentId});">启用</span>
									</c:when>
									<c:otherwise>
										<span class="forbid"
												onclick="setDisabled(${list.sysOptionDefinitionId},0,${list.parentId});">禁用</span>
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
		<c:if test="${empty list}">
			<!-- 查询无结果弹出 -->
			<div class="noresult">查询无结果！请尝试使用其他搜索条件。</div>
		</c:if>
	</div>
</div>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/optiondefinition/list_optiondefinition_childs.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../../common/footer.jsp"%>