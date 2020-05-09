<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="编辑设备与试剂分类" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript"
	src='<%=basePath%>/static/js/ordergoods/goods/edit_equipment_category.js?rnd=<%=Math.random()%>'></script>
</head>
<body>
	<div class="formpublic formpublic1">
		<div>
			<!-- ------------选择列表产品后操作--------start------- -->
			<div class="controlled" id="confirmGoodsDiv">
				<!-- 搜索最后结果lastResult -->
				<form id="confirmForm">
					<ul class="lastResult">
						<li>
							<div class="infor_name ">
								<label>产品名称</label>
							</div>
							<div class="f_left">${rOrdergoodsLaunchGoodsJCategoryVo.goodsName }</div>
						</li>
						<li>
							<div class="infor_name mt0">
								<label>品牌/型号</label>
							</div>
							<div class="f_left">${rOrdergoodsLaunchGoodsJCategoryVo.brandName }/${rOrdergoodsLaunchGoodsJCategoryVo.model }</div>
						</li>
						<li>
							<div class="infor_name ">
								<label>产品信息</label>
							</div>
							<div class="f_left mr10">${rOrdergoodsLaunchGoodsJCategoryVo.sku }</div>
						</li>
						<li>
							<div class="infor_name mt0">
								<label>对应的试剂分类</label>
							</div>
							<div class="f_left inputfloat inputfloatmb0">
								<ul>
									<c:if test="${not empty ordergoodsCategry }">
                        				<c:forEach items="${ordergoodsCategry }" var="category">
											<li><input type="checkbox" name="categoryId" value="${category.ordergoodsCategoryId }"
											<c:forEach items="${rOrdergoodsLaunchGoodsJCategoryVo.categoryIds }" var="id">
												<c:if test="${id eq category.ordergoodsCategoryId }">
													checked="checked"
												</c:if>
											</c:forEach> 
											> <label>${category.categoryName }</label></li>
										</c:forEach>
									</c:if>
								</ul>
							</div>
						</li>
					</ul>
					<div class="add-tijiao  tcenter">
						<input type="hidden" name="rOrdergoodsLaunchGoodsJCategoryId" id="rOrdergoodsLaunchGoodsJCategoryId" value="${rOrdergoodsLaunchGoodsJCategoryVo.rOrdergoodsLaunchGoodsJCategoryId }"/>
						<input type="hidden" name="beforeParams" value='${beforeParams}'/>
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