<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/tld/myfn.tld" prefix="myfn" %>
<c:set var="title" value="收票记录列表" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%=basePath%>static/js/finance/invoice/list_buy_invoice.js?rnd=<%=Math.random()%>'></script>
	<div class="main-container">
		<div class="list-pages-search">
			<form method="post" id="search" action="<%=basePath%>finance/invoice/getInvoiceListPage.do">
				<ul>
					<li>
						<label class="infor_name">发票号</label>
						<input type="text" class="input-middle" name="invoiceNo" id="invoiceNo" value="${invoice.invoiceNo}" />
					</li>
					<li>
						<label class="infor_name">订单号</label>
						<input type="text" class="input-middle" name="buyorderNo" id="buyorderNo" value="${invoice.buyorderNo}" />
					</li>
					<li>
						<label class="infor_name">供应商</label>
						<input type="text" class="input-middle" name="traderName" id="traderName" value="${invoice.traderName}" />
					</li>
					<li>
						<label class="infor_name">金蝶凭证号</label>
						<input type="text" class="input-middle" name="financeVoucherNo" id="financeVoucherNo" value="${invoice.financeVoucherNo}" />
					</li>
					
					<li>
						<label class="infor_name">发送结果</label> 
						<select class="input-middle f_left" name="sendResult">
							<option value="-1">全部</option>
							<option value="1" <c:if test="${invoice.sendResult == 1}">selected="selected"</c:if>>是</option>
							<option value="2" <c:if test="${invoice.sendResult == 2}">selected="selected"</c:if>>否</option> 
						</select>
					</li>
					
					<li>
						<label class="infor_name">红蓝字</label>
						<input type="hidden" name="colorType" id="colorType"/>
						<input type="hidden" name="isEnable" id="isEnable"/>
						<select class="input-middle" id="colorTypeEnable">
							<option value="">全部</option>
							<c:choose>
								<c:when test="${invoice.colorType eq 1}">
									<c:if test="${invoice.isEnable eq 1}">
										<option value="2-1">蓝字有效</option>
										<option selected value="1-1">红字有效</option>
										<option value="2-0">蓝字作废</option>
									</c:if>
								</c:when>
								<c:when test="${invoice.colorType eq 2}">
									<c:if test="${invoice.isEnable eq 0}">
										<option value="2-1">蓝字有效</option>
										<option value="1-1">红字有效</option>
										<option selected value="2-0">蓝字作废</option>
									</c:if>
									<c:if test="${invoice.isEnable eq 1}">
										<option selected value="2-1">蓝字有效</option>
										<option value="1-1">红字有效</option>
										<option value="2-0">蓝字作废</option>
									</c:if>
								</c:when>
								<c:otherwise>
									<option value="2-1">蓝字有效</option>
									<option value="1-1">红字有效</option>
									<option value="2-0">蓝字作废</option>
								</c:otherwise>
							</c:choose>
						</select>
					</li>
					<li>
						<label class="infor_name">票种</label>
						<select class="input-middle" name="invoiceType" id="invoiceType">
							<option value="">全部</option>
							<c:forEach var="list" items="${invoiceTypeList}" varStatus="status">
								<option value="${list.sysOptionDefinitionId}" <c:if test="${list.sysOptionDefinitionId eq invoice.invoiceType}">selected</c:if>>${list.title}</option>
							</c:forEach>
						</select>
					</li>
					<li>
						<label class="infor_name">发票总额</label>
						<input type="text" class="wid81" name="startAmount" id="startAmount" value="${invoice.startAmount}" />
						<span style="margin: 0 3px 0 -2px">-</span>
						<input type="text" class="wid81" name="endAmount" id="endAmount" value="${invoice.endAmount}" />
					</li>
					<li>
						<label class="infor_name">审核状态</label>
						<select class="input-middle" name="validStatus" id="validStatus">
							<option value="0">全部</option>
							<option <c:if test="${invoice.validStatus eq 1}">selected</c:if> value="1">审核通过</option>
							<option <c:if test="${invoice.validStatus eq 2}">selected</c:if> value="2">审核不通过</option>
						</select>
					</li>
					<li>
						<input type="hidden" name="searchDateType" value="second"/><!-- 标记是否新打开查询页 -->
						<div class="infor_name specialinfor" >
							<select name="dateType" id="dateType">
								<option value="1" <c:if test="${invoice.dateType eq 1}">selected</c:if>>申请日期</option>
								<option value="2" <c:if test="${invoice.dateType eq 2}">selected</c:if>>处理日期</option>
							</select>
						</div>
						<input type="hidden" id="de_startTime" value="${searchDateType==null?startTime:de_startTime}"/>
						<input class="Wdate f_left input-smaller96 m0" type="text" placeholder="请选择日期" onClick="WdatePicker()" name="startTime" id="startTime" value="${startTime}">
						<div class="f_left ml1 mr1 mt4">-</div> 
						<input type="hidden" id="de_endTime" value="${searchDateType==null?endTime:de_endTime}"/>
						<input class="Wdate f_left input-smaller96" type="text" placeholder="请选择日期" onClick="WdatePicker()" name="endTime" id="endTime" value="${endTime}">
					</li>
					<li>
						<label class="infor_name">认证状态</label>
						<select class="input-middle" name="isAuth" id="isAuth">
							<option value="-1">全部</option>
							<option value="0" <c:if test="${invoice.isAuth eq 0}">selected</c:if>>未认证</option>
							<option value="1" <c:if test="${invoice.isAuth eq 1}">selected</c:if>>已认证</option>
						</select>
					</li>
					<li>
						<label class="infor_name">当月认证</label>
						<select class="input-middle" name="isMonthAuth" id="isMonthAuth">
							<option value="0">全部</option>
							<option value="1" <c:if test="${invoice.isMonthAuth eq 1}">selected</c:if>>当月认证</option>
							<option value="2" <c:if test="${invoice.isMonthAuth eq 2}">selected</c:if>>非当月认证</option>
						</select>
					</li>
				</ul>
				<div class="tcenter">
					<span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" id="searchSpan">搜索</span>
					<span class="bt-small bg-light-blue bt-bg-style mr20" onclick="resetPage();">重置</span>
					<span class="bt-small bg-light-blue bt-bg-style" onclick="exportIncomeInvoiceList()">导出列表</span>
					<span class="bt-small bg-light-blue bt-bg-style" onclick="exportIncomeInvoiceDetailList()">导出详细</span>
					<span class="bt-small bg-light-blue bt-bg-style" onclick="sendIncomeInvoiceList(this)">发送至金蝶</span>
					<span class="bg-light-blue bt-bg-style bt-small pop-new-data" 
								layerParams='{"width":"450px","height":"250px","title":"批量认证","link":"<%=basePath%>finance/invoice/toBatchAuthenticationPage.do"}'>批量认证</span>
				</div>
			</form>
		</div>
		<div class="list-page">
			 <div class="fixdiv">
				<div class="superdiv"> 
					<table class="table">
						<thead>
							<tr>
								<th class="wid3">序号</th>
								<th class="wid7">发票号</th>
								<th class="wid8">发票金额</th>
								<th class="wid8">不含税金额</th>
								<th class="wid6">税额</th>
								<th class="wid11">票种</th>
								<th class="wid6">红蓝字</th>
								<th class="wid7">录票人员</th>
								<th class="wid10">关联订单号</th>
								<th class="wid16">
									<c:choose>
										<c:when test="${list.afterSubjectType eq 537}">
											客户
										</c:when>
										<c:otherwise>
											供应商
										</c:otherwise>
									</c:choose>
								</th>
								<th class="wid11">申请日期</th>
								<th class="wid11">审核日期</th>
								<th class="wid7">审核人</th>
								<th class="wid8">审核状态</th>
								<th class="wid14">审核备注</th>
								<th class="wid10">金蝶凭证号</th>
                        		<th class="wid6">发送结果</th>
								<th class="wid6">认证状态</th>
								<th class="wid6">当月认证</th>
								<th class="wid10">认证时间</th>
							</tr>
						</thead>
						<tbody>
							<c:set var="lzyxAmount" value="0"></c:set><!-- 蓝字有效总额 -->
                            <c:set var="lzyxTaxAmount" value="0"></c:set><!-- 蓝字有效税额 -->
                            <c:set var="lzyxTaxFreeAmount" value="0"></c:set><!-- 蓝字有效不含税额 -->

							<c:set var="lzzfAmount" value="0"></c:set><!-- 蓝字作废总额 -->
                            <c:set var="lzzfTaxAmount" value="0"></c:set><!-- 蓝字作废税额 -->
                            <c:set var="lzzfTaxFreeAmount" value="0"></c:set><!-- 蓝字作废不含税额 -->

							<c:set var="hzyxAmount" value="0"></c:set><!-- 红字有效总额 -->
                            <c:set var="hzyxTaxAmount" value="0"></c:set><!-- 红字有效税额 -->
                            <c:set var="hzyxTaxFreeAmount" value="0"></c:set><!-- 红字有效不含税额 -->


							<c:set var="pageNum" value="0"></c:set><!-- 当前页记录数 -->
							<c:forEach var="list" items="${invoiceList}" varStatus="num">
								<c:set var="pageNum" value="${pageNum + 1}"></c:set>
								<tr>
									<fmt:parseNumber value="${list.amount}" type="number" var="list_amount" /><!-- 总额 -->
									<fmt:parseNumber value="${list.ratio}" type="number" var="list_ratio" /><!-- 税率 -->
									<td>${num.count}</td>
									<td>${list.invoiceNo}</td>
									<td>
										<fmt:formatNumber type="number" value="${list.amount}" pattern="0.00" maxFractionDigits="2" />
									</td>
									<td>
										<c:choose>
											<c:when test="${list.invoiceType eq 429 or list.invoiceType eq 682 or list.invoiceType eq 684 or list.invoiceType eq 686 or list.invoiceType eq 688 or list.invoiceType eq 972}"><!-- 专票 -->
												<fmt:formatNumber type="number" value="${list_amount/(1+list_ratio)}" pattern="0.00" maxFractionDigits="2" />
                                                <c:set var="taxFreeAmount" value="${myfn:rounding(list_amount/(1+list_ratio))}" />
                                                <!--根据发票类型计算不含税额-->
                                                <c:choose>
                                                    <c:when test="${list.colorType eq 1 and list.isEnable eq 1}"><!--红字有效-->
                                                        <c:set var="hzyxTaxFreeAmount" value="${hzyxTaxFreeAmount + taxFreeAmount}"></c:set>
                                                    </c:when>
                                                    <c:when test="${list.colorType eq 2 and list.isEnable eq 0}"><!--蓝字作废-->
                                                        <c:set var="lzzfTaxFreeAmount" value="${lzzfTaxFreeAmount + taxFreeAmount}"></c:set>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:set var="lzyxTaxFreeAmount" value="${lzyxTaxFreeAmount + taxFreeAmount}"></c:set>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:when>
											<c:otherwise>
												<fmt:formatNumber type="number" value="${list.amount}" pattern="0.00" maxFractionDigits="2" />
                                                <!--根据发票类型计算不含税额-->
                                                <c:choose>
                                                    <c:when test="${list.colorType eq 1 and list.isEnable eq 1}"><!--红字有效-->
                                                        <c:set var="hzyxTaxFreeAmount" value="${hzyxTaxFreeAmount + list.amount}"></c:set>
                                                    </c:when>
                                                    <c:when test="${list.colorType eq 2 and list.isEnable eq 0}"><!--蓝字作废-->
                                                        <c:set var="lzzfTaxFreeAmount" value="${lzzfTaxFreeAmount + list.amount}"></c:set>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:set var="lzyxTaxFreeAmount" value="${lzyxTaxFreeAmount + list.amount}"></c:set>
                                                    </c:otherwise>
                                                </c:choose>
											</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when test="${list.invoiceType eq 429 or list.invoiceType eq 682 or list.invoiceType eq 684 or list.invoiceType eq 686 or list.invoiceType eq 688 or list.invoiceType eq 972}"><!-- 专票 -->
												<fmt:formatNumber type="number" value="${list_amount - list_amount/(1+list_ratio)}" pattern="0.00" maxFractionDigits="2" />
                                                <c:set var="taxAmount" value="${myfn:rounding(list_amount - list_amount/(1+list_ratio))}" />
                                                <!--根据发票类型计算税额-->
                                                <c:choose>
                                                    <c:when test="${list.colorType eq 1 and list.isEnable eq 1}"><!--红字有效-->
                                                        <c:set var="hzyxTaxAmount" value="${hzyxTaxAmount + taxAmount}"></c:set>
                                                    </c:when>
                                                    <c:when test="${list.colorType eq 2 and list.isEnable eq 0}"><!--蓝字作废-->
                                                        <c:set var="lzzfTaxAmount" value="${lzzfTaxAmount + taxAmount}"></c:set>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:set var="lzyxTaxAmount" value="${lzyxTaxAmount + taxAmount}"></c:set>
                                                    </c:otherwise>
                                                </c:choose>
											</c:when>
											<c:otherwise>
												0.00
											</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:forEach var="invoiceList" items="${invoiceTypeList}">
											<c:if test="${list.invoiceType eq invoiceList.sysOptionDefinitionId}">${invoiceList.title}</c:if>
										</c:forEach>
									</td>
									<td>
                                        <c:choose>
                                            <c:when test="${list.colorType eq 1 and list.isEnable eq 1}"><!--红字有效-->
                                                <span style="color: red">红字有效</span>
                                                <c:set var="hzyxAmount" value="${hzyxAmount + list.amount}"></c:set>
                                            </c:when>
                                            <c:when test="${list.colorType eq 2 and list.isEnable eq 0}"><!--蓝字作废-->
                                                <span style="color: red">蓝字作废</span>
                                                <c:set var="lzzfAmount" value="${lzzfAmount + list.amount}"></c:set>
                                            </c:when>
                                            <c:otherwise>
                                                蓝字有效
                                                <c:set var="lzyxAmount" value="${lzyxAmount + list.amount}"></c:set>
                                            </c:otherwise>
                                        </c:choose>
									</td>
									<td>
										<c:forEach varStatus="userNum" var="userList" items="${inputUserList}">
											<c:if test="${list.creator eq userList.userId}">
												${userList.username}
											</c:if>
										</c:forEach>
									</td>
									<td>
										<c:choose>
											<c:when test="${list.type eq 504}">
												<span class="font-blue addtitle" tabtitle='{"num":"view_invoice_after${list.relatedId}",
													"link":"./finance/after/getFinanceAfterSaleDetail.do?afterSalesId=${list.relatedId}","title":"财务售后订单"}'>
													${list.buyorderNo}</span>
											</c:when>
											<c:otherwise>
												<a class="addtitle" href="javascript:void(0);" tabtitle='{"num":"viewinvoicesaleorder${list.relatedId}","link":"./finance/buyorder/viewBuyorder.do?buyorderId=${list.relatedId}","title":"订单信息"}'>
													${list.buyorderNo}
												</a>
											</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<%-- <c:when test="${list.afterSubjectType eq 537}"> --%>
											<c:when test="${list.type eq 504}">
												<%-- <a class="addtitle" href="javascript:void(0);" tabtitle='{"num":"viewcustomer${list.traderId}", "link":"./trader/customer/baseinfo.do?traderId=${list.traderId}", "title":"客户信息"}'> --%>
													${list.traderName}
												<!-- </a> -->
											</c:when>
											<c:otherwise>
												<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewsupplier${list.traderId}",
													"link":"./trader/supplier/baseinfo.do?traderId=${list.traderId}","title":"供应商信息"}'>
													${list.traderName}
												</a>
											</c:otherwise>
										</c:choose>
									</td>
									<td><date:date value="${list.addTime}" /></td>
									<td><date:date value="${list.validTime}" /></td>
									<td>
										<c:forEach varStatus="userNum" var="userList" items="${auditUserList}">
											<c:if test="${list.validUserId eq userList.userId}">
												${userList.username}
											</c:if>
										</c:forEach>
									</td>
									<td>
										<c:choose>
											<c:when test="${list.validStatus eq 0}">待审核</c:when>
											<c:when test="${list.validStatus eq 1}">审核通过</c:when>
											<c:otherwise>审核不通过</c:otherwise>
										</c:choose>
									</td>
									<td>${list.validComments}</td>
									<td>${list.financeVoucherNo}</td>
			                        <c:choose>
			                        	<c:when test="${list.financeVoucherNoId != null and list.financeVoucherNoId != ''}">
			                        		<td>是</td>
			                        	</c:when>
			                        	<c:otherwise>
			                        		<td>否</td>
			                        	</c:otherwise>
			                        </c:choose>
									<td>
										<c:if test="${list.isAuth eq 0}">未认证</c:if>
										<c:if test="${list.isAuth eq 1}">已认证</c:if>
									</td>
									<td>
										<c:if test="${list.isMonthAuth eq 1}">当月认证</c:if>
										<c:if test="${list.isMonthAuth eq 2}">非当月认证</c:if>
									</td>
									<td><date:date value="${list.authTime}" /></td>
								</tr>
							</c:forEach>
							<c:if test="${empty invoiceList}">
								<tr>
									<td colspan="20">
										<!-- 查询无结果弹出 --> 查询无结果！请尝试使用其他搜索条件。
									</td>
								</tr>
							</c:if>
						</tbody>
					</table>
				<!-- <div class="tablelastline">
					
				</div> -->
				</div>
			</div>
            <div>
                <tags:page page="${page}" />
                <div class="total">
                    <div class="clear"></div>
                    <div class="fixtablelastline" style="height: initial;">
                        【本页统计 条目：${pageNum}
                        总金额：<fmt:formatNumber type="number" value="${lzyxAmount - lzzfAmount - hzyxAmount}" pattern="0.00" maxFractionDigits="2" />；
                        不含税总金额：<fmt:formatNumber type="number" value="${lzyxTaxFreeAmount - lzzfTaxFreeAmount - hzyxTaxFreeAmount}" pattern="0.00" maxFractionDigits="2" />；
                        税额总金额：<fmt:formatNumber type="number" value="${lzyxTaxAmount - lzzfTaxAmount - hzyxTaxAmount}" pattern="0.00" maxFractionDigits="2" />；】
                        <br/>
                        【蓝字有效金额总计： <fmt:formatNumber type="number" value="${lzyxAmount}" pattern="0.00" maxFractionDigits="2" />；
                        蓝字有效不含税总金额： <fmt:formatNumber type="number" value="${lzyxTaxFreeAmount}" pattern="0.00" maxFractionDigits="2" />；
                        蓝字有效税额总金额： <fmt:formatNumber type="number" value="${lzyxTaxAmount}" pattern="0.00" maxFractionDigits="2" />；

                        蓝字作废金额总计：<fmt:formatNumber type="number" value="${lzzfAmount}" pattern="0.00" maxFractionDigits="2" />；
                        蓝字作废不含税总金额：<fmt:formatNumber type="number" value="${lzzfTaxFreeAmount}" pattern="0.00" maxFractionDigits="2" />；
                        蓝字作废税额总金额：<fmt:formatNumber type="number" value="${lzzfTaxAmount}" pattern="0.00" maxFractionDigits="2" />；

                        红字有效金额总计：<fmt:formatNumber type="number" value="${hzyxAmount}" pattern="0.00" maxFractionDigits="2" />
                        红字有效不含税总金额：<fmt:formatNumber type="number" value="${hzyxTaxFreeAmount}" pattern="0.00" maxFractionDigits="2" />
                        红字有效税额总金额：<fmt:formatNumber type="number" value="${hzyxTaxAmount}" pattern="0.00" maxFractionDigits="2" /> 】
                    </div>
                    <div class="clear"></div>
                    <div class="fixtablelastline" style="height: initial;">
                        【全部结果  蓝字有效发票：<fmt:formatNumber type="number" value="${invoice.lzyxNum == null ? 0 : invoice.lzyxNum}" pattern="0" maxFractionDigits="0" />；
                        蓝字作废：<fmt:formatNumber type="number" value="${invoice.lzzfNum == null ? 0 : invoice.lzzfNum}" pattern="0" maxFractionDigits="0" />；
                        红字有效：<fmt:formatNumber type="number" value="${invoice.hzyxNum == null ? 0 : invoice.hzyxNum}" pattern="0" maxFractionDigits="0" /> 】
                    </div>
                    <div class="clear"></div>
                    <div class="fixtablelastline" style="height: initial;">
                        【全部结果 条目：${invoice.invoiceCount}
                        总金额：<fmt:formatNumber type="number" value="${invoice.amountCount == null ? 0 : invoice.amountCount}" pattern="0.00" maxFractionDigits="2" /> ；
                        不含税总金额：<fmt:formatNumber type="number" value="${invoice.lzyxTaxFreeAmount - invoice.lzzfTaxFreeAmount - invoice.hzyxTaxFreeAmount}" pattern="0.00" maxFractionDigits="2" />；
                        税额总金额：<fmt:formatNumber type="number" value="${invoice.lzyxTaxAmount - invoice.lzzfTaxAmount - invoice.hzyxTaxAmount}" pattern="0.00" maxFractionDigits="2" />；】
                        <br/>
                        【蓝字有效金额总计：<fmt:formatNumber type="number" value="${invoice.lzyxAmount == null ? 0 : invoice.lzyxAmount}" pattern="0.00" maxFractionDigits="2" />；
                        蓝字有效不含税总金额：<fmt:formatNumber type="number" value="${invoice.lzyxTaxFreeAmount == null ? 0 : invoice.lzyxTaxFreeAmount}" pattern="0.00" maxFractionDigits="2" />；
                        蓝字有效税额总金额：<fmt:formatNumber type="number" value="${invoice.lzyxTaxAmount == null ? 0 : invoice.lzyxTaxAmount}" pattern="0.00" maxFractionDigits="2" />；

                        蓝字作废金额总计：<fmt:formatNumber type="number" value="${invoice.lzzfAmount == null ? 0 : invoice.lzzfAmount}" pattern="0.00" maxFractionDigits="2" />；
                        蓝字作废不含税总金额：<fmt:formatNumber type="number" value="${invoice.lzzfTaxFreeAmount == null ? 0 : invoice.lzzfTaxFreeAmount}" pattern="0.00" maxFractionDigits="2" />；
                        蓝字作废税额总金额：<fmt:formatNumber type="number" value="${invoice.lzzfTaxAmount == null ? 0 : invoice.lzzfTaxAmount}" pattern="0.00" maxFractionDigits="2" />；

                        红字有效金额总计：<fmt:formatNumber type="number" value="${invoice.hzyxAmount == null ? 0 : invoice.hzyxAmount}" pattern="0.00" maxFractionDigits="2" />
                        红字有效不含税总金额：<fmt:formatNumber type="number" value="${invoice.hzyxTaxFreeAmount == null ? 0 : invoice.hzyxTaxFreeAmount}" pattern="0.00" maxFractionDigits="2" />
                        红字有效税额总金额：<fmt:formatNumber type="number" value="${invoice.hzyxTaxAmount == null ? 0 : invoice.hzyxTaxAmount}" pattern="0.00" maxFractionDigits="2" />】
                    </div>
                </div>
            </div>

		</div>
	</div>
<%@ include file="../../common/footer.jsp"%>
