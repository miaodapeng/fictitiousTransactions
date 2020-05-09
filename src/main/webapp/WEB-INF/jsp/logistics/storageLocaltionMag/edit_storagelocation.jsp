<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="编辑库位" scope="application" />
<%@ include file="../../common/common.jsp"%>
	<div class="addElement">
		<div class="add-main">
			<form method="post" id="addStoragelocationForm" action="${pageContext.request.contextPath}/warehouse/storageLocationMag/editStorageLocation.do?storageLocationId=${storageLocationInfo.storageLocationId}">
				<ul class="add-detail floatnone">
					<li>
						<div class="infor_name">
							<span>*</span> <label>库位名称</label>
						</div>
						<div class="f_left">
							<input type="text" class="input-largest" id="storageLocationName" name="storageLocationName" value="${storageLocationInfo.storageLocationName}"/>
						</div>
					</li>
					<li>
						<div class="infor_name">
							<span>*</span> <label>所属库房 </label>
						</div>
						<div class="f_left">
							<select id="wareHouseId" name="wareHouseId" class="input-middle" style="margin-right: 5px">
								<option value="0">请选择仓库</option>
								<c:if test="${not empty warehouseList }">
									<c:forEach items="${warehouseList }" var="warehouse">
										<option value="${warehouse.warehouseId }"
										<c:if test="${ not empty storageLocationInfo &&  warehouse.warehouseId == storageLocationInfo.wareHouseId }">selected="selected"</c:if>>${warehouse.warehouseName }</option>
									</c:forEach>
								</c:if>
							</select> 
							<select id="storageroomId" name="storageroomId" class="input-middle" style="margin-right: 5px">
								<option value="0">请选择库房</option>
								<c:if test="${not empty listRoom }">
									<c:forEach items="${listRoom }" var="storageroom">
										<option value="${storageroom.storageroomId }"
											<c:if test="${ not empty storageLocationInfo &&  storageroom.storageroomId == storageLocationInfo.storageroomId }">selected="selected"</c:if>>${storageroom.storageRoomName }</option>
									</c:forEach>
								</c:if>
							</select>
							<select id="storageAreaId" name="storageAreaId" class="input-middle" style="margin-right: 5px">
								<option value="0">请选择货区</option>
								<c:if test="${not empty storageAreaList }">
									<c:forEach items="${storageAreaList }" var="storageArea">
										<option value="${storageArea.storageAreaId }"
											<c:if test="${ not empty storageLocationInfo &&  storageArea.storageAreaId == storageLocationInfo.storageAreaId }">selected="selected"</c:if>>${storageArea.storageAreaName }</option>
									</c:forEach>
								</c:if>
							</select>
							<select id="storageRackId" name="storageRackId" class="input-middle" style="margin-right: 5px">
								<option value="0">请选择货架</option>
								<c:if test="${not empty storageRackList }">
									<c:forEach items="${storageRackList }" var="storagerack">
										<option value="${storageLocationInfo.storageRackId }"
											<c:if test="${ not empty storageLocationInfo &&  storagerack.storageRackId == storageLocationInfo.storageRackId }">selected="selected"</c:if>>${storagerack.storageRackName }</option>
									</c:forEach>
								</c:if>
							</select>
						</div>
					</li>
					<li>
						<div class="infor_name mt0">
							<label>库位备注</label>
						</div>
						<div class="f_left">
							<input type="text" class="input-largest" id="comments" name="comments" value="${storageRackInfo.comments}"/>
						</div>
					</li>
					<div class="add-tijiao tcenter">
					<input type="hidden" name="beforeParams" value='${beforeParams}'>
					<input type="hidden" name="storageLocationInfo.storageLocationId"
							value="${storageLocationInfo.storageLocationId}">
					<button type="submit">提交</button>
				    </div>
				</ul>
			</form>
		</div>
	</div>
	<script type="text/javascript" src='<%= basePath %>static/js/logistics/storageLocationMag/add_storagelocation.js?rnd=<%=Math.random()%>'></script>
    <%@ include file="../../common/footer.jsp"%>
