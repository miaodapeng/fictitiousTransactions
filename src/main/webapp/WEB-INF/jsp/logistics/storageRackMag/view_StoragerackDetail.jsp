<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="货架详情" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="content mt10">
	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">基本信息</div>
			<div class="title-click nobor">
				<span class="brand-color1 addtitle mr10"
					tabTitle='{"num":"warehouse_storageRackMag_editStoragerackJump_storageRackId","title":"编辑货架","link":"./warehouse/storageRackMag/editStoragerackJump.do?storageRackId=${storageRack.storageRackId}"}'>编辑</span>
			    <c:choose>
					<c:when test="${storageRack.isEnable eq 1}">
						<span class="font-red pop-new-data"
							layerParams='{"width":"40%","height":"220px","title":"禁用货架","link":"./disableStorageRack.do?storageRackId=${storageRack.storageRackId}&isEnable=${storageRack.isEnable}&sa_is_enable=${storageRack.sa_is_enable }"}'>禁用</span>
					</c:when>
					<c:otherwise>
						<span id="start">启用</span>
						<input type="hidden" id="storage_RackId"
							value="${storageRack.storageRackId}" />
						<input type="hidden" id="is_Enable" value="${storageRack.isEnable}">
						<input type="hidden" id="sa_is_enable" value="${storageRack.sa_is_enable}">
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<table
			class="table table-bordered table-striped table-condensed table-centered">
			<tbody>
				<tr>
					<td class="table-smaller" id="storageRackName"
						name="storageRackName">货架名称</td>
					<td>${storageRack.storageRackName}</td>
					<td class="table-smaller">所属货区</td>
					<td>${storageRack.wareHouseName}&nbsp;&nbsp;
						${storageRack.storageRoomName}&nbsp;&nbsp; ${storageRack.storageAreaName}</td>
				</tr>
				<tr>
					<td>创建人</td>
					<td>${storageRack.creatorName}</td>
					<td>创建时间</td>
					<td><date:date value="${storageRack.addTime}" /></td>
				</tr>
				<tr>
					<td>货架状态</td>
					<c:choose>
						<c:when test="${storageRack.isEnable eq 1}">
							<td>未禁用</td>
						</c:when>
						<c:otherwise>
							<td class="font-red">已禁用</td>
						</c:otherwise>
					</c:choose>
					<td>禁用原因</td>
					<td>${storageRack.enableComment}</td>
				</tr>
				<tr>
					<td>禁用时间</td>
					<td><date:date value="${storageRack.enabletime}" /></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td>货架备注</td>
					<td colspan="3">${storageRack.comments}</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">内部库位信息</div>
		</div>
		<table
			class="table table-bordered table-striped table-condensed table-centered">
			<thead>
				<tr>
					<th>库位名称</th>
					<th>库位备注</th>
					<th>库位状态</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="list" items="${StorageLocationList}">
					<tr>
						<td class="font-blue">
						<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"warehouse_storageLocationMag_addStorageLocation_storageLocationId",
									"link":"./warehouse/storageLocationMag/toStorageLocationDetailPage.do?storageLocationId=${list.storageLocationId}",
									"title":"库位详情"}'>${list.storageLocationName}</a>
						</td>
						<td>${list.comments}</td>
						<c:choose>
							<c:when test="${list.isEnable eq 1}">
								<td>有效</td>
							</c:when>
							<c:otherwise>
								<td class="font-red">无效</td>
							</c:otherwise>
						</c:choose>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:if test="${  empty StorageLocationList}">
		<div class="noresult">暂无库位信息</div>
	    </c:if>
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
					<td>${storageRack.cnt }</td>
					<td class="table-smaller"></td>
					<td></td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
<script type="text/javascript"
	src='<%=basePath%>static/js/logistics/storageRackMag/storagerackForbid.js?rnd=<%=Math.random()%>'></script>
	<%@ include file="../../common/footer.jsp"%>