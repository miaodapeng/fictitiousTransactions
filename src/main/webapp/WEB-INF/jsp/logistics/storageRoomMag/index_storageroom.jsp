<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="库房管理" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="searchfunc">
	<form
		action="${pageContext.request.contextPath}/warehouse/storageRoomMag/index.do"
		method="post" id="search">
		<ul>
			<li><label class="infor_name">库房名称</label> <input type="text"
				class="input-middle" name="storageRoomName" id="storageRoomName" value="${storageroom.storageRoomName}"/>
			</li>
			<li><label class="infor_name">库房状态</label> <select class="input-middle f_left" name="isEnable" id="isEnable">
						<option selected="selected" value="">全部</option>
						<option value="0" <c:if test="${storageroom.isEnable != null and storageroom.isEnable=='0'}">selected="selected"</c:if>>已禁用</option>
						<option value="1" <c:if test="${storageroom.isEnable != null and storageroom.isEnable=='1'}">selected="selected"</c:if>>未禁用</option>
					</select></li>
			<li><label class="infor_name">所属仓库</label> <select
				class="input-middle" name="warehouseId" id="warehouseId">
					<option value="0">全部</option>
					<c:forEach items="${warehouseList }" var="type">
						<option value="${type.warehouseId }" 
						 <c:if test="${type.warehouseId eq storageroom.warehouseId }">selected="selected"</c:if>>${type.warehouseName }</option>
					</c:forEach>
			</select></li>
		</ul>
		<div class="tcenter">
			<span class="bg-light-blue bt-bg-style bt-small" onclick="search();"
				id="searchSpan">搜索</span> <span
				class="bt-small bg-light-blue bt-bg-style" onclick="reset();">重置</span>
			<span class="bg-light-blue bt-bg-style bt-small addtitle"
				tabTitle='{"num":"warehouse_storageRoomMag_addStorageRoom_storageroomId","link":"./warehouse/storageRoomMag/addStorageRoom.do","title":"新增库房"}'>新增库房</span>
		</div>
	</form>
</div>
<div class="content">
	<table
		class="table table-bordered table-striped table-condensed table-centered">
		<thead>
			<tr>
				<th style="width: 60px">序号</th>
				<th style="width: 200px">库房名称</th>
				<th >库房备注</th>
				<th style="width: 150px">库存商品数量</th>
				<th >所属仓库</th>
				<th style="width: 100px">库房状态</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="list" items="${storageRoomList}" varStatus="num">
				<tr>
					<td>${num.count}</td>
					<td>
						<div class="font-blue">
							<a class="addtitle" href="javascript:void(0);"
								tabTitle='{"num":"warehouse_storageRoomMag_toStorageRoomDetailPage_storageroomId${list.storageroomId}",
									"link":"./warehouse/storageRoomMag/toStorageRoomDetailPage.do?storageroomId=${list.storageroomId}",
									"title":"库房详情"}'>${list.storageRoomName}</a>
						</div>
					</td>
					<td>${list.comments}</td>
					<td>${list.cnt}</td>
					<td><div class="font-blue">
								<a class="addtitle" href="javascript:void(0);"
									tabTitle='{"num":"warehouse_warehouses_toWarehouseDetailPage_wareHouseId",
									"link":"./warehouse/warehouses/toWarehouseDetailPage.do?warehouseId=${list.warehouseId}",
									"title":"仓库详情"}'>${list.warehouseName}</a>
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

<c:if test="${ empty storageRoomList}">
	<!-- 查询无结果弹出 -->
	<div class="noresult">查询无结果！请尝试使用其他搜索条件。</div>
</c:if>
<c:if test="${not empty storageRoomList}">
	<tags:page page="${page}" />
</c:if>
</div>
<script type="text/javascript"
	src='<%= basePath %>static/js/logistics/storageRoomMag/index_storageroom_mag.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>