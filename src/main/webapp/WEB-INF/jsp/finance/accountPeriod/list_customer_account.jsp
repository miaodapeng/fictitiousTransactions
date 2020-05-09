<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="客户账期记录" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%=basePath%>static/js/finance/accountPeriod/list_customer_account.js?rnd=<%=Math.random()%>'></script>
	<div class="main-container">
		<div class="list-pages-search">
			<form method="post" id="search" action="<%=basePath%>finance/accountperiod/getCustomerAccountListPage.do">
				<ul>
					<li>
						<label class="infor_name">客户名称</label>
						<input type="text" class="input-middle" name="traderName" id="traderName" value="${ap.traderName}" />
					</li>
					<li>
						<label class="infor_name">归属销售</label>
						<select class="input-middle" name="traderUserId" id="traderUserId">
							<option value="">全部</option>
							<c:forEach var="list" items="${ascriptionUserList}" varStatus="num">
								<option <c:if test="${ap.traderUserId eq list.userId}">selected</c:if> value="${list.userId}">${list.username}</option>
							</c:forEach>
						</select>
					</li>
					<li>
						<label class="infor_name">逾期状态</label>
						<select class="input-middle" name="overdueState" id="overdueState">
							<option value="">全部</option>
							<option value="1" <c:if test="${ap.overdueState eq 1}">selected</c:if>>未逾期</option>
							<option value="2" <c:if test="${ap.overdueState eq 2}">selected</c:if>>已逾期</option>
							<option value="3" <c:if test="${ap.overdueState eq 3}">selected</c:if>>未开始</option>
						</select>
					</li>
					<li>
						<label class="infor_name">订单号</label>
						<input type="text" class="input-middle" name="saleorderNo" id="saleorderNo" value="${ap.saleorderNo}" />
					</li>
					<li>
						<label class="infor_name">
							订单生效日期
						</label>
						<input class="Wdate f_left input-smaller96 m0" type="text" placeholder="请选择日期" onClick="WdatePicker()" name="startTime"	value="${startTime}">
						<div class="f_left ml1 mr1 mt4">-</div> 
						<input class="Wdate f_left input-smaller96" type="text" placeholder="请选择日期" onClick="WdatePicker()" name="endTime" value="${endTime}">
					</li>
				</ul>
				<div class="tcenter">
					<span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" id="searchSpan">搜索</span>
					<span class="bt-small bg-light-blue bt-bg-style mr20" onclick="reset();">重置</span>
					<span class="bt-small bg-light-blue bt-bg-style mr20" onclick="exportCustomerAccount();">导出</span>
				</div>
			</form>
		</div>
		<div class="list-page">
			<div class="fixdiv">
				<div class="superdiv">
					<table class="table table-bordered table-striped table-condensed table-centered">
						<thead>
							<tr>
								<th class="wid4">序号</th>
								<th class="wid18">客户名称</th>
								<th class="wid10">归属销售</th>
								<th class="wid10">订单编号</th>
								<th class="wid14">订单生效日期</th>
								<th class="wid10">订单金额</th>
								<th class="wid10">使用帐期金额</th>
								<th class="wid10">归还帐期金额</th>
								<th class="wid14">帐期开始时间</th>
								<th class="wid10">帐期结算期限</th>
								<th class="wid14">帐期结算时间</th>
								<th class="wid10">逾期状态</th>
							</tr>
						</thead>
						<tbody>
							<c:set var="orderNumPage" value="0"></c:set><!-- 本页统计 订单数 -->
							<c:set var="orderAmountPage" value="0"></c:set><!-- 订单总额 -->
							<c:set var="accountAmountPage" value="0"></c:set><!-- 使用帐期总额 -->
							<c:forEach var="list" items="${customerAccountList}" varStatus="num">
								<tr>
									<td>${num.count}</td>
									<td>
										<a class="addtitle" href="javascript:void(0);" tabtitle='{"num":"viewcustomer${list.traderId}", "link":"./trader/customer/baseinfo.do?traderId=${list.traderId}", "title":"客户信息"}'>${list.traderName}</a>
									</td>
									<td>
										<c:forEach var="user" items="${userList}" varStatus="statu">
											<c:if test="${list.traderId eq user.traderId}">${user.username}</c:if>
										</c:forEach>
									</td>
									<td>
										<c:if test="${! empty list.saleorderNo}">
											<c:set var="orderNumPage" value="${orderNumPage+1}"></c:set>
										</c:if>
										<a class="addtitle" href="javascript:void(0);" tabtitle='{"num":"viewsaleorder${list.relatedId}","link":"./order/saleorder/view.do?saleorderId=${list.relatedId}","title":"订单信息"}'>${list.saleorderNo}</a>
									</td>
									<td><date:date value="${list.validTime}" /></td>
									<td>
										<c:set var="orderAmountPage" value="${list.totalAmount + orderAmountPage}"></c:set>
										<fmt:formatNumber type="number" value="${list.totalAmount}" pattern="0.00" maxFractionDigits="2" />
									</td>
									<td>
										<c:set var="accountAmountPage" value="${list.accountPeriodAmount + accountAmountPage}"></c:set>
										<fmt:formatNumber type="number" value="${list.accountPeriodAmount}" pattern="0.00" maxFractionDigits="2" />
									</td>
									<td><fmt:formatNumber type="number" value="${list.amount}" pattern="0.00" maxFractionDigits="2" /></td>
									
									<td><date:date value="${list.deliveryTime}" /></td>
									<td><date:date value="${list.accountEndTime}" format="yyyy-MM-dd"/></td>
									<!-- 截止日期加一天 -->
									<c:set var="accountEndTime" value="${list.accountEndTime + 24 * 60 * 60 * 1000}"></c:set>
									<c:choose>
										<c:when test="${!empty list.deliveryTime and list.deliveryTime ne 0}"><!-- 账期开始日期不为空 -->
											<c:choose>
												<c:when test="${empty list.traderTime or list.traderTime eq 0}"><!-- 无归还日期 -->
													<c:if test="${accountEndTime > sysdate}"><!-- 账期截止日期大于当前日期 -->
														<!-- 未逾期 -->
														<td><date:date value="${list.traderTime}" /></td>
														<td><span class="font-green">未逾期</span></td>
													</c:if>
													<c:if test="${accountEndTime <= sysdate}"><!-- 账期截止日期 -->
														<td><date:date value="${list.traderTime}" /></td>
														<td><span class="font-red">已逾期</span></td>
													</c:if>
												</c:when>
												<c:otherwise>
													<c:if test="${accountEndTime > list.traderTime}"><!-- 账期截止日期大于最后归还日期 -->
														<!-- 逾期 -->
														<td><date:date value="${list.traderTime}" /></td>
														<td><span class="font-green">未逾期</span></td>
													</c:if>
													<c:if test="${accountEndTime <= list.traderTime}"><!-- 账期截止日期 -->
														<td><date:date value="${list.traderTime}" /></td>
														<td><span class="font-red">已逾期</span></td>
													</c:if>
												</c:otherwise>
											</c:choose>
											
										</c:when>
										<c:otherwise><!-- 账期未开始 -->
											<c:choose>
												<c:when test="${!empty list.traderTime and list.traderTime ne 0}"><!-- 有归还 -->
													<td><date:date value="${list.traderTime}" /></td>
													<td><span class="font-green">未逾期</span></td>
												</c:when>
												<c:otherwise>
													<td></td>
													<td>未开始</td>
												</c:otherwise>
											</c:choose>
										</c:otherwise>
									</c:choose>
								</tr>
							</c:forEach>
							<c:if test="${empty customerAccountList}">
								<tr>
									<td colspan="12">
										<!-- 查询无结果弹出 --> 查询无结果！请尝试使用其他搜索条件。
									</td>
								</tr>
							</c:if>
						</tbody>
					</table>
				</div>
			</div>
			<div class="clear"></div>
			<tags:page page="${page}" />
			<div class="clear"></div>
			<div class="fixtablelastline">
				【全部结果 订单数：${ap.orderNum} 订单总额：<fmt:formatNumber type="number" value="${ap.totalAmount == null ? 0 : ap.totalAmount}" pattern="0.00" maxFractionDigits="2" /> 使用帐期总额：<fmt:formatNumber type="number" value="${ap.accountPeriodAmount == null ?0 :ap.accountPeriodAmount}" pattern="0.00" maxFractionDigits="2" /> 】
				【本页统计 订单数：${orderNumPage} 订单总额：<fmt:formatNumber type="number" value="${orderAmountPage}" pattern="0.00" maxFractionDigits="2" /> 使用帐期总额：<fmt:formatNumber type="number" value="${accountAmountPage}" pattern="0.00" maxFractionDigits="2" />】
			</div>
		</div>
	</div>
<%@ include file="../../common/footer.jsp"%>
