<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="采购列表" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript"></script>
<script type="text/javascript" src='<%= basePath %>static/js/order/saleorder/index.js?rnd=<%=Math.random()%>'></script>
<c:if test="${not empty method}">
<%@ include file="../../trader/supplier/supplier_tag.jsp"%>
</c:if>
	<div class="searchfunc" <c:if test="${not empty method}">style="padding-top:0px;"</c:if>>
		<form method="post" id="search" action="<%= basePath %>/order/buyorder/getBuyorderList.do">
			<ul>
				<li>
					<label class="infor_name">采购单号</label>
					<input type="text" class="input-middle" name="buyorderNo" id="buyorderNo" value="${buyorderVo.buyorderNo}"/>
				</li>
				<li>
					<label class="infor_name">订单状态</label>
					<select class="input-middle" name="status" id="status">
						<option value="">全部</option>
						<option <c:if test="${buyorderVo.status eq 0}">selected</c:if> value="0">待确认</option>
						<option <c:if test="${buyorderVo.status eq 1}">selected</c:if> value="1">进行中</option>
						<option <c:if test="${buyorderVo.status eq 2}">selected</c:if> value="2">已完结</option>
						<option <c:if test="${buyorderVo.status eq 3}">selected</c:if> value="3">已关闭</option>
					</select>
				</li>
				<li>
					<label class="infor_name">生效状态</label>
					<select class="input-middle" name="validStatus" id="validStatus">
						<option value="">全部</option>
						<option <c:if test="${buyorderVo.validStatus eq 0}">selected</c:if> value="0">未生效</option>
						<option <c:if test="${buyorderVo.validStatus eq 1}">selected</c:if> value="1">已生效</option>
					</select>
				</li>
				<li>
					<label class="infor_name">付款状态</label>
					<select class="input-middle multiple-options" name="paymentStatus" id="paymentStatus">
						<option value="">全部</option>
						<option <c:if test="${buyorderVo.paymentStatus eq 0}">selected</c:if> value="0">未付款</option>
						<option <c:if test="${buyorderVo.paymentStatus eq 1}">selected</c:if> value="1">部分付款</option>
						<option <c:if test="${buyorderVo.paymentStatus eq 2}">selected</c:if> value="2">全部付款</option>
					</select>
				</li>
				<li>
					<label class="infor_name">发货状态</label>
					<select class="input-middle" name="deliveryStatus" id="deliveryStatus">
						<option value="">全部</option>
						<option <c:if test="${buyorderVo.deliveryStatus eq 0}">selected</c:if> value="0">未发货</option>
						<option <c:if test="${buyorderVo.deliveryStatus eq 1}">selected</c:if> value="1">部分发货</option>
						<option <c:if test="${buyorderVo.deliveryStatus eq 2}">selected</c:if> value="2">全部发货</option>
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
					<label class="infor_name">收票状态</label>
					<select class="input-middle" name="invoiceStatus" id="invoiceStatus">
						<option value="">全部</option>
						<option <c:if test="${buyorderVo.invoiceStatus eq 0}">selected</c:if> value="0">未收票</option>
						<option <c:if test="${buyorderVo.invoiceStatus eq 1}">selected</c:if> value="1">部分收票</option>
						<option <c:if test="${buyorderVo.invoiceStatus eq 2}">selected</c:if> value="2">全部收票</option>
					</select>
				</li>
				<li>
					<label class="infor_name">开票申请</label>
					<select class="input-middle" name="invoiceVerifyStatus" id="invoiceVerifyStatus">
						<option value="0">全部</option>
						<option <c:if test="${buyorderVo.invoiceVerifyStatus eq 1}">selected</c:if> value="1">审核中</option>
					</select>
				</li>
				<li>
					<label class="infor_name">审核状态</label>
					<select class="input-middle" name="verifyStatus" id="verifyStatus">
						<option value="">全部</option>
						<option value="3" <c:if test="${buyorderVo.verifyStatus eq 3 }">selected="selected"</c:if>>待审核</option>
						<option value="0" <c:if test="${buyorderVo.verifyStatus eq 0 }">selected="selected"</c:if>>审核中</option>
						<option value="1" <c:if test="${buyorderVo.verifyStatus eq 1 }">selected="selected"</c:if>>审核通过</option>
						<option value="2" <c:if test="${buyorderVo.verifyStatus eq 2 }">selected="selected"</c:if>>审核不通过</option>
					</select>
				</li>
				<li>
					<label class="infor_name">售后状态</label>
					<select class="input-middle" name="serviceStatus" id="serviceStatus">
						<option value="">全部</option>
						<option <c:if test="${buyorderVo.serviceStatus eq 0}">selected</c:if> value="0">无售后</option>
						<option <c:if test="${buyorderVo.serviceStatus eq 1}">selected</c:if> value="1">售后中</option>
						<option <c:if test="${buyorderVo.serviceStatus eq 2}">selected</c:if> value="2">售后完成</option>
						<option <c:if test="${buyorderVo.serviceStatus eq 3}">selected</c:if> value="3">售后关闭</option>
					</select>
				</li>
				<li>
					<label class="infor_name">锁定状态</label>
					<select class="input-middle" name="lockedStatus" id="lockedStatus">
						<option value="">全部</option>
						<option <c:if test="${buyorderVo.lockedStatus eq 0}">selected</c:if> value="0">未锁定</option>
						<option <c:if test="${buyorderVo.lockedStatus eq 1}">selected</c:if> value="1">已锁定</option>
					</select>
				</li>
				<li>
					<label class="infor_name">供应商名称</label>
					<input type="text" class="input-middle" name="traderName" id="traderName" value="${buyorderVo.traderName}"/>
				</li>
				<li>
					<label class="infor_name">产品名称</label>
					<input type="text" class="input-middle" name="goodsName" id="goodsName" value="${buyorderVo.goodsName}"/>
				</li>
				<li>
					<label class="infor_name">品牌</label>
					<input type="text" class="input-middle" name="brandName" id="brandName" value="${buyorderVo.brandName}"/>
				</li>
				<li>
					<label class="infor_name">型号</label>
					<input type="text" class="input-middle" name="model" id="model" value="${buyorderVo.model}"/>
				</li>
				<li>
					<label class="infor_name">订货号</label>
					<input type="text" class="input-middle" name="sku" id="sku" value="${buyorderVo.sku}"/>
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
					<select class="input-middle" name="creator" >
						<option value="0">全部</option>
						<c:forEach items="${addUserlist}" var="user">
							<option value="${user.userId}" <c:if test="${buyorderVo.creator eq user.userId}">selected="selected"</c:if>>${user.username}</option>
						</c:forEach>

					</select>
				</li>
				<li>
					<label class="infor_name">产品归属</label>
					<select class="input-middle" name="proUserId" id="">
						<option value="">全部</option>
						<c:forEach items="${productUserList}" var="user">
							<option value="${user.userId}" <c:if test="${buyorderVo.proUserId eq user.userId}">selected="selected"</c:if>>${user.username}</option>
						</c:forEach>
					</select>
				</li>
				<li>
					<label class="infor_name">是否直发</label>
					<select class="input-middle" name="deliveryDirect" id="deliveryDirect">
						<option value="">全部</option>
						<option <c:if test="${buyorderVo.deliveryDirect eq 0}">selected</c:if> value="0">普发</option>
						<option <c:if test="${buyorderVo.deliveryDirect eq 1}">selected</c:if> value="1">直发</option>
					</select>
				</li>
				<li>
					<label class="infor_name">销售单号</label>
					<input type="text" class="input-middle" name="saleorderNo" id="saleorderNo" value="${buyorderVo.saleorderNo}"/>
				</li>
				<li>
					<label class="infor_name">是否需催票</label>
						<select class="input-middle" name="isRemindTicket" id="isRemindTicket">
							<option value="">全部</option>
							<option <c:if test="${buyorderVo.isRemindTicket eq 0}">selected</c:if> value="0">否</option>
							<option <c:if test="${buyorderVo.isRemindTicket eq 1}">selected</c:if> value="1">是</option>
						</select>
				</li>
				<li>
					<label class="infor_name">是否需催货</label>
						<select class="input-middle" name="isRemindGoods" id="isRemindGoods">
							<option value="">全部</option>
							<option <c:if test="${buyorderVo.isRemindGoods eq 0}">selected</c:if> value="0">否</option>
							<option <c:if test="${buyorderVo.isRemindGoods eq 1}">selected</c:if> value="1">是</option>
						</select>
				</li>
				<li>
					<label class="infor_name">是否需付款</label>
						<select class="input-middle" name="isRemindMoney" id="isRemindMoney">
							<option value="">全部</option>
							<option <c:if test="${buyorderVo.isRemindMoney eq 0}">selected</c:if> value="0">否</option>
							<option <c:if test="${buyorderVo.isRemindMoney eq 1}">selected</c:if> value="1">是</option>
						</select>
				</li>
				<li>
					<label class="infor_name">帐期未还</label>
						<select class="input-middle" name="lackPeriod" id="">
							<option value="0">全部</option>
							<option <c:if test="${buyorderVo.lackPeriod eq 1}">selected</c:if> value="1">是</option>
						</select>
				</li>
				<li>
					<div class="infor_name specialinfor">
						<select name="searchDateType" id="searchDateType">
							<option value="1" <c:if test="${buyorderVo.searchDateType == 1}">selected="selected"</c:if> >创建时间</option>
							<option value="2" <c:if test="${buyorderVo.searchDateType == 2}">selected="selected"</c:if> >生效时间</option>
							<option value="3" <c:if test="${buyorderVo.searchDateType == 3}">selected="selected"</c:if> >付款时间</option>
							<option value="4" <c:if test="${buyorderVo.searchDateType == 4}">selected="selected"</c:if> >发货时间</option>
							<option value="5" <c:if test="${buyorderVo.searchDateType == 5}">selected="selected"</c:if> >收货时间</option>
							<option value="6" <c:if test="${buyorderVo.searchDateType == 6}">selected="selected"</c:if> >收票时间</option>
							<option value="7" <c:if test="${buyorderVo.searchDateType == 7}">selected="selected"</c:if> >审核时间</option>
						</select>
					</div>
					<input class="Wdate f_left input-smaller96 mr5" type="text" placeholder="请选择日期" onClick="WdatePicker()" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'searchEndtimeStr\')}'})" name="searchBegintimeStr" id="searchBegintimeStr" value='<date:date value ="${buyorderVo.searchBegintime}" format="yyyy-MM-dd"/>'>
                    <div class="gang">-</div>
                    <input class="Wdate f_left input-smaller96" type="text" placeholder="请选择日期" onClick="WdatePicker()" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'searchBegintimeStr\')}'})" name="searchEndtimeStr" id="searchEndtimeStr" value='<date:date value ="${buyorderVo.searchEndtime}" format="yyyy-MM-dd"/>'>
				</li>
				<li>
					<label class="infor_name">合同回传</label>
						<select class="input-middle" name="isContractReturn" id="isContractReturn">
							<option value="">全部</option>
							<option <c:if test="${buyorderVo.isContractReturn eq 0}">selected</c:if> value="0">否</option>
							<option <c:if test="${buyorderVo.isContractReturn eq 1}">selected</c:if> value="1">是</option>
						</select>
				</li>
				<li>
					<label class="infor_name">付款申请到财务</label>
						<select class="input-small" name="isFinanceAlready" id="isFinanceAlready">
							<option value="">全部</option>
							<option <c:if test="${buyorderVo.isFinanceAlready eq 0}">selected</c:if> value="0">否</option>
							<option <c:if test="${buyorderVo.isFinanceAlready eq 1}">selected</c:if> value="1">是</option>
						</select>
				</li>
				<li>
					<label class="infor_name">待操作人</label>
					<input type="text" class="input-middle" name="currentOperateUser" id="currentOperateUser" value="${buyorderVo.currentOperateUser}"/>
				</li>
			</ul>
			<div class="tcenter">
				<c:if test="${not empty traderId}">
					<input type="hidden" name="traderId" value="${traderId }" >
				</c:if>
				<span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" id="searchSpan">搜索</span>
				<span class="bt-small bg-light-blue bt-bg-style mr20" onclick="reset();">重置</span>
				<!-- <span class="bt-small bg-light-blue bt-bg-style" onclick="exportBuyOrderList();">导出列表</span>
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
							<th class="wid11">订单号</th>
							<!--  
							-->
							<th class="wid20">供应商</th>
							<!-- 
							<th class="table-smallest6">归属</th> -->
							<th class="wid10">总额</th>
							<th class="wid10">已付款金额</th>
							<th class="wid10">未还账期款</th>
							<th class="wid6">收票状态</th>
							<th class="wid6">收货状态</th>
							<th class="wid6">付款状态</th>
							<th class="wid6">发货状态</th>
							<th class="wid10">部门</th>
							<th class="wid8">创建人</th>
							<th class="wid6">订单状态</th>
							<th class="wid6">是否直发</th>
							<th class="wid10">票种</th>
							<th class="wid12">创建时间</th>
							<th class="wid12">生效时间</th>
							<th class="wid12">预计发货时间</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="buyorder" items="${list}" varStatus="num">
							<tr>
								<td id="${buyorder.buyorderId}">
									<c:choose>
										<c:when test="${buyorder.status eq 0}">
											<span class="orangecircle"></span>
										</c:when>
										<c:when test="${buyorder.status eq 1}">
											<span class="greencircle"></span>
										</c:when>
										<c:when test="${buyorder.status eq 2}">
											<span class="bluecircle"></span>
										</c:when>
										<c:otherwise>
											<span class="greycircle"></span>
										</c:otherwise>
									</c:choose>
									<!-- 
									<c:if test="${buyorder.isView ne 1}">
										${buyorder.buyorderNo}
									</c:if>
									<c:if test="${buyorder.isView eq 1}"></c:if> -->
										<c:if test="${buyorder.validStatus eq 1}">
											<a class="addtitle" href="javascript:void(0);" 
												tabTitle='{"num":"viewbuyorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
												"link":"./order/buyorder/viewBuyordersh.do?buyorderId=${buyorder.buyorderId}","title":"订单信息"}'>${buyorder.buyorderNo}</a>
										</c:if>
										<c:if test="${buyorder.validStatus ne 1}">
											<a class="addtitle" href="javascript:void(0);" 
												tabTitle='{"num":"viewbuyorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
												"link":"./order/buyorder/viewBuyorder.do?buyorderId=${buyorder.buyorderId}","title":"订单信息"}'>${buyorder.buyorderNo}</a>
										</c:if>
										${buyorder.verifyStatus == 0 && fn:contains(buyorder.verifyUsername, curr_user.username) ?"<font color='red'  class='shenhe_exception'>[审]</font>":""}
										${buyorder.paymentVerifyStatus == 0 && fn:contains(buyorder.paymentVerifyUsername, curr_user.username) ?"<font color='red'>[审]</font>":""}
									
								</td>
								<td class="text-left">${buyorder.traderName}</td>
								<!-- 
								<td>${buyorder.homePurchasing}</td> -->
								<td><fmt:formatNumber type="number" value="${buyorder.totalAmount}" pattern="0.00" maxFractionDigits="2" /></td>
								<td><fmt:formatNumber type="number" value="${buyorder.paymentAmount}" pattern="0.00" maxFractionDigits="2" /></td>
								<td><fmt:formatNumber type="number" value="${buyorder.lackAccountPeriodAmount}" pattern="0.00" maxFractionDigits="2" /></td>
								<td>
									<c:if test="${buyorder.invoiceStatus eq 0}"> <span class="warning-color1">未收票</span></c:if>
									<c:if test="${buyorder.invoiceStatus eq 1}"><span class="warning-color1">部分收票</span></c:if>
									<c:if test="${buyorder.invoiceStatus eq 2}">全部收票</c:if>
								</td>
								<td>
									<c:if test="${buyorder.arrivalStatus eq 0}"><span class="warning-color1">未收货</span></c:if>
									<c:if test="${buyorder.arrivalStatus eq 1}"><span class="warning-color1">部分收货</span></c:if>
									<c:if test="${buyorder.arrivalStatus eq 2}">全部收货</c:if>
								</td>
								<td>
									<c:if test="${buyorder.paymentStatus eq 0}">未付款</c:if>
									<c:if test="${buyorder.paymentStatus eq 1}">部分付款</c:if>
									<c:if test="${buyorder.paymentStatus eq 2}">全部付款</c:if>
								</td>
								<td>
									<c:if test="${buyorder.deliveryStatus eq 0}">未发货</c:if>
									<c:if test="${buyorder.deliveryStatus eq 1}">部分发货</c:if>
									<c:if test="${buyorder.deliveryStatus eq 2}">全部发货</c:if>
								</td>
								<td>${buyorder.buyDepartmentName}</td>
								<td>${buyorder.buyPerson}</td>
								<td>
									<c:choose>
										<c:when test="${buyorder.status eq 0}">
											<span >待确认</span>
										</c:when>
										<c:when test="${buyorder.status eq 1}">
											<span >进行中</span>
										</c:when>
										<c:when test="${buyorder.status eq 2}">
											<span >已完结</span>
										</c:when>
										<c:otherwise>
											<span >已关闭</span>
										</c:otherwise>
									</c:choose>
								</td>
								<td>
									<c:if test="${buyorder.deliveryDirect eq 0}">普发</c:if>
									<c:if test="${buyorder.deliveryDirect eq 1}">直发</c:if>
								</td>
								<td>
									<c:if test="${buyorder.invoiceType eq 429}">17%增值税专用发票</c:if>
									<c:if test="${buyorder.invoiceType eq 430}">17%增值税普通发票</c:if>
		                        	<c:if test="${buyorder.invoiceType eq 681}">16%增值税普通发票</c:if>
		                        	<c:if test="${buyorder.invoiceType eq 682}">16%增值税专用发票</c:if>
		                        	
		                        	<c:if test="${buyorder.invoiceType eq 971}">13%增值税普通发票</c:if>
		                        	<c:if test="${buyorder.invoiceType eq 972}">13%增值税专用发票</c:if>
		                        	<c:if test="${buyorder.invoiceType eq 683}">6%增值税普通发票</c:if>
		                        	<c:if test="${buyorder.invoiceType eq 684}">6%增值税专用发票</c:if>
		                        	<c:if test="${buyorder.invoiceType eq 685}">3%增值税普通发票</c:if>
		                        	<c:if test="${buyorder.invoiceType eq 686}">3%增值税专用发票</c:if>
		                        	<c:if test="${buyorder.invoiceType eq 687}">0%增值税普通发票</c:if>
		                        	<c:if test="${buyorder.invoiceType eq 688}">0%增值税专用发票</c:if>
								</td>
								<td><date:date value ="${buyorder.addTime}"/></td>
								<td><date:date value ="${buyorder.validTime}"/></td>
								<td>
									<date:date value ="${buyorder.satisfyDeliveryTime}"/> 
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
       	<tags:page page="${page}"/>
	</div>
</body>
<script>

	$(function(){
		$(".shenhe_exception").click(function(){
			 location.href="/order/buyorder/viewBuyorder.do?buyorderId="+$(this).parent().attr("id");
		})
	})

</script>
</html>
