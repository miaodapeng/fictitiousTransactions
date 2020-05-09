<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="修改报价" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%=basePath%>/static/js/order/quote/edit_quote_detail.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src='<%= basePath %>static/js/region/index.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript">
	$(function(){
		var	url = page_url + '/order/quote/getQuoteDetail.do?quoteorderId='+$("#quoteorderId").val()+'&quoteSource='+$("#quoteSource").val();
		if($(window.frameElement).attr('src').indexOf("quoteorderId")<0){
			$(window.frameElement).attr('data-url',url);
		}
	});
</script>
<div class="content mt10">
	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">基本信息</div>
		</div>
		<table
			class="table">
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
				<%-- <c:if test="${quote.followOrderStatus eq 2}"> --%>
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
				<%-- </c:if> --%>
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
			</tbody>
		</table>
	</div>
	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">客户相关信息</div>
			<c:if test="${quote.followOrderStatus ne 2}">
				<div class="title-click nobor  pop-new-data"
					layerParams='{"width":"60%","height":"70%","title":"编辑客户","link":"./editQuote.do?quoteorderId=${quote.quoteorderId}"}'>编辑</div>
			</c:if>
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

	<%-- <div class="parts">
		<div class="title-container">
			<div class="table-title nobor">终端信息</div>
			<input type="hidden" value="${quote.quoteorderId}" name="quoteorderId" id="quoteorderId">
			<c:if test="${(quote.customerNature ne 466) and (quote.followOrderStatus ne 2)}">
				<!-- 客户为终端 并且未失单 -->
				<div class="title-click nobor  pop-new-data" id="updateTerminalDiv">
					<a href="javascript:void(0);"
						onclick="updateTerminal('${quote.terminalTraderName}');">编辑</a>
				</div>
			</c:if>
		</div>
		<table class="table table-bordered table-striped table-condensed table-centered">
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
	</div> --%>
	<input type="hidden" value="${quote.quoteorderId}" name="quoteorderId" id="quoteorderId">
	<c:if test="${quote.customerNature eq 465}"><!-- 分销 -->
		<div class="parts" id="updateTerminalInfo">
			<div class="formtitle">终端信息</div>
			<ul class="payplan">
				<c:choose>
					<c:when test="${not empty quote.terminalTraderName}"><!-- 客户名称存在，则默认不显示选择框 -->
						<li id="terminalNameCheck" style="display: none;">
					</c:when>
					<c:otherwise><!-- 客户名称不存在，默认显示选择框 -->
						<li id="terminalNameCheck">
					</c:otherwise>
				</c:choose>
					<div class="infor_name">
						<label>终端名称</label>
					</div>
					<div class="f_left">
						<div class="inputfloat" id="errorTxtMsg">
							<!-- 客户为终端 --> 
							<input type="text" placeholder="请输入终端名称" class="input-largest" name="searchTraderName" id="searchTraderName"> 
							<label class="bt-bg-style bg-light-blue bt-small" onclick="searchTerminal();" id="errorMes">搜索</label> 
							<span style="display: none;"> <!-- 弹框 -->
								<div class="title-click nobor  pop-new-data" id="terminalDiv"></div>
							</span>
						</div>
					</div>
				</li>
				<c:choose>
					<c:when test="${empty quote.terminalTraderName}"><!-- 客户名称不存在，默认显示选择框 -->
						<li id="terminalNameDetail" style="display: none;">
					</c:when>
					<c:otherwise><!-- 客户名称不存在，默认显示选择框 -->
						<li id="terminalNameDetail">
					</c:otherwise>
				</c:choose>
					<div class="infor_name">
						<label>终端名称</label>
					</div>
					<div class="f_left">
						<div class=" inputfloat" id="errorTxtMsg">
							<span class="mr8 mt3" id="terminalTraderNameDiv">${quote.terminalTraderName}</span> 
							<label class="bt-bg-style bg-light-blue bt-small" onclick="agingSearchTerminal();">重新搜索</label>
						</div>
					</div>
				</li>
				<li>
					<div class="infor_name ">
						<label>销售区域</label>
					</div> 
					<div class="f_left">
						<c:choose>
							<c:when test="${empty quote.salesAreaId}">
								<select class="input-small f_left mr10" name="province" id="province">
									<option value="0">请选择</option>
									<c:if test="${not empty provinceList }">
										<c:forEach items="${provinceList }" var="prov">
											<option value="${prov.regionId }" <c:if test="${province eq prov.regionId }">selected="selected"</c:if>>${prov.regionName }</option>
										</c:forEach>
									</c:if>
								</select> 
								<select class="input-small f_left mr10" name="city" id="city">
									<option value="0">请选择</option>
								</select> 
								<select class="input-small f_left" name="zone" id="zone">
									<option value="0">请选择</option>
								</select>
							</c:when>
							<c:otherwise>
								<select class="input-small f_left mr10" name="province" id="province">
									<option value="0">请选择</option>
									<c:if test="${not empty provinceList }">
										<c:forEach items="${provinceList }" var="province">
											<option value="${province.regionId }"
												<c:if test="${ not empty provinceRegion &&  province.regionId == provinceRegion.regionId }">selected="selected"</c:if>>${province.regionName }</option>
										</c:forEach>
									</c:if>
								</select> 
								<select class="input-small f_left mr10" name="city" id="city">
									<option value="0">请选择</option>
									<c:if test="${not empty cityList }">
										<c:forEach items="${cityList }" var="city">
											<option value="${city.regionId }"
												<c:if test="${ not empty cityRegion &&  city.regionId == cityRegion.regionId }">selected="selected"</c:if>>${city.regionName }</option>
										</c:forEach>
									</c:if>
								</select> 
								<select class="input-small f_left" name="zone" id="zone">
									<option value="0">请选择</option>
									<c:if test="${not empty zoneList }">
										<c:forEach items="${zoneList }" var="zone">
											<option value="${zone.regionId }"
												<c:if test="${ not empty zoneRegion &&  zone.regionId == zoneRegion.regionId }">selected="selected"</c:if>>${zone.regionName }</option>
										</c:forEach>
									</c:if>
								</select>
							</c:otherwise>
						</c:choose>
					</div>
				</li>
			</ul>
		</div>
	</c:if>

	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">产品信息</div>
			<c:if test="${quote.followOrderStatus ne 2}">
				<div class="title-click nobor  pop-new-data"
					layerParams='{"width":"65%","height":"75%","title":"添加产品","link":"./addQuoteGoods.do?quoteorderId=${quote.quoteorderId}"}'>添加</div>
			</c:if>
		</div>
		<table
			class="table table-bordered table-striped table-condensed table-centered">
			<thead>
				<tr>
					<th class="wid4">序号</th>
					<th width="10%">产品名称</th>
					<th width="10%">品牌型号</th>
					<th width="6%">报价</th>
					<th width="5%">数量</th>
					<th width="5%">单位</th>
					<th width="6%">总额</th>
					<th width="5%">货期</th>
					<th width="5%">直发</th>
					<th width="16%">核价参考</th>
					<th width="5%">含安调</th>
					<th>产品备注</th>
					<th>内部备注</th>
					<th class="wid12">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:set var="num" value="0"></c:set>
				<%-- <c:set var="totleMoney" value="0.00"></c:set> --%>
				<c:forEach var="list" items="${quoteGoodsList}" varStatus="staut">
					<tr <c:if test="${list.isDelete eq 1}">class="caozuo-grey"</c:if>>
						<c:if test="${list.isDelete eq 0}">
							<c:set var="num" value="${num + list.num}"></c:set>
							<%-- <c:set var="totleMoney" value="${totleMoney + (list.price * list.num)}"></c:set> --%>
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
										<c:if test="${list.isTemp eq 1}">${newSkuInfosMap[list.sku].SHOW_NAME}</c:if>
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
								${newSkuInfosMap[list.sku].SKU_NO}

								<c:set var="skuNo" value="${list.sku}"></c:set>
								<%@ include file="../../common/new_sku_common_tip.jsp" %>
							</div>
						</td>
						<td>
							${newSkuInfosMap[list.sku].BRAND_NAME}
							<br/>
							${newSkuInfosMap[list.sku].MODEL}
						</td>
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
						<td>
							<div class="caozuo">
								<c:if test="${quote.followOrderStatus ne 2}">
									<!-- 未失单 -->
									<c:choose>
										<c:when test="${list.isDelete eq 0}">
											<span class="caozuo-blue pop-new-data"
												layerparams='{"width":"60%","height":"70%","title":"编辑产品信息","link":"./editQuoteGoodsInit.do?quoteorderId=${list.quoteorderId}&quoteorderGoodsId=${list.quoteorderGoodsId}"}'>编辑</span>
											<span class="caozuo-red"
												onclick="delQuoteGoods(${list.quoteorderId},${list.quoteorderGoodsId});">删除</span>
										</c:when>
										<c:otherwise>
											已删除
										</c:otherwise>
									</c:choose>
								</c:if>
							</div>
						</td>
				</c:forEach>
				<tr style="background: #eaf2fd;">
					<td colspan="14" class="text-left">总件数<span class="font-red">${num}</span>，
						总金额 <span class="font-red"> <fmt:formatNumber type="number" value="${quote.totalAmount==null?0:quote.totalAmount}" pattern="0.00" maxFractionDigits="2" /> 
						<input type="hidden" id="goodsTotleMoney" value="${quote.totalAmount==null?0:quote.totalAmount}">
					</span>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<!-- ------------------付款计划 AND 其他信息--------------------------- -->
	<form method="post" id="quotePayMoneForm" action="./editQuoteAmount.do">
	<input type="hidden" name="beforeParams" value='${beforeParams}'><!-- 日志 -->
	<input type="hidden" value="${quote.customerNature}" id="quoteCustomerNature">
	<c:if test="${quote.customerNature eq 465}"><!-- 分销 -->
		<!-- 终端ID、名称、类型  地区和最小级ID -->
		<input type="hidden" name="terminalTraderName" id="terminalTraderName" class="terminal" value="${quote.terminalTraderName}"/> 
		<input type="hidden" name="terminalTraderId" id="terminalTraderId" class="terminal" value="${quote.terminalTraderId}"/> 
		<input type="hidden" name="terminalTraderType" id="terminalTraderType" class="terminal" value="${quote.terminalTraderType}"/>
		<input type="hidden" name="salesArea" id="salesArea" class="terminal" value="${quote.salesArea}"/> 
		<input type="hidden" name="salesAreaId" id="salesAreaId" class="terminal" value="${quote.salesAreaId}"/> 
	</c:if>
		<div class="parts content1">
			<%-- <input type="hidden" name="terminalTraderName" id="terminalTraderName" class="terminal" value="${quote.terminalTraderName}"/>  --%>
			<input type="hidden" name="quoteorderId" id="quoteorderId" value="${quote.quoteorderId}" />
			<input type="hidden" name="quoteSource" id="quoteSource" value="${quoteSource}">
			<div class="formtitle mt10">付款计划</div>
			<ul class="payplan">
				<li>
					<div class="infor_name">
						<label>付款方式</label>
					</div>
					<div class="f_left inputfloat">
						<input type="hidden" id="paymentTypeHid" value="${quote.paymentType}">
						<c:choose>
							<c:when test="${quote.paymentType eq 0}">
								<select id="paymentType" name="paymentType" autocomplete="off" class="input-middle" onChange="totleMoney(this)">
									<c:forEach var="list" items="${paymentTermList}" varStatus="status">
										<option value="${list.sysOptionDefinitionId}"
											<c:if test="${list.sysOptionDefinitionId eq 419}">selected</c:if>>${list.title}</option>
									</c:forEach>
								</select>
							</c:when>
							<c:otherwise>
								<select id="paymentType" name="paymentType" autocomplete="off" class="input-middle" onChange="totleMoney(this)">
									<c:forEach var="list" items="${paymentTermList}" varStatus="status">
										<option value="${list.sysOptionDefinitionId}"
											<c:if test="${list.sysOptionDefinitionId eq quote.paymentType}">selected</c:if>>${list.title}</option>
									</c:forEach>
								</select>
							</c:otherwise>
						</c:choose>
					</div>
				</li>
				<li>
					<div class="infor_name">
						<label>预付金额</label>
					</div>

					<div class="f_left">
						<input type="text" class="input-middle" autocomplete="off" id="prepaidAmount" name="prepaidAmount" 
							<c:if test="${quote.paymentType ne 424}">
								readonly
							</c:if>
							<c:choose>
								<c:when test="${quote.prepaidAmount == '0.00' && quote.paymentType == 419}">
									value="${quote.totalAmount}"
								</c:when>
								<c:otherwise>
									value="${quote.prepaidAmount}"
								</c:otherwise>
							</c:choose>>
					</div>
					<div id="prepaidAmountError"></div>
				</li>
				<li id="accountPeriodLi"
					<c:if test="${(quote.paymentType eq 419) or (quote.paymentType eq 0)}">style="display:none"</c:if>>
					<!-- 419先款后货100%预付:0默认不显示 -->
					<div class="infor_name ">
						<label>账期支付</label>
					</div>
					<div class="f_left inputfloat">
						<!-- 账期支付最大限额（剩余账期额度） -->
						<input type="hidden" id="accountPeriodLeft" value="<fmt:formatNumber type="number" value="${customer.accountPeriodLeft}" pattern="0.00" maxFractionDigits="2" />">
						<input type="text" class="input-middle" name="accountPeriodAmount" id="accountPeriodAmount" value="<fmt:formatNumber type="number" value="${quote.accountPeriodAmount}" pattern="0.00" maxFractionDigits="2" />">
						<input type="checkbox" style="margin-top: 7px" name="logisticsCheckBox" id="logisticsCheckBox"
							<c:if test="${quote.logisticsCollection eq 1}">checked</c:if>>
						<label class="mt4">物流代收帐期款</label> 
						<input type="hidden" name="logisticsCollection" id="logisticsCollection" />
						<div id="accountPeriodAmountError"></div>
					</div>
				</li>
				<li id="retainageLi" <c:if test="${quote.paymentType ne 424}">style="display:none"</c:if>>
					<!-- 424先货后款自定义 -->
					<div class="infor_name ">
						<label>尾款</label>
					</div>
					<div class="f_left">
						<div>
							<input type="text" class="input-middle" name="retainageAmount" id="retainageAmount" value="<fmt:formatNumber type="number" value="${quote.retainageAmount}" pattern="0.00" maxFractionDigits="2" />">
							<label class="ml10 mr10">尾款期限</label> 
							<input type="text" class="input-smaller" name="retainageAmountMonth" id="retainageAmountMonth" value="${quote.retainageAmountMonth==0?'':quote.retainageAmountMonth}">
							<label>个月</label>
						</div>
						<!-- <div class="font-red">帐期支付金额超过帐期剩余额度</div> -->
						<div id="retainageAmountError"></div>
					</div>
				</li>
				<li id="accountAmountId" <c:if test="${(quote.paymentType eq 419) or (quote.paymentType eq 0)}">style="display:none"</c:if>>
					<!-- 419先款后货，预付100% -->
					<div class="infor_name "></div>
					<div class="f_left">
						<div class="font-grey9 mt4" id="retainageAmountError">
							客户当前帐期剩余额度<fmt:formatNumber type="number" value="${customer.accountPeriodLeft}" pattern="0.00" maxFractionDigits="2" />元，帐期天数${customer.periodDay}天；如需更改帐期，您需要在客户详情财务信息中申请帐期；
						</div>
					</div>
				</li>
			</ul>
		</div>
		<div class="line"></div>
		<div>
			<div class="formtitle">其他信息</div>
			<ul class="payplan">
				<li>
					<div class="infor_name">
						<span>*</span> <label>报价有效期</label>
					</div>
					<div class="f_left">
						<div class=" inputfloat" id="errorMsg">
							<input type="text" class="input-small" name="period" id="period"
								value="${quote.period==0?14:quote.period}"> <span
								class="mt4 mr10">天</span> <span class="font-grey9 mt4">不允许超过30天</span>
						</div>
					</div>
				</li>
				<li>
					<div class="infor_name ">
						<label>发票类型</label>
					</div> <!-- 默认选择普通发票 -->
					<!-- 获取当前日期 -->
					<jsp:useBean id="now" class="java.util.Date" />
					<fmt:formatDate value="${now}" type="both" dateStyle="long" var="today" pattern="yyyy-MM-dd"/>
					<div class="f_left">
						<select class="input-middle" name="invoiceType" id="invoiceType">
							<!-- 4月1号后税率只有13% -->
							<c:choose>
								<c:when test="${today >= '2019-04-01'}">
									<c:forEach var="list" items="${invoiceTypeList}" varStatus="status">
										<c:if test="${list.sysOptionDefinitionId eq 971 or list.sysOptionDefinitionId eq 972}"><!-- 屏蔽17%税率 -->
											<option value="${list.sysOptionDefinitionId}" id="${list.comments}"
												<c:if test="${list.sysOptionDefinitionId eq (invoice.invoiceType==null?972:invoice.invoiceType)}">selected</c:if>>${list.title}
											</option>
										</c:if>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<c:forEach var="list" items="${invoiceTypeList}" varStatus="status">
										<c:if test="${list.sysOptionDefinitionId eq 681 or list.sysOptionDefinitionId eq 682 or list.sysOptionDefinitionId eq 971 or list.sysOptionDefinitionId eq 972}">
											<option value="${list.sysOptionDefinitionId}"
												<c:if test="${list.sysOptionDefinitionId eq (quote.invoiceType==0?972:quote.invoiceType)}">selected</c:if>>${list.title}</option>
										</c:if>
									</c:forEach>
								</c:otherwise>
							</c:choose>
							
						</select>
					</div>
				</li>
				<li>
					<div class="infor_name">
						<label>运费说明</label>
					</div>
					<div class="f_left">
						<select class="input-large" name="freightDescription"
							id="freightDescription">
							<c:forEach var="list" items="${freightList}" varStatus="status">
								<option value="${list.sysOptionDefinitionId}"
									<c:if test="${list.sysOptionDefinitionId eq quote.freightDescription}">selected</c:if>>${list.title}</option>
							</c:forEach>
						</select>
					</div>
				</li>
				<li>
					<div class="infor_name">
						<label>附加条款</label>
					</div>
					<div class="f_left">
						<input type="text" class="input-large" placeholder="面向客户条款，客户可见"
							name="additionalClause" id="additionalClause"
							value="${quote.additionalClause}">
					</div>
				</li>
				<li>
					<div class="infor_name ">
						<label>内部备注</label>
					</div>
					<div class="f_left inputfloat">
						<input type="text" class="input-large" placeholder="仅内部可见"
							name="comments" id="comments" value="${quote.comments}">
					</div>
				</li>
			</ul>
		</div>
		<c:if test="${quote.followOrderStatus ne 2}">
			<!-- 未失单 -->
			<div class="add-tijiao ml110 mb15">
				<button type="button" class="bt-bg-style bg-deep-green" onclick="quotePayMoneSub()">确定</button>
			</div>
		</c:if>
		<input type="hidden" name="formToken" value="${formToken}"/>
	</form>

<%@ include file="../../common/footer.jsp"%>