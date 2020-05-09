<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="入库单" scope="application" />
<%@ include file="../../common/common.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="stylesheet"
	href="<%=basePath%>static/css/print_out_order.css?rnd=<%=Math.random()%>" />

</head>
<body>
	<style type="text/css">
.red {
	color: red;
}
* {
	font-family: Arial;
}
</style>
	<div id="printdiv" style="margin-left: 25%">
		<table cellpadding="0" cellspacing="0" width="650" border="0">
			<tbody>
				<tr>
					<td>
						<!-- header -->
						<table cellpadding="0" cellspacing="0" width="100%" border="0"
							height="90" style="padding: 0px;">
							<tbody>
								<tr>
									<td class="vedeng_bg" style="padding-left: 10px;"><img
										width="250" src="<%=basePath%>static/images/vedenglogo_yl.png"></td>
									<td class="align_c"
										style="line-height: 30px; font-size: 17px; font-family: Arial; font-weight: bold;">
										${companyName }产品入库单<br> 每一次沟通，都是一次卓越的体验！
									</td>
								</tr>
								<tr>
									<th colspan="2" class="align_l">&nbsp;</th>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
				<tr>
					<td valign="top" align="center">
						<table cellpadding="0" cellspacing="0" width="630" border="0"
							align="center">
							<tbody>
								<tr height="16">
									<td style="line-height: 18px;"><b>供应商：</b></td>
									<td class="align_l" style="line-height: 18px;">${traderName}</td>
									<td style="line-height: 18px;"><b>到货日期：</b></td>
									<td class="align_l" style="line-height: 18px;"><date:date value="${arrivalTime}" format="yyyy-MM-dd" /></td>
								</tr>
								<tr>
									<td style="line-height: 18px;"><b>联系人：</b></td>
									<td style="line-height: 18px;">${contactName}</td>
									<td style="line-height: 18px;"><b>订单号：</b></td>
									<td style="line-height: 18px;">${bussinessNo}</td>
								</tr>
								<tr>
									<td style="line-height: 18px;"><b>联系电话：</b></td>
									<td style="line-height: 18px;">${contactMobile} / ${contactTelephone}</td>
									<td style="line-height: 18px;"><b>所属公司：</b></td>
									<td style="line-height: 18px;" colspan="6">${companyName}</td>
								</tr>

								<tr>
									<th colspan="6" class="align_l">&nbsp;</th>
								</tr>
								<tr>
									<td colspan="6"><strong>产品信息</strong>
										<table cellpadding="0" width="100%" cellspacing="0" border="1"
											class="table_form"
											style="border-collapse: collapse; border: 1px solid #000;">
											<tbody>
												<tr>
													<td class="align_c" nowrap=""><strong>序号</strong></td>
													<td class="align_c" nowrap=""><strong>贝登订货号</strong></td>
													<td class="align_c" nowrap=""><strong>产品名称</strong></td>
													<td class="align_c" nowrap=""><strong>品牌</strong></td>
													<td class="align_c" nowrap=""><strong>型号</strong></td>
													<td class="align_c" nowrap=""><strong>数量/单位</strong></td>
													<td class="align_c" nowrap=""><strong>仓库库位</strong></td>
													<c:if test="${type == 1}">
														<td class="align_c" nowrap=""><strong>物料编码</strong></td>
														<td class="align_c" nowrap=""><strong>批次号</strong></td>
														<td class="align_c" nowrap=""><strong>效期截止日期</strong></td>
													</c:if>
												</tr>
												<c:forEach var="list" items="${woList}" varStatus="num">
												   <tr>
													<td class="align_c">${num.count}</td>
													<td class="align_c">${list.sku}</td>
													<td class="align_c">${list.goodsName}</td>
													<td class="align_c">${list.brandName}</td>
													<td class="align_c">${list.model}</td>
													<td class="align_c">${list.num}/${list.unitName}</td>
													<td class="align_c">${list.warehouseArea}</td>
													<c:if test="${type == 1}">
														<td class="align_c" nowrap=""><strong>${list.materialCode}</strong></td>
														<td class="align_c" nowrap=""><strong>${list.batchNumber}</strong></td>
														<td class="align_c" nowrap=""><strong><date:date value="${list.expirationDate}" /></strong></td>
													</c:if>
												   </tr>
												</c:forEach>
											</tbody>
										</table></td>
								</tr>
								<tr>
									<td colspan="6">
										<table width="100%" cellspacing="0">
											<tbody>
												<tr>
													<td width="33%"><b>制单：</b>${userName}</td>
													<td><b>审核：</b></td>
													<td width="33%"><b>收货：</b></td>
												</tr>
											</tbody>
										</table>
									</td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<span class="confSearch bt-small bt-bg-style bg-light-blue"
		onclick="preview('printdiv')" id="searchSpan">打印</span>
<script type="text/javascript"
	src='<%=basePath%>static/js/jquery.PrintArea.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript"
	src='<%=basePath%>static/js/logistics/warehouseIn/addBarcode.js?rnd=<%=Math.random()%>'></script>
	<%@ include file="../../common/footer.jsp"%>