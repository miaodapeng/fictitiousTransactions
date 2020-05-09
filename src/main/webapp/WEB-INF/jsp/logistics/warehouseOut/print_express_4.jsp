<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="打印EMS快递单" scope="application" />
<%@ include file="../../common/common.jsp"%>
<%@ taglib uri="http://com.vedeng.common.util/tags" prefix="date"%>
<link rel="stylesheet" href="<%=basePath%>static/css/print.css?rnd=<%=Math.random()%>" />
<style type="text/css">
body {
	margin: 0px;
	padding: 0px;
	text-align: left;
}

table {
	border-collapse: collapse;
	padding: 0px;
	margin: 0px;
}

td {
	line-height: 30px;
	font-size: 13px;
}

.alltbl {
	margin-top: 50px;
	border: 0px solid #000;
}

.lefttbl {
	width: 100%;
}

.lefttbl td {
	border: 0px solid #f00;
}

.left2tbl {
	width: 100%;
	margin-top: 50px;
}

.left2tbl td {
	border: 0px solid #0f0;
}

.righttbl {
	width: 100%;
}

.righttbl td {
	border: 0px solid #00f;
}
</style>
	<table cellpadding="0" cellspacing="0" border='0' class='alltbl'>
		<tr>
			<td valign='top' align='left' width='310'>
				<!-- 左上 -->
				<table cellpadding="0" cellspacing="0" border='0' class='lefttbl'
					style="margin-left: 23px;">
					<tr>
						<td width='20%'></td>
						<td width='30%' nowrap>
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
						<td colspan='3' nowrap style='text-align: center;'>
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
					<tr>
						<td></td>
						<td colspan='2'>
						<c:if test="${type == 1 }">
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
                       		${saleorder.traderName}
                       	</c:if>
						</td>
						<td colspan='2'>
						<c:if test="${type == 1}">
						<c:if test="${btype == 1 or btype == -1 or btype==4 or btype==3}">
						<c:if test="${companyId==1 }">28055</c:if>
						</c:if>
						</c:if>
                       	<c:if test="${type == 2}">
                       	</c:if>
						</td>
					</tr>
					<tr>
						<td></td>
						<td colspan='2' style='height: 60px;' nowrap>
						<c:if test="${type == 1 }">
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
						<td colspan='2' nowrap
							style='text-align: right; padding-top: 20px;'>
							<c:if test="${type == 1}">
							<c:if test="${btype == 1 or btype == -1 or btype==4 or btype==3}">
							<c:if test="${companyId==1 }">
							210001
							</c:if>
							</c:if>
	                       	</c:if>
	                       	<c:if test="${type == 2}">
	                       	</c:if>
						</td>
					</tr>
				</table>
			</td>
			<td style="padding-top: 97px; padding-left: 244px;">
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
			<td rowspan='4' valign='top' align='left' width='310'>
				<!-- 右侧 -->
				<table cellpadding="0" cellspacing="0" border='0' class='righttbl'
					style="margin-left: -30px;">
					<tr>
						<td width='22%'></td>
						<td width='28%'>
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
						<!-- <td width='15%'></td> -->
						<td colspan='2' style='text-align: center; padding-left: 20px;'
							nowrap>
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
						<td colspan='3' style='height: 60px;' valign='top'>
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
						<td></td>
						<td></td>
						<td></td>
						<td style='text-align: right; padding-right: 30px; padding-bottom: 5px;'>
							&nbsp;
						</td>
					</tr>
				</table>
			</td>
			<td	style="padding-top: 0px; padding-left: 218px; padding-bottom: 79px;">
				<date:date value ="${currTime}" format="yyyy"/>&nbsp;&nbsp;&nbsp;&nbsp;<date:date value ="${currTime}" format="MM"/>&nbsp;&nbsp;&nbsp;&nbsp;<date:date value ="${currTime}" format="dd"/>
			</td>
		</tr>
	</table>
<%@ include file="../../common/footer.jsp"%>