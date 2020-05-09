<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="打印中通快运快递单" scope="application" />
<%@ include file="../../common/common.jsp"%>
<%@ taglib uri="http://com.vedeng.common.util/tags" prefix="date"%>
<style type="text/css">
* {
	margin: 0;
	padding: 0;
	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
	box-sizing: border-box;
}

span {
	display: inline-block;
	margin-left: 55px;
}

html {
	color: #000;
	height: 100%;
	font-size: 12px;
	font-weight: bold;
	font-family: '宋体';
}

body {
	margin: 0;
	height: 100%;
}

li {
	list-style: none;
}

.tuoyunren, .kuaidi div {
	overflow: hidden;
}

.tuoyunren li {
	float: left;
	overflow: visible;
}

.kuaidi {
	width: 400px;
}

.kuaidi span {
	float: left;
	line-height: 22px;
}

.nowrap {
	white-space: nowrap;
}

.wid4 {
	width: 40px;
}

.wid5 {
	width: 50px;
}

.wid6 {
	width: 60px;
}

.wid10 {
	width: 100px;
}

.wid34 {
	width: 340px;
}

.wid35 {
	width: 350px;
}

.ml50 {
	margin-left: 50px;
}

.pt3 {
	padding-top: 3px;
}

.pt5 {
	padding-top: 5px;
}

.pt6 {
	padding-top: 6px;
}

.pt25 {
	padding-top: 25px;
}

.pt60 {
	padding-top: 60px;
}

.pt15 {
	padding-top: 15px;
}

.ml15 {
	margin-left: 15px;
}

.ml30 {
	margin-left: 30px;
}
</style>
</head>

<body>
	<div class="kuaidi">
		<div class="pt60">
			<div>
				<span class="wid34">
				      <c:if test="${type == 1}">
						<c:if test="${btype == 1 or btype == -1 or btype==4 or btype==3}">
						<c:if test="${companyId==1 }">南京贝登医疗股份有限公司</c:if>
                        <c:if test="${companyId!=1 }">广东贝海医疗供应链管理有限公司</c:if>
						</c:if>
					  </c:if> 
					  <c:if test="${type == 2}">
                      ${saleorder.traderName}                                              
                      </c:if>
				</span>
			</div>
			<div class="tuoyunren">
				<ul>
					<li style="text-align:right;">
				       <span class="wid10">
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
                   	    </span>
                    </li>
					<li><span class="wid10 ml30">
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
                        </c:if></span></li>
				</ul>
			</div>
			<div class="pt6">
				<span class="wid35">
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
	                </c:if></span>
			</div>
			<div class="pt25">
				<span class="wid34"> 
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
				</span>
			</div>
			<div class="tuoyunren">
				<ul>
					<li  style="text-align:right;"><span class="wid10">
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
					</span></li>
					<li><span class="wid10 ml30">
						  <c:if test="${type == 1}">
							  <c:if test="${btype == 1 }">
							  ${saleorder.takeTraderContactMobile} 
							  </c:if>
							  <c:if test="${btype == 4 }">
							  ${av.traderContactMobile} 
							  </c:if>
							  <c:if test="${ btype == 3}">
							   <c:if test="${stype==504}">
								 ${av.invoiceTraderContactMobile}
							   </c:if>
							   <c:if test="${stype==505 }">
							     ${saleorder.invoiceTraderContactMobile}
							   </c:if>
							  </c:if>
							  <c:if test="${btype == -1}">
								${fileDelivery.mobile}
							  </c:if>
						  </c:if>
						  <c:if test="${type == 2}">
							<c:if test="${shType == 1}">
							${av.traderContactMobile}
							</c:if>
							<c:if test="${shType != 1}">
							${saleorder.takeTraderContactMobile}
							</c:if>
							</c:if>
							</span></li>
				</ul>
			</div>
			<div class="pt5">
				<span class="wid35"> 
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
				</span>
			</div>
		</div>
	</div>
	<%@ include file="../../common/footer.jsp"%>