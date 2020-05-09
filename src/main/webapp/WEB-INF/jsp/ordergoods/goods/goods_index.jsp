<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="${ordergoodsStore.name }产品列表" scope="application" />
<%@ include file="../../common/common.jsp"%>
	<script type="text/javascript" src='<%= basePath %>static/js/ordergoods/goods/goods_index.js?rnd=<%=Math.random()%>'></script>
    <div class="main-container">
    	<div class="list-pages-search">
            <form action="${pageContext.request.contextPath}/ordergoods/manage/goodslist.do" method="post" id="search">
                <ul>
                    <li>
                        <label class="infor_name">订货号</label>
                        <input type="text" class="input-middle" name="sku" value="${ordergoodsGoodsVo.sku }"/>
                    </li>
                    <li>
                        <label class="infor_name">产品名称</label>
                        <input type="text" class="input-middle" name="goodsName" value="${ordergoodsGoodsVo.goodsName }"/>
                    </li>
                    <li>
                        <label class="infor_name">品牌</label>
                        <input type="text" class="input-middle" name="brandName" value="${ordergoodsGoodsVo.brandName }"/>
                    </li>
                    <li>
                        <label class="infor_name">型号</label>
                        <input type="text" class="input-middle" name="model" value="${ordergoodsGoodsVo.model }"/>
                    </li>
                    <li>
                        <label class="infor_name">物料编码</label>
                        <input type="text" class="input-middle" name="materialCode" value="${ordergoodsGoodsVo.materialCode }"/>
                    </li>
                </ul>
                <div class="tcenter">
                    <span class="bt-middle bg-light-blue bt-bg-style mr20" onclick="search();">搜索</span>
                    <span class="bt-middle bg-light-blue bt-bg-style mr20" onclick="reset();">重置</span>
                    <input type="hidden" name="ordergoodsStoreId" value="${ordergoodsStore.ordergoodsStoreId }">
                    <span class="bg-light-blue bt-bg-style bt-small pop-new-data" layerParams='{"width":"400px","height":"200px","title":"批量上传产品","link":"./uplodebatchgoods.do?ordergoodsStoreId=${ordergoodsStore.ordergoodsStoreId }"}'>上传产品</span>
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
                        <th>物料编码</th>
                        <th>上架</th>
                        <th>废弃</th>
                        <th>规格</th>
                        <th>计价单位</th>
                        <th>成本价</th>
                        <th>零售价</th>
                        <th>备注</th>
                        <th>运输条件</th>
                        <th>排序</th>
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
	                        <td>${list.materialCode}</td>
							<td>
								<c:choose>
									<c:when test="${list.isOnSale eq 1 }">是</c:when>
									<c:when test="${list.isOnSale eq 0 }">否</c:when>
									<c:otherwise>-</c:otherwise>
								</c:choose>
							</td>
							<td>
								<c:choose>
									<c:when test="${list.isDiscard eq 1 }">否</c:when>
									<c:when test="${list.isDiscard eq 0 }">是</c:when>
									<c:otherwise>-</c:otherwise>
								</c:choose>
							</td>
							<td>${list.spec }</td>
							<td>${list.unit }</td>
							<td>${list.costPrice }</td>
							<td>${list.retailPrice }</td>
							<td>${list.comments }</td>
							<td>
								<c:choose>
									<c:when test="${list.transportRequirements eq 1 }">常温</c:when>
									<c:when test="${list.transportRequirements eq 2 }">冷藏</c:when>
									<c:when test="${list.transportRequirements eq 3 }">血球冷藏</c:when>
									<c:otherwise>-</c:otherwise>
								</c:choose>
							</td>
							<td>
							<c:if test="${not empty list.sort }">
							<input type="text" value="${list.sort }" autocomplete="off" class="input-smallest" onchange="changeSort(this,${list.rOrdergoodsGoodsJCategoryId });">
							</c:if>
							</td>
	                        <td>
	                        	<span class="delete" onclick="delGoods(${list.ordergoodsGoodsId},${list.goodsId},${ordergoodsStore.ordergoodsStoreId });">删除</span>
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

