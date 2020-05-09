<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="备货计划管理" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<div class="searchfunc">
	<form method="post"
		action="${pageContext.request.contextPath}/order/buyorder/bhmanage.do"
		id="search">
		<ul>
			<li><label class="infor_name">产品级别</label> <select
				class="input-middle" name="goodsLevel">
					<option value="0">全部</option>
					<c:forEach items="${levelList }" var="level">
						<option value="${level.sysOptionDefinitionId }"
							<c:if test="${level.sysOptionDefinitionId == goodsVo.goodsLevel}">selected="selected"</c:if>>${level.title }</option>
					</c:forEach>
			</select></li>
			<li><label class="infor_name">产品名称</label> <input type="text"
				class="input-middle" name="goodsName" id="goodsName"
				value="${goodsVo.goodsName }" /></li>
		</ul>
		<div class="tcenter">
			<span class="bt-small bg-light-blue bt-bg-style mr20 "
				onclick="search();" id="searchSpan">搜索</span> <span
				class="bt-small bg-light-blue bt-bg-style mr20" onclick="reset();">重置</span>
			<span class="bg-light-blue bt-bg-style bt-small pop-new-data" layerParams='{"width":"400px","height":"200px","title":"设置安全库存","link":"./uplodegoodssafesotck.do"}'>设置安全库存</span>
         	<span class="bg-light-blue bt-bg-style bt-small" onclick="exportList()">导出列表</span>
		</div>
	</form>
</div>
<div class="content">
	<div class="fixdiv">
		<div class="superdiv" style="width: 3000px;">
			<table
				class="table table-bordered table-striped table-condensed table-centered">
				<thead>
					<tr>
						<th style="width: 60px">选择</th>
						<th>产品名称</th>
						<th>品牌</th>
						<th>型号</th>
						<th>单位</th>
						<th>产品级别</th>
						<th>产品归属</th>
						<th>库存</th>
						<th>在途</th>
						<th>订单占用</th>
						<th>未来30天销量预测</th>
						<th>安全库存</th>
						<th>预计30天备货量</th>
						<th>预计资金占用</th>
						<th>采购价</th>
						<th>库存资金占用</th>
						<th>在途资金占用</th>
						<th>平均到货时间</th>
						<th  style="width: 300px">主渠道未来到货量（15天/30天/45天/60天/更长）</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty goodsList}">
						<c:forEach items="${goodsList }" var="goods">
							<tr>
								<td>
									<input type="checkbox" name="checkOne"
										value="${goods.goodsId }">
								</td>
								<td class="text-left">
									<div class="brand-color1"><a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewgoods${goods.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${goods.goodsId}","title":"产品信息"}'>${newSkuInfosMap[goods.sku].SHOW_NAME} </a></div>
									<div>${newSkuInfosMap[goods.sku].SKU_NO} </div>
									<div>${newSkuInfosMap[goods.sku].MATERIAL_CODE} </div>
								</td>
								<td>${newSkuInfosMap[goods.sku].BRAND_NAME}</td>
								<td>${newSkuInfosMap[goods.sku].MODEL}</td>
								<td>${newSkuInfosMap[goods.sku].UNIT_NAME}</td>
								<td>${newSkuInfosMap[goods.sku].GOODS_LEVEL_NAME}</td>
								<td>${newSkuInfosMap[goods.sku].GOODS_LEVEL_NAME}</td>
								<td>${goods.goodsStock}</td>
								<td>${goods.ztGoodsStock }</td>
								<td>${goods.orderOccupy }</td>
								<td>${goods.saleNum30 }</td>
								<td>${goods.safeNum }</td>
								<td alt="${goods.goodsId }">${goods.maybeSaleNum30 }</td>
								<td alt2="${goods.goodsId }">${goods.maybeSaleNum30 * goods.purchasePrice}</td>
								<td>${goods.purchasePrice}</td>
								<td>${goods.occupyStockAmount }</td>
								<td>${goods.occupyAmount }</td>
								<td>${goods.averageArrivalTime }</td>
								<td>-/-/-/-/-</td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
			
			<c:if test="${empty goodsList }">
				<!-- 查询无结果弹出 -->
				<div class="noresult">查询无结果！</div>
			</c:if>
		</div>
	</div>
	<div class='parts'>
		<div class="inputfloat f_left">
			<input type="checkbox" class="mt6 mr4" name="checkAll" autocomplete="off" style="margin-left: 4px;"> <label
				class="mr10 mt4">全选</label>
		</div>
		<tags:page page="${page}" />
		<%-- <div class='clear'></div>
		<iframe src="${pageContext.request.contextPath}/order/buyorder/bhmanagestat.do" name="statIframe" frameborder="0" style="width: 100%;border: 0; height: 50px "  scrolling="no">
    	</iframe> --%>
       <div class="table-buttons" style="margin-top:0px;">
          <button type="button" class="bt-bg-style bg-light-green bt-small ml10" onclick="addBHOrder(this);"
          tabTitle='{"num":"addbhorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>","link":"./order/buyorder/addbhorder.do","title":"备货信息"}'>生成备货单</button>
      </div>
	</div>
	
</div>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/order/buyorder/bh_manage.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../../common/footer.jsp"%>
