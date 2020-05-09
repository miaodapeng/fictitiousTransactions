<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="数据字典" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="main-container">
	<div class="searchfunc">
		<form
			action="${pageContext.request.contextPath}/system/sysoptiondefinition/list.do"
			method="get" id="search">
			<ul>
				<li><label class="infor_name">ID</label> <input type="text"
					class="input-middle" name="sysOptionDefinitionId" id="sysOptionDefinitionId"
					value="${sysOptionDefinition.sysOptionDefinitionId}" /></li>
				<li><label class="infor_name">作用域</label> <input type="text"
					class="input-middle" name="scope" id="scope"
					value="${sysOptionDefinition.scope}" /></li>
				<li><label class="infor_name">名称</label> <input type="text"
					class="input-middle" name="title" id="title"
					value="${sysOptionDefinition.title}" /></li>
					<li><label class="infor_name">类别</label> <input type="text"
					class="input-middle" name="optionType" id="optionType"
					value="${sysOptionDefinition.optionType}" /></li>
				<li><label class="infor_name">状态</label> <select
					class="input-middle f_left" name="status">
						<option value="-1">请选择</option>
						<option value="0"
							<c:if test="${sysOptionDefinition.status != null and sysOptionDefinition.status =='0'}">selected="selected"</c:if>>禁用</option>
						<option value="1"
							<c:if test="${sysOptionDefinition.status != null and sysOptionDefinition.status =='1'}">selected="selected"</c:if>>启用</option>
				</select></li>
			</ul>
			<div class="tcenter">
				<span class="bt-small bg-light-blue bt-bg-style" onclick="search();"
					id="searchSpan">搜索</span> <span
					class="bt-small bg-light-blue bt-bg-style" onclick="reset();">重置</span>
				<span class="bt-small bg-light-blue bt-bg-style pop-new-data"
					layerParams='{"width":"500px","height":"260px","title":"新增数据字典","link":"./add.do"}'>新增数据字典</span>
			</div>
		</form>
	</div>
	<div class="list-page normal-list-page">
		<table
			class="table table-bordered table-striped table-condensed table-centered">
			<thead>
				<tr>
					<th class="sorts">序号</th>
					<th>ID</th>
					<th>作用域</th>
					<th>名称</th>
					<th>类别</th>
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
							<td>${list.scope }</td>
							<td>${list.title }</td>
							<td>${list.optionType }</td>
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
								<span class="edit-user pop-new-data" layerParams='{"width":"500px","height":"260px","title":"编辑数据字典","link":"./edit.do?sysOptionDefinitionId=${list.sysOptionDefinitionId}"}'>
									编辑
								</span>
								<span class="edit-user addtitle" tabTitle='{"num":"sysOptionDefinitionView${list.sysOptionDefinitionId}","link":"./system/sysoptiondefinition/viewchilds.do?sysOptionDefinitionId=${list.sysOptionDefinitionId}","title":"查看子集"}'>
									查看子集
								</span>
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
		<tags:page page="${page}" />
	</div>
</div>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/optiondefinition/list_optiondefinition.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../../common/footer.jsp"%>