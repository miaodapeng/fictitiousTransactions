<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="订单修改列表" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/order/saleorder/index.js?rnd=<%=Math.random()%>'></script>
	<div class="searchfunc">
		<form method="post" id="search" action="<%= basePath %>order/saleorder/modifyApplyindex.do">
			<ul>
				<li>
					<label class="infor_name">订单号</label>
					<input type="text" class="input-middle" name="saleorderNo" id="saleorderNo" value="${saleorder.saleorderNo}"/>
				</li>
				<li>
					<label class="infor_name">审核状态</label>
					<select class="input-middle" name="verifyStatus" id="">
						<option value="">全部</option>
						<option <c:if test="${saleorder.verifyStatus eq 3}">selected</c:if> value="3">待审核</option>
						<option <c:if test="${saleorder.verifyStatus eq 0}">selected</c:if> value="0">审核中</option>
						<option <c:if test="${saleorder.verifyStatus eq 1}">selected</c:if> value="1">审核通过</option>
						<option <c:if test="${saleorder.verifyStatus eq 2}">selected</c:if> value="2">审核未通过</option>
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
							<th class="wid10">销售部门</th>
							<th class="wid15">归属</th>
							<th class="wid10">收款状态</th>
							<th class="wid10">发货状态</th>
							<th class="wid10">开票状态</th>
							<th class="wid10">审核状态</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="list" items="${saleorderList}" varStatus="num">
							<tr>
								<td>${num.count}</td>
								<td>
									
									<c:if test="${list.validStatus eq 0}">
										<c:set var="shenhe" value="0"></c:set>
										<c:if test="${list.verifyStatus!=null && null!=list.verifyUsernameList}">
											<c:forEach items="${list.verifyUsernameList}" var="verifyUsernameInfo">
												<c:if test="${verifyUsernameInfo == loginUser.username}">
													<c:set var="shenhe" value="1"></c:set>
												</c:if>
											</c:forEach>
										</c:if>
									</c:if>
									<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewsaleordermodifyapply${list.saleorderModifyApplyId}","link":"./order/saleorder/viewModifyApply.do?saleorderModifyApplyId=${list.saleorderModifyApplyId}&saleorderId=${list.saleorderId}","title":"订单信息"}'>${list.saleorderModifyApplyNo}</a>${list.validStatus == 0 && list.verifyStatus != 1 && shenhe == 1 ?"<font color='red'>[审]</font>":""}
								</td>
								<td>
									<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewsaleorder${list.saleorderId}","link":"./order/saleorder/view.do?saleorderId=${list.saleorderId}","title":"订单信息"}'>${list.saleorderNo}</a>
								</td>
								<td>${list.salesDeptName}</td>
								<td>${list.optUserName}</td>
								<td>
									<c:choose>
										<c:when test="${list.paymentStatus eq 0}">
											未收款
										</c:when>
										<c:when test="${list.paymentStatus eq 1}">
											部分收款
										</c:when>
										<c:when test="${list.paymentStatus eq 2}">
											全部收款
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
											<span style="color: red">未开票</span>
										</c:when>
										<c:when test="${list.invoiceStatus eq 1}">
											<span style="color: red">部分开票</span>
										</c:when>
										<c:when test="${list.invoiceStatus eq 2}">
											全部开票
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
				<c:if test="${empty saleorderList}">
	      			<!-- 查询无结果弹出 -->
	          		<div class="noresult">查询无结果！请尝试使用其他搜索条件。</div>
		       	</c:if>
			</div>
		</div>
       	<tags:page page="${page}"/>
	</div>
<%@ include file="../../common/footer.jsp"%>