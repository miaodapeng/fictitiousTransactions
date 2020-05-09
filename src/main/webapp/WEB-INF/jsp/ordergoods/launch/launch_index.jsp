<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="${ordergoodsStore.name }投放列表" scope="application" />
<%@ include file="../../common/common.jsp"%>
	<script type="text/javascript" src='<%= basePath %>static/js/ordergoods/goods/goods_cate_index.js?rnd=<%=Math.random()%>'></script>
    <div class="main-container">
    	<div class="list-pages-search">
            <form action="" method="post" id="search">
                <ul>
                    <li>
                        <label class="infor_name">客户名称</label>
                        <input type="text" class="input-middle" name="traderName" value="${ordergoodsLaunchVo.traderName }"/>
                    </li>
                    <li>
                        <label class="infor_name">订货号</label>
                        <input type="text" class="input-middle" name="sku" value="${ordergoodsLaunchVo.sku }"/>
                    </li>
                    <li>
                        <label class="infor_name">产品名称</label>
                        <input type="text" class="input-middle" name="goodsName" value="${ordergoodsLaunchVo.goodsName }"/>
                    </li>
                    <li>
                        <label class="infor_name">品牌</label>
                        <input type="text" class="input-middle" name="brandName" value="${ordergoodsLaunchVo.brandName }"/>
                    </li>
                    <li>
                        <label class="infor_name">型号</label>
                        <input type="text" class="input-middle" name="model" value="${ordergoodsLaunchVo.model }"/>
                    </li>
                </ul>
                <div class="tcenter">
                    <span class="bt-middle bg-light-blue bt-bg-style mr20" onclick="search();">搜索</span>
                    <span class="bt-middle bg-light-blue bt-bg-style mr20" onclick="reset();">重置</span>
                    <span class="bg-light-blue bt-bg-style bt-small addtitle"
						tabTitle='{"num":"addordergoodslaunch${ordergoodsStore.ordergoodsStoreId }","link":"./ordergoods/manage/addordergoodslaunch.do?ordergoodsStoreId=${ordergoodsStore.ordergoodsStoreId }","title":"添加投放"}'>添加投放</span>
                </div>
            </form>
        </div>
        <div class="list-page normal-list-page">
        	<table class="table">
                <thead>
                    <tr>
                        <th class="wid4">序号</th>
                        <th>客户名称</th>
                        <th>投放设备名称</th>
                        <th>订货号</th>
                        <th>品牌</th>
                        <th>型号</th>
                        <th>投放开始时间</th>
                        <th>投放结束时间</th>
                        <th>目标总额(万)</th>
                        <th>当前完成度</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
        		<c:if test="${not empty ordergoodsLaunchList}">
                	<c:forEach items="${ordergoodsLaunchList }" var="list" varStatus="status">
	                    <tr>
	                        <td>${status.count}</td>
	                        <td>${list.traderName}</td>
							<td class="text-left">${list.goodsName}</td>
	                        <td>${list.sku}</td>
	                        <td>${list.brandName}</td>
	                        <td>${list.model}</td>
							<td><date:date value="${list.startTime} " format="yyyy-MM-dd"/></td>
							<td><date:date value="${list.endTime} " format="yyyy-MM-dd"/></td>
							<td>${list.goalAmount}</td>
							<td>${list.haveAmountPre} %</td>
	                        <td>
	                        	<span class="edit-user addtitle"
									tabTitle='{"num":"viewordergoodslaunch${ordergoodsStore.ordergoodsStoreId}${list.ordergoodsLaunchId}","link":"./ordergoods/manage/viewordergoodslaunch.do?ordergoodsLaunchId=${list.ordergoodsLaunchId}","title":"查看"}'>查看</span>
	                        	<span class="edit-user addtitle"
									tabTitle='{"num":"editordergoodslaunch${ordergoodsStore.ordergoodsStoreId}${list.ordergoodsLaunchId}","link":"./ordergoods/manage/editordergoodslaunch.do?ordergoodsLaunchId=${list.ordergoodsLaunchId}","title":"编辑"}'>编辑</span>
	                        </td>
	                    </tr>
                	</c:forEach>
        		</c:if>
                </tbody>
            </table>
        	<c:if test="${empty ordergoodsLaunchList}">
       			<!-- 查询无结果弹出 -->
           		<div class="noresult">查询无结果！</div>
        	</c:if>
		    <div class="parts">
		    	<tags:page page="${page}" />
		    </div>
        </div>
    </div>
<%@ include file="../../common/footer.jsp"%>

