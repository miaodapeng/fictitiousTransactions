<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="财务销售列表" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/order/saleorder/index.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src='<%= basePath %>static/js/region/index.js?rnd=<%=Math.random()%>'></script>
	<div class="searchfunc">
		<form method="post" id="search" action="<%=basePath%>finance/invoice/getSaleorderListPage.do">
			<ul>
				<li>
					<label class="infor_name">订单号</label>
					<input type="text" class="input-middle" name="saleorderNo" id="saleorderNo" value="${saleorder.saleorderNo}"/>
				</li>
				<li>
					<label class="infor_name">订单状态</label>
					<select class="input-middle" name="status" id="status">
						<option value="-1">全部</option>
						<option <c:if test="${saleorder.status eq 0}">selected</c:if> value="0">待确认</option>
						<option <c:if test="${saleorder.status eq 1}">selected</c:if> value="1">进行中</option>
						<option <c:if test="${saleorder.status eq 2}">selected</c:if> value="2">已完结</option>
						<option <c:if test="${saleorder.status eq 3}">selected</c:if> value="3">已关闭</option>
						<!-- changed by Tomcat.Hui 2020/3/11 1:13 下午 .Desc: VDERP-2057 BD订单流程优化-ERP部分 删除待用户确认下拉框. -->
						<%-- <option <c:if test="${saleorder.status eq 4}">selected</c:if> value="4">待用户确认</option>--%>
					</select>
				</li>
				<%-- <li>
					<label class="infor_name">生效状态</label>
					<select class="input-middle" name="validStatus" id="validStatus">
						<option value="-1">全部</option>
						<option <c:if test="${saleorder.validStatus eq 0}">selected</c:if> value="0">未生效</option>
						<option <c:if test="${saleorder.validStatus eq 1}">selected</c:if> value="1">已生效</option>
					</select>
				</li> --%>
				<li>
					<label class="infor_name">客户名称</label>
					<input type="text" class="input-middle" name="traderName" id="traderName" value="${saleorder.traderName}"/>
				</li>
				<li>
					<label class="infor_name">销售部门</label>
					<select class="input-middle" name="orgId" id="orgId">
						<option value="-1">全部</option>
						<c:forEach items="${orgList}" var="org">
							<option value="${org.orgId}" <c:if test="${saleorder.orgId eq org.orgId}">selected="selected"</c:if>>${org.orgName}</option>
						</c:forEach>
					</select>
				</li>
				<li>
					<label class="infor_name">归属销售</label>
					<select class="input-middle" name="optUserId" id="optUserId">
						<option value="-1">全部</option>
						<c:forEach items="${userList}" var="list">
							<option value="${list.userId}" <c:if test="${saleorder.optUserId eq list.userId}">selected="selected"</c:if>>${list.username}</option>
						</c:forEach>
					</select>
				</li>
				<li>
					<label class="infor_name">产品名称</label>
					<input type="text" class="input-middle" name="goodsName" id="goodsName" value="${saleorder.goodsName}"/>
				</li>
				<li>
					<label class="infor_name">品牌</label>
					<input type="text" class="input-middle" name="brandName" id="brandName" value="${saleorder.brandName}"/>
				</li>
				<li>
					<label class="infor_name">订货号</label>
					<input type="text" class="input-middle" name="sku" id="sku" value="${saleorder.sku}"/>
				</li>
				<li>
					<label class="infor_name">是否直发</label>
					<select class="input-middle" name="deliveryDirect" id="deliveryDirect">
						<option value="-1">全部</option>
						<option <c:if test="${saleorder.deliveryDirect eq 0}">selected</c:if> value="0">否</option>
						<option <c:if test="${saleorder.deliveryDirect eq 1}">selected</c:if> value="1">是</option>
					</select>
				</li>
				<li>
					<label class="infor_name">发票类型</label>
					<select class="input-middle" name="invoiceType" id="invoiceType">
						<option value="-1">全部</option>
						<c:forEach var="list" items="${invoiceTypeList}" varStatus="status">
								<option value="${list.sysOptionDefinitionId}" <c:if test="${list.sysOptionDefinitionId eq saleorder.invoiceType}">selected</c:if>>${list.title}</option>
							</c:forEach>
					</select>
				</li>
				<li>
					<label class="infor_name">开票状态</label>
					<select class="input-middle" name="invoiceStatus" id="invoiceStatus">
						<option value="-1">全部</option>
						<option <c:if test="${saleorder.invoiceStatus eq 0}">selected</c:if> value="0">未开票</option>
						<option <c:if test="${saleorder.invoiceStatus eq 1}">selected</c:if> value="1">部分开票</option>
						<option <c:if test="${saleorder.invoiceStatus eq 2}">selected</c:if> value="2">全部开票</option>
					</select>
				</li>
				<!-- li>
					<label class="infor_name">需催款</label>
					<select class="input-middle" name="" id="">
						<option value="">全部</option>
					</select>
				</li-->
				<li>
					<label class="infor_name">发货状态</label>
					<select class="input-middle" name="deliveryStatus" id="deliveryStatus">
						<option value="-1">全部</option>
						<option <c:if test="${saleorder.deliveryStatus eq 0}">selected</c:if> value="0">未发货</option>
						<option <c:if test="${saleorder.deliveryStatus eq 1}">selected</c:if> value="1">部分发货</option>
						<option <c:if test="${saleorder.deliveryStatus eq 2}">selected</c:if> value="2">全部发货</option>
					</select>
				</li>
				<li>
					<label class="infor_name">收货状态</label>
					<select class="input-middle" name="arrivalStatus" id="arrivalStatus">
						<option value="-1">全部</option>
						<option <c:if test="${saleorder.arrivalStatus eq 0}">selected</c:if> value="0">未收货</option>
						<option <c:if test="${saleorder.arrivalStatus eq 1}">selected</c:if> value="1">部分收货</option>
						<option <c:if test="${saleorder.arrivalStatus eq 2}">selected</c:if> value="2">全部收货</option>
					</select>
				</li>
				<li>
					<label class="infor_name">订单原金额</label>
					<!-- 只允许输入数字和小数 -->
					<input type="text" class="wid81" name="startAmount" id="startAmount" value="${saleorder.startAmount}" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">
					<span style="margin: 0 3px 0 -2px">-</span>
					<input type="text" class="wid81" name="endAmount" id="endAmount" value="${saleorder.endAmount}" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">
				</li>
				<li>
					<label class="infor_name">收款状态</label>
					<select class="input-middle" name="paymentStatus" id="paymentStatus">
						<option value="-1">全部</option>
						<option <c:if test="${saleorder.paymentStatus eq 0}">selected</c:if> value="0">未收款</option>
						<option <c:if test="${saleorder.paymentStatus eq 1}">selected</c:if> value="1">部分收款</option>
						<option <c:if test="${saleorder.paymentStatus eq 2}">selected</c:if> value="2">全部收款</option>
					</select>
				</li>
				<li>
					<div class="infor_name specialinfor">
						<select name="searchDateType" id="searchDateType">
							<option value="1" <c:if test="${saleorder.searchDateType == 1}">selected="selected"</c:if> >创建时间</option>
							<option value="2" <c:if test="${saleorder.searchDateType == 2}">selected="selected"</c:if> >生效时间</option>
							<option value="3" <c:if test="${saleorder.searchDateType == 3}">selected="selected"</c:if> >付款时间</option>
							<option value="4" <c:if test="${saleorder.searchDateType == 4}">selected="selected"</c:if> >发货时间</option>
							<option value="5" <c:if test="${saleorder.searchDateType == 5}">selected="selected"</c:if> >收货时间</option>
							<option value="6" <c:if test="${saleorder.searchDateType == 6}">selected="selected"</c:if> >开票时间</option>
						</select>
					</div>
					<input class="Wdate f_left input-smaller96 mr5" type="text" placeholder="请选择日期" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'searchEndtimeStr\')}'})" name="searchBegintimeStr" id="searchBegintimeStr" value='<date:date value ="${saleorder.searchBegintime}" format="yyyy-MM-dd"/>'>
                    <div class="gang">-</div>
                    <input class="Wdate f_left input-smaller96" type="text" placeholder="请选择日期" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'searchBegintimeStr\')}'})" name="searchEndtimeStr" id="searchEndtimeStr" value='<date:date value ="${saleorder.searchEndtime}" format="yyyy-MM-dd"/>'>
				</li>
			</ul>
			<div class="tcenter">
				<span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" id="searchSpan">搜索</span>
				<span class="bt-small bg-light-blue bt-bg-style mr20" onclick="reset();">重置</span>
				<!-- <span class="bt-small bg-light-blue bt-bg-style" onclick="exportFinanceSaleOrderList();">导出列表</span> -->
				<!-- <span class="bt-small bg-light-blue bt-bg-style"  onclick="exportFinanceSaleOrderDetailList();">导出明细</span> -->
			</div>
		</form>
	</div>
	<div class="content">
		<div class="fixdiv">
			<div style="width:1752px;" class='superdiv'>		
				<table class="table table-bordered table-striped table-condensed table-centered">
					<thead>
						<tr>
							<th class="wid4">序号</th>
							<th class="wid11">订单号</th>
							<th class="wid20">客户名称</th>
							<th class="wid10">生效日期</th>
							<th class="wid8">销售部门</th>
							<th class="wid8">归属销售</th>
							<th class="wid14">票种</th>
							<th class="wid5">直发</th>
							<th class="wid9">订单原金额</th>
							<th class="wid9">订单实际金额</th>
							<th class="wid9">客户实付金额</th>
							<th class="wid9">未收金额</th>
							<th class="wid9">未还帐期款</th>
							<th class="wid6">收款状态</th>
							<th class="wid6">开票状态</th>
							<th class="wid6">发货状态</th>
							<th class="wid6">收货状态</th>
							<th class="wid6">售后状态</th>
							<th class="wid10">是否提前采购</th>
							<th class="wid14">开票备注</th>
							<th class="wid6">可否开票</th>
						</tr>
					</thead>
					<tbody>
						<c:set var="pageAmount" value="0.00"></c:set>
						<c:forEach var="list" items="${saleorderList}" varStatus="num">
							<tr>
								<td>${num.count}</td>
								<td>
									<c:choose>
										<c:when test="${list.status eq 0}">
											<span class="orangecircle"></span>
										</c:when>
										<c:when test="${list.status eq 1}">
											<span class="greencircle"></span>
										</c:when>
										<c:when test="${list.status eq 2}">
											<span class="bluecircle"></span>
										</c:when>
										<c:when test="${list.status eq 3}">
											<span class="greycircle"></span>
										</c:when>
									</c:choose>
									<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewinvoicesaleorder${list.saleorderId}","link":"./finance/invoice/viewSaleorder.do?saleorderId=${list.saleorderId}","title":"订单信息"}'>${list.saleorderNo}</a>
								</td>
								<td>
									<a class="addtitle" href="javascript:void(0);" tabtitle='{"num":"viewcustomer${list.traderId}", "link":"./trader/customer/baseinfo.do?traderId=${list.traderId}", "title":"客户信息"}'>${list.traderName}</a>
								</td>
								<td>
									<c:if test="${list.validStatus eq 1}">
										<date:date value ="${list.validTime}" format="yyyy-MM-dd"/>
									</c:if>
								</td>
								<td>${list.salesDeptName}</td>
								<td>${list.optUserName}</td>
								<td>
									<c:if test="${!empty list.invoiceType}">
										<c:forEach var="invoice" items="${invoiceTypeList}">
											<c:if test="${invoice.sysOptionDefinitionId eq list.invoiceType}">${invoice.title}</c:if>
										</c:forEach>
									</c:if>
								</td>
								<td>
									<c:choose>
										<c:when test="${list.deliveryDirect eq 0}">
											否
										</c:when>
										<c:when test="${list.deliveryDirect eq 1}">
											<span style="color: red">是</span>
										</c:when>
									</c:choose>
								</td>
								<td>
									<fmt:formatNumber type="number" value="${list.totalAmount}" pattern="0.00" maxFractionDigits="2" />
									<c:set var="pageAmount" value="${list.totalAmount + pageAmount}"></c:set>
								</td>
								<td><fmt:formatNumber type="number" value="${list.realAmount}" pattern="0.00" maxFractionDigits="2" /></td>
								<td><fmt:formatNumber type="number" value="${list.paymentAmount}" pattern="0.00" maxFractionDigits="2" /></td>
								<td><fmt:formatNumber type="number" value="${list.realAmount-list.paymentAmount}" pattern="0.00" maxFractionDigits="2" /></td>

								<c:choose>
									<c:when test="${!empty capitalBillList}">
										<c:set var="capitalNum" value="0"></c:set>
										<c:forEach var="capital" items="${capitalBillList}">
											<c:if test="${list.saleorderId eq capital.saleorderId}">
												<c:set var="capitalNum" value="${capitalNum+1}"></c:set>
												<!-- changed by Tomcat.Hui 2019/9/5 10:44 .Desc: VDERP-1053 每笔订单做实际金额运算. start -->
												<%--<td><fmt:formatNumber type="number" value="${capital.paymentAmount + capital.periodAmount - capital.lackAccountPeriodAmount - capital.refundBalanceAmount}" pattern="0.00" maxFractionDigits="2" /></td>--%>
												<!-- changed by Tomcat.Hui 2019/9/5 10:44 .Desc: VDERP-1053 每笔订单做实际金额运算. end -->
												<%--<td><fmt:formatNumber type="number" value="${capital.paymentAmount + capital.periodAmount - capital.lackAccountPeriodAmount}" pattern="0.00" maxFractionDigits="2" /></td>--%>
												<%--<td><span style="color:red;"><fmt:formatNumber type="number" value="${list.totalAmount - (capital.paymentAmount + capital.periodAmount - capital.lackAccountPeriodAmount)}" pattern="0.00" maxFractionDigits="2" /></span></td>--%>
												<td><fmt:formatNumber type="number" value="${capital.lackAccountPeriodAmount}" pattern="0.00" maxFractionDigits="2" /></td>
											</c:if>
										</c:forEach>
										<c:if test="${capitalNum == 0}">
											<%--<td></td>--%>
											<%--<td></td>--%>
											<td></td>
										</c:if>
									</c:when>
									<c:otherwise>
										<%--<td></td>--%>
										<%--<td></td>--%>
										<td></td>
									</c:otherwise>
								</c:choose>
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
										<c:when test="${list.invoiceStatus eq 0}">
											未开票
										</c:when>
										<c:when test="${list.invoiceStatus eq 1}">
											部分开票
										</c:when>
										<c:when test="${list.invoiceStatus eq 2}">
											全部开票
										</c:when>
									</c:choose>
								</td>
								<td>
									<c:choose>
										<c:when test="${list.deliveryStatus eq 0}">
											未发货
										</c:when>
										<c:when test="${list.deliveryStatus eq 1}">
											部分发货
										</c:when>
										<c:when test="${list.deliveryStatus eq 2}">
											全部发货
										</c:when>
									</c:choose>
								</td>
								<td>
									<c:choose>
										<c:when test="${list.arrivalStatus eq 0}">
											未收货
										</c:when>
										<c:when test="${list.arrivalStatus eq 1}">
											部分收货
										</c:when>
										<c:when test="${list.arrivalStatus eq 2}">
											全部收货
										</c:when>
									</c:choose>
								</td>
								<td>
									<c:choose>
										<c:when test="${list.serviceStatus eq 0}">
											无
										</c:when>
										<c:when test="${list.serviceStatus eq 1}">
											售后中
										</c:when>
										<c:when test="${list.serviceStatus eq 2}">
											售后完成
										</c:when>
										<c:when test="${list.serviceStatus eq 3}">
											售后关闭
										</c:when>
									</c:choose>
								</td>
								<td>
									<c:choose>
										<c:when test="${list.haveAdvancePurchase eq 0}">
											否
										</c:when>
										<c:otherwise>
											是										
										</c:otherwise>
									</c:choose>
								</td>
								<td>${list.invoiceComments}</td>
								<td>
									<c:choose>
										<c:when test="${list.isOpenInvoice eq 1 or list.isOpenInvoice eq 3}">
											是
										</c:when>
										<c:otherwise>
											<span style="color: red">否</span>
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
		<div>
       	<tags:page page="${page}"/>
       	<div class="clear"></div>
       	<div class="fixtablelastline">
			【全部结果 条目：${page.totalRecord} 总金额：<fmt:formatNumber type="number" value="${saleorder.allTotalAmount == null ? 0: saleorder.allTotalAmount}" pattern="0.00" maxFractionDigits="2" />】
			【本页统计 条目：${fn:length(saleorderList) > page.pageSize ? page.pageSize : fn:length(saleorderList)} 总金额：<fmt:formatNumber type="number" value="${pageAmount}" pattern="0.00" maxFractionDigits="2" />】
		</div>
		</div>
	</div>
<%@ include file="../../common/footer.jsp"%>
