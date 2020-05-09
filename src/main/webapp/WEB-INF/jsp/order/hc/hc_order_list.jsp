<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="耗材订单列表" scope="application" />
<%@ include file="../../common/common.jsp"%>
	<div class="searchfunc">
		<form method="post" id="search" action="<%=basePath%>order/hc/hcOrderListPage.do"	>
			<ul>
				<li>
					<label class="infor_name">订单号</label>
					<input type="text" class="input-middle" name="saleorderNo" id="saleorderNo" value="${saleorder.saleorderNo}"/>
				</li>
				<li>
                    <label class="infor_name">订货号</label>
                    <input type="text" class="input-middle" name="sku" id="sku" value="${saleorder.sku}"/>
                </li>
                <li>
                    <label class="infor_name">收款状态</label>
                    <select class="input-middle" name="paymentStatus" id="paymentStatus" onchange="submitForm('search')">
                        <option value="-1">全部</option>
                        <option <c:if test="${saleorder.paymentStatus eq 0}">selected</c:if> value="0">未收款</option>
                        <option <c:if test="${saleorder.paymentStatus eq 1}">selected</c:if> value="1">部分收款</option>
                        <option <c:if test="${saleorder.paymentStatus eq 2}">selected</c:if> value="2">全部收款</option>
                    </select>
                </li>
                <li>
                    <label class="infor_name">开票申请</label>
                    <select class="input-middle" name="openInvoiceApply" id="openInvoiceApply" onchange="submitForm('search')">
                        <option value="-1">全部</option>
                        <option <c:if test="${saleorder.openInvoiceApply eq 1}">selected</c:if> value="1">审核中</option>
                    </select>
                </li>
                <li>
                    <label class="infor_name">客户名称</label>
                    <input type="text" class="input-middle" name="traderName" id="traderName" value="${saleorder.traderName}"/>
                </li>
                <li>
                    <label class="infor_name">联系人</label>
                    <input type="text" class="input-middle" placeholder="请填写联系人或联系方式" name="traderContact" id="traderContact" value="${saleorder.traderContact}"/>
                </li>
                <li>
                    <label class="infor_name">提前采购</label>
                    <select class="input-middle" name="haveAdvancePurchase" id="haveAdvancePurchase" onchange="submitForm('search')">
                        <option value="-1">全部</option>
                        <option <c:if test="${saleorder.haveAdvancePurchase eq 0}">selected</c:if> value="0">否</option>
                        <option <c:if test="${saleorder.haveAdvancePurchase eq 1}">selected</c:if> value="1">是</option>
                    </select>
                </li>
                <li>
                    <label class="infor_name">直发</label>
                    <select class="input-middle" name="deliveryDirect" id="deliveryDirect" onchange="submitForm('search')">
                        <option value="-1">全部</option>
                        <option <c:if test="${saleorder.deliveryDirect eq 0}">selected</c:if> value="0">否</option>
                        <option <c:if test="${saleorder.deliveryDirect eq 1}">selected</c:if> value="1">是</option>
                    </select>
                </li>
                <li>
                    <label class="infor_name">产品名称</label>
                    <input type="text" class="input-middle" name="goodsName" id="goodsName" value="${saleorder.goodsName}"/>
                </li>
				<li>
					<label class="infor_name">订单状态</label>
					<select class="input-middle" name="status" id="status" onchange="submitForm('search')">
						<option value="-1">全部</option>
						<option <c:if test="${saleorder.status eq 0}">selected</c:if> value="0">待确认</option>
						<option <c:if test="${saleorder.status eq 1}">selected</c:if> value="1">进行中</option>
						<option <c:if test="${saleorder.status eq 2}">selected</c:if> value="2">已完结</option>
						<option <c:if test="${saleorder.status eq 3}">selected</c:if> value="3">已关闭</option>
					</select>
				</li>
				<li>
                    <label class="infor_name">发货状态</label>
                    <select class="input-middle" name="deliveryStatus" id="deliveryStatus" onchange="submitForm('search')">
                        <option value="-1">全部</option>
                        <option <c:if test="${saleorder.deliveryStatus eq 0}">selected</c:if> value="0">未发货</option>
                        <option <c:if test="${saleorder.deliveryStatus eq 1}">selected</c:if> value="1">部分发货</option>
                        <option <c:if test="${saleorder.deliveryStatus eq 2}">selected</c:if> value="2">全部发货</option>
                    </select>
                </li>
                <li>
                    <label class="infor_name">锁定状态</label>
                    <select class="input-middle" name="lockedStatus" id="lockedStatus" onchange="submitForm('search')">
                        <option value="-1">全部</option>
                        <option <c:if test="${saleorder.lockedStatus eq 0}">selected</c:if> value="0">未锁定</option>
                        <option <c:if test="${saleorder.lockedStatus eq 1}">selected</c:if> value="1">已锁定</option>
                    </select>
                </li>
                <li>
                    <label class="infor_name">品牌</label>
                    <input type="text" class="input-middle" name="brandName" id="brandName" value="${saleorder.brandName}"/>
                </li>
				<li>
					<label class="infor_name">生效状态</label>
					<select class="input-middle" name="validStatus" id="validStatus" onchange="submitForm('search')">
						<option value="-1">全部</option>
						<option <c:if test="${saleorder.validStatus eq 0}">selected</c:if> value="0">未生效</option>
						<option <c:if test="${saleorder.validStatus eq 1}">selected</c:if> value="1">已生效</option>
					</select>
				</li>
				
				<li>
					<label class="infor_name">收货状态</label>
					<select class="input-middle" name="arrivalStatus" id="arrivalStatus" onchange="submitForm('search')">
						<option value="-1">全部</option>
						<option <c:if test="${saleorder.arrivalStatus eq 0}">selected</c:if> value="0">未收货</option>
						<option <c:if test="${saleorder.arrivalStatus eq 1}">selected</c:if> value="1">部分收货</option>
						<option <c:if test="${saleorder.arrivalStatus eq 2}">selected</c:if> value="2">全部收货</option>
					</select>
				</li>
				<li>
                    <label class="infor_name">售后状态</label>
                    <select class="input-middle" name="serviceStatus" id="serviceStatus" onchange="submitForm('search')">
                        <option value="-1">全部</option>
                        <option <c:if test="${saleorder.serviceStatus eq 0}">selected</c:if> value="0">无</option>
                        <option <c:if test="${saleorder.serviceStatus eq 1}">selected</c:if> value="1">售后中</option>
                        <option <c:if test="${saleorder.serviceStatus eq 2}">selected</c:if> value="2">售后完成</option>
                        <option <c:if test="${saleorder.serviceStatus eq 3}">selected</c:if> value="3">售后关闭</option>
                    </select>
                </li>
                <li>
                    <label class="infor_name">型号</label>
                    <input type="text" class="input-middle" name="model" id="model" value="${saleorder.model}"/>
                </li>
                <li>
                    <label class="infor_name">审核状态</label>
                    <select class="input-middle" name="verifyStatus" id="" onchange="submitForm('search')">
                        <option value="">全部</option>
                        <option <c:if test="${saleorder.verifyStatus eq 3}">selected</c:if> value="3">待审核</option>
                        <option <c:if test="${saleorder.verifyStatus eq 0}">selected</c:if> value="0">审核中</option>
                        <option <c:if test="${saleorder.verifyStatus eq 1}">selected</c:if> value="1">审核通过</option>
                        <option <c:if test="${saleorder.verifyStatus eq 2}">selected</c:if> value="2">审核未通过</option>
                    </select>
                </li>
				<li>
					<label class="infor_name">开票状态</label>
					<select class="input-middle" name="invoiceStatus" id="invoiceStatus" onchange="submitForm('search')">
						<option value="-1">全部</option>
						<option <c:if test="${saleorder.invoiceStatus eq 0}">selected</c:if> value="0">未开票</option>
						<option <c:if test="${saleorder.invoiceStatus eq 1}">selected</c:if> value="1">部分开票</option>
						<option <c:if test="${saleorder.invoiceStatus eq 2}">selected</c:if> value="2">全部开票</option>
					</select>
				</li>				
				<li>
					<div class="infor_name specialinfor">
						<select name="searchDateType" id="searchDateType" style="width:75px;" onchange="submitForm('search')">
							<option value="1" <c:if test="${saleorder.searchDateType == 1}">selected="selected"</c:if> >创建时间</option>
							<option value="2" <c:if test="${saleorder.searchDateType == 2}">selected="selected"</c:if> >生效时间</option>
							<option value="3" <c:if test="${saleorder.searchDateType == 3}">selected="selected"</c:if> >付款时间</option>
							<option value="4" <c:if test="${saleorder.searchDateType == 4}">selected="selected"</c:if> >发货时间</option>
							<option value="5" <c:if test="${saleorder.searchDateType == 5}">selected="selected"</c:if> >收货时间</option>
							<option value="6" <c:if test="${saleorder.searchDateType == 6}">selected="selected"</c:if> >开票时间</option>
						</select>
					</div>
					<input class="Wdate f_left input-smaller96 mr5" autocomplete="off" type="text" placeholder="请选择日期" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'searchEndtimeStr\')}'})" name="searchBegintimeStr" id="searchBegintimeStr" value='<date:date value ="${saleorder.searchBegintime}" format="yyyy-MM-dd"/>'>
                    <div class="gang">-</div>
                    <input class="Wdate f_left input-smaller96" autocomplete="off" type="text" placeholder="请选择日期" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'searchBegintimeStr\')}'})" name="searchEndtimeStr" id="searchEndtimeStr" value='<date:date value ="${saleorder.searchEndtime}" format="yyyy-MM-dd"/>'>
				</li>
				<li>
					<label class="infor_name">资质状态</label>
					<!-- 0 未审核 1 待审核 2 审核通过 3 审核不通过 -->
					<select class="input-middle" name="traderStatus" id="traderStatus" onchange="submitForm('search')">
						<option value="-1">全部</option>
						<option <c:if test="${saleorder.traderStatus eq 0}">selected</c:if> value="0">未认证</option>
						<option <c:if test="${saleorder.traderStatus eq 1}">selected</c:if> value="1">审核中</option>
						<option <c:if test="${saleorder.traderStatus eq 2}">selected</c:if> value="2">审核通过</option>
						<option <c:if test="${saleorder.traderStatus eq 3}">selected</c:if> value="3">审核未通过</option>
					</select>
				</li>
				<li>
					<label class="infor_name">归属部门</label>
					<select class="input-middle" name="orgId" id="orgId" onchange="getUserList();">
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
					<label class="infor_name">优惠类型</label>
					<select class="input-middle" name="discountTypeId" id="discountTypeId" onchange="submitForm('search')">
						<option value="-1" selected="selected">全部</option>
						<option <c:if test="${saleorder.discountTypeId eq 1}">selected</c:if> value="1">限购活动</option>
						<option <c:if test="${saleorder.discountTypeId eq 2}">selected</c:if> value="2">满减券</option>
					</select>
				</li>
			</ul>
			<div class="tcenter">
				<c:if test="${not empty traderId}">
					<input type="hidden" name="traderId" value="${traderId }" >
				</c:if>
				<span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" id="searchSpan">搜索</span>
				<span class="bt-small bg-light-blue bt-bg-style mr20" onclick="formReset('search');">重置</span>
			</div>
		</form>
	</div>
	<div class="content">
		<div class="fixdiv">
			<div class="superdiv" style='width:2000px;'>
				<table
					class="table table-bordered table-striped table-condensed table-centered">
					<thead>
						<tr>
							<th class="wid10">订单号</th>
							<th class="wid16">客户名称</th>
							<th class="wid8">归属销售</th>
							<th class="wid8">订单原金额</th>
							<th class="wid8">订单实际金额</th>
							<th class="wid8">客户实付金额</th>
							<th class="wid8">优惠金额</th>
							<th class="wid8">资质状态</th>
							<th class="wid6">收款状态</th>
							<th class="wid6">采购状态</th>
							<th class="wid6">发货状态</th>
							<th class="wid6">收货状态</th>
							<th class="wid5">可否开票</th>
							<th class="wid6">开票状态</th>
							<th class="wid10">订单创建时间</th>
							<th class="wid6">直发</th>
							<th class="wid6">售后状态</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="list" items="${saleorderList}" varStatus="num">
							<tr>
							    <!-- 
								<td>${num.count}</td>
							     -->
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
									<c:set var="costPriceEmpty" value="0"></c:set>
										<c:if test="${list.costUserIdsList!=null && null!=list.costUserIdsList}">
											<c:forEach items="${list.costUserIdsList}" var="costUserIds">
												<c:if test="${costUserIds == loginUser.userId}">
													<c:set var="costPriceEmpty" value="1"></c:set>
												</c:if>
											</c:forEach>
										</c:if>
									<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"hc_view_saleorder${list.saleorderId}","link":"./order/hc/hcOrderDetailsPage.do?saleorderId=${list.saleorderId}","title":"订单信息"}'>${list.saleorderNo}</a>${list.validStatus == 0 && list.verifyStatus != 1 && shenhe == 1 ?"<font color='red'>[审]</font>":""}${costPriceEmpty == 1 ?"<font color='red'>[填]</font>":""}
								</td>
								<td>
									<a class="addtitle" href="javascript:void(0);" tabtitle='{"num":"viewcustomer${list.traderId}", "link":"./trader/customer/baseinfo.do?traderId=${list.traderId}", "title":"客户信息"}'>${list.traderName}</a>
								</td>
								<!-- 订单归属 -->
								<td>${list.ownerUserName}</td>
								<td><fmt:formatNumber type="number" value="${list.totalAmount}" pattern="0.00" maxFractionDigits="2" /></td>
								<td><fmt:formatNumber type="number" value="${list.realAmount}" pattern="0.00" maxFractionDigits="2" /></td>
								<td><fmt:formatNumber type="number" value="${list.paymentAmount}" pattern="0.00" maxFractionDigits="2" /></td>
								<!-- 优惠金额 -->
								<td><fmt:formatNumber type="number" value="${null == list.couponAmount ? 0 : list.couponAmount}" pattern="0.00" maxFractionDigits="2" /></td>
								<!-- 资质状态 -->
								<td>
									<c:choose>
										<c:when test="${list.traderStatus eq 0}">
											未认证
										</c:when>
										<c:when test="${list.traderStatus eq 1}">
											审核中
										</c:when>
										<c:when test="${list.traderStatus eq 2}">
											审核通过
										</c:when>
										<c:when test="${list.traderStatus eq 3}">
											审核未通过
										</c:when>
										<c:otherwise>
											-
										</c:otherwise>
									</c:choose>
								</td>
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
										<c:when test="${list.purchaseStatus eq 0}">
											未采购
										</c:when>
										<c:when test="${list.purchaseStatus eq 1}">
											部分采购
										</c:when>
										<c:when test="${list.purchaseStatus eq 2}">
											已采购
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
										<c:when test="${list.isOpenInvoice eq 1 or list.isOpenInvoice eq 3}">
											是
										</c:when>
										<c:otherwise>
											<span style="color: red">否</span>
										</c:otherwise>
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
									<date:date value ="${list.addTime}" format="yyyy-MM-dd HH:mm:ss"/>
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
								<!--  
								<td>
									<c:choose>
										<c:when test="${list.contractStatus eq 3}">
											待审核
										</c:when>
										<c:when test="${list.contractStatus eq 0}">
											审核中
										</c:when>
										<c:when test="${list.contractStatus eq 1}">
											<span style="color: green;">合格</span>
										</c:when>
										<c:when test="${list.contractStatus eq 2}">
											<span style="color: red;">不合格</span>
										</c:when>
										<c:otherwise>
											--
										</c:otherwise>
									</c:choose>
								</td>
								-->
							</tr>
						</c:forEach>
						<c:if test="${empty saleorderList}">
			          		<tr>
			          			<td colspan="13">查询无结果！请尝试使用其他搜索条件。</td>
			          		</tr>
				       	</c:if>
					</tbody>
				</table>
			</div>
		</div>
       	<tags:page page="${page}"/>
	</div>
<script type="text/javascript" src='<%= basePath %>static/js/order/hc/hc_list.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>
