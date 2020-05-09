<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="采购退货售后" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/finance/after/finance_after_buy.js?rnd=<%=Math.random()%>'></script>
<div class="main-container">
	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">基本信息</div>
		</div>
		<table class="table">
			<tbody>
				<tr>
					<td class="wid20">订单号</td>
					<td>${afterSalesVo.afterSalesNo}</td>
					<td class="wid20">订单状态</td>
					<td>
						<c:if test="${afterSalesVo.atferSalesStatus eq 0}">待确认</c:if>
						<c:if test="${afterSalesVo.atferSalesStatus eq 1}">进行中</c:if> 
						<c:if test="${afterSalesVo.atferSalesStatus eq 2}">已完结</c:if> 
						<c:if test="${afterSalesVo.atferSalesStatus eq 3}">已关闭</c:if>
					</td>
				</tr>
				<tr>
					<td>创建者</td>
					<td>
						<c:forEach items="${afterSalesVo.userList}" var="user">
							<c:if test="${afterSalesVo.creator eq user.userId}">${user.username}</c:if>
						</c:forEach>
					</td>
					<td>创建时间</td>
					<td><date:date value="${afterSalesVo.addTime}" /></td>
				</tr>
				<tr>
					<td>生效状态</td>
					<td>
						<c:if test="${afterSalesVo.validStatus eq 0}">未生效</c:if> 
						<c:if test="${afterSalesVo.validStatus eq 1}">已生效</c:if>
					</td>
					<td>生效时间</td>
					<td><date:date value="${afterSalesVo.validTime}" /></td>
				</tr>
				<tr>
					<td>审核状态</td>
					<td>
						<c:if test="${afterSalesVo.status eq 0}">待确认</c:if> 
						<c:if test="${afterSalesVo.status eq 1}">审核中</c:if> 
						<c:if test="${afterSalesVo.status eq 2}">审核通过</c:if> 
						<c:if test="${afterSalesVo.status eq 3}">审核不通过</c:if>
					</td>
					<td>售后类型</td>
					<td class="warning-color1">${afterSalesVo.typeName}</td>
				</tr>
			</tbody>
		</table>
	</div>

	<input type="hidden" name="afterSalesId" value="${afterSalesVo.afterSalesId}" />
	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">售后申请</div>
		</div>
		<table class="table">
			<tbody>
				<tr>
					<td class="wid20">售后原因</td>
					<td>${afterSalesVo.reasonName}</td>
					<td class="wid20">联系人</td>
					<td>${afterSalesVo.traderContactName}</td>
				</tr>
				<tr>
					<td>电话</td>
					<td>${afterSalesVo.traderContactTelephone}</td>
					<td>手机</td>
					<td>${afterSalesVo.traderContactMobile}</td>
				</tr>
				<tr>
					<td>款项退还</td>
					<td>
						<c:if test="${afterSalesVo.refund eq 0}">无</c:if> 
						<c:if test="${afterSalesVo.refund eq 1}">退至公司账户</c:if>
						<c:if test="${afterSalesVo.refund eq 2}">退至供应商余额</c:if>
					</td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td>详情说明</td>
					<td colspan="3" class="text-left">${afterSalesVo.comments}</td>

				</tr>
				<tr>
					<td>附件</td>
					<td colspan="3" class="text-left">
						<%@ include file="../../order/saleorder/view_afterSales_files.jsp"%>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">所属订单</div>
		</div>
		<table class="table">
			<tbody>
				<tr>
					<td class="wid20">订单号</td>
					<td>
						<div class="customername pos_rel">
							<span class="brand-color1 addtitle" tabTitle='{"num":"viewfinancebuyorder${afterSalesVo.orderId}","title":"订单信息",
										"link":"./finance/buyorder/viewBuyorder.do?buyorderId=${afterSalesVo.orderId}"}'>
								${afterSalesVo.orderNo}
							</span>
							<i class="iconbluemouth"></i>
							<div class="pos_abs customernameshow" style="display: none;">
								付款状态：
								<c:if test="${afterSalesVo.paymentStatus eq 0}">未付款</c:if>
								<c:if test="${afterSalesVo.paymentStatus eq 1}">部分付款</c:if>
								<c:if test="${afterSalesVo.paymentStatus eq 2}">全部付款</c:if>
								<br> 收货状态：
								<c:if test="${afterSalesVo.arrivalStatus eq 0}">未收货</c:if>
								<c:if test="${afterSalesVo.arrivalStatus eq 1}">部分收货</c:if>
								<c:if test="${afterSalesVo.arrivalStatus eq 2}">全部收货</c:if>
								<br> 收票状态：
								<c:if test="${afterSalesVo.invoiceStatus eq 0}">未开票</c:if>
								<c:if test="${afterSalesVo.invoiceStatus eq 1}">部分开票</c:if>
								<c:if test="${afterSalesVo.invoiceStatus eq 2}">全部开票</c:if>

							</div>
						</div>
					</td>
					<td class="wid20">订单金额</td>
					<td><fmt:formatNumber type="number" value="${afterSalesVo.totalAmount}" pattern="0.00" maxFractionDigits="2" /></td>
				</tr>
				<tr>
					<td>部门</td>
					<td>${afterSalesVo.orgName}</td>
					<td>归属采购</td>
					<td>
						<c:forEach items="${afterSalesVo.userList}" var="user">
							<c:if test="${afterSalesVo.userId eq user.userId}">${user.username}</c:if>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<td>订单状态</td>
					<td>
						<c:if test="${afterSalesVo.buyorderStatus eq 0}">待确认</c:if>
						<c:if test="${afterSalesVo.buyorderStatus eq 1}">进行中</c:if> 
						<c:if test="${afterSalesVo.buyorderStatus eq 2}">已完结</c:if> 
						<c:if test="${afterSalesVo.buyorderStatus eq 3}">已关闭</c:if>
					</td>
					<td>生效时间</td>
					<td><date:date value="${afterSalesVo.buyorderValidTime}" /></td>
				</tr>
				<tr>
					<td>供应商名称</td>
					<td>
						<div class="customername pos_rel">
							<span class="brand-color1 addtitle"
								tabTitle='{"num":"viewfinancesupplier${afterSalesVo.traderId}","title":"供应商信息",
										"link":"./trader/supplier/baseinfo.do?traderId=${afterSalesVo.traderId}"}'>
								${afterSalesVo.traderName}
							</span>
							<i class="iconbluemouth"></i>
							<div class="pos_abs customernameshow" style="display: none;">
								交易次数：${afterSalesVo.orderCount}<br>
								交易金额：<fmt:formatNumber type="number" value="${afterSalesVo.orderTotalAmount}" pattern="0.00" maxFractionDigits="2" /><br>
								上次交易日期：<date:date value="${afterSalesVo.lastOrderTime}" format="yyyy.MM"/>
							</div>
						</div>
					</td>
					<td>供应商等级</td>
					<td>
						<c:if test="${afterSalesVo.grade eq 59}">核心供应商</c:if> 
						<c:if test="${afterSalesVo.grade eq 60}">普通供应商</c:if>
					</td>
				</tr>
			</tbody>
		</table>
	</div>

	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">退货产品信息</div>
		</div>
		<table class="table  table-style6">
			<thead>
				<tr>
					<th class="wid4">序号</th>
                    <th class="wid8">SKU</th>
					<th class="wid25">产品名称</th>
					<th class="wid15">品牌</th>
					<th class="wid10">型号</th>
					<th class="wid10">物料编号</th>
					<th class="wid10">单价</th>
					<th class="wid8">数量</th>
					<th class="wid8">单位</th>
					<th class="wid8">直发</th>
					<th class="wid8">退货数量</th>
					<th class="wid10">已发货数量</th>
					<th class="wid8">退货方式</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty afterSalesVo.afterSalesGoodsList}">
					<c:forEach items="${afterSalesVo.afterSalesGoodsList}" var="asg" varStatus="sttaus">
						<tr class="J-skuInfo-tr" skuId="${asg.goodsId}">

							<td>${sttaus.count }</td>
							<td class="JskuCode"> </td>
							<td class="text-left">
								<div class="customername pos_rel">
									<span  style="float:none" class="brand-color1 addtitle JskuName"
										tabTitle='{"num":"viewgoods${asg.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${asg.goodsId}","title":"产品信息"}'>
										${asg.goodsName}
									</span>
									<i class="iconbluemouth"></i> <br>

									<div class="pos_abs customernameshow JskuInfo" style="display: none;">

									</div>
								</div>
							</td>
                            <td class="JbrandName"> </td>
                            <td  class="JskuModel"> </td>
                            <td class="JmaterialCode"> </td>
							<td>${asg.buyorderPrice}</td>
							<td>${asg.buyorderNum}</td>
							<td class="JskuUnit">${asg.unitName}</td>
							<td>
								<c:if test="${asg.buyorderDeliveryDirect eq 0}">否</c:if>
								<c:if test="${asg.buyorderDeliveryDirect eq 1}">是</c:if>
							</td>
							<td class="warning-color1">
								${asg.num}
								<c:set var="back_num" value="${back_num + asg.num}"></c:set> 
							</td>
							<td>${asg.deliveryNum}</td>
							<td class="warning-color1">
								<c:if test="${asg.deliveryDirect eq 0}">普发</c:if> 
								<c:if test="${asg.deliveryDirect eq 1}">直发</c:if>
							</td>
						</tr>
					</c:forEach>
					<tr>
						<td colspan="13" class="allchosetr text-left">
							退货总件数:<span class="warning-color1 mr10">${back_num}</span>
						</td>
					</tr>
				</c:if>
				<c:if test="${empty afterSalesVo.afterSalesGoodsList}">
					<tr>
						<td colspan="13">暂无记录</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>

	<!-- 等soctt完成 -->
	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">退货出库记录</div>
		</div>
		<table class="table  table-style6">
			<thead>
				<tr>
					<th class="wid6">序号</th>
					<th class="wid18">产品名称</th>
					<th class="wid12">品牌</th>
					<th class="wid8">型号</th>
					<th class="wid8">数量</th>
					<th class="wid5">单位</th>
					<th class="wid10">贝登条码</th>
					<th class="wid10">厂商条码</th>
					<th class="wid12">入库时间</th>
					<th class="wid6">操作人</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty afterSalesVo.afterReturnGoodsOutStorageList}">
					<c:forEach items="${afterSalesVo.afterReturnGoodsOutStorageList}" var="arg" varStatus="num_index">
						<tr>
							<td>${num_index.count }</td>
							<td class="text-left">
								<div class="customername pos_rel">
									<span class="brand-color1 addtitle"
										tabTitle='{"num":"viewgoods${arg.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${arg.goodsId}", "title":"产品信息"}'>
										${arg.goodsName}
									</span>
									<br>${arg.sku}
									<br>${arg.materialCode}
								</div>
							</td>
							<td>${arg.brandName}</td>
							<td>${arg.model}</td>
							<td>${arg.num}</td>
							<td>${arg.unitName}</td>
							<td>${arg.barcode}</td>
							<td>${arg.barcodeFactory}</td>
							<td><date:date value="${arg.addTime}" /></td>
							<td>
								<c:forEach var="user" items="${afterSalesVo.userList}">
									<c:if test="${user.userId eq arg.creator}">${user.username}</c:if>
								</c:forEach>
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty afterSalesVo.afterReturnGoodsOutStorageList}">
					<tr>
						<td colspan="10">暂无记录</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>

	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">退票信息</div>
		</div>
		<table class="table">
			<thead>
				<tr>
					<th>发票号</th>
					<th>开票日期</th>
					<th>发票金额</th>
					<th>票种</th>
					<th>寄送状态</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty afterSalesVo.afterSalesInvoiceVoList}">
					<c:forEach items="${afterSalesVo.afterSalesInvoiceVoList}" var="asi">
						<tr>
							<td>${asi.invoiceNo}</td>
							<td><date:date value="${asi.addTime}" format="yyyy.MM.dd"/></td>
							<td>${asi.amount}</td>
							<td>
								<c:forEach var="list" items="${invoiceTypeList}">
									<c:if test="${asi.invoiceType eq list.sysOptionDefinitionId}">${list.title}</c:if>
								</c:forEach>
							</td>
							<td>
								<span style="color: red">
									<c:choose>
										<c:when test="${(empty asi.expressId) or (asi.expressId eq 0)}">未寄送</c:when>
										<c:when test="${(not empty asi.expressId) and (asi.expressId ne 0)}">已寄送</c:when>
										<c:otherwise>--</c:otherwise>
									</c:choose>
								</span>
							</td>
							<td>
								<c:if test="${afterSalesVo.atferSalesStatus eq 1}"><!-- 售后单進行中 -->
									<!-- isRefundInvoice：是否需要退票；；status：退票状态 -->
									<c:choose>
										<c:when test="${asi.isRefundInvoice eq 1}">
											<c:if test="${asi.status eq 0}">
												<span class="font-blue addtitle" tabtitle='{"num":"after_return_invoice_after${asi.afterSalesId}","title":"售后退票",
													"link":"${pageContext.request.contextPath}/finance/after/getAfterReturnInvoiceInfo.do?afterSalesId=${afterSalesVo.afterSalesId}&afterSalesInvoiceId=${asi.afterSalesInvoiceId}"}'>确认退票</span>
											</c:if>
											<c:if test="${asi.status eq 1}"><span style="color: red">已退票</span></c:if>
										</c:when>
										<c:otherwise>
											<span style="color: red">无需退票</span>
										</c:otherwise>
									</c:choose>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty afterSalesVo.afterSalesInvoiceVoList}">
					<!-- 查询无结果弹出 -->
					<tr>
						<td colspan='6'>查询无结果！</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>

		
	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">录票信息</div>
		</div>
		<table class="table">
			<thead>
				<tr>
					<th>发票号</th>
					<th>发票金额</th>
					<th>票种</th>
					<th>红蓝字</th>
					<th>录票人员</th>
					<th>申请日期</th>
					<th>审核日期</th>
					<th>审核人</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty afterSalesVo.afterInInvoiceList}">
					<c:forEach items="${afterSalesVo.afterInInvoiceList}" var="aoi">
						<tr>
							<td>${aoi.invoiceNo}</td>
							<td>${aoi.amount}</td>
							<td>
								<c:forEach var="list" items="${invoiceTypeList}">
									<c:if test="${aoi.invoiceType eq list.sysOptionDefinitionId}">${list.title}</c:if>
								</c:forEach>
							</td>
							<td>
								<c:choose>
									<c:when test="${aoi.colorType eq 1}">
										<c:choose>
											<c:when test="${aoi.isEnable eq 0}">
												<span style="color: red">红字作废</span>
											</c:when>
											<c:otherwise>
												<span style="color: red">红字有效</span>
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${aoi.isEnable eq 0}">
												<span style="color: red">蓝字作废</span>
											</c:when>
											<c:otherwise>
												蓝字有效
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
							</td>
							<td>
								<c:forEach var="user" items="${afterSalesVo.userList}">
									<c:if test="${user.userId eq aoi.creator}">${user.username}</c:if>
								</c:forEach>
							</td>
							<td><date:date value="${aoi.addTime}" format="yyyy.MM.dd"/></td>
							<td><date:date value="${aoi.validTime}" format="yyyy.MM.dd"/></td>
							<td>
								<c:forEach var="user" items="${afterSalesVo.userList}">
									<c:if test="${user.userId eq aoi.validUserId}">${user.username}</c:if>
								</c:forEach>
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty afterSalesVo.afterInInvoiceList}">
					<!-- 查询无结果弹出 -->
					<tr>
						<td colspan='8'>查询无结果！</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>

	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">退款信息</div>
		</div>
		<table class="table">
			<thead>
				<tr>
					<th>已付款金额（不含信用支付）</th>
					<th>退货金额</th>
					<th>偿还账期</th>
					<th>售后退货手续费</th>
					<th>款项退还</th>
					<th>实退金额</th>
					<th>已退金额</th>
                    <th>退款状态</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>${afterSalesVo.payAmount}</td>
					<td>${afterSalesVo.refundAmount}</td>
					<td class="warning-color1">${afterSalesVo.payPeriodAmount}</td>
					<td class="warning-color1">${afterSalesVo.serviceAmount}</td>
					<td class="warning-color1">
						<c:if test="${afterSalesVo.refund eq 0}">无</c:if> 
						<c:if test="${afterSalesVo.refund eq 1}">退还到贝登账户客户</c:if> 
						<c:if test="${afterSalesVo.refund eq 2}">退到贝登在供应商处余额</c:if>
					</td>
                    <td class="warning-color1">${afterSalesVo.realRefundAmount}</td>
					<td class="warning-color1">${afterSalesVo.haveRefundAmount}</td>
                    <td class="warning-color1">
                    	<c:if test="${afterSalesVo.refundAmountStatus eq 0}">无退款</c:if>
                    	<c:if test="${afterSalesVo.refundAmountStatus eq 1}">未退款</c:if>
                    	<c:if test="${afterSalesVo.refundAmountStatus eq 2}">部分退款</c:if>
                    	<c:if test="${afterSalesVo.refundAmountStatus eq 3}">已退款</c:if>
                    </td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="tcenter mb15 mt-5">
       		<c:if test="${(null!=taskInfoPay and null!=taskInfoPay.getProcessInstanceId() and null!=taskInfoPay.assignee) or !empty candidateUserMapPay[taskInfoPay.id]}">
				<c:if test="${endStatusPay ne '财务审核'}">
					<c:choose>
						<c:when test="${taskInfoPay.assignee == curr_user.username or candidateUserMapPay['belong']}">
						<button type="button" class="bt-bg-style bg-light-green bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?taskId=${taskInfoPay.id}&pass=true&type=1"}'>审核通过</button>
						<button type="button" class="bt-bg-style bg-light-orange bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?taskId=${taskInfoPay.id}&pass=false&type=1"}'>审核不通过</button>
						</c:when>
						<c:otherwise>
	       				<button type="button" class="bt-bg-style bt-small bg-light-greybe mr10">已申请审核</button>
						</c:otherwise>
					</c:choose>
				</c:if>
				<c:if test="${endStatusPay eq '财务审核'}">
				<shiro:hasPermission name="/finance/capitalbill/showPaymentVerify.do">
					<c:choose>
						<c:when test="${taskInfoPay.assignee == curr_user.username or candidateUserMapPay['belong']}">
						<button type="button" class="bt-bg-style bg-light-green bt-small mr10 pop-new-data" layerParams='{"width":"600px","height":"350px","title":"新增交易记录","link":"./addFinanceAfterCapital.do?afterSalesId=${afterSalesVo.afterSalesId}&billType=2&payApplyId=${payApplyId}&taskId=${taskInfoPay.id}&pageType=1"}'>审核通过</button>
						<button type="button" class="bt-bg-style bg-light-orange bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?taskId=${taskInfoPay.id}&pass=false&type=1"}'>审核不通过</button>
						</c:when>
						<c:otherwise>
	       				<button type="button" class="bt-bg-style bt-small bg-light-greybe mr10">已申请审核</button>
						</c:otherwise>
					</c:choose>
				</shiro:hasPermission>
				</c:if>
			</c:if>
        </div>
	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">付款申请</div>
		</div>
		<table class="table  table-style6">
			<thead>
				<tr>
					<th class="wid6">申请金额</th>
					<th class="wid18">申请时间</th>
					<th class="wid12">申请人</th>
					<th class="wid8">交易名称</th>
					<th class="wid8">开户行及联行号</th>
					<th class="wid10">银行帐号</th>
					<th class="wid10">付款备注</th>
					<th class="wid5">审核状态</th>
					<th class="wid5">查看</th>
					
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty afterSalesVo.afterPayApplyList}">
					<c:forEach items="${afterSalesVo.afterPayApplyList}" var="apal" varStatus="num_index">
						<tr>
							<td><fmt:formatNumber type="number" value="${apal.amount}" pattern="0.00" maxFractionDigits="2" /></td>
							<td><date:date value="${apal.addTime}" format="yyyy.MM.dd HH:mm:ss"/></td>
							<td>
								<c:forEach var="user" items="${afterSalesVo.userList}">
									<c:if test="${user.userId eq apal.creator}">${user.username}</c:if>
								</c:forEach>
							</td>
							<td>${apal.traderName}</td>
							<td>
								${apal.bank}<br/>${apal.bankCode}
							</td>
							<td>${apal.bankAccount}</td>
							<td>${apal.comments}</td>
							<td>
								<c:choose>
									<c:when test="${apal.validStatus eq 0}">待审核</c:when>
									<c:when test="${apal.validStatus eq 1}">通过</c:when>
									<c:when test="${apal.validStatus eq 2}">不通过</c:when>
									<c:otherwise>--</c:otherwise>
								</c:choose>
							</td>
							<td>
		                        	<div class="caozuo">
		                        	<span class="caozuo-blue pop-new-data" layerparams='{"width":"50%","height":"30%","title":"付款申请审核信息","link":"<%=basePath%>finance/after/paymentVerify.do?payApplyId=${apal.payApplyId}"}'>查看</span>
									</div>
                        		</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty afterSalesVo.afterPayApplyList}">
					<tr>
						<td colspan="9">暂无记录</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>
	
	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">交易记录</div>
			<c:if test="${(afterSalesVo.atferSalesStatus eq 1) and (afterSalesVo.refund eq 1)}"><!-- 進行中::退到客户（退给我们） -->
				<c:if test="${(afterSalesVo.realRefundAmount<0?-afterSalesVo.realRefundAmount:afterSalesVo.realRefundAmount) > afterSalesVo.haveRefundAmount}">
					<div class="title-click nobor  pop-new-data" layerParams='{"width":"45%","height":"50%","title":"新增交易记录",
						"link":"${pageContext.request.contextPath}/finance/after/addFinanceAfterCapital.do?afterSalesId=${afterSalesVo.afterSalesId}"}'>新增交易记录</div>
				</c:if>
			</c:if>
		</div>
		<table class="table">
			<thead>
				<tr>
					<th class="wid18">记账编号</th>
					<th class="wid8">业务类型</th>
					<th class="wid8">交易金额</th>
					<th class="wid13">交易时间</th>
					<th class="wid8">交易主体</th>
					<th class="wid8">交易方式</th>
					<th class="wid18">交易名称</th>
					<th class="wid14">交易备注</th>
					<th class="wid8">操作人</th>
					<th class="wid13">操作时间</th>
					<th>电子回执单</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty afterSalesVo.afterCapitalBillList}">
					<c:forEach items="${afterSalesVo.afterCapitalBillList}" var="acb">
						<tr>
							<td>${acb.capitalBillNo}</td>
							<td>
								<c:forEach var="typeList" items="${bussinessTypes}" varStatus="">
									<c:if test="${typeList.sysOptionDefinitionId eq acb.capitalBillDetail.bussinessType}">${typeList.title}</c:if>
								</c:forEach>
							</td>
							<td><fmt:formatNumber type='number' value='${acb.amount}' pattern='0.00' maxFractionDigits='2' /></td>
							<td>
								<c:if test="${acb.traderTime != 0}">
									<date:date value="${acb.traderTime}" />
								</c:if>
							</td>
							<td>
								<c:if test="${acb.traderSubject == 1}">
	                        		对公
	                        	</c:if> 
	                        	<c:if test="${acb.traderSubject == 2}">
	                        		对私
	                        	</c:if>
	                        </td>
							<td>
								<c:forEach var="modeList" items="${traderModes}" varStatus="">
									<c:if test="${modeList.sysOptionDefinitionId eq acb.traderMode}">${modeList.title}</c:if>
								</c:forEach>
							</td>
							<td>${acb.payer}</td>
							<td class="text-left">${acb.comments}</td>
							<td>
								<c:forEach var="user" items="${afterSalesVo.userList}">
									<c:if test="${user.userId eq acb.creator}">${user.username}</c:if>
								</c:forEach>
							</td>
							<td>
								<c:if test="${acb.addTime != 0}">
									<date:date value="${acb.addTime}" />
								</c:if>
							</td>
							<td>
	                        	<c:if test="${(acb.traderType == 2 || acb.traderType == 5) && acb.bankBillId != 0}">
		                        	<div class="caozuo">
										<span class="caozuo-blue addtitle"   tabTitle='{"num":"credentials${acb.bankBillId}", "link":"<%=basePath%>finance/capitalbill/credentials.do?bankBillId=${acb.bankBillId}","title":"电子回执单"}'>查看</span>
									</div>
	                        	</c:if>
		                    </td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
	</div>

	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">沟通记录</div>
		</div>
		<table
			class="table table-bordered table-striped table-condensed table-centered">
			<thead>
				<tr>
					<th class="wid18">沟通时间</th>
					<th class="wid8">录音</th>
					<th class="wid8">联系人</th>
					<th class="wid10">联系方式</th>
					<th class="wid6">沟通方式</th>
					<th>沟通内容</th>
					<th>操作人</th>
					<th>下次联系日期</th>
					<th>下次沟通内容</th>
					<th>备注</th>
					<th>创建时间</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty communicateList}">
					<c:forEach items="${communicateList}" var="communicateRecord" varStatus="">
						<tr>
							<td>
								<date:date value="${communicateRecord.begintime} " />~<date:date value="${communicateRecord.endtime}" format="HH:mm:ss" />
							</td>
							<td><c:if test="${not empty communicateRecord.coidUri }">${communicateRecord.communicateRecordId }</c:if></td>
							<td>${communicateRecord.contactName}</td>
							<td>${communicateRecord.phone}</td>
							<td>${communicateRecord.communicateModeName}</td>
							<td>
								<ul class="communicatecontent ml0">
									<c:if test="${not empty communicateRecord.tag }">
										<c:forEach items="${communicateRecord.tag }" var="tag">
											<li class="bluetag" title="${tag.tagName}">${tag.tagName}</li>
										</c:forEach>
									</c:if>
								</ul>
							</td>
							<td>${communicateRecord.user.username}</td>
							<c:choose>
								<c:when test="${communicateRecord.isDone == 0 }">
									<td class="font-red">${communicateRecord.nextContactDate }</td>
								</c:when>
								<c:otherwise>
									<td>${communicateRecord.nextContactDate }</td>
								</c:otherwise>
							</c:choose>
							<td>${communicateRecord.nextContactContent}</td>
							<td>${communicateRecord.comments}</td>
							<td><date:date value="${communicateRecord.addTime} " /></td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty communicateList}">
					<!-- 查询无结果弹出 -->
					<tr>
						<td colspan='11'>查询无结果！请尝试使用其他搜索条件。</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>

	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">售后过程</div>
		</div>
		<table class="table">
			<thead>
				<tr>
					<th>沟通时间</th>
					<th>操作人</th>
					<th>售后内容</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty afterSalesVo.afterSalesRecordVoList}">
					<c:forEach items="${afterSalesVo.afterSalesRecordVoList}" var="asi">
						<tr>
							<td><date:date value="${asi.addTime} " /></td>
							<td>
								<c:forEach items="${afterSalesVo.userList}" var="user">
									<c:if test="${asi.creator eq user.userId}">${user.username}</c:if>
								</c:forEach>
							</td>
							<td>${asi.content}</td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
	</div>

		   <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                    审核记录
                </div>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                        <th>操作人</th>
                        <th>操作时间</th>
                        <th>操作事项</th>
                        <th>备注</th>
                    </tr>
                 </thead>
                 <tbody>   
					<c:if test="${null!=historicActivityInstance}">
                    <c:forEach var="hi" items="${historicActivityInstance}" varStatus="status">
                    <c:if test="${not empty  hi.activityName}">
                    <tr>
                    	<td>
                    	<c:choose>
							<c:when test="${hi.activityType == 'startEvent'}"> 
							${startUser}
							</c:when>
							<c:when test="${hi.activityType == 'intermediateThrowEvent'}">
							</c:when>
							<c:otherwise>
								<c:if test="${historicActivityInstance.size() == status.count}">
									${verifyUsers}
								</c:if>
								<c:if test="${historicActivityInstance.size() != status.count}">
									${hi.assignee}  
								</c:if>   
							</c:otherwise>
						</c:choose>
                    	
                    	
                    	</td>
                        <td><fmt:formatDate value="${hi.endTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        <td>
                        <c:choose>
									<c:when test="${hi.activityType == 'startEvent'}"> 
							开始
							</c:when>
									<c:when test="${hi.activityType == 'intermediateThrowEvent'}"> 
							结束
							</c:when>
							<c:otherwise>   
							${hi.activityName}  
							</c:otherwise>
						</c:choose>
						</td>
                        <td class="font-red">${commentMap[hi.taskId]}</td>
                    </tr>
                    </c:if>
                    </c:forEach>
                    </c:if>
                	<c:if test="${null==historicActivityInstance}">
                		<!-- 查询无结果弹出 -->
		           		<tr>
					       <td colspan='4'>暂无记录！</td>
					    </tr>
                	</c:if>
		       			
                </tbody>
            </table>
        </div>
        
    <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                   	付款审核记录 
                </div>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                        <th>操作人</th>
                        <th>操作时间</th>
                        <th>操作事项</th>
                        <th>备注</th>
                    </tr>
                 </thead>
                 <tbody>   
					<c:if test="${null!=historicActivityInstancePay}">
                    <c:forEach var="hip" items="${historicActivityInstancePay}" varStatus="status">
                    <c:if test="${not empty  hip.activityName}">
                    <tr>
                    	<td>
                    	<c:choose>
							<c:when test="${hip.activityType == 'startEvent'}"> 
							${startUser}
							</c:when>
							<c:when test="${hip.activityType == 'intermediateThrowEvent'}">
							</c:when>
							<c:otherwise>
								<c:if test="${historicActivityInstancePay.size() == status.count}">
									${verifyUsersPay}
								</c:if>
								<c:if test="${historicActivityInstancePay.size() != status.count}">
									${hip.assignee}  
								</c:if>   
							</c:otherwise>
						</c:choose>
                    	
                    	
                    	</td>
                        <td><fmt:formatDate value="${hip.endTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        <td>
                        <c:choose>
									<c:when test="${hip.activityType == 'startEvent'}"> 
							开始
							</c:when>
									<c:when test="${hip.activityType == 'intermediateThrowEvent'}"> 
							结束
							</c:when>
							<c:otherwise>   
							${hip.activityName}  
							</c:otherwise>
						</c:choose>
						</td>
                        <td class="font-red">${commentMapPay[hip.taskId]}</td>
                    </tr>
                    </c:if>
                    </c:forEach>
                    </c:if>
                	<c:if test="${null==historicActivityInstancePay}">
                		<!-- 查询无结果弹出 -->
		           		<tr>
					       <td colspan='4'>暂无记录！</td>
					    </tr>
                	</c:if>
		       			
                </tbody>
            </table>
        </div>

	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">合同回传</div>
		</div>
		<table class="table">
			<thead>
				<tr>
					<th>合同</th>
					<th>操作人</th>
					<th>时间</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty afterSalesVo.afterContractAttachmentList}">
					<c:forEach items="${afterSalesVo.afterContractAttachmentList}" var="aca">
						<tr>
							<td class="font-blue"><a href="http://${aca.domain}${aca.uri}" target="_blank">${aca.name}</a></td>
							<td>
								<c:forEach var="user" items="${afterSalesVo.userList}">
									<c:if test="${user.userId eq aca.creator}">${user.username}</c:if>
								</c:forEach>
							</td>
							<td><date:date value="${aca.addTime}" /></td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
	</div>
</div>
<%@ include file="../../common/footer.jsp"%>
