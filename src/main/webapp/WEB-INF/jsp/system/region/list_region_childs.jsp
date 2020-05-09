<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="地区" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<div class="main-container">
	
	<div>
		<table class="table table-bordered table-striped table-condensed table-centered">
			<thead>
				<tr>
					<th class="wid10">序号</th>
					<th class="wid15">名称</th>
					<th class="wid10">类型</th>
					<th class="wid15">操作</th>
				</tr>
			</thead>
			<tbody class="company">
				<c:if test="${not empty list}">
					<c:forEach items="${list}" var="region" varStatus="status">
						<tr>
							<td>${status.count}</td>
							<td>${region.regionName}</td>
							<td>
								<c:if test="${region.regionType eq 1 }">
									省
								</c:if>
								<c:if test="${region.regionType eq 2 }">
									市
								</c:if>
								<c:if test="${region.regionType eq 3 }">
									区/县
								</c:if>
							</td>
							
							<td>
								<span class="edit-user pop-new-data" layerParams='{"width":"400px","height":"200px","title":"编辑地区","link":"./editRegionPage.do?regionId=${region.regionId}"}'>
									编辑
								</span>
								<c:if test="${region.regionType eq 2 }">
									<span class="edit-user addtitle" tabTitle='{"num":"sysOptionDefinitionView${region.regionId}","link":"./system/region/viewRegionChilds.do?regionId=${region.regionId}","title":"查看子地区"}'>
										查看子地区
									</span>
								</c:if>
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