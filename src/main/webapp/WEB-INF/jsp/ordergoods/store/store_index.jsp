<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="店铺列表" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="content">
	<div class="searchfunc searchfuncpl0">
		<!-- <span class="bt-small bg-light-blue bt-bg-style pop-new-data"
			layerParams='{"width":"500px","height":"180px","title":"新增店铺","link":"./addstore.do"}'>新增店铺</span> -->
	</div>
	<div class="list-page normal-list-page">
  		<table class="table">
			<thead>
				<tr>
					<th class="wid6">序号</th>
					<th class="wid20">店铺名称</th>
					<th class="wid6">状态</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty stores}">
					<c:forEach items="${stores}" var="store" varStatus="status">
						<tr>
							<td>${status.count }</td>
							<td>${store.name }</td>
							<td>
								<c:choose>
									<c:when test="${store.isEnable eq 0}">
										<span class="offstate">禁用</span>
									</c:when>
									<c:when test="${store.isEnable eq 1}">
										<span class="onstate">启用</span>
									</c:when>
								</c:choose>
							</td>
							<td>
								<c:choose>
									<c:when test="${store.isEnable eq 0}">
										<span class="edit-user"
												onclick="setDisabled(${store.ordergoodsStoreId},1);">启用</span>
									</c:when>
									<c:when test="${store.isEnable eq 1}">
										<span class="forbid clcforbid"
												onclick="setDisabled(${store.ordergoodsStoreId},0);">禁用</span>
										<span class="edit-user pop-new-data"
											layerParams='{"width":"500px","height":"180px","title":"编辑店铺","link":"./editstore.do?ordergoodsStoreId=${store.ordergoodsStoreId}"}'>编辑</span>
										<span class="edit-user addtitle"
											tabTitle='{"num":"ordergoodscategory${store.ordergoodsStoreId}","link":"./ordergoods/manage/categoryindex.do?ordergoodsStoreId=${store.ordergoodsStoreId}","title":"产品分类"}'>产品分类</span>
										<span class="edit-user addtitle"
											tabTitle='{"num":"ordergoodsgoods${store.ordergoodsStoreId}","link":"./ordergoods/manage/goodslist.do?ordergoodsStoreId=${store.ordergoodsStoreId}","title":"产品列表"}'>产品列表</span>
										<c:if test="${store.ordergoodsStoreId eq 1}">	
										<span class="edit-user addtitle"
											tabTitle='{"num":"goodscategories${store.ordergoodsStoreId}","link":"./ordergoods/manage/goodscategories.do?ordergoodsStoreId=${store.ordergoodsStoreId}","title":"设备与试剂分类"}'>设备与试剂分类</span>
										<span class="edit-user addtitle"
											tabTitle='{"num":"ordergoodslaunch${store.ordergoodsStoreId}","link":"./ordergoods/manage/ordergoodslaunch.do?ordergoodsStoreId=${store.ordergoodsStoreId}","title":"投放列表"}'>投放列表</span>
										</c:if>
										<span class="edit-user addtitle"
											tabTitle='{"num":"getordergoodsaccount${store.ordergoodsStoreId}","link":"./ordergoods/manage/getordergoodsaccount.do?ordergoodsStoreId=${store.ordergoodsStoreId}","title":"经销商列表"}'>经销商列表</span>
									</c:when>
								</c:choose>
							</td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
		<c:if test="${empty stores}">
			<!-- 查询无结果弹出 -->
			<div class="noresult">查询无结果！</div>
		</c:if>
	</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/ordergoods/store/store_index.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../../common/footer.jsp"%>