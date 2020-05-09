<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="未到货商品" scope="application" />
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
								<tr>
									<th colspan='4' class='align_c' style='font-size:16px;line-height:50px;height:50px;font-family:Arial;'><strong>VEDENG未到货打印单</strong></th>
								  </tr>
								  <tr>
									<td width='75'><b>供应商：</b></td>
									<td width='235'  class='align_l'>${traderName}</td>
									<td><b>订单号：</b></td>
									<td>${orderNo}</td></tr>
									
								  <tr>
								  	<td><b>所属公司</b></td>
									<td>${companyName}</td>
									<td width='75'><b>打印时间：</b></td>
									<td width='235'>${time}</td></tr>
								  <tr>
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
													<td class="align_c" nowrap=""><strong>制造商型号</strong></td>
													<td class="align_c" nowrap=""><strong>物料编码</strong></td>
													<td class="align_c" nowrap=""><strong>未到货数量</strong></td>
													<td class="align_c" nowrap=""><strong>单位</strong></td>
												</tr>
												 <c:set var="flag" value="-1"></c:set>
												<c:forEach var="list" items="${list}" varStatus="num">
												<c:if test="${list.arrivalNum<list.num }">
												<c:set var="flag" value="1"></c:set>
												<tr>
													<td class="align_c">${num.count}</td>
													<td class="align_c">${list.sku}</td>
													<td class="align_c">${list.goodsName}</td>
													<td class="align_c">${list.model}</td>
													<td class="align_c">${list.materialCode}</td>
													<td class="align_c">${list.num-list.arrivalNum}</td>
													<td class="align_c">${list.unitName}</td>
												   </tr>
												</c:if>
												</c:forEach>
											</tbody>
										</table>
										<c:if test="${flag eq -1}">
										<table class="table_form" style="border-collapse: collapse; border: 1px solid #000;">
										 	<tbody>
												<tr>
						                        	<td colspan="7" style="text-align: center;">已无未入库商品！</td>
						                    	</tr>
						                     </tbody>
						           		</table>
										</c:if>
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