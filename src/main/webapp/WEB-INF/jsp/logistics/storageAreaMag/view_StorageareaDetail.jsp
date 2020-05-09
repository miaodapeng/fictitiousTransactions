<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="货区详情" scope="application" />
<%@ include file="../../common/common.jsp"%>
	<div class="content mt10">
		<div class="parts">
			<div class="title-container">
				<div class="table-title nobor">基本信息</div>
				<div class="title-click nobor">
					<span class="brand-color1 addtitle mr10"
						tabTitle='{"num":"warehouse_storageAreaMag_editStorageAreaJump_storageAreaId","title":"编辑库房","link":"./warehouse/storageAreaMag/editStorageAreaJump.do?storageAreaId=${storageArea.storageAreaId}"}'>编辑</span>
				    <c:choose>
					<c:when test="${storageArea.isEnable eq 1}">
						<span class="font-red pop-new-data"
							layerParams='{"width":"40%","height":"220px","title":"禁用货区","link":"./disableStorageArea.do?storageAreaId=${storageArea.storageAreaId}&isEnable=${storageArea.isEnable}&st_is_enable=${storageArea.st_is_enable}"}'>禁用</span>
					</c:when>
					<c:otherwise>
						<span id="start">启用</span>
						<input type="hidden" id="storage_AreaId"
							value="${storageArea.storageAreaId}" />
						<input type="hidden" id="is_Enable"
							value="${storageArea.isEnable}">
							 <input type="hidden" id="st_is_enable" name="st_is_enable" value="${storageArea.st_is_enable}" />
					</c:otherwise>
				    </c:choose>
				</div>
			</div>
			<table
				class="table table-bordered table-striped table-condensed table-centered">
				<tbody>
					<tr>
						<td class="table-smaller" id="storageRoomName" name="storageRoomName">货区名称</td>
						<td>${storageArea.storageAreaName}</td>
						<td class="table-smaller">所属库房</td>
						<td>${storageArea.wareHouseName}&nbsp;&nbsp;${storageArea.storageRoomName}   </td>
					</tr>
					<tr>
						<td>创建人</td>
						<td>${storageArea.creatorName}</td>
						<td>创建时间</td>
						<td><date:date value="${storageArea.addTime}"/></td>
					</tr>
					<tr>
						<td>货区状态</td>
						<c:choose>
								<c:when test="${storageArea.isEnable eq 1}"><td>未禁用</td></c:when>
								<c:otherwise><td class="font-red">已禁用</td></c:otherwise>
							</c:choose>
						<td>禁用原因</td>
						<td>${storageArea.enableComment}</td>
					</tr>
					<tr>
						<td>禁用时间</td>
						<td><date:date value="${storageArea.enabletime}"/></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>货区备注</td>
						<td colspan="3">${storageArea.comments}</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="parts">
			<div class="title-container">
				<div class="table-title nobor">内部货架信息</div>
			</div>
			<table
				class="table table-bordered table-striped table-condensed table-centered">
				<thead>
					<tr>
						<th>货架名称</th>
						<th>货架备注</th>
						<th>货架状态</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="list" items="${storageRackList}">
						<tr>
							<td class="font-blue">
							<a class="addtitle" href="javascript:void(0);"
								tabTitle='{"num":"warehouse_storageRackMag_toStorageRackDetailPage_storageRackId",
									"link":"./warehouse/storageRackMag/toStorageRackDetailPage.do?storageRackId=${list.storageRackId}",
									"title":"货架详情"}'>${list.storageRackName}</a></td>
							<td>${list.comments}</td>
							<c:choose>
								<c:when test="${list.isEnable eq 1}"><td>有效</td></c:when>
								<c:otherwise><td class="font-red">无效</td></c:otherwise>
							</c:choose>
						</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<c:if test="${  empty storageRackList}">
			<div class="noresult">暂无货架信息</div>
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
						<td>${storageArea.cnt }</td>
						<td class="table-smaller"></td>
						<td></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
<script type="text/javascript"
	src='<%=basePath%>static/js/logistics/storageAreaMag/storageareaForbid.js?rnd=<%=Math.random()%>'></script>
	<%@ include file="../../common/footer.jsp"%>
