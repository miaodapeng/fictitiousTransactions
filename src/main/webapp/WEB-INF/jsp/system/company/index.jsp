<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="分公司" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="content">
	<div class="searchfunc">
		<form
			action="${pageContext.request.contextPath}/system/company/index.do"
			method="get" id="search">
			<ul>
				<li><label class="infor_name">公司名称</label> <input type="text"
					class="input-middle" name="companyName" id="companyName"
					value="${company.companyName}" /></li>
			</ul>
			<div class="tcenter">
				<span class="bt-small bg-light-blue bt-bg-style" onclick="search();"
					id="searchSpan">搜索</span> <span
					class="bt-small bg-light-blue bt-bg-style" onclick="reset();">重置</span>
				<span class="bt-small bg-light-blue bt-bg-style pop-new-data"
					layerParams='{"width":"500px","height":"180px","title":"新增公司","link":"./addcompany.do"}'>新增公司</span>
			</div>
		</form>
	</div>
	<div>
		<table
			class="table table-bordered table-striped table-condensed table-centered">
			<thead>
				<tr>
					<th>公司名称</th>
					<th>访问地址</th>
					<th>创建时间</th>
					<th style="width: 15%;">操作</th>
				</tr>
			</thead>
			<tbody class="company">
				<c:if test="${not empty companyList}">
					<c:forEach items="${companyList}" var="company" varStatus="status">
						<tr>
							<td class="text-ellipsis" title="${company.companyName }">${company.companyName }</td>
							<td>${company.domain }</td>
							<td><jsp:useBean id="dateValue" class="java.util.Date" /> <jsp:setProperty
									name="dateValue" property="time" value="${company.addTime}" />
								<fmt:formatDate value="${dateValue}"
									pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td><span class="edit-user pop-new-data"
								layerParams='{"width":"500px","height":"180px","title":"编辑公司","link":"./editcompany.do?companyId=${company.companyId}"}'>
									编辑 </span></td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
		<c:if test="${empty companyList}">
			<!-- 查询无结果弹出 -->
			<div class="noresult">查询无结果！请尝试使用其他搜索条件。</div>
		</c:if>
		<c:if test="${not empty companyList}">
			<tags:page page="${page}" />
		</c:if>
	</div>
</div>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/company/index.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../../common/footer.jsp"%>