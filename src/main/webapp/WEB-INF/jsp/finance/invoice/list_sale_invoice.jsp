<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="开票记录列表" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%=basePath%>static/js/finance/invoice/list_sale_invoice.js?rnd=<%=Math.random()%>'></script>
	<div class="main-container">
		<div class="list-pages-search">
			<form method="post" id="search2" action="<%=basePath%>finance/invoice/getSaleInvoiceListPage.do">
				<ul>
					<li>
						<label class="infor_name">发票号</label>
						<input type="text" class="input-middle" name="invoiceNo" id="invoiceNo" value="${invoice.invoiceNo}" />
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
						<label class="infor_name">发票类型</label>
						<select class="input-middle" id="invoiceProperty" name="invoiceProperty">
							<option value="">全部</option>
							<option <c:if test="${invoice.invoiceProperty eq 1}">selected</c:if> value="1">纸质发票</option>
							<option <c:if test="${invoice.invoiceProperty eq 2}">selected</c:if> value="2">电子发票</option>
						</select>
					</li>
					<li>
						<label class="infor_name">订单号</label>
						<input type="text" class="input-middle" name="saleorderNo" id="saleorderNo" value="${invoice.saleorderNo}" />
					</li>
					<li>
						<label class="infor_name">客户名称</label>
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
						<label class="infor_name">寄送</label>
						<select class="input-middle" name="isSendInvoice" id="isSendInvoice">
							<option value="">全部</option>
							<option <c:if test="${invoice.isSendInvoice eq 1}">selected</c:if> value="1">寄</option>
							<option <c:if test="${invoice.isSendInvoice eq 0}">selected</c:if> value="0">不寄</option>
						</select>
					</li>
					<li>
						<label class="infor_name">寄送状态</label>
						<select class="input-middle" name="expressId" id="expressId">
							<option value="">全部</option>
							<option <c:if test="${invoice.expressId eq 1}">selected</c:if> value="1">已寄送</option>
							<option <c:if test="${invoice.expressId eq -1}">selected</c:if> value="-1">未寄送</option>
						</select>
					</li>
					<li>
						<label class="infor_name">开票总额</label>
						<input type="text" class="wid81" name="startAmount" id="startAmount" value="${invoice.startAmount}" />
						<span style="margin: 0 3px 0 -2px">-</span>
						<input type="text" class="wid81" name="endAmount" id="endAmount" value="${invoice.endAmount}" />
					</li>

					<li>
						<label class="infor_name">开票备注</label>
						<select class="input-middle" name="invoiceComments" id="invoiceComments">
							<option value="">全部</option>
							<option <c:if test="${invoice.invoiceComments eq 1}">selected</c:if> value="1">有</option>
							<option <c:if test="${invoice.invoiceComments eq -1}">selected</c:if> value="-1">无</option>
						</select>
					</li>
					<li>
						<input type="hidden" name="searchDateType" value="second"/><!-- 标记是否新打开查询页 -->
						<div class="infor_name specialinfor" >
							<select name="dateType" id="dateType" style="width:75px">
								<option value="1" <c:if test="${invoice.dateType eq 1}">selected</c:if>>开票日期</option>
								<option value="2" <c:if test="${invoice.dateType eq 2}">selected</c:if>>寄送日期</option>
							</select>
						</div>
						<input type="hidden" id="de_startTime" value="${(empty searchDateType)?startTime:de_startTime}"/>
						<input class="Wdate f_left input-smaller96 m0" type="text" placeholder="请选择日期" onClick="WdatePicker()" name="startTime" id="startTime" value="${startTime}">
						<div class="f_left ml1 mr1 mt4">-</div>
						<input type="hidden" id="de_endTime" value="${(empty searchDateType)?endTime:de_endTime}"/>
						<input class="Wdate f_left input-smaller96" type="text" placeholder="请选择日期" onClick="WdatePicker()" name="endTime" id="endTime" value="${endTime}">
					</li>
				</ul>
				<div class="tcenter">
				    <input type="hidden" id="companyId" value="${invoice.companyId}">
					<span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" id="searchSpan">搜索</span>
					<span class="bt-small bg-light-blue bt-bg-style mr20" onclick="resetPage();">重置</span>
					<span class="bt-small bg-light-blue bt-bg-style" onclick="exportOpenInvoiceList()">导出列表</span>
					<span class="bt-small bg-light-blue bt-bg-style" onclick="sendOpenInvoicelist(this)">发送至金蝶</span>
					<span class="bt-small bg-light-blue bt-bg-style" onclick="batchDownEInvoice()">下载电子发票</span>
				</div>
			</form>
			<div class="parts mt10">
					<div class="title-container">
							<div class="table-title nobor">寄送发票</div>
							<form action="<%=basePath%>finance/invoice/getSaleInvoiceListPage.do" id="invoiceTrader">
								<input type="hidden" id="jsInvoiceNo" name="jsInvoiceNo">
								<input type="hidden" name="searchDateType" value="second"/>
								<input type="hidden" id="jsStartTime" name="startTime" value=""/>
								<input type="hidden" id="jsEndTime" name="endTime" value=""/>
							</form>
					</div>
					<table class="table table-style11" style="margin-bottom:0;">
						<tbody>
							<tr>
								<td>
									<div class="inputfloat limb0">
										<ul>
											<li><label>发票号</label> <input type="text" id="sInvoiceNo" name="sInvoiceNo" value="${invoice.jsInvoiceNo}">
											</li>
											<li><label>快递单号</label> <input type="text" id="logisticsNo">
											</li>
											<li>
												<label>快递公司</label>
												<select style='width:178px;' id="logisticsName">
													<c:forEach var="list" items="${logisticsList}" varStatus="num">
														<c:if test="${list.isEnable eq 1}">
															<option <c:if test="${list.name == '中通快递'}">selected</c:if> value="${list.name}" id="${list.logisticsId}">${list.name}</option>
														</c:if>
													</c:forEach>
												</select>
											</li>
											<li>
												<button type="button" class="bt-bg-style bg-light-blue bt-small" onclick="printview()">寄送发票并打印快递单</button>
											</li>
										</ul>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
		</div>
	
			<div class="fixdiv">
				<div class="superdiv" id="list_table">
					<table class="table table-bordered table-striped table-condensed table-centered" id="listInfo">
						<thead>
							<tr>
								<th class="wid4">选择</th>
								<th class="wid4">序号</th>
								<th class="wid10">发票号</th>
								<th class="wid12">发票代码</th>
								<th class="wid14">票种</th>
								<th class="wid10">红蓝字</th>
								<th class="wid8">发票金额</th>
								<th class="wid14">开票日期</th>
								<th class="wid6">开票人</th>
								<th class="wid10">订单号</th>
								<th class="wid6">寄送</th>
								<th class="wid8">寄送状态</th>
								<th class="wid7">寄送人</th>
								<th class="wid20">客户名称</th>
								<th class="wid12">发起人</th>
								<th class="wid14">开票备注</th>
								<th class="wid4">查看</th>
								<th class="wid12">金蝶凭证号</th>
								<th class="wid6">发送结果</th>
								<th class="wid12">操作</th>
							</tr>
						</thead>
						<tbody>
							<c:set var="pageAmount" value="0"></c:set><!-- 当前页总额 -->
							<c:set var="lzyxAmount" value="0"></c:set><!-- 蓝字有效总额 -->
							<c:set var="lzzfAmount" value="0"></c:set><!-- 蓝字作废总额 -->
							<c:set var="hzyxAmount" value="0"></c:set><!-- 红字有效总额 -->
							<c:set var="pageNum" value="0"></c:set><!-- 当前页记录数 -->
							<c:forEach var="list" items="${saleInvoiceList}" varStatus="num">
								<c:set var="pageNum" value="${pageNum + 1}"></c:set>
								<tr>
									<td>
										<!-- 未寄送 -->
										<c:if test="${(empty list.expressId) or (list.expressId eq 0)}">
											<c:set var="isAfter" value="0"></c:set>
											<c:forEach items="${saleAfterList}" var="afterList">
												<c:if test="${list.relatedId eq afterList.orderId}">
													<c:set var="isAfter" value="1"></c:set><!-- 存在售后退货记录不允许寄送 -->
												</c:if>
											</c:forEach>
											<c:if test="${isAfter == 0 and list.isSendInvoice eq 1 and empty list.invoiceFlow}">
												<input type="checkbox" name="checkName" value="${list.traderId}" id="${list.invoiceId}" class="${list.expressId}" alt="${list.invoiceNo}" placeholder="${list.type}" onclick="optCheck(this);">
												<input type="hidden" name="traderDetalis"  value="${list.invoiceTraderContactName },${list.invoiceTraderContactMobile },${list.invoiceTraderContactTelephone },${list.invoiceArea },${list.invoiceAddress }"/>
											</c:if>
										</c:if>
									</td>
									<td>${num.count}</td>
									<td>
										<c:if test="${not empty list.invoiceFlow}">
											<font color='red'>[电]</font>
										</c:if>
										${list.invoiceNo}
										<%-- <a class="addtitle" href="javascript:void(0);" tabtitle='{"num":"viewsaleorder${list.invoiceId}","link":"./finance/invoice/viewSaleorder.do?saleorderId=${list.invoiceId}","title":"发票号"}'>${list.invoiceNo}</a> --%>
									</td>
									<td>${list.invoiceCode}</td>
									<td>
										<c:forEach var="invoiceType" items="${invoiceTypeList}" varStatus="num">
											<c:if test="${invoiceType.sysOptionDefinitionId eq list.invoiceType}">${invoiceType.title}</c:if>
										</c:forEach>
									</td>
									<td>
										<c:choose>
											<c:when test="${list.colorType eq 1}">
												<c:choose>
													<c:when test="${list.isEnable eq 0}">
														<span style="color: red">红字作废</span>
													</c:when>
													<c:otherwise>
														<span style="color: red">红字有效</span>
														<c:set var="hzyxAmount" value="${hzyxAmount + list.amount}"></c:set>
													</c:otherwise>
												</c:choose>
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when test="${list.isEnable eq 0}">
														<span style="color: red">蓝字作废</span>
														<c:set var="lzzfAmount" value="${lzzfAmount + list.amount}"></c:set>
													</c:when>
													<c:otherwise>
														蓝字有效
														<c:set var="lzyxAmount" value="${lzyxAmount + list.amount}"></c:set>
													</c:otherwise>
												</c:choose>
											</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:set var="pageAmount" value="${list.amount + pageAmount}"></c:set>
										<fmt:formatNumber type="number" value="${list.amount}" pattern="0.00" maxFractionDigits="2" />
									</td>
									<td><date:date value="${list.addTime}" /></td>
									<td>
										<c:forEach varStatus="userNum" var="user" items="${userList}">
											<c:if test="${list.creator eq user.userId}">
												${user.username}
											</c:if>
										</c:forEach>
									</td>
									<td>
										<c:if test="${list.type eq 505}"><!-- 销售订单 -->
											<a class="addtitle" href="javascript:void(0);" tabtitle='{"num":"view_finance_sale${list.relatedId}","link":"./finance/invoice/viewSaleorder.do?saleorderId=${list.relatedId}","title":"订单信息"}'>${list.saleorderNo}</a>
										</c:if>
										<c:if test="${list.type eq 504}"><!-- 售后订单 -->
											<span class="font-blue addtitle" tabTitle='{"num":"view_finance_after${list.relatedId}",
												"link":"./finance/after/getFinanceAfterSaleDetail.do?afterSalesId=${list.relatedId}&subjectType=${list.afterSubjectType}&type=${list.afterType}","title":"财务售后订单"}'>
												${list.saleorderNo}
											</span>
										</c:if>
									</td>
									<td>
										<c:choose>
											<c:when test="${list.isSendInvoice eq 1}">寄</c:when>
											<c:otherwise>不寄</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when test="${(!empty list.expressId) and (list.expressId ne 0)}">已寄送</c:when>
											<c:otherwise>未寄送</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:forEach varStatus="userNum" var="user" items="${userList}">
											<c:if test="${list.sendUserId eq user.userId}">
												${user.username}
											</c:if>
										</c:forEach>
									</td>
									<td>
										<a class="addtitle" href="javascript:void(0);" tabtitle='{"num":"viewcustomer${list.traderId}", "link":"./trader/customer/baseinfo.do?traderId=${list.traderId}", "title":"客户信息"}'>${list.traderName}</a>
									</td>
									<td>
										<c:forEach varStatus="traderNum" var="user" items="${traderList}">
											<c:if test="${list.traderId eq user.traderId}">
												${user.username}
											</c:if>
										</c:forEach>
									</td>
									<td>${list.invoiceComments}</td>
									<td>
									<c:if test="${(!empty list.express) or (null != list.express)}">
										<a  href="javascript:void(0);" onclick="openprintview('${list.express.logisticsName}','${list.invoiceNo}','${list.express.logisticsNo}','${list.express.expressId }','${list.type}')">快递单打印</a>
									</c:if>
									</td>
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
										<c:if test="${not empty list.invoiceNo and not empty list.invoiceFlow}">
											<a href= "${list.invoiceHref}" target="_blank"><font color='bule'><span class="edit-user" onclick="delAction(3);">下载</span></font></a>
										</c:if>
										<span class="edit-user pop-new-data" layerParams='{"width":"850px","height":"460px","title":"查看已开发票详情","link":"<%=basePath%>finance/invoice/viewInvoicedItems.do?invoiceId=${list.invoiceId}"}' >查看开票商品</span>
									</td>
								</tr>
							</c:forEach>
							<c:if test="${empty saleInvoiceList}">
								<tr>
									<td colspan="20">
										<!-- 查询无结果弹出 --> 查询无结果！请尝试使用其他搜索条件。
									</td>
								</tr>
							</c:if>
						</tbody>
					</table>
				</div>
			</div>
			<div>
				<div class="table-style4 f_left" style="margin-top:-3px;">
					<div class="allchose">
						<input type="checkbox" name="checkOptAll" onclick="checkAllOpt(this);"><span>全选</span>
					</div>
					<!-- <div class="print-record">
						<span class="bt-border-style border-blue" onclick="sendInvoice();">寄送发票</span>
						<span class="nobor pop-new-data" id="relatedIdArr"></span>
		          	</div> -->
				</div>
				<tags:page page="${page}" />
				<div class="clear"></div>
				<div class="fixtablelastline">
					【本页统计 条目：${pageNum} 总金额：<fmt:formatNumber type="number" value="${pageAmount}" pattern="0.00" maxFractionDigits="2" />；
											     蓝字有效金额总计： <fmt:formatNumber type="number" value="${lzyxAmount}" pattern="0.00" maxFractionDigits="2" />； 
											     蓝字作废金额总计：<fmt:formatNumber type="number" value="${lzzfAmount}" pattern="0.00" maxFractionDigits="2" />；
											     红字有效金额总计：<fmt:formatNumber type="number" value="${hzyxAmount}" pattern="0.00" maxFractionDigits="2" /> 】
				</div>
				<div class="clear"></div>
				<div class="fixtablelastline">
					【全部结果 条目：${invoice.invoiceCount} 总金额：<fmt:formatNumber type="number" value="${invoice.amountCount == null ? 0 : invoice.amountCount}" pattern="0.00" maxFractionDigits="2" /> ；
														      蓝字有效金额总计：<fmt:formatNumber type="number" value="${invoice.lzyxAmount == null ? 0 : invoice.lzyxAmount}" pattern="0.00" maxFractionDigits="2" />； 
														      蓝字作废金额总计：<fmt:formatNumber type="number" value="${invoice.lzzfAmount == null ? 0 : invoice.lzzfAmount}" pattern="0.00" maxFractionDigits="2" />； 
														      红字有效金额总计：<fmt:formatNumber type="number" value="${invoice.hzyxAmount == null ? 0 : invoice.hzyxAmount}" pattern="0.00" maxFractionDigits="2" />】 
				</div>
			</div>
	</div>
<%@ include file="../../common/footer.jsp"%>
