<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="交易记录" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<%@ include file="./customer_tag.jsp"%>
	<div class="searchfunc" style="padding-top:0px;">
		<form method="post" id="search" action="<%= basePath %>/trader/customer/businesslist.do">
			<ul>
				<li>
					<label class="infor_name">产品名称</label>
					<input type="text" class="input-middle" name="goodsName" id="goodsName" value="${saleorderGoodsVo.goodsName}"/>
				</li>
				<li>
					<label class="infor_name">品牌</label>
					<input type="text" class="input-middle" name="brandName" id="brandName" value="${saleorderGoodsVo.brandName}"/>
				</li>
				<li>
					<label class="infor_name">型号</label>
					<input type="text" class="input-middle" name="model" id="model" value="${saleorderGoodsVo.model}"/>
				</li>
				<li>
					<label class="infor_name">订货号</label>
					<input type="text" class="input-middle" name="sku" id="sku" value="${saleorderGoodsVo.sku}"/>
				</li>
				<li>
					<label class="infor_name">终端客户</label>
					<input type="text" class="input-middle" name="terminalTraderName" id="terminalTraderName" value="${saleorderGoodsVo.terminalTraderName}"/>
				</li>
				<li>
					<label class="infor_name">单号</label>
					<input type="text" class="input-middle" name="saleorderNo" id="saleorderNo" value="${saleorderGoodsVo.saleorderNo}"/>
				</li>
				<li>
					<label class="infor_name">时间</label>
					<input class="Wdate f_left input-smaller96 m0" type="text"
					placeholder="请选择日期"
					onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endtime\')}'})"
					name="starttime" id="starttime"
					value="${saleorderGoodsVo.starttime }">
					<div class="f_left ml1 mr1 mt4">-</div> <input
					class="Wdate f_left input-smaller96" type="text" placeholder="请选择日期"
					onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'starttime\')}'})"
					name="endtime" id="endtime" value="${saleorderGoodsVo.endtime }">
				</li>
			</ul>
			<div class="tcenter">
				<input type="hidden" name="traderId" value="${saleorderGoodsVo.traderId }" >
				<span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" id="searchSpan">搜索</span>
				<span class="bt-small bg-light-blue bt-bg-style mr20" onclick="reset();">重置</span>
			</div>
		</form>
	</div>
	<div class="content">
		<div class="fixdiv">
			<div class="superdiv">
				<table
					class="table table-bordered table-striped table-condensed table-centered">
					<thead>
						<tr>
							<th class="wid10">时间</th>
							<th class="wid10">单号</th>
							<th class="wid20">产品名称</th>
							<th class="wid8">订货号</th>
							<th class="wid8">品牌</th>
							<th class="wid8">型号</th>
							<th class="wid8">单价</th>
							<th class="wid8">数量</th>
							<th class="wid8">单位</th>
							<th class="wid12">总额</th>
							<th class="wid12">终端客户</th>
							<th class="wid12">销售区域</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="saleorderGoods" items="${businessList}" varStatus="num">
							<tr>
								<td><date:date value ="${saleorderGoods.validTime}"/></td>
								<td>
								<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewsaleorder${saleorderGoods.saleorderId}","link":"./order/saleorder/view.do?saleorderId=${saleorderGoods.saleorderId}","title":"订单信息"}'>${saleorderGoods.saleorderNo}</a>
								</td>
								<td class="text-left">${newSkuInfosMap[saleorderGoods.sku].SHOW_NAME}</td>
								<td>
								<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewgoods${saleorderGoods.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${saleorderGoods.goodsId}","title":"产品信息"}'>${saleorderGoods.sku}</a>
								</td>
								<td>${newSkuInfosMap[saleorderGoods.sku].BRAND_NAME}</td>
								<td>${newSkuInfosMap[saleorderGoods.sku].MODEL}</td>
								<td>${saleorderGoods.price}</td>
								<td>${saleorderGoods.num}</td>
								<td>${newSkuInfosMap[saleorderGoods.sku].UNIT_NAME}</td>
								<td><fmt:formatNumber type="number" value="${saleorderGoods.price * saleorderGoods.num}" pattern="0.00" maxFractionDigits="2" /></td>
								<td>${saleorderGoods.terminalTraderName}</td>
								<td>${saleorderGoods.salesArea}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<c:if test="${empty businessList}">
	      			<!-- 查询无结果弹出 -->
	          		<div class="noresult">查询无结果！请尝试使用其他搜索条件。</div>
		       	</c:if>
			</div>
		</div>
       	<tags:page page="${page}"/>
	</div>
</body>

</html>
