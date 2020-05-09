<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="入库单" scope="application" />
<%@ include file="../../common/common.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="stylesheet" href="<%=basePath%>static/css/branch-company-print.css?rnd=<%=Math.random()%>" />

</head>

<div id="printdiv_5" class="content" style="width:650px;">
	<h2 class="list-title">
		广东贝海医疗供应链管理有限公司<br /> 入库单
	</h2>
	<div class="user-infor">
		<div class="f_left">
			<ul>
				<li>
					<span class="user-infor-list" style='font-size:12px;'>进货单号:</span> 
					<span class="user-infor-content" style='font-size:12px;'>${bussinessNo}</span>
				</li>
				<li>
					<!-- 入库时间 -->
					<span class="user-infor-list" style='font-size:12px;'>进货日期:</span> 
					<span class="user-infor-content" style='font-size:12px;'><date:date value="${arrivalTime}" format="yyyy-MM-dd" /> </span>
				</li>
				<li>
					<span class="user-infor-list" style='font-size:12px;'>供商名称:</span>
					<span class="user-infor-content" style='font-size:12px;'>${traderName}</span>
				</li>
				<li>
					<span class="user-infor-list" style='font-size:12px;'>公司地址:</span> 
					<span class="user-infor-content" style='font-size:12px;'>${traderAddress}</span>
				</li>
				<li>
					<span class="user-infor-list" style='font-size:12px;'>仓库地址:</span> 
					<span class="user-infor-content" style='font-size:12px;'></span>
				</li>
			</ul>
		</div>
		<div class="f_right mt20">
			<ul>
				<li>
					<span class="user-infor-list">联系人:</span> 
					<span class="user-infor-content">${contactName}</span>
				</li>
				<li>
					<span class="user-infor-list">联系电话:</span> 
					<span class="user-infor-content"> ${contactMobile}<br/>${contactTelephone}</span>
				</li>
			</ul>
		</div>
	</div>
	<div class="branch-company-table">
		<table>
			<thead>
				<tr>
					
					<th rowspan="3" style="width: 40px; font-size:12px;font-weight:normal;">商品编码</th>
					<th rowspan="3" style="width: 35px; font-size:12px;font-weight:normal;">商品名称</th>
					<th rowspan="3" style="width: 35px; font-size:12px;font-weight:normal;">规格型号</th>
					<th rowspan="3" style="width: 40px; font-size:12px;font-weight:normal;">注册证号</th>
					<th rowspan="3" style="width: 40px; font-size:12px;font-weight:normal;">生产企业</th>
					<th rowspan="3" style="width: 20px; font-size:12px;font-weight:normal;">单位</th>
					<th rowspan="3" style="width: 20px; font-size:12px;font-weight:normal;">数量</th>
					<th rowspan="3" style="width: 40px; font-size:12px;font-weight:normal;">单价</th>
					<th rowspan="3" style="width: 55px; font-size:12px;font-weight:normal;">金额</th>
					<th rowspan="3" style="width: 60px; font-size:12px;font-weight:normal;">灭菌批号<br> 灭菌日期</th>
					<th rowspan="" style="width: 36px; font-size:12px;font-weight:normal;">生产批号</th>
					<th rowspan="3" style="width: 20px; font-size:12px;font-weight:normal;">质量状况</th>
					<th rowspan="3" style="width: 20px; font-size:12px;font-weight:normal;">包装情况</th>
				</tr>
				<tr>
					<th rowspan="" style="width: 40px; font-size:12px;
					font-weight:normal;">生产日期</th>
				</tr>
				<tr>
					<th rowspan="" style="width: 40px; font-size:12px;
					font-weight:normal;">有效期至</th>
				</tr>
			</thead>
			<tbody>
				<!-- 产品开始 -->
				<c:forEach var="list" items="${woList}" varStatus="num">
					<tr>
						<td rowspan="3" style="width: 53px;font-size:12px;font-weight:normal;">${list.materialCode}</td>
						<td rowspan="3" style="width: 80px;font-size:12px;font-weight:normal;">${list.goodsName} 12345456778990000000</td>
						<td rowspan="3" style="width: 80px;font-size:12px;font-weight:normal;">${list.model}</td>
						<td rowspan="3" style="width: 120px;font-size:12px;font-weight:normal;">${list.registrationNumber}</td>
						<td rowspan="3" style="width: 70px;font-size:12px;font-weight:normal;">${list.manufacturer}</td>
						<td rowspan="3" style="width: 40px;font-size:12px;font-weight:normal;" class="text-center">${list.unitName}</td>
						<td rowspan="3" style="width: 60px;font-size:12px;font-weight:normal;" class="text-center">${list.num}</td>
						<td rowspan="3" style="width: 80px;font-size:12px;font-weight:normal;" class="text-center">${list.price}</td>
						<td rowspan="3" style="width: 120px;font-size:12px;font-weight:normal;" class="text-center">${list.num * list.price}</td>
						<td rowspan="3" style="width: 100px;font-size:12px;font-weight:normal;"
							style="vertical-align: top; text-align: top;">
							<div style="border-bottom: 1px solid #000;" class="text-center">&nbsp;</div>
							<div class="text-center">&nbsp;</div>
						</td>
						<td style="width: 120px;" class="text-center font-size:12px;font-weight:normal;">
							<c:if test="${list.batchNumber != null or list.batchNumber != ''}">
								${list.batchNumber}
							</c:if>
							<c:if test="${list.batchNumber == null or list.batchNumber == ''}">
								<span style='opacity:0;'>&nbsp;</span>
							</c:if>
						</td> 					
						<td rowspan="3" style="width: 40px; font-size:12px;font-weight:normal;" class="text-center">合格</td>
						<td rowspan="3" style="width: 40px; font-size:12px;font-weight:normal;" class="text-center">合格</td>
					</tr>
					
					<tr>
						<td  style="width: 120px;" class="text-center">
							<span style='opacity:0;'>&nbsp;</span>
						</td>
					</tr>
					<tr>
						<td  style="width: 120px;" class="text-center">
							<c:if test="${list.expirationDate != null or list.expirationDate != ''}">
								<date:date value="${list.expirationDate}" format="yyyy-MM-dd" />
							</c:if>
							<c:if test="${list.expirationDate == null or list.expirationDate == ''}">
								<span style='opacity:0;'>&nbsp;</span>
							</c:if>
						</td>
					</tr>
				</c:forEach>
				<!-- 产品结束-->
				<tr>
					<td colspan="6" class="count" class=" p4" style="font-size:12px;">
						<div class="f_left">
							<b>本页小计：</b>
						</div>
					</td>
					<td class="text-center"  style="font-size:12px;">
						<div>
							<b>${pageTotalNum}</b>
						</div>
					</td>
					<td  style="font-size:12px;"></td>
					<td colspan="5" class=" p4"  style="font-size:12px;"><b>${pageTotalPrice}</b></td>
				</tr>
				<tr>
					<td colspan="8" style="padding: 4px;"  >
						<div class="f_left" >
							<b style="font-size:12px;">金额总合计（大写）:</b><b style="margin-left: 50px;font-size:12px;">${chineseNumberTotalPrice}</b>
						</div>
					</td>
					<td colspan="5" style="padding: 4px;"  style="font-size:12px;"><b>${pageTotalPrice}</b></td>
				</tr>
			</tbody>
		</table>
	</div>
</div>

<div class='text-center mt10 mb15'><span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="preview('printdiv_5')" id="searchSpan">打印</span></div>
<script type="text/javascript"
	src='<%=basePath%>static/js/jquery.PrintArea.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript"
	src='<%=basePath%>static/js/logistics/warehouseIn/addBarcode.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>