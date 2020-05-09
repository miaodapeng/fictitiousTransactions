<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="采购列表" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/finance/buyorder/index.js?rnd=<%=Math.random()%>'></script>
	<div class="searchfunc">
		<form method="post" id="search" action="<%= basePath %>/finance/buyorder/getBuyorderList.do">
			<ul>
				<li>
					<label class="infor_name">订单号</label>
					<input type="text" class="input-middle" name="buyorderNo" id="buyorderNo" value="${buyorderVo.buyorderNo}"/>
				</li>
				<li>
					<label class="infor_name">供应商名称</label>
					<input type="text" class="input-middle" name="traderName" id="traderName" value="${buyorderVo.traderName}"/>
				</li>
				<li>
					<label class="infor_name">创建人部门</label>
					<select class="input-middle" name="proOrgtId" id="">
						<option value="">全部</option>
						<c:forEach items="${productOrgList}" var="org">
							<option value="${org.orgId}" <c:if test="${buyorderVo.proOrgtId eq org.orgId}">selected="selected"</c:if>>${org.orgName}</option>
						</c:forEach>
					</select>
				</li>
				<li>
					<label class="infor_name">创建人</label>
					<select class="input-middle" name="proUserId" id="">
						<option value="">全部</option>
						<c:forEach items="${userList}" var="user">
							<option value="${user.userId}" <c:if test="${buyorderVo.proUserId eq user.userId}">selected="selected"</c:if>>${user.username}</option>
						</c:forEach>
					</select>
				</li>
				<li>
					<label class="infor_name">产品名称</label>
					<input type="text" class="input-middle" name="goodsName" id="goodsName" value="${buyorderVo.goodsName}"/>
				</li>
				<li>
					<label class="infor_name">品牌</label>
					<input type="text" class="input-middle" name="brandName" id="brandName" value="${buyorderVo.brandName}"/>
				</li>
				<!-- li>
					<label class="infor_name">型号</label>
					<input type="text" class="input-middle" name="model" id="model" value="${buyorderVo.model}"/>
				</li-->
				<li>
					<label class="infor_name">订货号</label>
					<input type="text" class="input-middle" name="sku" id="sku" value="${buyorderVo.sku}"/>
				</li>
				<li>
					<label class="infor_name">票种</label>
					<select class="input-middle" name="invoiceType" id="invoiceType">
						<option value="">全部</option>
						<c:forEach items="${receiptTypes}" var="receiptType">
							<option value="${receiptType.sysOptionDefinitionId}" <c:if test="${buyorderVo.invoiceType eq receiptType.sysOptionDefinitionId}">selected="selected"</c:if>>${receiptType.title}</option>
						</c:forEach>
					</select>
				</li>
				<li>
					<label class="infor_name">付款状态</label>
					<select class="input-middle" name="paymentStatus" id="paymentStatus">
						<option value="">全部</option>
						<option <c:if test="${buyorderVo.paymentStatus eq 0}">selected</c:if> value="0">未付款</option>
						<option <c:if test="${buyorderVo.paymentStatus eq 1}">selected</c:if> value="1">部分付款</option>
						<option <c:if test="${buyorderVo.paymentStatus eq 2}">selected</c:if> value="2">全部付款</option>
					</select>
				</li>
				<li>
					<label class="infor_name">收货状态</label>
					<select class="input-middle" name="arrivalStatus" id="arrivalStatus">
						<option value="">全部</option>
						<option <c:if test="${buyorderVo.arrivalStatus eq 0}">selected</c:if> value="0">未收货</option>
						<option <c:if test="${buyorderVo.arrivalStatus eq 1}">selected</c:if> value="1">部分收货</option>
						<option <c:if test="${buyorderVo.arrivalStatus eq 2}">selected</c:if> value="2">全部收货</option>
					</select>
				</li>
				<li>
					<label class="infor_name">发票状态</label>
					<select class="input-middle" name="invoiceStatus" id="invoiceStatus">
						<option value="-1">全部</option>
						<option <c:if test="${buyorderVo.invoiceStatus eq 0}">selected</c:if> value="0">未收票</option>
						<option <c:if test="${buyorderVo.invoiceStatus eq 1}">selected</c:if> value="1">部分收票</option>
						<option <c:if test="${buyorderVo.invoiceStatus eq 2}">selected</c:if> value="2">全部收票</option>
					</select>
				</li>
				<li>
					<label class="infor_name">备货采购</label>
					<select class="input-middle" name="orderType" id="orderType">
						<option value="">全部</option>
						<option <c:if test="${buyorderVo.orderType eq 1}">selected</c:if> value="1">是</option>
						<option <c:if test="${buyorderVo.orderType eq 0}">selected</c:if> value="0">否</option>
					</select>
				</li>
				<li>
					<label class="infor_name">是否直发</label>
					<select class="input-middle" name="deliveryDirect" id="deliveryDirect">
						<option value="">全部</option>
						<option <c:if test="${buyorderVo.deliveryDirect eq 1}">selected</c:if> value="1">是</option>
						<option <c:if test="${buyorderVo.deliveryDirect eq 0}">selected</c:if> value="0">否</option>
					</select>
				</li>
				<li>
					<label class="infor_name">订单金额</label>
					<input class="f_left input-smaller96 mr5" type="text" name="searchBeginAmount" id="searchBeginAmount" value='${buyorderVo.searchBeginAmount}'>
                    <div class="gang">-</div>
                    <input class="f_left input-smaller96" type="text" name="searchEndAmount" id="searchEndAmount" value='${buyorderVo.searchEndAmount}'>
				</li>
				<li>
					<div class="infor_name">
						生效时间
					</div>
					<input class="Wdate f_left input-smaller96 mr5" type="text" placeholder="请选择日期" onClick="WdatePicker()" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'searchEndtimeStr\')}'})" name="searchBegintimeStr" id="searchBegintimeStr" value='<c:choose>
						<c:when test="${buyorderVo.searchBegintime > 0}">
							<date:date value ="${buyorderVo.searchBegintime}" format="yyyy-MM-dd"/>
						</c:when>
						<c:when test="${buyorderVo.searchBegintime == null}">
						</c:when>
						<c:otherwise>
							${pre1MonthDate}
						</c:otherwise>
					</c:choose>'>
                    <div class="gang">-</div>
                    <input class="Wdate f_left input-smaller96" type="text" placeholder="请选择日期" onClick="WdatePicker()" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'searchBegintimeStr\')}'})" name="searchEndtimeStr" id="searchEndtimeStr" value='<c:choose>
						<c:when test="${buyorderVo.searchEndtime > 0}">
							<date:date value ="${buyorderVo.searchEndtime}" format="yyyy-MM-dd"/>
						</c:when>
						<c:when test="${buyorderVo.searchEndtime == null}">
						</c:when>
						<c:otherwise>
							${nowDate}
						</c:otherwise>
					</c:choose>'>
				</li>
			</ul>
			<div class="tcenter">
				<input type="hidden" name="pre1MonthDate" value="${pre1MonthDate}">
           		<input type="hidden" name="nowDate" value="${nowDate}">
				<span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" id="searchSpan">搜索</span>
				<span class="bt-small bg-light-blue bt-bg-style mr20" onclick="searchReset();">重置</span>
				<!-- <span class="bt-small bg-light-blue bt-bg-style">导出列表</span>
				<span class="bt-small bg-light-blue bt-bg-style">导出明细</span> -->
			</div>
		</form>
	</div>
	<div class="content">
		<div class="fixdiv">
			<div class="superdiv">
				<table
					class="table table-bordered table-striped table-condensed table-centered">
					<thead>
						<tr>
							<th class="table-smallest3">序号</th>
							<th class="table-smallest4">订单号</th>
							<th class="table-smallest">供应商</th>
							<th class="table-smallest6">生效时间</th>
							<th class="table-smallest4">部门</th>
							<th class="table-smallest4">创建人</th>
							<th class="table-smallest5">票种</th>
							<th class="table-smallest3">是否直发</th>
							<th class="table-smallest4">订单总额</th>
							<th class="table-smallest4">已付款金额</th>
							<th class="table-smallest4">应付账期款</th>
							<th class="table-smallest4">已收票总额</th>
							<th class="table-smallest3">付款状态</th>
							<th class="table-smallest3">收货状态</th>
							<th class="table-smallest3">发票状态</th>
						</tr>
					</thead>
					<tbody>
						<c:set var="pageNum" value="0"></c:set>
						<c:set var="pageAmount1" value="0.00"></c:set>
						<c:set var="pageAmount2" value="0.00"></c:set>
						<c:set var="pageAmount3" value="0.00"></c:set>
						<c:forEach var="buyorder" items="${list}" varStatus="num">
							<c:set var="pageNum" value="${num.count}"></c:set>
							<c:set var="pageAmount1" value="${pageAmount1 + buyorder.totalAmount}"></c:set>
							<c:set var="pageAmount2" value="${pageAmount2 + buyorder.paymentAmount}"></c:set>
							<c:set var="pageAmount3" value="${pageAmount3 + buyorder.invoiceAmount}"></c:set>
							<tr>
								<td>${num.count}</td>
								<td>
										<a class="addtitle" href="javascript:void(0);" 
											tabTitle='{"num":"viewfinancebuyorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
													"link":"./finance/buyorder/viewBuyorder.do?buyorderId=${buyorder.buyorderId}","title":"订单信息"}'>${buyorder.buyorderNo}</a>
								</td>
								<td>${buyorder.traderName}</td>
								<td><date:date value ="${buyorder.validTime}"/></td>
								<td>${buyorder.buyDepartmentName}</td>
								<td>${buyorder.userName}</td>
								<td>
									<c:forEach items="${receiptTypes}" var="receiptType">
										<c:if test="${buyorder.invoiceType eq receiptType.sysOptionDefinitionId}">${receiptType.title}</c:if>
									</c:forEach>
								</td>
								<td>
									<c:if test="${buyorder.deliveryDirect eq 0}">普发</c:if>
									<c:if test="${buyorder.deliveryDirect eq 1}">直发</c:if>
								</td>
								<td>${buyorder.totalAmount}</td>
								<td>${buyorder.paymentAmount}</td>
								<td>${buyorder.lackAccountPeriodAmount}</td>
								<td>${buyorder.invoiceAmount}</td>
								<td>
									<c:if test="${buyorder.paymentStatus eq 0}">未付款</c:if>
									<c:if test="${buyorder.paymentStatus eq 1}">部分付款</c:if>
									<c:if test="${buyorder.paymentStatus eq 2}">全部付款</c:if>
								</td>
								<td>
									<c:if test="${buyorder.arrivalStatus eq 0}">未收货</c:if>
									<c:if test="${buyorder.arrivalStatus eq 1}">部分收货</c:if>
									<c:if test="${buyorder.arrivalStatus eq 2}">全部收货</c:if>
								</td>
								<td>
									<c:if test="${buyorder.invoiceStatus eq 0}">未收票</c:if>
									<c:if test="${buyorder.invoiceStatus eq 1}">部分收票</c:if>
									<c:if test="${buyorder.invoiceStatus eq 2}">全部收票</c:if>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				
				<c:if test="${empty list}">
	      			<!-- 查询无结果弹出 -->
	          		<div class="noresult">查询无结果！请尝试使用其他搜索条件。</div>
		       	</c:if>
			</div>
		</div>
		<div>
       	<tags:page page="${page}"/>
       	<div class="clear"></div>
       	<c:if test="${not empty list}">
			<div class="fixtablelastline">
				【全部结果 订单数：${page.totalRecord} 总金额：<fmt:formatNumber type="number" value="${buyorder.allTotalAmount}" pattern="0.00" maxFractionDigits="2" /> 已付款总额：<fmt:formatNumber type="number" value="${buyorderVo.paymentTotalAmount}" pattern="0.00" maxFractionDigits="2" /> 收票总额：<fmt:formatNumber type="number" value="${buyorderVo.invoiceTotalAmount}" pattern="0.00" maxFractionDigits="2" />】
				【本页统计 订单数：${pageNum} 总金额：<fmt:formatNumber type="number" value="${pageAmount1}" pattern="0.00" maxFractionDigits="2" /> 已付款总额：<fmt:formatNumber type="number" value="${pageAmount2}" pattern="0.00" maxFractionDigits="2" /> 收票总额：<fmt:formatNumber type="number" value="${pageAmount3}" pattern="0.00" maxFractionDigits="2" />】
			</div>
		</c:if>
		</div>
	</div>
<%@ include file="../../common/footer.jsp"%>
