<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="出库单" scope="application" />
<%@ include file="../../common/common.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="stylesheet"
	href="<%=basePath%>static/css/print_out_order.css?rnd=<%=Math.random()%>" />

	<style type="text/css">
.red {
	color: red;
}
* {
	font-family: Arial;
}
</style>
	<div id="printdiv" style="margin-left: 0">
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
										VEDENG贝登(www.vedeng.com)产品出货单<br> 每一次沟通，都是一次卓越的体验！
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
									<td style="line-height: 18px;width: 80px;"><b>客户名称：</b></td>
									<td class="align_l" style="line-height: 18px;">
									<c:if test="${bussinessType != 496}">
									${afterSalesDetail.traderName}
									</c:if>
									<c:if test="${bussinessType == 496}">
									${saleorder.traderName}
									</c:if>
									</td>
									<td style="line-height: 18px;width: 80px;"><b>合同单号：</b></td>
									<td class="align_l" style="line-height: 18px;">${bussinessNo}</td>
									<td style="line-height: 18px;width: 80px;"><b>发货日期：</b></td>
									<td style="line-height: 18px;">
									<date:date value="${express.addTime}" />
									</td>
								</tr>
								<tr>
								    <td style="line-height: 18px;width: 80px;"><b>收货地址：</b></td>
									<td style="line-height: 18px;">
									<c:if test="${bussinessType != 496}">
									${afterSalesDetail.address}
									</c:if>
									<c:if test="${bussinessType == 496}">
									${saleorder.takeTraderAddress}
									</c:if>
									</td>
									<td style="line-height: 18px;width: 80px;"><b>联系人：</b></td>
									<td style="line-height: 18px;">
									<c:if test="${bussinessType != 496}">
									${afterSalesDetail.traderContactName}
									</c:if>
									<c:if test="${bussinessType == 496}">
									${saleorder.takeTraderContactName}
									</c:if>
									</td>
									<td style="line-height: 18px;width: 80px;"><b>联系电话：</b></td>
									<td style="line-height: 18px;">
									<c:if test="${bussinessType != 496}">
									${afterSalesDetail.traderContactMobile}
										/ ${afterSalesDetail.traderContactTelephone}
									</c:if>
									<c:if test="${bussinessType == 496}">
									${saleorder.takeTraderContactMobile}
										/ ${saleorder.takeTraderContactTelephone}
									</c:if>
									</td>
								</tr>
								<tr>
									<td style="line-height: 18px;width: 80px;"><b>承运商：</b></td>
									<td style="line-height: 18px;">
									${express.logisticsName}
									</td>
									<td style="line-height: 18px;width: 100px;"><b>物流/快递单号：</b></td>
									<td style="line-height: 18px;">
									${express.logisticsNo}
									</td>
									<td style="line-height: 18px;width: 100px;"><b>本次发货件数：</b></td>
									<td style="line-height: 18px;">
									${express.j_num}
									</td>
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
													<td class="align_c" nowrap=""><strong>产品名称</strong></td>
													<td class="align_c" nowrap=""><strong>品牌</strong></td>
													<td class="align_c" nowrap=""><strong>型号</strong></td>
													<td class="align_c" nowrap=""><strong>数量单位</strong></td>
													<td class="align_c" nowrap=""><strong>物料编码</strong></td>
												    <td class="align_c" nowrap=""><strong>批次号</strong></td>
												</tr>
												<c:forEach var="list" items="${woList}" varStatus="num">
												   <tr>
													<td class="align_c">${num.count}</td>
													<td class="align_c">${list.goodsName}</td>
													<td class="align_c">${list.brandName}</td>
													<td class="align_c">${list.model}</td>
													<td class="align_c">${list.num}${list.unitName}</td>
													<td class="align_c" nowrap=""><strong>${list.materialCode}</strong></td>
													<td class="align_c" nowrap=""><strong>${list.batchNumber}</strong></td>
												   </tr>
												</c:forEach>
											</tbody>
										</table></td>
								</tr>
								<tr>
									<td colspan="6">
										<table width="100%" cellspacing="0">
											<tbody >
												<tr >
													<td width="33%"><b>制单：${userName }</b></td>
													<td><b>审核：Shawn.xiong</b></td>
													<div></div>
													<td width="33%" ><b >发货：${outName }</b></td>
												</tr>
											</tbody>
										</table>
									</td>
								</tr>
								<tr>
									<td colspan="6" style="position:relative;"><b>客户签收：</b>_______________________________<b>(签收后请回传贝登公司)</b>
										&nbsp;&nbsp;&nbsp;&nbsp;<b>收货：</b>_______________<br> <b>友情提示：尊敬的客户，发票将在一周左右通过快递送达公司，请注意查收。</b>
										<img src="<%=basePath%>static/images/printoutlog.png" style="z-index: 12;position: absolute;right: 47px;top: -8px;display: inline-block;height: 100px;width: 140px;">
									</td>
								</tr>
								<tr>
									<td colspan="6">
										<table width="100%" border="0" cellpadding="0" cellspacing="0">
											<tbody>
												<tr>
													<td style="padding: 0px;">南京贝登医疗股份有限公司（www.vedeng.com）</td>
													<td width="33%" class="align_r" style="padding: 0px;">TEL:
														4006-999-569</td>
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
		onclick="preview('printdiv')" style="margin-top: 50px" id="searchSpan">打印</span>
<script type="text/javascript"
	src='<%=basePath%>static/js/jquery.PrintArea.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript"
	src='<%=basePath%>static/js/logistics/warehouseIn/addBarcode.js?rnd=<%=Math.random()%>'></script>
	<%@ include file="../../common/footer.jsp"%>