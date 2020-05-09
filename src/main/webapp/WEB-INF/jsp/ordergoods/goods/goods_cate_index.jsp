<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="${ordergoodsStore.name }设备与试剂分类" scope="application" />
<%@ include file="../../common/common.jsp"%>
	<script type="text/javascript" src='<%= basePath %>static/js/ordergoods/goods/goods_cate_index.js?rnd=<%=Math.random()%>'></script>
    <div class="main-container">
    	<div class="list-pages-search">
            <form action="" method="post" id="search">
                <ul>
                    <li>
                        <label class="infor_name">订货号</label>
                        <input type="text" class="input-middle" name="sku" value="${rOrdergoodsLaunchGoodsJCategoryVo.sku }"/>
                    </li>
                    <li>
                        <label class="infor_name">产品名称</label>
                        <input type="text" class="input-middle" name="goodsName" value="${rOrdergoodsLaunchGoodsJCategoryVo.goodsName }"/>
                    </li>
                    <li>
                        <label class="infor_name">品牌</label>
                        <input type="text" class="input-middle" name="brandName" value="${rOrdergoodsLaunchGoodsJCategoryVo.brandName }"/>
                    </li>
                    <li>
                        <label class="infor_name">型号</label>
                        <input type="text" class="input-middle" name="model" value="${rOrdergoodsLaunchGoodsJCategoryVo.model }"/>
                    </li>
                    <li>
                        <label class="infor_name">试剂分类</label>
                        <select name="categoryId">
                        	<option value="0">请选择</option>
                        	<c:if test="${not empty ordergoodsCategry }">
                        		<c:forEach items="${ordergoodsCategry }" var="category">
                        			<option value="${category.ordergoodsCategoryId }" 
                        			<c:if test="${rOrdergoodsLaunchGoodsJCategoryVo.categoryId eq category.ordergoodsCategoryId }">
                        			selected="selected"
                        			</c:if>
                        			>${category.categoryName }</option>
                        		</c:forEach>
                        	</c:if>
                        </select>
                    </li>
                </ul>
                <div class="tcenter">
                    <span class="bt-middle bg-light-blue bt-bg-style mr20" onclick="search();">搜索</span>
                    <span class="bt-middle bg-light-blue bt-bg-style mr20" onclick="reset();">重置</span>
                    <span class="bg-light-blue bt-bg-style bt-small pop-new-data" layerParams='{"width":"900px","height":"500px","title":"设备与试剂分类","link":"./addequipmentcategory.do?ordergoodsStoreId=${ordergoodsStore.ordergoodsStoreId }"}'>添加设备与试剂分类</span>
                </div>
            </form>
        </div>
        <div class="list-page normal-list-page">
        	<table class="table">
                <thead>
                    <tr>
                        <th class="wid4">序号</th>
                        <th>订货号</th>
                        <th>产品名称</th>
                        <th>品牌</th>
                        <th>型号</th>
                        <th>对应试剂分类</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
        		<c:if test="${not empty ordergoodsGoodsList}">
                	<c:forEach items="${ordergoodsGoodsList }" var="list" varStatus="status">
	                    <tr>
	                        <td>${status.count}</td>
	                        <td>${list.sku}</td>
							<td class="text-left">${list.goodsName}</td>
	                        <td>${list.brandName}</td>
	                        <td>${list.model}</td>
							<td>${list.categoryNames }</td>
	                        <td>
	                        	<span class="edit-user pop-new-data"
									layerParams='{"width":"900px","height":"500px","title":"编辑设备与试剂分类","link":"./editequipmentcategory.do?rOrdergoodsLaunchGoodsJCategoryId=${list.rOrdergoodsLaunchGoodsJCategoryId}"}'>编辑</span>
	                        </td>
	                    </tr>
                	</c:forEach>
        		</c:if>
                </tbody>
            </table>
        	<c:if test="${empty ordergoodsGoodsList}">
       			<!-- 查询无结果弹出 -->
           		<div class="noresult">查询无结果！</div>
        	</c:if>
		    <div class="parts">
		    	<tags:page page="${page}" />
		    </div>
        </div>
    </div>
<%@ include file="../../common/footer.jsp"%>

