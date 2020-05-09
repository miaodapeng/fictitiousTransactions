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
		<input type="hidden"  id="printAllOrder" value="${printAllOrder}"> 
		<table cellpadding="0" cellspacing="0" width="650" border="0">
			<tbody>
				<tr>
					<td>
						<!-- header -->
						<table cellpadding="0" cellspacing="0" width="100%" border="0"
							height="90" style="padding: 0px;">
							<tbody>
								<tr>
									<td class="vedeng_bg" style="padding-left: 10px;">
									<c:if test="${type != 2}">
									<img width="250" src="<%=basePath%>static/images/vedenglogo_yl.png">
									</c:if>
									<c:if test="${type == 2}">
									<img width="250" src="<%=basePath%>static/images/vedeng_log_HC.png">	
									</c:if>
									</td>
									<c:if test="${type != 2}">
									<td class="align_c"
										style="line-height: 30px; font-size: 17px; font-family: Arial; font-weight: bold;">
										VEDENG贝登(www.vedeng.com)产品出货单<br> 每一次沟通，都是一次卓越的体验！
									</td>
									</c:if>
									<c:if test="${type == 2}">
									<td class="align_c"
										style="line-height: 30px; font-size: 17px; font-family: Arial; font-weight: bold;">
										医械购(go.vedeng.com)产品出货单<br> 每一次沟通，都是一次卓越的体验！
									</td>
									</c:if>
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
						<table cellpadding="0" cellspacing="0" width="650" border="0"
							align="center">
							<tbody>
								<tr height="17">
									<td style="line-height: 18px;width: 80px;" ><b>客户名称：</b></td>
									<td class="align_l" style="line-height: 18px;" >
									<c:if test="${bussinessType != 496}">
									${afterSalesDetail.traderName}
									</c:if>
									<c:if test="${bussinessType == 496}">
									${saleorder.traderName}
									</c:if>
									</td>
									<td style="line-height: 18px;width: 70px;" ><b>合同单号：</b></td>
									<td class="align_l" style="line-height: 18px;width: 100px;" nowrap="">${bussinessNo}</td>
									<td style="line-height: 18px;width: 80px;" ><b>发货日期：</b></td>
									<td style="line-height: 18px;width:155px;" >
									<date:date value="${currTime}" />
									</td>
								</tr>
								<tr>
								    <td style="line-height: 18px;width: 80px;" ><b>收货地址：</b></td>
									<td style="line-height: 18px;" >
									<c:if test="${bussinessType != 496}">
									${afterSalesDetail.address}
									</c:if>
									<c:if test="${bussinessType == 496}">
									${saleorder.takeTraderAddress}
									</c:if>
									</td>
									<td style="line-height: 18px;width: 80px;" ><b>联系人：</b></td>
									<td style="line-height: 18px;" >
									<c:if test="${bussinessType != 496}">
									${afterSalesDetail.traderContactName}
									</c:if>
									<c:if test="${bussinessType == 496}">
									${saleorder.takeTraderContactName}
									</c:if>
									</td>
									<td style="line-height: 18px;width: 80px;" ><b>联系电话：</b></td>
									<td style="line-height: 18px;" >
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
									<th colspan="20" class="align_l">&nbsp;</th>
								</tr>
								<tr>
									<td colspan="20"><strong>产品信息</strong>
										<table cellpadding="0" width="100%" cellspacing="0" border="1"
											class="table_form"
											style="border-collapse: collapse; border: 1px solid #000;">
											<tbody>
												<tr>
													<td class="align_c" nowrap="" width="15px"><b>序号</b></td>
													<td class="align_c" nowrap="" width="60px" style="word-wrap:break-word;"><b>产品名称</b></td>
													<td class="align_c" nowrap="" width="40px" style="word-wrap:break-word;"><b>品牌</b></td>
													<td class="align_c" cz-tab="J-model" nowrap="" width="40px" style="word-wrap:break-word;"><b>型号</b></td>
													<td class="align_c" width="40px" nowrap=""><b>数量单位</b></td>
													<c:if test="${type == 1}">
														<c:if test="${printFlag == 5}">
															<td class="align_c" cz-tab="J-price" nowrap="" width="40px"><b>单价(元)</b></td>
															<td class="align_c" cz-tab="J-amount" nowrap="" width="40px"><b>总金额(元)</b></td>
														</c:if>
														<td class="align_c" cz-tab="J-materialCode" nowrap=""><b>物料编码</b></td>
														<td class="align_c" cz-tab="J-batchNumber" nowrap=""><b>批次号</b></td>
                                                        <c:if test="${printFlag == 5 or printFlag == 6}">
                                                        <td class="align_c" cz-tab="J-productDate" nowrap="" width="50px"><b>生产日期</b></td>
                                                        </c:if>
                                                        <td class="align_c" cz-tab="J-expirationDate"  nowrap="" width="50px"><b>效期截止日期</b></td>
														<td class="align_c" cz-tab="J-number" width="60px" nowrap=""><b>产品注册证号/备案凭证编号</b></td>
														<td class="align_c" cz-tab="J-manufacturer"  nowrap="" ><b>生产企业</b></td>
														<td class="align_c" cz-tab="J-productCompanyLicence" nowrap="" width="80px"><b>生产企业许可证号/备案凭证编号</b></td>
														<td class="align_c" cz-tab="J-temperaTure" nowrap="" width="40px"><b>储运条件</b></td>
													</c:if>
													<c:if test="${type == 2}">
													<td class="align_c" cz-tab="J-realPrice" nowrap="" width="80px"><b>原单价</b></td>
													<td class="align_c" cz-tab="J-price" nowrap="" width="80px"><b>单价</b></td>
													<td class="align_c" cz-tab="J-materialCode" nowrap=""><b>物料编码</b></td>
													<td class="align_c" cz-tab="J-batchNumber" nowrap=""><b>批次号</b></td>
													<td class="align_c" cz-tab="J-expirationDate" nowrap=""><b>效期截止日期</b></td>
													<td class="align_c" cz-tab="J-number" width="80px" nowrap=""><b>产品注册证号/备案凭证编号</b></td>
													<td class="align_c" cz-tab="J-manufacturer" nowrap="" width="60px"><b>生产企业</b></td>
													<td class="align_c" cz-tab="J-productCompanyLicence" nowrap="" width="80px"><b>生产企业许可证号/备案凭证编号</b></td>
													<td class="align_c" cz-tab="J-temperaTure" nowrap=""><b>储运条件</b></td>
												</c:if>
												</tr>
												<c:forEach var="list" items="${woList}" varStatus="num">
												   <tr>
													<td class="align_c">${num.count}</td>
													<td class="align_c">
														<c:if test="${list.isActionGoods eq 1}">
															<span style="color:red;">【活动】</span>
														</c:if>
														${list.goodsName}
													</td>
													<td class="align_c">${list.brandName}</td>
													<td class="align_c J-model">${list.model}</td>
													<td class="align_c">${0-list.num}${list.unitName}</td>
													<c:if test="${type == 1}">
														<c:if test="${printFlag == 5}">
															<td class="align_c J-price" nowrap="">${list.price}</td>
															<td class="align_c J-amount" nowrap="">${list.amount}</td>
														</c:if>
														<td class="align_c J-materialCode" nowrap="">${list.materialCode}</td>
														<td class="align_c J-batchNumber" nowrap="">${list.batchNumber}</td>
                                                       <c:if test="${printFlag == 5 or printFlag == 6}">
														   <c:choose>
															   <c:when test="${list.productDate == 0 or list.productDate == null }">
																   <td class="align_c J-productDate">${list.productDateStr}</td>
															   </c:when>
															   <c:otherwise>
																   <td class="align_c J-productDate"><date:dateNOSingLe value="${list.productDate}"/></td>
															   </c:otherwise>
														   </c:choose>
                                                       </c:if>
														<c:choose>
															<c:when test="${list.expirationDate == 0 or list.expirationDate == null }">
																<td class="align_c J-expirationDate" >${list.title}</td>
															</c:when>
															<c:otherwise>
																<td class="align_c J-expirationDate" ><date:dateNOSingLe value="${list.expirationDate}"/></td>
															</c:otherwise>
														</c:choose>
														<td class="align_c J-number">
														<c:if test="${titleType == 1}">${list.recordNumber}</c:if>
														<c:if test="${titleType == 2}">${list.registrationNumber}</c:if>
														</td>
														<td class="align_c J-manufacturer">${list.manufacturer}</td>
														<td class="align_c J-productCompanyLicence" >${list.productCompanyLicence}</td>
														<td class="align_c J-temperaTure" nowrap=""> ${list.temperaTure} </td>
													</c:if>
													<c:if test="${type == 2}">
													<td class="align_c J-realPrice" nowrap="">${list.realPrice}</td>
													<td class="align_c J-price" nowrap="">${list.price}</td>
													<%--<td class="align_c J-maxSkuRefundAmount" nowrap="">${list.maxSkuRefundAmount}</td>--%>
													<td class="align_c J-materialCode" nowrap="">${list.materialCode}</td>
														<td class="align_c J-batchNumber" nowrap="">${list.batchNumber}</td>
														<c:choose>
															<c:when test="${list.expirationDate == 0 or list.expirationDate == null }">
																<td class="align_c J-expirationDate" nowrap="">${list.title}</td>
															</c:when>
															<c:otherwise>
															<td class="align_c J-expirationDate" nowrap=""><date:dateNOSingLe value="${list.expirationDate}" /></td>
															</c:otherwise>
														</c:choose>
														<td class="align_c J-number" nowrap="">
														<c:if test="${titleType == 1}">${list.recordNumber}</c:if>
														<c:if test="${titleType == 2}">${list.registrationNumber}</c:if>
														</td>
														<td class="align_c J-manufacturer" >${list.manufacturer}</td>
														<td class="align_c J-productCompanyLicence" >${list.productCompanyLicence}</td>
														<td class="align_c J-temperaTure" nowrap=""> ${list.temperaTure} </td>
													</c:if>
												   </tr>
												</c:forEach>
											</tbody>
										</table></td>
									<c:if test="${printFlag == 5}">
								<tr>
									<table cellpadding="0" width="100%" cellspacing="0" border="1"
										   class="table_form"
										   style="border-collapse: collapse; border: 1px solid #000;">
										<tr class="align_c ">
											<td class="align_c">金额总计(小写): ¥${totalPrice}    金额总计(大写): ¥${ChineseTotal}</td>
										</tr>
									</table>
								</tr>
								</c:if>
									<c:if test="${hcPrintOutflag}">
										<tr>
											<table cellpadding="0" width="100%" cellspacing="0" border="1"
												   class="table_form"
												   style="border-collapse: collapse; border: 1px solid #000;">
												<tr class="align_c ">
													<td class="align_c">商品总额: ¥${totalPrice}    优惠券: ¥${couponsPrice}    运费: ¥${expressPrice}   实付金额: ¥${realTotalPrice}</td>
												</tr>
											</table>
										</tr>
									</c:if>
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
										&nbsp;&nbsp;&nbsp;&nbsp;<br>
										<c:if test="${type != 2}">
										<b>友情提示：尊敬的客户，发票将在一周左右通过快递送达公司，请注意查收。</b>
										</c:if>
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