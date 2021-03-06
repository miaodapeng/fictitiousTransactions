<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="售后录票" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%=basePath%>static/js/finance/invoice/after_invoice_input.js?rnd=<%=Math.random()%>'></script>
<!-- 获取当前日期 -->
<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate value="${now}" type="both" dateStyle="long" var="today" pattern="yyyy-MM-dd"/>
	<div class="main-container">
		<div class='list-pages-search'> 
			<form method="post" id="search" action="<%=basePath%>finance/invoice/afterInvoiceInput.do">
				<ul>
					<li>
						<label class="infor_name">订单号</label> 
						<input type="text" class="input-middle" name="buyorderNo" id="buyorderNo" value="${bo.buyorderNo}" />
					</li>
					<li>
						<label class="infor_name">供应商</label> 
						<input type="text" class="input-middle" name="traderName" id="traderName" value="${bo.traderName}" />
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
				<div class="table-title nobor">填写发票信息</div>
			</div>
			<table class="table" id="invoiceInfo">
				<tbody>
					<tr>
						<td>
							<div class="form-list" style='padding-bottom:0px;'>
								<form method="post" action="./saveBuyorderInvoice.do" id="invoiceForm">
									<input type="hidden" name="formToken" value="${formToken}"/>
									<input type="hidden" name="saveInvoiceType" id="saveInvoiceType" value="${inputInvoiceType}">
									<ul>
										<li>
											<div class="form-tips">
												<span>*</span>
												<lable>发票号</lable>
											</div>
											<div class="f_left">
												<div class="form-blanks">
													<input type="text" class="input-middle" name="invoiceNo" id="invoiceNo" onkeyup="vailInvoiceNo(this);" />
												</div>
											</div>
										</li>
										<li>
											<div class="form-tips">
												
												<span>*</span>
												<lable>票种</lable>
											</div>
											<div class="f_left">
												<div class="form-blanks">
													<select class="input-middle" name="invoiceType" id="invoiceType" onchange="changeInvoiceType(this);">
														<!-- 4月1号后税率只有13% -->
														<c:choose>
															<c:when test="${today >= '2019-04-01'}">
																<c:forEach var="list" items="${invoiceTypeList}" varStatus="status">
																	<c:if test="${list.sysOptionDefinitionId ne 429 and list.sysOptionDefinitionId ne 430}"><!-- 屏蔽17%税率 -->
																		<option value="${list.sysOptionDefinitionId}" id="${list.comments}"
																			<c:if test="${list.sysOptionDefinitionId eq (invoice.invoiceType==null?972:invoice.invoiceType)}">selected</c:if>>${list.title}
																		</option>
																	</c:if>
																</c:forEach>
															</c:when>
															<c:otherwise>
																<c:forEach var="list" items="${invoiceTypeList}" varStatus="status">
																	<option value="${list.sysOptionDefinitionId}" id="${list.comments}"
																		<c:if test="${list.sysOptionDefinitionId eq (invoice.invoiceType==null?682:invoice.invoiceType)}">selected</c:if>>${list.title}
																	</option>
																</c:forEach>
															</c:otherwise>
														</c:choose>
													</select>
												</div>
											</div>
										</li>
										<li>
											<div class="form-tips">
												<span>*</span>
												<lable>红蓝字</lable>
											</div>
											<div class="f_left">
												<div class="form-blanks">
                                                    <ul>
                                                        <li>
                                                            <input type="radio" name="invoiceColor" checked value="2-1" onclick="updateInvoiceColor();">
                                                            <label>蓝字有效</label>
                                                        </li>
                                                        <!-- <li>
                                                            <input type="radio" name="invoiceColor" value="2-0" onclick="updateInvoiceColor();">
                                                            <label>蓝字作废</label>
                                                        </li>
                                                        <li>
                                                            <input type="radio" name="invoiceColor" value="1-1" onclick="updateInvoiceColor();">
                                                            <label>红字有效</label>
                                                        </li> -->
                                                    </ul>
                                                </div>
												<!-- <div class="form-blanks">
													<select class="input-middle" name="invoiceColor" id="invoiceColor" onchange="updateInvoiceColor(this);">
														<option value="2-1" selected="selected"></option>
														<option value="2-0"></option>
														<option value="1-1"></option>
													</select>
												</div> -->
												<input type="hidden" name="colorType" id="colorType" value="2"/>
												<input type="hidden" name="isEnable" id="isEnable" value="1"/>
											</div>
										</li>
										<li style='margin-bottom:4px;'>
											<div class="form-tips">
												<span>*</span>
												<lable>金额</lable>
											</div>
											<div class="f_left">
												<div class="form-blanks">
													<input type="text" class="input-middle" name="amount" id="amount" value="0.00" style="background-color: #dddddd;" readonly="readonly"/>
												</div>
											</div>
										</li>
									</ul>
									<!-- 4月1号后税率只有13% -->
									<c:choose>
										<c:when test="${today >= '2019-04-01'}">
											<c:set var="taxes" value="0.13"></c:set>
										</c:when>
										<c:otherwise>
											<c:set var="taxes" value="0.16"></c:set>
										</c:otherwise>
									</c:choose>
									<c:choose>
										<c:when test="${!empty invoice.invoiceType}">
											<c:forEach var="list" items="${invoiceTypeList}" varStatus="status">
												<c:if test="${list.sysOptionDefinitionId eq invoice.invoiceType}"><!-- 默认专票 -->
													<c:set var="taxes" value="${list.comments}"></c:set>
												</c:if>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<c:forEach var="list" items="${invoiceTypeList}" varStatus="status">
												<!-- 4月1号后税率只有13% -->
												<c:choose>
													<c:when test="${today >= '2019-04-01'}">
														<c:if test="${list.sysOptionDefinitionId eq 972}"><!-- 默认专票 -->
															<c:set var="taxes" value="${list.comments}"></c:set>
														</c:if>
													</c:when>
													<c:otherwise>
														<c:if test="${list.sysOptionDefinitionId eq 682}"><!-- 默认专票 -->
															<c:set var="taxes" value="${list.comments}"></c:set>
														</c:if>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</c:otherwise>
									</c:choose>
									<input type="hidden" name="ratio" id="ratio" value="${taxes}">
									<span id="hideValue" style="display: none;"></span>
								</form>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>

		<div class="parts table-style10-1" id="buyorderInfo">
			<div class="title-container" style='margin-bottom: 10px;'>
				<div class="table-title nobor">产品及订单信息</div>
			</div>
			<c:forEach items="${afterOrderList}" var="list" varStatus="buyNum">
				<table class="table table-style10">
					<thead>
						<tr>
							<th >订单号</th>
							<th >生效时间</th>
							<th>供应商</th>
							<th>票种</th>
							<th>付款时间</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>
								<a class="addtitle" href="javascript:void(0);" tabtitle='{"num":"viewbuyorder${list.afterSalesId}","link":"./finance/after/getFinanceAfterSaleDetail.do?afterSalesId=${list.afterSalesId}","title":"售后单信息"}'>${list.afterSalesNo}</a>
							</td>
							<td><date:date value="${list.validTime}" /></td>
							<td>
								<div class="brand-color1">
									<%-- <c:choose>
										<c:when test="${list.subjectType eq 537}">
											<a class="addtitle" href="javascript:void(0);" tabtitle='{"num":"viewcustomer${list.traderId}", "link":"./trader/customer/baseinfo.do?traderId=${list.traderId}", "title":"客户信息"}'>${list.traderName}</a>
										</c:when>
										<c:otherwise>
											<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewsupplier${list.traderId}","link":"./trader/supplier/baseinfo.do?traderId=${list.traderId}","title":"供应商信息"}'>${list.traderName}</a>
										</c:otherwise>
									</c:choose> --%>
									${list.traderName}
								</div>
							</td>
							<td>
								<c:forEach var="sysList" items="${invoiceTypeList}">
									<c:if test="${list.invoiceType eq sysList.sysOptionDefinitionId}">${sysList.title}</c:if>
								</c:forEach>
							</td>
							<td><date:date value="${list.lastOrderTime}" /></td>
						</tr>
						<tr>
							<td colspan="5" class="table-container">
								<table class="table table-style10">
									<thead>
										<tr>
											<th>产品名称</th>
											<th>费用</th>
											<th>总数</th>
											<!-- <th class="wid8">已入库数量</th> -->
											<th>已录票数量</th>
											<th>已录票总额</th>
											<th class="wid13">录票单价</th>
											<th class="wid11">本次录票数量</th>
											<th class="wid11">本次录票总额</th>
											<th class="wid10">不含税金额</th>
											<th class="wid9">税额</th>
											<th class="wid7">选择</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="goods" items="${list.afterSalesGoodsList}" varStatus="goodsNum">
											<tr>
												<td class="text-left">
													<div class="brand-color1">
														<a class="addtitle" href="javascript:void(0);" tabtitle='{"num":"viewgoods${goods.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${goods.goodsId}","title":"产品信息"}'>${newSkuInfosMap[goods.sku].SHOW_NAME}${goods.goodsName}</a>
													</div>
													<div>${goods.sku}</div>
													<input type="hidden" name="relatedId" id="relatedId${buyNum.index}_${goodsNum.index}" value="${list.afterSalesId}">
													<input type="hidden" name="buyorderGoodsId" id="buyorderGoodsId${buyNum.index}_${goodsNum.index}" value="${goods.afterSalesGoodsId}">
												</td>
												<td>${goods.price}</td>
												<td>${goods.num}</td>
												<!-- <td></td> -->
												<c:set var="inNum" value="0"></c:set><!-- 已录票数量 -->
												<c:set var="totalAmount" value="0"></c:set><!-- 已录票总额 -->
												<c:choose>
													<c:when test="${(!empty invoiceDetailList) and (invoiceDetailList.size()>0)}">
														<c:forEach items="${invoiceDetailList}" var="invoiceDetail">
															<c:if test="${(invoiceDetail.relatedId eq list.afterSalesId) and (invoiceDetail.detailgoodsId eq goods.afterSalesGoodsId)}">
																<c:set var="inNum" value="${invoiceDetail.num}"></c:set>
																<c:set var="totalAmount" value="${invoiceDetail.totalAmount}"></c:set>
																<td>
																<fmt:formatNumber type="number" value="${invoiceDetail.num }"  maxFractionDigits="10"/>
																</td>
																<td>
																<fmt:formatNumber type="number" value="${invoiceDetail.totalAmount}"  maxFractionDigits="9"/>
																</td>
															</c:if>
														</c:forEach>
														<c:if test="${inNum eq 0}">
															<td>--</td>
															<td>--</td>
														</c:if>
													</c:when>
													<c:otherwise>
														<td>--</td>
														<td>--</td>
													</c:otherwise>
												</c:choose>
												<input type="hidden" name="update_num_pice" onclick="updateInvoice(${goods.price},${goods.num},${inNum},${buyNum.index},${goodsNum.index});">
												<input type="hidden" id="inNum${buyNum.index}_${goodsNum.index}" value="${inNum}"/>
												<fmt:parseNumber value="${goods.price}" type="number" var="goods_price" pattern="0.000000000"/>
												<%-- 将已录票数量转化为保留0个小数点 (仅作判断除数是否为0用) --%>
												<fmt:parseNumber value="${inNum }" type="number" var = "innNum" pattern="0.000000000"/>
												<td id="invoice_price${buyNum.index}_${goodsNum.index}">
													<c:choose>
														<c:when test="${list.invoiceType eq 430}">
															<fmt:formatNumber type="number" value="${goods_price}"  maxFractionDigits="9" />
														</c:when>
														<c:otherwise>
															<fmt:parseNumber value="${(goods.price * goods.num)-totalAmount}" type="number" pattern="0.000000000" var="goods_amount" />
															<c:set var="invoicePrice" value="${(goods.num - inNum)}"></c:set>
															<fmt:formatNumber type="number" value="${goods_amount/invoicePrice}" maxFractionDigits="9" />
														</c:otherwise>
													</c:choose>
												</td>
												<td>
													<input type="text" id="invoice_num${buyNum.index}_${goodsNum.index}" name="invoice_num" alt="${buyNum.index}_${goodsNum.index}" value="<fmt:formatNumber type="number" value="${goods.num - inNum}" maxFractionDigits="10" />" onBlur="invoiceNumChange(this,${goods.price},${goods.num - inNum});" ><!--  onkeyup="value=value.replace(/[^\d.]/g,'');" -->
													<input type="hidden" id="max_num${buyNum.index}_${goodsNum.index}" value="${goods.num - inNum}"/>
												</td>
												<td>
													<input type="text" id="invoice_totle_amount${buyNum.index}_${goodsNum.index}" name="invoice_totle_amount" alt="${buyNum.index}_${goodsNum.index}" value="<fmt:formatNumber type="number" value="${(goods_price * goods.num)-totalAmount}" pattern="0.00" maxFractionDigits="2" />" onBlur="invoiceTotleAmountChange(this,<fmt:formatNumber type="number" value="${(goods_price * goods.num)-totalAmount}" pattern="0.00" maxFractionDigits="2" />,${goods.price});">
													<input type="hidden" id="max_price${buyNum.index}_${goodsNum.index}" value="<fmt:formatNumber type="number" value="${(goods_price * goods.num)-totalAmount}" pattern="0.00" maxFractionDigits="2" />"/>
												</td>
												<td id="invoice_no_tax${buyNum.index}_${goodsNum.index}">
													<fmt:formatNumber type="number" value="${goods_amount/(1+taxes)}" pattern="0.000000000" maxFractionDigits="9" />
												</td>
												<td id="invoice_tax${buyNum.index}_${goodsNum.index}">
													<fmt:formatNumber type="number" value="${goods_amount-(goods_amount/(1+taxes))}" pattern="0.000000000" maxFractionDigits="9" />
												</td>
												<td>
													<input type="checkbox" id="${buyNum.index}_${goodsNum.index}" value="${list.invoiceType}" name="selectInvoiceName" class="${list.traderId}" onchange="selectBuyOrder(this,${goods.price},${goods.num - inNum},<fmt:formatNumber type="number" value="${(goods_price * goods.num)-totalAmount}" pattern="0.000000000" maxFractionDigits="9" />,${inNum},'${buyNum.index}_${goodsNum.index}')">
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</td>
						</tr>
					</tbody>
				</table>
			</c:forEach>
			<c:if test="${!empty buyorderList or !empty afterOrderList}">
				<div class="tablelastline">
					本次录票总数：<span class="warning-color1" id="selectNum">0.00</span>，本次录票总额：<span class="warning-color1" id="selectPrice">0.00</span>
				</div>
				<div class="table-friend-tip">
					友情提示 <br />1、本次录票数量<=已到货数量-已录票数量；<br /> 2、做负时，|本次录票数量|<=已录票数量； <br />
									3、由于算法问题，数额有可能产生0.01元的误差，请忽视该误差；<br />4、仅允许输入不为0的数字，可精确到小数点后2位；
				</div>
			</div>
		</c:if>
		<c:if test="${empty buyorderList and empty afterOrderList}">
			<div class='noresult' style='margin-top:-11px;'>
				查询无结果！请尝试使用其他搜索条件。		
			</div>
		</c:if>
		<div class="table-buttons tcenter">
			<button type="button" class="bt-bg-style bg-light-green bt-small" onclick="addInvoice();">提交</button>
		</div>
	</div>
<%@ include file="../../common/footer.jsp"%>
