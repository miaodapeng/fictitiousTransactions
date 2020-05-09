<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="报价咨询" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%=basePath%>/static/js/order/quote/edit_quote_detail.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src='<%=basePath%>/static/js/closable-tab.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src='<%=basePath%>/static/js/order/quote/view_quote_consult.js?rnd=<%=Math.random()%>'></script>
<div class="content mt10">
	<!-- ----------------------------------基本信息 ------------------------------------- -->
	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">基本信息</div>
		</div>
		<table
			class="table table-bordered table-striped table-condensed table-centered">
			<tbody>
				<tr>
					<td class="table-smaller">报价单号</td>
					<td>${quote.quoteorderNo}</td>
					<td class="table-smaller">报价单状态</td>
					<td><c:choose>
							<c:when test="${quote.validStatus eq 1}">
								<span style="color: green;">已生效</span>
							</c:when>
							<c:otherwise>
								<span style="color: red;">未生效</span>
							</c:otherwise>
						</c:choose></td>
				</tr>
				<tr>
					<td>创建者</td>
					<td>${quote.creatorName}</td>
					<td>创建时间</td>
					<td><date:date value="${quote.addTime}" /></td>
				</tr>
				<tr>
					<td>销售部门</td>
					<td>${quote.salesDeptName}</td>
					<%-- <td>销售人员</td>
						<td>${quote.salesName}</td> --%>
					<td>归属人员</td>
					<td>${quote.optUserName}</td>
				</tr>
				<tr>
					<td>商机编号</td>
					<td><a class="addtitle" href="javascript:void(0);"
						tabTitle='{"num":"view${quote.bussinessChanceId}",
								"link":"./order/bussinesschance/toSalesDetailPage.do?bussinessChanceId=${quote.bussinessChanceId}",
								"title":"商机详情"}'>${quote.bussinessChanceNo}
					</a></td>
					<td>商机时间</td>
					<td><date:date value="${quote.receiveTime}" /></td>
				</tr>
				<tr>
					<td>审核状态</td>
					<td>
						<c:choose>
							<c:when test="${quote.verifyStatus eq 0}">
								审核中
							</c:when>
							<c:when test="${quote.verifyStatus eq 1}">
								审核通过
							</c:when>
							<c:when test="${quote.verifyStatus eq 2}">
								审核不通过
							</c:when>
							<c:otherwise>
								--
							</c:otherwise>
						</c:choose>
					</td>
					<td>审核时间</td>
					<td><date:date value="${quote.verifyTime}" /></td>
				</tr>
				<tr>
					<td>跟单状态</td>
					<td>
						<c:choose>
							<c:when test="${quote.followOrderStatus eq 1}">
								成单
								<a class='addtitle' href='javascript:void(0);' tabtitle='{"num":"viewsaleorder${quote.saleorderId}","link":"./order/saleorder/view.do?saleorderId=${quote.saleorderId}","title":"订单信息"}'>(${quote.saleorderNo})</a>
							</c:when>
							<c:when test="${quote.followOrderStatus eq 2}">
								失单
								<c:if test="${!empty quote.followOrderStatusComments}">
									(<span style="color: red">${quote.followOrderStatusComments}</span>)
								</c:if>
							</c:when>
							<c:otherwise>跟单中</c:otherwise>
						</c:choose> 
					</td>
					<td>成单/失单时间</td>
					<td><date:date value="${quote.followOrderTime}" /></td>
				</tr>
			</tbody>
		</table>
	</div>
	<!-- ----------------------------------客户相关信息 ------------------------------------- -->
	<div class="parts ">
		<div class="title-container">
			<div class="table-title nobor">客户相关信息</div>
		</div>
		<table
			class="table table-bordered table-striped table-condensed table-centered">
			<tbody>
				<tr>
					<td class="table-smaller">客户名称</td>
					<td>
						<div class="customername pos_rel">
							<span class="font-blue"> <a class="addtitle"
								href="javascript:void(0);"
								tabTitle='{"num":"viewcustomer${customer.traderCustomerId}",
										"link":"./trader/customer/baseinfo.do?traderCustomerId=${customer.traderCustomerId}&traderId=${customer.traderId}",
										"title":"客户信息"}'>${quote.taderIdName}
							</a>
							</span> <i class="iconbluemouth"></i>
							<div class="pos_abs customernameshow mouthControlPos">
								报价次数：${customer.quoteCount} <br /> 
								交易次数：${customer.buyCount} <br />
								交易金额：${customer.buyMoney} <br /> 
								上次交易时间：<date:date value="${customer.lastBussinessTime}" /><br /> 
								归属销售：${quote.optUserName}
							</div>
						</div>
					</td>
					<td class="table-smaller">地区</td>
					<td>${quote.area}</td>
				</tr>
				<tr>
					<td>客户类型</td>
					<td>${quote.customerTypeStr}</td>
					<td>客户性质</td>
					<td>${quote.customerNatureStr}</td>
				</tr>
				<tr>
					<td>客户等级</td>
					<td>${quote.customerLevel}</td>
					<td>新老客户</td>
					<td>
						<c:choose>
							<c:when test="${quote.isNewCustomer eq 0}">老客户</c:when>
							<c:otherwise>新客户</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<%-- <tr>
						<td>联系人</td>
						<td>${quote.traderContactName}</td>
						<td>电话</td>
						<td>${quote.telephone}</td>
					</tr>
					<tr>
						<td>手机</td>
						<td>${quote.mobile}</td>
						<td>联系地址</td>
						<td>${quote.address}</td>
					</tr> --%>
				<tr>
					<td>联系人情况</td>
					<td>
						<c:choose>
							<c:when test="${quote.isPolicymaker eq 1}">采购关键人</c:when>
							<c:otherwise>非采购关键人</c:otherwise>
						</c:choose>
					</td>
					<td>采购方式</td>
					<td>${quote.purchasingTypeStr}</td>
				</tr>
				<tr>
					<td>付款条件</td>
					<td>${quote.paymentTermStr}</td>
					<td>采购时间</td>
					<td>${quote.purchasingTimeStr}</td>
				</tr>
				<tr>
					<td>项目进展情况</td>
					<td colspan="3">${quote.projectProgress}</td>
				</tr>
			</tbody>
		</table>
	</div>
	<!-- ----------------------------------终端信息 ------------------------------------- -->
	<div class="parts ">
		<div class="title-container">
			<div class="table-title nobor">终端信息</div>
			<input type="hidden" value="${quote.quoteorderId}"
				name="quoteorderId" id="quoteorderId">
		</div>
		<table
			class="table table-bordered table-striped table-condensed table-centered">
			<tbody>
				<tr>
					<td>终端名称</td>
					<td>
						<!-- 客户为终端 --> <c:choose>
							<c:when test="${quote.customerNature eq 466}">${quote.traderName}</c:when>
							<c:otherwise>${quote.terminalTraderName}</c:otherwise>
						</c:choose>
					</td>
					<td>终端类型</td>
					<td>
						<!-- 客户为终端 --> <c:choose>
							<c:when test="${quote.customerNature eq 466}">${quote.customerTypeStr}</c:when>
							<c:otherwise>${quote.terminalTraderTypeStr}</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<td>销售区域</td>
					<td>
						<!-- 客户为终端 --> <c:choose>
							<c:when test="${quote.customerNature eq 466}">${quote.area}</c:when>
							<c:otherwise>${quote.salesArea}</c:otherwise>
						</c:choose>
					</td>
					<td></td>
					<td></td>
				</tr>
			</tbody>
		</table>
	</div>
	<!-- ----------------------------------产品信息 ------------------------------------- -->
	<div class="parts table-style8">
		<div class="title-container">
			<div class="table-title nobor">产品信息</div>
		</div>
		<c:set var="num" value="0"></c:set>
		<c:set var="totleMoney" value="0.00"></c:set>
		<c:forEach var="list" items="${quoteGoodsList}" varStatus="staut">
			<c:if test="${empty list.goodsUserIdStr}">
				<%-- <c:if test="${contsultantUserId eq loginUserId}"><!-- 商品无归属人，但当前登录用户是报价咨询默认人 -->
					<table class="table">
					<c:set var="goodsCategoryUser" value="y"></c:set>
				</c:if> --%>
				
				<c:choose>
					<c:when test="${contsultantUserId eq loginUserId}"><!-- 商品无归属人，但当前登录用户是报价咨询默认人 -->
						<table class="table">
						<c:set var="goodsCategoryUser" value="y"></c:set>
					</c:when>
					<c:otherwise>
						<table class="table caozuo-grey" >
						<c:set var="goodsCategoryUser" value="n"></c:set>
					</c:otherwise>
				</c:choose>
				
			</c:if>
			<c:if test="${not empty list.goodsUserIdStr}">
				<c:choose>
					<c:when test="${fn:contains(list.goodsUserIdStr, loginUserId)}">
						<table class="table">
						<c:set var="goodsCategoryUser" value="y"></c:set>
					</c:when>
					<c:otherwise>
						<table class="table caozuo-grey" >
						<c:set var="goodsCategoryUser" value="n"></c:set>
					</c:otherwise>
				</c:choose>
			</c:if>
				<input type="hidden" id="goodsCategory_${staut.count}" name="goodsCategoryUser_${staut.count}" value="${goodsCategoryUser}"/>
				<tbody>
					<tr>
						<td class="wid4">序号</td>
						<td class="wid12">产品名称</td>
						<td class="wid9">品牌</td>
						<td class="wid8">型号</td>
						<td class="wid10">报价</td>
						<td class="wid10">数量</td>
						<td>单位</td>
						<td>总额</td>
						<td>货期</td>
						<td>直发</td>
						<td class="wid9">直发原因</td>
						<td>含安调</td>
						<td >产品备注</td>
						<td>内部备注</td>
					</tr>

					<input type="hidden" name="quoteorderConsultId" value="${list.quoteorderGoodsId}" />
					<tr>
						<c:if test="${list.isDelete eq 0}">
							<!-- 未删除 -->
							<c:set var="num" value="${num + list.num}"></c:set>
							<c:set var="totleMoney" value="${totleMoney + (list.price * list.num)}"></c:set>
						</c:if>
						<th rowspan="3">${staut.count}</th>
						<th rowspan="3" class='text-left'>
							<div class="customername pos_rel">
								<c:choose>
									<c:when test="${list.isDelete eq 1}">
											${list.goodsName}
											<i class="iconbluemouth"></i>
										<br>
									</c:when>
									<c:otherwise>
										<!-- 未删除 -->
										<c:if test="${list.isTemp eq 1 or goodsCategoryUser eq 'n'}">${newSkuInfosMap[list.sku].SHOW_NAME}<br></c:if>
										<c:if test="${list.isTemp eq 0 and goodsCategoryUser eq 'y'}"><!-- 非临时产品 -->
											<span class="font-blue"> 
												<a class="addtitle" href="javascript:void(0);"
													tabtitle='{"num":"viewgoods${list.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${list.goodsId}","title":"产品信息"}'>
													${newSkuInfosMap[list.sku].SHOW_NAME}
												<i class="iconbluemouth"></i><br>
												</a>
											</span>
										</c:if>
									</c:otherwise>
								</c:choose>
								${newSkuInfosMap[list.sku].SKU_NO}
								<c:if test="${list.isDelete eq 1}"><span style="color: red">(已删除)</span></c:if>

								<c:set var="skuNo" value="${list.sku}"></c:set>
								<%@ include file="../../common/new_sku_common_tip.jsp" %>

								<%--<div class="pos_abs customernameshow">--%>
									<%--物料编码：${list.goods.materialCode} <br>--%>
									<%--注册证号：${list.goods.registrationNumber} <br>--%>
									<%--管理类别：${list.manageCategoryName} <br> --%>
									<%--产品归属：${list.goodsUserNm}<br>--%>
									<%--采购提醒：${list.goods.purchaseRemind} <br>--%>
									<%--包装清单：${list.goods.packingList} <br> --%>
									<%--服务条款：${list.goods.tos} <br> --%>
									<%--库存：${(list.goods.stockNum < 0)?0:(list.goods.stockNum)} <br> --%>
									<%--可用库存：${(list.goods.stockNum - list.goods.orderOccupy) < 0 ? 0 : (list.goods.stockNum - list.goods.orderOccupy)} <br> --%>
									<%--订单占用：${list.goods.orderOccupy}<br> --%>
									<%--可调剂：${list.goods.adjustableNum} <br> --%>
									<%--审核状态：<c:choose>--%>
											 	<%--<c:when test="${empty list.goods.verifyStatus or list.goods.verifyStatus eq 0}">审核中</c:when>--%>
											 	<%--<c:when test="${list.goods.verifyStatus eq 1}">审核通过</c:when>--%>
											 	<%--<c:when test="${list.goods.verifyStatus eq 2}">审核不通过</c:when>--%>
											 	<%--<c:otherwise>--</c:otherwise>--%>
											 <%--</c:choose>--%>
								<%--</div>--%>
							</div>
						</th>
						<th rowspan="3">${newSkuInfosMap[list.sku].BRAND_NAME}</th>
						<th rowspan="3">${newSkuInfosMap[list.sku].MODEL}</th>
						<td>${list.price}</td>
						<td>${list.num}</td>
						<td>${newSkuInfosMap[list.sku].UNIT_NAME}</td>
						<td><fmt:formatNumber type="number" value="${list.price * list.num}" pattern="0.00" maxFractionDigits="2" /></td>
						<td>${list.deliveryCycle}</td>
						<td><c:choose>
								<c:when test="${list.deliveryDirect eq 0}">否</c:when>
								<c:otherwise>是</c:otherwise>
							</c:choose></td>
						<td style="width: 70px">${list.deliveryDirectComments}</td>
						<td style="width: 100px">
							<c:choose>
								<c:when test="${list.haveInstallation eq 0}">否</c:when>
								<c:otherwise>是</c:otherwise>
							</c:choose></td>
						<td style="width: 70px">${list.goodsComments}</td>
						<td style="width: 100px">${list.insideComments}</td>
					</tr>
					<tr>
						<td>核价信息</td>
						<td>订单占用/库存</td>
						<c:choose>
							<c:when test="${goodsCategoryUser eq 'y'}">
								<td><span style="color: red">参考成本</span></td>
								<td><span style="color: red">参考报价</span></td>
								<td><span style="color: red">参考货期</span></td>
								<td><span style="color: red">报备结果</span></td>
								<td><span style="color: red">报备失败原因</span></td>
								<td><span style="color: red">注册证号</span></td>
								<td><span style="color: red">供应商</span></td>
							</c:when>
							<c:otherwise>
								<td><span>参考成本</span></td>
								<td><span>参考报价</span></td>
								<td><span>参考货期</span></td>
								<td><span>报备结果</span></td>
								<td><span>报备失败原因</span></td>
								<td><span>注册证号</span></td>
								<td><span>供应商</span></td>
							</c:otherwise>
						</c:choose>
						<td>管制类别</td>
					</tr>
					<tr>
						<td class="text-left">
							价格：<fmt:formatNumber type="number" value="${list.channelPrice}" pattern="0.00" maxFractionDigits="2" /><br/>
							期货交货期：${list.channelDeliveryCycle} <br> 
							现货交货期：${list.delivery} <br>
							成本：${list.costPrice}
						</td>
						<td>${list.goods.orderOccupy}/${(list.goods.stockNum<0)?0:(list.goods.stockNum)}</td>
						<td>
							<!-- value="${list.referenceCostPrice == '0.00'?(list.costPrice==null?'0.00':list.costPrice):list.referenceCostPrice}" -->
							<input type="text" name="referenceCostPrice_${staut.count}" value="${list.referenceCostPrice == null?'0.00':list.referenceCostPrice}" alt="${list.referenceCostPrice == null?'0.00':list.referenceCostPrice}">
						</td>
						<td>
							<!-- value="${list.referencePrice == '0.00'?(list.channelPrice==null?'0.00':list.channelPrice):list.referencePrice}" -->
							<input type="text" name="referencePrice_${staut.count}" value="${list.referencePrice == null?'0.00':list.referencePrice}" alt="${list.referencePrice == null?'0.00':list.referencePrice}">
						</td>
						<td>
							<input type="text" name="referenceDeliveryCycle_${staut.count}" value="${list.referenceDeliveryCycle}" alt="${list.referenceDeliveryCycle}">
						</td>
						<td>
							<input type="radio" onclick="changeReport(2,${staut.count})" name="reportResult_${staut.count}" <c:if test="${list.reportStatus eq 2}">checked</c:if>><label>成功</label><br>
							<input type="radio" onclick="changeReport(3,${staut.count})" name="reportResult_${staut.count}" <c:if test="${list.reportStatus eq 3}">checked</c:if>><label>失败</label>
							<input type="hidden" name="reportStatus" id="reportStatus${staut.count}" value="" alt="">
						</td>
						<td>
							<input type="text" name="reportComments_${staut.count}" value="${list.reportComments}" alt="${list.reportComments}">
						</td>
						<td>
							<input type="text" name="registrationNumber_${staut.count}" value="${list.registrationNumber}" alt="${list.registrationNumber}">
						</td>
						<td>
							<input type="text" name="supplierName_${staut.count}" value="${list.supplierName}" alt="${list.supplierName}">
						</td>
						<td>xx</td>
					</tr>
					</c:forEach>
					<tr style="background: #eaf2fd;">
						<td colspan="14" class="text-left">总件数<span class="font-red">${num}</span>，
							总金额 
							<span class="font-red"> 
								<fmt:formatNumber type="number" value="${totleMoney}" pattern="0.00" maxFractionDigits="2" />
							</span>
						</td>
					</tr>
				</tbody>
			</table>
	</div>
	<!-- ----------------------------------付款计划 ------------------------------------- -->
	<div class="parts ">
		<div class="title-container">
			<div class="table-title nobor">付款计划</div>
		</div>
		<table
			class="table table-bordered table-striped table-condensed table-centered">
			<thead>
				<tr>
					<th style="width: 150px">计划</th>
					<th style="width: 150px">计划内容</th>
					<th style="width: 150px">支付金额</th>
					<th>备注</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${quote.paymentType eq 419}">
					<tr>
						<td>第一期</td>
						<td>预付款</td>
						<td>${quote.prepaidAmount}</td>
						<td>
							<c:forEach var="list" items="${paymentTermList}" varStatus="status">
								<c:if test="${list.sysOptionDefinitionId eq quote.paymentType}">${list.title}</c:if>
							</c:forEach>
						</td>
					</tr>
				</c:if>
				<c:if test="${quote.paymentType ne 419 and quote.paymentType ne 424}">
					<tr>
						<td>第一期</td>
						<td>预付款</td>
						<td>${quote.prepaidAmount}</td>
						<td><c:forEach var="list" items="${paymentTermList}" varStatus="status">
								<c:if test="${list.sysOptionDefinitionId eq quote.paymentType}">${list.title}</c:if>
							</c:forEach></td>
					</tr>
					<tr>
						<td>第二期</td>
						<td>帐期付款</td>
						<td>${quote.accountPeriodAmount}</td>
						<td>到货后${customer.periodDay}天内支付 <c:if test="${quote.logisticsCollection eq 1}"> / 物流代收</c:if>
						</td>
					</tr>
				</c:if>
				<c:if test="${quote.paymentType eq 424}">
					<tr>
						<td>第一期</td>
						<td>预付款</td>
						<td>${quote.prepaidAmount}</td>
						<td><c:forEach var="list" items="${paymentTermList}"
								varStatus="status">
								<c:if test="${list.sysOptionDefinitionId eq quote.paymentType}">${list.title}</c:if>
							</c:forEach></td>
					</tr>
					<tr>
						<td>第二期</td>
						<td>帐期付款</td>
						<td>${quote.accountPeriodAmount}</td>
						<td>到货后${customer.periodDay}天内支付 <c:if test="${quote.logisticsCollection eq 1}"> / 物流代收</c:if>
						</td>
					</tr>
					<tr>
						<td>第三期</td>
						<td>尾款</td>
						<td>${quote.retainageAmount}</td>
						<td>到货后${quote.retainageAmountMonth}个月内支付</td>
					</tr>
				</c:if>
				<!-- <tr style="background: #eaf2fd;">
					<td colspan="4" class="text-left">
						帐期付款：帐期付款是我司向客户提供的信用付款方式，您需要在约定时间内支付帐期额度的金额。本合同中帐期以合同开始发货为结算开始时间。
					</td>
				</tr> -->
			</tbody>
		</table>
	</div>
	<!-- ----------------------------------其他信息 ------------------------------------- -->
	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">其他信息</div>
		</div>
		<table
			class="table table-bordered table-striped table-condensed table-centered">
			<tbody>
				<tr>
					<td class="table-smaller">报价有效期</td>
					<td>${quote.period}</td>
					<td class="table-smaller">发票类型</td>
					<td><c:forEach var="list" items="${invoiceTypeList}"
							varStatus="status">
							<c:if test="${list.sysOptionDefinitionId eq quote.invoiceType}">${list.title}</c:if>
						</c:forEach></td>
				</tr>
				<tr>
					<td>运费说明</td>
					<td><c:forEach var="list" items="${freightList}"
							varStatus="status">
							<c:if
								test="${list.sysOptionDefinitionId eq quote.freightDescription}">${list.title}</c:if>
						</c:forEach></td>
					<td class="table-smaller"></td>
					<td></td>
				</tr>
				<tr>
					<td>附加条款</td>
					<td colspan="3">${quote.additionalClause}</td>
				</tr>
				<tr>
					<td>内部备注</td>
					<td colspan="3">${quote.comments}</td>
				</tr>
				<!-- <tr>
						<td>审核项</td>
						<td colspan="3" class="font-red text-left">1234</td>
					</tr> -->
			</tbody>
		</table>
	</div>
	<!-- ----------------------------------内部咨询 ------------------------------------- -->
	<div class="parts content1">
		<div class="title-container">
			<div class="table-title nobor">内部咨询</div>
		</div>
		<table
			class="table table-bordered table-striped table-condensed table-centered">
			<tbody>
				<tr>
					<td class="table-smaller">时间</td>
					<td class="table-smaller">人员</td>
					<td>内容</td>
				</tr>
				<c:forEach var="list" items="${consultList}" varStatus="status">
					<tr>
						<td><date:date value="${list.addTime}" /></td>
						<td><c:forEach var="user" items="${userList}"
								varStatus="stat">
								<c:if test="${user.userId eq list.creator}">${user.username}</c:if>
							</c:forEach></td>
						<td>${list.content}</td>
					</tr>
				</c:forEach>
				<tr>
					<td></td>
					<td></td>
					<td><textarea style='width: 100%; height: 100px' name="quoteContent" id="quoteContent"></textarea></td>
				</tr>
			</tbody>
		</table>
		<div class="table-buttons">
			<!-- 修改报价状态后跳转新标签页使用 -->
			<form action="./revokeValIdStatus.do" id="viewQuoteOperationForm">
				<input type="hidden" name="beforeParams" value='${beforeParams}'><!-- 日志 -->
				<!-- 报价主键 -->
				<input type="hidden" name="quoteorderId" value="${quote.quoteorderId}" />
				<!-- 商机主键 -->
				<input type="hidden" name="bussinessChanceId" value="${quote.bussinessChanceId}" />
				<!-- 报价失效 -->
				<input type="hidden" name="validStatus" value="0" />
			</form>
			<input type="hidden" name="formToken" value="${formToken}"/>
			<button type="button" class="bt-bg-style bg-light-green bt-small mr10" onclick="replyQuoteConsult(${quote.quoteorderId},3);">答复</button>
			<%--<button type="button" class="bt-bg-style bg-light-green bt-small" onclick="editConsultStatus(${quote.quoteorderId},2);">标记处理中</button>
			<button type="button" class="bt-bg-style bg-light-green bt-small" onclick="editConsultStatus(${quote.quoteorderId},3);">标记已处理</button>--%>
		</div>
	</div>
	
<%@ include file="../../common/footer.jsp"%>