<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="库房详情" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="content mt10">
	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">基本信息</div>
			<div class="title-click nobor">
				<span class="brand-color1 addtitle mr10"
					tabTitle='{"num":"warehouse_storageRoomMag_editStorageRoomJump_storageroomId","title":"编辑库房","link":"./warehouse/storageRoomMag/editStorageRoomJump.do?storageroomId=${storageroom.storageroomId}"}'>编辑</span>
				<c:choose>
					<c:when test="${storageroom.isEnable eq 1}">
						<span class="font-red pop-new-data"
							layerParams='{"width":"40%","height":"220px","title":"禁用库房","link":"./disableStorageroom.do?storageroomId=${storageroom.storageroomId}&isEnable=${storageroom.isEnable}&wareIsEnable=${storageroom.wareIsEnable}"}'>禁用</span>
					</c:when>
					<c:otherwise>
						<span id="start">启用</span>
						<input type="hidden" id="storage_roomId"
							value="${storageroom.storageroomId}" />
						<input type="hidden" id="is_Enable"
							value="${storageroom.isEnable}">
					    <input type="hidden" id="wareIsEnable" name="wareIsEnable" value="${storageroom.wareIsEnable}" />
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<table
			class="table table-bordered table-striped table-condensed table-centered">
			<tbody>
				<tr>
					<td class="table-smaller" id="storageRoomName"
						name="storageRoomName">库房名称</td>
					<td>${storageroom.storageRoomName}</td>
					<td class="table-smaller">所属仓库</td>
					<td>${storageroom.warehouseName}</td>
				</tr>
				<tr>
					<td>创建人</td>
					<td>${storageroom.creatorName}</td>
					<td>创建时间</td>
					<td><date:date value="${storageroom.addTime}" /></td>
				</tr>
				<tr>
					<td>库房温度要求</td>
					<c:choose>
						<c:when test="${not empty storageroom.temperatureMin && not empty storageroom.temperatureMax }">
							<td>${storageroom.temperatureMin}-${storageroom.temperatureMax}℃</td>
						</c:when>
						<c:otherwise>
							<td>--</td>
						</c:otherwise>
					</c:choose>
					<td>库房湿度要求</td>
					<c:choose>
						<c:when test="${not empty storageroom.humidityMin && not empty storageroom.humidityMax }">
							<td>${storageroom.humidityMin}-${storageroom.humidityMax}%</td>
						</c:when>
						<c:otherwise>
							<td>--</td>
						</c:otherwise>
					</c:choose>
				</tr>
				<tr>
					<td>库房状态</td>
					<c:choose>
						<c:when test="${storageroom.isEnable eq 1}">
							<td>未禁用</td>
						</c:when>
						<c:otherwise>
							<td class="font-red">已禁用</td>
						</c:otherwise>
					</c:choose>
					<td>禁用原因</td>
					<td>${storageroom.enableComment}</td>
				</tr>
				<tr>
					<td>禁用时间</td>
					<td><date:date value="${storageroom.enabletime}" /></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td>库房备注</td>
					<td colspan="3">${storageroom.comments}</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">内部货区信息</div>
		</div>
		<table
			class="table table-bordered table-striped table-condensed table-centered">
			<thead>
				<tr>
					<th>货区名称</th>
					<th>货区备注</th>
					<th>货区状态</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="list" items="${storageAreaList}">
					<tr>
						<td class="font-blue"><a class="addtitle"
							href="javascript:void(0);"
							tabTitle='{"num":"warehouse_storageAreaMag_toStorageAreaDetailPage_storageAreaId",
									"link":"./warehouse/storageAreaMag/toStorageAreaDetailPage.do?storageAreaId=${list.storageAreaId}",
									"title":"货区详情"}'>${list.storageAreaName}</a></td>
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
			</tbody>
		</table>
		<c:if test="${  empty storageAreaList}">
		<div class="noresult">暂无货区信息</div>
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
					<td>${storageroom.cnt }</td>
					<td class="table-smaller"></td>
					<td></td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
<script type="text/javascript"
	src='<%=basePath%>static/js/logistics/storageRoomMag/storageroomForbid.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>
