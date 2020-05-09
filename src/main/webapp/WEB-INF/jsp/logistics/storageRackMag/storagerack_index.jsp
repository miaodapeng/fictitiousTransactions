<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="货架管理" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="searchfunc">
	<form
		action="${pageContext.request.contextPath}/warehouse/storageRackMag/index.do"
		method="post" id="search">
		<ul>
			<li><label class="infor_name">货架名称</label> <input type="text"
				class="input-middle" name="storageRackName" id="storageRackName" value="${storageRack.storageRackName}"/>
			</li>
			<li><label class="infor_name">货架状态</label> <select
				class="input-middle f_left" name="isEnable" id="isEnable">
					<option selected="selected" value="">全部</option>
					<option value="0"
						<c:if test="${storageRack.isEnable != null and storageRack.isEnable=='0'}">selected="selected"</c:if>>已禁用</option>
					<option value="1"
						<c:if test="${storageRack.isEnable != null and storageRack.isEnable=='1'}">selected="selected"</c:if>>未禁用</option>
			</select></li>
			<li><label class="infor_name">所属货区</label> <select
				class="input-middle" name="storageAreaId" id="storageAreaId">
					<option value="0">全部</option>
					<c:forEach items="${listArea }" var="type">
						<option value="${type.storageAreaId }"
						   <c:if test="${not empty storageRack &&  storageRack.storageAreaId == type.storageAreaId}">selected="selected"</c:if> >${type.storageAreaName }</option>
					</c:forEach>
			</select></li>
		</ul>
		<div class="tcenter">
			<span class="bg-light-blue bt-bg-style bt-small" onclick="search();"
				id="searchSpan">搜索</span> <span
				class="bt-small bg-light-blue bt-bg-style" onclick="reset();">重置</span>
			<span class="bg-light-blue bt-bg-style bt-small addtitle"
				tabTitle='{"num":"warehouse_storageRackMag_addStorageRack_storageRackId",
				"link":"./warehouse/storageRackMag/addStorageRack.do","title":"新增货架"}'>新增货架</span>
			<button type="button" class="bt-bg-style bg-light-blue bt-small pop-new-data"
					layerparams='{"width":"500px","height":"200px","title":"批量新增货架","link":"./batchAddStorageRack.do"}'>批量新增货架</button>
		</div>
	</form>
</div>
<div class="content">
	<table
		class="table table-bordered table-striped table-condensed table-centered">
		<thead>
			<tr>
				<th style="width: 60px">序号</th>
				<th style="width: 200px">货架名称</th>
				<th >货架备注</th>
				<th style="width: 150px">库存商品数量</th>
				<th >所属货区</th>
				<th style="width: 100px">货架状态</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="list" items="${storageRackList}" varStatus="num">
				<tr>
					<td>${num.count}</td>
					<td>
						<div class="font-blue">
							<a class="addtitle" href="javascript:void(0);"
								tabTitle='{"num":"warehouse_storageRackMag_toStorageRackDetailPage_storageRackId${list.storageRackId}",
									"link":"./warehouse/storageRackMag/toStorageRackDetailPage.do?storageRackId=${list.storageRackId}",
									"title":"货架详情"}'>${list.storageRackName}</a>
						</div>
					</td>
					<td>${list.comments}</td>
					<td>${list.cnt}</td>
					<td>
					   <div class="font-blue"><a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"warehouse_storageAreaMag_toStorageAreaDetailPage_storageAreaId${list.storageAreaId}",
									"link":"./warehouse/storageAreaMag/toStorageAreaDetailPage.do?storageAreaId=${list.storageAreaId}",
									"title":"货区详情"}'>${list.storageAreaName}</a>
						</div>
					</td>
					<c:choose>
						<c:when test="${list.isEnable eq 1}">
							<td>未禁用</td>
						</c:when>
						<c:otherwise>
							<td class="font-red">已禁用</td>
						</c:otherwise>
					</c:choose>
				</tr>
			</c:forEach>
		</tbody>
	</table>

<c:if test="${empty storageRackList}">
	<!-- 查询无结果弹出 -->
	<div class="noresult">查询无结果！请尝试使用其他搜索条件。</div>
</c:if>
<tags:page page="${page}" />
</div>
<script type="text/javascript"
	src='<%= basePath %>static/js/logistics/storageRackMag/index_storagerack_mag.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>