<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="录保卡列表" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<div class="searchfunc">
	<form method="post"
		action="${pageContext.request.contextPath}/aftersales/goodswarranty/list.do"
		id="search">
		<ul>
			<li><label class="infor_name">销售单号</label> <input type="text"
				class="input-middle" name="saleorderNo" id="saleorderNo"
				value="${saleorderGoodsWarrantyVo.saleorderNo }" /></li>
			<li><label class="infor_name">服务单号</label> <input type="text"
				class="input-middle" name="serviceNo" id="serviceNo"
				value="${saleorderGoodsWarrantyVo.serviceNo }" /></li>
			<li><label class="infor_name">经销商名称</label> <input type="text"
				class="input-middle" name="distributorName" id="distributorName"
				value="${saleorderGoodsWarrantyVo.distributorName }" /></li>
			<li><label class="infor_name">终端名称</label> <input type="text"
				class="input-middle" name="terminalName" id="terminalName"
				value="${saleorderGoodsWarrantyVo.terminalName }" /></li>
			<li><label class="infor_name">产品名称</label> <input type="text"
				class="input-middle" name="goodsName" id="goodsName"
				value="${saleorderGoodsWarrantyVo.goodsName }" /></li>
			<li><label class="infor_name">安装验收时间</label> <input class="Wdate f_left input-smaller96 m0" type="text"
				placeholder="请选择日期"
				onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'checkTimeEndStr\')}'})"
				name="checkTimeStartStr" id="checkTimeStartStr"
				value="${saleorderGoodsWarrantyVo.checkTimeStartStr }">
				<div class="f_left ml1 mr1 mt4">-</div> <input
				class="Wdate f_left input-smaller96" type="text" placeholder="请选择日期"
				onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'checkTimeStartStr\')}'})"
				name="checkTimeEndStr" id="checkTimeEndStr" value="${saleorderGoodsWarrantyVo.checkTimeEndStr }">
			</li>
			<li><label class="infor_name">录保卡时间</label> <input class="Wdate f_left input-smaller96 m0" type="text"
				placeholder="请选择日期"
				onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'addTimeEndStr\')}'})"
				name="addTimeStartStr" id="addTimeStartStr"
				value="${saleorderGoodsWarrantyVo.addTimeStartStr }">
				<div class="f_left ml1 mr1 mt4">-</div> <input
				class="Wdate f_left input-smaller96" type="text" placeholder="请选择日期"
				onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'addTimeStartStr\')}'})"
				name="addTimeEndStr" id="addTimeEndStr" value="${saleorderGoodsWarrantyVo.addTimeEndStr }">
			</li>
		</ul>
		<div class="tcenter">
			<span class="bt-small bg-light-blue bt-bg-style mr20 "
				onclick="search();" id="searchSpan">搜索</span> <span
				class="bt-small bg-light-blue bt-bg-style mr20" onclick="reset();">重置</span>
			<span class="bg-light-blue bt-bg-style bt-small" onclick="exportList()">导出列表</span>				
		</div>
	</form>
</div>
<div class="content">
	<div class="list-page normal-list-page">
			<table
				class="table table-bordered table-striped table-condensed table-centered">
				<thead>
					<tr>
						<th class="wid4">序号</th>
						<th>销售单号</th>
						<th>服务单号</th>
						<th>经销商名称</th>
						<th>终端名称</th>
						<th>产品名称</th>
						<th>厂商条码</th>
						<th>录保卡时间</th>
						<th class="wid10">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty list}">
						<c:forEach items="${list }" var="goodsWarranty" varStatus="status">
							<tr>
								<td>${status.count }</td>
								<td><a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewsaleorder${goodsWarranty.saleorderId}","link":"./order/saleorder/view.do?saleorderId=${goodsWarranty.saleorderId}","title":"订单信息"}'>${goodsWarranty.saleorderNo }</a></td>
								<td>${goodsWarranty.serviceNo }</td>
								<td>${goodsWarranty.distributorName }</td>
								<td>${goodsWarranty.terminalName}</td>
								<td class="text-left">
								<div class="brand-color1"><a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewgoods${goodsWarranty.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${goodsWarranty.goodsId}","title":"产品信息"}'>${goodsWarranty.goodsName }</a></div>
								<div>${goodsWarranty.sku }</div>
								<div>${goodsWarranty.materialCode }</div>
								</td>
								<td>${goodsWarranty.goodsSn }</td>
								<td><date:date value="${goodsWarranty.addTime}" /></td>
								<td>
									<span class="edit-user addtitle"
										tabTitle='{"num":"viewwarranty${goodsWarranty.saleorderGoodsWarrantyId }","link":"./order/saleorder/viewgoodswarrantyNew.do?saleorderGoodsWarrantyId=${goodsWarranty.saleorderGoodsWarrantyId}","title":"查看保卡"}'>查看</span>
								</td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
			<c:if test="${empty list }">
				<!-- 查询无结果弹出 -->
				<div class="noresult">查询无结果！</div>
			</c:if>
	</div>
	<div>
		<tags:page page="${page}" />
	</div>
</div>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/aftersales/goodswarranty/list_goodswarranty.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../../common/footer.jsp"%>
