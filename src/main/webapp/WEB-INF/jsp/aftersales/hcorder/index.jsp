<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="耗材售后订单列表" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript"></script>
<script type="text/javascript" src='<%= basePath %>static/js/order/saleorder/index.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src='<%= basePath %>static/js/aftersales/order/index.js?rnd=<%=Math.random()%>'></script>
	<div class="searchfunc">
		<form method="post" id="search" action="<%= basePath %>/aftersales/hcorder/getHcAfterSalesPage.do">
			<ul>
				<li>
					<label class="infor_name">售后单号</label>
					<input type="text" class="input-middle" name="afterSalesNo" id="afterSalesNo" value="${afterSalesVo.afterSalesNo}"/>
				</li>
				<li>
					<label class="infor_name">对应订单号</label>
					<input type="text" class="input-middle" name="orderNo" id="orderNo" value="${afterSalesVo.orderNo}"/>
				</li>
				<li>
					<label class="infor_name">客户名称</label>
					<input type="text" class="input-middle" name="traderName" id="traderName" value="${afterSalesVo.traderName}"/>
				</li>
				<li>
					<label class="infor_name">订单状态</label>
					<select class="input-middle" name="atferSalesStatus" id="atferSalesStatus">
						<option value="">全部</option>
						<option <c:if test="${afterSalesVo.atferSalesStatus eq 0}">selected</c:if> value="0">待确认</option>
						<option <c:if test="${afterSalesVo.atferSalesStatus eq 1}">selected</c:if> value="1">进行中</option>
						<option <c:if test="${afterSalesVo.atferSalesStatus eq 2}">selected</c:if> value="2">已完结</option>
						<option <c:if test="${afterSalesVo.atferSalesStatus eq 3}">selected</c:if> value="3">已关闭</option>
					</select>
				</li>
				<li>
					<label class="infor_name">审核状态</label>
					<select class="input-middle" name="status" id="status">
						<option value="">全部</option>
						<option <c:if test="${afterSalesVo.status eq 0}">selected</c:if> value="0">待审核</option>
						<option <c:if test="${afterSalesVo.status eq 1}">selected</c:if> value="1">审核中</option>
						<option <c:if test="${afterSalesVo.status eq 2}">selected</c:if> value="2">审核通过</option>
						<option <c:if test="${afterSalesVo.status eq 3}">selected</c:if> value="3">审核不通过</option>
					</select>
				</li>
				<li>
					<label class="infor_name">开票状态</label>
					<select class="input-middle" name="invoiceStatus" id="invoiceStatus">
						<option value="">全部</option>
						<option <c:if test="${afterSalesVo.invoiceStatus eq 0}">selected</c:if> value="0">未开票</option>
						<option <c:if test="${afterSalesVo.invoiceStatus eq 1}">selected</c:if> value="1">部分开票</option>
						<option <c:if test="${afterSalesVo.invoiceStatus eq 2}">selected</c:if> value="2">全部开票</option>
					</select>
				</li>
				<li>
					<label class="infor_name">收票状态</label>
					<select class="input-middle" name="receiveInvoiceStatus" id="receiveInvoiceStatus">
						<option value="">全部</option>
						<option <c:if test="${afterSalesVo.receiveInvoiceStatus eq 0}">selected</c:if> value="0">未收票</option>
						<option <c:if test="${afterSalesVo.receiveInvoiceStatus eq 1}">selected</c:if> value="1">部分收票</option>
						<option <c:if test="${afterSalesVo.receiveInvoiceStatus eq 2}">selected</c:if> value="2">全部收票</option>
					</select>
				</li>
				<li>
					<label class="infor_name">收款状态</label>
					<select class="input-middle" name="receivePaymentStatus" id="receivePaymentStatus">
						<option value="">全部</option>
						<option <c:if test="${afterSalesVo.receivePaymentStatus eq 0}">selected</c:if> value="0">未收款</option>
						<option <c:if test="${afterSalesVo.receivePaymentStatus eq 1}">selected</c:if> value="1">部分收款</option>
						<option <c:if test="${afterSalesVo.receivePaymentStatus eq 2}">selected</c:if> value="2">全部收款</option>
					</select>
				</li>
				<li>
					<label class="infor_name">付款状态</label>
					<select class="input-middle" name="paymentStatus" id="paymentStatus">
						<option value="">全部</option>
						<option <c:if test="${afterSalesVo.paymentStatus eq 0}">selected</c:if> value="0">未付款</option>
						<option <c:if test="${afterSalesVo.paymentStatus eq 1}">selected</c:if> value="1">部分付款</option>
						<option <c:if test="${afterSalesVo.paymentStatus eq 2}">selected</c:if> value="2">全部付款</option>
					</select>
				</li>
				<li>
					<label class="infor_name">业务类型</label>
					<select class="input-middle" name="type" id="type">
						<option value="">全部</option>
						<c:forEach items="${sysList }" var="sys">
							<c:if test="${empty sys.relatedField }">
								<option value="${sys.sysOptionDefinitionId }"
								<c:if test="${sys.sysOptionDefinitionId == afterSalesVo.type}">selected="selected"</c:if>>${sys.title }</option>
							</c:if>
							
						</c:forEach>
					</select>
				</li>
				<li>
					<label class="infor_name">售后人员</label>
					<select class="input-middle" name="serviceUserId" id="serviceUserId">
						<option value="">全部</option>
						<c:forEach items="${serviceUserList }" var="su">
							<c:if test="${sys.relatedField == null}">
							<option value="${su.userId }"
								<c:if test="${su.userId == afterSalesVo.serviceUserId}">selected="selected"</c:if>>${su.username}</option>
								</c:if>
						</c:forEach>
					</select>
				</li>
				<li>
					<div class="infor_name specialinfor">
						<select name="timeType">
							<option value="1" <c:if test="${afterSalesVo.timeType == 1 }">selected="selected"</c:if>>申请时间</option>
							<option value="2" <c:if test="${afterSalesVo.timeType == 2 }">selected="selected"</c:if>>生效时间</option>
						</select>
					</div> 
					<input type="hidden" name="search" value="click">
					<input type="hidden" name="nowDate" value="${nowDate}">
					<input type="hidden" name="pre1MonthDate" value="${pre1MonthDate}">
					
					<input class="Wdate f_left input-smaller96 m0" autocomplete="off" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endtime\')}'})"
						name="starttime" id="starttime"  type="text" placeholder="请选择日期" value='<c:choose>
							<c:when test="${afterSalesVo.starttime != ''}">
								<date:date value ="${afterSalesVo.searchStartTime}" format="yyyy-MM-dd"/>
							</c:when>
							<c:when test="${afterSalesVo.starttime == ''}">
							</c:when>
							<c:otherwise>
								${pre1MonthDate}
							</c:otherwise>
						</c:choose>'>
					
					<div class="f_left ml1 mr1 mt4">-</div> 
					<input class="Wdate f_left input-smaller96" autocomplete="off" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'starttime\')}'})"
						name="endtime" id="endtime" type="text" placeholder="请选择日期" value='<c:choose>
							<c:when test="${afterSalesVo.endtime != ''}">
								<date:date value ="${afterSalesVo.searchEndTime}" format="yyyy-MM-dd"/>
							</c:when>
							<c:when test="${afterSalesVo.endtime == ''}">
							</c:when>
							<c:otherwise>
								${nowDate}
							</c:otherwise>
						</c:choose>'>
				</li>
				
			</ul>
			<div class="tcenter">
				<span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" id="searchSpan">搜索</span>
				<span class="bt-small bg-light-blue bt-bg-style mr20" onclick="searchReset();">重置</span>
				<span class="bt-small bg-light-blue bt-bg-style addtitle" tabTitle='{"num":"viewaftersales<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
						"link":"./aftersales/order/addAfterSalesPage.do?flag=at","title":"新增售后"}'>新增售后</span>
				<!-- <span class="bt-small bg-light-blue bt-bg-style" onclick="exportList();">导出列表</span> -->
			</div>
		</form>
	</div>
	<div class="content">
		<div class="normal-list-page">
			<div >
				<table
					class="table">
					<thead>
						<tr>
							<th class="wid3">序号</th>
							<th class="wid9">售后单号</th>
							<th class="wid8">对应订单号</th>
							<th class="wid15">客户名称</th>
							<th class="wid10">业务类型</th>
							<th class="wid10">销售部门</th>
							<th class="wid8">创建人</th>
							<th class="wid8">售后人员</th>
							<th class="wid8">订单状态</th>
							<th class="wid8">开票状态</th>
							<th class="wid8">收票状态</th>
							<th class="wid10">申请时间</th>
							<th class="wid10">生效时间</th>
							<th class="wid8">审核状态</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="aftersales" items="${list}" varStatus="num">
							<c:set var="shenhe" value="0"></c:set>
							<c:if test="${aftersales.verifyStatus==0 && null!=aftersales.verifyUsernameList}">
								<c:forEach items="${aftersales.verifyUsernameList}" var="verifyUsernameInfo">
									<c:if test="${verifyUsernameInfo == loginUser.username}">
										<c:set var="shenhe" value="1"></c:set>
									</c:if>
								</c:forEach>
							</c:if>
							<tr>
								<td>${num.count}</td>
								<td>
									<c:if test="${aftersales.atferSalesStatus eq 0}"><span class="orangecircle"></span></c:if>
									<c:if test="${aftersales.atferSalesStatus eq 1}"><span class="greencircle"></span></c:if>
									<c:if test="${aftersales.atferSalesStatus eq 2}"><span class="bluecircle"></span></c:if>
									<c:if test="${aftersales.atferSalesStatus eq 3}"><span class="greycircle"></span></c:if>
								<!-- 
									<c:if test="${aftersales.isView ne 1}">
										${aftersales.afterSalesNo}
									</c:if> -->
									<c:if test="${not empty aftersales.orderNo}">
										<span class="font-blue addtitle" tabTitle='{"num":"viewaftersales<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
											"link":"./aftersales/hcorder/viewAfterSalesDetail.do?afterSalesId=${aftersales.afterSalesId}&traderType=1","title":"售后详情"}'>${aftersales.afterSalesNo}${aftersales.verifyStatus eq 0 and shenhe == 1 ?"<font color='red'>[审]</font>":""}</span>
									</c:if>
									<c:if test="${empty aftersales.orderNo}">
										<span class="font-blue addtitle" tabTitle='{"num":"viewaftersales<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
											"link":"./aftersales/order/viewAfterSalesDetail.do?afterSalesId=${aftersales.afterSalesId}","title":"售后详情"}'>${aftersales.afterSalesNo}${aftersales.verifyStatus eq 0 and shenhe == 1 ?"<font color='red'>[审]</font>":""}</span>
									</c:if>
								</td>
								<td>
									<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewsaleorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
										"link":"<%= basePath %>/order/hc/hcOrderDetailsPage.do?saleorderId=${aftersales.orderId}","title":"耗材订单信息"}'>${aftersales.orderNo}</a>
								</td>
								<td>
									<a class="addtitle" href="javascript:void(0);"
										tabTitle='{"num":"viewcustomer<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
											"link":"./trader/customer/baseinfo.do?traderId=${aftersales.traderId}",
											"title":"客户信息"}'>${aftersales.traderName}</a>
								</td>
								<td>${aftersales.typeName}</td>
								<td>${aftersales.orgName}</td>
								<td>${aftersales.creatorName}</td>
								<td>
									<c:if test="${aftersales.subjectType eq 537}">${aftersales.creatorName}</c:if>
									<c:if test="${aftersales.subjectType ne 537}">${aftersales.serviceUserName}</c:if>
								</td>
								<td>
									<c:if test="${aftersales.atferSalesStatus eq 0}">待确认</c:if>
									<c:if test="${aftersales.atferSalesStatus eq 1}">进行中</c:if>
									<c:if test="${aftersales.atferSalesStatus eq 2}">已完结</c:if>
									<c:if test="${aftersales.atferSalesStatus eq 3}">已关闭</c:if>
								</td>
								<td>
									<c:if test="${aftersales.invoiceStatus eq 0}">未开票</c:if>
									<c:if test="${aftersales.invoiceStatus eq 1}">部分开票</c:if>
									<c:if test="${aftersales.invoiceStatus eq 2}">全部开票</c:if>
								</td>
								<td>
									<c:if test="${aftersales.receiveInvoiceStatus eq 0}">未收票</c:if>
									<c:if test="${aftersales.receiveInvoiceStatus eq 1}">部分收票</c:if>
									<c:if test="${aftersales.receiveInvoiceStatus eq 2}">全部收票</c:if>
								</td>
								<td><date:date value ="${aftersales.addTime}"/></td>
								<td><date:date value ="${aftersales.validTime}"/></td>
								<td>
									<c:if test="${empty aftersales.verifyStatus}">待审核</c:if>
									<c:if test="${aftersales.verifyStatus eq 0}">审核中</c:if>
									<c:if test="${aftersales.verifyStatus eq 1}">审核通过</c:if>
									<c:if test="${aftersales.verifyStatus eq 2}">审核不通过</c:if>
								</td>
							</tr>
						</c:forEach>
						<c:if test="${empty list}">
			      			<tr>
			      				<td colspan="12">查询无结果！请尝试使用其他搜索条件。</td>
			      			</tr>
				       	</c:if>
					</tbody>
				</table>
			</div>
		</div>
       	<tags:page page="${page}"/>
	</div>
</body>

</html>
