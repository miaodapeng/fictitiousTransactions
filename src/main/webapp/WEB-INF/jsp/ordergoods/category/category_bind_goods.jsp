<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="添加产品" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript"
	src='<%=basePath%>/static/js/ordergoods/category/category_bind_goods.js?rnd=<%=Math.random()%>'></script>
</head>
<body>
	<div class="formpublic formpublic1">
		<div>
			<!-- ------------产品数据列表--------start------- -->
			<div class="controlled" id="goodsListDiv">
				<!-- 搜索表格出来 -->
				<ul class="searchTable">
					<li>
						<form method="post" id="search">
							<div class="infor_name ">
								<span>*</span> <label>产品名称</label>
							</div>
							<div class="f_left table-larger">
								<div class="">
									<input type="text" class="input-larger mr5"
										placeholder="请输入产品名称/订货号/品牌/型号等关键词" id="searchContent"
										name="searchContent" value="${searchContent}"> <span
										class="bt-bg-style bt-small bg-light-blue"
										onclick="search2();" id="errorMes">搜索</span>
								</div>
							</div>
						</form>
					</li>
				</ul>
				<div>
					<table
						class="table table-bordered table-striped table-condensed table-centered mb10">
						<thead>
							<th class="table-smallest">选择</th>
							<th class="table-smallest8">订货号</th>
							<th>产品名称</th>
							<th>品牌</th>
							<th class="table-smallest8">型号</th>
							<th class="table-smallest5">单位</th>
							<th>物料编码</th>
							<th class="table-smallest">上架</th>
							<th class="table-smallest">废弃</th>
						</thead>
						<tbody>
							<c:forEach var="list" items="${ordergoodsGoodsList}"
								varStatus="status">
								<tr>
									<td>
									<c:choose>
										<c:when test="${list.ordergoodsCategoryId > 0 }">
											
										</c:when>
										<c:otherwise>
										<input type="checkbox" name="checkOne"
											value="${list.ordergoodsGoodsId}">
										</c:otherwise>
									</c:choose>
									</td>
									<td>${list.sku}</td>
									<td>${list.goodsName}</td>
									<td>${list.brandName}</td>
									<td>${list.model}</td>
									<td>${list.unit}</td>
									<td>${list.materialCode}</td>
									<td><c:choose>
											<c:when test="${list.isOnSale eq 1 }">是</c:when>
											<c:when test="${list.isOnSale eq 0 }">否</c:when>
											<c:otherwise>-</c:otherwise>
										</c:choose></td>
									<td><c:choose>
											<c:when test="${list.isDiscard eq 1 }">是</c:when>
											<c:when test="${list.isDiscard eq 0 }">否</c:when>
											<c:otherwise>-</c:otherwise>
										</c:choose></td>
								</tr>
							</c:forEach>
							<c:if test="${empty ordergoodsGoodsList}">
								<!-- 查询无结果弹出 -->
								<tr>
									<td colspan='9'>查询无结果！请尝试使用其他搜索条件。</td>
								</tr>
							</c:if>
						</tbody>
					</table>
				</div>

			</div>
			<c:if test="${not empty ordergoodsGoodsList}">
			<div>
				<div class="inputfloat f_left">
					<input type="checkbox" class="mt6 mr4" name="checkAll"
						autocomplete="off"> <label class="mr10 mt4">全选</label><span
				class="bt-bg-style bg-light-blue bt-small mr10" onclick="selectGoods(${ordergoodsGoodsVo.ordergoodsCategoryId});">选择</span>
				</div>
				<tags:page page="${page}" optpage="n" />
			</div>
			</c:if>
			<!-- ------------产品数据列表--------end------- -->
		</div>
	</div>
</body>
</html>