<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="宝石花出库单列表" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="content logistics-warehousein-index">
	<div class="searchfunc">
		<form method="post" id="search" action="<%= basePath %>warehouse/warehousesout/flowerPrintOutListPage.do">
			<ul>
				<li><label class="infor_name">出库时间</label>
					<input class="Wdate f_left input-smaller96 mr5" type="text" placeholder="请选择日期" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'searchEndtimeStr\')}'})"
						   name="searchBegintimeStr" id="searchBegintimeStr" value='<date:date value ="${saleorder.searchBegintime}" format="yyyy-MM-dd"/>'>
					<div class="gang">-</div>
					<input class="Wdate f_left input-smaller96" type="text" placeholder="请选择日期" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'searchBegintimeStr\')}'})"
						   name="searchEndtimeStr" id="searchEndtimeStr" value='<date:date value ="${saleorder.searchEndtime}" format="yyyy-MM-dd"/>'>
				</li>
				<li><label class="infor_name">收货方</label>
					<input type="text" class="input-middle" name="takeTraderName" id="takeTraderName" value="${saleorder.takeTraderName}" />
				</li>
			</ul>
			<div class="tcenter">
				<input type="hidden" name="secondSearch" value="second"/>
				<span class="confSearch bt-small bt-bg-style bg-light-blue"
					onclick="search();" id="searchSpan">搜索</span>
				<span class="bt-small bg-light-blue bt-bg-style mr20" onclick="reset();">重置</span>

			</div>
		</form>
	</div>
	<div class="content">
		<!-- normal-list-page -->
		<div class="fixdiv">
			<div class="superdiv" style='width:100%;'>
				<table class="table table-bordered table-striped table-condensed table-centered">
					<thead>
					<tr>
						<th class="wid1">序号</th>
						<th class="wid3">订单号</th>
						<th class="wid5">客户名称</th>
						<th class="wid3">订单实际金额</th>
						<th class="wid3">生效日期</th>
						<th class="wid3">收款状态</th>
						<th class="wid3">发货状态</th>
						<th class="wid3">收货状态</th>
						<th class="wid2">操作</th>
					</tr>
					</thead>
					<tbody>
					<c:forEach var="list" items="${saleorderList}" varStatus="num">
						<tr>
							<td>${num.count}</td>
							<td>
								<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewsaleorder${list.saleorderId}","link":"./order/saleorder/view.do?saleorderId=${list.saleorderId}","title":"订单信息"}'>
										${list.saleorderNo}
								</a>
							</td>
							<td>
								<c:choose>
									<c:when test="${empty list.traderId || list.traderId == 0}">
										${list.traderName}
									</c:when>
									<c:otherwise>
										<a class="addtitle" href="javascript:void(0);" tabtitle='{"num":"viewcustomer${list.traderId}", "link":"./trader/customer/baseinfo.do?traderId=${list.traderId}", "title":"客户信息"}'>${list.traderName}</a>
									</c:otherwise>
								</c:choose>
							</td>

							<td>${list.realAmount}</td>
							<td><date:date value="${list.validTime}" format="yyyy-MM-dd" /></td>
							<td>
								<c:choose>
									<c:when test="${list.paymentStatus eq 0}">
										未收款
									</c:when>
									<c:when test="${list.paymentStatus eq 1}">
										部分收款
									</c:when>
									<c:when test="${list.paymentStatus eq 2}">
										全部收款
									</c:when>
								</c:choose>
							</td>

							<td>
								<c:choose>
									<c:when test="${list.deliveryStatus eq 0}">
										<span style="color: red">未发货</span>
									</c:when>
									<c:when test="${list.deliveryStatus eq 1}">
										<span style="color: red">部分发货</span>
									</c:when>
									<c:when test="${list.deliveryStatus eq 2}">
										全部发货
									</c:when>
								</c:choose>
							</td>
							<td>
								<c:choose>
									<c:when test="${list.arrivalStatus eq 0}">
										未收货
									</c:when>
									<c:when test="${list.arrivalStatus eq 1}">
										部分收货
									</c:when>
									<c:when test="${list.arrivalStatus eq 2}">
										全部收货
									</c:when>
								</c:choose>
							</td>
							<td>
								<c:choose>
									<c:when test="${saleFlag}">
								<span class="font-blue pop-new-data" layerParams='{"width":"90%","height":"90%","title":"宝石花出库单",
								"link":"/warehouse/warehousesout/printOutOrder.do?type_f=10&orderId=${list.saleorderId}&bussinessType=496&bussinessNo=${list.saleorderNo}"}'>打印出库单</span>
									</c:when>
									<c:otherwise>
										<button class="bt-border-style border-gray" onclick="alert('非销售不可打印')">打印</button>
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</c:forEach>
					<c:if test="${empty saleorderList}">
						<!-- 查询无结果弹出 -->
						<tr>
							<td colspan="7">查询无结果！请尝试使用其他搜索条件。</td>
						</tr>
					</c:if>
					</tbody>
				</table>

			</div>
		</div>
		<c:if test="${not empty saleorderList}">
			<div>
				<tags:page page="${page}" />
			</div>
		</c:if>
	</div>
<font color="red">注:宝石花项目出库单只包含客户名称为"宝石花医疗器械有限公司"和"盘锦宝石花药材经销有限公司"的出库单,该项目若增加客户,需及时联系产品部</font>
</div>
<script type="text/javascript"
	src='<%= basePath %>static/js/logistics/warehouseOut/edit_express.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>
