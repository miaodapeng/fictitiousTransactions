<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增分公司管理员" scope="application" />
<%@ include file="../../common/common.jsp"%>

<div class="content">
	<div class="searchfunc">
		<form action="" method="post" id="search">
			<ul>
			</ul>
		</form>
	</div>
	<div class="parts list-page normal-list-page">
		<table
			class="table table-bordered table-striped table-condensed table-centered">
			<thead>
				<tr>
					<th class="wid10">公司ID</th>
					<th>流程ID</th>
					<th>功能ID</th>
					<th>业务标识串前缀</th>
					<th class="wid16">操作</th>
				</tr>
			</thead>

			<tbody class="employeestate">
				<c:if test="${not empty list}">
					<c:forEach items="${list}" var="item" varStatus="status">
						<tr>
							<td>${item.companyId}</td>
							<td>${item.procdefId}</td>
							<td>${item.actionId}</td>
							<td>${item.preBusinessKey}</td>
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
<%@ include file="../../common/footer.jsp"%>