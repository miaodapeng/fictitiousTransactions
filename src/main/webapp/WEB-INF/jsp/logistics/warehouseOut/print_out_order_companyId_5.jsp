<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="广东贝海医疗供应链管理有限公司销售单" scope="application" />
<%@ include file="../../common/common.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<link rel="stylesheet" href="<%=basePath%>static/css/branch-company-print.css?rnd=<%=Math.random()%>" />

<div id="printdiv" class="content" style="width:660px;">
	<h2 class="list-title">
		广东贝海医疗供应链管理有限公司<br /> 销售单（随货同行）
	</h2>
	<div class="user-infor">
		<div class="f_left">
			<ul>
				<li>
					<span class="user-infor-list">客户名称:</span> 
					<span class="user-infor-content">
						<c:if test="${bussinessType != 496}">
							${afterSalesDetail.traderName}
						</c:if>
						<c:if test="${bussinessType == 496}">
							${saleorder.traderName}
						</c:if>
					</span>
				</li>
				<li>
					<span class="user-infor-list">联系电话:</span> 
					<span class="user-infor-content">
						<c:if test="${bussinessType != 496}">
							${afterSalesDetail.traderContactName}&nbsp;${afterSalesDetail.traderContactMobile}&nbsp;${afterSalesDetail.traderContactTelephone}
						</c:if>
						<c:if test="${bussinessType == 496}">
							${saleorder.takeTraderContactName}&nbsp;${saleorder.takeTraderContactMobile}&nbsp;${saleorder.takeTraderContactTelephone}
						</c:if> 
					</span>
				</li>
				<li>
					<span class="user-infor-list">地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址:</span>
					<span class="user-infor-content">
						<c:if test="${bussinessType !=496}">
							${afterSalesDetail.address}
						</c:if>
						<c:if test="${bussinessType == 496}">
							${saleorder.takeTraderAddress}
						</c:if>
					</span>
				</li>
			</ul>
		</div>
		<div class="f_right mt20">
			<ul>
				<li>
					<span class="user-infor-list">单号:</span> 
					<span class="user-infor-content">${bussinessNo}</span>
				</li>
				<li>
					<span class="user-infor-list">日期:</span> 
					<!-- 当前打印订单时间 -->
					<span class="user-infor-content">${nowDade}</span>
				</li>
			</ul>
		</div>
	</div>
	<div class="branch-company-table">
		<table>
			<thead>
				<tr>
					<th rowspan="3" style="width:90px;font-size:12px;font-weight:normal;">商品名称/编码</th>
					<th rowspan="3" style="width: 60px;font-size:12px;font-weight:normal;">规格型号</th>
					<th rowspan="3" style="width: 100px;font-size:12px;font-weight:normal;">生产厂家及许可证</th>
					
					<th rowspan="3" style="width: 30px;font-size:12px;font-weight:normal;">单位</th>
					<th rowspan="3" style="width: 40px;font-size:12px;font-weight:normal;">数量</th>
					<!-- 销售订单的单价 -->
					<th rowspan="3" style="width: 60px;font-size:12px;font-weight:normal;">单价</th>
					<th rowspan="3" style="width: 60px;font-size:12px;font-weight:normal;">金额</th>
					<th rowspan="3" style="width: 70px;font-size:12px;font-weight:normal;">注册证号</th>
					<th style="width: 80px;font-size:12px;font-weight:normal;">生产批号</th>
					<!-- 目前置空 -->
					<th rowspan="3" style="width: 30px;font-size:12px;font-weight:normal;">储存条件</th>
					<!-- 默认合格 -->
					<th rowspan="3" style="width: 30px;font-size:12px;font-weight:normal;">质量状况</th>
				</tr>
				<tr>
					<!-- 目前置空 -->
					<th style="font-size:12px;font-weight:normal;">生产日期</th>
				</tr>
				<tr>
					<th style="font-size:12px;font-weight:normal;">有效期至</th>
				</tr>
			</thead>
			<tbody>
				<!-- 产品开始 -->
				<c:forEach var="list" items="${woList}" varStatus="num">
					<tr>
						<td rowspan="3" style="font-size:12px;font-weight:normal;">
							<div>
								<div class="pro-name">${list.goodsName}</div>
								<br>
								<div class="code-num">${list.materialCode}</div>
							</div>
						</td>
						<td rowspan="3" class="vertical-top" style="font-size:12px;font-weight:normal;">${list.model}</td>
						<td rowspan="3" class="vertical-top" style="font-size:12px;font-weight:normal;">${list.manufacturer}<br>${list.productionLicenseNumber}</td>
						<td rowspan="3" class="text-center" style="font-size:12px;font-weight:normal;">${list.unitName}</td>
						<td rowspan="3" class="text-right" style="font-size:12px;font-weight:normal;">${0-list.num}</td>
						<c:if test="${list.price == null or list.price == 0}">
							<td rowspan="3" class="text-right" style="font-size:12px;font-weight:normal;">&nbsp;</td>
							<td rowspan="3" class="text-right" style="font-size:12px;font-weight:normal;">&nbsp;</td>
						</c:if>
						<c:if test="${list.price != null and list.price != 0 }">
							<td rowspan="3" class="text-right" style="font-size:12px;font-weight:normal;">${list.price}</td>
							<td rowspan="3" class="text-right" id="td_price_${num}" style="font-size:12px;font-weight:normal;">${(0-list.num) * list.price}</td>
						</c:if>
						<td rowspan="3" class="text-center" style="font-size:12px;font-weight:normal;">
							<c:if test="${list.registrationNumber == null}">&nbsp;</c:if>
							<c:if test="${list.registrationNumber != null}">${list.registrationNumber}</c:if>
						</td>
						<td class="text-center" style="font-size:12px;font-weight:normal;">
							<c:if test="${list.batchNumber == null}">&nbsp;</c:if>
							<c:if test="${list.batchNumber != null}">${list.batchNumber}</c:if>
						</td>
						<td rowspan="3" class="text-center" style="font-size:12px;font-weight:normal;">&nbsp;</td>
						<td rowspan="3" class="text-center" style="font-size:12px;font-weight:normal;">合格 </td>
					</tr>
					<tr>
						<td class="text-center" style="font-size:12px;font-weight:normal;">&nbsp;</td>
					</tr>
					<tr>
						<td class="text-center" style="font-size:12px;font-weight:normal;">
							<c:if test="${list.expirationDate == null or list.expirationDate == '' }">&nbsp;</c:if>
							<c:if test="${list.expirationDate != null and list.expirationDate != ''}"><date:date value="${list.expirationDate}" format="yyyy-MM-dd" /></c:if>
						</td>
					</tr>												  
				</c:forEach>
				
				<!-- 产品结束-->
				<tr>
					<td colspan="5" class="count p4" style="font-size:12px;font-weight:bold;">
						<div class="f_left">本页小计：</div>
						<div class="f_right">
							<b>${pageTotalNum}</b>
						</div>
					</td>
					<!-- 数字 -->
					<td colspan="6" class="text-center" style="font-size:12px;font-weight:normal;"><b>${pageTotalPrice}</b></td>
				</tr>
				<tr>
					<td colspan="5" style="border-right: none;font-size:12px;font-weight:normal;" class=" p4">
						<div class="f_left">
							<b>金额总合计（大写）:</b><b style="margin-left: 50px;">${chineseNumberTotalPrice} </b>
						</div>
					</td>
					<!-- 数字 -->
					<td colspan="6" class="text-center  p4" style="font-size:12px;font-weight:normal;"><b>${pageTotalPrice}</b></td>
				</tr>
				<tr>
					<td colspan="11" class=" p4" style="font-size:12px;font-weight:normal;">备注：</td>
				</tr>

			</tbody>
		</table>
	</div>
	<div class="corper-members">
		<ul>
			<li style="font-size:12px;font-weight:normal;">制单员：${userName}</li>
			<li style="font-size:12px;font-weight:normal;">保管员：</li>
			<li style="font-size:12px;font-weight:normal;">审核人：Luyi</li>
			<li style="font-size:12px;font-weight:normal;">签收人：</li>
		</ul>
	</div>
</div>
<div class='text-center mt10 mb15'><span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="preview('printdiv')" style="margin-top: 50px" id="searchSpan">打印</span></div>
<script type="text/javascript" src='<%=basePath%>static/js/jquery.PrintArea.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src='<%=basePath%>static/js/logistics/warehouseIn/addBarcode.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>