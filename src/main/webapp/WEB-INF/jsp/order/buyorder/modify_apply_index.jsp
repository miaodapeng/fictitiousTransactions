<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="采购订单修改列表" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/order/buyorder/index.js?rnd=<%=Math.random()%>'></script>
	<div class="searchfunc">
		<form method="post" id="search" action="<%= basePath %>order/buyorder/getBuyorderModifyApplyListPage.do">
			<ul>
				<li>
					<label class="infor_name">订单号</label>
					<input type="text" class="input-middle" name="buyorderNo" value="${bmav.buyorderNo}"/>
				</li>
				<li>
					<label class="infor_name">审核状态</label>
					<select class="input-middle" name="verifyStatus" id="">
						<option value="">全部</option>
						<option <c:if test="${bmav.verifyStatus eq 3}">selected</c:if> value="3">待审核</option>
						<option <c:if test="${bmav.verifyStatus eq 0}">selected</c:if> value="0">审核中</option>
						<option <c:if test="${bmav.verifyStatus eq 1}">selected</c:if> value="1">审核通过</option>
						<option <c:if test="${bmav.verifyStatus eq 2}">selected</c:if> value="2">审核未通过</option>
					</select>
				</li>
			</ul>
			<div class="tcenter">
				<span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" id="searchSpan">搜索</span>
				<span class="bt-small bg-light-blue bt-bg-style mr20" onclick="reset();">重置</span>
			</div>
		</form>
	</div>
	<div class="content">
		<div class="">
			<div class="normal-list-page">
				<table
					class="table table-bordered table-striped table-condensed table-centered">
					<thead>
						<tr>
							<th class="wid6">序号</th>
							<th class="wid10">修改申请单编号</th>
							<th class="wid10">订单号</th>
							<th class="wid10">部门</th>
							<th class="wid15">采购员</th>
							<th class="wid10">付款状态</th>
							<th class="wid10">发货状态</th>
							<th class="wid10">收票状态</th>
							<th class="wid10">审核状态</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="list" items="${list}" varStatus="num">
							<tr>
								<td>${num.count}</td>
								<td>
									
									<%-- <c:if test="${list.validStatus eq 0}">
										<c:set var="shenhe" value="0"></c:set>
										<c:if test="${list.verifyStatus!=null && null!=list.verifyUsernameList}">
											<c:forEach items="${list.verifyUsernameList}" var="verifyUsernameInfo">
												<c:if test="${verifyUsernameInfo == loginUser.username}">
													<c:set var="shenhe" value="1"></c:set>
												</c:if>
											</c:forEach>
										</c:if>
									</c:if> --%>
									<a class="addtitle" href="javascript:void(0);" 
											tabTitle='{"num":"view<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
												"link":"./order/buyorder/viewModifyApply.do?buyorderModifyApplyId=${list.buyorderModifyApplyId}","title":"订单信息"}'>
												${list.buyorderModifyApplyNo}</a><%-- ${list.validStatus == 0 && list.verifyStatus != 1 && shenhe == 1 ?"<font color='red'>[审]</font>":""} --%>
								</td>
								<td>
									<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"view<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
												"link":"./order/buyorder/viewBuyordersh.do?buyorderId=${list.buyorderId}","title":"订单信息"}'>${list.buyorderNo}</a>
								</td>
								<td>${list.orgName}</td>
								<td>${list.userName}</td>
								<td>
									<c:choose>
										<c:when test="${list.paymentStatus eq 0}">
											未付款
										</c:when>
										<c:when test="${list.paymentStatus eq 1}">
											部分付款
										</c:when>
										<c:when test="${list.paymentStatus eq 2}">
											全部付款
										</c:when>
									</c:choose>
								</td>
								<td>
									<c:choose>
										<c:when test="${list.deliveryStatus eq 0}">
											<span style="color: red">未发货</span>
										</c:when>
										<c:when test="${list.deliveryStatus eq 1}">
											<span style="color: red">部分发货</span>
										</c:when>
										<c:when test="${list.deliveryStatus eq 2}">
											全部发货
										</c:when>
									</c:choose>
								</td>
								<td>
									<c:choose>
										<c:when test="${list.invoiceStatus eq 0}">
											<span style="color: red">未收票</span>
										</c:when>
										<c:when test="${list.invoiceStatus eq 1}">
											<span style="color: red">部分收票</span>
										</c:when>
										<c:when test="${list.invoiceStatus eq 2}">
											全部收票
										</c:when>
									</c:choose>
								</td>
								<td>
								<c:choose>
									<c:when test="${list.verifyStatus eq 0}">
										审核中
									</c:when>
									<c:when test="${list.verifyStatus eq 1}">
										审核通过
									</c:when>
									<c:when test="${list.verifyStatus eq 2}">
										审核未通过
									</c:when>
									<c:otherwise>
										待审核
									</c:otherwise>
								</c:choose>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<c:if test="${empty list}">
	      			<tr>
	      				<td colspan="9">查询无结果！请尝试使用其他搜索条件。</td>
	      			</tr>
		       	</c:if>
			</div>
		</div>
       	<tags:page page="${page}"/>
	</div>
<%@ include file="../../common/footer.jsp"%>