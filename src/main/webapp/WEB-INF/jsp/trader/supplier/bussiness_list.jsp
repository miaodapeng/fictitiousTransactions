<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="交易记录" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<%@ include file="./supplier_tag.jsp"%>
	<div class="searchfunc" style="padding-top:0px;">
		<form method="post" id="search" action="<%= basePath %>/trader/supplier/businesslist.do">
			<ul>
				<li>
					<label class="infor_name">产品名称</label>
					<input type="text" class="input-middle" name="goodsName" id="goodsName" value="${buyorderGoodsVo.goodsName}"/>
				</li>
				<li>
					<label class="infor_name">品牌</label>
					<input type="text" class="input-middle" name="brandName" id="brandName" value="${buyorderGoodsVo.brandName}"/>
				</li>
				<li>
					<label class="infor_name">型号</label>
					<input type="text" class="input-middle" name="model" id="model" value="${buyorderGoodsVo.model}"/>
				</li>
				<li>
					<label class="infor_name">订货号</label>
					<input type="text" class="input-middle" name="sku" id="sku" value="${buyorderGoodsVo.sku}"/>
				</li>
				<li>
					<label class="infor_name">单号</label>
					<input type="text" class="input-middle" name="buyorderNo" id="buyorderNo" value="${buyorderGoodsVo.buyorderNo}"/>
				</li>
				<li>
					<label class="infor_name">时间</label>
					<input class="Wdate f_left input-smaller96 m0" type="text"
					placeholder="请选择日期"
					onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endtime\')}'})"
					name="starttime" id="starttime"
					value="${buyorderGoodsVo.starttime }">
					<div class="f_left ml1 mr1 mt4">-</div> <input
					class="Wdate f_left input-smaller96" type="text" placeholder="请选择日期"
					onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'starttime\')}'})"
					name="endtime" id="endtime" value="${buyorderGoodsVo.endtime }">
				</li>
			</ul>
			<div class="tcenter">
				<input type="hidden" name="traderId" value="${buyorderGoodsVo.traderId }" >
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
							<th class="wid14">时间</th>
							<th class="wid12">单号</th>
							<th class="wid20">产品名称</th>
							<th class="wid8">订货号</th>
							<th class="wid8">品牌</th>
							<th class="wid8">型号</th>
							<th class="wid8">采购价</th>
							<th class="wid8">数量</th>
							<th class="wid8">单位</th>
							<th class="wid12">总额</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="buyorderGoods" items="${businessList}" varStatus="num">
							<tr>
								<td><date:date value ="${buyorderGoods.validTime}"/></td>
								<td>
								<a class="addtitle" href="javascript:void(0);" 
									tabTitle='{"num":"viewbuyorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
									"link":"./order/buyorder/viewBuyordersh.do?buyorderId=${buyorderGoods.buyorderId}","title":"订单信息"}'>${buyorderGoods.buyorderNo}</a></td>
								<td class="text-left">${newSkuInfosMap[buyorderGoods.sku].SHOW_NAME}</td>
								<td>
									<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewgoods${buyorderGoods.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${buyorderGoods.goodsId}","title":"产品信息"}'>${buyorderGoods.sku}</a>
								</td>
								<td>${newSkuInfosMap[buyorderGoods.sku].BRAND_NAME}</td>
								<td>${newSkuInfosMap[buyorderGoods.sku].MODEL}</td>
								<td>${buyorderGoods.price}</td>
								<td>${buyorderGoods.num}</td>
								<td>${newSkuInfosMap[buyorderGoods.sku].UNIT_NAME}</td>
								<td><fmt:formatNumber type="number" value="${buyorderGoods.price * buyorderGoods.num}" pattern="0.00" maxFractionDigits="2" /></td>
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
