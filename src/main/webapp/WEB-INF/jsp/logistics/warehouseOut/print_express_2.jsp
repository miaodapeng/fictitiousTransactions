<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="打印中通快递单" scope="application" />
<%@ include file="../../common/common.jsp"%>
<%@ taglib uri="http://com.vedeng.common.util/tags" prefix="date"%>
<link rel="stylesheet" href="<%=basePath%>static/css/print.css?rnd=<%=Math.random()%>" />
<style type="text/css">
* {
	font-size: 14px;
}

body {
	margin: 0px;
	padding: 0px;
	text-align: left;
	/*background: url(images/zht.jpg) no-repeat*/
	max-height: 300px;
}

table {
	margin: 0px;
	padding: 0px;
}

.alltbl {
	width: 700px;
	height: 378px;
}

.alltbl td {
	margin: 0px;
	padding: 0px
}

.left {
	width: 100%;
}

.left td {
	height: 34px;
	line-height: 27px;
	text-align: left;
}

.left td.line2 {
	height: 53px;
}

.bottom {
	width: 235px
}

.bottom td {
	height: 10px;
	line-height: 18px;
}
</style>
	<table cellpadding="0" cellspacing="0" border='0' class='alltbl'
		style="margin: 20px 0 0 -40px;padding-left: 10px;width: 820px;">
		<tr>
			<td width='50%' valign='top' align='left' style='height: 130px;'>
				<table cellpadding="0" cellspacing="0" border='0' class='left'
					style="margin-top: 45px; margin-bottom: 0px;">
					<tr>
						<td width='30%'></td>
						<td width='25%'>
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
						<td width='22%'></td>
						<td>
						<c:if test="${type == 1}">
						<c:if test="${btype == 1 or btype == -1 or btype==4 or btype==3}">
						    <c:if test="${companyId==1 }">江苏 南京</c:if>
							<c:if test="${companyId!=1 }">广东 广州</c:if>
						</c:if>
                       	</c:if>
                       	<c:if test="${type == 2}">
                       		${saleorder.traderArea}
                       	</c:if>
						</td>
					</tr>
					<tr>
						<td></td>
						<td colspan='3' class='line2' valign='top'>
						<c:if test="${type == 1}">
						<c:if test="${btype == 1 or btype == -1 or btype==4 or btype==3}">
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
						<td></td>
						<td colspan='3'>
						<c:if test="${type == 1}">
						<c:if test="${btype == 1 or btype == -1 or btype==4 or btype==3}">
						<c:if test="${companyId==1 }">南京贝登医疗股份有限公司</c:if>
                        <c:if test="${companyId!=1 }">广东贝海医疗供应链管理有限公司</c:if>
						</c:if>
                       	</c:if>
                       	<c:if test="${type == 2}">
                       		${saleorder.traderName}
                       	</c:if>
						</td>
					</tr>
					<tr>
						<td></td>
						<td style="vertical-align: top; line-height: 22px" colspan='2'>&nbsp;&nbsp;&nbsp;&nbsp;
						<c:if test="${type == 1}">
						<c:if test="${btype == 1 or btype == -1 or btype == 3  or btype==4}">
							<c:if test="${companyId==1 }">4006-999-569</c:if>
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
						<td style="vertical-align: top; line-height: 22px">
						<c:if test="${type == 1}">
						<c:if test="${btype == 1 or btype == -1 or btype==4 or btype==3}">
						<c:if test="${companyId==1 }">210001</c:if>
						</c:if>
                       	</c:if>
                       	<c:if test="${type == 2}">
                       	</c:if>
						</td>
					</tr>
				</table>
			</td>
			<td valign='top'>
				<table cellpadding="0" cellspacing="0" border='0' class='left'
					style="margin-top: 45px; margin-bottom: 0px;padding-left: 40px;">
					<tr>
						<td width='20%'></td>
						<td width='24%'>
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
						<td width='1%'></td>
						<td width='55%'>
						<c:if test="${type == 1}">
						<c:if test="${btype == 1 }">
						  ${saleorder.takeTraderArea}
						</c:if>
						<c:if test="${btype == 4 }">
						  ${av.area}${av.address}
						</c:if>
						<c:if test="${btype == 3}">
						 <c:if test="${stype==504}">
						 ${av.invoiceTraderArea}
						 </c:if>
						 <c:if test="${stype==505 }">
						 ${saleorder.invoiceTraderArea}
						 </c:if>
						</c:if>
						<c:if test="${btype == -1}">
						 ${fileDelivery.area}
						</c:if>
						</c:if>
						<c:if test="${type == 2}">
						 <c:if test="${shType == 1}">
						 ${av.area}
						 </c:if>
						 <c:if test="${shType != 1}">
						 ${saleorder.takeTraderArea}
						 </c:if>
						</c:if>
						</td>
					</tr>
					<tr>
						<td></td>
						<td colspan='3' class='line2' valign='top'>
						<c:if test="${type == 1}">
						<c:if test="${btype == 1 }">
						  ${saleorder.takeTraderAddress}
						</c:if>
						<c:if test="${btype == 4 }">
						  ${av.area}${av.address}
						</c:if>
						<c:if test="${btype == 3}">
						 <c:if test="${stype==504}">
						 ${av.invoiceTraderAddress}
						 </c:if>
						 <c:if test="${stype==505 }">
						 ${saleorder.invoiceTraderAddress}
						 </c:if>
						</c:if>
						<c:if test="${btype == -1}">
						 ${fileDelivery.address}
						</c:if>
						</c:if>
						<c:if test="${type == 2}">
						 <c:if test="${shType == 1}">
						 ${av.address}
						 </c:if>
						 <c:if test="${shType != 1}">
						 ${saleorder.takeTraderAddress}
						 </c:if>
						</c:if>
						</td>
					</tr>
					<tr>
						<td></td>
						<td colspan='3'>
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
					</tr>
					<tr>
						<td></td>
						<td nowrap style="vertical-align: top; line-height: 22px; padding: 0 0 0 10px">
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
						<td></td>
						<td style="vertical-align: top; line-height: 22px">
							&nbsp;
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan='2' valign='top'>
				<table cellpadding="0" cellspacing="0" border='0' class='bottom' style="padding-top: 150px;">
					<tr>
						<td style="padding: 0 0 0 110px">
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
						<td>
							<date:date value ="${currTime}" format="MM"/>月<date:date value ="${currTime}" format="dd"/>日
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
<%@ include file="../../common/footer.jsp"%>