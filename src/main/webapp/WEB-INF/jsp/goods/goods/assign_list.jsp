<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="产品类别归属列表" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="main-container">
	<div class="list-page normal-list-page">
		<table class="table">
			<thead>
				<tr>
					<th class="wid4">选择</th>
					<th class="wid4">序号</th>
					<th>分类名称</th>
					<th class="wid10">产品数量</th>
					<th>归属</th>
					<th class="wid10">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty list}">
					<c:forEach items="${list }" var="category" varStatus="status">
						<tr>
							<td><input type="checkbox" name="checkOne"
								value="${category.sysOptionDefinitionId}" autocomplete="off"></td>
							<td>${status.count }</td>
							<td>${category.title }</td>
							<td>${category.num }</td>
							<td><c:if test="${not empty category.userList }">
									<c:forEach items="${category.userList }" var="user"
										varStatus="st">
								${user.username } <c:if
											test="${st.count != category.userList.size() }">、</c:if>
									</c:forEach>
								</c:if></td>
							<td><span class="edit-user pop-new-data"
								layerParams='{"width":"500px","height":"210px","title":"编辑归属","link":"./editcategoryowner.do?manageCategories=${category.sysOptionDefinitionId}"}'>编辑</span>
							</td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
		<c:if test="${empty list }">
			<!-- 查询无结果弹出 -->
			<div class="noresult">查询无结果！</div>
		</c:if>
	</div>
	<div class="inputfloat f_left ml10">
		<input type="checkbox" class="mt6 mr4" name="checkAll" autocomplete="off"> <label
			class="mr10 mt4">全选</label> <span
			class="bt-bg-style bg-light-blue bt-small mr10" onclick="editAll(this);" layerParams='{"width":"500px","height":"210px","title":"编辑归属","link":"./editcategoryowner.do"}'>编辑</span>
	</div>
</div>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/goods/goods/assign_list.js?rnd=<%=Math.random()%>"></script>

<%@ include file="../../common/footer.jsp"%>
