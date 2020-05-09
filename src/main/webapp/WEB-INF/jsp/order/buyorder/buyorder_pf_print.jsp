<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="采购普发订单打印" scope="application" />
<%@ include file="../../common/common.jsp"%>
<link rel="stylesheet"
	href='<%=basePath%>static/css/admin/buyOrder_print/system.css?rnd=<%=Math.random()%>'
	type="text/css" media="all">
<style type="text/css">
* {
	font-family: Arial, '宋体';
	font-size: 12px;
	line-height: 20px;
}

.vedeng_fg {
	color: #08528C;
}

.vedeng_bg {
	border-bottom: 4px solid #08528C;
}

.con {
	margin-left: 30px;
}

.con li {
	list-style-type: upper-roman;
}

.underline {
	border-bottom: 1px solid #000;
	width: 80px;
}

table td {
	line-height: 20px;
}
</style>

<script>
	var comp = 1;
	if (comp == 2) {
		alert("请注意：这是发往北京仓库的订单！");
	}
</script>
<div id="printdiv" >
<table cellpadding="0" cellspacing="0" width="650" border="0">
	<tbody>
		<tr>
			<td valign="top">
				<!-- body -->

				<table cellpadding="0" cellspacing="0" width="100%" border="1"
					bordercolor="#000000"
					style="border-collapse: collapse; border-top: 2px solid #000;">
					<tbody>
						<tr>
						
							<td width="50%" height="90" align="left">
							<c:if test="${ buyorderVo.companyId ==1}"><img
								src="<%=basePath%>static/images/vedenglogo.png" width="253" height="68">
							</c:if></td>
							<td class="align_r" style="padding: 0px;">
								<table width="100%" align="right" border="0" cellpadding="0"
									cellspacing="0">
									<tbody>
										<tr>
											<td class="align_r" valign="top" height="20">制单日期:
												${currTime }</td>
										    <c:if test="${ buyorderVo.companyId ==1}">
											 <td class="align_r">www.vedeng.com</td>
											</c:if>
										</tr>
										<tr>
											<td height="46" colspan="2"><div
													style="font-size: 20px; font-weight: bold;">订单号:
													${buyorderVo.buyorderNo }</div></td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
						<tr>
							<td class="align_l">
							 <c:if test="${ buyorderVo.companyId ==1}">
							<b>买方：南京贝登医疗股份有限公司</b>
							</c:if>
							 <c:if test="${ buyorderVo.companyId !=1}">
							 <b>买方：${company.companyName }</b>
							 </c:if>
							</td>
							<td width="50%" class="align_l"><b>卖方：${ buyorderVo.traderName}</b></td>

						</tr>
						<tr>
							<td class="align_l" valign="top">
								<table width="100%" cellpadding="0" cellspacing="0">
									<tbody>
										<tr>
											<td height="20">采购人员：${detail.realName }</td>
										</tr>
										<tr>
											<td height="20">电话：${detail.telephone }</td>
										</tr>
										<tr>
											<td height="20">传真：${detail.fax }</td>
										</tr>
										<tr>
											<td height="20">邮件：${detail.email }</td>
										</tr>
									</tbody>
								</table>
							</td>
							<td class="align_l" valign="top">
								<table width="100%" cellpadding="0" cellspacing="0">
									<tbody>
										<tr>
											<td height="20">销售人员：${ buyorderVo.traderContactName}</td>
										</tr>
										<tr>
											<td height="20">电话：${buyorderVo.traderContactTelephone }</td>
										</tr>
										<tr>
											<td height="20">传真：${buyorderVo.fax }</td>
										</tr>
										<tr>
											<td height="20">邮件：${buyorderVo.email }</td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
					</tbody>
				</table>

				<table cellpadding="0" cellspacing="0" width="100%">
					<tbody>
						<tr>
							<td class="align_l"><b>发票及货物接收信息</b></td>
						</tr>
					</tbody>
				</table>
				<table cellpadding="0" cellspacing="0" width="100%" border="1"
					bordercolor="#000000" style="border-collapse: collapse;">
					<tbody>
						<tr>
							<td width="50%" style="padding: 1px;">发票接收：
							<c:if test="${ buyorderVo.companyId ==1}">
							南京贝登医疗股份有限公司<br>
							</c:if>
							<c:if test="${ buyorderVo.companyId !=1}">
							${company.companyName }<br>
							</c:if>
									接收人：${detail.realName }<br> 电话：${detail.mobile } / ${detail.telephone }<br>
									<c:if test="${ buyorderVo.companyId ==1}">
											地址：南京市白下高新技术产业园永丰大道紫霞路斯坦德电子商务大厦北楼三层<br>
									</c:if>
									<c:if test="${ buyorderVo.companyId !=1}">
											地址：${delivery.address }<br>
									</c:if>
											</td>
							<td style="padding: 1px;">货物接收：
							<c:if test="${ buyorderVo.companyId ==1}">
							南京贝登医疗股份有限公司<br>
							</c:if>
							<c:if test="${ buyorderVo.companyId !=1}">
							${company.companyName }<br>
							</c:if>
									接收人：${buyorderVo.takeTraderContactName }<br> 电话：${buyorderVo.takeTraderContactMobile }/${buyorderVo.takeTraderContactTelephone }<br>
											地址：${buyorderVo.takeTraderArea}${buyorderVo.takeTraderAddress}<br></td>
						</tr>
					</tbody>
				</table>

				<table cellpadding="0" cellspacing="0" width="100%">
					<tbody>
						<tr>
							<td class="align_l"><b>产品信息</b></td>
						</tr>
					</tbody>
				</table>
				<table cellpadding="0" cellspacing="0" width="100%" border="1"
					bordercolor="#000000" style="border-collapse: collapse;">
					<tbody>
						<tr>
							<td class="align_c" nowrap="" width="20"><b></b></td>
							<td class="align_c" nowrap="" width="30"><b>订货号</b></td>
							<td class="align_c" nowrap="" width="200px"><b>产品名称</b></td>
							<td class="align_c" nowrap=""><b>品牌</b></td>
							<td class="align_c" nowrap=""><b>型号</b></td>
							<td class="align_c" nowrap=""><b>注册证号</b></td>
							<td class="align_c" nowrap=""><b>备注</b></td>
							<td class="align_c" nowrap="" width="30"><b>数量</b></td>
							<td class="align_c" nowrap="" width="30"><b>单价<br>(RMB)</b></td>
							<td class="align_c" nowrap="" width="30"><b>金额<br>(RMB)</b></td>
							<td class="align_c" nowrap="" width="40"><b>到货日期</b></td>
						</tr>
						<c:forEach var="list" items="${buyorderGoodsList}" varStatus="num">
						<tr>
							<td class="align_c">${num.count}</td>
							<td class="align_c" nowrap="">${list.sku }</td>
							<td>${list.goodsName }</td>
							<td>${list.brandName }</td>
							<td>${list.model }</td>
							<td>${list.registrationNumber }</td>
							<td>${list.insideComments }</td>
							<td class="align_c" nowrap="">${list.num }</td>
							<td class="align_c" nowrap="">${list.price }</td>
							<td class="align_c" nowrap="">${list.price*list.num }</td>
							<td class="align_c" nowrap=""><date:date value="${list.arrivalTime}" /></td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
				<table cellpadding="0" cellspacing="0" width="100%"
					style="border-collapse: collapse; border: 2px solid #000;">
					<tbody>
						<tr>
							<td class="align_c" nowrap=""
								style="border: 1px solid #000; border-top: 0px;"><b>付款方式</b></td>
							<!-- <td class='align_c' nowrap style='border:1px solid #000;border-top:0px;'><b>货期</b></td> -->
							<td class="align_c" nowrap=""
								style="border: 1px solid #000; border-top: 0px;"><b>运费</b></td>
							<td class="align_c" nowrap=""
								style="border: 1px solid #000; border-top: 0px;"><b>发票类型</b></td>
							<td class="align_c" nowrap=""
								style="border: 1px solid #000; border-top: 0px;"><b>总金额(RMB)</b></td>
						</tr>
						<tr>
							<td class="align_l" style="border: 1px solid #000;">
							<c:if test="${buyorderVo.paymentType == 419}">
							先款后货，预付100%
							<br>预付金额${buyorderVo.prepaidAmount }元
							</c:if>
							<c:if test="${buyorderVo.paymentType == 420}">
							先货后款，预付80%
							<br>预付金额${buyorderVo.prepaidAmount }元
							<br>账期支付${buyorderVo.accountPeriodAmount }元
							</c:if>
							<c:if test="${buyorderVo.paymentType == 421}">
							先货后款，预付50%
							<br>预付金额${buyorderVo.prepaidAmount }元
							<br>账期支付${buyorderVo.accountPeriodAmount }元
							</c:if>
							<c:if test="${buyorderVo.paymentType == 422}">
							先货后款，预付30%
							<br>预付金额${buyorderVo.prepaidAmount }元
							<br>账期支付${buyorderVo.accountPeriodAmount }元
							</c:if>
							<c:if test="${buyorderVo.paymentType == 423}">
							先货后款，预付0%
							<br>预付金额${buyorderVo.prepaidAmount }元
							<br>账期支付${buyorderVo.accountPeriodAmount }元
							</c:if>
							<c:if test="${buyorderVo.paymentType == 424}">
							自定义
							<br>预付金额${buyorderVo.prepaidAmount }元
							<br>账期支付${buyorderVo.accountPeriodAmount }元
							<br>尾款${buyorderVo.retainageAmount }元
							<br>尾款期限${buyorderVo.retainageAmountMonth }月
							</c:if>
							</td>
							<td class="align_l" style="border: 1px solid #000;">合同总额包含运费，送货上门</td>
							<td class="align_l" style="border: 1px solid #000;">
							<c:forEach var="list" items="${invoiceTypes}">
	                    	<c:if test="${buyorderVo.invoiceType == list.sysOptionDefinitionId}">${list.title}</c:if>
	                        </c:forEach>
							</td>
							<td class="align_c" style="border: 1px solid #000;">${buyorderVo.totalAmount }</td>
						</tr>
					</tbody>
				</table> <br>

					<table cellpadding="0" cellspacing="0" width="100%" border="0"
						style="border-collapse: collapse; border: 1px solid #000;">
						<tbody>
							<tr>
								<td valign="top">
									<ol style="margin-left: 30px;">
										<li><b>卖方发送货物必须附上送货单，并在送货单上注明双方签订合同时的订单号；卖方开具的发票必须注明双方签订合同时的订单号；否则会影响后续合作和付款。</b></li>
										<li>如因卖方原因（除不可抗力）造成货物不能在订单规定的日期完成交货，买方有权每天扣除合同总金额的0.5%作为违约赔偿金，直到收到货物为止；如到合同规定的交期后2周，卖方仍不能交货，买方有权提出退货，卖方必须全额退还买方的订货预付款。</li>
										<li>在任何检查、交货、使用、货物接收或卖方付款的情况下，质量保证都依然有效。当收到买方对产品与订单不符，或质量不合格声明时，卖方负责更换产品。在处理质量保证案时，卖方应考虑买方的经营需要。买方由此而生的所有费用，应由卖方负担。</li>
										<!-- <li>卖方须在发票、送货单及其他相关的文件上注明买方的订单号码、订货号。</li> -->
										<li>买方向卖方提供的与此订单有关的所有信息，包括但不限于关于产品、商业机密、图纸、文件、商标，卖方必须保密，且未经买方事先书面同意，卖方不得公开或以其他方式向第三方透露。</li>
										<li>因此订单产生的，或与此订单相关的任何争议应通过双方友好协商解决，如不能协商解决，可以向
										<c:if test="${ buyorderVo.companyId ==1}">
										南京市白下区人民法院提起诉讼。
										</c:if>
										<c:if test="${ buyorderVo.companyId !=1}">
										合同签订地人民法院提请诉讼。
										</c:if>
										</li>
										<li>违约责任：合同生效后，卖方不得单方面撤销此供货合同，若单方面撤销合同，需赔偿此供货合同总额的20%作为赔偿金。</li>

										<li>送货方式：其他送货上门。</li>

										<li>请在收到订单后签字盖章确认后回传给买方。</li>
										<li>本订单买卖双方盖章或签字有效，传真件具有与原件同等的法律效力。</li>
									</ol>
								</td>
								<td width="200" style="border-left: 1px solid #000;">
									<table width="100%" border="0" height="350">
										<tbody>
											<tr>
												<td valign="top"><b>供应商签字确认</b></td>
											</tr>
											<tr>
												<td><div style="border-bottom: 1px solid #000;">&nbsp;</div></td>
											</tr>
											<tr>
												<td valign="top"><b>公司盖章</b></td>
											</tr>
											<tr>
												<td><div style="border-bottom: 1px solid #000;">&nbsp;</div></td>
											</tr>
										</tbody>
									</table>
								</td>
							</tr>
						</tbody>
					</table>
                    <c:if test="${ buyorderVo.companyId ==1}">
                    <div 
						style=" background-image:url('<%=basePath%>static/images/order_sign_b.png'); background-repeat: no-repeat; background-position: 75%; border: 0px solid #f00; padding-top: 30px; padding-bottom: 40px; background-size: 140px auto;">
						
						<table cellpadding="0" cellspacing="0" width="100%" border="0">
							<tbody>
								<tr>
									<td height="100" width="25%" nowrap="">制单 <u>&nbsp;${detail.realName }&nbsp;</u></td>
									<td width="25%" nowrap="">审核&nbsp;${ buyorderVo.shName}&nbsp;</td>
									<td>盖章 ___________</td>
									<td width="25%" nowrap="">日期 <u>&nbsp;${currTime }&nbsp;</u></td>
								</tr>
							</tbody>
						</table>
					</div>
                    </c:if>
					<c:if test="${ buyorderVo.companyId !=1}">
					<div 
						style=" background-image:url('<%=basePath%>static/images/order_sign_bh.png'); background-repeat: no-repeat; background-position: 75%; border: 0px solid #f00; padding-top: 30px; padding-bottom: 40px; background-size: 140px auto;">
						
						<table cellpadding="0" cellspacing="0" width="100%" border="0">
							<tbody>
								<tr>
									<td height="100" width="25%" nowrap="">制单 <u>&nbsp;${detail.realName }&nbsp;</u></td>
									<td width="25%" nowrap="">审核&nbsp;${ buyorderVo.shName}&nbsp;</td>
									<td>盖章 ___________</td>
									<td width="25%" nowrap="">日期 <u>&nbsp;${currTime }&nbsp;</u></td>
								</tr>
							</tbody>
						</table>
					</div>
					</c:if>

					<table cellpadding="0" cellspacing="0" width="100%" border="1"
						bordercolor="#000000"
						style="border-collapse: collapse; border-top: 2px solid #000;">
						<tbody>
							<tr>
								<td class="align_l"><b>买方开票信息</b></td>
								<td width="50%" class="align_l"><b>卖方汇款信息</b></td>
							</tr>
							<tr>
								<td class="align_l" valign="top" style="padding: 0px;">
									<table width="100%" cellpadding="0" cellspacing="0">
										<tbody>
										<c:if test="${ buyorderVo.companyId ==1}">
											<tr>
												<td height="20">账户名称：南京贝登医疗股份有限公司</td>
											</tr>
											<tr>
												<td height="20">注册地址及电话：南京市秦淮区永丰大道8号南京白下高新技术产业园区三号楼106A室
													025-68538253</td>
											</tr>
											<tr>
												<td height="20">开户行/账号：中国建设银行江苏南京中山南路支行
													32001881236052503686</td>
											</tr>
											<tr>
												<td height="20">税号：91320100589439066H</td>
											</tr>
										</c:if>
										<c:if test="${ buyorderVo.companyId !=1}">
											<tr>
												<td height="20">账户名称：广东贝海医疗供应链管理有限公司</td>
											</tr>
											<tr>
												<td height="20">注册地址及电话：广州市越秀区寺右南路一街一巷九号广日大厦901A室
													020-87665453</td>
											</tr>
											<tr>
												<td height="20">开户行/账号：中国建设银行股份有限公司广州五羊新城支行
													44050140090500000444</td>
											</tr>
											<tr>
												<td height="20">税号：91440101MA59ULG4X6</td>
											</tr>
										</c:if>
										</tbody>
									</table>
								</td>
								<td class="align_l" valign="top" style="padding: 0px;">
									<table width="100%" cellpadding="0" cellspacing="0">
										<tbody>
										<tr>
											<td height="20" valign="middle">账户名称：${ buyorderVo.traderName}</td>
										</tr>
										<tr>
											<td height="40">开户行：${buyorderVo.bank }</td>
										</tr>
										<tr>
											<td height="40">账号：${buyorderVo.bankAccount }</td>
										</tr>
									</tbody>
									</table>
								</td>
							</tr>
						</tbody>
					</table>
			</td>
		</tr>
		<tr>
			<td>
				<!-- footer -->
				<table cellpadding="0" cellspacing="0" width="100%" align="align_r"
					border="0">
					<tbody>
						<tr>
						</tr>
					</tbody>
				</table>
			</td>
		</tr>
	</tbody>
</table>
</div>
<div class='tcenter mt20 mb15'>
	<span class="confSearch bt-small bt-bg-style bg-light-blue"
	onclick="preview('printdiv')" id="searchSpan">打印</span>
</div>
<script type="text/javascript"
	src='<%=basePath%>static/js/jquery.PrintArea.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript"
	src='<%=basePath%>static/js/logistics/warehouseIn/addBarcode.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>