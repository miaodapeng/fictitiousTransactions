<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="打印顺丰快递单" scope="application" />
<%@ include file="../../common/common.jsp"%>
<%@ taglib uri="http://com.vedeng.common.util/tags" prefix="date"%>
<link rel="stylesheet" href="<%=basePath%>static/css/print.css?rnd=<%=Math.random()%>" />
<style>
body {
	margin: 0px;
	padding: 0px;
	text-align: left;
}

table {
	border-collapse: collapse;
}

table, td {
	padding: 0px;
	margin: 0px;
}

.alltbl {
	border: 0px solid #f00;
	margin-left: -40px;
	margin-top: 0px;
}

.alltbl td {
	text-align: left;
}

.toptbl {
	width: 100%;
}

.toptbl td {
	border: 0px solid #0f0;
	line-height: 25px;
	padding: 0px;
	margin: 0px;
}

.toptbl td.address {
	height: 50px;
	line-height: 25px;
}

.customer td {
	line-height: 15px;
}
</style>
	<table cellpadding="0" cellspacing="0" width='700' border='0' class='alltbl'>
		<tr>
			<td></td>
			<td rowspan='3' width='400' valign='top' align='left'>
				<!-- 右侧 -->
				<table cellpadding="0" cellspacing="0" border='0' class='toptbl'
					style='border: 0px solid #00f;'>
					<tr>
						<td height='320' valign='top'>
							<div style='margin-top: 0px; margin-left: 255px; font-size: 14px;'>
							<c:if test="${type == 1}">
							<c:if test="${btype == 1  or btype == -1 or btype==4 or btype==3}">
							<c:if test="${companyId==1 }">90049825</c:if>
							</c:if>
                       	    </c:if>
	                       	<c:if test="${type == 2}">
	                       	</c:if>
							</div>
							<div style='margin-left: 255px; font-size: 14px;'>
							<c:if test="${type == 1}">
							<c:if test="${btype == 1  or btype == -1 or btype==4 or btype==3}">
							<c:if test="${companyId==1 }">025P</c:if>
							</c:if>
	                       	</c:if>
	                       	<c:if test="${type == 2}">
	                       	</c:if>
							</div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td align='left' valign='top' width='300' style="padding-top: 55px;">
				<!-- 公司信息 -->
				<table cellpadding="0" cellspacing="0" width='100%' border='0' class='toptbl customer' style="margin-left: 20px;">
					<tr>
						<td colspan='4'>&nbsp;</td>
					</tr>
					<tr>
						<td colspan='4'>&nbsp;</td>
					</tr>
					<tr>
						<td width='20%'>&nbsp;</td>
						<td nowrap colspan='2'>
						<c:if test="${type == 1}">
						<c:if test="${btype == 1  or btype == -1 or btype==4 or btype==3}">
						<c:if test="${companyId==1 }">南京贝登医疗股份有限公司</c:if>
						<c:if test="${companyId!=1 }">广东贝海医疗供应链管理有限公司</c:if>
						</c:if>
                       	</c:if>
                       	<c:if test="${type == 2}">
	                       	${saleorder.traderName}
                       	</c:if>
						</td>
						<td width='25%' style='text-align: right;'>
                        <c:if test="${type == 1}">
                        <c:if test="${btype == 1 or btype == -1 or btype==4}">
                        <c:if test="${companyId==1 }">
                                                                         物流部
                        </c:if>
                        <c:if test="${companyId!=1 }">
                                                                         黄伟
                        </c:if>
                        </c:if>
                      <c:if test="${btype == 3}">
                        <c:if test="${companyId==1 }">
                                                                        财务部                                             
                        </c:if>
                        <c:if test="${companyId!=1 }">
                                                                         陈秋芬
                        </c:if>
                        </c:if>
                       	</c:if>
                       	<c:if test="${type == 2}">
                       		${saleorder.traderContactName}
                       	</c:if>
                        </td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td colspan='3' class='address' valign='top'>
						<c:if test="${type == 1}">
						 <c:if test="${btype == 1 or btype == -1 or btype == 3  or btype==4}">
						 <c:if test="${companyId==1 }">
						 江苏省南京市白下高新技术产业园永丰大道<br>紫霞路斯坦德电子商务大厦北楼三层
						 </c:if>
						 <c:if test="${companyId!=1 }">
						 <c:if test="${btype == 3 }">广州市越秀区寺右南一街广日大厦602</c:if>
						 <c:if test="${btype != 3 }">广东广州市越秀区寺右南路95号广州太平洋大厦310</c:if>
						 </c:if>
						 </c:if>
                       	</c:if>
                       	<c:if test="${type == 2}">
                       		${saleorder.traderAddress}
                       	</c:if>
                       	</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td colspan='3'><span style='display: inline-block; width: 80px;'></span>
						<c:if test="${type == 1}">
						<c:if test="${btype == 1 or btype == -1 or btype == 3  or btype==4}">
						<c:if test="${companyId==1 }">025-68538253</c:if>
						<c:if test="${companyId!=1 }">
						 <c:if test="${btype == 3 }">13710678448</c:if>
						 <c:if test="${btype != 3 }">18824681914</c:if>
						</c:if>
						</c:if>
                       	</c:if>
                       	<c:if test="${type == 2}">
                       		${saleorder.traderContactTelephone}/${saleorder.traderContactMobile}
                       	</c:if>
						</td>
					</tr>
				</table>
				<table cellpadding="0" cellspacing="0" width='390' border='0'
					class='toptbl customer'
					style="margin-bottom: 65px; margin-left: 20px;">
					<tr>
						<td width='20%'>&nbsp;</td>
						<td nowrap colspan='2'>
						<c:if test="${type == 1}">
							<c:if test="${btype == 1 }">
							  ${saleorder.takeTraderName}
							</c:if>
							<c:if test="${btype == 4 }">
							  ${av.traderName}
							</c:if>
							<c:if test="${ btype == 3}">
							   <c:if test="${stype==505}">
							   ${saleorder.invoiceTraderName}
							   </c:if>
							   <c:if test="${stype==504 }">
							   ${av.invoiceTraderName}
							   </c:if>
							</c:if>
							<c:if test="${btype == -1 }">
							${fileDelivery.traderName}
							</c:if>
						</c:if>
						<c:if test="${type == 2}">
							<c:if test="${shType == 1}">
							${av.traderName}
							</c:if>
							<c:if test="${shType != 1}">
							${saleorder.takeTraderName}
							</c:if>
						</c:if>
						</td>
						<td width='16%' style='text-align: right;'>
						<c:if test="${type == 1}">
							<c:if test="${btype == 1 }">
							  ${saleorder.takeTraderContactName}
							</c:if>
							<c:if test="${btype == 4 }">
							  ${av.traderContactName}
							</c:if>
							<c:if test="${btype == 3}">
							 <c:if test="${stype==504}">
							 ${av.invoiceTraderContactName}
							 </c:if>
							 <c:if test="${stype==505 }">
							 ${saleorder.invoiceTraderContactName}
							 </c:if>
							</c:if>
							<c:if test="${btype == -1 }">
							${fileDelivery.traderContactName}
							</c:if>
						</c:if>
						<c:if test="${type == 2}">
						    <c:if test="${shType == 1}">
							${av.traderContactName}
							</c:if>
							<c:if test="${shType != 1}">
							${saleorder.takeTraderContactName}
							</c:if>
						</c:if>
						</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td colspan='3' class='address' valign='top'>
						<c:if test="${type == 1}">
						<c:if test="${btype == 1 }">
						  ${saleorder.takeTraderArea}${saleorder.takeTraderAddress}
						</c:if>
						<c:if test="${btype == 4 }">
						  ${av.area}${av.address}
						</c:if>
						<c:if test="${btype == 3}">
						 <c:if test="${stype==504}">
						 ${av.invoiceTraderArea}${av.invoiceTraderAddress}
						 </c:if>
						 <c:if test="${stype==505 }">
						 ${saleorder.invoiceTraderArea}${saleorder.invoiceTraderAddress}
						 </c:if>
						</c:if>
						<c:if test="${btype == -1}">
						 ${fileDelivery.area}${fileDelivery.address}
						</c:if>
						</c:if>
						<c:if test="${type == 2}">
						 <c:if test="${shType == 1}">
						 ${av.area}${av.address}
						 </c:if>
						 <c:if test="${shType != 1}">
						 ${saleorder.takeTraderArea}${saleorder.takeTraderAddress}
						 </c:if>
						</c:if>
						</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td colspan='3'>
							<span style='display: inline-block; width: 75px;' nowrap></span>
							<c:if test="${type == 1}">
							<c:if test="${btype == 1 }">
							 ${saleorder.takeTraderContactMobile} / ${saleorder.takeTraderContactTelephone}
							</c:if>
							<c:if test="${btype == 4 }">
							 ${av.traderContactMobile} / ${av.traderContactTelephone}
							</c:if>
							<c:if test="${ btype == 3}">
							   <c:if test="${stype==504}">
								 ${av.invoiceTraderContactTelephone} / ${av.invoiceTraderContactMobile}
							   </c:if>
							   <c:if test="${stype==505 }">
							     ${saleorder.invoiceTraderContactTelephone} / ${saleorder.invoiceTraderContactMobile}
							   </c:if>
							</c:if>
							<c:if test="${btype == -1}">
							${fileDelivery.mobile} / ${fileDelivery.telephone}
							</c:if>
							</c:if>
							<c:if test="${type == 2}">
							<c:if test="${shType == 1}">
							${av.traderContactMobile}${av.traderContactTelephone}
							</c:if>
							<c:if test="${shType != 1}">
							${saleorder.takeTraderContactMobile} / ${saleorder.takeTraderContactTelephone}
							</c:if>
							</c:if>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td align='left' valign='top' style="padding-bottom: 50px;">
				<!-- 客户信息 -->
			</td>
		</tr>
		<tr>
			<td align='left' valign='top'>
				<table cellpadding="0" cellspacing="0" width='100%' border='0' class='toptbl' style="margin-top: 15px;">
					<tr>
						<td style='text-align: center; height: 30px;' valign='bottom'></td>
					</tr>
					<tr>
						<td rowspan='3'>
							<div style='padding-left: 65px;'>
								<c:if test="${type == 1}">
								<c:if test="${btype == 1 or btype == -1  or btype==4}">
								<c:if test="${companyId==1 }">物流部</c:if>
                                <c:if test="${companyId!=1 }">黄伟</c:if>
								</c:if>
								 <c:if test="${btype == 3}">
                        		 <c:if test="${companyId==1 }"> 财务部</c:if>
                                <c:if test="${companyId!=1 }">陈秋芬</c:if>
                        		</c:if>
                       	       </c:if>
		                       	<c:if test="${type == 2}">
		                       	 ${saleorder.traderContactName}
		                       	</c:if><BR>
								<date:date value ="${currTime}" format="yyyy-MM-dd"/>
							</div>
						</td>
					</tr>
				</table>
			</td>
			<td>
				<table cellpadding="0" cellspacing="0" width='100%' border='0' class='toptbl' style="margin-top: 5px;">
					<tr>
						<td rowspan='5'>
							<div style='margin-top: 0px; margin-left: 120px; font-size: 14px;'>
							<c:if test="${type == 1}">
							<c:if test="${companyId==1 }">
							<c:if test="${btype == 1 or btype == -1 or btype==4}">
							0&nbsp;&nbsp;2&nbsp;&nbsp;5&nbsp;&nbsp;3&nbsp;&nbsp;5&nbsp;&nbsp;2&nbsp;&nbsp;3&nbsp;&nbsp;4&nbsp;&nbsp;6&nbsp;&nbsp;4
							</c:if>
	                       	<c:if test="${type == 3}">
	                       	0&nbsp;&nbsp;2&nbsp;&nbsp;5&nbsp;&nbsp;3&nbsp;&nbsp;5&nbsp;&nbsp;2&nbsp;&nbsp;3&nbsp;&nbsp;4&nbsp;&nbsp;6&nbsp;&nbsp;4
	                       	</c:if>
	                       	</c:if>
	                       	</c:if>
							</div>
						</td>
					</tr>
				</table>

			</td>
		</tr>
	</table>
<%@ include file="../../common/footer.jsp"%>