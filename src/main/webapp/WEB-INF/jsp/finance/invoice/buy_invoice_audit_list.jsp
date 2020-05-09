<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/myfn.tld" prefix="myfn" %>
<c:set var="title" value="收票审核" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%=basePath%>static/js/finance/invoice/buy_invoice_audit.js?rnd=<%=Math.random()%>'></script>
	<div class="main-container">
		<div class="list-pages-search">
			<form method="post" id="searchForm" action="<%=basePath%>finance/invoice/buyInvoiceAuditList.do">
				<ul>
					<li>
						<label class="infor_name">发票号</label>
						<input type="text" class="input-middle" id="invoiceNo" value="${invoice.invoiceNo}" />
						<input type="hidden" name="invoiceNo" id="hideInvoiceNo" value="${invoice.invoiceNo}"/>
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
						<label class="infor_name">供应商</label>
						<input type="text" class="input-middle" name="traderName" id="traderName" value="${invoice.traderName}" />
					</li>
					<li>
						<label class="infor_name">录票人</label>
						<input type="text" class="input-middle" name="creatorName" id="creatorName" value="${invoice.creatorName}" />
					</li>
					<li>
						<label class="infor_name">申请日期</label>
						<input class="Wdate f_left input-smaller96 m0" type="text" placeholder="请选择日期" onClick="WdatePicker()" name="startTime" id="startTime" value="${startTime}">
						<div class="f_left ml1 mr1 mt4">-</div>
						<input class="Wdate f_left input-smaller96" type="text" placeholder="请选择日期" onClick="WdatePicker()" name="endTime" id="endTime" value="${endTime}">
					</li>
				</ul>
				<div class="tcenter">
					<span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" id="searchSpan">搜索</span>
					<span class="bt-small bg-light-blue bt-bg-style mr20" onclick="resetInvoice();">重置</span>
				</div>
			</form>
		</div>
		<div class="tablelastline" style="margin-top:0;">
				<!-- onclick="selectChange(2,1);" -->
				蓝字有效发票<span style="color:blue">【${invoice.lzyxNum}】</span>
				<%-- 蓝字作废<a href="javaScript:void(0);" onclick="selectChange(2,0);"><span style="color:blue">【${invoice.lzzfNum}】</span></a>，
				红字有效<a href="javaScript:void(0);" onclick="selectChange(1,1);"><span style="color:blue">【${invoice.hzyxNum}】</span></a> --%>
		</div>
		<div class="main-container">
			 <div class="list-pages-search">
				<table class="table">
					<thead>
						<tr>
							<th class="wid4">序号</th>
							<th class="wid10">发票号</th>
							<th class="wid10">录票总额</th>
							<th class="wid10">不含税总额</th>
							<th class="wid10">税额</th>
							<th>票种</th>
							<th>
								<c:choose>
									<c:when test="${list.afterSubjectType eq 537}">
										客户
									</c:when>
									<c:otherwise>供应商</c:otherwise>
								</c:choose>
							</th>
							<th>红蓝字</th>
							<th>申请时间</th>
							<th class="wid10">录票人员</th>
						</tr>
					</thead>
					<tbody>
						<c:set var="pageAmount" value="0"></c:set><!-- 当前页总额 -->
						<c:set var="lzyxTaxFreeAmount" value="0"></c:set><!-- 不含税金额 -->
						<c:set var="lzyxTaxAmount" value="0"></c:set><!-- 税额 -->
						<c:set var="pageNum" value="0"></c:set><!-- 当前页记录数 -->
						<c:forEach var="list" items="${invoiceList}" varStatus="num">
							<c:set var="pageNum" value="${pageNum + 1}"></c:set>
							<tr>
								<td>${num.index + 1}</td>
								<fmt:parseNumber value="${list.amount}" var="amount" />
	                        	<fmt:parseNumber value="${list.ratio}" var="ratio" />
	                        	<c:set var="pageAmount" value="${list.amount + pageAmount}"></c:set>
								<td><a href="javaScript:void(0)" onclick="buyAuditDetail('${list.invoiceNo}',${list.colorType},${list.isEnable},${list.type})">${list.invoiceNo}</a></td>
		                        <td><fmt:formatNumber type="number" value="${amount}" pattern="0.00" maxFractionDigits="2" /></td>
		                        <td>
		                        	<c:choose>
			                        	<c:when test="${list.invoiceType eq 429 or list.invoiceType eq 682 or list.invoiceType eq 684 or list.invoiceType eq 686 or list.invoiceType eq 688 or list.invoiceType eq 972}"><!-- 专用发票 -->
				                        	<fmt:formatNumber type="number" value="${amount/(1+ratio)}" pattern="0.00" maxFractionDigits="2" />
											<c:set var="lzyxTaxFreeAmount" value="${myfn:rounding(amount/(1+ratio)) + lzyxTaxFreeAmount}"></c:set>
			                        	</c:when>
			                        	<c:otherwise>
											<fmt:formatNumber type="number" value="${amount}" pattern="0.00" maxFractionDigits="2" />
											<c:set var="lzyxTaxFreeAmount" value="${amount + lzyxTaxFreeAmount}"></c:set>
										</c:otherwise>
		                        	</c:choose>
		                        </td>
		                        <td>
		                        	<c:choose>
			                        	<c:when test="${list.invoiceType eq 429 or list.invoiceType eq 682 or list.invoiceType eq 684 or list.invoiceType eq 686 or list.invoiceType eq 688 or list.invoiceType eq 972}"><!-- 专用发票 -->
				                        	<fmt:parseNumber value="${list.amount}" var="amount" />
				                        	<fmt:formatNumber type="number" value="${amount - amount/(1+ratio)}" pattern="0.00" maxFractionDigits="2" />
											<c:set var="lzyxTaxAmount" value="${myfn:rounding(amount - amount/(1+ratio)) + lzyxTaxAmount}"></c:set>
			                        	</c:when>
			                        	<c:otherwise>
											0
											<c:set var="lzyxTaxAmount" value="${0 + lzyxTaxAmount}"></c:set>
										</c:otherwise>
		                        	</c:choose>
		                        </td>
		                        <td>
		                        	<c:forEach var="typelist" items="${invoiceTypeList}">
		                        		<c:if test="${list.invoiceType eq typelist.sysOptionDefinitionId}">${typelist.title}</c:if>
		                        	</c:forEach>
		                        </td>
		                        <td>
		                        	<c:choose>
		                        		<c:when test="${list.afterType eq -1}"><!-- 售后工程师发票 -->
		                        			<%-- <a class="addtitle" href="javascript:void(0);" tabtitle='{"num":"viewengineer2","link":"./aftersales/engineer/view.do?engineerId=${list.traderId}","title":"查看工程师"}'>${list.traderName}</a> --%>
		                        			<a class="addtitle" href="javascript:void(0);" tabtitle='{"num":"viewcustomer${list.traderId}","link":"./trader/customer/baseinfo.do?traderId=${list.traderId}","title":"客户信息"}'>${list.traderName}</a>
		                        		</c:when>
		                        		<c:otherwise><!-- 供应商 -->
				                        	<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewsupplier${list.traderId}","link":"./trader/supplier/baseinfo.do?traderId=${list.traderId}","title":"供应商信息"}'>${list.traderName}</a>
		                        		</c:otherwise>
		                        	</c:choose>
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
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${list.isEnable eq 0}">
													<span style="color: red">蓝字作废</span>
												</c:when>
												<c:otherwise>
													蓝字有效
												</c:otherwise>
											</c:choose>
										</c:otherwise>
									</c:choose>
								</td>
		                        <td><date:date value="${list.addTime}"/></td>
		                        <td>${list.userName}
		                        	<%-- <c:forEach var="user" items="${userList}">
		                        		<c:if test="${list.creator eq user.userId}">${user.username}</c:if>
		                        	</c:forEach> --%>
		                        </td>
		                    </tr>
						</c:forEach>
						<c:if test="${empty invoiceList}">
							<tr>
								<td colspan="10">
									<!-- 查询无结果弹出 --> 查询无结果！请尝试使用其他搜索条件。
								</td>
							</tr>
						</c:if>
					</tbody>
				</table>
			</div>
			<tags:page page="${page}" />
			<div class="clear"></div>
			<div class="fixtablelastline">
				【全部结果 条目：${page.totalRecord}
					总金额：<fmt:formatNumber type="number" value="${invoice.lzyxAmount == null ? 0 : invoice.lzyxAmount}" pattern="0.00" maxFractionDigits="2" />
					不含税总金额：<fmt:formatNumber type="number" value="${invoice.lzyxTaxFreeAmount == null ? 0 : invoice.lzyxTaxFreeAmount}" pattern="0.00" maxFractionDigits="2" />
					税额总金额：<fmt:formatNumber type="number" value="${invoice.lzyxTaxAmount == null ? 0 : invoice.lzyxTaxAmount}" pattern="0.00" maxFractionDigits="2" />
				】
				【本页统计 条目：${pageNum}
					总金额：<fmt:formatNumber type="number" value="${pageAmount}" pattern="0.00" maxFractionDigits="2" />
					不含税总金额：<fmt:formatNumber type="number" value="${lzyxTaxFreeAmount}" pattern="0.00" maxFractionDigits="2" />
					税额总金额：<fmt:formatNumber type="number" value="${lzyxTaxAmount}" pattern="0.00" maxFractionDigits="2" />
				】
			</div>
		</div>
	</div>
<%@ include file="../../common/footer.jsp"%>
