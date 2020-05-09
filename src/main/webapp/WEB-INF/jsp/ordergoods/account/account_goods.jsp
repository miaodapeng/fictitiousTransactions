<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="${ordergoodsStore.name }产品列表" scope="application" />
<%@ include file="../../common/common.jsp"%>
	<script type="text/javascript" src='<%= basePath %>static/js/ordergoods/account/account_goods.js?rnd=<%=Math.random()%>'></script>
    <div class="main-container">
    	<div class="list-pages-search">
            <form action="" method="post" id="search">
                <ul>
                    <li>
                        <label class="infor_name">订货号</label>
                        <input type="text" class="input-middle" name="sku" value="${ordergoodsGoodsAccountVo.sku }"/>
                    </li>
                    <li>
                        <label class="infor_name">产品名称</label>
                        <input type="text" class="input-middle" name="goodsName" value="${ordergoodsGoodsAccountVo.goodsName }"/>
                    </li>
                    <li>
                        <label class="infor_name">品牌</label>
                        <input type="text" class="input-middle" name="brandName" value="${ordergoodsGoodsAccountVo.brandName }"/>
                    </li>
                    <li>
                        <label class="infor_name">型号</label>
                        <input type="text" class="input-middle" name="model" value="${ordergoodsGoodsAccountVo.model }"/>
                    </li>
                </ul>
                <div class="tcenter">
                	<input type="hidden" name="webAccountId" value="${ordergoodsGoodsAccountVo.webAccountId}">
                	<input type="hidden" name="ordergoodsStoreId" value="${ordergoodsStore.ordergoodsStoreId}">
                    <span class="bt-middle bg-light-blue bt-bg-style mr20" onclick="search();">搜索</span>
                    <span class="bt-middle bg-light-blue bt-bg-style mr20" onclick="reset();">重置</span>
                    <span class="bg-light-blue bt-bg-style bt-small pop-new-data" layerParams='{"width":"400px","height":"200px","title":"批量上传产品价格","link":"./uplodebatchaccountgoods.do?ordergoodsStoreId=${ordergoodsStore.ordergoodsStoreId }&webAccountId=${ordergoodsGoodsAccountVo.webAccountId}"}'>上传产品价格</span>
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
                        <th>价格</th>
                        <th>起订量1</th>
                        <th>批量价1</th>
                        <th>起订量2</th>
                        <th>批量价2</th>
                        <th>起订量3</th>
                        <th>批量价3</th>
                        <th>起订量4</th>
                        <th>批量价4</th>
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
							<td>${list.price }</td>
							<td>${list.minQuantity1 }</td>
							<td>${list.price1 }</td>
							<td>${list.minQuantity2 }</td>
							<td>${list.price2 }</td>
							<td>${list.minQuantity3 }</td>
							<td>${list.price3 }</td>
							<td>${list.minQuantity4 }</td>
							<td>${list.price4 }</td>
	                        <td>
	                        	<span class="delete" onclick="delAccountGoods(${list.ordergoodsGoodsAccountId},${list.goodsId},${list.webAccountId},${list.ordergoodsStoreId});">删除</span>
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

