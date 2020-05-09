<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="商品归还入库列表" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="customer">
	<ul>
		<li><a href="/logistics/warehousein/index.do">采购入库</a></li>
		<li><a href="/aftersales/order/getChangeAftersales.do">采购售后入库</a>
		</li>
		<li><a href="/aftersales/order/getStorageAftersales.do">销售售后入库</a>
		</li>
		<li><a href="/logistics/warehousein/lendOutIndex.do"
			class="customer-color">商品归还入库</a></li>
	</ul>
</div>
<div
	class="content logistics-warehousein-index>
<div class="searchfunc">
<form method="post" id="search" action="<%=basePath%>logistics/warehousein/lendOutIndex.do">
	<ul style='display: flex;justify-content: space-around;'>
		<li>
			<label class="infor_name">关键词搜索</label>
			<input type="text" class="input-middle" placeholder="输入外借单号、借用企业、产品名称、关联售后单查找" name="searchStr"  value="${lendout.searchStr}"/>
		</li>
		<li>
			<label class="infor_name">借用原因</label>
			<select class="input-middle" name="lendOutType" id="type">
						<option value="">全部</option>
						<option <c:if test="${lendout.lendOutType eq 1}">selected</c:if> value="1">样品外借</option>
	<option <c:if test="${lendout.lendOutType eq 2}">selected</c:if> value="2">售后垫货</option>
	</select>
	</li>
	<li><label class="infor_name">逾期归还</label> <select
		class="input-middle" name="overdue" id="returntime">
			<option value="">全部</option>
			<%-- <option <c:if test="${lendout.overdue eq 0}">selected</c:if> value="0">全部</option> --%>
			<option <c:if test="${lendout.overdue eq 1}">selected</c:if>
				value="1">是</option>
			<option <c:if test="${lendout.overdue eq 2}">selected</c:if>
				value="2">否</option>
	</select></li>
	</ul>
	<div class="tcenter" style='margin-top: 20px;margin-bottom: 10px;'>
		<span class="confSearch bt-small bt-bg-style bg-light-blue" 
			onclick="search();" id="searchSpan">搜索</span> 
		<span class="bt-small bg-light-blue bt-bg-style mr20" onclick="reset();">重置</span>
	</div>
	</form>
</div>
<div class="table-style5">
	<table class="table">
		<thead>
			<tr>
				<th class="wid3">序号</th>
				<th class="wid9">外借单号</th>
				<th class="wid8">借用原因</th>
				<th class="wid15">借用企业</th>
				<th class="wid10">产品名称</th>
				<th class="wid10">借用数量</th>
				<th class="wid10">出库数量</th>
				<th class="wid8">归还数量</th>
				<th class="wid8">创建人</th>
				<th class="wid8">创建时间</th>
				<th class="wid8">预计归还时间</th>
				<th class="wid8">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="list" items="${lendoutlist}" varStatus="num">
			<tr>
				<td>${num.count}</td>
				<td><a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"lendOutdetailJump${list.lendOutId}","link":"./warehouse/warehousesout/lendOutdetailJump.do?lendOutId=${list.lendOutId}","title":"外借详情页"}'>${list.lendOutNo}</a></td>
				<td>
				<c:if test="${list.lendOutType eq 1}">样品外借</c:if>
				<c:if test="${list.lendOutType eq 2}">售后垫货</c:if>
				</td>
				<td>
				<c:if test="${list.traderType eq 1 }">
				<a class="addtitle" href="javascript:void(0);" tabtitle='{"num":"viewcustomer${list.traderId}", "link":"./trader/customer/baseinfo.do?traderId=${list.traderId}", "title":"客户信息"}'>${list.traderName}</a>
				</c:if> <c:if test="${list.traderType eq 2 }">
				<a class="addtitle" href="javascript:void(0);" tabtitle='{"num":"viewsupplier${list.traderId}", "link":"./trader/supplier/baseinfo.do?traderId=${list.traderId}", "title":"供应商信息"}'>${list.traderName}</a>
				</c:if>
				</td>
				<td> <a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewgoods${list.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${list.goodsId}","title":"产品信息"}'>${spuMap[list.goodsId]}</a></td>
				<td>${list.lendOutNum}</td>
				<td>${list.deliverNum}</td>
				<td>${list.returnNum}</td>
				<td>${list.creatorName}</td>
				<td><date:date value ="${list.addTime}"/></td>
				<td><date:date value ="${list.returnTimeStr}"/></td>
				<td>
				<div>
				<c:if test="${list.barcodeNum == 0 }">
				<a class="addtitle" tabTitle='{"num":"addBarcode${list.lendOutId}_${list.goodsId}",
				"link":"./logistics/warehousein/addBarcode.do?goodsId=${list.goodsId}&afterSalesId=${list.lendOutId}&afterSalesGoodsId=${list.lendOutId}&type=4&businessType=9","title":"生成条形码"}'>生成条形码</a>
				</c:if>
				<c:if test="${list.barcodeNum > 0 }">
                <a  class=" addtitle" 
                tabTitle='{"num":"addWarehouseIn${list.lendOutId}_${list.goodsId}","link":"./logistics/warehousein/addWarehouseIn.do?afterSalesId=${list.lendOutId}&goodsId=${list.goodsId}&afterSalesGoodsId=${list.lendOutId}&type=4","title":"入库"}'>归还入库</button>
               <%--  <a  class=" addtitle" tabTitle='{"num":"addBarcode${list.lendOutId}_${list.goodsId}","link":"./logistics/warehousein/getWarehouseInBarcode.do?buyorderId=${list.lendOutId}&type=4","title":"查看全部入库条码"}'>查看入库条码</button> --%>
				<a class="addtitle" tabTitle='{"num":"addBarcode${list.lendOutId}_${list.goodsId}",
				"link":"./logistics/warehousein/addBarcode.do?goodsId=${list.goodsId}&afterSalesId=${list.lendOutId}&afterSalesGoodsId=${list.lendOutId}&type=4&businessType=9","title":"生成条形码"}'>查看入库条码</a>
				
				</c:if>
              <%--  <br/>
                  <div><a class="addtitle" tabTitle='{"num":"addWarehouseIn${buyorderInfo.buyorderId}_${bgv.buyorderGoodsId}",
                 "link":"./logistics/warehousein/addWarehouseIn.do?buyorderId=${buyorderInfo.buyorderId}&goodsId=${list.goodsId}&buyorderGoodsId=${bgv.buyorderGoodsId}&type=1","title":"入库"}'>入库</a>
                  --%></div>
				</td>
			</tr>
			</c:forEach>
			<c:if test="${empty lendoutlist}">
				<tr>
					<td colspan="12">查询无结果！请尝试使用其他搜索条件。</td>
				</tr>
			</c:if>
		</tbody>
	</table>

	<div class="mt-5">
		<tags:page page="${page}" />
	</div>
</div>
</div>
<script type="text/javascript"
	src='<%=basePath%>static/js/logistics/warehouseIn/lendout_index.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>