<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="${ordergoodsStore.name }产品分类管理" scope="application" />
<%@ include file="../../common/common.jsp"%>
	<script type="text/javascript" src='<%= basePath %>static/js/ordergoods/category/category_index.js?rnd=<%=Math.random()%>'></script>
    <div class="content">
    	<div class="searchfunc searchfuncpl0">
			<span class="bt-small bg-light-blue bt-bg-style pop-new-data"
				layerParams='{"width":"550px","height":"300px","title":"新增分类","link":"./addcategory.do?ordergoodsStoreId=${ordergoodsStore.ordergoodsStoreId}"}'>新增分类</span>
		</div>
        <div class="list-page normal-list-page">
        	<table class="table">
                <thead>
                    <tr>
                        <th>分类名称</th>
                        <th>包邮最小起订金额</th>
                        <th>是否启用</th>
                        <th>排序</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
        		<c:if test="${not empty ordergoodsCategory}">
                	<c:forEach items="${ordergoodsCategory }" var="list" varStatus="status">
	                    <tr>
	                        <td class="text-left">${list.categoryName}</td>
							<td>${list.minAmount}</td>
							<td>
								<c:choose>
									<c:when test="${list.status eq 1 }">启用</c:when>
									<c:otherwise>禁用</c:otherwise>
								</c:choose>
							</td>
							<td>${list.sort }</td>
	                        <td>
	                        	<span class="edit-user pop-new-data" layerParams='{"width":"550px","height":"300px","title":"编辑分类","link":"./editcategory.do?ordergoodsCategoryId=${list.ordergoodsCategoryId}&ordergoodsStoreId=${ordergoodsStore.ordergoodsStoreId }"}'>
									编辑
								</span>
								<c:if test="${list.parentId ne 0 }">
								<span class="edit-user addtitle"
									tabTitle='{"num":"getcategorygoods${ordergoodsStore.ordergoodsStoreId}${list.ordergoodsCategoryId}","link":"./ordergoods/manage/getcategorygoods.do?ordergoodsCategoryId=${list.ordergoodsCategoryId}&ordergoodsStoreId=${ordergoodsStore.ordergoodsStoreId} ","title":"查看产品"}'>查看产品</span>
								</c:if>
										
	                        </td>
	                    </tr>
                	</c:forEach>
        		</c:if>
                </tbody>
            </table>
        	<c:if test="${empty ordergoodsCategory}">
       			<!-- 查询无结果弹出 -->
           		<div class="noresult">查询无结果！</div>
        	</c:if>
        </div>
    </div>
<%@ include file="../../common/footer.jsp"%>

