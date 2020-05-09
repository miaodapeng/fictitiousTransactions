<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="运营-开票申请列表" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%=basePath%>static/js/finance/invoice/list_sale_invoice_apply.js?rnd=<%=Math.random()%>'></script>
	<div class="main-container">
		<div class="list-pages-search">
			<form method="post" id="search" action="<%=basePath%>finance/invoice/getSaleYyInvoiceApplyListPage.do">
				<ul>
					<li>
						<label class="infor_name">开票方式</label>
						<select class="input-middle" name="isAuto" id="isAuto">
							<option value="">全部</option>
							<option <c:if test="${invoiceApply.isAuto eq 2}">selected</c:if> value="2">自动开票</option>
							<option <c:if test="${invoiceApply.isAuto eq 1}">selected</c:if> value="1">手动开票</option>
							<option <c:if test="${invoiceApply.isAuto eq 3}">selected</c:if> value="3">电子发票</option>
						</select>
					</li>
					<li>
						<label class="infor_name">发票类型</label>
						<select class="input-middle" name="invoiceType" id="invoiceType">
							<option value="">全部</option>
							<c:forEach var="list" items="${invoiceTypeList}" varStatus="status">
								<option value="${list.sysOptionDefinitionId}" <c:if test="${list.sysOptionDefinitionId eq invoiceApply.invoiceType}">selected</c:if>>${list.title}</option>
							</c:forEach>
						</select>
					</li>
					<li>
						<label class="infor_name">提前开票</label>
						<select class="input-middle" name="isAdvance" id="isAdvance">
							<option value="">全部</option>
							<option <c:if test="${invoiceApply.isAdvance eq 0}">selected</c:if> value="0">否</option>
							<option <c:if test="${invoiceApply.isAdvance eq 1}">selected</c:if> value="1">是</option>
						</select>
					</li>
					<li>
						<label class="infor_name">订单号</label>
						<input type="text" class="input-middle" name="saleorderNo" id="saleorderNo" value="${invoiceApply.saleorderNo}" />
					</li>
					<li>
						<label class="infor_name">客户公司</label>
						<input type="text" class="input-middle" name="traderName" id="traderName" value="${invoiceApply.traderName}" />
					</li>
					<li>
						<label class="infor_name">销售部门</label>
						<select class="input-middle" name="orgId" id="orgId">
							<option value="">全部</option>
							<c:forEach var="list" items="${searchOrgList}" varStatus="orgNum">
								<option <c:if test="${invoiceApply.orgId eq list.orgId}">selected</c:if> value="${list.orgId}">${list.orgName}</option>
							</c:forEach>
						</select>
					</li>
					<li>
						<label class="infor_name">归属销售</label>
						<select class="input-middle" name="traderUserId" id="traderUserId">
							<option value="">全部</option>
							<c:forEach var="list" items="${searchTraderUserList}" varStatus="num">
								<option <c:if test="${invoiceApply.traderUserId eq list.userId}">selected</c:if> value="${list.userId}">${list.username}</option>
							</c:forEach>
						</select>
					</li>
					<li>
						<label class="infor_name">收款状态</label>
						<select class="input-middle" name="paymentStatus" id="paymentStatus">
							<option value="">全部</option>
							<option <c:if test="${invoiceApply.paymentStatus eq 0}">selected</c:if> value="0">未收款</option>
							<option <c:if test="${invoiceApply.paymentStatus eq 1}">selected</c:if> value="1">部分收款</option>
							<option <c:if test="${invoiceApply.paymentStatus eq 2}">selected</c:if> value="2">已收款</option>
						</select>
					</li>
					<li>
						<label class="infor_name">发货状态</label>
						<select class="input-middle" name="deliveryStatus" id="deliveryStatus">
							<option value="">全部</option>
							<option <c:if test="${invoiceApply.deliveryStatus eq 0}">selected</c:if> value="0">未发货</option>
							<option <c:if test="${invoiceApply.deliveryStatus eq 1}">selected</c:if> value="1">部分发货</option>
							<option <c:if test="${invoiceApply.deliveryStatus eq 2}">selected</c:if> value="2">全部发货</option>
						</select>
					</li>
					<li>
						<label class="infor_name">售后状态</label>
						<select class="input-middle" name="serviceStatus" id="serviceStatus">
							<option value="">全部</option>
							<option <c:if test="${invoiceApply.serviceStatus eq 0}">selected</c:if> value="0">无售后</option>
							<option <c:if test="${invoiceApply.serviceStatus eq 1}">selected</c:if> value="1">售后中</option>
							<option <c:if test="${invoiceApply.serviceStatus eq 2}">selected</c:if> value="2">售后完成</option>
							<option <c:if test="${invoiceApply.serviceStatus eq 3}">selected</c:if> value="3">售后关闭</option>
						</select>
					</li>
					<%-- <li>
						<label class="infor_name">审核状态</label>
						<select class="input-middle" name="validStatus" id="validStatus">
							<option value="-1">全部</option>
							<option <c:if test="${invoiceApply.validStatus eq 0}">selected</c:if> value="0">审核中</option>
							<option <c:if test="${invoiceApply.validStatus eq 1}">selected</c:if> value="1">审核通过</option>
							<option <c:if test="${invoiceApply.validStatus eq 2}">selected</c:if> value="2">审核不通过</option>
						</select>
					</li> --%>
				</ul>
				<div class="tcenter">
					<span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" id="searchSpan">搜索</span>
					<span class="bt-small bg-light-blue bt-bg-style mr20" onclick="resetPage();">重置</span>
					<span class="bt-small bg-light-blue bt-bg-style" onclick="exportInvoiceApplyList()">导出列表</span>
				</div>
			</form>
		</div>
		<div class="list-page">
			<div class="fixdiv">
				<div style="width:1814px;" class='superdiv'>
					<table class="table table-bordered table-striped table-condensed table-centered">
						<thead>
							<tr>
								<th class="wid4">序号</th>
								<th class="wid12">订单号</th>
								<th class="wid20">客户名称</th>
								<th class="wid10">销售部门</th>
								<th class="wid8">归属销售</th>
								<th class="wid12">票种</th>
								<th class="wid7">开票方式</th>
								<th class="wid7">提前开票</th>
								<th class="wid9">订单金额</th>
								<th class="wid8">收款状态</th>
								<th class="wid8">发货状态</th>
								<th class="wid8">收货状态</th>
								<th class="wid8">售后状态</th>
								<!-- <th class="wid8">状态</th> -->
								<th class="wid10">操作</th>
								<th class="wid14">申请时间</th>
								<th class="wid8">申请人</th>
								<th class="wid14">审核时间</th>
								<th class="wid8">审核人</th>
							</tr>
						</thead>
						<tbody>
							<c:set var="pageAmount" value="0"></c:set><!-- 当前页总额 -->
							<c:set var="pageNum" value="0"></c:set><!-- 当前页记录数 -->
							<c:forEach var="list" items="${openInvoiceApplyList}" varStatus="num">
								<c:set var="pageNum" value="${pageNum + 1}"></c:set>
								<tr>
									<td>${num.count}</td>
									<td>
										<a class="addtitle" href="javascript:void(0);" tabtitle='{"num":"viewfinancesaleorder${list.relatedId}","link":"./finance/invoice/viewSaleorder.do?saleorderId=${list.relatedId}","title":"订单信息"}'>${list.saleorderNo}</a>
									</td>
									<td>
										<a class="addtitle" href="javascript:void(0);" tabtitle='{"num":"viewfinancecustomer${list.traderId}", "link":"./trader/customer/baseinfo.do?traderId=${list.traderId}", "title":"客户信息"}'>${list.traderName}</a>
									</td>
									<td>
										<c:forEach var="org" items="${traderUserList}" varStatus="num">
											<c:if test="${list.traderId eq org.traderId}">${org.orgName}</c:if>
										</c:forEach>
									</td>
									<td>
										<c:forEach items="${traderUserList}" var="user" varStatus="status">
											<c:if test="${user.traderId eq list.traderId}">${user.username}</c:if>
										</c:forEach>
									</td>
									<td>${list.invoiceTypeStr}</td>
									<td>
										<c:choose>
											<c:when test="${list.isAuto eq 1}"><span style="color:red;">手动</span></c:when>
											<c:when test="${list.isAuto eq 2}">自动</c:when>
											<c:when test="${list.isAuto eq 3}">电子</c:when>
											<c:otherwise>--</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when test="${list.isAdvance eq 0}">否</c:when>
											<c:otherwise>是</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:set var="pageAmount" value="${list.totalAmount + pageAmount}"></c:set>
										<fmt:formatNumber type="number" value="${list.totalAmount}" pattern="0.00" maxFractionDigits="2" />
									</td>
									<td>
										<c:choose>
											<c:when test="${list.paymentStatus eq 0}">未收款</c:when>
											<c:when test="${list.paymentStatus eq 1}">部分收款</c:when>
											<c:otherwise>已收款</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when test="${list.deliveryStatus eq 0}">未发货</c:when>
											<c:when test="${list.deliveryStatus eq 1}">部分发货</c:when>
											<c:otherwise>全部发货</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when test="${list.arrivalStatus eq 0}">未收货</c:when>
											<c:when test="${list.arrivalStatus eq 1}">部分收货</c:when>
											<c:otherwise>全部收货</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when test="${list.serviceStatus eq 0}">无</c:when>
											<c:when test="${list.serviceStatus eq 1}">售后中</c:when>
											<c:when test="${list.serviceStatus eq 2}">售后完成</c:when>
											<c:otherwise>售后关闭</c:otherwise>
										</c:choose>
									</td>
									<%-- <td>
										<c:choose>
											<c:when test="${list.validStatus eq 1}"><span style="color: green">通过</span></c:when>
											<c:when test="${list.validStatus eq 2}"><span style="color: red">不通过</span></c:when>
											<c:otherwise>审核中</c:otherwise>
										</c:choose>
									</td> --%>
									<td>
										<c:choose>
											<c:when test="${list.validStatus eq 0}">
												<span class="edit-user pop-new-data" layerparams='{"width":"600px","height":"220px","title":"确认审核","link":"./auditYyOpenInvoice.do?invoiceApplyId=${list.invoiceApplyId}&yyValidStatus=1"}'>
													通过
												</span>
												<span class="edit-user pop-new-data delete" layerparams='{"width":"600px","height":"220px","title":"确认审核","link":"./auditYyOpenInvoice.do?invoiceApplyId=${list.invoiceApplyId}&yyValidStatus=2"}'>
													驳回
												</span>
											</c:when>
											<c:otherwise>--</c:otherwise>
										</c:choose>
									</td>
									<td><date:date value="${list.addTime}" /></td>
									<td>
										<c:forEach varStatus="userNum" var="user" items="${userList}">
											<c:if test="${list.creator eq user.userId}">
												${user.username}
											</c:if>
										</c:forEach>
									</td>
									<td><date:date value="${list.validTime}" /></td>
									<td>
										<c:forEach varStatus="userNum" var="user" items="${userList}">
											<c:if test="${list.validUserId eq user.userId}">
												${user.username}
											</c:if>
										</c:forEach>
									</td>
								</tr>
							</c:forEach>
							<c:if test="${empty openInvoiceApplyList}">
								<tr>
									<td colspan="17">
										<!-- 查询无结果弹出 --> 查询无结果！请尝试使用其他搜索条件。
									</td>
								</tr>
							</c:if>
						</tbody>
					</table>
				</div>
			</div>
			<div>
				<tags:page page="${page}" />
				<div class="clear"></div>
				<div class="fixtablelastline">
					【全部结果 条目：${page.totalRecord} 总金额：<fmt:formatNumber type="number" value="${invoiceApply.totalMoney}" pattern="0.00" maxFractionDigits="2" />】
					【本页统计 条目：${pageNum} 总金额：<fmt:formatNumber type="number" value="${pageAmount}" pattern="0.00" maxFractionDigits="2" />】
				</div>
			</div>
		</div>
	</div>
<%@ include file="../../common/footer.jsp"%>
