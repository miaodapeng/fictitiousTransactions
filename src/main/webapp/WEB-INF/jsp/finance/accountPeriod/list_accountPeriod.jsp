<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="账期申请记录" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/finance/accountPeriod/list_accountPeriod.js?rnd=<%=Math.random()%>'></script>
	<div class="main-container">
		<div class="list-pages-search">
			<form action="" method="post" id="search" action="<%=basePath%>finance/accountperiod/accountPeriodApplyList.do">
				<ul>
					<li>
						<label class="infor_name">客户名称</label>
						<input type="text" class="input-middle" name="traderName" value="${tapa.traderName}"/>
					</li>
					<li>
						<label class="infor_name">客户身份</label>
						<select class="input-middle" name="traderType">
							<option value="">全部</option>
							<option <c:if test="${tapa.traderType eq 1}">selected</c:if> value="1">客户</option>
							<option <c:if test="${tapa.traderType eq 2}">selected</c:if> value="2">供应商</option>
						</select>
					</li>
					<li>
						<label class="infor_name">审核状态</label>
						<select class="input-middle" name="status">
							<option value="-1">全部</option>
							<option <c:if test="${tapa.status eq 0}">selected</c:if> value="0">审核中</option>
							<option <c:if test="${tapa.status eq 1}">selected</c:if> value="1">审核通过</option>
							<option <c:if test="${tapa.status eq 2}">selected</c:if> value="2">审核不通过</option>
						</select>
					</li>
					<li>
						<label class="infor_name">归属人员</label>
						<select class="input-middle" name="creator">
							<option value="">全部</option>
							<c:forEach items="${userList}" var="list" varStatus="status">
								<option value="${list.userId}" <c:if test="${tapa.creator eq list.userId}">selected</c:if>>${list.username}</option>
							</c:forEach>
						</select>
					</li>
					<li>
						<label class="infor_name">日期</label>
						<input class="Wdate f_left input-smaller96 mr5" type="text" placeholder="请选择日期" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')}'})" name="startTime" id="startTime" value="${startTime}">
						<div class="gang">-</div> 
						<input class="Wdate f_left input-smaller96" type="text"	placeholder="请选择日期" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}'})" name="endTime" id="endTime" value="${endTime}">
					</li>
				</ul>
				<div class="tcenter">
					<span class="bt-small bg-light-blue bt-bg-style mr20" onclick="search()">搜索</span> 
					<span class="bt-small bg-light-blue bt-bg-style mr20" onclick="reset();">重置</span> 
					<!-- <span class="bt-small bg-light-blue bt-bg-style mr20" onclick="exportAccountPeriod(1);">导出1</span> 
					<span class="bt-small bg-light-blue bt-bg-style mr20" onclick="exportAccountPeriod(2);">导出2</span>  -->
					<span class="bt-small bg-light-blue bt-bg-style mr20" onclick="exportAccountPeriodList();">导出</span>
				</div>
			</form>
		</div>
		<div class="list-page">
				<table class="table table-bordered table-striped table-condensed table-centered">
					<thead>
						<tr>
							<th class="wid3">序号</th>
							<th class="wid16">客户名称</th>
							<th class="wid5">客户身份</th>
							<th class="wid5">客户性质</th>
							<th class="wid6">当前额度</th>
							<th class="wid7">当前帐期天数</th>
							<th class="wid5">逾期次数</th>
							<th class="wid7">逾期总额</th>
							<th class="wid7">日期</th>
							<th class="wid6">申请金额</th>
							<th class="wid6">申请帐期天数</th>
							<th class="wid6">归属人员</th>
							<th class="wid6">审核状态</th>
							<th class="wid4">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${accountPeriodApplyList}" var="list" varStatus="status">
							<c:set var="shenhe" value="0"></c:set>
							<c:if test="${list.verifyStatus!=null && list.verifyStatus!=1 && null!=list.verifyUsernameList}">
								<c:forEach items="${list.verifyUsernameList}" var="verifyUsernameInfo">
									<c:if test="${verifyUsernameInfo == loginUser.username}">
										<c:set var="shenhe" value="1"></c:set>
									</c:if>
								</c:forEach>
							</c:if>
							<tr>
								<td>${status.index+1}</td>
								<td>
									<div class="font-blue text-ellipsis">
										<c:choose>
											<c:when test="${list.traderType eq 1}">
												<a class="addtitle" href="javascript:void(0);" tabtitle='{"num":"view_quote_customer${list.traderId}",
													"link":"./trader/customer/baseinfo.do?traderId=${list.traderId}","title":"客户信息"}'>${list.traderName}</a>
											</c:when>
											<c:otherwise>
												<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewsupplier${list.traderId}",
													"link":"./trader/supplier/baseinfo.do?traderId=${list.traderId}","title":"供应商信息"}'>${list.traderName}</a>
											</c:otherwise>
										</c:choose>
										${list.verifyStatus != 1 && shenhe == 1 ?"<font color='red'>[审]</font>":""}
									</div>
								</td>
								<td>
									<c:choose>
										<c:when test="${list.traderType eq 1}">客户</c:when>
										<c:otherwise>供应商</c:otherwise>
									</c:choose>
								</td>
								<td>
									<c:forEach var="sysList" items="${invoiceTypeList}">
										<c:if test="${list.customerNature eq sysList.sysOptionDefinitionId}">${sysList.title}</c:if>
									</c:forEach>
								</td>
								<td>${list.accountPeriodNow}</td>
								<td>${list.accountPeriodDaysNow}</td>
								<td>${list.overdueTimes}</td>
								<td>${list.overdueAmount}</td>
								<td><date:date value ="${list.addTime}" format="yyyy.MM.dd" /></td>
								<td>${list.accountPeriodApply}</td>
								<td>${list.accountPeriodDaysApply}</td>
								<td>
									<c:forEach items="${creatorUserList}" var="userList">
										<c:if test="${list.creator eq userList.userId}">${userList.username}</c:if>
									</c:forEach>
								</td>
								<td>
									<c:choose>
										<c:when test="${list.status eq 0}">审核中</c:when>
										<c:when test="${list.status eq 1}">审核通过</c:when>
										<c:when test="${list.status eq 2}">审核不通过</c:when>
										<c:otherwise>待审核</c:otherwise>
									</c:choose>
								</td>
								<td>
									<div class="font-blue"><a href="javascript:void(0);" class="addtitle" tabtitle='{"num":"finance_accountperiod_applyAudit_${list.traderAccountPeriodApplyId}","link":"./finance/accountperiod/getAccountPeriodApply.do?traderAccountPeriodApplyId=${list.traderAccountPeriodApplyId}","title":"账期申请审核"}'>查看</a></div>
								</td>
							</tr>
						</c:forEach>
						<c:if test="${empty accountPeriodApplyList}">
							<tr>
								<td colspan="14">
					      			<!-- 查询无结果弹出 -->
					          		查询无结果！请尝试使用其他搜索条件。
								</td>
							</tr>
				       	</c:if>
					</tbody>
				</table>
       	<tags:page page="${page}"/>
	</div>
<%@ include file="../../common/footer.jsp"%>
