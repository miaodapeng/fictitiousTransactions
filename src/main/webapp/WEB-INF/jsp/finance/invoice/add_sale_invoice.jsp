<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增发票" scope="application" />
<%@ include file="../../common/common.jsp"%>
 <script type="text/javascript" src='<%=basePath%>static/js/finance/invoice/add_sale_invoice.js?rnd=<%=Math.random()%>'></script>

	<div class="main-container">
		<div>
			<table class="table" id="invoiceInfo">
				<tbody>
					<tr>
						<td>
							<div class="form-list" style="padding-bottom:0; margin-bottom:-7px;">
								<form method="post" action="./saveSaleorderInvoice.do" id="invoiceForm">
									<input type="hidden" name="formToken" value="${formToken}"/>
									<!--  add by Tomcat.Hui 2019/11/22 13:31 .Desc: VDERP-1325 分批开票 保存开票申请ID 发票代码 start -->
									<input type="hidden" name="invoiceApplyId" value="${invoiceApply.invoiceApplyId}"/>
									<!--  add by Tomcat.Hui 2019/11/22 13:31 .Desc: VDERP-1325 分批开票 保存开票申请ID end -->
									<input type="hidden" name="saleorderId" id="saleorderId" value="${invoice.relatedId}"/>
									<ul>
										<li>
											<div class="form-tips">
												<span>*</span>
												<lable>发票号</lable>
											</div>
											<div class="f_left">
												<div class="form-blanks">
													<input type="text" class="input-middle" name="invoiceNo" id="invoiceNo" onkeyup="vailInvoiceNo(this);"/>
												</div>
											</div>
										</li>
										<!--  add by Tomcat.Hui 2019/11/22 13:31 .Desc: VDERP-1325 分批开票 发票代码 start -->
										<li>
											<div class="form-tips">
												<span>*</span>
												<lable>发票代码</lable>
											</div>
											<div class="f_left">
												<div class="form-blanks">
													<input type="text" class="input-middle" name="invoiceCode" id="invoiceCode" onkeyup="vailInvoiceCode(this);"/>
												</div>
											</div>
										</li>
										<!--  add by Tomcat.Hui 2019/11/22 13:31 .Desc: VDERP-1325 分批开票 发票代码 end -->
										<li>
											<div class="form-tips">
												<span>*</span>
												<lable>票种</lable>
											</div>
											<div class="f_left">
												<div class="form-blanks">
													<c:forEach var="list" items="${invoiceTypeList}" varStatus="status">
														<input type="hidden" value="${list.comments}" id="ratio"/>
														<input type="hidden" value="${invoice.invoiceType}" id="invoiceType" name="invoiceType"/>
														<c:if test="${list.sysOptionDefinitionId eq (invoice.invoiceType==null?972:invoice.invoiceType)}">${list.title}</c:if>
													</c:forEach>
													<%-- <select class="input-middle" name="invoiceType" id="invoiceType" onchange="changeInvoiceType(this);">
														<c:forEach var="list" items="${invoiceTypeList}" varStatus="status">
															<option value="${list.sysOptionDefinitionId}" id="${list.comments}"
																<c:if test="${list.sysOptionDefinitionId eq (invoice.invoiceType==null?429:invoice.invoiceType)}">selected</c:if>>${list.title}</option>
														</c:forEach>
													</select> --%>
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
                                                            <input type="radio" name="invoiceColor" checked value="2-1" >
                                                            <label>蓝字有效</label>
                                                        </li>
                                                        <!-- <li>
                                                            <input type="radio" name="invoiceColor" value="2-0" >
                                                            <label>蓝字作废</label>
                                                        </li>
                                                        <li>
                                                            <input type="radio" name="invoiceColor" value="1-1">
                                                            <label>红字有效</label>
                                                        </li> -->
                                                    </ul>
                                                </div>
												<input type="hidden" name="colorType" id="colorType" value="2"/>
												<input type="hidden" name="isEnable" id="isEnable" value="1"/>
											</div>
										</li>
										<li>
											<div class="form-tips">
												<span>*</span>
												<lable>发票金额</lable>
											</div>
											<div class="f_left">
												<div class="form-blanks">
													<input type="text" class="input-middle" name="amount" id="amount" value="0.00" style="background-color: #c5c2c2;" readonly="readonly"/>
												</div>
											</div>
										</li>
									</ul>
									<c:set var="taxes" value="0.13"></c:set>
									<c:choose>
										<c:when test="${!empty invoice.invoiceType}">
											<c:forEach var="list" items="${invoiceTypeList}" varStatus="status">
												<c:if test="${list.sysOptionDefinitionId eq invoice.invoiceType}">
													<c:set var="taxes" value="${list.comments}"></c:set>
												</c:if>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<c:forEach var="list" items="${invoiceTypeList}" varStatus="status">
												<c:if test="${list.sysOptionDefinitionId eq 972}"><!-- 默认13%专票 -->
													<c:set var="taxes" value="${list.comments}"></c:set>
												</c:if>
											</c:forEach>
										</c:otherwise>
									</c:choose>
									<input type="hidden" name="ratio" id="ratio" value="${taxes}">
									<span id="hideValue" class="none"></span>
								</form>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>

		<div class=" table-style10-1" id="saleorderInfo">
			<table class="table ">
				<thead>
					<tr>
						<th class="wid5">选择</th>
						<th>产品名称</th>
						<th>品牌</th>
						<th>型号</th>
						<th class="wid10">单价</th>
						<th class="wid10">数量</th>
						<th>单位</th>
						<th class="wid10">总额</th>
						<th class="wid12">开票单价</th>
						<th class="wid18">开票数量/已开票数量</th>
						<th class="wid18">开票金额/已开票金额</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="list" items="${invoiceDetailList}" varStatus="num">
						<tr>
							<fmt:parseNumber value="${list.price}" type="number" var="list_price" /><!-- 总额 -->
							<td>
								<input type="checkbox" name="checkName" value="${list.detailgoodsId}" id="${num.index}" onclick="selectObj(this,${num.index});">
								<input type="hidden" name="relatedId" id="relatedId${num.index}" value="${list.relatedId}">
							</td>
							<td>
								<c:if test="${list.isActionGoods==1}"><span style="color:red;">【活动】</span></c:if>
								<a class="addtitle" href="javascript:void(0);" tabtitle='{"num":"viewgoods${list.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${list.goodsId}","title":"产品信息"}'>${newSkuInfosMap[list.sku].SHOW_NAME}</a>
								<br/>
								${list.sku}
							</td>
							<td>
								<c:if test="${list.isActionGoods eq 1}"><span style="color:red;">【活动】</span></c:if>
								${newSkuInfosMap[list.sku].BRAND_NAME}
							</td>
							<td>${newSkuInfosMap[list.sku].MODEL}</td>
							<td><fmt:formatNumber type="number" value="${list_price}" pattern="0.00" maxFractionDigits="2" /></td>
							<td><fmt:formatNumber type="number" value="${list.applyNum}" pattern="0.00" maxFractionDigits="2" /></td>
							<td>${newSkuInfosMap[list.sku].UNIT_NAME}</td>
							<td>${list.totalAmount}</td>
							<td id="invoice_price_${num.index}">
								<fmt:formatNumber type="number" value="${list_price/(1+taxes)}" pattern="0.00000000" maxFractionDigits="8" />
							</td>
							<c:set var="countNum" value="0"></c:set>
							<input type="hidden" name="update_num_pice" onclick="updateInvoice(${list.price},${num.index});">
							<td>
								<!-- 最大录取数量 -->
								<input type="hidden" id="max_num_${num.index}" value="${list.applyNum - list.enterNum}"/>
								<input type="text" style="width:70px;" name="invoiceNum" id="invoice_num_${num.index}" alt="${num.index}" onBlur="invoiceNumChange(this,${list.price},${list.applyNum},${list.enterNum==null?0:list.enterNum});" value='<fmt:formatNumber type="number" value="${list.applyNum - list.enterNum}" pattern="0.00" maxFractionDigits="2" />'/><!-- onkeyup="value=value.replace(/[^\d.]/g,'');" -->
								/
								<fmt:formatNumber type="number" value="${list.enterNum}" pattern="0.00" maxFractionDigits="2" />
								<!-- 已录数量 -->
								<input type="hidden" id="in_num_${num.index}" value="${list.enterNum}"/>
							</td>
							<td>
								<!-- 最大录取金额 -->
								<input type="hidden" id="max_price_${num.index}" value="${list.totalAmount - list.enterAmount}"/>
								<input type="text" style="width:90px;" name="invoiceTotalAmount" id="invoice_totalAmount_${num.index}" alt="${num.index}" onBlur="invoiceTotleAmountChange(this,${(list.applyNum - list.enterNum)*list_price},${list.price});" value='<fmt:formatNumber type="number" value="${list.totalAmount - list.enterAmount}" pattern="0.00" maxFractionDigits="2" />'/><!-- onkeyup="value=value.replace(/[^\d.]/g,'');" -->
								/
								<fmt:formatNumber type="number" value="${list.enterAmount}" pattern="0.00" maxFractionDigits="2" />
								<!-- 已录金额 -->
								<input type="hidden" id="in_price_${num.index}" value="${list.enterAmount}"/>
							</td>
						</tr>
					</c:forEach>
					<c:if test="${empty invoiceDetailList}">
						<tr>
							<td colspan="11">
								<!-- 查询无结果弹出 --> 查询无结果！请尝试使用其他搜索条件。
							</td>
						</tr>
					</c:if>
				</tbody>
			</table>
			<div class="allchose mt-5">
				<input type="checkbox" name="checkAllOpt" onclick="checkAllOpt(this);"><span>全选</span>
			</div>
			<div class="add-tijiao tcenter" style="margin:10px 0 15px 0">
				<button type="button" class="bt-bg-style bg-light-green bt-small" onclick="addSaleInvoice();" id="button_id">提交</button>
				<button class="dele" type="button" id="close-layer">取消</button>
			</div>
		</div>
	</div>
<%@ include file="../../common/footer.jsp"%>
