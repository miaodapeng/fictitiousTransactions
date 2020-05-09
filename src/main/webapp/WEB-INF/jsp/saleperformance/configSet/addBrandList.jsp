<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增品牌" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/configset/brandConfig.js?rnd=<%=Math.random()%>"></script>
		<div class="content">
    	<div class="searchfunc">		
			<ul class="searchTable">
           		<li>
           			<form method="post" id="search" action="<%=basePath%>sales/salesperformance/getBrandListPage.do?groupId=${groupId}">
           				<input type="hidden" id="groupIds" name="groupIds" value="${groupId}">
           				<div class="infor_name ">
							<span>*</span> <label>品牌名称</label>
						</div>
            			<div class="f_left">
								<div class="">
									<input type="text" class="input-larger" placeholder="请输入品牌名称" id="brandName" name="brandName" value="${searchContent}">
									<span class="bt-bg-style bt-small bg-light-blue" onclick="search();">搜索</span>
								</div>
						</div>
					</form>
           		</li>
          	</ul>
		</div>
		<div class='normal-list-page list-page'>
			<table
				class="table table-bordered table-striped table-condensed table-centered">
				<thead>
					<tr class="sort" style="font-weight: bold;">
						<th class="wid10" style="font-weight: bold;">选择</th>
						<th class="wid30" style="font-weight: bold;">品牌ID</th>
						<th class="wid40" style="font-weight: bold;">品牌名称</th>
					</tr>
				</thead>
				<tbody class="brand">
					<c:if test="${not empty brandList}">
						<c:forEach items="${brandList }" var="brand" varStatus="status">
								<tr>
									<td>
										<input name="checkOne" value="${brand.brandId}" type="checkbox">
									</td>
									<td>${brand.brandId}</td>
									<td>${brand.brandName}</td>
									<td><date:date value="${brand.addTime}" /></td>
								</tr>
						</c:forEach>
					</c:if>
					<c:if test="${empty brandList}">
						<!-- 查询无结果弹出 -->
						<tr>
							<td colspan='3'>查询无结果！请尝试使用其他搜索条件。</td>
						</tr>
					</c:if>
				</tbody>
			</table>
			<c:if test="${not empty brandList}">
				<div>
					<div class="inputfloat f_left">
						<input type="checkbox" class="mt6 mr4" name="checkAll"
							autocomplete="off"> <label class="mr10 mt4">全选</label>
					</div>
					<tags:page page="${page}" optpage="n" />
				</div>
			</c:if>
		</div>
		<c:if test="${not empty brandList}">
			<div class="add-tijiao tcenter">
		 		<button type="button" class="bt-bg-style bg-light-green" onclick="checkBrands();"> 确认新增</button>
		 		<button class="dele" id="close-layer">取消</button>
			</div>
		</c:if>
	</div>
</body>
</html>