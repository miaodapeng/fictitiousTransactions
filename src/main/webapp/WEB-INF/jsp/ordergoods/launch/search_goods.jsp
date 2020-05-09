<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="搜索设备" scope="application" />	  
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%=basePath%>/static/js/ordergoods/launch/search_goods.js?rnd=<%=Math.random()%>'></script>
<div class="formpublic formpublic1">
	<div>
		<!-- ------------产品数据列表--------start------- -->
		<div class="controlled" id="goodsListDiv">
			<!-- 搜索表格出来 -->
			<ul class="searchTable">
				<li>
					<form method="post" id="myform" action="${pageContext.request.contextPath}/ordergoods/manage/searchgoods.do">
						<div class="infor_name ">
							<span>*</span> <label>产品名称</label>
						</div>
						<div class="f_left table-larger">
							<div class="">
								<input type="text" class="input-larger mr5"
									placeholder="请输入产品名称/订货号/品牌/型号等关键词" id="searchContent"
									name="searchContent" value="${searchContent}"> <span
									class="bt-bg-style bt-small bg-light-blue"
									onclick="search();" id="errorMes">搜索</span>
							</div>
							<div id="searchNameError"></div>
						</div>
					</form>
				</li>
			</ul>
			<div>
				<table
					class="table table-bordered table-striped table-condensed table-centered mb10">
					<thead>
						<th>订货号</th>
						<th>产品名称</th>
						<th>品牌</th>
						<th>型号</th>
						<th>操作</th>
					</thead>
					<tbody>
						<c:forEach var="list" items="${goodsList}"
							varStatus="status">
							<tr>
								<td>${list.sku}</td>
								<td>${list.goodsName}</td>
								<td>${list.brandName}</td>
								<td>${list.model}</td>
								<td>
									<a href="javascript:void(0);"
										onclick="selectObj('${list.rOrdergoodsLaunchGoodsJCategoryId}','${list.goodsName}[${list.sku}]','${list.categoryNames}');">选择</a>
								</td>
							</tr>
						</c:forEach>
						<c:if test="${empty goodsList}">
							<!-- 查询无结果弹出 -->
							<tr>
								<td colspan='5'>查询无结果！请尝试使用其他搜索条件。</td>
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
  </div>
</div>
<%@ include file="../../common/footer.jsp"%>