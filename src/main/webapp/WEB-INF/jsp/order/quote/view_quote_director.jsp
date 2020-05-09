<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="报价详情" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%=basePath%>/static/js/order/quote/edit_quote_detail.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src='<%=basePath%>/static/js/order/quote/view_quote_sale.js?rnd=<%=Math.random()%>'></script>
<div class="pb100 content mt10">
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
							<c:when test="${quote.validStatus eq 1}">已生效</c:when>
							<c:otherwise>
									未生效
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
				<tr>
					<td>商机编号</td>
					<td><a class="addtitle" href="javascript:void(0);"
						tabTitle='{"num":"view${quote.bussinessChanceId}",
								"link":"./order/bussinesschance/toAfterSalesDetailPage.do?bussinessChanceId=${quote.bussinessChanceId}",
								"title":"商机详情"}'>${quote.bussinessChanceNo}
					</a></td>
					<td>商机时间</td>
					<td><date:date value="${quote.receiveTime}" /></td>
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
				<tr>
					<td>联系人</td>
					<td>${quote.traderContactName}</td>
					<td>电话</td>
					<td><c:if test="${!empty quote.telephone}">
							<i class="icontel cursor-pointer" title="点击拨号"
								onclick="callout('${quote.telephone}',${quote.traderId},1,3,${quote.quoteorderId},0);"></i>${quote.telephone}
							</c:if></td>
				</tr>
				<tr>
					<td>手机</td>
					<td><c:if test="${!empty quote.mobile}">
							<i class="icontel cursor-pointer" title="点击拨号"
								onclick="callout('${quote.mobile}',${quote.traderId},1,3,${quote.quoteorderId},0);"></i>${quote.mobile}
							</c:if></td>
					<td>联系地址</td>
					<td>${quote.address}</td>
				</tr>
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
	<div class="parts content1">
		<div class="title-container">
			<div class="table-title nobor">产品信息</div>
		</div>
		<table
			class="table table-bordered table-striped table-condensed table-centered">
			<thead>
				<tr>
					<th class="wid4">序号</th>
					<th class="wid16">产品名称</th>
					<th class="wid12">品牌</th>
					<th class="wid9">型号</th>
					<th class="wid8">报价</th>
					<th class="wid6">数量</th>
					<th class="wid6">单位</th>
					<th class="wid8">总额</th>
					<th class="wid6">货期</th>
					<th class="wid5">直发</th>
					<th class="wid18">核价参考</th>
					<th class="wid6">含安调</th>
					<th>产品备注</th>
					<th>内部备注</th>
				</tr>
			</thead>
			<tbody>
				<c:set var="num" value="0"></c:set>
				<c:set var="totleMoney" value="0.00"></c:set>
				<c:forEach var="list" items="${quoteGoodsList}" varStatus="staut">
					<tr <c:if test="${list.isDelete eq 1}">class="caozuo-grey"</c:if>>
						<c:if test="${list.isDelete eq 0}">
							<!-- 未删除 -->
							<c:set var="num" value="${num + list.num}"></c:set>
							<c:set var="totleMoney"
								value="${totleMoney + (list.price * list.num)}"></c:set>
						</c:if>
						<td>${staut.count}</td>
						<td class="text-left">
							<div class="customername pos_rel">
								<c:choose>
									<c:when test="${list.isDelete eq 1}">
											${newSkuInfosMap[list.sku].SHOW_NAME}
											<i class="iconbluemouth"></i>
										<br>
									</c:when>
									<c:otherwise>
										<!-- 未删除 -->
										<c:if test="${list.isTemp eq 1}">${list.goodsName}</c:if>
										<c:if test="${list.isTemp eq 0}"><!-- 非临时产品 -->
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
								${list.sku}
								<c:set var="skuNo" value="${list.sku}"></c:set>
								<%@ include file="../../common/new_sku_common_tip.jsp" %>
							</div>
						</td>
						<td>${newSkuInfosMap[list.sku].BRAND_NAME}</td>
						<td>${newSkuInfosMap[list.sku].MODEL}</td>
						<td>${list.price}</td>
						<td>${list.num}</td>
						<td>${newSkuInfosMap[list.sku].UNIT_NAME}</td>
						<td><fmt:formatNumber type="number"
								value="${list.price * list.num}" pattern="0.00"
								maxFractionDigits="2" /></td>
						<td>${list.deliveryCycle}</td>
						<td>
							<div class="customername pos_rel">
								<span> <c:choose>
										<c:when test="${list.deliveryDirect eq 0}">否</c:when>
										<c:otherwise>
												是
											<i class="iconbluesigh ml4"></i>
											<div class="pos_abs customernameshow">直发原因：${list.deliveryDirectComments}</div>
										</c:otherwise>
									</c:choose>
								</span>
							</div>
						</td>
						<td>
							<div class="customername pos_rel">
								核价参考价格：<fmt:formatNumber type="number" value="${list.channelPrice}" pattern="0.00" maxFractionDigits="2" /><br/>
								参考价格：${list.referencePrice} <br/>
								参考货期：${list.referenceDeliveryCycle} <br/>
								<shiro:hasPermission name="/order/quote/settlementPrice.do">
								结算价：${list.settlePrice} <br/>
								</shiro:hasPermission>
								<c:choose>
									<c:when test="${list.reportStatus eq 2}">
										报备成功<i class="iconbluesigh ml4"></i>
									</c:when>
									<c:when test="${list.reportStatus eq 3}">
										报备失败<i class="iconredsigh ml4"></i>
									</c:when>
									<c:otherwise>
										<i class="iconbluesigh ml4"></i>
									</c:otherwise>
								</c:choose>
								<div class="pos_abs customernameshow">
									<c:set var="goodsUserNm" value=""/>
									<c:forEach var="user" items="${userList}">
										<c:if test="${user.userId eq list.lastReferenceUser}"><c:set var="goodsUserNm" value="${user.username}"/></c:if>
									</c:forEach>
									核价参考价格：<fmt:formatNumber type="number" value="${list.channelPrice}" pattern="0.00" maxFractionDigits="2" /><br/>
									客户上次购买价格：<fmt:formatNumber type="number" value="${list.lastOrderPrice}" pattern="0.00" maxFractionDigits="2" /><br/>
									参考价格（${goodsUserNm}）：${list.referencePrice} <br/>
									<%-- 核价参考货期：${list.channelDeliveryCycle} <br/> --%>
									核价期货交货期：${list.channelDeliveryCycle} <br> 
									核价现货交货期：${list.delivery} <br>
									参考货期（${goodsUserNm}）：${list.referenceDeliveryCycle} <br/>
									报备结果：
										<c:if test="${list.reportStatus eq 2}">
											成功 <br/>
											报备失败原因：<%-- ${list.reportComments} --%>
										</c:if>
										<c:if test="${list.reportStatus eq 3}">
											失败 <br/>
											报备失败原因：${list.reportComments}
										</c:if>
								</div>
							</div>
						</td>
						<td><c:choose>
								<c:when test="${list.haveInstallation eq 0}">否</c:when>
								<c:otherwise>是</c:otherwise>
							</c:choose></td>
						<td>${list.goodsComments}</td>
						<td>${list.insideComments}</td>
					</tr>
				</c:forEach>
				<tr style="background: #eaf2fd;">
					<td colspan="14" class="text-left">总件数<span class="font-red">${num}</span>，
						总金额 <span class="font-red"> <fmt:formatNumber type="number"
								value="${totleMoney}" pattern="0.00" maxFractionDigits="2" />
					</span>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<!-- ----------------------------------付款计划 ------------------------------------- -->
	<div class="parts">
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
						<td><c:forEach var="list" items="${paymentTermList}"
								varStatus="status">
								<c:if test="${list.sysOptionDefinitionId eq quote.paymentType}">${list.title}</c:if>
							</c:forEach></td>
					</tr>
				</c:if>
				<c:if
					test="${quote.paymentType ne 419 and quote.paymentType ne 424}">
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
				<c:if test="${quote.followOrderStatus ne 2}">
					<!-- 2:失单 -->
					<tr>
						<td></td>
						<td></td>
						<td><textarea style='width: 100%; height: 150px'
								name="quoteContent" id="quoteContent"
								placeholder="1、有明确品牌，型号无需报备；
