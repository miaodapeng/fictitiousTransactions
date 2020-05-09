<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="仓库管理" scope="application" />
<%@ include file="../../common/common.jsp"%>
	<div class="searchfunc">
		<form
			action="${pageContext.request.contextPath}/warehouse/warehouses/index.do"
			method="post" id="search">
			<ul>
				<li><label class="infor_name">仓库名称</label> <input type="text"
					class="input-middle" name="warehouseName" id="warehouseName"
					value="${warehouses.warehouseName }" /></li>
			<%-- 	<li><label class="infor_name">所属公司</label> <select
					class="input-middle f_left" name="companyId" id="companyId">
						<option selected="selected" value="">请选择</option>
						<option value="${company.companyId }"
						   <c:if test="${warehouses.companyId eq company.companyId }">selected="selected"</c:if> >${company.companyName }</option>
				</select></li> --%>
				<li><label class="infor_name">仓库状态</label> <select class="input-middle f_left" name="isEnable" id="isEnable">
						<option selected="selected" value="">全部</option>
						<option value="0" <c:if test="${warehouses.isEnable != null and warehouses.isEnable=='0'}">selected="selected"</c:if>>已禁用</option>
						<option value="1" <c:if test="${warehouses.isEnable != null and warehouses.isEnable=='1'}">selected="selected"</c:if>>未禁用</option>
					</select></li>
				<li><label class="infor_name">仓库地区</label> <select
					id="province" name="province" class="input-smaller">
						<option value="">请选择</option>
						<c:if test="${not empty provinceList }">
							<c:forEach items="${provinceList }" var="province">
								<option value="${province.regionId }"
									<c:if test="${ not empty warehouses &&  province.regionId == warehouses.province }">selected="selected"</c:if>>${province.regionName }</option>
							</c:forEach>
						</c:if>
				</select> <select id="city" name="city" class="input-smaller">
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
				</select></li>
			</ul>
			<div class="tcenter">
				<span class="bg-light-blue bt-bg-style bt-small" onclick="search();"
					id="searchSpan">搜索</span> <span
					class="bt-small bg-light-blue bt-bg-style" onclick="reset();">重置</span>
				<span class="bg-light-blue bt-bg-style bt-small addtitle"
					tabTitle='{"num":"warehouse_warehouses_addwarehouse_wareHouseId","link":"./warehouse/warehouses/addwarehouse.do","title":"新增仓库"}'>新增仓库</span>
			</div>
		</form>
	</div>
	<div class="content">
		<table class="table table-bordered table-striped table-condensed table-centered" >
			<thead>
				<tr>
					<th style="width: 60px">序号</th>
					<th style="width: 100xp">仓库名称</th>
					<th style="width: 200px">仓库地区</th>
					<th>仓库地址</th>
					<th>仓库备注</th>
					<th style="width: 100px">库存商品数量</th>
					<th>所属公司</th>
					<th style="width: 100px">仓库状态</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="list" items="${warehouseList}" varStatus="num">
					<tr>
						<td>${num.count}</td>
						<td>
							<div class="font-blue">
								<a class="addtitle" href="javascript:void(0);"
									tabTitle='{"num":"warehouse_warehouses_toWarehouseDetailPage_wareHouseId${list.warehouseId}",
									"link":"./warehouse/warehouses/toWarehouseDetailPage.do?warehouseId=${list.warehouseId}",
									"title":"仓库详情"}'>${list.warehouseName}</a>
							</div>
						</td>
						<td>${list.areaName}</td>
						<td>${list.address}</td>
						<td>${list.comments}</td>
						<td>${list.cnt}</td>
						<td>${list.companyName}</td>
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
		<c:if test="${  empty warehouseList}">
		<!-- 查询无结果弹出 -->
		<div class="noresult">查询无结果！请尝试使用其他搜索条件。</div>
	</c:if>
	<c:if test="${not empty warehouseList}">
		<tags:page page="${page}" />
	</c:if>
		</div>
	
	
	<script type="text/javascript"
	src='<%= basePath %>static/js/logistics/warehouseMag/index_warehouse_mag.js?rnd=<%=Math.random()%>'></script>
	<script type="text/javascript"
	src='<%= basePath %>static/js/region/index.js?rnd=<%=Math.random()%>'></script>	
   <%@ include file="../../common/footer.jsp"%>