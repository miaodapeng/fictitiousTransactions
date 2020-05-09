<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="销售退货-售后" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/finance/after/finance_after_sales.js?rnd=<%=Math.random()%>'></script>
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
						<c:forEach var="user" items="${afterSalesVo.userList}">
							<c:if test="${user.userId eq afterSalesVo.creator}">${user.username}</c:if>
						</c:forEach>
					</td>
					<td>创建时间</td>
					<td><date:date value="${afterSalesVo.addTime}" /></td>
				</tr>
				<tr>
					<td>生效状态</td>
					<td><c:if test="${afterSalesVo.validStatus eq 0}">未生效</c:if> 
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
				<c:choose>
					<c:when test="${afterSalesVo.refund eq 2}">
						<tr>
							<td>款项退还</td>
							<td>
								<c:if test="${afterSalesVo.refund eq 0}">无</c:if> 
								<c:if test="${afterSalesVo.refund eq 1}">退到客户余额</c:if> 
								<c:if test="${afterSalesVo.refund eq 2}">退给客户</c:if>
							</td>
							<td>交易主体</td>
							<td>
								<c:if test="${afterSalesVo.traderSubject eq 1}">对公</c:if>
								<c:if test="${afterSalesVo.traderSubject eq 2}">对私</c:if>
							</td>
						</tr>
						<tr>
							<td>收款名称</td>
							<td>${afterSalesVo.payee}</td>
							<td>开户银行</td>
							<td>${afterSalesVo.bank}</td>
						</tr>
						<tr>
							<td>开户行支付联行号</td>
							<td>${afterSalesVo.bankCode}</td>
							<td>银行账号</td>
							<td>${afterSalesVo.bankAccount}</td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr>
							<td>款项退还</td>
							<td>
								<c:if test="${afterSalesVo.refund eq 0}">无</c:if> 
								<c:if test="${afterSalesVo.refund eq 1}">退到客户余额</c:if> 
							</td>
							<td></td>
							<td></td>
						</tr>
					</c:otherwise>
				</c:choose>
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
							<span style="float:none"  class="brand-color1 addtitle" tabTitle='{"num":"viewfinancesaleorder${afterSalesVo.orderId}","title":"订单信息",
                               	"link":"./finance/invoice/viewSaleorder.do?saleorderId=${afterSalesVo.orderId}"}'>
                               	${afterSalesVo.orderNo}
							</span>
							<i class="iconbluemouth"></i>
							<div class="pos_abs customernameshow" style="display: none;">
								付款状态：
								<c:if test="${afterSalesVo.paymentStatus eq 0}">未付款</c:if>
								<c:if test="${afterSalesVo.paymentStatus eq 1}">部分付款</c:if>
								<c:if test="${afterSalesVo.paymentStatus eq 2}">全部付款</c:if>
								<br> 发货状态：
								<c:if test="${afterSalesVo.deliveryStatus eq 0}">未发货</c:if>
								<c:if test="${afterSalesVo.deliveryStatus eq 1}">部分发货</c:if>
								<c:if test="${afterSalesVo.deliveryStatus eq 2}">全部发货</c:if>
								<br> 开票状态：
								<c:if test="${afterSalesVo.invoiceStatus eq 0}">未开票</c:if>
								<c:if test="${afterSalesVo.invoiceStatus eq 1}">部分开票</c:if>
								<c:if test="${afterSalesVo.invoiceStatus eq 2}">全部开票</c:if>
								<br> 收货状态：
								<c:if test="${afterSalesVo.arrivalStatus eq 0}">未收货</c:if>
								<c:if test="${afterSalesVo.arrivalStatus eq 1}">部分收货</c:if>
								<c:if test="${afterSalesVo.arrivalStatus eq 2}">全部收货</c:if>
							</div>
						</div>
					</td>
					<td class="wid20">订单金额</td>
					<td><fmt:formatNumber type="number" value="${afterSalesVo.totalAmount}" pattern="0.00" maxFractionDigits="2" /></td>
				</tr>
				<tr>
					<td>部门</td>
					<td>${afterSalesVo.orgName}</td>
					<td>归属销售</td>
					<td>
						<c:forEach var="user" items="${afterSalesVo.userList}">
							<c:if test="${user.userId eq afterSalesVo.userId}">${user.username}</c:if>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<td>订单状态</td>
					<td><c:if test="${afterSalesVo.saleorderStatus eq 0}">未生效</c:if>
						<c:if test="${afterSalesVo.saleorderStatus eq 1}">已生效</c:if></td>
					<td>生效时间</td>
					<td><date:date value="${afterSalesVo.saleorderValidTime}" /></td>
				</tr>
				<tr>
					<td>客户名称</td>
					<td  >
						<div class="customername pos_rel">
							<span style="float:none" class="brand-color1 addtitle" tabTitle='{"num":"viewcustomer${afterSalesVo.traderId}","title":"客户信息",
								"link":"./trader/customer/baseinfo.do?traderId=${afterSalesVo.traderId}"}'>${afterSalesVo.traderName}
							</span>
							<i class="iconbluemouth"></i>
							<div class="pos_abs customernameshow" style="display: none;">
								客户性质：
								<c:if test="${afterSalesVo.customerNature eq 465}">分销</c:if>
								<c:if test="${afterSalesVo.customerNature eq 466}">终端</c:if>
								<br> 交易次数：${afterSalesVo.orderCount}<br>
								交易金额：<fmt:formatNumber type="number" value="${afterSalesVo.orderTotalAmount}" pattern="0.00" maxFractionDigits="2" /><br>
								上次交易日期：<date:date value="${afterSalesVo.lastOrderTime}" format="yyyy.MM"/>
							</div>
						</div>
					</td>
					<td>客户等级</td>
					
					<td><c:if test="${afterSalesVo.customerLevel eq 146}">A</c:if>
						<c:if test="${afterSalesVo.customerLevel eq 147}">B</c:if> 
						<c:if test="${afterSalesVo.customerLevel eq 148}">C</c:if> 
						<c:if test="${afterSalesVo.customerLevel eq 149}">D</c:if> 
						<c:if test="${afterSalesVo.customerLevel eq 150}">E</c:if>
						<c:if test="${afterSalesVo.customerLevel eq 931}">S</c:if>
					</td>
				</tr>
			</tbody>
		</table>
	</div>

	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">退货信息</div>
		</div>
		<table class="table  table-style6">
			<thead>
				<tr>
					<th class="wid5">序号</th>
					<th class="wid8">SKU</th>
					<th class="">产品名称</th>
					<th class="">品牌</th>
					<th class="">型号</th>
					<th class="">物料编号</th>
					<th class="wid10">单价</th>
					<th class="wid5">数量</th>
					<th class="wid5">单位</th>
					<th class="wid9">合计</th>
					<th class="wid5">直发</th>
					<th class="wid8">已发货数量</th>
					<th class="wid6">退货数量</th>
					<th class="wid8">已退货数量</th>
					<th class="wid8">退货方式</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty afterSalesVo.afterSalesGoodsList}">
					<c:set var="back_num" value="0"></c:set> 
					<c:set var="sum" value="0"></c:set>
                    <c:set var="deliveryNum" value="0"></c:set>
                    <c:set var="nowSalesNum" value="0"></c:set><!-- 系统拦截出库数量 -->
					<c:forEach items="${afterSalesVo.afterSalesGoodsList}" var="asg" varStatus="sttaus">
						<c:set var="deliveryNum" value="${deliveryNum+asg.deliveryNum}"></c:set>
                        <c:set var="nowSalesNum" value="${nowSalesNum+asg.nowSalesNum}"></c:set>
                        <c:set var="sum" value="${sum+asg.num}"></c:set>
						<tr class="J-skuInfo-tr" skuId="${asg.goodsId}">

							<td>${sttaus.count }</td>
							<td class="JskuCode"> </td>
							<td class="text-left">
								<div class="customername pos_rel">
									<span style="float:none" class="brand-color1 addtitle JskuName" tabTitle='{"num":"viewgoods${list.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${asg.goodsId}", "title":"产品信息"}'>
										${asg.goodsName}
									</span>
									<i class="iconbluemouth"></i> 

									<div class="pos_abs customernameshow JskuInfo" style="display: none;">

									</div>
								</div>
							</td>
							<td class="JbrandName"> </td>
							<td  class="JskuModel"> </td>
							<td class="JmaterialCode"> </td>
							<td>${asg.saleorderPrice}</td>
							<td>${asg.saleorderNum}</td>
							<td class="JskuUnit">${asg.unitName}</td>
							<td><fmt:formatNumber type="number" value="${asg.saleorderNum * asg.saleorderPrice}" pattern="0.00" maxFractionDigits="2" /></td>
							<td>
								<c:if test="${asg.saleorderDeliveryDirect eq 0}">否</c:if>
								<c:if test="${asg.saleorderDeliveryDirect eq 1}">是</c:if>
							</td>
							<td style="color:red;">${asg.outcnt}</td>
							<td style="color:red;">${asg.num}</td>
							<td style="color:red;">
								${asg.incnt}
								<c:set var="back_num" value="${back_num + asg.num}"></c:set> 
							</td>
							<td style="color:red;">
								<c:if test="${asg.deliveryDirect eq 0}">普发</c:if> 
								<c:if test="${asg.deliveryDirect eq 1}">直发</c:if>
							</td>
						</tr>
					</c:forEach>
					<tr>
						<td colspan="15" class="allchosetr text-left">
							退货总件数:<span class="warning-color1 mr10">${sum}</span>&nbsp;&nbsp;
                            	 系统拦截出库数量:<span class="warning-color1 mr10">
                            	 <c:if test="${sum + deliveryNum le nowSalesNum }">${sum}</c:if>
                            	 <c:if test="${sum + deliveryNum gt nowSalesNum }">${nowSalesNum-deliveryNum > 0 ? nowSalesNum-deliveryNum : 0}</c:if>
                            	 </span>
						</td>
					</tr>
				</c:if>
				<c:if test="${empty afterSalesVo.afterSalesGoodsList}">
					<tr>
						<td colspan="15">暂无记录</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>
	<%-- <div class="parts">
		<div class="title-container">
			<div class="table-title nobor">售后服务费</div>
		</div>
		<table class="table">
			<tbody>
				<tr>
					<td>退换货手续费</td>
					<td>${afterSalesVo.serviceAmount }</td>
					<td>票种</td>
					<td>
						<c:forEach var="list" items="${invoiceTypeList}">
							<c:if test="${afterSalesVo.invoiceType eq list.sysOptionDefinitionId}">${list.title}</c:if>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<td>收票客户</td>
					<td>${afterSalesVo.invoiceTraderName }</td>
					<td>收票联系人</td>
					<td>${afterSalesVo.invoiceTraderContactName }</td>
				</tr>
				<tr>
					<td>电话</td>
					<td>${afterSalesVo.invoiceTraderContactTelephone }</td>
					<td>手机</td>
					<td>${afterSalesVo.invoiceTraderContactMobile }</td>
				</tr>
				<tr>
					<td>收票地区</td>
					<td>${afterSalesVo.invoiceTraderArea }</td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td>收票地址</td>
					<td colspan="3">${afterSalesVo.invoiceTraderAddress }</td>
				</tr>
				<tr>
					<td>开票备注</td>
					<td colspan="3">${afterSalesVo.invoiceComments }</td>
				</tr>
			</tbody>
		</table>
	</div> --%>
	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">售后服务费与票</div>
		</div>
		<table class="table">
			<tbody>
				<tr>
					<td>售后退换货手续费</td>
					<td><fmt:formatNumber type="number" value="${afterSalesVo.serviceAmount}" pattern="0.00" maxFractionDigits="2" /></td>
					<td>票种</td>
					<td>
						<c:forEach var="list" items="${invoiceTypeList}">
							<c:if test="${afterSalesVo.invoiceType eq list.sysOptionDefinitionId}">${list.title}</c:if>
						</c:forEach>
						<c:if test="${afterSalesVo.isSendInvoice eq 1}">（寄送）</c:if>
                    	<c:if test="${afterSalesVo.isSendInvoice eq 0}">（不寄送）</c:if>
					</td>
				</tr>
				<tr>
                    <td>收票客户</td>
                    <td>${afterSalesVo.invoiceTraderName }</td>
                    <td>收票联系人</td>
                    <td>${afterSalesVo.invoiceTraderContactName }</td>
                </tr>
                <tr>
                    <td>电话</td>
                    <td>${afterSalesVo.invoiceTraderContactTelephone }</td>
                    <td>手机</td>
                    <td>${afterSalesVo.invoiceTraderContactMobile }</td>
                </tr>
                <tr>
                    <td>税务登记号</td>
                    <td>${afterSalesVo.taxNum }</td>
                    <td>注册地址</td>
                    <td>${afterSalesVo.regAddress }</td>
                </tr>
                <tr>
                    <td>注册电话</td>
                    <td>${afterSalesVo.regTel }</td>
                    <td>开户银行</td>
                    <td>${afterSalesVo.bank }</td>
                </tr>
                <tr>
                	<td>银行账号</td>
                    <td>${afterSalesVo.bankAccount }</td>
                    <td>收票地区</td>
                    <td>${afterSalesVo.invoiceTraderArea }</td>
                </tr>
                <tr>
                    <td>收票地址</td>
                    <td colspan="3">${afterSalesVo.invoiceTraderAddress }</td>
                </tr>
                <tr>
                    <td>开票备注</td>
                    <td colspan="3">${afterSalesVo.invoiceComments }</td>
                </tr>
			</tbody>
		</table>
		<c:if test="${afterSalesVo.atferSalesStatus eq 1 and afterSalesVo.realRefundAmount > 0}"><!-- 售后订单进行中:实际退款金额大于零 -->
			<c:if test="${afterSalesVo.refundAmountStatus eq 0}"><!-- 未退到余额：：：或需要退还金额为零 -->
				<c:if test="${empty afterSalesVo.afterCapitalBillList or afterSalesVo.afterCapitalBillList.size() == 0}">
					<div class="table-buttons">
						<button type="button" class="bt-bg-style bg-light-orange bt-small mr10" onclick="confirmRefundAmount(${afterSalesVo.afterSalesId},${afterSalesVo.subjectType},${afterSalesVo.type},${afterSalesVo.status},${afterSalesVo.atferSalesStatus});">确认退款</button>
					</div>
				</c:if>
			</c:if>
			<%-- <c:choose>
				<c:when test="${afterSalesVo.refund eq 2}"><!-- refund=2：退给客户 -->
					<c:if test="${afterSalesVo.refundAmountStatus eq 0}"><!-- 未退到余额 -->
						<div class="table-buttons">
							<button type="button" class="bt-bg-style bg-light-orange bt-small mr10" onclick="confirmRefundAmount(${afterSalesVo.afterSalesId},${afterSalesVo.subjectType},${afterSalesVo.type},${afterSalesVo.status},${afterSalesVo.atferSalesStatus});">确认退款</button>
						</div>
					</c:if>
				</c:when>
				<c:when test="${afterSalesVo.refund eq 1}"><!-- refund=1：退到余额 -->
					
				</c:when>
			</c:choose> --%>
		</c:if>
	</div>
	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">退货入库记录</div>
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
				<c:set var="storage_num" value="0"></c:set><!-- 入库数量 -->
				<c:if test="${not empty afterSalesVo.afterReturnGoodsInStorageList}">
					<c:forEach items="${afterSalesVo.afterReturnGoodsInStorageList}" var="arg" varStatus="num_index">
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
							<td>
								${arg.incnt}
								<c:set var="storage_num" value="${storage_num + arg.incnt}"></c:set>
							</td>
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
				<c:if test="${empty afterSalesVo.afterReturnGoodsInStorageList}">
					<tr>
						<td colspan="10">暂无记录</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>

	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">退票记录</div>
		</div>
		<table class="table">
			<thead>
				<tr>
					<th>发票号</th>
					<th>发票代码</th>
					<th>开票日期</th>
					<th>发票金额</th>
					<th>票种</th>
					<th>寄送状态</th>
					<th>是否需寄回</th>
					<th>退票状态</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<!-- 根据退票状态，判断是否能开票 -->
				<c:set var="isOpenInvoice" value="0"></c:set>
				<c:if test="${not empty afterSalesVo.afterSalesInvoiceVoList}">
					<c:forEach items="${afterSalesVo.afterSalesInvoiceVoList}" var="asi">
						<tr>
							<td>${asi.invoiceNo}</td>
							<td>${asi.invoiceCode}</td>
							<td><date:date value="${asi.addTime}" format="yyyy.MM.dd"/></td>
							<td><fmt:formatNumber type="number" value="${asi.amount}" pattern="0.00" maxFractionDigits="2" /></td>
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
								<span style="color: red">
									<c:if test="${asi.isSendInvoice eq 0}">否</c:if> 
									<c:if test="${asi.isSendInvoice eq 1}">是</c:if>
								</span>
							</td>
							<td>
								<span style="color: red">
									<c:if test="${asi.status eq 0}">未退票</c:if> 
									<c:if test="${asi.status eq 1}">已退票</c:if>
								</span>
							</td>
							<td>
								<c:if test="${afterSalesVo.atferSalesStatus eq 1}"><!-- 售后单進行中 -->
									<!-- isRefundInvoice：是否需要退票；；status：退票状态 -->
									<c:choose>
										<c:when test="${asi.isRefundInvoice eq 1}">
											<!-- isRefundInvoice：是否需退票0否1是 	status：退票状态0未退票1已退票 -->
											<c:if test="${asi.status eq 0}">
												<c:if test="${asi.invoiceProperty eq 2}"><!-- 电子发票 -->
													<span class="forbid clcforbid" onclick="cancelEInvoicePush(${asi.invoiceId},${afterSalesVo.afterSalesId})">作废电子发票</span>
												</c:if>
												<c:if test="${asi.invoiceProperty eq 1}"><!-- 纸质发票 -->
													<span class="font-blue addtitle" tabtitle='{"num":"after_return_invoice_after${asi.afterSalesId}","title":"售后退票",
														"link":"${pageContext.request.contextPath}/finance/after/getAfterReturnInvoiceInfo.do?afterSalesId=${afterSalesVo.afterSalesId}&afterSalesInvoiceId=${asi.afterSalesInvoiceId}"}'>确认退票</span>
												</c:if>
											</c:if>
											<c:if test="${asi.status eq 1}">
												<c:set var="isOpenInvoice" value="${isOpenInvoice+1}"></c:set>
											</c:if>
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
						<td colspan='9'>查询无结果！</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>

	<!-- 注：售后订单中只开具退换货手续费的发票，其他发票放在销售订单中 -->
	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">发票记录</div>
			<c:if test="${afterSalesVo.atferSalesStatus eq 1}"><!-- 進行中 -->
				<c:if test="${isOpenInvoice eq fn:length(afterSalesVo.afterSalesInvoiceVoList)}"><!-- 全部退票 -->
					<c:if test="${((afterSalesVo.isCanApplyInvoice eq 1 and afterSalesVo.companyId eq 1) or (invoiceApply.validStatus eq 1 and afterSalesVo.companyId ne 1)) and afterSalesVo.eFlag eq 'cw'}">
						<div class="title-click nobor addtitle" tabTitle='{"num":"at_add_invoice${afterSalesVo.afterSalesId}","title":"退货新增发票",
							"link":"${pageContext.request.contextPath}/finance/after/addAfterInvoiceTp.do?afterId=${afterSalesVo.afterSalesId}&relatedId=${afterSalesVo.orderId}&afterType=${afterSalesVo.type}"}'>
							新增发票
						</div>
					</c:if>
				</c:if>
			</c:if> 
		</div>
		<table class="table">
			<thead>
				<tr>
					<th>发票号</th>
					<th>票种</th>
					<th>红蓝字</th>
					<th>发票金额</th>
					<th>操作人</th>
					<th>操作时间</th>
					<th>快递公司</th>
					<th>快递单号</th>
					<th>快递状态</th>
					<th>备注</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty afterSalesVo.afterOpenInvoiceList}">
					<c:forEach items="${afterSalesVo.afterOpenInvoiceList}" var="aoi">
						<tr>
							<td>${aoi.invoiceNo}</td>
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
							<td>${aoi.amount}</td>
							<td>
								<c:forEach var="user" items="${afterSalesVo.userList}">
									<c:if test="${user.userId eq aoi.creator}">${user.username}</c:if>
								</c:forEach>
							</td>
							<td><date:date value="${aoi.addTime}" /></td>
							<!--快递公司名 -->
							<td>${aoi.logisticsName}</td>
							<!--快递单号 -->
							<td>${aoi.logisticsNo}</td>
							<!--快递状态 -->
							<td>
								<c:choose>
									<c:when test="${aoi.arrivalStatus eq 0}">
										未收货
									</c:when>
									<c:when test="${aoi.arrivalStatus eq 1}">
										部分收货
									</c:when>
									<c:when test="${aoi.arrivalStatus eq 2}">
										全部收货
									</c:when>
								</c:choose>
							</td>
							<!--物流备注 -->
							<td>${aoi.logisticsComments}</td>
							<!--操作 -->
							<td>
	                        	<c:choose>
	                        		<c:when test="${empty aoi.expressId}">
	                        				<a class="pop-new-data" layerParams='{"width":"500px","height":"280px","title":"寄送发票","link":"../../finance/invoice/sendSaleInvoice.do?invoiceId=${aoi.invoiceId}"}'>寄送发票</a>
	                        				<%-- <c:choose>
												<c:when test="${list.type eq 504}"><!-- 售后发票 -->
													<!-- 售后单2：已完结，3：已关闭 -->
													<c:if test="${(list.atferStatus eq 2) or (list.atferStatus eq 3)}">
							                        	<a class="pop-new-data" layerParams='{"width":"500px","height":"280px","title":"寄送发票","link":"./sendSaleInvoice.do?invoiceId=${list.invoiceId}"}'>寄送发票</a>
													</c:if>
												</c:when>
												<c:otherwise>
													<a class="pop-new-data" layerParams='{"width":"500px","height":"280px","title":"寄送发票","link":"./sendSaleInvoice.do?invoiceId=${list.invoiceId}"}'>寄送发票</a>
												</c:otherwise>
											</c:choose> --%>
	                        		</c:when>
	                        		<c:otherwise>
	                        			<!-- 未签收,则可以编辑 -->
	                        			<c:choose>
	                        				<c:when test="${aoi.arrivalStatus != 2}">
	                        					<a class="pop-new-data" layerParams='{"width":"570px","height":"300px","link":"../../finance/invoice/editExpressView.do?expressId=${aoi.expressId}&invoiceId=${aoi.invoiceId}&invoiceNo=${aoi.invoiceNo}","title":" 编辑快递"}'>编辑</a>
	                        				</c:when>
	                        				<c:otherwise>
	                        					已寄送
	                        				</c:otherwise>
	                        			</c:choose>
	                        		</c:otherwise>
	                        	</c:choose>
	                        </td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty afterSalesVo.afterOpenInvoiceList}">
					<!-- 查询无结果弹出 -->
					<tr>
						<td colspan='11'>查询无结果！</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>

	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">付款申请</div>
			<%-- <span class="title-click nobor  pop-new-data" layerParams='{"width":"45%","height":"45%","title":"新增交易记录",
				"link":"${pageContext.request.contextPath}/finance/after/addFinanceAfterCapital.do?afterSalesId=${afterSalesVo.afterSalesId}&payApplyId=16133&billType=2"}'>新增交易记录</span> --%>
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
	<c:if test="${invoiceApply != null && companyId != 1}">
	<div class="tcenter mb15 mt-5">
        		<c:choose>
				<c:when test="${invoiceApply.validStatus == null || invoiceApply.validStatus != 1}">
				<c:if test="${(null!=taskInfoInvoice and null!=taskInfoInvoice.getProcessInstanceId() and null!=taskInfoInvoice.assignee) or !empty candidateUserMapInvoice[taskInfoInvoice.id]}">
					<c:choose>
						<c:when test="${taskInfoInvoice.assignee == curr_user.username or candidateUserMapInvoice['belong']}">
						<button type="button" class="bt-bg-style bg-light-green bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"../../finance/invoice/complement.do?taskId=${taskInfoInvoice.id}&pass=true&type=1"}'>审核通过</button>
						<button type="button" class="bt-bg-style bg-light-orange bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"../../finance/invoice/complement.do?taskId=${taskInfoInvoice.id}&pass=false&type=1"}'>审核不通过</button>
						</c:when>
						<c:otherwise>
        						<button type="button" class="bt-bg-style bt-small bg-light-greybe mr10">已申请审核</button>
						</c:otherwise>
					</c:choose>
				</c:if>
				</c:when>
			</c:choose>
    </div>
    </c:if>
        
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
					<th>退货手续费</th>
					<th>款项退还</th>
					<th>实退金额</th>
					<th>已退金额</th>
					<th>退款状态</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><fmt:formatNumber type="number" value="${afterSalesVo.payAmount}" pattern="0.00" maxFractionDigits="2" /></td>
					<td><fmt:formatNumber type="number" value="${afterSalesVo.refundAmount}" pattern="0.00" maxFractionDigits="2" /></td>
					<td>
						<span style="color: red">
							<%-- <c:set var="zaAmount" value="${afterSalesVo.refundAmount - afterSalesVo.serviceAmount - afterSalesVo.realRefundAmount}"></c:set> --%>
							<fmt:formatNumber type="number" value="${afterSalesVo.payPeriodAmount}" pattern="0.00" maxFractionDigits="2" />
						</span>
					</td>
					<td>
						<span style="color: red">
							<fmt:formatNumber type="number" value="${afterSalesVo.serviceAmount}" pattern="0.00" maxFractionDigits="2" />
						</span>
					</td>
					<td>
						<span style="color: red">
							<c:if test="${afterSalesVo.refund eq 0}">无</c:if>
							<c:if test="${afterSalesVo.refund eq 1}">退到客户余额</c:if>
							<c:if test="${afterSalesVo.refund eq 2}">退给客户</c:if>
						</span>
					</td>
					<td>
						<span style="color: red">
							<fmt:formatNumber type="number" value="${afterSalesVo.realRefundAmount}" pattern="0.00" maxFractionDigits="2" />
						</span>
					</td>
					<td>
						<span style="color: red">
							<fmt:formatNumber type="number" value="${afterSalesVo.haveRefundAmount==null?0:afterSalesVo.haveRefundAmount}" pattern="0.00" maxFractionDigits="2" />
						</span>
					</td>
                    <td>
                    	<span style="color: red">
	                    	<c:choose>
	                    		<c:when test="${afterSalesVo.refundAmountStatus eq 0}">无退款</c:when>
	                    		<c:when test="${afterSalesVo.refundAmountStatus eq 1}">未退款</c:when>
	                    		<c:when test="${afterSalesVo.refundAmountStatus eq 2}">部分退款</c:when>
	                    		<c:when test="${afterSalesVo.refundAmountStatus eq 3}">已退款</c:when>
	                    		<c:when test="${afterSalesVo.refundAmountStatus eq 4}">退款中</c:when>
	                    		<c:otherwise>--</c:otherwise>
	                    	</c:choose>
                    	</span>
                    </td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">交易记录</div>
			<c:if test="${afterSalesVo.atferSalesStatus eq 1}"><!-- 進行中 -->
				<c:if test="${afterSalesVo.realRefundAmount < 0}"> <!-- 实际退款金额负数：即需要收款 -->
					<div class="title-click nobor  pop-new-data" layerParams='{"width":"45%","height":"55%","title":"新增收款",
						"link":"${pageContext.request.contextPath}/finance/after/addFinanceAfterCapital.do?afterSalesId=${afterSalesVo.afterSalesId}&billType=1"}'>新增交易记录</div>
				</c:if>
				<!-- 实际需退金额大于已退金额  并且  退货数量等于(入库数量+系统拦截出库数量) -->
				<%-- <c:if test="${(afterSalesVo.realRefundAmount - (afterSalesVo.haveRefundAmount==null?0:afterSalesVo.haveRefundAmount) > 0) and (back_num eq (storage_num+interceptNum))}">
					<c:if test="${afterSalesVo.refund eq 2}"><!-- refund=2：退给客户 -->
						<div class="title-click nobor  pop-new-data" layerParams='{"width":"40%","height":"45%","title":"新增退款",
							"link":"${pageContext.request.contextPath}/finance/after/addFinanceAfterCapital.do?afterSalesId=${afterSalesVo.afterSalesId}&billType=2"}'>新增交易记录</div>
					</c:if>
				</c:if> --%>
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
							<td><fmt:formatNumber type="number" value="${acb.amount}" pattern="0.00" maxFractionDigits="2" /></td>
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
			<div class="table-title nobor">退票材料</div>
		</div>
		<table class="table">
			<thead>
				<tr>
					<th>材料</th>
					<th>操作人</th>
					<th>时间</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty afterSalesVo.afterInvoiceAttachmentList}">
					<c:forEach items="${afterSalesVo.afterInvoiceAttachmentList}" var="aia">
						<tr>
							<td class="font-blue"><a href="http://${aia.domain}${aia.uri}" target="_blank">${aia.name}</a></td>
							<td>
								<c:forEach var="user" items="${afterSalesVo.userList}">
									<c:if test="${user.userId eq aia.creator}">${user.username}</c:if>
								</c:forEach>
							</td>
							<td><date:date value="${aia.addTime}" /></td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
	</div>

	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">沟通记录</div>
			<%-- <c:if test="${buyorderVo.status ne 3}">
				<div class="title-click nobor  pop-new-data" layerParams='{"width":"850px","height":"460px","title":"新增沟通记录",
					"link":"./addCommunicatePage.do?afterSalesId=${afterSalesVo.afterSalesId}&&traderId=${afterSalesVo.traderId }&&flag=wsx"}'>
					新增
				</div>
			</c:if> --%>
		</div>
		<table class="table table-bordered table-striped table-condensed table-centered">
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
					<!-- <th>操作</th> -->
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty communicateList}">
					<c:forEach items="${communicateList}" var="communicateRecord" varStatus="">
						<tr>
							<td><date:date value="${communicateRecord.begintime} " />~<date:date value="${communicateRecord.endtime}" format="HH:mm:ss" /></td>
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

							<%-- <td class="caozuo">
								<span class="border-blue pop-new-data" layerParams='{"width":"60%","height":"63%","title":"编辑沟通记录",
		                        	"link":"./editcommunicate.do?communicateRecordId=${communicateRecord.communicateRecordId}&afterSalesId=${afterSalesVo.afterSalesId}&&traderId=${afterSalesVo.traderId }&&flag=wsx"}'>
		                        	编辑
		                        </span>
							</td> --%>
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
								<c:forEach var="user" items="${afterSalesVo.userList}">
									<c:if test="${user.userId eq asi.creator}">${user.username}</c:if>
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
       	<c:if test="${invoiceApply != null && companyId != 1}">
        <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                   	开票审核记录 
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
					<c:if test="${null!=historicActivityInstanceInvoice}">
                    <c:forEach var="hii" items="${historicActivityInstanceInvoice}" varStatus="status">
                    <c:if test="${not empty  hii.activityName}">
                    <tr>
                    	<td>
                    		<c:choose>
							<c:when test="${hii.activityType == 'startEvent'}"> 
							${startUserInvoice}
							</c:when>
							<c:when test="${hii.activityType == 'intermediateThrowEvent'}">
							</c:when>
							<c:otherwise>
								<c:if test="${historicActivityInstanceInvoice.size() == status.count}">
									${verifyUsersInvoice}
								</c:if>
								<c:if test="${historicActivityInstanceInvoice.size() != status.count}">
									${hii.assignee}  
								</c:if>   
							</c:otherwise>
						</c:choose>
                    	
                    	
                    	</td>
                        <td><fmt:formatDate value="${hii.endTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        <td>
                        <c:choose>
									<c:when test="${hii.activityType == 'startEvent'}"> 
							开始
							</c:when>
									<c:when test="${hii.activityType == 'intermediateThrowEvent'}"> 
							结束
							</c:when>
							<c:otherwise>   
							${hii.activityName}  
							</c:otherwise>
						</c:choose>
						</td>
                        <td class="font-red">${commentMapInvoice[hii.taskId]}</td>
                    </tr>
                    </c:if>
                    </c:forEach>
                    </c:if>
                	<c:if test="${null==historicActivityInstanceInvoice}">
                		<!-- 查询无结果弹出 -->
		           		<tr>
					       <td colspan='4'>暂无记录！</td>
					    </tr>
                	</c:if>
		       			
                </tbody>
            </table>
        </div>
		</c:if>
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
