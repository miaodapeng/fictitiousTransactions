<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="库位详情" scope="application" />
<%@ include file="../../common/common.jsp"%>
	<div class="content mt10">
		<div class="parts">
			<div class="title-container">
				<div class="table-title nobor">基本信息</div>
				<div class="title-click nobor">
					<span class="brand-color1 addtitle mr10"
						tabTitle='{"num":"warehouse_storageLocationMag_editStoragelocationJump_storageLocationId","title":"编辑库位","link":"./warehouse/storageLocationMag/editStoragelocationJump.do?storageLocationId=${storageLocation.storageLocationId}"}'>编辑</span>
				    <c:choose>
					<c:when test="${storageLocation.isEnable eq 1}">
						<span class="font-red pop-new-data"
							layerParams='{"width":"40%","height":"220px","title":"禁用库位","link":"./disableStorageLocation.do?storageLocationId=${storageLocation.storageLocationId}&isEnable=${storageLocation.isEnable}&scIsEnable=${storageLocation.scIsEnable }"}'>禁用</span>
					</c:when>
					<c:otherwise>
						<span id="start">启用</span>
						<input type="hidden" id="storage_LocationId" value="${storageLocation.storageLocationId}" />
						<input type="hidden" id="is_Enable" value="${storageLocation.isEnable}">
						<input type="hidden" id="scIsEnable" value="${storageLocation.scIsEnable}">
					</c:otherwise>
				</c:choose>
				</div>
			</div>
			<table
				class="table table-bordered table-striped table-condensed table-centered">
				<tbody>
					<tr>
						<td class="table-smaller" id="storageRackName" name="storageRackName">库位名称</td>
						<td>${storageLocation.storageLocationName}</td>
						<td class="table-smaller">所属货架</td>
						<td>${storageLocation.wareHouseName}&nbsp;&nbsp; ${storageLocation.storageRoomName}&nbsp;&nbsp; ${storageLocation.storageAreaName}&nbsp;&nbsp; ${storageLocation.storageRackName}  </td>
					</tr>
					<tr>
						<td>创建人</td>
						<td>${storageLocation.creatorName}</td>
						<td>创建时间</td>
						<td><date:date value="${storageLocation.addTime}"/></td>
					</tr>
					<tr>
						<td>库位状态</td>
						<c:choose>
								<c:when test="${storageLocation.isEnable eq 1}"><td>已启用</td></c:when>
								<c:otherwise><td class="font-red">已禁用</td></c:otherwise>
							</c:choose>
						<td>禁用原因</td>
						<td>${storageLocation.enableComment}</td>
					</tr>
					<tr>
						<td>禁用时间</td>
						<td><date:date value="${storageLocation.enabletime}"/></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>库位备注</td>
						<td colspan="3">${storageLocation.comments}</td>
					</tr>
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
						<td>${storageLocation.cnt }</td>
						<td class="table-smaller"></td>
						<td></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
<script type="text/javascript"
	src='<%=basePath%>static/js/logistics/storageLocationMag/storagelocationForbid.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>