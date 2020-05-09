<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="库位分配" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="main-container">
	<div class="list-pages-search">
		<form action="${pageContext.request.contextPath}/warehouse/warehouseGoodsSet/index.do" method="post" id="search">
			<ul>
				<li>
				    <label class="infor_name">产品名称</label> 
				    <input type="text" class="input-middle" id="goodsName" name="goodsName" value="${warehouseGoodsSet.goodsName}"/>
				</li>
				<li>
				    <label class="infor_name">品牌</label>
				    <input type="text" class="input-middle" id="brandName" name="brandName" value="${warehouseGoodsSet.brandName}"/>
			    </li>
				<li>
				    <label class="infor_name">型号</label>
				    <input type="text" class="input-middle" id="model" name="model" value="${warehouseGoodsSet.model}"/>
			   </li>
				<li><label class="infor_name">订货号</label> 
				    <input type="text" class="input-middle" id="sku" name="sku" value="${warehouseGoodsSet.sku}"/>
			   </li>
				<li><label class="infor_name">物料编码</label>
				    <input type="text" class="input-middle" id="materialCode" name="materialCode" value="${warehouseGoodsSet.materialCode}"/>
			   </li>
				<li>
				   <label class="infor_name" >仓库</label> 
				   <select id="wareHouseId" name="wareHouseId" class="input-middle">
						<option value="">全部</option>
						<c:if test="${not empty warehouseList }">
							<c:forEach items="${warehouseList }" var="warehouse">
								<option value="${warehouse.warehouseId }"
								   <c:if test="${ not empty warehouseGoodsSet &&  warehouse.warehouseId == warehouseGoodsSet.wareHouseId }">selected="selected"</c:if>>${warehouse.warehouseName }</option>
							</c:forEach>
						</c:if>
					</select> 
			   </li>
				<li>
				   <label class="infor_name" >库房</label> 
				   <select class="input-middle" id="storageroomId" name="storageroomId">
						<option selected="selected" value="">全部</option>
						<c:if test="${not empty listRoom }">
							<c:forEach items="${listRoom }" var="listRoom">
								<option value="${listRoom.storageroomId }"
								   <c:if test="${ not empty warehouseGoodsSet && listRoom.storageroomId == warehouseGoodsSet.storageroomId }">selected="selected"</c:if>>${listRoom.storageRoomName }</option>
							</c:forEach>
						</c:if>
				   </select>
			   </li>
				<li>
				    <label class="infor_name">货区</label> 
					<select class="input-middle"  id="storageAreaId" name="storageAreaId">
							<option selected="selected" value="">全部</option>
							<c:if test="${not empty storageAreaList }">
							<c:forEach items="${storageAreaList }" var="storageAreaList">
								<option value="${storageAreaList.storageAreaId }"
								   <c:if test="${ not empty warehouseGoodsSet && storageAreaList.storageAreaId == warehouseGoodsSet.storageAreaId }">selected="selected"</c:if>>${storageAreaList.storageAreaName }</option>
							</c:forEach>
						</c:if>
					</select>
				</li>
				<li>
					<label class="infor_name" >货架</label> 
					<select class="input-middle" id="storageRackId" name="storageRackId">
							<option selected="selected" value="">全部</option>
							<c:if test="${not empty storageRackList }">
							<c:forEach items="${storageRackList }" var="storageRackList">
								<option value="${storageRackList.storageRackId }"
								   <c:if test="${ not empty warehouseGoodsSet && storageRackList.storageRackId == warehouseGoodsSet.storageRackId }">selected="selected"</c:if>>${storageRackList.storageRackName }</option>
							</c:forEach>
							</c:if>
					</select>
			   </li>
				<li>
					<label class="infor_name">库位</label> 
					<select class="input-middle"  id="storageLocationId" name="storageLocationId">
							<option selected="selected" value="">全部</option>
							<c:if test="${not empty storageLocationList }">
							<c:forEach items="${storageLocationList }" var="storageLocationList">
								<option value="${storageLocationList.storageLocationId }"
								   <c:if test="${ not empty warehouseGoodsSet && storageLocationList.storageLocationId == warehouseGoodsSet.storageLocationId }">selected="selected"</c:if>>${storageLocationList.storageLocationName }</option>
							</c:forEach>
							</c:if>
					</select>
				</li>
				<li>
				    <label class="infor_name">仓存备注</label> 
				    <input type="text" class="input-middle" id="comments" name="comments" value="${warehouseGoodsSet.comments}"/>
			   </li>
			   <li>
			       <label class="infor_name" >产品状态</label> 
			       <select class="input-middle" id="isDiscard" name="isDiscard">
						<option selected="selected" value="">全部</option>
						<option value="0" <c:if test="${warehouseGoodsSet.isDiscard != null and warehouseGoodsSet.isDiscard=='0'}">selected="selected"</c:if>>已禁用</option>
						<option value="1" <c:if test="${warehouseGoodsSet.isDiscard != null and warehouseGoodsSet.isDiscard=='1'}">selected="selected"</c:if>>非禁用</option>
				   </select>
			  </li>
			</ul>
			<div class="tcenter">
				<span class="bt-small bg-light-blue bt-bg-style mr20" onclick="search();">搜索</span> 
				<span class="bt-small bg-light-blue bt-bg-style mr20" onclick="reset();">重置</span> 
				<span class="bg-light-blue bt-bg-style bt-small" onclick="exportWarehouselist();">导出库位列表</span>
				<span class="bg-light-blue bt-bg-style bt-small" onclick="exportWarehouseGoodsSetlist()">导出商品库位</span>
				<span class="bg-light-blue bt-bg-style bt-small pop-new-data" layerParams='{"width":"450px","height":"200px","title":"设置商品库位","link":"./batchSetWarehouseGoodsJump.do"}'>设置商品库位</span>
			</div>
		</form>
	</div>
	<div class="list-page normal-list-page">
		<table
			class="table">
			<thead>
				<tr>
					<th class="wid4">选择</th>
					<th >产品名称</th>
					<th class="wid8">品牌</th>
					<th class="wid8">型号</th>
					<th class="wid8">物料编码</th>
					<th class="wid5">单位</th>
					<th >仓库</th>
					<th >库房</th>
					<th >货区</th>
					<th >货架</th>
					<th >库位</th>
					<th>仓存备注</th>
					<th class="wid6">在库数量</th>
					<th class="wid6">产品状态</th>
					<th class="wid7">操作</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="list" items="${warehouseGoodsSetList}" varStatus="num">
				<tr>
					<td><input type="checkbox" name="checkOne" id="checkOne" value="${list.goodsName}@${list.warehouseGoodsSetId}"></td>
					<td class="text-left">
                        <div >
                          <a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewgoods${list.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${list.goodsId}","title":"产品信息"}'>${list.goodsName}</a>
                        </div>
                        <div>${list.sku}</div>
                    </td>
					<td>${list.brandName}</td>
					<td>
						${list.model}
					</td>
					<td>${list.materialCode}</td>
					<td>${list.unitName}</td>
					<td>${list.wareHouseName}</td>
					<td>${list.storageroomName}</td>
					<td>${list.storageAreaName}</td>
					<td>${list.storageRackName}</td>
					<td>${list.storageLocationName}</td>
					<td>${list.comments}</td>
					<td>
						${list.num}
					</td>
				    <c:choose>
						<c:when test="${list.isDiscard eq 1}"><td class="font-red">已禁用</td></c:when>
						<c:otherwise><td >未禁用</td></c:otherwise>
					</c:choose>
					<td>
						<span class="bt-smallest bt-border-style border-red mr0" onclick="delRecode('${list.goodsName}','${list.warehouseGoodsSetId}');">删除</span>
					</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:if test="${empty warehouseGoodsSetList}">
		<!-- 查询无结果弹出 -->
		<div class="noresult">查询无结果！请尝试使用其他搜索条件。</div>
	    </c:if>
	</div>
	<c:if test="${not empty warehouseGoodsSetList}">
		<div>
		   <div class="inputfloat f_left ml10" style='padding-left:2.5px'>
		   		<div class="allchose">
		             <input type="checkbox" class="mt6 mr4" name="checkAll">
		             <label class="mr10 mt4">全选</label>
		             <span class="bt-middle bt-border-style border-red" onclick="delBatchRecode();">删除</span>
		        </div>
		    </div>
			<tags:page page="${page}"/>
		</div>
 	</c:if>
</div>

<script type="text/javascript" src='<%= basePath %>static/js/logistics/warehouseGoodsSet/warehouseGoodsSet.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>
