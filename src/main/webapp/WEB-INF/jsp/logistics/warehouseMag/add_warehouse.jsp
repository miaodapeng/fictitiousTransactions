<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增仓库" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="addElement">
	<div class="add-main">
		<form method="post" id="addWarehouseForm"
			action="${pageContext.request.contextPath}/warehouse/warehouses/saveWarehouse.do">
			<ul class="add-detail floatnone">
			    <li>
					<div class="infor_name mt0">
						<label>是否是临时仓库</label>
					</div>
					<div class="f_left inputfloat">
						<input type="radio" name="isTemp" id="isTemp" value="1"/ style='margin-top:2px;'><label style='margin-right:20px;'>是</label>
						<input type="radio" name="isTemp" id="isTemp" checked="checked" value="0"/ style='margin-top:2px;'><span id="no"  style="color: black;">否</span>
					</div>
				</li>
				<li>
					<div class="infor_name">
						<span>*</span> <label>仓库名称</label>
					</div>
					<div class="f_left">
						<input type="text" class="input-largest" id="warehouseName"
							name="warehouseName" />
					</div>
				</li>
				<li>
					<div class="infor_name">
						<span>*</span> <label>仓库地区</label>
					</div>
					<div class="f_left">
						<select id="province" name="province" class="input-smaller" style="margin-right: 10px">
							<option value="">请选择</option>
							<c:if test="${not empty provinceList }">
								<c:forEach items="${provinceList }" var="province">
									<option value="${province.regionId }"
										<c:if test="${ not empty warehouses &&  province.regionId == warehouses.province }">selected="selected"</c:if>>${province.regionName }</option>
								</c:forEach>
							</c:if>
						</select> <select id="city" name="city" class="input-smaller" style="margin-right: 10px">
							<option value="">请选择</option>
							<c:if test="${not empty cityList }">
								<c:forEach items="${cityList }" var="city">
									<option value="${city.regionId }"
										<c:if test="${ not empty warehouses &&  city.regionId == warehouses.city }">selected="selected"</c:if>>${city.regionName }</option>
								</c:forEach>
							</c:if>
						</select> <select id="zone" name="zone" class="input-smaller">
							<option value="">请选择</option>
							<c:if test="${not empty zoneList }">
								<c:forEach items="${zoneList }" var="zone">
									<option value="${zone.regionId }"
										<c:if test="${ not empty warehouses &&  zone.regionId == warehouses.zone }">selected="selected"</c:if>>${zone.regionName }</option>
								</c:forEach>
							</c:if>
						</select>
					</div>
				</li>
				<li>
					<div class="infor_name">
						<span>*</span> <label>仓库地址</label>
					</div>
					<div class="f_left">
						<input type="text" class="input-largest" id="wareAddress"
							name="wareAddress" />
					</div>
				</li>
				<li>
					<div class="infor_name mt0">
						<label>仓库备注</label>
					</div>
					<div class="f_left">
						<input type="text" class="input-largest" id="comments"
							name="comments" />
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
	src='<%= basePath %>static/js/logistics/warehouseMag/add_warehouse.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript"
	src='<%= basePath %>static/js/region/index.js?rnd=<%=Math.random()%>'></script>	
<%@ include file="../../common/footer.jsp"%>