<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="添加设备与试剂分类" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript"
	src='<%=basePath%>/static/js/ordergoods/goods/add_equipment_category.js?rnd=<%=Math.random()%>'></script>
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
							<th class="table-smallest8">订货号</th>
							<th>产品名称</th>
							<th>品牌</th>
							<th class="table-smallest8">型号</th>
							<th class="table-smallest5">单位</th>
							<th>物料编码</th>
							<th class="table-smallest">上架</th>
							<th class="table-smallest">废弃</th>
							<th class="table-smallest">操作</th>
						</thead>
						<tbody>
							<c:forEach var="list" items="${goodsList}"
								varStatus="status">
								<tr>
									<td>${list.sku}</td>
									<td>${list.goodsName}</td>
									<td>${list.brandName}</td>
									<td>${list.model}</td>
									<td>${list.unitName}</td>
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
									<td>
										<a href="javascript:void(0);"
											onclick="selectGoods('${list.goodsId}','${list.sku}','${list.goodsName}','${list.brandName}','${list.model}','${list.unitName}');">选择</a>
									</td>
								</tr>
							</c:forEach>
							<c:if test="${empty goodsList}">
								<!-- 查询无结果弹出 -->
								<tr>
									<td colspan='9'>查询无结果！请尝试使用其他搜索条件。</td>
								</tr>
							</c:if>
						</tbody>
					</table>
				</div>

				<div>
					<tags:page page="${page}" optpage="n" />
				</div>
			</div>
			<!-- ------------产品数据列表--------end------- -->
			<!-- ------------选择列表产品后操作--------start------- -->
			<div class="controlled none" id="confirmGoodsDiv">
				<!-- 搜索最后结果lastResult -->
				<form id="confirmForm">
					<ul class="lastResult">
						<li>
							<div class="infor_name ">
								<span>*</span> <label>产品名称</label>
							</div>
							<div class="f_left table-largest content1">
								<div class="">
									<a class="font-blue mr10 productname2 addtitle2" id="confirmGoodsName"></a>
									<span class="bt-bg-style bt-small bg-light-blue searchAgain" onclick="againSearch();">重新搜索</span>
								</div>
							</div>
						</li>
						<li>
							<div class="infor_name mt0">
								<label>品牌/型号</label>
							</div>
							<div class="f_left" id="confirmGoodsBrandNameModel"></div>
						</li>
						<li>
							<div class="infor_name ">
								<label>产品信息</label>
							</div>
							<div class="f_left mr10" id="confirmGoodsContent"></div>
						</li>
						<li>
							<div class="infor_name mt0">
								<label>对应的试剂分类</label>
							</div>
							<div class="f_left inputfloat inputfloatmb0">
								<ul>
									<c:if test="${not empty ordergoodsCategry }">
                        				<c:forEach items="${ordergoodsCategry }" var="category">
											<li><input type="checkbox" name="categoryId" value="${category.ordergoodsCategoryId }"> <label>${category.categoryName }</label></li>
										</c:forEach>
									</c:if>
								</ul>
							</div>
						</li>
					</ul>
					<div class="add-tijiao  tcenter">
						<input type="hidden" name="goodsId" id="goodsId"/>
						<input type="hidden" name="ordergoodsStoreId" id="ordergoodsStoreId" value="${ordergoodsStoreId}"/>
						<button class="bt-bg-style bg-deep-green" type="button" onClick="confirmSubmit();">提交</button>
						<button id="close-layer" type="button" class="dele">取消</button>
					</div>
				</form>
			</div>
			<!-- ------------选择列表产品后操作--------end------- -->
		</div>
	</div>
</body>
</html>