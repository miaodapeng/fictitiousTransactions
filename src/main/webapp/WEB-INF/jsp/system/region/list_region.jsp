<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="地区管理" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="main-container">
	<div class="searchfunc">
		<form action="${pageContext.request.contextPath}/system/region/getRegionListPage.do" method="get" id="search">
			<ul>
				<li>
					<label class="infor_name">名称</label> 
					<input type="text" class="input-middle" name="regionName" id="regionName" value="${region.regionName}" />
				</li>
				
			</ul>
			<div class="tcenter">
				<span class="bt-small bg-light-blue bt-bg-style" onclick="search();"
					id="searchSpan">搜索</span> <span
					class="bt-small bg-light-blue bt-bg-style" onclick="reset();">重置</span>
				<span class="bt-small bg-light-blue bt-bg-style pop-new-data"
					layerParams='{"width":"400px","height":"250px","title":"新增地区","link":"./addRegionPage.do"}'>新增地区</span>
			</div>
		</form>
	</div>
	<div class="list-page normal-list-page">
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
								<span class="edit-user addtitle" tabTitle='{"num":"sysOptionDefinitionView${region.regionId}","link":"./system/region/viewRegionChilds.do?regionId=${region.regionId}","title":"查看下一级"}'>
									查看下一级
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
	src="${pageContext.request.contextPath}/static/js/region/index.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../../common/footer.jsp"%>