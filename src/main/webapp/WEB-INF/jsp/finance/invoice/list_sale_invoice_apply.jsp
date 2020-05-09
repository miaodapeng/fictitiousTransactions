<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="开票申请列表" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%=basePath%>static/js/finance/invoice/list_sale_invoice_apply.js?rnd=<%=Math.random()%>'></script>

	<div class="main-container">
		<div class="list-pages-search">
			<form method="post" id="search" action="<%=basePath%>finance/invoice/getSaleInvoiceApplyListPage.do">
				<ul>
					<li>
						<label class="infor_name">是否标记</label>
						<select class="input-middle" name="isSign" id="isSign">
							<option value="-1">全部</option>
							<option <c:if test="${invoiceApply.isSign eq 0}">selected</c:if> value="0">未标记</option>
							<option <c:if test="${invoiceApply.isSign eq 1}">selected</c:if> value="1">已标记</option>
						</select>
					</li>
					<li>
						<label class="infor_name">集中开票</label>
						<select class="input-middle" name="isCollect" id="isCollect">
							<option value="-1">全部</option>
							<option <c:if test="${invoiceApply.isCollect eq 0}">selected</c:if> value="0">否</option>
							<option <c:if test="${invoiceApply.isCollect eq 1}">selected</c:if> value="1">是</option>
						</select>
					</li>
					<li>
						<label class="infor_name">客户等级</label>
						<select class="input-middle" name="customerLevel" id="customerLevel">
							<option value="">全部</option>
							<c:forEach var="list" items="${customerLevelList}" varStatus="status">
								<option value="${list.sysOptionDefinitionId}" <c:if test="${list.sysOptionDefinitionId eq invoiceApply.customerLevel}">selected</c:if>>${list.title}</option>
							</c:forEach>
						</select>
					</li>

					<li>
						<label class="infor_name">开票方式</label>
						<select class="input-middle" name="isAuto" id="isAuto">
							<option value="">全部</option>
							<option <c:if test="${invoiceApply.isAuto eq 2}">selected</c:if> value="2">自动开票</option>
							<option <c:if test="${invoiceApply.isAuto eq 1}">selected</c:if> value="1">手动开票</option>
							<option <c:if test="${invoiceApply.isAuto eq 3}">selected</c:if> value="3">电子发票</option>
						</select>
					</li>
					<li>
						<label class="infor_name">发票类型</label>
						<select class="input-middle" name="invoiceType" id="invoiceType">
							<option value="">全部</option>
							<c:forEach var="list" items="${invoiceTypeList}" varStatus="status">
								<option value="${list.sysOptionDefinitionId}" <c:if test="${list.sysOptionDefinitionId eq invoiceApply.invoiceType}">selected</c:if>>${list.title}</option>
							</c:forEach>
						</select>
					</li>
					<li>
						<label class="infor_name">提前开票</label>
						<select class="input-middle" name="isAdvance" id="isAdvance">
							<option value="">全部</option>
							<option <c:if test="${invoiceApply.isAdvance eq 0}">selected</c:if> value="0">否</option>
							<option <c:if test="${invoiceApply.isAdvance eq 1}">selected</c:if> value="1">是</option>
						</select>
					</li>
					<li>
						<label class="infor_name">订单号</label>
						<input type="text" class="input-middle" name="saleorderNo" id="saleorderNo" value="${invoiceApply.saleorderNo}" />
					</li>
					<li>
						<label class="infor_name">客户公司</label>
						<input type="text" class="input-middle" name="traderName" id="traderName" value="${invoiceApply.traderName}" />
					</li>
					<li>
						<label class="infor_name">销售部门</label>
						<select class="input-middle" name="orgId" id="orgId">
							<option value="">全部</option>
							<c:forEach var="list" items="${searchOrgList}" varStatus="orgNum">
								<option <c:if test="${invoiceApply.orgId eq list.orgId}">selected</c:if> value="${list.orgId}">${list.orgName}</option>
							</c:forEach>
						</select>
					</li>
					<li>
						<label class="infor_name">归属销售</label>
						<select class="input-middle" name="traderUserId" id="traderUserId">
							<option value="">全部</option>
							<c:forEach var="list" items="${searchTraderUserList}" varStatus="num">
								<option <c:if test="${invoiceApply.traderUserId eq list.userId}">selected</c:if> value="${list.userId}">${list.username}</option>
							</c:forEach>
						</select>
					</li>
					<li>
						<label class="infor_name">收款状态</label>
						<select class="input-middle" name="paymentStatus" id="paymentStatus">
							<option value="">全部</option>
							<option <c:if test="${invoiceApply.paymentStatus eq 0}">selected</c:if> value="0">未收款</option>
							<option <c:if test="${invoiceApply.paymentStatus eq 1}">selected</c:if> value="1">部分收款</option>
							<option <c:if test="${invoiceApply.paymentStatus eq 2}">selected</c:if> value="2">已收款</option>
						</select>
					</li>
					<li>
						<label class="infor_name">发货状态</label>
						<select class="input-middle" name="deliveryStatus" id="deliveryStatus">
							<option value="">全部</option>
							<option <c:if test="${invoiceApply.deliveryStatus eq 0}">selected</c:if> value="0">未发货</option>
							<option <c:if test="${invoiceApply.deliveryStatus eq 1}">selected</c:if> value="1">部分发货</option>
							<option <c:if test="${invoiceApply.deliveryStatus eq 2}">selected</c:if> value="2">全部发货</option>
						</select>
					</li>
					<li>
						<label class="infor_name">售后状态</label>
						<select class="input-middle" name="serviceStatus" id="serviceStatus">
							<option value="">全部</option>
							<option <c:if test="${invoiceApply.serviceStatus eq 0}">selected</c:if> value="0">无售后</option>
							<option <c:if test="${invoiceApply.serviceStatus eq 1}">selected</c:if> value="1">售后中</option>
							<option <c:if test="${invoiceApply.serviceStatus eq 2}">selected</c:if> value="2">售后完成</option>
							<option <c:if test="${invoiceApply.serviceStatus eq 3}">selected</c:if> value="3">售后关闭</option>
						</select>
					</li>

					<!-- add by Tomcat.Hui 2019/9/3 19:34 .Desc:VDERP-1214 开票申请界面优化 . start -->
					<li>
						<label class="infor_name">开票备注</label>
						<select class="input-middle" name="invoiceComments" id="invoiceComments">
							<!-- add by Tomcat.Hui 2019/10/17 19:34 .Desc:VDERP-1342 财务管理-开票申请列表：筛选问题 . start -->
							<option value="-1">全部</option>
							<!-- add by Tomcat.Hui 2019/10/17 19:34 .Desc:VDERP-1342 财务管理-开票申请列表：筛选问题 . start -->
							<option <c:if test="${invoiceApply.invoiceComments eq 0}">selected</c:if> value="0">有</option>
							<option <c:if test="${invoiceApply.invoiceComments eq 1}">selected</c:if> value="1">无</option>
						</select>
					</li>
					<!-- add by Tomcat.Hui 2019/9/3 19:34 .Desc:VDERP-1214 开票申请界面优化 . end -->

					<!--  add by Tomcat.Hui 2019/11/23 9:42 .Desc:  VDERP-1325 分批开票 增加筛选字段. start -->
					<li>
						<label class="infor_name">申请方式</label>
						<select class="input-middle" name="applyMethod" id="applyMethod">
							<option value="-1">全部</option>
							<option <c:if test="${invoiceApply.applyMethod eq 0}">selected</c:if> value="0">销售手动申请</option>
							<option <c:if test="${invoiceApply.applyMethod eq 1}">selected</c:if> value="1">系统自动推送</option>
						</select>
					</li>
					<!--  add by Tomcat.Hui 2019/11/23 9:42 .Desc: VDERP-1325 分批开票 增加筛选字段. end -->

				</ul>
				<div class="tcenter">
					<span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" id="searchSpan">搜索</span>
					<span class="bt-small bg-light-blue bt-bg-style mr20" onclick="resetPage();">重置</span>
					<span class="bt-small bg-light-blue bt-bg-style" onclick="exportInvoiceApplyList()">导出列表</span>

					<span class="bt-small bg-light-blue bt-bg-style pop-new-data"
					  layerparams='{"width":"700px","height":"550px","title":"集中开票客户维护","link":"./getCollectInvoiceTraderName.do"}'>
						集中开票客户维护
					</span>
				<%--<c:set var="pj" value=""></c:set>
				<c:forEach var="pjlist" items="${applyAmountList}">
					<c:set var="pj" value="${pj}${pjlist.saleorderNo},"></c:set>
				</c:forEach>
					<c:set var="il" value=""></c:set>
					<c:forEach var="illist" items="${applyAmountList}">
						<c:set var="il" value="${il}${illist.invoiceApplyId},"></c:set>
					</c:forEach>--%>
					<span class="bg-light-blue bt-bg-style bt-small pop-new-data" layerParams='{"width":"400px","height":"200px","title":"批量标记","link":"./uplodebatchsign.do"}'>批量标记</span>
		</div>
			</form>
		</div>
		<div class="list-page">
			<div class="fixdiv">
				<div style="width:1852px;" class='superdiv'>
					<table class="table table-bordered table-striped table-condensed table-centered" id="invoice_apply_list_tab">
						<thead>
							<tr>
								<th class="wid6">
									<span style="vertical-align:middle;">全选&nbsp;</span>
									<input type="checkbox" name="checkAllOpt" style="vertical-align:middle;" onchange="checkAllOpt(this);">
								</th>
								<th class="wid4">序号</th>
								<th class="wid12">订单号</th>
								<th class="wid22">客户名称</th>
								<th class="wid7">集中开票</th>
								<th class="wid8">客户等级</th>
								<th class="wid12">销售部门</th>
								<th class="wid8">归属销售</th>
								<th class="wid12">票种</th>
								<th class="wid7">开票方式</th>
								<th class="wid7">提前开票</th>
								<th class="wid10">订单实际金额</th>
								<th class="wid8">收款状态</th>
								<th class="wid8">发货状态</th>
								<th class="wid8">收货状态</th>
								<th class="wid8">售后状态</th>
								<th class="wid7">是否标记</th>
								<th class="wid14">操作</th>
								<th class="wid14">满足开票条件时间</th>
								<th class="wid8">申请人</th>

								<!-- add by Tomcat.Hui 2019/9/3 19:34 .Desc:VDERP-1214 开票申请界面优化 . start -->
								<!-- 去掉字段
								<th class="wid14">审核时间</th>
								<th class="wid8">审核人</th>
								-->

								<!--  add by Tomcat.Hui 2019/11/23 9:42 .Desc:  VDERP-1325 分批开票 增加字段. start -->
								<th class="wid12">申请开票金额</th>
								<th class="wid12">申请方式</th>
								<!--  add by Tomcat.Hui 2019/11/23 9:42 .Desc:  VDERP-1325 分批开票 增加字段. end -->

								<th class="wid22">开票备注</th>
								<!-- add by Tomcat.Hui 2019/9/3 19:34 .Desc:VDERP-1214 开票申请界面优化 . end -->

								<th class="wid8">修改税率</th>
								<!--  add by Tomcat.Hui 2019/11/23 9:42 .Desc:  VDERP-1325 分批开票 查看申请商品. start -->
								<th class="wid12">查看申请商品</th>
								<!--  add by Tomcat.Hui 2019/11/23 9:42 .Desc:  VDERP-1325 分批开票 查看申请商品. end -->
							</tr>
						</thead>
						<tbody>
							<c:set var="pageAmount" value="0"></c:set><!-- 当前页总额 -->
							<c:set var="pageNum" value="0"></c:set><!-- 当前页记录数 -->
							<c:forEach var="list" items="${openInvoiceApplyList}" varStatus="num">
								<c:set var="pageNum" value="${pageNum + 1}"></c:set>
								<tr>
									<td><input type="checkbox" name="checkName" value="${list.invoiceApplyId}" isSign="${list.isSign}" isAuto="${list.isAuto}" onchange="checkedOnly(this)" amount="${list.totalAmount}"/></td>
									<td>${num.count}</td>
									<td>
										<a class="addtitle" href="javascript:void(0);" tabtitle='{"num":"viewfinancesaleorder${list.relatedId}","link":"./finance/invoice/viewSaleorder.do?saleorderId=${list.relatedId}","title":"订单信息"}'>${list.saleorderNo}</a>
									</td>
									<td>
										<a class="addtitle" href="javascript:void(0);" tabtitle='{"num":"viewfinancecustomer${list.traderId}", "link":"./trader/customer/baseinfo.do?traderId=${list.traderId}", "title":"客户信息"}'>${list.traderName}</a>
									</td>
									<td>
										<c:if test="${list.isCollect eq 0}">否</c:if>
										<c:if test="${list.isCollect eq 1}"><font color="red">是</font></c:if>
									</td>
									<td>
										<c:forEach var="clList" items="${customerLevelList}" varStatus="num">
											<c:if test="${list.customerLevel eq clList.sysOptionDefinitionId}">${clList.title}</c:if>
										</c:forEach>
									</td>
									<td>
										<c:forEach var="org" items="${traderUserList}" varStatus="num">
											<c:if test="${list.traderId eq org.traderId}">${org.orgName}</c:if>
										</c:forEach>
									</td>
									<td>
										<c:forEach items="${traderUserList}" var="user" varStatus="status">
											<c:if test="${user.traderId eq list.traderId}">
												${user.username}
											</c:if>
										</c:forEach>
									</td>
									<td>${list.invoiceTypeStr}</td>
									<td>
										<c:choose>
											<c:when test="${list.isAuto eq 1}"><span style="color:red;">手动</span></c:when>
											<c:when test="${list.isAuto eq 2}">自动</c:when>
											<c:when test="${list.isAuto eq 3}">电子</c:when>
											<c:otherwise>--</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when test="${list.isAdvance eq 0}">否</c:when>
											<c:otherwise>是</c:otherwise>
										</c:choose>
									</td>
									<td>
										<%--<c:set var="pageAmount" value="${list.totalAmount + pageAmount}"></c:set>--%>
										<fmt:formatNumber type="number" value="${list.totalAmount}" pattern="0.00" maxFractionDigits="2" />
									</td>
									<td>
										<c:choose>
											<c:when test="${list.paymentStatus eq 0}">未收款</c:when>
											<c:when test="${list.paymentStatus eq 1}">部分收款</c:when>
											<c:otherwise>已收款</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when test="${list.deliveryStatus eq 0}">未发货</c:when>
											<c:when test="${list.deliveryStatus eq 1}">部分发货</c:when>
											<c:otherwise>全部发货</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when test="${list.arrivalStatus eq 0}">未收货</c:when>
											<c:when test="${list.arrivalStatus eq 1}">部分收货</c:when>
											<c:otherwise>全部收货</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when test="${list.serviceStatus eq 0}">无</c:when>
											<c:when test="${list.serviceStatus eq 1}">售后中</c:when>
											<c:when test="${list.serviceStatus eq 2}">售后完成</c:when>
											<c:otherwise>售后关闭</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when test="${list.isSign eq 0}">未标记</c:when>
											<c:otherwise>已标记</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when test="${list.validStatus eq 0}">
												<!-- 此处审核费提前开票审核  和  提前开票申请已通过的记录，故：isAdvance为0，在js中作区分 -->
												<span class="pop-new-data delete"  layerparams='{"width":"600px","height":"220px","title":"确认审核","link":"./auditOpenInvoice.do?invoiceApplyId=${list.invoiceApplyId}&isAdvance=0"}'>驳回</span>
												<c:if test="${list.isAuto eq 3}">
													<button class="edit-user clcforbid" onclick="openEInvoice(${list.invoiceApplyId},${list.relatedId},'${formToken}');" id="openEinvoiceId">电子发票</button>
												</c:if>
											</c:when>
											<c:otherwise>--</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:if test="${list.creator eq 0}"><date:date value="${list.arrivalTime}" /></c:if>
										<c:if test="${list.creator ne 0}"><date:date value="${list.addTime}" /></c:if>
									</td>
									<td>
                                        <!--  add by Tomcat.Hui 2019/11/23 9:42 .Desc:  VDERP-1325 分批开票 开票人为空显示自动申请. start -->
                                        <c:choose>
                                            <c:when test="${list.applyMethod eq 1}">
                                                自动申请
                                            </c:when>
                                            <c:otherwise>
                                                <c:forEach varStatus="userNum" var="user" items="${userList}">
                                                    <c:if test="${list.creator eq user.userId}">
                                                        ${user.username}
                                                    </c:if>
                                                </c:forEach>
                                            </c:otherwise>
                                        </c:choose>
                                        <!--  add by Tomcat.Hui 2019/11/23 9:42 .Desc:   VDERP-1325 分批开票 开票人为空显示自动申请. end -->
									</td>
									<!--  add by Tomcat.Hui 2019/11/23 9:42 .Desc:  VDERP-1325 分批开票 增加字段. start -->
									<td><fmt:formatNumber type="number" value="${empty list.applyAmount ? list.totalAmount : list.applyAmount}" pattern="0.00" maxFractionDigits="2" /></td>
									<c:set var="pageAmount" value="${(empty list.applyAmount ? list.totalAmount : list.applyAmount) + pageAmount}"></c:set>
									<td>
										<c:if test="${list.applyMethod eq 0}">销售手动申请</c:if>
										<c:if test="${list.applyMethod eq 1}">系统自动推送</c:if>
									</td>
									<!--  add by Tomcat.Hui 2019/11/23 9:42 .Desc:  VDERP-1325 分批开票 增加字段. end -->

									<!-- add by Tomcat.Hui 2019/9/3 19:34 .Desc:VDERP-1214 开票申请界面优化 . start -->
									<td>
										${list.invoiceComments}
									</td>
									<!-- add by Tomcat.Hui 2019/9/3 19:34 .Desc:VDERP-1214 开票申请界面优化 . end -->

									<td>
										<span class="edit-user pop-new-data" layerparams='{"width":"600px","height":"220px","title":"修改税率",
											  "link":"<%=basePath%>order/saleorder/editOrderRatioInit.do?orderId=${list.relatedId}&invoiceType=${list.invoiceType}"}'>
											修改
										</span>
									</td>

									<!--  add by Tomcat.Hui 2019/11/23 9:42 .Desc:  VDERP-1325 分批开票 查看申请商品. start -->
									<td>
										<span class="edit-user pop-new-data" layerParams='{"width":"80%","height":"600px","title":"查看开票申请","link":"../../finance/invoice/selectInvoiceItems.do?relatedId=${list.relatedId}&invoiceApplyId=${list.invoiceApplyId}&editFlag=false"}' >查看开票申请</span>
									</td>
									<!--  add by Tomcat.Hui 2019/11/23 9:42 .Desc:  VDERP-1325 分批开票 查看申请商品. end -->
								</tr>
							</c:forEach>
							<c:if test="${empty openInvoiceApplyList}">
								<tr>
									<td colspan="22">
										<!-- 查询无结果弹出 --> 查询无结果！请尝试使用其他搜索条件。
									</td>
								</tr>
							</c:if>
						</tbody>
					</table>
				</div>
			</div>
			<div>
				<c:if test="${!empty openInvoiceApplyList}">
					<div class="table-style4 f_left" style="margin:0px;margin-left: 20px;">
						<div class="print-record">
							<span class="bt-border-style border-blue" onclick="invoiceSign(1);">标记</span>
						</div>
						<div class="print-record">
							<span class="bt-border-style border-blue" onclick="invoiceSign(0);">取消标记</span>
						</div>
						<div class="print-record">
							<span class="bt-border-style border-blue" onclick="invoiceBatch('${formToken}');">批量开票</span>
						</div>
					</div>
				</c:if>
				<tags:page page="${page}" />
				<div class="clear"></div>
				<div class="fixtablelastline">
					<span style="float:left">
						【已勾选 条目：<span id="checkNum">0</span> 总金额：<span id="checkAmount"><fmt:formatNumber type="number" value="0.00" pattern="0.00" maxFractionDigits="2" /></span>】
					</span>
					【全部结果 条目：${page.totalRecord} 总金额：<fmt:formatNumber type="number" value="${applyAmountMoney}" pattern="0.00" maxFractionDigits="2" />】
					【本页统计 条目：${pageNum} 总金额：<fmt:formatNumber type="number" value="${pageAmount}" pattern="0.00" maxFractionDigits="2" />】
				</div>
			</div>
		</div>
	</div>

<%@ include file="../../common/footer.jsp"%>
