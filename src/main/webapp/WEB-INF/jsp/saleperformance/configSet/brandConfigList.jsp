<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="品牌列表" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/configset/brandConfig.js?rnd=<%=Math.random()%>"></script>
		<div class="main-container">
			<div class="parts">
    	<%-- <div class="searchfunc">		
			<form method="post" id="search" action="<%=basePath%>sales/salesperformance/getBrandConfigListPage.do">
				<ul>
            		<li>
						<label class="infor_name">品牌名称</label>
						<input type="text" class="input-middle" name="brandName" id="brandName" value="${brand.brandName}" >
            		</li>
            		<li>
						<label class="infor_name">状态</label>
						<select class="input-middle" name="isEnable" id="isEnable">
							<option value="">全部</option>
							<option value="0">已禁用</option>
							<option value="1">已启用</option>
						</select>
            		</li>
           		</ul>
           		<div class="tcenter">
					<span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" id="searchSpan">搜索</span>
					<span class="bt-small bg-light-blue bt-bg-style" onclick="reset();">重置</span>
					<span class="bg-light-blue bt-bg-style bt-small pop-new-data" 
           			layerparams='{"width":"822px","height":"500px","title":"新增品牌","link":"./getBrandListPage.do?groupId=${groupId}"}'>新增品牌</span>
				</div>
			</form>
		</div> --%>
		<div class="title-container">
            <div class="table-title nobor">
                 	五行剑法 - ${groupName} - 有效品牌库
            </div>
            <div class="title-click nobor  pop-new-data" layerparams='{"width":"822px","height":"500px","title":"新增品牌","link":"./getBrandListPage.do?groupId=${groupId}"}'>
                                  	新增
            </div>
        </div>
		<div class='normal-list-page list-page'>
			<table
				class="table table-bordered table-striped table-condensed table-centered">
				<thead>
					<tr class="sort" style="background-color: white;font-weight: bold;">
						<th style="font-weight: bold;">序号</th>
						<th style="font-weight: bold;">品牌ID</th>
						<th style="font-weight: bold;">品牌名称</th>
						<th style="font-weight: bold;">商品数量</th>
						<th style="font-weight: bold;">加入时间</th>
						<th style="font-weight: bold;">状态</th>
						<th class="wid10" style="font-weight: bold;">操作</th>
					</tr>
				</thead>
				<tbody class="brand">
					<c:if test="${not empty brandConfigList}">
						<c:forEach items="${brandConfigList }" var="brand" varStatus="num" >
							<tr>
								<td>${num.count}</td>
								<td>${brand.brandId}</td>
								<td>${brand.brandName}</td>
								<td>${brand.goodsNum}</td>
								<td><date:date value="${brand.addTime}" /></td>
								<td>
									<c:choose>
										<c:when test="${brand.isEnable==0}">
											<span style="color: red;">已禁用</span>
										</c:when>
										<c:otherwise>
											<span>已启用</span>
										</c:otherwise>
									</c:choose>
								</td>
								<td>
									<c:choose>
										<c:when test="${brand.isEnable==0}">
											<span class="edit-user" onclick="openOrcloseBrand(${brand.salesPerformanceBrandConfigId},1)">启用</span>
										</c:when>
										<c:otherwise>
											<span class="forbid clcforbid" onclick="openOrcloseBrand(${brand.salesPerformanceBrandConfigId},0)">禁用</span>
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
				<c:if test="${empty brandConfigList}">
					<!-- 查询无结果弹出 -->
					<tr>
						<td colspan="7">查询无结果！请尝试使用其他搜索条件。</td>
					</tr>
				</c:if>
			</table>
			<c:if test="${not empty brandConfigList}">
				<tags:page page="${page}"  />
			</c:if>
		</div>
	</div>
	</div>
</body>
</html>