2、有明确品牌、型号，需要报备销售提供终端信息、项目信息的；
3、对应在贝登目前供应品类，只有产品名称，但提供包括不限于：进口或国产，应用场景（如科室），预算要求，配置要求（如超声探头）等有助于选型信息，根据选型信息产品部提供推荐选型，如无法提供则转为无法报价；需要报备的产品，务必提供终端机项目信息；不能确认是否需要报备，尽量一次性要求客户提供，了解终端信息，也是我们销售掌握商机的必要环节，特别是需要装机考核；
4、对于没有供应链的新品类询价， 同上要求；
5、其他规则与产品部Jason确认。"></textarea>
						</td>
					</tr>
				</c:if>
			</tbody>
		</table>
		<div class="tcenter mb15">
			<!-- 修改报价状态后跳转新标签页使用 -->
			<form action="./revokeValIdStatus.do" id="viewQuoteOperationForm">
				<input type="hidden" name="quoteorderId"
					value="${quote.quoteorderId}" />
				<!-- 报价主键 -->
				<input type="hidden" name="validStatus" value="0" />
				<!-- 报价失效 -->
				<input type="hidden" name="bussinessChanceId" value="${quote.bussinessChanceId}" />
				<!-- 商机主键 -->
			</form>
			<c:if test="${quote.followOrderStatus ne 2}">
				<!-- 2:失单 -->
				<button type="button" class="bt-bg-style bg-light-green bt-small mr10" onclick="sendQuoteConsult('${quote.quoteorderNo}',${quote.quoteorderId},${num});">发送咨询</button>
				<span class="bg-light-green bt-bg-style bt-small addtitle"
					tabTitle='{"num":"quote_viewOutDetail_${quote.quoteorderId}","link":"./order/quote/printOrder.do?quoteorderId=${quote.quoteorderId}","title":"打印报价单"}'>打印报价单</span>
				<button type="button" class="bt-bg-style bg-light-green bt-small">转为订单</button>
				<button type="button" class="bt-bg-style bt-small bg-light-orange"
					onclick="revokeValId();">撤销生效</button>
				<button type="button"
					class="bt-bg-style bt-small bg-light-orange pop-new-data"
					layerParams='{"width":"400px","height":"200px","title":"失单原因","link":"./reasonOfLostOrder.do?quoteorderId=${quote.quoteorderId}"}'
					style="width: 70px; text-align: center;">失单</button>
			</c:if>
		</div>
	</div>
	<!-- ----------------------------------沟通记录 ------------------------------------- -->
	<div class="parts content1">
		<div class="title-container">
			<div class="table-title nobor">沟通记录</div>
			<c:if test="${quote.followOrderStatus ne 2}">
				<!-- 2:失单 -->
				<div class="title-click nobor  pop-new-data"
					layerParams='{"width":"70%","height":"70%","title":"新增沟通记录","link":"./addComrecord.do?quoteorderId=${quote.quoteorderId}&traderId=${quote.traderId}"}'>新增</div>
			</c:if>
		</div>
		<table
			class="table table-bordered table-striped table-condensed table-centered">
			<tbody>
				<tr>
					<td>沟通时间</td>
					<td>单号</td>
					<td>录音</td>
					<th>录音内容</th>
					<td>联系人</td>
					<td>联系方式</td>
					<td>沟通方式</td>
					<td>沟通目的</td>
					<td>沟通内容</td>
					<td>操作人</td>
					<td>下次联系日期</td>
					<td>下次沟通内容</td>
					<td>备注</td>
					<td>创建时间</td>
					<td>操作</td>
				</tr>
				<c:forEach var="list" items="${communicateList}" varStatus="status">
					<tr>
						<td><date:date value="${list.begintime} " />~<date:date
								value="${list.endtime}" format="HH:mm:ss" /></td>
						<td><c:choose>
								<c:when test="${list.communicateType == 244 }">
									<a class="addtitle" href="javascript:void(0);"
										tabTitle='{"num":"view${list.relatedId}",
											"link":"./order/bussinesschance/toSalesDetailPage.do?bussinessChanceId=${list.relatedId}&traderId=${list.traderId }&traderCustomerId=${list.traderCustomerId }",
											"title":"销售商机详情"}'>${list.bussinessChanceNo }</a>
								</c:when>
								<c:when test="${list.communicateType == 245 }">
									<a class="addtitle"
										tabtitle="{&quot;num&quot;:&quot;viewQuote${list.relatedId}&quot;,&quot;link&quot;:&quot;./order/quote/getQuoteDetail.do?quoteorderId=${list.relatedId}&viewType=3&quot;,&quot;title&quot;:&quot;报价详情&quot;}">${list.quoteorderNo}</a>
								</c:when>
								<c:when test="${list.communicateType == 246 }">
									<a class="addtitle" href="javascript:void(0);"
										tabTitle='{"num":"viewsaleorder${list.relatedId}","link":"./order/saleorder/view.do?saleorderId=${list.relatedId}","title":"订单信息"}'>${list.saleorderNo}</a>
								</c:when>
								<c:otherwise></c:otherwise>
							</c:choose></td>
						<td><c:if test="${not empty list.coidUri}">${list.communicateRecordId}</c:if></td>
						<td><c:if test="${not empty list.coidUri}">
							<c:if test="${list.isTranslation eq 1}">
									  <span class="edit-user pop-new-data"
											layerParams='{"width":"90%","height":"90%","title":"查看详情","link":"${pageContext.request.contextPath}/phoneticTranscription/phonetic/viewContent.do?communicateRecordId=${list.communicateRecordId}"}'>查看</span>
							</c:if>
							<span class="edit-user"
								  onclick="playrecord('${list.coidUri}');">播放</span>
						</c:if></td>
						<td>${list.contactName}</td>
						<td>${list.phone}</td>
						<td>${list.communicateModeName}</td>
						<td>${list.communicateGoalName}</td>
						<td>
							<ul class="communicatecontent ml0">
								<c:if test="${not empty list.tag }">
									<c:forEach items="${list.tag }" var="tag">
										<li class="bluetag" title="${tag.tagName}">${tag.tagName}</li>
									</c:forEach>
								</c:if>
							</ul>
						</td>
						<td>${list.user.username}</td>
						<c:choose>
							<c:when test="${list.isDone == 0 }">
								<td class="font-red">${list.nextContactDate }</td>
							</c:when>
							<c:otherwise>
								<td>${list.nextContactDate }</td>
							</c:otherwise>
						</c:choose>
						<td>${list.nextContactContent}</td>
						<td>${list.comments}</td>
						<td><date:date value="${list.addTime}" /></td>
						<td class="caozuo"><c:if
								test="${quote.followOrderStatus ne 2}">
								<!-- 2:失单 -->
								<span class="border-blue pop-new-data"
									layerParams='{"width":"60%","height":"63%","title":"编辑沟通记录","link":"./editCommunicate.do?communicateRecordId=${list.communicateRecordId}&bussinessChanceId=${quote.bussinessChanceId}&traderId=${quote.traderId}"}'>编辑</span>
							</c:if></td>
					</tr>
				</c:forEach>
				<c:if test="${empty communicateList}">
					<tr>
						<!-- 查询无结果弹出 -->
						<td colspan="14">查询无结果！请尝试使用其他搜索条件。</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>
<%@ include file="../../common/footer.jsp"%>