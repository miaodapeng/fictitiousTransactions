<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="仓库详情" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="content mt10">
	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">基本信息</div>
			<div class="title-click nobor">
				<span class=" brand-color1 addtitle mr10"
					tabTitle='{"num":"warehouse_warehouses_editWarehouseJump_wareHouseId${warehouses.warehouseId}","title":"编辑仓储","link":"./warehouse/warehouses/editWarehouseJump.do?warehouseId=${warehouses.warehouseId}"}'>编辑</span>
				<c:choose>
					<c:when test="${warehouses.isEnable eq 1}">
						<span class="font-red pop-new-data"
							layerParams='{"width":"40%","height":"220px","title":"禁用仓库","link":"./disableWarehouse.do?warehouseId=${warehouses.warehouseId}&isEnable=${warehouses.isEnable}"}'>禁用</span>
					</c:when>
					<c:otherwise>
						<span id="start">启用</span>
						<input type="hidden" id="ware_HouseId" value="${warehouses.warehouseId}"/>
						<input type="hidden" id="is_Enable" value="${warehouses.isEnable}">
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<table
			class="table table-bordered table-striped table-condensed table-centered">
			<tbody>
				<tr>
					<td class="table-smaller" id="warehouseName" name="warehouseName">仓库名称</td>
					<td>${warehouses.warehouseName}</td>
					<td class="table-smaller">所属公司</td>
					<td>${warehouses.companyName}</td>
				</tr>
				<tr>
					<td>创建人</td>
					<td>${warehouses.creatorName}</td>
					<td>创建时间</td>
					<td><date:date value="${warehouses.addTime}" /></td>
				</tr>
				<tr>
					<td>仓库状态</td>
					<c:choose>
						<c:when test="${warehouses.isEnable eq 1}">
							<td>未禁用</td>
						</c:when>
						<c:otherwise>
							<td class="font-red">已禁用</td>
						</c:otherwise>
					</c:choose>
					<td>禁用原因</td>
					<td>${warehouses.enableComment}</td>
				</tr>
				<tr>
					<td>仓库地区</td>
					<td>
						<div >${warehouses.areaName}</div>
					</td>
					<td>禁用时间</td>
					<td><date:date value="${warehouses.enabletime}" /></td>
				</tr>
				<tr>
					<td>仓库地址</td>
					<td colspan="3">${warehouses.address}</td>
				</tr>
				<tr>
					<td>仓库备注</td>
					<td colspan="3">${warehouses.comments}</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">内部库房信息</div>
		</div>
		<table
			class="table">
			<thead>
				<tr>
					<th>库房名称</th>
					<th>库房备注</th>
					<th>库房状态</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="list" items="${storageroomList}">
					<tr>
						<td class="font-blue"><a class="addtitle"
							href="javascript:void(0);"
							tabTitle='{"num":"warehouse_warehouses_toStorageRoomDetailPage_wareHouseId",
									"link":"./warehouse/storageRoomMag/toStorageRoomDetailPage.do?storageroomId=${list.storageroomId}",
									"title":"库房详情"}'>${list.storageRoomName}</a></td>
						<td>${list.comments}</td>
						<c:choose>
							<c:when test="${list.isEnable eq 1}">
								<td>未禁用</td>
							</c:when>
							<c:otherwise>
								<td class="font-red">已禁用</td>
							</c:otherwise>
						</c:choose>
						</td>
					</tr>
				</c:forEach>
				<c:if test="${  empty storageroomList}">
		<tr>
			<td colspan="3">
				暂无库位信息
			</td>
		</tr>
	    </c:if>
			</tbody>
		</table>
		
	</div>
	<div>
		<div class="title-container">
			<div class="table-title nobor">库存商品</div>
		</div>
		<table
			class="table table-bordered table-striped table-condensed table-centered">
			<tbody>
				<tr>
					<td class="table-smaller">商品总数</td>
					<td>${ warehouses.cnt}</td>
					<td class="table-smaller"></td>
					<td></td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
<script type="text/javascript"
	src='<%= basePath %>static/js/logistics/warehouseMag/warehouseForbid.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>