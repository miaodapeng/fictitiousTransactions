<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增货区" scope="application" />
<%@ include file="../../common/common.jsp"%>
	<div class="addElement">
		<div class="add-main">
			<form method="post" id="addStorageareaForm" action="${pageContext.request.contextPath}/warehouse/storageAreaMag/saveStorageArea.do">
				<ul class="add-detail floatnone">
					<li>
						<div class="infor_name">
							<span>*</span> <label>货区名称</label>
						</div>
						<div class="f_left">
							<input type="text" class="input-largest" id="storageAreaName" name="storageAreaName"/>
						</div>
					</li>
					<li>
						<div class="infor_name">
							<span>*</span> <label>所属库房 </label>
						</div>
						<div class="f_left">
							<select id="warehouseId" name="warehouseId" class="input-middle" style="margin-right: 5px">
								<option value="0">请选择仓库</option>
								<c:if test="${not empty warehouseList }">
									<c:forEach items="${warehouseList }" var="warehouse">
										<option value="${warehouse.warehouseId }">${warehouse.warehouseName }</option>
									</c:forEach>
								</c:if>
							</select> 
							<select id="storageRoomId" name="storageRoomId" class="input-middle" style="margin-right: 5px">
								<option value="0">请选择库房</option>
								<%-- <c:if test="${not empty cityList }">
									<c:forEach items="${cityList }" var="city">
										<option value="${city.regionId }"
											<c:if test="${ not empty warehouses &&  city.regionId == warehouses.city }">selected="selected"</c:if>>${city.regionName }</option>
									</c:forEach>
								</c:if> --%>
							</select>
						</div>
					</li>
					<li>
						<div class="infor_name mt0">
							<label>货区备注</label>
						</div>
						<div class="f_left">
							<input type="text" class="input-largest" id="comments" name="comments"/>
						</div>
					</li>
					<div class="add-tijiao tcenter">
					<button type="submit">提交</button>
				    </div>
				</ul>
			</form>
		</div>
	</div>
	<script type="text/javascript"
	src='<%= basePath %>static/js/logistics/storageAreaMag/add_storagearea.js?rnd=<%=Math.random()%>'></script>
	<%@ include file="../../common/footer.jsp"%>
