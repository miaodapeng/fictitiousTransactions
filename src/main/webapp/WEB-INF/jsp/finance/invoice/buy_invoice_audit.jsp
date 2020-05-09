<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="收票审核" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%=basePath%>static/js/finance/invoice/buy_invoice_audit.js?rnd=<%=Math.random()%>'></script>
	<input type="hidden" name="beforeParams" value='${beforeParams}'><!-- 日志 -->
	<div class="main-container">
		<div class="list-pages-search">
			<form method="post" id="searchForm">
				<ul>
					<li>
						<label class="infor_name">发票号</label> 
						<input type="text" class="input-middle" id="invoiceNo" value="${invoice.invoiceNo}" />
						<input type="hidden" name="invoiceNo" id="hideInvoiceNo" value="${invoice.invoiceNo}"/>
					</li>
				</ul>
				<div class="tcenter">
					<span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" id="searchSpan">搜索</span> 
					<span class="bt-small bg-light-blue bt-bg-style mr20" onclick="reset();">重置</span>
				</div>
			</form>
		</div>
		<div class="parts">
			<div class="title-container">
				<div class="table-title nobor">发票信息 </div>
			</div>
			<c:if test="${(!empty invoice) and (!empty invoice.invoiceId)}">
				<table class="table">
	              <thead>
	                  <tr>
	                      <th class="wid14">发票号</th>
	                      <th class="wid16">录票总额</th>
	                      <th class="wid14">不含税总额</th>
	                      <th class="wid14">税额</th>
	                      <th class="">票种</th>
	                      <th class="">
	                      	<c:choose>
								<c:when test="${invoice.afterSubjectType eq 537}">
									客户
								</c:when>
								<c:otherwise>供应商</c:otherwise>
							</c:choose>
	                      </th>
	                      <th class="wid12">红蓝字</th>
	                      <th class="wid18">申请时间</th>
	                      <th class="wid16">录票人员</th>
	                  </tr>
	              </thead>
	              <tbody>
	                  <tr>
	                    <fmt:parseNumber value="${invoice.amount}" var="amount" />
	                    <fmt:parseNumber value="${invoice.ratio}" var="ratio" />
	                    <td>${invoice.invoiceNo}</td>
	                    <td>${invoice.amount}</td>
	                    <td>
	                    	<c:choose>
	                      		<c:when test="${invoice.invoiceType eq 429 or invoice.invoiceType eq 682 or invoice.invoiceType eq 684 or invoice.invoiceType eq 686 or invoice.invoiceType eq 688 or invoice.invoiceType eq 972}"><!-- 专票 -->
	                        	<fmt:formatNumber type="number" value="${amount/(1+ratio)}" pattern="0.00" maxFractionDigits="2" />
	                      		</c:when>
	                      		<c:otherwise>
	                      			<fmt:formatNumber type="number" value="${amount}" pattern="0.00" maxFractionDigits="2" />
	                      		</c:otherwise>
							</c:choose>
						</td>
							<td><fmt:parseNumber value="${invoice.amount}" var="amount" />
								<c:choose>
									<c:when
										test="${invoice.invoiceType eq 429 or invoice.invoiceType eq 682 or invoice.invoiceType eq 684 or invoice.invoiceType eq 686 or invoice.invoiceType eq 688 or invoice.invoiceType eq 972}">
										<!-- 专票 -->
										<fmt:formatNumber type="number"
											value="${amount - amount/(1+ratio)}" pattern="0.00"
											maxFractionDigits="2" />
									</c:when>
									<c:otherwise>
										0.00
									</c:otherwise>
								</c:choose>
							</td>
							<td>
								<c:forEach var="list" items="${invoiceTypeList}">
									<c:if test="${invoice.invoiceType eq list.sysOptionDefinitionId}">${list.title}</c:if>
								</c:forEach>
							</td>
							<td>
								<c:choose>
									<c:when test="${invoice.afterSubjectType eq 537}">
										<a class="addtitle" href="javascript:void(0);"
											tabtitle='{"num":"viewcustomer${invoice.traderId}", "link":"./trader/customer/baseinfo.do?traderId=${invoice.traderId}", "title":"客户信息"}'>${invoice.traderName}</a>
									</c:when>
									<c:otherwise>
										<a class="addtitle" href="javascript:void(0);"
											tabTitle='{"num":"viewsupplier${invoice.traderId}","link":"./trader/supplier/baseinfo.do?traderId=${invoice.traderId}","title":"供应商信息"}'>${invoice.traderName}</a>
									</c:otherwise>
								</c:choose>
							</td>
							<td>
								<c:choose>
									<c:when test="${invoice.colorType eq 1}">
										<c:choose>
											<c:when test="${invoice.isEnable eq 0}">
												<span style="color: red">红字作废</span>
											</c:when>
											<c:otherwise>
												<span style="color: red">红字有效</span>
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${invoice.isEnable eq 0}">
												<span style="color: red">蓝字作废</span>
											</c:when>
											<c:otherwise>
												蓝字有效
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
							</td>
							<td><date:date value="${invoice.addTime}"/></td>
						<td>${invoice.creatorName}</td>
					</tr>
				</tbody>
			</table>
			</c:if>
		</div>
		<c:if test="${empty invoice or empty invoice.invoiceId}">
			<tr>
				<div style="padding:4px 10px;border:1px solid #ddd;margin-bottom:10px;">查询无结果！请尝试使用其他发票号搜索。</div>
			</tr>
		</c:if>
		<c:if test="${!empty invoiceGoodsList}">
			<div class="parts table-style10-1">
				<div class="title-container" style='margin-bottom: 10px;'>
					<div class="table-title nobor">产品及订单信息</div>
				</div>
				<table class="table table-style10">
					<thead>
						<tr>
							<th class="wid4">序号</th>
							<th class="">产品名称</th>
							<th class="">型号</th>
							<th class="wid10">规格参数</th>
							<th class="wid6">单位</th>
							<th class="wid10">录票总数</th>
							<th class="wid15">录票单价</th>
							<th class="wid10">不含税金额</th>
							<th class="wid10">税额</th>
							<th class="wid12">录票总额</th>
							<th class="wid16">已录票数量/到货数量</th>
							<th class="wid10">产品注册证</th>
							<th class="wid16">采购订单号</th>
							<th class="wid14">订货号</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${invoiceGoodsList}" var="goodsList" varStatus="goodsNum">
							<tr>
								<!-- 总额 -->
								<fmt:parseNumber value="${goodsList.totalAmount}" type="number" var="totalAmount" />
								<!-- 税率 -->
								<fmt:parseNumber value="${invoice.ratio}" type="number" var="ratio" />
								<td>${goodsNum.index + 1}</td>
								<td class="text-left" >

								
									  <c:choose>
										<c:when test="${not empty goodsList.goodsId and goodsList.goodsId > 0}">
                                            <div class="brand-color1">
                                                <a class="addtitle" href="javascript:void(0);"
                                                   tabtitle='{"num":"viewgoods${goodsList.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${goodsList.goodsId}","title":"产品信息"}'>${newSkuInfosMap[goodsList.sku].SHOW_NAME}</a>
                                            </div>
										</c:when>
										<c:otherwise>
                                            ${goodsList.goodsName}
										</c:otherwise>
									</c:choose>
								</td>
								<td>${newSkuInfosMap[goodsList.sku].MODEL}</td>
								<td>${newSkuInfosMap[goodsList.sku].SPEC}</td>
								<td>${newSkuInfosMap[goodsList.sku].UNIT_NAME}</td>
								<td><fmt:formatNumber type="number" value="${goodsList.num}" pattern="0.00" maxFractionDigits="2" /></td>
								<td><fmt:formatNumber type="number" value="${goodsList.price}" pattern="0.00000000" maxFractionDigits="8" /></td>
								<td>
									<c:choose>
										<c:when test="${invoice.invoiceType eq 429 or invoice.invoiceType eq 682 or invoice.invoiceType eq 684 or invoice.invoiceType eq 686 or invoice.invoiceType eq 688 or invoice.invoiceType eq 972}">
											<!-- 专票 -->
											<fmt:formatNumber type="number" value="${totalAmount/(1+ratio)}" pattern="0.00" maxFractionDigits="2" />
										</c:when>
										<c:otherwise>
											<fmt:formatNumber type="number" value="${goodsList.totalAmount}" pattern="0.00" maxFractionDigits="2" />
										</c:otherwise>
									</c:choose>
								</td>
								<td>
									<c:choose>
										<c:when test="${invoice.invoiceType eq 429 or invoice.invoiceType eq 682 or invoice.invoiceType eq 684 or invoice.invoiceType eq 686 or invoice.invoiceType eq 688 or invoice.invoiceType eq 972}">
											<!-- 专票 -->
											<fmt:formatNumber type="number" value="${totalAmount - totalAmount/(1+ratio)}" pattern="0.00" maxFractionDigits="2" />
										</c:when>
										<c:otherwise>
	                        				0.00
	                        			</c:otherwise>
									</c:choose>
								</td>
								<td><fmt:formatNumber type="number" value="${goodsList.totalAmount}" pattern="0.00" maxFractionDigits="2" /></td>
								<td>${goodsList.enterNum}/${goodsList.arrivalNum}</td>
								<td>
									<c:forEach items="${attachmentList}" var="attachment" varStatus="atta">
										<c:if test="${goodsList.goodsId eq attachment.goodsId}">
											<a href="http://${attachment.domain}${attachment.uri}" target="_blank">查看</a>
											<c:if test="${(not empty attachment.alt) and attachment.alt > 1}">
												<font color="red">[多]</font>
											</c:if>
										</c:if>
									</c:forEach>
								</td>
								<td>
									<c:choose>
										<c:when test="${invoice.type eq 504}">
											<a class="addtitle" href="javascript:void(0);" tabtitle='{"num":"viewaftersales${goodsList.orderId}","link":"./finance/after/getFinanceAfterSaleDetail.do?afterSalesId=${goodsList.orderId}","title":"售后详情"}'>${goodsList.orderNo}</a>
										</c:when>
										<c:otherwise>
											<a class="addtitle" href="javascript:void(0);" tabtitle='{"num":"viewbuyorder${goodsList.orderId}","link":"./order/buyorder/viewBuyorder.do?buyorderId=${goodsList.orderId}","title":"订单信息"}'>${goodsList.orderNo}</a>
										</c:otherwise>
									</c:choose>
								</td>
								<td>${goodsList.sku}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="table-buttons mt10">
				<input type="hidden" name="formToken" value="${formToken}"/>
				<%-- <c:if test="${(not empty invoiceGoodsList and invoiceGoodsList.size() > 0) and (not empty invoiceGoodsList  and invoiceGoodsList.size() > 0)}"> --%>
				<c:if test="${positType eq 314}"><!-- 职位类型：314财务 -->
					<button type="button" id="passBtn" class="bt-bg-style bg-light-green bt-small" onclick="auditInvoice('${invoice.invoiceId}','${invoice.invoiceNo}',1,${invoice.colorType},${invoice.isEnable},${invoice.type});">审核通过</button>
				</c:if>
				<%-- <button type="button" id="unPassBtn" class="bt-bg-style bt-small bg-light-orange" onclick="auditInvoice('${invoice.invoiceId}','${invoice.invoiceNo}',2,${invoice.colorType},${invoice.isEnable},${invoice.type});">审核不通过</button> --%>
				<span class="bg-light-orange bt-small bt-bg-style pop-new-data" layerparams='{"width":"550px","height":"210px","title":"审核不通过",
					"link":"./auditInvoiceReason.do?invoiceId=${invoice.invoiceId}&invoiceNo=${invoice.invoiceNo}&validStatus=2&colorType=${invoice.colorType}&isEnable=${invoice.isEnable}&type=${invoice.type}"}'>审核不通过</span>
				<%-- </c:if> --%>
			</div>
		</div>
	</c:if>
<%@ include file="../../common/footer.jsp"%>
